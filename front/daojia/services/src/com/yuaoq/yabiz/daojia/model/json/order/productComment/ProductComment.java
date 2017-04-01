package com.yuaoq.yabiz.daojia.model.json.order.productComment;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import org.json.JSONException;
import org.json.JSONObject;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by changsy on 16/9/23.
 */
public class ProductComment {
    
    
    /**
     * orderId : 608130900053842
     * address : 南京市浦口区旭日华庭比华利5栋1单元402
     * customerName : 常胜永
     * mobile : 13705188361
     * shipmentType : 1
     * paymentType : 4
     * orderState : 33060
     * orderStateName : 已完成
     * dateSubmit : 2016-08-13 09:49:00
     * totalfee : 200
     * totalfee_dec : 2
     * trueTotalfee : 200
     * usedBalance : 0
     * userTruepay : 2220
     * price : 2020
     * price_dec : 20.2
     * discount : 0
     * discount_dec : 0
     * shouldpay : 2220
     * shouldpay_dec : 22.2
     * storeId : 10055645
     * pin : JD_284ubc3b41a4
     * remark : 所购商品如遇缺货，您需要：其它商品继续配送（缺货商品退款）
     * cityId : 904
     * provinceId : 0
     * deliveryTime : 2016-08-13 11:49:00
     * deliveryTimeStr : 2016-08-13 11:49
     * packagingCost : 0
     * packagingCost_dec : 0
     * orderPlatform : iOS3.1.0
     * arriveTimeStart : 2016-08-13 11:49:00
     * arriveTimeEnd : 2016-08-13 11:49:00
     * timingArrive : 0
     * tagTiming : dj_new_cashier;dj_cs;
     * cancelSwitch : 0
     * imSwitch : 2
     * commentStatus : 2
     * servicePhone : 4006805065
     * promoCodeAmount : 0
     * buyerIp : 49.77.245.224
     * buyerCoordType : 2
     * buyerLng : 118.72889
     * buyerLat : 32.13012
     * orgCode : 74597
     * orderType : 10000
     * storeName : 永辉超市-滨江新城店
     * deliveryMan : 裴泽强
     * deliveryManMobile : 13952038890
     * orderProductList : [{"sku":2001857878,"price":150,"discountPrice":0,"imgPath":"https://img10.360buyimg.com/n7/jfs/t2827/130/195812239/223883/294fbb28/5706b8a9N151cb9ff.jpg","shopId":"10055645","num":1,"name":"黄瓜约250±30g/份","orderId":608130900053842,"upVote":1,"score":0,"content":"","skuCommentStatus":1,"productCategory":0,"promotionType":1},{"sku":2001862437,"price":160,"discountPrice":0,"imgPath":"https://img10.360buyimg.com/n7/jfs/t1906/106/2231847033/505957/74b3df9e/56f78592N78c945ee.jpg","shopId":"10055645","num":2,"name":"怡宝纯净水555ml/瓶","orderId":608130900053842,"upVote":1,"score":0,"content":"","skuCommentStatus":1,"productCategory":0,"promotionType":1},{"sku":2001857876,"price":100,"discountPrice":0,"imgPath":"https://img10.360buyimg.com/n7/jfs/t2065/162/2926454761/79783/b40bfdf4/56f4d425N456302fe.jpg","shopId":"10055645","num":3,"name":"冬瓜450±50g/份","orderId":608130900053842,"upVote":1,"score":0,"content":"","skuCommentStatus":1,"productCategory":0,"promotionType":1},{"sku":2001858203,"price":180,"discountPrice":0,"imgPath":"https://img10.360buyimg.com/n7/jfs/t1996/153/2858166976/105030/34589452/56f4d561N75e653b9.jpg","shopId":"10055645","num":1,"name":"韭菜约250g/份","orderId":608130900053842,"upVote":1,"score":0,"content":"","skuCommentStatus":1,"productCategory":0,"promotionType":1},{"sku":2001857785,"price":290,"discountPrice":0,"imgPath":"https://img10.360buyimg.com/n7/jfs/t2053/296/2441453055/288261/2bce54b6/5706b980Nbed08ecc.jpg","shopId":"10055645","num":1,"name":"苦瓜约450±50g/份","orderId":608130900053842,"upVote":1,"score":0,"content":"","skuCommentStatus":1,"productCategory":0,"promotionType":1},{"sku":2001858118,"price":190,"discountPrice":0,"imgPath":"https://img10.360buyimg.com/n7/jfs/t2203/229/2514417151/200436/382a385b/5707ddc5N79b0772a.jpg","shopId":"10055645","num":1,"name":"胡萝卜约450±50g/份","orderId":608130900053842,"upVote":1,"score":0,"content":"","skuCommentStatus":1,"productCategory":0,"promotionType":1},{"sku":2001858155,"price":590,"discountPrice":0,"imgPath":"https://img10.360buyimg.com/n7/jfs/t2701/152/180155831/166004/bfa66b2/5706abddNa2de327e.jpg","shopId":"10055645","num":1,"name":"丰水梨约1±0.1kg/份","orderId":608130900053842,"upVote":1,"score":0,"content":"","skuCommentStatus":1,"productCategory":0,"promotionType":1}]
     * orderPromotionList : []
     * jdouDiscount : 0
     * jdouDiscount_dec : 0
     * cancelSwitchNew : 0
     * rePurchaseSwitch : 0
     * deleteSwitch : 0
     * afterSaleSign : 0
     * freightRule : 本单运费共计2元，包括<br/>·基础运费 2元<br/>
     * ts : 2016-08-13 10:24:45
     * freightPrice : 200
     * payablePrice : 2220
     * productDiscount : 0
     * freightDiscount : 0
     * discountNew : 0
     * carrier : 9966
     * businessType : 1
     * shopDeliveryScore : 0
     * shopServiceScore : 0
     * productScoreName : 商品评价
     * shopScoreName : 服务评价
     * shopDeliveryScoreName : 送货速度
     * shopServiceScoreName : 配送服务
     * commentDescHtml : <p><span style=";font-family:&#39;微软雅黑&#39;,&#39;sans-serif&#39;">1</span><span style=";font-family:&#39;微软雅黑&#39;,&#39;sans-serif&#39;">. 用户只能对30天内购买的订单进行商品评价和服务评价（非阿凡提到家平台商家的商品除外）。</span></p><p><span style=";font-family:&#39;微软雅黑&#39;,&#39;sans-serif&#39;">2.&nbsp;</span><span style=";font-family:&#39;微软雅黑&#39;,&#39;sans-serif&#39;">商品评价是您对所购买商品质量、使用感受等进行评价，您公平公正的评价可以帮助其他用户做出正确的购买决策。</span></p><p><span style=";font-family:&#39;微软雅黑&#39;,&#39;sans-serif&#39;">3.&nbsp;</span><span style=";font-family:&#39;微软雅黑&#39;,&#39;sans-serif&#39;">鼓励发表原创、有价值的评价；杜绝剽窃、发表与购买商品不相关的内容，禁止发表违反法律法规的评价内容。</span></p><p><span style=";font-family:&#39;微软雅黑&#39;,&#39;sans-serif&#39;">4.&nbsp;</span><span style=";font-family:&#39;微软雅黑&#39;,&#39;sans-serif&#39;">晒单发表成功后会对晒图进行审核，审核过程中，评价文字会先展示出来，审核通过后，晒图会一起进行展示。</span></p><p><span style="font-size: 16px; font-family: 微软雅黑, sans-serif;">5.<span style="font-stretch: normal;font-size: 9px;font-family: &#39;Times New Roman&#39;">&nbsp;</span></span><span style="font-size: 16px; font-family: 微软雅黑, sans-serif;">对于审核不通过的评价晒单，晒图不能被展示出来，有下列情形之一的，审核不予通过：</span></p><p><span style=";font-family:&#39;微软雅黑&#39;,&#39;sans-serif&#39;">（1）涉及到赌毒及政治图片；</span></p><p><span style=";font-family:&#39;微软雅黑&#39;,&#39;sans-serif&#39;">（2）涉及到阿凡提的相关负责人图片；</span></p><p><span style=";font-family:&#39;微软雅黑&#39;,&#39;sans-serif&#39;">（3）涉及到广告类图片，如微信和qq信息和其他购物网站类的；</span></p><p><span style=";font-family:&#39;微软雅黑&#39;,&#39;sans-serif&#39;">（4）配送人员及上门服务人员工作时候正面照片；</span></p><p><span style=";font-family:&#39;微软雅黑&#39;,&#39;sans-serif&#39;">（5）涉及到客户和客服聊天记录照片；</span></p><p><span style=";font-family:&#39;微软雅黑&#39;,&#39;sans-serif&#39;">（6）客户晒单时候拍摄到人体照片未遮三点；</span></p><p><span style=";font-family:&#39;微软雅黑&#39;,&#39;sans-serif&#39;">（7）模糊我们无法识别的图片；</span></p><p><span style=";font-family:&#39;微软雅黑&#39;,&#39;sans-serif&#39;">（8）图片中涉及敏感词汇（如：曝光，315，假二水，翻新等）；</span></p><p><span style=";font-family:&#39;微软雅黑&#39;,&#39;sans-serif&#39;">（9）盗用其他用户评价的图片；</span></p>
     * defaultCommentList : ["残忍的打击了我脆弱的小心脏。","不是很满意，期待商家的进步。","并没有想象中的那么好。","服务贴心，购物开心，商品暖心。","非常满意，好评哦！","<\/p>"]
     * noCommentOrderProductList : [{"sku":2001857878,"price":150,"discountPrice":0,"imgPath":"https://img10.360buyimg.com/n7/jfs/t2827/130/195812239/223883/294fbb28/5706b8a9N151cb9ff.jpg","shopId":"10055645","num":1,"name":"黄瓜约250±30g/份","orderId":608130900053842,"upVote":1,"score":0,"content":"","skuCommentStatus":1,"productCategory":0,"promotionType":1},{"sku":2001862437,"price":160,"discountPrice":0,"imgPath":"https://img10.360buyimg.com/n7/jfs/t1906/106/2231847033/505957/74b3df9e/56f78592N78c945ee.jpg","shopId":"10055645","num":2,"name":"怡宝纯净水555ml/瓶","orderId":608130900053842,"upVote":1,"score":0,"content":"","skuCommentStatus":1,"productCategory":0,"promotionType":1},{"sku":2001857876,"price":100,"discountPrice":0,"imgPath":"https://img10.360buyimg.com/n7/jfs/t2065/162/2926454761/79783/b40bfdf4/56f4d425N456302fe.jpg","shopId":"10055645","num":3,"name":"冬瓜450±50g/份","orderId":608130900053842,"upVote":1,"score":0,"content":"","skuCommentStatus":1,"productCategory":0,"promotionType":1},{"sku":2001858203,"price":180,"discountPrice":0,"imgPath":"https://img10.360buyimg.com/n7/jfs/t1996/153/2858166976/105030/34589452/56f4d561N75e653b9.jpg","shopId":"10055645","num":1,"name":"韭菜约250g/份","orderId":608130900053842,"upVote":1,"score":0,"content":"","skuCommentStatus":1,"productCategory":0,"promotionType":1},{"sku":2001857785,"price":290,"discountPrice":0,"imgPath":"https://img10.360buyimg.com/n7/jfs/t2053/296/2441453055/288261/2bce54b6/5706b980Nbed08ecc.jpg","shopId":"10055645","num":1,"name":"苦瓜约450±50g/份","orderId":608130900053842,"upVote":1,"score":0,"content":"","skuCommentStatus":1,"productCategory":0,"promotionType":1},{"sku":2001858118,"price":190,"discountPrice":0,"imgPath":"https://img10.360buyimg.com/n7/jfs/t2203/229/2514417151/200436/382a385b/5707ddc5N79b0772a.jpg","shopId":"10055645","num":1,"name":"胡萝卜约450±50g/份","orderId":608130900053842,"upVote":1,"score":0,"content":"","skuCommentStatus":1,"productCategory":0,"promotionType":1},{"sku":2001858155,"price":590,"discountPrice":0,"imgPath":"https://img10.360buyimg.com/n7/jfs/t2701/152/180155831/166004/bfa66b2/5706abddNa2de327e.jpg","shopId":"10055645","num":1,"name":"丰水梨约1±0.1kg/份","orderId":608130900053842,"upVote":1,"score":0,"content":"","skuCommentStatus":1,"productCategory":0,"promotionType":1}]
     * commentOrderProductList : []
     * serviceStarTipList : ["很差","不够满意","一般","满意","非常满意"]
     * deliveryStarTipList : ["很差","不够满意","一般","满意","非常满意"]
     * productStarTipList : ["很差","不够满意","一般","满意","非常满意"]
     * isShowSubmmit : 1
     * isWaimaiOrder : 0
     */
    
