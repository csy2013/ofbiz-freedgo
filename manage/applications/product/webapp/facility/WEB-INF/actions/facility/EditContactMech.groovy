import org.ofbiz.party.contact.ContactMechWorker

facilityId = parameters.facilityId
context.facilityId = facilityId

facility = delegator.findOne("Facility", [facilityId : facilityId], false)
context.facility = facility

mechMap = [:]
ContactMechWorker.getFacilityContactMechAndRelated(request, facilityId, mechMap)
context.mechMap = mechMap

contactMechId = mechMap.contactMechId
if (contactMechId) {
    context.contactMechId = contactMechId
}

preContactMechTypeId = request.getParameter("preContactMechTypeId")
if (preContactMechTypeId) {
    context.preContactMechTypeId = preContactMechTypeId
}

paymentMethodId = request.getParameter("paymentMethodId")
if (!paymentMethodId) {
    paymentMethodId = request.getAttribute("paymentMethodId")
}
if (paymentMethodId) {
    context.paymentMethodId = paymentMethodId
}

donePage = request.getParameter("DONE_PAGE")
if (!donePage) {
    donePage = request.getAttribute("DONE_PAGE")
}
if (!donePage || donePage.length() <= 0) {
    donePage = "ViewContactMechs"
}
context.donePage = donePage

cmNewPurposeTypeId = request.getParameter("contactMechPurposeTypeId")
if (!cmNewPurposeTypeId) {
    cmNewPurposeTypeId = mechMap.contactMechPurposeTypeId
}
if (cmNewPurposeTypeId) {
    context.contactMechPurposeTypeId = cmNewPurposeTypeId
    contactMechPurposeType = delegator.findOne("ContactMechPurposeType", [contactMechPurposeTypeId : cmNewPurposeTypeId], false)
    if (contactMechPurposeType) {
        context.contactMechPurposeType = contactMechPurposeType
    }
}
