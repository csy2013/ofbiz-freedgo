package com.yuaoq.yabiz.daojia.service.product;

import com.yuaoq.yabiz.daojia.model.json.base.Tags;
import com.yuaoq.yabiz.daojia.model.json.common.store.ServiceTimes;
import com.yuaoq.yabiz.daojia.model.json.product.ProductInfo;
import com.yuaoq.yabiz.daojia.model.json.product.category.*;
import com.yuaoq.yabiz.daojia.model.json.product.category.categorySearchByStore.SearchBody;
import com.yuaoq.yabiz.daojia.model.json.product.category.categorySearchByStore.SearchByStorePost;
import com.yuaoq.yabiz.daojia.model.json.product.category.categorySearchByStore.StoreInfo;
import com.yuaoq.yabiz.daojia.model.json.product.category.categorySearchByStore.StoreSkuList;
import javolution.util.FastList;
import javolution.util.FastMap;
import javolution.util.FastSet;
import org.apache.commons.beanutils.PropertyUtils;
import org.ofbiz.base.util.*;
import org.ofbiz.entity.Delegator;
import org.ofbiz.entity.GenericEntityException;
import org.ofbiz.entity.GenericValue;
import org.ofbiz.entity.condition.EntityCondition;
import org.ofbiz.entity.condition.EntityOperator;
import org.ofbiz.entity.util.EntityUtil;
import org.ofbiz.service.DispatchContext;
import org.ofbiz.service.GenericServiceException;
import org.ofbiz.service.LocalDispatcher;
import org.ofbiz.service.ServiceUtil;

import java.io.File;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.util.*;

/**
 * Created by changsy on 16/9/6.
 */
public class ProductCategoryService {
    public static final String module = ProductCategoryService.class.getName();
    public static final int DEFAULT_TX_TIMEOUT = 600;
    public static String homePath = System.getProperty("ofbiz.home");
    
    
    public static Map<String, Object> DaoJia_TabCateList(DispatchContext dcx, Map<String, ? extends Object> context) {
        Map<String, Object> result = ServiceUtil.returnSuccess();
        String functionId = (String) context.get("functionId");
        String body = (String) context.get("body");
        String appVersion = (String) context.get("appVersion");
        String appName = (String) context.get("appName");
        String platCode = (String) context.get("platCode");
        String rand = (String) context.get("rand");
        Locale locale = (Locale) context.get("locale");
        Delegator delegator = dcx.getDelegator();
        CategoryList categoryList = new CategoryList("homeSearch.tabCateList", "0", UtilProperties.getMessage("CommonUiLabels", "CommonSuccess", locale), true);
        LocalDispatcher dispatcher = dcx.getDispatcher();
        //根据catalog获取对应的分类根
        String homePath = System.getProperty("ofbiz.home");
        try {
            String json = FileUtil.readString("utf-8", new File(homePath + "/daojia/daojia/config/product/categoryList.json"));
            categoryList = CategoryList.objectFromData(json);
            List<GenericValue> prodCatalogs = delegator.findByAnd("ProdCatalog", UtilMisc.toMap("isBelongStore", "N"));
            if (UtilValidate.isNotEmpty(prodCatalogs)) {
                GenericValue prodCatalog = EntityUtil.getFirst(prodCatalogs);
                String prodCatalogId = prodCatalog.getString("prodCatalogId");
                //获取系统目录对应的浏览根categoryId
                GenericValue pccategory = EntityUtil.getFirst(EntityUtil.filterByDate(delegator.findByAnd("ProdCatalogCategory", UtilMisc.toMap("prodCatalogId", prodCatalogId, "prodCatalogCategoryTypeId", "PCCT_BROWSE_ROOT"))));
                if (UtilValidate.isNotEmpty(pccategory)) {
                    String parentCategoryId = pccategory.getString("productCategoryId");
                    List<FirstTabCate> firstTabCates = FastList.newInstance();
                    //获取分类树
                    FirstTabCate seed = categoryList.result.firstTabCate.get(0);
                    getCateList(delegator, parentCategoryId, true, true, firstTabCates, seed, dispatcher, locale);
                    categoryList.result.firstTabCate = firstTabCates;
                }
            }
            result.put("resultData", categoryList);
        } catch (GenericEntityException e) {
            e.printStackTrace();
            categoryList.setMsg(UtilProperties.getMessage("CommonUiLabels", "CommonError", locale) + ":" + e.getMessage());
            categoryList.setSuccess(false);
        } catch (IOException e) {
            e.printStackTrace();
            categoryList.setMsg(UtilProperties.getMessage("CommonUiLabels", "CommonError", locale) + ":" + e.getMessage());
            categoryList.setSuccess(false);
        }
        return result;
    }
    
