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
package org.ofbiz.widget.screen;

import freemarker.template.TemplateException;
import freemarker.template.utility.StandardCompress;
import org.apache.commons.beanutils.ConstructorUtils;
import org.ofbiz.base.util.*;
import org.ofbiz.webapp.view.AbstractViewHandler;
import org.ofbiz.webapp.view.ViewHandlerException;
import org.ofbiz.widget.form.FormStringRenderer;
import org.ofbiz.widget.form.MacroFormRenderer;
import org.ofbiz.widget.html.HtmlMenuRenderer;
import org.ofbiz.widget.menu.MenuStringRenderer;
import org.ofbiz.widget.tree.MacroTreeRenderer;
import org.ofbiz.widget.tree.TreeStringRenderer;
import org.xml.sax.SAXException;

import javax.servlet.ServletContext;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.xml.parsers.ParserConfigurationException;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.lang.reflect.InvocationTargetException;

public class MacroScreenViewHandler extends AbstractViewHandler {

    public static final String module = MacroScreenViewHandler.class.getName();

    protected ServletContext servletContext = null;

    public void init(ServletContext context) throws ViewHandlerException {
        this.servletContext = context;
    }

    public void render(String name, String page, String info, String contentType, String encoding, HttpServletRequest request, HttpServletResponse response) throws ViewHandlerException {
        Writer writer = null;
        try {
            // use UtilJ2eeCompat to get this setup properly
            boolean useOutputStreamNotWriter = false;
            if (this.servletContext != null) {
                useOutputStreamNotWriter = UtilJ2eeCompat.useOutputStreamNotWriter(this.servletContext);
            }
            if (useOutputStreamNotWriter) {
                ServletOutputStream ros = response.getOutputStream();
                String defaultEncoding = UtilProperties.getPropertyValue("widget", getName() + ".default.encoding","UTF-8");
                writer = new OutputStreamWriter(ros, defaultEncoding);
            } else {
                writer = response.getWriter();
            }

            // compress output if configured to do so
            if (UtilValidate.isEmpty(encoding)) {
                encoding = UtilProperties.getPropertyValue("widget", getName() + ".default.encoding", "none");
            }
            boolean compressOutput = "compressed".equals(encoding);
            if (!compressOutput) {
                compressOutput = "true".equals(UtilProperties.getPropertyValue("widget", getName() + ".compress"));
            }
            if (!compressOutput && this.servletContext != null) {
                compressOutput = "true".equals(this.servletContext.getAttribute("compressHTML"));
            }
            if (compressOutput) {
                // StandardCompress defaults to a 2k buffer. That could be increased
                // to speed up output.
                writer = new StandardCompress().getWriter(writer, null);
            }
//          此处要设置如果是amaze的情况的MacroScreenRender为AmazeMacroScreenRender
            ScreenStringRenderer screenStringRenderer = null;
            FormStringRenderer formStringRenderer = null;
            TreeStringRenderer treeStringRenderer = null;
            MenuStringRenderer menuStringRenderer = null;
            //过滤screencsv，
            if(UtilValidate.isNotEmpty(getExtend()) && contentType.equals("text/html")) {
                String screeName = UtilProperties.getPropertyValue("widget", getExtend() + "." + getName() + ".name");
                String macroPath = UtilProperties.getPropertyValue("widget", getExtend() + "." +getName() + ".screenrenderer");
                String className = UtilStrings.firstUpperCase(extend);
                String fullClassName =  "org.ofbiz."+extend+".widget.screen."+className+"MacroScreenRenderer";
                screenStringRenderer = (ScreenStringRenderer) ConstructorUtils.invokeConstructor(ObjectType.loadClass(fullClassName),new Object[]{screeName,macroPath},new Class[]{String.class,String.class});

                screeName = UtilProperties.getPropertyValue("widget", getExtend() + "." +getName() + ".formrenderer");
                fullClassName =  "org.ofbiz."+extend+".widget.form."+className+"MacroFormRenderer";
                formStringRenderer = (FormStringRenderer) ConstructorUtils.invokeConstructor(ObjectType.loadClass(fullClassName),new Object[]{screeName,request,response},new Class[]{String.class,HttpServletRequest.class,HttpServletResponse.class});

                screeName = UtilProperties.getPropertyValue("widget", getExtend() + "." +getName()+".treerenderer");
                fullClassName =  "org.ofbiz."+extend+".widget.tree."+className+"MacroTreeRenderer";
                treeStringRenderer = (TreeStringRenderer) ConstructorUtils.invokeConstructor(ObjectType.loadClass(fullClassName),new Object[]{screeName,writer},new Class[]{String.class,Appendable.class});


                fullClassName =  "org.ofbiz."+extend+".widget.html."+className+"HtmlMenuRenderer";
                menuStringRenderer = (HtmlMenuRenderer)ConstructorUtils.invokeConstructor(ObjectType.loadClass(fullClassName), new Object[]{request,response}, new Class[]{request.getClass(),response.getClass()});
            }
            else {
                screenStringRenderer = new MacroScreenRenderer(UtilProperties.getPropertyValue("widget", getName() + ".name"), UtilProperties.getPropertyValue("widget", getName() + ".screenrenderer"));
                formStringRenderer = new MacroFormRenderer(UtilProperties.getPropertyValue("widget", getName() + ".formrenderer"), request, response);
                treeStringRenderer = new MacroTreeRenderer(UtilProperties.getPropertyValue("widget", getName() + ".treerenderer"), writer);
                // TODO: uncomment these lines when the renderers are implemented
                 menuStringRenderer = new HtmlMenuRenderer(request,response);
            }

            ScreenRenderer screens = new ScreenRenderer(writer, null, screenStringRenderer,getExtend());

            screens.populateContextForRequest(request, response, servletContext);
            // this is the object used to render forms from their definitions
            screens.getContext().put("formStringRenderer", formStringRenderer);
            screens.getContext().put("treeStringRenderer", treeStringRenderer);
            screens.getContext().put("menuStringRenderer", menuStringRenderer);
            //update by changsy 2015.6.22
            screens.getContext().put("simpleEncoder", StringUtil.getEncoder(UtilProperties.getPropertyValue("widget", getName() + ".encoder")));
            screenStringRenderer.renderScreenBegin(writer, screens.getContext());
            screens.render(page);
            screenStringRenderer.renderScreenEnd(writer, screens.getContext());
            writer.flush();
        } catch (TemplateException e) {
            Debug.logError(e, "Error initializing screen renderer", module);
            throw new ViewHandlerException(e.getMessage());
        } catch (IOException e) {
            throw new ViewHandlerException("Error in the response writer/output stream: " + e.toString(), e);
        } catch (SAXException e) {
            throw new ViewHandlerException("XML Error rendering page: " + e.toString(), e);
        } catch (ParserConfigurationException e) {
            throw new ViewHandlerException("XML Error rendering page: " + e.toString(), e);
        } catch (GeneralException e) {
            throw new ViewHandlerException("Lower level error rendering page: " + e.toString(), e);
        } catch (InvocationTargetException e) {
            throw new ViewHandlerException("InvocationTargetException error: " + e.toString(), e);
        } catch (NoSuchMethodException e) {
            throw new ViewHandlerException("NoSuchMethodException error: " + e.toString(), e);
        } catch (ClassNotFoundException e) {
            throw new ViewHandlerException("ClassNotFoundException error: " + e.toString(), e);
        } catch (InstantiationException e) {
            throw new ViewHandlerException("InstantiationException error: " + e.toString(), e);
        } catch (IllegalAccessException e) {
            throw new ViewHandlerException("IllegalAccessException error: " + e.toString(), e);
        }
    }
}
