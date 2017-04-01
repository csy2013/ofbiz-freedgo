package com.yuaoq.yabiz.daojia.model.json.cart.marketSettleGetCurrentAccount;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.yuaoq.yabiz.daojia.model.json.BaseResult;
import org.json.JSONException;
import org.json.JSONObject;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by changsy on 16/9/18.
 */
public class MarketSettleGetCurrentAccount extends BaseResult {
    
    
    /**
     * unique : f4d5fb99-f51f-4268-a273-5194f98c7bab
     * serverSign : 047E9088EEACD2B4D10ABDE3F7940B38
     * totalMoney : ￥34
     * freightFee : 200
     * manJianMoney : 3200
     * modules : [{"show":true,"moduleKey":"receiptAddress","title":"收货地址","defaultText":"","type":0,"data":{"type":"1","status":"2","title":"","desc":"","addressVo":{"addressId":10771,"name":"常胜永","phone":"137****8361","addressName":"南京市浦口区旭日华庭比华利5栋1单元402","longitude":118.72889,"latitude":32.13012,"cityId":904},"bindPhoneVO":{"bindType":1,"bindNewPhone":"137****8361"}},"group":"1","changeNum":false},{"show":true,"moduleKey":"deliverTime","title":"送达时间","defaultText":"","type":0,"data":{"promiseServerResp":{"serverFlag":"2","data":[{"promiseDateText":"今天","promiseDate":"2016-08-20","promiseType":"2","promiseTimeRespItems":[{"promiseTimeText":"立即送达","promiseStartTime":"13:57","promiseEndTime":"13:57","steppedFreight":"2元运费","dingshida":false},{"promiseTimeText":"14:00-14:30","promiseStartTime":"14:00","promiseEndTime":"14:30","steppedFreight":"2元运费","dingshida":true},{"promiseTimeText":"14:30-15:00","promiseStartTime":"14:30","promiseEndTime":"15:00","steppedFreight":"2元运费","dingshida":true},{"promiseTimeText":"15:00-15:30","promiseStartTime":"15:00","promiseEndTime":"15:30","steppedFreight":"2元运费","dingshida":true},{"promiseTimeText":"15:30-16:00","promiseStartTime":"15:30","promiseEndTime":"16:00","steppedFreight":"2元运费","dingshida":true},{"promiseTimeText":"16:00-16:30","promiseStartTime":"16:00","promiseEndTime":"16:30","steppedFreight":"2元运费","dingshida":true},{"promiseTimeText":"16:30-17:00","promiseStartTime":"16:30","promiseEndTime":"17:00","steppedFreight":"2元运费","dingshida":true},{"promiseTimeText":"17:00-17:30","promiseStartTime":"17:00","promiseEndTime":"17:30","steppedFreight":"2元运费","dingshida":true},{"promiseTimeText":"17:30-18:00","promiseStartTime":"17:30","promiseEndTime":"18:00","steppedFreight":"2元运费","dingshida":true},{"promiseTimeText":"18:00-18:30","promiseStartTime":"18:00","promiseEndTime":"18:30","steppedFreight":"2元运费","dingshida":true},{"promiseTimeText":"18:30-19:00","promiseStartTime":"18:30","promiseEndTime":"19:00","steppedFreight":"2元运费","dingshida":true},{"promiseTimeText":"19:00-19:30","promiseStartTime":"19:00","promiseEndTime":"19:30","steppedFreight":"2元运费","dingshida":true},{"promiseTimeText":"19:30-20:00","promiseStartTime":"19:30","promiseEndTime":"20:00","steppedFreight":"2元运费","dingshida":true},{"promiseTimeText":"20:00-20:30","promiseStartTime":"20:00","promiseEndTime":"20:30","steppedFreight":"2元运费","dingshida":true},{"promiseTimeText":"20:30-21:00","promiseStartTime":"20:30","promiseEndTime":"21:00","steppedFreight":"2元运费","dingshida":true}]}]},"noPushTime":{"pushTimeFlag":true,"nonPushTimeText":"立即送达"},"nonPushTimeText":"立即送达","deliverTimeFlag":true},"group":"1","changeNum":false},{"show":false,"moduleKey":"bookInfo","title":"订购人信息","defaultText":"","type":0,"data":"","group":"1","changeNum":false},{"show":false,"moduleKey":"artistInfo","title":"手艺人","defaultText":"","type":0,"data":"","group":"1","changeNum":false},{"show":true,"moduleKey":"payMethod","title":"支付方式","defaultText":"","type":0,"data":{"payType":4,"payTypeDefault":4,"paymentTextDTOList":[{"payType":4,"payText":"<font size='3' color='#555555' class='onlinePay_text'>在线支付<\/font>&nbsp;&nbsp;<font size='2' color='#999999' class='onlinePay_prompt'>(微信/阿凡提支付)<\/font>"}]},"group":"2","changeNum":false},{"show":true,"moduleKey":"conponInfo","title":"优惠券","type":0,"data":{"voucherVO":{"show":true},"couponVO":{"show":false},"promoteType":true},"group":"3","changeNum":false},{"show":true,"moduleKey":"orderMark","defaultText":"订单备注（30字以内）","type":0,"data":"","group":"4","changeNum":false},{"show":true,"moduleKey":"productInfo","defaultText":"","type":0,"data":[{"skuId":2001863086,"name":"欧莱雅男士炭爽冰感洁面膏100ml/支","price":3900,"quantity":1,"img":"https://img30.360buyimg.com/n7/jfs/t2068/303/2909639208/110599/b6767a09/56f7b11aN1bc84244.jpg","maxPurchaseNum":0,"minPurchaseNum":0,"editorPurchaseNum":0,"money":3900}],"group":"5","changeNum":false},{"show":true,"moduleKey":"moneyInfo","defaultText":"","type":0,"data":[{"name":"商品金额","value":"￥39.00","color":"#0F0F0F"},{"name":"运费金额","value":"￥2.00","color":"#0F0F0F","desc":"本单运费共计2元，包括<br/>·基础运费 2元<br/>","deliverType":""}],"group":"5","changeNum":false},{"show":true,"moduleKey":"disMoneyInfo","defaultText":"","type":0,"data":[{"name":"满减优惠","value":"-￥7.00","color":"#0F0F0F","flagColor":"#6CC272","flagText":"满减"}],"group":"5","changeNum":false},{"show":true,"moduleKey":"outOfStockConfig","title":"所购商品如遇缺货，您需要：","defaultText":"其它商品继续配送（缺货商品退款）,有缺货直接取消订单,缺货时电话与我沟通","type":0,"data":["其它商品继续配送（缺货商品退款）","有缺货直接取消订单","缺货时电话与我沟通"],"group":"6","changeNum":false},{"show":true,"moduleKey":"submitInfo","title":"提交订单","defaultText":"","type":0,"data":"","group":"7","changeNum":false}]
     * settleType : 0
     * totalDiscount : ￥7.00
     * totalCost : ￥41.00
     * totalWeight : 0.1
     * distributionType : 1
     */
    
    public AccountResult result;
    
    public MarketSettleGetCurrentAccount(String id, String code, String msg, boolean success) {
        super(id, code, msg, success);
    }
    
    public static MarketSettleGetCurrentAccount objectFromData(String str) {
        
        return new Gson().fromJson(str, MarketSettleGetCurrentAccount.class);
    }
    
    public static MarketSettleGetCurrentAccount objectFromData(String str, String key) {
        
        try {
            JSONObject jsonObject = new JSONObject(str);
            
            return new Gson().fromJson(jsonObject.getString(str), MarketSettleGetCurrentAccount.class);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        
        return null;
    }
    
    public static List<MarketSettleGetCurrentAccount> arrayMarketSettleGetCurrentAccountFromData(String str) {
        
        Type listType = new TypeToken<ArrayList<MarketSettleGetCurrentAccount>>() {
        }.getType();
        
        return new Gson().fromJson(str, listType);
    }
    

}
