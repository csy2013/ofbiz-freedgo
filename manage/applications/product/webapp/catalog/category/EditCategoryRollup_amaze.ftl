<#if productCategoryId?has_content>
<div class="am-cf am-padding-xs">
<div class="am-panel am-panel-default">
<div class="am-panel-hd am-cf">${uiLabelMap.ProductCategoryRollupParentCategories}</div>
<div class="am-panel-bd am-collapse am-in">
<div class="am-g">
<div class="am-u-lg-12">
    <#if currentProductCategoryRollups.size() == 0>
        <table class="am-table am-table-striped am-table-hover table-main">
            <tr>
                <td><b>${uiLabelMap.ProductParentCategoryId}</b></td>
                <td><b>${uiLabelMap.CommonFromDate}</b></td>
                <td><span style="margin-left: 20px"><b>${uiLabelMap.ProductThruDateTimeSequence}</b></span></td>
                <td><b>&nbsp;</b></td>
            </tr>
            <tr valign="middle">
                <td colspan="4">${uiLabelMap.ProductNoParentCategoriesFound}.</td>
            </tr>
        </table>
    <#else>
        <form method="post" action="<@ofbizUrl>updateProductCategoryToCategory</@ofbizUrl>" name="updateProductCategoryToCategoryChild">
        <input type="hidden" name="showProductCategoryId" value="${productCategoryId}" />
        <table class="am-table am-table-striped am-table-hover table-main" >
            <tr>
                <td><b>${uiLabelMap.ProductParentCategoryId}</b></td>
                <td><b>${uiLabelMap.CommonFromDate}</b></td>
                <td><span style="margin-left: 20px"><b>${uiLabelMap.ProductThruDateTimeSequence}</b></span></td>
                <td><b>&nbsp;</b></td>
            </tr>
            <#assign rowClass = "2">
            <#list currentProductCategoryRollups as productCategoryRollup>
                <#assign suffix = "_o_" + productCategoryRollup_index>
                <#assign curCategory = productCategoryRollup.getRelatedOne("ParentProductCategory")>
                <#assign hasntStarted = false>
                <#if productCategoryRollup.fromDate?exists && nowTimestamp.before(productCategoryRollup.getTimestamp("fromDate"))><#assign hasntStarted = true></#if>
                <#assign hasExpired = false>
                <#if productCategoryRollup.thruDate?exists && nowTimestamp.after(productCategoryRollup.getTimestamp("thruDate"))><#assign hasExpired = true></#if>
                <tr<#if rowClass == "1"> class="am-active"</#if>>
                    <td><#if curCategory?has_content>
                        <a href="<@ofbizUrl>EditCategory?productCategoryId=${curCategory.productCategoryId}</@ofbizUrl>" >
                            <#assign catContentWrapper = Static["org.ofbiz.product.category.CategoryContentWrapper"].makeCategoryContentWrapper(curCategory, request)?if_exists>
                            <#if catContentWrapper?has_content>
                            ${catContentWrapper.get("CATEGORY_NAME")!catContentWrapper.get("DESCRIPTION")!curCategory.categoryName!curCategory.description!}
                            <#else>
                            ${curCategory.categoryName!curCategory.description!}
                            </#if>
                            [${curCategory.productCategoryId}]
                        </a>
                    </#if>
                    </td>
                    <td <#if hasntStarted>style="color: red;"</#if>>${productCategoryRollup.fromDate}</td>
                    <td colspan="2">
                        <input type="hidden" name="showProductCategoryId${suffix}" value="${productCategoryRollup.productCategoryId}" />
                        <input type="hidden" name="productCategoryId${suffix}" value="${productCategoryRollup.productCategoryId}" />
                        <input type="hidden" name="parentProductCategoryId${suffix}" value="${productCategoryRollup.parentProductCategoryId}" />
                        <input type="hidden" name="fromDate${suffix}" value="${productCategoryRollup.fromDate}" />
                        <div class="am-u-lg-5">
                            <@amazeHtmlTemplate.renderDateTimeField name="thruDate${suffix}" event="" action="" className="" alert="" title="Format: yyyy-MM-dd HH:mm:ss.SSS" value="${productCategoryRollup.thruDate!''}" size="25" maxlength="30" id="thruDate_1" dateType="date" shortDateInput=false timeDropdownParamName="" defaultDateTimeString="" localizedIconTitle="" timeDropdown="" timeHourName="" classString="" hour1="" hour2="" timeMinutesName="" minutes="" isTwelveHour="" ampmName="" amSelected="" pmSelected="" compositeType="" formName=""/>
                        </div>
                        <div class="am-u-lg-2">
                            <input type="text" class="am-form-field am-input-sm" size="5" name="sequenceNum${suffix}" value="${productCategoryRollup.sequenceNum?if_exists}" />
                        </div>
                        <div class="am-u-lg-3 am-u-end">
                            <a href="javascript:document.removeProductCategoryFromCategory_${productCategoryRollup_index}.submit();" class="am-btn am-btn-primary am-btn-sm">${uiLabelMap.CommonDelete}</a>
                        </div>

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
                <td colspan="4" align="center">
                    <input type="submit" class="am-btn am-btn-primary am-btn-sm"  value="${uiLabelMap.CommonUpdate}" />
                    <input type="hidden" value="${currentProductCategoryRollups.size()}" name="_rowCount" />
                </td>
            </tr>
        </table>

    </form>
    <#list currentProductCategoryRollups as productCategoryRollup>
        <form name="removeProductCategoryFromCategory_${productCategoryRollup_index}" method="post" action="<@ofbizUrl>removeProductCategoryFromCategory</@ofbizUrl>">
            <input type="hidden" name="showProductCategoryId" value="${productCategoryId}"/>
            <input type="hidden" name="productCategoryId" value="${productCategoryRollup.productCategoryId}"/>
            <input type="hidden" name="parentProductCategoryId" value="${productCategoryRollup.parentProductCategoryId}"/>
            <input type="hidden" name="fromDate" value="${productCategoryRollup.fromDate}"/>
        </form>
    </#list>
    </#if>
