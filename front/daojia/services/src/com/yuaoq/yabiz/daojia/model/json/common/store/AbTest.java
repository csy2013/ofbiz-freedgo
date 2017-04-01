package com.yuaoq.yabiz.daojia.model.json.common.store;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import org.json.JSONException;
import org.json.JSONObject;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by changsy on 16/8/31.
 */
public class AbTest {
    public String duration;
    public String experimentName;
    public String testTag;
    
    public AbTest(String duration, String experimentName, String testTag) {
        this.duration = duration;
        this.experimentName = experimentName;
        this.testTag = testTag;
    }
    
    public static AbTest objectFromData(String str) {
        
        return new Gson().fromJson(str, AbTest.class);
    }
    
    public static AbTest objectFromData(String str, String key) {
        
        try {
            JSONObject jsonObject = new JSONObject(str);
            
            return new Gson().fromJson(jsonObject.getString(str), AbTest.class);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        
        return null;
    }
    
    public static List<AbTest> arrayAbTestFromData(String str) {
        
        Type listType = new TypeToken<ArrayList<AbTest>>() {
        }.getType();
        
        return new Gson().fromJson(str, listType);
    }
}