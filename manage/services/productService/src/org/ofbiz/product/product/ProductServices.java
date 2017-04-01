/*******************************************************************************
 * Licensed to the Apache Software Foundation (ASF) under one
 * or more contributor license agreements.  See the NOTICE file
 * distributed with this work for additional information
 * regarding copyright ownership.  The ASF licenses this file
 * to you under the Apache License, Version 2.0 (the
 * "License"); you may not use this file except in compliance
 * with the License.  You may obtain a copy of the License at
 * <p>
 * http://www.apache.org/licenses/LICENSE-2.0
 * <p>
 * Unless required by applicable law or agreed to in writing,
 * software distributed under the License is distributed on an
 * "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
 * KIND, either express or implied.  See the License for the
 * specific language governing permissions and limitations
 * under the License.
 *******************************************************************************/
package org.ofbiz.product.product;

import javolution.util.FastList;
import javolution.util.FastMap;
import javolution.util.FastSet;
import org.jdom.JDOMException;
import org.ofbiz.base.util.*;
import org.ofbiz.base.util.string.FlexibleStringExpander;
import org.ofbiz.entity.Delegator;
import org.ofbiz.entity.GenericEntityException;
import org.ofbiz.entity.GenericValue;
import org.ofbiz.entity.condition.EntityCondition;
import org.ofbiz.entity.condition.EntityJoinOperator;
import org.ofbiz.entity.condition.EntityOperator;
import org.ofbiz.entity.util.EntityUtil;
import org.ofbiz.product.catalog.CatalogWorker;
import org.ofbiz.product.category.CategoryWorker;
import org.ofbiz.product.config.ProductConfigWorker;
import org.ofbiz.product.config.ProductConfigWrapper;
import org.ofbiz.product.image.ScaleImage;
import org.ofbiz.service.*;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.math.BigDecimal;
import java.nio.ByteBuffer;
import java.sql.Timestamp;
import java.util.*;

/**
 * Product Services
 */
public class ProductServices {

    public static final String module = org.ofbiz.product.product.ProductServices.class.getName();
    public static final String resource = "ProductUiLabels";
    public static final String resourceError = "ProductErrorUiLabels";

    /**
     * Creates a Collection of product entities which are variant products from the specified product ID.
     */
    public static Map<String, Object> prodFindAllVariants(DispatchContext dctx, Map<String, ? extends Object> context) {
        // * String productId      -- Parent (virtual) product ID
        Map<String, Object> subContext = UtilMisc.makeMapWritable(context);
        subContext.put("type", "PRODUCT_VARIANT");
        return prodFindAssociatedByType(dctx, subContext);
    }

    /**
     * Finds a specific product or products which contain the selected features.
     */
    public static Map<String, Object> prodFindSelectedVariant(DispatchContext dctx, Map<String, ? extends Object> context) {
        // * String productId      -- Parent (virtual) product ID
        // * Map selectedFeatures  -- Selected features
        Delegator delegator = dctx.getDelegator();
        Locale locale = (Locale) context.get("locale");
        Map<String, String> selectedFeatures = UtilGenerics.checkMap(context.get("selectedFeatures"));
        List<GenericValue> products = FastList.newInstance();
        // All the variants for this products are retrieved
        Map<String, Object> resVariants = prodFindAllVariants(dctx, context);
        List<GenericValue> variants = UtilGenerics.checkList(resVariants.get("assocProducts"));
        for (GenericValue oneVariant : variants) {
            // For every variant, all the standard features are retrieved
            Map<String, String> feaContext = FastMap.newInstance();
            feaContext.put("productId", oneVariant.getString("productIdTo"));
            feaContext.put("type", "STANDARD_FEATURE");
            Map<String, Object> resFeatures = prodGetFeatures(dctx, feaContext);
            List<GenericValue> features = UtilGenerics.checkList(resFeatures.get("productFeatures"));
            boolean variantFound = true;
            // The variant is discarded if at least one of its standard features
            // has the same type of one of the selected features but a different feature id.
            // Example:
            // Input: (COLOR, Black), (SIZE, Small)
            // Variant1: (COLOR, Black), (SIZE, Large) --> nok
            // Variant2: (COLOR, Black), (SIZE, Small) --> ok
            // Variant3: (COLOR, Black), (SIZE, Small), (IMAGE, SkyLine) --> ok
            // Variant4: (COLOR, Black), (IMAGE, SkyLine) --> ok
            for (GenericValue oneFeature : features) {
                if (selectedFeatures.containsKey(oneFeature.getString("productFeatureTypeId"))) {
                    if (!selectedFeatures.containsValue(oneFeature.getString("productFeatureId"))) {
                        variantFound = false;
                        break;
                    }
                }
            }
            if (variantFound) {
                try {
                    products.add(delegator.findByPrimaryKey("Product", UtilMisc.toMap("productId", oneVariant.getString("productIdTo"))));
                } catch (GenericEntityException e) {
                    Map<String, String> messageMap = UtilMisc.toMap("errProductFeatures", e.toString());
                    String errMsg = UtilProperties.getMessage(resourceError, "productservices.problem_reading_product_features_errors", messageMap, locale);
                    Debug.logError(e, errMsg, module);
                    return ServiceUtil.returnError(errMsg);
                }
            }
        }

        Map<String, Object> result = ServiceUtil.returnSuccess();
        result.put("products", products);
        return result;
    }

    /**
     * Finds product variants based on a product ID and a distinct feature.
     */
    public static Map<String, Object> prodFindDistinctVariants(DispatchContext dctx, Map<String, ? extends Object> context) {
        // * String productId      -- Parent (virtual) product ID
        // * String feature        -- Distinct feature name
        //TODO This service has not yet been implemented.
        return ServiceUtil.returnFailure();
    }

    /**
     * Finds a Set of feature types in sequence.
     */
    public static Map<String, Object> prodFindFeatureTypes(DispatchContext dctx, Map<String, ? extends Object> context) {
        // * String productId      -- Product ID to look up feature types
        Delegator delegator = dctx.getDelegator();
        String productId = (String) context.get("productId");
        String productFeatureApplTypeId = (String) context.get("productFeatureApplTypeId");
        if (UtilValidate.isEmpty(productFeatureApplTypeId)) {
            productFeatureApplTypeId = "SELECTABLE_FEATURE";
        }
        Locale locale = (Locale) context.get("locale");
        String errMsg = null;
        Set<String> featureSet = new LinkedHashSet<String>();

        try {
            Map<String, String> fields = UtilMisc.toMap("productId", productId, "productFeatureApplTypeId", productFeatureApplTypeId);
            List<String> order = UtilMisc.toList("sequenceNum", "productFeatureTypeId");
            List<GenericValue> features = delegator.findByAndCache("ProductFeatureAndAppl", fields, order);
            for (GenericValue v : features) {
                featureSet.add(v.getString("productFeatureTypeId"));
            }
            //if (Debug.infoOn()) Debug.logInfo("" + featureSet, module);
        } catch (GenericEntityException e) {
            Map<String, String> messageMap = UtilMisc.toMap("errProductFeatures", e.toString());
            errMsg = UtilProperties.getMessage(resourceError, "productservices.problem_reading_product_features_errors", messageMap, locale);
            Debug.logError(e, errMsg, module);
            return ServiceUtil.returnError(errMsg);
        }

        if (featureSet.size() == 0) {
            errMsg = UtilProperties.getMessage(resourceError, "productservices.problem_reading_product_features", locale);
            // ToDo DO 2004-02-23 Where should the errMsg go?
            Debug.logWarning(errMsg + " for product " + productId, module);
            //return ServiceUtil.returnError(errMsg);
        }
        Map<String, Object> result = ServiceUtil.returnSuccess();
        result.put("featureSet", featureSet);
        return result;
    }

    /**
     * Builds a variant feature tree.
     */
    public static Map<String, Object> prodMakeFeatureTree(DispatchContext dctx, Map<String, ? extends Object> context) {
        // * String productId      -- Parent (virtual) product ID
        // * List featureOrder     -- Order of features
        // * Boolean checkInventory-- To calculate available inventory.
        // * String productStoreId -- Product Store ID for Inventory
        String productStoreId = (String) context.get("productStoreId");
        Locale locale = (Locale) context.get("locale");

        Delegator delegator = dctx.getDelegator();
        LocalDispatcher dispatcher = dctx.getDispatcher();
        Map<String, Object> result = FastMap.newInstance();
        List featureOrder = UtilMisc.makeListWritable(UtilGenerics.checkCollection(context.get("featureOrder")));
        if (UtilValidate.isEmpty(featureOrder)) {
            return ServiceUtil.returnError(UtilProperties.getMessage(resource,
                    "ProductFeatureTreeCannotFindFeaturesList", locale));
        }
        List<GenericValue> variants = UtilGenerics.checkList(prodFindAllVariants(dctx, context).get("assocProducts"));
        List<String> virtualVariant = FastList.newInstance();
        if (UtilValidate.isEmpty(variants)) {
            return ServiceUtil.returnSuccess();
        }
        List<String> items = FastList.newInstance();
        List<GenericValue> outOfStockItems = FastList.newInstance();
        for (GenericValue variant : variants) {
            String productIdTo = variant.getString("productIdTo");
            // first check to see if intro and discontinue dates are within range
            GenericValue productTo = null;

            try {
                productTo = delegator.findByPrimaryKeyCache("Product", UtilMisc.toMap("productId", productIdTo));
            } catch (GenericEntityException e) {
                Debug.logError(e, module);
                Map<String, String> messageMap = UtilMisc.toMap("productIdTo", productIdTo, "errMessage", e.toString());
                return ServiceUtil.returnError(UtilProperties.getMessage(resourceError,
                        "productservices.error_finding_associated_variant_with_ID_error", messageMap, locale));
            }
            if (productTo == null) {
                Debug.logWarning("Could not find associated variant with ID " + productIdTo + ", not showing in list", module);
                continue;
            }

            Timestamp nowTimestamp = UtilDateTime.nowTimestamp();

            // check to see if introductionDate hasn't passed yet
            if (productTo.get("introductionDate") != null && nowTimestamp.before(productTo.getTimestamp("introductionDate"))) {
                if (Debug.verboseOn()) {
                    String excMsg = "Tried to view the Product " + productTo.getString("productName") +
                            " (productId: " + productTo.getString("productId") + ") as a variant. This product has not yet been made available for sale, so not adding for view.";

                    Debug.logVerbose(excMsg, module);
                }
                continue;
            }

            // check to see if salesDiscontinuationDate has passed
            if (productTo.get("salesDiscontinuationDate") != null && nowTimestamp.after(productTo.getTimestamp("salesDiscontinuationDate"))) {
                if (Debug.verboseOn()) {
                    String excMsg = "Tried to view the Product " + productTo.getString("productName") +
                            " (productId: " + productTo.getString("productId") + ") as a variant. This product is no longer available for sale, so not adding for view.";

                    Debug.logVerbose(excMsg, module);
                }
                continue;
            }

            // next check inventory for each item: if inventory is not required or is available
            Boolean checkInventory = (Boolean) context.get("checkInventory");
            try {
                if (checkInventory) {
                    Map<String, Object> invReqResult = dispatcher.runSync("isStoreInventoryAvailableOrNotRequired", UtilMisc.<String, Object>toMap("productStoreId", productStoreId, "productId", productIdTo, "quantity", BigDecimal.ONE));
                    if (ServiceUtil.isError(invReqResult)) {
                        return ServiceUtil.returnError(UtilProperties.getMessage(resource,
                                "ProductFeatureTreeCannotCallIsStoreInventoryRequired", locale), null, null, invReqResult);
                    } else if ("Y".equals(invReqResult.get("availableOrNotRequired"))) {
                        items.add(productIdTo);
                        if (productTo.getString("isVirtual") != null && productTo.getString("isVirtual").equals("Y")) {
                            virtualVariant.add(productIdTo);
                        }
                    } else {
                        outOfStockItems.add(productTo);
                    }
                } else {
                    items.add(productIdTo);
                    if (productTo.getString("isVirtual") != null && productTo.getString("isVirtual").equals("Y")) {
                        virtualVariant.add(productIdTo);
                    }
                }
            } catch (GenericServiceException e) {
                Debug.logError(e, "Error calling the isStoreInventoryRequired when building the variant product tree: " + e.toString(), module);
                return ServiceUtil.returnError(UtilProperties.getMessage(resource,
                        "ProductFeatureTreeCannotCallIsStoreInventoryRequired", locale));
            }
        }

        String productId = (String) context.get("productId");

        // Make the selectable feature list
        List<GenericValue> selectableFeatures = null;
        try {
            Map<String, String> fields = UtilMisc.toMap("productId", productId, "productFeatureApplTypeId", "SELECTABLE_FEATURE");
            List<String> sort = UtilMisc.toList("sequenceNum");

            selectableFeatures = delegator.findByAndCache("ProductFeatureAndAppl", fields, sort);
            selectableFeatures = EntityUtil.filterByDate(selectableFeatures, true);
        } catch (GenericEntityException e) {
            Debug.logError(e, module);
            return ServiceUtil.returnError(UtilProperties.getMessage(resourceError,
                    "productservices.empty_list_of_selectable_features_found", locale));
        }
        Map<String, List<String>> features = FastMap.newInstance();
        for (GenericValue v : selectableFeatures) {
            String featureType = v.getString("productFeatureTypeId");
            String feature = v.getString("description");

            if (!features.containsKey(featureType)) {
                List<String> featureList = FastList.newInstance();
                featureList.add(feature);
                features.put(featureType, featureList);
            } else {
                List<String> featureList = features.get(featureType);
                featureList.add(feature);
                features.put(featureType, featureList);
            }
        }

        Map<String, Object> tree = null;
        try {
            tree = makeGroup(delegator, features, items, featureOrder, 0);
        } catch (Exception e) {
            Debug.logError(e, module);
            return ServiceUtil.returnError(e.getMessage());
        }
        if (UtilValidate.isEmpty(tree)) {
            result.put(ModelService.RESPONSE_MESSAGE, ModelService.RESPOND_ERROR);
            result.put(ModelService.ERROR_MESSAGE, UtilProperties.getMessage(resourceError,
                    "productservices.feature_grouping_came_back_empty", locale));
        } else {
            result.put("variantTree", tree);
            result.put("virtualVariant", virtualVariant);
            result.put(ModelService.RESPONSE_MESSAGE, ModelService.RESPOND_SUCCESS);
        }

        //add by changsy 2015-9-29 增加变形选择

        Map<String, List<GenericValue>> treeMap = null;
        try {
            treeMap = makeGroupMap(delegator, items);
        } catch (Exception e) {
            Debug.logError(e, module);
            return ServiceUtil.returnError(e.getMessage());
        }
        if (UtilValidate.isEmpty(treeMap)) {
            result.put(ModelService.RESPONSE_MESSAGE, ModelService.RESPOND_ERROR);
            result.put(ModelService.ERROR_MESSAGE, UtilProperties.getMessage(resourceError,
                    "productservices.feature_grouping_came_back_empty", locale));
        } else {
            result.put("variantTreeChoose", treeMap);
            result.put(ModelService.RESPONSE_MESSAGE, ModelService.RESPOND_SUCCESS);
        }


        //add by changsy 2015-9-29 增加变形选择

        Map<String, GenericValue> sample = null;
        try {
            sample = makeVariantSample(dctx.getDelegator(), features, items, (String) featureOrder.get(0));
        } catch (Exception e) {
            return ServiceUtil.returnError(e.getMessage());
        }

        if (outOfStockItems.size() > 0) {
            result.put("unavailableVariants", outOfStockItems);
        }
        result.put("variantSample", sample);
        result.put(ModelService.RESPONSE_MESSAGE, ModelService.RESPOND_SUCCESS);

        return result;
    }


