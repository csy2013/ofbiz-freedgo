<#--
Licensed to the Apache Software Foundation (ASF) under one
or more contributor license agreements.  See the NOTICE file
distributed with this work for additional information
regarding copyright ownership.  The ASF licenses this file
to you under the Apache License, Version 2.0 (the
"License"); you may not use this file except in compliance
with the License.  You may obtain a copy of the License at

http://www.apache.org/licenses/LICENSE-2.0

Unless required by applicable law or agreed to in writing,
software distributed under the License is distributed on an
"AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
KIND, either express or implied.  See the License for the
specific language governing permissions and limitations
under the License.
-->

<#assign extInfo = parameters.extInfo?default("N")>
<#assign inventoryItemId = parameters.inventoryItemId?default("")>
<#assign serialNumber = parameters.serialNumber?default("")>
<#assign softIdentifier = parameters.softIdentifier?default("")>
<#assign sortField = parameters.sortField?if_exists/>
<#-- Only allow the search fields to be hidden when we have some results -->
<#if partyList?has_content>
  <#assign hideFields = parameters.hideFields?default("N")>
<#else>
  <#assign hideFields = "N">
</#if>
<div class="am-cf">
    <div class="am-cf am-padding-xs">
        <span class="am-text-primary am-text-default">${uiLabelMap.PageTitleFindParty}</span>
    </div>
</div>

<#if (parameters.firstName?has_content || parameters.lastName?has_content)>
  <#assign createUrl = "editperson?create_new=Y&amp;lastName=${parameters.lastName?if_exists}&amp;firstName=${parameters.firstName?if_exists}"/>
<#elseif (parameters.groupName?has_content)>
  <#assign createUrl = "editpartygroup?create_new=Y&amp;groupName=${parameters.groupName?if_exists}"/>
<#else>
  <#assign createUrl = "createnew"/>
