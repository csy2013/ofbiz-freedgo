package com.yuaoq.yabiz.eshop.shoppingcart;


import org.ofbiz.base.util.UtilHttp;
import org.ofbiz.base.util.UtilProperties;
import org.ofbiz.base.util.UtilValidate;
import org.ofbiz.entity.Delegator;
import org.ofbiz.entity.GenericValue;
import org.ofbiz.marketing.tracking.TrackingCodeEvents;
import org.ofbiz.order.shoppingcart.ShoppingCartEvents;
import org.ofbiz.product.catalog.CatalogWorker;
import org.ofbiz.service.LocalDispatcher;
import org.ofbiz.service.ModelService;
import org.ofbiz.service.ServiceUtil;
import org.ofbiz.webapp.stats.VisitHandler;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.sql.Timestamp;
import java.util.List;
import java.util.Map;

/**
 * Created by tusm on 15/5/30.
 */
public class CheckOutEvents {

    public static final String module = CheckOutEvents.class.getName();
    public static final String resource = "OrderUiLabels";
    public static final String resource_error = "OrderErrorUiLabels";


    public static String cartNotEmpty(HttpServletRequest request, HttpServletResponse response) {
        org.ofbiz.order.shoppingcart.ShoppingCart cart = ShoppingCartEvents.getCartObject(request);

        if ((cart != null) && UtilValidate.isNotEmpty(cart.items())) {
            return "success";
        } else {
            String errMsg = UtilProperties.getMessage(resource_error, "checkevents.cart_empty",
                    (cart != null ? cart.getLocale() : UtilHttp.getLocale(request)));
            request.setAttribute("_ERROR_MESSAGE_", errMsg);
            return "error";
        }
    }


    /**
     * order Add By Changsy
     *
     * @param request
     * @param response
     * @return
     */
    public static String checkCartNotEmpty(HttpServletRequest request, HttpServletResponse response) {
        ShoppingCart cart = (ShoppingCart) ShoppingCartEvents.getCartObject(request);
        Timestamp time = null;
        if ((cart != null) && UtilValidate.isNotEmpty(cart.items())) {
            /*if(request.getParameter("may_split").equals("true")){
                for (int i = 0; i < cart.items().size(); i++) {
                    if (request.getParameter("date_" + cart.items().get(i).getProductId()) != null) {
                        String date = request.getParameter("date_" + cart.items().get(i).getProductId());
                        date  += " 00:00:00";
                        time = Timestamp.valueOf(date);
                        cart.items().get(i).setDesiredDeliveryDate(time);
                    }
                }
            }else{
                if (request.getParameter("date_" + cart.items().get(0).getProductId()) != null) {
                    for (int i = 0; i < cart.items().size(); i++) {
                        String date = request.getParameter("date_" + cart.items().get(0).getProductId());
                        if(!date.equals("")){
                            date  += " 00:00:00";
                            time = Timestamp.valueOf(date);
                            cart.items().get(i).setDesiredDeliveryDate(time);
                        }

                    }
                }
            }*/

            return "success";
        } else {
            String errMsg = UtilProperties.getMessage(resource_error, "checkevents.cart_empty",
                    (cart != null ? cart.getLocale() : UtilHttp.getLocale(request)));
            request.setAttribute("_ERROR_MESSAGE_", errMsg);
            return "error";
        }
    }

    // Create order event - uses createOrder service for processing
    public static String createOrder(HttpServletRequest request, HttpServletResponse response) {
        HttpSession session = request.getSession();
        org.ofbiz.order.shoppingcart.ShoppingCart cart = ShoppingCartEvents.getCartObject(request);
        LocalDispatcher dispatcher = (LocalDispatcher) request.getAttribute("dispatcher");
        Delegator delegator = (Delegator) request.getAttribute("delegator");
        GenericValue userLogin = (GenericValue) session.getAttribute("userLogin");
        CheckOutHelper checkOutHelper = new CheckOutHelper(dispatcher, delegator, cart);

        Map<String, Object> callResult;

        if (UtilValidate.isEmpty(userLogin)) {
            userLogin = cart.getUserLogin();
            session.setAttribute("userLogin", userLogin);
        }
        // remove this whenever creating an order so quick reorder cache will refresh/recalc
        session.removeAttribute("_QUICK_REORDER_PRODUCTS_");

        boolean areOrderItemsExploded = org.ofbiz.order.shoppingcart.CheckOutEvents.explodeOrderItems(delegator, cart);

        // get the TrackingCodeOrder List
        List<GenericValue> trackingCodeOrders = TrackingCodeEvents.makeTrackingCodeOrders(request);
        String distributorId = (String) session.getAttribute("_DISTRIBUTOR_ID_");
        String affiliateId = (String) session.getAttribute("_AFFILIATE_ID_");
        String visitId = VisitHandler.getVisitId(session);
        String webSiteId = CatalogWorker.getWebSiteId(request);

        callResult = checkOutHelper.createOrder(userLogin, distributorId, affiliateId, trackingCodeOrders,
                areOrderItemsExploded, visitId, webSiteId);
        if (callResult != null) {
            ServiceUtil.getMessages(request, callResult, null);
            if (ServiceUtil.isError(callResult)) {
                // messages already setup with the getMessages call, just return the error response code
                return "error";
            }
            if (callResult.get(ModelService.RESPONSE_MESSAGE).equals(ModelService.RESPOND_SUCCESS)) {
                // set the orderId for use by chained events
                String orderId = cart.getOrderId();
                request.setAttribute("orderId", orderId);
                request.setAttribute("orderAdditionalEmails", cart.getOrderAdditionalEmails());
            }
        }

        String issuerId = request.getParameter("issuerId");
        if (UtilValidate.isNotEmpty(issuerId)) {
            request.setAttribute("issuerId", issuerId);
        }

        return cart.getOrderType().toLowerCase();
    }


}