    /**
     * Builds a variant feature tree.
     */
    public static Map<String, Object> findVariantProductByFeatureList(DispatchContext dctx, Map<String, ? extends Object> context) {
        // * String productId      -- Parent (virtual) product ID
        // * Boolean checkInventory-- To calculate available inventory.
        // * String productStoreId -- Product Store ID for Inventory
        String productStoreId = (String) context.get("productStoreId");
        Locale locale = (Locale) context.get("locale");
        String productId = (String) context.get("productId");
        Map<String, String> selectFeatures = (Map<String, String>) context.get("selectFeatures");

        Delegator delegator = dctx.getDelegator();
        LocalDispatcher dispatcher = dctx.getDispatcher();
        Map<String, Object> result = FastMap.newInstance();
        //根据虚拟产品查询出对应的变形产品
        List<GenericValue> variants = UtilGenerics.checkList(prodFindAllVariants(dctx, context).get("assocProducts"));
        List<String> virtualVariant = FastList.newInstance();

        if (UtilValidate.isEmpty(variants)) {
            return ServiceUtil.returnSuccess();
        }
        List<String> items = FastList.newInstance();
        List<GenericValue> outOfStockItems = FastList.newInstance();


        //查找每个变形产品是否有效。
        for (GenericValue variant : variants) {
            String productIdTo = variant.getString("productIdTo");

            // first check to see if intro and discontinue dates are within range
            GenericValue productTo = null;

            try {
                productTo = delegator.findByPrimaryKeyCache("Product", UtilMisc.toMap("productId", productIdTo));
            } catch (GenericEntityException e) {
                Debug.logError(e, module);
                Map<String, String> messageMap = UtilMisc.toMap("productIdTo", productIdTo, "errMessage", e.toString());
                return ServiceUtil.returnError(UtilProperties.getMessage(resourceError,
                        "productservices.error_finding_associated_variant_with_ID_error", messageMap, locale));
            }
            if (productTo == null) {
                Debug.logWarning("Could not find associated variant with ID " + productIdTo + ", not showing in list", module);
                continue;
            }

            Timestamp nowTimestamp = UtilDateTime.nowTimestamp();

            // check to see if introductionDate hasn't passed yet
            if (productTo.get("introductionDate") != null && nowTimestamp.before(productTo.getTimestamp("introductionDate"))) {
                if (Debug.verboseOn()) {
                    String excMsg = "Tried to view the Product " + productTo.getString("productName") +
                            " (productId: " + productTo.getString("productId") + ") as a variant. This product has not yet been made available for sale, so not adding for view.";

                    Debug.logVerbose(excMsg, module);
                }
                continue;
            }

            // check to see if salesDiscontinuationDate has passed
            if (productTo.get("salesDiscontinuationDate") != null && nowTimestamp.after(productTo.getTimestamp("salesDiscontinuationDate"))) {
                if (Debug.verboseOn()) {
                    String excMsg = "Tried to view the Product " + productTo.getString("productName") +
                            " (productId: " + productTo.getString("productId") + ") as a variant. This product is no longer available for sale, so not adding for view.";

                    Debug.logVerbose(excMsg, module);
                }
                continue;
            }

            // next check inventory for each item: if inventory is not required or is available
            Boolean checkInventory = (Boolean) context.get("checkInventory");
            try {
                if (checkInventory) {
                    Map<String, Object> invReqResult = dispatcher.runSync("isStoreInventoryAvailableOrNotRequired", UtilMisc.<String, Object>toMap("productStoreId", productStoreId, "productId", productIdTo, "quantity", BigDecimal.ONE));
                    if (ServiceUtil.isError(invReqResult)) {
                        return ServiceUtil.returnError(UtilProperties.getMessage(resource,
                                "ProductFeatureTreeCannotCallIsStoreInventoryRequired", locale), null, null, invReqResult);
                    } else if ("Y".equals(invReqResult.get("availableOrNotRequired"))) {
                        items.add(productIdTo);
                        if (productTo.getString("isVirtual") != null && productTo.getString("isVirtual").equals("Y")) {
                            virtualVariant.add(productIdTo);
                        }
                    } else {
                        outOfStockItems.add(productTo);
                    }
                } else {
                    items.add(productIdTo);
                    if (productTo.getString("isVirtual") != null && productTo.getString("isVirtual").equals("Y")) {
                        virtualVariant.add(productIdTo);
                    }
                }
            } catch (GenericServiceException e) {
                Debug.logError(e, "Error calling the isStoreInventoryRequired when building the variant product tree: " + e.toString(), module);
                return ServiceUtil.returnError(UtilProperties.getMessage(resource,
                        "ProductFeatureTreeCannotCallIsStoreInventoryRequired", locale));
            }
        }
        //根据变形产品查询出来对应的featureTypeId
        if (!items.isEmpty()) {
            for (int i = 0; i < items.size(); i++) {
                String asscoProductId = items.get(i);
                List<GenericValue> features = null;

                try {
                    Map<String, String> fields = UtilMisc.toMap("productId", asscoProductId, "productFeatureApplTypeId", "STANDARD_FEATURE");
                    List<String> sort = UtilMisc.toList("sequenceNum");

                    // get the features and filter out expired dates
                    features = delegator.findByAndCache("ProductFeatureAndAppl", fields, sort);
                    features = EntityUtil.filterByDate(features, true);
                    boolean correct = true;
                    if (!features.isEmpty()) {
                        for (int j = 0; j < features.size(); j++) {
                            GenericValue feature = features.get(j);
                            if (selectFeatures.containsKey(feature.get("productFeatureTypeId"))) {
                                if (!selectFeatures.get(feature.get("productFeatureTypeId")).equals(feature.get("productFeatureId"))) {
                                    correct = false;
                                }
                            } else {
                                correct = false;
                            }
                        }
                    }

                    if (correct) {
                        GenericValue genericValue = delegator.findByPrimaryKey("Product", UtilMisc.toMap("productId", asscoProductId));
                        result.put("variantProduct", genericValue);
                        break;
                    }

                } catch (GenericEntityException e) {
                    throw new IllegalStateException("Problem reading relation: " + e.getMessage());
                }

            }
        }


        result.put(ModelService.RESPONSE_MESSAGE, ModelService.RESPOND_SUCCESS);

        return result;
    }

    /**
     * Gets the product features of a product.
     */
    public static Map<String, Object> prodGetFeatures(DispatchContext dctx, Map<String, ? extends Object> context) {
        // * String productId      -- Product ID to find
        // * String type           -- Type of feature (STANDARD_FEATURE, SELECTABLE_FEATURE)
        // * String distinct       -- Distinct feature (SIZE, COLOR)
        Delegator delegator = dctx.getDelegator();
        Map<String, Object> result = FastMap.newInstance();
        String productId = (String) context.get("productId");
        String distinct = (String) context.get("distinct");
        String type = (String) context.get("type");
        Locale locale = (Locale) context.get("locale");
        String errMsg = null;
        List<GenericValue> features = null;

        try {
            Map<String, String> fields = UtilMisc.toMap("productId", productId);
            List<String> order = UtilMisc.toList("sequenceNum", "productFeatureTypeId");

            if (distinct != null) fields.put("productFeatureTypeId", distinct);
            if (type != null) fields.put("productFeatureApplTypeId", type);
            features = delegator.findByAndCache("ProductFeatureAndAppl", fields, order);
            result.put("productFeatures", features);
            result.put(ModelService.RESPONSE_MESSAGE, ModelService.RESPOND_SUCCESS);
        } catch (GenericEntityException e) {
            Map<String, String> messageMap = UtilMisc.toMap("errMessage", e.toString());
            errMsg = UtilProperties.getMessage(resourceError,
                    "productservices.problem_reading_product_feature_entity", messageMap, locale);
            result.put(ModelService.RESPONSE_MESSAGE, ModelService.RESPOND_ERROR);
            result.put(ModelService.ERROR_MESSAGE, errMsg);
        }
        return result;
    }

    /**
     * Finds a product by product ID.
     */
    public static Map<String, Object> prodFindProduct(DispatchContext dctx, Map<String, ? extends Object> context) {
        // * String productId      -- Product ID to find
        Delegator delegator = dctx.getDelegator();
        Map<String, Object> result = FastMap.newInstance();
        String productId = (String) context.get("productId");
        Locale locale = (Locale) context.get("locale");
        String errMsg = null;

        if (UtilValidate.isEmpty(productId)) {
            errMsg = UtilProperties.getMessage(resourceError,
                    "productservices.invalid_productId_passed", locale);
            result.put(ModelService.RESPONSE_MESSAGE, ModelService.RESPOND_ERROR);
            result.put(ModelService.ERROR_MESSAGE, errMsg);
            return result;
        }

        try {
            GenericValue product = delegator.findByPrimaryKeyCache("Product", UtilMisc.toMap("productId", productId));
            GenericValue mainProduct = product;

            if (product.get("isVariant") != null && product.getString("isVariant").equalsIgnoreCase("Y")) {
                List<GenericValue> c = product.getRelatedByAndCache("AssocProductAssoc",
                        UtilMisc.toMap("productAssocTypeId", "PRODUCT_VARIANT"));
                //if (Debug.infoOn()) Debug.logInfo("Found related: " + c, module);
                c = EntityUtil.filterByDate(c);
                //if (Debug.infoOn()) Debug.logInfo("Found Filtered related: " + c, module);
                if (c.size() > 0) {
                    GenericValue asV = c.iterator().next();

                    //if (Debug.infoOn()) Debug.logInfo("ASV: " + asV, module);
                    mainProduct = asV.getRelatedOneCache("MainProduct");
                    //if (Debug.infoOn()) Debug.logInfo("Main product = " + mainProduct, module);
                }
            }
            result.put("product", mainProduct);
            result.put(ModelService.RESPONSE_MESSAGE, ModelService.RESPOND_SUCCESS);
        } catch (GenericEntityException e) {
            e.printStackTrace();
            Map<String, String> messageMap = UtilMisc.toMap("errMessage", e.getMessage());
            errMsg = UtilProperties.getMessage(resourceError,
                    "productservices.problems_reading_product_entity", messageMap, locale);
            result.put(ModelService.RESPONSE_MESSAGE, ModelService.RESPOND_ERROR);
            result.put(ModelService.ERROR_MESSAGE, errMsg);
        }

        return result;
    }

    /**
     * Finds associated products by product ID and association ID.
     */
    public static Map<String, Object> prodFindAssociatedByType(DispatchContext dctx, Map<String, ? extends Object> context) {
        // * String productId      -- Current Product ID
        // * String type           -- Type of association (ie PRODUCT_UPGRADE, PRODUCT_COMPLEMENT, PRODUCT_VARIANT)
        Delegator delegator = dctx.getDelegator();
        Map<String, Object> result = FastMap.newInstance();
        String productId = (String) context.get("productId");
        String productIdTo = (String) context.get("productIdTo");
        String type = (String) context.get("type");
        Locale locale = (Locale) context.get("locale");
        String errMsg = null;

        Boolean cvaBool = (Boolean) context.get("checkViewAllow");
        boolean checkViewAllow = (cvaBool == null ? false : cvaBool);
        String prodCatalogId = (String) context.get("prodCatalogId");
        Boolean bidirectional = (Boolean) context.get("bidirectional");
        bidirectional = bidirectional == null ? false : bidirectional;
        Boolean sortDescending = (Boolean) context.get("sortDescending");
        sortDescending = sortDescending == null ? false : sortDescending;

        if (productId == null && productIdTo == null) {
            errMsg = UtilProperties.getMessage(resourceError,
                    "productservices.both_productId_and_productIdTo_cannot_be_null", locale);
            result.put(ModelService.RESPONSE_MESSAGE, ModelService.RESPOND_ERROR);
            result.put(ModelService.ERROR_MESSAGE, errMsg);
            return result;
        }

        if (productId != null && productIdTo != null) {
            errMsg = UtilProperties.getMessage(resourceError,
                    "productservices.both_productId_and_productIdTo_cannot_be_defined", locale);
            result.put(ModelService.RESPONSE_MESSAGE, ModelService.RESPOND_ERROR);
            result.put(ModelService.ERROR_MESSAGE, errMsg);
            return result;
        }

        productId = productId == null ? productIdTo : productId;
        GenericValue product = null;

        try {
            product = delegator.findByPrimaryKeyCache("Product", UtilMisc.toMap("productId", productId));
        } catch (GenericEntityException e) {
            Map<String, String> messageMap = UtilMisc.toMap("errMessage", e.getMessage());
            errMsg = UtilProperties.getMessage(resourceError,
                    "productservices.problems_reading_product_entity", messageMap, locale);
            result.put(ModelService.RESPONSE_MESSAGE, ModelService.RESPOND_ERROR);
            result.put(ModelService.ERROR_MESSAGE, errMsg);
            return result;
        }

        if (product == null) {
            errMsg = UtilProperties.getMessage(resourceError,
                    "productservices.problems_getting_product_entity", locale);
            result.put(ModelService.RESPONSE_MESSAGE, ModelService.RESPOND_ERROR);
            result.put(ModelService.ERROR_MESSAGE, errMsg);
            return result;
        }

        try {
            List<GenericValue> productAssocs = null;

            List<String> orderBy = FastList.newInstance();
            if (sortDescending) {
                orderBy.add("sequenceNum DESC");
            } else {
                orderBy.add("sequenceNum");
            }

            if (bidirectional) {
                EntityCondition cond = EntityCondition.makeCondition(
                        UtilMisc.toList(
                                EntityCondition.makeCondition("productId", productId),
                                EntityCondition.makeCondition("productIdTo", productId)
                        ), EntityJoinOperator.OR);
                cond = EntityCondition.makeCondition(cond, EntityCondition.makeCondition("productAssocTypeId", type));
                productAssocs = delegator.findList("ProductAssoc", cond, null, orderBy, null, true);
            } else {
                if (productIdTo == null) {
                    productAssocs = product.getRelatedCache("MainProductAssoc", UtilMisc.toMap("productAssocTypeId", type), orderBy);
                } else {
                    productAssocs = product.getRelatedCache("AssocProductAssoc", UtilMisc.toMap("productAssocTypeId", type), orderBy);
                }
            }
            // filter the list by date
            productAssocs = EntityUtil.filterByDate(productAssocs);
            // first check to see if there is a view allow category and if these products are in it...
            if (checkViewAllow && prodCatalogId != null && UtilValidate.isNotEmpty(productAssocs)) {
                String viewProductCategoryId = CatalogWorker.getCatalogViewAllowCategoryId(delegator, prodCatalogId);
                if (viewProductCategoryId != null) {
                    if (productIdTo == null) {
                        productAssocs = CategoryWorker.filterProductsInCategory(delegator, productAssocs, viewProductCategoryId, "productIdTo");
                    } else {
                        productAssocs = CategoryWorker.filterProductsInCategory(delegator, productAssocs, viewProductCategoryId, "productId");
                    }
                }
            }


            result.put("assocProducts", productAssocs);
            result.put(ModelService.RESPONSE_MESSAGE, ModelService.RESPOND_SUCCESS);
        } catch (GenericEntityException e) {
            Map<String, String> messageMap = UtilMisc.toMap("errMessage", e.getMessage());
            errMsg = UtilProperties.getMessage(resourceError,
                    "productservices.problems_product_association_relation_error", messageMap, locale);
            result.put(ModelService.RESPONSE_MESSAGE, ModelService.RESPOND_ERROR);
            result.put(ModelService.ERROR_MESSAGE, errMsg);
            return result;
        }

        return result;
    }

