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
public class Product5 {
    
    
    public String floorStyle;
    public FloorTitle floorTitle;
    public boolean edge;
    public String styleTpl;
    public int index;
    public long timer;
    public BusyAttrMaps busyAttrMaps;
    public List<Product5Data> data;
    
    public Product5(String floorStyle, FloorTitle floorTitle, boolean edge, String styleTpl, int index, long timer, BusyAttrMaps busyAttrMaps, List<Product5Data> data) {
        
        
        this.floorStyle = floorStyle;
        this.floorTitle = floorTitle;
        this.edge = edge;
        this.styleTpl = styleTpl;
        this.index = index;
        this.timer = timer;
        this.busyAttrMaps = busyAttrMaps;
        this.data = data;
    }
    
    public String getFloorStyle() {
        return floorStyle;
    }
    
    public void setFloorStyle(String floorStyle) {
        this.floorStyle = floorStyle;
    }
    
    public FloorTitle getFloorTitle() {
        return floorTitle;
    }
    
    public void setFloorTitle(FloorTitle floorTitle) {
        this.floorTitle = floorTitle;
    }
    
    public boolean isEdge() {
        return edge;
    }
    
    public void setEdge(boolean edge) {
        this.edge = edge;
    }
    
    public String getStyleTpl() {
        return styleTpl;
    }
    
    public void setStyleTpl(String styleTpl) {
        this.styleTpl = styleTpl;
    }
    
    public int getIndex() {
        return index;
    }
    
    public void setIndex(int index) {
        this.index = index;
    }
    
    public long getTimer() {
        return timer;
    }
    
    public void setTimer(long timer) {
        this.timer = timer;
    }
    
    public BusyAttrMaps getBusyAttrMaps() {
        return busyAttrMaps;
    }
    
    public void setBusyAttrMaps(BusyAttrMaps busyAttrMaps) {
        this.busyAttrMaps = busyAttrMaps;
    }
    
    public List<Product5Data> getData() {
        return data;
    }
    
    public void setData(List<Product5Data> data) {
        this.data = data;
    }
    
    public static Product5 objectFromData(String str) {
        
        return new Gson().fromJson(str, Product5.class);
    }
    
    public static Product5 objectFromData(String str, String key) {
        
        try {
            JSONObject jsonObject = new JSONObject(str);
            
            return new Gson().fromJson(jsonObject.getString(str), Product5.class);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        
        return null;
    }
    
    public static List<Product5> arrayProduct5DetailFromData(String str) {
        
        Type listType = new TypeToken<ArrayList<Product5>>() {
        }.getType();
        
        return new Gson().fromJson(str, listType);
    }
    
    
    
    
    
   
}
