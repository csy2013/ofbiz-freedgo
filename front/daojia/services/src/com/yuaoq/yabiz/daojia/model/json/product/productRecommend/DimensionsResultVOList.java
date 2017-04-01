package com.yuaoq.yabiz.daojia.model.json.product.productRecommend;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.yuaoq.yabiz.daojia.model.json.product.ProductInfo;
import org.json.JSONException;
import org.json.JSONObject;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by changsy on 16/9/5.
 */
public class DimensionsResultVOList {
    
    public String dimensionName;
    /**
     * skuId : 2002983305
     * skuName : 酸辣土豆丝
     * imgUrl : https://img30.360buyimg.com/n2//jfs/t2974/3/1598648713/193771/68f3033f/578728c3Ne30b5810.jpg
     * storeId : 11027324
     * orgCode : 77458
     * fixedStatus : true
     * incart : false
     * incartCount : 0
     * stockCount : 1
     * tags : []
     * miaoshaInfo : {"miaosha":false,"button":"","miaoShaSate":0}
     * saleStatus : true
     * showCartButton : true
     * userActionSku : {"sku_id":"2002983305","solution":"lf","store_id":"11027324"}
     * catId :
     * funcIndicatins :
     * standard :
     * aging : -1
     * promotion : 1
     * basicPrice : 3.00
     * realTimePrice : 3.00
     * venderId : 77458
     * mkPrice : 暂无报价
     */
    
    public List<ProductInfo> recommendSkuVOList;
    
    public static DimensionsResultVOList objectFromData(String str) {
        
        return new Gson().fromJson(str, DimensionsResultVOList.class);
    }
    
    public static DimensionsResultVOList objectFromData(String str, String key) {
        
        try {
            JSONObject jsonObject = new JSONObject(str);
            
            return new Gson().fromJson(jsonObject.getString(str), DimensionsResultVOList.class);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        
        return null;
    }
    
    public static List<DimensionsResultVOList> arrayDimensionsResultVOListFromData(String str) {
        
        Type listType = new TypeToken<ArrayList<DimensionsResultVOList>>() {
        }.getType();
        
        return new Gson().fromJson(str, listType);
    }
    
    
}
