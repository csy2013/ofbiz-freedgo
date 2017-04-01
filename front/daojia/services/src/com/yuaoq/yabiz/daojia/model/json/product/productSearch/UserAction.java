package com.yuaoq.yabiz.daojia.model.json.product.productSearch;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import org.json.JSONException;
import org.json.JSONObject;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by changsy on 16/9/3.
 */
public class UserAction {
    
    /**
     * key :
     * query : {"catId":"","key":"","page":1,"pageSize":10,"promotLable":"3","sortType":"sort_default","storeId":"10055645"}
     * skunum : 12
     * storeid : 10055645
     */
    
    public String key;
    /**
     * catId :
     * key :
     * page : 1
     * pageSize : 10
     * promotLable : 3
     * sortType : sort_default
     * storeId : 10055645
     */
    
    public Query query;
    public String skunum;
    public String storeid;
    
    public static UserAction objectFromData(String str) {
        
        return new Gson().fromJson(str, UserAction.class);
    }
    
    public static UserAction objectFromData(String str, String key) {
        
        try {
            JSONObject jsonObject = new JSONObject(str);
            
            return new Gson().fromJson(jsonObject.getString(str), UserAction.class);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        
        return null;
    }
    
    public static List<UserAction> arrayUserActionFromData(String str) {
        
        Type listType = new TypeToken<ArrayList<UserAction>>() {
        }.getType();
        
        return new Gson().fromJson(str, listType);
    }
    
    public static String objectToStr(UserAction userAction) {
        return new Gson().toJson(userAction);
    }
    
    public static class Query {
        public String catId;
        public String key;
        public int page;
        public int pageSize;
        public String promotLable;
        public String sortType;
        public String storeId;
        
        public static Query objectFromData(String str) {
            
            return new Gson().fromJson(str, Query.class);
        }
        
        public static Query objectFromData(String str, String key) {
            
            try {
                JSONObject jsonObject = new JSONObject(str);
                
                return new Gson().fromJson(jsonObject.getString(str), Query.class);
            } catch (JSONException e) {
                e.printStackTrace();
            }
            
            return null;
        }
        
        public static List<Query> arrayQueryFromData(String str) {
            
            Type listType = new TypeToken<ArrayList<Query>>() {
            }.getType();
            
            return new Gson().fromJson(str, listType);
        }
    }
}
