<script language="JavaScript" type="text/javascript">
    function setNow(field) {
        eval('document.selectAllForm.' + field + '.value="${nowTimestamp}"');
    }
</script>
<@htmlScreenTemplate.renderScreenletBegin id="" title="${title}"/>
<#if invalidProductId?exists>
<div class="errorMessage">${invalidProductId}</div>
</#if>
<#-- Receiving Results -->
<#if receivedItems?has_content>
<h4>${uiLabelMap.ProductReceiptPurchaseOrder} ${purchaseOrder.orderId}</h4>
<hr/>
<div class="table-responsive">
    <table class="table table-striped table-bordered">
        <tr class="header-row">
            <th>${uiLabelMap.ProductShipmentId}</th>
            <th>${uiLabelMap.ProductReceipt}</th>
            <th>${uiLabelMap.CommonDate}</th>
            <th>${uiLabelMap.ProductPo}</th>
            <th>${uiLabelMap.ProductLine}</th>
            <th>${uiLabelMap.ProductProductId}</th>
            <th>${uiLabelMap.ProductLotId}</th>
            <th>${uiLabelMap.ProductPerUnitPrice}</th>
            <th>${uiLabelMap.CommonRejected}</th>
            <th>${uiLabelMap.CommonAccepted}</th>
            <th></th>
        </tr>
        <#list receivedItems as item>
            <form name="cancelReceivedItemsForm_${item_index}" method="post" action="<@ofbizUrl>cancelReceivedItems</@ofbizUrl>" class="form-horizontal">
                <input type="hidden" name="receiptId" value="${(item.receiptId)?if_exists}"/>
                <input type="hidden" name="purchaseOrderId" value="${(item.orderId)?if_exists}"/>
                <input type="hidden" name="facilityId" value="${facilityId?if_exists}"/>
                <tr>
                    <td><a href="<@ofbizUrl>ViewShipment?shipmentId=${item.shipmentId?if_exists}</@ofbizUrl>"
                           class="buttontext">${item.shipmentId?if_exists} ${item.shipmentItemSeqId?if_exists}</a></td>
                    <td>${item.receiptId}</td>
                    <td>${item.datetimeReceived?string('yyyy-MM-dd hh:mm')}</td>
                    <td><a href="/ordermgr/control/orderview?orderId=${item.orderId}" class="buttontext">${item.orderId}</a></td>
                    <td>${item.orderItemSeqId}</td>
                    <td>${item.productId?default("Not Found")}</td>
                    <td>${item.lotId?default("")}</td>
                    <td>${item.unitCost?default(0)?string("##0.00")}</td>
                    <td>${item.quantityRejected?default(0)?string.number}</td>
                    <td>${item.quantityAccepted?string.number}</td>
                    <td>
                        <#if (item.quantityAccepted?int > 0 || item.quantityRejected?int > 0)>
                            <a href="javascript:document.cancelReceivedItemsForm_${item_index}.submit();" class="btn btn-primary btn-sm">${uiLabelMap.CommonCancel}</a>
                        </#if>
                    </td>
                </tr>
            </form>
        </#list>
        <tr>
            <td colspan="11">
                &nbsp;
            </td>
        </tr>
    </table>
</div>
<br/>
</#if>

