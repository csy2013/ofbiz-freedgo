<div id="editContactmechAreaId">
<#if !mechMap.contactMech?exists>
<#-- When creating a new contact mech, first select the type, then actually create -->
    <#if !preContactMechTypeId?has_content>
        <form method="post" action="<@ofbizUrl>editcontactmech</@ofbizUrl>" name="createcontactmechform" id="createcontactmechform" class="form-inline">
            <input type="hidden" name="partyId" value="${partyId}"/>

            <div class="form-group">
                <div class="input-group">
                    <span class="input-group-addon">${uiLabelMap.PartySelectContactType}</span>
                    <select name="preContactMechTypeId" class="form-control">
                        <#list mechMap.contactMechTypes as contactMechType>
                            <#if contactMechType.contactMechTypeId =='EMAIL_ADDRESS' || contactMechType.contactMechTypeId=='POSTAL_ADDRESS'
                            || contactMechType.contactMechTypeId=='TELECOM_NUMBER' || contactMechType.contactMechTypeId=='WEB_ADDRESS'>
                                <option value="${contactMechType.contactMechTypeId}">${contactMechType.get("description",locale)}</option>
                            </#if>
                        </#list>
                    </select>
                </div>
                <a href="javascript:" onclick="ajaxSubmitFormUpdateAreas('createcontactmechform','ajax,editContactmechFormAreaId,,')"
                   class="btn btn-primary btn-sm">${uiLabelMap.CommonCreate}</a>
            </div>
        </form>
    </#if>
