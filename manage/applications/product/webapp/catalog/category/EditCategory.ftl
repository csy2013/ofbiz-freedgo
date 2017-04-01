<script language="JavaScript" type="text/javascript">
    function insertImageName(type, nameValue) {
        eval('document.productCategoryForm.' + type + 'ImageUrl.value=nameValue;');
    }

</script>
<#--<#if fileType?has_content>
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
</#if>-->

<#if !productCategory?has_content>
    <#if productCategoryId?has_content>
        <@htmlScreenTemplate.renderScreenletBegin id="" title="${uiLabelMap.ProductCouldNotFindProductCategoryWithId} ${productCategoryId}" collapsible="" showMore=true/>
    <form action="<@ofbizUrl>createProductCategory</@ofbizUrl>" method="post" class="form-horizontal" name="productCategoryForm">
        <div class="form-group">
            <label class="col-md-4 control-label">${uiLabelMap.ProductProductCategoryId}</label>

            <div class="col-md-4">
                <input type="text" name="productCategoryId" size="20" maxlength="40" value="${productCategoryId}" class="form-control"/>
            </div>
        </div>
    <#else>
        <@htmlScreenTemplate.renderScreenletBegin id="" title="${uiLabelMap.PageTitleCreateProductCategory}" collapsible="" showMore=true/>
    <form action="<@ofbizUrl>createProductCategory</@ofbizUrl>" method="post" class="form-horizontal" name="productCategoryForm" data-parsley-validate="true">

        <div class="form-group">
            <label class="col-md-4 control-label">${uiLabelMap.ProductProductCategoryId}</label>

            <div class="col-md-4 ">
                <input type="text" name="productCategoryId" size="20" maxlength="40" value="" class="form-control"/>
            </div>
        </div>
    </#if>
<#else>
    <@htmlScreenTemplate.renderScreenletBegin id="" title="${uiLabelMap.PageTitleEditProductCategories}" collapsible="" showMore=true/>
<form action="<@ofbizUrl>updateProductCategory</@ofbizUrl>" method="post" class="form-horizontal" name="productCategoryForm" data-parsley-validate="true">
    <input type="hidden" name="productCategoryId" value="${productCategoryId}"/>
    <div class="form-group">
        <label class="col-md-4 control-label">${uiLabelMap.ProductProductCategoryId}</label>

        <div class="col-md-4 ">
            <b>${productCategoryId}</b> (${uiLabelMap.ProductNotModificationRecreationCategory}.)
        </div>
    </div>
</#if>
    <div class="form-group">
        <label class="col-md-4 control-label">${uiLabelMap.ProductProductCategoryType}</label>
        <div class="col-md-4 ">
            <div class="input-group">
                <select name="productCategoryTypeId" class="form-control">

                <#assign selectedKey = "">
                <#list productCategoryTypes as productCategoryTypeData>
                    <#if productCategoryTypeData.productCategoryTypeId == 'BEST_SELL_CATEGORY'||productCategoryTypeData.productCategoryTypeId == 'CATALOG_CATEGORY'||productCategoryTypeData.productCategoryTypeId == 'CROSS_SELL_CATEGORY'
                    ||productCategoryTypeData.productCategoryTypeId == 'GIFT_CARD_CATEGORY'|| productCategoryTypeData.productCategoryTypeId =='QUICKADD_CATEGORY'>
                        <#if requestParameters.productCategoryTypeId?has_content>
                            <#assign selectedKey = requestParameters.productCategoryTypeId>
                        <#elseif (productCategory?has_content && productCategory.productCategoryTypeId?if_exists == productCategoryTypeData.productCategoryTypeId)>
                            <#assign selectedKey = productCategory.productCategoryTypeId>
                        <#else>
                            <#assign selectedKey = "CATALOG_CATEGORY">
                        </#if>
                        <option <#if selectedKey == productCategoryTypeData.productCategoryTypeId?if_exists>selected="selected"</#if>
                                value="${productCategoryTypeData.productCategoryTypeId}">${productCategoryTypeData.get("description",locale)}</option>
                    </#if>
                </#list>
                </select>
            </div>
        </div>
    </div>

    <div class="form-group">
        <label class="col-md-4 control-label">${uiLabelMap.ProductCatalogShowInTop}</label>

        <div class="col-md-4 ">
            <select name="showInShop" class="form-control input-sm" size="1">

                <option value="Y" <#if productCategory.showInShop?default("Y") == 'Y'>selected</#if>>是</option>
                <option value="N" <#if productCategory.showInShop?default("Y") == 'N'>selected</#if>>否</option>
            </select>
        </div>
    </div>
    <div class="form-group">
        <label class="col-md-4 control-label">${uiLabelMap.ProductProductCategoryName}</label>

        <div class="col-md-4 "><input type="text" class="form-control" value="${(productCategory.categoryName)?if_exists}" name="categoryName" data-parsley-required="true"/></div>
    </div>
    <div class="form-group">
        <label class="col-md-4 control-label">${uiLabelMap.ProductProductCategoryDescription}</label>

        <div class="col-md-4 "><textarea name="description" class="form-control" cols="60" data-parsley-required="true"
                                         rows="4"><#if productCategory?has_content>${(productCategory.description)?if_exists}</#if></textarea></div>
    </div>
