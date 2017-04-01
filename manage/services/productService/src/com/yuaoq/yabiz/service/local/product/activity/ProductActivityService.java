package com.yuaoq.yabiz.service.local.product.activity;

import javolution.util.FastList;
import javolution.util.FastMap;
import org.ofbiz.base.util.*;
import org.ofbiz.entity.Delegator;
import org.ofbiz.entity.GenericEntityException;
import org.ofbiz.entity.GenericValue;
import org.ofbiz.entity.condition.EntityCondition;
import org.ofbiz.entity.condition.EntityFunction;
import org.ofbiz.entity.condition.EntityOperator;
import org.ofbiz.entity.util.EntityFindOptions;
import org.ofbiz.entity.util.EntityListIterator;
import org.ofbiz.entity.util.EntityUtil;
import org.ofbiz.service.DispatchContext;
import org.ofbiz.service.GenericServiceException;
import org.ofbiz.service.LocalDispatcher;
import org.ofbiz.service.ServiceUtil;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.*;

/**
 * Created by changsy on 16/1/9.
 */
public class ProductActivityService {

    public Map<String, Object> findSecKill(DispatchContext dcx, Map<String, ? extends Object> context) {
        Map<String, Object> result = ServiceUtil.returnSuccess();
        return result;
    }


    public Map<String, Object> addGroupOrder(DispatchContext dcx, Map<String, ? extends Object> context) {
        LocalDispatcher dispatcher = dcx.getDispatcher();
        Map<String, Object> result = ServiceUtil.returnSuccess();
        String productStoreIds = (String) context.get("productStoreIds");
        String activityCode = (String) context.get("activityCode");
        String activityAuditStatus = (String) context.get("activityAuditStatus");
        String activityType = (String) context.get("activityType");
        String activityName = (String) context.get("activityName");
        Timestamp publishDate = (Timestamp) context.get("publishDate");
        Timestamp endDate = (Timestamp) context.get("endDate");
        Timestamp activityStartDate = (Timestamp) context.get("activityStartDate");
        Timestamp activityEndDate = (Timestamp) context.get("activityEndDate");
        Long limitQuantity = (Long) context.get("limitQuantity");
        Long activityQuantity = (Long) context.get("activityQuantity");
        Long scoreValue = (Long) context.get("scoreValue");
        String activityPayType = (String) context.get("activityPayType");
        String activityDesc = (String) context.get("activityDesc");
        BigDecimal productPrice = (BigDecimal) context.get("productPrice");

        String productId = (String) context.get("productId");
        String shipmentType = (String) context.get("shipmentType");
        Timestamp virtualProductStartDate = (Timestamp) context.get("virtualProductStartDate");
        Timestamp virtualProductEndDate = (Timestamp) context.get("virtualProductEndDate");
        String isAnyReturn = (String) context.get("isAnyReturn");
        String isSupportOverTimeReturn = (String) context.get("isSupportOverTimeReturn");
        String isSupportScore = (String) context.get("isSupportScore");
        String isSupportReturnScore = (String) context.get("isSupportReturnScore");
        String isShowIndex = (String) context.get("isShowIndex");
        String productGroupOrderRules = (String) context.get("productGroupOrderRules");
        String productActivityPartyLevels = (String) context.get("productActivityPartyLevels");
        String productActivityAreas = (String) context.get("productActivityAreas");
        GenericValue userLogin = (GenericValue) context.get("userLogin");

        //创建productActivity
        Delegator delegator = dcx.getDelegator();

        List<GenericValue> allStore = new LinkedList<GenericValue>();
        GenericValue productActivity = delegator.makeValue("ProductActivity");
        productActivity.set("activityCode", activityCode);
        productActivity.set("activityAuditStatus", activityAuditStatus);
        productActivity.set("activityName", activityName);
        productActivity.set("activityType", activityType);
        productActivity.set("publishDate", publishDate);
        productActivity.set("endDate", endDate);
        productActivity.set("activityStartDate", activityStartDate);
        productActivity.set("activityEndDate", activityEndDate);
        productActivity.set("limitQuantity", limitQuantity);
        productActivity.set("activityQuantity", activityQuantity);
        productActivity.set("scoreValue", scoreValue);
        productActivity.set("productPrice", productPrice);
        productActivity.set("activityPayType", activityPayType);
        productActivity.set("activityDesc", activityDesc);

        String promoId = "";
        String activityId = delegator.getNextSeqId("ProductActivity");

        try {
            productActivity.set("activityId", activityId);
            productActivity.create();

            //创建对应商品
            GenericValue productActivityGoods = delegator.makeValue("ProductActivityGoods");
            productActivityGoods.set("productId", productId);
            productActivityGoods.set("shipmentType", shipmentType);
            productActivityGoods.set("isAnyReturn", isAnyReturn);
            productActivityGoods.set("isSupportOverTimeReturn", isSupportOverTimeReturn);
            productActivityGoods.set("isSupportScore", isSupportScore);
            productActivityGoods.set("isSupportReturnScore", isSupportReturnScore);
            productActivityGoods.set("isShowIndex", isShowIndex);
            productActivityGoods.set("virtualProductStartDate", virtualProductStartDate);
            productActivityGoods.set("virtualProductEndDate", virtualProductEndDate);
            productActivityGoods.set("activityId", activityId);
            allStore.add(productActivityGoods);
            //创建对应ProductGroupOrderRule
            if (UtilValidate.isNotEmpty(productGroupOrderRules)) {
                List<String> rules = StringUtil.split(productGroupOrderRules, ",");
                if (UtilValidate.isNotEmpty(rules)) {
                    for (int i = 0; i < rules.size(); i++) {
                        String rule = rules.get(i);
                        if (UtilValidate.isNotEmpty(rule)) {
                            List<String> ruleObj = StringUtil.split(rule, ":");
                            if (UtilValidate.isNotEmpty(ruleObj)) {
                                String seq = ruleObj.get(0);
                                String quantity = ruleObj.get(1);
                                Long lQuantity = Long.parseLong(quantity);
                                BigDecimal price = new BigDecimal(ruleObj.get(2));

                                //创建团购促销

                                String promoName = activityName + ":促销" + (i + 1);
                                String promoText = promoName;
                                String userEntered = "Y";
                                String showToCustomer = "Y";
                                String requireCode = "N";
                                Long useLimitPerOrder = 1l;
                                Long useLimitPerCustomer = 1l;
                                Map inputParams = FastMap.newInstance();
                                inputParams.put("promoName", promoName);
                                inputParams.put("promoText", promoText);
                                inputParams.put("userEntered", userEntered);
                                inputParams.put("showToCustomer", showToCustomer);
                                inputParams.put("requireCode", requireCode);
                                inputParams.put("useLimitPerOrder", useLimitPerOrder);
                                inputParams.put("useLimitPerCustomer", useLimitPerCustomer);
                                inputParams.put("userLogin", userLogin);

                                try {
                                    Map<String, Object> callback = dispatcher.runSync("createProductPromo", inputParams);
                                    if (ServiceUtil.isError(callback)) {
                                        return result;
                                    }

                                    String productPromoId = (String) callback.get("productPromoId");
                                    //创建createProductPromoRule
                                    String ruleName = activityName + ":促销条件" + (i + 1);
                                    inputParams = FastMap.newInstance();
                                    inputParams.put("productPromoId", productPromoId);
                                    inputParams.put("ruleName", ruleName);
                                    inputParams.put("userLogin", userLogin);
                                    callback = dispatcher.runSync("createProductPromoRule", inputParams);
                                    if (ServiceUtil.isError(callback)) {
                                        return result;
                                    }
                                    String RQuantity = null;
                                    String productPromoRuleId = (String) callback.get("productPromoRuleId");
//                                    获取阶梯价的next值
                                    if (i < (rules.size() - 1)) {
                                        if (UtilValidate.isNotEmpty(rules.get(i + 1))) {
                                            String nextRule = rules.get(i + 1);
                                            List<String> nextRuleObj = StringUtil.split(nextRule, ":");
                                            if (UtilValidate.isNotEmpty(nextRuleObj)) {
                                                RQuantity = nextRuleObj.get(1);

                                            }
                                        }
                                    }
                                    //创建createProductPromoCond
                                    inputParams = FastMap.newInstance();
                                    inputParams.put("productPromoRuleId", productPromoRuleId);
                                    inputParams.put("productPromoId", productPromoId);

                                    inputParams.put("inputParamEnumId", "PPIP_GRPODR_TOTAL");
                                    inputParams.put("operatorEnumId", "PPC_BTW");
                                    inputParams.put("condValue", lQuantity.toString());
                                    inputParams.put("otherValue", RQuantity);
                                    inputParams.put("userLogin", userLogin);
                                    callback = dispatcher.runSync("createProductPromoCond", inputParams);
                                    if (ServiceUtil.isError(callback)) {
                                        return result;
                                    }
                                    String productPromoCondSeqId = (String) callback.get("productPromoCondSeqId");
                                    //创建条件产品
                                    inputParams = FastMap.newInstance();
                                    inputParams.put("productPromoRuleId", productPromoRuleId);
                                    inputParams.put("productPromoId", productPromoId);
                                    inputParams.put("productPromoCondSeqId", productPromoCondSeqId);
                                    inputParams.put("productPromoActionSeqId", "_NA_");
                                    inputParams.put("productId", productId);
                                    inputParams.put("productPromoApplEnumId", "PPPA_INCLUDE");
                                    inputParams.put("userLogin", userLogin);
                                    callback = dispatcher.runSync("createProductPromoProduct", inputParams);
                                    if (ServiceUtil.isError(callback)) {
                                        return result;
                                    }
                                    //创建createProductPromoAction
                                    inputParams = FastMap.newInstance();
                                    inputParams.put("productPromoRuleId", productPromoRuleId);
                                    inputParams.put("productPromoId", productPromoId);
                                    inputParams.put("productPromoActionEnumId", "PROMO_PROD_ASGPC");
                                    inputParams.put("orderAdjustmentTypeId", "PROMOTION_ADJUSTMENT");
                                    inputParams.put("quantity", BigDecimal.ONE);
                                    inputParams.put("amount", price);
                                    inputParams.put("useCartQuantity", "N");
                                    inputParams.put("userLogin", userLogin);
                                    callback = dispatcher.runSync("createProductPromoAction", inputParams);
                                    if (ServiceUtil.isError(callback)) {
                                        return result;
                                    }
                                    //创建ProductPromoProduct
                                    String productPromoActionSeqId = (String) callback.get("productPromoActionSeqId");
                                    inputParams = FastMap.newInstance();
                                    inputParams.put("productPromoRuleId", productPromoRuleId);
                                    inputParams.put("productPromoId", productPromoId);
                                    inputParams.put("productPromoCondSeqId", "_NA_");
                                    inputParams.put("productPromoActionSeqId", productPromoActionSeqId);
                                    inputParams.put("productId", productId);
                                    inputParams.put("productPromoApplEnumId", "PPPA_INCLUDE");
                                    inputParams.put("userLogin", userLogin);
                                    callback = dispatcher.runSync("createProductPromoProduct", inputParams);
                                    if (ServiceUtil.isError(callback)) {
                                        return result;
                                    }
                                    //创建促销对应店铺createProductStorePromoAppl

                                    if (UtilValidate.isNotEmpty(productStoreIds)) {
                                        List storeIds = StringUtil.split(productStoreIds, ",");
                                        for (int j = 0; j < storeIds.size(); j++) {
                                            String productStoreId = (String) storeIds.get(j);
                                            inputParams = FastMap.newInstance();
                                            inputParams.put("productStoreId", productStoreId);
                                            inputParams.put("productPromoId", productPromoId);
                                            inputParams.put("fromDate", UtilDateTime.nowTimestamp());
                                            inputParams.put("userLogin", userLogin);
                                            callback = dispatcher.runSync("createProductStorePromoAppl", inputParams);
                                            if (ServiceUtil.isError(callback)) {
                                                return result;
                                            }

                                        }

                                    }

                                    //创建产品团购规则对应每个促销
                                    GenericValue productGroupOrderRule = delegator.makeValue("ProductGroupOrderRule");
                                    productGroupOrderRule.set("activityId", activityId);
                                    productGroupOrderRule.set("seqId", seq);
                                    productGroupOrderRule.set("orderQuantity", lQuantity);
                                    productGroupOrderRule.set("orderPrice", price);
                                    productGroupOrderRule.set("productPromoId", productPromoId);
                                    allStore.add(productGroupOrderRule);

                                } catch (GenericServiceException e) {
                                    return ServiceUtil.returnError(e.getMessage());
                                }


                            }

                        }
                    }
                }
            }
            //创建对应ProductPartyLevel
            if (UtilValidate.isNotEmpty(productActivityPartyLevels)) {
                List<String> levels = StringUtil.split(productActivityPartyLevels, ",");
                if (UtilValidate.isNotEmpty(levels)) {
                    for (int i = 0; i < levels.size(); i++) {
                        String level = levels.get(i);
                        List<String> levelObj = StringUtil.split(level, ":");
                        if (UtilValidate.isNotEmpty(levelObj)) {
                            String levelId = levelObj.get(0);
                            String levelName = levelObj.get(1);
                            GenericValue activityPartyLevel = delegator.makeValue("ProductActivityPartyLevel");
                            activityPartyLevel.set("levelId", levelId);
                            activityPartyLevel.set("levelName", levelName);
                            activityPartyLevel.set("activityId", activityId);
                            allStore.add(activityPartyLevel);
                        }
                    }
                }
            }
            //productActivityAreas
            if (UtilValidate.isNotEmpty(productActivityAreas)) {
                List<String> areas = StringUtil.split(productActivityAreas, ",");
                if (UtilValidate.isNotEmpty(areas)) {
                    for (int i = 0; i < areas.size(); i++) {
                        String area = areas.get(i);
                        List<String> areaObj = StringUtil.split(area, ":");
                        if (UtilValidate.isNotEmpty(areaObj)) {
                            String geoId = areaObj.get(0);
                            String geoName = areaObj.get(1);
                            GenericValue productActivityArea = delegator.makeValue("ProductActivityArea");
                            productActivityArea.set("communityId", geoId);
                            productActivityArea.set("communityName", geoName);
                            productActivityArea.set("activityId", activityId);
                            allStore.add(productActivityArea);
                        }
                    }
                }
            }

            //

            delegator.storeAll(allStore);

        } catch (GenericEntityException e) {

            return ServiceUtil.returnError(e.getMessage());
        }
        result.put("activityId", activityId);

        return result;
    }


