<script type="text/javascript">
    jQuery(document).ready(function() {
        // override elRTE save action to make "save" toolbar button work
      /*  elRTE.prototype.save = function() {
            this.beforeSave();
            cmsSave();
        }*/
    });

    function cmsSave() {
        for ( instance in CKEDITOR.instances )
       CKEDITOR.instances[instance].updateElement();
        var simpleFormAction = '<@ofbizUrl>/updateContentCms</@ofbizUrl>';
     /*   var editor = jQuery("#cmsdata");
        if (editor.length) {
            var cmsdata = jQuery("#cmsdata");
            var data = editor.innerHTML;
            cmsdata.val(data);
        }*/

        // get the cmsform
        var form = document.cmsform;

        // set the data resource name
        form.dataResourceName.value = form.contentName.value;

        // check to see if we need to change the form action
        var isUpload = form.elements['isUploadObject'];
        if (isUpload && isUpload.value == 'Y') {
            var uploadValue = form.elements['uploadedFile'].value;
            if (uploadValue == null || uploadValue == "") {
                form.action = simpleFormAction;
            }

            // if we have a file upload make a 'real' form submit, ajax submits won't work in this cases
            form.submit();
            return false;
        }

        // submit the form
        if (form != null) {
            <#if content?has_content>
                ajaxSubmitForm(form, "${content.contentId!}");
            <#else>
                // for new content we need a real submit, so that the nav tree gets updated
                // and because ajaxSubmitForm() cannot retrieve the new contentId, so subsequent saves would create more new contents
                form.submit();
            </#if>
        } else {
            showErrorAlert("${uiLabelMap.CommonErrorMessage2}","${uiLabelMap.CannotFindCmsform}");
        }

        return false;
    }

    function selectDataType(contentId) {
        var selectObject = document.forms['cmsdatatype'].elements['dataResourceTypeId'];
        var typeValue = selectObject.options[selectObject.selectedIndex].value;
        callDocument(true, contentId, '', typeValue);
    }
</script>

<#-- cms menu bar -->
<div id="cmsmenu" style="margin-bottom: 8px;">
    <#if (content?has_content)>
        <a href="javascript:void(0);" onclick="callDocument(true, '${content.contentId}', '', 'ELECTRONIC_TEXT');" class="am-btn am-btn-primary am-btn-sm">${uiLabelMap.ContentQuickSubContent}</a>
        <a href="javascript:void(0);" onclick="callPathAlias('${content.contentId}');" class="am-btn am-btn-primary am-btn-sm">${uiLabelMap.ContentPathAlias}</a>
        <a href="javascript:void(0);" onclick="callMetaInfo('${content.contentId}');" class="am-btn am-btn-primary am-btn-sm">${uiLabelMap.ContentMetaTags}</a>
    </#if>
</div>

<#-- content info -->
<#if (!content?has_content)>
<div class="am-g am-margin-top-sm">
    <div class="am-u-sm-9">
    <div class="am-text-center">
        ${uiLabelMap.CommonNew} <span >${contentAssocTypeId?default("SUBSITE")}</span> ${uiLabelMap.ContentWebSiteAttachedToContent} ${contentIdFrom?default(contentRoot)}
    </div>
    </div>
</div>
</#if>

<#-- dataResourceTypeId -->
<#if (!dataResourceTypeId?has_content)>
    <#if (dataResource?has_content)>
        <#assign dataResourceTypeId = dataResource.dataResourceTypeId/>
    <#elseif (content?has_content)>
        <#assign dataResourceTypeId = "NONE"/>
    <#else>
        <form name="cmsdatatype" class="am-form am-form-horizontal">

                    <div class="am-g am-margin-top-sm">
                    <div class="am-u-sm-3 am-text-right" >${uiLabelMap.ContentDataType}</div>
                    <div class="am-u-sm-6 am-u-end">
                        <select name="dataResourceTypeId" class="am-input-sm">
                            <option value="NONE">${uiLabelMap.ContentResourceNone}</option>
                            <option value="SHORT_TEXT">${uiLabelMap.ContentResourceShortText}</option>
                            <option value="ELECTRONIC_TEXT">${uiLabelMap.ContentResourceLongText}</option>
                            <option value="URL_RESOURCE">${uiLabelMap.ContentResourceUrlResource}</option>
                            <option value="IMAGE_OBJECT">${uiLabelMap.ContentImage}</option>
                            <option value="VIDEO_OBJECT">${uiLabelMap.ContentResourceVideo}</option>
                            <option value="AUDIO_OBJECT">${uiLabelMap.ContentResourceAudio}</option>
                            <option value="OTHER_OBJECT">${uiLabelMap.ContentResourceOther}</option>
                        </select>
                    </div>
                    </div>

                <div class="am-g am-margin-top-sm">
                    <div class="am-u-sm-9 am-u-end">
                        <div class="am-cf am-fr">
                        <a href="javascript:void(0);" onclick="selectDataType('${contentIdFrom?default(contentRoot)}');" class="am-btn am-btn-primary am-btn-sm">${uiLabelMap.CommonContinue}</a>
                       </div>
                    </div>
                </div>
              <#--  <#list 0..15 as x>
                    <div class="am-u-sm-6" colspan="2">&nbsp;</div>
                </#list>-->

        </form>
    </#if>
