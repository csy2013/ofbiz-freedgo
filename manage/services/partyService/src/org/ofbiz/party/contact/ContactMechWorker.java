/*******************************************************************************
 * Licensed to the Apache Software Foundation (ASF) under one
 * or more contributor license agreements.  See the NOTICE file
 * distributed with this work for additional information
 * regarding copyright ownership.  The ASF licenses this file
 * to you under the Apache License, Version 2.0 (the
 * "License"); you may not use this file except in compliance
 * with the License.  You may obtain a copy of the License at
 * <p>
 * http://www.apache.org/licenses/LICENSE-2.0
 * <p>
 * Unless required by applicable law or agreed to in writing,
 * software distributed under the License is distributed on an
 * "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
 * KIND, either express or implied.  See the License for the
 * specific language governing permissions and limitations
 * under the License.
 *******************************************************************************/

package org.ofbiz.party.contact;

import javolution.util.FastList;
import javolution.util.FastMap;
import org.ofbiz.base.util.Assert;
import org.ofbiz.base.util.Debug;
import org.ofbiz.base.util.UtilMisc;
import org.ofbiz.base.util.UtilValidate;
import org.ofbiz.entity.Delegator;
import org.ofbiz.entity.GenericEntityException;
import org.ofbiz.entity.GenericValue;
import org.ofbiz.entity.condition.EntityCondition;
import org.ofbiz.entity.util.EntityUtil;
import org.ofbiz.entity.util.EntityUtilProperties;

import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

/**
 * Worker methods for Contact Mechanisms
 */
public class ContactMechWorker {
    
    public static final String module = ContactMechWorker.class.getName();
    
    private ContactMechWorker() {
    }
    
    public static List<Map<String, Object>> getPartyContactMechValueMaps(Delegator delegator, String partyId, boolean showOld) {
        return getPartyContactMechValueMaps(delegator, partyId, showOld, null);
    }
    
    public static List<Map<String, Object>> getPartyContactMechValueMaps(Delegator delegator, String partyId, boolean showOld, String contactMechTypeId) {
        List<Map<String, Object>> partyContactMechValueMaps = FastList.newInstance();
        
        List<GenericValue> allPartyContactMechs = null;
        
        try {
            List<GenericValue> tempCol = delegator.findByAnd("PartyContactMech", UtilMisc.toMap("partyId", partyId));
            if (contactMechTypeId != null) {
                List<GenericValue> tempColTemp = FastList.newInstance();
                for (GenericValue partyContactMech : tempCol) {
                    GenericValue contactMech = delegator.getRelatedOne("ContactMech", partyContactMech);
                    if (contactMech != null && contactMechTypeId.equals(contactMech.getString("contactMechTypeId"))) {
                        tempColTemp.add(partyContactMech);
                    }
                    
                }
                tempCol = tempColTemp;
            }
            if (!showOld) tempCol = EntityUtil.filterByDate(tempCol, true);
            allPartyContactMechs = tempCol;
        } catch (GenericEntityException e) {
            Debug.logWarning(e, module);
        }
        
        if (allPartyContactMechs == null) return partyContactMechValueMaps;
        
        for (GenericValue partyContactMech : allPartyContactMechs) {
            GenericValue contactMech = null;
            
            try {
                contactMech = partyContactMech.getRelatedOne("ContactMech");
            } catch (GenericEntityException e) {
                Debug.logWarning(e, module);
            }
            if (contactMech != null) {
                Map<String, Object> partyContactMechValueMap = FastMap.newInstance();
                
                partyContactMechValueMaps.add(partyContactMechValueMap);
                partyContactMechValueMap.put("contactMech", contactMech);
                partyContactMechValueMap.put("partyContactMech", partyContactMech);
                
                try {
                    partyContactMechValueMap.put("contactMechType", contactMech.getRelatedOneCache("ContactMechType"));
                } catch (GenericEntityException e) {
                    Debug.logWarning(e, module);
                }
                
                try {
                    List<GenericValue> partyContactMechPurposes = partyContactMech.getRelated("PartyContactMechPurpose");
                    
                    if (!showOld) partyContactMechPurposes = EntityUtil.filterByDate(partyContactMechPurposes, true);
                    partyContactMechValueMap.put("partyContactMechPurposes", partyContactMechPurposes);
                } catch (GenericEntityException e) {
                    Debug.logWarning(e, module);
                }
                
                try {
                    if ("POSTAL_ADDRESS".equals(contactMech.getString("contactMechTypeId"))) {
                        partyContactMechValueMap.put("postalAddress", contactMech.getRelatedOne("PostalAddress"));
                    } else if ("TELECOM_NUMBER".equals(contactMech.getString("contactMechTypeId"))) {
                        partyContactMechValueMap.put("telecomNumber", contactMech.getRelatedOne("TelecomNumber"));
                    }
                } catch (GenericEntityException e) {
                    Debug.logWarning(e, module);
                }
            }
        }
        
        return partyContactMechValueMaps;
    }
    
    public static List<Map<String, Object>> getFacilityContactMechValueMaps(Delegator delegator, String facilityId, boolean showOld, String contactMechTypeId) {
        List<Map<String, Object>> facilityContactMechValueMaps = FastList.newInstance();
        
        List<GenericValue> allFacilityContactMechs = null;
        
        try {
            List<GenericValue> tempCol = delegator.findByAnd("FacilityContactMech", UtilMisc.toMap("facilityId", facilityId));
            if (contactMechTypeId != null) {
                List<GenericValue> tempColTemp = FastList.newInstance();
                for (GenericValue partyContactMech : tempCol) {
                    GenericValue contactMech = delegator.getRelatedOne("ContactMech", partyContactMech);
                    if (contactMech != null && contactMechTypeId.equals(contactMech.getString("contactMechTypeId"))) {
                        tempColTemp.add(partyContactMech);
                    }
                    
                }
                tempCol = tempColTemp;
            }
            if (!showOld) tempCol = EntityUtil.filterByDate(tempCol, true);
            allFacilityContactMechs = tempCol;
        } catch (GenericEntityException e) {
            Debug.logWarning(e, module);
        }
        
        if (allFacilityContactMechs == null) return facilityContactMechValueMaps;
        
        for (GenericValue facilityContactMech : allFacilityContactMechs) {
            GenericValue contactMech = null;
            
            try {
                contactMech = facilityContactMech.getRelatedOne("ContactMech");
            } catch (GenericEntityException e) {
                Debug.logWarning(e, module);
            }
            if (contactMech != null) {
                Map<String, Object> facilityContactMechValueMap = FastMap.newInstance();
                
                facilityContactMechValueMaps.add(facilityContactMechValueMap);
                facilityContactMechValueMap.put("contactMech", contactMech);
                facilityContactMechValueMap.put("facilityContactMech", facilityContactMech);
                
                try {
                    facilityContactMechValueMap.put("contactMechType", contactMech.getRelatedOneCache("ContactMechType"));
                } catch (GenericEntityException e) {
                    Debug.logWarning(e, module);
                }
                
                try {
                    List<GenericValue> facilityContactMechPurposes = facilityContactMech.getRelated("FacilityContactMechPurpose");
                    
                    if (!showOld) facilityContactMechPurposes = EntityUtil.filterByDate(facilityContactMechPurposes, true);
                    facilityContactMechValueMap.put("facilityContactMechPurposes", facilityContactMechPurposes);
                } catch (GenericEntityException e) {
                    Debug.logWarning(e, module);
                }
                
                try {
                    if ("POSTAL_ADDRESS".equals(contactMech.getString("contactMechTypeId"))) {
                        facilityContactMechValueMap.put("postalAddress", contactMech.getRelatedOne("PostalAddress"));
                    } else if ("TELECOM_NUMBER".equals(contactMech.getString("contactMechTypeId"))) {
                        facilityContactMechValueMap.put("telecomNumber", contactMech.getRelatedOne("TelecomNumber"));
                    }
                } catch (GenericEntityException e) {
                    Debug.logWarning(e, module);
                }
            }
        }
        
        return facilityContactMechValueMaps;
    }
    
