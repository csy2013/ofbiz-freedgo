package com.yuaoq.yabiz.daojia.model.json.order.orderComment;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import org.json.JSONException;
import org.json.JSONObject;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by changsy on 16/9/23.
 */
public class OrderComment {
    
    
    /**
     * orderId : WSOD10070
     * deliveryScore : 5
     * serviceScore : 5
     * skus : [{"skuId":"10002","score":5,"comment":"sss"},{"skuId":"10009","score":4,"comment":"aaa"}]
     */
    
    public String orderId;
    public int deliveryScore;
    public int serviceScore;
    /**
     * skuId : 10002
     * score : 5
     * comment : sss
     */
    
    public List<Skus> skus;
    
    public static OrderComment objectFromData(String str) {
        
        return new Gson().fromJson(str, OrderComment.class);
    }
    
    public static OrderComment objectFromData(String str, String key) {
        
        try {
            JSONObject jsonObject = new JSONObject(str);
            
            return new Gson().fromJson(jsonObject.getString(str), OrderComment.class);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        
        return null;
    }
    
    public static List<OrderComment> arrayOrderCommentFromData(String str) {
        
        Type listType = new TypeToken<ArrayList<OrderComment>>() {
        }.getType();
        
        return new Gson().fromJson(str, listType);
    }
    
    public String getOrderId() {
        return orderId;
    }
    
    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }
    
    public int getDeliveryScore() {
        return deliveryScore;
    }
    
    public void setDeliveryScore(int deliveryScore) {
        this.deliveryScore = deliveryScore;
    }
    
    public int getServiceScore() {
        return serviceScore;
    }
    
    public void setServiceScore(int serviceScore) {
        this.serviceScore = serviceScore;
    }
    
    public List<Skus> getSkus() {
        return skus;
    }
    
    public void setSkus(List<Skus> skus) {
        this.skus = skus;
    }
    
    public static class Skus {
        public String skuId;
        public int score;
        public String comment;
        
        public static Skus objectFromData(String str) {
            
            return new Gson().fromJson(str, Skus.class);
        }
        
        public static Skus objectFromData(String str, String key) {
            
            try {
                JSONObject jsonObject = new JSONObject(str);
                
                return new Gson().fromJson(jsonObject.getString(str), Skus.class);
            } catch (JSONException e) {
                e.printStackTrace();
            }
            
            return null;
        }
        
        public static List<Skus> arraySkusFromData(String str) {
            
            Type listType = new TypeToken<ArrayList<Skus>>() {
            }.getType();
            
            return new Gson().fromJson(str, listType);
        }
        
        public String getSkuId() {
            return skuId;
        }
        
        public void setSkuId(String skuId) {
            this.skuId = skuId;
        }
        
        public int getScore() {
            return score;
        }
        
        public void setScore(int score) {
            this.score = score;
        }
        
        public String getComment() {
            return comment;
        }
        
        public void setComment(String comment) {
            this.comment = comment;
        }
    }
}
