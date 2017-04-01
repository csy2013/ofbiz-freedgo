package com.yuaoq.yabiz.daojia.model.json.common.act;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import org.json.JSONException;
import org.json.JSONObject;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Created by changsy on 2016/10/19.
 */
public   class Act1Data {
    public boolean expired;
    public String to;
    public String imgUrl;
    public Map params;
    public String userAction;
    public String imgWidth;
    public String imgHeight;
    
    public Act1Data(boolean expired, String to, String imgUrl, Map params, String userAction, String imgWidth, String imgHeight) {
        this.expired = expired;
        this.to = to;
        this.imgUrl = imgUrl;
        this.params = params;
        this.userAction = userAction;
        this.imgWidth = imgWidth;
        this.imgHeight = imgHeight;
    }
    
    public static Act1Data objectFromData(String str) {
        
        return new Gson().fromJson(str, Act1Data.class);
    }
    
    public static Act1Data objectFromData(String str, String key) {
        
        try {
            JSONObject jsonObject = new JSONObject(str);
            
            return new Gson().fromJson(jsonObject.getString(str), Act1Data.class);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        
        return null;
    }
    
    public static List<Act1Data> arrayDataFromData(String str) {
        
        Type listType = new TypeToken<ArrayList<Act1Data>>() {
        }.getType();
        
        return new Gson().fromJson(str, listType);
    }
    
    
}
