package org.ofbiz.accounting.thirdparty.alipay;


import javolution.util.FastMap;
import org.ofbiz.accounting.thirdparty.Charge;
import org.ofbiz.accounting.thirdparty.alipay.config.AlipayConfig;
import org.ofbiz.accounting.thirdparty.alipay.util.AlipayNotify;
import org.ofbiz.accounting.thirdparty.alipay.util.AlipaySubmit;
import org.ofbiz.accounting.thirdparty.alipay.util.UtilDate;
import org.ofbiz.base.util.*;
import org.ofbiz.entity.Delegator;
import org.ofbiz.entity.GenericEntityException;
import org.ofbiz.entity.GenericValue;
import org.ofbiz.entity.transaction.GenericTransactionException;
import org.ofbiz.entity.transaction.TransactionUtil;
import org.ofbiz.order.order.OrderChangeHelper;
import org.ofbiz.product.store.ProductStoreWorker;
import org.ofbiz.service.GenericServiceException;
import org.ofbiz.service.LocalDispatcher;
import org.ofbiz.service.ModelService;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.math.BigDecimal;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * Created by csy on 2014/11/2.
 */
public class AlipayEvents {

    public static final String resource = "AccountingUiLabels";
    public static final String resourceErr = "AccountingErrorUiLabels";
    public static final String commonResource = "CommonUiLabels";
    public static final String module = AlipayEvents.class.getName();


    public static String orderCallAlipay(HttpServletRequest request, HttpServletResponse response) throws Exception {
        String orderId = request.getParameter("orderId");
        if (orderId != null && !orderId.equals("")) {
            request.setAttribute("orderId", orderId);

            return callAlipay(request, response);
        } else {
            return "error";
        }
    }


