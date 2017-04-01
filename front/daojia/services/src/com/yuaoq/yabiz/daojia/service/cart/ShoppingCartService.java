package com.yuaoq.yabiz.daojia.service.cart;

import com.google.gson.Gson;
import com.yuaoq.yabiz.daojia.model.json.cart.*;
import com.yuaoq.yabiz.daojia.model.json.cart.cartQuery.CartQuery;
import com.yuaoq.yabiz.daojia.model.json.cart.cartQuery.CartQueryResult;
import com.yuaoq.yabiz.daojia.model.json.cart.marketSettleGetCurrentAccount.*;
import com.yuaoq.yabiz.eshop.shoppingcart.ShoppingCartEvents;
import javolution.util.FastList;
import javolution.util.FastMap;
import org.ofbiz.base.util.*;
import org.ofbiz.entity.Delegator;
import org.ofbiz.entity.GenericEntityException;
import org.ofbiz.entity.GenericValue;
import org.ofbiz.order.order.OrderReadHelper;
import org.ofbiz.order.shoppingcart.ShoppingCart;
import org.ofbiz.order.shoppingcart.ShoppingCartItem;
import org.ofbiz.party.contact.ContactMechWorker;
import org.ofbiz.product.product.ProductContentWrapper;
import org.ofbiz.service.GenericServiceException;
import org.ofbiz.service.LocalDispatcher;
import org.ofbiz.service.ModelService;
import org.ofbiz.service.ServiceUtil;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.IOException;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.Map;

/**
 * Created by changsy on 16/9/12.
 */