    public static void getCateList(Delegator delegator, String parentId, boolean limitView, boolean recursive, List<FirstTabCate> cateList, FirstTabCate seed, LocalDispatcher localDispatcher, Locale locale) {
        
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
                    FirstTabCate tabCate = new FirstTabCate();
                    PropertyUtils.copyProperties(tabCate, seed);
                    category = parent.getRelatedOneCache("CurrentProductCategory");
                    categoryId = category.getString("productCategoryId");
                    String cateName = category.getString("categoryName");
                    tabCate.catId = Integer.valueOf(categoryId);
                    tabCate.name = cateName;
                    List<GroupList> groupLists = FastList.newInstance();
                    tabCate.setGroupList(groupLists);
                    if (recursive) {
                        getGroupList(delegator, categoryId, limitView, recursive, groupLists, cateName, localDispatcher, locale);
                    }
                    cateList.add(tabCate);
                    
                } catch (GenericEntityException e) {
                    Debug.logWarning(e.getMessage(), module);
                } catch (NoSuchMethodException e) {
                    e.printStackTrace();
                } catch (IllegalAccessException e) {
                    e.printStackTrace();
                } catch (InvocationTargetException e) {
                    e.printStackTrace();
                }
                
            }
        }
    }
    
    public static void getGroupList(Delegator delegator, String parentId, boolean limitView, boolean recursive, List<GroupList> groups, String cateName1, LocalDispatcher localDispatcher, Locale locale) {
        
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
                    GroupList groupList = new GroupList();
                    groupList.setCatId(Integer.valueOf(categoryId));
                    groupList.setName(cateName);
                    List<TabCateList> tabCateLists = FastList.newInstance();
                    groupList.setTabCateList(tabCateLists);
                    if (category != null) {
                        if (recursive) {
                            getTabCateList(delegator, categoryId, limitView, recursive, tabCateLists, cateName1, cateName, localDispatcher, locale);
                        }
                    }
                    groups.add(groupList);
                } catch (GenericEntityException e) {
                    Debug.logWarning(e.getMessage(), module);
                }
                
            }
        }
    }
    
    public static void getTabCateList(Delegator delegator, String parentId, boolean limitView, boolean recursive, List<TabCateList> tabCateLists, String cateName1, String cateName2, LocalDispatcher localDispatcher, Locale locale) {
        
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
                    categoryId = category.getString("productCategoryId");
                    String cateName = category.getString("categoryName");
                    TabCateList tabCateList = new TabCateList();
                    tabCateList.setName(cateName);
                    tabCateList.setCatId(Integer.valueOf(categoryId));
                    Long productCounts = delegator.findCountByCondition("ProductCategoryMember", buildCountCondition("productCategoryId", categoryId), null, null);
                    
                    String imgUrl = "https://static-o2o.360buyimg.com/daojia/new/images/show_default.png";
                    
                    if (UtilValidate.isNotEmpty(category.getString("categoryImageUrl"))) {
                        String baseUrl = UtilProperties.getMessage("content.properties", "daojia.baseUrl", locale);
                        imgUrl = baseUrl + category.getString("categoryImageUrl");
                        tabCateList.setImgUrl(imgUrl);
                    } else {
                        tabCateList.setImgUrl(imgUrl);
                    }
                    CatIds catIds = new CatIds();
                    catIds.setCategoryId(categoryId);
                    catIds.setCategoryName(cateName);
                    catIds.setLevel(3);
                    tabCateList.setCatIds(UtilMisc.toList(catIds));
                    tabCateList.setProductConut(productCounts.intValue());
                    String user_action = "{\"catename1\":" + cateName1 + ",\"catename2\":" + cateName2 + ",\"catename3\":" + cateName + "}";
                    tabCateList.setUser_action(user_action);
                    tabCateLists.add(tabCateList);
                } catch (GenericEntityException e) {
                    Debug.logWarning(e.getMessage(), module);
                }
                
            }
        }
    }
    
    private static EntityCondition buildCountCondition(String fieldName, String fieldValue) {
        List<EntityCondition> orCondList = FastList.newInstance();
        orCondList.add(EntityCondition.makeCondition("thruDate", EntityOperator.GREATER_THAN, UtilDateTime.nowTimestamp()));
        orCondList.add(EntityCondition.makeCondition("thruDate", EntityOperator.EQUALS, null));
        EntityCondition orCond = EntityCondition.makeCondition(orCondList, EntityOperator.OR);
        
        List<EntityCondition> andCondList = FastList.newInstance();
        andCondList.add(EntityCondition.makeCondition("fromDate", EntityOperator.LESS_THAN, UtilDateTime.nowTimestamp()));
        andCondList.add(EntityCondition.makeCondition(fieldName, EntityOperator.EQUALS, fieldValue));
        andCondList.add(orCond);
        EntityCondition andCond = EntityCondition.makeCondition(andCondList, EntityOperator.AND);
        
        return andCond;
    }
    
    /**
     * 查询店铺及商品2种方式，
     * 1、根据全局的分类树选择了某个分类
     * 2、根据关键字，包括店铺和产品关键字
     *https://changsy.cn/mobile-daojia/dj/homeSearch.searchByStorePostV_230?functionId=homeSearch%2FsearchByStorePostV_230&body=%7B%22longitude%22%3A118.723389%2C%22latitude%22%3A32.130882%2C%22type%22%3A2%2C%22key%22%3A%22%E8%8B%B9%E6%9E%9C%22%2C%22industryTags%22%3A%5B%221%22%5D%2C%22catIds%22%3A%5B%5D%2C%22sortType%22%3A1%2C%22page%22%3A1%2C%22pageSize%22%3A5%2C%22storeIds%22%3A%5B%5D%2C%22promotLabels%22%3A%22%22%2C%22discountRange%22%3A%22%22%2C%22serviceNo%22%3A1476863113780%7D&appVersion=3.3.0&appName=paidaojia&platCode=H5&lng=118.723389&lat=32.130882&city_id=3201&rand=1476863113784&_=1476863113785&callback=jsonp2
     * @param dcx
     * @param context
     * @return
     */
    public static Map<String, Object> DaoJia_SearchByStorePostV_230(DispatchContext dcx, Map<String, ? extends Object> context) {
        Map<String, Object> result = ServiceUtil.returnSuccess();
        String functionId = (String) context.get("functionId");
        String body = (String) context.get("body");
        String appVersion = (String) context.get("appVersion");
        String appName = (String) context.get("appName");
        String platCode = (String) context.get("platCode");
        String rand = (String) context.get("rand");
        Delegator delegator = dcx.getDelegator();
        Locale locale = (Locale) context.get("locale");
        LocalDispatcher dispatcher = dcx.getDispatcher();
        String categoryId = "";
        SearchBody searchBody = SearchBody.objectFromData(body);
        int page = searchBody.page;
        int pageSize = searchBody.pageSize;
        String key = searchBody.key;
        List<CatIds> catIds = searchBody.catIds;
        
        SearchByStorePost storePost = new SearchByStorePost("homeSearch.searchByStorePostV_230", "0", UtilProperties.getMessage("CommonUiLabels", "CommonSuccess", locale), true);
        if (UtilValidate.isNotEmpty(catIds)) {
            for (int i = 0; i < catIds.size(); i++) {
                CatIds ids = catIds.get(i);
                categoryId = ids.getCategoryId();
            }
        }
        if (!categoryId.equals("")) {
            storePost = getStorePostByCategoryId(delegator, locale, dispatcher, categoryId, page, pageSize, storePost);
            
        } else if (!key.equals("")) {
            storePost = getStorePostByKeyword(delegator, locale, dispatcher, key, page, pageSize, storePost);
        }
        //获取该店铺下对应的产品
        result.put("resultData", storePost);
        
        return result;
    }
    
    
    public static Map<String, Object> DaoJia_HotWords(DispatchContext dcx, Map<String, ? extends Object> context) {
        String functionId = (String) context.get("functionId");
        String body = (String) context.get("body");
        String appVersion = (String) context.get("appVersion");
        String appName = (String) context.get("appName");
        String platCode = (String) context.get("platCode");
        String rand = (String) context.get("rand");
        LocalDispatcher dispatcher = dcx.getDispatcher();
        Delegator delegator = dcx.getDelegator();
        Locale locale = (Locale) context.get("locale");
        
        HotWords words = new HotWords("hotWords.list", "0", UtilProperties.getMessage("CommonUiLabels", "CommonSuccess", locale), true);
        try {
            String homePath = System.getProperty("ofbiz.home");
            String json = FileUtil.readString("utf-8", new File(homePath + "/daojia/daojia/config/product/hotKeyWords.json"));
            words = HotWords.objectFromData(json);
            
            List<GenericValue> hotWords = delegator.findByAnd("HotKeyWordsAndStore", null, null);
            List<HotWordVOList> hotWordVOLists = FastList.newInstance();
            if (UtilValidate.isNotEmpty(hotWords)) {
                Map<String, List<GenericValue>> hotWordsMap = FastMap.newInstance();
                for (int i = 0; i < hotWords.size(); i++) {
                    GenericValue hotWord = hotWords.get(i);
                    String wordsId = hotWord.getString("hotKeyWordsId");
                    if (hotWordsMap.containsKey(wordsId)) {
                        hotWordsMap.get(wordsId).add(hotWord);
                    } else {
                        hotWordsMap.put(wordsId, UtilMisc.toList(hotWord));
                    }
                }
                
                if (UtilValidate.isNotEmpty(hotWordsMap)) {
                    
                    Iterator hotIter = hotWordsMap.keySet().iterator();
                    while (hotIter.hasNext()) {
                        String hotWordsId = (String) hotIter.next();
                        List<GenericValue> hwords = hotWordsMap.get(hotWordsId);
                        HotWordVOList hotWordVOList = new HotWordVOList();
                        if (UtilValidate.isNotEmpty(hwords)) {
                            List<Integer> storeIds = FastList.newInstance();
                            List<Integer> typeIds = FastList.newInstance();
                            String keyword = "";
                            for (int i = 0; i < hwords.size(); i++) {
                                GenericValue hotWordStore = hwords.get(i);
                                keyword = hotWordStore.getString("keyword");
                                Integer storeId = Integer.valueOf(hotWordStore.getString("productStoreId"));
                                Integer typeId = Integer.valueOf(hotWordStore.getString("keywordTypeId"));
                                storeIds.add(storeId);
                                typeIds.add(typeId);
                            }
                            hotWordVOList.setStoreIds(storeIds);
                            hotWordVOList.setHotWords(keyword);
                            hotWordVOList.setTypeIds(typeIds);
                            hotWordVOLists.add(hotWordVOList);
                        }
                    }
                }
            }
            
            words.getResult().setHotWordVOList(hotWordVOLists);
            
            
        } catch (GenericEntityException e) {
            e.printStackTrace();
            words.setMsg(UtilProperties.getMessage("CommonUiLabels", "CommonError", locale) + ":" + e.getMessage());
            words.setSuccess(false);
            
        } catch (IOException e) {
            e.printStackTrace();
            words.setMsg(UtilProperties.getMessage("CommonUiLabels", "CommonError", locale) + ":" + e.getMessage());
            words.setSuccess(false);
        }
        Map<String, Object> result = ServiceUtil.returnSuccess();
        result.put("resultData", words);
        return result;
    }
    
    /**
     * 搜索商品和门店，联想suggest
     *
     * @param dcx
     * @param context
     * @return
     */
    public static Map<String, Object> DaoJia_SuggestList(DispatchContext dcx, Map<String, ? extends Object> context) {
        String functionId = (String) context.get("functionId");
        String body = (String) context.get("body");
        String appVersion = (String) context.get("appVersion");
        String appName = (String) context.get("appName");
        String platCode = (String) context.get("platCode");
        String rand = (String) context.get("rand");
        LocalDispatcher dispatcher = dcx.getDispatcher();
        Delegator delegator = dcx.getDelegator();
        Locale locale = (Locale) context.get("locale");
        SuggestListBody listBody = SuggestListBody.objectFromData(body);
        String key = listBody.key;
        key = "%" + key + "%";
        SuggestList suggestList = new SuggestList("suggest.list", "0", UtilProperties.getMessage("CommonUiLabels", "CommonSuccess", locale), true);
        try {
            //搜索门户 ProductStore(storeName,title,subtitle
            String homePath = System.getProperty("ofbiz.home");
            String json = FileUtil.readString("utf-8", new File(homePath + "/daojia/daojia/config/product/suggestList.json"));
            suggestList = SuggestList.objectFromData(json);
            
            List<EntityCondition> entityConditions = FastList.newInstance();
            entityConditions.add(EntityCondition.makeCondition("storeName", EntityOperator.LIKE, key));
            entityConditions.add(EntityCondition.makeCondition("title", EntityOperator.LIKE, key));
            entityConditions.add(EntityCondition.makeCondition("subtitle", EntityOperator.LIKE, key));
            List<String> suggestNames = FastList.newInstance();
            
            List<GenericValue> storeNames = delegator.findList("ProductStore", EntityCondition.makeCondition(entityConditions, EntityOperator.OR), UtilMisc.toSet("storeName"), null, null, false);
            if (UtilValidate.isNotEmpty(storeNames)) {
                for (int i = 0; i < storeNames.size(); i++) {
                    GenericValue stNames = storeNames.get(i);
                    suggestNames.add(stNames.getString("storeName"));
                    
                }
            }
            //搜索产品
            
            List<GenericValue> keywords = delegator.findList("ProductKeyword", EntityCondition.makeCondition("keyword", EntityOperator.LIKE, key), UtilMisc.toSet("keyword"), null, null, false);
            if (UtilValidate.isNotEmpty(keywords)) {
                for (int i = 0; i < keywords.size(); i++) {
                    GenericValue keyword = keywords.get(i);
                    suggestNames.add(keyword.getString("keyword"));
                }
            }
            if (UtilValidate.isNotEmpty(suggestNames)) {
                List<ResultListVO> resultVo = FastList.newInstance();
                for (int i = 0; i < suggestNames.size(); i++) {
                    ResultListVO listVo = new ResultListVO();
                    String name = suggestNames.get(i);
                    listVo.setCount("0");
                    listVo.setName(name);
                    listVo.setType(0);
                    resultVo.add(listVo);
                    
                }
                suggestList.getResult().setResultListVO(resultVo);
                suggestList.getResult().setResult(resultVo);
            }
        } catch (GenericEntityException e) {
            e.printStackTrace();
            suggestList.setMsg(UtilProperties.getMessage("CommonUiLabels", "CommonError", locale) + ":" + e.getMessage());
            suggestList.setSuccess(false);
        } catch (IOException e) {
            suggestList.setMsg(UtilProperties.getMessage("CommonUiLabels", "CommonError", locale) + ":" + e.getMessage());
            suggestList.setSuccess(false);
        }
        
        
        Map<String, Object> result = ServiceUtil.returnSuccess();
        result.put("resultData", suggestList);
        return result;
    }
    
    
    public static SearchByStorePost getStorePostByCategoryId(Delegator delegator, Locale locale, LocalDispatcher dispatcher, String categoryId, int page, int pageSize, SearchByStorePost storePost) {
        //根据catalog获取对应的分类根
        try {
            String json = FileUtil.readString("utf-8", new File(homePath + "/daojia/daojia/config/product/searchByStorePost.json"));
            storePost = SearchByStorePost.objectFromData(json);
            ProductInfo seed = storePost.result.getStoreSkuList().get(0).getSkuList().get(0);
            //获取该分类下所有的店铺
            Map<String, Object> params = FastMap.newInstance();
            params.put("productCategoryId", categoryId);
            GenericValue category = delegator.findByPrimaryKey("ProductCategory", UtilMisc.toMap("productCategoryId", categoryId));
            Map<String, Object> perResult = dispatcher.runSync("performFindList", UtilMisc.toMap("entityName", "ProductCategoryMemberAndStore", "distinct", "Y", "inputFields", params, "viewIndex", page - 1, "viewSize", pageSize));
            int productCount = 0;
            if (ServiceUtil.isSuccess(perResult)) {
                List<GenericValue> stores = (List<GenericValue>) perResult.get("list");
                Integer listSize = (Integer) perResult.get("listSize");
                storePost.result.page = page;
                storePost.result.storeCount = listSize;
                storePost.result.user_action = "";//暂为空
                storePost.result.promptTips = category.getString("categoryName");
                
                if (UtilValidate.isNotEmpty(stores)) {
                    List skuLists = FastList.newInstance();
                    for (int i = 0; i < stores.size(); i++) {
                        GenericValue store = stores.get(i);
                        String storeId = store.getString("productStoreId");
                        StoreSkuList skuList = new StoreSkuList();
                        skuList.setCount((Integer) perResult.get("listSize"));
                        skuList.setPage(page);
                        StoreInfo storeInfo = new StoreInfo();
                        skuList.setStore(storeInfo);
                        //设置tags
                        Map<String, Object> storeResult = dispatcher.runSync("DaoJia_StoreInfo", UtilMisc.toMap("productStoreId", storeId));
                        String orgCode = null;
                        String contactNumber = null;
                        String facilityId = null;
                        String catalogId = null;
                        List<ServiceTimes> serviceTimesList = null;
                        if (ServiceUtil.isSuccess(storeResult)) {
                            orgCode = (String) storeResult.get("orgCode");
                            contactNumber = (String) storeResult.get("contactNumber");
                            facilityId = (String) ((GenericValue) storeResult.get("facility")).get("facilityId");
                            catalogId = (String) ((GenericValue) storeResult.get("catalog")).get("prodCatalogId");
                            serviceTimesList = (List<ServiceTimes>) storeResult.get("serviceTimes");
                            store = (GenericValue) storeResult.get("productStore");
                            
                        }
                        Map<String, Object> productPromos = dispatcher.runSync("DaoJia_StorePromos", UtilMisc.toMap("productStoreId", storeId));
                        if (ServiceUtil.isSuccess(productPromos)) {
                            List<Tags> tagses = (List<Tags>) productPromos.get("resultData");
                            storeInfo.tag = tagses;
                        }
                        //grade
                        storeInfo.grade = new Double(store.getString("rate"));
                        storeInfo.starGrade = new Double(store.getString("rate"));
                        storeInfo.userActionStore = "{\"solution\":\"chengdu_divide\",\"store_id\":" + storeId + "}";
                        storeInfo.storePageType = "2";
                        storeInfo.carrierNo = 2938;
                        storeInfo.desc = store.getString("title");
                        storeInfo.storeId = storeId;
                        storeInfo.orgCode = orgCode;
                        storeInfo.storeName = store.getString("storeName");
                        storeInfo.templeType = "2";
                        storeInfo.openJPIndustry = "6";
                        storeInfo.openJPIndustryName = category.getString("categoryName");
                        storeInfo.phone = contactNumber;
                        storeInfo.serviceTimes = serviceTimesList;
                        
                        storeInfo.logo = (String) storeResult.get("logo");
                        
                        List conditions = FastList.newInstance();
                        conditions.add(EntityCondition.makeCondition("productStoreId", storeId));
                        conditions.add(EntityCondition.makeCondition("productCategoryId", categoryId));
                        List<GenericValue> productIds = delegator.findList("ProductCategoryMemberAndProduct", EntityCondition.makeCondition(conditions, EntityOperator.AND), UtilMisc.toSet("productId"), UtilMisc.toList("productId"), null, true);
                        if (UtilValidate.isNotEmpty(productIds)) {
                            skuList.setCount(productIds.size());
                            skuList.setPage(1);
                            productCount += productIds.size();
                            Set proSets = FastSet.newInstance();
                            for (int j = 0; j < productIds.size(); j++) {
                                GenericValue product = productIds.get(j);
                                proSets.add(product.getString("productId"));
                            }
                            
                            List<ProductInfo> productInfos = ProductService.generateDaojiaProductInfos(storeId, locale, dispatcher, orgCode, seed, catalogId, proSets, facilityId);
                            if (UtilValidate.isNotEmpty(productInfos)) {
                                skuList.setSkuList(productInfos);
                            }
                        }
                        
                        skuLists.add(skuList);
                        storePost.result.storeSkuList = skuLists;
                    }
                    storePost.result.count = productCount;
                    
                }
            }
            
        } catch (IOException e) {
            e.printStackTrace();
            storePost.setMsg(UtilProperties.getMessage("CommonUiLabels", "CommonError", locale) + ":" + e.getMessage());
            storePost.setSuccess(false);
        } catch (GenericEntityException e) {
            e.printStackTrace();
            storePost.setMsg(UtilProperties.getMessage("CommonUiLabels", "CommonError", locale) + ":" + e.getMessage());
            storePost.setSuccess(false);
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
            storePost.setMsg(UtilProperties.getMessage("CommonUiLabels", "CommonError", locale) + ":" + e.getMessage());
            storePost.setSuccess(false);
        } catch (IllegalAccessException e) {
            e.printStackTrace();
            storePost.setMsg(UtilProperties.getMessage("CommonUiLabels", "CommonError", locale) + ":" + e.getMessage());
            storePost.setSuccess(false);
        } catch (InvocationTargetException e) {
            e.printStackTrace();
            storePost.setMsg(UtilProperties.getMessage("CommonUiLabels", "CommonError", locale) + ":" + e.getMessage());
            storePost.setSuccess(false);
        } catch (GenericServiceException e) {
            e.printStackTrace();
            storePost.setMsg(UtilProperties.getMessage("CommonUiLabels", "CommonError", locale) + ":" + e.getMessage());
            storePost.setSuccess(false);
        }
        return storePost;
    }
    
    
    public static SearchByStorePost getStorePostByKeyword(Delegator delegator, Locale locale, LocalDispatcher dispatcher, String keyword, int page, int pageSize, SearchByStorePost storePost) {
        //根据catalog获取对应的分类根
        try {
            
            //获取该关键字下所有的店铺，产品
            List<EntityCondition> entityConditions = FastList.newInstance();
            entityConditions.add(EntityCondition.makeCondition("storeName", EntityOperator.LIKE, "%"+keyword+"%"));
            entityConditions.add(EntityCondition.makeCondition("title", EntityOperator.LIKE, "%"+keyword+"%"));
            entityConditions.add(EntityCondition.makeCondition("subtitle", EntityOperator.LIKE, "%"+keyword+"%"));
            List<String> suggestNames = FastList.newInstance();
            List<GenericValue> productStoreIds = delegator.findList("ProductStore", EntityCondition.makeCondition(entityConditions, EntityOperator.OR), UtilMisc.toSet("productStoreId"), null, null, false);
            Set<String> storeIds = FastSet.newInstance();
            if (UtilValidate.isNotEmpty(productStoreIds)) {
                for (int i = 0; i < productStoreIds.size(); i++) {
                    GenericValue store = productStoreIds.get(i);
                    storeIds.add(store.getString("productStoreIds"));
                }
            }
            //搜索产品
            List<GenericValue> keywords = delegator.findList("ProductKeyword", EntityCondition.makeCondition("keyword", EntityOperator.LIKE, "%"+keyword+"%"), UtilMisc.toSet("productId"), null, null, false);
            List<String> productIds = FastList.newInstance();
            if (UtilValidate.isNotEmpty(keywords)) {
                for (int i = 0; i < keywords.size(); i++) {
                    GenericValue keywordsProIds = keywords.get(i);
                    productIds.add(keywordsProIds.getString("productId"));
                }
            }
            
            List<GenericValue> pStoreIds = delegator.findList("ProductStoreProduct", EntityCondition.makeCondition("productId", EntityOperator.IN, productIds), UtilMisc.toSet("productStoreId"), null, null, false);
            if (UtilValidate.isNotEmpty(pStoreIds)) {
                for (int i = 0; i < pStoreIds.size(); i++) {
                    GenericValue pStoreId = pStoreIds.get(i);
                    storeIds.add(pStoreId.getString("productStoreId"));
                }
            }
            
            int productCount = 0;
            if (UtilValidate.isNotEmpty(storeIds)) {
                String json = FileUtil.readString("utf-8", new File(homePath + "/daojia/daojia/config/product/searchByStorePost.json"));
                storePost = SearchByStorePost.objectFromData(json);
                
                storePost.result.page = page;
                storePost.result.storeCount = storeIds.size();
                storePost.result.user_action = "";//暂为空
                storePost.result.promptTips = keyword;
                //取page，pagesize下的storeIds
                String[] storeIdArrs = new String[storeIds.size()];
                storeIds.toArray(storeIdArrs);
                List<String> destStoreIds = FastList.newInstance();
                for (int i = ((page - 1) * pageSize); i < (page * pageSize); i++) {
                    if (i < storeIdArrs.length && storeIdArrs[i] != null) {
                        destStoreIds.add(storeIdArrs[i]);
                    }
                }
                List skuLists = FastList.newInstance();
                ProductInfo seed = storePost.result.getStoreSkuList().get(0).getSkuList().get(0);
                for (int i = 0; i < destStoreIds.size(); i++) {
                    String storeId = destStoreIds.get(i);
                    StoreSkuList skuList = new StoreSkuList();
                    skuList.setCount(storeIds.size());
                    skuList.setPage(page);
                    StoreInfo storeInfo = new StoreInfo();
                    skuList.setStore(storeInfo);
                    //设置tags
                    Map<String, Object> storeResult = dispatcher.runSync("DaoJia_StoreInfo", UtilMisc.toMap("productStoreId", storeId));
                    String orgCode = null;
                    String contactNumber = null;
                    String facilityId = null;
                    String catalogId = null;
                    List<ServiceTimes> serviceTimesList = null;
                    GenericValue store = null;
                    if (ServiceUtil.isSuccess(storeResult)) {
                        orgCode = (String) storeResult.get("orgCode");
                        contactNumber = (String) storeResult.get("contactNumber");
                        facilityId = (String) ((GenericValue) storeResult.get("facility")).get("facilityId");
                        catalogId = (String) ((GenericValue) storeResult.get("catalog")).get("prodCatalogId");
                        serviceTimesList = (List<ServiceTimes>) storeResult.get("serviceTimes");
                        store = (GenericValue) storeResult.get("productStore");
                    }
                    Map<String, Object> productPromos = dispatcher.runSync("DaoJia_StorePromos", UtilMisc.toMap("productStoreId", storeId));
                    if (ServiceUtil.isSuccess(productPromos)) {
                        List<Tags> tagses = (List<Tags>) productPromos.get("resultData");
                        storeInfo.tag = tagses;
                    }
                    //grade
                    storeInfo.grade = new Double(store.getString("rate"));
                    storeInfo.starGrade = new Double(store.getString("rate"));
                    storeInfo.userActionStore = "{\"solution\":\"chengdu_divide\",\"store_id\":" + storeId + "}";
                    storeInfo.storePageType = "2";
                    storeInfo.carrierNo = 2938;
                    storeInfo.desc = store.getString("title");
                    storeInfo.storeId = storeId;
                    storeInfo.orgCode = orgCode;
                    storeInfo.storeName = store.getString("storeName");
                    storeInfo.templeType = "2";
                    storeInfo.openJPIndustry = "6";
                    storeInfo.openJPIndustryName = store.getString("storeName");
                    storeInfo.phone = contactNumber;
                    storeInfo.serviceTimes = serviceTimesList;
                    
                    storeInfo.logo = (String) storeResult.get("logo");
                    
                    //根据productIds获取该stroe下的product
                    List<EntityCondition> findPros = FastList.newInstance();
                    findPros.add(EntityCondition.makeCondition("productStoreId", EntityOperator.EQUALS, storeId));
                    findPros.add(EntityCondition.makeCondition("productId", EntityOperator.IN, productIds));
                    
                    List<GenericValue> sProductIds = delegator.findList("ProductStoreProduct", EntityCondition.makeCondition(findPros, EntityOperator.AND), UtilMisc.toSet("productId"), null, null, false);
                    if (UtilValidate.isNotEmpty(sProductIds)) {
                        skuList.setCount(sProductIds.size());
                        skuList.setPage(1);
                        productCount += sProductIds.size();
                        Set proSets = FastSet.newInstance();
                        for (int j = 0; j < sProductIds.size(); j++) {
                            GenericValue product = sProductIds.get(j);
                            proSets.add(product.getString("productId"));
                        }
                        
                        List<ProductInfo> productInfos = ProductService.generateDaojiaProductInfos(storeId, locale, dispatcher, orgCode, seed, catalogId, proSets, facilityId);
                        if (UtilValidate.isNotEmpty(productInfos)) {
                            skuList.setSkuList(productInfos);
                        }
                    }
                    
                    skuLists.add(skuList);
                    storePost.result.storeSkuList = skuLists;
                }
                storePost.result.count = productCount;
            }
            
        } catch (IOException e) {
            e.printStackTrace();
            storePost.setMsg(UtilProperties.getMessage("CommonUiLabels", "CommonError", locale) + ":" + e.getMessage());
            storePost.setSuccess(false);
        } catch (GenericEntityException e) {
            e.printStackTrace();
            storePost.setMsg(UtilProperties.getMessage("CommonUiLabels", "CommonError", locale) + ":" + e.getMessage());
            storePost.setSuccess(false);
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
            storePost.setMsg(UtilProperties.getMessage("CommonUiLabels", "CommonError", locale) + ":" + e.getMessage());
            storePost.setSuccess(false);
        } catch (IllegalAccessException e) {
            e.printStackTrace();
            storePost.setMsg(UtilProperties.getMessage("CommonUiLabels", "CommonError", locale) + ":" + e.getMessage());
            storePost.setSuccess(false);
        } catch (InvocationTargetException e) {
            e.printStackTrace();
            storePost.setMsg(UtilProperties.getMessage("CommonUiLabels", "CommonError", locale) + ":" + e.getMessage());
            storePost.setSuccess(false);
        } catch (GenericServiceException e) {
            e.printStackTrace();
            storePost.setMsg(UtilProperties.getMessage("CommonUiLabels", "CommonError", locale) + ":" + e.getMessage());
            storePost.setSuccess(false);
        }
        return storePost;
    }
}
