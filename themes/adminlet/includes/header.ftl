<#--定义header-->
<#if (requestAttributes.person)?exists><#assign person = requestAttributes.person></#if>
<#assign externalKeyParam = "?externalLoginKey=" + requestAttributes.externalLoginKey?if_exists>
<#if (requestAttributes.person)?exists><#assign person = requestAttributes.person></#if>
<#if (requestAttributes.partyGroup)?exists><#assign partyGroup = requestAttributes.partyGroup></#if>
<#assign docLangAttr = locale.toString()?replace("_", "-")>
<#assign langDir = "ltr">
<#if "ar.iw"?contains(docLangAttr?substring(0, 2))>
  <#assign langDir = "rtl">
</#if>
<html lang="${docLangAttr}" dir="${langDir}" xmlns="http://www.w3.org/1999/xhtml">
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
  <#list layoutSettings.VT_HDR_JAVASCRIPT as javaScript>
      <script src="<@ofbizContentUrl>${StringUtil.wrapString(javaScript)}</@ofbizContentUrl>" type="text/javascript"></script>
  </#list>
</#if>

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
<#--<script src="<@ofbizUrl>barebone.js</@ofbizUrl>${externalKeyParam}" type="text/javascript"></script>-->
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

    <!--[if lt IE 9]>
  <script src="https://oss.maxcdn.com/html5shiv/3.7.3/html5shiv.min.js"></script>
  <script src="https://oss.maxcdn.com/respond/1.4.2/respond.min.js"></script>
    <![endif]-->
    <script type="text/javascript">
        $.fn.bootstrapBtn = $.fn.button.noConflict();
        //        $.fn.bootstrapDP = $.fn.datepicker.noConflict(); // return $.fn.datepicker to previously assigned value
        //解决bootstrap button 与 jquery ui button 冲突
    </script>
</head>
<#if layoutSettings.headerImageLinkUrl?exists>
  <#assign logoLinkURL = "${layoutSettings.headerImageLinkUrl}">
<#else>
  <#assign logoLinkURL = "${layoutSettings.commonHeaderImageLinkUrl}">
</#if>
<#assign organizationLogoLinkURL = "${layoutSettings.organizationLogoLinkUrl?if_exists}">