    // Builds a product feature tree
    private static Map<String, Object> makeGroup(Delegator delegator, Map<String, List<String>> featureList, List<String> items, List<String> order, int index)
            throws IllegalArgumentException, IllegalStateException {
        //List featureKey = FastList.newInstance();
        Map<String, List<String>> tempGroup = FastMap.newInstance();
        Map<String, Object> group = new LinkedHashMap<String, Object>();
        String orderKey = order.get(index);

        if (featureList == null) {
            throw new IllegalArgumentException("Cannot build feature tree: featureList is null");
        }

        if (index < 0) {
            throw new IllegalArgumentException("Invalid index '" + index + "' min index '0'");
        }
        if (index + 1 > order.size()) {
            throw new IllegalArgumentException("Invalid index '" + index + "' max index '" + (order.size() - 1) + "'");
        }

        // loop through items and make the lists
        for (String thisItem : items) {
            // -------------------------------
            // Gather the necessary data
            // -------------------------------

            if (Debug.verboseOn()) Debug.logVerbose("ThisItem: " + thisItem, module);
            List<GenericValue> features = null;

            try {
                Map<String, String> fields = UtilMisc.toMap("productId", thisItem, "productFeatureTypeId", orderKey,
                        "productFeatureApplTypeId", "STANDARD_FEATURE");
                List<String> sort = UtilMisc.toList("sequenceNum");

                // get the features and filter out expired dates
                features = delegator.findByAndCache("ProductFeatureAndAppl", fields, sort);
                features = EntityUtil.filterByDate(features, true);
            } catch (GenericEntityException e) {
                throw new IllegalStateException("Problem reading relation: " + e.getMessage());
            }
            if (Debug.verboseOn()) Debug.logVerbose("Features: " + features, module);

            // -------------------------------
            for (GenericValue item : features) {
                String itemKey = item.getString("description");

                if (tempGroup.containsKey(itemKey)) {
                    List<String> itemList = tempGroup.get(itemKey);

                    if (!itemList.contains(thisItem))
                        itemList.add(thisItem);
                } else {
                    List<String> itemList = UtilMisc.toList(thisItem);

                    tempGroup.put(itemKey, itemList);
                }
            }
        }
        if (Debug.verboseOn()) Debug.logVerbose("TempGroup: " + tempGroup, module);

        // Loop through the feature list and order the keys in the tempGroup
        List<String> orderFeatureList = featureList.get(orderKey);

        if (orderFeatureList == null) {
            throw new IllegalArgumentException("Cannot build feature tree: orderFeatureList is null for orderKey=" + orderKey);
        }

        for (String featureStr : orderFeatureList) {
            if (tempGroup.containsKey(featureStr))
                group.put(featureStr, tempGroup.get(featureStr));
        }

        if (Debug.verboseOn()) Debug.logVerbose("Group: " + group, module);

        // no groups; no tree
        if (group.size() == 0) {
            return group;
            //throw new IllegalStateException("Cannot create tree from group list; error on '" + orderKey + "'");
        }

        if (index + 1 == order.size()) {
            return group;
        }

        // loop through the keysets and get the sub-groups
        for (String key : group.keySet()) {
            List<String> itemList = UtilGenerics.checkList(group.get(key));

            if (UtilValidate.isNotEmpty(itemList)) {
                Map<String, Object> subGroup = makeGroup(delegator, featureList, itemList, order, index + 1);
                group.put(key, subGroup);
            } else {
                // do nothing, ie put nothing in the Map
                //throw new IllegalStateException("Cannot create tree from an empty list; error on '" + key + "'");
            }
        }
        return group;
    }


    // 变形产品属性
    private static Map<String, List<GenericValue>> makeGroupMap(Delegator delegator, List<String> items)
            throws IllegalArgumentException, IllegalStateException {
        //List featureKey = FastList.newInstance();
        Map<String, List<GenericValue>> group = new LinkedHashMap<String, List<GenericValue>>();

        // loop through items and make the lists
        for (String thisItem : items) {
            // -------------------------------
            // Gather the necessary data
            // -------------------------------

            List<GenericValue> features = null;

            try {
                Map<String, String> fields = UtilMisc.toMap("productId", thisItem,
                        "productFeatureApplTypeId", "STANDARD_FEATURE");
                List<String> sort = UtilMisc.toList("sequenceNum");

                // get the features and filter out expired dates
                features = delegator.findByAndCache("ProductFeatureAndAppl", fields, sort);
                features = EntityUtil.filterByDate(features, true);
            } catch (GenericEntityException e) {
                throw new IllegalStateException("Problem reading relation: " + e.getMessage());
            }
            if (Debug.verboseOn()) Debug.logVerbose("Features: " + features, module);

            // -------------------------------
            for (GenericValue feature : features) {
//                String itemKey = item.getString("description");
                try {
                    GenericValue featureType = feature.getRelatedOne("ProductFeatureType");
                    String desc = (String) featureType.get("description");
                    if (group.containsKey(desc)) {
                        List<GenericValue> itemList = group.get(desc);
                        boolean hasFeature = false;
                        for (int i = 0; i < itemList.size(); i++) {
                            GenericValue genericValue = itemList.get(i);
                            if (genericValue.get("productFeatureId").equals(feature.get("productFeatureId"))) {
                                hasFeature = true;
                            }
                        }
                        if (!hasFeature)
                            itemList.add(feature);
                    } else {
                        List<GenericValue> itemList = UtilMisc.toList(feature);
                        group.put(desc, itemList);
                    }
                } catch (GenericEntityException e) {
                    e.printStackTrace();
                }

            }
        }

        return group;
    }


    // builds a variant sample (a single sku for a featureType)
    private static Map<String, GenericValue> makeVariantSample(Delegator delegator, Map<String, List<String>> featureList, List<String> items, String feature) {
        Map<String, GenericValue> tempSample = FastMap.newInstance();
        Map<String, GenericValue> sample = new LinkedHashMap<String, GenericValue>();
        for (String productId : items) {
            List<GenericValue> features = null;

            try {
                Map<String, String> fields = UtilMisc.toMap("productId", productId, "productFeatureTypeId", feature,
                        "productFeatureApplTypeId", "STANDARD_FEATURE");
                List<String> sort = UtilMisc.toList("sequenceNum", "description");

                // get the features and filter out expired dates
                features = delegator.findByAndCache("ProductFeatureAndAppl", fields, sort);
                features = EntityUtil.filterByDate(features, true);
            } catch (GenericEntityException e) {
                throw new IllegalStateException("Problem reading relation: " + e.getMessage());
            }
            for (GenericValue featureAppl : features) {
                try {
                    GenericValue product = delegator.findByPrimaryKeyCache("Product",
                            UtilMisc.toMap("productId", productId));

                    tempSample.put(featureAppl.getString("description"), product);
                } catch (GenericEntityException e) {
                    throw new RuntimeException("Cannot get product entity: " + e.getMessage());
                }
            }
        }

        // Sort the sample based on the feature list.
        List<String> features = featureList.get(feature);
        for (String f : features) {
            if (tempSample.containsKey(f))
                sample.put(f, tempSample.get(f));
        }

        return sample;
    }

    public static Map<String, Object> quickAddVariant(DispatchContext dctx, Map<String, ? extends Object> context) {
        Delegator delegator = dctx.getDelegator();
        Map<String, Object> result = FastMap.newInstance();
        Locale locale = (Locale) context.get("locale");
        String errMsg = null;
        String productId = (String) context.get("productId");
        String variantProductId = (String) context.get("productVariantId");
        String productFeatureIds = (String) context.get("productFeatureIds");
        Long prodAssocSeqNum = (Long) context.get("sequenceNum");

        try {
            // read the product, duplicate it with the given id
            GenericValue product = delegator.findByPrimaryKey("Product", UtilMisc.toMap("productId", productId));
            if (product == null) {
                Map<String, String> messageMap = UtilMisc.toMap("productId", productId);
                errMsg = UtilProperties.getMessage(resourceError,
                        "productservices.product_not_found_with_ID", messageMap, locale);
                result.put(ModelService.RESPONSE_MESSAGE, ModelService.RESPOND_ERROR);
                result.put(ModelService.ERROR_MESSAGE, errMsg);
                return result;
            }
            // check if product exists
            GenericValue variantProduct = delegator.findByPrimaryKey("Product", UtilMisc.toMap("productId", variantProductId));
            boolean variantProductExists = (variantProduct != null);
            if (variantProduct == null) {
                //if product does not exist
                variantProduct = GenericValue.create(product);
                variantProduct.set("productId", variantProductId);
                variantProduct.set("isVirtual", "N");
                variantProduct.set("isVariant", "Y");
                variantProduct.set("primaryProductCategoryId", null);
                //create new
                variantProduct.create();
            } else {
                //if product does exist
                variantProduct.set("isVirtual", "N");
                variantProduct.set("isVariant", "Y");
                variantProduct.set("primaryProductCategoryId", null);
                //update entry
                variantProduct.store();
            }
            if (variantProductExists) {
                // Since the variant product is already a variant, first of all we remove the old features
                // and the associations of type PRODUCT_VARIANT: a given product can be a variant of only one product.
                delegator.removeByAnd("ProductAssoc", UtilMisc.toMap("productIdTo", variantProductId,
                        "productAssocTypeId", "PRODUCT_VARIANT"));
                delegator.removeByAnd("ProductFeatureAppl", UtilMisc.toMap("productId", variantProductId,
                        "productFeatureApplTypeId", "STANDARD_FEATURE"));
            }
            // add an association from productId to variantProductId of the PRODUCT_VARIANT
            Map<String, Object> productAssocMap = UtilMisc.toMap("productId", productId, "productIdTo", variantProductId,
                    "productAssocTypeId", "PRODUCT_VARIANT",
                    "fromDate", UtilDateTime.nowTimestamp());
            if (prodAssocSeqNum != null) {
                productAssocMap.put("sequenceNum", prodAssocSeqNum);
            }
            GenericValue productAssoc = delegator.makeValue("ProductAssoc", productAssocMap);
            productAssoc.create();

            // add the selected standard features to the new product given the productFeatureIds
            java.util.StringTokenizer st = new java.util.StringTokenizer(productFeatureIds, "|");
            while (st.hasMoreTokens()) {
                String productFeatureId = st.nextToken();

                GenericValue productFeature = delegator.findByPrimaryKey("ProductFeature", UtilMisc.toMap("productFeatureId", productFeatureId));

                GenericValue productFeatureAppl = delegator.makeValue("ProductFeatureAppl",
                        UtilMisc.toMap("productId", variantProductId, "productFeatureId", productFeatureId,
                                "productFeatureApplTypeId", "STANDARD_FEATURE", "fromDate", UtilDateTime.nowTimestamp()));

                // set the default seq num if it's there...
                if (productFeature != null) {
                    productFeatureAppl.set("sequenceNum", productFeature.get("defaultSequenceNum"));
                }

                productFeatureAppl.create();
            }

        } catch (GenericEntityException e) {
            Debug.logError(e, "Entity error creating quick add variant data", module);
            Map<String, String> messageMap = UtilMisc.toMap("errMessage", e.toString());
            errMsg = UtilProperties.getMessage(resourceError,
                    "productservices.entity_error_quick_add_variant_data", messageMap, locale);
            result.put(ModelService.RESPONSE_MESSAGE, ModelService.RESPOND_ERROR);
            result.put(ModelService.ERROR_MESSAGE, errMsg);
            return result;
        }
        result.put("productVariantId", variantProductId);
        return result;
    }

    /**
     * This will create a virtual product and return its ID, and associate all of the variants with it.
     * It will not put the selectable features on the virtual or standard features on the variant.
     */
    public static Map<String, Object> quickCreateVirtualWithVariants(DispatchContext dctx, Map<String, ? extends Object> context) {
        Delegator delegator = dctx.getDelegator();
        Timestamp nowTimestamp = UtilDateTime.nowTimestamp();

        // get the various IN attributes
        String variantProductIdsBag = (String) context.get("variantProductIdsBag");
        String productFeatureIdOne = (String) context.get("productFeatureIdOne");
        String productFeatureIdTwo = (String) context.get("productFeatureIdTwo");
        String productFeatureIdThree = (String) context.get("productFeatureIdThree");
        Locale locale = (Locale) context.get("locale");

        Map<String, Object> successResult = ServiceUtil.returnSuccess();

        try {
            // Generate new virtual productId, prefix with "VP", put in successResult
            String productId = (String) context.get("productId");

            if (UtilValidate.isEmpty(productId)) {
                productId = "VP" + delegator.getNextSeqId("Product");
                // Create new virtual product...
                GenericValue product = delegator.makeValue("Product");
                product.set("productId", productId);
                // set: isVirtual=Y, isVariant=N, productTypeId=FINISHED_GOOD, introductionDate=now
                product.set("isVirtual", "Y");
                product.set("isVariant", "N");
                product.set("productTypeId", "FINISHED_GOOD");
                product.set("introductionDate", nowTimestamp);
                // set all to Y: returnable, taxable, chargeShipping, autoCreateKeywords, includeInPromotions
                product.set("returnable", "Y");
                product.set("taxable", "Y");
                product.set("chargeShipping", "Y");
                product.set("autoCreateKeywords", "Y");
                product.set("includeInPromotions", "Y");
                // in it goes!
                product.create();
            }
            successResult.put("productId", productId);

            // separate variantProductIdsBag into a Set of variantProductIds
            //note: can be comma, tab, or white-space delimited
            Set<String> prelimVariantProductIds = FastSet.newInstance();
            List<String> splitIds = Arrays.asList(variantProductIdsBag.split("[,\\p{Space}]"));
            Debug.logInfo("Variants: bag=" + variantProductIdsBag, module);
            Debug.logInfo("Variants: split=" + splitIds, module);
            prelimVariantProductIds.addAll(splitIds);
            //note: should support both direct productIds and GoodIdentification entries (what to do if more than one GoodID? Add all?

            Map<String, GenericValue> variantProductsById = FastMap.newInstance();
            for (String variantProductId : prelimVariantProductIds) {
                if (UtilValidate.isEmpty(variantProductId)) {
                    // not sure why this happens, but seems to from time to time with the split method
                    continue;
                }
                // is a Product.productId?
                GenericValue variantProduct = delegator.findByPrimaryKey("Product", UtilMisc.toMap("productId", variantProductId));
                if (variantProduct != null) {
                    variantProductsById.put(variantProductId, variantProduct);
                } else {
                    // is a GoodIdentification.idValue?
                    List<GenericValue> goodIdentificationList = delegator.findByAnd("GoodIdentification", UtilMisc.toMap("idValue", variantProductId));
                    if (UtilValidate.isEmpty(goodIdentificationList)) {
                        // whoops, nothing found... return error
                        return ServiceUtil.returnError(UtilProperties.getMessage(resource,
                                "ProductVirtualVariantCreation", UtilMisc.toMap("variantProductId", variantProductId), locale));
                    }

                    if (goodIdentificationList.size() > 1) {
                        // what to do here? for now just log a warning and add all of them as variants; they can always be dissociated later
                        Debug.logWarning("Warning creating a virtual with variants: the ID [" + variantProductId + "] was not a productId and resulted in [" + goodIdentificationList.size() + "] GoodIdentification records: " + goodIdentificationList, module);
                    }

                    for (GenericValue goodIdentification : goodIdentificationList) {
                        GenericValue giProduct = goodIdentification.getRelatedOne("Product");
                        if (giProduct != null) {
                            variantProductsById.put(giProduct.getString("productId"), giProduct);
                        }
                    }
                }
            }

            // Attach productFeatureIdOne, Two, Three to the new virtual and all variant products as a standard feature
            Set<String> featureProductIds = FastSet.newInstance();
            featureProductIds.add(productId);
            featureProductIds.addAll(variantProductsById.keySet());
            Set<String> productFeatureIds = new HashSet<String>();
            productFeatureIds.add(productFeatureIdOne);
            productFeatureIds.add(productFeatureIdTwo);
            productFeatureIds.add(productFeatureIdThree);

            for (String featureProductId : featureProductIds) {
                for (String productFeatureId : productFeatureIds) {
                    if (UtilValidate.isNotEmpty(productFeatureId)) {
                        GenericValue productFeatureAppl = delegator.makeValue("ProductFeatureAppl",
                                UtilMisc.toMap("productId", featureProductId, "productFeatureId", productFeatureId,
                                        "productFeatureApplTypeId", "STANDARD_FEATURE", "fromDate", nowTimestamp));
                        productFeatureAppl.create();
                    }
                }
            }

            for (GenericValue variantProduct : variantProductsById.values()) {
                // for each variant product set: isVirtual=N, isVariant=Y, introductionDate=now
                variantProduct.set("isVirtual", "N");
                variantProduct.set("isVariant", "Y");
                variantProduct.set("introductionDate", nowTimestamp);
                variantProduct.store();

                // for each variant product create associate with the new virtual as a PRODUCT_VARIANT
                GenericValue productAssoc = delegator.makeValue("ProductAssoc",
                        UtilMisc.toMap("productId", productId, "productIdTo", variantProduct.get("productId"),
                                "productAssocTypeId", "PRODUCT_VARIANT", "fromDate", nowTimestamp));
                productAssoc.create();
            }
        } catch (GenericEntityException e) {
            String errMsg = "Error creating new virtual product from variant products: " + e.toString();
            Debug.logError(e, errMsg, module);
            return ServiceUtil.returnError(errMsg);
        }
        return successResult;
    }

