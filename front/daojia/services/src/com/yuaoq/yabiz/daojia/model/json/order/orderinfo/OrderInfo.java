package com.yuaoq.yabiz.daojia.model.json.order.orderinfo;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.yuaoq.yabiz.daojia.model.json.order.orderlist.MainOrderStateMap;
import com.yuaoq.yabiz.daojia.model.json.order.orderlist.OrderStateMap;
import com.yuaoq.yabiz.daojia.model.json.order.orderstate.OrderStateList;
import org.json.JSONException;
import org.json.JSONObject;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

/**
 * Created by changsy on 16/9/22.
 */
public class OrderInfo {
    
    
    /**
     * orderId : 608130900053842
     * pin : JD_284ubc3b41a4
     * storeId : 10055645
     * storeName : 永辉超市-滨江新城店
     * orderState : 33060
     * rePurchaseSwitch : 1
     * deleteSwitch : 1
     * commentStatus : 2
     * isWaimaiOrder : 0
     * productTotalNumStr : 共10件
     * paymentType : 4
     * showPay : 0
     * orgCode : 74597
     * deliveryTime : 2016-08-13 11:49:00
     * dateSubmit : 2016-08-13 09:49:00
     * dateSubmitStr : 08-13 09:49
     * productTotalPrice : ¥22.20
     * orderListShowTrack : 0
     * orderStateMap : {"stateId":90,"stateTitle":"订单已完成","stateDesc":"感谢您使用阿凡提到家","stateTime":"08-13 10:24","stateIcon":"4"}
     * mainOrderStateMap : {"orderState":"3","orderStateName":"已完成","orderColor":"#333333"}
     * realPay : ¥22.20
     * buyerLng : 118.72889
     * buyerLat : 32.13012
     * isSowMap : 0
     * deliveryType : 0
     * deliveryTypeDesc : 达达专送
     * isGroup : 0
     * servicePhoneType : 2
     * afterSaleSign : 0
     * imSwitch : 1
     * servicePhone : 18914460003
     * deliveryManMobileNew : 13952038890
     * cancelSwitchNew : 0
     * shouldPay : ¥22.20
     * freightRule : 本单运费共计2元，包括<br/>·基础运费 2元<br/>
     * afsSwitch : 0
     * deliveryInfoList : [{"title":"配送信息","valueFlag":0},{"title":"送达时间：","value":"2016-08-13 11:49","valueFlag":0},{"title":"收货地址：","value":"常胜永 137****8361<br/>南京市浦口区旭日华庭比华利5栋1单元402","valueFlag":0},{"title":"配送方式：","value":"达达专送","valueFlag":1},{"title":"配送员  ：","value":"裴泽强 13952038890","valueFlag":0}]
     * orderInfoList : [{"title":"订单信息","valueFlag":0},{"title":"订单号码：","value":608130900053842,"valueFlag":0},{"title":"订单时间：","value":"2016-08-13 09:49:00","valueFlag":0},{"title":"支付方式：","value":"在线支付","valueFlag":0},{"title":"订单备注：","value":"所购商品如遇缺货，您需要：其它商品继续配送（缺货商品退款）","valueFlag":0}]
     * priceInfoList : [{"title":"商品金额","value":"¥20.20","valueFlag":0},{"title":"运费金额","titleTip":"本单运费共计2元，包括<br/>·基础运费 2元<br/>","value":"¥2.00","valueFlag":0},{"value":"应付：¥22.20","color":"#ff5757","valueFlag":0}]
     * orderCancelReasons : [{"text":"商家缺货","code":"40"},{"text":"在线支付遇到问题","code":"10"},{"text":"忘记使用优惠券/码","code":"20"},{"text":"买错商品/暂不想购买","code":"30"},{"text":"无人配送","code":"50"},{"text":"点错了，不取消订单","code":"99"}]
     * orderProductList : [{"sku":2001857878,"price":150,"discountPrice":0,"imgPath":"https://img10.360buyimg.com/n7/jfs/t2827/130/195812239/223883/294fbb28/5706b8a9N151cb9ff.jpg","shopId":"10055645","num":1,"name":"黄瓜约250±30g/份","orderId":608130900053842,"skuCommentStatus":0,"productCategory":0,"promotionType":1},{"sku":2001862437,"price":160,"discountPrice":0,"imgPath":"https://img10.360buyimg.com/n7/jfs/t1906/106/2231847033/505957/74b3df9e/56f78592N78c945ee.jpg","shopId":"10055645","num":2,"name":"怡宝纯净水555ml/瓶","orderId":608130900053842,"skuCommentStatus":0,"productCategory":0,"promotionType":1},{"sku":2001857876,"price":100,"discountPrice":0,"imgPath":"https://img10.360buyimg.com/n7/jfs/t2065/162/2926454761/79783/b40bfdf4/56f4d425N456302fe.jpg","shopId":"10055645","num":3,"name":"冬瓜450±50g/份","orderId":608130900053842,"skuCommentStatus":0,"productCategory":0,"promotionType":1},{"sku":2001858203,"price":180,"discountPrice":0,"imgPath":"https://img10.360buyimg.com/n7/jfs/t1996/153/2858166976/105030/34589452/56f4d561N75e653b9.jpg","shopId":"10055645","num":1,"name":"韭菜约250g/份","orderId":608130900053842,"skuCommentStatus":0,"productCategory":0,"promotionType":1},{"sku":2001857785,"price":290,"discountPrice":0,"imgPath":"https://img10.360buyimg.com/n7/jfs/t2053/296/2441453055/288261/2bce54b6/5706b980Nbed08ecc.jpg","shopId":"10055645","num":1,"name":"苦瓜约450±50g/份","orderId":608130900053842,"skuCommentStatus":0,"productCategory":0,"promotionType":1},{"sku":2001858118,"price":190,"discountPrice":0,"imgPath":"https://img10.360buyimg.com/n7/jfs/t2203/229/2514417151/200436/382a385b/5707ddc5N79b0772a.jpg","shopId":"10055645","num":1,"name":"胡萝卜约450±50g/份","orderId":608130900053842,"skuCommentStatus":0,"productCategory":0,"promotionType":1},{"sku":2001858155,"price":590,"discountPrice":0,"imgPath":"https://img10.360buyimg.com/n7/jfs/t2701/152/180155831/166004/bfa66b2/5706abddNa2de327e.jpg","shopId":"10055645","num":1,"name":"丰水梨约1±0.1kg/份","orderId":608130900053842,"skuCommentStatus":0,"productCategory":0,"promotionType":1}]
     * orderShowTitles : {"shouldPayTitle":"应付 ","realPayTitle":"实付："}
     * contactList : [{"phoneNum":"13952038890","code":"10","text":"联系配送员"},{"phoneNum":"18914460003","code":"20","text":"联系商家"},{"phoneNum":"4000020020","code":"100","text":"在线客服（售后，交易纠纷）"}]
     * receiveConfirm : 0
     * urgeOrder : {"show":0}
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
    public double deliveryManlng;
    public double deliveryManlat;
    /**
     * stateId : 90
     * stateTitle : 订单已完成
     * stateDesc : 感谢您使用阿凡提到家
     * stateTime : 08-13 10:24
     * stateIcon : 4
     */
    
