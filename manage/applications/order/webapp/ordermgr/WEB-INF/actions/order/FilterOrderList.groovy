import org.ofbiz.order.order.OrderListState
import org.ofbiz.order.order.OrderReadHelper
import org.ofbiz.product.store.ProductStoreWorker

orderHeaderList = context.orderHeaderList
productStore = ProductStoreWorker.getProductStore(request)

filterInventoryProblems = []

if (state.hasFilter("filterInventoryProblems") && orderHeaderList) {
    orderHeaderList.each { orderHeader ->
        orderReadHelper = OrderReadHelper.getHelper(orderHeader)
        backorderQty = orderReadHelper.getOrderBackorderQuantity()
        if (backorderQty.compareTo(BigDecimal.ZERO) > 0) {
            filterInventoryProblems.add(orderHeader.orderId)
        }
    }
}

filterPOsOpenPastTheirETA = []
filterPOsWithRejectedItems = []
filterPartiallyReceivedPOs = []

state = OrderListState.getInstance(request)
//print("### " + state.toString());

if ((state.hasFilter("filterPartiallyReceivedPOs") ||
        state.hasFilter("filterPOsOpenPastTheirETA") ||
        state.hasFilter("filterPOsWithRejectedItems")) &&
        orderHeaderList) {
    orderHeaderList.each { orderHeader ->
        orderReadHelper = OrderReadHelper.getHelper(orderHeader)
        if ("PURCHASE_ORDER".equals(orderHeader.orderTypeId)) {
            if (orderReadHelper.getRejectedOrderItems() &&
                    state.hasFilter("filterPOsWithRejectedItems")) {
                filterPOsWithRejectedItems.add(orderHeader.get("orderId"))
            } else if (orderReadHelper.getPastEtaOrderItems(orderHeader.get("orderId")) &&
                    state.hasFilter("filterPOsOpenPastTheirETA")) {
                filterPOsOpenPastTheirETA.add(orderHeader.orderId)
            } else if (orderReadHelper.getPartiallyReceivedItems() &&
                    state.hasFilter("filterPartiallyReceivedPOs")) {
                filterPartiallyReceivedPOs.add(orderHeader.orderId)
            }
        }
    }
}

filterAuthProblems = []

if (state.hasFilter("filterAuthProblems") && orderHeaderList) {
    orderHeaderList.each { orderHeader ->
        orderReadHelper = OrderReadHelper.getHelper(orderHeader)
        paymentPrefList = orderReadHelper.getPaymentPreferences()
        paymentPrefList.each { paymentPref ->
            if ("PAYMENT_NOT_AUTH".equals(paymentPref.statusId)) {
                filterAuthProblems.add(orderHeader.orderId)
            }
        }
    }
}
context.filterInventoryProblems = filterInventoryProblems
context.filterPOsWithRejectedItems = filterPOsWithRejectedItems
context.filterPOsOpenPastTheirETA = filterPOsOpenPastTheirETA
context.filterPartiallyReceivedPOs = filterPartiallyReceivedPOs
context.filterAuthProblems = filterAuthProblems
