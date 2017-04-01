package com.yuaoq.yabiz.daojia.model.json.request;

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
public class StoreDetailBody {
    
    
    /**
     * storeId : 10055645
     * activityId :
     * promotionType : 9
     * skuId : 2001862457
     * longitude : 0
     * latitude : 0
     */
    
    public String storeId;
    public String activityId;
    public String promotionType;
    public String skuId;
    public String longitude;
    public String latitude;
    
    public static StoreDetailBody objectFromData(String str) {
        
        return new Gson().fromJson(str, StoreDetailBody.class);
    }
    
    public static StoreDetailBody objectFromData(String str, String key) {
        
        try {
            JSONObject jsonObject = new JSONObject(str);
            
            return new Gson().fromJson(jsonObject.getString(str), StoreDetailBody.class);
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
