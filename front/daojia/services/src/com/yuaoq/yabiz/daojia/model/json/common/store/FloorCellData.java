package com.yuaoq.yabiz.daojia.model.json.common.store;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.yuaoq.yabiz.daojia.model.json.base.Tags;
import org.json.JSONException;
import org.json.JSONObject;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by changsy on 16/8/31.
 */
public class FloorCellData {
    public String to;
    /**
     * orgCode : 74597
     * imgOrder : 1
     * storeId : 10055645
     */
    
    
    public Params params;
    public String words;
    public String imgUrl;
    public String title;
    public String userAction;
    public String name;
    public String deliveryFirst;
    public String storeType;
    public String inSale;
    public String monthSale;
    public double socreAvg;
    public double storeStar;
    public String inCartNum;
    public int recommendType;
    public String recommendTypeName;
    public int carrierNo;
    public int closeStatus;
    /**
     * name : 白条
     * iconText : 白条
     * type : 8
     * belongIndustry : 3
     * words : 该商家支持使用京东白条
     * colorCode : 19BAFF
     */
    
    public List<Tags> tags;
    /**
     * startTime : 09:00
     * endTime : 19:00
     */
    
    public List<ServiceTimes> serviceTimes;
    /**
     * skuId : 2001858205
     * realTimePrice : 4.99
     * basicPrice : 5.90
     * mkPrice : 暂无报价
     * promotion : 0
     * skuName : 本地红提约500g/份
     * imgUrl : https://img30.360buyimg.com/n6/jfs/t2212/57/2939422418/254973/6157dd04/56f4d563N81a5be58.jpg
     * storeId : 10055645
     * tags : [{"name":"限时抢","iconText":"秒杀","type":9,"belongIndustry":1,"words":"限时抢购,限购3件","activityRange":0,"colorCode":"FF5959"}]
     * miaoshaInfo : {"miaoshaRemainName":"距恢复原价","button":"","miaoShaSate":0,"miaosha":false}
     * venderId : 74597
     * userAction : {"sku_id":"2001858205","sku_order":1,"store_id":"10055645","store_order":"1","store_recommend_type":"2"}
     * aging : -1
     * showCart : false
     * incart : false
     * numInCart : 0
     * stockCount : 7555
     * showCartButton : true
     * incartCount : 0
     * orgCode : 74597
     * storeName :
     * noPrefixImgUrl : /jfs/t2212/57/2939422418/254973/6157dd04/56f4d563N81a5be58.jpg
     */
    
    public List<Skus> skus;
    public FloorCellData(String to, Params params, String words, String imgUrl, String title, String userAction, String name, String deliveryFirst, String storeType, String inSale, String monthSale, double socreAvg, double storeStar, String inCartNum, int recommendType, String recommendTypeName, int carrierNo, int closeStatus, List<Tags> tags, List<ServiceTimes> serviceTimes, List<Skus> skus) {
        this.to = to;
        this.params = params;
        this.words = words;
        this.imgUrl = imgUrl;
        this.title = title;
        this.userAction = userAction;
        this.name = name;
        this.deliveryFirst = deliveryFirst;
        this.storeType = storeType;
        this.inSale = inSale;
        this.monthSale = monthSale;
        this.socreAvg = socreAvg;
        this.storeStar = storeStar;
        this.inCartNum = inCartNum;
        this.recommendType = recommendType;
        this.recommendTypeName = recommendTypeName;
        this.carrierNo = carrierNo;
        this.closeStatus = closeStatus;
        this.tags = tags;
        this.serviceTimes = serviceTimes;
        this.skus = skus;
    }
    
    public static FloorCellData objectFromData(String str) {
        
        return new Gson().fromJson(str, FloorCellData.class);
    }
    
    public static FloorCellData objectFromData(String str, String key) {
        
        try {
            JSONObject jsonObject = new JSONObject(str);
            
            return new Gson().fromJson(jsonObject.getString(str), FloorCellData.class);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        
        return null;
    }
    
    public static List<FloorCellData> arrayFloorCellDataFromData(String str) {
        
        Type listType = new TypeToken<ArrayList<FloorCellData>>() {
        }.getType();
        
        return new Gson().fromJson(str, listType);
    }
    
    
}