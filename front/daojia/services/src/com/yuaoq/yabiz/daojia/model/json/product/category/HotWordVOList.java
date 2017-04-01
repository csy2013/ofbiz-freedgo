package com.yuaoq.yabiz.daojia.model.json.product.category;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import org.json.JSONException;
import org.json.JSONObject;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by changsy on 16/9/9.
 */
public class HotWordVOList {
    public String hotWords;
    public List<Integer> storeIds;
    public List<Integer> typeIds;
    
    public static HotWordVOList objectFromData(String str) {
        
        return new Gson().fromJson(str, HotWordVOList.class);
    }
    
    public static HotWordVOList objectFromData(String str, String key) {
        
        try {
            JSONObject jsonObject = new JSONObject(str);
            
            return new Gson().fromJson(jsonObject.getString(str), HotWordVOList.class);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        
        return null;
    }
    
    public static List<HotWordVOList> arrayHotWordVOListFromData(String str) {
        
        Type listType = new TypeToken<ArrayList<HotWordVOList>>() {
        }.getType();
        
        return new Gson().fromJson(str, listType);
    }
    
    public String getHotWords() {
        return hotWords;
    }
    
    public void setHotWords(String hotWords) {
        this.hotWords = hotWords;
    }
    
    public List<Integer> getStoreIds() {
        return storeIds;
    }
    
    public void setStoreIds(List<Integer> storeIds) {
        this.storeIds = storeIds;
    }
    
    public List<Integer> getTypeIds() {
        return typeIds;
    }
    
    public void setTypeIds(List<Integer> typeIds) {
        this.typeIds = typeIds;
    }
}
