<#include "component://common/webcommon/includes/htmlScreenTemplate.ftl"/>
<div id="partyUserLogins" class="${panelStyle}">
    <div class="${panelHeadingStyle}">
      <div class="${panelHeadingBarStyle}">
        <#if security.hasEntityPermission("PARTYMGR", "_CREATE", session)>
           <a href="<@ofbizUrl>ProfileCreateNewLogin?partyId=${party.partyId}</@ofbizUrl>" class="btn btn-white btn-xs">${uiLabelMap.CommonCreateNew}</a>
        </#if>
        </div>
        <h4 class="${panelTitleStyle}">${uiLabelMap.PartyUserName}</h4>
    </div>
    <div class="${panelBodyStyle}">
      <#if userLogins?has_content>
       <div class="table-responsive">
        <table class="table" cellspacing="0">
          <#list userLogins as userUserLogin>
            <tr>
              <td>${uiLabelMap.PartyUserLogin}</td>
              <td>${userUserLogin.userLoginId}</td>
              <td>
                <#assign enabled = uiLabelMap.PartyEnabled>
                <#if (userUserLogin.enabled)?default("Y") == "N">
                  <#if userUserLogin.disabledDateTime?exists>
                    <#assign disabledTime = userUserLogin.disabledDateTime.toString()>
                  <#else>
                    <#assign disabledTime = "??">
                  </#if>
                  <#assign enabled = uiLabelMap.PartyDisabled + " - " + disabledTime>
                </#if>
                ${enabled}
              </td>
              <td class="button-col">
                <#if security.hasEntityPermission("PARTYMGR", "_CREATE", session)>
                  <a href="<@ofbizUrl>ProfileEditUserLogin?partyId=${party.partyId}&amp;userLoginId=${userUserLogin.userLoginId}</@ofbizUrl>">${uiLabelMap.CommonEdit}</a>
                </#if>
                <#if security.hasEntityPermission("SECURITY", "_VIEW", session)>
                    <#if party.partyTypeId="PARTNER_PERSON">
                      <a href="<@ofbizUrl>ProfileEditUserLoginSecurityGroups?partyId=${party.partyId}&amp;userLoginId=${userUserLogin.userLoginId}</@ofbizUrl>">${uiLabelMap.SecurityGroups}</a>
                    </#if>
                </#if>
              </td>
            </tr>
          </#list>
        </table>
          </div>
      <#else>
        ${uiLabelMap.PartyNoUserLogin}
      </#if>
    </div>
  </div>