<div class="am-cf am-padding-xs">
    <div class="am-panel am-panel-default">
        <div class="am-panel-hd am-cf">${uiLabelMap.PageTitleEditProductFeatures}</div>
        <div class="am-panel-bd am-collapse am-in">
            <div class="am-g">
                <div class="am-u-lg-12">
                    <form method="post" action="<@ofbizUrl>UpdateFeatureToProductApplication</@ofbizUrl>"
                          name="selectAllForm">
                        <input type="hidden" name="_useRowSubmit" value="Y"/>
                        <input type="hidden" name="_checkGlobalScope" value="Y"/>
                        <input type="hidden" name="productId" value="${productId}"/>
                        <table class="am-table am-table-striped am-table-hover table-main">
                            <tr>
                                <td><b>${uiLabelMap.CommonId}</b></td>
                                <td><b>${uiLabelMap.CommonDescription}</b></td>
                                <td><b>${uiLabelMap.ProductUomId}</b></td>
                                <td><b>${uiLabelMap.ProductType}</b></td>
                                <td><b>${uiLabelMap.ProductCategory}</b></td>
                                <td><b>${uiLabelMap.CommonFromDate}</b></td>
                                <td><b>${uiLabelMap.ProductThruDateAmountSequenceApplicationType}</b></td>
                                <td><b>${uiLabelMap.CommonAll} &nbsp;<input type="checkbox" name="selectAll"
                                                                            value="${uiLabelMap.CommonY}"
                                                                            onclick="toggleAll(this, 'selectAllForm');highlightAllRows(this, 'productFeatureId_tableRow_', 'selectAllForm');"/></b>
                                </td>
                                <td/>
                            </tr>
                        <#assign rowClass = "2">
                        <#list productFeatureAndAppls as productFeatureAndAppl>
                            <#if productFeatureAndAppl.uomId?exists>
                                <#assign curProductFeatureUom = delegator.findOne("Uom",{"uomId",productFeatureAndAppl.uomId}, true)>
                            </#if>
                            <#assign curProductFeatureType = productFeatureAndAppl.getRelatedOneCache("ProductFeatureType")>
                            <#assign curProductFeatureApplType = productFeatureAndAppl.getRelatedOneCache("ProductFeatureApplType")>
                            <#assign curProductFeatureCategory = (productFeatureAndAppl.getRelatedOneCache("ProductFeatureCategory")?if_exists)>
                            <tr id="productFeatureId_tableRow_${productFeatureAndAppl_index}"
                                valign="middle"<#if rowClass == "1"> class="am-active"</#if>>
                                <td>
                                    <input type="hidden" name="productId_o_${productFeatureAndAppl_index}"
                                           value="${(productFeatureAndAppl.productId)?if_exists}"/>
                                    <input type="hidden" name="productFeatureId_o_${productFeatureAndAppl_index}"
                                           value="${(productFeatureAndAppl.productFeatureId)?if_exists}"/>
                                    <input type="hidden" name="fromDate_o_${productFeatureAndAppl_index}"
                                           value="${(productFeatureAndAppl.fromDate)?if_exists}"/>
                                    <a href="<@ofbizUrl>EditFeature?productFeatureId=${(productFeatureAndAppl.productFeatureId)?if_exists}</@ofbizUrl>">
                                    ${(productFeatureAndAppl.productFeatureId)?if_exists}</a></td>
                                <td>${(productFeatureAndAppl.get("description",locale))?if_exists}</td>
                                <td><#if productFeatureAndAppl.uomId?exists>${curProductFeatureUom.abbreviation!}</#if>
                                    &nbsp;</td>
                                <td>${(curProductFeatureType.get("description",locale))?default((productFeatureAndAppl.productFeatureTypeId)?if_exists)}</td>
                                <td>
                                    <a href="<@ofbizUrl>EditFeatureCategoryFeatures?productFeatureCategoryId=${(productFeatureAndAppl.productFeatureCategoryId)?if_exists}&amp;productId=${(productFeatureAndAppl.productId)?if_exists}</@ofbizUrl>">
                                    ${(curProductFeatureCategory.description)?if_exists}
                                        [${(productFeatureAndAppl.productFeatureCategoryId)?if_exists}]</a></td>
                                <#assign hasntStarted = false>
                                <#if (productFeatureAndAppl.getTimestamp("fromDate"))?exists && Static["org.ofbiz.base.util.UtilDateTime"].nowTimestamp().before(productFeatureAndAppl.getTimestamp("fromDate"))> <#assign hasntStarted = true></#if>
                                <td <#if hasntStarted>
                                        style='color: red;'</#if>>${(productFeatureAndAppl.fromDate)?if_exists}</td>
                                <td>
                                    <#assign hasExpired = false>
                                    <#if (productFeatureAndAppl.getTimestamp("thruDate"))?exists && Static["org.ofbiz.base.util.UtilDateTime"].nowTimestamp().after(productFeatureAndAppl.getTimestamp("thruDate"))> <#assign hasExpired = true></#if>
                                    <#if hasExpired><#assign class="alert"></#if>
                                    <div class="am-u-lg-5">
                                        <@amazeHtmlTemplate.renderDateTimeField name="thruDate_o_${productFeatureAndAppl_index}" event="" action="" className="${class!''}" alert="" title="Format: yyyy-MM-dd HH:mm:ss.SSS" value="${(productFeatureAndAppl.thruDate)?if_exists}" size="25" maxlength="30" id="thruDate_o_${productFeatureAndAppl_index}" dateType="date" shortDateInput=false timeDropdownParamName="" defaultDateTimeString="" localizedIconTitle="" timeDropdown="" timeHourName="" classString="" hour1="" hour2="" timeMinutesName="" minutes="" isTwelveHour="" ampmName="" amSelected="" pmSelected="" compositeType="" formName=""/>
                                    </div>
                                    <div class="am-u-lg-2">
                                        <input type="text" size='6' class="am-form-field am-input-sm"
                                               name='amount_o_${productFeatureAndAppl_index}'
                                               value='${(productFeatureAndAppl.amount)?if_exists}'/>
                                    </div>
                                    <div class="am-u-lg-2 ">
                                        <input type="text" size='5' class="am-form-field am-input-sm"
                                               name='sequenceNum_o_${productFeatureAndAppl_index}'
                                               value='${(productFeatureAndAppl.sequenceNum)?if_exists}'/>
                                    </div>

                                    <div class="am-u-lg-3 am-u-end">
                                        <select data-am-selected="{btnWidth:80, btnStyle:'default'}"
                                                name='productFeatureApplTypeId_o_${productFeatureAndAppl_index}'
                                                size="1">
                                            <#if (productFeatureAndAppl.productFeatureApplTypeId)?exists>
                                                <option value='${(productFeatureAndAppl.productFeatureApplTypeId)?if_exists}'><#if curProductFeatureApplType?exists> ${(curProductFeatureApplType.get("description",locale))?if_exists} <#else>
                                                    [${productFeatureAndAppl.productFeatureApplTypeId}]</#if></option>
                                                <option value='${productFeatureAndAppl.productFeatureApplTypeId}'></option>
                                            </#if>
                                            <#list productFeatureApplTypes as productFeatureApplType>
                                                <option value='${(productFeatureApplType.productFeatureApplTypeId)?if_exists}'>${(productFeatureApplType.get("description",locale))?if_exists} </option>
                                            </#list>
                                        </select>
                                    </div>

                                </td>
                                <td>
                                    &nbsp; &nbsp; &nbsp; &nbsp;
                                    <input type="checkbox" name="_rowSubmit_o_${productFeatureAndAppl_index}" value="Y"
                                           onclick="checkToggle(this, 'selectAllForm');highlightRow(this,'productFeatureId_tableRow_${productFeatureAndAppl_index}');"/>
                                </td>
                                <td>
                                    <a href="javascript:document.RemoveFeatureFromProduct_o_${productFeatureAndAppl_index}.submit()"
                                       class="am-btn am-btn-primary am-btn-sm">${uiLabelMap.CommonDelete}</a>
                                </td>
                            </tr>
                        <#-- toggle the row color -->
                            <#if rowClass == "2">
                                <#assign rowClass = "1">
                            <#else>
                                <#assign rowClass = "2">
                            </#if>
                        </#list>

                            <tr>
                                <td colspan="9" align="center">
                                    <input type="hidden" name="_rowCount" value="${productFeatureAndAppls.size()}"/>
                                    <input type="submit" value='${uiLabelMap.CommonUpdate}'
                                           class="am-btn am-btn-primary am-btn-sm"/>
                                </td>
                            </tr>
                        </table>
                    </form>
                </div>
            </div>
        </div>
    </div>
