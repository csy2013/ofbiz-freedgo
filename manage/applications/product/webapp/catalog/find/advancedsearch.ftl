<@htmlScreenTemplate.renderScreenletBegin id="" title="${uiLabelMap.ProductAdvancedSearchInCategory}"/>
<form name="advtokeywordsearchform" method="post" action="<@ofbizUrl>keywordsearch</@ofbizUrl>" class="form-inline" ">
<input type="hidden" name="VIEW_SIZE" value="25"/>
<input type="hidden" name="PAGING" value="Y"/>
<input type="hidden" name="noConditionFind" value="Y"/>

<#if searchCategory?has_content>
<input type="hidden" name="SEARCH_CATEGORY_ID" value="${searchCategoryId?if_exists}"/>
</#if>
<div class="form-group">
<#if searchCategory?has_content>
    <div class="input-group m-b-10 m-r-5">
    <span class="input-group-addon">
    ${uiLabelMap.ProductCategory}:
    </span>
        <b>"${(searchCategory.description)?if_exists}" [${(searchCategory.productCategoryId)?if_exists}]</b> ${uiLabelMap.ProductIncludeSubCategories}
    ${uiLabelMap.CommonYes}<input type="radio" name="SEARCH_SUB_CATEGORIES" value="Y" checked="checked"/>
    ${uiLabelMap.CommonNo}<input type="radio" name="SEARCH_SUB_CATEGORIES" value="N"/>
    </div>
<#else>
    <div class="input-group m-b-10 m-r-5">
    <span class="input-group-addon">
    ${uiLabelMap.ProductCatalog}:
    </span>
        <select class="form-control" name="SEARCH_CATALOG_ID">
            <option value="">- ${uiLabelMap.ProductAnyCatalog} -</option>
            <#list prodCatalogs as prodCatalog>
                <#assign displayDesc = prodCatalog.catalogName?default("${uiLabelMap.ProductNoDescription}")>
                <#if 18 < displayDesc?length>
                    <#assign displayDesc = displayDesc[0..15] + "...">
                </#if>
                <option value="${prodCatalog.prodCatalogId}">${displayDesc} [${prodCatalog.prodCatalogId}]</option>
            </#list>
        </select>


    </div>
    <div class="input-group m-b-10 m-r-5">
    <span class="input-group-addon">
    ${uiLabelMap.ProductCategory}:
    </span>
        <@htmlTemplate.lookupField value="${requestParameters.SEARCH_CATEGORY_ID?if_exists}" formName="advtokeywordsearchform" name="SEARCH_CATEGORY_ID" id="SEARCH_CATEGORY_ID" fieldFormName="LookupProductCategory"/>
        <span class="input-group-addon">${uiLabelMap.ProductIncludeSubCategories}
        ${uiLabelMap.CommonYes}<input type="radio" name="SEARCH_SUB_CATEGORIES" value="Y" checked="checked"/>
        ${uiLabelMap.CommonNo}<input type="radio" name="SEARCH_SUB_CATEGORIES" value="N"/>
        ${uiLabelMap.CommonInclude}<input type="radio" name="SEARCH_CATEGORY_EXC" value="" checked="checked"/>
        ${uiLabelMap.CommonExclude}<input type="radio" name="SEARCH_CATEGORY_EXC" value="Y"/>
        ${uiLabelMap.CommonAlwaysInclude}<input type="radio" name="SEARCH_CATEGORY_EXC" value="N"/>
    </span>
    </div>
