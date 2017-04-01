import org.ofbiz.order.shoppingcart.ShoppingCartEvents

//用户登录默认已经登录

shoppingCart = ShoppingCartEvents.getCartObject(request);
shoppingCart.setUserLogin(userLogin, dispatcher);

result = com.yuaoq.yabiz.mobile.order.shoppingcart.CheckOutEvents.cartNotEmpty(request, response);
return result;