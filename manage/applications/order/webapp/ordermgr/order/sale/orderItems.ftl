<style>
    .data_item {
        padding-left: 65px;
        position: relative;
        min-height: 65px;
        overflow-y: hidden;
    }

    .data_item img {
        position: absolute;
        left: 0;
        top: 0;
        width: 60px;
    }

    img {
        vertical-align: middle;
    }

    img {
        border: 0;
    }

    .data_item p {
        margin: 0 0 5px 0;
        font-size: 14px;
        text-align: left;
    }

    p {
        margin: 0 0 10px;
    }
    .panel {
        margin-bottom: 0px;
    }
</style>
<#include "component://common/webcommon/includes/htmlScreenTemplate.ftl"/>
<#if orderHeader?has_content>
<div class="panel panel-default f-s-12">
    <div class="panel-heading">
        <h4 class="panel-title">订购商品</h4>
    </div>
<div class="panel-body">
    <div class="table-responsive">
        <table class="table table-striped table-bordered">
        <#--<table class="order-items basic-table" cellspacing='0'>-->
            <tr class="">
                <th width="20%">${uiLabelMap.ProductProduct}</th>
                <th width="8%">${uiLabelMap.CommonStatus}</th>

                <th width="15%">库存</th>
                <th width="15%">货运</th>
                <th>促销</th>

                <th width="10%">单价</th>
                <th width="10%">${uiLabelMap.OrderQuantity}</th>
                <th width="8%">${uiLabelMap.OrderAdjustments}</th>
                <th width="8%">${uiLabelMap.OrderSubTotal}</th>
            </tr>
            <#if !orderItemList?has_content>
                <tr>
                    <td colspan="8">
                        <font color="red">${uiLabelMap.checkhelper_sales_order_lines_lookup_failed}</font>
                    </td>
                </tr>
            <#else>
                <#list orderItemList as orderItem>
                    <#assign orderItemContentWrapper = Static["org.ofbiz.order.order.OrderContentWrapper"].makeOrderContentWrapper(orderItem, request)>
                    <#assign orderItemShipGrpInvResList = orderReadHelper.getOrderItemShipGrpInvResList(orderItem)>
                    <#assign orderItemType = orderItem.getRelatedOne("OrderItemType")?if_exists>
                    <#assign productId = orderItem.productId?if_exists>
                    <#if productId?has_content>
                        <#assign product = orderItem.getRelatedOneCache("Product")>
                    </#if>
                    <#if orderHeader.orderTypeId == "SALES_ORDER"><#assign pickedQty = orderReadHelper.getItemPickedQuantityBd(orderItem)></#if>
                    <tr>

                        <td>
                            <div class="data_item">
                                <img src="${product.smallImageUrl}" width="50"/>
                            <p>
                                <#if orderItem.supplierProductId?has_content>
                                ${orderItem.supplierProductId} - ${orderItem.itemDescription?if_exists}
                                <#elseif productId?exists>
                                ${orderItem.productId?default("N/A")} - ${orderItem.itemDescription?if_exists}
                                    <#if (product.salesDiscontinuationDate)?exists && Static["org.ofbiz.base.util.UtilDateTime"].nowTimestamp().after(product.salesDiscontinuationDate)>
                                        <br/>
                                        <span style="color: red;">${uiLabelMap.OrderItemDiscontinued}
                                            : ${Static["org.ofbiz.base.util.UtilFormatOut"].formatDateTime(product.salesDiscontinuationDate, "", locale, timeZone)!}</span>
                                    </#if>
                                <#elseif orderItemType?exists>
                                ${orderItemType.description} - ${orderItem.itemDescription?if_exists}
                                <#else>
                                ${orderItem.itemDescription?if_exists}
                                </#if>
                                <#assign orderItemAttributes = orderItem.getRelated("OrderItemAttribute")/>
                                <#if orderItemAttributes?has_content>
                                    <ul>
                                        <#list orderItemAttributes as orderItemAttribute>
                                            <li>
                                            ${orderItemAttribute.attrName} : ${orderItemAttribute.attrValue}
                                            </li>
                                        </#list>
                                    </ul>
                                </#if>
                                </p>
                            </div>
                        </td>

                    <#-- now show status details per line item -->
                        <#assign currentItemStatus = orderItem.getRelatedOne("StatusItem")>
                        <td>
                        ${currentItemStatus.get("description")?default(currentItemStatus.statusId)}

                            <#assign returns = orderItem.getRelated("ReturnItem")?if_exists>
                            <#if returns?has_content>
                                <#list returns as returnItem>
                                    <#assign returnHeader = returnItem.getRelatedOne("ReturnHeader")>
                                    <#if returnHeader.statusId != "RETURN_CANCELLED">
                                        <font color="red">${uiLabelMap.OrderReturned}</font>
                                    ${uiLabelMap.CommonNbr}
                                        <@htmlScreenTemplate.renderModalPage id="returnMain_${returnItem.returnId}" description="退货信息" name="returnMain_${returnItem.returnId}" modalTitle="退货信息"
                                        modalUrl="/ordermgr/control/returnMain"
                                        targetParameterIter="returnId:'${returnItem.returnId}'"/>

                                        <#--<a href="<@ofbizUrl>returnMain?returnId=${returnItem.returnId}</@ofbizUrl>" class="buttontext">${returnItem.returnId}</a>-->
                                    </#if>
                                </#list>
                            </#if>
                        </td>

                        <td>
                        <#-- INVENTORY -->
                            <#if (orderHeader.statusId != "ORDER_COMPLETED") && availableToPromiseMap?exists && quantityOnHandMap?exists && availableToPromiseMap.get(productId)?exists && quantityOnHandMap.get(productId)?exists>
                                <#assign quantityToProduce = 0>
                                <#assign atpQuantity = availableToPromiseMap.get(productId)?default(0)>
                                <#assign qohQuantity = quantityOnHandMap.get(productId)?default(0)>
                                <#assign mktgPkgATP = mktgPkgATPMap.get(productId)?default(0)>
                                <#assign mktgPkgQOH = mktgPkgQOHMap.get(productId)?default(0)>
                                <#assign requiredQuantity = requiredProductQuantityMap.get(productId)?default(0)>
                                <#assign onOrderQuantity = onOrderProductQuantityMap.get(productId)?default(0)>
                                <#assign inProductionQuantity = productionProductQuantityMap.get(productId)?default(0)>
                                <#assign unplannedQuantity = requiredQuantity - qohQuantity - inProductionQuantity - onOrderQuantity - mktgPkgQOH>
                                <#if unplannedQuantity < 0><#assign unplannedQuantity = 0></#if>


                                <a class="btn btn-primary btn-xs"
                                   href="/catalog/control/EditProductInventoryItems?productId=${productId}&amp;showAllFacilities=Y${externalKeyParam}"
                                   target="_blank">${uiLabelMap.ProductInventory}</a>
                            <p>
                                <#if availableToPromiseByFacilityMap?exists && quantityOnHandByFacilityMap?exists && quantityOnHandByFacilityMap.get(productId)?exists && availableToPromiseByFacilityMap.get(productId)?exists>
                                <#assign atpQuantityByFacility = availableToPromiseByFacilityMap.get(productId)?default(0)>
                                <#assign qohQuantityByFacility = quantityOnHandByFacilityMap.get(productId)?default(0)>

                                    <p>${uiLabelMap.ProductQoh}:${qohQuantityByFacility}</p>

                                    <#if orderItemShipGrpInvResList?exists && orderItemShipGrpInvResList?has_content>
                                        <#list orderItemShipGrpInvResList as orderItemShipGrpInvRes>
                                            <span>当前库存:</span>&nbsp;
                                            <a href="/facility/control/EditInventoryItem?inventoryItemId=${orderItemShipGrpInvRes.inventoryItemId}&amp;externalLoginKey=${externalLoginKey}"
                                               class="btn btn-primary btn-xs">${orderItemShipGrpInvRes.inventoryItemId}</a>
                                        </#list>
                                    </#if>

                                </#if>
                            </#if>

                        </td>
                        <td>


                        <#-- now show ship group info per line item -->
                            <#assign orderItemShipGroupAssocs = orderItem.getRelated("OrderItemShipGroupAssoc")?if_exists>
                            <#if orderItemShipGroupAssocs?has_content>
                                <#list orderItemShipGroupAssocs as shipGroupAssoc>
                                    <#assign shipGroup = shipGroupAssoc.getRelatedOne("OrderItemShipGroup")>
                                    <#assign shipGroupAddress = shipGroup.getRelatedOne("PostalAddress")?if_exists>
                                    <span>${uiLabelMap.OrderShipGroup}</span>&nbsp;[${shipGroup.shipGroupSeqId}]
                                </#list>

                            </#if>
                        <#-- now show inventory reservation info per line item -->

                        </td>
                    <#-- now show adjustment details per line item -->
                        <td>
                            <#assign orderItemAdjustments = Static["org.ofbiz.order.order.OrderReadHelper"].getOrderItemAdjustmentList(orderItem, orderAdjustments)>
                            <#if orderItemAdjustments?exists && orderItemAdjustments?has_content>
                                <#list orderItemAdjustments as orderItemAdjustment>
                                    <#assign adjustmentType = orderItemAdjustment.getRelatedOneCache("OrderAdjustmentType")>

                                    <span>${uiLabelMap.OrderAdjustment}</span>&nbsp;${adjustmentType.get("description",locale)}
                                ${StringUtil.wrapString(orderItemAdjustment.get("description",locale)?if_exists)}
                                    <#if orderItemAdjustment.comments?has_content>
                                        (${orderItemAdjustment.comments?default("")})
                                    </#if>
                                    <#if orderItemAdjustment.productPromoId?has_content>
                                        <a href="/catalog/control/EditProductPromo?productPromoId=${orderItemAdjustment.productPromoId}&amp;externalLoginKey=${externalLoginKey}"
                                        >${orderItemAdjustment.getRelatedOne("ProductPromo").getString("promoName")}</a>
                                    </#if>
                                    <#if orderItemAdjustment.orderAdjustmentTypeId == "SALES_TAX">
                                        <#if orderItemAdjustment.primaryGeoId?has_content>
                                            <#assign primaryGeo = orderItemAdjustment.getRelatedOneCache("PrimaryGeo")/>
                                            <#if primaryGeo.geoName?has_content>
                                                <span>${uiLabelMap.OrderJurisdiction}</span>&nbsp;${primaryGeo.geoName} [${primaryGeo.abbreviation?if_exists}]
                                            </#if>
                                            <#if orderItemAdjustment.secondaryGeoId?has_content>
                                                <#assign secondaryGeo = orderItemAdjustment.getRelatedOneCache("SecondaryGeo")/>
                                                <span>${uiLabelMap.CommonIn}</span>&nbsp;${secondaryGeo.geoName} [${secondaryGeo.abbreviation?if_exists}])
                                            </#if>
                                        </#if>
                                        <#if orderItemAdjustment.sourcePercentage?exists>
                                            <span>${uiLabelMap.OrderRate}</span>&nbsp;${orderItemAdjustment.sourcePercentage?string("0.######")}
                                        </#if>
                                        <#if orderItemAdjustment.customerReferenceId?has_content>
                                            <span>${uiLabelMap.OrderCustomerTaxId}</span>&nbsp;${orderItemAdjustment.customerReferenceId}
                                        </#if>
                                        <#if orderItemAdjustment.exemptAmount?exists>
                                            <span>${uiLabelMap.OrderExemptAmount}</span>&nbsp;${orderItemAdjustment.exemptAmount}
                                        </#if>
                                    </#if>
                                    <@ofbizCurrency amount=Static["org.ofbiz.order.order.OrderReadHelper"].calcItemAdjustment(orderItemAdjustment, orderItem) isoCode=currencyUomId/>

                                </#list>
                            </#if>
                            &nbsp;</td>

                        <td>
                            <@ofbizCurrency amount=orderItem.unitPrice isoCode=currencyUomId/>
                        </td>
                        <td>
                        ${orderItem.quantity?default(0)?string.number}

                        </td>
                        <td>
                            <@ofbizCurrency amount=Static["org.ofbiz.order.order.OrderReadHelper"].getOrderItemAdjustmentsTotal(orderItem, orderAdjustments, true, false, false) isoCode=currencyUomId/>
                        </td>
                        <td>
                            <#if orderItem.statusId != "ITEM_CANCELLED">
                       <@ofbizCurrency amount=Static["org.ofbiz.order.order.OrderReadHelper"].getOrderItemSubTotal(orderItem, orderAdjustments) isoCode=currencyUomId/>
                         <#else>
                                <@ofbizCurrency amount=0.00 isoCode=currencyUomId/>
                            </#if>
                        </td>

                    </tr>



                </#list>
            </#if>
            <td colspan="5">
                <#list orderHeaderAdjustments as orderHeaderAdjustment>
                    <#assign adjustmentType = orderHeaderAdjustment.getRelatedOne("OrderAdjustmentType")>
                    <#assign adjustmentAmount = Static["org.ofbiz.order.order.OrderReadHelper"].calcOrderAdjustment(orderHeaderAdjustment, orderSubTotal)>
                    <#if adjustmentAmount != 0>

                        <#if orderHeaderAdjustment.comments?has_content>${orderHeaderAdjustment.comments} - </#if>
                        <#if orderHeaderAdjustment.description?has_content>${orderHeaderAdjustment.description} - </#if>
                        <span>${adjustmentType.get("description", locale)}</span>

                        <@ofbizCurrency amount=adjustmentAmount isoCode=currencyUomId/>
                        &nbsp; &nbsp; &nbsp; &nbsp; &nbsp;
                    </#if>
                </#list>&nbsp;
            </td>
        <#-- subtotal -->

            <td>

                <span>${uiLabelMap.OrderItemsSubTotal}:</span>
                <@ofbizCurrency amount=orderSubTotal isoCode=currencyUomId/>
                &nbsp;

            </td>
        <#-- other adjustments -->
            <td>
                <span>其他调整:</span>
                <@ofbizCurrency amount=otherAdjAmount isoCode=currencyUomId/>
                &nbsp;
            </td>
        <#-- shipping adjustments -->
            <td>
                <span>配送费:</span>
                <@ofbizCurrency amount=shippingAmount isoCode=currencyUomId/>
                &nbsp;
            </td>
        <#-- tax adjustments -->

        <#-- grand total -->
            <td>
                <span>合计:</span>
                <@ofbizCurrency amount=grandTotal isoCode=currencyUomId/>
                &nbsp;
            </td>
        </table>
    </div>
</#if>
</div>
</div>