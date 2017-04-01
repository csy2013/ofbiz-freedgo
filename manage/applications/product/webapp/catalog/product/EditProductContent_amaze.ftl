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
<#if product?exists>
<script language="JavaScript" type="text/javascript">
    function insertNowTimestamp(field) {
        eval('document.productForm.' + field + '.value="${nowTimestampString}";');
    }
    function insertImageName(type, nameValue) {
        eval('document.productForm.' + type + 'ImageUrl.value=nameValue;');
    }

</script>

    <#if fileType?has_content>
    <h3>${uiLabelMap.ProductResultOfImageUpload}</h3>
        <#if !(clientFileName?has_content)>
        <div>${uiLabelMap.ProductNoFileSpecifiedForUpload}.</div>
        <#else>
        <div>${uiLabelMap.ProductTheFileOnYourComputer}: <b>${clientFileName?if_exists}</b></div>
        <div>${uiLabelMap.ProductServerFileName}: <b>${fileNameToUse?if_exists}</b></div>
        <div>${uiLabelMap.ProductServerDirectory}: <b>${imageServerPath?if_exists}</b></div>
        <div>${uiLabelMap.ProductTheUrlOfYourUploadedFile}: <b><a
                href="<@ofbizContentUrl>${imageUrl?if_exists}</@ofbizContentUrl>">${imageUrl?if_exists}</a></b></div>
        </#if>
    <br/>
    </#if>
