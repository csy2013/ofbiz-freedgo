<div class="am-cf am-padding-xs" xmlns="http://www.w3.org/1999/html">
<#if !mechMap.contactMech?exists>
  <#-- When creating a new contact mech, first select the type, then actually create -->
  <#if !preContactMechTypeId?has_content>
  <div id="screenlet_1" class="am-panel am-panel-default">
   <div class="am-panel-hd am-cf">${uiLabelMap.PartyCreateNewContact}</div>
  </div>
    <form method="post" action="<@ofbizUrl>editcontactmech</@ofbizUrl>" name="createcontactmechform" >
      <input type="hidden" name="partyId" value="${partyId}" />
      <table class="basic-table" cellspacing="0">
        <tr>
          <td class="label">${uiLabelMap.PartySelectContactType}</td>
          <td>
            <select name="preContactMechTypeId">
              <#list mechMap.contactMechTypes as contactMechType>
                <option value="${contactMechType.contactMechTypeId}">${contactMechType.get("description",locale)}</option>
              </#list>
            </select>
            <a href="javascript:document.createcontactmechform.submit()" class="am-btn am-btn-primary am-btn-sm">${uiLabelMap.CommonCreate}</a>
          </td>
        </tr>
      </table>
    </form>
    </#if>
</#if>
</div>
<div class="am-cf am-padding-xs">
<#if mechMap.contactMechTypeId?has_content>
  <#if !mechMap.contactMech?has_content>
    <div id="screenlet_1" class="am-panel am-panel-default">
      <div class="am-panel-hd am-cf">${uiLabelMap.PartyCreateNewContact}</div>
    </div>
    <div id="mech-purpose-types">
    <#if contactMechPurposeType?exists>
      <p>(${uiLabelMap.PartyMsgContactHavePurpose} <b>"${contactMechPurposeType.get("description",locale)?if_exists}"</b>)</p>
    </#if>
    <table class="basic-table" cellspacing="0">
      <form method="post" action="<@ofbizUrl>${mechMap.requestName}</@ofbizUrl>" name="editcontactmechform" id="editcontactmechform">
        <input type="hidden" name="DONE_PAGE" value="${donePage}" />
        <input type="hidden" name="contactMechTypeId" value="${mechMap.contactMechTypeId}" />
        <input type="hidden" name="partyId" value="${partyId}" />
        <#if cmNewPurposeTypeId?has_content><input type="hidden" name="contactMechPurposeTypeId" value="${cmNewPurposeTypeId}" /></#if>
        <#if preContactMechTypeId?exists><input type="hidden" name="preContactMechTypeId" value="${preContactMechTypeId}" /></#if>
        <#if contactMechPurposeTypeId?exists><input type="hidden" name="contactMechPurposeTypeId" value="${contactMechPurposeTypeId?if_exists}" /></#if>
        <#if paymentMethodId?has_content><input type='hidden' name='paymentMethodId' value='${paymentMethodId}' /></#if>
  <#else>
  <div id="screenlet_1" class="am-panel am-panel-default">
   <div class="am-panel-hd am-cf">${uiLabelMap.PartyEditContactInformation}</div>
  </div>
  <div id="screenlet_1_col" class="am-panel-bd am-collapse am-in">
    <div class="am-g am-center">
     <div class="am-form-group am-g">
      <#if mechMap.purposeTypes?has_content>
      <div class="am-form-group am-g">
          <div class="am-control-label am-u-md-3 am-u-lg-3"><h2 align="right">${uiLabelMap.PartyContactPurposes}</h2></div>
          <div class="am-control-label am-u-md-8 am-u-lg-8">
              <#if mechMap.partyContactMechPurposes?has_content>
                <#list mechMap.partyContactMechPurposes as partyContactMechPurpose>
                  <#assign contactMechPurposeType = partyContactMechPurpose.getRelatedOneCache("ContactMechPurposeType")>

                    <div class="am-control-label am-u-md-5 am-u-lg-5">
                      <#if contactMechPurposeType?has_content>
                        ${contactMechPurposeType.get("description",locale)}
                      <#else>
                        ${uiLabelMap.PartyPurposeTypeNotFound}: "${partyContactMechPurpose.contactMechPurposeTypeId}"
                      </#if>
                      (${uiLabelMap.CommonSince}:${partyContactMechPurpose.fromDate.toString()})
                      <#if partyContactMechPurpose.thruDate?has_content>(${uiLabelMap.CommonExpire}: ${partyContactMechPurpose.thruDate.toString()}</#if>
                    </div>
                    <div class="am-u-md-7 am-u-lg-7 am-u-end">
                      <form name="deletePartyContactMechPurpose_${partyContactMechPurpose.contactMechPurposeTypeId}" method="post" action="<@ofbizUrl>deletePartyContactMechPurpose</@ofbizUrl>" >
                         <input type="hidden" name="partyId" value="${partyId}" />
                         <input type="hidden" name="contactMechId" value="${contactMechId}" />
                         <input type="hidden" name="contactMechPurposeTypeId" value="${partyContactMechPurpose.contactMechPurposeTypeId}" />
                         <input type="hidden" name="fromDate" value="${partyContactMechPurpose.fromDate.toString()}" />
                         <input type="hidden" name="DONE_PAGE" value="${donePage?replace("=","%3d")}" />
                         <input type="hidden" name="useValues" value="true" />
                         <a class="am-btn am-btn-primary am-btn-sm" href="javascript:document.deletePartyContactMechPurpose_${partyContactMechPurpose.contactMechPurposeTypeId}.submit()" class="buttontext">${uiLabelMap.CommonDelete}</a>
                       <br/><br/></form>
                    </div>

                </#list>
              </#if>
              <div class="am-form-group am-g">
                <form method="post" action="<@ofbizUrl>createPartyContactMechPurpose</@ofbizUrl>" name="newpurposeform" class="am-form am-form-horizontal">
                  <input type="hidden" name="partyId" value="${partyId}" />
                  <input type="hidden" name="DONE_PAGE" value="${donePage}" />
                  <input type="hidden" name="useValues" value="true" />
                  <input type="hidden" name="contactMechId" value="${contactMechId?if_exists}" />
                   <div class="am-control-label am-u-md-5 am-u-lg-5">
                    <select name="contactMechPurposeTypeId">
                      <option></option>
                      <#list mechMap.purposeTypes as contactMechPurposeType>
                        <option value="${contactMechPurposeType.contactMechPurposeTypeId}">${contactMechPurposeType.get("description",locale)}</option>
                      </#list>
                    </select>
                  </div>
                </form>
                  <div class="am-u-md-7 am-u-lg-7 am-u-end"><a href="javascript:document.newpurposeform.submit()" class="am-btn am-btn-primary am-btn-sm">${uiLabelMap.PartyAddPurpose}</a></div>
              </div>
              </div>
      </div>
      </#if>

  <div class="am-form-group am-g">
      <div class="am-control-label am-u-md-3 am-u-lg-3"></div>
     <div class="am-control-label am-u-md-9 am-u-lg-9">
      <form method="post" action="<@ofbizUrl>${mechMap.requestName}</@ofbizUrl>" name="editcontactmechform" id="editcontactmechform" class="am-form am-form-horizontal">
        <input type="hidden" name="contactMechId" value="${contactMechId}" />
        <input type="hidden" name="contactMechTypeId" value="${mechMap.contactMechTypeId}" />
        <input type="hidden" name="partyId" value="${partyId}" />
        <input type="hidden" name="DONE_PAGE" value="${donePage?if_exists}" />
  </#if>
  <#if "POSTAL_ADDRESS" = mechMap.contactMechTypeId?if_exists>

      <div class="am-form-group">
          <label for="doc-ipt-3" class="am-u-sm-2 am-control-label">${uiLabelMap.PartyAddressLine1} *</label>
        <div class="am-u-sm-10">
          <input style="width:520px;" type="text" size="100" maxlength="255" name="address1" value="${(mechMap.postalAddress.address1)?default(request.getParameter('address1')?if_exists)}" />
        </div>
      </div>
      <div class="am-form-group">
          <label for="doc-ipt-3" class="am-u-sm-2 am-control-label">${uiLabelMap.PartyAddressLine2}</label>
       <div class="am-u-sm-10">
        <input style="width:520px;" type="text" size="100" maxlength="255" name="address2" value="${(mechMap.postalAddress.address2)?default(request.getParameter('address2')?if_exists)}" />
       </div>
     </div>
      <div class="am-form-group">
          <label for="doc-ipt-3" class="am-u-sm-2 am-control-label">${uiLabelMap.PartyCity} *</label>
      <div class="am-u-sm-10">
          <select class="select" name="city" id="editcontactmechform_cityGeoId">
          </select>
      </div>
    </div>
     <div class="am-form-group">
          <label for="doc-ipt-3" class="am-u-sm-2 am-control-label">${uiLabelMap.PartyState}</label>
      <div class="am-u-sm-10">
        <select name="stateProvinceGeoId" id="editcontactmechform_stateProvinceGeoId">
        </select>
      </div>
    </div>
      <div class="am-form-group">
          <label for="doc-ipt-3" class="am-u-sm-2 am-control-label">${uiLabelMap.PartyZipCode} *</label>
      <div class="am-u-sm-10">
        <input style="width:520px;" type="text" size="30" maxlength="60" name="postalCode" value="${(mechMap.postalAddress.postalCode)?default(request.getParameter('postalCode')?if_exists)}" />
      </div>
    </div>
      <div class="am-form-group">
          <label for="doc-ipt-3" class="am-u-sm-2 am-control-label">${uiLabelMap.CommonCountry}</label>
      <div class="am-u-sm-10">
        <select name="countryGeoId" id="editcontactmechform_countryGeoId">
          ${screens.render("component://common/widget/CommonScreens.xml#countries")}
          <#if (mechMap.postalAddress?exists) && (mechMap.postalAddress.countryGeoId?exists)>
            <#assign defaultCountryGeoId = mechMap.postalAddress.countryGeoId>
          <#else>
           <#assign defaultCountryGeoId = Static["org.ofbiz.base.util.UtilProperties"].getPropertyValue("general.properties", "country.geo.id.default")>
          </#if>
          <option selected="selected" value="${defaultCountryGeoId}">
            <#assign countryGeo = delegator.findByPrimaryKey("Geo",Static["org.ofbiz.base.util.UtilMisc"].toMap("geoId",defaultCountryGeoId))>
            ${countryGeo.get("geoName",locale)}
          </option>
        </select>
      </div>
    </div>
    <#assign isUsps = Static["org.ofbiz.party.contact.ContactMechWorker"].isUspsAddress(mechMap.postalAddress)>
      <div class="am-form-group">
          <label for="doc-ipt-3" class="am-u-sm-2 am-control-label">${uiLabelMap.PartyIsUsps}</label>
      <div class="am-u-sm-10"><#if isUsps>${uiLabelMap.CommonY}<#else>${uiLabelMap.CommonN}</#if>
      </div>
    </div>

  <#elseif "TELECOM_NUMBER" = mechMap.contactMechTypeId?if_exists>
  <div class="am-form-group">
      <label for="doc-ipt-3" class="am-u-sm-2 am-control-label">${uiLabelMap.PartyPhoneNumber}</label>
      <div class="am-u-sm-10">
        <input style="width:520px;" type="text" size="4" maxlength="10" name="countryCode" value="${(mechMap.telecomNumber.countryCode)?default(request.getParameter('countryCode')?if_exists)}" />
        -&nbsp;<input type="text" size="4" maxlength="10" name="areaCode" value="${(mechMap.telecomNumber.areaCode)?default(request.getParameter('areaCode')?if_exists)}" />
        -&nbsp;<input type="text" size="15" maxlength="15" name="contactNumber" value="${(mechMap.telecomNumber.contactNumber)?default(request.getParameter('contactNumber')?if_exists)}" />
        &nbsp;${uiLabelMap.PartyContactExt}&nbsp;<input type="text" size="6" maxlength="10" name="extension" value="${(mechMap.partyContactMech.extension)?default(request.getParameter('extension')?if_exists)}" />
      </div>
    </div>
   <div class="am-form-group">
       <label for="doc-ipt-3" class="am-u-sm-2 am-control-label"></label>
      <div class="am-u-sm-10">[${uiLabelMap.PartyCountryCode}] [${uiLabelMap.PartyAreaCode}] [${uiLabelMap.PartyContactNumber}] [${uiLabelMap.PartyContactExt}]</div>
    </div>
  <#elseif "EMAIL_ADDRESS" = mechMap.contactMechTypeId?if_exists>
   <div class="am-form-group">
       <label for="doc-ipt-3" class="am-u-sm-2 am-control-label">${mechMap.contactMechType.get("description",locale)}</label>
      <div class="am-u-sm-10">
        <input style="width:520px;" type="text" size="60" maxlength="255" name="emailAddress" value="${(mechMap.contactMech.infoString)?default(request.getParameter('emailAddress')?if_exists)}" />
      </div>
    </div>
  <#else>
   <div class="am-form-group">
       <label for="doc-ipt-3" class="am-u-sm-2 am-control-label">${mechMap.contactMechType.get("description",locale)}</label>
      <div class="am-u-sm-10">
        <input style="width:520px;" type="text" size="60" maxlength="255" name="infoString" value="${(mechMap.contactMech.infoString)?if_exists}" />
      </div>
    </div>
  </#if>
   <div class="am-form-group">
       <label for="doc-ipt-3" class="am-u-sm-2 am-control-label">${uiLabelMap.PartyContactAllowSolicitation}?</label>
     <div class="am-u-sm-10">
      <select name="allowSolicitation">
        <#if (((mechMap.partyContactMech.allowSolicitation)!"") == "Y")><option value="Y">${uiLabelMap.CommonY}</option></#if>
        <#if (((mechMap.partyContactMech.allowSolicitation)!"") == "N")><option value="N">${uiLabelMap.CommonN}</option></#if>
        <option></option>
        <option value="Y">${uiLabelMap.CommonY}</option>
        <option value="N">${uiLabelMap.CommonN}</option>
      </select>
    </div>
  </div>
  </form>
</div>
</div>
  <div class="am-form-group">
      <div class="am-control-label am-u-md-3 am-u-lg-3"></div>
      <div class="am-control-label am-u-md-7 am-u-lg-7">
        <a href="<@ofbizUrl>backHome</@ofbizUrl>" class="am-btn am-btn-primary am-btn-sm">${uiLabelMap.CommonGoBack}</a>
        <a href="javascript:document.editcontactmechform.submit()" class="am-btn am-btn-primary am-btn-sm">${uiLabelMap.CommonSave}</a>
      </div>
   </div>
  </div>
</div>
</div>
<#else>
  <a href="<@ofbizUrl>backHome</@ofbizUrl>" class="am-btn am-btn-primary am-btn-sm">${uiLabelMap.CommonGoBack}</a>
</#if>
</div>
</div>