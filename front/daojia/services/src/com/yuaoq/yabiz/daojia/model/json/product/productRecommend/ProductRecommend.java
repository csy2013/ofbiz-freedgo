package com.yuaoq.yabiz.daojia.model.json.product.productRecommend;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.yuaoq.yabiz.daojia.model.json.BaseResult;
import org.json.JSONException;
import org.json.JSONObject;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by changsy on 16/9/5.
 */
public class ProductRecommend extends BaseResult {
    
    
    public ProductRecommendResult result;
    
    
    public ProductRecommend(String id, String code, String msg, boolean success, ProductRecommendResult result) {
        super(id, code, msg, success);
        this.result = result;
    }
    
    public static ProductRecommend objectFromData(String str) {
        
        return new Gson().fromJson(str, ProductRecommend.class);
    }
    
    public static ProductRecommend objectFromData(String str, String key) {
        
        try {
            JSONObject jsonObject = new JSONObject(str);
            
            return new Gson().fromJson(jsonObject.getString(str), ProductRecommend.class);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        
        return null;
    }
    
    public static List<ProductRecommend> arrayProductRecommendFromData(String str) {
        
        Type listType = new TypeToken<ArrayList<ProductRecommend>>() {
        }.getType();
        
        return new Gson().fromJson(str, listType);
    }
    
    
}
