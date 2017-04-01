<#--定义header-->
<#assign externalKeyParam = "?externalLoginKey=" + requestAttributes.externalLoginKey?if_exists>
<#if (requestAttributes.person)?exists><#assign person = requestAttributes.person></#if>
<#if (requestAttributes.partyGroup)?exists><#assign partyGroup = requestAttributes.partyGroup></#if>
<#assign docLangAttr = locale.toString()?replace("_", "-")>
<#assign langDir = "ltr">
<#if "ar.iw"?contains(docLangAttr?substring(0, 2))>
  <#assign langDir = "rtl">
</#if>
<html lang="${docLangAttr}" dir="${langDir}" xmlns="http://ww.w3.org/1999/xhtml">
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8"/>
    <meta name="viewport" content="width=device-width,initial-scale=1.0"/>
    <meta name="description" content="裕奥软件是一个企业级电商ERP,电商o2o解决方案,电商产品。">
    <meta name="keywords" content="裕奥软件 电商平台">
    <meta name="keywords" content="裕奥软件 企业ERP, O2O">
    <meta name="keywords" content="裕奥软件 供应链, 内容管理">
    <meta name="keywords" content="裕奥软件 , PORTAL, CRM, 客户管理, 仓储管理, 生产计划, 财务, 可扩展组件">
    <meta name="description" content="Ofbiz开发,Ofbiz培训,Ofbiz技术">
    <meta name="keywords" content="Ofbiz开发,Ofbiz培训,Ofbiz技术">
    <META HTTP-EQUIV="Pragma" CONTENT="no-cache">
    <META HTTP-EQUIV="Cache-Control" CONTENT="no-cache">
    <META HTTP-EQUIV="Expires" CONTENT="0">
    <title>电商定制开发 - 电商中台 - 裕奥软件 -
        ofbiz定制开发${layoutSettings.companyName}: <#if (page.titleProperty)?has_content>${uiLabelMap[page.titleProperty]}<#else>${(page.title)?if_exists}</#if></title>
<#if layoutSettings.shortcutIcon?has_content>
  <#assign shortcutIcon = layoutSettings.shortcutIcon/>
<#elseif layoutSettings.VT_SHORTCUT_ICON?has_content>
  <#assign shortcutIcon = layoutSettings.VT_SHORTCUT_ICON.get(0)/>
</#if>
<#if shortcutIcon?has_content>
    <link rel="shortcut icon" href="<@ofbizContentUrl>${StringUtil.wrapString(shortcutIcon)}</@ofbizContentUrl>"/>
</#if>
<#if layoutSettings.javaScripts?has_content>
<#--layoutSettings.javaScripts is a list of java scripts. -->
<#-- use a Set to make sure each javascript is declared only once, but iterate the list to maintain the correct order -->
  <#assign javaScriptsSet = Static["org.ofbiz.base.util.UtilMisc"].toSetWithoutNull(layoutSettings.javaScripts)/>
  <#list layoutSettings.javaScripts as javaScript>
    <#if javaScript?exists && javaScriptsSet.contains(javaScript)>
      <#assign nothing = javaScriptsSet.remove(javaScript)/>
        <script src="<@ofbizContentUrl>${StringUtil.wrapString(javaScript)}</@ofbizContentUrl>" type="text/javascript"></script>
    </#if>
  </#list>
</#if>
<#if layoutSettings.VT_HDR_JAVASCRIPT?has_content>
  <#assign hdrJavaScripts = Static["org.ofbiz.base.util.UtilMisc"].toSetWithoutNull(layoutSettings.VT_HDR_JAVASCRIPT)/>
  <#list layoutSettings.VT_HDR_JAVASCRIPT as javaScript>
    <#if javaScript?exists && hdrJavaScripts.contains(javaScript)>
      <#assign nothing = hdrJavaScripts.remove(javaScript)/>
        <script src="<@ofbizContentUrl>${StringUtil.wrapString(javaScript)}</@ofbizContentUrl>" type="text/javascript"></script>
    </#if>
  </#list>
</#if>
<#--minify js-->
<#--<script src="<@ofbizUrl>barebone.js</@ofbizUrl>${externalKeyParam}" type="text/javascript"></script>-->
<#if layoutSettings.styleSheets?has_content>
  <#assign styleSheetsSet = Static["org.ofbiz.base.util.UtilMisc"].toSetWithoutNull(layoutSettings.styleSheets)/>