</div>
</div>
</div>
</div>
</div>

<!-- 添加上级分类 -->
<div class="am-cf am-padding-xs">
    <div class="am-panel am-panel-default">
        <div class="am-panel-hd am-cf">${uiLabelMap.ProductAddCategoryParent} ${uiLabelMap.ProductCategorySelectCategoryAndEnterFromDate}:</div>
        <div class="am-panel-bd am-collapse am-in">
            <div class="am-g">
                <div class="am-u-lg-12">
                    <form method="post" action="<@ofbizUrl>addProductCategoryToCategory</@ofbizUrl>"  name="addParentForm" class="am-form am-form-horizontal">
                        <input type="hidden" name="productCategoryId" value="${productCategoryId}" />
                        <input type="hidden" name="showProductCategoryId" value="${productCategoryId}" />
                        <div class="am-form-group am-g">
                            <div class="am-u-md-2 am-u-lg-2">
                                <@amazeHtmlTemplate.lookupField value="${requestParameters.SEARCH_CATEGORY_ID?if_exists}" formName="addParentForm" name="parentProductCategoryId" id="parentProductCategoryId" fieldFormName="LookupProductCategory"/>
                            </div>
                            <div class="am-u-md-2 am-u-lg-2 am-u-end">
                                <@amazeHtmlTemplate.renderDateTimeField name="fromDate" event="" action="" className="" alert="" title="Format: yyyy-MM-dd HH:mm:ss.SSS" value="" size="25" maxlength="30" id="fromDate_1" dateType="date" shortDateInput=false timeDropdownParamName="" defaultDateTimeString="" localizedIconTitle="" timeDropdown="" timeHourName="" classString="" hour1="" hour2="" timeMinutesName="" minutes="" isTwelveHour="" ampmName="" amSelected="" pmSelected="" compositeType="" formName=""/>
                            </div>
                            <input type="submit" class="am-btn am-btn-primary am-btn-sm" value="${uiLabelMap.CommonAdd}"/>
                        </div>
                    </form>
                </div>
            </div>
        </div>
    </div>
