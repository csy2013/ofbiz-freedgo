package com.yuaoq.yabiz.daojia.service.order;

import com.google.gson.Gson;
import com.google.gson.internal.StringMap;
import com.yuaoq.yabiz.daojia.model.json.order.CashierPageInfo;
import com.yuaoq.yabiz.daojia.model.json.order.OrderProductInventory;
import com.yuaoq.yabiz.daojia.model.json.order.OrderSubmit;
import com.yuaoq.yabiz.daojia.model.json.order.PayWays;
import com.yuaoq.yabiz.daojia.model.json.order.orderComment.OrderComment;
import com.yuaoq.yabiz.daojia.model.json.order.orderinfo.*;
import com.yuaoq.yabiz.daojia.model.json.order.orderlist.MainOrderStateMap;
import com.yuaoq.yabiz.daojia.model.json.order.orderlist.Order;
import com.yuaoq.yabiz.daojia.model.json.order.orderlist.OrderStateMap;
import com.yuaoq.yabiz.daojia.model.json.order.orderlist.ProductList;
import com.yuaoq.yabiz.daojia.model.json.order.orderstate.OrderStateList;
import com.yuaoq.yabiz.daojia.model.json.order.productComment.ProductComment;
import com.yuaoq.yabiz.eshop.shoppingcart.CheckOutEvents;
import com.yuaoq.yabiz.eshop.shoppingcart.CheckOutHelper;
import com.yuaoq.yabiz.eshop.shoppingcart.ShoppingCartEvents;
import javolution.util.FastList;
import javolution.util.FastMap;
import org.ofbiz.accounting.thirdparty.alipay.AlipayEvents;
import org.ofbiz.base.crypto.HashCrypt;
import org.ofbiz.base.util.*;
import org.ofbiz.common.login.LoginServices;
import org.ofbiz.entity.Delegator;
import org.ofbiz.entity.GenericEntityException;
import org.ofbiz.entity.GenericValue;
import org.ofbiz.entity.condition.EntityCondition;
import org.ofbiz.entity.condition.EntityOperator;
import org.ofbiz.entity.util.EntityUtil;
import org.ofbiz.order.order.OrderReadHelper;
import org.ofbiz.order.shoppingcart.ShoppingCart;
import org.ofbiz.order.shoppingcart.ShoppingCartHelper;
import org.ofbiz.product.product.ProductContentWrapper;
import org.ofbiz.service.DispatchContext;
import org.ofbiz.service.GenericServiceException;
import org.ofbiz.service.LocalDispatcher;
import org.ofbiz.service.ServiceUtil;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.IOException;
import java.math.BigDecimal;
import java.util.*;

/**
 * Created by changsy on 16/9/19.
 */
public class OrderService {
    public static String DaoJia_SubmitOrder(HttpServletRequest request, HttpServletResponse response) {
        Locale locale = (Locale) request.getAttribute("locale");
        if (locale == null) locale = Locale.CHINA;
        Delegator delegator = (Delegator) request.getAttribute("delegator");
        OrderSubmit orderSubmit = new OrderSubmit("order.submitOrder24", "0", UtilProperties.getMessage("CommonUiLabels", "CommonSuccess", locale), true);
        String ret = CheckOutEvents.createOrder(request, response);
        if (ret.equals("sales_order")) {
            String orderId = (String) request.getAttribute("orderId");
            try {
                GenericValue orderHeader = delegator.findByPrimaryKey("OrderHeader", UtilMisc.toMap("orderId", orderId));
                
                OrderSubmit.Result orderResult = new OrderSubmit.Result();
                orderResult.orderId = orderId;
                
                orderResult.orderDate = UtilFormatOut.formatDate(orderHeader.getTimestamp("orderDate"), "yyyy-MM-dd hh:mm:ss", locale, TimeZone.getDefault());
                orderResult.orderPrice = (orderHeader.getBigDecimal("grandTotal").multiply(new BigDecimal(100))).intValue();
                orderResult.orderStateName = "在线支付";
                orderResult.deliveryTime = UtilFormatOut.formatDate(orderHeader.getTimestamp("orderDate"), "yyyy-MM-dd hh:mm:ss", locale, TimeZone.getDefault());
                orderResult.savedAddr = true;
                orderSubmit.result = orderResult;
            } catch (GenericEntityException e) {
                e.printStackTrace();
            }
        }
        ShoppingCartEvents.clearCart(request, response);
        request.setAttribute("resultData", orderSubmit);
        
        return "success";
    }
    
    
    public static String DaoJia_WebPayGraySwitch(HttpServletRequest request, HttpServletResponse response) {
        String homePath = System.getProperty("ofbiz.home");
        String json = null;
        try {
            json = FileUtil.readString("utf-8", new File(homePath + "/daojia/daojia/config/order/webPayGraySwitch.json"));
        } catch (IOException e) {
            e.printStackTrace();
        }
        Map<String, Object> webPayGraySwitch = new Gson().fromJson(json, Map.class);
        request.setAttribute("resultData", webPayGraySwitch);
        
        return "success";
    }
    
    /**
     * {"ref":"storeHome/skuId:10002/storeId:10000/orgCode:CN-110101/fromAnchor:true","tradeName":"红富士苹果约950±50g/份","merchantOrderNum":"WSOD10125",
     * "paySuccessUrl":"https://changsy.cn/daojia/www/index.html#orderopresult/result:success/payType:pay/orderId:WSOD10125",
     * "appId":1,"source":2,"paySource":3,"os":"OS04","osVersion":"","deviceType":"DT01","openId":""}
     *
     * @return desc: 根据用户提供的订单信息，生成payToken信息。
     */
    public static Map<String, Object> DaoJia_WebPaySign(DispatchContext dcx, Map<String, ? extends Object> context) {
        Map<String, Object> result = ServiceUtil.returnSuccess();
        String body = (String) context.get("body");
        Delegator delegator = dcx.getDelegator();
        Map bodyMap = new Gson().fromJson(body, Map.class);
        Locale locale = (Locale) context.get("locale");
        String ref = (String) bodyMap.get("ref");
        String tradeName = (String) bodyMap.get("tradeName");
        String merchantOrderNum = (String) bodyMap.get("merchantOrderNum");
        String paySuccessUrl = (String) bodyMap.get("paySuccessUrl");
        String appId = String.valueOf(((Double) bodyMap.get("appId")).intValue());
        String sourceId = String.valueOf(((Double) bodyMap.get("source")).intValue());
        String paySource = String.valueOf(((Double) bodyMap.get("paySource")).intValue());
        String os = (String) bodyMap.get("os");
        String osVersion = (String) bodyMap.get("osVersion");
        String deviceType = (String) bodyMap.get("deviceType");
        String openId = (String) bodyMap.get("openId");
        String tokenStr = HashCrypt.cryptPassword(LoginServices.getHashType(), merchantOrderNum);
        GenericValue payToken = delegator.makeValue("OrderPayToken");
        payToken.put("ref", ref);
        payToken.put("tradeName", tradeName);
        payToken.put("merchantOrderNum", merchantOrderNum);
        payToken.put("paySuccessUrl", paySuccessUrl);
        payToken.put("appId", appId);
        payToken.put("source", sourceId);
        payToken.put("paySource", paySource);
        payToken.put("os", os);
        payToken.put("osVersion", osVersion);
        payToken.put("deviceType", deviceType);
        payToken.put("openId", openId);
        payToken.setNextSeqId();
        payToken.put("payToken", tokenStr);
        try {
            payToken.create();
        } catch (GenericEntityException e) {
            e.printStackTrace();
        }
        Map retMap = FastMap.newInstance();
        retMap.put("id", "webpay.sign");
        retMap.put("code", "0");
        retMap.put("msg", UtilProperties.getMessage("CommonUiLabels", "CommonSuccess", locale));
        retMap.put("result", "index.html?payToken=" + tokenStr + "&from=daojia#newPay");
        retMap.put("success", true);
        result.put("resultData", retMap);
        return result;
    }
    
    /**
     * "token":"$SHA$qp2sWqx$hXKeneWCXVb9XldkHxKy9SuI4Ac","paySource":3
     *
     * @param dcx
     * @param context
     * @return
     */
    public static Map<String, Object> DaoJia_CashierPageInfo(DispatchContext dcx, Map<String, ? extends Object> context) {
        Locale locale = (Locale) context.get("locale");
        Delegator delegator = dcx.getDelegator();
        Map<String, Object> result = ServiceUtil.returnSuccess();
        String body = (String) context.get("body");
        Map<String, Object> bodyMap = new Gson().fromJson(body, Map.class);
        String token = (String) bodyMap.get("token");
        String paySource = String.valueOf(((Double) bodyMap.get("paySource")).intValue());
        CashierPageInfo pageInfo = new CashierPageInfo("cashier.initCashierPageInfo", "0", UtilProperties.getMessage("CommonUiLabels", "CommonSuccess", locale), true);
        CashierPageInfo.PayWaysResult payWaysResult = new CashierPageInfo.PayWaysResult();
        try {
            GenericValue orderPayToken = EntityUtil.getFirst(delegator.findByAnd("OrderPayToken", UtilMisc.toMap("payToken", token)));
            if (UtilValidate.isNotEmpty(orderPayToken)) {
                String orderId = orderPayToken.getString("merchantOrderNum");
                OrderReadHelper orderReadHelper = new OrderReadHelper(delegator, orderId);
                payWaysResult.setAmount(orderReadHelper.getOrderGrandTotal().setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue());
                payWaysResult.setNotifyUrl("./index.html#orderopresult/result:success/payType:pay/orderId:" + orderId);
                payWaysResult.setCountDownTime("647919");
                
                List<PayWays> payWaysList = FastList.newInstance();
                PayWays payWays = new PayWays();
                payWays.setPayWay("10");
                payWays.setPayName("微信支付");
                payWays.setStatus("0");
                payWays.setDefaultCopy("推荐安装微信5.0及以上版本使用");
                payWays.setIconUrl("https://changsy.cn/images/icon/wxpay-icon.png");
                payWaysList.add(payWays);
                
                PayWays payWays1 = new PayWays();
                payWays1.setPayWay("40");
                payWays1.setPayName("支付宝");
                payWays1.setStatus("0");
                payWays1.setDefaultCopy("阿凡提旗下 快捷支付");
                payWays1.setIconUrl("https://changsy.cn/images/icon/alipay.png");
                payWaysList.add(payWays1);
                
                payWaysResult.setPayWays(payWaysList);
                pageInfo.result = payWaysResult;
            }
        } catch (GenericEntityException e) {
            e.printStackTrace();
        }
        result.put("resultData", pageInfo);
        return result;
    }
    
    /**
     * "body":"{'token':'$SHA$rjCppnl20Jx55q$PPtONi476x_WkSL-PqiI3Ar3ars'}"
     *
     * @param dcx
     * @param context
     * @return
     */
    public static Map<String, Object> DaoJia_CashierAliPay(DispatchContext dcx, Map<String, ? extends Object> context) {
        Locale locale = (Locale) context.get("locale");
        Delegator delegator = dcx.getDelegator();
        Map<String, Object> result = ServiceUtil.returnSuccess();
        String body = (String) context.get("body");
        Map<String, Object> bodyMap = new Gson().fromJson(body, Map.class);
        String token = (String) bodyMap.get("token");
        GenericValue userLogin = (GenericValue)context.get("userLogin");
        LocalDispatcher dispatcher = dcx.getDispatcher();
        Map<String, Object> retMap = FastMap.newInstance();
        retMap.put("id", "cashier.aliPay");
        retMap.put("msg", UtilProperties.getMessage("CommonUiLabels", "CommonSuccess", locale));
        retMap.put("success", true);
        retMap.put("code", "0");
        Map<String, String> alipayMap = FastMap.newInstance();
        try {
            GenericValue orderPayToken = EntityUtil.getFirst(delegator.findByAnd("OrderPayToken", UtilMisc.toMap("payToken", token)));
            if (UtilValidate.isNotEmpty(orderPayToken)) {
                String orderId = orderPayToken.getString("merchantOrderNum");
                
                OrderReadHelper readHelper = new OrderReadHelper(delegator, orderId);
                GenericValue productStore = readHelper.getProductStore();
                //生成payment
                GenericValue opp = delegator.makeValue("OrderPaymentPreference");
                opp.set("orderId", orderId);
                opp.set("paymentMethodTypeId", "EXT_ALIPAY");
                opp.set("statusId", "PAYMENT_NOT_RECEIVED");
                opp.set("maxAmount", readHelper.getOrderGrandTotal());
                opp.set("createdDate", UtilDateTime.nowTimestamp());
                if (userLogin != null) {
                    opp.set("createdByUserLogin", userLogin.getString("userLoginId"));
                }
                opp.setNextSeqId();
//                delegator.createSetNextSeqId(opp);
                opp.create();
                
                String returnUrl = orderPayToken.getString("paySuccessUrl");
                String notifyUrl = UtilProperties.getMessage("payment.properties","payment.alipay.wap.notify",locale);
                  returnUrl = notifyUrl;
                alipayMap = AlipayEvents.callWapAlipayForDaoJia(orderId, locale, delegator, productStore, returnUrl, notifyUrl);
                retMap.put("result", alipayMap);
            }
        } catch (GenericEntityException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }
        result.put("resultData", retMap);
        return result;
    }
    