<div class="am-panel-bd am-collapse am-in">
<div class="am-g">
<div class="am-u-lg-10">
    <form action="<@ofbizUrl>updateProductContent</@ofbizUrl>" method="post" class="am-form am-form-horizontal" name="productForm">
        <input type="hidden" name="productId" value="${productId?if_exists}"/>
        <div class="am-form-group am-g">
            <label class="am-control-label am-u-md-5 am-u-lg-5">${uiLabelMap.ProductProductName}</label>
            <div class="am-u-md-5 am-u-lg-5 am-u-end">
                <input class="am-form-field am-input-sm" type="text" name="productName" size="30" maxlength="60"
                       value="${(product.productName)?if_exists}"/>
            </div>
        </div>

        <div class="am-form-group am-g">
            <label class="am-control-label am-u-md-5 am-u-lg-5">${uiLabelMap.ProductProductDescription}</label>
            <div class="am-u-md-5 am-u-lg-5 am-u-end">
                <textarea name="description" cols="60" rows="2">${(product.description)?if_exists}</textarea>
            </div>
        </div>

        <div class="am-form-group am-g">
            <label class="am-control-label am-u-md-5 am-u-lg-5">${uiLabelMap.ProductLongDescription}</label>
            <div class="am-u-md-5 am-u-lg-5 am-u-end">
                <@amazeHtmlTemplate.renderTextareaField name="longDescription" className="dojo-ResizableTextArea" alert="false" value="${(product.longDescription)?if_exists}" cols="60" rows="5" id="textData" readonly="" visualEditorEnable="true" language="zh_CN" buttons="maxi" />
            </div>
        </div>

        <div class="am-form-group am-g">
            <label class="am-control-label am-u-md-5 am-u-lg-5">${uiLabelMap.ProductDetailScreen}</label>
            <div class="am-u-md-7 am-u-lg-7 am-u-end">
                <div class="am-input-group">
                    <input  class="am-form-field am-input-sm"  type="text" <#if productCategory?has_content>value="${productCategory.detailScreen?if_exists}"</#if> name="detailScreen"  size="57" value="${(productCategory.linkTwoImageUrl)?default('')}"/>
                </div>
                <div>
                ${uiLabelMap.ProductDefaultsTo} &quot;categorydetail&quot;, ${uiLabelMap.ProductDetailScreenMessage}: &quot;component://ecommerce/widget/CatalogScreens.xml#categorydetail&quot;</span>
                </div>
            </div>
        </div>

        <div class="am-form-group am-g">
            <label class="am-control-label am-u-md-5 am-u-lg-5">
            ${uiLabelMap.ProductSmallImage}
                <#if (product.smallImageUrl)?exists>
                    <a href="<@ofbizContentUrl>${(product.smallImageUrl)?if_exists}</@ofbizContentUrl>" target="_blank"><img alt="Small Image" src="<@ofbizContentUrl>${(product.smallImageUrl)?if_exists}</@ofbizContentUrl>" class="cssImgSmall"/></a>
                </#if></label>
            <div class="am-u-md-7 am-u-lg-7 am-u-end">
                <div class="am-input-group">
                    <input  class="am-form-field am-input-sm"  type="text" name="smallImageUrl" size="57" value="${(product.smallImageUrl)?default('')}"/>
                </div>
                <#if productId?has_content>
                    <div>
                    ${uiLabelMap.ProductInsertDefaultImageUrl}:
                        <a href="javascript:insertImageName('small','${imageNameSmall}.jpg');" class="buttontext">.jpg</a>
                        <a href="javascript:insertImageName('small','${imageNameSmall}.gif');" class="buttontext">.gif</a>
                        <a href="javascript:insertImageName('small','');" class="buttontext">${uiLabelMap.CommonClear}</a>
                    </div>
                </#if>
            </div>
        </div>

        <div class="am-form-group am-g">
            <label class="am-control-label am-u-md-5 am-u-lg-5">
            ${uiLabelMap.ProductMediumImage}
                <#if (product.mediumImageUrl)?exists>
                    <a href="<@ofbizContentUrl>${product.mediumImageUrl}</@ofbizContentUrl>" target="_blank"><img alt="Medium Image" src="<@ofbizContentUrl>${product.mediumImageUrl}</@ofbizContentUrl>" class="cssImgSmall"/></a>
                </#if>
            </label>
            <div class="am-u-md-7 am-u-lg-7 am-u-end">
                <div class="am-input-group">
                    <input  class="am-form-field am-input-sm"  type="text" name="mediumImageUrl" size="57" value="${(product.mediumImageUrl)?default('')}"/>
                </div>
                <#if productId?has_content>
                    <div>
                        <span>${uiLabelMap.ProductInsertDefaultImageUrl}: </span>
                        <a href="javascript:insertImageName('medium','${imageNameMedium}.jpg');" class="buttontext">.jpg</a>
                        <a href="javascript:insertImageName('medium','${imageNameMedium}.gif');" class="buttontext">.gif</a>
                        <a href="javascript:insertImageName('medium','');" class="buttontext">${uiLabelMap.CommonClear}</a>
                    </div>
                </#if>
            </div>
        </div>


        <div class="am-form-group am-g">
            <label class="am-control-label am-u-md-5 am-u-lg-5">
                ${uiLabelMap.ProductLargeImage}
                <#if (product.largeImageUrl)?exists>
                    <a href="<@ofbizContentUrl>${product.largeImageUrl}</@ofbizContentUrl>" target="_blank"><img alt="Large Image" src="<@ofbizContentUrl>${product.largeImageUrl}</@ofbizContentUrl>" class="cssImgSmall"/></a>
                </#if></label>
            <div class="am-u-md-7 am-u-lg-7 am-u-end">
                <div class="am-input-group">
                    <input  class="am-form-field am-input-sm"  type="text" name="largeImageUrl" size="57" value="${(product.largeImageUrl)?default('')}"/>
                </div>
                <#if productId?has_content>
                    <div>
                    ${uiLabelMap.ProductInsertDefaultImageUrl}:
                        <a href="javascript:insertImageName('large','${imageNameLarge}.jpg');" class="buttontext">.jpg</a>
                        <a href="javascript:insertImageName('large','${imageNameLarge}.gif');" class="buttontext">.gif</a>
                        <a href="javascript:insertImageName('large','');" class="buttontext">${uiLabelMap.CommonClear}</a>
                    </div>
                </#if>
            </div>
        </div>

        <div class="am-form-group am-g">
            <label class="am-control-label am-u-md-5 am-u-lg-5">
            ${uiLabelMap.ProductDetailImage}
                <#if (product.detailImageUrl)?exists>
                    <a href="<@ofbizContentUrl>${product.detailImageUrl}</@ofbizContentUrl>" target="_blank"><img alt="Detail Image" src="<@ofbizContentUrl>${product.detailImageUrl}</@ofbizContentUrl>" class="cssImgSmall"/></a>
                </#if></label>
            <div class="am-u-md-7 am-u-lg-7 am-u-end">
                <div class="am-input-group">
                    <input  class="am-form-field am-input-sm"  type="text" name="detailImageUrl" size="57" value="${(product.detailImageUrl)?default('')}"/>
                </div>
                <#if productId?has_content>
                    <div>
                    ${uiLabelMap.ProductInsertDefaultImageUrl}:
                        <a href="javascript:insertImageName('detail','${imageNameDetail}.jpg');" class="buttontext">.jpg</a>
                        <a href="javascript:insertImageName('detail','${imageNameDetail}.gif');" class="buttontext">.gif</a>
                        <a href="javascript:insertImageName('detail','');" class="buttontext">${uiLabelMap.CommonClear}</a>
                    </div>
                </#if>
            </div>
        </div>
        <div class="am-form-group am-g">
            <label class="am-control-label am-u-md-5 am-u-lg-5">&nbsp;</label>
            <div class="am-u-md-5 am-u-lg-5 am-u-end">
                <input type="submit" name="Update" value="${uiLabelMap.CommonUpdate}" class="am-btn am-btn-primary am-btn-sm"/>
            </div>
        </div>

    </form>
