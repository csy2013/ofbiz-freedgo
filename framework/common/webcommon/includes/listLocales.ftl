
<div class="panel panel-default">
    <div class="panel-heading">
        <div class="panel-title">
            <h4>${uiLabelMap.WebtoolsLabelManagerAllLocales}</h4>
            <#--<li><a href="<@ofbizUrl>main</@ofbizUrl>">${uiLabelMap.CommonCancel}</a></li>-->
        </div>
    </div>
    <div class="panel-body">
        <div class="table-responsive">
            <table class="table table-borded table-striped">
            <#assign altRow = true>
            <#assign availableLocales = Static["org.ofbiz.base.util.UtilMisc"].availableLocales()/>
            <#--${availableLocales}-->
            <#list availableLocales as availableLocale>
                <#if StringUtil.wrapString(availableLocale) == "zh" ||
                StringUtil.wrapString(availableLocale) == "en" ||
                StringUtil.wrapString(availableLocale) == "fr" ||
                StringUtil.wrapString(availableLocale) == "de" ||
                StringUtil.wrapString(availableLocale) == "it" ||
                StringUtil.wrapString(availableLocale) == "ru" ||
                StringUtil.wrapString(availableLocale) == "ja" ||
                StringUtil.wrapString(availableLocale) == "ro" ||
                StringUtil.wrapString(availableLocale) == "pt" ||
                StringUtil.wrapString(availableLocale) == "nl"
                >
                    <#assign altRow = !altRow>
                    <#assign langAttr = availableLocale.toString()?replace("_", "-")>
                    <#assign langDir = "ltr">
                    <#if "ar.iw"?contains(langAttr?substring(0, 2))>
                        <#assign langDir = "rtl">
                    </#if>
                <#--${availableLocale}-->
                    <tr <#if altRow>class="alternate-row"</#if>>
                        <td lang="${langAttr}" dir="${langDir}">
                            <a href="<@ofbizUrl>setSessionLocale</@ofbizUrl>?newLocale=${availableLocale.toString()}">${availableLocale.getDisplayName(availableLocale)} &nbsp;&nbsp;&nbsp;-&nbsp;&nbsp;&nbsp;
                                [${availableLocale.toString()}]</a>
                        </td>
                    </tr>
                </#if>
            </#list>
            </table>
        </div>
    </div>
</div>
