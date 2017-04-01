package com.yuaoq.yabiz.daojia.model.json.common.channel;

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
public class ChannelDetail extends BaseResult{
    
   
    public Result result;
    
    public ChannelDetail(String id, String code, String msg, boolean success) {
        super(id, code, msg, success);
    }
    
    public static ChannelDetail objectFromData(String str) {
        
        return new Gson().fromJson(str, ChannelDetail.class);
    }
    
    public static ChannelDetail objectFromData(String str, String key) {
        
        try {
            JSONObject jsonObject = new JSONObject(str);
            
            return new Gson().fromJson(jsonObject.getString(str), ChannelDetail.class);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        
        return null;
    }
    
    public static List<ChannelDetail> arrayChannelDetailFromData(String str) {
        
        Type listType = new TypeToken<ArrayList<ChannelDetail>>() {
        }.getType();
        
        return new Gson().fromJson(str, listType);
    }
    
    public static class Result {
       
        
        public Config config;
       
        
        public List<Object> data;
        
        public static Result objectFromData(String str) {
            
            return new Gson().fromJson(str, Result.class);
        }
        
        public static Result objectFromData(String str, String key) {
            
            try {
                JSONObject jsonObject = new JSONObject(str);
                
                return new Gson().fromJson(jsonObject.getString(str), Result.class);
            } catch (JSONException e) {
                e.printStackTrace();
            }
            
            return null;
        }
        
        public static List<Result> arrayResultFromData(String str) {
            
            Type listType = new TypeToken<ArrayList<Result>>() {
            }.getType();
            
            return new Gson().fromJson(str, listType);
        }
        
        public static class Config {
            public String gloabTitle;
            public String isWaimai;
            public String userAction;
            
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
        
    
    }
}
