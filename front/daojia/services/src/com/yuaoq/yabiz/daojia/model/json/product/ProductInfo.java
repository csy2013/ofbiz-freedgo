package com.yuaoq.yabiz.daojia.model.json.product;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.yuaoq.yabiz.daojia.model.json.product.productSearch.MiaoshaInfo;
import org.json.JSONException;
import org.json.JSONObject;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by changsy on 16/9/5.
 */
public class ProductInfo {
    public String skuId;
    public String skuName;
    public String imgUrl;
    public String storeId;
    public String orgCode;
    public boolean fixedStatus;
    public Object preName;
    public boolean incart;
    public int incartCount;
    public int stockCount;
    
    public MiaoshaInfo miaoshaInfo;
    public Object buriedPoint;
    public boolean saleStatus;
    public boolean showCartButton;
    public Object userActionSku;
    public String catId;
    public String standard;
    public String funcIndicatins;
    public String aging;
    public String venderId;
    public String basicPrice;
    public String mkPrice;
    public String realTimePrice;
    public int promotion;
    public List<?> tags;
    
    public static ProductInfo objectFromData(String str) {
        
        return new Gson().fromJson(str, ProductInfo.class);
    }
    
    public static ProductInfo objectFromData(String str, String key) {
        
        try {
            JSONObject jsonObject = new JSONObject(str);
            
            return new Gson().fromJson(jsonObject.getString(str), ProductInfo.class);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        
        return null;
    }
    
    public static List<ProductInfo> arrayAnchoredProductFromData(String str) {
        
        Type listType = new TypeToken<ArrayList<ProductInfo>>() {
        }.getType();
        
        return new Gson().fromJson(str, listType);
    }
    
    public String getSkuId() {
        return skuId;
    }
    
    public void setSkuId(String skuId) {
        this.skuId = skuId;
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
    
    public String getOrgCode() {
        return orgCode;
    }
    
    public void setOrgCode(String orgCode) {
        this.orgCode = orgCode;
    }
    
    public boolean isFixedStatus() {
        return fixedStatus;
    }
    
    public void setFixedStatus(boolean fixedStatus) {
        this.fixedStatus = fixedStatus;
    }
    
    public Object getPreName() {
        return preName;
    }
    
    public void setPreName(Object preName) {
        this.preName = preName;
    }
    
    public boolean isIncart() {
        return incart;
    }
    
    public void setIncart(boolean incart) {
        this.incart = incart;
    }
    
    public int getIncartCount() {
        return incartCount;
    }
    
    public void setIncartCount(int incartCount) {
        this.incartCount = incartCount;
    }
    
    public int getStockCount() {
        return stockCount;
    }
    
    public void setStockCount(int stockCount) {
        this.stockCount = stockCount;
    }
    
    public MiaoshaInfo getMiaoshaInfo() {
        return miaoshaInfo;
    }
    
    public void setMiaoshaInfo(MiaoshaInfo miaoshaInfo) {
        this.miaoshaInfo = miaoshaInfo;
    }
    
    public Object getBuriedPoint() {
        return buriedPoint;
    }
    
    public void setBuriedPoint(Object buriedPoint) {
        this.buriedPoint = buriedPoint;
    }
    
    public boolean isSaleStatus() {
        return saleStatus;
    }
    
    public void setSaleStatus(boolean saleStatus) {
        this.saleStatus = saleStatus;
    }
    
    public boolean isShowCartButton() {
        return showCartButton;
    }
    
    public void setShowCartButton(boolean showCartButton) {
        this.showCartButton = showCartButton;
    }
    
    public Object getUserActionSku() {
        return userActionSku;
    }
    
    public void setUserActionSku(Object userActionSku) {
        this.userActionSku = userActionSku;
    }
    
    public String getCatId() {
        return catId;
    }
    
    public void setCatId(String catId) {
        this.catId = catId;
    }
    
    public String getStandard() {
        return standard;
    }
    
    public void setStandard(String standard) {
        this.standard = standard;
    }
    
    public String getFuncIndicatins() {
        return funcIndicatins;
    }
    
    public void setFuncIndicatins(String funcIndicatins) {
        this.funcIndicatins = funcIndicatins;
    }
    
    public String getAging() {
        return aging;
    }
    
    public void setAging(String aging) {
        this.aging = aging;
    }
    
    public String getVenderId() {
        return venderId;
    }
    
    public void setVenderId(String venderId) {
        this.venderId = venderId;
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
    
    public String getRealTimePrice() {
        return realTimePrice;
    }
    
    public void setRealTimePrice(String realTimePrice) {
        this.realTimePrice = realTimePrice;
    }
    
    public int getPromotion() {
        return promotion;
    }
    
    public void setPromotion(int promotion) {
        this.promotion = promotion;
    }
    
    public List<?> getTags() {
        return tags;
    }
    
    public void setTags(List<?> tags) {
        this.tags = tags;
    }
}