    public static Map<String, Object> DaoJia_OrderIsExistsComment(DispatchContext dcx, Map<String, ? extends Object> context) {
        Map<String, Object> result = ServiceUtil.returnSuccess();
        Map<String, Object> retMap = FastMap.newInstance();
        Locale locale = (Locale) context.get("locale");
        retMap.put("id", "order.isExistsComment");
        retMap.put("msg", UtilProperties.getMessage("CommonUiLabels", "CommmonSuccess", locale));
        retMap.put("result", true);
        retMap.put("success", true);
        retMap.put("code", "0");
        result.put("resultData", retMap);
        return result;
    }
    
    public static Map<String, Object> DaoJia_OrderList(DispatchContext dcx, Map<String, ? extends Object> context) {
        Map<String, Object> result = ServiceUtil.returnSuccess();
        //{'startIndex':0,'dataSize':10}
        String body = (String) context.get("body");
        Map bodyMap = new Gson().fromJson(body, Map.class);
        GenericValue userLogin = (GenericValue) context.get("userLogin");
        Integer index = ((Double) bodyMap.get("startIndex")).intValue();
        Integer size = ((Double) bodyMap.get("dataSize")).intValue();
        Locale locale = (Locale) context.get("locale");
        java.text.DecimalFormat df = new java.text.DecimalFormat("0.00");
        
        String userLoginId = userLogin.getString("userLoginId");
        //获得party order列表，按照时间倒序
        LocalDispatcher dispatcher = dcx.getDispatcher();
        Delegator delegator = dcx.getDelegator();
        Map serviceIn = FastMap.newInstance();
        serviceIn.put("partyId", userLogin.getString("partyId"));
        serviceIn.put("viewIndex", index / size);
        serviceIn.put("viewSize", size);
        serviceIn.put("orderTypeId", UtilMisc.toList("SALES_ORDER"));
        serviceIn.put("userLogin", userLogin);
        Map retMap = FastMap.newInstance();
        List orders = FastList.newInstance();
        retMap.put("id", "order.list");
        retMap.put("msg", UtilProperties.getMessage("CommonUiLabels", "CommonSuccess", locale));
        retMap.put("success", true);
        try {
            Map<String, Map> storesMap = FastMap.newInstance();
            Map orderRet = dispatcher.runSync("findOrders", serviceIn);

//            return orderList(orderId,orderName,statusId,orderTypeId,orderDate,currencyUom,grandTotal,remainingSubTotal) orderListSize
            if (ServiceUtil.isSuccess(orderRet)) {
                List orderList = (List) orderRet.get("orderList");
                if (UtilValidate.isNotEmpty(orderList)) {
                    for (int i = 0; i < orderList.size(); i++) {
                        GenericValue order = (GenericValue) orderList.get(i);
                        String orderId = order.getString("orderId");
                        
                        Map orderMap = FastMap.newInstance();
                        orderMap.put("orderId", order.getString("orderId"));
                        orderMap.put("queryType", UtilMisc.toMap("showCurrentOrderStatus", "Y", "showOrderItems", "Y", "showOrderHeader", "Y", "showOrderStatusList", "Y", "showOrderReviews", "Y", "showOrderReviews", "Y"));
                        Map orderInfoRet = dispatcher.runSync("DaoJia_OrderInfo", orderMap);
                        GenericValue orderHeader = (GenericValue) orderInfoRet.get("orderHeader");
                        String productStoreId = (String) orderHeader.get("productStoreId");
                        if (ServiceUtil.isSuccess(orderInfoRet)) {
                            Map storeMap = FastMap.newInstance();
                            if (UtilValidate.isNotEmpty(storesMap.get(productStoreId))) {
                                storeMap = storesMap.get(productStoreId);
                            } else {
                                storeMap = dispatcher.runSync("DaoJia_StoreInfo", UtilMisc.toMap("productStoreId", productStoreId));
                                storesMap.put(productStoreId, storeMap);
                            }
                            GenericValue productStore = (GenericValue) storeMap.get("productStore");
                            String productStoreName = productStore.getString("storeName");
                            String orgCode = (String) storeMap.get("orgCode");
                            String orderTotal = df.format(order.get("grandTotal"));
                            Order ord = new Order(orderId, "JD_284ubc3b41a4", Integer.parseInt(productStoreId), productStoreName, 0, 0, 0, orgCode, orderTotal, 1, orderTotal,
                                    new Double("118.72889"), new Double("32.13012"), 0, 0, 10000, 0);
                            //订单状态 31000待支付，
//                          int orderStatus = 310000;
                            
                            String currentOrderStatus = "";
                            String currentOrderStatusDateStr = "";
                            
                            GenericValue orderStatus = (GenericValue) orderInfoRet.get("currentOrderStatus");
                            String statusId = orderStatus.getString("orderStatusId");
                            ord.orderState = Integer.parseInt(statusId);
                            currentOrderStatus = orderStatus.getString("statusId");
                            currentOrderStatusDateStr = UtilFormatOut.formatDateTime(orderStatus.getTimestamp("statusDatetime"), "MM-dd hh:mm", locale, TimeZone.getDefault());
                            //订单商品数量
                            List<GenericValue> orderItems = (List<GenericValue>) orderInfoRet.get("orderItems");
                            
                            if (UtilValidate.isNotEmpty(orderItems)) {
                                int totalQuantity = 0;
                                String deliveryTime = "";
                                List productList = FastList.newInstance();
                                String baseUrl = UtilProperties.getMessage("content.properties", "daojia.baseUrl", locale);
                                for (int j = 0; j < orderItems.size(); j++) {
                                    GenericValue item = orderItems.get(j);
                                    totalQuantity = totalQuantity + item.getBigDecimal("quantity").intValue();
                                    if (item.getTimestamp("shipAfterDate") != null)
                                        ord.deliveryTime = UtilFormatOut.formatDateTime(item.getTimestamp("shipAfterDate"), "yyyy-MM-dd hh:mm:ss", locale, TimeZone.getDefault());
                                    ProductList product = new ProductList();
                                    product.setSku(Integer.parseInt(item.getString("productId")));
                                    product.setPrice(item.getBigDecimal("unitPrice").intValue());
                                    product.setDiscountPrice(0);
                                    GenericValue miniProduct = item.getRelatedOne("Product");
                                    ProductContentWrapper miniProductContentWrapper = new ProductContentWrapper(dispatcher, miniProduct, locale, "text/html");
                                    String mediumImageUrl = miniProductContentWrapper.get("MEDIUM_IMAGE_URL").toString();
                                    product.setImgPath(baseUrl + mediumImageUrl);
                                    product.setShopId(productStoreId);
                                    product.setNum(item.getBigDecimal("quantity").intValue());
                                    product.setName(item.getString("itemDescription"));
                                    product.setOrderId(orderId);
                                    //
                                    List<GenericValue> orderReviews = delegator.findByAnd("ProductReview", UtilMisc.toMap("productId", item.getString("productId"), "orderId", orderId, "userLoginId", userLoginId));
                                    if (UtilValidate.isNotEmpty(orderReviews)) {
                                        product.setSkuCommentStatus(1);
                                    } else {
                                        product.setSkuCommentStatus(0);
                                    }
                                    product.setProductCategory(0);
                                    product.setPromotionType(1);
                                    productList.add(product);
                                }
                                ord.productList = productList;
                                ord.productTotalNum = totalQuantity;
                                ord.productTotalNumStr = "共" + totalQuantity + "件";
                                
                            }
                            //支付时间
                            GenericValue payment = (GenericValue) orderInfoRet.get("orderPaymentPreference");
                            boolean hasPay = false;
                            if (UtilValidate.isNotEmpty(payment)) {
                                if (payment.getString("paymentMethodTypeId").equals("EXT_ALIPAY") || payment.getString("paymentMethodTypeId").equals("EXT_WEIXIN")) {
                                    if (payment.getString("statusId").equals("PAYMENT_RECEIVED")) {
                                        ord.showPay = 0;
                                        //realPay
                                        //支付完成不需要显示
                                        hasPay = true;
                                        ord.realPay = UtilFormatOut.formatCurrency(payment.getBigDecimal("maxAccount"), "", locale, 2);
                                    }
                                }
                            }
                            if (!hasPay) {
                                //如果没有支付，并且订单状态为cancel ，statusUserLogin为system 代表支付超时
                                if (orderStatus.getString("statusId").equals("ORDER_CREATED")) {
                                    long rePayTime = orderHeader.getTimestamp("orderDate").getTime() + 1000 * 60 * 30;
                                    if (rePayTime > System.currentTimeMillis()) {
                                        //payEndTime 支付时间为订单创建时间半小时之内
                                        ord.payEndTime = orderHeader.getTimestamp("orderDate").getTime() + 1000 * 60 * 30;
                                        ord.showPay = 1;
                                        currentOrderStatus = "ORDER_PAY_UNPAY";
                                    } else {
                                        ord.showPay = 1;
                                        currentOrderStatus = "ORDER_CREATED";
                                    }
                                    
                                } else if (orderStatus.getString("statusId").equals("ORDER_CANCELLED") && orderStatus.getString("statusUserLogin").equals("system")) {
                                    ord.showPay = 0;
                                    currentOrderStatus = "ORDER_PAY_DELAY";
                                } else {
                                    ord.showPay = 0;
                                }
                            }
                            
                            ord.dateSubmit = UtilFormatOut.formatDateTime(orderHeader.getTimestamp("orderDate"), "yyyy-MM-dd hh:mm:ss", locale, TimeZone.getDefault());
                            String orderDate = UtilFormatOut.formatDateTime(orderHeader.getTimestamp("orderDate"), "yyyy-MM-dd", locale, TimeZone.getDefault());
                            String orderTime = UtilFormatOut.formatDateTime(orderHeader.getTimestamp("orderDate"), "hh:mm", locale, TimeZone.getDefault());
                            String nowDate = UtilFormatOut.formatDateTime(new Date(), "yyyy-MM-dd", locale, TimeZone.getDefault());
                            String dateSubmitStr = "";
                            if (orderDate.equals(nowDate)) {
                                dateSubmitStr += "今天";
                            } else {
                                dateSubmitStr += orderDate;
                            }
                            dateSubmitStr += " " + orderTime;
                            ord.dateSubmitStr = dateSubmitStr;
                            ord.productTotalPrice = UtilFormatOut.formatCurrency(order.getBigDecimal("grandTotal"), "", locale, 2);
                            
                            //评价状态
                            
                            //支付方式默认4
                            int paymentType = 4;
                            String homePath = System.getProperty("ofbiz.home");
                            String json = null;
                            json = FileUtil.readString("utf-8", new File(homePath + "/daojia/daojia/config/order/orderlist/orderStatus.json"));
                            Map<String, StringMap> ordStatusMap = new Gson().fromJson(json, Map.class);
                            
                            if (UtilValidate.isNotEmpty(ordStatusMap.get(currentOrderStatus))) {
                                StringMap orderStateStringMap = ordStatusMap.get(currentOrderStatus);
                                OrderStateMap orderStateMap = new OrderStateMap(((Double) (orderStateStringMap.get("stateId"))).intValue(), (String) orderStateStringMap.get("stateTitle"), (String) orderStateStringMap.get("stateDesc"), (String) orderStateStringMap.get("startTime"), (String) orderStateStringMap.get("stateIcon"));
                                orderStateMap.stateTime = currentOrderStatusDateStr;
                                ord.orderStateMap = orderStateMap;
                                
                                MainOrderStateMap mainOrderStateMap = null;
                                //已取消"orderState": "4"
                                if (currentOrderStatus.equals("ORDER_PAY_UNPAY")) {
                                    mainOrderStateMap = new MainOrderStateMap("1", orderStateMap.stateTitle, "#ff5757");
                                } else if (currentOrderStatus.equals("ORDER_COMPLETED")) {
                                    mainOrderStateMap = new MainOrderStateMap("3", orderStateMap.stateTitle, "#333333");
                                    
                                } else if (currentOrderStatus.equals("ORDER_PAY_DELAY") || currentOrderStatus.equals("ORDER_CANNEL")) {
                                    mainOrderStateMap = new MainOrderStateMap("4", orderStateMap.stateTitle, "#999999");
                                    
                                } else {
                                    mainOrderStateMap = new MainOrderStateMap("2", orderStateMap.stateTitle, "#333333");
                                }
                                ord.mainOrderStateMap = mainOrderStateMap;
                            }
                            ord.deliveryType = "0";
                            ord.deliveryType = "达达专送";
                            //productList
                            orders.add(ord);
                        }
                    }
                }
            } else {
                retMap.put("result", "");
                
            }
            
            retMap.put("result", orders);
            
            
        } catch (GenericServiceException e) {
            e.printStackTrace();
            retMap.put("msg", e.getMessage());
            retMap.put("result", "");
        } catch (IOException e) {
            e.printStackTrace();
            retMap.put("msg", e.getMessage());
            retMap.put("result", "");
        } catch (GenericEntityException e) {
            retMap.put("msg", e.getMessage());
            e.printStackTrace();
            retMap.put("result", "");
        }
        result.put("resultData", retMap);
        return result;
    }
    
