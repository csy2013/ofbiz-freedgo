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
public class AccountResult {
    public String unique;
    public String serverSign;
    public String totalMoney;
    public int freightFee;
    public int manJianMoney;
    public String settleType;
    public String totalDiscount;
    public String totalCost;
    public String totalWeight;
    public String distributionType;
    /**
     * show : true
     * moduleKey : receiptAddress
     * title : 收货地址
     * defaultText :
     * type : 0
     * data : {"type":"1","status":"2","title":"","desc":"","addressVo":{"addressId":10771,"name":"常胜永","phone":"137****8361","addressName":"南京市浦口区旭日华庭比华利5栋1单元402","longitude":118.72889,"latitude":32.13012,"cityId":904},"bindPhoneVO":{"bindType":1,"bindNewPhone":"137****8361"}}
     * group : 1
     * changeNum : false
     */
    
    public List<AccountModules> modules;
    
    public static AccountResult objectFromData(String str) {
        
        return new Gson().fromJson(str, AccountResult.class);
    }
    
    public static AccountResult objectFromData(String str, String key) {
        
        try {
            JSONObject jsonObject = new JSONObject(str);
            
            return new Gson().fromJson(jsonObject.getString(str), AccountResult.class);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        
        return null;
    }
    
    public static List<AccountResult> arrayAccountResultFromData(String str) {
        
        Type listType = new TypeToken<ArrayList<AccountResult>>() {
        }.getType();
        
        return new Gson().fromJson(str, listType);
    }
    
    public String getUnique() {
        return unique;
    }
    
    public void setUnique(String unique) {
        this.unique = unique;
    }
    
    public String getServerSign() {
        return serverSign;
    }
    
    public void setServerSign(String serverSign) {
        this.serverSign = serverSign;
    }
    
    public String getTotalMoney() {
        return totalMoney;
    }
    
    public void setTotalMoney(String totalMoney) {
        this.totalMoney = totalMoney;
    }
    
    public int getFreightFee() {
        return freightFee;
    }
    
    public void setFreightFee(int freightFee) {
        this.freightFee = freightFee;
    }
    
    public int getManJianMoney() {
        return manJianMoney;
    }
    
    public void setManJianMoney(int manJianMoney) {
        this.manJianMoney = manJianMoney;
    }
    
    public String getSettleType() {
        return settleType;
    }
    
    public void setSettleType(String settleType) {
        this.settleType = settleType;
    }
    
    public String getTotalDiscount() {
        return totalDiscount;
    }
    
    public void setTotalDiscount(String totalDiscount) {
        this.totalDiscount = totalDiscount;
    }
    
    public String getTotalCost() {
        return totalCost;
    }
    
    public void setTotalCost(String totalCost) {
        this.totalCost = totalCost;
    }
    
    public String getTotalWeight() {
        return totalWeight;
    }
    
    public void setTotalWeight(String totalWeight) {
        this.totalWeight = totalWeight;
    }
    
    public String getDistributionType() {
        return distributionType;
    }
    
    public void setDistributionType(String distributionType) {
        this.distributionType = distributionType;
    }
    
    public List<AccountModules> getModules() {
        return modules;
    }
    
    public void setModules(List<AccountModules> modules) {
        this.modules = modules;
    }
}