    public String orderId;
    public String address;
    public String customerName;
    public String mobile;
    public int shipmentType;
    public int paymentType;
    public String orderState;
    public String orderStateName;
    public String dateSubmit;
    public int totalfee;
    public int totalfee_dec;
    public int trueTotalfee;
    public int usedBalance;
    public int userTruepay;
    public int price;
    public double price_dec;
    public int discount;
    public int discount_dec;
    public int shouldpay;
    public double shouldpay_dec;
    public int storeId;
    public String pin;
    public String remark;
    public int cityId;
    public int provinceId;
    public String deliveryTime;
    public String deliveryTimeStr;
    public int packagingCost;
    public int packagingCost_dec;
    public String orderPlatform;
    public String arriveTimeStart;
    public String arriveTimeEnd;
    public int timingArrive;
    public String tagTiming;
    public int cancelSwitch;
    public String imSwitch;
    public int commentStatus;
    public String servicePhone;
    public int promoCodeAmount;
    public String buyerIp;
    public int buyerCoordType;
    public double buyerLng;
    public double buyerLat;
    public String orgCode;
    public int orderType;
    public String storeName;
    public String deliveryMan;
    public String deliveryManMobile;
    public int jdouDiscount;
    public int jdouDiscount_dec;
    public int cancelSwitchNew;
    public int rePurchaseSwitch;
    public int deleteSwitch;
    public int afterSaleSign;
    public String freightRule;
    public String ts;
    public int freightPrice;
    public int payablePrice;
    public int productDiscount;
    public int freightDiscount;
    public int discountNew;
    public String carrier;
    public int businessType;
    public String shopDeliveryScore;
    public String shopServiceScore;
    public String productScoreName;
    public String shopScoreName;
    public String shopDeliveryScoreName;
    public String shopServiceScoreName;
    public String commentDescHtml;
    public int isShowSubmmit;
    public int isWaimaiOrder;
    /**
     * sku : 2001857878
     * price : 150
     * discountPrice : 0
     * imgPath : https://img10.360buyimg.com/n7/jfs/t2827/130/195812239/223883/294fbb28/5706b8a9N151cb9ff.jpg
     * shopId : 10055645
     * num : 1
     * name : 黄瓜约250±30g/份
     * orderId : 608130900053842
     * upVote : 1
     * score : 0
     * content :
     * skuCommentStatus : 1
     * productCategory : 0
     * promotionType : 1
     */
    
