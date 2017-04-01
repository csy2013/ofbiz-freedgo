
<#assign shipmentMethodType = shipGroup.getRelatedOne("ShipmentMethodType")?if_exists>
<#assign shipGroupAddress = shipGroup.getRelatedOne("PostalAddress")?if_exists>
<form name="updateOrderItemShipGroup" method="post" action="<@ofbizUrl>updateOrderItemShipGroup</@ofbizUrl>" class="form-inline">
    <input type="hidden" name="orderId" value="${orderId?if_exists}"/>
    <input type="hidden" name="shipGroupSeqId" value="${shipGroup.shipGroupSeqId?if_exists}"/>
    <input type="hidden" name="contactMechPurposeTypeId" value="SHIPPING_LOCATION"/>
    <input type="hidden" name="oldContactMechId" value="${shipGroup.contactMechId?if_exists}"/>

    <div class="form-group">
        <div class="input-group">
            <span class="label-white"><b>${uiLabelMap.OrderAddress}:</b></span>
        <#if orderHeader?has_content && orderHeader.statusId != "ORDER_CANCELLED" && orderHeader.statusId != "ORDER_COMPLETED" && orderHeader.statusId != "ORDER_REJECTED">
            <select name="contactMechId" class="form-control">
                <option selected="selected" value="${shipGroup.contactMechId?if_exists}">${(shipGroupAddress.address1)?default("")}
                    - ${Static["org.ofbiz.common.geo.GeoWorker"].getGeoNameById(shipGroupAddress.city?default(""),delegator)}</option>
                <#if shippingContactMechList?has_content>
                    <option disabled="disabled" value=""></option>
                    <#list shippingContactMechList as shippingContactMech>
                        <#assign shippingPostalAddress = shippingContactMech.getRelatedOne("PostalAddress")?if_exists>
                        <#if shippingContactMech.contactMechId?has_content>
                            <option value="${shippingContactMech.contactMechId?if_exists}">${(shippingPostalAddress.address1)?default("")}
                                - ${Static["org.ofbiz.common.geo.GeoWorker"].getGeoNameById(shippingPostalAddress.city?default(""),delegator)}</option>
                        </#if>
                    </#list>
                </#if>
            </select>
        <#else>
        ${(shipGroupAddress.address1)?default("")}
        </#if>
        </div>


    <#-- the setting of shipping method is only supported for sales orders at this time -->
    <#if orderHeader.orderTypeId == "SALES_ORDER">
        <div class="input-group">
            <span class="label-white">&nbsp;<b>配送方式:</b></span>

            <#if orderHeader?has_content && orderHeader.statusId != "ORDER_CANCELLED" && orderHeader.statusId != "ORDER_COMPLETED" && orderHeader.statusId != "ORDER_REJECTED">
            <#-- passing the shipmentMethod value as the combination of three fields value
            i.e shipmentMethodTypeId & carrierPartyId & roleTypeId. Values are separated by
            "@" symbol.
            -->
                <select name="shipmentMethod" class="form-control">
                    <#if shipGroup.shipmentMethodTypeId?has_content>
                        <option value="${shipGroup.shipmentMethodTypeId}@${shipGroup.carrierPartyId!}@${shipGroup.carrierRoleTypeId!}"><#if shipGroup.carrierPartyId?exists && shipGroup.carrierPartyId != "_NA_">${shipGroup.carrierPartyId!}</#if>
                            &nbsp;${shipmentMethodType.get("description",locale)!}</option>
                    <#else>
                        <option value=""/>
                    </#if>
                    <#list productStoreShipmentMethList as productStoreShipmentMethod>
                        <#assign shipmentMethodTypeAndParty = productStoreShipmentMethod.shipmentMethodTypeId + "@" + productStoreShipmentMethod.partyId + "@" + productStoreShipmentMethod.roleTypeId>
                        <#if productStoreShipmentMethod.partyId?has_content || productStoreShipmentMethod?has_content>
                            <option value="${shipmentMethodTypeAndParty?if_exists}"><#if productStoreShipmentMethod.partyId != "_NA_">${productStoreShipmentMethod.partyId?if_exists}</#if>
                                &nbsp;${productStoreShipmentMethod.get("description",locale)?default("")}</option>
                        </#if>
                    </#list>
                </select>
            <#else>
                <#if (shipGroup.carrierPartyId)?default("_NA_") != "_NA_">
                ${shipGroup.carrierPartyId?if_exists}
                </#if>
                <#if shipmentMethodType?has_content>
                ${shipmentMethodType.get("description",locale)?default("")}
                </#if>
            </#if>
        </div>

    </#if>
        <input type="submit" value="${uiLabelMap.CommonUpdate}" class="btn btn-primary btn-sm"/>
    <#if !shipGroup.contactMechId?has_content && !shipGroup.shipmentMethodTypeId?has_content>
        <#assign noShipment = "true">
        <tr>
            <td colspan="3" align="center">${uiLabelMap.OrderNotShipped}</td>
        </tr>
    </#if>
    </div>
</form>