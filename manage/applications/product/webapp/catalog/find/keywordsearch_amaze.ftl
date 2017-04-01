

<div class="am-cf am-padding-xs">
    <div class="am-panel am-panel-default">
        <div class="am-panel-hd am-cf">${uiLabelMap.ProductSearchProducts}, ${uiLabelMap.ProductSearchFor}:</div>
    <div  class="am-panel-bd">
        <#list searchConstraintStrings as searchConstraintString>
            <p class = "am-text-sm"><a href="<@ofbizUrl>keywordsearch?removeConstraint=${searchConstraintString_index}&amp;clearSearch=N</@ofbizUrl>" class="buttontext">X</a>&nbsp;${searchConstraintString}</p>
        </#list>
    </div>
        <div class="am-panel-bd">
            <font class = "am-text-sm">${uiLabelMap.CommonSortedBy}:${searchSortOrderString}</font>
        </div>
        <form method="post" name="products">
            <table class="am-table am-table-striped am-table-hover table-main">
                <tr>
                    <td><b>${uiLabelMap.ProductProduct}</b></td>
                    <td align="right">
                        <b>
                            <#if 0 < viewIndex?int>
                                <#if parameters.ACTIVE_PRODUCT?has_content && parameters.GOOGLE_SYNCED?has_content && parameters.DISCONTINUED_PRODUCT?has_content>
                                    <a href="<@ofbizUrl>keywordsearch/~VIEW_INDEX=${viewIndex-1}/~VIEW_SIZE=${viewSize}/~clearSearch=N/~PAGING=${paging}/~noConditionFind=${noConditionFind}/~ACTIVE_PRODUCT=${parameters.ACTIVE_PRODUCT}/~GOOGLE_SYNCED=${parameters.GOOGLE_SYNCED}/~DISCONTINUED_PRODUCT=${parameters.DISCONTINUED_PRODUCT}/~productStoreId=${parameters.productStoreId}</@ofbizUrl>" class="buttontext">${uiLabelMap.CommonPrevious}</a> |
                                <#else>
                                    <a href="<@ofbizUrl>keywordsearch/~VIEW_INDEX=${viewIndex-1}/~VIEW_SIZE=${viewSize}/~clearSearch=N/~PAGING=${paging}/~noConditionFind=${noConditionFind}</@ofbizUrl>" class="buttontext">${uiLabelMap.CommonPrevious}</a>
                                </#if>
                            </#if>
                            <#if 0 < listSize?int>
                            ${lowIndex+1} - ${highIndex} ${uiLabelMap.CommonOf} ${listSize}
                            </#if>
                            <#if highIndex?int < listSize?int>
                                <#if parameters.ACTIVE_PRODUCT?has_content && parameters.GOOGLE_SYNCED?has_content && parameters.DISCONTINUED_PRODUCT?has_content>
                                    |     <a href="<@ofbizUrl>keywordsearch/~VIEW_INDEX=${viewIndex+1}/~VIEW_SIZE=${viewSize}/~clearSearch=N/~PAGING=${paging}/~noConditionFind=${noConditionFind}/~ACTIVE_PRODUCT=${parameters.ACTIVE_PRODUCT}/~GOOGLE_SYNCED=${parameters.GOOGLE_SYNCED}/~DISCONTINUED_PRODUCT=${parameters.DISCONTINUED_PRODUCT}/~productStoreId=${parameters.productStoreId}</@ofbizUrl>" class="buttontext">${uiLabelMap.CommonNext}</a>
                                <#else>
                                    <a href="<@ofbizUrl>keywordsearch/~VIEW_INDEX=${viewIndex+1}/~VIEW_SIZE=${viewSize}/~clearSearch=N/~PAGING=${paging}/~noConditionFind=${noConditionFind}</@ofbizUrl>" class="buttontext">${uiLabelMap.CommonNext}</a>
                                </#if>
                            </#if>
                            <#if paging == "Y">
                                <#if parameters.ACTIVE_PRODUCT?has_content && parameters.GOOGLE_SYNCED?has_content && parameters.DISCONTINUED_PRODUCT?has_content>
                                    <a href="<@ofbizUrl>keywordsearch/~VIEW_INDEX=0/~VIEW_SIZE=99999/~clearSearch=N/~PAGING=N/~noConditionFind=${noConditionFind}/~ACTIVE_PRODUCT=${parameters.ACTIVE_PRODUCT}/~GOOGLE_SYNCED=${parameters.GOOGLE_SYNCED}/~DISCONTINUED_PRODUCT=${parameters.DISCONTINUED_PRODUCT}/~productStoreId=${parameters.productStoreId}</@ofbizUrl>" class="buttontext">${uiLabelMap.CommonPagingOff}</a>
                                <#else>
                                    <a href="<@ofbizUrl>keywordsearch/~VIEW_INDEX=0/~VIEW_SIZE=99999/~clearSearch=N/~PAGING=N/~noConditionFind=${noConditionFind}</@ofbizUrl>" class="buttontext">${uiLabelMap.CommonPagingOff}</a>
                                </#if>
                            <#else>
                                <#if parameters.ACTIVE_PRODUCT?has_content && parameters.GOOGLE_SYNCED?has_content && parameters.DISCONTINUED_PRODUCT?has_content>
                                    <a href="<@ofbizUrl>keywordsearch/~VIEW_INDEX=0/~VIEW_SIZE=${previousViewSize}/~clearSearch=N/~PAGING=Y/~noConditionFind=${noConditionFind}/~ACTIVE_PRODUCT=${parameters.ACTIVE_PRODUCT}/~GOOGLE_SYNCED=${parameters.GOOGLE_SYNCED}/~DISCONTINUED_PRODUCT=${parameters.DISCONTINUED_PRODUCT}/~productStoreId=${parameters.productStoreId}</@ofbizUrl>" class="buttontext">${uiLabelMap.CommonPagingOn}</a>
                                <#else>
                                    <a href="<@ofbizUrl>keywordsearch/~VIEW_INDEX=0/~VIEW_SIZE=${previousViewSize}/~clearSearch=N/~PAGING=Y/~noConditionFind=${noConditionFind}</@ofbizUrl>" class="buttontext">${uiLabelMap.CommonPagingOn}</a>
                                </#if>
                            </#if>
                        </b>
                    </td>
                </tr>

                <#assign listIndex = lowIndex>
                <#assign rowClass = "2">
                <#list productIds as productId><#-- note that there is no boundary range because that is being done before the list is put in the content -->
                    <#assign product = delegator.findByPrimaryKey("Product", Static["org.ofbiz.base.util.UtilMisc"].toMap("productId", productId))>
                    <tr <#if rowClass == "1"> class="am-active"</#if>>
                        <td>
                        <#--<input type="checkbox" name="selectResult" value="${productId}" onchange="checkProductToBagTextArea(this, '${productId}');"/>-->
                            <a href="<@ofbizUrl>EditProduct?productId=${productId}</@ofbizUrl>" class="buttontext">[${productId}] ${(product.internalName)?if_exists}</a>
                        </td>
                        <td>

                        </td>
                    </tr>
                <#-- toggle the row color -->
                    <#if rowClass == "2">
                        <#assign rowClass = "1">
                    <#else>
                        <#assign rowClass = "2">
                    </#if>
                </#list>
                <#if !productIds?has_content>
                    <tr>
                        <td colspan="2">
                        ${uiLabelMap.ProductNoResultsFound}
                        </td>
                    </tr>
                </#if>

            </table>
        </form>
    </div>
</div>