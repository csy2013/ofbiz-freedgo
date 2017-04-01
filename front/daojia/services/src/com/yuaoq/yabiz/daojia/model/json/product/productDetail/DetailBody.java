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
public class DetailBody {
    
    
    /**
     * skuId : 10002
     * storeId : 10000
     * orgCode : CHN
     * buyNum : 1
     * longitude : 118.91616
     * latitude : 31.90052
     * type : 2
     */
    
    public String skuId;
    public String storeId;
    public String orgCode;
    public int buyNum;
    public double longitude;
    public double latitude;
    public int type;
    
    public static DetailBody objectFromData(String str) {
        
        return new Gson().fromJson(str, DetailBody.class);
    }
    
    public static DetailBody objectFromData(String str, String key) {
        
        try {
            JSONObject jsonObject = new JSONObject(str);
            
            return new Gson().fromJson(jsonObject.getString(str), DetailBody.class);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        
        return null;
    }
    
    public static List<DetailBody> arrayDetailBodyFromData(String str) {
        
        Type listType = new TypeToken<ArrayList<DetailBody>>() {
        }.getType();
        
        return new Gson().fromJson(str, listType);
    }
}
