package com.yuaoq.yabiz.eshop.order;

import javolution.util.FastMap;
import org.apache.commons.collections.FastArrayList;
import org.ofbiz.base.util.Debug;
import org.ofbiz.base.util.UtilDateTime;
import org.ofbiz.base.util.UtilMisc;
import org.ofbiz.entity.Delegator;
import org.ofbiz.entity.GenericEntityException;
import org.ofbiz.entity.GenericValue;
import org.ofbiz.service.LocalDispatcher;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.List;
import java.util.Map;

/**
 * Created by tusm on 15/5/30.
 */
public class OrderEvents {

    public static final String module = OrderEvents.class.getName();

    /**
     * 获取订单状态  Add By Changsy
     * @param request
     * @param delegator
     * @param orderId
     * @return
     */
    public static Map<String,Object> getPpaymentMethodType(HttpServletRequest request, Delegator delegator, String orderId) {
        List<GenericValue> orderPaymentPreference=null;
        List<GenericValue> shipments=null;
        List<GenericValue> shipmentPackageContents=null;
        List<GenericValue> orderItems=null;
        List<GenericValue> productAssocs=null;
        List<GenericValue> productAssocs1=null;
        List<GenericValue> productReviews=null;
        Map<String,Object> map=new FastMap<String, Object>();
        try {
            orderPaymentPreference=delegator.findByAnd("OrderPaymentPreference", UtilMisc.toMap("orderId",orderId));
            shipments=delegator.findByAnd("Shipment", UtilMisc.toMap("primaryOrderId",orderId));
            orderItems=delegator.findByAnd("OrderItem", UtilMisc.toMap("orderId",orderId));
        } catch (GenericEntityException e) {
            e.printStackTrace();
        }
        int orderAmount= 0;
        for(int i=0;i<orderItems.size();i++){
            java.math.BigDecimal str=(java.math.BigDecimal) orderItems.get(i).get("quantity");
            orderAmount+=str.intValue();
        }
        if (orderPaymentPreference.size()>0){
            map.put("paymentMethodType",orderPaymentPreference.get(0).get("paymentMethodTypeId"));
            map.put("paymentStatusId",orderPaymentPreference.get(orderPaymentPreference.size()-1).get("statusId"));
            if (shipments.size()<=0){
                map.put("shipmentStatusId","none");
            }else{
                int temp=0;
                int shipcount = 0;
                int shipmentAmount=0;
                for(int i=0;i<shipments.size();i++){
                    if ("SHIPMENT_DELIVERED".equals(shipments.get(i).get("statusId"))){
                        temp++;
                    }else if("SHIPMENT_SHIPPED".equals(shipments.get(i).get("statusId"))){
                        shipcount++;
                    }
                    try {
                        shipmentPackageContents=delegator.findByAnd("ShipmentPackageContent", UtilMisc.toMap("shipmentId",shipments.get(i).get("shipmentId")));
                    } catch (GenericEntityException e) {
                        e.printStackTrace();
                    }
                    if("SHIPMENT_SHIPPED".equals(shipments.get(i).get("statusId"))){
                        for(int j=0;j<shipmentPackageContents.size();j++){
                            java.math.BigDecimal str=(java.math.BigDecimal) shipmentPackageContents.get(j).get("quantity");
                            shipmentAmount+=str.intValue();
                        }
                    }
                }
                if (temp>0){
                    map.put("shipmentStatusId","delivered");
                }else {
                    if(shipcount>0){
                        map.put("shipmentStatusId", "shipped");
                    }else{
                        map.put("shipmentStatusId","none");
                    }
                    map.put("orderAmount",orderAmount);
                    map.put("shipmentAmount",shipmentAmount);
                }
            }
        }else{
            map.put("shipmentStatusId","none");
            map.put("orderAmount",orderAmount);
            map.put("shipmentAmount",0);
        }
        int index=0;
        for(int i=0;i<orderItems.size();i++){
            try {
                productAssocs=delegator.findByAnd("ProductAssoc", UtilMisc.toMap("productIdTo",orderItems.get(i).get("productId"),"productAssocTypeId","PRODUCT_CONF"));
                productAssocs1=delegator.findByAnd("ProductAssoc", UtilMisc.toMap("productIdTo",orderItems.get(i).get("productId"),"productAssocTypeId","PRODUCT_VARIANT"));
            } catch (GenericEntityException e) {
                e.printStackTrace();
            }
            if(productAssocs.size()>0){
                try {
                    productReviews=delegator.findByAnd("ProductReview", UtilMisc.toMap("orderId",orderId,"productId",productAssocs.get(0).get("productId")));
                } catch (GenericEntityException e) {
                    e.printStackTrace();
                }
            }else if(productAssocs1.size()>0){
                try {
                    productReviews=delegator.findByAnd("ProductReview", UtilMisc.toMap("orderId",orderId,"productId",productAssocs1.get(0).get("productId")));
                } catch (GenericEntityException e) {
                    e.printStackTrace();
                }
            }else{
                try {
                    productReviews=delegator.findByAnd("ProductReview", UtilMisc.toMap("orderId",orderId,"productId",orderItems.get(i).get("productId")));
                } catch (GenericEntityException e) {
                    e.printStackTrace();
                }
            }
            if(productReviews.size()>0){
                index++;
            }
        }
        if (index==orderItems.size()){
            map.put("reviewStatusId","Y");
        }else {
            map.put("reviewStatusId","N");
        }
        return map;
    }

