package com.yuaoq.yabiz.daojia.model.json.map.suggestion;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import org.json.JSONException;
import org.json.JSONObject;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by changsy on 16/9/26.
 */
public class SuggestionAddress {
    
    /**
     * status : 0
     * message : query ok
     * count : 10
     * data : [{"id":"15477449678633568235","title":"金鼎轩(地坛店)","address":"北京市东城区和平里西街77号","type":0,"location":{"lat":39.951,"lng":116.41613},"adcode":110101,"province":"北京市","city":"北京市","district":"东城区"},{"id":"13774482554307834979","title":"海底捞火锅(牡丹园店)","address":"北京市海淀区花园东路2号(牡丹宾馆北200米)","type":0,"location":{"lat":39.97884,"lng":116.36849},"adcode":110108,"province":"北京市","city":"北京市","district":"海淀区"},{"id":"7334967656608876089","title":"聚宝源火锅城","address":"北京市西城区牛街5-2号","type":0,"location":{"lat":39.88677,"lng":116.36328},"adcode":110102,"province":"北京市","city":"北京市","district":"西城区"},{"id":"18179071038304970242","title":"眉州东坡酒楼(中关村店)","address":"北京市海淀区中关村大街27号中关村大厦2层","type":0,"location":{"lat":39.97753,"lng":116.31643},"adcode":110108,"province":"北京市","city":"北京市","district":"海淀区"},{"id":"18139472983375685763","title":"日昌餐馆港式茶餐厅(地安门总店)","address":"北京市西城区地安门西大街14","type":0,"location":{"lat":39.93325,"lng":116.39412},"adcode":110102,"province":"北京市","city":"北京市","district":"西城区"},{"id":"17977284172807120930","title":"度小月餐厅(芳草地店)","address":"北京市朝阳区东大桥路9号侨福芳草地大厦地下2层","type":0,"location":{"lat":39.91956,"lng":116.44984},"adcode":110105,"province":"北京市","city":"北京市","district":"朝阳区"},{"id":"17955393126807421499","title":"南京大牌档(中关村店)","address":"北京市海淀区中关村大街5号中关村广场购物中心2层","type":0,"location":{"lat":39.98276,"lng":116.31348},"adcode":110108,"province":"北京市","city":"北京市","district":"海淀区"},{"id":"17662065097386151011","title":"窝夫小子(富华大厦店)","address":"北京市东城区朝阳门北大街8号富华大厦A座B1层17","type":0,"location":{"lat":39.93078,"lng":116.43522},"adcode":110101,"province":"北京市","city":"北京市","district":"东城区"},{"id":"17283883824033405115","title":"火炉火年糕火锅(爱琴海店)","address":"北京市朝阳区七圣中街12号院爱琴海购物中心4层","type":0,"location":{"lat":39.97235,"lng":116.43604},"adcode":110105,"province":"北京市","city":"北京市","district":"朝阳区"},{"id":"17003011861735444102","title":"金鼎轩(亚运村店)","address":"北京市朝阳区安慧北里逸园15号","type":0,"location":{"lat":40.00253,"lng":116.41298},"adcode":110105,"province":"北京市","city":"北京市","district":"朝阳区"}]
     * request_id : 5779439414260014911
     */
    
    public int status;
    public String message;
    public int count;
    public String request_id;
    /**
     * id : 15477449678633568235
     * title : 金鼎轩(地坛店)
     * address : 北京市东城区和平里西街77号
     * type : 0
     * location : {"lat":39.951,"lng":116.41613}
     * adcode : 110101
     * province : 北京市
     * city : 北京市
     * district : 东城区
     */
    
    public List<Data> data;
    
    public static SuggestionAddress objectFromData(String str) {
        
        return new Gson().fromJson(str, SuggestionAddress.class);
    }
    
    public static SuggestionAddress objectFromData(String str, String key) {
        
        try {
            JSONObject jsonObject = new JSONObject(str);
            
            return new Gson().fromJson(jsonObject.getString(str), SuggestionAddress.class);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        
        return null;
    }
    
    public static List<SuggestionAddress> arraySuggestionAddressFromData(String str) {
        
        Type listType = new TypeToken<ArrayList<SuggestionAddress>>() {
        }.getType();
        
        return new Gson().fromJson(str, listType);
    }
    
    public static class Data {
        public String id;
        public String title;
        public String address;
        public int type;
        /**
         * lat : 39.951
         * lng : 116.41613
         */
        
        public Location location;
        public int adcode;
        public String province;
        public String city;
        public String district;
        
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
        
        public static class Location {
            public double lat;
            public double lng;
            
            public static Location objectFromData(String str) {
                
                return new Gson().fromJson(str, Location.class);
            }
            
            public static Location objectFromData(String str, String key) {
                
                try {
                    JSONObject jsonObject = new JSONObject(str);
                    
                    return new Gson().fromJson(jsonObject.getString(str), Location.class);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                
                return null;
            }
            
            public static List<Location> arrayLocationFromData(String str) {
                
                Type listType = new TypeToken<ArrayList<Location>>() {
                }.getType();
                
                return new Gson().fromJson(str, listType);
            }
        }
    }
}
