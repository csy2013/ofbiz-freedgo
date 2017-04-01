package com.yuaoq.yabiz.daojia.model.json.product.category.categorySearchByStore;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.yuaoq.yabiz.daojia.model.json.product.ProductInfo;
import org.json.JSONException;
import org.json.JSONObject;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by changsy on 16/9/7.
 */
public class StoreSkuList {
    public int page;
    public int count;
    
    public StoreInfo store;
    
    
    public List<ProductInfo> skuList;
    
    public static StoreSkuList objectFromData(String str) {
        
        return new Gson().fromJson(str, StoreSkuList.class);
    }
    
    public static StoreSkuList objectFromData(String str, String key) {
        
        try {
            JSONObject jsonObject = new JSONObject(str);
            
            return new Gson().fromJson(jsonObject.getString(str), StoreSkuList.class);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        
        return null;
    }
    
    public static List<StoreSkuList> arrayStoreSkuListFromData(String str) {
        
        Type listType = new TypeToken<ArrayList<StoreSkuList>>() {
        }.getType();
        
        return new Gson().fromJson(str, listType);
    }
    
    
    public int getPage() {
        return page;
    }
    
    public void setPage(int page) {
        this.page = page;
    }
    
    public int getCount() {
        return count;
    }
    
    public void setCount(int count) {
        this.count = count;
    }
    
    public StoreInfo getStore() {
        return store;
    }
    
    public void setStore(StoreInfo store) {
        this.store = store;
    }
    
    public List<ProductInfo> getSkuList() {
        return skuList;
    }
    
    public void setSkuList(List<ProductInfo> skuList) {
        this.skuList = skuList;
    }
}
