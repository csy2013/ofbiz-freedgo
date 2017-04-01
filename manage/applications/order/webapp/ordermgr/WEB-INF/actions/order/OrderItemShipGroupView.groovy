import org.ofbiz.order.order.OrderReadHelper
import org.ofbiz.party.contact.ContactHelper

orderId = parameters.orderId
shipGroupSeqId = parameters.shipGroupSeqId
partyId = parameters.partyId
context.orderId = orderId
if (shipGroupSeqId) {
    shipGroup = delegator.findByPrimaryKey("OrderItemShipGroup", [orderId: orderId, shipGroupSeqId: shipGroupSeqId])
    context.shipGroup = shipGroup
}
println "parameters = $parameters"
if (orderId) {
    orderHeader = delegator.findByPrimaryKey("OrderHeader", [orderId: orderId])
    context.orderHeader = orderHeader
}
println "partyId = $partyId"
if (orderHeader) {
    // list to find all the POSTAL_ADDRESS for the shipment party.
    orderParty = delegator.findByPrimaryKey("Party", [partyId: partyId])
    shippingContactMechList = ContactHelper.getContactMech(orderParty, "SHIPPING_LOCATION", "POSTAL_ADDRESS", false)
    context.shippingContactMechList = shippingContactMechList
    orderReadHelper = new OrderReadHelper(orderHeader)
    productStore = orderReadHelper.getProductStore()
    println "productStore = $productStore"
    context.productStore = productStore
    if (productStore) {
        context.productStoreShipmentMethList = delegator.findByAndCache('ProductStoreShipmentMethView', [productStoreId: productStore.productStoreId], ['sequenceNumber'])
        println "partyId2 = $partyId"
    }
}