    public static Map<String, Object> updateProductIfAvailableFromShipment(DispatchContext dctx, Map<String, ? extends Object> context) {
        if ("Y".equals(UtilProperties.getPropertyValue("catalog.properties", "reactivate.product.from.receipt", "N"))) {
            LocalDispatcher dispatcher = dctx.getDispatcher();
            Delegator delegator = dctx.getDelegator();
            GenericValue userLogin = (GenericValue) context.get("userLogin");
            String inventoryItemId = (String) context.get("inventoryItemId");

            GenericValue inventoryItem = null;
            try {
                inventoryItem = delegator.findByPrimaryKeyCache("InventoryItem", UtilMisc.toMap("inventoryItemId", inventoryItemId));
            } catch (GenericEntityException e) {
                Debug.logError(e, module);
                return ServiceUtil.returnError(e.getMessage());
            }

            if (inventoryItem != null) {
                String productId = inventoryItem.getString("productId");
                GenericValue product = null;
                try {
                    product = delegator.findByPrimaryKeyCache("Product", UtilMisc.toMap("productId", productId));
                } catch (GenericEntityException e) {
                    Debug.logError(e, module);
                    return ServiceUtil.returnError(e.getMessage());
                }

                if (product != null) {
                    Timestamp salesDiscontinuationDate = product.getTimestamp("salesDiscontinuationDate");
                    if (salesDiscontinuationDate != null && salesDiscontinuationDate.before(UtilDateTime.nowTimestamp())) {
                        Map<String, Object> invRes = null;
                        try {
                            invRes = dispatcher.runSync("getProductInventoryAvailable", UtilMisc.<String, Object>toMap("productId", productId, "userLogin", userLogin));
                        } catch (GenericServiceException e) {
                            Debug.logError(e, module);
                            return ServiceUtil.returnError(e.getMessage());
                        }

                        BigDecimal availableToPromiseTotal = (BigDecimal) invRes.get("availableToPromiseTotal");
                        if (availableToPromiseTotal != null && availableToPromiseTotal.compareTo(BigDecimal.ZERO) > 0) {
                            // refresh the product so we can update it
                            GenericValue productToUpdate = null;
                            try {
                                productToUpdate = delegator.findByPrimaryKey("Product", product.getPrimaryKey());
                            } catch (GenericEntityException e) {
                                Debug.logError(e, module);
                                return ServiceUtil.returnError(e.getMessage());
                            }

                            // set and save
                            productToUpdate.set("salesDiscontinuationDate", null);
                            try {
                                delegator.store(productToUpdate);
                            } catch (GenericEntityException e) {
                                Debug.logError(e, module);
                                return ServiceUtil.returnError(e.getMessage());
                            }
                        }
                    }
                }
            }
        }

        return ServiceUtil.returnSuccess();
    }

    public static Map<String, Object> addAdditionalViewForProduct(DispatchContext dctx, Map<String, ? extends Object> context)
            throws IOException, JDOMException {
        Map<String, Object> result = ServiceUtil.returnSuccess();
        Map<String, Object> retObj = FastMap.newInstance();
        LocalDispatcher dispatcher = dctx.getDispatcher();
        Delegator delegator = dctx.getDelegator();
        String productId = (String) context.get("productId");
        String productContentTypeId = (String) context.get("productContentTypeId");
        ByteBuffer imageData = (ByteBuffer) context.get("uploadedFile");
        Locale locale = (Locale) context.get("locale");

        if (UtilValidate.isNotEmpty(context.get("_uploadedFile_fileName"))) {
            String imageFilenameFormat = UtilProperties.getPropertyValue("catalog", "image.filename.additionalviewsize.format");
            String imageServerPath = FlexibleStringExpander.expandString(UtilProperties.getPropertyValue("catalog", "image.server.path"), context);
            while(imageServerPath.endsWith("/")) imageServerPath = imageServerPath.substring(0,imageServerPath.length()-1);
            String imageUrlPrefix = FlexibleStringExpander.expandString(UtilProperties.getPropertyValue("catalog", "image.url.prefix"), context);
            if(imageUrlPrefix.endsWith("/")) imageUrlPrefix = imageUrlPrefix.substring(0,imageUrlPrefix.length()-1);
            FlexibleStringExpander filenameExpander = FlexibleStringExpander.getInstance(imageFilenameFormat);
            String viewNumber = String.valueOf(productContentTypeId.charAt(productContentTypeId.length() - 1));
            if (viewNumber.equals("1")) {
                retObj.put("paramName", "additionalImageOne");
            } else if (viewNumber.equals("2")) {
                retObj.put("paramName", "additionalImageTwo");
            } else if (viewNumber.equals("3")) {
                retObj.put("paramName", "additionalImageThree");
            } else if (viewNumber.equals("4")) {
                retObj.put("paramName", "additionalImageFour");
            } else if (viewNumber.equals("5")) {
                retObj.put("paramName", "additionalImageFive");
            } else if (viewNumber.equals("6")) {
                retObj.put("paramName", "additionalImageSix");
            } else if (viewNumber.equals("7")) {
                retObj.put("paramName", "additionalImageSeven");
            } else if (viewNumber.equals("8")) {
                retObj.put("paramName", "additionalImageEight");
            }
            String viewType = "additional" + viewNumber;
            String id = productId;
            if (imageFilenameFormat.endsWith("${id}")) {
                id = productId + "_View_" + viewNumber;
                viewType = "additional";
            }
            String fileLocation = filenameExpander.expandString(UtilMisc.toMap("location", "products", "id", id, "viewtype", viewType, "sizetype", "original"));
            String filePathPrefix = "";
            String filenameToUse = fileLocation;
            if (fileLocation.lastIndexOf("/") != -1) {
                filePathPrefix = fileLocation.substring(0, fileLocation.lastIndexOf("/") + 1); // adding 1 to include the trailing slash
                filenameToUse = fileLocation.substring(fileLocation.lastIndexOf("/") + 1);
            }

            List<GenericValue> fileExtension = FastList.newInstance();
            try {
                fileExtension = delegator.findByAnd("FileExtension", UtilMisc.toMap("mimeTypeId", context.get("_uploadedFile_contentType")));
            } catch (GenericEntityException e) {
                Debug.logError(e, module);
                return ServiceUtil.returnError(e.getMessage());
            }

            GenericValue extension = EntityUtil.getFirst(fileExtension);
            if (extension != null) {
                filenameToUse += "." + extension.getString("fileExtensionId");
                retObj.put("name", filenameToUse);
                retObj.put("type", extension.getString("fileExtensionId"));
            }
            retObj.put("delete_url", "removeProductImages");
            retObj.put("delete_type", "POST");
            retObj.put("productContentTypeId", productContentTypeId);
            retObj.put("productId", productId);
            retObj.put("size", imageData.array().length);
            /* Write the new image file */
            String targetDirectory = imageServerPath + "/" + filePathPrefix;
            try {
                File targetDir = new File(targetDirectory);
                // Create the new directory
                if (!targetDir.exists()) {
                    boolean created = targetDir.mkdirs();
                    if (!created) {
                        String errMsg = UtilProperties.getMessage(resource, "ScaleImage.unable_to_create_target_directory", locale) + " - " + targetDirectory;
                        Debug.logFatal(errMsg, module);
                        return ServiceUtil.returnError(errMsg);
                    }
                    // Delete existing image files
                    // Images are ordered by productId (${location}/${id}/${viewtype}/${sizetype})
                } else if (!filenameToUse.contains(productId)) {
                    try {
                        File[] files = targetDir.listFiles();
                        for (File file : files) {
                            if (file.isFile()) file.delete();
                        }
                    } catch (SecurityException e) {
                        Debug.logError(e, module);
                    }
                    // Images aren't ordered by productId (${location}/${viewtype}/${sizetype}/${id})
                } else {
                    try {
                        File[] files = targetDir.listFiles();
                        for (File file : files) {
                            if (file.isFile() && file.getName().startsWith(productId + "_View_" + viewNumber))
                                file.delete();
                        }
                    } catch (SecurityException e) {
                        Debug.logError(e, module);
                    }
                }
            } catch (NullPointerException e) {
                Debug.logError(e, module);
            }
            // Write
            String filePath = imageServerPath + "/" + fileLocation + "." + extension.getString("fileExtensionId");
            try {
                File file = new File(filePath);
                try {
                    RandomAccessFile out = new RandomAccessFile(file, "rw");
                    out.write(imageData.array());
                    out.close();
                } catch (FileNotFoundException e) {
                    Debug.logError(e, module);
                    return ServiceUtil.returnError(UtilProperties.getMessage(resource,
                            "ProductImageViewUnableWriteFile", UtilMisc.toMap("fileName", file.getAbsolutePath()), locale));
                } catch (IOException e) {
                    Debug.logError(e, module);
                    return ServiceUtil.returnError(UtilProperties.getMessage(resource,
                            "ProductImageViewUnableWriteBinaryData", UtilMisc.toMap("fileName", file.getAbsolutePath()), locale));
                }
            } catch (NullPointerException e) {
                Debug.logError(e, module);
            }

            //文件上传到服务器之后(备份使用)，选择是否上传到云存储
            String uploadType = UtilProperties.getPropertyValue("content", "content.image.upload.type");


            /* scale Image in different sizes */
            Map<String, Object> resultResize = FastMap.newInstance();
            Map<String, String> qiniuImageUrlMap = FastMap.newInstance();
            String saveImagePath = imageUrlPrefix + "/" + filePathPrefix;
            String saveImageKey = (saveImagePath + "original." + extension.getString("fileExtensionId"));
            try {
                resultResize.putAll(ScaleImage.scaleImageInAllSize(context, filenameToUse, "additional", viewNumber, "products", productId));
                if (uploadType.equals("qiniu")) {
                    //异步调用qiniu 上传文件的服务器
                    dispatcher.runAsync("coverUpload", UtilMisc.toMap("filePath", filePath, "fileKey", saveImageKey));
                    for (String sizeType : ScaleImage.sizeTypeList) {
                        if (sizeType.equals("small")) {
                            qiniuImageUrlMap.put(sizeType, saveImageKey + "?imageView2/2/w/200");
                        } else if (sizeType.equals("medium")) {
                            qiniuImageUrlMap.put(sizeType, saveImageKey + "?imageView2/2/w/400");
                        } else if (sizeType.equals("large")) {
                            qiniuImageUrlMap.put(sizeType, saveImageKey + "?imageView2/2/w/800");
                        } else if (sizeType.equals("detail")) {
                            qiniuImageUrlMap.put(sizeType, saveImageKey + "?imageView2/2/w/600");
                        } else if (sizeType.equals("thumbnail")) {
                            qiniuImageUrlMap.put(sizeType, saveImageKey + "?imageView2/2/w/220");
                        }

                    }

                }
            } catch (IOException e) {
                Debug.logError(e, "Scale additional image in all different sizes is impossible : " + e.toString(), module);
                return ServiceUtil.returnError(UtilProperties.getMessage(resource,
                        "ProductImageViewScaleImpossible", UtilMisc.toMap("errorString", e.toString()), locale));
            } catch (JDOMException e) {
                Debug.logError(e, "Errors occur in parsing ImageProperties.xml : " + e.toString(), module);
                return ServiceUtil.returnError(UtilProperties.getMessage(resource,
                        "ProductImageViewParsingError", UtilMisc.toMap("errorString", e.toString()), locale));
            } catch (ServiceValidationException e) {
                e.printStackTrace();
                return ServiceUtil.returnError(e.getMessage());
            } catch (ServiceAuthException e) {
                e.printStackTrace();
                return ServiceUtil.returnError(e.getMessage());
            } catch (GenericServiceException e) {
                e.printStackTrace();
                return ServiceUtil.returnError(e.getMessage());
            }

            String imageUrl = imageUrlPrefix + File.separator + fileLocation + "." + extension.getString("fileExtensionId");
            /* store the imageUrl version of the image, for backwards compatibility with code that does not use scaled versions */
            result = addImageResource(dispatcher, delegator, context, imageUrl, saveImageKey, productContentTypeId);

            if (ServiceUtil.isError(result)) {
                return result;
            }
            GenericValue product = null;
            ProductContentWrapper contentWrapper = null;
            try {
                product = delegator.findByPrimaryKey("Product", UtilMisc.toMap("productId", productId));
                contentWrapper = new ProductContentWrapper(dispatcher, product, locale, "text/html");
                String url = contentWrapper.get(productContentTypeId).toString();
                retObj.put("url", url);
            } catch (GenericEntityException e) {
                e.printStackTrace();
            }


            /* now store the image versions created by ScaleImage.scaleImageInAllSize */
            /* have to shrink length of productContentTypeId, as otherwise value is too long for database field */
            Map<String, String> imageUrlMap = UtilGenerics.checkMap(resultResize.get("imageUrlMap"));
            for (String sizeType : ScaleImage.sizeTypeList) {
                imageUrl = imageUrlMap.get(sizeType);
                String qiniuImageUrl = qiniuImageUrlMap.get(sizeType);
                if (UtilValidate.isNotEmpty(imageUrl)) {
                    result = addImageResource(dispatcher, delegator, context, imageUrl, qiniuImageUrl, "XTRA_IMG_" + viewNumber + "_" + sizeType.toUpperCase());
                    if (contentWrapper != null) {
                        String domain = UtilProperties.getPropertyValue("content", "img.qiniu.domain");
                        String pathUrl = contentWrapper.get("XTRA_IMG_" + viewNumber + "_" + sizeType.toUpperCase()).toString();
                        pathUrl = UtilStrings.replace(pathUrl, domain, "/");
                        retObj.put(sizeType.toLowerCase() + "_url", pathUrl);
                    }
                    if (ServiceUtil.isError(result)) {
                        return result;
                    }
                }
            }

        }
        result.put("retObj", retObj);
        return result;
    }