    public static List<Map<String, Object>> getProductStoreContactMechValueMaps(Delegator delegator, String productStoreId, boolean showOld, String contactMechTypeId) {
        List<Map<String, Object>> productStoreContactMechValueMaps = FastList.newInstance();
        List<GenericValue> allProductStoreContactMechs = null;
        try {
            List<GenericValue> tempCol = delegator.findByAnd("ProductStoreContactMech", UtilMisc.toMap("productStoreId", productStoreId));
            if (contactMechTypeId != null) {
                List<GenericValue> tempColTemp = FastList.newInstance();
                for (GenericValue partyContactMech : tempCol) {
                    GenericValue contactMech = delegator.getRelatedOne("ContactMech", partyContactMech);
                    if (contactMech != null && contactMechTypeId.equals(contactMech.getString("contactMechTypeId"))) {
                        tempColTemp.add(partyContactMech);
                    }
                }
                tempCol = tempColTemp;
            }
            if (!showOld) tempCol = EntityUtil.filterByDate(tempCol, true);
            allProductStoreContactMechs = tempCol;
        } catch (GenericEntityException e) {
            Debug.logWarning(e, module);
        }
        
        if (allProductStoreContactMechs == null) return productStoreContactMechValueMaps;
        
        for (GenericValue productStoreContactMech : allProductStoreContactMechs) {
            GenericValue contactMech = null;
            
            try {
                contactMech = productStoreContactMech.getRelatedOne("ContactMech");
            } catch (GenericEntityException e) {
                Debug.logWarning(e, module);
            }
            if (contactMech != null) {
                Map<String, Object> productStoreContactMechValueMap = FastMap.newInstance();
                
                productStoreContactMechValueMaps.add(productStoreContactMechValueMap);
                productStoreContactMechValueMap.put("contactMech", contactMech);
                productStoreContactMechValueMap.put("productStoreContactMech", productStoreContactMech);
                
                try {
                    productStoreContactMechValueMap.put("contactMechType", contactMech.getRelatedOneCache("ContactMechType"));
                } catch (GenericEntityException e) {
                    Debug.logWarning(e, module);
                }
                
                try {
                    List<GenericValue> productStoreContactMechPurposes = productStoreContactMech.getRelated("ProductStoreContactMechPurpose");
                    
                    if (!showOld) productStoreContactMechPurposes = EntityUtil.filterByDate(productStoreContactMechPurposes, true);
                    productStoreContactMechValueMap.put("productStoreContactMechPurposes", productStoreContactMechPurposes);
                } catch (GenericEntityException e) {
                    Debug.logWarning(e, module);
                }
                
                try {
                    if ("POSTAL_ADDRESS".equals(contactMech.getString("contactMechTypeId"))) {
                        productStoreContactMechValueMap.put("postalAddress", contactMech.getRelatedOne("PostalAddress"));
                    } else if ("TELECOM_NUMBER".equals(contactMech.getString("contactMechTypeId"))) {
                        productStoreContactMechValueMap.put("telecomNumber", contactMech.getRelatedOne("TelecomNumber"));
                    }
                } catch (GenericEntityException e) {
                    Debug.logWarning(e, module);
                }
            }
        }
        
        return productStoreContactMechValueMaps;
    }
    
    public static List<Map<String, GenericValue>> getOrderContactMechValueMaps(Delegator delegator, String orderId) {
        List<Map<String, GenericValue>> orderContactMechValueMaps = FastList.newInstance();
        
        List<GenericValue> allOrderContactMechs = null;
        
        try {
            allOrderContactMechs = delegator.findByAnd("OrderContactMech", UtilMisc.toMap("orderId", orderId), UtilMisc.toList("contactMechPurposeTypeId"));
        } catch (GenericEntityException e) {
            Debug.logWarning(e, module);
        }
        
        if (allOrderContactMechs == null) return orderContactMechValueMaps;
        
        for (GenericValue orderContactMech : allOrderContactMechs) {
            GenericValue contactMech = null;
            
            try {
                contactMech = orderContactMech.getRelatedOne("ContactMech");
            } catch (GenericEntityException e) {
                Debug.logWarning(e, module);
            }
            if (contactMech != null) {
                Map<String, GenericValue> orderContactMechValueMap = FastMap.newInstance();
                
                orderContactMechValueMaps.add(orderContactMechValueMap);
                orderContactMechValueMap.put("contactMech", contactMech);
                orderContactMechValueMap.put("orderContactMech", orderContactMech);
                
                try {
                    orderContactMechValueMap.put("contactMechType", contactMech.getRelatedOneCache("ContactMechType"));
                } catch (GenericEntityException e) {
                    Debug.logWarning(e, module);
                }
                
                try {
                    GenericValue contactMechPurposeType = orderContactMech.getRelatedOne("ContactMechPurposeType");
                    
                    orderContactMechValueMap.put("contactMechPurposeType", contactMechPurposeType);
                } catch (GenericEntityException e) {
                    Debug.logWarning(e, module);
                }
                
                try {
                    if ("POSTAL_ADDRESS".equals(contactMech.getString("contactMechTypeId"))) {
                        orderContactMechValueMap.put("postalAddress", contactMech.getRelatedOne("PostalAddress"));
                    } else if ("TELECOM_NUMBER".equals(contactMech.getString("contactMechTypeId"))) {
                        orderContactMechValueMap.put("telecomNumber", contactMech.getRelatedOne("TelecomNumber"));
                    }
                } catch (GenericEntityException e) {
                    Debug.logWarning(e, module);
                }
            }
        }
        
        return orderContactMechValueMaps;
    }
    
    public static Collection<Map<String, GenericValue>> getWorkEffortContactMechValueMaps(Delegator delegator, String workEffortId) {
        Collection<Map<String, GenericValue>> workEffortContactMechValueMaps = FastList.newInstance();
        
        List<GenericValue> allWorkEffortContactMechs = null;
        
        try {
            List<GenericValue> workEffortContactMechs = delegator.findByAnd("WorkEffortContactMech", UtilMisc.toMap("workEffortId", workEffortId));
            allWorkEffortContactMechs = EntityUtil.filterByDate(workEffortContactMechs);
        } catch (GenericEntityException e) {
            Debug.logWarning(e, module);
        }
        
        if (allWorkEffortContactMechs == null) return null;
        
        for (GenericValue workEffortContactMech : allWorkEffortContactMechs) {
            GenericValue contactMech = null;
            
            try {
                contactMech = workEffortContactMech.getRelatedOne("ContactMech");
            } catch (GenericEntityException e) {
                Debug.logWarning(e, module);
            }
            if (contactMech != null) {
                Map<String, GenericValue> workEffortContactMechValueMap = FastMap.newInstance();
                
                workEffortContactMechValueMaps.add(workEffortContactMechValueMap);
                workEffortContactMechValueMap.put("contactMech", contactMech);
                workEffortContactMechValueMap.put("workEffortContactMech", workEffortContactMech);
                
                try {
                    workEffortContactMechValueMap.put("contactMechType", contactMech.getRelatedOneCache("ContactMechType"));
                } catch (GenericEntityException e) {
                    Debug.logWarning(e, module);
                }
                
                try {
                    if ("POSTAL_ADDRESS".equals(contactMech.getString("contactMechTypeId"))) {
                        workEffortContactMechValueMap.put("postalAddress", contactMech.getRelatedOne("PostalAddress"));
                    } else if ("TELECOM_NUMBER".equals(contactMech.getString("contactMechTypeId"))) {
                        workEffortContactMechValueMap.put("telecomNumber", contactMech.getRelatedOne("TelecomNumber"));
                    }
                } catch (GenericEntityException e) {
                    Debug.logWarning(e, module);
                }
            }
        }
        
        return workEffortContactMechValueMaps.size() > 0 ? workEffortContactMechValueMaps : null;
    }
    
