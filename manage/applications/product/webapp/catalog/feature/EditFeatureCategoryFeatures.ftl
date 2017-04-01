    <@htmlScreenTemplate.renderScreenletBegin id="" title="${uiLabelMap.ProductEditFeaturesForFeatureCategory}${(curProductFeatureCategory.description)?if_exists}"/>
        <div class="button-bar">
            <a href="<@ofbizUrl>CreateFeature?productFeatureCategoryId=${productFeatureCategoryId?if_exists}</@ofbizUrl>" class="btn btn-primary btn-sm">${uiLabelMap.ProductCreateNewFeature}</a>
        </div>
        <hr/>
        <form action="<@ofbizUrl>QuickAddProductFeatures</@ofbizUrl>" method="post" class="form-inline">
            <div class="input-group">
                <div class="input-group-addon"><span>${uiLabelMap.CommonAdd}特征数量</span></div>
                <input type="text" name="featureNum" value="1" size="3" class="form-control"/>
            </div>
            <input class="btn btn-primary btn-sm" type="submit" value="${uiLabelMap.CommonCreate}"/>
            <input type="hidden" name="productFeatureCategoryId" value="${productFeatureCategoryId}"/>
        </form>
    <@htmlScreenTemplate.renderScreenletEnd/>
<@htmlScreenTemplate.renderScreenletBegin id="" title="${uiLabelMap.ProductProductFeatureMaintenance}"/>

<#if (listSize > 0)>
    <#if productId?has_content>
        <#assign productString = "&amp;productId=" + productId>
    </#if>
<div class="table-responsive">
    <table class="table table-striped table-bordered">
        <tr>
            <td align="right">
                    <span>
                    <b>
                        <#if (viewIndex > 0)>
                            <a href="<@ofbizUrl>EditFeatureCategoryFeatures?productFeatureCategoryId=${productFeatureCategoryId?if_exists}&amp;VIEW_SIZE=${viewSize}&amp;VIEW_INDEX=${viewIndex-1}${productString?if_exists}</@ofbizUrl>"
                               class="btn btn-primary btn-sm">[${uiLabelMap.CommonPrevious}]</a> |
                        </#if>
                    ${lowIndex+1} - ${highIndex} ${uiLabelMap.CommonOf} ${listSize}
                        <#if (listSize > highIndex)>
                            | <a
                                href="<@ofbizUrl>EditFeatureCategoryFeatures?productFeatureCategoryId=${productFeatureCategoryId?if_exists}&amp;VIEW_SIZE=${viewSize}&amp;VIEW_INDEX=${viewIndex+1}${productString?if_exists}</@ofbizUrl>"
                                class="btn btn-primary btn-sm">[${uiLabelMap.CommonNext}]</a>
                        </#if>
                    </b>
                    </span>
            </td>
        </tr>
    </table>
