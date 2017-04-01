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

<#assign productFeaturesByTypeMap = Static["org.ofbiz.product.feature.ParametricSearch"].makeCategoryFeatureLists(productCategoryId, delegator)>

<#if productCategoryId?has_content>
<a href="<@ofbizUrl>EditCategory?productCategoryId=${productCategoryId}</@ofbizUrl>"
   class="buttontext">[${uiLabelMap.ProductBackToEditCategory}]</a>
</#if>
<div class="am-cf am-padding-xs">
    <div class="am-panel am-panel-default">
        <div class="am-panel-hd am-cf">&nbsp;</div>
        <div class="am-panel-bd am-collapse am-in">
            <div class="am-g">
                <div class="am-u-lg-12">
                    <form name="createProductInCategoryCheckExistingForm" method="post" class="am-form am-form-horizontal"
                          action="<@ofbizUrl>CreateProductInCategoryCheckExisting</@ofbizUrl>">
                        <input type="hidden" name="productCategoryId" value="${productCategoryId}" />
                        <#list productFeaturesByTypeMap.keySet() as productFeatureTypeId>
                            <#assign findPftMap = Static["org.ofbiz.base.util.UtilMisc"].toMap("productFeatureTypeId", productFeatureTypeId)>
                            <#assign productFeatureType = delegator.findByPrimaryKeyCache("ProductFeatureType", findPftMap)>
                            <#assign productFeatures = productFeaturesByTypeMap[productFeatureTypeId]>
                            <#--<tr>
                                <td width="15%">${productFeatureType.description}:</td>
                                <td>
                                    <div>
                                        <select name="pft_${productFeatureTypeId}">
                                            <option value="">- ${uiLabelMap.CommonNone} -</option>
                                            <#list productFeatures as productFeature>
                                                <option value="${productFeature.productFeatureId}">${productFeature.description}</option>
                                            </#list>
                                        </select>
                                        <input type="checkbox" name="pftsel_${productFeatureTypeId}"/>${uiLabelMap.ProductSelectable}
                                    </div>
                                </td>
                            </tr>-->
                            <div class="am-form-group am-g">
                                <label class="am-control-label am-u-md-5 am-u-lg-5 am-u-end">${productFeatureType.description}:</label>
                                <div class="am-u-md-7 am-u-lg-7 am-u-end">
                                    <div class="am-input-group">
                                        <select name="pft_${productFeatureTypeId}" data-am-selected = "{btnWidth:80, btnStyle:'default'}" id="" size="1">
                                            <option value="">- ${uiLabelMap.CommonNone} -</option>
                                            <#list productFeatures as productFeature>
                                                <option value="${productFeature.productFeatureId}">${productFeature.description}</option>
                                            </#list>
                                        </select>
                                        <input type="checkbox" name="pftsel_${productFeatureTypeId}"/>${uiLabelMap.ProductSelectable}
                                    </div>
                                </div>
                            </div>
                        </#list>
                        <div class="am-form-group am-g">
                            <label class="am-control-label am-u-md-5 am-u-lg-5">${uiLabelMap.ProductInternalName}:</label>
                            <div class="am-u-md-7 am-u-lg-7 am-u-end">
                                <div class="am-u-md-4 am-u-lg-4">
                                    <input  class="am-form-field am-input-sm"  type="text" name="internalName" size="30" maxlength="30" />
                                </div>
                            </div>
                        </div>
                        <div class="am-form-group am-g">
                            <label class="am-control-label am-u-md-5 am-u-lg-5">${uiLabelMap.ProductProductName}:</label>
                            <div class="am-u-md-7 am-u-lg-7 am-u-end">
                                <div class="am-u-md-4 am-u-lg-4">
                                    <input  class="am-form-field am-input-sm"  type="text" name="productName" size="30" maxlength="30" />
                                </div>
                            </div>
                        </div>
                        <div class="am-form-group am-g">
                            <label class="am-control-label am-u-md-5 am-u-lg-5">${uiLabelMap.ProductShortDescription}:</label>
                            <div class="am-u-md-7 am-u-lg-7 am-u-end">
                                <div class="am-u-md-4 am-u-lg-4">
                                    <input  class="am-form-field am-input-sm"  type="text" name="description" size="30" maxlength="250" />
                                </div>
                            </div>
                        </div>
                        <div class="am-form-group am-g">
                            <label class="am-control-label am-u-md-5 am-u-lg-5">${uiLabelMap.ProductDefaultPrice}:</label>
                            <div class="am-u-md-7 am-u-lg-7 am-u-end">
                                <div class="am-u-md-2 am-u-lg-2">
                                    <input  class="am-form-field am-input-sm"  type="text" name="defaultPrice" size="8"  />
                                </div>
                                <div class="am-u-md-3 am-u-lg-3 am-u-end">
                                    <#assign findCurrenciesMap = Static["org.ofbiz.base.util.UtilMisc"].toMap("uomTypeId", "CURRENCY_MEASURE")>
                                    <#assign currencies = delegator.findByAndCache('Uom', findCurrenciesMap) />
                                    <#if currencies?has_content && (currencies?size > 0)>
                                        <select name="currencyUomId" data-am-selected = "{btnWidth:120, btnStyle:'default'}">
                                            <option value=""></option>
                                            <#list currencies as currency>
                                                <option value="${currency.uomId}">${currency.get("description",locale)} [${currency.uomId}]</option>
                                            </#list>
                                        </select>
                                    </#if>
                                </div>

                            </div>
                        </div>
                        <div class="am-form-group am-g">
                            <label class="am-control-label am-u-md-5 am-u-lg-5">${uiLabelMap.ProductAverageCost}:</label>
                            <div class="am-u-md-7 am-u-lg-7 am-u-end">
                                <div class="am-u-md-4 am-u-lg-4">
                                    <input  class="am-form-field am-input-sm"  type="text" name="averageCost" size="30" maxlength="250" />
                                </div>
                            </div>
                        </div>
                        <div class="am-form-group am-g">
                            <label class="am-control-label am-u-md-5 am-u-lg-5">&nbsp;</label>
                            <div class="am-u-md-7 am-u-lg-7 am-u-end">
                                <div class="am-u-md-4 am-u-lg-4">
                                    <a class="am-btn am-btn-primary am-btn-sm" href="javascript:document.createProductInCategoryCheckExistingForm.submit()" class="buttontext">${uiLabelMap.ProductCheckExisting}</a>
                                </div>
                            </div>
                        </div>
                    </form>
                </div>
            </div>
        </div>
    </div>
</div>
</div>