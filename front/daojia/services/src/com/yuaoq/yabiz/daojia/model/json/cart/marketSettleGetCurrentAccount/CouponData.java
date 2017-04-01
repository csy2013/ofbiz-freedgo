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
public class CouponData {
    
    
    /**
     * show : true
     */
    
    public VoucherVO voucherVO;
    /**
     * voucherVO : {"show":true}
     * couponVO : {"show":false}
     * promoteType : true
     */
    
    public VoucherVO couponVO;
    public boolean promoteType;
    
    public static CouponData objectFromData(String str) {
        
        return new Gson().fromJson(str, CouponData.class);
    }
    
    public static CouponData objectFromData(String str, String key) {
        
        try {
            JSONObject jsonObject = new JSONObject(str);
            
            return new Gson().fromJson(jsonObject.getString(str), CouponData.class);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        
        return null;
    }
    
    public static List<CouponData> arrayCouponDataFromData(String str) {
        
        Type listType = new TypeToken<ArrayList<CouponData>>() {
        }.getType();
        
        return new Gson().fromJson(str, listType);
    }
    
    public static class VoucherVO {
        public boolean show;
        
        public static VoucherVO objectFromData(String str) {
            
            return new Gson().fromJson(str, VoucherVO.class);
        }
        
        public static VoucherVO objectFromData(String str, String key) {
            
            try {
                JSONObject jsonObject = new JSONObject(str);
                
                return new Gson().fromJson(jsonObject.getString(str), VoucherVO.class);
            } catch (JSONException e) {
                e.printStackTrace();
            }
            
            return null;
        }
        
        public static List<VoucherVO> arrayVoucherVOFromData(String str) {
            
            Type listType = new TypeToken<ArrayList<VoucherVO>>() {
            }.getType();
            
            return new Gson().fromJson(str, listType);
        }
    }
}
