<#if locationSeqId?exists>
<#if ajaxUpdateEvent?exists && ajaxUpdateEvent == 'Y'>
<#else>
    <@htmlScreenTemplate.renderScreenletBegin id="" title="${uiLabelMap.ProductAddProduct}"  collapsed=false/>
<form method="post" action="<@ofbizUrl>createProductFacilityLocation</@ofbizUrl>" data-parsley-validate="true" class="form-inline" name="createProductFacilityLocationForm"
      id="createProductFacilityLocationForm">

    <input type="hidden" name="facilityId" value="${facilityId?if_exists}"/>
    <input type="hidden" name="locationSeqId" value="${locationSeqId?if_exists}"/>
    <input type="hidden" name="useValues" value="true"/>
    <input type="hidden" name="ajaxUpdateEvent" value="Y"/>
    <div class="form-group">
        <div class="input-group m-b-10 m-r-5">
            <div class="input-group-addon"><span>${uiLabelMap.ProductProductId}</span></div>
            <@htmlTemplate.lookupField formName="createProductFacilityLocationForm" name="productId" id="productId" fieldFormName="LookupProduct" required="true"/>
        <#--<input type="text" size="10" name="productId"/>-->
        </div>
    </div>
    <div class="form-group">
        <div class="input-group m-b-10 m-r-5">
            <div class="input-group-addon">
                <span>${uiLabelMap.ProductMinimumStock}</span>
            </div>
            <input type="text" size="10" name="minimumStock" class="form-control" data-parsley-required="true"/>
        </div>

        <div class="input-group m-b-10 m-r-5">
            <div class="input-group-addon"><span>${uiLabelMap.ProductMoveQuantity}</span></div>

            <input type="text" size="10" name="moveQuantity" class="form-control" data-parsley-required="true"/>
        </div>
        <div class="input-group m-b-10 m-r-5">
            <div class="col-md-3">&nbsp;</div>
            <input type="button" value="保存" class="btn btn-primary btn-sm"
                   onclick="if ($('#createProductFacilityLocationForm').parsley().validate()){ajaxUpdateArea('search-results-product', 'createProductFacilityLocation',$('#createProductFacilityLocationForm').serialize());}"/>
        </div>
    </div>
</form>
    <@htmlScreenTemplate.renderScreenletEnd/>
    <@htmlScreenTemplate.renderScreenletBegin id="" title="${uiLabelMap.ProductLocationProduct}" collapsed=false/>
</#if>
<div class="table-responsive" id="search-results-product">
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
                        <input type="hidden" name="ajaxUpdateEvent" value="Y"/>
                        <div class="input-group-sm">
                            <input type="text" class="form-control" size="3" name="minimumStock" value="${(productFacilityLocation.minimumStock)?if_exists}"/>
                            <input type="text" class="form-control" size="3" name="moveQuantity" value="${(productFacilityLocation.moveQuantity)?if_exists}"/>
                            <input type="button" value="${uiLabelMap.CommonUpdate}" class="btn btn-primary btn-xs"
                                   onclick="ajaxUpdateArea('search-results-product', 'updateProductFacilityLocation',$('#lineForm${productFacilityLocation_index}').serialize());alert('操作成功');"
                                    />
                        </div>
                    </form>
                </td>
                <td>

                    <a href="#<#--javascript:document.getElementById('lineForm${productFacilityLocation_index}').action='<@ofbizUrl>deleteProductFacilityLocation</@ofbizUrl>';document.getElementById('lineForm${productFacilityLocation_index}').submit();-->"
                       class="btn btn-primary btn-xs"
                       onclick="if (confirm('确定删除该产品位置?')) {ajaxUpdateArea('search-results-product', 'deleteProductFacilityLocation',$('#lineForm${productFacilityLocation_index}').serialize());}">${uiLabelMap.CommonDelete}</a>
                </td>
            </tr>
        </#list>
    </table>
</div>
    <#if ajaxUpdateEvent?exists && ajaxUpdateEvent == 'Y'>
    <#else>
    <@htmlScreenTemplate.renderScreenletEnd/>
    </#if>

</#if>