<script language="JavaScript" type="text/javascript">
    function clearLine(facilityId, orderId, orderItemSeqId, productId, shipGroupSeqId, inventoryItemId, packageSeqId) {
        document.clearPackLineForm.facilityId.value = facilityId;
        document.clearPackLineForm.orderId.value = orderId;
        document.clearPackLineForm.orderItemSeqId.value = orderItemSeqId;
        document.clearPackLineForm.productId.value = productId;
        document.clearPackLineForm.shipGroupSeqId.value = shipGroupSeqId;
        document.clearPackLineForm.inventoryItemId.value = inventoryItemId;
        document.clearPackLineForm.packageSeqId.value = packageSeqId;
        document.clearPackLineForm.submit();
    }
</script>

<#if security.hasEntityPermission("FACILITY", "_VIEW", session)>
    <#assign showInput = requestParameters.showInput?default("Y")>
    <#assign hideGrid = requestParameters.hideGrid?default("N")>

    <#if (requestParameters.forceComplete?has_content && !invoiceIds?has_content)>
        <#assign forceComplete = "true">
        <#assign showInput = "Y">
    </#if>

    <@htmlScreenTemplate.renderScreenletBegin id="" title="${uiLabelMap.ProductPackOrder}&nbsp;in&nbsp;${facility.facilityName?if_exists} [${facilityId?if_exists}]"/>
    <#if invoiceIds?has_content>
    <div class="button-bar">
    ${uiLabelMap.CommonView}
        <a href="<@ofbizUrl>/PackingSlip.pdf?shipmentId=${shipmentId}</@ofbizUrl>" target="_blank"
           class="btn btn-primary btn-sm">${uiLabelMap.ProductPackingSlip}</a> ${uiLabelMap.CommonOr}
    ${uiLabelMap.CommonView}
        <a href="<@ofbizUrl>/ShipmentBarCode.pdf?shipmentId=${shipmentId}</@ofbizUrl>" target="_blank"
           class="btn btn-primary btn-sm">${uiLabelMap.ProductBarcode}</a>  ${uiLabelMap.ProductShipmentId}
        <#assign shipment = delegator.findByPrimaryKey("Shipment",{"shipmentId":shipmentId})>
        <a href="<@ofbizUrl>/ViewShipment?shipmentId=${shipmentId}</@ofbizUrl>" class="btn btn-primary btn-sm">${shipmentId}</a>
    ${uiLabelMap.CommonView} 订单
        <a href="/ordermgr/control/orderview?orderId=${shipment.primaryOrderId}"
           class="btn btn-primary btn-sm">${shipment.primaryOrderId}</a>
    </div>
        <#if invoiceIds?exists && invoiceIds?has_content>
        <div>
            <p>${uiLabelMap.AccountingInvoices}:</p>
            <ul>
                <#list invoiceIds as invoiceId>
                    <li>
                    ${uiLabelMap.CommonNbr}<a href="/accounting/control/invoiceOverview?invoiceId=${invoiceId}&amp;externalLoginKey=${externalLoginKey}" target="_blank"
                                              class="buttontext">${invoiceId}</a>
                        (<a href="/accounting/control/invoice.pdf?invoiceId=${invoiceId}&amp;externalLoginKey=${externalLoginKey}" target="_blank" class="buttontext">PDF</a>)
                    </li>
                </#list>
            </ul>
        </div>
        </#if>
    </#if>
<br/>

<!-- select order form -->
<form name="selectOrderForm" method="post" action="<@ofbizUrl>PackOrder</@ofbizUrl>" class="form-inline">
    <input type="hidden" name="facilityId" value="${facilityId?if_exists}"/>

    <div class="form-group">
        <div class="input-group m-b-10"><span class="input-group-addon">${uiLabelMap.ProductOrderId}</span>
            <input type="text" name="orderId" size="20" maxlength="20" value="${orderId?if_exists}" class="form-control"/>
            <span class="input-group-addon">/</span>
            <input type="text" name="shipGroupSeqId" size="6" maxlength="6" value="${shipGroupSeqId?default("00001")}" class="form-control"/>
        </div>
        <input type="hidden" name="hideGrid" value=""/>
    <#-- <div class="input-group m-b-10">
         <span class="input-group-addon">${uiLabelMap.ProductHideGrid}</span>

         <div class="input-group-addon"><input type="checkbox" class="checkbox-inline" name="hideGrid" value="Y" <#if (hideGrid == "Y")>checked=""</#if> /></div>
     </div>-->

        <input type="image" src="<@ofbizContentUrl>/images/spacer.gif</@ofbizContentUrl>" onclick="document.selectOrderForm.submit();"/>
        <a href="javascript:document.selectOrderForm.submit();" class="btn btn-primary btn-sm m-b-10">${uiLabelMap.ProductPackOrder}</a>
    <#--<a href="javascript:document.selectOrderForm.action='<@ofbizUrl>WeightPackageOnly</@ofbizUrl>';document.selectOrderForm.submit();" class="btn btn-primary btn-sm m-b-10">
    ${uiLabelMap.ProductWeighPackageOnly}</a>-->

    </div>
