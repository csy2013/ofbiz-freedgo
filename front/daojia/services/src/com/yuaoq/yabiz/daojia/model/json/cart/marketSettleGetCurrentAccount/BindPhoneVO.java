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
public class BindPhoneVO {
    public int bindType;
    public String bindNewPhone;
    
    public BindPhoneVO(int bindType, String bindNewPhone) {
        this.bindType = bindType;
        this.bindNewPhone = bindNewPhone;
    }
    
    public static BindPhoneVO objectFromData(String str) {

        return new Gson().fromJson(str, BindPhoneVO.class);
    }
    
    public static BindPhoneVO objectFromData(String str, String key) {

        try {
            JSONObject jsonObject = new JSONObject(str);

            return new Gson().fromJson(jsonObject.getString(str), BindPhoneVO.class);
        } catch (JSONException e) {
            e.printStackTrace();
        }

        return null;
    }
    
    public static List<BindPhoneVO> arrayBindPhoneVOFromData(String str) {

        Type listType = new TypeToken<ArrayList<BindPhoneVO>>() {
        }.getType();

        return new Gson().fromJson(str, listType);
    }
}