    /**
     * 确认收货  Add By Changsy 2015-4-3 14:41:49
     * @param request
     * @param response
     * @return
     */
    public static String receiveProduct(HttpServletRequest request, HttpServletResponse response) {
        Delegator delegator = (Delegator) request.getAttribute("delegator");
        String orderId = request.getParameter("orderId");
        List<GenericValue> shipments=null;
        try {
            shipments=delegator.findByAnd("Shipment", UtilMisc.toMap("primaryOrderId", orderId));
        } catch (GenericEntityException e) {
            e.printStackTrace();
            return "error";
        }
        for(int i=0;i<shipments.size();i++){
            shipments.get(i).set("statusId","SHIPMENT_DELIVERED");
        }
        try {
            delegator.storeAll(shipments);
        } catch (GenericEntityException e) {
            e.printStackTrace();
            return "error";
        }
        request.setAttribute("success", "SUCCESS");
        return "success";
    }

    /**
     * 领取优惠券  Add By Changsy
     * @param request
     * @param response
     * @return
     */
    public static String createProductPromoCodeParty(HttpServletRequest request, HttpServletResponse response) {

        Delegator delegator = (Delegator) request.getAttribute("delegator");
        LocalDispatcher dispatcher = (LocalDispatcher)request.getAttribute("dispatcher");
        String productPromoId= request.getParameter("productPromoId");
        String partyId= request.getParameter("partyId");
        List<GenericValue> promoCodes= null;
        List<GenericValue> promoCodePartys = null;
        GenericValue promoCodeParty = delegator.makeValue("ProductPromoCodeParty");
        try {
            promoCodes = delegator.findByAnd("ProductPromoCode", UtilMisc.toMap("productPromoId", productPromoId));
        } catch (GenericEntityException e) {
            request.setAttribute("_ERROR_MESSAGE_", e.getMessage());
            return "error";
        }
        for(GenericValue promoCode:promoCodes){
            try {
                promoCodePartys = delegator.findByAnd("ProductPromoCodeParty", UtilMisc.toMap("productPromoCodeId", promoCode.get("productPromoCodeId")));
            } catch (GenericEntityException e) {
                request.setAttribute("_ERROR_MESSAGE_", e.getMessage());
                return "error";
            }
            if(promoCodePartys.size()==0){
                promoCodeParty.set("productPromoCodeId",promoCode.get("productPromoCodeId"));
                promoCodeParty.set("partyId",partyId);
                try {
                    promoCodeParty.create();
                    break;
                } catch (GenericEntityException e) {
                    request.setAttribute("_ERROR_MESSAGE_", e.getMessage());
                    return "error";
                }
            }
        }
        return "success";
    }




