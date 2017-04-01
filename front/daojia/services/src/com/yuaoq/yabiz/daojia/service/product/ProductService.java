package com.yuaoq.yabiz.daojia.service.product;


import com.yuaoq.yabiz.daojia.model.json.base.Tags;
import com.yuaoq.yabiz.daojia.model.json.common.product5.SkuInfo;
import com.yuaoq.yabiz.daojia.model.json.product.ProductInfo;
import com.yuaoq.yabiz.daojia.model.json.product.productDetail.*;
import com.yuaoq.yabiz.daojia.model.json.product.productRecommend.ProductRecommend;
import com.yuaoq.yabiz.daojia.model.json.product.productSearch.ProductSearch;
import com.yuaoq.yabiz.daojia.model.json.product.productSearch.SearchResult;
import com.yuaoq.yabiz.daojia.model.json.product.productSearch.UserAction;
import com.yuaoq.yabiz.daojia.model.json.request.BaseBody;
import com.yuaoq.yabiz.daojia.model.json.request.ProductSearchBody;
import javolution.util.FastList;
import javolution.util.FastMap;
import javolution.util.FastSet;
import org.apache.commons.beanutils.PropertyUtils;
import org.ofbiz.base.util.*;
import org.ofbiz.entity.Delegator;
import org.ofbiz.entity.GenericEntityException;
import org.ofbiz.entity.GenericValue;
import org.ofbiz.entity.transaction.GenericTransactionException;
import org.ofbiz.entity.transaction.TransactionUtil;
import org.ofbiz.entity.util.EntityUtil;
import org.ofbiz.product.config.ProductConfigWorker;
import org.ofbiz.product.config.ProductConfigWrapper;
import org.ofbiz.product.product.ProductContentWrapper;
import org.ofbiz.product.product.ProductSearchSession;
import org.ofbiz.product.product.ProductWorker;
import org.ofbiz.service.DispatchContext;
import org.ofbiz.service.GenericServiceException;
import org.ofbiz.service.LocalDispatcher;
import org.ofbiz.service.ServiceUtil;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.net.MalformedURLException;
import java.sql.Timestamp;
import java.util.*;

/**
 * Created by changsy on 16/9/2.
 */
public class ProductService {
    
    public static final String module = ProductService.class.getName();
    public static final int DEFAULT_TX_TIMEOUT = 600;
    
