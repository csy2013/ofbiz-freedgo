package com.yuaoq.yabiz.daojia.model.json.common.store.storeDetail;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import org.json.JSONException;
import org.json.JSONObject;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by changsy on 16/9/2.
 */
public class CateList {
    public String catId;
    public String parentId;
    public String title;
    public int productCount;
    public int sort;
    public int level;
    public String promotLabel;
    public boolean ispromotcat;
    public String user_action;
    public boolean openCatetory;
    public List<CateList> childCategoryList;
    
    public CateList(String catId, String parentId, String title, int productCount, int sort, int level, String promotLabel, boolean ispromotcat, String user_action, boolean openCatetory, List<CateList> childCategoryList) {
        this.catId = catId;
        this.parentId = parentId;
        this.title = title;
        this.productCount = productCount;
        this.sort = sort;
        this.level = level;
        this.promotLabel = promotLabel;
        this.ispromotcat = ispromotcat;
        this.user_action = user_action;
        this.openCatetory = openCatetory;
        this.childCategoryList = childCategoryList;
    }
    
    public static CateList objectFromData(String str) {
        
        return new Gson().fromJson(str, CateList.class);
    }
    
    public static CateList objectFromData(String str, String key) {
        
        try {
            JSONObject jsonObject = new JSONObject(str);
            
            return new Gson().fromJson(jsonObject.getString(str), CateList.class);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        
        return null;
    }
    
    public static List<CateList> arrayCateListFromData(String str) {
        
        Type listType = new TypeToken<ArrayList<CateList>>() {
        }.getType();
        
        return new Gson().fromJson(str, listType);
    }
}