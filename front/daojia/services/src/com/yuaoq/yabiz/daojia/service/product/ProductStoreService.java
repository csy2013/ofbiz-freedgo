package com.yuaoq.yabiz.daojia.service.product;


import com.yuaoq.yabiz.daojia.model.json.base.Tags;
import com.yuaoq.yabiz.daojia.model.json.common.store.*;
import com.yuaoq.yabiz.daojia.model.json.common.store.storeDetail.*;
import com.yuaoq.yabiz.daojia.model.json.common.store.storeDetail.Params;
import com.yuaoq.yabiz.daojia.model.json.coupon.ActList;
import com.yuaoq.yabiz.daojia.model.json.coupon.StoreCoupon;
import com.yuaoq.yabiz.daojia.model.json.request.StoreDetailBody;
import javolution.util.FastList;
import javolution.util.FastMap;
import javolution.util.FastSet;
import org.ofbiz.base.util.*;
import org.ofbiz.content.data.OfbizUrlContentWrapper;
import org.ofbiz.entity.Delegator;
import org.ofbiz.entity.GenericEntityException;
import org.ofbiz.entity.GenericValue;
import org.ofbiz.entity.util.EntityUtil;
import org.ofbiz.party.contact.ContactMechWorker;
import org.ofbiz.product.catalog.CatalogWorker;
import org.ofbiz.product.store.ProductStoreWorker;
import org.ofbiz.service.DispatchContext;
import org.ofbiz.service.GenericServiceException;
import org.ofbiz.service.LocalDispatcher;
import org.ofbiz.service.ServiceUtil;

import java.io.File;
import java.io.IOException;
import java.math.BigDecimal;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Set;

/**
 * Created by changsy on 16/9/2.
 */
public class ProductStoreService {
    
    public static final String module = ProductStoreService.class.getName();
    public static final int DEFAULT_TX_TIMEOUT = 600;
    
    public static Map<String, Object> DaoJia_StoreInfo(DispatchContext dct, Map<String, ? extends Object> context) {
        Map<String, Object> result = ServiceUtil.returnSuccess();
        String productStoreId = (String) context.get("productStoreId");
        Delegator delegator = dct.getDelegator();
        Locale locale = (Locale) context.get("locale");
        LocalDispatcher dispatcher = dct.getDispatcher();
        try {
            GenericValue productStore = delegator.findByPrimaryKey("ProductStore", UtilMisc.toMap("productStoreId", productStoreId));
            result.put("productStore", productStore);
            List<Map<String, Object>> contactMechMap = ContactMechWorker.getProductStoreContactMechValueMaps(delegator, productStoreId, false, null);
            if (UtilValidate.isNotEmpty(contactMechMap)) {
                for (int i = 0; i < contactMechMap.size(); i++) {
                    Map<String, Object> contactMap = contactMechMap.get(i);
                    if (contactMap.containsKey("postalAddress")) {
                        GenericValue postalAddress = (GenericValue) contactMap.get("postalAddress");
                        String orgCode = (String) postalAddress.get("countyGeoId");
                        result.put("orgCode", orgCode);
                    }
                    if (contactMap.containsKey("telecomNumber")) {
                        GenericValue teleComNumber = (GenericValue) contactMap.get("telecomNumber");
                        String contactNumber = (String) teleComNumber.get("contactNumber");
                        result.put("contactNumber", contactNumber);
                    }
                }
            }
            result.put("contactMech", contactMechMap);
            GenericValue storeFacility = EntityUtil.getFirst(EntityUtil.filterByDate(delegator.findByAnd("ProductStoreFacility", UtilMisc.toMap("productStoreId", productStoreId))));
            result.put("facility", storeFacility);
            //根据store获取catalogId,对应的仓库
            GenericValue proStoreCatalog = EntityUtil.getFirst(EntityUtil.filterByDate(delegator.findByAnd("ProductStoreCatalog", UtilMisc.toMap("productStoreId", productStoreId))));
            result.put("catalog", proStoreCatalog);
            //获取营业时间
            List<GenericValue> serviceTimes = delegator.findByAnd("ProductStoreServiceTimes", UtilMisc.toMap("productStoreId", productStoreId));
            List<ServiceTimes> sts = FastList.newInstance();
            if (UtilValidate.isNotEmpty(serviceTimes)) {
                for (int i = 0; i < serviceTimes.size(); i++) {
                    GenericValue serviceTime = serviceTimes.get(i);
                    ServiceTimes times = new ServiceTimes(serviceTime.getString("saleStartTime"), serviceTime.getString("saleEndTime"));
                    sts.add(times);
                }
            }
            //获取店铺LOGO
            GenericValue storeContent = EntityUtil.getFirst(EntityUtil.filterByDate(delegator.findByAnd("ProductStoreContent", UtilMisc.toMap("productStoreId", productStoreId))));
            if (UtilValidate.isNotEmpty(storeContent)) {
                String contentId = storeContent.getString("contentId");
                String imgUrl = OfbizUrlContentWrapper.getOfbizUrlContentAsText(storeContent.getRelatedOne("Content"), locale, dispatcher);
                String baseUrl = UtilProperties.getMessage("content.properties", "daojia.baseUrl", locale);
                result.put("logo", baseUrl + imgUrl);
            }
            result.put("serviceTimes", sts);
        } catch (GenericEntityException e) {
            e.printStackTrace();
        }
        return result;
    }
    
