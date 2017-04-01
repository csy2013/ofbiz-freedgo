package com.yuaoq.yabiz.daojia.model.json.order.orderlist;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import org.json.JSONException;
import org.json.JSONObject;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by changsy on 16/9/19.
 */
public class Order {
    
    /**
     * orderId : 100001039232481
     * pin : JD_284ubc3b41a4
     * storeId : 10061753
     * storeName : 农丰水果-丽岛路店
     * orderState : 20020
     * rePurchaseSwitch : 1
     * deleteSwitch : 1
     * commentStatus : 3
     * isWaimaiOrder : 0
     * productTotalNumStr : 共1件
     * paymentType : 4
     * showPay : 0
     * orgCode : 72384
     * deliveryTime : 2016-08-20 18:22:00
     * dateSubmit : 2016-08-20 16:22:35
     * dateSubmitStr : 08-20 16:22
     * productTotalPrice : ¥63.00
     * orderListShowTrack : 0
     * orderStateMap : {"stateId":120,"stateTitle":"订单已取消","stateDesc":"您的订单已取消成功。","stateTime":"08-20 16:24","stateIcon":"4"}
     * mainOrderStateMap : {"orderState":"4","orderStateName":"已取消","orderColor":"#999999"}
     * realPay : ¥63.00
     * buyerLng : 118.72889
     * buyerLat : 32.13012
     * isSowMap : 0
     * deliveryType : 0
     * deliveryTypeDesc : 达达专送
     * cancelSwitchNew : 0
     * productList : [{"sku":2000373353,"price":6800,"discountPrice":0,"imgPath":"https://img10.360buyimg.com/n7/jfs/t1558/133/1042696402/237823/ca3e48d2/55b89b70N15b4bac3.jpg","shopId":"10061753","num":1,"name":"新西兰绿果礼盒16个/盒","orderId":100001039232481,"skuCommentStatus":0,"productCategory":0,"promotionType":1}]
     * orderType : 10000
     * productTotalNum : 0
     */
    
    public String orderId;
    public String pin;
    public int storeId;
    public String storeName;
    public int orderState;
    public int rePurchaseSwitch;
    public int deleteSwitch;
    public int commentStatus;
    public int isWaimaiOrder;
    public String productTotalNumStr;
    public int paymentType;
    public int showPay;
    public String orgCode;
    public String deliveryTime;
    public String dateSubmit;
    public String dateSubmitStr;
    public String productTotalPrice;
    public int orderListShowTrack;
    public long payEndTime;
    /**
     * stateId : 120
     * stateTitle : 订单已取消
     * stateDesc : 您的订单已取消成功。
     * stateTime : 08-20 16:24
     * stateIcon : 4
     */
    
    public OrderStateMap orderStateMap;
    /**
     * orderState : 4
     * orderStateName : 已取消
     * orderColor : #999999
     */
    
    public MainOrderStateMap mainOrderStateMap;
    public String realPay;
    public double buyerLng;
    public double buyerLat;
    public int isSowMap;
    public String deliveryType;
    public String deliveryTypeDesc;
    public int cancelSwitchNew;
    public int orderType;
    public int productTotalNum;
    /**
     * sku : 2000373353
     * price : 6800
     * discountPrice : 0
     * imgPath : https://img10.360buyimg.com/n7/jfs/t1558/133/1042696402/237823/ca3e48d2/55b89b70N15b4bac3.jpg
     * shopId : 10061753
     * num : 1
     * name : 新西兰绿果礼盒16个/盒
     * orderId : 100001039232481
     * skuCommentStatus : 0
     * productCategory : 0
     * promotionType : 1
     */
    