</#if>
<!--button bar-->
<#include "component://widget/templates/amazeHtmlTemplate.ftl"/>
<#assign commonUrl = "findparty?hideFields=" + hideFields + paramList + "&sortField=" + sortField?if_exists + "&"/>
<#assign viewIndexFirst = 0/>
<#assign viewIndexPrevious = viewIndex - 1/>
<#assign viewIndexNext = viewIndex + 1/>
<#assign viewIndexLast = Static["org.ofbiz.base.util.UtilMisc"].getViewLastIndex(partyListSize, viewSize) />
<#assign messageMap = Static["org.ofbiz.base.util.UtilMisc"].toMap("lowCount", lowIndex, "highCount", highIndex, "total", partyListSize)/>
<#assign commonDisplaying = Static["org.ofbiz.base.util.UtilProperties"].getMessage("CommonUiLabels", "CommonDisplaying", messageMap, locale)/>
<div class="button-bar"><a href="<@ofbizUrl>${createUrl}</@ofbizUrl>" class="am-btn am-btn-primary am-round am-btn-sm">${uiLabelMap.CommonCreateNew}</a></div>
<!-- search option -->
<div class="am-cf am-padding-xs">
    <div class="am-panel am-panel-default">
        <div class="am-panel-hd am-cf">
          ${uiLabelMap.CommonSearchOptions}
         </div>
        <div class="am-panel-bd am-collapse am-in" id="searchOptions_col">
            <div id="search-options" class="am-cf">
                <!-- Begin  Form Widget - Form Element  component://webtools/widget/PortalAdmForms.xml#FindPortalPages -->
                <div class="am-g am-center">
                    <div class="am-u-lg-10">
                        <form method="post" name="lookupparty" action="<@ofbizUrl>findparty</@ofbizUrl>" class="am-form am-form-horizontal" >
                            <input type="hidden" name="lookupFlag" value="Y"/>
                            <input type="hidden" name="hideFields" value="Y"/>

                            <div class="am-form-group am-g">
                                <label class="am-control-label am-u-md-3 am-u-lg-3" for="extInfo">${uiLabelMap.PartyContactInformation}</label>
                                <div class="am-u-md-3 am-u-lg-3 am-u-end">
                                    <input type="radio" id="extInfo" name="extInfo" value="N" onclick="refreshInfo();" <#if extInfo == "N">checked="checked"</#if>/>${uiLabelMap.CommonNone}&nbsp;
                                    <input type="radio" id="extInfo" name="extInfo" value="P" onclick="refreshInfo();" <#if extInfo == "P">checked="checked"</#if>/>${uiLabelMap.PartyPostal}&nbsp;
                                    <input type="radio" id="extInfo" name="extInfo" value="T" onclick="refreshInfo();" <#if extInfo == "T">checked="checked"</#if>/>${uiLabelMap.PartyTelecom}&nbsp;
                                    <input type="radio" id="extInfo" name="extInfo" value="O" onclick="refreshInfo();" <#if extInfo == "O">checked="checked"</#if>/>${uiLabelMap.CommonOther}&nbsp;
                                </div>
                                <label class="am-control-label am-u-md-3 am-u-lg-3" for="partyId">${uiLabelMap.PartyPartyId}</label>
                                <div class="am-u-md-3 am-u-lg-3 am-u-end">
                                    <input type="text" name="partyId" class="am-form-field am-input-sm" value="${parameters.partyId?if_exists}"/>
                                </div>
                            </div>

                            <div class="am-form-group am-g">
                                <label class="am-control-label am-u-md-3 am-u-lg-3" for="userLoginId">${uiLabelMap.PartyUserLogin}</label>
                                <div class="am-u-md-3 am-u-lg-3 am-u-end">
                                    <input type="text" name="userLoginId" class="am-form-field am-input-sm" value="${parameters.userLoginId?if_exists}"/>
                                </div>
                                <label class="am-control-label am-u-md-3 am-u-lg-3" for="lastName">${uiLabelMap.PartyLastName}</label>
                                <div class="am-u-md-3 am-u-lg-3 am-u-end">
                                    <input type="text" name="lastName" class="am-form-field am-input-sm" value="${parameters.lastName?if_exists}"/>
                                </div>
                            </div>

                            <div class="am-form-group am-g">
                                <label class="am-control-label am-u-md-3 am-u-lg-3" for="firstName">${uiLabelMap.PartyFirstName}</label>
                                <div class="am-u-md-3 am-u-lg-3 am-u-end">
                                    <input type="text" name="firstName" class="am-form-field am-input-sm" value="${parameters.firstName?if_exists}"/>
                                </div>
                                <label class="am-control-label am-u-md-3 am-u-lg-3" for="groupName">${uiLabelMap.PartyPartyGroupName}</label>
                                <div class="am-u-md-3 am-u-lg-3 am-u-end">
                                    <input type="text" name="groupName" class="am-form-field am-input-sm" value="${parameters.groupName?if_exists}"/>
                                </div>
                            </div>
                            <div class="am-form-group am-g">
                                <label class="am-control-label am-u-md-3 am-u-lg-3" for="roleTypeId">${uiLabelMap.PartyRoleType}</label>
                                <div class="am-u-md-3 am-u-lg-3 am-u-end">
                                    <select name="roleTypeId" >
                                    <#if currentRole?has_content>
                                        <option value="${currentRole.roleTypeId}">${currentRole.get("description",locale)}</option>
                                        <option value="${currentRole.roleTypeId}">---</option>
                                    </#if>
                                        <option value="ANY">${uiLabelMap.CommonAnyRoleType}</option>
                                    <#list roleTypes as roleType>
                                        <option value="${roleType.roleTypeId}">${roleType.get("description",locale)}</option>
                                    </#list>
                                    </select>
                                </div>
                                <label class="am-control-label am-u-md-3 am-u-lg-3" for="partyTypeId">${uiLabelMap.PartyType}</label>
                                <div class="am-u-md-3 am-u-lg-3 am-u-end">
                                    <select name="partyTypeId" class="am-input-lg">
                                    <#if currentPartyType?has_content>
                                        <option value="${currentPartyType.partyTypeId}">${currentPartyType.get("description",locale)}</option>
                                        <option value="${currentPartyType.partyTypeId}">---</option>
                                    </#if>
                                        <option value="ANY">${uiLabelMap.CommonAny}</option>
                                    <#list partyTypes as partyType>
                                        <option value="${partyType.partyTypeId}">${partyType.get("description",locale)}</option>
                                    </#list>
                                    </select>
                                </div>
                            </div>

                            <div class="am-form-group am-g">
                                <label class="am-control-label am-u-md-3 am-u-lg-3" for="inventoryItemId">${uiLabelMap.ProductInventoryItemId}</label>
                                <div class="am-u-md-3 am-u-lg-3 am-u-end">
                                    <input class="am-form-field am-input-sm" type="text" name="inventoryItemId" value="${parameters.inventoryItemId?if_exists}"/>
                                </div>
                                <label class="am-control-label am-u-md-3 am-u-lg-3" for="serialNumber">${uiLabelMap.ProductSerialNumber}</label>
                                <div class="am-u-md-3 am-u-lg-3 am-u-end">
                                    <input class="am-form-field am-input-sm" type="text" name="serialNumber" value="${parameters.serialNumber?if_exists}"/>
                                </div>
                            </div>

                            <div class="am-form-group am-g">
                                <label class="am-control-label am-u-md-3 am-u-lg-3" for="softIdentifier">${uiLabelMap.ProductSoftIdentifier}</label>
                                <div class="am-u-md-3 am-u-lg-3 am-u-end">
                                    <input class="am-form-field am-input-sm" type="text" name="softIdentifier" value="${parameters.softIdentifier?if_exists}"/>
                                </div>
                            </div>
                        <#if extInfo == "P">
                            <div class="am-form-group am-g">
                                <label class="am-control-label am-u-md-3 am-u-lg-3" for="address1">${uiLabelMap.CommonAddress1}</label>
                                <div class="am-u-md-3 am-u-lg-3 am-u-end">
                                    <input class="am-form-field am-input-sm" type="text" name="address1" value="${parameters.address1?if_exists}"/>
                                </div>
                                <label class="am-control-label am-u-md-3 am-u-lg-3" for="address2">${uiLabelMap.CommonAddress2}</label>
                                <div class="am-u-md-3 am-u-lg-3 am-u-end">
                                    <input class="am-form-field am-input-sm" type="text" name="address2" value="${parameters.address2?if_exists}"/>
                                </div>
                            </div>

                            <div class="am-form-group am-g">
                                <label class="am-control-label am-u-md-3 am-u-lg-3" for="city">${uiLabelMap.CommonCity}</label>
                                <div class="am-u-md-3 am-u-lg-3 am-u-end">
                                    <input class="am-form-field am-input-sm" type="text" name="city" value="${parameters.city?if_exists}"/>
                                </div>
                                <label class="am-control-label am-u-md-3 am-u-lg-3" for="stateProvinceGeoId">${uiLabelMap.CommonStateProvince}</label>
                                <div class="am-u-md-3 am-u-lg-3 am-u-end">
                                    <select name="stateProvinceGeoId" class="am-input-lg">
                                        <#if currentStateGeo?has_content>
                                            <option value="${currentStateGeo.geoId}">${currentStateGeo.geoName?default(currentStateGeo.geoId)}</option>
                                            <option value="${currentStateGeo.geoId}">---</option>
                                        </#if>
                                        <option value="ANY">${uiLabelMap.CommonAnyStateProvince}</option>
                                    ${screens.render("component://common/widget/CommonScreens.xml#states")}
                                    </select>
                                </div>
                            </div>

                            <div class="am-form-group am-g">
                                <label class="am-control-label am-u-md-3 am-u-lg-3" for="postalCode">${uiLabelMap.PartyPostalCode}</label>
                                <div class="am-u-md-3 am-u-lg-3 am-u-end">
                                    <input class="am-form-field am-input-sm" type="text" name="postalCode" value="${parameters.postalCode?if_exists}"/>
                                </div>
                            </div>

                        </#if>
                        <#if extInfo == "T">
                            <div class="am-form-group am-g">
                                <label class="am-control-label am-u-md-3 am-u-lg-3" for="countryCode">${uiLabelMap.PartyCountryCode}</label>
                                <div class="am-u-md-3 am-u-lg-3 am-u-end">
                                    <input  class="am-form-field am-input-sm" type="text" name="countryCode" value="${parameters.countryCode?if_exists}"/>
                                </div>
                                <label class="am-control-label am-u-md-3 am-u-lg-3" for="areaCode">${uiLabelMap.PartyAreaCode}</label>
                                <div class="am-u-md-3 am-u-lg-3 am-u-end">
                                    <input class="am-form-field am-input-sm" type="text" name="areaCode" value="${parameters.areaCode?if_exists}"/>
                                </div>
                            </div>
                            <div class="am-form-group am-g">
                                <label class="am-control-label am-u-md-3 am-u-lg-3" for="contactNumber">${uiLabelMap.PartyContactNumber}</label>
                                <div class="am-u-md-3 am-u-lg-3 am-u-end">PageTitleSearchResults
                                    <input class="am-form-field am-input-sm" type="text" name="contactNumber" value="${parameters.contactNumber?if_exists}"/>
                                </div>
                            </div>
                        </#if>
                        <#if extInfo == "O">
                            <div class="am-form-group am-g">
                                <label class="am-control-label am-u-md-3 am-u-lg-3" for="infoString">${uiLabelMap.PartyContactInformation}</label>
                                <div class="am-u-md-3 am-u-lg-3 am-u-end">
                                    <input class="am-form-field am-input-sm" type="text" name="infoString" value="${parameters.infoString?if_exists}"/>
                                </div>
                            </div>
                        </#if>
                            <div class="am-form-group am-g">
                                <div class="am-control-label am-u-md-5 am-u-lg-5"> </div>
                                <div class="am-u-md-7 am-u-lg-7 am-u-end">
                                    <div class="am-input-group am-cf am-fr">
                                        <input class="am-btn am-btn-primary am-btn-sm am-fl" type="submit" id="looksubmit" value="${uiLabelMap.CommonFind}" onclick="document.lookupparty.submit();"/>
                                    </div>
                                </div>
                            </div>
                        </form>
                    </div>
                </div>

     </div>