public class ShoppingCartService {
    /**
     * 查询购物车
     *
     * @param request
     * @param response
     * @return
     */
    public static String DaoJia_QuerySingleCart(HttpServletRequest request, HttpServletResponse response) {
        String body = request.getParameter("body");
        Map<String, Object> bodyMap = new Gson().fromJson(body, Map.class);
        List<Map> skuList = (List<Map>) bodyMap.get("skus");
        String storeId = (String) bodyMap.get("storeId");
        String orgCode = (String) bodyMap.get("orgCode");
        Locale locale = request.getLocale();
        String baseUrl = UtilProperties.getMessage("content.properties", "daojia.baseUrl", locale);
        ShoppingCartEvents.keepCartUpdated(request, response);
        ShoppingCart shoppingCart = ShoppingCartEvents.getCartObject(request);
        
        CartItem cartItem = null;
        if (shoppingCart != null && shoppingCart.size() > 0) {
            java.text.DecimalFormat df = new java.text.DecimalFormat("0.00");
            //购物车信息
            Map cartInfo = FastMap.newInstance();
            String homePath = System.getProperty("ofbiz.home");
            //cart 总计
            
            BigDecimal displayGrandTotal = shoppingCart.getDisplayGrandTotal();
            cartInfo.put("displayGrandTotal", displayGrandTotal);
            String json = null;
            try {
                json = FileUtil.readString("utf-8", new File(homePath + "/daojia/daojia/config/cart/singleCart.json"));
            } catch (IOException e) {
                e.printStackTrace();
            }
            cartItem = CartItem.objectFromData(json);
            CartItemResult cartItemResult = cartItem.result;
            //如果有价格调整的情况
            BigDecimal displaySubTotal = shoppingCart.getDisplaySubTotal();
            //购物车小计
            cartInfo.put("displaySubTotal", displaySubTotal);
            cartItemResult.payMoneyPriceValue = df.format(displaySubTotal);
            cartItemResult.totalNum = shoppingCart.getTotalQuantity().toBigInteger().intValue();
            cartItemResult.numWeightDesc = "(已选" + shoppingCart.getTotalQuantity().toBigInteger().intValue() + "件，共0.1kg)";
            if (shoppingCart.getDisplayTaxIncluded().compareTo(BigDecimal.ZERO) == 1) {
                BigDecimal displayTaxInclude = shoppingCart.getDisplayTaxIncluded();
                cartInfo.put("displayTaxInclude", displayTaxInclude);
            }
            List<GenericValue> orderAdjustments = shoppingCart.makeAllAdjustments();
            List<GenericValue> orderItems = shoppingCart.makeOrderItems();
            List<GenericValue> workEfforts = shoppingCart.makeWorkEfforts();
            
            //除促销，销售税、配送费之外总计 WEB-INF/actions/order/CheckoutReview.groovy
            cartInfo.put("orderSubTotal", OrderReadHelper.getOrderItemsSubTotal(orderItems, orderAdjustments, workEfforts));
            cartInfo.put("orderShippingTotal", shoppingCart.getTotalShipping());
            cartInfo.put("orderTaxTotal", shoppingCart.getTotalSalesTax());
            
            
            if (shoppingCart.getAdjustments().size() > 0) {
                
                List<GenericValue> adjustments = shoppingCart.getAdjustments();
                
                BigDecimal adjustmentTotalAmount = BigDecimal.ZERO;
                List adjustmentList = FastList.newInstance();
                if (UtilValidate.isNotEmpty(adjustments)) {
                    String discountName = "";
                    for (int i = 0; i < adjustmentList.size(); i++) {
                        GenericValue adjustment = (GenericValue) adjustmentList.get(i);
                        Map adjustMap = FastMap.newInstance();
                        BigDecimal adjustmentAmount = OrderReadHelper.calcOrderAdjustment(adjustment, shoppingCart.getSubTotal());
                        String adjustmentDescription = (String) adjustment.get("description");
                        String productPromoId = (String) adjustment.get("productPromoId");
                        adjustMap.put("adjustmentAmount", adjustmentAmount);
                        adjustmentTotalAmount.add(adjustmentAmount);
                        adjustMap.put("adjustmentDescription", adjustmentDescription);
                        discountName += adjustmentDescription;
                        adjustMap.put("productPromoId", productPromoId);
                        adjustmentList.add(adjustMap);
                    }
                    cartItemResult.discountName = discountName;
                }
                
                cartInfo.put("adjustments", adjustmentList);
                cartInfo.put("adjustmentTotalAmount", adjustmentTotalAmount);
                
                
            }
            
            List<SkuList> skuLists = FastList.newInstance();
            List<ShoppingCartItem> shoppingCartItems = shoppingCart.items();
            List cartList = FastList.newInstance();
            BigDecimal toalQuantity = BigDecimal.ZERO;
            if (UtilValidate.isNotEmpty(shoppingCartItems)) {
                for (int i = 0; i < shoppingCartItems.size(); i++) {
                    
                    ShoppingCartItem cartLine = shoppingCartItems.get(i);
                    Map<String, Object> cartObj = FastMap.newInstance();
                    cartObj.put("quantity", cartLine.getQuantity());
                    toalQuantity.add(cartLine.getQuantity());
                    cartObj.put("name", cartLine.getName());
                    cartObj.put("description", cartLine.getItemTypeDescription());
                    cartObj.put("displayPrice", cartLine.getDisplayPrice());
                    GenericValue miniProduct = cartLine.getProduct();
                    ProductContentWrapper miniProductContentWrapper = new ProductContentWrapper(miniProduct, request);
                    String mediumImageUrl = miniProductContentWrapper.get("MEDIUM_IMAGE_URL").toString();
                    cartObj.put("mediumImageUrl", mediumImageUrl);
                    //调整
                    cartObj.put("otherAdjustments", cartLine.getOtherAdjustments());
                    //明细合计
                    cartObj.put("itemSubTotal", cartLine.getDisplayItemSubTotal());
                    String productId = cartLine.getProductId();
                    cartObj.put("productTypeId", miniProduct.get("productTypeId"));
                    
                    //variant product
                    cartObj.put("productId", productId);
                    if (UtilValidate.isNotEmpty(productId)) {
                        String parentProductId = cartLine.getParentProductId();
                        cartObj.put("parentProductId", parentProductId);
                    }
                    cartList.add(cartObj);
                    SkuList sku = new SkuList();
                    sku.setGoodsIndex(String.valueOf(i));
                    sku.setSkuId(productId);
                    sku.setCheckType(1);
                    sku.setSkuName(cartLine.getName());
                    sku.setBasePrice(df.format(cartLine.getBasePrice()));
                    sku.setPrice(df.format(cartLine.getDisplayPrice()));
                    sku.setImageUrl(baseUrl + mediumImageUrl);
                    sku.setCartNum(cartLine.getQuantity().intValue());
                    sku.setSkuState(1);
                    Picker picker = new Picker(1, 99);
                    sku.setPicker(picker);
                    sku.setWeight("0.1kg");
                    skuLists.add(sku);
                    
                }
                List<ItemList> itemLists = cartItemResult.getItemList();
                ItemList itemList = itemLists.get(0);
                itemList.setSkuList(skuLists);
                itemLists = FastList.newInstance();
                itemLists.add(itemList);
                cartItem.result.setItemList(itemLists);
//                cartInfo.put("totalQuantity", toalQuantity);
                //println "shoppingCartItems = $shoppingCartItems"
                Map<String, Object> resultData = FastMap.newInstance();
//                resultData.put("shoppingCartItems", cartList);
//                resultData.put("shoppingCart", cartInfo);
            }
        } else {
            cartItem = new CartItem("cartV3_2_0.querySingleCart", "0", UtilProperties.getMessage("CommonUiLabels", "CommonSuccess", locale), true);
        }
        
        
        request.setAttribute(ModelService.RESPONSE_MESSAGE, ModelService.RESPOND_SUCCESS);
        request.setAttribute("resultData", cartItem);
        return "success";
        
    }
    
