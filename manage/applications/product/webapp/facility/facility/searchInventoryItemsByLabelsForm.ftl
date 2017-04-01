<form method="post" action="<@ofbizUrl>SearchInventoryItemsByLabels</@ofbizUrl>" class="form-inline">
  <input type="hidden" name="facilityId" value="${facility.facilityId}"/>
 <div class="form-group">
  <#assign index = 0>
  <#list labelTypes as labelType>
    <#assign index = index + 1>
    <#assign labels = labelType.getRelated("InventoryItemLabel", Static["org.ofbiz.base.util.UtilMisc"].toList("inventoryItemLabelId"))>

          <div class="input-group">
          <div class="input-group-addon"><span>${labelType.description?if_exists} [${labelType.inventoryItemLabelTypeId}]</span></div>

          <select name="inventoryItemLabelId_${index}" class="form-control">
            <option></option>
            <#list labels as label>
            <option value="${label.inventoryItemLabelId}" <#if parameters["inventoryItemLabelId_" + index]?has_content && parameters["inventoryItemLabelId_" + index] == label.inventoryItemLabelId>selected="selected"</#if>>${label.description?if_exists} [${label.inventoryItemLabelId}]</option>
            </#list>
          </select>
          </div>

  </#list>

  <input type="submit" value="搜索" class="btn btn-success btn-sm"/>
      <input type="hidden" name="numberOfFields" value="${index}"/>
 </div>
</form>
