package com.yuaoq.yabiz.daojia.model.json.order;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import org.json.JSONException;
import org.json.JSONObject;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by changsy on 16/9/22.
 */
public class OrderProductInventory {
    
    
    /**
     * sku : 2001857878
     * price : 150
     * discountPrice : 0
     * imgPath : https://img10.360buyimg.com/n7/jfs/t2827/130/195812239/223883/294fbb28/5706b8a9N151cb9ff.jpg
     * shopId : 10055645
     * num : 1
     * name : 黄瓜约250±30g/份
     * orderId : 608130900053842
     * skuCommentStatus : 0
     * productCategory : 0
     * priceStr : ¥1.50
     * totalPriceStr : ¥1.50
     * promotionType : 1
     * promotionTypeForAfs : 1
     * canApplyAfs : false
     * showAfsDetail : false
     */
    
    public int sku;
    public int price;
    public int discountPrice;
    public String imgPath;
    public String shopId;
    public int num;
    public String name;
    public String orderId;
    public int skuCommentStatus;
    public int productCategory;
    public String priceStr;
    public String totalPriceStr;
    public int promotionType;
    public int promotionTypeForAfs;
    public boolean canApplyAfs;
    public boolean showAfsDetail;
    
    public OrderProductInventory() {
    }
    
    public OrderProductInventory(int sku, int price, int discountPrice, String imgPath, String shopId, int num, String name, String orderId, int skuCommentStatus, int productCategory, String priceStr, String totalPriceStr, int promotionType, int promotionTypeForAfs, boolean canApplyAfs, boolean showAfsDetail) {
        this.sku = sku;
        this.price = price;
        this.discountPrice = discountPrice;
        this.imgPath = imgPath;
        this.shopId = shopId;
        this.num = num;
        this.name = name;
        this.orderId = orderId;
        this.skuCommentStatus = skuCommentStatus;
        this.productCategory = productCategory;
        this.priceStr = priceStr;
        this.totalPriceStr = totalPriceStr;
        this.promotionType = promotionType;
        this.promotionTypeForAfs = promotionTypeForAfs;
        this.canApplyAfs = canApplyAfs;
        this.showAfsDetail = showAfsDetail;
    }
    
    public static OrderProductInventory objectFromData(String str) {
        
        return new Gson().fromJson(str, OrderProductInventory.class);
    }
    
    public static OrderProductInventory objectFromData(String str, String key) {
        
        try {
            JSONObject jsonObject = new JSONObject(str);
            
            return new Gson().fromJson(jsonObject.getString(str), OrderProductInventory.class);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        
        return null;
    }
    
    public static List<OrderProductInventory> arrayOrderProductInventoryFromData(String str) {
        
        Type listType = new TypeToken<ArrayList<OrderProductInventory>>() {
        }.getType();
        
        return new Gson().fromJson(str, listType);
    }
    
    public int getSku() {
        return sku;
    }
    
    public void setSku(int sku) {
        this.sku = sku;
    }
    
    public int getPrice() {
        return price;
    }
    
    public void setPrice(int price) {
        this.price = price;
    }
    
    public int getDiscountPrice() {
        return discountPrice;
    }
    
    public void setDiscountPrice(int discountPrice) {
        this.discountPrice = discountPrice;
    }
    
    public String getImgPath() {
        return imgPath;
    }
    
    public void setImgPath(String imgPath) {
        this.imgPath = imgPath;
    }
    
    public String getShopId() {
        return shopId;
    }
    
    public void setShopId(String shopId) {
        this.shopId = shopId;
    }
    
    public int getNum() {
        return num;
    }
    
    public void setNum(int num) {
        this.num = num;
    }
    
    public String getName() {
        return name;
    }
    
    public void setName(String name) {
        this.name = name;
    }
    
    public String getOrderId() {
        return orderId;
    }
    
    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }
    
    public int getSkuCommentStatus() {
        return skuCommentStatus;
    }
    
    public void setSkuCommentStatus(int skuCommentStatus) {
        this.skuCommentStatus = skuCommentStatus;
    }
    
    public int getProductCategory() {
        return productCategory;
    }
    
    public void setProductCategory(int productCategory) {
        this.productCategory = productCategory;
    }
    
    public String getPriceStr() {
        return priceStr;
    }
    
    public void setPriceStr(String priceStr) {
        this.priceStr = priceStr;
    }
    
    public String getTotalPriceStr() {
        return totalPriceStr;
    }
    
    public void setTotalPriceStr(String totalPriceStr) {
        this.totalPriceStr = totalPriceStr;
    }
    
    public int getPromotionType() {
        return promotionType;
    }
    
    public void setPromotionType(int promotionType) {
        this.promotionType = promotionType;
    }
    
    public int getPromotionTypeForAfs() {
        return promotionTypeForAfs;
    }
    
    public void setPromotionTypeForAfs(int promotionTypeForAfs) {
        this.promotionTypeForAfs = promotionTypeForAfs;
    }
    
    public boolean isCanApplyAfs() {
        return canApplyAfs;
    }
    
    public void setCanApplyAfs(boolean canApplyAfs) {
        this.canApplyAfs = canApplyAfs;
    }
    
    public boolean isShowAfsDetail() {
        return showAfsDetail;
    }
    
    public void setShowAfsDetail(boolean showAfsDetail) {
        this.showAfsDetail = showAfsDetail;
    }
}
