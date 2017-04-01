<script language="JavaScript" type="text/javascript">
    function insertNowTimestamp(field) {
        eval('document.productForm.' + field + '.value="${nowTimestamp?string}";');
    }
    function insertImageName(size, nameValue) {
        eval('document.productForm.' + size + 'ImageUrl.value=nameValue;');
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

<#if !(configItem?exists)>
<h3>${uiLabelMap.ProductCouldNotFindProductConfigItem} "${configItemId}".</h3>
<#else>
<table class="am-table am-table-striped am-table-hover table-main">
    <tr>
        <td><b>${uiLabelMap.ProductContent}</b></td>
        <td><b>${uiLabelMap.ProductType}</b></td>
        <td><b>${uiLabelMap.CommonFrom}</b></td>
        <td><b>${uiLabelMap.CommonThru}</b></td>
        <td><b>&nbsp;</b></td>
        <td><b>&nbsp;</b></td>
    </tr>
    <#assign rowClass = "2">
    <#list productContentList as entry>
        <#assign productContent=entry.productContent/>
        <#assign productContentType=productContent.getRelatedOneCache("ProdConfItemContentType")/>
        <tr>
            <td>
                <a href="<@ofbizUrl>EditProductConfigItemContentContent?configItemId=${productContent.configItemId}&amp;contentId=${productContent.contentId}&amp;confItemContentTypeId=${productContent.confItemContentTypeId}&amp;fromDate=${productContent.fromDate}</@ofbizUrl>"
                   class="buttontext">${entry.content.description?default("[${uiLabelMap.ProductNoDescription}]")}
                    [${entry.content.contentId}]</td>
            <td>${productContentType.description?default(productContent.confItemContentTypeId)}</td>
            <td>${productContent.fromDate?default("N/A")}</td>
            <td>${productContent.thruDate?default("N/A")}</td>
            <td>
                <a class="am-btn am-btn-primary am-btn-sm" href="<@ofbizUrl>removeContentFromProductConfigItem?configItemId=${productContent.configItemId}&amp;contentId=${productContent.contentId}&amp;confItemContentTypeId=${productContent.confItemContentTypeId}&amp;fromDate=${productContent.fromDate}</@ofbizUrl>"
                   class="buttontext">${uiLabelMap.CommonDelete}</a></td>
            <td>
                <a class="am-btn am-btn-primary am-btn-sm" href="/content/control/EditContent?contentId=${productContent.contentId}&amp;externalLoginKey=${requestAttributes.externalLoginKey?if_exists}"
                   class="buttontext">${uiLabelMap.ProductEditContent} ${entry.content.contentId}</td>
        </tr>
    <#-- toggle the row color -->
        <#if rowClass == "2">
            <#assign rowClass = "1">
        <#else>
            <#assign rowClass = "2">
        </#if>
    </#list>
</table>
    <#if configItemId?has_content && configItem?has_content>
    <div class="am-cf am-padding-xs">
        <div class="am-panel am-panel-default">
            <div class="am-panel-hd am-cf">${uiLabelMap.ProductCreateNewProductConfigItemContent}</div>
            <div class="am-panel-bd am-collapse am-in">
                <div class="am-g">
                    <div class="am-u-lg-12">
                    ${prepareAddProductContentWrapper.renderFormString(context)}
                    </div>
                </div>
            </div>
        </div>
    </div>
    <div class="am-cf am-padding-xs">
        <div class="am-panel am-panel-default">
            <div class="am-panel-hd am-cf">${uiLabelMap.ProductAddContentProductConfigItem}</div>
            <div class="am-panel-bd am-collapse am-in">
                <div class="am-g">
                    <div class="am-u-lg-12">
                    ${addProductContentWrapper.renderFormString(context)}
                    </div>
                </div>
            </div>
        </div>
    </div>
    </#if>

<div class="am-cf am-padding-xs">
    <div class="am-panel am-panel-default">
        <div class="am-panel-hd am-cf">${uiLabelMap.ProductOverrideSimpleFields}</div>
        <div class="am-panel-bd am-collapse am-in">
            <div class="am-g">
                <div class="am-u-lg-10">
                    <form action="<@ofbizUrl>updateProductConfigItemContent</@ofbizUrl>" method="post" class="am-form am-form-horizontal" name="productForm">
                        <input type="hidden" name="configItemId" value="${configItemId?if_exists}"/>
                        <div class="am-form-group am-g">
                            <label class="am-control-label am-u-md-5 am-u-lg-5">${uiLabelMap.CommonDescription}</label>

                            <div class="am-u-md-7 am-u-lg-7 am-u-end">
                                <div class="am-input-group">
                                    <textarea name="description" cols="60" rows="2">${(configItem.description)?if_exists}</textarea>
                                </div>
                            </div>
                        </div>

                        <div class="am-form-group am-g">
                            <label class="am-control-label am-u-md-5 am-u-lg-5 am-u-end">${uiLabelMap.ProductLongDescription}</label>

                            <div class="am-u-md-7 am-u-lg-7 am-u-end">
                                <div class="am-input-group">
                                    <textarea name="longDescription" cols="60" rows="5">${(configItem.longDescription)?if_exists}</textarea>
                                </div>
                            </div>
                        </div>

                        <div class="am-form-group am-g">
                            <label class="am-control-label am-u-md-5 am-u-lg-5">
                            ${uiLabelMap.ProductSmallImage}
                                <#if (configItem.imageUrl)?exists>
                                    <a href="<@ofbizContentUrl>${configItem.imageUrl}</@ofbizContentUrl>" target="_blank"><img vspace="5" hspace="5" width="150" height="150" alt="Image" src="<@ofbizContentUrl>${configItem.imageUrl}</@ofbizContentUrl>" class="cssImgSmall" /></a>
                                </#if>
                             </label>
                            <div class="am-u-md-7 am-u-lg-7 am-u-end">
                                <div class="am-input-group">
                                    <input class="am-form-field am-input-sm" type="text" name="smallImageUrl" value="${(configItem.imageUrl)?default(imageNameSmall + '.jpg')}" size="68" />
                                </div>
                                <#if configItemId?has_content>
                                    <div>
                                        <span class="label">${uiLabelMap.ProductInsertDefaultImageUrl}: </span>
                                        <a href="javascript:insertImageName('small','${imageNameSmall}.jpg');" class="buttontext">.jpg</a>
                                        <a href="javascript:insertImageName('small','${imageNameSmall}.gif');" class="buttontext">.gif</a>
                                        <a href="javascript:insertImageName('small','');" class="buttontext">${uiLabelMap.CommonClear}</a>
                                    </div>
                                </#if>
                            </div>
                        </div>

                        <div class="am-form-group am-g">
                            <label class="am-control-label am-u-md-5 am-u-lg-5">
                            &nbsp;
                            </label>
                            <div class="am-u-md-7 am-u-lg-7 am-u-end">
                              <span style="margin-left: 200px">
                                  <input type="submit" class="am-btn am-btn-primary am-btn-sm" name="Update" value="${uiLabelMap.CommonUpdate}" />
                              </span>
                            </div>
                        </div>
                    </form>
                </div>
            </div>
        </div>
    </div>
</div>

<div class="am-cf am-padding-xs">
    <div class="am-panel am-panel-default">
        <div class="am-panel-hd am-cf">${uiLabelMap.ProductUploadImage}</div>
        <div class="am-panel-bd am-collapse am-in">
            <div class="am-g">
                <div class="am-u-lg-10">
                    <form method="post" enctype="multipart/form-data" action="<@ofbizUrl>UploadProductConfigItemImage?configItemId=${configItemId}&amp;upload_file_type=small</@ofbizUrl>" name="imageUploadForm">
                        <div class="am-form-group am-g">
                            <div class="am-u-md-5 am-u-lg-5 am-u-end"><input class="am-form-field am-input-sm" type="file" size="50" name="fname" /></div>
                            <div class="am-u-md-7 am-u-lg-7 am-u-end"><input class="am-btn am-btn-primary am-btn-sm" type="submit" class="smallSubmit" value="${uiLabelMap.ProductUploadImage}" /></div>
                        </div>
                    </form>
                </div>
            </div>
        </div>
    </div>
</div>

</#if>
