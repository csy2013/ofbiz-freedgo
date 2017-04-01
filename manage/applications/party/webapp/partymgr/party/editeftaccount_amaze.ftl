

<!-- begin editeftaccount.ftl -->
 <div class="am-cf am-padding-xs">
    <div id="screenlet_1" class="am-panel am-panel-default">
      <div class="am-panel-hd am-cf">
    <#if !eftAccount?exists>
      ${uiLabelMap.AccountingAddNewEftAccount}
    <#else>
      ${uiLabelMap.PageTitleEditEftAccount}
    </#if>
  </div>
  <div class="am-panel-bd am-collapse am-in">
   <div class="am-g am-center">
      <div class="am-u-lg-10">
    <#if !eftAccount?exists>
      <form method="post" class="am-form am-form-horizontal" action='<@ofbizUrl>createEftAccount?DONE_PAGE=${donePage}</@ofbizUrl>' name="editeftaccountform" style='margin: 0;'>
    <#else>
      <form method="post" action='<@ofbizUrl>updateEftAccount?DONE_PAGE=${donePage}</@ofbizUrl>' name="editeftaccountform" style='margin: 0;'>
        <input type="hidden" name='paymentMethodId' value='${paymentMethodId}' />
    </#if>
        <input type="hidden" name="partyId" value="${partyId}"/>
        <div class="am-form-group am-g">
            <label for="nameOnAccount"  class="am-control-label am-u-md-5 am-u-lg-5">${uiLabelMap.AccountingNameAccount}</label>
            <div class="am-u-md-7 am-u-lg-7 am-u-end">
                <div class="am-input-group">
                <input type="text" class='required am-form-field am-input-sm' size="30" maxlength="60" name="nameOnAccount" value="${eftAccountData.nameOnAccount?if_exists}" />
                <span class="tooltip">${uiLabelMap.CommonRequired}</span>
                </div>
            </div>
        </div>
        <div class="am-form-group am-g">
            <label for="companyNameOnAccount"  class="am-control-label am-u-md-5 am-u-lg-5">${uiLabelMap.AccountingCompanyNameAccount}</label>
            <div class="am-u-md-7 am-u-lg-7 am-u-end">
                <div class="am-input-group">
                  <input class="am-form-field am-input-sm" type="text" size="30" maxlength="60" name="companyNameOnAccount" value="${eftAccountData.companyNameOnAccount?if_exists}" />
                </div>
            </div>
        </div>
        <div class="am-form-group am-g">
            <label for="bankName"  class="am-control-label am-u-md-5 am-u-lg-5">${uiLabelMap.AccountingBankName}</label>
            <div class="am-u-md-7 am-u-lg-7 am-u-end">
                <div class="am-input-group">
                  <input  type="text" class='required am-form-field am-input-sm' size="30" maxlength="60" name="bankName" value="${eftAccountData.bankName?if_exists}" />
                  <span class="tooltip">${uiLabelMap.CommonRequired}</span>
                </div>
            </div>
        </div>
        <div class="am-form-group am-g">
            <label for="routingNumber"  class="am-control-label am-u-md-5 am-u-lg-5">${uiLabelMap.AccountingRoutingNumber}</label>
            <div class="am-u-md-7 am-u-lg-7 am-u-end">
                <div class="am-input-group">
                  <input  type="text" class='required am-form-field am-input-sm' size="10" maxlength="30" name="routingNumber" value="${eftAccountData.routingNumber?if_exists}" />
                  <span class="tooltip">${uiLabelMap.CommonRequired}</span>
                </div>
            </div>
        </div>
        <div class="am-form-group am-g">
            <label for="accountType"  class="am-control-label am-u-md-5 am-u-lg-5">${uiLabelMap.AccountingAccountType}</label>
            <div class="am-u-md-7 am-u-lg-7 am-u-end">
                <div class="am-input-group">
                  <select name="accountType" class='required'>
                    <option>${eftAccountData.accountType?if_exists}</option>
                    <option></option>
                    <option>${uiLabelMap.CommonChecking}</option>
                    <option>${uiLabelMap.CommonSavings}</option>
                  </select>
                  <span class="tooltip">${uiLabelMap.CommonRequired}</span>
                 </div>
           </div>
        </div>
        <div class="am-form-group am-g">
            <label for="accountType"  class="am-control-label am-u-md-5 am-u-lg-5">${uiLabelMap.AccountingAccountNumber}</label>
            <div class="am-u-md-7 am-u-lg-7 am-u-end">
                <div class="am-input-group">
                  <input type="text" class='required am-form-field am-input-sm' size="20" maxlength="40" name="accountNumber" value="${eftAccountData.accountNumber?if_exists}" />
                  <span class="tooltip">${uiLabelMap.CommonRequired}</span>
               </div>
            </div>
        </div>
        <div class="am-form-group am-g">
            <label for="accountType"  class="am-control-label am-u-md-5 am-u-lg-5">${uiLabelMap.CommonDescription}</label>
            <div class="am-u-md-7 am-u-lg-7 am-u-end">
                <div class="am-input-group">
                  <input type="text" class='required am-form-field am-input-sm' size="30" maxlength="60" name="description" value="${paymentMethodData.description?if_exists}" />
                  <span class="tooltip">${uiLabelMap.CommonRequired}</span>
                </div>
            </div>
        </div>
        <div class="am-form-group am-g">
            <label for="accountType"  class="am-control-label am-u-md-5 am-u-lg-5">${uiLabelMap.PartyBillingAddress}</label>
            <div class="am-u-md-7 am-u-lg-7 am-u-end">
                <div class="am-input-group">
            <#-- Removed because is confusing, can add but would have to come back here with all data populated as before...
            <a href="<@ofbizUrl>editcontactmech</@ofbizUrl>" class="smallSubmit">
              [Create New Address]</a>&nbsp;&nbsp;
            -->
            <table cellspacing="0">
            <#if curPostalAddress?exists>
              <tr>
                <td class="button-col">
                  <input type="radio" name="contactMechId" value="${curContactMechId}" checked="checked" />
                </td>
                <td>
                  <p><b>${uiLabelMap.PartyUseCurrentAddress}:</b></p>
                  <#list curPartyContactMechPurposes as curPartyContactMechPurpose>
                    <#assign curContactMechPurposeType = curPartyContactMechPurpose.getRelatedOneCache("ContactMechPurposeType")>
                    <p><b>${curContactMechPurposeType.get("description",locale)?if_exists}</b></p>
                    <#if curPartyContactMechPurpose.thruDate?exists>
                      <p>(${uiLabelMap.CommonExpire}:${curPartyContactMechPurpose.thruDate.toString()})</p>
                    </#if>
                  </#list>
                  <#if curPostalAddress.toName?exists><p><b>${uiLabelMap.CommonTo}:</b> ${curPostalAddress.toName}</p></#if>
                  <#if curPostalAddress.attnName?exists><p><b>${uiLabelMap.PartyAddrAttnName}:</b> ${curPostalAddress.attnName}</p></#if>
                  <#if curPostalAddress.address1?exists><p>${curPostalAddress.address1}</p></#if>
                  <#if curPostalAddress.address2?exists><p>${curPostalAddress.address2}</p></#if>
                  <p>${curPostalAddress.city}<#if curPostalAddress.stateProvinceGeoId?has_content>,&nbsp;${curPostalAddress.stateProvinceGeoId}</#if>&nbsp;${curPostalAddress.postalCode}</p>
                  <#if curPostalAddress.countryGeoId?exists><p>${curPostalAddress.countryGeoId}</p></#if>
                  <p>(${uiLabelMap.CommonUpdated}:&nbsp;${(curPartyContactMech.fromDate.toString())?if_exists})</p>
                  <#if curPartyContactMech.thruDate?exists><p><b>${uiLabelMap.CommonDelete}:&nbsp;${curPartyContactMech.thruDate.toString()}</b></p></#if>
                </td>
              </tr>
            <#else>
               <#-- <tr>
                <td valign="top" colspan='2'>
                  ${uiLabelMap.PartyNoBillingAddress}</div>
                </td>
              </tr> -->
            </#if>
              <#-- is confusing
              <tr>
                <td valign="top" colspan='2'>
                  <b>Select a New Billing Address:</b></div>
                </td>
              </tr>
              -->
              <#list postalAddressInfos as postalAddressInfo>
                <#assign contactMech = postalAddressInfo.contactMech>
                <#assign partyContactMechPurposes = postalAddressInfo.partyContactMechPurposes>
                <#assign postalAddress = postalAddressInfo.postalAddress>
                <#assign partyContactMech = postalAddressInfo.partyContactMech>
                <tr>
                  <td class="button-col">
                    <input type='radio' name='contactMechId' value='${contactMech.contactMechId}' />
                  </td>
                  <td>
                    <#list partyContactMechPurposes as partyContactMechPurpose>
                      <#assign contactMechPurposeType = partyContactMechPurpose.getRelatedOneCache("ContactMechPurposeType")>
                      <p><b>${contactMechPurposeType.get("description",locale)?if_exists}</b></p>
                      <#if partyContactMechPurpose.thruDate?exists><p>(${uiLabelMap.CommonExpire}:${partyContactMechPurpose.thruDate})</p></#if>
                    </#list>
                    <#if postalAddress.toName?exists><p><b>${uiLabelMap.CommonTo}:</b> ${postalAddress.toName}</p></#if>
                    <#if postalAddress.attnName?exists><p><b>${uiLabelMap.PartyAddrAttnName}:</b> ${postalAddress.attnName}</p></#if>
                    <#if postalAddress.address1?exists><p>${postalAddress.address1}</p></#if>
                    <#if postalAddress.address2?exists><p>${postalAddress.address2}</p></#if>
                    <p>${postalAddress.city}<#if postalAddress.stateProvinceGeoId?has_content>,&nbsp;${postalAddress.stateProvinceGeoId}</#if>&nbsp;${postalAddress.postalCode}</p>
                    <#if postalAddress.countryGeoId?exists><p>${postalAddress.countryGeoId}</p></#if>
                    <p>(${uiLabelMap.CommonUpdated}:&nbsp;${(partyContactMech.fromDate.toString())?if_exists})</p>
                    <#if partyContactMech.thruDate?exists><p><b>${uiLabelMap.CommonDelete}:&nbsp;${partyContactMech.thruDate.toString()}</b></p></#if>
                  </td>
                </tr>
              </#list>
              <#if !postalAddressInfos?has_content && !curContactMech?exists>
                  <tr><td colspan='2'>${uiLabelMap.PartyNoContactInformation}.</td></tr>
              </#if>
            </table>
          </div>
         </div>
        </div>

      </form>
      <div class="am-form-group am-g">
          <div class="am-control-label am-u-md-3 am-u-lg-3"></div>
          <div class="am-control-label am-u-md-7 am-u-lg-7">
            <a href="javascript:document.editeftaccountform.submit()" class="am-btn am-btn-primary am-btn-sm">${uiLabelMap.CommonSave}</a>
           </div>
          </div>
      </div>
      </div>
       </div>
  </div>
</div>
</div>
  </div>
<!-- end editeftaccount.ftl -->