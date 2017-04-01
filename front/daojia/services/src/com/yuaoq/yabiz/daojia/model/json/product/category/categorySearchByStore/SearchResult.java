package com.yuaoq.yabiz.daojia.model.json.product.category.categorySearchByStore;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import org.json.JSONException;
import org.json.JSONObject;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by changsy on 16/9/7.
 */
public class SearchResult {
    public String solution;
    public int page;
    public int count;
    public int storeCount;
    public String user_action;
    public Object onlinedebugId;
    public Object promptWord;
    public String promptTips;
    public List<?> storeList;
    public List<StoreSkuList> storeSkuList;
    
    public static SearchResult objectFromData(String str) {
        
        return new Gson().fromJson(str, SearchResult.class);
    }
    
    public static SearchResult objectFromData(String str, String key) {
        
        try {
            JSONObject jsonObject = new JSONObject(str);
            
            return new Gson().fromJson(jsonObject.getString(str), SearchResult.class);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        
        return null;
    }
    
    public static List<SearchResult> arrayResultFromData(String str) {
        
        Type listType = new TypeToken<ArrayList<SearchResult>>() {
        }.getType();
        
        return new Gson().fromJson(str, listType);
    }
    
    
    public String getSolution() {
        return solution;
    }
    
    public void setSolution(String solution) {
        this.solution = solution;
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
    
    public int getStoreCount() {
        return storeCount;
    }
    
    public void setStoreCount(int storeCount) {
        this.storeCount = storeCount;
    }
    
    public String getUser_action() {
        return user_action;
    }
    
    public void setUser_action(String user_action) {
        this.user_action = user_action;
    }
    
    public Object getOnlinedebugId() {
        return onlinedebugId;
    }
    
    public void setOnlinedebugId(Object onlinedebugId) {
        this.onlinedebugId = onlinedebugId;
    }
    
    public Object getPromptWord() {
        return promptWord;
    }
    
    public void setPromptWord(Object promptWord) {
        this.promptWord = promptWord;
    }
    
    public String getPromptTips() {
        return promptTips;
    }
    
    public void setPromptTips(String promptTips) {
        this.promptTips = promptTips;
    }
    
    public List<?> getStoreList() {
        return storeList;
    }
    
    public void setStoreList(List<?> storeList) {
        this.storeList = storeList;
    }
    
    public List<StoreSkuList> getStoreSkuList() {
        return storeSkuList;
    }
    
    public void setStoreSkuList(List<StoreSkuList> storeSkuList) {
        this.storeSkuList = storeSkuList;
    }
}