    public Map<String, Object> updateGroupOrder(DispatchContext dcx, Map<String, ? extends Object> context) {
        LocalDispatcher dispatcher = dcx.getDispatcher();
        Map<String, Object> result = ServiceUtil.returnSuccess();
        String activityId = (String) context.get("activityId");
        String productStoreIds = (String) context.get("productStoreIds");
        String activityCode = (String) context.get("activityCode");
        String activityAuditStatus = (String) context.get("activityAuditStatus");
        String activityType = (String) context.get("activityType");
        String activityName = (String) context.get("activityName");
        Timestamp publishDate = (Timestamp) context.get("publishDate");
        Timestamp endDate = (Timestamp) context.get("endDate");
        Timestamp activityStartDate = (Timestamp) context.get("activityStartDate");
        Timestamp activityEndDate = (Timestamp) context.get("activityEndDate");
        Long limitQuantity = (Long) context.get("limitQuantity");
        Long activityQuantity = (Long) context.get("activityQuantity");
        Long scoreValue = (Long) context.get("scoreValue");
        String activityPayType = (String) context.get("activityPayType");
        String activityDesc = (String) context.get("activityDesc");
        BigDecimal productPrice = (BigDecimal) context.get("productPrice");

        String productId = (String) context.get("productId");
        String shipmentType = (String) context.get("shipmentType");
        Timestamp virtualProductStartDate = (Timestamp) context.get("virtualProductStartDate");
        Timestamp virtualProductEndDate = (Timestamp) context.get("virtualProductEndDate");
        String isAnyReturn = (String) context.get("isAnyReturn");
        String isSupportOverTimeReturn = (String) context.get("isSupportOverTimeReturn");
        String isSupportScore = (String) context.get("isSupportScore");
        String isSupportReturnScore = (String) context.get("isSupportReturnScore");
        String isShowIndex = (String) context.get("isShowIndex");
        String productGroupOrderRules = (String) context.get("productGroupOrderRules");
        String productActivityPartyLevels = (String) context.get("productActivityPartyLevels");
        String productActivityAreas = (String) context.get("productActivityAreas");
        GenericValue userLogin = (GenericValue) context.get("userLogin");

        //创建productActivity
        Delegator delegator = dcx.getDelegator();

        List<GenericValue> allStore = new LinkedList<GenericValue>();
        GenericValue productActivity = delegator.makeValue("ProductActivity");
        productActivity.set("activityCode", activityCode);
        productActivity.set("activityAuditStatus", "ACTY_AUDIT_INIT");
        productActivity.set("activityName", activityName);
        productActivity.set("activityType", activityType);
        productActivity.set("publishDate", publishDate);
        productActivity.set("endDate", endDate);
        productActivity.set("activityStartDate", activityStartDate);
        productActivity.set("activityEndDate", activityEndDate);
        productActivity.set("limitQuantity", limitQuantity);
        productActivity.set("activityQuantity", activityQuantity);
        productActivity.set("scoreValue", scoreValue);
        productActivity.set("productPrice", productPrice);
        productActivity.set("activityPayType", activityPayType);
        productActivity.set("activityDesc", activityDesc);

        try {
            productActivity.set("activityId", activityId);
            allStore.add(productActivity);

            //修改对应商品
            GenericValue productActivityGoods = delegator.makeValue("ProductActivityGoods");
            productActivityGoods.set("productId", productId);
            productActivityGoods.set("shipmentType", shipmentType);
            productActivityGoods.set("isAnyReturn", isAnyReturn);
            productActivityGoods.set("isSupportOverTimeReturn", isSupportOverTimeReturn);
            productActivityGoods.set("isSupportScore", isSupportScore);
            productActivityGoods.set("isSupportReturnScore", isSupportReturnScore);
            productActivityGoods.set("isShowIndex", isShowIndex);
            productActivityGoods.set("virtualProductStartDate", virtualProductStartDate);
            productActivityGoods.set("virtualProductEndDate", virtualProductEndDate);
            productActivityGoods.set("activityId", activityId);
            allStore.add(productActivityGoods);


            //删除存在的促销ProductPromo
            //删除存在的促销ProductPromoRule
            //删除存在的促销ProductPromoRule
            //删除存在的促销ProductPromoCond
            //删除存在的促销ProductPromoAction
            //删除存在的促销ProductStorePromoAppl
            //删除存在的促销ProductGroupOrderRule

            List<GenericValue> o_productGroupOrderRules = delegator.findByAnd("ProductGroupOrderRule", UtilMisc.toMap("activityId", activityId));
            if (UtilValidate.isNotEmpty(o_productGroupOrderRules)) {
                for (GenericValue productOrderRlue : o_productGroupOrderRules) {
                    String o_promoId = (String) productOrderRlue.get("productPromoId");

                    delegator.removeByAnd("ProductPromoProduct", UtilMisc.toMap("productPromoId", o_promoId));
                    delegator.removeByAnd("ProductPromoCond", UtilMisc.toMap("productPromoId", o_promoId));
                    delegator.removeByAnd("ProductPromoAction", UtilMisc.toMap("productPromoId", o_promoId));
                    delegator.removeByAnd("ProductPromoRule", UtilMisc.toMap("productPromoId", o_promoId));
                    List<GenericValue> appls = delegator.findByAnd("ProductStorePromoAppl", UtilMisc.toMap("productPromoId", o_promoId));
                    if (UtilValidate.isNotEmpty(appls)) {
                        for (int i = 0; i < appls.size(); i++) {
                            GenericValue genericValue = appls.get(i);
                            delegator.removeValue(genericValue);
                        }
                    }
                    delegator.removeByAnd("ProductGroupOrderRule", UtilMisc.toMap("productPromoId", o_promoId));
                    delegator.removeByAnd("ProductPromo", UtilMisc.toMap("productPromoId", o_promoId));
                }
            }

            //创建对应ProductGroupOrderRule
            if (UtilValidate.isNotEmpty(productGroupOrderRules)) {
                List<String> rules = StringUtil.split(productGroupOrderRules, ",");
                if (UtilValidate.isNotEmpty(rules)) {
                    for (int i = 0; i < rules.size(); i++) {
                        String rule = rules.get(i);
                        if (UtilValidate.isNotEmpty(rule)) {
                            List<String> ruleObj = StringUtil.split(rule, ":");
                            if (UtilValidate.isNotEmpty(ruleObj)) {
                                String seq = ruleObj.get(0);
                                String quantity = ruleObj.get(1);
                                Long lQuantity = Long.parseLong(quantity);
                                BigDecimal price = new BigDecimal(ruleObj.get(2));

                                //创建团购促销
                                String promoName = activityName + ":促销" + (i + 1);
                                String promoText = promoName;
                                String userEntered = "Y";
                                String showToCustomer = "Y";
                                String requireCode = "N";
                                Long useLimitPerOrder = 1l;
                                Long useLimitPerCustomer = 1l;
                                Map inputParams = FastMap.newInstance();
                                inputParams.put("promoName", promoName);
                                inputParams.put("promoText", promoText);
                                inputParams.put("userEntered", userEntered);
                                inputParams.put("showToCustomer", showToCustomer);
                                inputParams.put("requireCode", requireCode);
                                inputParams.put("useLimitPerOrder", useLimitPerOrder);
                                inputParams.put("useLimitPerCustomer", useLimitPerCustomer);
                                inputParams.put("userLogin", userLogin);

                                try {
                                    Map<String, Object> callback = dispatcher.runSync("createProductPromo", inputParams);
                                    if (ServiceUtil.isError(callback)) {
                                        return result;
                                    }
                                    String productPromoId = (String) callback.get("productPromoId");
                                    //创建createProductPromoRule
                                    String ruleName = activityName + ":促销条件" + (i + 1);
                                    inputParams = FastMap.newInstance();
                                    inputParams.put("productPromoId", productPromoId);
                                    inputParams.put("ruleName", ruleName);
                                    inputParams.put("userLogin", userLogin);
                                    callback = dispatcher.runSync("createProductPromoRule", inputParams);
                                    if (ServiceUtil.isError(callback)) {
                                        return result;
                                    }
                                    String RQuantity = null;
                                    String productPromoRuleId = (String) callback.get("productPromoRuleId");
//                                    获取阶梯价的next值
                                    if (i < (rules.size() - 1)) {
                                        if (UtilValidate.isNotEmpty(rules.get(i + 1))) {
                                            String nextRule = rules.get(i + 1);
                                            List<String> nextRuleObj = StringUtil.split(nextRule, ":");
                                            if (UtilValidate.isNotEmpty(nextRuleObj)) {
                                                RQuantity = nextRuleObj.get(1);

                                            }
                                        }
                                    }
                                    //创建createProductPromoCond
                                    inputParams = FastMap.newInstance();
                                    inputParams.put("productPromoRuleId", productPromoRuleId);
                                    inputParams.put("productPromoId", productPromoId);

                                    inputParams.put("inputParamEnumId", "PPIP_GRPODR_TOTAL");
                                    inputParams.put("operatorEnumId", "PPC_BTW");
                                    inputParams.put("condValue", lQuantity.toString());
                                    inputParams.put("otherValue", RQuantity);
                                    inputParams.put("userLogin", userLogin);
                                    callback = dispatcher.runSync("createProductPromoCond", inputParams);
                                    if (ServiceUtil.isError(callback)) {
                                        return result;
                                    }
                                    String productPromoCondSeqId = (String) callback.get("productPromoCondSeqId");
                                    //创建条件产品
                                    inputParams = FastMap.newInstance();
                                    inputParams.put("productPromoRuleId", productPromoRuleId);
                                    inputParams.put("productPromoId", productPromoId);
                                    inputParams.put("productPromoCondSeqId", productPromoCondSeqId);
                                    inputParams.put("productPromoActionSeqId", "_NA_");
                                    inputParams.put("productId", productId);
                                    inputParams.put("productPromoApplEnumId", "PPPA_INCLUDE");
                                    inputParams.put("userLogin", userLogin);
                                    callback = dispatcher.runSync("createProductPromoProduct", inputParams);
                                    if (ServiceUtil.isError(callback)) {
                                        return result;
                                    }
                                    //创建createProductPromoAction
                                    inputParams = FastMap.newInstance();
                                    inputParams.put("productPromoRuleId", productPromoRuleId);
                                    inputParams.put("productPromoId", productPromoId);
                                    inputParams.put("productPromoActionEnumId", "PROMO_PROD_ASGPC");
                                    inputParams.put("orderAdjustmentTypeId", "PROMOTION_ADJUSTMENT");
                                    inputParams.put("quantity", BigDecimal.ONE);
                                    inputParams.put("amount", price);
                                    inputParams.put("useCartQuantity", "N");
                                    inputParams.put("userLogin", userLogin);
                                    callback = dispatcher.runSync("createProductPromoAction", inputParams);
                                    if (ServiceUtil.isError(callback)) {
                                        return result;
                                    }
                                    //创建ProductPromoProduct
                                    String productPromoActionSeqId = (String) callback.get("productPromoActionSeqId");
                                    inputParams = FastMap.newInstance();
                                    inputParams.put("productPromoRuleId", productPromoRuleId);
                                    inputParams.put("productPromoId", productPromoId);
                                    inputParams.put("productPromoCondSeqId", "_NA_");
                                    inputParams.put("productPromoActionSeqId", productPromoActionSeqId);
                                    inputParams.put("productId", productId);
                                    inputParams.put("productPromoApplEnumId", "PPPA_INCLUDE");
                                    inputParams.put("userLogin", userLogin);
                                    callback = dispatcher.runSync("createProductPromoProduct", inputParams);
                                    if (ServiceUtil.isError(callback)) {
                                        return result;
                                    }
                                    //创建促销对应店铺createProductStorePromoAppl

                                    if (UtilValidate.isNotEmpty(productStoreIds)) {
                                        List storeIds = StringUtil.split(productStoreIds, ",");
                                        for (int j = 0; j < storeIds.size(); j++) {
                                            String productStoreId = (String) storeIds.get(j);
                                            inputParams = FastMap.newInstance();
                                            inputParams.put("productStoreId", productStoreId);
                                            inputParams.put("productPromoId", productPromoId);
                                            inputParams.put("fromDate", UtilDateTime.nowTimestamp());
                                            inputParams.put("userLogin", userLogin);
                                            callback = dispatcher.runSync("createProductStorePromoAppl", inputParams);
                                            if (ServiceUtil.isError(callback)) {
                                                return result;
                                            }

                                        }

                                    }
                                    //创建促销与活动关系
                                    GenericValue productGroupOrderRule = delegator.makeValue("ProductGroupOrderRule");
                                    productGroupOrderRule.set("activityId", activityId);
                                    productGroupOrderRule.set("seqId", seq);
                                    productGroupOrderRule.set("orderQuantity", lQuantity);
                                    productGroupOrderRule.set("orderPrice", price);
                                    productGroupOrderRule.set("productPromoId", productPromoId);
                                    allStore.add(productGroupOrderRule);

                                } catch (GenericServiceException e) {
                                    return ServiceUtil.returnError(e.getMessage());
                                }
                            }

                        }
                    }
                }
            }
            delegator.removeByAnd("ProductActivityPartyLevel", UtilMisc.toMap("activityId", activityId));
            //创建对应ProductPartyLevel
            if (UtilValidate.isNotEmpty(productActivityPartyLevels)) {
                List<String> levels = StringUtil.split(productActivityPartyLevels, ",");
                if (UtilValidate.isNotEmpty(levels)) {
                    for (int i = 0; i < levels.size(); i++) {
                        String level = levels.get(i);
                        List<String> levelObj = StringUtil.split(level, ":");
                        if (UtilValidate.isNotEmpty(levelObj)) {
                            String levelId = levelObj.get(0);
                            String levelName = levelObj.get(1);
                            GenericValue activityPartyLevel = delegator.makeValue("ProductActivityPartyLevel");
                            activityPartyLevel.set("levelId", levelId);
                            activityPartyLevel.set("levelName", levelName);
                            activityPartyLevel.set("activityId", activityId);
                            allStore.add(activityPartyLevel);
                        }
                    }
                }
            }

            delegator.removeByAnd("ProductActivityArea", UtilMisc.toMap("activityId", activityId));
            //productActivityAreas
            if (UtilValidate.isNotEmpty(productActivityAreas)) {
                List<String> areas = StringUtil.split(productActivityAreas, ",");
                if (UtilValidate.isNotEmpty(areas)) {
                    for (int i = 0; i < areas.size(); i++) {
                        String area = areas.get(i);
                        List<String> areaObj = StringUtil.split(area, ":");
                        if (UtilValidate.isNotEmpty(areaObj)) {
                            String geoId = areaObj.get(0);
                            String geoName = areaObj.get(1);
                            GenericValue productActivityArea = delegator.makeValue("ProductActivityArea");
                            productActivityArea.set("communityId", geoId);
                            productActivityArea.set("communityName", geoName);
                            productActivityArea.set("activityId", activityId);
                            allStore.add(productActivityArea);
                        }
                    }
                }
            }

            //
            delegator.storeAll(allStore);

        } catch (GenericEntityException e) {

            return ServiceUtil.returnError(e.getMessage());
        }
        result.put("activityId", activityId);

        return result;
    }

