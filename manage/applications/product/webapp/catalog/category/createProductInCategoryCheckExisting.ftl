<@htmlScreenTemplate.renderScreenletBegin id="" title="检查已有的产品"/>
<b>分类：<#if (productCategory.description)?has_content>"${productCategory.description}"</#if> [${uiLabelMap.CommonId}:${productCategoryId?if_exists}]</b>
<#if productFeatureAndTypeDatas?has_content>
<br/>
条件：
    <#list productFeatureAndTypeDatas as productFeatureAndTypeData>
        <#assign productFeatureType = productFeatureAndTypeData.productFeatureType>
        <#assign productFeature = productFeatureAndTypeData.productFeature>
    ${productFeatureType.description} = ${productFeature.description}
        <#if productFeatureAndTypeData_has_next>,${uiLabelMap.CommonAnd} </#if>
    </#list>
</#if>
<#if products?has_content>
<table class="table table-striped table-bordered">
    <tr>
        <th>${uiLabelMap.ProductInternalName}</
        >
        <th>${uiLabelMap.ProductProductName}</th>
        <th width="10%">&nbsp;</th>
    </tr>
    <#list products as product>
        <tr>
            <td>${product.internalName?default("-no internal name-")} [${product.productId}]</td>
            <td>${product.productName?default("-no name-")} [${product.productId}]</td>
            <td width="10%"><a href="<@ofbizUrl>EditProduct?productId=${product.productId}</@ofbizUrl>" class="buttontext">[${uiLabelMap.ProductThisIsIt}]</a></td>
        </tr>
    </#list>
</table>
<#else>
<div class="alert alert-warning fade in m-b-15">结果：${uiLabelMap.ProductNoExistingProductsFound}.</div>
</#if>

<form name="createProductInCategoryForm" method="post" action="<@ofbizUrl>createProductInCategory</@ofbizUrl>" class="form-horizontal">
    <input type="hidden" name="productCategoryId" value="${productCategoryId}"/>

<#list productFeatureAndTypeDatas?if_exists as productFeatureAndTypeData>
    <#assign productFeatureType = productFeatureAndTypeData.productFeatureType>
    <#assign productFeature = productFeatureAndTypeData.productFeature>
    <#assign productFeatureTypeId = productFeatureType.productFeatureTypeId>
    <input type="hidden" name="pft_${productFeatureType.productFeatureTypeId}" value="${productFeature.productFeatureId}"/>

    <div class="form-group">
        <div class="col-md-3">${productFeatureType.description}</div>
        <div class="col-md-5 ">

        ${productFeature.description}
            <#if requestParameters["pftsel_" + productFeatureTypeId]?exists>
                <input type="hidden" name="pftsel_${productFeatureTypeId}" value="Y"/>
                [${uiLabelMap.ProductSelectable}]
            <#else>
                <input type="hidden" name="pftsel_${productFeatureTypeId}" value="N"/>
                [${uiLabelMap.ProductStandard}]
            </#if>

        </div>
    </div>
</#list>
    <div class="form-group">
        <div class="col-md-3">${uiLabelMap.ProductInternalName}:</div>
        <div class="col-md-5 ">
            <input type="hidden" name="internalName" value="${requestParameters.internalName?if_exists}"/>

            &nbsp;${requestParameters.internalName?default("&nbsp;")}
        </div>
    </div>
    <div class="form-group">
        <div class="col-md-3">${uiLabelMap.ProductProductName}:</div>
        <div class="col-md-5 ">
            <input type="hidden" name="productName" value="${requestParameters.productName?if_exists}"/>
            &nbsp;${requestParameters.productName?default("&nbsp;")}
        </div>
    </div>
    <div class="form-group">
        <div class="col-md-3">${uiLabelMap.ProductShortDescription}:</div>
        <div class="col-md-5 ">
            <input type="hidden" name="description" value="${requestParameters.description?if_exists}"/>

            &nbsp;${requestParameters.description?default("&nbsp;")}
        </div>
    </div>
    <div class="form-group">
        <div class="col-md-3">${uiLabelMap.ProductDefaultPrice}:</div>
        <div class="col-md-5 ">
            <input type="hidden" name="defaultPrice" value="${requestParameters.defaultPrice?if_exists}"/>
            <input type="hidden" name="currencyUomId" value="${requestParameters.currencyUomId?if_exists}"/>

            &nbsp;${requestParameters.defaultPrice?default("&nbsp;")}&nbsp;${requestParameters.currencyUomId?default("&nbsp;")}
        </div>
    </div>
    <div class="form-group">
        <div class="col-md-3">${uiLabelMap.ProductAverageCost}:</div>
        <div class="col-md-5 ">
            <input type="hidden" name="averageCost" value="${requestParameters.averageCost?if_exists}"/>

            &nbsp;${requestParameters.averageCost?default("&nbsp;")}
        </div>
    </div>
    <div class="form-group">
        <div class="col-md-3">${uiLabelMap.ProductListPrice}:</div>
        <div class="col-md-5 ">
            <input type="hidden" name="listPrice" value="${requestParameters.listPrice?if_exists}"/>

            &nbsp;${requestParameters.listPrice?default("&nbsp;")}
        </div>
    </div>
    <div class="form-group">
        <div class="col-md-3">${uiLabelMap.ProductPromoPrice}:</div>
        <div class="col-md-5 ">
            <input type="hidden" name="promoPrice" value="${requestParameters.promoPrice?if_exists}"/>

            &nbsp;${requestParameters.promoPrice?default("&nbsp;")}
        </div>
    </div>
    <div class="form-group">
        <div class="col-md-3">
        ${uiLabelMap.ProductNewProductId}:
        </div>
        <div class="col-md-5 ">
            <input type="text" name="productId" value=""/>
        </div>
    </div>
    <div class="form-group">
        <div class="col-md-3">&nbsp;</div>
        <div class="col-md-5 ">

            <input type="submit" value="${uiLabelMap.ProductCreateNewProduct}" class="btn btn-primary btn-sm"/>
        </div>
    </div>
</form>