</div>


<div class="am-cf am-padding-xs">
    <div class="am-panel am-panel-default">
        <div class="am-panel-hd am-cf">${uiLabelMap.ProductAddProductFeatureFromCategory}</div>
        <div class="am-panel-bd am-collapse am-in">
            <div class="am-g">
                <div class="am-u-lg-12">
                    <form method="post" action="<@ofbizUrl>ApplyFeaturesFromCategory</@ofbizUrl>"
                          class="am-form am-form-horizontal">
                        <input type="hidden" name="productId" value="${productId}"/>

                        <div class="am-form-group am-g">
                            <div class="am-u-md-2 am-u-lg-2">
                                <select data-am-selected="{btnWidth:240, btnStyle:'default'}"
                                        name='productFeatureCategoryId' size="1">
                                    <option value=''
                                            selected="selected">${uiLabelMap.ProductChooseFeatureCategory}</option>
                                <#list productFeatureCategories as productFeatureCategory>
                                    <option value='${(productFeatureCategory.productFeatureCategoryId)?if_exists}'>${(productFeatureCategory.description)?if_exists}
                                        [${(productFeatureCategory.productFeatureCategoryId)?if_exists}]
                                    </option>
                                </#list>
                                </select>
                            </div>
                            <div class="am-u-md-2 am-u-lg-2">
                                <select data-am-selected="{btnWidth:300, btnStyle:'default'}"
                                        name='productFeatureGroupId' size="1">
                                    <option value=''
                                            selected="selected">${uiLabelMap.ProductChooseFeatureGroup}</option>
                                <#list productFeatureGroups as productFeatureGroup>
                                    <option value='${(productFeatureGroup.productFeatureGroupId)?if_exists}'>${(productFeatureGroup.description)?if_exists}
                                        [${(productFeatureGroup.productFeatureGroupId)?if_exists}]
                                    </option>
                                </#list>
                                </select>
                            </div>

                            <div class="am-u-md-6 am-u-lg-6 am-u-end">
                                <label class="am-control-label am-u-md-3 am-u-lg-3">
                                ${uiLabelMap.ProductFeatureApplicationType}:
                                </label>

                                <div class="am-u-md-2 am-u-lg-2 am-u-end">
                                    <select data-am-selected="{btnWidth:100}"
                                            name='productFeatureApplTypeId' size="1">
                                    <#list productFeatureApplTypes as productFeatureApplType>
                                        <option value='${(productFeatureApplType.productFeatureApplTypeId)?if_exists}'
                                                <#if (productFeatureApplType.productFeatureApplTypeId?exists && product?exists && product.isVirtual == 'Y' && productFeatureApplType.productFeatureApplTypeId =="SELECTABLE_FEATURE")>selected="selected"</#if>
                                                <#if (productFeatureApplType.productFeatureApplTypeId?exists && product?exists && product.isVirtual == 'N' && productFeatureApplType.productFeatureApplTypeId?if_exists =="STANDARD_FEATURE")>selected="selected"</#if>
                                                >${(productFeatureApplType.get("description",locale))?if_exists} </option>
                                    </#list>
                                    </select>
                                </div>

                                <div class="am-u-md-1 am-u-lg-1 am-u-end">
                                    <input type="submit" class="am-btn am-btn-primary am-btn-sm"
                                           value="${uiLabelMap.CommonAdd}"/>
                                </div>
                            </div>
                        </div>
                    </form>
                </div>
            </div>
        </div>
    </div>
