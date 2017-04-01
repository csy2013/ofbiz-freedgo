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
<#if activeOnly>
<a href="<@ofbizUrl>EditCategoryProducts?productCategoryId=${productCategoryId?if_exists}&amp;activeOnly=false</@ofbizUrl>"
   class="buttontext">${uiLabelMap.ProductActiveAndInactive}</a>
<#else>
<a href="<@ofbizUrl>EditCategoryProducts?productCategoryId=${productCategoryId?if_exists}&amp;activeOnly=true</@ofbizUrl>"
   class="buttontext">${uiLabelMap.ProductActiveOnly}</a>
</#if>

<div class="am-cf am-padding-xs">
    <div class="am-panel am-panel-default">
        <div class="am-panel-hd am-cf"> ${uiLabelMap.PageTitleEditCategoryProducts}
        <#if (listSize > 0)>
            <span style="margin-left: 60px">
                <#if (viewIndex > 1)>
                    <a href="<@ofbizUrl>EditCategoryProducts?productCategoryId=${productCategoryId?if_exists}&amp;VIEW_SIZE=${viewSize}&amp;VIEW_INDEX=${viewIndex-1}&amp;activeOnly=${activeOnly.toString()}</@ofbizUrl>"
                       class="submenutext">${uiLabelMap.CommonPrevious}</a> |
                </#if>
                <span class="submenutextinfo">${lowIndex}
                    - ${highIndex} ${uiLabelMap.CommonOf} ${listSize}</span>
                <#if (listSize > highIndex)>
                    | <a class="lightbuttontext"
                         href="<@ofbizUrl>EditCategoryProducts?productCategoryId=${productCategoryId?if_exists}&amp;VIEW_SIZE=${viewSize}&amp;VIEW_INDEX=${viewIndex+1}&amp;activeOnly=${activeOnly.toString()}</@ofbizUrl>"
                         class="submenutextright">${uiLabelMap.CommonNext}</a>
                </#if>
                </span>
        </#if>
        </div>
        <div class="am-panel-bd am-collapse am-in">
            <div class="am-g">
                <div class="am-u-lg-12">
                <#if (listSize == 0)>
                    <table class="am-table am-table-striped am-table-hover table-main">
                        <tr class="header-row">
                            <td>${uiLabelMap.ProductProductNameId}</td>
                            <td>${uiLabelMap.CommonFromDateTime}</td>
                            <td>${uiLabelMap.ProductThruDateTimeSequenceQuantity} ${uiLabelMap.CommonComments}</td>
                            <td>&nbsp;</td>
                        </tr>
                    </table>
                <#else>
                    <form method="post" action="<@ofbizUrl>updateCategoryProductMember</@ofbizUrl>"
                          name="updateCategoryProductForm">
                        <input type="hidden" name="VIEW_SIZE" value="${viewSize}"/>
                        <input type="hidden" name="VIEW_INDEX" value="${viewIndex}"/>
                        <input type="hidden" name="activeOnly" value="${activeOnly.toString()}"/>
                        <input type="hidden" name="productCategoryId" value="${productCategoryId?if_exists}"/>
                        <table class="am-table am-table-striped am-table-hover table-main">
                            <tr>
                                <td>${uiLabelMap.ProductProductNameId}</td>
                                <td>${uiLabelMap.CommonFromDateTime}</td>
                                <td>
                                    &nbsp;&nbsp;&nbsp;&nbsp;${uiLabelMap.ProductThruDateTimeSequenceQuantity} ${uiLabelMap.CommonComments}</td>
                                <td>&nbsp;</td>
                            </tr>
                            <#assign rowClass = "2">
                            <#assign rowCount = 0>
                            <#list productCategoryMembers as productCategoryMember>
                                <#assign suffix = "_o_" + productCategoryMember_index>
                                <#assign product = productCategoryMember.getRelatedOne("Product")>
                                <#assign hasntStarted = false>
                                <#if productCategoryMember.fromDate?exists && nowTimestamp.before(productCategoryMember.getTimestamp("fromDate"))><#assign hasntStarted = true></#if>
                                <#assign hasExpired = false>
                                <#if productCategoryMember.thruDate?exists && nowTimestamp.after(productCategoryMember.getTimestamp("thruDate"))><#assign hasExpired = true></#if>
                                <tr <#if rowClass == "1"> class="am-active"</#if>>
                                    <td>
                                        <#if (product.smallImageUrl)?exists>
                                            <a href="<@ofbizUrl>EditProduct?productId=${(productCategoryMember.productId)?if_exists}</@ofbizUrl>"><img
                                                    alt="Small Image"
                                                    src="<@ofbizContentUrl>${product.smallImageUrl}</@ofbizContentUrl>"
                                                    class="cssImgSmall" align="middle"/></a>
                                        </#if>
                                        <a href="<@ofbizUrl>EditProduct?productId=${(productCategoryMember.productId)?if_exists}</@ofbizUrl>"
                                           class="buttontext"><#if product?exists>${(product.internalName)?if_exists}</#if>
                                            [${(productCategoryMember.productId)?if_exists}]</a>
                                    </td>
                                    <td <#if hasntStarted>
                                            style="color: red;"</#if>>${(productCategoryMember.fromDate)?if_exists}</td>
                                    <td>
                                        <input type="hidden" name="productId${suffix}"
                                               value="${(productCategoryMember.productId)?if_exists}"/>
                                        <input type="hidden" name="productCategoryId${suffix}"
                                               value="${(productCategoryMember.productCategoryId)?if_exists}"/>
                                        <input type="hidden" name="fromDate${suffix}"
                                               value="${(productCategoryMember.fromDate)?if_exists}"/>
                                        <#if hasExpired><#assign class="alert"></#if>
                                        <div class="am-u-lg-3">
                                            <@amazeHtmlTemplate.renderDateTimeField name="thruDate${suffix}" event="" action="" className="${class!''}" alert="" title="Format: yyyy-MM-dd HH:mm:ss.SSS" value="${(productCategoryMember.thruDate)?if_exists}" size="25" maxlength="30" id="thruDate${suffix}" dateType="date" shortDateInput=false timeDropdownParamName="" defaultDateTimeString="" localizedIconTitle="" timeDropdown="" timeHourName="" classString="" hour1="" hour2="" timeMinutesName="" minutes="" isTwelveHour="" ampmName="" amSelected="" pmSelected="" compositeType="" formName=""/>
                                        </div>
                                        <div class="am-u-lg-1">
                                            <input type="text" class="am-form-field am-input-sm" size="5"
                                                   name="sequenceNum${suffix}"
                                                   value="${(productCategoryMember.sequenceNum)?if_exists}"/>
                                        </div>
                                        <div class="am-u-lg-2">
                                            <input type="text" class="am-form-field am-input-sm" size="5"
                                                   name="quantity${suffix}"
                                                   value="${(productCategoryMember.quantity)?if_exists}"/>
                                        </div>
                                        <div class="am-u-lg-3 am-u-end">
                                            <textarea class="am-form-field am-input-sm" name="comments${suffix}"
                                                      rows="2"
                                                      cols="40">${(productCategoryMember.comments)?if_exists}</textarea>
                                        </div>
                                        <div class="am-u-lg-2 am-u-end">
                                            <input type="submit" value="${uiLabelMap.CommonUpdate}"
                                                   class="am-btn am-btn-primary am-btn-sm"/>
                                            <input type="hidden" value="${productCategoryMembers.size()}"
                                                   name="_rowCount"/>
                                        </div>
                                    </td>
                                    <td align="center">
                                        <a href="javascript:document.deleteProductFromCategory_o_${rowCount}.submit()"
                                           class="am-btn am-btn-primary am-btn-sm">${uiLabelMap.CommonDelete}</a>
                                    </td>
                                </tr>
                            </#list>
                        </table>
                    </form>
                    <#assign rowCount = 0>
                    <#list productCategoryMembers as productCategoryMember>
                        <form name="deleteProductFromCategory_o_${rowCount}" method="post"
                              action="<@ofbizUrl>removeCategoryProductMember</@ofbizUrl>">
                            <input type="hidden" name="VIEW_SIZE" value="${viewSize}"/>
                            <input type="hidden" name="VIEW_INDEX" value="${viewIndex}"/>
                            <input type="hidden" name="productId"
                                   value="${(productCategoryMember.productId)?if_exists}"/>
                            <input type="hidden" name="productCategoryId"
                                   value="${(productCategoryMember.productCategoryId)?if_exists}"/>
                            <input type="hidden" name="fromDate" value="${(productCategoryMember.fromDate)?if_exists}"/>
                            <input type="hidden" name="activeOnly" value="${activeOnly.toString()}"/>
                        </form>
                        <#assign rowCount = rowCount + 1>
                    </#list>
                </#if>
                </div>
            </div>
        </div>
    </div>
