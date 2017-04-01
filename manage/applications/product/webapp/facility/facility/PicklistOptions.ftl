<@htmlScreenTemplate.renderScreenletBegin id="" title="${uiLabelMap.FacilitySelectOptionsToGroupBy}"/>

<form method="post" name="selectFactors" action="<@ofbizUrl>PicklistOptions</@ofbizUrl>" class="form-inline">
    <input type="hidden" name="facilityId" value="${facilityId}"/>

    <div class="form-group">
        <div class="input-group">
            <div class='input-group-addon'><input type="checkbox" class="checkbox-inline" name="groupByShippingMethod" value="Y"
                                                  <#if "${requestParameters.groupByShippingMethod?if_exists}" == "Y">checked="checked"</#if>/></div>
            <div class='input-group-addon'>${uiLabelMap.FacilityGroupByShippingMethod}</div>
        </div>
        <div class="input-group">

            <div class='input-group-addon'><input type="checkbox" name="groupByWarehouseArea" value="Y"
                                                  <#if "${requestParameters.groupByWarehouseArea?if_exists}" == "Y">checked="checked"</#if>/></div>
            <div class='input-group-addon'><span>${uiLabelMap.FacilityGroupByWarehouseArea}</span></div>
        </div>
        <div class="input-group">

            <div class='input-group-addon'><input type="checkbox" class="checkbox-inline" name="groupByNoOfOrderItems" value="Y"
                                                  <#if "${requestParameters.groupByNoOfOrderItems?if_exists}" == "Y">checked="checked"</#if>/></div>
            <div class='input-group-addon'><span>${uiLabelMap.FacilityGroupByNoOfOrderItems}</span></div>

        </div>

        <input type="submit" value="确定" class="btn btn-primary btn-sm"/>
    </div>
</form>
<@htmlScreenTemplate.renderScreenletEnd/>
<@htmlScreenTemplate.renderScreenletBegin id="" title="${uiLabelMap.ProductFindOrdersToPick}"/>

<a class="btn btn-primary btn-sm" href="<@ofbizUrl>ReviewOrdersNotPickedOrPacked?facilityId=${facilityId}</@ofbizUrl>">${uiLabelMap.FormFieldTitle_reviewOrdersNotPickedOrPacked}</a>
<br/>
<br/>
<div class="table-responsive">
<table class="table table-striped table-bordered">
<#if pickMoveInfoList?has_content || rushOrderInfo?has_content>
    <tr class="header-row">
        <#if !((requestParameters.groupByShippingMethod?exists && requestParameters.groupByShippingMethod == "Y") || (requestParameters.groupByWarehouseArea?exists && requestParameters.groupByWarehouseArea == "Y") || (requestParameters.groupByNoOfOrderItems?exists && requestParameters.groupByNoOfOrderItems == "Y"))>
            <th>${uiLabelMap.OrderOrder} ${uiLabelMap.CommonNbr}</th>
        <#else>
            <th>${uiLabelMap.ProductShipmentMethod}</th>
            <th>${uiLabelMap.ProductWarehouseArea}</th>
            <th>${uiLabelMap.ProductNumberOfOrderItems}</th>
        </#if>
        <th>${uiLabelMap.ProductReadyToPick}</th>
        <th>${uiLabelMap.ProductNeedStockMove}</th>
        <th>&nbsp;</th>
        <th>&nbsp;</th>
        <th>&nbsp;</th>
    </tr>
</#if>
<#if rushOrderInfo?has_content>
    <#assign orderReadyToPickInfoList = rushOrderInfo.orderReadyToPickInfoList?if_exists>
    <#assign orderNeedsStockMoveInfoList = rushOrderInfo.orderNeedsStockMoveInfoList?if_exists>
    <#assign orderReadyToPickInfoListSize = (orderReadyToPickInfoList.size())?default(0)>
    <#assign orderNeedsStockMoveInfoListSize = (orderNeedsStockMoveInfoList.size())?default(0)>
    <tr>
        <th>[Rush Orders, all Methods]</th>
        <th>${orderReadyToPickInfoListSize}</th>
        <th>${orderNeedsStockMoveInfoListSize}</th>
        <th>
            <#if orderReadyToPickInfoList?has_content>
                <form method="post" action="<@ofbizUrl>createPicklistFromOrders</@ofbizUrl>" class="form-inline">
                    <input type="hidden" name="facilityId" value="${facilityId}"/>
                    <input type="hidden" name="isRushOrder" value="Y"/>
                    <div class="input-group">
                        <span class="input-group-addon">${uiLabelMap.ProductPickFirst}:</span>
                        <input type="text" size="4" name="maxNumberOfOrders" value="20" class="form-control"/>
                    </div>
                    <input type="submit" value="${uiLabelMap.ProductCreatePicklist}" class="btn btn-primary btn-sm"/>
                </form>
            <#else>
                &nbsp;
            </#if>
        </th>
    </tr>