    /**
     * 查找活动
     *
     * @param dcx
     * @param context
     * @return
     */
    public static Map<String, Object> findActivities(DispatchContext dcx, Map<String, ? extends Object> context) {

        Map<String, Object> result = ServiceUtil.returnSuccess();
        String activityCode = (String) context.get("activityCode");
        String activityName = (String) context.get("activityName");
        String activityStartDate = (String) context.get("activityStartDate");
        String activityEndDate = (String) context.get("activityEndDate");
        String activityType = (String) context.get("activityType");
        String activityAuditStatus = (String) context.get("activityAuditStatus");
        Delegator delegator = dcx.getDelegator();


        Locale locale = (Locale) context.get("locale");
        String lookupFlag = (String) context.get("lookupFlag");
        if (lookupFlag == null) lookupFlag = "N";
        String orderFiled = (String) context.get("ORDER_FILED");
        String orderFiledBy = (String) context.get("ORDER_BY");

        result.put("orderFiled", orderFiled == null ? "" : orderFiled);
        result.put("orderBy", orderFiledBy == null ? "" : orderFiledBy);


        List<GenericValue> activityList = FastList.newInstance();
        int listSize = 0;
        int lowIndex = 0;
        int highIndex = 0;
        // set the page parameters
        int viewIndex = 0;
        try {
            viewIndex = Integer.parseInt((String) context.get("VIEW_INDEX"));
        } catch (Exception e) {
            viewIndex = 0;
        }
        result.put("viewIndex", Integer.valueOf(viewIndex));

        int viewSize = 20;
        try {
            viewSize = Integer.parseInt((String) context.get("VIEW_SIZE"));
        } catch (Exception e) {
            viewSize = 20;
        }
        result.put("viewSize", Integer.valueOf(viewSize));

        List<String> fieldsToSelect = FastList.newInstance();
        fieldsToSelect.add("activityId");
        fieldsToSelect.add("activityCode");
        fieldsToSelect.add("activityName");
        fieldsToSelect.add("activityStartDate");
        fieldsToSelect.add("activityEndDate");
        fieldsToSelect.add("activityAuditStatus");
        fieldsToSelect.add("activityType");
        fieldsToSelect.add("hasGroup");
        fieldsToSelect.add("leaveQuantity");
        List<String> orderBy = FastList.newInstance();
        if (UtilValidate.isNotEmpty(orderFiled)) {
            orderBy.add(orderFiled + " " + orderFiledBy);
        }
        // blank param list
        List<EntityCondition> andExprs = FastList.newInstance();
        EntityCondition mainCond = null;
        String paramList = "";
        if (UtilValidate.isNotEmpty(activityCode)) {
            paramList = paramList + "&activityCode=" + activityCode;
            andExprs.add(EntityCondition.makeCondition(EntityFunction.UPPER_FIELD("activityCode"), EntityOperator.LIKE, EntityFunction.UPPER("%" + activityCode + "%")));
        }
        if (UtilValidate.isNotEmpty(activityType)) {
            andExprs.add(EntityCondition.makeCondition(EntityFunction.UPPER_FIELD("activityType"), EntityOperator.EQUALS, activityType));
        }
        if (UtilValidate.isNotEmpty(activityName)) {
            paramList = paramList + "&activityName=" + activityName;
            andExprs.add(EntityCondition.makeCondition(EntityFunction.UPPER_FIELD("activityName"), EntityOperator.LIKE, EntityFunction.UPPER("%" + activityName + "%")));
        }
        if (UtilValidate.isNotEmpty(activityAuditStatus)) {
            paramList = paramList + "&activityAuditStatus=" + activityAuditStatus;
            Timestamp noTime = UtilDateTime.nowTimestamp();
            if (activityAuditStatus.equals("ACTY_AUDIT_PUBING")) {
                //待发布（auditStatus为审批通过并且系统当前时间小于发布时间）
                andExprs.add(EntityCondition.makeCondition(EntityFunction.UPPER_FIELD("activityAuditStatus"), EntityOperator.EQUALS, "ACTY_AUDIT_PASS"));
                andExprs.add(EntityCondition.makeCondition(EntityFunction.UPPER_FIELD("publishDate"), EntityOperator.GREATER_THAN_EQUAL_TO, noTime));
            } else if (activityAuditStatus.equals("ACTY_AUDIT_UNBEGIN")) {
                //未开始（auditStatus为审批通过并且系统当前时间小于销售开始时间）
                andExprs.add(EntityCondition.makeCondition(EntityFunction.UPPER_FIELD("activityAuditStatus"), EntityOperator.EQUALS, "ACTY_AUDIT_PASS"));
                andExprs.add(EntityCondition.makeCondition(EntityFunction.UPPER_FIELD("activityStartDate"), EntityOperator.GREATER_THAN_EQUAL_TO, noTime));
            } else if (activityAuditStatus.equals("ACTY_AUDIT_DOING")) {
                //进行中（auditStatus为审批通过并且系统当前时间大于等于销售开始时间小于销售结束时间）
                andExprs.add(EntityCondition.makeCondition(EntityFunction.UPPER_FIELD("activityAuditStatus"), EntityOperator.EQUALS, "ACTY_AUDIT_PASS"));
                andExprs.add(EntityCondition.makeCondition(EntityFunction.UPPER_FIELD("activityStartDate"), EntityOperator.LESS_THAN, noTime));
                andExprs.add(EntityCondition.makeCondition(EntityFunction.UPPER_FIELD("activityEndDate"), EntityOperator.GREATER_THAN_EQUAL_TO, noTime));
            } else if (activityAuditStatus.equals("ACTY_AUDIT_END")) {
                //已结束（auditStatus为审批通过并且系统当前时间大于等于销售结束时间小于下架时间）
                andExprs.add(EntityCondition.makeCondition(EntityFunction.UPPER_FIELD("activityAuditStatus"), EntityOperator.EQUALS, "ACTY_AUDIT_PASS"));
                andExprs.add(EntityCondition.makeCondition(EntityFunction.UPPER_FIELD("activityEndDate"), EntityOperator.LESS_THAN, noTime));
                andExprs.add(EntityCondition.makeCondition(EntityFunction.UPPER_FIELD("endDate"), EntityOperator.GREATER_THAN_EQUAL_TO, noTime));
            } else if (activityAuditStatus.equals("ACTY_AUDIT_OFF")) {
                //已下架（auditStatus为审批通过并且系统当前时间大于等于销售开始时间小于销售结束时间）
                andExprs.add(EntityCondition.makeCondition(EntityFunction.UPPER_FIELD("activityAuditStatus"), EntityOperator.EQUALS, "ACTY_AUDIT_PASS"));
                andExprs.add(EntityCondition.makeCondition(EntityFunction.UPPER_FIELD("endDate"), EntityOperator.LESS_THAN, noTime));
            } else {
                andExprs.add(EntityCondition.makeCondition(EntityFunction.UPPER_FIELD("activityAuditStatus"), EntityOperator.EQUALS, activityAuditStatus));
            }
        }
        if (UtilValidate.isNotEmpty(activityStartDate)) {
            paramList = paramList + "&activityStartDate=" + activityStartDate;
            Object startDate = null;
            try {
                startDate = ObjectType.simpleTypeConvert(activityStartDate, "Timestamp", null, (TimeZone) context.get("timeZone"), (Locale) context.get("locale"), true);
            } catch (GeneralException e) {
                return ServiceUtil.returnError(e.getMessage());
            }
            andExprs.add(EntityCondition.makeCondition(EntityFunction.UPPER_FIELD("activityStartDate"), EntityOperator.GREATER_THAN_EQUAL_TO, startDate));
        }
        if (UtilValidate.isNotEmpty(activityEndDate)) {
            paramList = paramList + "&activityEndDate=" + activityEndDate;
            Object endDate = null;
            try {
                endDate = ObjectType.simpleTypeConvert(activityEndDate, "Timestamp", null, (TimeZone) context.get("timeZone"), (Locale) context.get("locale"), true);
            } catch (GeneralException e) {
                return ServiceUtil.returnError(e.getMessage());
            }
            andExprs.add(EntityCondition.makeCondition(EntityFunction.UPPER_FIELD("activityEndDate"), EntityOperator.LESS_THAN_EQUAL_TO, endDate));
        }
        List<GenericValue> activities = null;
        // build the main condition
        if (andExprs.size() > 0) mainCond = EntityCondition.makeCondition(andExprs, EntityOperator.AND);

        if (lookupFlag.equals("Y")) {
            try {
                // get the indexes for the partial list
                // get the indexes for the partial list
                lowIndex = viewIndex * viewSize + 1;
                highIndex = (viewIndex + 1) * viewSize;

                // set distinct on so we only get one row per order
                EntityFindOptions findOpts = new EntityFindOptions(true, EntityFindOptions.TYPE_SCROLL_INSENSITIVE, EntityFindOptions.CONCUR_READ_ONLY, -1, highIndex, true);
                // using list iterator
                EntityListIterator pli = delegator.find("ProductActivity", mainCond, null, UtilMisc.makeSetWritable(fieldsToSelect), orderBy, findOpts);

                // get the partial list for this page
                activityList = pli.getPartialList(lowIndex, viewSize);

                // attempt to get the full size
                listSize = pli.getResultsSizeAfterPartialList();
                if (highIndex > listSize) {
                    highIndex = listSize;
                }

                // close the list iterator
                pli.close();
            } catch (GenericEntityException e) {
                String errMsg = "Failure in party find operation, rolling back transaction: " + e.toString();
                Debug.logError(e, errMsg);
                return ServiceUtil.returnError(e.getMessage());
            }
        } else {
            listSize = 0;
        }
        result.put("groupList", activityList);
        result.put("activityCode", activityCode);
        result.put("activityName", activityName);
        result.put("activityStartDate", activityStartDate);
        result.put("activityEndDate", activityEndDate);
        result.put("activityAuditStatus", activityAuditStatus);

        result.put("groupListSize", Integer.valueOf(listSize));
        result.put("paramList", paramList);
        result.put("highIndex", Integer.valueOf(highIndex));
        result.put("lowIndex", Integer.valueOf(lowIndex));

        return result;
    }


