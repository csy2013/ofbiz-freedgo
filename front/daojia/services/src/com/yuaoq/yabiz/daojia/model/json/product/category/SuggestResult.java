package com.yuaoq.yabiz.daojia.model.json.product.category;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import org.json.JSONException;
import org.json.JSONObject;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by changsy on 16/9/9.
 */
public class SuggestResult {
    public String solution;
    public Object onlinedebugId;
    /**
     * name : 鲜花
     * count : 0
     * type : 0
     */
    
    public List<ResultListVO> resultListVO;
    
    public List<ResultListVO> result;
    
    public static SuggestResult objectFromData(String str) {
        
        return new Gson().fromJson(str, SuggestResult.class);
    }
    
    public static SuggestResult objectFromData(String str, String key) {
        
        try {
            JSONObject jsonObject = new JSONObject(str);
            
            return new Gson().fromJson(jsonObject.getString(str), SuggestResult.class);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        
        return null;
    }
    
    public static List<SuggestResult> arraySuggestResultFromData(String str) {
        
        Type listType = new TypeToken<ArrayList<SuggestResult>>() {
        }.getType();
        
        return new Gson().fromJson(str, listType);
    }
    
    public String getSolution() {
        return solution;
    }
    
    public void setSolution(String solution) {
        this.solution = solution;
    }
    
    public Object getOnlinedebugId() {
        return onlinedebugId;
    }
    
    public void setOnlinedebugId(Object onlinedebugId) {
        this.onlinedebugId = onlinedebugId;
    }
    
    public List<ResultListVO> getResultListVO() {
        return resultListVO;
    }
    
    public void setResultListVO(List<ResultListVO> resultListVO) {
        this.resultListVO = resultListVO;
    }
    
    public List<ResultListVO> getResult() {
        return result;
    }
    
    public void setResult(List<ResultListVO> result) {
        this.result = result;
    }
}

