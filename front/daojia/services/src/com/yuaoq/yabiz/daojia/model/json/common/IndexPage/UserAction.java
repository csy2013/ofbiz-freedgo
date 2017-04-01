package com.yuaoq.yabiz.daojia.model.json.common.IndexPage;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import org.json.JSONException;
import org.json.JSONObject;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by changsy on 16/8/29.
 */
public class UserAction {
    
    
    /**
     * imgUrl : https://img30.360buyimg.com/mobilecms/jfs/t3142/282/983539982/95103/ba5d1434/57c3a01cN494a99be.jpg
     * activityId : 12901
     * floorStyle  : banner
     * index : 1
     * imgOrder : 1
     */
    
    public String imgUrl;
    public String activityId;
    public String floorStyle;
    public int index;
    public String imgOrder;
    
    public UserAction(String imgUrl, String activityId, String floorStyle, int index, String imgOrder) {
        this.imgUrl = imgUrl;
        this.activityId = activityId;
        this.floorStyle = floorStyle;
        this.index = index;
        this.imgOrder = imgOrder;
    }
    
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
    
    public String objectToString() {
        return new Gson().toJson(this);
    }
    
    
}
