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
package org.ofbiz.amaze.widget.screen;


import javolution.util.FastList;
import org.ofbiz.amaze.widget.html.AmazeHtmlFormRenderer;
import org.ofbiz.amaze.widget.html.AmazeHtmlMenuRenderer;
import org.ofbiz.base.util.*;
import org.ofbiz.base.util.collections.MapStack;
import org.ofbiz.entity.Delegator;
import org.ofbiz.entity.GenericEntityException;
import org.ofbiz.entity.GenericValue;
import org.ofbiz.entity.condition.EntityCondition;
import org.ofbiz.widget.PortalPageWorker;
import org.ofbiz.widget.form.FormStringRenderer;
import org.ofbiz.widget.form.ModelForm;
import org.ofbiz.widget.menu.MenuStringRenderer;
import org.ofbiz.widget.menu.ModelMenu;
import org.ofbiz.widget.screen.ModelScreenWidget;
import org.ofbiz.widget.screen.ScreenFactory;
import org.ofbiz.widget.screen.ScreenRenderer;
import org.ofbiz.widget.screen.ScreenStringRenderer;
import org.w3c.dom.Element;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.ListIterator;
import java.util.Map;

/**
 * Widget Library - Screen model class
 */
@SuppressWarnings("serial")
public abstract class AmazeModelScreenWidget extends ModelScreenWidget {

    public static final String module = AmazeModelScreenWidget.class.getName();
    public static final String contarea = "contentarea";
    public static final String colContainer = "column-container";
    public static final String conMainSection = "content-main-section";
    public static final String extend = "amaze";
//    public static final String contarea = "contentarea";

    public AmazeModelScreenWidget(AmazeModelScreen modelScreen, Element widgetElement) {
        super(modelScreen, widgetElement);
        if (Debug.verboseOn()) Debug.logVerbose("Reading Screen sub-widget with name: " + widgetElement.getNodeName(), module);
    }

    public static void renderSubWidgetsString(List<ModelScreenWidget> subWidgets, Appendable writer, Map<String, Object> context, ScreenStringRenderer screenStringRenderer) throws GeneralException, IOException {
        if (subWidgets == null) {
            return;
        }
        for (int i = 0; i < subWidgets.size(); i++) {
            ModelScreenWidget subWidget = subWidgets.get(i);
            if (Debug.verboseOn()) Debug.logVerbose("Rendering screen " + subWidget.modelScreen.getName() + "; widget class is " + subWidget.getClass().getName(), module);
            // render the sub-widget itself
            if(i==(subWidgets.size()-1)){
                //add by changsy 2015/2/5 amaze 设置 am-g 是最后一个div class add am-g-end
                context.put("isEnd",true);
                subWidget.renderWidgetString(writer, context, screenStringRenderer);
                context.remove("isEnd");
            }else if(i==0){
                context.put("isFirst",true);
                subWidget.renderWidgetString(writer, context, screenStringRenderer);
                context.remove("isFirst");
            } else{
                subWidget.renderWidgetString(writer, context, screenStringRenderer);
            }

        }
    }


    public static class SectionsRenderer extends ModelScreenWidget.SectionsRenderer {

        public SectionsRenderer(Map<String, ? extends Object> sectionMap, Map<String, Object> context, Appendable writer, ScreenStringRenderer screenStringRenderer) {
           super(sectionMap,context,writer,screenStringRenderer);
        }

        /**
         * This is a lot like the ScreenRenderer class and returns an empty String so it can be used more easily with FreeMarker
         */
        public String render(String sectionName) throws GeneralException, IOException {
            ModelScreenWidget section = (ModelScreenWidget) this.get(sectionName);
            // if no section by that name, write nothing
            if (section != null) {
                section.renderWidgetString(this.writer, this.context, this.screenStringRenderer);
            }
            return "";
        }
    }


    public static class Container extends ModelScreenWidget.Container {


