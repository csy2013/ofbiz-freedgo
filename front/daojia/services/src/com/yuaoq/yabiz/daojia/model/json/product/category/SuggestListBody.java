package com.yuaoq.yabiz.daojia.model.json.product.category;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import org.json.JSONException;
import org.json.JSONObject;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by changsy on 16/9/10.
 */
public class SuggestListBody {
    
    
    /**
     * type : 2
     * longitude : 118.72446
     * latitude : 32.12944
     * storeIds : []
     * key : 苹果3
     */
    
    public int type;
    public double longitude;
    public double latitude;
    public String key;
    public List<Integer> storeIds;
    
    public static SuggestListBody objectFromData(String str) {
        
        return new Gson().fromJson(str, SuggestListBody.class);
    }
    
    public static SuggestListBody objectFromData(String str, String key) {
        
        try {
            JSONObject jsonObject = new JSONObject(str);
            
            return new Gson().fromJson(jsonObject.getString(str), SuggestListBody.class);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        
        return null;
    }
    
    public static List<SuggestListBody> arraySuggestListBodyFromData(String str) {
        
        Type listType = new TypeToken<ArrayList<SuggestListBody>>() {
        }.getType();
        
        return new Gson().fromJson(str, listType);
    }
}
