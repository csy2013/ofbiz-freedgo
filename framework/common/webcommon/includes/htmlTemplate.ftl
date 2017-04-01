<#--
Licensed to the Apache Software Foundation (ASF) under one
or more contributor license agreements.  See the NOTICE file
distributed with this work for additional information
regarding copyright ownership.  The ASF licenses this file
to you under the Apache License, Version 2.0 (the
"License"); you may not use this file except in compliance
with the License.  You may obtain a copy of the License at

http://www.apache.org/licenses/LICENSE-2.0

Unless required by applicable law or agreed to in writing,
software distributed under the License is distributed on an
"AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
KIND, either express or implied.  See the License for the
specific language governing permissions and limitations
under the License.
-->
<#if userPreferences?exists>
 <#if userPreferences.VISUAL_THEME == "BOOTS">
    <#include "component://widget/templates/bootsHtmlFormMacroLibrary.ftl"/>
<#elseif userPreferences.VISUAL_THEME == "ADMINLET">
    <#include "component://widget/templates/adminletHtmlFormMacroLibrary.ftl"/>
<#elseif userPreferences.VISUAL_THEME == "ACE">
    <#include "component://widget/templates/htmlFormMacroLibrary.ftl"/>
<#elseif userPreferences.VISUAL_THEME == "COLORADMIN">
    <#include "component://widget/templates/coloradminHtmlFormMacroLibrary.ftl"/>
<#else>
    <#include "component://widget/templates/htmlFormMacroLibrary.ftl"/>
</#if>
</#if>


<#macro lookupField className="" alert="" name="" value="" size="20" maxlength="20" id="" event="" action="" readonly="" autocomplete="" descriptionFieldName="" formName="" fieldFormName="" targetParameterIter="" imgSrc="" ajaxUrl="" ajaxEnabled="" presentation="layer" width="" height="" position="topcenter" fadeBackground="true" clearText="" showDescription="" initiallyCollapsed="" required="">
    <#if (!ajaxEnabled?has_content)>
        <#assign javascriptEnabled = Static["org.ofbiz.base.util.UtilHttp"].isJavaScriptEnabled(request) />
        <#if (javascriptEnabled)>
            <#local ajaxEnabled = true>
        </#if>
    </#if>
    <#if (!id?has_content)>
        <#local id = Static["org.ofbiz.base.util.UtilHttp"].getNextUniqueId(request) />
    </#if>
    <#if (!ajaxUrl?has_content)>
        <#local ajaxUrl = requestAttributes._REQUEST_HANDLER_.makeLink(request, response, fieldFormName)/>
        <#local ajaxUrl = id + "," + ajaxUrl + ",ajaxLookup=Y" />
        <#local ajaxUrl = "," + ajaxUrl/>
    </#if>
    <#if (!showDescription?has_content)>
        <#local showDescription = Static["org.ofbiz.base.util.UtilProperties"].getPropertyValue("widget.properties", "widget.lookup.showDescription", "N")>
        <#if "Y" == showDescription>
            <#local showDescription = "true" />
        <#else>
            <#local showDescription = "false" />
        </#if>
    </#if>
        <#if "true" == readonly>
            <#local readonly = true/>
        <#else>
            <#local readonly = false />
        </#if>
        <#if "true" == required>
            <#local required = uiLabelMap.CommonRequired />
        <#else>
            <#local required = "" />
        </#if>
    <@renderLookupField name formName fieldFormName className alert value size maxlength id event action readonly autocomplete descriptionFieldName targetParameterIter imgSrc ajaxUrl ajaxEnabled presentation width height position fadeBackground clearText showDescription initiallyCollapsed lastViewName required tooltip/>
</#macro>