    public static Map<String, Object> DaoJia_OrderInfo(DispatchContext dcx, Map<String, ? extends Object> context) {
        Map<String, Object> result = ServiceUtil.returnSuccess();
        String orderId = (String) context.get("orderId");
        Delegator delegator = dcx.getDelegator();
        Map<String, String> queryType = (Map<String, String>) context.get("queryType");
        String showCurrentOrderStatus = queryType.get("showCurrentOrderStatus");
        String showOrderReviews = queryType.get("showOrderReviews");
        String showOrderItems = queryType.get("showOrderItems");
        String showOrderHeader = queryType.get("showOrderHeader");
        String showOrderStatusList = queryType.get("showOrderStatusList");
        String showOrderPaymentPreference = queryType.get("showOrderPaymentPreference");
        if (UtilValidate.isNotEmpty(showCurrentOrderStatus) && showCurrentOrderStatus.equalsIgnoreCase("Y") || UtilValidate.isNotEmpty(showOrderStatusList) && showOrderStatusList.equalsIgnoreCase("Y")) {
            try {
                List<EntityCondition> exps = FastList.newInstance();
                EntityCondition condition = EntityCondition.makeCondition("orderId", EntityOperator.EQUALS, orderId);
                exps.add(condition);
                EntityCondition condition1 = EntityCondition.makeCondition("orderItemSeqId", EntityOperator.EQUALS, null);
                exps.add(condition1);
                List<GenericValue> orderStatus = delegator.findList("OrderStatus", EntityCondition.makeCondition(exps, EntityOperator.AND), null, UtilMisc.toList("-statusDatetime"), null, false);
                if (UtilValidate.isNotEmpty(orderStatus)) {
                    if (UtilValidate.isNotEmpty(showCurrentOrderStatus) && showCurrentOrderStatus.equals("Y")) {
                        GenericValue currentOrderStatus = EntityUtil.getFirst(orderStatus);
                        result.put("currentOrderStatus", currentOrderStatus);
                    }
                    if (UtilValidate.isNotEmpty(showOrderStatusList) && showOrderStatusList.equals("Y")) {
                        result.put("orderStatusList", orderStatus);
                    }
                }
                
                if (UtilValidate.isNotEmpty(showOrderItems) && showOrderItems.equalsIgnoreCase("Y")) {
                    List<GenericValue> items = delegator.findByAnd("OrderItem", UtilMisc.toMap("orderId", orderId));
                    result.put("orderItems", items);
                }
                if (UtilValidate.isNotEmpty(showOrderPaymentPreference) && showOrderPaymentPreference.equalsIgnoreCase("Y")) {
                    GenericValue payment = EntityUtil.getFirst(delegator.findByAnd("OrderPaymentPreference", UtilMisc.toMap("orderId", orderId)));
                    result.put("orderPaymentPreference", payment);
                }
                
                GenericValue orderHeader = delegator.findByPrimaryKey("OrderHeader", UtilMisc.toMap("orderId", orderId));
                result.put("orderHeader", orderHeader);
                
            } catch (GenericEntityException e) {
                e.printStackTrace();
            }
        }
        
        return result;
        
    }
    
    public static Map<String, Object> DaoJia_CheckBeforeToStoreDetail(DispatchContext dcx, Map<String, ? extends Object> context) {
        Map<String, Object> result = ServiceUtil.returnSuccess();
        Locale locale = (Locale) context.get("locale");
        Map<String, Object> resultData = FastMap.newInstance();
        resultData.put("id", "check.checkBeforeToStoreDetail");
        resultData.put("code", "0");
        resultData.put("msg", UtilProperties.getMessage("CommonUiLabels", "CommonSuccess", locale));
        resultData.put("result", 1);
        resultData.put("success", true);
        result.put("resultData", resultData);
        return result;
    }
    
