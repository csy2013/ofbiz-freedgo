package com.yuaoq.yabiz.daojia.model.json.common.act;

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
public class Act1 {
    public String floorStyle;
    public FloorTitle floorTitle;
    public boolean edge;
    public String styleTpl;
    public long index;
    public long timer;
    public List<Act1Data> data;
    
    public Act1(String floorStyle, FloorTitle floorTitle, boolean edge, String styleTpl, long index, long timer, List<Act1Data> data) {
        this.floorStyle = floorStyle;
        this.floorTitle = floorTitle;
        this.edge = edge;
        this.styleTpl = styleTpl;
        this.index = index;
        this.timer = timer;
        this.data = data;
    }
    
    public static Act1 objectFromData(String str) {
        
        return new Gson().fromJson(str, Act1.class);
    }
    
    public static Act1 objectFromData(String str, String key) {
        
        try {
            JSONObject jsonObject = new JSONObject(str);
            
            return new Gson().fromJson(jsonObject.getString(str), Act1.class);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        
        return null;
    }
    
    public static List<Act1> arrayAct1FromData(String str) {
        
        Type listType = new TypeToken<ArrayList<Act1>>() {
        }.getType();
        
        return new Gson().fromJson(str, listType);
    }
    
    public static class FloorTitle {
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
    
    
}
