 
<div class="panel panel-default cf">
  <div class="panel-hd cf">

        ${uiLabelMap.CommonCreate}&nbsp;
        <#if shoppingCart.getOrderType() == "PURCHASE_ORDER">
            ${uiLabelMap.OrderPurchaseOrder}
        <#else>
            ${uiLabelMap.OrderSalesOrder}
        </#if>
   </div>


    <div class="panel-bd cf">
        <ul class="nav nav-pills">
        <#if shoppingCart.getOrderType() == "PURCHASE_ORDER">
        <#if shoppingCart.getOrderPartyId() == "_NA_" || (shoppingCart.size() = 0)>
          <li class="disabled">${uiLabelMap.OrderFinalizeOrder}</li>
        <#else>
          <li><a href="<@ofbizUrl>finalizeOrder?finalizeMode=purchase&amp;finalizeReqCustInfo=false&amp;finalizeReqShipInfo=false&amp;finalizeReqOptions=false&amp;finalizeReqPayInfo=false</@ofbizUrl>">${uiLabelMap.OrderFinalizeOrder}</a></li>
        </#if>
      <#else>
      <#--  <#if shoppingCart.size() = 0>
          <li class="disabled">${uiLabelMap.OrderQuickFinalizeOrder}</li>
          <li class="disabled">${uiLabelMap.OrderFinalizeOrderDefault}</li>
          <li class="disabled">${uiLabelMap.OrderFinalizeOrder}</li>
        <#else>
          <li><a href="<@ofbizUrl>quickcheckout</@ofbizUrl>">${uiLabelMap.OrderQuickFinalizeOrder}</a></li>
          <li><a href="<@ofbizUrl>finalizeOrder?finalizeMode=default</@ofbizUrl>">${uiLabelMap.OrderFinalizeOrderDefault}</a></li>
          <li><a href="<@ofbizUrl>finalizeOrder?finalizeMode=init</@ofbizUrl>">${uiLabelMap.OrderFinalizeOrder}</a></li>
        </#if>-->
      </#if>

      <#if (shoppingCart.size() > 0)>
        <li><a href="javascript:submitForm(document.salesentryform, 'MC', '');">${uiLabelMap.OrderRecalculateOrder}</a></li>
        <li><a href="javascript:submitForm(document.salesentryform, 'DC', '');">${uiLabelMap.OrderRemoveSelected}</a></li>
      <#else>
        <li class="disabled">${uiLabelMap.OrderRecalculateOrder}</li>
        <li class="disabled">${uiLabelMap.OrderRemoveSelected}</li>
      </#if>
      <li><a href="<@ofbizUrl>saleemptycart</@ofbizUrl>">${uiLabelMap.OrderClearOrder}</a></li>
      <li><a href="javascript:submitForm(document.salesentryform, 'DN', '');">${uiLabelMap.OrderOrderConfirmation}</a></li>
    </ul>
  </div>
    </div>