    /**
     * 分页获取
     * /**
     * <attribute name="AVAILABILITY_FILTER" type="Boolean" mode="IN"  default-value="true" />
     * <attribute name="SEARCH_OPERATOR_OR" type="String" mode="IN" default-value="OR" />
     * <attribute name="PAGING"  type="String" default-value="Y" mode="IN"/>
     * <attribute name="SEARCH_CATEGORY_name"  type="String"  mode="IN"/>
     * <attribute name="SEARCH_CATALOG_name"  type="String" mode="IN"/>
     * <attribute name="VIEW_SIZE"   type="Integer" mode="IN" />
     * <attribute name="VIEW_INDEX"  type="Integer" mode="IN"/>
     * <attribute name="SEARCH_STRING"  type="String" mode="IN"/>
     * <attribute name="sortAscending"  type="String" mode="IN"/>
     * <attribute name="sortOrder"  type="String" mode="IN"/>
     * <attribute name="attributeNames" type="String" mode="IN"/>
     * <attribute name="attributeValues" type="String" mode="IN"/>
     * <attribute name="DEFAULT_PRICE_HIGH" type="String" mode="IN"/>
     * <attribute name="DEFAULT_PRICE_LOW" type="String" mode="IN"/>
     * <attribute name="productBrandString" type="String" mode="IN"/>
     * <attribute name="productPriceString" type="String" mode="IN"/>
     * <attribute name="partBrandname" type="String" mode="IN"/>
     * <attribute name="partSerialname" type="String" mode="IN"/>
     * <attribute name="partGroupname" type="String" mode="IN"/>
     * <attribute name="partCarname" type="String" mode="IN"/>
     * <attribute name="hasInventoryOnly" type="String" mode="IN"/>
     * <attribute name="currentCategoryname" type="String" mode="IN"/>
     * <attribute mode="OUT" name="resultData" optional="true" type="java.util.Map"/>
     *
     * @param request
     * @param response
     * @return
     */
    public static String DaoJia_ProductSearch(HttpServletRequest request, HttpServletResponse response) {
        Map<String, Object> result = ServiceUtil.returnSuccess();
        ProductSearch search = new ProductSearch("product.productsearch", "0", "success", true);
        boolean beganTransaction = false;
        Map<String, String[]> context = request.getParameterMap();
        String functionId = context.get("functionId")[0];
        String body = context.get("body")[0];
        String appVersion = context.get("appVersion")[0];
        String appName = context.get("appName")[0];
        String platCode = context.get("platCode")[0];
        String rand = context.get("rand")[0];
        String productStoreId = context.get("productStoreId")[0];
        Locale locale = request.getLocale();
        try {
            beganTransaction = TransactionUtil.begin(DEFAULT_TX_TIMEOUT);
            ProductSearchSession.checkDoKeywordOverride(request, response);
            Delegator delegator = (Delegator) request.getAttribute("delegator");
            LocalDispatcher localDispatcher = (LocalDispatcher) request.getAttribute("dispatcher");
            Map<String, Object> parameters = FastMap.newInstance();
            ProductSearchSession.processSearchParameters(parameters, request);
            //当前catalogId
            String prodCatalogId = null;
            //根据store获取地区code
            String orgCode = "";
            String contactNumber = null;
            String facilityId = null;
            Map<String, Object> storeResult = null;
            try {
                storeResult = localDispatcher.runSync("DaoJia_StoreInfo", UtilMisc.toMap("productStoreId", productStoreId));
            } catch (GenericServiceException e) {
                e.printStackTrace();
            }
            if (ServiceUtil.isSuccess(storeResult)) {
                orgCode = (String) storeResult.get("orgCode");
                contactNumber = (String) storeResult.get("contactNumber");
//              productStore = (GenericValue)storeResult.get("productStore");
                facilityId = (String) ((GenericValue) storeResult.get("facility")).get("facilityId");
                prodCatalogId = (String) ((GenericValue) storeResult.get("catalog")).get("prodCatalogId");
            }
            
            Map<String, Object> productSearchResult = ProductSearchSession.getProductSearchResult(request, delegator, prodCatalogId);
            Set<String> productIds = FastSet.newInstance();
            if (UtilValidate.isNotEmpty(productSearchResult)) {
                List lProductIds = (List<String>) productSearchResult.get("productIds");
                productIds.addAll(lProductIds);
            }
            
            // load the script
            ProductSearchBody productSearchBody = ProductSearchBody.objectFromData(body);
            int page = productSearchBody.page;
            int pageSize = productSearchBody.pageSize;
            int sortType = productSearchBody.sortType;
            String key = productSearchBody.key;
            
            String homePath = System.getProperty("ofbiz.home");
            String json = FileUtil.readString("utf-8", new File(homePath + "/daojia/daojia/config/product/productsearch.json"));
            search = ProductSearch.objectFromData(json);
            
            //获取对应的categoryId
            
            String productId = productSearchBody.skuId;
            
            //获取categoryId下所有的产品，如果没有取下级至叶子节点第一个
            
            String userActionJson = FileUtil.readString("utf-8", new File(homePath + "/daojia/daojia/config/product/userAction.json"));
            SearchResult searchResult = search.result;
            UserAction userAction = UserAction.objectFromData(userActionJson);
            userAction.query.storeId = productStoreId;
            userAction.storeid = productStoreId;
            searchResult.user_action = UserAction.objectToStr(userAction);
            //设置商品searchResultVOList
            
            //设置sku
            if (productId != null) {
                productIds.add(productId);
            }
            ProductInfo seed = search.result.anchoredProduct;
            List<ProductInfo> anchoredProducts = generateDaojiaProductInfos(productStoreId, locale, localDispatcher, orgCode, seed, prodCatalogId, productIds, facilityId);
            
            searchResult.searchResultVOList = anchoredProducts;
            
            if (productId == null) {
                searchResult.anchoredProduct = null;
            } else {
                if (UtilValidate.isNotEmpty(anchoredProducts)) {
                    for (int i = 0; i < anchoredProducts.size(); i++) {
                        ProductInfo productInfo = anchoredProducts.get(i);
                        if (productInfo.getSkuId().equals(productId)) {
                            searchResult.anchoredProduct = productInfo;
                        }
                    }
                }
                
            }
            //总记录数
            searchResult.count = (Integer) productSearchResult.get("listSize");
            searchResult.page = (Integer) productSearchResult.get("viewIndex");
            searchResult.storeId = productStoreId;
            
            
        } catch (GenericTransactionException e) {
            search.setCode("1");
            search.setMsg(e.getMessage());
        } catch (MalformedURLException e) {
            search.setCode("1");
            search.setMsg(e.getMessage());
            e.printStackTrace();
        } catch (IOException e) {
            search.setCode("1");
            search.setMsg(e.getMessage());
            e.printStackTrace();
        } catch (GenericEntityException e) {
            search.setCode("1");
            search.setMsg(e.getMessage());
            e.printStackTrace();
        } catch (GenericServiceException e) {
            search.setCode("1");
            search.setMsg(e.getMessage());
            e.printStackTrace();
        } catch (NoSuchMethodException e) {
            search.setCode("1");
            search.setMsg(e.getMessage());
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            search.setCode("1");
            search.setMsg(e.getMessage());
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            search.setCode("1");
            search.setMsg(e.getMessage());
            e.printStackTrace();
        } finally {
            try {
                TransactionUtil.commit(beganTransaction);
            } catch (GenericTransactionException e) {
                e.printStackTrace();
            }
        }
        request.setAttribute("resultData", search);
        return "success";
    }
    
    
    public static Map<String, Object> daoJia_productSummary(DispatchContext dcx, Map<String, ? extends Object> context) {
        java.text.DecimalFormat df = new java.text.DecimalFormat("0.00");
        Set productIds = (Set) context.get("productIds");
        String webSiteId = (String) context.get("webSiteId");
        String prodCatalogId = (String) context.get("prodCatalogId");
        String productStoreId = (String) context.get("productStoreId");
        Locale locale = (Locale) context.get("locale");
        GenericValue userLogin = (GenericValue) context.get("userLogin");
        Delegator delegator = dcx.getDelegator();
        LocalDispatcher dispatcher = dcx.getDispatcher();
        //获取店铺信息
        GenericValue productStore = null;
        String orgCode = null;
        String contactNumber = null;
        Map<String, Object> storeResult = null;
        try {
            storeResult = dispatcher.runSync("DaoJia_StoreInfo", UtilMisc.toMap("productStoreId", productStoreId));
        } catch (GenericServiceException e) {
            e.printStackTrace();
        }
        if (ServiceUtil.isSuccess(storeResult)) {
            orgCode = (String) storeResult.get("orgCode");
            contactNumber = (String) storeResult.get("contactNumber");
            productStore = (GenericValue) storeResult.get("productStore");
        }
        String defaultCurrency = (String) productStore.get("defaultCurrencyUomId");
        
        
        Map<String, Object> retMap = ServiceUtil.returnSuccess();
        List retList = FastList.newInstance();
        Iterator productIter = productIds.iterator();
        while (productIter.hasNext()) {
            String productId = (String) productIter.next();
            GenericValue miniProduct = null;
            try {
                miniProduct = delegator.findByPrimaryKey("Product", UtilMisc.toMap("productId", productId));
            } catch (GenericEntityException e) {
                Debug.logError(e, "Problems reading order header from datasource.", module);
                
            }
            if (miniProduct != null && productStoreId != null && prodCatalogId != null) {
                // calculate the "your" price
                Map<String, Object> priceResult = null;
                try {
                    priceResult = dispatcher.runSync("calculateProductPrice",
                            UtilMisc.<String, Object>toMap("product", miniProduct, "prodCatalogId", prodCatalogId, "webSiteId", webSiteId, "currencyUomId", defaultCurrency, "autoUserLogin", userLogin, "productStoreId", productStoreId));
                    if (ServiceUtil.isError(priceResult)) {
                        
                    }
                } catch (GenericServiceException e) {
                    Debug.logError(e, "Error changing item status to " + "ITEM_COMPLETED" + ": " + e.toString(), module);
                }
                
                Map<String, Object> result = FastMap.newInstance();
                
                // returns: basePrice listPrice ,COMPETITIVE_PRICE,AVERAGE_COST
                result.put("priceResult", priceResult);
                
                // get aggregated product totalPrice
                if ("AGGREGATED".equals(miniProduct.get("productTypeId")) || "AGGREGATED_SERVICE".equals(miniProduct.get("productTypeId"))) {
                    ProductConfigWrapper configWrapper = ProductConfigWorker.getProductConfigWrapper(productId, defaultCurrency, prodCatalogId, webSiteId, productStoreId, userLogin, dispatcher, delegator, locale);
                    if (configWrapper != null) {
                        configWrapper.setDefaultConfig();
                        // Check if Config Price has to be displayed with tax
                        if (productStore.get("showPricesWithVatTax").equals("Y")) {
                            BigDecimal totalPriceNoTax = configWrapper.getTotalPrice();
                            Map totalPriceMap = null;
                            try {
                                totalPriceMap = dispatcher.runSync("calcTaxForDisplay", UtilMisc.toMap("basePrice", totalPriceNoTax, "locale", locale, "productId", productId, "productStoreId", productStoreId));
                            } catch (GenericServiceException e) {
                                e.printStackTrace();
                            }
                            result.put("totalPrice", df.format(totalPriceMap.get("priceWithTax")));
                        } else {
                            result.put("totalPrice", df.format(configWrapper.getTotalPrice()));
                        }
                    }
                }
                result.put("nowTimeLong", UtilDateTime.nowTimestamp());
                // make the miniProductContentWrapper
                ProductContentWrapper miniProductContentWrapper = new ProductContentWrapper(dispatcher, miniProduct, locale, "text/html");
                String mediumImageUrl = miniProductContentWrapper.get("MEDIUM_IMAGE_URL").toString();
                String description = miniProductContentWrapper.get("DESCRIPTION").toString();
                String productName = miniProductContentWrapper.get("PRODUCT_NAME").toString();
                String isVirtual = miniProductContentWrapper.get("IS_VIRTUAL").toString();
                String largeImageUrl = miniProductContentWrapper.get("LARGE_IMAGE_URL").toString();
                String originalImageUrl = miniProductContentWrapper.get("ORIGINAL_IMAGE_URL").toString();
                String smallImageUrl = miniProductContentWrapper.get("SMALL_IMAGE_URL").toString();
                String wrapProductId = miniProductContentWrapper.get("PRODUCT_ID").toString();
                //增加虚拟产品对应的信息featureTypeId,featureId
                
                if (isVirtual.equals("Y")) {
                    GenericValue product = null;
                    try {
                        product = delegator.findByPrimaryKey("Product", UtilMisc.toMap("productId", productId));
                    } catch (GenericEntityException e) {
                        e.printStackTrace();
                    }
                    List featureLists = ProductWorker.getSelectableProductFeaturesByTypesAndSeq(product);
                    result.put("featureList", featureLists);
                    Map<String, Object> featureMap = null;
                    try {
                        featureMap = dispatcher.runSync("getProductFeatureSet", UtilMisc.toMap("productId", productId));
                    } catch (GenericServiceException e) {
                        e.printStackTrace();
                    }
                    Set featureSet = (Set) featureMap.get("featureSet");
                    if (featureSet != null) {
                        Map<String, Object> variantTreeMap = null;
                        try {
                            variantTreeMap = dispatcher.runSync("getProductVariantTree", UtilMisc.toMap("productId", productId, "featureOrder", featureSet, "productStoreId", productStoreId));
                        } catch (GenericServiceException e) {
                            e.printStackTrace();
                        }
                        Map<String, Object> variantsRes = null;
                        // make a list of variant sku with requireAmount
                        try {
                            variantsRes = dispatcher.runSync("getAssociatedProducts", UtilMisc.toMap("productId", productId, "type", "PRODUCT_VARIANT",
                                    "checkViewAllow", true, "prodCatalogId", prodCatalogId));
                        } catch (GenericServiceException e) {
                            e.printStackTrace();
                        }
                        result.put("variantTreeChoose", variantTreeMap.get("variantTreeChoose"));
//                    result.put("variantsRes", variantsRes);
                        List<GenericValue> assocProducts = (List<GenericValue>) variantsRes.get("assocProducts");
                    }
                    
                }
                result.put("description", description);
                result.put("productId", productId);
                result.put("catalogId", prodCatalogId);
                result.put("mediumImageUrl", mediumImageUrl);
                result.put("smallImageUrl", smallImageUrl);
                //result.put("longDescription",longDescription);
                result.put("productName", productName);
                result.put("isVirtual", isVirtual);
                result.put("largeImageUrl", largeImageUrl);
                result.put("originalImageUrl", originalImageUrl);
                result.put("productTypeId", miniProduct.get("productTypeId"));
                result.put("wrapProductId", wrapProductId);
                retList.add(result);
            }
        }
        
        
        retMap.put("resultData", retList);
        return retMap;
    }
    
