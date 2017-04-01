import javolution.util.FastList
import org.ofbiz.order.shoppingcart.ShoppingCartEvents
import org.ofbiz.order.shoppingcart.shipping.ShippingEstimateWrapper
import org.ofbiz.service.ModelService

cart = ShoppingCartEvents.getCartObject(request);
resultData = [:];
cartShipments = FastList.newInstance();
if (cart) {
    shippingEstWpr = new ShippingEstimateWrapper(dispatcher, cart, 0);
    carrierShipmentMethodList = shippingEstWpr.getShippingMethods();
    carrierShipmentMethodList.each { carrierShipmentMethod ->

        cartShipment = [:];
        cartShipment.put('estimate', shippingEstWpr.getShippingEstimate(carrierShipmentMethod));
        cartShipment.put('shipmentMethodTypeId', carrierShipmentMethod.shipmentMethodTypeId);
        cartShipment.put('partyId', carrierShipmentMethod.partyId);
        cartShipment.put('description', carrierShipmentMethod.description);

        if (carrierShipmentMethod.partyId == '_NA_') {
            cartShipment.put('estimate', '0');
            cartShipment.put('shipmentMethodTypeId', carrierShipmentMethod.shipmentMethodTypeId);
            cartShipment.put('description', carrierShipmentMethod.description);
        }

        cartShipments.add(cartShipment)
    }

    resultData.put("carrierShipmentMethodList", cartShipments);
    request.setAttribute(ModelService.RESPONSE_MESSAGE, ModelService.RESPOND_SUCCESS);
} else {
    request.setAttribute(ModelService.RESPONSE_MESSAGE, ModelService.RESPOND_FAIL);
}

request.setAttribute("resultData", resultData);
return "success"