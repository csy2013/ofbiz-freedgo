<#--
右侧定义小widget
-->
<#assign appModelMenu = Static["org.ofbiz.widget.menu.MenuFactory"].getMenuFromLocation(applicationMenuLocation,applicationMenuName,delegator,dispatcher)>
<#if person?has_content>
  <#assign userName = person.firstName?if_exists + " " + person.middleName?if_exists + " " + person.lastName?if_exists>
<#elseif partyGroup?has_content>
  <#assign userName = partyGroup.groupName?if_exists>
<#elseif userLogin?exists>
  <#assign userName = userLogin.userLoginId>
<#else>
  <#assign userName = "">
</#if>
<#if defaultOrganizationPartyGroupName?has_content>
  <#assign orgName = " - " + defaultOrganizationPartyGroupName?if_exists>
<#else>
  <#assign orgName = "">
</#if>

<#--<#if userLogin?exists>
    <#if (userPreferences.COMPACT_HEADER)?default("N") == "Y">
        <li class="collapsed"><a href="javascript:document.setUserPreferenceCompactHeaderN.submit()">&nbsp;</a>

            <form name="setUserPreferenceCompactHeaderN" method="post"
                  action="<@ofbizUrl>setUserPreference</@ofbizUrl>">
                <input type="hidden" name="userPrefGroupTypeId" value="GLOBAL_PREFERENCES"/>
                <input type="hidden" name="userPrefTypeId" value="COMPACT_HEADER"/>
                <input type="hidden" name="userPrefValue" value="N"/>
            </form>
        </li>
    <#else>
        <li class="expanded"><a href="javascript:document.setUserPreferenceCompactHeaderY.submit()">&nbsp;</a>

            <form name="setUserPreferenceCompactHeaderY" method="post"
                  action="<@ofbizUrl>setUserPreference</@ofbizUrl>">
                <input type="hidden" name="userPrefGroupTypeId" value="GLOBAL_PREFERENCES"/>
                <input type="hidden" name="userPrefTypeId" value="COMPACT_HEADER"/>
                <input type="hidden" name="userPrefValue" value="Y"/>
            </form>
        </li>
    </#if>
</#if>-->
<div id="page-wrapper" class="gray-bg dashbard-1">
    <div class="row border-bottom">
        <nav class="navbar navbar-static-top" role="navigation" style="margin-bottom: 0">
            <div class="navbar-header">
                <a class="navbar-minimalize minimalize-styl-2 btn btn-primary " href="#"><i class="fa fa-bars"></i> </a>
                <form role="search" class="navbar-form-custom" method="post" action="search_results.html">
                    <div class="form-group">
                        <input type="text" placeholder="Search for something..." class="form-control" name="top-search"
                               id="top-search">
                    </div>
                </form>
            </div>
        <#if userLogin?exists>
            <ul class="nav navbar-top-links navbar-right">
            <#--<li>-->
            <#--<span class="m-r-sm text-muted welcome-message">Welcome to ${uiLabelMap.CommonManagerSystem}</span>-->
            <#--</li>-->
              <#if layoutSettings.topLines?has_content>
                <#list layoutSettings.topLines as topLine>
                  <#if topLine.dropDownList?exists>
                      <li><span class="m-r-sm text-muted welcome-message">${uiLabelMap.CommonWelcome},${topLine.dropDownList[0].value?if_exists}</span>
                      </li>
                  </#if>
                </#list>
              </#if>

              <#if layoutSettings.middleTopMessage1?has_content && layoutSettings.middleTopMessage1 != " ">

                  <li class="dropdown">
                      <a class="dropdown-toggle count-info" data-toggle="dropdown" href="#">
                          <i class="fa fa-bell"></i> <span class="label label-primary">3</span>
                      </a>
                      <ul class="dropdown-menu dropdown-alerts">
                          <li>
                              <a href="${StringUtil.wrapString(layoutSettings.middleTopLink1!)}">
                                  <div>
                                      <i class="fa fa-envelope fa-fw"></i> ${layoutSettings.middleTopMessage1?if_exists}
                                      <span class="pull-right text-muted small">4 minutes ago</span>
                                  </div>
                              </a>
                          </li>
                          <li class="divider"></li>
                          <li>
                              <a href="${StringUtil.wrapString(layoutSettings.middleTopLink2!)}">
                                  <div>
                                      <i class="fa fa-twitter fa-fw"></i> ${layoutSettings.middleTopMessage2?if_exists}
                                      <span class="pull-right text-muted small">12 minutes ago</span>
                                  </div>
                              </a>
                          </li>
                          <li class="divider"></li>
                          <li>
                              <a href="${StringUtil.wrapString(layoutSettings.middleTopLink3!)}">
                                  <div>
                                      <i class="fa fa-upload fa-fw"></i> ${layoutSettings.middleTopMessage3?if_exists}
                                      <span class="pull-right text-muted small">4 minutes ago</span>
                                  </div>
                              </a>
                          </li>
                          <li class="divider"></li>
                          <li>
                              <div class="text-center link-block">
                                  <a href="notifications.html">
                                      <strong>See All Alerts</strong>
                                      <i class="fa fa-angle-right"></i>
                                  </a>
                              </div>
                          </li>
                      </ul>
                  </li>

              </#if>

              <#if parameters.componentName?exists && requestAttributes._CURRENT_VIEW_?exists && helpTopic?exists>
                <#include "component://common/webcommon/includes/helplink.ftl" />
                  <li><a   <#if pageAvail?has_content> alert</#if>"
                      href="javascript:lookup_popup2('showHelp?helpTopic=${helpTopic}&amp;portalPageId=${parameters.portalPageId?if_exists}','help' ,500,500);"
                      title="${uiLabelMap.CommonHelp}"><i class="fa fa-help"></i></a></li>
              </#if>

            <#--<li><a href="<@ofbizUrl>ListLocales</@ofbizUrl>"><i class="fa fa-language"></i>${uiLabelMap.CommonLanguageTitle}</a></li>-->
                <li>
                    <a href="<@ofbizUrl>ListVisualThemes</@ofbizUrl>">
                        <i class="fa fa-google-plus"></i> ${uiLabelMap.CommonVisualThemes}
                    </a>
                </li>
                <li>
                  <@htmlScreenTemplate.renderModalPage id="header_setLocale" name="header_setLocale"  modalUrl="ListLocales" buttonType="custom"
                  buttonStyle="" buttonSpanStyle="<i class='fa fa-language'></i>"
                  modalTitle="${StringUtil.wrapString(uiLabelMap.CommonChooseLanguage)}" description="${StringUtil.wrapString(uiLabelMap.CommonChooseLanguage)}"/>

                        <#--<a href="<@ofbizUrl>ListLocales</@ofbizUrl>">语言</a> -->
                </li>
                <li>
                    <a href="<@ofbizUrl>logout</@ofbizUrl>">
                        <i class="fa fa-sign-out"></i> ${uiLabelMap.CommonLogout}
                    </a>
                </li>
            </ul>
        <#else>
            <li><a href="<@ofbizUrl>${checkLoginUrl}</@ofbizUrl>"><i class="fa fa-sign-in"></i>${uiLabelMap.CommonLogin}</a></li>
        </#if>

        </nav>
    </div>




