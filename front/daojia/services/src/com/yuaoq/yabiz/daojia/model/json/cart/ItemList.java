package com.yuaoq.yabiz.daojia.model.json.cart;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import org.json.JSONException;
import org.json.JSONObject;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by changsy on 16/9/12.
 */
public class ItemList {
    public String suitType;
    public String suitName;
    public boolean buttonFlag;
    public int giftCanChooseNum;
    public List<String> suitDescrip;
    /**
     * skuId : 2001863086
     * checkType : 1
     * skuName : 欧莱雅男士炭爽冰感洁面膏100ml/支
     * basePrice : 39.00
     * price : 39.00
     * imageUrl : https://img10.360buyimg.com/n7//jfs/t2068/303/2909639208/110599/b6767a09/56f7b11aN1bc84244.jpg
     * cartNum : 1
     * skuState : 1
     * tags : []
     * picker : {"start":1,"end":99}
     * weight : 0.1kg
     */
    
    public List<SkuList> skuList;
    
    public static ItemList objectFromData(String str) {
        
        return new Gson().fromJson(str, ItemList.class);
    }
    
    public static ItemList objectFromData(String str, String key) {
        
        try {
            JSONObject jsonObject = new JSONObject(str);
            
            return new Gson().fromJson(jsonObject.getString(str), ItemList.class);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        
        return null;
    }
    
    public static List<ItemList> arrayItemListFromData(String str) {
        
        Type listType = new TypeToken<ArrayList<ItemList>>() {
        }.getType();
        
        return new Gson().fromJson(str, listType);
    }
    
    public String getSuitType() {
        return suitType;
    }
    
    public void setSuitType(String suitType) {
        this.suitType = suitType;
    }
    
    public String getSuitName() {
        return suitName;
    }
    
    public void setSuitName(String suitName) {
        this.suitName = suitName;
    }
    
    public boolean isButtonFlag() {
        return buttonFlag;
    }
    
    public void setButtonFlag(boolean buttonFlag) {
        this.buttonFlag = buttonFlag;
    }
    
    public int getGiftCanChooseNum() {
        return giftCanChooseNum;
    }
    
    public void setGiftCanChooseNum(int giftCanChooseNum) {
        this.giftCanChooseNum = giftCanChooseNum;
    }
    
    public List<String> getSuitDescrip() {
        return suitDescrip;
    }
    
    public void setSuitDescrip(List<String> suitDescrip) {
        this.suitDescrip = suitDescrip;
    }
    
    public List<SkuList> getSkuList() {
        return skuList;
    }
    
    public void setSkuList(List<SkuList> skuList) {
        this.skuList = skuList;
    }
}
