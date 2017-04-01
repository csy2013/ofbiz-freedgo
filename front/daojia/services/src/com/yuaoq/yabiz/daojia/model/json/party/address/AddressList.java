package com.yuaoq.yabiz.daojia.model.json.party.address;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.yuaoq.yabiz.daojia.model.json.BaseResult;
import org.json.JSONException;
import org.json.JSONObject;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by changsy on 16/9/15.
 */
public class AddressList extends BaseResult {
    /**
     * id : 10771
     * addressName : 南京市浦口区旭日华庭比华利常胜永
     * name : 常胜永
     * cityId : 904
     * countyId : 50647
     * cityName : 南京市
     * countyName : 浦口区
     * poi : 旭日华庭比华利
     * addressDetail : 5栋1单元402
     * fullAddress : 南京市浦口区旭日华庭比华利5栋1单元402
     * mobile : 137****8361
     * coordType : 2
     * longitude : 118.72889
     * latitude : 32.13012
     * addressType : 1
     * pin : JD_284ubc3b41a4
     * lastUsedTime : 1471404879678
     * createPin : JD_284ubc3b41a4
     * yn : 0
     * quantityOfGoods : 0
     * exists : false
     * needCheckDelivery : 0
     * canDelivery : true
     */
    
    public List<Address> result;
    
    public AddressList(String id, String code, String msg, boolean success) {
        super(id, code, msg, success);
    }
    
    
    public static AddressList objectFromData(String str) {
        
        return new Gson().fromJson(str, AddressList.class);
    }
    
    public static AddressList objectFromData(String str, String key) {
        
        try {
            JSONObject jsonObject = new JSONObject(str);
            
            return new Gson().fromJson(jsonObject.getString(str), AddressList.class);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        
        return null;
    }
    
    public static List<AddressList> arrayAddressListFromData(String str) {
        
        Type listType = new TypeToken<ArrayList<AddressList>>() {
        }.getType();
        
        return new Gson().fromJson(str, listType);
    }
    

}
