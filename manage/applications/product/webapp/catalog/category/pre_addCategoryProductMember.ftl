<form method="post" action="<@ofbizUrl>addCategoryProductMember</@ofbizUrl>" class="form-horizontal" name="addProductCategoryMemberForm">
    <input type="hidden" name="productCategoryId" value="${productCategoryId?if_exists}"/>
    <input type="hidden" name="activeOnly" value="${activeOnly}"/>

    <div class="form-group">
        <label class="control-label col-md-3">${uiLabelMap.ProductProductId}</label>
        <div class="col-md-5 ">
        <@htmlTemplate.lookupField formName="addProductCategoryMemberForm" name="productId" id="productId" fieldFormName="LookupProduct"/>
        </div>
    </div>
    <div class="form-group">
        <label class="control-label col-md-3">${uiLabelMap.CommonFromDate}</label>
        <div class="col-md-5 ">
        <@htmlTemplate.renderDateTimeField name="fromDate" event="" action="" className="" alert="" title="Format: yyyy-MM-dd HH:mm:ss.SSS" value="" size="25" maxlength="30" id="fromDate_1" dateType="date" shortDateInput=false timeDropdownParamName="" defaultDateTimeString="" localizedIconTitle="" timeDropdown="" timeHourName="" classString="" hour1="" hour2="" timeMinutesName="" minutes="" isTwelveHour="" ampmName="" amSelected="" pmSelected="" compositeType="" formName=""/>
        </div>
    </div>
    <div class="form-group">
        <label class="control-label col-md-3">${uiLabelMap.CommonComments}</label>
        <div class="col-md-5 ">
            <textarea name="comments" rows="2" cols="40" class="form-control"></textarea>
        </div>
    </div>
    <div class="form-group">
        <div class="col-md-3">&nbsp;</div>
        <div class="col-md-5  pull-right">
            <input type="submit" value="${uiLabelMap.CommonAdd}" class="btn btn-primary btn-sm"/>
        </div>
    </div>
</form>