    public static Map<String, Object> DaoJia_OrderInfo1(DispatchContext dcx, Map<String, ? extends Object> context) {
        Map<String, Object> result = ServiceUtil.returnSuccess();
        //{'startIndex':0,'dataSize':10}
        String body = (String) context.get("body");
        Map bodyMap = new Gson().fromJson(body, Map.class);
        GenericValue userLogin = (GenericValue) context.get("userLogin");
        String userLoginId = userLogin.getString("userLoginId");
        String orderId = ((String) bodyMap.get("orderId"));
        Locale locale = (Locale) context.get("locale");
        
        Map retMap = FastMap.newInstance();
        retMap.put("id", "order.list");
        retMap.put("msg", UtilProperties.getMessage("CommonUiLabels", "CommonSuccess", locale));
        retMap.put("success", true);
        
        java.text.DecimalFormat df = new java.text.DecimalFormat("0.00");
        //获得party order列表，按照时间倒序
        LocalDispatcher dispatcher = dcx.getDispatcher();
        Delegator delegator = dcx.getDelegator();
        try {
            Map orderMap = FastMap.newInstance();
            
            orderMap.put("orderId", orderId);
            orderMap.put("queryType", UtilMisc.toMap("showCurrentOrderStatus", "Y", "showOrderItems", "Y", "showOrderHeader", "Y", "showOrderStatusList", "Y", "showOrderReviews", "Y", "showOrderReviews", "Y"));
            Map orderInfoRet = dispatcher.runSync("DaoJia_OrderInfo", orderMap);
            GenericValue orderHeader = (GenericValue) orderInfoRet.get("orderHeader");
            String productStoreId = (String) orderHeader.get("productStoreId");
            if (ServiceUtil.isSuccess(orderInfoRet)) {
                Map storeMap = FastMap.newInstance();
                
                storeMap = dispatcher.runSync("DaoJia_StoreInfo", UtilMisc.toMap("productStoreId", productStoreId));
                
                GenericValue productStore = (GenericValue) storeMap.get("productStore");
                String productStoreName = productStore.getString("storeName");
                String orgCode = (String) storeMap.get("orgCode");
                
                OrderInfo ord = new OrderInfo(orderId, "JD_284ubc3b41a4", Integer.parseInt(productStoreId), productStoreName, 1, 1, 2, orgCode, 1,
                        new Double("118.72889"), new Double("32.13012"), 0, 0);
                
                List<GenericValue> orderReviews = delegator.findByAnd("ProductStoreReview", UtilMisc.toMap("productStoreId", productStoreId, "orderId", orderId));
                if (UtilValidate.isNotEmpty(orderReviews)) {
                    ord.setCommentStatus(0);
                } else {
                    ord.setCommentStatus(2);
                }
                
                //订单状态 31000待支付，
//               int orderStatus = 310000;
                
                String currentOrderStatus = "";
                String currentOrderStatusDateStr = "";
                
                GenericValue orderStatus = (GenericValue) orderInfoRet.get("currentOrderStatus");
                String statusId = orderStatus.getString("orderStatusId");
                ord.orderState = Integer.parseInt(statusId);
                currentOrderStatus = orderStatus.getString("statusId");
                currentOrderStatusDateStr = UtilFormatOut.formatDateTime(orderStatus.getTimestamp("statusDatetime"), "MM-dd hh:mm", locale, TimeZone.getDefault());
                //订单商品数量
                List<GenericValue> orderItems = (List<GenericValue>) orderInfoRet.get("orderItems");
                
                if (UtilValidate.isNotEmpty(orderItems)) {
                    int totalQuantity = 0;
                    String deliveryTime = "";
                    List productList = FastList.newInstance();
                    String baseUrl = UtilProperties.getMessage("content.properties", "daojia.baseUrl", locale);
                    for (int j = 0; j < orderItems.size(); j++) {
                        GenericValue item = orderItems.get(j);
                        totalQuantity = totalQuantity + item.getBigDecimal("quantity").intValue();
                        if (item.getTimestamp("shipAfterDate") != null)
                            ord.deliveryTime = UtilFormatOut.formatDateTime(item.getTimestamp("shipAfterDate"), "yyyy-MM-dd hh:mm:ss", locale, TimeZone.getDefault());
                        ProductList product = new ProductList();
                        product.setSku(Integer.parseInt(item.getString("productId")));
                        product.setPrice(item.getBigDecimal("unitPrice").intValue());
                        product.setDiscountPrice(0);
                        GenericValue miniProduct = item.getRelatedOne("Product");
                        ProductContentWrapper miniProductContentWrapper = new ProductContentWrapper(dispatcher, miniProduct, locale, "text/html");
                        String mediumImageUrl = miniProductContentWrapper.get("MEDIUM_IMAGE_URL").toString();
                        product.setImgPath(baseUrl + mediumImageUrl);
                        product.setShopId(productStoreId);
                        product.setNum(item.getBigDecimal("quantity").intValue());
                        product.setName(item.getString("itemDescription"));
                        product.setOrderId(orderId);
                        orderReviews = delegator.findByAnd("ProductReview", UtilMisc.toMap("productId", item.getString("productId"), "orderId", orderId, "userLoginId", userLoginId));
                        if (UtilValidate.isNotEmpty(orderReviews)) {
                            product.setSkuCommentStatus(1);
                        } else {
                            product.setSkuCommentStatus(0);
                        }
                        product.setProductCategory(0);
                        product.setPromotionType(1);
                        productList.add(product);
                    }
                    
                    ord.productTotalNumStr = "共" + totalQuantity + "件";
                    
                }
                //支付时间
                GenericValue payment = (GenericValue) orderInfoRet.get("orderPaymentPreference");
                boolean hasPay = false;
                if (UtilValidate.isNotEmpty(payment)) {
                    if (payment.getString("paymentMethodTypeId").equals("EXT_ALIPAY") || payment.getString("paymentMethodTypeId").equals("EXT_WEIXIN")) {
                        if (payment.getString("statusId").equals("PAYMENT_RECEIVED")) {
                            ord.showPay = 0;
                            //realPay
                            //支付完成不需要显示
                            hasPay = true;
                            ord.realPay = UtilFormatOut.formatCurrency(payment.getBigDecimal("maxAccount"), "", locale, 2);
                        }
                    }
                }
                if (!hasPay) {
                    //如果没有支付，并且订单状态为cancel ，statusUserLogin为system 代表支付超时
                    if (orderStatus.getString("statusId").equals("ORDER_CREATED")) {
                        long rePayTime = orderHeader.getTimestamp("orderDate").getTime() + 1000 * 60 * 30;
                        if (rePayTime > System.currentTimeMillis()) {
                            //payEndTime 支付时间为订单创建时间半小时之内
                            ord.showPay = 1;
                            currentOrderStatus = "ORDER_PAY_UNPAY";
                        } else {
                            ord.showPay = 1;
                            currentOrderStatus = "ORDER_CREATED";
                        }
                        
                    } else if (orderStatus.getString("statusId").equals("ORDER_CANCELLED") && orderStatus.getString("statusUserLogin").equals("system")) {
                        ord.showPay = 0;
                        currentOrderStatus = "ORDER_PAY_DELAY";
                    } else {
                        ord.showPay = 0;
                    }
                }
                
                ord.dateSubmit = UtilFormatOut.formatDateTime(orderHeader.getTimestamp("orderDate"), "yyyy-MM-dd hh:mm:ss", locale, TimeZone.getDefault());
                String orderDate = UtilFormatOut.formatDateTime(orderHeader.getTimestamp("orderDate"), "yyyy-MM-dd", locale, TimeZone.getDefault());
                String orderTime = UtilFormatOut.formatDateTime(orderHeader.getTimestamp("orderDate"), "hh:mm", locale, TimeZone.getDefault());
                String nowDate = UtilFormatOut.formatDateTime(new Date(), "yyyy-MM-dd", locale, TimeZone.getDefault());
                String dateSubmitStr = "";
                if (orderDate.equals(nowDate)) {
                    dateSubmitStr += "今天";
                } else {
                    dateSubmitStr += orderDate;
                }
                dateSubmitStr += " " + orderTime;
                ord.dateSubmitStr = dateSubmitStr;
                
                
                //支付方式默认4
                int paymentType = 4;
                String homePath = System.getProperty("ofbiz.home");
                String json = null;
                json = FileUtil.readString("utf-8", new File(homePath + "/daojia/daojia/config/order/orderlist/orderStatus.json"));
                Map<String, StringMap> ordStatusMap = new Gson().fromJson(json, Map.class);
                
                if (UtilValidate.isNotEmpty(ordStatusMap.get(currentOrderStatus))) {
                    StringMap orderStateStringMap = ordStatusMap.get(currentOrderStatus);
                    OrderStateMap orderStateMap = new OrderStateMap(((Double) (orderStateStringMap.get("stateId"))).intValue(), (String) orderStateStringMap.get("stateTitle"), (String) orderStateStringMap.get("stateDesc"), (String) orderStateStringMap.get("startTime"), (String) orderStateStringMap.get("stateIcon"));
                    orderStateMap.stateTime = currentOrderStatusDateStr;
                    ord.orderStateMap = orderStateMap;
                    
                    MainOrderStateMap mainOrderStateMap = null;
                    //已取消"orderState": "4"
                    if (currentOrderStatus.equals("ORDER_PAY_UNPAY")) {
                        mainOrderStateMap = new MainOrderStateMap("1", orderStateMap.stateTitle, "#ff5757");
                    } else if (currentOrderStatus.equals("ORDER_COMPLETED")) {
                        mainOrderStateMap = new MainOrderStateMap("3", orderStateMap.stateTitle, "#333333");
                        
                    } else if (currentOrderStatus.equals("ORDER_PAY_DELAY") || currentOrderStatus.equals("ORDER_CANNEL")) {
                        mainOrderStateMap = new MainOrderStateMap("4", orderStateMap.stateTitle, "#999999");
                        
                    } else {
                        mainOrderStateMap = new MainOrderStateMap("2", orderStateMap.stateTitle, "#333333");
                    }
                    ord.mainOrderStateMap = mainOrderStateMap;
                }
                ord.deliveryType = "0";
                ord.deliveryType = "达达专送";
                ord.isGroup = 0;
                ord.servicePhoneType = 2;
                ord.afterSaleSign = 0;
                ord.imSwitch = "1";
                ord.servicePhone = "18914460003";
                ord.deliveryManMobileNew = "13952038890";
                ord.cancelSwitchNew = 0;
                
                ord.freightRule = "本单运费共计2元，包括<br/>·基础运费 2元<br/>";
                ord.afsSwitch = 0;
                //deliveryInfoList
                List<DeliveryInfoList> deliveryInfoLists = FastList.newInstance();
                DeliveryInfoList delivery = new DeliveryInfoList("配送信息", 0);
                deliveryInfoLists.add(delivery);
                DeliveryInfoList delivery1 = new DeliveryInfoList("送达时间", 0, ord.deliveryTime);
                deliveryInfoLists.add(delivery1);
                
                GenericValue shipment = EntityUtil.getFirst(delegator.findByAnd("Shipment", UtilMisc.toMap("primaryOrderId", orderId), UtilMisc.toList("-createdStamp")));
                if (UtilValidate.isNotEmpty(shipment)) {
                    String contactMechId = (String) shipment.get("destinationContactMechId");
                    GenericValue postalAddress = delegator.findByPrimaryKey("PostalAddress", UtilMisc.toMap("contactMechId", contactMechId));
                    String toName = (String) postalAddress.get("toName");
                    String phone = (String) postalAddress.get("mobilePhone");
                    String address1 = (String) postalAddress.get("address1");
                    String address2 = (String) postalAddress.get("address2");
                    DeliveryInfoList delivery2 = new DeliveryInfoList("收货地址", 0, toName + " " + UtilStrings.getMaskString(phone, 3, 4) + address1 + address2);
                    deliveryInfoLists.add(delivery2);
                }
                DeliveryInfoList delivery3 = new DeliveryInfoList("配送方式", 1, "达达专送");
                deliveryInfoLists.add(delivery3);
                
                DeliveryInfoList delivery4 = new DeliveryInfoList("配送员", 0, "xxx 13988888888");
                deliveryInfoLists.add(delivery4);
                ord.setDeliveryInfoList(deliveryInfoLists);
                //price Info
                OrderReadHelper orderReadHelper = new OrderReadHelper(orderHeader);
                BigDecimal itemSubTotal = orderReadHelper.getOrderItemsSubTotal();
                String productPrice = UtilFormatOut.formatCurrency(itemSubTotal, "", locale, 2);
                List<PriceInfoList> priceInfoLists = FastList.newInstance();
                PriceInfoList priceInfoList = new PriceInfoList("商品金额", productPrice, 0);
                priceInfoLists.add(priceInfoList);
                
                List<GenericValue> orderHeaderAdjustments = orderReadHelper.getOrderHeaderAdjustments();
                BigDecimal orderSubTotal = orderReadHelper.getOrderItemsSubTotal();
                
                BigDecimal otherAdjAmount = OrderReadHelper.calcOrderAdjustments(orderHeaderAdjustments, orderSubTotal, true, false, false);
                List<GenericValue> orderAdjustments = orderReadHelper.getAdjustments();
                
                BigDecimal shippingAmount = OrderReadHelper.getAllOrderItemsAdjustmentsTotal(orderItems, orderAdjustments, false, false, true);
                shippingAmount = shippingAmount.add(OrderReadHelper.calcOrderAdjustments(orderHeaderAdjustments, orderSubTotal, false, false, true));
                
                String otherAdjAmountStr = UtilFormatOut.formatCurrency(otherAdjAmount, "", locale, 2);
                PriceInfoList priceInfoList1 = new PriceInfoList("其他调整总计", otherAdjAmountStr, 0);
                priceInfoLists.add(priceInfoList1);
                
                String shippingAmountStr = UtilFormatOut.formatCurrency(shippingAmount, "", locale, 2);
                PriceInfoList priceInfoList2 = new PriceInfoList("运费金额", shippingAmountStr, 0);
                priceInfoLists.add(priceInfoList2);
                
                BigDecimal grandTotal = OrderReadHelper.getOrderGrandTotal(orderItems, orderAdjustments);
                
                String grandTotalStr = UtilFormatOut.formatCurrency(grandTotal, "", locale, 2);
                ord.setProductTotalPrice(grandTotalStr);
                ord.setRealPay(grandTotalStr);
                ord.productTotalPrice = grandTotalStr;
                ord.shouldPay = grandTotalStr;
                
                PriceInfoList priceInfoList3 = new PriceInfoList("应付：" + grandTotal, 0, "#ff5757");
                priceInfoLists.add(priceInfoList3);
                
                ord.setPriceInfoList(priceInfoLists);
                //orderInfoList
                List<DeliveryInfoList> orderInfos = FastList.newInstance();
                DeliveryInfoList infoList = new DeliveryInfoList("订单信息", 0);
                orderInfos.add(infoList);
                DeliveryInfoList infoList1 = new DeliveryInfoList("订单号码：", 0, orderId);
                orderInfos.add(infoList1);
                DeliveryInfoList infoList2 = new DeliveryInfoList("订单时间", 0, orderDate);
                orderInfos.add(infoList2);
                DeliveryInfoList infoList3 = new DeliveryInfoList("支付方式", 0, "在线支付");
                orderInfos.add(infoList3);
                DeliveryInfoList infoList4 = new DeliveryInfoList("订单备注", 0, "所购商品如遇缺货，您需要：其它商品继续配送（缺货商品退款）");
                orderInfos.add(infoList4);
                ord.setOrderInfoList(orderInfos);
                
                List<OrderCancelReasons> reasonsList = FastList.newInstance();
                OrderCancelReasons reasons = new OrderCancelReasons("商家缺货", "40");
                reasonsList.add(reasons);
                OrderCancelReasons reasons1 = new OrderCancelReasons("在线支付遇到问题", "10");
                reasonsList.add(reasons1);
                OrderCancelReasons reasons2 = new OrderCancelReasons("忘记使用优惠券/码", "20");
                reasonsList.add(reasons2);
                OrderCancelReasons reasons3 = new OrderCancelReasons("买错商品/暂不想购买", "30");
                reasonsList.add(reasons3);
                OrderCancelReasons reasons4 = new OrderCancelReasons("无人配送", "50");
                reasonsList.add(reasons4);
                OrderCancelReasons reasons5 = new OrderCancelReasons("点错了，不取消订单", "99");
                reasonsList.add(reasons5);
                //orderProductList
                //订单商品数量
                
                if (UtilValidate.isNotEmpty(orderItems)) {
                    int totalQuantity = 0;
                    String deliveryTime = "";
                    List productList = FastList.newInstance();
                    String baseUrl = UtilProperties.getMessage("content.properties", "daojia.baseUrl", locale);
                    for (int j = 0; j < orderItems.size(); j++) {
                        GenericValue item = orderItems.get(j);
                        totalQuantity = totalQuantity + item.getBigDecimal("quantity").intValue();
                        if (item.getTimestamp("shipAfterDate") != null)
                            ord.deliveryTime = UtilFormatOut.formatDateTime(item.getTimestamp("shipAfterDate"), "yyyy-MM-dd hh:mm:ss", locale, TimeZone.getDefault());
                        OrderProductList product = new OrderProductList();
                        product.setSku(Integer.parseInt(item.getString("productId")));
                        product.setPrice(item.getBigDecimal("unitPrice").intValue());
                        product.setDiscountPrice(0);
                        GenericValue miniProduct = item.getRelatedOne("Product");
                        ProductContentWrapper miniProductContentWrapper = new ProductContentWrapper(dispatcher, miniProduct, locale, "text/html");
                        String mediumImageUrl = miniProductContentWrapper.get("MEDIUM_IMAGE_URL").toString();
                        product.setImgPath(baseUrl + mediumImageUrl);
                        product.setShopId(productStoreId);
                        product.setNum(item.getBigDecimal("quantity").intValue());
                        product.setName(item.getString("itemDescription"));
                        product.setOrderId(orderId);
                        orderReviews = delegator.findByAnd("ProductReview", UtilMisc.toMap("productId", item.getString("productId"), "orderId", orderId, "userLoginId", userLoginId));
                        if (UtilValidate.isNotEmpty(orderReviews)) {
                            product.setSkuCommentStatus(1);
                        } else {
                            product.setSkuCommentStatus(0);
                        }
                        product.setProductCategory(0);
                        product.setPromotionType(1);
                        productList.add(product);
                    }
                    ord.setOrderProductList(productList);
                    ord.productTotalNumStr = "共" + totalQuantity + "件";
                }
                //orderShowTitles
                OrderInfo.OrderShowTitles titles = new OrderInfo.OrderShowTitles();
                titles.shouldPayTitle = "应付";
                titles.realPayTitle = "实付：";
                ord.setOrderShowTitles(titles);
                //contactList
                ContactList contactList = new ContactList("13958888888", "10", "联系配送员");
                ContactList contactList1 = new ContactList("13958888888", "20", "联系商家");
                ContactList contactList2 = new ContactList("4000020020", "100", "在线客服（售后，交易纠纷）");
                List<ContactList> contactLists = FastList.newInstance();
                contactLists.add(contactList);
                contactLists.add(contactList1);
                contactLists.add(contactList2);
                ord.setContactList(contactLists);
                ord.setReceiveConfirm(0);
                ord.setUrgeOrder(new OrderInfo.UrgeOrder(0));
                retMap.put("result", ord);
            } else {
                retMap.put("result", "");
            }
            
        } catch (GenericServiceException e) {
            e.printStackTrace();
            retMap.put("result", e.getMessage());
        } catch (IOException e) {
            e.printStackTrace();
            retMap.put("result", e.getMessage());
        } catch (GenericEntityException e) {
            e.printStackTrace();
            retMap.put("result", e.getMessage());
        }
        
        result.put("resultData", retMap);
        return result;
    }
    