</div>
</div>
    <script language="JavaScript" type="text/javascript">
        document.lookupparty.partyId.focus();
    </script>
</div>

<div class="am-cf am-padding-xs">
    <div class="am-panel am-panel-default" id="screenlet_1">
    <div class="am-panel-bd am-collapse am-in" id="screenlet_1_col">
<#if partyList?exists>
    <div class="am-cf am-padding-xs">
        <h3>${uiLabelMap.CommonSearchResults}</h3>
    </div>
    <#if partyList?has_content>
    <#-- Pagination -->
      <div class="am-cf">

          <@amazeHtmlTemplate.nextPrev commonUrl=commonUrl ajaxEnabled=false javaScriptEnabled=false paginateStyle="am-fr" paginateFirstStyle="" viewIndex=viewIndex highIndex=highIndex listSize=partyListSize viewSize=viewSize ajaxFirstUrl="" firstUrl="" paginateFirstLabel="" paginatePreviousStyle="" ajaxPreviousUrl="" previousUrl="" paginatePreviousLabel="" pageLabel="" ajaxSelectUrl="" selectUrl="" ajaxSelectSizeUrl="" selectSizeUrl="" commonDisplaying=commonDisplaying paginateNextStyle="" ajaxNextUrl="" nextUrl="" paginateNextLabel="" paginateLastStyle="nav-last" ajaxLastUrl="" lastUrl="" paginateLastLabel="" paginateViewSizeLabel="" />
      </div>
        <div id="search-results" class="am-cf">
       <div class="am-u-sm-12">
        <table class="am-table am-table-striped am-table-hover" cellspacing="0">
            <tr class="header-row-2">
                <td>${uiLabelMap.PartyPartyId}</td>
                <td>${uiLabelMap.PartyUserLogin}</td>
                <td>${uiLabelMap.PartyName}</td>
                <#if extInfo?default("") == "P" >
                    <td>${uiLabelMap.PartyCity}</td>
                </#if>
                <#if extInfo?default("") == "P">
                    <td>${uiLabelMap.PartyPostalCode}</td>
                </#if>
                <#if extInfo?default("") == "T">
                    <td>${uiLabelMap.PartyAreaCode}</td>
                </#if>
                <#if inventoryItemId?default("") != "">
                    <td>${uiLabelMap.ProductInventoryItemId}</td>
                </#if>
                <#if serialNumber?default("") != "">
                    <td>${uiLabelMap.ProductSerialNumber}</td>
                </#if>
                <#if softIdentifier?default("") != "">
                    <td>${uiLabelMap.ProductSoftIdentifier}</td>
                </#if>
                <td>${uiLabelMap.PartyRelatedCompany}</td>
                <td>${uiLabelMap.PartyType}</td>
                <td>${uiLabelMap.PartyMainRole}</td>
                <td>
                    <a href="<@ofbizUrl>findparty</@ofbizUrl>?<#if sortField?has_content><#if sortField == "createdDate">sortField=-createdDate<#elseif sortField == "-createdDate">sortField=createdDate<#else>sortField=createdDate</#if><#else>sortField=createdDate</#if>${paramList?if_exists}&VIEW_SIZE=${viewSize?if_exists}&VIEW_INDEX=${viewIndex?if_exists}"
                        <#if sortField?has_content><#if sortField == "createdDate">class="sort-order-desc"<#elseif sortField == "-createdDate">class="sort-order-asc"<#else>class="sort-order"</#if><#else>class="sort-order"</#if>>${uiLabelMap.FormFieldTitle_createdDate}
                    </a>
                </td>
                <td>
                    <a href="<@ofbizUrl>findparty</@ofbizUrl>?<#if sortField?has_content><#if sortField == "lastModifiedDate">sortField=-lastModifiedDate<#elseif sortField == "-lastModifiedDate">sortField=lastModifiedDate<#else>sortField=lastModifiedDate</#if><#else>sortField=lastModifiedDate</#if>${paramList?if_exists}&VIEW_SIZE=${viewSize?if_exists}&VIEW_INDEX=${viewIndex?if_exists}"
                        <#if sortField?has_content><#if sortField == "lastModifiedDate">class="sort-order-desc"<#elseif sortField == "-lastModifiedDate">class="sort-order-asc"<#else>class="sort-order"</#if><#else>class="sort-order"</#if>>${uiLabelMap.FormFieldTitle_lastModifiedDate}
                    </a>
                </td
            </tr>
            <#assign alt_row = false>
            <#assign rowCount = 0>
            <#list partyList as partyRow>
                <#assign partyType = partyRow.getRelatedOne("PartyType")?if_exists>
                <tr valign="middle"<#if alt_row> class="alternate-row"</#if>>
                    <td><a href="<@ofbizUrl>viewprofile?partyId=${partyRow.partyId}</@ofbizUrl>">${partyRow.partyId}</a></td>
                    <td>
                        <#if partyRow.containsKey("userLoginId")>
                        ${partyRow.userLoginId?default("N/A")}
                        <#else>
                            <#assign userLogins = partyRow.getRelated("UserLogin")>
                            <#if (userLogins.size() > 0)>
                                <#if (userLogins.size() > 1)>
                                    (${uiLabelMap.CommonMany})
                                <#else>
                                    <#assign userLogin = userLogins.get(0)>
                                ${userLogin.userLoginId}
                                </#if>
                            <#else>
                                (${uiLabelMap.CommonNone})
                            </#if>
                        </#if>
                    </td>
                    <td>
                        <#if partyRow.getModelEntity().isField("lastName") && lastName?has_content>
                        ${partyRow.lastName}<#if partyRow.firstName?has_content>, ${partyRow.firstName}</#if>
                        <#elseif partyRow.getModelEntity().isField("groupName") && partyRow.groupName?has_content>
                        ${partyRow.groupName}
                        <#else>
                            <#assign partyName = Static["org.ofbiz.party.party.PartyHelper"].getPartyName(partyRow, true)>
                            <#if partyName?has_content>
                            ${partyName}
                            <#else>
                                (${uiLabelMap.PartyNoNameFound})
                            </#if>
                        </#if>
                    </td>
                    <#if extInfo?default("") == "T">
                        <td>${partyRow.areaCode?if_exists}</td>
                    </#if>
                    <#if extInfo?default("") == "P" >
                        <td>${partyRow.city?if_exists}, ${partyRow.stateProvinceGeoId?if_exists}</td>
                    </#if>
                    <#if extInfo?default("") == "P">
                        <td>${partyRow.postalCode?if_exists}</td>
                    </#if>
                    <#if inventoryItemId?default("") != "">
                        <td>${partyRow.inventoryItemId?if_exists}</td>
                    </#if>
                    <#if serialNumber?default("") != "">
                        <td>${partyRow.serialNumber?if_exists}</td>
                    </#if>
                    <#if softIdentifier?default("") != "">
                        <td>${partyRow.softIdentifier?if_exists}</td>
                    </#if>
                    <#if partyType?exists>
                        <td>
                            <#if partyType.partyTypeId?has_content && partyType.partyTypeId=="PERSON">
          <#assign partyRelateCom = delegator.findByAnd("PartyRelationship", {"partyIdTo", partyRow.partyId,"roleTypeIdFrom","ACCOUNT","roleTypeIdTo","CONTACT"})>
          <#if partyRelateCom?has_content>
                                <#list partyRelateCom as partyRelationship>
                                    <#if partyRelationship.partyIdFrom?has_content>
                                        <#assign companyName=Static["org.ofbiz.party.party.PartyHelper"].getPartyName(delegator, partyRelationship.partyIdFrom, true)>
                                    ${companyName?if_exists}
                                    </#if>
                                </#list>
                            </#if>
        </#if>
                        </td>
                        <td><#if partyType.description?exists>${partyType.get("description", locale)}<#else>???</#if></td>
                    <#else>
                        <td></td><td></td>
                    </#if>
                    <td>
                        <#assign mainRole = dispatcher.runSync("getPartyMainRole", Static["org.ofbiz.base.util.UtilMisc"].toMap("partyId", partyRow.partyId, "userLogin", userLogin))/>
              ${mainRole.description?if_exists}
                    </td>
                    <#assign partyDate = delegator.findOne("Party", {"partyId":partyRow.partyId}, true)/>
                    <td>${partyDate.createdDate?if_exists}</td>
                    <td>${partyDate.lastModifiedDate?if_exists}</td>
                </tr>
                <#assign rowCount = rowCount + 1>
            <#-- toggle the row color -->
                <#assign alt_row = !alt_row>
            </#list>
        </table>
         </div>
    </div>
    <#else>
        <div id="findPartyResults_2">
            <h3>${uiLabelMap.PartyNoPartiesFound}</h3>
        </div>
    </#if>
    <#if lookupErrorMessage?exists>
        <h3>${lookupErrorMessage}</h3>
    </#if>

</#if>
    </div>
    </div>
    </div>





