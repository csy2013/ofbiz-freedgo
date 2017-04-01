package com.yuaoq.yabiz.daojia.model.json.order.orderinfo;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import org.json.JSONException;
import org.json.JSONObject;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by changsy on 16/9/22.
 */

public class ContactList {
    public String phoneNum;
    public String code;
    public String text;
    
    public ContactList(String phoneNum, String code, String text) {
        this.phoneNum = phoneNum;
        this.code = code;
        this.text = text;
    }
    
    public static ContactList objectFromData(String str) {
        
        return new Gson().fromJson(str, ContactList.class);
    }
    
    public static ContactList objectFromData(String str, String key) {
        
        try {
            JSONObject jsonObject = new JSONObject(str);
            
            return new Gson().fromJson(jsonObject.getString(str), ContactList.class);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        
        return null;
    }
    
    public static List<ContactList> arrayContactListFromData(String str) {
        
        Type listType = new TypeToken<ArrayList<ContactList>>() {
        }.getType();
        
        return new Gson().fromJson(str, listType);
    }
}
