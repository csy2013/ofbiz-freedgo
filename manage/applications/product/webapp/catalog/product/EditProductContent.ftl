<#if product?exists>


<#assign nowTimestampString = Static["org.ofbiz.base.util.UtilDateTime"].nowTimestamp() >
<#assign uploadType = Static["org.ofbiz.base.util.UtilProperties"].getPropertyValue("content","content.image.upload.type")>
<#if uploadType == "qiniu">
    <#assign imageServerUrl = Static["org.ofbiz.base.util.UtilProperties"].getPropertyValue("content","img.qiniu.domain")>
<#elseif uploadType == "local">
    <#assign imageServerUrl = "">
</#if>

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
<form action="<@ofbizUrl>updateProductContent</@ofbizUrl>" method="post" class="form-horizontal" name="productForm">
    <input type="hidden" name="productId" value="${productId?if_exists}"/>

    <div class="form-group">
        <label class="control-label col-md-2">${uiLabelMap.ProductProductName}</label>

        <div class="col-md-6">
            <input type="text" class="form-control" name="productName" value="${(product.productName)?if_exists}" size="30"
                   maxlength="60"/>
        </div>
    </div>
    <div class="form-group">
        <label class="control-label col-md-2">${uiLabelMap.ProductProductDescription}</label>

        <div class="col-md-6">
            <textarea name="description" class="form-control" cols="60" rows="5">${(product.description)?if_exists}</textarea>
        </div>
    </div>
    <div class="form-group">
        <label class="control-label col-md-2">${uiLabelMap.ProductLongDescription}</label>

        <div class="col-md-9">
            <@htmlTemplate.renderTextareaField name="longDescription" className="dojo-ResizableTextArea" alert="false"
            value="${(product.longDescription)?if_exists}" cols="60" rows="15" id="textData" readonly="" visualEditorEnable="true" language="zh_CN" buttons="maxi" />

                <#--<textarea class="dojo-ResizableTextArea" name="longDescription" cols="60"-->
                          <#--rows="7">${(product.longDescription)?if_exists}</textarea>-->
        </div>
    </div>
    <div class="form-group">
        <label class="control-label col-md-2">${uiLabelMap.ProductLongWapDescription}</label>

        <div class="col-md-9">
            <@htmlTemplate.renderTextareaField name="longWapDescription" className="dojo-ResizableTextArea" alert="false"
            value="${(product.longWapDescription)?if_exists}" cols="100" rows="15" id="textData1" readonly="" visualEditorEnable="true" language="zh_CN" buttons="maxi" />

                <#--<textarea class="dojo-ResizableTextArea" name="longDescription" cols="60"-->
                          <#--rows="7">${(product.longDescription)?if_exists}</textarea>-->
        </div>
    </div>
    <div class="form-group">
        <label class="control-label col-md-2">${uiLabelMap.ProductDetailScreen}</label>

        <div class="col-md-6">
            <input type="text" class="form-control" name="detailScreen" value="${(product.detailScreen)?if_exists}" size="60"
                   maxlength="250"/>
            <br/><span class="tooltip">${uiLabelMap.ProductIfNotSpecifiedDefaultsIsProductdetail} &quot;productdetail&quot;, ${uiLabelMap.ProductDetailScreenMessage}
            : &quot;component://ecommerce/widget/CatalogScreens.xml#productdetail&quot;</span>
        </div>
    </div>
    <div class="form-group">
        <label class="control-label col-md-2">${uiLabelMap.ProductSmallImage}</label>
        <#if (product.smallImageUrl)?exists>
            <a href="${imageServerUrl}<@ofbizContentUrl>${(product.smallImageUrl)?if_exists}</@ofbizContentUrl>" target="_blank"><img
                    alt="Small Image"
                    src="<@ofbizContentUrl>${(product.originalImageUrl)?if_exists}</@ofbizContentUrl>"
                    class="cssImgSmall"/></a>
        </#if>


        <div class="col-md-6">
            <input type="text" class="form-control" name="smallImageUrl" value="${(product.smallImageUrl)?default('')}" size="60"
                   maxlength="255"/>
            <#if productId?has_content>
                <div>
                    <a href="javascript:insertImageName('small','${imageNameSmall}.jpg');"
                       class="buttontext">.jpg</a>
                    <a href="javascript:insertImageName('small','${imageNameSmall}.gif');"
                       class="buttontext">.gif</a>
                    <a href="javascript:insertImageName('small','');"
                       class="buttontext">${uiLabelMap.CommonClear}</a>
                </div>
            </#if>
        </div>
    </div>
    <div class="form-group">
        <label class="control-label col-md-2">${uiLabelMap.ProductMediumImage}</label>
        <#if (product.mediumImageUrl)?exists>
            <a href="${imageServerUrl}<@ofbizContentUrl>${product.mediumImageUrl}</@ofbizContentUrl>" target="_blank"><img
                    alt="Medium Image" src="<@ofbizContentUrl>${product.originalImageUrl}</@ofbizContentUrl>"
                    class="cssImgSmall"/></a>
        </#if>

        <div class="col-md-6">
            <input type="text" class="form-control" name="mediumImageUrl" value="${(product.mediumImageUrl)?default('')}" size="60"
                   maxlength="255"/>
            <#if productId?has_content>
                <div>

                    <a href="javascript:insertImageName('medium','${imageNameMedium}.jpg');"
                       class="buttontext">.jpg</a>
                    <a href="javascript:insertImageName('medium','${imageNameMedium}.gif');"
                       class="buttontext">.gif</a>
                    <a href="javascript:insertImageName('medium','');"
                       class="buttontext">${uiLabelMap.CommonClear}</a>
                </div>
            </#if>
        </div>
    </div>
    <div class="form-group">
        <label class="control-label col-md-2">${uiLabelMap.ProductLargeImage}</label>
        <#if (product.largeImageUrl)?exists>
            <a href="${imageServerUrl}<@ofbizContentUrl>${product.largeImageUrl}</@ofbizContentUrl>" target="_blank"><img
                    alt="Large Image" src="<@ofbizContentUrl>${product.originalImageUrl}</@ofbizContentUrl>"
                    class="cssImgSmall"/></a>
        </#if>

        <div class="col-md-6">
            <input type="text" class="form-control" name="largeImageUrl" value="${(product.largeImageUrl)?default('')}" size="60"
                   maxlength="255"/>
            <#if productId?has_content>
                <div>

                    <a href="javascript:insertImageName('large','${imageNameLarge}.jpg');"
                       class="buttontext">.jpg</a>
                    <a href="javascript:insertImageName('large','${imageNameLarge}.gif');"
                       class="buttontext">.gif</a>
                    <a href="javascript:insertImageName('large','');"
                       class="buttontext">${uiLabelMap.CommonClear}</a>
                </div>
            </#if>
        </div>
    </div>
    <div class="form-group">
        <label class="control-label col-md-2">${uiLabelMap.ProductDetailImage}</label>
        <#if (product.detailImageUrl)?exists>
            <a href="${imageServerUrl}<@ofbizContentUrl>${product.detailImageUrl}</@ofbizContentUrl>" target="_blank"><img
                    alt="Detail Image" src="<@ofbizContentUrl>${product.originalImageUrl}</@ofbizContentUrl>"
                    class="cssImgSmall"/></a>
        </#if>

        <div class="col-md-6">
            <input type="text" class="form-control" name="detailImageUrl" value="${(product.detailImageUrl)?default('')}" size="60"
                   maxlength="255"/>
            <#if productId?has_content>
                <div>

                    <a href="javascript:insertImageName('detail','${imageNameDetail}.jpg');"
                       class="buttontext">.jpg</a>
                    <a href="javascript:insertImageName('detail','${imageNameDetail}.gif');"
                       class="buttontext">.gif</a>
                    <a href="javascript:insertImageName('detail','');"
                       class="buttontext">${uiLabelMap.CommonClear}</a>
                </div>
            </#if>
        </div>
    </div>
    <div class="form-group">
        <label class="control-label col-md-2">${uiLabelMap.ProductOriginalImage}</label>
        <#if (product.originalImageUrl)?exists>
            <a href="${imageServerUrl}<@ofbizContentUrl>${product.originalImageUrl}</@ofbizContentUrl>" target="_blank"><img
                    alt="Original Image" src="<@ofbizContentUrl>${product.originalImageUrl}</@ofbizContentUrl>"
                    class="cssImgSmall"/></a>
        </#if>

        <div class="col-md-6">
            <input type="text" class="form-control" name="originalImageUrl" value="${(product.originalImageUrl)?default('')}" size="60"
                   maxlength="255"/>
            <#if productId?has_content>
                <div>
                    <a href="javascript:insertImageName('original','${imageNameOriginal}.jpg');" class="buttontext">.jpg</a>
                    <a href="javascript:insertImageName('original','${imageNameOriginal}.gif');" class="buttontext">.gif</a>
                    <a href="javascript:insertImageName('original','');" class="buttontext">${uiLabelMap.CommonClear}</a>
                </div>
            </#if>
        </div>

    </div>
    <div class="form-group">
        <div class="col-md-2">&nbsp;</div>
        <div class="col-md-6 pull-right"><input type="submit" name="Update" value="${uiLabelMap.CommonUpdate}" class="btn btn-primary btn-sm"/></div>

    </div>
</form>
<script language="JavaScript" type="text/javascript">
    function setUploadUrl(newUrl) {
        var toExec = 'document.imageUploadForm.action="' + newUrl + '";';
        eval(toExec);
    }
</script>

</#if>
