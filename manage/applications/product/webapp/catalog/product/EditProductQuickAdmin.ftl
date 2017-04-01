<#assign externalKeyParam = "&amp;externalLoginKey=" + requestAttributes.externalLoginKey?if_exists>
<#if product?has_content>
<!-- First some general forms and scripts -->
<form name="removeAssocForm" action="<@ofbizUrl>quickAdminUpdateProductAssoc</@ofbizUrl>">
    <input type="hidden" name="productId" value="${product.productId?if_exists}"/>
    <input type="hidden" name="PRODUCT_ID" value="${product.productId?if_exists}"/>
    <input type="hidden" name="PRODUCT_ID_TO" value=""/>
    <input type="hidden" name="PRODUCT_ASSOC_TYPE_ID" value="PRODUCT_VARIANT"/>
    <input type="hidden" name="FROM_DATE" value=""/>
    <input type="hidden" name="UPDATE_MODE" value="DELETE"/>
    <input type="hidden" name="useValues" value="true"/>
</form>
<form name="removeSelectable" action="<@ofbizUrl>updateProductQuickAdminDelFeatureTypes</@ofbizUrl>">
    <input type="hidden" name="productId" value="${product.productId?if_exists}"/>
    <input type="hidden" name="productFeatureTypeId" value=""/>
</form>
<script language="JavaScript" type="text/javascript">

    function removeAssoc(productIdTo, fromDate) {
        if (confirm("Are you sure you want to remove the association of " + productIdTo + "?")) {
            document.removeAssocForm.PRODUCT_ID_TO.value = productIdTo;
            document.removeAssocForm.FROM_DATE.value = fromDate;
            document.removeAssocForm.submit();
        }
    }

    function removeSelectable(typeString, productFeatureTypeId, productId) {
        if (confirm("Are you sure you want to remove all the selectable features of type " + typeString + "?")) {
            document.removeSelectable.productId.value = productId;
            document.removeSelectable.productFeatureTypeId.value = productFeatureTypeId;
            document.removeSelectable.submit();
        }
    }

    function doPublish() {
        window.open('/ecommerce/control/product?product_id=${productId?if_exists}');
        document.publish.submit();
    }

</script>
    <@htmlScreenTemplate.renderScreenletBegin id="" title="${uiLabelMap.PageTitleEditProductQuickAdmin}"/>

<!-- Name update section -->
<form action="<@ofbizUrl>updateProductQuickAdminName</@ofbizUrl>" method="post" class="form-horizontal" name="editProduct">
    <input type="hidden" name="productId" value="${productId?if_exists}"/>
    <#if (product.isVirtual)?if_exists == "Y">
        <input type="hidden" name="isVirtual" value="Y"/>
    </#if>
    <div class="form-group">
        <label class="control-label col-md-3">${productId?if_exists}</label>

        <div class="col-md-3"><input type="text" class="form-control" name="productName" size="40" maxlength="40" value="${product.productName?if_exists}"/></div>
        <div class="col-md-3"><input type="submit" value="${uiLabelMap.ProductUpdateName}" class="btn btn-primary btn-sm"/></div>
    </div>
