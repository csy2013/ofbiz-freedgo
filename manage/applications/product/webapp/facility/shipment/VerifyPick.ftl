
<#if security.hasEntityPermission("FACILITY", "_VIEW", session)>
  <#assign showInput = "Y">
    <@htmlScreenTemplate.renderScreenletBegin id="" title="${facility.facilityName?if_exists} [${facility.facilityId?if_exists}]下的${uiLabelMap.OrderOrder}"/>
    <#if (shipmentId?has_content) || (isOrderStatusApproved == false)>
      <#assign showInput = "N">
    </#if>
    <#if shipmentId?has_content>
      <div>
        <span class="label">${uiLabelMap.ProductShipmentId}</span><a href="<@ofbizUrl>/ViewShipment?shipmentId=${shipmentId}</@ofbizUrl>" class="btn btn-primary btn-sm">${shipmentId}</a>
      </div>
      <#if invoiceIds?exists && invoiceIds?has_content>
        <div>
          <span class="label">${uiLabelMap.AccountingInvoices}:</span>
          <ul>
            <#list invoiceIds as invoiceId>
              <li>
                ${uiLabelMap.CommonNbr}<a href="/accounting/control/invoiceOverview?invoiceId=${invoiceId}&amp;externalLoginKey=${externalLoginKey}" target="_blank" class="buttontext">${invoiceId}</a>
                (<a href="/accounting/control/invoice.pdf?invoiceId=${invoiceId}&amp;externalLoginKey=${externalLoginKey}" target="_blank" class="buttontext">PDF</a>)
              </li>
            </#list>
          </ul>
        </div>
      </#if>
    </#if>
    <form name="selectOrderForm" method="post" action="<@ofbizUrl>VerifyPick</@ofbizUrl>" class="form-inline">
        <input type="hidden" name="facilityId" value="${facility.facilityId?if_exists}"/>
        <div class="form-group">
          <div class="input-group m-b-10">
             <span class="input-group-addon">${uiLabelMap.ProductOrderId}</span>
             <#if shipmentId?has_content>
                <input type="text" name="orderId" size="20" maxlength="20" value="" class="form-control"/>
              <#else>
                <input type="text" name="orderId" size="20" maxlength="20" value="${orderId?if_exists}" class="form-control"/>
              </#if>
              <span class="input-group-addon">/</span>
                <input type="text" name="shipGroupSeqId" size="6" maxlength="6" value="${shipGroupSeqId?default("00001")}" class="form-control"/>
             </div>
            <input type="submit" value="${uiLabelMap.ProductVerify}&nbsp;${uiLabelMap.OrderOrder}" class="btn btn-primary btn-sm m-b-10"/>
        </div>
      </form>

      <!-- select picklist bin form -->
      <form name="selectPicklistBinForm" method="post" action="<@ofbizUrl>VerifyPick</@ofbizUrl>" class="form-inline">
        <input type="hidden" name="facilityId" value="${facility.facilityId?if_exists}"/>
        <div class="form-group">
         <div class="input-group m-b-10">
             <span class="input-group-addon">${uiLabelMap.FormFieldTitle_picklistBinId}</span>
             <input type="text" name="picklistBinId" size="29" maxlength="60" value="${picklistBinId?if_exists}" class="form-control"/>
             </div>
              <input type="submit" value="${uiLabelMap.ProductVerify}&nbsp;${uiLabelMap.OrderOrder}" class="btn btn-primary btn-sm m-b-10"/>
        </div>
      </form>
      <form name="clearPickForm" method="post" action="<@ofbizUrl>cancelAllRows</@ofbizUrl>">
        <input type="hidden" name="orderId" value="${orderId?if_exists}"/>
        <input type="hidden" name="shipGroupSeqId" value="${shipGroupSeqId?if_exists}"/>
        <input type="hidden" name="facilityId" value="${facility.facilityId?if_exists}"/>
      </form>

  <#if showInput != "N" && orderHeader?exists && orderHeader?has_content>
      <@htmlScreenTemplate.renderScreenletBegin id="" title="${uiLabelMap.ProductOrderId} ${uiLabelMap.CommonNbr}"/>
      <a href="/ordermgr/control/orderview?orderId=${orderId}">${orderId}</a> / ${uiLabelMap.ProductOrderShipGroupId} #${shipGroupSeqId}
      <#if orderItemShipGroup?has_content>
          <#assign postalAddress = orderItemShipGroup.getRelatedOne("PostalAddress")>
          <#assign carrier = orderItemShipGroup.carrierPartyId?default("N/A")>
      <div class="table-responsive">
      <table class="table table-striped table-bordered">
          <tr>
              <th class="table-td-valign-top">
                <h4 class="text-info">${uiLabelMap.ProductShipToAddress}</h4>
                <br />
                ${uiLabelMap.CommonTo}: ${postalAddress.toName?default("")}
                <br />
                <#if postalAddress.attnName?has_content>
                  ${uiLabelMap.CommonAttn}: ${postalAddress.attnName}
                  <br />
                </#if>
                ${postalAddress.address1}
                <br />
                <#if postalAddress.address2?has_content>
                  ${postalAddress.address2}
                  <br />
                </#if>
                ${postalAddress.city?if_exists}, ${postalAddress.stateProvinceGeoId?if_exists} ${postalAddress.postalCode?if_exists}
                <br />
                ${postalAddress.countryGeoId?if_exists}
                <br />
              </th>
              <th>&nbsp;</th>
              <th class="table-td-valign-top">
                <h4 class="text-info">${uiLabelMap.ProductCarrierShipmentMethod}</h4>
                <br />
                <#if carrier == "USPS">
                  <#assign color = "red">
                <#elseif carrier == "UPS">
                  <#assign color = "green">
                <#else>
                  <#assign color = "black">
                </#if>
                <#if carrier != "_NA_">
                  <font color="${color}">${carrier}</font>
                  &nbsp;
                </#if>
                ${orderItemShipGroup.shipmentMethodTypeId?default("??")}
              </th>
              <th>&nbsp;</th>
              <th class="table-td-valign-top">
                <h4 class="text-info">${uiLabelMap.OrderInstructions}</h4>
                <br />
                ${orderItemShipGroup.shippingInstructions?default("(${uiLabelMap.CommonNone})")}
              </th>
            </tr>
          </table>
      </div>
        </#if>
        <hr />
        <form name="singlePickForm" method="post" action="<@ofbizUrl>processVerifyPick</@ofbizUrl>" class="form-inline">
          <input type="hidden" name="orderId" value="${orderId?if_exists}"/>
          <input type="hidden" name="shipGroupSeqId" value="${shipGroupSeqId?if_exists}"/>
          <input type="hidden" name="facilityId" value="${facility.facilityId?if_exists}"/>
            <div class="form-group">
                  <div class="input-group"> <span class="input-group-addon">${uiLabelMap.ProductProductNumber}</span>
                  <input type="text" name="productId" size="20" maxlength="20" value="" class="form-control"/>
                  <span class="input-group-addon">@</span>
                  <input type="text" name="quantity" size="6" maxlength="6" value="1" class="form-control"/>
                  </div>
                  <input type="submit" value="${uiLabelMap.ProductVerify}&nbsp;${uiLabelMap.OrderItem}" class="btn btn-primary btn-sm"/>
                </div>
        </form>
        <br />
        <#assign orderItems = orderItems?if_exists>
        <form name="multiPickForm" method="post" action="<@ofbizUrl>processBulkVerifyPick</@ofbizUrl>" class="form-inline">
          <input type="hidden" name="facilityId" value="${facility.facilityId?if_exists}"/>
          <input type="hidden" name="userLoginId" value="${userLoginId?if_exists}"/>
          <input type="hidden" name="orderId" value="${orderId?if_exists}"/>
          <input type="hidden" name="shipGroupSeqId" value="${shipGroupSeqId?if_exists}"/>
            <div class="table-responsive">
            <table class="table table-striped table-bordered">
            <tr class="header-row">
              <th>&nbsp;</th>
              <th>${uiLabelMap.ProductItem} ${uiLabelMap.CommonNbr}</th>
              <th>${uiLabelMap.ProductProductId}</th>
              <th>${uiLabelMap.ProductInternalName}</th>
              <th>${uiLabelMap.ProductCountryOfOrigin}</th>
              <th align="right">${uiLabelMap.ProductOrderedQuantity}</th>
              <th align="right">${uiLabelMap.ProductVerified}&nbsp;${uiLabelMap.CommonQuantity}</th>
              <th align="center">${uiLabelMap.CommonQty}&nbsp;${uiLabelMap.CommonTo}&nbsp;${uiLabelMap.ProductVerify}</th>
            </tr>
            <#if orderItems?has_content>
              <#assign rowKey = 1>
              <#assign counter = 1>
              <#assign isShowVerifyItemButton = "false">
              <#list orderItems as orderItem>
                <#assign orderItemSeqId = orderItem.orderItemSeqId?if_exists>
                <#assign readyToVerify = verifyPickSession.getReadyToVerifyQuantity(orderId,orderItemSeqId)>
                <#assign orderItemQuantity = orderItem.getBigDecimal("quantity")>
                <#assign verifiedQuantity = 0.000000>
                <#assign shipments = delegator.findByAnd("Shipment", Static["org.ofbiz.base.util.UtilMisc"].toMap("primaryOrderId", orderItem.getString("orderId"), "statusId", "SHIPMENT_PICKED"))/>
                <#if (shipments?has_content)>
                  <#list shipments as shipment>
                    <#assign itemIssuances = delegator.findByAnd("ItemIssuance", Static["org.ofbiz.base.util.UtilMisc"].toMap("shipmentId", shipment.getString("shipmentId"), "orderItemSeqId", orderItemSeqId))/>
                    <#if itemIssuances?has_content>
                      <#list itemIssuances as itemIssuance>
                        <#assign verifiedQuantity = verifiedQuantity + itemIssuance.getBigDecimal("quantity")>
                      </#list>
                    </#if>
                  </#list>
                </#if>
                <#if verifiedQuantity == orderItemQuantity>
                  <#assign counter = counter +1>
                </#if>
                <#assign orderItemQuantity = orderItemQuantity.subtract(verifiedQuantity)>
                <#assign product = orderItem.getRelatedOne("Product")?if_exists/>
                <tr>
                  <#if (orderItemQuantity.compareTo(readyToVerify) > 0) >
                    <td><input type="checkbox" name="sel_${rowKey}" value="Y" checked="" class="checkbox-inline"/></td>
                    <#assign isShowVerifyItemButton = "true">
                  <#else>
                    <td>&nbsp;</td>
                  </#if>
                  <td>${orderItemSeqId?if_exists}</td>
                  <td>${product.productId?default("N/A")}</td>
                  <td>
                    <a href="/catalog/control/EditProduct?productId=${product.productId?if_exists}${externalKeyParam}" class="buttontext" target="_blank">${(product.internalName)?if_exists}</a>
                  </td>
                  <td>
                    <select name="geo_${rowKey}" class="form-control">
                      <#if product.originGeoId?has_content>
                        <#assign originGeoId = product.originGeoId>
                        <#assign geo = delegator.findOne("Geo", Static["org.ofbiz.base.util.UtilMisc"].toMap("geoId", originGeoId), true)>
                        <option value="${originGeoId}">${geo.geoName?if_exists}</option>
                        <option value="${originGeoId}">---</option>
                      </#if>
                      <option value=""></option>
                      ${screens.render("component://common/widget/CommonScreens.xml#countries")}
                    </select>
                  </td>
                  <td align="right">${orderItemQuantity?if_exists}</td>
                  <td align="right">${readyToVerify?if_exists}</td>
                  <td align="center">
                    <#if (orderItemQuantity.compareTo(readyToVerify) > 0)>
                      <#assign qtyToVerify = orderItemQuantity.subtract(readyToVerify) >
                      <input type="text" size="7" name="qty_${rowKey}" value="${qtyToVerify?if_exists}" class="form-control"/>
                    <#else>
                      0
                    </#if>
                  </td>
                  <input type="hidden" name="prd_${rowKey}" value="${(orderItem.productId)?if_exists}"/>
                  <input type="hidden" name="ite_${rowKey}" value="${(orderItem.orderItemSeqId)?if_exists}"/>
                </tr>
                <#assign workOrderItemFulfillments = orderItem.getRelated("WorkOrderItemFulfillment")/>
                <#if workOrderItemFulfillments?has_content>
                  <#assign workOrderItemFulfillment = Static["org.ofbiz.entity.util.EntityUtil"].getFirst(workOrderItemFulfillments)/>
                  <#if workOrderItemFulfillment?has_content>
                    <#assign workEffort = workOrderItemFulfillment.getRelatedOne("WorkEffort")/>
                    <#if workEffort?has_content>
                      <#assign workEffortTask = Static["org.ofbiz.entity.util.EntityUtil"].getFirst(delegator.findByAnd("WorkEffort", Static["org.ofbiz.base.util.UtilMisc"].toMap("workEffortParentId", workEffort.workEffortId)))/>
                      <#if workEffortTask?has_content>
                        <#assign workEffortInventoryAssigns = workEffortTask.getRelated("WorkEffortInventoryAssign")/>
                        <#if workEffortInventoryAssigns?has_content>
                          <tr>
                            <th colspan="8">
                              ${uiLabelMap.OrderMarketingPackageComposedBy}
                            </th>
                          </tr>
                          <tr><td colspan="8"><hr /></td></tr>
                          <#list workEffortInventoryAssigns as workEffortInventoryAssign>
                            <#assign inventoryItem = workEffortInventoryAssign.getRelatedOne("InventoryItem")/>
                            <#assign product = inventoryItem.getRelatedOne("Product")/>
                            <tr>
                              <td colspan="2"></td>
                              <td>${product.productId?default("N/A")}</td>
                              <td>${product.internalName?if_exists}</td>
                              <td></td>
                              <td align="right">${workEffortInventoryAssign.quantity?if_exists}</td>
                            </tr>
                          </#list>
                          <tr><td colspan="8"><hr /></td></tr>
                        </#if>
                      </#if>
                    </#if>
                  </#if>
                </#if>
                <#assign rowKey = rowKey + 1>
              </#list>
            </#if>
            <tr>
              <td colspan="10">&nbsp;</td>
            </tr>
            <tr>
              <td colspan="12" align="right">
                <#if isShowVerifyItemButton?default("true") == "true">
                  <input type="submit" value="${uiLabelMap.ProductVerify}&nbsp;${uiLabelMap.OrderItems}" class="btn btn-primary btn-sm"/>
                </#if>
                &nbsp;
                <#if rowKey != counter>
                  <input type="button" value="${uiLabelMap.CommonCancel}" onclick="document.clearPickForm.submit();" class="btn btn-primary btn-sm"/>
                </#if>
              </td>
            </tr>
          </table>
           </div>
        </form>
        <br />
    <@htmlScreenTemplate.renderScreenletEnd/>
    <#assign orderId = orderId?if_exists >
    <#assign pickRows = verifyPickSession.getPickRows(orderId)?if_exists>
    <form name="completePickForm" method="post" action="<@ofbizUrl>completeVerifiedPick</@ofbizUrl>" class="form-inline">
      <input type="hidden" name="orderId" value="${orderId?if_exists}"/>
      <input type="hidden" name="shipGroupSeqId" value="${shipGroupSeqId?if_exists}"/>
      <input type="hidden" name="facilityId" value="${facility.facilityId?if_exists}"/>
      <input type="hidden" name="userLoginId" value="${userLoginId?if_exists}"/>
      <#if pickRows?has_content>
          <@htmlScreenTemplate.renderScreenletBegin id="" title="${uiLabelMap.ProductVerified}&nbsp;${uiLabelMap.OrderItems} : ${pickRows.size()?if_exists}"/>
      <div class="table-responsive">
          <table class="table table-striped table-bordered">
              <tr class="header-row">
                <th>${uiLabelMap.ProductItem} ${uiLabelMap.CommonNbr}</th>
                <th>${uiLabelMap.ProductProductId}</th>
                <th>${uiLabelMap.ProductInventoryItem} ${uiLabelMap.CommonNbr}</th>
                <th align="right">${uiLabelMap.ProductVerified}&nbsp;${uiLabelMap.CommonQuantity}</th>
                <th>&nbsp;</th>
              </tr>
              <#list pickRows as pickRow>
                <#if (pickRow.getOrderId()?if_exists).equals(orderId)>
                  <tr>
                    <td>${pickRow.getOrderItemSeqId()?if_exists}</td>
                    <td>${pickRow.getProductId()?if_exists}</td>
                    <td>${pickRow.getInventoryItemId()?if_exists}</td>
                    <td align="right">${pickRow.getReadyToVerifyQty()?if_exists}</td>
                  </tr>
                </#if>
              </#list>
            </table>
          </div>
            <div align="right">
              <a href="javascript:document.completePickForm.submit()" class="buttontext">${uiLabelMap.ProductComplete}</a>
            </div>
          <@htmlScreenTemplate.renderScreenletEnd/>
      </#if>
    </form>
  </#if>
  <#if orderId?has_content>
    <script language="javascript" type="text/javascript">
      document.singlePickForm.productId.focus();
    </script>
  <#else>
    <script language="javascript" type="text/javascript">
      document.selectOrderForm.orderId.focus();
    </script>
  </#if>
  <#if shipmentId?has_content>
    <script language="javascript" type="text/javascript">
      document.selectOrderForm.orderId.focus();
    </script>
  </#if>
<#else>
  <h4>${uiLabelMap.ProductFacilityViewPermissionError}</h4>
</#if>
<@htmlScreenTemplate.renderScreenletEnd/>