</div>


<div class="am-cf am-padding-xs">
    <div class="am-panel am-panel-default">
        <div class="am-panel-hd am-cf">${uiLabelMap.ProductAddProductCategoryMember}:</div>
        <div class="am-panel-bd am-collapse am-in">
            <div class="am-g">
                <div class="am-u-lg-12">
                    <form method="post" action="<@ofbizUrl>addCategoryProductMember</@ofbizUrl>" class="am-form am-form-horizontal"
                          name="addProductCategoryMemberForm">
                        <input type="hidden" name="productCategoryId" value="${productCategoryId?if_exists}"/>
                        <input type="hidden" name="activeOnly" value="${activeOnly.toString()}"/>


                        <div class="am-form-group am-g">
                            <label class="am-control-label  am-u-lg-2">${uiLabelMap.ProductProductId}</label>
                            <div class="am-u-md-2 am-u-lg-2 am-u-end">
                            <@amazeHtmlTemplate.lookupField formName="addProductCategoryMemberForm" name="productId" id="productId" fieldFormName="LookupProduct"/>
                            </div>
                        </div>

                        <div class="am-form-group am-g">
                            <label class="am-control-label  am-u-lg-2">${uiLabelMap.CommonFromDate}</label>
                            <div class="am-u-md-2 am-u-lg-2 am-u-end">
                            <@amazeHtmlTemplate.renderDateTimeField name="fromDate" event="" action="" className="" alert="" title="Format: yyyy-MM-dd HH:mm:ss.SSS" value="" size="25" maxlength="30" id="fromDate1" dateType="date" shortDateInput=false timeDropdownParamName="" defaultDateTimeString="" localizedIconTitle="" timeDropdown="" timeHourName="" classString="" hour1="" hour2="" timeMinutesName="" minutes="" isTwelveHour="" ampmName="" amSelected="" pmSelected="" compositeType="" formName=""/>
                            </div>${uiLabelMap.CommonRequired}
                        <#--<input type="submit" class="am-btn am-btn-primary am-btn-sm" value="${uiLabelMap.CommonAdd}"/>-->
                        </div>

                        <div class="am-form-group am-g">
                            <label class="am-control-label  am-u-lg-2">${uiLabelMap.CommonComments}</label>
                            <div class="am-u-md-2 am-u-lg-2 am-u-end">
                                <textarea  name="comments" rows="2" cols="40"></textarea>
                            </div>
                            <input type="submit" class="am-btn am-btn-primary am-btn-sm" value="${uiLabelMap.CommonAdd}"/>
                        </div>
                    </form>
                </div>
            </div>
        </div>
    </div>
