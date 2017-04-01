<#if curProductFeatureCategory?exists>
<a class="am-btn am-btn-primary am-btn-sm" href="<@ofbizUrl>EditFeature?productFeatureCategoryId=${productFeatureCategoryId?if_exists}</@ofbizUrl>"
   class="buttontext">${uiLabelMap.ProductCreateNewFeature}</a>
<#elseif productFeatureGroup?exists>
<a class="am-btn am-btn-primary am-btn-sm" href="<@ofbizUrl>EditFeatureGroupAppls?productFeatureGroupId=${productFeatureGroup.productFeatureGroupId?if_exists}</@ofbizUrl>"
   class="buttontext">${uiLabelMap.CommonEdit} ${productFeatureGroup.description?if_exists}</a>
</#if>
<#if productId?has_content>
<a class="am-btn am-btn-primary am-btn-sm" href="<@ofbizUrl>EditProduct?productId=${productId}</@ofbizUrl>"
   class="buttontext">${uiLabelMap.ProductReturnToEditProduct}</a>
<a class="am-btn am-btn-primary am-btn-sm" href="<@ofbizUrl>EditProductFeatures?productId=${productId}</@ofbizUrl>"
   class="buttontext">${uiLabelMap.ProductReturnToEditProductFeatures}</a>
</#if>

<#if (listSize > 0)>
    <#assign selectedFeatureApplTypeId = selFeatureApplTypeId?if_exists>

    <#if productId?has_content>
        <#assign productString = "&amp;productId=" + productId>
    </#if>
<table class="am-table am-table-striped am-table-hover table-main">
    <tr>
        <td>
            <span>
            <b>
                <#if (viewIndex > 0)>
                    <a href="<@ofbizUrl>ApplyFeaturesFromCategory?productFeatureCategoryId=${productFeatureCategoryId?if_exists}&amp;productFeatureApplTypeId=${selectedFeatureApplTypeId?if_exists}&amp;VIEW_SIZE=${viewSize}&amp;VIEW_INDEX=${viewIndex-1}${productString?if_exists}</@ofbizUrl>"
                       class="buttontext">[${uiLabelMap.CommonPrevious}]</a> |
                </#if>
            ${lowIndex+1} - ${highIndex} ${uiLabelMap.CommonOf} ${listSize}
                <#if (listSize > highIndex)>
                    | <a
                        href="<@ofbizUrl>ApplyFeaturesFromCategory?productFeatureCategoryId=${productFeatureCategoryId?if_exists}&amp;productFeatureApplTypeId=${selectedFeatureApplTypeId?if_exists}&amp;VIEW_SIZE=${viewSize}&amp;VIEW_INDEX=${viewIndex+1}${productString?if_exists}</@ofbizUrl>"
                        class="buttontext">[${uiLabelMap.CommonNext}]</a>
                </#if>
            </b>
            </span>
        </td>
    </tr>
</table>
</#if>

<form method="post" action="<@ofbizUrl>ApplyFeaturesToProduct</@ofbizUrl>" name="selectAllForm"  class="am-form am-form-horizontal">
    <input type="hidden" name="_useRowSubmit" value="Y"/>
    <input type="hidden" name="_checkGlobalScope" value="Y"/>
    <input type="hidden" name="productId" value="${productId}"/>
<table class="am-table am-table-striped am-table-hover table-main">
    <tr>
        <td><b>${uiLabelMap.CommonId}</b></td>
        <td><b>${uiLabelMap.CommonDescription}</b></td>
        <td><b>${uiLabelMap.ProductFeatureType}</b></td>
        <td><b>${uiLabelMap.ProductApplType}</b></td>
        <td><b>${uiLabelMap.CommonFromDate}</b></td>
        <td><b>${uiLabelMap.CommonThruDate}</b></td>
        <td><b>${uiLabelMap.ProductAmount}</b></td>
        <td><b>${uiLabelMap.CommonSequence}</b></td>
        <td align="right"><b>${uiLabelMap.CommonAll}<input type="checkbox" name="selectAll" value="${uiLabelMap.CommonY}"
                                             onclick="toggleAll(this, 'selectAllForm');highlightAllRows(this, 'productFeatureId_tableRow_', 'selectAllForm');"/>
        </td>
    </tr>
