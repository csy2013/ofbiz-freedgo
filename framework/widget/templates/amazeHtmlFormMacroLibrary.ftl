<#macro renderField text>
    <#if text?exists>
    ${text}<#lt/>
    </#if>
</#macro>
<#--?-->
<#macro renderDisplayField type imageLocation idName description title class alert inPlaceEditorUrl="" inPlaceEditorParams="">
    <#if type?has_content && type=="image">
    <img src="${imageLocation}" alt=""><#lt/>
    <#else>
        <#if inPlaceEditorUrl?has_content || class?has_content || alert=="true" || title?has_content>
        <span <#if idName?has_content>id="cc_${idName}"</#if> <#if title?has_content>title="${title}"</#if> <@renderClass class alert />><#t/>
        </#if>

        <#if description?has_content>
        ${description?replace("\n", "<br />")}<#t/>
        <#else>
            &nbsp;<#t/>
        </#if>
        <#if inPlaceEditorUrl?has_content || class?has_content || alert=="true">
        </span><#lt/>
        </#if>
        <#if inPlaceEditorUrl?has_content && idName?has_content>
        <script language="JavaScript" type="text/javascript"><#lt/>
        ajaxInPlaceEditDisplayField('cc_${idName}', '${inPlaceEditorUrl}', ${inPlaceEditorParams});<#lt/>
        </script><#lt/>
        </#if>
    </#if>
</#macro>
<#macro renderHyperlinkField></#macro>

<#macro renderTextField name className alert value textSize maxlength id event action disabled clientAutocomplete ajaxUrl ajaxEnabled mask>
    <#if mask?has_content>
    <script type="text/javascript">
        jQuery(function ($) {
            jQuery("#${id}").mask("${mask}");
        });
    </script>
    </#if>

   <input type="text" name="${name?default("")?html}"<#t/>
               class="am-form-field am-input-sm"
    <#if value?has_content> value="${value}"</#if><#rt/>
    <#--<#if textSize?has_content> size="${textSize}"</#if><#rt/>-->
    <#--<#if maxlength?has_content> maxlength="${maxlength}"</#if><#rt/>-->
    <#if disabled?has_content && disabled> disabled="disabled"</#if><#rt/>
    <#if id?has_content> id="${id}"</#if><#rt/>
    <#if event?has_content && action?has_content> ${event}="${action}"</#if><#rt/>
    <#if clientAutocomplete?has_content && clientAutocomplete=="false"> autocomplete="off"</#if><#rt/>
        /><#t/>
    <#if ajaxEnabled?has_content && ajaxEnabled>
        <#assign defaultMinLength = Static["org.ofbiz.base.util.UtilProperties"].getPropertyValue("widget.properties", "widget.autocompleter.defaultMinLength")>
        <#assign defaultDelay = Static["org.ofbiz.base.util.UtilProperties"].getPropertyValue("widget.properties", "widget.autocompleter.defaultDelay")>
    <script language="JavaScript" type="text/javascript">ajaxAutoCompleter('${ajaxUrl}', false, ${defaultMinLength!2}, ${defaultDelay!300});</script><#lt/>
    </#if>
</#macro>

<#macro renderTextField1 name className alert value textSize maxlength id event action disabled clientAutocomplete ajaxUrl ajaxEnabled mask required tooltip>
    <#if mask?has_content>
    <script type="text/javascript">
        jQuery(function ($) {
            jQuery("#${id}").mask("${mask}");
        });
    </script>
    </#if>
    <div class="am-input-group">
        <input type="text" name="${name?default("")?html}" class="am-form-field am-input-sm" <#t/>

    <#if value?has_content> value="${value}"</#if><#rt/>
    <#if textSize?has_content> size="${textSize}"</#if><#rt/>
    <#if maxlength?has_content> maxlength="${maxlength}"</#if><#rt/>
    <#if disabled?has_content && disabled> disabled="disabled"</#if><#rt/>
    <#if id?has_content> id="${id}"</#if><#rt/>
    <#if event?has_content && action?has_content> ${event}="${action}"</#if><#rt/>
    <#if clientAutocomplete?has_content && clientAutocomplete=="false"> autocomplete="off"</#if><#rt/>
  <#--  <#if required?has_content>placeholder="${required}<#if tooltip?has_content>,${tooltip}"<#else>"</#if><#rt/>
    <#else>-->
    <#if tooltip?has_content>placeholder="${tooltip}"</#if><#rt/>
    <#--</#if>-->
    /></div><#t/>
    <#if ajaxEnabled?has_content && ajaxEnabled>
        <#assign defaultMinLength = Static["org.ofbiz.base.util.UtilProperties"].getPropertyValue("widget.properties", "widget.autocompleter.defaultMinLength")>
        <#assign defaultDelay = Static["org.ofbiz.base.util.UtilProperties"].getPropertyValue("widget.properties", "widget.autocompleter.defaultDelay")>
    <script language="JavaScript" type="text/javascript">ajaxAutoCompleter('${ajaxUrl}', false, ${defaultMinLength!2}, ${defaultDelay!300});</script><#lt/>
    </#if>
</#macro>

<#macro renderTextareaField name className alert cols rows id readonly value visualEditorEnable buttons language="">
<textarea name="${name}"<#t/>
    <@renderClass className alert />
    <#if cols?has_content> cols="${cols}"</#if><#rt/>
    <#if rows?has_content> rows="${rows}"</#if><#rt/>
    <#if id?has_content> id="${id}"</#if><#rt/>
    <#if readonly?has_content && readonly=='readonly'> readonly="readonly"</#if><#rt/>
    <#if maxlength?has_content> maxlength="${maxlength}"</#if><#rt/>
        ><#t/>
    <#if value?has_content>${value}</#if><#t/>
    </textarea><#lt/>
    <#if visualEditorEnable?has_content>
    <#--<script language="javascript" src="/images/jquery/plugins/elrte-1.3/js/elrte.min.js" type="text/javascript"></script><#rt/>
        <#if language?has_content && language != "en">
        <script language="javascript" src="/images/jquery/plugins/elrte-1.3/js/i18n/elrte.${language!"en"}.js" type="text/javascript"></script><#rt/>
        </#if>
    <link href="/images/jquery/plugins/elrte-1.3/css/elrte.min.css" rel="stylesheet" type="text/css">
    <script language="javascript" type="text/javascript">
        var opts = {
            cssClass: 'el-rte',
            lang: '${language!"en"}',
            toolbar: '${buttons?default("maxi")}',
            doctype: '<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">', //'<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN">',
            cssfiles: ['/images/jquery/plugins/elrte-1.3/css/elrte-inner.css']
        }
        jQuery('#${id?default("")}').elrte(opts);
    </script>-->
    <script language="javascript" type="text/javascript" src="/images/ckeditor/ckeditor.js"></script>
    <script language="javascript" type="text/javascript">
               if (!CKEDITOR.instances['${id?default("")}']) {
                CKEDITOR.replace('${id?default("")}', {
                            language: '${language}'
                        }
                );
            } else {
                var editor2 = CKEDITOR.instances['${id?default("")}'];
                if (editor2) editor2.destroy(true);
                CKEDITOR.replace('${id?default("")}', {
                            language: '${language}',

                        }
                )
            }
    </script>
    </#if>
</#macro>

<#macro renderDateTimeField name className alert title value size maxlength id dateType shortDateInput timeDropdownParamName defaultDateTimeString localizedIconTitle timeDropdown timeHourName classString hour1 hour2 timeMinutesName minutes isTwelveHour ampmName amSelected pmSelected compositeType formName mask="" event="" action="" step="" timeValues="">
<div class="am-input-group">
    <#if dateType!="time" >
        <input type="text" name="${name}_i18n" class="am-form-field am-input-sm" <#rt/>
            <#if title?has_content> title="${title}"</#if>
            <#if value?has_content> value="${value}"</#if>
            <#if size?has_content> size="14"</#if><#rt/>
            <#if maxlength?has_content>  maxlength="${maxlength}"</#if>
            <#if id?has_content> id="${id}_i18n"</#if>   /><#rt/>

    </#if>