</form>
    <@htmlScreenTemplate.renderScreenletEnd/>
    <#if (product.isVirtual)?if_exists == "Y">
        <@htmlScreenTemplate.renderScreenletBegin id="" title="${uiLabelMap.ProductSelectableFeatures}"/>
    <!-- ***************************************************** Selectable features section -->
    <form action="<@ofbizUrl>EditProductQuickAdmin</@ofbizUrl>" method="post" class="form-horizontal" name="selectableFeatureTypeSelector">
        <input type="hidden" name="productId" value="${product.productId?if_exists}"/>

        <div class="form-group">
            <label class="col-md-3 control-label"></label>

            <div class="col-md-5>"><select name="productFeatureTypeId" onchange="document.selectableFeatureTypeSelector.submit();">
                <option value="~~任何~~">${uiLabelMap.ProductAnyFeatureType}</option>
                <#list featureTypes as featureType>
                    <#if (featureType.productFeatureTypeId)?if_exists == (productFeatureTypeId)?if_exists>
                        <#assign selected="selected"/>
                    <#else>
                        <#assign selected=""/>
                    </#if>
                    <option ${selected} value="${featureType.productFeatureTypeId?if_exists}">${featureType.get("description",locale)?if_exists}</option>
                </#list>
            </select>
            </div>
        </div>
    </form>
    <br/>
    <form action="<@ofbizUrl>updateProductQuickAdminSelFeat</@ofbizUrl>" method="post" class="form-horizontal" name="selectableFeature">
        <input type="hidden" name="productId" value="${product.productId?if_exists}"/>
        <input type="hidden" name="productFeatureTypeId" value="${(productFeatureTypeId)?if_exists}"/>

        <div class="table-responsive">
            <table class="table table-striped table-bordered">
                <tr class="header-row">
                    <th><b>${uiLabelMap.ProductProductId}</b></th>
                    <th><b>&nbsp;</b></th>
                    <th><b>&nbsp;</b></th>
                    <th><b>&nbsp;</b></th>
                    <th><b>${uiLabelMap.ProductSRCH}</b></th>
                    <th><b>${uiLabelMap.ProductDL}</b></th>
                </tr>
                <#assign idx=0/>
                <#assign rowClass = "2">
                <#list productAssocs as productAssoc>
                    <#assign assocProduct = productAssoc.getRelatedOne("AssocProduct")/>
                    <tr valign="middle"<#if rowClass == "1"> class="alternate-row"</#if>>
                        <td nowrap="nowrap">
                            <input type="hidden" name="productId${idx}" value="${assocProduct.productId?if_exists}"/>
                            <a class="buttontext" href="<@ofbizUrl>EditProduct?productId=${assocProduct.productId}</@ofbizUrl>">${assocProduct.productId?if_exists}</a></td>
                        <td width="100%"><a class="buttontext" href="<@ofbizUrl>EditProduct?productId=${assocProduct.productId}</@ofbizUrl>">${assocProduct.internalName?if_exists}</a></td>
                        <td colspan="2">
                            <input type="text" name="description${idx}" size="70" maxlength="100" value="${selFeatureDesc[assocProduct.productId]?if_exists}"/>
                        </td>
                        <#assign checked=""/>
                        <#if ((assocProduct.smallImageUrl?if_exists != "") && (assocProduct.smallImageUrl?if_exists == product.smallImageUrl?if_exists) &&
                        (assocProduct.smallImageUrl?if_exists != "") && (assocProduct.smallImageUrl?if_exists == product.smallImageUrl?if_exists)) >
                            <#assign checked = "checked='checked'"/>
                        </#if>
                        <td><input type="radio" ${checked} name="useImages" value="${assocProduct.productId}"/></td>
                        <#assign fromDate = Static["org.ofbiz.base.util.UtilFormatOut"].encodeQueryValue(productAssoc.getTimestamp("fromDate").toString())/>
                        <td><a class="buttontext" href="javascript:removeAssoc('${productAssoc.productIdTo}','${fromDate}');">x</a></td>
                    </tr>
                    <#assign idx = idx + 1/>
                <#-- toggle the row color -->
                    <#if rowClass == "2">
                        <#assign rowClass = "1">
                    <#else>
                        <#assign rowClass = "2">
                    </#if>
                </#list>
                <tr>
                    <td colspan="2">&nbsp;</td>
                    <td>
                        <table cellspacing="0" class="basic-table">
                            <#list selectableFeatureTypes as selectableFeatureType>
                                <tr>
                                    <td><a class="buttontext"
                                           href="javascript:removeSelectable('${(selectableFeatureType.get("description",locale))?if_exists}','${selectableFeatureType.productFeatureTypeId}','${product.productId}')">x</a>
                                        <a class="buttontext"
                                           href="<@ofbizUrl>EditProductQuickAdmin?productFeatureTypeId=${(selectableFeatureType.productFeatureTypeId)?if_exists}&amp;productId=${product.productId?if_exists}</@ofbizUrl>">${(selectableFeatureType.get("description",locale))?if_exists}</a>
                                    </td>
                                </tr>
                            </#list>
                        </table>
                    </td>
                    <td align="right">
                        <table cellspacing="0" class="basic-table">
                            <tr>
                                <td align="right"><input name="applyToAll" type="submit" value="${uiLabelMap.ProductAddSelectableFeature}"/></td>
                            </tr>
                        </table>
                    </td>
                </tr>
            </table>
        </div>
    </form>
        <@htmlScreenTemplate.renderScreenletEnd/>
    </#if>
    <#if (product.isVariant)?if_exists == "Y">
        <@htmlScreenTemplate.renderScreenletBegin id="" title="${uiLabelMap.ProductDistinguishingFeatures}"/>

    <form action="<@ofbizUrl>updateProductQuickAdminDistFeat</@ofbizUrl>" method="post" class="form-horizontal" name="distFeature">
        <input type="hidden" name="productId" value="${product.productId?if_exists}"/>
        <table class="table table-striped table-bordered">
            <tr class="header-row">
                <td><b>${uiLabelMap.ProductProductId}</b></td>
            </tr>
            <#assign idx=0/>
            <#assign rowClass = "2">
            <#list distinguishingFeatures as distinguishingFeature>
                <tr valign="middle"<#if rowClass == "1"> class="alternate-row"</#if>>
                    <td><a href="<@ofbizUrl>quickAdminRemoveProductFeature?productId=${productId}&amp;productFeatureId=${distinguishingFeature.productFeatureId}</@ofbizUrl>"
                           class="buttontext">x</a>&nbsp;
                    ${distinguishingFeature.productFeatureId} ${productFeatureTypeLookup.get(distinguishingFeature.productFeatureId).get("description",locale)}
                        : ${distinguishingFeature.get("description",locale)}
                        &nbsp;
                    </td>
                </tr>
            <#-- toggle the row color -->
                <#if rowClass == "2">
                    <#assign rowClass = "1">
                <#else>
                    <#assign rowClass = "2">
                </#if>
            </#list>
        </table>
    </form>
        <@htmlScreenTemplate.renderScreenletEnd/>
    </#if>
