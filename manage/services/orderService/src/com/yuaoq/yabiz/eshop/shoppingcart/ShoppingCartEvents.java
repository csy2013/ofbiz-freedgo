package com.yuaoq.yabiz.eshop.shoppingcart;

import javolution.util.FastList;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import org.ofbiz.base.util.*;
import org.ofbiz.entity.Delegator;
import org.ofbiz.entity.GenericEntityException;
import org.ofbiz.entity.GenericValue;
import org.ofbiz.entity.condition.EntityCondition;
import org.ofbiz.entity.condition.EntityOperator;
import org.ofbiz.entity.util.EntityUtil;
import org.ofbiz.order.shoppingcart.CartItemModifyException;
import org.ofbiz.order.shoppingcart.ShoppingCartHelper;
import org.ofbiz.order.shoppingcart.ShoppingCartItem;
import org.ofbiz.product.catalog.CatalogWorker;
import org.ofbiz.product.config.ProductConfigWorker;
import org.ofbiz.product.config.ProductConfigWrapper;
import org.ofbiz.product.product.ProductWorker;
import org.ofbiz.product.store.ProductStoreSurveyWrapper;
import org.ofbiz.product.store.ProductStoreWorker;
import org.ofbiz.security.Security;
import org.ofbiz.service.LocalDispatcher;
import org.ofbiz.webapp.control.RequestHandler;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.*;

/**
 * Created by tusm on 15/5/30.
 */
public class ShoppingCartEvents extends org.ofbiz.order.shoppingcart.ShoppingCartEvents {

    public static String module = ShoppingCartEvents.class.getName();
    public static final String resource = "OrderUiLabels";
    public static final String resource_error = "OrderErrorUiLabels";

    private static final String NO_ERROR = "noerror";
    private static final String NON_CRITICAL_ERROR = "noncritical";
    private static final String ERROR = "error";


    /**
     * 检查优惠券是否可用  Add By Changsy
     * @param request
     * @param productPromoCodeId
     * @return
     */
    public static String checkProductPromoCode(HttpServletRequest request, String productPromoCodeId) {
        LocalDispatcher dispatcher = (LocalDispatcher) request.getAttribute("dispatcher");
        ShoppingCart cart = (ShoppingCart) getCartObject(request);
//        String productPromoCodeId = request.getParameter("productPromoCodeId");
        if (UtilValidate.isNotEmpty(productPromoCodeId)) {
            String checkResult = cart.checkProductPromoCode(productPromoCodeId, dispatcher);
            if("success".equals(checkResult)){
                return "Y";
            }
        }
        return "N";
    }

    /**
     * ajax加入购物车 Add By Changsy
     *
     * @param request
     * @param response
     * @return
     */

