<#if productCategoryId?exists && productCategory?exists>
    <@htmlScreenTemplate.renderScreenletBegin id="" title="${uiLabelMap.ProductApplyFeatureGroupToCategory}"/>
<#-- Feature Categories -->
    <@htmlTemplate.renderModalPage id="pre_createProductFeatureCategoryAppl" name="pre_createProductFeatureCategoryAppl"
    modalTitle="${StringUtil.wrapString(uiLabelMap.ProductApplyFeatureGroupToCategory)}" description="${uiLabelMap.ProductApplyFeatureGroupToCategory}"
    modalUrl="/catalog/control/pre_createProductFeatureCategoryAppl"
    targetParameterIter="productCategoryId:'${productCategoryId?if_exists}'"/>


<hr/>
<table class="table table-striped table-bordered">
    <tr>
        <th><b>${uiLabelMap.ProductFeatureCategory}</b></th>
        <th><b>${uiLabelMap.CommonFromDateTime}</b></th>
        <th align="center"><b>${uiLabelMap.CommonThruDateTime}</b></th>
        <th><b>操作</b></th>
    </tr>
    <#assign line = 0>
    <#list productFeatureCategoryAppls as productFeatureCategoryAppl>
        <#assign line = line + 1>
        <#assign productFeatureCategory = (productFeatureCategoryAppl.getRelatedOne("ProductFeatureCategory"))?default(null)>
        <tr>
            <td><a href="<@ofbizUrl>EditFeatureCategoryFeatures?productFeatureCategoryId=${(productFeatureCategoryAppl.productFeatureCategoryId)?if_exists}</@ofbizUrl>"
                   class="buttontext"><#if productFeatureCategory?exists>${(productFeatureCategory.description)?if_exists}</#if>
                [${(productFeatureCategoryAppl.productFeatureCategoryId)?if_exists}]</a></td>
            <#assign hasntStarted = false>
            <#if (productFeatureCategoryAppl.getTimestamp("fromDate"))?exists && nowTimestamp.before(productFeatureCategoryAppl.getTimestamp("fromDate"))> <#assign hasntStarted = true></#if>
            <td <#if hasntStarted> style="color: red;"</#if>>${(productFeatureCategoryAppl.fromDate)?if_exists}</td>
            <td align="center">
                <form method="post" action="<@ofbizUrl>updateProductFeatureCategoryAppl</@ofbizUrl>" name="lineForm${line}" class="form-inline">
                    <#assign hasExpired = false>
                    <#if (productFeatureCategoryAppl.getTimestamp("thruDate"))?exists && nowTimestamp.after(productFeatureCategoryAppl.getTimestamp("thruDate"))> <#assign hasExpired = true></#if>
                    <input type="hidden" name="productCategoryId" value="${(productFeatureCategoryAppl.productCategoryId)?if_exists}"/>
                    <input type="hidden" name="productFeatureCategoryId" value="${(productFeatureCategoryAppl.productFeatureCategoryId)?if_exists}"/>
                    <input type="hidden" name="fromDate" value="${(productFeatureCategoryAppl.fromDate)?if_exists}"/>
                    <#if hasExpired><#assign class="alert"></#if>
                    <@htmlTemplate.renderDateTimeField name="thruDate" event="" action="" value="${(productFeatureCategoryAppl.thruDate)?if_exists}" className="${class!''}" alert="" title="Format: yyyy-MM-dd HH:mm:ss.SSS" size="25" maxlength="30" id="thruDate${line}" dateType="date" shortDateInput=false timeDropdownParamName="" defaultDateTimeString="" localizedIconTitle="" timeDropdown="" timeHourName="" classString="" hour1="" hour2="" timeMinutesName="" minutes="" isTwelveHour="" ampmName="" amSelected="" pmSelected="" compositeType="" formName=""/>
                    <input type="submit" value="${uiLabelMap.CommonUpdate}" class="btn btn-primary btn-sm"/>
                </form>
            </td>
            <td align="center">
                <@htmlTemplate.renderConfirmField id="removeProductFeatureCategoryApplForm_${productFeatureCategoryAppl_index}" name="removeProductFeatureCategoryApplForm_${productFeatureCategoryAppl_index}"
                confirmUrl="/catalog/control/removeProductFeatureCategoryAppl" buttonType="button"
                confirmMessage="确定删除该记录" confirmTitle="分类对应特征分类删除" description="${uiLabelMap.CommonDelete}"
                targetParameterIter="productFeatureCategoryId:'${(productFeatureCategoryAppl.productFeatureCategoryId)?if_exists}',
                    productCategoryId:'${(productFeatureCategoryAppl.productCategoryId)?if_exists}',
                    fromDate:'${(productFeatureCategoryAppl.fromDate)?if_exists}'">
                </@htmlTemplate.renderConfirmField>


               <#-- <a href="javascript:document.removeProductFeatureCategoryApplForm_${productFeatureCategoryAppl_index}.submit()" onclick="return confirm('确定删除该记录？'); "
                   class="btn btn-primary btn-sm">${uiLabelMap.CommonDelete}</a>

                <form method="post" action="<@ofbizUrl>removeProductFeatureCategoryAppl</@ofbizUrl>" name="removeProductFeatureCategoryApplForm_${productFeatureCategoryAppl_index}">
                    <input type="hidden" name="productFeatureCategoryId" value="${(productFeatureCategoryAppl.productFeatureCategoryId)?if_exists}"/>
                    <input type="hidden" name="productCategoryId" value="${(productFeatureCategoryAppl.productCategoryId)?if_exists}"/>
                    <input type="hidden" name="fromDate" value="${(productFeatureCategoryAppl.fromDate)?if_exists}"/>
                </form>-->
            </td>
        </tr>
    </#list>