    /**
     * Initiate AliPay Request
     */
    public static String callAlipay(HttpServletRequest request, HttpServletResponse response) throws Exception {
        Locale locale = UtilHttp.getLocale(request);
        Delegator delegator = (Delegator) request.getAttribute("delegator");
        GenericValue userLogin = (GenericValue) request.getSession().getAttribute("userLogin");

        // get the orderId
        String orderId = (String) request.getAttribute("orderId");

        // get the order header
        GenericValue orderHeader = null;
        try {
            orderHeader = delegator.findByPrimaryKey("OrderHeader", UtilMisc.toMap("orderId", orderId));
        } catch (GenericEntityException e) {
            Debug.logError(e, "Cannot get the order header for order: " + orderId, module);
            request.setAttribute("_ERROR_MESSAGE_", UtilProperties.getMessage(resourceErr, "aliPayEvents.problemsGettingOrderHeader", locale));
            return "error";
        }

        // get the order total
        String orderTotal = orderHeader.getBigDecimal("grandTotal").toPlainString();
        String currencyUom = orderHeader.getString("currencyUom");

        // get the product store
        GenericValue productStore = ProductStoreWorker.getProductStore(request);

        if (productStore == null) {
            Debug.logError("ProductStore is null", module);
            request.setAttribute("_ERROR_MESSAGE_", UtilProperties.getMessage(resourceErr, "alipayEvents.problemsGettingMerchantConfiguration", locale));
            return "error";
        }

        // get the payment properties file
        // 设置alipay的
        GenericValue paymentConfig = ProductStoreWorker.getProductStorePaymentSetting(delegator, productStore.getString("productStoreId"), "EXT_ALIPAY", null, true);
        String configString = null;
        String paymentGatewayConfigId = null;
        if (paymentConfig != null) {
            paymentGatewayConfigId = paymentConfig.getString("paymentGatewayConfigId");
            configString = paymentConfig.getString("paymentPropertiesPath");
        }

        if (configString == null) {
            configString = "payment.properties";
        }

        // get the company name
        String company = UtilFormatOut.checkEmpty(productStore.getString("companyName"), "");

        // create the item name
        String itemName = UtilProperties.getMessage(resource, "AccountingOrderNr", locale) + orderId + " " +
                (company != null ? UtilProperties.getMessage(commonResource, "CommonFrom", locale) + " " + company : "");
        String itemNumber = "1";

        // get the redirect url
        String redirectUrl = getPaymentGatewayConfigValue(delegator, paymentGatewayConfigId, "redirectUrl", configString, "payment.alipay.redirect");

        // get the notify url
        String notifyUrl = getPaymentGatewayConfigValue(delegator, paymentGatewayConfigId, "notifyUrl", configString, "payment.alipay.notify");

        // get the return urls
        String returnUrl = getPaymentGatewayConfigValue(delegator, paymentGatewayConfigId, "returnUrl", configString, "payment.alipay.return");

        // get the cancel return urls
        String cancelReturnUrl = getPaymentGatewayConfigValue(delegator, paymentGatewayConfigId, "cancelReturnUrl", configString, "payment.alipay.cancelReturn");

        // get the paypal account
        String aliPayAccount = getPaymentGatewayConfigValue(delegator, paymentGatewayConfigId, "sellerEmail", configString, "payment.alipay.business");

//    判断接口类型是双功能还是及时到账接口

        String interfaceType = getPaymentGatewayConfigValue(delegator, paymentGatewayConfigId, "interfaceType", configString, "payment.alipay.interface.type");
        String interfaceName = getPaymentGatewayConfigValue(delegator, paymentGatewayConfigId, "interfaceName", configString, "payment.alipay.interface.name");
        String partner = getPaymentGatewayConfigValue(delegator, paymentGatewayConfigId, "partner", configString, "payment.alipay.partner");
        String pubKey = getPaymentGatewayConfigValue(delegator, paymentGatewayConfigId, "publicKey", configString, "payment.alipay.public.key");
         String privateKey = getPaymentGatewayConfigValue(delegator, paymentGatewayConfigId, "privateKey", configString, "payment.alipay.private.key");
      

        if (UtilValidate.isEmpty(redirectUrl)
                || UtilValidate.isEmpty(notifyUrl)
                || UtilValidate.isEmpty(returnUrl)
                || UtilValidate.isEmpty(aliPayAccount)) {
            Debug.logError("Payment properties is not configured properly, some notify URL from Alipay is not correctly defined!", module);
            request.setAttribute("_ERROR_MESSAGE_", UtilProperties.getMessage(resourceErr, "aliPayEvents.problemsGettingMerchantConfiguration", locale));
            return "error";
        }
//        sParaTemp.put("service", AlipayConfig.service);
//        sParaTemp.put("partner", AlipayConfig.partner);
//        sParaTemp.put("seller_id", AlipayConfig.seller_id);
//        sParaTemp.put("_input_charset", AlipayConfig.input_charset);
//        sParaTemp.put("payment_type", AlipayConfig.payment_type);
//        sParaTemp.put("notify_url", AlipayConfig.notify_url);
//        sParaTemp.put("return_url", AlipayConfig.return_url);
//        sParaTemp.put("anti_phishing_key", AlipayConfig.anti_phishing_key);
//        sParaTemp.put("exter_invoke_ip", AlipayConfig.exter_invoke_ip);
//        sParaTemp.put("out_trade_no", out_trade_no);
//        sParaTemp.put("subject", subject);
//        sParaTemp.put("total_fee", total_fee);
//        sParaTemp.put("body", body);
        // create alipay the redirect string
        Map<String, String> parameters = new HashMap<String, String>();
        //从payment_gateway_alipay中取出公共信息
        parameters.put("service", interfaceName);
        parameters.put("partner", partner);
        parameters.put("seller_id", partner);
        parameters.put("_input_charset", AlipayConfig.input_charset);
        parameters.put("payment_type", org.ofbiz.accounting.thirdparty.alipay.config.AlipayConfig.payment_type);
        parameters.put("notify_url", notifyUrl);
        parameters.put("return_url", returnUrl);
        parameters.put("anti_phishing_key", AlipayConfig.anti_phishing_key);
        parameters.put("exter_invoke_ip", AlipayConfig.exter_invoke_ip);
//        parameters.put("seller_email", aliPayAccount);
        String curDate = UtilDate.getOrderNum();
        String out_trade_no = orderId + "_" + curDate;
        parameters.put("out_trade_no", out_trade_no);
        //下面是从购物车中取，参考paypal
        parameters.put("subject", itemName);  //订单名称
        parameters.put("total_fee", orderTotal);
        parameters.put("body", itemName);//订单描述
        parameters.put("show_url", "http://yuaoq.com/ecommerce");//

//    parameters.put("subject", "test");
//    parameters.put("body", "test00002");//订单描述

//        parameters.put("anti_phishing_key", "");//防钓鱼时间戳 必须开通
//        parameters.put("exter_invoke_ip", request.getLocalAddr()); //客户端IP
 /*
        if (interfaceType.equalsIgnoreCase("redirect")) {
            parameters.put("total_fee", orderTotal);
        }

//        如果是双功能
       if (interfaceType.equalsIgnoreCase("double")) {

            parameters.put("price", orderTotal);
            parameters.put("quantity", itemNumber);

            parameters.put("logistics_fee", "0");
            parameters.put("logistics_type", "EXPRESS");
            parameters.put("logistics_payment", "SELLER_PAY");

            //需要获取买家信息
            List<Map<String, GenericValue>> orderContact = ContactMechWorker.getOrderContactMechValueMaps(delegator, orderId);
            if (orderContact != null && orderContact.size() > 0) {
                for (int i = 0; i < orderContact.size(); i++) {
                    Map<String, GenericValue> valueMap = orderContact.get(i);
                    GenericValue value = valueMap.get("postalAddress");
                    if (value != null) {

                        parameters.put("receive_name", (String) value.get("attnName"));
                        parameters.put("receive_address", (String) value.get("address1"));
                        parameters.put("receive_zip", (String) value.get("postalCode"));
                    }

                }
            }


            List<Map<String, Object>> contacts = ContactMechWorker.getPartyContactMechValueMaps(delegator, (String) userLogin.get("partyId"), false, "TELECOM_NUMBER");
            if (contacts != null && contacts.size() > 0) {
                Map<String, Object> contact = contacts.get(0);
                GenericValue phoneObj = (GenericValue) contact.get("telecomNumber");
                String mobilePhone = (String) phoneObj.get("contactNumber");
                parameters.put("receive_phone", mobilePhone);
                parameters.put("receive_mobile", mobilePhone);
            }

//
        }*/


        //建立请求,该方法是创建一个form表单提交
//        String sHtmlText = AlipaySubmit.buildRequest("","",parameters);
        String sHtmlText = org.ofbiz.accounting.thirdparty.alipay.util.AlipaySubmit.buildRequest(parameters, "get", "确定", "PC",privateKey);

        sHtmlText = new String(sHtmlText);


        //直接提交给支付宝
//  String sHtmlText = AlipaySubmit.buildRequest(null,null,parameters);


        // set the order in the session for cancelled orders
        request.getSession().setAttribute("ALIPAY_ORDER", orderId);

        // redirect to paypal
        try {
            response.setContentType("text/html; charset=UTF-8");
            response.setCharacterEncoding("utf-8");
//            response.getOutputStream().print(sHtmlText);
            response.getWriter().print(sHtmlText);


        } catch (IOException e) {
            Debug.logError(e, "Problems redirecting to AliPay", module);
            request.setAttribute("_ERROR_MESSAGE_", UtilProperties.getMessage(resourceErr, "aliPayEvents.problemsConnectingWithAliPay", locale));
            return "error";
        }

        return "success";
    }


