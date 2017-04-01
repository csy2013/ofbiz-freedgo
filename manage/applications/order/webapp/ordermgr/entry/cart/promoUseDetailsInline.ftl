<h4>订单应用的促销:</h4>
        <ul>    
            <#list shoppingCart.getProductPromoUseInfoIter() as productPromoUseInfo>

                <li>
                    <#-- TODO: when promo pretty print is done show promo short description here -->
                        <#assign productPromo = delegator.findByPrimaryKey("ProductPromo", Static["org.ofbiz.base.util.UtilMisc"].toMap("productPromoId", productPromoUseInfo.productPromoId))/>

                        <#--<a href="<@ofbizUrl>showPromotionDetails?productPromoId=${productPromoUseInfo.productPromoId?if_exists}</@ofbizUrl>" class="button">${uiLabelMap.CommonDetails}</a>-->
                    <#if productPromoUseInfo.productPromoCodeId?has_content> - ${uiLabelMap.OrderWithPromoCode} [${productPromoUseInfo.productPromoCodeId}]</#if>
                    <#if (productPromoUseInfo.totalDiscountAmount != 0)> - ${uiLabelMap.CommonTotalValue} <@ofbizCurrency amount=(-1*productPromoUseInfo.totalDiscountAmount) isoCode=shoppingCart.getCurrency()/></#if>
                </li>
                <#if (productPromoUseInfo.quantityLeftInActions > 0)>
                    <li>- Could be used for ${productPromoUseInfo.quantityLeftInActions} more discounted item<#if (productPromoUseInfo.quantityLeftInActions > 1)>s</#if> if added to your cart.</li>
                </#if>
             &nbsp;${uiLabelMap.OrderPromotion}:${productPromo.promoName?if_exists}
                <@htmlScreenTemplate.renderModalPage id="showPromotionDetails_${productPromoUseInfo_index}" name="showPromotionDetails_${productPromoUseInfo_index}" modalUrl="showPromotionDetails?productPromoId=${productPromoUseInfo.productPromoId?if_exists}"
                modalTitle="订单促销详细页面" modalType="view" description="查看"/>
            </#list>
        </ul>
    <h4>订单项应用的促销:</h4>
    <ul>
        <#list shoppingCart.items() as cartLine>
            <#assign cartLineIndex = shoppingCart.getItemIndex(cartLine)>
            <#if cartLine.getIsPromo()>
                <li>${uiLabelMap.OrderItemN} ${cartLineIndex+1} [${cartLine.getProductId()?if_exists}] - ${uiLabelMap.OrderIsAPromotionalItem}</li>
            <#else>
                <li>${uiLabelMap.OrderItemN} ${cartLineIndex+1} [${cartLine.getProductId()?if_exists}] - ${cartLine.getPromoQuantityUsed()?string.number}/${cartLine.getQuantity()?string.number} ${uiLabelMap.CommonUsed} - ${cartLine.getPromoQuantityAvailable()?string.number} ${uiLabelMap.CommonAvailable}
                    <ul>
                        <#list cartLine.getQuantityUsedPerPromoActualIter() as quantityUsedPerPromoActualEntry>
                            <#assign productPromoActualPK = quantityUsedPerPromoActualEntry.getKey()>
                            <#assign actualQuantityUsed = quantityUsedPerPromoActualEntry.getValue()>
                            <#assign isQualifier = "ProductPromoCond" == productPromoActualPK.getEntityName()>
                            <li>&nbsp;&nbsp;-&nbsp;${actualQuantityUsed} ${uiLabelMap.CommonUsedAs} <#if isQualifier>${uiLabelMap.CommonQualifier}<#else>${uiLabelMap.CommonBenefit}</#if> ${uiLabelMap.OrderOfPromotion}
                                <#--<a href="<@ofbizUrl>showPromotionDetails?productPromoId=${productPromoActualPK.productPromoId}</@ofbizUrl>" class="button">${uiLabelMap.CommonDetails}</a>-->
                                <@htmlScreenTemplate.renderModalPage id="showPromotionDetails" name="showPromotionDetails" modalUrl="showPromotionDetails?productPromoId=${productPromoActualPK.productPromoId?if_exists}"
                                modalTitle="订单促销详细页面" modalType="view" description="查看"/>
                            </li>
                            <!-- productPromoActualPK ${productPromoActualPK.toString()} -->
                        </#list>
                    </ul>
                    <ul>
                        <#list cartLine.getQuantityUsedPerPromoFailedIter() as quantityUsedPerPromoFailedEntry>
                            <#assign productPromoFailedPK = quantityUsedPerPromoFailedEntry.getKey()>
                            <#assign failedQuantityUsed = quantityUsedPerPromoFailedEntry.getValue()>
                            <#assign isQualifier = "ProductPromoCond" == productPromoFailedPK.getEntityName()>
                            <li>&nbsp;&nbsp;-&nbsp;${uiLabelMap.CommonCouldBeUsedAs} <#if isQualifier>${uiLabelMap.CommonQualifier}<#else>${uiLabelMap.CommonBenefit}</#if> ${uiLabelMap.OrderOfPromotion}
                                <#--<a href="<@ofbizUrl>showPromotionDetails?productPromoId=${productPromoFailedPK.productPromoId}</@ofbizUrl>" class="button">${uiLabelMap.CommonDetails}</a>-->
                                <@htmlScreenTemplate.renderModalPage id="showPromotionDetails" name="showPromotionDetails" modalUrl="showPromotionDetails?productPromoId=${productPromoFailedPK.productPromoId?if_exists}"
                                modalTitle="订单促销详细页面" modalType="view" description="查看"/>
                            </li>
                            <!-- Total times checked but failed: ${failedQuantityUsed}, productPromoFailedPK ${productPromoFailedPK.toString()} -->
                        </#list>
                    </ul>
                    <#--<#list cartLine.getQuantityUsedPerPromoCandidateIter() as quantityUsedPerPromoCandidateEntry>
                        <#assign productPromoCandidatePK = quantityUsedPerPromoCandidateEntry.getKey()>
                        <#assign candidateQuantityUsed = quantityUsedPerPromoCandidateEntry.getValue()>
                        <#assign isQualifier = "ProductPromoCond" == productPromoCandidatePK.getEntityName()>
                        <!-- Left over not reset or confirmed, shouldn't happen: ${candidateQuantityUsed} Might be Used (Candidate) as <#if isQualifier>${uiLabelMap.CommonQualifier}<#else>${uiLabelMap.CommonBenefit}</#if> ${uiLabelMap.OrderOfPromotion} [${productPromoCandidatePK.productPromoId}] &ndash;&gt;
                        <!-- productPromoCandidatePK ${productPromoCandidatePK.toString()} &ndash;&gt;
                    </#list>-->
                </li>
            </#if>
        </#list>
    </ul>