</div>
</div>
</div>
<script language="JavaScript" type="text/javascript">
    function setUploadUrl(newUrl) {
        var toExec = 'document.imageUploadForm.action="' + newUrl + '";';
        eval(toExec);
    }

</script>
<#--<h3>${uiLabelMap.ProductUploadImage}</h3>
<form method="post" enctype="multipart/form-data" action="<@ofbizUrl>UploadProductImage?productId=${productId}&amp;upload_file_type=original</@ofbizUrl>" name="imageUploadForm">
    <table cellspacing="0" class="basic-table">
        <tr>
            <td width="20%" align="right" valign="top">
                <input type="file" size="50" name="fname"/>
            </td>
            <td>&nbsp;</td>
            <td width="80%" colspan="4" valign="top">
                <input type="radio" name="upload_file_type_bogus" value="small" onclick='setUploadUrl("<@ofbizUrl>UploadProductImage?productId=${productId}&amp;upload_file_type=small</@ofbizUrl>");'/>${uiLabelMap.CommonSmall}
                <input type="radio" name="upload_file_type_bogus" value="medium" onclick='setUploadUrl("<@ofbizUrl>UploadProductImage?productId=${productId}&amp;upload_file_type=medium</@ofbizUrl>");'/>${uiLabelMap.CommonMedium}
                <input type="radio" name="upload_file_type_bogus" value="large"onclick='setUploadUrl("<@ofbizUrl>UploadProductImage?productId=${productId}&amp;upload_file_type=large</@ofbizUrl>");'/>${uiLabelMap.CommonLarge}
                <input type="radio" name="upload_file_type_bogus" value="detail" onclick='setUploadUrl("<@ofbizUrl>UploadProductImage?productId=${productId}&amp;upload_file_type=detail</@ofbizUrl>");'/>${uiLabelMap.CommonDetail}
                <input type="radio" name="upload_file_type_bogus" value="original" checked="checked" onclick='setUploadUrl("<@ofbizUrl>UploadProductImage?productId=${productId}&amp;upload_file_type=original</@ofbizUrl>");'/>${uiLabelMap.ProductOriginal}
                <input type="submit" class="smallSubmit" value="${uiLabelMap.ProductUploadImage}"/>
            </td>
        </tr>
    </table>
    <span class="tooltip">${uiLabelMap.ProductOriginalImageMessage} : {ofbiz.home}/applications/product/config/ImageProperties.xml&quot;</span>
</form>-->

<div class="am-cf am-padding-xs">
    <div class="am-panel am-panel-default">
        <div class="am-panel-hd am-cf">${uiLabelMap.ProductUploadImage}</div>
        <div class="am-panel-bd am-collapse am-in">
            <div class="am-g">
                <div class="am-u-lg-10">
                    <form class="am-form am-form-horizontal" method="post" enctype="multipart/form-data" action="<@ofbizUrl>UploadProductImage?productId=${productId}&amp;upload_file_type=original</@ofbizUrl>" name="imageUploadForm">
                        <div class="am-form-group am-g">
                            <div class="am-u-md-5 am-u-lg-5 am-u-end"><input class="am-form-field am-input-sm" type="file"  size="50" name="fname"/></div>
                            <div class="am-u-md-7 am-u-lg-7 am-u-end">
                                <input type="radio" name="upload_file_type_bogus" value="small" onclick='setUploadUrl("<@ofbizUrl>UploadProductImage?productId=${productId}&amp;upload_file_type=small</@ofbizUrl>");'/>${uiLabelMap.CommonSmall}
                                <input type="radio" name="upload_file_type_bogus" value="medium" onclick='setUploadUrl("<@ofbizUrl>UploadProductImage?productId=${productId}&amp;upload_file_type=medium</@ofbizUrl>");'/>${uiLabelMap.CommonMedium}
                                <input type="radio" name="upload_file_type_bogus" value="large"onclick='setUploadUrl("<@ofbizUrl>UploadProductImage?productId=${productId}&amp;upload_file_type=large</@ofbizUrl>");'/>${uiLabelMap.CommonLarge}
                                <input type="radio" name="upload_file_type_bogus" value="detail" onclick='setUploadUrl("<@ofbizUrl>UploadProductImage?productId=${productId}&amp;upload_file_type=detail</@ofbizUrl>");'/>${uiLabelMap.CommonDetail}
                                <input type="radio" name="upload_file_type_bogus" value="original" checked="checked" onclick='setUploadUrl("<@ofbizUrl>UploadProductImage?productId=${productId}&amp;upload_file_type=original</@ofbizUrl>");'/>${uiLabelMap.ProductOriginal}
                                <input type="submit" class="am-btn am-btn-primary am-btn-sm" value="${uiLabelMap.ProductUploadImage}"/>
                            </div>
                        </div>
                    </form>
                </div>
            </div>
        </div>
    </div>
</div>
</#if>
