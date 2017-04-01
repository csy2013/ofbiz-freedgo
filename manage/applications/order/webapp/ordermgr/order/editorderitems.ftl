<#if orderHeader?has_content>

<#-- price change rules -->
    <#assign allowPriceChange = false/>
    <#if (orderHeader.orderTypeId == 'PURCHASE_ORDER' || security.hasEntityPermission("ORDERMGR", "_SALES_PRICEMOD", session))>
        <#assign allowPriceChange = true/>
    </#if>

<#--<div class="panel panel-default">
    <div class="panel-body">-->
    <@htmlScreenTemplate.renderScreenletBegin id="editorderitems" title=""/>
        <#if security.hasEntityPermission("ORDERMGR", "_UPDATE", session)>
            <#if orderHeader?has_content && orderHeader.statusId != "ORDER_CANCELLED" && orderHeader.statusId != "ORDER_COMPLETED">
                <a href="javascript:document.updateItemInfo.action='<@ofbizUrl>cancelSelectedOrderItems</@ofbizUrl>';document.updateItemInfo.submit()"
                   class="btn btn-primary btn-sm">${uiLabelMap.OrderCancelSelectedItems}</a>
                <a href="javascript:document.updateItemInfo.action='<@ofbizUrl>cancelOrderItem</@ofbizUrl>';document.updateItemInfo.submit()"
                   class="btn btn-primary btn-sm">${uiLabelMap.OrderCancelAllItems}</a>
            <#--<a href="<@ofbizUrl>orderview?${paramString}</@ofbizUrl>" class="btn btn-primary btn-sm">${uiLabelMap.OrderViewOrder}</a>-->
            </#if>
            <br/><br/>
        </#if>
        <#if !orderItemList?has_content>
            <span class="alert">${uiLabelMap.checkhelper_sales_order_lines_lookup_failed}</span>
        <#else>
            <form name="updateItemInfo" method="post" action="<@ofbizUrl>updateOrderItems</@ofbizUrl>" class="form-inline">
                <input type="hidden" name="orderId" value="${orderId}"/>
                <input type="hidden" name="orderItemSeqId" value=""/>
                <input type="hidden" name="shipGroupSeqId" value=""/>
                <#if (orderHeader.orderTypeId == 'PURCHASE_ORDER')>
                    <input type="hidden" name="supplierPartyId" value="${partyId}"/>
                    <input type="hidden" name="orderTypeId" value="PURCHASE_ORDER"/>
                </#if>
                <div class="table-responsive">
                <#--<table class="basic-table order-items" cellspacing="0">-->
                    <table class="table" cellpadding="0">
                        <tr class="warning">
                            <th>${uiLabelMap.ProductProduct}</th>
                            <th>${uiLabelMap.CommonStatus}</th>
                            <th>${uiLabelMap.OrderQuantity}</th>
                            <th>${uiLabelMap.OrderUnitPrice}</th>
                            <th>${uiLabelMap.OrderAdjustments}</th>
                            <th>${uiLabelMap.OrderSubTotal}</th>
                            <th>&nbsp;</th>
                            <th>&nbsp;</th>
                        </tr>
                        <#list orderItemList as orderItem>
                            <#if orderItem.productId?exists> <#-- a null product may come from a quote -->
                                <#assign orderItemContentWrapper = Static["org.ofbiz.order.order.OrderContentWrapper"].makeOrderContentWrapper(orderItem, request)>
                                <tr class="info">
                                    <#assign orderItemType = orderItem.getRelatedOne("OrderItemType")?if_exists>
                                    <#assign productId = orderItem.productId?if_exists>
                                    <#if productId?exists && productId == "shoppingcart.CommentLine">
                                        <td colspan="8" valign="top" class="text-left">
                                            <span>&gt;&gt; ${orderItem.itemDescription}</span>
                                        </td>
                                    <#else>
                                        <td valign="top" class="text-left">
                                            <div>
                                                <#if orderHeader.statusId = "ORDER_CANCELLED" || orderHeader.statusId = "ORDER_COMPLETED">
                                                    <#if productId?exists>
                                                    ${orderItem.productId?default("N/A")} - ${orderItem.itemDescription?if_exists}
                                                    <#elseif orderItemType?exists>
                                                    ${orderItemType.description} - ${orderItem.itemDescription?if_exists}
                                                    <#else>
                                                    ${orderItem.itemDescription?if_exists}
                                                    </#if>
                                                <#else>
                                                    <#if productId?exists>
                                                        <#assign orderItemName = orderItem.productId?default("N/A")/>
                                                    <#elseif orderItemType?exists>
                                                        <#assign orderItemName = orderItemType.description/>
                                                    </#if>
                                                    <p>${uiLabelMap.ProductProduct}&nbsp;${orderItemName}</p>
                                                    <#if productId?exists>
                                                        <#assign product = orderItem.getRelatedOneCache("Product")>
                                                        <#if product.salesDiscontinuationDate?exists && Static["org.ofbiz.base.util.UtilDateTime"].nowTimestamp().after(product.salesDiscontinuationDate)>
                                                            <span class="alert">${uiLabelMap.OrderItemDiscontinued}: ${product.salesDiscontinuationDate}</span>
                                                        </#if>
                                                    </#if>
                                                ${uiLabelMap.CommonDescription}<br/>
                                                    <input type="text" size="20" name="idm_${orderItem.orderItemSeqId}" value="${orderItem.itemDescription?if_exists}" class="form-control"/>
                                                </#if>
                                            </div>
                                            <#if productId?exists>
                                                <div>
                                                <#-- <a href="/catalog/control/EditProduct?productId=${productId}" class="btn btn-primary btn-xs" target="_blank">${uiLabelMap.ProductCatalog}</a>
                                                 <a href="/ecommerce/control/product?product_id=${productId}" class="btn btn-primary btn-xs" target="_blank">${uiLabelMap.OrderEcommerce}</a>
                                               -->  <#if orderItemContentWrapper.get("IMAGE_URL")?has_content>
                                                    <a href="<@ofbizUrl>viewimage?orderId=${orderId}&amp;orderItemSeqId=${orderItem.orderItemSeqId}&amp;orderContentTypeId=IMAGE_URL</@ofbizUrl>"
                                                       target="_orderImage" class="btn btn-primary btn-xs">${uiLabelMap.OrderViewImage}</a>
                                                </#if>
                                                </div>
                                            </#if>
                                        </td>

                                    <#-- now show status details per line item -->
                                        <#assign currentItemStatus = orderItem.getRelatedOne("StatusItem")>
                                        <td class="text-left">
                                        ${uiLabelMap.CommonCurrent}&nbsp;${currentItemStatus.get("description")?default(currentItemStatus.statusId)}<br/>
                                            <#assign orderItemStatuses = orderReadHelper.getOrderItemStatuses(orderItem)>
                                            <#list orderItemStatuses as orderItemStatus>
                                                <#assign loopStatusItem = orderItemStatus.getRelatedOne("StatusItem")>
                                                <#if orderItemStatus.statusDatetime?has_content>${orderItemStatus.statusDatetime.toString()}</#if>
                                                &nbsp;${loopStatusItem.get("description",locale)?default(orderItemStatus.statusId)}<br/>
                                            </#list>
                                            <#assign returns = orderItem.getRelated("ReturnItem")?if_exists>
                                            <#if returns?has_content>
                                                <#list returns as returnItem>
                                                    <#assign returnHeader = returnItem.getRelatedOne("ReturnHeader")>
                                                    <#if returnHeader.statusId != "RETURN_CANCELLED">
                                                        <div class="alert">
                                                            <span>${uiLabelMap.OrderReturned}</span> ${uiLabelMap.CommonNbr}<a
                                                                href="<@ofbizUrl>returnMain?returnId=${returnItem.returnId}</@ofbizUrl>"
                                                                class="btn btn-primary btn-xs">${returnItem.returnId}</a>
                                                        </div>
                                                    </#if>
                                                </#list>
                                            </#if>
                                        </td>
                                        <td class="text-left" valign="top" nowrap="nowrap">
                                            <#assign shippedQuantity = orderReadHelper.getItemShippedQuantity(orderItem)>
                                            <#assign shipmentReceipts = delegator.findByAnd("ShipmentReceipt", {"orderId" : orderHeader.getString("orderId"), "orderItemSeqId" : orderItem.orderItemSeqId})/>
                                            <#assign totalReceived = 0.0>
                                            <#if shipmentReceipts?exists && shipmentReceipts?has_content>
                                                <#list shipmentReceipts as shipmentReceipt>
                                                    <#if shipmentReceipt.quantityAccepted?exists && shipmentReceipt.quantityAccepted?has_content>
                                                        <#assign  quantityAccepted = shipmentReceipt.quantityAccepted>
                                                        <#assign totalReceived = quantityAccepted + totalReceived>
                                                    </#if>
                                                    <#if shipmentReceipt.quantityRejected?exists && shipmentReceipt.quantityRejected?has_content>
                                                        <#assign  quantityRejected = shipmentReceipt.quantityRejected>
                                                        <#assign totalReceived = quantityRejected + totalReceived>
                                                    </#if>
                                                </#list>
                                            </#if>
                                            <#if orderHeader.orderTypeId == "PURCHASE_ORDER">
                                                <#assign remainingQuantity = ((orderItem.quantity?default(0) - orderItem.cancelQuantity?default(0)) - totalReceived?double)>
                                            <#else>
                                                <#assign remainingQuantity = ((orderItem.quantity?default(0) - orderItem.cancelQuantity?default(0)) - shippedQuantity?double)>
                                            </#if>
                                        ${uiLabelMap.OrderOrdered}&nbsp;${orderItem.quantity?default(0)?string.number}&nbsp;&nbsp;<br/>
                                        ${uiLabelMap.OrderCancelled}:&nbsp;${orderItem.cancelQuantity?default(0)?string.number}&nbsp;&nbsp;<br/>
                                        ${uiLabelMap.OrderRemaining}:&nbsp;${remainingQuantity}&nbsp;&nbsp;<br/>
                                        </td>
                                        <td class="text-left" valign="top" nowrap="nowrap">
                                        <#-- check for permission to modify price -->
                                            <#if (allowPriceChange)>
                                                <input type="text" size="8" name="ipm_${orderItem.orderItemSeqId}" value="<@ofbizAmount amount=orderItem.unitPrice/>" class="form-control"/>
                                                &nbsp;<input type="checkbox" name="opm_${orderItem.orderItemSeqId}" value="Y"/>
                                            <#else>
                                                <div><@ofbizCurrency amount=orderItem.unitPrice isoCode=currencyUomId/>
                                                    / <@ofbizCurrency amount=orderItem.unitListPrice isoCode=currencyUomId/></div>
                                            </#if>
                                        </td>
                                        <td class="text-left" valign="top" nowrap="nowrap">
                                            <@ofbizCurrency amount=Static["org.ofbiz.order.order.OrderReadHelper"].getOrderItemAdjustmentsTotal(orderItem, orderAdjustments, true, false, false) isoCode=currencyUomId/>
                                        </td>
                                        <td class="text-left" valign="top" nowrap="nowrap">
                                            <#if orderItem.statusId != "ITEM_CANCELLED">
                                  <@ofbizCurrency amount=Static["org.ofbiz.order.order.OrderReadHelper"].getOrderItemSubTotal(orderItem, orderAdjustments) isoCode=currencyUomId/>
                                  <#else>
                                                <@ofbizCurrency amount=0.00 isoCode=currencyUomId/>
                                            </#if>
                                        </td>
                                        <td>&nbsp;</td>
                                    </#if>
                                    <td>&nbsp;</td>
                                </tr>

                            <#-- now update/cancel reason and comment field -->
                                <#if orderItem.statusId != "ITEM_CANCELLED" && orderItem.statusId != "ITEM_COMPLETED" && ("Y" != orderItem.isPromo?if_exists)>
                                    <tr class="info">
                                        <td colspan="8"><span>${uiLabelMap.OrderReturnReason}</span>
                                            <select name="irm_${orderItem.orderItemSeqId}" class="form-control">
                                                <option value="">&nbsp;</option>
                                                <#list orderItemChangeReasons as reason>
                                                    <option value="${reason.enumId}">${reason.get("description",locale)?default(reason.enumId)}</option>
                                                </#list>
                                            </select>
                                            <span>${uiLabelMap.CommonComments}</span>
                                            <input type="text" name="icm_${orderItem.orderItemSeqId}" value="" size="30" maxlength="60" class="form-control"/>
                                            <#if (orderHeader.orderTypeId == 'PURCHASE_ORDER')>
                                                <span>${uiLabelMap.OrderEstimatedShipDate}</span>
                                                <@htmlTemplate.renderDateTimeField name="isdm_${orderItem.orderItemSeqId}" value="${orderItem.estimatedShipDate?if_exists}" event="" action="" className="" alert="" title="Format: yyyy-MM-dd HH:mm:ss.SSS" size="25" maxlength="30" id="isdm_${orderItem.orderItemSeqId}" dateType="date" shortDateInput=false timeDropdownParamName="" defaultDateTimeString="" localizedIconTitle="" timeDropdown="" timeHourName="" classString="" hour1="" hour2="" timeMinutesName="" minutes="" isTwelveHour="" ampmName="" amSelected="" pmSelected="" compositeType="" formName=""/>
                                                <span>${uiLabelMap.OrderOrderQuoteEstimatedDeliveryDate}</span>
                                                <@htmlTemplate.renderDateTimeField name="iddm_${orderItem.orderItemSeqId}" value="${orderItem.estimatedDeliveryDate?if_exists}" event="" action="" className="" alert="" title="Format: yyyy-MM-dd HH:mm:ss.SSS" size="25" maxlength="30" id="iddm_${orderItem.orderItemSeqId}" dateType="date" shortDateInput=false timeDropdownParamName="" defaultDateTimeString="" localizedIconTitle="" timeDropdown="" timeHourName="" classString="" hour1="" hour2="" timeMinutesName="" minutes="" isTwelveHour="" ampmName="" amSelected="" pmSelected="" compositeType="" formName=""/>
                                            </#if>
                                        </td>
                                    </tr>
                                </#if>
                            <#-- now show adjustment details per line item -->
                                <#assign orderItemAdjustments = Static["org.ofbiz.order.order.OrderReadHelper"].getOrderItemAdjustmentList(orderItem, orderAdjustments)>
                                <#if orderItemAdjustments?exists && orderItemAdjustments?has_content>
                                    <#list orderItemAdjustments as orderItemAdjustment>
                                        <#assign adjustmentType = orderItemAdjustment.getRelatedOneCache("OrderAdjustmentType")>
                                        <tr class="info">
                                            <td class="text-right" colspan="4">
                                                <span>${uiLabelMap.OrderAdjustment}</span>&nbsp;${adjustmentType.get("description",locale)}&nbsp;
                                            ${orderItemAdjustment.get("description",locale)?if_exists} (${orderItemAdjustment.comments?default("")})

                                                <#if orderItemAdjustment.orderAdjustmentTypeId == "SALES_TAX">
                                                    <#if orderItemAdjustment.primaryGeoId?has_content>
                                                        <#assign primaryGeo = orderItemAdjustment.getRelatedOneCache("PrimaryGeo")/>
                                                        <span>${uiLabelMap.OrderJurisdiction}</span>&nbsp;${primaryGeo.geoName} [${primaryGeo.abbreviation?if_exists}]
                                                        <#if orderItemAdjustment.secondaryGeoId?has_content>
                                                            <#assign secondaryGeo = orderItemAdjustment.getRelatedOneCache("SecondaryGeo")/>
                                                            (<span>${uiLabelMap.CommonIn}</span>&nbsp;${secondaryGeo.geoName} [${secondaryGeo.abbreviation?if_exists}])
                                                        </#if>
                                                    </#if>
                                                    <#if orderItemAdjustment.sourcePercentage?exists><span>Rate</span>&nbsp;${orderItemAdjustment.sourcePercentage}</#if>
                                                    <#if orderItemAdjustment.customerReferenceId?has_content>
                                                        <span>Customer Tax ID</span>&nbsp;${orderItemAdjustment.customerReferenceId}</#if>
                                                    <#if orderItemAdjustment.exemptAmount?exists><span>Exempt Amount</span>&nbsp;${orderItemAdjustment.exemptAmount}</#if>
                                                </#if>
                                            </td>

                                            <td>
                                                <@ofbizCurrency amount=Static["org.ofbiz.order.order.OrderReadHelper"].calcItemAdjustment(orderItemAdjustment, orderItem) isoCode=currencyUomId/>
                                            </td>
                                            <td>&nbsp;</td>
                                            <td>&nbsp;</td>
                                            <td>&nbsp;</td>
                                        </tr>
                                    </#list>
                                </#if>

                            <#-- now show ship group info per line item -->
                                <#assign orderItemShipGroupAssocs = orderItem.getRelated("OrderItemShipGroupAssoc")?if_exists>
                                <#if orderItemShipGroupAssocs?has_content>

                                    <#list orderItemShipGroupAssocs as shipGroupAssoc>
                                        <#assign shipGroupQty = shipGroupAssoc.quantity - shipGroupAssoc.cancelQuantity?default(0)>
                                        <#assign shipGroup = shipGroupAssoc.getRelatedOne("OrderItemShipGroup")>
                                        <#assign shipGroupAddress = shipGroup.getRelatedOne("PostalAddress")?if_exists>
                                        <tr class="info">
                                            <td class="text-right" colspan="2">
                                                <span>${uiLabelMap.OrderShipGroup}</span>&nbsp;[${shipGroup.shipGroupSeqId}
                                                ] ${shipGroupAddress.address1?default("${uiLabelMap.OrderNotShipped}")}
                                            </td>
                                            <td align="center">
                                                <input type="text" name="iqm_${shipGroupAssoc.orderItemSeqId}:${shipGroupAssoc.shipGroupSeqId}" size="6"
                                                       value="${shipGroupQty?string.number}" class="form-control"/>
                                            </td>
                                            <td colspan="4">&nbsp;</td>
                                            <td>
                                                <#assign itemStatusOkay = (orderItem.statusId != "ITEM_CANCELLED" && orderItem.statusId != "ITEM_COMPLETED" && (shipGroupAssoc.cancelQuantity?default(0) < shipGroupAssoc.quantity?default(0)) && ("Y" != orderItem.isPromo?if_exists))>
                                                <#if (security.hasEntityPermission("ORDERMGR", "_ADMIN", session) && itemStatusOkay) || (security.hasEntityPermission("ORDERMGR", "_UPDATE", session) && itemStatusOkay && orderHeader.statusId != "ORDER_SENT")>
                                                    <input type="checkbox" name="selectedItem" value="${orderItem.orderItemSeqId}"/>
                                                    <a href="javascript:document.updateItemInfo.action='<@ofbizUrl>cancelOrderItem</@ofbizUrl>';document.updateItemInfo.orderItemSeqId.value='${orderItem.orderItemSeqId}';document.updateItemInfo.shipGroupSeqId.value='${shipGroup.shipGroupSeqId}';document.updateItemInfo.submit()"
                                                       class="btn btn-primary btn-xs">${uiLabelMap.CommonCancel}</a>
                                                <#else>
                                                    &nbsp;
                                                </#if>
                                            </td>
                                        </tr>
                                    </#list>
                                </#if>
                            </#if>
                        </#list>
                        <tr class="info">
                            <td colspan="7">&nbsp;</td>
                            <td>
                                <input type="submit" value="${uiLabelMap.OrderUpdateItems}" class="btn btn-primary btn-xs"/>
                            </td>
                        </tr>

                    </table>
                </div>
            </form>
        </#if>
        <#list orderHeaderAdjustments as orderHeaderAdjustment>
            <#assign adjustmentType = orderHeaderAdjustment.getRelatedOne("OrderAdjustmentType")>
            <#assign adjustmentAmount = Static["org.ofbiz.order.order.OrderReadHelper"].calcOrderAdjustment(orderHeaderAdjustment, orderSubTotal)>
            <#assign orderAdjustmentId = orderHeaderAdjustment.get("orderAdjustmentId")>
            <#assign productPromoCodeId = ''>
            <#if adjustmentType.get("orderAdjustmentTypeId") == "PROMOTION_ADJUSTMENT" && orderHeaderAdjustment.get("productPromoId")?has_content>
                <#assign productPromo = orderHeaderAdjustment.getRelatedOne("ProductPromo")>
                <#assign productPromoCodes = delegator.findByAnd("ProductPromoCode", {"productPromoId":productPromo.productPromoId})>
                <#assign orderProductPromoCode = ''>
                <#list productPromoCodes as productPromoCode>
                    <#if !(orderProductPromoCode?has_content)>
                        <#assign orderProductPromoCode = delegator.findOne("OrderProductPromoCode", {"productPromoCodeId":productPromoCode.productPromoCodeId, "orderId":orderHeaderAdjustment.orderId}, false)?if_exists>
                    </#if>
                </#list>
                <#if orderProductPromoCode?has_content>
                    <#assign productPromoCodeId = orderProductPromoCode.get("productPromoCodeId")>
                </#if>
            </#if>
            <#if adjustmentAmount != 0>
            <div class="table-responsive">
            <#--<table class="basic-table" cellspacing="0">-->
            <table class="table">

            <tr class="info">
                <td>
                <form name="updateOrderAdjustmentForm${orderAdjustmentId}" method="post" action="<@ofbizUrl>updateOrderAdjustment</@ofbizUrl>" class="form-inline">
                    <input type="hidden" name="orderAdjustmentId" value="${orderAdjustmentId?if_exists}"/>
                    <input type="hidden" name="orderId" value="${orderId?if_exists}"/>

                    <div class="form-group">
                        <div class="input-group">
                            <span class="input-group-addon">${adjustmentType.get("description",locale)}&nbsp;${orderHeaderAdjustment.comments?if_exists}描述:</span>
                            <#if (allowPriceChange)>
                                <input type="text" name="description" value="${orderHeaderAdjustment.get("description")?if_exists}" size="30" maxlength="60" class="form-control"/>
                            <#else>
                            ${orderHeaderAdjustment.get("description")?if_exists}
                            </#if>
                        </div>
                        <div class="input-group">
                            <#if (allowPriceChange)>
                                <input type="text" name="amount" size="6" value="<@ofbizAmount amount=adjustmentAmount/>" class="form-control"/>

                            <#else>
                                <@ofbizAmount amount=adjustmentAmount/>
                            </#if>
                        </div>
                        <input class="btn btn-primary" type="submit" value="${uiLabelMap.CommonUpdate}"/>
                        <a href="javascript:document.deleteOrderAdjustment${orderAdjustmentId}.submit();" class="btn btn-primary">${uiLabelMap.CommonDelete}</a>
                    </div>
                </form>
                <form name="deleteOrderAdjustment${orderAdjustmentId}" method="post" action="<@ofbizUrl>deleteOrderAdjustment</@ofbizUrl>">
                    <input type="hidden" name="orderAdjustmentId" value="${orderAdjustmentId?if_exists}"/>
                    <input type="hidden" name="orderId" value="${orderId?if_exists}"/>
                    <#if adjustmentType.get("orderAdjustmentTypeId") == "PROMOTION_ADJUSTMENT">
                        <input type="hidden" name="productPromoCodeId" value="${productPromoCodeId?if_exists}"/>
                    </#if>
                </form>
                </td>
            </tr>
            </table>
            </div>
            </#if>
        </#list>

    <#-- add new adjustment -->
        <#if security.hasEntityPermission("ORDERMGR", "_UPDATE", session) && orderHeader.statusId != "ORDER_COMPLETED" && orderHeader.statusId != "ORDER_CANCELLED" && orderHeader.statusId != "ORDER_REJECTED">
        <div class="table-responsive">
        <#--<table class="basic-table" cellspacing="0">-->
        <table class="table">

            <tr class="info">
                        <td colspan="4">
                            <form name="addAdjustmentForm" method="post" action="<@ofbizUrl>createOrderAdjustment</@ofbizUrl>" class="form-inline">
                                <input type="hidden" name="comments" value="Added manually by [${userLogin.userLoginId}]"/>
                                <input type="hidden" name="orderId" value="${orderId?if_exists}"/>
                                <div class="form-group">
                                    <div class="input-group">
                                        <span class="input-group-addon">${uiLabelMap.OrderAdjustment}&nbsp;</span>
                                        <select name="orderAdjustmentTypeId" class="form-control">
                                            <#list orderAdjustmentTypes as type>
                                                <option value="${type.orderAdjustmentTypeId}">${type.get("description",locale)?default(type.orderAdjustmentTypeId)}</option>
                                            </#list>
                                        </select>
                                        </div>
                                        <div class="input-group">
                                        <span class="input-group-addon">货运组</span><select name="shipGroupSeqId" class="form-control">
                                            <option value="_NA_"></option>
                                            <#list shipGroups as shipGroup>
                                                <option value="${shipGroup.shipGroupSeqId}">${uiLabelMap.OrderShipGroup} ${shipGroup.shipGroupSeqId}</option>
                                            </#list>
                                        </select>
                                            </div>
                                    <div class="input-group">

                                        <span class="input-group-addon">描述</span><input type="text" name="description" value="" size="30" maxlength="60" class="form-control"/>
                                        </div>
                                    <div class="input-group">
                                        <span class="input-group-addon">金额</span>
                                        <input type="text" name="amount" size="6" value="<@ofbizAmount amount=0.00/>" class="form-control"/>

                                    </div>
                                    <input class="btn btn-primary btn-sm" name="submitBtn" type="submit" value="增加"/>
                                </div>
                            </form>
                        </td>
                        <td colspan="3">&nbsp;</td>
             </tr>
        </table>
        </div>
        </#if>

    <#-- subtotal -->
        <div class="table-responsive">
        <#--<table class="basic-table" cellspacing="0">-->
            <table class="table">

                <tr class="info">
                    <td class="text-right" width="80%"><span>${uiLabelMap.OrderItemsSubTotal}</span></td>
                    <td class="text-left" width="10%" nowrap="nowrap"><@ofbizCurrency amount=orderSubTotal isoCode=currencyUomId/></td>
                    <td width="10%" colspan="2">&nbsp;</td>
                </tr>

            <#-- other adjustments -->
                <tr class="info">
                    <td class="text-right"><span>${uiLabelMap.OrderTotalOtherOrderAdjustments}</span></td>
                    <td class="text-left" nowrap="nowrap"><@ofbizCurrency amount=otherAdjAmount isoCode=currencyUomId/></td>
                    <td colspan="2">&nbsp;</td>
                </tr>

            <#-- shipping adjustments -->
                <tr class="info">
                    <td class="text-right"><span>${uiLabelMap.OrderTotalShippingAndHandling}</span></td>
                    <td class="text-left" nowrap="nowrap"><@ofbizCurrency amount=shippingAmount isoCode=currencyUomId/></td>
                    <td colspan="2">&nbsp;</td>
                </tr>

            <#-- tax adjustments -->
                <tr class="info">
                    <td class="text-right"><span>${uiLabelMap.OrderTotalSalesTax}</span></td>
                    <td class="text-left" nowrap="nowrap"><@ofbizCurrency amount=taxAmount isoCode=currencyUomId/></td>
                    <td colspan="2">&nbsp;</td>
                </tr>

            <#-- grand total -->
                <tr class="info">
                    <td class="text-right"><span>${uiLabelMap.OrderTotalDue}</span></td>
                    <td class="text-left" nowrap="nowrap"><@ofbizCurrency amount=grandTotal isoCode=currencyUomId/></td>
                    <td colspan="2">&nbsp;</td>
                </tr>
            </table>
        </div>
    <@htmlScreenTemplate.renderScreenEnd/>
</#if>