</div>


<div class="am-cf am-padding-xs">
    <div class="am-panel am-panel-default">
        <div class="am-panel-hd am-cf">${uiLabelMap.ProductCopyProductCategoryMembersToAnotherCategory}:</div>
        <div class="am-panel-bd am-collapse am-in">
            <div class="am-g">
                <div class="am-u-lg-12">
                    <form method="post" action="<@ofbizUrl>copyCategoryProductMembers</@ofbizUrl>" class="am-form am-form-horizontal"
                          name="copyCategoryProductMembersForm">
                        <input type="hidden" name="productCategoryId" value="${productCategoryId?if_exists}"/>
                        <input type="hidden" name="activeOnly" value="${activeOnly.toString()}"/>


                        <div class="am-form-group am-g">
                            <label class="am-control-label  am-u-lg-2">${uiLabelMap.ProductTargetProductCategory}</label>
                            <div class="am-u-md-2 am-u-lg-2 am-u-end">
                            <@amazeHtmlTemplate.lookupField formName="copyCategoryProductMembersForm" name="productCategoryIdTo" id="productCategoryIdTo" fieldFormName="LookupProductCategory"/>
                            </div>
                        </div>

                        <div class="am-form-group am-g">
                            <label class="am-control-label  am-u-lg-2">${uiLabelMap.ProductOptionalFilterWithDate}</label>
                            <div class="am-u-md-2 am-u-lg-2 am-u-end">
                            <@amazeHtmlTemplate.renderDateTimeField name="validDate" event="" action="" className="" alert="" title="Format: yyyy-MM-dd HH:mm:ss.SSS" value="" size="25" maxlength="30" id="validDate1" dateType="date" shortDateInput=false timeDropdownParamName="" defaultDateTimeString="" localizedIconTitle="" timeDropdown="" timeHourName="" classString="" hour1="" hour2="" timeMinutesName="" minutes="" isTwelveHour="" ampmName="" amSelected="" pmSelected="" compositeType="" formName=""/>
                            </div>
                            <#--<input type="submit" class="am-btn am-btn-primary am-btn-sm" value="${uiLabelMap.CommonAdd}"/>-->
                        </div>

                        <div class="am-form-group am-g">
                            <label class="am-control-label  am-u-lg-2">${uiLabelMap.ProductOptionalFilterWithDate}</label>
                            <div class="am-u-md-2 am-u-lg-2 am-u-end">
                                <select name="recurse">
                                    <option value="N">${uiLabelMap.CommonN}</option>
                                    <option value="Y">${uiLabelMap.CommonY}</option>
                                </select>
                            </div>
                            <input type="submit" class="am-btn am-btn-primary am-btn-sm" value="${uiLabelMap.CommonCopy}"/>
                        </div>
                    </form>
                </div>
            </div>
        </div>
    </div>
