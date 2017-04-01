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
public class CartItemResult {
    public String storeId;
    public String orgCode;
    public String storeName;
    public String openJPIndustry;
    public String discountName;
    public String payMoneyPriceValue;
    public int buttonState;
    public String buttonName;
    public int totalNum;
    public String numWeightDesc;
    public boolean wholeStore;
    public boolean authorize;
    /**
     * suitType : suit
     * suitName : 满减
     * suitDescrip : ["已满28已减7"]
     * skuList : [{"skuId":"2001863086","checkType":1,"skuName":"欧莱雅男士炭爽冰感洁面膏100ml/支","basePrice":"39.00","price":"39.00","imageUrl":"https://img10.360buyimg.com/n7//jfs/t2068/303/2909639208/110599/b6767a09/56f7b11aN1bc84244.jpg","cartNum":1,"skuState":1,"tags":[],"picker":{"start":1,"end":99},"weight":"0.1kg"}]
     * buttonFlag : true
     * giftCanChooseNum : 0
     */
    
    public List<ItemList> itemList;
    
    public static CartItemResult objectFromData(String str) {
        
        return new Gson().fromJson(str, CartItemResult.class);
    }
    
    public static CartItemResult objectFromData(String str, String key) {
        
        try {
            JSONObject jsonObject = new JSONObject(str);
            
            return new Gson().fromJson(jsonObject.getString(str), CartItemResult.class);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        
        return null;
    }
    
    public static List<CartItemResult> arrayCartItemResultFromData(String str) {
        
        Type listType = new TypeToken<ArrayList<CartItemResult>>() {
        }.getType();
        
        return new Gson().fromJson(str, listType);
    }
    
    public String getStoreId() {
        return storeId;
    }
    
    public void setStoreId(String storeId) {
        this.storeId = storeId;
    }
    
    public String getOrgCode() {
        return orgCode;
    }
    
    public void setOrgCode(String orgCode) {
        this.orgCode = orgCode;
    }
    
    public String getStoreName() {
        return storeName;
    }
    
    public void setStoreName(String storeName) {
        this.storeName = storeName;
    }
    
    public String getOpenJPIndustry() {
        return openJPIndustry;
    }
    
    public void setOpenJPIndustry(String openJPIndustry) {
        this.openJPIndustry = openJPIndustry;
    }
    
    public String getDiscountName() {
        return discountName;
    }
    
    public void setDiscountName(String discountName) {
        this.discountName = discountName;
    }
    
    public String getPayMoneyPriceValue() {
        return payMoneyPriceValue;
    }
    
    public void setPayMoneyPriceValue(String payMoneyPriceValue) {
        this.payMoneyPriceValue = payMoneyPriceValue;
    }
    
    public int getButtonState() {
        return buttonState;
    }
    
    public void setButtonState(int buttonState) {
        this.buttonState = buttonState;
    }
    
    public String getButtonName() {
        return buttonName;
    }
    
    public void setButtonName(String buttonName) {
        this.buttonName = buttonName;
    }
    
    public int getTotalNum() {
        return totalNum;
    }
    
    public void setTotalNum(int totalNum) {
        this.totalNum = totalNum;
    }
    
    public String getNumWeightDesc() {
        return numWeightDesc;
    }
    
    public void setNumWeightDesc(String numWeightDesc) {
        this.numWeightDesc = numWeightDesc;
    }
    
    public boolean isWholeStore() {
        return wholeStore;
    }
    
    public void setWholeStore(boolean wholeStore) {
        this.wholeStore = wholeStore;
    }
    
    public boolean isAuthorize() {
        return authorize;
    }
    
    public void setAuthorize(boolean authorize) {
        this.authorize = authorize;
    }
    
    public List<ItemList> getItemList() {
        return itemList;
    }
    
    public void setItemList(List<ItemList> itemList) {
        this.itemList = itemList;
    }
}