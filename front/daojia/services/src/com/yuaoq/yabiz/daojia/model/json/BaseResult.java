package com.yuaoq.yabiz.daojia.model.json;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by changsy on 16/8/28.
 */
public class BaseResult {
    
    
    /**
     * id : lauch.isflashscreen
     * code : 0
     * msg : 成功
     * result :
     * success : true
     */
    
    private String id;
    private String code;
    private String msg;
    private boolean success;
    
    
    public BaseResult(String id, String code, String msg, boolean success) {
        this.id = id;
        this.code = code;
        this.msg = msg;
        this.success = success;
    }
    
    public static BaseResult objectFromData(String str) {
        
        return new Gson().fromJson(str, BaseResult.class);
    }
    
    public static List<BaseResult> arrayResultFromData(String str) {
        
        Type listType = new TypeToken<ArrayList<BaseResult>>() {
        }.getType();
        
        return new Gson().fromJson(str, listType);
    }
    
    public String getId() {
        return id;
    }
    
    public void setId(String id) {
        this.id = id;
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
}
