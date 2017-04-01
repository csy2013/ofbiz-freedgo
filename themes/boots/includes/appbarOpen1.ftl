<#--
左侧定义应用菜单
-->
<#if (requestAttributes.person)?exists><#assign person = requestAttributes.person></#if>
<#if (requestAttributes.externalLoginKey)?exists><#assign externalKeyParam = "?externalLoginKey=" + requestAttributes.externalLoginKey?if_exists></#if>
<#if (externalLoginKey)?exists><#assign externalKeyParam = "?externalLoginKey=" + requestAttributes.externalLoginKey?if_exists></#if>
<#assign ofbizServerName = application.getAttribute("_serverId")?default("default-server")>
<#assign contextPath = request.getContextPath()>
<#assign displayApps = Static["org.ofbiz.base.component.ComponentConfig"].getExistsAppBarWebInfos(ofbizServerName, "main")>
<#assign displaySecondaryApps = Static["org.ofbiz.base.component.ComponentConfig"].getExistsAppBarWebInfos(ofbizServerName, "secondary")>
<#-- 设置不显示的一级菜单  -->

<#assign displayMainAppsMount = ["order","catalog","facility","content","party"]>

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
                        <span>
                            <img alt="image" width="50px" class="img-circle"
                                 src="/images/DEMO1/products/10180/original.jpg"/>
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
            <#list displayMainAppsMount as mount>
              <#assign contextRoot = display.getName()>
              <#if mount == contextRoot>
                <#assign thisApp = display.getContextRoot()?substring(1)>
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
                                  <#if menuItem.getLink()?exists>
                                    <#assign itemLink = menuItem.getLink()>
                                    <#assign commonDisplaying = itemLink.getText(context)>
                                    <#list uiLabels as uiLabel>
                                      <#assign commonDisplaying = Static["org.ofbiz.base.util.UtilProperties"].getMessage(uiLabel, commonDisplaying, locale)/>
                                    </#list>
                                    <#if !commonDisplaying?starts_with("&#")>
                                      <#assign commonDisplaying = menuItem.getTitle(context)>
                                    </#if>
                                      <li>
                                          <a href="/${appUrl}/control/${itemLink.getTarget(context) + externalKeyParam}">${commonDisplaying}</a>
                                      </li>
                                  </#if>
                                </#list>
                              </#if>
                            </ul>

                        </#if>

                      </li>
                  </#if>
                </#if>
              </#if>
            </#list>
          </#list>
        <#-- Secondary Applications -->
          <#list displaySecondaryApps as display>
            <#list displayMainAppsMount as mount>
              <#assign contextRoot = display.getName()>
              <#if mount == contextRoot>
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
                                  <#if menuItem.getLink()?exists>

                                    <#assign itemLink = menuItem.getLink()>

                                  <#--<#assign  mapNameAcsr = ["org.ofbiz.base.util.collections.FlexibleMapAccessor"].getInstance("uiLabelMap")>-->
                                  <#--<#assign  resBundleMap = Static["org.ofbiz.base.util.UtilProperties"].getResourceBundleMap(uiLabels, locale, context)>-->
                                  <#--${mapNameAcsr.put(context, resBundleMap)}-->

                                  <#-- <#assign  resourceBundle = Static["org.ofbiz.base.util.UtilProperties"].getResourceBundleMap(uiLabels, locale, context)>
                                   ${Static["org.ofbiz.base.util.string.UelUtil"].setValue(context,"org.ofbiz.base.util.collections.ResourceBundleMapWrapper", resourceBundle)}
                                   <li><a href="/${appUrl}/control/${itemLink.getTarget(context) + externalKeyParam}">${itemLink.getText(context)}</a></li>-->
                                    <#assign commonDisplaying = itemLink.getText(context)>
                                    <#list uiLabels as uiLabel>
                                      <#assign commonDisplaying = Static["org.ofbiz.base.util.UtilProperties"].getMessage(uiLabel, commonDisplaying, locale)/>
                                    </#list>
                                    <#if !commonDisplaying?starts_with("&#")>
                                    <#--this is error-->
                                      <#assign commonDisplaying = menuItem.getTitle(context)>
                                    <#else>
                                    </#if>
                                      <li>
                                          <a href="/${appUrl}/control/${itemLink.getTarget(context) + externalKeyParam}">${commonDisplaying}</a>
                                      </li>
                                  </#if>
                                </#list>
                              </#if>
                            </ul>
                        </#if>
                      </li>
                  </#if>
                </#if>
              </#if>
            </#list>
          </#list>
        </ul>
    </div>
</nav>
</#if>