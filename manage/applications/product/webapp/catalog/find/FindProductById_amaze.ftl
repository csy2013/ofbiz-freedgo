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


<div class="screenlet">
    <div class="screenlet-body">
        <div class="am-cf am-padding-xs">
            <div class="am-panel am-panel-default">
                <div class="am-panel-hd am-cf">${uiLabelMap.ProductFindProductWithIdValue}</div>
                <div class="am-panel-bd am-collapse am-in">
                    <form name="idsearchform" method="post" action="<@ofbizUrl>FindProductById</@ofbizUrl>"
                          class="am-form am-form-horizontal">
                        <div class="am-form-group am-g">
                            <label class="am-control-label am-u-md-3 am-u-lg-3">${uiLabelMap.CommonId} ${uiLabelMap.CommonValue}:</label>
                            <div class="am-u-md-7 am-u-lg-7 am-u-end">
                                <div class="am-form-group am-u-md-6 am-u-lg-6">
                                    <input type="text" class="am-form-field am-input-sm" name="idValue" size="20"
                                           maxlength="50"
                                           value="${idValue?if_exists}"/>
                                </div>
                                <input type="submit" value="${uiLabelMap.CommonFind}"
                                       class="am-btn am-btn-primary am-btn-sm"/>
                            </div>
                        </div>
                    </form>
                </div>
            </div>
        </div>
        <div class="am-cf am-padding-xs">
            <div class="am-panel am-panel-default">
                <div class="am-panel-hd am-cf">${uiLabelMap.ProductSearchResultsWithIdValue}: ${idValue?if_exists}</div>
            <#if !goodIdentifications?has_content && !idProduct?has_content>
                &nbsp;${uiLabelMap.ProductNoResultsFound}.
            <#else>
                <table cellspacing="0" class="basic-table">
                    <#assign rowClass = "1">
                    <#if idProduct?has_content>
                        <tr valign="middle"<#if rowClass == "1"> class="alternate-row"</#if>>
                            <td>
                            ${idProduct.productId}
                            </td>
                            <td>&nbsp;&nbsp;</td>
                            <td>
                                <a href="<@ofbizUrl>EditProduct?productId=${idProduct.productId}</@ofbizUrl>"
                                   class="buttontext">${(idProduct.internalName)?if_exists}</a>
                                (${uiLabelMap.ProductSearchResultsFound})
                            </td>
                        </tr>
                    </#if>
                    <#list goodIdentifications as goodIdentification>
                    <#-- toggle the row color -->
                        <#if rowClass == "2">
                            <#assign rowClass = "1">
                        <#else>
                            <#assign rowClass = "2">
                        </#if>
                        <#assign product = goodIdentification.getRelatedOneCache("Product")/>
                        <#assign goodIdentificationType = goodIdentification.getRelatedOneCache("GoodIdentificationType")/>
                        <tr valign="middle"<#if rowClass == "1"> class="alternate-row"</#if>>
                            <td>
                            ${product.productId}
                            </td>
                            <td>&nbsp;&nbsp;</td>
                            <td>
                                <a href="<@ofbizUrl>EditProduct?productId=${product.productId}</@ofbizUrl>"
                                   class="buttontext">${(product.internalName)?if_exists}</a>
                                (${uiLabelMap.ProductSearchResultsFound} ${goodIdentificationType.get("description",locale)?default(goodIdentification.goodIdentificationTypeId)}
                                .)
                            </td>
                        </tr>
                    </#list>
                </table>
            </#if>
            </div>
        </div>
    </div>
</div>
