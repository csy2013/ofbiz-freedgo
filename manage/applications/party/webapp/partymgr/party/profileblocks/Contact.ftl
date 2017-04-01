<#include "component://common/webcommon/includes/htmlScreenTemplate.ftl"/>
<div id="partyContactInfo" class="${panelStyle}">
    <div class="${panelHeadingStyle}">
        <div class="btn-group pull-right">
        <#if security.hasEntityPermission("PARTYMGR", "_CREATE", session) || userLogin.partyId == partyId>
            <@htmlScreenTemplate.renderModalPage id="addcontactmechpa"  name="addcontactmechpa"
                modalTitle="${StringUtil.wrapString(uiLabelMap.CommonCreateNew)}" buttonType=""
                modalUrl="editcontactmech" buttonType="custom" buttonStyle="btn btn-white btn-xs"
                description="新建地址"   targetParameterIter="partyId:'${partyId}',preContactMechTypeId:'POSTAL_ADDRESS'"/>


                <button type="button" class="btn btn-white btn-xs dropdown-toggle" data-toggle="dropdown" aria-expanded="false">
                    <span class="caret"></span>
                </button>
                <ul class="dropdown-menu" role="menu">
                    <li><@htmlScreenTemplate.renderModalPage id="addcontactmechemail"  name="addcontactmechemail"
                    modalTitle="${StringUtil.wrapString(uiLabelMap.CommonCreateNew)}"
                    modalUrl="editcontactmech" buttonType="custom" buttonStyle=""
                    description="新建电子邮件地址"
                    targetParameterIter="partyId:'${partyId}',preContactMechTypeId:'EMAIL_ADDRESS'"/></li>
                    <li><@htmlScreenTemplate.renderModalPage id="addcontactmechtm"  name="addcontactmechtm"
                    modalTitle="${StringUtil.wrapString(uiLabelMap.CommonCreateNew)}"
                    modalUrl="editcontactmech" buttonType="custom" buttonStyle=""
                    description="新建电话号码"
                    targetParameterIter="partyId:'${partyId}',preContactMechTypeId:'TELECOM_NUMBER'"/></li>
                    <li><@htmlScreenTemplate.renderModalPage id="addcontactmechurl"  name="addcontactmechurl"
                    modalTitle="${StringUtil.wrapString(uiLabelMap.CommonCreateNew)}"
                    modalUrl="editcontactmech" buttonType="custom" buttonStyle=""
                    description="新建网站网址"
                    targetParameterIter="partyId:'${partyId}',preContactMechTypeId:'WEB_ADDRESS'"/></li>

            </ul>



        <#--<a href="<@ofbizUrl>editcontactmech?partyId=${partyId}</@ofbizUrl>" class="btn btn-white btn-xs">${uiLabelMap.CommonCreateNew}</a>-->
        </#if>
        </div>

        <h4 class="${panelTitleStyle}"> ${uiLabelMap.PartyContactInformation}</h4>

    </div>
    <div class="${panelBodyStyle}">
    <#if contactMeches?has_content>
        <div class="table-responsive">
            <table class="table">
                <tr>
                    <th>${uiLabelMap.PartyContactType}</th>
                    <th>${uiLabelMap.PartyContactInformation}</th>
                    <th>${uiLabelMap.PartyContactSolicitingOk}</th>
                    <th>&nbsp;</th>
                </tr>
                <#list contactMeches as contactMechMap>
                    <#assign contactMech = contactMechMap.contactMech>
                    <#assign partyContactMech = contactMechMap.partyContactMech>
                    <tr>
                        <td>${contactMechMap.contactMechType.get("description",locale)}</td>
                        <td>
                            <#list contactMechMap.partyContactMechPurposes as partyContactMechPurpose>
                                <#assign contactMechPurposeType = partyContactMechPurpose.getRelatedOneCache("ContactMechPurposeType")>
                                <div>
                                    <#if contactMechPurposeType?has_content>
                                        <b>${contactMechPurposeType.get("description",locale)}</b>
                                    <#else>
                                        <b>${uiLabelMap.PartyMechPurposeTypeNotFound}: "${partyContactMechPurpose.contactMechPurposeTypeId}"</b>
                                    </#if>
                                    <#if partyContactMechPurpose.thruDate?has_content>
                                        (${uiLabelMap.CommonExpire}: ${partyContactMechPurpose.thruDate})
                                    </#if>
                                </div>
                            </#list>
                            <#if "POSTAL_ADDRESS" = contactMech.contactMechTypeId>
                                <#if contactMechMap.postalAddress?has_content>
                                    <#assign postalAddress = contactMechMap.postalAddress>
                                ${setContextField("postalAddress", postalAddress)}
                                ${screens.render("component://party/widget/partymgr/PartyScreens.xml#postalAddressHtmlFormatter")}
                                    <#if postalAddress.geoPointId?has_content>
                                        <#if contactMechPurposeType?has_content>
                                            <#assign popUptitle = contactMechPurposeType.get("description", locale) + uiLabelMap.CommonGeoLocation>
                                        </#if>
                                        <a href="javascript:popUp('<@ofbizUrl>PartyGeoLocation?geoPointId=${postalAddress.geoPointId}&partyId=${partyId}</@ofbizUrl>', '${popUptitle?if_exists}', '450', '550')"
                                           class="buttontext">${uiLabelMap.CommonGeoLocation}</a>
                                    </#if>
                                </#if>
                            <#elseif "TELECOM_NUMBER" = contactMech.contactMechTypeId>
                                <#if contactMechMap.telecomNumber?has_content>
                                    <#assign telecomNumber = contactMechMap.telecomNumber>
                                    <div>
                                    ${telecomNumber.countryCode?if_exists}
                                        <#if telecomNumber.areaCode?has_content>${telecomNumber.areaCode?default("000")}
                                            -</#if><#if telecomNumber.contactNumber?has_content>${telecomNumber.contactNumber?default("000-0000")}</#if>
                                        <#if partyContactMech.extension?has_content>${uiLabelMap.PartyContactExt}&nbsp;${partyContactMech.extension}</#if>
                                        <#if !telecomNumber.countryCode?has_content || telecomNumber.countryCode = "011">
                                            <a target="_blank" href="${uiLabelMap.CommonLookupAnywhoLink}" class="buttontext">${uiLabelMap.CommonLookupAnywho}</a>
                                            <a target="_blank" href="${uiLabelMap.CommonLookupWhitepagesTelNumberLink}" class="buttontext">${uiLabelMap.CommonLookupWhitepages}</a>
                                        </#if>
                                    </div>
                                </#if>
                            <#elseif "EMAIL_ADDRESS" = contactMech.contactMechTypeId>
                                <div>
                                ${contactMech.infoString?if_exists}
                                    <form method="post" action="<@ofbizUrl>NewDraftCommunicationEvent</@ofbizUrl>" onsubmit="submitFormDisableSubmits(this)"
                                          name="createEmail${contactMech.infoString?replace("&#64;","")?replace(".","")}">
                                        <#if userLogin.partyId?has_content>
                                            <input name="partyIdFrom" value="${userLogin.partyId}" type="hidden"/>
                                        </#if>
                                        <input name="partyIdTo" value="${partyId}" type="hidden"/>
                                        <input name="contactMechIdTo" value="${contactMech.contactMechId}" type="hidden"/>
                                        <input name="my" value="My" type="hidden"/>
                                        <input name="statusId" value="COM_PENDING" type="hidden"/>
                                        <input name="communicationEventTypeId" value="EMAIL_COMMUNICATION" type="hidden"/>
                                    </form>
                                    <a class="buttontext"
                                       href="javascript:document.createEmail${contactMech.infoString?replace("&#64;","")?replace(".","")}.submit()">${uiLabelMap.CommonSendEmail}</a>
                                </div>
                            <#elseif "WEB_ADDRESS" = contactMech.contactMechTypeId>
                                <div>
                                ${contactMech.infoString?if_exists}
                                    <#assign openAddress = contactMech.infoString?default("")>
                                    <#if !openAddress?starts_with("http") && !openAddress?starts_with("HTTP")><#assign openAddress = "http://" + openAddress></#if>
                                    <a target="_blank" href="${openAddress}" class="buttontext">${uiLabelMap.CommonOpenPageNewWindow}</a>
                                </div>
                            <#else>
                                <div>${contactMech.infoString?if_exists}</div>
                            </#if>
                            <div>(${uiLabelMap.CommonUpdated}:&nbsp;${partyContactMech.fromDate})</div>
                            <#if partyContactMech.thruDate?has_content>
                                <div><b>${uiLabelMap.PartyContactEffectiveThru}:&nbsp;${partyContactMech.thruDate}</b></div></#if>
                        <#-- create cust request -->
                            <#if custRequestTypes?exists>
                                <form name="createCustRequestForm" action="<@ofbizUrl>createCustRequest</@ofbizUrl>" method="post" onsubmit="submitFormDisableSubmits(this)">
                                    <input type="hidden" name="partyId" value="${partyId}"/>
                                    <input type="hidden" name="fromPartyId" value="${partyId}"/>
                                    <input type="hidden" name="fulfillContactMechId" value="${contactMech.contactMechId}"/>
                                    <select name="custRequestTypeId">
                                        <#list custRequestTypes as type>
                                            <option value="${type.custRequestTypeId}">${type.get("description", locale)}</option>
                                        </#list>
                                    </select>
                                    <input type="submit" class="smallSubmit" value="${uiLabelMap.PartyCreateNewCustRequest}"/>
                                </form>
                            </#if>
                        </td>
                        <td valign="top"><b>(${partyContactMech.allowSolicitation?if_exists})</b></td>
                        <td class="button-col">
                            <#if security.hasEntityPermission("PARTYMGR", "_UPDATE", session) || userLogin.partyId == partyId>
                            <#--<a href="<@ofbizUrl>editcontactmech?partyId=${partyId}&amp;contactMechId=${contactMech.contactMechId}</@ofbizUrl>">${uiLabelMap.CommonUpdate}</a>-->
                                <@htmlScreenTemplate.renderModalPage id="PartyUpdateAddress_${contactMechMap_index}"  name="PartyUpdateAddress_${contactMechMap_index}"
                                modalTitle="${StringUtil.wrapString(uiLabelMap.CommonUpdate)}"
                                modalUrl="editcontactmech"
                                description="${uiLabelMap.CommonUpdate}"
                                targetParameterIter="partyId:'${partyId}',contactMechId:'${contactMech.contactMechId}'"/>

                            </#if>
                            <#if security.hasEntityPermission("PARTYMGR", "_DELETE", session) || userLogin.partyId == partyId>
                            <@htmlScreenTemplate.renderConfirmField id="partyDeleteContace_${contactMech.contactMechId}" name="partyDeleteContace_${contactMech.contactMechId}"
                            confirmTitle="删除联系" confirmMessage="确定删除该联系方式" targetParameterIter="partyId:'${partyId}',contactMechId:'${contactMech.contactMechId}'"
                            confirmUrl="deleteContactMech" description="删除"/>
                        <#-- <form name="partyDeleteContact" method="post" action="<@ofbizUrl>deleteContactMech</@ofbizUrl>" onsubmit="submitFormDisableSubmits(this)">
                             <input name="partyId" value="${partyId}" type="hidden"/>
                             <input name="contactMechId" value="${contactMech.contactMechId}" type="hidden"/>
                             <input type="submit" class="smallSubmit" value="${uiLabelMap.CommonExpire}"/>
                         </form>-->
                        </#if>
                        </td>
                    </tr>
                </#list>
            </table>
        </div>
    <#else>
    ${uiLabelMap.PartyNoContactInformation}
    </#if>
    </div>
</div>