</form>
<br/>

<!-- select picklist bin form -->
<#--<form name="selectPicklistBinForm" method="post" action="<@ofbizUrl>PackOrder</@ofbizUrl>" class="form-inline">
    <input type="hidden" name="facilityId" value="${facilityId?if_exists}"/>

    <div class="form-gourp">
        <div class="input-group"><span class="input-group-addon">${uiLabelMap.FormFieldTitle_picklistBinId}</span>
            <input type="text" name="picklistBinId" size="29" maxlength="60" value="${picklistBinId?if_exists}" class="form-control"/>
        </div>

        <div class="input-group"><span class="input-group-addon">${uiLabelMap.ProductHideGrid}</span>
            <input type="checkbox" name="hideGrid" class="checkbox-inline" value="Y" <#if (hideGrid == "Y")>checked=""</#if> class="form-control"/>
        </div>

        <input type="image" src="<@ofbizContentUrl>/images/spacer.gif</@ofbizContentUrl>" onclick="document.selectPicklistBinForm.submit();"/>
        <a href="javascript:document.selectPicklistBinForm.submit();" class="btn btn-primary btn-sm">${uiLabelMap.ProductPackOrder}</a>
        <a href="javascript:document.selectPicklistBinForm.action='<@ofbizUrl>WeightPackageOnly</@ofbizUrl>';document.selectPicklistBinForm.submit();"
           class="btn btn-primary btn-sm">${uiLabelMap.ProductWeighPackageOnly}</a>
    </div>
</form>-->
<form name="clearPackForm" method="post" action="<@ofbizUrl>ClearPackAll</@ofbizUrl>">
    <input type="hidden" name="orderId" value="${orderId?if_exists}"/>
    <input type="hidden" name="shipGroupSeqId" value="${shipGroupSeqId?if_exists}"/>
    <input type="hidden" name="facilityId" value="${facilityId?if_exists}"/>
</form>
<form name="incPkgSeq" method="post" action="<@ofbizUrl>SetNextPackageSeq</@ofbizUrl>">
    <input type="hidden" name="orderId" value="${orderId?if_exists}"/>
    <input type="hidden" name="shipGroupSeqId" value="${shipGroupSeqId?if_exists}"/>
    <input type="hidden" name="facilityId" value="${facilityId?if_exists}"/>
</form>
<form name="clearPackLineForm" method="post" action="<@ofbizUrl>ClearPackLine</@ofbizUrl>">
    <input type="hidden" name="facilityId"/>
    <input type="hidden" name="orderId"/>
    <input type="hidden" name="orderItemSeqId"/>
    <input type="hidden" name="productId"/>
    <input type="hidden" name="shipGroupSeqId"/>
    <input type="hidden" name="inventoryItemId"/>
    <input type="hidden" name="packageSeqId"/>
