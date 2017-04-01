package com.yuaoq.yabiz.service.local.order;

import javolution.util.FastList;
import javolution.util.FastMap;
import org.ofbiz.base.util.UtilDateTime;
import org.ofbiz.base.util.UtilMisc;
import org.ofbiz.base.util.UtilProperties;
import org.ofbiz.base.util.UtilValidate;
import org.ofbiz.entity.Delegator;
import org.ofbiz.entity.GenericEntityException;
import org.ofbiz.entity.GenericValue;
import org.ofbiz.entity.condition.EntityCondition;
import org.ofbiz.entity.condition.EntityOperator;
import org.ofbiz.entity.util.EntityUtil;
import org.ofbiz.order.order.OrderReadHelper;
import org.ofbiz.service.DispatchContext;
import org.ofbiz.service.ServiceUtil;

import java.math.BigDecimal;
import java.util.*;

/**
 * Created by changsy on 15/12/19.
 */
public class OrderServices {

    /**
     * ORDER_COMPLETED,ORDER_CANCELLED,ORDER_CREATED
     * paymentStatusId.equals("PAYMENT_NOT_RECEIVED')
     * else reviewOrders
     *
     * @param dxt
     * @param context
     * @return
     */

    public static Map<String, Object> queryOrderHistory(DispatchContext dxt, Map<String, ? extends Object> context) {
        String loginId = (String) context.get("loginId");
        Map result = ServiceUtil.returnSuccess();
        Delegator delegator = dxt.getDelegator();
        GenericValue userLogin = null;
        List<GenericValue> orderHeaderList = null;
        Locale locale = (Locale)context.get("locale");
        
        List payOrders = FastList.newInstance();
        List finishOrders = FastList.newInstance();
        List cancelOrders = FastList.newInstance();
        List shipOrders = FastList.newInstance();
        List reviewOrders = FastList.newInstance();

        try {
            userLogin = delegator.findByPrimaryKey("UserLogin", UtilMisc.toMap("userLoginId", loginId));
            List conditions = FastList.newInstance();

            Calendar calendar = Calendar.getInstance();//创建实例
            calendar.setTime(UtilDateTime.nowDate());
            calendar.add(Calendar.MONTH, -3);//三个月
            Date date = calendar.getTime();//三个月
            conditions.add(EntityCondition.makeCondition("orderDate", EntityOperator.GREATER_THAN, UtilDateTime.toTimestamp(date)));
            conditions.add(EntityCondition.makeCondition("statusId", EntityOperator.NOT_EQUAL, "ORDER_REJECTED"));

            List<GenericValue> orderRoleCollection = delegator.findByAnd("OrderRole", UtilMisc.toMap("partyId", userLogin.get("partyId"), "roleTypeId", "PLACING_CUSTOMER"));
            orderHeaderList = EntityUtil.orderBy(EntityUtil.filterByAnd(EntityUtil.getRelated("OrderHeader", orderRoleCollection), conditions), UtilMisc.toList("orderDate DESC"));

        } catch (GenericEntityException e) {
            ServiceUtil.returnError(e.getMessage());
            e.printStackTrace();
        }

        if (orderHeaderList != null) {
            for (int i = 0; i < orderHeaderList.size(); i++) {
                GenericValue orderHeader = orderHeaderList.get(i);

                String orderId = (String) orderHeader.get("orderId");
                OrderReadHelper orderReadHelper = new OrderReadHelper(orderHeader);
                List<GenericValue> orderItems = orderReadHelper.getOrderItems();
                List<GenericValue> orderAdjustments = orderReadHelper.getAdjustments();
                List<GenericValue> orderHeaderAdjustments = orderReadHelper.getOrderHeaderAdjustments();
                BigDecimal orderSubTotal = orderReadHelper.getOrderItemsSubTotal();
                List<GenericValue> orderItemShipGroups = orderReadHelper.getOrderItemShipGroups();


                BigDecimal orderShippingTotal = OrderReadHelper.getAllOrderItemsAdjustmentsTotal(orderItems, orderAdjustments, false, false, true);
                orderShippingTotal = orderShippingTotal.add(OrderReadHelper.calcOrderAdjustments(orderHeaderAdjustments, orderSubTotal, false, false, true));

                BigDecimal orderTaxTotal = OrderReadHelper.getAllOrderItemsAdjustmentsTotal(orderItems, orderAdjustments, false, true, false);
                orderTaxTotal = orderTaxTotal.add(OrderReadHelper.calcOrderAdjustments(orderHeaderAdjustments, orderSubTotal, false, true, false));


                List<GenericValue> orderPaymentPreferences = null;
                try {

                    orderPaymentPreferences = EntityUtil.filterByAnd(orderHeader.getRelated("OrderPaymentPreference"),UtilMisc.toList(EntityCondition.makeCondition("statusId", EntityOperator.NOT_EQUAL, "PAYMENT_CANCELLED")));
                } catch (GenericEntityException e) {
                    ServiceUtil.returnError(e.getMessage());
                    e.printStackTrace();
                }
                Map orderMap = FastMap.newInstance();
                if (orderPaymentPreferences != null && (!orderPaymentPreferences.isEmpty())) {
                    GenericValue orderPaymentPreference = EntityUtil.getFirst(orderPaymentPreferences);

                    orderMap.put("paymentStatus", orderPaymentPreference.get("statusId"));
                }
                orderMap.put("orderId", orderId);
                List<Map> orderItemList = FastList.newInstance();
                if (orderItems != null) {
                    for (int j = 0; j < orderItems.size(); j++) {
                        GenericValue orderItem = orderItems.get(j);


                        Map orderItemMap = FastMap.newInstance();
                        BigDecimal itemTotalAmount = orderReadHelper.getOrderItemTotal(orderItem);
                        orderItemMap.put("totalAmount", itemTotalAmount);
                        orderItemMap.put("description", orderItem.get("itemDescription"));
                        orderItemMap.put("quantity", orderItem.get("quantity"));
                        orderItemMap.put("productId", orderItem.get("productId"));
                        GenericValue product = null;
                        try {
                            product = delegator.findByPrimaryKey("Product", UtilMisc.toMap("productId", orderItem.get("productId")));
                            orderItemMap.put("mediumImageUrl", product.get("mediumImageUrl"));
                            orderItemMap.put("productTypeId", product.get("productTypeId"));
                            orderItemMap.put("unitPrice", orderItem.get("unitPrice"));
                            orderItemMap.put("unitListPrice", orderItem.get("unitListPrice"));
                            orderItemMap.put("adjustmentsTotal", orderReadHelper.getOrderItemAdjustmentsTotal(orderItem));
                            orderItemList.add(orderItemMap);
                        } catch (GenericEntityException e) {
                            ServiceUtil.returnError(e.getMessage());
                            e.printStackTrace();
                        }


                    }
                }

                List<GenericValue> headerAdjustmentsToShow = orderReadHelper.getOrderHeaderAdjustmentsToShow();
                if (headerAdjustmentsToShow!=null) {
                    List<Map> adjustments =FastList.newInstance();
                    for (int j = 0; j < headerAdjustmentsToShow.size(); j++) {
                        GenericValue orderHeaderAdjustment = headerAdjustmentsToShow.get(j);
                        Map adjustmentMap =FastMap.newInstance();
                        adjustmentMap.put("adjustmentType", orderReadHelper.getAdjustmentType(orderHeaderAdjustment));
                        adjustmentMap.put("adjustmentTotal", orderReadHelper.getOrderAdjustmentTotal(orderHeaderAdjustment));
                        adjustments.add(adjustmentMap);
                    }
                    orderMap.put("headerAdjustments", adjustments);
                }
                
                orderMap.put("orderDate",orderHeader.get("orderDate"));
                orderMap.put("orderItems", orderItemList);
                orderMap.put("orderAdjustments", orderAdjustments);
                orderMap.put("orderHeaderAdjustments", orderHeaderAdjustments);
                orderMap.put("orderSubTotal", orderSubTotal);
                orderMap.put("orderItemShipGroups", orderItemShipGroups);

                orderMap.put("currencyUomId", orderReadHelper.getCurrency());

                orderMap.put("orderShippingTotal", orderShippingTotal);
                orderMap.put("orderTaxTotal", orderTaxTotal);
                orderMap.put("orderGrandTotal", OrderReadHelper.getOrderGrandTotal(orderItems, orderAdjustments));

                orderMap.put("orderStatus", orderHeader.get("statusId"));
                orderMap.put("orderStatusName",  orderHeader.get("statusId",locale));
                String statusId = (String)orderHeader.get("statusId");
                String paymentStatusId = (String) orderMap.get("paymentStatus");
                if(paymentStatusId!=null) {
                    //未付费并且订单状态不为取消状态
                    if (paymentStatusId.equals("PAYMENT_NOT_RECEIVED") && (!statusId.equals("ORDER_CANCELLED"))) {
                        payOrders.add(orderMap);
                    } else if (statusId.equals("ORDER_CANCELLED")) {
                        cancelOrders.add(orderMap);
                    } else if (statusId.equals("ORDER_CREATED") || statusId.equals("ORDER_APPROVED") || statusId.equals("ORDER_COMPLETED")) {
                        List<GenericValue> sorderHeaderList = null;
                        try {
                            sorderHeaderList = delegator.findByAnd("Shipment",UtilMisc.toMap("primaryOrderId",orderId));
                            if (UtilValidate.isNotEmpty(sorderHeaderList)) {
                                //待收货不为: SHIPMENT_CANCELLED,SHIPMENT_DELIVERED,SHIPMENT_CANCELLED
                                for (int k = 0; k < sorderHeaderList.size(); k++) {
                                    GenericValue shipment = sorderHeaderList.get(k);
                                    if ( shipment.get("statusId").equals("SHIPMENT_DELIVERED")) {
                                        List<GenericValue> porderHeaderList = null;
                                        try {
                                            porderHeaderList = delegator.findByAnd("ProductReview",UtilMisc.toMap("orderId",orderId,"userLoginId", userLogin.get("userLoginId")));
                                            if (UtilValidate.isEmpty(porderHeaderList)) {
                                                reviewOrders.add(orderMap); //待评价，需要完善
                                            }else{
                                                //已完成状态: 订单状态为已经完成,并且已经评价,已经已收货,已经支付
                                                finishOrders.add(orderMap);
                                            }
        
                                        } catch (GenericEntityException e) {
                                            e.printStackTrace();
                                        }
                                        
                                    }else if(!shipment.get("statusId").equals("SHIPMENT_CANCELLED")){
                                        //正在shipping
                                        if (orderMap.get("orderId").equals(shipment.get("primaryOrderId"))) {
                                            shipOrders.add(orderMap);
                                        }
                                    }
                                }
                            }else{
                                //还没有shipp记录
                                shipOrders.add(orderMap);
                            }

                        } catch (GenericEntityException e) {
                            e.printStackTrace();
                        }
                    }
                }
//            orders.add(orderMap);
            }
        }
    
    //println "orders = $orders"
     
        result.put("payOrders", payOrders);
        result.put("finishOrders", finishOrders);
        result.put("cancelOrders", cancelOrders);
        result.put("shipOrders", shipOrders);
        result.put("reviewOrders", reviewOrders);
        return result;
    }
}