</#if>
    <div id="editContactmechFormAreaId">
    <#if mechMap.contactMechTypeId?has_content>
        <#if !mechMap.contactMech?has_content>
            <#if contactMechPurposeType?exists>
                <p>(${uiLabelMap.PartyMsgContactHavePurpose} <b>"${contactMechPurposeType.get("description",locale)?if_exists}"</b>)</p>
            </#if>

        <form method="post" action="<@ofbizUrl>${mechMap.requestName}</@ofbizUrl>" name="editcontactmechform" id="editcontactmechform" class="form-horizontal" data-parsley-validate="true">
            <input type="hidden" name="DONE_PAGE" value="${donePage}"/>
            <input type="hidden" name="contactMechTypeId" value="${mechMap.contactMechTypeId}"/>
            <input type="hidden" name="partyId" value="${partyId}"/>
            <#if cmNewPurposeTypeId?has_content><input type="hidden" name="contactMechPurposeTypeId" value="${cmNewPurposeTypeId}"/></#if>
            <#if preContactMechTypeId?exists><input type="hidden" name="preContactMechTypeId" value="${preContactMechTypeId}"/></#if>
            <#if contactMechPurposeTypeId?exists><input type="hidden" name="contactMechPurposeTypeId" value="${contactMechPurposeTypeId?if_exists}"/></#if>
            <#if paymentMethodId?has_content><input type='hidden' name='paymentMethodId' value='${paymentMethodId}'/></#if>
        <#else>
            <#if mechMap.purposeTypes?has_content>
                <table class="table table-bordered">
                    <tr>
                        <th>${uiLabelMap.PartyContactPurposes}</th>
                        <th>&nbsp;</th>
                    </tr>
                    <#if mechMap.partyContactMechPurposes?has_content>
                        <#list mechMap.partyContactMechPurposes as partyContactMechPurpose>
                        <tr>
                            <td>
                                <#assign contactMechPurposeType = partyContactMechPurpose.getRelatedOneCache("ContactMechPurposeType")>

                                <#if contactMechPurposeType?has_content>
                                ${contactMechPurposeType.get("description",locale)}
                                <#else>
                                ${uiLabelMap.PartyPurposeTypeNotFound}: "${partyContactMechPurpose.contactMechPurposeTypeId}"
                                </#if>
                                (${uiLabelMap.CommonSince}:${partyContactMechPurpose.fromDate.toString()})
                                <#if partyContactMechPurpose.thruDate?has_content>(${uiLabelMap.CommonExpire}: ${partyContactMechPurpose.thruDate.toString()}</#if>
                            </td>
                            <td>
                                <a href="javascript:"
                                   onclick="ajaxSubmitFormUpdateAreas('deletePartyContactMechPurpose_${partyContactMechPurpose.contactMechPurposeTypeId}','url,,,')"
                                   class="btn btn-primary btn-sm">${uiLabelMap.CommonDelete}
                                    <form name="deletePartyContactMechPurpose_${partyContactMechPurpose.contactMechPurposeTypeId}" method="post"
                                          id="deletePartyContactMechPurpose_${partyContactMechPurpose.contactMechPurposeTypeId}"
                                          action="<@ofbizUrl>deletePartyContactMechPurpose</@ofbizUrl>" class="form-inline">
                                        <input type="hidden" name="partyId" value="${partyId}"/>
                                        <input type="hidden" name="contactMechId" value="${contactMechId}"/>
                                        <input type="hidden" name="contactMechPurposeTypeId" value="${partyContactMechPurpose.contactMechPurposeTypeId}"/>
                                        <input type="hidden" name="fromDate" value="${partyContactMechPurpose.fromDate.toString()}"/>
                                        <input type="hidden" name="DONE_PAGE" value="${donePage?replace("=","%3d")}"/>
                                        <input type="hidden" name="useValues" value="true"/>
                                    </form>
                                </a></td>
                        </#list>
                    </#if>
                    <tr>
                        <td>

                            <form method="post" action="<@ofbizUrl>createPartyContactMechPurpose</@ofbizUrl>" name="newpurposeform" id="newpurposeform" class="form-horizontal">
                                <input type="hidden" name="partyId" value="${partyId}"/>
                                <input type="hidden" name="DONE_PAGE" value="${donePage}"/>
                                <input type="hidden" name="useValues" value="true"/>
                                <input type="hidden" name="contactMechId" value="${contactMechId?if_exists}"/>

                                <select name="contactMechPurposeTypeId" class="form-control">
                                    <option></option>
                                    <#list mechMap.purposeTypes as contactMechPurposeType>
                                        <#if contactMechPurposeType.contactMechPurposeTypeId!='PAYMENT_LOCATION' && contactMechPurposeType.contactMechPurposeTypeId!='PREVIOUS_LOCATION' &&
                                        contactMechPurposeType.contactMechPurposeTypeId!='PRIMARY_LOCATION'&& contactMechPurposeType.contactMechPurposeTypeId!='SHIP_ORIG_LOCATION'>
                                            <option value="${contactMechPurposeType.contactMechPurposeTypeId}">${contactMechPurposeType.get("description",locale)}</option>
                                        </#if>
                                    </#list>
                                </select>
                            </form>
                        </td>
                        <td>
                            <a href="javascript:"
                               onclick="ajaxSubmitFormUpdateAreas('newpurposeform','url,,,')"
                               class="btn btn-primary btn-sm">${uiLabelMap.PartyAddPurpose}</a>
                        </td>
                    </tr>
                </table>
            </#if>
        <div class="">
        <form method="post" action="<@ofbizUrl>${mechMap.requestName}</@ofbizUrl>" name="editcontactmechform" id="editcontactmechform" class="form-horizontal ">
            <input type="hidden" name="contactMechId" value="${contactMechId}"/>
            <input type="hidden" name="contactMechTypeId" value="${mechMap.contactMechTypeId}"/>
            <input type="hidden" name="partyId" value="${partyId}"/>
            <input type="hidden" name="DONE_PAGE" value="${donePage?if_exists}"/>
        </#if>
        <#if "POSTAL_ADDRESS" = mechMap.contactMechTypeId?if_exists>
            <h4>地址编辑</h4>
        <#--<div class="form-group">
            <label class="control-label col-md-4">${uiLabelMap.CommonCountry}</label>

            <div class="col-md-7">
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
        </div>-->
            <input type="hidden" name="countryGeoId" id="editcontactmechform_countryGeoId" value="CHN"/>
            <div class="form-group">
                <label class="control-label col-md-4">${uiLabelMap.PartyState}</label>

                <div class="col-md-7">
                    <select name="stateProvinceGeoId" id="editcontactmechform_stateProvinceGeoId" class="form-control">
                    </select>
                </div>
            </div>

            <div class="form-group">
                <label class="control-label col-md-4">${uiLabelMap.PartyCity} *</label>

                <div class="col-md-7">
                    <select name="city" id="editcontactmechform_city" class="form-control">
                    </select>
                <#--<input type="text" size="50" maxlength="100" name="city" value="${(mechMap.postalAddress.city)?default(request.getParameter('city')?if_exists)}"/>-->
                </div>
            </div>
            <div class="form-group">
                <label class="control-label col-md-4">${uiLabelMap.FormFieldTitle_countyGeoId} *</label>

                <div class="col-md-7">
                    <select name="countyGeoId" id="editcontactmechform_countyGeoId" class="form-control">
                    </select>
                <#--<input type="text" size="50" maxlength="100" name="city" value="${(mechMap.postalAddress.city)?default(request.getParameter('city')?if_exists)}"/>-->
                </div>
            </div>
            <div class="form-group">
                <label class="control-label col-md-4">${uiLabelMap.PartyToName}</label>

                <div class="col-md-7">
                    <input type="text" name="toName" maxlength="100" value="${(mechMap.postalAddress.toName)?default(request.getParameter('toName')?if_exists)}" class="form-control"/>
                </div>
            </div>
          <#--  <div class="form-group">
                <label class="control-label col-md-4">${uiLabelMap.PartyAttentionName}</label>

                <div class="col-md-7">
                    <input type="text" name="attnName" maxlength="100" value="${(mechMap.postalAddress.attnName)?default(request.getParameter('attnName')?if_exists)}"
                           class="form-control"/>
                </div>
            </div>-->
            <div class="form-group">
                <label class="control-label col-md-4">${uiLabelMap.PartyAddressLine1}</label>

                <div class="col-md-7">
                    <input type="text" name="address1" required="true" maxlength="255" value="${(mechMap.postalAddress.address1)?default(request.getParameter('address1')?if_exists)}"
                           class="form-control"/>
                </div>
            </div>
            <div class="form-group">
                <label class="control-label col-md-4">${uiLabelMap.PartyAddressLine2}</label>

                <div class="col-md-7">
                    <input type="text" name="address2" maxlength="255" value="${(mechMap.postalAddress.address2)?default(request.getParameter('address2')?if_exists)}"
                           class="form-control"/>
                </div>
            </div>
            <div class="form-group">
                <label class="control-label col-md-4">${uiLabelMap.PartyContactMobilePhoneNumber}</label>

                <div class="col-md-7">
                    <input type="text" name="mobilePhone" maxlength="255" value="${(mechMap.postalAddress.mobilePhone)?default(request.getParameter('mobilePhone')?if_exists)}"
                           class="form-control"/>
                </div>
            </div>

            <div class="form-group">
                <label class="control-label col-md-4">${uiLabelMap.PartyZipCode} *</label>

                <div class="col-md-7">
                    <input type="text" name="postalCode" data-parsley-required="true" value="${(mechMap.postalAddress.postalCode)?default(request.getParameter('postalCode')?if_exists)}"
                           class="form-control"/>
                </div>
            </div>

        <#-- <#assign isUsps = Static["org.ofbiz.party.contact.ContactMechWorker"].isUspsAddress(mechMap.postalAddress)>
         <div class="form-group">
             <label class="control-label col-md-4">${uiLabelMap.PartyIsUsps}</div>
             <div class="col-md-7"><#if isUsps>${uiLabelMap.CommonY}<#else>${uiLabelMap.CommonN}</#if>
             </div>
         </div>-->
        <#elseif "TELECOM_NUMBER" = mechMap.contactMechTypeId?if_exists>
            <h4>${uiLabelMap.PartyPhoneNumber}</h4>
            <div class="form-group">
                <label class="control-label col-md-4">国家</label>

                <div class="col-md-7">
                    <input type="text" size="4" maxlength="10" name="countryCode" value="${(mechMap.telecomNumber.countryCode)?default(request.getParameter('countryCode')?if_exists)}"
                           class="form-control"/>
                </div>
            </div>
            <div class="form-group">
                <label class="control-label col-md-4">区号</label>

                <div class="col-md-7">
                    <input type="text" size="4" maxlength="10" name="areaCode" value="${(mechMap.telecomNumber.areaCode)?default(request.getParameter('areaCode')?if_exists)}"
                           class="form-control"/>
                </div>
            </div>
            <div class="form-group">
                <label class="control-label col-md-4">联系号码</label>

                <div class="col-md-7">
                    <input type="text" size="15" maxlength="15" name="contactNumber"
                           value="${(mechMap.telecomNumber.contactNumber)?default(request.getParameter('contactNumber')?if_exists)}" class="form-control"/></div>
            </div>
            <div class="form-group">
                <label class="control-label col-md-4">分机号</label>

                <div class="col-md-7">
                    <input type="text" size="6" maxlength="10" name="extension"
                           value="${(mechMap.partyContactMech.extension)?default(request.getParameter('extension')?if_exists)}" class="form-control"/>
                </div>
            </div>

        <#elseif "EMAIL_ADDRESS" = mechMap.contactMechTypeId?if_exists>
            <br/><br/>
            <div class="form-group">
                <label class="control-label col-md-4">${mechMap.contactMechType.get("description",locale)}</label>

                <div class="col-md-7">
                    <input type="text" size="60" class="form-control" maxlength="255" name="emailAddress"
                           value="${(mechMap.contactMech.infoString)?default(request.getParameter('emailAddress')?if_exists)}"/>
                </div>
            </div>
        <#else>
            <br/><br/>
            <div class="form-group">
                <label class="control-label col-md-4">${mechMap.contactMechType.get("description",locale)}</label>

                <div class="col-md-7">
                    <input type="text" size="60" maxlength="255" name="infoString" value="${(mechMap.contactMech.infoString)?if_exists}" class="form-control"/>
                </div>
            </div>
        </#if>
    <#--<div class="form-group">
        <label class="control-label col-md-4">${uiLabelMap.PartyContactAllowSolicitation}?</div>
        <div class="col-md-7">
            <select name="allowSolicitation">
                <#if (((mechMap.partyContactMech.allowSolicitation)!"") == "Y")>
                    <option value="Y">${uiLabelMap.CommonY}</option></#if>
                <#if (((mechMap.partyContactMech.allowSolicitation)!"") == "N")>
                    <option value="N">${uiLabelMap.CommonN}</option></#if>
                <option></option>
                <option value="Y">${uiLabelMap.CommonY}</option>
                <option value="N">${uiLabelMap.CommonN}</option>
            </select>
        </div>
    </div>-->
        <div class="form-group">
        <#--<a href="<@ofbizUrl>backHome</@ofbizUrl>" class="btn btn-primary btn-sm">${uiLabelMap.CommonGoBack}</a>-->
            <div class="col-md-4">&nbsp;</div>
            <div class="col-md-7 ">
                <input type="submit" class="btn btn-primary btn-sm pull-right" value="${uiLabelMap.CommonSave}"/>
            </div>
        </div>
    </form>
    </div>
    <#else>
    <#--<a href="<@ofbizUrl>backHome</@ofbizUrl>" class="btn btn-primary btn-sm">${uiLabelMap.CommonGoBack}</a>-->
    </#if>
    </div>
</div>
