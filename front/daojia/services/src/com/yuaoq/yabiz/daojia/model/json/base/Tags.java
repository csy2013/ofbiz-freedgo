package com.yuaoq.yabiz.daojia.model.json.base;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import org.json.JSONException;
import org.json.JSONObject;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by changsy on 16/9/4.
 */
public class Tags {
    public String name;
    public String iconText;
    public int type;
    public int belongIndustry;
    public String words;
    public int activityRange;
    public String colorCode;
    
    public Tags(String name, String iconText, int type, int belongIndustry, String words, int activityRange, String colorCode) {
        this.name = name;
        this.iconText = iconText;
        this.type = type;
        this.belongIndustry = belongIndustry;
        this.words = words;
        this.activityRange = activityRange;
        this.colorCode = colorCode;
    }
    
    public static Tags objectFromData(String str) {
        
        return new Gson().fromJson(str, Tags.class);
    }
    
    public static Tags objectFromData(String str, String key) {
        
        try {
            JSONObject jsonObject = new JSONObject(str);
            
            return new Gson().fromJson(jsonObject.getString(str), Tags.class);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        
        return null;
    }
    
    public static List<Tags> arrayTagsFromData(String str) {
        
        Type listType = new TypeToken<ArrayList<Tags>>() {
        }.getType();
        
        return new Gson().fromJson(str, listType);
    }
}