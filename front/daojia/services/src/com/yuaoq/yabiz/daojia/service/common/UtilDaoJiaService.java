package com.yuaoq.yabiz.daojia.service.common;

import com.google.gson.Gson;
import com.yuaoq.yabiz.daojia.model.json.base.Tags;
import com.yuaoq.yabiz.daojia.model.json.common.IndexPage.*;
import com.yuaoq.yabiz.daojia.model.json.common.act.Act1;
import com.yuaoq.yabiz.daojia.model.json.common.act.Act1Data;
import com.yuaoq.yabiz.daojia.model.json.common.product5.*;
import com.yuaoq.yabiz.daojia.model.json.common.product7.Product7;
import com.yuaoq.yabiz.daojia.model.json.common.product7.Product7Data;
import com.yuaoq.yabiz.daojia.model.json.common.store.ServiceTimes;
import javolution.util.FastList;
import javolution.util.FastMap;
import javolution.util.FastSet;
import org.ofbiz.base.util.UtilMisc;
import org.ofbiz.base.util.UtilProperties;
import org.ofbiz.base.util.UtilValidate;
import org.ofbiz.entity.Delegator;
import org.ofbiz.entity.GenericEntityException;
import org.ofbiz.entity.GenericValue;
import org.ofbiz.entity.util.EntityUtil;
import org.ofbiz.product.store.ProductStoreWorker;
import org.ofbiz.service.GenericServiceException;
import org.ofbiz.service.LocalDispatcher;
import org.ofbiz.service.ServiceUtil;

import java.lang.reflect.InvocationTargetException;
import java.math.BigDecimal;
import java.util.*;

/**
 * Created by changsy on 2016/10/18.
 */
public class UtilDaoJiaService {
    
    public static Map getUserAction(int i, String imgUrl, String relateVal, String imgOrder, String relationTypeId, String floorStyle) {
        Map userActionMap = FastMap.newInstance();
        if (relationTypeId.equals("activityDetail")) {
            userActionMap.put("imgUrl", imgUrl);
            userActionMap.put("activityId", relateVal);
            userActionMap.put("floorStyle", floorStyle);
            userActionMap.put("index", i);
            userActionMap.put("imgOrder", imgOrder);
        } else if (relationTypeId.equals("channelPage")) {
            userActionMap.put("imgUrl", imgUrl);
            userActionMap.put("channelId", relateVal);
            userActionMap.put("floorStyle", floorStyle);
            userActionMap.put("index", i);
            userActionMap.put("imgOrder", imgOrder);
        } else if (relationTypeId.equals("storeList")) {
            userActionMap.put("venderIndustryType", relateVal);
        } else if (relationTypeId.equals("storeListByKey")) {
            userActionMap.put("imgUrl", imgUrl);
            userActionMap.put("industryLabel", "1");
            userActionMap.put("floorStyle", floorStyle);
            userActionMap.put("index", i);
            userActionMap.put("title", "");
            userActionMap.put("isHiddenInput", "true");
            userActionMap.put("keyWords", relateVal);
            userActionMap.put("promotLabels", "");
            userActionMap.put("discountRange", "");
            userActionMap.put("industryTags", "");
        } else if (relationTypeId.equals("store")) {
            userActionMap.put("imgUrl", imgUrl);
            userActionMap.put("orgCode", "");
            userActionMap.put("floorStyle", floorStyle);
            userActionMap.put("index", i);
            userActionMap.put("storeId", relateVal);
        } else if (relationTypeId.equals("productDetail")) {
            userActionMap.put("imgUrl", imgUrl);
            userActionMap.put("industryLabel", "1");
            userActionMap.put("floorStyle", floorStyle);
            userActionMap.put("index", i);
            userActionMap.put("orgCode", "");
            userActionMap.put("storeId", "");
            userActionMap.put("skuId", relateVal);
        } else if (relationTypeId.equals("web")) {
            userActionMap.put("imgUrl", imgUrl);
            userActionMap.put("industryLabel", "1");
            userActionMap.put("floorStyle", floorStyle);
            userActionMap.put("index", i);
            userActionMap.put("orgCode", "");
            userActionMap.put("shareId", "");
            userActionMap.put("url", relateVal);
        }
        return userActionMap;
    }
    
    public static Map getDataParams(int i, String imgUrl, String relateVal, String imgOrder, String relationTypeId, String floorStyle) {
        Map dataParams = FastMap.newInstance();
        if (relationTypeId.equals("activityDetail")) {
            
            dataParams.put("activityId", relateVal);
            dataParams.put("imgOrder", imgOrder);
            
        } else if (relationTypeId.equals("channelPage")) {
            dataParams.put("channelId", relateVal);
        } else if (relationTypeId.equals("storeList")) {
            
        } else if (relationTypeId.equals("storeListByKey")) {
            dataParams.put("imgUrl", imgUrl);
            String[] labels = new String[1];
            labels[0] = "1";
            dataParams.put("industryLabel", labels);
            dataParams.put("floorStyle", floorStyle);
            dataParams.put("index", i);
            dataParams.put("title", "");
            dataParams.put("isHiddenInput", "true");
            dataParams.put("keyWords", relateVal);
            dataParams.put("promotLabels", "");
            dataParams.put("discountRange", "");
            dataParams.put("industryTags", labels);
        } else if (relationTypeId.equals("store")) {
            dataParams.put("orgCode", "");
            dataParams.put("storeId", relateVal);
            dataParams.put("imgOrder", imgOrder);
        } else if (relationTypeId.equals("productDetail")) {
            
            dataParams.put("skuId", relateVal);
            dataParams.put("orgCode", "");
            dataParams.put("storeId", "10000");
            
        } else if (relationTypeId.equals("web")) {
            
            dataParams.put("shareId", "");
            dataParams.put("url", relateVal);
        }
        return dataParams;
    }
    
