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
public class DeliveryInfoList {
    public String title;
    public int valueFlag;
    
    public String value;
    
    public DeliveryInfoList(String title, int valueFlag) {
        this.title = title;
        this.valueFlag = valueFlag;
    }
    
    public DeliveryInfoList(String title, int valueFlag, String value) {
        this.title = title;
        this.valueFlag = valueFlag;
        this.value = value;
    }
    
    public static DeliveryInfoList objectFromData(String str) {
        
        return new Gson().fromJson(str, DeliveryInfoList.class);
    }
    
    public static DeliveryInfoList objectFromData(String str, String key) {
        
        try {
            JSONObject jsonObject = new JSONObject(str);
            
            return new Gson().fromJson(jsonObject.getString(str), DeliveryInfoList.class);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        
        return null;
    }
    
    public static List<DeliveryInfoList> arrayDeliveryInfoListFromData(String str) {
        
        Type listType = new TypeToken<ArrayList<DeliveryInfoList>>() {
        }.getType();
        
        return new Gson().fromJson(str, listType);
    }
}
