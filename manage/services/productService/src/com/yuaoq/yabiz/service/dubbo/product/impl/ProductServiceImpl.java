package com.yuaoq.yabiz.service.dubbo.product.impl;

import com.yuaoq.yabiz.dubbo.provider.service.ServiceImpl;
import com.yuaoq.yabiz.service.dubbo.product.api.service.ProductService;
import javolution.util.FastMap;
import org.ofbiz.entity.Delegator;
import org.ofbiz.product.catalog.CatalogWorker;
import org.ofbiz.product.product.ProductSearchSession;
import org.ofbiz.service.GenericServiceException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Map;

/**
 * Created by tusm on 15/11/24.
 */
public class ProductServiceImpl extends ServiceImpl implements ProductService {


    public static final String module = ProductServiceImpl.class.getName();
    public static final String resource_error = "ProductErrorUiLabels";

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
    public   Map<String, Object> keywordSearch(HttpServletRequest request, HttpServletResponse response) {

        ProductSearchSession.checkDoKeywordOverride(request, response);
        Delegator delegator = (Delegator) request.getAttribute("delegator");
        Map<String, Object> parameters = FastMap.newInstance();
        ProductSearchSession.processSearchParameters(parameters, request);
        String prodCatalogId = CatalogWorker.getCurrentCatalogId(request);
        Map<String, Object> result = ProductSearchSession.getProductSearchResult(request, delegator, prodCatalogId);

        return result;
    }


    /**
     * 产品基本信息
     * @param context
     * @return
     * @throws GenericServiceException
     */
    public  Map<String, Object> productSummary( Map<String, ? extends Object> context) throws GenericServiceException {
        setDispatcherName((String)context.get("dispatcherName"));
        setDelegatorName((String)context.get("delegatorName"));
        //checkExportFlag("productSummary");
        // create the LocalDispatcher
        context.remove("dispatcherName");
        context.remove("delegatorName");
        Map<String, Object> returnMap =  getDispatcher().runSync("productSummary", context);
        return returnMap;
    }

    /**
     *

     * @param context
     * @return
     */
    public    Map<String, Object> productContent(Map<String, ? extends Object> context ) throws GenericServiceException {
        setDispatcherName((String)context.get("dispatcherName"));
        setDelegatorName((String)context.get("delegatorName"));
        //checkExportFlag("productSummary");
        // create the LocalDispatcher
        context.remove("dispatcherName");
        context.remove("delegatorName");
        Map<String, Object> returnMap =  getDispatcher().runSync("productContent", context);
        return returnMap;
    }


    /**
     *产品评论

     * @param context
     * @return
     */
    public   Map<String, Object> productReview( Map<String, ? extends Object> context ) throws GenericServiceException {

        setDispatcherName((String)context.get("dispatcherName"));
        setDelegatorName((String)context.get("delegatorName"));
        //checkExportFlag("productSummary");
        // create the LocalDispatcher
        context.remove("dispatcherName");
        context.remove("delegatorName");
        Map<String, Object> result =  getDispatcher().runSync("productReview", context);
        return result;
    }

    /**
     * 获取所有的目录下得分类， 目录下浏览根目录

     * @param context
     * @return
     */
    public  Map<String,Object> productCategoryList( Map<String,? extends Object> context) throws GenericServiceException {
        setDispatcherName((String)context.get("dispatcherName"));
        setDelegatorName((String)context.get("delegatorName"));
        //checkExportFlag("productSummary");
        // create the LocalDispatcher
        context.remove("dispatcherName");
        context.remove("delegatorName");
        Map<String, Object> result =  getDispatcher().runSync("productCategoryList", context);
        return result;
    }


}
