package com.yuaoq.yabiz.daojia.model.json.request;

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
public class ProductSearchBody {
    
    
    /**
     * key :
     * catId : 10022
     * storeId : 10000
     * sortType : 1
     * page : 1
     * pageSize : 10
     * cartUuid :
     * promotLable :
     * timeTag : 1472829854480
     */
    
    public String key;
    public String catId;
    public String storeId;
    public int sortType;
    public int page;
    public int pageSize;
    public String cartUuid;
    public String promotLable;
    public long timeTag;
    public String skuId;
    
    public static ProductSearchBody objectFromData(String str) {
        
        return new Gson().fromJson(str, ProductSearchBody.class);
    }
    
    public static ProductSearchBody objectFromData(String str, String key) {
        
        try {
            JSONObject jsonObject = new JSONObject(str);
            
            return new Gson().fromJson(jsonObject.getString(str), ProductSearchBody.class);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        
        return null;
    }
    
    public static List<ProductSearchBody> arrayProductSearchBodyFromData(String str) {
        
        Type listType = new TypeToken<ArrayList<ProductSearchBody>>() {
        }.getType();
        
        return new Gson().fromJson(str, listType);
    }
}