    /**
     * Initiate AliPay WAP Request
     */
    public static String callWapAlipay(HttpServletRequest request, HttpServletResponse response) throws Exception {
        Locale locale = UtilHttp.getLocale(request);
        Delegator delegator = (Delegator) request.getAttribute("delegator");
        GenericValue userLogin = (GenericValue) request.getSession().getAttribute("userLogin");

        // get the orderId
        String orderId = request.getParameter("orderId");
        if (orderId == null) {
            orderId = (String) request.getAttribute("orderId");
        }
        //out_trade_no 64字符，为了保证唯一性orderId+timestamp


        // get the order header
        GenericValue orderHeader = null;
        try {
            orderHeader = delegator.findByPrimaryKey("OrderHeader", UtilMisc.toMap("orderId", orderId));
        } catch (GenericEntityException e) {
            Debug.logError(e, "Cannot get the order header for order: " + orderId, module);
            request.setAttribute("_ERROR_MESSAGE_", UtilProperties.getMessage(resourceErr, "aliPayEvents.problemsGettingOrderHeader", locale));
            return "error";
        }

        // get the order total
        String orderTotal = orderHeader.getBigDecimal("grandTotal").toPlainString();
        String price = request.getParameter("price");
        String subject = (String) orderHeader.get("orderName");
        //IF user enter amount
        if (price != null && (!price.equals(""))) {
            orderTotal = price;
            //因为是用户输入金额
        }
        request.getSession().setAttribute("_ALIPAY_PRICE", orderTotal);
        // get the product store
        GenericValue productStore = ProductStoreWorker.getProductStore(request);

        if (productStore == null) {
            Debug.logError("ProductStore is null", module);
            request.setAttribute("_ERROR_MESSAGE_", UtilProperties.getMessage(resourceErr, "alipayEvents.problemsGettingMerchantConfiguration", locale));
            return "error";
        }

        // get the payment properties file
        // 设置alipay的
        GenericValue paymentConfig = ProductStoreWorker.getProductStorePaymentSetting(delegator, productStore.getString("productStoreId"), "EXT_ALIPAY", null, true);
        String configString = null;
        String paymentGatewayConfigId = null;
        if (paymentConfig != null) {
            paymentGatewayConfigId = paymentConfig.getString("paymentGatewayConfigId");
            configString = paymentConfig.getString("paymentPropertiesPath");
        }

        if (configString == null) {
            configString = "payment.properties";
        }

        // get the company name
        String company = UtilFormatOut.checkEmpty(productStore.getString("companyName"), "");

        // create the item name
        String itemName = UtilProperties.getMessage(resource, "AccountingOrderNr", locale) + orderId + " " +
                (company != null ? UtilProperties.getMessage(commonResource, "CommonFrom", locale) + " " + company : "");
//        itemName="test";
        // get the redirect url
        String redirectUrl = getPaymentGatewayConfigValue(delegator, paymentGatewayConfigId, "wapRedirectUrl", configString, "payment.alipay.wap.redirect");

        // get the notify url
        String notifyUrl = getPaymentGatewayConfigValue(delegator, paymentGatewayConfigId, "wapNotifyUrl", configString, "payment.alipay.wap.notify");

        //wap 异步通知不使用，
//        notifyUrl = "";

        //操作中断返回地址
        String merchant_url = getPaymentGatewayConfigValue(delegator, paymentGatewayConfigId, "wapMerchantUrl", configString, "payment.alipay.wap.merchant");
        //用户付款中途退出返回商户的地址。需http://格式的完整路径，不允许加?id=123这类自定义参数

        // get the return urls
        String returnUrl = getPaymentGatewayConfigValue(delegator, paymentGatewayConfigId, "wapReturnUrl", configString, "payment.alipay.wap.return");

        // get the cancel return urls
        String cancelReturnUrl = getPaymentGatewayConfigValue(delegator, paymentGatewayConfigId, "wapCancelReturnUrl", configString, "payment.alipay.wap.cancelReturn");

        // get the paypal account
        String aliPayAccount = getPaymentGatewayConfigValue(delegator, paymentGatewayConfigId, "sellerEmail", configString, "payment.alipay.business");

        String interfaceType = getPaymentGatewayConfigValue(delegator, paymentGatewayConfigId, "interfaceType", configString, "payment.alipay.wap.interface.type");
        String interfaceName = getPaymentGatewayConfigValue(delegator, paymentGatewayConfigId, "wapInterfaceName", configString, "payment.alipay.wap.interface.name");
        String pubwapKey = getPaymentGatewayConfigValue(delegator, paymentGatewayConfigId, "wapPublicKey", configString, "payment.alipay.wap.public.key");
        AlipayConfig.alipay_wap_public_key = pubwapKey;
        String privatewapKey = getPaymentGatewayConfigValue(delegator, paymentGatewayConfigId, "wapPrivateKey", configString, "payment.alipay.wap.private.key");
        AlipayConfig.wap_private_key = privatewapKey;


        // 判断接口类型是双功能还是及时到账接口

        String partner = getPaymentGatewayConfigValue(delegator, paymentGatewayConfigId, "partner", configString, "payment.alipay.partner");
        AlipayConfig.partner = partner;


        if (UtilValidate.isEmpty(redirectUrl)
               /* || UtilValidate.isEmpty(notifyUrl)*/
                || UtilValidate.isEmpty(returnUrl)
                || UtilValidate.isEmpty(aliPayAccount)) {
            Debug.logError("Payment properties is not configured properly, some notify URL from Alipay is not correctly defined!", module);
            request.setAttribute("_ERROR_MESSAGE_", UtilProperties.getMessage(resourceErr, "aliPayEvents.problemsGettingMerchantConfiguration", locale));
            return "error";
        }
        String curDate = UtilDate.getOrderNum();
        String out_trade_no = orderId + "_" + curDate;

       /* //请求业务参数详细
        String req_dataToken = "<direct_trade_create_req><notify_url>" + notifyUrl + "</notify_url><call_back_url>" + returnUrl +
                "</call_back_url><seller_account_name>" + aliPayAccount + "</seller_account_name><out_trade_no>" + out_trade_no + "</out_trade_no><subject>"
                + itemName + "</subject><total_fee>" + orderTotal + "</total_fee><merchant_url>" + merchant_url + "</merchant_url></direct_trade_create_req>";

        // create alipay the redirect string
        Map<String, String> parameters = new HashMap<String, String>();
        //从payment_gateway_alipay中取出公共信息
        parameters.put("service", interfaceName);
        parameters.put("partner", partner);
        parameters.put("_input_charset", AlipayConfig.input_charset); //从
        parameters.put("sec_id", AlipayConfig.wap_sign_type);
        parameters.put("format", AlipayConfig.format);
        parameters.put("v", AlipayConfig.v);
        parameters.put("req_id", curDate);
        parameters.put("req_data", req_dataToken);
        //建立请求
        String sHtmlTextToken = AlipaySubmit.buildRequest(redirectUrl, "", "", parameters);
        //URLDECODE返回的信息
        sHtmlTextToken = URLDecoder.decode(sHtmlTextToken, AlipayConfig.input_charset);
        //获取token
        String request_token = AlipaySubmit.getRequestToken(sHtmlTextToken);*/
        //out.println(request_token);

        ////////////////////////////////////根据授权码token调用交易接口alipay.wap.auth.authAndExecute//////////////////////////////////////


        //业务详细
//        String req_data = "<auth_and_execute_req><request_token>" + request_token + "</request_token></auth_and_execute_req>";
    
    
        //把请求参数打包成数组
        Map<String, String> sParaTemp = new HashMap<String, String>();
    
    
        sParaTemp.put("service", interfaceName);
        sParaTemp.put("partner", partner);
        sParaTemp.put("seller_id", partner);
        sParaTemp.put("_input_charset", AlipayConfig.input_charset);
        sParaTemp.put("payment_type", AlipayConfig.payment_type);
        sParaTemp.put("notify_url", notifyUrl);
        sParaTemp.put("return_url", returnUrl);
        sParaTemp.put("out_trade_no", out_trade_no);
        sParaTemp.put("subject", itemName);
        sParaTemp.put("total_fee", orderTotal);
        sParaTemp.put("show_url", merchant_url);
        sParaTemp.put("body", itemName);
        Map<String, String> sPara = AlipaySubmit.buildRequestPara(sParaTemp, "WAP",privatewapKey);
    
    
        Map<String, Object> credential = new HashMap<String, Object>();
        credential.put("alipay_wap", sPara);
        Charge charge = new Charge();
        charge.setId(orderId);
        charge.setChannel("alipay_wap");
        charge.setCredential(credential);
        charge.setLivemode(true);
    
        Map map = FastMap.newInstance();
    
        map.put("charge", charge);
        map.put("payment","alipay");
        request.setAttribute("resultData",map);
/*        request.setAttribute("charge", map);
        request.setAttribute("payment", "alipay");*/
    
    
        //建立请求
//        String sHtmlText = AlipaySubmit.buildRequest(redirectUrl, sParaTemp, "get", "确认");
    
    
        //直接提交给支付宝
//        String sHtmlText = AlipaySubmit.buildRequest(null,null,parameters);
    
        // set the order in the session for cancelled orders
//        request.getSession().setAttribute("ALIPAY_ORDER", orderId);
    
        // redirect to paypal
        /*try {
            response.setContentType("text/html; charset=UTF-8");
            response.setCharacterEncoding("utf-8");
//            response.getOutputStream().print(sHtmlText);
            response.getWriter().print(sHtmlText);


        } catch (IOException e) {
            Debug.logError(e, "Problems redirecting to AliPay", module);
            request.setAttribute("_ERROR_MESSAGE_", UtilProperties.getMessage(resourceErr, "aliPayEvents.problemsConnectingWithAliPay", locale));
            return "error";
        }*/
    
        return "success";
    }

