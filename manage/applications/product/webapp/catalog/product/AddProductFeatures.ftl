<form method="post" action="<@ofbizUrl>ApplyFeatureToProduct</@ofbizUrl>" name="addFeatureById" class="form-horizontal">
    <input type="hidden" name="productId" value="${productId}"/>

    <div class="form-group">
        <lable class="col-md-3 control-label">${uiLabelMap.CommonId}:</lable>
        <div class="col-md-7">
        <@htmlTemplate.lookupField formName="addFeatureById" name="productFeatureId" id="productFeatureId" fieldFormName="LookupProductFeature"/>
        </div>
    </div>
    <div class="form-group">
        <lable class="col-md-3 control-label">${uiLabelMap.ProductFeatureApplicationType}:</lable>
        <div class="col-md-7">
            <select name="productFeatureApplTypeId" class="form-control">
            <#list productFeatureApplTypes as productFeatureApplType>
                <option value='${(productFeatureApplType.productFeatureApplTypeId)?if_exists}'
                        <#if (productFeatureApplType.productFeatureApplTypeId?exists && product?exists && product.isVirtual == 'Y' && productFeatureApplType.productFeatureApplTypeId =="SELECTABLE_FEATURE")>selected="selected"</#if>
                        <#if (productFeatureApplType.productFeatureApplTypeId?exists && product?exists && product.isVirtual == 'N' && productFeatureApplType.productFeatureApplTypeId =="STANDARD_FEATURE")>selected="selected"</#if>
                        >${(productFeatureApplType.get("description",locale))?if_exists} </option>
            </#list>
            </select>
        </div>
    </div>

    <div class="form-group">
        <lable class="col-md-3 control-label">${uiLabelMap.CommonFrom}:</lable>
        <div class="col-md-7"><@htmlTemplate.renderDateTimeField name="fromDate" event="" action="" className="" alert="" title="Format: yyyy-MM-dd HH:mm:ss.SSS" value="" size="25" maxlength="30" id="fromDate2" dateType="date" shortDateInput=false timeDropdownParamName="" defaultDateTimeString="" localizedIconTitle="" timeDropdown="" timeHourName="" classString="" hour1="" hour2="" timeMinutesName="" minutes="" isTwelveHour="" ampmName="" amSelected="" pmSelected="" compositeType="" formName=""/>
        </div>
    </div>

    <div class="form-group">
        <lable class="col-md-3 control-label">${uiLabelMap.CommonThru}:</lable>
        <div class="col-md-7"><@htmlTemplate.renderDateTimeField name="thruDate" event="" action="" className="" alert="" title="Format: yyyy-MM-dd HH:mm:ss.SSS" value="" size="25" maxlength="30" id="thruDate2" dateType="date" shortDateInput=false timeDropdownParamName="" defaultDateTimeString="" localizedIconTitle="" timeDropdown="" timeHourName="" classString="" hour1="" hour2="" timeMinutesName="" minutes="" isTwelveHour="" ampmName="" amSelected="" pmSelected="" compositeType="" formName=""/>
        </div>
    </div>

    <div class="form-group">
        <lable class="col-md-3 control-label">${uiLabelMap.CommonSequence} :</lable>
        <div class="col-md-7"><input class="form-control" type="text" size="5" name="sequenceNum"/></div>
    </div>

    <div class="form-group">
        <div class="col-md-3">&nbsp;</div>
        <div class="col-md-7">
            <input type="submit" value="${uiLabelMap.CommonAdd}" class="btn btn-primary btn-sm"/>
        </div>
    </div>
</form>