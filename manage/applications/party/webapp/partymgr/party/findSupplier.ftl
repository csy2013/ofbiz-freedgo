<#assign extInfo = parameters.extInfo?default("N")>
<#assign inventoryItemId = parameters.inventoryItemId?default("")>
<#assign serialNumber = parameters.serialNumber?default("")>
<#assign softIdentifier = parameters.softIdentifier?default("")>
<#assign sortField = parameters.sortField?if_exists/>
<#assign hideFields = parameters.hideFields?default("N")>
<#-- Only allow the search fields to be hidden when we have some results -->

<#if !requestParameters.ajaxUpdateEvent?exists>
    <@htmlScreenTemplate.renderScreenletBegin id="findPartyScreen" title="${uiLabelMap.PageTitleFindParty}"/>
<form method="post" name="lookupparty" action="<@ofbizUrl>findparty</@ofbizUrl>" class="form-inline" id="lookupparty">
    <input type="hidden" name="lookupFlag" value="Y"/>
    <input type="hidden" name="hideFields" value="Y"/>

    <div class="form-group">
        <div class= "input-group m-b-10 m-r-5"><span class="input-group-addon">${uiLabelMap.PartyContactInformation} </span>
            <div class="input-group-addon">
                <input type="radio" class="radio-inline" name="extInfo" value="N"
                       <#if extInfo == "N">checked="checked"</#if>/>${uiLabelMap.CommonNone}&nbsp;
                <input type="radio" class="radio-inline" name="extInfo" value="P"
                       <#if extInfo == "P">checked="checked"</#if>/>${uiLabelMap.PartyPostal}&nbsp;
                <input type="radio" class="radio-inline" name="extInfo" value="T"
                       <#if extInfo == "T">checked="checked"</#if>/>${uiLabelMap.PartyTelecom}&nbsp;
                <input type="radio" class="radio-inline" name="extInfo" value="O"
                       <#if extInfo == "O">checked="checked"</#if>/>${uiLabelMap.CommonOther}&nbsp;
            </div>
        </div>


        <div class= "input-group m-b-10 m-r-5"><span class="input-group-addon">${uiLabelMap.PartyPartyId}</span>
            <input class="form-control" type="text" name="partyId" value="${parameters.partyId?if_exists}"/></div>


        <div class= "input-group m-b-10 m-r-5"><span class="input-group-addon">${uiLabelMap.PartyUserLogin}</span>
            <input type="text" name="userLoginId" class="form-control" value="${parameters.userLoginId?if_exists}"/></div>


        <div class= "input-group m-b-10 m-r-5"><span class="input-group-addon">${uiLabelMap.PartyLastName}</span>
            <input type="text" class="form-control" name="lastName" value="${parameters.lastName?if_exists}"/></div>


        <div class= "input-group m-b-10 m-r-5"><span class="input-group-addon">${uiLabelMap.PartyFirstName}</span>
            <input type="text" class="form-control" name="firstName" value="${parameters.firstName?if_exists}"/></div>


    <#--<div class= "input-group m-b-10 m-r-5"><span class="input-group-addon">${uiLabelMap.PartyPartyGroupName}</span>
        <input type="text" class="form-control" name="groupName" value="${parameters.groupName?if_exists}"/></div>-->


    <#--<div class= "input-group m-b-10 m-r-5"><span class="input-group-addon">${uiLabelMap.PartyRoleType}</span>

        <select name="roleTypeId" class="form-control">
        <#if currentRole?has_content>
            <option value="${currentRole.roleTypeId}">${currentRole.get("description",locale)}</option>
            <option value="${currentRole.roleTypeId}">---</option>
        </#if>
            <option value="ANY">${uiLabelMap.CommonAnyRoleType}</option>
        <#list roleTypes as roleType>
            <option value="${roleType.roleTypeId}">${roleType.get("description",locale)}</option>
        </#list>
        </select>
    </div>-->


    <#--<div class= "input-group m-b-10 m-r-5"><span class="input-group-addon">${uiLabelMap.PartyType}</span>
        <select name="partyTypeId" class="form-control">
        <#if currentPartyType?has_content>
            <option value="${currentPartyType.partyTypeId}">${currentPartyType.get("description",locale)}</option>
            <option value="${currentPartyType.partyTypeId}">---</option>
        </#if>
            <option value="ANY">${uiLabelMap.CommonAny}</option>
        <#list partyTypes as partyType>
            <option value="${partyType.partyTypeId}">${partyType.get("description",locale)}</option>
        </#list>
        </select>
    </div>-->
        <input type="hidden" name="partyTypeId" value="SUPPLIER"/>

    <#-- <div class= "input-group m-b-10 m-r-5"><span class="input-group-addon">${uiLabelMap.ProductInventoryItemId}</span>
         <input type="text" name="inventoryItemId" value="${parameters.inventoryItemId?if_exists}" class="form-control"/>
     </div>


     <div class= "input-group m-b-10 m-r-5"><span class="input-group-addon">${uiLabelMap.ProductSerialNumber}</span>
         <input type="text" class="form-control" name="serialNumber" value="${parameters.serialNumber?if_exists}"/></div>


     <div class= "input-group m-b-10 m-r-5"><span class="input-group-addon">${uiLabelMap.ProductSoftIdentifier}</span>
         <input type="text" class="form-control" name="softIdentifier" value="${parameters.softIdentifier?if_exists}"/>
     </div>-->

        <#if extInfo == "P">


            <div class= "input-group m-b-10 m-r-5"><span class="input-group-addon">${uiLabelMap.CommonAddress1}</span>
                <input type="text" class="form-control" name="address1" value="${parameters.address1?if_exists}"/></div>


            <div class= "input-group m-b-10 m-r-5"><span class="input-group-addon">${uiLabelMap.CommonAddress2}</span>
                <input type="text" class="form-control" name="address2" value="${parameters.address2?if_exists}"/></div>


            <div class= "input-group m-b-10 m-r-5"><span class="input-group-addon">${uiLabelMap.CommonCity}</span>
                <input type="text" class="form-control" name="city" value="${parameters.city?if_exists}"/></div>


            <div class= "input-group m-b-10 m-r-5"><span class="input-group-addon">${uiLabelMap.CommonStateProvince}</span>

                <select name="stateProvinceGeoId" class="form-control">
                    <#if currentStateGeo?has_content>
                        <option value="${currentStateGeo.geoId}">${currentStateGeo.geoName?default(currentStateGeo.geoId)}</option>
                        <option value="${currentStateGeo.geoId}">---</option>
                    </#if>
                    <option value="ANY">${uiLabelMap.CommonAnyStateProvince}</option>
                ${screens.render("component://common/widget/CommonScreens.xml#states")}
                </select>
            </div>


            <div class= "input-group m-b-10 m-r-5"><span class="input-group-addon">${uiLabelMap.PartyPostalCode}</span>
                <input type="text" class="form-control" name="postalCode" value="${parameters.postalCode?if_exists}"/></div>

        </#if>
        <#if extInfo == "T">
            <div class= "input-group m-b-10 m-r-5"><span class="input-group-addon">${uiLabelMap.PartyCountryCode}</span>
                <input type="text" class="form-control" name="countryCode" value="${parameters.countryCode?if_exists}"/></div>
            <div class= "input-group m-b-10 m-r-5"><span class="input-group-addon">${uiLabelMap.PartyAreaCode}</span>
                <input type="text" class="form-control" name="areaCode" value="${parameters.areaCode?if_exists}"/></div>
            <div class= "input-group m-b-10 m-r-5"><span class="input-group-addon">${uiLabelMap.PartyContactNumber}</span>
                <input type="text" class="form-control" name="contactNumber" value="${parameters.contactNumber?if_exists}"/></div>
        </#if>
        <#if extInfo == "O">
            <div class= "input-group m-b-10 m-r-5"><span class="input-group-addon">${uiLabelMap.PartyContactInformation}</span>
                <input type="text" class="form-control" name="infoString" value="${parameters.infoString?if_exists}"/></div>

        </#if>
        <input type="hidden" name="ajaxUpdateEvent" value="Y"/>
        <input type="button" value="${uiLabelMap.CommonFind}" class="btn btn-primary btn-sm m-b-10"
               onclick="ajaxSubmitFormUpdateAreas('lookupparty','ajax,search-results,,')"/>
    </div>