    public static void getContactMechAndRelated(ServletRequest request, String partyId, Map<String, Object> target) {
        Delegator delegator = (Delegator) request.getAttribute("delegator");
        
        boolean tryEntity = true;
        if (request.getAttribute("_ERROR_MESSAGE_") != null) tryEntity = false;
        if ("true".equals(request.getParameter("tryEntity"))) tryEntity = true;
        
        String donePage = request.getParameter("DONE_PAGE");
        if (donePage == null) donePage = (String) request.getAttribute("DONE_PAGE");
        if (donePage == null || donePage.length() <= 0) donePage = "viewprofile";
        target.put("donePage", donePage);
        
        String contactMechTypeId = request.getParameter("preContactMechTypeId");
        
        if (contactMechTypeId == null) contactMechTypeId = (String) request.getAttribute("preContactMechTypeId");
        if (contactMechTypeId != null)
            tryEntity = false;
        
        String contactMechId = request.getParameter("contactMechId");
        
        if (request.getAttribute("contactMechId") != null)
            contactMechId = (String) request.getAttribute("contactMechId");
        
        GenericValue contactMech = null;
        
        if (contactMechId != null) {
            target.put("contactMechId", contactMechId);
            
            // try to find a PartyContactMech with a valid date range
            List<GenericValue> partyContactMechs = null;
            
            try {
                partyContactMechs = EntityUtil.filterByDate(delegator.findByAnd("PartyContactMech", UtilMisc.toMap("partyId", partyId, "contactMechId", contactMechId)), true);
            } catch (GenericEntityException e) {
                Debug.logWarning(e, module);
            }
            
            GenericValue partyContactMech = EntityUtil.getFirst(partyContactMechs);
            
            if (partyContactMech != null) {
                target.put("partyContactMech", partyContactMech);
                
                Collection<GenericValue> partyContactMechPurposes = null;
                
                try {
                    partyContactMechPurposes = EntityUtil.filterByDate(partyContactMech.getRelated("PartyContactMechPurpose"), true);
                } catch (GenericEntityException e) {
                    Debug.logWarning(e, module);
                }
                if (UtilValidate.isNotEmpty(partyContactMechPurposes))
                    target.put("partyContactMechPurposes", partyContactMechPurposes);
            }
            
            try {
                contactMech = delegator.findByPrimaryKey("ContactMech", UtilMisc.toMap("contactMechId", contactMechId));
            } catch (GenericEntityException e) {
                Debug.logWarning(e, module);
            }
            
            if (contactMech != null) {
                target.put("contactMech", contactMech);
                contactMechTypeId = contactMech.getString("contactMechTypeId");
            }
        }
        
        if (contactMechTypeId != null) {
            target.put("contactMechTypeId", contactMechTypeId);
            
            try {
                GenericValue contactMechType = delegator.findByPrimaryKey("ContactMechType", UtilMisc.toMap("contactMechTypeId", contactMechTypeId));
                
                if (contactMechType != null)
                    target.put("contactMechType", contactMechType);
            } catch (GenericEntityException e) {
                Debug.logWarning(e, module);
            }
            
            Collection<GenericValue> purposeTypes = FastList.newInstance();
            Iterator<GenericValue> typePurposes = null;
            
            try {
                typePurposes = UtilMisc.toIterator(delegator.findByAnd("ContactMechTypePurpose", UtilMisc.toMap("contactMechTypeId", contactMechTypeId)));
            } catch (GenericEntityException e) {
                Debug.logWarning(e, module);
            }
            while (typePurposes != null && typePurposes.hasNext()) {
                GenericValue contactMechTypePurpose = typePurposes.next();
                GenericValue contactMechPurposeType = null;
                
                try {
                    contactMechPurposeType = contactMechTypePurpose.getRelatedOne("ContactMechPurposeType");
                } catch (GenericEntityException e) {
                    Debug.logWarning(e, module);
                }
                if (contactMechPurposeType != null) {
                    purposeTypes.add(contactMechPurposeType);
                }
            }
            if (purposeTypes.size() > 0)
                target.put("purposeTypes", purposeTypes);
        }
        
        String requestName;
        
        if (contactMech == null) {
            // create
            if ("POSTAL_ADDRESS".equals(contactMechTypeId)) {
                if (request.getParameter("contactMechPurposeTypeId") != null || request.getAttribute("contactMechPurposeTypeId") != null) {
                    requestName = "createPostalAddressAndPurpose";
                } else {
                    requestName = "createPostalAddress";
                }
            } else if ("TELECOM_NUMBER".equals(contactMechTypeId)) {
                requestName = "createTelecomNumber";
            } else if ("EMAIL_ADDRESS".equals(contactMechTypeId)) {
                requestName = "createEmailAddress";
            } else {
                requestName = "createContactMech";
            }
        } else {
            // update
            if ("POSTAL_ADDRESS".equals(contactMechTypeId)) {
                requestName = "updatePostalAddress";
            } else if ("TELECOM_NUMBER".equals(contactMechTypeId)) {
                requestName = "updateTelecomNumber";
            } else if ("EMAIL_ADDRESS".equals(contactMechTypeId)) {
                requestName = "updateEmailAddress";
            } else {
                requestName = "updateContactMech";
            }
        }
        target.put("requestName", requestName);
        
        if ("POSTAL_ADDRESS".equals(contactMechTypeId)) {
            GenericValue postalAddress = null;
            
            try {
                if (contactMech != null) postalAddress = contactMech.getRelatedOne("PostalAddress");
            } catch (GenericEntityException e) {
                Debug.logWarning(e, module);
            }
            if (postalAddress != null) target.put("postalAddress", postalAddress);
        } else if ("TELECOM_NUMBER".equals(contactMechTypeId)) {
            GenericValue telecomNumber = null;
            
            try {
                if (contactMech != null) telecomNumber = contactMech.getRelatedOne("TelecomNumber");
            } catch (GenericEntityException e) {
                Debug.logWarning(e, module);
            }
            if (telecomNumber != null) target.put("telecomNumber", telecomNumber);
        }
        
        if ("true".equals(request.getParameter("useValues"))) tryEntity = true;
        target.put("tryEntity", Boolean.valueOf(tryEntity));
        
        try {
            Collection<GenericValue> contactMechTypes = delegator.findList("ContactMechType", null, null, null, null, true);
            
            if (contactMechTypes != null) {
                target.put("contactMechTypes", contactMechTypes);
            }
        } catch (GenericEntityException e) {
            Debug.logWarning(e, module);
        }
    }
    
