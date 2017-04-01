package com.yuaoq.yabiz.daojia.model.json.common.store;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.yuaoq.yabiz.daojia.model.json.BaseResult;
import org.json.JSONException;
import org.json.JSONObject;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by changsy on 16/8/31.
 */
public class RecommendStoreList extends BaseResult {
    
    
    public Object result;
    /**
     * duration : 10800000
     * experimentName : recommend_homepage
     * testTag : recommend_homepage:commonRank:2016-08-31 08:03:52
     */
    
    public AbTest abTest;
    
    
    public RecommendStoreList(String id, String code, String msg, boolean success) {
        super(id, code, msg, success);
    }
    
    public RecommendStoreList(String id, String code, String msg, boolean success, String result) {
        super(id, code, msg, success);
        this.result = result;
    }
    
    public RecommendStoreList(String id, String code, String msg, boolean success, StoreResult result, AbTest abTest) {
        super(id, code, msg, success);
        this.result = result;
        this.abTest = abTest;
    }
    
    
    public static RecommendStoreList objectFromData(String str) {
        
        return new Gson().fromJson(str, RecommendStoreList.class);
    }
    
    public static RecommendStoreList objectFromData(String str, String key) {
        
        try {
            JSONObject jsonObject = new JSONObject(str);
            
            return new Gson().fromJson(jsonObject.getString(str), RecommendStoreList.class);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        
        return null;
    }
    
    public static List<RecommendStoreList> arrayRecommendStoreListFromData(String str) {
        
        Type listType = new TypeToken<ArrayList<RecommendStoreList>>() {
        }.getType();
        
        return new Gson().fromJson(str, listType);
    }
}
    
     
