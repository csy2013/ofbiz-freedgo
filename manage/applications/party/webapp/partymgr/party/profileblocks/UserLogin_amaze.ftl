<div class="am-cf am-padding-xs">
<div id="partyUserLogins" class="am-panel am-panel-default">

        <div class="am-panel-hd am-cf">
            <div class="am-u-lg-8">${uiLabelMap.PartyUserName}</div>
        <#if security.hasEntityPermission("PARTYMGR", "_CREATE", session)>
            <div  class="am-u-lg-2"> <a href="<@ofbizUrl>ProfileCreateNewLogin?partyId=${party.partyId}</@ofbizUrl>">${uiLabelMap.CommonCreateNew}</a></div>
        </#if>
        </div>
      <br class="clear" />
    <div class="am-panel-bd am-collapse am-in">
      <#if userLogins?has_content>
        <table  cellspacing="0" style="border-collapse: separate;border-spacing: 0;margin-bottom: 1.6rem;width: 100%;">
          <#list userLogins as userUserLogin>
            <tr>
              <td class="label">${uiLabelMap.PartyUserLogin}</td>
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
                  <a class="am-btn am-btn-primary am-btn-sm" href="<@ofbizUrl>ProfileEditUserLogin?partyId=${party.partyId}&amp;userLoginId=${userUserLogin.userLoginId}</@ofbizUrl>">${uiLabelMap.CommonEdit}</a>
                </#if>
                <#if security.hasEntityPermission("SECURITY", "_VIEW", session)>
                  <a class="am-btn am-btn-primary am-btn-sm" href="<@ofbizUrl>ProfileEditUserLoginSecurityGroups?partyId=${party.partyId}&amp;userLoginId=${userUserLogin.userLoginId}</@ofbizUrl>">${uiLabelMap.SecurityGroups}</a>
                </#if>
              </td>
            </tr>
          </#list>
        </table>
      <#else>
        ${uiLabelMap.PartyNoUserLogin}
      </#if>
    </div>
  </div>
</div>