    /**
     * 删除活动
     *
     * @param dcx
     * @param context
     * @return
     */
    public static Map<String, Object> deleteGroupOrder(DispatchContext dcx, Map<String, ? extends Object> context) {

        Map<String, Object> result = ServiceUtil.returnSuccess();
        String activityId = (String) context.get("activityId");
        Delegator delegator = dcx.getDelegator();
        //删除存在的促销ProductPromo
        //删除存在的促销ProductPromoRule
        //删除存在的促销ProductPromoRule
        //删除存在的促销ProductPromoCond
        //删除存在的促销ProductPromoAction
        //删除存在的促销ProductStorePromoAppl
        //删除存在的促销ProductGroupOrderRule

        List<GenericValue> o_productGroupOrderRules = null;
        try {
            o_productGroupOrderRules = delegator.findByAnd("ProductGroupOrderRule", UtilMisc.toMap("activityId", activityId));

            if (UtilValidate.isNotEmpty(o_productGroupOrderRules)) {
                for (GenericValue productOrderRlue : o_productGroupOrderRules) {
                    String o_promoId = (String) productOrderRlue.get("productPromoId");

                    delegator.removeByAnd("ProductPromoProduct", UtilMisc.toMap("productPromoId", o_promoId));
                    delegator.removeByAnd("ProductPromoCond", UtilMisc.toMap("productPromoId", o_promoId));
                    delegator.removeByAnd("ProductPromoAction", UtilMisc.toMap("productPromoId", o_promoId));
                    delegator.removeByAnd("ProductPromoRule", UtilMisc.toMap("productPromoId", o_promoId));
                    List<GenericValue> appls = delegator.findByAnd("ProductStorePromoAppl", UtilMisc.toMap("productPromoId", o_promoId));
                    if (UtilValidate.isNotEmpty(appls)) {
                        for (int i = 0; i < appls.size(); i++) {
                            GenericValue genericValue = appls.get(i);
                            delegator.removeValue(genericValue);
                        }
                    }
                    delegator.removeByAnd("ProductGroupOrderRule", UtilMisc.toMap("productPromoId", o_promoId));
                    delegator.removeByAnd("ProductPromo", UtilMisc.toMap("productPromoId", o_promoId));
                }
            }
            delegator.removeByAnd("ProductActivityGoods", UtilMisc.toMap("activityId", activityId));
            delegator.removeByAnd("ProductActivityPartyLevel", UtilMisc.toMap("activityId", activityId));
            delegator.removeByAnd("ProductActivityArea", UtilMisc.toMap("activityId", activityId));
            delegator.removeByAnd("ProductActivity", UtilMisc.toMap("activityId", activityId));
        } catch (GenericEntityException e) {
            e.printStackTrace();
        }
        return result;
    }

