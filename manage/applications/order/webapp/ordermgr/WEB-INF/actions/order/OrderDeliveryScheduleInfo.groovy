orderId = request.getParameter("orderId")
orderTypeId = null
orderHeader = delegator.findByPrimaryKey("OrderHeader", [orderId : orderId])
if (orderHeader) {
    orderTypeId = orderHeader.orderTypeId
}

//Determine whether a schedule has already been defined for this PO
schedule = delegator.findByPrimaryKey("OrderDeliverySchedule", [orderId : orderId, orderItemSeqId : "_NA_"])

// Determine whether the current user can VIEW the order
checkMap = [orderId : orderId, userLogin : session.getAttribute("userLogin"), checkAction : "VIEW"]
checkResult = dispatcher.runSync("checkSupplierRelatedOrderPermission", checkMap)
hasSupplierRelatedPermissionStr = checkResult.hasSupplierRelatedPermission

// Determine what the reuslt is, no result is FALSE
hasSupplierRelatedPermission = "true".equals(hasSupplierRelatedPermissionStr)

// Initialize the PO Delivery Schedule form
/*updatePODeliveryInfoWrapper = new HtmlFormWrapper("component://order/widget/ordermgr/OrderDeliveryScheduleForms.xml", "UpdateDeliveryScheduleInformation", request, response);
updatePODeliveryInfoWrapper.putInContext("orderId", orderId);
updatePODeliveryInfoWrapper.putInContext("orderItemSeqId", "_NA_");
updatePODeliveryInfoWrapper.putInContext("schedule", schedule);
updatePODeliveryInfoWrapper.putInContext("hasSupplierRelatedPermission", hasSupplierRelatedPermission);*/

context.orderId = orderId
context.orderTypeId = orderTypeId
context.orderHeader = orderHeader
context.hasPermission = hasSupplierRelatedPermission
context.orderItemSeqId = "_NA_"
context.schedule = schedule
context.hasSupplierRelatedPermission = hasSupplierRelatedPermission
