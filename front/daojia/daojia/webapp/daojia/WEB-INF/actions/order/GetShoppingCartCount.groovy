import org.ofbiz.order.shoppingcart.ShoppingCartEvents
import org.ofbiz.service.ModelService

shoppingCart = ShoppingCartEvents.getCartObject(request);
size = shoppingCart?.size() ?: 0;
println "size = $size"

resultData = [:]
resultData.put("cartCount",size);
request.setAttribute(ModelService.RESPONSE_MESSAGE, ModelService.RESPOND_SUCCESS);
request.setAttribute("resultData",resultData);
return "success"