    /**
     * 查询购物车
     *
     * @param request
     * @param response
     * @return
     */
    public static String DaoJia_QueryCart(HttpServletRequest request, HttpServletResponse response) {
        String body = request.getParameter("body");
        Map<String, Object> bodyMap = new Gson().fromJson(body, Map.class);
        List<Map> skuList = (List<Map>) bodyMap.get("skus");
        Delegator delegator = (Delegator) request.getAttribute("delegator");
        LocalDispatcher dispatcher = (LocalDispatcher) request.getAttribute("dispatcher");
        
        Locale locale = request.getLocale();
        String baseUrl = UtilProperties.getMessage("content.properties", "daojia.baseUrl", locale);
        ShoppingCartEvents.keepCartUpdated(request, response);
        ShoppingCart shoppingCart = ShoppingCartEvents.getCartObject(request);
        CartQuery cartQuery = new CartQuery("cartV3_0_0.query", "0", UtilProperties.getMessage("CommonUiLabels", "CommonSuccess", locale), true);
        String productStoreId = shoppingCart.getProductStoreId();
        try {
            java.text.DecimalFormat df = new java.text.DecimalFormat("0.00");
            Map<String, Object> storeMap = dispatcher.runSync("DaoJia_StoreInfo", UtilMisc.toMap("productStoreId", productStoreId));
            String orgCode = (String) storeMap.get("orgCode");
            GenericValue productStore = (GenericValue) storeMap.get("productStore");
            String storeName = productStore.getString("storeName");
            String logo = (String) storeMap.get("logo");
            if (shoppingCart != null && shoppingCart.size() > 0) {
                List<CartQueryResult> cartQueryResults = FastList.newInstance();
                CartQueryResult cartQueryResult = new CartQueryResult(productStoreId, orgCode, storeName, "2", logo, df.format(shoppingCart.getDisplayGrandTotal()), "合计", shoppingCart.getTotalQuantity().intValue());
                List<ShoppingCartItem> shoppingCartItems = shoppingCart.items();
                if (UtilValidate.isNotEmpty(shoppingCartItems)) {
                    List<com.yuaoq.yabiz.daojia.model.json.cart.cartQuery.ItemList> itemLists = FastList.newInstance();
                    for (int i = 0; i < shoppingCartItems.size(); i++) {
                        ShoppingCartItem cartLine = shoppingCartItems.get(i);
                        com.yuaoq.yabiz.daojia.model.json.cart.cartQuery.ItemList sku = new com.yuaoq.yabiz.daojia.model.json.cart.cartQuery.ItemList();
                        sku.setSkuId(cartLine.getProductId());
                        GenericValue miniProduct = cartLine.getProduct();
                        ProductContentWrapper miniProductContentWrapper = new ProductContentWrapper(miniProduct, request);
                        String mediumImageUrl = miniProductContentWrapper.get("MEDIUM_IMAGE_URL").toString();
                        sku.setPrice(df.format(cartLine.getDisplayPrice()));
                        sku.setImageUrl(baseUrl + mediumImageUrl);
                        sku.setCartNum(cartLine.getQuantity().intValue());
                        sku.setSkuState(1);
                        sku.setUpdateTime(cartLine.getShipAfterDate() == null ? new Date().getTime() : cartLine.getShipAfterDate().getTime());
                        itemLists.add(sku);
                    }
                    cartQueryResult.setItemList(itemLists);
                }
                cartQueryResults.add(cartQueryResult);
                cartQuery.setResult(cartQueryResults);
            }
            
        } catch (GenericServiceException e) {
            e.printStackTrace();
        }
        
        
        request.setAttribute(ModelService.RESPONSE_MESSAGE, ModelService.RESPOND_SUCCESS);
        request.setAttribute("resultData", cartQuery);
        return "success";
        
    }
    