    public static Map<String, Object> DaoJia_OrderProductInventory(DispatchContext dcx, Map<String, ? extends Object> context) {
        Map<String, Object> result = ServiceUtil.returnSuccess();
        String body = (String) context.get("body");
        Map bodyMap = new Gson().fromJson(body, Map.class);
        String orderId = (String) bodyMap.get("orderId");
        Delegator delegator = dcx.getDelegator();
        LocalDispatcher dispatcher = dcx.getDispatcher();
        GenericValue userLogin = (GenericValue) context.get("userLogin");
        String userLoginId = userLogin.getString("userLoginId");
        Locale locale = (Locale) context.get("locale");
        String baseUrl = UtilProperties.getMessage("content.properties", "daojia.baseUrl", locale);
        Map resultMap = FastMap.newInstance();
        resultMap.put("id", "order.productInventory");
        resultMap.put("code", "0");
        resultMap.put("msg", UtilProperties.getMessage("CommonUiLabels", "CommonSuccess", locale));
        resultMap.put("success", true);
        try {
            GenericValue orderHeader = delegator.findByPrimaryKey("OrderHeader", UtilMisc.toMap("orderId", orderId));
            if (UtilValidate.isNotEmpty(orderHeader)) {
                OrderReadHelper orderReadHelper = new OrderReadHelper(orderHeader);
                List<GenericValue> orderItems = orderReadHelper.getOrderItems();
                List<GenericValue> orderAdjustments = orderReadHelper.getAdjustments();
                if (UtilValidate.isNotEmpty(orderItems)) {
                    List<OrderProductInventory> orderProducts = FastList.newInstance();
                    for (int i = 0; i < orderItems.size(); i++) {
                        GenericValue orderItem = orderItems.get(i);
                        GenericValue product = orderItem.getRelatedOne("Product");
                        OrderProductInventory orderProduct = new OrderProductInventory();
                        orderProduct.setSku(Integer.parseInt((String) product.get("productId")));
                        //单价
                        int unitPrice = ((BigDecimal) orderItem.get("unitPrice")).multiply(new BigDecimal(100)).intValue();
                        orderProduct.setPrice(unitPrice);
                        BigDecimal itemAdjustAmount = OrderReadHelper.getOrderItemAdjustmentsTotal(orderItem, orderAdjustments, true, false, false);
                        orderProduct.setDiscountPrice(itemAdjustAmount.multiply(new BigDecimal(100)).intValue());
                        ProductContentWrapper contentWrapper = new ProductContentWrapper(dispatcher, product, locale, "text/html");
                        String mediumImageUrl = contentWrapper.get("MEDIUM_IMAGE_URL").toString();
                        orderProduct.setImgPath(baseUrl + mediumImageUrl);
                        orderProduct.setShopId("10055645");
                        orderProduct.setNum(((BigDecimal) orderItem.get("quantity")).intValue());
                        orderProduct.setName((String) orderItem.get("itemDescription"));
                        orderProduct.setOrderId(orderId);
                        List<GenericValue> orderReviews = delegator.findByAnd("ProductReview", UtilMisc.toMap("productId", product.get("productId"), "orderId", orderId, "userLoginId", userLoginId));
                        if (UtilValidate.isNotEmpty(orderReviews)) {
                            orderProduct.setSkuCommentStatus(1);
                        } else {
                            orderProduct.setSkuCommentStatus(0);
                        }
                        orderProduct.setProductCategory(1);
                        orderProduct.setPriceStr(UtilFormatOut.formatCurrency((BigDecimal) orderItem.get("unitPrice"), "", locale, 2));
                        orderProduct.setTotalPriceStr(UtilFormatOut.formatCurrency(OrderReadHelper.getOrderItemTotal(orderItem, orderAdjustments), "", locale, 2));
                        orderProduct.setPromotionType(1);
                        orderProduct.setPromotionTypeForAfs(1);
                        orderProduct.setCanApplyAfs(false);
                        orderProduct.setShowAfsDetail(false);
                        orderProducts.add(orderProduct);
                    }
                    
                    Map retMap = FastMap.newInstance();
                    retMap.put("orderProductList", orderProducts);
                    retMap.put("orgCode", "74597");
                    retMap.put("isWaimaiOrder", 0);
                    resultMap.put("result", retMap);
                }
                orderReadHelper.getOrderItems();
            }
        } catch (GenericEntityException e) {
            e.printStackTrace();
            resultMap.put("result", e.getMessage());
        }
        result.put("resultData", resultMap);
        return result;
    }
    