        public Container(AmazeModelScreen modelScreen, Element containerElement) {
            super(modelScreen, containerElement);
            // read sub-widgets
            List<? extends Element> subElementList = UtilXml.childElementList(containerElement);
            this.subWidgets = AmazeModelScreenWidget.readSubWidgets(this.modelScreen, subElementList, "amaze");
        }

        public String getStyle(Map<String, Object> context) {
            String style = this.styleExdr.expandString(context);
            String id = this.idExdr.toString();

            /**
             * style: contentarea 代表是内容部分
             * id:  column-container: 段
             * id : content-main-section 主体
             <container style="contentarea">
             <decorator-section-include name="pre-body" />
             <container id="column-container">
             <section>
             <condition>
             <if-empty-section section-name="left-column" />
             </condition>
             <widgets>
             <container id="content-main-section">
             <decorator-section-include name="body" />
             </container>
             </widgets>
             <fail-widgets>
             <container style="left">
             <decorator-section-include name="left-column" />
             </container>
             <container id="content-main-section" style="leftonly">
             <container style="no-clear" id="centerdiv">
             <decorator-section-include name="body" />
             </container>
             </container>
             </fail-widgets>
             </section>
             <container style="clear"></container>
             </container>
             </container>
             */
            if (style.equals("contentarea")) {
                String contentStyle = UtilProperties.getPropertyValue("amaze.properties", "admin.content");
                style = contentStyle;
            }else if(id.equals("left-column")){
                style = UtilProperties.getPropertyValue("amaze.properties","admin.default.container.style");
//                style = style + " am-fr";
            }else if(id.equals("centerdiv")){
                style = UtilProperties.getPropertyValue("amaze.properties","admin.default.container.style");
//                style = style + " am-fl";
            }else if(style.equals("lefthalf")){
                style = UtilProperties.getPropertyValue("amaze.properties", "admin.default.container.half.style");
//                style = style + " am-fl";
            }else if(style.equals("righthalf")){
                style = UtilProperties.getPropertyValue("amaze.properties","admin.default.container.half.style");
//                style = style + " am-fl";
            }else {
                style = UtilProperties.getPropertyValue("amaze.properties","admin.default.container.style");
            }
            return style;
        }

        @Override
        public void renderWidgetString(Appendable writer, Map<String, Object> context, ScreenStringRenderer screenStringRenderer) throws GeneralException, IOException {

           /* if(this.styleExdr.expandString(context)!=null && this.styleExdr.expandString(context).equals("clear")){
                return;
            }*/
            try {
//                screenStringRenderer = new AmazeMacroScreenRenderer(UtilProperties.getPropertyValue("widget.properties", getName() + ".name"), UtilProperties.getPropertyValue("widget", getName() + ".screenrenderer"));
                String id = getId(context);
                if(id.equals("left-column")||(id.equals("cmsnav"))){
                    writer.append("<div class=\"am-g\">");
                    writer.append("<div class=\"am-u-md-4 am-u-lg-4 am-cf am-fr\">");
                }else if(id.equals("centerdiv")||id.equals("cmsmain")){
                    writer.append("<div class=\"am-u-md-8 am-u-lg-8 am-cf am-fl\">");
                }

                if (UtilValidate.isNotEmpty(id) && (id.equals(conMainSection) || id.equals(colContainer))) {

                } else
                    screenStringRenderer.renderContainerBegin(writer, context, this);
                if(id.equals("split50")){
                    writer.append("<div class=\"am-g\">");
                }
                // render sub-widgets

                renderSubWidgetsString(this.subWidgets, writer, context, screenStringRenderer);
                if (UtilValidate.isNotEmpty(id) && (id.equals(conMainSection) || id.equals(colContainer))) {

                } else
                    screenStringRenderer.renderContainerEnd(writer, context, this);


                if(id.equals("left-column")||id.equals("cmsnav")){
                    writer.append("</div>");

                }else if(id.equals("centerdiv")||id.equals("cmsmain")){
                    writer.append("</div>");
                    writer.append("</div>");
                }else if(id.equals("split50")){
                    writer.append("<div>");
                }


            } catch (IOException e) {
                String errMsg = "Error rendering container in screen named [" + this.modelScreen.getName() + "]: " + e.toString();
                Debug.logError(e, errMsg, module);
                throw new RuntimeException(errMsg);
            }
        }


    }


