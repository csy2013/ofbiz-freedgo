

import org.ofbiz.entity.condition.EntityCondition

// get physicalInventoryAndVarianceDatas if this is a NON_SERIAL_INV_ITEM
if (inventoryItem && "NON_SERIAL_INV_ITEM".equals(inventoryItem.inventoryItemTypeId)) {
    physicalInventoryAndVariances = delegator.findList("PhysicalInventoryAndVariance", EntityCondition.makeCondition([inventoryItemId : inventoryItemId]), null, ['-physicalInventoryDate', '-physicalInventoryId'], null, false)
    physicalInventoryAndVarianceDatas = new ArrayList(physicalInventoryAndVariances.size())
    physicalInventoryAndVariances.each { physicalInventoryAndVariance ->
        physicalInventoryAndVarianceData = [:]
        physicalInventoryAndVarianceDatas.add(physicalInventoryAndVarianceData)

        physicalInventoryAndVarianceData.physicalInventoryAndVariance = physicalInventoryAndVariance
        physicalInventoryAndVarianceData.varianceReason = physicalInventoryAndVariance.getRelatedOneCache("VarianceReason")
        physicalInventoryAndVarianceData.person = physicalInventoryAndVariance.getRelatedOne("Person")
        physicalInventoryAndVarianceData.partyGroup = physicalInventoryAndVariance.getRelatedOne("PartyGroup")
    }
    context.physicalInventoryAndVarianceDatas = physicalInventoryAndVarianceDatas
}
