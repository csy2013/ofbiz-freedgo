package com.yuaoq.yabiz.daojia.model.json.common;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by changsy on 16/8/29.
 */
public class Location {
    private double lat;
    private double lng;
    
    public Location objectFromData(String str) {
        
        return new Gson().fromJson(str, Location.class);
    }
    
    public List<Location> arrayLocationObjFromData(String str) {
        
        Type listType = new TypeToken<ArrayList<Location>>() {
        }.getType();
        
        return new Gson().fromJson(str, listType);
    }
    
    public double getLat() {
        return lat;
    }
    
    public void setLat(double lat) {
        this.lat = lat;
    }
    
    public double getLng() {
        return lng;
    }
    
    public void setLng(double lng) {
        this.lng = lng;
    }
}