    public static void getBannerData(Delegator delegator, String baseUrl, List<Object> indexDatas, String advertId, String tplStyle, String defineType, Long seq) {
        List<GenericValue> banners = null;
        try {
            banners = delegator.findByAnd("AdvertAndContent", UtilMisc.toMap("advertTypeId", "banner", "advertId", advertId, "defineType", defineType), UtilMisc.toList("sequenceNum"));
            if (UtilValidate.isNotEmpty(banners)) {
                List<IndexSubData> subDataList = FastList.newInstance();
                for (int i = 0; i < banners.size(); i++) {
                    GenericValue advertContent = banners.get(i);
                    String imgUrl = advertContent.getString("imgSrc");
                    imgUrl = baseUrl + imgUrl;
                    String relateVal = (String) advertContent.get("relationId");
                    String imgOrder = advertContent.get("sequenceNum").toString();
                    String relationTypeId = advertContent.getString("relationTypeId");
                    String floorStyle = "banner";
                    Map uAMap = getUserAction(i, imgUrl, relateVal, imgOrder, relationTypeId, floorStyle);
                    Map actDataParams = getDataParams(i, imgUrl, relateVal, imgOrder, relationTypeId, floorStyle);
                    FloorCellData cellData = new FloorCellData(relationTypeId, actDataParams, imgUrl, new Gson().toJson(uAMap));
                    IndexSubData subData = new IndexSubData(cellData, "common");
                    subDataList.add(subData);
                }
                IndexData indexData = new IndexData("banner", 1, System.currentTimeMillis(), tplStyle, false, false, subDataList);
                indexDatas.add(indexData);
            }
        } catch (GenericEntityException e) {
            e.printStackTrace();
        }
    }
    
    
    public static void getBallData(Delegator delegator, String baseUrl, List<Object> indexDatas, String advertId, String tplStyle, String defineType, Long seq) {
        //获取ball
        List<GenericValue> balls = null;
        try {
            balls = delegator.findByAnd("AdvertAndContent", UtilMisc.toMap("advertTypeId", "ball", "advertId", advertId, "defineType", defineType), UtilMisc.toList("sequenceNum"));
            
            if (UtilValidate.isNotEmpty(balls)) {
                List<IndexSubData> subDataList = FastList.newInstance();
                for (int i = 0; i < balls.size(); i++) {
                    GenericValue advertContent = balls.get(i);
                    String imgUrl = advertContent.getString("imgSrc");
                    imgUrl = baseUrl + imgUrl;
                    String relateVal = (String) advertContent.get("relationId");
                    String relationTypeId = advertContent.getString("relationTypeId");
                    String imgOrder = advertContent.get("sequenceNum").toString();
                    String title = (String) advertContent.get("advertContentName");
                    String floorStyle = "ball";
                    Map uAMap = getUserAction(i, imgUrl, relateVal, imgOrder, relationTypeId, floorStyle);
                    Map actDataParams = getDataParams(i, imgUrl, relateVal, imgOrder, relationTypeId, floorStyle);
                    FloorCellData cellData = new FloorCellData(relationTypeId, actDataParams, imgUrl, new Gson().toJson(uAMap), title);
                    IndexSubData subData = new IndexSubData(cellData, "common");
                    subDataList.add(subData);
                }
                IndexData indexData = new IndexData("ball", 2, System.currentTimeMillis(), "tpl1", false, false, subDataList);
                indexDatas.add(indexData);
            }
        } catch (GenericEntityException e) {
            e.printStackTrace();
        }
    }
    
