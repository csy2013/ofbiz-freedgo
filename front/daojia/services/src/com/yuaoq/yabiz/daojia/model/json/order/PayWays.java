package com.yuaoq.yabiz.daojia.model.json.order;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import org.json.JSONException;
import org.json.JSONObject;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by changsy on 16/9/19.
 */
public class PayWays {
    public String payWay;
    public String payName;
    public String status;
    public String defaultCopy;
    public String iconUrl;
    
    public static PayWays objectFromData(String str) {
        
        return new Gson().fromJson(str, PayWays.class);
    }
    
    public static PayWays objectFromData(String str, String key) {
        
        try {
            JSONObject jsonObject = new JSONObject(str);
            
            return new Gson().fromJson(jsonObject.getString(str), PayWays.class);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        
        return null;
    }
    
    public static List<PayWays> arrayPayWaysFromData(String str) {
        
        Type listType = new TypeToken<ArrayList<PayWays>>() {
        }.getType();
        
        return new Gson().fromJson(str, listType);
    }
    
    public String getPayWay() {
        return payWay;
    }
    
    public void setPayWay(String payWay) {
        this.payWay = payWay;
    }
    
    public String getPayName() {
        return payName;
    }
    
    public void setPayName(String payName) {
        this.payName = payName;
    }
    
    public String getStatus() {
        return status;
    }
    
    public void setStatus(String status) {
        this.status = status;
    }
    
    public String getDefaultCopy() {
        return defaultCopy;
    }
    
    public void setDefaultCopy(String defaultCopy) {
        this.defaultCopy = defaultCopy;
    }
    
    public String getIconUrl() {
        return iconUrl;
    }
    
    public void setIconUrl(String iconUrl) {
        this.iconUrl = iconUrl;
    }
}