<#-- Single Product Receiving -->
<#if requestParameters.initialSelected?exists && product?has_content>
<form method="post" action="<@ofbizUrl>receiveSingleInventoryProduct</@ofbizUrl>" name="selectAllForm" class="form-horizontal">
    <div class="table-responsive">
        <table class="table table-striped table-bordered">
        <#-- general request fields -->
            <input type="hidden" name="facilityId" value="${requestParameters.facilityId?if_exists}"/>
            <input type="hidden" name="purchaseOrderId" value="${requestParameters.purchaseOrderId?if_exists}"/>
        <#-- special service fields -->
            <input type="hidden" name="productId" value="${requestParameters.productId?if_exists}"/>
            <#if purchaseOrder?has_content>
                <#assign unitCost = firstOrderItem.unitPrice?default(standardCosts.get(firstOrderItem.productId)?default(0))/>
                <input type="hidden" name="orderId" value="${purchaseOrder.orderId}"/>
                <input type="hidden" name="orderItemSeqId" value="${firstOrderItem.orderItemSeqId}"/>

                <div class="form-group">

                    <label class="col-md-4 control-label">${uiLabelMap.ProductPurchaseOrder}</label>

                    <div class="col-md-5">
                        <b>${purchaseOrder.orderId}</b>&nbsp;/&nbsp;<b>${firstOrderItem.orderItemSeqId}</b>
                        <#if 1 < purchaseOrderItems.size()>
                            (${uiLabelMap.ProductMultipleOrderItemsProduct} - ${purchaseOrderItems.size()}:1 ${uiLabelMap.ProductItemProduct})
                        <#else>
                            (${uiLabelMap.ProductSingleOrderItemProduct} - 1:1 ${uiLabelMap.ProductItemProduct})
                        </#if>
                    </div>
                </div>
            </#if>
            <div class="form-group">

                <label class="col-md-4 control-label">${uiLabelMap.ProductProductId}</label>

                <div class="col-md-5">
                    <b>${requestParameters.productId?if_exists}</b>
                </div>
            </div>
            <div class="form-group">

                <label class="col-md-4 control-label">${uiLabelMap.ProductProductName}</label>

                <div class="col-md-5">
                    <a href="/catalog/control/EditProduct?productId=${product.productId}${externalKeyParam?if_exists}" target="catalog"
                       class="buttontext">${product.internalName?if_exists}</a>
                </div>
            </div>
            <div class="form-group">

                <label class="col-md-4 control-label">${uiLabelMap.ProductProductDescription}</label>

                <div class="col-md-5">
                ${product.description?if_exists}
                </div>
            </div>
            <div class="form-group">

                <label class="col-md-4 control-label">${uiLabelMap.ProductItemDescription}</label>

                <div class="col-md-5">
                    <input type="text" name="itemDescription" size="30" maxlength="60" class="form-control"/>
                </div>
            </div>
            <div class="form-group">

                <label class="col-md-4 control-label">${uiLabelMap.ProductInventoryItemType}</label>

                <div class="col-md-5">
                    <select name="inventoryItemTypeId" class="form-control">
                        <#list inventoryItemTypes as nextInventoryItemType>
                            <option value="${nextInventoryItemType.inventoryItemTypeId}"
                                <#if (facility.defaultInventoryItemTypeId?has_content) && (nextInventoryItemType.inventoryItemTypeId == facility.defaultInventoryItemTypeId)>
                                    selected="selected"
                                </#if>
                                    >${nextInventoryItemType.get("description",locale)?default(nextInventoryItemType.inventoryItemTypeId)}</option>
                        </#list>
                    </select>
                </div>
            </div>


            <div class="form-group">

                <label class="col-md-4 control-label">${uiLabelMap.ProductFacilityOwner}</label>

                <div class="col-md-5">
                    <@htmlTemplate.lookupField formName="selectAllForm" name="ownerPartyId" id="ownerPartyId" fieldFormName="LookupPartyName"/>
                </div>
            </div>
            <div class="form-group">

                <label class="col-md-4 control-label">${uiLabelMap.ProductSupplier}</label>

                <div class="col-md-5">
                    <select name="partyId" class="form-control">
                        <option value=""></option>
                        <#if supplierPartyIds?has_content>
                            <#list supplierPartyIds as supplierPartyId>
                                <option value="${supplierPartyId}" <#if supplierPartyId == parameters.partyId?if_exists> selected="selected"</#if>>
                                    [${supplierPartyId}] ${Static["org.ofbiz.party.party.PartyHelper"].getPartyName(delegator, supplierPartyId, true)}
                                </option>
                            </#list>
                        </#if>
                    </select>
                </div>
            </div>
            <div class="form-group">

                <label class="col-md-4 control-label">${uiLabelMap.ProductDateReceived}</label>

                <div class="col-md-5">
                    <input type="text" name="datetimeReceived" size="24" value="${nowTimestamp}" class="form-control"/>
                <#-- <a href="#" onclick="setNow("datetimeReceived")" class="buttontext">[Now]</a> -->
                </div>
            </div>


            <div class="form-group">

                <label class="col-md-4 control-label">lotId</label>

                <div class="col-md-5">
                    <input type="text" name="lotId" size="10" class="form-control"/>
                </div>
            </div>

        <#-- facility location(s) -->
            <#assign facilityLocations = (product.getRelatedByAnd("ProductFacilityLocation", Static["org.ofbiz.base.util.UtilMisc"].toMap("facilityId", facilityId)))?if_exists/>
            <div class="form-group">

                <label class="col-md-4 control-label">${uiLabelMap.ProductFacilityLocation}</label>

                <div class="col-md-5">
                    <#if facilityLocations?has_content>
                        <select name="locationSeqId" class="form-control">
                            <#list facilityLocations as productFacilityLocation>
                                <#assign facility = productFacilityLocation.getRelatedOneCache("Facility")/>
                                <#assign facilityLocation = productFacilityLocation.getRelatedOne("FacilityLocation")?if_exists/>
                                <#assign facilityLocationTypeEnum = (facilityLocation.getRelatedOneCache("TypeEnumeration"))?if_exists/>
                                <option value="${productFacilityLocation.locationSeqId}"><#if facilityLocation?exists>${facilityLocation.areaId?if_exists}
                                    :${facilityLocation.aisleId?if_exists}
                                    :${facilityLocation.sectionId?if_exists}:${facilityLocation.levelId?if_exists}
                                    :${facilityLocation.positionId?if_exists}</#if><#if facilityLocationTypeEnum?exists>(${facilityLocationTypeEnum.get("description",locale)})</#if>
                                    [${productFacilityLocation.locationSeqId}]
                                </option>
                            </#list>
                            <option value="">${uiLabelMap.ProductNoLocation}</option>
                        </select>
                    <#else>
                        <#if parameters.facilityId?exists>
                            <#assign LookupFacilityLocationView="LookupFacilityLocation?facilityId=${facilityId}">
                        <#else>
                            <#assign LookupFacilityLocationView="LookupFacilityLocation">
                        </#if>
                        <@htmlTemplate.lookupField formName="selectAllForm" name="locationSeqId" id="locationSeqId" fieldFormName="${LookupFacilityLocationView}"/>
                    </#if>
                </div>
            </div>
            <div class="form-group">

                <label class="col-md-4 control-label">${uiLabelMap.ProductRejectedReason}</label>

                <div class="col-md-5">
                    <select name="rejectionId" class="form-control">
                        <option></option>
                        <#list rejectReasons as nextRejection>
                            <option value="${nextRejection.rejectionId}">${nextRejection.get("description",locale)?default(nextRejection.rejectionId)}</option>
                        </#list>
                    </select>
                </div>
            </div>
            <div class="form-group">

                <label class="col-md-4 control-label">${uiLabelMap.ProductQuantityRejected}</label>

                <div class="col-md-5">
                    <input type="text" name="quantityRejected" size="5" value="0" class="form-control"/>
                </div>
            </div>
            <div class="form-group">

                <label class="col-md-4 control-label">${uiLabelMap.ProductQuantityAccepted}</label>

                <div class="col-md-5">
                    <input type="text" name="quantityAccepted" size="5" value="${defaultQuantity?default(1)?string.number}" class="form-control"/>
                </div>
            </div>
            <div class="form-group">

                <label class="col-md-4 control-label">${uiLabelMap.ProductPerUnitPrice}</label>

                <div class="col-md-5">
                <#-- get the default unit cost -->
                    <#if (!unitCost?exists || unitCost == 0.0)><#assign unitCost = standardCosts.get(product.productId)?default(0)/></#if>
                    <input type="text" name="unitCost" size="10" value="${unitCost}" class="form-control"/>
                </div>
            </div>
            <div class="form-group">
                <div class="col-md-4">&nbsp;</div>
                <div class="col-md-5 pull-right"><input type="submit" value="${uiLabelMap.CommonReceive}" class="btn btn-primary btn-sm"/></div>
            </div>
        </table>
    </div>
    <script language="JavaScript" type="text/javascript">
        document.selectAllForm.quantityAccepted.focus();
    </script>
