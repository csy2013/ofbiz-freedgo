<#if showPromoText?exists && showPromoText>
<div class="am-panel am-panel-default">
    <div class="am-panel-hd am-cf">
        ${uiLabelMap.OrderSpecialOffers}
    </div>
    <div class="am-panel-bd am-collapse am-in">
        <div class="am-cf am-padding-xs">
        <#-- show promotions text -->
            <#list productPromos as productPromo>
                    <div class="am-g">
                        <div><a href="<@ofbizUrl>showPromotionDetails?productPromoId=${productPromo.productPromoId}</@ofbizUrl>" class="linktext">${uiLabelMap.CommonDetails}</a> ${StringUtil.wrapString(productPromo.promoText?if_exists)}</div>
                    </div>
                <#if productPromo_has_next>
                    <hr />
                </#if>
            </#list>
            <hr />
  <a href="<@ofbizUrl>showAllPromotions</@ofbizUrl>" class="buttontext">${uiLabelMap.OrderViewAllPromotions}</a>

        </div>
    </div>
</div>
</#if>