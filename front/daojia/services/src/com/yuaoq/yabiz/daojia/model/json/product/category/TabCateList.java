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
public class TabCateList {
    public int catId;
    public String name;
    public String imgUrl;
    public int productConut;
    public String user_action;
    /**
     * categoryId : 20315
     * categoryName : null
     * level : 3
     */
    
    public List<CatIds> catIds;
    
    public static TabCateList objectFromData(String str) {
        
        return new Gson().fromJson(str, TabCateList.class);
    }
    
    public static TabCateList objectFromData(String str, String key) {
        
        try {
            JSONObject jsonObject = new JSONObject(str);
            
            return new Gson().fromJson(jsonObject.getString(str), TabCateList.class);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        
        return null;
    }
    
    public static List<TabCateList> arrayTabCateListFromData(String str) {
        
        Type listType = new TypeToken<ArrayList<TabCateList>>() {
        }.getType();
        
        return new Gson().fromJson(str, listType);
    }
    
    public int getCatId() {
        return catId;
    }
    
    public void setCatId(int catId) {
        this.catId = catId;
    }
    
    public String getName() {
        return name;
    }
    
    public void setName(String name) {
        this.name = name;
    }
    
    public String getImgUrl() {
        return imgUrl;
    }
    
    public void setImgUrl(String imgUrl) {
        this.imgUrl = imgUrl;
    }
    
    public int getProductConut() {
        return productConut;
    }
    
    public void setProductConut(int productConut) {
        this.productConut = productConut;
    }
    
    public String getUser_action() {
        return user_action;
    }
    
    public void setUser_action(String user_action) {
        this.user_action = user_action;
    }
    
    public List<CatIds> getCatIds() {
        return catIds;
    }
    
    public void setCatIds(List<CatIds> catIds) {
        this.catIds = catIds;
    }
}