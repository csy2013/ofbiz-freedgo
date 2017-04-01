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
public class CatIds {
    public String categoryId;
    public Object categoryName;
    public int level;
    
    public static CatIds objectFromData(String str) {
        
        return new Gson().fromJson(str, CatIds.class);
    }
    
    public static CatIds objectFromData(String str, String key) {
        
        try {
            JSONObject jsonObject = new JSONObject(str);
            
            return new Gson().fromJson(jsonObject.getString(str), CatIds.class);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        
        return null;
    }
    
    public static List<CatIds> arrayCatIdsFromData(String str) {
        
        Type listType = new TypeToken<ArrayList<CatIds>>() {
        }.getType();
        
        return new Gson().fromJson(str, listType);
    }
    
    public String getCategoryId() {
        return categoryId;
    }
    
    public void setCategoryId(String categoryId) {
        this.categoryId = categoryId;
    }
    
    public Object getCategoryName() {
        return categoryName;
    }
    
    public void setCategoryName(Object categoryName) {
        this.categoryName = categoryName;
    }
    
    public int getLevel() {
        return level;
    }
    
    public void setLevel(int level) {
        this.level = level;
    }
}
