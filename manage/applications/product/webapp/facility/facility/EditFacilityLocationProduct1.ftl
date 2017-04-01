<#if locationSeqId?exists>

<table class="table table-striped table-bordered">
    <tr class="header-row">
        <td>${uiLabelMap.ProductProduct}</td>
        <td>${uiLabelMap.ProductMinimumStockAndMoveQuantity}</td>
        <td>&nbsp;</td>
    </tr>
    <#list productFacilityLocations?if_exists as productFacilityLocation>
        <#assign product = productFacilityLocation.getRelatedOne("Product")?if_exists>
        <tr>
            <td><#if product?exists>${(product.internalName)?if_exists}</#if>[${productFacilityLocation.productId}]</td>
            <td>
                <form method="post" action="<@ofbizUrl>updateProductFacilityLocation</@ofbizUrl>" id="lineForm${productFacilityLocation_index}" class="form-inline">
                    <input type="hidden" name="productId" value="${(productFacilityLocation.productId)?if_exists}"/>
                    <input type="hidden" name="facilityId" value="${(productFacilityLocation.facilityId)?if_exists}"/>
                    <input type="hidden" name="locationSeqId" value="${(productFacilityLocation.locationSeqId)?if_exists}"/>
                    <div class="input-group-sm">
                        <input type="text" class="form-control" size="3" name="minimumStock" value="${(productFacilityLocation.minimumStock)?if_exists}"/>
                        <input type="text" class="form-control" size="3" name="moveQuantity" value="${(productFacilityLocation.moveQuantity)?if_exists}"/>
                        <input type="button" value="${uiLabelMap.CommonUpdate}" class="btn btn-primary btn-xs"
                               onclick="ajaxUpdateArea('search-results', '/facility/control/updateProductFacilityLocation',$('#lineForm${productFacilityLocation_index}').serialize());alert('操作成功');"   />
                    </div>
                </form>
            </td>
            <td>
            <a href="#<#--javascript:document.getElementById('lineForm${productFacilityLocation_index}').action='<@ofbizUrl>deleteProductFacilityLocation</@ofbizUrl>';document.getElementById('lineForm${productFacilityLocation_index}').submit();-->"
                   class="btn btn-primary btn-xs"
                   onclick="if (confirm('确定删除该产品位置?')) {ajaxUpdateArea('search-results', '/facility/control/deleteProductFacilityLocation',$('#lineForm${productFacilityLocation_index}').serialize());}">${uiLabelMap.CommonDelete}</a>
            </td>
        </tr>
    </#list>
</table>

</#if>