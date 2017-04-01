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

public class Config {
    
    public String userAction;
    /**
     * pageSize : 10
     * totalCount : 68
     * recommendStore : true
     */
    
    public StoreConfig storeConfig;
    
    public Config(String userAction, StoreConfig storeConfig) {
        this.userAction = userAction;
        this.storeConfig = storeConfig;
    }
    
    public static Config objectFromData(String str) {
        
        return new Gson().fromJson(str, Config.class);
    }
    
    public static Config objectFromData(String str, String key) {
        
        try {
            JSONObject jsonObject = new JSONObject(str);
            
            return new Gson().fromJson(jsonObject.getString(str), Config.class);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        
        return null;
    }
    
    public static List<Config> arrayConfigFromData(String str) {
        
        Type listType = new TypeToken<ArrayList<Config>>() {
        }.getType();
        
        return new Gson().fromJson(str, listType);
    }
    
    
}