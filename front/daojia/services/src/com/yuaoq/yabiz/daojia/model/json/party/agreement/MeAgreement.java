package com.yuaoq.yabiz.daojia.model.json.party.agreement;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import org.json.JSONException;
import org.json.JSONObject;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by changsy on 16/9/15.
 */
public class MeAgreement {
    
    
    public String code;
    public String msg;
    public String result;
    public boolean success;
    
    public static MeAgreement objectFromData(String str) {
        
        return new Gson().fromJson(str, MeAgreement.class);
    }
    
    public static MeAgreement objectFromData(String str, String key) {
        
        try {
            JSONObject jsonObject = new JSONObject(str);
            
            return new Gson().fromJson(jsonObject.getString(str), MeAgreement.class);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        
        return null;
    }
    
    public static List<MeAgreement> arrayMeAgreementFromData(String str) {
        
        Type listType = new TypeToken<ArrayList<MeAgreement>>() {
        }.getType();
        
        return new Gson().fromJson(str, listType);
    }
}
