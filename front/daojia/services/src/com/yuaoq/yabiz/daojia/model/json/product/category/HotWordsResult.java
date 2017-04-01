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
public class HotWordsResult {
    
    
    public String solution;
    public int count;
    public int cacheTime;
    public Object onlinedebugId;
    /**
     * hotWords : 扁豆
     * storeIds : [10060549]
     * typeIds : [0]
     */
    
    public List<HotWordVOList> hotWordVOList;
    
    public static Result objectFromData(String str) {
        
        return new Gson().fromJson(str, Result.class);
    }
    
    public static Result objectFromData(String str, String key) {
        
        try {
            JSONObject jsonObject = new JSONObject(str);
            
            return new Gson().fromJson(jsonObject.getString(str), Result.class);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        
        return null;
    }
    
    public static List<Result> arrayResultFromData(String str) {
        
        Type listType = new TypeToken<ArrayList<Result>>() {
        }.getType();
        
        return new Gson().fromJson(str, listType);
    }
    
    public String getSolution() {
        return solution;
    }
    
    public void setSolution(String solution) {
        this.solution = solution;
    }
    
    public int getCount() {
        return count;
    }
    
    public void setCount(int count) {
        this.count = count;
    }
    
    public int getCacheTime() {
        return cacheTime;
    }
    
    public void setCacheTime(int cacheTime) {
        this.cacheTime = cacheTime;
    }
    
    public Object getOnlinedebugId() {
        return onlinedebugId;
    }
    
    public void setOnlinedebugId(Object onlinedebugId) {
        this.onlinedebugId = onlinedebugId;
    }
    
    public List<HotWordVOList> getHotWordVOList() {
        return hotWordVOList;
    }
    
    public void setHotWordVOList(List<HotWordVOList> hotWordVOList) {
        this.hotWordVOList = hotWordVOList;
    }
}
