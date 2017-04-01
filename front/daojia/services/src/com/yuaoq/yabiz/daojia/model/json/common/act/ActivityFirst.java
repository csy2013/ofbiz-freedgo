package com.yuaoq.yabiz.daojia.model.json.common.act;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.yuaoq.yabiz.daojia.model.json.BaseResult;
import com.yuaoq.yabiz.daojia.model.json.common.IndexPage.IndexData;
import org.json.JSONException;
import org.json.JSONObject;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by changsy on 2016/10/18.
 */
public class ActivityFirst extends BaseResult {
    
    
    public  Result result;
    
    public ActivityFirst(String id, String code, String msg, boolean success) {
        super(id, code, msg, success);
    }
    
    public static ActivityFirst objectFromData(String str) {
        
        return new Gson().fromJson(str, ActivityFirst.class);
    }
    
    public static ActivityFirst objectFromData(String str, String key) {
        
        try {
            JSONObject jsonObject = new JSONObject(str);
            
            return new Gson().fromJson(jsonObject.getString(str), ActivityFirst.class);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        
        return null;
    }
    
    public static List<ActivityFirst> arrayActivityFirstFromData(String str) {
        
        Type listType = new TypeToken<ArrayList<ActivityFirst>>() {
        }.getType();
        
        return new Gson().fromJson(str, listType);
    }
    
    public static class Result {
        
        
        public ActivityFirst.Result.Config config;
        
        
        public List<Object> data;
        
        public static ActivityFirst.Result objectFromData(String str) {
            
            return new Gson().fromJson(str, ActivityFirst.Result.class);
        }
        
        public static ActivityFirst.Result objectFromData(String str, String key) {
            
            try {
                JSONObject jsonObject = new JSONObject(str);
                
                return new Gson().fromJson(jsonObject.getString(str), ActivityFirst.Result.class);
            } catch (JSONException e) {
                e.printStackTrace();
            }
            
            return null;
        }
        
        public static List<ActivityFirst.Result> arrayResultFromData(String str) {
            
            Type listType = new TypeToken<ArrayList<ActivityFirst.Result>>() {
            }.getType();
            
            return new Gson().fromJson(str, listType);
        }
        
        public static class Config {
            public String gloabTitle;
            public boolean isCart;
            public boolean isShowStoreButton;
            
            public static ActivityFirst.Result.Config objectFromData(String str) {
                
                return new Gson().fromJson(str, ActivityFirst.Result.Config.class);
            }
            
            public static ActivityFirst.Result.Config objectFromData(String str, String key) {
                
                try {
                    JSONObject jsonObject = new JSONObject(str);
                    
                    return new Gson().fromJson(jsonObject.getString(str), ActivityFirst.Result.Config.class);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                
                return null;
            }
            
            public static List<ActivityFirst.Result.Config> arrayConfigFromData(String str) {
                
                Type listType = new TypeToken<ArrayList<ActivityFirst.Result.Config>>() {
                }.getType();
                
                return new Gson().fromJson(str, listType);
            }
        }
        
        
    }
}
