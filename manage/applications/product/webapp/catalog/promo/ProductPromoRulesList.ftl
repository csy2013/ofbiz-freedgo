<#if productPromoId?exists && productPromo?exists>
<div class="h4" style="color:red">&nbsp;&nbsp;在所有条件都满足的情况下，才会执行促销动作
    促销对应产品及产品分类，既是条件产品也是执行的产品,当没有选择任何产品则所有商品都满足条件。
</div>
<div class="table-responsive">
    <table id="data-table" class="table table-striped table-bordered">
        <thead>
        <tr>
            <th>
                <span id="ListProductPromoContent_editProductPromoContent_title">促销规则名称</span></th>
            <th>
                <span id="ListProductPromoContent_productPromoContentTypeId_title">促销规则条件</span>
            </th>
            <th>
                <span id="ListProductPromoContent_thruDate_title">促销规则执行动作</span>
            </th>
        </tr>
        </thead>
        <#if productPromoRules?has_content>
            <#list productPromoRules as productPromoRule>
                <#assign productPromoConds = productPromoRule.getRelated("ProductPromoCond")>
                <#assign productPromoActions = productPromoRule.getRelated("ProductPromoAction")>
                <#assign condsize = 0>
                <#assign actionsize = 0>
                <#assign size = 0>
                <#if (productPromoConds?has_content)>
                    <#assign  condsize= productPromoConds.size()>
                    <#if condsize gt size>
                        <#assign size = condsize>
                    </#if>
                </#if>
                <#if (productPromoActions?has_content)>
                    <#assign  actionsize= productPromoActions.size()>
                    <#if actionsize gt size>
                        <#assign size = actionsize>
                    </#if>
                </#if>


                <#list 0..(size-1) as i>
                    <tr>
                        <#if i==0>
                            <td rowspan="${size}">${productPromoRule.ruleName}<br/><br/>

                                <div class="am-btn-group-stacked m-r-5">
                                    <@htmlTemplate.renderModalPage id="addProductPromoRule_${productPromoRule.productPromoRuleId}" name="addProductPromoCond"
                                    modalTitle="新增规则条件" description="新增规则条件" modalUrl="/catalog/control/AddProductPromoCond"
                                    targetParameterIter="productPromoId:'${productPromoId}',productPromoRuleId:'${productPromoRule.productPromoRuleId}'"/>
                                <#--<button class="btn btn-primary btn-xs">新增规则条件</button>-->
                                    <br/>


                                    <@htmlTemplate.renderConfirmField id="deleteProductPromoRule_${productPromoRule.productPromoId}_${productPromoRule.productPromoRuleId}" name="deleteProductPromoRule"
                                    confirmTitle="删除规则" description="删除规则" confirmMessage="确定删除该促销规则" confirmUrl="/catalog/control/deleteProductPromoRule"
                                    targetParameterIter="productPromoId:'${productPromoId}',productPromoRuleId:'${productPromoRule.productPromoRuleId}'"/>
                                </div>
                            </td>
                        </#if>

                        <#if (productPromoConds?has_content) && (productPromoConds.size() gte 1)>


                            <#if (i+1) lt condsize >
                            <td>
                            <#elseif ((i+1) == condsize)>
                            <td rowspan="${size-condsize+1}">
                            </#if>

                            <#if (i+1) lte condsize>
                                <#assign productPromoCond = productPromoConds.get(i)>
                                <#assign inputParamEnum = productPromoCond.getRelatedOneCache("InputParamEnumeration")>
                                <#assign operatorEnum = productPromoCond.getRelatedOneCache("OperatorEnumeration")>
                                <#assign otherValue = productPromoCond.get("otherValue")?if_exists>

                                <#if otherValue?has_content && otherValue.contains("@")>
                                    <#assign carrierShippingMethod = productPromoCond.otherValue?if_exists>
                                </#if>

                                <#if carrierShippingMethod?has_content>
                                    <#assign carrierParty = carrierShippingMethod.substring(0, carrierShippingMethod.indexOf("@"))>
                                    <#assign shippingMethodTypeId = carrierShippingMethod.substring(carrierShippingMethod.indexOf("@")+1)>
                                    <#assign description = (delegator.findOne("ShipmentMethodType", {"shipmentMethodTypeId":shippingMethodTypeId}, false)).description>
                                <#else>
                                    <#assign description = "">
                                </#if>

                                <#assign condProductPromoProducts = productPromoCond.getRelated("ProductPromoProduct")>
                                <#assign condProductPromoCategories = productPromoCond.getRelated("ProductPromoCategory")>
                                <div class="">
                                    条件${productPromoCond.get("productPromoCondSeqId")}:
                                ${(inputParamEnum.get("description",locale))?if_exists}
                                    <#if (inputParamEnum.get("enumId") == 'PPC_BTW')||(inputParamEnum.get("enumId") == 'PPIP_GRPODR_TOTAL')>在:${productPromoCond.condValue},${otherValue}之间
                                    <#else>${(operatorEnum.get("description",locale)?if_exists)}:${productPromoCond.condValue?if_exists}
                                    <#if condValue?has_content>送货方式:${carrierParty}&nbsp;${description}</#if>
                                </#if>

                                </div>

                                <div class="">
                                    条件${productPromoCond.get("productPromoCondSeqId")}适用产品:
                                    <#if condProductPromoProducts?has_content>
                                        <#list condProductPromoProducts as condProductPromoProduct>
                                            <#assign condProduct = condProductPromoProduct.getRelatedOneCache("Product")?if_exists>
                                            <#assign condApplEnumeration = condProductPromoProduct.getRelatedOneCache("ApplEnumeration")>
                                            <div>
                                                &nbsp;&nbsp;${(condApplEnumeration.get("description",locale))?default(condProductPromoProduct.productPromoApplEnumId)}
                                                : ${(condProduct.internalName)?if_exists} (${condProductPromoProduct.productId})

                                            </div>
                                        </#list>
                                    <#else>
                                        &nbsp;&nbsp;
                                        <div>无限制</div>
                                    </#if>
                                </div>
                                <div class="">
                                    条件${productPromoCond.get("productPromoCondSeqId")}适用产品分类：
                                    <#if condProductPromoCategories?has_content>
                                        <#list condProductPromoCategories as condProductPromoCategory>
                                            <#assign condProductCategory = condProductPromoCategory.getRelatedOneCache("ProductCategory")>
                                            <#assign condApplEnumeration = condProductPromoCategory.getRelatedOneCache("ApplEnumeration")>
                                            <div>
                                                &nbsp;&nbsp;${(condApplEnumeration.get("description",locale))?default(condProductPromoCategory.productPromoApplEnumId)}:
                                                分类${(condProductCategory.get("description",locale))?if_exists}
                                                (${condProductPromoCategory.productCategoryId}),
                                                <#if condProductPromoCategory.includeSubCategories?default("N")=='N'>不包含<#else>包含</#if>
                                            ${uiLabelMap.ProductSubCats}
                                            </div>
                                        </#list>
                                    <#else>
                                        &nbsp;&nbsp;
                                        <div>无限制</div>
                                    </#if>
                                </div>
                                <@htmlTemplate.renderModalPage id="editProductPromoCond_${productPromoCond.productPromoId}_${productPromoCond.productPromoRuleId}_${productPromoCond.productPromoCondSeqId}" name="eidtProductPromoCond"
                                modalTitle="修改条件" description="修改条件" modalUrl="/catalog/control/EditProductPromoCond"
                                targetParameterIter="productPromoId:'${productPromoId}',productPromoRuleId:'${productPromoCond.productPromoRuleId}',productPromoCondSeqId:'${productPromoCond.productPromoCondSeqId}'"/>

                                <@htmlTemplate.renderConfirmField id="deleteProductPromoCond_${productPromoCond.productPromoId}_${productPromoCond.productPromoRuleId}_${productPromoCond.productPromoCondSeqId}" name="deleteProductPromoCond"
                                confirmTitle="删除条件" description="删除条件" confirmMessage="确定删除该促销规则" confirmUrl="/catalog/control/deleteProductPromoCond"
                                targetParameterIter="productPromoId:'${productPromoId}',productPromoRuleId:'${productPromoCond.productPromoRuleId}',productPromoCondSeqId:'${productPromoCond.productPromoCondSeqId}'"/>

                                <@htmlTemplate.renderModalPage id="addProductPromoAction_${productPromoRule.productPromoRuleId}" name="addProductPromoAction"
                                modalTitle="新增执行动作" description="新增执行动作" modalUrl="/catalog/control/AddProductPromoAction"
                                targetParameterIter="productPromoId:'${productPromoId}',productPromoRuleId:'${productPromoRule.productPromoRuleId}',productPromoCondSeqId:'${productPromoCond.productPromoCondSeqId}'"/>

                            </td>
                            </#if>
                        <#else>
                            <td rowspan="${size-condsize+1}">&nbsp;</td>
                        </#if>

                        <#if (productPromoActions?has_content) && (productPromoActions.size() gte 1)>

                            <#if (i+1) lt actionsize >
                            <td>
                            <#elseif ((i+1) == actionsize)>
                            <td rowspan="${size-actionsize+1}">
                            </#if>
                            <#if (i+1) lte actionsize >
                                <#assign productPromoAction = productPromoActions.get(i)>
                                <#assign productPromoActionCurEnum = productPromoAction.getRelatedOneCache("ActionEnumeration")>
                                <#assign actionProductPromoProducts = productPromoAction.getRelated("ProductPromoProduct")>
                                <#assign actionProductPromoCategories = productPromoAction.getRelated("ProductPromoCategory")>
                                <div class="">
                                    <div class="">
                                        动作${productPromoAction.get("productPromoActionSeqId")}:
                                     ${(productPromoActionCurEnum.get("description",locale))?if_exists}

                                        <#if (productPromoAction.productPromoActionEnumId!='PROMO_PROD_SPPRC')>
                                        <#if (productPromoAction.quantity)?exists>数量为:${(productPromoAction.quantity)?if_exists},</#if>
                                        <#if (productPromoAction.productPromoActionEnumId=='PROMO_PROD_DISC'||productPromoAction.productPromoActionEnumId=='PROMO_ORDER_PERCENT') && (productPromoAction.amount)?exists>
                                            折扣百分比为:${(productPromoAction.amount)?if_exists}%,
                                            <#else>金额为:${(productPromoAction.amount)?if_exists},</#if>
                                        </#if>
                                        <#--<#if (productPromoAction.productId)?exists>${uiLabelMap.ProductItemId}为:${(productPromoAction.productId)?if_exists},</#if>
                                        <#if (productPromoAction.partyId)?exists>${uiLabelMap.PartyParty}为：${(productPromoAction.partyId)?if_exists},</#if>-->
                                        <#--<#if (productPromoAction.useCartQuantity)?exists>-->
                                            <#--<#if productPromoAction.useCartQuantity.equals("Y")>使用购物车数量<#else>不使用购物车数量</#if>-->
                                        <#--</#if>-->
                                    </div>
                                    动作${productPromoAction.get("productPromoActionSeqId")}适用产品:
                                    <#if actionProductPromoProducts?has_content>
                                        <#list actionProductPromoProducts as actionProductPromoProduct>
                                            <#assign actionProduct = actionProductPromoProduct.getRelatedOneCache("Product")?if_exists>
                                            <#assign actionApplEnumeration = actionProductPromoProduct.getRelatedOneCache("ApplEnumeration")>
                                            <div>
                                                &nbsp;&nbsp;${(actionApplEnumeration.get("description",locale))?default(actionProductPromoProduct.productPromoApplEnumId)}
                                                : ${(actionProduct.internalName)?if_exists} (${actionProductPromoProduct.productId})
                                            </div>
                                        </#list>
                                    <#else>
                                        &nbsp;&nbsp;
                                        <div>无限制</div>
                                    </#if>
                                </div>
                                <div class="">
                                    条件${productPromoAction.get("productPromoActionSeqId")}适用产品分类：
                                    <#if actionProductPromoCategories?has_content>
                                        <#list actionProductPromoCategories as actionProductPromoCategory>
                                            <#assign actionProductCategory = actionProductPromoCategory.getRelatedOneCache("ProductCategory")>
                                            <#assign actionApplEnumeration = actionProductPromoCategory.getRelatedOneCache("ApplEnumeration")>
                                            <div>
                                                &nbsp;&nbsp;${(actionApplEnumeration.get("description",locale))?default(actionProductPromoCategory.productPromoApplEnumId)}:
                                                分类${(actionProductCategory.get("description",locale))?if_exists}
                                                (${actionProductPromoCategory.productCategoryId}),
                                                <#if actionProductPromoCategory.includeSubCategories?default("N")=='N'>不包含<#else>包含</#if>
                                            ${uiLabelMap.ProductSubCats}
                                            </div>
                                        </#list>
                                    <#else>
                                        &nbsp;&nbsp;
                                        <div>无限制</div>
                                    </#if>
                                </div>
                                <@htmlTemplate.renderModalPage id="editProductPromoAction_${productPromoAction.productPromoId}_${productPromoAction.productPromoRuleId}_${productPromoAction.productPromoActionSeqId}" name="editProductPromoAction"
                                modalTitle="修改动作" description="修改动作" modalUrl="/catalog/control/EditProductPromoAction"
                                targetParameterIter="productPromoId:'${productPromoId}',productPromoCondSeqId:'${productPromoAction.productPromoCondSeqId}',productPromoRuleId:'${productPromoAction.productPromoRuleId}',productPromoActionSeqId:'${productPromoAction.productPromoActionSeqId}'"/>

                                <@htmlTemplate.renderConfirmField id="deleteProductPromoAction_${productPromoAction.productPromoId}_${productPromoAction.productPromoRuleId}_${productPromoAction.productPromoActionSeqId}" name="deleteProductPromoAction"
                                confirmTitle="删除动作" description="删除动作" confirmMessage="确定删除该促销动作" confirmUrl="/catalog/control/deleteProductPromoAction"
                                targetParameterIter="productPromoId:'${productPromoId}',productPromoCondSeqId:'${productPromoAction.productPromoCondSeqId}',productPromoRuleId:'${productPromoAction.productPromoRuleId}',productPromoActionSeqId:'${productPromoAction.productPromoActionSeqId}'"/>


                            </td>
                            </#if>

                        <#else>
                            <td rowspan="${size-actionsize+1}">&nbsp;</td>
                        </#if>
                    </tr>
                </#list>
            </#list>
        </#if>

    </table>
</div>

</#if>