package org.ofbiz.accounting.thirdparty.weixin;


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
import java.net.URLEncoder;
import java.text.SimpleDateFormat;
import java.util.LinkedList;
import java.util.List;
import java.util.Locale;
import java.util.Map;

/**
 * Created by csy on 2014/11/2.
 */
public class WeixinEvents {

    public static final String resource = "AccountingUiLabels";
    public static final String resourceErr = "AccountingErrorUiLabels";
    public static final String commonResource = "CommonUiLabels";
    public static final String module = WeixinEvents.class.getName();

    public static String authWeixinPay(HttpServletRequest request,HttpServletResponse response) throws IOException {
        Delegator delegator = (Delegator) request.getAttribute("delegator");
        Locale locale = request.getLocale();
        String orderId = request.getParameter("orderId");
        if(orderId==null) {
            orderId = (String) request.getAttribute("orderId");
        }
        //out_trade_no 64字符，为了保证唯一性orderId+timestamp
      //
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
        //IF user enter amount
        if(price!=null && (!price.equals(""))){
            orderTotal = price;
            //因为是用户输入金额
        }
        // get the product store
        GenericValue productStore = ProductStoreWorker.getProductStore(request);

        // get the payment properties file
        // 设置weixin的
        GenericValue paymentConfig = ProductStoreWorker.getProductStorePaymentSetting(delegator, productStore.getString("productStoreId"), "EXT_WEIXIN", null, true);
        String configString = null;
        String paymentGatewayConfigId = null;
        if (paymentConfig != null) {
            paymentGatewayConfigId = paymentConfig.getString("paymentGatewayConfigId");
            configString = paymentConfig.getString("paymentPropertiesPath");
        }
        if (configString == null) {
            configString = "payment.properties";
        }
        String company = UtilFormatOut.checkEmpty(productStore.getString("companyName"), "");
        String itemName = UtilProperties.getMessage(resource, "AccountingOrderNr", locale) + orderId + " " +
                (company != null ? UtilProperties.getMessage(commonResource, "CommonFrom", locale) + " "+ company : "");
        //共账号及商户相关参数
        String appId  = getPaymentGatewayConfigValue(delegator, paymentGatewayConfigId, "appId", configString, "payment.weixin.appId");
        String payUrl = getPaymentGatewayConfigValue(delegator, paymentGatewayConfigId, "payUrl", configString, "payment.weixin.payUrl");
        //授权后要跳转的链接所需的参数一般有会员号，金额，订单号之类，
        //最好自己带上一个加密字符串将金额加上一个自定义的key用MD5签名或者自己写的签名,
        //比如 Sign = %3D%2F%CS%
        payUrl = payUrl+"?orderId="+orderId+"&price="+orderTotal;
        //URLEncoder.encode 后可以在backUri 的url里面获取传递的所有参数
        payUrl = URLEncoder.encode(payUrl);
        //scope 参数视各自需求而定，这里用scope=snsapi_base 不弹出授权页面直接授权目的只获取统一支付接口的openid
        String url = "https://open.weixin.qq.com/connect/oauth2/authorize?" +
                "appid=" + appId+
                "&redirect_uri=" +
                 payUrl+
                "&response_type=code&scope=snsapi_userinfo&state=123#wechat_redirect";
        response.sendRedirect(url);
        return "success";
    }


