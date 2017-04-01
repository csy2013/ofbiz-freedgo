package com.yuaoq.yabiz.daojia.model.json.cart.marketSettleGetCurrentAccount;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import org.json.JSONException;
import org.json.JSONObject;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by changsy on 16/9/18.
 */
public class PaymentMethodData {
    
    
    /**
     * payType : 4
     * payTypeDefault : 4
     * paymentTextDTOList : [{"payType":4,"payText":"<font size='3' color='#555555' class='onlinePay_text'>在线支付<\/font>&nbsp;&nbsp;<font size='2' color='#999999' class='onlinePay_prompt'>(微信/阿凡提支付)<\/font>"}]
     */
    
    public int payType;
    public int payTypeDefault;
    /**
     * payType : 4
     * payText : <font size='3' color='#555555' class='onlinePay_text'>在线支付</font>&nbsp;&nbsp;<font size='2' color='#999999' class='onlinePay_prompt'>(微信/阿凡提支付)</font>
     */
    
    public List<PaymentTextDTOList> paymentTextDTOList;
    
    public static PaymentMethodData objectFromData(String str) {
        
        return new Gson().fromJson(str, PaymentMethodData.class);
    }
    
    public static PaymentMethodData objectFromData(String str, String key) {
        
        try {
            JSONObject jsonObject = new JSONObject(str);
            
            return new Gson().fromJson(jsonObject.getString(str), PaymentMethodData.class);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        
        return null;
    }
    
    public static List<PaymentMethodData> arrayPaymentMethodDataFromData(String str) {
        
        Type listType = new TypeToken<ArrayList<PaymentMethodData>>() {
        }.getType();
        
        return new Gson().fromJson(str, listType);
    }
    
    public static class PaymentTextDTOList {
        public int payType;
        public String payText;
        
        public static PaymentTextDTOList objectFromData(String str) {
            
            return new Gson().fromJson(str, PaymentTextDTOList.class);
        }
        
        public static PaymentTextDTOList objectFromData(String str, String key) {
            
            try {
                JSONObject jsonObject = new JSONObject(str);
                
                return new Gson().fromJson(jsonObject.getString(str), PaymentTextDTOList.class);
            } catch (JSONException e) {
                e.printStackTrace();
            }
            
            return null;
        }
        
        public static List<PaymentTextDTOList> arrayPaymentTextDTOListFromData(String str) {
            
            Type listType = new TypeToken<ArrayList<PaymentTextDTOList>>() {
            }.getType();
            
            return new Gson().fromJson(str, listType);
        }
    }
}
