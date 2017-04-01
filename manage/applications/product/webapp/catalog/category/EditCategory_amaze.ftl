
<script language="JavaScript" type="text/javascript">
function insertImageName(type,nameValue) {
  eval('document.productCategoryForm.' + type + 'ImageUrl.value=nameValue;');
}
</script>
<#if fileType?has_content>
<div class="am-cf am-padding-xs">
    <div class="am-panel am-panel-default">
        <div class="am-panel-hd am-cf">${uiLabelMap.ProductResultOfImageUpload}</div>
        <div class="am-panel-bd">
            <#if !(clientFileName?has_content)>
                <p class = "am-text-sm">${uiLabelMap.ProductNoFileSpecifiedForUpload}.</p>
            <#else>
                <p class = "am-text-sm">${uiLabelMap.ProductTheFileOnYourComputer}: <b>${clientFileName?if_exists}</p>
                <p class = "am-text-sm">${uiLabelMap.ProductServerFileName}: <b>${fileNameToUse?if_exists}</b></p>
                <p class = "am-text-sm">${uiLabelMap.ProductServerDirectory}: <b>${imageServerPath?if_exists}</b></p>
                <p class = "am-text-sm">${uiLabelMap.ProductTheUrlOfYourUploadedFile}: <b><a href="<@ofbizContentUrl>${imageUrl?if_exists}</@ofbizContentUrl>">${imageUrl?if_exists}</a></b></p>
            </#if>
        </div>
    </div>
</div>
</#if>
<#if ! productCategory?has_content>
    <#if productCategoryId?has_content>
        <div class="am-cf am-padding-xs">
        <div class="am-panel am-panel-default ">
            <div class="am-panel-hd am-cf">${uiLabelMap.ProductCouldNotFindProductCategoryWithId} "${productCategoryId}".</div>
        <div class="am-panel-bd am-collapse am-in">
        <div class="am-g">
        <div class="am-u-lg-10">
        <form action="<@ofbizUrl>createProductCategory</@ofbizUrl>" method="post" class="am-form am-form-horizontal" name="productCategoryForm">
            <div class="am-form-group am-g">
                <label class="am-control-label am-u-md-5 am-u-lg-5">${uiLabelMap.ProductProductCategoryId}</label>
                <div class="am-u-md-7 am-u-lg-7 am-u-end">
                    <div class="am-input-group">
                        <input  class="am-form-field am-input-sm"  type="text" name="productCategoryId" size="57" maxlength="40" value="${productCategoryId}"/>
                    </div>
                </div>
            </div>
    <#else>
        <div class="am-cf am-padding-xs">
        <div class="am-panel am-panel-default">
            <div class="am-panel-hd am-cf">${uiLabelMap.PageTitleCreateProductCategory}</div>
        <div class="am-panel-bd am-collapse am-in">
        <div class="am-g">
        <div class="am-u-lg-10">
        <form action="<@ofbizUrl>createProductCategory</@ofbizUrl>" method="post" class="am-form am-form-horizontal" name="productCategoryForm">
            <div class="am-form-group am-g">
                <label class="am-control-label am-u-md-5 am-u-lg-5">${uiLabelMap.ProductProductCategoryId}</label>
                <div class="am-u-md-7 am-u-lg-7 am-u-end">
                    <div class="am-input-group">
                        <input  class="am-form-field am-input-sm"  type="text" name="productCategoryId" size="57" maxlength="40" value=""/>
                    </div>
                </div>
            </div>
    </#if>
<#else>
        <div class="am-cf am-padding-xs">
        <div class="am-panel am-panel-default">
            <div class="am-panel-hd am-cf">${uiLabelMap.PageTitleEditProductCategories}</div>
        <div class="am-panel-bd am-collapse am-in">
        <div class="am-g">
        <div class="am-u-lg-10">
        <form action="<@ofbizUrl>updateProductCategory</@ofbizUrl>" method="post" class="am-form am-form-horizontal" name="productCategoryForm">
            <input type="hidden" name="productCategoryId" value="${productCategoryId}"/>
            <div class="am-form-group am-g">
                <label class="am-control-label am-u-md-5 am-u-lg-5">${uiLabelMap.ProductProductCategoryId}</label>
                <div class="am-u-md-7 am-u-lg-7 am-u-end">
                    <label class="am-control-label">${productCategoryId}</label> (${uiLabelMap.ProductNotModificationRecreationCategory}.)
                </div>
            </div>
