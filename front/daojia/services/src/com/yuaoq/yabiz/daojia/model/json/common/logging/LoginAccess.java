package com.yuaoq.yabiz.daojia.model.json.common.logging;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import org.json.JSONException;
import org.json.JSONObject;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by changsy on 16/9/10.
 */
public class LoginAccess {
    
    
    /**
     * errCode : 0
     * g2Int : 60
     * g2Sz : 10
     * g3Int : 60
     * g3Sz : 10
     * g4Int : 60
     * g4Sz : 10
     * ret : 1
     * wifiInt : 30
     * wifiSz : 20
     */
    
    public String errCode;
    public String g2Int;
    public String g2Sz;
    public String g3Int;
    public String g3Sz;
    public String g4Int;
    public String g4Sz;
    public String ret;
    public String wifiInt;
    public String wifiSz;
    
    public LoginAccess(String errCode, String g2Int, String g2Sz, String g3Int, String g3Sz, String g4Int, String g4Sz, String ret, String wifiInt, String wifiSz) {
        this.errCode = errCode;
        this.g2Int = g2Int;
        this.g2Sz = g2Sz;
        this.g3Int = g3Int;
        this.g3Sz = g3Sz;
        this.g4Int = g4Int;
        this.g4Sz = g4Sz;
        this.ret = ret;
        this.wifiInt = wifiInt;
        this.wifiSz = wifiSz;
    }
    
    public static LoginAccess objectFromData(String str) {
        
        return new Gson().fromJson(str, LoginAccess.class);
    }
    
    public static LoginAccess objectFromData(String str, String key) {
        
        try {
            JSONObject jsonObject = new JSONObject(str);
            
            return new Gson().fromJson(jsonObject.getString(str), LoginAccess.class);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        
        return null;
    }
    
    public static List<LoginAccess> arrayLoginAccessFromData(String str) {
        
        Type listType = new TypeToken<ArrayList<LoginAccess>>() {
        }.getType();
        
        return new Gson().fromJson(str, listType);
    }
}