    public List<OrderProductList> orderProductList;
    public List<?> orderPromotionList;
    public List<String> defaultCommentList;
    public List<OrderProductList> noCommentOrderProductList;
    public List<?> commentOrderProductList;
    public List<String> serviceStarTipList;
    public List<String> deliveryStarTipList;
    public List<String> productStarTipList;
    
    public static ProductComment objectFromData(String str) {
        
        return new Gson().fromJson(str, ProductComment.class);
    }
    
    public static ProductComment objectFromData(String str, String key) {
        
        try {
            JSONObject jsonObject = new JSONObject(str);
            
            return new Gson().fromJson(jsonObject.getString(str), ProductComment.class);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        
        return null;
    }
    
    public static List<ProductComment> arrayProductCommentFromData(String str) {
        
        Type listType = new TypeToken<ArrayList<ProductComment>>() {
        }.getType();
        
        return new Gson().fromJson(str, listType);
    }
    
    public String getOrderId() {
        return orderId;
    }
    
    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }
    
    public String getAddress() {
        return address;
    }
    
    public void setAddress(String address) {
        this.address = address;
    }
    
    public String getCustomerName() {
        return customerName;
    }
    
    public void setCustomerName(String customerName) {
        this.customerName = customerName;
    }
    
    public String getMobile() {
        return mobile;
    }
    
