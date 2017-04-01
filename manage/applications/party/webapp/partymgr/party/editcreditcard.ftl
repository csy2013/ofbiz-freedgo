<#if !creditCard?exists>
<form method="post" action="<@ofbizUrl>createCreditCard?DONE_PAGE=${donePage}</@ofbizUrl>" name="editcreditcardform" class="form-horizontal">
<#else>
<form method="post" action="<@ofbizUrl>updateCreditCard?DONE_PAGE=${donePage}</@ofbizUrl>" name="editcreditcardform" class="form-horizontal">
    <input type="hidden" name="paymentMethodId" value="${paymentMethodId}"/>
</#if>
    <input type="hidden" name="partyId" value="${partyId}"/>
${screens.render("component://accounting/widget/CommonScreens.xml#creditCardFields")}
<div class="form-group">
   <#-- <label class="control-label">${uiLabelMap.AccountingBillingAddress}</label>
    <div class="col-md-6">&nbsp;</div>-->

<#-- Removed because is confusing, can add but would have to come back here with all data populated as before...
<a href="<@ofbizUrl>editcontactmech</@ofbizUrl>" class="smallSubmit">
  [Create New Address]</a>&nbsp;&nbsp;
-->

<#assign hasCurrent = false>
<#if curPostalAddress?has_content>
    <#assign hasCurrent = true>
    <div class="form-group">
        <div class="col-md-4">
            <input type="radio" name="contactMechId" class="form-control" value="${curContactMechId}" checked="checked"/>
        </div>
        <div class="col-md-6">
            <p><b>${uiLabelMap.PartyUseCurrentAddress}:</b></p>
            <#list curPartyContactMechPurposes as curPartyContactMechPurpose>
                <#assign curContactMechPurposeType = curPartyContactMechPurpose.getRelatedOneCache("ContactMechPurposeType")>
                <p>
                    <b>${curContactMechPurposeType.get("description",locale)?if_exists}</b>
                    <#if curPartyContactMechPurpose.thruDate?exists>
                        (${uiLabelMap.CommonExpire}:${curPartyContactMechPurpose.thruDate.toString()})
                    </#if>
                </p>
            </#list>
            <#if curPostalAddress.toName?exists><p><b>${uiLabelMap.CommonTo}:</b> ${curPostalAddress.toName}</p></#if>
            <#if curPostalAddress.attnName?exists><p><b>${uiLabelMap.PartyAddrAttnName}:</b> ${curPostalAddress.attnName}</p></#if>
            <#if curPostalAddress.address1?exists><p>${curPostalAddress.address1}</p></#if>
            <#if curPostalAddress.address2?exists><p>${curPostalAddress.address2}</p></#if>
            <p>${curPostalAddress.city?if_exists}<#if curPostalAddress.stateProvinceGeoId?has_content>,&nbsp;${curPostalAddress.stateProvinceGeoId?if_exists}</#if>
                &nbsp;${curPostalAddress.postalCode?if_exists}</p>
            <#if curPostalAddress.countryGeoId?exists><p>${curPostalAddress.countryGeoId}</p></#if>
            <p>(${uiLabelMap.CommonUpdated}:&nbsp;${(curPartyContactMech.fromDate.toString())?if_exists})</p>
            <#if curPartyContactMech.thruDate?exists><p><b>${uiLabelMap.CommonDelete}:&nbsp;${curPartyContactMech.thruDate.toString()}</b></p></#if>
        </div>
    </div>
<#else>
<#-- <div class="form-group">
 <div class="col-md-6 valign="top" colspan="2">
   ${uiLabelMap.PartyBillingAddressNotSelected}
 </td>
</tr> -->
</#if>
<#-- is confusing
<div class="form-group">
  <div class="col-md-6 valign="top" colspan="2">
    <b>Select a New Billing Address:</b>
  </td>
</tr>
-->
<#list postalAddressInfos as postalAddressInfo>
    <#assign contactMech = postalAddressInfo.contactMech>
    <#assign partyContactMechPurposes = postalAddressInfo.partyContactMechPurposes>
    <#assign postalAddress = postalAddressInfo.postalAddress>
    <#assign partyContactMech = postalAddressInfo.partyContactMech>
    <div class="form-group">
        <div class="col-md-4">
            <input type="radio" name="contactMechId" value="${contactMech.contactMechId}"/>
        </div>
        <div class="col-md-6">
            <#list partyContactMechPurposes as partyContactMechPurpose>
                <#assign contactMechPurposeType = partyContactMechPurpose.getRelatedOneCache("ContactMechPurposeType")>
                <p>
                    <b>${contactMechPurposeType.get("description",locale)?if_exists}</b>
                    <#if partyContactMechPurpose.thruDate?exists>(${uiLabelMap.CommonExpire}:${partyContactMechPurpose.thruDate})</#if>
                </p>
            </#list>
            <#if postalAddress.toName?exists><p><b>${uiLabelMap.CommonTo}:</b> ${postalAddress.toName}</p></#if>
            <#if postalAddress.attnName?exists><p><b>${uiLabelMap.PartyAddrAttnName}:</b> ${postalAddress.attnName}</p></#if>
            <#if postalAddress.address1?exists><p>${postalAddress.address1}</p></#if>
            <#if postalAddress.address2?exists><p>${postalAddress.address2}</p></#if>
            <p>${postalAddress.city}<#if postalAddress.stateProvinceGeoId?has_content>,&nbsp;${postalAddress.stateProvinceGeoId}</#if>
                &nbsp;${postalAddress.postalCode?if_exists}</p>
            <#if postalAddress.countryGeoId?exists><p>${postalAddress.countryGeoId}</p></#if>
            <p>(${uiLabelMap.CommonUpdated}:&nbsp;${(partyContactMech.fromDate.toString())?if_exists})</p>
            <#if partyContactMech.thruDate?exists><p><b>${uiLabelMap.CommonDelete}:&nbsp;${partyContactMech.thruDate.toString()}</b></p></#if>
        </div>
    </div>
</#list>
<#if !postalAddressInfos?has_content && !curContactMech?exists>
    <div class="form-group">
        <div class="col-md-6">${uiLabelMap.PartyNoContactInformation}.</td>
    </div>
</#if>
<#-- not yet supported in party manager
<div class="form-group">
  <div class="col-md-6 align="right" valigh="top" width="1%">
    <input type="radio" name="contactMechId" value="_NEW_" <#if !hasCurrent>checked="checked"</#if> />
  </td>
  <div class="col-md-6 valign="middle" width="80%">
    ${uiLabelMap.PartyCreateNewBillingAddress}.
  </td>
</tr>
-->
    <div class="form-group">
        <div class="col-md-4">&nbsp;</div>
        <div class="col-md-6"> <input type="submit" class="btn btn-primary btn-sm">${uiLabelMap.CommonSave}</input></div>
    </div>

</form>
