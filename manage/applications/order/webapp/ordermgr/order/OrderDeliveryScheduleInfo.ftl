
<#if hasPermission>

    <@htmlScreenTemplate.renderScreenletBegin id="OrderScheduleDelivery"  title="${uiLabelMap.OrderScheduleDelivery}" />

        <#if orderId?has_content>
        ${screens.render("component://order/widget/ordermgr/OrderDeliveryScheduleForms.xml#UpdateDeliveryScheduleInformation")}
          <#--${updatePODeliveryInfoWrapper.renderFormString(context)}-->
        <#else>
          ${uiLabelMap.OrderNoPurchaseSpecified}
        </#if>
        <@htmlScreenTemplate.renderScreenletEnd/>
<#else>
 <h3>${uiLabelMap.OrderViewPermissionError}</h3>
</#if>