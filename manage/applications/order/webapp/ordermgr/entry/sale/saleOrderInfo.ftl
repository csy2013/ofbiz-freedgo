<div class="wizard-step-1">
    <fieldset>
        <legend class="pull-left width-full">
            订单基本信息

            <a class="btn btn-white  btn-sm pull-right m-r-10" href="javascript:submitForm(document.salesentryform, 'DN', '');">订单确认</a>
            <a class="btn btn-white  btn-sm pull-right m-r-10" href="<@ofbizUrl>saleemptycart</@ofbizUrl>">${uiLabelMap.OrderClearOrder}</a>
        </legend>
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
            <div class="input-group m-b-10 m-r-5">
                <label class='input-group-addon'>${uiLabelMap.ProductProductStore}</label>
                <select name="productStoreId"<#if sessionAttributes.orderMode?exists> disabled</#if> class="form-control">
                    <#assign currentStore = shoppingCartProductStore>
                    <#if defaultProductStore?has_content>
                        <option value="${defaultProductStore.productStoreId}">${defaultProductStore.storeName?if_exists}</option>
                        <option value="${defaultProductStore.productStoreId}">----</option>
                    </#if>
                    <#list productStores as productStore>
                        <option value="${productStore.productStoreId}"<#if productStore.productStoreId == currentStore>
                                selected="selected"</#if>>${productStore.storeName?if_exists}</option>
                    </#list>
                </select>
                <span class='input-group-addon'><#if sessionAttributes.orderMode?exists>${uiLabelMap.OrderCannotBeChanged}</#if></span>
                </div>

            <div class="input-group m-b-10 m-r-5">
                <label class='input-group-addon'>${uiLabelMap.OrderSalesChannel}</label>

                
                <select name="salesChannelEnumId" class="form-control">
                        <#assign currentChannel = shoppingCartChannelType>

                        <#list salesChannels as salesChannel>
                        <#if salesChannel.enumId !='GC_SALES_CHANNEL' && salesChannel.enumId !='FAX_SALES_CHANNEL'  && salesChannel.enumId !='AFFIL_SALES_CHANNEL' &&
                        salesChannel.enumId !='EBAY_SALES_CHANNEL' >
                             <option value="${salesChannel.enumId}" <#if (salesChannel.enumId == currentChannel)>selected="selected"</#if>>${salesChannel.get("description",locale)}</option>
                        </#if>
                        </#list>
                    </select>
                </div>


            <#if partyId?exists>
                <#assign thisPartyId = partyId>
            <#else>
                <#assign thisPartyId = requestParameters.partyId?if_exists>
            </#if>
            <div class="input-group m-b-10 m-r-5">
                <label class='input-group-addon'>${uiLabelMap.CommonUserLoginId}</label>
                <@htmlTemplate.lookupField value="${parameters.userLogin.userLoginId}" formName="salesentryform" name="userLoginId" id="userLoginId_sales" fieldFormName="LookupUserLoginAndPartyDetails"/>
                </div>
            <div class="input-group m-b-10 m-r-5">
                <label class='input-group-addon'>${uiLabelMap.OrderCustomer}</label>
                <@htmlTemplate.lookupField value='${thisPartyId?if_exists}' formName="salesentryform" name="partyId" id="partyId" fieldFormName="LookupCustomerName"/>
                </div>
        </#if>
    </#if>
    <#if agreements?exists>
        <input type='hidden' name='hasAgreements' value='Y'/>

        <div class="input-group m-b-10 m-r-5">
            <label class='input-group-addon'>
            ${uiLabelMap.OrderSelectAgreement}
            </label>

            
                <select name="agreementId" class="form-control">
                    <option value="">${uiLabelMap.CommonNone}</option>
                    <#list agreements as agreement>
                        <option value='${agreement.agreementId}'>${agreement.agreementId} - ${agreement.description?if_exists}</option>
                    </#list>
                </select>

        </div>
    <#else>
        <input type='hidden' name='hasAgreements' value='N'/>
    </#if>
    <#if agreementRoles?exists>
        <div class="input-group m-b-10 m-r-5">
            <label class='input-group-addon'>${uiLabelMap.OrderSelectAgreementRoles}</label>

            
                <select name="agreementId" class="form-control">
                    <option value="">${uiLabelMap.CommonNone}</option>
                    <#list agreementRoles as agreementRole>
                        <option value='${agreementRole.agreementId?if_exists}'>${agreementRole.agreementId?if_exists} - ${agreementRole.roleTypeId?if_exists}</option>
                    </#list>
                </select>

        </div>

    </#if>
        <div class="input-group m-b-10 m-r-5">
            <label class='input-group-addon'>${uiLabelMap.OrderOrderName}</label>
            <input type='text' name='orderName' class="form-control"/>

        </div>
        <#--<div class="input-group m-b-10 m-r-5">
            <label class='input-group-addon'>${uiLabelMap.OrderPONumber}</label>

            
                <input type="text" name="correspondingPoId" size="15" class="form-control"/>

        </div>-->
        <#--<div class="input-group m-b-10 m-r-5">
            <label class='input-group-addon'><#if agreements?exists>${uiLabelMap.OrderSelectCurrencyOr}<#else>${uiLabelMap.OrderSelectCurrency}</#if></label>

            
                <select name="currencyUomId" class="form-control">
                <#list currencies as currency>
                    <option value="${currency.uomId}" <#if currencyUomId?default('') == currency.uomId>selected="selected"</#if> >${currency.uomId}</option>
                </#list>
                </select>

        </div>-->

        <input type="hidden" name="currencyUomId" value="CNY"/>
        <input type="hidden" name="CURRENT_CATALOG_ID"/>
        <input type="hidden" name="workEffortId"/>
        <input type="hidden" name=" shipAfterDate"/>
        <input type="hidden" name=" shipBeforeDate"/>
        <input type="hidden" name=" shipAfterDate"/>
        <!-- Purchase Order Entry -->
        <#--<input type="button" onclick="document.salesentryform.submit();" class="btn btn-primary btn-sm m-b-10 m-r-5" value="保存"/>-->
        </div>

    </fieldset>
</div>