    /**
     * 清空购物车商品
     *
     * @param request
     * @param response
     * @return
     */
    public static String DaoJia_RemoveAllItems(HttpServletRequest request, HttpServletResponse response) {
        
        String body = request.getParameter("body");
        Map<String, Object> bodyMap = new Gson().fromJson(body, Map.class);
        List<Map> skuList = (List<Map>) bodyMap.get("skus");
        String storeId = (String) bodyMap.get("storeId");
        String orgCode = (String) bodyMap.get("orgCode");
        Locale locale = request.getLocale();
        //获取request里面的ADD_PRODUCT_ID  QUANTITY
        String ret = ShoppingCartEvents.clearCart(request, response);
        Map<String, Object> retMap = FastMap.newInstance();
        retMap.put("id", "cartV3_2_0.addItem");
        retMap.put("code", "0");
        retMap.put("success", true);
        if (ret.equals("success")) {
            retMap.put("msg", UtilProperties.getMessage("CommonUiLabels", "CommonSuccess", locale));
            retMap.put("result", UtilProperties.getMessage("CommonUiLabels", "CommonSuccess", locale));
        } else {
            retMap.put("msg", UtilProperties.getMessage("CommonUiLabels", "CommonError", locale));
            retMap.put("result", UtilProperties.getMessage("CommonUiLabels", "CommonError", locale));
        }
        
        request.setAttribute("resultData", retMap);
        return "success";
    }
    
    /**
     * 购物车小计
     *
     * @param request
     * @param response
     * @return
     */
    public static String DaoJia_CartSum(HttpServletRequest request, HttpServletResponse response) {
        
        String body = request.getParameter("body");
        Map<String, Object> bodyMap = new Gson().fromJson(body, Map.class);
        List<Map> skuList = (List<Map>) bodyMap.get("skus");
        String storeId = (String) bodyMap.get("storeId");
        String orgCode = (String) bodyMap.get("orgCode");
        Locale locale = request.getLocale();
        //获取request里面的ADD_PRODUCT_ID  QUANTITY
        ShoppingCart shoppingCart = ShoppingCartEvents.getCartObject(request);
        Map<String, Object> retMap = FastMap.newInstance();
        retMap.put("id", "cartV1_4_0.sum");
        retMap.put("code", "0");
        retMap.put("success", true);
        
        retMap.put("msg", UtilProperties.getMessage("CommonUiLabels", "CommonSuccess", locale));
        Map rstMap = FastMap.newInstance();
        rstMap.put("totalNum", shoppingCart.getTotalQuantity().intValue());
        rstMap.put("totalPriceValue", String.valueOf(shoppingCart.getDisplayGrandTotal()));
        retMap.put("result", rstMap);
        request.setAttribute("resultData", retMap);
        return "success";
    }
    
