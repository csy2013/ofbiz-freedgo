package com.yuaoq.yabiz.daojia.model.json.cart;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.yuaoq.yabiz.daojia.model.json.BaseResult;
import org.json.JSONException;
import org.json.JSONObject;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by changsy on 16/9/12.
 */
public class CartItem extends BaseResult {
    
    
    /**
     * id : cartV3_2_0.querySingleCart
     * code : 0
     * msg : 成功
     * detail :
     * success : true
     * result : {"storeId":"10055645","orgCode":"74597","storeName":"永辉超市-滨江新城店","openJPIndustry":"1","discountName":"已减7元","payMoneyPriceValue":"32.00","buttonState":0,"buttonName":"去结算","itemList":[{"suitType":"suit","suitName":"满减","suitDescrip":["已满28已减7"],"skuList":[{"skuId":"2001863086","checkType":1,"skuName":"欧莱雅男士炭爽冰感洁面膏100ml/支","basePrice":"39.00","price":"39.00","imageUrl":"https://img10.360buyimg.com/n7//jfs/t2068/303/2909639208/110599/b6767a09/56f7b11aN1bc84244.jpg","cartNum":1,"skuState":1,"tags":[],"picker":{"start":1,"end":99},"weight":"0.1kg"}],"buttonFlag":true,"giftCanChooseNum":0}],"totalNum":1,"numWeightDesc":"(已选1件，共0.1kg)","wholeStore":true,"authorize":false}
     */
    
    public String detail;
    /**
     * storeId : 10055645
     * orgCode : 74597
     * storeName : 永辉超市-滨江新城店
     * openJPIndustry : 1
     * discountName : 已减7元
     * payMoneyPriceValue : 32.00
     * buttonState : 0
     * buttonName : 去结算
     * itemList : [{"suitType":"suit","suitName":"满减","suitDescrip":["已满28已减7"],"skuList":[{"skuId":"2001863086","checkType":1,"skuName":"欧莱雅男士炭爽冰感洁面膏100ml/支","basePrice":"39.00","price":"39.00","imageUrl":"https://img10.360buyimg.com/n7//jfs/t2068/303/2909639208/110599/b6767a09/56f7b11aN1bc84244.jpg","cartNum":1,"skuState":1,"tags":[],"picker":{"start":1,"end":99},"weight":"0.1kg"}],"buttonFlag":true,"giftCanChooseNum":0}]
     * totalNum : 1
     * numWeightDesc : (已选1件，共0.1kg)
     * wholeStore : true
     * authorize : false
     */
    
    public CartItemResult result;
    
    public CartItem(String id, String code, String msg, boolean success) {
        super(id, code, msg, success);
    }
    
    public static CartItem objectFromData(String str) {
        
        return new Gson().fromJson(str, CartItem.class);
    }
    
    public static CartItem objectFromData(String str, String key) {
        
        try {
            JSONObject jsonObject = new JSONObject(str);
            
            return new Gson().fromJson(jsonObject.getString(str), CartItem.class);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        
        return null;
    }
    
    public static List<CartItem> arrayCartItemFromData(String str) {
        
        Type listType = new TypeToken<ArrayList<CartItem>>() {
        }.getType();
        
        return new Gson().fromJson(str, listType);
    }
    

}