    /**
     * Returns the first valid FacilityContactMech found based on the given facilityId and a prioritized list of purposes
     *
     * @param delegator    the delegator
     * @param facilityId   the facility id
     * @param purposeTypes a List of ContactMechPurposeType ids which will be checked one at a time until a valid contact mech is found
     * @return returns the first valid FacilityContactMech found based on the given facilityId and a prioritized list of purposes
     */
    public static GenericValue getFacilityContactMechByPurpose(Delegator delegator, String facilityId, List<String> purposeTypes) {
        if (UtilValidate.isEmpty(facilityId)) return null;
        if (UtilValidate.isEmpty(purposeTypes)) return null;
        
        for (String purposeType : purposeTypes) {
            List<GenericValue> facilityContactMechPurposes = null;
            List<EntityCondition> conditionList = FastList.newInstance();
            conditionList.add(EntityCondition.makeCondition("facilityId", facilityId));
            conditionList.add(EntityCondition.makeCondition("contactMechPurposeTypeId", purposeType));
            EntityCondition entityCondition = EntityCondition.makeCondition(conditionList);
            try {
                facilityContactMechPurposes = delegator.findList("FacilityContactMechPurpose", entityCondition, null, UtilMisc.toList("-fromDate"), null, true);
                facilityContactMechPurposes = EntityUtil.filterByDate(facilityContactMechPurposes);
            } catch (GenericEntityException e) {
                Debug.logWarning(e, module);
                continue;
            }
            for (GenericValue facilityContactMechPurpose : facilityContactMechPurposes) {
                String contactMechId = facilityContactMechPurpose.getString("contactMechId");
                List<GenericValue> facilityContactMechs = null;
                conditionList = FastList.newInstance();
                conditionList.add(EntityCondition.makeCondition("facilityId", facilityId));
                conditionList.add(EntityCondition.makeCondition("contactMechId", contactMechId));
                entityCondition = EntityCondition.makeCondition(conditionList);
                try {
                    facilityContactMechs = delegator.findList("FacilityContactMech", entityCondition, null, UtilMisc.toList("-fromDate"), null, true);
                    facilityContactMechs = EntityUtil.filterByDate(facilityContactMechs);
                } catch (GenericEntityException e) {
                    Debug.logWarning(e, module);
                }
                if (UtilValidate.isNotEmpty(facilityContactMechs)) {
                    return EntityUtil.getFirst(facilityContactMechs);
                }
            }
            
        }
        return null;
    }
    
