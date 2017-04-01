package com.yuaoq.yabiz.daojia.model.json.product.productDetail;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.yuaoq.yabiz.daojia.model.json.base.Tags;
import org.json.JSONException;
import org.json.JSONObject;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by changsy on 16/9/4.
 */
public class ProductDetailResult {
    public int skuId;
    public String name;
    public String orgCode;
    public String priceUnit;
    public String adword;
    public int productInfoType;
    public boolean fixedStatus;
    public String catId;
    public String standard;
    public String funcIndicatins;
    public boolean hasSaleAttr;
    /**
     * venderId : 77458
     * venderName : 苏客中式餐饮-华侨城店
     * venderPhone : 13914465769
     * storeId : 11027324
     * storeName : 苏客中式餐饮-华侨城店
     */
    
    public StoreInfo storeInfo;
    /**
     * totalScore : 5.0
     * goodRating : 100%
     * commentNum : 1
     * hasMore : false
     * commentList : [{"commentName":"j***z","commentScore":"5","commentTime":"2016.07.23 08:04","commentDesc":"品质量俱佳，强烈推荐。","littleImg":[],"bigCommentImg":[]}]
     */
    
    public ProductComment productComment;
    public boolean isRemind;
    public boolean buttonEnable;
    public String showStateEnum;
    /**
     * skuId : 2002983426
     * realTimePrice : 8.00
     * basicPrice : 8.00
     * mkPrice : 暂无报价
     * promotion : 1
     */
    
    public SkuPriceVO skuPriceVO;
    public String productTypeEnum;
    public boolean showTimLine;
    public boolean prescription;
    public int inCartCount;
    public boolean isInScope;
    public int productType;
    public int showState;
    public String showStateName;
    public String venderId;
    /**
     * small : https://img30.360buyimg.com/n1//jfs/t2791/111/3271013738/222926/462ed8f5/578733feN01340fe7.jpg
     * big : https://img30.360buyimg.com/n1//jfs/t2791/111/3271013738/222926/462ed8f5/578733feN01340fe7.jpg
     * share : http://img30.360buyimg.com/n1//jfs/t2791/111/3271013738/222926/462ed8f5/578733feN01340fe7.jpg
     */
    
    public List<ProductImage> image;
    /**
     * name : 满减
     * iconText : 满减
     * type : 6
     * belongIndustry : 1
     * words : 全场满25减6
     * activityRange : 1
     * colorCode : 5FBC65
     */
    
    public List<Tags> tags;
    
    public static ProductDetailResult objectFromData(String str) {
        
        return new Gson().fromJson(str, ProductDetailResult.class);
    }
    
    public static ProductDetailResult objectFromData(String str, String key) {
        
        try {
            JSONObject jsonObject = new JSONObject(str);
            
            return new Gson().fromJson(jsonObject.getString(str), ProductDetailResult.class);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        
        return null;
    }
    
    public static List<ProductDetailResult> arrayResultFromData(String str) {
        
        Type listType = new TypeToken<ArrayList<ProductDetailResult>>() {
        }.getType();
        
        return new Gson().fromJson(str, listType);
    }
    
}