    /**
     * 根据活动ID活动有效的活动订单数
     *
     * @param dcx
     * @param context
     * @return活动订单数
     */
    public static int orderNum = 0;

    public static Map<String, Object> getAvailableGroupOrderQuantity(DispatchContext dcx, Map<String, ? extends Object> context) {
        Map<String, Object> result = ServiceUtil.returnSuccess();
        result.put("quantities", 50);
        return result;
    }

    /**
     * 查询活动
     * d_productStoreName
     * d_activityTypeName
     * d_activityAuditStatusName
     * d_activityCode
     * d_activityName
     * d_publicDate
     * d_endDate
     * d_activityStartDate
     * d_activityEndDate
     * d_limitQuantity
     * d_activityQuantity
     * d_productName
     * d_shipmentTypeName
     * d_scoreValue
     * d_activityPayTypeName
     * d_productPrice
     * d_virtualProductStartDate
     * d_isAnyReturn
     * d_isSupportOverTimeReturn
     * d_isSupportScore
     * d_isSupportReturnScore
     * d_isShowIndex
     * d_activityDesc
     * d_productGroupOrderRules
     * d_productActivityPartyLevels
     * d_productActivityAreas
     *
     * @param dcx
     * @param context
     * @return
     */
    public static Map<String, Object> productActivityDetail(DispatchContext dcx, Map<String, ? extends Object> context) {
        Map<String, Object> result = ServiceUtil.returnSuccess();
        Locale locale = (Locale) context.get("locale");
        String productActivityId = (String) context.get("activityId");
        TimeZone timezone = (TimeZone) context.get("timeZone");
        Delegator delegator = dcx.getDelegator();
        String d_productStoreName;
        String d_activityTypeName;
        String activityType;
        String d_activityAuditStatusName;
        String d_activityCode;
        String d_activityName;
        String d_publishDate;
        String d_endDate;
        String d_activityStartDate;
        String d_activityEndDate;
        String d_limitQuantity;
        String d_activityQuantity;
        String d_productName = null;
        String d_shipmentTypeName = null;
        String d_scoreValue;
        String d_activityPayTypeName;
        String d_productPrice;
        String d_virtualProductStartDate = null;
        String d_virtualProductEndDate = null;
        String d_isAnyReturn = null;
        String d_isSupportOverTimeReturn = null;
        String d_isSupportScore = null;
        String d_isSupportReturnScore = null;
        String d_isShowIndex = null;
        String d_activityDesc;
        String activityAuditStatus;
        String shipmentType = null;
        String activityPayType = null;
        List<GenericValue> d_productGroupOrderRules = null;
        List<GenericValue> d_productActivityPartyLevels = null;
        List<GenericValue> d_productActivityAreas = null;
        List<String> stores = new ArrayList<String>();
        String productId = null;
        try {
            GenericValue productActivity = delegator.findByPrimaryKey("ProductActivity", UtilMisc.toMap("activityId", productActivityId));
            activityType = (String) productActivity.get("activityType");
            GenericValue activityEnum = delegator.findByPrimaryKey("Enumeration", UtilMisc.toMap("enumId", activityType));
            d_activityTypeName = (String) activityEnum.get("description", locale);

            activityAuditStatus = (String) productActivity.get("activityAuditStatus");

            GenericValue activityAuditEnum = delegator.findByPrimaryKey("Enumeration", UtilMisc.toMap("enumId", activityAuditStatus));
            d_activityAuditStatusName = (String) activityAuditEnum.get("description", locale);

            d_activityCode = (String) productActivity.get("activityCode");
            d_activityName = (String) productActivity.get("activityName");
            d_publishDate = UtilDateTime.timeStampToString((Timestamp) productActivity.get("publishDate"), "yyyy-mm-dd HH:mm", timezone, locale);
            d_endDate = UtilDateTime.timeStampToString((Timestamp) productActivity.get("activityEndDate"), "yyyy-mm-dd HH:mm", timezone, locale);
            d_activityStartDate = UtilDateTime.timeStampToString((Timestamp) productActivity.get("activityStartDate"), "yyyy-mm-dd HH:mm", timezone, locale);
            d_activityEndDate = UtilDateTime.timeStampToString((Timestamp) productActivity.get("activityEndDate"), "yyyy-mm-dd HH:mm", timezone, locale);
            d_limitQuantity = productActivity.get("limitQuantity").toString();
            d_activityQuantity = productActivity.get("activityQuantity").toString();
            d_scoreValue = productActivity.get("scoreValue").toString();

            activityPayType = (String) productActivity.get("activityPayType");
            GenericValue payEnum = delegator.findByPrimaryKey("Enumeration", UtilMisc.toMap("enumId", activityPayType));
            d_activityPayTypeName = (String) payEnum.get("description", locale);

            d_productPrice = productActivity.get("productPrice").toString();
            d_activityDesc = (String) productActivity.get("activityDesc");

            //对应产品

            List<GenericValue> goods = delegator.findByAnd("ProductActivityGoods", UtilMisc.toMap("activityId", productActivityId));
            if (UtilValidate.isNotEmpty(goods)) {
                for (GenericValue good : goods) {
                    productId = (String) good.get("productId");
                    GenericValue product = delegator.findByPrimaryKey("Product", UtilMisc.toMap("productId", productId));
                    d_productName = (String) product.get("productName");
                    shipmentType = (String) good.get("shipmentType");
                    GenericValue shipEnum = delegator.findByPrimaryKey("Enumeration", UtilMisc.toMap("enumId", shipmentType));
                    if (UtilValidate.isNotEmpty(shipEnum))
                        d_shipmentTypeName = (String) shipEnum.get("description", locale);
                    if (UtilValidate.isNotEmpty(good.get("virtualProductStartDate")))
                        d_virtualProductStartDate = UtilDateTime.timeStampToString((Timestamp) good.get("virtualProductStartDate"), "yyyy-mm-dd HH:mm", timezone, locale);
                    if (UtilValidate.isNotEmpty(good.get("virtualProductEndDate")))
                        d_virtualProductEndDate = UtilDateTime.timeStampToString((Timestamp) good.get("virtualProductEndDate"), "yyyy-mm-dd HH:mm", timezone, locale);
                    d_isAnyReturn = (String) good.get("isAnyReturn");
                    d_isSupportOverTimeReturn = (String) good.get("isSupportOverTimeReturn");
                    d_isSupportScore = (String) good.get("isSupportScore");
                    d_isSupportReturnScore = (String) good.get("isSupportReturnScore");
                    d_isShowIndex = (String) good.get("isShowIndex");

                }
            }

            //对应店铺
//            productActivity.getRelated()
            List<GenericValue> promos = delegator.findByAnd("ProductGroupOrderRule", UtilMisc.toMap("activityId", productActivityId));

            d_productStoreName = "";
            if (UtilValidate.isNotEmpty(promos)) {
                for (GenericValue promo : promos) {
                    String productPromoId = (String) promo.get("productPromoId");
                    List<GenericValue> appls = delegator.findByAnd("ProductStorePromoAppl", UtilMisc.toMap("productPromoId", productPromoId));
                    appls = EntityUtil.filterByDate(appls);
                    if (UtilValidate.isNotEmpty(appls)) {
                        for (int i = 0; i < appls.size(); i++) {
                            GenericValue app = appls.get(i);
                            GenericValue store = app.getRelatedOne("ProductStore");
                            d_productStoreName = store.get("storeName") + ",";
                            stores.add((String) store.get("productStoreId"));
                        }
                    }
                }
            }

            //d_productGroupOrderRules
            d_productGroupOrderRules = delegator.findByAnd("ProductGroupOrderRule", UtilMisc.toMap("activityId", productActivityId));

            //d_productActivityPartyLevels
            d_productActivityPartyLevels = delegator.findByAnd("ProductActivityPartyLevel", UtilMisc.toMap("activityId", productActivityId));
            //d_productActivityAreas
            d_productActivityAreas = delegator.findByAnd("ProductActivityArea", UtilMisc.toMap("activityId", productActivityId));

        } catch (GenericEntityException e) {
            return ServiceUtil.returnError(e.getMessage());
        }

        result.put("d_productStoreName", d_productStoreName);
        result.put("d_activityTypeName", d_activityTypeName);
        result.put("activityType", activityType);
        result.put("d_activityAuditStatusName", d_activityAuditStatusName);
        result.put("d_activityCode", d_activityCode);
        result.put("d_activityName", d_activityName);
        result.put("d_publishDate", d_publishDate);
        result.put("d_endDate", d_endDate);
        result.put("d_activityStartDate", d_activityStartDate);
        result.put("d_activityEndDate", d_activityEndDate);
        result.put("d_limitQuantity", d_limitQuantity);
        result.put("d_activityQuantity", d_activityQuantity);
        result.put("d_productName", d_productName);
        result.put("d_shipmentTypeName", d_shipmentTypeName);
        result.put("shipmentType", shipmentType);
        result.put("d_scoreValue", d_scoreValue);
        result.put("d_activityPayTypeName", d_activityPayTypeName);
        result.put("d_productPrice", d_productPrice);
        result.put("d_virtualProductStartDate", d_virtualProductStartDate);
        result.put("d_isAnyReturn", d_isAnyReturn);
        result.put("d_isSupportOverTimeReturn", d_isSupportOverTimeReturn);
        result.put("d_isSupportScore", d_isSupportScore);
        result.put("d_isSupportReturnScore", d_isSupportReturnScore);
        result.put("d_isShowIndex", d_isShowIndex);
        result.put("d_activityDesc", d_activityDesc);
        result.put("d_virtualProductEndDate", d_virtualProductEndDate);
        result.put("activityAuditStatus", activityAuditStatus);
        result.put("d_productGroupOrderRules", d_productGroupOrderRules);
        result.put("d_productActivityPartyLevels", d_productActivityPartyLevels);
        result.put("d_productActivityAreas", d_productActivityAreas);

        result.put("productStoreIds", stores);
        result.put("productId", productId);
        result.put("activityPayType", activityPayType);
        return result;
    }