    /**
     * 保存信息
     * @return
     * @throws org.ofbiz.service.GenericServiceException
     */
    public static String saveMessage(HttpServletRequest request, HttpServletResponse response) {
        Delegator delegator = (Delegator) request.getAttribute("delegator");
        GenericValue userLogin = (GenericValue) request.getSession().getAttribute("userLogin");
        String nowToken = (String) request.getSession().getAttribute("token");
        String loginIdToken = "";
        GenericValue person=null;
        List<GenericValue> orderItems=null;
        GenericValue orderAddition=null;
        if(userLogin!=null){
            loginIdToken = (String) userLogin.get("token");
            try {
                person = delegator.findByPrimaryKey("Person", UtilMisc.toMap("partyId",userLogin.get("partyId")));
            } catch (GenericEntityException e) {
                e.printStackTrace();
            }
        }
        Map<String,String> map=null;
        map = (Map<String, String>) request.getSession().getAttribute("messageMap");
        String userPartyId = map.get("userPartyId");
        String hotelPartyId = map.get("hotelPartyId");
        String orderId = map.get("orderId");
        String operaterId = map.get("operaterId");
        String operation = map.get("operation");
        String messageId = delegator.getNextSeqId("Message");
        GenericValue message = delegator.makeValue("Message");
        message.set("messageId",messageId);
        message.set("userPartyId",userPartyId);
        message.set("hotelPartyId",hotelPartyId);
        message.set("orderId",orderId);
        message.set("operaterId",operaterId);
        message.set("operation", operation);

        try {
            orderItems=delegator.findByAnd("OrderItem", UtilMisc.toMap("orderId", orderId));
        } catch (GenericEntityException e) {
            e.printStackTrace();
        }
        try {
            orderAddition=delegator.findByPrimaryKey("OrderAdditions", UtilMisc.toMap("orderId",orderId));
        } catch (GenericEntityException e) {
            e.printStackTrace();
        }
        try {
            message.create();
            request.getSession().removeAttribute("messageMap");
            String lastName="";
            if(person.get("lastName")!=null&&person.get("lastName")!=""){
                lastName = (String) person.get("lastName");
            }
            String firstName="";
            if(person.get("firstName")!=null&&person.get("firstName")!=""){
                firstName = (String) person.get("firstName");
            }
            String itemDescriPtion="";
            if(orderItems.get(0).get("itemDescriPtion")!=null&&orderItems.get(0).get("itemDescriPtion")!=""){
                itemDescriPtion = (String) orderItems.get(0).get("itemDescriPtion");
            }
            DateFormat sdf = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
            String appointDate="";
            if(orderAddition.get("appointDate")!=null&&orderAddition.get("appointDate")!=""){
                appointDate = sdf.format(orderAddition.get("appointDate"));
            }
            String content= "尊敬的"+lastName+firstName+"，您已在"+itemDescriPtion+"预订酒桌，"+"，时间："+appointDate;
            if(nowToken!=null&&nowToken!=""){
//                XinGe.pushToAndriod("预约成功", content,nowToken);
            }
            if(loginIdToken!=null&&loginIdToken!=""){
//                XinGe.pushToAndriod("预约成功", content,loginIdToken);
            }
        } catch (GenericEntityException e) {
            String errMsg = "myappServices.saveMessage.save_Message_error: " + e.toString();
            Debug.logError(e, errMsg, module);
            request.setAttribute("_ERROR_MESSAGE_", errMsg);
            return "error";

        }
        return "success";
    }

