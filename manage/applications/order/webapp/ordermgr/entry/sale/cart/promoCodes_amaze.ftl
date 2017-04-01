<div class="am-panel am-panel-default">
    <div class="am-panel-hd am-cf">
        &nbsp;${uiLabelMap.OrderPromotionCouponCodes}
    </div>
    <div class="am-panel-bd am-collapse am-in">
        <div>
            <#--<form method="post" action="<@ofbizUrl>addpromocode<#if requestAttributes._CURRENT_VIEW_?has_content>/${requestAttributes._CURRENT_VIEW_}</#if></@ofbizUrl>" name="addpromocodeform" style="margin: 0;">-->
                <input type="text" size="15" name="productPromoCodeId" value="" />
                <input type="submit" class="smallSubmit" value="${uiLabelMap.OrderAddCode}" />
                <#assign productPromoCodeIds = (shoppingCart.getProductPromoCodesEntered())?if_exists>
                <#if productPromoCodeIds?has_content>
                ${uiLabelMap.OrderEnteredPromoCodes}:
                    <#list productPromoCodeIds as productPromoCodeId>
                    ${productPromoCodeId}
                    </#list>
                </#if>
            <#--</form>-->
        </div>
    </div>
</div>