</#if>
    <div class="input-group m-b-10 m-r-5">
        <span class="input-group-addon">
        ${uiLabelMap.ProductProductName}:
        </span>
        <input class="form-control" type="text" name="SEARCH_PRODUCT_NAME" size="20" value="${requestParameters.SEARCH_PRODUCT_NAME?if_exists}"/>
    </div>
    <div class="input-group m-b-10 m-r-5">
        <span class="input-group-addon">
        ${uiLabelMap.ProductInternalName}:
        </span>
        <input class="form-control" type="text" name="SEARCH_INTERNAL_PROD_NAME" size="20" value="${requestParameters.SEARCH_INTERNAL_PROD_NAME?if_exists}"/>
    </div>
    <div class="input-group m-b-10 m-r-5">
        <span class="input-group-addon">
        ${uiLabelMap.ProductKeywords}:
        </span>
        <input class="form-control" type="text" name="SEARCH_STRING" value="${requestParameters.SEARCH_STRING?if_exists}"/>
        <span class="input-group-addon">
        ${uiLabelMap.CommonAny}<input type="radio" name="SEARCH_OPERATOR" value="OR" <#if searchOperator == "OR">checked="checked"</#if>/>
        ${uiLabelMap.CommonAll}<input type="radio" name="SEARCH_OPERATOR" value="AND" <#if searchOperator == "AND">checked="checked"</#if>/>
        </span>
    </div>


    <div class="input-group m-b-10 m-r-5">
    <span class="input-group-addon">
    ${uiLabelMap.ProductFeatureCategory} ${uiLabelMap.CommonIds}1:
    </span>
        <input class="form-control" type="text" name="SEARCH_PROD_FEAT_CAT1" size="15" value="${requestParameters.SEARCH_PROD_FEAT_CAT1?if_exists}"/>
        <span class="input-group-addon">
        ${uiLabelMap.CommonInclude}<input type="radio" name="SEARCH_PROD_FEAT_CAT_EXC1" value="" checked="checked"/>
        ${uiLabelMap.CommonExclude}<input type="radio" name="SEARCH_PROD_FEAT_CAT_EXC1" value="Y"/>
        ${uiLabelMap.CommonAlwaysInclude}<input type="radio" name="SEARCH_PROD_FEAT_CAT_EXC1" value="N"/>
         </span>
    </div>
    <div class="input-group m-b-10 m-r-5">
    <span class="input-group-addon">
    ${uiLabelMap.ProductFeatureCategory} ${uiLabelMap.CommonIds}2:
    </span>
        <input class="form-control" type="text" name="SEARCH_PROD_FEAT_CAT2" size="15" value="${requestParameters.SEARCH_PROD_FEAT_CAT2?if_exists}"/>
        <span class="input-group-addon">
    ${uiLabelMap.CommonInclude}<input type="radio" name="SEARCH_PROD_FEAT_CAT_EXC2" value="" checked="checked"/>
    ${uiLabelMap.CommonExclude}<input type="radio" name="SEARCH_PROD_FEAT_CAT_EXC2" value="Y"/>
    ${uiLabelMap.CommonAlwaysInclude}<input type="radio" name="SEARCH_PROD_FEAT_CAT_EXC2" value="N"/>
            </span>
    </div>

    <div class="input-group m-b-10 m-r-5">
    <span class="input-group-addon">
    ${uiLabelMap.ProductFeatureCategory} ${uiLabelMap.CommonIds}3:
    </span>

        <input class="form-control" type="text" name="SEARCH_PROD_FEAT_CAT3" size="15" value="${requestParameters.SEARCH_PROD_FEAT_CAT3?if_exists}"/>
        <span class="input-group-addon">
    ${uiLabelMap.CommonInclude}<input type="radio" name="SEARCH_PROD_FEAT_CAT_EXC3" value="" checked="checked"/>
    ${uiLabelMap.CommonExclude}<input type="radio" name="SEARCH_PROD_FEAT_CAT_EXC3" value="Y"/>
    ${uiLabelMap.CommonAlwaysInclude}<input type="radio" name="SEARCH_PROD_FEAT_CAT_EXC3" value="N"/>
            </span>
    </div>
    <div class="input-group m-b-10 m-r-5">
    <span class="input-group-addon">
    ${uiLabelMap.ProductFeatureGroup} ${uiLabelMap.CommonIds}1:
    </span>
        <input class="form-control" type="text" name="SEARCH_PROD_FEAT_GRP1" size="15" value="${requestParameters.SEARCH_PROD_FEAT_GRP1?if_exists}"/>
        <span class="input-group-addon">
