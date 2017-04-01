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
public class OrderStateMap {
    public int stateId;
    public String stateTitle;
    public String stateDesc;
    public String stateTime;
    public String stateIcon;
    public String functionNumber;
    public String phoneNumType;
    
    public OrderStateMap(int stateId, String stateTitle, String stateDesc, String stateTime, String stateIcon) {
        this.stateId = stateId;
        this.stateTitle = stateTitle;
        this.stateDesc = stateDesc;
        this.stateTime = stateTime;
        this.stateIcon = stateIcon;
    }
    
    public OrderStateMap(int stateId, String stateTitle, String stateDesc, String stateTime, String stateIcon, String functionNumber, String phoneNumType) {
        this.stateId = stateId;
        this.stateTitle = stateTitle;
        this.stateDesc = stateDesc;
        this.stateTime = stateTime;
        this.stateIcon = stateIcon;
        this.functionNumber = functionNumber;
        this.phoneNumType = phoneNumType;
    }
    
    public static OrderStateMap objectFromData(String str) {
        
        return new Gson().fromJson(str, OrderStateMap.class);
    }
    
    public static OrderStateMap objectFromData(String str, String key) {
        
        try {
            JSONObject jsonObject = new JSONObject(str);
            
            return new Gson().fromJson(jsonObject.getString(str), OrderStateMap.class);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        
        return null;
    }
    
    public static List<OrderStateMap> arrayOrderStateMapFromData(String str) {
        
        Type listType = new TypeToken<ArrayList<OrderStateMap>>() {
        }.getType();
        
        return new Gson().fromJson(str, listType);
    }
}
