package com.yuaoq.yabiz.daojia.model.json.common.IndexPage;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import org.json.JSONException;
import org.json.JSONObject;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by changsy on 16/8/30.
 */
public class SecKillData {
    
    
    /**
     * adwords : 抢购中
     * syntime : 60
     * timeRemain : 787
     * timeRemainName : 距结束
     * miaoShaSate : 1
     * miaoShaList : [{"name":"丝瓜 约500g/份","price":"4.80","miaoShaPrice":"0.10","imageurl":"https://img30.360buyimg.com/n2/jfs/t775/15/13451299/36284/24e40727/547d6752Nc8646b18.jpg","storeId":"10060549","skuId":"2001697084","stateName":"立即抢购","miaoShaSate":1,"orgCode":"73709"},{"name":"金针菇约250g/份","price":"2.50","miaoShaPrice":"1.70","imageurl":"https://img30.360buyimg.com/n2/jfs/t1081/94/116662165/315103/ec75e8cf/54ffdb66N0c4a7adf.jpg","storeId":"10060549","skuId":"2002161465","stateName":"立即抢购","miaoShaSate":1,"orgCode":"73709"},{"name":"精品西红柿约480g/份","price":"2.90","miaoShaPrice":"1.70","imageurl":"https://img30.360buyimg.com/n2/jfs/t2170/180/538280165/184884/d52abf31/5616407eN84d9f3af.jpg","storeId":"10060549","skuId":"2001156806","stateName":"立即抢购","miaoShaSate":1,"orgCode":"73709"}]
     */
    
    public String adwords;
    public int syntime;
    public int timeRemain;
    public String timeRemainName;
    public int miaoShaSate;
    /**
     * name : 丝瓜 约500g/份
     * price : 4.80
     * miaoShaPrice : 0.10
     * imageurl : https://img30.360buyimg.com/n2/jfs/t775/15/13451299/36284/24e40727/547d6752Nc8646b18.jpg
     * storeId : 10060549
     * skuId : 2001697084
     * stateName : 立即抢购
     * miaoShaSate : 1
     * orgCode : 73709
     */
    
    public List<MiaoShaList> miaoShaList;
    
    public SecKillData(String adwords, int syntime, int timeRemain, String timeRemainName, int miaoShaSate, List<MiaoShaList> miaoShaList) {
        this.adwords = adwords;
        this.syntime = syntime;
        this.timeRemain = timeRemain;
        this.timeRemainName = timeRemainName;
        this.miaoShaSate = miaoShaSate;
        this.miaoShaList = miaoShaList;
    }
    
    public static SecKillData objectFromData(String str) {
        
        return new Gson().fromJson(str, SecKillData.class);
    }
    
    public static SecKillData objectFromData(String str, String key) {
        
        try {
            JSONObject jsonObject = new JSONObject(str);
            
            return new Gson().fromJson(jsonObject.getString(str), SecKillData.class);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        
        return null;
    }
    
    public static List<SecKillData> arraySecKillDataFromData(String str) {
        
        Type listType = new TypeToken<ArrayList<SecKillData>>() {
        }.getType();
        
        return new Gson().fromJson(str, listType);
    }
    
    
}