<#macro nextPrev commonUrl="" ajaxEnabled=false javaScriptEnabled=false paginateStyle="nav-pager" paginateFirstStyle="nav-first" viewIndex=0 highIndex=0 listSize=0 viewSize=1 ajaxFirstUrl="" firstUrl="" paginateFirstLabel="" paginatePreviousStyle="nav-previous" ajaxPreviousUrl="" previousUrl="" paginatePreviousLabel="" pageLabel="" ajaxSelectUrl="" selectUrl="" ajaxSelectSizeUrl="" selectSizeUrl="" commonDisplaying="" paginateNextStyle="nav-next" ajaxNextUrl="" nextUrl="" paginateNextLabel="" paginateLastStyle="nav-last" ajaxLastUrl="" lastUrl="" paginateLastLabel="" paginateViewSizeLabel="" targetAreaId="search-results" paramUrl="">

    <#local javaScriptEnabled = javaScriptEnabled />
    <#if (!javaScriptEnabled)>
        <#local javaScriptEnabled = Static["org.ofbiz.base.util.UtilHttp"].isJavaScriptEnabled(request) />
    </#if>
    <#if (commonUrl?has_content)>

        <#if (!firstUrl?has_content)>
            <#local firstUrl=commonUrl+"viewSize=${viewSize}&amp;viewIndex=${viewIndexFirst}"/>
            <#--<#local ajaxFirstUrl =-->
        </#if>
        <#if (!previousUrl?has_content)>
             <#local previousUrl=commonUrl+"viewSize=${viewSize}&amp;viewIndex=${viewIndexPrevious}"/>
        </#if>
        <#if (!nextUrl?has_content)>
            <#local nextUrl=commonUrl+"viewSize=${viewSize}&amp;viewIndex=${viewIndexNext}"/>
        </#if>
        <#if (!lastUrl?has_content)>
            <#local lastUrl=commonUrl+"viewSize=${viewSize}&amp;viewIndex=${viewIndexLast}"/>
            <#local ajaxLastUrl = "',"+targetAreaId+","+commonUrl+","+paramList+"')"/>
        </#if>
        <#if (!selectUrl?has_content)>
            <#local selectUrl=commonUrl+"viewSize=${viewSize}&amp;viewIndex="/>
        </#if>
        <#if (!selectSizeUrl?has_content)>
            <#local selectSizeUrl=commonUrl+"viewSize='+this.value+'&amp;viewIndex=0"/>
        </#if>
    </#if>
    <@renderNextPrev paginateStyle paginateFirstStyle viewIndex highIndex listSize viewSize ajaxEnabled javaScriptEnabled ajaxFirstUrl firstUrl uiLabelMap.CommonFirst paginatePreviousStyle ajaxPreviousUrl previousUrl uiLabelMap.CommonPrevious uiLabelMap.CommonPage ajaxSelectUrl selectUrl ajaxSelectSizeUrl selectSizeUrl commonDisplaying paginateNextStyle ajaxNextUrl nextUrl uiLabelMap.CommonNext paginateLastStyle ajaxLastUrl lastUrl uiLabelMap.CommonLast uiLabelMap.CommonItemsPerPage/>
</#macro>

