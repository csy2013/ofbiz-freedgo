<#if product?has_content>
    <#assign productAdditionalImage1 = (Static["org.ofbiz.product.product.ProductContentWrapper"].getProductContentAsText(product, "ADDITIONAL_IMAGE_1", locale, dispatcher))?if_exists />
    <#assign productAdditionalImage2 = (Static["org.ofbiz.product.product.ProductContentWrapper"].getProductContentAsText(product, "ADDITIONAL_IMAGE_2", locale, dispatcher))?if_exists />
    <#assign productAdditionalImage3 = (Static["org.ofbiz.product.product.ProductContentWrapper"].getProductContentAsText(product, "ADDITIONAL_IMAGE_3", locale, dispatcher))?if_exists />
    <#assign productAdditionalImage4 = (Static["org.ofbiz.product.product.ProductContentWrapper"].getProductContentAsText(product, "ADDITIONAL_IMAGE_4", locale, dispatcher))?if_exists />
    <#assign productAdditionalImage5 = (Static["org.ofbiz.product.product.ProductContentWrapper"].getProductContentAsText(product, "ADDITIONAL_IMAGE_5", locale, dispatcher))?if_exists />
    <#assign productAdditionalImage6 = (Static["org.ofbiz.product.product.ProductContentWrapper"].getProductContentAsText(product, "ADDITIONAL_IMAGE_6", locale, dispatcher))?if_exists />
    <#assign productAdditionalImage7 = (Static["org.ofbiz.product.product.ProductContentWrapper"].getProductContentAsText(product, "ADDITIONAL_IMAGE_7", locale, dispatcher))?if_exists />
    <#assign productAdditionalImage8 = (Static["org.ofbiz.product.product.ProductContentWrapper"].getProductContentAsText(product, "ADDITIONAL_IMAGE_8", locale, dispatcher))?if_exists />
</#if>
<form id="addAdditionalImagesForm" method="post" action="<@ofbizUrl>addAdditionalImagesForProduct</@ofbizUrl>" enctype="multipart/form-data" class="form-horizontal">
    <input id="additionalImageProductId" type="hidden" name="productId" value="${productId?if_exists}"/>

    <div class="row form-group">
        <div class="col-xs-3">
            <div class="col-xs-4"><#if productAdditionalImage1?has_content><a href="javascript:void(0);" swapDetail="<@ofbizContentUrl>${productAdditionalImage1}</@ofbizContentUrl>"><img
                    src="<@ofbizContentUrl>${productAdditionalImage1}</@ofbizContentUrl>" class="cssImgSmall" alt=""/></a></#if>
            </div>
            <div class="col-xs-8"><input id="additionalImageOne" type="file" size="20" name="additionalImageOne" class="w100"/>
            </div>
        </div>
        <div class="col-xs-3">
            <div class="col-xs-4"><#if productAdditionalImage2?has_content><a href="javascript:void(0);" swapDetail="<@ofbizContentUrl>${productAdditionalImage2}</@ofbizContentUrl>"><img
                    src="<@ofbizContentUrl>${productAdditionalImage2}</@ofbizContentUrl>" class="cssImgSmall" alt=""/></a></#if>
            </div>
            <div class="col-xs-8">
                <input type="file" size="20" name="additionalImageTwo" class="w100"/>
            </div>
        </div>
        <div class="col-xs-3">
            <div class="col-xs-4"><#if productAdditionalImage3?has_content><a href="javascript:void(0);" swapDetail="<@ofbizContentUrl>${productAdditionalImage3}</@ofbizContentUrl>"><img
                    src="<@ofbizContentUrl>${productAdditionalImage3}</@ofbizContentUrl>" class="cssImgSmall" alt=""/></a></#if>
            </div>
            <div class="col-xs-8"><input type="file" size="20" name="additionalImageThree" class="w100"/>
            </div>
        </div>
        <div class="col-xs-3">
            <div class="col-xs-4"><#if productAdditionalImage4?has_content><a href="javascript:void(0);" swapDetail="<@ofbizContentUrl>${productAdditionalImage4}</@ofbizContentUrl>"><img
                    src="<@ofbizContentUrl>${productAdditionalImage4}</@ofbizContentUrl>" class="cssImgSmall" alt=""/></a></#if>
            </div>
            <div class="col-xs-8"><input type="file" size="20" name="additionalImageFour" class="w100"/>
            </div>
        </div>
    </div>
    <div class="row">

        <div class="col-xs-3">
            <div class="col-xs-4"><#if productAdditionalImage5?has_content><a href="javascript:void(0);" swapDetail="<@ofbizContentUrl>${productAdditionalImage5}</@ofbizContentUrl>"><img
                    src="<@ofbizContentUrl>${productAdditionalImage5}</@ofbizContentUrl>" class="cssImgSmall" alt=""/></a></#if>
            </div>
            <div class="col-xs-8"><input id="additionalImageOne" type="file" size="20" name="additionalImageFive" class="w100"/>
            </div>
        </div>
        <div class="col-xs-3">
            <div class="col-xs-4"><#if productAdditionalImage6?has_content><a href="javascript:void(0);" swapDetail="<@ofbizContentUrl>${productAdditionalImage6}</@ofbizContentUrl>"><img
                    src="<@ofbizContentUrl>${productAdditionalImage6}</@ofbizContentUrl>" class="cssImgSmall" alt=""/></a></#if>
            </div>
            <div class="col-xs-8">
                <input type="file" size="20" name="additionalImageSix" class="w100"/>
            </div>
        </div>
        <div class="col-xs-3">
            <div class="col-xs-4"><#if productAdditionalImage7?has_content><a href="javascript:void(0);" swapDetail="<@ofbizContentUrl>${productAdditionalImage7}</@ofbizContentUrl>"><img
                    src="<@ofbizContentUrl>${productAdditionalImage7}</@ofbizContentUrl>" class="cssImgSmall" alt=""/></a></#if>
            </div>
            <div class="col-xs-8"><input type="file" size="20" name="additionalImageSeven" class="w100"/>
            </div>
        </div>
        <div class="col-xs-3">
            <div class="col-xs-4"><#if productAdditionalImage8?has_content><a href="javascript:void(0);" swapDetail="<@ofbizContentUrl>${productAdditionalImage8}</@ofbizContentUrl>"><img
                    src="<@ofbizContentUrl>${productAdditionalImage8}</@ofbizContentUrl>" class="cssImgSmall" alt=""/></a></#if>
            </div>
            <div class="col-xs-8"><input type="file" size="20" name="additionalImageEight" class="w100"/>
            </div>
        </div>
        <div class="col-xs-4 col-xs-offset-8" style="text-align:right">
            <input type="submit" value='${uiLabelMap.CommonUpload}'/>
        </div>
    </div>

    <div class="right">
        <a href="javascript:void(0);"><img id="detailImage" name="mainImage" vspace="5" hspace="5" width="150" height="150" style='margin-left:100px' src="" alt=""/></a>
        <input type="hidden" id="originalImage" name="originalImage"/>
    </div>
</form>
