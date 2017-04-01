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
public class SkuList {
    public String skuId;
    public int checkType;
    public String skuName;
    public String basePrice;
    public String price;
    public String imageUrl;
    public int cartNum;
    public int skuState;
    public String goodsIndex;
    /**
     * start : 1
     * end : 99
     */
    
    public Picker picker;
    public String weight;
    public List<?> tags;
    
    public static SkuList objectFromData(String str) {
        
        return new Gson().fromJson(str, SkuList.class);
    }
    
    public static SkuList objectFromData(String str, String key) {
        
        try {
            JSONObject jsonObject = new JSONObject(str);
            
            return new Gson().fromJson(jsonObject.getString(str), SkuList.class);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        
        return null;
    }
    
    public static List<SkuList> arraySkuListFromData(String str) {
        
        Type listType = new TypeToken<ArrayList<SkuList>>() {
        }.getType();
        
        return new Gson().fromJson(str, listType);
    }
    
    public String getSkuId() {
        return skuId;
    }
    
    public void setSkuId(String skuId) {
        this.skuId = skuId;
    }
    
    public int getCheckType() {
        return checkType;
    }
    
    public void setCheckType(int checkType) {
        this.checkType = checkType;
    }
    
    public String getSkuName() {
        return skuName;
    }
    
    public void setSkuName(String skuName) {
        this.skuName = skuName;
    }
    
    public String getBasePrice() {
        return basePrice;
    }
    
    public void setBasePrice(String basePrice) {
        this.basePrice = basePrice;
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
    
    public Picker getPicker() {
        return picker;
    }
    
    public void setPicker(Picker picker) {
        this.picker = picker;
    }
    
    public String getWeight() {
        return weight;
    }
    
    public void setWeight(String weight) {
        this.weight = weight;
    }
    
    public List<?> getTags() {
        return tags;
    }
    
    public void setTags(List<?> tags) {
        this.tags = tags;
    }
    
    public String getGoodsIndex() {
        return goodsIndex;
    }
    
    public void setGoodsIndex(String goodsIndex) {
        this.goodsIndex = goodsIndex;
    }
}