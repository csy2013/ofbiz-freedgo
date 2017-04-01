<@htmlScreenTemplate.renderScreenletBegin id="" title="${uiLabelMap.ProductSearchProducts}, ${uiLabelMap.ProductSearchFor}:"/>
<#list searchConstraintStrings as searchConstraintString>
<div>&nbsp;<a href="<@ofbizUrl>keywordsearch?removeConstraint=${searchConstraintString_index}&amp;clearSearch=N</@ofbizUrl>"
              class="btn btn-default btn-sm">X</a>&nbsp;${searchConstraintString}</div>
</#list>
<div class="alert alert-info fadeIn">
    <div>${uiLabelMap.CommonSortedBy}:${searchSortOrderString}</div>
</div>
<div class="btn-group">
    <a href="<@ofbizUrl>advancedsearch?SEARCH_CATEGORY_ID=${(requestParameters.SEARCH_CATEGORY_ID)?if_exists}</@ofbizUrl>"
       class="btn btn-primary btn-sm">${uiLabelMap.CommonRefineSearch}</a>
<#if paging == "Y">
    <#if parameters.ACTIVE_PRODUCT?has_content && parameters.GOOGLE_SYNCED?has_content && parameters.DISCONTINUED_PRODUCT?has_content>
        <a href="<@ofbizUrl>keywordsearch/~VIEW_INDEX=0/~VIEW_SIZE=99999/~clearSearch=N/~PAGING=N/~noConditionFind=${noConditionFind}/~ACTIVE_PRODUCT=${parameters.ACTIVE_PRODUCT}/~GOOGLE_SYNCED=${parameters.GOOGLE_SYNCED}/~DISCONTINUED_PRODUCT=${parameters.DISCONTINUED_PRODUCT}/~productStoreId=${parameters.productStoreId}</@ofbizUrl>"
           class="btn btn-primary btn-sm">${uiLabelMap.CommonPagingOff}</a>
    <#else>
        <a href="<@ofbizUrl>keywordsearch/~VIEW_INDEX=0/~VIEW_SIZE=99999/~clearSearch=N/~PAGING=N/~noConditionFind=${noConditionFind}</@ofbizUrl>"
           class="btn btn-primary btn-sm">${uiLabelMap.CommonPagingOff}</a>
    </#if>
<#else>
    <#if parameters.ACTIVE_PRODUCT?has_content && parameters.GOOGLE_SYNCED?has_content && parameters.DISCONTINUED_PRODUCT?has_content>
        <a href="<@ofbizUrl>keywordsearch/~VIEW_INDEX=0/~VIEW_SIZE=${previousViewSize}/~clearSearch=N/~PAGING=Y/~noConditionFind=${noConditionFind}/~ACTIVE_PRODUCT=${parameters.ACTIVE_PRODUCT}/~GOOGLE_SYNCED=${parameters.GOOGLE_SYNCED}/~DISCONTINUED_PRODUCT=${parameters.DISCONTINUED_PRODUCT}/~productStoreId=${parameters.productStoreId}</@ofbizUrl>"
           class="btn btn-primary btn-sm">${uiLabelMap.CommonPagingOn}</a>
    <#else>
        <a href="<@ofbizUrl>keywordsearch/~VIEW_INDEX=0/~VIEW_SIZE=${previousViewSize}/~clearSearch=N/~PAGING=Y/~noConditionFind=${noConditionFind}</@ofbizUrl>"
           class="btn btn-primary btn-sm">${uiLabelMap.CommonPagingOn}</a>
    </#if>
</#if>
</div>

<hr/>
<#if !productIds?has_content>
<br/><h2>&nbsp;${uiLabelMap.ProductNoResultsFound}.</h2>
</#if>

<#if productIds?has_content>
<script language="JavaScript" type="text/javascript">
    function checkProductToBagTextArea(field, idValue) {
        fullValue = idValue + "\n";
        tempStr = document.forms["quickCreateVirtualWithVariants"].elements["variantProductIdsBag"].value;
        if (field.checked) {
            if (tempStr.length > 0 && tempStr.substring(tempStr.length - 1, tempStr.length) != "\n") {
                tempStr = tempStr + "\n";
            }
            document.forms["quickCreateVirtualWithVariants"].elements["variantProductIdsBag"].value = tempStr + fullValue;
        } else {
            start = document.forms["quickCreateVirtualWithVariants"].elements["variantProductIdsBag"].value.indexOf(fullValue);
            if (start >= 0) {
                end = start + fullValue.length;
                document.forms["quickCreateVirtualWithVariants"].elements["variantProductIdsBag"].value = tempStr.substring(0, start) + tempStr.substring(end, tempStr.length);
                //document.forms["quickCreateVirtualWithVariants"].elements["variantProductIdsBag"].value += start + ", " + end + "\n";
            }
        }
    }

    function toggleAll(e) {
        var cform = document.products;
        var len = cform.elements.length;
        for (var i = 0; i < len; i++) {
            var element = cform.elements[i];
            if (element.name == "selectResult" && element.checked != e.checked) {
                toggle(element);
            }
        }
    }

    function toggle(e) {
        e.checked = !e.checked;
    }