<#assign rowCount = 0>
<#assign rowClass = "2">
<#if (listSize > 0)>
    <#list productFeatures as productFeature>
        <#assign curProductFeatureType = productFeature.getRelatedOneCache("ProductFeatureType")>
        <tr id="productFeatureId_tableRow_${rowCount}" valign="middle"<#--<#if rowClass == "1"> class="am-active"</#if>-->>
            <input type="hidden" name="productFeatureId_o_${rowCount}" value="${productFeature.productFeatureId}"/>
            <td><a href="<@ofbizUrl>EditFeature?productFeatureId=${productFeature.productFeatureId}</@ofbizUrl>"
                   class="buttontext">${productFeature.productFeatureId}</a></td>
            <td>${productFeature.description!}</td>
            <td><#if curProductFeatureType?exists>${curProductFeatureType.description!}<#else>
                [${productFeature.productFeatureTypeId}]</#if></td>
            <td>
                <select  data-am-selected="{btnWidth:80, btnStyle:'default'}" name="productFeatureApplTypeId_o_${rowCount}" size="1">
                    <#list productFeatureApplTypes as productFeatureApplType>
                        <option value="${productFeatureApplType.productFeatureApplTypeId}"
                                <#if (selectedFeatureApplTypeId?has_content) && (productFeatureApplType.productFeatureApplTypeId == selectedFeatureApplTypeId)>selected="selected"</#if>>${productFeatureApplType.get("description", locale)}</option>
                    </#list>
                </select>
            </td>
            <td>
                    <@amazeHtmlTemplate.renderDateTimeField name="fromDate_o_${rowCount}" event="" action="" className="" alert="" title="Format: yyyy-MM-dd HH:mm:ss.SSS" value="" size="25" maxlength="30" id="fromDate_o_${rowCount}" dateType="date" shortDateInput=false timeDropdownParamName="" defaultDateTimeString="" localizedIconTitle="" timeDropdown="" timeHourName="" classString="" hour1="" hour2="" timeMinutesName="" minutes="" isTwelveHour="" ampmName="" amSelected="" pmSelected="" compositeType="" formName=""/>
            </td>
            <td>
                    <@amazeHtmlTemplate.renderDateTimeField name="thruDate_o_${rowCount}" event="" action="" className="" alert="" title="Format: yyyy-MM-dd HH:mm:ss.SSS" value="" size="25" maxlength="30" id="thruDate_o_${rowCount}" dateType="date" shortDateInput=false timeDropdownParamName="" defaultDateTimeString="" localizedIconTitle="" timeDropdown="" timeHourName="" classString="" hour1="" hour2="" timeMinutesName="" minutes="" isTwelveHour="" ampmName="" amSelected="" pmSelected="" compositeType="" formName=""/>
            </td>
            <td><input type="text" size="6" name="amount_o_${rowCount}"
                       value="${productFeature.defaultAmount?if_exists}"/></td>
            <td><input type="text" size="5" name="sequenceNum_o_${rowCount}"
                       value="${productFeature.defaultSequenceNum?if_exists}"/></td>
            <td align="right">
                <input type="checkbox" name="_rowSubmit_o_${rowCount}" value="Y"
                       onclick="checkToggle(this, 'selectAllForm');highlightRow(this,'productFeatureId_tableRow_${rowCount}');"/>
            </td>
        </tr>
        <#assign rowCount = rowCount + 1>
    <#-- toggle the row color -->
        <#if rowClass == "2">
            <#assign rowClass = "1">
        <#else>
            <#assign rowClass = "2">
        </#if>
    </#list>
    <tr>
        <td colspan="9" align="center"><input class="am-btn am-btn-primary am-btn-sm" type="submit" value="${uiLabelMap.CommonApply}"/></td>
    </tr>
</#if>
</table>
    <input type="hidden" name="_rowCount" value="${rowCount?if_exists}"/>
</form>

