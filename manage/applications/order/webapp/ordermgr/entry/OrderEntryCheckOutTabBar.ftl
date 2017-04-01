
<#include "component://common/webcommon/includes/htmlScreenTemplate.ftl"/>
<#if stepTitleId?exists>
    <#assign stepTitle = uiLabelMap.get(stepTitleId)>
</#if>
<div class="${panelStyle}">
  <div class="${panelHeadingStyle}">
      <div class="${panelHeadingBarStyle}">
      <#if isLastStep == "N">
         <a href="javascript:document.checkoutsetupform.submit();" class="btn btn-success m-b-5 btn-xs">${uiLabelMap.CommonContinue}</a>
      <#else>
         <a href="<@ofbizUrl>processorder</@ofbizUrl>" class="btn btn-success m-b-5 btn-xs">${uiLabelMap.OrderCreateOrder}</a>
      </#if>
          <#--${checkoutSteps}-->
      <#list checkoutSteps?reverse as checkoutStep>
        <#assign stepUiLabel = uiLabelMap.get(checkoutStep.label)>
      <#--${checkoutStep.label}-->
        <#if checkoutStep.label !='PartyParties' && checkoutStep.label !='AccountingPayment' && checkoutStep.label !='OrderOrderTerms' &&checkoutStep.label !='CommonOptions' && checkoutStep.label !='FacilityShipping'>
            <#if checkoutStep.enabled == "N">
                <span class="disabled">${stepUiLabel}</span>
            <#else>
                <a href="<@ofbizUrl>${checkoutStep.uri}</@ofbizUrl>" class="btn btn-success m-b-5 btn-xs">${stepUiLabel}</a>
            </#if>
        </#if>
      </#list>
      </div>
      <h4 class="${panelTitleStyle}">
      <#if shoppingCart.getOrderType() == "PURCHASE_ORDER"> ${uiLabelMap.OrderPurchaseOrder} <#else> ${uiLabelMap.OrderSalesOrder} </#if> :&nbsp;${stepTitle?if_exists}
      </h4>
  </div>
</div>