</div>


<div class="am-cf am-padding-xs">
    <div class="am-panel am-panel-default">
        <div class="am-panel-hd am-cf">${uiLabelMap.ProductCategoryRollupChildCategories}</div>
        <div class="am-panel-bd am-collapse am-in">
            <div class="am-g">
                <div class="am-u-lg-12">
                <#if currentProductCategoryRollups.size() == 0>
                    <table class="am-table am-table-striped am-table-hover table-main">
                        <tr>
                            <td><b>${uiLabelMap.ProductChildCategoryId}</b></td>
                            <td><b>${uiLabelMap.CommonFromDate}</b></td>
                            <td><span style="margin-left: 20px"><b>${uiLabelMap.ProductThruDateTimeSequence}</b></span></td>
                            <td><b>&nbsp;</b></td>
                        </tr>
                        <tr valign="middle">
                            <td colspan="4">${uiLabelMap.ProductNoParentCategoriesFound}.</td>
                        </tr>
                    </table>
                <#else>
                    <form method="post" action="<@ofbizUrl>updateProductCategoryToCategory</@ofbizUrl>" name="updateProductCategoryToCategoryChild">
                        <input type="hidden" name="showProductCategoryId" value="${productCategoryId}" />
                        <table class="am-table am-table-striped am-table-hover table-main" >
                            <tr>
                                <td><b>${uiLabelMap.ProductChildCategoryId}</b></td>
                                <td><b>${uiLabelMap.CommonFromDate}</b></td>
                                <td><span style="margin-left: 20px"><b>${uiLabelMap.ProductThruDateTimeSequence}</b></span></td>
                                <td><b>&nbsp;</b></td>
                            </tr>
                            <#assign lineChild = 0>
                            <#assign rowClass = "2">
                            <#list parentProductCategoryRollups as productCategoryRollup>
                                <#assign suffix = "_o_" + productCategoryRollup_index>
                                <#assign lineChild = lineChild + 1>
                                <#assign curCategory = productCategoryRollup.getRelatedOne("CurrentProductCategory")>
                                <#assign hasntStarted = false>
                                <#if productCategoryRollup.fromDate?exists && nowTimestamp.before(productCategoryRollup.getTimestamp("fromDate"))><#assign hasntStarted = true></#if>
                                <#assign hasExpired = false>
                                <#if productCategoryRollup.thruDate?exists && nowTimestamp.after(productCategoryRollup.getTimestamp("thruDate"))><#assign hasExpired = true></#if>
                                <tr<#if rowClass == "1"> class="am-active"</#if>>
                                    <td><#if curCategory?has_content>
                                        <a href="<@ofbizUrl>EditCategory?productCategoryId=${curCategory.productCategoryId}</@ofbizUrl>" >
                                            <#assign catContentWrapper = Static["org.ofbiz.product.category.CategoryContentWrapper"].makeCategoryContentWrapper(curCategory, request)?if_exists>
                                            <#if catContentWrapper?has_content>
                                            ${catContentWrapper.get("CATEGORY_NAME")!catContentWrapper.get("DESCRIPTION")!curCategory.categoryName!curCategory.description!}
                                            <#else>
                                            ${curCategory.categoryName!curCategory.description!}
                                            </#if>
                                            [${curCategory.productCategoryId}]
                                        </a>
                                    </#if>
                                    </td>
                                    <td <#if hasntStarted>style="color: red;"</#if>>${productCategoryRollup.fromDate}</td>
                                    <td colspan="2">
                                        <input type="hidden" name="productCategoryId${suffix}" value="${productCategoryRollup.productCategoryId}" />
                                        <input type="hidden" name="parentProductCategoryId${suffix}" value="${productCategoryRollup.parentProductCategoryId}" />
                                        <input type="hidden" name="fromDate${suffix}" value="${productCategoryRollup.fromDate}" />
                                        <div class="am-u-lg-5">
                                            <@amazeHtmlTemplate.renderDateTimeField name="thruDate${suffix}" event="" action="" className="" alert="" title="Format: yyyy-MM-dd HH:mm:ss.SSS" value="${productCategoryRollup.thruDate!''}" size="25" maxlength="30" id="thruDatefromDate${suffix}" dateType="date" shortDateInput=false timeDropdownParamName="" defaultDateTimeString="" localizedIconTitle="" timeDropdown="" timeHourName="" classString="" hour1="" hour2="" timeMinutesName="" minutes="" isTwelveHour="" ampmName="" amSelected="" pmSelected="" compositeType="" formName=""/>
                                        </div>
                                        <div class="am-u-lg-2">
                                            <input type="text" class="am-form-field am-input-sm" size="5" name="sequenceNum${suffix}" value="${productCategoryRollup.sequenceNum?if_exists}" />
                                        </div>
                                        <div class="am-u-lg-3 am-u-end">
                                            <a href="javascript:document.removeProductCategoryFromCategory_1_${productCategoryRollup_index}.submit();" class="am-btn am-btn-primary am-btn-sm">${uiLabelMap.CommonDelete}</a>
                                        </div>

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
                                <td colspan="4" align="center">
                                    <input type="submit" class="am-btn am-btn-primary am-btn-sm"  value="${uiLabelMap.CommonUpdate}" />
                                    <input type="hidden" value="${lineChild}" name="_rowCount" />
                                </td>
                            </tr>
                        </table>
                    </form>
                    <#list parentProductCategoryRollups as productCategoryRollup>
                        <form name="removeProductCategoryFromCategory_1_${productCategoryRollup_index}" method="post" action="<@ofbizUrl>removeProductCategoryFromCategory</@ofbizUrl>">
                            <input type="hidden" name="showProductCategoryId" value="${productCategoryId}"/>
                            <input type="hidden" name="productCategoryId" value="${productCategoryRollup.productCategoryId}"/>
                            <input type="hidden" name="parentProductCategoryId" value="${productCategoryRollup.parentProductCategoryId}"/>
                            <input type="hidden" name="fromDate" value="${productCategoryRollup.fromDate}"/>
                        </form>
                    </#list>
                </#if>
                </div>
            </div>
        </div>
    </div>
