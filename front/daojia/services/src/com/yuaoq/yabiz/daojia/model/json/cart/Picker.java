package com.yuaoq.yabiz.daojia.model.json.cart;

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
public class Picker {
    public int start;
    public int end;
    
    public Picker(int start, int end) {
        this.start = start;
        this.end = end;
    }
    
    public static Picker objectFromData(String str) {
        
        return new Gson().fromJson(str, Picker.class);
    }
    
    public static Picker objectFromData(String str, String key) {
        
        try {
            JSONObject jsonObject = new JSONObject(str);
            
            return new Gson().fromJson(jsonObject.getString(str), Picker.class);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        
        return null;
    }
    
    public static List<Picker> arrayPickerFromData(String str) {
        
        Type listType = new TypeToken<ArrayList<Picker>>() {
        }.getType();
        
        return new Gson().fromJson(str, listType);
    }
    
    public int getStart() {
        return start;
    }
    
    public void setStart(int start) {
        this.start = start;
    }
    
    public int getEnd() {
        return end;
    }
    
    public void setEnd(int end) {
        this.end = end;
    }
}