    /**
     * 获取产品详细
     */
    public static Map<String, Object> daoJia_productDetail(DispatchContext dcx, Map<String, ? extends Object> context) {
        Map<String, Object> result = ServiceUtil.returnSuccess();
        String functionId = (String) context.get("functionId");
        String body = (String) context.get("body");
        String appVersion = (String) context.get("appVersion");
        String appName = (String) context.get("appName");
        String platCode = (String) context.get("platCode");
        String rand = (String) context.get("rand");
        Locale locale = (Locale) context.get("locale");
        Delegator delegator = dcx.getDelegator();
        LocalDispatcher dispatcher = dcx.getDispatcher();
        
        DetailBody detailBody = DetailBody.objectFromData(body);
        String productId = detailBody.skuId;
        String productStoreId = detailBody.storeId;
        java.text.DecimalFormat df = new java.text.DecimalFormat("0.00");
        
        //获取orgCode
        String orgCode = "";
        String contactNumber = "";
        GenericValue productStore = null;
        try {
            
            Map<String, Object> storeResult = dispatcher.runSync("DaoJia_StoreInfo", UtilMisc.toMap("productStoreId", productStoreId));
            if (ServiceUtil.isSuccess(storeResult)) {
                orgCode = (String) storeResult.get("orgCode");
                contactNumber = (String) storeResult.get("contactNumber");
                productStore = (GenericValue) storeResult.get("productStore");
            }
            
            
            String homePath = System.getProperty("ofbiz.home");
            String json = null;
            
            json = FileUtil.readString("utf-8", new File(homePath + "/daojia/daojia/config/product/productdetail.json"));
            
            ProductDetail detail = ProductDetail.objectFromData(json);
            ProductDetailResult detailResult = detail.result;
            detailResult.orgCode = orgCode;
            detailResult.skuId = Integer.valueOf(detailBody.skuId);
            //根据store获取catalogId
            GenericValue proStoreCatalog = null;
            
            proStoreCatalog = EntityUtil.getFirst(EntityUtil.filterByDate(delegator.findByAnd("ProductStoreCatalog", UtilMisc.toMap("productStoreId", productStoreId))));
            String prodCatalogId = (String) proStoreCatalog.get("prodCatalogId");
            Map<String, Object> prodSummaryResult = dispatcher.runSync("DaoJia_productSummary", UtilMisc.toMap("productIds", UtilMisc.toSet(productId), "webSiteId", "daojia", "prodCatalogId", prodCatalogId, "productStoreId", productStoreId));
            List<Map> productInfos = (List<Map>) prodSummaryResult.get("resultData");
            if (UtilValidate.isNotEmpty(productInfos)) {
                for (int i = 0; i < productInfos.size(); i++) {
                    Map productMap = productInfos.get(i);
                    detailResult.name = (String) productMap.get("productName");
                    //设置产品图片
                    List images = FastList.newInstance();
                    ProductImage pImage = new ProductImage();
                    String baseUrl = UtilProperties.getMessage("content.properties", "daojia.baseUrl", locale);
                    pImage.small = baseUrl + productMap.get("smallImageUrl");
                    pImage.big = baseUrl + productMap.get("largeImageUrl");
                    pImage.small = baseUrl + productMap.get("largeImageUrl");
                    pImage.share = baseUrl + productMap.get("largeImageUrl");
                    images.add(pImage);
                    detailResult.image = images;
                    //设置storeInfo
                    StoreInfo storeInfo = detailResult.storeInfo;
                    storeInfo.storeId = productStoreId;
                    storeInfo.storeName = (String) productStore.get("storeName");
                    storeInfo.venderPhone = contactNumber;
                    storeInfo.venderId = productStoreId;
                    storeInfo.venderName = (String) productStore.get("storeName");
                    //设置tags
                    Map<String, Object> productPromos = dispatcher.runSync("DaoJia_ProductPromos", UtilMisc.toMap("productId", productId, "productStoreId", productStoreId));
                    if (ServiceUtil.isSuccess(productPromos)) {
                        List<Tags> tagses = (List<Tags>) productPromos.get("resultData");
                        detailResult.tags = tagses;
                    }
                    //设置产品评价
                    
                    
                    //设置产品价格
                    Map priceResultMap = (Map) productMap.get("priceResult");
                    BigDecimal pieces = BigDecimal.ONE;
                    int decimals = 2;
                    String basicPrice = df.format(((BigDecimal) priceResultMap.get("basePrice")).divide(pieces, decimals, RoundingMode.HALF_UP));
                    detailResult.skuPriceVO.skuId = productId;
                    detailResult.skuPriceVO.realTimePrice = basicPrice;
                    detailResult.skuPriceVO.basicPrice = basicPrice;
                    detailResult.skuPriceVO.promotion = 1;
                }
            }
            result.put("resultData", detail);
            
        } catch (GenericServiceException e) {
            e.printStackTrace();
        } catch (GenericEntityException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        
        return result;
    }
    
    public static Map<String, Object> daoJia_productComment(DispatchContext dcx, Map<String, ? extends Object> context) {
        Map<String, Object> result = ServiceUtil.returnSuccess();
        String productId = (String) context.get("productId");
        String productStroeId = (String) context.get("productStroeId");
        LocalDispatcher localDispatcher = dcx.getDispatcher();
        Locale locale = (Locale) context.get("locale");
        ProductComment comment = new ProductComment();
        try {
            Map<String, Object> reviewMap = localDispatcher.runSync("productReviews", UtilMisc.toMap("productId", productId, "productStroeId", productStroeId));
            if (ServiceUtil.isSuccess(reviewMap)) {
                BigDecimal pieces = BigDecimal.ONE;
                int decimals = 2;
                comment.totalScore = ((BigDecimal) reviewMap.get("averageRating")).divide(pieces, decimals, RoundingMode.HALF_UP).toString();
                comment.commentNum = reviewMap.get("numRatings").toString();
                List<GenericValue> reviews = (List<GenericValue>) reviewMap.get("reviews");
                if (UtilValidate.isNotEmpty(reviews)) {
                    List<CommentList> commentLists = FastList.newInstance();
                    for (int i = 0; i < reviews.size(); i++) {
                        GenericValue review = reviews.get(i);
                        String userLoginId = (String) review.get("userLoginId");
                        String productRating = review.get("productRating").toString();
                        Timestamp postTime = (Timestamp) review.get("postedDateTime");
                        String productReview = (String) review.get("productReview");
                        CommentList commentList = new CommentList(StringUtil.firstEndStr(userLoginId), productRating, UtilDateTime.timeStampToString(postTime, null, locale),
                                productReview, null, null);
                        commentLists.add(commentList);
                    }
                }
            }
            
        } catch (GenericServiceException e) {
            e.printStackTrace();
        }
        result.put("resultData", comment);
        return result;
    }
    
    public static Map<String, Object> DaoJia_GetTwoDimensionsRecommendSkusNew(DispatchContext dcx, Map<String, ? extends Object> context) {
        Map<String, Object> result = ServiceUtil.returnSuccess();
        String functionId = (String) context.get("functionId");
        String body = (String) context.get("body");
        String appVersion = (String) context.get("appVersion");
        String appName = (String) context.get("appName");
        String platCode = (String) context.get("platCode");
        String rand = (String) context.get("rand");
        
        Delegator delegator = dcx.getDelegator();
        BaseBody baseBody = BaseBody.objectFromData(body);
        String productId = baseBody.skuId;
        String storeId = baseBody.storeId;
        Locale locale = (Locale) context.get("locale");
        String type = "ALSO_BOUGHT";
        LocalDispatcher dispatcher = dcx.getDispatcher();
        String homePath = System.getProperty("ofbiz.home");
        String json = null;
        //获取店铺信息
        GenericValue productStore = null;
        String orgCode = null;
        String contactNumber = null;
        String facilityId = null;
        Map<String, Object> storeResult = null;
        try {
            storeResult = dispatcher.runSync("DaoJia_StoreInfo", UtilMisc.toMap("productStoreId", storeId));
        } catch (GenericServiceException e) {
            e.printStackTrace();
        }
        if (ServiceUtil.isSuccess(storeResult)) {
            orgCode = (String) storeResult.get("orgCode");
            contactNumber = (String) storeResult.get("contactNumber");
            productStore = (GenericValue) storeResult.get("productStore");
            facilityId = (String) ((GenericValue) storeResult.get("facility")).get("facilityId");
        }
        try {
            json = FileUtil.readString("utf-8", new File(homePath + "/daojia/daojia/config/product/productRecommend.json"));
        } catch (IOException e) {
            e.printStackTrace();
        }
        ProductRecommend recommend = ProductRecommend.objectFromData(json);
        try {
            //当前catalogId
            //根据store获取catalogId,对应的仓库
            GenericValue proStoreCatalog = EntityUtil.getFirst(EntityUtil.filterByDate(delegator.findByAnd("ProductStoreCatalog", UtilMisc.toMap("productStoreId", storeId))));
            String prodCatalogId = (String) proStoreCatalog.get("prodCatalogId");
            Map<String, Object> pRet = dispatcher.runSync("getAssociatedProducts", UtilMisc.toMap("productId", productId, "type", type, "checkViewAllow", true, "prodCatalogId", prodCatalogId, "sortDescending", true));
            if (ServiceUtil.isError(result)) {
                recommend.setMsg(ServiceUtil.getErrorMessage(result));
            }
            List<GenericValue> assocProducts = (List<GenericValue>) pRet.get("assocProducts");
            if (UtilValidate.isNotEmpty(assocProducts)) {
                Set<String> relateProIds = FastSet.newInstance();
                for (int i = 0; i < assocProducts.size(); i++) {
                    relateProIds.add((String) assocProducts.get(i).get("productIds"));
                }
                ProductInfo seed = recommend.result.dimensionsResultVOList.get(0).recommendSkuVOList.get(0);
                List<ProductInfo> productInfos = generateDaojiaProductInfos(storeId, locale, dispatcher, orgCode, seed, prodCatalogId, relateProIds, facilityId);
                if (UtilValidate.isNotEmpty(productInfos)) {
                    recommend.result.dimensionsResultVOList.get(0).recommendSkuVOList = productInfos;
                }
            }
            
        } catch (GenericServiceException e) {
            e.printStackTrace();
            result = ServiceUtil.returnError(e.getMessage());
            recommend.setMsg(ServiceUtil.getErrorMessage(result));
        } catch (GenericEntityException e) {
            e.printStackTrace();
            recommend.setMsg(ServiceUtil.getErrorMessage(result));
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        }
        
        result.put("resultData", recommend);
        return result;
    }
    
    public static List<ProductInfo> generateDaojiaProductInfos(String storeId, Locale locale, LocalDispatcher dispatcher, String orgCode, ProductInfo seed, String prodCatalogId, Set<String> productIds, String facilityId) throws GenericServiceException, IllegalAccessException, InvocationTargetException, NoSuchMethodException {
        Map<String, Object> prodSummaryResult = dispatcher.runSync("DaoJia_productSummary", UtilMisc.toMap("productIds", productIds, "webSiteId", "daojia", "prodCatalogId", prodCatalogId, "productStoreId", storeId));
        List<Map> productInfos = (List<Map>) prodSummaryResult.get("resultData");
        List<ProductInfo> productInfoList = FastList.newInstance();
        java.text.DecimalFormat df = new java.text.DecimalFormat("0.00");
        if (UtilValidate.isNotEmpty(productInfos)) {
            for (int i = 0; i < productInfos.size(); i++) {
                ProductInfo product = new ProductInfo();
                PropertyUtils.copyProperties(product, seed);
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
                String userActionSku = "{\"sku_id\":" + outProductId + ",\"solution\":\"chengdu_divide\",\"store_id\":" + storeId + "}";
                product.userActionSku = userActionSku;
                
                productInfoList.add(product);
            }
        }
        return productInfoList;
    }
    
    public static Map<String, Object> DaoJia_CheckBeforeToProductDetail(DispatchContext dcx, Map<String, ? extends Object> context) {
        Map<String, Object> result = ServiceUtil.returnSuccess();
        Locale locale = (Locale) context.get("locale");
        Map<String, Object> retMap = FastMap.newInstance();
        retMap.put("id", "check.checkBeforeToProductDetail");
        retMap.put("code", "0");
        retMap.put("msg", UtilProperties.getMessage("CommonUiLabels", "CommonSuccess", locale));
        retMap.put("result", 1);
        retMap.put("success", true);
        result.put("resultData", retMap);
        return result;
    }
    
    
   
}