</div>

<!-- 添加下级分类 -->
<div class="am-cf am-padding-xs">
<div class="am-panel am-panel-default">
    <div class="am-panel-hd am-cf">${uiLabelMap.ProductAddCategoryChild} ${uiLabelMap.ProductCategorySelectCategoryAndEnterFromDate}:</div>
<div class="am-panel-bd am-collapse am-in">
<div class="am-g">
<div class="am-u-lg-12">
    <form method="post" action="<@ofbizUrl>addProductCategoryToCategory</@ofbizUrl>"  name="addChildForm" class="am-form am-form-horizontal">
        <input type="hidden" name="showProductCategoryId" value="${productCategoryId}" />
        <input type="hidden" name="parentProductCategoryId" value="${productCategoryId}" />
        <div class="am-form-group am-g">
            <div class="am-u-md-2 am-u-lg-2">
                <@amazeHtmlTemplate.lookupField value="${requestParameters.SEARCH_CATEGORY_ID?if_exists}"  formName="addChildForm" name="productCategoryId" id="productCategoryId" fieldFormName="LookupProductCategory"/>
            </div>
            <div class="am-u-md-2 am-u-lg-2 am-u-end">
                <@amazeHtmlTemplate.renderDateTimeField name="fromDate" event="" action="" className="" alert="" title="Format: yyyy-MM-dd HH:mm:ss.SSS" value="" size="25" maxlength="30" id="fromDate_2" dateType="date" shortDateInput=false timeDropdownParamName="" defaultDateTimeString="" localizedIconTitle="" timeDropdown="" timeHourName="" classString="" hour1="" hour2="" timeMinutesName="" minutes="" isTwelveHour="" ampmName="" amSelected="" pmSelected="" compositeType="" formName=""/>
            </div>
            <input type="submit" class="am-btn am-btn-primary am-btn-sm" value="${uiLabelMap.CommonAdd}"/>
        </div>
    </form>
</div>
</div>
</div>
</div>
</div>



</#if>