    private static Map<String, Object> addImageResource(LocalDispatcher dispatcher, Delegator delegator, Map<String, ? extends Object> context,
                                                        String imageUrl, String qiniuImageUrl, String productContentTypeId) {
        GenericValue userLogin = (GenericValue) context.get("userLogin");
        String productId = (String) context.get("productId");

        if (UtilValidate.isNotEmpty(imageUrl) && imageUrl.length() > 0) {
            String contentId = (String) context.get("contentId");

            Map<String, Object> dataResourceCtx = FastMap.newInstance();
            dataResourceCtx.put("objectInfo", imageUrl);
            dataResourceCtx.put("qiniuObjectInfo", qiniuImageUrl);
            dataResourceCtx.put("dataResourceName", context.get("_uploadedFile_fileName"));
            dataResourceCtx.put("isPublic", "Y");
            dataResourceCtx.put("userLogin", userLogin);

            Map<String, Object> productContentCtx = FastMap.newInstance();
            productContentCtx.put("productId", productId);
            productContentCtx.put("productContentTypeId", productContentTypeId);
            productContentCtx.put("fromDate", context.get("fromDate"));
            productContentCtx.put("thruDate", context.get("thruDate"));
            productContentCtx.put("userLogin", userLogin);

            if (UtilValidate.isNotEmpty(contentId)) {
                GenericValue content = null;
                try {
                    content = delegator.findOne("Content", UtilMisc.toMap("contentId", contentId), false);
                } catch (GenericEntityException e) {
                    Debug.logError(e, module);
                    return ServiceUtil.returnError(e.getMessage());
                }

                if (content != null) {
                    GenericValue dataResource = null;
                    try {
                        dataResource = content.getRelatedOne("DataResource");
                    } catch (GenericEntityException e) {
                        Debug.logError(e, module);
                        return ServiceUtil.returnError(e.getMessage());
                    }

                    if (dataResource != null) {
                        dataResourceCtx.put("dataResourceId", dataResource.getString("dataResourceId"));
                        try {
                            dispatcher.runSync("updateDataResource", dataResourceCtx);
                        } catch (GenericServiceException e) {
                            Debug.logError(e, module);
                            return ServiceUtil.returnError(e.getMessage());
                        }
                    } else {
                        dataResourceCtx.put("dataResourceTypeId", "SHORT_TEXT");
                        dataResourceCtx.put("mimeTypeId", "text/html");
                        Map<String, Object> dataResourceResult = FastMap.newInstance();
                        try {
                            dataResourceResult = dispatcher.runSync("createDataResource", dataResourceCtx);
                        } catch (GenericServiceException e) {
                            Debug.logError(e, module);
                            return ServiceUtil.returnError(e.getMessage());
                        }

                        Map<String, Object> contentCtx = FastMap.newInstance();
                        contentCtx.put("contentId", contentId);
                        contentCtx.put("dataResourceId", dataResourceResult.get("dataResourceId"));
                        contentCtx.put("userLogin", userLogin);
                        try {
                            dispatcher.runSync("updateContent", contentCtx);
                        } catch (GenericServiceException e) {
                            Debug.logError(e, module);
                            return ServiceUtil.returnError(e.getMessage());
                        }
                    }

                    productContentCtx.put("contentId", contentId);
                    try {
                        dispatcher.runSync("updateProductContent", productContentCtx);
                    } catch (GenericServiceException e) {
                        Debug.logError(e, module);
                        return ServiceUtil.returnError(e.getMessage());
                    }
                }
            } else {
                dataResourceCtx.put("dataResourceTypeId", "SHORT_TEXT");
                dataResourceCtx.put("mimeTypeId", "text/html");
                Map<String, Object> dataResourceResult = FastMap.newInstance();
                try {
                    dataResourceResult = dispatcher.runSync("createDataResource", dataResourceCtx);
                } catch (GenericServiceException e) {
                    Debug.logError(e, module);
                    return ServiceUtil.returnError(e.getMessage());
                }

                Map<String, Object> contentCtx = FastMap.newInstance();
                contentCtx.put("contentTypeId", "DOCUMENT");
                contentCtx.put("dataResourceId", dataResourceResult.get("dataResourceId"));
                contentCtx.put("userLogin", userLogin);
                Map<String, Object> contentResult = FastMap.newInstance();
                try {
                    contentResult = dispatcher.runSync("createContent", contentCtx);
                } catch (GenericServiceException e) {
                    Debug.logError(e, module);
                    return ServiceUtil.returnError(e.getMessage());
                }

                productContentCtx.put("contentId", contentResult.get("contentId"));
                try {
                    dispatcher.runSync("createProductContent", productContentCtx);
                } catch (GenericServiceException e) {
                    Debug.logError(e, module);
                    return ServiceUtil.returnError(e.getMessage());
                }
            }
        }
        return ServiceUtil.returnSuccess();
    }

    /**
     * Finds productId(s) corresponding to a product reference, productId or a GoodIdentification idValue
     *
     * @param ctx     the dispatch context
     * @param context productId use to search with productId or goodIdentification.idValue
     * @return a GenericValue with a productId and a List of complementary productId found
     */
    public static Map<String, Object> findProductById(DispatchContext ctx, Map<String, Object> context) {
        Delegator delegator = ctx.getDelegator();
        String idToFind = (String) context.get("idToFind");
        String goodIdentificationTypeId = (String) context.get("goodIdentificationTypeId");
        String searchProductFirstContext = (String) context.get("searchProductFirst");
        String searchAllIdContext = (String) context.get("searchAllId");

        boolean searchProductFirst = !(UtilValidate.isNotEmpty(searchProductFirstContext) && "N".equals(searchProductFirstContext));
        boolean searchAllId = UtilValidate.isNotEmpty(searchAllIdContext) && "Y".equals(searchAllIdContext);

        GenericValue product = null;
        List<GenericValue> productsFound = null;
        try {
            productsFound = ProductWorker.findProductsById(delegator, idToFind, goodIdentificationTypeId, searchProductFirst, searchAllId);
        } catch (GenericEntityException e) {
            Debug.logError(e, module);
            return ServiceUtil.returnError(e.getMessage());
        }

        if (UtilValidate.isNotEmpty(productsFound)) {
            // gets the first productId of the List
            product = EntityUtil.getFirst(productsFound);
            // remove this productId
            productsFound.remove(0);
        }

        Map<String, Object> result = ServiceUtil.returnSuccess();
        result.put("product", product);
        result.put("productsList", productsFound);

        return result;
    }

    public static Map<String, Object> addImageForProductPromo(DispatchContext dctx, Map<String, ? extends Object> context)
            throws IOException, JDOMException {
        LocalDispatcher dispatcher = dctx.getDispatcher();
        Delegator delegator = dctx.getDelegator();
        GenericValue userLogin = (GenericValue) context.get("userLogin");
        String productPromoId = (String) context.get("productPromoId");
        String productPromoContentTypeId = (String) context.get("productPromoContentTypeId");
        ByteBuffer imageData = (ByteBuffer) context.get("uploadedFile");
        String contentId = (String) context.get("contentId");
        Locale locale = (Locale) context.get("locale");

        if (UtilValidate.isNotEmpty(context.get("_uploadedFile_fileName"))) {
            String imageFilenameFormat = UtilProperties.getPropertyValue("catalog", "image.filename.format");
            String imageServerPath = FlexibleStringExpander.expandString(UtilProperties.getPropertyValue("catalog", "image.server.path"), context);
            while(imageServerPath.endsWith("/")) imageServerPath = imageServerPath.substring(0,imageServerPath.length()-1);
            String imageUrlPrefix = FlexibleStringExpander.expandString(UtilProperties.getPropertyValue("catalog", "image.url.prefix"), context);
            if(imageUrlPrefix.endsWith("/")) imageUrlPrefix = imageUrlPrefix.substring(0,imageUrlPrefix.length()-1);
            FlexibleStringExpander filenameExpander = FlexibleStringExpander.getInstance(imageFilenameFormat);
            String id = productPromoId + "_Image_" + productPromoContentTypeId.charAt(productPromoContentTypeId.length() - 1);
            String fileLocation = filenameExpander.expandString(UtilMisc.toMap("location", "products", "type", "promo", "id", id));
            String filePathPrefix = "";
            String filenameToUse = fileLocation;
            if (fileLocation.lastIndexOf("/") != -1) {
                filePathPrefix = fileLocation.substring(0, fileLocation.lastIndexOf("/") + 1); // adding 1 to include the trailing slash
                filenameToUse = fileLocation.substring(fileLocation.lastIndexOf("/") + 1);
            }

            List<GenericValue> fileExtension = FastList.newInstance();
            try {
                fileExtension = delegator.findList("FileExtension", EntityCondition.makeCondition("mimeTypeId", EntityOperator.EQUALS, context.get("_uploadedFile_contentType")), null, null, null, false);
            } catch (GenericEntityException e) {
                Debug.logError(e, module);
                return ServiceUtil.returnError(e.getMessage());
            }

            GenericValue extension = EntityUtil.getFirst(fileExtension);
            if (extension != null) {
                filenameToUse += "." + extension.getString("fileExtensionId");
            }

            File makeResourceDirectory = new File(imageServerPath + "/" + filePathPrefix);
            if (!makeResourceDirectory.exists()) {
                makeResourceDirectory.mkdirs();
            }

            File file = new File(imageServerPath + "/" + filePathPrefix + filenameToUse);

            try {
                RandomAccessFile out = new RandomAccessFile(file, "rw");
                out.write(imageData.array());
                out.close();
            } catch (FileNotFoundException e) {
                Debug.logError(e, module);
                return ServiceUtil.returnError(UtilProperties.getMessage(resource,
                        "ProductImageViewUnableWriteFile", UtilMisc.toMap("fileName", file.getAbsolutePath()), locale));
            } catch (IOException e) {
                Debug.logError(e, module);
                return ServiceUtil.returnError(UtilProperties.getMessage(resource,
                        "ProductImageViewUnableWriteBinaryData", UtilMisc.toMap("fileName", file.getAbsolutePath()), locale));
            }

            String imageUrl = imageUrlPrefix + "/" + filePathPrefix + filenameToUse;

            if (UtilValidate.isNotEmpty(imageUrl) && imageUrl.length() > 0) {
                Map<String, Object> dataResourceCtx = FastMap.newInstance();
                dataResourceCtx.put("objectInfo", imageUrl);
                dataResourceCtx.put("dataResourceName", context.get("_uploadedFile_fileName"));
                dataResourceCtx.put("userLogin", userLogin);

                Map<String, Object> productPromoContentCtx = FastMap.newInstance();
                productPromoContentCtx.put("productPromoId", productPromoId);
                productPromoContentCtx.put("productPromoContentTypeId", productPromoContentTypeId);
                productPromoContentCtx.put("fromDate", context.get("fromDate"));
                productPromoContentCtx.put("thruDate", context.get("thruDate"));
                productPromoContentCtx.put("userLogin", userLogin);

                if (UtilValidate.isNotEmpty(contentId)) {
                    GenericValue content = null;
                    try {
                        content = delegator.findOne("Content", UtilMisc.toMap("contentId", contentId), false);
                    } catch (GenericEntityException e) {
                        Debug.logError(e, module);
                        return ServiceUtil.returnError(e.getMessage());
                    }

                    if (UtilValidate.isNotEmpty(content)) {
                        GenericValue dataResource = null;
                        try {
                            dataResource = content.getRelatedOne("DataResource");
                        } catch (GenericEntityException e) {
                            Debug.logError(e, module);
                            return ServiceUtil.returnError(e.getMessage());
                        }

                        if (UtilValidate.isNotEmpty(dataResource)) {
                            dataResourceCtx.put("dataResourceId", dataResource.getString("dataResourceId"));
                            try {
                                dispatcher.runSync("updateDataResource", dataResourceCtx);
                            } catch (GenericServiceException e) {
                                Debug.logError(e, module);
                                return ServiceUtil.returnError(e.getMessage());
                            }
                        } else {
                            dataResourceCtx.put("dataResourceTypeId", "SHORT_TEXT");
                            dataResourceCtx.put("mimeTypeId", "text/html");
                            Map<String, Object> dataResourceResult = FastMap.newInstance();
                            try {
                                dataResourceResult = dispatcher.runSync("createDataResource", dataResourceCtx);
                            } catch (GenericServiceException e) {
                                Debug.logError(e, module);
                                return ServiceUtil.returnError(e.getMessage());
                            }

                            Map<String, Object> contentCtx = FastMap.newInstance();
                            contentCtx.put("contentId", contentId);
                            contentCtx.put("dataResourceId", dataResourceResult.get("dataResourceId"));
                            contentCtx.put("userLogin", userLogin);
                            try {
                                dispatcher.runSync("updateContent", contentCtx);
                            } catch (GenericServiceException e) {
                                Debug.logError(e, module);
                                return ServiceUtil.returnError(e.getMessage());
                            }
                        }

                        productPromoContentCtx.put("contentId", contentId);
                        try {
                            dispatcher.runSync("updateProductPromoContent", productPromoContentCtx);
                        } catch (GenericServiceException e) {
                            Debug.logError(e, module);
                            return ServiceUtil.returnError(e.getMessage());
                        }
                    }
                } else {
                    dataResourceCtx.put("dataResourceTypeId", "SHORT_TEXT");
                    dataResourceCtx.put("mimeTypeId", "text/html");
                    Map<String, Object> dataResourceResult = FastMap.newInstance();
                    try {
                        dataResourceResult = dispatcher.runSync("createDataResource", dataResourceCtx);
                    } catch (GenericServiceException e) {
                        Debug.logError(e, module);
                        return ServiceUtil.returnError(e.getMessage());
                    }

                    Map<String, Object> contentCtx = FastMap.newInstance();
                    contentCtx.put("contentTypeId", "DOCUMENT");
                    contentCtx.put("dataResourceId", dataResourceResult.get("dataResourceId"));
                    contentCtx.put("userLogin", userLogin);
                    Map<String, Object> contentResult = FastMap.newInstance();
                    try {
                        contentResult = dispatcher.runSync("createContent", contentCtx);
                    } catch (GenericServiceException e) {
                        Debug.logError(e, module);
                        return ServiceUtil.returnError(e.getMessage());
                    }

                    productPromoContentCtx.put("contentId", contentResult.get("contentId"));
                    try {
                        dispatcher.runSync("createProductPromoContent", productPromoContentCtx);
                    } catch (GenericServiceException e) {
                        Debug.logError(e, module);
                        return ServiceUtil.returnError(e.getMessage());
                    }
                }
            }
        } else {
            Map<String, Object> productPromoContentCtx = FastMap.newInstance();
            productPromoContentCtx.put("productPromoId", productPromoId);
            productPromoContentCtx.put("productPromoContentTypeId", productPromoContentTypeId);
            productPromoContentCtx.put("contentId", contentId);
            productPromoContentCtx.put("fromDate", context.get("fromDate"));
            productPromoContentCtx.put("thruDate", context.get("thruDate"));
            productPromoContentCtx.put("userLogin", userLogin);
            try {
                dispatcher.runSync("updateProductPromoContent", productPromoContentCtx);
            } catch (GenericServiceException e) {
                Debug.logError(e, module);
                return ServiceUtil.returnError(e.getMessage());
            }
        }
        return ServiceUtil.returnSuccess();
    }


