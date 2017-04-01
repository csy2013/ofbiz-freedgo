import org.ofbiz.party.contact.ContactMechWorker

productStoreId = parameters.productStoreId
context.productStoreId = productStoreId

productStore = delegator.findOne("ProductStore", [productStoreId : productStoreId], false)
context.productStore = productStore

mechMap = [:]
ContactMechWorker.getProductStoreContactMechAndRelated(request, productStoreId, mechMap)
context.mechMap = mechMap

contactMechId = mechMap.contactMechId
if (contactMechId) {
    context.contactMechId = contactMechId
}

preContactMechTypeId = request.getParameter("preContactMechTypeId")
if (preContactMechTypeId) {
    context.preContactMechTypeId = preContactMechTypeId
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
