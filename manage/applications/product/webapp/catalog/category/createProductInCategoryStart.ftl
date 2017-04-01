<#assign productFeaturesByTypeMap = Static["org.ofbiz.product.feature.ParametricSearch"].makeCategoryFeatureLists(productCategoryId, delegator)>
<@htmlScreenTemplate.renderScreenletBegin id="" title=""/>
<form name="createProductInCategoryCheckExistingForm" method="post" action="<@ofbizUrl>CreateProductInCategoryCheckExisting</@ofbizUrl>" class="form-horizontal">
    <input type="hidden" name="productCategoryId" value="${productCategoryId}"/>
    <#list productFeaturesByTypeMap.keySet() as productFeatureTypeId>
        <#assign findPftMap = Static["org.ofbiz.base.util.UtilMisc"].toMap("productFeatureTypeId", productFeatureTypeId)>
        <#assign productFeatureType = delegator.findByPrimaryKeyCache("ProductFeatureType", findPftMap)>
        <#assign productFeatures = productFeaturesByTypeMap[productFeatureTypeId]>
        <div class="form-group">
            <label class="control-label col-md-3"> ${productFeatureType.description}:</label>
            <div class="col-md-5  input-group">
                <select name="pft_${productFeatureTypeId}" class="form-control">
                    <option value="">- ${uiLabelMap.CommonNone} -</option>
                    <#list productFeatures as productFeature>
                        <option value="${productFeature.productFeatureId}">${productFeature.description}</option>
                    </#list>
                </select>
                <span class="input-group-addon">
                <input type="checkbox" class="" name="pftsel_${productFeatureTypeId}"/>${uiLabelMap.ProductSelectable}</span>
            </div>
        </div>
    </#list>
    <div class="form-group">
        <label class="control-label col-md-3"> ${uiLabelMap.ProductInternalName}:</label>
        <div class="col-md-5 "><input type="text" name="internalName" class="form-control" size="30" maxlength="60"/></div>
    </div>
    <div class="form-group">
        <label class="control-label col-md-3"> ${uiLabelMap.ProductProductName}:</label>

        <div class="col-md-5 "><input type="text" name="productName" class="form-control" size="30" maxlength="60"/></div>
    </div>
    <div class="form-group">
        <label class="control-label col-md-3"> ${uiLabelMap.ProductShortDescription}:</label>

        <div class="col-md-5 "><input type="text" name="description" class="form-control" size="60" maxlength="250"/></div>
    </div>
    <div class="form-group">
        <label class="control-label col-md-3"> ${uiLabelMap.ProductDefaultPrice}:</label>
        <div class="col-md-3">
        <#assign findCurrenciesMap = Static["org.ofbiz.base.util.UtilMisc"].toMap("uomTypeId", "CURRENCY_MEASURE","uomId","CNY")>
        <#assign currencies = delegator.findByAndCache('Uom', findCurrenciesMap) />
        <#if currencies?has_content && (currencies?size > 0)>
            <select name="currencyUomId" class="form-control">
                <#list currencies as currency>
                    <option value="${currency.uomId}">${currency.get("description",locale)} [${currency.uomId}]</option>
                </#list>
            </select>
        </#if>
        </div>
        <div class="col-md-3 ">
            <input type="text" name="defaultPrice" size="8" class="form-control"/>
        </div>
    </div>
    <div class="form-group">
        <label class="control-label col-md-3">${uiLabelMap.ProductAverageCost}:</label>
        <div class="col-md-5 "><input type="text" name="averageCost" size="8" class="form-control"/></div>
    </div>
    <div class="form-group">
        <label class="control-label col-md-3">${uiLabelMap.ProductListPrice}:</label>
        <div class="col-md-5 "><input type="text" name="listPrice" size="8" class="form-control"/></div>
    </div>
    <div class="form-group">
        <label class="control-label col-md-3">${uiLabelMap.ProductPromoPrice}:</label>
        <div class="col-md-5 "><input type="text" name="promoPrice" size="8" class="form-control"/></div>
    </div>
    <div class="form-group">
        <div class="col-md-3">&nbsp;</div>
        <div class="col-md-5  pull-right"><a href="javascript:document.createProductInCategoryCheckExistingForm.submit()"
                                             class="btn btn-primary btn-sm">${uiLabelMap.ProductCheckExisting}</a></div>
    </div>
</form>
<@htmlScreenTemplate.renderScreenletEnd/>
