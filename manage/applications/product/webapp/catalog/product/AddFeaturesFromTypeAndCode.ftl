<form method="post" action="<@ofbizUrl>ApplyFeatureToProductFromTypeAndCode</@ofbizUrl>" name='addFeatureByTypeIdCode' class="form-horizontal">
    <input type="hidden" name="productId" value="${productId}"/>
    <div class="form-group">
        <label class='col-md-3 control-label'>${uiLabelMap.ProductFeatureType}: </label>
        <div class="col-md-7">
            <select name='productFeatureTypeId' class="form-control">
            <#list productFeatureTypes as productFeatureType>
                <option value='${(productFeatureType.productFeatureTypeId)?if_exists}'>${(productFeatureType.get("description",locale))?if_exists} </option>
            </#list>
            </select>
        </div>
    </div>

    <div class="form-group">
        <label class='col-md-3 control-label'>${uiLabelMap.CommonIdCode}: </label>

        <div class="col-md-7">
            <input type="text" size='10' name='idCode' value='' class="form-control"/>
        </div>
    </div>
    <div class="form-group">
        <label class='col-md-3 control-label'>${uiLabelMap.ProductFeatureApplicationType}: </label>

        <div class="col-md-7">
            <select name='productFeatureApplTypeId' class="form-control">
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
        <label class='col-md-3 control-label'>${uiLabelMap.CommonFrom} : </label>

        <div class="col-md-7"><@htmlTemplate.renderDateTimeField name="fromDate" event="" action="" className="" alert="" title="Format: yyyy-MM-dd HH:mm:ss.SSS" value="" size="25" maxlength="30" id="fromDate1" dateType="date" shortDateInput=false timeDropdownParamName="" defaultDateTimeString="" localizedIconTitle="" timeDropdown="" timeHourName="" classString="" hour1="" hour2="" timeMinutesName="" minutes="" isTwelveHour="" ampmName="" amSelected="" pmSelected="" compositeType="" formName=""/>
        </div>
    </div>
    <div class="form-group">
        <label class='col-md-3 control-label'>${uiLabelMap.CommonThru} : </label>

        <div class="col-md-7"><@htmlTemplate.renderDateTimeField name="thruDate" event="" action="" className="" alert="" title="Format: yyyy-MM-dd HH:mm:ss.SSS" value="" size="25" maxlength="30" id="thruDate1" dateType="date" shortDateInput=false timeDropdownParamName="" defaultDateTimeString="" localizedIconTitle="" timeDropdown="" timeHourName="" classString="" hour1="" hour2="" timeMinutesName="" minutes="" isTwelveHour="" ampmName="" amSelected="" pmSelected="" compositeType="" formName=""/>
        </div>
    </div>
    <div class="form-group">
        <label class='col-md-3 control-label'>${uiLabelMap.CommonSequence} : </label>
        <div class="col-md-7"><input type="text" size='5' name='sequenceNum' class="form-control"/>
        </div>
    </div>
    <div class="form-group">
        <div class="col-md-3">&nbsp;</div>
        <div class="col-md-7"><input type="submit" value="${uiLabelMap.CommonAdd}" class="btn btn-primary btn-sm"/></div>
    </div>
</form>