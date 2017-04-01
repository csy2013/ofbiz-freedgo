package com.yuaoq.yabiz.daojia.model.json.common.product5;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import org.json.JSONException;
import org.json.JSONObject;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by changsy on 2016/10/19.
 */
public class Product5Data {
    
    public String imgUrl;
    public String userAction;
    public String venderId;
    public String storeId;
    public String storeName;
    public String backGroundUrl;
    public SkuInfo skuInfo;
    
    public Product5Data() {
    }
    
    public Product5Data(String imgUrl, String userAction, String venderId, String storeId, String storeName, String backGroundUrl, SkuInfo skuInfo) {
        this.imgUrl = imgUrl;
        this.userAction = userAction;
        this.venderId = venderId;
        this.storeId = storeId;
        this.storeName = storeName;
        this.backGroundUrl = backGroundUrl;
        this.skuInfo = skuInfo;
    }
    
    public static Product5Data objectFromData(String str) {
        
        return new Gson().fromJson(str, Product5Data.class);
    }
    
    public static Product5Data objectFromData(String str, String key) {
        
        try {
            JSONObject jsonObject = new JSONObject(str);
            
            return new Gson().fromJson(jsonObject.getString(str), Product5Data.class);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        
        return null;
    }
    
    public static List<Product5Data> arrayDataFromData(String str) {
        
        Type listType = new TypeToken<ArrayList<Product5Data>>() {
        }.getType();
        
        return new Gson().fromJson(str, listType);
    }
    
    
}