</#if>

<#-- form action -->
<#if (dataResourceTypeId?has_content)>
    <#assign actionSuffix = "ContentCms"/>
    <#if (dataResourceTypeId == "NONE" || (content?has_content && !content.dataResourceId?has_content))>
        <#assign actionMiddle = ""/>
    <#else>
        <#if (dataResourceTypeId?ends_with("_OBJECT"))>
            <#assign actionMiddle = "Object"/>
        <#else>
            <#assign actionMiddle = "Text"/>
        </#if>
    </#if>

    <#if (!contentRoot?has_content)>
        <#assign contentRoot = parameters.contentRoot/>
    </#if>
    <#if (currentPurposes?has_content)>
        <#assign currentPurpose = Static["org.ofbiz.entity.util.EntityUtil"].getFirst(currentPurposes) />
    </#if>
    <#if (content?has_content)>
        <#assign actionPrefix = "/update"/>
    <#else>
        <#assign actionPrefix = "/create"/>
    </#if>
    <#assign formAction = actionPrefix + actionMiddle + actionSuffix/>
<#else>
    <#assign formAction = "javascript:void(0);"/>
</#if>

<#-- main content form -->
<#if (dataResourceTypeId?has_content)>
    <form name="cmsform" class="am-form am-form-horizontal" enctype="multipart/form-data" method="post" action="<@ofbizUrl>${formAction}</@ofbizUrl>">
        <#if (content?has_content)>
            <input type="hidden" name="dataResourceId" value="${(dataResource.dataResourceId)?if_exists}"/>
            <input type="hidden" name="contentId" value="${content.contentId}"/>
            <#list requestParameters.keySet() as paramName>
                <#if (paramName == 'contentIdFrom' || paramName == 'contentAssocTypeId' || paramName == 'fromDate')>
                    <input type="hidden" name="${paramName}" value="${requestParameters.get(paramName)}"/>
                </#if>
            </#list>
        <#else>
            <input type="hidden" name="contentAssocTypeId" value="${contentAssocTypeId?default('SUBSITE')}"/>
            <input type="hidden" name="ownerContentId" value="${contentIdFrom?default(contentRoot)}"/>
            <input type="hidden" name="contentIdFrom" value="${contentIdFrom?default(contentRoot)}"/>
        </#if>
        <#if (dataResourceTypeId != 'IMAGE_OBJECT' && dataResourceTypeId != 'OTHER_OBJECT' && dataResourceTypeId != 'LOCAL_FILE' &&
            dataResourceTypeId != 'OFBIZ_FILE' && dataResourceTypeId != 'VIDEO_OBJECT' && dataResourceTypeId != 'AUDIO_OBJECT')>
            <input type="hidden" name="mimeTypeId" value="${mimeTypeId}"/>
        </#if>
        <#if (dataResourceTypeId != 'NONE')>
        <#if (dataResourceTypeId == 'IMAGE_OBJECT' || dataResourceTypeId == 'OTHER_OBJECT' || dataResourceTypeId == 'LOCAL_FILE' ||
                dataResourceTypeId == 'OFBIZ_FILE' || dataResourceTypeId == 'VIDEO_OBJECT' || dataResourceTypeId == 'AUDIO_OBJECT')>
            <input type="hidden" name="dataResourceTypeId" value="IMAGE_OBJECT"/>
        <#else>
            <input type="hidden" name="dataResourceTypeId" value="${dataResourceTypeId}"/>
        </#if>
        </#if>
        <input type="hidden" name="webSiteId" value="${webSiteId}"/>
        <input type="hidden" name="dataResourceName" value="${(dataResource.dataResourceName)?if_exists}"/>

        <div class="am-g am-margin-top-sm">
          <#if (content?has_content)>
                <div class="am-u-sm-3 am-text-right" >${uiLabelMap.FormFieldTitle_contentId}</div>
                <div class="am-u-sm-6 am-u-end ">${content.contentId}</div>
          </#if>
            </div>
        <div class="am-g am-margin-top-sm">
          
            <div class="am-u-sm-3 am-text-right" >${uiLabelMap.CommonName}</div>
            <div class="am-u-sm-6 am-u-end">
                <input type="text" class="am-input-sm" name="contentName" value="${(content.contentName)?if_exists}" size="40"/>
            </div>

        </div>
        <div class="am-g am-margin-top-sm">
            <div class="am-u-sm-3 am-text-right" >${uiLabelMap.CommonDescription}</div>
            <div class="am-u-sm-6 am-u-end">
                <textarea name="description" cols="40" rows="6">${(content.description)?if_exists}</textarea>
            </div>

        </div>
        <div class="am-g am-margin-top-sm">
            <div class="am-u-sm-3 am-text-right" >${uiLabelMap.ContentMapKey}</div>
            <div class="am-u-sm-6 am-u-end">
                <input type="text" class="am-input-sm"  name="mapKey" value="${(assoc.mapKey)?if_exists}" size="40"/>
            </div>
        </div>
        <div class="am-g am-margin-top-sm">
          
            <div class="am-u-sm-3 am-text-right" >${uiLabelMap.CommonPurpose}</div>
            <div class="am-u-sm-6 am-u-end">
                <select name="contentPurposeTypeId" class="am-input-sm" >
                    <#if (currentPurpose?has_content)>
                        <#assign purpose = currentPurpose.getRelatedOne("ContentPurposeType")/>
                        <option value="${purpose.contentPurposeTypeId}">${purpose.description?default(purpose.contentPurposeTypeId)}</option>
                        <option value="${purpose.contentPurposeTypeId}">----</option>
                    <#else>
                        <option value="SECTION">Section</option>
                        <option value="SECTION">----</option>
                    </#if>
                    <#list purposeTypes as type>
                        <option value="${type.contentPurposeTypeId}">${type.description}</option>
                    </#list>
                </select>
            </div>
        </div>
        <div class="am-g am-margin-top-sm">
          
            <div class="am-u-sm-3 am-text-right" >${uiLabelMap.CommonSequenceNum}</div>
            <div class="am-u-sm-6 am-u-end">
              <input type="text" class="am-input-sm"  name="sequenceNum" value="${(currentPurpose.sequenceNum)?if_exists}" size="5" />
            </div>
        </div>
        <div class="am-g am-margin-top-sm">
            <div class="am-u-sm-3 am-text-right" >${uiLabelMap.ContentDataType}</div>
            <div class="am-u-sm-6 am-u-end">
                <select name="dataTemplateTypeId" class="am-input-sm" >
                    <#if (dataResource?has_content)>
                        <#if (dataResource.dataTemplateTypeId?has_content)>
                            <#assign thisType = dataResource.getRelatedOne("DataTemplateType")?if_exists/>
                            <option value="${thisType.dataTemplateTypeId}">${thisType.description}</option>
                            <option value="${thisType.dataTemplateTypeId}">----</option>
                        </#if>
                    </#if>
                    <#list templateTypes as type>
                        <option value="${type.dataTemplateTypeId}">${type.description}</option>
                    </#list>
                </select>
            </div>
        </div>
        <div class="am-g am-margin-top-sm">
            <div class="am-u-sm-3 am-text-right" >${uiLabelMap.ContentDecorator}</div>
            <div class="am-u-sm-6 am-u-end">
                <select name="decoratorContentId" class="am-input-sm" >
                    <#if (content?has_content)>
                        <#if (content.decoratorContentId?has_content)>
                            <#assign thisDec = content.getRelatedOne("DecoratorContent")/>
                            <option value="${thisDec.contentId}">${thisDec.contentName}</option>
                            <option value="${thisDec.contentId}">----</option>
                        </#if>
                    </#if>
                    <option value="">${uiLabelMap.ContentResourceNone}</option>
                    <#list decorators as decorator>
                        <option value="${decorator.contentId}">${decorator.contentName}</option>
                    </#list>
                </select>
            </div>
        </div>
        <div class="am-g am-margin-top-sm">
          
            <div class="am-u-sm-3 am-text-right" >${uiLabelMap.ContentTemplate}</div>
            <div class="am-u-sm-6 am-u-end">
                <select name="templateDataResourceId" class="am-input-sm" >
                    <#if (content?has_content)>
                        <#if (content.templateDataResourceId?has_content && content.templateDataResourceId != "NONE")>
                            <#assign template = content.getRelatedOne("TemplateDataResource")/>
                            <option value="${template.dataResourceId}">${template.dataResourceName?if_exists}</option>
                            <option value="${template.dataResourceId}">----</option>
                        </#if>
                    </#if>
                    <option value="">${uiLabelMap.ContentResourceNone}</option>
                    <#list templates as template>
                        <option value="${template.dataResourceId}">${template.dataResourceName}</option>
                    </#list>
                </select>
            </div>
        </div>
        <div class="am-g am-margin-top-sm">
          
            <div class="am-u-sm-3 am-text-right" >${uiLabelMap.CommonStatus}</div>
            <div class="am-u-sm-6 am-u-end">
                <select name="statusId" class="am-input-sm" >
                    <#if (content?has_content)>
                        <#if (content.statusId?has_content)>
                            <#assign statusItem = content.getRelatedOne("StatusItem")/>
                            <option value="${statusItem.statusId}">${statusItem.description}</option>
                            <option value="${statusItem.statusId}">----</option>
                        </#if>
                    </#if>
                    <#list statuses as status>
                        <option value="${status.statusId}">${status.description}</option>
                    </#list>
                </select>
            </div>
        </div>
        <div class="am-g am-margin-top-sm">
          
            <div class="am-u-sm-3 am-text-right" >${uiLabelMap.FormFieldTitle_isPublic}</div>
            <div class="am-u-sm-6 am-u-end" >
                <select name="isPublic" class="am-input-sm" >
                    <#if (dataResource?has_content)>
                        <#if (dataResource.isPublic?has_content)>
                            <option>${dataResource.isPublic}</option>
                            <option value="${dataResource.isPublic}">----</option>
                        <#else>
                            <option></option>
                        </#if>
                    </#if>
                    <option>Y</option>
                    <option>N</option>
                </select>
            </div>
        </div>
        <div class="am-g am-margin-top-sm">
          
            <div class="am-u-sm-9 am-u-end">

            </div>
        </div>


          <#-- this all depends on the dataResourceTypeId which was selected -->
          <#if (dataResourceTypeId == 'IMAGE_OBJECT' || dataResourceTypeId == 'OTHER_OBJECT' || dataResourceTypeId == 'LOCAL_FILE' ||
                dataResourceTypeId == 'OFBIZ_FILE' || dataResourceTypeId == 'VIDEO_OBJECT' || dataResourceTypeId == 'AUDIO_OBJECT')>
          <div class="am-g am-margin-top-sm">
              <div class="am-u-sm-9 am-u-end am-text-center">
                <#if ((content.contentId)?has_content)>
                    <@renderContentAsText contentId="${content.contentId}" ignoreTemplate="true"/>
                </#if>
              </div>
            </div>

          <div class="am-g am-margin-top-sm">
              <div class="am-u-sm-3 am-text-right" >${uiLabelMap.CommonUpload}</div>
              <div class="am-u-sm-6 am-u-end">
                <input type="hidden" name="isUploadObject" value="Y"/>
                <input type="file" name="uploadedFile" size="30"/>
              </div>
          </div>

          <#elseif (dataResourceTypeId == 'URL_RESOURCE')>
          <div class="am-g am-margin-top-sm">
              <div class="am-u-sm-3 am-text-right" >${uiLabelMap.ContentUrl}</div>
              <div class="am-u-sm-6 am-u-end">
                <input type="text" name="objectInfo" size="40" maxsize="255" value="${(dataResource.objectInfo)?if_exists}"/>
              </div>
          </div>

          <#elseif (dataResourceTypeId == 'SHORT_TEXT')>
          <div class="am-g am-margin-top-sm">
              <div class="am-u-sm-3 am-text-right" >${uiLabelMap.ContentText}</div>
              <div class="am-u-sm-6 am-u-end">
                <input type="text" name="objectInfo" size="40" maxsize="255" value="${(dataResource.objectInfo)?if_exists}"/>
              </div>
          </div>

          <#elseif (dataResourceTypeId == 'ELECTRONIC_TEXT')>
          <div class="am-g am-margin-top-sm">
           <textarea id="cmsdata" name="textData" cols="60" rows="30" >
               <#if (dataText?has_content)>
                    ${StringUtil.wrapString(dataText.textData!)}
                </#if>
           </textarea>
          </div>
          </#if>
        <div class="am-g am-margin-top-sm">  <div class="am-u-sm-9">
        <div class="am-cf">
            <div class="am-fr">
                <a href="javascript:void(0);" onclick="cmsSave();" class="am-btn am-btn-primary am-btn-sm">${uiLabelMap.CommonSave}</a>
            </div>
            </div>
        </div>
        </div>
    </form>
</#if>