<#if shipment?exists>
    <@htmlScreenTemplate.renderScreenletBegin id="" title="${uiLabelMap.PageTitleViewShipment}"/>

<div class="row">
    <label class="col-md-2 text-right">${uiLabelMap.ProductShipmentId}</label>

    <div class="col-md-4">${shipment.shipmentId}</div>
    <label class="col-md-2 text-right">${uiLabelMap.ProductShipmentType}</label>

    <div class="col-md-4">${(shipmentType.get("description",locale))?default(shipment.shipmentTypeId?if_exists)}</div>
</div>

<div class="row">
    <label class="col-md-2 text-right">${uiLabelMap.CommonStatus}</label>

    <div class="col-md-4">${(statusItem.get("description",locale))?default(shipment.statusId?if_exists)}</div>
    <label class="col-md-2 text-right">${uiLabelMap.ProductPrimaryOrderId}</label>

    <div class="col-md-4"><#if shipment.primaryOrderId?exists><a href="/ordermgr/control/orderview?orderId=${shipment.primaryOrderId}" class="buttontext">${shipment.primaryOrderId}</a></#if>
    </div>
</div>

<div class="row">
    <label class="col-md-2 text-right">${uiLabelMap.ProductPrimaryReturnId}</label>

    <div class="col-md-4"><#if shipment.primaryReturnId?exists><a href="/ordermgr/control/returnMain?returnId=${shipment.primaryReturnId}"
                                                                  class="buttontext">${shipment.primaryReturnId}</a></#if></div>

    <label class="col-md-2 text-right">${uiLabelMap.ProductPrimaryShipGroupSeqId}</label>

    <div class="col-md-4">${shipment.primaryShipGroupSeqId?if_exists}</div>
</div>

<div class="row">
    <label class="col-md-2 text-right">${uiLabelMap.ProductEstimatedDates}</label>

    <div class="col-md-4">
              <span>
                <span>${uiLabelMap.CommonReady}:&nbsp;</span>${(shipment.estimatedReadyDate?string("yyyy-MM-dd hh:mm:ss"))?default("N/A")}<br/>
                  <span>${uiLabelMap.ProductEstimatedShipDate}:&nbsp;</span>${(shipment.estimatedShipDate?string("yyyy-MM-dd hh:mm:ss"))?default("N/A")}<br/>
                  <span>${uiLabelMap.ProductArrival}:&nbsp;</span>${(shipment.estimatedArrivalDate?string("yyyy-MM-dd hh:mm:ss"))?default("N/A")}<br/>
              </span>
    </div>
    <label class="col-md-2 text-right">${uiLabelMap.ProductLatestCancelDate}</label>

    <div class="col-md-4">${(shipment.latestCancelDate.toString())?if_exists}</div>
</div>

<div class="row">
    <label class="col-md-2 text-right">${uiLabelMap.ProductEstimatedShipCost}</label>

    <div class="col-md-4">${(shipment.estimatedShipCost)?if_exists}</div>
    <label class="col-md-2 text-right">${uiLabelMap.ProductAdditionalShippingCharge}</label>

    <div class="col-md-4">
        <#if shipment.additionalShippingCharge?exists>
                    <@ofbizCurrency amount=shipment.additionalShippingCharge isoCode=shipment.currencyUomId?if_exists />
                </#if>
    </div>
</div>

<div class="row">
    <label class="col-md-2 text-right">${uiLabelMap.ProductHandlingInstructions}</label>

    <div class="col-md-4">${shipment.handlingInstructions?if_exists}</div>
    <label class="col-md-2 text-right">${uiLabelMap.ProductFacilities}</label>

    <div class="col-md-4">
        <div>${uiLabelMap.ProductOrigin}:&nbsp;${(originFacility.facilityName)?if_exists}&nbsp;[${(shipment.originFacilityId?if_exists)}]</div>
        <#--<div>${uiLabelMap.ProductDestination}:&nbsp;${(destinationFacility.facilityName)?if_exists}&nbsp;[${(shipment.destinationFacilityId?if_exists)}]</div>-->
    </div>
</div>