<!-- ***************************************************** end Selectable features section -->
    <@htmlScreenTemplate.renderScreenletBegin id="" title="${uiLabelMap.ProductShippingDimensionsAndWeights}"/>

<!-- ***************************************************** Shipping dimensions section -->
<form action="<@ofbizUrl>updateProductQuickAdminShipping</@ofbizUrl>" method="post" name="updateShipping" class="form-horizontal">
    <input type="hidden" name="productId" value="${product.productId?if_exists}"/>
    <input type="hidden" name="heightUomId" value="LEN_in"/>
    <input type="hidden" name="widthUomId" value="LEN_in"/>
    <input type="hidden" name="depthUomId" value="LEN_in"/>
    <input type="hidden" name="weightUomId" value="WT_oz"/>
    <table class="table table-striped table-bordered">
        <tr class="header-row">
            <th><b>${uiLabelMap.ProductProductHeight}</b></th>
            <th><b>${uiLabelMap.ProductProductWidth}</b></th>
            <th><b>${uiLabelMap.ProductProductDepth}</b></th>
            <th><b>${uiLabelMap.ProductWeight}</b></th>
            <th><b>${uiLabelMap.ProductFlOz}</b></th>
            <th><b>${uiLabelMap.ProductML}</b></th>
            <th><b>${uiLabelMap.ProductNtWt}</b></th>
            <th><b>${uiLabelMap.ProductGrams}</b></th>
            <th><b>${uiLabelMap.ProductHZ}</b></th>
            <th><b>${uiLabelMap.ProductST}</b></th>
            <th><b>${uiLabelMap.ProductTD}</b></th>
        </tr>
        <#if (product.isVirtual)?if_exists == "Y">
            <#assign idx=0/>
            <#assign rowClass = "2">
            <#list assocProducts as assocProduct>
                <tr valign="middle"<#if rowClass == "1"> class="alternate-row"</#if>>
                    <td><input type="text" name="productHeight${idx}" size="6" maxlength="20" value="${assocProduct.productHeight?if_exists}"/></td>
                    <td><input type="text" name="productWidth${idx}" size="6" maxlength="20" value="${assocProduct.productWidth?if_exists}"/></td>
                    <td><input type="text" name="productDepth${idx}" size="6" maxlength="20" value="${assocProduct.productDepth?if_exists}"/></td>
                    <td><input type="text" name="weight${idx}" size="6" maxlength="20" value="${assocProduct.weight?if_exists}"/></td>
                    <td><input type="text" name="~floz${idx}" size="6" maxlength="20" value="${featureFloz.get(assocProduct.productId)?if_exists}"/></td>
                    <td><input type="text" name="~ml${idx}" size="6" maxlength="20" value="${featureMl.get(assocProduct.productId)?if_exists}"/></td>
                    <td><input type="text" name="~ntwt${idx}" size="6" maxlength="20" value="${featureNtwt.get(assocProduct.productId)?if_exists}"/></td>
                    <td><input type="text" name="~grams${idx}" size="6" maxlength="20" value="${featureGrams.get(assocProduct.productId)?if_exists}"/></td>
                    <td><a class="buttontext"
                           href="<@ofbizUrl>EditProductFeatures?productId=${assocProduct.productId}</@ofbizUrl>">${StringUtil.wrapString(featureHazmat.get(assocProduct.productId)?if_exists)}</a>
                    </td>
                    <td><a class="buttontext"
                           href="<@ofbizUrl>EditProduct?productId=${assocProduct.productId}</@ofbizUrl>">${StringUtil.wrapString(featureSalesThru.get(assocProduct.productId)?if_exists)}</a>
                    </td>
                    <td><a class="buttontext"
                           href="<@ofbizUrl>EditProductAssoc?productId=${assocProduct.productId}</@ofbizUrl>">${StringUtil.wrapString(featureThruDate.get(assocProduct.productId)?if_exists)}</a>
                    </td>
                </tr>
                <#assign idx = idx + 1/>
            <#-- toggle the row color -->
                <#if rowClass == "2">
                    <#assign rowClass = "1">
                <#else>
                    <#assign rowClass = "2">
                </#if>
            </#list>
            <tr>
                <td colspan="10" align="right"><input name="applyToAll" type="submit" value="${uiLabelMap.ProductApplyToAll}"/>
                    &nbsp;&nbsp;<input name="updateShipping" type="submit" value="${uiLabelMap.ProductUpdateShipping}"/></td>
            </tr>
        <#else>
            <tr>
                <td><input type="text" class="form-control" name="productHeight" size="6" maxlength="20" value="${product.productHeight?if_exists}"/></td>
                <td><input type="text" class="form-control" name="productWidth" size="6" maxlength="20" value="${product.productWidth?if_exists}"/></td>
                <td><input type="text" class="form-control" name="productDepth" size="6" maxlength="20" value="${product.productDepth?if_exists}"/></td>
                <td><input type="text" class="form-control" name="weight" size="6" maxlength="20" value="${product.weight?if_exists}"/></td>
                <td><input type="text" class="form-control" name="~floz" size="6" maxlength="20" value="${floz?if_exists}"/></td>
                <td><input type="text" class="form-control" name="~ml" size="6" maxlength="20" value="${ml?if_exists}"/></td>
                <td><input type="text" class="form-control" name="~ntwt" size="6" maxlength="20" value="${ntwt?if_exists}"/></td>
                <td><input type="text" class="form-control" name="~grams" size="6" maxlength="20" value="${grams?if_exists}"/></td>
                <td><a class="buttontext" href="<@ofbizUrl>EditProductFeatures?productId=${product.productId}</@ofbizUrl>">${StringUtil.wrapString(hazmat?if_exists)}</a></td>
                <td><a class="buttontext" href="<@ofbizUrl>EditProduct?productId=${product.productId}</@ofbizUrl>">${StringUtil.wrapString(salesthru?if_exists)}</a></td>
                <td><a class="buttontext" href="<@ofbizUrl>EditProductAssoc?productId=${product.productId}</@ofbizUrl>">${StringUtil.wrapString(thrudate?if_exists)}</a></td>
            </tr>
            <tr>
                <td colspan="11" align="right"><input type="submit" value="${uiLabelMap.ProductUpdateShipping}" class="btn btn-primary btn-sm"/></td>
            </tr>
        </#if>

    </table>
