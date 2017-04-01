/*******************************************************************************
 * Licensed to the Apache Software Foundation (ASF) under one
 * or more contributor license agreements.  See the NOTICE file
 * distributed with this work for additional information
 * regarding copyright ownership.  The ASF licenses this file
 * to you under the Apache License, Version 2.0 (the
 * "License"); you may not use this file except in compliance
 * with the License.  You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing,
 * software distributed under the License is distributed on an
 * "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
 * KIND, either express or implied.  See the License for the
 * specific language governing permissions and limitations
 * under the License.
 *******************************************************************************/
package com.yuaoq.yabiz.eshop.order;

import javolution.util.FastList;
import javolution.util.FastMap;
import org.ofbiz.base.util.*;
import org.ofbiz.entity.Delegator;
import org.ofbiz.entity.GenericEntityException;
import org.ofbiz.entity.GenericValue;
import org.ofbiz.entity.condition.EntityComparisonOperator;
import org.ofbiz.entity.condition.EntityCondition;
import org.ofbiz.entity.condition.EntityExpr;
import org.ofbiz.entity.condition.EntityOperator;
import org.ofbiz.entity.model.DynamicViewEntity;
import org.ofbiz.entity.model.ModelKeyMap;
import org.ofbiz.entity.util.EntityFindOptions;
import org.ofbiz.entity.util.EntityListIterator;
import org.ofbiz.service.DispatchContext;
import org.ofbiz.service.ModelService;
import org.ofbiz.service.ServiceUtil;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * Order Processing Services
 */

public class OrderPlanServices {

    public static final String module = OrderPlanServices.class.getName();
    public static final String resource = "OrderUiLabels";
    public static final String resourceError = "PartyErrorUiLabels";

    public static Map<String, Object> createOrderPlan(DispatchContext ctx, Map<String, ?> context) {
        Map<String, Object> result = FastMap.newInstance();
        Delegator delegator = ctx.getDelegator();
        List<String> errorMessageList = FastList.newInstance();
        Locale locale = (Locale) context.get("locale");

        String planId = (String) context.get("planId");
        if (UtilValidate.isEmpty(planId)) {
            try {
                planId = delegator.getNextSeqId("PersonOrderPlan");
            } catch (IllegalArgumentException e) {
                return ServiceUtil.returnError(UtilProperties.getMessage(resourceError,
                        "PersonOrderPlan.id_generation_failure", locale));
            }
        }
        String comment = (String) context.get("comment");
        String personName = (String) context.get("personName");
        String mobile = (String) context.get("mobile");
        java.sql.Timestamp planDate =java.sql.Timestamp.valueOf((String) context.get("planDate"));
        String facilityId = (String) context.get("facilityId");
        String partyId = (String) context.get("partyId");
        String salePartyId = (String) context.get("salePartyId");
        String status = (String) context.get("status");
        String errMsg = null;
        GenericValue personOrderPlan=delegator.makeValue("PersonOrderPlan");
        personOrderPlan.set("id",planId);
        personOrderPlan.set("planId",planId);
        personOrderPlan.set("comment",comment);
        personOrderPlan.set("personName",personName);
        personOrderPlan.set("mobile",mobile);
        personOrderPlan.set("planDate",planDate);
        personOrderPlan.set("facilityId",facilityId);
        personOrderPlan.set("partyId",partyId);
        personOrderPlan.set("salePartyId",salePartyId);
        personOrderPlan.set("status",status);
        personOrderPlan.set("createUserLoginId",partyId);
        try {
            delegator.create(personOrderPlan);
        } catch (GenericEntityException e) {
            Debug.logWarning(e, "", module);
            Map<String, String> messageMap = UtilMisc.toMap("errorMessage", e.getMessage());
            errMsg = UtilProperties.getMessage(resource, "orderplanservices.could_not_create_order_plan_write_failure", messageMap, locale);
            errorMessageList.add(errMsg);
        }

        if (errorMessageList.size() > 0) {
            return ServiceUtil.returnError(errorMessageList);
        }

        result.put(ModelService.RESPONSE_MESSAGE, ModelService.RESPOND_SUCCESS);
        return result;
    }