    /** WeiXin Call-Back Event  目前只用于已步返回的情况 */
    public static String weiXinCallBack(HttpServletRequest request, HttpServletResponse response) throws UnsupportedEncodingException {
        Locale locale = UtilHttp.getLocale(request);
        Delegator delegator = (Delegator) request.getAttribute("delegator");
        LocalDispatcher dispatcher = (LocalDispatcher) request.getAttribute("dispatcher");

        // get the product store
        GenericValue productStore = ProductStoreWorker.getProductStore(request);
        if (productStore == null) {
            Debug.logError("ProductStore is null", module);
            request.setAttribute("_ERROR_MESSAGE_", UtilProperties.getMessage(resourceErr, "weiXinEvents.problemsGettingMerchantConfiguration", locale));
            return "error";
        }

        // get the system user
        GenericValue userLogin = null;
        try {
            userLogin = delegator.findByPrimaryKey("UserLogin", UtilMisc.toMap("userLoginId", "system"));
        } catch (GenericEntityException e) {
            Debug.logError(e, "Cannot get UserLogin for: system; cannot continue", module);
            request.setAttribute("_ERROR_MESSAGE_", UtilProperties.getMessage(resourceErr, "weiXinEvents.problemsGettingAuthenticationUser", locale));
            return "error";
        }
        WxPayResponseHandler resHandler = new WxPayResponseHandler(request, response);
        String orderId = resHandler.getParameter("out_trade_no");
        orderId = orderId.substring(0,orderId.indexOf("_"));
        // get the order header
        GenericValue orderHeader = null;
        if (UtilValidate.isNotEmpty(orderId)) {
            try {
                orderHeader = delegator.findByPrimaryKey("OrderHeader", UtilMisc.toMap("orderId", orderId));
            } catch (GenericEntityException e) {
                Debug.logError(e, "Cannot get the order header for order: " + orderId, module);
                request.setAttribute("_ERROR_MESSAGE_", UtilProperties.getMessage(resourceErr, "weiXinEvents.problemsGettingOrderHeader", locale));
                return "error";
            }
        } else {
            Debug.logError("WeiXin did not callback with a valid orderId!", module);
            request.setAttribute("_ERROR_MESSAGE_", UtilProperties.getMessage(resourceErr, "weiXinEvents.noValidOrderIdReturned", locale));
            return "error";
        }

        if (orderHeader == null) {
            Debug.logError("Cannot get the order header for order: " + orderId, module);
            request.setAttribute("_ERROR_MESSAGE_", UtilProperties.getMessage(resourceErr, "weiXinEvents.problemsGettingOrderHeader", locale));
            return "error";
        }
        // 设置weixin的
        GenericValue paymentConfig = ProductStoreWorker.getProductStorePaymentSetting(delegator, productStore.getString("productStoreId"), "EXT_WEIXIN", null, true);
        String configString = null;
        String paymentGatewayConfigId = null;
        if (paymentConfig != null) {
            paymentGatewayConfigId = paymentConfig.getString("paymentGatewayConfigId");
            configString = paymentConfig.getString("paymentPropertiesPath");        }

        if (configString == null) {
            configString = "payment.properties";
        }

        String appkey = getPaymentGatewayConfigValue(delegator, paymentGatewayConfigId, "appkey", configString, "payment.weixin.appkey");
        resHandler.setKey(appkey);
        //创建请求对象

        boolean okay = false;
        if (resHandler.isValidSign() == true) {
            boolean beganTransaction = false;
            try {
//                if (resHandler.isWXsign() == true) {
                    //商户订单号
                    //财付通订单号
                    String transaction_id = resHandler.getParameter("transaction_id");
                    //金额,以分为单位
                    String total_fee = resHandler.getParameter("total_fee");
                    //如果有使用折扣券，discount有值，total_fee+discount=原请求的total_fee
                    String discount = resHandler.getParameter("discount");
                    //支付结果
                    String trade_state = resHandler.getParameter("result_code");
                    //判断签名及结果
                    beganTransaction = TransactionUtil.begin();

                    if ("SUCCESS".equals(trade_state)) {
                        //判断该笔订单是否在商户网站中已经做过处理
                        //如果没有做过处理，根据订单号（out_trade_no）在商户网站的订单系统中查到该笔订单的详细，并执行商户的业务程序
                        //如果有做过处理，不执行商户的业务程序
                        okay = OrderChangeHelper.completeOrder(dispatcher, userLogin, orderId);
                        //注意：
                        //该种交易状态只在两种情况下出现
                        //1、开通了普通即时到账，买家付款成功后。
                        //2、开通了高级即时到账，从该笔交易成功时间算起，过了签约时的可退款时限（如：三个月以内可退款、一年以内可退款等）后。
                    } else {
                        //是否还有其他的情况？？？？
//                        okay = OrderChangeHelper.cancelOrder(dispatcher, userLogin, orderId);
                    }

                    if (okay) {
                        // set the payment preference
                        okay = setPaymentPreferences(delegator, dispatcher, userLogin, orderId, transaction_id,total_fee,discount,trade_state,request);
                        resHandler.sendToCFT("success");
                    }
                    //给财付通系统发送成功信息，财付通系统收到此结果后不再进行后续通知


            /*}else{//sha1签名失败
                Debug.logError("fail Weixin -SHA1 failed",module);
                resHandler.sendToCFT("fail");
            }*/
            }catch (Exception e) {
                String errMsg = "Error handling WeiXin notification";
                Debug.logError(e, errMsg, module);
                try {
                    TransactionUtil.rollback(beganTransaction, errMsg, e);
                } catch (GenericTransactionException gte2) {
                    Debug.logError(gte2, "Unable to rollback transaction", module);
                }
            } finally {
                if (!okay) {
                    try {
                        TransactionUtil.rollback(beganTransaction, "Failure in processing WeiXin callback", null);
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
        }else {//MD5签名失败
            Debug.logError("Problems Weixin -Md5 failed", module);
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
        return "success";
    }

    /** Event called when customer cancels a paypal order */
    public static String cancellWeixinOrder(HttpServletRequest request, HttpServletResponse response) {
        Locale locale = UtilHttp.getLocale(request);
        LocalDispatcher dispatcher = (LocalDispatcher) request.getAttribute("dispatcher");
        GenericValue userLogin = (GenericValue) request.getSession().getAttribute("userLogin");

        // get the stored order id from the session
        String orderId = (String) request.getSession().getAttribute("WEIXIN_ORDER");

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
                TransactionUtil.rollback(beganTransaction, "Failure in processing WeiXin cancel callback", null);
            } catch (GenericTransactionException gte) {
                Debug.logError(gte, "Unable to rollback transaction", module);
            }
        }

        // attempt to release the offline hold on the order (workflow)
        if (okay)
            OrderChangeHelper.releaseInitialOrderHold(dispatcher, orderId);

        request.setAttribute("_EVENT_MESSAGE_", UtilProperties.getMessage(resourceErr, "weiXinEvents.previousWeiXinOrderHasBeenCancelled", locale));
        return "success";
    }

    private static boolean setPaymentPreferences(Delegator delegator, LocalDispatcher dispatcher, GenericValue userLogin, String orderId, String transactionId,String totalFee,String discount,String status,HttpServletRequest request) {
        Debug.logVerbose("Setting payment prefrences..", module);
        List<GenericValue> paymentPrefs = null;
        try {
            Map <String, String> paymentFields = UtilMisc.toMap("orderId", orderId, "statusId", "PAYMENT_NOT_RECEIVED");
            paymentPrefs = delegator.findByAnd("OrderPaymentPreference", paymentFields);
        } catch (GenericEntityException e) {
            Debug.logError(e, "Cannot get payment preferences for order #" + orderId, module);
            return false;
        }
        if (paymentPrefs.size() > 0) {
            for(GenericValue pref : paymentPrefs) {
                boolean okay = setPaymentPreference(dispatcher, userLogin, pref, orderId,transactionId,totalFee,discount,status,request);
                if (!okay)
                    return false;
            }
        }
        return true;
    }


    private static boolean setPaymentPreference(LocalDispatcher dispatcher, GenericValue userLogin, GenericValue paymentPreference, String orderId,String transactionId,String totalFee,String discount,String status,HttpServletRequest request) {
        Locale locale = UtilHttp.getLocale(request);
        List <GenericValue> toStore = new LinkedList <GenericValue> ();

        // WeiXin returns the timestamp in the format 'hh:mm:ss Jan 1, 2000 PST'
        // Parse this into a valid Timestamp Object
        SimpleDateFormat sdf = new SimpleDateFormat("hh:mm:ss MMM d, yyyy z");
        java.sql.Timestamp authDate = UtilDateTime.nowTimestamp();

        paymentPreference.set("maxAmount", new BigDecimal(((Double)(new BigDecimal(totalFee).doubleValue() / 100)).toString()));

        if (status.equalsIgnoreCase("success")) {
            paymentPreference.set("statusId", "PAYMENT_RECEIVED");
        } else if (status.equals("Pending")) {
            paymentPreference.set("statusId", "PAYMENT_NOT_RECEIVED");
        } else {
            paymentPreference.set("statusId", "PAYMENT_RECEIVED");
        }
        paymentPreference.set("paymentMethodTypeId","EXT_WEIXIN");
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
        response.set("amount", new BigDecimal(((Double)(new BigDecimal(totalFee).doubleValue() / 100)).toString()));
        response.set("referenceNum", transactionId);
        response.set("gatewayCode", status);
        response.set("gatewayFlag", status.substring(0,1));
        response.set("gatewayMessage", status);
        response.set("transactionDate", authDate);
        toStore.add(response);

        try {
            delegator.storeAll(toStore);
        } catch (GenericEntityException e) {
            Debug.logError(e, "Cannot set payment preference/payment info", module);
            return false;
        }

        // create a payment record too
        Map <String, Object> results = null;
        try {
            String comment = UtilProperties.getMessage(resource, "AccountingPaymentReceiveViaWeiXin", locale);
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
                GenericValue weixin = delegator.findOne("PaymentGatewayWeixin", UtilMisc.toMap("paymentGatewayConfigId", paymentGatewayConfigId), false);
                if (UtilValidate.isNotEmpty(weixin)) {
                    Object weixinField = weixin.get(paymentGatewayConfigParameterName);
                    if (weixinField != null) {
                        returnValue = weixinField.toString().trim();
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
}
