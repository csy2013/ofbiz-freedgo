<@htmlScreenTemplate.renderScreenletBegin id="" title="${uiLabelMap.PageTitleEditProductStoreWebSites}"/>
    <a href="/content/control/EditWebSite?productStoreId=${productStoreId}&amp;externalLoginKey=${requestAttributes.externalLoginKey}" class="btn btn-primary btn-sm">${uiLabelMap.ProductCreateNewProductStoreWebSite}</a>
<@htmlScreenTemplate.renderModalPage id="AddWebSiteForStore" name="AddWebSiteForStore" modalTitle="为店铺新增站点"  modalUrl="AddWebSiteForStore" description="为店铺新增站点"  targetParameterIter="productStoreId:${productStoreId}"/>
<hr/>
<div class="table-responsive">
    <table class="table table-striped table-bordered">
            <tr class="header-row">
              <td>${uiLabelMap.ProductWebSiteId}</td>
              <td>${uiLabelMap.ProductHost}</td>
              <td>${uiLabelMap.ProductPort}</td>
              <td>&nbsp;</td>
            </tr>
            <#if storeWebSites?has_content>
              <#assign rowClass = "2">
              <#list storeWebSites as webSite>
                <tr valign="middle"<#if rowClass == "1"> class="alternate-row"</#if>>
                  <td><a href="/content/control/EditWebSite?webSiteId=${webSite.webSiteId}&amp;externalLoginKey=${requestAttributes.externalLoginKey}" class="buttontext">${webSite.siteName?if_exists} [${webSite.webSiteId}]</a></td>
                  <td>${webSite.httpHost?default('&nbsp;')}</td>
                  <td>${webSite.httpPort?default('&nbsp;')}</td>
                  <td align="center">
                      <@htmlScreenTemplate.renderConfirmField id="storeUpdateWebSite_${webSite_index}" description="删除"
                      name="storeUpdateWebSite_${webSite_index}"  confirmUrl="storeUpdateWebSite"  confirmMessage="确定删除该记录?" confirmTitle="特征店铺对应网站"
                      targetParameterIter="viewProductStoreId:'${(productStoreId)?if_exists}',productStoreId:'',webSiteId:'${(webSite.webSiteId)?if_exists}'" />

                      <#--<a href="javascript:document.storeUpdateWebSite_${webSite_index}.submit();" class="buttontext">${uiLabelMap.CommonDelete}</a>-->
                   <#-- <form name="storeUpdateWebSite_${webSite_index}" method="post" action="<@ofbizUrl>storeUpdateWebSite</@ofbizUrl>">
                        <input type="hidden" name="viewProductStoreId" value="${productStoreId}"/>
                        <input type="hidden" name="productStoreId" value=""/>
                        <input type="hidden" name="webSiteId" value="${webSite.webSiteId}"/>
                    </form>-->
                  </td>
                </tr>
                <#-- toggle the row color -->
                <#if rowClass == "2">
                    <#assign rowClass = "1">
                <#else>
                    <#assign rowClass = "2">
                </#if>
              </#list>
            </#if>
        </table>
    </div>
<@htmlScreenTemplate.renderScreenletEnd/>


