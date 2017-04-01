package com.yuaoq.yabiz.daojia.model.json.common.product5;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import org.json.JSONException;
import org.json.JSONObject;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by changsy on 2016/10/19.
 */
public   class BusyAttrMaps {
    public String pdBgc;
    public String pageBgc;
    public String storeShow;
    
    public static BusyAttrMaps objectFromData(String str) {
        
        return new Gson().fromJson(str, BusyAttrMaps.class);
    }
    
    public static BusyAttrMaps objectFromData(String str, String key) {
        
        try {
            JSONObject jsonObject = new JSONObject(str);
            
            return new Gson().fromJson(jsonObject.getString(str), BusyAttrMaps.class);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        
        return null;
    }
    
    public static List<BusyAttrMaps> arrayBusyAttrMapsFromData(String str) {
        
        Type listType = new TypeToken<ArrayList<BusyAttrMaps>>() {
        }.getType();
        
        return new Gson().fromJson(str, listType);
    }
}
