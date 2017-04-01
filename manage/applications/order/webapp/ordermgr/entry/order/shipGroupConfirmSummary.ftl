<#if !(cart?exists)><#assign cart = shoppingCart?if_exists/></#if>
<#if cart?exists>
<#--<div class="panel panel-default">
    <div class="panel-heading">
        <h4 class="panel-title">${uiLabelMap.OrderShippingInformation}</h4>
    </div>
    <div class="panel-body">-->
        <@htmlScreenTemplate.renderScreenletBegin id="OrderShippingInformationPanel" title="${uiLabelMap.OrderShippingInformation}"/>
        <div class="table-responsive">
            <table class="table table-bordered">
            <#-- header -->

                <tr>
                    <td><span>${uiLabelMap.OrderDestination}</span></td>
                    <td><span>${uiLabelMap.PartySupplier}</span></td>
                    <td><span>${uiLabelMap.ProductShipmentMethod}</span></td>
                    <td><span>${uiLabelMap.ProductItem}</span></td>
                    <td><span>${uiLabelMap.ProductQuantity}</span></td>
                </tr>


            <#-- BEGIN LIST SHIP GROUPS -->
            <#--
            The structure of this table is one row per line item, grouped by ship group.
            The address column spans a number of rows equal to the number of items of its group.
            -->

                <#list cart.getShipGroups() as cartShipInfo>
                    <#assign numberOfItems = cartShipInfo.getShipItems().size()>
                    <#if (numberOfItems > 0)>

                    <#-- spacer goes here -->


                    <tr>

                    <#-- address destination column (spans a number of rows = number of cart items in it) -->

                        <td rowspan="${numberOfItems}">
                            <#assign contactMech = delegator.findByPrimaryKey("ContactMech", Static["org.ofbiz.base.util.UtilMisc"].toMap("contactMechId", cartShipInfo.contactMechId))?if_exists />
                            <#if contactMech?has_content>
                                <#assign address = contactMech.getRelatedOne("PostalAddress")?if_exists />
                            </#if>

                            <#if address?exists>
                                <#if address.toName?has_content><b>${uiLabelMap.CommonTo}:</b>&nbsp;${address.toName}<br/></#if>
                                <#if address.attnName?has_content><b>${uiLabelMap.CommonAttn}:</b>&nbsp;${address.attnName}<br/></#if>
                                <#if address.address1?has_content>${address.address1}<br/></#if>
                                <#if address.address2?has_content>${address.address2}<br/></#if>
                                <#if address.city?has_content>${address.city}</#if>
                                <#if address.stateProvinceGeoId?has_content>&nbsp;${address.stateProvinceGeoId}</#if>
                                <#if address.postalCode?has_content>, ${address.postalCode?if_exists}</#if>
                            </#if>
                        </td>

                    <#-- supplier id (for drop shipments) (also spans rows = number of items) -->

                        <td rowspan="${numberOfItems}" valign="top">
                            <#assign supplier =  delegator.findByPrimaryKey("PartyGroup", Static["org.ofbiz.base.util.UtilMisc"].toMap("partyId", cartShipInfo.getSupplierPartyId()))?if_exists />
          <#if supplier?has_content>${supplier.groupName?default(supplier.partyId)}</#if>
                        </td>

                    <#-- carrier column (also spans rows = number of items) -->

                        <td rowspan="${numberOfItems}" valign="top">
                            <#assign carrier =  delegator.findByPrimaryKey("PartyGroup", Static["org.ofbiz.base.util.UtilMisc"].toMap("partyId", cartShipInfo.getCarrierPartyId()))?if_exists />
          <#assign method =  delegator.findByPrimaryKey("ShipmentMethodType", Static["org.ofbiz.base.util.UtilMisc"].toMap("shipmentMethodTypeId", cartShipInfo.getShipmentMethodTypeId()))?if_exists />
          <#if carrier?has_content>${carrier.groupName?default(carrier.partyId)}</#if>
          <#if method?has_content>${method.description?default(method.shipmentMethodTypeId)}</#if>
                        </td>

                    <#-- list each ShoppingCartItem in this group -->

                        <#assign itemIndex = 0 />
                        <#list cartShipInfo.getShipItems() as shoppingCartItem>
                            <#if (itemIndex > 0)>
                            <tr> </#if>

                            <td valign="top"> ${shoppingCartItem.getProductId()?default("")} - ${shoppingCartItem.getName()?default("")} </td>
                            <td valign="top"> ${cartShipInfo.getShipItemInfo(shoppingCartItem).getItemQuantity()?default("0")} </td>

                            <#if (itemIndex == 0)> </tr> </#if>
                            <#assign itemIndex = itemIndex + 1 />
                        </#list>

                        </tr>

                    </#if>
                </#list>

            <#-- END LIST SHIP GROUPS -->

            </table>
        </div>
    <@htmlScreenTemplate.renderScreenletEnd/>
</#if>
