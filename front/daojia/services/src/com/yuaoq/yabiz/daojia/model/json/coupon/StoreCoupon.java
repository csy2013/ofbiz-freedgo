package com.yuaoq.yabiz.daojia.model.json.coupon;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import org.json.JSONException;
import org.json.JSONObject;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by changsy on 2016/10/20.
 */
public class StoreCoupon {
    
    
    public String couponCode;
    public int couponValue;
    public int couponType;
    public int couponTotal;
    public int receiveTotal;
    public boolean receiveOver;
    public String showInfo;
    public String showDes;
    public int maxReceive;
    public int maxReceiveDay;
    public boolean isGain;
    public int minOrderAmount;
    public int expiryType;
    public int expirydays;
    public String useTime;
    public String useRuleRemark;
    public String storeId;
    public String orgCode;
    public int couponCanGet;
    public boolean gain;
    
    public static StoreCoupon objectFromData(String str) {
        
        return new Gson().fromJson(str, StoreCoupon.class);
    }
    
    public static StoreCoupon objectFromData(String str, String key) {
        
        try {
            JSONObject jsonObject = new JSONObject(str);
            
            return new Gson().fromJson(jsonObject.getString(str), StoreCoupon.class);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        
        return null;
    }
    
    public static List<StoreCoupon> arrayStoreCouponFromData(String str) {
        
        Type listType = new TypeToken<ArrayList<StoreCoupon>>() {
        }.getType();
        
        return new Gson().fromJson(str, listType);
    }
}