${uiLabelMap.CommonInclude}<input type="radio" name="SEARCH_PROD_FEAT_GRP_EXC1" value="" checked="checked"/>
${uiLabelMap.CommonExclude}<input type="radio" name="SEARCH_PROD_FEAT_GRP_EXC1" value="Y"/>
${uiLabelMap.CommonAlwaysInclude}<input type="radio" name="SEARCH_PROD_FEAT_GRP_EXC1" value="N"/>
            </span>
    </div>

    <div class="input-group m-b-10 m-r-5">
    <span class="input-group-addon">
    ${uiLabelMap.ProductFeatureGroup} ${uiLabelMap.CommonIds}2:
    </span>
        <input class="form-control" type="text" name="SEARCH_PROD_FEAT_GRP2" size="15" value="${requestParameters.SEARCH_PROD_FEAT_GRP2?if_exists}"/>
         <span class="input-group-addon">
${uiLabelMap.CommonInclude}<input type="radio" name="SEARCH_PROD_FEAT_GRP_EXC2" value="" checked="checked"/>
${uiLabelMap.CommonExclude}<input type="radio" name="SEARCH_PROD_FEAT_GRP_EXC2" value="Y"/>
${uiLabelMap.CommonAlwaysInclude}<input type="radio" name="SEARCH_PROD_FEAT_GRP_EXC2" value="N"/>
             </span>
    </div>

    <div class="input-group m-b-10 m-r-5">
    <span class="input-group-addon">
    ${uiLabelMap.ProductFeatureGroup} ${uiLabelMap.CommonIds}3:
    </span>

        <input class="form-control" type="text" name="SEARCH_PROD_FEAT_GRP3" size="15" value="${requestParameters.SEARCH_PROD_FEAT_GRP3?if_exists}"/>
         <span class="input-group-addon">
${uiLabelMap.CommonInclude}<input type="radio" name="SEARCH_PROD_FEAT_GRP_EXC3" value="" checked="checked"/>
${uiLabelMap.CommonExclude}<input type="radio" name="SEARCH_PROD_FEAT_GRP_EXC3" value="Y"/>
${uiLabelMap.CommonAlwaysInclude}<input type="radio" name="SEARCH_PROD_FEAT_GRP_EXC3" value="N"/>
             </span>


    </div>

    <div class="input-group m-b-10 m-r-5">
    <span class="input-group-addon">
    ${uiLabelMap.ProductFeatures} ${uiLabelMap.CommonIds}1:
    </span>


        <input class="form-control" type="text" name="SEARCH_FEAT1" size="15" value="${requestParameters.SEARCH_FEAT1?if_exists}"/>
    <span class="input-group-addon">
${uiLabelMap.CommonInclude}<input type="radio" name="SEARCH_FEAT_EXC1" value="" checked="checked"/>
${uiLabelMap.CommonExclude}<input type="radio" name="SEARCH_FEAT_EXC1" value="Y"/>
${uiLabelMap.CommonAlwaysInclude}<input type="radio" name="SEARCH_FEAT_EXC1" value="N"/>
        </span>
    </div>
    <div class="input-group m-b-10 m-r-5">
    <span class="input-group-addon">
    ${uiLabelMap.ProductFeatures} ${uiLabelMap.CommonIds}2:
    </span>
        <input class="form-control" type="text" name="SEARCH_FEAT2" size="15" value="${requestParameters.SEARCH_FEAT2?if_exists}"/>
        <span class="input-group-addon">
