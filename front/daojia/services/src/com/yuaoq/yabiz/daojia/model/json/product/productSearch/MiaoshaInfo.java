package com.yuaoq.yabiz.daojia.model.json.product.productSearch;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import org.json.JSONException;
import org.json.JSONObject;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by changsy on 16/9/2.
 */
public class MiaoshaInfo {
    public Object miaoshaRemainTime;
    public Object miaoshaRemainName;
    public Object syntime;
    public Object words;
    public Object status;
    public boolean miaosha;
    public String button;
    public int miaoShaSate;
    
    public static MiaoshaInfo objectFromData(String str) {
        
        return new Gson().fromJson(str, MiaoshaInfo.class);
    }
    
    public static MiaoshaInfo objectFromData(String str, String key) {
        
        try {
            JSONObject jsonObject = new JSONObject(str);
            
            return new Gson().fromJson(jsonObject.getString(str), MiaoshaInfo.class);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        
        return null;
    }
    
    public static List<MiaoshaInfo> arrayMiaoshaInfoFromData(String str) {
        
        Type listType = new TypeToken<ArrayList<MiaoshaInfo>>() {
        }.getType();
        
        return new Gson().fromJson(str, listType);
    }
}