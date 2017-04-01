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

<#if productIds?has_content>
<div class="panel panel-default">
    <div class="panel-heading">${uiLabelMap.OrderProductsForPromotion}"</div>
    <div class="panel-body">
        <#if (listSize > 0)>
            <div class="table-responsive">
                <table class="table table-bordered">
                    <tr>
                        <td align="right">
                    <span>
                    <b>
                        <#if (viewIndex > 0)>
                            <a href="<@ofbizUrl>showPromotionDetails?productPromoId=${productPromoId?if_exists}&amp;VIEW_SIZE=${viewSize}&amp;VIEW_INDEX=${viewIndex-1}</@ofbizUrl>"
                               class="buttontext">${uiLabelMap.CommonPrevious}</a> |
                        </#if>
                    ${lowIndex+1} - ${highIndex} ${uiLabelMap.CommonOf} ${listSize}
                        <#if (listSize > highIndex)>
                            | <a
                                href="<@ofbizUrl>showPromotionDetails?productPromoId=${productPromoId?if_exists}&amp;VIEW_SIZE=${viewSize}&amp;VIEW_INDEX=${viewIndex+1}</@ofbizUrl>"
                                class="buttontext">${uiLabelMap.CommonNext}</a>
                        </#if>
                    </b>
                    </span>
                        </td>
                    </tr>
                </table>
            </div>

        </#if>
        <div class="table-responsive">
            <table class="table table-bordered">
            <#--<tr>
                <td>
                    <div>${uiLabelMap.CommonQualifier}</div>
                </td>
                <td>
                    <div>${uiLabelMap.CommonBenefit}</div>
                </td>
                <td>
                    <div>&nbsp;</div>
                </td>
            </tr>-->
                <#if (listSize > 0)>
                    <#list productIds[lowIndex..highIndex-1] as productId>
                        <tr>
                        <#--  <td>
                              <div>[<#if productIdsCond.contains(productId)>x<#else>&nbsp;</#if>]</div>
                          </td>
                          <td>
                              <div>[<#if productIdsAction.contains(productId)>x<#else>&nbsp;</#if>]</div>
                          </td>-->
                            <td>
                            ${setRequestAttribute("optProductId", productId)}
                      ${setRequestAttribute("listIndex", productId_index)}
                      ${screens.render(productsummaryScreen)}
                            </td>
                        </tr>
                    </#list>
                </#if>
            </table>
        </div>
    </div>
</div>

</#if>