    /**
     * 购物车商品
     *
     * @param request
     * @param response
     * @return
     */
    public static String DaoJia_CartRemoveItem(HttpServletRequest request, HttpServletResponse response) {
        
        String body = request.getParameter("body");
        Map<String, Object> bodyMap = new Gson().fromJson(body, Map.class);
        List<Map> skuList = (List<Map>) bodyMap.get("skus");
        String storeId = (String) bodyMap.get("storeId");
        String orgCode = (String) bodyMap.get("orgCode");
        Locale locale = request.getLocale();
        //获取request里面的ADD_PRODUCT_ID  QUANTITY
        String ret = ShoppingCartEvents.modifyCart(request, response);
        ret = DaoJia_QuerySingleCart(request, response);
        return "success";
    }
    
    
    /**
     * 修改购物车商品
     *
     * @param request  removeSelected=false&changeSelected=0&selectAll=0&selectedItem=0&update_0=3
     * @param response {"id": "cartV3_2_0.addItem","code": "0","msg": "商品已加入购物车","detail": "操作成功","success": true}
     * @return
     */
    public static String DaoJia_CartAddItem(HttpServletRequest request, HttpServletResponse response) {
        
        String body = request.getParameter("body");
        Map<String, Object> bodyMap = new Gson().fromJson(body, Map.class);
        List<Map> skuList = (List<Map>) bodyMap.get("skus");
        String storeId = (String) bodyMap.get("storeId");
        String orgCode = (String) bodyMap.get("orgCode");
        Locale locale = request.getLocale();
        //获取request里面的ADD_PRODUCT_ID  QUANTITY
        String ret = ShoppingCartEvents.addToCart(request, response);
        Map<String, Object> retMap = FastMap.newInstance();
        retMap.put("id", "cartV3_2_0.addItem");
        retMap.put("code", "0");
        retMap.put("success", true);
        if (ret.equals("success")) {
            retMap.put("msg", UtilProperties.getMessage("CommonUiLabels", "CommonSuccess", locale));
            retMap.put("result", UtilProperties.getMessage("CommonUiLabels", "CommonSuccess", locale));
        } else {
            retMap.put("msg", UtilProperties.getMessage("CommonUiLabels", "CommonError", locale));
            retMap.put("result", UtilProperties.getMessage("CommonUiLabels", "CommonError", locale));
        }
        
        request.setAttribute("resultData", retMap);
        return "success";
    }
    
