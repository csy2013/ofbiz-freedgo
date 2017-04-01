<script language="JavaScript" type="text/javascript">
    function editInstruction(shipGroupSeqId) {
        jQuery('#shippingInstructions_' + shipGroupSeqId).css({display: 'block'});
        jQuery('#saveInstruction_' + shipGroupSeqId).css({display: 'inline'});
        jQuery('#editInstruction_' + shipGroupSeqId).css({display: 'none'});
        jQuery('#instruction_' + shipGroupSeqId).css({display: 'none'});
    }
    function addInstruction(shipGroupSeqId) {
        jQuery('#shippingInstructions_' + shipGroupSeqId).css({display: 'block'});
        jQuery('#saveInstruction_' + shipGroupSeqId).css({display: 'inline'});
        jQuery('#addInstruction_' + shipGroupSeqId).css({display: 'none'});
    }
    function saveInstruction(shipGroupSeqId) {
        jQuery("#updateShippingInstructionsForm_" + shipGroupSeqId).submit();
    }
    function editGiftMessage(shipGroupSeqId) {
        jQuery('#giftMessage_' + shipGroupSeqId).css({display: 'block'});
        jQuery('#saveGiftMessage_' + shipGroupSeqId).css({display: 'inline'});
        jQuery('#editGiftMessage_' + shipGroupSeqId).css({display: 'none'});
        jQuery('#message_' + shipGroupSeqId).css({display: 'none'});
    }
    function addGiftMessage(shipGroupSeqId) {
        jQuery('#giftMessage_' + shipGroupSeqId).css({display: 'block'});
        jQuery('#saveGiftMessage_' + shipGroupSeqId).css({display: 'inline'});
        jQuery('#addGiftMessage_' + shipGroupSeqId).css({display: 'none'});
    }
    function saveGiftMessage(shipGroupSeqId) {
        jQuery("#setGiftMessageForm_" + shipGroupSeqId).submit();
    }
</script>



