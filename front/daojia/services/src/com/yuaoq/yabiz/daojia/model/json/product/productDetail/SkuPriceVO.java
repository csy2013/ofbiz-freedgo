package com.yuaoq.yabiz.daojia.model.json.product.productDetail;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import org.json.JSONException;
import org.json.JSONObject;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by changsy on 16/9/4.
 */
public class SkuPriceVO {
    public String skuId;
    public String realTimePrice;
    public String basicPrice;
    public String mkPrice;
    public int promotion;
    
    public static SkuPriceVO objectFromData(String str) {
        
        return new Gson().fromJson(str, SkuPriceVO.class);
    }
    
    public static SkuPriceVO objectFromData(String str, String key) {
        
        try {
            JSONObject jsonObject = new JSONObject(str);
            
            return new Gson().fromJson(jsonObject.getString(str), SkuPriceVO.class);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        
        return null;
    }
    
    public static List<SkuPriceVO> arraySkuPriceVOFromData(String str) {
        
        Type listType = new TypeToken<ArrayList<SkuPriceVO>>() {
        }.getType();
        
        return new Gson().fromJson(str, listType);
    }
}
