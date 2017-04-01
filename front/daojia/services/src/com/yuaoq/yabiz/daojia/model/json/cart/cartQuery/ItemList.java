package com.yuaoq.yabiz.daojia.model.json.cart.cartQuery;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import org.json.JSONException;
import org.json.JSONObject;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by changsy on 16/9/19.
 */
public class ItemList {
    public String skuId;
    public String price;
    public String imageUrl;
    public int cartNum;
    public int skuState;
    public long updateTime;
    
    public static ItemList objectFromData(String str) {
        
        return new Gson().fromJson(str, ItemList.class);
    }
    
    public static ItemList objectFromData(String str, String key) {
        
        try {
            JSONObject jsonObject = new JSONObject(str);
            
            return new Gson().fromJson(jsonObject.getString(str), ItemList.class);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        
        return null;
    }
    
    public static List<ItemList> arrayItemListFromData(String str) {
        
        Type listType = new TypeToken<ArrayList<ItemList>>() {
        }.getType();
        
        return new Gson().fromJson(str, listType);
    }
    
    public String getSkuId() {
        return skuId;
    }
    
    public void setSkuId(String skuId) {
        this.skuId = skuId;
    }
    
    public String getPrice() {
        return price;
    }
    
    public void setPrice(String price) {
        this.price = price;
    }
    
    public String getImageUrl() {
        return imageUrl;
    }
    
    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }
    
    public int getCartNum() {
        return cartNum;
    }
    
    public void setCartNum(int cartNum) {
        this.cartNum = cartNum;
    }
    
    public int getSkuState() {
        return skuState;
    }
    
    public void setSkuState(int skuState) {
        this.skuState = skuState;
    }
    
    public long getUpdateTime() {
        return updateTime;
    }
    
    public void setUpdateTime(long updateTime) {
        this.updateTime = updateTime;
    }
}
