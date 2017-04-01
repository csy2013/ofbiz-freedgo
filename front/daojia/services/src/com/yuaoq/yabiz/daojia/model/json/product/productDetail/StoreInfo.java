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
public class StoreInfo {
    public String venderId;
    public String venderName;
    public String venderPhone;
    public String storeId;
    public String storeName;
    
    public static StoreInfo objectFromData(String str) {
        
        return new Gson().fromJson(str, StoreInfo.class);
    }
    
    public static StoreInfo objectFromData(String str, String key) {
        
        try {
            JSONObject jsonObject = new JSONObject(str);
            
            return new Gson().fromJson(jsonObject.getString(str), StoreInfo.class);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        
        return null;
    }
    
    public static List<StoreInfo> arrayStoreInfoFromData(String str) {
        
        Type listType = new TypeToken<ArrayList<StoreInfo>>() {
        }.getType();
        
        return new Gson().fromJson(str, listType);
    }
}
