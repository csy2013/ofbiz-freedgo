<#include "component://common/webcommon/includes/htmlScreenTemplate.ftl"/>

  <div id="partyNotes" class="${panelStyle}">
    <div class="${panelHeadingStyle}">
      <div class="${panelHeadingBarStyle}">

      <#if security.hasEntityPermission("PARTYMGR", "_NOTE", session)>
           <a href="<@ofbizUrl>AddPartyNote?partyId=${partyId}</@ofbizUrl>" class="btn btn-white btn-xs">${uiLabelMap.CommonCreateNew}</a>
        </#if>
      </div>
        <h4 class="${panelTitleStyle}">${uiLabelMap.CommonNotes}</h4>
    </div>
    <div class="${panelBodyStyle}">
      <#if notes?has_content>
          <div class="table-responsive">
        <table class="table">
          <#list notes as noteRef>
            <tr>
              <td>
                <div><b>${uiLabelMap.FormFieldTitle_noteName}: </b>${noteRef.noteName?if_exists}</div>
                <#if noteRef.noteParty?has_content>
                  <div><b>${uiLabelMap.CommonBy}: </b>${Static["org.ofbiz.party.party.PartyHelper"].getPartyName(delegator, noteRef.noteParty, true)}</div>
                </#if>
                <div><b>${uiLabelMap.CommonAt}: </b><#if noteRef.noteDateTime??>${noteRef.noteDateTime?string('yyyy-MM-dd HH:mm')}</#if></div>
              </td>
              <td>
                ${noteRef.noteInfo}
              </td>
            </tr>

          </#list>
        </table>
          </div>
      <#else>
        ${uiLabelMap.PartyNoNotesForParty}
      </#if>
    </div>
  </div>
