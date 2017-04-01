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
public class PriceInfoList {
    public String title;
    public String value;
    public int valueFlag;
    public String color;
    
    public PriceInfoList(String title, String value, int valueFlag) {
        this.title = title;
        this.value = value;
        this.valueFlag = valueFlag;
    }
    
    public PriceInfoList(String value, int valueFlag, String color) {
        this.value = value;
        this.valueFlag = valueFlag;
        this.color = color;
    }
    
    public static PriceInfoList objectFromData(String str) {
        
        return new Gson().fromJson(str, PriceInfoList.class);
    }
    
    public static PriceInfoList objectFromData(String str, String key) {
        
        try {
            JSONObject jsonObject = new JSONObject(str);
            
            return new Gson().fromJson(jsonObject.getString(str), PriceInfoList.class);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        
        return null;
    }
    
    public static List<PriceInfoList> arrayPriceInfoListFromData(String str) {
        
        Type listType = new TypeToken<ArrayList<PriceInfoList>>() {
        }.getType();
        
        return new Gson().fromJson(str, listType);
    }
}