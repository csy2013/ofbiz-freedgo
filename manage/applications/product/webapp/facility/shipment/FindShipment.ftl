<script language="JavaScript" type="text/javascript">
    <!-- //
    function lookupShipments() {
        shipmentIdValue = document.lookupShipmentForm.shipmentId.value;
        if (shipmentIdValue.length > 1) {
            document.lookupShipmentForm.action = "<@ofbizUrl>ViewShipment</@ofbizUrl>";
        } else {
            document.lookupShipmentForm.action = "<@ofbizUrl>FindShipment</@ofbizUrl>";
        }
        document.lookupShipmentForm.submit();
    }
    // -->
</script>
<@htmlScreenTemplate.renderScreenletBegin id="" title="${uiLabelMap.ProductFindShipmentTitle}"/>
<#if requestParameters.facilityId?has_content>
<a href="<@ofbizUrl>quickShipOrder?facilityId=${requestParameters.facilityId}</@ofbizUrl>" class="btn btn-primary btn-sm">${uiLabelMap.ProductQuickShipOrder}</a>
</#if>
<@htmlScreenTemplate.renderModalPage id="EditShipment" name="EditShipment"  modalUrl="EditShipment"   description="${uiLabelMap.ProductNewShipment}"  modalTitle="新建送货页面"  />
<#--<a href="<@ofbizUrl>EditShipment</@ofbizUrl>" class="btn btn-primary btn-sm">${uiLabelMap.ProductNewShipment}</a>-->
<hr/>

<form method="post" name="lookupShipmentForm" action="<@ofbizUrl>FindShipment</@ofbizUrl>" class="form-inline">
    <input type="hidden" name="lookupFlag" value="Y"/>
    <div class="form-group">
        <div class="input-group m-b-10">
            <span class="input-group-addon">${uiLabelMap.ProductShipmentId}</span>
            <input type="text" name="shipmentId" value="${shipmentId?if_exists}" class="form-control"/>
        </div>
        <div class="input-group m-b-10">
            <span class="input-group-addon">${uiLabelMap.ProductShipmentType}</span>
            <select name="shipmentTypeId" class="form-control">
            <#if currentShipmentType?has_content>
                <option value="${currentShipmentType.shipmentTypeId}">${currentShipmentType.get("description",locale)}</option>
                <option value="${currentShipmentType.shipmentTypeId}">---</option>
            </#if>
                <option value="">${uiLabelMap.ProductAnyShipmentType}</option>
            <#list shipmentTypes as shipmentType>
                <option value="${shipmentType.shipmentTypeId}">${shipmentType.get("description",locale)}</option>
            </#list>
            </select>
        </div>
        <div class="input-group m-b-10">
            <span class="input-group-addon">${uiLabelMap.ProductOriginFacility}</span>

            <select name="originFacilityId" class="form-control">
            <#if currentOriginFacility?has_content>
                <option value="${currentOriginFacility.facilityId}">${currentOriginFacility.facilityName} [${currentOriginFacility.facilityId}]</option>
                <option value="${currentOriginFacility.facilityId}">---</option>
            </#if>
                <option value="">${uiLabelMap.ProductAnyFacility}</option>
            <#list facilities as facility>
                <option value="${facility.facilityId}">${facility.facilityName} [${facility.facilityId}]</option>
            </#list>
            </select>
        </div>
        <div class="input-group m-b-10"><span class="input-group-addon">${uiLabelMap.ProductDestinationFacility}</span>

            <select name="destinationFacilityId" class="form-control">
            <#if currentDestinationFacility?has_content>
                <option value="${currentDestinationFacility.facilityId}">${currentDestinationFacility.facilityName} [${currentDestinationFacility.facilityId}]</option>
                <option value="${currentDestinationFacility.facilityId}">---</option>
            </#if>
                <option value="">${uiLabelMap.ProductAnyFacility}</option>
            <#list facilities as facility>
                <option value="${facility.facilityId}">${facility.facilityName} [${facility.facilityId}]</option>
            </#list>
            </select>
        </div>
        <div class="input-group m-b-10"><span class="input-group-addon">${uiLabelMap.CommonStatus}</span>
            <select name="statusId" class="form-control">
            <#if currentStatus?has_content>
                <option value="${currentStatus.statusId}">${currentStatus.get("description",locale)}</option>
                <option value="${currentStatus.statusId}">---</option>
            </#if>
                <option value="">${uiLabelMap.ProductSalesShipmentStatus}</option>
            <#list shipmentStatuses as shipmentStatus>
                <option value="${shipmentStatus.statusId}">${shipmentStatus.get("description",locale)}</option>
            </#list>
                <option value="">---</option>
                <option value="">${uiLabelMap.ProductPurchaseShipmentStatus}</option>
            <#list purchaseShipmentStatuses as shipmentStatus>
                <option value="${shipmentStatus.statusId}">${shipmentStatus.get("description",locale)}</option>
            </#list>
                <option value="">---</option>
                <option value="">${uiLabelMap.ProductOrderReturnStatus}</option>
            <#list returnStatuses as returnStatus>
                <#if returnStatus.statusId != "RETURN_REQUESTED">
                    <option value="${returnStatus.statusId}">${returnStatus.get("description",locale)}</option>
                </#if>
            </#list>
            </select>
        </div>
        <div class="input-group m-b-10"><span class="input-group-addon">${uiLabelMap.ProductDateFilter}</span>

        <@htmlTemplate.renderDateTimeField name="minDate" event="" action="" className="" alert="" title="Format: yyyy-MM-dd HH:mm:ss.SSS" value="${requestParameters.minDate?if_exists}" size="25" maxlength="30" id="minDate1" dateType="date" shortDateInput=false timeDropdownParamName="" defaultDateTimeString="" localizedIconTitle="" timeDropdown="" timeHourName="" classString="" hour1="" hour2="" timeMinutesName="" minutes="" isTwelveHour="" ampmName="" amSelected="" pmSelected="" compositeType="" formName=""/>
            <span class="input-group-addon">${uiLabelMap.CommonFrom}</span>

        <@htmlTemplate.renderDateTimeField name="maxDate" event="" action="" className="" alert="" title="Format: yyyy-MM-dd HH:mm:ss.SSS" value="${requestParameters.maxDate?if_exists}" size="25" maxlength="30" id="maxDate1" dateType="date" shortDateInput=false timeDropdownParamName="" defaultDateTimeString="" localizedIconTitle="" timeDropdown="" timeHourName="" classString="" hour1="" hour2="" timeMinutesName="" minutes="" isTwelveHour="" ampmName="" amSelected="" pmSelected="" compositeType="" formName=""/>
            <span class="input-group-addon">${uiLabelMap.CommonThru}</span>
        </div>
        <a href="javascript:lookupShipments();" class="btn btn-primary btn-sm m-b-10">${uiLabelMap.ProductFindShipment}</a>
    </div>
