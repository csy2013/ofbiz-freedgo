package com.yuaoq.yabiz.daojia.model.json.order.orderinfo;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import org.json.JSONException;
import org.json.JSONObject;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by changsy on 16/9/22.
 */
public class OrderCancelReasons {
    public String text;
    public String code;
    
    public OrderCancelReasons(String text, String code) {
        this.text = text;
        this.code = code;
    }
    
    public static OrderCancelReasons objectFromData(String str) {
        
        return new Gson().fromJson(str, OrderCancelReasons.class);
    }
    
    public static OrderCancelReasons objectFromData(String str, String key) {
        
        try {
            JSONObject jsonObject = new JSONObject(str);
            
            return new Gson().fromJson(jsonObject.getString(str), OrderCancelReasons.class);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        
        return null;
    }
    
    public static List<OrderCancelReasons> arrayOrderCancelReasonsFromData(String str) {
        
        Type listType = new TypeToken<ArrayList<OrderCancelReasons>>() {
        }.getType();
        
        return new Gson().fromJson(str, listType);
    }
}