/*******************************************************************************
 * Licensed to the Apache Software Foundation (ASF) under one
 * or more contributor license agreements.  See the NOTICE file
 * distributed with this work for additional information
 * regarding copyright ownership.  The ASF licenses this file
 * to you under the Apache License, Version 2.0 (the
 * "License"); you may not use this file except in compliance
 * with the License.  You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing,
 * software distributed under the License is distributed on an
 * "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
 * KIND, either express or implied.  See the License for the
 * specific language governing permissions and limitations
 * under the License.
 *******************************************************************************/
package org.ofbiz.adminlet.widget.html;

import org.ofbiz.base.util.StringUtil;
import org.ofbiz.base.util.UtilMisc;
import org.ofbiz.base.util.UtilProperties;
import org.ofbiz.base.util.UtilValidate;
import org.ofbiz.entity.GenericValue;
import org.ofbiz.widget.ModelWidget;
import org.ofbiz.widget.WidgetWorker;
import org.ofbiz.widget.html.HtmlMenuRenderer;
import org.ofbiz.widget.menu.ModelMenu;
import org.ofbiz.widget.menu.ModelMenuItem;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.math.BigDecimal;
import java.util.Map;

/**
 * Widget Library - HTML Menu Renderer implementation
 */
public class AdminletHtmlMenuRenderer extends HtmlMenuRenderer {

    HttpServletRequest request;
    HttpServletResponse response;

    public AdminletHtmlMenuRenderer(HttpServletRequest request, HttpServletResponse response) {
        super(request, response);
        this.request = request;
        this.response = response;
    }

    public void renderMenuOpen(Appendable writer, Map<String, Object> context, ModelMenu modelMenu) throws IOException {
        if (!userLoginIdHasChanged) {
            userLoginIdHasChanged = userLoginIdHasChanged();
        }
        //Debug.logInfo("in HtmlMenuRenderer, userLoginIdHasChanged:" + userLoginIdHasChanged,"");
        this.widgetCommentsEnabled = ModelWidget.widgetBoundaryCommentsEnabled(context);
        // TODO: Remove else after UI refactor - allow both id and style
        String menuContainerStyle = modelMenu.getMenuContainerStyle(context);
        //当为左侧菜单时定义了id 时style 为：div : admin-sidebar ul:am-list admin-sidebar-list
        // 当菜单没有定义Id，说明是下级菜单，则使用adminlet的menu div：<nav data-am-widget="menu" class="am-menu  am-menu-default"> ul:am-menu-nav am-avg-sm-3
        String menuId = modelMenu.getId();
        if (UtilValidate.isNotEmpty(menuId)) {
            renderBeginningBoundaryComment(writer, "Menu Widget", modelMenu);
            writer.append("<ul");

            if (UtilValidate.isNotEmpty(menuId)) {
                writer.append(" id=\"").append(menuId).append("\"");
            }
            String menu_div = UtilProperties.getPropertyValue("adminlet.properties", "admin.appbar.div");
            writer.append(" class=\"").append(menu_div).append("\"");
            String menuWidth = modelMenu.getMenuWidth();
            // TODO: Eliminate embedded styling after refactor
            if (UtilValidate.isNotEmpty(menuWidth)) {
                writer.append(" style=\"width:").append(menuWidth).append(";\"");
            }
            writer.append(">");
            String menuTitle = modelMenu.getTitle(context);
            writer.append("<li class=\"dropdown\" data-dropdown><a class=\"dropdown-toggle text-lg\" data-dropdown-toggle href=\"javascript:;\">");
            writer.append(menuTitle+"<span class=\"icon-caret-down\"></span></a>");
            appendWhitespace(writer);

        }else{
             if(menuContainerStyle.indexOf("tab-bar")!=-1) {
                 String secondBarNav = UtilProperties.getPropertyValue("adminlet.properties", "admin.secondbar.nav");
                 writer.append(secondBarNav);
             }else{

             }
        }
        if (modelMenu.renderedMenuItemCount(context) > 0) {
            String admin_menu_ul = UtilProperties.getPropertyValue("adminlet.properties", "admin.appbar.ul");
            if (UtilValidate.isEmpty(menuId)) {
                //使用Adminlet menu
                if(menuContainerStyle.indexOf("tab-bar")!=-1) {
                String barStyle = UtilProperties.getPropertyValue("adminlet.properties", "admin.secondbar.ul");
                writer.append("<ul class=\"").append(barStyle).append("\">");
                }else{
                    writer.append("<div class=\"ibox m-t-md\">");
                    appendWhitespace(writer);
                }
            }else{
                    writer.append("<ul class=\"").append(admin_menu_ul).append("\">");
            }
            appendWhitespace(writer);
        }
    }

