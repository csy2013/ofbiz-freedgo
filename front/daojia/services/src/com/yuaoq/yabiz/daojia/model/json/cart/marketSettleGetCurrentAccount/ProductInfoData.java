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
public class ProductInfoData {
    
    /**
     * skuId : 2001863086
     * name : 欧莱雅男士炭爽冰感洁面膏100ml/支
     * price : 3900
     * quantity : 1
     * img : https://img30.360buyimg.com/n7/jfs/t2068/303/2909639208/110599/b6767a09/56f7b11aN1bc84244.jpg
     * maxPurchaseNum : 0
     * minPurchaseNum : 0
     * editorPurchaseNum : 0
     * money : 3900
     */
    
    public int skuId;
    public String name;
    public int price;
    public int quantity;
    public String img;
    public int maxPurchaseNum;
    public int minPurchaseNum;
    public int editorPurchaseNum;
    public int money;
    
    public static ProductInfoData objectFromData(String str) {
        
        return new Gson().fromJson(str, ProductInfoData.class);
    }
    
    public static ProductInfoData objectFromData(String str, String key) {
        
        try {
            JSONObject jsonObject = new JSONObject(str);
            
            return new Gson().fromJson(jsonObject.getString(str), ProductInfoData.class);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        
        return null;
    }
    
    public static List<ProductInfoData> arrayProductInfoDataFromData(String str) {
        
        Type listType = new TypeToken<ArrayList<ProductInfoData>>() {
        }.getType();
        
        return new Gson().fromJson(str, listType);
    }
}