</form>
<!--  **************************************************** end - Shipping dimensions section -->
    <@htmlScreenTemplate.renderScreenletEnd/>
    <@htmlScreenTemplate.renderScreenletBegin id="" title="${uiLabelMap.ProductStandardFeatures}"/>

<!--  **************************************************** Standard Features section -->
    <#if addedFeatureTypeIds?has_content || standardFeatureAppls?has_content>
    <table cellspacing="0" class="basic-table">
        <tr>
            <td>
                <#if addedFeatureTypeIds?has_content>
                    <form method="post" action="<@ofbizUrl>quickAdminApplyFeatureToProduct</@ofbizUrl>" name="addFeatureById">
                        <input type="hidden" name="productId" value="${product.productId?if_exists}"/>
                        <input type="hidden" name="productFeatureApplTypeId" value="STANDARD_FEATURE"/>
                        <input type="hidden" name="fromDate" value="${nowTimestampString}"/>
                        <table cellspacing="0" class="basic-table">
                            <#assign rowClass = "2">
                            <#list addedFeatureTypeIds as addedFeatureTypeId>
                                <tr valign="middle"<#if rowClass == "1"> class="alternate-row"</#if>>
                                    <td align="right">${addedFeatureTypes.get(addedFeatureTypeId).description}</td>
                                    <td>
                                        <select name="productFeatureId">
                                            <option value="~~any~~">${uiLabelMap.ProductAnyFeatureType}</option>
                                            <#list featuresByType.get(addedFeatureTypeId) as feature>
                                                <option value="${feature.getString("productFeatureId")}">${feature.description}</option>
                                            </#list>
                                        </select>
                                    </td>
                                </tr>
                            <#-- toggle the row color -->
                                <#if rowClass == "2">
                                    <#assign rowClass = "1">
                                <#else>
                                    <#assign rowClass = "2">
                                </#if>
                            </#list>
                            <tr>
                                <td colspan="2" align="right"><input type="submit" value="${uiLabelMap.ProductAddFeatures}"/></td>
                            </tr>
                        </table>
                    </form>
                </#if>
            </td>
            <td width="20">&nbsp;</td>
            <td valign="top">
                <#if standardFeatureAppls?has_content>
                    <table cellspacing="0" class="basic-table">
                        <#assign rowClass = "2">
                        <#list standardFeatureAppls as standardFeatureAppl>
                            <#assign featureId = standardFeatureAppl.productFeatureId/>
                            <tr valign="middle"<#if rowClass == "1"> class="alternate-row"</#if>>
                                <td colspan="2"><a
                                        href='<@ofbizUrl>quickAdminRemoveFeatureFromProduct?productId=${standardFeatureAppl.productId?if_exists}&amp;productFeatureId=${featureId?if_exists}&amp;fromDate=${(standardFeatureAppl.fromDate)?if_exists}</@ofbizUrl>'
                                        class="buttontext">x</a>
                                ${productFeatureTypeLookup.get(featureId).description}: ${standardFeatureLookup.get(featureId).description}
                                </td>
                            </tr>
                        <#-- toggle the row color -->
                            <#if rowClass == "2">
                                <#assign rowClass = "1">
                            <#else>
                                <#assign rowClass = "2">
                            </#if>
                        </#list>
                    </table>
                </#if>
            </td>
        </tr>
    </table>
    <br/>
    </#if>
