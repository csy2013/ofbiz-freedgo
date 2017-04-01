package com.yuaoq.yabiz.service.local.product;

import javolution.util.FastList;
import javolution.util.FastMap;
import org.ofbiz.base.util.*;
import org.ofbiz.common.geo.GeoWorker;
import org.ofbiz.content.data.OfbizUrlContentWrapper;
import org.ofbiz.entity.Delegator;
import org.ofbiz.entity.GenericEntityException;
import org.ofbiz.entity.GenericValue;
import org.ofbiz.entity.condition.EntityCondition;
import org.ofbiz.entity.condition.EntityOperator;
import org.ofbiz.entity.transaction.GenericTransactionException;
import org.ofbiz.entity.transaction.TransactionUtil;
import org.ofbiz.entity.util.EntityTypeUtil;
import org.ofbiz.entity.util.EntityUtil;
import org.ofbiz.party.contact.ContactMechWorker;
import org.ofbiz.product.catalog.CatalogWorker;
import org.ofbiz.product.product.ProductSearchSession;
import org.ofbiz.product.product.ProductWorker;
import org.ofbiz.service.DispatchContext;
import org.ofbiz.service.GenericServiceException;
import org.ofbiz.service.LocalDispatcher;
import org.ofbiz.service.ServiceUtil;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.math.BigDecimal;
import java.util.*;

/**
 * Created by changsy on 15/12/18.
 */
public class ProductServices {
    
    public static final String module = ProductServices.class.getName();
    public static final String resource_error = "ProductErrorUiLabels";
    public static final int DEFAULT_TX_TIMEOUT = 600;
    
    /**
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
     * @return "success" or "error"
     */
    public static String keywordSearch(HttpServletRequest request, HttpServletResponse response) {
        String errMsg = null;
        boolean beganTransaction = false;
        try {
            beganTransaction = TransactionUtil.begin(DEFAULT_TX_TIMEOUT);
            ProductSearchSession.checkDoKeywordOverride(request, response);
            Delegator delegator = (Delegator) request.getAttribute("delegator");
            Map<String, Object> parameters = FastMap.newInstance();
            ProductSearchSession.processSearchParameters(parameters, request);
            String prodCatalogId = CatalogWorker.getCurrentCatalogId(request);
            Map<String, Object> result = ProductSearchSession.getProductSearchResult(request, delegator, prodCatalogId);
            request.setAttribute("resultData", result);
        } catch (GenericTransactionException e) {
            Map<String, String> messageMap = UtilMisc.toMap("errSearchResult", e.toString());
            errMsg = UtilProperties.getMessage(resource_error, "productsearchevents.error_getting_search_results", messageMap, UtilHttp.getLocale(request));
            Debug.logError(e, errMsg, module);
            request.setAttribute("_ERROR_MESSAGE_", errMsg);
            return "error";
        } finally {
            try {
                TransactionUtil.commit(beganTransaction);
            } catch (GenericTransactionException e) {
                e.printStackTrace();
            }
        }
        return "success";
    }
    
    /**
     * @param dctx
     * @param context
     * @return
     */
    public static Map<String, Object> productReview(DispatchContext dctx, Map<String, ? extends Object> context) {
        String productId = (String) context.get("productId");
        String productStoreId = (String) context.get("productStoreId");
        Map<String, Object> result = ServiceUtil.returnSuccess();
        Map resultData = FastMap.newInstance();
        // get all product review in case of Purchase Order.
        try {
            GenericValue product = dctx.getDelegator().findByPrimaryKey("Product", UtilMisc.toMap("productId", productId));
            List<GenericValue> reviews = product.getRelatedCache("ProductReview", UtilMisc.toMap("statusId", "PRR_APPROVED", "productStoreId", productStoreId), UtilMisc.toList("-postedDateTime"));
            result.put("reviews", reviews);
            // get the average rating
            if (reviews != null && !reviews.isEmpty()) {
                List ratingReviews = EntityUtil.filterByAnd(reviews, UtilMisc.toList(EntityCondition.makeCondition("productRating", EntityOperator.NOT_EQUAL, null)));
                if (ratingReviews != null && (!ratingReviews.isEmpty())) {
                    result.put("averageRating", ProductWorker.getAverageProductRating(product, reviews, productStoreId));
                    result.put("numRatings", ratingReviews.size());
                }
            }
        } catch (GenericEntityException e) {
            e.printStackTrace();
            return ServiceUtil.returnFailure(e.getMessage());
        }
        return result;
    }
    
    
    /**
     * 获取所有的目录下得分类， 目录下浏览根目录
     *
     * @param dcx
     * @param context
     * @return
     */
    public static Map<String, Object> queryProductCategory(DispatchContext dcx, Map<String, ? extends Object> context) {
        Delegator delegator = dcx.getDelegator();
        Map<String, Object> result = ServiceUtil.returnSuccess();
        List<Map<String, Object>> completedTree = new ArrayList<Map<String, Object>>();
        try {
            List<GenericValue> prodCatalogs = delegator.findList("ProdCatalog", null, null, null, null, false);
            if (prodCatalogs != null) {
                for (GenericValue prodCatalog : prodCatalogs) {
                    Map prodCatalogMap = new HashMap();
                    prodCatalogMap.put("productCategoryId", prodCatalog.get("productCategoryId"));
                    prodCatalogMap.put("categoryName", prodCatalog.get("categoryName"));
                    prodCatalogMap.put("isCatalog", true);
                    prodCatalogMap.put("isCategoryType", false);
                    List<GenericValue> prodCatalogCategories = EntityUtil.filterByDate(delegator.findByAnd("ProdCatalogCategory", UtilMisc.toMap("prodCatalogId", prodCatalog.get("prodCatalogId"))));
                    if (prodCatalogCategories != null) {
                        prodCatalogMap.put("child", separateRootType(prodCatalogCategories));
                    }
                    completedTree.add(prodCatalogMap);
                    
                }
            }
        } catch (GenericEntityException e) {
            e.printStackTrace();
            return ServiceUtil.returnFailure(e.getMessage());
        }
        
        result.put("categories", completedTree);
        return result;
    }
    
