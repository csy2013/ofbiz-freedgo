<table cellspacing="0" class="basic-table">
    <tr>
        <td>
            <form method="post" action="<@ofbizUrl>category_addProductCategoryToProdCatalog</@ofbizUrl>" class="form-horizontal" name="addNewForm">
                <input type="hidden" name="productCategoryId" value="${productCategoryId?if_exists}"/>
                <div class="form-group">
                    <label class="control-label col-md-4">目录</label>
                    <div class="col-md-5 ">
                        <select name="prodCatalogId">
                        <#list prodCatalogs as prodCatalog>
                            <#assign hasCatalogId = false>
                            <#list prodCatalogCategories as prodCatalogCategory>
                                <#if prodCatalogCategory.prodCatalogId == prodCatalog.prodCatalogId>
                                    <#assign  hasCatalogId = true>
                                </#if>
                            </#list>
                            <#if !hasCatalogId>
                                <option value="${(prodCatalog.prodCatalogId)?if_exists}">${(prodCatalog.catalogName)?if_exists} [${(prodCatalog.prodCatalogId)?if_exists}]</option>
                            </#if>
                        </#list>
                        </select>
                    </div>
                </div>
               <div class="form-group">
                   <label class="control-label col-md-4">目录类型</label>
                   <div class="col-md-5 ">
                   <select name="prodCatalogCategoryTypeId" size="1">
                   <#list prodCatalogCategoryTypes as prodCatalogCategoryType>
                       <option value="${(prodCatalogCategoryType.prodCatalogCategoryTypeId)?if_exists}">${(prodCatalogCategoryType.get("description",locale))?if_exists}</option>
                   </#list>
                   </select>
                       </div>
               </div>
                <div class="form-group">
                    <label class="control-label col-md-4">开始时间</label>
                    <div class="col-md-5 ">
                    <@htmlTemplate.renderDateTimeField name="fromDate" event="" action="" className="" alert="" title="Format: yyyy-MM-dd HH:mm:ss.SSS" value="" size="25" maxlength="30" id="fromDate_1" dateType="date" shortDateInput=false timeDropdownParamName="" defaultDateTimeString="" localizedIconTitle="" timeDropdown="" timeHourName="" classString="" hour1="" hour2="" timeMinutesName="" minutes="" isTwelveHour="" ampmName="" amSelected="" pmSelected="" compositeType="" formName=""/>

                    </div>
                </div>
                <div class="form-group">
                    <div class="col-md-4">&nbsp;</div>
                    <div class="col-md-5 ">
                    <input type="submit" value="${uiLabelMap.CommonAdd}"/>
                        </div>
                </div>

            </form>
        </td>
    </tr>
</table>