</form>

<#-- Select Shipment Screen -->
<#elseif requestParameters.initialSelected?exists && !requestParameters.shipmentId?exists>
<h4>${uiLabelMap.ProductSelectShipmentReceive}</h4>
<form method="post" action="<@ofbizUrl>ReceiveInventory</@ofbizUrl>" name="selectAllForm">
<#-- general request fields -->
    <input type="hidden" name="facilityId" value="${requestParameters.facilityId?if_exists}"/>
    <input type="hidden" name="purchaseOrderId" value="${requestParameters.purchaseOrderId?if_exists}"/>
    <input type="hidden" name="initialSelected" value="Y"/>
    <input type="hidden" name="partialReceive" value="${partialReceive?if_exists}"/>
    <input type="hidden" name="uomId" value="">

    <div class="table-responsive">
        <table class="table table-striped table-bordered">
            <#list shipments?if_exists as shipment>
                <#assign originFacility = shipment.getRelatedOneCache("OriginFacility")?if_exists/>
                <#assign destinationFacility = shipment.getRelatedOneCache("DestinationFacility")?if_exists/>
                <#assign statusItem = shipment.getRelatedOneCache("StatusItem")/>
                <#assign shipmentType = shipment.getRelatedOneCache("ShipmentType")/>
                <#assign shipmentDate = shipment.estimatedArrivalDate?if_exists/>
                <tr>
                    <td>
                        &nbsp;
                    </td>
                </tr>
                <tr>
                    <td>
                        <div class="table-responsive">
                            <table class="table table-striped table-bordered">
                                <tr>
                                    <td width="5%" nowrap="nowrap"><input type="radio" name="shipmentId" value="${shipment.shipmentId}"/></td>
                                    <td width="5%" nowrap="nowrap">${shipment.shipmentId}</td>
                                    <td>${shipmentType.get("description",locale)?default(shipmentType.shipmentTypeId?default(""))}</td>
                                    <td>${statusItem.get("description",locale)?default(statusItem.statusId?default("N/A"))}</td>
                                    <td>${(originFacility.facilityName)?if_exists} [${shipment.originFacilityId?if_exists}]</td>
                                    <td>${(destinationFacility.facilityName)?if_exists} [${shipment.destinationFacilityId?if_exists}]</td>
                                    <td style="white-space: nowrap;">${(shipment.estimatedArrivalDate.toString())?if_exists}</td>
                                </tr>
                            </table>
                        </div>
                    </td>
                </tr>
            </#list>
            <tr>
                <td>
                    &nbsp;
                </td>
            </tr>
            <tr>
                <td>
                    <div class="table-responsive">
                        <table class="table table-striped table-bordered">
                            <tr>
                                <td width="5%" nowrap="nowrap"><input type="radio" name="shipmentId" value="_NA_"/></td>
                                <td width="5%" nowrap="nowrap">${uiLabelMap.ProductNoSpecificShipment}</td>
                                <td colspan="5"></td>
                            </tr>
                        </table>
                    </div>
                </td>
            </tr>
            <tr>
                <td>&nbsp;<a href="javascript:document.selectAllForm.submit();" class="btn btn-primary btn-sm">${uiLabelMap.ProductReceiveSelectedShipment}</a></td>
            </tr>
        </table>
    </div>
