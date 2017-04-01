<@htmlScreenTemplate.renderScreenletBegin id="" title="${uiLabelMap.ProductInventorySummary}"/>
<#if parameters.showAllFacilities?exists>
<a href="EditProductInventoryItems?productId=${productId}" class="btn btn-primary btn-sm">${uiLabelMap.ProductShowProductFacilities}</a>
<#else>
<a href="EditProductInventoryItems?productId=${productId}&amp;showAllFacilities=Y" class="btn btn-primary btn-sm">${uiLabelMap.ProductShowAllFacilities}</a>

</#if>
<hr/>
<#if product?exists>
<div class="table-responsive">
<table  class="table table-striped table-bordered">
    <tr class="header-row">
        <th><b>${uiLabelMap.ProductFacility}</b></th>
        <th><b>${uiLabelMap.ProductAtp}</b></th>
        <th><b>${uiLabelMap.ProductQoh}</b></th>
        <#if isMarketingPackage == "true">
            <td><b>${uiLabelMap.ProductMarketingPackageATP}</b></td>
            <td><b>${uiLabelMap.ProductMarketingPackageQOH}</b></td>
        </#if>
        <th><b>${uiLabelMap.ProductIncomingShipments}</b></th>
        <th><b>${uiLabelMap.ProductIncomingProductionRuns}</b></th>
        <th><b>${uiLabelMap.ProductOutgoingProductionRuns}</b></th>
    </tr>
    <#assign rowClass = "2">
    <#list quantitySummaryByFacility.values() as quantitySummary>
        <#if quantitySummary.facilityId?exists>
            <#assign facilityId = quantitySummary.facilityId>
            <#assign facility = delegator.findByPrimaryKey("Facility", Static["org.ofbiz.base.util.UtilMisc"].toMap("facilityId", facilityId))>
            <#assign manufacturingInQuantitySummary = manufacturingInQuantitySummaryByFacility.get(facilityId)?if_exists>
            <#assign manufacturingOutQuantitySummary = manufacturingOutQuantitySummaryByFacility.get(facilityId)?if_exists>
            <#assign totalQuantityOnHand = quantitySummary.totalQuantityOnHand?if_exists>
            <#assign totalAvailableToPromise = quantitySummary.totalAvailableToPromise?if_exists>
            <#assign mktgPkgATP = quantitySummary.mktgPkgATP?if_exists>
            <#assign mktgPkgQOH = quantitySummary.mktgPkgQOH?if_exists>
            <#assign incomingShipmentAndItemList = quantitySummary.incomingShipmentAndItemList?if_exists>
            <#assign incomingProductionRunList = manufacturingInQuantitySummary.incomingProductionRunList?if_exists>
            <#assign incomingQuantityTotal = manufacturingInQuantitySummary.estimatedQuantityTotal?if_exists>
            <#assign outgoingProductionRunList = manufacturingOutQuantitySummary.outgoingProductionRunList?if_exists>
            <#assign outgoingQuantityTotal = manufacturingOutQuantitySummary.estimatedQuantityTotal?if_exists>
            <tr valign="middle"<#if rowClass == "1"> class="alternate-row"</#if>>
                <td>${(facility.facilityName)?if_exists} [${facilityId?default("[No Facility]")}]
                    <a href="/facility/control/ReceiveInventory?facilityId=${facilityId}&amp;productId=${productId}&amp;externLoginKey=${externalLoginKey}"
                       class="btn btn-primary btn-sm">${uiLabelMap.ProductInventoryReceive}</a></td>
                <td><#if totalAvailableToPromise?exists>${totalAvailableToPromise}<#else>&nbsp;</#if></td>
                <td><#if totalQuantityOnHand?exists>${totalQuantityOnHand}<#else>&nbsp;</#if></td>
                <#if isMarketingPackage == "true">
                    <td><#if mktgPkgATP?exists>${mktgPkgATP}<#else>&nbsp;</#if></td>
                    <td><#if mktgPkgQOH?exists>${mktgPkgQOH}<#else>&nbsp;</#if></td>
                </#if>
                <td>
                    <#if incomingShipmentAndItemList?has_content>
                        <#list incomingShipmentAndItemList as incomingShipmentAndItem>
                            <div>${incomingShipmentAndItem.shipmentId}:${incomingShipmentAndItem.shipmentItemSeqId}-${(incomingShipmentAndItem.estimatedArrivalDate.toString())?if_exists}
                                -<#if incomingShipmentAndItem.quantity?exists>${incomingShipmentAndItem.quantity?string.number}<#else>[${uiLabelMap.ProductQuantityNotSet}]</#if></div>
                        </#list>
                    <#else>
                        <div>&nbsp;</div>
                    </#if>
                </td>
                <td>
                    <#if incomingProductionRunList?has_content>
                        <#list incomingProductionRunList as incomingProductionRun>
                            <div>${incomingProductionRun.workEffortId}-${(incomingProductionRun.estimatedCompletionDate.toString())?if_exists}
                                -<#if incomingProductionRun.estimatedQuantity?exists>${incomingProductionRun.estimatedQuantity?string.number}<#else>[${uiLabelMap.ProductQuantityNotSet}
                                    ]</#if></div>
                        </#list>
                        <div><b>${uiLabelMap.CommonTotal}:&nbsp;${incomingQuantityTotal?if_exists}</b></div>
                    <#else>
                        <div>&nbsp;</div>
                    </#if>
                </td>
                <td>
                    <#if outgoingProductionRunList?has_content>
                        <#list outgoingProductionRunList as outgoingProductionRun>
                            <div>${outgoingProductionRun.workEffortParentId?default("")}:${outgoingProductionRun.workEffortId}
                                -${(outgoingProductionRun.estimatedStartDate.toString())?if_exists}
                                -<#if outgoingProductionRun.estimatedQuantity?exists>${outgoingProductionRun.estimatedQuantity?string.number}<#else>[${uiLabelMap.ProductQuantityNotSet}
                                    ]</#if></div>
                        </#list>
                        <div><b>${uiLabelMap.CommonTotal}:&nbsp;${outgoingQuantityTotal?if_exists}</b></div>
                    <#else>
                        <div>&nbsp;</div>
                    </#if>
                </td>
            </tr>

        </#if>
    <#-- toggle the row color -->
        <#if rowClass == "2">
            <#assign rowClass = "1">
        <#else>
            <#assign rowClass = "2">
        </#if>
    </#list>
</table>
</div>
<#else>
<h2>${uiLabelMap.ProductProductNotFound} ${productId?if_exists}!</h2>
</#if>

<@htmlScreenTemplate.renderScreenletEnd/>