    public void setMobile(String mobile) {
        this.mobile = mobile;
    }
    
    public int getShipmentType() {
        return shipmentType;
    }
    
    public void setShipmentType(int shipmentType) {
        this.shipmentType = shipmentType;
    }
    
    public int getPaymentType() {
        return paymentType;
    }
    
    public void setPaymentType(int paymentType) {
        this.paymentType = paymentType;
    }
    
    public String getOrderState() {
        return orderState;
    }
    
    public void setOrderState(String orderState) {
        this.orderState = orderState;
    }
    
    public String getOrderStateName() {
        return orderStateName;
    }
    
    public void setOrderStateName(String orderStateName) {
        this.orderStateName = orderStateName;
    }
    
    public String getDateSubmit() {
        return dateSubmit;
    }
    
    public void setDateSubmit(String dateSubmit) {
        this.dateSubmit = dateSubmit;
    }
    
    public int getTotalfee() {
        return totalfee;
    }
    
    public void setTotalfee(int totalfee) {
        this.totalfee = totalfee;
    }
    
    public int getTotalfee_dec() {
        return totalfee_dec;
    }
    
    public void setTotalfee_dec(int totalfee_dec) {
        this.totalfee_dec = totalfee_dec;
    }
    
    public int getTrueTotalfee() {
        return trueTotalfee;
    }
    
