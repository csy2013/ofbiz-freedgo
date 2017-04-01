<#if allProductPromos?has_content>
<div class="screenlet">
    <div class="screenlet-title-bar">
        <div class="h3">&nbsp;${uiLabelMap.OrderManualPromotions}</div>
    </div>
    <div class="screenlet-body">
        <div>
            <#--<form method="post" action="<@ofbizUrl>doManualPromotions</@ofbizUrl>" name="domanualpromotions" style="margin: 0;">-->
                <!-- to enter more than two manual promotions, just add a new select box with name="productPromoId_n" -->
                <select name="productPromoId_1">
                    <option value=""></option>
                    <#list allProductPromos as productPromo>
                        <option value="${productPromo.productPromoId}">${productPromo.promoName?if_exists}</option>
                    </#list>
                </select>
                <select name="productPromoId_2">
                    <option value=""></option>
                    <#list allProductPromos as productPromo>
                        <option value="${productPromo.productPromoId}">${productPromo.promoName?if_exists}</option>
                    </#list>
                </select>
                <input type="submit" class="smallSubmit" value="${uiLabelMap.OrderDoPromotions}" />
            <#--</form>-->
        </div>
    </div>
</div>
</#if>
