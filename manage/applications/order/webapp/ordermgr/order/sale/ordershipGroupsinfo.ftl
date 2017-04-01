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
<div class="panel panel-default f-s-12">
<#if shipGroups?has_content >
    <#list shipGroups as shipGroup>
        <#assign shipmentMethodType = shipGroup.getRelatedOne("ShipmentMethodType")?if_exists>
        <#assign shipGroupAddress = shipGroup.getRelatedOne("PostalAddress")?if_exists>
        <div class="panel-heading">
            <div class="pull-right ">
                <#if orderHeader?has_content && orderHeader.statusId != "ORDER_CANCELLED" && orderHeader.statusId != "ORDER_COMPLETED" && orderHeader.statusId != "ORDER_REJECTED">
                    <@htmlScreenTemplate.renderModalPage id="newShippingAddressForm_${shipGroup_index}" description="新增收货地址" name="newShippingAddressForm_${shipGroup_index}" modalTitle="创建新的发货地址"
                    modalUrl="addShippingAddressForm" buttonStyle="btn btn-white btn-xs" buttonType="custom"
                    targetParameterIter="orderId:'${orderId?if_exists}',partyId:'${partyId?if_exists}',oldContactMechId:'${shipGroup.contactMechId?if_exists}',shipGroupSeqId:'${shipGroup.shipGroupSeqId?if_exists}'"/>

                    <@htmlScreenTemplate.renderModalPage id="editOrderItemShipGroup_${shipGroup_index}" description="修改收货地址" name="editOrderItemShipGroup_${shipGroup_index}" modalTitle="修改发货地址"
                    modalUrl="editOrderItemShipGroup" buttonStyle="btn btn-white btn-xs" buttonType="custom"
                    targetParameterIter="orderId:'${orderId?if_exists}',partyId:'${partyId?if_exists}',oldContactMechId:'${shipGroup.contactMechId?if_exists}',shipGroupSeqId:'${shipGroup.shipGroupSeqId?if_exists}'"/>

                    <@htmlScreenTemplate.renderModalPage id="updateOrderNote_${shipGroup_index}" description="修改操作说明" name="updateOrderNote_${shipGroup_index}" modalTitle="操作说明"
                    modalUrl="updateShippingAddressForm" buttonStyle="btn btn-white btn-xs" buttonType="custom"
                    targetParameterIter="orderId:'${orderId?if_exists}',partyId:'${partyId?if_exists}',oldContactMechId:'${shipGroup.contactMechId?if_exists}',shipGroupSeqId:'${shipGroup.shipGroupSeqId?if_exists}'"/>
                </#if>
            <#-- shipment actions -->
                <#if security.hasEntityPermission("ORDERMGR", "_UPDATE", session) && ((orderHeader.statusId == "ORDER_CREATED") || (orderHeader.statusId == "ORDER_APPROVED") || (orderHeader.statusId == "ORDER_PICKED")|| (orderHeader.statusId == "ORDER_PACKED"))>
                <#-- Manual shipment options -->
                    <#if orderHeader.orderTypeId == "SALES_ORDER">
                        <#if !shipGroup.supplierPartyId?has_content>
                            <#if orderHeader.statusId == "ORDER_APPROVED">
                                <a class="btn btn-white btn-xs"
                                   href="/facility/control/PackOrder?facilityId=${storeFacilityId?if_exists}&amp;orderId=${orderId}&amp;shipGroupSeqId=${shipGroup.shipGroupSeqId}&amp;externalLoginKey=${externalLoginKey}"
                                >${uiLabelMap.OrderPackShipmentForShipGroup}</a>
                            </#if>
                        <#-- <a href="javascript:document.createShipment_${shipGroup.shipGroupSeqId}.submit()"
                            class="btn btn-primary btn-sm">${uiLabelMap.OrderNewShipmentForShipGroup}</a>-->
                           <#-- <@htmlScreenTemplate.renderModalPage id="createShipment_${shipGroup.shipGroupSeqId}" name="createShipment_${shipGroup.shipGroupSeqId}"
                            modalUrl="/facility/control/createShipment" buttonType="custom" buttonStyle="btn btn-white btn-xs"
                            targetParameterIter="primaryOrderId:'${orderId}',primaryShipGroupSeqId:'${shipGroup.shipGroupSeqId}',statusId:'SHIPMENT_INPUT',facilityId:'${storeFacilityId?if_exists}',estimatedShipDate:'${shipGroup.shipByDate?if_exists}'"
                            modalTitle = "${StringUtil.wrapString(uiLabelMap.OrderNewShipmentForShipGroup)}" description="${uiLabelMap.OrderNewShipmentForShipGroup}"/>-->
                        </#if>
                    </#if>
                </#if>

                <#assign shipGroupShipments = shipGroup.getRelated("PrimaryShipment")>
                <#if shipGroupShipments?has_content>
                    <#list shipGroupShipments as shipment>
                        <a href="/facility/control/ViewShipment?shipmentId=${shipment.shipmentId}&amp;externalLoginKey=${externalLoginKey}"
                           class="btn btn-white btn-xs m-r-5">查看配送信息${shipment_index+1}</a>
                    </#list>
                </#if>
                <#if currentStatus.statusId != "ORDER_CANCELLED" && orderHeader.orderTypeId != "ORDER_REJECTED">
                    <a target='_BLANK' href='shipGroups.pdf?orderId=${orderId}&amp;shipGroupSeqId=${shipGroup.shipGroupSeqId}'
                       class="btn btn-white btn-xs m-r-5">${uiLabelMap.OrderShipGroup} PDF</a>
                </#if>
                <form name="createShipment_${shipGroup.shipGroupSeqId}" method="post" action="/facility/control/createShipment" class="form-inline">
                    <input type="hidden" name="primaryOrderId" value="${orderId}"/>
                    <input type="hidden" name="primaryShipGroupSeqId" value="${shipGroup.shipGroupSeqId}"/>
                    <input type="hidden" name="statusId" value="SHIPMENT_INPUT"/>
                    <input type="hidden" name="facilityId" value="${storeFacilityId?if_exists}"/>
                    <input type="hidden" name="estimatedShipDate" value="${shipGroup.shipByDate?if_exists}"/>
                </form>
            </div>
            <h4 class="panel-title">配送信息-${shipGroup_index+1}</h4>
        </div>
        <#if shipmentMethodType?has_content || shipGroupAddress?has_content>
            <div class="panel-body">

            <#--<div class="panel panel-default">
                <div class="panel-heading">
                    <div class="panel-heading-btn">
                        <a target="_BLANK" href="<@ofbizUrl>shipGroups.pdf?orderId=${orderId}&amp;shipGroupSeqId=${shipGroup.shipGroupSeqId}</@ofbizUrl>">${uiLabelMap.OrderShipGroup} PDF</a>
                        <a href="javascript:" class="btn btn-xs btn-icon btn-circle btn-warning" data-click="panel-collapse"><i class="fa fa-minus"></i></a>
                        <a href="javascript:" class="btn btn-xs btn-icon btn-circle btn-danger" data-click="panel-remove"><i class="fa fa-times"></i></a>
                    </div>
                    <h4 class="panel-title">&nbsp;${uiLabelMap.OrderShipmentInformation} - ${shipGroup.shipGroupSeqId}</h4>
                </div>-->
                <div class="form-horizontal">
                    <div class="form-group">
                        <div class="text-right col-md-2">收货人:</div>
                        <div class="col-md-4">${shipGroupAddress.toName?default("")}</div>
                        <div class="text-right col-md-2">联系方式:</div>
                        <div class="col-md-4">${shipGroupAddress.mobilePhone?default("")}</div>
                    </div>
                    <div class="form-group">
                        <div class="text-right col-md-2">收货地址:</div>
                        <div class="col-md-4">${Static["org.ofbiz.common.geo.GeoWorker"].getGeoNameById(shipGroupAddress.city,delegator)}${(shipGroupAddress.address1)?default("")}</div>
                        <div class="text-right col-md-2">配送方式:</div>

                        <div class="col-md-4"><#if shipmentMethodType?has_content>${shipmentMethodType.get("description",locale)?if_exists}</#if></div>
                    </div>
                    <div class="form-group">
                        <div class="text-right col-md-2">物流单号:</div>
                        <div class="col-md-4">
                            <#if shipGroup.trackingNumber?has_content || orderShipmentInfoSummaryList?has_content>
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
                                                <#if orderShipmentInfoSummary.carrierPartyId?has_content>(${uiLabelMap.ProductCarrier}: ${orderShipmentInfoSummary.carrierPartyId}
                                                    )</#if>
                                            </div>
                                        </#if>
                                    </#list>
                                </#if>
                            </#if>
                        </div>
                        <div class="text-right col-md-2">操作说明:</div>
                        <div class="col-md-4">${shipGroup.shippingInstructions?default("")}</div>
                    </div>
                </div>
            </div>
        <#else>
            <div class="panel-body">
                <div class="text-danger f-s-16">暂无配送信息</div>
            </div>
        </#if>
    </#list>
</#if>
</div>
