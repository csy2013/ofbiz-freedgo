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
public class FloorTitle {
    public String floorName;
    public String floorWords;
    
    public FloorTitle(String floorName, String floorWords) {
        this.floorName = floorName;
        this.floorWords = floorWords;
    }
    
    public static FloorTitle objectFromData(String str) {
        
        return new Gson().fromJson(str, FloorTitle.class);
    }
    
    public static FloorTitle objectFromData(String str, String key) {
        
        try {
            JSONObject jsonObject = new JSONObject(str);
            
            return new Gson().fromJson(jsonObject.getString(str), FloorTitle.class);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        
        return null;
    }
    
    public static List<FloorTitle> arrayFloorTitleFromData(String str) {
        
        Type listType = new TypeToken<ArrayList<FloorTitle>>() {
        }.getType();
        
        return new Gson().fromJson(str, listType);
    }
}