    public void renderMenuClose(Appendable writer, Map<String, Object> context, ModelMenu modelMenu) throws IOException {
        // TODO: div can't be directly inside an UL
        String fillStyle = modelMenu.getFillStyle();
        if (UtilValidate.isNotEmpty(fillStyle)) {
            writer.append("<div class=\"").append(fillStyle).append("\">&nbsp;</div>");
        }
        if (modelMenu.renderedMenuItemCount(context) > 0) {
            String menuContainerStyle = modelMenu.getMenuContainerStyle(context);
            if(menuContainerStyle.indexOf("tab-bar")!=-1) {
                writer.append("</ul>");
                appendWhitespace(writer);
            }else{
                writer.append("</div>");
                appendWhitespace(writer);
            }
        }
        String menuId = modelMenu.getId();
        appendWhitespace(writer);
        if (UtilValidate.isNotEmpty(menuId)) {
            writer.append("</li>");
            writer.append("</ul>");
        }else{
//            writer.append("</nav>");
        }

        appendWhitespace(writer);
        renderEndingBoundaryComment(writer, "Menu Widget", modelMenu);

        userLoginIdHasChanged = userLoginIdHasChanged();
        GenericValue userLogin = (GenericValue) request.getSession().getAttribute("userLogin");
        if (userLogin != null) {
            String userLoginId = userLogin.getString("userLoginId");
            //request.getSession().setAttribute("userLoginIdAtPermGrant", userLoginId);
            setUserLoginIdAtPermGrant(userLoginId);
            //Debug.logInfo("in HtmlMenuRenderer, userLoginId(Close):" + userLoginId + " userLoginIdAtPermGrant:" + request.getSession().getAttribute("userLoginIdAtPermGrant"),"");
        } else {
            request.getSession().setAttribute("userLoginIdAtPermGrant", null);
        }
    }


    public void renderMenuItem(Appendable writer, Map<String, Object> context, ModelMenuItem menuItem) throws IOException {
   //Debug.logInfo("in renderMenuItem, menuItem:" + menuItem.getName() + " context:" + context ,"");
        boolean hideThisItem = isHideIfSelected(menuItem, context);
        //if (Debug.infoOn()) Debug.logInfo("in HtmlMenuRendererImage, hideThisItem:" + hideThisItem,"");
        if (hideThisItem)
            return;

        String menuId = menuItem.getModelMenu().getId();

        String style = menuItem.getWidgetStyle();

        if (menuItem.isSelected(context)) {
            style = "active";
           /* style = menuItem.getSelectedStyle();
            if (UtilValidate.isEmpty(style)) {
                style = "am-active";
            }*/
        }

        if (menuItem.getDisabled() || this.isDisableIfEmpty(menuItem, context)) {
            style = menuItem.getDisabledTitleStyle();
            style="disabled";
        }

        String menuContainerStyle = menuItem.getModelMenu().getMenuContainerStyle(context);
        if (UtilValidate.isNotEmpty(menuId)||menuContainerStyle.indexOf("tab-bar")!=-1) {
            writer.append("  <li");

        String alignStyle = menuItem.getAlignStyle();
        if (UtilValidate.isNotEmpty(style) || UtilValidate.isNotEmpty(alignStyle)) {
            writer.append(" class=\"");
            if (UtilValidate.isNotEmpty(style)) {
                writer.append(style).append(" ");
            }
            if (UtilValidate.isNotEmpty(alignStyle)) {
                writer.append(alignStyle);
            }
            writer.append("\"");
        }
        String toolTip = menuItem.getTooltip(context);
        if (UtilValidate.isNotEmpty(toolTip)) {
            writer.append(" title=\"").append(toolTip).append("\"");
        }
            writer.append(">");
        }
        ModelMenuItem.Link link = menuItem.getLink();

        //if (Debug.infoOn()) Debug.logInfo("in HtmlMenuRendererImage, link(0):" + link,"");
        if (link != null) {
            if (UtilValidate.isNotEmpty(menuId)||menuContainerStyle.indexOf("tab-bar")!=-1) {
            }else{
                link.setStyle("btn btn-primary");
            }
            renderLink(writer, context, link);
        } else {
            String txt = menuItem.getTitle(context);
            StringUtil.SimpleEncoder simpleEncoder = (StringUtil.SimpleEncoder) context.get("simpleEncoder");
            if (simpleEncoder != null) {
                txt = simpleEncoder.encode(txt);
            }
            if (UtilValidate.isNotEmpty(menuId)||menuContainerStyle.indexOf("tab-bar")!=-1) {
                writer.append(txt);
            }else{
                writer.append("<button class=\"btn btn-primary\">"+txt+"</button>");
            }


        }
        if (UtilValidate.isNotEmpty(menuId)||menuContainerStyle.indexOf("tab-bar")!=-1) {
            if (!menuItem.getMenuItemList().isEmpty()) {
                appendWhitespace(writer);
                writer.append("    <ul>");
                appendWhitespace(writer);
                for (ModelMenuItem childMenuItem : menuItem.getMenuItemList()) {
                    childMenuItem.renderMenuItemString(writer, context, this);
                }
                writer.append("    </ul>");
                appendWhitespace(writer);
            }
        }
            appendWhitespace(writer);

    }