    public void setTrueTotalfee(int trueTotalfee) {
        this.trueTotalfee = trueTotalfee;
    }
    
    public int getUsedBalance() {
        return usedBalance;
    }
    
    public void setUsedBalance(int usedBalance) {
        this.usedBalance = usedBalance;
    }
    
    public int getUserTruepay() {
        return userTruepay;
    }
    
    public void setUserTruepay(int userTruepay) {
        this.userTruepay = userTruepay;
    }
    
    public int getPrice() {
        return price;
    }
    
    public void setPrice(int price) {
        this.price = price;
    }
    
    public double getPrice_dec() {
        return price_dec;
    }
    
    public void setPrice_dec(double price_dec) {
        this.price_dec = price_dec;
    }
    
    public int getDiscount() {
        return discount;
    }
    
    public void setDiscount(int discount) {
        this.discount = discount;
    }
    
    public int getDiscount_dec() {
        return discount_dec;
    }
    
    public void setDiscount_dec(int discount_dec) {
        this.discount_dec = discount_dec;
    }
    
    public int getShouldpay() {
        return shouldpay;
    }
    
    public void setShouldpay(int shouldpay) {
        this.shouldpay = shouldpay;
    }
    
    public double getShouldpay_dec() {
        return shouldpay_dec;
    }
    
    public void setShouldpay_dec(double shouldpay_dec) {
        this.shouldpay_dec = shouldpay_dec;
    }
    
    public int getStoreId() {
        return storeId;
    }
    
    public void setStoreId(int storeId) {
        this.storeId = storeId;
    }
    
    public String getPin() {
        return pin;
    }
    
    public void setPin(String pin) {
        this.pin = pin;
    }
    
    public String getRemark() {
        return remark;
    }
    
    public void setRemark(String remark) {
        this.remark = remark;
    }
    
    public int getCityId() {
        return cityId;
    }
    
    public void setCityId(int cityId) {
        this.cityId = cityId;
    }
    
    public int getProvinceId() {
        return provinceId;
    }
    
    public void setProvinceId(int provinceId) {
        this.provinceId = provinceId;
    }
    
    public String getDeliveryTime() {
        return deliveryTime;
    }
    
    public void setDeliveryTime(String deliveryTime) {
        this.deliveryTime = deliveryTime;
    }
    
    public String getDeliveryTimeStr() {
        return deliveryTimeStr;
    }
    
    public void setDeliveryTimeStr(String deliveryTimeStr) {
        this.deliveryTimeStr = deliveryTimeStr;
    }
    
    public int getPackagingCost() {
        return packagingCost;
    }
    
    public void setPackagingCost(int packagingCost) {
        this.packagingCost = packagingCost;
    }
    
    public int getPackagingCost_dec() {
        return packagingCost_dec;
    }
    
    public void setPackagingCost_dec(int packagingCost_dec) {
        this.packagingCost_dec = packagingCost_dec;
    }
    
    public String getOrderPlatform() {
        return orderPlatform;
    }
    
    public void setOrderPlatform(String orderPlatform) {
        this.orderPlatform = orderPlatform;
    }
    
    public String getArriveTimeStart() {
        return arriveTimeStart;
    }
    
    public void setArriveTimeStart(String arriveTimeStart) {
        this.arriveTimeStart = arriveTimeStart;
    }
    
    public String getArriveTimeEnd() {
        return arriveTimeEnd;
    }
    
