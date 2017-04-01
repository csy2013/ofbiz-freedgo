package com.yuaoq.yabiz.daojia.model.json.product.category;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import org.json.JSONException;
import org.json.JSONObject;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by changsy on 16/9/6.
 */
public class GroupList {
    public int catId;
    public String name;
    public String user_action;
    /**
     * catId : 140
     * name : 苹果
     * imgUrl : https://img30.360buyimg.com/mobilecms/jfs/t2560/274/768172012/6706/f9b6c3a7/5667d32fN5f8f1011.jpg
     * productConut : 30
     * catIds : [{"categoryId":"20315","categoryName":null,"level":3},{"categoryId":"20340","categoryName":null,"level":3}]
     * user_action : {"catename1":"水果蔬菜","catename2":"水果","catename3":"苹果"}
     */
    
    public List<TabCateList> tabCateList;
    
    public static GroupList objectFromData(String str) {
        
        return new Gson().fromJson(str, GroupList.class);
    }
    
    public static GroupList objectFromData(String str, String key) {
        
        try {
            JSONObject jsonObject = new JSONObject(str);
            
            return new Gson().fromJson(jsonObject.getString(str), GroupList.class);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        
        return null;
    }
    
    public static List<GroupList> arrayGroupListFromData(String str) {
        
        Type listType = new TypeToken<ArrayList<GroupList>>() {
        }.getType();
        
        return new Gson().fromJson(str, listType);
    }
    
    public int getCatId() {
        return catId;
    }
    
    public void setCatId(int catId) {
        this.catId = catId;
    }
    
    public String getName() {
        return name;
    }
    
    public void setName(String name) {
        this.name = name;
    }
    
    public String getUser_action() {
        return user_action;
    }
    
    public void setUser_action(String user_action) {
        this.user_action = user_action;
    }
    
    public List<TabCateList> getTabCateList() {
        return tabCateList;
    }
    
    public void setTabCateList(List<TabCateList> tabCateList) {
        this.tabCateList = tabCateList;
    }
}