<form method="post" class="form-horizontal" name="salesentryform" action="<@ofbizUrl>dealsaleorderentry</@ofbizUrl>">
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
        <input type="hidden" name="originOrderId" value="${parameters.originOrderId?if_exists}"/>
        <input type="hidden" name="finalizeMode" value="type"/>
        <input type="hidden" name="orderMode" value="SALES_ORDER"/>

        <div class="form-group">
            <label class='control-label col-md-3'>${uiLabelMap.ProductProductStore}</label>

            <div class="col-md-5">
                <select name="productStoreId"<#if sessionAttributes.orderMode?exists> disabled</#if> class="form-control">
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

        <div class="form-group">
            <label class='control-label col-md-3'>${uiLabelMap.OrderSalesChannel}</label>

            <div class="col-md-5">
                <select name="salesChannelEnumId" class="form-control">
                    <#assign currentChannel = shoppingCartChannelType>

                    <option value="">${uiLabelMap.OrderNoChannel}</option>
                    <#list salesChannels as salesChannel>
                        <option value="${salesChannel.enumId}" <#if (salesChannel.enumId == currentChannel)>selected="selected"</#if>>${salesChannel.get("description",locale)}</option>
                    </#list>
                </select>
            </div>
        </div>

        <#if partyId?exists>
            <#assign thisPartyId = partyId>
        <#else>
            <#assign thisPartyId = requestParameters.partyId?if_exists>
        </#if>
        <div class="form-group">
            <label class='control-label col-md-3'>${uiLabelMap.CommonUserLoginId}</label>

            <div class="col-md-5">
                <@htmlTemplate.lookupField value="${parameters.userLogin.userLoginId}" formName="salesentryform" name="userLoginId" id="userLoginId_sales" fieldFormName="LookupUserLoginAndPartyDetails"/>
            </div>
        </div>
        <div class="form-group">
            <label class='control-label col-md-3'>${uiLabelMap.OrderCustomer}</label>

            <div class="col-md-5">
                <@htmlTemplate.lookupField value='${thisPartyId?if_exists}' formName="salesentryform" name="partyId" id="partyId" fieldFormName="LookupCustomerName"/>
            </div>
        </div>

    </#if>
</#if>


<#if agreements?exists>
    <input type='hidden' name='hasAgreements' value='Y'/>

    <div class="form-group">
        <label class="control-label col-md-3">
        ${uiLabelMap.OrderSelectAgreement}
        </label>

        <div class="col-md-5">
            <select name="agreementId" class="form-control">
                <option value="">${uiLabelMap.CommonNone}</option>
                <#list agreements as agreement>
                    <option value='${agreement.agreementId}'>${agreement.agreementId} - ${agreement.description?if_exists}</option>
                </#list>
            </select>
        </div>
    </div>
<#else>
    <input type='hidden' name='hasAgreements' value='N'/>
</#if>
<#if agreementRoles?exists>
    <div class="form-group">
        <label class="control-label col-md-3">${uiLabelMap.OrderSelectAgreementRoles}</label>

        <div class="col-md-5">
            <select name="agreementId" class="form-control">
                <option value="">${uiLabelMap.CommonNone}</option>
                <#list agreementRoles as agreementRole>
                    <option value='${agreementRole.agreementId?if_exists}'>${agreementRole.agreementId?if_exists} - ${agreementRole.roleTypeId?if_exists}</option>
                </#list>
            </select>
        </div>
    </div>

</#if>


    <div class="form-group">
        <label class="control-label col-md-3">${uiLabelMap.OrderOrderName}</label>

        <div class="col-md-5">
            <input type='text' name='orderName' class="form-control"/>
        </div>
    </div>


    <div class="form-group">
        <label class="control-label col-md-3">${uiLabelMap.OrderPONumber}</label>

        <div class="col-md-5">
            <input type="text" name="correspondingPoId" size="15" class="form-control"/>
        </div>
    </div>


    <div class="form-group">
        <label class="control-label col-md-3"><#if agreements?exists>${uiLabelMap.OrderSelectCurrencyOr}<#else>${uiLabelMap.OrderSelectCurrency}</#if></label>

        <div class="col-md-5">
            <select name="currencyUomId" class="form-control">
            <#list currencies as currency>
                <option value="${currency.uomId}" <#if currencyUomId?default('') == currency.uomId>selected="selected"</#if> >${currency.uomId}</option>
            </#list>
            </select>
        </div>
    </div>
    <input type="hidden" name="currencyUomId" value="CNY"/>
    <input type="hidden" name="CURRENT_CATALOG_ID"/>
    <input type="hidden" name="workEffortId"/>
    <input type="hidden" name=" shipAfterDate"/>
    <input type="hidden" name=" shipBeforeDate"/>
    <input type="hidden" name=" shipAfterDate"/>
    <!-- Purchase Order Entry -->

