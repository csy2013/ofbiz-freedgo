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
public class CartQueryResult {
    public String storeId;
    public String orgCode;
    public String storeName;
    public String openJPIndustry;
    public String storeImgUrl;
    public String payMoneyPriceValue;
    public String payMoneyName;
    public int totalNum;
    /**
     * skuId : 2002436595
     * price : 5.00
     * imageUrl : https://img10.360buyimg.com/n7//jfs/t1381/209/1169681626/408421/76c6d88b/55bf28eeNe720dfdb.jpg
     * cartNum : 1
     * skuState : 1
     * updateTime : 1471667525561
     */

    public List<ItemList> itemList;
    
    public CartQueryResult(String storeId, String orgCode, String storeName, String openJPIndustry, String storeImgUrl, String payMoneyPriceValue, String payMoneyName, int totalNum, List<ItemList> itemList) {
        this.storeId = storeId;
        this.orgCode = orgCode;
        this.storeName = storeName;
        this.openJPIndustry = openJPIndustry;
        this.storeImgUrl = storeImgUrl;
        this.payMoneyPriceValue = payMoneyPriceValue;
        this.payMoneyName = payMoneyName;
        this.totalNum = totalNum;
        this.itemList = itemList;
    }
    
    public CartQueryResult(String storeId, String orgCode, String storeName, String openJPIndustry, String storeImgUrl, String payMoneyPriceValue, String payMoneyName, int totalNum) {
        this.storeId = storeId;
        this.orgCode = orgCode;
        this.storeName = storeName;
        this.openJPIndustry = openJPIndustry;
        this.storeImgUrl = storeImgUrl;
        this.payMoneyPriceValue = payMoneyPriceValue;
        this.payMoneyName = payMoneyName;
        this.totalNum = totalNum;
    }
    
    public static CartQueryResult objectFromData(String str) {
        
        return new Gson().fromJson(str, CartQueryResult.class);
    }
    
    public static CartQueryResult objectFromData(String str, String key) {
        
        try {
            JSONObject jsonObject = new JSONObject(str);
            
            return new Gson().fromJson(jsonObject.getString(str), CartQueryResult.class);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        
        return null;
    }
    
    public static List<CartQueryResult> arrayResultFromData(String str) {
        
        Type listType = new TypeToken<ArrayList<CartQueryResult>>() {
        }.getType();
        
        return new Gson().fromJson(str, listType);
    }
    
    public String getStoreId() {
        return storeId;
    }
    
    public void setStoreId(String storeId) {
        this.storeId = storeId;
    }
    
    public String getOrgCode() {
        return orgCode;
    }
    
    public void setOrgCode(String orgCode) {
        this.orgCode = orgCode;
    }
    
    public String getStoreName() {
        return storeName;
    }
    
    public void setStoreName(String storeName) {
        this.storeName = storeName;
    }
    
    public String getOpenJPIndustry() {
        return openJPIndustry;
    }
    
    public void setOpenJPIndustry(String openJPIndustry) {
        this.openJPIndustry = openJPIndustry;
    }
    
    public String getStoreImgUrl() {
        return storeImgUrl;
    }
    
    public void setStoreImgUrl(String storeImgUrl) {
        this.storeImgUrl = storeImgUrl;
    }
    
    public String getPayMoneyPriceValue() {
        return payMoneyPriceValue;
    }
    
    public void setPayMoneyPriceValue(String payMoneyPriceValue) {
        this.payMoneyPriceValue = payMoneyPriceValue;
    }
    
    public String getPayMoneyName() {
        return payMoneyName;
    }
    
    public void setPayMoneyName(String payMoneyName) {
        this.payMoneyName = payMoneyName;
    }
    
    public int getTotalNum() {
        return totalNum;
    }
    
    public void setTotalNum(int totalNum) {
        this.totalNum = totalNum;
    }
    
    public List<ItemList> getItemList() {
        return itemList;
    }
    
    public void setItemList(List<ItemList> itemList) {
        this.itemList = itemList;
    }
}