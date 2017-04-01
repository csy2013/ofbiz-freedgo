package com.yuaoq.yabiz.daojia.model.json.cart.marketSettleGetCurrentAccount;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import org.json.JSONException;
import org.json.JSONObject;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by changsy on 16/9/18.
 */
public class DeliverTimeData {
    
    
    /**
     * serverFlag : 2
     * data : [{"promiseDateText":"今天","promiseDate":"2016-08-20","promiseType":"2","promiseTimeRespItems":[{"promiseTimeText":"立即送达","promiseStartTime":"13:57","promiseEndTime":"13:57","steppedFreight":"2元运费","dingshida":false},{"promiseTimeText":"14:00-14:30","promiseStartTime":"14:00","promiseEndTime":"14:30","steppedFreight":"2元运费","dingshida":true},{"promiseTimeText":"14:30-15:00","promiseStartTime":"14:30","promiseEndTime":"15:00","steppedFreight":"2元运费","dingshida":true},{"promiseTimeText":"15:00-15:30","promiseStartTime":"15:00","promiseEndTime":"15:30","steppedFreight":"2元运费","dingshida":true},{"promiseTimeText":"15:30-16:00","promiseStartTime":"15:30","promiseEndTime":"16:00","steppedFreight":"2元运费","dingshida":true},{"promiseTimeText":"16:00-16:30","promiseStartTime":"16:00","promiseEndTime":"16:30","steppedFreight":"2元运费","dingshida":true},{"promiseTimeText":"16:30-17:00","promiseStartTime":"16:30","promiseEndTime":"17:00","steppedFreight":"2元运费","dingshida":true},{"promiseTimeText":"17:00-17:30","promiseStartTime":"17:00","promiseEndTime":"17:30","steppedFreight":"2元运费","dingshida":true},{"promiseTimeText":"17:30-18:00","promiseStartTime":"17:30","promiseEndTime":"18:00","steppedFreight":"2元运费","dingshida":true},{"promiseTimeText":"18:00-18:30","promiseStartTime":"18:00","promiseEndTime":"18:30","steppedFreight":"2元运费","dingshida":true},{"promiseTimeText":"18:30-19:00","promiseStartTime":"18:30","promiseEndTime":"19:00","steppedFreight":"2元运费","dingshida":true},{"promiseTimeText":"19:00-19:30","promiseStartTime":"19:00","promiseEndTime":"19:30","steppedFreight":"2元运费","dingshida":true},{"promiseTimeText":"19:30-20:00","promiseStartTime":"19:30","promiseEndTime":"20:00","steppedFreight":"2元运费","dingshida":true},{"promiseTimeText":"20:00-20:30","promiseStartTime":"20:00","promiseEndTime":"20:30","steppedFreight":"2元运费","dingshida":true},{"promiseTimeText":"20:30-21:00","promiseStartTime":"20:30","promiseEndTime":"21:00","steppedFreight":"2元运费","dingshida":true}]}]
     */
    
    public PromiseServerResp promiseServerResp;
    /**
     * pushTimeFlag : true
     * nonPushTimeText : 立即送达
     */
    
