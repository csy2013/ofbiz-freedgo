package com.yuaoq.yabiz.daojia.model.json.common.product7;

import com.yuaoq.yabiz.daojia.model.json.base.Tags;
import com.yuaoq.yabiz.daojia.model.json.common.product5.SkuInfo;
import com.yuaoq.yabiz.daojia.model.json.common.product5.Tag;
import com.yuaoq.yabiz.daojia.model.json.common.store.ServiceTimes;

import java.util.List;
import java.util.Map;

/**
 * Created by changsy on 2016/10/19.
 */
public class FloorCellData {
    
    public String to;
    public Map params;
    public String words;
    public String imgUrl;
    public String title;
    public String userAction;
    public String name;
    public List<Tags> tags;
    public List<ServiceTimes> serviceTimes;
    public String deliveryFirst;
    public String deliverySecond;
    public String storeType;
    public String inSale;
    public String monthSale;
    public Double socreAvg;
    public Double storeStar;
    public int recommendType;
    public List<SkuInfo> skus;
    public String carrierNo;
    
    public FloorCellData() {
    }
    
    public FloorCellData(String to, Map params, String words, String imgUrl, String title, String userAction, String name, List<Tags> tags, List<ServiceTimes> serviceTimes, String deliveryFirst, String deliverySecond, String storeType, String inSale, String monthSale, Double socreAvg, Double storeStar, int recommendType, String carrierNo) {
        this.to = to;
        this.params = params;
        this.words = words;
        this.imgUrl = imgUrl;
        this.title = title;
        this.userAction = userAction;
        this.name = name;
        this.tags = tags;
        this.serviceTimes = serviceTimes;
        this.deliveryFirst = deliveryFirst;
        this.deliverySecond = deliverySecond;
        this.storeType = storeType;
        this.inSale = inSale;
        this.monthSale = monthSale;
        this.socreAvg = socreAvg;
        this.storeStar = storeStar;
        this.recommendType = recommendType;
        this.carrierNo = carrierNo;
    }
    
    public FloorCellData(String to, Map params, String words, String imgUrl, String title, String userAction, String name, List<Tags> tags, List<ServiceTimes> serviceTimes, String deliveryFirst, String deliverySecond, String storeType, String inSale, String monthSale, Double socreAvg, Double storeStar, int recommendType, List<SkuInfo> skus, String carrierNo) {
        this.to = to;
        this.params = params;
        this.words = words;
        this.imgUrl = imgUrl;
        this.title = title;
        this.userAction = userAction;
        this.name = name;
        this.tags = tags;
        this.serviceTimes = serviceTimes;
        this.deliveryFirst = deliveryFirst;
        this.deliverySecond = deliverySecond;
        this.storeType = storeType;
        this.inSale = inSale;
        this.monthSale = monthSale;
        this.socreAvg = socreAvg;
        this.storeStar = storeStar;
        this.recommendType = recommendType;
        this.skus = skus;
        this.carrierNo = carrierNo;
    }
    
    public String getTo() {
        return to;
    }
    
    public void setTo(String to) {
        this.to = to;
    }
    
    public Map getParams() {
        return params;
    }
    
    public void setParams(Map params) {
        this.params = params;
    }
    
    public String getWords() {
        return words;
    }
    
    public void setWords(String words) {
        this.words = words;
    }
    
    public String getImgUrl() {
        return imgUrl;
    }
    
    public void setImgUrl(String imgUrl) {
        this.imgUrl = imgUrl;
    }
    
    public String getTitle() {
        return title;
    }
    
    public void setTitle(String title) {
        this.title = title;
    }
    
    public String getUserAction() {
        return userAction;
    }
    
    public void setUserAction(String userAction) {
        this.userAction = userAction;
    }
    
    public String getName() {
        return name;
    }
    
    public void setName(String name) {
        this.name = name;
    }
    
    public List<Tags> getTags() {
        return tags;
    }
    
    public void setTags(List<Tags> tags) {
        this.tags = tags;
    }
    
    public List<ServiceTimes> getServiceTimes() {
        return serviceTimes;
    }
    
    public void setServiceTimes(List<ServiceTimes> serviceTimes) {
        this.serviceTimes = serviceTimes;
    }
    
    public String getDeliveryFirst() {
        return deliveryFirst;
    }
    
    public void setDeliveryFirst(String deliveryFirst) {
        this.deliveryFirst = deliveryFirst;
    }
    
    public String getDeliverySecond() {
        return deliverySecond;
    }
    
    public void setDeliverySecond(String deliverySecond) {
        this.deliverySecond = deliverySecond;
    }
    
    public String getStoreType() {
        return storeType;
    }
    
    public void setStoreType(String storeType) {
        this.storeType = storeType;
    }
    
    public String getInSale() {
        return inSale;
    }
    
    public void setInSale(String inSale) {
        this.inSale = inSale;
    }
    
    public String getMonthSale() {
        return monthSale;
    }
    
    public void setMonthSale(String monthSale) {
        this.monthSale = monthSale;
    }
    
    public Double getSocreAvg() {
        return socreAvg;
    }
    
    public void setSocreAvg(Double socreAvg) {
        this.socreAvg = socreAvg;
    }
    
    public Double getStoreStar() {
        return storeStar;
    }
    
    public void setStoreStar(Double storeStar) {
        this.storeStar = storeStar;
    }
    
    public int getRecommendType() {
        return recommendType;
    }
    
    public void setRecommendType(int recommendType) {
        this.recommendType = recommendType;
    }
    
    public List<SkuInfo> getSkus() {
        return skus;
    }
    
    public void setSkus(List<SkuInfo> skus) {
        this.skus = skus;
    }
    
    public String getCarrierNo() {
        return carrierNo;
    }
    
    public void setCarrierNo(String carrierNo) {
        this.carrierNo = carrierNo;
    }
}