    public void setArriveTimeEnd(String arriveTimeEnd) {
        this.arriveTimeEnd = arriveTimeEnd;
    }
    
    public int getTimingArrive() {
        return timingArrive;
    }
    
    public void setTimingArrive(int timingArrive) {
        this.timingArrive = timingArrive;
    }
    
    public String getTagTiming() {
        return tagTiming;
    }
    
    public void setTagTiming(String tagTiming) {
        this.tagTiming = tagTiming;
    }
    
    public int getCancelSwitch() {
        return cancelSwitch;
    }
    
    public void setCancelSwitch(int cancelSwitch) {
        this.cancelSwitch = cancelSwitch;
    }
    
    public String getImSwitch() {
        return imSwitch;
    }
    
    public void setImSwitch(String imSwitch) {
        this.imSwitch = imSwitch;
    }
    
    public int getCommentStatus() {
        return commentStatus;
    }
    
    public void setCommentStatus(int commentStatus) {
        this.commentStatus = commentStatus;
    }
    
    public String getServicePhone() {
        return servicePhone;
    }
    
    public void setServicePhone(String servicePhone) {
        this.servicePhone = servicePhone;
    }
    
    public int getPromoCodeAmount() {
        return promoCodeAmount;
    }
    
    public void setPromoCodeAmount(int promoCodeAmount) {
        this.promoCodeAmount = promoCodeAmount;
    }
    
    public String getBuyerIp() {
        return buyerIp;
    }
    
    public void setBuyerIp(String buyerIp) {
        this.buyerIp = buyerIp;
    }
    
    public int getBuyerCoordType() {
        return buyerCoordType;
    }
    