    public static class Menu extends ModelScreenWidget.Menu {

        public Menu(AmazeModelScreen modelScreen, Element menuElement) {
            super(modelScreen, menuElement);
        }

        @Override
        public void renderWidgetString(Appendable writer, Map<String, Object> context, ScreenStringRenderer screenStringRenderer) throws IOException {
            // try finding the menuStringRenderer by name in the context in case one was prepared and put there
            MenuStringRenderer menuStringRenderer = (MenuStringRenderer) context.get("menuStringRenderer");
            // if there was no menuStringRenderer put in place, now try finding the request/response in the context and creating a new one
            if (menuStringRenderer == null) {
                HttpServletRequest request = (HttpServletRequest) context.get("request");
                HttpServletResponse response = (HttpServletResponse) context.get("response");
                if (request != null && response != null) {
                    menuStringRenderer = new AmazeHtmlMenuRenderer(request, response);
                }
            }
            // still null, throw an error
            if (menuStringRenderer == null) {
                throw new IllegalArgumentException("Could not find a menuStringRenderer in the context, and could not find HTTP request/response objects need to create one.");
            }

            ModelMenu modelMenu = getModelMenu(context);
            modelMenu.renderMenuString(writer, context, menuStringRenderer);
        }
    }


    public static class DecoratorSection extends ModelScreenWidget.DecoratorSection {

        public DecoratorSection(AmazeModelScreen modelScreen, Element decoratorSectionElement) {
            super(modelScreen, decoratorSectionElement);
            // read sub-widgets
            List<? extends Element> subElementList = UtilXml.childElementList(decoratorSectionElement);
            subWidgets = ModelScreenWidget.readSubWidgets(this.modelScreen, subElementList, extend);
        }

        @Override
        public void renderWidgetString(Appendable writer, Map<String, Object> context, ScreenStringRenderer screenStringRenderer) throws GeneralException, IOException {
            // render sub-widgets
            renderSubWidgetsString(this.subWidgets, writer, context, screenStringRenderer);
        }

        @Override
        public String rawString() {
            return "<decorator-section name=\"" + this.name + "\">";
        }


    }


    public static class Screenlet extends ModelScreenWidget.Screenlet {

        public Screenlet(AmazeModelScreen modelScreen, Element screenletElement) {
            super(modelScreen, screenletElement);
            List<? extends Element> subElementList = UtilXml.childElementList(screenletElement);
            this.subWidgets = ModelScreenWidget.readSubWidgets(this.modelScreen, subElementList, extend);
        }
    }


    public static class Section extends ModelScreenWidget.Section {

        public Section(AmazeModelScreen modelScreen, Element sectionElement) {
            super(modelScreen, sectionElement);


            // read sub-widgets
            Element widgetsElement = UtilXml.firstChildElement(sectionElement, "widgets");
            List<? extends Element> subElementList = UtilXml.childElementList(widgetsElement);
            subWidgets = ModelScreenWidget.readSubWidgets(this.modelScreen, subElementList, extend);

            // read fail-widgets
            Element failWidgetsElement = UtilXml.firstChildElement(sectionElement, "fail-widgets");
            if (failWidgetsElement != null) {
                List<? extends Element> failElementList = UtilXml.childElementList(failWidgetsElement);
                failWidgets = ModelScreenWidget.readSubWidgets(this.modelScreen, failElementList, extend);
            }
        }

    }


    public static class DecoratorScreen extends ModelScreenWidget.DecoratorScreen {

