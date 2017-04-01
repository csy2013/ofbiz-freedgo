package com.yuaoq.yabiz.daojia.model.json.common.store;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import org.json.JSONException;
import org.json.JSONObject;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by changsy on 16/8/31.
 */
public class Data {
    
    public String floorStyle;
    public String styleTpl;
    public boolean edge;
    /**
     * floorName : 附近的店铺
     * floorWords :
     */
    
    public FloorTitle floorTitle;
    public boolean channelPage;
    /**
     * floorCellData : {"to":"store","params":{"orgCode":74597,"imgOrder":1,"storeId":"10055645"},"words":"民生超市·百姓永辉","imgUrl":"https://img30.360buyimg.com/vendersettle/jfs/t1963/102/1922378949/3615/5fe01608/56e626a2N3056b2fd.png","title":"4.8","userAction":"{\"orgCode\":74597,\"storeId\":\"10055645\",\"imgOrder\":1,\"recommendType\":2}","name":"永辉超市-滨江新城店","tags":[{"name":"白条","iconText":"白条","type":8,"belongIndustry":3,"words":"该商家支持使用京东白条","colorCode":"19BAFF"}],"serviceTimes":[{"startTime":"09:00","endTime":"19:00"}],"deliveryFirst":"可预订","storeType":"综合超市","inSale":"936件商品","monthSale":"月售3960单","socreAvg":4.8,"storeStar":4.75,"inCartNum":"0","recommendType":2,"recommendTypeName":"推荐","skus":[{"skuId":"2001858205","realTimePrice":"4.99","basicPrice":"5.90","mkPrice":"暂无报价","promotion":0,"skuName":"本地红提约500g/份","imgUrl":"https://img30.360buyimg.com/n6/jfs/t2212/57/2939422418/254973/6157dd04/56f4d563N81a5be58.jpg","storeId":"10055645","tags":[{"name":"限时抢","iconText":"秒杀","type":9,"belongIndustry":1,"words":"限时抢购,限购3件","activityRange":0,"colorCode":"FF5959"}],"miaoshaInfo":{"miaoshaRemainName":"距恢复原价","button":"","miaoShaSate":0,"miaosha":false},"venderId":"74597","userAction":"{\"sku_id\":\"2001858205\",\"sku_order\":1,\"store_id\":\"10055645\",\"store_order\":\"1\",\"store_recommend_type\":\"2\"}","aging":"-1","showCart":false,"incart":false,"numInCart":0,"stockCount":7555,"showCartButton":true,"incartCount":0,"orgCode":"74597","storeName":"","noPrefixImgUrl":"/jfs/t2212/57/2939422418/254973/6157dd04/56f4d563N81a5be58.jpg"},{"skuId":"2001892550","realTimePrice":"9.90","basicPrice":"15.90","mkPrice":"暂无报价","promotion":0,"skuName":"蒙牛消健黄桃粒酸牛奶(冷藏)100*8/组","imgUrl":"https://img30.360buyimg.com/n6/jfs/t2779/35/21679913/314057/8fcf2132/56fba3e6N5251ecbe.jpg","storeId":"10055645","tags":[{"name":"限时抢","iconText":"秒杀","type":9,"belongIndustry":1,"words":"限时抢购,限购4件","activityRange":0,"colorCode":"FF5959"}],"miaoshaInfo":{"miaoshaRemainName":"距恢复原价","button":"","miaoShaSate":0,"miaosha":false},"venderId":"74597","userAction":"{\"sku_id\":\"2001892550\",\"sku_order\":2,\"store_id\":\"10055645\",\"store_order\":\"1\",\"store_recommend_type\":\"2\"}","aging":"-1","showCart":false,"incart":false,"numInCart":0,"stockCount":89,"showCartButton":true,"incartCount":0,"orgCode":"74597","storeName":"","noPrefixImgUrl":"/jfs/t2779/35/21679913/314057/8fcf2132/56fba3e6N5251ecbe.jpg"},{"skuId":"2001875464","realTimePrice":"13.90","basicPrice":"19.50","mkPrice":"暂无报价","promotion":0,"skuName":"维达超韧抽取纸面巾130抽*6/V2239 195mm*133mm/提","imgUrl":"https://img30.360buyimg.com/n6/jfs/t2062/309/2209938658/279526/97a1813f/56f90545Nfc790a9b.jpg","storeId":"10055645","tags":[{"name":"限时抢","iconText":"秒杀","type":9,"belongIndustry":1,"words":"限时抢购,限购4件","activityRange":0,"colorCode":"FF5959"}],"miaoshaInfo":{"miaoshaRemainName":"距恢复原价","button":"","miaoShaSate":0,"miaosha":false},"venderId":"74597","userAction":"{\"sku_id\":\"2001875464\",\"sku_order\":3,\"store_id\":\"10055645\",\"store_order\":\"1\",\"store_recommend_type\":\"2\"}","aging":"-1","showCart":false,"incart":false,"numInCart":0,"stockCount":242,"showCartButton":true,"incartCount":0,"orgCode":"74597","storeName":"","noPrefixImgUrl":"/jfs/t2062/309/2209938658/279526/97a1813f/56f90545Nfc790a9b.jpg"},{"skuId":"2001875479","realTimePrice":"9.90","basicPrice":"12.90","mkPrice":"暂无报价","promotion":0,"skuName":"立白清新柠檬洗洁精1.5kg/瓶","imgUrl":"https://img30.360buyimg.com/n6/jfs/t2803/338/39947332/258585/e4a4b622/56fce894Nbd4e41a1.jpg","storeId":"10055645","tags":[{"name":"限时抢","iconText":"秒杀","type":9,"belongIndustry":1,"words":"限时抢购,限购4件","activityRange":0,"colorCode":"FF5959"}],"miaoshaInfo":{"miaoshaRemainName":"距恢复原价","button":"","miaoShaSate":0,"miaosha":false},"venderId":"74597","userAction":"{\"sku_id\":\"2001875479\",\"sku_order\":4,\"store_id\":\"10055645\",\"store_order\":\"1\",\"store_recommend_type\":\"2\"}","aging":"-1","showCart":false,"incart":false,"numInCart":0,"stockCount":95,"showCartButton":true,"incartCount":0,"orgCode":"74597","storeName":"","noPrefixImgUrl":"/jfs/t2803/338/39947332/258585/e4a4b622/56fce894Nbd4e41a1.jpg"}],"carrierNo":9966,"closeStatus":0}
     * floorCellType : common
     */
    
    
    public List<StoreData> data;
    
    public Data(String floorStyle, String styleTpl, boolean edge, FloorTitle floorTitle, boolean channelPage, List<StoreData> data) {
        this.floorStyle = floorStyle;
        this.styleTpl = styleTpl;
        this.edge = edge;
        this.floorTitle = floorTitle;
        this.channelPage = channelPage;
        this.data = data;
    }
    
    public static Data objectFromData(String str) {
        
        return new Gson().fromJson(str, Data.class);
    }
    
    public static Data objectFromData(String str, String key) {
        
        try {
            JSONObject jsonObject = new JSONObject(str);
            
            return new Gson().fromJson(jsonObject.getString(str), Data.class);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        
        return null;
    }
    
    public static List<Data> arrayDataFromData(String str) {
        
        Type listType = new TypeToken<ArrayList<Data>>() {
        }.getType();
        
        return new Gson().fromJson(str, listType);
    }
    
}