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
public class AccountModules {
    public boolean show;
    public String moduleKey;
    public String title;
    public String defaultText;
    public int type;
    /**
     * type : 1
     * status : 2
     * title :
     * desc :
     * addressVo : {"addressId":10771,"name":"常胜永","phone":"137****8361","addressName":"南京市浦口区旭日华庭比华利5栋1单元402","longitude":118.72889,"latitude":32.13012,"cityId":904}
     * bindPhoneVO : {"bindType":1,"bindNewPhone":"137****8361"}
     */
    
    public Object data;
    public String group;
    public boolean changeNum;
    
    public AccountModules(boolean show, String moduleKey, String title, String defaultText, int type, String group, boolean changeNum) {
        this.show = show;
        this.moduleKey = moduleKey;
        this.title = title;
        this.defaultText = defaultText;
        this.type = type;
        this.group = group;
        this.changeNum = changeNum;
    }
    
    public static AccountModules objectFromData(String str) {
        
        return new Gson().fromJson(str, AccountModules.class);
    }
    
    public static AccountModules objectFromData(String str, String key) {
        
        try {
            JSONObject jsonObject = new JSONObject(str);
            
            return new Gson().fromJson(jsonObject.getString(str), AccountModules.class);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        
        return null;
    }
    
    public static List<AccountModules> arrayModulesFromData(String str) {
        
        Type listType = new TypeToken<ArrayList<AccountModules>>() {
        }.getType();
        
        return new Gson().fromJson(str, listType);
    }
    
    public boolean isShow() {
        return show;
    }
    
    public void setShow(boolean show) {
        this.show = show;
    }
    
    public String getModuleKey() {
        return moduleKey;
    }
    
    public void setModuleKey(String moduleKey) {
        this.moduleKey = moduleKey;
    }
    
    public String getTitle() {
        return title;
    }
    
    public void setTitle(String title) {
        this.title = title;
    }
    
    public String getDefaultText() {
        return defaultText;
    }
    
    public void setDefaultText(String defaultText) {
        this.defaultText = defaultText;
    }
    
    public int getType() {
        return type;
    }
    
    public void setType(int type) {
        this.type = type;
    }
    
    public Object getData() {
        return data;
    }
    
    public void setData(Object data) {
        this.data = data;
    }
    
    public String getGroup() {
        return group;
    }
    
    public void setGroup(String group) {
        this.group = group;
    }
    
    public boolean isChangeNum() {
        return changeNum;
    }
    
    public void setChangeNum(boolean changeNum) {
        this.changeNum = changeNum;
    }
}
