<div class="table-responsive">
    <table class="table table-striped table-bordered">
  <tr class="header-row-2">
    <th>${uiLabelMap.ProductInventoryItemId}</th>
    <th>${uiLabelMap.ProductFacilityId}</th>
    <th>${uiLabelMap.ProductLocationSeqId}</th>
    <th>${uiLabelMap.ProductQoh}</th>
    <th>${uiLabelMap.ProductAtp}</th>
    <th>${uiLabelMap.FormFieldTitle_unitCost}</th>
  </tr>
  <tr><td colspan="6"><hr /></td></tr>
  <#if (inventoryItemsForPo?exists && inventoryItemsForPo?has_content)>
    <tr class="header-row-2"><td colspan="6"><span class="label">&nbsp;${uiLabelMap.ProductInventoryItemsFor} ${uiLabelMap.ProductPurchaseOrder} - ${orderId}</span></td></tr>
    <#list inventoryItemsForPo as inventoryItem>
      <tr>
        <td><a class="buttontext" href="javascript:set_value('${inventoryItem.inventoryItemId}')">${inventoryItem.inventoryItemId}</a></td>
        <td>${inventoryItem.facilityId?if_exists}</td>
        <td>${inventoryItem.locationSeqId?if_exists}</td>
        <td>${inventoryItem.quantityOnHandTotal?if_exists}</td>
        <td>${inventoryItem.availableToPromiseTotal?if_exists}</td>
        <td>${inventoryItem.unitCost?if_exists}</td>
      </tr>
    </#list>
  </#if>
  <#if (inventoryItemsForSupplier?exists && inventoryItemsForSupplier?has_content)>
    <tr class="header-row-2"><td colspan="6"><span class="label centered">&nbsp;${uiLabelMap.ProductInventoryItemsFor} ${uiLabelMap.ProductSupplier} - ${partyId}</span></td></tr>
    <#list inventoryItemsForSupplier as inventoryItem>
      <tr>
        <td><a class="buttontext" href="javascript:set_value('${inventoryItem.inventoryItemId}')">${inventoryItem.inventoryItemId}</a></td>
        <td>${inventoryItem.facilityId?if_exists}</td>
        <td>${inventoryItem.locationSeqId?if_exists}</td>
        <td>${inventoryItem.quantityOnHandTotal?if_exists}</td>
        <td>${inventoryItem.availableToPromiseTotal?if_exists}</td>
        <td>${inventoryItem.unitCost?if_exists}</td>
      </tr>
    </#list>
  </#if>
  <#if (inventoryItemsForProduct?exists && inventoryItemsForProduct?has_content)>
    <tr class="header-row-2"><td colspan="6"><span class="label">&nbsp;${uiLabelMap.ProductInventoryItemsFor} ${uiLabelMap.ProductProduct} - ${internalName?if_exists} [${productId}]</span></td></tr>
    <#list inventoryItemsForProduct as inventoryItem>
      <tr>
        <td><a class="buttontext" href="javascript:set_value('${inventoryItem.inventoryItemId}')">${inventoryItem.inventoryItemId}</a></td>
        <td>${inventoryItem.facilityId?if_exists}</td>
        <td>${inventoryItem.locationSeqId?if_exists}</td>
        <td>${inventoryItem.quantityOnHandTotal?if_exists}</td>
        <td>${inventoryItem.availableToPromiseTotal?if_exists}</td>
        <td>${inventoryItem.unitCost?if_exists}</td>
      </tr>
    </#list>
    <tr>
  </#if>
  <#if !(inventoryItemsForPo?exists && inventoryItemsForPo?has_content) && !(inventoryItemsForSupplier?exists && inventoryItemsForSupplier?has_content) && !(inventoryItemsForProduct?exists && inventoryItemsForProduct?has_content)>
    <tr><td><span class="label">${uiLabelMap.CommonNo} ${uiLabelMap.ProductInventoryItems} ${uiLabelMap.ProductAvailable}.</span></td></tr>
  </#if>
</table>
    </div>
