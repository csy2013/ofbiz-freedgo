package com.yuaoq.yabiz.daojia.model.json.product.category.categorySearchByStore;

import com.yuaoq.yabiz.daojia.model.json.base.Tags;
import com.yuaoq.yabiz.daojia.model.json.common.store.ServiceTimes;

import java.util.List;

/**
 * Created by changsy on 16/9/7.
 */
public class StoreInfo {
    public double grade;
    public double starGrade;
    public String userActionStore;
    public String storePageType;
    public int carrierNo;
    public String desc;
    public String storeId;
    public String orgCode;
    public String openJPIndustry;
    public String storeName;
    public String templeType;
    public boolean showCart;
    public boolean showSearch;
    public String openJPIndustryName;
    public String phone;
    public String ferightDesc;
    public String sendThePriceDesc;
    public boolean showFeright;
    public boolean showMerchantDesc;
    public String logo;
    /**
     * name : 白条
     * iconText : 白条
     * type : 8
     * belongIndustry : 3
     * words : 该商家支持使用京东白条
     * activityId : null
     * activityRange : 0
     * colorCode : 19BAFF
     */
    
    public List<Tags> tag;
    /**
     * startTime : 08:00
     * endTime : 20:00
     */
    
    public List<ServiceTimes> serviceTimes;
    
    
}