        public DecoratorScreen(AmazeModelScreen modelScreen, Element decoratorScreenElement) {
            super(modelScreen, decoratorScreenElement);
            List<? extends Element> decoratorSectionElementList = UtilXml.childElementList(decoratorScreenElement, "decorator-section");
            for (Element decoratorSectionElement : decoratorSectionElementList) {
                DecoratorSection decoratorSection = new AmazeModelScreenWidget.DecoratorSection(modelScreen, decoratorSectionElement);
                this.sectionMap.put(decoratorSection.getName(), decoratorSection);
            }
        }

        @Override
        @SuppressWarnings("unchecked")
        public void renderWidgetString(Appendable writer, Map<String, Object> context, ScreenStringRenderer screenStringRenderer) throws GeneralException, IOException {
            // isolate the scope
            if (!(context instanceof MapStack)) {
                context = MapStack.create(context);
            }

            MapStack contextMs = (MapStack) context;

            // create a standAloneStack, basically a "save point" for this SectionsRenderer, and make a new "screens" object just for it so it is isolated and doesn't follow the stack down
            MapStack standAloneStack = contextMs.standAloneChildStack();
            standAloneStack.put("screens", new ScreenRenderer(writer, standAloneStack, screenStringRenderer));
            AmazeModelScreenWidget.SectionsRenderer sections = new AmazeModelScreenWidget.SectionsRenderer(this.sectionMap, standAloneStack, writer, screenStringRenderer);

            // put the sectionMap in the context, make sure it is in the sub-scope, ie after calling push on the MapStack
            contextMs.push();
            context.put("sections", sections);

            String name = this.getName(context);
            String location = this.getLocation(context);

            ScreenFactory.renderReferencedScreen(name, location, this, writer, context, screenStringRenderer, "amaze");

            contextMs.pop();
        }


    }


    public static class IncludeScreen extends ModelScreenWidget.IncludeScreen {

        public IncludeScreen(AmazeModelScreen modelScreen, Element includeScreenElement) {
            super(modelScreen, includeScreenElement);
        }

        @Override
        public void renderWidgetString(Appendable writer, Map<String, Object> context, ScreenStringRenderer screenStringRenderer) throws GeneralException, IOException {
            // if we are not sharing the scope, protect it using the MapStack
            boolean protectScope = !shareScope(context);
            if (protectScope) {
                if (!(context instanceof MapStack<?>)) {
                    context = MapStack.create(context);
                }

                UtilGenerics.<MapStack<String>>cast(context).push();

                // build the widgetpath
                List<String> widgetTrail = UtilGenerics.toList(context.get("_WIDGETTRAIL_"));
                if (widgetTrail == null) {
                    widgetTrail = FastList.newInstance();
                }

                String thisName = nameExdr.expandString(context);
                widgetTrail.add(thisName);
                context.put("_WIDGETTRAIL_", widgetTrail);
            }

            // dont need the renderer here, will just pass this on down to another screen call; screenStringRenderer.renderContainerBegin(writer, context, this);
            String name = this.getName(context);
            String location = this.getLocation(context);

            if (UtilValidate.isEmpty(name)) {
                if (Debug.verboseOn()) Debug.logVerbose("In the include-screen tag the screen name was empty, ignoring include; in screen [" + this.modelScreen.getName() + "]", module);
                return;
            }

            ScreenFactory.renderReferencedScreen(name, location, this, writer, context, screenStringRenderer, "amaze");

            if (protectScope) {
                UtilGenerics.<MapStack<String>>cast(context).pop();
            }
        }

    }

    public static class HorizontalSeparator extends ModelScreenWidget.HorizontalSeparator {

        public HorizontalSeparator(AmazeModelScreen modelScreen, Element separatorElement) {
            super(modelScreen, separatorElement);
        }
    }

    public static class DecoratorSectionInclude extends ModelScreenWidget.DecoratorSectionInclude {

        public DecoratorSectionInclude(AmazeModelScreen modelScreen, Element decoratorSectionElement) {
            super(modelScreen, decoratorSectionElement);
        }