<#--<div class="form-group">
    <label class="col-md-4 control-label">${uiLabelMap.ProductCategoryImageUrl}

    <#if (productCategory.categoryImageUrl)?exists>
        <a href="<@ofbizContentUrl>${(productCategory.categoryImageUrl)?if_exists}</@ofbizContentUrl>" target="_blank">
            <img alt="Category Image" src="<@ofbizContentUrl>${(productCategory.categoryImageUrl)?if_exists}</@ofbizContentUrl>" class="cssImgSmall"/></a>
    </#if></label>

    <div class="col-md-4 ">
        <input type="text" name="categoryImageUrl" class="form-control" value="${(productCategory.categoryImageUrl)?default('')}" size="60" maxlength="255"/>
    <#if productCategory?has_content>
    ${uiLabelMap.ProductInsertDefaultImageUrl}:
        <a href="javascript:insertImageName('category','${imageNameCategory}.jpg');" class="buttontext">.jpg</a>
        <a href="javascript:insertImageName('category','${imageNameCategory}.gif');" class="buttontext">.gif</a>
        <a href="javascript:insertImageName('category','');" class="buttontext">${uiLabelMap.CommonClear}</a>
    </#if>
    </div>
</div>
<div class="form-group">
    <label class="col-md-4 control-label">${uiLabelMap.ProductLinkOneImageUrl}
    <#if (productCategory.linkOneImageUrl)?exists>
        <a href="<@ofbizContentUrl>${(productCategory.linkOneImageUrl)?if_exists}</@ofbizContentUrl>" target="_blank"><img alt="Link One Image"
                                                                                                                           src="<@ofbizContentUrl>${(productCategory.linkOneImageUrl)?if_exists}</@ofbizContentUrl>"
                                                                                                                           class="cssImgSmall"/></a>
    </#if>
    </label>

    <div class="col-md-4 ">
        <input type="text" name="linkOneImageUrl" class="form-control" value="${(productCategory.linkOneImageUrl)?default('')}" size="60" maxlength="255"/>
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
<div class="form-group">
    <label class="col-md-4 control-label">${uiLabelMap.ProductLinkTwoImageUrl}
    <#if (productCategory.linkTwoImageUrl)?exists>
        <a href="<@ofbizContentUrl>${(productCategory.linkTwoImageUrl)?if_exists}</@ofbizContentUrl>" target="_blank"><img alt="Link One Image"
                                                                                                                           src="<@ofbizContentUrl>${(productCategory.linkTwoImageUrl)?if_exists}</@ofbizContentUrl>"
                                                                                                                           class="cssImgSmall"/></a>
    </#if>
    </label>

    <div class="col-md-4 ">
        <input type="text" name="linkTwoImageUrl" class="form-control" value="${(productCategory.linkTwoImageUrl)?default('')}" size="60" maxlength="255"/>
    <#if productCategory?has_content>
        <div class="text-info">
        ${uiLabelMap.ProductInsertDefaultImageUrl}:
            <a href="javascript:insertImageName('linkTwo','${imageNameLinkTwo}.jpg');" class="buttontext">.jpg</a>
            <a href="javascript:insertImageName('linkTwo','${imageNameLinkTwo}.gif');" class="buttontext">.gif</a>
            <a href="javascript:insertImageName('linkTwo','');" class="buttontext">${uiLabelMap.CommonClear}</a>
        </div>
    </#if>
    </div>
</div>
<div class="form-group">
    <label class="col-md-4 control-label">${uiLabelMap.ProductDetailScreen}</label>

    <div class="col-md-4 ">
        <input type="text" <#if productCategory?has_content>value="${productCategory.detailScreen?if_exists}"</#if> class="form-control" name="detailScreen" size="60"
               maxlength="250"/>
        <br/><span class="tooltip">${uiLabelMap.ProductDefaultsTo} &quot;categorydetail&quot;, ${uiLabelMap.ProductDetailScreenMessage}: &quot;component://ecommerce/widget/CatalogScreens.xml#categorydetail&quot;</span>
    </div>
