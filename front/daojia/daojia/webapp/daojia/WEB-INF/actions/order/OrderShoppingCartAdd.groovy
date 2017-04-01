
import org.ofbiz.service.ModelService
import com.yuaoq.yabiz.mobile.order.shoppingcart.ShoppingCartEvents;

String result = ShoppingCartEvents.addToCart(request,response);
println "result = $result"
if(result.equals("error")){
    request.setAttribute(ModelService.RESPONSE_MESSAGE, ModelService.RESPOND_ERROR);
    return "error"

}else{
    request.setAttribute(ModelService.RESPONSE_MESSAGE, ModelService.RESPOND_SUCCESS);
    return "success"
}