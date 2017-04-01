package com.yuaoq.yabiz.daojia.model.json.common.store.storeDetail;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.yuaoq.yabiz.daojia.model.json.base.Tags;
import com.yuaoq.yabiz.daojia.model.json.common.store.ServiceTimes;
import org.json.JSONException;
import org.json.JSONObject;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by changsy on 16/9/2.
 */
public class StoreInfo {
    public String storeName;
    public String storeUrl;
    public String logoUrl;
    public int upToSendprice;
    public String shopFreeFreight;
    public String to;
    public String params;
    public String storeId;
    public String orgCode;
    public double scoreAvg;
    public int newMessageNum;
    public double storeStar;
    public String inSaleNum;
    public String monthSaleNum;
    public String storeAddress;
    public String industry;
    public String showType;
    public String storeCertificateUrl;
    public boolean flag;
    public int carrierNo;
    public String storeTel;
    public boolean isOverZone;
    /**
     * startTime : 10:30
     * endTime : 13:30
     */
    
    public List<ServiceTimes> serviceTimes;
    /**
     * name : 满减
     * iconText : 满减
     * type : 6
     * belongIndustry : 1
     * words : 全场满25减6
     * activityRange : 0
     * colorCode : 5FBC65
     */
    
    public List<Tags> tags;
    /**
     * type : 1
     * msg : 现在下单，预计最早今日17:48送达
     */
    
    public List<ExpectArrivedTips> expectArrivedTips;
    
    public StoreInfo(String storeName, String storeUrl, String logoUrl, int upToSendprice, String shopFreeFreight, String to, String params, String storeId, String orgCode, double scoreAvg, int newMessageNum, double storeStar, String inSaleNum, String monthSaleNum, String storeAddress, String industry, String showType, String storeCertificateUrl, boolean flag, int carrierNo, String storeTel, boolean isOverZone, List<ServiceTimes> serviceTimes, List<Tags> tags, List<ExpectArrivedTips> expectArrivedTips) {
        this.storeName = storeName;
        this.storeUrl = storeUrl;
        this.logoUrl = logoUrl;
        this.upToSendprice = upToSendprice;
        this.shopFreeFreight = shopFreeFreight;
        this.to = to;
        this.params = params;
        this.storeId = storeId;
        this.orgCode = orgCode;
        this.scoreAvg = scoreAvg;
        this.newMessageNum = newMessageNum;
        this.storeStar = storeStar;
        this.inSaleNum = inSaleNum;
        this.monthSaleNum = monthSaleNum;
        this.storeAddress = storeAddress;
        this.industry = industry;
        this.showType = showType;
        this.storeCertificateUrl = storeCertificateUrl;
        this.flag = flag;
        this.carrierNo = carrierNo;
        this.storeTel = storeTel;
        this.isOverZone = isOverZone;
        this.serviceTimes = serviceTimes;
        this.tags = tags;
        this.expectArrivedTips = expectArrivedTips;
    }
    
    public static StoreInfo objectFromData(String str) {
        
        return new Gson().fromJson(str, StoreInfo.class);
    }
    
    public static StoreInfo objectFromData(String str, String key) {
        
        try {
            JSONObject jsonObject = new JSONObject(str);
            
            return new Gson().fromJson(jsonObject.getString(str), StoreInfo.class);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        
        return null;
    }
    
    public static List<StoreInfo> arrayStoreInfoFromData(String str) {
        
        Type listType = new TypeToken<ArrayList<StoreInfo>>() {
        }.getType();
        
        return new Gson().fromJson(str, listType);
    }
}
    
     