</form>

<#-- Multi-Item PO Receiving -->
<#elseif requestParameters.initialSelected?exists && purchaseOrder?has_content>
<input type="hidden" id="getConvertedPrice" value="<@ofbizUrl secure="${request.isSecure()?string}">getConvertedPrice"</@ofbizUrl> />
<input type="hidden" id="alertMessage" value="${uiLabelMap.ProductChangePerUnitPrice}"/>
<form method="post" action="<@ofbizUrl>receiveInventoryProduct</@ofbizUrl>" name="selectAllForm">
<#-- general request fields -->
    <input type="hidden" name="facilityId" value="${requestParameters.facilityId?if_exists}"/>
    <input type="hidden" name="purchaseOrderId" value="${requestParameters.purchaseOrderId?if_exists}"/>
    <input type="hidden" name="initialSelected" value="Y"/>
    <#if shipment?has_content>
        <input type="hidden" name="shipmentIdReceived" value="${shipment.shipmentId}"/>
    </#if>
    <input type="hidden" name="_useRowSubmit" value="Y"/>
    <#assign rowCount = 0/>
    <div class="table-responsive">
        <table class="table table-striped table-bordered">
            <#if !purchaseOrderItems?exists || purchaseOrderItems.size() == 0>
                <tr>
                    <td colspan="2">${uiLabelMap.ProductNoItemsPoReceive}.</td>
                </tr>
            <#else>
                <tr>
                    <td>
                        <h4>${uiLabelMap.ProductReceivePurchaseOrder} #${purchaseOrder.orderId}</h4>
                        <#if shipment?has_content>
                            <h4>${uiLabelMap.ProductShipmentId} #${shipment.shipmentId}</h4>
                            <span>Set Shipment As Received</span>&nbsp;
                            <input type="checkbox" name="forceShipmentReceived" value="Y"/>
                        </#if>
                    </td>
                    <td align="right">
                    ${uiLabelMap.CommonSelectAll}
                        <input type="checkbox" name="selectAll" value="Y" onclick="toggleAll(this, 'selectAllForm');"/>
                    </td>
                </tr>
                <#list purchaseOrderItems as orderItem>
                    <#assign defaultQuantity = orderItem.quantity - receivedQuantities[orderItem.orderItemSeqId]?double/>
                    <#assign itemCost = orderItem.unitPrice?default(0)/>
                    <#assign salesOrderItem = salesOrderItems[orderItem.orderItemSeqId]?if_exists/>
                    <#if shipment?has_content>
                        <#if shippedQuantities[orderItem.orderItemSeqId]?exists>
                            <#assign defaultQuantity = shippedQuantities[orderItem.orderItemSeqId]?double - receivedQuantities[orderItem.orderItemSeqId]?double/>
                        <#else>
                            <#assign defaultQuantity = 0/>
                        </#if>
                    </#if>
                    <#if 0 < defaultQuantity>
                        <#assign orderItemType = orderItem.getRelatedOne("OrderItemType")/>
                        <input type="hidden" name="orderId_o_${rowCount}" value="${orderItem.orderId}"/>
                        <input type="hidden" name="orderItemSeqId_o_${rowCount}" value="${orderItem.orderItemSeqId}"/>
                        <input type="hidden" name="facilityId_o_${rowCount}" value="${requestParameters.facilityId?if_exists}"/>
                        <input type="hidden" name="datetimeReceived_o_${rowCount}" value="${nowTimestamp}"/>
                        <#if shipment?exists && shipment.shipmentId?has_content>
                            <input type="hidden" name="shipmentId_o_${rowCount}" value="${shipment.shipmentId}"/>
                        </#if>
                        <#if salesOrderItem?has_content>
                            <input type="hidden" name="priorityOrderId_o_${rowCount}" value="${salesOrderItem.orderId}"/>
                            <input type="hidden" name="priorityOrderItemSeqId_o_${rowCount}" value="${salesOrderItem.orderItemSeqId}"/>
                        </#if>

                        <tr>
                            <td colspan="2">
                                &nbsp;
                            </td>
                        </tr>
                        <tr>
                            <td>
                                <div class="table-responsive">
                                    <table class="table table-striped table-bordered">
                                        <tr>
                                            <#if orderItem.productId?exists>
                                                <#assign product = orderItem.getRelatedOneCache("Product")/>
                                                <input type="hidden" name="productId_o_${rowCount}" value="${product.productId}"/>
                                                <td>
                                                ${orderItem.orderItemSeqId}:&nbsp;
                                                    <a href="/catalog/control/EditProduct?productId=${product.productId}${externalKeyParam?if_exists}"
                                                                                     target="catalog"
                                                                                     class="buttontext">${product.productId}&nbsp;-&nbsp;${orderItem.itemDescription?if_exists}</a>
                                                    : ${product.description?if_exists}
                                                </td>
                                            <#else>
                                                <td>
                                                    <b>${orderItemType.get("description",locale)}</b> : ${orderItem.itemDescription?if_exists}&nbsp;&nbsp;
                                                    <input type="text" name="productId_o_${rowCount}" class="form-control"/>
                                                    <a href="/catalog/control/EditProduct?externalLoginKey=${externalLoginKey}" target="catalog"
                                                       class="buttontext">${uiLabelMap.ProductCreateProduct}</a>
                                                </td>
                                            </#if>
                                            <td width="10%">${uiLabelMap.ProductLocation}:</td>
                                        <#-- location(s) -->
                                            <td width="25%">
                                                <#assign facilityLocations = (orderItem.getRelatedByAnd("ProductFacilityLocation", Static["org.ofbiz.base.util.UtilMisc"].toMap("facilityId", facilityId)))?if_exists/>
                                                <#if facilityLocations?has_content>
                                                    <select name="locationSeqId_o_${rowCount}"  class="form-control">
                                                        <#list facilityLocations as productFacilityLocation>
                                                            <#assign facility = productFacilityLocation.getRelatedOneCache("Facility")/>
                                                            <#assign facilityLocation = productFacilityLocation.getRelatedOne("FacilityLocation")?if_exists/>
                                                            <#assign facilityLocationTypeEnum = (facilityLocation.getRelatedOneCache("TypeEnumeration"))?if_exists/>
                                                            <option value="${productFacilityLocation.locationSeqId}"><#if facilityLocation?exists>${facilityLocation.areaId?if_exists}
                                                                :${facilityLocation.aisleId?if_exists}:${facilityLocation.sectionId?if_exists}:${facilityLocation.levelId?if_exists}
                                                                :${facilityLocation.positionId?if_exists}</#if><#if facilityLocationTypeEnum?exists>
                                                                (${facilityLocationTypeEnum.get("description",locale)})</#if>[${productFacilityLocation.locationSeqId}]
                                                            </option>
                                                        </#list>
                                                        <option value="">${uiLabelMap.ProductNoLocation}</option>
                                                    </select>
                                                <#else>
                                                    <#if parameters.facilityId?exists>
                                                        <#assign LookupFacilityLocationView="LookupFacilityLocation?facilityId=${facilityId}">
                                                    <#else>
                                                        <#assign LookupFacilityLocationView="LookupFacilityLocation">
                                                    </#if>
                                                    <@htmlTemplate.lookupField formName="selectAllForm" name="locationSeqId_o_${rowCount}" id="locationSeqId_o_${rowCount}" size="10" fieldFormName="${LookupFacilityLocationView}"/>
                                                </#if>
                                            </td>
                                            <td width="15%">${uiLabelMap.ProductQtyReceived} :</td>
                                            <td width="10%">
                                            <input type="text" class="form-control" name="quantityAccepted_o_${rowCount}" size="6" value=<#if partialReceive?exists>"0"<#else>"${defaultQuantity?string.number}
                                                "</#if>/>
                                            </td>
                                        </tr>
                                        <tr>
                                            <td>
                                            ${uiLabelMap.ProductInventoryItemType} :&nbsp;
                                                <select name="inventoryItemTypeId_o_${rowCount}"  class="form-control">
                                                    <#list inventoryItemTypes as nextInventoryItemType>
                                                        <option value="${nextInventoryItemType.inventoryItemTypeId}"
                                                            <#if (facility.defaultInventoryItemTypeId?has_content) && (nextInventoryItemType.inventoryItemTypeId == facility.defaultInventoryItemTypeId)>
                                                                selected="selected"
                                                            </#if>
                                                                >${nextInventoryItemType.get("description",locale)?default(nextInventoryItemType.inventoryItemTypeId)}</option>
                                                    </#list>
                                                </select>
                                            </td>
                                            <td align="right">${uiLabelMap.ProductRejectionReason} :</td>
                                            <td align="right">
                                                <select name="rejectionId_o_${rowCount}" class="form-control">
                                                    <option></option>
                                                    <#list rejectReasons as nextRejection>
                                                        <option value="${nextRejection.rejectionId}">${nextRejection.get("description",locale)?default(nextRejection.rejectionId)}</option>
                                                    </#list>
                                                </select>
                                            </td>
                                            <td align="right">${uiLabelMap.ProductQtyRejected} :</td>
                                            <td align="right">
                                                <input type="text" name="quantityRejected_o_${rowCount}" value="0" size="6" class="form-control"/>
                                            </td>
                                        <tr>
                                            <td>&nbsp;</td>
                                            <#if !product.lotIdFilledIn?has_content || product.lotIdFilledIn != "Forbidden">
                                                <td align="right">${uiLabelMap.ProductLotId}</td>
                                                <td align="right">
                                                    <input type="text" name="lotId_o_${rowCount}" class="form-control" />
                                                </td>
                                            <#else>
                                                <td align="right">&nbsp;</td>
                                                <td align="right">&nbsp;</td>
                                            </#if>
                                            <td align="right">${uiLabelMap.OrderQtyOrdered} :</td>
                                            <td align="right">
                                                <input type="text" class="inputBox" class="form-control" name="quantityOrdered" value="${orderItem.quantity}" size="6" maxlength="20" disabled="disabled"/>
                                            </td>
                                        </tr>
                                        </tr>
                                        <tr>
                                            <td>&nbsp;</td>
                                            <td align="right">${uiLabelMap.ProductFacilityOwner}:</td>
                                            <td align="right"><input type="text" name="ownerPartyId_o_${rowCount}" size="20" maxlength="20" value="${facility.ownerPartyId}"/></td>
                                            <#if currencyUomId?default('') != orderCurrencyUomId?default('')>
                                                <td>${uiLabelMap.ProductPerUnitPriceOrder}:</td>
                                                <td>
                                                    <input type="hidden" name="orderCurrencyUomId_o_${rowCount}" value="${orderCurrencyUomId?if_exists}"/>
                                                    <input type="text" id="orderCurrencyUnitPrice_${rowCount}" name="orderCurrencyUnitPrice_o_${rowCount}" class="form-control"
                                                           value="${orderCurrencyUnitPriceMap[orderItem.orderItemSeqId]}"
                                                           onchange="getConvertedPrice(orderCurrencyUnitPrice_${rowCount}, '${orderCurrencyUomId}', '${currencyUomId}', '${rowCount}', '${orderCurrencyUnitPriceMap[orderItem.orderItemSeqId]}', '${itemCost}');"
                                                           size="6" maxlength="20"/>
                                                ${orderCurrencyUomId?if_exists}
                                                </td>
                                                <td>${uiLabelMap.ProductPerUnitPriceFacility}:</td>
                                                <td>
                                                    <input type="hidden" name="currencyUomId_o_${rowCount}" value="${currencyUomId?if_exists}"/>
                                                    <input type="text" id="unitCost_${rowCount}" name="unitCost_o_${rowCount}" value="${itemCost}" readonly="readonly" size="6"
                                                           maxlength="20" class="form-control"/>
                                                ${currencyUomId?if_exists}
                                                </td>
                                            <#else>
                                                <td align="right">${uiLabelMap.ProductPerUnitPrice}:</td>
                                                <td align="right">
                                                    <input type="hidden" name="currencyUomId_o_${rowCount}" value="${currencyUomId?if_exists}"/>
                                                    <input type="text" name="unitCost_o_${rowCount}" value="${itemCost}" size="6" maxlength="20" class="form-control"/>
                                                ${currencyUomId?if_exists}
                                                </td>
                                            </#if>
                                        </tr>
                                    </table>
                                </div>
                            </td>
                            <td align="right">
                                <input type="checkbox" name="_rowSubmit_o_${rowCount}" value="Y" onclick="checkToggle(this, 'selectAllForm');"/>
                            </td>
                        </tr>
                        <#assign rowCount = rowCount + 1>
                    </#if>
                </#list>
                <tr>
                    <td colspan="2">
                        &nbsp;
                    </td>
                </tr>
                <#if rowCount == 0>
                    <tr>
                        <td colspan="2">${uiLabelMap.ProductNoItemsPo} #${purchaseOrder.orderId} ${uiLabelMap.ProductToReceive}.</td>
                    </tr>
                    <tr>
                        <td colspan="2" align="right">
                            <a href="<@ofbizUrl>ReceiveInventory?facilityId=${requestParameters.facilityId?if_exists}</@ofbizUrl>"
                               class="buttontext">${uiLabelMap.ProductReturnToReceiving}</a>
                        </td>
                    </tr>
                <#else>
                    <tr>
                        <td colspan="2" align="right">
                            <a href="javascript:document.selectAllForm.submit();" class="buttontext">${uiLabelMap.ProductReceiveSelectedProduct}</a>
                        </td>
                    </tr>
                </#if>
            </#if>
        </table>
    </div>
    <input type="hidden" name="_rowCount" value="${rowCount}"/>