    public void renderLink(Appendable writer, Map<String, Object> context, ModelMenuItem.Link link) throws IOException {
        String target = link.getTarget(context);
        ModelMenuItem menuItem = link.getLinkMenuItem();
        if (menuItem.getDisabled() || isDisableIfEmpty(menuItem, context)) {
            target = null;
        }

        if (UtilValidate.isNotEmpty(target)) {
            HttpServletResponse response = (HttpServletResponse) context.get("response");
            HttpServletRequest request = (HttpServletRequest) context.get("request");

            String targetWindow = link.getTargetWindow(context);
            String uniqueItemName = menuItem.getModelMenu().getName() + "_" + menuItem.getName() + "_LF_" + UtilMisc.addToBigDecimalInMap(context, "menuUniqueItemIndex", BigDecimal.ONE);

            String linkType = WidgetWorker.determineAutoLinkType(link.getLinkType(), target, link.getUrlMode(), request);


            writer.append("<a");
            String id = link.getId(context);
            if (UtilValidate.isNotEmpty(id)) {
                writer.append(" id=\"");
                writer.append(id);
                writer.append("\"");
            }

            String style = link.getStyle(context);
            if (UtilValidate.isNotEmpty(style)) {
                writer.append(" class=\"");
                writer.append(style);
                writer.append("\"");
            }
            String name = link.getName(context);
            if (UtilValidate.isNotEmpty(name)) {
                writer.append(" name=\"");
                writer.append(name);
                writer.append("\"");
            }
            if (!"hidden-form".equals(linkType)) {
                if (UtilValidate.isNotEmpty(targetWindow)) {
                    writer.append(" target=\"");
                    writer.append(targetWindow);
                    writer.append("\"");
                }
            }

            writer.append(" href=\"");
            String confirmationMsg = link.getConfirmation(context);
            if ("hidden-form".equals(linkType)) {
                if (UtilValidate.isNotEmpty(confirmationMsg)) {
                    writer.append("javascript:confirmActionFormLink('");
                    writer.append(confirmationMsg);
                    writer.append("', '");
                    writer.append(uniqueItemName);
                    writer.append("')");
                } else {
                    writer.append("javascript:document.");
                    writer.append(uniqueItemName);
                    writer.append(".submit()");
                }
            } else {
                if (UtilValidate.isNotEmpty(confirmationMsg)) {
                    writer.append("javascript:confirmActionLink('");
                    writer.append(confirmationMsg);
                    writer.append("', '");
                    WidgetWorker.buildHyperlinkUrl(writer, target, link.getUrlMode(), link.getParameterMap(context), link.getPrefix(context),
                            link.getFullPath(), link.getSecure(), link.getEncode(), request, response, context);
                    writer.append("')");
                } else {
                    WidgetWorker.buildHyperlinkUrl(writer, target, link.getUrlMode(), link.getParameterMap(context), link.getPrefix(context),
                            link.getFullPath(), link.getSecure(), link.getEncode(), request, response, context);
                }
            }
            writer.append("\">");

            if ("hidden-form".equals(linkType)) {
                writer.append("<form method=\"post\"");
                writer.append(" action=\"");
                // note that this passes null for the parameterList on purpose so they won't be put into the URL
                WidgetWorker.buildHyperlinkUrl(writer, target, link.getUrlMode(), null, link.getPrefix(context),
                        link.getFullPath(), link.getSecure(), link.getEncode(), request, response, context);
                writer.append("\"");

                if (UtilValidate.isNotEmpty(targetWindow)) {
                    writer.append(" target=\"");
                    writer.append(targetWindow);
                    writer.append("\"");
                }

                writer.append(" name=\"");
                writer.append(uniqueItemName);
                writer.append("\">");

                StringUtil.SimpleEncoder simpleEncoder = (StringUtil.SimpleEncoder) context.get("simpleEncoder");
                for (Map.Entry<String, String> parameter : link.getParameterMap(context).entrySet()) {
                    writer.append("<input name=\"");
                    writer.append(parameter.getKey());
                    writer.append("\" value=\"");
                    if (simpleEncoder != null) {
                        writer.append(simpleEncoder.encode(parameter.getValue()));
                    } else {
                        writer.append(parameter.getValue());
                    }
                    writer.append("\" type=\"hidden\"/>");
                }

                writer.append("</form>");
            }
        }

        // the text
        ModelMenuItem.Image img = link.getImage();
        if (img != null) {
            renderImage(writer, context, img);
            writer.append("&nbsp;" + link.getText(context));
        } else {
            writer.append(link.getText(context));
        }


        if (UtilValidate.isNotEmpty(target)) {
            // close tag
            writer.append("</a>");
        }

        /* NOTE DEJ20090316: This was here as a comment and not sure what it is for or if it is useful... can probably be safely removed in the future if still not used/needed
        boolean isSelected = menuItem.isSelected(context);

        String style = null;

        if (isSelected) {
        style = menuItem.getSelectedStyle();
        } else {
        style = link.getStyle(context);
        if (UtilValidate.isEmpty(style))
        style = menuItem.getTitleStyle();
        if (UtilValidate.isEmpty(style))
        style = menuItem.getWidgetStyle();
        }

        if (menuItem.getDisabled()) {
        style = menuItem.getDisabledTitleStyle();
        }

        if (UtilValidate.isNotEmpty(style)) {
        writer.append(" class=\"");
        writer.append(style);
        writer.append("\"");
        }
        */
    }
}
