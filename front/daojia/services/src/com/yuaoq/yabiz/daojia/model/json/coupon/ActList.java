package com.yuaoq.yabiz.daojia.model.json.coupon;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import org.json.JSONException;
import org.json.JSONObject;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by changsy on 2016/10/20.
 */
public class ActList {
    
    
    public int orgCode;
    public String storeId;
    public String storeName;
    public String logo;
    public List<Tags> tags;
    
    public static ActList objectFromData(String str) {
        
        return new Gson().fromJson(str, ActList.class);
    }
    
    public static ActList objectFromData(String str, String key) {
        
        try {
            JSONObject jsonObject = new JSONObject(str);
            
            return new Gson().fromJson(jsonObject.getString(str), ActList.class);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        
        return null;
    }
    
    public static List<ActList> arrayActListFromData(String str) {
        
        Type listType = new TypeToken<ArrayList<ActList>>() {
        }.getType();
        
        return new Gson().fromJson(str, listType);
    }
    
    public static class Tags {
        public String name;
        public String iconText;
        public int type;
        public int belongIndustry;
        public String words;
        public int activityRange;
        public String colorCode;
        
        public static Tags objectFromData(String str) {
            
            return new Gson().fromJson(str, Tags.class);
        }
        
        public static Tags objectFromData(String str, String key) {
            
            try {
                JSONObject jsonObject = new JSONObject(str);
                
                return new Gson().fromJson(jsonObject.getString(str), Tags.class);
            } catch (JSONException e) {
                e.printStackTrace();
            }
            
            return null;
        }
        
        public static List<Tags> arrayTagsFromData(String str) {
            
            Type listType = new TypeToken<ArrayList<Tags>>() {
            }.getType();
            
            return new Gson().fromJson(str, listType);
        }
    }
}
