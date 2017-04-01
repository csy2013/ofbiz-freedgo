<#--
Licensed to the Apache Software Foundation (ASF) under one
or more contributor license agreements.  See the NOTICE file
distributed with this work for additional information
regarding copyright ownership.  The ASF licenses this file
to you under the Apache License, Version 2.0 (the
"License"); you may not use this file except in compliance
with the License.  You may obtain a copy of the License at

http://www.apache.org/licenses/LICENSE-2.0

Unless required by applicable law or agreed to in writing,
software distributed under the License is distributed on an
"AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
KIND, either express or implied.  See the License for the
specific language governing permissions and limitations
under the License.
-->

<#if productIds?has_content>
    <hr />
    <span class="label"><b>${uiLabelMap.ProductNote}:</b></span> ${uiLabelMap.ProductNoteKeywordSearch}
    <hr />

    ${screens.render("component://product/widget/catalog/ProductScreens.xml#CreateVirtualWithVariantsFormInclude")}

    <hr />

    <div class="am-u-lg-12">
        <form method="post" action="<@ofbizUrl>searchRemoveFromCategory</@ofbizUrl>" name="searchRemoveFromCategory" class="am-form am-form-horizontal">
            <div class="am-form-group am-g">
                <label class="am-control-label am-u-md-3 am-u-lg-3">${uiLabelMap.ProductRemoveResultsFrom} ${uiLabelMap.ProductCategory}:</label>
                <div class="am-u-md-7 am-u-lg-7 am-u-end">
                    <div class="am-form-group am-u-md-4 am-u-lg-4">
                        <@amazeHtmlTemplate.lookupField formName="searchRemoveFromCategory" name="SE_SEARCH_CATEGORY_ID" id="SE_SEARCH_CATEGORY_ID" fieldFormName="LookupProductCategory"/>
                    </div>
                    <input type="submit" value="${uiLabelMap.CommonRemove}" class="am-btn am-btn-primary am-btn-sm"/>
                </div>
            </div>
              <input type="hidden" name="clearSearch" value="N" />
        </form>
    </div>

    <hr/>

    <div class="am-u-lg-12">
        <form method="post" action="<@ofbizUrl>searchExpireFromCategory</@ofbizUrl>" name="searchExpireFromCategory" class="am-form am-form-horizontal">
            <div class="am-form-group am-g">
                <label class="am-control-label am-u-md-3 am-u-lg-3">${uiLabelMap.ProductExpireResultsFrom} ${uiLabelMap.ProductCategory}:</label>
                <div class="am-u-md-9 am-u-lg-9 am-u-end">
                    <div class="am-form-group am-u-md-3 am-u-lg-3">
                        <@amazeHtmlTemplate.lookupField formName="searchExpireFromCategory" name="SE_SEARCH_CATEGORY_ID" id="SE_SEARCH_CATEGORY_ID" fieldFormName="LookupProductCategory"/>
                    </div>
                    <label class="am-control-label am-u-md-1 am-u-lg-1">${uiLabelMap.CommonThru}:</label>
                    <div class="am-u-md-3 am-u-lg-3">
                        <@amazeHtmlTemplate.renderDateTimeField name="thruDate" event="" action="" className="" alert="" title="Format: yyyy-MM-dd HH:mm:ss.SSS" value="" size="25" maxlength="30" id="thruDate1" dateType="date" shortDateInput=false timeDropdownParamName="" defaultDateTimeString="" localizedIconTitle="" timeDropdown="" timeHourName="" classString="" hour1="" hour2="" timeMinutesName="" minutes="" isTwelveHour="" ampmName="" amSelected="" pmSelected="" compositeType="" formName=""/>
                    </div>
                    <input type="submit" value="${uiLabelMap.CommonExpire}" class="am-btn am-btn-primary am-btn-sm"/>
                </div>
            </div>
             <input type="hidden" name="clearSearch" value="N" />
            </form>
    </div>


    <div class="am-u-lg-12">
        <form method="post" action="<@ofbizUrl>searchAddToCategory</@ofbizUrl>" name="searchAddToCategory" class="am-form am-form-horizontal">
            <div class="am-form-group am-g">
                <label class="am-control-label am-u-md-3 am-u-lg-3">${uiLabelMap.ProductAddResultsTo} ${uiLabelMap.ProductCategory}:</label>
                <div class="am-u-md-9 am-u-lg-9 am-u-end">
                    <div class="am-form-group am-u-md-3 am-u-lg-3">
                        <@amazeHtmlTemplate.lookupField formName="searchAddToCategory" name="SE_SEARCH_CATEGORY_ID" id="SE_SEARCH_CATEGORY_ID" fieldFormName="LookupProductCategory"/>
                    </div>
                    <label class="am-control-label am-u-md-1 am-u-lg-1">${uiLabelMap.CommonFrom}:</label>
                    <div class="am-u-md-3 am-u-lg-3">
                        <@amazeHtmlTemplate.renderDateTimeField name="fromDate" event="" action="" className="" alert="" title="Format: yyyy-MM-dd HH:mm:ss.SSS" value="" size="25" maxlength="30" id="fromDate1" dateType="date" shortDateInput=false timeDropdownParamName="" defaultDateTimeString="" localizedIconTitle="" timeDropdown="" timeHourName="" classString="" hour1="" hour2="" timeMinutesName="" minutes="" isTwelveHour="" ampmName="" amSelected="" pmSelected="" compositeType="" formName=""/>
                    </div>
                    <input type="submit" value="${uiLabelMap.ProductAddToCategory}" class="am-btn am-btn-primary am-btn-sm"/>
                </div>
            </div>
            <input type="hidden" name="clearSearch" value="N" />
        </form>
    </div>


    <div class="am-u-lg-12">
        <form method="post" action="<@ofbizUrl>searchAddFeature</@ofbizUrl>" name="searchAddFeature" class="am-form am-form-horizontal">
            <div class="am-form-group am-g">
                <label class="am-control-label am-u-md-3 am-u-lg-3">${uiLabelMap.ProductAddFeatureToResults}:</label>
            </div>
            <div class="am-form-group am-g">
                <label class="am-control-label am-u-md-3 am-u-lg-3">${uiLabelMap.ProductFeatureId}</label>
                <div class="am-u-md-9 am-u-lg-9">
                    <div class="am-form-group am-u-md-3 am-u-lg-3">
                        <input type="text" size="10" name="productFeatureId" value="" />
                    </div>
                    <label class="am-control-label am-u-md-1 am-u-lg-1">${uiLabelMap.CommonFrom}</label>
                    <div class="am-u-md-3 am-u-lg-3">
                        <@amazeHtmlTemplate.renderDateTimeField name="fromDate" event="" action="" className="" alert="" title="Format: yyyy-MM-dd HH:mm:ss.SSS" value="" size="25" maxlength="30" id="fromDate2" dateType="date" shortDateInput=false timeDropdownParamName="" defaultDateTimeString="" localizedIconTitle="" timeDropdown="" timeHourName="" classString="" hour1="" hour2="" timeMinutesName="" minutes="" isTwelveHour="" ampmName="" amSelected="" pmSelected="" compositeType="" formName=""/>
                    </div>

                    <label class="am-control-label am-u-md-1 am-u-lg-1">${uiLabelMap.CommonThru}</label>
                    <div class="am-u-md-3 am-u-lg-3  am-u-end">
                        <@amazeHtmlTemplate.renderDateTimeField name="thruDate" event="" action="" className="" alert="" title="Format: yyyy-MM-dd HH:mm:ss.SSS" value="" size="25" maxlength="30" id="thruDate2" dateType="date" shortDateInput=false timeDropdownParamName="" defaultDateTimeString="" localizedIconTitle="" timeDropdown="" timeHourName="" classString="" hour1="" hour2="" timeMinutesName="" minutes="" isTwelveHour="" ampmName="" amSelected="" pmSelected="" compositeType="" formName=""/>
                    </div>
                </div>
            </div>
            <div class="am-form-group am-g">
                <label class="am-control-label am-u-md-3 am-u-lg-3">${uiLabelMap.CommonAmount}</label>
                <div class="am-u-md-9 am-u-lg-9">
                    <div class="am-form-group am-u-md-1 am-u-lg-1">
                        <input type="text" size="5" name="amount" value="" />
                    </div>
                    <label class="am-control-label am-u-md-1 am-u-lg-1">${uiLabelMap.CommonSequence}</label>
                    <div class="am-u-md-1 am-u-lg-1">
                        <input type="text" size="5" name="sequenceNum" value="" />
                    </div>

                    <label class="am-control-label am-u-md-3 am-u-lg-3">${uiLabelMap.ProductFeatureApplicationType}&nbsp;&nbsp;${uiLabelMap.ProductCategoryId}:</label>
                    <div class="am-u-md-3 am-u-lg-3  am-u-end">
                        <select name='productFeatureApplTypeId' size='1'>
                            <#list applicationTypes as applicationType>
                                <#assign displayDesc = applicationType.get("description", locale)?default("No Description")>
                                <#if 18 < displayDesc?length>
                                    <#assign displayDesc = displayDesc[0..15] + "...">
                                </#if>
                                <option value="${applicationType.productFeatureApplTypeId}">${displayDesc}</option>
                            </#list>
                        </select>
                    </div>
                    <input type="submit" value="${uiLabelMap.ProductAddFeature}" class="am-btn am-btn-primary am-btn-sm"/>
                </div>
            </div>
            <input type="hidden" name="clearSearch" value="N" />
        </form>
    </div>

    <div class="am-u-lg-12">
        <form method="post" action="<@ofbizUrl>searchRemoveFeature</@ofbizUrl>" name="searchRemoveFeature"  class="am-form am-form-horizontal">

            <div class="am-form-group am-g">
                <label class="am-control-label am-u-md-3 am-u-lg-3">${uiLabelMap.ProductRemoveFeatureFromResults}:&nbsp;&nbsp; ${uiLabelMap.ProductFeatureId}</label>
                <div class="am-u-md-7 am-u-lg-7 am-u-end">
                    <div class="am-form-group am-u-md-4 am-u-lg-4">
                        <input type="text" size="10" name="productFeatureId" value="" />
                    </div>
                    <input type="submit" value="${uiLabelMap.ProductRemoveFeature}" class="am-btn am-btn-primary am-btn-sm"/>
                </div>
            </div>
            <input type="hidden" name="clearSearch" value="N" />
        </form>
    </div>

    <hr />

    <#--<div>
        <form method="post" action="" name="searchShowParams" >
          <#assign searchParams = Static["org.ofbiz.product.product.ProductSearchSession"].makeSearchParametersString(session)>
          <span class="label">${uiLabelMap.ProductPlainSearchParameters}:</span><input type="text" size="60" name="searchParameters" value="${StringUtil.wrapString(searchParams)}" />
          <br />
          <span class="label">${uiLabelMap.ProductHtmlSearchParameters}:</span><input type="text" size="60" name="searchParameters" value="${StringUtil.wrapString(searchParams)?html}" />
          <input type="hidden" name="clearSearch" value="N" />
        </form>
    </div>

    <hr />

    <div>
      <span class="label">${uiLabelMap.ProductSearchExportProductList}:</span><a href="<@ofbizUrl>searchExportProductList?clearSearch=N</@ofbizUrl>" class="buttontext">${uiLabelMap.ProductSearchExport}</a>
    </div>-->
</#if>
