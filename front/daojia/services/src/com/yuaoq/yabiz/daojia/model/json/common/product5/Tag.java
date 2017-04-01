package com.yuaoq.yabiz.daojia.model.json.common.product5;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import org.json.JSONException;
import org.json.JSONObject;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by changsy on 2016/10/19.
 */
public class Tag {
    
    
    public String name;
    public String iconText;
    public int type;
    public int belongIndustry;
    public String words;
    public String colorCode;
    
    public Tag(String name, String iconText, int type, int belongIndustry, String words, String colorCode) {
        this.name = name;
        this.iconText = iconText;
        this.type = type;
        this.belongIndustry = belongIndustry;
        this.words = words;
        this.colorCode = colorCode;
    }
    
    public static Tag objectFromData(String str) {
        
        return new Gson().fromJson(str, Tag.class);
    }
    
    public static Tag objectFromData(String str, String key) {
        
        try {
            JSONObject jsonObject = new JSONObject(str);
            
            return new Gson().fromJson(jsonObject.getString(str), Tag.class);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        
        return null;
    }
    
    public static List<Tag> arrayTagFromData(String str) {
        
        Type listType = new TypeToken<ArrayList<Tag>>() {
        }.getType();
        
        return new Gson().fromJson(str, listType);
    }
}