</#if>
<#if pickMoveInfoList?has_content>
    <#assign orderReadyToPickInfoListSizeTotal = 0>
    <#assign orderNeedsStockMoveInfoListSizeTotal = 0>
    <#assign alt_row = false>
    <#list pickMoveInfoList as pickMoveInfo>
        <#assign groupName = pickMoveInfo.groupName?if_exists>
        <#assign groupName1 = pickMoveInfo.groupName1?if_exists>
        <#assign groupName2 = pickMoveInfo.groupName2?if_exists>
        <#assign groupName3 = pickMoveInfo.groupName3?if_exists>
        <#assign orderReadyToPickInfoList = pickMoveInfo.orderReadyToPickInfoList?if_exists>
        <#assign orderNeedsStockMoveInfoList = pickMoveInfo.orderNeedsStockMoveInfoList?if_exists>
        <#assign orderReadyToPickInfoListSize = (orderReadyToPickInfoList.size())?default(0)>
        <#assign orderNeedsStockMoveInfoListSize = (orderNeedsStockMoveInfoList.size())?default(0)>
        <#assign orderReadyToPickInfoListSizeTotal = orderReadyToPickInfoListSizeTotal + orderReadyToPickInfoListSize>
        <#assign orderNeedsStockMoveInfoListSizeTotal = orderNeedsStockMoveInfoListSizeTotal + orderNeedsStockMoveInfoListSize>
        <tr valign="middle"<#if alt_row> class="alternate-row"</#if>>
        <td>
            <form name="viewGroupDetail_${pickMoveInfo_index}" action="<@ofbizUrl>PicklistOptions</@ofbizUrl>" method="post" class="form-inline">
                <input type="hidden" name="viewDetail" value="${groupName?if_exists}"/>
                <input type="hidden" name="groupByShippingMethod" value="${requestParameters.groupByShippingMethod?if_exists}"/>
                <input type="hidden" name="groupByWarehouseArea" value="${requestParameters.groupByWarehouseArea?if_exists}"/>
                <input type="hidden" name="groupByNoOfOrderItems" value="${requestParameters.groupByNoOfOrderItems?if_exists}"/>
                <input type="hidden" name="facilityId" value="${facilityId?if_exists}"/>
            </form>
            <#if ((requestParameters.groupByShippingMethod?exists && requestParameters.groupByShippingMethod == "Y") || (requestParameters.groupByWarehouseArea?exists && requestParameters.groupByWarehouseArea == "Y") || (requestParameters.groupByNoOfOrderItems?exists && requestParameters.groupByNoOfOrderItems == "Y"))>
                <#if groupName1?has_content>
                    <a href="javascript:document.viewGroupDetail_${pickMoveInfo_index}.submit()" class="">${groupName1}</a>
                </#if>
                </td>
                    <td>
                        <#if groupName2?has_content>
                            <a href="javascript:document.viewGroupDetail_${pickMoveInfo_index}.submit()" class="">${groupName2}</a>
                        </#if>
                    </td>
                <td>
                <#if groupName3?has_content>
                    <a href="javascript:document.viewGroupDetail_${pickMoveInfo_index}.submit()" class="">${groupName3}</a></td>
                </#if>
            <#else>
                <a href="javascript:document.viewGroupDetail_${pickMoveInfo_index}.submit()" class="">${groupName?if_exists}</a>
            </#if>
          </td>
            <td>
                <#if !((requestParameters.groupByShippingMethod?exists && requestParameters.groupByShippingMethod == "Y") || (requestParameters.groupByWarehouseArea?exists && requestParameters.groupByWarehouseArea == "Y") || (requestParameters.groupByNoOfOrderItems?exists && requestParameters.groupByNoOfOrderItems == "Y"))>
                <#if orderReadyToPickInfoListSize == 0 >${uiLabelMap.CommonN}<#else>${uiLabelMap.CommonY}</#if>
              <#else>
                ${orderReadyToPickInfoListSize}
                </#if>
            </td>
            <td>
                <#if !((requestParameters.groupByShippingMethod?exists && requestParameters.groupByShippingMethod == "Y") || (requestParameters.groupByWarehouseArea?exists && requestParameters.groupByWarehouseArea == "Y") || (requestParameters.groupByNoOfOrderItems?exists && requestParameters.groupByNoOfOrderItems == "Y"))>
                <#if orderNeedsStockMoveInfoListSize == 0>${uiLabelMap.CommonN}<#else>${uiLabelMap.CommonY}</#if>
              <#else>
                ${orderNeedsStockMoveInfoListSize}
                </#if>
            </td>
            <td>
                <#if orderReadyToPickInfoList?has_content>
                    <form method="post" action="<@ofbizUrl>createPicklistFromOrders</@ofbizUrl>" class="form-inline">
                        <input type="hidden" name="facilityId" value="${facilityId?if_exists}"/>
                        <input type="hidden" name="groupByShippingMethod" value="${requestParameters.groupByShippingMethod?if_exists}"/>
                        <input type="hidden" name="groupByWarehouseArea" value="${requestParameters.groupByWarehouseArea?if_exists}"/>
                        <input type="hidden" name="groupByNoOfOrderItems" value="${requestParameters.groupByNoOfOrderItems?if_exists}"/>
                        <input type="hidden" name="orderIdList" value=""/>
                        <#assign orderIdsForPickList = orderReadyToPickInfoList?if_exists>
                        <#list orderIdsForPickList as orderIdForPickList>
                            <input type="hidden" name="orderIdList" value="${orderIdForPickList.orderHeader.orderId}"/>
                        </#list>
                        <#if ((requestParameters.groupByShippingMethod?exists && requestParameters.groupByShippingMethod == "Y") || (requestParameters.groupByWarehouseArea?exists && requestParameters.groupByWarehouseArea == "Y") || (requestParameters.groupByNoOfOrderItems?exists && requestParameters.groupByNoOfOrderItems == "Y"))>
                            <div class="input-group">
                            <span class="input-group-addon">${uiLabelMap.ProductPickFirst}</span>
                            <input type="text" size="4" name="maxNumberOfOrders" value="20" class="form-control"/>
                            </div>
                        </#if>
                        <input type="submit" value="${uiLabelMap.ProductCreatePicklist}" class="btn btn-primary btn-sm"/>
                    </form>
                <#else>
                    &nbsp;
                </#if>
            </td>
            <td>
                <#if orderReadyToPickInfoList?has_content>
                    <form method="post" action="<@ofbizUrl>printPickSheets</@ofbizUrl>" class="form-inline">
                        <input type="hidden" name="printGroupName" value="${groupName?if_exists}"/>
                        <input type="hidden" name="facilityId" value="${facilityId?if_exists}"/>
                        <input type="hidden" name="groupByShippingMethod" value="${requestParameters.groupByShippingMethod?if_exists}"/>
                        <input type="hidden" name="groupByWarehouseArea" value="${requestParameters.groupByWarehouseArea?if_exists}"/>
                        <input type="hidden" name="groupByNoOfOrderItems" value="${requestParameters.groupByNoOfOrderItems?if_exists}"/>
                        <#if !((requestParameters.groupByShippingMethod?exists && requestParameters.groupByShippingMethod == "Y") || (requestParameters.groupByWarehouseArea?exists && requestParameters.groupByWarehouseArea == "Y") || (requestParameters.groupByNoOfOrderItems?exists && requestParameters.groupByNoOfOrderItems == "Y"))>
                            <input type="hidden" name="maxNumberOfOrdersToPrint" value="1"/>
                            <input type="hidden" name="orderId" value="${groupName?if_exists}"/>
                        <#else>
                            <div class="input-group">
                            <span class="input-group-addon">${uiLabelMap.FormFieldTitle_printPickSheetFirst}</span>
                            <input type="text" size="4" name="maxNumberOfOrdersToPrint" value="20" class="form-control"/>
                            </div>
                        </#if>
                        <input type="submit" value="${uiLabelMap.FormFieldTitle_printPickSheet}" class="btn btn-primary btn-sm"/>
                    </form>
                <#else>
                    &nbsp;
                </#if>
            </td>
        </tr>
    <#-- toggle the row color -->
    <#assign alt_row = !alt_row>
    </#list>
    <#if ((requestParameters.groupByShippingMethod?exists && requestParameters.groupByShippingMethod == "Y") || (requestParameters.groupByWarehouseArea?exists && requestParameters.groupByWarehouseArea == "Y") || (requestParameters.groupByNoOfOrderItems?exists && requestParameters.groupByNoOfOrderItems == "Y"))>
        <tr<#if alt_row> class="alternate-row"</#if>>
            <th>${uiLabelMap.CommonAllMethods}</div></th>
    <td>&nbsp;</td>
    <td>&nbsp;</td>
    <th>${orderReadyToPickInfoListSizeTotal}</div></th>
    <th>${orderNeedsStockMoveInfoListSizeTotal}</div></th>
    <td>
        <#if (orderReadyToPickInfoListSizeTotal > 0)>
            <form method="post" action="<@ofbizUrl>createPicklistFromOrders</@ofbizUrl>" class="form-inline">
                <input type="hidden" name="facilityId" value="${facilityId?if_exists}"/>
                <div class="input-group">
                <span class="input-group-addon">${uiLabelMap.ProductPickFirst}</span>
                <input type="text" size="4" name="maxNumberOfOrders" value="20" class="form-control"/>
                </div>
                <input type="submit" value="${uiLabelMap.ProductCreatePicklist}" class="btn btn-primary btn-sm"/>
            </form>
        <#else>
            &nbsp;
        </#if>
    </td>
    <td>
        <#if (orderReadyToPickInfoListSizeTotal > 0)>
            <form method="post" action="<@ofbizUrl>printPickSheets</@ofbizUrl>" class="form-inline">
                <input type="hidden" name="facilityId" value="${facilityId?if_exists}"/>
                <div class="input-group">
                <span class="input-group-addon">${uiLabelMap.FormFieldTitle_printPickSheetFirst}</span>
                <input type="text" size="4" name="maxNumberOfOrdersToPrint" value="20" class="form-control"/>
                 </div>

                <input type="submit" value="${uiLabelMap.FormFieldTitle_printPickSheet}" class="btn btn-primary btn-sm"/>
            </form>
        <#else>
            &nbsp;
        </#if>
    </td>
    </tr>
    </#if>
