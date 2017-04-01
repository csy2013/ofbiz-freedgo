package com.yuaoq.yabiz.daojia.model.json.order.orderstate;

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
public class OrderStateList implements java.lang.Comparable {
    
    
    /**
     * stateId : 80
     * stateTitle : 配送员已取货
     * stateDesc : 开始为您配送，配送员：裴泽强
     * stateTime : 08-13 10:10
     * functionNumber : 13952038890
     * stateIcon : 3
     * phoneNumType : 2
     * isShowStaticMap : 1
     */
    
    public Integer stateId;
    public String stateTitle;
    public String stateDesc;
    public String stateTime;
    public String functionNumber;
    public String stateIcon;
    public String phoneNumType;
    public int isShowStaticMap;
    
    public OrderStateList(Integer stateId, String stateTitle, String stateDesc, String stateTime, String stateIcon) {
        this.stateId = stateId;
        this.stateTitle = stateTitle;
        this.stateDesc = stateDesc;
        this.stateTime = stateTime;
        this.stateIcon = stateIcon;
    }
    
    public static OrderStateList objectFromData(String str) {
        
        return new Gson().fromJson(str, OrderStateList.class);
    }
    
    public static OrderStateList objectFromData(String str, String key) {
        
        try {
            JSONObject jsonObject = new JSONObject(str);
            
            return new Gson().fromJson(jsonObject.getString(str), OrderStateList.class);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        
        return null;
    }
    
    public static List<OrderStateList> arrayOrderStateListFromData(String str) {
        
        Type listType = new TypeToken<ArrayList<OrderStateList>>() {
        }.getType();
        
        return new Gson().fromJson(str, listType);
    }
    
    public Integer getStateId() {
        return stateId;
    }
    
    public void setStateId(Integer stateId) {
        this.stateId = stateId;
    }
    
    public String getStateTitle() {
        return stateTitle;
    }
    
    public void setStateTitle(String stateTitle) {
        this.stateTitle = stateTitle;
    }
    
    public String getStateDesc() {
        return stateDesc;
    }
    
    public void setStateDesc(String stateDesc) {
        this.stateDesc = stateDesc;
    }
    
    public String getStateTime() {
        return stateTime;
    }
    
    public void setStateTime(String stateTime) {
        this.stateTime = stateTime;
    }
    
    public String getFunctionNumber() {
        return functionNumber;
    }
    
    public void setFunctionNumber(String functionNumber) {
        this.functionNumber = functionNumber;
    }
    
    public String getStateIcon() {
        return stateIcon;
    }
    
    public void setStateIcon(String stateIcon) {
        this.stateIcon = stateIcon;
    }
    
    public String getPhoneNumType() {
        return phoneNumType;
    }
    
    public void setPhoneNumType(String phoneNumType) {
        this.phoneNumType = phoneNumType;
    }
    
    public int getIsShowStaticMap() {
        return isShowStaticMap;
    }
    
    public void setIsShowStaticMap(int isShowStaticMap) {
        this.isShowStaticMap = isShowStaticMap;
    }
    
    @Override
    public int compareTo(Object o) {
        if (o == null || this == null) {
            return 0;
        } else {
            OrderStateList obj1 = this;
            OrderStateList obj2 = (OrderStateList) o;
            return obj2.getStateId().compareTo(obj1.getStateId());
        }
    }
}