    /**
     * 审批活动
     *
     * @param dcx
     * @param context
     * @return
     */
    public static Map<String, Object> auditGroupOrder(DispatchContext dcx, Map<String, ? extends Object> context) {

        Map<String, Object> result = ServiceUtil.returnSuccess();
        String activityId = (String) context.get("activityId");
        String pass = (String) context.get("pass");
        Delegator delegator = dcx.getDelegator();

        try {
            GenericValue productActivity = delegator.findByPrimaryKey("ProductActivity", UtilMisc.toMap("activityId", activityId));

            if (UtilValidate.isNotEmpty(productActivity)) {
                if (pass != null && pass.equals("Y")) {
                    productActivity.set("activityAuditStatus", "ACTY_AUDIT_PASS");
                } else {
                    productActivity.set("activityAuditStatus", "ACTY_AUDIT_NOPASS");
                }
                productActivity.store();
            }

        } catch (GenericEntityException e) {
            e.printStackTrace();
        }
        return result;
    }


    //    begin秒杀
    public Map<String, Object> addSecKill(DispatchContext dcx, Map<String, ? extends Object> context) {
        LocalDispatcher dispatcher = dcx.getDispatcher();
        Map<String, Object> result = ServiceUtil.returnSuccess();
        String productStoreIds = (String) context.get("productStoreIds");
        String activityCode = (String) context.get("activityCode");
        String activityAuditStatus = (String) context.get("activityAuditStatus");
        String activityType = (String) context.get("activityType");
        String activityName = (String) context.get("activityName");
        Timestamp publishDate = (Timestamp) context.get("publishDate");
        Timestamp endDate = (Timestamp) context.get("endDate");
        Timestamp activityStartDate = (Timestamp) context.get("activityStartDate");
        Timestamp activityEndDate = (Timestamp) context.get("activityEndDate");
        Long limitQuantity = (Long) context.get("limitQuantity");
        Long activityQuantity = (Long) context.get("activityQuantity");
        Long scoreValue = (Long) context.get("scoreValue");
        String activityPayType = (String) context.get("activityPayType");
        String activityDesc = (String) context.get("activityDesc");
        BigDecimal productPrice = (BigDecimal) context.get("productPrice");

        String productId = (String) context.get("productId");
        String shipmentType = (String) context.get("shipmentType");
        Timestamp virtualProductStartDate = (Timestamp) context.get("virtualProductStartDate");
        Timestamp virtualProductEndDate = (Timestamp) context.get("virtualProductEndDate");
        String isAnyReturn = (String) context.get("isAnyReturn");
        String isSupportOverTimeReturn = (String) context.get("isSupportOverTimeReturn");
        String isSupportScore = (String) context.get("isSupportScore");
        String isSupportReturnScore = (String) context.get("isSupportReturnScore");
        String isShowIndex = (String) context.get("isShowIndex");
        String productGroupOrderRules = (String) context.get("productGroupOrderRules");
        String productActivityPartyLevels = (String) context.get("productActivityPartyLevels");
        String productActivityAreas = (String) context.get("productActivityAreas");
        GenericValue userLogin = (GenericValue) context.get("userLogin");

        //创建productActivity
        Delegator delegator = dcx.getDelegator();

        List<GenericValue> allStore = new LinkedList<GenericValue>();
        GenericValue productActivity = delegator.makeValue("ProductActivity");
        productActivity.set("activityCode", activityCode);
        productActivity.set("activityAuditStatus", activityAuditStatus);
        productActivity.set("activityName", activityName);
        productActivity.set("activityType", activityType);
        productActivity.set("publishDate", publishDate);
        productActivity.set("endDate", endDate);
        productActivity.set("activityStartDate", activityStartDate);
        productActivity.set("activityEndDate", activityEndDate);
        productActivity.set("limitQuantity", limitQuantity);
        productActivity.set("activityQuantity", activityQuantity);
        productActivity.set("scoreValue", scoreValue);
        productActivity.set("productPrice", productPrice);
        productActivity.set("activityPayType", activityPayType);
        productActivity.set("activityDesc", activityDesc);

        String activityId = delegator.getNextSeqId("ProductActivity");

        try {
            productActivity.set("activityId", activityId);
            productActivity.create();

            //创建对应商品
            GenericValue productActivityGoods = delegator.makeValue("ProductActivityGoods");
            productActivityGoods.set("productId", productId);
            productActivityGoods.set("shipmentType", shipmentType);
            productActivityGoods.set("isAnyReturn", isAnyReturn);
            productActivityGoods.set("isSupportOverTimeReturn", isSupportOverTimeReturn);
            productActivityGoods.set("isSupportScore", isSupportScore);
            productActivityGoods.set("isSupportReturnScore", isSupportReturnScore);
            productActivityGoods.set("isShowIndex", isShowIndex);
            productActivityGoods.set("virtualProductStartDate", virtualProductStartDate);
            productActivityGoods.set("virtualProductEndDate", virtualProductEndDate);
            productActivityGoods.set("activityId", activityId);
            allStore.add(productActivityGoods);
            //创建对应ProductGroupOrderRule

            //创建团购促销
            String promoName = activityName + ":促销";
            String promoText = promoName;
            String userEntered = "Y";
            String showToCustomer = "Y";
            String requireCode = "N";
            Long useLimitPerOrder = 1l;
            Long useLimitPerCustomer = 1l;
            Map inputParams = FastMap.newInstance();
            inputParams.put("promoName", promoName);
            inputParams.put("promoText", promoText);
            inputParams.put("userEntered", userEntered);
            inputParams.put("showToCustomer", showToCustomer);
            inputParams.put("requireCode", requireCode);
            inputParams.put("useLimitPerOrder", useLimitPerOrder);
            inputParams.put("useLimitPerCustomer", useLimitPerCustomer);
            inputParams.put("userLogin", userLogin);
            try {
                Map<String, Object> callback = dispatcher.runSync("createProductPromo", inputParams);
                if (ServiceUtil.isError(callback)) {
                    return result;
                }
                String productPromoId = (String) callback.get("productPromoId");
                //创建createProductPromoRule
                String ruleName = activityName + ":促销条件";
                inputParams = FastMap.newInstance();
                inputParams.put("productPromoId", productPromoId);
                inputParams.put("ruleName", ruleName);
                inputParams.put("userLogin", userLogin);
                callback = dispatcher.runSync("createProductPromoRule", inputParams);
                if (ServiceUtil.isError(callback)) {
                    return result;
                }

                String productPromoRuleId = (String) callback.get("productPromoRuleId");

                //创建createProductPromoCond
                inputParams = FastMap.newInstance();
                inputParams.put("productPromoRuleId", productPromoRuleId);
                inputParams.put("productPromoId", productPromoId);

                inputParams.put("inputParamEnumId", "SALE_TIME_BTW");
                inputParams.put("operatorEnumId", "PPC_BTW");
                inputParams.put("condValue", activityStartDate.toString());
                inputParams.put("otherValue", activityEndDate.toString());
                inputParams.put("userLogin", userLogin);
                callback = dispatcher.runSync("createProductPromoCond", inputParams);
                if (ServiceUtil.isError(callback)) {
                    return result;
                }
                String productPromoCondSeqId = (String) callback.get("productPromoCondSeqId");
                //创建条件产品
                inputParams = FastMap.newInstance();
                inputParams.put("productPromoRuleId", productPromoRuleId);
                inputParams.put("productPromoId", productPromoId);
                inputParams.put("productPromoCondSeqId", productPromoCondSeqId);
                inputParams.put("productPromoActionSeqId", "_NA_");
                inputParams.put("productId", productId);
                inputParams.put("productPromoApplEnumId", "PPPA_INCLUDE");
                inputParams.put("userLogin", userLogin);
                callback = dispatcher.runSync("createProductPromoProduct", inputParams);
                if (ServiceUtil.isError(callback)) {
                    return result;
                }
                //创建createProductPromoAction
                inputParams = FastMap.newInstance();
                inputParams.put("productPromoRuleId", productPromoRuleId);
                inputParams.put("productPromoId", productPromoId);
                inputParams.put("productPromoActionEnumId", "PROMO_PROD_ASGPC");
                inputParams.put("orderAdjustmentTypeId", "PROMOTION_ADJUSTMENT");
                inputParams.put("quantity", BigDecimal.ONE);
                inputParams.put("amount", productPrice);
                inputParams.put("useCartQuantity", "N");
                inputParams.put("userLogin", userLogin);
                callback = dispatcher.runSync("createProductPromoAction", inputParams);
                if (ServiceUtil.isError(callback)) {
                    return result;
                }
                //创建ProductPromoProduct
                String productPromoActionSeqId = (String) callback.get("productPromoActionSeqId");
                inputParams = FastMap.newInstance();
                inputParams.put("productPromoRuleId", productPromoRuleId);
                inputParams.put("productPromoId", productPromoId);
                inputParams.put("productPromoCondSeqId", "_NA_");
                inputParams.put("productPromoActionSeqId", productPromoActionSeqId);
                inputParams.put("productId", productId);
                inputParams.put("productPromoApplEnumId", "PPPA_INCLUDE");
                inputParams.put("userLogin", userLogin);
                callback = dispatcher.runSync("createProductPromoProduct", inputParams);
                if (ServiceUtil.isError(callback)) {
                    return result;
                }
                //创建促销对应店铺createProductStorePromoAppl

                if (UtilValidate.isNotEmpty(productStoreIds)) {
                    List storeIds = StringUtil.split(productStoreIds, ",");
                    for (int j = 0; j < storeIds.size(); j++) {
                        String productStoreId = (String) storeIds.get(j);
                        inputParams = FastMap.newInstance();
                        inputParams.put("productStoreId", productStoreId);
                        inputParams.put("productPromoId", productPromoId);
                        inputParams.put("fromDate", UtilDateTime.nowTimestamp());
                        inputParams.put("userLogin", userLogin);
                        callback = dispatcher.runSync("createProductStorePromoAppl", inputParams);
                        if (ServiceUtil.isError(callback)) {
                            return result;
                        }

                    }

                }

                //创建产品团购规则对应每个促销
                GenericValue productGroupOrderRule = delegator.makeValue("ProductGroupOrderRule");
                productGroupOrderRule.set("activityId", activityId);
                productGroupOrderRule.set("seqId", "001");
                productGroupOrderRule.set("orderQuantity", 1l);
                productGroupOrderRule.set("orderPrice", productPrice);
                productGroupOrderRule.set("productPromoId", productPromoId);
                allStore.add(productGroupOrderRule);

            } catch (GenericServiceException e) {
                return ServiceUtil.returnError(e.getMessage());
            }


            //创建对应ProductPartyLevel
            if (UtilValidate.isNotEmpty(productActivityPartyLevels)) {
                List<String> levels = StringUtil.split(productActivityPartyLevels, ",");
                if (UtilValidate.isNotEmpty(levels)) {
                    for (int i = 0; i < levels.size(); i++) {
                        String level = levels.get(i);
                        List<String> levelObj = StringUtil.split(level, ":");
                        if (UtilValidate.isNotEmpty(levelObj)) {
                            String levelId = levelObj.get(0);
                            String levelName = levelObj.get(1);
                            GenericValue activityPartyLevel = delegator.makeValue("ProductActivityPartyLevel");
                            activityPartyLevel.set("levelId", levelId);
                            activityPartyLevel.set("levelName", levelName);
                            activityPartyLevel.set("activityId", activityId);
                            allStore.add(activityPartyLevel);
                        }
                    }
                }
            }
            //productActivityAreas
            if (UtilValidate.isNotEmpty(productActivityAreas)) {
                List<String> areas = StringUtil.split(productActivityAreas, ",");
                if (UtilValidate.isNotEmpty(areas)) {
                    for (int i = 0; i < areas.size(); i++) {
                        String area = areas.get(i);
                        List<String> areaObj = StringUtil.split(area, ":");
                        if (UtilValidate.isNotEmpty(areaObj)) {
                            String geoId = areaObj.get(0);
                            String geoName = areaObj.get(1);
                            GenericValue productActivityArea = delegator.makeValue("ProductActivityArea");
                            productActivityArea.set("communityId", geoId);
                            productActivityArea.set("communityName", geoName);
                            productActivityArea.set("activityId", activityId);
                            allStore.add(productActivityArea);
                        }
                    }
                }
            }

            //

            delegator.storeAll(allStore);

        } catch (GenericEntityException e) {

            return ServiceUtil.returnError(e.getMessage());
        }
        result.put("activityId", activityId);

        return result;
    }


