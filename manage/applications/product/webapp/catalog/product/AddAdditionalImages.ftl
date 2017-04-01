<@htmlScreenTemplate.renderScreenletBegin id="uploadProductImgPanel" title="上传产品图片"/>
<form method="post" enctype="multipart/form-data"
      action="<@ofbizUrl>UploadProductImage?productId=${productId}&amp;upload_file_type=original</@ofbizUrl>" class="form-inline"
      name="imageUploadForm">
    <table cellspacing="0" class="basic-table">
        <div class="form-group">
            <input type="file" size="50" name="fname"/>
            <div class="input-group">
                <input type="radio" class="radio-inline" name="upload_file_type_bogus" value="small"
                       onclick='setUploadUrl("<@ofbizUrl>UploadProductImage?productId=${productId}&amp;upload_file_type=small</@ofbizUrl>");'/>${uiLabelMap.CommonSmall}
                <input type="radio" class="radio-inline" name="upload_file_type_bogus" value="medium"
                       onclick='setUploadUrl("<@ofbizUrl>UploadProductImage?productId=${productId}&amp;upload_file_type=medium</@ofbizUrl>");'/>${uiLabelMap.CommonMedium}
                <input type="radio" class="radio-inline" name="upload_file_type_bogus" value="large"
                       onclick='setUploadUrl("<@ofbizUrl>UploadProductImage?productId=${productId}&amp;upload_file_type=large</@ofbizUrl>");'/>${uiLabelMap.CommonLarge}
                <input type="radio" class="radio-inline" name="upload_file_type_bogus" value="detail"
                       onclick='setUploadUrl("<@ofbizUrl>UploadProductImage?productId=${productId}&amp;upload_file_type=detail</@ofbizUrl>");'/>${uiLabelMap.CommonDetail}
                <input type="radio" class="radio-inline" name="upload_file_type_bogus" value="original" checked="checked"
                       onclick='setUploadUrl("<@ofbizUrl>UploadProductImage?productId=${productId}&amp;upload_file_type=original</@ofbizUrl>");'/>${uiLabelMap.ProductOriginal}

                <input type="submit" value="${uiLabelMap.ProductUploadImage}" class="btn btn-primary btn-sm"/>

            </div>
        </div>
    </table>
    <span class="tooltip">${uiLabelMap.ProductOriginalImageMessage} : {ofbiz.home}/applications/product/config/ImageProperties.xml&quot;</span>
</form>
<@htmlScreenTemplate.renderScreenletEnd/>

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

<!-- ================== BEGIN PAGE LEVEL CSS STYLE ================== -->
<link href="/images/themes/coloradmin/plugins/jquery-file-upload/blueimp-gallery/blueimp-gallery.min.css" rel="stylesheet"/>
<link href="/images/themes/coloradmin/plugins/jquery-file-upload/css/jquery.fileupload.css" rel="stylesheet"/>
<link href="/images/themes/coloradmin/plugins/jquery-file-upload/css/jquery.fileupload-ui.css" rel="stylesheet"/>
<!-- ================== END PAGE LEVEL CSS STYLE ================== -->
<@htmlScreenTemplate.renderScreenletBegin id="uploadProductImgPanel" title="上传产品额外图片"/>
<form id="addAdditionalImagesForm" method="post" action="<@ofbizUrl>addAdditionalImagesForProduct</@ofbizUrl>" enctype="multipart/form-data" class="form-horizontal">
    <input id="additionalImageProductId" type="hidden" name="productId" value="${productId?if_exists}"/>

    <div class="row fileupload-buttonbar">
        <div class="col-md-7">
        <span class="btn btn-success fileinput-button">
            <i class="fa fa-plus"></i>
            <span>上传图片</span>
            <input type="file" name="files[]" multiple>
        </span>
            <button type="submit" class="btn btn-primary start">
                <i class="fa fa-upload"></i>
                <span>开始上传</span>
            </button>
            <button type="reset" class="btn btn-warning cancel">
                <i class="fa fa-ban"></i>
                <span>取消上传</span>
            </button>
            <button type="button" class="btn btn-danger delete">
                <i class="glyphicon glyphicon-trash"></i>
                <span>删除</span>
            </button>
            <!-- The global file processing state -->
            <span class="fileupload-process"></span>
        </div>
        <!-- The global progress state -->
        <div class="col-md-5 fileupload-progress fade">
            <!-- The global progress bar -->
            <div class="progress progress-striped active" role="progressbar" aria-valuemin="0" aria-valuemax="100">
                <div class="progress-bar progress-bar-success" style="width:0%;"></div>
            </div>
            <!-- The extended global progress state -->
            <div class="progress-extended">&nbsp;</div>
        </div>
    </div>