    public static void getFloorBannerData(Delegator delegator, String baseUrl, List<Object> indexDatas, String advertId, String tplStyle, String defineType, Long seq) {
        //获取floorBanner
        List<GenericValue> floorBanners = null;
        try {
            floorBanners = delegator.findByAnd("AdvertAndContent", UtilMisc.toMap("advertTypeId", "floorBanner", "advertId", advertId, "defineType", defineType), UtilMisc.toList("sequenceNum"));
            
            if (UtilValidate.isNotEmpty(floorBanners)) {
                List<IndexSubData> subDataList = FastList.newInstance();
                for (int i = 0; i < floorBanners.size(); i++) {
                    GenericValue advertContent = floorBanners.get(i);
                    String imgUrl = advertContent.getString("imgSrc");
                    imgUrl = baseUrl + imgUrl;
                    String relateVal = (String) advertContent.get("relationId");
                    String relationTypeId = advertContent.getString("relationTypeId");
                    String imgOrder = advertContent.get("sequenceNum").toString();
                    String title = (String) advertContent.get("advertContentName");
                    String floorStyle = "ball";
                    Map uAMap = getUserAction(i, imgUrl, relateVal, imgOrder, relationTypeId, floorStyle);
                    Map actDataParams = getDataParams(i, imgUrl, relateVal, imgOrder, relationTypeId, floorStyle);
                    FloorCellData cellData = new FloorCellData("activityDetail", actDataParams, imgUrl, new Gson().toJson(uAMap), title);
                    IndexSubData subData = new IndexSubData(cellData, "common");
                    subDataList.add(subData);
                }
                IndexData indexData = new IndexData("floorBanner", 2, System.currentTimeMillis(), "tpl1", true, false, subDataList);
                indexDatas.add(indexData);
            }
        } catch (GenericEntityException e) {
            e.printStackTrace();
        }
    }
    
    public static void getActData(Delegator delegator, String baseUrl, List<Object> indexDatas, String advertId, String tplStyle, String defineType, Long seq) {
        
        //获取act
        List<GenericValue> acts = null;
        try {
            acts = delegator.findByAnd("AdvertAndContent", UtilMisc.toMap("advertTypeId", "act", "advertId", advertId, "defineType", defineType), UtilMisc.toList("sequenceNum"));
            
            if (UtilValidate.isNotEmpty(acts)) {
                List<IndexSubData> subDataList = FastList.newInstance();
                for (int i = 0; i < acts.size(); i++) {
                    GenericValue advertContent = acts.get(i);
                    String imgUrl = advertContent.getString("imgSrc");
                    imgUrl = baseUrl + imgUrl;
                    String relateVal = (String) advertContent.get("relationId");
                    String relationTypeId = advertContent.getString("relationTypeId");
                    String imgOrder = advertContent.get("sequenceNum").toString();
                    String title = (String) advertContent.get("advertContentName");
                    String desc = (String) advertContent.get("description");
                    String floorStyle = "ball";
                    Map uAMap = getUserAction(i, imgUrl, relateVal, imgOrder, relationTypeId, floorStyle);
                    Map actDataParams = getDataParams(i, imgUrl, relateVal, imgOrder, relationTypeId, floorStyle);
                    FloorCellData cellData = new FloorCellData(relationTypeId, actDataParams, imgUrl, new Gson().toJson(uAMap), title, desc);
                    IndexSubData subData = new IndexSubData(cellData, "common");
                    subDataList.add(subData);
                }
                IndexData indexData = new IndexData("act" + acts.size(), 2, System.currentTimeMillis(), tplStyle, true, false, subDataList);
                indexDatas.add(indexData);
            }
        } catch (GenericEntityException e) {
            e.printStackTrace();
        }
    }
    
    public static void getAct1Data(Delegator delegator, String baseUrl, List<Object> indexDatas, String advertId, String tplStyle, String defineType, Long seq) {
        //获取act1
        
        List<GenericValue> act1s = null;
        try {
            act1s = delegator.findByAnd("AdvertAndContent", UtilMisc.toMap("advertTypeId", "act1", "advertId", advertId, "defineType", defineType), UtilMisc.toList("sequenceNum"));
            if (UtilValidate.isNotEmpty(act1s)) {
                List<IndexSubData> subDataList = FastList.newInstance();
                List<Act1Data> act1Datas = FastList.newInstance();
                for (int i = 0; i < act1s.size(); i++) {
                    GenericValue advertContent = act1s.get(i);
                    String imgUrl = advertContent.getString("imgSrc");
                    imgUrl = baseUrl + imgUrl;
                    String relateVal = (String) advertContent.get("relationId");
                    String relationTypeId = advertContent.getString("relationTypeId");
                    String imgOrder = advertContent.get("sequenceNum").toString();
                    String title = (String) advertContent.get("advertContentName");
                    String floorStyle = "ball";
                    Map act1DataParams = getDataParams(i, imgUrl, relateVal, imgOrder, relationTypeId, floorStyle);
                    Map uAMap = getUserAction(i, imgUrl, relateVal, imgOrder, relationTypeId, floorStyle);
                    Act1Data act1Data = new Act1Data(false, relationTypeId, imgUrl, act1DataParams, new Gson().toJson(uAMap), "", "");
                    act1Datas.add(act1Data);
                }
                Act1 act1 = new Act1("act1", null, false, tplStyle, seq, System.currentTimeMillis(), act1Datas);
                
                indexDatas.add(act1);
            }
        } catch (GenericEntityException e) {
            e.printStackTrace();
        }
    }
    
