package com.yuaoq.yabiz.daojia.model.json.order;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.yuaoq.yabiz.daojia.model.json.BaseResult;
import org.json.JSONException;
import org.json.JSONObject;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by changsy on 16/9/19.
 */
public class CashierPageInfo extends BaseResult {
    
    
    /**
     * orderId : 100001039266434
     * amount : 7.4
     * notifyUrl : ./index.html#orderopresult/result:success/payType:pay/orderId:100001039266434
     * payWays : [{"payWay":"10","payName":"微信支付","status":"0","defaultCopy":"推荐安装微信5.0及以上版本使用","iconUrl":"http://storage.jd.com/daojia/wxpay-icon.png"},{"payWay":"20","payName":"阿凡提支付","status":"0","defaultCopy":"阿凡提旗下 快捷支付","iconUrl":"http://storage.jd.com/daojia/icon-jdpay.png"}]
     * countDownTime : 647919
     */
    
    public PayWaysResult result;
    
    public CashierPageInfo(String id, String code, String msg, boolean success) {
        super(id, code, msg, success);
    }
    
    public static CashierPageInfo objectFromData(String str) {
        
        return new Gson().fromJson(str, CashierPageInfo.class);
    }
    
    public static CashierPageInfo objectFromData(String str, String key) {
        
        try {
            JSONObject jsonObject = new JSONObject(str);
            
            return new Gson().fromJson(jsonObject.getString(str), CashierPageInfo.class);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        
        return null;
    }
    
    public static List<CashierPageInfo> arrayCashierPageInfoFromData(String str) {
        
        Type listType = new TypeToken<ArrayList<CashierPageInfo>>() {
        }.getType();
        
        return new Gson().fromJson(str, listType);
    }
    
    public static class PayWaysResult {
        public String orderId;
        public double amount;
        public String notifyUrl;
        public String countDownTime;
        /**
         * payWay : 10
         * payName : 微信支付
         * status : 0
         * defaultCopy : 推荐安装微信5.0及以上版本使用
         * iconUrl : http://storage.jd.com/daojia/wxpay-icon.png
         */
        
        public List<PayWays> payWays;
        
        public static PayWaysResult objectFromData(String str) {
            
            return new Gson().fromJson(str, PayWaysResult.class);
        }
        
        public static PayWaysResult objectFromData(String str, String key) {
            
            try {
                JSONObject jsonObject = new JSONObject(str);
                
                return new Gson().fromJson(jsonObject.getString(str), PayWaysResult.class);
            } catch (JSONException e) {
                e.printStackTrace();
            }
            
            return null;
        }
        
        public static List<PayWaysResult> arrayResultFromData(String str) {
            
            Type listType = new TypeToken<ArrayList<PayWaysResult>>() {
            }.getType();
            
            return new Gson().fromJson(str, listType);
        }
        
        public String getOrderId() {
            return orderId;
        }
        
        public void setOrderId(String orderId) {
            this.orderId = orderId;
        }
        
        public double getAmount() {
            return amount;
        }
        
        public void setAmount(double amount) {
            this.amount = amount;
        }
        
        public String getNotifyUrl() {
            return notifyUrl;
        }
        
        public void setNotifyUrl(String notifyUrl) {
            this.notifyUrl = notifyUrl;
        }
        
        public String getCountDownTime() {
            return countDownTime;
        }
        
        public void setCountDownTime(String countDownTime) {
            this.countDownTime = countDownTime;
        }
        
        public List<PayWays> getPayWays() {
            return payWays;
        }
        
        public void setPayWays(List<PayWays> payWays) {
            this.payWays = payWays;
        }
    }
}