        @Override
        public void renderWidgetString(Appendable writer, Map<String, Object> context, ScreenStringRenderer screenStringRenderer) throws GeneralException, IOException {
            Map<String, ? extends Object> preRenderedContent = UtilGenerics.checkMap(context.get("preRenderedContent"));
            if (preRenderedContent != null && preRenderedContent.containsKey(this.name)) {
                try {
                    writer.append((String) preRenderedContent.get(this.name));
                } catch (IOException e) {
                    String errMsg = "Error rendering pre-rendered content in screen named [" + this.modelScreen.getName() + "]: " + e.toString();
                    Debug.logError(e, errMsg, module);
                    throw new RuntimeException(errMsg);
                }
            } else {
                AmazeModelScreenWidget.SectionsRenderer sections = (AmazeModelScreenWidget.SectionsRenderer) context.get("sections");
                // for now if sections is null, just log a warning; may be permissible to make the screen for flexible
                if (sections == null) {
                    Debug.logWarning("In decorator-section-include could not find sections object in the context, not rendering section with name [" + this.name + "]", module);
                } else {
                    sections.render(this.name);
                }
            }
        }
    }

    public static class Label extends ModelScreenWidget.Label {


        public Label(AmazeModelScreen modelScreen, Element labelElement) {
            super(modelScreen, labelElement);
        }
    }

    public static class Form extends ModelScreenWidget.Form {

        public Form(AmazeModelScreen modelScreen, Element formElement) {
            super(modelScreen, formElement);
        }
        @Override
        public void renderWidgetString(Appendable writer, Map<String, Object> context, ScreenStringRenderer screenStringRenderer) {
            boolean protectScope = !shareScope(context);
            if (protectScope) {
                if (!(context instanceof MapStack<?>)) {
                    context = MapStack.create(context);
                }
                UtilGenerics.<MapStack<String>>cast(context).push();
            }

            // try finding the formStringRenderer by name in the context in case one was prepared and put there
            FormStringRenderer formStringRenderer = (FormStringRenderer) context.get("formStringRenderer");
            // if there was no formStringRenderer put in place, now try finding the request/response in the context and creating a new one
            if (formStringRenderer == null) {
                HttpServletRequest request = (HttpServletRequest) context.get("request");
                HttpServletResponse response = (HttpServletResponse) context.get("response");
                if (request != null && response != null) {
                    formStringRenderer = new AmazeHtmlFormRenderer(request, response);
                }
            }
            // still null, throw an error
            if (formStringRenderer == null) {
                throw new IllegalArgumentException("Could not find a formStringRenderer in the context, and could not find HTTP request/response objects need to create one.");
            }

            ModelForm modelForm = getModelForm(context);
            //Debug.logInfo("before renderFormString, context:" + context, module);
            try {
                modelForm.renderFormString(writer, context, formStringRenderer);
            } catch (IOException e) {
                String errMsg = "Error rendering included form named [" + name + "] at location [" + this.getLocation(context) + "]: " + e.toString();
                Debug.logError(e, errMsg, module);
                throw new RuntimeException(errMsg + e);
            }

            if (protectScope) {
                UtilGenerics.<MapStack<String>>cast(context).pop();
            }
        }

        public ModelForm getModelForm(Map<String, Object> context) {
            return super.getModelForm(context);
        }
    }

    public static class Tree extends ModelScreenWidget.Tree {

        public Tree(AmazeModelScreen modelScreen, Element treeElement) {
            super(modelScreen, treeElement);
        }
    }

    public static class PlatformSpecific extends ModelScreenWidget.PlatformSpecific {


