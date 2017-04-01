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

<#if productCategoryId?has_content>
<a href="<@ofbizUrl>EditCategory?productCategoryId=${productCategoryId}</@ofbizUrl>"
   class="buttontext">[${uiLabelMap.ProductBackToEditCategory}]</a>
</#if>
<div class="am-cf am-padding-xs">
    <div class="am-panel am-panel-default">
        <div class="am-panel-hd am-cf">
        ${uiLabelMap.ProductCheckingForExistingProductInCategory} <#if (productCategory.description)?has_content>
            "${productCategory.description}"</#if> [${uiLabelMap.CommonId}:${productCategoryId?if_exists}]
        <#if productFeatureAndTypeDatas?has_content>
        ${uiLabelMap.CommonWhere}
            <#list productFeatureAndTypeDatas as productFeatureAndTypeData>
                <#assign productFeatureType = productFeatureAndTypeData.productFeatureType>
                <#assign productFeature = productFeatureAndTypeData.productFeature>
            ${productFeatureType.description} = ${productFeature.description}
                <#if productFeatureAndTypeData_has_next>,${uiLabelMap.CommonAnd} </#if>
            </#list>
        </#if>
        </div>
        <div class="am-panel-bd am-collapse am-in">
            <div class="am-g">
                <div class="am-u-lg-12">
                <#if products?has_content>
                    <table class="am-table am-table-striped am-table-hover table-main">
                        <tr>
                            <td>${uiLabelMap.ProductInternalName}</td>
                            <td>${uiLabelMap.ProductProductName}</td>
                            <td width="10%">&nbsp;</td>
                        </tr>
                        <#list products as product>
                            <tr>
                                <td>${product.internalName?default("-no internal name-")} [${product.productId}]</td>
                                <td>${product.productName?default("-no name-")} [${product.productId}]</td>
                                <td><a href="<@ofbizUrl>EditProduct?productId=${product.productId}</@ofbizUrl>"
                                       class="buttontext">[${uiLabelMap.ProductThisIsIt}]</a></td>
                            </tr>
                        </#list>
                    </table>
                <#else>
                    <h3>&nbsp;${uiLabelMap.ProductNoExistingProductsFound}.</h3>
                </#if>
                <#if productCategoryId?if_exists>
                <form name="createProductInCategoryForm" method="post"
                      action="<@ofbizUrl>createProductInCategory</@ofbizUrl>" class="am-form am-form-horizontal">
                    <input type="hidden" name="productCategoryId" value="${productCategoryId}"/>
                    <#list productFeatureAndTypeDatas?if_exists as productFeatureAndTypeData>
                        <#assign productFeatureType = productFeatureAndTypeData.productFeatureType>
                        <#assign productFeature = productFeatureAndTypeData.productFeature>
                        <#assign productFeatureTypeId = productFeatureType.productFeatureTypeId>
                        <input type="hidden" name="pft_${productFeatureType.productFeatureTypeId}"
                               value="${productFeature.productFeatureId}"/>

                        <div class="am-form-group am-g">
                            <label class="am-control-label am-u-md-5 am-u-lg-5 am-u-end">${productFeatureType.description}</label>
                            <#--<label class="am-control-label am-u-md-5 am-u-lg-5 am-u-end">${uiLabelMap.ProductProductCategoryType}</label>-->
                            <#if requestParameters["pftsel_" + productFeatureTypeId]?exists>
                                <input type="hidden" name="pftsel_${productFeatureTypeId}" value="Y"/>
                                [${uiLabelMap.ProductSelectable}]
                            <#else>
                                <input type="hidden" name="pftsel_${productFeatureTypeId}" value="N"/>
                                [${uiLabelMap.ProductStandard}]
                            </#if>
                        </div>
                    </#list>
                    <div class="am-form-group am-g">
                        <label class="am-control-label am-u-md-5 am-u-lg-5">${uiLabelMap.ProductInternalName}:</label>
                        <label class="am-control-label am-u-md-2 am-u-lg-2 am-u-end"> ${requestParameters.internalName?default("&nbsp;")}</label>
                        <input type="hidden" name="internalName" value="${requestParameters.internalName?if_exists}"/>
                    </div>
                    <div class="am-form-group am-g">
                        <label class="am-control-label am-u-md-5 am-u-lg-5">${uiLabelMap.ProductProductName}:</label>
                        <input type="hidden" name="productName" value="${requestParameters.productName?if_exists}"/>
                        <label class="am-control-label am-u-md-2 am-u-lg-2 am-u-end"> ${requestParameters.productName?default("&nbsp;")}</label>
                    </div>

                    <div class="am-form-group am-g">
                        <label class="am-control-label am-u-md-5 am-u-lg-5">${uiLabelMap.ProductShortDescription}:</label>
                        <input type="hidden" name="description" value="${requestParameters.description?if_exists}"/>
                        <label class="am-control-label am-u-md-2 am-u-lg-2 am-u-end">${requestParameters.description?default("&nbsp;")}</label>
                    </div>

                    <div class="am-form-group am-g">
                        <label class="am-control-label am-u-md-5 am-u-lg-5">${uiLabelMap.ProductAverageCost}:</label>
                        <input type="hidden" name="description" value="${requestParameters.averageCost?if_exists}"/>
                        <label class="am-control-label am-u-md-2 am-u-lg-2 am-u-end">${requestParameters.averageCost?default("&nbsp;")}</label>
                    </div>

                    <div class="am-form-group am-g">
                        <label class="am-control-label am-u-md-5 am-u-lg-5">${uiLabelMap.ProductNewProductId}:</label>

                        <div class="am-u-md-7 am-u-lg-7 am-u-end">

                            <div class="am-u-md-4 am-u-lg-4"><input type="text" name="productId" class="am-form-field am-u-sm"  value=""/> </div>
                             <input type="submit" value="${uiLabelMap.ProductCreateNewProduct}" class="am-btn am-btn-primary am-btn-sm"/>
                        </div>

                    </div>
                </#if>
                </form>
            </div>
        </div>
    </div>
</div>
</div>