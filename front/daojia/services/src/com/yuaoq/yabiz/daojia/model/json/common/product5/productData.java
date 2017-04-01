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
public class productData {
    
    
    public String imgUrl;
    public String userAction;
    public String venderId;
    public String storeId;
    public String storeName;
    public String backGroundUrl;
    public SkuInfo skuInfo;
    
    public productData(String imgUrl, String userAction, String venderId, String storeId, String storeName, String backGroundUrl, SkuInfo skuInfo) {
        this.imgUrl = imgUrl;
        this.userAction = userAction;
        this.venderId = venderId;
        this.storeId = storeId;
        this.storeName = storeName;
        this.backGroundUrl = backGroundUrl;
        this.skuInfo = skuInfo;
    }
    
    public String getImgUrl() {
        return imgUrl;
    }
    
    public void setImgUrl(String imgUrl) {
        this.imgUrl = imgUrl;
    }
    
    public String getUserAction() {
        return userAction;
    }
    
    public void setUserAction(String userAction) {
        this.userAction = userAction;
    }
    
    public String getVenderId() {
        return venderId;
    }
    
    public void setVenderId(String venderId) {
        this.venderId = venderId;
    }
    
    public String getStoreId() {
        return storeId;
    }
    
    public void setStoreId(String storeId) {
        this.storeId = storeId;
    }
    
    public String getStoreName() {
        return storeName;
    }
    
    public void setStoreName(String storeName) {
        this.storeName = storeName;
    }
    
    public String getBackGroundUrl() {
        return backGroundUrl;
    }
    
    public void setBackGroundUrl(String backGroundUrl) {
        this.backGroundUrl = backGroundUrl;
    }
    
    public SkuInfo getSkuInfo() {
        return skuInfo;
    }
    
    public void setSkuInfo(SkuInfo skuInfo) {
        this.skuInfo = skuInfo;
    }
    
    public static productData objectFromData(String str) {
        
        return new Gson().fromJson(str, productData.class);
    }
    
    public static productData objectFromData(String str, String key) {
        
        try {
            JSONObject jsonObject = new JSONObject(str);
            
            return new Gson().fromJson(jsonObject.getString(str), productData.class);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        
        return null;
    }
    
    public static List<productData> arrayproductDataFromData(String str) {
        
        Type listType = new TypeToken<ArrayList<productData>>() {
        }.getType();
        
        return new Gson().fromJson(str, listType);
    }
    
   
}
