<@htmlScreenTemplate.renderScreenletBegin id="" title="${uiLabelMap.PageTitleEditProductFeatureGroups}"/>
<@htmlScreenTemplate.renderModalPage id="AddProductFeatureGroup" name="AddProductFeatureGroup" modalTitle="添加特征组"  modalUrl="AddProductFeatureGroup" description="添加特征组"/>
<hr/>
<div class="table-responsive">
    <table class="table table-striped table-bordered">
          <tr class="header-row">
            <td><b>${uiLabelMap.CommonId}</b></td>
            <td><b>${uiLabelMap.CommonDescription}</b></td>
            <td><b>&nbsp;</b></td>
          </tr>
          <#assign rowClass = "2">
          <#list productFeatureGroups as productFeatureGroup>
            <tr valign="middle"<#if rowClass == "1"> class="alternate-row"</#if>>
                <td><a href='<@ofbizUrl>EditFeatureGroupAppls?productFeatureGroupId=${productFeatureGroup.productFeatureGroupId}</@ofbizUrl>' class="buttontext">${productFeatureGroup.productFeatureGroupId}</a></td>
                <td>
                    <form method='post' action='<@ofbizUrl>UpdateProductFeatureGroup</@ofbizUrl>' class="form-inline">
                    <input type='hidden' name="productFeatureGroupId" value="${productFeatureGroup.productFeatureGroupId}" />
                    <input type='text' size='30' name="description" value="${productFeatureGroup.description?if_exists}" class="form-control"/>
                    <input type="submit" value="${uiLabelMap.CommonUpdate}"  class="btn btn-primary btn-sm"/>
                    </form>
                </td>
                <td>
                    <@htmlScreenTemplate.renderConfirmField id="RemoveFeatureGroupAppl_${productFeatureGroup.productFeatureGroupId}" description="删除"
                    name="RemoveFeatureGroupAppl_${productFeatureGroup.productFeatureGroupId}"  confirmUrl="removeProductFeatureGroup"  confirmMessage="确定删除该记录?" confirmTitle="特征组删除"
                    targetParameterIter="productFeatureGroupId:'${productFeatureGroup.productFeatureGroupId}'" />
                </td>
                <#--<td><a href='<@ofbizUrl>EditFeatureGroupAppls?productFeatureGroupId=${productFeatureGroup.productFeatureGroupId}</@ofbizUrl>' class="btn btn-primary btn-sm">${uiLabelMap.ProductFeatureGroupAppls}</a></td>-->
            </tr>
            <#-- toggle the row color -->
            <#if rowClass == "2">
              <#assign rowClass = "1">
            <#else>
              <#assign rowClass = "2">
            </#if>
          </#list>
        </table>
    </div>
<@htmlScreenTemplate.renderScreenletEnd/>
