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
        <input type='text'  name='orderName' class="form-control"/>
    </div>
</div>


<div class="form-group">
    <label class="control-label col-md-3">${uiLabelMap.OrderPONumber}</label>
    <div class="col-md-5">
        <input type="text"   name="correspondingPoId" size="15" class="form-control"/>
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



