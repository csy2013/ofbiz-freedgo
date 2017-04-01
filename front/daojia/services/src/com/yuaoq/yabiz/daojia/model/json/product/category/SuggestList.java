package com.yuaoq.yabiz.daojia.model.json.product.category;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.yuaoq.yabiz.daojia.model.json.BaseResult;
import org.json.JSONException;
import org.json.JSONObject;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by changsy on 16/9/9.
 */
public class SuggestList extends BaseResult {
    
    
    /**
     * id : suggest.list
     * code : 0
     * msg : 成功
     * detail : null
     * result : {"solution":"chengdu_divide","resultListVO":[{"name":"鲜花","count":"0","type":0},{"name":"鲜花玫瑰","count":"0","type":0}],"onlinedebugId":null,"result":[{"name":"鲜花","count":"0","type":0},{"name":"鲜花玫瑰","count":"0","type":0}]}
     * success : true
     */
    
    public Object detail;
    /**
     * solution : chengdu_divide
     * resultListVO : [{"name":"鲜花","count":"0","type":0},{"name":"鲜花玫瑰","count":"0","type":0}]
     * onlinedebugId : null
     * result : [{"name":"鲜花","count":"0","type":0},{"name":"鲜花玫瑰","count":"0","type":0}]
     */
    
    public SuggestResult result;
    
    public SuggestList(String id, String code, String msg, boolean success) {
        super(id, code, msg, success);
    }
    
    public static SuggestList objectFromData(String str) {
        
        return new Gson().fromJson(str, SuggestList.class);
    }
    
    public static SuggestList objectFromData(String str, String key) {
        
        try {
            JSONObject jsonObject = new JSONObject(str);
            
            return new Gson().fromJson(jsonObject.getString(str), SuggestList.class);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        
        return null;
    }
    
    public static List<SuggestList> arraySuggestListFromData(String str) {
        
        Type listType = new TypeToken<ArrayList<SuggestList>>() {
        }.getType();
        
        return new Gson().fromJson(str, listType);
    }
    
    public Object getDetail() {
        return detail;
    }
    
    public void setDetail(Object detail) {
        this.detail = detail;
    }
    
    public SuggestResult getResult() {
        return result;
    }
    
    public void setResult(SuggestResult result) {
        this.result = result;
    }
}
