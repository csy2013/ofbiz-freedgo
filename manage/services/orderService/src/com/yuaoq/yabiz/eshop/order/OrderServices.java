package com.yuaoq.yabiz.eshop.order;

import javolution.util.FastList;
import javolution.util.FastMap;
import org.ofbiz.base.util.*;
import org.ofbiz.common.DataModelConstants;
import org.ofbiz.entity.Delegator;
import org.ofbiz.entity.GenericEntityException;
import org.ofbiz.entity.GenericValue;
import org.ofbiz.entity.condition.EntityCondition;
import org.ofbiz.entity.condition.EntityOperator;
import org.ofbiz.entity.util.EntityUtil;
import org.ofbiz.security.Security;
import org.ofbiz.service.*;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.*;

/**
 * Created by tusm on 15/6/7.
 */
public class OrderServices extends org.ofbiz.order.order.OrderServices {
    /**
     * Service for creating a new order
     */
    public static Map<String, Object> createOrder(DispatchContext ctx, Map<String, ? extends Object> context) {
        Delegator delegator = ctx.getDelegator();
        LocalDispatcher dispatcher = ctx.getDispatcher();
        Security security = ctx.getSecurity();
        List<GenericValue> toBeStored = new LinkedList<GenericValue>();
        Locale locale = (Locale) context.get("locale");
        Map<String, Object> successResult = ServiceUtil.returnSuccess();

        GenericValue userLogin = (GenericValue) context.get("userLogin");
        // get the order type
        String orderTypeId = (String) context.get("orderTypeId");
        String partyId = (String) context.get("partyId");
        String billFromVendorPartyId = (String) context.get("billFromVendorPartyId");

        // check security permissions for order:
        // SALES ORDERS - if userLogin has ORDERMGR_SALES_CREATE or ORDERMGR_CREATE permission, or if it is same party as
        // the partyId, or
        // if it is an AGENT (sales rep) creating an order for his customer
        // PURCHASE ORDERS - if there is a PURCHASE_ORDER permission
        Map<String, Object> resultSecurity = new HashMap<String, Object>();
        boolean hasPermission = OrderServices.hasPermission(orderTypeId, partyId, userLogin, "CREATE", security);
        // final check - will pass if userLogin's partyId = partyId for order or if userLogin has ORDERMGR_CREATE permission
        // jacopoc: what is the meaning of this code block? FIXME
        if (!hasPermission) {
            partyId = ServiceUtil
                    .getPartyIdCheckSecurity(userLogin, security, context, resultSecurity, "ORDERMGR", "_CREATE");
            if (resultSecurity.size() > 0) {
                return resultSecurity;
            }
        }

        // get the product store for the order, but it is required only for sales orders
        String productStoreId = (String) context.get("productStoreId");
        GenericValue productStore = null;
        if ((orderTypeId.equals("SALES_ORDER")) && (UtilValidate.isNotEmpty(productStoreId))) {
            try {
                productStore = delegator
                        .findByPrimaryKeyCache("ProductStore", UtilMisc.toMap("productStoreId", productStoreId));
            } catch (GenericEntityException e) {
                return ServiceUtil.returnError(UtilProperties.getMessage(resource_error,
                        "OrderErrorCouldNotFindProductStoreWithID", UtilMisc.toMap("productStoreId", productStoreId), locale)
                        + e.toString());
            }
        }

        // figure out if the order is immediately fulfilled based on product store settings
        boolean isImmediatelyFulfilled = false;
        if (productStore != null) {
            isImmediatelyFulfilled = "Y".equals(productStore.getString("isImmediatelyFulfilled"));
        }

        successResult.put("orderTypeId", orderTypeId);

        // lookup the order type entity
        GenericValue orderType = null;
        try {
            orderType = delegator.findByPrimaryKeyCache("OrderType", UtilMisc.toMap("orderTypeId", orderTypeId));
        } catch (GenericEntityException e) {
            return ServiceUtil.returnError(UtilProperties.getMessage(resource_error,
                    "OrderErrorOrderTypeLookupFailed", locale) + e.toString());
        }

        // make sure we have a valid order type
        if (orderType == null) {
            return ServiceUtil.returnError(UtilProperties.getMessage(resource_error,
                    "OrderErrorInvalidOrderTypeWithID", UtilMisc.toMap("orderTypeId", orderTypeId), locale));
        }

        // check to make sure we have something to order
        List<GenericValue> orderItems = UtilGenerics.checkList(context.get("orderItems"));
        if (orderItems.size() < 1) {
            return ServiceUtil.returnError(UtilProperties.getMessage(resource_error, "items.none", locale));
        }

        // all this marketing pkg auto stuff is deprecated in favor of MARKETING_PKG_AUTO productTypeId and a BOM of
        // MANUF_COMPONENT assocs
        // these need to be retrieved now because they might be needed for exploding MARKETING_PKG_AUTO
        List<GenericValue> orderAdjustments = UtilGenerics.checkList(context.get("orderAdjustments"));
        List<GenericValue> orderItemShipGroupInfo = UtilGenerics.checkList(context.get("orderItemShipGroupInfo"));
        List<GenericValue> orderItemPriceInfo = UtilGenerics.checkList(context.get("orderItemPriceInfos"));

        // check inventory and other things for each item
        List<String> errorMessages = FastList.newInstance();
        Map<String, BigDecimal> normalizedItemQuantities = FastMap.newInstance();
        Map<String, String> normalizedItemNames = FastMap.newInstance();
        Map<String, GenericValue> itemValuesBySeqId = FastMap.newInstance();
        Timestamp nowTimestamp = UtilDateTime.nowTimestamp();

        // need to run through the items combining any cases where multiple lines refer to the
        // same product so the inventory check will work correctly
        // also count quantities ordered while going through the loop
        for (GenericValue orderItem : orderItems) {
            // start by putting it in the itemValuesById Map
            itemValuesBySeqId.put(orderItem.getString("orderItemSeqId"), orderItem);

            String currentProductId = orderItem.getString("productId");
            if (currentProductId != null) {
                // only normalize items with a product associated (ignore non-product items)
                if (normalizedItemQuantities.get(currentProductId) == null) {
                    normalizedItemQuantities.put(currentProductId, orderItem.getBigDecimal("quantity"));
                    normalizedItemNames.put(currentProductId, orderItem.getString("itemDescription"));
                } else {
                    BigDecimal currentQuantity = normalizedItemQuantities.get(currentProductId);
                    normalizedItemQuantities.put(currentProductId, currentQuantity.add(orderItem.getBigDecimal("quantity")));
                }

                try {
                    // count product ordered quantities
                    // run this synchronously so it will run in the same transaction
                    dispatcher.runSync(
                            "countProductQuantityOrdered",
                            UtilMisc.<String, Object>toMap("productId", currentProductId, "quantity",
                                    orderItem.getBigDecimal("quantity"), "userLogin", userLogin));
                } catch (GenericServiceException e1) {
                    Debug.logError(e1, "Error calling countProductQuantityOrdered service", module);
                    return ServiceUtil.returnError(UtilProperties.getMessage(resource_error,
                            "OrderErrorCallingCountProductQuantityOrderedService", locale) + e1.toString());
                }
            }
        }

        if (!"PURCHASE_ORDER".equals(orderTypeId) && (productStoreId == null)) {
            return ServiceUtil.returnError(UtilProperties.getMessage(resource_error,
                    "OrderErrorTheProductStoreIdCanOnlyBeNullForPurchaseOrders", locale));
        }

        Timestamp orderDate = (Timestamp) context.get("orderDate");

        for (String currentProductId : normalizedItemQuantities.keySet()) {
            // lookup the product entity for each normalized item; error on products not found
            BigDecimal currentQuantity = normalizedItemQuantities.get(currentProductId);
            String itemName = normalizedItemNames.get(currentProductId);
            GenericValue product = null;

            try {
                product = delegator.findByPrimaryKeyCache("Product", UtilMisc.toMap("productId", currentProductId));
            } catch (GenericEntityException e) {
                String errMsg = UtilProperties.getMessage(resource_error, "product.not_found",
                        new Object[]{currentProductId}, locale);
                Debug.logError(e, errMsg, module);
                errorMessages.add(errMsg);
                continue;
            }

            if (product == null) {
                String errMsg = UtilProperties.getMessage(resource_error, "product.not_found",
                        new Object[]{currentProductId}, locale);
                Debug.logError(errMsg, module);
                errorMessages.add(errMsg);
                continue;
            }

            if ("SALES_ORDER".equals(orderTypeId)) {
                // check to see if introductionDate hasn't passed yet
                if ((product.get("introductionDate") != null) && nowTimestamp.before(product.getTimestamp("introductionDate"))) {
                    String excMsg = UtilProperties.getMessage(resource_error, "product.not_yet_for_sale",
                            new Object[]{getProductName(product, itemName), product.getString("productId")}, locale);
                    Debug.logWarning(excMsg, module);
                    errorMessages.add(excMsg);
                    continue;
                }
            }

            if ("SALES_ORDER".equals(orderTypeId)) {
                boolean salesDiscontinuationFlag = false;
                // When past orders are imported, they should be imported even if sales discontinuation date is in the past but
                // if the order date was before it
                if ((orderDate != null) && (product.get("salesDiscontinuationDate") != null)) {
                    salesDiscontinuationFlag = orderDate.after(product.getTimestamp("salesDiscontinuationDate"))
                            && nowTimestamp.after(product.getTimestamp("salesDiscontinuationDate"));
                } else if (product.get("salesDiscontinuationDate") != null) {
                    salesDiscontinuationFlag = nowTimestamp.after(product.getTimestamp("salesDiscontinuationDate"));
                }
                // check to see if salesDiscontinuationDate has passed
                if (salesDiscontinuationFlag) {
                    String excMsg = UtilProperties.getMessage(resource_error, "product.no_longer_for_sale",
                            new Object[]{getProductName(product, itemName), product.getString("productId")}, locale);
                    Debug.logWarning(excMsg, module);
                    errorMessages.add(excMsg);
                    continue;
                }
            }

            if ("SALES_ORDER".equals(orderTypeId)) {
                // check to see if we have inventory available
                try {
                    Map<String, Object> invReqResult = dispatcher.runSync("isStoreInventoryAvailableOrNotRequired", UtilMisc
                            .toMap("productStoreId", productStoreId, "productId", product.get("productId"), "product", product,
                                    "quantity", currentQuantity));
                    if (ServiceUtil.isError(invReqResult)) {
                        errorMessages.add((String) invReqResult.get(ModelService.ERROR_MESSAGE));
                        List<String> errMsgList = UtilGenerics.checkList(invReqResult.get(ModelService.ERROR_MESSAGE_LIST));
                        errorMessages.addAll(errMsgList);
                    } else if (!"Y".equals(invReqResult.get("availableOrNotRequired"))) {
                        String invErrMsg = UtilProperties.getMessage(resource_error, "product.out_of_stock",
                                new Object[]{getProductName(product, itemName), currentProductId}, locale);
                        Debug.logWarning(invErrMsg, module);
                        errorMessages.add(invErrMsg);
                        continue;
                    }
                } catch (GenericServiceException e) {
                    String errMsg = "Fatal error calling inventory checking services: " + e.toString();
                    Debug.logError(e, errMsg, module);
                    errorMessages.add(errMsg);
                }
            }
        }

        // add the fixedAsset id to the workefforts map by obtaining the fixed Asset number from the FixedAssetProduct table
        List<GenericValue> workEfforts = UtilGenerics.checkList(context.get("workEfforts")); // is an optional parameter
        // from this service but
        // mandatory for rental items
        for (GenericValue orderItem : orderItems) {
            if ("RENTAL_ORDER_ITEM".equals(orderItem.getString("orderItemTypeId"))) {
                // check to see if workefforts are available for this order type.
                if (UtilValidate.isEmpty(workEfforts)) {
                    String errMsg = "Work Efforts missing for ordertype RENTAL_ORDER_ITEM " + "Product: "
                            + orderItem.getString("productId");
                    Debug.logError(errMsg, module);
                    errorMessages.add(errMsg);
                    return ServiceUtil.returnError(UtilProperties.getMessage(resource_error,
                            "OrderRentalOrderItems", locale));
                }
                for (GenericValue workEffort : workEfforts) {
                    // find the related workEffortItem (workEffortId = orderSeqId)
                    // create the entity maps required.
                    if (workEffort.getString("workEffortId").equals(orderItem.getString("orderItemSeqId"))) {
                        List<GenericValue> selFixedAssetProduct = null;
                        try {
                            List<GenericValue> allFixedAssetProduct = delegator.findByAnd("FixedAssetProduct",
                                    UtilMisc.toMap("productId", orderItem.getString("productId"), "fixedAssetProductTypeId", "FAPT_USE"));
                            selFixedAssetProduct = EntityUtil.filterByDate(allFixedAssetProduct, nowTimestamp, "fromDate",
                                    "thruDate", true);
                        } catch (GenericEntityException e) {
                            String excMsg = "Could not find related Fixed Asset for the product: " + orderItem.getString("productId");
                            Debug.logError(excMsg, module);
                            errorMessages.add(excMsg);
                            return ServiceUtil.returnError(UtilProperties.getMessage(resource_error,
                                    "OrderCouldNotFindRelatedFixedAssetForTheProduct",
                                    UtilMisc.toMap("productId", orderItem.getString("productId")), locale));
                        }

                        if (UtilValidate.isNotEmpty(selFixedAssetProduct)) {
                            Iterator<GenericValue> firstOne = selFixedAssetProduct.iterator();
                            if (firstOne.hasNext()) {
                                GenericValue fixedAssetProduct = delegator.makeValue("FixedAssetProduct");
                                fixedAssetProduct = firstOne.next();
                                workEffort.set("fixedAssetId", fixedAssetProduct.get("fixedAssetId"));
                                workEffort.set("quantityToProduce", orderItem.get("quantity")); // have quantity easy available later...
                                workEffort.set("createdByUserLogin", userLogin.get("userLoginId"));
                            }
                        }
                        break; // item found, so go to next orderitem.
                    }
                }
            }
        }

        if (errorMessages.size() > 0) {
            return ServiceUtil.returnError(errorMessages);
        }

        // the inital status for ALL order types
        String initialStatus = "ORDER_CREATED";
        successResult.put("statusId", initialStatus);

        // create the order object
        String orderId = (String) context.get("orderId");
        String orgPartyId = null;
        if (productStore != null) {
            orgPartyId = productStore.getString("payToPartyId");
        } else if (billFromVendorPartyId != null) {
            orgPartyId = billFromVendorPartyId;
        }

        if (UtilValidate.isNotEmpty(orgPartyId)) {
            Map<String, Object> getNextOrderIdContext = FastMap.newInstance();
            getNextOrderIdContext.putAll(context);
            getNextOrderIdContext.put("partyId", orgPartyId);
            getNextOrderIdContext.put("userLogin", userLogin);

            if ((orderTypeId.equals("SALES_ORDER")) || (productStoreId != null)) {
                getNextOrderIdContext.put("productStoreId", productStoreId);
            }
            if (UtilValidate.isEmpty(orderId)) {
                try {
                    getNextOrderIdContext = ctx.makeValidContext("getNextOrderId", "IN", getNextOrderIdContext);
                    Map<String, Object> getNextOrderIdResult = dispatcher.runSync("getNextOrderId", getNextOrderIdContext);
                    if (ServiceUtil.isError(getNextOrderIdResult)) {
                        String errMsg = UtilProperties.getMessage(resource_error,
                                "OrderErrorGettingNextOrderIdWhileCreatingOrder", locale);
                        return ServiceUtil.returnError(errMsg, null, null, getNextOrderIdResult);
                    }
                    orderId = (String) getNextOrderIdResult.get("orderId");
                } catch (GenericServiceException e) {
                    String errMsg = UtilProperties.getMessage(resource_error,
                            "OrderCaughtGenericServiceExceptionWhileGettingOrderId", locale);
                    Debug.logError(e, errMsg, module);
                    return ServiceUtil.returnError(errMsg);
                }
            }
        }

        if (UtilValidate.isEmpty(orderId)) {
            // for purchase orders or when other orderId generation fails, a product store id should not be required to make
            // an order
            orderId = delegator.getNextSeqId("OrderHeader");
        }

        String billingAccountId = (String) context.get("billingAccountId");
        if (orderDate == null) {
            orderDate = nowTimestamp;
        }

        Map<String, Object> orderHeaderMap = UtilMisc.<String, Object>toMap("orderId", orderId, "orderTypeId",
                orderTypeId,
                "orderDate", orderDate, "entryDate", nowTimestamp,
                "statusId", initialStatus, "billingAccountId", billingAccountId);

        if (isImmediatelyFulfilled) {
            // also flag this order as needing inventory issuance so that when it is set to complete it will be issued
            // immediately (needsInventoryIssuance = Y)
            orderHeaderMap.put("needsInventoryIssuance", "Y");
        }
        GenericValue orderHeader = delegator.makeValue("OrderHeader", orderHeaderMap);

        // determine the sales channel
        String salesChannelEnumId = (String) context.get("salesChannelEnumId");
        if ((salesChannelEnumId == null) || salesChannelEnumId.equals("UNKNWN_SALES_CHANNEL")) {
            // try the default store sales channel
            if (orderTypeId.equals("SALES_ORDER") && (productStore != null)) {
                salesChannelEnumId = productStore.getString("defaultSalesChannelEnumId");
            }
            // if there's still no channel, set to unknown channel
            if (salesChannelEnumId == null) {
                salesChannelEnumId = "UNKNWN_SALES_CHANNEL";
            }
        }
        orderHeader.set("salesChannelEnumId", salesChannelEnumId);

        if (context.get("currencyUom") != null) {
            orderHeader.set("currencyUom", context.get("currencyUom"));
        }

        if (context.get("firstAttemptOrderId") != null) {
            orderHeader.set("firstAttemptOrderId", context.get("firstAttemptOrderId"));
        }

        if (context.get("grandTotal") != null) {
            orderHeader.set("grandTotal", context.get("grandTotal"));
        }

        if (UtilValidate.isNotEmpty(context.get("visitId"))) {
            orderHeader.set("visitId", context.get("visitId"));
        }

        if (UtilValidate.isNotEmpty(context.get("internalCode"))) {
            orderHeader.set("internalCode", context.get("internalCode"));
        }

        if (UtilValidate.isNotEmpty(context.get("externalId"))) {
            orderHeader.set("externalId", context.get("externalId"));
        }

        if (UtilValidate.isNotEmpty(context.get("originFacilityId"))) {
            orderHeader.set("originFacilityId", context.get("originFacilityId"));
        }

        if (UtilValidate.isNotEmpty(context.get("productStoreId"))) {
            orderHeader.set("productStoreId", context.get("productStoreId"));
        }

        if (UtilValidate.isNotEmpty(context.get("transactionId"))) {
            orderHeader.set("transactionId", context.get("transactionId"));
        }

        if (UtilValidate.isNotEmpty(context.get("terminalId"))) {
            orderHeader.set("terminalId", context.get("terminalId"));
        }

        if (UtilValidate.isNotEmpty(context.get("autoOrderShoppingListId"))) {
            orderHeader.set("autoOrderShoppingListId", context.get("autoOrderShoppingListId"));
        }

        if (UtilValidate.isNotEmpty(context.get("webSiteId"))) {
            orderHeader.set("webSiteId", context.get("webSiteId"));
        }

        if ((userLogin != null) && (userLogin.get("userLoginId") != null)) {
            orderHeader.set("createdBy", userLogin.getString("userLoginId"));
        }

        // Del by zhajh at 2014/12/24 Begin
        // String invoicePerShipment = UtilProperties.getPropertyValue("AccountingConfig", "create.invoice.per.shipment");
        // if (UtilValidate.isNotEmpty(invoicePerShipment)) {
        // orderHeader.set("invoicePerShipment", invoicePerShipment);
        // }
        // Del by zhajh at 2014/12/24 End

        // first try to create the OrderHeader; if this does not fail, continue.
        try {
            delegator.create(orderHeader);
        } catch (GenericEntityException e) {
            Debug.logError(e, "Cannot create OrderHeader entity; problems with insert", module);
            return ServiceUtil.returnError(UtilProperties.getMessage(resource_error,
                    "OrderOrderCreationFailedPleaseNotifyCustomerService", locale));
        }

        // create the order status record
        String orderStatusSeqId = delegator.getNextSeqId("OrderStatus");
        GenericValue orderStatus = delegator.makeValue("OrderStatus", UtilMisc.toMap("orderStatusId", orderStatusSeqId));
        orderStatus.set("orderId", orderId);
        orderStatus.set("statusId", orderHeader.getString("statusId"));
        orderStatus.set("statusDatetime", nowTimestamp);
        orderStatus.set("statusUserLogin", userLogin.getString("userLoginId"));
        toBeStored.add(orderStatus);

        // before processing orderItems process orderItemGroups so that they'll be in place for the foreign keys and what
        // not
        List<GenericValue> orderItemGroups = UtilGenerics.checkList(context.get("orderItemGroups"));
        if (UtilValidate.isNotEmpty(orderItemGroups)) {
            for (GenericValue orderItemGroup : orderItemGroups) {
                orderItemGroup.set("orderId", orderId);
                toBeStored.add(orderItemGroup);
            }
        }

        // set the order items
        for (GenericValue orderItem : orderItems) {
            orderItem.set("orderId", orderId);
            toBeStored.add(orderItem);

            // create the item status record
            String itemStatusId = delegator.getNextSeqId("OrderStatus");
            GenericValue itemStatus = delegator.makeValue("OrderStatus", UtilMisc.toMap("orderStatusId", itemStatusId));
            itemStatus.put("statusId", orderItem.get("statusId"));
            itemStatus.put("orderId", orderId);
            itemStatus.put("orderItemSeqId", orderItem.get("orderItemSeqId"));
            itemStatus.put("statusDatetime", nowTimestamp);
            itemStatus.set("statusUserLogin", userLogin.getString("userLoginId"));
            toBeStored.add(itemStatus);
        }

        // set the order attributes
        List<GenericValue> orderAttributes = UtilGenerics.checkList(context.get("orderAttributes"));
        if (UtilValidate.isNotEmpty(orderAttributes)) {
            for (GenericValue oatt : orderAttributes) {
                oatt.set("orderId", orderId);
                toBeStored.add(oatt);
            }
        }

        // set the order item attributes
        List<GenericValue> orderItemAttributes = UtilGenerics.checkList(context.get("orderItemAttributes"));
        if (UtilValidate.isNotEmpty(orderItemAttributes)) {
            for (GenericValue oiatt : orderItemAttributes) {
                oiatt.set("orderId", orderId);
                toBeStored.add(oiatt);
            }
        }

        // create the order internal notes
        List<String> orderInternalNotes = UtilGenerics.checkList(context.get("orderInternalNotes"));
        if (UtilValidate.isNotEmpty(orderInternalNotes)) {
            for (String orderInternalNote : orderInternalNotes) {
                try {
                    Map<String, Object> noteOutputMap = dispatcher.runSync("createOrderNote",
                            UtilMisc.<String, Object>toMap("orderId", orderId,
                                    "internalNote", "Y",
                                    "note", orderInternalNote,
                                    "userLogin", userLogin));
                    if (ServiceUtil.isError(noteOutputMap)) {
                        return ServiceUtil.returnError(UtilProperties.getMessage(resource,
                                "OrderOrderNoteCannotBeCreated", UtilMisc.toMap("errorString", ""), locale),
                                null, null, noteOutputMap);
                    }
                } catch (GenericServiceException e) {
                    Debug.logError(e, "Error creating internal notes while creating order: " + e.toString(), module);
                    return ServiceUtil.returnError(UtilProperties.getMessage(resource,
                            "OrderOrderNoteCannotBeCreated", UtilMisc.toMap("errorString", e.toString()), locale));
                }
            }
        }

        // create the order public notes
        List<String> orderNotes = UtilGenerics.checkList(context.get("orderNotes"));
        if (UtilValidate.isNotEmpty(orderNotes)) {
            for (String orderNote : orderNotes) {
                try {
                    Map<String, Object> noteOutputMap = dispatcher.runSync("createOrderNote",
                            UtilMisc.<String, Object>toMap("orderId", orderId,
                                    "internalNote", "N",
                                    "note", orderNote,
                                    "userLogin", userLogin));
                    if (ServiceUtil.isError(noteOutputMap)) {
                        return ServiceUtil.returnError(UtilProperties.getMessage(resource,
                                "OrderOrderNoteCannotBeCreated", UtilMisc.toMap("errorString", ""), locale),
                                null, null, noteOutputMap);
                    }
                } catch (GenericServiceException e) {
                    Debug.logError(e, "Error creating notes while creating order: " + e.toString(), module);
                    return ServiceUtil.returnError(UtilProperties.getMessage(resource,
                            "OrderOrderNoteCannotBeCreated", UtilMisc.toMap("errorString", e.toString()), locale));
                }
            }
        }

        // create the workeffort records
        // and connect them with the orderitem over the WorkOrderItemFulfillment
        // create also the techData calendars to keep track of availability of the fixed asset.
        if (UtilValidate.isNotEmpty(workEfforts)) {
            List<GenericValue> tempList = new LinkedList<GenericValue>();
            for (GenericValue workEffort : workEfforts) {
                // create the entity maps required.
                GenericValue workOrderItemFulfillment = delegator.makeValue("WorkOrderItemFulfillment");
                // find fixed asset supplied on the workeffort map
                GenericValue fixedAsset = null;
                Debug.logInfo("find the fixedAsset", module);
                try {
                    fixedAsset = delegator.findByPrimaryKey("FixedAsset",
                            UtilMisc.toMap("fixedAssetId", workEffort.get("fixedAssetId")));
                } catch (GenericEntityException e) {
                    return ServiceUtil.returnError(UtilProperties.getMessage(resource_error,
                            "OrderFixedAssetNotFoundFixedAssetId",
                            UtilMisc.toMap("fixedAssetId", workEffort.get("fixedAssetId")), locale));
                }
                if (fixedAsset == null) {
                    return ServiceUtil.returnError(UtilProperties.getMessage(resource_error,
                            "OrderFixedAssetNotFoundFixedAssetId",
                            UtilMisc.toMap("fixedAssetId", workEffort.get("fixedAssetId")), locale));
                }
                // see if this fixed asset has a calendar, when no create one and attach to fixed asset
                Debug.logInfo("find the techdatacalendar", module);
                GenericValue techDataCalendar = null;
                try {
                    techDataCalendar = fixedAsset.getRelatedOne("TechDataCalendar");
                } catch (GenericEntityException e) {
                    Debug.logInfo(
                            "TechData calendar does not exist yet so create for fixedAsset: " + fixedAsset.get("fixedAssetId"),
                            module);
                }
                if (techDataCalendar == null) {
                    for (GenericValue currentValue : tempList) {
                        if ("FixedAsset".equals(currentValue.getEntityName())
                                && currentValue.getString("fixedAssetId").equals(workEffort.getString("fixedAssetId"))) {
                            fixedAsset = currentValue;
                            break;
                        }
                    }
                    for (GenericValue currentValue : tempList) {
                        if ("TechDataCalendar".equals(currentValue.getEntityName())
                                && currentValue.getString("calendarId").equals(fixedAsset.getString("calendarId"))) {
                            techDataCalendar = currentValue;
                            break;
                        }
                    }
                }
                if (techDataCalendar == null) {
                    techDataCalendar = delegator.makeValue("TechDataCalendar");
                    Debug.logInfo("create techdata calendar because it does not exist", module);
                    String calendarId = delegator.getNextSeqId("TechDataCalendar");
                    techDataCalendar.set("calendarId", calendarId);
                    tempList.add(techDataCalendar);
                    Debug.logInfo("update fixed Asset", module);
                    fixedAsset.set("calendarId", calendarId);
                    tempList.add(fixedAsset);
                }
                // then create the workEffort and the workOrderItemFulfillment to connect to the order and orderItem
                workOrderItemFulfillment.set("orderItemSeqId", workEffort.get("workEffortId").toString()); // orderItemSeqNo is
                // stored here so
                // save first
                // workeffort
                String workEffortId = delegator.getNextSeqId("WorkEffort"); // find next available workEffortId
                workEffort.set("workEffortId", workEffortId);
                workEffort.set("workEffortTypeId", "ASSET_USAGE");
                toBeStored.add(workEffort); // store workeffort before workOrderItemFulfillment because of workEffortId key
                // constraint
                // workOrderItemFulfillment
                workOrderItemFulfillment.set("workEffortId", workEffortId);
                workOrderItemFulfillment.set("orderId", orderId);
                toBeStored.add(workOrderItemFulfillment);
                // Debug.logInfo("Workeffort "+ workEffortId + " created for asset " + workEffort.get("fixedAssetId") +
                // " and order "+ workOrderItemFulfillment.get("orderId") + "/" + workOrderItemFulfillment.get("orderItemSeqId")
                // + " created", module);
                //
                // now create the TechDataExcDay, when they do not exist, create otherwise update the capacity values
                // please note that calendarId is the same for (TechData)Calendar, CalendarExcDay and CalendarExWeek
                Timestamp estimatedStartDate = workEffort.getTimestamp("estimatedStartDate");
                Timestamp estimatedCompletionDate = workEffort.getTimestamp("estimatedCompletionDate");
                long dayCount = (estimatedCompletionDate.getTime() - estimatedStartDate.getTime()) / 86400000;
                while (--dayCount >= 0) {
                    GenericValue techDataCalendarExcDay = null;
                    // find an existing Day exception record
                    Timestamp exceptionDateStartTime = UtilDateTime.getDayStart(new Timestamp(estimatedStartDate.getTime()),
                            (int) dayCount);
                    try {
                        techDataCalendarExcDay = delegator.findByPrimaryKey("TechDataCalendarExcDay",
                                UtilMisc.toMap("calendarId", fixedAsset.get("calendarId"), "exceptionDateStartTime",
                                        exceptionDateStartTime));
                    } catch (GenericEntityException e) {
                        Debug.logInfo(" techData excday record not found so creating........", module);
                    }
                    if (techDataCalendarExcDay == null) {
                        for (GenericValue currentValue : tempList) {
                            if ("TechDataCalendarExcDay".equals(currentValue.getEntityName())
                                    && currentValue.getString("calendarId").equals(fixedAsset.getString("calendarId"))
                                    && currentValue.getTimestamp("exceptionDateStartTime").equals(exceptionDateStartTime)) {
                                techDataCalendarExcDay = currentValue;
                                break;
                            }
                        }
                    }
                    if (techDataCalendarExcDay == null) {
                        techDataCalendarExcDay = delegator.makeValue("TechDataCalendarExcDay");
                        techDataCalendarExcDay.set("calendarId", fixedAsset.get("calendarId"));
                        techDataCalendarExcDay.set("exceptionDateStartTime", exceptionDateStartTime);
                        techDataCalendarExcDay.set("usedCapacity", BigDecimal.ZERO); // initialise to zero
                        techDataCalendarExcDay.set("exceptionCapacity", fixedAsset.getBigDecimal("productionCapacity"));
                        // Debug.logInfo(" techData excday record not found creating for calendarId: " +
                        // techDataCalendarExcDay.getString("calendarId") +
                        // " and date: " + exceptionDateStartTime.toString(), module);
                    }
                    // add the quantity to the quantity on the date record
                    BigDecimal newUsedCapacity = techDataCalendarExcDay.getBigDecimal("usedCapacity").add(
                            workEffort.getBigDecimal("quantityToProduce"));
                    // check to see if the requested quantity is available on the requested day but only when the maximum capacity
                    // is set on the fixed asset
                    if (fixedAsset.get("productionCapacity") != null) {
                        // Debug.logInfo("see if maximum not reached, available:  " +
                        // techDataCalendarExcDay.getString("exceptionCapacity") +
                        // " already allocated: " + techDataCalendarExcDay.getString("usedCapacity") +
                        // " Requested: " + workEffort.getString("quantityToProduce"), module);
                        if (newUsedCapacity.compareTo(techDataCalendarExcDay.getBigDecimal("exceptionCapacity")) > 0) {
                            String errMsg = "ERROR: fixed_Asset_sold_out AssetId: " + workEffort.get("fixedAssetId") + " on date: "
                                    + techDataCalendarExcDay.getString("exceptionDateStartTime");
                            Debug.logError(errMsg, module);
                            errorMessages.add(errMsg);
                            continue;
                        }
                    }
                    techDataCalendarExcDay.set("usedCapacity", newUsedCapacity);
                    tempList.add(techDataCalendarExcDay);
                    // Debug.logInfo("Update success CalendarID: " + techDataCalendarExcDay.get("calendarId").toString() +
                    // " and for date: " + techDataCalendarExcDay.get("exceptionDateStartTime").toString() +
                    // " and for quantity: " + techDataCalendarExcDay.getDouble("usedCapacity").toString(), module);
                }
            }
            if (tempList.size() > 0) {
                toBeStored.addAll(tempList);
            }
        }
        if (errorMessages.size() > 0) {
            return ServiceUtil.returnError(errorMessages);
        }

        // set the orderId on all adjustments; this list will include order and
        // item adjustments...
        if (UtilValidate.isNotEmpty(orderAdjustments)) {
            for (GenericValue orderAdjustment : orderAdjustments) {
                try {
                    orderAdjustment.set("orderAdjustmentId", delegator.getNextSeqId("OrderAdjustment"));
                } catch (IllegalArgumentException e) {
                    return ServiceUtil.returnError(UtilProperties.getMessage(resource_error,
                            "OrderErrorCouldNotGetNextSequenceIdForOrderAdjustmentCannotCreateOrder", locale));
                }

                orderAdjustment.set("orderId", orderId);
                orderAdjustment.set("createdDate", UtilDateTime.nowTimestamp());
                orderAdjustment.set("createdByUserLogin", userLogin.getString("userLoginId"));

                if (UtilValidate.isEmpty(orderAdjustment.get("orderItemSeqId"))) {
                    orderAdjustment.set("orderItemSeqId", DataModelConstants.SEQ_ID_NA);
                }
                if (UtilValidate.isEmpty(orderAdjustment.get("shipGroupSeqId"))) {
                    orderAdjustment.set("shipGroupSeqId", DataModelConstants.SEQ_ID_NA);
                }
                toBeStored.add(orderAdjustment);
            }
        }

        // set the order contact mechs
        List<GenericValue> orderContactMechs = UtilGenerics.checkList(context.get("orderContactMechs"));
        if (UtilValidate.isNotEmpty(orderContactMechs)) {
            for (GenericValue ocm : orderContactMechs) {
                ocm.set("orderId", orderId);
                toBeStored.add(ocm);
            }
        }

        // set the order item contact mechs
        List<GenericValue> orderItemContactMechs = UtilGenerics.checkList(context.get("orderItemContactMechs"));
        if (UtilValidate.isNotEmpty(orderItemContactMechs)) {
            for (GenericValue oicm : orderItemContactMechs) {
                oicm.set("orderId", orderId);
                toBeStored.add(oicm);
            }
        }

        // set the order item ship groups
        List<String> dropShipGroupIds = FastList.newInstance(); // this list will contain the ids of all the ship groups for
        // drop shipments (no reservations)
        if (UtilValidate.isNotEmpty(orderItemShipGroupInfo)) {
            for (GenericValue valueObj : orderItemShipGroupInfo) {
                valueObj.set("orderId", orderId);
                if ("OrderItemShipGroup".equals(valueObj.getEntityName())) {
                    // ship group
                    if (valueObj.get("carrierRoleTypeId") == null) {
                        valueObj.set("carrierRoleTypeId", "CARRIER");
                    }
                    if (!UtilValidate.isEmpty(valueObj.getString("supplierPartyId"))) {
                        dropShipGroupIds.add(valueObj.getString("shipGroupSeqId"));
                    }
                } else if ("OrderAdjustment".equals(valueObj.getEntityName())) {
                    // shipping / tax adjustment(s)
                    if (UtilValidate.isEmpty(valueObj.get("orderItemSeqId"))) {
                        valueObj.set("orderItemSeqId", DataModelConstants.SEQ_ID_NA);
                    }
                    valueObj.set("orderAdjustmentId", delegator.getNextSeqId("OrderAdjustment"));
                    valueObj.set("createdDate", UtilDateTime.nowTimestamp());
                    valueObj.set("createdByUserLogin", userLogin.getString("userLoginId"));
                }
                toBeStored.add(valueObj);
            }
        }

        // set the additional party roles
        Map<String, List<String>> additionalPartyRole = UtilGenerics.checkMap(context.get("orderAdditionalPartyRoleMap"));
        if (additionalPartyRole != null) {
            for (Map.Entry<String, List<String>> entry : additionalPartyRole.entrySet()) {
                String additionalRoleTypeId = entry.getKey();
                List<String> parties = entry.getValue();
                if (parties != null) {
                    for (String additionalPartyId : parties) {
                        toBeStored.add(delegator.makeValue("PartyRole",
                                UtilMisc.toMap("partyId", additionalPartyId, "roleTypeId", additionalRoleTypeId)));
                        toBeStored.add(delegator.makeValue("OrderRole",
                                UtilMisc.toMap("orderId", orderId, "partyId", additionalPartyId, "roleTypeId", additionalRoleTypeId)));
                    }
                }
            }
        }

        // set the item survey responses
        List<GenericValue> surveyResponses = UtilGenerics.checkList(context.get("orderItemSurveyResponses"));
        if (UtilValidate.isNotEmpty(surveyResponses)) {
            for (GenericValue surveyResponse : surveyResponses) {
                surveyResponse.set("orderId", orderId);
                toBeStored.add(surveyResponse);
            }
        }

        // set the item price info; NOTE: this must be after the orderItems are stored for referential integrity
        if (UtilValidate.isNotEmpty(orderItemPriceInfo)) {
            for (GenericValue oipi : orderItemPriceInfo) {
                try {
                    oipi.set("orderItemPriceInfoId", delegator.getNextSeqId("OrderItemPriceInfo"));
                } catch (IllegalArgumentException e) {
                    return ServiceUtil.returnError(UtilProperties.getMessage(resource_error,
                            "OrderErrorCouldNotGetNextSequenceIdForOrderItemPriceInfoCannotCreateOrder", locale));
                }

                oipi.set("orderId", orderId);
                toBeStored.add(oipi);
            }
        }

        // set the item associations
        List<GenericValue> orderItemAssociations = UtilGenerics.checkList(context.get("orderItemAssociations"));
        if (UtilValidate.isNotEmpty(orderItemAssociations)) {
            for (GenericValue orderItemAssociation : orderItemAssociations) {
                if (orderItemAssociation.get("toOrderId") == null) {
                    orderItemAssociation.set("toOrderId", orderId);
                } else if (orderItemAssociation.get("orderId") == null) {
                    orderItemAssociation.set("orderId", orderId);
                }
                toBeStored.add(orderItemAssociation);
            }
        }

        // store the orderProductPromoUseInfos
        List<GenericValue> orderProductPromoUses = UtilGenerics.checkList(context.get("orderProductPromoUses"));
        if (UtilValidate.isNotEmpty(orderProductPromoUses)) {
            for (GenericValue productPromoUse : orderProductPromoUses) {
                productPromoUse.set("orderId", orderId);
                toBeStored.add(productPromoUse);
            }
        }

        // store the orderProductPromoCodes
        Set<String> orderProductPromoCodes = UtilGenerics.checkSet(context.get("orderProductPromoCodes"));
        if (UtilValidate.isNotEmpty(orderProductPromoCodes)) {
            for (String productPromoCodeId : orderProductPromoCodes) {
                GenericValue orderProductPromoCode = delegator.makeValue("OrderProductPromoCode");
                orderProductPromoCode.set("orderId", orderId);
                orderProductPromoCode.set("productPromoCodeId", productPromoCodeId);
                toBeStored.add(orderProductPromoCode);
                //修改promoCode 状态已使用
                GenericValue promoCode = null;
                try {
                    promoCode = delegator.findByPrimaryKey("ProductPromoCode", UtilMisc.toMap("productPromoCodeId",productPromoCodeId));
                    promoCode.put("promoCodeStatus","U");
                    toBeStored.add(promoCode);
                } catch (GenericEntityException e) {
                    e.printStackTrace();
                }
                
            }
        }

    /*
     * DEJ20050529 the OLD way, where a single party had all roles... no longer doing things this way...
     * // define the roles for the order
     * List userOrderRoleTypes = null;
     * if ("SALES_ORDER".equals(orderTypeId)) {
     * userOrderRoleTypes = UtilMisc.toList("END_USER_CUSTOMER", "SHIP_TO_CUSTOMER", "BILL_TO_CUSTOMER",
     * "PLACING_CUSTOMER");
     * } else if ("PURCHASE_ORDER".equals(orderTypeId)) {
     * userOrderRoleTypes = UtilMisc.toList("SHIP_FROM_VENDOR", "BILL_FROM_VENDOR", "SUPPLIER_AGENT");
     * } else {
     * // TODO: some default behavior
     * }
     * // now add the roles
     * if (userOrderRoleTypes != null) {
     * Iterator i = userOrderRoleTypes.iterator();
     * while (i.hasNext()) {
     * String roleType = (String) i.next();
     * String thisParty = partyId;
     * if (thisParty == null) {
     * thisParty = "_NA_"; // will always set these roles so we can query
     * }
     * // make sure the party is in the role before adding
     * toBeStored.add(delegator.makeValue("PartyRole", UtilMisc.toMap("partyId", partyId, "roleTypeId", roleType)));
     * toBeStored.add(delegator.makeValue("OrderRole", UtilMisc.toMap("orderId", orderId, "partyId", partyId,
     * "roleTypeId", roleType)));
     * }
     * }
     */

        // see the attributeRoleMap definition near the top of this file for attribute-role mappings
        Map<String, String> attributeRoleMap = salesAttributeRoleMap;
        if ("PURCHASE_ORDER".equals(orderTypeId)) {
            attributeRoleMap = purchaseAttributeRoleMap;
        }
        for (Map.Entry<String, String> attributeRoleEntry : attributeRoleMap.entrySet()) {
            if (UtilValidate.isNotEmpty(context.get(attributeRoleEntry.getKey()))) {
                // make sure the party is in the role before adding
                toBeStored.add(delegator.makeValue(
                        "PartyRole",
                        UtilMisc.toMap("partyId", context.get(attributeRoleEntry.getKey()), "roleTypeId",
                                attributeRoleEntry.getValue())));
                toBeStored.add(delegator.makeValue("OrderRole", UtilMisc.toMap("orderId", orderId, "partyId",
                        context.get(attributeRoleEntry.getKey()), "roleTypeId", attributeRoleEntry.getValue())));
            }
        }

        // set the affiliate -- This is going to be removed...
        String affiliateId = (String) context.get("affiliateId");
        if (UtilValidate.isNotEmpty(affiliateId)) {
            toBeStored.add(delegator.makeValue("OrderRole",
                    UtilMisc.toMap("orderId", orderId, "partyId", affiliateId, "roleTypeId", "AFFILIATE")));
        }

        // set the distributor
        String distributorId = (String) context.get("distributorId");
        if (UtilValidate.isNotEmpty(distributorId)) {
            toBeStored.add(delegator.makeValue("OrderRole",
                    UtilMisc.toMap("orderId", orderId, "partyId", distributorId, "roleTypeId", "DISTRIBUTOR")));
        }

        // find all parties in role VENDOR associated with WebSite OR ProductStore (where WebSite overrides, if specified),
        // associated first valid with the Order
        if (UtilValidate.isNotEmpty(context.get("productStoreId"))) {
            try {
                List<GenericValue> productStoreRoles = delegator.findByAnd("ProductStoreRole",
                        UtilMisc.toMap("roleTypeId", "VENDOR", "productStoreId", context.get("productStoreId")),
                        UtilMisc.toList("-fromDate"));
                productStoreRoles = EntityUtil.filterByDate(productStoreRoles, true);
                GenericValue productStoreRole = EntityUtil.getFirst(productStoreRoles);
                if (productStoreRole != null) {
                    toBeStored.add(delegator.makeValue("OrderRole",
                            UtilMisc.toMap("orderId", orderId, "partyId", productStoreRole.get("partyId"), "roleTypeId", "VENDOR")));
                }
            } catch (GenericEntityException e) {
                Debug.logError(e, "Error looking up Vendor for the current Product Store", module);
            }

        }
        if (UtilValidate.isNotEmpty(context.get("webSiteId"))) {
            try {
                List<GenericValue> webSiteRoles = delegator
                        .findByAnd("WebSiteRole", UtilMisc.toMap("roleTypeId", "VENDOR", "webSiteId", context.get("webSiteId")),
                                UtilMisc.toList("-fromDate"));
                webSiteRoles = EntityUtil.filterByDate(webSiteRoles, true);
                GenericValue webSiteRole = EntityUtil.getFirst(webSiteRoles);
                if (webSiteRole != null) {
                    toBeStored.add(delegator.makeValue("OrderRole",
                            UtilMisc.toMap("orderId", orderId, "partyId", webSiteRole.get("partyId"), "roleTypeId", "VENDOR")));
                }
            } catch (GenericEntityException e) {
                Debug.logError(e, "Error looking up Vendor for the current Web Site", module);
            }

        }

        // set the order payment info
        List<GenericValue> orderPaymentInfos = UtilGenerics.checkList(context.get("orderPaymentInfo"));
        if (UtilValidate.isNotEmpty(orderPaymentInfos)) {
            for (GenericValue valueObj : orderPaymentInfos) {
                valueObj.set("orderId", orderId);
                if ("OrderPaymentPreference".equals(valueObj.getEntityName())) {
                    if (valueObj.get("orderPaymentPreferenceId") == null) {
                        valueObj.set("orderPaymentPreferenceId", delegator.getNextSeqId("OrderPaymentPreference"));
                        valueObj.set("createdDate", UtilDateTime.nowTimestamp());
                        valueObj.set("createdByUserLogin", userLogin.getString("userLoginId"));
                    }
                    if (valueObj.get("statusId") == null) {
                        valueObj.set("statusId", "PAYMENT_NOT_RECEIVED");
                    }
                }
                toBeStored.add(valueObj);
            }
        }

        // store the trackingCodeOrder entities
        List<GenericValue> trackingCodeOrders = UtilGenerics.checkList(context.get("trackingCodeOrders"));
        if (UtilValidate.isNotEmpty(trackingCodeOrders)) {
            for (GenericValue trackingCodeOrder : trackingCodeOrders) {
                trackingCodeOrder.set("orderId", orderId);
                toBeStored.add(trackingCodeOrder);
            }
        }

        // store the OrderTerm entities

        List<GenericValue> orderTerms = UtilGenerics.checkList(context.get("orderTerms"));
        if (UtilValidate.isNotEmpty(orderTerms)) {
            for (GenericValue orderTerm : orderTerms) {
                orderTerm.set("orderId", orderId);
                if (orderTerm.get("orderItemSeqId") == null) {
                    orderTerm.set("orderItemSeqId", "_NA_");
                }
                toBeStored.add(orderTerm);
            }
        }

        // if a workEffortId is passed, then prepare a OrderHeaderWorkEffort value
        String workEffortId = (String) context.get("workEffortId");
        if (UtilValidate.isNotEmpty(workEffortId)) {
            GenericValue orderHeaderWorkEffort = delegator.makeValue("OrderHeaderWorkEffort");
            orderHeaderWorkEffort.set("orderId", orderId);
            orderHeaderWorkEffort.set("workEffortId", workEffortId);
            toBeStored.add(orderHeaderWorkEffort);
        }

        try {
            // store line items, etc so that they will be there for the foreign key checks
            delegator.storeAll(toBeStored);

            List<String> resErrorMessages = new LinkedList<String>();

            // add a product service to inventory
            if (UtilValidate.isNotEmpty(orderItems)) {
                for (GenericValue orderItem : orderItems) {
                    String productId = (String) orderItem.get("productId");
                    GenericValue product = delegator.getRelatedOne("Product", orderItem);

                    if ((product != null)
                            && ("SERVICE_PRODUCT".equals(product.get("productTypeId")) || "AGGREGATEDSERV_CONF".equals(product
                            .get("productTypeId")))) {
                        String inventoryFacilityId = null;
                        if ("Y".equals(productStore.getString("oneInventoryFacility"))) {
                            inventoryFacilityId = productStore.getString("inventoryFacilityId");

                            if (UtilValidate.isEmpty(inventoryFacilityId)) {
                                Debug
                                        .logWarning(
                                                "ProductStore with id "
                                                        + productStoreId
                                                        + " has Y for oneInventoryFacility but inventoryFacilityId is empty, returning false for inventory check",
                                                module);
                            }
                        } else {
                            List<GenericValue> productFacilities = null;
                            GenericValue productFacility = null;

                            try {
                                productFacilities = delegator.getRelatedCache("ProductFacility", product);
                            } catch (GenericEntityException e) {
                                Debug.logWarning(e, "Error invoking getRelatedCache in isCatalogInventoryAvailable", module);
                            }

                            if (UtilValidate.isNotEmpty(productFacilities)) {
                                productFacility = EntityUtil.getFirst(productFacilities);
                                inventoryFacilityId = (String) productFacility.get("facilityId");
                            }
                        }

                        Map<String, Object> ripCtx = FastMap.newInstance();
                        if (UtilValidate.isNotEmpty(inventoryFacilityId) && UtilValidate.isNotEmpty(productId)
                                && (orderItem.getBigDecimal("quantity").compareTo(BigDecimal.ZERO) > 0)) {
                            // do something tricky here: run as the "system" user
                            GenericValue permUserLogin = delegator.findByPrimaryKeyCache("UserLogin",
                                    UtilMisc.toMap("userLoginId", "system"));
                            ripCtx.put("productId", productId);
                            ripCtx.put("facilityId", inventoryFacilityId);
                            ripCtx.put("inventoryItemTypeId", "SERIALIZED_INV_ITEM");
                            ripCtx.put("statusId", "INV_AVAILABLE");
                            ripCtx.put("quantityAccepted", orderItem.getBigDecimal("quantity"));
                            ripCtx.put("quantityRejected", 0.0);
                            ripCtx.put("userLogin", permUserLogin);
                            try {
                                Map<String, Object> ripResult = dispatcher.runSync("receiveInventoryProduct", ripCtx);
                                if (ServiceUtil.isError(ripResult)) {
                                    String errMsg = ServiceUtil.getErrorMessage(ripResult);
                                    resErrorMessages.addAll((Collection<? extends String>) UtilMisc.<String, String>toMap("reasonCode",
                                            "ReceiveInventoryServiceError", "description", errMsg));
                                }
                            } catch (GenericServiceException e) {
                                Debug.logWarning(e, "Error invoking receiveInventoryProduct service in createOrder", module);
                            }
                        }
                    }
                }
            }

            // START inventory reservation
            try {
                reserveInventory(delegator, dispatcher, userLogin, locale, orderItemShipGroupInfo, dropShipGroupIds,
                        itemValuesBySeqId,
                        orderTypeId, productStoreId, resErrorMessages);
            } catch (GeneralException e) {
                return ServiceUtil.returnError(e.getMessage());
            }

            if (resErrorMessages.size() > 0) {
                return ServiceUtil.returnError(resErrorMessages);
            }
            // END inventory reservation

            successResult.put("orderId", orderId);
        } catch (GenericEntityException e) {
            Debug.logError(e, "Problem with order storage or reservations", module);
            return ServiceUtil.returnError(UtilProperties.getMessage(resource_error,
                    "OrderErrorCouldNotCreateOrderWriteError", locale) + e.getMessage() + ").");
        }

        return successResult;
    }

