

<#if shipment?exists>
<#--<div class="screenlet">
    <div class="screenlet-title-bar">
        <ul>
            <li class="h3">${uiLabelMap.PageTitleEditShipmentItems}</li>
        </ul>
        <br class="clear"/>
    </div>
    <div class="screenlet-body">-->
        <@htmlScreenTemplate.renderScreenletBegin id="" title="${uiLabelMap.PageTitleEditShipmentItems}"/>
        <table cellspacing="0" class="table table-bordered">
            <tr class="header-row">
                <td>${uiLabelMap.ProductItem}</td>
                <td>&nbsp;</td>
                <td>&nbsp;</td>
                <td>${uiLabelMap.ProductQuantity}</td>
                <td>&nbsp;</td>
                <td>&nbsp;</td>
                <td>&nbsp;</td>
            </tr>
        <#assign alt_row = false>
        <#list shipmentItemDatas as shipmentItemData>
            <#assign shipmentItem = shipmentItemData.shipmentItem>
            <#assign itemIssuances = shipmentItemData.itemIssuances>
            <#assign orderShipments = shipmentItemData.orderShipments>
            <#assign shipmentPackageContents = shipmentItemData.shipmentPackageContents>
            <#assign product = shipmentItemData.product?if_exists>
            <#assign totalQuantityPackaged = shipmentItemData.totalQuantityPackaged>
            <#assign totalQuantityToPackage = shipmentItemData.totalQuantityToPackage>
            <tr valign="middle"<#if alt_row> class="alternate-row"</#if>>
                <td>${shipmentItem.shipmentItemSeqId}</td>
                <td colspan="2">${(product.internalName)?if_exists} <a class="btn btn-primary btn-sm" href="/catalog/control/EditProduct?productId=${shipmentItem.productId?if_exists}" class="buttontext">${shipmentItem.productId?if_exists}</a></td>
                <td>${shipmentItem.quantity?default("&nbsp;")}</td>
                <td colspan="2">${shipmentItem.shipmentContentDescription?default("&nbsp;")}</td>
                <td><a class="btn btn-primary btn-sm" href="javascript:document.deleteShipmentItem${shipmentItemData_index}.submit();" class="buttontext">${uiLabelMap.CommonDelete}</a></td>
            </tr>
            <form name="deleteShipmentItem${shipmentItemData_index}" method="post" action="<@ofbizUrl>deleteShipmentItem</@ofbizUrl>">
                <input type="hidden" name="shipmentId" value="${shipmentId}"/>
                <input type="hidden" name="shipmentItemSeqId" value="${shipmentItem.shipmentItemSeqId}"/>
            </form>
            <#list orderShipments as orderShipment>
                <tr valign="middle"<#if alt_row> class="alternate-row"</#if>>
                    <td>&nbsp;</td>
                    <td><span class="label1">${uiLabelMap.ProductOrderItem}</span> <a class="btn btn-primary btn-sm" href="/ordermgr/control/orderview?orderId=${orderShipment.orderId?if_exists}" class="buttontext">${orderShipment.orderId?if_exists}</a> ${orderShipment.orderItemSeqId?if_exists}</td>
                    <td>&nbsp;</td>
                    <td>${orderShipment.quantity?if_exists}</td>
                    <td>&nbsp;</td>
                    <td>&nbsp;</td>
                    <td>&nbsp;<#-- don't allow a delete, need to implement a cancel issuance <a class="btn btn-primary btn-sm" href="<@ofbizUrl>deleteShipmentItemIssuance?shipmentId=${shipmentId}&amp;itemIssuanceId=${itemIssuance.itemIssuanceId}</@ofbizUrl>" class="buttontext">${uiLabelMap.CommonDelete}</a> --></td>
                </tr>
            </#list>
            <#list itemIssuances as itemIssuance>
                <tr valign="middle"<#if alt_row> class="alternate-row"</#if>>
                    <td>&nbsp;</td>
                    <td><span class="label1">${uiLabelMap.ProductOrderItem}</span> <a class="btn btn-primary btn-sm" href="/ordermgr/control/orderview?orderId=${itemIssuance.orderId?if_exists}" class="buttontext">${itemIssuance.orderId?if_exists}</a> ${itemIssuance.orderItemSeqId?if_exists}</td>
                    <td><span class="label1">${uiLabelMap.ProductInventory}</span> <a class="btn btn-primary btn-sm" href="<@ofbizUrl>EditInventoryItem?inventoryItemId=${itemIssuance.inventoryItemId?if_exists}</@ofbizUrl>" class="buttontext">${itemIssuance.inventoryItemId?if_exists}</a></td>
                    <td>${itemIssuance.quantity?if_exists}</td>
                    <td>${itemIssuance.issuedDateTime?if_exists}</td>
                    <td class="label1">${uiLabelMap.ProductFuturePartyRoleList}</td>
                    <td>&nbsp;<#-- don't allow a delete, need to implement a cancel issuance <a class="btn btn-primary btn-sm" href="<@ofbizUrl>deleteShipmentItemIssuance?shipmentId=${shipmentId}&amp;itemIssuanceId=${itemIssuance.itemIssuanceId}</@ofbizUrl>" class="buttontext">${uiLabelMap.CommonDelete}</a> --></td>
                </tr>
            </#list>
            <#list shipmentPackageContents as shipmentPackageContent>
                <tr valign="middle"<#if alt_row> class="alternate-row"</#if>>
                    <td>&nbsp;</td>
                    <td colspan="2"><span class="label1">${uiLabelMap.ProductPackage}</span> ${shipmentPackageContent.shipmentPackageSeqId}</td>
                    <td>${shipmentPackageContent.quantity?if_exists}&nbsp;</td>
                    <#if shipmentPackageContent.subProductId?has_content>
                    <td><span class="label1">${uiLabelMap.ProductSubProduct}</span> ${shipmentPackageContent.subProductId}</td>
                    <td>${shipmentPackageContent.subProductQuantity?if_exists}</td>
                    <#else>
                    <td colspan="2">&nbsp;</td>
                    </#if>
                    <td><a class="btn btn-primary btn-sm" href="javascript:document.deleteShipmentItemPackageContent${shipmentItemData_index}${shipmentPackageContent_index}.submit();" class="buttontext">${uiLabelMap.CommonDelete}</a></td>
                </tr>
                <form name="deleteShipmentItemPackageContent${shipmentItemData_index}${shipmentPackageContent_index}" method="post" action="<@ofbizUrl>deleteShipmentItemPackageContent</@ofbizUrl>">
                    <input type="hidden" name="shipmentId" value="${shipmentId}"/>
                    <input type="hidden" name="shipmentItemSeqId" value="${shipmentPackageContent.shipmentItemSeqId}"/>
                    <input type="hidden" name="shipmentPackageSeqId" value="${shipmentPackageContent.shipmentPackageSeqId}"/>
                </form>
            </#list>
            <#if (totalQuantityToPackage > 0)>
                <tr valign="middle"<#if alt_row> class="alternate-row"</#if>>
                    <form action="<@ofbizUrl>createShipmentItemPackageContent</@ofbizUrl>" method="post" name="createShipmentPackageContentForm${shipmentItemData_index}">
                    <input type="hidden" name="shipmentId" value="${shipmentId}"/>
                    <input type="hidden" name="shipmentItemSeqId" value="${shipmentItem.shipmentItemSeqId}"/>
                    <td>&nbsp;</td>
                    <td colspan="2">
                        <div><span class="label1">${uiLabelMap.ProductAddToPackage}</span>
                        <select name="shipmentPackageSeqId">
                            <#list shipmentPackages as shipmentPackage>
                                <option>${shipmentPackage.shipmentPackageSeqId}</option>
                            </#list>
                            <option value="New">${uiLabelMap.CommonNew}</option><!-- Warning: the "New" value cannot be translated because it is used in secas -->
                        </select>
                        </div>
                    </td>
                    <td>
                        <div>
                            <input type="text" name="quantity" size="5" value="${totalQuantityToPackage}"/>
                            <a class="btn btn-primary btn-sm" href="javascript:document.createShipmentPackageContentForm${shipmentItemData_index}.submit()" class="buttontext">${uiLabelMap.CommonAdd}</a>
                        </div>
                    </td>
                    <td colspan="2">&nbsp;</td>
                    <td>&nbsp;</td>
                    </form>
                </tr>
            </#if>
            <#-- toggle the row color -->
            <#assign alt_row = !alt_row>
        </#list>
        <tr>
            <form action="<@ofbizUrl>createShipmentItem</@ofbizUrl>" method="post" name="createShipmentItemForm">
                <input type="hidden" name="shipmentId" value="${shipmentId}"/>
                <td><span class="label1">${uiLabelMap.ProductNewItem}</span></td>
                <td colspan="2"><span class="label1">${uiLabelMap.ProductProductId}</span> 
                  <@htmlTemplate.lookupField formName="createShipmentItemForm" name="productId" id="productId" fieldFormName="LookupProduct"/>
                </td>
                <td><input type="text" name="quantity" size="5" value="0"/></td>
                <td colspan="2"><span class="label1">${uiLabelMap.ProductProductDescription}</span> <input name="shipmentContentDescription" size="30" maxlength="255"/></td>
                <td><a class="btn btn-primary btn-sm" href="javascript:document.createShipmentItemForm.submit()" class="buttontext">${uiLabelMap.CommonCreate}</a></td>
            </form>
        </tr>
        </table>
  <@htmlScreenTemplate.renderScreenletEnd/>
<#else> 

        <@htmlScreenTemplate.renderScreenletBegin id="" title="${uiLabelMap.ProductShipmentNotFoundId} : [${shipmentId?if_exists}]"/>
        <@htmlScreenTemplate.renderScreenletEnd/>
</#if>