    public OrderStateMap orderStateMap;
    /**
     * orderState : 3
     * orderStateName : 已完成
     * orderColor : #333333
     */
    
    public MainOrderStateMap mainOrderStateMap;
    public String realPay;
    public double buyerLng;
    public double buyerLat;
    public int isSowMap;
    public String deliveryType;
    public String deliveryTypeDesc;
    public int isGroup;
    public int servicePhoneType;
    public int afterSaleSign;
    public String imSwitch;
    public String servicePhone;
    public String deliveryManMobileNew;
    public int cancelSwitchNew;
    public String shouldPay;
    public String freightRule;
    public int afsSwitch;
    /**
     * shouldPayTitle : 应付
     * realPayTitle : 实付：
     */
    
    public OrderShowTitles orderShowTitles;
    public int receiveConfirm;
    /**
     * show : 0
     */
    
    public UrgeOrder urgeOrder;
    /**
     * title : 配送信息
     * valueFlag : 0
     */
    
    public List<DeliveryInfoList> deliveryInfoList;
    public List<DeliveryInfoList> orderInfoList;
    /**
     * title : 商品金额
     * value : ¥20.20
     * valueFlag : 0
     */
    
    public List<PriceInfoList> priceInfoList;
    /**
     * text : 商家缺货
     * code : 40
     */
    