    protected static EntityExpr makeExpr(String fieldName, String value) {
        return makeExpr(fieldName, value, false);
    }

    protected static EntityExpr makeExpr(String fieldName, String value, boolean forceLike) {
        EntityComparisonOperator<?, ?> op = forceLike ? EntityOperator.LIKE : EntityOperator.EQUALS;

        if (value.startsWith("*")) {
            op = EntityOperator.LIKE;
            value = "%" + value.substring(1);
        } else if (value.startsWith("%")) {
            op = EntityOperator.LIKE;
        }

        if (value.endsWith("*")) {
            op = EntityOperator.LIKE;
            value = value.substring(0, value.length() - 1) + "%";
        } else if (value.endsWith("%")) {
            op = EntityOperator.LIKE;
        }

        if (forceLike) {
            if (!value.startsWith("%")) {
                value = "%" + value;
            }
            if (!value.endsWith("%")) {
                value = value + "%";
            }
        }

        return EntityCondition.makeCondition(fieldName, op, value);
    }

    /**
     * Add By Changsy
     * @param dctx
     * @param context
     * @return
     * @throws ParseException
     */
    public static Map<String, Object> findReservations(DispatchContext dctx, Map<String, ? extends Object> context) throws ParseException {
        Delegator delegator = dctx.getDelegator();
        // create the result map
        Map<String, Object> result = ServiceUtil.returnSuccess();
        Integer viewIndex = 1;
        Integer viewSize = 1000;
        GenericValue userLogin= (GenericValue) context.get("userLogin");
        String partyId = (String) userLogin.get("partyId");
        String salePartyId = (String) context.get("salePartyId");
        String mobile = (String) context.get("mobile");
        String comment = (String) context.get("comment");
        String status = (String) context.get("status");
        String reservationTimeStart = (String) context.get("reservationTimeStart");
        String reservationTimeEnd = (String) context.get("reservationTimeEnd");
        String planId = (String) context.get("planId");
        // dynamic view entity
        DynamicViewEntity dve = new DynamicViewEntity();
        dve.addMemberEntity("POP", "PersonOrderPlan");
        dve.addMemberEntity("PER", "Person");
        dve.addMemberEntity("PG", "PartyGroup");
        dve.addAlias("POP", "partyId");
        dve.addAlias("POP", "planId");
        dve.addAlias("POP", "orderId");
        dve.addAlias("POP", "comment");
        dve.addAlias("POP", "mobile");
        dve.addAlias("POP", "planDate");
        dve.addAlias("POP", "salePartyId");
        dve.addAlias("PER", "firstName");
        dve.addAlias("PER", "lastName");
        dve.addAlias("PG", "groupName");
        dve.addViewLink("POP", "PER", true, UtilMisc.toList(new ModelKeyMap("partyId", "partyId")));
        dve.addViewLink("POP", "PG", true, UtilMisc.toList(new ModelKeyMap("salePartyId", "partyId")));
//        List<String> fieldsToSelect = FastList.newInstance();
        List<String> orderBy = UtilMisc.toList("-planDate");
        List<String> paramList = FastList.newInstance();
        List<EntityCondition> conditions = FastList.newInstance();
        if (UtilValidate.isNotEmpty(planId)) {
            paramList.add("planId=" + planId);
            conditions.add(makeExpr("planId", planId));
        }
        if (UtilValidate.isNotEmpty(partyId)) {
            paramList.add("partyId=" + partyId);
            conditions.add(makeExpr("partyId", partyId));
        }
        if (UtilValidate.isNotEmpty(salePartyId)) {
            paramList.add("salePartyId=" + salePartyId);
            conditions.add(makeExpr("salePartyId", salePartyId));
        }
        if (UtilValidate.isNotEmpty(mobile)) {
            paramList.add("mobile=" + mobile);
            conditions.add(makeExpr("mobile", mobile));
        }
        if (UtilValidate.isNotEmpty(comment)) {
            paramList.add("comment=" + comment);
            conditions.add(makeExpr("comment", comment, true));
        }
        if (UtilValidate.isNotEmpty(reservationTimeStart)) {
            paramList.add("reservationTimeStart=" + reservationTimeStart);
            try {
                Object converted = ObjectType.simpleTypeConvert(reservationTimeStart, "Timestamp", null, null);
                conditions.add(EntityCondition.makeCondition("planDate", EntityOperator.GREATER_THAN_EQUAL_TO, converted));
            } catch (GeneralException e) {
                e.printStackTrace();
            }
        }
        if (UtilValidate.isNotEmpty(reservationTimeEnd)) {
            paramList.add("reservationTimeEnd=" + reservationTimeEnd);
            try {
                Object converted = ObjectType.simpleTypeConvert(reservationTimeEnd, "Timestamp", null, null);
                conditions.add(EntityCondition.makeCondition("planDate", EntityOperator.LESS_THAN_EQUAL_TO, converted));
            } catch (GeneralException e) {
                e.printStackTrace();
            }
        }
        EntityFindOptions findOpts = new EntityFindOptions(true, EntityFindOptions.TYPE_SCROLL_INSENSITIVE, EntityFindOptions.CONCUR_READ_ONLY, true);
        // create the main condition
        EntityCondition cond = null;
        if (conditions.size() > 0) {
            cond = EntityCondition.makeCondition(conditions, EntityOperator.AND);
        }
        List<GenericValue> reservationList = FastList.newInstance();
        int reservationCount = 0;
        // get the index for the partial list
        int lowIndex = (((viewIndex.intValue() - 1) * viewSize.intValue()) + 1);
        int highIndex = viewIndex.intValue() * viewSize.intValue();
        findOpts.setMaxRows(50000000);
        EntityListIterator eli = null;
        try {
            // do the lookup
            eli = delegator.findListIteratorByCondition(dve, cond, null, null, orderBy, findOpts);
            reservationCount = eli.getCompleteList().size();
            // get the partial list for this page
            eli.beforeFirst();
            if (reservationCount > viewSize.intValue()) {
                reservationList = eli.getPartialList(lowIndex, viewSize.intValue());
            } else if (reservationCount > 0) {
                reservationList = eli.getCompleteList();
            }

            if (highIndex > reservationCount) {
                highIndex = reservationCount;
            }
        } catch (GenericEntityException e) {
            Debug.logError(e, module);
            return ServiceUtil.returnError(e.getMessage());
        } finally {
            if (eli != null) {
                try {
                    eli.close();
                } catch (GenericEntityException e) {
                    Debug.logWarning(e, e.getMessage(), module);
                }
            }
        }
        List<GenericValue> reservationList2 = FastList.newInstance();
        for (int i = 0; i < reservationList.size(); i++) {
            GenericValue genericValue = reservationList.get(i);
            List<EntityCondition> ld = FastList.newInstance();
            ld.add(EntityCondition.makeCondition("planId", EntityOperator.EQUALS, genericValue.get("planId")));
            ld.add(EntityCondition.makeCondition("partyId", EntityOperator.EQUALS, genericValue.get("partyId")));
            ld.add(EntityCondition.makeCondition("salePartyId", EntityOperator.EQUALS, genericValue.get("salePartyId")));
            ld.add(EntityCondition.makeCondition("mobile", EntityOperator.EQUALS, genericValue.get("mobile")));
            ld.add(EntityCondition.makeCondition("comment", EntityOperator.EQUALS, genericValue.get("comment")));
            ld.add(EntityCondition.makeCondition("planDate", EntityOperator.EQUALS, genericValue.get("planDate")));
            EntityCondition condition = EntityCondition.makeCondition(ld, EntityOperator.AND);
            List<GenericValue> tempList = FastList.newInstance();
            try {
                tempList = delegator.findList("PersonOrderPlan", condition, UtilMisc.toSet("id"), UtilMisc.toList("-id"), null, true);
            } catch (GenericEntityException e) {
                e.printStackTrace();
            }
            String idtemp = (String) tempList.get(0).get("id");
            GenericValue genericValue1 = null;
            try {
                DynamicViewEntity dveTemp = new DynamicViewEntity();
                dveTemp.addMemberEntity("POP", "PersonOrderPlan");
                dveTemp.addMemberEntity("PER", "Person");
                dveTemp.addMemberEntity("PG", "PartyGroup");
                dveTemp.addAliasAll("POP", "", null);
                dveTemp.addAlias("PER", "firstName");
                dveTemp.addAlias("PER", "lastName");
                dveTemp.addAlias("PG", "groupName");
                dveTemp.addViewLink("POP", "PER", true, UtilMisc.toList(new ModelKeyMap("partyId", "partyId")));
                dveTemp.addViewLink("POP", "PG", true, UtilMisc.toList(new ModelKeyMap("salePartyId", "partyId")));
                eli = delegator.findListIteratorByCondition(dveTemp, makeExpr("id", idtemp), null, null, null, null);
                genericValue1 = eli.getCompleteList().get(0);
            } catch (GenericEntityException e) {
                e.printStackTrace();
            } finally {
                if (eli != null) {
                    try {
                        eli.close();
                    } catch (GenericEntityException e) {
                        Debug.logWarning(e, e.getMessage(), module);
                    }
                }
            }
            reservationList2.add(genericValue1);
        }
        List<GenericValue> reservationList3 = new LinkedList<GenericValue>();
        if (UtilValidate.isNotEmpty(status) && reservationList2 != null && reservationList2.size() > 0) {
            paramList.add("status=" + status);
            if (status.equals("0") || status.equals("2")) {     //已取消或已完成
                for (GenericValue g : reservationList2) {
                    if (g.get("status").equals(status)) {
                        reservationList3.add(g);
                    }
                }
            } else if (status.equals("3")) {                     //已过期
                DateFormat sdf = new SimpleDateFormat("yyyyMMdd");
                DateFormat sdf2 = new SimpleDateFormat("yyyyMMddHHmmss");
                for (GenericValue g : reservationList2) {
                    Date dateTemp = sdf.parse(g.getTimestamp("planDate").toString());
                    String dateStr = sdf.format(dateTemp) + "235959";
                    Date d = sdf2.parse(dateStr);
                    java.sql.Timestamp dd = new java.sql.Timestamp(d.getTime());
                    if (g.get("status").equals("1") && (dd.before(new java.sql.Timestamp(new Date().getTime()))) || dd.equals(new java.sql.Timestamp(new Date().getTime()))) {
                        reservationList3.add(g);
                    }
                }
            } else if (status.equals("1")) {                   //已预约
                DateFormat sdf = new SimpleDateFormat("yyyyMMdd");
                DateFormat sdf2 = new SimpleDateFormat("yyyyMMddHHmmss");
                for (GenericValue g : reservationList2) {
                    Date dateTemp = sdf.parse(g.getTimestamp("planDate").toString());
                    String dateStr = sdf.format(dateTemp) + "235959";
                    Date d = sdf2.parse(dateStr);
                    java.sql.Timestamp dd = new java.sql.Timestamp(d.getTime());
                    if (g.get("status").equals(status) && dd.after(new java.sql.Timestamp(new Date().getTime()))) {
                        reservationList3.add(g);
                    }
                }
            }

        }
        if (UtilValidate.isNotEmpty(status)) {
            reservationCount = reservationList3.size();
            List<GenericValue> reservationList4 = new LinkedList<GenericValue>();
            // get the partial list for this page
            if (reservationCount > viewSize.intValue()) {
                for (int i = (lowIndex - 1); i < (viewSize.intValue() - 1); i++) {
                    reservationList4.add(reservationList3.get(i));
                }
            } else if (reservationCount > 0) {
                reservationList4 = reservationList3;
            }
            if (highIndex > reservationCount) {
                highIndex = reservationCount;
            }
            result.put("highIndex", Integer.valueOf(highIndex));
            result.put("lowIndex", Integer.valueOf(lowIndex));
            result.put("reservationList", reservationList4);
            result.put("reservationListSize", reservationCount);
        } else {
            result.put("highIndex", Integer.valueOf(highIndex));
            result.put("lowIndex", Integer.valueOf(lowIndex));
            result.put("reservationList", reservationList2);
            result.put("reservationListSize", reservationCount);
        }
        result.put("viewSize", viewSize);
        result.put("viewIndex", viewIndex);
        result.put("paramList", paramList);
        return result;
    }

}
