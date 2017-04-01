
<#if security.hasEntityPermission("CATALOG", "_CREATE", session)>
<div>
<a href="/catalog/control/EditProduct?productId=${productId}${externalKeyParam?if_exists}" target="catalog" class="buttontext">${uiLabelMap.ProductEditProduct}</a>
</div>
</#if>
