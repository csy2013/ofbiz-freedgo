<#if productCategoryId?exists && productCategory?exists>
<div class="am-cf am-padding-xs">
    <div class="am-panel am-panel-default" id="screenlet_1">
        <div class="am-panel-hd am-cf">
        ${uiLabelMap.PageTitleEditCategoryProductCatalogs}
        </div>
        <div class="am-panel-bd am-collapse am-in" id="screenlet_1_col">
            <div class="am-g">
                <div class="am-u-sm-12">
                    <table class="am-table am-table-striped am-table-hover table-main">
                        <tr>
                            <td><b>${uiLabelMap.ProductCatalogNameId}</b></td>
                            <td><b>${uiLabelMap.CommonType}</b></td>
                            <td><b>${uiLabelMap.CommonFromDateTime}</b></td>
                            <td><b>${uiLabelMap.ProductThruDateTimeSequence}</b></td>
                            <td><b>&nbsp;</b></td>
                        </tr>
                        <#assign line = 0>
                        <#assign rowClass = "2">
                        <#list prodCatalogCategories as prodCatalogCategory>
                            <#assign line = line + 1>
                            <#assign prodCatalog = prodCatalogCategory.getRelatedOne("ProdCatalog")>
                            <#assign curProdCatalogCategoryType = prodCatalogCategory.getRelatedOneCache("ProdCatalogCategoryType")>
                            <tr <#if rowClass == "1"> class="am-active"</#if>>
                                <td>
                                    <a href="<@ofbizUrl>EditProdCatalog?prodCatalogId=${(prodCatalogCategory.prodCatalogId)?if_exists}</@ofbizUrl>"
                                       class="buttontext"><#if prodCatalog?exists>${(prodCatalog.catalogName)?if_exists}</#if>
                                        [${(prodCatalogCategory.prodCatalogId)?if_exists}]</a></td>
                                <td>
                                ${(curProdCatalogCategoryType.get("description",locale))?default(prodCatalogCategory.prodCatalogCategoryTypeId)}
                                </td>
                                <#assign hasntStarted = false>
                                <#if (prodCatalogCategory.getTimestamp("fromDate"))?exists && nowTimestamp.before(prodCatalogCategory.getTimestamp("fromDate"))> <#assign hasntStarted = true></#if>
                                <td <#if hasntStarted>
                                        style="color: red;"</#if>>${(prodCatalogCategory.fromDate)?if_exists}</td>
                                <td>
                                    <div class="am-g">
                                        <div class="am-u-lg-10">
                                            <form method="post" class="am-form am-form-inline"
                                                  action="<@ofbizUrl>category_updateProductCategoryToProdCatalog</@ofbizUrl>"
                                                  name="lineForm_update${line}">
                                                <#assign hasExpired = false>
                                                <#if (prodCatalogCategory.getTimestamp("thruDate"))?exists && nowTimestamp.after(prodCatalogCategory.getTimestamp("thruDate"))> <#assign hasExpired = true></#if>
                                                <input type="hidden" name="prodCatalogId"
                                                       value="${(prodCatalogCategory.prodCatalogId)?if_exists}"/>
                                                <input type="hidden" name="productCategoryId"
                                                       value="${(prodCatalogCategory.productCategoryId)?if_exists}"/>
                                                <input type="hidden" name="prodCatalogCategoryTypeId"
                                                       value="${prodCatalogCategory.prodCatalogCategoryTypeId}"/>
                                                <input type="hidden" name="fromDate"
                                                       value="${(prodCatalogCategory.fromDate)?if_exists}"/>
                                                <#if hasExpired><#assign class="alert"></#if>
                                                <@amazeHtmlTemplate.renderDateTimeField name="thruDate" event="" action="" className="${class!''}" alert="" title="Format: yyyy-MM-dd HH:mm:ss.SSS" value="${(prodCatalogCategory.thruDate)?if_exists}" size="25" maxlength="30" id="thruDate_1" dateType="date" shortDateInput=false timeDropdownParamName="" defaultDateTimeString="" localizedIconTitle="" timeDropdown="" timeHourName="" classString="" hour1="" hour2="" timeMinutesName="" minutes="" isTwelveHour="" ampmName="" amSelected="" pmSelected="" compositeType="" formName=""/>
                                                <div class="am-input-group">
                                                    <input type="text" size="5" name="sequenceNum"
                                                           value="${(prodCatalogCategory.sequenceNum)?if_exists}"/>
                                                </div>
                                                <div class="am-input-group">
                                                    <input type="submit" class="am-btn am-btn-primary am-btn-sm"
                                                           value="${uiLabelMap.CommonUpdate}"/>
                                                </div>
                                            </form>
                                        </div>
                                    </div>

                                </td>
                                <td>
                                    <form method="post"
                                          action="<@ofbizUrl>category_removeProductCategoryFromProdCatalog</@ofbizUrl>"
                                          name="lineForm_delete${line}">
                                        <input type="hidden" name="prodCatalogId"
                                               value="${(prodCatalogCategory.prodCatalogId)?if_exists}"/>
                                        <input type="hidden" name="productCategoryId"
                                               value="${(prodCatalogCategory.productCategoryId)?if_exists}"/>
                                        <input type="hidden" name="prodCatalogCategoryTypeId"
                                               value="${prodCatalogCategory.prodCatalogCategoryTypeId}"/>
                                        <input type="hidden" name="fromDate"
                                               value="${(prodCatalogCategory.fromDate)?if_exists}"/>
                                        <a href="javascript:document.lineForm_delete${line}.submit()"
                                           class="am-btn am-btn-primary am-btn-sm">${uiLabelMap.CommonDelete}</a>
                                    </form>
                                </td>
                            </tr>
                            <#if rowClass == "2">
                                <#assign rowClass = "1">
                            <#else>
                                <#assign rowClass = "2">
                            </#if>
                        </#list>
                    </table>
                </div>
            </div>
        </div>
    </div>
