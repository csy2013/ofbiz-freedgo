package com.yuaoq.yabiz.daojia.model.json.party.address;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import org.json.JSONException;
import org.json.JSONObject;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by changsy on 16/9/15.
 */
public class Address {
    
    /**
     * id : 10771
     * addressName : 南京市浦口区旭日华庭比华利常胜永
     * name : 常胜永
     * cityId : 904
     * countyId : 50647
     * cityName : 南京市
     * countyName : 浦口区
     * poi : 旭日华庭比华利
     * addressDetail : 5栋1单元402
     * fullAddress : 南京市浦口区旭日华庭比华利5栋1单元402
     * mobile : 137****8361
     * coordType : 2
     * longitude : 118.72889
     * latitude : 32.13012
     * addressType : 1
     * pin : JD_284ubc3b41a4
     * lastUsedTime : 1471404879678
     * createPin : JD_284ubc3b41a4
     * yn : 0
     * quantityOfGoods : 0
     * exists : false
     * needCheckDelivery : 0
     * canDelivery : true
     */
    
    public int id;
    public String addressName;
    public String name;
    public String cityId;
    public String countyId;
    public String cityName;
    public String countyName;
    public String poi;
    public String addressDetail;
    public String fullAddress;
    public String mobile;
    public int coordType;
    public double longitude;
    public double latitude;
    public int addressType;
    public String pin;
    public long lastUsedTime;
    public String createPin;
    public int yn;
    public int quantityOfGoods;
    public boolean exists;
    public int needCheckDelivery;
    public boolean canDelivery;
    
    public Address(int id, String addressName, String name, String cityId, String countyId, String cityName, String countyName, String poi, String addressDetail, String fullAddress, String mobile, int coordType, double longitude, double latitude, int addressType, String pin, long lastUsedTime, String createPin, int yn, int quantityOfGoods, boolean exists, int needCheckDelivery, boolean canDelivery) {
        this.id = id;
        this.addressName = addressName;
        this.name = name;
        this.cityId = cityId;
        this.countyId = countyId;
        this.cityName = cityName;
        this.countyName = countyName;
        this.poi = poi;
        this.addressDetail = addressDetail;
        this.fullAddress = fullAddress;
        this.mobile = mobile;
        this.coordType = coordType;
        this.longitude = longitude;
        this.latitude = latitude;
        this.addressType = addressType;
        this.pin = pin;
        this.lastUsedTime = lastUsedTime;
        this.createPin = createPin;
        this.yn = yn;
        this.quantityOfGoods = quantityOfGoods;
        this.exists = exists;
        this.needCheckDelivery = needCheckDelivery;
        this.canDelivery = canDelivery;
    }

    public Address(int id, String addressName, String name, String cityId, String countyId, String cityName, String countyName, String poi, String addressDetail, String fullAddress, String mobile, long lastUsedTime) {
        this.id = id;
        this.addressName = addressName;
        this.name = name;
        this.cityId = cityId;
        this.countyId = countyId;
        this.cityName = cityName;
        this.countyName = countyName;
        this.poi = poi;
        this.addressDetail = addressDetail;
        this.fullAddress = fullAddress;
        this.mobile = mobile;
        this.coordType = 2;
        this.longitude = 118.72889;
        this.latitude = 32.13012;
        this.addressType = 1;
        this.pin = "JD_284ubc3b41a4";
        this.lastUsedTime = lastUsedTime;
        this.createPin = "JD_284ubc3b41a4";
        this.yn = 0;
        this.quantityOfGoods = 0;
        this.exists = false;
        this.needCheckDelivery = 0;
        this.canDelivery = true;
    }
    
    
    public static Address objectFromData(String str) {
        
        return new Gson().fromJson(str, Address.class);
    }
    
    public static Address objectFromData(String str, String key) {
        
        try {
            JSONObject jsonObject = new JSONObject(str);
            
            return new Gson().fromJson(jsonObject.getString(str), Address.class);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        
        return null;
    }
    
    public static List<Address> arrayAddressFromData(String str) {
        
        Type listType = new TypeToken<ArrayList<Address>>() {
        }.getType();
        
        return new Gson().fromJson(str, listType);
    }
}
