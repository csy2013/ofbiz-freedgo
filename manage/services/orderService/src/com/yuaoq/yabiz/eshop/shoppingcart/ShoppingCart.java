package com.yuaoq.yabiz.eshop.shoppingcart;

import org.ofbiz.base.util.UtilMisc;
import org.ofbiz.base.util.UtilProperties;
import org.ofbiz.order.shoppingcart.product.ProductPromoWorker;
import org.ofbiz.service.LocalDispatcher;

import javax.servlet.http.HttpServletRequest;
import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

/**
 * Created by tusm on 15/5/30.
 */
public class ShoppingCart extends org.ofbiz.order.shoppingcart.WebShoppingCart {

    public ShoppingCart(HttpServletRequest request, Locale locale, String currencyUom) {
        super(request, locale, currencyUom);
    }



    /**
     * 检查优惠券是否可用 Add By Changsy
     * @param productPromoCodeId
     * @param dispatcher
     * @return
     */
    public String checkProductPromoCode(String productPromoCodeId, LocalDispatcher dispatcher) {

        BigDecimal totalprice=this.getDisplayGrandTotal();
        if (this.getProductPromoCodes().contains(productPromoCodeId)) {
            return UtilProperties.getMessage(resource_error, "productpromoworker.promotion_code_already_been_entered",
                    UtilMisc.toMap("productPromoCodeId", productPromoCodeId), this.locale);
        }
        if (!getDoPromotions()) {
            this.getProductPromoCodes().add(productPromoCodeId);
            return null;
        }
        // if the promo code requires it make sure the code is valid
        String checkResult = ProductPromoWorker.checkCanUsePromoCode(productPromoCodeId, getPartyId(), getDelegator(),
                this, this.locale);
        if (checkResult == null) {
            this.getProductPromoCodes().add(productPromoCodeId);
            // new promo code, re-evaluate promos
            ProductPromoWorker.doPromotions(this, dispatcher);
            BigDecimal totalprice1=this.getDisplayGrandTotal();
            if(!totalprice.equals(totalprice1)){
                this.getProductPromoCodes().clear();
                ProductPromoWorker.doPromotions(this, dispatcher);
                return "success";
            }
            return null;
        } else {
            return checkResult;
        }
    }

    /**
     * Returns a Map of cart values to pass to the storeOrder service
     */
    public Map<String, Object> makeCartMap(LocalDispatcher dispatcher, boolean explodeItems) {
        Map<String, Object> result = new HashMap<String, Object>();

        result.put("orderTypeId", getOrderType());
        result.put("orderName", getOrderName());
        result.put("externalId", getExternalId());
        result.put("orderDate", getOrderDate());
        result.put("internalCode", getInternalCode());
        result.put("salesChannelEnumId", getChannelType());
        result.put("orderItemGroups", makeOrderItemGroups());
        result.put("orderItems", this.makeOrderItems(explodeItems, Boolean.TRUE, dispatcher));
        result.put("workEfforts", makeWorkEfforts());
        result.put("orderAdjustments", makeAllAdjustments());
        result.put("orderTerms", getOrderTerms());
        result.put("orderItemPriceInfos", makeAllOrderItemPriceInfos());
        result.put("orderProductPromoUses", makeProductPromoUses());
        result.put("orderProductPromoCodes", getProductPromoCodesEntered());

        result.put("orderAttributes", this.makeAllOrderAttributes());
        result.put("orderItemAttributes", this.makeAllOrderItemAttributes());
        result.put("orderContactMechs", makeAllOrderContactMechs());
        result.put("orderItemContactMechs", makeAllOrderItemContactMechs());
        result.put("orderPaymentInfo", makeAllOrderPaymentInfos(dispatcher));
        result.put("orderItemShipGroupInfo", makeAllShipGroupInfos());
        result.put("orderItemSurveyResponses", makeAllOrderItemSurveyResponses());
        result.put("orderAdditionalPartyRoleMap", getAdditionalPartyRoleMap());
        result.put("orderItemAssociations", makeAllOrderItemAssociations());
        result.put("orderInternalNotes", getInternalOrderNotes());
        result.put("orderNotes", getOrderNotes());

        result.put("firstAttemptOrderId", getFirstAttemptOrderId());
        result.put("currencyUom", getCurrency());
        result.put("billingAccountId", getBillingAccountId());

        result.put("partyId", getPartyId());
        result.put("productStoreId", getProductStoreId());
        result.put("transactionId", getTransactionId());
        result.put("originFacilityId", getFacilityId());
        result.put("terminalId", getTerminalId());
        result.put("workEffortId", getWorkEffortId());
        result.put("autoOrderShoppingListId", getAutoOrderShoppingListId());

        result.put("billToCustomerPartyId", getBillToCustomerPartyId());
        result.put("billFromVendorPartyId", getBillFromVendorPartyId());

        if (isSalesOrder()) {
            result.put("placingCustomerPartyId", getPlacingCustomerPartyId());
            result.put("shipToCustomerPartyId", getShipToCustomerPartyId());
            result.put("endUserCustomerPartyId", getEndUserCustomerPartyId());
        }

        if (isPurchaseOrder()) {
            result.put("shipFromVendorPartyId", getShipFromVendorPartyId());
            result.put("supplierAgentPartyId", getSupplierAgentPartyId());
        }

        return result;
    }
}
