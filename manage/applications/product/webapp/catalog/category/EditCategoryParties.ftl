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

<#if productCategoryId?exists && productCategory?exists>
<div class="screenlet">
    <div class="screenlet-title-bar">
        <h3>${uiLabelMap.PageTitleEditCategoryParties}</h3>
    </div>
    <div class="screenlet-body">
        <table cellspacing="0" class="basic-table">
            <tr class="header-row">
                <td>${uiLabelMap.PartyPartyId}</td>
                <td>${uiLabelMap.PartyRole}</td>
                <td>${uiLabelMap.CommonFromDateTime}</td>
                <td align="center">${uiLabelMap.CommonThruDateTime}</td>
                <td>&nbsp;</td>
            </tr>
            <#assign line = 0>
            <#assign rowClass = "2">

        </table>
    </div>
</div>
<div class="screenlet">
    <div class="screenlet-title-bar">
        <h3>${uiLabelMap.ProductAssociatePartyToCategory}</h3>
    </div>
    <div class="screenlet-body">
        <table cellspacing="0" class="basic-table">
            <tr>
                <td>
                    <form method="post" action="<@ofbizUrl>addPartyToCategory</@ofbizUrl>" style="margin: 0;" name="addNewForm">
                        <input type="hidden" name="productCategoryId" value="${productCategoryId}"/>
                        <input type="text" size="20" maxlength="20" name="partyId" value=""/>
                        <select name="roleTypeId" size="1">
                            <#list roleTypes as roleType>
                                <option value="${(roleType.roleTypeId)?if_exists}" <#if roleType.roleTypeId.equals("_NA_")>
                                        selected="selected"</#if>>${(roleType.get("description",locale))?if_exists}</option>
                            </#list>
                        </select>

                        <@htmlTemplate.renderDateTimeField name="fromDate" event="" action="" className="" alert="" title="Format: yyyy-MM-dd HH:mm:ss.SSS" value="" size="25" maxlength="30" id="fromDate_1" dateType="date" shortDateInput=false timeDropdownParamName="" defaultDateTimeString="" localizedIconTitle="" timeDropdown="" timeHourName="" classString="" hour1="" hour2="" timeMinutesName="" minutes="" isTwelveHour="" ampmName="" amSelected="" pmSelected="" compositeType="" formName=""/>
                        <input type="submit" value="${uiLabelMap.CommonAdd}"/>
                    </form>
                </td>
            </tr>
        </table>
    </div>
</div>
</#if>
