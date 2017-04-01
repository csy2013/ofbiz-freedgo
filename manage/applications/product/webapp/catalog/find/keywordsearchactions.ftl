<#if productIds?has_content>
<hr/>
<div class="alert alert-info fade in m-b-15"><b>${uiLabelMap.ProductNote}:</b> ${uiLabelMap.ProductNoteKeywordSearch}</div>
<hr/>

${screens.render("component://product/widget/catalog/ProductScreens.xml#CreateVirtualWithVariantsFormInclude")}

<div class="panel panel-body">
    <form method="post" action="<@ofbizUrl>searchRemoveFromCategory</@ofbizUrl>" name="searchRemoveFromCategory" class="form-inline">
        <div class="form-group">
            <div class="input-group m-b-10 m-r-5">
                <div class="input-group-addon">
                    <span>${uiLabelMap.ProductRemoveResultsFrom} ${uiLabelMap.ProductCategory}:</span>
                    <@htmlTemplate.lookupField formName="searchRemoveFromCategory" name="SE_SEARCH_CATEGORY_ID" id="SE_SEARCH_CATEGORY_ID" fieldFormName="LookupProductCategory"/>
                </div>
            </div>
            <input type="hidden" name="clearSearch" value="N"/>
            <input type="submit" value="${uiLabelMap.CommonRemove}" class="btn btn-primary btn-sm"/>
        </div>
    </form>
</div>

<hr/>

<div class="panel panel-body">
    <form method="post" action="<@ofbizUrl>searchExpireFromCategory</@ofbizUrl>" name="searchExpireFromCategory" class="form-inline">
        <div class="form-group">
            <div class="input-group m-b-10 m-r-5">
                <div class="input-group-addon">
                    <span>${uiLabelMap.ProductExpireResultsFrom} ${uiLabelMap.ProductCategory}:</span>
                </div>
                <@htmlTemplate.lookupField formName="searchExpireFromCategory" name="SE_SEARCH_CATEGORY_ID" id="SE_SEARCH_CATEGORY_ID" fieldFormName="LookupProductCategory"/>
            </div>
            <div class="input-group m-b-10 m-r-5">
                <div class="input-group-addon">
                    <span>${uiLabelMap.CommonThru}</span>
                    <@htmlTemplate.renderDateTimeField name="thruDate" event="" action="" className="" alert="" title="Format: yyyy-MM-dd HH:mm:ss.SSS" value="" size="25" maxlength="30" id="thruDate1" dateType="date" shortDateInput=false timeDropdownParamName="" defaultDateTimeString="" localizedIconTitle="" timeDropdown="" timeHourName="" classString="" hour1="" hour2="" timeMinutesName="" minutes="" isTwelveHour="" ampmName="" amSelected="" pmSelected="" compositeType="" formName=""/>
                </div>
            </div>
            <input type="hidden" name="clearSearch" value="N"/>
            <input type="submit" value="${uiLabelMap.CommonExpire}" class="btn btn-primary btn-sm"/>
        </div>
    </form>
</div>

<hr/>

<div class="panel panel-body">
    <form method="post" action="<@ofbizUrl>searchAddToCategory</@ofbizUrl>" name="searchAddToCategory" class="form-inline">
        <div class="form-group">
            <div class="input-group m-b-10 m-r-5">
                <div class="input-group-addon">
                    <span>${uiLabelMap.ProductAddResultsTo} ${uiLabelMap.ProductCategory}:</span>
                    <@htmlTemplate.lookupField formName="searchAddToCategory" name="SE_SEARCH_CATEGORY_ID" id="SE_SEARCH_CATEGORY_ID" fieldFormName="LookupProductCategory"/>
                </div>
            </div>
            <div class="input-group m-b-10 m-r-5">
                <div class="input-group-addon">
                    <span>${uiLabelMap.CommonFrom}</span>
                </div>
                <@htmlTemplate.renderDateTimeField name="fromDate" event="" action="" className="" alert="" title="Format: yyyy-MM-dd HH:mm:ss.SSS" value="" size="25" maxlength="30" id="fromDate1" dateType="date" shortDateInput=false timeDropdownParamName="" defaultDateTimeString="" localizedIconTitle="" timeDropdown="" timeHourName="" classString="" hour1="" hour2="" timeMinutesName="" minutes="" isTwelveHour="" ampmName="" amSelected="" pmSelected="" compositeType="" formName=""/>
            </div>

            <input type="hidden" name="clearSearch" value="N"/>
            <input type="submit" value="${uiLabelMap.ProductAddToCategory}" class="btn btn-primary btn-sm"/>
        </div>
    </form>
</div>

<hr/>

