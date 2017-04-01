package com.yuaoq.yabiz.daojia.model.json.common.logging;

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
public class LogInfo {
    
    
    /**
     * channel_name :
     * session_id : ff7d525009fd0dbb343fc87244d30c7b
     * device_id : H5_DEV_DC4CD118-2DE1-4416-BE78-331279313D8E
     * agent_ver :
     * log_data_type : site_click
     * user_id : 360buy_csy
     * channel_id : 999
     * city_name_select : 南京市
     * lat : 32.1341
     * lng : 118.722
     * poi : 泰达路/火炬南路(路口)
     * ref_url : https://daojia.jd.com/html/index.html?channel=weixin_gzh#index/LID:8
     * ref_page_name : #index
     * ref_keyword :
     * ref_page_id : 999
     * create_time : 2016-8-31 8:3:51.539
     * url : https://daojia.jd.com/html/index.html?channel=weixin_gzh#index/LID:8
     * keyword :
     * page_id : 999
     * page_name : #index
     * order_id :
     * url_page_par : {}
     * click_par : {"click_id":"home_store_list_load_20160714","userAction":[{"rankNo":1,"currentPage":1,"storeId":"10055645"},{"rankNo":2,"currentPage":1,"storeId":"10061753"},{"rankNo":3,"currentPage":1,"storeId":"10060816"},{"rankNo":4,"currentPage":1,"storeId":"10043201"},{"rankNo":5,"currentPage":1,"storeId":"10049627"},{"rankNo":6,"currentPage":1,"storeId":"10045716"},{"rankNo":7,"currentPage":1,"storeId":"11000223"},{"rankNo":8,"currentPage":1,"storeId":"10047018"},{"rankNo":9,"currentPage":1,"storeId":"11029239"},{"rankNo":10,"currentPage":1,"storeId":"11027324"}]}
     * cur_page_par : ?channel=weixin_gzh
     * click_name : home_store_list_load_20160714
     * click_no : 999
     */
    
    public String channel_name;
    public String session_id;
    public String device_id;
    public String agent_ver;
    public String log_data_type;
    public String user_id;
    public int channel_id;
    public String city_name_select;
    public double lat;
    public double lng;
    public String poi;
    public String ref_url;
    public String ref_page_name;
    public String ref_keyword;
    public int ref_page_id;
    public String create_time;
    public String url;
    public String keyword;
    public int page_id;
    public String page_name;
    public String order_id;
    /**
     * click_id : home_store_list_load_20160714
     * userAction : [{"rankNo":1,"currentPage":1,"storeId":"10055645"},{"rankNo":2,"currentPage":1,"storeId":"10061753"},{"rankNo":3,"currentPage":1,"storeId":"10060816"},{"rankNo":4,"currentPage":1,"storeId":"10043201"},{"rankNo":5,"currentPage":1,"storeId":"10049627"},{"rankNo":6,"currentPage":1,"storeId":"10045716"},{"rankNo":7,"currentPage":1,"storeId":"11000223"},{"rankNo":8,"currentPage":1,"storeId":"10047018"},{"rankNo":9,"currentPage":1,"storeId":"11029239"},{"rankNo":10,"currentPage":1,"storeId":"11027324"}]
     */
    
    public ClickPar click_par;
    public String cur_page_par;
    public String click_name;
    public int click_no;
    
    public static LogInfo objectFromData(String str) {
        
        return new Gson().fromJson(str, LogInfo.class);
    }
    
    public static LogInfo objectFromData(String str, String key) {
        
        try {
            JSONObject jsonObject = new JSONObject(str);
            
            return new Gson().fromJson(jsonObject.getString(str), LogInfo.class);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        
        return null;
    }
    
    public static List<LogInfo> arrayLogInfoFromData(String str) {
        
        Type listType = new TypeToken<ArrayList<LogInfo>>() {
        }.getType();
        
        return new Gson().fromJson(str, listType);
    }
    
    public static class ClickPar {
        public String click_id;
        /**
         * rankNo : 1
         * currentPage : 1
         * storeId : 10055645
         */
        
        public List<UserAction> userAction;
        
        public static ClickPar objectFromData(String str) {
            
            return new Gson().fromJson(str, ClickPar.class);
        }
        
        public static ClickPar objectFromData(String str, String key) {
            
            try {
                JSONObject jsonObject = new JSONObject(str);
                
                return new Gson().fromJson(jsonObject.getString(str), ClickPar.class);
            } catch (JSONException e) {
                e.printStackTrace();
            }
            
            return null;
        }
        
        public static List<ClickPar> arrayClickParFromData(String str) {
            
            Type listType = new TypeToken<ArrayList<ClickPar>>() {
            }.getType();
            
            return new Gson().fromJson(str, listType);
        }
        
        public static class UserAction {
            public int rankNo;
            public int currentPage;
            public String storeId;
            
            public static UserAction objectFromData(String str) {
                
                return new Gson().fromJson(str, UserAction.class);
            }
            
            public static UserAction objectFromData(String str, String key) {
                
                try {
                    JSONObject jsonObject = new JSONObject(str);
                    
                    return new Gson().fromJson(jsonObject.getString(str), UserAction.class);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                
                return null;
            }
            
            public static List<UserAction> arrayUserActionFromData(String str) {
                
                Type listType = new TypeToken<ArrayList<UserAction>>() {
                }.getType();
                
                return new Gson().fromJson(str, listType);
            }
        }
    }
}
