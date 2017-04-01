package com.yuaoq.yabiz.daojia.model.json.common;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by changsy on 16/8/29.
 */
public class Address {
    
    
    /**
     * code : 0
     * msg : 成功
     * result : [{"title":"南京中医药大学(仙林校区)","address":"江苏省南京市栖霞区仙林大道138号","location":{"lat":32.10317,"lng":118.94482},"adcode":"320113","cityCode":904,"districtCode":3378,"city":"南京市","district":"栖霞区"},{"title":"中保绿苑","address":"江苏省南京市鼓楼区漓江路48","location":{"lat":32.05467,"lng":118.73817},"adcode":"320106","cityCode":904,"districtCode":3377,"city":"南京市","district":"鼓楼区"},{"title":"钟山晶典苑","address":"江苏省南京市栖霞区金马路19号","location":{"lat":32.06702,"lng":118.91109},"adcode":"320113","cityCode":904,"districtCode":3378,"city":"南京市","district":"栖霞区"},{"title":"中航·樾府","address":"江苏省南京市江宁区秣陵街道静淮街128号","location":{"lat":31.93062,"lng":118.79894},"adcode":"320115","cityCode":904,"districtCode":905,"city":"南京市","district":"江宁区"},{"title":"中国药科大学(江宁校区)","address":"江苏省南京市江宁区龙眠大道639号","location":{"lat":31.90052,"lng":118.91616},"adcode":"320115","cityCode":904,"districtCode":905,"city":"南京市","district":"江宁区"},{"title":"中山东路小区","address":"江苏省南京市秦淮区长白街634号附近","location":{"lat":32.03917,"lng":118.80004},"adcode":"320104","cityCode":904,"districtCode":3375,"city":"南京市","district":"秦淮区"},{"title":"中国南京徐庄软件园","address":"江苏省南京市玄武区玄武大道699号,宁镇公路","location":{"lat":32.0964,"lng":118.88324},"adcode":"320102","cityCode":904,"districtCode":3373,"city":"南京市","district":"玄武区"},{"title":"中冶天城","address":"江苏省南京市江宁区秣陵街道殷富街375号","location":{"lat":31.9123,"lng":118.84389},"adcode":"320115","cityCode":904,"districtCode":905,"city":"南京市","district":"江宁区"},{"title":"致和新村","address":"江苏省南京市秦淮区长白街83号附近","location":{"lat":32.02571,"lng":118.79247},"adcode":"320104","cityCode":904,"districtCode":3375,"city":"南京市","district":"秦淮区"},{"title":"中央路262号住宅小区","address":"江苏省南京市玄武区中央路262号","location":{"lat":32.0803,"lng":118.7855},"adcode":"320102","cityCode":904,"districtCode":3373,"city":"南京市","district":"玄武区"}]
     * success : true
     */
    
    private String code;
    private String msg;
    private boolean success;
    /**
     * title : 南京中医药大学(仙林校区)
     * address : 江苏省南京市栖霞区仙林大道138号
     * location : {"lat":32.10317,"lng":118.94482}
     * adcode : 320113
     * cityCode : 904
     * districtCode : 3378
     * city : 南京市
     * district : 栖霞区
     */
    
    private List<ResultObj> result;
    
    public Address(String code, String msg, boolean success, List<ResultObj> result) {
        this.code = code;
        this.msg = msg;
        this.success = success;
        this.result = result;
    }
    
    public static Address objectFromData(String str) {

        return new Gson().fromJson(str, Address.class);
    }
    
    public static List<Address> arrayAddressFromData(String str) {

        Type listType = new TypeToken<ArrayList<Address>>() {
        }.getType();

        return new Gson().fromJson(str, listType);
    }
    
    public String getCode() {
        return code;
    }
    
    public void setCode(String code) {
        this.code = code;
    }
    
    public String getMsg() {
        return msg;
    }
    
    public void setMsg(String msg) {
        this.msg = msg;
    }
    
    public boolean isSuccess() {
        return success;
    }
    
    public void setSuccess(boolean success) {
        this.success = success;
    }
    
    public List<ResultObj> getResult() {
        return result;
    }
    
    public void setResult(List<ResultObj> result) {
        this.result = result;
    }
    
    public static class ResultObj {
        private String title;
        private String address;
        /**
         * lat : 32.10317
         * lng : 118.94482
         */

        private LocationObj location;
        private String adcode;
        private int cityCode;
        private int districtCode;
        private String city;
        private String district;

        public static ResultObj objectFromData(String str) {

            return new Gson().fromJson(str, ResultObj.class);
        }

        public static List<ResultObj> arrayResultObjFromData(String str) {

            Type listType = new TypeToken<ArrayList<ResultObj>>() {
            }.getType();

            return new Gson().fromJson(str, listType);
        }

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }

        public String getAddress() {
            return address;
        }

        public void setAddress(String address) {
            this.address = address;
        }

        public LocationObj getLocation() {
            return location;
        }

        public void setLocation(LocationObj location) {
            this.location = location;
        }

        public String getAdcode() {
            return adcode;
        }

        public void setAdcode(String adcode) {
            this.adcode = adcode;
        }

        public int getCityCode() {
            return cityCode;
        }

        public void setCityCode(int cityCode) {
            this.cityCode = cityCode;
        }

        public int getDistrictCode() {
            return districtCode;
        }

        public void setDistrictCode(int districtCode) {
            this.districtCode = districtCode;
        }

        public String getCity() {
            return city;
        }

        public void setCity(String city) {
            this.city = city;
        }

        public String getDistrict() {
            return district;
        }

        public void setDistrict(String district) {
            this.district = district;
        }

        public static class LocationObj {
            private double lat;
            private double lng;

            public LocationObj(double lat, double lng) {
                this.lat = lat;
                this.lng = lng;
            }

            public static LocationObj objectFromData(String str) {

                return new Gson().fromJson(str, LocationObj.class);
            }

            public static List<LocationObj> arrayLocationObjFromData(String str) {

                Type listType = new TypeToken<ArrayList<LocationObj>>() {
                }.getType();

                return new Gson().fromJson(str, listType);
            }

            public double getLat() {
                return lat;
            }

            public void setLat(double lat) {
                this.lat = lat;
            }

            public double getLng() {
                return lng;
            }

            public void setLng(double lng) {
                this.lng = lng;
            }
        }
    }
}