    public Map<String, Object> updateSecKill(DispatchContext dcx, Map<String, ? extends Object> context) {
        LocalDispatcher dispatcher = dcx.getDispatcher();
        Map<String, Object> result = ServiceUtil.returnSuccess();
        String activityId = (String) context.get("activityId");
        String productStoreIds = (String) context.get("productStoreIds");
        String activityCode = (String) context.get("activityCode");
        String activityAuditStatus = (String) context.get("activityAuditStatus");
        String activityType = (String) context.get("activityType");
        String activityName = (String) context.get("activityName");
        Timestamp publishDate = (Timestamp) context.get("publishDate");
        Timestamp endDate = (Timestamp) context.get("endDate");
        Timestamp activityStartDate = (Timestamp) context.get("activityStartDate");
        Timestamp activityEndDate = (Timestamp) context.get("activityEndDate");
        Long limitQuantity = (Long) context.get("limitQuantity");
        Long activityQuantity = (Long) context.get("activityQuantity");
        Long scoreValue = (Long) context.get("scoreValue");
        String activityPayType = (String) context.get("activityPayType");
        String activityDesc = (String) context.get("activityDesc");
        BigDecimal productPrice = (BigDecimal) context.get("productPrice");

        String productId = (String) context.get("productId");
        String shipmentType = (String) context.get("shipmentType");
        Timestamp virtualProductStartDate = (Timestamp) context.get("virtualProductStartDate");
        Timestamp virtualProductEndDate = (Timestamp) context.get("virtualProductEndDate");
        String isAnyReturn = (String) context.get("isAnyReturn");
        String isSupportOverTimeReturn = (String) context.get("isSupportOverTimeReturn");
        String isSupportScore = (String) context.get("isSupportScore");
        String isSupportReturnScore = (String) context.get("isSupportReturnScore");
        String isShowIndex = (String) context.get("isShowIndex");
        String productGroupOrderRules = (String) context.get("productGroupOrderRules");
        String productActivityPartyLevels = (String) context.get("productActivityPartyLevels");
        String productActivityAreas = (String) context.get("productActivityAreas");
        GenericValue userLogin = (GenericValue) context.get("userLogin");

        //创建productActivity
        Delegator delegator = dcx.getDelegator();

        List<GenericValue> allStore = new LinkedList<GenericValue>();
        GenericValue productActivity = delegator.makeValue("ProductActivity");
        productActivity.set("activityCode", activityCode);
        productActivity.set("activityAuditStatus", "ACTY_AUDIT_INIT");
        productActivity.set("activityName", activityName);
        productActivity.set("activityType", activityType);
        productActivity.set("publishDate", publishDate);
        productActivity.set("endDate", endDate);
        productActivity.set("activityStartDate", activityStartDate);
        productActivity.set("activityEndDate", activityEndDate);
        productActivity.set("limitQuantity", limitQuantity);
        productActivity.set("activityQuantity", activityQuantity);
        productActivity.set("scoreValue", scoreValue);
        productActivity.set("productPrice", productPrice);
        productActivity.set("activityPayType", activityPayType);
        productActivity.set("activityDesc", activityDesc);

        try {
            productActivity.set("activityId", activityId);
            allStore.add(productActivity);

            //修改对应商品
            GenericValue productActivityGoods = delegator.makeValue("ProductActivityGoods");
            productActivityGoods.set("productId", productId);
            productActivityGoods.set("shipmentType", shipmentType);
            productActivityGoods.set("isAnyReturn", isAnyReturn);
            productActivityGoods.set("isSupportOverTimeReturn", isSupportOverTimeReturn);
            productActivityGoods.set("isSupportScore", isSupportScore);
            productActivityGoods.set("isSupportReturnScore", isSupportReturnScore);
            productActivityGoods.set("isShowIndex", isShowIndex);
            productActivityGoods.set("virtualProductStartDate", virtualProductStartDate);
            productActivityGoods.set("virtualProductEndDate", virtualProductEndDate);
            productActivityGoods.set("activityId", activityId);
            allStore.add(productActivityGoods);


            //删除存在的促销ProductPromo
            //删除存在的促销ProductPromoRule
            //删除存在的促销ProductPromoRule
            //删除存在的促销ProductPromoCond
            //删除存在的促销ProductPromoAction
            //删除存在的促销ProductStorePromoAppl
            //删除存在的促销ProductGroupOrderRule

            List<GenericValue> o_productGroupOrderRules = delegator.findByAnd("ProductGroupOrderRule", UtilMisc.toMap("activityId", activityId));
            if (UtilValidate.isNotEmpty(o_productGroupOrderRules)) {
                for (GenericValue productOrderRlue : o_productGroupOrderRules) {
                    String o_promoId = (String) productOrderRlue.get("productPromoId");

                    delegator.removeByAnd("ProductPromoProduct", UtilMisc.toMap("productPromoId", o_promoId));
                    delegator.removeByAnd("ProductPromoCond", UtilMisc.toMap("productPromoId", o_promoId));
                    delegator.removeByAnd("ProductPromoAction", UtilMisc.toMap("productPromoId", o_promoId));
                    delegator.removeByAnd("ProductPromoRule", UtilMisc.toMap("productPromoId", o_promoId));
                    List<GenericValue> appls = delegator.findByAnd("ProductStorePromoAppl", UtilMisc.toMap("productPromoId", o_promoId));
                    if (UtilValidate.isNotEmpty(appls)) {
                        for (int i = 0; i < appls.size(); i++) {
                            GenericValue genericValue = appls.get(i);
                            delegator.removeValue(genericValue);
                        }
                    }
                    delegator.removeByAnd("ProductGroupOrderRule", UtilMisc.toMap("productPromoId", o_promoId));
                    delegator.removeByAnd("ProductPromo", UtilMisc.toMap("productPromoId", o_promoId));
                }
            }

            //创建团购促销
            String promoName = activityName + ":促销";
            String promoText = promoName;
            String userEntered = "Y";
            String showToCustomer = "Y";
            String requireCode = "N";
            Long useLimitPerOrder = 1l;
            Long useLimitPerCustomer = 1l;
            Map inputParams = FastMap.newInstance();
            inputParams.put("promoName", promoName);
            inputParams.put("promoText", promoText);
            inputParams.put("userEntered", userEntered);
            inputParams.put("showToCustomer", showToCustomer);
            inputParams.put("requireCode", requireCode);
            inputParams.put("useLimitPerOrder", useLimitPerOrder);
            inputParams.put("useLimitPerCustomer", useLimitPerCustomer);
            inputParams.put("userLogin", userLogin);
            try {
                Map<String, Object> callback = dispatcher.runSync("createProductPromo", inputParams);
                if (ServiceUtil.isError(callback)) {
                    return result;
                }
                String productPromoId = (String) callback.get("productPromoId");
                //创建createProductPromoRule
                String ruleName = activityName + ":促销条件";
                inputParams = FastMap.newInstance();
                inputParams.put("productPromoId", productPromoId);
                inputParams.put("ruleName", ruleName);
                inputParams.put("userLogin", userLogin);
                callback = dispatcher.runSync("createProductPromoRule", inputParams);
                if (ServiceUtil.isError(callback)) {
                    return result;
                }

                String productPromoRuleId = (String) callback.get("productPromoRuleId");

                //创建createProductPromoCond
                inputParams = FastMap.newInstance();
                inputParams.put("productPromoRuleId", productPromoRuleId);
                inputParams.put("productPromoId", productPromoId);

                inputParams.put("inputParamEnumId", "SALE_TIME_BTW");
                inputParams.put("operatorEnumId", "PPC_BTW");
                inputParams.put("condValue", activityStartDate.toString());
                inputParams.put("otherValue", activityEndDate.toString());
                inputParams.put("userLogin", userLogin);
                callback = dispatcher.runSync("createProductPromoCond", inputParams);
                if (ServiceUtil.isError(callback)) {
                    return result;
                }
                String productPromoCondSeqId = (String) callback.get("productPromoCondSeqId");
                //创建条件产品
                inputParams = FastMap.newInstance();
                inputParams.put("productPromoRuleId", productPromoRuleId);
                inputParams.put("productPromoId", productPromoId);
                inputParams.put("productPromoCondSeqId", productPromoCondSeqId);
                inputParams.put("productPromoActionSeqId", "_NA_");
                inputParams.put("productId", productId);
                inputParams.put("productPromoApplEnumId", "PPPA_INCLUDE");
                inputParams.put("userLogin", userLogin);
                callback = dispatcher.runSync("createProductPromoProduct", inputParams);
                if (ServiceUtil.isError(callback)) {
                    return result;
                }
                //创建createProductPromoAction
                inputParams = FastMap.newInstance();
                inputParams.put("productPromoRuleId", productPromoRuleId);
                inputParams.put("productPromoId", productPromoId);
                inputParams.put("productPromoActionEnumId", "PROMO_PROD_ASGPC");
                inputParams.put("orderAdjustmentTypeId", "PROMOTION_ADJUSTMENT");
                inputParams.put("quantity", BigDecimal.ONE);
                inputParams.put("amount", productPrice);
                inputParams.put("useCartQuantity", "N");
                inputParams.put("userLogin", userLogin);
                callback = dispatcher.runSync("createProductPromoAction", inputParams);
                if (ServiceUtil.isError(callback)) {
                    return result;
                }
                //创建ProductPromoProduct
                String productPromoActionSeqId = (String) callback.get("productPromoActionSeqId");
                inputParams = FastMap.newInstance();
                inputParams.put("productPromoRuleId", productPromoRuleId);
                inputParams.put("productPromoId", productPromoId);
                inputParams.put("productPromoCondSeqId", "_NA_");
                inputParams.put("productPromoActionSeqId", productPromoActionSeqId);
                inputParams.put("productId", productId);
                inputParams.put("productPromoApplEnumId", "PPPA_INCLUDE");
                inputParams.put("userLogin", userLogin);
                callback = dispatcher.runSync("createProductPromoProduct", inputParams);
                if (ServiceUtil.isError(callback)) {
                    return result;
                }
                //创建促销对应店铺createProductStorePromoAppl

                if (UtilValidate.isNotEmpty(productStoreIds)) {
                    List storeIds = StringUtil.split(productStoreIds, ",");
                    for (int j = 0; j < storeIds.size(); j++) {
                        String productStoreId = (String) storeIds.get(j);
                        inputParams = FastMap.newInstance();
                        inputParams.put("productStoreId", productStoreId);
                        inputParams.put("productPromoId", productPromoId);
                        inputParams.put("fromDate", UtilDateTime.nowTimestamp());
                        inputParams.put("userLogin", userLogin);
                        callback = dispatcher.runSync("createProductStorePromoAppl", inputParams);
                        if (ServiceUtil.isError(callback)) {
                            return result;
                        }

                    }

                }

                //创建产品团购规则对应每个促销
                GenericValue productGroupOrderRule = delegator.makeValue("ProductGroupOrderRule");
                productGroupOrderRule.set("activityId", activityId);
                productGroupOrderRule.set("seqId", "001");
                productGroupOrderRule.set("orderQuantity", 1l);
                productGroupOrderRule.set("orderPrice", productPrice);
                productGroupOrderRule.set("productPromoId", productPromoId);
                allStore.add(productGroupOrderRule);

            } catch (GenericServiceException e) {
                return ServiceUtil.returnError(e.getMessage());
            }

            delegator.removeByAnd("ProductActivityPartyLevel", UtilMisc.toMap("activityId", activityId));
            //创建对应ProductPartyLevel
            if (UtilValidate.isNotEmpty(productActivityPartyLevels)) {
                List<String> levels = StringUtil.split(productActivityPartyLevels, ",");
                if (UtilValidate.isNotEmpty(levels)) {
                    for (int i = 0; i < levels.size(); i++) {
                        String level = levels.get(i);
                        List<String> levelObj = StringUtil.split(level, ":");
                        if (UtilValidate.isNotEmpty(levelObj)) {
                            String levelId = levelObj.get(0);
                            String levelName = levelObj.get(1);
                            GenericValue activityPartyLevel = delegator.makeValue("ProductActivityPartyLevel");
                            activityPartyLevel.set("levelId", levelId);
                            activityPartyLevel.set("levelName", levelName);
                            activityPartyLevel.set("activityId", activityId);
                            allStore.add(activityPartyLevel);
                        }
                    }
                }
            }

            delegator.removeByAnd("ProductActivityArea", UtilMisc.toMap("activityId", activityId));
            //productActivityAreas
            if (UtilValidate.isNotEmpty(productActivityAreas)) {
                List<String> areas = StringUtil.split(productActivityAreas, ",");
                if (UtilValidate.isNotEmpty(areas)) {
                    for (int i = 0; i < areas.size(); i++) {
                        String area = areas.get(i);
                        List<String> areaObj = StringUtil.split(area, ":");
                        if (UtilValidate.isNotEmpty(areaObj)) {
                            String geoId = areaObj.get(0);
                            String geoName = areaObj.get(1);
                            GenericValue productActivityArea = delegator.makeValue("ProductActivityArea");
                            productActivityArea.set("communityId", geoId);
                            productActivityArea.set("communityName", geoName);
                            productActivityArea.set("activityId", activityId);
                            allStore.add(productActivityArea);
                        }
                    }
                }
            }

            //
            delegator.storeAll(allStore);

        } catch (GenericEntityException e) {

            return ServiceUtil.returnError(e.getMessage());
        }
        result.put("activityId", activityId);

        return result;
    }

