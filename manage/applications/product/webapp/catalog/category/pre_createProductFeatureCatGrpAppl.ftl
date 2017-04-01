<#if productFeatureGroups?has_content>
<form method="post" action="<@ofbizUrl>createProductFeatureCatGrpAppl</@ofbizUrl>" class="form-inline" name="addNewGroupForm">
    <input type="hidden" name="productCategoryId" value="${productCategoryId?if_exists}"/>
    <div class="form-group">
        <select name="productFeatureGroupId" class="form-control">
            <#list productFeatureGroups as productFeatureGroup>
                <option value="${(productFeatureGroup.productFeatureGroupId)?if_exists}">${(productFeatureGroup.description)?if_exists}
                    [${(productFeatureGroup.productFeatureGroupId)?if_exists}]
                </option>
            </#list>
        </select>
        <@htmlTemplate.renderDateTimeField name="fromDate" event="" action="" value="" className="" alert="" title="Format: yyyy-MM-dd HH:mm:ss.SSS" size="25" maxlength="30" id="fromDate2" dateType="date" shortDateInput=false timeDropdownParamName="" defaultDateTimeString="" localizedIconTitle="" timeDropdown="" timeHourName="" classString="" hour1="" hour2="" timeMinutesName="" minutes="" isTwelveHour="" ampmName="" amSelected="" pmSelected="" compositeType="" formName=""/>
    </div>
    <div class="form-group">
        <input type="submit" value="${uiLabelMap.CommonAdd}" class="btn btn-primary btn-sm"/>
    </div>
</form>

<#else>
&nbsp;
</#if>