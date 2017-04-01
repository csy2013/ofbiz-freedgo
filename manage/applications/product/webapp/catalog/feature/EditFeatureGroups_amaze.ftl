<div class="am-cf am-padding-xs">
    <div class="am-panel am-panel-default">
        <div class="am-panel-hd am-cf">${uiLabelMap.PageTitleEditProductFeatureGroups}</div>
        <div class="am-panel-bd am-collapse am-in">
            <div class="am-g">
                <div class="am-u-lg-12">
                    <table class="am-table am-table-striped am-table-hover table-main">
                        <tr>
                            <td><b>${uiLabelMap.CommonId}</b></td>
                            <td><b>&nbsp;&nbsp;&nbsp;&nbsp;${uiLabelMap.CommonDescription}</b></td>
                            <td><b>&nbsp;</b></td>
                            <td><b>&nbsp;</b></td>
                        </tr>
                    <#assign rowClass = "2">
                    <#list productFeatureGroups as productFeatureGroup>
                        <tr>
                            <td>
                                <a href='<@ofbizUrl>EditFeatureGroupAppls?productFeatureGroupId=${productFeatureGroup.productFeatureGroupId}</@ofbizUrl>'
                                   class="buttontext">${productFeatureGroup.productFeatureGroupId}</a></td>
                            <td>
                                <form method='post' action='<@ofbizUrl>UpdateProductFeatureGroup</@ofbizUrl>'
                                      class="am-form am-form-horizontal">
                                    <input type='hidden' name="productFeatureGroupId"
                                           value="${productFeatureGroup.productFeatureGroupId}"/>

                                    <div class="am-u-sm-3">
                                        <input type='text' size='30' name="description" class="am-form-field am-u-sm"
                                               value="${productFeatureGroup.description?if_exists}"/>
                                    </div>
                                    <input type="submit" class="am-btn am-btn-primary am-btn-sm"
                                           value="${uiLabelMap.CommonUpdate}"/>
                                </form>
                            </td>
                            <td><a class="am-btn am-btn-primary am-btn-sm"
                                   href='<@ofbizUrl>EditFeatureGroupAppls?productFeatureGroupId=${productFeatureGroup.productFeatureGroupId}</@ofbizUrl>'
                                   class="buttontext">${uiLabelMap.ProductFeatureGroupAppls}</a></td>
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
            </div>
        </div>
    </div>
</div>


<div class="am-cf am-padding-xs">
    <div class="am-panel am-panel-default">
        <div class="am-panel-hd am-cf">${uiLabelMap.ProductCreateProductFeatureGroup}</div>
        <div class="am-panel-bd am-collapse am-in">
            <div class="am-g">
                <div class="am-u-lg-12">
                    <form method="post" action="<@ofbizUrl>CreateProductFeatureGroup</@ofbizUrl>" class="am-form am-form-horizontal">
                        <div class="am-form-group am-g">
                            <label class="am-control-label am-u-md-2 am-u-lg-2">${uiLabelMap.CommonDescription}:</label>
                            <div class="am-u-md-2 am-u-lg-2 am-u-end">
                                <input type="text" size='30' name='description' value='' />
                            </div>
                            <input type="submit" class="am-btn am-btn-primary am-btn-sm" value="${uiLabelMap.CommonCreate}"/>
                        </div>
                    </form>
                </div>
            </div>
        </div>
    </div>
</div>