    public static void getFacilityContactMechAndRelated(ServletRequest request, String facilityId, Map<String, Object> target) {
        Delegator delegator = (Delegator) request.getAttribute("delegator");
        
        boolean tryEntity = true;
        if (request.getAttribute("_ERROR_MESSAGE") != null) tryEntity = false;
        if ("true".equals(request.getParameter("tryEntity"))) tryEntity = true;
        
        String donePage = request.getParameter("DONE_PAGE");
        if (donePage == null) donePage = (String) request.getAttribute("DONE_PAGE");
        if (donePage == null || donePage.length() <= 0) donePage = "viewprofile";
        target.put("donePage", donePage);
        
        String contactMechTypeId = request.getParameter("preContactMechTypeId");
        
        if (contactMechTypeId == null) contactMechTypeId = (String) request.getAttribute("preContactMechTypeId");
        if (contactMechTypeId != null)
            tryEntity = false;
        
        String contactMechId = request.getParameter("contactMechId");
        
        if (request.getAttribute("contactMechId") != null)
            contactMechId = (String) request.getAttribute("contactMechId");
        
        GenericValue contactMech = null;
        
        if (contactMechId != null) {
            target.put("contactMechId", contactMechId);
            
            // try to find a PartyContactMech with a valid date range
            List<GenericValue> facilityContactMechs = null;
            
            try {
                facilityContactMechs = EntityUtil.filterByDate(delegator.findByAnd("FacilityContactMech", UtilMisc.toMap("facilityId", facilityId, "contactMechId", contactMechId)), true);
            } catch (GenericEntityException e) {
                Debug.logWarning(e, module);
            }
            
            GenericValue facilityContactMech = EntityUtil.getFirst(facilityContactMechs);
            
            if (facilityContactMech != null) {
                target.put("facilityContactMech", facilityContactMech);
                
                Collection<GenericValue> facilityContactMechPurposes = null;
                
                try {
                    facilityContactMechPurposes = EntityUtil.filterByDate(facilityContactMech.getRelated("FacilityContactMechPurpose"), true);
                } catch (GenericEntityException e) {
                    Debug.logWarning(e, module);
                }
                if (UtilValidate.isNotEmpty(facilityContactMechPurposes))
                    target.put("facilityContactMechPurposes", facilityContactMechPurposes);
            }
            
            try {
                contactMech = delegator.findByPrimaryKey("ContactMech", UtilMisc.toMap("contactMechId", contactMechId));
            } catch (GenericEntityException e) {
                Debug.logWarning(e, module);
            }
            
            if (contactMech != null) {
                target.put("contactMech", contactMech);
                contactMechTypeId = contactMech.getString("contactMechTypeId");
            }
        }
        
        if (contactMechTypeId != null) {
            target.put("contactMechTypeId", contactMechTypeId);
            
            try {
                GenericValue contactMechType = delegator.findByPrimaryKey("ContactMechType", UtilMisc.toMap("contactMechTypeId", contactMechTypeId));
                
                if (contactMechType != null)
                    target.put("contactMechType", contactMechType);
            } catch (GenericEntityException e) {
                Debug.logWarning(e, module);
            }
            
            Collection<GenericValue> purposeTypes = FastList.newInstance();
            Iterator<GenericValue> typePurposes = null;
            
            try {
                typePurposes = UtilMisc.toIterator(delegator.findByAnd("ContactMechTypePurpose", UtilMisc.toMap("contactMechTypeId", contactMechTypeId)));
            } catch (GenericEntityException e) {
                Debug.logWarning(e, module);
            }
            while (typePurposes != null && typePurposes.hasNext()) {
                GenericValue contactMechTypePurpose = typePurposes.next();
                GenericValue contactMechPurposeType = null;
                
                try {
                    contactMechPurposeType = contactMechTypePurpose.getRelatedOne("ContactMechPurposeType");
                } catch (GenericEntityException e) {
                    Debug.logWarning(e, module);
                }
                if (contactMechPurposeType != null) {
                    purposeTypes.add(contactMechPurposeType);
                }
            }
            if (purposeTypes.size() > 0)
                target.put("purposeTypes", purposeTypes);
        }
        
        String requestName;
        
        if (contactMech == null) {
            // create
            if ("POSTAL_ADDRESS".equals(contactMechTypeId)) {
                if (request.getParameter("contactMechPurposeTypeId") != null || request.getAttribute("contactMechPurposeTypeId") != null) {
                    requestName = "createPostalAddressAndPurpose";
                } else {
                    requestName = "createPostalAddress";
                }
            } else if ("TELECOM_NUMBER".equals(contactMechTypeId)) {
                requestName = "createTelecomNumber";
            } else if ("EMAIL_ADDRESS".equals(contactMechTypeId)) {
                requestName = "createEmailAddress";
            } else {
                requestName = "createContactMech";
            }
        } else {
            // update
            if ("POSTAL_ADDRESS".equals(contactMechTypeId)) {
                requestName = "updatePostalAddress";
            } else if ("TELECOM_NUMBER".equals(contactMechTypeId)) {
                requestName = "updateTelecomNumber";
            } else if ("EMAIL_ADDRESS".equals(contactMechTypeId)) {
                requestName = "updateEmailAddress";
            } else {
                requestName = "updateContactMech";
            }
        }
        target.put("requestName", requestName);
        
        if ("POSTAL_ADDRESS".equals(contactMechTypeId)) {
            GenericValue postalAddress = null;
            
            try {
                if (contactMech != null) postalAddress = contactMech.getRelatedOne("PostalAddress");
            } catch (GenericEntityException e) {
                Debug.logWarning(e, module);
            }
            if (postalAddress != null) target.put("postalAddress", postalAddress);
        } else if ("TELECOM_NUMBER".equals(contactMechTypeId)) {
            GenericValue telecomNumber = null;
            
            try {
                if (contactMech != null) telecomNumber = contactMech.getRelatedOne("TelecomNumber");
            } catch (GenericEntityException e) {
                Debug.logWarning(e, module);
            }
            if (telecomNumber != null) target.put("telecomNumber", telecomNumber);
        }
        
        if ("true".equals(request.getParameter("useValues"))) tryEntity = true;
        target.put("tryEntity", Boolean.valueOf(tryEntity));
        
        try {
            Collection<GenericValue> contactMechTypes = delegator.findList("ContactMechType", null, null, null, null, true);
            
            if (contactMechTypes != null) {
                target.put("contactMechTypes", contactMechTypes);
            }
        } catch (GenericEntityException e) {
            Debug.logWarning(e, module);
        }
    }
    
    
    public static void getProductStoreContactMechAndRelated(ServletRequest request, String productStoreId, Map<String, Object> target) {
        Delegator delegator = (Delegator) request.getAttribute("delegator");
        
        boolean tryEntity = true;
        if (request.getAttribute("_ERROR_MESSAGE") != null) tryEntity = false;
        if ("true".equals(request.getParameter("tryEntity"))) tryEntity = true;
        
        String donePage = request.getParameter("DONE_PAGE");
        if (donePage == null) donePage = (String) request.getAttribute("DONE_PAGE");
        if (donePage == null || donePage.length() <= 0) donePage = "viewprofile";
        target.put("donePage", donePage);
        
        String contactMechTypeId = request.getParameter("preContactMechTypeId");
        
        if (contactMechTypeId == null) contactMechTypeId = (String) request.getAttribute("preContactMechTypeId");
        if (contactMechTypeId != null)
            tryEntity = false;
        
        String contactMechId = request.getParameter("contactMechId");
        
        if (request.getAttribute("contactMechId") != null)
            contactMechId = (String) request.getAttribute("contactMechId");
        
        GenericValue contactMech = null;
        
        if (contactMechId != null) {
            target.put("contactMechId", contactMechId);
            
            // try to find a PartyContactMech with a valid date range
            List<GenericValue> productStoreContactMechs = null;
            
            try {
                productStoreContactMechs = EntityUtil.filterByDate(delegator.findByAnd("ProductStoreContactMech", UtilMisc.toMap("productStoreId", productStoreId, "contactMechId", contactMechId)), true);
            } catch (GenericEntityException e) {
                Debug.logWarning(e, module);
            }
            
            GenericValue productStoreContactMech = EntityUtil.getFirst(productStoreContactMechs);
            
            if (productStoreContactMech != null) {
                target.put("productStoreContactMech", productStoreContactMech);
                
                Collection<GenericValue> productStoreContactMechPurposes = null;
                
                try {
                    productStoreContactMechPurposes = EntityUtil.filterByDate(productStoreContactMech.getRelated("ProductStoreContactMechPurpose"), true);
                } catch (GenericEntityException e) {
                    Debug.logWarning(e, module);
                }
                if (UtilValidate.isNotEmpty(productStoreContactMechPurposes))
                    target.put("productStoreContactMechPurposes", productStoreContactMechPurposes);
            }
            
            try {
                contactMech = delegator.findByPrimaryKey("ContactMech", UtilMisc.toMap("contactMechId", contactMechId));
            } catch (GenericEntityException e) {
                Debug.logWarning(e, module);
            }
            
            if (contactMech != null) {
                target.put("contactMech", contactMech);
                contactMechTypeId = contactMech.getString("contactMechTypeId");
            }
        }
        
        if (contactMechTypeId != null) {
            target.put("contactMechTypeId", contactMechTypeId);
            
            try {
                GenericValue contactMechType = delegator.findByPrimaryKey("ContactMechType", UtilMisc.toMap("contactMechTypeId", contactMechTypeId));
                
                if (contactMechType != null)
                    target.put("contactMechType", contactMechType);
            } catch (GenericEntityException e) {
                Debug.logWarning(e, module);
            }
            
            Collection<GenericValue> purposeTypes = FastList.newInstance();
            Iterator<GenericValue> typePurposes = null;
            
            try {
                typePurposes = UtilMisc.toIterator(delegator.findByAnd("ContactMechTypePurpose", UtilMisc.toMap("contactMechTypeId", contactMechTypeId)));
            } catch (GenericEntityException e) {
                Debug.logWarning(e, module);
            }
            while (typePurposes != null && typePurposes.hasNext()) {
                GenericValue contactMechTypePurpose = typePurposes.next();
                GenericValue contactMechPurposeType = null;
                
                try {
                    contactMechPurposeType = contactMechTypePurpose.getRelatedOne("ContactMechPurposeType");
                } catch (GenericEntityException e) {
                    Debug.logWarning(e, module);
                }
                if (contactMechPurposeType != null) {
                    purposeTypes.add(contactMechPurposeType);
                }
            }
            if (purposeTypes.size() > 0)
                target.put("purposeTypes", purposeTypes);
        }
        
        String requestName;
        
        if (contactMech == null) {
            // create
            if ("POSTAL_ADDRESS".equals(contactMechTypeId)) {
                if (request.getParameter("contactMechPurposeTypeId") != null || request.getAttribute("contactMechPurposeTypeId") != null) {
                    requestName = "createPostalAddressAndPurpose";
                } else {
                    requestName = "createPostalAddress";
                }
            } else if ("TELECOM_NUMBER".equals(contactMechTypeId)) {
                requestName = "createTelecomNumber";
            } else if ("EMAIL_ADDRESS".equals(contactMechTypeId)) {
                requestName = "createEmailAddress";
            } else {
                requestName = "createContactMech";
            }
        } else {
            // update
            if ("POSTAL_ADDRESS".equals(contactMechTypeId)) {
                requestName = "updatePostalAddress";
            } else if ("TELECOM_NUMBER".equals(contactMechTypeId)) {
                requestName = "updateTelecomNumber";
            } else if ("EMAIL_ADDRESS".equals(contactMechTypeId)) {
                requestName = "updateEmailAddress";
            } else {
                requestName = "updateContactMech";
            }
        }
        target.put("requestName", requestName);
        
        if ("POSTAL_ADDRESS".equals(contactMechTypeId)) {
            GenericValue postalAddress = null;
            
            try {
                if (contactMech != null) postalAddress = contactMech.getRelatedOne("PostalAddress");
            } catch (GenericEntityException e) {
                Debug.logWarning(e, module);
            }
            if (postalAddress != null) target.put("postalAddress", postalAddress);
        } else if ("TELECOM_NUMBER".equals(contactMechTypeId)) {
            GenericValue telecomNumber = null;
            
            try {
                if (contactMech != null) telecomNumber = contactMech.getRelatedOne("TelecomNumber");
            } catch (GenericEntityException e) {
                Debug.logWarning(e, module);
            }
            if (telecomNumber != null) target.put("telecomNumber", telecomNumber);
        }
        
        if ("true".equals(request.getParameter("useValues"))) tryEntity = true;
        target.put("tryEntity", Boolean.valueOf(tryEntity));
        
        try {
            Collection<GenericValue> contactMechTypes = delegator.findList("ContactMechType", null, null, null, null, true);
            
            if (contactMechTypes != null) {
                target.put("contactMechTypes", contactMechTypes);
            }
        } catch (GenericEntityException e) {
            Debug.logWarning(e, module);
        }
    }
    