    public static Map<String, Object> DaoJia_OrderState(DispatchContext dcx, Map<String, ? extends Object> context) {
        Map<String, Object> result = ServiceUtil.returnSuccess();
        //{'startIndex':0,'dataSize':10}
        String body = (String) context.get("body");
        Map bodyMap = new Gson().fromJson(body, Map.class);
        GenericValue userLogin = (GenericValue) context.get("userLogin");
        String orderId = ((String) bodyMap.get("orderId"));
        Locale locale = (Locale) context.get("locale");
        String userLoginId = userLogin.getString("userLoginId");
        Map retMap = FastMap.newInstance();
        retMap.put("id", "order.state");
        retMap.put("msg", UtilProperties.getMessage("CommonUiLabels", "CommonSuccess", locale));
        retMap.put("success", true);
        
        java.text.DecimalFormat df = new java.text.DecimalFormat("0.00");
        //获得party order列表，按照时间倒序
        LocalDispatcher dispatcher = dcx.getDispatcher();
        Delegator delegator = dcx.getDelegator();
        try {
            Map orderMap = FastMap.newInstance();
            
            orderMap.put("orderId", orderId);
            orderMap.put("queryType", UtilMisc.toMap("showCurrentOrderStatus", "Y", "showOrderItems", "Y", "showOrderHeader", "Y", "showOrderStatusList", "Y", "showOrderReviews", "Y", "showOrderReviews", "Y"));
            Map orderInfoRet = dispatcher.runSync("DaoJia_OrderInfo", orderMap);
            GenericValue orderHeader = (GenericValue) orderInfoRet.get("orderHeader");
            String productStoreId = (String) orderHeader.get("productStoreId");
            if (ServiceUtil.isSuccess(orderInfoRet)) {
                Map storeMap = FastMap.newInstance();
                
                storeMap = dispatcher.runSync("DaoJia_StoreInfo", UtilMisc.toMap("productStoreId", productStoreId));
                
                GenericValue productStore = (GenericValue) storeMap.get("productStore");
                String productStoreName = productStore.getString("storeName");
                String orgCode = (String) storeMap.get("orgCode");
                
                OrderInfo ord = new OrderInfo(orderId, "JD_284ubc3b41a4", Integer.parseInt(productStoreId), productStoreName, 1, 1, 2, orgCode, 1,
                        new Double("118.72889"), new Double("32.13012"), 0, 0);
                ord.deliveryManlat = new Double("32.13012");
                ord.deliveryManlng = new Double("118.72889");
                
                List<GenericValue> orderReviews = delegator.findByAnd("ProductStoreReview", UtilMisc.toMap("productStoreId", productStoreId, "orderId", orderId));
                if (UtilValidate.isNotEmpty(orderReviews)) {
                    ord.setCommentStatus(0);
                } else {
                    ord.setCommentStatus(2);
                }
                //订单状态 31000待支付，
//               int orderStatus = 310000;
                
                String currentOrderStatus = "";
                String currentOrderStatusDateStr = "";
                
                GenericValue orderStatus = (GenericValue) orderInfoRet.get("currentOrderStatus");
                String statusId = orderStatus.getString("orderStatusId");
                ord.orderState = Integer.parseInt(statusId);
                currentOrderStatus = orderStatus.getString("statusId");
                currentOrderStatusDateStr = UtilFormatOut.formatDateTime(orderStatus.getTimestamp("statusDatetime"), "MM-dd hh:mm", locale, TimeZone.getDefault());
                //订单商品数量
                List<GenericValue> orderItems = (List<GenericValue>) orderInfoRet.get("orderItems");
                
                if (UtilValidate.isNotEmpty(orderItems)) {
                    int totalQuantity = 0;
                    String deliveryTime = "";
                    List productList = FastList.newInstance();
                    String baseUrl = UtilProperties.getMessage("content.properties", "daojia.baseUrl", locale);
                    for (int j = 0; j < orderItems.size(); j++) {
                        GenericValue item = orderItems.get(j);
                        totalQuantity = totalQuantity + item.getBigDecimal("quantity").intValue();
                        if (item.getTimestamp("shipAfterDate") != null)
                            ord.deliveryTime = UtilFormatOut.formatDateTime(item.getTimestamp("shipAfterDate"), "yyyy-MM-dd hh:mm:ss", locale, TimeZone.getDefault());
                        ProductList product = new ProductList();
                        product.setSku(Integer.parseInt(item.getString("productId")));
                        product.setPrice(item.getBigDecimal("unitPrice").intValue());
                        product.setDiscountPrice(0);
                        GenericValue miniProduct = item.getRelatedOne("Product");
                        ProductContentWrapper miniProductContentWrapper = new ProductContentWrapper(dispatcher, miniProduct, locale, "text/html");
                        String mediumImageUrl = miniProductContentWrapper.get("MEDIUM_IMAGE_URL").toString();
                        product.setImgPath(baseUrl + mediumImageUrl);
                        product.setShopId(productStoreId);
                        product.setNum(item.getBigDecimal("quantity").intValue());
                        product.setName(item.getString("itemDescription"));
                        product.setOrderId(orderId);
                        orderReviews = delegator.findByAnd("ProductReview", UtilMisc.toMap("productId", item.getString("productId"), "orderId", orderId, "userLoginId", userLoginId));
                        if (UtilValidate.isNotEmpty(orderReviews)) {
                            product.setSkuCommentStatus(1);
                        } else {
                            product.setSkuCommentStatus(0);
                        }
                        product.setProductCategory(0);
                        product.setPromotionType(1);
                        productList.add(product);
                    }
                    
                    ord.productTotalNumStr = "共" + totalQuantity + "件";
                    
                }
                //支付时间
                GenericValue payment = (GenericValue) orderInfoRet.get("orderPaymentPreference");
                boolean hasPay = false;
                if (UtilValidate.isNotEmpty(payment)) {
                    if (payment.getString("paymentMethodTypeId").equals("EXT_ALIPAY") || payment.getString("paymentMethodTypeId").equals("EXT_WEIXIN")) {
                        if (payment.getString("statusId").equals("PAYMENT_RECEIVED")) {
                            ord.showPay = 0;
                            //realPay
                            //支付完成不需要显示
                            hasPay = true;
                            ord.realPay = UtilFormatOut.formatCurrency(payment.getBigDecimal("maxAccount"), "", locale, 2);
                        }
                    }
                }
                boolean isSystemCancel = false;
                if (!hasPay) {
                    //如果没有支付，并且订单状态为cancel ，statusUserLogin为system 代表支付超时
                    if (orderStatus.getString("statusId").equals("ORDER_CREATED")) {
                        long rePayTime = orderHeader.getTimestamp("orderDate").getTime() + 1000 * 60 * 30;
                        if (rePayTime > System.currentTimeMillis()) {
                            //payEndTime 支付时间为订单创建时间半小时之内
                            ord.showPay = 1;
                            currentOrderStatus = "ORDER_PAY_UNPAY";
                        } else {
                            ord.showPay = 1;
                            currentOrderStatus = "ORDER_CREATED";
                        }
                        
                    } else if (orderStatus.getString("statusId").equals("ORDER_CANCELLED") && orderStatus.getString("statusUserLogin").equals("system")) {
                        ord.showPay = 0;
                        currentOrderStatus = "ORDER_PAY_DELAY";
                        isSystemCancel = true;
                    } else {
                        ord.showPay = 0;
                    }
                }
                
                ord.dateSubmit = UtilFormatOut.formatDateTime(orderHeader.getTimestamp("orderDate"), "yyyy-MM-dd hh:mm:ss", locale, TimeZone.getDefault());
                String orderDate = UtilFormatOut.formatDateTime(orderHeader.getTimestamp("orderDate"), "yyyy-MM-dd", locale, TimeZone.getDefault());
                String orderTime = UtilFormatOut.formatDateTime(orderHeader.getTimestamp("orderDate"), "hh:mm", locale, TimeZone.getDefault());
                String nowDate = UtilFormatOut.formatDateTime(new Date(), "yyyy-MM-dd", locale, TimeZone.getDefault());
                String dateSubmitStr = "";
                if (orderDate.equals(nowDate)) {
                    dateSubmitStr += "今天";
                } else {
                    dateSubmitStr += orderDate;
                }
                dateSubmitStr += " " + orderTime;
                ord.dateSubmitStr = dateSubmitStr;
                
                
                //支付方式默认4
                int paymentType = 4;
                String homePath = System.getProperty("ofbiz.home");
                String json = null;
                json = FileUtil.readString("utf-8", new File(homePath + "/daojia/daojia/config/order/orderlist/orderStatus.json"));
                Map<String, StringMap> ordStatusMap = new Gson().fromJson(json, Map.class);
                
                if (UtilValidate.isNotEmpty(ordStatusMap.get(currentOrderStatus))) {
                    StringMap orderStateStringMap = ordStatusMap.get(currentOrderStatus);
                    OrderStateMap orderStateMap = new OrderStateMap(((Double) (orderStateStringMap.get("stateId"))).intValue(), (String) orderStateStringMap.get("stateTitle"), (String) orderStateStringMap.get("stateDesc"), (String) orderStateStringMap.get("startTime"), (String) orderStateStringMap.get("stateIcon"));
                    orderStateMap.stateTime = currentOrderStatusDateStr;
                    ord.orderStateMap = orderStateMap;
                    
                    MainOrderStateMap mainOrderStateMap = null;
                    //已取消"orderState": "4"
                    if (currentOrderStatus.equals("ORDER_PAY_UNPAY")) {
                        mainOrderStateMap = new MainOrderStateMap("1", orderStateMap.stateTitle, "#ff5757");
                    } else if (currentOrderStatus.equals("ORDER_COMPLETED")) {
                        mainOrderStateMap = new MainOrderStateMap("3", orderStateMap.stateTitle, "#333333");
                        
                    } else if (currentOrderStatus.equals("ORDER_PAY_DELAY") || currentOrderStatus.equals("ORDER_CANNEL")) {
                        mainOrderStateMap = new MainOrderStateMap("4", orderStateMap.stateTitle, "#999999");
                        
                    } else {
                        mainOrderStateMap = new MainOrderStateMap("2", orderStateMap.stateTitle, "#333333");
                    }
                    ord.mainOrderStateMap = mainOrderStateMap;
                }
                ord.deliveryType = "0";
                ord.deliveryType = "达达专送";
                ord.isGroup = 0;
                ord.servicePhoneType = 2;
                ord.afterSaleSign = 0;
                ord.imSwitch = "1";
                ord.servicePhone = "18914460003";
                ord.deliveryManMobileNew = "13952038890";
                ord.cancelSwitchNew = 0;
                
                ord.freightRule = "本单运费共计2元，包括<br/>·基础运费 2元<br/>";
                ord.afsSwitch = 0;
                //deliveryInfoList
                List<DeliveryInfoList> deliveryInfoLists = FastList.newInstance();
                DeliveryInfoList delivery = new DeliveryInfoList("配送信息", 0);
                deliveryInfoLists.add(delivery);
                DeliveryInfoList delivery1 = new DeliveryInfoList("送达时间", 0, ord.deliveryTime);
                deliveryInfoLists.add(delivery1);
                
                GenericValue shipment = EntityUtil.getFirst(delegator.findByAnd("Shipment", UtilMisc.toMap("primaryOrderId", orderId), UtilMisc.toList("-createdStamp")));
                if (UtilValidate.isNotEmpty(shipment)) {
                    String contactMechId = (String) shipment.get("destinationContactMechId");
                    GenericValue postalAddress = delegator.findByPrimaryKey("PostalAddress", UtilMisc.toMap("contactMechId", contactMechId));
                    String toName = (String) postalAddress.get("toName");
                    String phone = (String) postalAddress.get("mobilePhone");
                    String address1 = (String) postalAddress.get("address1");
                    String address2 = (String) postalAddress.get("address2");
                    DeliveryInfoList delivery2 = new DeliveryInfoList("收货地址", 0, toName + " " + UtilStrings.getMaskString(phone, 3, 4) + address1 + address2);
                    deliveryInfoLists.add(delivery2);
                }
                DeliveryInfoList delivery3 = new DeliveryInfoList("配送方式", 1, "达达专送");
                deliveryInfoLists.add(delivery3);
                
                DeliveryInfoList delivery4 = new DeliveryInfoList("配送员", 0, "xxx 13988888888");
                deliveryInfoLists.add(delivery4);
                ord.setDeliveryInfoList(deliveryInfoLists);
                //price Info
                OrderReadHelper orderReadHelper = new OrderReadHelper(orderHeader);
                BigDecimal itemSubTotal = orderReadHelper.getOrderItemsSubTotal();
                String productPrice = UtilFormatOut.formatCurrency(itemSubTotal, "", locale, 2);
                List<PriceInfoList> priceInfoLists = FastList.newInstance();
                PriceInfoList priceInfoList = new PriceInfoList("商品金额", productPrice, 0);
                priceInfoLists.add(priceInfoList);
                
                List<GenericValue> orderHeaderAdjustments = orderReadHelper.getOrderHeaderAdjustments();
                BigDecimal orderSubTotal = orderReadHelper.getOrderItemsSubTotal();
                
                BigDecimal otherAdjAmount = OrderReadHelper.calcOrderAdjustments(orderHeaderAdjustments, orderSubTotal, true, false, false);
                List<GenericValue> orderAdjustments = orderReadHelper.getAdjustments();
                
                BigDecimal shippingAmount = OrderReadHelper.getAllOrderItemsAdjustmentsTotal(orderItems, orderAdjustments, false, false, true);
                shippingAmount = shippingAmount.add(OrderReadHelper.calcOrderAdjustments(orderHeaderAdjustments, orderSubTotal, false, false, true));
                
                String otherAdjAmountStr = UtilFormatOut.formatCurrency(otherAdjAmount, "", locale, 2);
                PriceInfoList priceInfoList1 = new PriceInfoList("其他调整总计", otherAdjAmountStr, 0);
                priceInfoLists.add(priceInfoList1);
                
                String shippingAmountStr = UtilFormatOut.formatCurrency(shippingAmount, "", locale, 2);
                PriceInfoList priceInfoList2 = new PriceInfoList("运费金额", shippingAmountStr, 0);
                priceInfoLists.add(priceInfoList2);
                
                BigDecimal grandTotal = OrderReadHelper.getOrderGrandTotal(orderItems, orderAdjustments);
                
                String grandTotalStr = UtilFormatOut.formatCurrency(grandTotal, "", locale, 2);
                ord.setProductTotalPrice(grandTotalStr);
                ord.setRealPay(grandTotalStr);
                ord.productTotalPrice = grandTotalStr;
                ord.shouldPay = grandTotalStr;
                ord.isSowMap = 1;
                PriceInfoList priceInfoList3 = new PriceInfoList("应付：" + grandTotal, 0, "#ff5757");
                priceInfoLists.add(priceInfoList3);
                
                ord.setPriceInfoList(priceInfoLists);
                //orderInfoList
                List<DeliveryInfoList> orderInfos = FastList.newInstance();
                DeliveryInfoList infoList = new DeliveryInfoList("订单信息", 0);
                orderInfos.add(infoList);
                DeliveryInfoList infoList1 = new DeliveryInfoList("订单号码：", 0, orderId);
                orderInfos.add(infoList1);
                DeliveryInfoList infoList2 = new DeliveryInfoList("订单时间", 0, orderDate);
                orderInfos.add(infoList2);
                DeliveryInfoList infoList3 = new DeliveryInfoList("支付方式", 0, "在线支付");
                orderInfos.add(infoList3);
                DeliveryInfoList infoList4 = new DeliveryInfoList("订单备注", 0, "所购商品如遇缺货，您需要：其它商品继续配送（缺货商品退款）");
                orderInfos.add(infoList4);
                ord.setOrderInfoList(orderInfos);
                
                List<OrderCancelReasons> reasonsList = FastList.newInstance();
                OrderCancelReasons reasons = new OrderCancelReasons("商家缺货", "40");
                reasonsList.add(reasons);
                OrderCancelReasons reasons1 = new OrderCancelReasons("在线支付遇到问题", "10");
                reasonsList.add(reasons1);
                OrderCancelReasons reasons2 = new OrderCancelReasons("忘记使用优惠券/码", "20");
                reasonsList.add(reasons2);
                OrderCancelReasons reasons3 = new OrderCancelReasons("买错商品/暂不想购买", "30");
                reasonsList.add(reasons3);
                OrderCancelReasons reasons4 = new OrderCancelReasons("无人配送", "50");
                reasonsList.add(reasons4);
                OrderCancelReasons reasons5 = new OrderCancelReasons("点错了，不取消订单", "99");
                reasonsList.add(reasons5);
                //orderProductList
                //订单商品数量
                
                if (UtilValidate.isNotEmpty(orderItems)) {
                    int totalQuantity = 0;
                    String deliveryTime = "";
                    List productList = FastList.newInstance();
                    String baseUrl = UtilProperties.getMessage("content.properties", "daojia.baseUrl", locale);
                    for (int j = 0; j < orderItems.size(); j++) {
                        GenericValue item = orderItems.get(j);
                        totalQuantity = totalQuantity + item.getBigDecimal("quantity").intValue();
                        if (item.getTimestamp("shipAfterDate") != null)
                            ord.deliveryTime = UtilFormatOut.formatDateTime(item.getTimestamp("shipAfterDate"), "yyyy-MM-dd hh:mm:ss", locale, TimeZone.getDefault());
                        OrderProductList product = new OrderProductList();
                        product.setSku(Integer.parseInt(item.getString("productId")));
                        product.setPrice(item.getBigDecimal("unitPrice").intValue());
                        product.setDiscountPrice(0);
                        GenericValue miniProduct = item.getRelatedOne("Product");
                        ProductContentWrapper miniProductContentWrapper = new ProductContentWrapper(dispatcher, miniProduct, locale, "text/html");
                        String mediumImageUrl = miniProductContentWrapper.get("MEDIUM_IMAGE_URL").toString();
                        product.setImgPath(baseUrl + mediumImageUrl);
                        product.setShopId(productStoreId);
                        product.setNum(item.getBigDecimal("quantity").intValue());
                        product.setName(item.getString("itemDescription"));
                        product.setOrderId(orderId);
                        orderReviews = delegator.findByAnd("ProductReview", UtilMisc.toMap("productId", item.getString("productId"), "orderId", orderId, "userLoginId", userLoginId));
                        if (UtilValidate.isNotEmpty(orderReviews)) {
                            product.setSkuCommentStatus(1);
                        } else {
                            product.setSkuCommentStatus(0);
                        }
                        product.setProductCategory(0);
                        product.setPromotionType(1);
                        productList.add(product);
                    }
                    ord.setOrderProductList(productList);
                    ord.productTotalNumStr = "共" + totalQuantity + "件";
                }
                //orderShowTitles
                OrderInfo.OrderShowTitles titles = new OrderInfo.OrderShowTitles();
                titles.shouldPayTitle = "应付";
                titles.realPayTitle = "实付：";
                ord.setOrderShowTitles(titles);
                //contactList
                ContactList contactList = new ContactList("13958888888", "10", "联系配送员");
                ContactList contactList1 = new ContactList("13958888888", "20", "联系商家");
                ContactList contactList2 = new ContactList("4000020020", "100", "在线客服（售后，交易纠纷）");
                List<ContactList> contactLists = FastList.newInstance();
                contactLists.add(contactList);
                contactLists.add(contactList1);
                contactLists.add(contactList2);
                ord.setContactList(contactLists);
                ord.setReceiveConfirm(0);
                ord.setUrgeOrder(new OrderInfo.UrgeOrder(0));
                //orderstateList
                SortedSet<OrderStateList> orderStateLists = new TreeSet<OrderStateList>();
                orderReadHelper.getOrderStatuses();
                List<GenericValue> orStatus = (List<GenericValue>) orderInfoRet.get("orderStatusList");
                if (UtilValidate.isNotEmpty(orStatus)) {
                    for (int i = 0; i < orStatus.size(); i++) {
                        GenericValue status = orStatus.get(i);
                        String statusString = status.getString("statusId");
                        if (statusString.equals("ORDER_CREATED")) {
                            String statusTime = UtilFormatOut.formatDateTime(status.getTimestamp("statusDatetime"), "yy-MM-dd hh:mm", locale, TimeZone.getDefault());
                            OrderStateList orderStateList = new OrderStateList(1, "订单提交成功", "订单号:" + orderId, statusTime, "1");
                            orderStateLists.add(orderStateList);
                        }
                        if (statusString.equals("PAYMENT_NOT_RECEIVED")) {
                            String statusTime = UtilFormatOut.formatDateTime(status.getTimestamp("statusDatetime"), "yy-MM-dd hh:mm", locale, TimeZone.getDefault());
                            OrderStateList orderStateList = new OrderStateList(5, "待支付", "请在订单提交后尽快完成支付，超时订单将自动取消", statusTime, "5");
                            orderStateLists.add(orderStateList);
                        }
                        if (statusString.equals("PAYMENT_RECEIVED")) {
                            String statusTime = UtilFormatOut.formatDateTime(status.getTimestamp("statusDatetime"), "yy-MM-dd hh:mm", locale, TimeZone.getDefault());
                            OrderStateList orderStateList = new OrderStateList(10, "已支付", "订单支付成功", statusTime, "6");
                            orderStateLists.add(orderStateList);
                        }
                        if (statusString.equals("ORDER_APPROVED")) {
                            String statusTime = UtilFormatOut.formatDateTime(status.getTimestamp("statusDatetime"), "yy-MM-dd hh:mm", locale, TimeZone.getDefault());
                            OrderStateList orderStateList = new OrderStateList(38, "商家已接单", "开始为您拣货", statusTime, "2");
                            //商家电话
                            orderStateList.setFunctionNumber((String) storeMap.get("contactNumber"));
                            orderStateList.setPhoneNumType("0");
                            orderStateLists.add(orderStateList);
                        }
                        if (statusString.equals("ORDER_COMPLETED")) {
                            String statusTime = UtilFormatOut.formatDateTime(status.getTimestamp("statusDatetime"), "yy-MM-dd hh:mm", locale, TimeZone.getDefault());
                            OrderStateList orderStateList = new OrderStateList(90, "订单完成", "感谢您使用阿凡提到家", statusTime, "4");
                            orderStateLists.add(orderStateList);
                        }
                        
                        if (statusString.equals("ORDER_CANCELLED")) {
                            if (isSystemCancel) {
                                String statusTime = UtilFormatOut.formatDateTime(status.getTimestamp("statusDatetime"), "yy-MM-dd hh:mm", locale, TimeZone.getDefault());
                                OrderStateList orderStateList = new OrderStateList(115, "超时未支付取消", "订单超时未支付，已自动取消", statusTime, "4");
                                orderStateLists.add(orderStateList);
                            } else {
                                String statusTime = UtilFormatOut.formatDateTime(status.getTimestamp("statusDatetime"), "yy-MM-dd hh:mm", locale, TimeZone.getDefault());
                                OrderStateList orderStateList = new OrderStateList(115, "订单取消", "订单已取消，感谢您的使用", statusTime, "4");
                                orderStateLists.add(orderStateList);
                            }
                        }
                        
                    }
                    
                    //配送员状态
                    //配送员已取货
                    if (currentOrderStatus.equals("ORDER_APPROVED") || currentOrderStatus.equals("ORDER_COMPLETED") || currentOrderStatus.equals("ORDER_CANCELLED")) {
                        GenericValue orderShipment = EntityUtil.getFirst(delegator.findByAnd("Shipment", UtilMisc.toMap("primaryOrderId", orderId, "shipmentTypeId", "SALES_SHIPMENT")));
                        if (UtilValidate.isNotEmpty(orderShipment)) {
                            String shipStatus = orderShipment.getString("statusId");
                            String statusTime = UtilFormatOut.formatDateTime(orderStatus.getTimestamp("statusDatetime"), "yy-MM-dd hh:mm", locale, TimeZone.getDefault());
                            if (shipStatus.equals("SHIPMENT_SHIPPED")) {
                                OrderStateList orderStateList = new OrderStateList(80, "配送员已取货", "开始为您配送，配送员：XXX", statusTime, "3");
                                orderStateList.setPhoneNumType("2");
                                orderStateList.setFunctionNumber("198888888888");
                                orderStateList.setIsShowStaticMap(1);
                                orderStateLists.add(orderStateList);
                            }
                            if (shipStatus.equals("SHIPMENT_PICKED")) {
                                OrderStateList orderStateList = new OrderStateList(40, "拣货完成", "商家已完成拣货，等待配送", statusTime, "2");
                                orderStateLists.add(orderStateList);
                            }
                            if (shipStatus.equals("SHIPMENT_PACKED")) {
                                OrderStateList orderStateList = new OrderStateList(40, "拣货完成", "商家已完成拣货，等待配送", statusTime, "2");
                                orderStateLists.add(orderStateList);
                            }
                            if (shipStatus.equals("SHIPMENT_DELIVERED")) {
                                OrderStateList orderStateList = new OrderStateList(80, "配送员已取货", "开始为您配送，配送员：XXX", statusTime, "3");
                                orderStateList.setPhoneNumType("2");
                                orderStateList.setFunctionNumber("198888888888");
                                orderStateList.setIsShowStaticMap(1);
                                orderStateLists.add(orderStateList);
                            }
                            
                        }
                    }
                    
                    ord.orderStateList = (orderStateLists);
                }
                
                
                retMap.put("result", ord);
            } else {
                retMap.put("result", "");
            }
            
        } catch (GenericServiceException e) {
            e.printStackTrace();
            retMap.put("result", e.getMessage());
        } catch (IOException e) {
            e.printStackTrace();
            retMap.put("result", e.getMessage());
        } catch (GenericEntityException e) {
            e.printStackTrace();
            retMap.put("result", e.getMessage());
        }
        
        result.put("resultData", retMap);
        return result;
    }
    