    public static void getAct2Data(Delegator delegator, String baseUrl, List<Object> indexDatas, String advertId, String tplStyle, String defineType, Long seq) {
        
        
        List<GenericValue> act2s = null;
        try {
            act2s = delegator.findByAnd("AdvertAndContent", UtilMisc.toMap("advertTypeId", "act2", "advertId", advertId, "defineType", defineType), UtilMisc.toList("sequenceNum"));
            
            
            if (UtilValidate.isNotEmpty(act2s)) {
                Map<Long, Map<Long, List<GenericValue>>> activityMap = new FastMap<>().newInstance();
                for (int i = 0; i < act2s.size(); i++) {
                    GenericValue actBanner = act2s.get(i);
                    Long groupNum = actBanner.getLong("sequenceNum");
                    Long subgroupNum = actBanner.getLong("subGroupNum");
                    if (activityMap.containsKey(groupNum)) {
                        Map<Long, List<GenericValue>> sActBannerMap = activityMap.get(groupNum);
                        if (sActBannerMap.containsKey(subgroupNum)) {
                            List<GenericValue> sActBannerList = sActBannerMap.get(subgroupNum);
                            sActBannerList.add(actBanner);
                        } else {
                            List<GenericValue> actBannerList = FastList.newInstance();
                            actBannerList.add(actBanner);
                            sActBannerMap.put(subgroupNum, actBannerList);
                        }
                    } else {
                        Map<Long, List<GenericValue>> subActMap = FastMap.newInstance();
                        List<GenericValue> subActList = FastList.newInstance();
                        subActList.add(actBanner);
                        subActMap.put(subgroupNum, subActList);
                        activityMap.put(groupNum, subActMap);
                    }
                }
                
                
                if (UtilValidate.isNotEmpty(activityMap)) {
                    List<IndexSubData> subDataList = FastList.newInstance();
                    Iterator keySet = activityMap.keySet().iterator();
                    while (keySet.hasNext()) {
                        Long groupNum = (Long) keySet.next();
                        Map<Long, List<GenericValue>> subActiveMap = activityMap.get(groupNum);
                        Iterator subKeyIter = subActiveMap.keySet().iterator();
                        int subsize = 0;
                        
                        while (subKeyIter.hasNext()) {
                            subsize++;
                            Long subGroupNum = (Long) subKeyIter.next();
                            List<GenericValue> advertContents = subActiveMap.get(subGroupNum);
                            if (UtilValidate.isNotEmpty(advertContents)) {
                                int size = advertContents.size();
                                //目前是判断size>1的情况为秒杀
                                if (size > 1) {
                                    List<MiaoShaList> miaoShaLists = FastList.newInstance();
                                    for (int i = 0; i < advertContents.size(); i++) {
                                        GenericValue content = advertContents.get(i);
                                        String name = content.getString("advertContentName");
                                        String description = content.getString("description");
                                        String imgUrl = content.getString("imgSrc");
                                        imgUrl = baseUrl + imgUrl;
                                        String relateVal = (String) content.get("relationId");
                                        String relationTypeId = content.getString("relationTypeId");
                                        String imgOrder = content.get("sequenceNum").toString();
                                        String title = (String) content.get("advertContentName");
                                        String floorStyle = "ball";
                                        //根据relationId查询秒杀信息。
                                        MiaoShaList miaoShaList = new MiaoShaList(name, "5.00", "1.90", imgUrl, "10000", "2002318200", description, 1, "75109");
                                        miaoShaLists.add(miaoShaList);
                                    }
                                    SecKillData secKillData = new SecKillData("抢购中", 60, 787, "距结束", 1, miaoShaLists);
                                    IndexSubData subData = new IndexSubData("miaosha", "2", secKillData);
                                    subDataList.add(subData);
                                } else {
                                    for (int i = 0; i < advertContents.size(); i++) {
                                        GenericValue advertContent = advertContents.get(i);
                                        String imgUrl = advertContent.getString("imgSrc");
                                        imgUrl = baseUrl + imgUrl;
                                        String relateVal = (String) advertContent.get("relationId");
                                        String imgOrder = advertContent.get("sequenceNum").toString();
                                        String title = (String) advertContent.get("advertContentName");
                                        String relationTypeId = advertContent.getString("relationTypeId");
                                        String desc = (String) advertContent.get("description");
                                        Map uAMap = getUserAction(i, imgUrl, relateVal, imgOrder, relationTypeId, tplStyle);
                                        Map actDataParams = getDataParams(i, imgUrl, relateVal, imgOrder, relationTypeId, "act2");
                                        FloorCellData cellData = new FloorCellData("activityDetail", actDataParams, imgUrl, new Gson().toJson(uAMap), title, desc);
                                        IndexSubData subData = new IndexSubData(cellData, "common");
                                        subDataList.add(subData);
                                    }
                                }
                            }
                        }
                    }
                    IndexData indexData = new IndexData("act2", 5, System.currentTimeMillis(), tplStyle, true, false, subDataList);
                    indexDatas.add(indexData);
                    
                }
            }
        } catch (GenericEntityException e) {
            e.printStackTrace();
        }
    }
    