<#--layoutSettings.styleSheets is a list of style sheets. So, you can have a user-specified "main" style sheet, AND a component style sheet.-->
  <#list layoutSettings.styleSheets as styleSheet>
    <#if styleSheet?exists && styleSheetsSet.contains(styleSheet)>
      <#assign nothing = styleSheetsSet.remove(styleSheet)/>
        <link rel="stylesheet" href="<@ofbizContentUrl>${StringUtil.wrapString(styleSheet)}</@ofbizContentUrl>" type="text/css"/>
    </#if>
  </#list>
</#if>
<#if layoutSettings.VT_STYLESHEET?has_content>
  <#list layoutSettings.VT_STYLESHEET as styleSheet>
      <link rel="stylesheet" href="<@ofbizContentUrl>${StringUtil.wrapString(styleSheet)}</@ofbizContentUrl>" type="text/css"/>
  </#list>
</#if>
<#if layoutSettings.rtlStyleSheets?has_content && langDir == "rtl">
<#--layoutSettings.rtlStyleSheets is a list of rtl style sheets.-->
  <#list layoutSettings.rtlStyleSheets as styleSheet>
      <link rel="stylesheet" href="<@ofbizContentUrl>${StringUtil.wrapString(styleSheet)}</@ofbizContentUrl>" type="text/css"/>
  </#list>
</#if>
<#if layoutSettings.VT_RTL_STYLESHEET?has_content && langDir == "rtl">
  <#list layoutSettings.VT_RTL_STYLESHEET as styleSheet>
      <link rel="stylesheet" href="<@ofbizContentUrl>${StringUtil.wrapString(styleSheet)}</@ofbizContentUrl>" type="text/css"/>
  </#list>
</#if>
<#--<link rel="stylesheet" href="<@ofbizUrl>barebone.css</@ofbizUrl>${externalKeyParam}" type="text/css"/>-->

<#if layoutSettings.VT_EXTRA_HEAD?has_content>
  <#list layoutSettings.VT_EXTRA_HEAD as extraHead>
  ${extraHead}
  </#list>
</#if>
<#if layoutSettings.WEB_ANALYTICS?has_content>
    <script language="JavaScript" type="text/javascript">
        <#list layoutSettings.WEB_ANALYTICS as webAnalyticsConfig>
    ${StringUtil.wrapString(webAnalyticsConfig.webAnalyticsCode?if_exists)}
    </#list>
      </script>


</#if>
    <script>
        var _hmt = _hmt || [];
        (function () {
            var hm = document.createElement("script");
            hm.src = "//hm.baidu.com/hm.js?8aa259178d3e2dd5d3165f5e6f5919d4";
            var s = document.getElementsByTagName("script")[0];
            s.parentNode.insertBefore(hm, s);
        })();
    </script>

<#--<script src="/images/themes/coloradmin/plugins/pace/pace.min.js"></script>-->
    <!--[if lt IE 9]>
  <script src="/images/themes/coloradmin/crossbrowserjs/html5shiv.js"></script>
  <script src="/images/themes/coloradmin/crossbrowserjs/respond.min.js"></script>
  <script src="/images/themes/coloradmin/crossbrowserjs/excanvas.min.js"></script>
    <![endif]-->
    <script type="text/javascript">
        $.fn.bootstrapBtn = $.fn.button.noConflict();
        //        $.fn.bootstrapDP = $.fn.datepicker.noConflict();
        //         return $.fn.datepicker to previously assigned value
        //解决bootstrap button 与 jquery ui button 冲突
    </script>
</head>
<#if layoutSettings.headerImageLinkUrl?exists>
  <#assign logoLinkURL = "${layoutSettings.headerImageLinkUrl}">
<#else>
  <#assign logoLinkURL = "${layoutSettings.commonHeaderImageLinkUrl}">