    public static List<Map<String, Object>> getPartyPostalAddresses(ServletRequest request, String partyId, String curContactMechId) {
        Delegator delegator = (Delegator) request.getAttribute("delegator");
        List<Map<String, Object>> postalAddressInfos = FastList.newInstance();
        
        List<GenericValue> allPartyContactMechs = null;
        
        try {
            allPartyContactMechs = EntityUtil.filterByDate(delegator.findByAnd("PartyContactMech", UtilMisc.toMap("partyId", partyId)), true);
        } catch (GenericEntityException e) {
            Debug.logWarning(e, module);
        }
        
        if (allPartyContactMechs == null) return postalAddressInfos;
        
        for (GenericValue partyContactMech : allPartyContactMechs) {
            GenericValue contactMech = null;
            
            try {
                contactMech = partyContactMech.getRelatedOne("ContactMech");
            } catch (GenericEntityException e) {
                Debug.logWarning(e, module);
            }
            if (contactMech != null && "POSTAL_ADDRESS".equals(contactMech.getString("contactMechTypeId")) && !contactMech.getString("contactMechId").equals(curContactMechId)) {
                Map<String, Object> postalAddressInfo = FastMap.newInstance();
                
                postalAddressInfos.add(postalAddressInfo);
                postalAddressInfo.put("contactMech", contactMech);
                postalAddressInfo.put("partyContactMech", partyContactMech);
                
                try {
                    GenericValue postalAddress = contactMech.getRelatedOne("PostalAddress");
                    postalAddressInfo.put("postalAddress", postalAddress);
                } catch (GenericEntityException e) {
                    Debug.logWarning(e, module);
                }
                
                try {
                    List<GenericValue> partyContactMechPurposes = EntityUtil.filterByDate(partyContactMech.getRelated("PartyContactMechPurpose"), true);
                    postalAddressInfo.put("partyContactMechPurposes", partyContactMechPurposes);
                } catch (GenericEntityException e) {
                    Debug.logWarning(e, module);
                }
            }
        }
        
        return postalAddressInfos;
    }
    
    public static Map<String, Object> getCurrentPostalAddress(ServletRequest request, String partyId, String curContactMechId) {
        Delegator delegator = (Delegator) request.getAttribute("delegator");
        Map<String, Object> results = FastMap.newInstance();
        
        if (curContactMechId != null) {
            List<GenericValue> partyContactMechs = null;
            
            try {
                partyContactMechs = EntityUtil.filterByDate(delegator.findByAnd("PartyContactMech", UtilMisc.toMap("partyId", partyId, "contactMechId", curContactMechId)), true);
            } catch (GenericEntityException e) {
                Debug.logWarning(e, module);
            }
            GenericValue curPartyContactMech = EntityUtil.getFirst(partyContactMechs);
            results.put("curPartyContactMech", curPartyContactMech);
            
            GenericValue curContactMech = null;
            if (curPartyContactMech != null) {
                try {
                    curContactMech = curPartyContactMech.getRelatedOne("ContactMech");
                } catch (GenericEntityException e) {
                    Debug.logWarning(e, module);
                }
                
                Collection<GenericValue> curPartyContactMechPurposes = null;
                try {
                    curPartyContactMechPurposes = EntityUtil.filterByDate(curPartyContactMech.getRelated("PartyContactMechPurpose"), true);
                } catch (GenericEntityException e) {
                    Debug.logWarning(e, module);
                }
                results.put("curPartyContactMechPurposes", curPartyContactMechPurposes);
            }
            results.put("curContactMech", curContactMech);
            
            GenericValue curPostalAddress = null;
            if (curContactMech != null) {
                try {
                    curPostalAddress = curContactMech.getRelatedOne("PostalAddress");
                } catch (GenericEntityException e) {
                    Debug.logWarning(e, module);
                }
            }
            
            results.put("curPostalAddress", curPostalAddress);
        }
        return results;
    }
    
    public static boolean isUspsAddress(GenericValue postalAddress) {
        if (postalAddress == null) {
            // null postal address is not a USPS address
            return false;
        }
        if (!"PostalAddress".equals(postalAddress.getEntityName())) {
            // not a postal address not a USPS address
            return false;
        }
        
        // get and clean the address strings
        String addr1 = postalAddress.getString("address1");
        String addr2 = postalAddress.getString("address2");
        
        // get the matching string from general.properties
        String matcher = EntityUtilProperties.getPropertyValue("general.properties", "usps.address.match", postalAddress.getDelegator());
        if (UtilValidate.isNotEmpty(matcher)) {
            if (addr1 != null && addr1.toLowerCase().matches(matcher)) {
                return true;
            }
            if (addr2 != null && addr2.toLowerCase().matches(matcher)) {
                return true;
            }
        }
        
        return false;
    }
    
    public static boolean isCompanyAddress(GenericValue postalAddress, String companyPartyId) {
        if (postalAddress == null) {
            // null postal address is not an internal address
            return false;
        }
        if (!"PostalAddress".equals(postalAddress.getEntityName())) {
            // not a postal address not an internal address
            return false;
        }
        if (companyPartyId == null) {
            // no partyId not an internal address
            return false;
        }
        
        String state = postalAddress.getString("stateProvinceGeoId");
        String addr1 = postalAddress.getString("address1");
        String addr2 = postalAddress.getString("address2");
        if (state != null) {
            state = state.replaceAll("\\W", "").toLowerCase();
        } else {
            state = "";
        }
        if (addr1 != null) {
            addr1 = addr1.replaceAll("\\W", "").toLowerCase();
        } else {
            addr1 = "";
        }
        if (addr2 != null) {
            addr2 = addr2.replaceAll("\\W", "").toLowerCase();
        } else {
            addr2 = "";
        }
        
        // get all company addresses
        Delegator delegator = postalAddress.getDelegator();
        List<GenericValue> postalAddresses = FastList.newInstance();
        try {
            List<GenericValue> partyContactMechs = delegator.findByAnd("PartyContactMech", UtilMisc.toMap("partyId", companyPartyId));
            partyContactMechs = EntityUtil.filterByDate(partyContactMechs);
            if (partyContactMechs != null) {
                for (GenericValue pcm : partyContactMechs) {
                    GenericValue addr = pcm.getRelatedOne("PostalAddress");
                    if (addr != null) {
                        postalAddresses.add(addr);
                    }
                }
            }
        } catch (GenericEntityException e) {
            Debug.logError(e, "Unable to get party postal addresses", module);
        }
        
        if (postalAddresses != null) {
            for (GenericValue addr : postalAddresses) {
                String thisAddr1 = addr.getString("address1");
                String thisAddr2 = addr.getString("address2");
                String thisState = addr.getString("stateProvinceGeoId");
                if (thisState != null) {
                    thisState = thisState.replaceAll("\\W", "").toLowerCase();
                } else {
                    thisState = "";
                }
                if (thisAddr1 != null) {
                    thisAddr1 = thisAddr1.replaceAll("\\W", "").toLowerCase();
                } else {
                    thisAddr1 = "";
                }
                if (thisAddr2 != null) {
                    thisAddr2 = thisAddr2.replaceAll("\\W", "").toLowerCase();
                } else {
                    thisAddr2 = "";
                }
                if (thisAddr1.equals(addr1) && thisAddr2.equals(addr2) && thisState.equals(state)) {
                    return true;
                }
            }
        }
        
        return false;
    }
    
    public static String getContactMechAttribute(Delegator delegator, String contactMechId, String attrName) {
        GenericValue attr = null;
        try {
            attr = delegator.findByPrimaryKey("ContactMechAttribute", UtilMisc.toMap("contactMechId", contactMechId, "attrName", attrName));
        } catch (GenericEntityException e) {
            Debug.logError(e, module);
        }
        if (attr == null) {
            return null;
        } else {
            return attr.getString("attrValue");
        }
    }
    
