package com.yuaoq.yabiz.daojia.model.json.common.store.storeDetail;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import org.json.JSONException;
import org.json.JSONObject;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by changsy on 16/9/2.
 */
public class StoreDetailResult {
    public String templeType;
    /**
     * storeName : 苏客中式餐饮-华侨城店
     * storeUrl : https://img30.360buyimg.com/vendersettle/jfs/t2626/169/3052367740/4284/d2e7154c/577e252dNe1bb8e57.jpg
     * logoUrl : http://img30.360buyimg.com/vendersettle/jfs/t2626/169/3052367740/4284/d2e7154c/577e252dNe1bb8e57.jpg
     * serviceTimes : [{"startTime":"10:30","endTime":"13:30"},{"startTime":"16:30","endTime":"19:30"}]
     * upToSendprice : 0
     * shopFreeFreight : 基础运费3元
     * to : productList
     * params : {"data":{"storeId":"11027324","orgCode":"77458"},"searchType":2}
     * storeId : 11027324
     * orgCode : 77458
     * scoreAvg : 4.5
     * tags : [{"name":"满减","iconText":"满减","type":6,"belongIndustry":1,"words":"全场满25减6","activityRange":0,"colorCode":"5FBC65"}]
     * newMessageNum : 0
     * storeStar : 4.5
     * inSaleNum : 47
     * monthSaleNum : 310
     * storeAddress : 江苏省南京市浦口区大桥北路1号华侨广场1楼
     * industry : 30
     * showType : 2
     * storeCertificateUrl : https://daojia.jd.com/activity/shangjiazizhi/index.html?type=meishi&orgCode=77458&certificateServerId=0
     * flag : false
     * carrierNo : 9966
     * expectArrivedTips : [{"type":1,"msg":"现在下单，预计最早今日17:48送达"}]
     * storeTel : 13914465769
     * isOverZone : false
     */
    
    public StoreInfo storeInfo;
    public boolean isCart;
    public boolean isSearch;
    public boolean is618;
    public List<?> coupons;
    public List<?> storeBanner;
    /**
     * catId : 4135631
     * parentId : 0
     * title : 新式足料饭
     * productCount : 4
     * sort : 0
     * level : 1
     * childCategoryList : []
     * promotLabel :
     * ispromotcat : false
     * user_action : {"catename1":"新式足料饭","store_id":"11027324"}
     * openCatetory : false
     */
    
    public List<CateList> cateList;
    
    public StoreDetailResult(String templeType, StoreInfo storeInfo, boolean isCart, boolean isSearch, boolean is618, List<?> coupons, List<?> storeBanner, List<CateList> cateList) {
        this.templeType = templeType;
        this.storeInfo = storeInfo;
        this.isCart = isCart;
        this.isSearch = isSearch;
        this.is618 = is618;
        this.coupons = coupons;
        this.storeBanner = storeBanner;
        this.cateList = cateList;
    }
    
    public static StoreDetailResult objectFromData(String str) {
        
        return new Gson().fromJson(str, StoreDetailResult.class);
    }
    
    public static StoreDetailResult objectFromData(String str, String key) {
        
        try {
            JSONObject jsonObject = new JSONObject(str);
            
            return new Gson().fromJson(jsonObject.getString(str), StoreDetailResult.class);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        
        return null;
    }
    
    public static List<StoreDetailResult> arrayResultFromData(String str) {
        
        Type listType = new TypeToken<ArrayList<StoreDetailResult>>() {
        }.getType();
        
        return new Gson().fromJson(str, listType);
    }
}
