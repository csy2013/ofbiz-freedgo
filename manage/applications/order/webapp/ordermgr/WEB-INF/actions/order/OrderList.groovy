import org.ofbiz.entity.condition.EntityCondition
import org.ofbiz.entity.condition.EntityOperator
import org.ofbiz.order.order.OrderListState

partyId = request.getParameter("partyId")
facilityId = request.getParameter("facilityId")

state = OrderListState.getInstance(request)
state.update(request)
context.state = state

// check permission for each order type
hasPermission = false
if (security.hasEntityPermission("ORDERMGR", "_VIEW", session)) {
    if (state.hasType("view_SALES_ORDER") || (!(state.hasType("view_SALES_ORDER")) && !(state.hasType("view_PURCHASE_ORDER")))) {
        hasPermission = true
        salesOrdersCondition = EntityCondition.makeCondition("orderTypeId", EntityOperator.EQUALS, "SALES_ORDER")
    }
}
if (security.hasEntityPermission("ORDERMGR", "_PURCHASE_VIEW", session)) {
    if (state.hasType("view_PURCHASE_ORDER") || (!(state.hasType("view_SALES_ORDER")) && !(state.hasType("view_PURCHASE_ORDER")))) {
        hasPermission = true
        purchaseOrdersCondition = EntityCondition.makeCondition("orderTypeId", EntityOperator.EQUALS, "PURCHASE_ORDER")
    }
}
context.hasPermission = hasPermission

orderHeaderList = state.getOrders(facilityId, filterDate, delegator)
context.orderHeaderList = orderHeaderList

// a list of order type descriptions
ordertypes = delegator.findList("OrderType", null, null, null, null, true)
ordertypes.each { type ->
    context["descr_" + type.orderTypeId] = type.get("description",locale)
}

context.filterDate = filterDate