<#else>
<tr>
    <td colspan="4"><h3>${uiLabelMap.ProductNoOrdersFoundReadyToPickOrNeedStockMoves}.</h3></td>
</tr>
</#if>
</table>
</div>

<#assign viewDetail = requestParameters.viewDetail?if_exists>
<#if viewDetail?has_content>
    <#list pickMoveInfoList as pickMoveInfo>
        <#assign groupName = pickMoveInfo.groupName?if_exists>
        <#if groupName?if_exists == viewDetail>
            <#assign toPickList = pickMoveInfo.orderReadyToPickInfoList?if_exists>
        </#if>
    </#list>
</#if>
<@htmlScreenTemplate.renderScreenletEnd/>
<#if toPickList?has_content>
<@htmlScreenTemplate.renderScreenletBegin id="" title="${uiLabelMap.ProductPickingDetail}"/>
<div class="table-responsive">
    <table class="table table-striped table-bordered">
        <tr class="header-row">
            <th>${uiLabelMap.ProductOrderId}</th>
            <th>${uiLabelMap.FormFieldTitle_orderDate}</th>
            <th>${uiLabelMap.ProductChannel}</th>
            <th>${uiLabelMap.ProductOrderItem}</th>
            <th>${uiLabelMap.ProductProductDescription}</th>
            <th>${uiLabelMap.ProductOrderShipGroupId}</th>
            <th>${uiLabelMap.ProductQuantity}</th>
            <th>${uiLabelMap.ProductQuantityNotAvailable}</th>
        </tr>
        <#assign alt_row = false>
        <#list toPickList as toPick>
            <#assign oiasgal = toPick.orderItemShipGrpInvResList>
            <#assign header = toPick.orderHeader>
            <#assign channel = header.getRelatedOne("SalesChannelEnumeration")?if_exists>
            <#list oiasgal as oiasga>
                <#assign orderProduct = oiasga.getRelatedOne("OrderItem").getRelatedOne("Product")?if_exists>
                <#assign product = oiasga.getRelatedOne("InventoryItem").getRelatedOne("Product")?if_exists>
                <tr valign="middle"<#if alt_row> class="alternate-row"</#if>>
                    <td><a href="/ordermgr/control/orderview?orderId=${oiasga.orderId}${externalKeyParam}" class="buttontext" target="_blank">${oiasga.orderId}</a></td>
                    <td>${header.orderDate?string('yyyy-MM-dd hh:mm')}</td>
                    <td>${(channel.description)?if_exists}</td>
                    <td>${oiasga.orderItemSeqId}</td>
                    <td>
                        <a href="/catalog/control/EditProduct?productId=${orderProduct.productId?if_exists}${externalKeyParam}" class="buttontext"
                           target="_blank">${(orderProduct.internalName)?if_exists}</a>
                        <#if orderProduct.productId != product.productId>
                            &nbsp;[<a href="/catalog/control/EditProduct?productId=${product.productId?if_exists}${externalKeyParam}" class="buttontext"
                                      target="_blank">${(product.internalName)?if_exists}</a>]
                        </#if>
                    </td>
                    <td>${oiasga.shipGroupSeqId}</td>
                    <td>${oiasga.quantity}</td>
                    <td>${oiasga.quantityNotAvailable?if_exists}</td>
                </tr>
            </#list>
        <#-- toggle the row color -->
            <#assign alt_row = !alt_row>
        </#list>
    </table>
</div>

</#if>
