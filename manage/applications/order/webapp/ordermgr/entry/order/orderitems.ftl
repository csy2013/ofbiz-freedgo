<#include "component://common/webcommon/includes/htmlScreenTemplate.ftl"/>
<div class="${panelStyle}">
    <div class="${panelHeadingStyle}">
        <div class="${panelHeadingBarStyle}">
            <#if maySelectItems?default(false)>
                <a href="javascript:document.addOrderToCartForm.add_all.value='true';document.addOrderToCartForm.submit()" class="btn btn-xs btn-icon btn-circle btn-success">${uiLabelMap.OrderAddAllToCart}</a>
                <a href="javascript:document.addOrderToCartForm.add_all.value='false';document.addOrderToCartForm.submit()" class="btn btn-xs btn-icon btn-circle btn-success">${uiLabelMap.OrderAddCheckedToCart}</a>
            </#if>
        </div>
        <h4 class="${panelTitleStyle}">${uiLabelMap.OrderOrderItems}</h4>
    </div>
    <div class="${panelBodyStyle}">
        <div class="table-responsive">
        <table width="100%" border="0" cellpadding="0">
          <tr valign="bottom">
            <td width="65%"><span><b>${uiLabelMap.ProductProduct}</b></span></td>
            <td width="5%" align="right"><span><b>${uiLabelMap.OrderQuantity}</b></span></td>
            <td width="10%" align="right"><span><b>${uiLabelMap.CommonUnitPrice}</b></span></td>
            <td width="10%" align="right"><span><b>${uiLabelMap.OrderAdjustments}</b></span></td>
            <td width="10%" align="right"><span><b>${uiLabelMap.OrderSubTotal}</b></span></td>
          </tr>
          <#list orderItems?if_exists as orderItem>
            <#assign itemType = orderItem.getRelatedOne("OrderItemType")?if_exists>
            <tr><td colspan="6"><hr /></td></tr>
            <tr>
              <#if orderItem.productId?exists && orderItem.productId == "_?_">
                <td colspan="1" valign="top">
                  <b><div> &gt;&gt; ${orderItem.itemDescription}</div></b>
                </td>
              <#else>
                <td valign="top">
                  <div>
                    <#if orderItem.productId?exists>
                      <a href="<@ofbizUrl>product?product_id=${orderItem.productId}</@ofbizUrl>" class="buttontext">${orderItem.productId} - ${orderItem.itemDescription}</a>
                    <#else>
                      <b>${itemType?if_exists.description?if_exists}</b> : ${orderItem.itemDescription?if_exists}
                    </#if>
                  </div>

                </td>
                <td align="right" valign="top">
                  <div nowrap="nowrap">${orderItem.quantity?string.number}</div>
                </td>
                <td align="right" valign="top">
                  <div nowrap="nowrap"><@ofbizCurrency amount=orderItem.unitPrice isoCode=currencyUomId/></div>
                </td>
                <td align="right" valign="top">
                  <div nowrap="nowrap"><@ofbizCurrency amount=localOrderReadHelper.getOrderItemAdjustmentsTotal(orderItem) isoCode=currencyUomId/></div>
                </td>
                <td align="right" valign="top" nowrap="nowrap">
                  <div><@ofbizCurrency amount=localOrderReadHelper.getOrderItemSubTotal(orderItem) isoCode=currencyUomId/></div>
                </td>
                <#if maySelectItems?default(false)>
                  <td>
                    <input name="item_id" value="${orderItem.orderItemSeqId}" type="checkbox" />
                  </td>
                </#if>
              </#if>
            </tr>
            <#-- show info from workeffort if it was a rental item -->
            <#if orderItem.orderItemTypeId?exists && orderItem.orderItemTypeId == "RENTAL_ORDER_ITEM">
                <#assign WorkOrderItemFulfillments = orderItem.getRelated("WorkOrderItemFulfillment")?if_exists>
                <#if WorkOrderItemFulfillments?has_content>
                    <#list WorkOrderItemFulfillments as WorkOrderItemFulfillment>
                        <#assign workEffort = WorkOrderItemFulfillment.getRelatedOneCache("WorkEffort")?if_exists>
                          <tr><td>&nbsp;</td><td>&nbsp;</td><td colspan="8"><div>${uiLabelMap.CommonFrom}: ${workEffort.estimatedStartDate?string("yyyy-MM-dd")} ${uiLabelMap.CommonTo}: ${workEffort.estimatedCompletionDate?string("yyyy-MM-dd")} ${uiLabelMap.OrderNbrPersons}: ${workEffort.reservPersons}</div></td></tr>
                        <#break><#-- need only the first one -->
                    </#list>
                </#if>
            </#if>

            <#-- now show adjustment details per line item -->
            <#assign itemAdjustments = localOrderReadHelper.getOrderItemAdjustments(orderItem)>
            <#list itemAdjustments as orderItemAdjustment>
              <tr>
                <td align="right">
                  <div style="font-size: xx-small;">
                    <b><i>${uiLabelMap.OrderAdjustment}</i>:</b> <b>${localOrderReadHelper.getAdjustmentType(orderItemAdjustment)}</b>&nbsp;
                    <#if orderItemAdjustment.description?has_content>: ${StringUtil.wrapString(orderItemAdjustment.get("description",locale))}</#if>

                    <#if orderItemAdjustment.orderAdjustmentTypeId == "SALES_TAX">
                      <#if orderItemAdjustment.primaryGeoId?has_content>
                        <#assign primaryGeo = orderItemAdjustment.getRelatedOneCache("PrimaryGeo")/>
                        <#if primaryGeo.geoName?has_content>
                            <b>${uiLabelMap.OrderJurisdiction}:</b> ${primaryGeo.geoName} [${primaryGeo.abbreviation?if_exists}]
                        </#if>
                        <#if orderItemAdjustment.secondaryGeoId?has_content>
                          <#assign secondaryGeo = orderItemAdjustment.getRelatedOneCache("SecondaryGeo")/>
                          (<b>in:</b> ${secondaryGeo.geoName} [${secondaryGeo.abbreviation?if_exists}])
                        </#if>
                      </#if>
                      <#if orderItemAdjustment.sourcePercentage?exists><b>${uiLabelMap.OrderRate}:</b> ${orderItemAdjustment.sourcePercentage}%</#if>
                      <#if orderItemAdjustment.customerReferenceId?has_content><b>${uiLabelMap.OrderCustomerTaxId}:</b> ${orderItemAdjustment.customerReferenceId}</#if>
                      <#if orderItemAdjustment.exemptAmount?exists><b>${uiLabelMap.OrderExemptAmount}:</b> ${orderItemAdjustment.exemptAmount}</#if>
                    </#if>
                  </div>
                </td>
                <td>&nbsp;</td>
                <td>&nbsp;</td>
                <td align="right">
                  <div style="font-size: xx-small;"><@ofbizCurrency amount=localOrderReadHelper.getOrderItemAdjustmentTotal(orderItem, orderItemAdjustment) isoCode=currencyUomId/></div>
                </td>
                <td>&nbsp;</td>
                <td>&nbsp;</td>
                <#if maySelectItems?default(false)><td>&nbsp;</td></#if>
              </tr>
            </#list>
           </#list>
           <#if !orderItems?has_content>
             <tr><td><font color="red">${uiLabelMap.checkhelpertotalsdonotmatchordertotal}</font></td></tr>
           </#if>

          <tr><td colspan="8"><hr /></td></tr>
          <tr>
            <td align="right" colspan="4"><div><b>${uiLabelMap.OrderSubTotal}</b></div></td>
            <td align="right" nowrap="nowrap"><div>&nbsp;<#if orderSubTotal?exists><@ofbizCurrency amount=orderSubTotal isoCode=currencyUomId/></#if></div></td>
          </tr>
          <#list headerAdjustmentsToShow?if_exists as orderHeaderAdjustment>
            <tr>
              <td align="right" colspan="4"><div><b>${localOrderReadHelper.getAdjustmentType(orderHeaderAdjustment)}:${orderHeaderAdjustment.get("description")?if_exists}</b></div></td>
              <td align="right" nowrap="nowrap"><div><@ofbizCurrency amount=localOrderReadHelper.getOrderAdjustmentTotal(orderHeaderAdjustment) isoCode=currencyUomId/></div></td>
            </tr>
          </#list>
          <tr>
            <td align="right" colspan="4"><div><b>${uiLabelMap.FacilityShippingAndHandling}</b></div></td>
            <td align="right" nowrap="nowrap"><div><#if orderShippingTotal?exists><@ofbizCurrency amount=orderShippingTotal isoCode=currencyUomId/></#if></div></td>
          </tr>
          <tr>
            <td align="right" colspan="4"><div><b>${uiLabelMap.OrderSalesTax}</b></div></td>
            <td align="right" nowrap="nowrap"><div><#if orderTaxTotal?exists><@ofbizCurrency amount=orderTaxTotal isoCode=currencyUomId/></#if></div></td>
          </tr>

          <tr><td colspan=2></td><td colspan="8"><hr /></td></tr>
          <tr>
            <td align="right" colspan="4"><div><b>${uiLabelMap.OrderGrandTotal}</b></div></td>
            <td align="right" nowrap="nowrap">
              <div><#if orderGrandTotal?exists><@ofbizCurrency amount=orderGrandTotal isoCode=currencyUomId/></#if></div>
            </td>
          </tr>
        </table>
    </div>
</div>