</table>
    <@htmlScreenTemplate.renderScreenletEnd/>

    <#--<@htmlScreenTemplate.renderScreenletBegin id="" title="${uiLabelMap.ProductApplyFeatureGroupToCategory}"/>
<form method="post" action="<@ofbizUrl>createProductFeatureCategoryAppl</@ofbizUrl>" class="form-inline" name="addNewCategoryForm">
    <input type="hidden" name="productCategoryId" value="${productCategoryId?if_exists}"/>

    <div class="form-group">
        <div class="input-group">
            <select name="productFeatureCategoryId" class="form-control">
                <#list productFeatureCategories as productFeatureCategory>
                    <option value="${(productFeatureCategory.productFeatureCategoryId)?if_exists}">${(productFeatureCategory.description)?if_exists}
                        [${(productFeatureCategory.productFeatureCategoryId)?if_exists}]
                    </option>
                </#list>
            </select>
        </div>

        <@htmlTemplate.renderDateTimeField name="fromDate" event="" action="" value="" className="" alert="" title="Format: yyyy-MM-dd HH:mm:ss.SSS" size="25" maxlength="30" id="fromDate2" dateType="date" shortDateInput=false timeDropdownParamName="" defaultDateTimeString="" localizedIconTitle="" timeDropdown="" timeHourName="" classString="" hour1="" hour2="" timeMinutesName="" minutes="" isTwelveHour="" ampmName="" amSelected="" pmSelected="" compositeType="" formName=""/>
        <input type="submit" value="${uiLabelMap.CommonAdd}" class="btn btn-primary btn-sm"/>
    </div>
</form>
    <@htmlScreenTemplate.renderScreenletEnd/>-->


<@htmlScreenTemplate.renderScreenletBegin id="" title="${uiLabelMap.PageTitleEditCategoryFeatureCategories}"/>

    <@htmlTemplate.renderModalPage id="pre_createProductFeatureCatGrpAppl" name="pre_createProductFeatureCatGrpAppl"
    modalTitle="${StringUtil.wrapString(uiLabelMap.ProductApplyFeatureGroupFromCategory)}" description="${uiLabelMap.ProductApplyFeatureGroupFromCategory}"
    modalUrl="/catalog/control/pre_createProductFeatureCatGrpAppl"
    targetParameterIter="productCategoryId:'${productCategoryId?if_exists}'"/>

<a href="javascript:document.attachProductFeaturesToCategory.submit()" class="btn btn-primary btn-sm">${uiLabelMap.ProductFeatureCategoryAttach}</a>
<hr/>
<form method="post" action="<@ofbizUrl>attachProductFeaturesToCategory</@ofbizUrl>" name="attachProductFeaturesToCategory">
    <input type="hidden" name="productCategoryId" value="${productCategoryId?if_exists}"/>
</form>