    public static void getAct3Data(Delegator delegator, String baseUrl, List<Object> indexDatas, String advertId, String tplStyle, String defineType, Long seq) {
        List<GenericValue> act3s = null;
        try {
            act3s = delegator.findByAnd("AdvertAndContent", UtilMisc.toMap("advertTypeId", "act3", "advertId", advertId, "defineType", defineType), UtilMisc.toList("sequenceNum"));
            
            if (UtilValidate.isNotEmpty(act3s)) {
                Map<Long, Map<Long, List<GenericValue>>> activityMap = new FastMap<>().newInstance();
                for (int i = 0; i < act3s.size(); i++) {
                    GenericValue actBanner = act3s.get(i);
                    Long groupNum = actBanner.getLong("sequenceNum");
                    Long subgroupNum = actBanner.getLong("subGroupNum");
                    if (activityMap.containsKey(groupNum)) {
                        Map<Long, List<GenericValue>> sActBannerMap = activityMap.get(groupNum);
                        if (sActBannerMap.containsKey(subgroupNum)) {
                            List<GenericValue> sActBannerList = sActBannerMap.get(subgroupNum);
                            sActBannerList.add(actBanner);
                        } else {
                            List<GenericValue> actBannerList = FastList.newInstance();
                            actBannerList.add(actBanner);
                            sActBannerMap.put(subgroupNum, actBannerList);
                        }
                    } else {
                        Map<Long, List<GenericValue>> subActMap = FastMap.newInstance();
                        List<GenericValue> subActList = FastList.newInstance();
                        subActList.add(actBanner);
                        subActMap.put(subgroupNum, subActList);
                        activityMap.put(groupNum, subActMap);
                    }
                }
                
                if (UtilValidate.isNotEmpty(activityMap)) {
                    List<IndexSubData> subDataList = FastList.newInstance();
                    Iterator keySet = activityMap.keySet().iterator();
                    while (keySet.hasNext()) {
                        Long sequenceNum = (Long) keySet.next();
                        Map<Long, List<GenericValue>> subActiveMap = activityMap.get(sequenceNum);
                        int size = subActiveMap.size();
                        //目前是判断size>1的情况为秒杀
                        if (size > 1) {
                            List<MiaoShaList> miaoShaLists = FastList.newInstance();
                            Iterator keyIter = subActiveMap.keySet().iterator();
                            while (keyIter.hasNext()) {
                                GenericValue content = subActiveMap.get(keyIter.next()).get(0);
                                String name = content.getString("advertContentName");
                                String description = content.getString("description");
                                String imgUrl = content.getString("imgSrc");
                                imgUrl = baseUrl + imgUrl;
                                String relateVal = (String) content.get("relationId");
                                String relationTypeId = content.getString("relationTypeId");
                                String imgOrder = content.get("sequenceNum").toString();
                                String title = (String) content.get("advertContentName");
                                String floorStyle = "ball";
                                //根据relationId查询秒杀信息。
                                MiaoShaList miaoShaList = new MiaoShaList(name, "5.00", "1.90", imgUrl, "10000", "2002318200", description, 1, "75109");
                                miaoShaLists.add(miaoShaList);
                            }
                            SecKillData secKillData = new SecKillData("抢购中", 60, 787, "距结束", 1, miaoShaLists);
                            IndexSubData subData = new IndexSubData("miaosha", "2", secKillData);
                            subDataList.add(subData);
                        } else {
                            Iterator keyIter = subActiveMap.keySet().iterator();
                            int i = 1;
                            while (keyIter.hasNext()) {
                                GenericValue advertContent = subActiveMap.get(keyIter.next()).get(0);
                                String imgUrl = advertContent.getString("imgSrc");
                                imgUrl = baseUrl + imgUrl;
                                String relateVal = (String) advertContent.get("relationId");
                                String imgOrder = advertContent.get("sequenceNum").toString();
                                String title = (String) advertContent.get("advertContentName");
                                String relationTypeId = advertContent.getString("relationTypeId");
                                String desc = (String) advertContent.get("description");
                                Map uAMap = getUserAction(i, imgUrl, relateVal, imgOrder, relationTypeId, tplStyle);
                                Map actDataParams = getDataParams(i, imgUrl, relateVal, imgOrder, relationTypeId, "act3");
                                FloorCellData cellData = new FloorCellData(relationTypeId, actDataParams, imgUrl, new Gson().toJson(uAMap), title, desc);
                                IndexSubData subData = new IndexSubData(cellData, "common");
                                subDataList.add(subData);
                            }
                        }
                        
                        
                    }
                    IndexData indexData = new IndexData("act3", 5, System.currentTimeMillis(), tplStyle, true, false, subDataList);
                    indexDatas.add(indexData);
                }
            }
        } catch (GenericEntityException e) {
            e.printStackTrace();
        }
    }
    