    public static Map<String, Object> DaoJia_OrderStateMap(DispatchContext dcx, Map<String, ? extends Object> context) {
        Map<String, Object> result = ServiceUtil.returnSuccess();
        String homePath = System.getProperty("ofbiz.home");
        String json = null;
        try {
            json = FileUtil.readString("utf-8", new File(homePath + "/daojia/daojia/config/order/orderStateMap.json"));
        } catch (IOException e) {
            e.printStackTrace();
        }
        Map<String, Object> orderStateMap = new Gson().fromJson(json, Map.class);
        result.put("resultData", orderStateMap);
        return result;
    }
    
    public static String DaoJia_RePurchaseSingleProduct(HttpServletRequest request, HttpServletResponse response) {
        Map<String, Object> result = ServiceUtil.returnSuccess();
//        {'cartUuid':'C6A0707D39D000018AC7DFA031FE1C8A','skuInfoList':[{'skuId':10002,'num':3},{'skuId':10009,'num':10}],'storeId':'10000'}
        String body = request.getParameter("body");
        Delegator delegator = (Delegator) request.getAttribute("delegator");
        LocalDispatcher dispatcher = (LocalDispatcher) request.getAttribute("dispatcher");
        Locale locale = (Locale) request.getAttribute("locale");
        if (locale == null) locale = Locale.CHINA;
        Map map = new Gson().fromJson(body, Map.class);
        List skuInfoList = (List) map.get("skuInfoList");
        if (UtilValidate.isNotEmpty(skuInfoList)) {
            ShoppingCart cart = ShoppingCartEvents.getCartObject(request);
            ShoppingCartHelper cartHelper = new ShoppingCartHelper(delegator, dispatcher, cart);
            for (int i = 0; i < skuInfoList.size(); i++) {
                Map skuMap = (Map) skuInfoList.get(i);
                String productId = String.valueOf(((Double) skuMap.get("skuId")).intValue());
                Integer num = ((Double) skuMap.get("num")).intValue();
                Map<String, Object> paramMap = UtilHttp.getCombinedMap(request);
                cartHelper.addToCart(null, null, null, productId, null, null, null, null, null, new BigDecimal(num), null, null, null, null, null, null, null, null, null, paramMap, null);
            }
        }
        Map<String, Object> retMap = FastMap.newInstance();
        retMap.put("id", "rePurchase.singleProduct");
        retMap.put("code", "0");
        retMap.put("msg", UtilProperties.getMessage("CommonUiLabels", "CommonSuccess", locale));
        retMap.put("result", UtilProperties.getMessage("CommonUiLabels", "CommonSuccess", locale));
        retMap.put("success", true);
        request.setAttribute("resultData", retMap);
        return "success";
    }
    
