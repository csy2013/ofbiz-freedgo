<#if fileType?has_content>
    <@htmlScreenTemplate.renderScreenletBegin id="" title="${uiLabelMap.ProductResultOfImageUpload}" collapsible="" showMore=true/>
    <#if !(clientFileName?has_content)>
    <div>${uiLabelMap.ProductNoFileSpecifiedForUpload}.</div>
    <#else>
    <div>${uiLabelMap.ProductTheFileOnYourComputer}: <b>${clientFileName?if_exists}</b></div>
    <div>${uiLabelMap.ProductServerFileName}: <b>${fileNameToUse?if_exists}</b></div>
    <div>${uiLabelMap.ProductServerDirectory}: <b>${imageServerPath?if_exists}</b></div>
    <div>${uiLabelMap.ProductTheUrlOfYourUploadedFile}: <b><a href="<@ofbizContentUrl>${imageUrl?if_exists}</@ofbizContentUrl>">${imageUrl?if_exists}</a></b></div>
    </#if>
    <@htmlScreenTemplate.renderScreenletEnd/>
</#if>

<script language="JavaScript" type="text/javascript">
    function setUploadUrl(newUrl) {
        var toExec = 'document.imageUploadForm.action="' + newUrl + '";';
        eval(toExec);
    }

</script>
<@htmlScreenTemplate.renderScreenletBegin id="" title="${uiLabelMap.ProductCategoryUploadImage}" collapsible="" showMore=true/>

<div class="row">
    <label class="col-md-3 control-label">${uiLabelMap.ProductCategoryImageUrl}</label>
    <#if (productCategory.categoryImageUrl)?exists>
        <a href="<@ofbizContentUrl>${(productCategory.categoryImageUrl)?if_exists}</@ofbizContentUrl>" target="_blank">
            <img alt="Category Image" src="<@ofbizContentUrl>${(productCategory.categoryImageUrl)?if_exists}</@ofbizContentUrl>" class="cssImgSmall"/></a>
    </#if>
</div>
<br/>
<#--<div class="row">
    <label class="col-md-3 control-label">${uiLabelMap.ProductLinkOneImageUrl}</label>
    <#if (productCategory.linkOneImageUrl)?exists>
        <a href="<@ofbizContentUrl>${(productCategory.linkOneImageUrl)?if_exists}</@ofbizContentUrl>" target="_blank">
            <img alt="Link One Image"  src="<@ofbizContentUrl>${(productCategory.linkOneImageUrl)?if_exists}</@ofbizContentUrl>" class="cssImgSmall"/></a></#if>

</div>
<br/>
<div class="row">
    <label class="col-md-3 control-label">${uiLabelMap.ProductLinkTwoImageUrl}</label>
    <#if (productCategory.linkTwoImageUrl)?exists>
        <a href="<@ofbizContentUrl>${(productCategory.linkTwoImageUrl)?if_exists}</@ofbizContentUrl>" target="_blank">
            <img alt="Link One Image"   src="<@ofbizContentUrl>${(productCategory.linkTwoImageUrl)?if_exists}</@ofbizContentUrl>"
                 class="cssImgSmall"/></a</#if>

</div>-->
<br/><br/>
<form method="post" enctype="multipart/form-data" class="form-inline"
      action="<@ofbizUrl>UploadCategoryImage?productCategoryId=${productCategoryId?if_exists}&amp;upload_file_type=category</@ofbizUrl>" name="imageUploadForm">
    <div class="form-group">
        <div class="col-md-4">
            <input type="file" size="50" name="fname"/>
        </div>
    </div>
    <div class="form-group">
        <div class="col-md-12">
            <label class="radio-inline">
                <input type="radio" name="upload_file_type_bogus" value="category" checked="checked"
                       onclick='setUploadUrl("<@ofbizUrl>UploadCategoryImage?productCategoryId=${productCategoryId}&amp;upload_file_type=category</@ofbizUrl>");'/>
            ${uiLabelMap.ProductCategoryImageUrl}
            </label>
            <#--<label class="radio-inline">
                <input type="radio" name="upload_file_type_bogus" value="linkOne"
                       onclick='setUploadUrl("<@ofbizUrl>UploadCategoryImage?productCategoryId=${productCategoryId}&amp;upload_file_type=linkOne</@ofbizUrl>");'/>${uiLabelMap.ProductLinkOneImageUrl}
            </label>
            <label class="radio-inline">
                <input type="radio" name="upload_file_type_bogus" value="linkTwo"
                       onclick='setUploadUrl("<@ofbizUrl>UploadCategoryImage?productCategoryId=${productCategoryId}&amp;upload_file_type=linkTwo</@ofbizUrl>");'/>${uiLabelMap.ProductLinkTwoImageUrl}
            </label>-->
            <input type="submit" class="btn btn-primary btn-sm m-l-10" value="${uiLabelMap.ProductUploadImage}"/>
        </div>
    </div>
</form>
<@htmlScreenTemplate.renderScreenletEnd/>