    public NoPushTime noPushTime;
    /**
     * promiseServerResp : {"serverFlag":"2","data":[{"promiseDateText":"今天","promiseDate":"2016-08-20","promiseType":"2","promiseTimeRespItems":[{"promiseTimeText":"立即送达","promiseStartTime":"13:57","promiseEndTime":"13:57","steppedFreight":"2元运费","dingshida":false},{"promiseTimeText":"14:00-14:30","promiseStartTime":"14:00","promiseEndTime":"14:30","steppedFreight":"2元运费","dingshida":true},{"promiseTimeText":"14:30-15:00","promiseStartTime":"14:30","promiseEndTime":"15:00","steppedFreight":"2元运费","dingshida":true},{"promiseTimeText":"15:00-15:30","promiseStartTime":"15:00","promiseEndTime":"15:30","steppedFreight":"2元运费","dingshida":true},{"promiseTimeText":"15:30-16:00","promiseStartTime":"15:30","promiseEndTime":"16:00","steppedFreight":"2元运费","dingshida":true},{"promiseTimeText":"16:00-16:30","promiseStartTime":"16:00","promiseEndTime":"16:30","steppedFreight":"2元运费","dingshida":true},{"promiseTimeText":"16:30-17:00","promiseStartTime":"16:30","promiseEndTime":"17:00","steppedFreight":"2元运费","dingshida":true},{"promiseTimeText":"17:00-17:30","promiseStartTime":"17:00","promiseEndTime":"17:30","steppedFreight":"2元运费","dingshida":true},{"promiseTimeText":"17:30-18:00","promiseStartTime":"17:30","promiseEndTime":"18:00","steppedFreight":"2元运费","dingshida":true},{"promiseTimeText":"18:00-18:30","promiseStartTime":"18:00","promiseEndTime":"18:30","steppedFreight":"2元运费","dingshida":true},{"promiseTimeText":"18:30-19:00","promiseStartTime":"18:30","promiseEndTime":"19:00","steppedFreight":"2元运费","dingshida":true},{"promiseTimeText":"19:00-19:30","promiseStartTime":"19:00","promiseEndTime":"19:30","steppedFreight":"2元运费","dingshida":true},{"promiseTimeText":"19:30-20:00","promiseStartTime":"19:30","promiseEndTime":"20:00","steppedFreight":"2元运费","dingshida":true},{"promiseTimeText":"20:00-20:30","promiseStartTime":"20:00","promiseEndTime":"20:30","steppedFreight":"2元运费","dingshida":true},{"promiseTimeText":"20:30-21:00","promiseStartTime":"20:30","promiseEndTime":"21:00","steppedFreight":"2元运费","dingshida":true}]}]}
     * noPushTime : {"pushTimeFlag":true,"nonPushTimeText":"立即送达"}
     * nonPushTimeText : 立即送达
     * deliverTimeFlag : true
     */
    
    public String nonPushTimeText;
    public boolean deliverTimeFlag;
    
    public static DeliverTimeData objectFromData(String str) {
        
        return new Gson().fromJson(str, DeliverTimeData.class);
    }
    
