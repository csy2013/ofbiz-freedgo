<form method="post" action="<@ofbizUrl>ApplyFeaturesFromCategory</@ofbizUrl>" class="form-inline">
    <input type="hidden" name="productId" value="${productId}"/>
    <div class="form-group">
        <select name='productFeatureCategoryId'    class="form-control">
            <option value='' selected="selected">${uiLabelMap.ProductChooseFeatureCategory}</option>
        <#list productFeatureCategories as productFeatureCategory>
            <option value='${(productFeatureCategory.productFeatureCategoryId)?if_exists}'>${(productFeatureCategory.description)?if_exists}
                [${(productFeatureCategory.productFeatureCategoryId)?if_exists}]
            </option>
        </#list>
        </select>
        <select name='productFeatureGroupId'   class="form-control">
            <option value='' selected="selected">${uiLabelMap.ProductChooseFeatureGroup}</option>
        <#list productFeatureGroups as productFeatureGroup>
            <option value='${(productFeatureGroup.productFeatureGroupId)?if_exists}'>${(productFeatureGroup.description)?if_exists} [${(productFeatureGroup.productFeatureGroupId)?if_exists}
                ]
            </option>
        </#list>
        </select>

        <div class="input-group">
            <div class="input-group-addon">
                <span>${uiLabelMap.ProductFeatureApplicationType}: </span>
            </div>
            <select name='productFeatureApplTypeId'   class="form-control">
            <#list productFeatureApplTypes as productFeatureApplType>
                <option value='${(productFeatureApplType.productFeatureApplTypeId)?if_exists}'
                        <#if (productFeatureApplType.productFeatureApplTypeId?exists && product?exists && product.isVirtual == 'Y' && productFeatureApplType.productFeatureApplTypeId =="SELECTABLE_FEATURE")>selected="selected"</#if>
                        <#if (productFeatureApplType.productFeatureApplTypeId?exists && product?exists && product.isVirtual == 'N' && productFeatureApplType.productFeatureApplTypeId?if_exists =="STANDARD_FEATURE")>selected="selected"</#if>
                        >${(productFeatureApplType.get("description",locale))?if_exists} </option>
            </#list>
            </select></div>
        <input type="submit" value='${uiLabelMap.CommonAdd}' class="btn btn-primary btn-sm"/>
    </div>
</form>