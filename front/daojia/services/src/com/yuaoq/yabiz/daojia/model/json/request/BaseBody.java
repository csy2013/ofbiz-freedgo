package com.yuaoq.yabiz.daojia.model.json.request;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import org.json.JSONException;
import org.json.JSONObject;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by changsy on 16/9/5.
 */
public class BaseBody {
    public String storeId;
    public String activityId;
    public String promotionType;
    public String skuId;
    public String longitude;
    public String latitude;
    
    public static BaseBody objectFromData(String str) {
        
        return new Gson().fromJson(str, BaseBody.class);
    }
    
    public static BaseBody objectFromData(String str, String key) {
        
        try {
            JSONObject jsonObject = new JSONObject(str);
            
            return new Gson().fromJson(jsonObject.getString(str), BaseBody.class);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        
        return null;
    }
    
    public static List<StoreDetailBody> arrayBodyFromData(String str) {
        
        Type listType = new TypeToken<ArrayList<StoreDetailBody>>() {
        }.getType();
        
        return new Gson().fromJson(str, listType);
    }
}