    public List<OrderCancelReasons> orderCancelReasons;
    /**
     * sku : 2001857878
     * price : 150
     * discountPrice : 0
     * imgPath : https://img10.360buyimg.com/n7/jfs/t2827/130/195812239/223883/294fbb28/5706b8a9N151cb9ff.jpg
     * shopId : 10055645
     * num : 1
     * name : 黄瓜约250±30g/份
     * orderId : 608130900053842
     * skuCommentStatus : 0
     * productCategory : 0
     * promotionType : 1
     */
    
    public List<OrderProductList> orderProductList;
    
    public Set<OrderStateList> orderStateList;
    public List<ContactList> contactList;
    
    /**
     * phoneNum : 13952038890
     * code : 10
     * text : 联系配送员
     */
    public OrderInfo(String orderId, String pin, int storeId, String storeName, int rePurchaseSwitch, int deleteSwitch, int isWaimaiOrder, String orgCode, int orderListShowTrack, double buyerLng, double buyerLat, int isSowMap, int cancelSwitchNew) {
        this.orderId = orderId;
        this.pin = pin;
        this.storeId = storeId;
        this.storeName = storeName;
        this.rePurchaseSwitch = rePurchaseSwitch;
        this.deleteSwitch = deleteSwitch;
        
        this.isWaimaiOrder = isWaimaiOrder;
        this.orgCode = orgCode;
        
        this.orderListShowTrack = orderListShowTrack;
        this.buyerLng = buyerLng;
        this.buyerLat = buyerLat;
        this.isSowMap = isSowMap;
        this.cancelSwitchNew = cancelSwitchNew;
        this.orderListShowTrack = orderListShowTrack;
    }
    
    public static OrderInfo objectFromData(String str) {
        
        return new Gson().fromJson(str, OrderInfo.class);
    }
    
    public static OrderInfo objectFromData(String str, String key) {
        
        try {
            JSONObject jsonObject = new JSONObject(str);
            
            return new Gson().fromJson(jsonObject.getString(str), OrderInfo.class);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        
        return null;
    }
    
    public static List<OrderInfo> arrayOrderInfoFromData(String str) {
        
        Type listType = new TypeToken<ArrayList<OrderInfo>>() {
        }.getType();
        
        return new Gson().fromJson(str, listType);
    }
    
    public String getOrderId() {
        return orderId;
    }
    
    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }
    
    public String getPin() {
        return pin;
    }
    
    public void setPin(String pin) {
        this.pin = pin;
    }
    
    public int getStoreId() {
        return storeId;
    }
    
    public void setStoreId(int storeId) {
        this.storeId = storeId;
    }
    
    public String getStoreName() {
        return storeName;
    }
    
    public void setStoreName(String storeName) {
        this.storeName = storeName;
    }
    
    public int getOrderState() {
        return orderState;
    }
    
    public void setOrderState(int orderState) {
        this.orderState = orderState;
    }
    
    public int getRePurchaseSwitch() {
        return rePurchaseSwitch;
    }
    
    public void setRePurchaseSwitch(int rePurchaseSwitch) {
        this.rePurchaseSwitch = rePurchaseSwitch;
    }
    
    public int getDeleteSwitch() {
        return deleteSwitch;
    }
    
    public void setDeleteSwitch(int deleteSwitch) {
        this.deleteSwitch = deleteSwitch;
    }
    
    public int getCommentStatus() {
        return commentStatus;
    }
    
    public void setCommentStatus(int commentStatus) {
        this.commentStatus = commentStatus;
    }
    
    public int getIsWaimaiOrder() {
        return isWaimaiOrder;
    }
    
    public void setIsWaimaiOrder(int isWaimaiOrder) {
        this.isWaimaiOrder = isWaimaiOrder;
    }
    
    public String getProductTotalNumStr() {
        return productTotalNumStr;
    }
    
    public void setProductTotalNumStr(String productTotalNumStr) {
        this.productTotalNumStr = productTotalNumStr;
    }
    
    public int getPaymentType() {
        return paymentType;
    }
    
    public void setPaymentType(int paymentType) {
        this.paymentType = paymentType;
    }
    
