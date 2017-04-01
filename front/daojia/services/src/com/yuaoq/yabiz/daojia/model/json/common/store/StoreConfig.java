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
public class StoreConfig {
    
    
    public int pageSize;
    public int totalCount;
    public boolean recommendStore;
    
    public StoreConfig(int pageSize, int totalCount, boolean recommendStore) {
        this.pageSize = pageSize;
        this.totalCount = totalCount;
        this.recommendStore = recommendStore;
    }
    
    public static StoreConfig objectFromData(String str) {
        
        return new Gson().fromJson(str, StoreConfig.class);
    }
    
    public static StoreConfig objectFromData(String str, String key) {
        
        try {
            JSONObject jsonObject = new JSONObject(str);
            
            return new Gson().fromJson(jsonObject.getString(str), StoreConfig.class);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        
        return null;
    }
    
    public static List<StoreConfig> arrayStoreConfigFromData(String str) {
        
        Type listType = new TypeToken<ArrayList<StoreConfig>>() {
        }.getType();
        
        return new Gson().fromJson(str, listType);
    }
    
}