</div>
</#if>
    <br/>

    <form method='post' action='<@ofbizUrl>UpdateProductFeatureInCategory</@ofbizUrl>' name="selectAllForm" class="form-inline">
        <input type="hidden" name="_useRowSubmit" value="Y"/>
        <input type="hidden" name="_checkGlobalScope" value="N"/>
        <input type="hidden" name="productFeatureCategoryId" value="${productFeatureCategoryId}"/>
        <div class="table-responsive">
            <table class="table table-striped table-bordered">
            <tr class="header-row">
                <th><b>${uiLabelMap.CommonId}</b></th>
                <th><b>${uiLabelMap.CommonDescription}</b></th>
                <th><b>${uiLabelMap.ProductFeatureType}</b></th>
                <th><b>${uiLabelMap.ProductFeatureCategory}</b></th>
                <th><b>${uiLabelMap.ProductUnitOfMeasureId}</b></th>
                <th><b>${uiLabelMap.ProductQuantity}</b></th>
                <th><b>${uiLabelMap.ProductAmount}</b></th>
                <th><b>${uiLabelMap.ProductIdSeqNum}</b></th>
                <th><b>${uiLabelMap.ProductIdCode}</b></th>
                <th><b>${uiLabelMap.ProductAbbrev}</b></th>
                <th align="right"><b>${uiLabelMap.CommonAll}
                    <input type="checkbox" name="selectAll" value="${uiLabelMap.CommonY}"
                                                                   onclick="toggleAll(this, 'selectAllForm');highlightAllRows(this, 'productFeatureId_tableRow_', 'selectAllForm');"/></b>
                </th>
            </tr>
        <#if (listSize > 0)>
            <#assign rowCount = 0>
            <#assign rowClass = "2">
            <#list productFeatures as productFeature>
                <#assign curProductFeatureType = productFeature.getRelatedOneCache("ProductFeatureType")>
                <tr id="productFeatureId_tableRow_${rowCount}" valign="middle"<#if rowClass == "1"> class="alternate-row"</#if>>
                    <td><input type="hidden" name="productFeatureId_o_${rowCount}" value="${productFeature.productFeatureId}" />
                        <a href="<@ofbizUrl>EditFeature?productFeatureId=${productFeature.productFeatureId}</@ofbizUrl>" class="buttontext">${productFeature.productFeatureId}</a></td>
                    <td><input type="text" size='6' class="form-control" name="description_o_${rowCount}" value="${productFeature.description}"/></td>
                    <td><select name='productFeatureTypeId_o_${rowCount}' class="form-control">
                        <#if productFeature.productFeatureTypeId?has_content>
                            <option value='${productFeature.productFeatureTypeId}'><#if curProductFeatureType?exists>${curProductFeatureType.get("description",locale)?if_exists}<#else>
                                [${productFeature.productFeatureTypeId}]</#if></option>
                            <option value='${productFeature.productFeatureTypeId}'>---</option>
                        </#if>
                        <#list productFeatureTypes as productFeatureType>
                            <option value='${productFeatureType.productFeatureTypeId}'>${productFeatureType.get("description",locale)?if_exists}</option>
                        </#list>
                    </select></td>
                    <td><select name='productFeatureCategoryId_o_${rowCount}' class="form-control">
                        <#if productFeature.productFeatureCategoryId?has_content>
                            <#assign curProdFeatCat = productFeature.getRelatedOne("ProductFeatureCategory")>
                            <option value='${productFeature.productFeatureCategoryId}'>${(curProdFeatCat.description)?if_exists} [${productFeature.productFeatureCategoryId}]</option>
                            <option value='${productFeature.productFeatureCategoryId}'>---</option>
                        </#if>
                        <#list productFeatureCategories as productFeatureCategory>
                            <option value='${productFeatureCategory.productFeatureCategoryId}'>${productFeatureCategory.get("description",locale)?if_exists}
                                [${productFeatureCategory.productFeatureCategoryId}]
                            </option>
                        </#list>
                    </select></td>
                    <td><input type="text" size='6' class="form-control" name="uomId_o_${rowCount}" value="${productFeature.uomId?if_exists}"/></td>
                    <td><input type="text" size='5' class="form-control" name="numberSpecified_o_${rowCount}" value="${productFeature.numberSpecified?if_exists}"/></td>
                    <td><input type="text" size='5' class="form-control" name="defaultAmount_o_${rowCount}" value="${productFeature.defaultAmount?if_exists}"/></td>
                    <td><input type="text" size='5' class="form-control" name="defaultSequenceNum_o_${rowCount}" value="${productFeature.defaultSequenceNum?if_exists}"/></td>
                    <td><input type="text" size='5' class="form-control" name="idCode_o_${rowCount}" value="${productFeature.idCode?if_exists}"/></td>
                    <td><input type="text" size='5' class="form-control" name="abbrev_o_${rowCount}" value="${productFeature.abbrev?if_exists}"/></td>
                    <td align="right"><input type="checkbox" name="_rowSubmit_o_${rowCount}" value="Y"
                                             onclick="checkToggle(this, 'selectAllForm');highlightRow(this,'productFeatureId_tableRow_${rowCount}');"/></td>
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
                <td colspan="11" align="center">
                    <input type="hidden" name="_rowCount" value="${rowCount}"/>
                    <input type="submit" class="btn btn-primary btn-sm pull-right" value='${uiLabelMap.CommonUpdate}'/></td>
            </tr>
        </#if>
        </table>
            </div>
    </form>

<@htmlScreenTemplate.renderScreenletEnd/>
