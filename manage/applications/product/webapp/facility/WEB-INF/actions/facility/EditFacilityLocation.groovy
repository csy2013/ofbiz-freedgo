import org.ofbiz.entity.condition.EntityCondition

facilityId = request.getParameter("facilityId")
locationSeqId = request.getParameter("locationSeqId")
facility = null
facilityLocation = null

if (!facilityId && request.getAttribute("facilityId")) {
    facilityId = request.getAttribute("facilityId")
}

if (!locationSeqId && request.getAttribute("locationSeqId")) {
    locationSeqId = request.getAttribute("locationSeqId")
}

if (facilityId && locationSeqId) {
    facilityLocation = delegator.findOne("FacilityLocation", [facilityId : facilityId, locationSeqId : locationSeqId], false)
}
if (facilityId) {
    facility = delegator.findOne("Facility", [facilityId : facilityId], false)
}

locationTypeEnums = delegator.findList("Enumeration", EntityCondition.makeCondition([enumTypeId : 'FACLOC_TYPE']), null, null, null, false)

// ProductFacilityLocation stuff
productFacilityLocations = null
if (facilityLocation) {
    productFacilityLocations = facilityLocation.getRelated("ProductFacilityLocation", null, ['productId'])
}

context.facilityId = facilityId
context.locationSeqId = locationSeqId
context.facility = facility
context.facilityLocation = facilityLocation
context.locationTypeEnums = locationTypeEnums
context.productFacilityLocations = productFacilityLocations