</#if>
            <div class="am-form-group am-g">
                <label class="am-control-label am-u-md-5 am-u-lg-5 am-u-end">${uiLabelMap.ProductProductCategoryType}</label>
                <div class="am-u-md-7 am-u-lg-7 am-u-end">
                    <div class="am-input-group">
                        <select name="productCategoryTypeId" data-am-selected = "{btnWidth:466, btnStyle:'default'}" id="" size="1">
                        <#assign selectedKey = "">
                        <#list productCategoryTypes as productCategoryTypeData>
                            <#if requestParameters.productCategoryTypeId?has_content>
                                <#assign selectedKey = requestParameters.productCategoryTypeId>
                            <#elseif (productCategory?has_content && productCategory.productCategoryTypeId?if_exists == productCategoryTypeData.productCategoryTypeId)>
                                <#assign selectedKey = productCategory.productCategoryTypeId>
                            </#if>
                            <option <#if selectedKey == productCategoryTypeData.productCategoryTypeId?if_exists>selected="selected"</#if> value="${productCategoryTypeData.productCategoryTypeId}">${productCategoryTypeData.get("description",locale)}</option>
                        </#list>
                        </select>
                    </div>
                </div>
            </div>
            <div class="am-form-group am-g">
                <label class="am-control-label am-u-md-5 am-u-lg-5">${uiLabelMap.ProductProductCategoryName}</label>
                <div class="am-u-md-7 am-u-lg-7 am-u-end">
                    <div class="am-input-group">
                        <input  class="am-form-field am-input-sm"  type="text" name="categoryName" size="57" maxlength="40" value="${(productCategory.categoryName)?if_exists}"/>
                    </div>
                </div>
            </div>

            <div class="am-form-group am-g">
                <label class="am-control-label am-u-md-5 am-u-lg-5 am-u-end">${uiLabelMap.ProductProductCategoryDescription}</label>
                <div class="am-u-md-7 am-u-lg-7 am-u-end">
                    <div class="am-input-group">
                        <textarea name="description" cols="50" rows="2"><#if productCategory?has_content>${(productCategory.description)?if_exists}</#if></textarea>
                    </div>
                </div>
            </div>

            <div class="am-form-group am-g">
                <label class="am-control-label am-u-md-5 am-u-lg-5 am-u-end">${uiLabelMap.ProductPrimaryParentCategory}</label>
                <div class="am-u-md-5 am-u-lg-5 am-u-end">
                    <@amazeHtmlTemplate.lookupField value="${(productCategory.primaryParentCategoryId)?default('')}" formName="productCategoryForm" name="primaryParentCategoryId" id="primaryParentCategoryId" fieldFormName="LookupProductCategory"/>
                </div>
            </div>

            <div class="am-form-group am-g">
                <label class="am-control-label am-u-md-5 am-u-lg-5">
                ${uiLabelMap.ProductCategoryImageUrl}
                <#if (productCategory.categoryImageUrl)?exists>
                    <a href="<@ofbizContentUrl>${(productCategory.categoryImageUrl)?if_exists}</@ofbizContentUrl>" target="_blank"><img alt="Category Image" src="<@ofbizContentUrl>${(productCategory.categoryImageUrl)?if_exists}</@ofbizContentUrl>" class="cssImgSmall" />
                    </a>
                </#if></label>
                <div class="am-u-md-7 am-u-lg-7 am-u-end">
                    <div class="am-input-group">
                        <input  class="am-form-field am-input-sm"  type="text" name="categoryImageUrl" size="57" value="${(productCategory.categoryImageUrl)?default('')}"/>
                    </div>
                <#if productCategory?has_content>
                    <div>
                    ${uiLabelMap.ProductInsertDefaultImageUrl}:
                        <a href="javascript:insertImageName('category','${imageNameCategory}.jpg');" class="buttontext">.jpg</a>
                        <a href="javascript:insertImageName('category','${imageNameCategory}.gif');" class="buttontext">.gif</a>
                        <a href="javascript:insertImageName('category','');" class="buttontext">${uiLabelMap.CommonClear}</a>
                    </div>
                </#if>
                </div>
            </div>

            <div class="am-form-group am-g">
                <label class="am-control-label am-u-md-5 am-u-lg-5">
                ${uiLabelMap.ProductLinkOneImageUrl}
                <#if (productCategory.linkOneImageUrl)?exists>
                    <a href="<@ofbizContentUrl>${(productCategory.linkOneImageUrl)?if_exists}</@ofbizContentUrl>" target="_blank"><img alt="Link One Image" src="<@ofbizContentUrl>${(productCategory.linkOneImageUrl)?if_exists}</@ofbizContentUrl>" class="cssImgSmall" /></a>
                </#if></label>
                <div class="am-u-md-7 am-u-lg-7 am-u-end">
                    <div class="am-input-group">
                        <input  class="am-form-field am-input-sm"  type="text" name="linkOneImageUrl" size="57" value="${(productCategory.linkOneImageUrl)?default('')}"/>
                    </div>
                <#if productCategory?has_content>
                    <div>
                    ${uiLabelMap.ProductInsertDefaultImageUrl}:
                        <a href="javascript:insertImageName('linkOne','${imageNameLinkOne}.jpg');" class="buttontext">.jpg</a>
                        <a href="javascript:insertImageName('linkOne','${imageNameLinkOne}.gif');" class="buttontext">.gif</a>
                        <a href="javascript:insertImageName('linkOne','');" class="buttontext">${uiLabelMap.CommonClear}</a>
                    </div>
                </#if>
                </div>
            </div>

            <div class="am-form-group am-g">
                <label class="am-control-label am-u-md-5 am-u-lg-5">
                ${uiLabelMap.ProductLinkTwoImageUrl}
                <#if (productCategory.linkTwoImageUrl)?exists>
                    <a href="<@ofbizContentUrl>${(productCategory.linkTwoImageUrl)?if_exists}</@ofbizContentUrl>" target="_blank"><img alt="Link One Image" src="<@ofbizContentUrl>${(productCategory.linkTwoImageUrl)?if_exists}</@ofbizContentUrl>" class="cssImgSmall" /></a>
                </#if></label>
                <div class="am-u-md-7 am-u-lg-7 am-u-end">
                    <div class="am-input-group">
                        <input  class="am-form-field am-input-sm"  type="text" name="linkTwoImageUrl" size="57" value="${(productCategory.linkTwoImageUrl)?default('')}"/>
                    </div>
                <#if productCategory?has_content>
                    <div>
                    ${uiLabelMap.ProductInsertDefaultImageUrl}:
                        <a href="javascript:insertImageName('linkTwo','${imageNameLinkTwo}.jpg');" class="buttontext">.jpg</a>
                        <a href="javascript:insertImageName('linkTwo','${imageNameLinkTwo}.gif');" class="buttontext">.gif</a>
                        <a href="javascript:insertImageName('linkTwo','');" class="buttontext">${uiLabelMap.CommonClear}</a>
                    </div>
                </#if>
                </div>
            </div>

            <div class="am-form-group am-g">
                <label class="am-control-label am-u-md-5 am-u-lg-5">
                    ${uiLabelMap.ProductDetailScreen}
                </label>
                <div class="am-u-md-7 am-u-lg-7 am-u-end">
                    <div class="am-input-group">
                        <input  class="am-form-field am-input-sm"  type="text" <#if productCategory?has_content>value="${productCategory.detailScreen?if_exists}"</#if> name="detailScreen"  size="57" />
                    </div>
                    <div>
                        ${uiLabelMap.ProductDefaultsTo} &quot;categorydetail&quot;, ${uiLabelMap.ProductDetailScreenMessage}: &quot;component://ecommerce/widget/CatalogScreens.xml#categorydetail&quot;</span>
                    </div>
                </div>
            </div>

            <div class="am-form-group am-g">
                <label class="am-control-label am-u-md-5 am-u-lg-5 am-u-end"></label>
                <div class="am-u-md-7 am-u-lg-7 ">
                    <div class="am-input-group">
                        <span style="margin-left: 180px"><input type="submit" class="am-btn am-btn-primary am-btn-sm" name="Update" value="${uiLabelMap.CommonUpdate}" /></span>
                    </div>
                </div>
            </div>
        </form>
    </div>
    </div>
    </div>
    </div>
    </div>