<form action="<@ofbizUrl>EditProductQuickAdmin</@ofbizUrl>" class="form-inline">
    <input type="hidden" name="productFeatureTypeId" value="${(productFeatureTypeId)?if_exists}"/>
    <input type="hidden" name="productId" value="${product.productId?if_exists}"/>

    <div class="input-group">
        <div class="input-group-addon">
            <span>${uiLabelMap.ProductFeatureTypes}</span>
        </div>
        <select multiple="multiple" name="addFeatureTypeId" size="10">
            <#list featureTypes as featureType>
                <option value="${featureType.productFeatureTypeId?if_exists}">${featureType.get("description",locale)?if_exists}</option>
            </#list>
        </select>

        <div class="input-group-addon">
            <input type="submit" value="${uiLabelMap.ProductAddFeatureType}" class="btn btn-primary btn-sm"/>
        </div>

    </div>
</form>
<!--  **************************************************** end - Standard Features section -->
    <@htmlScreenTemplate.renderScreenletEnd/>
    <@htmlScreenTemplate.renderScreenletBegin id="" title="${uiLabelMap.ProductCategories}"/>

<!--  **************************************************** Categories section -->
<form action="<@ofbizUrl>quickAdminAddCategories</@ofbizUrl>" class="form-inline">
    <input type="hidden" name="fromDate" value="${nowTimestampString}"/>
    <input type="hidden" name="productId" value="${product.productId?if_exists}"/>
    <select multiple="multiple" name="categoryId" class="from-control" size="5">
        <#list allCategories as category>
            <option value="${category.productCategoryId?if_exists}">${category.description?if_exists} ${category.productCategoryId}</option>
        </#list>
    </select>
    <input type="submit" value="${uiLabelMap.ProductUpdateCategories}" class="btn btn-primary btn-sm"/>
