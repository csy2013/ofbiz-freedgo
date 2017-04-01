<#if shipment?exists>
<div class="panel panel-default">

    <div class="panel-heading">
        <div class="panel-title">
            <h4>${uiLabelMap.PageTitleEditShipmentRouteSegments}</h4>
        </div>
    </div>

<div class="panel-body">
    <#list shipmentRouteSegmentDatas as shipmentRouteSegmentData>
        <#assign shipmentRouteSegment = shipmentRouteSegmentData.shipmentRouteSegment>
        <#assign shipmentPackageRouteSegs = shipmentRouteSegmentData.shipmentPackageRouteSegs?if_exists>
        <#assign originFacility = shipmentRouteSegmentData.originFacility?if_exists>
        <#assign destFacility = shipmentRouteSegmentData.destFacility?if_exists>
        <#assign shipmentMethodType = shipmentRouteSegmentData.shipmentMethodType?if_exists>
        <#assign carrierPerson = shipmentRouteSegmentData.carrierPerson?if_exists>
        <#assign carrierPartyGroup = shipmentRouteSegmentData.carrierPartyGroup?if_exists>
        <#assign originPostalAddress = shipmentRouteSegmentData.originPostalAddress?if_exists>
        <#assign destPostalAddress = shipmentRouteSegmentData.destPostalAddress?if_exists>
        <#assign originTelecomNumber = shipmentRouteSegmentData.originTelecomNumber?if_exists>
        <#assign destTelecomNumber = shipmentRouteSegmentData.destTelecomNumber?if_exists>
        <#assign carrierServiceStatusItem = shipmentRouteSegmentData.carrierServiceStatusItem?if_exists>
        <#assign currencyUom = shipmentRouteSegmentData.currencyUom?if_exists>
        <#assign billingWeightUom = shipmentRouteSegmentData.billingWeightUom?if_exists>
        <#assign carrierServiceStatusValidChangeToDetails = shipmentRouteSegmentData.carrierServiceStatusValidChangeToDetails?if_exists>
        <form name="duplicateShipmentRouteSegment_${shipmentRouteSegmentData_index}" method="post" action="<@ofbizUrl>duplicateShipmentRouteSegment</@ofbizUrl>">
            <input type="hidden" name="shipmentId" value="${shipmentRouteSegment.shipmentId}"/>
            <input type="hidden" name="shipmentRouteSegmentId" value="${shipmentRouteSegment.shipmentRouteSegmentId}"/>
        </form>
        <form name="deleteShipmentRouteSegment_${shipmentRouteSegmentData_index}" method="post" action="<@ofbizUrl>deleteShipmentRouteSegment</@ofbizUrl>">
            <input type="hidden" name="shipmentId" value="${shipmentRouteSegment.shipmentId}"/>
            <input type="hidden" name="shipmentRouteSegmentId" value="${shipmentRouteSegment.shipmentRouteSegmentId}"/>
        </form>
        <form action="<@ofbizUrl>updateShipmentRouteSegment</@ofbizUrl>" method="post" name="updateShipmentRouteSegmentForm${shipmentRouteSegmentData_index}"
              class="form-horizontal">
            <input type="hidden" name="shipmentId" value="${shipmentId}"/>
            <input type="hidden" name="shipmentRouteSegmentId" value="${shipmentRouteSegment.shipmentRouteSegmentId}"/>

            <div class="form-group">
                <div class="control-label col-md-2">${uiLabelMap.ProductCarrierStatus}</div>
                <div class="col-md-4">
                    <select name="carrierServiceStatusId" class="form-control">
                        <#if carrierServiceStatusItem?has_content>
                            <option value="${carrierServiceStatusItem.statusId}">${carrierServiceStatusItem.description}</option>
                            <option value="${carrierServiceStatusItem.statusId}">---</option>
                        <#else>
                            <option value="">&nbsp;</option>
                        </#if>
                        <#list carrierServiceStatusValidChangeToDetails as carrierServiceStatusValidChangeToDetail>
                            <option value="${carrierServiceStatusValidChangeToDetail.statusIdTo}">${carrierServiceStatusValidChangeToDetail.transitionName}
                                [${carrierServiceStatusValidChangeToDetail.description}]
                            </option>
                        </#list>
                    </select>
                </div>
                <div class="control-label col-md-2">${uiLabelMap.ProductTrackingNumber}</div>
                <div class="col-md-4"><input type="text" name="trackingIdNumber" value="${shipmentRouteSegment.trackingIdNumber?if_exists}" class="form-control"/></div>
            </div>

            <div class="form-group">
                <div class="control-label col-md-2">${uiLabelMap.ProductEstimatedStartArrive}</div>
                <div class="col-md-4">
                    <@htmlTemplate.renderDateTimeField name="estimatedStartDate" event="" action="" className="" alert="" title="Format: yyyy-MM-dd HH:mm:ss.SSS" value="${(shipmentRouteSegment.estimatedStartDate.toString())?if_exists}" size="25" maxlength="30" id="estimatedStartDate1" dateType="date" shortDateInput=false timeDropdownParamName="" defaultDateTimeString="" localizedIconTitle="" timeDropdown="" timeHourName="" classString="" hour1="" hour2="" timeMinutesName="" minutes="" isTwelveHour="" ampmName="" amSelected="" pmSelected="" compositeType="" formName=""/>
                    <@htmlTemplate.renderDateTimeField name="estimatedArrivalDate" event="" action="" className="" alert="" title="Format: yyyy-MM-dd HH:mm:ss.SSS" value="${(shipmentRouteSegment.estimatedArrivalDate.toString())?if_exists}" size="25" maxlength="30" id="estimatedArrivalDate1" dateType="date" shortDateInput=false timeDropdownParamName="" defaultDateTimeString="" localizedIconTitle="" timeDropdown="" timeHourName="" classString="" hour1="" hour2="" timeMinutesName="" minutes="" isTwelveHour="" ampmName="" amSelected="" pmSelected="" compositeType="" formName=""/>
                </div>
                <div class="control-label col-md-2">${uiLabelMap.ProductActualStartArrive}</div>
                <div class="col-md-4">
                    <@htmlTemplate.renderDateTimeField name="actualStartDate" event="" action="" className="" alert="" title="Format: yyyy-MM-dd HH:mm:ss.SSS" value="${(shipmentRouteSegment.actualStartDate.toString())?if_exists}" size="25" maxlength="30" id="actualStartDate2" dateType="date" shortDateInput=false timeDropdownParamName="" defaultDateTimeString="" localizedIconTitle="" timeDropdown="" timeHourName="" classString="" hour1="" hour2="" timeMinutesName="" minutes="" isTwelveHour="" ampmName="" amSelected="" pmSelected="" compositeType="" formName=""/>
                    <@htmlTemplate.renderDateTimeField name="actualArrivalDate" event="" action="" className="" alert="" title="Format: yyyy-MM-dd HH:mm:ss.SSS" value="${(shipmentRouteSegment.actualArrivalDate.toString())?if_exists}" size="25" maxlength="30" id="actualArrivalDate2" dateType="date" shortDateInput=false timeDropdownParamName="" defaultDateTimeString="" localizedIconTitle="" timeDropdown="" timeHourName="" classString="" hour1="" hour2="" timeMinutesName="" minutes="" isTwelveHour="" ampmName="" amSelected="" pmSelected="" compositeType="" formName=""/>
                </div>
            </div>

            <div class="form-group">
                <div class="control-label col-md-2">重量单位:</div>
                <div class="col-md-4">
                    <select name="billingWeightUomId" class="form-control">
                        <#if billingWeightUom?has_content>
                            <option value="${billingWeightUom.uomId}">${billingWeightUom.get("description",locale)} [${billingWeightUom.abbreviation}]</option>
                            <option value="${billingWeightUom.uomId}">---</option>
                        <#else>
                            <option value="">&nbsp;</option>
                        </#if>
                        <#list weightUoms as weightUom>
                            <option value="${weightUom.uomId}">${weightUom.get("description",locale)} [${weightUom.abbreviation}]</option>
                        </#list>
                    </select>
                </div>
                <div class="control-label col-md-2">${uiLabelMap.ProductBillingWeightUom}</div>
                <div class="col-md-4"><input type="text" size="5" name="billingWeight" class="form-control" value="${shipmentRouteSegment.billingWeight?if_exists}"/>
                </div>

            </div>
            <input type="hidden" name="currencyUomId" value="CNY"/>
            <div class="form-group">
                <div class="control-label col-md-2">${uiLabelMap.ProductActualTransport}</div>
                <div class="col-md-4"><input type="text" class="form-control" size="8" name="actualTransportCost" value="${shipmentRouteSegment.actualTransportCost?if_exists}"/>
                </div>
                <div class="control-label col-md-2">${uiLabelMap.ProductActualServices}</div>
                <div class="col-md-4"><input type="text" class="form-control" size="8" name="actualServiceCost" value="${shipmentRouteSegment.actualServiceCost?if_exists}"/></div>
            </div>

            <div class="form-group">
                <div class="control-label col-md-2">${uiLabelMap.ProductActualOther}</div>
                <div class="col-md-4"><input type="text" class="form-control" size="8" name="actualOtherCost" value="${shipmentRouteSegment.actualOtherCost?if_exists}"/></div>
                <div class="control-label col-md-2">${uiLabelMap.ProductActualTotal}</div>
                <div class="col-md-4"><input type="text" size="8" class="form-control" name="actualCost" value="${shipmentRouteSegment.actualCost?if_exists}"/></div>
            </div>

            <div>
            <#--物流接口调用-->
                <#if "UPS" == shipmentRouteSegment.carrierPartyId?if_exists>
                <#--  UPS upsShipmentConfirm-upsShipmentAccept-> upsTrackShipment(轨迹)->upsVoidShipment  -->
                    <#if !shipmentRouteSegment.carrierServiceStatusId?has_content || "SHRSCS_NOT_STARTED" == shipmentRouteSegment.carrierServiceStatusId?if_exists>
                        <a class="btn btn-primary btn-sm" href="javascript:document.upsShipmentConfirm_${shipmentRouteSegmentData_index}.submit()"
                           class="buttontext">${uiLabelMap.ProductConfirmShipmentUps}</a>
                        <br/>
                    ${uiLabelMap.ProductShipmentUpsResidential}:
                        <input type="checkbox" name="homeDeliveryType" value="Y" ${(shipmentRouteSegment.homeDeliveryType?has_content)?string("checked=\"checked\"","")}/>
                    <#elseif "SHRSCS_CONFIRMED" == shipmentRouteSegment.carrierServiceStatusId?if_exists>
                        <a class="btn btn-primary btn-sm" href="javascript:document.upsShipmentAccept_${shipmentRouteSegmentData_index}.submit()"
                           class="buttontext">${uiLabelMap.ProductAcceptUpsShipmentConfirmation}</a>
                        <br/>
                        <a class="btn btn-primary btn-sm" href="javascript:document.upsVoidShipment_${shipmentRouteSegmentData_index}.submit()"
                           class="buttontext">${uiLabelMap.ProductVoidUpsShipmentConfirmation}</a>
                    <#elseif "SHRSCS_ACCEPTED" == shipmentRouteSegment.carrierServiceStatusId?if_exists>
                        <a class="btn btn-primary btn-sm" href="javascript:document.upsTrackShipment_${shipmentRouteSegmentData_index}.submit()"
                           class="buttontext">${uiLabelMap.ProductTrackUpsShipment}</a>
                        <br/>
                        <a class="btn btn-primary btn-sm" href="javascript:document.upsVoidShipment_${shipmentRouteSegmentData_index}.submit()"
                           class="buttontext">${uiLabelMap.ProductVoidUpsShipment}</a>
                    </#if>
                </#if>
                <#if "DHL" == shipmentRouteSegment.carrierPartyId?if_exists>
                    <#if !shipmentRouteSegment.carrierServiceStatusId?has_content || "SHRSCS_NOT_STARTED" == shipmentRouteSegment.carrierServiceStatusId?if_exists>
                        <a class="btn btn-primary btn-sm" href="javascript:document.dhlShipmentConfirm_${shipmentRouteSegmentData_index}.submit()"
                           class="buttontext">${uiLabelMap.ProductConfirmShipmentDHL}</a>
                    </#if>
                </#if>
                <#if "FEDEX" == shipmentRouteSegment.carrierPartyId?if_exists>
                    <#if !shipmentRouteSegment.carrierServiceStatusId?has_content || "SHRSCS_NOT_STARTED" == shipmentRouteSegment.carrierServiceStatusId?if_exists>
                        <a class="btn btn-primary btn-sm" href="javascript:document.fedexShipmentConfirm_${shipmentRouteSegmentData_index}.submit()"
                           class="buttontext">${uiLabelMap.ProductConfirmShipmentFedex}</a>
                        <br/>
                        <#if shipmentMethodType?exists && shipmentMethodType.shipmentMethodTypeId=="GROUND_HOME">
                            <select name="homeDeliveryType">
                                <option value="">${uiLabelMap.ProductShipmentNone}</option>
                                <option ${(shipmentRouteSegment.homeDeliveryType?default("")=="DATECERTAIN")?string("selected=\"selected\"","")}
                                        value="DATECERTAIN">${uiLabelMap.ProductShipmentFedexHomeDateCertain}</option>
                                <option ${(shipmentRouteSegment.homeDeliveryType?default("")=="EVENING")?string("selected=\"selected\"","")}
                                        value="EVENING">${uiLabelMap.ProductShipmentFedexHomeEvening}</option>
                                <option ${(shipmentRouteSegment.homeDeliveryType?default("")=="APPOINTMENT")?string("selected=\"selected\"","")}
                                        value="APPOINTMENT">${uiLabelMap.ProductShipmentFedexHomeAppointment}</option>
                            </select>
                            <@htmlTemplate.renderDateTimeField name="homeDeliveryDate" event="" action="" className="" alert="" title="Format: yyyy-MM-dd HH:mm:ss.SSS" value="${(shipmentRouteSegment.homeDeliveryDate.toString())?if_exists}" size="25" maxlength="30" id="homeDeliveryDate1" dateType="date" shortDateInput=false timeDropdownParamName="" defaultDateTimeString="" localizedIconTitle="" timeDropdown="" timeHourName="" classString="" hour1="" hour2="" timeMinutesName="" minutes="" isTwelveHour="" ampmName="" amSelected="" pmSelected="" compositeType="" formName=""/>
                        </#if>
                    <#else>
                    <#-- Todo: implement closeout with Fedex -->
                    <#-- Todo: implement shipment cancellation with Fedex -->
                    <#-- Todo: implement shipment tracking with Fedex -->
                    ${shipmentRouteSegment.homeDeliveryType?default(uiLabelMap.ProductShipmentNone)}
                        <#if shipmentRouteSegment.homeDeliveryDate?exists>
                            &nbsp;(${shipmentRouteSegment.homeDeliveryDate?string("yyyy-MM-dd")})
                        </#if>
                        <br/>
                    </#if>
                </#if>
            </div>

            <div class="btn-bar pull-right">
                <a class="btn btn-primary btn-sm" href="javascript:document.updateShipmentRouteSegmentForm${shipmentRouteSegmentData_index}.submit();"
                >${uiLabelMap.CommonUpdate}</a>

                <a class="btn btn-primary btn-sm" href="javascript:document.duplicateShipmentRouteSegment_${shipmentRouteSegmentData_index}.submit();"
                >${uiLabelMap.CommonDuplicate}</a>

                <a class="btn btn-primary btn-sm" href="javascript:document.deleteShipmentRouteSegment_${shipmentRouteSegmentData_index}.submit();"
                >${uiLabelMap.CommonDelete}</a>
            </div>
        </form>
        <form name="upsShipmentConfirm_${shipmentRouteSegmentData_index}" method="post" action="<@ofbizUrl>upsShipmentConfirm</@ofbizUrl>">
            <input type="hidden" name="shipmentId" value="${shipmentRouteSegment.shipmentId}"/>
            <input type="hidden" name="shipmentRouteSegmentId" value="${shipmentRouteSegment.shipmentRouteSegmentId}"/>
        </form>
        <form name="upsShipmentAccept_${shipmentRouteSegmentData_index}" method="post" action="<@ofbizUrl>upsShipmentAccept</@ofbizUrl>">
            <input type="hidden" name="shipmentId" value="${shipmentRouteSegment.shipmentId}"/>
            <input type="hidden" name="shipmentRouteSegmentId" value="${shipmentRouteSegment.shipmentRouteSegmentId}"/>
        </form>
        <form name="upsVoidShipment_${shipmentRouteSegmentData_index}" method="post" action="<@ofbizUrl>upsVoidShipment</@ofbizUrl>">
            <input type="hidden" name="shipmentId" value="${shipmentRouteSegment.shipmentId}"/>
            <input type="hidden" name="shipmentRouteSegmentId" value="${shipmentRouteSegment.shipmentRouteSegmentId}"/>
        </form>
        <form name="upsTrackShipment_${shipmentRouteSegmentData_index}" method="post" action="<@ofbizUrl>upsTrackShipment</@ofbizUrl>">
            <input type="hidden" name="shipmentId" value="${shipmentRouteSegment.shipmentId}"/>
            <input type="hidden" name="shipmentRouteSegmentId" value="${shipmentRouteSegment.shipmentRouteSegmentId}"/>
        </form>

        <form name="dhlShipmentConfirm_${shipmentRouteSegmentData_index}" method="post" action="<@ofbizUrl>dhlShipmentConfirm</@ofbizUrl>">
            <input type="hidden" name="shipmentId" value="${shipmentRouteSegment.shipmentId}"/>
            <input type="hidden" name="shipmentRouteSegmentId" value="${shipmentRouteSegment.shipmentRouteSegmentId}"/>
        </form>

        <form name="fedexShipmentConfirm_${shipmentRouteSegmentData_index}" method="post" action="<@ofbizUrl>fedexShipmentConfirm</@ofbizUrl>">
            <input type="hidden" name="shipmentId" value="${shipmentRouteSegment.shipmentId}"/>
            <input type="hidden" name="shipmentRouteSegmentId" value="${shipmentRouteSegment.shipmentRouteSegmentId}"/>
        </form>
    </div>

        <#list shipmentPackageRouteSegs as shipmentPackageRouteSeg>
            <div class="panel-body">
                <form action="<@ofbizUrl>updateRouteSegmentShipmentPackage</@ofbizUrl>" method="post" class="form-horizontal"
                      name="updateShipmentPackageRouteSegForm${shipmentRouteSegmentData_index}${shipmentPackageRouteSeg_index}">
                    <input type="hidden" name="shipmentId" value="${shipmentId}"/>
                    <input type="hidden" name="shipmentRouteSegmentId" value="${shipmentPackageRouteSeg.shipmentRouteSegmentId}"/>
                    <input type="hidden" name="shipmentPackageSeqId" value="${shipmentPackageRouteSeg.shipmentPackageSeqId}"/>

                    <div class="form-group">
                        <div class="control-label col-md-2">${uiLabelMap.ProductPackage} ${shipmentPackageRouteSeg.shipmentPackageSeqId}
                            <#if shipmentPackageRouteSeg.labelImage?exists>
                                <a class="btn btn-primary btn-sm"
                                   href="javascript:document.viewShipmentPackageRouteSegLabelImage_${shipmentRouteSegmentData_index}_${shipmentPackageRouteSeg_index}.submit();"
                                   class="buttontext">${uiLabelMap.ProductViewLabelImage}</a>
                            </#if>物流单号:
                        </div>

                        <div class="col-md-4"><input class="form-control" type="text" size="22" name="trackingCode"
                                                     value="${shipmentPackageRouteSeg.trackingCode?if_exists}"/></div>

                        <div class="control-label col-md-2">包装盒编号:</div>
                        <div class="col-md-4"><input type="text" size="5" class="form-control" name="boxNumber" value="${shipmentPackageRouteSeg.boxNumber?if_exists}"/></div>
                    </div>


                    <div class="pull-right">
                        <a class="btn btn-primary btn-sm"
                           href="javascript:document.updateShipmentPackageRouteSegForm${shipmentRouteSegmentData_index}${shipmentPackageRouteSeg_index}.submit();"
                           class="buttontext">${uiLabelMap.CommonUpdate}</a>
                        <a class="btn btn-primary btn-sm"
                           href="javascript:document.deleteRouteSegmentShipmentPackage_${shipmentRouteSegmentData_index}_${shipmentPackageRouteSeg_index}.submit();"
                           class="buttontext">${uiLabelMap.CommonDelete}</a>
                    </div>
                </form>
                <form name="viewShipmentPackageRouteSegLabelImage_${shipmentRouteSegmentData_index}_${shipmentPackageRouteSeg_index}" method="post"
                      action="<@ofbizUrl>viewShipmentPackageRouteSegLabelImage</@ofbizUrl>">
                    <input type="hidden" name="shipmentId" value="${shipmentPackageRouteSeg.shipmentId}"/>
                    <input type="hidden" name="shipmentPackageSeqId" value="${shipmentPackageRouteSeg.shipmentPackageSeqId}"/>
                    <input type="hidden" name="shipmentRouteSegmentId" value="${shipmentPackageRouteSeg.shipmentRouteSegmentId}"/>
                </form>
                <form name="deleteRouteSegmentShipmentPackage_${shipmentRouteSegmentData_index}_${shipmentPackageRouteSeg_index}" method="post"
                      action="<@ofbizUrl>deleteRouteSegmentShipmentPackage</@ofbizUrl>">
                    <input type="hidden" name="shipmentId" value="${shipmentId}"/>
                    <input type="hidden" name="shipmentPackageSeqId" value="${shipmentPackageRouteSeg.shipmentPackageSeqId}"/>
                    <input type="hidden" name="shipmentRouteSegmentId" value="${shipmentPackageRouteSeg.shipmentRouteSegmentId}"/>
                </form>
            </div>
        </#list>
    </#list>