</form>
    <@htmlScreenTemplate.renderScreenletEnd/>
    <#if showInput != "N" && ((orderHeader?exists && orderHeader?has_content))>
        <@htmlScreenTemplate.renderScreenletBegin id="" title="${uiLabelMap.ProductOrderId} ${uiLabelMap.CommonNbr}"/>
    <a href="/ordermgr/control/orderview?orderId=${orderId}">${orderId}</a>/ ${uiLabelMap.ProductOrderShipGroupId} #${shipGroupSeqId}
        <#if orderItemShipGroup?has_content>
            <#assign postalAddress = orderItemShipGroup.getRelatedOne("PostalAddress")>
            <#assign carrier = orderItemShipGroup.carrierPartyId?default("N/A")>
        <div class="table-responsive">
            <table class="table table-striped table-bordered">
                <tr>
                    <td valign="top">
                        <h4 class="text-info">${uiLabelMap.ProductShipToAddress}</h4>

                         ${uiLabelMap.CommonTo}: ${postalAddress.toName?default("")}
                        <br/>
                        <#if postalAddress.attnName?has_content>
                        ${uiLabelMap.CommonAttn}: ${postalAddress.attnName}
                            <br/>
                        </#if>
                    ${Static["org.ofbiz.common.geo.GeoWorker"].getGeoNameById(postalAddress.stateProvinceGeoId?if_exists,delegator)}${Static["org.ofbiz.common.geo.GeoWorker"].getGeoNameById(postalAddress.city?if_exists,delegator)}${Static["org.ofbiz.common.geo.GeoWorker"].getGeoNameById(postalAddress.countyGeoId?if_exists,delegator)} ${postalAddress.postalCode?if_exists}
                    ${postalAddress.address1}
                        <br/>
                        <#if postalAddress.address2?has_content>
                        ${postalAddress.address2}
                            <br/>
                        </#if>
                    </td>

                    <td valign="top">
                        <h4 class="text-info">${uiLabelMap.ProductCarrierShipmentMethod}</h4>

                        <#if carrier == "USPS">
                            <#assign color = "red">
                        <#elseif carrier == "UPS">
                            <#assign color = "green">
                        <#else>
                            <#assign color = "black">
                        </#if>
                        <#if carrier != "_NA_">
                            <font color="${color}">${carrier}</font>
                        </#if>
                        ${orderItemShipGroup.getRelatedOne("ShipmentMethodType").description}
                        <br/>
                        <h4 class="text-info">${uiLabelMap.ProductEstimatedShipCostForShipGroup}</h4>

                        <#if shipmentCostEstimateForShipGroup?exists>
                            <@ofbizCurrency amount=shipmentCostEstimateForShipGroup isoCode=orderReadHelper.getCurrency()?if_exists/>
                            <br/>
                        </#if>
                    </td>

                    <td valign="top">
                        <h4 class="text-info">${uiLabelMap.OrderInstructions}</h4>
                    ${orderItemShipGroup.shippingInstructions?default("(${uiLabelMap.CommonNone})")}
                    </td>
                </tr>
            </table>
        </div>
        </#if>

    <!-- manual per item form -->
        <#if showInput != "N">
        <hr/>
        <form name="singlePackForm" method="post" action="<@ofbizUrl>ProcessPackOrder</@ofbizUrl>" class="form-inline">
            <input type="hidden" name="packageSeq" value="${packingSession.getCurrentPackageSeq()}"/>
            <input type="hidden" name="orderId" value="${orderId}"/>
            <input type="hidden" name="shipGroupSeqId" value="${shipGroupSeqId}"/>
            <input type="hidden" name="facilityId" value="${facilityId?if_exists}"/>
            <input type="hidden" name="hideGrid" value="${hideGrid}"/>

            <div class="from-group">
                <div class="input-group">
                    <span class="input-group-addon">${uiLabelMap.ProductProductNumber}</span>
                    <input type="text" name="productId" size="20" maxlength="20" value="" class="form-control"/>
                    <span class="input-group-addon">@</span>
                    <input type="text" name="quantity" size="6" maxlength="6" value="1" class="form-control"/>
                </div>
                <a href="javascript:document.singlePackForm.submit();" class="btn btn-primary btn-sm">${uiLabelMap.ProductPackItem}</a>

                <div class="input-group"><span class="input-group-addon">${uiLabelMap.ProductCurrentPackageSequence}</span>
                    <span class="input-group-addon">${packingSession.getCurrentPackageSeq()}</span>
                </div>
                <input type="button" class="btn btn-primary btn-sm" value="${uiLabelMap.ProductNextPackage}" onclick="document.incPkgSeq.submit();"/>
            </div>
        </form>
        </#if>

    <!-- auto grid form -->
        <#assign itemInfos = packingSession.getItemInfos()?if_exists>
        <#if showInput != "N" && hideGrid != "Y" && itemInfos?has_content>
        <br/>

        <form name="multiPackForm" method="post" action="<@ofbizUrl>ProcessBulkPackOrder</@ofbizUrl>" class="form-inline">
            <input type="hidden" name="facilityId" value="${facilityId?if_exists}"/>
            <input type="hidden" name="orderId" value="${orderId?if_exists}"/>
            <input type="hidden" name="shipGroupSeqId" value="${shipGroupSeqId?if_exists}"/>
            <input type="hidden" name="originFacilityId" value="${facilityId?if_exists}"/>
            <input type="hidden" name="hideGrid" value="${hideGrid}"/>

            <div class="table-responsive">
                <table class="table table-striped table-bordered">
                    <tr class="header-row">
                        <td>&nbsp;</td>
                        <td>${uiLabelMap.ProductItem} ${uiLabelMap.CommonNbr}</td>
                        <td>${uiLabelMap.ProductProductId}</td>
                        <td>${uiLabelMap.ProductInternalName}</td>
                        <td align="right">${uiLabelMap.ProductOrderedQuantity}</td>
                        <td align="right">${uiLabelMap.ProductQuantityShipped}</td>
                        <td align="right">${uiLabelMap.ProductPackedQty}</td>
                        <td>&nbsp;</td>
                        <td align="center">${uiLabelMap.ProductPackQty}</td>
                        <td align="center">${uiLabelMap.ProductPackedWeight}&nbsp;(${("uiLabelMap.ProductShipmentUomAbbreviation_" + defaultWeightUomId)?eval})</td>
                        <#if carrierShipmentBoxTypes?has_content>
                            <td align="center">${uiLabelMap.ProductShipmentBoxType}</td>
                        </#if>
                        <td align="center">${uiLabelMap.ProductPackage}</td>
                        <td align="right">&nbsp;<b>*</b>&nbsp;${uiLabelMap.ProductPackages}</td>
                    </tr>

                    <#if (itemInfos?has_content)>
                        <#assign rowKey = 1>
                        <#list itemInfos as itemInfo>
                        <#-- <#list itemInfos as orderItem>  -->
                            <#assign orderItem = itemInfo.orderItem/>
                            <#assign shippedQuantity = orderReadHelper.getItemShippedQuantity(orderItem)?if_exists>
                            <#assign orderItemQuantity = itemInfo.quantity/>
                            <#assign orderProduct = orderItem.getRelatedOne("Product")?if_exists/>
                            <#assign product = Static["org.ofbiz.product.product.ProductWorker"].findProduct(delegator, itemInfo.productId)?if_exists/>
                        <#--
                        <#if orderItem.cancelQuantity?exists>
                          <#assign orderItemQuantity = orderItem.quantity - orderItem.cancelQuantity>
                        <#else>
                          <#assign orderItemQuantity = orderItem.quantity>
                        </#if>
                        -->
                            <#assign inputQty = orderItemQuantity - packingSession.getPackedQuantity(orderId, orderItem.orderItemSeqId, shipGroupSeqId, itemInfo.productId)>
                            <tr>
                                <td><input type="checkbox" name="sel_${rowKey}" value="Y" <#if (inputQty >0)>checked=""</#if>/></td>
                                <td>${orderItem.orderItemSeqId}</td>
                                <td>
                                ${orderProduct.productId?default("N/A")}
                                    <#if orderProduct.productId != product.productId>
                                        &nbsp;${product.productId?default("N/A")}
                                    </#if>
                                </td>
                                <td>
                                    <a href="/catalog/control/EditProduct?productId=${orderProduct.productId?if_exists}${externalKeyParam}" class="buttontext"
                                       target="_blank">${(orderProduct.internalName)?if_exists}</a>
                                    <#if orderProduct.productId != product.productId>
                                        &nbsp;[<a href="/catalog/control/EditProduct?productId=${product.productId?if_exists}${externalKeyParam}" class="buttontext"
                                                  target="_blank">${(product.internalName)?if_exists}</a>]
                                    </#if>
                                </td>
                                <td align="right">${orderItemQuantity}</td>
                                <td align="right">${shippedQuantity?default(0)}</td>
                                <td align="right">${packingSession.getPackedQuantity(orderId, orderItem.orderItemSeqId, shipGroupSeqId, itemInfo.productId)}</td>
                                <td>&nbsp;</td>
                                <td align="center">
                                    <input type="text" size="7" name="qty_${rowKey}" value="${inputQty}" class="form-control"/>
                                </td>
                                <td align="center">
                                    <input type="text" size="7" name="wgt_${rowKey}" value="" class="form-control"/>
                                </td>
                                <#if carrierShipmentBoxTypes?has_content>
                                    <td align="center">
                                        <select name="boxType_${rowKey}" class="form-control">
                                            <option value=""></option>
                                            <#list carrierShipmentBoxTypes as carrierShipmentBoxType>
                                                <#assign shipmentBoxType = carrierShipmentBoxType.getRelatedOne("ShipmentBoxType") />
                                                <option value="${shipmentBoxType.shipmentBoxTypeId}">${shipmentBoxType.description?default(shipmentBoxType.shipmentBoxTypeId)}</option>
                                            </#list>
                                        </select>
                                    </td>
                                </#if>
                                <td align="center">
                                    <select name="pkg_${rowKey}" class="form-control">
                                        <#if packingSession.getPackageSeqIds()?exists>
                                            <#list packingSession.getPackageSeqIds() as packageSeqId>
                                                <option value="${packageSeqId}">${uiLabelMap.ProductPackage} ${packageSeqId}</option>
                                            </#list>
                                            <#assign nextPackageSeqId = packingSession.getPackageSeqIds().size() + 1>
                                            <option value="${nextPackageSeqId}">${uiLabelMap.ProductNextPackage}</option>
                                        <#else>
                                            <option value="1">${uiLabelMap.ProductPackage} 1</option>
                                            <option value="2">${uiLabelMap.ProductPackage} 2</option>
                                            <option value="3">${uiLabelMap.ProductPackage} 3</option>
                                            <option value="4">${uiLabelMap.ProductPackage} 4</option>
                                            <option value="5">${uiLabelMap.ProductPackage} 5</option>
                                        </#if>
                                    </select>
                                </td>
                                <td align="right">
                                    <input type="text" size="7" name="numPackages_${rowKey}" value="1" class="form-control"/>
                                </td>
                                <input type="hidden" name="prd_${rowKey}" value="${itemInfo.productId?if_exists}"/>
                                <input type="hidden" name="ite_${rowKey}" value="${orderItem.orderItemSeqId}"/>
                            </tr>
                            <#assign rowKey = rowKey + 1>
                        </#list>
                    </#if>
                    <tr>
                        <td colspan="10">&nbsp;</td>
                    </tr>
                    <tr>
                        <td colspan="12" align="right">
                            <input type="submit" value="${uiLabelMap.ProductPackItem}"/>
                            &nbsp;
                            <input type="button" value="${uiLabelMap.CommonClear} (${uiLabelMap.CommonAll})" onclick="document.clearPackForm.submit();"/>
                        </td>
                    </tr>
                </table>
            </div>
        </form>
        <br/>
        </#if>

    <!-- complete form -->
        <#if showInput != "N">
        <form name="completePackForm" method="post" action="<@ofbizUrl>CompletePack</@ofbizUrl>" class="form-inline">
            <input type="hidden" name="orderId" value="${orderId?if_exists}"/>
            <input type="hidden" name="shipGroupSeqId" value="${shipGroupSeqId?if_exists}"/>
            <input type="hidden" name="facilityId" value="${facilityId?if_exists}"/>
            <input type="hidden" name="forceComplete" value="${forceComplete?default('false')}"/>
            <input type="hidden" name="weightUomId" value="${defaultWeightUomId}"/>
            <input type="hidden" name="showInput" value="N"/>
            <hr/>
            <div class="table-responsive">
                <table class="table table-striped table-bordered">
                    <tr>
                        <#assign packageSeqIds = packingSession.getPackageSeqIds()/>
                        <#if packageSeqIds?has_content>
                            <td>
                                <h4 class="text-info">${uiLabelMap.ProductPackedWeight} (${("uiLabelMap.ProductShipmentUomAbbreviation_" + defaultWeightUomId)?eval}):</h4>
                                <br/>
                                <#list packageSeqIds as packageSeqId>
                                ${uiLabelMap.ProductPackage} ${packageSeqId}
                                    <input type="text" size="7" name="packageWeight_${packageSeqId}" value="${packingSession.getPackageWeight(packageSeqId?int)?if_exists}"
                                           class="form-control"/>
                                    <br/>
                                </#list>
                                <#if orderItemShipGroup?has_content>
                                    <input type="hidden" name="shippingContactMechId" value="${orderItemShipGroup.contactMechId?if_exists}"/>
                                    <input type="hidden" name="shipmentMethodTypeId" value="${orderItemShipGroup.shipmentMethodTypeId?if_exists}"/>
                                    <input type="hidden" name="carrierPartyId" value="${orderItemShipGroup.carrierPartyId?if_exists}"/>
                                    <input type="hidden" name="carrierRoleTypeId" value="${orderItemShipGroup.carrierRoleTypeId?if_exists}"/>
                                    <input type="hidden" name="productStoreId" value="${productStoreId?if_exists}"/>
                                </#if>
                            </td>
                        </#if>
                        <td nowrap="nowrap">
                            <h4 class="text-info">${uiLabelMap.ProductAdditionalShippingCharge}:</h4>
                            <br/>
                            <input type="text" name="additionalShippingCharge" value="${packingSession.getAdditionalShippingCharge()?if_exists}" size="20" class="form-control"/>
                            <#if packageSeqIds?has_content>
                                <a href="javascript:document.completePackForm.action='<@ofbizUrl>calcPackSessionAdditionalShippingCharge</@ofbizUrl>';document.completePackForm.submit();"
                                   class="buttontext">${uiLabelMap.ProductEstimateShipCost}</a>
                                <br/>
                            </#if>
                        </td>
                        <td>
                            <h4 class="text-info">${uiLabelMap.ProductHandlingInstructions}:</h4>
                            <br/>
                            <textarea name="handlingInstructions" rows="2" cols="30">${packingSession.getHandlingInstructions()?if_exists}</textarea>
                        </td>
                        <td align="right">
                            <div>
                                <#assign buttonName = "${uiLabelMap.ProductComplete}">
                                <#if forceComplete?default("false") == "true">
                                    <#assign buttonName = "${uiLabelMap.ProductCompleteForce}">
                                </#if>
                                <input type="button" value="${buttonName}" onclick="document.completePackForm.submit();"/>
                            </div>
                        </td>
                    </tr>
                </table>
            </div>
            <br/>
        </form>
        </#if>

        <@htmlScreenTemplate.renderScreenletEnd/>

    <!-- display items in packages, per packed package and in order -->
        <#assign linesByPackageResultMap = packingSession.getPackingSessionLinesByPackage()?if_exists>
        <#assign packageMap = linesByPackageResultMap.get("packageMap")?if_exists>
        <#assign sortedKeys = linesByPackageResultMap.get("sortedKeys")?if_exists>
        <#if ((packageMap?has_content) && (sortedKeys?has_content))>
            <@htmlScreenTemplate.renderScreenletBegin id="" title="${uiLabelMap.ProductPackages} : ${sortedKeys.size()?if_exists}"/>


            <#list sortedKeys as key>
                <#assign packedLines = packageMap.get(key)>
                <#if packedLines?has_content>
                <br/>
                    <#assign packedLine = packedLines.get(0)?if_exists>
                <h4 class="text-info">${uiLabelMap.ProductPackage}&nbsp;${packedLine.getPackageSeq()?if_exists}</h4>
                <br/>
                <div class="table-responsive">
                    <table class="table table-striped table-bordered">
                        <tr class="header-row">
                            <th>${uiLabelMap.ProductItem} ${uiLabelMap.CommonNbr}</th>
                            <th>${uiLabelMap.ProductProductId}</th>
                            <th>${uiLabelMap.ProductProductDescription}</th>
                            <th>${uiLabelMap.ProductInventoryItem} ${uiLabelMap.CommonNbr}</th>
                            <th align="right">${uiLabelMap.ProductPackedQty}</th>
                            <th align="right">${uiLabelMap.ProductPackedWeight}&nbsp;(${("uiLabelMap.ProductShipmentUomAbbreviation_" + defaultWeightUomId)?eval}
                                )&nbsp;(${uiLabelMap.ProductPackage})
                            </th>
                            <th align="right">${uiLabelMap.ProductPackage} ${uiLabelMap.CommonNbr}</th>
                            <th>&nbsp;</th>
                        </tr>
                        <#list packedLines as line>
                            <#assign product = Static["org.ofbiz.product.product.ProductWorker"].findProduct(delegator, line.getProductId())/>
                            <tr>
                                <td>${line.getOrderItemSeqId()}</td>
                                <td>${line.getProductId()?default("N/A")}</td>
                                <td>
                                    <a href="/catalog/control/EditProduct?productId=${line.getProductId()?if_exists}${externalKeyParam}" class="buttontext"
                                       target="_blank">${product.internalName?if_exists?default("[N/A]")}</a>
                                </td>
                                <td>${line.getInventoryItemId()}</td>
                                <td align="right">${line.getQuantity()}</td>
                                <td align="right">${line.getWeight()} (${packingSession.getPackageWeight(line.getPackageSeq()?int)?if_exists})</td>
                                <td align="right">${line.getPackageSeq()}</td>
                                <td align="right"><a
                                        href="javascript:clearLine('${facilityId}', '${line.getOrderId()}', '${line.getOrderItemSeqId()}', '${line.getProductId()?default("")}', '${line.getShipGroupSeqId()}', '${line.getInventoryItemId()}', '${line.getPackageSeq()}')"
                                        class="buttontext">${uiLabelMap.CommonClear}</a></td>
                            </tr>
                        </#list>
                    </table>
                </div>
                </#if>
            </#list>
            <@htmlScreenTemplate.renderScreenletEnd/>
        </#if>

    <!-- packed items display -->
        <#assign packedLines = packingSession.getLines()?if_exists>
        <#if packedLines?has_content>
            <@htmlScreenTemplate.renderScreenletBegin id="" title="${uiLabelMap.ProductItems} (${uiLabelMap.ProductPackages}): ${packedLines.size()?if_exists}"/>


        <div class="table-responsive">
            <table class="table table-striped table-bordered">
                <tr class="header-row">
                    <th>${uiLabelMap.ProductItem} ${uiLabelMap.CommonNbr}</th>
                    <th>${uiLabelMap.ProductProductId}</th>
                    <th>${uiLabelMap.ProductProductDescription}</th>
                    <th>${uiLabelMap.ProductInventoryItem} ${uiLabelMap.CommonNbr}</th>
                    <th align="right">${uiLabelMap.ProductPackedQty}</th>
                    <th align="right">${uiLabelMap.ProductPackedWeight}&nbsp;(${("uiLabelMap.ProductShipmentUomAbbreviation_" + defaultWeightUomId)?eval}
                        )&nbsp;(${uiLabelMap.ProductPackage})
                    </th>
                    <th align="right">${uiLabelMap.ProductPackage} ${uiLabelMap.CommonNbr}</th>
                    <th>&nbsp;</th>
                </tr>
                <#list packedLines as line>
                    <#assign product = Static["org.ofbiz.product.product.ProductWorker"].findProduct(delegator, line.getProductId())/>
                    <tr>
                        <td>${line.getOrderItemSeqId()}</td>
                        <td>${line.getProductId()?default("N/A")}</td>
                        <td>
                            <a href="/catalog/control/EditProduct?productId=${line.getProductId()?if_exists}${externalKeyParam}" class="buttontext"
                               target="_blank">${product.internalName?if_exists?default("[N/A]")}</a>
                        </td>
                        <td>${line.getInventoryItemId()}</td>
                        <td align="right">${line.getQuantity()}</td>
                        <td align="right">${line.getWeight()} (${packingSession.getPackageWeight(line.getPackageSeq()?int)?if_exists})</td>
                        <td align="right">${line.getPackageSeq()}</td>
                        <td align="right"><a
                                href="javascript:clearLine('${facilityId}', '${line.getOrderId()}', '${line.getOrderItemSeqId()}', '${line.getProductId()?default("")}', '${line.getShipGroupSeqId()}', '${line.getInventoryItemId()}', '${line.getPackageSeq()}')"
                                class="buttontext">${uiLabelMap.CommonClear}</a></td>
                    </tr>
                </#list>
            </table>
        </div>
            <@htmlScreenTemplate.renderScreenletEnd/>
        </#if>
    </#if>

    <#if orderId?has_content>
    <script language="javascript" type="text/javascript">
        document.singlePackForm.productId.focus();
    </script>
    <#else>
    <script language="javascript" type="text/javascript">
        document.selectOrderForm.orderId.focus();
    </script>
    </#if>
<#else>
<h3>${uiLabelMap.ProductFacilityViewPermissionError}</h3>
</#if>
