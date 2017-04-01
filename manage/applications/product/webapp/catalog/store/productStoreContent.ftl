<div class="row">
    <div class="col-md-2">
    <#if storeContent?has_content>
      <#assign siteIcon = storeContent >
      <#assign linkContentWrapper = Static["org.ofbiz.content.data.OfbizUrlContentWrapper"].makeOfbizUrlContentWrapper(siteIcon.get('contentId'),delegator, request)/>
      <#assign url = linkContentWrapper.get('siteIcon')?if_exists/>
      <#assign fileName = linkContentWrapper.get('fileName')?if_exists/>
        <form action="<@ofbizUrl>uploadProductStoreContent</@ofbizUrl>" class="dropzone dz-started" id="dropzoneSiteIcon" enctype="multipart/form-data">
            <input type="hidden" name="productStoreId" value="${productStoreId}"/>
            <div class="dz-default dz-message"><span><span class="smaller-80 grey">点击上传图片</span> <br>
                <i class="upload-icon ace-icon fa fa-cloud-upload blue fa-3x"></i></span></div>
            <div class="dz-preview dz-processing dz-image-preview dz-success dz-complete">
                <div class="dz-image">
                    <img data-dz-thumbnail="" alt="" width="120" height="120"
                         src="<@ofbizContentUrl>${url}</@ofbizContentUrl>">
                </div>
                <div class="dz-details">
                    <div class="dz-size"><span data-dz-size=""></div>
                    <div class="dz-filename"><span data-dz-name="">${fileName}</span></div>
                </div>
                <div class="dz-progress"><span class="dz-upload" data-dz-uploadprogress="" style="width: 100%;"></span></div>
                <div class="dz-error-message"><span data-dz-errormessage=""></span></div>
                <div class="dz-success-mark"></div>
                <div class="dz-error-mark"></div>
                <br/>
                <a class="dz-remove" href="javascript:onSiteIconDelete('#dropzoneSiteIcon',${siteIcon.get('productStoreContentId')});" data-dz-remove="">删除</a>
            <#--<div class="fallback">
                <input name="uploadedFile" type="file"/>
            </div>-->

            </div>
        </form>
    <#else>
        <form action="<@ofbizUrl>uploadProductStoreContent</@ofbizUrl>" class="dropzone" id="dropzoneSiteIcon" enctype="multipart/form-data">
            <input type="hidden" name="productStoreId" value="${productStoreId}"/>
            <div class="fallback">
                <input name="uploadedFile" type="file"/>
            </div>

        </form>
    </#if>
    </div>
</div>

<script type="text/javascript">
    $(function () {
        try {
            Dropzone.autoDiscover = false;
            var myDropzone = new Dropzone("#dropzoneSiteIcon", {
                paramName: "uploadedFile", // The name that will be used to transfer the file
                addRemoveLinks: true,
                maxFiles: 1,
                uploadMultiple: false,
                dictDefaultMessage: '<span class="smaller-80 grey">点击上传图片</span> <br /> \
                                <i class="upload-icon ace-icon fa fa-cloud-upload blue fa-3x"></i>',
                dictResponseError: '上传图片失败!',
                dictRemoveFile: '删除',
                init: function () {
                    this.on("removedfile", function (file) {
                        console.log(file.xhr.responseText);
                        $.ajax({
                            type: 'POST',
                            url: '<@ofbizUrl>deleteProductStoreContent</@ofbizUrl>',
                            data: $.parseJSON(file.xhr.responseText),
                            success: function (data) {
                                console.log(data)
                            },
                            dataType: 'json'
                        });

                    });
                    this.on("sending", function (file, xhr, formData) {
                    });

                }
            });

            $(document).one('ajaxloadstart.page', function (e) {
                try {
                    myDropzone.destroy();
                } catch (e) {
                }
            });

        } catch (e) {
            console.log(e);
            alert('Dropzone.js does not support older browsers!');
        }
    });


    var onSiteIconDelete = function (pid, id) {
        console.log(id);
        $(pid).find('.dz-preview ').remove();
        $(pid).removeClass('dz-started');
        $.ajax({
            type: 'POST',
            url: '<@ofbizUrl>deleteProductStoreContent</@ofbizUrl>',
            data: {'productStoreContentId': id},
            success: function (data) {
                console.log(data)
            },
            dataType: 'json'
        });
    }
</script>
