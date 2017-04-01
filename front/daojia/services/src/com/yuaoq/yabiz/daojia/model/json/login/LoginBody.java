package com.yuaoq.yabiz.daojia.model.json.login;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import org.json.JSONException;
import org.json.JSONObject;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by changsy on 16/9/12.
 */
public class LoginBody {
    public String returnLink;

    public static LoginBody objectFromData(String str) {
        
        return new Gson().fromJson(str, LoginBody.class);
    }
    
    public static LoginBody objectFromData(String str, String key) {
        
        try {
            JSONObject jsonObject = new JSONObject(str);
            
            return new Gson().fromJson(jsonObject.getString(str), LoginBody.class);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        
        return null;
    }
    
    public static List<LoginBody> arrayLoginBodyFromData(String str) {
        
        Type listType = new TypeToken<ArrayList<LoginBody>>() {
        }.getType();
        
        return new Gson().fromJson(str, listType);
    }
    
    
}