    public static String getPostalAddressPostalCodeGeoId(GenericValue postalAddress, Delegator delegator) throws GenericEntityException {
        // if postalCodeGeoId not empty use that
        if (UtilValidate.isNotEmpty(postalAddress.getString("postalCodeGeoId"))) {
            return postalAddress.getString("postalCodeGeoId");
        }
        
        // no postalCodeGeoId, see if there is a Geo record matching the countryGeoId and postalCode fields
        if (UtilValidate.isNotEmpty(postalAddress.getString("countryGeoId")) && UtilValidate.isNotEmpty(postalAddress.getString("postalCode"))) {
            // first try the shortcut with the geoId convention for "{countryGeoId}-{postalCode}"
            GenericValue geo = delegator.findByPrimaryKeyCache("Geo", UtilMisc.toMap("geoId", postalAddress.getString("countryGeoId") + "-" + postalAddress.getString("postalCode")));
            if (geo != null) {
                // save the value to the database for quicker future reference
                if (postalAddress.isMutable()) {
                    postalAddress.set("postalCodeGeoId", geo.getString("geoId"));
                    postalAddress.store();
                } else {
                    GenericValue mutablePostalAddress = delegator.findByPrimaryKey("PostalAddress", UtilMisc.toMap("contactMechId", postalAddress.getString("contactMechId")));
                    mutablePostalAddress.set("postalCodeGeoId", geo.getString("geoId"));
                    mutablePostalAddress.store();
                }
                
                return geo.getString("geoId");
            }
            
            // no shortcut, try the longcut to see if there is something with a geoCode associated to the countryGeoId
            List<GenericValue> geoAssocAndGeoToList = delegator.findByAndCache("GeoAssocAndGeoTo",
                    UtilMisc.toMap("geoIdFrom", postalAddress.getString("countryGeoId"), "geoCode", postalAddress.getString("postalCode"), "geoAssocTypeId", "REGIONS"));
            GenericValue geoAssocAndGeoTo = EntityUtil.getFirst(geoAssocAndGeoToList);
            if (geoAssocAndGeoTo != null) {
                // save the value to the database for quicker future reference
                if (postalAddress.isMutable()) {
                    postalAddress.set("postalCodeGeoId", geoAssocAndGeoTo.getString("geoId"));
                    postalAddress.store();
                } else {
                    GenericValue mutablePostalAddress = delegator.findByPrimaryKey("PostalAddress", UtilMisc.toMap("contactMechId", postalAddress.getString("contactMechId")));
                    mutablePostalAddress.set("postalCodeGeoId", geoAssocAndGeoTo.getString("geoId"));
                    mutablePostalAddress.store();
                }
                
                return geoAssocAndGeoTo.getString("geoId");
            }
        }
        
        // nothing found, return null
        return null;
    }
    
    /**
     * Returns a <b>PostalAddress</b> <code>GenericValue</code> as a URL encoded <code>String</code>.
     *
     * @param postalAddress A <b>PostalAddress</b> <code>GenericValue</code>.
     * @return A URL encoded <code>String</code>.
     * @throws GenericEntityException
     * @throws UnsupportedEncodingException
     */
    public static String urlEncodePostalAddress(GenericValue postalAddress) throws GenericEntityException, UnsupportedEncodingException {
        Assert.notNull("postalAddress", postalAddress);
        if (!"PostalAddress".equals(postalAddress.getEntityName())) {
            throw new IllegalArgumentException("postalAddress argument is not a PostalAddress entity");
        }
        StringBuilder sb = new StringBuilder();
        if (postalAddress.get("address1") != null) {
            sb.append(postalAddress.get("address1"));
        }
        if (postalAddress.get("address2") != null) {
            sb.append(", ").append(postalAddress.get("address2"));
        }
        if (postalAddress.get("city") != null) {
            sb.append(", ").append(postalAddress.get("city"));
        }
        if (postalAddress.get("stateProvinceGeoId") != null) {
            GenericValue geoValue = postalAddress.getRelatedOne("StateProvinceGeo");
            if (geoValue != null) {
                sb.append(", ").append(geoValue.get("geoName"));
            }
        } else if (postalAddress.get("countyGeoId") != null) {
            GenericValue geoValue = postalAddress.getRelatedOne("CountyGeo");
            if (geoValue != null) {
                sb.append(", ").append(geoValue.get("geoName"));
            }
        }
        if (postalAddress.get("postalCode") != null) {
            sb.append(", ").append(postalAddress.get("postalCode"));
        }
        if (postalAddress.get("countryGeoId") != null) {
            GenericValue geoValue = postalAddress.getRelatedOne("CountryGeo");
            if (geoValue != null) {
                sb.append(", ").append(geoValue.get("geoName"));
            }
        }
        String postalAddressString = sb.toString().trim();
        while (postalAddressString.contains("  ")) {
            postalAddressString = postalAddressString.replace("  ", " ");
        }
        return URLEncoder.encode(postalAddressString, "UTF-8");
    }
    
    
    /**
     * 获取party的联系方式
     *
     * @param delegator
     * @param partyId
     * @param showOld
     * @param contactMechTypeId
     * @param partyPurposesTypeId
     * @return
     */
    public static List<GenericValue> getPartyContactMechByPurpose(Delegator delegator, String partyId, boolean showOld, String contactMechTypeId, String partyPurposesTypeId) {
        List<GenericValue> partyContactMechValues = FastList.newInstance();
        
        List<GenericValue> allPartyContactMechs = null;
        try {
            List<GenericValue> tempCol = delegator.findByAnd("PartyContactMech", UtilMisc.toMap("partyId", partyId));
            if (contactMechTypeId != null) {
                List<GenericValue> tempColTemp = FastList.newInstance();
                for (GenericValue partyContactMech : tempCol) {
                    GenericValue contactMech = delegator.getRelatedOne("ContactMech", partyContactMech);
                    if (contactMech != null && contactMechTypeId.equals(contactMech.getString("contactMechTypeId"))) {
                        tempColTemp.add(partyContactMech);
                    }
                    
                }
                tempCol = tempColTemp;
            }
            if (!showOld) tempCol = EntityUtil.filterByDate(tempCol, true);
            allPartyContactMechs = tempCol;
        } catch (GenericEntityException e) {
            Debug.logWarning(e, module);
        }
        
        if (allPartyContactMechs == null) return null;
        
        for (GenericValue partyContactMech : allPartyContactMechs) {
            GenericValue contactMech = null;
            try {
                contactMech = partyContactMech.getRelatedOne("ContactMech");
            } catch (GenericEntityException e) {
                Debug.logWarning(e, module);
            }
            if (contactMech != null) {
                try {
                    List<GenericValue> partyContactMechPurposes = partyContactMech.getRelated("PartyContactMechPurpose");
                    if (!showOld) partyContactMechPurposes = EntityUtil.filterByDate(partyContactMechPurposes, true);
                    for (GenericValue partyContactMechPurpose : partyContactMechPurposes) {
                        if (partyContactMechPurpose.get("contactMechPurposeTypeId").equals(partyPurposesTypeId)) {
                            if ("POSTAL_ADDRESS".equals(contactMech.getString("contactMechTypeId"))) {
                                partyContactMechValues.add(contactMech.getRelatedOne("PostalAddress"));
                            } else if ("TELECOM_NUMBER".equals(contactMech.getString("contactMechTypeId"))) {
                                partyContactMechValues.add(contactMech.getRelatedOne("TelecomNumber"));
                            }
                        }
                    }
                } catch (GenericEntityException e) {
                    Debug.logWarning(e, module);
                }
                
                
            }
        }
        
        return partyContactMechValues;
    }
    
