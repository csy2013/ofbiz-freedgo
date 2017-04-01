package com.yuaoq.yabiz.daojia.model.json.common.store;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.yuaoq.yabiz.daojia.model.json.base.Tags;
import org.json.JSONException;
import org.json.JSONObject;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by changsy on 16/8/31.
 */
public class Skus {
    public String skuId;
    public String realTimePrice;
    public String basicPrice;
    public String mkPrice;
    public int promotion;
    public String skuName;
    public String imgUrl;
    public String storeId;
    /**
     * miaoshaRemainName : 距恢复原价
     * button :
     * miaoShaSate : 0
     * miaosha : false
     */
    
    public MiaoshaInfo miaoshaInfo;
    public String venderId;
    public String userAction;
    public String aging;
    public boolean showCart;
    public boolean incart;
    public int numInCart;
    public int stockCount;
    public boolean showCartButton;
    public int incartCount;
    public String orgCode;
    public String storeName;
    public String noPrefixImgUrl;
    /**
     * name : 限时抢
     * iconText : 秒杀
     * type : 9
     * belongIndustry : 1
     * words : 限时抢购,限购3件
     * activityRange : 0
     * colorCode : FF5959
     */
    
    public List<Tags> tags;
    
    public static Skus objectFromData(String str) {
        
        return new Gson().fromJson(str, Skus.class);
    }
    
    public static Skus objectFromData(String str, String key) {
        
        try {
            JSONObject jsonObject = new JSONObject(str);
            
            return new Gson().fromJson(jsonObject.getString(str), Skus.class);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        
        return null;
    }
    
    public static List<Skus> arraySkusFromData(String str) {
        
        Type listType = new TypeToken<ArrayList<Skus>>() {
        }.getType();
        
        return new Gson().fromJson(str, listType);
    }
    
    
}