    public static String addToCartajax(HttpServletRequest request, HttpServletResponse response) {
        Delegator delegator = (Delegator) request.getAttribute("delegator");
        LocalDispatcher dispatcher = (LocalDispatcher) request.getAttribute("dispatcher");
        ShoppingCart cart = (ShoppingCart) getCartObject(request);
        ShoppingCartHelper cartHelper = new ShoppingCartHelper(delegator, dispatcher, cart);
        String controlDirective = null;
        Map<String, Object> result = null;
        String productId = null;
        String parentProductId = null;
        String itemType = null;
        String itemDescription = null;
        String productCategoryId = null;
        String priceStr = null;
        BigDecimal price = null;
        String quantityStr = null;
        BigDecimal quantity = BigDecimal.ZERO;
        String reservStartStr = null;
        String reservEndStr = null;
        Timestamp reservStart = null;
        Timestamp reservEnd = null;
        String reservLengthStr = null;
        BigDecimal reservLength = null;
        String reservPersonsStr = null;
        BigDecimal reservPersons = null;
        String accommodationMapId = null;
        String accommodationSpotId = null;
        String shipBeforeDateStr = null;
        String shipAfterDateStr = null;
        Timestamp shipBeforeDate = null;
        Timestamp shipAfterDate = null;
        String numberOfDay = null;

        // not used right now: Map attributes = null;
        String catalogId = CatalogWorker.getCurrentCatalogId(request);
        Locale locale = UtilHttp.getLocale(request);

        // Get the parameters as a MAP, remove the productId and quantity params.
        Map<String, Object> paramMap = UtilHttp.getCombinedMap(request);

        String itemGroupNumber = (String) paramMap.get("itemGroupNumber");

        // Get shoppingList info if passed
        String shoppingListId = (String) paramMap.get("shoppingListId");
        String shoppingListItemSeqId = (String) paramMap.get("shoppingListItemSeqId");
        if (paramMap.containsKey("ADD_PRODUCT_ID")) {
            productId = (String) paramMap.remove("ADD_PRODUCT_ID");
        } else if (paramMap.containsKey("add_product_id")) {
            Object object = paramMap.remove("add_product_id");
            try {
                productId = (String) object;
            } catch (ClassCastException e) {
                List<String> productList = UtilGenerics.checkList(object);
                productId = productList.get(0);
            }
        }
        if (paramMap.containsKey("PRODUCT_ID")) {
            parentProductId = (String) paramMap.remove("PRODUCT_ID");
        } else if (paramMap.containsKey("product_id")) {
            parentProductId = (String) paramMap.remove("product_id");
        }

        Debug.logInfo("adding item product " + productId, module);
        Debug.logInfo("adding item parent product " + parentProductId, module);

        if (paramMap.containsKey("ADD_CATEGORY_ID")) {
            productCategoryId = (String) paramMap.remove("ADD_CATEGORY_ID");
        } else if (paramMap.containsKey("add_category_id")) {
            productCategoryId = (String) paramMap.remove("add_category_id");
        }
        if ((productCategoryId != null) && (productCategoryId.length() == 0)) {
            productCategoryId = null;
        }

        if (paramMap.containsKey("ADD_ITEM_TYPE")) {
            itemType = (String) paramMap.remove("ADD_ITEM_TYPE");
        } else if (paramMap.containsKey("add_item_type")) {
            itemType = (String) paramMap.remove("add_item_type");
        }

        if (UtilValidate.isEmpty(productId)) {
            // before returning error; check make sure we aren't adding a special item type
            if (UtilValidate.isEmpty(itemType)) {
                request.setAttribute("_ERROR_MESSAGE_",
                        UtilProperties.getMessage(resource_error, "cart.addToCart.noProductInfoPassed", locale));
                return "success"; // not critical return to same page
            }
        } else {
            try {
                String pId = ProductWorker.findProductId(delegator, productId);
                if (pId != null) {
                    productId = pId;
                }
            } catch (Throwable e) {
                Debug.logWarning(e, module);
            }
        }

        // check for an itemDescription
        if (paramMap.containsKey("ADD_ITEM_DESCRIPTION")) {
            itemDescription = (String) paramMap.remove("ADD_ITEM_DESCRIPTION");
        } else if (paramMap.containsKey("add_item_description")) {
            itemDescription = (String) paramMap.remove("add_item_description");
        }
        if ((itemDescription != null) && (itemDescription.length() == 0)) {
            itemDescription = null;
        }

        // Get the ProductConfigWrapper (it's not null only for configurable items)
        ProductConfigWrapper configWrapper = null;
        configWrapper = ProductConfigWorker.getProductConfigWrapper(productId, cart.getCurrency(), request);

        if (configWrapper != null) {
            if (paramMap.containsKey("configId")) {
                try {
                    configWrapper.loadConfig(delegator, (String) paramMap.remove("configId"));
                } catch (Exception e) {
                    Debug.logWarning(e, "Could not load configuration", module);
                }
            } else {
                // The choices selected by the user are taken from request and set in the wrapper
                ProductConfigWorker.fillProductConfigWrapper(configWrapper, request);
            }
            if (!configWrapper.isCompleted()) {
                // The configuration is not valid
                request.setAttribute("product_id", productId);
                request.setAttribute("_EVENT_MESSAGE_",
                        UtilProperties.getMessage(resource_error, "cart.addToCart.configureProductBeforeAddingToCart", locale));
                return "product";
            } else {
                // load the Config Id
                ProductConfigWorker.storeProductConfigWrapper(configWrapper, delegator);
            }
        }

        // Check for virtual products
        if (ProductWorker.isVirtual(delegator, productId)) {

            if ("VV_FEATURETREE".equals(ProductWorker.getProductVirtualVariantMethod(delegator, productId))) {
                // get the selected features.
                List<String> selectedFeatures = new LinkedList<String>();
                Enumeration<String> paramNames = UtilGenerics.cast(request.getParameterNames());
                while (paramNames.hasMoreElements()) {
                    String paramName = paramNames.nextElement();
                    if (paramName.startsWith("FT")) {
                        selectedFeatures.add(request.getParameterValues(paramName)[0]);
                    }
                }

                // check if features are selected
                if (UtilValidate.isEmpty(selectedFeatures)) {
                    request.setAttribute("paramMap", paramMap);
                    request.setAttribute("product_id", productId);
                    request.setAttribute("_EVENT_MESSAGE_",
                            UtilProperties.getMessage(resource_error, "cart.addToCart.chooseVariationBeforeAddingToCart", locale));
                    return "product";
                }

                String variantProductId = ProductWorker.getVariantFromFeatureTree(productId, selectedFeatures, delegator);
                if (UtilValidate.isNotEmpty(variantProductId)) {
                    productId = variantProductId;
                } else {
                    request.setAttribute("paramMap", paramMap);
                    request.setAttribute("product_id", productId);
                    request.setAttribute("_EVENT_MESSAGE_",
                            UtilProperties.getMessage(resource_error, "cart.addToCart.incompatibilityVariantFeature", locale));
                    return "product";
                }

            } else {
                request.setAttribute("paramMap", paramMap);
                request.setAttribute("product_id", productId);
                request.setAttribute("_EVENT_MESSAGE_",
                        UtilProperties.getMessage(resource_error, "cart.addToCart.chooseVariationBeforeAddingToCart", locale));
                return "product";
            }
        }

        // get the override price
        if (paramMap.containsKey("PRICE")) {
            priceStr = (String) paramMap.remove("PRICE");
        } else if (paramMap.containsKey("price")) {
            priceStr = (String) paramMap.remove("price");
        }
        if (priceStr == null) {
            priceStr = "0"; // default price is 0
        }

        if ("ASSET_USAGE_OUT_IN".equals(ProductWorker.getProductTypeId(delegator, productId))) {
            if (paramMap.containsKey("numberOfDay")) {
                numberOfDay = (String) paramMap.remove("numberOfDay");
                reservStart = UtilDateTime.addDaysToTimestamp(UtilDateTime.nowTimestamp(), 1);
                reservEnd = UtilDateTime.addDaysToTimestamp(reservStart, Integer.valueOf(numberOfDay));
            }
        }

        // get the renting data
        if ("ASSET_USAGE".equals(ProductWorker.getProductTypeId(delegator, productId))
                || "ASSET_USAGE_OUT_IN".equals(ProductWorker.getProductTypeId(delegator, productId))) {
            if (paramMap.containsKey("reservStart")) {
                reservStartStr = (String) paramMap.remove("reservStart");
                if (reservStartStr.length() == 10) {
                    reservStartStr += " 00:00:00.000000000"; // should have format: yyyy-mm-dd hh:mm:ss.fffffffff
                }
                if (reservStartStr.length() > 0) {
                    try {
                        reservStart = Timestamp.valueOf(reservStartStr);
                    } catch (Exception e) {
                        Debug.logWarning(e, "Problems parsing Reservation start string: "
                                + reservStartStr, module);
                        reservStart = null;
                        request.setAttribute("_ERROR_MESSAGE_",
                                UtilProperties.getMessage(resource_error, "cart.addToCart.rental.startDate", locale));
                        return "error";
                    }
                } else {
                    reservStart = null;
                }
            }

            if (paramMap.containsKey("reservEnd")) {
                reservEndStr = (String) paramMap.remove("reservEnd");
                if (reservEndStr.length() == 10) {
                    reservEndStr += " 00:00:00.000000000"; // should have format: yyyy-mm-dd hh:mm:ss.fffffffff
                }
                if (reservEndStr.length() > 0) {
                    try {
                        reservEnd = Timestamp.valueOf(reservEndStr);
                    } catch (Exception e) {
                        Debug.logWarning(e, "Problems parsing Reservation end string: " + reservEndStr, module);
                        reservEnd = null;
                        request.setAttribute("_ERROR_MESSAGE_",
                                UtilProperties.getMessage(resource_error, "cart.addToCart.rental.endDate", locale));
                        return "error";
                    }
                } else {
                    reservEnd = null;
                }
            }

            if ((reservStart != null) && (reservEnd != null)) {
                reservLength = new BigDecimal(UtilDateTime.getInterval(reservStart, reservEnd)).divide(new BigDecimal(
                        "86400000"), generalRounding);
            }

            if ((reservStart != null) && paramMap.containsKey("reservLength")) {
                reservLengthStr = (String) paramMap.remove("reservLength");
                // parse the reservation Length
                try {
                    reservLength = (BigDecimal) ObjectType.simpleTypeConvert(reservLengthStr, "BigDecimal", null, locale);
                } catch (Exception e) {
                    Debug.logWarning(e, "Problems parsing reservation length string: "
                            + reservLengthStr, module);
                    reservLength = BigDecimal.ONE;
                    request.setAttribute("_ERROR_MESSAGE_",
                            UtilProperties.getMessage(resource_error, "OrderReservationLengthShouldBeAPositiveNumber", locale));
                    return "error";
                }
            }

            if ((reservStart != null) && paramMap.containsKey("reservPersons")) {
                reservPersonsStr = (String) paramMap.remove("reservPersons");
                // parse the number of persons
                try {
                    reservPersons = (BigDecimal) ObjectType.simpleTypeConvert(reservPersonsStr, "BigDecimal", null, locale);
                } catch (Exception e) {
                    Debug.logWarning(e, "Problems parsing reservation number of persons string: " + reservPersonsStr, module);
                    reservPersons = BigDecimal.ONE;
                    request.setAttribute("_ERROR_MESSAGE_",
                            UtilProperties.getMessage(resource_error, "OrderNumberOfPersonsShouldBeOneOrLarger", locale));
                    return "error";
                }
            }

            // check for valid rental parameters
            if (UtilValidate.isEmpty(reservStart) && UtilValidate.isEmpty(reservLength)
                    && UtilValidate.isEmpty(reservPersons)) {
                request.setAttribute("product_id", productId);
                request.setAttribute("_EVENT_MESSAGE_", UtilProperties.getMessage(resource_error,
                        "cart.addToCart.enterBookingInforamtionBeforeAddingToCart", locale));
                return "product";
            }

            // check accommodation for reservations
            if ((paramMap.containsKey("accommodationMapId")) && (paramMap.containsKey("accommodationSpotId"))) {
                accommodationMapId = (String) paramMap.remove("accommodationMapId");
                accommodationSpotId = (String) paramMap.remove("accommodationSpotId");
            }
        }

        // get the quantity
        if (paramMap.containsKey("QUANTITY")) {
            quantityStr = (String) paramMap.remove("QUANTITY");
        } else if (paramMap.containsKey("quantity")) {
            quantityStr = (String) paramMap.remove("quantity");
        }
        if (UtilValidate.isEmpty(quantityStr)) {
            quantityStr = "1"; // default quantity is 1
        }

        // parse the price
        try {
            price = (BigDecimal) ObjectType.simpleTypeConvert(priceStr, "BigDecimal", null, locale);
        } catch (Exception e) {
            Debug.logWarning(e, "Problems parsing price string: " + priceStr, module);
            price = null;
        }

        // parse the quantity
        try {
            quantity = (BigDecimal) ObjectType.simpleTypeConvert(quantityStr, "BigDecimal", null, locale);
            // For quantity we should test if we allow to add decimal quantity for this product an productStore : if not then
            // round to 0
            if (!ProductWorker.isDecimalQuantityOrderAllowed(delegator, productId, cart.getProductStoreId())) {
                quantity = quantity.setScale(0, UtilNumber.getBigDecimalRoundingMode("order.rounding"));
            } else {
                quantity = quantity.setScale(UtilNumber.getBigDecimalScale("order.decimals"),
                        UtilNumber.getBigDecimalRoundingMode("order.rounding"));
            }
        } catch (Exception e) {
            Debug.logWarning(e, "Problems parsing quantity string: " + quantityStr, module);
            quantity = BigDecimal.ONE;
        }

        // get the selected amount
        String selectedAmountStr = null;
        if (paramMap.containsKey("ADD_AMOUNT")) {
            selectedAmountStr = (String) paramMap.remove("ADD_AMOUNT");
        } else if (paramMap.containsKey("add_amount")) {
            selectedAmountStr = (String) paramMap.remove("add_amount");
        }

        // parse the amount
        BigDecimal amount = null;
        if (UtilValidate.isNotEmpty(selectedAmountStr)) {
            try {
                amount = (BigDecimal) ObjectType.simpleTypeConvert(selectedAmountStr, "BigDecimal", null, locale);
            } catch (Exception e) {
                Debug.logWarning(e, "Problem parsing amount string: " + selectedAmountStr, module);
                amount = null;
            }
        } else {
            amount = BigDecimal.ZERO;
        }

        // check for required amount
        if ((ProductWorker.isAmountRequired(delegator, productId)) && ((amount == null) || (amount.doubleValue() == 0.0))) {
            request.setAttribute("product_id", productId);
            request.setAttribute("_EVENT_MESSAGE_",
                    UtilProperties.getMessage(resource_error, "cart.addToCart.enterAmountBeforeAddingToCart", locale));
            return "product";
        }

        // get the ship before date (handles both yyyy-mm-dd input and full timestamp)
        shipBeforeDateStr = (String) paramMap.remove("shipBeforeDate");
        if (UtilValidate.isNotEmpty(shipBeforeDateStr)) {
            if (shipBeforeDateStr.length() == 10) {
                shipBeforeDateStr += " 00:00:00.000";
            }
            try {
                shipBeforeDate = Timestamp.valueOf(shipBeforeDateStr);
            } catch (IllegalArgumentException e) {
                Debug.logWarning(e, "Bad shipBeforeDate input: " + e.getMessage(), module);
                shipBeforeDate = null;
            }
        }

        // get the ship after date (handles both yyyy-mm-dd input and full timestamp)
        shipAfterDateStr = (String) paramMap.remove("shipAfterDate");
        if (UtilValidate.isNotEmpty(shipAfterDateStr)) {
            if (shipAfterDateStr.length() == 10) {
                shipAfterDateStr += " 00:00:00.000";
            }
            try {
                shipAfterDate = Timestamp.valueOf(shipAfterDateStr);
            } catch (IllegalArgumentException e) {
                Debug.logWarning(e, "Bad shipAfterDate input: " + e.getMessage(), module);
                shipAfterDate = null;
            }
        }

        // check for an add-to cart survey
        List<String> surveyResponses = null;
        if (productId != null) {
            String productStoreId = ProductStoreWorker.getProductStoreId(request);
            List<GenericValue> productSurvey = ProductStoreWorker.getProductSurveys(delegator, productStoreId, productId,
                    "CART_ADD", parentProductId);
            if (UtilValidate.isNotEmpty(productSurvey)) {
                // TODO: implement multiple survey per product
                GenericValue survey = EntityUtil.getFirst(productSurvey);
                String surveyResponseId = (String) request.getAttribute("surveyResponseId");
                if (surveyResponseId != null) {
                    surveyResponses = UtilMisc.toList(surveyResponseId);
                } else {
                    String origParamMapId = UtilHttp.stashParameterMap(request);
                    Map<String, Object> surveyContext = UtilMisc.<String, Object>toMap("_ORIG_PARAM_MAP_ID_", origParamMapId);
                    GenericValue userLogin = cart.getUserLogin();
                    String partyId = null;
                    if (userLogin != null) {
                        partyId = userLogin.getString("partyId");
                    }
                    String formAction = "/additemsurvey";
                    String nextPage = RequestHandler.getOverrideViewUri(request.getPathInfo());
                    if (nextPage != null) {
                        formAction = formAction + "/" + nextPage;
                    }
                    ProductStoreSurveyWrapper wrapper = new ProductStoreSurveyWrapper(survey, partyId, surveyContext);
                    request.setAttribute("surveyWrapper", wrapper);
                    request.setAttribute("surveyAction", formAction); // will be used as the form action of the survey
                    return "survey";
                }
            }
        }
        if (surveyResponses != null) {
            paramMap.put("surveyResponses", surveyResponses);
        }

        GenericValue productStore = ProductStoreWorker.getProductStore(request);
        if (productStore != null) {
            String addToCartRemoveIncompat = productStore.getString("addToCartRemoveIncompat");
            String addToCartReplaceUpsell = productStore.getString("addToCartReplaceUpsell");
            try {
                if ("Y".equals(addToCartRemoveIncompat)) {
                    List<GenericValue> productAssocs = null;
                    EntityCondition cond = EntityCondition.makeCondition(UtilMisc.toList(
                                    EntityCondition.makeCondition(
                                            EntityCondition.makeCondition("productId", EntityOperator.EQUALS, productId), EntityOperator.OR,
                                            EntityCondition.makeCondition("productIdTo", EntityOperator.EQUALS, productId)),
                                    EntityCondition.makeCondition("productAssocTypeId", EntityOperator.EQUALS, "PRODUCT_INCOMPATABLE")),
                            EntityOperator.AND);
                    productAssocs = delegator.findList("ProductAssoc", cond, null, null, null, false);
                    productAssocs = EntityUtil.filterByDate(productAssocs);
                    List<String> productList = FastList.newInstance();
                    for (GenericValue productAssoc : productAssocs) {
                        if (productId.equals(productAssoc.getString("productId"))) {
                            productList.add(productAssoc.getString("productIdTo"));
                            continue;
                        }
                        if (productId.equals(productAssoc.getString("productIdTo"))) {
                            productList.add(productAssoc.getString("productId"));
                            continue;
                        }
                    }
                    for (ShoppingCartItem sci : cart) {
                        if (productList.contains(sci.getProductId())) {
                            try {
                                cart.removeCartItem(sci, dispatcher);
                            } catch (CartItemModifyException e) {
                                Debug.logError(e.getMessage(), module);
                            }
                        }
                    }
                }
                if ("Y".equals(addToCartReplaceUpsell)) {
                    List<GenericValue> productList = null;
                    EntityCondition cond = EntityCondition.makeCondition(UtilMisc.toList(
                                    EntityCondition.makeCondition("productIdTo", EntityOperator.EQUALS, productId),
                                    EntityCondition.makeCondition("productAssocTypeId", EntityOperator.EQUALS, "PRODUCT_UPGRADE")),
                            EntityOperator.AND);
                    productList = delegator.findList("ProductAssoc", cond, UtilMisc.toSet("productId"), null, null, false);
                    if (productList != null) {
                        for (ShoppingCartItem sci : cart) {
                            if (productList.contains(sci.getProductId())) {
                                try {
                                    cart.removeCartItem(sci, dispatcher);
                                } catch (CartItemModifyException e) {
                                    Debug.logError(e.getMessage(), module);
                                }
                            }
                        }
                    }
                }
            } catch (GenericEntityException e) {
                Debug.logError(e.getMessage(), module);
            }
        }

        // check for alternative packing
        if (ProductWorker.isAlternativePacking(delegator, productId, parentProductId)) {
            GenericValue parentProduct = null;
            try {
                parentProduct = delegator.findByPrimaryKey("Product", UtilMisc.toMap("productId", parentProductId));
            } catch (GenericEntityException e) {
                Debug.logError(e, "Error getting parent product", module);
            }
            BigDecimal piecesIncluded = BigDecimal.ZERO;
            if (parentProduct != null) {
                piecesIncluded = new BigDecimal(parentProduct.getLong("piecesIncluded"));
                quantity = quantity.multiply(piecesIncluded);
            }
        }

        // Translate the parameters and add to the cart
        result = cartHelper.addToCart(catalogId, shoppingListId, shoppingListItemSeqId, productId, productCategoryId,
                itemType, itemDescription, price, amount, quantity, reservStart, reservLength, reservPersons,
                accommodationMapId, accommodationSpotId,
                shipBeforeDate, shipAfterDate, configWrapper, itemGroupNumber, paramMap, parentProductId);
        controlDirective = processResult(result, request);

        Integer itemId = (Integer) result.get("itemId");
        if (UtilValidate.isNotEmpty(itemId)) {
            request.setAttribute("itemId", itemId);
        }

        // Determine where to send the browser
        if (controlDirective.equals(ERROR)) {
            return "error";
        } else {
            if (cart.viewCartOnAdd()) {
                return "viewcart";
            } else {
                java.text.DecimalFormat format = new java.text.DecimalFormat("###,###,###,###,###.00");

                if (cart.getDisplayGrandTotal() != null) {
                    if(cart.getDisplayGrandTotal().compareTo(BigDecimal.ONE)<=0){
                        request.setAttribute("totalPrice", "0"+format.format(cart.getDisplayGrandTotal()));
                    }else{
                        request.setAttribute("totalPrice", format.format(cart.getDisplayGrandTotal()));
                    }
                } else {
                    request.setAttribute("totalPrice", 0);
                }
                if (cart.getTotalQuantity() != null) {
                    request.setAttribute("totalQuantity", cart.getTotalQuantity());
                } else {
                    request.setAttribute("totalQuantity", 0);
                }
                String categoryIds = "";
                try {
                    for (int i = 0; i < cart.getCartItemsInNoGroup().size(); i++) {
                        List<GenericValue> productCategoryMembers = null;
                        if(UtilValidate.isNotEmpty(cart.getCartItemsInNoGroup().get(i).getParentProductId())){
                            productCategoryMembers = delegator.findByAnd("ProductCategoryMember",
                                    UtilMisc.toMap("productId", cart.getCartItemsInNoGroup().get(i).getParentProductId()));
                        }else{
                            productCategoryMembers = delegator.findByAnd("ProductCategoryMember",
                                    UtilMisc.toMap("productId", cart.getCartItemsInNoGroup().get(i).getProductId()));
                        }
                        if (productCategoryMembers.size() > 0) {
                            for (int j = 0; j < productCategoryMembers.size(); j++) {
                                if ((i == 0) && (j == 0)) {
                                    categoryIds = categoryIds + productCategoryMembers.get(j).get("productCategoryId");
                                } else {
                                    categoryIds = categoryIds + "," + productCategoryMembers.get(j).get("productCategoryId");
                                }
                            }
                        }
                    }

                } catch (GenericEntityException e) {
                    e.printStackTrace();
                }
                request.setAttribute("categoryIds", categoryIds);
                JSONArray jsonArray = new JSONArray();
                JSONObject jsonObject = new JSONObject();
                for (int i=0;i<cart.items().size();i++){
                    jsonObject.put("cartLineIndex",cart.getItemIndex(cart.items().get(i)));
                    String parentProductId1=null;
                    if(cart.items().get(i).getParentProductId()!=null){
                        parentProductId1=cart.items().get(i).getParentProductId();
                    }else{
                        parentProductId1=cart.items().get(i).getProductId();
                    }
                    String productLink="/eshop/products/"+parentProductId1;
                    jsonObject.put("productLink",productLink);
                    String smallImageUrl=null;
                    smallImageUrl=org.ofbiz.product.product.ProductContentWrapper.getProductContentAsText(cart.items().get(i).getProduct(), "SMALL_IMAGE_URL", locale, dispatcher);
                    if(smallImageUrl==null){
                        smallImageUrl = "/images/defaultImage.jpg";
                    }
                    jsonObject.put("smallImageUrl",smallImageUrl);
                    jsonObject.put("productName",cart.items().get(i).getName());
                    jsonObject.put("quantity",cart.items().get(i).getQuantity());
                    if(cart.items().get(i).getDisplayItemSubTotal().compareTo(BigDecimal.ONE)<=0){
                        jsonObject.put("amount","￥0"+format.format(cart.items().get(i).getDisplayItemSubTotal()));
                    }else{
                        jsonObject.put("amount", "￥" + format.format(cart.items().get(i).getDisplayItemSubTotal()));
                    }
                    GenericValue product = null;
                    try {
                        product = delegator.findByPrimaryKey("Product", UtilMisc.toMap("productId", parentProductId1));
                    } catch (GenericEntityException e) {
                        e.printStackTrace();
                    }
                    String categoryIds1 = org.ofbiz.product.product.ProductContentWrapper.getProductCategory(product, locale, dispatcher);
                    jsonObject.put("categoryIds",categoryIds1);
                    jsonArray.add(jsonObject);
                }
                request.setAttribute("jsonArray", jsonArray);
                return "success";
            }
        }
    }

