<#-- cms menu bar -->
<div id="cmsmenu" style="margin-bottom: 8px;">
<#if (content?has_content)>
    <a href="javascript:void(0);" onclick="callDocument(true, '${content.contentId}', '', 'ELECTRONIC_TEXT');" class="am-btn am-btn-primary am-btn-sm">${uiLabelMap.ContentQuickSubContent}</a>
    <a href="javascript:void(0);" onclick="callPathAlias('${content.contentId}');" class="am-btn am-btn-primary am-btn-sm">${uiLabelMap.ContentPathAlias}</a>
    <a href="javascript:void(0);" onclick="callMetaInfo('${content.contentId}');" class="am-btn am-btn-primary am-btn-sm">${uiLabelMap.ContentMetaTags}</a>
</#if>
</div>

<#if (content?has_content)>
<div class=" am-text-primary">
    New <b>PathAlias</b> attached from WebSite: <b>${webSite.webSiteId}</b> to Content: <b>${content.contentId}</b></b>
</div>
</#if>


<table   class="am-table am-table-radius">
    <tr class="header-row">
        <td>Web Site ID</td>
        <td>Path Alias</td>
        <td>Alias To</td>
        <td>Content ID</td>
        <td>Map Key</td>
        <td>&nbsp;</td>
    </tr>
<#if (aliases?has_content)>
    <#list aliases as alias>
        <tr>
            <td>${alias.webSiteId}</td>
            <td>${alias.pathAlias}</td>
            <td>${alias.aliasTo?default("N/A")}</td>
            <td>${alias.contentId?default("N/A")}</td>
            <td>${alias.mapKey?default("N/A")}</td>
            <td><a href="javascript:void(0);" onclick="pathRemove('${webSiteId}', '${alias.pathAlias}', '${contentId}');" class="buttontext">Remove</a></td>
        </tr>
    </#list>
<#else>
    <tr>
        <td colspan="5">No aliases currently defined.</td>
    </tr>
</#if>
</table>

<form name="cmspathform" method="post" action="<@ofbizUrl>/createWebSitePathAliasJson</@ofbizUrl>" class="am-form">
    <table class="am-table">
        <tr>
            <td colspan="2">&nbsp;</td>
        </tr>
        <tr>
            <td class="label">Web Site:</td>
            <td>${webSite.siteName?default(webSite.webSiteId)}</td>
            <input type="hidden" name="webSiteId" value="${webSiteId}"/>
        </tr>
        <tr>
            <td class="label">Content:</td>
            <td>${content.contentName?default(content.contentId)}</td>
            <input type="hidden" name="contentId" value="${contentId}"/>
        </tr>

        <tr>
            <td class="label">Path Alias:</td>
            <td><input type="text" name="pathAlias" value=""/></td>
        </tr>
        <tr>
            <td class="label">Map Key:</td>
            <td><input type="text" name="mapKey" value=""/></td>
        </tr>
        <tr>
            <td colspan="2" align="center"><input id="submit" type="button" onclick="pathSave('${contentId}');" class="smallSubmit" value="Create"/></td>
        </tr>
    </table>
</form>