    /**
     * 获取商品的可用库存
     *
     * @param dcx
     * @param context
     * @return
     */
    public static Map<String, Object> queryAvailableInventoryForProduct(DispatchContext dcx, Map<String, ? extends Object> context) {
        Delegator delegator = dcx.getDelegator();
        String productId = (String) context.get("productId");
        Map<String, Object> result = ServiceUtil.returnSuccess();
        GenericValue product = null;
        BigDecimal availableInventory = BigDecimal.ZERO;
        
        try {
            product = delegator.findByPrimaryKeyCache("Product", UtilMisc.toMap("productId", productId));
            boolean isMarketingPackage = EntityTypeUtil.hasParentType(delegator, "ProductType", "productTypeId", (String) product.get("productTypeId"), "parentTypeId", "MARKETING_PKG");
            if (isMarketingPackage) {
                LocalDispatcher dispatcher = dcx.getDispatcher();
                Map<String, Object> resultOutput = null;
                try {
                    resultOutput = dispatcher.runSync("getMktgPackagesAvailable", UtilMisc.toMap("productId", productId));
                    availableInventory = (BigDecimal) resultOutput.get("availableToPromiseTotal");
                    
                } catch (GenericServiceException e) {
                    Debug.logError(e, "Error calling the getMktgPackagesAvailable when query queryVailableInventoryForProduct: " + e.toString(), module);
                    return ServiceUtil.returnError(e.getMessage());
                }
                
            } else {
                List<GenericValue> facilities = delegator.findList("ProductFacility",
                        EntityCondition.makeCondition(UtilMisc.toMap("productId", productId)), null, null, null, false);
                
                if (facilities != null) {
                    for (GenericValue facility : facilities) {
                        BigDecimal lastInventoryCount = (BigDecimal) facility.get("lastInventoryCount");
                        if (lastInventoryCount != null) {
                            availableInventory.add(lastInventoryCount);
                        }
                    }
                    
                }
            }
        } catch (GenericEntityException e) {
            e.printStackTrace();
        }
        result.put("availableInventory", availableInventory);
        return result;
    }
    
    
    public static List<Map<String, Object>> separateRootType(List<GenericValue> prodCatalogCategories) throws GenericEntityException {
        if (prodCatalogCategories != null) {
            List<Map<String, Object>> prodRootTypeTree = new ArrayList<Map<String, Object>>();
            for (GenericValue prodCatalogCategory : prodCatalogCategories) {
                Map prodCateMap = new HashMap();
                GenericValue productCategory = prodCatalogCategory.getRelatedOne("ProductCategory");
                prodCateMap.put("productCategoryId", productCategory.getString("productCategoryId"));
                prodCateMap.put("categoryName", productCategory.getString("categoryName"));
                prodCateMap.put("isCatalog", false);
                prodCateMap.put("isCategoryType", true);
                prodRootTypeTree.add(prodCateMap);
            }
            return prodRootTypeTree;
        }
        return null;
    }
    
