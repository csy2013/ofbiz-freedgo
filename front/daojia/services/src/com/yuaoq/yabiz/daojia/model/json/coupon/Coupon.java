package com.yuaoq.yabiz.daojia.model.json.coupon;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import org.json.JSONException;
import org.json.JSONObject;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by changsy on 16/9/15.
 */
public class Coupon {
    
    public String userPin;
    public String couponName;
    public String couponCode;
    public String venderName;
    public String beginTime;
    public String endTime;
    public String createTime;
    public String modifyTime;
    public int couponType;
    public int source;
    public int state;
    public int quota;
    public int minOrderAmount;
    public String limitType;
    public String beginTimeStr;
    public String endTimeStr;
    public int couponSignNew;
    
    public static Coupon objectFromData(String str) {
        
        return new Gson().fromJson(str, Coupon.class);
    }
    
    public static Coupon objectFromData(String str, String key) {
        
        try {
            JSONObject jsonObject = new JSONObject(str);
            
            return new Gson().fromJson(jsonObject.getString(str), Coupon.class);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        
        return null;
    }
    
    public static List<Coupon> arrayCouponListFromData(String str) {
        
        Type listType = new TypeToken<ArrayList<Coupon>>() {
        }.getType();
        
        return new Gson().fromJson(str, listType);
    }
}