<#if productCategoryId?has_content>
    <script language="JavaScript" type="text/javascript">
        function setUploadUrl(newUrl) {
        var toExec = 'document.imageUploadForm.action="' + newUrl + '";';
        eval(toExec);
        }
    </script>
    <div class="am-cf am-padding-xs">
    <div class="am-panel am-panel-default">
        <div class="am-panel-hd am-cf">${uiLabelMap.ProductCategoryUploadImage}</div>
    <div class="am-panel-bd am-collapse am-in">
    <div class="am-g">
    <div class="am-u-lg-10">
        <form class="am-form am-form-horizontal" method="post" enctype="multipart/form-data" action="<@ofbizUrl>UploadCategoryImage?productCategoryId=${productCategoryId?if_exists}&amp;upload_file_type=category</@ofbizUrl>" name="imageUploadForm">
                <div class="am-form-group am-g">
                    <div class="am-u-md-5 am-u-lg-5 am-u-end"><input class="am-form-field am-input-sm" type="file"  size="50" name="fname"/></div>
                    <div class="am-u-md-7 am-u-lg-7 am-u-end">
                        <input type="radio" name="upload_file_type_bogus" value="category" checked="checked" onclick='setUploadUrl("<@ofbizUrl>UploadCategoryImage?productCategoryId=${productCategoryId}&amp;upload_file_type=category</@ofbizUrl>");'/>${uiLabelMap.ProductCategoryImageUrl}
                        <input type="radio" name="upload_file_type_bogus" value="linkOne" onclick='setUploadUrl("<@ofbizUrl>UploadCategoryImage?productCategoryId=${productCategoryId}&amp;upload_file_type=linkOne</@ofbizUrl>");'/>${uiLabelMap.ProductLinkOneImageUrl}
                        <input type="radio" name="upload_file_type_bogus" value="linkTwo"onclick='setUploadUrl("<@ofbizUrl>UploadCategoryImage?productCategoryId=${productCategoryId}&amp;upload_file_type=linkTwo</@ofbizUrl>");'/>${uiLabelMap.ProductLinkTwoImageUrl}
                        <input type="submit" class="am-btn am-btn-primary am-btn-sm" value="${uiLabelMap.ProductUploadImage}"/>
                    </div>
                </div>
        </form>
    </div>
    </div>
    </div>
    </div>
    </div>

    <!-- 复制产品分类 暂时先注释掉 -->
    <#--<div class="am-cf am-padding-xs">
    <div class="am-panel am-panel-default">
        <div class="am-panel-hd am-cf">${uiLabelMap.ProductDuplicateProductCategory}</div>
    <div class="am-panel-bd am-collapse am-in">
    <div class="am-g">
    <div class="am-u-lg-12">
        <form action="<@ofbizUrl>DuplicateProductCategory</@ofbizUrl>" method="post" style="margin: 0;">
            <div class="am-form-group am-g">
                <div class="am-u-md-5 am-u-lg-5">
                    <div class="am-g">
                        <label class="am-control-label am-u-md-4 am-u-lg-4"> ${uiLabelMap.ProductDuplicateProductCategorySelected}:</label>
                        <div class="am-u-md-6 am-u-lg-6 am-form-group am-u-end">
                            <input type="text" size="20" maxlength="20" class="am-form-field am-input-sm" name="productCategoryId"/>&nbsp;
                        </div>
                        <input type="submit" class="am-btn am-btn-primary am-btn-sm" value="${uiLabelMap.CommonGo}"/>
                    </div>
                </div>
                <div class="am-u-md-7 am-u-lg-7 am-u-end">
                    <b>${uiLabelMap.CommonDuplicate}:</b>
                ${uiLabelMap.ProductCategoryContent}&nbsp;<input type="checkbox" name="duplicateContent" value="Y" checked="checked" />
                ${uiLabelMap.ProductCategoryRollupParentCategories}&nbsp;<input type="checkbox" name="duplicateParentRollup" value="Y" checked="checked" />
                ${uiLabelMap.ProductCategoryRollupChildCategories}&nbsp;<input type="checkbox" name="duplicateChildRollup" value="Y" />
                ${uiLabelMap.ProductProducts}&nbsp;<input type="checkbox" name="duplicateMembers" value="Y" checked="checked" />
                ${uiLabelMap.ProductCatalogs}&nbsp;<input type="checkbox" name="duplicateCatalogs" value="Y" checked="checked" />
                ${uiLabelMap.ProductFeatures}&nbsp;<input type="checkbox" name="duplicateFeatures" value="Y" checked="checked" />
                ${uiLabelMap.PartyParties}&nbsp;<input type="checkbox" name="duplicateRoles" value="Y" checked="checked" />
                ${uiLabelMap.ProductAttributes}&nbsp;<input type="checkbox" name="duplicateAttributes" value="Y" checked="checked" />
                </div>
            </div>
        </form>
    </div>
    </div>
    </div>
    </div>
    </div>
    </div>-->


</#if>
