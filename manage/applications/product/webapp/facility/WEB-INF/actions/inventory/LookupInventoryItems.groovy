
orderId = parameters.orderId
partyId = parameters.partyId
productId = parameters.productId

if (orderId && productId) {
    shipmentReceiptAndItems = delegator.findByAnd("ShipmentReceiptAndItem", [orderId : orderId, productId : productId])
    context.inventoryItemsForPo = shipmentReceiptAndItems
    context.orderId = orderId
}

if (partyId && productId) {
    orderRoles = delegator.findByAnd("OrderRole", [partyId : partyId, roleTypeId : "BILL_FROM_VENDOR"])
    inventoryItemsForSupplier = []
    orderRoles.each { orderRole ->
        shipmentReceiptAndItems = delegator.findByAnd("ShipmentReceiptAndItem", [productId : productId, orderId : orderRole.orderId])
        inventoryItemsForSupplier.addAll(shipmentReceiptAndItems)
    }
    context.inventoryItemsForSupplier = inventoryItemsForSupplier
    context.partyId = partyId
}

if (productId) {
    inventoryItems = delegator.findByAnd("InventoryItem", [productId : productId])
    context.inventoryItemsForProduct = inventoryItems
    context.productId = productId
    product = delegator.findByPrimaryKey("Product", [productId : productId])
    context.internalName = product.internalName
}
