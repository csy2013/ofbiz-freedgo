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
<div class="am-panel-bd am-collapse am-in">
    <div class="am-g">
        <div class="am-u-lg-10">
            <form id="addAdditionalImagesForm" class="am-form am-form-horizontal" method="post" action="<@ofbizUrl>addAdditionalImagesForProduct</@ofbizUrl>" enctype="multipart/form-data">
                <input id="additionalImageProductId" type="hidden" name="productId" value="${productId?if_exists}" />
                <div class="am-form-group am-g">
                    <label class="am-control-label am-u-md-5 am-u-lg-5"><#if productAdditionalImage1?has_content><img vspace="5" hspace="5" width="150" height="150" src="<@ofbizContentUrl>${productAdditionalImage1}</@ofbizContentUrl>" class="cssImgSmall" alt="" /></#if></label>
                    <div class="am-u-md-5 am-u-lg-5 am-u-end">
                        <input class="am-form-field am-input-sm" id="additionalImageOne" type="file" size="20" name="additionalImageOne" />
                    </div>
                </div>
                <div class="am-form-group am-g">
                    <label class="am-control-label am-u-md-5 am-u-lg-5"><#if productAdditionalImage2?has_content><img vspace="5" hspace="5" width="150" height="150" src="<@ofbizContentUrl>${productAdditionalImage2}</@ofbizContentUrl>" class="cssImgSmall" alt="" /></#if></label>
                    <div class="am-u-md-5 am-u-lg-5 am-u-end">
                        <input class="am-form-field am-input-sm" id="additionalImageTwo" type="file" size="20" name="additionalImageTwo" />
                    </div>
                </div>
                <div class="am-form-group am-g">
                    <label class="am-control-label am-u-md-5 am-u-lg-5"><#if productAdditionalImage3?has_content><img vspace="5" hspace="5" width="150" height="150" src="<@ofbizContentUrl>${productAdditionalImage3}</@ofbizContentUrl>" class="cssImgSmall" alt="" /></#if></label>
                    <div class="am-u-md-5 am-u-lg-5 am-u-end">
                        <input class="am-form-field am-input-sm" id="additionalImageThree" type="file" size="20" name="additionalImageThree" />
                    </div>
                </div>
                <div class="am-form-group am-g">
                    <label class="am-control-label am-u-md-5 am-u-lg-5"><#if productAdditionalImage4?has_content><img  vspace="5" hspace="5" width="150" height="150" src="<@ofbizContentUrl>${productAdditionalImage4}</@ofbizContentUrl>" class="cssImgSmall" alt="" /></#if></label>
                    <div class="am-u-md-5 am-u-lg-5 am-u-end">
                        <input class="am-form-field am-input-sm" id="additionalImageFour" type="file" size="20" name="additionalImageFour" />
                    </div>
                </div>
                <div class="am-form-group am-g">
                    <label class="am-control-label am-u-md-5 am-u-lg-5"><#if productAdditionalImage5?has_content><img  vspace="5" hspace="5" width="150" height="150" src="<@ofbizContentUrl>${productAdditionalImage5}</@ofbizContentUrl>" class="cssImgSmall" alt="" /></#if></label>
                    <div class="am-u-md-5 am-u-lg-5 am-u-end">
                        <input class="am-form-field am-input-sm" id="additionalImageFive" type="file" size="20" name="additionalImageFive" />
                    </div>
                </div>
                <div class="am-form-group am-g">
                    <label class="am-control-label am-u-md-5 am-u-lg-5"><#if productAdditionalImage6?has_content><img  vspace="5" hspace="5" width="150" height="150" src="<@ofbizContentUrl>${productAdditionalImage6}</@ofbizContentUrl>" class="cssImgSmall" alt="" /></#if></label>
                    <div class="am-u-md-5 am-u-lg-5 am-u-end">
                        <input class="am-form-field am-input-sm" id="additionalImageSix" type="file" size="20" name="additionalImageSix" />
                    </div>
                </div>
                <div class="am-form-group am-g">
                    <label class="am-control-label am-u-md-5 am-u-lg-5"><#if productAdditionalImage7?has_content><img  vspace="5" hspace="5" width="150" height="150" src="<@ofbizContentUrl>${productAdditionalImage7}</@ofbizContentUrl>" class="cssImgSmall" alt="" /></#if></label>
                    <div class="am-u-md-5 am-u-lg-5 am-u-end">
                        <input class="am-form-field am-input-sm" id="additionalImageSeven" type="file" size="20" name="additionalImageSeven" />
                    </div>
                </div>
                <div class="am-form-group am-g">
                    <label class="am-control-label am-u-md-5 am-u-lg-5"><#if productAdditionalImage8?has_content><img  vspace="5" hspace="5" width="150" height="150" src="<@ofbizContentUrl>${productAdditionalImage8}</@ofbizContentUrl>" class="cssImgSmall" alt="" /></#if></label>
                    <div class="am-u-md-5 am-u-lg-5 am-u-end">
                        <input class="am-form-field am-input-sm" id="additionalImageEight" type="file" size="20" name="additionalImageEight" />
                    </div>
                </div>
                <div class="am-form-group am-g">
                    <label class="am-control-label am-u-md-5 am-u-lg-5">&nbsp;</label>
                    <div class="am-u-md-5 am-u-lg-5 am-u-end">
                        <input class="am-btn am-btn-primary am-btn-sm" type="submit" value='${uiLabelMap.CommonUpload}' />
                    </div>
                </div>
            </form>
        </div>
    </div>
</div>
