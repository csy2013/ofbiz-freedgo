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
        <#include "component://widget/templates/bootsHtmlScreenMacroLibrary.ftl"/>
        <#assign panelStyle="ibox float-e-margins"/>
        <#assign panelHeadingStyle="ibox-title"/>
        <#assign panelHeadingBarStyle="ibox-tools"/>
        <#assign panelBodyStyle="ibox-content"/>
        <#assign panelTitleStyle=""/>
    <#elseif userPreferences.VISUAL_THEME == "ADMINLET">
        <#include "component://widget/templates/adminletHtmlScreenMacroLibrary.ftl"/>
        <#assign panelStyle="box box-info"/>
        <#assign panelHeadingStyle="box-header with-border"/>
        <#assign panelHeadingBarStyle="box-tools pull-right"/>
        <#assign panelBodyStyle="box-body"/>
        <#assign panelTitleStyle="box-title"/>
    <#elseif userPreferences.VISUAL_THEME == "ACE">
        <#include "component://widget/templates/htmlScreenMacroLibrary.ftl"/>
    <#elseif userPreferences.VISUAL_THEME == "COLORADMIN">
        <#include "component://widget/templates/coloradminHtmlScreenMacroLibrary.ftl"/>
        <#assign panelStyle="panel panel-default"/>
        <#assign panelHeadingStyle="panel-heading"/>
        <#assign panelHeadingBarStyle="panel-heading-btn"/>
        <#assign panelBodyStyle="panel-body"/>
        <#assign panelTitleStyle="panel-title"/>
    <#else>
        <#include "component://widget/templates/htmlScreenMacroLibrary.ftl"/>
        <#assign panelStyle="panel panel-default"/>
        <#assign panelHeadingStyle="panel-heading"/>
        <#assign panelHeadingBarStyle="panel-heading-btn"/>
        <#assign panelBodyStyle="panel-body"/>
        <#assign panelTitleStyle="panel-title"/>
    </#if>
</#if>

