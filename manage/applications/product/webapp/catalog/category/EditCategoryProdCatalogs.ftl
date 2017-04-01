<#if productCategoryId?exists && productCategory?exists>
    <@htmlScreenTemplate.renderScreenletBegin id="" title="${uiLabelMap.PageTitleEditCategoryProductCatalogs}" collapsible="" showMore=true/>
    <@htmlScreenTemplate.renderModalPage id="addCategroyProdCatalogs"  name="addCategroyProdCatalogs" buttonType="button" modalUrl="/catalog/control/addCategoryProdCatalogs" modalMsg="" modalTitle="添加目录分类"
    description="${uiLabelMap.CommonAdd}" targetParameterIter="productCategoryId:${productCategoryId}">
    </@htmlScreenTemplate.renderModalPage>
<hr/>
<div class="table-responsive">
    <table class="table table-striped table-bordered">
        <thead>
        <tr>
            <th><b>${uiLabelMap.ProductCatalogNameId}</b></th>
            <th><b>${uiLabelMap.CommonType}</b></th>
            <th><b>${uiLabelMap.CommonFromDateTime}</b></th>
            <th align="center"><b>${uiLabelMap.ProductThruDateTimeSequence}</b></th>
            <th><b>删除</b></th>
        </tr>
        </thead>
            <#assign line = 0>
            <#list prodCatalogCategories as prodCatalogCategory>
                <#assign line = line + 1>
                <#assign prodCatalog = prodCatalogCategory.getRelatedOne("ProdCatalog")>
                <#assign curProdCatalogCategoryType = prodCatalogCategory.getRelatedOneCache("ProdCatalogCategoryType")>
            <tr>
                <td><a href="<@ofbizUrl>EditProdCatalog?prodCatalogId=${(prodCatalogCategory.prodCatalogId)?if_exists}</@ofbizUrl>"
                       class="buttontext"><#if prodCatalog?exists>${(prodCatalog.catalogName)?if_exists}</#if> [${(prodCatalogCategory.prodCatalogId)?if_exists}]</a>
                </td>
                <td>
                ${(curProdCatalogCategoryType.get("description",locale))?default(prodCatalogCategory.prodCatalogCategoryTypeId)}
                </td>
                <#assign hasntStarted = false>
                <#if (prodCatalogCategory.getTimestamp("fromDate"))?exists && nowTimestamp.before(prodCatalogCategory.getTimestamp("fromDate"))> <#assign hasntStarted = true></#if>
                <td <#if hasntStarted> style="color: red;"</#if>>${(prodCatalogCategory.fromDate)?if_exists}</td>
                <td>

                    <form method="post" action="<@ofbizUrl>category_updateProductCategoryToProdCatalog</@ofbizUrl>" name="lineForm_update${line}" class="form-inline">
                        <#assign hasExpired = false>
                        <#if (prodCatalogCategory.getTimestamp("thruDate"))?exists && nowTimestamp.after(prodCatalogCategory.getTimestamp("thruDate"))> <#assign hasExpired = true></#if>
                        <input type="hidden" name="prodCatalogId" value="${(prodCatalogCategory.prodCatalogId)?if_exists}"/>
                        <input type="hidden" name="productCategoryId" value="${(prodCatalogCategory.productCategoryId)?if_exists}"/>
                        <input type="hidden" name="prodCatalogCategoryTypeId" value="${prodCatalogCategory.prodCatalogCategoryTypeId}"/>
                        <input type="hidden" name="fromDate" value="${(prodCatalogCategory.fromDate)?if_exists}"/>
                        <#if hasExpired><#assign class="alert"></#if>


                        <@htmlTemplate.renderDateTimeField name="thruDate" id="thruDate${line}" event="" action="" className="${class!''}" alert="" title="Format: yyyy-MM-dd HH:mm:ss.SSS" value="${(prodCatalogCategory.thruDate)?if_exists}" size="10" maxlength="30" dateType="date" shortDateInput=false timeDropdownParamName="" defaultDateTimeString="" localizedIconTitle="" timeDropdown="" timeHourName="" classString="" hour1="" hour2="" timeMinutesName="" minutes="" isTwelveHour="" ampmName="" amSelected="" pmSelected="" compositeType="" formName=""/>

                        <input type="text" size="5" name="sequenceNum" value="${(prodCatalogCategory.sequenceNum)?if_exists}" class="form-control input-sm"/>
                <#-- the prodCatalogCategoryTypeId field is now part of the PK, so it can't be changed, must be re-created
                        <select name="prodCatalogCategoryTypeId" size="1">
                            <#if (prodCatalogCategory.prodCatalogCategoryTypeId)?exists>
                                <option value="${prodCatalogCategory.prodCatalogCategoryTypeId}"><#if curProdCatalogCategoryType?exists>${(curProdCatalogCategoryType.description)?if_exists}<#else> [${(prodCatalogCategory.prodCatalogCategoryTypeId)}]</#if></option>
                                <option value="${prodCatalogCategory.prodCatalogCategoryTypeId}"></option>
                            <#else>
                                <option value="">&nbsp;</option>
                            </#if>
                            <#list prodCatalogCategoryTypes as prodCatalogCategoryType>
                            <option value="${(prodCatalogCategoryType.prodCatalogCategoryTypeId)?if_exists}">${(prodCatalogCategoryType.get("description",locale))?if_exists}</option>
                            </#list>
                        </select> -->

                        <input type="submit" value="${uiLabelMap.CommonUpdate}" class="btn btn-primary btn-sm"/>
                    </form>

                </td>
                <td align="center">
                <@htmlTemplate.renderConfirmField id="deleteProductCategoryFromProdCatalog${line}" name="deleteProductCategoryFromProdCatalog"
                confirmUrl="/catalog/control/category_removeProductCategoryFromProdCatalog" buttonType="button"
                confirmMessage="确定删除该记录" confirmTitle="分类对应目录删除" description="删除"
                targetParameterIter="prodCatalogId:'${(prodCatalogCategory.prodCatalogId)?if_exists}',
                productCategoryId:'${(prodCatalogCategory.productCategoryId)?if_exists}',
                prodCatalogCategoryTypeId:'${prodCatalogCategory.prodCatalogCategoryTypeId}',
                fromDate:'${(prodCatalogCategory.fromDate)?if_exists}'">
                </@htmlTemplate.renderConfirmField>


                    <#--<form method="post" action="<@ofbizUrl>category_removeProductCategoryFromProdCatalog</@ofbizUrl>" name="lineForm_delete${line}">
                        <input type="hidden" name="prodCatalogId" value="${(prodCatalogCategory.prodCatalogId)?if_exists}"/>
                        <input type="hidden" name="productCategoryId" value="${(prodCatalogCategory.productCategoryId)?if_exists}"/>
                        <input type="hidden" name="prodCatalogCategoryTypeId" value="${prodCatalogCategory.prodCatalogCategoryTypeId}"/>
                        <input type="hidden" name="fromDate" value="${(prodCatalogCategory.fromDate)?if_exists}"/>
                        <a href="javascript:document.lineForm_delete${line}.submit()" class="buttontext">${uiLabelMap.CommonDelete}</a>
                    </form>-->
                </td>
            </tr>

            </#list>
    </table>
</div>
<br/>
    <@htmlScreenTemplate.renderScreenletEnd/>
</#if>