</form>
<script language="JavaScript" type="text/javascript">
    document.lookupparty.partyId.focus();
</script>
</#if>
<#if partyList?exists>
    <#if !requestParameters.ajaxUpdateEvent?exists>
    <hr/>
    <#-- <#if (parameters.firstName?has_content || parameters.lastName?has_content)>
         <#assign createUrl = "editperson?create_new=Y&amp;lastName=${parameters.lastName?if_exists}&amp;firstName=${parameters.firstName?if_exists}"/>
     <#elseif (parameters.groupName?has_content)>
         <#assign createUrl = "editpartygroup?create_new=Y&amp;groupName=${parameters.groupName?if_exists}"/>
     <#else>
         <#assign createUrl = "createnew"/>
     </#if>-->
    <div class="button-bar"><a href="<@ofbizUrl>NewSupplier</@ofbizUrl>" class="btn btn-primary btn-sm">${uiLabelMap.CommonCreateNew}</a></div>
    </#if>
<div class="table-responsive" id="search-results">
    <#if partyList?has_content>
    <#-- Pagination -->
        <#include "component://common/webcommon/includes/htmlTemplate.ftl"/>
        <#assign commonUrl = "findparty?hideFields=" + hideFields + paramList + "&sortField=" + sortField?if_exists + "&"/>
        <#assign viewIndexFirst = 0/>
        <#assign viewIndexPrevious = viewIndex - 1/>
        <#assign viewIndexNext = viewIndex + 1/>
        <#assign viewIndexLast = Static["org.ofbiz.base.util.UtilMisc"].getViewLastIndex(partyListSize, viewSize) />
        <#assign messageMap = Static["org.ofbiz.base.util.UtilMisc"].toMap("lowCount", lowIndex, "highCount", highIndex, "total", partyListSize)/>
        <#assign commonDisplaying = Static["org.ofbiz.base.util.UtilProperties"].getMessage("CommonUiLabels", "CommonDisplaying", messageMap, locale)/>

        <table class="table table-bordered table-striped" cellspacing="0">
            <thead>
            <tr class="header-row-2">
                <th>${uiLabelMap.PartyPartyId}</th>
                <th>${uiLabelMap.PartyUserLogin}</th>
                <th>${uiLabelMap.PartyName}</th>
                <#if extInfo?default("") == "P" >
                    <th>${uiLabelMap.PartyCity}</th>
                </#if>
                <#if extInfo?default("") == "P">
                    <th>${uiLabelMap.PartyPostalCode}</th>
                </#if>
                <#if extInfo?default("") == "T">
                    <th>${uiLabelMap.PartyAreaCode}</th>
                </#if>
                <#if inventoryItemId?default("") != "">
                    <th>${uiLabelMap.ProductInventoryItemId}</th>
                </#if>
                <#if serialNumber?default("") != "">
                    <th>${uiLabelMap.ProductSerialNumber}</th>
                </#if>
                <#if softIdentifier?default("") != "">
                    <th>${uiLabelMap.ProductSoftIdentifier}</th>
                </#if>
                <th>${uiLabelMap.PartyRelatedCompany}</th>
                <th>${uiLabelMap.PartyType}</th>
                <th>${uiLabelMap.PartyMainRole}</th>
                <th>
                    <a href="<@ofbizUrl>findparty</@ofbizUrl>?<#if sortField?has_content><#if sortField == "createdDate">sortField=-createdDate<#elseif sortField == "-createdDate">sortField=createdDate<#else>sortField=createdDate</#if><#else>sortField=createdDate</#if>${paramList?if_exists}&viewSize=${viewSize?if_exists}&viewIndex=${viewIndex?if_exists}"
                        <#if sortField?has_content><#if sortField == "createdDate">class="sort-order-desc"
                       <#elseif sortField == "-createdDate">class="sort-order-asc"
                       <#else>class="sort-order"</#if><#else>class="sort-order"</#if>>${uiLabelMap.FormFieldTitle_createdDate}
                    </a>
                </th>
                <th>
                    <a href="<@ofbizUrl>findparty</@ofbizUrl>?<#if sortField?has_content><#if sortField == "lastModifiedDate">sortField=-lastModifiedDate<#elseif sortField == "-lastModifiedDate">sortField=lastModifiedDate<#else>sortField=lastModifiedDate</#if><#else>sortField=lastModifiedDate</#if>${paramList?if_exists}&viewSize=${viewSize?if_exists}&viewIndex=${viewIndex?if_exists}"
                        <#if sortField?has_content><#if sortField == "lastModifiedDate">class="sort-order-desc"
                       <#elseif sortField == "-lastModifiedDate">class="sort-order-asc"
                       <#else>class="sort-order"</#if><#else>class="sort-order"</#if>>${uiLabelMap.FormFieldTitle_lastModifiedDate}
                    </a>
                </th>
                <th>&nbsp;</th>
            </tr>
            </thead>
            <#assign alt_row = false>
            <#assign rowCount = 0>
            <#list partyList as partyRow>
                <#assign partyType = partyRow.getRelatedOne("PartyType")?if_exists>
                <tr valign="middle"<#if alt_row> class="alternate-row"</#if>>
                    <td>
                        <a href="<@ofbizUrl>viewprofile?partyId=${partyRow.partyId}</@ofbizUrl>">${partyRow.partyId}</a>
                    </td>
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
                        <td><#if partyType.description?exists>${partyType.get("description", locale)}<#else>
                            ???</#if></td>
                    <#else>
                        <td></td>
                        <td></td>
                    </#if>
                    <td>
                        <#assign mainRole = dispatcher.runSync("getPartyMainRole", Static["org.ofbiz.base.util.UtilMisc"].toMap("partyId", partyRow.partyId, "userLogin", userLogin))/>
              ${mainRole.description?if_exists}
                    </td>
                    <#assign partyDate = delegator.findOne("Party", {"partyId":partyRow.partyId}, true)/>
                    <td>${partyDate.createdDate?if_exists}</td>
                    <td>${partyDate.lastModifiedDate?if_exists}</td>
                    <td class="button-col align-float">
                        <a class="btn btn-primary btn-sm" href="<@ofbizUrl>viewprofile?partyId=${partyRow.partyId}</@ofbizUrl>">${uiLabelMap.CommonDetails}</a>
                    <#--  <#if security.hasEntityPermission("ORDERMGR", "_VIEW", session)>
                          <form name="searchorders_o_${rowCount}" method="post"
                                action="/ordermgr/control/searchorders">
                              <input type="hidden" name="lookupFlag" value="Y"/>
                              <input type="hidden" name="hideFields" value="Y"/>
                              <input type="hidden" name="partyId" value="${partyRow.partyId}"/>
                              <input type="hidden" name="viewIndex" value="1"/>
                              <input type="hidden" name="viewSize" value="20"/>
                              <a href="javascript:document.searchorders_o_${rowCount}.submit()">${uiLabelMap.OrderOrders}</a>
                          </form>
                          <a href="/ordermgr/control/FindQuote?partyId=${partyRow.partyId + externalKeyParam}">${uiLabelMap.OrderOrderQuotes}</a>
                      </#if>-->
                    <#-- <#if security.hasEntityPermission("ORDERMGR", "_CREATE", session)>
                         <a href="/ordermgr/control/checkinits?partyId=${partyRow.partyId + externalKeyParam}">${uiLabelMap.OrderNewOrder}</a>
                         <a href="/ordermgr/control/EditQuote?partyId=${partyRow.partyId + externalKeyParam}">${uiLabelMap.OrderNewQuote}</a>
                     </#if>-->
                        <@htmlScreenTemplate.renderConfirmField id="partyDelete_${partyRow_index}" name="partyDelete_${partyRow_index}"
                        confirmTitle="删除供应商" confirmMessage="确定删除该供应商" targetParameterIter="partyId:'${partyRow.partyId}'"
                        confirmUrl="deleteParty" description="删除"/>

                    </td>
                </tr>
                <#assign rowCount = rowCount + 1>
            <#-- toggle the row color -->
                <#assign alt_row = !alt_row>
            </#list>
        </table>
        <@nextPrev1 commonUrl=commonUrl ajaxEnabled=true javaScriptEnabled=false paginateStyle="nav-pager" paginateFirstStyle="nav-first" viewIndex=viewIndex highIndex=highIndex listSize=partyListSize viewSize=viewSize ajaxFirstUrl="" firstUrl="" paginateFirstLabel="" paginatePreviousStyle="nav-previous" ajaxPreviousUrl="" previousUrl="" paginatePreviousLabel="" pageLabel="" ajaxSelectUrl="" selectUrl="" ajaxSelectSizeUrl="" selectSizeUrl="" commonDisplaying=commonDisplaying paginateNextStyle="nav-next" ajaxNextUrl="" nextUrl="" paginateNextLabel="" paginateLastStyle="nav-last" ajaxLastUrl="" lastUrl="" paginateLastLabel="" paginateViewSizeLabel="" />

    <#else>
        <div id="findPartyResults_2">
            <h3>${uiLabelMap.SupplierNoPartiesFound}</h3>
        </div>
    </#if>
</div>
    <#if lookupErrorMessage?exists>
    <h3>${lookupErrorMessage}</h3>
    </#if>

</#if>
<#if !requestParameters.ajaxUpdateEvent?exists>
    <@htmlScreenTemplate.renderScreenletEnd/>
</#if>

