<form method="post" action="<@ofbizUrl>copyCategoryProductMembers</@ofbizUrl>" class="form-horizontal" name="copyCategoryProductMembersForm">
    <input type="hidden" name="productCategoryId" value="${productCategoryId?if_exists}"/>
    <input type="hidden" name="activeOnly" value="${activeOnly.toString()}"/>

    <div class="form-group">
        <label class="control-label col-md-3">${uiLabelMap.ProductTargetProductCategory}</label>

        <div class="col-md-5 ">
        <@htmlTemplate.lookupField formName="copyCategoryProductMembersForm" name="productCategoryIdTo" id="productCategoryIdTo" fieldFormName="LookupProductCategory"/>
        </div>
    </div>
    <div class="form-group">
        <label class="control-label col-md-3">${uiLabelMap.ProductOptionalFilterWithDate}</label>

        <div class="col-md-5 ">
        <@htmlTemplate.renderDateTimeField name="validDate" event="" action="" className="" alert="" title="Format: yyyy-MM-dd HH:mm:ss.SSS" value="" size="25" maxlength="30" id="validDate1" dateType="date" shortDateInput=false timeDropdownParamName="" defaultDateTimeString="" localizedIconTitle="" timeDropdown="" timeHourName="" classString="" hour1="" hour2="" timeMinutesName="" minutes="" isTwelveHour="" ampmName="" amSelected="" pmSelected="" compositeType="" formName=""/>
        </div>
    </div>
    <div class="form-group">
        <label class="control-label col-md-3">${uiLabelMap.ProductIncludeSubCategories}</label>

        <div class="col-md-5 ">
            <select name="recurse" class="form-control">
                <option value="N">${uiLabelMap.CommonN}</option>
                <option value="Y">${uiLabelMap.CommonY}</option>
            </select>
        </div>
    </div>
    <div class="form-group">
        <div class="col-md-3">&nbsp;</div>
        <div class="col-md-5  pull-right">
            <input type="submit" value="${uiLabelMap.CommonCopy}" class="btn btn-primary btn-sm"/>
        </div>
    </div>
</form>