</div>



<div class="am-cf am-padding-xs">
    <div class="am-panel am-panel-default">
        <div class="am-panel-hd am-cf">${uiLabelMap.ProductAddProductFeatureTypeId}</div>
        <div class="am-panel-bd am-collapse am-in">
            <div class="am-g">
                <div class="am-u-lg-12">
                    <form method="post" action="<@ofbizUrl>ApplyFeatureToProductFromTypeAndCode</@ofbizUrl>"
                          name='addFeatureByTypeIdCode' class="am-form am-form-horizontal">
                        <input type="hidden" name="productId" value="${productId}"/>

                        <div class="am-form-group am-g">
                            <label class="am-control-label am-u-md-2 am-u-lg-2">${uiLabelMap.ProductFeatureType}:</label>

                            <div class="am-u-md-2 am-u-lg-2 am-u-end">
                                <select data-am-selected="{btnWidth:226}" name="productFeatureTypeId" size="1">
                                <#list productFeatureTypes as productFeatureType>
                                    <option value='${(productFeatureType.productFeatureTypeId)?if_exists}'>${(productFeatureType.get("description",locale))?if_exists} </option>
                                </#list>
                                </select>
                            </div>
                        </div>

                        <div class="am-form-group am-g">
                            <label class="am-control-label am-u-md-2 am-u-lg-2">${uiLabelMap.CommonId}:</label>

                            <div class="am-u-md-2 am-u-lg-2"> <input class="am-form-field am-input-sm" type="text" size='10' name='idCode' value=''/></div>

                            <label class="am-control-label am-u-md-2 am-u-lg-2">${uiLabelMap.ProductFeatureApplicationType}:</label>

                            <div class="am-u-md-2 am-u-lg-2">
                                <select data-am-selected="{btnWidth:226}" name="productFeatureApplTypeId" size="1">
                                <#list productFeatureApplTypes as productFeatureApplType>
                                    <option value='${(productFeatureApplType.productFeatureApplTypeId)?if_exists}'
                                            <#if (productFeatureApplType.productFeatureApplTypeId?exists && product?exists && product.isVirtual == 'Y' && productFeatureApplType.productFeatureApplTypeId =="SELECTABLE_FEATURE")>selected="selected"</#if>
                                            <#if (productFeatureApplType.productFeatureApplTypeId?exists && product?exists && product.isVirtual == 'N' && productFeatureApplType.productFeatureApplTypeId =="STANDARD_FEATURE")>selected="selected"</#if>
                                            >${(productFeatureApplType.get("description",locale))?if_exists} </option>
                                </#list>
                                </select>
                            </div>
                            <label class="am-control-label am-u-md-2 am-u-lg-2">&nbsp;</label>

                            <div class="am-u-md-2 am-u-lg-2">&nbsp;</div>
                        </div>

                        <div class="am-form-group am-g">
                            <label class="am-control-label am-u-md-2 am-u-lg-2">${uiLabelMap.CommonFrom}:</label>

                            <div class="am-u-md-2 am-u-lg-2">
                            <@amazeHtmlTemplate.renderDateTimeField name="fromDate" event="" action="" className="" alert="" title="Format: yyyy-MM-dd HH:mm:ss.SSS" value="" size="25" maxlength="30" id="fromDate1" dateType="date" shortDateInput=false timeDropdownParamName="" defaultDateTimeString="" localizedIconTitle="" timeDropdown="" timeHourName="" classString="" hour1="" hour2="" timeMinutesName="" minutes="" isTwelveHour="" ampmName="" amSelected="" pmSelected="" compositeType="" formName=""/>
                            </div>

                            <label class="am-control-label am-u-md-2 am-u-lg-2">${uiLabelMap.CommonThru} : </label>

                            <div class="am-u-md-2 am-u-lg-2 am-u-end">
                            <@amazeHtmlTemplate.renderDateTimeField name="thruDate" event="" action="" className="" alert="" title="Format: yyyy-MM-dd HH:mm:ss.SSS" value="" size="25" maxlength="30" id="thruDate1" dateType="date" shortDateInput=false timeDropdownParamName="" defaultDateTimeString="" localizedIconTitle="" timeDropdown="" timeHourName="" classString="" hour1="" hour2="" timeMinutesName="" minutes="" isTwelveHour="" ampmName="" amSelected="" pmSelected="" compositeType="" formName=""/>
                            </div>
                        </div>

                        <div class="am-form-group am-g">
                            <label class="am-control-label am-u-md-2 am-u-lg-2">${uiLabelMap.CommonSequence} :</label>
                            <div class="am-u-md-2 am-u-lg-2 am-u-end">
                                <input class="am-form-field am-input-sm" type="text" size="5" name="sequenceNum"/>
                            </div>
                            <label class="am-control-label am-u-md-2 am-u-lg-2">&nbsp;</label>
                            <div class="am-u-md-2 am-u-lg-2 am-u-end">
                                <input type="submit" class="am-btn am-btn-primary am-btn-sm" value="${uiLabelMap.CommonAdd}"/>
                            </div>

                        </div>
                    </form>
                </div>
            </div>
        </div>
    </div>