</form>
<script language="JavaScript" type="text/javascript">selectAll('selectAllForm');</script>

<#-- Initial Screen -->
<#else>
<h4>${uiLabelMap.ProductReceiveItem}</h4>
<form name="selectAllForm" method="post" action="<@ofbizUrl>ReceiveInventory</@ofbizUrl>" class="form-horizontal">
    <input type="hidden" name="facilityId" value="${requestParameters.facilityId?if_exists}"/>
    <input type="hidden" name="initialSelected" value="Y"/>

    <div class="form-group">
        <label class="col-md-4 control-label">${uiLabelMap.ProductPurchaseOrderNumber}</label>

        <div class="col-md-5">
            <@htmlTemplate.lookupField value="${requestParameters.purchaseOrderId?if_exists}" formName="selectAllForm" name="purchaseOrderId" id="purchaseOrderId" fieldFormName="LookupPurchaseOrderHeaderAndShipInfo"/>
            <span class="tooltip">${uiLabelMap.ProductLeaveSingleProductReceiving}</span>
        </div>
    </div>
    <div class="form-group">
        <label class="col-md-4 control-label">${uiLabelMap.ProductProductId}</label>

        <div class="col-md-5">
            <@htmlTemplate.lookupField value="${requestParameters.productId?if_exists}" formName="selectAllForm" name="productId" id="productId" fieldFormName="LookupProduct"/>
            <span class="tooltip">${uiLabelMap.ProductLeaveEntirePoReceiving}</span>
        </div>
    </div>
    <div class="form-group">
        <div class="col-md-4">&nbsp;</div>
        <div class="col-md-5 pull-right">
            <a href="javascript:document.selectAllForm.submit();" class="btn btn-primary btn-sm">${uiLabelMap.ProductReceiveProduct}</a>
        </div>

</form>
</#if>
<@htmlScreenTemplate.renderScreenletEnd/>
