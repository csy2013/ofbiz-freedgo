package com.yuaoq.yabiz.daojia.model.json.common.store;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import org.json.JSONException;
import org.json.JSONObject;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by changsy on 16/8/31.
 */
public class UserAction {
    
    
    /**
     * rankNo : 1
     * currentPage : 1
     * storeId : 10055645
     */
    
    
    public int rankNo;
    public int currentPage;
    public String storeId;
    public UserAction(int rankNo, int currentPage, String storeId) {
        this.rankNo = rankNo;
        this.currentPage = currentPage;
        this.storeId = storeId;
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
    
    public String ObjectsToStr(List<UserAction> actions) {
        return new Gson().toJson(actions);
    }
}
