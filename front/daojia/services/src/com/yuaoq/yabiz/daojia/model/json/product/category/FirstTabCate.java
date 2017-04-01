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
public class FirstTabCate {
    public int catId;
    public String name;
    public Object cateActivityList;
    public String user_action;
    /**
     * catId : 60
     * name : 水果
     * tabCateList :
     * user_action : {"catename1":"水果蔬菜","catename2":"水果"}
     */
    
    public List<GroupList> groupList;
    
    public static FirstTabCate objectFromData(String str) {
        
        return new Gson().fromJson(str, FirstTabCate.class);
    }
    
    public static FirstTabCate objectFromData(String str, String key) {
        
        try {
            JSONObject jsonObject = new JSONObject(str);
            
            return new Gson().fromJson(jsonObject.getString(str), FirstTabCate.class);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        
        return null;
    }
    
    public static List<FirstTabCate> arrayFirstTabCateFromData(String str) {
        
        Type listType = new TypeToken<ArrayList<FirstTabCate>>() {
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
    
    public Object getCateActivityList() {
        return cateActivityList;
    }
    
    public void setCateActivityList(Object cateActivityList) {
        this.cateActivityList = cateActivityList;
    }
    
    public String getUser_action() {
        return user_action;
    }
    
    public void setUser_action(String user_action) {
        this.user_action = user_action;
    }
    
    public List<GroupList> getGroupList() {
        return groupList;
    }
    
    public void setGroupList(List<GroupList> groupList) {
        this.groupList = groupList;
    }
}