</div>


<div class="am-cf am-padding-xs">
    <div class="am-panel am-panel-default">
        <div class="am-panel-hd am-cf">${uiLabelMap.ProductAddProductFeatureID}</div>
        <div class="am-panel-bd am-collapse am-in">
            <div class="am-g">
                <div class="am-u-lg-12">
                    <form method="post" action="<@ofbizUrl>ApplyFeatureToProduct</@ofbizUrl>"
                          name='addFeatureById' class="am-form am-form-horizontal">
                        <input type="hidden" name="productId" value="${productId}"/>

                        <div class="am-form-group am-g">
                            <label class="am-control-label am-u-md-2 am-u-lg-2">${uiLabelMap.CommonId}:</label>

                            <div class="am-u-md-2 am-u-lg-2"> <@amazeHtmlTemplate.lookupField formName="addFeatureById" name="productFeatureId" id="productFeatureId" fieldFormName="LookupProductFeature"/></div>

                            <label class="am-control-label am-u-md-2 am-u-lg-2">${uiLabelMap.ProductFeatureApplicationType}:</label>

                            <div class="am-u-md-2 am-u-lg-2">
                                <select data-am-selected="{btnWidth:240}" name="productFeatureApplTypeId" size="1">
                                <#list productFeatureApplTypes as productFeatureApplType>
                                    <option value='${(productFeatureApplType.productFeatureApplTypeId)?if_exists}'
                                            <#if (productFeatureApplType.productFeatureApplTypeId?exists && product?exists && product.isVirtual == 'Y' && productFeatureApplType.productFeatureApplTypeId =="SELECTABLE_FEATURE")>selected="selected"</#if>
                                            <#if (productFeatureApplType.productFeatureApplTypeId?exists && product?exists && product.isVirtual == 'N' && productFeatureApplType.productFeatureApplTypeId =="STANDARD_FEATURE")>selected="selected"</#if>
                                            >${(productFeatureApplType.get("description",locale))?if_exists} </option>
                                </#list>
                                </select>
                            </div>
                            <label class="am-control-label am-u-md-2 am-u-lg-2">&nbsp;</label>

                            <div class="am-u-md-2 am-u-lg-2">&nbsp;</div>
                        </div>

                        <div class="am-form-group am-g">
                            <label class="am-control-label am-u-md-2 am-u-lg-2">${uiLabelMap.CommonFrom}:</label>

                            <div class="am-u-md-2 am-u-lg-2">
                                <@amazeHtmlTemplate.renderDateTimeField name="fromDate" event="" action="" className="" alert="" title="Format: yyyy-MM-dd HH:mm:ss.SSS" value="" size="25" maxlength="30" id="fromDate2" dateType="date" shortDateInput=false timeDropdownParamName="" defaultDateTimeString="" localizedIconTitle="" timeDropdown="" timeHourName="" classString="" hour1="" hour2="" timeMinutesName="" minutes="" isTwelveHour="" ampmName="" amSelected="" pmSelected="" compositeType="" formName=""/>
                            </div>

                            <label class="am-control-label am-u-md-2 am-u-lg-2">${uiLabelMap.CommonThru} : </label>

                            <div class="am-u-md-2 am-u-lg-2 am-u-end">
                                <@amazeHtmlTemplate.renderDateTimeField name="thruDate" event="" action="" className="" alert="" title="Format: yyyy-MM-dd HH:mm:ss.SSS" value="" size="25" maxlength="30" id="thruDate2" dateType="date" shortDateInput=false timeDropdownParamName="" defaultDateTimeString="" localizedIconTitle="" timeDropdown="" timeHourName="" classString="" hour1="" hour2="" timeMinutesName="" minutes="" isTwelveHour="" ampmName="" amSelected="" pmSelected="" compositeType="" formName=""/>
                            </div>
                        </div>

                        <div class="am-form-group am-g">
                            <label class="am-control-label am-u-md-2 am-u-lg-2">${uiLabelMap.CommonSequence} :</label>
                            <div class="am-u-md-2 am-u-lg-2 am-u-end">
                                <input class="am-form-field am-input-sm" type="text" size="5" name="sequenceNum"/>
                            </div>
                            <label class="am-control-label am-u-md-2 am-u-lg-2">&nbsp;</label>
                            <div class="am-u-md-2 am-u-lg-2 am-u-end">
                                <input type="submit" class="am-btn am-btn-primary am-btn-sm" value="${uiLabelMap.CommonAdd}"/>
                            </div>

                        </div>
                    </form>
                </div>
            </div>
        </div>
    </div>
</div>