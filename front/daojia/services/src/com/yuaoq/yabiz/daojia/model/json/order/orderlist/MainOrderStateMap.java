package com.yuaoq.yabiz.daojia.model.json.order.orderlist;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import org.json.JSONException;
import org.json.JSONObject;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by changsy on 16/9/19.
 */
public class MainOrderStateMap {
    public String orderState;
    public String orderStateName;
    public String orderColor;
    
    public MainOrderStateMap(String orderState, String orderStateName, String orderColor) {
        this.orderState = orderState;
        this.orderStateName = orderStateName;
        this.orderColor = orderColor;
    }
    
    public static MainOrderStateMap objectFromData(String str) {
        
        return new Gson().fromJson(str, MainOrderStateMap.class);
    }
    
    public static MainOrderStateMap objectFromData(String str, String key) {
        
        try {
            JSONObject jsonObject = new JSONObject(str);
            
            return new Gson().fromJson(jsonObject.getString(str), MainOrderStateMap.class);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        
        return null;
    }
    
    public static List<MainOrderStateMap> arrayMainOrderStateMapFromData(String str) {
        
        Type listType = new TypeToken<ArrayList<MainOrderStateMap>>() {
        }.getType();
        
        return new Gson().fromJson(str, listType);
    }
}
