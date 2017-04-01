package com.yuaoq.yabiz.daojia.model.json.cart.cartQuery;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.yuaoq.yabiz.daojia.model.json.BaseResult;
import org.json.JSONException;
import org.json.JSONObject;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by changsy on 16/9/19.
 */
public class CartQuery extends BaseResult {
    
    /**
     * id : cartV3_0_0.query
     * code : 0
     * msg : 成功
     * detail :
     * success : true
     * result : [{"storeId":"10060816","orgCode":"75109","storeName":"阳光菜园-丽岛路店","openJPIndustry":"2","storeImgUrl":"https://img30.360buyimg.com/vendersettle/jfs/t2665/180/874487141/56943/ac3d7686/5729721fNa2b2c534.jpg","payMoneyPriceValue":"5.00","payMoneyName":"合计:","itemList":[{"skuId":"2002436595","price":"5.00","imageUrl":"https://img10.360buyimg.com/n7//jfs/t1381/209/1169681626/408421/76c6d88b/55bf28eeNe720dfdb.jpg","cartNum":1,"skuState":1,"updateTime":1471667525561}],"totalNum":1}]
     */
    
    public String detail;
    /**
     * storeId : 10060816
     * orgCode : 75109
     * storeName : 阳光菜园-丽岛路店
     * openJPIndustry : 2
     * storeImgUrl : https://img30.360buyimg.com/vendersettle/jfs/t2665/180/874487141/56943/ac3d7686/5729721fNa2b2c534.jpg
     * payMoneyPriceValue : 5.00
     * payMoneyName : 合计:
     * itemList : [{"skuId":"2002436595","price":"5.00","imageUrl":"https://img10.360buyimg.com/n7//jfs/t1381/209/1169681626/408421/76c6d88b/55bf28eeNe720dfdb.jpg","cartNum":1,"skuState":1,"updateTime":1471667525561}]
     * totalNum : 1
     */
    
    public List<CartQueryResult> result;
    
    public CartQuery(String id, String code, String msg, boolean success) {
        super(id, code, msg, success);
    }
    
    public static CartQuery objectFromData(String str) {
        
        return new Gson().fromJson(str, CartQuery.class);
    }
    
    public static CartQuery objectFromData(String str, String key) {
        
        try {
            JSONObject jsonObject = new JSONObject(str);
            
            return new Gson().fromJson(jsonObject.getString(str), CartQuery.class);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        
        return null;
    }
    
    public static List<CartQuery> arrayCartQueryFromData(String str) {
        
        Type listType = new TypeToken<ArrayList<CartQuery>>() {
        }.getType();
        
        return new Gson().fromJson(str, listType);
    }
    
    public String getDetail() {
        return detail;
    }
    
    public void setDetail(String detail) {
        this.detail = detail;
    }
    
    public List<CartQueryResult> getResult() {
        return result;
    }
    
    public void setResult(List<CartQueryResult> result) {
        this.result = result;
    }
}
