<#include "component://common/webcommon/includes/htmlScreenTemplate.ftl"/>
  <div id="partyVisits" class="${panelStyle}">
      <div class="${panelHeadingStyle}">
        <div class="${panelHeadingBarStyle}">
          <a href="<@ofbizUrl>findVisits?partyId=${partyId}</@ofbizUrl>" class="btn btn-white btn-xs">${uiLabelMap.CommonListAll}</a>
        </div>
          <h4 class="${panelTitleStyle}">${uiLabelMap.PartyVisits}</h4>
      </div>
    <div class="${panelBodyStyle}">
      <#if visits?has_content>
        <div class="table-responsive">
        <table class="table" cellspacing="0">
            <thead>
          <tr class="header-row">
            <th>${uiLabelMap.PartyVisitId}</th>
            <th>${uiLabelMap.PartyUserLogin}</th>
            <th>${uiLabelMap.PartyNewUser}</th>
            <th>${uiLabelMap.PartyWebApp}</th>
            <th>${uiLabelMap.PartyClientIP}</th>
            <th>${uiLabelMap.CommonFromDate}</th>
            <th>${uiLabelMap.CommonThruDate}</th>
          </tr>
            </thead>
          <#list visits as visitObj>
            <#if (visitObj_index > 4)><#break></#if>
              <tr>
                <td class="button-col">
                  <a href="<@ofbizUrl>visitdetail?visitId=${visitObj.visitId?if_exists}</@ofbizUrl>">${visitObj.visitId?if_exists}</a>
                </td>
                <td>${visitObj.userLoginId?if_exists}</td>
                <td>${visitObj.userCreated?if_exists}</td>
                <td>${visitObj.webappName?if_exists}</td>
                <td>${visitObj.clientIpAddress?if_exists}</td>
                <td><#if visitObj.fromDate??>${visitObj.fromDate?string('yyyy-MM-dd HH:mm')}</#if></td>
                <td><#if visitObj.thruDate??>${visitObj.thruDate?string('yyyy-MM-dd HH:mm')}</#if></td>
              </tr>
          </#list>
        </table>
        </div>
      <#else>
        ${uiLabelMap.PartyNoVisitFound}
      </#if>
    </div>
  </div>