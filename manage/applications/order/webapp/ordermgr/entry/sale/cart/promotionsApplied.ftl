<div class="wizard-step-3">
<#if (shoppingCartSize?default(0) > 0)>
${screens.render(promoUseDetailsInlineScreen)}
</#if>
</div>