</#if>
<#assign organizationLogoLinkURL = "${layoutSettings.organizationLogoLinkUrl?if_exists}">
<body>
<!-- begin #page-loader -->
<div id="page-loader" class="fade in"><span class="spinner"></span></div>
<!-- end #page-loader -->
<!-- begin #page-container -->
<div id="page-container" class="fade page-sidebar-fixed page-header-fixed">
    <!-- begin #header -->
    <div id="header" class="header navbar navbar-default navbar-fixed-top">
        <!-- begin container-fluid -->
        <div class="container-fluid">
            <!-- begin mobile sidebar expand / collapse button -->
            <div class="navbar-header">
                <a href="#" class="navbar-brand"><span class="navbar-logo"></span> YABIZ</a>
                <button type="button" class="navbar-toggle" data-click="sidebar-toggled">
                    <span class="icon-bar"></span>
                    <span class="icon-bar"></span>
                    <span class="icon-bar"></span>
                </button>
            </div>
            <!-- end mobile sidebar expand / collapse button -->

            <!-- begin header navigation right -->
            <ul class="nav navbar-nav navbar-right">
                <li>
                    <form class="navbar-form full-width">
                        <div class="form-group">
                            <input type="text" class="form-control" placeholder="Enter keyword"/>
                            <button type="submit" class="btn btn-search"><i class="fa fa-search"></i></button>
                        </div>
                    </form>
                </li>
            <#assign  count = 0 />
            <#if layoutSettings.middleTopMessage1?has_content>
              <#assign count = (count+1) />
            </#if>
            <#if layoutSettings.middleTopMessage2?has_content>
              <#assign count = (count+1) />
            </#if>
            <#if layoutSettings.middleTopMessage3?has_content>
              <#assign count = (count+1) />
            </#if>
                <li class="dropdown">
                    <a href="javascript:" data-toggle="dropdown" class="dropdown-toggle f-s-14">
                        <i class="fa fa-bell-o"></i>
                        <span class="label">${count}</span>
                    </a>
                    <ul class="dropdown-menu media-list pull-right animated fadeInDown">
                        <li class="dropdown-header">消息 (${count})</li>
                        <li class="media">
                            <a href="javascript:">
                                <div class="media-left"><i class="fa fa-bug media-object bg-red"></i></div>
                                <div class="media-body">
                                    <h6 class="media-heading">${layoutSettings.middleTopName1?if_exists}</h6>

                                    <p>${layoutSettings.middleTopMessage1?if_exists}</p>

                                    <div class="text-muted f-s-11">3 minutes ago</div>
                                </div>
                            </a>
                        </li>
                        <li class="media">
                            <a href="javascript:">
                                <div class="media-left"><img src="<@ofbizContentUrl>/images/themes/coloradmin/img/user-1.jpg</@ofbizContentUrl>" class="media-object"
                                                             alt=""/></div>
                                <div class="media-body">
                                    <h6 class="media-heading">${layoutSettings.middleTopName2?if_exists}</h6>

                                    <p>${layoutSettings.middleTopMessage2?if_exists}</p>

                                    <div class="text-muted f-s-11">25 minutes ago</div>
                                </div>
                            </a>
                        </li>

                        <li class="media">
                            <a href="javascript:">
                                <div class="media-left"><img src="<@ofbizContentUrl>/images/themes/coloradmin/img//user-2.jpg</@ofbizContentUrl>" class="media-object"
                                                             alt=""/></div>
                                <div class="media-body">
                                    <h6 class="media-heading">${layoutSettings.middleTopName3?if_exists}</h6>

                                    <p>${layoutSettings.middleTopMessage3?if_exists}</p>

                                    <div class="text-muted f-s-11">35 minutes ago</div>
                                </div>
                            </a>
                        </li>

                        <li class="dropdown-footer text-center">
                            <a href="javascript:">查看更多</a>
                        </li>
                    </ul>
                </li>
                <li class="dropdown navbar-user">
                    <a href="javascript:" class="dropdown-toggle" data-toggle="dropdown">
                        <img src="<#if layoutSettings.personLogoLinkUrl?has_content>${layoutSettings.personLogoLinkUrl}<#else><@ofbizContentUrl>/images/themes/coloradmin/img/user-1.jpg</@ofbizContentUrl></#if>"
                             alt=""/>
                        <span class="hidden-xs"><#if person?exists>${person.name?if_exists}</#if></span> <b class="caret"></b>
                    </a>
                    <ul class="dropdown-menu animated fadeInLeft">
                        <li class="arrow"></li>
                        <li><a href="javascript:">个人中心</a></li>
                        <li><a href="javascript:"><span class="badge badge-danger pull-right">2</span> 消息</a></li>

                        <li><a href="<@ofbizUrl>ListVisualThemes</@ofbizUrl>">视觉风格</a></li>
                        <li>
                        <@htmlScreenTemplate.renderModalPage id="header_setLocale" name="header_setLocale"  modalUrl="ListLocales" buttonType="custom"
                        modalTitle="${StringUtil.wrapString(uiLabelMap.CommonChooseLanguage)}" description="${StringUtil.wrapString(uiLabelMap.CommonChooseLanguage)}"/>

                            <#--<a href="<@ofbizUrl>ListLocales</@ofbizUrl>">语言</a> -->
                        </li>
                        <li class="divider"></li>
                        <li><a href="<@ofbizUrl>logout</@ofbizUrl>">退出</a></li>
                    </ul>
                </li>
            </ul>
            <!-- end header navigation right -->
        </div>
        <!-- end container-fluid -->
    </div>
    <!-- end #header -->