    public static DeliverTimeData objectFromData(String str, String key) {
        
        try {
            JSONObject jsonObject = new JSONObject(str);
            
            return new Gson().fromJson(jsonObject.getString(str), DeliverTimeData.class);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        
        return null;
    }
    
    public static List<DeliverTimeData> arrayDeliverTimeDataFromData(String str) {
        
        Type listType = new TypeToken<ArrayList<DeliverTimeData>>() {
        }.getType();
        
        return new Gson().fromJson(str, listType);
    }
    
    public static class PromiseServerResp {
        public String serverFlag;
        /**
         * promiseDateText : 今天
         * promiseDate : 2016-08-20
         * promiseType : 2
         * promiseTimeRespItems : [{"promiseTimeText":"立即送达","promiseStartTime":"13:57","promiseEndTime":"13:57","steppedFreight":"2元运费","dingshida":false},{"promiseTimeText":"14:00-14:30","promiseStartTime":"14:00","promiseEndTime":"14:30","steppedFreight":"2元运费","dingshida":true},{"promiseTimeText":"14:30-15:00","promiseStartTime":"14:30","promiseEndTime":"15:00","steppedFreight":"2元运费","dingshida":true},{"promiseTimeText":"15:00-15:30","promiseStartTime":"15:00","promiseEndTime":"15:30","steppedFreight":"2元运费","dingshida":true},{"promiseTimeText":"15:30-16:00","promiseStartTime":"15:30","promiseEndTime":"16:00","steppedFreight":"2元运费","dingshida":true},{"promiseTimeText":"16:00-16:30","promiseStartTime":"16:00","promiseEndTime":"16:30","steppedFreight":"2元运费","dingshida":true},{"promiseTimeText":"16:30-17:00","promiseStartTime":"16:30","promiseEndTime":"17:00","steppedFreight":"2元运费","dingshida":true},{"promiseTimeText":"17:00-17:30","promiseStartTime":"17:00","promiseEndTime":"17:30","steppedFreight":"2元运费","dingshida":true},{"promiseTimeText":"17:30-18:00","promiseStartTime":"17:30","promiseEndTime":"18:00","steppedFreight":"2元运费","dingshida":true},{"promiseTimeText":"18:00-18:30","promiseStartTime":"18:00","promiseEndTime":"18:30","steppedFreight":"2元运费","dingshida":true},{"promiseTimeText":"18:30-19:00","promiseStartTime":"18:30","promiseEndTime":"19:00","steppedFreight":"2元运费","dingshida":true},{"promiseTimeText":"19:00-19:30","promiseStartTime":"19:00","promiseEndTime":"19:30","steppedFreight":"2元运费","dingshida":true},{"promiseTimeText":"19:30-20:00","promiseStartTime":"19:30","promiseEndTime":"20:00","steppedFreight":"2元运费","dingshida":true},{"promiseTimeText":"20:00-20:30","promiseStartTime":"20:00","promiseEndTime":"20:30","steppedFreight":"2元运费","dingshida":true},{"promiseTimeText":"20:30-21:00","promiseStartTime":"20:30","promiseEndTime":"21:00","steppedFreight":"2元运费","dingshida":true}]
         */
        
        public List<Data> data;
        
        public static PromiseServerResp objectFromData(String str) {
            
            return new Gson().fromJson(str, PromiseServerResp.class);
        }
        
        public static PromiseServerResp objectFromData(String str, String key) {
            
            try {
                JSONObject jsonObject = new JSONObject(str);
                
                return new Gson().fromJson(jsonObject.getString(str), PromiseServerResp.class);
            } catch (JSONException e) {
                e.printStackTrace();
            }
            
            return null;
        }
        
        public static List<PromiseServerResp> arrayPromiseServerRespFromData(String str) {
            
            Type listType = new TypeToken<ArrayList<PromiseServerResp>>() {
            }.getType();
            
            return new Gson().fromJson(str, listType);
        }
        
        public static class Data {
            public String promiseDateText;
            public String promiseDate;
            public String promiseType;
            /**
             * promiseTimeText : 立即送达
             * promiseStartTime : 13:57
             * promiseEndTime : 13:57
             * steppedFreight : 2元运费
             * dingshida : false
             */
            
            public List<PromiseTimeRespItems> promiseTimeRespItems;
            
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
            
            public static class PromiseTimeRespItems {
                public String promiseTimeText;
                public String promiseStartTime;
                public String promiseEndTime;
                public String steppedFreight;
                public boolean dingshida;
                
                public static PromiseTimeRespItems objectFromData(String str) {
                    
                    return new Gson().fromJson(str, PromiseTimeRespItems.class);
                }
                
                public static PromiseTimeRespItems objectFromData(String str, String key) {
                    
                    try {
                        JSONObject jsonObject = new JSONObject(str);
                        
                        return new Gson().fromJson(jsonObject.getString(str), PromiseTimeRespItems.class);
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                    
                    return null;
                }
                
                public static List<PromiseTimeRespItems> arrayPromiseTimeRespItemsFromData(String str) {
                    
                    Type listType = new TypeToken<ArrayList<PromiseTimeRespItems>>() {
                    }.getType();
                    
                    return new Gson().fromJson(str, listType);
                }
            }
        }
    }
    
    public static class NoPushTime {
        public boolean pushTimeFlag;
        public String nonPushTimeText;
        
        public static NoPushTime objectFromData(String str) {
            
            return new Gson().fromJson(str, NoPushTime.class);
        }
        
        public static NoPushTime objectFromData(String str, String key) {
            
            try {
                JSONObject jsonObject = new JSONObject(str);
                
                return new Gson().fromJson(jsonObject.getString(str), NoPushTime.class);
            } catch (JSONException e) {
                e.printStackTrace();
            }
            
            return null;
        }
        
        public static List<NoPushTime> arrayNoPushTimeFromData(String str) {
            
            Type listType = new TypeToken<ArrayList<NoPushTime>>() {
            }.getType();
            
            return new Gson().fromJson(str, listType);
        }
    }
}