    public static Map<String, Object> DaoJia_StoreDetailV220(DispatchContext dcx, Map<String, ? extends Object> context) {
        Map<String, Object> result = ServiceUtil.returnSuccess();
        
        String functionId = (String) context.get("functionId");
        String body = (String) context.get("body");
        String appVersion = (String) context.get("appVersion");
        String appName = (String) context.get("appName");
        String platCode = (String) context.get("platCode");
        String rand = (String) context.get("rand");
        Delegator delegator = dcx.getDelegator();
        LocalDispatcher localDispatcher = dcx.getDispatcher();
        Locale locale = (Locale) context.get("locale");
        StoreDetailBody detailBody = StoreDetailBody.objectFromData(body);
        String storeId = detailBody.storeId;
        String skuId = detailBody.skuId;
        StoreDetail storeDetail = new StoreDetail("store.storeDetailV220", "0", UtilProperties.getMessage("CommonUiLabels", "CommonSuccess", locale), true);
        //获取店铺信息
        try {
            //获取skuId对应的产品所属分类
            List<GenericValue> categoryMembers = delegator.findByAnd("ProductCategoryMember", UtilMisc.toMap("productId", skuId));
            List<String> categoryIds = FastList.newInstance();
            if (UtilValidate.isNotEmpty(categoryMembers)) {
                for (int i = 0; i < categoryMembers.size(); i++) {
                    GenericValue value = categoryMembers.get(i);
                    categoryIds.add(value.getString("productCategoryId"));
                }
            }
            
            GenericValue productStore = delegator.findByPrimaryKey("ProductStore", UtilMisc.toMap("productStoreId", storeId));
            String storeName = productStore.getString("storeName");
            
            //获取店铺的logoUrl
            String logoUrl = "";
            String baseUrl = UtilProperties.getMessage("content.properties", "daojia.baseUrl", locale);
            List<GenericValue> productContents = delegator.findByAnd("ProductStoreContent", UtilMisc.toMap("productStoreId", storeId, "productStoreContentTypeId", "LOGO"));
            if (UtilValidate.isNotEmpty(productContents)) {
                GenericValue productContent = productContents.get(0);
                String imgUrl = OfbizUrlContentWrapper.getOfbizUrlContentAsText(productContent.getRelatedOne("Content"), locale, localDispatcher);
                logoUrl = baseUrl + imgUrl;
            }
            //获取店铺归属地区
            GenericValue postalAddress = null;
            GenericValue telecomNumber = null;
            String orgCode = "";
            List<Map<String, Object>> contactMap = ContactMechWorker.getProductStoreContactMechValueMaps(delegator, storeId, false, null);
            if (UtilValidate.isNotEmpty(contactMap)) {
                for (int i = 0; i < contactMap.size(); i++) {
                    Map<String, Object> contact = contactMap.get(i);
                    if (contact.get("postalAddress") != null) {
                        postalAddress = (GenericValue) contact.get("postalAddress");
                        orgCode = postalAddress.getString("countyGeoId");
                    } else if (contact.get("telecomNumber") != null) {
                        telecomNumber = (GenericValue) contact.get("telecomNumber");
                    }
                }
            }
            com.yuaoq.yabiz.daojia.model.json.common.store.storeDetail.Params params = new com.yuaoq.yabiz.daojia.model.json.common.store.storeDetail.Params(storeId, orgCode, 2);
            Double scoreAvg = new Double(productStore.getString("rate"));
            //获取评分
            BigDecimal storeScoreAvg = ProductStoreWorker.getAverageProductStoreRating(productStore, null, storeId);
            if (!storeScoreAvg.equals(BigDecimal.ZERO)) {
                scoreAvg = storeScoreAvg.doubleValue();
            }
            //获取销售商品数
            int inSaleNum = ProductStoreWorker.getProductStoreProducts(storeId, delegator).size();
            //获取月销售量（从店铺表取，建议定时任务）
            String monthSaleNum = productStore.getString("monthSaleNum");
            //获取营业时间
            List<GenericValue> serviceTimes = delegator.findByAnd("ProductStoreServiceTimes", UtilMisc.toMap("productStoreId", storeId));
            List<ServiceTimes> sts = FastList.newInstance();
            if (UtilValidate.isNotEmpty(serviceTimes)) {
                for (int i = 0; i < serviceTimes.size(); i++) {
                    GenericValue serviceTime = serviceTimes.get(i);
                    ServiceTimes times = new ServiceTimes(serviceTime.getString("saleStartTime"), serviceTime.getString("saleEndTime"));
                    sts.add(times);
                }
            }
            //获取tags（促销信息）
            List<GenericValue> promos = EntityUtil.filterByDate(delegator.findByAnd("ProductStorePromoAndAppl", UtilMisc.toMap("productStoreId", storeId, "userEntered", "Y"), UtilMisc.toList("sequenceNum", "productPromoId")));
            List<Tags> tags = FastList.newInstance();
            String[] colors = new String[20];
            colors[0] = "5FBC65";
            colors[1] = "19BAFF";
            colors[2] = "19BAFF";
            colors[3] = "5FBC65";
            colors[4] = "19BAFF";
            colors[5] = "19BAFF";
            colors[6] = "5FBC65";
            colors[7] = "19BAFF";
            colors[8] = "5FBC65";
            colors[9] = "19BAFF";
            colors[10] = "5FBC65";
            colors[11] = "19BAFF";
            colors[12] = "19BAFF";
            colors[13] = "5FBC65";
            colors[14] = "19BAFF";
            colors[15] = "19BAFF";
            colors[16] = "5FBC65";
            colors[17] = "19BAFF";
            colors[18] = "5FBC65";
            colors[19] = "19BAFF";
            if (UtilValidate.isNotEmpty(promos)) {
                for (int i = 0; i < (promos.size()>2?2:promos.size()); i++) {
                    GenericValue promo = promos.get(i);
                    Tags ts = new Tags(promo.getString("promoName"), promo.getString("promoName"), 6, 1, promo.getString("promoName"), 1, colors[i]);
                    tags.add(ts);
                }
            }
            //expectArrivedTips
            ExpectArrivedTips arrivedTips = new ExpectArrivedTips(3, productStore.getString("subtitle"));
            List<ExpectArrivedTips> eats = FastList.newInstance();
            eats.add(arrivedTips);
            String contactNumber = "";
            String address = "";
            if (UtilValidate.isNotEmpty(postalAddress)) address = postalAddress.getString("addresss1");
            if (UtilValidate.isNotEmpty(telecomNumber)) contactNumber = telecomNumber.getString("contactNumber");
            
            StoreInfo storeInfo = new StoreInfo(storeName, logoUrl, logoUrl, 0, "", "productList", Params.ObjtoStr(params), storeId, orgCode, scoreAvg, 0, scoreAvg, String.valueOf(inSaleNum), monthSaleNum,
                    address, "1", "1", "1", false, 9966, contactNumber, false, sts, tags, eats);
            List coupons = FastList.newInstance();
            List storeBanner = FastList.newInstance();
            List<CateList> cateList = FastList.newInstance();
            //获取分类及下级分类
            getProductStoreCategories(storeId, delegator, cateList, categoryIds);
            StoreDetailResult detailResult = new StoreDetailResult("3", storeInfo, true, true, false, coupons, storeBanner, cateList);
            storeDetail.result = detailResult;
        } catch (GenericEntityException e) {
            e.printStackTrace();
            storeDetail.setCode("1");
            storeDetail.setMsg(e.getMessage());
            storeDetail.setSuccess(false);
        }
        
        
        result.put("resultData", storeDetail);
        
        return result;
    }
    