<body class="skin-blue sidebar-mini">
<div id="wrapper" class="wrapper">
    <header class="main-header">
        <!-- Logo -->
        <a href="#" class="logo">
            <!-- mini logo for sidebar mini 50x50 pixels -->
            <span class="logo-mini"><b>YA</b>BIZ</span>
            <!-- logo for regular state and mobile devices -->
            <span class="logo-lg"><b>YABIZ</b>中台管理</span>
        </a>
        <!-- Header Navbar: style can be found in header.less -->
        <nav class="navbar navbar-static-top" role="navigation">
            <!-- Sidebar toggle button-->
            <a href="#" class="sidebar-toggle" data-toggle="offcanvas" role="button">
                <span class="sr-only"></span>
                <span class="icon-bar"></span>
                <span class="icon-bar"></span>
                <span class="icon-bar"></span>
            </a>

            <div class="navbar-custom-menu">
                <ul class="nav navbar-nav">
                    <!-- Messages: style can be found in dropdown.less-->
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
                    <li class="dropdown messages-menu">
                        <a href="#" class="dropdown-toggle" data-toggle="dropdown">
                            <i class="fa fa-envelope-o"></i>
                            <span class="label label-success">${count}</span>
                        </a>
                        <ul class="dropdown-menu">
                            <li class="header">你有${count}条消息</li>
                        <#if layoutSettings.middleTopMessage1?has_content && layoutSettings.middleTopMessage1 != " ">
                            <li>
                                <a href="${StringUtil.wrapString(layoutSettings.middleTopLink1!)}">
                                    <div class="pull-left"></div>
                                    <h4>${layoutSettings.middleTopMessage1?if_exists}
                                        <small><i class="fa fa-clock-o"></i> 5 mins</small>
                                    </h4>
                                    <p>${layoutSettings.middleTopName1?if_exists}</p>
                                </a>
                            </li>
                        </#if>
                        <#if layoutSettings.middleTopMessage2?has_content && layoutSettings.middleTopMessage2 != " ">
                            <li>
                                <a href="${StringUtil.wrapString(layoutSettings.middleTopLink2!)}">
                                    <div class="pull-left"></div>
                                    <h4>${layoutSettings.middleTopMessage2?if_exists}
                                        <small><i class="fa fa-clock-o"></i> 5 mins</small>
                                    </h4>
                                    <p>${layoutSettings.middleTopName2?if_exists}</p>
                                </a>

                            </li>
                        </#if>
                        <#if layoutSettings.middleTopMessage3?has_content && layoutSettings.middleTopMessage3 != " ">
                            <li>
                                <a href="${StringUtil.wrapString(layoutSettings.middleTopLink3!)}">
                                    <div class="pull-left"></div>
                                    <h4>${layoutSettings.middleTopMessage3?if_exists}
                                        <small><i class="fa fa-clock-o"></i> 5 mins</small>
                                    </h4>
                                    <p>${layoutSettings.middleTopName3?if_exists}</p>
                                </a>

                            </li>

                        </#if>
                            <li class="footer"><a href="#">查看所有消息</a></li>
                        </ul>
                    </li>
                    <!-- Notifications: style can be found in dropdown.less -->
                    <li class="dropdown notifications-menu">
                        <a href="#" class="dropdown-toggle" data-toggle="dropdown">
                            <i class="fa fa-bell-o"></i>
                            <span class="label label-warning">10</span>
                        </a>
                        <ul class="dropdown-menu">
                            <li class="header">You have 10 notifications</li>
                            <li>
                                <!-- inner menu: contains the actual data -->
                                <ul class="menu">
                                    <li>
                                        <a href="#">
                                            <i class="fa fa-users text-aqua"></i> 5 new members joined today
                                        </a>
                                    </li>
                                    <li>
                                        <a href="#">
                                            <i class="fa fa-warning text-yellow"></i> Very long description here that
                                            may not fit into the page and may cause design problems
                                        </a>
                                    </li>
                                    <li>
                                        <a href="#">
                                            <i class="fa fa-users text-red"></i> 5 new members joined
                                        </a>
                                    </li>

                                    <li>
                                        <a href="#">
                                            <i class="fa fa-shopping-cart text-green"></i> 25 sales made
                                        </a>
                                    </li>
                                    <li>
                                        <a href="#">
                                            <i class="fa fa-user text-red"></i> You changed your username
                                        </a>
                                    </li>
                                </ul>
                            </li>
                            <li class="footer"><a href="#">View all</a></li>
                        </ul>
                    </li>
                    <!-- Tasks: style can be found in dropdown.less -->
                    <li class="dropdown tasks-menu">
                        <a href="#" class="dropdown-toggle" data-toggle="dropdown">
                            <i class="fa fa-flag-o"></i>
                            <span class="label label-danger">9</span>
                        </a>
                        <ul class="dropdown-menu">
                            <li class="header">You have 9 tasks</li>
                            <li>
                                <!-- inner menu: contains the actual data -->
                                <ul class="menu">
                                    <li><!-- Task item -->
                                        <a href="#">
                                            <h3>
                                                Design some buttons
                                                <small class="pull-right">20%</small>
                                            </h3>
                                            <div class="progress xs">
                                                <div class="progress-bar progress-bar-aqua" style="width: 20%"
                                                     role="progressbar" aria-valuenow="20" aria-valuemin="0"
                                                     aria-valuemax="100">
                                                    <span class="sr-only">20% Complete</span>
                                                </div>
                                            </div>
                                        </a>
                                    </li>
                                    <!-- end task item -->
                                    <li><!-- Task item -->
                                        <a href="#">
                                            <h3>
                                                Create a nice theme
                                                <small class="pull-right">40%</small>
                                            </h3>
                                            <div class="progress xs">
                                                <div class="progress-bar progress-bar-green" style="width: 40%"
                                                     role="progressbar" aria-valuenow="20" aria-valuemin="0"
                                                     aria-valuemax="100">
                                                    <span class="sr-only">40% Complete</span>
                                                </div>
                                            </div>
                                        </a>
                                    </li>
                                    <!-- end task item -->
                                    <li><!-- Task item -->
                                        <a href="#">
                                            <h3>
                                                Some task I need to do
                                                <small class="pull-right">60%</small>
                                            </h3>
                                            <div class="progress xs">
                                                <div class="progress-bar progress-bar-red" style="width: 60%"
                                                     role="progressbar" aria-valuenow="20" aria-valuemin="0"
                                                     aria-valuemax="100">
                                                    <span class="sr-only">60% Complete</span>
                                                </div>
                                            </div>
                                        </a>
                                    </li>
                                    <!-- end task item -->
                                    <li><!-- Task item -->
                                        <a href="#">
                                            <h3>
                                                Make beautiful transitions
                                                <small class="pull-right">80%</small>
                                            </h3>
                                            <div class="progress xs">
                                                <div class="progress-bar progress-bar-yellow" style="width: 80%"
                                                     role="progressbar" aria-valuenow="20" aria-valuemin="0"
                                                     aria-valuemax="100">
                                                    <span class="sr-only">80% Complete</span>
                                                </div>
                                            </div>
                                        </a>
                                    </li>
                                    <!-- end task item -->
                                </ul>
                            </li>
                            <li class="footer">
                                <a href="#">View all tasks</a>
                            </li>
                        </ul>
                    </li>
                    <!-- User Account: style can be found in dropdown.less -->
                    <li class="dropdown user user-menu">
                        <a href="#" class="dropdown-toggle" data-toggle="dropdown">
                            <img src="<#if layoutSettings.personLogoLinkUrl?has_content>${layoutSettings.personLogoLinkUrl}<#else><@ofbizContentUrl>/images/themes/coloradmin/img/user-1.jpg</@ofbizContentUrl></#if>"
                                 class="user-image" alt="User Image">
                            <span class="hidden-xs"><#if person?exists>${person.name?if_exists}</#if></span>
                        </a>
                        <ul class="dropdown-menu">
                            <!-- User image -->
                            <li class="user-header">

                                <img src="<#if layoutSettings.personLogoLinkUrl?has_content>${layoutSettings.personLogoLinkUrl}<#else><@ofbizContentUrl>/images/themes/coloradmin/img/user-1.jpg</@ofbizContentUrl></#if>"
                                     class="img-circle" alt="User Image">
                            <#--<p>-->
                            <#--Alexander Pierce - Web Developer-->
                            <#--<small>Member since Nov. 2012</small>-->
                            <#--</p>-->
                            </li>
                            <!-- Menu Body -->
                            <li class="user-body">
                                <div class="col-xs-4 text-center">
                                    <a href="#">个人中心</a>
                                </div>
                                <div class="col-xs-4 text-center">
                                    <a href="<@ofbizUrl>ListVisualThemes</@ofbizUrl>">${uiLabelMap.CommonVisualThemes}</a>
                                </div>
                                <div class="col-xs-4 text-center">
                                <@htmlScreenTemplate.renderModalPage id="header_setLocale" name="header_setLocale"  modalUrl="ListLocales" buttonType="custom"
                                modalTitle="${StringUtil.wrapString(uiLabelMap.CommonChooseLanguage)}" description="${StringUtil.wrapString(uiLabelMap.CommonChooseLanguage)}"/>

                                </div>
                            </li>
                            <!-- Menu Footer-->
                            <li class="user-footer">
                                <div class="pull-left">
                                    <a href="#" class="btn btn-default btn-flat">Profile</a>
                                </div>
                                <div class="pull-right">
                                    <a class="btn btn-default btn-flat" href="<@ofbizUrl>logout</@ofbizUrl>">${uiLabelMap.CommonLogout}</a>

                                </div>
                            </li>
                        </ul>
                    </li>
                    <!-- Control Sidebar Toggle Button -->
                    <li>
                        <a href="#" data-toggle="control-sidebar"><i class="fa fa-gears"></i></a>
                    </li>
                </ul>
            </div>
        </nav>
    </header>