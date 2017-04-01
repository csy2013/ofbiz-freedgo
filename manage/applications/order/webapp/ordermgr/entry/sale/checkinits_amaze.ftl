
<form method="post" name="salesentryform" action="<@ofbizUrl>dealsaleorderentry</@ofbizUrl>">
<#assign shoppingCartOrderType = "">
<#assign shoppingCartProductStore = "NA">
<#assign shoppingCartChannelType = "">
<#if shoppingCart?exists>
    <#assign shoppingCartOrderType = shoppingCart.getOrderType()>
    <#assign shoppingCartProductStore = shoppingCart.getProductStoreId()?default("NA")>
    <#assign shoppingCartChannelType = shoppingCart.getChannelType()?default("")>
<#else>
<#-- allow the order type to be set in parameter, so only the appropriate section (Sales or Purchase Order) shows up -->
    <#if parameters.orderTypeId?has_content>
        <#assign shoppingCartOrderType = parameters.orderTypeId>
    </#if>
</#if>
    <!-- Sales Order Entry -->
<#if security.hasEntityPermission("ORDERMGR", "_CREATE", session)>
    <#if shoppingCartOrderType != "PURCHASE_ORDER">
        <div class="am-panel am-panel-default">
            <div class="am-panel-hd am-cf">
                ${uiLabelMap.OrderSalesOrder}<#if shoppingCart?exists>&nbsp;${uiLabelMap.OrderInProgress}</#if></li>
            </div>
            <div class="am-panel-bd am-collapse am-in">
                <input type="hidden" name="originOrderId" value="${parameters.originOrderId?if_exists}"/>
                <input type="hidden" name="finalizeMode" value="type"/>
                <input type="hidden" name="orderMode" value="SALES_ORDER"/>
                <div class="am-g">
                    <div class="am-from-group">
                        <div class="am-u-md-2" for="productStoreId">${uiLabelMap.ProductProductStore}</div>
                        <div class="am-u-md-10">
                            <select id="productStoreId" name="productStoreId"<#if sessionAttributes.orderMode?exists> disabled</#if>>
                                <#assign currentStore = shoppingCartProductStore>
                                <#if defaultProductStore?has_content>
                                    <option value="${defaultProductStore.productStoreId}">${defaultProductStore.storeName?if_exists}</option>
                                    <option value="${defaultProductStore.productStoreId}">----</option>
                                </#if>
                                <#list productStores as productStore>
                                    <option value="${productStore.productStoreId}"<#if productStore.productStoreId == currentStore> selected="selected"</#if>>${productStore.storeName?if_exists}</option>
                                </#list>
                            </select>
                            <#if sessionAttributes.orderMode?exists>${uiLabelMap.OrderCannotBeChanged}</#if>
                        </div>
                    </div>
                </div>
                 <div class="am-g">
                        <div class="am-from-group">
                            <div class="am-u-md-2">${uiLabelMap.OrderSalesChannel}</div>
                            <div class="am-u-md-10">
                                <select  name="salesChannelEnumId">
                                    <#assign currentChannel = shoppingCartChannelType>
                                    <#if defaultSalesChannel?has_content>
                                        <option value="${defaultSalesChannel.enumId}">${defaultSalesChannel.description?if_exists}</option>
                                        <option value="${defaultSalesChannel.enumId}"> ----</option>
                                    </#if>
                                    <option value="">${uiLabelMap.OrderNoChannel}</option>
                                    <#list salesChannels as salesChannel>
                                        <option value="${salesChannel.enumId}" <#if (salesChannel.enumId == currentChannel)>selected="selected"</#if>>${salesChannel.get("description",locale)}</option>
                                    </#list>
                                </select>
                            </div>
                        </div>
                     </div>
                    <div class="am-g">
                        <#if partyId?exists>
                            <#assign thisPartyId = partyId>
                        <#else>
                            <#assign thisPartyId = requestParameters.partyId?if_exists>
                        </#if>
                        <div class="am-g">
                            <div class="am-u-md-2">${uiLabelMap.CommonUserLoginId}</div>
                            <div class="am-u-md-4">
                                  <@amazeHtmlTemplate.lookupField value="${parameters.userLogin.userLoginId}" formName="salesentryform" name="userLoginId" id="userLoginId_sales" fieldFormName="LookupUserLoginAndPartyDetails"/>
                             </div>

                            <div class="am-u-md-2">${uiLabelMap.OrderCustomer}</div>
                            <div class="am-u-md-4">
                                <@amazeHtmlTemplate.lookupField value='${thisPartyId?if_exists}' formName="salesentryform" name="partyId" id="partyId" fieldFormName="LookupCustomerName"/>

                            </div>
                       </div>
                    </div>
                </div>
            </div>
    </#if>
</#if>
    <!-- Purchase Order Entry -->

