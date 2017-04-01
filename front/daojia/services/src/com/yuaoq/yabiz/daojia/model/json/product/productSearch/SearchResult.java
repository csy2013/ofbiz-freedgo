package com.yuaoq.yabiz.daojia.model.json.product.productSearch;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.yuaoq.yabiz.daojia.model.json.product.ProductInfo;
import org.json.JSONException;
import org.json.JSONObject;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by changsy on 16/9/2.
 */
public class SearchResult {
    
    public String solution;
    public String templeType;
    public boolean showCart;
    public boolean showSearch;
    public int page;
    public int count;
    public String storeId;
    public String storeName;
    public String orgCode;
    public String user_action;
    public Object onlinedebugId;
    public Object promptWord;
    public String promptTips;
    /**
     * skuId : 2002983426
     * skuName : 干煸四季豆
     * imgUrl : https://img30.360buyimg.com/n2//jfs/t2791/111/3271013738/222926/462ed8f5/578733feN01340fe7.jpg
     * storeId : 11027324
     * orgCode : 77458
     * fixedStatus : true
     * preName : null
     * incart : false
     * incartCount : 0
     * stockCount : 980
     * tags : []
     * miaoshaInfo : {"miaoshaRemainTime":null,"miaoshaRemainName":null,"syntime":null,"words":null,"status":null,"miaosha":false,"button":"","miaoShaSate":0}
     * buriedPoint : null
     * saleStatus : true
     * showCartButton : true
     * userActionSku : null
     * catId :
     * standard :
     * funcIndicatins :
     * aging : -1
     * venderId : 77458
     * basicPrice : 8.00
     * mkPrice : 暂无报价
     * realTimePrice : 8.00
     * promotion : 1
     */
    
    public ProductInfo anchoredProduct;
    public String storePageType;
    public List<ProductInfo> searchResultVOList;
    
    public SearchResult(String solution, String templeType, boolean showCart, boolean showSearch, int page, int count, String storeId, String storeName, String orgCode, String user_action, Object onlinedebugId, Object promptWord, String promptTips, ProductInfo anchoredProduct, String storePageType, List<ProductInfo> searchResultVOList) {
        this.solution = solution;
        this.templeType = templeType;
        this.showCart = showCart;
        this.showSearch = showSearch;
        this.page = page;
        this.count = count;
        this.storeId = storeId;
        this.storeName = storeName;
        this.orgCode = orgCode;
        this.user_action = user_action;
        this.onlinedebugId = onlinedebugId;
        this.promptWord = promptWord;
        this.promptTips = promptTips;
        this.anchoredProduct = anchoredProduct;
        this.storePageType = storePageType;
        this.searchResultVOList = searchResultVOList;
    }
    
    public static SearchResult objectFromData(String str) {
        
        return new Gson().fromJson(str, SearchResult.class);
    }
    
    public static SearchResult objectFromData(String str, String key) {
        
        try {
            JSONObject jsonObject = new JSONObject(str);
            
            return new Gson().fromJson(jsonObject.getString(str), SearchResult.class);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        
        return null;
    }
    
    public static List<SearchResult> arrayResultFromData(String str) {
        
        Type listType = new TypeToken<ArrayList<SearchResult>>() {
        }.getType();
        
        return new Gson().fromJson(str, listType);
    }
    
    
}
