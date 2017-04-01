package com.yuaoq.yabiz.daojia.model.json.party.userInfo;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import org.json.JSONException;
import org.json.JSONObject;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by changsy on 16/9/13.
 */
public class UserInfoResult {
    
    public String jdPeasBalance;
    public String birthday;
    public String gendar;
    public String nickName;
    public String yunMidImageUrl;
    public String yunBigImageUrl;
    public String yunSmaImageUrl;
    public String mobile;
    
    public UserInfoResult(String jdPeasBalance, String birthday, String gendar, String nickName, String yunMidImageUrl, String yunBigImageUrl, String yunSmaImageUrl, String mobile) {
        this.jdPeasBalance = jdPeasBalance;
        this.birthday = birthday;
        this.gendar = gendar;
        this.nickName = nickName;
        this.yunMidImageUrl = yunMidImageUrl;
        this.yunBigImageUrl = yunBigImageUrl;
        this.yunSmaImageUrl = yunSmaImageUrl;
        this.mobile = mobile;
    }
    
    public static UserInfoResult objectFromData(String str) {

        return new Gson().fromJson(str, UserInfoResult.class);
    }
    
    public static UserInfoResult objectFromData(String str, String key) {

        try {
            JSONObject jsonObject = new JSONObject(str);

            return new Gson().fromJson(jsonObject.getString(str), UserInfoResult.class);
        } catch (JSONException e) {
            e.printStackTrace();
        }

        return null;
    }
    
    public static List<UserInfoResult> arrayUserInfoResultFromData(String str) {

        Type listType = new TypeToken<ArrayList<UserInfoResult>>() {
        }.getType();

        return new Gson().fromJson(str, listType);
    }
}