    /**
     * 修改购物车商品
     *
     * @param request  removeSelected=false&changeSelected=0&selectAll=0&selectedItem=0&update_0=3
     * @param response
     * @return
     */
    public static String DaoJia_ChangeItemNum(HttpServletRequest request, HttpServletResponse response) {
        
        String body = request.getParameter("body");
        
        Locale locale = request.getLocale();
        //获取request里面的ADD_PRODUCT_ID  QUANTITY
        String ret = ShoppingCartEvents.modifyCart(request, response);
        ret = DaoJia_QuerySingleCart(request, response);
        return "success";
    }
    
    
    /**
     * @param request
     * @param response
     * @return
     */
    public static String DaoJia_VerifySettle(HttpServletRequest request, HttpServletResponse response) {
        Map<String,Object> retMap = FastMap.newInstance();
        GenericValue userLogin = (GenericValue) request.getSession().getAttribute("userLogin");
        if(UtilValidate.isNotEmpty(userLogin)){
            retMap.put("id","cartV3_3_0.verifySettle");
            retMap.put("type","0");
            retMap.put("code","0");
            retMap.put("msg","商品已加入购物车");
            retMap.put("result","操作成功");
            retMap.put("detail","操作成功");
            retMap.put("success",true);
            
        }else {
            retMap.put("id","cartV3_3_0.verifySettle");
            retMap.put("type","1");
            retMap.put("code","201");
            retMap.put("msg","请您登录");
            retMap.put("result",null);
            retMap.put("detail","");
            retMap.put("success",false);
        }
        
       
        request.setAttribute("resultData", retMap);
        return "success";
    }
    
    
    /**
     * 结算信息
     * {"orderPayType":4,"fromSource":2,"jingBeansNum":0,"source":2,"channelType":"0","orgCode":"CN-110101","storeId":10000,"storeName":"永辉超市-滨江新城店","openJPIndustry":"1","addressType":false,"cityCode":"904","longitude":118.72889,"latitude":32.13012,"addressId":10161,"voucherCode":""}
     *
     * @param request
     * @param response
     * @return
     */
    public static String DaoJia_GetCurrentAccount(HttpServletRequest request, HttpServletResponse response) {
        Map<String, Object> result = ServiceUtil.returnSuccess();
        Delegator delegator = (Delegator) request.getAttribute("delegator");
        String body = request.getParameter("body");
        Map bodyMap = new Gson().fromJson(body, Map.class);
        String addressIdStr = bodyMap.get("addressId") == null ? "" : String.valueOf(((Double) bodyMap.get("addressId")).intValue());
        Locale locale = request.getLocale();
        LocalDispatcher dispatcher = (LocalDispatcher) request.getAttribute("dispatcher");
        java.text.DecimalFormat df = new java.text.DecimalFormat("0.00");
        ShoppingCart shoppingCart = ShoppingCartEvents.getCartObject(request);
        MarketSettleGetCurrentAccount account = new MarketSettleGetCurrentAccount("marketsettle.getCurrentAccount", "0", UtilProperties.getMessage("CommonUiLabels", "CommonSuccess", locale), true);
        String homePath = System.getProperty("ofbiz.home");
        String json = null;
        try {
            json = FileUtil.readString("utf-8", new File(homePath + "/daojia/daojia/config/account/marketSettleGetCurrentAccount.json"));
            account = MarketSettleGetCurrentAccount.objectFromData(json);
            AccountResult accountResult = account.result;
            accountResult.setTotalMoney(UtilFormatOut.formatCurrency(shoppingCart.getDisplayGrandTotal(), "", locale, 2));
            accountResult.setTotalDiscount(UtilFormatOut.formatCurrency(shoppingCart.getProductPromoTotal(), "", locale, 2));
            accountResult.setFreightFee(shoppingCart.getTotalShipping().multiply(new BigDecimal(100)).intValue());
            accountResult.setManJianMoney(shoppingCart.getDisplaySubTotal().multiply(new BigDecimal(100)).intValue());
            accountResult.setTotalCost(UtilFormatOut.formatCurrency(shoppingCart.getGrandTotal(), "", locale, 2));
            GenericValue userLogin = (GenericValue) request.getSession().getAttribute("userLogin");
            
            List<AccountModules> modules = FastList.newInstance();
            accountResult.setModules(modules);
            //地址
            AccountModules addressModule = new AccountModules(true, "receiptAddress", "收货地址", "", 0, "1", false);
            json = FileUtil.readString("utf-8", new File(homePath + "/daojia/daojia/config/cart/addressData.json"));
            AddressData addressData = AddressData.objectFromData(json);
            GenericValue address = null;
            if (UtilValidate.isNotEmpty(addressIdStr)) {
                address = delegator.findByPrimaryKey("PostalAddress", UtilMisc.toMap("contactMechId", addressIdStr));
            } else {
                address = ContactMechWorker.getDefaultAddress(delegator, userLogin.getString("partyId"), null);
            }
            if (UtilValidate.isNotEmpty(address)) {
                int addressId = Integer.parseInt((String) address.get("contactMechId"));
                String toName = (String) address.get("toName");
                String mobile = (String) address.get("mobilePhone");
                mobile = UtilStrings.getMaskString(mobile, 3, 4);
                addressData.bindPhoneVO = new BindPhoneVO(1, mobile);
                
                String addressDetail = address.getString("address1") + address.getString("address2");
                String cityStr = address.getString("city");
                if (cityStr == null || cityStr.equals("")) cityStr = "0";
                if (cityStr.startsWith("CN-")) {
                    cityStr = cityStr.substring(3);
                }
                int cityId = Integer.parseInt(cityStr);
                AddressVo addressVo = new AddressVo(addressId, toName, mobile, addressDetail, cityId);
                addressVo.latitude = (Double) bodyMap.get("latitude");
                addressVo.longitude = (Double) bodyMap.get("longitude");
                String pointId = (String) address.get("geoPointId");
                if (pointId != null) {
                    GenericValue value = delegator.findByPrimaryKey("GeoPoint", UtilMisc.toMap("geoPointId", pointId));
                    if (UtilValidate.isNotEmpty(value)) {
                        addressVo.latitude = new Double(value.getString("latitude"));
                        addressVo.longitude = new Double(value.getString("longitude"));
                    }
                }
                addressData.addressVo = addressVo;
                addressModule.setData(addressData);
            }
            modules.add(addressModule);
            
            //送达时间
            AccountModules deliverTimeModule = new AccountModules(true, "deliverTime", "送达时间", "", 0, "1", false);
            json = FileUtil.readString("utf-8", new File(homePath + "/daojia/daojia/config/cart/deliverTimeData.json"));
            DeliverTimeData deliverTimeData = DeliverTimeData.objectFromData(json);
            /*String dateStr = UtilDateTime.toDateString(new Date(), "yyyy-MM-dd");
            deliverTimeData.promiseDate = dateStr;
            List<DeliverTimeData.PromiseTimeRespItems> timeRespItemses = FastList.newInstance();
            long timeLose = System.currentTimeMillis() + 2 * 60 * 60 * 1000;
            String lastTime = UtilDateTime.toTimeString(21, 0, 0);
            long lastDateTime = UtilDateTime.stringToTimeStamp(dateStr + " " + lastTime, "yyyy-MM-dd HH:mm", TimeZone.getDefault(), locale).getTime();
            String shippFee = df.format(shoppingCart.getTotalShipping()) + "元运费";
            String loseDate = UtilFormatOut.formatDate(new Date(timeLose), "HH:mm", locale, TimeZone.getDefault());
            DeliverTimeData.PromiseTimeRespItems timeRespItems = new DeliverTimeData.PromiseTimeRespItems("立即送达", loseDate, loseDate, shippFee, false);
            timeRespItemses.add(timeRespItems);
            while (timeLose < lastDateTime) {
                timeLose = timeLose + 2 * 60 * 60 * 1000;
                String startTime = UtilDateTime.toDateString(new Date(), "HH:mm");
                timeLose = timeLose + 1 * 30 * 60 * 1000;
                String endTime =  UtilDateTime.toDateString(new Date(), "HH:mm");
                timeRespItems = new DeliverTimeData.PromiseTimeRespItems(startTime+"-"+endTime, startTime, endTime, shippFee, false);
                timeRespItemses.add(timeRespItems);
            }
            deliverTimeData.promiseTimeRespItems = timeRespItemses;*/
            deliverTimeModule.setData(deliverTimeData);
            modules.add(deliverTimeModule);
            //订购人信息
            AccountModules bookInfoModule = new AccountModules(false, "bookInfo", "订购人信息", "", 0, "1", false);
            modules.add(bookInfoModule);
            //手艺人信息
            AccountModules artistInfoModule = new AccountModules(false, "artistInfo", "手艺人", "", 0, "1", false);
            modules.add(artistInfoModule);
            //支付方式
            AccountModules paymentModule = new AccountModules(true, "payMethod", "支付方式", "", 0, "2", false);
            json = FileUtil.readString("utf-8", new File(homePath + "/daojia/daojia/config/cart/paymentMethodData.json"));
            PaymentMethodData paymentMethodData = PaymentMethodData.objectFromData(json);
            paymentModule.setData(paymentMethodData);
            modules.add(paymentModule);
            
            //优惠劵
            AccountModules couponModule = new AccountModules(true, "conponInfo", "优惠劵", "", 0, "3", false);
            json = FileUtil.readString("utf-8", new File(homePath + "/daojia/daojia/config/cart/couponData.json"));
            CouponData couponData = CouponData.objectFromData(json);
            couponModule.setData(couponData);
            modules.add(couponModule);
            //订单备注
            AccountModules orderMarkModule = new AccountModules(true, "orderMark", "订单备注(30字以内)", "", 0, "4", false);
            modules.add(orderMarkModule);
            //产品信息
            AccountModules productModule = new AccountModules(true, "productInfo", "", "", 0, "5", false);
            json = FileUtil.readString("utf-8", new File(homePath + "/daojia/daojia/config/cart/paymentMethodData.json"));
            ProductInfoData productInfoData = ProductInfoData.objectFromData(json);
            List<ShoppingCartItem> items = shoppingCart.items();
            String baseUrl = UtilProperties.getMessage("content.properties", "daojia.baseUrl", locale);
            if (UtilValidate.isNotEmpty(items)) {
                List<ProductInfoData> pDatas = FastList.newInstance();
                for (int i = 0; i < items.size(); i++) {
                    ShoppingCartItem cartItem = items.get(i);
                    ProductInfoData pData = new ProductInfoData();
                    pData.skuId = Integer.parseInt(cartItem.getProductId());
                    pData.name = cartItem.getName();
                    pData.price = cartItem.getDisplayPrice().multiply(new BigDecimal(100)).intValue();
                    pData.quantity = cartItem.getQuantity().intValue();
                    ProductContentWrapper miniProductContentWrapper = new ProductContentWrapper(dispatcher, cartItem.getProduct(), locale, "text/html");
                    pData.img = baseUrl + miniProductContentWrapper.get("MEDIUM_IMAGE_URL");
                    pData.maxPurchaseNum = 0;
                    pData.minPurchaseNum = 0;
                    pData.editorPurchaseNum = 0;
                    pData.money = cartItem.getDisplayPrice().multiply(new BigDecimal(100)).intValue();
                    pDatas.add(pData);
                }
                productModule.setData(pDatas);
            }
            modules.add(productModule);
            
            //金额
            AccountModules moneyModule = new AccountModules(true, "moneyInfo", "", "", 0, "5", false);
            List<MoneyInfoData> mDatas = FastList.newInstance();
            
            MoneyInfoData moneyInfoData = new MoneyInfoData("商品金额", df.format(shoppingCart.getDisplayGrandTotal()), "#0F0F0F", "", "");
            mDatas.add(moneyInfoData);
            
            MoneyInfoData moneyShipInfoData = new MoneyInfoData("运费金额", UtilFormatOut.formatCurrency(shoppingCart.getTotalShipping(), null, locale, 2), "#0F0F0F", "本单运费共计" + df.format(shoppingCart.getTotalShipping()) + "元，包括<br/>·基础运费" + df.format(shoppingCart.getTotalShipping()) + "元<br/>", "");
            mDatas.add(moneyShipInfoData);
            
            moneyModule.setData(mDatas);
            modules.add(moneyModule);
            
            
            //优惠金额
            AccountModules disMoneyModule = new AccountModules(true, "disMoneyInfo", "", "", 0, "5", false);
            json = FileUtil.readString("utf-8", new File(homePath + "/daojia/daojia/config/cart/disMoneyInfoData.json"));
            DisMoneyInfoData disMoneyInfoData = DisMoneyInfoData.objectFromData(json);
            
            disMoneyModule.setData(UtilMisc.toList(disMoneyInfoData));
            
            modules.add(disMoneyModule);
            //outOfStockConfig
            AccountModules outOfStockConfigModule = new AccountModules(true, "outOfStockConfig", "所购商品如遇缺货，您需要：", "其它商品继续配送（缺货商品退款）,有缺货直接取消订单,缺货时电话与我沟通", 0, "6", false);
            List<String> outStrs = FastList.newInstance();
            outStrs.add("其它商品继续配送（缺货商品退款）");
            outStrs.add("有缺货直接取消订单");
            outStrs.add("缺货时电话与我沟通");
            outOfStockConfigModule.setData(outStrs);
            modules.add(outOfStockConfigModule);
            //submitInfo
            AccountModules submitInfoModule = new AccountModules(true, "submitInfo", "提交订单", "", 0, "7", false);
            modules.add(submitInfoModule);
            
        } catch (IOException e) {
            e.printStackTrace();
        } catch (GenericEntityException e) {
            e.printStackTrace();
        }
        request.setAttribute("resultData", account);
        return "success";
    }
    
    
    /**
     * 增加购物车商品
     *
     * @param request
     * @param response
     * @return
     */
    public static String DaoJia_GetUsableVoucherCountFive(HttpServletRequest request, HttpServletResponse response) {
        
        String homePath = System.getProperty("ofbiz.home");
        String json = null;
        try {
            json = FileUtil.readString("utf-8", new File(homePath + "/daojia/daojia/config/cart/cartAddItem.json"));
        } catch (IOException e) {
            e.printStackTrace();
        }
        CartAddItem cartItem = CartAddItem.objectFromData(json);
        request.setAttribute("resultData", cartItem);
        return "success";
    }
    
    
}