<#if shipGroups?has_content && (!orderHeader.salesChannelEnumId?exists || orderHeader.salesChannelEnumId != "POS_SALES_CHANNEL")>
    <#list shipGroups as shipGroup>
        <#assign shipmentMethodType = shipGroup.getRelatedOne("ShipmentMethodType")?if_exists>
        <#assign shipGroupAddress = shipGroup.getRelatedOne("PostalAddress")?if_exists>

    <@htmlScreenTemplate.renderScreenletBegin id="orderShippemntInfomation" title="${uiLabelMap.OrderShipmentInformation} - ${shipGroup.shipGroupSeqId}" collapsed=true
    addHeadBarHtml="<a target='_BLANK' href='shipGroups.pdf?orderId=${orderId}&amp;shipGroupSeqId=${shipGroup.shipGroupSeqId}'>${uiLabelMap.OrderShipGroup} PDF</a>" />
    <#--<div class="panel panel-default">
        <div class="panel-heading">
            <div class="panel-heading-btn">
                <a target="_BLANK" href="<@ofbizUrl>shipGroups.pdf?orderId=${orderId}&amp;shipGroupSeqId=${shipGroup.shipGroupSeqId}</@ofbizUrl>">${uiLabelMap.OrderShipGroup} PDF</a>
                <a href="javascript:" class="btn btn-xs btn-icon btn-circle btn-warning" data-click="panel-collapse"><i class="fa fa-minus"></i></a>
                <a href="javascript:" class="btn btn-xs btn-icon btn-circle btn-danger" data-click="panel-remove"><i class="fa fa-times"></i></a>
            </div>
            <h4 class="panel-title">&nbsp;${uiLabelMap.OrderShipmentInformation} - ${shipGroup.shipGroupSeqId}</h4>
        </div>-->

            <form name="updateOrderItemShipGroup" method="post" action="<@ofbizUrl>updateOrderItemShipGroup</@ofbizUrl>" class="form-inline">
                <input type="hidden" name="orderId" value="${orderId?if_exists}"/>
                <input type="hidden" name="shipGroupSeqId" value="${shipGroup.shipGroupSeqId?if_exists}"/>
                <input type="hidden" name="contactMechPurposeTypeId" value="SHIPPING_LOCATION"/>
                <input type="hidden" name="oldContactMechId" value="${shipGroup.contactMechId?if_exists}"/>

                <div class="form-group">
                    <div class="input-group">
                        <span class="label-white">&nbsp;${uiLabelMap.OrderAddress}</span>
                        <#if orderHeader?has_content && orderHeader.statusId != "ORDER_CANCELLED" && orderHeader.statusId != "ORDER_COMPLETED" && orderHeader.statusId != "ORDER_REJECTED">
                            <select name="contactMechId" class="form-control">
                                <option selected="selected" value="${shipGroup.contactMechId?if_exists}">${(shipGroupAddress.address1)?default("")}
                                    - ${shipGroupAddress.city?default("")}</option>
                                <#if shippingContactMechList?has_content>
                                    <option disabled="disabled" value=""></option>
                                    <#list shippingContactMechList as shippingContactMech>
                                        <#assign shippingPostalAddress = shippingContactMech.getRelatedOne("PostalAddress")?if_exists>
                                        <#if shippingContactMech.contactMechId?has_content>
                                            <option value="${shippingContactMech.contactMechId?if_exists}">${(shippingPostalAddress.address1)?default("")}
                                                - ${shippingPostalAddress.city?default("")}</option>
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
                            <span class="label-white">&nbsp;<b>${uiLabelMap.CommonMethod}</b></span>

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
                    <#if orderHeader?has_content && orderHeader.statusId != "ORDER_CANCELLED" && orderHeader.statusId != "ORDER_COMPLETED" && orderHeader.statusId != "ORDER_REJECTED">

                        <input type="submit" value="${uiLabelMap.CommonUpdate}" class="btn btn-primary btn-sm"/>
                        <@htmlScreenTemplate.renderModalPage id="newShippingAddressForm" description="新" name="newShippingAddressForm" modalTitle="创建新的发货地址" modalUrl="addShippingAddressForm"
                        targetParameterIter="orderId:'${orderId?if_exists}',partyId:'${partyId?if_exists}',oldContactMechId:'${shipGroup.contactMechId?if_exists}',shipGroupSeqId:'${shipGroup.shipGroupSeqId?if_exists}'"/>

                       <#-- <a class="btn btn-primary btn-sm" id="newShippingAddress" href="javascript:void(0);">创建新的发货地址</a>
                        <script type="text/javascript">
                            jQuery("#newShippingAddress").click(function () {
                                jQuery("#newShippingAddressForm").dialog("open")
                            });
                        </script>-->

                    </#if>
                    <#if !shipGroup.contactMechId?has_content && !shipGroup.shipmentMethodTypeId?has_content>
                        <#assign noShipment = "true">
                        <tr>
                            <td colspan="3" align="center">${uiLabelMap.OrderNotShipped}</td>
                        </tr>
                    </#if>
                </div>
            </form>
            <hr/><br/>

            <#--<script language="JavaScript" type="text/javascript">
                jQuery(document).ready(function () {
                    jQuery("#newShippingAddressForm").dialog({
                        autoOpen: false, modal: true,
                        buttons: {
                            '${uiLabelMap.CommonSubmit}': function () {
                                var addShippingAddress = jQuery("#addShippingAddress");
                                jQuery("<p>${uiLabelMap.CommonUpdatingData}</p>").insertBefore(addShippingAddress);
                                addShippingAddress.submit();
                            },
                            '${uiLabelMap.CommonClose}': function () {
                                jQuery(this).dialog('close');
                            }
                        }
                    });
                });
            </script>-->
            <div class="table-responsive">
                <table class="table table-bordered">
                    <#if shipGroup.supplierPartyId?has_content>
                        <#assign supplier =  delegator.findByPrimaryKey("PartyGroup", Static["org.ofbiz.base.util.UtilMisc"].toMap("partyId", shipGroup.supplierPartyId))?if_exists />

                        <tr>
                            <td align="right" valign="top" width="15%">
                                <span class="label-white">&nbsp;${uiLabelMap.ProductDropShipment} - ${uiLabelMap.PartySupplier}</span>
                            </td>

                            <td valign="top" width="80%">
                                <#if supplier?has_content> - ${supplier.description?default(shipGroup.supplierPartyId)}</#if>
                            </td>
                        </tr>
                    </#if>

                <#-- This section appears when Shipment of order is in picked status and its items are packed,this case comes when new shipping estimates based on weight of packages are more than or less than default percentage (defined in shipment.properties) of original shipping estimate-->
                <#-- getShipGroupEstimate method of ShippingEvents class can be used for get shipping estimate from system, on the basis of new package's weight -->
                    <#if shippingRateList?has_content>
                        <#if orderReadHelper.getOrderTypeId() != "PURCHASE_ORDER">

                            <tr>
                                <td colspan="3">
                                    <table>
                                        <tr>
                                            <td>
                                                <span class="label-white">&nbsp;${uiLabelMap.OrderOnlineUPSShippingEstimates}</span>
                                            </td>
                                        </tr>
                                        <form name="UpdateShippingMethod" method="post" action="<@ofbizUrl>updateShippingMethodAndCharges</@ofbizUrl>" class="form-inline">
                                            <#list shippingRateList as shippingRate>
                                                <tr>
                                                    <td>
                                                        <#assign shipmentMethodAndAmount = shippingRate.shipmentMethodTypeId + "@" + "UPS" + "*" + shippingRate.rate>
                                                        <input type='radio' name='shipmentMethodAndAmount' value='${shipmentMethodAndAmount?if_exists}'/>
                                                        UPS&nbsp;${shippingRate.shipmentMethodDescription?if_exists}
                                                        <#if (shippingRate.rate > -1)>
                                                            <@ofbizCurrency amount=shippingRate.rate isoCode=orderReadHelper.getCurrency()/>
                                                        <#else>
                                                        ${uiLabelMap.OrderCalculatedOffline}
                                                        </#if>
                                                    </td>
                                                </tr>
                                            </#list>
                                            <input type="hidden" name="shipmentRouteSegmentId" value="${shipmentRouteSegmentId?if_exists}"/>
                                            <input type="hidden" name="shipmentId" value="${pickedShipmentId?if_exists}"/>
                                            <input type="hidden" name="orderAdjustmentId" value="${orderAdjustmentId?if_exists}"/>
                                            <input type="hidden" name="orderId" value="${orderId?if_exists}"/>
                                            <input type="hidden" name="shipGroupSeqId" value="${shipGroup.shipGroupSeqId?if_exists}"/>
                                            <input type="hidden" name="contactMechPurposeTypeId" value="SHIPPING_LOCATION"/>
                                            <input type="hidden" name="oldContactMechId" value="${shipGroup.contactMechId?if_exists}"/>
                                            <input type="hidden" name="shippingAmount" value="${shippingAmount?if_exists}"/>
                                            <tr>
                                                <td valign="top" width="80%">
                                                    <input type="submit" value="${uiLabelMap.CommonUpdate}" class="btn btn-primary btn-sm"/>
                                                </td>
                                            </tr>
                                        </form>
                                    </table>
                                </td>
                            </tr>
                        </#if>
                    </#if>

                <#-- tracking number -->
                    <#if shipGroup.trackingNumber?has_content || orderShipmentInfoSummaryList?has_content>

                        <tr>
                            <td align="right" valign="top" width="15%">
                                <span class="label-white">&nbsp;${uiLabelMap.OrderTrackingNumber}</span>
                            </td>

                            <td valign="top" width="80%">
                            <#-- TODO: add links to UPS/FEDEX/etc based on carrier partyId  -->
                                <#if shipGroup.trackingNumber?has_content>
                                ${shipGroup.trackingNumber}
                                </#if>
                                <#if orderShipmentInfoSummaryList?has_content>
                                    <#list orderShipmentInfoSummaryList as orderShipmentInfoSummary>
                                        <#if orderShipmentInfoSummary.shipGroupSeqId?if_exists == shipGroup.shipGroupSeqId?if_exists>
                                            <div>
                                                <#if (orderShipmentInfoSummaryList?size > 1)>${orderShipmentInfoSummary.shipmentPackageSeqId}: </#if>
                                            ${uiLabelMap.CommonIdCode}: ${orderShipmentInfoSummary.trackingCode?default("[${uiLabelMap.OrderNotYetKnown}]")}
                                                <#if orderShipmentInfoSummary.boxNumber?has_content> ${uiLabelMap.ProductBox} #${orderShipmentInfoSummary.boxNumber}</#if>
                                                <#if orderShipmentInfoSummary.carrierPartyId?has_content>(${uiLabelMap.ProductCarrier}: ${orderShipmentInfoSummary.carrierPartyId})</#if>
                                            </div>
                                        </#if>
                                    </#list>
                                </#if>
                            </td>
                        </tr>
                    </#if>
                    <#if shipGroup.maySplit?has_content && noShipment?default("false") != "true">

                        <tr>
                            <td align="right" valign="top" width="15%">
                                <span class="label-white">&nbsp;${uiLabelMap.OrderSplittingPreference}</span>
                            </td>

                            <td valign="top" width="80%">
                                <div>
                                    <#if shipGroup.maySplit?upper_case == "N">
                                    ${uiLabelMap.FacilityWaitEntireOrderReady}
                                        <#if security.hasEntityPermission("ORDERMGR", "_UPDATE", session)>
                                            <#if orderHeader.statusId != "ORDER_COMPLETED" && orderHeader.statusId != "ORDER_CANCELLED">

                                                <a href="javascript:document.allowordersplit_${shipGroup.shipGroupSeqId}.submit()"
                                                   class="btn btn-primary btn-sm">${uiLabelMap.OrderAllowSplit}<form name="allowordersplit_${shipGroup.shipGroupSeqId}" method="post" action="<@ofbizUrl>allowordersplit</@ofbizUrl>" class="form-inline">
                                                    <input type="hidden" name="orderId" value="${orderId}"/>
                                                    <input type="hidden" name="shipGroupSeqId" value="${shipGroup.shipGroupSeqId}"/>
                                                </form></a>
                                            </#if>
                                        </#if>
                                    <#else>
                                    ${uiLabelMap.FacilityShipAvailable}
                                    </#if>
                                </div>
                            </td>
                        </tr>
                    </#if>


                    <tr>
                        <td align="right" valign="top" width="15%">
                            <span class="label-white">&nbsp;${uiLabelMap.OrderInstructions}</span>
                        </td>

                        <td align="left" valign="top" width="80%">
                            <#if (!orderHeader.statusId.equals("ORDER_COMPLETED")) && !(orderHeader.statusId.equals("ORDER_REJECTED")) && !(orderHeader.statusId.equals("ORDER_CANCELLED"))>
                                <form id="updateShippingInstructionsForm_${shipGroup.shipGroupSeqId}" name="updateShippingInstructionsForm" method="post"
                                      action="<@ofbizUrl>setShippingInstructions</@ofbizUrl>">
                                    <input type="hidden" name="orderId" value="${orderHeader.orderId}"/>
                                    <input type="hidden" name="shipGroupSeqId" value="${shipGroup.shipGroupSeqId}"/>
                                    <#if shipGroup.shippingInstructions?has_content>
                                        <table>
                                            <tr>
                                                <td id="instruction">
                                                    <label>${shipGroup.shippingInstructions}</label>
                                                </td>
                                                <td>
                                                    <a href="javascript:editInstruction('${shipGroup.shipGroupSeqId}');" class="btn btn-primary btn-sm"
                                                       id="editInstruction_${shipGroup.shipGroupSeqId}">${uiLabelMap.CommonEdit}</a>
                                                </td>
                                            </tr>
                                        </table>
                                    <#else>
                                        <a href="javascript:addInstruction('${shipGroup.shipGroupSeqId}');" class="btn btn-primary btn-sm"
                                           id="addInstruction_${shipGroup.shipGroupSeqId}">${uiLabelMap.CommonAdd}</a>
                                    </#if>
                                    <a href="javascript:saveInstruction('${shipGroup.shipGroupSeqId}');" class="btn btn-primary btn-sm" id="saveInstruction_${shipGroup.shipGroupSeqId}"
                                       style="display:none">${uiLabelMap.CommonSave}</a>
                                <textarea name="shippingInstructions" id="shippingInstructions_${shipGroup.shipGroupSeqId}" style="display:none" rows="0"
                                          cols="0">${shipGroup.shippingInstructions?if_exists}</textarea>
                                </form>
                            <#else>
                                <#if shipGroup.shippingInstructions?has_content>
                                    <span>${shipGroup.shippingInstructions}</span>
                                <#else>
                                    <span>${uiLabelMap.OrderThisOrderDoesNotHaveShippingInstructions}</span>
                                </#if>
                            </#if>
                        </td>
                    </tr>

                    <#if shipGroup.isGift?has_content && noShipment?default("false") != "true">

                        <tr>
                            <td align="right" valign="top" width="15%">
                                <span class="label-white">&nbsp;${uiLabelMap.OrderGiftMessage}</span>
                            </td>

                            <td>
                                <form id="setGiftMessageForm_${shipGroup.shipGroupSeqId}" name="setGiftMessageForm" method="post" action="<@ofbizUrl>setGiftMessage</@ofbizUrl>">
                                    <input type="hidden" name="orderId" value="${orderHeader.orderId}"/>
                                    <input type="hidden" name="shipGroupSeqId" value="${shipGroup.shipGroupSeqId}"/>
                                    <#if shipGroup.giftMessage?has_content>
                                        <label>${shipGroup.giftMessage}</label>
                                        <a href="javascript:editGiftMessage('${shipGroup.shipGroupSeqId}');" class="btn btn-primary btn-sm"
                                           id="editGiftMessage_${shipGroup.shipGroupSeqId}">${uiLabelMap.CommonEdit}</a>
                                    <#else>
                                        <a href="javascript:addGiftMessage('${shipGroup.shipGroupSeqId}');" class="btn btn-primary btn-sm"
                                           id="addGiftMessage_${shipGroup.shipGroupSeqId}">${uiLabelMap.CommonAdd}</a>
                                    </#if>
                                    <textarea name="giftMessage" id="giftMessage_${shipGroup.shipGroupSeqId}" style="display:none" rows="0"
                                              cols="0">${shipGroup.giftMessage?if_exists}</textarea>
                                    <a href="javascript:saveGiftMessage('${shipGroup.shipGroupSeqId}');" class="btn btn-primary btn-sm" id="saveGiftMessage_${shipGroup.shipGroupSeqId}"
                                       style="display:none">${uiLabelMap.CommonSave}</a>
                                </form>
                            </td>
                        </tr>
                    </#if>

                    <tr>
                        <td align="right" valign="top" width="15%">
                            <span class="label-white">&nbsp;${uiLabelMap.OrderShipAfterDate}</span><br/>
                            <span class="label-white">&nbsp;${uiLabelMap.OrderShipBeforeDate}</span>
                        </td>

                        <td valign="top" width="80%">
                            <form name="setShipGroupDates_${shipGroup.shipGroupSeqId}" method="post" action="<@ofbizUrl>updateOrderItemShipGroup</@ofbizUrl>" class="form-inline">
                                <input type="hidden" name="orderId" value="${orderHeader.orderId}"/>
                                <input type="hidden" name="shipGroupSeqId" value="${shipGroup.shipGroupSeqId}"/>
                                <@htmlTemplate.renderDateTimeField name="shipAfterDate" event="" action="" value="${shipGroup.shipAfterDate?if_exists}" className="" alert="" title="Format: yyyy-MM-dd HH:mm:ss.SSS"   maxlength="30" id="shipAfterDate_${shipGroup.shipGroupSeqId}" dateType="date" shortDateInput=false timeDropdownParamName="" defaultDateTimeString="" localizedIconTitle="" timeDropdown="" timeHourName="" classString="" hour1="" hour2="" timeMinutesName="" minutes="" isTwelveHour="" ampmName="" amSelected="" pmSelected="" compositeType="" formName=""/>
                                <br/><br/>
                                <@htmlTemplate.renderDateTimeField name="shipByDate" event="" action="" value="${shipGroup.shipByDate?if_exists}" className="" alert="" title="Format: yyyy-MM-dd HH:mm:ss.SSS"   maxlength="30" id="shipByDate_${shipGroup.shipGroupSeqId}" dateType="date" shortDateInput=false timeDropdownParamName="" defaultDateTimeString="" localizedIconTitle="" timeDropdown="" timeHourName="" classString="" hour1="" hour2="" timeMinutesName="" minutes="" isTwelveHour="" ampmName="" amSelected="" pmSelected="" compositeType="" formName=""/>
                                <input type="submit" value="${uiLabelMap.CommonUpdate}" class="btn btn-primary btn-sm"/>
                            </form>
                        </td>
                    </tr>


                    <#assign shipGroupShipments = shipGroup.getRelated("PrimaryShipment")>
                    <#if shipGroupShipments?has_content>

                        <tr>
                            <td align="right" valign="top" width="15%">
                                <span class="label-white">&nbsp;${uiLabelMap.FacilityShipments}</span>
                            </td>

                            <td valign="top" width="80%">
                                <#list shipGroupShipments as shipment>
                                    <div>
                                    ${uiLabelMap.CommonNbr}<a href="/facility/control/ViewShipment?shipmentId=${shipment.shipmentId}&amp;externalLoginKey=${externalLoginKey}"
                                                              class="btn btn-primary btn-sm">${shipment.shipmentId}</a>&nbsp;&nbsp;
                                        <a target="_BLANK" href="/facility/control/PackingSlip.pdf?shipmentId=${shipment.shipmentId}&amp;externalLoginKey=${externalLoginKey}"
                                           class="btn btn-primary btn-sm">${uiLabelMap.ProductPackingSlip}</a>
                                        <#if "SALES_ORDER" == orderHeader.orderTypeId && "ORDER_COMPLETED" == orderHeader.statusId>
                                            <#assign shipmentRouteSegments = delegator.findByAnd("ShipmentRouteSegment", {"shipmentId" : shipment.shipmentId})>
                                            <#if shipmentRouteSegments?has_content>
                                                <#assign shipmentRouteSegment = Static["org.ofbiz.entity.util.EntityUtil"].getFirst(shipmentRouteSegments)>
                                                <#if "UPS" == (shipmentRouteSegment.carrierPartyId)?if_exists>
                                                    <a href="javascript:document.upsEmailReturnLabel${shipment_index}.submit();"
                                                       class="btn btn-primary btn-sm">${uiLabelMap.ProductEmailReturnShippingLabelUPS}</a>
                                                </#if>
                                                <form name="upsEmailReturnLabel${shipment_index}" method="post" action="<@ofbizUrl>upsEmailReturnLabelOrder</@ofbizUrl>" class="form-inline">
                                                    <input type="hidden" name="orderId" value="${orderId}"/>
                                                    <input type="hidden" name="shipmentId" value="${shipment.shipmentId}"/>
                                                    <input type="hidden" name="shipmentRouteSegmentId" value="${shipmentRouteSegment.shipmentRouteSegmentId}"/>
                                                </form>
                                            </#if>
                                        </#if>
                                    </div>
                                </#list>
                            </td>
                        </tr>
                    </#if>

                <#-- shipment actions -->
                    <#if security.hasEntityPermission("ORDERMGR", "_UPDATE", session) && ((orderHeader.statusId == "ORDER_CREATED") || (orderHeader.statusId == "ORDER_APPROVED") || (orderHeader.statusId == "ORDER_SENT"))>
                    <#-- Manual shipment options -->
                        <tr>
                            <td colspan="3" valign="top" width="100%" >
                                <div class="row">
                                <#if orderHeader.orderTypeId == "SALES_ORDER">
                                    <#if !shipGroup.supplierPartyId?has_content>
                                        <#if orderHeader.statusId == "ORDER_APPROVED">
                                            <a href="/facility/control/PackOrder?facilityId=${storeFacilityId?if_exists}&amp;orderId=${orderId}&amp;shipGroupSeqId=${shipGroup.shipGroupSeqId}&amp;externalLoginKey=${externalLoginKey}"
                                               class="btn btn-primary btn-sm">${uiLabelMap.OrderPackShipmentForShipGroup}</a>

                                        </#if>
                                       <#-- <a href="javascript:document.createShipment_${shipGroup.shipGroupSeqId}.submit()"
                                           class="btn btn-primary btn-sm">${uiLabelMap.OrderNewShipmentForShipGroup}</a>-->
                                        <@htmlScreenTemplate.renderModalPage id="createShipment_${shipGroup.shipGroupSeqId}" name="createShipment_${shipGroup.shipGroupSeqId}"
                                        modalUrl="/facility/control/createShipment"
                                        targetParameterIter="primaryOrderId:'${orderId}',primaryShipGroupSeqId:'${shipGroup.shipGroupSeqId}',statusId:'SHIPMENT_INPUT',facilityId:'${storeFacilityId?if_exists}',estimatedShipDate:'${shipGroup.shipByDate?if_exists}'"
                                        modalTitle = "${StringUtil.wrapString(uiLabelMap.OrderNewShipmentForShipGroup)}" description="${uiLabelMap.OrderNewShipmentForShipGroup}"/>

                                        <form name="createShipment_${shipGroup.shipGroupSeqId}" method="post" action="/facility/control/createShipment" class="form-inline">
                                            <input type="hidden" name="primaryOrderId" value="${orderId}"/>
                                            <input type="hidden" name="primaryShipGroupSeqId" value="${shipGroup.shipGroupSeqId}"/>
                                            <input type="hidden" name="statusId" value="SHIPMENT_INPUT"/>
                                            <input type="hidden" name="facilityId" value="${storeFacilityId?if_exists}"/>
                                            <input type="hidden" name="estimatedShipDate" value="${shipGroup.shipByDate?if_exists}"/>
                                        </form>
                                    </#if>
                                <#else>
                                    <#assign facilities = facilitiesForShipGroup.get(shipGroup.shipGroupSeqId)>
                                    <#if facilities?has_content>
                                        <div>
                                            <form name="createShipment2_${shipGroup.shipGroupSeqId}" method="post" action="/facility/control/createShipment" class="form-inline">
                                                <input type="hidden" name="primaryOrderId" value="${orderId}"/>
                                                <input type="hidden" name="primaryShipGroupSeqId" value="${shipGroup.shipGroupSeqId}"/>
                                                <input type="hidden" name="shipmentTypeId" value="PURCHASE_SHIPMENT"/>
                                                <input type="hidden" name="statusId" value="PURCH_SHIP_CREATED"/>
                                                <input type="hidden" name="externalLoginKey" value="${externalLoginKey}"/>
                                                <input type="hidden" name="estimatedShipDate" value="${shipGroup.estimatedShipDate?if_exists}"/>
                                                <input type="hidden" name="estimatedArrivalDate" value="${shipGroup.estimatedDeliveryDate?if_exists}"/>
                                                <select name="destinationFacilityId" class="form-control">
                                                    <#list facilities as facility>
                                                        <option value="${facility.facilityId}">${facility.facilityName}</option>
                                                    </#list>
                                                </select>
                                                <input type="submit" class="btn btn-primary btn-sm" value="${uiLabelMap.OrderNewShipmentForShipGroup} [${shipGroup.shipGroupSeqId}]"/>
                                            </form>
                                        </div>
                                    <#else>
                                        <a href="javascript:document.quickDropShipOrder_${shipGroup_index}.submit();"
                                           class="btn btn-primary btn-sm">${uiLabelMap.ProductShipmentQuickComplete}</a>
                                        <a href="javascript:document.createShipment3_${shipGroup.shipGroupSeqId}.submit();"
                                           class="btn btn-primary btn-sm">${uiLabelMap.OrderNewDropShipmentForShipGroup}
                                            [${shipGroup.shipGroupSeqId}]</a>

                                        <form name="quickDropShipOrder_${shipGroup_index}" method="post" action="<@ofbizUrl>quickDropShipOrder</@ofbizUrl>" class="form-inline">
                                            <input type="hidden" name="orderId" value="${orderId}"/>
                                            <input type="hidden" name="shipGroupSeqId" value="${shipGroup.shipGroupSeqId}"/>
                                            <input type="hidden" name="externalLoginKey" value="${externalLoginKey}"/>
                                        </form>
                                        <form name="createShipment3_${shipGroup.shipGroupSeqId}" method="post" action="/facility/control/createShipment" class="form-inline">
                                            <input type="hidden" name="primaryOrderId" value="${orderId}"/>
                                            <input type="hidden" name="primaryShipGroupSeqId" value="${shipGroup.shipGroupSeqId}"/>
                                            <input type="hidden" name="shipmentTypeId" value="DROP_SHIPMENT"/>
                                            <input type="hidden" name="statusId" value="PURCH_SHIP_CREATED"/>
                                            <input type="hidden" name="externalLoginKey" value="${externalLoginKey}"/>
                                        </form>
                                    </#if>
                                </#if>
                               </div>
                            </td>
                        </tr>

                    </#if>

                </table>
            </div>

 <#--   </div>-->
    <@htmlScreenTemplate.renderScreenletEnd/>
    </#list>
</#if>
