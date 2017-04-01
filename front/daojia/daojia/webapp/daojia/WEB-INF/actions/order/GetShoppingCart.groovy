import javolution.util.FastList
import javolution.util.FastMap
import org.ofbiz.order.order.OrderReadHelper
import org.ofbiz.order.shoppingcart.ShoppingCartEvents
import org.ofbiz.product.product.ProductContentWrapper
import org.ofbiz.service.ModelService

shoppingCart = ShoppingCartEvents.getCartObject(request);
//购物车信息
Map cartInfo = FastMap.newInstance();
//cart 总计
displayGrandTotal = shoppingCart.getDisplayGrandTotal();
cartInfo.put('displayGrandTotal', displayGrandTotal);

//如果有价格调整的情况
displaySubTotal = shoppingCart.getDisplaySubTotal();
//购物车小计
cartInfo.put("displaySubTotal", displaySubTotal);

if (shoppingCart.getDisplayTaxIncluded() > 0.0) {
    displayTaxInclude = shoppingCart.getDisplayTaxIncluded();
    cartInfo.put("displayTaxInclude", displayTaxInclude);
}
orderAdjustments = shoppingCart.makeAllAdjustments();
orderItems = shoppingCart.makeOrderItems();
workEfforts = shoppingCart.makeWorkEfforts();

//除促销，销售税、配送费之外总计 WEB-INF/actions/order/CheckoutReview.groovy
cartInfo.orderSubTotal = OrderReadHelper.getOrderItemsSubTotal(orderItems, orderAdjustments, workEfforts);
cartInfo.orderShippingTotal = shoppingCart.getTotalShipping();
cartInfo.orderTaxTotal = shoppingCart.getTotalSalesTax();


if (shoppingCart.getAdjustments().size() > 0) {

    adjustments = shoppingCart.getAdjustments();

    adjustmentTotalAmount = 0;
    List adjustmentList = FastList.newInstance();
    adjustments.each { adjustment ->
        Map adjustMap = FastMap.newInstance();
        adjustmentAmount = org.ofbiz.order.order.OrderReadHelper.calcOrderAdjustment(adjustment, shoppingCart.getSubTotal());
        adjustmentDescription = adjustment.description;
        productPromoId = adjustment.productPromoId;
        adjustMap.put("adjustmentAmount", adjustmentAmount);
        adjustmentTotalAmount += adjustmentAmount;
        adjustMap.put("adjustmentDescription", adjustmentDescription);
        adjustMap.put("productPromoId", productPromoId);
        adjustmentList.add(adjustMap);
    }
    cartInfo.put("adjustments", adjustmentList);
    cartInfo.put("adjustmentTotalAmount", adjustmentTotalAmount);

}


shoppingCartItems = shoppingCart.items();
List cartList = FastList.newInstance();
toalQuantity = 0;
if (shoppingCartItems) {
    shoppingCartItems.each { cartLine ->
        cartObj = [:];
        cartObj.put("quantity", cartLine.getQuantity());
        toalQuantity += cartLine.getQuantity();
        cartObj.put("name", cartLine.getName());
        cartObj.put("description", cartLine.getItemTypeDescription());
        cartObj.put("displayPrice", cartLine.getDisplayPrice());
        miniProduct = cartLine.getProduct()
        ProductContentWrapper miniProductContentWrapper = new ProductContentWrapper(miniProduct, request);
        String mediumImageUrl = miniProductContentWrapper.get("MEDIUM_IMAGE_URL").toString();
        cartObj.put('mediumImageUrl', mediumImageUrl)
        //调整
        cartObj.put("otherAdjustments", cartLine.getOtherAdjustments());
        //明细合计
        cartObj.put("itemSubTotal", cartLine.getDisplayItemSubTotal());
        productId = cartLine.getProductId();
        cartObj.put('productTypeId', miniProduct.productTypeId);

        //variant product
        cartObj.put("productId", productId);
        if (productId) {
            parentProductId = cartLine.getParentProductId();

            cartObj.put("parentProductId", parentProductId);
        }
        //variant product feature
//        configWrapper = cartLine.getConfigWrapper();
//        if (configWrapper) {
//            features = cartLine.getConfigWrapper().getSelectedOptions();
//            cartObj.put("features", features);
//        }
        cartList.add(cartObj)

    }

    }

cartInfo.put("totalQuantity", toalQuantity);
//println "shoppingCartItems = $shoppingCartItems"
resultData = [:]
resultData.put("shoppingCartItems", cartList);
resultData.put("shoppingCart", cartInfo);
request.setAttribute(ModelService.RESPONSE_MESSAGE, ModelService.RESPOND_SUCCESS);
request.setAttribute("resultData", resultData);
return "success"