${uiLabelMap.CommonInclude}<input type="radio" name="SEARCH_FEAT_EXC2" value="" checked="checked"/>
${uiLabelMap.CommonExclude}<input type="radio" name="SEARCH_FEAT_EXC2" value="Y"/>
${uiLabelMap.CommonAlwaysInclude}<input type="radio" name="SEARCH_FEAT_EXC2" value="N"/></span>
    </div>


    <div class="input-group m-b-10 m-r-5">
    <span class="input-group-addon">
    ${uiLabelMap.ProductFeatures} ${uiLabelMap.CommonIds}3:
    </span>
        <input class="form-control" type="text" name="SEARCH_FEAT3" size="15" value="${requestParameters.SEARCH_FEAT3?if_exists}"/>
        <span class="input-group-addon">
${uiLabelMap.CommonInclude}<input type="radio" name="SEARCH_FEAT_EXC3" value="" checked="checked"/>
${uiLabelMap.CommonExclude}<input type="radio" name="SEARCH_FEAT_EXC3" value="Y"/>
${uiLabelMap.CommonAlwaysInclude}<input type="radio" name="SEARCH_FEAT_EXC3" value="N"/>
</span>
    </div>
    <div class="input-group m-b-10 m-r-5">
    <span class="input-group-addon">
    ${uiLabelMap.ProductListPriceRange}:
    </span>
         <span class="input-group-addon">
  <input class="form-control col-md-4" type="text" name="LIST_PRICE_LOW" size="8" value="${requestParameters.LIST_PRICE_LOW?if_exists}"/>
        <input class="form-control col-md-4" type="text" name="LIST_PRICE_HIGH" size="8" value="${requestParameters.LIST_PRICE_HIGH?if_exists}"/>
</span>

    </div>
<#list productFeatureTypeIdsOrdered as productFeatureTypeId>
    <#assign findPftMap = Static["org.ofbiz.base.util.UtilMisc"].toMap("productFeatureTypeId", productFeatureTypeId)>
    <#assign productFeatureType = delegator.findByPrimaryKeyCache("ProductFeatureType", findPftMap)>
    <#assign productFeatures = productFeaturesByTypeMap[productFeatureTypeId]>
    <div class="input-group m-b-10 m-r-5">
    <span class="input-group-addon">
    ${(productFeatureType.get("description",locale))?if_exists}:
    </span>


        <select class="form-control" name="pft_${productFeatureTypeId}">
            <option value="">- ${uiLabelMap.CommonSelectAny} -</option>
            <#list productFeatures as productFeature>
                <option value="${productFeature.productFeatureId}">${productFeature.description?default("${uiLabelMap.ProductNoDescription}")} [${productFeature.productFeatureId}]
                </option>
            </#list>
        </select>


    </div>