</script>

<div class="table-responsive">
    <table id="data-table" class="table table-striped table-bordered">
        <tr>
            <td><input type="checkbox" name="selectAll" value="0" onclick="toggleAll(this);"/> <b>${uiLabelMap.ProductProduct}</b></td>
            <td align="right">
                <b>
                    <#if 0 < viewIndex?int>
                        <#if parameters.ACTIVE_PRODUCT?has_content && parameters.GOOGLE_SYNCED?has_content && parameters.DISCONTINUED_PRODUCT?has_content>
                            <a href="<@ofbizUrl>keywordsearch/~VIEW_INDEX=${viewIndex-1}/~VIEW_SIZE=${viewSize}/~clearSearch=N/~PAGING=${paging}/~noConditionFind=${noConditionFind}/~ACTIVE_PRODUCT=${parameters.ACTIVE_PRODUCT}/~GOOGLE_SYNCED=${parameters.GOOGLE_SYNCED}/~DISCONTINUED_PRODUCT=${parameters.DISCONTINUED_PRODUCT}/~productStoreId=${parameters.productStoreId}</@ofbizUrl>"
                               class="btn btn-primary btn-sm">上一页</a> |
                        <#else>
                            <a href="<@ofbizUrl>keywordsearch/~VIEW_INDEX=${viewIndex-1}/~VIEW_SIZE=${viewSize}/~clearSearch=N/~PAGING=${paging}/~noConditionFind=${noConditionFind}</@ofbizUrl>"
                               class="btn btn-primary btn-sm">上一页</a>
                        </#if>
                    </#if>
                    <#if 0 < listSize?int>
                    ${lowIndex+1} - ${highIndex}条，${uiLabelMap.CommonOf} ${listSize}条记录
                    </#if>
                    <#if highIndex?int < listSize?int>
                        <#if parameters.ACTIVE_PRODUCT?has_content && parameters.GOOGLE_SYNCED?has_content && parameters.DISCONTINUED_PRODUCT?has_content>
                            | <a
                                href="<@ofbizUrl>keywordsearch/~VIEW_INDEX=${viewIndex+1}/~VIEW_SIZE=${viewSize}/~clearSearch=N/~PAGING=${paging}/~noConditionFind=${noConditionFind}/~ACTIVE_PRODUCT=${parameters.ACTIVE_PRODUCT}/~GOOGLE_SYNCED=${parameters.GOOGLE_SYNCED}/~DISCONTINUED_PRODUCT=${parameters.DISCONTINUED_PRODUCT}/~productStoreId=${parameters.productStoreId}</@ofbizUrl>"
                                class="btn btn-primary btn-sm">下一页</a>
                        <#else>
                            <a href="<@ofbizUrl>keywordsearch/~VIEW_INDEX=${viewIndex+1}/~VIEW_SIZE=${viewSize}/~clearSearch=N/~PAGING=${paging}/~noConditionFind=${noConditionFind}</@ofbizUrl>"
                               class="btn btn-primary btn-sm">下一页</a>
                        </#if>
                    </#if>
                </b>
            </td>
        </tr>
    </table>
</div>

<form method="post" name="products">
    <input type="hidden" name="productStoreId" value="${parameters.productStoreId?if_exists}"/>
    <div class="table-responsive">
        <table id="data-table" class="table">
            <#assign listIndex = lowIndex>
            <#list productIds as productId><#-- note that there is no boundary range because that is being done before the list is put in the content -->
                <#assign product = delegator.findByPrimaryKey("Product", Static["org.ofbiz.base.util.UtilMisc"].toMap("productId", productId))>
                <tr>
                    <td>
                        <input type="checkbox" name="selectResult" value="${productId}" onchange="checkProductToBagTextArea(this, '${productId}');"/>
                        <a href="<@ofbizUrl>EditProduct?productId=${productId}</@ofbizUrl>" class="buttontext">[${productId}] ${(product.internalName)?if_exists}</a>
                    </td>
                </tr>
            <#-- toggle the row color -->

            </#list>
        </table>
    </div>
</form>


</#if>
<@htmlScreenTemplate.renderScreenletEnd/>
