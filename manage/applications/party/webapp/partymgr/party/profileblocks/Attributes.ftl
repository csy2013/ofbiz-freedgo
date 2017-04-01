<#include "component://common/webcommon/includes/htmlScreenTemplate.ftl"/>
<div id="partyAttributes" class="${panelStyle}">
    <div class="${panelHeadingStyle}">
        <div class="${panelHeadingBarStyle}">
        <#if security.hasEntityPermission("PARTYMGR", "_CREATE", session)>
            <a href="<@ofbizUrl>editPartyAttribute?partyId=${party.partyId?if_exists}</@ofbizUrl>" class="btn btn-white btn-xs">${uiLabelMap.CommonCreateNew}</a>
        </#if>
        </div>
        <h5 class="${panelTitleStyle}">${uiLabelMap.PartyAttributes}</h5>
    </div>
    <div class="${panelBodyStyle}">
    <#if attributes?has_content>
        <div class="table-responsive">
            <table class="table table-bordered table-striped" cellspacing="0">
                <tr class="header-row">
                    <td>${uiLabelMap.CommonName}</td>
                    <td>${uiLabelMap.CommonValue}</td>
                    <td>&nbsp;</td>
                </tr>
                <#assign alt_row = false>
                <#list attributes as attr>
                    <tr valign="middle"<#if alt_row> class="alternate-row"</#if>>
                        <td>
                        ${attr.attrName?if_exists}
                        </td>
                        <td>
                        ${attr.attrValue?if_exists}
                        </td>
                        <td class="button-col">
                            <a href="<@ofbizUrl>editPartyAttribute?partyId=${partyId?if_exists}&attrName=${attr.attrName?if_exists}</@ofbizUrl>">${uiLabelMap.CommonEdit}</a>
                        </td>
                    </tr>
                <#-- toggle the row color -->
                    <#assign alt_row = !alt_row>
                </#list>
            </table>
        </div>
    <#else>
    ${uiLabelMap.PartyNoPartyAttributesFound}
    </#if>
    </div>
</div>