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
public class OrderSubmit extends BaseResult {
    
    
    /**
     * orderId : 100001039271170
     * orderDate : 2016-08-20 18:08:17
     * orderPrice : 590
     * orderStateName : 在线支付
     * deliveryTime : 2016-08-20 20:08:00
     * savedAddr : true
     */
    
    public Result result;
    public boolean expect;
    public boolean transfer;
    
    public OrderSubmit(String id, String code, String msg, boolean success) {
        super(id, code, msg, success);
    }
    
    public static OrderSubmit objectFromData(String str) {
        
        return new Gson().fromJson(str, OrderSubmit.class);
    }
    
    public static OrderSubmit objectFromData(String str, String key) {
        
        try {
            JSONObject jsonObject = new JSONObject(str);
            
            return new Gson().fromJson(jsonObject.getString(str), OrderSubmit.class);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        
        return null;
    }
    
    public static List<OrderSubmit> arrayOrderSubmitFromData(String str) {
        
        Type listType = new TypeToken<ArrayList<OrderSubmit>>() {
        }.getType();
        
        return new Gson().fromJson(str, listType);
    }
    
    public static class Result {
        public String orderId;
        public String orderDate;
        public int orderPrice;
        public String orderStateName;
        public String deliveryTime;
        public boolean savedAddr;
        
        public static Result objectFromData(String str) {
            
            return new Gson().fromJson(str, Result.class);
        }
        
        public static Result objectFromData(String str, String key) {
            
            try {
                JSONObject jsonObject = new JSONObject(str);
                
                return new Gson().fromJson(jsonObject.getString(str), Result.class);
            } catch (JSONException e) {
                e.printStackTrace();
            }
            
            return null;
        }
        
        public static List<Result> arrayResultFromData(String str) {
            
            Type listType = new TypeToken<ArrayList<Result>>() {
            }.getType();
            
            return new Gson().fromJson(str, listType);
        }
    }
}
