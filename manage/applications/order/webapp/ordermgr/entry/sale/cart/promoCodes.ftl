<div class="wizard-step-3">
    <div>
            <form method="post" action="<@ofbizUrl>addpromocode<#if requestAttributes._CURRENT_VIEW_?has_content>/${requestAttributes._CURRENT_VIEW_}</#if></@ofbizUrl>" name="addpromocodeform" style="margin: 0;">
                <div class="input-group">
                <#assign productPromoCodeIds = (shoppingCart.getProductPromoCodesEntered())?if_exists>
                    ${productPromoCodeIds}
                <#if productPromoCodeIds?has_content>
                <label class="input-group-addon"> ${uiLabelMap.OrderEnteredPromoCodes}:
                    <#list productPromoCodeIds as productPromoCodeId>
                    ${productPromoCodeId}
                    </#list>
                </#if>
                </label><input type="text" size="15" name="productPromoCodeId" value="" class="form-control"/>
                <input type="submit" class="btn btn-primary btn-sm" value="${uiLabelMap.OrderAddCode}" />
                </div>
            </form>
        </div>