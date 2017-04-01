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
public class CouponList {
    
    
    /**
     * id : user.couponlist
     * code : 0
     * msg : 成功
     * result : [{"userPin":"JD_284ubc3b41a4","couponName":"8.11-8.16订单红包裂变立减2元","couponCode":"acad1c33efec49129a623e8a8a840386#2053017300","venderName":"全场券（除外卖）","beginTime":"2016-08-13 12:16:50","endTime":"2016-08-16 23:59:59","createTime":"2016-08-13 12:16:50","modifyTime":"2016-08-13 12:16:50","couponType":2,"source":1,"state":3,"quota":200,"minOrderAmount":0,"limitType":"VENDER","beginTimeStr":"2016.08.13","endTimeStr":"2016.08.16","couponSignNew":1}]
     * success : true
     */
    
    public String id;
    public String code;
    public String msg;
    public boolean success;
    /**
     * userPin : JD_284ubc3b41a4
     * couponName : 8.11-8.16订单红包裂变立减2元
     * couponCode : acad1c33efec49129a623e8a8a840386#2053017300
     * venderName : 全场券（除外卖）
     * beginTime : 2016-08-13 12:16:50
     * endTime : 2016-08-16 23:59:59
     * createTime : 2016-08-13 12:16:50
     * modifyTime : 2016-08-13 12:16:50
     * couponType : 2
     * source : 1
     * state : 3
     * quota : 200
     * minOrderAmount : 0
     * limitType : VENDER
     * beginTimeStr : 2016.08.13
     * endTimeStr : 2016.08.16
     * couponSignNew : 1
     */
    
    public List<Coupon> result;
    
    public static CouponList objectFromData(String str) {
        
        return new Gson().fromJson(str, CouponList.class);
    }
    
    public static CouponList objectFromData(String str, String key) {
        
        try {
            JSONObject jsonObject = new JSONObject(str);
            
            return new Gson().fromJson(jsonObject.getString(str), CouponList.class);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        
        return null;
    }
    
    public static List<CouponList> arrayCouponListFromData(String str) {
        
        Type listType = new TypeToken<ArrayList<CouponList>>() {
        }.getType();
        
        return new Gson().fromJson(str, listType);
    }
    

}
