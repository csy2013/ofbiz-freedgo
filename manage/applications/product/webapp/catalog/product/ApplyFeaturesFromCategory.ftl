
<@htmlScreenTemplate.renderScreenletBegin id="" title="分类和组编辑特征"/>
<#if curProductFeatureCategory?exists>
<a href="<@ofbizUrl>EditFeature?productFeatureCategoryId=${productFeatureCategoryId?if_exists}</@ofbizUrl>" class="btn btn-primary btn-sm">${uiLabelMap.ProductCreateNewFeature}</a>
<#elseif productFeatureGroup?exists>
<a href="<@ofbizUrl>EditFeatureGroupAppls?productFeatureGroupId=${productFeatureGroup.productFeatureGroupId?if_exists}</@ofbizUrl>" class="btn btn-primary btn-sm">${uiLabelMap.CommonEdit} ${productFeatureGroup.description?if_exists}</a>
</#if>
<#if productId?has_content>
    <a href="<@ofbizUrl>EditProduct?productId=${productId}</@ofbizUrl>" class="btn btn-primary btn-sm">${uiLabelMap.ProductReturnToEditProduct}</a>
    <a href="<@ofbizUrl>EditProductFeatures?productId=${productId}</@ofbizUrl>" class="btn btn-primary btn-sm">${uiLabelMap.ProductReturnToEditProductFeatures}</a>
</#if>
<hr/>
<#if (listSize > 0)>
<#assign selectedFeatureApplTypeId = selFeatureApplTypeId?if_exists>

    <#if productId?has_content>
      <#assign productString = "&amp;productId=" + productId>
    </#if>
    <table cellspacing="0" class="basic-table">
        <tr>
        <td align="right">
            <span>
            <b>
            <#if (viewIndex > 0)>
            <a href="<@ofbizUrl>ApplyFeaturesFromCategory?productFeatureCategoryId=${productFeatureCategoryId?if_exists}&amp;productFeatureApplTypeId=${selectedFeatureApplTypeId?if_exists}&amp;VIEW_SIZE=${viewSize}&amp;VIEW_INDEX=${viewIndex-1}${productString?if_exists}</@ofbizUrl>" class="buttontext">[${uiLabelMap.CommonPrevious}]</a> |
            </#if>
            ${lowIndex+1} - ${highIndex} ${uiLabelMap.CommonOf} ${listSize}
            <#if (listSize > highIndex)>
            | <a href="<@ofbizUrl>ApplyFeaturesFromCategory?productFeatureCategoryId=${productFeatureCategoryId?if_exists}&amp;productFeatureApplTypeId=${selectedFeatureApplTypeId?if_exists}&amp;VIEW_SIZE=${viewSize}&amp;VIEW_INDEX=${viewIndex+1}${productString?if_exists}</@ofbizUrl>" class="buttontext">[${uiLabelMap.CommonNext}]</a>
            </#if>
            </b>
            </span>
        </td>
        </tr>
    </table>
</#if>
<form method="post" action="<@ofbizUrl>ApplyFeaturesToProduct</@ofbizUrl>" name="selectAllForm" class="form-inline">
<div class="table-responsive">
<table class="table table-striped table-bordered">

  <input type="hidden" name="_useRowSubmit" value="Y" />
  <input type="hidden" name="_checkGlobalScope" value="Y" />
  <input type="hidden" name="productId" value="${productId}" />
  <tr>
    <th><b>${uiLabelMap.CommonId}</b></th>
    <th><b>${uiLabelMap.CommonDescription}</b></th>
    <th><b>${uiLabelMap.ProductFeatureType}</b></th>
    <th><b>${uiLabelMap.ProductApplType}</b></th>
    <th><b>${uiLabelMap.CommonFromDate}</b></th>
    <th><b>${uiLabelMap.CommonThruDate}</b></th>
    <th><b>${uiLabelMap.ProductAmount}</b></th>
    <th><b>${uiLabelMap.CommonSequence}</b></th>
    <th><b>${uiLabelMap.CommonAll}<input type="checkbox" name="selectAll" value="${uiLabelMap.CommonY}" onclick="toggleAll(this, 'selectAllForm');highlightAllRows(this, 'productFeatureId_tableRow_', 'selectAllForm');" /></th>
  </tr>
<#assign rowCount = 0>
<#assign rowClass = "2">
<#if (listSize > 0)>
<#list productFeatures as productFeature>
  <#assign curProductFeatureType = productFeature.getRelatedOneCache("ProductFeatureType")>
    <tr id="productFeatureId_tableRow_${rowCount}"  valign="middle">
        <input type="hidden" name="productFeatureId_o_${rowCount}" value="${productFeature.productFeatureId}" />
        <td><a href="<@ofbizUrl>EditFeature?productFeatureId=${productFeature.productFeatureId}</@ofbizUrl>" class="buttontext">${productFeature.productFeatureId}</a></td>
        <td>${productFeature.description!}</td>
        <td><#if curProductFeatureType?exists>${curProductFeatureType.description!}<#else> [${productFeature.productFeatureTypeId}]</#if></td>
        <td>
          <select name="productFeatureApplTypeId_o_${rowCount}" class="form-control">
            <#list productFeatureApplTypes as productFeatureApplType>
              <option value="${productFeatureApplType.productFeatureApplTypeId}" <#if (selectedFeatureApplTypeId?has_content) && (productFeatureApplType.productFeatureApplTypeId == selectedFeatureApplTypeId)>selected="selected"</#if>>${productFeatureApplType.get("description", locale)}</option>
            </#list>
          </select>
        </td>
        <td>
            <@htmlTemplate.renderDateTimeField name="fromDate_o_${rowCount}" event="" action="" className="" alert="" title="Format: yyyy-MM-dd HH:mm:ss.SSS" value=""   id="fromDate_o_${rowCount}" dateType="date" shortDateInput=false timeDropdownParamName="" defaultDateTimeString="" localizedIconTitle="" timeDropdown="" timeHourName="" classString="" hour1="" hour2="" timeMinutesName="" minutes="" isTwelveHour="" ampmName="" amSelected="" pmSelected="" compositeType="" formName=""/>
        </td>
        <td>
           <@htmlTemplate.renderDateTimeField name="thruDate_o_${rowCount}" event="" action="" className="" alert="" title="Format: yyyy-MM-dd HH:mm:ss.SSS" value="" id="thruDate_o_${rowCount}" dateType="date" shortDateInput=false timeDropdownParamName="" defaultDateTimeString="" localizedIconTitle="" timeDropdown="" timeHourName="" classString="" hour1="" hour2="" timeMinutesName="" minutes="" isTwelveHour="" ampmName="" amSelected="" pmSelected="" compositeType="" formName=""/>
        </td>
        <td><input type="text" class="form-control" size="4"  name="amount_o_${rowCount}" value="${productFeature.defaultAmount?if_exists}" /></td>
        <td><input type="text" class="form-control"  size="2" name="sequenceNum_o_${rowCount}" value="${productFeature.defaultSequenceNum?if_exists}" /></td>
        <td align="right">
            <input type="checkbox" name="_rowSubmit_o_${rowCount}" value="Y" onclick="checkToggle(this, 'selectAllForm');highlightRow(this,'productFeatureId_tableRow_${rowCount}');" />
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
<tr><td colspan="9" align="center"><input type="submit" value="${uiLabelMap.CommonApply}" class="btn btn-primary btn-sm"/></td></tr>
</#if>
<input type="hidden" name="_rowCount" value="${rowCount?if_exists}"/>

</table>
</div></form>
<@htmlScreenTemplate.renderScreenletEnd/>