</form>
<#if shipmentList?exists>
<div id="findOrders_2" class="screenlet">
    <div class="screenlet-title-bar">
        <ul>
            <#--<li class="h3">${uiLabelMap.ProductShipmentsFound}</li>-->
            <#if 0 < shipmentList?size>
                <#if (shipmentList?size > highIndex)>
                    <li><a class="nav-next"
                           href="<@ofbizUrl>FindShipment?VIEW_SIZE=${viewSize}&amp;VIEW_INDEX=${viewIndex+1}${paramList}&amp;lookupFlag=Y</@ofbizUrl>">下页</a></li>
                <#else>
                    <li class="disabled">下页</li>
                </#if>
                <li>${lowIndex} - ${highIndex} ${uiLabelMap.CommonOf} ${shipmentList?size}</li>
                <#if (viewIndex > 1)>
                    <li><a class="nav-previous"
                           href="<@ofbizUrl>FindShipment?VIEW_SIZE=${viewSize}&amp;VIEW_INDEX=${viewIndex-1}${paramList}&amp;lookupFlag=Y</@ofbizUrl>">上页</a></li>
                <#else>
                    <li class="disabled">上页</li>
                </#if>
            </#if>
        </ul>
        <br class="clear"/>
    </div>
    <div class="table-responsive">
        <table class="table table-striped table-bordered">
            <tr class="header-row">
                <td width="5%">${uiLabelMap.ProductShipmentId}</td>
                <td width="15%">${uiLabelMap.ProductShipmentType}</td>
                <td width="10%">${uiLabelMap.CommonStatus}</td>
                <td width="25%">${uiLabelMap.ProductOriginFacility}</td>
                <td width="25%">${uiLabelMap.ProductDestFacility}</td>
                <td width="15%">${uiLabelMap.ProductShipDate}</td>
                <td width="5%">&nbsp;</td>
            </tr>
            <#if shipmentList?has_content>
                <#assign alt_row = false>
                <#list shipmentList as shipment>
                    <#assign originFacility = delegator.findByPrimaryKeyCache("Facility", Static["org.ofbiz.base.util.UtilMisc"].toMap("facilityId", shipment.originFacilityId))?if_exists />
                    <#assign destinationFacility = delegator.findByPrimaryKeyCache("Facility", Static["org.ofbiz.base.util.UtilMisc"].toMap("facilityId", shipment.destinationFacilityId))?if_exists />
                    <#assign statusItem = delegator.findByPrimaryKeyCache("StatusItem", Static["org.ofbiz.base.util.UtilMisc"].toMap("statusId", shipment.statusId))?if_exists/>
                    <#assign shipmentType = delegator.findByPrimaryKeyCache("ShipmentType", Static["org.ofbiz.base.util.UtilMisc"].toMap("shipmentTypeId", shipment.shipmentTypeId))?if_exists/>
                    <tr valign="middle"<#if alt_row> class="alternate-row"</#if>>
                        <td><a href="<@ofbizUrl>ViewShipment?shipmentId=${shipment.shipmentId}</@ofbizUrl>" class="buttontext">${shipment.shipmentId}</a></td>
                        <td>${(shipmentType.get("description",locale))?default(shipmentType.shipmentTypeId?default(""))}</td>
                        <td>${(statusItem.get("description",locale))?default(statusItem.statusId?default("N/A"))}</td>
                        <td>${(originFacility.facilityName)?if_exists} [${shipment.originFacilityId?if_exists}]</td>
                        <td>${(destinationFacility.facilityName)?if_exists} [${shipment.destinationFacilityId?if_exists}]</td>
                        <td><span style="white-space: nowrap;">${(shipment.estimatedShipDate.toString())?if_exists}</span></td>
                        <td align="right">
                            <a href="<@ofbizUrl>ViewShipment?shipmentId=${shipment.shipmentId}</@ofbizUrl>" class="buttontext">${uiLabelMap.CommonView}</a>
                        </td>
                    </tr>
                <#-- toggle the row color -->
                    <#assign alt_row = !alt_row>
                </#list>
            <#else>
                <tr>
                    <td colspan="7"><h3>${uiLabelMap.ProductNoShipmentsFound}.</h3></td>
                </tr>
            </#if>
        </table>
    </div>
</div>
</#if>