<div class="panel panel-body">
    <form method="post" action="<@ofbizUrl>searchAddFeature</@ofbizUrl>" name="searchAddFeature" class="form-inline">
        <div class="form-group">
            <div class="text-info">${uiLabelMap.ProductAddFeatureToResults}:</div>


            <div class="input-group m-b-10 m-r-5">
                <div class="input-group-addon">
                    <span>${uiLabelMap.ProductFeatureId}</span> </div><input type="text" class="form-control" size="10" name="productFeatureId" value=""/>

            </div>
            <div class="input-group m-b-10 m-r-5">
                <div class="input-group-addon"><span>${uiLabelMap.CommonFrom}</span></div>
                    <@htmlTemplate.renderDateTimeField name="fromDate" event="" action="" className="" alert="" title="Format: yyyy-MM-dd HH:mm:ss.SSS" value="" size="25" maxlength="30" id="fromDate2" dateType="date" shortDateInput=false timeDropdownParamName="" defaultDateTimeString="" localizedIconTitle="" timeDropdown="" timeHourName="" classString="" hour1="" hour2="" timeMinutesName="" minutes="" isTwelveHour="" ampmName="" amSelected="" pmSelected="" compositeType="" formName=""/>

            </div>
            <div class="input-group m-b-10 m-r-5">
                <div class="input-group-addon"><span>${uiLabelMap.CommonThru}</span></div>
                    <@htmlTemplate.renderDateTimeField name="thruDate" event="" action="" className="" alert="" title="Format: yyyy-MM-dd HH:mm:ss.SSS" value="" size="25" maxlength="30" id="thruDate2" dateType="date" shortDateInput=false timeDropdownParamName="" defaultDateTimeString="" localizedIconTitle="" timeDropdown="" timeHourName="" classString="" hour1="" hour2="" timeMinutesName="" minutes="" isTwelveHour="" ampmName="" amSelected="" pmSelected="" compositeType="" formName=""/>


            </div>
            <div class="input-group m-b-10 m-r-5">
                <div class="input-group-addon"><span>${uiLabelMap.CommonAmount}</span></div><input type="text" class="form-control" size="5" name="amount" value=""/>

            </div>
            <div class="input-group m-b-10 m-r-5">
                <div class="input-group-addon"><span>${uiLabelMap.CommonSequence}</span></div><input type="text" class="form-control" size="5" name="sequenceNum" value=""/>

            </div>
            <div class="input-group m-b-10 m-r-5">
                <div class="input-group-addon"><span>${uiLabelMap.ProductFeatureApplicationType}</span> </div>
            </div>
            <div class="input-group m-b-10 m-r-5">
                <div class="input-group-addon"><span>${uiLabelMap.ProductCategoryId}:</span> </div>
                    <select name='productFeatureApplTypeId'  class="form-control">
                        <#list applicationTypes as applicationType>
                            <#assign displayDesc = applicationType.get("description", locale)?default("No Description")>
                            <#if 18 < displayDesc?length>
                                <#assign displayDesc = displayDesc[0..15] + "...">
                            </#if>
                            <option value="${applicationType.productFeatureApplTypeId}">${displayDesc}</option>
                        </#list>
                    </select> </div>
                    <input type="hidden" name="clearSearch" value="N"/>
                    <input type="submit" value="${uiLabelMap.ProductAddFeature}" class="btn btn-primary btn-sm"/>
        </div>
    </form>
</div>

<hr/>

<div class="panel panel-body">
    <form method="post" action="<@ofbizUrl>searchRemoveFeature</@ofbizUrl>" name="searchRemoveFeature" class="form-inline">
        <div class="form-group">

        <div class="text-info">${uiLabelMap.ProductRemoveFeatureFromResults}:</div><br/>
            <div class="input-group"><div class="input-group-addon"> <span>${uiLabelMap.ProductFeatureId}</span></div>
                <input type="text" class="form-control" size="10" name="productFeatureId" value=""/>
                </div>
        <input type="hidden" name="clearSearch" value="N"/>
        <input type="submit" value="${uiLabelMap.ProductRemoveFeature}" class="btn btn-primary btn-sm"/>
        </div>
    </form>
</div>

<hr/>

<div class="panel panel-body">
    <form method="post" action="" name="searchShowParams" class="form-inline">
        <#assign searchParams = Static["org.ofbiz.product.product.ProductSearchSession"].makeSearchParametersString(session)>
        <div class="form-group">
            <div class="input-group"><div class="input-group-addon">
        <span>${uiLabelMap.ProductPlainSearchParameters}:</span></div><input type="text" class="form-control" size="60" name="searchParameters" value="${StringUtil.wrapString(searchParams)}"/>
        </div>
            <div class="input-group"><div class="input-group-addon">
        <span>${uiLabelMap.ProductHtmlSearchParameters}:</span></div>
                <input type="text" class="form-control" size="60" name="searchParameters" value="${StringUtil.wrapString(searchParams)?html}"/>
                </div>
        <input type="hidden" name="clearSearch" value="N"/>
            </div>
    </form>
</div>

<hr/>

<div class="panel panel-body">

    <span>${uiLabelMap.ProductSearchExportProductList}:</span>
    <a href="<@ofbizUrl>searchExportProductList?clearSearch=N</@ofbizUrl>" class="btn btn-primary btn-sm">${uiLabelMap.ProductSearchExport}</a>
</div>
</#if>
