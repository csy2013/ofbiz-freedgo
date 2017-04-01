<form name="addWebSite" action="<@ofbizUrl>storeUpdateWebSite</@ofbizUrl>" method="post" class="form-inline">
    <input type="hidden" name="viewProductStoreId" value="${productStoreId}" />
    <input type="hidden" name="productStoreId" value="${productStoreId}" />
    <div class="form-group">
    <div class="input-group">
    <select name="webSiteId" class="form-control">
    <#list webSites as webSite>
        <option value="${webSite.webSiteId}">${webSite.siteName?if_exists} [${webSite.webSiteId}]</option>
    </#list>
    </select>
        <input type="submit" class="btn btn-primary btn-sm" value="${uiLabelMap.CommonUpdate}" />
    </div>
    </div>
</form>