<#macro nextPrev1 commonUrl="" ajaxEnabled=false javaScriptEnabled=false paginateStyle="nav-pager" paginateFirstStyle="nav-first" viewIndex=0 highIndex=0 listSize=0 viewSize=1 ajaxFirstUrl="" firstUrl="" paginateFirstLabel="" paginatePreviousStyle="nav-previous" ajaxPreviousUrl="" previousUrl="" paginatePreviousLabel="" pageLabel="" ajaxSelectUrl="" selectUrl="" ajaxSelectSizeUrl="" selectSizeUrl="" commonDisplaying="" paginateNextStyle="nav-next" ajaxNextUrl="" nextUrl="" paginateNextLabel="" paginateLastStyle="nav-last" ajaxLastUrl="" lastUrl="" paginateLastLabel="" paginateViewSizeLabel="" targetAreaId="search-results" paramUrl="">
    <#if ajaxEnabled>
        <#if paramUrl?has_content>
            <#local paramUrl = paramUrl+"&amp;ajaxUpdateEvent=Y"/>
        <#else>
            <#local paramUrl = "ajaxUpdateEvent=Y"/>
        </#if>
    </#if>
    <#local javaScriptEnabled = javaScriptEnabled />
    <#if (!javaScriptEnabled)>
        <#local javaScriptEnabled = Static["org.ofbiz.base.util.UtilHttp"].isJavaScriptEnabled(request) />
    </#if>

    <#if (commonUrl?has_content)>
        <#if (!firstUrl?has_content)>
            <#if paramUrl == "">
                <#local paramUrl1 = "viewSize="+viewSize+"&amp;viewIndex="+viewIndexFirst?default(0)>
            <#else>
                <#local paramUrl1 = paramUrl+"&amp;viewSize="+viewSize+"&amp;viewIndex="+viewIndexFirst?default(0)>
            </#if>
            <#local firstUrl = commonUrl +  paramUrl1/>
            <#local ajaxFirstUrl =","+targetAreaId+","+commonUrl+","+paramUrl1/>
        </#if>
        <#if (!previousUrl?has_content)>
            <#if paramUrl == "">
                <#local paramUrl1 = "viewSize="+viewSize+"&amp;viewIndex="+viewIndexPrevious>
            <#else>
                <#local paramUrl1 = paramUrl+"&amp;viewSize="+viewSize+"&amp;viewIndex="+viewIndexPrevious>
            </#if>
            <#local previousUrl = commonUrl +  paramUrl1/>
            <#local ajaxPreviousUrl =","+targetAreaId+","+commonUrl+","+paramUrl1/>
        </#if>
        <#if (!nextUrl?has_content)>
            <#if paramUrl == "">
                <#local paramUrl1 = "viewSize="+viewSize+"&amp;viewIndex="+viewIndexNext>
            <#else>
                <#local paramUrl1 = paramUrl+"&amp;viewSize="+viewSize+"&amp;viewIndex="+viewIndexNext>
            </#if>
            <#local nextUrl=commonUrl+paramUrl1/>
            <#local ajaxNextUrl =","+targetAreaId+","+commonUrl+","+paramUrl1/>
        </#if>
        <#if (!lastUrl?has_content)>
            <#if paramUrl == "">
                <#local paramUrl1 = "viewSize="+viewSize+"&amp;viewIndex="+viewIndexLast>
            <#else>
                <#local paramUrl1 = paramUrl+"&amp;viewSize="+viewSize+"&amp;viewIndex="+viewIndexLast>
            </#if>
            <#local lastUrl=commonUrl+paramUrl1/>
            <#local ajaxLastUrl =","+targetAreaId+","+commonUrl+","+paramUrl1/>
        </#if>
        <#if (!selectUrl?has_content)>
            <#if !paramUrl?has_content>
                <#local paramUrl1 = "viewSize="+viewSize+"&amp;viewIndex=">
            <#else>
                <#local paramUrl1 = paramUrl+"&amp;viewSize="+viewSize+"&amp;viewIndex=">
            </#if>

            <#local selectUrl=commonUrl+paramUrl1/>
            <#local ajaxSelectUrl =","+targetAreaId+","+commonUrl+","+paramUrl1/>

        </#if>
        <#if (!selectSizeUrl?has_content)>
            <#if paramUrl == "">
                <#local paramUrl1 = "viewSize='+this.value+'&amp;viewIndex=0">
            <#else>
                <#local paramUrl1 = paramUrl+"&amp;viewSize='+this.value+'&amp;viewIndex=0">
            </#if>
            <#local selectSizeUrl=commonUrl+paramUrl1/>
            <#local ajaxSelectSizeUrl =","+targetAreaId+","+commonUrl+","+paramUrl1/>
        </#if>
    </#if>

    <@renderNextPrev paginateStyle paginateFirstStyle viewIndex highIndex listSize viewSize ajaxEnabled javaScriptEnabled ajaxFirstUrl firstUrl uiLabelMap.CommonFirst paginatePreviousStyle ajaxPreviousUrl previousUrl uiLabelMap.CommonPrevious uiLabelMap.CommonPage ajaxSelectUrl selectUrl ajaxSelectSizeUrl selectSizeUrl commonDisplaying paginateNextStyle ajaxNextUrl nextUrl uiLabelMap.CommonNext paginateLastStyle ajaxLastUrl lastUrl uiLabelMap.CommonLast uiLabelMap.CommonItemsPerPage/>
</#macro>