<#-- <table role="presentation" class="table table-striped">
     <tbody class="files"></tbody>

 </table>-->
    <table role="presentation" class="table table-striped">
        <tbody class="files">

        <#list [productAdditionalImage1,productAdditionalImage2,productAdditionalImage3,productAdditionalImage4,productAdditionalImage5,productAdditionalImage6,productAdditionalImage7,productAdditionalImage8]  as image>
            <#if image?has_content>

                <#assign smallImage = (Static["org.ofbiz.product.product.ProductContentWrapper"].getProductContentAsText(product, "XTRA_IMG_${image_index+1}_SMALL", locale, dispatcher))?if_exists />
            <tr class="template-download fade in">
                <td>
                <span class="preview">
                    <a href="${image}" title="original.png" download="original.png" data-gallery=""><img class="cssImgSmall" src="${smallImage}"></a>
                </span>
                </td>
                <td>
                    <p class="name">
                        <span>original.png</span>
                    </p>
                </td>
                <td>
                    <span class="size"></span>
                </td>
                <td>
                    <button class="btn btn-danger delete" data-datatype="json" data-data="productId=${productId}&amp;productContentTypeId=ADDITIONAL_IMAGE_${image_index+1}" data-type="POST"
                            data-url="removeProductImages">
                        <i class="glyphicon glyphicon-trash"></i>
                        <span>删除</span>
                    </button>
                    <input type="checkbox" name="delete" value="${image_index+1}" class="toggle">
                </td>
            </tr>
            </#if>
        </#list>
        <!-- The template to display files available for upload -->
        <script id="template-upload" type="text/x-tmpl">
        {% for (var i=0, file; file=o.files[i]; i++) { %}
            <tr class="template-upload fade">
                <td class="col-md-1">
                    <span class="preview"></span>
                </td>
                <td>
                    <p class="name">{%=file.name%}</p>
                    <strong class="error text-danger"></strong>
                </td>
                <td>
                    <p class="size">Processing...</p>
                    <div class="progress progress-striped active"><div class="progress-bar progress-bar-success" style="width:0%;"></div></div>
                </td>
                <td>
                    {% if (!i && !o.options.autoUpload) { %}
                        <button class="btn btn-primary btn-sm start" disabled>
                            <i class="fa fa-upload"></i>
                            <span>开始</span>
                        </button>
                    {% } %}
                    {% if (!i) { %}
                        <button class="btn btn-white btn-sm cancel">
                            <i class="fa fa-ban"></i>
                            <span>取消</span>
                        </button>
                    {% } %}
                </td>
            </tr>
        {% } %}





        </script>
        <!-- The template to display files available for download -->
        <script id="template-download" type="text/x-tmpl">
        {%
            for (var i=0, file; file=o.files[i]; i++) {
        %}
            <tr class="template-download fade">
                <td>
                    <span class="preview">
                        {% if (file.small_url) { %}
                            <a href="{%=file.url%}" title="{%=file.name%}" download="{%=file.name%}" data-gallery><img class="cssImgSmall" src="{%=file.small_url%}"></a>
                        {% } %}
                    </span>
                </td>
                <td>
                    <p class="name">
                        {% if (file.url) { %}
                            <a href="{%=file.url%}" title="{%=file.name%}" download="{%=file.name%}" {%=file.small_url?'data-gallery':''%}>{%=file.name%}</a>
                        {% } else { %}
                            <span>{%=file.name%}</span>
                        {% } %}
                    </p>
                    {% if (file.error) { %}
                        <div><span class="label label-danger">错误</span> {%=file.error%}</div>
                    {% } %}
                </td>
                <td>
                    <span class="size">{%=o.formatFileSize(file.size)%}</span>
                </td>
                <td>
                    {% if (file.delete_url) { %}
                        <button class="btn btn-danger delete" data-paramName={%=file.paramName%}  data-data="productId={%=file.productId%}&productContentTypeId={%=file.productContentTypeId%}" data-type="{%=file.delete_type%}" data-url="{%=file.delete_url%}" {% if (file.deleteWithCredentials) { %} data-xhr-fields='{"withCredentials":true}'{% } %}>
                            <i class="glyphicon glyphicon-trash"></i>
                            <span>删除</span>
                        </button>
                        <input type="checkbox" name="delete" value="1" class="toggle">
                    {% } else { %}
                        <button class="btn btn-warning cancel">
                            <i class="glyphicon glyphicon-ban-circle"></i>
                            <span>取消</span>
                        </button>
                    {% } %}
                </td>
            </tr>
        {% } %}

      </script>
        </tbody>
    </table>


    <!-- ================== BEGIN PAGE LEVEL JS ================== -->
    <script src="/images/themes/coloradmin/plugins/jquery-file-upload/js/vendor/jquery.ui.widget.js"></script>
    <script src="/images/themes/coloradmin/plugins/jquery-file-upload/js/vendor/tmpl.min.js"></script>
    <script src="/images/themes/coloradmin/plugins/jquery-file-upload/js/vendor/load-image.min.js"></script>
    <script src="/images/themes/coloradmin/plugins/jquery-file-upload/js/vendor/canvas-to-blob.min.js"></script>
    <script src="/images/themes/coloradmin/plugins/jquery-file-upload/blueimp-gallery/jquery.blueimp-gallery.min.js"></script>
    <script src="/images/themes/coloradmin/plugins/jquery-file-upload/js/jquery.iframe-transport.js"></script>
    <script src="/images/themes/coloradmin/plugins/jquery-file-upload/js/jquery.fileupload.js"></script>
    <script src="/images/themes/coloradmin/plugins/jquery-file-upload/js/jquery.fileupload-process.js"></script>
    <script src="/images/themes/coloradmin/plugins/jquery-file-upload/js/jquery.fileupload-image.js"></script>
    <script src="/images/themes/coloradmin/plugins/jquery-file-upload/js/jquery.fileupload-audio.js"></script>
    <script src="/images/themes/coloradmin/plugins/jquery-file-upload/js/jquery.fileupload-video.js"></script>
    <script src="/images/themes/coloradmin/plugins/jquery-file-upload/js/jquery.fileupload-validate.js"></script>
    <script src="/images/themes/coloradmin/plugins/jquery-file-upload/js/jquery.fileupload-ui.js"></script>
    <!--[if (gte IE 8)&(lt IE 10)]>
    <script src="/images/themes/coloradmin/plugins/jquery-file-upload/js/cors/jquery.xdr-transport.js"></script>
    <![endif]-->
    <script src="/images/themes/coloradmin/js/form-multiple-upload.product.min.js"></script>
    <!-- ================== END PAGE LEVEL JS ================== -->
    <#assign jsParamName = "[">
    <#if !productAdditionalImage1?has_content><#assign jsParamName = jsParamName+"'additionalImageOne',"></#if>
    <#if !productAdditionalImage2?has_content><#assign jsParamName = jsParamName+"'additionalImageTwo',"></#if>
    <#if !productAdditionalImage3?has_content><#assign jsParamName = jsParamName+"'additionalImageThree',"></#if>
    <#if !productAdditionalImage4?has_content><#assign jsParamName = jsParamName+"'additionalImageFour',"></#if>
    <#if !productAdditionalImage5?has_content><#assign jsParamName = jsParamName+"'additionalImageFive',"></#if>
    <#if !productAdditionalImage6?has_content><#assign jsParamName = jsParamName+"'additionalImageSix',"></#if>
    <#if !productAdditionalImage7?has_content><#assign jsParamName = jsParamName+"'additionalImageSeven',"></#if>
    <#if !productAdditionalImage8?has_content><#assign jsParamName = jsParamName+"'additionalImageEight',"></#if>
    <#if jsParamName?index_of(",")!=-1>
        <#assign jsParamName = jsParamName?substring(0,(jsParamName?length-1))>
    </#if>
    <#assign jsParamName = jsParamName+"]">
    <script>
        $(document).ready(function () {
            var paramNames =${jsParamName};
            FormMultipleUpload.init(paramNames,${productId});
        });
    </script>

    <!-- The blueimp Gallery widget -->
    <div id="blueimp-gallery" class="blueimp-gallery blueimp-gallery-controls" data-filter=":even">
        <div class="slides"></div>
        <h3 class="title"></h3>
        <a class="prev">‹</a>
        <a class="next">›</a>
        <a class="close">×</a>
        <a class="play-pause"></a>
        <ol class="indicator"></ol>
    </div>

</form>
<@htmlScreenTemplate.renderScreenletEnd/>