<div class="table-responsive">
    <table class="table table-striped table-bordered ">
        <tr>
            <th><b>${uiLabelMap.ProductFeatureGroup}</b></th>
            <th><b>${uiLabelMap.CommonFromDateTime}</b></th>
            <th align="center"><b>${uiLabelMap.CommonThruDateTime}</b></th>
            <th><b>&nbsp;</b></th>
        </tr>
        <#assign line = 0>

        <#list productFeatureCatGrpAppls as productFeatureCatGrpAppl>
            <#assign line = line + 1>
            <#assign productFeatureGroup = (productFeatureCatGrpAppl.getRelatedOne("ProductFeatureGroup"))?default(null)>
            <tr>
                <td><a href="<@ofbizUrl>EditFeatureGroupAppls?productFeatureGroupId=${(productFeatureCatGrpAppl.productFeatureGroupId)?if_exists}</@ofbizUrl>"
                       class="buttontext"><#if productFeatureGroup?exists>${(productFeatureGroup.description)?if_exists}</#if>
                    [${(productFeatureCatGrpAppl.productFeatureGroupId)?if_exists}]</a></td>
                <#assign hasntStarted = false>
                <#if (productFeatureCatGrpAppl.getTimestamp("fromDate"))?exists && nowTimestamp.before(productFeatureCatGrpAppl.getTimestamp("fromDate"))> <#assign hasntStarted = true></#if>
                <td>
                    <div<#if hasntStarted> style="color: red;</#if>>${(productFeatureCatGrpAppl.fromDate)?if_exists}</div></td>
                    <td>
                        <form method=" post
                    " action="<@ofbizUrl>updateProductFeatureCatGrpAppl</@ofbizUrl>" name="lineFormGrp${line}" class="form-inline">
                    <#assign hasExpired = false>
                    <#if (productFeatureCatGrpAppl.getTimestamp("thruDate"))?exists && nowTimestamp.after(productFeatureCatGrpAppl.getTimestamp("thruDate"))> <#assign hasExpired = true></#if>
                    <input type="hidden" name="productCategoryId" value="${(productFeatureCatGrpAppl.productCategoryId)?if_exists}"/>
                    <input type="hidden" name="productFeatureGroupId" value="${(productFeatureCatGrpAppl.productFeatureGroupId)?if_exists}"/>
                    <input type="hidden" name="fromDate" value="${(productFeatureCatGrpAppl.fromDate)?if_exists}"/>
                    <#if hasExpired><#assign class="alert"></#if>
                    <@htmlTemplate.renderDateTimeField name="thruDate" event="" action="" value="${(productFeatureCatGrpAppl.thruDate)?if_exists}" className="${class!''}" alert="" title="Format: yyyy-MM-dd HH:mm:ss.SSS" size="22" maxlength="25" id="fromDate${line}" dateType="date" shortDateInput=false timeDropdownParamName="" defaultDateTimeString="" localizedIconTitle="" timeDropdown="" timeHourName="" classString="" hour1="" hour2="" timeMinutesName="" minutes="" isTwelveHour="" ampmName="" amSelected="" pmSelected="" compositeType="" formName=""/>
                    <input type="submit" value="${uiLabelMap.CommonUpdate}" class="btn btn-primary btn-sm"/>
                    </form>
                </td>
                <td align="center">
                    <#--<a href="javascript:document.removeProductFeatureCatGrpApplForm_${productFeatureCatGrpAppl_index}.submit()" class="buttontext">${uiLabelMap.CommonDelete}</a>-->
                    <@htmlTemplate.renderConfirmField id="removeProductFeatureCatGrpApplForm_${productFeatureCatGrpAppl_index}" name="removeProductFeatureCatGrpApplForm_${productFeatureCatGrpAppl_index}"
                    confirmUrl="/catalog/control/removeProductFeatureCatGrpAppl" buttonType="button"
                    confirmMessage="确定删除该记录" confirmTitle="分类对应特征组删除" description="${uiLabelMap.CommonDelete}"
                    targetParameterIter="productFeatureGroupId:'${(productFeatureCatGrpAppl.productFeatureGroupId)?if_exists}',
                    productCategoryId:'${(productFeatureCatGrpAppl.productCategoryId)?if_exists}',
                    fromDate:'${(productFeatureCatGrpAppl.fromDate)?if_exists}'">
                        </@htmlTemplate.renderConfirmField>
                    <#--<form method="post" action="<@ofbizUrl>removeProductFeatureCatGrpAppl</@ofbizUrl>" name="removeProductFeatureCatGrpApplForm_${productFeatureCatGrpAppl_index}">
                        <input type="hidden" name="productFeatureGroupId" value="${(productFeatureCatGrpAppl.productFeatureGroupId)?if_exists}"/>
                        <input type="hidden" name="productCategoryId" value="${(productFeatureCatGrpAppl.productCategoryId)?if_exists}"/>
                        <input type="hidden" name="fromDate" value="${(productFeatureCatGrpAppl.fromDate)?if_exists}"/>
                    </form>-->
                </td>
            </tr>

        </#list>
    </table>
</div>
    <@htmlScreenTemplate.renderScreenletEnd/>
 <#--   <@htmlScreenTemplate.renderScreenletBegin id="" title="${uiLabelMap.ProductApplyFeatureGroupFromCategory}"/>
    <#if productFeatureGroups?has_content>
    <form method="post" action="<@ofbizUrl>createProductFeatureCatGrpAppl</@ofbizUrl>" class="form-inline" name="addNewGroupForm">
        <input type="hidden" name="productCategoryId" value="${productCategoryId?if_exists}"/>

        <div class="form-group">

            <select name="productFeatureGroupId" class="form-control">
                <#list productFeatureGroups as productFeatureGroup>
                    <option value="${(productFeatureGroup.productFeatureGroupId)?if_exists}">${(productFeatureGroup.description)?if_exists}
                        [${(productFeatureGroup.productFeatureGroupId)?if_exists}]
                    </option>
                </#list>
            </select>
            <@htmlTemplate.renderDateTimeField name="fromDate" event="" action="" value="" className="" alert="" title="Format: yyyy-MM-dd HH:mm:ss.SSS" size="25" maxlength="30" id="fromDate2" dateType="date" shortDateInput=false timeDropdownParamName="" defaultDateTimeString="" localizedIconTitle="" timeDropdown="" timeHourName="" classString="" hour1="" hour2="" timeMinutesName="" minutes="" isTwelveHour="" ampmName="" amSelected="" pmSelected="" compositeType="" formName=""/>
        </div>
        <input type="submit" value="${uiLabelMap.CommonAdd}" class="btn btn-primary btn-sm"/>
    </form>

    <#else>
    &nbsp;
    </#if>
    <@htmlScreenTemplate.renderScreenletEnd/>-->

</#if>