    /**
     * @param ctx     待付款,待发货,待收货,待评价,已取消,已完成
     * @param context PAYMENT_NOT_RECEIVED,NOT_SHIPPING,SHIPPED,NO_REVIEW,ORDER_CANCELLED,ORDER_COMPLETE
     * @return 订单状态, 支付状态, 货运状态
     */
    public static Map<String, Object> queryOrder(DispatchContext ctx, Map<String, ? extends Object> context) {
        Map<String, Object> result = ServiceUtil.returnSuccess();
        Delegator delegator = ctx.getDelegator();
        GenericValue userLogin = (GenericValue) context.get("userLogin");
        String month = (String) context.get("month");
        String status = (String) context.get("status");
        GenericValue partyRole = null;
        try {
            List conditions = FastList.newInstance();
            if (month != null && month.equals("3")) {
                Calendar calendar = Calendar.getInstance();//创建实例
                calendar.setTime(UtilDateTime.nowDate());
                calendar.add(Calendar.MONTH, -3);//三个月
                Date date = calendar.getTime();//三个月
                conditions.add(EntityCondition.makeCondition("orderDate", EntityOperator.GREATER_THAN, UtilDateTime.toTimestamp(date)));
            }

            List<GenericValue> orderHeaderList = null;
            conditions.add(EntityCondition.makeCondition("statusId", EntityOperator.NOT_EQUAL, "ORDER_REJECTED"));
            List<GenericValue> orderRoleCollection = delegator.findByAnd("OrderRole", UtilMisc.toMap("partyId", userLogin.get("partyId"), "roleTypeId", "PLACING_CUSTOMER"));

            if (status == null || status.equals("")) {

                orderHeaderList = EntityUtil.filterByAnd(EntityUtil.getRelated("OrderHeader", orderRoleCollection),
                        UtilMisc.toList(EntityCondition.makeCondition(conditions, EntityOperator.AND)));
            } else {
                if (status != null && !status.equals("")) {
                    //待付款: orderPaymentReference stautsId=PAYMENT_NOT_RECEIVED,并且订单未取消
                    if (status.equals("PAYMENT_NOT_RECEIVED")) {
                        List<GenericValue> porderHeaderList = EntityUtil.filterByAnd(EntityUtil.getRelated("OrderPaymentPreference", orderRoleCollection),
                                UtilMisc.toList(EntityCondition.makeCondition("statusId", EntityOperator.EQUALS, "PAYMENT_NOT_RECEIVED")));
                        orderHeaderList = EntityUtil.getRelated("OrderHeader", porderHeaderList);
                        orderHeaderList = EntityUtil.filterByCondition(orderHeaderList, EntityCondition.makeCondition("statusId", EntityOperator.NOT_EQUAL,"ORDER_CANCELLED"));

                    } else if (status.equals("NOT_SHIPPING")) {
                        //待发货
                        //已经支付。
                        //待发货shipment 对应的statusId: 不为 SHIPMENT_CANCELLED 和 SHIPMENT_DELIVERED,SHIPMENT_SHIPPED (包含没有创建shippment)
                        //订单状态不为已完成
                        
                        //shipConds.add(EntityCondition.makeCondition("statusId", EntityOperator.NOT_EQUAL, "SHIPMENT_CANCELLED"));
                        //shipConds.add(EntityCondition.makeCondition("statusId", EntityOperator.NOT_EQUAL, "SHIPMENT_DELIVERED"));
                        //shipConds.add(EntityCondition.makeCondition("statusId", EntityOperator.NOT_EQUAL, "SHIPMENT_SHIPPED"));
                        
                        List newOrderRule = FastList.newInstance();
                        List<GenericValue> sorderHeaderList = EntityUtil.getRelated("Shipment", orderRoleCollection);
                        if (UtilValidate.isNotEmpty(sorderHeaderList)) {
                            newOrderRule.addAll(orderRoleCollection);
                            for (int i = 0; i < sorderHeaderList.size(); i++) {
                                GenericValue shipment = sorderHeaderList.get(i);
                                if (shipment.get("statusId").equals("SHIPMENT_CANCELLED") || shipment.get("statusId").equals("SHIPMENT_DELIVERED") || shipment.get("statusId").equals("SHIPMENT_SHIPPED")) {
                                    if (UtilValidate.isNotEmpty(orderRoleCollection)) {
                                        for (int j = 0; j < orderRoleCollection.size(); j++) {
                                            GenericValue orderRule = orderRoleCollection.get(j);
                                            if (orderRule.get("orderId").equals(shipment.get("primaryOrderId"))) {
                                                newOrderRule.remove(orderRule);
                                            }
                                        }
                                    }
                                }
                            }
                        }
    
                        List<GenericValue> porderHeaderList = EntityUtil.filterByAnd(EntityUtil.getRelated("OrderPaymentPreference", newOrderRule),
                                UtilMisc.toList(EntityCondition.makeCondition("statusId", EntityOperator.EQUALS, "PAYMENT_RECEIVED")));
                        orderHeaderList = EntityUtil.getRelated("OrderHeader", porderHeaderList);
                        orderHeaderList = EntityUtil.filterByCondition(orderHeaderList, EntityCondition.makeCondition("statusId", EntityOperator.NOT_EQUAL,"ORDER_CANCELLED"));
                   
                    } else if (status.equals("SHIPPED")) {
                        //待收货
                        //已经支付,shippment 状态为 已发货
                        // shipment 对应的statusId: 不为 SHIPMENT_CANCELLED 和 SHIPMENT_DELIVERED
                        List newOrderRule = FastList.newInstance();
                        List<GenericValue> sorderHeaderList = EntityUtil.getRelated("Shipment", orderRoleCollection);
                        if (UtilValidate.isNotEmpty(sorderHeaderList)) {
                            for (int i = 0; i < sorderHeaderList.size(); i++) {
                                GenericValue shipment = sorderHeaderList.get(i);
                                if (shipment.get("statusId").equals("SHIPMENT_SHIPPED")) {
                                    if (UtilValidate.isNotEmpty(orderRoleCollection)) {
                                        for (int j = 0; j < orderRoleCollection.size(); j++) {
                                            GenericValue orderRule = orderRoleCollection.get(j);
                                            if (orderRule.get("orderId").equals(shipment.get("primaryOrderId"))) {
                                                newOrderRule.add(orderRule);
                                            }
                                        }
                                    }
                                }
                            }
                        }
                        List<GenericValue> porderHeaderList = EntityUtil.filterByAnd(EntityUtil.getRelated("OrderPaymentPreference", newOrderRule),
                                UtilMisc.toList(EntityCondition.makeCondition("statusId", EntityOperator.EQUALS, "PAYMENT_RECEIVED")));
                        orderHeaderList = EntityUtil.getRelated("OrderHeader", porderHeaderList);
                        orderHeaderList = EntityUtil.filterByCondition(orderHeaderList, EntityCondition.makeCondition("statusId", EntityOperator.NOT_EQUAL,"ORDER_CANCELLED"));
    
                    } else if (status.equals("NO_REVIEW")) {
                        //已经支付,已经收货,待评价
                        List newOrderRule = FastList.newInstance();
                        List<GenericValue> sorderHeaderList = EntityUtil.filterByAnd(EntityUtil.getRelated("ProductReview", orderRoleCollection), UtilMisc.toMap("userLoginId", userLogin.get("userLoginId")));
                        newOrderRule.addAll(orderRoleCollection);
                        if (UtilValidate.isNotEmpty(sorderHeaderList)) {
                          
                            for (int i = 0; i < sorderHeaderList.size(); i++) {
                                GenericValue review = sorderHeaderList.get(i);
                                if (UtilValidate.isNotEmpty(orderRoleCollection)) {
                                    for (int j = 0; j < orderRoleCollection.size(); j++) {
                                        GenericValue orderRule = orderRoleCollection.get(j);
                                        if (orderRule.get("orderId").equals(review.get("orderId"))) {
                                            newOrderRule.remove(orderRule);
                                        }
                                    }

                                }
                            }
                        }
                        List<GenericValue> porderHeaderList = EntityUtil.filterByAnd(EntityUtil.getRelated("OrderPaymentPreference", newOrderRule),
                                UtilMisc.toList(EntityCondition.makeCondition("statusId", EntityOperator.EQUALS, "PAYMENT_RECEIVED")));
                        orderHeaderList = EntityUtil.getRelated("OrderHeader", porderHeaderList);
                        orderHeaderList = EntityUtil.filterByCondition(orderHeaderList, EntityCondition.makeCondition("statusId", EntityOperator.EQUALS,"ORDER_COMPLETED"));
                        
                    } else if (status.equals("ORDER_CANCELLED")) {
                        //订单取消
                        conditions.add(EntityCondition.makeCondition("statusId", EntityOperator.EQUALS, "ORDER_CANCELLED"));
                        orderHeaderList = EntityUtil.filterByAnd(EntityUtil.getRelated("OrderHeader", orderRoleCollection),
                                UtilMisc.toList(EntityCondition.makeCondition(conditions, EntityOperator.AND)));

                    } else if (status.equals("ORDER_COMPLETED")) {
                        
                        //已经支付,已经收货,订单状态已完成,已经评价
                        //已经支付,已经收货,待评价
                        List newOrderRule = FastList.newInstance();
                        List<GenericValue> sorderHeaderList = EntityUtil.filterByAnd(EntityUtil.getRelated("ProductReview", orderRoleCollection), UtilMisc.toMap("userLoginId", userLogin.get("userLoginId")));
                        if (UtilValidate.isNotEmpty(sorderHeaderList)) {
                            for (int i = 0; i < sorderHeaderList.size(); i++) {
                                GenericValue review = sorderHeaderList.get(i);
                                if (UtilValidate.isNotEmpty(orderRoleCollection)) {
                                    for (int j = 0; j < orderRoleCollection.size(); j++) {
                                        GenericValue orderRule = orderRoleCollection.get(j);
                                        if (orderRule.get("orderId").equals(review.get("orderId"))) {
                                            newOrderRule.add(orderRule);
                                        }
                                    }
                
                                }
                            }
                        }
                        List<GenericValue> porderHeaderList = EntityUtil.filterByAnd(EntityUtil.getRelated("OrderPaymentPreference", newOrderRule),
                                UtilMisc.toList(EntityCondition.makeCondition("statusId", EntityOperator.EQUALS, "PAYMENT_RECEIVED")));
                        orderHeaderList = EntityUtil.getRelated("OrderHeader", porderHeaderList);
                        orderHeaderList = EntityUtil.filterByCondition(orderHeaderList, EntityCondition.makeCondition("statusId", EntityOperator.EQUALS,"ORDER_COMPLETED"));
                    }

                } else {
                    orderHeaderList = EntityUtil.filterByAnd(EntityUtil.getRelated("OrderHeader", orderRoleCollection),
                            UtilMisc.toList(EntityCondition.makeCondition(conditions, EntityOperator.AND)));
                }
            }
            orderHeaderList = EntityUtil.orderBy(orderHeaderList, UtilMisc.toList("orderDate DESC"));

            result.put("orderHeaderList", orderHeaderList);


     /* partyRole = delegator.findByPrimaryKey("PartyRole", UtilMisc.toMap("partyId", userLogin.get("partyId"), "roleTypeId", "SUPPLIER"));

      if (UtilValidate.isNotEmpty(partyRole)) {
        if ("SUPPLIER".equals(partyRole.get("roleTypeId"))) {
          *//** drop shipper or supplier **//*
          List<GenericValue> porderRoleCollection = delegator.findByAnd("OrderRole", UtilMisc.toMap("partyId", userLogin.get("partyId"), "roleTypeId", "SUPPLIER_AGENT"));
          List<GenericValue> porderHeaderList = EntityUtil.orderBy(EntityUtil.filterByAnd(EntityUtil.getRelated("OrderHeader", porderRoleCollection),
             UtilMisc.toList(EntityCondition.makeCondition(conditions, EntityOperator.AND))),
             UtilMisc.toList("orderDate DESC"));
          result.put("porderHeaderList", porderHeaderList);
        }
      }*/

        } catch (GenericEntityException e) {
            e.printStackTrace();
        }

        result.put("status", status);
        result.put("month", month);

        return result;
    }
}