<#-- the style attribute is a little bit messy but when using disply:none the timepicker is shown on a wrong place -->
    <input type="hidden" name="${name}" style="height:1px;width:1px;border:none;background-color:transparent" <#if event?has_content && action?has_content> ${event}="${action}
    "</#if> <@renderClass className alert /><#rt/>
    <#if title?has_content> title="${title}"</#if>
    <#if value?has_content> value="${value}"</#if>
    <#if size?has_content> size="${size}"</#if><#rt/>
    <#if maxlength?has_content>  maxlength="${maxlength}"</#if>
    <#if id?has_content> id="${id}"</#if>/><#rt/>
    <#if dateType!="time" >
        <script type="text/javascript">
            <#-- If language specific lib is found, use date / time converter else just copy the value fields -->
            if (Date.CultureInfo != undefined) {
            var initDate = <#if value?has_content>jQuery("#${id}_i18n").val()<#else>""</#if>;;
                if (initDate != "") {
                    var dateFormat = Date.CultureInfo.formatPatterns.shortDate<#if shortDateInput?exists && !shortDateInput> + " " + Date.CultureInfo.formatPatterns.longTime</#if>;
                <#-- bad hack because the JS date parser doesn't understand dots in the date / time string -->
                    if (initDate.indexOf('.') != -1) {
                        initDate = initDate.substring(0, initDate.indexOf('.'));
                    }
                    var ofbizTime = "<#if shortDateInput?exists && shortDateInput>yyyy-MM-dd<#else>yyyy-MM-dd HH:mm:ss</#if>";
                    var dateObj = Date.parseExact(initDate, ofbizTime);
                    var formatedObj = dateObj.toString(dateFormat);
                    jQuery("#${id}_i18n").val(formatedObj);
                }

                jQuery("#${id}").change(function () {
                    var ofbizTime = "<#if shortDateInput?exists && shortDateInput>yyyy-MM-dd<#else>yyyy-MM-dd HH:mm:ss</#if>";
                    var newValue = "";
                    if (this.value != "") {
                        var dateObj = Date.parseExact(this.value, ofbizTime);
                        var dateFormat = Date.CultureInfo.formatPatterns.shortDate<#if shortDateInput?exists && !shortDateInput> + " " + Date.CultureInfo.formatPatterns.longTime</#if>;
                        newValue = dateObj.toString(dateFormat);
                    }
                    jQuery("#${id}_i18n").val(newValue);
                });
                jQuery("#${id}_i18n").change(function () {
                    var dateFormat = Date.CultureInfo.formatPatterns.shortDate<#if shortDateInput?exists && !shortDateInput> + " " + Date.CultureInfo.formatPatterns.longTime</#if>;
                    var newValue = "";
                    if (this.value != "") {
                        var dateObj = Date.parseExact(this.value, dateFormat);
                        var ofbizTime = "<#if shortDateInput?exists && shortDateInput>yyyy-MM-dd<#else>yyyy-MM-dd HH:mm:ss</#if>";
                        newValue = dateObj.toString(ofbizTime);
                    }
                    jQuery("#${id}").val(newValue);
                });
            } else {
            <#-- fallback if no language specific js date file is found -->
                jQuery("#${id}").change(function () {
                    jQuery("#${id}_i18n").val(this.value);
                });
                jQuery("#${id}_i18n").change(function () {
                    jQuery("#${id}").val(this.value);
                });
            }

                <#if shortDateInput?exists && shortDateInput>
                        jQuery("#${id}").datepicker({
                <#else>
                jQuery("#${id}").datetimepicker({
                    showSecond: true,
                <#-- showMillisec: true, -->
                    timeFormat: 'hh:mm:ss',
                    stepHour: 1,
                    stepMinute: 1,
                    stepSecond: 1,
                </#if>
                showOn: 'button',
                buttonImage: '',
                buttonText: '',
                buttonSpan:'<span class="am-input-group-btn"><button class="am-btn am-btn-default am-btn-sm"><span class="am-icon-calendar"></span></button></span>',
                buttonImageOnly: false,
                dateFormat: 'yy-mm-dd'
            })
               <#if mask?has_content>.mask("${mask}")</#if>
            ;
        </script>
    </#if>
    <#if timeDropdown?has_content && timeDropdown=="time-dropdown">
        <select name="${timeHourName}" class="am-input-sm"   ><#rt/>
            <#if isTwelveHour>
                <#assign x=11>
                <#list 0..x as i>
                    <option value="${i}"<#if hour1?has_content><#if i=hour1> selected="selected"</#if></#if>>${i}</option><#rt/>
                </#list>
            <#else>
                <#assign x=23>
                <#list 0..x as i>
                    <option value="${i}"<#if hour2?has_content><#if i=hour2> selected="selected"</#if></#if>>${i}</option><#rt/>
                </#list>
            </#if>
        </select>:<select name="${timeMinutesName}" class="am-input-sm"  ><#rt/>
        <#assign values = Static["org.ofbiz.base.util.StringUtil"].toList(timeValues)>
        <#list values as i>
            <option value="${i}"<#if minutes?has_content><#if i?number== minutes ||((i?number==(60 -step?number)) && (minutes &gt; 60 - (step?number/2))) || ((minutes &gt; i?number )&& (minutes &lt; i?number+(step?number/2))) || ((minutes &lt; i?number )&& (minutes &gt; i?number-(step?number/2)))>
                    selected="selected"</#if></#if>>${i}</option><#rt/>
        </#list>
    </select>
        <#rt/>
        <#if isTwelveHour>
            <select name="${ampmName}" class="am-input-sm" ><#rt/>
                <option value="AM" <#if amSelected == "selected">selected="selected"</#if> >AM</option><#rt/>
                <option value="PM" <#if pmSelected == "selected">selected="selected"</#if>>PM</option><#rt/>
            </select>
            <#rt/>
        </#if>
    </#if>
    <input type="hidden" name="${compositeType}" value="Timestamp"/>
  </div>
</#macro>

<#macro renderDropDownField name className alert id multiple formName otherFieldName event action size firstInList currentValue explicitDescription allowEmpty options fieldName otherFieldName otherValue otherFieldSize dDFCurrent ajaxEnabled noCurrentSelectedKey ajaxOptions frequency minChars choices autoSelect partialSearch partialChars ignoreCase fullSearch>
<div class="am-input-group">
<select name="${name?default("")}<#rt/>"   class="am-input-sm" <#if id?has_content> id="${id}"</#if><#if multiple?has_content> multiple="multiple"</#if><#if otherFieldSize gt 0>
        onchange="process_choice(this,document.${formName}.${otherFieldName})"</#if><#if event?has_content> ${event}="${action}"</#if><#if size?has_content> size="${size}"</#if>>
    <#if firstInList?has_content && currentValue?has_content && !multiple?has_content>
        <option selected="selected" value="${currentValue}">${explicitDescription}</option><#rt/>
        <option value="${currentValue}">---</option><#rt/>
    </#if>
    <#if allowEmpty?has_content || !options?has_content>
        <option value="">&nbsp;</option>
    </#if>
    <#list options as item>
        <#if multiple?has_content>
            <option<#if currentValue?has_content && item.selected?has_content>
                    selected="${item.selected}" <#elseif !currentValue?has_content && noCurrentSelectedKey?has_content && noCurrentSelectedKey == item.key> selected="selected" </#if>
                    value="${item.key}">${item.description}</option><#rt/>
        <#else>
            <option<#if currentValue?has_content && currentValue == item.key && dDFCurrent?has_content && "selected" == dDFCurrent>
                    selected="selected"<#elseif !currentValue?has_content && noCurrentSelectedKey?has_content && noCurrentSelectedKey == item.key> selected="selected"</#if>
                    value="${item.key}">${item.description}</option><#rt/>
        </#if>
    </#list>
    </select>
</div>

    <#if otherFieldName?has_content>
    <noscript><input type='text' name='${otherFieldName}' class="am-form-field am-input-sm"/></noscript>
    <script type='text/javascript' language='JavaScript'><!--
    disa = ' disabled';
    if (other_choice(document.${formName}.${fieldName}))
        disa = '';
    document.write("<input type='text' class='am-form-field am-input-sm' name='${otherFieldName}' value='${otherValue?js_string}' size='${otherFieldSize}'" + disa + " onfocus='check_choice(document.${formName}.${fieldName})' />");
    if (disa && document.styleSheets)
        document.${formName}.${fieldName}.style.visibility = 'hidden';
    //--></script>
    </#if>

    <#if ajaxEnabled>
    <script language="JavaScript" type="text/javascript">
        ajaxAutoCompleteDropDown();
        jQuery(function () {
            jQuery("#${id}").combobox();
        });
    </script>
    </#if>

</#macro>

<#macro renderCheckField items className alert id allChecked currentValue name event action>
    <#list items as item>
    <input type="checkbox"<#if (item_index == 0)> id="${id}"</#if> <@renderClass className alert /><#rt/>
        <#if allChecked?has_content && allChecked> checked="checked" <#elseif  item.checked?has_content>
           checked="${item.checked}"
        <#elseif currentValue?has_content && currentValue==item.value> checked="checked"</#if>
           name="${name?default("")?html}" value="${item.value?default("")?html}"<#if event?has_content> ${event}="${action}"</#if>/><#rt/>
    ${item.description?default("")}
    </#list>
</#macro>

<#macro renderRadioField items className alert currentValue noCurrentSelectedKey name event action>
    <#list items as item>
    <span <@renderClass className alert />><#rt/>
        <input type="radio"<#if currentValue?has_content><#if currentValue==item.key> checked="checked"</#if><#elseif noCurrentSelectedKey?has_content && noCurrentSelectedKey == item.key>
               checked="checked"</#if> name="${name?default("")?html}" value="${item.key?default("")?html}"<#if event?has_content> ${event}="${action}"</#if>/><#rt/>
    ${item.description}</span>
    </#list>
</#macro>

<#macro renderSubmitField buttonType className alert formName title name event action imgSrc confirmation containerId ajaxUrl>
    <div class="am-input-group am-cf am-fr">
    <#if buttonType=="text-link">
    <a <@renderClass className alert /> href="javascript:document.${formName}.submit()"
                                        <#if confirmation?has_content>onclick="return confirm('${confirmation?js_string}');"</#if>><#if title?has_content>${title}</#if> </a>
    <#elseif buttonType=="image">
            <input type="image" src="${imgSrc}" <@renderClass className alert /><#if name?has_content> name="${name}"</#if><#if title?has_content>
                   alt="${title}"</#if><#if event?has_content> ${event}="${action}"</#if> <#if confirmation?has_content>onclick="return confirm('${confirmation?js_string}');"</#if>/>
    <#else>
            <input class="am-btn am-btn-primary am-btn-sm" type="<#if containerId?has_content>button<#else>submit</#if>" <@renderClass className alert /><#if name?exists> name="${name}"</#if><#if title?has_content>
                   value="${title}"</#if><#if event?has_content> ${event}="${action}"</#if><#if containerId?has_content> onclick="<#if confirmation?has_content>if (confirm('${confirmation?js_string}')) </#if>ajaxSubmitFormUpdateAreas('${containerId}', '${ajaxUrl}')"<#else><#if confirmation?has_content> onclick="return confirm('${confirmation?js_string}');"</#if></#if>/></#if>
    </div>
</#macro>

<#macro renderResetField className alert name title>
<input  class="am-btn am-btn-primary am-btn-ms am-fr"  type="reset" <@renderClass className alert /> name="${name}"<#if title?has_content> value="${title}"</#if>/>
</#macro>

<#macro renderHiddenField name value id event action>
        <input type="hidden" name="${name}"<#if value?has_content> value="${value}"</#if><#if id?has_content> id="${id}"</#if><#if event?has_content && action?has_content> ${event}="${action}"</#if>/>
</#macro>

<#macro renderIgnoredField></#macro>

<#macro renderFieldTitle style title id fieldHelpText="">
<span<#if fieldHelpText?has_content> title="${fieldHelpText}"</#if><#if style?has_content> class="${style}"</#if><#if id?has_content> id="${id}"</#if>><#t/>
    ${title}<#t/>
    </span><#t/>
</#macro>

<#macro renderFieldTitle1 style title id avg  isEnd idfor="" fieldHelpText="">
<label class="am-form-label  am-u-md-${avg} am-u-lg-${avg}"<#if fieldHelpText?has_content> title="${fieldHelpText}"</#if><#if style?has_content> class="${style}"</#if><#if id?has_content> id="${id}"</#if> <#if idfor?has_content> for="${idfor}"</#if> ><#t/>
    ${title}<#t/>
    </label><#t/>
</#macro>

<#macro renderSingleFormFieldTitle></#macro>

<#macro renderFormOpen linkUrl formType targetWindow containerId containerStyle autocomplete name viewIndexField viewSizeField viewIndex viewSize useRowSubmit>
<div class="am-g am-center">
<div class="am-u-lg-11">
<form method="post" class="am-form am-form-horizontal"  action="${linkUrl}"<#if formType=="upload"> enctype="multipart/form-data"</#if>
    <#if targetWindow?has_content> target="${targetWindow}"</#if><#if containerId?has_content>
      id="${containerId}"</#if> onsubmit=" submitFormDisableSubmits(this)"<#if autocomplete?has_content> autocomplete="${autocomplete}"</#if> name="${name}"><#lt/>

    <#if useRowSubmit?has_content && useRowSubmit>
    <input type="hidden" name="_useRowSubmit" value="Y"/>
        <#if linkUrl?index_of("VIEW_INDEX") &lt;= 0 && linkUrl?index_of(viewIndexField) &lt;= 0>
        <input type="hidden" name="${viewIndexField}" value="${viewIndex}"/>
        </#if>
        <#if linkUrl?index_of("VIEW_SIZE") &lt;= 0 && linkUrl?index_of(viewSizeField) &lt;= 0>
        <input type="hidden" name="${viewSizeField}" value="${viewSize}"/>
        </#if>
    </#if>
</#macro>

<#macro renderListFormOpen linkUrl formType targetWindow containerId containerStyle autocomplete name viewIndexField viewSizeField viewIndex viewSize useRowSubmit>
<div class="am-g">
<div class="am-u-lg-11">
<form method="post" class="am-form am-form-inline"  action="${linkUrl}"<#if formType=="upload"> enctype="multipart/form-data"</#if>
    <#if targetWindow?has_content> target="${targetWindow}"</#if><#if containerId?has_content>
      id="${containerId}"</#if> onsubmit=" submitFormDisableSubmits(this)"<#if autocomplete?has_content> autocomplete="${autocomplete}"</#if> name="${name}"><#lt/>

    <#if useRowSubmit?has_content && useRowSubmit>
        <input type="hidden" name="_useRowSubmit" value="Y"/>
        <#if linkUrl?index_of("VIEW_INDEX") &lt;= 0 && linkUrl?index_of(viewIndexField) &lt;= 0>
            <input type="hidden" name="${viewIndexField}" value="${viewIndex}"/>
        </#if>
        <#if linkUrl?index_of("VIEW_SIZE") &lt;= 0 && linkUrl?index_of(viewSizeField) &lt;= 0>
            <input type="hidden" name="${viewSizeField}" value="${viewSize}"/>
        </#if>
    </#if>
</#macro>

<#macro renderFormClose focusFieldName formName containerId hasRequiredField>
</form>
</div>
</div><#lt/>


</#macro>
<#macro renderMultiFormClose>
</form></div></div><#lt/>
</#macro>

<#macro renderFormatListWrapperOpen formName style columnStyles>
<div class="am-g">
<div class="am-u-sm-12">
<table class="am-table am-table-striped am-table-hover table-main"><#lt/>
</#macro>

<#macro renderFormatListWrapperClose formName>
</table></div></div>
<#lt/>
</#macro>

<#macro renderFormatHeaderRowOpen style>
<tr>
</#macro>
<#macro renderFormatHeaderRowClose>
</tr>
</#macro>
<#macro renderFormatHeaderRowCellOpen style positionSpan>
<td>
</#macro>
<#macro renderFormatHeaderRowCellClose>
</td>
</#macro>

<#macro renderFormatHeaderRowFormCellOpen style>
<td <#if style?has_content>class="${style}"</#if>>
</#macro>
<#macro renderFormatHeaderRowFormCellClose>
</td>
</#macro>
<#macro renderFormatHeaderRowFormCellTitleSeparator style isLast>
    <#if style?has_content><span class="${style}"></#if> - <#if style?has_content></span></#if>
</#macro>

<#macro renderFormatItemRowOpen formName itemIndex altRowStyles evenRowStyle oddRowStyle>
<tr <#if itemIndex?has_content><#if itemIndex%2==0><#if evenRowStyle?has_content>class="${evenRowStyle}<#if altRowStyles?has_content> ${altRowStyles}</#if>"
<#elseif altRowStyles?has_content>class="${altRowStyles}"</#if><#else><#if oddRowStyle?has_content>class="${oddRowStyle}<#if altRowStyles?has_content> ${altRowStyles}</#if>"
<#elseif altRowStyles?has_content>class="${altRowStyles}"</#if></#if></#if> >
</#macro>
<#macro renderFormatItemRowClose formName>
</tr>
</#macro>
<#macro renderFormatItemRowCellOpen fieldName style positionSpan>
<td <#if positionSpan?has_content && positionSpan gt 1>colspan="${positionSpan}"</#if><#if style?has_content>class="${style}"</#if>>
</#macro>
<#macro renderFormatItemRowCellClose fieldName>
</td>
</#macro>
<#macro renderFormatItemRowFormCellOpen style>
<td<#if style?has_content> class="${style}"</#if>>
</#macro>
<#macro renderFormatItemRowFormCellClose>
</td>
</#macro>

<#macro renderFormatSingleWrapperOpen formName style>
<#--<div class="am-g">-->
</#macro>
<#macro renderFormatSingleWrapperClose formName>
<#--</div>-->
</#macro>

<#macro renderFormatFieldRowOpen>
<div class="am-form-group am-g">
</#macro>
<#macro renderFormatFieldRowClose>
</div>
</#macro>
<#macro renderFormatFieldRowTitleCellOpen style>
<#--<td class="<#if style?has_content>${style}<#else>label</#if>">-->
</#macro>
<#macro renderFormatFieldRowTitleCellClose>
<#--</td>-->
<#--</div>-->
</#macro>
<#macro renderFormatFieldRowSpacerCell></#macro>
<#macro renderFormatFieldRowWidgetCellOpen positionSpan style avg isEnd>
<#if avg=='0'>
<div class="am-u-md-9 am-u-lg-8"  >
<#else>
<div class="am-u-md-${avg} am-u-lg-${avg} <#if isEnd=='true'>am-u-end</#if>" >
</#if>
</#macro>
<#macro renderFormatFieldRowWidgetCellClose>
</div>
</#macro>

<#--
    Initial work to convert table based layout for "single" form to divs.
<#macro renderFormatSingleWrapperOpen style> <div <#if style?has_content>class="${style}"</#if> ></#macro>
<#macro renderFormatSingleWrapperClose> </div></#macro>

<#macro renderFormatFieldRowOpen>  <div></#macro>
<#macro renderFormatFieldRowClose>  </div></#macro>
<#macro renderFormatFieldRowTitleCellOpen style>   <div class="<#if style?has_content>${style}<#else>label</#if>"></#macro>
<#macro renderFormatFieldRowTitleCellClose></div></#macro>
<#macro renderFormatFieldRowSpacerCell></#macro>
<#macro renderFormatFieldRowWidgetCellOpen positionSpan style>   <div<#if positionSpan?has_content && positionSpan gt 0> colspan="${1+positionSpan*3}"</#if><#if style?has_content> class="${style}"</#if>></#macro>
<#macro renderFormatFieldRowWidgetCellClose></div></#macro>

-->


<#macro renderFormatEmptySpace><div class="am-form-label am-u-md-5 am-u-lg-5">&nbsp;</div></#macro>

<#macro renderTextFindField name value defaultOption opEquals opBeginsWith opContains opIsEmpty opNotEqual className alert size maxlength autocomplete titleStyle hideIgnoreCase ignCase ignoreCase>
<div class="am-g">
    <#if opEquals?has_content>
    <div class="am-u-md-3 am-u-lg-3" >
        <select <#if name?has_content>name="${name}_op"</#if> class="am-input-sm"><#rt/>
        <option value="equals"<#if defaultOption=="equals"> selected="selected"</#if>>${opEquals}</option><#rt/>
        <option value="like"<#if defaultOption=="like"> selected="selected"</#if>>${opBeginsWith}</option><#rt/>
        <option value="contains"<#if defaultOption=="contains"> selected="selected"</#if>>${opContains}</option><#rt/>
        <option value="empty"<#rt/><#if defaultOption=="empty"> selected="selected"</#if>>${opIsEmpty}</option><#rt/>
        <option value="notEqual"<#if defaultOption=="notEqual"> selected="selected"</#if>>${opNotEqual}</option><#rt/>
    </select>
    </div>
    <#else>
    <input type="hidden" name=<#if name?has_content> "${name}_op"</#if>    value="${defaultOption}"/><#rt/>
    </#if>
    <div class="am-u-md-5 am-u-lg-5">
        <input type="text" class="am-form-field am-input-sm" name="${name}"<#if value?has_content> value="${value}"</#if><#if size?has_content> size="${size}"</#if><#if maxlength?has_content>
           maxlength="${maxlength}"</#if><#if autocomplete?has_content> autocomplete="off"</#if>/>
    </div><#rt/>
    <div class="am-u-md-4 am-u-lg-4 an-u-end">
        <#if titleStyle?has_content><span class="${titleStyle}"><#rt/></#if>
        <#if hideIgnoreCase>
             <input type="hidden" name="${name}_ic" value=<#if ignCase>"Y"<#else> ""</#if>/><#rt/>
        <#else>
             <input type="checkbox" name="${name}_ic" value="Y" <#if ignCase> checked="checked"</#if> /> ${ignoreCase}<#rt/>
    </#if><#if titleStyle?has_content></span></#if>
    </div>
    </div>
</#macro>

<#macro renderDateFindField className alert name localizedInputTitle value size maxlength dateType formName defaultDateTimeString imgSrc localizedIconTitle titleStyle defaultOptionFrom defaultOptionThru opEquals opSameDay opGreaterThanFromDayStart opGreaterThan opGreaterThan opLessThan opUpToDay opUpThruDay opIsEmpty>
<div class="am-g">
<div class="am-input-group  am-u-md-4 am-u-lg-4">
<input id="${name?html}_fld0_value" type="text" class="am-form-field am-input-sm" <#if name?has_content> name="${name?html}_fld0_value"</#if><#if localizedInputTitle?has_content>
       title="${localizedInputTitle}"</#if><#if value?has_content> value="${value}"</#if><#if size?has_content> size="${size}"</#if><#if maxlength?has_content> maxlength="${maxlength}"</#if>/><#rt/>
    <#if dateType != "time">
        <script type="text/javascript">
                <#if dateType == "date">
                        jQuery("#${name?html}_fld0_value").datepicker({
                <#else>
                jQuery("#${name?html}_fld0_value").datetimepicker({
                    showSecond: true,
                <#-- showMillisec: true, -->
                    timeFormat: 'hh:mm:ss',
                    stepHour: 1,
                    stepMinute: 5,
                    stepSecond: 10,
                </#if>
                showOn: 'button',
                buttonImage: '',
                buttonText: '',
                buttonSpan:'<span class="am-input-group-btn"><button class="am-btn  am-btn-default "><span class="am-icon-calendar"></span></button></span>',
                buttonImageOnly: false,
                dateFormat: 'yy-mm-dd'

            });
        </script>
        <#rt/>
    </#if>
</div>
    <div class="am-u-md-2 am-u-lg-2">
    <select<#if name?has_content> name="${name}_fld0_op"</#if> class="am-input-sm selectBox"><#rt/>
        <option value="equals"<#if defaultOptionFrom=="equals"> selected="selected"</#if>>${opEquals}</option><#rt/>
        <option value="sameDay"<#if defaultOptionFrom=="sameDay"> selected="selected"</#if>>${opSameDay}</option><#rt/>
        <option value="greaterThanFromDayStart"<#if defaultOptionFrom=="greaterThanFromDayStart"> selected="selected"</#if>>${opGreaterThanFromDayStart}</option><#rt/>
        <option value="greaterThan"<#if defaultOptionFrom=="greaterThan"> selected="selected"</#if>>${opGreaterThan}</option><#rt/>
    </select><#rt/>
    </div><#rt/>
    <#rt/>
    <div class="am-input-group am-u-md-4 am-u-lg-4">
    <input id="${name?html}_fld1_value" type="text" class="am-form-input am-input-sm" <#if name?has_content> name="${name}_fld1_value"</#if><#if localizedInputTitle?exists>
           title="${localizedInputTitle?html}"</#if><#if value2?has_content> value="${value2}"</#if><#if size?has_content> size="${size}"</#if><#if maxlength?has_content> maxlength="${maxlength}"</#if>/><#rt/>
    <#if dateType != "time">
        <script type="text/javascript">
                <#if dateType == "date">
                        jQuery("#${name?html}_fld1_value").datepicker({
                <#else>
                jQuery("#${name?html}_fld1_value").datetimepicker({
                    showSecond: true,
                <#-- showMillisec: true, -->
                    timeFormat: 'hh:mm:ss',
                    stepHour: 1,
                    stepMinute: 5,
                    stepSecond: 10,
                </#if>
                showOn: 'button',
                buttonImage: '',
                buttonSpan:'<span class="am-input-group-btn"><button class="am-btn am-btn-default am-btn-sm "><span class="am-icon-calendar"></span></button></span>',
                buttonText: '',
                buttonImageOnly: false,
                dateFormat: 'yy-mm-dd'
            });
        </script>
        <#rt/>
    </#if>
    </div>
    <div class="am-u-md-2 am-u-lg-2"><#rt/>
    <select name=<#if name?has_content>"${name}_fld1_op"</#if> class="am-input-sm selectBox"><#rt/>
        <option value="opLessThan"<#if defaultOptionThru=="opLessThan"> selected="selected"</#if>>${opLessThan}</option><#rt/>
        <option value="upToDay"<#if defaultOptionThru=="upToDay"> selected="selected"</#if>>${opUpToDay}</option><#rt/>
        <option value="upThruDay"<#if defaultOptionThru=="upThruDay"> selected="selected"</#if>>${opUpThruDay}</option><#rt/>
        <option value="empty"<#if defaultOptionFrom=="empty"> selected="selected"</#if>>${opIsEmpty}</option><#rt/>
    </select><#rt/>
    </div>
</div>

</#macro>

<#macro renderRangeFindField className alert name value size maxlength autocomplete titleStyle defaultOptionFrom opEquals opGreaterThan opGreaterThanEquals opLessThan opLessThanEquals value2 defaultOptionThru>
<input type="text" class="am-form-field am-input-sm" <#if name?has_content>name="${name}_fld0_value"</#if><#if value?has_content> value="${value}"</#if><#if size?has_content>
       size="${size}"</#if><#if maxlength?has_content> maxlength="${maxlength}"</#if><#if autocomplete?has_content> autocomplete="off"</#if>/><#rt/>
    <#if titleStyle?has_content>
    <span class="${titleStyle}"><#rt/>
    </#if>
    <select <#if name?has_content>name="${name}_fld0_op"</#if> class="am-input-sm selectBox"><#rt/>
        <option value="equals"<#if defaultOptionFrom=="equals"> selected="selected"</#if>>${opEquals}</option><#rt/>
        <option value="greaterThan"<#if defaultOptionFrom=="greaterThan"> selected="selected"</#if>>${opGreaterThan}</option><#rt/>
        <option value="greaterThanEqualTo"<#if defaultOptionFrom=="greaterThanEqualTo"> selected="selected"</#if>>${opGreaterThanEquals}</option><#rt/>
    </select><#rt/>
    <#if titleStyle?has_content>
    </span><#rt/>
    </#if>
<br/><#rt/>
<input type="text" class="am-form-field am-input-sm" <#if name?has_content> name="${name}_fld1_value"</#if><#if value2?has_content> value="${value2}"</#if><#if size?has_content>
       size="${size}"</#if><#if maxlength?has_content> maxlength="${maxlength}"</#if><#if autocomplete?has_content> autocomplete="off"</#if>/><#rt/>
    <#if titleStyle?has_content>
    <span class="${titleStyle}"><#rt/>
    </#if>
    <select name=<#if name?has_content>"${name}_fld1_op"</#if> class="am-input-sm selectBox"><#rt/>
        <option value="lessThan"<#if defaultOptionThru=="lessThan"> selected="selected"</#if>>${opLessThan?html}</option><#rt/>
        <option value="lessThanEqualTo"<#if defaultOptionThru=="lessThanEqualTo"> selected="selected"</#if>>${opLessThanEquals?html}</option><#rt/>
    </select><#rt/>
    <#if titleStyle?has_content>
    </span>
    </#if>
</#macro>

<#--
@renderLookupField

Description: Renders a text input field as a lookup field.

Parameter: name, String, required - The name of the lookup field.
Parameter: formName, String, required - The name of the form that contains the lookup field.
Parameter: fieldFormName, String, required - Contains the lookup window form name.
Parameter: className, String, optional - The CSS class name for the lookup field.
Parameter: alert, String, optional - If "true" then the "alert" CSS class will be added to the lookup field.
Parameter: value, Object, optional - The value of the lookup field.
Parameter: size, String, optional - The size of the lookup field.
Parameter: maxlength, String or Integer, optional - The max length of the lookup field.
Parameter: id, String, optional - The ID of the lookup field.
Parameter: event, String, optional - The lookup field event that invokes "action". If the event parameter is not empty, then the action parameter must be specified as well.
Parameter: action, String, optional - The action that is invoked on "event". If action parameter is not empty, then the event parameter must be specified as well.
Parameter: readonly, boolean, optional - If true, the lookup field is made read-only.
Parameter: autocomplete, String, optional - If not empty, autocomplete is turned off for the lookup field.
Parameter: descriptionFieldName, String, optional - If not empty and the presentation parameter contains "window", specifies an alternate input field for updating.
Parameter: targetParameterIter, List, optional - Contains a list of form field names whose values will be passed to the lookup window.
Parameter: imgSrc, Not used.
Parameter: ajaxUrl, String, optional - Contains the Ajax URL, used only when the ajaxEnabled parameter contains true.
Parameter: ajaxEnabled, boolean, optional - If true, invokes the Ajax auto-completer.
Parameter: presentation, String, optional - Contains the lookup window type, either "layer" or "window".
Parameter: width, String or Integer, optional - The width of the lookup field.
Parameter: height, String or Integer, optional - The height of the lookup field.
Parameter: position, String, optional - The position style of the lookup field.
Parameter: fadeBackground, ?
Parameter: clearText, String, optional - If the readonly parameter is true, clearText contains the text to be displayed in the field, default is CommonClear label.
Parameter: showDescription, String, optional - If the showDescription parameter is true, a special span with css class "tooltip" will be created at right of the lookup button and a description will fill in (see setLookDescription in selectall.js). For now not when the lookup is read only.
Parameter: initiallyCollapsed, Not used.
Parameter: lastViewName, String, optional - If the ajaxEnabled parameter is true, the contents of lastViewName will be appended to the Ajax URL.
-->
<#macro renderLookupField name formName fieldFormName className="" alert="false" value="" size="" maxlength="" id="" event="" action="" readonly=false autocomplete="" descriptionFieldName="" targetParameterIter="" imgSrc="" ajaxUrl="" ajaxEnabled=javaScriptEnabled presentation="layer" width="" height="" position="" fadeBackground="true" clearText="" showDescription="" initiallyCollapsed="" lastViewName="main" required="" tooltip="">
    <#if Static["org.ofbiz.widget.ModelWidget"].widgetBoundaryCommentsEnabled(context)>
    <!-- @renderLookupField -->
    </#if>
    <#if (!ajaxUrl?has_content) && ajaxEnabled?has_content && ajaxEnabled>
        <#local ajaxUrl = requestAttributes._REQUEST_HANDLER_.makeLink(request, response, fieldFormName)/>
        <#local ajaxUrl = id + "," + ajaxUrl + ",ajaxLookup=Y" />
    </#if>
    <#if (!showDescription?has_content)>
        <#local showDescriptionProp = Static["org.ofbiz.base.util.UtilProperties"].getPropertyValue("widget.properties", "widget.lookup.showDescription", "N")>
        <#if "Y" == showDescriptionProp>
            <#local showDescription = "true" />
        <#else>
            <#local showDescription = "false" />
        </#if>
    </#if>
    <#if ajaxEnabled?has_content && ajaxEnabled>
    <script type="text/javascript">
        jQuery(document).ready(function () {
            if (!jQuery('form[name="${formName}"]').length) {
                alert("Developer: for lookups to work you must provide a form name!")
            }
        });
    </script>
    </#if>

<#if size?has_content && size=="0"><input type="hidden" <#if name?has_content> name="${name}"/></#if>
<#else>
        <div class="am-input-group">
        <input type="text" class="am-form-field am-input-sm"<#if name?has_content>
        name="${name}"</#if><#if value?has_content> value="${value}"</#if><#if size?has_content>
        size="${size}"</#if><#if maxlength?has_content>
        maxlength="${maxlength}"</#if><#if id?has_content>
        id="${id}"</#if><#rt/><#if readonly?has_content && readonly>
        readonly="readonly"</#if><#rt/>
    <#if event?has_content && action?has_content> ${event}="${action}"</#if><#rt/><#if autocomplete?has_content> autocomplete="off"</#if>
            <#if required?has_content>placeholder="${required}<#if tooltip?has_content>,${tooltip}"<#else>"</#if><#rt/>
            <#else>
                <#if tooltip?has_content> placeholder="${tooltip}"</#if><#rt/>
            </#if>
            /></div><#rt/>
</#if>
    <#if presentation?has_content && descriptionFieldName?has_content && presentation == "window">
        <a href="javascript:call_fieldlookup3(document.${formName?html}.${name?html},document.${formName?html}.${descriptionFieldName},'${fieldFormName}', '${presentation}'<#rt/>
    <#if targetParameterIter?has_content>
     <#list targetParameterIter as item>
    ,document.${formName}.${item}.value<#rt>
     </#list>
    </#if>
    );"></a><#rt>
    <#elseif presentation?has_content && presentation == "window">
        <a href="javascript:call_fieldlookup2(document.${formName?html}.${name?html},'${fieldFormName}', '${presentation}'<#rt/>
    <#if targetParameterIter?has_content>
     <#list targetParameterIter as item>
    ,document.${formName}.${item}.value<#rt>
     </#list>
    </#if>
    );"></a><#rt>
    <#else>
        <#if ajaxEnabled?has_content && ajaxEnabled>
            <#assign defaultMinLength = Static["org.ofbiz.base.util.UtilProperties"].getPropertyValue("widget.properties", "widget.autocompleter.defaultMinLength")>
            <#assign defaultDelay = Static["org.ofbiz.base.util.UtilProperties"].getPropertyValue("widget.properties", "widget.autocompleter.defaultDelay")>
            <#local ajaxUrl = ajaxUrl + "&amp;_LAST_VIEW_NAME_=" + lastViewName />
            <#if !ajaxUrl?contains("searchValueFieldName=")>
                <#if descriptionFieldName?has_content && showDescription == "true">
                    <#local ajaxUrl = ajaxUrl + "&amp;searchValueFieldName=" + descriptionFieldName />
                <#else>
                    <#local ajaxUrl = ajaxUrl + "&amp;searchValueFieldName=" + name />
                </#if>
            </#if>
        </#if>
        <script type="text/javascript">
            jQuery(document).ready(function () {
                new ConstructAmazeLookup("${fieldFormName}", "${id}", document.${formName?html}.${name?html}, <#if descriptionFieldName?has_content>document.${formName?html}.${descriptionFieldName}<#else>null</#if>, "${formName?html}", "${width}", "${height}", "${position}", "${fadeBackground}", <#if ajaxEnabled?has_content && ajaxEnabled>"${ajaxUrl}", ${showDescription}<#else>"", false</#if>, "${presentation!}", "${defaultMinLength!2}", "${defaultDelay!300}"<#rt/>
                    <#if targetParameterIter?has_content>
                        <#assign isFirst = true>
                        <#lt/>, [<#rt/>
                                <#list targetParameterIter as item>
                                    <#if isFirst>
                                            <#lt/>document.${formName}.${item}<#rt/>
                                        <#assign isFirst = false>
                                    <#else>
                                        <#lt/> , document.${formName}.${item}<#rt/>
                                    </#if>
                                </#list>
                                <#lt/>]<#rt/>
                    </#if>
                    <#lt/>);
            });
        </script>
    </#if>
    <#if readonly?has_content && readonly>
        <a id="${id}_clear"
           style="background:none;margin-left:5px;margin-right:15px;"
           class="clearField"
           href="javascript:void(0);"
           onclick="document.${formName}.${name}.value='';
                   jQuery('#' + jQuery('#${id}_clear').next().attr('id').replace('_button','') + '_${id}_lookupDescription').html('');
               <#if descriptionFieldName?has_content>document.${formName}.${descriptionFieldName}.value='';</#if>">
            <#if clearText?has_content>${clearText}<#else>${uiLabelMap.CommonClear}</#if>
        </a>
    </#if>

    <#if ajaxEnabled?has_content && ajaxEnabled && (presentation?has_content && presentation == "window")>
        <#if ajaxUrl?index_of("_LAST_VIEW_NAME_") < 0>
            <#local ajaxUrl = ajaxUrl + "&amp;_LAST_VIEW_NAME_=" + lastViewName />
        </#if>
    <script language="JavaScript" type="text/javascript">ajaxAutoCompleter('${ajaxUrl}', ${showDescription}, ${defaultMinLength!2}, ${defaultDelay!300});</script><#t/>
    </#if>
</#macro>
<#--显示：  显示1 - 20，共70            前 第一页  1 2 3 4  ... 后 最后页-->
<#macro renderNextPrev paginateStyle paginateFirstStyle viewIndex highIndex listSize viewSize ajaxEnabled javaScriptEnabled ajaxFirstUrl firstUrl paginateFirstLabel paginatePreviousStyle ajaxPreviousUrl previousUrl paginatePreviousLabel pageLabel ajaxSelectUrl selectUrl ajaxSelectSizeUrl selectSizeUrl commonDisplaying paginateNextStyle ajaxNextUrl nextUrl paginateNextLabel paginateLastStyle ajaxLastUrl lastUrl paginateLastLabel paginateViewSizeLabel>
    <#if listSize gt viewSize>

    <div class="am-cf">&nbsp;
        <div class="nav-pager am-fl am-padding">${commonDisplaying}
        ${paginateViewSizeLabel}:
            <#if javaScriptEnabled>
                <select name="pageSize" size="1" class=" am-input-sm"
                    onchange="ajaxUpdateAreas('${ajaxSelectSizeUrl}')<#else>location.href='${selectSizeUrl}';;</#if>"><#rt/>
        <#assign availPageSizes = [viewSize, 5,20, 30, 50, 100, 200]>
                    <#list availPageSizes as ps>
                        <option<#if viewSize == ps> selected="selected" </#if> value="${ps}">${ps}</option>
                    </#list>
                </select></#if>
        </div>
        <div class="am-fr">
            <ul class="nav-pager am-pagination">
                <li<#if viewIndex gt 0>><a href="<#if ajaxEnabled>javascript:ajaxUpdateAreas('${ajaxFirstUrl}')<#else>${firstUrl}</#if>">${paginateFirstLabel}</a><#else> class="am-disabled"><a
                        href="#">${paginateFirstLabel}</a></#if></li>

                <li<#if viewIndex gt 0>><a href="<#if ajaxEnabled>javascript:ajaxUpdateAreas('${ajaxPreviousUrl}')<#else>${previousUrl}</#if>">«</a><#else> class="am-disabled"><a href="#">«</a></#if></li>
                <#if listSize gt 0 && javaScriptEnabled>
                <#-- <li>
                     <select name="page" size="1" onchange="<#if ajaxEnabled>javascript:ajaxUpdateAreas('${ajaxSelectUrl}')<#else>location.href='${selectUrl}'+this.value;</#if>"><#rt/>
                         <#assign x=(listSize/viewSize)?ceiling>
                         <#list 1..x as i>
                             <#if i == (viewIndex+1)>
                                 <option selected="selected" value="<#else><option value="</#if>${i-1}">${i}</option>
                         </#list>
                     </select>

                 </li>-->
                    <#assign x=(listSize/viewSize)?ceiling>
                    <#assign pages1 = -1>
                    <#assign pages2 = -1>
                    <#assign pages3 = -1>
                    <#assign pages4 = -1>
                    <#assign pages5 = -1>
                <#-- 说明默认显示前5页， 如果页数大于5，则 取（总页码- 当前页码+5））/2 前后的两页-->
                    <#if (x>5)>
                        <#assign  y =(x-(viewIndex+1))/2?ceiling>
                        <#if (y>0)>
                            <#if ((viewIndex-1+y)<=x &&((viewIndex-1+y)>5)) >
                                <#assign pages1 = (viewIndex-1+y)?ceiling>
                            </#if>
                            <#if ((viewIndex+y)<=x&&((viewIndex-1+y)>5))>
                                <#assign pages2 = (viewIndex+y)?ceiling>
                            </#if>
                            <#if ((viewIndex+1+y)<=x&&((viewIndex-1+y)>5))>
                                <#assign pages3 = (viewIndex+1+y)?ceiling>
                            </#if>
                            <#if ((viewIndex+2+y)<=x&&((viewIndex-1+y)>5))>
                                <#assign pages4 = (viewIndex+2+y)?ceiling>
                            </#if>
                            <#if ((viewIndex+3+y)<=x&&((viewIndex-1+y)>5))>
                                <#assign pages5 = (viewIndex+3+y)?ceiling>
                            </#if>
                        </#if>
                         <#if (y==0)>
                             <#if ((x-3)&gt;5)><#assign pages1 = x-3> </#if>
                             <#if ((x-2)&gt;5)><#assign pages2 = x-2></#if>
                             <#if ((x-1)&gt;5)><#assign pages3 = x-1></#if>

                         </#if>
                    </#if>

                    <#if (x>5)>
                        <#assign x=5>
                    </#if>
                    <#--javascript:ajaxUpdateAreas('search-results,/content/control/findContentSearchResults,contentName_op=contains&noConditionFind=Y&contentId_op=contains&contentId_ic=Y&description_ic=Y&description_op=contains&contentName_ic=Y&VIEW_SIZE_1=30&VIEW_INDEX_1=' + this.value + '')-->
                    <#--amaze 取' + this.value + '' 之前值+index-->
                    <#assign urlIndex = '\' + this.value + \''?length/>

                    <#assign  isExist = false>
                    <#list  1..x as i>
                        <#if (i!=-1)>
                            <#if i == (viewIndex+1)>
                            <#assign isExist = true>
                            <li class="am-active"><a href="<#if ajaxEnabled>javascript:ajaxUpdateAreas('${ajaxSelectUrl?substring(0,ajaxSelectUrl?length-urlIndex)}${i-1}')<#else>${selectUrl}${i-1}</#if>">${i}</a>
                            <#else>
                            <li class=""><a href="<#if ajaxEnabled>javascript:ajaxUpdateAreas('${ajaxSelectUrl?substring(0,ajaxSelectUrl?length-urlIndex)}${i-1}')<#else>${selectUrl}${i-1}</#if>">${i}</a>
                            </#if>
                        </#if>
                    </li>
                    </#list>

                <#--如果当前页不在1..5 和page1..page5 中计算他的值-->



                    <#if (!isExist &&(((viewIndex+1) \lt pages1)|| pages1==-1))>
                        ...<li class="am-active"><a href="#">${viewIndex+1}</a></li>
                    </#if>

                    <#assign  lastIndex = -1>
                    <#list  [pages1,pages2,pages3,pages4,pages5] as i>
                        <#if pages1 &gt; 5>
                            <#if i_index ==0>...</#if>
                                <#if (i!=-1)>
                                    <#if i == (viewIndex+1)>
                                    <li class="am-active"><a href="#">${i}</a>
                                    <#else>
                                    <li class=""><a href="<#if ajaxEnabled>javascript:ajaxUpdateAreas('${ajaxSelectUrl?substring(0,ajaxSelectUrl?length-urlIndex)}${i-1}')<#else>${selectUrl}${i-1}</#if>">${i}</a>
                                    </#if>
                                    <#assign  lastIndex = i>
                                </#if>
                            </li>

                        </#if>
                    </#list>
                <#--${viewIndex?string}-->
                    <#if (lastIndex!=-1 && ((viewIndex \gte lastIndex)))>
                        <li class="am-active"><a href="#">${viewIndex+1}</a></li>
                    </#if>
                <#--<#assign x=(listSize/viewSize)?ceiling>
                <#list 1..x as i>
                    <#if i == (viewIndex+1)>
                    <li class="am-active"><a href="<#if ajaxEnabled>javascript:ajaxUpdateAreas('${ajaxSelectUrl}')<#else>${selectUrl}${i-1}</#if>">${i}</a>
                    <#else>
                    <li class=""><a href="<#if ajaxEnabled>javascript:ajaxUpdateAreas('${ajaxSelectUrl}')<#else>${selectUrl}${i-1}</#if>">${i}</a>
                    </#if>
                </li>
                </#list>-->
                </#if>
                <li<#if highIndex lt listSize>><a href="<#if ajaxEnabled>javascript:ajaxUpdateAreas('${ajaxNextUrl}')<#else>${nextUrl}</#if>">»</a><#else> class="am-disabled"><a href="#">»</a></#if></li>
                <li<#if highIndex lt listSize>><a href="<#if ajaxEnabled>javascript:ajaxUpdateAreas('${ajaxLastUrl}')<#else>${lastUrl}</#if>">${paginateLastLabel}</a><#else> class="am-disabled"><a
                        href="#">${paginateLastLabel}</a></#if></li>
            </ul>
        </div>
        </div>


</#macro>

<#macro renderFileField className alert name value size maxlength autocomplete><input type="file" <@renderClass className alert /><#if name?has_content> name="${name}"</#if><#if value?has_content>
                                                                                      value="${value}"</#if><#if size?has_content> size="${size}"</#if><#if maxlength?has_content>
                                                                                      maxlength="${maxlength}"</#if><#if autocomplete?has_content> autocomplete="off"</#if>/><#rt/></#macro>
<#macro renderPasswordField className alert name value size maxlength id autocomplete><input type="password" <@renderClass className alert /><#if name?has_content> name="${name}"</#if><#if value?has_content>
                                                                                             value="${value}"</#if><#if size?has_content> size="${size}"</#if><#if maxlength?has_content>
                                                                                             maxlength="${maxlength}"</#if><#if id?has_content> id="${id}"</#if><#if autocomplete?has_content>
                                                                                             autocomplete="off"</#if>/></#macro>
<#macro renderImageField value description alternate style event action><img<#if value?has_content> src="${value}"</#if><#if description?has_content>
                                                                                                    title="${description}"</#if> alt="<#if alternate?has_content>${alternate}"</#if><#if style?has_content>
                                                                                                    class="${style}"</#if><#if event?has_content> ${event?html}="${action}" </#if>/></#macro>

<#macro renderBanner style leftStyle rightStyle leftText text rightText>
<div class="am-cf">

        <#if leftText?has_content>
            <div class="am-fl"> ${leftText}</div><#rt/></#if>
        <#if text?has_content>
            <div class="am-center"> ${text}</div><#rt/></#if>
        <#if rightText?has_content>
            <div class="am-fr"> ${rightText} </div><#rt/></#if>

</div>
</#macro>

<#macro renderContainerField id className>
<div id="${id}" class="${className}"/></#macro>

<#macro renderFieldGroupOpen style id title collapsed collapsibleAreaId collapsible expandToolTip collapseToolTip>

    <#if style?has_content || id?has_content || title?has_content>
    <#if style == 'tabs'>
         <div class="am-tab-panel am-fade am-in am-active" id="${id}">
    <#else>
         <div class="am-panel am-panel-default" <#if id?has_content> id="${id}"</#if>>
        <#if collapsible>
                <div class="am-panel-hd am-cf" data-am-collapse="{target: '#${collapsibleAreaId}'}">
                    <#if title?has_content>${title}</#if>
                    <span class="am-icon-chevron-down am-fr"></span>
                </div>
                <#if collapsed>
                    <div id="${collapsibleAreaId}" class="fieldgroup-body am-panel-bd am-collapse ">
                <#else>
                     <div id="${collapsibleAreaId}" class="fieldgroup-body am-panel-bd am-collapse am-in">
                </#if>
        <#else>
            <#if title?has_content>
                <div class="am-panel-hd am-cf am-text-lg">${title}</div>
            </#if>
                <div id="${collapsibleAreaId}" class="fieldgroup-nobody am-panel-bd am-collapse am-in">
        </#if>
        <#rt/>
    </#if>
    </#if>
</#macro>

<#macro renderFieldGroupClose style id title>
    <#if style?has_content || id?has_content || title?has_content>
        <#if style == 'tabs'></div><#else>
            </div></div>
        </#if>
    </#if>

</#macro>

<#macro renderHyperlinkTitle name title showSelectAll="N">
    <#if title?has_content>${title}<br/></#if><#if showSelectAll="Y"><input type="checkbox" name="selectAll" value="Y"
 onclick="toggleAll(this, '${name}');"/></#if></#macro>

<#macro renderSortField style title linkUrl ajaxEnabled><a<#if style?has_content> class="${style}"</#if>
                                                                                  href="<#if ajaxEnabled?has_content && ajaxEnabled>javascript:ajaxUpdateAreas('${linkUrl}')<#else>${linkUrl}</#if>">${title}</a></#macro>
<#macro formatBoundaryComment boundaryType widgetType widgetName><!-- ${boundaryType}  ${widgetType}  ${widgetName} --></#macro>

<#macro renderTooltip tooltip tooltipStyle><#if tooltip?has_content><span class="<#if tooltipStyle?has_content>${tooltipStyle}<#else>tooltip</#if>">${tooltip}</span><#rt/></#if></#macro>

<#macro renderClass className="" alert="">
    <#if className?has_content || (alert?has_content && alert=="true")> class="${className}<#if alert?has_content && alert=="true"> alert</#if>" </#if>
</#macro>

<#macro renderAsterisks requiredField requiredStyle>
    <#if requiredField=="true"><#if !requiredStyle?has_content>*</#if></#if>
</#macro>

<#macro makeHiddenFormLinkForm actionUrl name parameters targetWindow>
<form method="post" action="${actionUrl}" <#if targetWindow?has_content>target="${targetWindow}"</#if> onsubmit="submitFormDisableSubmits(this)" name="${name}"><#list parameters as parameter><input
        name="${parameter.name}" value="${parameter.value}" type="hidden"/></#list></form></#macro>
<#macro makeHiddenFormLinkAnchor linkStyle hiddenFormName event action imgSrc description confirmation><a <#if linkStyle?has_content>class="${linkStyle}"</#if>
                                                                                                          href="javascript:document.${hiddenFormName}.submit()"<#if action?has_content && event?has_content> ${event}="${action}"</#if><#if confirmation?has_content> onclick="return confirm('${confirmation?js_string}')"</#if>><#if imgSrc?has_content>
<img src="${imgSrc}" alt=""/></#if>${description}</a></#macro>
<#macro makeHyperlinkString linkStyle hiddenFormName event action imgSrc title alternate linkUrl targetWindow description confirmation>
        <a <#if linkStyle?has_content>class="${linkStyle}"</#if> href="${linkUrl}"<#if targetWindow?has_content>  target="${targetWindow}"</#if>
        <#if action?has_content && event?has_content> ${event}="${action}"</#if><#if confirmation?has_content>
        onclick="return confirm('${confirmation?js_string}')"</#if><#if imgSrc?length == 0 && title?has_content> title="${title}"</#if>><#if imgSrc?has_content>
        <img src="${imgSrc}" alt="${alternate}" title="${title}"/></#if>${description}</a>
</#macro>

<#--add amaze tab -->
<#macro renderFileldGroupTabStart>
    <div class="am-tabs" data-am-tabs>
        <ul class="am-tabs-nav am-nav am-nav-tabs">
</#macro>
<#macro renderFileldGroupTabNavStart seq title id>
    <#if seq ==0>
        <li class="am-active"><a href="#${id}">${title}</a></li>
    <#else>
        <li><a href="#${id}">${title}</a></li>
    </#if>
</#macro>
<#macro renderFileldGroupTabNavEnd>
</ul>
<div class="am-tabs-bd">
</#macro>
<#macro  renderFileldGroupTabEnd>
</div>
</div>
<br/>
</#macro>

<#--增加inputgroupform-->
<#macro renderInputGroupFieldRowTitleCellOpen  style>
<span class="input-group-addon">
</#macro>
<#macro renderInputGroupFieldRowTitleCellClose >
</span>
</#macro>
<#--End增加inputgroupform-->
<#macro renderInputGroupWrapperOpen formName style>
<div class="form-group m-b-10">
</#macro>
<#macro rendeInputGroupWrapperEnd formName>
</div>
</#macro>