    public static void getProduct5Data(Delegator delegator, String baseUrl, List<Object> indexDatas, String advertId, String tplStyle, String defineType, Long seq, Locale locale, LocalDispatcher dispatcher) {
        
        //获取product5
        List<GenericValue> product5 = null;
        try {
            product5 = delegator.findByAnd("AdvertAndContent", UtilMisc.toMap("advertTypeId", "product5", "advertId", advertId, "defineType", defineType), UtilMisc.toList("sequenceNum"));
            String advertName = "";
            if (UtilValidate.isNotEmpty(product5)) {
                List<Product5Data> subDataList = FastList.newInstance();
                for (int i = 0; i < product5.size(); i++) {
                    GenericValue advertContent = product5.get(i);
                    advertName = advertContent.getString("advertName");
                    String imgUrl = advertContent.getString("imgSrc");
                    imgUrl = baseUrl + imgUrl;
                    String relateVal = (String) advertContent.get("relationId");
                    String relationTypeId = advertContent.getString("relationTypeId");
                    String imgOrder = advertContent.get("sequenceNum").toString();
                    String title = (String) advertContent.get("advertContentName");
                    //获取productIds
                    String[] productIdArrs = relateVal.split(",");
                    Set productIds = UtilMisc.toSetArray(productIdArrs);
                    if (UtilValidate.isNotEmpty(productIdArrs)) {
                        
                        Map userActionMap = FastMap.newInstance();
                        String productId = productIdArrs[0];
                        GenericValue store = EntityUtil.getFirst(EntityUtil.filterByDate(delegator.findByAnd("ProductStoreProduct", UtilMisc.toMap("productId", productId))));
                        if (UtilValidate.isNotEmpty(store)) {
                            String storeId = store.getString("productStoreId");
                            Map<String, Object> storeResult = dispatcher.runSync("DaoJia_StoreInfo", UtilMisc.toMap("productStoreId", storeId));
                            String orgCode = "";
                            String facilityId = "";
                            String catalogId = "";
                            String storeName = "";
                            if (ServiceUtil.isSuccess(storeResult)) {
                                orgCode = (String) storeResult.get("orgCode");
                                facilityId = (String) ((GenericValue) storeResult.get("facility")).get("facilityId");
                                catalogId = (String) ((GenericValue) storeResult.get("catalog")).get("prodCatalogId");
                                store = (GenericValue) storeResult.get("productStore");
                                storeName = store.getString("storeName");
                                userActionMap.put("orgCode", orgCode);
                                userActionMap.put("storeId", storeId);
                            }
                            String userAction = new Gson().toJson(userActionMap);
                            subDataList = generateDaojiaSkuInfos(store.getString("productStoreId"), locale, dispatcher, orgCode, catalogId, productIds, facilityId, userAction, storeName);
                            
                        }
                    }
                }
                FloorTitle floorTitle = null;
                if (!advertName.equals("")) {
                    floorTitle = new FloorTitle(advertName, "");
                }
                BusyAttrMaps busyAttrMaps = new BusyAttrMaps();
                Product5 indexData = new Product5("product5", floorTitle, false, tplStyle, seq.intValue(), System.currentTimeMillis(), busyAttrMaps, subDataList);
                indexDatas.add(indexData);
            }
        } catch (GenericEntityException e) {
            e.printStackTrace();
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        } catch (GenericServiceException e) {
            e.printStackTrace();
        }
    }
    
