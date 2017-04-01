package com.yuaoq.yabiz.daojia.model.json.common.IndexPage;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import org.json.JSONException;
import org.json.JSONObject;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by changsy on 16/8/30.
 */
public class MiaoShaList {
    
    public String name;
    public String price;
    public String miaoShaPrice;
    public String imageurl;
    public String storeId;
    public String skuId;
    public String stateName;
    public int miaoShaSate;
    public String orgCode;
    
    public MiaoShaList(String name, String price, String miaoShaPrice, String imageurl, String storeId, String skuId, String stateName, int miaoShaSate, String orgCode) {
        this.name = name;
        this.price = price;
        this.miaoShaPrice = miaoShaPrice;
        this.imageurl = imageurl;
        this.storeId = storeId;
        this.skuId = skuId;
        this.stateName = stateName;
        this.miaoShaSate = miaoShaSate;
        this.orgCode = orgCode;
    }
    
    public static MiaoShaList objectFromData(String str) {
        
        return new Gson().fromJson(str, MiaoShaList.class);
    }
    
    public static MiaoShaList objectFromData(String str, String key) {
        
        try {
            JSONObject jsonObject = new JSONObject(str);
            
            return new Gson().fromJson(jsonObject.getString(str), MiaoShaList.class);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        
        return null;
    }
    
    public static List<MiaoShaList> arrayMiaoShaListFromData(String str) {
        
        Type listType = new TypeToken<ArrayList<MiaoShaList>>() {
        }.getType();
        
        return new Gson().fromJson(str, listType);
    }
    
    
}