    public static String lookupProduct(HttpServletRequest request, HttpServletResponse response) {
        String productName = request.getParameter("name");
        String pageNo = request.getParameter("pageNo");
        LocalDispatcher dispatcher = (LocalDispatcher) request.getAttribute("dispatcher");
        Map serviceIn = FastMap.newInstance();
        serviceIn.put("entityName", "Product");
        if (UtilValidate.isNotEmpty(productName)) {
            serviceIn.put("inputFields", UtilMisc.toMap("productName", productName, "productName_op", "contains", "productName_ic", "Y"));
            
        } else {
            serviceIn.put("inputFields", UtilMisc.toMap("productName", ""));
        }
        if (UtilValidate.isEmpty(pageNo)) {
            serviceIn.put("viewIndex", 0);
        } else {
            serviceIn.put("viewIndex", Integer.parseInt(pageNo));
        }
        serviceIn.put("noConditionFind", "Y");
        serviceIn.put("viewSize", 10);
        try {
            Map<String, Object> result = dispatcher.runSync("performFindList", serviceIn);
            request.setAttribute("resultData", result);
        } catch (GenericServiceException e) {
            e.printStackTrace();
        }
        return "success";
    }
    
    /**
     * 根据店铺名称获取店铺，LOGO，地址信息
     *
     * @param request
     * @param response
     * @return
     */
    public static String lookupStore(HttpServletRequest request, HttpServletResponse response) {
        String storeName = request.getParameter("name");
        String pageNo = request.getParameter("pageNo");
        LocalDispatcher dispatcher = (LocalDispatcher) request.getAttribute("dispatcher");
        Delegator delegator = (Delegator) request.getAttribute("delegator");
        Map<String, Object> result = ServiceUtil.returnSuccess();
        Locale locale = request.getLocale();
        Map serviceIn = FastMap.newInstance();
        serviceIn.put("entityName", "ProductStore");
        if (UtilValidate.isNotEmpty(storeName)) {
            serviceIn.put("inputFields", UtilMisc.toMap("storeName", storeName, "storeName_op", "contains", "storeName_ic", "Y"));
            
        } else {
            serviceIn.put("inputFields", UtilMisc.toMap("storeName", ""));
        }
        if (UtilValidate.isEmpty(pageNo)) {
            serviceIn.put("viewIndex", 0);
        } else {
            serviceIn.put("viewIndex", Integer.parseInt(pageNo));
        }
        serviceIn.put("noConditionFind", "Y");
        serviceIn.put("viewSize", 10);
        try {
            result = dispatcher.runSync("performFindList", serviceIn);
            if (ServiceUtil.isSuccess(result)) {
                List<GenericValue> stores = (List<GenericValue>) result.get("list");
                if (UtilValidate.isNotEmpty(stores)) {
                    List<Map<String, String>> storeMaps = FastList.newInstance();
                    for (int i = 0; i < stores.size(); i++) {
                        GenericValue store = stores.get(i);
                        Map<String, String> storeMap = FastMap.newInstance();
                        String productStoreId = store.getString("productStoreId");
                        storeMap.put("storeId", productStoreId);
                        storeMap.put("storeName", store.getString("storeName"));
                        List<Map<String, Object>> contactMechMap = ContactMechWorker.getProductStoreContactMechValueMaps(delegator, productStoreId, false, null);
                        if (UtilValidate.isNotEmpty(contactMechMap)) {
                            for (int j = 0; j < contactMechMap.size(); j++) {
                                Map<String, Object> contactMap = contactMechMap.get(j);
                                if (contactMap.containsKey("postalAddress")) {
                                    GenericValue postalAddress = (GenericValue) contactMap.get("postalAddress");
                                    String cityCode = (String) postalAddress.get("city");
                                    storeMap.put("cityName", GeoWorker.getGeoNameById(cityCode, delegator));
                                    String countyCode = (String) postalAddress.get("countyGeoId");
                                    storeMap.put("countyName", GeoWorker.getGeoNameById(countyCode, delegator));
                                }
                            }
                        }
                        
                        GenericValue storeContent = EntityUtil.getFirst(EntityUtil.filterByDate(delegator.findByAnd("ProductStoreContent", UtilMisc.toMap("productStoreId", productStoreId))));
                        if (UtilValidate.isNotEmpty(storeContent)) {
                            String contentId = storeContent.getString("contentId");
                            String imgUrl = OfbizUrlContentWrapper.getOfbizUrlContentAsText(storeContent.getRelatedOne("Content"), locale, dispatcher);
                            String baseUrl = UtilProperties.getMessage("content.properties", "daojia.baseUrl", locale);
                            storeMap.put("logo", baseUrl + imgUrl);
                        }
                        storeMaps.add(storeMap);
                    }
                    result.put("list", storeMaps);
                }
            }
            
        } catch (GenericServiceException e) {
            e.printStackTrace();
        } catch (GenericEntityException e) {
            e.printStackTrace();
        }
        request.setAttribute("resultData",result);
        return "success";
    }
}