    public static void getProduct7Data(Delegator delegator, String baseUrl, List<Object> indexDatas, String advertId, String tplStyle, String defineType, Long seq, Locale locale, LocalDispatcher dispatcher) {
        
        //获取product5
        List<GenericValue> product7 = null;
        try {
            product7 = delegator.findByAnd("AdvertAndContent", UtilMisc.toMap("advertTypeId", "product7", "advertId", advertId, "defineType", defineType), UtilMisc.toList("sequenceNum"));
            String advertName = "";
            if (UtilValidate.isNotEmpty(product7)) {
                List<Product7Data> subDataList = FastList.newInstance();
                for (int i = 0; i < product7.size(); i++) {
                    GenericValue advertContent = product7.get(i);
                    advertName = advertContent.getString("advertName");
                    String imgUrl = advertContent.getString("imgSrc");
                    imgUrl = baseUrl + imgUrl;
                    String relateVal = (String) advertContent.get("relationId");
                    String relationTypeId = advertContent.getString("relationTypeId");
                    String imgOrder = advertContent.get("sequenceNum").toString();
                    String title = (String) advertContent.get("advertContentName");
                    //获取productIds
                    String[] storeIdArrs = relateVal.split(",");
                    Map uAMap = getUserAction(i, imgUrl, relateVal, imgOrder, relationTypeId, tplStyle);
                    Map actDataParams = getDataParams(i, imgUrl, relateVal, imgOrder, relationTypeId, "product7");
                    
                    if (UtilValidate.isNotEmpty(storeIdArrs)) {
                        for (int j = 0; j < storeIdArrs.length; j++) {
                            String storeId = storeIdArrs[j];
                            Map userActionMap = FastMap.newInstance();
                            
                            Map<String, Object> storeResult = dispatcher.runSync("DaoJia_StoreInfo", UtilMisc.toMap("productStoreId", storeId));
                            String orgCode = "";
                            String facilityId = "";
                            String catalogId = "";
                            String storeName = "";
                            String logo = "";
                            String deliveryFirst = "";
                            String monthSaleNum = "";
                            List<ServiceTimes> serviceTimes = FastList.newInstance();
                            Product7Data product7Data = new Product7Data();
                            if (ServiceUtil.isSuccess(storeResult)) {
                                orgCode = (String) storeResult.get("orgCode");
                                facilityId = (String) ((GenericValue) storeResult.get("facility")).get("facilityId");
                                catalogId = (String) ((GenericValue) storeResult.get("catalog")).get("prodCatalogId");
                                GenericValue store = (GenericValue) storeResult.get("productStore");
                                serviceTimes = (List<ServiceTimes>) storeResult.get("servcieTimes");
                                storeName = store.getString("storeName");
                                logo = (String) storeResult.get("logo");
                                Double scoreAvg = new Double(store.getString("rate"));
                                userActionMap.put("orgCode", orgCode);
                                userActionMap.put("storeId", storeId);
                                deliveryFirst = store.getString("deliveryFirst");
                                monthSaleNum = store.getString("monthSaleNum");
                                
                                Double storeStar = 4.75d;
                                List<Tags> tags = FastList.newInstance();
                                
                                Map<String, Object> productPromos = dispatcher.runSync("DaoJia_StorePromos", UtilMisc.toMap("productStoreId", storeId));
                                if (ServiceUtil.isSuccess(productPromos)) {
                                    tags = (List<Tags>) productPromos.get("resultData");
                                }
                                //获取销售商品数
                                int inSaleNum = ProductStoreWorker.getProductStoreProducts(storeId, delegator).size();
                                //获取月销售量（从店铺表取，建议定时任务）
                                
                                String userAction = new Gson().toJson(userActionMap);
                                com.yuaoq.yabiz.daojia.model.json.common.product7.FloorCellData floorCellData = new com.yuaoq.yabiz.daojia.model.json.common.product7.FloorCellData(
                                        relationTypeId, actDataParams, "", logo, advertName, new Gson().toJson(uAMap), storeName, tags, serviceTimes, deliveryFirst, "", "综合超市", String.valueOf(inSaleNum), monthSaleNum,
                                        scoreAvg, storeStar, 2, "9966");
                                List<GenericValue> ppValue = delegator.findByAnd("ProductStoreProduct",UtilMisc.toMap("productStoreId",storeId));
                                Set<String> productIds = FastSet.newInstance();
                                if(UtilValidate.isNotEmpty(ppValue)){
                                    for (int k = 0; k < ppValue.size() && k<4; k++) {
                                        GenericValue pp = ppValue.get(k);
                                        productIds.add(pp.getString("productId"));
                                        
                                    }
                                }
                                
                                floorCellData = generateDaojiaProduct7SkuInfos(store.getString("productStoreId"), locale, dispatcher, orgCode, catalogId, productIds, facilityId, userAction, storeName, floorCellData);
                                product7Data.floorCellData = floorCellData;
                                product7Data.floorCellType = "common";
                                subDataList.add(product7Data);
                            }
                        }
                    }
                }
                FloorTitle floorTitle = null;
                if (!advertName.equals("")) {
                    floorTitle = new FloorTitle(advertName, "");
                }
                BusyAttrMaps busyAttrMaps = new BusyAttrMaps();
                Product7 indexData = new Product7("product7", floorTitle, false, tplStyle, seq.intValue(), System.currentTimeMillis(), false, subDataList);
                indexDatas.add(indexData);
            }
        } catch (GenericEntityException e) {
            e.printStackTrace();
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        } catch (GenericServiceException e) {
            e.printStackTrace();
        }
    }
    
    
    public static List<Product5Data> generateDaojiaSkuInfos(String storeId, Locale locale, LocalDispatcher dispatcher, String orgCode, String prodCatalogId, Set<String> productIds, String facilityId, String userAction, String storeName) throws GenericServiceException, IllegalAccessException, InvocationTargetException, NoSuchMethodException {
        Map<String, Object> prodSummaryResult = dispatcher.runSync("DaoJia_productSummary", UtilMisc.toMap("productIds", productIds, "webSiteId", "daojia", "prodCatalogId", prodCatalogId, "productStoreId", storeId));
        List<Map> productInfos = (List<Map>) prodSummaryResult.get("resultData");
        List<Product5Data> product5Datas = FastList.newInstance();
        java.text.DecimalFormat df = new java.text.DecimalFormat("0.00");
        if (UtilValidate.isNotEmpty(productInfos)) {
            for (int i = 0; i < productInfos.size(); i++) {
                
                SkuInfo product = new SkuInfo();
                Map<String, Object> map = productInfos.get(i);
                String outProductId = (String) map.get("productId");
                product.skuId = outProductId;
                product.skuName = (String) map.get("productName");
                String baseUrl = UtilProperties.getMessage("content.properties", "daojia.baseUrl", locale);
                product.imgUrl = baseUrl + map.get("smallImageUrl");
                product.storeId = storeId;
                product.orgCode = orgCode;
                product.incart = false;
                //获取有效库存
                Map<String, Object> resultOutput = dispatcher.runSync("getInventoryAvailableByFacility", UtilMisc.toMap("productId", outProductId, "facilityId", facilityId));
                BigDecimal quantityOnHandTotal = (BigDecimal) resultOutput.get("quantityOnHandTotal");
                product.stockCount = quantityOnHandTotal.intValue();
                if (product.stockCount == 0) {
                    product.stockCount = 99;
                }
                product.showCartButton = true;
                Map priceResultMap = (Map) map.get("priceResult");
                BigDecimal pieces = BigDecimal.ONE;
                int decimals = 2;
                product.basicPrice = df.format(((BigDecimal) priceResultMap.get("basePrice")).divide(pieces, decimals));
                
                product.realTimePrice = df.format(priceResultMap.get("basePrice"));
                product.mkPrice = "暂无报价";
                String userActionSku = "{\"sku_id\":" + outProductId + ",\"solution\":\"chengdu_divide\",\"store_id\":" + storeId + "}";
                product.userAction = userActionSku;
                
                product.promotion = 1;
                product.miaoshaInfo = new MiaoshaInfo("距恢复原价", "", 0, false);
                Tag tag = new Tag("限时抢", "秒杀", 9, 1, "限时抢购,限购3件", "FF5959");
                List<Tag> tags = FastList.newInstance();
                tags.add(tag);
                product.tags = tags;
                product.venderId = "";
                product.showCart = false;
                product.setNumInCart(0);
                product.setNoPrefixImgUrl((String) map.get("smallImageUrl"));
                Product5Data product5Data = new Product5Data("", userAction, "", storeId, storeName, "", product);
                product5Datas.add(product5Data);
                
            }
        }
        return product5Datas;
    }
    
