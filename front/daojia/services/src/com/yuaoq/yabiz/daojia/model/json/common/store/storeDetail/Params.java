package com.yuaoq.yabiz.daojia.model.json.common.store.storeDetail;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import org.json.JSONException;
import org.json.JSONObject;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by changsy on 16/9/2.
 */
public class Params {
    
    /**
     * storeId : 10055645
     * orgCode : 74597
     */
    
    public Data data;
    /**
     * data : {"storeId":"10055645","orgCode":"74597"}
     * searchType : 2
     */
    
    public int searchType;
    
    public Params(Data data, int searchType) {
        this.data = data;
        this.searchType = searchType;
    }
    
    public Params(String storeId, String orgCode, int searchType) {
        this.data = new Data(storeId, orgCode);
        this.searchType = searchType;
    }
    
    public static Params objectFromData(String str) {
        
        return new Gson().fromJson(str, Params.class);
    }
    
    public static Params objectFromData(String str, String key) {
        
        try {
            JSONObject jsonObject = new JSONObject(str);
            
            return new Gson().fromJson(jsonObject.getString(str), Params.class);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        
        return null;
    }
    
    public static List<Params> arrayParamsFromData(String str) {
        
        Type listType = new TypeToken<ArrayList<Params>>() {
        }.getType();
        
        return new Gson().fromJson(str, listType);
    }
    
    public static String ObjtoStr(Params data) {
        return new Gson().toJson(data);
    }
    
    public static class Data {
        public String storeId;
        public String orgCode;
        
        public Data(String storeId, String orgCode) {
            this.storeId = storeId;
            this.orgCode = orgCode;
        }
        
        public static Data objectFromData(String str) {
            
            return new Gson().fromJson(str, Data.class);
        }
        
        public static Data objectFromData(String str, String key) {
            
            try {
                JSONObject jsonObject = new JSONObject(str);
                
                return new Gson().fromJson(jsonObject.getString(str), Data.class);
            } catch (JSONException e) {
                e.printStackTrace();
            }
            
            return null;
        }
        
        public static List<Data> arrayDataFromData(String str) {
            
            Type listType = new TypeToken<ArrayList<Data>>() {
            }.getType();
            
            return new Gson().fromJson(str, listType);
        }
    }
}
