
<#if shipmentItemDatas?has_content>

    <div class="table-responsive">
        <table class="table table-bordered table-striped">
        <tr class="header-row">
          <th>${uiLabelMap.ProductItem}</th>
          <th>&nbsp;</th>
          <th>&nbsp;</th>
          <th>${uiLabelMap.ProductQuantity}</th>
          <th>&nbsp;</th>
          <th>&nbsp;</th>
        </tr>
        <#assign alt_row = false>
        <#list shipmentItemDatas as shipmentItemData>
            <#assign shipmentItem = shipmentItemData.shipmentItem>
            <#assign itemIssuances = shipmentItemData.itemIssuances>
            <#assign orderShipments = shipmentItemData.orderShipments>
            <#assign shipmentPackageContents = shipmentItemData.shipmentPackageContents>
            <#assign product = shipmentItemData.product?if_exists>
           <#-- <tr valign="middle"<#if alt_row> class="alternate-row"</#if>>
                <td>${shipmentItem.shipmentItemSeqId}</td>
                <td colspan="2">${(product.internalName)?if_exists} <a href="/catalog/control/EditProduct?productId=${shipmentItem.productId?if_exists}" class="buttontext">${shipmentItem.productId?if_exists}</a></td>
                <td>${shipmentItem.quantity?default("&nbsp;")}</td>
                <td colspan="2">${shipmentItem.shipmentContentDescription?default("&nbsp;")}</td>
            </tr>-->
          <#--  <#list orderShipments as orderShipment>
                <tr valign="middle"<#if alt_row> class="alternate-row"</#if>>
                    <td>&nbsp;</td>
                    <td><span>${uiLabelMap.ProductOrderItem}</span> <a href="/ordermgr/control/orderview?orderId=${orderShipment.orderId?if_exists}&amp;externalLoginKey=${requestAttributes.externalLoginKey}" class="buttontext">${orderShipment.orderId?if_exists}</a>${orderShipment.orderItemSeqId?if_exists}</td>
                    <td>&nbsp;</td>
                    <td>${orderShipment.quantity?if_exists}</td>
                    <td>&nbsp;</td>
                    <td>&nbsp;</td>
                </tr>
            </#list>-->
            <#list itemIssuances as itemIssuance>
                <tr valign="middle"<#if alt_row> class="alternate-row"</#if>>
                    <td>&nbsp;</td>
                    <td><span>${uiLabelMap.ProductOrderItem}</span> <a href="/ordermgr/control/orderview?orderId=${itemIssuance.orderId?if_exists}&amp;externalLoginKey=${requestAttributes.externalLoginKey}" class="buttontext">${itemIssuance.orderId?if_exists}</a>${itemIssuance.orderItemSeqId?if_exists}</td>
                    <td><span>${uiLabelMap.ProductInventory}</span> <a href="<@ofbizUrl>EditInventoryItem?inventoryItemId=${itemIssuance.inventoryItemId?if_exists}</@ofbizUrl>" class="buttontext">${itemIssuance.inventoryItemId?if_exists}</a></td>
                    <td>${itemIssuance.quantity?if_exists}</td>
                    <td>${itemIssuance.issuedDateTime?if_exists}</td>
                    <td>&nbsp;</td>
                </tr>
            </#list>

            <#-- toggle the row color -->
            <#assign alt_row = !alt_row>
        </#list>
      </table>
</div>
</#if>