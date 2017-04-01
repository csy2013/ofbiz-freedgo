package com.yuaoq.yabiz.daojia.model.json.product.category;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.yuaoq.yabiz.daojia.model.json.BaseResult;
import org.json.JSONException;
import org.json.JSONObject;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by changsy on 16/9/5.
 */
public class CategoryList extends BaseResult {
    
    /**
     * id : homeSearch.tabCateList
     * code : 0
     * msg : 成功
     * detail : null
     * result :
     * cacheTime : 30
     * productCountLimit : 1
     * onlinedebugId : null
     */
    
    public Result result;
    
    public CategoryList(String id, String code, String msg, boolean success) {
        super(id, code, msg, success);
    }
    
    public static CategoryList objectFromData(String str) {
        
        return new Gson().fromJson(str, CategoryList.class);
    }
    
    public static CategoryList objectFromData(String str, String key) {
        
        try {
            JSONObject jsonObject = new JSONObject(str);
            
            return new Gson().fromJson(jsonObject.getString(str), CategoryList.class);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        
        return null;
    }
    
    public static List<CategoryList> arrayCategoryListFromData(String str) {
        
        Type listType = new TypeToken<ArrayList<CategoryList>>() {
        }.getType();
        
        return new Gson().fromJson(str, listType);
    }
    
    
}
