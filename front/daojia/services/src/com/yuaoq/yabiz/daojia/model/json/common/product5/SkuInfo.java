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
public   class SkuInfo {
    public String skuId;
    public String realTimePrice;
    public String basicPrice;
    public String mkPrice;
    public int promotion;
    public String skuName;
    public String imgUrl;
    public String storeId;
    public MiaoshaInfo miaoshaInfo;
    public String venderId;
    public String userAction;
    public String aging;
    public boolean showCart;
    public boolean incart;
    public int numInCart;
    public int stockCount;
    public boolean showCartButton;
    public int incartCount;
    public String orgCode;
    public String storeName;
    public String noPrefixImgUrl;
    public List<Tag> tags;
    
    public SkuInfo() {
    }
    
    public SkuInfo(String skuId, String realTimePrice, String basicPrice, String mkPrice, int promotion, String skuName, String imgUrl, String storeId, MiaoshaInfo miaoshaInfo, String venderId, String userAction, String aging, boolean showCart, boolean incart, int numInCart, int stockCount, boolean showCartButton, int incartCount, String orgCode, String storeName, String noPrefixImgUrl, List<Tag> tags) {
        this.skuId = skuId;
        this.realTimePrice = realTimePrice;
        this.basicPrice = basicPrice;
        this.mkPrice = mkPrice;
        this.promotion = promotion;
        this.skuName = skuName;
        this.imgUrl = imgUrl;
        this.storeId = storeId;
        this.miaoshaInfo = miaoshaInfo;
        this.venderId = venderId;
        this.userAction = userAction;
        this.aging = aging;
        this.showCart = showCart;
        this.incart = incart;
        this.numInCart = numInCart;
        this.stockCount = stockCount;
        this.showCartButton = showCartButton;
        this.incartCount = incartCount;
        this.orgCode = orgCode;
        this.storeName = storeName;
        this.noPrefixImgUrl = noPrefixImgUrl;
        this.tags = tags;
    }
    
    public String getSkuId() {
        return skuId;
    }
    
    public void setSkuId(String skuId) {
        this.skuId = skuId;
    }
    
    public String getRealTimePrice() {
        return realTimePrice;
    }
    
    public void setRealTimePrice(String realTimePrice) {
        this.realTimePrice = realTimePrice;
    }
    
    public String getBasicPrice() {
        return basicPrice;
    }
    
    public void setBasicPrice(String basicPrice) {
        this.basicPrice = basicPrice;
    }
    
    public String getMkPrice() {
        return mkPrice;
    }
    
    public void setMkPrice(String mkPrice) {
        this.mkPrice = mkPrice;
    }
    
    public int getPromotion() {
        return promotion;
    }
    
    public void setPromotion(int promotion) {
        this.promotion = promotion;
    }
    
    public String getSkuName() {
        return skuName;
    }
    
    public void setSkuName(String skuName) {
        this.skuName = skuName;
    }
    
    public String getImgUrl() {
        return imgUrl;
    }
    
    public void setImgUrl(String imgUrl) {
        this.imgUrl = imgUrl;
    }
    
    public String getStoreId() {
        return storeId;
    }
    
    public void setStoreId(String storeId) {
        this.storeId = storeId;
    }
    
    public MiaoshaInfo getMiaoshaInfo() {
        return miaoshaInfo;
    }
    
    public void setMiaoshaInfo(MiaoshaInfo miaoshaInfo) {
        this.miaoshaInfo = miaoshaInfo;
    }
    
    public String getVenderId() {
        return venderId;
    }
    
    public void setVenderId(String venderId) {
        this.venderId = venderId;
    }
    
    public String getUserAction() {
        return userAction;
    }
    
    public void setUserAction(String userAction) {
        this.userAction = userAction;
    }
    
    public String getAging() {
        return aging;
    }
    
    public void setAging(String aging) {
        this.aging = aging;
    }
    
    public boolean isShowCart() {
        return showCart;
    }
    
    public void setShowCart(boolean showCart) {
        this.showCart = showCart;
    }
    
    public boolean isIncart() {
        return incart;
    }
    
    public void setIncart(boolean incart) {
        this.incart = incart;
    }
    
    public int getNumInCart() {
        return numInCart;
    }
    
    public void setNumInCart(int numInCart) {
        this.numInCart = numInCart;
    }
    
    public int getStockCount() {
        return stockCount;
    }
    
    public void setStockCount(int stockCount) {
        this.stockCount = stockCount;
    }
    
    public boolean isShowCartButton() {
        return showCartButton;
    }
    
    public void setShowCartButton(boolean showCartButton) {
        this.showCartButton = showCartButton;
    }
    
    public int getIncartCount() {
        return incartCount;
    }
    
    public void setIncartCount(int incartCount) {
        this.incartCount = incartCount;
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
    
    public String getNoPrefixImgUrl() {
        return noPrefixImgUrl;
    }
    
    public void setNoPrefixImgUrl(String noPrefixImgUrl) {
        this.noPrefixImgUrl = noPrefixImgUrl;
    }
    
    public List<?> getTags() {
        return tags;
    }
    
    public void setTags(List<Tag> tags) {
        this.tags = tags;
    }
    
    public static SkuInfo objectFromData(String str) {
        
        return new Gson().fromJson(str, SkuInfo.class);
    }
    
    public static SkuInfo objectFromData(String str, String key) {
        
        try {
            JSONObject jsonObject = new JSONObject(str);
            
            return new Gson().fromJson(jsonObject.getString(str), SkuInfo.class);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        
        return null;
    }
    
    public static List<SkuInfo> arraySkuInfoFromData(String str) {
        
        Type listType = new TypeToken<ArrayList<SkuInfo>>() {
        }.getType();
        
        return new Gson().fromJson(str, listType);
    }
    
    
}
