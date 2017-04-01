<@htmlScreenTemplate.renderScreenletBegin id="" title="${uiLabelMap.ProductAddProductFeatureInBulk} ${uiLabelMap.CommonFor} ${featureCategory.description}"/>
<div class="table-responsive">
    <table class="table table-striped table-bordered">
          <form method='post' action='<@ofbizUrl>BulkAddProductFeatures</@ofbizUrl>' name="selectAllForm" class="form-horizontal">
          <input type="hidden" name="_useRowSubmit" value="Y" />
          <input type="hidden" name="_checkGlobalScope" value="N" />
          <input type="hidden" name="productFeatureCategoryId" value="${productFeatureCategoryId}" />
          <tr class="header-row">
            <th>${uiLabelMap.CommonDescription}</th>
            <th>${uiLabelMap.ProductFeatureType}</th>
            <th>${uiLabelMap.ProductIdSeqNum}</th>
            <th>${uiLabelMap.ProductIdCode}</th>
            <th align="right">${uiLabelMap.CommonAll}<input type="checkbox" name="selectAll" value="Y" checked="checked" onclick="toggleAll(this, 'selectAllForm');highlightAllRows(this, 'productFeatureTypeId_tableRow_', 'selectAllForm');" /></th>
          </tr>
        <#assign rowClass = "2">
        <#list 0..featureNum-1 as feature>
          <tr id="productFeatureTypeId_tableRow_${feature_index}" valign="middle"<#if rowClass == "1"> class="alternate-row"</#if>>
              <td><input type="text"  name="description_o_${feature_index}" class="form-control"/></td>
              <td><select name='productFeatureTypeId_o_${feature_index}'   class="form-control">
                  <#list productFeatureTypes as productFeatureType>
                  <option value='${productFeatureType.productFeatureTypeId}'>${productFeatureType.get("description",locale)?if_exists}</option>
                  </#list>
                  </select>
                  <input name='productFeatureCategoryId_o_${feature_index}' type="hidden" value="${productFeatureCategoryId}" />
              </td>
              <td><input type="text"  name="defaultSequenceNum_o_${feature_index}" class="form-control"/></td>
              <td><input type="text"  name="idCode_o_${feature_index}" class="form-control" /></td>
              <td><input type="checkbox" name="_rowSubmit_o_${feature_index}" value="Y" checked="checked" onclick="checkToggle(this, 'selectAllForm');highlightRow(this,'productFeatureTypeId_tableRow_${feature_index}');" /></td>
          </tr>
          <#-- toggle the row color -->
          <#if rowClass == "2">
            <#assign rowClass = "1">
          <#else>
            <#assign rowClass = "2">
          </#if>
        </#list>
        <input type="hidden" name="_rowCount" value="${featureNum}" />
        <tr><td colspan="11" align="right"><input type="submit" value='${uiLabelMap.CommonCreate}' class="btn btn-primary btn-sm pull-right"/></td></tr>
        </form>
        </table>
    </div>
<@htmlScreenTemplate.renderScreenletEnd/>