</div>
    <div class="form-group">
        <label class="col-md-4 control-label">${uiLabelMap.ProductPrimaryParentCategory}</label>

        <div class="col-md-4 ">
        <@htmlTemplate.lookupField value="${(productCategory.primaryParentCategoryId)?default('')}" formName="productCategoryForm" name="primaryParentCategoryId" id="primaryParentCategoryId" fieldFormName="LookupProductCategory"/>
        </div>
    </div>-->
    <div class="form-group">
        <div class="col-md-4">&nbsp;</div>
        <div class="col-md-4"><input type="submit" name="Update" value="${uiLabelMap.CommonUpdate}" class="btn btn-primary btn-sm pull-right"/></div>
    </div>

</form>
<@htmlScreenTemplate.renderScreenletEnd/>
<#--<#if productCategoryId?has_content>
    <script language="JavaScript" type="text/javascript">
        function setUploadUrl(newUrl) {
            var toExec = 'document.imageUploadForm.action="' + newUrl + '";';
            eval(toExec);
        }

    </script>
    <@htmlScreenTemplate.renderScreenletBegin id="" title="${uiLabelMap.ProductCategoryUploadImage}" collapsible="" showMore=true/>
    <form method="post" enctype="multipart/form-data" class="form-inline"
          action="<@ofbizUrl>UploadCategoryImage?productCategoryId=${productCategoryId?if_exists}&amp;upload_file_type=category</@ofbizUrl>" name="imageUploadForm">
        <div class="form-group">
            <div class="col-md-12">
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
                <label class="radio-inline">
                    <input type="radio" name="upload_file_type_bogus" value="linkOne"
                           onclick='setUploadUrl("<@ofbizUrl>UploadCategoryImage?productCategoryId=${productCategoryId}&amp;upload_file_type=linkOne</@ofbizUrl>");'/>${uiLabelMap.ProductLinkOneImageUrl}
                </label>
                <label class="radio-inline">
                    <input type="radio" name="upload_file_type_bogus" value="linkTwo"
                           onclick='setUploadUrl("<@ofbizUrl>UploadCategoryImage?productCategoryId=${productCategoryId}&amp;upload_file_type=linkTwo</@ofbizUrl>");'/>${uiLabelMap.ProductLinkTwoImageUrl}
                </label>
                <input type="submit" class="btn btn-primary btn-sm m-l-10" value="${uiLabelMap.ProductUploadImage}"/>
            </div>
        </div>
    </form>
    <@htmlScreenTemplate.renderScreenletEnd/>
    <@htmlScreenTemplate.renderScreenletBegin id="" title="${uiLabelMap.ProductDuplicateProductCategory}" collapsible="" showMore=true/>
    <form action="<@ofbizUrl>DuplicateProductCategory</@ofbizUrl>" method="post" class="form-inline">
        <input type="hidden" name="oldProductCategoryId" value="${productCategoryId}"/>

        <div class="input-group">
            <label class="input-group-addon">
            ${uiLabelMap.ProductDuplicateProductCategorySelected}:</label>

            <div class="input-group-addon">
                <input type="text" class="form-control" size="20" maxlength="20" name="productCategoryId"/>&nbsp;
                <input type="submit" class="btn btn-primary btn-sm" value="${uiLabelMap.CommonGo}"/>
            </div>
        </div>
        <div class="form-group ">

            复制选项：
            <label class="checkbox"><input type="checkbox" name="duplicateContent" value="Y" checked="checked"/>${uiLabelMap.ProductCategoryContent}&nbsp;</label>
            <label class="checkbox"><input type="checkbox" name="duplicateParentRollup" value="Y" checked="checked"/>${uiLabelMap.ProductCategoryRollupParentCategories}&nbsp;
            </label>
            <label class="checkbox"><input type="checkbox" name="duplicateChildRollup" value="Y"/>${uiLabelMap.ProductCategoryRollupChildCategories}&nbsp;</label>
            <label class="checkbox"><input type="checkbox" name="duplicateMembers" value="Y" checked="checked"/>${uiLabelMap.ProductProducts}&nbsp;</label>
            <label class="checkbox"><input type="checkbox" name="duplicateCatalogs" value="Y" checked="checked"/>${uiLabelMap.ProductCatalogs}&nbsp;</label>
            <label class="checkbox"><input type="checkbox" name="duplicateFeatures" value="Y" checked="checked"/> ${uiLabelMap.ProductFeatures}&nbsp;</label>
            <label class="checkbox"><input type="checkbox" name="duplicateRoles" value="Y" checked="checked"/>${uiLabelMap.PartyParties}&nbsp;</label>
            <label class="checkbox"><input type="checkbox" name="duplicateAttributes" value="Y" checked="checked"/>${uiLabelMap.ProductAttributes}&nbsp;</label>

        </div>

    </form>
    <@htmlScreenTemplate.renderScreenletEnd/>
</#if>-->
