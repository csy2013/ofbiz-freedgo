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
public class StoreResult {
    
    
    public Config config;
    
    public Data data;
    
    public StoreResult(Config config, Data data) {
        this.config = config;
        this.data = data;
    }
    
    public static StoreResult objectFromData(String str) {
        
        return new Gson().fromJson(str, StoreResult.class);
    }
    
    public static StoreResult objectFromData(String str, String key) {
        
        try {
            JSONObject jsonObject = new JSONObject(str);
            
            return new Gson().fromJson(jsonObject.getString(str), StoreResult.class);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        
        return null;
    }
    
    public static List<StoreResult> arrayResultFromData(String str) {
        
        Type listType = new TypeToken<ArrayList<StoreResult>>() {
        }.getType();
        
        return new Gson().fromJson(str, listType);
    }
    
}
