package com.yuaoq.yabiz.daojia.model.json.product.productDetail;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import org.json.JSONException;
import org.json.JSONObject;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by changsy on 16/9/4.
 */
public class ProductImage {
    public String small;
    public String big;
    public String share;
    
    public static ProductImage objectFromData(String str) {
        
        return new Gson().fromJson(str, ProductImage.class);
    }
    
    public static ProductImage objectFromData(String str, String key) {
        
        try {
            JSONObject jsonObject = new JSONObject(str);
            
            return new Gson().fromJson(jsonObject.getString(str), ProductImage.class);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        
        return null;
    }
    
    public static List<ProductImage> arrayImageFromData(String str) {
        
        Type listType = new TypeToken<ArrayList<ProductImage>>() {
        }.getType();
        
        return new Gson().fromJson(str, listType);
    }
}