    public static Map<String, Object> DaoJia_RecommendStoreList(DispatchContext dispatchContext, Map<String, ? extends Object> context) {
        
        String functionId = (String) context.get("functionId");
        String body = (String) context.get("body");
        String platCode = (String) context.get("platCode");
        String appVersion = (String) context.get("appVersion");
        String appName = (String) context.get("appName");
        String rand = (String) context.get("rand");
        Locale locale = (Locale) context.get("locale");
        LocalDispatcher dispatcher = dispatchContext.getDispatcher();
        Delegator delegator = dispatchContext.getDelegator();
        
        RecommendStoreList recommendStoreList = new RecommendStoreList("zone.recommendStoreList", "0", UtilProperties.getMessage("CommonUiLabels", "CommonSuccess", locale), true, "");
        
        List<com.yuaoq.yabiz.daojia.model.json.common.store.UserAction> userActions = FastList.newInstance();
        com.yuaoq.yabiz.daojia.model.json.common.store.UserAction userAction = new com.yuaoq.yabiz.daojia.model.json.common.store.UserAction(1, 1, "10000");
        userActions.add(userAction);
        String userActionStr = userAction.ObjectsToStr(userActions);
        
        //storeConfig
        com.yuaoq.yabiz.daojia.model.json.common.store.StoreConfig storeConfig = new com.yuaoq.yabiz.daojia.model.json.common.store.StoreConfig(10, 68, true);
        Config config = new Config(userActionStr, storeConfig);
        
        
        //storeData
        List<StoreData> storeDatas = FastList.newInstance();
        
        //获取店铺信息列表
        try {
            List<GenericValue> productStores = delegator.findList("ProductStore", null, null, UtilMisc.toList("productStoreId"), null, false);
            if (UtilValidate.isNotEmpty(productStores)) {
                for (int i = 0; i < productStores.size(); i++) {
                    GenericValue productStore = productStores.get(i);
                    String orgCode = productStore.getString("productStoreId");
                    
                    Map<String, Object> storeResult = dispatcher.runSync("DaoJia_StoreInfo", UtilMisc.toMap("productStoreId", productStore.get("productStoreId")));
                    int imgOrder = i + 1;
                    String storeId = productStore.getString("productStoreId");
                    com.yuaoq.yabiz.daojia.model.json.common.store.Params params = new com.yuaoq.yabiz.daojia.model.json.common.store.Params(orgCode, imgOrder, storeId);
                    String words = productStore.getString("subtitle");
                    String name = productStore.getString("storeName");
                    //评分 先默认
                    String title = productStore.getString("rate");
                    String deliveryFirst = productStore.getString("deliveryFirst");
                    String storeType = productStore.get("storeTypeId", locale).toString();
                    String inSale = "932件商品";
                    String monthSale = "月售3857单";
//                    "socreAvg": 4.8, "storeStar": 4.75, "inCartNum": "0", "recommendType": 2, "recommendTypeName": "推荐",
                    double socreAvg = 4.8d;
                    double storeStar = 4.75d;
                    String inCartNum = "0";
                    int recommendTypes = 2;
                    String recommendTypeName = "推荐";
                    int carrierNo = 9966;
                    int closeStatus = 0;
                    String saleStartDate = productStore.getString("saleStartDate");
                    String saleEndDate = productStore.getString("saleEndDate");
                    List<ServiceTimes> serviceTimesList = (List<ServiceTimes>) storeResult.get("serviceTimes");
                    List<Tags> tags = FastList.newInstance();
                    
                    Map<String, Object> productPromos = dispatcher.runSync("DaoJia_StorePromos", UtilMisc.toMap("productStoreId", storeId));
                    if (ServiceUtil.isSuccess(productPromos)) {
                        tags = (List<Tags>) productPromos.get("resultData");
                    }
                    
                    //先取默认的skus.json
                    
                    String homePath = System.getProperty("ofbiz.home");
                    String json = null;
                    try {
                        json = FileUtil.readString("utf-8", new File(homePath + "/daojia/daojia/config/skus.json"));
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    List<Skus> skus = Skus.arraySkusFromData(json);
                    String imgUrl = "https://img30.360buyimg.com/vendersettle/jfs/t1963/102/1922378949/3615/5fe01608/56e626a2N3056b2fd.png";
                    com.yuaoq.yabiz.daojia.model.json.common.store.FloorCellData floorData = new com.yuaoq.yabiz.daojia.model.json.common.store.FloorCellData("store", params,
                            words, imgUrl, title, userActionStr, name, deliveryFirst, storeType, inSale, monthSale, socreAvg, storeStar, inCartNum, recommendTypes,
                            recommendTypeName, carrierNo, closeStatus, tags, serviceTimesList, skus
                    );
                    StoreData storeData = new StoreData(floorData, "commons");
                    storeDatas.add(storeData);
                }
            }
            
        } catch (GenericEntityException e) {
            e.printStackTrace();
        } catch (GenericServiceException e) {
            e.printStackTrace();
        }
        
        //recommend data
        Data data = new Data("product7", "tpl1", true, new FloorTitle("附近的店铺", ""), false, storeDatas);
        StoreResult storeResult = new StoreResult(config, data);
        AbTest abTest = new AbTest("10800000", "recommend_homepage", "recommend_homepage:commonRank:2016-08-31 08:03:52");
        recommendStoreList = new RecommendStoreList("zone.recommendStoreList", "0", UtilProperties.getMessage("CommonUiLabels", "CommonSuccess", locale), true, storeResult, abTest);
        Map<String, Object> result = ServiceUtil.returnSuccess();
        result.put("resultData", recommendStoreList);
        return result;
    }
    
    
    /**
     * 支持二级分类
     *
     * @param productStoreId
     * @param delegator
     * @param cateList
     * @return
     */
    public static void getProductStoreCategories(String productStoreId, Delegator delegator, List<CateList> cateList, List<String> categoryIds) {
        //找出storeId对应的目录
        Set<String> categorySet = FastSet.newInstance();
        List<GenericValue> storeCatalogs = CatalogWorker.getStoreCatalogs(delegator, productStoreId);
        if (UtilValidate.isNotEmpty(storeCatalogs)) {
            for (int i = 0; i < storeCatalogs.size(); i++) {
                GenericValue prodCatalog = storeCatalogs.get(i);
                //根据目录查找对应的category
                List<GenericValue> prodCategories = CatalogWorker.getProdCatalogCategories(delegator, prodCatalog.getString("prodCatalogId"), null);
                if (UtilValidate.isNotEmpty(prodCategories)) {
                    for (int j = 0; j < prodCategories.size(); j++) {
                        //productCatalogCategory
                        GenericValue prodCategory = prodCategories.get(j);
                        //挂在目录下的第一级分类，浏览根，促销，新品等。
                        getRelatedCategoriesRet(delegator, productStoreId, prodCategory.getString("productCategoryId"), true, true, cateList, categoryIds);
                        
                    }
                }
            }
        }
    }
    
    public static void getRelatedCategoriesRet(Delegator delegator, String productStoreId, String parentId, boolean limitView, boolean recursive, List<CateList> cateList, List<String> categoryIds) {
        
        if (Debug.verboseOn()) Debug.logVerbose("[CategoryWorker.getRelatedCategories] ParentID: " + parentId, module);
        
        List<GenericValue> rollups = null;
        
        try {
            rollups = delegator.findByAndCache("ProductCategoryRollup",
                    UtilMisc.toMap("parentProductCategoryId", parentId),
                    UtilMisc.toList("sequenceNum"));
            if (limitView) {
                rollups = EntityUtil.filterByDate(rollups, true);
            }
        } catch (GenericEntityException e) {
            Debug.logWarning(e.getMessage(), module);
        }
        if (rollups != null) {
            // Debug.logInfo("Rollup size: " + rollups.size(), module);
            int j = 0;
            for (GenericValue parent : rollups) {
                j++;
                // Debug.logInfo("Adding child of: " + parent.getString("parentProductCategoryId"), module);
                GenericValue category = null;
                String categoryId = null;
                try {
                    category = parent.getRelatedOneCache("CurrentProductCategory");
                    int productCounts = 0;
                    categoryId = category.getString("productCategoryId");
                    String cateName = category.getString("categoryName");
                    String userAction = "{\"catename1\":" + cateName + ",\"store_id\":" + productStoreId + "}";
                    List<CateList> childList = FastList.newInstance();
                    boolean openCategory = false;
                    if (categoryIds.contains(categoryId)) openCategory = true;
                    CateList cate = new CateList(categoryId, "0", category.getString("categoryName"), productCounts, j, 1, "", false, userAction, openCategory, childList);
                    cateList.add(cate);
                    if (recursive) {
                        getRelatedCategoriesRet(delegator, productStoreId, categoryId, limitView, recursive, cate, categoryIds);
                    }
                    
                } catch (GenericEntityException e) {
                    Debug.logWarning(e.getMessage(), module);
                }
                
            }
        }
    }
    
    public static void getRelatedCategoriesRet(Delegator delegator, String productStoreId, String parentId, boolean limitView, boolean recursive, CateList cate, List<String> categoryIds) {
        
        if (Debug.verboseOn()) Debug.logVerbose("[CategoryWorker.getRelatedCategories] ParentID: " + parentId, module);
        
        List<GenericValue> rollups = null;
        
        try {
            rollups = delegator.findByAndCache("ProductCategoryRollup",
                    UtilMisc.toMap("parentProductCategoryId", parentId),
                    UtilMisc.toList("sequenceNum"));
            if (limitView) {
                rollups = EntityUtil.filterByDate(rollups, true);
            }
        } catch (GenericEntityException e) {
            Debug.logWarning(e.getMessage(), module);
        }
        if (rollups != null) {
            // Debug.logInfo("Rollup size: " + rollups.size(), module);
            int j = 0;
            for (GenericValue parent : rollups) {
                j++;
                // Debug.logInfo("Adding child of: " + parent.getString("parentProductCategoryId"), module);
                GenericValue category = null;
                String categoryId = null;
                try {
                    category = parent.getRelatedOneCache("CurrentProductCategory");
                    int productCounts = 0;
                    categoryId = category.getString("productCategoryId");
                    String cateName = category.getString("categoryName");
                    String userAction = "{\"catename1\":" + cateName + ",\"store_id\":" + productStoreId + "}";
                    List<CateList> childList = FastList.newInstance();
                    boolean openCategory = false;
                    if (categoryIds.contains(categoryId)) openCategory = true;
                    CateList childCate = new CateList(categoryId, "0", category.getString("categoryName"), productCounts, j, 1, "", false, userAction, openCategory, childList);
                    cate.childCategoryList.add(childCate);
                    if (category != null) {
                        if (recursive) {
                            getRelatedCategoriesRet(delegator, productStoreId, categoryId, limitView, recursive, childCate, categoryIds);
                        }
                    }
                } catch (GenericEntityException e) {
                    Debug.logWarning(e.getMessage(), module);
                }
                
            }
        }
    }
    
    public static Map<String,Object> DaoJia_StoreCouponBanner(DispatchContext dcx,Map<String,? extends Object> context){
        String functionId = (String) context.get("functionId");
        String body = (String) context.get("body");
        String rand = (String) context.get("rand");
        String appVersion = (String) context.get("appVersion");
        String platCode = (String) context.get("platCode");
        String appName = (String) context.get("appName");
        Map<String,Object> retMap = FastMap.newInstance();
        retMap.put("id","store.couponBanner");
        retMap.put("code","16004");
        retMap.put("msg","网络繁忙，请稍后再试[1600405]");
        retMap.put("detail","CMS配置楼层为空");
        retMap.put("success",false);
        Map<String,Object>  result = ServiceUtil.returnSuccess();
        result.put("resultData",retMap);
        return result;
    }
    
    public static Map<String,Object> DaoJia_StoreHotCoupon(DispatchContext dcx,Map<String,? extends Object> context){
        String functionId = (String) context.get("functionId");
        String body = (String) context.get("body");
        String rand = (String) context.get("rand");
        String appVersion = (String) context.get("appVersion");
        String platCode = (String) context.get("platCode");
        String appName = (String) context.get("appName");
        Map<String,Object> retMap = FastMap.newInstance();
        retMap.put("id","share.getHotConpon");
        retMap.put("code","0");
        retMap.put("msg","成功");
        retMap.put("success",true);
        String homePath = System.getProperty("ofbiz.home");
        String json = null;
        try {
            json = FileUtil.readString("utf-8", new File(homePath + "/daojia/daojia/config/coupon/storeCoupon.json"));
        } catch (IOException e) {
            e.printStackTrace();
        }
        List<StoreCoupon> storeCoupons =  StoreCoupon.arrayStoreCouponFromData(json);
        retMap.put("result",storeCoupons);
        Map<String,Object>  result = ServiceUtil.returnSuccess();
        result.put("resultData",retMap);
        return result;
    }
    public static Map<String,Object> DaoJia_GetStoreCoupon(DispatchContext dcx,Map<String,? extends Object> context){
        String functionId = (String) context.get("functionId");
        String body = (String) context.get("body");
        String rand = (String) context.get("rand");
        String appVersion = (String) context.get("appVersion");
        String platCode = (String) context.get("platCode");
        String appName = (String) context.get("appName");
        Map<String,Object> retMap = FastMap.newInstance();
        retMap.put("id","share.getHotConpon");
        retMap.put("code","0");
        retMap.put("msg","成功");
        retMap.put("success",true);
        String homePath = System.getProperty("ofbiz.home");
        String json = null;
        try {
            json = FileUtil.readString("utf-8", new File(homePath + "/daojia/daojia/config/coupon/storeHotCoupon.json"));
        } catch (IOException e) {
            e.printStackTrace();
        }
        List<StoreCoupon> storeCoupons =  StoreCoupon.arrayStoreCouponFromData(json);
        retMap.put("result",storeCoupons);
        Map<String,Object>  result = ServiceUtil.returnSuccess();
        result.put("resultData",retMap);
        return result;
    }
    public static Map<String,Object> DaoJia_StoreActList(DispatchContext dcx,Map<String,? extends Object> context){
        String functionId = (String) context.get("functionId");
        String body = (String) context.get("body");
        String rand = (String) context.get("rand");
        String appVersion = (String) context.get("appVersion");
        String platCode = (String) context.get("platCode");
        String appName = (String) context.get("appName");
        Map<String,Object> retMap = FastMap.newInstance();
        retMap.put("id","store.storeActList");
        retMap.put("code","0");
        retMap.put("msg","成功");
        retMap.put("success",true);
        String homePath = System.getProperty("ofbiz.home");
        String json = null;
        try {
            json = FileUtil.readString("utf-8", new File(homePath + "/daojia/daojia/config/coupon/storeActList.json"));
        } catch (IOException e) {
            e.printStackTrace();
        }
        List<ActList> actLists =  ActList.arrayActListFromData(json);
        retMap.put("result",actLists);
        Map<String,Object>  result = ServiceUtil.returnSuccess();
        result.put("resultData",retMap);
        return result;
    }
    
}
