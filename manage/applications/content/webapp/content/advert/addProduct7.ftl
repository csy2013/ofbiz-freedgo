<script src="<@ofbizContentUrl>/images/jquery/plugins/dropzone/dist/dropzone.js</@ofbizContentUrl>"></script>
<link rel="stylesheet" href="<@ofbizContentUrl>/images/jquery/plugins/dropzone/dist/dropzone.css</@ofbizContentUrl>">
<div class="row">
<#list 1..8 as seq>
    <div class="col-md-2">
        <#assign hasContent = false>
        <#if advertContents?has_content>
            <#list advertContents as adContent>
                <#if adContent.get('sequenceNum') == seq>
                    <#assign  hasContent = true>
                    <#assign linkContentWrapper = Static["org.ofbiz.content.data.OfbizUrlContentWrapper"].makeOfbizUrlContentWrapper(adContent.get('contentId'),delegator, request)/>
                    <#assign url = linkContentWrapper.get('slidePic')?if_exists/>
                    <#assign fileName = linkContentWrapper.get('fileName')?if_exists/>
                    <form action="<@ofbizUrl>uploadSiteSlide</@ofbizUrl>" class="dropzone dz-started" id="dropzone${seq}" enctype="multipart/form-data">
                        <input type="hidden" name="webSiteId" value="${webSiteId}"/>
                        <input type="hidden" name="sequenceNum" value="${seq}"/>
                        <input type="hidden" name="advertTypeId" value="ball"/>
                        <input type="hidden" name="advertTypeId" value="ball"/>
                        <input type="hidden" name="defineType" value="A"/>
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
                            <a class="dz-remove" href="javascript:onDelete('#dropzone${seq}',${adContent.get('advertContentId')});" data-dz-remove="">删除</a>
                        <#--<div class="fallback">
                            <input name="uploadedFile" type="file"/>
                        </div>-->

                        </div>
                    </form>
                </#if>
            </#list>
        </#if>
        <#if hasContent == false>
            <form action="<@ofbizUrl>uploadSiteSlide</@ofbizUrl>" class="dropzone" id="dropzone${seq}" enctype="multipart/form-data">
                <input type="hidden" name="webSiteId" value="${webSiteId}"/>
                <input type="hidden" name="sequenceNum" value="${seq}"/>
                <input type="hidden" name="advertTypeId" value="ball"/>
                <input type="hidden" name="defineType" value="A"/>
                <div class="fallback">
                    <input name="uploadedFile" type="file"/>
                </div>
            </form>
        </#if>
    </div>

</#list>
</div>
<br/>
<hr/>

<#list 1..8 as seq>
<form name="slideRelationForm${seq}" class="form-horizontal form-bordered">
    <div class="form-group">
        <div class="col-md-1">图片${seq}配置</div>
        <label class="control-label col-md-2" for="group${seq}">所属分组:</label>
        <div class="col-md-3">
            <#if advertContents?has_content>
                <#assign groupNum =""/>
                <#list advertContents as adContent>
                    <#if adContent.get('sequenceNum') == seq>
                        <#assign groupNum = adContent.get("groupNum")?if_exists>
                    </#if>
                </#list>
            </#if>
            <input type="text" class="form-control" name="groupNum${seq}" id="groupNum${seq}" value="${groupNum?if_exists}"/>
        </div>
        <label class="control-label col-md-2" for="slidePic${seq}">地址:</label>
        <div class="col-md-3">
            <#if advertContents?has_content>
                <#assign relationUrl =""/>
                <#list advertContents as adContent>
                    <#if adContent.get('sequenceNum') == seq>
                        <#assign relationUrl = adContent.get("relationUrl")?if_exists>
                    </#if>
                </#list>
            </#if>
            <input type="text" class="form-control" name="slidePic${seq}" id="slidePic${seq}" value="${relationUrl?if_exists}"/>
        </div>
    </div>
</form>
</#list>
<script type="text/javascript">
    $(function () {
        Dropzone.autoDiscover = false;
    <#list 1..8 as dzCount>
        try {
            var myDropzone${dzCount} = new Dropzone("#dropzone${dzCount}", {
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
                            url: '<@ofbizUrl>deleteAdvertContent</@ofbizUrl>',
                            data: $.parseJSON(file.xhr.responseText),
                            success: function (data) {
                                console.log(data)
                            },
                            dataType: 'json'
                        });

                    });
                    this.on("sending", function (file, xhr, formData) {
                        formData.append('relationUrl', $('#slidePic${dzCount}').val());
                        formData.append('groupNum', $('#groupNum${dzCount}').val());
                        formData.append('groupName', $('#groupNum${dzCount}').val());
                    });
                }
            });
            $(document).one('ajaxloadstart.page', function (e) {
                try {
                    myDropzone${dzCount}.destroy();
                } catch (e) {
                }
            });

        } catch (e) {
            console.log(e);
            alert('Dropzone.js does not support older browsers!');
        }

    </#list>
    });
    var onDelete = function (pid, id) {
        console.log(id);
        $(pid).find('.dz-preview ').remove();
        $(pid).removeClass('dz-started');
        $.ajax({
            type: 'POST',
            url: '<@ofbizUrl>deleteAdvertContent</@ofbizUrl>',
            data: {advertContentId: id},
            success: function (data) {
                console.log(data)
            },
            dataType: 'json'
        });
    }
</script>