        public PlatformSpecific(AmazeModelScreen modelScreen, Element platformSpecificElement) {
            super(modelScreen, platformSpecificElement);

                subWidgets = new HashMap<String, ModelScreenWidget>();
                List<? extends Element> childElements = UtilXml.childElementList(platformSpecificElement);
                if (childElements != null) {
                    for (Element childElement : childElements) {
                        if ("html".equals(childElement.getNodeName())) {
                            subWidgets.put("html", new AmazeHtmlWidget(modelScreen, childElement));
                        } else if ("xsl-fo".equals(childElement.getNodeName())) {
                            subWidgets.put("xsl-fo", new AmazeHtmlWidget(modelScreen, childElement));
                        } else if ("xml".equals(childElement.getNodeName())) {
                            subWidgets.put("xml", new AmazeHtmlWidget(modelScreen, childElement));
                        } else {
                            throw new IllegalArgumentException("Tag not supported under the platform-specific tag with name: " + childElement.getNodeName());
                        }
                    }
                }

        }
    }

    public static class Content extends ModelScreenWidget.Content {


        public Content(AmazeModelScreen modelScreen, Element subContentElement) {
            super(modelScreen, subContentElement);
        }
    }

    public static class SubContent extends ModelScreenWidget.SubContent {

        public SubContent(AmazeModelScreen modelScreen, Element subContentElement) {
            super(modelScreen, subContentElement);
        }
    }

    public static class Link extends ModelScreenWidget.Link {

        public Link(AmazeModelScreen modelScreen, Element linkElement) {
            super(modelScreen, linkElement);
        }
    }


    public static class Image extends ModelScreenWidget.Image {

        public Image(AmazeModelScreen modelScreen, Element imageElement) {
            super(modelScreen, imageElement);
        }
    }

    public static class PortalPage extends ModelScreenWidget.PortalPage {

