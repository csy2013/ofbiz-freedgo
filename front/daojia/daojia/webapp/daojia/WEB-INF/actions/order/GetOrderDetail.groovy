import org.ofbiz.accounting.payment.PaymentWorker
import org.ofbiz.entity.condition.EntityCondition
import org.ofbiz.entity.condition.EntityOperator
import org.ofbiz.entity.util.EntityFindOptions
import org.ofbiz.entity.util.EntityUtil
import org.ofbiz.order.order.OrderReadHelper
import org.ofbiz.product.catalog.CatalogWorker

orderId = request.getParameter('orderId');
orderHeader = delegator.findOne("OrderHeader", [orderId: orderId], false);

if (orderHeader) {
    productStore = orderHeader.getRelatedOneCache("ProductStore");

    orderReadHelper = new OrderReadHelper(orderHeader);
    orderItems = orderReadHelper.getOrderItems();
    orderAdjustments = orderReadHelper.getAdjustments();
    orderHeaderAdjustments = orderReadHelper.getOrderHeaderAdjustments();
    orderSubTotal = orderReadHelper.getOrderItemsSubTotal();
    orderItemShipGroups = orderReadHelper.getOrderItemShipGroups();
    headerAdjustmentsToShow = orderReadHelper.getOrderHeaderAdjustmentsToShow();

    orderShippingTotal = OrderReadHelper.getAllOrderItemsAdjustmentsTotal(orderItems, orderAdjustments, false, false, true);
    orderShippingTotal = orderShippingTotal.add(OrderReadHelper.calcOrderAdjustments(orderHeaderAdjustments, orderSubTotal, false, false, true));

    orderTaxTotal = OrderReadHelper.getAllOrderItemsAdjustmentsTotal(orderItems, orderAdjustments, false, true, false);
    orderTaxTotal = orderTaxTotal.add(OrderReadHelper.calcOrderAdjustments(orderHeaderAdjustments, orderSubTotal, false, true, false));

    placingCustomerOrderRoles = delegator.findByAnd("OrderRole", [orderId: orderId, roleTypeId: roleTypeId]);
    placingCustomerOrderRole = EntityUtil.getFirst(placingCustomerOrderRoles);
    placingCustomerPerson = placingCustomerOrderRole == null ? null : delegator.findByPrimaryKey("Person", [partyId: placingCustomerOrderRole.partyId]);

    billingAccount = orderHeader.getRelatedOne("BillingAccount");

    orderPaymentPreferences = EntityUtil.filterByAnd(orderHeader.getRelated("OrderPaymentPreference"), [EntityCondition.makeCondition("statusId", EntityOperator.NOT_EQUAL, "PAYMENT_CANCELLED")]);
    paymentMethods = [];
    orderPaymentPreferences.each { opp ->
        paymentMethod = opp.getRelatedOne("PaymentMethod");
        if (paymentMethod) {
            paymentMethods.add(paymentMethod);
        } else {
            paymentMethodType = opp.getRelatedOne("PaymentMethodType");
            if (paymentMethodType) {
                context.paymentMethodType = paymentMethodType;
            }
        }
    }

    webSiteId = orderHeader.webSiteId ?: CatalogWorker.getWebSiteId(request);

    payToPartyId = productStore.payToPartyId;
    paymentAddress = PaymentWorker.getPaymentAddress(delegator, payToPartyId);
    if (paymentAddress) context.paymentAddress = paymentAddress;

    // get Shipment tracking info
    osisCond = EntityCondition.makeCondition([orderId: orderId], EntityOperator.AND);
    osisOrder = ["shipmentId", "shipmentRouteSegmentId", "shipmentPackageSeqId"];
    osisFields = ["shipmentId", "shipmentRouteSegmentId", "carrierPartyId", "shipmentMethodTypeId"] as Set;
    osisFields.add("shipmentPackageSeqId");
    osisFields.add("trackingCode");
    osisFields.add("boxNumber");
    osisFindOptions = new EntityFindOptions();
    osisFindOptions.setDistinct(true);
    orderShipmentInfoSummaryList = delegator.findList("OrderShipmentInfoSummary", osisCond, osisFields, osisOrder, osisFindOptions, false);

    customerPoNumberSet = new TreeSet();
    orderItems.each { orderItemPo ->
        correspondingPoId = orderItemPo.correspondingPoId;
        if (correspondingPoId && !"(none)".equals(correspondingPoId)) {
            customerPoNumberSet.add(correspondingPoId);
        }
    }

    // check if there are returnable items
    returned = 0.00;
    totalItems = 0.00;
    orderItems.each { oitem ->
        totalItems += oitem.quantity;
        ritems = oitem.getRelated("ReturnItem");
        ritems.each { ritem ->
            rh = ritem.getRelatedOne("ReturnHeader");
            if (!rh.statusId.equals("RETURN_CANCELLED")) {
                returned += ritem.returnQuantity;
            }
        }
    }

    if (totalItems > returned) {
        context.returnLink = "Y";
    }

    orderMap.put('orderId', orderId);
    orderMap.put('orderItems', orderItems);
    orderMap.put('orderAdjustments', orderAdjustments);
    orderMap.put('orderHeaderAdjustments', orderHeaderAdjustments);
    orderMap.put('orderSubTotal', orderSubTotal);
    orderMap.put('orderItemShipGroups', orderItemShipGroups);
    orderMap.put('headerAdjustmentsToShow', headerAdjustmentsToShow);
    orderMap.put('currencyUomId', orderReadHelper.getCurrency());

    orderMap.put('orderShippingTotal', orderShippingTotal);
    orderMap.put('orderTaxTotal', orderTaxTotal);
    orderMap.put('orderGrandTotal', OrderReadHelper.getOrderGrandTotal(orderItems, orderAdjustments));
    orderMap.put('placingCustomerPerson', placingCustomerPerson);

    orderMap.put('billingAccount', billingAccount);
    orderMap.put('paymentMethods', paymentMethods);

    orderMap.put('productStore', productStore);

    orderMap.put('orderShipmentInfoSummaryList', orderShipmentInfoSummaryList);
    orderMap.put('customerPoNumberSet', customerPoNumberSet);

    orderItemChangeReasons = delegator.findByAnd("Enumeration", [enumTypeId: "ODR_ITM_CH_REASON"], ["sequenceId"]);
    orderMap.put('orderItemChangeReasons', orderItemChangeReasons);

    orders.add(orderMap);
}