<div class="row">
    <label class="col-md-2 text-right">${uiLabelMap.PartyParties}</label>

    <div class="col-md-4">
              <span>
                <span> ${(toPerson.firstName)?if_exists} ${(toPerson.middleName)?if_exists} ${(toPerson.lastName)?if_exists} ${(toPartyGroup.groupName)?if_exists}
                 </span>
               <#-- <span>${uiLabelMap.CommonFrom}
                    :&nbsp;${(fromPerson.firstName)?if_exists} ${(fromPerson.middleName)?if_exists} ${(fromPerson.lastName)?if_exists} ${(fromPartyGroup.groupName)?if_exists}
                    [${shipment.partyIdFrom?if_exists}]</span>-->
              </span>
    </div>
    <label class="col-md-2 text-right">${uiLabelMap.ProductAddresses}</label>

    <div class="col-md-4">
        <div>${uiLabelMap.ProductOrigin}:&nbsp; <#if originPostalAddress?has_content>[名称:${originPostalAddress.toName?if_exists}], 地址：${Static["org.ofbiz.common.geo.GeoWorker"].getGeoNameById(originPostalAddress.stateProvinceGeoId?if_exists,delegator)} ${Static["org.ofbiz.common.geo.GeoWorker"].getGeoNameById(originPostalAddress.city?if_exists,delegator)}
        ${Static["org.ofbiz.common.geo.GeoWorker"].getGeoNameById(originPostalAddress.countyGeoId?if_exists,delegator)}${originPostalAddress.address1?if_exists}
            , ${originPostalAddress.address2?if_exists} 邮编:${originPostalAddress.postalCode?if_exists}</#if></div>
        <div>${uiLabelMap.ProductDestination}:&nbsp; <#if destinationPostalAddress?has_content>
            [姓名: ${destinationPostalAddress.toName?if_exists}],地址:${Static["org.ofbiz.common.geo.GeoWorker"].getGeoNameById(destinationPostalAddress.stateProvinceGeoId?if_exists,delegator)}
        ${Static["org.ofbiz.common.geo.GeoWorker"].getGeoNameById(destinationPostalAddress.city?if_exists,delegator)}${Static["org.ofbiz.common.geo.GeoWorker"].getGeoNameById(originPostalAddress.countyGeoId?if_exists,delegator)}
         ${destinationPostalAddress.address1?if_exists} ${destinationPostalAddress.address2?if_exists},
            邮编: ${destinationPostalAddress.postalCode?if_exists} </#if></div>
    </div>
</div>

<div class="row">
    <label class="col-md-2 text-right">${uiLabelMap.ProductPhoneNumbers}</label>

    <div class="col-md-4">
        <div>${uiLabelMap.ProductOrigin}:&nbsp;${shipment.originTelecomNumberId?if_exists}&nbsp;<#if originTelecomNumber?has_content>
            [${originTelecomNumber.countryCode?if_exists}  ${originTelecomNumber.areaCode?if_exists} ${originTelecomNumber.contactNumber?if_exists}]</#if></div>
        <div>${uiLabelMap.ProductDestination}:&nbsp;${shipment.destinationTelecomNumberId?if_exists}&nbsp;<#if destinationTelecomNumber?has_content>
            [${destinationTelecomNumber.countryCode?if_exists}  ${destinationTelecomNumber.areaCode?if_exists} ${destinationTelecomNumber.contactNumber?if_exists}]</#if></div>
    </div>
    <label class="col-md-2 text-right">${uiLabelMap.CommonCreated}</label>

    <div class="col-md-4">
        <div>${uiLabelMap.CommonBy} [${shipment.createdByUserLogin?if_exists}] ${uiLabelMap.CommonOn} ${(shipment.createdDate?string("yyyy-MM-dd hh:mm:ss"))?if_exists}</div>
    </div>
</div>

<div class="row">
    <label class="col-md-2 text-right">${uiLabelMap.CommonLastUpdated}</label>

    <div class="col-md-4">
        <div>${uiLabelMap.CommonBy} [${shipment.lastModifiedByUserLogin?if_exists}] ${uiLabelMap.CommonOn} ${(shipment.lastModifiedDate?string("yyyy-MM-dd hh:mm:ss"))?if_exists}</div>
    </div>
</div>
    <@htmlScreenTemplate.renderScreenletEnd />
</#if>