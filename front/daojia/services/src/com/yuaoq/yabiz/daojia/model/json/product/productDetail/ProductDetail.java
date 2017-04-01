package com.yuaoq.yabiz.daojia.model.json.product.productDetail;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.yuaoq.yabiz.daojia.model.json.BaseResult;
import org.json.JSONException;
import org.json.JSONObject;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by changsy on 16/9/4.
 */
public class ProductDetail extends BaseResult {
    
    /**
     * id : product.detail
     * code : 0
     * msg : 成功
     * result : {"skuId":2002983426,"name":"干煸四季豆","orgCode":"77458","priceUnit":"0","adword":"","productInfoType":-1,"fixedStatus":true,"catId":"","standard":"","funcIndicatins":"","hasSaleAttr":false,"image":[{"small":"https://img30.360buyimg.com/n1//jfs/t2791/111/3271013738/222926/462ed8f5/578733feN01340fe7.jpg","big":"https://img30.360buyimg.com/n1//jfs/t2791/111/3271013738/222926/462ed8f5/578733feN01340fe7.jpg","share":"http://img30.360buyimg.com/n1//jfs/t2791/111/3271013738/222926/462ed8f5/578733feN01340fe7.jpg"}],"storeInfo":{"venderId":"77458","venderName":"苏客中式餐饮-华侨城店","venderPhone":"13914465769","storeId":"11027324","storeName":"苏客中式餐饮-华侨城店"},"tags":[{"name":"满减","iconText":"满减","type":6,"belongIndustry":1,"words":"全场满25减6","activityRange":1,"colorCode":"5FBC65"}],"productComment":{"totalScore":"5.0","goodRating":"100%","commentNum":"1","hasMore":false,"commentList":[{"commentName":"j***z","commentScore":"5","commentTime":"2016.07.23 08:04","commentDesc":"品质量俱佳，强烈推荐。","littleImg":[],"bigCommentImg":[]}]},"isRemind":true,"buttonEnable":true,"showStateEnum":"Add_To_Shopping_Cart","skuPriceVO":{"skuId":"2002983426","realTimePrice":"8.00","basicPrice":"8.00","mkPrice":"暂无报价","promotion":1},"productTypeEnum":"NORMAL","showTimLine":false,"prescription":false,"inCartCount":0,"isInScope":true,"productType":1,"showState":3,"showStateName":"加入购物车","venderId":"77458"}
     * success : true
     */
    
    
    /**
     * skuId : 2002983426
     * name : 干煸四季豆
     * orgCode : 77458
     * priceUnit : 0
     * adword :
     * productInfoType : -1
     * fixedStatus : true
     * catId :
     * standard :
     * funcIndicatins :
     * hasSaleAttr : false
     * image : [{"small":"https://img30.360buyimg.com/n1//jfs/t2791/111/3271013738/222926/462ed8f5/578733feN01340fe7.jpg","big":"https://img30.360buyimg.com/n1//jfs/t2791/111/3271013738/222926/462ed8f5/578733feN01340fe7.jpg","share":"http://img30.360buyimg.com/n1//jfs/t2791/111/3271013738/222926/462ed8f5/578733feN01340fe7.jpg"}]
     * storeInfo : {"venderId":"77458","venderName":"苏客中式餐饮-华侨城店","venderPhone":"13914465769","storeId":"11027324","storeName":"苏客中式餐饮-华侨城店"}
     * tags : [{"name":"满减","iconText":"满减","type":6,"belongIndustry":1,"words":"全场满25减6","activityRange":1,"colorCode":"5FBC65"}]
     * productComment : {"totalScore":"5.0","goodRating":"100%","commentNum":"1","hasMore":false,"commentList":[{"commentName":"j***z","commentScore":"5","commentTime":"2016.07.23 08:04","commentDesc":"品质量俱佳，强烈推荐。","littleImg":[],"bigCommentImg":[]}]}
     * isRemind : true
     * buttonEnable : true
     * showStateEnum : Add_To_Shopping_Cart
     * skuPriceVO : {"skuId":"2002983426","realTimePrice":"8.00","basicPrice":"8.00","mkPrice":"暂无报价","promotion":1}
     * productTypeEnum : NORMAL
     * showTimLine : false
     * prescription : false
     * inCartCount : 0
     * isInScope : true
     * productType : 1
     * showState : 3
     * showStateName : 加入购物车
     * venderId : 77458
     */
    
    public ProductDetailResult result;
    
    public ProductDetail(String id, String code, String msg, boolean success) {
        super(id, code, msg, success);
    }
    
    public static ProductDetail objectFromData(String str) {
        
        return new Gson().fromJson(str, ProductDetail.class);
    }
    
    public static ProductDetail objectFromData(String str, String key) {
        
        try {
            JSONObject jsonObject = new JSONObject(str);
            
            return new Gson().fromJson(jsonObject.getString(str), ProductDetail.class);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        
        return null;
    }
    
    public static List<ProductDetail> arrayProductDetailFromData(String str) {
        
        Type listType = new TypeToken<ArrayList<ProductDetail>>() {
        }.getType();
        
        return new Gson().fromJson(str, listType);
    }
    
    
}
