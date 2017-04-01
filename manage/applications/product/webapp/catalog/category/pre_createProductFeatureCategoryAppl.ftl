<form method="post" action="<@ofbizUrl>createProductFeatureCategoryAppl</@ofbizUrl>" class="form-inline" name="addNewCategoryForm">
    <input type="hidden" name="productCategoryId" value="${productCategoryId?if_exists}"/>
    <div class="form-group">
        <div class="input-group">
            <select name="productFeatureCategoryId" class="form-control">
            <#list productFeatureCategories as productFeatureCategory>
                <option value="${(productFeatureCategory.productFeatureCategoryId)?if_exists}">${(productFeatureCategory.description)?if_exists}
                    [${(productFeatureCategory.productFeatureCategoryId)?if_exists}]
                </option>
            </#list>
            </select>
        </div>

    <@htmlTemplate.renderDateTimeField name="fromDate" event="" action="" value="" className="" alert="" title="Format: yyyy-MM-dd HH:mm:ss.SSS" size="25" maxlength="30" id="fromDate2" dateType="date" shortDateInput=false timeDropdownParamName="" defaultDateTimeString="" localizedIconTitle="" timeDropdown="" timeHourName="" classString="" hour1="" hour2="" timeMinutesName="" minutes="" isTwelveHour="" ampmName="" amSelected="" pmSelected="" compositeType="" formName=""/>
        <input type="submit" value="${uiLabelMap.CommonAdd}" class="btn btn-primary btn-sm"/>
    </div>
</form>