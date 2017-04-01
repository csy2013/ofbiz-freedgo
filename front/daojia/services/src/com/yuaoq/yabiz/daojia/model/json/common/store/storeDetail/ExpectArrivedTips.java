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
public class ExpectArrivedTips {
    public int type;
    public String msg;
    
    public ExpectArrivedTips(int type, String msg) {
        this.type = type;
        this.msg = msg;
    }
    
    public static ExpectArrivedTips objectFromData(String str) {
        
        return new Gson().fromJson(str, ExpectArrivedTips.class);
    }
    
    public static ExpectArrivedTips objectFromData(String str, String key) {
        
        try {
            JSONObject jsonObject = new JSONObject(str);
            
            return new Gson().fromJson(jsonObject.getString(str), ExpectArrivedTips.class);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        
        return null;
    }
    
    public static List<ExpectArrivedTips> arrayExpectArrivedTipsFromData(String str) {
        
        Type listType = new TypeToken<ArrayList<ExpectArrivedTips>>() {
        }.getType();
        
        return new Gson().fromJson(str, listType);
    }
    
}
