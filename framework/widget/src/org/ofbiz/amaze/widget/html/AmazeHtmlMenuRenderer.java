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
package org.ofbiz.amaze.widget.html;

import org.ofbiz.base.util.StringUtil;
import org.ofbiz.base.util.UtilProperties;
import org.ofbiz.base.util.UtilValidate;
import org.ofbiz.entity.GenericValue;
import org.ofbiz.widget.ModelWidget;
import org.ofbiz.widget.html.HtmlMenuRenderer;
import org.ofbiz.widget.menu.ModelMenu;
import org.ofbiz.widget.menu.ModelMenuItem;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Map;

/**
 * Widget Library - HTML Menu Renderer implementation
 */
public class AmazeHtmlMenuRenderer extends HtmlMenuRenderer {

    HttpServletRequest request;
    HttpServletResponse response;

    public AmazeHtmlMenuRenderer(HttpServletRequest request, HttpServletResponse response) {
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
        // 当菜单没有定义Id，说明是下级菜单，则使用amaze的menu div：<nav data-am-widget="menu" class="am-menu  am-menu-default"> ul:am-menu-nav am-avg-sm-3
        String menuId = modelMenu.getId();
        if (UtilValidate.isNotEmpty(menuId)) {
            renderBeginningBoundaryComment(writer, "Menu Widget", modelMenu);
            writer.append("<ul");

            if (UtilValidate.isNotEmpty(menuId)) {
                writer.append(" id=\"").append(menuId).append("\"");
            }
            String menu_div = UtilProperties.getPropertyValue("amaze.properties", "admin.appbar.div");
            writer.append(" class=\"").append(menu_div).append("\"");
            String menuWidth = modelMenu.getMenuWidth();
            // TODO: Eliminate embedded styling after refactor
            if (UtilValidate.isNotEmpty(menuWidth)) {
                writer.append(" style=\"width:").append(menuWidth).append(";\"");
            }
            writer.append(">");
            String menuTitle = modelMenu.getTitle(context);
            writer.append("<li class=\"am-dropdown\" data-am-dropdown><a class=\"am-dropdown-toggle am-text-lg\" data-am-dropdown-toggle href=\"javascript:;\">");
            writer.append(menuTitle+"<span class=\"am-icon-caret-down\"></span></a>");
            appendWhitespace(writer);

        }else{
             if(menuContainerStyle.indexOf("tab-bar")!=-1) {
                 String secondBarNav = UtilProperties.getPropertyValue("amaze.properties", "admin.secondbar.nav");
                 writer.append(secondBarNav);
             }else{

             }
        }
        if (modelMenu.renderedMenuItemCount(context) > 0) {
            String admin_menu_ul = UtilProperties.getPropertyValue("amaze.properties", "admin.appbar.ul");
            if (UtilValidate.isEmpty(menuId)) {
                //使用Amaze menu
                if(menuContainerStyle.indexOf("tab-bar")!=-1) {
                String barStyle = UtilProperties.getPropertyValue("amaze.properties", "admin.secondbar.ul");
                writer.append("<ul class=\"").append(barStyle).append("\">");
                }else{
                    writer.append("<div class=\"am-g am-cf am-padding-ms am-btn-group\">");
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
            style = "am-active";
           /* style = menuItem.getSelectedStyle();
            if (UtilValidate.isEmpty(style)) {
                style = "am-active";
            }*/
        }

        if (menuItem.getDisabled() || this.isDisableIfEmpty(menuItem, context)) {
            style = menuItem.getDisabledTitleStyle();
            style="am-disabled";
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
                link.setStyle("am-btn am-btn-primary am-btn-sm");
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
                writer.append("<button class=\"am-btn am-btn-primary am-btn-sm\">"+txt+"</button>");
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
}