        public PortalPage(AmazeModelScreen modelScreen, Element portalPageElement) {
            super(modelScreen, portalPageElement);
        }
        @Override
        public void renderWidgetString(Appendable writer, Map<String, Object> context, ScreenStringRenderer screenStringRenderer) throws GeneralException, IOException {
            try {
                Delegator delegator = (Delegator) context.get("delegator");
                GenericValue portalPage = null;
                List<GenericValue> portalPageColumns = null;
                List<GenericValue> portalPagePortlets = null;
                List<GenericValue> portletAttributes = null;

                String expandedPortalPageId = getId(context);

                if (UtilValidate.isNotEmpty(expandedPortalPageId)) {
                    if (usePrivate) {
                        portalPage = PortalPageWorker.getPortalPage(expandedPortalPageId, context);
                    } else {
                        portalPage = delegator.findByPrimaryKeyCache("PortalPage", UtilMisc.toMap("portalPageId", expandedPortalPageId));
                    }
                    if (portalPage == null) {
                        String errMsg = "Could not find PortalPage with portalPageId [" + expandedPortalPageId + "] ";
                        Debug.logError(errMsg, module);
                        throw new RuntimeException(errMsg);
                    } else {
                        actualPortalPageId = portalPage.getString("portalPageId");
                        originalPortalPageId = portalPage.getString("originalPortalPageId");
                        portalPageColumns = delegator.findByAndCache("PortalPageColumn", UtilMisc.toMap("portalPageId", actualPortalPageId), UtilMisc.toList("columnSeqId"));
                    }
                } else {
                    String errMsg = "portalPageId is empty.";
                    Debug.logError(errMsg, module);
                    return;
                }

                // Renders the portalPage header
                screenStringRenderer.renderPortalPageBegin(writer, context, this);

                // First column has no previous column
                String prevColumnSeqId = "";

                // Iterates through the PortalPage columns
                ListIterator<GenericValue> columnsIterator = portalPageColumns.listIterator();
                int count = portalPageColumns.size();
                while (columnsIterator.hasNext()) {
                    GenericValue columnValue = columnsIterator.next();
                    String columnSeqId = columnValue.getString("columnSeqId");

                    // Renders the portalPageColumn header
                    screenStringRenderer.renderPortalPageColumnBegin(writer, context, this, columnValue,count);

                    // Get the Portlets located in the current column
                    portalPagePortlets = delegator.findByAnd("PortalPagePortletView", UtilMisc.toMap("portalPageId", portalPage.getString("portalPageId"), "columnSeqId", columnSeqId), UtilMisc.toList("sequenceNum"));

                    // First Portlet in a Column has no previous Portlet
                    String prevPortletId = "";
                    String prevPortletSeqId = "";

                    // If this is not the last column, get the next columnSeqId
                    String nextColumnSeqId = "";
                    if (columnsIterator.hasNext()) {
                        nextColumnSeqId = portalPageColumns.get(columnsIterator.nextIndex()).getString("columnSeqId");
                    }

                    // Iterates through the Portlets in the Column
                    ListIterator<GenericValue> portletsIterator = portalPagePortlets.listIterator();
                    while (portletsIterator.hasNext()) {
                        GenericValue portletValue = portletsIterator.next();

                        // If not the last portlet in the column, get the next nextPortletId and nextPortletSeqId
                        String nextPortletId = "";
                        String nextPortletSeqId = "";
                        if (portletsIterator.hasNext()) {
                            nextPortletId = portalPagePortlets.get(portletsIterator.nextIndex()).getString("portalPortletId");
                            nextPortletSeqId = portalPagePortlets.get(portletsIterator.nextIndex()).getString("portletSeqId");
                        }

                        // Set info to allow portlet movement in the page
                        context.put("prevPortletId", prevPortletId);
                        context.put("prevPortletSeqId", prevPortletSeqId);
                        context.put("nextPortletId", nextPortletId);
                        context.put("nextPortletSeqId", nextPortletSeqId);
                        context.put("prevColumnSeqId", prevColumnSeqId);
                        context.put("nextColumnSeqId", nextColumnSeqId);

                        // Get portlet's attributes
                        portletAttributes = delegator.findList("PortletAttribute",
                                EntityCondition.makeCondition(UtilMisc.toMap("portalPageId", portletValue.get("portalPageId"), "portalPortletId", portletValue.get("portalPortletId"), "portletSeqId", portletValue.get("portletSeqId"))),
                                null, null, null, false);
                        ListIterator<GenericValue> attributesIterator = portletAttributes.listIterator();
                        while (attributesIterator.hasNext()) {
                            GenericValue attribute = attributesIterator.next();
                            context.put(attribute.getString("attrName"), attribute.getString("attrValue"));
                        }

                        // Renders the portalPagePortlet
                        screenStringRenderer.renderPortalPagePortletBegin(writer, context, this, portletValue);
                        screenStringRenderer.renderPortalPagePortletBody(writer, context, this, portletValue);
                        screenStringRenderer.renderPortalPagePortletEnd(writer, context, this, portletValue);

                        // Remove the portlet's attributes so that these are not available for other portlets
                        while (attributesIterator.hasPrevious()) {
                            GenericValue attribute = attributesIterator.previous();
                            context.remove(attribute.getString("attrName"));
                        }

                        // Uses the actual portlet as prevPortlet for next iteration
                        prevPortletId = (String) portletValue.get("portalPortletId");
                        prevPortletSeqId = (String) portletValue.get("portletSeqId");
                    }
                    // Renders the portalPageColumn footer
                    screenStringRenderer.renderPortalPageColumnEnd(writer, context, this, columnValue);

                    // Uses the actual columnSeqId as prevColumnSeqId for next iteration
                    prevColumnSeqId = columnSeqId;
                }
                // Renders the portalPage footer
                screenStringRenderer.renderPortalPageEnd(writer, context, this);
            } catch (IOException e) {
                String errMsg = "Error rendering PortalPage with portalPageId [" + getId(context) + "]: " + e.toString();
                Debug.logError(e, errMsg, module);
                throw new RuntimeException(errMsg);
            } catch (GenericEntityException e) {
                String errMsg = "Error obtaining PortalPage with portalPageId [" + getId(context) + "]: " + e.toString();
                Debug.logError(e, errMsg, module);
                throw new RuntimeException(errMsg);
            }
        }
    }

}
