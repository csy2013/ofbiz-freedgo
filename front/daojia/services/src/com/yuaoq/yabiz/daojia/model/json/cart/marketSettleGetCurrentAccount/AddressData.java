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
public class AddressData {
    public String type;
    public String status;
    public String title;
    public String desc;
    /**
     * addressId : 10771
     * name : 常胜永
     * phone : 137****8361
     * addressName : 南京市浦口区旭日华庭比华利5栋1单元402
     * longitude : 118.72889
     * latitude : 32.13012
     * cityId : 904
     */
    
    public AddressVo addressVo;
    /**
     * bindType : 1
     * bindNewPhone : 137****8361
     */
    
    public BindPhoneVO bindPhoneVO;
    
    public static AddressData objectFromData(String str) {
        
        return new Gson().fromJson(str, AddressData.class);
    }
    
    public static AddressData objectFromData(String str, String key) {
        
        try {
            JSONObject jsonObject = new JSONObject(str);
            
            return new Gson().fromJson(jsonObject.getString(str), AddressData.class);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        
        return null;
    }
    
    public static List<AddressData> arrayDataFromData(String str) {
        
        Type listType = new TypeToken<ArrayList<AddressData>>() {
        }.getType();
        
        return new Gson().fromJson(str, listType);
    }
    

}