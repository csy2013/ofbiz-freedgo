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
public class AddressVo {
    public int addressId;
    public String name;
    public String phone;
    public String addressName;
    public double longitude;
    public double latitude;
    public int cityId;
    
    public AddressVo(int addressId, String name, String phone, String addressName, double longitude, double latitude, int cityId) {
        this.addressId = addressId;
        this.name = name;
        this.phone = phone;
        this.addressName = addressName;
        this.longitude = longitude;
        this.latitude = latitude;
        this.cityId = cityId;
    }
    
    public AddressVo(int addressId, String name, String phone, String addressName, int cityId) {
        this.addressId = addressId;
        this.name = name;
        this.phone = phone;
        this.addressName = addressName;

        this.cityId = cityId;
    }
    
    public static AddressVo objectFromData(String str) {

        return new Gson().fromJson(str, AddressVo.class);
    }
    
    public static AddressVo objectFromData(String str, String key) {

        try {
            JSONObject jsonObject = new JSONObject(str);

            return new Gson().fromJson(jsonObject.getString(str), AddressVo.class);
        } catch (JSONException e) {
            e.printStackTrace();
        }

        return null;
    }
    
    public static List<AddressVo> arrayAddressVoFromData(String str) {

        Type listType = new TypeToken<ArrayList<AddressVo>>() {
        }.getType();

        return new Gson().fromJson(str, listType);
    }
}