</#list>
    <div class="input-group m-b-10 m-r-5">
    <span class="input-group-addon">
    ${uiLabelMap.ProductSupplier}:
    </span>


        <select class="form-control" name="SEARCH_SUPPLIER_ID">
            <option value="">- ${uiLabelMap.CommonSelectAny} -</option>
        <#list supplerPartyRoleAndPartyDetails as supplerPartyRoleAndPartyDetail>
            <option value="${supplerPartyRoleAndPartyDetail.partyId}">${supplerPartyRoleAndPartyDetail.groupName?if_exists} ${supplerPartyRoleAndPartyDetail.firstName?if_exists} ${supplerPartyRoleAndPartyDetail.lastName?if_exists}
                [${supplerPartyRoleAndPartyDetail.partyId}]
            </option>
        </#list>
        </select>


    </div>
    <div class="input-group m-b-10 m-r-5">
    <span class="input-group-addon">
    ${uiLabelMap.CommonSortedBy}:
    </span>


        <select class="form-control" name="sortOrder">
            <option value="SortKeywordRelevancy">${uiLabelMap.ProductKeywordRelevancy}</option>
            <option value="SortProductField:productName">${uiLabelMap.ProductProductName}</option>
            <option value="SortProductField:internalName">${uiLabelMap.ProductInternalName}</option>
            <option value="SortProductField:totalQuantityOrdered">${uiLabelMap.ProductPopularityByOrders}</option>
            <option value="SortProductField:totalTimesViewed">${uiLabelMap.ProductPopularityByViews}</option>
            <option value="SortProductField:averageCustomerRating">${uiLabelMap.ProductCustomerRating}</option>
            <option value="SortProductPrice:LIST_PRICE">${uiLabelMap.ProductListPrice}</option>
            <option value="SortProductPrice:DEFAULT_PRICE">${uiLabelMap.ProductDefaultPrice}</option>
            <option value="SortProductPrice:AVERAGE_COST">${uiLabelMap.ProductAverageCost}</option>
            <option value="SortProductPrice:MINIMUM_PRICE">${uiLabelMap.ProductMinimumPrice}</option>
            <option value="SortProductPrice:MAXIMUM_PRICE">${uiLabelMap.ProductMaximumPrice}</option>
        </select>
        <span class="input-group-addon">
    ${uiLabelMap.ProductLowToHigh}<input type="radio" name="sortAscending" value="Y" checked="checked"/>
    ${uiLabelMap.ProductHighToLow}<input type="radio" name="sortAscending" value="N"/>
            </span>
    </div>
    <div class="input-group m-b-10 m-r-5">
    <span class="input-group-addon">
    ${uiLabelMap.ProductPrioritizeProductsInCategory}:
    </span>

    <@htmlTemplate.lookupField value="${requestParameters.PRIORITIZE_CATEGORY_ID?if_exists}" formName="advtokeywordsearchform" name="PRIORITIZE_CATEGORY_ID" id="PRIORITIZE_CATEGORY_ID" fieldFormName="LookupProductCategory"/>

    </div>
    <div class="input-group m-b-10 m-r-5">
    <span class="input-group-addon">
    ${uiLabelMap.ProductGoodIdentificationType}:

    </span>
        <select class="form-control" name="SEARCH_GOOD_IDENTIFICATION_TYPE">
            <option value="">- ${uiLabelMap.CommonSelectAny} -</option>
        <#list goodIdentificationTypes as goodIdentificationType>
            <option value="${goodIdentificationType.goodIdentificationTypeId}">${goodIdentificationType.get("description")?if_exists}</option>
        </#list>
        </select>

    </div>
    <div class="input-group m-b-10 m-r-5">
    <span class="input-group-addon">
    ${uiLabelMap.ProductGoodIdentificationValue}:

    </span>
        <input class="form-control" type="text" name="SEARCH_GOOD_IDENTIFICATION_VALUE" size="60" maxlength="60" value="${requestParameters.SEARCH_GOOD_IDENTIFICATION_VALUE?if_exists}"/>
   <span class="input-group-addon"> ${uiLabelMap.CommonInclude}<input type="radio" name="SEARCH_GOOD_IDENTIFICATION_INCL" value="Y" checked="checked"/>
    ${uiLabelMap.CommonExclude}<input type="radio" name="SEARCH_GOOD_IDENTIFICATION_INCL" value="N"/>
       </span>

    </div>
<#if searchConstraintStrings?has_content>
    <div class="input-group m-b-10 m-r-5">
    <span class="input-group-addon">
    ${uiLabelMap.ProductLastSearch}
    </span>

        <#list searchConstraintStrings as searchConstraintString>
            &nbsp;-&nbsp;${searchConstraintString}
        </#list>
        <span class="input-group-addon">${uiLabelMap.CommonSortedBy}:</span>${searchSortOrderString}

    ${uiLabelMap.ProductNewSearch}<input type="radio" name="clearSearch" value="Y" checked="checked"/>
    ${uiLabelMap.CommonRefineSearch}<input type="radio" name="clearSearch" value="N"/>


    </div>
</#if>
    <div class="input-group m-b-10 m-r-5">

        <a href="javascript:document.advtokeywordsearchform.submit()" class="btn btn-primary btn-sm">${uiLabelMap.CommonFind}</a>

    </div>
    </div>
    </form>
<@htmlScreenTemplate.renderScreenletEnd/>