</div>

<#-- <@htmlScreenTemplate.renderScreenletBegin id="" title="${uiLabelMap.PageTitleAddShipmentRouteSegment} "/>
<table cellspacing="0" class="table table-bordered table-responsive">
 <form action="<@ofbizUrl>createShipmentRouteSegment</@ofbizUrl>" method="post" name="createShipmentRouteSegmentForm">
     <input type="hidden" name="shipmentId" value="${shipmentId}"/>
     <tr>
         <td>
             <div>
                 <span class="text-info">${uiLabelMap.ProductNewSegment}</span>
                 <br/>
                 <a class="btn btn-primary btn-sm" href="javascript:document.createShipmentRouteSegmentForm.submit();" class="buttontext">${uiLabelMap.CommonCreate}</a>
             </div>
         </td>
         <td>
             <div>
                 <select name="carrierPartyId" class="form-control">
                     <option value="">&nbsp;</option>
                     <#list carrierPartyDatas as carrierPartyData>
                         <option value="${carrierPartyData.party.partyId}">${(carrierPartyData.person.firstName)?if_exists} ${(carrierPartyData.person.middleName)?if_exists} ${(carrierPartyData.person.lastName)?if_exists} ${(carrierPartyData.partyGroup.groupName)?if_exists}
                             [${carrierPartyData.party.partyId}]
                         </option>
                     </#list>
                 </select>
                 <select name="shipmentMethodTypeId" class="form-control">
                     <#list shipmentMethodTypes as shipmentMethodTypeOption>
                         <option value="${shipmentMethodTypeOption.shipmentMethodTypeId}">${shipmentMethodTypeOption.get("description",locale)}</option>
                     </#list>
                 </select>
                 <br/>
                 <select name="originFacilityId" class="form-control">
                     <option value="">&nbsp;</option>
                     <#list facilities as facility>
                         <option value="${facility.facilityId}">${facility.facilityName} [${facility.facilityId}]</option>
                     </#list>
                 </select>
                 <select name="destFacilityId" class="form-control">
                     <option value="">&nbsp;</option>
                     <#list facilities as facility>
                         <option value="${facility.facilityId}">${facility.facilityName} [${facility.facilityId}]</option>
                     </#list>
                 </select>
                 <br/>
                 <input type="text" size="15" name="originContactMechId" value="" class="form-control"/>
                 <input type="text" size="15" name="destContactMechId" value="" class="form-control"/>
                 <br/>
                 <input type="text" size="15" name="originTelecomNumberId" value="" class="form-control"/>
                 <input type="text" size="15" name="destTelecomNumberId" value="" class="form-control"/>
             </div>
         </td>
         <td>
             <select name="carrierServiceStatusId" class="form-control">
                 <option value="">&nbsp;</option>
                 <#list carrierServiceStatusValidChangeToDetails?if_exists as carrierServiceStatusValidChangeToDetail>
                     <option value="${carrierServiceStatusValidChangeToDetail.statusIdTo}">${carrierServiceStatusValidChangeToDetail.transitionName}
                         [${carrierServiceStatusValidChangeToDetail.description}]
                     </option>
                 </#list>
             </select>
             <br/>
             <input type="text" name="trackingIdNumber" value="" class="form-control"/>
             <br/>
             <@htmlTemplate.renderDateTimeField name="estimatedStartDate" event="" action="" className="" alert="" title="Format: yyyy-MM-dd HH:mm:ss.SSS" value="" size="25" maxlength="30" id="estimatedStartDate3" dateType="date" shortDateInput=false timeDropdownParamName="" defaultDateTimeString="" localizedIconTitle="" timeDropdown="" timeHourName="" classString="" hour1="" hour2="" timeMinutesName="" minutes="" isTwelveHour="" ampmName="" amSelected="" pmSelected="" compositeType="" formName=""/>
             <@htmlTemplate.renderDateTimeField name="estimatedArrivalDate" event="" action="" className="" alert="" title="Format: yyyy-MM-dd HH:mm:ss.SSS" value="" size="25" maxlength="30" id="estimatedArrivalDate3" dateType="date" shortDateInput=false timeDropdownParamName="" defaultDateTimeString="" localizedIconTitle="" timeDropdown="" timeHourName="" classString="" hour1="" hour2="" timeMinutesName="" minutes="" isTwelveHour="" ampmName="" amSelected="" pmSelected="" compositeType="" formName=""/>
             <br/>
             <@htmlTemplate.renderDateTimeField name="actualStartDate" event="" action="" className="" alert="" title="Format: yyyy-MM-dd HH:mm:ss.SSS" value="" size="25" maxlength="30" id="actualArrivalDate3" dateType="date" shortDateInput=false timeDropdownParamName="" defaultDateTimeString="" localizedIconTitle="" timeDropdown="" timeHourName="" classString="" hour1="" hour2="" timeMinutesName="" minutes="" isTwelveHour="" ampmName="" amSelected="" pmSelected="" compositeType="" formName=""/>
             <@htmlTemplate.renderDateTimeField name="actualArrivalDate" event="" action="" className="" alert="" title="Format: yyyy-MM-dd HH:mm:ss.SSS" value="" size="25" maxlength="30" id="actualArrivalDate3" dateType="date" shortDateInput=false timeDropdownParamName="" defaultDateTimeString="" localizedIconTitle="" timeDropdown="" timeHourName="" classString="" hour1="" hour2="" timeMinutesName="" minutes="" isTwelveHour="" ampmName="" amSelected="" pmSelected="" compositeType="" formName=""/>
         </td>
         <td>
             <input type="text" size="5" name="billingWeight" value="${(shipmentRouteSegment.billingWeight)?if_exists}" class="form-control"/>
             <select name="billingWeightUomId" class="form-control">
                 <option value="">&nbsp;</option>
                 <#list weightUoms as weightUom>
                     <option value="${weightUom.uomId}">${weightUom.get("description",locale)} [${weightUom.abbreviation}]</option>
                 </#list>
             </select>
             <br/>
             <select name="currencyUomId" class="form-control">
                 <option value="">&nbsp;</option>
                 <#list currencyUoms as altCurrencyUom>
                     <option value="${altCurrencyUom.uomId}">${altCurrencyUom.get("description",locale)} [${altCurrencyUom.uomId}]</option>
                 </#list>
             </select>
             <br/>
             <input type="text" size="8" name="actualTransportCost" class="form-control"/>
             <br/>
             <input type="text" size="8" name="actualServiceCost" class="form-control"/>
             <br/>
             <input type="text" size="8" name="actualOtherCost" class="form-control"/>
             <br/>
             <input type="text" size="8" name="actualCost" class="form-control"/>
         </td>
     </tr>
 </form>
</table>
 <@htmlScreenTemplate.renderScreenletEnd/>-->
<#else>

    <@htmlScreenTemplate.renderScreenletBegin id="" title="${uiLabelMap.ProductShipmentNotFoundId} : [${shipmentId?if_exists}] "/>
    <@htmlScreenTemplate.renderScreenletEnd/>
</#if>
