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

<nav class="navbar-default navbar-static-side" role="navigation">
    <div class="sidebar-collapse">
        <ul class="nav" id="side-menu">
            <li class="nav-header">
                <div class="dropdown profile-element">
                        <span class="personImage">
                           <img src="<#if layoutSettings.personLogoLinkUrl?has_content>${layoutSettings.personLogoLinkUrl}<#else><@ofbizContentUrl>/images/themes/coloradmin/img/user-1.jpg</@ofbizContentUrl></#if>"
                                class="img-circle" alt="User Image">
                        </span>
                    <a data-toggle="dropdown" class="dropdown-toggle" href="#">
                            <span class="clear"> <span class="block m-t-xs"> <strong class="font-bold">
                            ${personName?if_exists},${profileText?if_exists}</strong>
                             </span> <span class="text-muted text-xs block">客服人员 <b
                                    class="caret"></b></span> </span> </a>
                    <ul class="dropdown-menu animated fadeInRight m-t-xs">
                        <li>
                            <a href="${StringUtil.wrapString(profileUrl?if_exists)}&amp;externalLoginKey=${externalLoginKey}">${profileText?if_exists}</a>
                        </li>
                        <li>
                            <a href="${StringUtil.wrapString(profileUrl?if_exists)}&amp;externalLoginKey=${externalLoginKey}">Contacts</a>
                        </li>
                        <li>
                            <a href="${StringUtil.wrapString(profileUrl?if_exists)}&amp;externalLoginKey=${externalLoginKey}">Mailbox</a>
                        </li>
                        <li class="divider"></li>
                        <li><a href="<@ofbizUrl>logout</@ofbizUrl>">Logout</a></li>
                    </ul>
                </div>
                <div class="logo-element">
                    IN+
                </div>
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
                  <li<#if selected> class="active"</#if>>
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
                      <a href="#" title="${desc}">
                          <i class="${uistyle}"></i><span class="nav-label">${title}</span>
                          <span class="fa arrow"></span>
                      </a>

                    <#if applicationMenuLocation!="" && applicationMenuName!="">
                      <#assign appModelMenu = Static["org.ofbiz.widget.menu.MenuFactory"].getMenuFromLocation(applicationMenuLocation,applicationMenuName,delegator,dispatcher)>
                        <ul class="nav nav-second-level collapse">
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

                                  <li <#if active || subActive> class="active"</#if>>
                                    <#if hasLink>
                                      <#assign itemLink = menuItem.getLink()>
                                      <#assign commonDisplaying = itemLink.getText(context)>
                                      <#list uiLabels as uiLabel>
                                        <#assign commonDisplaying = Static["org.ofbiz.base.util.UtilProperties"].getMessage(uiLabel, commonDisplaying, locale)/>
                                      </#list>
                                      <#if !commonDisplaying?starts_with("&#")>
                                        <#assign commonDisplaying = menuItem.getTitle(context)>
                                      </#if>
                                        <#if itemLink.getParameterMap(context)?has_content>
                                            <#assign externalKeyParam1 = externalKeyParam + "&amp;"+ Static["org.ofbiz.base.util.UtilHttp"].urlEncodeArgs(itemLink.getParameterMap(context))/>
                                        <#else >
                                            <#assign externalKeyParam1 = externalKeyParam/>
                                        </#if>
                                        <a href="/${appUrl}/control/${itemLink.getTarget(context) + externalKeyParam1}">${commonDisplaying}<#if hasSub><span
                                                class="fa arrow"></span></#if></a>
                                    <#else>
                                        <a href="#">${menuItem.getTitle(context)}<#if hasSub><span class="fa arrow"></span></#if></a>
                                    </#if>
                                    <#if hasSub>
                                        <ul class="nav nav-third-level">
                                          <#assign subMenuItems = menuItem.getMenuItemList()>
                                          <#list subMenuItems as subMenuItem>
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

                                            <#if (currentURI == requestURI )>
                                            <li class="active">
                                            <#elseif ("/"+appUrl == StringUtil.wrapString(request.getContextPath())) && (requestURI=="/"+appUrl+"/control/main") && menuItem_index =0 >
                                            <li class="active">
                                            <#else>
                                            <li>
                                            </#if>
                                              <#if subItemLink.getParameterMap(context)?has_content>
                                                  <#assign externalKeyParam1 = externalKeyParam + "&amp;"+ Static["org.ofbiz.base.util.UtilHttp"].urlEncodeArgs(subItemLink.getParameterMap(context))/>
                                              <#else >
                                                  <#assign externalKeyParam1 = externalKeyParam/>
                                              </#if>
                                              <a href="/${appUrl}/control/${subItemLink.getTarget(context) + externalKeyParam1}">${subCommonDisplaying}</a></li>
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
                <#if uiLabelMap?exists>
                  <#assign desc = uiLabelMap[display.description]/>
                  <#assign title = uiLabelMap[display.title]/>
                <#else>
                  <#assign desc = display.description/>
                  <#assign title = display.title/>
                </#if>
                  <li<#if selected> class="active"</#if>>
                    <#assign appUrl = thisApp?substring(1)/>
                    <#assign applicationMenuLocation = Static["com.yuaoq.yabiz.admin.page.MenuUtil"].getMenuLocation(appUrl)?default("")/>
                    <#assign applicationMenuName = Static["com.yuaoq.yabiz.admin.page.MenuUtil"].getMenuName(appUrl)?default("")/>
                    <#assign uiLabels = Static["com.yuaoq.yabiz.admin.page.MenuUtil"].getMenuLabel(appUrl)?default("")/>
                    <#assign uistyle = Static["com.yuaoq.yabiz.admin.page.MenuUtil"].getMenuStyle(appUrl)?default("")/>
                      <a href="${thisURL + externalKeyParam}" title="${desc}">
                          <i class="${uistyle}"></i><span class="nav-label">${title}</span><span
                              class="fa arrow"></span>
                      </a>
                    <#if applicationMenuLocation != "" && applicationMenuName != "">

                    <#--${setRequestAttribute("applicationMenuLocation", location)}-->
                    <#--${setRequestAttribute("applicationMenuName", name)}-->
                    <#--${setRequestAttribute("applicationUilabels", uiLabels)}-->
                    <#--${screens.render("component://admin/widget/CommonScreens.xml#displayMenu")}-->

                      <#assign appModelMenu = Static["org.ofbiz.widget.menu.MenuFactory"].getMenuFromLocation(applicationMenuLocation,applicationMenuName,delegator,dispatcher)>
                        <ul class="nav nav-second-level collapse">
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

                                  <li <#if active || subActive> class="active"</#if>>
                                    <#if hasLink>
                                      <#assign itemLink = menuItem.getLink()>
                                      <#assign commonDisplaying = itemLink.getText(context)>
                                      <#list uiLabels as uiLabel>
                                        <#assign commonDisplaying = Static["org.ofbiz.base.util.UtilProperties"].getMessage(uiLabel, commonDisplaying, locale)/>
                                      </#list>
                                      <#if !commonDisplaying?starts_with("&#")>
                                        <#assign commonDisplaying = menuItem.getTitle(context)>
                                      </#if>
                                        <#if itemLink.getParameterMap(context)?has_content>
                                            <#assign externalKeyParam1 = externalKeyParam + "&amp;"+ Static["org.ofbiz.base.util.UtilHttp"].urlEncodeArgs(itemLink.getParameterMap(context))/>
                                        <#else >
                                            <#assign externalKeyParam1 = externalKeyParam/>
                                        </#if>
                                        <a href="/${appUrl}/control/${itemLink.getTarget(context) + externalKeyParam1}">${commonDisplaying}<#if hasSub><span
                                                class="fa arrow"></span></#if></a>
                                    <#else>
                                        <a href="#">${menuItem.getTitle(context)}<#if hasSub><span class="fa arrow"></span></#if></a>
                                    </#if>
                                    <#if hasSub>
                                        <ul class="nav nav-third-level">
                                          <#assign subMenuItems = menuItem.getMenuItemList()>
                                          <#list subMenuItems as subMenuItem>
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
                                            <li class="active">
                                            <#else>
                                            <li>
                                            </#if>
                                              <#if subItemLink.getParameterMap(context)?has_content>
                                                  <#assign externalKeyParam1 = externalKeyParam + "&amp;"+ Static["org.ofbiz.base.util.UtilHttp"].urlEncodeArgs(subItemLink.getParameterMap(context))/>
                                              <#else >
                                                  <#assign externalKeyParam1 = externalKeyParam/>
                                              </#if>
                                              <a href="/${appUrl}/control/${subItemLink.getTarget(context) + externalKeyParam1}">${subCommonDisplaying}</a></li>
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
        </ul>
    </div>
</nav>
</#if>