    public static com.yuaoq.yabiz.daojia.model.json.common.product7.FloorCellData generateDaojiaProduct7SkuInfos(String storeId, Locale locale, LocalDispatcher dispatcher, String orgCode, String prodCatalogId, Set<String> productIds, String facilityId, String userAction, String storeName, com.yuaoq.yabiz.daojia.model.json.common.product7.FloorCellData floorCellData) throws GenericServiceException, IllegalAccessException, InvocationTargetException, NoSuchMethodException {
        Map<String, Object> prodSummaryResult = dispatcher.runSync("DaoJia_productSummary", UtilMisc.toMap("productIds", productIds, "webSiteId", "daojia", "prodCatalogId", prodCatalogId, "productStoreId", storeId));
        List<Map> productInfos = (List<Map>) prodSummaryResult.get("resultData");
        List<Product7Data> product7Datas = FastList.newInstance();
        com.yuaoq.yabiz.daojia.model.json.common.product7.FloorCellData floorData = new com.yuaoq.yabiz.daojia.model.json.common.product7.FloorCellData();
        java.text.DecimalFormat df = new java.text.DecimalFormat("0.00");
        List<SkuInfo> skuInfos = FastList.newInstance();
        if (UtilValidate.isNotEmpty(productInfos)) {
            for (int i = 0; i < productInfos.size(); i++) {
                
                SkuInfo product = new SkuInfo();
                Map<String, Object> map = productInfos.get(i);
                String outProductId = (String) map.get("productId");
                product.skuId = outProductId;
                product.skuName = (String) map.get("productName");
                String baseUrl = UtilProperties.getMessage("content.properties", "daojia.baseUrl", locale);
                product.imgUrl = baseUrl + map.get("smallImageUrl");
                product.storeId = storeId;
                product.orgCode = orgCode;
                product.incart = false;
                //获取有效库存
                Map<String, Object> resultOutput = dispatcher.runSync("getInventoryAvailableByFacility", UtilMisc.toMap("productId", outProductId, "facilityId", facilityId));
                BigDecimal quantityOnHandTotal = (BigDecimal) resultOutput.get("quantityOnHandTotal");
                product.stockCount = quantityOnHandTotal.intValue();
                if (product.stockCount == 0) {
                    product.stockCount = 99;
                }
                product.showCartButton = true;
                Map priceResultMap = (Map) map.get("priceResult");
                BigDecimal pieces = BigDecimal.ONE;
                int decimals = 2;
                product.basicPrice = df.format(((BigDecimal) priceResultMap.get("basePrice")).divide(pieces, decimals));
                
                product.realTimePrice = df.format(priceResultMap.get("basePrice"));
                product.mkPrice = "暂无报价";
                String userActionSku = "{\"sku_id\":" + outProductId + ",\"solution\":\"chengdu_divide\",\"store_id\":" + storeId + "}";
                product.userAction = userActionSku;
                
                product.promotion = 1;
                product.miaoshaInfo = new MiaoshaInfo("距恢复原价", "", 0, false);
                Tag tag = new Tag("限时抢", "秒杀", 9, 1, "限时抢购,限购3件", "FF5959");
                List<Tag> tags = FastList.newInstance();
                tags.add(tag);
                product.tags = tags;
                product.venderId = "";
                product.showCart = false;
                product.setNumInCart(0);
                product.setNoPrefixImgUrl((String) map.get("smallImageUrl"));
                skuInfos.add(product);
            }
            floorCellData.setSkus(skuInfos);
            
        }
        return floorCellData;
    }
}
