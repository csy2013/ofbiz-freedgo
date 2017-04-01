package com.yuaoq.yabiz.eshop.shoppingcart;

import org.ofbiz.base.util.*;
import org.ofbiz.entity.Delegator;
import org.ofbiz.entity.GenericEntityException;
import org.ofbiz.entity.GenericValue;
import org.ofbiz.entity.util.EntityTypeUtil;
import org.ofbiz.order.shoppingcart.ShoppingCartItem;
import org.ofbiz.party.contact.ContactHelper;
import org.ofbiz.product.store.ProductStoreWorker;
import org.ofbiz.service.GenericServiceException;
import org.ofbiz.service.LocalDispatcher;
import org.ofbiz.service.ServiceUtil;

import java.math.BigDecimal;
import java.util.*;

/**
 * Created by tusm on 15/6/7.
 */
public class CheckOutHelper extends org.ofbiz.order.shoppingcart.CheckOutHelper {

    public CheckOutHelper(LocalDispatcher dispatcher, Delegator delegator, org.ofbiz.order.shoppingcart.ShoppingCart cart) {
        super(dispatcher, delegator, cart);
    }

    public Map<String, Object> createOrder(GenericValue userLogin, String distributorId, String affiliateId,
                                           List<GenericValue> trackingCodeOrders, boolean areOrderItemsExploded, String visitId, String webSiteId) {
        if (this.cart == null) {
            return null;
        }
        String orderId = this.cart.getOrderId();
        String supplierPartyId = this.cart.getAttribute("supplierPartyId");
        String originOrderId = this.cart.getAttribute("originOrderId");

        this.cart.clearAllItemStatus();

        BigDecimal grandTotal = this.cart.getGrandTotal();

        // store the order - build the context
        Map<String, Object> context = this.cart.makeCartMap(this.dispatcher, areOrderItemsExploded);

        //get the TrackingCodeOrder List
        context.put("trackingCodeOrders", trackingCodeOrders);

        if (distributorId != null) context.put("distributorId", distributorId);
        if (affiliateId != null) context.put("affiliateId", affiliateId);

        context.put("orderId", orderId);
        context.put("supplierPartyId", supplierPartyId);
        context.put("grandTotal", grandTotal);
        context.put("userLogin", userLogin);
        context.put("visitId", visitId);
        if (UtilValidate.isEmpty(webSiteId)) {
            webSiteId = cart.getWebSiteId();
        }
        context.put("webSiteId", webSiteId);
        context.put("originOrderId", originOrderId);

        // need the partyId; don't use userLogin in case of an order via order mgr
        String partyId = this.cart.getPartyId();
        String productStoreId = cart.getProductStoreId();

        // store the order - invoke the service
        Map<String, Object> storeResult = null;

        try {
            storeResult = dispatcher.runSync("storeOrderForPortal", context);
            orderId = (String) storeResult.get("orderId");
            if (UtilValidate.isNotEmpty(orderId)) {
                this.cart.setOrderId(orderId);
                if (this.cart.getFirstAttemptOrderId() == null) {
                    this.cart.setFirstAttemptOrderId(orderId);
                }
            }
        } catch (GenericServiceException e) {
            String service = e.getMessage();
            Map<String, Object> messageMap = UtilMisc.<String, Object>toMap("service", service);
            String errMsg = UtilProperties.getMessage(resource_error, "checkhelper.could_not_create_order_invoking_service", messageMap, (cart != null ? cart.getLocale() : Locale.getDefault()));
            Debug.logError(e, errMsg, module);
            return ServiceUtil.returnError(errMsg);
        }

        // check for error message(s)
        if (ServiceUtil.isError(storeResult)) {
            String errMsg = UtilProperties.getMessage(resource_error, "checkhelper.did_not_complete_order_following_occurred", (cart != null ? cart.getLocale() : Locale.getDefault()));
            List<String> resErrorMessages = new LinkedList<String>();
            resErrorMessages.add(errMsg);
            resErrorMessages.add(ServiceUtil.getErrorMessage(storeResult));
            return ServiceUtil.returnError(resErrorMessages);
        }

        // ----------
        // If needed, the production runs are created and linked to the order lines.
        //
        List<GenericValue> orderItems = UtilGenerics.checkList(context.get("orderItems"));
        int counter = 0;
        for(GenericValue orderItem : orderItems) {
            String productId = orderItem.getString("productId");
            if (productId != null) {
                try {
                    // do something tricky here: run as the "system" user
                    // that can actually create and run a production run
                    GenericValue permUserLogin = delegator.findByPrimaryKeyCache("UserLogin", UtilMisc.toMap("userLoginId", "system"));
                    GenericValue productStore = ProductStoreWorker.getProductStore(productStoreId, delegator);
                    GenericValue product = delegator.findByPrimaryKey("Product", UtilMisc.toMap("productId", productId));
                    if (EntityTypeUtil.hasParentType(delegator, "ProductType", "productTypeId", product.getString("productTypeId"), "parentTypeId", "AGGREGATED")) {
                        org.ofbiz.product.config.ProductConfigWrapper config = this.cart.findCartItem(counter).getConfigWrapper();
                        Map<String, Object> inputMap = new HashMap<String, Object>();
                        inputMap.put("config", config);
                        inputMap.put("facilityId", productStore.getString("inventoryFacilityId"));
                        inputMap.put("orderId", orderId);
                        inputMap.put("orderItemSeqId", orderItem.getString("orderItemSeqId"));
                        inputMap.put("quantity", orderItem.getBigDecimal("quantity"));
                        inputMap.put("userLogin", permUserLogin);

                        Map<String, Object> prunResult = dispatcher.runSync("createProductionRunFromConfiguration", inputMap);
                        if (ServiceUtil.isError(prunResult)) {
                            Debug.logError(ServiceUtil.getErrorMessage(prunResult) + " for input:" + inputMap, module);
                        }
                    }
                } catch (Exception e) {
                    String service = e.getMessage();
                    Map<String, String> messageMap = UtilMisc.toMap("service", service);
                    String errMsg = UtilProperties.getMessage(resource_error, "checkhelper.could_not_create_order_invoking_service", messageMap, (cart != null ? cart.getLocale() : Locale.getDefault()));
                    Debug.logError(e, errMsg, module);
                    return ServiceUtil.returnError(errMsg);
                }
            }
            counter++;
        }
        // ----------

        // ----------
        // The status of the requirement associated to the shopping cart lines is set to "ordered".
        //
        for(ShoppingCartItem shoppingCartItem : this.cart.items()) {
            String requirementId = shoppingCartItem.getRequirementId();
            if (requirementId != null) {
                try {
                    Map<String, Object> inputMap = UtilMisc.<String, Object>toMap("requirementId", requirementId, "statusId", "REQ_ORDERED");
                    inputMap.put("userLogin", userLogin);
                    // TODO: check service result for an error return
                    dispatcher.runSync("updateRequirement", inputMap);
                } catch (Exception e) {
                    String service = e.getMessage();
                    Map<String, String> messageMap = UtilMisc.toMap("service", service);
                    String errMsg = UtilProperties.getMessage(resource_error, "checkhelper.could_not_create_order_invoking_service", messageMap, (cart != null ? cart.getLocale() : Locale.getDefault()));
                    Debug.logError(e, errMsg, module);
                    return ServiceUtil.returnError(errMsg);
                }
            }
        }
        // ----------

        // set the orderId for use by chained events
        Map<String, Object> result = ServiceUtil.returnSuccess();
        result.put("orderId", orderId);
        result.put("orderAdditionalEmails", this.cart.getOrderAdditionalEmails());

        // save the emails to the order
        List<GenericValue> toBeStored = new LinkedList<GenericValue>();

        GenericValue party = null;
        try {
            party = this.delegator.findByPrimaryKey("Party", UtilMisc.toMap("partyId", partyId));
        } catch (GenericEntityException e) {
            Debug.logWarning(e, UtilProperties.getMessage(resource_error,"OrderProblemsGettingPartyRecord", cart.getLocale()), module);
        }

        // create order contact mechs for the email address(s)
        if (party != null) {
            Iterator<GenericValue> emailIter = UtilMisc.toIterator(ContactHelper.getContactMechByType(party, "EMAIL_ADDRESS", false));
            while (emailIter != null && emailIter.hasNext()) {
                GenericValue email = emailIter.next();
                GenericValue orderContactMech = this.delegator.makeValue("OrderContactMech",
                        UtilMisc.toMap("orderId", orderId, "contactMechId", email.getString("contactMechId"), "contactMechPurposeTypeId", "ORDER_EMAIL"));
                toBeStored.add(orderContactMech);
                if (UtilValidate.isEmpty(ContactHelper.getContactMechByPurpose(party, "ORDER_EMAIL", false))) {
                    GenericValue partyContactMechPurpose = this.delegator.makeValue("PartyContactMechPurpose",
                            UtilMisc.toMap("partyId", party.getString("partyId"), "contactMechId", email.getString("contactMechId"), "contactMechPurposeTypeId", "ORDER_EMAIL", "fromDate", UtilDateTime.nowTimestamp()));
                    toBeStored.add(partyContactMechPurpose);
                }
            }
        }

        // create dummy contact mechs and order contact mechs for the additional emails
        String additionalEmails = this.cart.getOrderAdditionalEmails();
        List<String> emailList = StringUtil.split(additionalEmails, ",");
        if (emailList == null) emailList = new ArrayList<String>();
        for(String email : emailList) {
            String contactMechId = this.delegator.getNextSeqId("ContactMech");
            GenericValue contactMech = this.delegator.makeValue("ContactMech",
                    UtilMisc.toMap("contactMechId", contactMechId, "contactMechTypeId", "EMAIL_ADDRESS", "infoString", email));

            GenericValue orderContactMech = this.delegator.makeValue("OrderContactMech",
                    UtilMisc.toMap("orderId", orderId, "contactMechId", contactMechId, "contactMechPurposeTypeId", "ORDER_EMAIL"));
            toBeStored.add(contactMech);

            toBeStored.add(orderContactMech);
        }

        if (toBeStored.size() > 0) {
            try {
                if (Debug.verboseOn()) Debug.logVerbose("To Be Stored: " + toBeStored, module);
                this.delegator.storeAll(toBeStored);
            } catch (GenericEntityException e) {
                // not a fatal error; so just print a message
                Debug.logWarning(e, UtilProperties.getMessage(resource_error,"OrderProblemsStoringOrderEmailContactInformation", cart.getLocale()), module);
            }
        }

        return result;
    }

}
