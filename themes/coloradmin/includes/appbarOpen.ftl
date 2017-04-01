<#--
左侧定义应用菜单
-->
<#if (requestAttributes.person)?exists><#assign person = requestAttributes.person></#if>
<#if (requestAttributes.externalLoginKey)?exists><#assign externalKeyParam = "?externalLoginKey=" + requestAttributes.externalLoginKey?if_exists></#if>
<#if (externalLoginKey)?exists><#assign externalKeyParam = "?externalLoginKey=" + requestAttributes.externalLoginKey?if_exists></#if>
<#assign ofbizServerName = application.getAttribute("_serverId")?default("default-server")>
<#assign contextPath = request.getContextPath()>
<#assign displayApps = Static["org.ofbiz.base.component.ComponentConfig"].getAppBarWebInfos(ofbizServerName, "main")>
<#assign displaySecondaryApps = Static["org.ofbiz.base.component.ComponentConfig"].getAppBarWebInfos(ofbizServerName, "secondary")>
<#-- 设置不显示的一级菜单  -->
<#assign nodisplayAppsNames = ["ar","ap","account","practice","ebay","example","exampleext","googlebase","oagis","ebaystore"]>

<#assign appModelMenu = Static["org.ofbiz.widget.menu.MenuFactory"].getMenuFromLocation(applicationMenuLocation,applicationMenuName,delegator,dispatcher)>
<#if appModelMenu.getModelMenuItemByName(headerItem)?exists>
    <#if headerItem!="main">
        <#assign show_last_menu = true>
    </#if>
</#if>
<#if parameters.portalPageId?exists && !appModelMenu.getModelMenuItemByName(headerItem)?exists>
    <#assign show_last_menu = true>
</#if>
<#--<div id="navigation" <#if show_last_menu?exists>class="menu_selected"</#if>>-->
<#if userLogin?has_content>
    <#if layoutSettings.topLines?has_content>
        <#list layoutSettings.topLines as topLine>
            <#if topLine.text?exists>
                <#assign personName = topLine.text/>
                <#assign profileUrl = topLine.url />
                <#assign profileText = topLine.urlText />
            </#if>
        </#list>
    <#else>
        <#assign personName = userLogin.userLoginId />
    </#if>
<!-- begin #sidebar -->
<div id="sidebar" class="sidebar">
    <!-- begin sidebar scrollbar -->
