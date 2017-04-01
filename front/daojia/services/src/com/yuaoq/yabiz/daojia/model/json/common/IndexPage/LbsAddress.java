package com.yuaoq.yabiz.daojia.model.json.common.IndexPage;

import com.google.gson.Gson;

/**
 * Created by changsy on 16/8/29.
 */
public class LbsAddress {
    
    
    /**
     * city : 南京市
     * longitude : 118.722
     * latitude : 32.1341
     * areaCode : 904
     * districtCode : 50647
     * address : 江苏省南京市浦口区柳洲北路
     * district : 浦口区
     * title : 泰达路/火炬南路(路口)
     * adcode : 320111
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
    
    public LbsAddress(String city, double longitude, double latitude, String areaCode, String districtCode, String address, String district, String title, String adcode) {
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
    
    public static LbsAddress objectFromData(String str) {
        
        return new Gson().fromJson(str, LbsAddress.class);
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
