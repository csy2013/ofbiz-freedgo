<#if showPromoText?exists && showPromoText>
<#--<div class="screenlet">
    <div class="screenlet-title-bar">
        <div class="h3">${uiLabelMap.OrderSpecialOffers}</div>
    </div>
    <div class="screenlet-body">
        <table cellspacing="0" cellpadding="1" border="0">
        &lt;#&ndash; show promotions text &ndash;&gt;
            <#list productPromos as productPromo>
                <tr>
                    <td>
                        <div><a href="<@ofbizUrl>showPromotionDetails?productPromoId=${productPromo.productPromoId}</@ofbizUrl>" class="linktext">${uiLabelMap.CommonDetails}</a> ${StringUtil.wrapString(productPromo.promoText?if_exists)}</div>
                    </td>
                </tr>
                <#if productPromo_has_next>
                    <tr><td><hr /></td></tr>
                </#if>
            </#list>
            <tr><td><hr /></td></tr>
            <tr>
                <td>
                    <div><a href="<@ofbizUrl>showAllPromotions</@ofbizUrl>" class="buttontext">${uiLabelMap.OrderViewAllPromotions}</a></div>
                </td>
            </tr>
        </table>
    </div>
</div>-->
</#if>