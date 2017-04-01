
<#if requestAttributes.uiLabelMap?exists>
    <#assign uiLabelMap = requestAttributes.uiLabelMap>
</#if>
<#assign selected = tabButtonItem?default("void")>
<#if shipmentId?has_content>
<div class="btn-bar">
    <br/>
        <a class="btn btn-primary btn-sm" href="<@ofbizUrl>ViewShipment?shipmentId=${shipmentId}</@ofbizUrl>">${uiLabelMap.CommonView}</a>
    <@htmlScreenTemplate.renderModalPage id="EditShipment" name="EditShipment"  modalUrl="EditShipment"
    description="${uiLabelMap.CommonEdit}"  modalTitle="编辑发货信息" targetParameterIter="shipmentId:${shipmentId}"/>
    <#--<a class="btn btn-primary btn-sm" href="<@ofbizUrl>EditShipment1?shipmentId=${shipmentId}</@ofbizUrl>">${uiLabelMap.CommonEdit}</a>-->
        <#if (shipment.shipmentTypeId)?exists && shipment.shipmentTypeId = "PURCHASE_RETURN">
            <a class="btn btn-primary btn-sm"
                    href="<@ofbizUrl>AddItemsFromInventory?shipmentId=${shipmentId}</@ofbizUrl>">${uiLabelMap.ProductOrderItems}</a>
        </#if>
        <#if (shipment.shipmentTypeId)?exists && shipment.shipmentTypeId = "SALES_SHIPMENT">

            <a class="btn btn-primary btn-sm" href="<@ofbizUrl>EditShipmentRouteSegments?shipmentId=${shipmentId}</@ofbizUrl>">${uiLabelMap.ProductRouteSegments}</a>
        </#if>

    <a class="btn btn-primary btn-sm" target="_blank" href="<@ofbizUrl>ShipmentManifest.pdf?shipmentId=${shipmentId}</@ofbizUrl>">${uiLabelMap.ProductGenerateShipmentManifestReport}</a>

        <br/>
    <br/>
</div>
</#if>
