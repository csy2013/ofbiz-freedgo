package com.yuaoq.yabiz.daojia.model.json.product.category;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import org.json.JSONException;
import org.json.JSONObject;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by changsy on 16/9/9.
 */
public class ResultListVO {
    
    
    public String name;
    public String count;
    public int type;
    
    public static ResultListVO objectFromData(String str) {
        
        return new Gson().fromJson(str, ResultListVO.class);
    }
    
    public static ResultListVO objectFromData(String str, String key) {
        
        try {
            JSONObject jsonObject = new JSONObject(str);
            
            return new Gson().fromJson(jsonObject.getString(str), ResultListVO.class);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        
        return null;
    }
    
    public static List<ResultListVO> arrayResultListVOFromData(String str) {
        
        Type listType = new TypeToken<ArrayList<ResultListVO>>() {
        }.getType();
        
        return new Gson().fromJson(str, listType);
    }
    
    public String getName() {
        return name;
    }
    
    public void setName(String name) {
        this.name = name;
    }
    
    public String getCount() {
        return count;
    }
    
    public void setCount(String count) {
        this.count = count;
    }
    
    public int getType() {
        return type;
    }
    
    public void setType(int type) {
        this.type = type;
    }
}