    public static Map<String, Object> DaoJia_OrderProductComment(DispatchContext dcx, Map<String, ? extends Object> context) {
        
        Map<String, Object> result = ServiceUtil.returnSuccess();
        Locale locale = (Locale) context.get("locale");
        Map<String, Object> retMap = FastMap.newInstance();
        retMap.put("id", "order.productComment");
        retMap.put("msg", UtilProperties.getMessage("CommonUiLabels", "CommonSuccess", locale));
        retMap.put("code", "0");
        retMap.put("success", true);
        String body = (String) context.get("body");
        Map bodyMap = new Gson().fromJson(body, Map.class);
        String orderId = (String) bodyMap.get("orderId");
        try {
            String homePath = System.getProperty("ofbiz.home");
            String json = FileUtil.readString("utf-8", new File(homePath + "/daojia/daojia/config/product/productComment.json"));
            
            ProductComment productComment = new Gson().fromJson(json, ProductComment.class);
            GenericValue userLogin = (GenericValue) context.get("userLogin");
            String userLoginId = userLogin.getString("userLoginId");
            Delegator delegator = dcx.getDelegator();
            LocalDispatcher dispatcher = dcx.getDispatcher();
            OrderReadHelper orderReadHelper = new OrderReadHelper(delegator, orderId);
            GenericValue shippingAddress = orderReadHelper.getShippingAddress();
            String address = "";
            if (UtilValidate.isNotEmpty(shippingAddress)) {
                address = shippingAddress.getString("address1") + shippingAddress.getString("address2");
                productComment.setAddress(address);
                productComment.setCustomerName(shippingAddress.getString("toName"));
                productComment.setMobile(shippingAddress.getString("mobilePhone"));
            }
            
            productComment.setOrderId(orderId);
            
            productComment.setPaymentType(4);
            productComment.setOrderState(orderReadHelper.getCurrentStatusString());
            productComment.setOrderStateName(orderReadHelper.getCurrentStatusString());
            GenericValue orderHeader = orderReadHelper.getOrderHeader();
            String orderDate = UtilFormatOut.formatDateTime(orderHeader.getTimestamp("orderDate"), "yyyy-MM-dd hh:mm", locale, TimeZone.getDefault());
            productComment.setDateSubmit(orderDate);
            productComment.setTotalfee(orderReadHelper.getShippingTotal().multiply(new BigDecimal(100)).intValue());
            productComment.setTotalfee_dec(orderReadHelper.getShippingTotal().intValue());
            productComment.setTrueTotalfee(orderReadHelper.getShippingTotal().multiply(new BigDecimal(100)).intValue());
            productComment.setUsedBalance(0);
            productComment.setUserTruepay(orderReadHelper.getOrderGrandTotal().multiply(new BigDecimal(100)).intValue());
            productComment.setPrice(orderReadHelper.getOrderGrandTotal().multiply(new BigDecimal(100)).intValue());
            productComment.setPrice_dec(orderReadHelper.getOrderGrandTotal().setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue());
            productComment.setDiscount(0);
            productComment.setDiscount_dec(0);
            productComment.setShouldpay(orderReadHelper.getOrderGrandTotal().multiply(new BigDecimal(100)).intValue());
            productComment.setShouldpay_dec(orderReadHelper.getOrderGrandTotal().setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue());
            productComment.setStoreId(Integer.parseInt(orderReadHelper.getProductStoreId()));
            productComment.setPin("JD_284ubc3b41a4");
            productComment.setRemark("所购商品如遇缺货，您需要：其它商品继续配送（缺货商品退款）");
            productComment.setCityId(904);
            productComment.setProvinceId(0);
            if (UtilValidate.isNotEmpty(orderReadHelper.getEarliestShipByDate())) {
                String deliveryTime = UtilFormatOut.formatDateTime(orderReadHelper.getEarliestShipByDate(), "yyyy-MM-dd hh:mm:ss", locale, TimeZone.getDefault());
                String deliveryTimeStr = UtilFormatOut.formatDateTime(orderReadHelper.getEarliestShipByDate(), "yyyy-MM-dd hh:mm", locale, TimeZone.getDefault());
                productComment.setDeliveryTime(deliveryTime);
                productComment.setDeliveryTimeStr(deliveryTimeStr);
            }
            productComment.setPackagingCost(0);
            productComment.setPackagingCost_dec(0);
            productComment.setOrderPlatform("h5");
            if (UtilValidate.isNotEmpty(orderReadHelper.getLatestShipAfterDate())) {
                String arriveTimeStart = UtilFormatOut.formatDateTime(orderReadHelper.getLatestShipAfterDate(), "yyyy-MM-dd hh:mm:ss", locale, TimeZone.getDefault());
                String arriveTimeEnd = UtilFormatOut.formatDateTime(orderReadHelper.getLatestShipAfterDate(), "yyyy-MM-dd hh:mm", locale, TimeZone.getDefault());
                
                productComment.setArriveTimeStart(arriveTimeStart);
                productComment.setArriveTimeEnd(arriveTimeEnd);
            }
            productComment.setTimingArrive(0);
            productComment.setTagTiming("dj_new_cashier;dj_cs;");
            productComment.setCancelSwitch(0);
            productComment.setImSwitch("2");
            productComment.setCommentStatus(2);
            productComment.setServicePhone("4006805065");
            productComment.setPromoCodeAmount(0);
            productComment.setBuyerIp("10.32.192.1");
            productComment.setBuyerCoordType(2);
            productComment.setBuyerLng(118.72889d);
            productComment.setBuyerLat(32.13012d);
            productComment.setOrgCode("74597");
            productComment.setOrderType(1000);
            productComment.setStoreName((String) orderReadHelper.getProductStore().get("storeName"));
            productComment.setDeliveryMan("xxx");
            productComment.setDeliveryManMobile("138444444333");
            productComment.setJdouDiscount(0);
            productComment.setJdouDiscount_dec(0);
            productComment.setCancelSwitchNew(0);
            productComment.setRePurchaseSwitch(0);
            productComment.setAfterSaleSign(0);
            String shippingTotal = UtilFormatOut.formatCurrency(orderReadHelper.getShippingTotal(), "", locale, 2);
            productComment.setFreightRule("本单运费共计" + shippingTotal + "元，包括<br/>·基础运费 " + shippingTotal + "元<br/>");
            productComment.setTs(orderDate);
            productComment.setFreightPrice(orderReadHelper.getShippingTotal().multiply(new BigDecimal(100)).intValue());
            productComment.setPayablePrice(orderReadHelper.getOrderGrandTotal().multiply(new BigDecimal(100)).intValue());
            productComment.setProductDiscount(0);
            productComment.setFreightDiscount(0);
            productComment.setDiscountNew(0);
            productComment.setCarrier(orderReadHelper.getShipToParty().getString("partyId"));
            productComment.setBusinessType(1);
            productComment.setShopDeliveryScore("0");
            productComment.setShopServiceScore("0");
            
            //orderProductList
            List<GenericValue> orderItems = orderReadHelper.getOrderItems();
            
            if (UtilValidate.isNotEmpty(orderItems)) {
                int totalQuantity = 0;
                List<com.yuaoq.yabiz.daojia.model.json.order.productComment.OrderProductList> noCommentProducts = FastList.newInstance();
                List productList = FastList.newInstance();
                String baseUrl = UtilProperties.getMessage("content.properties", "daojia.baseUrl", locale);
                for (int j = 0; j < orderItems.size(); j++) {
                    GenericValue item = orderItems.get(j);
                    com.yuaoq.yabiz.daojia.model.json.order.productComment.OrderProductList product = new com.yuaoq.yabiz.daojia.model.json.order.productComment.OrderProductList();
                    product.setSku(Integer.parseInt(item.getString("productId")));
                    product.setPrice(item.getBigDecimal("unitPrice").intValue());
                    product.setDiscountPrice(0);
                    GenericValue miniProduct = item.getRelatedOne("Product");
                    ProductContentWrapper miniProductContentWrapper = new ProductContentWrapper(dispatcher, miniProduct, locale, "text/html");
                    String mediumImageUrl = miniProductContentWrapper.get("MEDIUM_IMAGE_URL").toString();
                    product.setImgPath(baseUrl + mediumImageUrl);
                    product.setShopId(orderReadHelper.getProductStoreId());
                    product.setNum(item.getBigDecimal("quantity").intValue());
                    product.setName(item.getString("itemDescription"));
                    product.setOrderId(orderId);
                    List<GenericValue> orderReviews = delegator.findByAnd("ProductReview", UtilMisc.toMap("productId", item.getString("productId"), "orderId", orderId, "userLoginId", userLoginId));
                    
                    product.setProductCategory(0);
                    product.setPromotionType(1);
                    if (UtilValidate.isNotEmpty(orderReviews)) {
                        GenericValue orderReview = EntityUtil.getFirst(orderReviews);
                        BigDecimal productRating = orderReview.getBigDecimal("productRating");
                        product.setScore(productRating.intValue());
                        product.setUpVote(1);
                        product.setSkuCommentStatus(2);
                    } else {
                        product.setSkuCommentStatus(1);
                        noCommentProducts.add(product);
                    }
                    productList.add(product);
                }
                if (UtilValidate.isNotEmpty(noCommentProducts)) {
                    productComment.setNoCommentOrderProductList(noCommentProducts);
                }
                productComment.setOrderProductList(productList);
            }
            retMap.put("result", productComment);
            
            result.put("resultData", retMap);
            
        } catch (IOException e) {
            e.printStackTrace();
            result.put("msg", e.getMessage());
            
        } catch (GenericEntityException e) {
            e.printStackTrace();
            result.put("msg", e.getMessage());
        }
        return result;
    }
    
    public static Map<String, Object> DaoJia_OrderSkuBatchComment(DispatchContext dcx, Map<String, ? extends Object> context) {
        Map<String, Object> result = ServiceUtil.returnSuccess();
        Locale locale = (Locale) context.get("locale");
        Map<String, Object> retMap = FastMap.newInstance();
        retMap.put("id", "order.productComment");
        retMap.put("msg", UtilProperties.getMessage("CommonUiLabels", "CommonSuccess", locale));
        retMap.put("code", "0");
        retMap.put("success", true);
        String body = (String) context.get("body");
        OrderComment orderComment = OrderComment.objectFromData(body);
        GenericValue userLogin = (GenericValue) context.get("userLogin");
        String userLoginId = userLogin.getString("userLoginId");
        Delegator delegator = dcx.getDelegator();
        String orderId = orderComment.getOrderId();
        try {
            GenericValue orderHeader = delegator.findByPrimaryKey("OrderHeader", UtilMisc.toMap("orderId", orderId));
            
            int deliveryScore = orderComment.getDeliveryScore();
            int serviceScore = orderComment.getServiceScore();
            if (deliveryScore > 0) {
                GenericValue storeReivew = delegator.makeValue("ProductStoreReview");
                storeReivew.setNextSeqId();
                storeReivew.put("productStoreId", orderHeader.get("productStoreId"));
                storeReivew.put("userLoginId", userLoginId);
                storeReivew.put("statusId", "PRR_PENDING");
                storeReivew.put("postedAnonymous", "N");
                storeReivew.put("postedDateTime", UtilDateTime.nowTimestamp());
                storeReivew.put("productStoreRating", new BigDecimal(deliveryScore));
                storeReivew.put("orderId", orderId);
                storeReivew.put("reviewTypeId", "PRO_SHIP_SPEED");
                storeReivew.create();
            }
            if (serviceScore > 0) {
                GenericValue storeReivew = delegator.makeValue("ProductStoreReview");
                storeReivew.setNextSeqId();
                storeReivew.put("productStoreId", orderHeader.get("productStoreId"));
                storeReivew.put("userLoginId", userLoginId);
                storeReivew.put("statusId", "PRR_PENDING");
                storeReivew.put("postedAnonymous", "N");
                storeReivew.put("postedDateTime", UtilDateTime.nowTimestamp());
                storeReivew.put("productStoreRating", new BigDecimal(serviceScore));
                storeReivew.put("orderId", orderId);
                storeReivew.put("reviewTypeId", "PRO_SHIP_SERVICE");
                storeReivew.create();
            }
            List<OrderComment.Skus> skusList = orderComment.getSkus();
            if (UtilValidate.isNotEmpty(skusList)) {
                for (int i = 0; i < skusList.size(); i++) {
                    OrderComment.Skus skus = skusList.get(i);
                    GenericValue proReivew = delegator.makeValue("ProductReview");
                    proReivew.put("productStoreId", orderHeader.get("productStoreId"));
                    proReivew.put("productId", skus.getSkuId());
                    proReivew.put("userLoginId", userLoginId);
                    proReivew.put("statusId", "PRR_PENDING");
                    proReivew.put("postedAnonymous", "N");
                    proReivew.put("postedDateTime", UtilDateTime.nowTimestamp());
                    proReivew.put("productRating", new BigDecimal(skus.getScore()));
                    proReivew.put("productReview", skus.getComment());
                    proReivew.put("orderId", orderId);
                    proReivew.setNextSeqId();
                    proReivew.create();
                }
            }
            retMap.put("result", true);
        } catch (GenericEntityException e) {
            e.printStackTrace();
            retMap.put("msg", e.getMessage());
        }
        result.put("resultData", retMap);
        return result;
    }
}