<div data-scrollbar="true" data-height="100%">
    <!-- begin sidebar user -->
    <ul class="nav">
        <li class="nav-profile">
            <div class="image">
                <a href="javascript:"><img
                        src="<#if layoutSettings.personLogoLinkUrl?has_content>${layoutSettings.personLogoLinkUrl}<#else><@ofbizContentUrl>/images/themes/coloradmin/img/user-1.jpg</@ofbizContentUrl></#if>"
                        alt=""/></a>
            </div>
            <div class="info">
                <#if person?exists>${person.name?if_exists}</#if>
                <small>客服人员</small>
            </div>
        </li>
    </ul>
    <!-- end sidebar user -->
    <!-- begin sidebar nav -->
    <ul class="nav">
        <li class="nav-header">主菜单</li>
        <li class="has-sub">
            <a href="javascript:">
                <b class="caret pull-right"></b>
                <i class="fa fa-laptop"></i>
                <span>控制台</span>
            </a>
        </li>
    <#-- Primary Applications -->
        <#list displayApps as display>
            <#assign thisApp = display.getContextRoot()>
            <#assign permission = true>
            <#assign selected = false>
            <#assign permissions = display.getBasePermission()>
            <#list permissions as perm>
                <#if (perm != "NONE" && !security.hasEntityPermission(perm, "_VIEW", session) && !authz.hasPermission(session, perm, requestParameters))>
                <#-- User must have ALL permissions in the base-permission list -->
                    <#assign permission = false>
                </#if>
            </#list>
            <#if permission == true>
                <#if thisApp == contextPath || contextPath + "/" == thisApp>
                    <#assign selected = true>
                </#if>
                <#assign thisApp = StringUtil.wrapString(thisApp)>
                <#assign thisURL = thisApp>
                <#if thisApp != "/">
                    <#assign thisURL = thisURL + "/control/main">
                </#if>
                <#if layoutSettings.suppressTab?exists && display.name == layoutSettings.suppressTab>
                    <!-- do not display this component-->
                <#else>
                    <li class="has-sub<#if selected> active</#if>">
                        <#if uiLabelMap?exists>
                            <#assign desc = uiLabelMap[display.description]/>
                            <#assign title = uiLabelMap[display.title]/>
                        <#else>
                            <#assign desc = display.description/>
                            <#assign title = display.title/>
                        </#if>
                        <#assign appUrl = thisApp?substring(1)/>
                        <#assign applicationMenuLocation = Static["com.yuaoq.yabiz.admin.page.MenuUtil"].getMenuLocation(appUrl)?default("")/>
                        <#assign applicationMenuName = Static["com.yuaoq.yabiz.admin.page.MenuUtil"].getMenuName(appUrl)?default("")/>
                        <#assign uiLabels = Static["com.yuaoq.yabiz.admin.page.MenuUtil"].getMenuLabel(appUrl)?default("")/>
                        <#assign uistyle = Static["com.yuaoq.yabiz.admin.page.MenuUtil"].getMenuStyle(appUrl)?default("")/>
                        <a href="javascript:" title="${desc}">
                            <b class="caret pull-right"></b>
                            <i class="${uistyle?default("fa fa-laptop")}"></i>
                            <span>${title}</span>
                        </a>
                        <#if applicationMenuLocation!="" && applicationMenuName!="">
                            <#assign appModelMenu = Static["org.ofbiz.widget.menu.MenuFactory"].getMenuFromLocation(applicationMenuLocation,applicationMenuName,delegator,dispatcher)>
                            <ul class="sub-menu">
                                <#assign menuItemList = appModelMenu.getMenuItemList()>
                                <#if menuItemList?exists>
                                    <#list menuItemList as menuItem>
                                        <#assign passed = true />
                                        <#if (!menuItem.shouldBeRendered(context))>
                                            <#assign  passed = false/>
                                        </#if>
                                        <#if passed>
                                            <#assign hasSub = menuItem.getMenuItemList()?has_content/>
                                            <#assign hasLink = menuItem.getLink()?exists/>
                                            <#assign active = false/>
                                            <#assign subActive = false/>
                                            <#if menuItem.getLink()?exists>
                                                <#assign itemLink = menuItem.getLink()>
                                                <#assign currentURI = StringUtil.wrapString("/"+appUrl+"/control/"+itemLink.getTarget(context))>
                                                <#assign requestURI = StringUtil.wrapString(request.getRequestURI())>
                                                <#if (currentURI = requestURI)>
                                                    <#assign active = true/>
                                                <#elseif (("/"+appUrl == StringUtil.wrapString(request.getContextPath())) && (requestURI=="/"+appUrl+"/control/main") && menuItem_index ==0)>
                                                    <#assign active = true />
                                                </#if>
                                            </#if>
                                            <#if hasSub>
                                                <#assign subMenuItems = menuItem.getMenuItemList()>
                                                <#list subMenuItems as subMenuItem>
                                                    <#assign subItemLink = subMenuItem.getLink()>
                                                    <#assign currentURI = StringUtil.wrapString("/"+appUrl+"/control/"+subItemLink.getTarget(context))>
                                                    <#assign requestURI = StringUtil.wrapString(request.getRequestURI())>
                                                    <#if (currentURI == requestURI )>
                                                        <#assign subActive = true/>
                                                    <#elseif ("/"+appUrl == StringUtil.wrapString(request.getContextPath())) && (requestURI=="/"+appUrl+"/control/main") && menuItem_index =0 >
                                                        <#assign subActive = true/>
                                                    </#if>
                                                </#list>
                                            </#if>
                                            <li<#if hasSub && (active || subActive)> class="has-sub active"<#elseif (hasSub)> class="has-sub"<#elseif (active || subActive)>
                                                                                     class="active"</#if>>
                                                <#assign  itemTitle = menuItem.getTitle(context)>
                                                <#list uiLabels as uiLabel>
                                                    <#assign itemTitle = Static["org.ofbiz.base.util.UtilProperties"].getMessage(uiLabel, itemTitle, locale)/>
                                                </#list>
                                                <#if hasLink>
                                                    <#assign itemLink = menuItem.getLink()>
                                                    <#assign commonDisplaying = itemLink.getText(context)>
                                                    <#list uiLabels as uiLabel>
                                                        <#assign commonDisplaying = Static["org.ofbiz.base.util.UtilProperties"].getMessage(uiLabel, commonDisplaying, locale)/>
                                                    </#list>
                                                    <#if !commonDisplaying?starts_with("&#")>
                                                        <#assign commonDisplaying = itemTitle>
                                                    </#if>
                                                    <#if itemLink.getParameterMap(context)?has_content>
                                                        <#assign externalKeyParam1 = externalKeyParam + "&amp;"+ Static["org.ofbiz.base.util.UtilHttp"].urlEncodeArgs(itemLink.getParameterMap(context))/>
                                                    <#else >
                                                        <#assign externalKeyParam1 = externalKeyParam/>
                                                    </#if>
                                                    <a href="/${appUrl}/control/${itemLink.getTarget(context) + externalKeyParam1}"><#if hasSub><b
                                                            class="caret pull-right"></b></#if>${commonDisplaying}</a>
                                                <#else>
                                                    <a href="javascript:"><#if hasSub><b class="caret pull-right"></b></#if>${itemTitle}</a>
                                                </#if>
                                                <#if hasSub>
                                                    <ul class="sub-menu">
                                                        <#assign subMenuItems = menuItem.getMenuItemList()>
                                                        <#list subMenuItems as subMenuItem>
                                                            <#assign passed = true />
                                                            <#if (!subMenuItem.shouldBeRendered(context))>
                                                                <#assign  passed = false/>
                                                            </#if>
                                                            <#if passed>
                                                                <#assign subItemLink = subMenuItem.getLink()>
                                                                <#assign subCommonDisplaying = subItemLink.getText(context)>
                                                                <#list uiLabels as uiLabel>
                                                                    <#assign subCommonDisplaying = Static["org.ofbiz.base.util.UtilProperties"].getMessage(uiLabel, subCommonDisplaying, locale)/>
                                                                </#list>
                                                                <#if !subCommonDisplaying?starts_with("&#")>
                                                                    <#assign subCommonDisplaying = subMenuItem.getTitle(context)>
                                                                </#if>
                                                                <#assign currentURI = StringUtil.wrapString("/"+appUrl+"/control/"+subItemLink.getTarget(context))>
                                                                <#assign requestURI = StringUtil.wrapString(request.getRequestURI())>
                                                                <#if (currentURI = requestURI )>
                                                                <li class="active">
                                                                <#elseif ("/"+appUrl == StringUtil.wrapString(request.getContextPath())) && (requestURI=="/"+appUrl+"/control/main") && menuItem_index =0 >
                                                                <li class="active"><#else>
                                                                <li>
                                                                </#if>
                                                                <#if subItemLink.getParameterMap(context)?has_content>
                                                                    <#assign externalKeyParam1 = externalKeyParam + "&amp;"+ Static["org.ofbiz.base.util.UtilHttp"].urlEncodeArgs(subItemLink.getParameterMap(context))/>
                                                                <#else >
                                                                    <#assign externalKeyParam1 = externalKeyParam/>
                                                                </#if>
                                                                <a href="/${appUrl}/control/${subItemLink.getTarget(context) + externalKeyParam1}">${subCommonDisplaying}</a>
                                                            </li>
                                                            </#if>
                                                        </#list>
                                                    </ul>

                                                </#if>
                                            </li>
                                        </#if>
                                    </#list>
                                </#if>
                            </ul>
                        </#if>
                    </li>
                </#if>
            </#if>
        </#list>
    <#-- Secondary Applications -->
        <#list displaySecondaryApps as display>
            <#assign thisApp = display.getContextRoot()>
            <#assign permission = true>
            <#assign selected = false>
            <#assign permissions = display.getBasePermission()>
            <#list permissions as perm>
                <#if (perm != "NONE" && !security.hasEntityPermission(perm, "_VIEW", session) && !authz.hasPermission(session, perm, requestParameters))>
                <#-- User must have ALL permissions in the base-permission list -->
                    <#assign permission = false>
                </#if>
            </#list>
            <#if permission == true>
                <#if thisApp == contextPath || contextPath + "/" == thisApp>
                    <#assign selected = true>
                </#if>
                <#assign thisApp = StringUtil.wrapString(thisApp)>
                <#assign thisURL = thisApp>
                <#if thisApp != "/">
                    <#assign thisURL = thisURL + "/control/main">
                </#if>
                <#if layoutSettings.suppressTab?exists && display.name == layoutSettings.suppressTab>
                    <!-- do not display this component-->
                <#else>
                    <li class="has-sub<#if selected> active</#if>">
                        <#if uiLabelMap?exists>
                            <#assign desc = uiLabelMap[display.description]/>
                            <#assign title = uiLabelMap[display.title]/>
                        <#else>
                            <#assign desc = display.description/>
                            <#assign title = display.title/>
                        </#if>
                        <#assign appUrl = thisApp?substring(1)/>
                        <#assign applicationMenuLocation = Static["com.yuaoq.yabiz.admin.page.MenuUtil"].getMenuLocation(appUrl)?default("")/>
                        <#assign applicationMenuName = Static["com.yuaoq.yabiz.admin.page.MenuUtil"].getMenuName(appUrl)?default("")/>
                        <#assign uiLabels = Static["com.yuaoq.yabiz.admin.page.MenuUtil"].getMenuLabel(appUrl)?default("")/>
                        <#assign uistyle = Static["com.yuaoq.yabiz.admin.page.MenuUtil"].getMenuStyle(appUrl)?default("")/>
                        <a href="javascript:" title="${desc}">
                            <b class="caret pull-right"></b>
                            <i class="${uistyle?default("fa fa-laptop")}"></i>
                            <span>${title}</span>
                        </a>
                        <#if applicationMenuLocation!="" && applicationMenuName!="">
                            <#assign appModelMenu = Static["org.ofbiz.widget.menu.MenuFactory"].getMenuFromLocation(applicationMenuLocation,applicationMenuName,delegator,dispatcher)>
                            <ul class="sub-menu">
                                <#assign menuItemList = appModelMenu.getMenuItemList()>
                                <#if menuItemList?exists>
                                    <#list menuItemList as menuItem>
                                        <#assign passed = true />
                                        <#if (!menuItem.shouldBeRendered(context))>
                                            <#assign  passed = false/>
                                        </#if>
                                        <#if passed>
                                            <#assign hasSub = menuItem.getMenuItemList()?has_content/>
                                            <#assign hasLink = menuItem.getLink()?exists/>
                                            <#assign active = false/>
                                            <#assign subActive = false/>
                                            <#if menuItem.getLink()?exists>
                                                <#assign itemLink = menuItem.getLink()>
                                                <#assign currentURI = StringUtil.wrapString("/"+appUrl+"/control/"+itemLink.getTarget(context))>
                                                <#assign requestURI = StringUtil.wrapString(request.getRequestURI())>
                                                <#if (currentURI = requestURI)>
                                                    <#assign active = true/>
                                                <#elseif (("/"+appUrl == StringUtil.wrapString(request.getContextPath())) && (requestURI=="/"+appUrl+"/control/main") && menuItem_index ==0)>
                                                    <#assign active = true />
                                                </#if>
                                            </#if>
                                            <#if hasSub>
                                                <#assign subMenuItems = menuItem.getMenuItemList()>
                                                <#list subMenuItems as subMenuItem>
                                                    <#assign subItemLink = subMenuItem.getLink()>
                                                    <#assign currentURI = StringUtil.wrapString("/"+appUrl+"/control/"+subItemLink.getTarget(context))>
                                                    <#assign requestURI = StringUtil.wrapString(request.getRequestURI())>
                                                    <#if (currentURI == requestURI )>
                                                        <#assign subActive = true/>
                                                    <#elseif ("/"+appUrl == StringUtil.wrapString(request.getContextPath())) && (requestURI=="/"+appUrl+"/control/main") && menuItem_index =0 >
                                                        <#assign subActive = true/>
                                                    </#if>
                                                </#list>
                                            </#if>

                                            <li<#if hasSub && (active || subActive)> class="has-sub active"<#elseif (hasSub)> class="has-sub"<#elseif (active || subActive)>
                                                                                     class="active"</#if>>
                                                <#assign  itemTitle = menuItem.getTitle(context)>
                                                <#list uiLabels as uiLabel>
                                                    <#assign itemTitle = Static["org.ofbiz.base.util.UtilProperties"].getMessage(uiLabel, itemTitle, locale)/>
                                                </#list>
                                                <#if hasLink>
                                                    <#assign itemLink = menuItem.getLink()>
                                                    <#assign commonDisplaying = itemLink.getText(context)>
                                                    <#list uiLabels as uiLabel>
                                                        <#assign commonDisplaying = Static["org.ofbiz.base.util.UtilProperties"].getMessage(uiLabel, commonDisplaying, locale)/>
                                                    </#list>
                                                    <#if !commonDisplaying?starts_with("&#")>
                                                        <#assign commonDisplaying = itemTitle>
                                                    </#if>
                                                    <#if itemLink.getParameterMap(context)?has_content>
                                                        <#assign externalKeyParam1 = externalKeyParam + "&amp;"+ Static["org.ofbiz.base.util.UtilHttp"].urlEncodeArgs(itemLink.getParameterMap(context))/>
                                                    <#else >
                                                        <#assign externalKeyParam1 = externalKeyParam/>
                                                    </#if>
                                                    <a href="/${appUrl}/control/${itemLink.getTarget(context) + externalKeyParam1}"><#if hasSub><b
                                                            class="caret pull-right"></b></#if>${commonDisplaying}</a>
                                                <#else>
                                                    <a href="javascript:"><#if hasSub><b class="caret pull-right"></b></#if>${itemTitle}</a>
                                                </#if>
                                                <#if hasSub>
                                                    <ul class="sub-menu">
                                                        <#assign subMenuItems = menuItem.getMenuItemList()>
                                                        <#list subMenuItems as subMenuItem>
                                                            <#assign passed = true />
                                                            <#if (!subMenuItem.shouldBeRendered(context))>
                                                                <#assign  passed = false/>
                                                            </#if>
                                                            <#if passed>
                                                                <#assign subItemLink = subMenuItem.getLink()>
                                                                <#assign subCommonDisplaying = subItemLink.getText(context)>
                                                                <#list uiLabels as uiLabel>
                                                                    <#assign subCommonDisplaying = Static["org.ofbiz.base.util.UtilProperties"].getMessage(uiLabel, subCommonDisplaying, locale)/>
                                                                </#list>
                                                                <#if !subCommonDisplaying?starts_with("&#")>
                                                                    <#assign subCommonDisplaying = subMenuItem.getTitle(context)>
                                                                </#if>
                                                                <#assign currentURI = StringUtil.wrapString("/"+appUrl+"/control/"+subItemLink.getTarget(context))>
                                                                <#assign requestURI = StringUtil.wrapString(request.getRequestURI())>
                                                                <#if (currentURI = requestURI )>
                                                                <li class="active">
                                                                <#elseif ("/"+appUrl == StringUtil.wrapString(request.getContextPath())) && (requestURI=="/"+appUrl+"/control/main") && menuItem_index =0 >
                                                                <li class="active"><#else>
                                                                <li>
                                                                </#if>
                                                                <#if subItemLink.getParameterMap(context)?has_content>
                                                                    <#assign externalKeyParam1 = externalKeyParam + "&amp;"+ Static["org.ofbiz.base.util.UtilHttp"].urlEncodeArgs(subItemLink.getParameterMap(context))/>
                                                                <#else >
                                                                    <#assign externalKeyParam1 = externalKeyParam/>
                                                                </#if>
                                                                <a href="/${appUrl}/control/${subItemLink.getTarget(context) + externalKeyParam1}">${subCommonDisplaying}</a>
                                                            </li>
                                                            </#if>
                                                        </#list>
                                                    </ul>

                                                </#if>
                                            </li>
                                        </#if>
                                    </#list>
                                </#if>
                            </ul>
                        </#if>
                    </li>
                </#if>
            </#if>
        </#list>
        <li><a href="javascript:" class="sidebar-minify-btn" data-click="sidebar-minify"><i class="fa fa-angle-double-left"></i></a></li>
    </ul>
</#if>
</div>
</div>
<div class="sidebar-bg"></div>