package com.yuaoq.yabiz.daojia.model.json.common.store;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import org.json.JSONException;
import org.json.JSONObject;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by changsy on 16/8/31.
 */
public class ServiceTimes {
    
    public String startTime;
    public String endTime;
    public ServiceTimes(String startTime, String endTime) {
        this.startTime = startTime;
        this.endTime = endTime;
    }
    
    public static ServiceTimes objectFromData(String str) {
        
        return new Gson().fromJson(str, ServiceTimes.class);
    }
    
    public static ServiceTimes objectFromData(String str, String key) {
        
        try {
            JSONObject jsonObject = new JSONObject(str);
            
            return new Gson().fromJson(jsonObject.getString(str), ServiceTimes.class);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        
        return null;
    }
    
    public static List<ServiceTimes> arrayServiceTimesFromData(String str) {
        
        Type listType = new TypeToken<ArrayList<ServiceTimes>>() {
        }.getType();
        
        return new Gson().fromJson(str, listType);
    }
    
}
