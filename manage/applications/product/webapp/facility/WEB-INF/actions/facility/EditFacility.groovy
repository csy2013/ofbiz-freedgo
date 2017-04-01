/*
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
 */
import org.ofbiz.entity.condition.EntityCondition
import org.ofbiz.entity.condition.EntityExpr
import org.ofbiz.entity.condition.EntityOperator

facilityId = parameters.facilityId
if (!facilityId && request.getAttribute("facilityId")) {
  facilityId = request.getAttribute("facilityId")
}
facility = delegator.findOne("Facility", [facilityId : facilityId], false)
if (!facility) {
  facility = delegator.makeValue("Facility")
  facilityType = delegator.makeValue("FacilityType")
} else {
  facilityType = facility.getRelatedOne("FacilityType")
}
context.facility = facility
context.facilityType = facilityType
context.facilityId = facilityId

//Facility types
facilityTypes = delegator.findList("FacilityType", null, null, null, null, false)
if (facilityTypes) {
  context.facilityTypes = facilityTypes
}

// all possible inventory item types
context.inventoryItemTypes = delegator.findList("InventoryItemType", null, null, ['description'], null, true)

// weight unit of measures
List exprList = []
EntityExpr entityExpr = EntityCondition.makeCondition('uomId',EntityOperator.NOT_IN,["WT_dr_avdp","WT_dr_avdp","WT_oz","WT_oz_tr","WT_dwt","WT_lb","WT_st","WT_lt","WT_sh_t","WT_gr"])
exprList.add(entityExpr)
exprList.add(EntityCondition.makeCondition([uomTypeId : 'WEIGHT_MEASURE']))

context.weightUomList = delegator.findList("Uom", EntityCondition.makeCondition(exprList, EntityOperator.AND), null, null, null, true)

// area unit of measures
List exprList1=[]
EntityExpr entityExpr1 = EntityCondition.makeCondition('uomId',EntityOperator.NOT_IN,["AREA_A","AREA_cm2","AREA_ft2","AREA_in2","AREA_km2","AREA_mi2","AREA_mm2","AREA_rd2","AREA_yd2"])
exprList1.add(entityExpr1)
exprList1.add(EntityCondition.makeCondition([uomTypeId : 'AREA_MEASURE']))
context.areaUomList = delegator.findList("Uom", EntityCondition.makeCondition(exprList1, EntityOperator.AND), null, null, null, true)