</div>


<div class="am-cf am-padding-xs">
    <div class="am-panel am-panel-default">
        <div class="am-panel-hd am-cf">${uiLabelMap.ProductExpireAllProductMembers}:</div>
        <div class="am-panel-bd am-collapse am-in">
            <div class="am-g">
                <div class="am-u-lg-12">
                    <form method="post" action="<@ofbizUrl>expireAllCategoryProductMembers</@ofbizUrl>" class="am-form am-form-horizontal"
                          name="expireAllCategoryProductMembersForm">
                        <input type="hidden" name="productCategoryId" value="${productCategoryId?if_exists}"/>
                        <input type="hidden" name="activeOnly" value="${activeOnly.toString()}"/>
                        <div class="am-form-group am-g">
                            <label class="am-control-label  am-u-lg-2">${uiLabelMap.ProductOptionalExpirationDate}</label>
                            <div class="am-u-md-2 am-u-lg-2 am-u-end">
                            <@amazeHtmlTemplate.renderDateTimeField name="thruDate" event="" action="" className="" alert="" title="Format: yyyy-MM-dd HH:mm:ss.SSS" value="" size="25" maxlength="30" id="thruDate2" dateType="date" shortDateInput=false timeDropdownParamName="" defaultDateTimeString="" localizedIconTitle="" timeDropdown="" timeHourName="" classString="" hour1="" hour2="" timeMinutesName="" minutes="" isTwelveHour="" ampmName="" amSelected="" pmSelected="" compositeType="" formName=""/>
                            </div>
                            <input type="submit" class="am-btn am-btn-primary am-btn-sm" value="${uiLabelMap.CommonExpireAll}"/>
                        </div>
                    </form>
                </div>
            </div>
        </div>
    </div>
</div>

<div class="am-cf am-padding-xs">
    <div class="am-panel am-panel-default">
        <div class="am-panel-hd am-cf">${uiLabelMap.ProductRemoveExpiredProductMembers}:</div>
        <div class="am-panel-bd am-collapse am-in">
            <div class="am-g">
                <div class="am-u-lg-12">
                    <form method="post" action="<@ofbizUrl>removeExpiredCategoryProductMembers</@ofbizUrl>" class="am-form am-form-horizontal"
                          name="removeExpiredCategoryProductMembersForm">
                        <input type="hidden" name="productCategoryId" value="${productCategoryId?if_exists}"/>
                        <input type="hidden" name="activeOnly" value="${activeOnly.toString()}"/>
                        <div class="am-form-group am-g">
                            <label class="am-control-label  am-u-lg-2">${uiLabelMap.ProductOptionalExpiredBeforeDate}</label>
                            <div class="am-u-md-2 am-u-lg-2 am-u-end">
                            <@amazeHtmlTemplate.renderDateTimeField name="validDate" event="" action="" className="" alert="" title="Format: yyyy-MM-dd HH:mm:ss.SSS" value="" size="25" maxlength="30" id="validDate2" dateType="date" shortDateInput=false timeDropdownParamName="" defaultDateTimeString="" localizedIconTitle="" timeDropdown="" timeHourName="" classString="" hour1="" hour2="" timeMinutesName="" minutes="" isTwelveHour="" ampmName="" amSelected="" pmSelected="" compositeType="" formName=""/>
                            </div>
                            <input type="submit" class="am-btn am-btn-primary am-btn-sm" value="${uiLabelMap.CommonRemoveExpired}"/>
                        </div>
                    </form>
                </div>
            </div>
        </div>
    </div>
</div>