</form>
<table>
    <tr>
        <td valign="top">
            <table cellspacing="0" class="basic-table">
                <#assign rowClass = "2">
                <#list productCategoryMembers as prodCatMemb>
                    <#assign prodCat = prodCatMemb.getRelatedOne("ProductCategory")/>
                    <tr valign="middle"<#if rowClass == "1"> class="alternate-row"</#if>>
                        <td colspan="2">
                            <form name="quickAdminRemoveProductFromCategory_${prodCatMemb_index}" action="<@ofbizUrl>quickAdminRemoveProductFromCategory</@ofbizUrl>" method="post">
                                <input type="hidden" name="productId" value="${prodCatMemb.productId?if_exists}"/>
                                <input type="hidden" name="productCategoryId" value="${prodCatMemb.productCategoryId}"/>
                                <input type="hidden" name="fromDate" value="${(prodCatMemb.fromDate)?if_exists}"/>
                                <a href="javascript:document.quickAdminRemoveProductFromCategory_${prodCatMemb_index}.submit();" class="buttontext">x</a>
                            ${prodCat.description?if_exists} ${prodCat.productCategoryId}
                            </form>
                        </td>
                    </tr>
                <#-- toggle the row color -->
                    <#if rowClass == "2">
                        <#assign rowClass = "1">
                    <#else>
                        <#assign rowClass = "2">
                    </#if>
                </#list>
            </table>
        </td>
    </tr>
</table>
<!--  **************************************************** end - Categories section -->
    <@htmlScreenTemplate.renderScreenletEnd/>
    <@htmlScreenTemplate.renderScreenletBegin id="" title="${uiLabelMap.ProductPublishAndView}"/>
<!--  **************************************************** publish section -->
    <#if (showPublish == "true")>
    <form action="<@ofbizUrl>quickAdminAddCategories</@ofbizUrl>" name="publish" class="form-inline">
        <input type="hidden" name="productId" value="${product.productId?if_exists}"/>
        <input type="hidden" name="categoryId" value="${allCategoryId?if_exists}"/>

        <@htmlTemplate.renderDateTimeField name="fromDate" event="" action="" className="" alert="" title="Format: yyyy-MM-dd HH:mm:ss.SSS" value="" size="25" maxlength="30" id="fromDate1" dateType="date" shortDateInput=false timeDropdownParamName="" defaultDateTimeString="" localizedIconTitle="" timeDropdown="" timeHourName="" classString="" hour1="" hour2="" timeMinutesName="" minutes="" isTwelveHour="" ampmName="" amSelected="" pmSelected="" compositeType="" formName=""/>
        <input type="button" value="${uiLabelMap.ProductPublishAndView}" onclick="doPublish();" class="btn btn-primary btn-sm"/>

    </form>
    <#else>
    <form action="<@ofbizUrl>quickAdminUnPublish</@ofbizUrl>" name="unpublish">
        <input type="hidden" name="productId" value="${product.productId?if_exists}"/>
        <input type="hidden" name="productCategoryId" value="${allCategoryId?if_exists}"/>

        <@htmlTemplate.renderDateTimeField name="thruDate" event="" action="" className="" alert="" title="Format: yyyy-MM-dd HH:mm:ss.SSS" value="" size="25" maxlength="30" id="thruDate1" dateType="date" shortDateInput=false timeDropdownParamName="" defaultDateTimeString="" localizedIconTitle="" timeDropdown="" timeHourName="" classString="" hour1="" hour2="" timeMinutesName="" minutes="" isTwelveHour="" ampmName="" amSelected="" pmSelected="" compositeType="" formName=""/>
        <input type="submit" value="${uiLabelMap.ProductRemoveFromSite}" class="btn btn-primary btn-sm"/>

    </form>
    </#if>
<!--  **************************************************** end - publish section -->

<#else>
<h3>${uiLabelMap.ProductProductNotFound} ${productId?if_exists}</h3>

</#if>
<@htmlScreenTemplate.renderScreenletEnd/>