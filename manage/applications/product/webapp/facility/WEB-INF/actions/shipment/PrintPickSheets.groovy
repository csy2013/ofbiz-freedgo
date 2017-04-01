

import org.ofbiz.entity.util.EntityUtil
import org.ofbiz.order.order.OrderReadHelper

toPrintOrders = []
maxNumberOfOrders = parameters.maxNumberOfOrdersToPrint
int maxNumberOfOrders = maxNumberOfOrders.toInteger()
int printListCounter = 0
printGroupName = parameters.printGroupName
if (printGroupName != null) {
    pickMoveInfoList.each { pickMoveInfo ->
        groupName = pickMoveInfo.groupName
        if (groupName == printGroupName) {
            toPrintOrders.add(pickMoveInfo.orderReadyToPickInfoList)
        }
    }
}
else {
    pickMoveInfoList.each { pickMoveInfo ->
        toPrintOrders.add(pickMoveInfo.orderReadyToPickInfoList)
    }
}
if (toPrintOrders) {
    orderList = []
    orderInfoList = []
    itemInfoList = []
    orderHeaderList = []
    orderChargeList =[]
    toPrintOrders.each { toPrintOrder ->
        if(toPrintOrder) {
            orderHeaders = toPrintOrder.orderHeader
            orderItemShipGroups = toPrintOrder.orderItemShipGroup
            orderItemShipGrpInvResList = toPrintOrder.orderItemShipGrpInvResList
            orderItemShipGrpInvResInfoList = toPrintOrder.orderItemShipGrpInvResInfoList
            orderHeaders.each { orderHeader ->
                printListCounter = ++printListCounter
                if (printListCounter <= maxNumberOfOrders) {
                    orderMap = [:]
                    orderId = orderHeader.orderId
                    orderMap.orderId = orderId
                    orderMap.orderDate = orderHeader.orderDate
                    billingOrderContactMechs = []
                    billingOrderContactMechs = delegator.findByAnd("OrderContactMech", [orderId : orderId, contactMechPurposeTypeId : "BILLING_LOCATION"])
                    if (billingOrderContactMechs.size() > 0) {
                        billingContactMechId = EntityUtil.getFirst(billingOrderContactMechs).contactMechId
                        billingAddress = delegator.findOne("PostalAddress", [contactMechId : billingContactMechId], false)
                    }
                    shippingContactMechId = EntityUtil.getFirst(delegator.findByAnd("OrderContactMech", [orderId : orderId, contactMechPurposeTypeId : "SHIPPING_LOCATION"])).contactMechId
                    shippingAddress = delegator.findOne("PostalAddress", [contactMechId : shippingContactMechId], false)
                    orderItemShipGroups.each { orderItemShipGroup ->
                        if (orderItemShipGroup.orderId == orderId) {
                            orderMap.shipmentMethodType = EntityUtil.getFirst(orderItemShipGroup.getRelated("ShipmentMethodType")).description
                            orderMap.carrierPartyId = orderItemShipGroup.carrierPartyId
                            orderMap.shipGroupSeqId = orderItemShipGroup.shipGroupSeqId
                            orderMap.carrierPartyId = orderItemShipGroup.carrierPartyId
                            orderMap.isGift = orderItemShipGroup.isGift
                            orderMap.giftMessage = orderItemShipGroup.giftMessage
                        }
                        orderMap.shippingAddress = shippingAddress
                        if (billingOrderContactMechs.size() > 0) {
                            orderMap.billingAddress = billingAddress
                        }
                        orderInfoMap = [:]
                        orderInfoMap.(orderHeader.orderId) = orderMap
                    }
                    addInMap = "true"
                    orderItemMap = [:]
                    orderItemShipGrpInvResInfoList.each { orderItemShipGrpInvResInfos ->
                        orderItemShipGrpInvResInfos.each { orderItemShipGrpInvResInfo ->
                            if (orderItemShipGrpInvResInfo.orderItemShipGrpInvRes.orderId == orderId && addInMap == "true") {
                                orderItemMap.(orderHeader.orderId) = orderItemShipGrpInvResInfos
                                addInMap = "false"
                            }
                        }
                    }
                    orderChargeMap = [:]
                    orderReadHelper = new OrderReadHelper(orderHeader)
                    orderItems = orderReadHelper.getOrderItems()
                    orderAdjustments = orderReadHelper.getAdjustments()
                    orderHeaderAdjustments = orderReadHelper.getOrderHeaderAdjustments()
                    context.orderHeaderAdjustments = orderHeaderAdjustments
                    orderSubTotal = orderReadHelper.getOrderItemsSubTotal()
                    context.orderSubTotal = orderSubTotal
                    otherAdjAmount = orderReadHelper.calcOrderAdjustments(orderHeaderAdjustments, orderSubTotal, true, false, false)
                    shippingAmount = orderReadHelper.getAllOrderItemsAdjustmentsTotal(orderItems, orderAdjustments, false, false, true)
                    shippingAmount = shippingAmount.add(orderReadHelper.calcOrderAdjustments(orderHeaderAdjustments, orderSubTotal, false, false, true))
                    taxAmount = orderReadHelper.getAllOrderItemsAdjustmentsTotal(orderItems, orderAdjustments, false, true, false)
                    taxAmount = taxAmount.add(orderReadHelper.calcOrderAdjustments(orderHeaderAdjustments, orderSubTotal, false, true, false))
                    grandTotal = orderReadHelper.getOrderGrandTotal(orderItems, orderAdjustments)
                    orderChargeMap.orderSubTotal = orderSubTotal
                    orderChargeMap.taxAmount = taxAmount
                    orderChargeMap.shippingAmount = shippingAmount
                    orderChargeMap.otherAdjAmount = otherAdjAmount
                    orderChargeMap.grandTotal = grandTotal
                    orderChargeMap.totalItem = orderItems.size()
                    orderCharges = [:]
                    orderCharges.(orderHeader.orderId) = orderChargeMap
                    orderChargeList.add(orderCharges)
                    itemInfoList.add(orderItemMap)
                    orderInfoList.add(orderInfoMap)
                    orderList.add(orderHeader)
                    context.orderHeaderList = orderList
                    context.orderInfoList = orderInfoList
                    context.itemInfoList = itemInfoList
                    context.orderChargeList = orderChargeList
                    context.currencyUomId = orderReadHelper.getCurrency()
                }
            }
        }
    }
}
