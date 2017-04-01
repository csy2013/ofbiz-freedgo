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
public class HotWords extends BaseResult {
    
    /**
     * id : hotWords.list
     * code : 0
     * msg : 成功
     * detail : null
     * result : {"solution":"chengdu_divide","count":37,"hotWordVOList":[{"hotWords":"扁豆","storeIds":[10060549],"typeIds":[0]},{"hotWords":"肉松饼","storeIds":[10055645],"typeIds":[0]},{"hotWords":"牛奶","storeIds":[10055645,10043201],"typeIds":[0,0]},{"hotWords":"调味料","storeIds":[10043201],"typeIds":[0]},{"hotWords":"韭菜","storeIds":[10055645],"typeIds":[0]},{"hotWords":"酸奶","storeIds":[10055645],"typeIds":[0]},{"hotWords":"苦菊","storeIds":[10060549],"typeIds":[0]},{"hotWords":"苏打饼干","storeIds":[10055645],"typeIds":[0]},{"hotWords":"上好佳","storeIds":[10055645],"typeIds":[0]},{"hotWords":"四季豆","storeIds":[10055645],"typeIds":[0]},{"hotWords":"巧克力","storeIds":[10055645],"typeIds":[0]},{"hotWords":"椒盐","storeIds":[10060816],"typeIds":[0]}],"cacheTime":600,"onlinedebugId":null}
     * success : true
     */
    
    public Object detail;
    /**
     * solution : chengdu_divide
     * count : 37
     * hotWordVOList : [{"hotWords":"扁豆","storeIds":[10060549],"typeIds":[0]},{"hotWords":"肉松饼","storeIds":[10055645],"typeIds":[0]},{"hotWords":"牛奶","storeIds":[10055645,10043201],"typeIds":[0,0]},{"hotWords":"调味料","storeIds":[10043201],"typeIds":[0]},{"hotWords":"韭菜","storeIds":[10055645],"typeIds":[0]},{"hotWords":"酸奶","storeIds":[10055645],"typeIds":[0]},{"hotWords":"苦菊","storeIds":[10060549],"typeIds":[0]},{"hotWords":"苏打饼干","storeIds":[10055645],"typeIds":[0]},{"hotWords":"上好佳","storeIds":[10055645],"typeIds":[0]},{"hotWords":"四季豆","storeIds":[10055645],"typeIds":[0]},{"hotWords":"巧克力","storeIds":[10055645],"typeIds":[0]},{"hotWords":"椒盐","storeIds":[10060816],"typeIds":[0]}]
     * cacheTime : 600
     * onlinedebugId : null
     */
    
    public HotWordsResult result;
    
    public HotWords(String id, String code, String msg, boolean success) {
        super(id, code, msg, success);
    }
    
    public static HotWords objectFromData(String str) {
        
        return new Gson().fromJson(str, HotWords.class);
    }
    
    public static HotWords objectFromData(String str, String key) {
        
        try {
            JSONObject jsonObject = new JSONObject(str);
            
            return new Gson().fromJson(jsonObject.getString(str), HotWords.class);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        
        return null;
    }
    
    public static List<HotWords> arrayHotWordsFromData(String str) {
        
        Type listType = new TypeToken<ArrayList<HotWords>>() {
        }.getType();
        
        return new Gson().fromJson(str, listType);
    }
    
    public Object getDetail() {
        return detail;
    }
    
    public void setDetail(Object detail) {
        this.detail = detail;
    }
    
    public HotWordsResult getResult() {
        return result;
    }
    
    public void setResult(HotWordsResult result) {
        this.result = result;
    }
}
