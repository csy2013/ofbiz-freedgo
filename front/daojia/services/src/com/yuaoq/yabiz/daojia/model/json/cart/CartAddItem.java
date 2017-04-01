package com.yuaoq.yabiz.daojia.model.json.cart;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import org.json.JSONException;
import org.json.JSONObject;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by changsy on 16/9/12.
 */
public class CartAddItem {
    
    
    public String id;
    public String code;
    public String msg;
    public String detail;
    public boolean success;
    
    public static CartAddItem objectFromData(String str) {
        
        return new Gson().fromJson(str, CartAddItem.class);
    }
    
    public static CartAddItem objectFromData(String str, String key) {
        
        try {
            JSONObject jsonObject = new JSONObject(str);
            
            return new Gson().fromJson(jsonObject.getString(str), CartAddItem.class);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        
        return null;
    }
    
    public static List<CartAddItem> arrayCartAddItemFromData(String str) {
        
        Type listType = new TypeToken<ArrayList<CartAddItem>>() {
        }.getType();
        
        return new Gson().fromJson(str, listType);
    }
}
