package com.yuaoq.yabiz.daojia.service.product;


import com.yuaoq.yabiz.daojia.model.json.base.Tags;
import javolution.util.FastList;
import org.ofbiz.base.util.UtilMisc;
import org.ofbiz.base.util.UtilValidate;
import org.ofbiz.entity.Delegator;
import org.ofbiz.entity.GenericEntityException;
import org.ofbiz.entity.GenericValue;
import org.ofbiz.entity.util.EntityUtil;
import org.ofbiz.service.DispatchContext;
import org.ofbiz.service.ServiceUtil;

import java.util.List;
import java.util.Map;

/**
 * Created by changsy on 16/9/2.
 */
public class ProductPromoService {
    
    public static final String module = ProductPromoService.class.getName();
    public static final int DEFAULT_TX_TIMEOUT = 600;
    
    /**
     * 产品对应使用在促销活动，暂未实现
     *
     * @param dxt
     * @param context
     * @return
     */
    public static Map<String, Object> DaoJia_ProductPromos(DispatchContext dxt, Map<String, ? extends Object> context) {
        String productId = (String) context.get("productId");
        String storeId = (String) context.get("storeId");
        Map<String, Object> result = ServiceUtil.returnSuccess();
        Delegator delegator = dxt.getDelegator();
        //获取tags（促销信息）
        List<GenericValue> promos = null;
        try {
            promos = EntityUtil.filterByDate(delegator.findByAnd("ProductStorePromoAndAppl", UtilMisc.toMap("productStoreId", storeId, "userEntered", "Y"), UtilMisc.toList("sequenceNum", "productPromoId")));
        } catch (GenericEntityException e) {
            result = ServiceUtil.returnError(e.getMessage());
            e.printStackTrace();
        }
        List<Tags> tags = FastList.newInstance();
        String[] colors = new String[8];
        colors[0] = "5FBC65";
        colors[1] = "19BAFF";
        colors[2] = "19BAFF";
        colors[3] = "5FBC65";
        colors[4] = "19BAFF";
        colors[5] = "19BAFF";
        colors[6] = "5FBC65";
        colors[7] = "19BAFF";
        if (UtilValidate.isNotEmpty(promos)) {
            for (int i = 0; i < promos.size(); i++) {
                GenericValue promo = promos.get(i);
                //productId
                try {
                    delegator.findByAnd("ProductPromoProduct", UtilMisc.toMap("productPromoId", promo.get("productPromoId")));
                } catch (GenericEntityException e) {
                    e.printStackTrace();
                }
                Tags ts = new Tags(promo.getString("name"), promo.getString("name"), 6, 1, promo.getString("promoText"), 1, colors[i]);
                tags.add(ts);
            }
        }
        result.put("resultData", tags);
        return result;
    }
    
    
    public static Map<String, Object> DaoJia_StorePromos(DispatchContext dxt, Map<String, ? extends Object> context) {
        Map<String, Object> result = ServiceUtil.returnSuccess();
        String storeId = (String) context.get("storeId");
        Delegator delegator = dxt.getDelegator();
        //获取tags（促销信息）
        List<GenericValue> promos = null;
        try {
            promos = EntityUtil.filterByDate(delegator.findByAnd("ProductStorePromoAndAppl", UtilMisc.toMap("productStoreId", storeId, "userEntered", "Y"), UtilMisc.toList("sequenceNum", "productPromoId")));
        } catch (GenericEntityException e) {
            result = ServiceUtil.returnError(e.getMessage());
            e.printStackTrace();
        }
        List<Tags> tags = FastList.newInstance();
        String[] colors = new String[8];
        colors[0] = "5FBC65";
        colors[1] = "19BAFF";
        colors[2] = "19BAFF";
        colors[3] = "5FBC65";
        colors[4] = "19BAFF";
        colors[5] = "19BAFF";
        colors[6] = "5FBC65";
        colors[7] = "19BAFF";
        if (UtilValidate.isNotEmpty(promos)) {
            for (int i = 0; i < promos.size(); i++) {
                GenericValue promo = promos.get(i);
                Tags ts = new Tags(promo.getString("name"), promo.getString("name"), 6, 1, promo.getString("promoText"), 1, colors[i]);
                tags.add(ts);
            }
        }
        result.put("resultData", tags);
        return result;
    }
}
