package com.yuaoq.yabiz.daojia.model.json.account;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import org.json.JSONException;
import org.json.JSONObject;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by changsy on 16/9/14.
 */
public class AccountInfo {
    
    
    public String id;
    public String code;
    public String msg;
    public boolean success;
    public Object result;
    
    
    public AccountInfo(String id, String code, String msg, boolean success, String result) {
        this.id = id;
        this.code = code;
        this.msg = msg;
        this.success = success;
        this.result = result;
    }
    
    public AccountInfo(String id, String code, String msg, boolean success, List<List<Result>> result) {
        this.id = id;
        this.code = code;
        this.msg = msg;
        this.success = success;
        this.result = result;
    }
    
    public static AccountInfo objectFromData(String str) {
        
        return new Gson().fromJson(str, AccountInfo.class);
    }
    
    public static AccountInfo objectFromData(String str, String key) {
        
        try {
            JSONObject jsonObject = new JSONObject(str);
            
            return new Gson().fromJson(jsonObject.getString(str), AccountInfo.class);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        
        return null;
    }
    
    public static List<AccountInfo> arrayAccountInfoFromData(String str) {
        
        Type listType = new TypeToken<ArrayList<AccountInfo>>() {
        }.getType();
        
        return new Gson().fromJson(str, listType);
    }
    
    public static class Result {
        public String accName;
        public String acctype;
        public int value;
        
        public static Result objectFromData(String str) {
            
            return new Gson().fromJson(str, Result.class);
        }
        
        public static Result objectFromData(String str, String key) {
            
            try {
                JSONObject jsonObject = new JSONObject(str);
                
                return new Gson().fromJson(jsonObject.getString(str), Result.class);
            } catch (JSONException e) {
                e.printStackTrace();
            }
            
            return null;
        }
        
        public static List<Result> arrayResultFromData(String str) {
            
            Type listType = new TypeToken<ArrayList<Result>>() {
            }.getType();
            
            return new Gson().fromJson(str, listType);
        }
    }
}
