<#if productId?exists>
    <@htmlScreenTemplate.renderScreenletBegin id="" title="${uiLabelMap.PageTitleEditProductFeatures}"/>
    <#--<a href="AddFeaturesFromCategory?productId=${productId}" class="btn btn-primary btn-sm">从分类和组添加特征</a>-->
    <#--<@htmlScreenTemplate.renderModalPage id="AddFeaturesFromCategory" name="AddFeaturesFromCategory" modalTitle="从分类和组添加特征"  modalUrl="AddFeaturesFromCategory" description="从分类和组添加特征"  targetParameterIter="productId:${productId}"/>-->
    <#--<@htmlScreenTemplate.renderModalPage id="AddFeaturesFromTypeAndCode" name="AddFeaturesFromTypeAndCode" modalTitle="从类型和标识添加特征"  modalUrl="AddFeaturesFromTypeAndCode" description="从类型和标识添加特征"  targetParameterIter="productId:${productId}"/>-->
    <@htmlScreenTemplate.renderModalPage id="AddProductFeatures" name="AddProductFeatures"  modalUrl="AddProductFeatures" modalTitle="添加特征" description="添加特征"  targetParameterIter="productId:${productId}"/>
<hr/>
<form method="post" action="<@ofbizUrl>UpdateFeatureToProductApplication</@ofbizUrl>" name="selectAllForm" class="form-inline">
    <input type="hidden" name="_useRowSubmit" value="Y"/>
    <input type="hidden" name="_checkGlobalScope" value="Y"/>
    <input type="hidden" name="productId" value="${productId}"/>

    <div class="table-responsive">
        <table class="table table-striped table-bordered">
            <tr class="header-row">
                <th><b>${uiLabelMap.CommonId}</b></th>
                <th><b>${uiLabelMap.CommonDescription}</b></th>
                <th><b>${uiLabelMap.ProductUomId}</b></th>
                <th><b>${uiLabelMap.ProductType}</b></th>
                <th><b>${uiLabelMap.ProductCategory}</b></th>
                <th><b>${uiLabelMap.CommonFromDate}</b></th>
                <th><b>${uiLabelMap.ProductThruDateAmountSequenceApplicationType}</b></th>
                <th><b>${uiLabelMap.CommonAll}
                    <input type="checkbox" name="selectAll" value="${uiLabelMap.CommonY}"
                           onclick="toggleAll(this, 'selectAllForm');highlightAllRows(this, 'productFeatureId_tableRow_', 'selectAllForm');"/></b></th>
                <th>&nbsp;</th>
            </tr>
            <#assign rowClass = "2">
            <#list productFeatureAndAppls as productFeatureAndAppl>
                <#if productFeatureAndAppl.uomId?exists>
                    <#assign curProductFeatureUom = delegator.findOne("Uom",{"uomId",productFeatureAndAppl.uomId}, true)>
                </#if>
                <#assign curProductFeatureType = productFeatureAndAppl.getRelatedOneCache("ProductFeatureType")>
                <#assign curProductFeatureApplType = productFeatureAndAppl.getRelatedOneCache("ProductFeatureApplType")>
                <#assign curProductFeatureCategory = (productFeatureAndAppl.getRelatedOneCache("ProductFeatureCategory")?if_exists)>
                <tr id="productFeatureId_tableRow_${productFeatureAndAppl_index}" valign="middle"<#if rowClass == "1"> class="alternate-row"</#if>>
                    <td>
                        <input type="hidden" name="productId_o_${productFeatureAndAppl_index}" value="${(productFeatureAndAppl.productId)?if_exists}"/>
                        <input type="hidden" name="productFeatureId_o_${productFeatureAndAppl_index}" value="${(productFeatureAndAppl.productFeatureId)?if_exists}"/>
                        <input type="hidden" name="fromDate_o_${productFeatureAndAppl_index}" value="${(productFeatureAndAppl.fromDate)?if_exists}"/>
                        <a href="<@ofbizUrl>EditFeature?productFeatureId=${(productFeatureAndAppl.productFeatureId)?if_exists}</@ofbizUrl>" class="buttontext">
                        ${(productFeatureAndAppl.productFeatureId)?if_exists}</a></td>
                    <td>${(productFeatureAndAppl.get("description",locale))?if_exists}</td>
                    <td><#if productFeatureAndAppl.uomId?exists>${curProductFeatureUom.abbreviation!}</#if></td>
                    <td>${(curProductFeatureType.get("description",locale))?default((productFeatureAndAppl.productFeatureTypeId)?if_exists)}</td>
                    <td>
                        <a href="<@ofbizUrl>EditFeatureCategoryFeatures?productFeatureCategoryId=${(productFeatureAndAppl.productFeatureCategoryId)?if_exists}&amp;productId=${(productFeatureAndAppl.productId)?if_exists}</@ofbizUrl>"
                           class="buttontext">
                        ${(curProductFeatureCategory.description)?if_exists}
                            [${(productFeatureAndAppl.productFeatureCategoryId)?if_exists}]</a></td>
                    <#assign hasntStarted = false>
                    <#if (productFeatureAndAppl.getTimestamp("fromDate"))?exists && Static["org.ofbiz.base.util.UtilDateTime"].nowTimestamp().before(productFeatureAndAppl.getTimestamp("fromDate"))> <#assign hasntStarted = true></#if>
                    <td <#if hasntStarted> style='color: red;'</#if>>${(productFeatureAndAppl.fromDate)?if_exists?string("yyyy-MM-dd hh:mm")}</td>
                    <td>
                        <#assign hasExpired = false>
                        <#if (productFeatureAndAppl.getTimestamp("thruDate"))?exists && Static["org.ofbiz.base.util.UtilDateTime"].nowTimestamp().after(productFeatureAndAppl.getTimestamp("thruDate"))> <#assign hasExpired = true></#if>
                        <#if hasExpired><#assign class="alert"></#if>
                        <@htmlTemplate.renderDateTimeField name="thruDate_o_${productFeatureAndAppl_index}" event="" action="" className="${class!''}" alert="" title="Format: yyyy-MM-dd HH:mm:ss.SSS" value="${(productFeatureAndAppl.thruDate)?if_exists}" size="25" maxlength="30" id="thruDate_o_${productFeatureAndAppl_index}" dateType="date" shortDateInput=false timeDropdownParamName="" defaultDateTimeString="" localizedIconTitle="" timeDropdown="" timeHourName="" classString="" hour1="" hour2="" timeMinutesName="" minutes="" isTwelveHour="" ampmName="" amSelected="" pmSelected="" compositeType="" formName=""/>
                        <input type="text" size='6' class="form-control" name='amount_o_${productFeatureAndAppl_index}' value='${(productFeatureAndAppl.amount)?if_exists}'/>
                        <input type="text" size='5' class="form-control" name='sequenceNum_o_${productFeatureAndAppl_index}' value='${(productFeatureAndAppl.sequenceNum)?if_exists}'/>
                        <select class="form-control" class="form-control" name='productFeatureApplTypeId_o_${productFeatureAndAppl_index}'>
                            <#if (productFeatureAndAppl.productFeatureApplTypeId)?exists>
                                <option value='${(productFeatureAndAppl.productFeatureApplTypeId)?if_exists}'><#if curProductFeatureApplType?exists> ${(curProductFeatureApplType.get("description",locale))?if_exists} <#else>
                                    [${productFeatureAndAppl.productFeatureApplTypeId}]</#if></option>
                                <option value='${productFeatureAndAppl.productFeatureApplTypeId}'></option>
                            </#if>
                            <#list productFeatureApplTypes as productFeatureApplType>
                                <option value='${(productFeatureApplType.productFeatureApplTypeId)?if_exists}'>${(productFeatureApplType.get("description",locale))?if_exists} </option>
                            </#list>
                        </select>
                    </td>
                    <td>
                        <input type="checkbox" name="_rowSubmit_o_${productFeatureAndAppl_index}" value="Y"
                               onclick="checkToggle(this, 'selectAllForm');highlightRow(this,'productFeatureId_tableRow_${productFeatureAndAppl_index}');"/>
                    </td>
                    <td>
                     <@htmlScreenTemplate.renderConfirmField id="RemoveFeatureFromProduct_o_${productFeatureAndAppl_index}" description="删除"
                     name="RemoveFeatureFromProduct_o_${productFeatureAndAppl_index}"  confirmUrl="RemoveFeatureFromProduct"  confirmMessage="确定删除该记录?" confirmTitle="特征删除"
                     targetParameterIter="productId:'${(productFeatureAndAppl.productId)?if_exists}',productFeatureId:'${(productFeatureAndAppl.productFeatureId)?if_exists}',fromDate:'${(productFeatureAndAppl.fromDate)?if_exists}'" />
                        <#--<a href="javascript:document.RemoveFeatureFromProduct_o_${productFeatureAndAppl_index}.submit()" class="btn btn-primary btn-sm">${uiLabelMap.CommonDelete}</a>-->
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
                    <input type="submit" value='${uiLabelMap.CommonUpdate}' class="btn btn-primary btn-sm"/>
                </td>
            </tr>
        </table>
    </div>
</form>
    <#--<#list productFeatureAndAppls as productFeatureAndAppl>-->
    <#--<form name="RemoveFeatureFromProduct_o_${productFeatureAndAppl_index}" method="post" action="<@ofbizUrl>RemoveFeatureFromProduct</@ofbizUrl>">-->
        <#--<input type="hidden" name="productId" value="${(productFeatureAndAppl.productId)?if_exists}"/>-->
        <#--<input type="hidden" name="productFeatureId" value="${(productFeatureAndAppl.productFeatureId)?if_exists}"/>-->
        <#--<input type="hidden" name="fromDate" value="${(productFeatureAndAppl.fromDate)?if_exists}"/>-->
    <#--</form>-->
    <#--</#list>-->
</#if>
<@htmlScreenTemplate.renderScreenletEnd/>