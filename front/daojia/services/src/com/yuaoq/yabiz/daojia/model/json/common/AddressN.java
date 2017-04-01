package com.yuaoq.yabiz.daojia.model.json.common;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import org.json.JSONException;
import org.json.JSONObject;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by changsy on 16/9/25.
 */
public class AddressN {
    
    /**
     * city : 南京市
     * longitude : 118.7969
     * latitude : 32.06282
     * areaCode : 904
     * districtCode : 3373
     * address : 江苏省南京市玄武区环湖路
     * district : 玄武区
     * title : 玄武湖公园-水上游览
     * adcode : 320102
     */
    
    public String city;
    public double longitude;
    public double latitude;
    public String areaCode;
    public String districtCode;
    public String address;
    public String district;
    public String title;
    public String adcode;
    
    public AddressN(String city, double longitude, double latitude, String areaCode, String districtCode, String address, String district, String title, String adcode) {
        this.city = city;
        this.longitude = longitude;
        this.latitude = latitude;
        this.areaCode = areaCode;
        this.districtCode = districtCode;
        this.address = address;
        this.district = district;
        this.title = title;
        this.adcode = adcode;
    }
    
    public static AddressN objectFromData(String str) {

        return new Gson().fromJson(str, AddressN.class);
    }
    
    public static AddressN objectFromData(String str, String key) {

        try {
            JSONObject jsonObject = new JSONObject(str);

            return new Gson().fromJson(jsonObject.getString(str), AddressN.class);
        } catch (JSONException e) {
            e.printStackTrace();
        }

        return null;
    }
    
    public static List<AddressN> arrayAddressNFromData(String str) {

        Type listType = new TypeToken<ArrayList<AddressN>>() {
        }.getType();

        return new Gson().fromJson(str, listType);
    }
    
    public String getCity() {
        return city;
    }
    
    public void setCity(String city) {
        this.city = city;
    }
    
    public double getLongitude() {
        return longitude;
    }
    
    public void setLongitude(double longitude) {
        this.longitude = longitude;
    }
    
    public double getLatitude() {
        return latitude;
    }
    
    public void setLatitude(double latitude) {
        this.latitude = latitude;
    }
    
    public String getAreaCode() {
        return areaCode;
    }
    
    public void setAreaCode(String areaCode) {
        this.areaCode = areaCode;
    }
    
    public String getDistrictCode() {
        return districtCode;
    }
    
    public void setDistrictCode(String districtCode) {
        this.districtCode = districtCode;
    }
    
    public String getAddress() {
        return address;
    }
    
    public void setAddress(String address) {
        this.address = address;
    }
    
    public String getDistrict() {
        return district;
    }
    
    public void setDistrict(String district) {
        this.district = district;
    }
    
    public String getTitle() {
        return title;
    }
    
    public void setTitle(String title) {
        this.title = title;
    }
    
    public String getAdcode() {
        return adcode;
    }
    
    public void setAdcode(String adcode) {
        this.adcode = adcode;
    }
}