</div>

<div class="am-cf am-padding-xs">
    <div class="am-panel am-panel-default" id="screenlet_1">
        <div class="am-panel-hd am-cf">
        ${uiLabelMap.ProductAddCatalogProductCategory}
        </div>
        <div class="am-panel-bd am-collapse am-in" id="screenlet_1_col">
            <div class="am-g">
                <div class="am-u-sm-12">
                    <table class="am-table am-table-striped am-table-hover table-main">
                        <tr>
                            <td>
                                <form method="post"  class="am-form am-form-inline"
                                      action="<@ofbizUrl>category_addProductCategoryToProdCatalog</@ofbizUrl>"
                                      style="margin: 0;" name="addNewForm">
                                    <input type="hidden" name="productCategoryId"
                                           value="${productCategoryId?if_exists}"/>

                                    <div class="am-input-group">
                                        <select name="prodCatalogId" class="am-input-sm">
                                            <#list prodCatalogs as prodCatalog>
                                                <option value="${(prodCatalog.prodCatalogId)?if_exists}">${(prodCatalog.catalogName)?if_exists}
                                                    [${(prodCatalog.prodCatalogId)?if_exists}]
                                                </option>
                                            </#list>
                                        </select>
                                    </div>
                                    <div class="am-input-group">
                                        <select name="prodCatalogCategoryTypeId" size="1" class="am-input-sm">
                                            <#list prodCatalogCategoryTypes as prodCatalogCategoryType>
                                                <option value="${(prodCatalogCategoryType.prodCatalogCategoryTypeId)?if_exists}">${(prodCatalogCategoryType.get("description",locale))?if_exists}</option>
                                            </#list>
                                        </select>
                                    </div>
                                    <@amazeHtmlTemplate.renderDateTimeField name="fromDate" event="" action="" className="" alert="" title="Format: yyyy-MM-dd HH:mm:ss.SSS" value="" size="25" maxlength="30" id="fromDate_1" dateType="date" shortDateInput=false timeDropdownParamName="" defaultDateTimeString="" localizedIconTitle="" timeDropdown="" timeHourName="" classString="" hour1="" hour2="" timeMinutesName="" minutes="" isTwelveHour="" ampmName="" amSelected="" pmSelected="" compositeType="" formName=""/>
                                    <input type="submit" class="am-btn am-btn-primary am-btn-sm"
                                           value="${uiLabelMap.CommonAdd}"/>
                                </form>
                            </td>
                        </tr>
                    </table>
                </div>
            </div>
        </div>
    </div>
</div>
</#if>
