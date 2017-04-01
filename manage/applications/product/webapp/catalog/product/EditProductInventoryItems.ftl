<#assign externalKeyParam = "&amp;externalLoginKey=" + requestAttributes.externalLoginKey?if_exists>
<@htmlScreenTemplate.renderScreenletBegin id="" title="${uiLabelMap.ProductInventoryItems} ${uiLabelMap.CommonFor} ${(product.internalName)?if_exists}  [${uiLabelMap.CommonId}:${productId?if_exists}]"/>
  <#if product?exists>
      <#if productId?has_content>
            <a href="/facility/control/EditInventoryItem?productId=${productId}${externalKeyParam}" class="btn btn-primary btn-sm">${uiLabelMap.ProductCreateNewInventoryItemProduct}</a>
            <#if showEmpty>
                <a href="<@ofbizUrl>EditProductInventoryItems?productId=${productId}</@ofbizUrl>" class="btn btn-primary btn-sm">${uiLabelMap.ProductHideEmptyItems}</a>
            <#else>
                <a href="<@ofbizUrl>EditProductInventoryItems?productId=${productId}&amp;showEmpty=true</@ofbizUrl>" class="btn btn-primary btn-sm">${uiLabelMap.ProductShowEmptyItems}</a>
            </#if>
        </#if>
        <hr />
        <#if productId?exists>
        <div class="table-responsive">
        <table  class="table table-striped table-bordered">
            <tr class="header-row">
                <th><b>${uiLabelMap.ProductItemId}</b></th>
                <th><b>${uiLabelMap.ProductItemType}</b></th>
                <th><b>${uiLabelMap.CommonStatus}</b></th>
                <th><b>${uiLabelMap.CommonReceived}</b></th>
                <th><b>${uiLabelMap.CommonExpire}</b></th>
                <th><b>${uiLabelMap.ProductFacilityContainerId}</b></th>
                <th><b>${uiLabelMap.ProductLocation}</b></th>
                <th><b>${uiLabelMap.ProductLotId}</b></th>
                <th><b>${uiLabelMap.ProductBinNum}</b></th>
                <th align="right"><b>${uiLabelMap.ProductPerUnitPrice}</b></th>
                <th>&nbsp;</th>
                <th align="right"><b>${uiLabelMap.ProductInventoryItemInitialQuantity}</b></th>
                <th align="right"><b>${uiLabelMap.ProductAtpQohSerial}</b></th>
            </tr>
            <#assign rowClass = "2">
            <#list productInventoryItems as inventoryItem>
               <#-- NOTE: Delivered for serialized inventory means shipped to customer so they should not be displayed here any more -->
               <#if showEmpty || (inventoryItem.inventoryItemTypeId?if_exists == "SERIALIZED_INV_ITEM" && inventoryItem.statusId?if_exists != "INV_DELIVERED")
                              || (inventoryItem.inventoryItemTypeId?if_exists == "NON_SERIAL_INV_ITEM" && ((inventoryItem.availableToPromiseTotal?exists && inventoryItem.availableToPromiseTotal != 0) || (inventoryItem.quantityOnHandTotal?exists && inventoryItem.quantityOnHandTotal != 0)))>
                    <#assign curInventoryItemType = inventoryItem.getRelatedOne("InventoryItemType")>
                    <#assign curStatusItem = inventoryItem.getRelatedOneCache("StatusItem")?if_exists>
                    <#assign facilityLocation = inventoryItem.getRelatedOne("FacilityLocation")?if_exists>
                    <#assign facilityLocationTypeEnum = (facilityLocation.getRelatedOneCache("TypeEnumeration"))?if_exists>
                    <#assign inventoryItemDetailFirst = Static["org.ofbiz.entity.util.EntityUtil"].getFirst(inventoryItem.getRelated("InventoryItemDetail", Static["org.ofbiz.base.util.UtilMisc"].toList("effectiveDate")))?if_exists>
                    <#if curInventoryItemType?exists>
                        <tr valign="middle"<#if rowClass == "1"> class="alternate-row"</#if>>
                            <td><a href="/facility/control/EditInventoryItem?inventoryItemId=${(inventoryItem.inventoryItemId)?if_exists}${externalKeyParam}" class="buttontext">${(inventoryItem.inventoryItemId)?if_exists}</a></td>
                            <td>&nbsp;${(curInventoryItemType.get("description",locale))?if_exists}</td>
                            <td>
                                <div>
                                    <#if curStatusItem?has_content>
                                        ${(curStatusItem.get("description",locale))?if_exists}
                                    <#elseif inventoryItem.statusId?has_content>
                                        [${inventoryItem.statusId}]
                                    <#else>
                                        ${uiLabelMap.CommonNotSet}&nbsp;
                                    </#if>
                                </div>
                            </td>
                            <td>&nbsp;${(inventoryItem.datetimeReceived)?if_exists}</td>
                            <td>&nbsp;${(inventoryItem.expireDate)?if_exists}</td>
                            <#if inventoryItem.facilityId?exists && inventoryItem.containerId?exists>
                                <td style="color: red;">${uiLabelMap.ProductErrorFacility} (${inventoryItem.facilityId})
                                    ${uiLabelMap.ProductAndContainer} (${inventoryItem.containerId}) ${uiLabelMap.CommonSpecified}</td>
                            <#elseif inventoryItem.facilityId?exists>
                                <td>${uiLabelMap.ProductFacilityLetter}:&nbsp;<a href="/facility/control/EditFacility?facilityId=${inventoryItem.facilityId}${externalKeyParam}" class="linktext">${inventoryItem.facilityId}</a></td>
                            <#elseif (inventoryItem.containerId)?exists>
                                <td>${uiLabelMap.ProductContainerLetter}:&nbsp;<a href="<@ofbizUrl>EditContainer?containerId=${inventoryItem.containerId }</@ofbizUrl>" class="linktext">${inventoryItem.containerId}</a></td>
                            <#else>
                                <td>&nbsp;</td>
                            </#if>
                            <td><a href="/facility/control/EditFacilityLocation?facilityId=${(inventoryItem.facilityId)?if_exists}&amp;locationSeqId=${(inventoryItem.locationSeqId)?if_exists}${externalKeyParam}" class="linktext"><#if facilityLocation?exists>${facilityLocation.areaId?if_exists}:${facilityLocation.aisleId?if_exists}:${facilityLocation.sectionId?if_exists}:${facilityLocation.levelId?if_exists}:${facilityLocation.positionId?if_exists}</#if><#if facilityLocationTypeEnum?has_content> (${facilityLocationTypeEnum.get("description",locale)})</#if> [${(inventoryItem.locationSeqId)?if_exists}]</a></td>
                            <td>&nbsp;${(inventoryItem.lotId)?if_exists}</td>
                            <td>&nbsp;${(inventoryItem.binNumber)?if_exists}</td>
                            <td align="right"><@ofbizCurrency amount=inventoryItem.unitCost isoCode=inventoryItem.currencyUomId/></td>
                            <td>
                                <#if inventoryItemDetailFirst?exists && inventoryItemDetailFirst.workEffortId?exists>
                                    <b>${uiLabelMap.ProductionRunId}</b> ${inventoryItemDetailFirst.workEffortId}
                                <#elseif inventoryItemDetailFirst?exists && inventoryItemDetailFirst.orderId?exists>
                                    <b>${uiLabelMap.OrderId}</b> ${inventoryItemDetailFirst.orderId}
                                </#if>
                            </td>
                            <td align="right">${inventoryItemDetailFirst?if_exists.quantityOnHandDiff?if_exists}</td>
                            <#if inventoryItem.inventoryItemTypeId?if_exists == "NON_SERIAL_INV_ITEM">
                                <td align="right">
                                    <div>${(inventoryItem.availableToPromiseTotal)?default("NA")}
                                    / ${(inventoryItem.quantityOnHandTotal)?default("NA")}</div>
                                </td>
                            <#elseif inventoryItem.inventoryItemTypeId?if_exists == "SERIALIZED_INV_ITEM">
                                <td align="right">&nbsp;${(inventoryItem.serialNumber)?if_exists}</td>
                            <#else>
                                <td align="right" style="color: red;">${uiLabelMap.ProductErrorType} ${(inventoryItem.inventoryItemTypeId)?if_exists} ${uiLabelMap.ProductUnknownSerialNumber} (${(inventoryItem.serialNumber)?if_exists})
                                    ${uiLabelMap.ProductAndQuantityOnHand} (${(inventoryItem.quantityOnHandTotal)?if_exists} ${uiLabelMap.CommonSpecified}</td>
                            </#if>
                        </tr>
                    </#if>
                </#if>
                <#-- toggle the row color -->
                <#if rowClass == "2">
                    <#assign rowClass = "1">
                <#else>
                    <#assign rowClass = "2">
                </#if>
            </#list>
          </table>
        </#if>
    </div>
  <#else>
    <h2>${uiLabelMap.ProductProductNotFound} ${productId?if_exists}!</h2>
  </#if>

<@htmlScreenTemplate.renderScreenletEnd/>