    /**
     * AliPay Call-Back Event
     */
    public static String alipayCallBack(HttpServletRequest request, HttpServletResponse response) throws UnsupportedEncodingException {
        Locale locale = UtilHttp.getLocale(request);
        Delegator delegator = (Delegator) request.getAttribute("delegator");
        LocalDispatcher dispatcher = (LocalDispatcher) request.getAttribute("dispatcher");

        // get the product store
        GenericValue productStore = ProductStoreWorker.getProductStore(request);
        if (productStore == null) {
            Debug.logError("ProductStore is null", module);
            request.setAttribute("_ERROR_MESSAGE_", UtilProperties.getMessage(resourceErr, "aliPayEvents.problemsGettingMerchantConfiguration", locale));
            return "error";
        }

        // get the payment properties file
        GenericValue paymentConfig = ProductStoreWorker.getProductStorePaymentSetting(delegator, productStore.getString("productStoreId"), "EXT_ALIPAY", null, true);

        String configString = null;
        String paymentGatewayConfigId = null;
        if (paymentConfig != null) {
            paymentGatewayConfigId = paymentConfig.getString("paymentGatewayConfigId");
            configString = paymentConfig.getString("paymentPropertiesPath");
        }

        if (configString == null) {
            configString = "payment.properties";
        }
        // send off the confirm request
        // get the system user
        GenericValue userLogin = null;
        try {
            userLogin = delegator.findByPrimaryKey("UserLogin", UtilMisc.toMap("userLoginId", "system"));
        } catch (GenericEntityException e) {
            Debug.logError(e, "Cannot get UserLogin for: system; cannot continue", module);
            request.setAttribute("_ERROR_MESSAGE_", UtilProperties.getMessage(resourceErr, "aliPayEvents.problemsGettingAuthenticationUser", locale));
            return "error";
        }
        // get the orderId
        String orderId = request.getParameter("out_trade_no");
        orderId = orderId.substring(0, orderId.indexOf("_"));
        // get the order header
        GenericValue orderHeader = null;
        if (UtilValidate.isNotEmpty(orderId)) {
            try {
                orderHeader = delegator.findByPrimaryKey("OrderHeader", UtilMisc.toMap("orderId", orderId));
            } catch (GenericEntityException e) {
                Debug.logError(e, "Cannot get the order header for order: " + orderId, module);
                request.setAttribute("_ERROR_MESSAGE_", UtilProperties.getMessage(resourceErr, "aliPayEvents.problemsGettingOrderHeader", locale));
                return "error";
            }
        } else {
            Debug.logError("AliPay did not callback with a valid orderId!", module);
            request.setAttribute("_ERROR_MESSAGE_", UtilProperties.getMessage(resourceErr, "aliPayEvents.noValidOrderIdReturned", locale));
            return "error";
        }

        if (orderHeader == null) {
            Debug.logError("Cannot get the order header for order: " + orderId, module);
            request.setAttribute("_ERROR_MESSAGE_", UtilProperties.getMessage(resourceErr, "aliPayEvents.problemsGettingOrderHeader", locale));
            return "error";
        }

        /*  get payment data
        String paymentCurrency = request.getParameter("mc_currency");
        String paymentAmount = request.getParameter("mc_gross");
        String paymentFee = request.getParameter("mc_fee");
        String transactionId = request.getParameter("txn_id");
        */

        // get the transaction status
        String trade_status = new String(request.getParameter("trade_status").getBytes("ISO-8859-1"), "UTF-8");

        //支付宝处理Notify
        //获取支付宝POST过来反馈信息
        Map<String, String> params = new HashMap<String, String>();
        Map requestParams = request.getParameterMap();
        for (Iterator iter = requestParams.keySet().iterator(); iter.hasNext(); ) {
            String name = (String) iter.next();
            String[] values = (String[]) requestParams.get(name);
            String valueStr = "";
            for (int i = 0; i < values.length; i++) {
                valueStr = (i == values.length - 1) ? valueStr + values[i]
                        : valueStr + values[i] + ",";
            }
            //乱码解决，这段代码在出现乱码时使用。如果mysign和sign不相等也可以使用这段代码转化
            //valueStr = new String(valueStr.getBytes("ISO-8859-1"), "gbk");
            params.put(name, valueStr);
        }
        String partner = getPaymentGatewayConfigValue(delegator, paymentGatewayConfigId, "partner", configString, "payment.alipay.partner");
        String pubKey = getPaymentGatewayConfigValue(delegator, paymentGatewayConfigId, "publicKey", configString, "payment.alipay.public.key");

        if (org.ofbiz.accounting.thirdparty.alipay.util.AlipayNotify.verify(params, partner,pubKey)) {//验证成功
            //////////////////////////////////////////////////////////////////////////////////////////
            //请在这里加上商户的业务逻辑程序代码

            //——请根据您的业务逻辑来编写程序（以下代码仅作参考）——
            // attempt to start a transaction
            boolean okay = true;
            boolean beganTransaction = false;
            try {
                beganTransaction = TransactionUtil.begin();

                if (trade_status.equals("TRADE_FINISHED")) {
                    //判断该笔订单是否在商户网站中已经做过处理
                    //如果没有做过处理，根据订单号（out_trade_no）在商户网站的订单系统中查到该笔订单的详细，并执行商户的业务程序
                    //如果有做过处理，不执行商户的业务程序
                    okay = OrderChangeHelper.approveOrder(dispatcher, userLogin, orderId);
                    //注意：
                    //该种交易状态只在两种情况下出现
                    //1、开通了普通即时到账，买家付款成功后。
                    //2、开通了高级即时到账，从该笔交易成功时间算起，过了签约时的可退款时限（如：三个月以内可退款、一年以内可退款等）后。
                } else if (trade_status.equals("TRADE_SUCCESS")) {
                    //判断该笔订单是否在商户网站中已经做过处理
                    //如果没有做过处理，根据订单号（out_trade_no）在商户网站的订单系统中查到该笔订单的详细，并执行商户的业务程序
                    //如果有做过处理，不执行商户的业务程序
                    okay = OrderChangeHelper.approveOrder(dispatcher, userLogin, orderId);
                    //注意：
                    //该种交易状态只在一种情况下出现——开通了高级即时到账，买家付款成功后。
                } else if (trade_status.equals("WAIT_SELLER_SEND_GOODS")) {
                    //判断该笔订单是否在商户网站中已经做过处理
                    //如果没有做过处理，根据订单号（out_trade_no）在商户网站的订单系统中查到该笔订单的详细，并执行商户的业务程序
                    //如果有做过处理，不执行商户的业务程序
                    okay = OrderChangeHelper.approveOrder(dispatcher, userLogin, orderId);
                    //注意：
                    //该种交易状态只在一种情况下出现——开通了高级即时到账，买家付款成功后。
                } else {
                    //是否还有其他的情况？？？？
                    okay = OrderChangeHelper.cancelOrder(dispatcher, userLogin, orderId);
                }

                if (okay) {
                    // set the payment preference
                    okay = setPaymentPreferences(delegator, dispatcher, userLogin, orderId, request);
                }

            } catch (Exception e) {
                String errMsg = "Error handling AliPay notification";
                Debug.logError(e, errMsg, module);
                try {
                    TransactionUtil.rollback(beganTransaction, errMsg, e);
                } catch (GenericTransactionException gte2) {
                    Debug.logError(gte2, "Unable to rollback transaction", module);
                }
            } finally {
                if (!okay) {
                    try {
                        TransactionUtil.rollback(beganTransaction, "Failure in processing AliPay callback", null);
                    } catch (GenericTransactionException gte) {
                        Debug.logError(gte, "Unable to rollback transaction", module);
                    }
                } else {
                    try {
                        TransactionUtil.commit(beganTransaction);
                    } catch (GenericTransactionException gte) {
                        Debug.logError(gte, "Unable to commit transaction", module);
                    }
                }
            }


            if (okay) {
                // attempt to release the offline hold on the order (workflow)
                OrderChangeHelper.releaseInitialOrderHold(dispatcher, orderId);

                // call the email confirm service
                Map<String, String> emailContext = UtilMisc.toMap("orderId", orderId);
                try {
                    dispatcher.runSync("sendOrderConfirmation", emailContext);
                } catch (GenericServiceException e) {
                    Debug.logError(e, "Problems sending email confirmation", module);
                }
            }
        }
        return "success";
    }