    public int getShowPay() {
        return showPay;
    }
    
    public void setShowPay(int showPay) {
        this.showPay = showPay;
    }
    
    public String getOrgCode() {
        return orgCode;
    }
    
    public void setOrgCode(String orgCode) {
        this.orgCode = orgCode;
    }
    
    public String getDeliveryTime() {
        return deliveryTime;
    }
    
    public void setDeliveryTime(String deliveryTime) {
        this.deliveryTime = deliveryTime;
    }
    
    public String getDateSubmit() {
        return dateSubmit;
    }
    
    public void setDateSubmit(String dateSubmit) {
        this.dateSubmit = dateSubmit;
    }
    
    public String getDateSubmitStr() {
        return dateSubmitStr;
    }
    
    public void setDateSubmitStr(String dateSubmitStr) {
        this.dateSubmitStr = dateSubmitStr;
    }
    
    public String getProductTotalPrice() {
        return productTotalPrice;
    }
    
    public void setProductTotalPrice(String productTotalPrice) {
        this.productTotalPrice = productTotalPrice;
    }
    
    public int getOrderListShowTrack() {
        return orderListShowTrack;
    }
    
    public void setOrderListShowTrack(int orderListShowTrack) {
        this.orderListShowTrack = orderListShowTrack;
    }
    
    public OrderStateMap getOrderStateMap() {
        return orderStateMap;
    }
    
    public void setOrderStateMap(OrderStateMap orderStateMap) {
        this.orderStateMap = orderStateMap;
    }
    
    public MainOrderStateMap getMainOrderStateMap() {
        return mainOrderStateMap;
    }
    
    public void setMainOrderStateMap(MainOrderStateMap mainOrderStateMap) {
        this.mainOrderStateMap = mainOrderStateMap;
    }
    
    public String getRealPay() {
        return realPay;
    }
    
    public void setRealPay(String realPay) {
        this.realPay = realPay;
    }
    
    public double getBuyerLng() {
        return buyerLng;
    }
    
    public void setBuyerLng(double buyerLng) {
        this.buyerLng = buyerLng;
    }
    
    public double getBuyerLat() {
        return buyerLat;
    }
    
    public void setBuyerLat(double buyerLat) {
        this.buyerLat = buyerLat;
    }
    
    public int getIsSowMap() {
        return isSowMap;
    }
    
    public void setIsSowMap(int isSowMap) {
        this.isSowMap = isSowMap;
    }
    
    public String getDeliveryType() {
        return deliveryType;
    }
    
    public void setDeliveryType(String deliveryType) {
        this.deliveryType = deliveryType;
    }
    
    public String getDeliveryTypeDesc() {
        return deliveryTypeDesc;
    }
    
    public void setDeliveryTypeDesc(String deliveryTypeDesc) {
        this.deliveryTypeDesc = deliveryTypeDesc;
    }
    
    public int getIsGroup() {
        return isGroup;
    }
    
    public void setIsGroup(int isGroup) {
        this.isGroup = isGroup;
    }
    
    public int getServicePhoneType() {
        return servicePhoneType;
    }
    
    public void setServicePhoneType(int servicePhoneType) {
        this.servicePhoneType = servicePhoneType;
    }
    
    public int getAfterSaleSign() {
        return afterSaleSign;
    }
    
    public void setAfterSaleSign(int afterSaleSign) {
        this.afterSaleSign = afterSaleSign;
    }
    
    public String getImSwitch() {
        return imSwitch;
    }
    
    public void setImSwitch(String imSwitch) {
        this.imSwitch = imSwitch;
    }
    
    public String getServicePhone() {
        return servicePhone;
    }
    
    public void setServicePhone(String servicePhone) {
        this.servicePhone = servicePhone;
    }
    
    public String getDeliveryManMobileNew() {
        return deliveryManMobileNew;
    }
    
    public void setDeliveryManMobileNew(String deliveryManMobileNew) {
        this.deliveryManMobileNew = deliveryManMobileNew;
    }
    
    public int getCancelSwitchNew() {
        return cancelSwitchNew;
    }
    
    public void setCancelSwitchNew(int cancelSwitchNew) {
        this.cancelSwitchNew = cancelSwitchNew;
    }
    
