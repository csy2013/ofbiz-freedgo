package com.yuaoq.yabiz.daojia.model.json.product.category.categorySearchByStore;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.yuaoq.yabiz.daojia.model.json.BaseResult;
import org.json.JSONException;
import org.json.JSONObject;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by changsy on 16/9/7.
 */
public class SearchByStorePost extends BaseResult {
    
    
    public Object detail;
    
    
    public SearchResult result;
    
    public SearchByStorePost(String id, String code, String msg, boolean success) {
        super(id, code, msg, success);
    }
    
    public static SearchByStorePost objectFromData(String str) {
        
        return new Gson().fromJson(str, SearchByStorePost.class);
    }
    
    public static SearchByStorePost objectFromData(String str, String key) {
        
        try {
            JSONObject jsonObject = new JSONObject(str);
            
            return new Gson().fromJson(jsonObject.getString(str), SearchByStorePost.class);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        
        return null;
    }
    
    public static List<SearchByStorePost> arraySearchByStorePostFromData(String str) {
        
        Type listType = new TypeToken<ArrayList<SearchByStorePost>>() {
        }.getType();
        
        return new Gson().fromJson(str, listType);
    }
    
    
}