    /**
     * Created by Alex on 12-02-2014.
     *
     * @param request
     * @param response
     * @return
     */
    public static String ajaxmodifycart(HttpServletRequest request, HttpServletResponse response) {
        HttpSession session = request.getSession();
        ShoppingCart cart = (ShoppingCart) getCartObject(request);
        Locale locale = UtilHttp.getLocale(request);
        GenericValue userLogin = (GenericValue) session.getAttribute("userLogin");
        LocalDispatcher dispatcher = (LocalDispatcher) request.getAttribute("dispatcher");
        Security security = (Security) request.getAttribute("security");
        ShoppingCartHelper cartHelper = new ShoppingCartHelper(null, dispatcher, cart);
        String controlDirective;
        Map<String, Object> result;
        // not used yet: Locale locale = UtilHttp.getLocale(request);

        Map<String, Object> paramMap = UtilHttp.getParameterMap(request);
        Delegator delegator = (Delegator) request.getAttribute("delegator");
        String removeSelectedFlag = request.getParameter("removeSelected");
        String selectedItems[] = request.getParameterValues("selectedItem");
        boolean removeSelected = ("true".equals(removeSelectedFlag) && (selectedItems != null) && (selectedItems.length > 0));
        result = cartHelper.modifyCart(security, userLogin, paramMap, removeSelected, selectedItems, locale);
        controlDirective = processResult(result, request);

        // Determine where to send the browser
        if (controlDirective.equals(ERROR)) {
            return "error";
        } else {
            java.text.DecimalFormat format = new java.text.DecimalFormat("###,###,###,###,###.00");
            if (request.getParameter("changeSelected") != null) {
                request.setAttribute("index", Integer.parseInt(request.getParameter("changeSelected")));
                if (cart.getCartItemsInNoGroup().size() >= Integer.parseInt(request.getParameter("changeSelected"))) {
                    if (cart.getCartItemsInNoGroup().get(Integer.parseInt(request.getParameter("changeSelected")))
                            .getDisplayItemSubTotal() != null) {
                        request.setAttribute(
                                "newPrice",
                                format.format(cart.getCartItemsInNoGroup()
                                        .get(Integer.parseInt(request.getParameter("changeSelected"))).getDisplayItemSubTotal()));
                    } else {
                        request.setAttribute("newPrice", 0);
                    }
                } else {
                    request.setAttribute("newPrice", 0);
                }
            }
            if (cart.getDisplayGrandTotal() != null) {
                request.setAttribute("totalPrice", format.format(cart.getDisplayGrandTotal()));
            } else {
                request.setAttribute("totalPrice", 0);
            }
            if (cart.getTotalQuantity() != null) {
                request.setAttribute("totalQuantity", cart.getTotalQuantity());
            } else {
                request.setAttribute("totalQuantity", 0);
            }
            JSONArray jsonArray = new JSONArray();
            JSONObject jsonObject = new JSONObject();
            for (int i=0;i<cart.items().size();i++){
                jsonObject.put("cartLineIndex",cart.getItemIndex(cart.items().get(i)));
                String parentProductId=null;
                if(cart.items().get(i).getParentProductId()!=null){
                    parentProductId=cart.items().get(i).getParentProductId();
                }else{
                    parentProductId=cart.items().get(i).getProductId();
                }
                String productLink="/eshop/products/"+parentProductId;
                jsonObject.put("productLink",productLink);
                String smallImageUrl=null;
                smallImageUrl=org.ofbiz.product.product.ProductContentWrapper.getProductContentAsText(cart.items().get(i).getProduct(), "SMALL_IMAGE_URL", locale, dispatcher);
                if(smallImageUrl==null){
                    smallImageUrl = "/images/defaultImage.jpg";
                }
                jsonObject.put("smallImageUrl",smallImageUrl);
                jsonObject.put("productName",cart.items().get(i).getName());
                jsonObject.put("quantity",cart.items().get(i).getQuantity());
                jsonObject.put("amount","￥"+format.format(cart.items().get(i).getDisplayItemSubTotal()));
                GenericValue product = null;
                try {
                    product = delegator.findByPrimaryKey("Product", UtilMisc.toMap("productId", parentProductId));
                } catch (GenericEntityException e) {
                    e.printStackTrace();
                }
                String categoryIds = org.ofbiz.product.product.ProductContentWrapper.getProductCategory(product, locale, dispatcher);
                jsonObject.put("categoryIds",categoryIds);
                jsonArray.add(jsonObject);
            }
            request.setAttribute("jsonArray", jsonArray);
            return "success";
        }
    }
}