    /**
     * 创建预约单  Add By Changsy
     * @param request
     * @param response
     * @return
     */
    public static String createOrderPlan(HttpServletRequest request, HttpServletResponse response) {
        Delegator delegator = (Delegator) request.getAttribute("delegator");

        String planId = null;
        try {
            planId = delegator.getNextSeqId("PersonOrderPlan");
        } catch (IllegalArgumentException e) {
            request.setAttribute("errorMessage","N");
            return "error";
        }
        String comment = request.getParameter("comment");
        String personName = request.getParameter("personName");
        String mobile = request.getParameter("mobile");
        java.sql.Timestamp planDate =java.sql.Timestamp.valueOf(request.getParameter("planDate"));
        String facilityId = request.getParameter("facilityId");
        String partyId = request.getParameter("partyId");
        String salePartyId = request.getParameter("salePartyId");
        String status = request.getParameter("status");
        GenericValue personOrderPlan=delegator.makeValue("PersonOrderPlan");
        personOrderPlan.set("id",planId);
        personOrderPlan.set("planId",planId);
        personOrderPlan.set("comment",comment);
        personOrderPlan.set("personName",personName);
        personOrderPlan.set("mobile",mobile);
        personOrderPlan.set("planDate",planDate);
        personOrderPlan.set("facilityId",facilityId);
        personOrderPlan.set("partyId",partyId);
        personOrderPlan.set("salePartyId",salePartyId);
        personOrderPlan.set("status",status);
        personOrderPlan.set("createUserLoginId",partyId);
        try {
            delegator.create(personOrderPlan);
        } catch (GenericEntityException e) {
            request.setAttribute("errorMessage","N");
            return "error";
        }
        request.setAttribute("errorMessage","Y");
        return "success";
    }
    /**
     * 获取可配置产品配置 Add By Changsy
     * @param request
     * @param delegator
     * @param orderId
     * @param orderItemSeqId
     * @param productId
     * @return
     */
    public static List<String> getConfigProducts(HttpServletRequest request, Delegator delegator, String orderId, String orderItemSeqId, String productId) {
        List<GenericValue> workOrderItemFulfillments=null;
        List<GenericValue> workEfforts=null;
        List<GenericValue> workEffortGoodStandards=null;
        List<GenericValue> productConfigs=null;
        List<GenericValue> productConfigProducts=null;
        List<GenericValue> productConfigOptions=null;
        List<String> goodsNames=new FastArrayList();
        try {
            productConfigs=delegator.findByAnd("ProductConfig", UtilMisc.toMap("productId", productId));
        } catch (GenericEntityException e) {
            e.printStackTrace();
            return goodsNames;
        }

        try {
            workOrderItemFulfillments=delegator.findByAnd("WorkOrderItemFulfillment", UtilMisc.toMap("orderId", orderId, "orderItemSeqId", orderItemSeqId));
        } catch (GenericEntityException e) {
            e.printStackTrace();
            return goodsNames;
        }
        if (workOrderItemFulfillments.size()>0){
            try {
                workEfforts=delegator.findByAnd("WorkEffort", UtilMisc.toMap("workEffortParentId", workOrderItemFulfillments.get(0).get("workEffortId")));
            } catch (GenericEntityException e) {
                e.printStackTrace();
                return goodsNames;
            }
        }else{
            return goodsNames;
        }
        if (workEfforts.size()>0){
            try {
                workEffortGoodStandards=delegator.findByAnd("WorkEffortGoodStandard", UtilMisc.toMap("workEffortId", workEfforts.get(0).get("workEffortId")));
            } catch (GenericEntityException e) {
                e.printStackTrace();
                return goodsNames;
            }
        }else{
            return goodsNames;
        }
        if (workEffortGoodStandards.size()>0){
            for(int i=0;i<workEffortGoodStandards.size();i++){
                for(int j=0;j<productConfigs.size();j++){
                    try {
                        productConfigProducts=delegator.findByAnd("ProductConfigProduct", UtilMisc.toMap("configItemId", productConfigs.get(j).get("configItemId")));
                    } catch (GenericEntityException e) {
                        e.printStackTrace();
                        return null;
                    }
                    for(int k=0;k<productConfigProducts.size();k++){
                        if(workEffortGoodStandards.get(i).get("productId").equals(productConfigProducts.get(k).get("productId"))){
                            try {
                                productConfigOptions=delegator.findByAnd("ProductConfigOption", UtilMisc.toMap("configItemId", productConfigProducts.get(k).get("configItemId"),"configOptionId", productConfigProducts.get(k).get("configOptionId")));
                            } catch (GenericEntityException e) {
                                e.printStackTrace();
                                return null;
                            }
                            if(productConfigOptions.size()>0){
                                goodsNames.add((String) productConfigOptions.get(0).get("configOptionName"));
                            }
                        }
                    }
                }
            }
        }else{
            return goodsNames;
        }
        return goodsNames;
    }