    public String getShouldPay() {
        return shouldPay;
    }
    
    public void setShouldPay(String shouldPay) {
        this.shouldPay = shouldPay;
    }
    
    public String getFreightRule() {
        return freightRule;
    }
    
    public void setFreightRule(String freightRule) {
        this.freightRule = freightRule;
    }
    
    public int getAfsSwitch() {
        return afsSwitch;
    }
    
    public void setAfsSwitch(int afsSwitch) {
        this.afsSwitch = afsSwitch;
    }
    
    public OrderShowTitles getOrderShowTitles() {
        return orderShowTitles;
    }
    
    public void setOrderShowTitles(OrderShowTitles orderShowTitles) {
        this.orderShowTitles = orderShowTitles;
    }
    
    public int getReceiveConfirm() {
        return receiveConfirm;
    }
    
    public void setReceiveConfirm(int receiveConfirm) {
        this.receiveConfirm = receiveConfirm;
    }
    
    public UrgeOrder getUrgeOrder() {
        return urgeOrder;
    }
    
    public void setUrgeOrder(UrgeOrder urgeOrder) {
        this.urgeOrder = urgeOrder;
    }
    
    public List<DeliveryInfoList> getDeliveryInfoList() {
        return deliveryInfoList;
    }
    
    public void setDeliveryInfoList(List<DeliveryInfoList> deliveryInfoList) {
        this.deliveryInfoList = deliveryInfoList;
    }
    
    public List<DeliveryInfoList> getOrderInfoList() {
        return orderInfoList;
    }
    
    public void setOrderInfoList(List<DeliveryInfoList> orderInfoList) {
        this.orderInfoList = orderInfoList;
    }
    
    public List<PriceInfoList> getPriceInfoList() {
        return priceInfoList;
    }
    
    public void setPriceInfoList(List<PriceInfoList> priceInfoList) {
        this.priceInfoList = priceInfoList;
    }
    
    public List<OrderCancelReasons> getOrderCancelReasons() {
        return orderCancelReasons;
    }
    
    public void setOrderCancelReasons(List<OrderCancelReasons> orderCancelReasons) {
        this.orderCancelReasons = orderCancelReasons;
    }
    
    public List<OrderProductList> getOrderProductList() {
        return orderProductList;
    }
    
    public void setOrderProductList(List<OrderProductList> orderProductList) {
        this.orderProductList = orderProductList;
    }
    
    public List<ContactList> getContactList() {
        return contactList;
    }
    
    public void setContactList(List<ContactList> contactList) {
        this.contactList = contactList;
    }
    
    public static class OrderShowTitles {
        public String shouldPayTitle;
        public String realPayTitle;
        
        public static OrderShowTitles objectFromData(String str) {
            
            return new Gson().fromJson(str, OrderShowTitles.class);
        }
        
        public static OrderShowTitles objectFromData(String str, String key) {
            
            try {
                JSONObject jsonObject = new JSONObject(str);
                
                return new Gson().fromJson(jsonObject.getString(str), OrderShowTitles.class);
            } catch (JSONException e) {
                e.printStackTrace();
            }
            
            return null;
        }
        
        public static List<OrderShowTitles> arrayOrderShowTitlesFromData(String str) {
            
            Type listType = new TypeToken<ArrayList<OrderShowTitles>>() {
            }.getType();
            
            return new Gson().fromJson(str, listType);
        }
    }
    
    public static class UrgeOrder {
        public int show;
        
        public UrgeOrder(int show) {
            this.show = show;
        }
        
        public static UrgeOrder objectFromData(String str) {
            
            return new Gson().fromJson(str, UrgeOrder.class);
        }
        
        public static UrgeOrder objectFromData(String str, String key) {
            
            try {
                JSONObject jsonObject = new JSONObject(str);
                
                return new Gson().fromJson(jsonObject.getString(str), UrgeOrder.class);
            } catch (JSONException e) {
                e.printStackTrace();
            }
            
            return null;
        }
        
        public static List<UrgeOrder> arrayUrgeOrderFromData(String str) {
            
            Type listType = new TypeToken<ArrayList<UrgeOrder>>() {
            }.getType();
            
            return new Gson().fromJson(str, listType);
        }
    }
}
