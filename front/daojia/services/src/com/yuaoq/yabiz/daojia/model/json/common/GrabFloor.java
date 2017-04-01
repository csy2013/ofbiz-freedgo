package com.yuaoq.yabiz.daojia.model.json.common;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.yuaoq.yabiz.daojia.model.json.BaseResult;
import org.json.JSONException;
import org.json.JSONObject;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by changsy on 16/9/1.
 */
public class GrabFloor<T> extends BaseResult {
    
    /**
     * id : grab.grabFloor
     * code : 0
     * msg : 成功(0)
     * result : {"adwords":"抢购中","syntime":60,"timeRemain":8355,"timeRemainName":"距结束","miaoShaSate":1,"miaoShaList":[{"name":"精品生态鸡蛋-菜香天然 6枚装/盒","price":"5.00","miaoShaPrice":"1.90","imageurl":"https://img30.360buyimg.com/n2/jfs/t1759/209/1723603866/27637/1da654e3/560a63ffNa98eee99.jpg","storeId":"10060816","skuId":"2002318200","stateName":"立即抢购","miaoShaSate":1,"orgCode":"75109","storeName":"阳光菜园-丽岛路店"},{"name":"新鲜绿色 薄皮青椒 250g/份 菜香天然","price":"2.50","miaoShaPrice":"1.50","imageurl":"https://img30.360buyimg.com/n2/jfs/t2761/338/2074759874/63484/bd48bac6/57554814N80b0880c.jpg","storeId":"10060816","skuId":"2002441413","stateName":"立即抢购","miaoShaSate":1,"orgCode":"75109","storeName":"阳光菜园-丽岛路店"},{"name":"金针菇 约300g/份","price":"5.00","miaoShaPrice":"2.90","imageurl":"https://img30.360buyimg.com/n2/jfs/t1999/176/377321755/280234/3a57bf8/5604acd0N3801a837.jpg","storeId":"10060816","skuId":"2002137089","stateName":"立即抢购","miaoShaSate":1,"orgCode":"75109","storeName":"阳光菜园-丽岛路店"}]}
     * success : true
     */
    
    /**
     * adwords : 抢购中
     * syntime : 60
     * timeRemain : 8355
     * timeRemainName : 距结束
     * miaoShaSate : 1
     * miaoShaList : [{"name":"精品生态鸡蛋-菜香天然 6枚装/盒","price":"5.00","miaoShaPrice":"1.90","imageurl":"https://img30.360buyimg.com/n2/jfs/t1759/209/1723603866/27637/1da654e3/560a63ffNa98eee99.jpg","storeId":"10060816","skuId":"2002318200","stateName":"立即抢购","miaoShaSate":1,"orgCode":"75109","storeName":"阳光菜园-丽岛路店"},{"name":"新鲜绿色 薄皮青椒 250g/份 菜香天然","price":"2.50","miaoShaPrice":"1.50","imageurl":"https://img30.360buyimg.com/n2/jfs/t2761/338/2074759874/63484/bd48bac6/57554814N80b0880c.jpg","storeId":"10060816","skuId":"2002441413","stateName":"立即抢购","miaoShaSate":1,"orgCode":"75109","storeName":"阳光菜园-丽岛路店"},{"name":"金针菇 约300g/份","price":"5.00","miaoShaPrice":"2.90","imageurl":"https://img30.360buyimg.com/n2/jfs/t1999/176/377321755/280234/3a57bf8/5604acd0N3801a837.jpg","storeId":"10060816","skuId":"2002137089","stateName":"立即抢购","miaoShaSate":1,"orgCode":"75109","storeName":"阳光菜园-丽岛路店"}]
     */
    
    public T result;
    
    
    public GrabFloor(String id, String code, String msg, boolean success) {
        super(id, code, msg, success);
    }
    
    public GrabFloor(String id, String code, String msg, boolean success, T result) {
        super(id, code, msg, success);
        this.result = result;
    }
    
    public static GrabFloor objectFromData(String str) {
        
        return new Gson().fromJson(str, GrabFloor.class);
    }
    
    public static GrabFloor objectFromData(String str, String key) {
        
        try {
            JSONObject jsonObject = new JSONObject(str);
            
            return new Gson().fromJson(jsonObject.getString(str), GrabFloor.class);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        
        return null;
    }
    
    public static List<GrabFloor> arrayGrabFloorFromData(String str) {
        
        Type listType = new TypeToken<ArrayList<GrabFloor>>() {
        }.getType();
        
        return new Gson().fromJson(str, listType);
    }
    
    public T getResult() {
        return result;
    }
    
    public void setResult(T result) {
        this.result = result;
    }
}
