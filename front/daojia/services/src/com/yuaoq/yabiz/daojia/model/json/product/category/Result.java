package com.yuaoq.yabiz.daojia.model.json.product.category;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import org.json.JSONException;
import org.json.JSONObject;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by changsy on 16/9/6.
 */
public class Result {
    public String solution;
    public String cacheTime;
    public int productCountLimit;
    public Object onlinedebugId;
    /**
     * catId : 3
     * name : 水果蔬菜
     * groupList :
     * cateActivityList : null
     * user_action : {"catename1":"水果蔬菜"}
     */
    
    public List<FirstTabCate> firstTabCate;
    
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
    
    
}