    /**
     * 删除秒杀
     *
     * @param dcx
     * @param context
     * @return
     */
    public static Map<String, Object> deleteSecKill(DispatchContext dcx, Map<String, ? extends Object> context) {

        Map<String, Object> result = ServiceUtil.returnSuccess();
        String activityId = (String) context.get("activityId");
        Delegator delegator = dcx.getDelegator();
        //删除存在的促销ProductPromo
        //删除存在的促销ProductPromoRule
        //删除存在的促销ProductPromoRule
        //删除存在的促销ProductPromoCond
        //删除存在的促销ProductPromoAction
        //删除存在的促销ProductStorePromoAppl
        //删除存在的促销ProductGroupOrderRule

        List<GenericValue> o_productGroupOrderRules = null;
        try {
            o_productGroupOrderRules = delegator.findByAnd("ProductGroupOrderRule", UtilMisc.toMap("activityId", activityId));

            if (UtilValidate.isNotEmpty(o_productGroupOrderRules)) {
                for (GenericValue productOrderRlue : o_productGroupOrderRules) {
                    String o_promoId = (String) productOrderRlue.get("productPromoId");

                    delegator.removeByAnd("ProductPromoProduct", UtilMisc.toMap("productPromoId", o_promoId));
                    delegator.removeByAnd("ProductPromoCond", UtilMisc.toMap("productPromoId", o_promoId));
                    delegator.removeByAnd("ProductPromoAction", UtilMisc.toMap("productPromoId", o_promoId));
                    delegator.removeByAnd("ProductPromoRule", UtilMisc.toMap("productPromoId", o_promoId));
                    List<GenericValue> appls = delegator.findByAnd("ProductStorePromoAppl", UtilMisc.toMap("productPromoId", o_promoId));
                    if (UtilValidate.isNotEmpty(appls)) {
                        for (int i = 0; i < appls.size(); i++) {
                            GenericValue genericValue = appls.get(i);
                            delegator.removeValue(genericValue);
                        }
                    }
                    delegator.removeByAnd("ProductGroupOrderRule", UtilMisc.toMap("productPromoId", o_promoId));
                    delegator.removeByAnd("ProductPromo", UtilMisc.toMap("productPromoId", o_promoId));
                }
            }
            delegator.removeByAnd("ProductActivityGoods", UtilMisc.toMap("activityId", activityId));
            delegator.removeByAnd("ProductActivityPartyLevel", UtilMisc.toMap("activityId", activityId));
            delegator.removeByAnd("ProductActivityArea", UtilMisc.toMap("activityId", activityId));
            delegator.removeByAnd("ProductActivity", UtilMisc.toMap("activityId", activityId));
        } catch (GenericEntityException e) {
            e.printStackTrace();
        }
        return result;
    }
}