    public List<ProductList> productList;
    public Order(String orderId, String pin, int storeId, String storeName, int rePurchaseSwitch, int deleteSwitch, int isWaimaiOrder, String orgCode, String productTotalPrice, int orderListShowTrack, String realPay, double buyerLng, double buyerLat, int isSowMap, int cancelSwitchNew, int orderType, int productTotalNum) {
        this.orderId = orderId;
        this.pin = pin;
        this.storeId = storeId;
        this.storeName = storeName;
        this.rePurchaseSwitch = rePurchaseSwitch;
        this.deleteSwitch = deleteSwitch;
        
        this.isWaimaiOrder = isWaimaiOrder;
        this.orgCode = orgCode;
        this.productTotalPrice = productTotalPrice;
        this.orderListShowTrack = orderListShowTrack;
        this.realPay = realPay;
        this.buyerLng = buyerLng;
        this.buyerLat = buyerLat;
        this.isSowMap = isSowMap;
        this.cancelSwitchNew = cancelSwitchNew;
        this.orderType = orderType;
        this.productTotalNum = productTotalNum;
    }
    public Order(String orderId, String pin, int storeId, String storeName, int orderState, int rePurchaseSwitch, int deleteSwitch, int commentStatus, int isWaimaiOrder, String productTotalNumStr, int paymentType, int showPay, String orgCode, String deliveryTime, String dateSubmit, String dateSubmitStr, String productTotalPrice, int orderListShowTrack, String realPay, double buyerLng, double buyerLat, int isSowMap, String deliveryType, String deliveryTypeDesc, int cancelSwitchNew, int orderType, int productTotalNum) {
        this.orderId = orderId;
        this.pin = pin;
        this.storeId = storeId;
        this.storeName = storeName;
        this.orderState = orderState;
        this.rePurchaseSwitch = rePurchaseSwitch;
        this.deleteSwitch = deleteSwitch;
        this.commentStatus = commentStatus;
        this.isWaimaiOrder = isWaimaiOrder;
        this.productTotalNumStr = productTotalNumStr;
        this.paymentType = paymentType;
        this.showPay = showPay;
        this.orgCode = orgCode;
        this.deliveryTime = deliveryTime;
        this.dateSubmit = dateSubmit;
        this.dateSubmitStr = dateSubmitStr;
        this.productTotalPrice = productTotalPrice;
        this.orderListShowTrack = orderListShowTrack;
        this.realPay = realPay;
        this.buyerLng = buyerLng;
        this.buyerLat = buyerLat;
        this.isSowMap = isSowMap;
        this.deliveryType = deliveryType;
        this.deliveryTypeDesc = deliveryTypeDesc;
        this.cancelSwitchNew = cancelSwitchNew;
        this.orderType = orderType;
        this.productTotalNum = productTotalNum;
    }
    public Order(String orderId, String pin, int storeId, String storeName, int orderState, int rePurchaseSwitch, int deleteSwitch, int commentStatus, int isWaimaiOrder, String productTotalNumStr, int paymentType, int showPay, String orgCode, String deliveryTime, String dateSubmit, String dateSubmitStr, String productTotalPrice, int orderListShowTrack, OrderStateMap orderStateMap, MainOrderStateMap mainOrderStateMap, String realPay, double buyerLng, double buyerLat, int isSowMap, String deliveryType, String deliveryTypeDesc, int cancelSwitchNew, int orderType, int productTotalNum, List<ProductList> productList) {
        this.orderId = orderId;
        this.pin = pin;
        this.storeId = storeId;
        this.storeName = storeName;
        this.orderState = orderState;
        this.rePurchaseSwitch = rePurchaseSwitch;
        this.deleteSwitch = deleteSwitch;
        this.commentStatus = commentStatus;
        this.isWaimaiOrder = isWaimaiOrder;
        this.productTotalNumStr = productTotalNumStr;
        this.paymentType = paymentType;
        this.showPay = showPay;
        this.orgCode = orgCode;
        this.deliveryTime = deliveryTime;
        this.dateSubmit = dateSubmit;
        this.dateSubmitStr = dateSubmitStr;
        this.productTotalPrice = productTotalPrice;
        this.orderListShowTrack = orderListShowTrack;
        this.orderStateMap = orderStateMap;
        this.mainOrderStateMap = mainOrderStateMap;
        this.realPay = realPay;
        this.buyerLng = buyerLng;
        this.buyerLat = buyerLat;
        this.isSowMap = isSowMap;
        this.deliveryType = deliveryType;
        this.deliveryTypeDesc = deliveryTypeDesc;
        this.cancelSwitchNew = cancelSwitchNew;
        this.orderType = orderType;
        this.productTotalNum = productTotalNum;
        this.productList = productList;
    }
    
    public static Order objectFromData(String str) {
        
        return new Gson().fromJson(str, Order.class);
    }
    
    public static Order objectFromData(String str, String key) {
        
        try {
            JSONObject jsonObject = new JSONObject(str);
            
            return new Gson().fromJson(jsonObject.getString(str), Order.class);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        
        return null;
    }
    
    public static List<Order> arrayOrderFromData(String str) {
        
        Type listType = new TypeToken<ArrayList<Order>>() {
        }.getType();
        
        return new Gson().fromJson(str, listType);
    }
    
    
}
