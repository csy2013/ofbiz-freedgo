<form method="post" action="<@ofbizUrl>expireAllCategoryProductMembers</@ofbizUrl>" class="form-horizontal" name="expireAllCategoryProductMembersForm">
    <input type="hidden" name="productCategoryId" value="${productCategoryId?if_exists}"/>
    <input type="hidden" name="activeOnly" value="${activeOnly.toString()}"/>

    <div class="form-group">
        <label class="control-label col-md-3">${uiLabelMap.ProductOptionalExpirationDate}</label>

        <div class="col-md-5 ">
        <@htmlTemplate.renderDateTimeField name="thruDate" event="" action="" className="" alert="" title="Format: yyyy-MM-dd HH:mm:ss.SSS" value="" size="25" maxlength="30" id="thruDate2" dateType="date" shortDateInput=false timeDropdownParamName="" defaultDateTimeString="" localizedIconTitle="" timeDropdown="" timeHourName="" classString="" hour1="" hour2="" timeMinutesName="" minutes="" isTwelveHour="" ampmName="" amSelected="" pmSelected="" compositeType="" formName=""/>
        </div>
    </div>
    <div class="form-group">
        <div class="col-md-3">&nbsp;</div>
        <div class="col-md-5  pull-right">
            <input type="submit" value="${uiLabelMap.CommonExpireAll}" class="btn btn-primary btn-sm"/>
        </div>
    </div>
</form>