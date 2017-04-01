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

<#macro cmsNewMetaRec>
<input type="hidden" name="contentTypeId" value="DOCUMENT"/>
<input type="hidden" name="dataResourceTypeId" value="SHORT_TEXT"/>
<input type="hidden" name="contentAssocTypeId" value="SUB_CONTENT"/>
<input type="hidden" name="statusId" value="CTNT_PUBLISHED"/>
<input type="hidden" name="ownerContentId" value="${(content.contentId)?if_exists}"/>
<input type="hidden" name="contentIdFrom" value="${(content.contentId)?if_exists}"/>
</#macro>

<#-- cms menu bar -->
<div id="cmsmenu" style="margin-bottom: 8px;">
<#if (content?has_content)>
    <a href="javascript:void(0);" onclick="callDocument(true, '${content.contentId}', '', 'ELECTRONIC_TEXT');" class="am-btn am-btn-primary am-btn-sm">${uiLabelMap.ContentQuickSubContent}</a>

    <a href="javascript:void(0);" onclick="callPathAlias('${content.contentId}');" class="am-btn am-btn-primary am-btn-sm">${uiLabelMap.ContentPathAlias}</a>
    <a href="javascript:void(0);" onclick="callMetaInfo('${content.contentId}');" class="am-btn am-btn-primary am-btn-ms selected">${uiLabelMap.ContentMetaTags}</a>
</#if>
</div>

<#if (content?has_content)>
<div class="am-text-left">
    Set <b>Meta-Data</b> for Content: <b>${content.contentId}</b></b>
</div>
</#if>

<#if (title?has_content)>
    <#assign titleAction = "/updateWebSiteMetaInfoJson"/>
<#else>
    <#assign titleAction = "/createWebSiteMetaInfoJson"/>
</#if>
<#if (titleProperty?has_content)>
    <#assign titlePropertyAction = "/updateWebSiteMetaInfoJson"/>
<#else>
    <#assign titlePropertyAction = "/createWebSiteMetaInfoJson"/>
</#if>
<#if (metaDescription?has_content)>
    <#assign metaDescriptionAction = "/updateWebSiteMetaInfoJson"/>
<#else>
    <#assign metaDescriptionAction = "/createWebSiteMetaInfoJson"/>
</#if>
<#if (metaKeywords?has_content)>
    <#assign metaKeywordsAction = "/updateWebSiteMetaInfoJson"/>
<#else>
    <#assign metaKeywordsAction = "/createWebSiteMetaInfoJson"/>
</#if>

<form name="cmsmeta_title" action="<@ofbizUrl>/${titleAction}</@ofbizUrl>" class="am-form">
<#if (title?has_content)>
    <input type="hidden" name="dataResourceId" value="${title.dataResourceId}"/>
<#else>
    <input type="hidden" name="contentName" value="Meta-Title: ${contentId}"/>
    <input type="hidden" name="mapKey" value="title"/>
    <@cmsNewMetaRec/>
</#if>
    <input type="hidden" name="objectInfo" value=""/>
</form>
<form name="cmsmeta_titleProperty" action="<@ofbizUrl>/${titlePropertyAction}</@ofbizUrl>" class="am-form">
<#if (titleProperty?has_content)>
    <input type="hidden" name="dataResourceId" value="${titleProperty.dataResourceId}"/>
<#else>
    <input type="hidden" name="contentName" value="Meta-TitleProperty: ${contentId}"/>
    <input type="hidden" name="mapKey" value="titleProperty"/>
    <@cmsNewMetaRec/>
</#if>
    <input type="hidden" name="objectInfo" value=""/>
</form>
<form name="cmsmeta_metaDescription" action="<@ofbizUrl>/${metaDescriptionAction}</@ofbizUrl>" class="am-form">
<#if (metaDescription?has_content)>
    <input type="hidden" name="dataResourceId" value="${metaDescription.dataResourceId}"/>
<#else>
    <input type="hidden" name="contentName" value="Meta-Description: ${contentId}"/>
    <input type="hidden" name="mapKey" value="metaDescription"/>
    <@cmsNewMetaRec/>
</#if>
    <input type="hidden" name="objectInfo" value=""/>
</form>
<form name="cmsmeta_metaKeywords" action="<@ofbizUrl>/${metaKeywordsAction}</@ofbizUrl>" class="am-form">
<#if (metaKeywords?has_content)>
    <input type="hidden" name="dataResourceId" value="${metaKeywords.dataResourceId}"/>
<#else>
    <input type="hidden" name="contentName" value="Meta-Keywords: ${contentId}"/>
    <input type="hidden" name="mapKey" value="metaKeywords"/>
    <@cmsNewMetaRec/>
</#if>
    <input type="hidden" name="objectInfo" value=""/>
</form>

<form name="cmsmetaform" action="javascript:void(0);" class="am-form am-form-horizontal">
    <div class="am-g am-margin-top-sm">
        <div class="am-u-sm-3 am-control-label am-text-right">Page Title</div>
        <div class="am-u-sm-6 am-u-end"><input type="text" class="am-input-sm" name="title" value="${(title.objectInfo)?if_exists}" size="40"/></div>
    </div>
    <div class="am-g am-margin-top-sm">
        <div class="am-u-sm-3  am-control-label am-text-right">Title Property</div>
        <div class="am-u-sm-6 am-u-end"><input type="text" name="titleProperty" class="am-input-sm" value="${(titleProperty.objectInfo)?if_exists}" size="40"/>
    </div>
    </div>
    <div class="am-g am-margin-top-sm">
        <div class="am-u-sm-3  am-control-label am-text-right">Meta-Description</div>
        <div class="am-u-sm-6 am-u-end"><input type="text" name="metaDescription" class="am-input-sm" value="${(metaDescription.objectInfo)?if_exists}" size="40"/>
    </div>
    </div>
    <div class="am-g am-margin-top-sm">
        <div class="am-u-sm-3  am-control-label am-text-right">Meta-Keywords</div>
        <div class="am-u-sm-6 am-u-end"><input type="text" name="metaKeywords" class="am-input-sm" value="${(metaKeywords.objectInfo)?if_exists}" size="40"/></div>
    </div>

    <div class="am-g am-margin-top-sm">
        <div class="am-u-sm-9 am- am-control-label am-text-right am-u-end"><input id="submit" type="button" onclick="saveMetaInfo(cmsmetaform);" class="am-btn am-btn-primary am-btn-sm" value="${uiLabelMap.CommonSave}"/></td>
        </div>
    </div>
</form>