    /**
     * 获取运输状态  Add By Changsy
     * @param request
     * @param delegator
     * @param orderId
     * @param productId
     * @return
     */
    public static Map<String,Object> getShipmentStatus(HttpServletRequest request, Delegator delegator, String orderId, String productId) {
        List<GenericValue> orderShipments=null;
        List<GenericValue> shipmentItems=null;
        List<GenericValue> shipments=null;
        Map<String,Object> map=new FastMap<String, Object>();
        try {
            orderShipments=delegator.findByAnd("OrderShipment", UtilMisc.toMap("orderId",orderId));
        } catch (GenericEntityException e) {
            e.printStackTrace();
        }
        int index=0;
        int temp=0;
        for(int i=0;i<orderShipments.size();i++){
            try {
                shipmentItems=delegator.findByAnd("ShipmentItem", UtilMisc.toMap("shipmentId", orderShipments.get(i).get("shipmentId"), "productId", productId));
            } catch (GenericEntityException e) {
                e.printStackTrace();
            }
            if (shipmentItems.size()>0){
                try {
                    shipments=delegator.findByAnd("Shipment", UtilMisc.toMap("shipmentId", shipmentItems.get(0).get("shipmentId")));
                } catch (GenericEntityException e) {
                    e.printStackTrace();
                }
                map.put("shipDate",shipments.get(0).get("createdDate"));
                for(int j=0;j<shipments.size();j++){
                    if("SHIPMENT_DELIVERED".equals(shipments.get(j).get("statusId"))){
                        index++;
                    }
                }
                temp+=shipments.size();
            }
        }
        if(index==temp && shipments!=null){
            map.put("receiveDate",shipments.get(shipments.size()-1).get("lastModifiedDate"));
        }
        return map;
    }



    // Add by zhajh at 2015/1/9 订单关闭处理 Begin
    /**
     * 订单状态关闭处理
     * @param request
     * @param response
     * @return
     */
    public static String setOrderStatusClose(HttpServletRequest request, HttpServletResponse response)  {
        Delegator delegator = (Delegator) request.getAttribute("delegator");
        // check the userLogin object
        GenericValue userLogin = (GenericValue) request.getSession().getAttribute("userLogin");
        // 返回值
        String rtnStatus= "success";
        // 参数的取得
        // 订单状态
        String statusId = request.getParameter("statusId");
        // 订单ID
        String orderId = request.getParameter("orderId");
        try {
            GenericValue orderHeader = delegator.findByPrimaryKey("OrderHeader", UtilMisc.toMap("orderId", orderId));

            if (orderHeader == null) {
                rtnStatus="error";
                request.setAttribute("rtnStatus", rtnStatus);
                return rtnStatus;
            }

            // update the current status
            orderHeader.set("statusId", statusId);

            // now create a status change
            GenericValue orderStatus = delegator.makeValue("OrderStatus");
            orderStatus.put("orderStatusId", delegator.getNextSeqId("OrderStatus"));
            orderStatus.put("statusId", statusId);
            orderStatus.put("orderId", orderId);
            orderStatus.put("statusDatetime", UtilDateTime.nowTimestamp());
            orderStatus.put("statusUserLogin", userLogin.getString("userLoginId"));

            orderHeader.store();
            orderStatus.create();

        } catch (GenericEntityException e) {
            e.printStackTrace();
            rtnStatus="error";
            request.setAttribute("rtnStatus", rtnStatus);
            return rtnStatus;
        }
        request.setAttribute("rtnStatus", rtnStatus);

        return rtnStatus;
    }
    // Add by zhajh at 2015/1/9 订单关闭处理  End



}