    /**
     * 获取默认地址  Add By Changsy
     *
     * @param delegator
     * @param partyId
     * @return
     */
    public static GenericValue getDefaultAddress(Delegator delegator, String partyId, String productStoreId) {
        GenericValue profiledefs = null;
        GenericValue gv = null;
        try {
            if (productStoreId != null) {
                profiledefs = delegator.findByPrimaryKey("PartyProfileDefault", UtilMisc.toMap("partyId", partyId, "productStoreId", productStoreId));
            } else {
                List<GenericValue> profiles = delegator.findByAnd("PartyProfileDefault", UtilMisc.toMap("partyId", partyId));
                if (UtilValidate.isNotEmpty(profiles)) {
                    profiledefs = EntityUtil.getFirst(profiles);
                    
                }
            }
            if(UtilValidate.isNotEmpty(profiledefs)) {
                gv = delegator.findByPrimaryKey("PostalAddress", UtilMisc.toMap("contactMechId", profiledefs.get("defaultShipAddr")));
            }
        } catch (GenericEntityException e) {
            e.printStackTrace();
        }
        return gv;
    }
    
    /**
     * 获取邮件地址  Add By Changsy
     *
     * @param delegator
     * @param partyId
     * @return
     */
    public static String getEmail(Delegator delegator, String partyId) {
        String email = null;
        try {
            List<GenericValue> partyContactMechEmail = delegator.findByAnd("PartyContactMechPurpose", UtilMisc.toMap("partyId", partyId, "contactMechPurposeTypeId", "PRIMARY_EMAIL"));//第一个电子邮件
            if (partyContactMechEmail.size() > 0 && partyContactMechEmail.get(0).get("contactMechId") != null) {
                String contactMechId = (String) partyContactMechEmail.get(0).get("contactMechId");
                List<GenericValue> contactMech = delegator.findByAnd("ContactMech", UtilMisc.toMap("contactMechId", contactMechId));
                if (contactMech.size() > 0 && contactMech.get(0).get("infoString") != null) {
                    email = (String) contactMech.get(0).get("infoString");
                }
            }
        } catch (GenericEntityException e) {
            Debug.logWarning(e, "", module);
            return "error";
        }
        return email;
    }
    
    /**
     * 获取手机号  Add By Changsy
     *
     * @param delegator
     * @param partyId
     * @return
     */
    public static String getMobile(Delegator delegator, String partyId) {
        String mobile = null;
        try {
            List<GenericValue> partyContactMechMobile = delegator.findByAnd("PartyContactMechPurpose", UtilMisc.toMap("partyId", partyId, "contactMechPurposeTypeId", "PHONE_MOBILE"));//主要移动电话
            if (partyContactMechMobile.size() > 0 && partyContactMechMobile.get(0).get("contactMechId") != null) {
                String contactMechId = (String) partyContactMechMobile.get(0).get("contactMechId");
                List<GenericValue> telecomNumber = delegator.findByAnd("TelecomNumber", UtilMisc.toMap("contactMechId", contactMechId));
                if (telecomNumber.size() > 0 && telecomNumber.get(0).get("contactNumber") != null) {
                    mobile = (String) telecomNumber.get(0).get("contactNumber");
                }
            }
        } catch (GenericEntityException e) {
            Debug.logWarning(e, "", module);
            return "error";
        }
        return mobile;
    }
    
    /**
     * 获取地址  Add By Changsy
     *
     * @param delegator
     * @param partyId
     * @return
     */
    public static void getContactMech(Delegator delegator, String partyId, Map<String, Object> map) {
        try {
            List<GenericValue> partyContactMechMobile = delegator.findByAnd("PartyContactMechPurpose", UtilMisc.toMap("partyId", partyId, "contactMechPurposeTypeId", "PRIMARY_LOCATION"));//首选联系地址
            if (partyContactMechMobile.size() > 0 && partyContactMechMobile.get(0).get("contactMechId") != null) {
                partyContactMechMobile = EntityUtil.filterByDate(partyContactMechMobile);
                if (!partyContactMechMobile.isEmpty()) {
                    GenericValue pa = EntityUtil.getFirst(partyContactMechMobile);
                    String contactMechId = (String) pa.get("contactMechId");
                    List<GenericValue> postalAddress = delegator.findByAnd("PostalAddress", UtilMisc.toMap("contactMechId", contactMechId));
                    if (postalAddress.size() > 0) {
                        map.put("postalAddress", postalAddress.get(0));
                    }
                }
            }
        } catch (GenericEntityException e) {
            Debug.logWarning(e, "", module);
        }
    }
    
    /**
     * 获取头像Id   Add By Changsy
     *
     * @param delegator
     * @param partyId
     * @return
     */
    public static String getDataResourceId(Delegator delegator, String partyId) {
        String dataResourceId = null;
        try {
            List<GenericValue> partyContent = delegator.findByAnd("PartyContent", UtilMisc.toMap("partyId", partyId, "partyContentTypeId", "LGOIMGURL"));
            if (partyContent.size() > 0) {
                for (int i = 0; i < partyContent.size(); i++) {
                    List<GenericValue> content = delegator.findByAnd("Content", UtilMisc.toMap("contentId", partyContent.get(0).get("contentId"), "contentTypeId", "PERSON_LOGO"));
                    if (content.size() > 0) {
                        dataResourceId = (String) content.get(0).get("dataResourceId");
                    }
                }
            }
        } catch (GenericEntityException e) {
            Debug.logWarning(e, "", module);
            return "error";
        }
        return dataResourceId;
    }
    
    /**
     * 删除已有头像  Add By Changsy
     *
     * @param request
     * @param response
     * @return
     */
    public static String deletePartyContentAjax(ServletRequest request, ServletResponse response) {
        Delegator delegator = (Delegator) request.getAttribute("delegator");
        String partyId = request.getParameter("partyId");
        try {
            List<GenericValue> partyContent = delegator.findByAnd("PartyContent", UtilMisc.toMap("partyId", partyId, "partyContentTypeId", "LGOIMGURL"));
            if (partyContent.size() > 0) {
                for (int i = 0; i < partyContent.size(); i++) {
                    partyContent.get(i).remove();
                }
            }
        } catch (GenericEntityException e) {
            Debug.logWarning(e, "", module);
            return "error";
        }
        return "success";
    }
    
    /**
     * 获取地址  Add By xdw
     *
     * @param delegator
     * @param partyId
     * @return
     */
    public static void getContactMechForWap(Delegator delegator, String partyId, Map<String, Object> map) {
        try {
            List<GenericValue> partyContactMechMobile = delegator.findByAnd("PartyContactMechPurpose", UtilMisc.toMap("partyId", partyId, "contactMechPurposeTypeId", "PRIMARY_LOCATION", "thruDate", null));//首选联系地址
            if (partyContactMechMobile.size() > 0 && partyContactMechMobile.get(0).get("contactMechId") != null) {
                String contactMechId = (String) partyContactMechMobile.get(0).get("contactMechId");
                List<GenericValue> postalAddress = delegator.findByAnd("PostalAddress", UtilMisc.toMap("contactMechId", contactMechId));
                if (postalAddress.size() > 0) {
                    map.put("postalAddress", postalAddress.get(0));
                }
            }
        } catch (GenericEntityException e) {
            Debug.logWarning(e, "", module);
        }
    }
    
    
}