    public static Map<String, Object> productSummary(DispatchContext ctx, Map<String, Object> context) throws GenericEntityException {

        String productId = (String) context.get("productId");
        String webSiteId = (String) context.get("webSiteId");
        String prodCatalogId = (String) context.get("catalogId");
        String productStoreId = (String) context.get("productStoreId");
        GenericValue autoUserLogin = (GenericValue) context.get("UserLogin");

        Delegator delegator = ctx.getDelegator();
        GenericValue productStore = delegator.findByPrimaryKeyCache("ProductStore", UtilMisc.toMap("productStoreId", productStoreId));
        String defaultCurrency = (String) productStore.get("defaultCurrencyUomId");

        Locale locale = (Locale) context.get("locale");
        Map<String, Object> result = ServiceUtil.returnSuccess();
        GenericValue miniProduct = null;
        try {

            miniProduct = delegator.findByPrimaryKey("Product", UtilMisc.toMap("productId", productId));
        } catch (GenericEntityException e) {
            Debug.logError(e, "Problems reading order header from datasource.", module);
            return ServiceUtil.returnError(UtilProperties.getMessage(e.getMessage(), "productSummary", locale));

        }
        LocalDispatcher dispatcher = ctx.getDispatcher();
        if (miniProduct != null && productStoreId != null && prodCatalogId != null) {
            // calculate the "your" price
            Map<String, Object> priceResult = null;
            try {
                priceResult = dispatcher.runSync("calculateProductPrice",
                        UtilMisc.<String, Object>toMap("product", miniProduct, "prodCatalogId", prodCatalogId, "webSiteId", webSiteId, "currencyUomId", defaultCurrency, "autoUserLogin", autoUserLogin, "productStoreId", productStoreId));
                if (ServiceUtil.isError(priceResult)) {
                    Debug.logError(UtilProperties.getMessage(resourceError,
                            "ProductSummary", locale) + "priceResult error", null, null, priceResult);
                }
            } catch (GenericServiceException e) {
                Debug.logError(e, "Error changing item status to " + "ITEM_COMPLETED" + ": " + e.toString(), module);
                return ServiceUtil.returnError(e.getMessage());
            }


            // returns: basePrice listPrice ,COMPETITIVE_PRICE,AVERAGE_COST
            result.put("priceResult", priceResult);

            // get aggregated product totalPrice
            if ("AGGREGATED".equals(miniProduct.get("productTypeId")) || "AGGREGATED_SERVICE".equals(miniProduct.get("productTypeId"))) {
//                ProductConfigWrapper configWrapper = ProductConfigWorker.getProductConfigWrapper(productId, defaultCurrency, prodCatalogId,webSiteId,productStoreId,autoUserLogin,dispatcher,delegator,locale);
                ProductConfigWrapper configWrapper = ProductConfigWorker.getProductConfigWrapper(productId, defaultCurrency, prodCatalogId, webSiteId, productStoreId, autoUserLogin, dispatcher, delegator, locale);
                if (configWrapper != null) {
                    configWrapper.setDefaultConfig();
                    // Check if Config Price has to be displayed with tax
                    if (productStore.get("showPricesWithVatTax").equals("Y")) {
                        BigDecimal totalPriceNoTax = configWrapper.getTotalPrice();
                        Map<String, Object> totalPriceMap = null;
                        try {
                            totalPriceMap = dispatcher.runSync("calcTaxForDisplay", UtilMisc.toMap("basePrice", totalPriceNoTax, "locale", locale, "productId", productId, "productStoreId", productStoreId));
                        } catch (GenericServiceException e) {
                            e.printStackTrace();
                        }
                        result.put("totalPrice", totalPriceMap.get("priceWithTax"));
                    } else {
                        result.put("totalPrice", configWrapper.getTotalPrice());
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


            String additionalImage1Detail = miniProductContentWrapper.get("XTRA_IMG_1_DETAIL") == null ? "" : miniProductContentWrapper.get("XTRA_IMG_1_DETAIL").toString();
            String additionalImage1Dedium = miniProductContentWrapper.get("XTRA_IMG_1_MEDIUM") == null ? "" : miniProductContentWrapper.get("XTRA_IMG_1_MEDIUM").toString();
            String additionalImage1Large = miniProductContentWrapper.get("XTRA_IMG_1_LARGE") == null ? "" : miniProductContentWrapper.get("XTRA_IMG_1_LARGE").toString();
            String additionalImage1Orginal = miniProductContentWrapper.get("XTRA_IMG_1_ORIGINAL") == null ? "" : miniProductContentWrapper.get("XTRA_IMG_1_ORIGINAL").toString();
            String additionalImage1Small = miniProductContentWrapper.get("XTRA_IMG_1_SMALL") == null ? "" : miniProductContentWrapper.get("XTRA_IMG_1_SMALL").toString();
            List additionalImage1 = FastList.newInstance();
            if (!additionalImage1Detail.equals("")) {
                Map imageMap = new HashMap();
                imageMap.put("additionalImageDetail", additionalImage1Detail);
                additionalImage1.add(imageMap);
            }
            if (!additionalImage1Dedium.equals("")) {
                Map imageMap = new HashMap();
                imageMap.put("additionalImageDedium", additionalImage1Dedium);
                additionalImage1.add(imageMap);
            }
            if (!additionalImage1Large.equals("")) {
                Map imageMap = new HashMap();
                imageMap.put("additionalImageLarge", additionalImage1Large);
                additionalImage1.add(imageMap);
            }
            if (!additionalImage1Orginal.equals("")) {
                Map imageMap = new HashMap();
                imageMap.put("additionalImageOrginal", additionalImage1Orginal);
                additionalImage1.add(imageMap);
            }
            if (!additionalImage1Small.equals("")) {
                Map imageMap = new HashMap();
                imageMap.put("additionalImageSmall", additionalImage1Small);
                additionalImage1.add(imageMap);
            }


            String additionalImage2Detail = miniProductContentWrapper.get("XTRA_IMG_2_DETAIL") == null ? "" : miniProductContentWrapper.get("XTRA_IMG_2_DETAIL").toString();
            String additionalImage2Dedium = miniProductContentWrapper.get("XTRA_IMG_2_MEDIUM") == null ? "" : miniProductContentWrapper.get("XTRA_IMG_2_MEDIUM").toString();
            String additionalImage2Large = miniProductContentWrapper.get("XTRA_IMG_2_LARGE") == null ? "" : miniProductContentWrapper.get("XTRA_IMG_2_LARGE").toString();
            String additionalImage2Orginal = miniProductContentWrapper.get("XTRA_IMG_2_ORIGINAL") == null ? "" : miniProductContentWrapper.get("XTRA_IMG_2_ORIGINAL").toString();
            String additionalImage2Small = miniProductContentWrapper.get("XTRA_IMG_2_SMALL") == null ? "" : miniProductContentWrapper.get("XTRA_IMG_2_SMALL").toString();

            List additionalImage2 = FastList.newInstance();
            if (!additionalImage2Detail.equals("")) {
                Map imageMap = new HashMap();
                imageMap.put("additionalImageDetail", additionalImage2Detail);
                additionalImage2.add(imageMap);
            }
            if (!additionalImage2Dedium.equals("")) {
                Map imageMap = new HashMap();
                imageMap.put("additionalImageDedium", additionalImage2Dedium);
                additionalImage2.add(imageMap);
            }
            if (!additionalImage2Large.equals("")) {
                Map imageMap = new HashMap();
                imageMap.put("additionalImageLarge", additionalImage2Large);
                additionalImage2.add(imageMap);
            }
            if (!additionalImage2Orginal.equals("")) {
                Map imageMap = new HashMap();
                imageMap.put("additionalImageOrginal", additionalImage2Orginal);
                additionalImage2.add(imageMap);
            }
            if (!additionalImage2Small.equals("")) {
                Map imageMap = new HashMap();
                imageMap.put("additionalImageSmall", additionalImage2Small);
                additionalImage2.add(imageMap);
            }


            String additionalImage3Detail = miniProductContentWrapper.get("XTRA_IMG_3_DETAIL") == null ? "" : miniProductContentWrapper.get("XTRA_IMG_3_DETAIL").toString();
            String additionalImage3Dedium = miniProductContentWrapper.get("XTRA_IMG_3_MEDIUM") == null ? "" : miniProductContentWrapper.get("XTRA_IMG_3_MEDIUM").toString();
            String additionalImage3Large = miniProductContentWrapper.get("XTRA_IMG_3_LARGE") == null ? "" : miniProductContentWrapper.get("XTRA_IMG_3_LARGE").toString();
            String additionalImage3Orginal = miniProductContentWrapper.get("XTRA_IMG_3_ORIGINAL") == null ? "" : miniProductContentWrapper.get("XTRA_IMG_3_ORIGINAL").toString();
            String additionalImage3Small = miniProductContentWrapper.get("XTRA_IMG_3_SMALL") == null ? "" : miniProductContentWrapper.get("XTRA_IMG_3_SMALL").toString();


            List additionalImage3 = FastList.newInstance();
            if (!additionalImage3Detail.equals("")) {
                Map imageMap = new HashMap();
                imageMap.put("additionalImageDetail", additionalImage3Detail);
                additionalImage3.add(imageMap);
            }
            if (!additionalImage3Dedium.equals("")) {
                Map imageMap = new HashMap();
                imageMap.put("additionalImageDedium", additionalImage3Dedium);
                additionalImage3.add(imageMap);
            }
            if (!additionalImage3Large.equals("")) {
                Map imageMap = new HashMap();
                imageMap.put("additionalImageLarge", additionalImage3Large);
                additionalImage3.add(imageMap);
            }
            if (!additionalImage3Orginal.equals("")) {
                Map imageMap = new HashMap();
                imageMap.put("additionalImageOrginal", additionalImage3Orginal);
                additionalImage3.add(imageMap);
            }
            if (!additionalImage3Small.equals("")) {
                Map imageMap = new HashMap();
                imageMap.put("additionalImageSmall", additionalImage3Small);
                additionalImage3.add(imageMap);
            }

            String additionalImage4Detail = miniProductContentWrapper.get("XTRA_IMG_4_DETAIL") == null ? "" : miniProductContentWrapper.get("XTRA_IMG_4_DETAIL").toString();
            String additionalImage4Dedium = miniProductContentWrapper.get("XTRA_IMG_4_MEDIUM") == null ? "" : miniProductContentWrapper.get("XTRA_IMG_4_MEDIUM").toString();
            String additionalImage4Large = miniProductContentWrapper.get("XTRA_IMG_4_LARGE") == null ? "" : miniProductContentWrapper.get("XTRA_IMG_4_LARGE").toString();
            String additionalImage4Orginal = miniProductContentWrapper.get("XTRA_IMG_4_ORIGINAL") == null ? "" : miniProductContentWrapper.get("XTRA_IMG_4_ORIGINAL").toString();
            String additionalImage4Small = miniProductContentWrapper.get("XTRA_IMG_4_SMALL") == null ? "" : miniProductContentWrapper.get("XTRA_IMG_4_SMALL").toString();


            List additionalImage4 = FastList.newInstance();
            if (!additionalImage4Detail.equals("")) {
                Map imageMap = new HashMap();
                imageMap.put("additionalImageDetail", additionalImage4Detail);
                additionalImage4.add(imageMap);
            }
            if (!additionalImage4Dedium.equals("")) {
                Map imageMap = new HashMap();
                imageMap.put("additionalImageDedium", additionalImage4Dedium);
                additionalImage4.add(imageMap);
            }
            if (!additionalImage4Large.equals("")) {
                Map imageMap = new HashMap();
                imageMap.put("additionalImageLarge", additionalImage4Large);
                additionalImage4.add(imageMap);
            }
            if (!additionalImage4Orginal.equals("")) {
                Map imageMap = new HashMap();
                imageMap.put("additionalImageOrginal", additionalImage4Orginal);
                additionalImage4.add(imageMap);
            }
            if (!additionalImage4Small.equals("")) {
                Map imageMap = new HashMap();
                imageMap.put("additionalImageSmall", additionalImage4Small);
                additionalImage4.add(imageMap);
            }


            String additionalImage5Detail = miniProductContentWrapper.get("XTRA_IMG_5_DETAIL") == null ? "" : miniProductContentWrapper.get("XTRA_IMG_5_DETAIL").toString();
            String additionalImage5Dedium = miniProductContentWrapper.get("XTRA_IMG_5_MEDIUM") == null ? "" : miniProductContentWrapper.get("XTRA_IMG_5_MEDIUM").toString();
            String additionalImage5Large = miniProductContentWrapper.get("XTRA_IMG_5_LARGE") == null ? "" : miniProductContentWrapper.get("XTRA_IMG_5_LARGE").toString();
            String additionalImage5Orginal = miniProductContentWrapper.get("XTRA_IMG_5_ORIGINAL") == null ? "" : miniProductContentWrapper.get("XTRA_IMG_5_ORIGINAL").toString();
            String additionalImage5Small = miniProductContentWrapper.get("XTRA_IMG_5_SMALL") == null ? "" : miniProductContentWrapper.get("XTRA_IMG_5_SMALL").toString();

            List additionalImage5 = FastList.newInstance();
            if (!additionalImage5Detail.equals("")) {
                Map imageMap = new HashMap();
                imageMap.put("additionalImageDetail", additionalImage5Detail);
                additionalImage5.add(imageMap);
            }
            if (!additionalImage5Dedium.equals("")) {
                Map imageMap = new HashMap();
                imageMap.put("additionalImageDedium", additionalImage5Dedium);
                additionalImage5.add(imageMap);
            }
            if (!additionalImage5Large.equals("")) {
                Map imageMap = new HashMap();
                imageMap.put("additionalImageLarge", additionalImage5Large);
                additionalImage5.add(imageMap);
            }
            if (!additionalImage5Orginal.equals("")) {
                Map imageMap = new HashMap();
                imageMap.put("additionalImageOrginal", additionalImage5Orginal);
                additionalImage5.add(imageMap);
            }
            if (!additionalImage5Small.equals("")) {
                Map imageMap = new HashMap();
                imageMap.put("additionalImageSmall", additionalImage5Small);
                additionalImage5.add(imageMap);
            }

            String additionalImage6Detail = miniProductContentWrapper.get("XTRA_IMG_6_DETAIL") == null ? "" : miniProductContentWrapper.get("XTRA_IMG_6_DETAIL").toString();
            String additionalImage6Dedium = miniProductContentWrapper.get("XTRA_IMG_6_MEDIUM") == null ? "" : miniProductContentWrapper.get("XTRA_IMG_6_MEDIUM").toString();
            String additionalImage6Large = miniProductContentWrapper.get("XTRA_IMG_6_LARGE") == null ? "" : miniProductContentWrapper.get("XTRA_IMG_6_LARGE").toString();
            String additionalImage6Orginal = miniProductContentWrapper.get("XTRA_IMG_6_ORIGINAL") == null ? "" : miniProductContentWrapper.get("XTRA_IMG_6_ORIGINAL").toString();
            String additionalImage6Small = miniProductContentWrapper.get("XTRA_IMG_6_SMALL") == null ? "" : miniProductContentWrapper.get("XTRA_IMG_6_SMALL").toString();
            List additionalImage6 = FastList.newInstance();
            if (!additionalImage6Detail.equals("")) {
                Map imageMap = new HashMap();
                imageMap.put("additionalImageDetail", additionalImage6Detail);
                additionalImage6.add(imageMap);
            }
            if (!additionalImage6Dedium.equals("")) {
                Map imageMap = new HashMap();
                imageMap.put("additionalImage6Dedium", additionalImage6Dedium);
                additionalImage6.add(imageMap);
            }
            if (!additionalImage6Large.equals("")) {
                Map imageMap = new HashMap();
                imageMap.put("additionalImageLarge", additionalImage6Large);
                additionalImage6.add(imageMap);
            }
            if (!additionalImage6Orginal.equals("")) {
                Map imageMap = new HashMap();
                imageMap.put("additionalImageOrginal", additionalImage6Orginal);
                additionalImage6.add(imageMap);
            }
            if (!additionalImage6Small.equals("")) {
                Map imageMap = new HashMap();
                imageMap.put("additionalImageSmall", additionalImage6Small);
                additionalImage6.add(imageMap);
            }

            //增加虚拟产品对应的信息featureTypeId,featureId

            if (isVirtual.equals("Y")) {

                GenericValue product = delegator.findByPrimaryKey("Product", UtilMisc.toMap("productId", productId));
                List<List<Map<String, String>>> featureLists = ProductWorker.getSelectableProductFeaturesByTypesAndSeq(product);
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
                    if (variantTreeMap != null && variantTreeMap.get("variantTreeChoose") != null)
                        result.put("variantTreeChoose", variantTreeMap.get("variantTreeChoose"));
//                    result.put("variantsRes", variantsRes);
                    List<GenericValue> assocProducts = (List<GenericValue>) variantsRes.get("assocProducts");
                }

            }
            List<Map> imgsList = FastList.newInstance();
            Map<String, List> imgsMap1 = FastMap.newInstance();
            if(UtilValidate.isNotEmpty(additionalImage1)) imgsMap1.put("additionalImage1", additionalImage1);
            imgsList.add(imgsMap1);

            Map<String, List> imgsMap2 = FastMap.newInstance();
            if(UtilValidate.isNotEmpty(additionalImage2)) imgsMap2.put("additionalImage2", additionalImage2);
            imgsList.add(imgsMap2);

            Map<String, List> imgsMap3 = FastMap.newInstance();
            if(UtilValidate.isNotEmpty(additionalImage3)) imgsMap3.put("additionalImage3", additionalImage3);
            imgsList.add(imgsMap3);

            Map<String, List> imgsMap4 = FastMap.newInstance();
            if(UtilValidate.isNotEmpty(additionalImage4)) imgsMap4.put("additionalImage4", additionalImage4);
            imgsList.add(imgsMap4);

            Map<String, List> imgsMap5 = FastMap.newInstance();
            if(UtilValidate.isNotEmpty(additionalImage5)) imgsMap5.put("additionalImage5", additionalImage5);
            imgsList.add(imgsMap5);


            Map<String, List> imgsMap6 = FastMap.newInstance();
            if(UtilValidate.isNotEmpty(additionalImage6)) imgsMap6.put("additionalImage6", additionalImage6);
            imgsList.add(imgsMap6);

            List<GenericValue> productTags =  delegator.findByAnd("ProductTag",UtilMisc.toMap("productId",productId));
            List<String> tagNames = FastList.newInstance();
            productTags = EntityUtil.filterByDate(productTags);
            if(UtilValidate.isNotEmpty(productTags)){
                for (int i = 0; i < productTags.size(); i++) {
                    GenericValue productTag = productTags.get(i);
                    tagNames.add((String)productTag.get("tagName"));
                }
            }
            result.put("productTags",tagNames);

            result.put("additionalImages", imgsList);
            result.put("description", description);
            result.put("productId", productId);
            result.put("catalogId", prodCatalogId);
            result.put("mediumImageUrl", mediumImageUrl);
            result.put("smallImageUrl", smallImageUrl);
//            result.put("longDescription",longDescription);
            result.put("productName", productName);
            result.put("isVirtual", isVirtual);
            result.put("largeImageUrl", largeImageUrl);
            result.put("originalImageUrl", originalImageUrl);
            result.put("productTypeId", miniProduct.get("productTypeId"));
            result.put("wrapProductId", wrapProductId);
            return result;
        }
        return ServiceUtil.returnError(UtilProperties.getMessage(resourceError, "productevents.product_with_id_not_found", new Object[]{productId}, locale));
    }

    public static Map<String, Object> productContents(DispatchContext dctx, Map<String, ? extends Object> context) {
        Locale locale = (Locale) context.get("locale");
        Delegator delegator = dctx.getDelegator();
        String productId = (String) context.get("productId");
        String contentTypeId = (String) context.get("contentTypeId");
        Map<String, Object> result = ServiceUtil.returnSuccess();
        Map resultData = FastMap.newInstance();
        GenericValue miniProduct = null;
        try {
            miniProduct = delegator.findByPrimaryKey("Product", UtilMisc.toMap("productId", productId));
        } catch (GenericEntityException e) {
            return ServiceUtil.returnError(e.getMessage());
        }
        ProductContentWrapper miniProductContentWrapper = new ProductContentWrapper(dctx.getDispatcher(), miniProduct, locale, "text/html");
        if (contentTypeId == null) {

            try {
                List<GenericValue> contentTypes = delegator.findList("ProductContentType", null, null, null, null, true);
                if (contentTypes.size() > 0) {
                    for (int i = 0; i < contentTypes.size(); i++) {
                        GenericValue productContentType = contentTypes.get(i);
                        String contType = (String) productContentType.get("ProductContentTypeId");
                        String content = miniProductContentWrapper.get(contType).toString();
                        resultData.put(contType, content);
                    }
                }
            } catch (GenericEntityException e) {
                e.printStackTrace();
                ServiceUtil.returnFailure(e.getMessage());
            }
        } else {

            // make the miniProductContentWrapper
            String content = miniProductContentWrapper.get(contentTypeId).toString();
            resultData.put(contentTypeId, content);
        }
        result.put("productContents", resultData);
        return result;
    }


    /**
     * 产品评论
     *
     * @param dctx
     * @param context
     * @return
     */
    public Map<String, Object> productReview(DispatchContext dctx, Map<String, ? extends Object> context) {
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
                List<GenericValue> ratingReviews = EntityUtil.filterByAnd(reviews, UtilMisc.toList(EntityCondition.makeCondition("productRating", EntityOperator.NOT_EQUAL, null)));
                if (result != null && (!ratingReviews.isEmpty())) {
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
     * @param dctx
     * @param context
     * @return
     */
    public Map<String, Object> productCategoryList(DispatchContext dctx, Map<String, ? extends Object> context) {
        Map<String, Object> result = ServiceUtil.returnSuccess();
        Delegator delegator = dctx.getDelegator();
        List<GenericValue> catalogs = null;
        List cateList = FastList.newInstance();
        Map resultData = FastMap.newInstance();
        try {
            catalogs = delegator.findList("ProdCatalog", null, null, null, null, false);
        } catch (GenericEntityException e) {
            e.printStackTrace();
            return ServiceUtil.returnFailure(e.getMessage());
        }
        if (!catalogs.isEmpty()) {
            for (int i = 0; i < catalogs.size(); i++) {
                GenericValue catalog = catalogs.get(i);
                String catalogId = (String) catalog.get("prodCatalogId");
                try {
                    List<GenericValue> categories = EntityUtil.filterByDate(delegator.findByAnd("ProdCatalogCategory", UtilMisc.toMap("prodCatalogId", catalogId)));
                    if (!categories.isEmpty()) {
                        for (int j = 0; j < categories.size(); j++) {
                            GenericValue prodCategory = categories.get(j);
                            List<GenericValue> pcategories = prodCategory.getRelated("ProductCategory");
                            cateList.add(pcategories);
                        }
                    }

                } catch (GenericEntityException e) {
                    e.printStackTrace();
                    return ServiceUtil.returnFailure(e.getMessage());
                }

            }
        }
        result.put("categories", cateList);
        return result;
    }


    /**
     * id:当前节点,
     * type:当前节点类型
     * level:当前节点level
     *
     * @param dcx
     * @param context
     * @return
     */
    public static final Map<String, Object> getCatalogCateTree(DispatchContext dcx, Map<String, ? extends Object> context) {
        Map<String, Object> result = ServiceUtil.returnSuccess();
        String id = (String) context.get("id");
        String type = (String) context.get("type");
        Integer level = (Integer) context.get("level");
        String scope = (String)context.get("scope");
        Delegator delegator = dcx.getDelegator();
        List<Map> gProdCatalogList = FastList.newInstance();
        LocalDispatcher dispatcher = dcx.getDispatcher();
        try {
            List partyGroups = delegator.findByAnd("PartyGroup", UtilMisc.toMap("isInner", "Y"));
            String partyId = "0";
            if (UtilValidate.isNotEmpty(partyGroups)) {
                GenericValue party = (GenericValue) partyGroups.get(0);
                partyId = (String) party.get("partyId");
            }
          
            if (id.equals("0") && type.equals("catalog")) {
                //第一次加载
                 
                    List<GenericValue> proStoreCatas = delegator.findByAnd("ProdCatalogStore",  null, UtilMisc.toList("+sequenceNum"));
                    if (UtilValidate.isNotEmpty(proStoreCatas)) {
                        for (int i = 0; i < proStoreCatas.size(); i++) {
                            GenericValue prodCata = proStoreCatas.get(i);
                            Map gProdCatalogMap = FastMap.newInstance();
                            gProdCatalogMap.put("id", prodCata.getString("prodCatalogId") + "_catalog");
                            gProdCatalogMap.put("name", UtilProperties.getMessage(resource, "ProductCatalogs", (Locale) context.get("locale")) + "-" + prodCata.getString("catalogName"));
                            gProdCatalogMap.put("parent", "0");
                            gProdCatalogMap.put("type", "catalog");
                            gProdCatalogMap.put("level", level + 1);
                            gProdCatalogMap.put("isCatalog", "true");
                            gProdCatalogMap.put("isCategoryType", "false");
                            if (UtilValidate.isNotEmpty(proStoreCatas)) {
                                gProdCatalogMap.put("seq", prodCata.get("sequenceNum"));
                            } else {
                                gProdCatalogMap.put("seq", "1");
                            }
                            gProdCatalogList.add(gProdCatalogMap);
                        }
                    
                }
            } else if (!id.equals("0") && (type.equals("catalog"))) {
                id = id.substring(0, id.indexOf("_catalog"));
                List<GenericValue> prodCatalogCategories = EntityUtil.filterByDate(delegator.findByAnd("ProdCatalogCategory", UtilMisc.toMap("prodCatalogId", id), UtilMisc.toList("+sequenceNum")));
                if (UtilValidate.isNotEmpty(prodCatalogCategories)) {
                    for (int j = 0; j < prodCatalogCategories.size(); j++) {
                        Map gCategoryMap = FastMap.newInstance();
                        GenericValue cataCategory = prodCatalogCategories.get(j);
                        GenericValue productCategory = cataCategory.getRelatedOne("ProductCategory");
                        gCategoryMap.put("id", productCategory.getString("productCategoryId") + "_category");
                        gCategoryMap.put("name", UtilProperties.getMessage(resource, "ProductCategories", (Locale) context.get("locale")) + "-" + productCategory.getString("categoryName"));
                        gCategoryMap.put("parent", id);
                        gCategoryMap.put("type", "category");
                        gCategoryMap.put("level", level + 1);
                        gCategoryMap.put("isCatalog", "false");
                        gCategoryMap.put("isCategoryType", "true");
                        if (UtilValidate.isNotEmpty(cataCategory.get("sequenceNum"))) {
                            gCategoryMap.put("seq", cataCategory.get("sequenceNum"));
                        } else {
                            gCategoryMap.put("seq", "1");
                        }
                        gProdCatalogList.add(gCategoryMap);
                    }
                }


            } else if (!id.equals("0") && (type.equals("category"))) {
                id = id.substring(0, id.indexOf("_category"));
                List<GenericValue> childOfCats = EntityUtil.filterByDate(delegator.findByAnd("ProductCategoryRollupAndChild", UtilMisc.toMap(
                        "parentProductCategoryId", id), UtilMisc.toList("+sequenceNum")));
                if (UtilValidate.isNotEmpty(childOfCats)) {
                    for (int i = 0; i < childOfCats.size(); i++) {
                        Map gCategoryMap = FastMap.newInstance();
                        GenericValue cateCategory = childOfCats.get(i);
                        gCategoryMap.put("id", cateCategory.getString("productCategoryId") + "_category");
                        gCategoryMap.put("name", UtilProperties.getMessage(resource, "ProductCategories", (Locale) context.get("locale")) + "-" + cateCategory.getString("categoryName"));
                        gCategoryMap.put("parent", id);
                        gCategoryMap.put("type", "category");
                        gCategoryMap.put("level", level + 1);
                        gCategoryMap.put("isCatalog", "false");
                        gCategoryMap.put("isCategoryType", "true");
                        if (UtilValidate.isNotEmpty(cateCategory.get("sequenceNum"))) {
                            gCategoryMap.put("seq", cateCategory.get("sequenceNum"));
                        } else {
                            gCategoryMap.put("seq", "1");
                        }
                        gProdCatalogList.add(gCategoryMap);
                    }
                }
                //获取分类下的产品
                Map paramInMap = FastMap.newInstance();
                paramInMap.put("productCategoryId", id);

                paramInMap.put("limitView", false);
                paramInMap.put("useCacheForMembers", true);
                paramInMap.put("defaultViewSize", 25);
                // Returns: viewIndex, viewSize, lowIndex, highIndex, listSize, productCategory, productCategoryMembers
                if(UtilValidate.isEmpty(scope) || (!scope.equals("category"))) {
                    Map result1 = dispatcher.runSync("getProductCategoryAndLimitedMembers", paramInMap);
                    if (ServiceUtil.isError(result1)) {
                        return ServiceUtil.returnError(ServiceUtil.getErrorMessage(result1));
                    }
                    if (result1.get("productCategoryMembers") != null) {
                        List<GenericValue> productCategoryMembers = (List<GenericValue>) result1.get("productCategoryMembers");
                        if (UtilValidate.isNotEmpty(productCategoryMembers)) {
                            for (int i = 0; i < productCategoryMembers.size(); i++) {
                                GenericValue categoryMember = productCategoryMembers.get(i);
                                Map gCategoryMap = FastMap.newInstance();
                                gCategoryMap.put("id", categoryMember.getString("productId") + "_productId");
                                GenericValue product = categoryMember.getRelatedOne("Product");
                                gCategoryMap.put("name", UtilProperties.getMessage(resource, "ProductProducts", (Locale) context.get("locale")) + "-" + product.getString("productName"));
                                gCategoryMap.put("parent", id);
                                gCategoryMap.put("type", "product");
                                gCategoryMap.put("level", level + 1);
                                gCategoryMap.put("isCatalog", "false");
                                gCategoryMap.put("isCategoryType", "false");
                                if (UtilValidate.isNotEmpty(categoryMember.get("sequenceNum"))) {
                                    gCategoryMap.put("seq", categoryMember.get("sequenceNum"));
                                } else {
                                    gCategoryMap.put("seq", "1");
                                }
                                gProdCatalogList.add(gCategoryMap);
                            }
                        }
                    }
                }

            }else if (id.equals("0") && (type.equals("category"))) {
                List<GenericValue> prodCatalogCategories = EntityUtil.filterByDate(delegator.findByAnd("ProdCatalogCategory", null, UtilMisc.toList("+sequenceNum")));
                if (UtilValidate.isNotEmpty(prodCatalogCategories)) {
                    for (int j = 0; j < prodCatalogCategories.size(); j++) {
                        Map gCategoryMap = FastMap.newInstance();
                        GenericValue cataCategory = prodCatalogCategories.get(j);
                        GenericValue productCategory = cataCategory.getRelatedOne("ProductCategory");
                        gCategoryMap.put("id", productCategory.getString("productCategoryId") + "_category");
                        gCategoryMap.put("name", UtilProperties.getMessage(resource, "ProductCategories", (Locale) context.get("locale")) + "-" + productCategory.getString("categoryName"));
                        gCategoryMap.put("parent", id);
                        gCategoryMap.put("type", "category");
                        gCategoryMap.put("level", level + 1);
                        gCategoryMap.put("isCatalog", "false");
                        gCategoryMap.put("isCategoryType", "true");
                        if (UtilValidate.isNotEmpty(cataCategory.get("sequenceNum"))) {
                            gCategoryMap.put("seq", cataCategory.get("sequenceNum"));
                        } else {
                            gCategoryMap.put("seq", "1");
                        }
                        gProdCatalogList.add(gCategoryMap);
                    }
                }
            }
        } catch (GenericEntityException e) {
            e.printStackTrace();
            return ServiceUtil.returnError(e.getMessage());
        } catch (GenericServiceException e) {
            e.printStackTrace();
            return ServiceUtil.returnError(e.getMessage());
        }
        result.put("nodes", gProdCatalogList);
        return result;
    }

    /**
     * @param dcx
     * @param context
     * @return
     */
    public static final Map<String, Object> catalogNodeCreate(DispatchContext dcx, Map<String, ? extends Object> context) {
        Map<String, Object> result = ServiceUtil.returnSuccess();
        String parent = (String) context.get("parent");
        String name = (String) context.get("name");
        //before after firstChild lastChild
        String position = (String) context.get("position");
        String related = (String) context.get("related");
        Delegator delegator = dcx.getDelegator();
        GenericValue userLogin = (GenericValue) context.get("userLogin");
        String parentType = (String) context.get("parentType");
        String relatedType = (String) context.get("relatedType");
        String action = (String)context.get("action");
        String targetId = (String)context.get("targetId");
        LocalDispatcher localDispatcher = dcx.getDispatcher();
        try {
            List<GenericValue> updateList = FastList.newInstance();
            if (parentType.equals("catalog")) {
                String pCatalogId = "0";
                if (parent.indexOf("_catalog") != -1) {
                    pCatalogId = parent.substring(0, parent.indexOf("_catalog"));
                }
                String categoryId = "";
                Long num = 1l;
                if (position.equals("before") || position.equals("after")) {
                    if (relatedType.equals("catalog")) {
                        related = related.substring(0, related.indexOf("_catalog"));
                        GenericValue currentCatalog = EntityUtil.filterByDate(delegator.findByAnd("ProductStoreCatalog", UtilMisc.toMap("prodCatalogId", related))).get(0);
                        num = currentCatalog.get("sequenceNum") == null ? 1l : (Long) currentCatalog.get("sequenceNum");
                        List proStoreCatalogs = delegator.findList("ProductStoreCatalog", null, null, UtilMisc.toList("+sequenceNum"), null, false);
                        String productStoreId = null;
                        if (position.endsWith("before")) {
                            //当前catalog及之后的catalog num+1;
                            for (int i = 0; i < proStoreCatalogs.size(); i++) {
                                GenericValue proStoreCatalog = (GenericValue) proStoreCatalogs.get(i);
                                Long currentNum = proStoreCatalog.get("sequenceNum") == null ? 1l : (Long) proStoreCatalog.get("sequenceNum");
                                if (currentNum >= num) {
                                    proStoreCatalog.put("sequenceNum", currentNum + 1);
                                    updateList.add(proStoreCatalog);
                                }
                                productStoreId = (String) proStoreCatalog.get("productStoreId");
                            }
                        } else if (position.equals("after")) {
                            //当前不变,之后之后的category num+1;
                            for (int i = 0; i < proStoreCatalogs.size(); i++) {
                                GenericValue proStoreCatalog = (GenericValue) proStoreCatalogs.get(i);
                                Long currentNum = proStoreCatalog.get("sequenceNum") == null ? 1l : (Long) proStoreCatalog.get("sequenceNum");
                                if (currentNum > num) {
                                    proStoreCatalog.put("sequenceNum", currentNum + 1);
                                    updateList.add(proStoreCatalog);
                                }
                                productStoreId = (String) proStoreCatalog.get("productStoreId");
                            }
                            num = num + 1;
                        }
                        Map<String, Object> result1 = localDispatcher.runSync("createProdCatalog", UtilMisc.toMap("catalogName", name, "productStoreId", productStoreId, "fromDate", UtilDateTime.nowTimestamp(), "sequenceNum", num, "userLogin", userLogin));
                        if (ServiceUtil.isError(result1)) {
                            return ServiceUtil.returnError(ServiceUtil.getErrorMessage(result1));
                        }
                        result.put("id", result1.get("prodCatalogId") + "_catalog");
                        result.put("name", UtilProperties.getMessage(resource, "ProductCatalogs", (Locale) context.get("locale")) + "-" + name);
                        result.put("level", 1);
                        result.put("type", "catalog");
                        result.put("seq", num.intValue());
                        result.put("parent", pCatalogId);

                    } else {
                        if (relatedType.equals("category")) {
                            related = related.substring(0, related.indexOf("_category"));
                            //目录下创建分类
                            categoryId = related;
                            List<GenericValue> curCategories = delegator.findByAnd("ProdCatalogCategory", UtilMisc.toMap("prodCatalogId", pCatalogId, "productCategoryId", categoryId));
                            curCategories = EntityUtil.filterByDate(curCategories);
                            if (UtilValidate.isNotEmpty(curCategories)) {
                                num = curCategories.get(0).get("sequenceNum") == null ? 1l : (Long) curCategories.get(0).get("sequenceNum");
                            }

                            List<GenericValue> pCatalogCategories = delegator.findByAnd("ProdCatalogCategory", UtilMisc.toMap("prodCatalogId", pCatalogId), UtilMisc.toList("+sequenceNum"));
                            if (position.endsWith("before")) {
                                //当前category及之后的category num+1;
                                for (int i = 0; i < pCatalogCategories.size(); i++) {
                                    GenericValue pCatalogCate = pCatalogCategories.get(i);
                                    Long currentNum = pCatalogCate.get("sequenceNum") == null ? 1l : (Long) pCatalogCate.get("sequenceNum");
                                    if (currentNum >= num) {
                                        pCatalogCate.put("sequenceNum", currentNum + 1);
                                        updateList.add(pCatalogCate);
                                    }
                                }
                            } else if (position.equals("after")) {
                                //当前不变,之后之后的category num+1;
                                for (int i = 0; i < pCatalogCategories.size(); i++) {
                                    GenericValue pCatalogCate = pCatalogCategories.get(i);
                                    Long currentNum = pCatalogCate.get("sequenceNum") == null ? 1l : (Long) pCatalogCate.get("sequenceNum");
                                    if (currentNum > num) {
                                        pCatalogCate.put("sequenceNum", currentNum + 1);
                                        updateList.add(pCatalogCate);
                                    }
                                }
                                num = num + 1;
                            }


                        }
                    }
                }
                if (position.equals("firstChild") || position.equals("lastChild")) {
                    List<GenericValue> iterator = delegator.findByAnd("ProdCatalogCategory", UtilMisc.toMap("prodCatalogId", pCatalogId), UtilMisc.toList("+sequenceNum"));
                    if (UtilValidate.isNotEmpty(iterator)) {
                        for (int i = 0; i < iterator.size(); i++) {
                            GenericValue catalogCate = iterator.get(i);
                            if (position.equals("firstChild")) {
                                //第一个 则 所有+1
                                catalogCate.put("sequenceNum", catalogCate.get("sequenceNum") == null ? 1l : (Long) catalogCate.get("sequenceNum") + 1);
                                updateList.add(catalogCate);
                                num = 1l;
                            }
                            if (position.equals("lastChild")) {
                                if (i == iterator.size() - 1) {
                                    //取最后一个的sequenceNum
                                    num = catalogCate.get("sequenceNum") == null ? 1l : (Long) catalogCate.get("sequenceNum") + 1;
                                }
                            }
                        }

                    }
                    String id = targetId;
                    if(action.equals("1")){

                    }else {
                        Map<String, Object> result1 = localDispatcher.runSync("createProductCategory", UtilMisc.toMap("userLogin", userLogin, "productCategoryTypeId", "CATALOG_CATEGORY", "categoryName", name, "description", name));
                        if (ServiceUtil.isError(result1)) {
                            return ServiceUtil.returnError(ServiceUtil.getErrorMessage(result1));
                        }
                         id = (String) result1.get("productCategoryId");
                    }
                    Map<String,Object> result2 = localDispatcher.runSync("addProductCategoryToProdCatalog", UtilMisc.toMap("userLogin", userLogin, "productCategoryId", id, "prodCatalogId", pCatalogId,
                            "prodCatalogCategoryTypeId", "PCCT_BROWSE_ROOT", "fromDate", UtilDateTime.nowTimestamp(), "sequenceNum", num));

                    if (ServiceUtil.isError(result2)) {
                        return ServiceUtil.returnError(ServiceUtil.getErrorMessage(result2));
                    }
                    delegator.storeAll(updateList);
//                {"id":8273,"name":"ddsssaaassss","level":2,"type":"default"}
                    result.put("id", id + "_category");
                    result.put("name", UtilProperties.getMessage(resource, "ProductCategories", (Locale) context.get("locale")) + "-" + name);
                    result.put("level", 2);
                    result.put("type", "category");
                    result.put("seq", num.intValue());
                    result.put("parent", pCatalogId);
                }

            } else if (parentType.equals("category")) {
                parent = parent.substring(0, parent.indexOf("_category"));
                Long num = 1l;
                String pCategory = parent;
                if (position.equals("before") || position.equals("after")) {
                    if (relatedType.equals("category")) {
                        //目录下创建分类
                        related = related.substring(0, related.indexOf("_category"));
                        String categoryId = related;
                        List<GenericValue> childOfCats = delegator.findByAnd("ProductCategoryRollupAndChild", UtilMisc.toMap(
                                "parentProductCategoryId", pCategory, "productCategoryId", categoryId));

                        List<GenericValue> allChildOfCats = delegator.findByAnd("ProductCategoryRollup", UtilMisc.toMap(
                                "parentProductCategoryId", pCategory));
                        if (UtilValidate.isNotEmpty(childOfCats)) {
                            Long curNum = childOfCats.get(0).get("sequenceNum") == null ? 1l : (Long) childOfCats.get(0).get("sequenceNum");
                            for (int i = 0; i < allChildOfCats.size(); i++) {
                                GenericValue allCate = allChildOfCats.get(i);
                                Long allNum = allCate.get("sequenceNum") == null ? 1l : (Long) allCate.get("sequenceNum");
                                if (position.equals("before")) {
                                    //新增的为当前的
                                    if (allNum >= curNum) {
                                        allCate.put("sequenceNum", curNum + 1);
                                        updateList.add(allCate);
                                    }

                                } else if (position.equals("after")) {
                                    if (allNum > curNum) {
                                        allCate.put("sequenceNum", allNum + 1);
                                        updateList.add(allCate);
                                    }
                                }
                            }
                            if (position.equals("before")) {
                                num = curNum;
                            } else if (position.equals("after")) {
                                num = curNum + 1;
                            }
                        }
                    }

                } else if (position.equals("firstChild") || position.equals("lastChild")) {
                    List<GenericValue> iterator = delegator.findByAnd("ProductCategoryRollup", UtilMisc.toMap("parentProductCategoryId", pCategory), UtilMisc.toList("+sequenceNum"));
                    if (UtilValidate.isNotEmpty(iterator)) {
                        for (int i = 0; i < iterator.size(); i++) {
                            GenericValue catalogCate = iterator.get(i);
                            if (position.equals("firstChild")) {
                                //第一个 则 所有+1
                                catalogCate.put("sequenceNum", catalogCate.get("sequenceNum") == null ? 1l : (Long) catalogCate.get("sequenceNum") + 1);
                                updateList.add(catalogCate);
                            }
                            if (position.equals("lastChild")) {
                                if (i == iterator.size() - 1) {
                                    //取最后一个的sequenceNum
                                    num = catalogCate.get("sequenceNum") == null ? 1l : (Long) catalogCate.get("sequenceNum") + 1;
                                }
                            }
                        }
                        if (position.equals("firstChild")) {
                            num = 1l;
                        }
                    }
                }
                String id = null;
                if(action.equals("1")){
                   id=targetId;
                }else {
                    Map<String, Object> result1 = localDispatcher.runSync("createProductCategory", UtilMisc.toMap("userLogin", userLogin, "productCategoryTypeId", "CATALOG_CATEGORY", "categoryName", name, "description", name));
                    if (ServiceUtil.isError(result1)) {
                        return ServiceUtil.returnError(ServiceUtil.getErrorMessage(result1));
                    }

                     id = (String) result1.get("productCategoryId");
                }

                Map<String,Object> result2 = localDispatcher.runSync("addProductCategoryToCategory", UtilMisc.toMap("userLogin", userLogin, "productCategoryId", id, "parentProductCategoryId", pCategory,
                        "fromDate", UtilDateTime.nowTimestamp(), "sequenceNum", num));

                if (ServiceUtil.isError(result2)) {
                    return ServiceUtil.returnError(ServiceUtil.getErrorMessage(result2));
                }
                delegator.storeAll(updateList);
                result.put("id", id + "_category");
                result.put("name", UtilProperties.getMessage(resource, "ProductCategories", (Locale) context.get("locale")) + "-" + name);
                result.put("level", 3);
                result.put("type", "category");
                result.put("parent", pCategory);
                result.put("seq", num.intValue());
            }
        } catch (GenericEntityException e) {
            e.printStackTrace();
            return ServiceUtil.returnError(e.getMessage());
        } catch (GenericServiceException e) {
            e.printStackTrace();
            return ServiceUtil.returnError(e.getMessage());
        }

        return result;
    }

    public static final Map<String, Object> catalogNodeDelete(DispatchContext dcx, Map<String, ? extends Object> context) {
        Map<String, Object> result = ServiceUtil.returnSuccess();
        String id = (String) context.get("id");
        String type = (String) context.get("type");
        GenericValue userLogin = (GenericValue) context.get("userLogin");
        LocalDispatcher dispatcher = dcx.getDispatcher();
        try {
            if (UtilValidate.isNotEmpty(type)) {
                if (type.equals("catalog")) {
                    id = id.substring(0, id.indexOf("_catalog"));
                    result = dispatcher.runSync("deleteProdCatalog", UtilMisc.toMap("prodCatalogId", id, "userLogin", userLogin));
                    if (ServiceUtil.isError(result)) {
                        return ServiceUtil.returnError(ServiceUtil.getErrorMessage(result));
                    }

                } else if (type.equals("category")) {
                    id = id.substring(0, id.indexOf("_category"));
                    result = dispatcher.runSync("deleteCategory", UtilMisc.toMap("productCategoryId", id, "userLogin", userLogin));
                    if (ServiceUtil.isError(result)) {
                        return ServiceUtil.returnError(ServiceUtil.getErrorMessage(result));
                    }
                } else if (type.equals("product")) {
                    id = id.substring(0, id.indexOf("_product"));
                    result = dispatcher.runSync("removeProduct", UtilMisc.toMap("productId", id, "userLogin", userLogin));
                    if (ServiceUtil.isError(result)) {
                        return ServiceUtil.returnError(ServiceUtil.getErrorMessage(result));
                    }
                }
            }
        } catch (GenericServiceException e) {
            e.printStackTrace();
        }
        return result;
    }


    /**
     * @param dcx
     * @param context
     * @return
     */
    public static final Map<String, Object> catalogNodeUpdate(DispatchContext dcx, Map<String, ? extends Object> context) {
        Map<String, Object> result = ServiceUtil.returnSuccess();

        String name = (String) context.get("name");
        String id = (String) context.get("id");
        String type = (String) context.get("type");

        String parent = (String) context.get("parent");
        //before after firstChild lastChild
        String position = (String) context.get("position");
        String related = (String) context.get("related");
        String parentType = (String) context.get("parentType");
        String relatedType = (String) context.get("relatedType");
        Integer level = (Integer) context.get("level");
        Integer seq = (Integer) context.get("seq");

        Delegator delegator = dcx.getDelegator();
        try {
            if (UtilValidate.isNotEmpty(type)) {
                if (type.equals("catalog")) {
                    id = id.substring(0, id.indexOf("_catalog"));
                    String prefix = UtilProperties.getMessage(resource, "ProductCatalogs", (Locale) context.get("locale")) + "-";
                    if (name.startsWith(prefix)) {
                        name = name.substring(prefix.length());
                    }
                    GenericValue catalog = delegator.findByPrimaryKey("ProdCatalog", UtilMisc.toMap("prodCatalogId", id));
                    catalog.put("catalogName", name);
                    delegator.store(catalog);
                    result.put("id", id + "_catalog");
                    result.put("name", UtilProperties.getMessage(resource, "ProductCatalogs", (Locale) context.get("locale")) + "-" + name);
                    result.put("level", level);
                    result.put("type", "catalog");
                    result.put("parent", parent);
                    result.put("seq", seq);

                } else if (type.equals("category")) {
                    id = id.substring(0, id.indexOf("_category"));
                    String prefix = UtilProperties.getMessage(resource, "ProductCategories", (Locale) context.get("locale")) + "-";
                    if (name.startsWith(prefix)) {
                        name = name.substring(prefix.length());
                    }
                    GenericValue category = delegator.findByPrimaryKey("ProductCategory", UtilMisc.toMap("productCategoryId", id));
                    category.put("categoryName", name);
                    delegator.store(category);
                    result.put("id", id + "_category");
                    result.put("name", UtilProperties.getMessage(resource, "ProductCategories", (Locale) context.get("locale")) + "-" + name);
                    result.put("level", level);
                    result.put("type", "category");
                    result.put("parent", parent);
                    result.put("seq", seq);

                } else if (type.equals("product")) {
                    id = id.substring(0, id.indexOf("_product"));
                    String prefix = UtilProperties.getMessage(resource, "ProductProducts", (Locale) context.get("locale")) + "-";
                    if (name.startsWith(prefix)) {
                        name = name.substring(prefix.length());
                    }
                    GenericValue product = delegator.findByPrimaryKey("Product", UtilMisc.toMap("productId", id));
                    product.put("productName", name);
                    delegator.store(product);
                    result.put("id", id + "_product");
                    result.put("name", UtilProperties.getMessage(resource, "ProductProducts", (Locale) context.get("locale")) + "-" + name);
                    result.put("level", level);
                    result.put("type", "product");
                    result.put("parent", parent);
                    result.put("seq", seq);
                }
            }


        } catch (GenericEntityException e) {
            e.printStackTrace();
            return ServiceUtil.returnError(e.getMessage());
        }

        return result;
    }

    public static Map<String, Object> productReviews(DispatchContext dcx, Map<String, ? extends Object> context) {
        String productId = (String) context.get("productId");
        String productStoreId = (String) context.get("productStroeId");
        Delegator delegator = dcx.getDelegator();
        GenericValue product = null;
        Map<String,Object> result = ServiceUtil.returnSuccess();
        try {
            product = delegator.findByPrimaryKey("Product", UtilMisc.toMap("productId", productId));

        List<GenericValue> reviews = product.getRelatedCache("ProductReview", UtilMisc.toMap("statusId", "PRR_APPROVED", "productStroeId", productStoreId), UtilMisc.toList("-postedDateTime"));
        result.put("reviews",reviews);
        // get the average rating
        if (UtilValidate.isNotEmpty(reviews)) {
            List<GenericValue> ratingReviews = EntityUtil.filterByAnd(reviews, UtilMisc.toList(EntityCondition.makeCondition("productRating", EntityOperator.NOT_EQUAL, null)));
            if (UtilValidate.isNotEmpty(ratingReviews)) {
                BigDecimal averageRating = ProductWorker.getAverageProductRating(product, reviews, productStoreId);
                result.put("averageRating",averageRating);
                Integer numRatings = ratingReviews.size();
                result.put("numRatings",numRatings);
            }
        }
        } catch (GenericEntityException e) {
            e.printStackTrace();
            return ServiceUtil.returnError(e.getMessage());
        }
        return result;
    }
}