    public void setBuyerCoordType(int buyerCoordType) {
        this.buyerCoordType = buyerCoordType;
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
    
    public String getOrgCode() {
        return orgCode;
    }
    
    public void setOrgCode(String orgCode) {
        this.orgCode = orgCode;
    }
    
    public int getOrderType() {
        return orderType;
    }
    
    public void setOrderType(int orderType) {
        this.orderType = orderType;
    }
    
    public String getStoreName() {
        return storeName;
    }
    
    public void setStoreName(String storeName) {
        this.storeName = storeName;
    }
    
    public String getDeliveryMan() {
        return deliveryMan;
    }
    
    public void setDeliveryMan(String deliveryMan) {
        this.deliveryMan = deliveryMan;
    }
    
    public String getDeliveryManMobile() {
        return deliveryManMobile;
    }
    
    public void setDeliveryManMobile(String deliveryManMobile) {
        this.deliveryManMobile = deliveryManMobile;
    }
    
    public int getJdouDiscount() {
        return jdouDiscount;
    }
    
    public void setJdouDiscount(int jdouDiscount) {
        this.jdouDiscount = jdouDiscount;
    }
    
    public int getJdouDiscount_dec() {
        return jdouDiscount_dec;
    }
    
    public void setJdouDiscount_dec(int jdouDiscount_dec) {
        this.jdouDiscount_dec = jdouDiscount_dec;
    }
    
    public int getCancelSwitchNew() {
        return cancelSwitchNew;
    }
    
    public void setCancelSwitchNew(int cancelSwitchNew) {
        this.cancelSwitchNew = cancelSwitchNew;
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
    
    public int getAfterSaleSign() {
        return afterSaleSign;
    }
    
    public void setAfterSaleSign(int afterSaleSign) {
        this.afterSaleSign = afterSaleSign;
    }
    
    public String getFreightRule() {
        return freightRule;
    }
    
    public void setFreightRule(String freightRule) {
        this.freightRule = freightRule;
    }
    
    public String getTs() {
        return ts;
    }
    
    public void setTs(String ts) {
        this.ts = ts;
    }
    
    public int getFreightPrice() {
        return freightPrice;
    }
    
    public void setFreightPrice(int freightPrice) {
        this.freightPrice = freightPrice;
    }
    
    public int getPayablePrice() {
        return payablePrice;
    }
    
    public void setPayablePrice(int payablePrice) {
        this.payablePrice = payablePrice;
    }
    
    public int getProductDiscount() {
        return productDiscount;
    }
    
    public void setProductDiscount(int productDiscount) {
        this.productDiscount = productDiscount;
    }
    
    public int getFreightDiscount() {
        return freightDiscount;
    }
    
    public void setFreightDiscount(int freightDiscount) {
        this.freightDiscount = freightDiscount;
    }
    
    public int getDiscountNew() {
        return discountNew;
    }
    
    public void setDiscountNew(int discountNew) {
        this.discountNew = discountNew;
    }
    
    public String getCarrier() {
        return carrier;
    }
    
    public void setCarrier(String carrier) {
        this.carrier = carrier;
    }
    
    public int getBusinessType() {
        return businessType;
    }
    
    public void setBusinessType(int businessType) {
        this.businessType = businessType;
    }
    
    public String getShopDeliveryScore() {
        return shopDeliveryScore;
    }
    
    public void setShopDeliveryScore(String shopDeliveryScore) {
        this.shopDeliveryScore = shopDeliveryScore;
    }
    
    public String getShopServiceScore() {
        return shopServiceScore;
    }
    
    public void setShopServiceScore(String shopServiceScore) {
        this.shopServiceScore = shopServiceScore;
    }
    
    public String getProductScoreName() {
        return productScoreName;
    }
    
    public void setProductScoreName(String productScoreName) {
        this.productScoreName = productScoreName;
    }
    
    public String getShopScoreName() {
        return shopScoreName;
    }
    
    public void setShopScoreName(String shopScoreName) {
        this.shopScoreName = shopScoreName;
    }
    
    public String getShopDeliveryScoreName() {
        return shopDeliveryScoreName;
    }
    
    public void setShopDeliveryScoreName(String shopDeliveryScoreName) {
        this.shopDeliveryScoreName = shopDeliveryScoreName;
    }
    
    public String getShopServiceScoreName() {
        return shopServiceScoreName;
    }
    
    public void setShopServiceScoreName(String shopServiceScoreName) {
        this.shopServiceScoreName = shopServiceScoreName;
    }
    
    public String getCommentDescHtml() {
        return commentDescHtml;
    }
    
    public void setCommentDescHtml(String commentDescHtml) {
        this.commentDescHtml = commentDescHtml;
    }
    
    public int getIsShowSubmmit() {
        return isShowSubmmit;
    }
    
    public void setIsShowSubmmit(int isShowSubmmit) {
        this.isShowSubmmit = isShowSubmmit;
    }
    
    public int getIsWaimaiOrder() {
        return isWaimaiOrder;
    }
    
    public void setIsWaimaiOrder(int isWaimaiOrder) {
        this.isWaimaiOrder = isWaimaiOrder;
    }
    
    public List<OrderProductList> getOrderProductList() {
        return orderProductList;
    }
    
    public void setOrderProductList(List<OrderProductList> orderProductList) {
        this.orderProductList = orderProductList;
    }
    
    public List<?> getOrderPromotionList() {
        return orderPromotionList;
    }
    
    public void setOrderPromotionList(List<?> orderPromotionList) {
        this.orderPromotionList = orderPromotionList;
    }
    
    public List<String> getDefaultCommentList() {
        return defaultCommentList;
    }
    
    public void setDefaultCommentList(List<String> defaultCommentList) {
        this.defaultCommentList = defaultCommentList;
    }
    
    public List<OrderProductList> getNoCommentOrderProductList() {
        return noCommentOrderProductList;
    }
    
    public void setNoCommentOrderProductList(List<OrderProductList> noCommentOrderProductList) {
        this.noCommentOrderProductList = noCommentOrderProductList;
    }
    
    public List<?> getCommentOrderProductList() {
        return commentOrderProductList;
    }
    
    public void setCommentOrderProductList(List<?> commentOrderProductList) {
        this.commentOrderProductList = commentOrderProductList;
    }
    
    public List<String> getServiceStarTipList() {
        return serviceStarTipList;
    }
    
    public void setServiceStarTipList(List<String> serviceStarTipList) {
        this.serviceStarTipList = serviceStarTipList;
    }
    
    public List<String> getDeliveryStarTipList() {
        return deliveryStarTipList;
    }
    
    public void setDeliveryStarTipList(List<String> deliveryStarTipList) {
        this.deliveryStarTipList = deliveryStarTipList;
    }
    
    public List<String> getProductStarTipList() {
        return productStarTipList;
    }
    
    public void setProductStarTipList(List<String> productStarTipList) {
        this.productStarTipList = productStarTipList;
    }
}
