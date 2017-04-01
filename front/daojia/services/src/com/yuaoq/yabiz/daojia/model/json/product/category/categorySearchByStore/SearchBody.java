package com.yuaoq.yabiz.daojia.model.json.product.category.categorySearchByStore;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.yuaoq.yabiz.daojia.model.json.product.category.CatIds;
import org.json.JSONException;
import org.json.JSONObject;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by changsy on 16/9/7.
 */
public class SearchBody {
    public double longitude;
    public double latitude;
    public int type;
    public String key;
    public int sortType;
    public int page;
    public int pageSize;
    public String promotLabels;
    public String discountRange;
    public long serviceNo;
    public List<String> industryTags;
    public List<CatIds> catIds;
    public List<String> storeIds;
    
    public static SearchBody objectFromData(String str) {
        
        return new Gson().fromJson(str, SearchBody.class);
    }
    
    public static SearchBody objectFromData(String str, String key) {
        
        try {
            JSONObject jsonObject = new JSONObject(str);
            
            return new Gson().fromJson(jsonObject.getString(str), SearchBody.class);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        
        return null;
    }
    
    public static List<SearchBody> arraySearchBodyFromData(String str) {
        
        Type listType = new TypeToken<ArrayList<SearchBody>>() {
        }.getType();
        
        return new Gson().fromJson(str, listType);
    }
    
    public double getLongitude() {
        return longitude;
    }
    
    public void setLongitude(double longitude) {
        this.longitude = longitude;
    }
    
    public double getLatitude() {
        return latitude;
    }
    
    public void setLatitude(double latitude) {
        this.latitude = latitude;
    }
    
    public int getType() {
        return type;
    }
    
    public void setType(int type) {
        this.type = type;
    }
    
    public String getKey() {
        return key;
    }
    
    public void setKey(String key) {
        this.key = key;
    }
    
    public int getSortType() {
        return sortType;
    }
    
    public void setSortType(int sortType) {
        this.sortType = sortType;
    }
    
    public int getPage() {
        return page;
    }
    
    public void setPage(int page) {
        this.page = page;
    }
    
    public int getPageSize() {
        return pageSize;
    }
    
    public void setPageSize(int pageSize) {
        this.pageSize = pageSize;
    }
    
    public String getPromotLabels() {
        return promotLabels;
    }
    
    public void setPromotLabels(String promotLabels) {
        this.promotLabels = promotLabels;
    }
    
    public String getDiscountRange() {
        return discountRange;
    }
    
    public void setDiscountRange(String discountRange) {
        this.discountRange = discountRange;
    }
    
    public long getServiceNo() {
        return serviceNo;
    }
    
    public void setServiceNo(long serviceNo) {
        this.serviceNo = serviceNo;
    }
    
    public List<String> getIndustryTags() {
        return industryTags;
    }
    
    public void setIndustryTags(List<String> industryTags) {
        this.industryTags = industryTags;
    }
    
    public List<CatIds> getCatIds() {
        return catIds;
    }
    
    public void setCatIds(List<CatIds> catIds) {
        this.catIds = catIds;
    }
    
    public List<String> getStoreIds() {
        return storeIds;
    }
    
    public void setStoreIds(List<String> storeIds) {
        this.storeIds = storeIds;
    }
}
