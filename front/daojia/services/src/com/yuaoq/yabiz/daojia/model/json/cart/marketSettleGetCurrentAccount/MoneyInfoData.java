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
public class MoneyInfoData {
    
    
    /**
     * name : 运费金额
     * value : ￥2.00
     * color : #0F0F0F
     * desc : 本单运费共计2元，包括<br/>·基础运费 2元<br/>
     * deliverType :
     */
    
    public String name;
    public String value;
    public String color;
    public String desc;
    public String deliverType;
    
    public MoneyInfoData(String name, String value, String color, String desc, String deliverType) {
        this.name = name;
        this.value = value;
        this.color = color;
        this.desc = desc;
        this.deliverType = deliverType;
    }
    
    public static MoneyInfoData objectFromData(String str) {
        
        return new Gson().fromJson(str, MoneyInfoData.class);
    }
    
    public static MoneyInfoData objectFromData(String str, String key) {
        
        try {
            JSONObject jsonObject = new JSONObject(str);
            
            return new Gson().fromJson(jsonObject.getString(str), MoneyInfoData.class);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        
        return null;
    }
    
    public static List<MoneyInfoData> arrayMoneyInfoDataFromData(String str) {
        
        Type listType = new TypeToken<ArrayList<MoneyInfoData>>() {
        }.getType();
        
        return new Gson().fromJson(str, listType);
    }
}
