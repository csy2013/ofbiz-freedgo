package com.yuaoq.yabiz.daojia.model.json.cart.marketSettleGetCurrentAccount;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import org.json.JSONException;
import org.json.JSONObject;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by changsy on 16/9/18.
 */
public class DisMoneyInfoData {
    
    /**
     * name : 满减优惠
     * value : -￥7.00
     * color : #0F0F0F
     * flagColor : #6CC272
     * flagText : 满减
     */
    
    public String name;
    public String value;
    public String color;
    public String flagColor;
    public String flagText;
    
    public static DisMoneyInfoData objectFromData(String str) {
        
        return new Gson().fromJson(str, DisMoneyInfoData.class);
    }
    
    public static DisMoneyInfoData objectFromData(String str, String key) {
        
        try {
            JSONObject jsonObject = new JSONObject(str);
            
            return new Gson().fromJson(jsonObject.getString(str), DisMoneyInfoData.class);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        
        return null;
    }
    
    public static List<DisMoneyInfoData> arrayDisMoneyInfoFromData(String str) {
        
        Type listType = new TypeToken<ArrayList<DisMoneyInfoData>>() {
        }.getType();
        
        return new Gson().fromJson(str, listType);
    }
}