    /**
     * AliPay Call-Back Event  目前只用于同步返回的情况
     */
    public static String alipayWapCallBack(HttpServletRequest request, HttpServletResponse response) throws UnsupportedEncodingException {Locale locale = UtilHttp.getLocale(request);
        Delegator delegator = (Delegator) request.getAttribute("delegator");
        LocalDispatcher dispatcher = (LocalDispatcher) request.getAttribute("dispatcher");

        // get the product store
        GenericValue productStore = ProductStoreWorker.getProductStore(request);
        if (productStore == null) {
            Debug.logError("ProductStore is null", module);
            request.setAttribute("_ERROR_MESSAGE_", UtilProperties.getMessage(resourceErr, "aliPayEvents.problemsGettingMerchantConfiguration", locale));
            return "error";
        }

        // get the system user
        GenericValue userLogin = null;
        try {
            userLogin = delegator.findByPrimaryKey("UserLogin", UtilMisc.toMap("userLoginId", "system"));
        } catch (GenericEntityException e) {
            Debug.logError(e, "Cannot get UserLogin for: system; cannot continue", module);
            request.setAttribute("_ERROR_MESSAGE_", UtilProperties.getMessage(resourceErr, "aliPayEvents.problemsGettingAuthenticationUser", locale));
            return "error";
        }

        // get the orderId
        String orderId = request.getParameter("out_trade_no");
        orderId = orderId.substring(0, orderId.indexOf("_"));

        // get the order header
        GenericValue orderHeader = null;
        if (UtilValidate.isNotEmpty(orderId)) {
            try {
                orderHeader = delegator.findByPrimaryKey("OrderHeader", UtilMisc.toMap("orderId", orderId));
            } catch (GenericEntityException e) {
                Debug.logError(e, "Cannot get the order header for order: " + orderId, module);
                request.setAttribute("_ERROR_MESSAGE_", UtilProperties.getMessage(resourceErr, "aliPayEvents.problemsGettingOrderHeader", locale));
                return "error";
            }
        } else {
            Debug.logError("AliPay did not callback with a valid orderId!", module);
            request.setAttribute("_ERROR_MESSAGE_", UtilProperties.getMessage(resourceErr, "aliPayEvents.noValidOrderIdReturned", locale));
            return "error";
        }

        if (orderHeader == null) {
            Debug.logError("Cannot get the order header for order: " + orderId, module);
            request.setAttribute("_ERROR_MESSAGE_", UtilProperties.getMessage(resourceErr, "aliPayEvents.problemsGettingOrderHeader", locale));
            return "error";
        }

        //判断订单是否已经支付，如果已经支付则return success
        try {
            Map<String, String> paymentFields = UtilMisc.toMap("orderId", orderId, "statusId", "PAYMENT_RECEIVED");
            List<GenericValue> paymentPrefs = delegator.findByAnd("OrderPaymentPreference", paymentFields);
            if (UtilValidate.isNotEmpty(paymentPrefs)) {
                response.getOutputStream().print("success");
                response.getOutputStream().flush();
                return "error";
            }
        } catch (GenericEntityException e) {
            Debug.logError(e, "Cannot get payment preferences for order #" + orderId, module);
            return "success";
        } catch (IOException e) {
            e.printStackTrace();
            return "fail";
        }

        // get the payment properties file
        // 设置alipay的
        GenericValue paymentConfig = ProductStoreWorker.getProductStorePaymentSetting(delegator, productStore.getString("productStoreId"), "EXT_ALIPAY", null, true);
        String configString = null;
        String paymentGatewayConfigId = null;
        if (paymentConfig != null) {
            paymentGatewayConfigId = paymentConfig.getString("paymentGatewayConfigId");
            configString = paymentConfig.getString("paymentPropertiesPath");
        }

        if (configString == null) {
            configString = "payment.properties";
        }
        String key = getPaymentGatewayConfigValue(delegator, paymentGatewayConfigId, "wapKey", configString, "payment.alipay.wap.key");
        /*  get payment data
        String paymentCurrency = request.getParameter("mc_currency");
        String paymentAmount = request.getParameter("mc_gross");
        String paymentFee = request.getParameter("mc_fee");
        String transactionId = request.getParameter("txn_id");
        */
        Map<String, String> params = new HashMap<String, String>();
        Map requestParams = request.getParameterMap();
        for (Iterator iter = requestParams.keySet().iterator(); iter.hasNext(); ) {
            String name = (String) iter.next();
            String[] values = (String[]) requestParams.get(name);
            String valueStr = "";
            for (int i = 0; i < values.length; i++) {
                valueStr = (i == values.length - 1) ? valueStr + values[i]
                        : valueStr + values[i] + ",";
            }
            //乱码解决，这段代码在出现乱码时使用。如果mysign和sign不相等也可以使用这段代码转化
//            valueStr = new String(valueStr.getBytes("ISO-8859-1"), "utf-8");
            params.put(name, valueStr);
        }


        //计算得出通知验证结果
        String partner = getPaymentGatewayConfigValue(delegator, paymentGatewayConfigId, "partner", configString, "payment.alipay.partner");
         
        String pubwapKey = getPaymentGatewayConfigValue(delegator, paymentGatewayConfigId, "wapPublicKey", configString, "payment.alipay.wap.public.key");
    
        boolean verify_result = AlipayNotify.wapVerify(params, partner,pubwapKey);

        //////////////////////////////////////////////////////////////////////////////////////////
        //请在这里加上商户的业务逻辑程序代码

        //——请根据您的业务逻辑来编写程序（以下代码仅作参考）——
        // attempt to start a transaction
        boolean okay = true;
        if (verify_result) {
            boolean beganTransaction = false;
            try {
                beganTransaction = TransactionUtil.begin();
                if (params.get("trade_status").equals("TRADE_FINISHED") || params.get("trade_status").equals("TRADE_SUCCESS")) {
                    //判断该笔订单是否在商户网站中已经做过处理
                    //如果没有做过处理，根据订单号（out_trade_no）在商户网站的订单系统中查到该笔订单的详细，并执行商户的业务程序
                    //如果有做过处理，不执行商户的业务程序
                    okay = OrderChangeHelper.payOrder(dispatcher, userLogin, orderId);
                    //注意：
                    //该种交易状态只在两种情况下出现
                    //1、开通了普通即时到账，买家付款成功后。
                    //2、开通了高级即时到账，从该笔交易成功时间算起，过了签约时的可退款时限（如：三个月以内可退款、一年以内可退款等）后。
                } else {
                    //是否还有其他的情况？？？？
                    okay = OrderChangeHelper.cancelOrder(dispatcher, userLogin, orderId);
                }

                if (okay) {
                    // set the payment preference
                    okay = setPaymentPreferences(delegator, dispatcher, userLogin, orderId, request);
                }

            } catch (Exception e) {
                String errMsg = "Error handling AliPay notification";
                Debug.logError(e, errMsg, module);
                try {
                    TransactionUtil.rollback(beganTransaction, errMsg, e);
                } catch (GenericTransactionException gte2) {
                    Debug.logError(gte2, "Unable to rollback transaction", module);
                }
            } finally {
                if (!okay) {
                    try {
                        TransactionUtil.rollback(beganTransaction, "Failure in processing AliPay callback", null);
                    } catch (GenericTransactionException gte) {
                        Debug.logError(gte, "Unable to rollback transaction", module);
                    }
                } else {
                    try {
                        TransactionUtil.commit(beganTransaction);
                    } catch (GenericTransactionException gte) {
                        Debug.logError(gte, "Unable to commit transaction", module);
                    }
                }
            }
        }

        if (okay) {
            // attempt to release the offline hold on the order (workflow)
            OrderChangeHelper.releaseInitialOrderHold(dispatcher, orderId);

            // call the email confirm service
            Map<String, String> emailContext = UtilMisc.toMap("orderId", orderId);
            try {
                dispatcher.runSync("sendOrderConfirmation", emailContext);
            } catch (GenericServiceException e) {
                Debug.logError(e, "Problems sending email confirmation", module);
            }
        }

        try {
            //程序执行完后必须打印输出“success”(不包含引号)。如果商户反馈给支付宝的字符不是 success 这 7 个字符,
            // 支付宝服务器会不断重发通知,直到 超过24小时22分钟。一般情况下,25 小时以内完成 8 次通知
            // (通知的间隔频率一般是: 2m,10m,10m,1h,2h,6h,15h);
            response.getOutputStream().print("success");
            response.getOutputStream().flush();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return "success";
    }

    /**
     * Event called when customer cancels a paypal order
     */
    public static String cancellAlipayOrder(HttpServletRequest request, HttpServletResponse response) {
        Locale locale = UtilHttp.getLocale(request);
        LocalDispatcher dispatcher = (LocalDispatcher) request.getAttribute("dispatcher");
        GenericValue userLogin = (GenericValue) request.getSession().getAttribute("userLogin");

        // get the stored order id from the session
        String orderId = (String) request.getSession().getAttribute("ALIPAY_ORDER");

        // attempt to start a transaction
        boolean beganTransaction = false;
        try {
            beganTransaction = TransactionUtil.begin();
        } catch (GenericTransactionException gte) {
            Debug.logError(gte, "Unable to begin transaction", module);
        }

        // cancel the order
        boolean okay = OrderChangeHelper.cancelOrder(dispatcher, userLogin, orderId);

        if (okay) {
            try {
                TransactionUtil.commit(beganTransaction);
            } catch (GenericTransactionException gte) {
                Debug.logError(gte, "Unable to commit transaction", module);
            }
        } else {
            try {
                TransactionUtil.rollback(beganTransaction, "Failure in processing AliPay cancel callback", null);
            } catch (GenericTransactionException gte) {
                Debug.logError(gte, "Unable to rollback transaction", module);
            }
        }

        // attempt to release the offline hold on the order (workflow)
        if (okay)
            OrderChangeHelper.releaseInitialOrderHold(dispatcher, orderId);

        request.setAttribute("_EVENT_MESSAGE_", UtilProperties.getMessage(resourceErr, "aliPayEvents.previousAliPayOrderHasBeenCancelled", locale));
        return "success";
    }


    private static boolean setPaymentPreferences(Delegator delegator, LocalDispatcher dispatcher, GenericValue userLogin, String orderId, HttpServletRequest request) {
        Debug.logVerbose("Setting payment prefrences..", module);
        List<GenericValue> paymentPrefs = null;
        try {
            Map<String, String> paymentFields = UtilMisc.toMap("orderId", orderId, "statusId", "PAYMENT_NOT_RECEIVED");
            paymentPrefs = delegator.findByAnd("OrderPaymentPreference", paymentFields);
        } catch (GenericEntityException e) {
            Debug.logError(e, "Cannot get payment preferences for order #" + orderId, module);
            return false;
        }
        if (paymentPrefs.size() > 0) {
            for (GenericValue pref : paymentPrefs) {
                boolean okay = setPaymentPreference(dispatcher, userLogin, pref, request);
                if (!okay)
                    return false;
            }
        }
        return true;
    }


    private static boolean setPaymentPreference(LocalDispatcher dispatcher, GenericValue userLogin, GenericValue paymentPreference, HttpServletRequest request) {
        Locale locale = UtilHttp.getLocale(request);
        String paymentDate = request.getParameter("notify_time");
        String paymentType = request.getParameter("payment_type");
        String paymentAmount = request.getParameter("total_fee");
        String paymentStatus = request.getParameter("trade_status");
        String transactionId = request.getParameter("trade_no"); //taobao 交易号

        List<GenericValue> toStore = new LinkedList<GenericValue>();

        // PayPal returns the timestamp in the format 'hh:mm:ss Jan 1, 2000 PST'
        // Parse this into a valid Timestamp Object
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-mm-dd hh:mm:ss");
        java.sql.Timestamp authDate = null;
        try {
            authDate = new java.sql.Timestamp(sdf.parse(paymentDate).getTime());
        } catch (ParseException e) {
            Debug.logError(e, "Cannot parse date string: " + paymentDate, module);
            authDate = UtilDateTime.nowTimestamp();
        } catch (NullPointerException e) {
            Debug.logError(e, "Cannot parse date string: " + paymentDate, module);
            authDate = UtilDateTime.nowTimestamp();
        }

        String gatewayFlag = "S";
        if (paymentAmount != null) {
            paymentPreference.set("maxAmount", new BigDecimal(paymentAmount));
        } else {
            paymentAmount = request.getSession().getAttribute("_ALIPAY_PRICE") == null ? "0" : ((String) request.getSession().getAttribute("_ALIPAY_PRICE"));
            paymentPreference.set("maxAmount", new BigDecimal(paymentAmount));
        }
        if (paymentStatus.equals("TRADE_SUCCESS") || paymentStatus.equals("TRADE_FINISH")) {
            paymentPreference.set("statusId", "PAYMENT_RECEIVED");
        } /*else if (paymentStatus.equals("TRAD")) {
            paymentPreference.set("statusId", "PAYMENT_NOT_RECEIVED");
        }*/ else {
            gatewayFlag = "F";
            paymentPreference.set("statusId", "PAYMENT_NOT_RECEIVED");
        }
        paymentPreference.set("paymentMethodTypeId", "EXT_ALIPAY");
        toStore.add(paymentPreference);


        Delegator delegator = paymentPreference.getDelegator();

        // create the PaymentGatewayResponse
        String responseId = delegator.getNextSeqId("PaymentGatewayResponse");
        GenericValue response = delegator.makeValue("PaymentGatewayResponse");
        response.set("paymentGatewayResponseId", responseId);
        response.set("paymentServiceTypeEnumId", "PRDS_PAY_EXTERNAL");
        response.set("orderPaymentPreferenceId", paymentPreference.get("orderPaymentPreferenceId"));
        response.set("paymentMethodTypeId", paymentPreference.get("paymentMethodTypeId"));
        response.set("paymentMethodId", paymentPreference.get("paymentMethodId"));

        // set the auth info
        response.set("amount", new BigDecimal(paymentAmount));
        response.set("referenceNum", transactionId);
        response.set("gatewayCode", paymentStatus);
        response.set("gatewayFlag", gatewayFlag);
        response.set("gatewayMessage", AlipayConfig.payment_type);
        response.set("transactionDate", authDate);
        toStore.add(response);

        try {
            delegator.storeAll(toStore);
        } catch (GenericEntityException e) {
            Debug.logError(e, "Cannot set payment preference/payment info", module);
            return false;
        }

        // create a payment record too
        Map<String, Object> results = null;
        try {
            String comment = UtilProperties.getMessage(resource, "AccountingPaymentReceiveViaPayPal", locale);
            results = dispatcher.runSync("createPaymentFromPreference", UtilMisc.toMap("userLogin", userLogin,
                    "orderPaymentPreferenceId", paymentPreference.get("orderPaymentPreferenceId"), "comments", comment));
        } catch (GenericServiceException e) {
            Debug.logError(e, "Failed to execute service createPaymentFromPreference", module);
            request.setAttribute("_ERROR_MESSAGE_", UtilProperties.getMessage(resourceErr, "payPalEvents.failedToExecuteServiceCreatePaymentFromPreference", locale));
            return false;
        }

        if ((results == null) || (results.get(ModelService.RESPONSE_MESSAGE).equals(ModelService.RESPOND_ERROR))) {
            Debug.logError((String) results.get(ModelService.ERROR_MESSAGE), module);
            request.setAttribute("_ERROR_MESSAGE_", results.get(ModelService.ERROR_MESSAGE));
            return false;
        }

        return true;
    }

    private static String getPaymentGatewayConfigValue(Delegator delegator, String paymentGatewayConfigId, String paymentGatewayConfigParameterName,
                                                       String resource, String parameterName) {
        String returnValue = "";
        if (UtilValidate.isNotEmpty(paymentGatewayConfigId)) {
            try {
                GenericValue alipay = delegator.findOne("PaymentGatewayAlipay", UtilMisc.toMap("paymentGatewayConfigId", paymentGatewayConfigId), false);
                if (UtilValidate.isNotEmpty(alipay)) {
                    Object alipayField = alipay.get(paymentGatewayConfigParameterName);
                    if (alipayField != null) {
                        returnValue = alipayField.toString().trim();
                    }
                }
            } catch (GenericEntityException e) {
                Debug.logError(e, module);
            }
        } else {
            String value = UtilProperties.getPropertyValue(resource, parameterName);
            if (value != null) {
                returnValue = value.trim();
            }
        }
        return returnValue;
    }
    
    /**
     * Initiate AliPay WAP Request
     */
    public static Map<String,String> callWapAlipayForDaoJia(String orderId,Locale locale, Delegator delegator,GenericValue productStore,String returnUrl,String notifyUrl) throws Exception {
        // get the order header
        GenericValue orderHeader = null;
        try {
            orderHeader = delegator.findByPrimaryKey("OrderHeader", UtilMisc.toMap("orderId", orderId));
        } catch (GenericEntityException e) {
            Debug.logError(e, "Cannot get the order header for order: " + orderId, module);
             
        }
        // get the order total
        String price = orderHeader.getBigDecimal("grandTotal").toPlainString();
        
        String subject = (String) orderHeader.get("orderName");
      
        if (productStore == null) {
            Debug.logError("ProductStore is null", module);
               
        }
        
        // get the payment properties file
        // 设置alipay的
        GenericValue paymentConfig = ProductStoreWorker.getProductStorePaymentSetting(delegator, productStore.getString("productStoreId"), "EXT_ALIPAY", null, true);
        String configString = null;
        String paymentGatewayConfigId = null;
        if (paymentConfig != null) {
            paymentGatewayConfigId = paymentConfig.getString("paymentGatewayConfigId");
            configString = paymentConfig.getString("paymentPropertiesPath");
        }
        
        if (configString == null) {
            configString = "payment.properties";
        }
        
        // get the company name
        String company = UtilFormatOut.checkEmpty(productStore.getString("companyName"), "");
        
        // create the item name
        String itemName = UtilProperties.getMessage(resource, "AccountingOrderNr", locale) + orderId + " " +
                (company != null ? UtilProperties.getMessage(commonResource, "CommonFrom", locale) + " " + company : "");
//        itemName="test";
        // get the redirect url
        String redirectUrl = getPaymentGatewayConfigValue(delegator, paymentGatewayConfigId, "wapRedirectUrl", configString, "payment.alipay.wap.redirect");
        
        
        //wap 异步通知不使用，
//        notifyUrl = "";
        
        //操作中断返回地址
        String merchant_url = getPaymentGatewayConfigValue(delegator, paymentGatewayConfigId, "wapMerchantUrl", configString, "payment.alipay.wap.merchant");
        //用户付款中途退出返回商户的地址。需http://格式的完整路径，不允许加?id=123这类自定义参数
        
        
        // get the cancel return urls
        String cancelReturnUrl = getPaymentGatewayConfigValue(delegator, paymentGatewayConfigId, "wapCancelReturnUrl", configString, "payment.alipay.wap.cancelReturn");
        
        // get the paypal account
        String aliPayAccount = getPaymentGatewayConfigValue(delegator, paymentGatewayConfigId, "sellerEmail", configString, "payment.alipay.business");
        
        String interfaceType = getPaymentGatewayConfigValue(delegator, paymentGatewayConfigId, "interfaceType", configString, "payment.alipay.wap.interface.type");
        String interfaceName = getPaymentGatewayConfigValue(delegator, paymentGatewayConfigId, "wapInterfaceName", configString, "payment.alipay.wap.interface.name");
        String pubwapKey = getPaymentGatewayConfigValue(delegator, paymentGatewayConfigId, "wapPublicKey", configString, "payment.alipay.wap.public.key");
        AlipayConfig.alipay_wap_public_key = pubwapKey;
        String privatewapKey = getPaymentGatewayConfigValue(delegator, paymentGatewayConfigId, "wapPrivateKey", configString, "payment.alipay.wap.private.key");
        AlipayConfig.wap_private_key = privatewapKey;
        
        
        // 判断接口类型是双功能还是及时到账接口
        
        String partner = getPaymentGatewayConfigValue(delegator, paymentGatewayConfigId, "partner", configString, "payment.alipay.partner");
        AlipayConfig.partner = partner;
        
        
        if (UtilValidate.isEmpty(redirectUrl)
               /* || UtilValidate.isEmpty(notifyUrl)*/
                || UtilValidate.isEmpty(returnUrl)
                || UtilValidate.isEmpty(aliPayAccount)) {
            Debug.logError("Payment properties is not configured properly, some notify URL from Alipay is not correctly defined!", module);
            
        }
        String curDate = UtilDate.getOrderNum();
        String out_trade_no = orderId + "_" + curDate;
    
    
        //把请求参数打包成数组
        Map<String, String> sParaTemp = new HashMap<String, String>();
    
    
        sParaTemp.put("service", interfaceName);
        sParaTemp.put("partner", partner);
        sParaTemp.put("seller_id", partner);
        sParaTemp.put("_input_charset", AlipayConfig.input_charset);
        sParaTemp.put("payment_type", AlipayConfig.payment_type);
        sParaTemp.put("notify_url", notifyUrl);
        sParaTemp.put("return_url", returnUrl);
        sParaTemp.put("out_trade_no", out_trade_no);
        sParaTemp.put("subject", itemName);
        sParaTemp.put("total_fee", price);
        sParaTemp.put("show_url", merchant_url);
        sParaTemp.put("body", itemName);
        sParaTemp.put("app_pay","");
        sParaTemp.put("rn_check","");
        sParaTemp.put("goods_type","");
        sParaTemp.put("it_b_pay","");
        
        Map<String, String> sPara = AlipaySubmit.buildRequestPara(sParaTemp, "WAP",privatewapKey);
    
    
    
        sPara.put("action",redirectUrl);
        sPara.put("method","get");
        return sPara;
    }
}
