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

import javolution.util.FastMap;
import org.apache.commons.beanutils.ConstructorUtils;
import org.ofbiz.base.location.FlexibleLocation;
import org.ofbiz.base.util.*;
import org.ofbiz.base.util.cache.UtilCache;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.xml.sax.SAXException;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.xml.parsers.ParserConfigurationException;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.net.URL;
import java.util.List;
import java.util.Map;


/**
 * Widget Library - Screen factory class
 */
public class ScreenFactory {

    public static final String module = ScreenFactory.class.getName();

    public static final UtilCache<String, Map<String, ModelScreen>> screenLocationCache = UtilCache.createUtilCache("widget.screen.locationResource", 0, 0, false);
    public static final UtilCache<String, Map<String, ModelScreen>> screenWebappCache = UtilCache.createUtilCache("widget.screen.webappResource", 0, 0, false);

    public static boolean isCombinedName(String combinedName) {
        int numSignIndex = combinedName.lastIndexOf("#");
        if (numSignIndex == -1) {
            return false;
        }
        return numSignIndex + 1 < combinedName.length();
    }

    public static String getResourceNameFromCombined(String combinedName) {
        // split out the name on the last "#"
        int numSignIndex = combinedName.lastIndexOf("#");
        if (numSignIndex == -1) {
            throw new IllegalArgumentException("Error in screen location/name: no \"#\" found to separate the location from the name; correct example: component://product/screen/product/ProductScreens.xml#EditProduct");
        }
        if (numSignIndex + 1 >= combinedName.length()) {
            throw new IllegalArgumentException("Error in screen location/name: the \"#\" was at the end with no screen name after it; correct example: component://product/screen/product/ProductScreens.xml#EditProduct");
        }
        String resourceName = combinedName.substring(0, numSignIndex);
        return resourceName;
    }

    public static String getScreenNameFromCombined(String combinedName) {
        // split out the name on the last "#"
        int numSignIndex = combinedName.lastIndexOf("#");
        if (numSignIndex == -1) {
            throw new IllegalArgumentException("Error in screen location/name: no \"#\" found to separate the location from the name; correct example: component://product/screen/product/ProductScreens.xml#EditProduct");
        }
        if (numSignIndex + 1 >= combinedName.length()) {
            throw new IllegalArgumentException("Error in screen location/name: the \"#\" was at the end with no screen name after it; correct example: component://product/screen/product/ProductScreens.xml#EditProduct");
        }
        String screenName = combinedName.substring(numSignIndex + 1);
        return screenName;
    }

    public static ModelScreen getScreenFromLocation(String combinedName)
            throws IOException, SAXException, ParserConfigurationException {
        String resourceName = getResourceNameFromCombined(combinedName);
        String screenName = getScreenNameFromCombined(combinedName);
        return getScreenFromLocation(resourceName, screenName);
    }

    public static ModelScreen getScreenFromLocationExtend(String combinedName,String extend)
            throws IOException, SAXException, ParserConfigurationException {
        String resourceName = getResourceNameFromCombined(combinedName);
        String screenName = getScreenNameFromCombined(combinedName);
        return getScreenFromLocation(resourceName, screenName);
    }

    public static ModelScreen getScreenFromLocation(String resourceName, String screenName)
            throws IOException, SAXException, ParserConfigurationException {
        Map<String, ModelScreen> modelScreenMap = getScreensFromLocation(resourceName);
        ModelScreen modelScreen = modelScreenMap.get(screenName);
        if (modelScreen == null) {
            throw new IllegalArgumentException("Could not find screen with name [" + screenName + "] in class resource [" + resourceName + "]");
        }
        return modelScreen;
    }

    public static ModelScreen getScreenFromLocation(String resourceName, String screenName,String extend)
            throws IOException, SAXException, ParserConfigurationException {
        Map<String, ModelScreen> modelScreenMap = getScreensFromLocation(resourceName,extend);
        ModelScreen modelScreen = modelScreenMap.get(screenName);
        if (modelScreen == null) {
            throw new IllegalArgumentException("Could not find screen with name [" + screenName + "] in class resource [" + resourceName + "]");
        }
        return modelScreen;
    }

    public static Map<String, ModelScreen> getScreensFromLocation(String resourceName)
            throws IOException, SAXException, ParserConfigurationException {
        Map<String, ModelScreen> modelScreenMap = screenLocationCache.get(resourceName);
        if (modelScreenMap == null) {
            synchronized (ScreenFactory.class) {
                modelScreenMap = screenLocationCache.get(resourceName);
                if (modelScreenMap == null) {
                    long startTime = System.currentTimeMillis();
                    ClassLoader loader = Thread.currentThread().getContextClassLoader();
                    if (loader == null) {
                        loader = ScreenFactory.class.getClassLoader();
                    }

                    URL screenFileUrl = null;
                    screenFileUrl = FlexibleLocation.resolveLocation(resourceName, loader);
                    if (screenFileUrl == null) {
                        throw new IllegalArgumentException("Could not resolve location to URL: " + resourceName);
                    }
                    Document screenFileDoc = UtilXml.readXmlDocument(screenFileUrl, true, true);
                    modelScreenMap = readScreenDocument(screenFileDoc, resourceName);
                    screenLocationCache.put(resourceName, modelScreenMap);
                    double totalSeconds = (System.currentTimeMillis() - startTime)/1000.0;
                    Debug.logInfo("Got " + modelScreenMap.size() + " screens in " + totalSeconds + "s from: " + screenFileUrl.toExternalForm(), module);
                }
            }
        }

        if (modelScreenMap == null) {
            throw new IllegalArgumentException("Could not find screen file with name [" + resourceName + "]");
        }
        return modelScreenMap;
    }

    public static ModelScreen getScreenFromWebappContext(String resourceName, String screenName, HttpServletRequest request)
            throws IOException, SAXException, ParserConfigurationException {
        String webappName = UtilHttp.getApplicationName(request);
        String cacheKey = webappName + "::" + resourceName;


        Map<String, ModelScreen> modelScreenMap = screenWebappCache.get(cacheKey);
        if (modelScreenMap == null) {
            synchronized (ScreenFactory.class) {
                modelScreenMap = screenWebappCache.get(cacheKey);
                if (modelScreenMap == null) {
                    ServletContext servletContext = (ServletContext) request.getAttribute("servletContext");

                    URL screenFileUrl = servletContext.getResource(resourceName);
                    Document screenFileDoc = UtilXml.readXmlDocument(screenFileUrl, true, true);
                    String themeId = (String) request.getAttribute("VISUAL_THEME");
                    modelScreenMap = readScreenDocument(screenFileDoc, resourceName,themeId);
                    screenWebappCache.put(cacheKey, modelScreenMap);
                }
            }
        }

        ModelScreen modelScreen = modelScreenMap.get(screenName);
        if (modelScreen == null) {
            throw new IllegalArgumentException("Could not find screen with name [" + screenName + "] in webapp resource [" + resourceName + "] in the webapp [" + webappName + "]");
        }
        return modelScreen;
    }

    public static Map<String, ModelScreen> readScreenDocument(Document screenFileDoc, String sourceLocation) {
        Map<String, ModelScreen> modelScreenMap = FastMap.newInstance();
        if (screenFileDoc != null) {
            // read document and construct ModelScreen for each screen element
            Element rootElement = screenFileDoc.getDocumentElement();
            List<? extends Element> screenElements = UtilXml.childElementList(rootElement, "screen");
            for (Element screenElement: screenElements) {
                ModelScreen modelScreen = new ModelScreen(screenElement, modelScreenMap, sourceLocation);
                //Debug.logInfo("Read Screen with name: " + modelScreen.getName(), module);
                modelScreenMap.put(modelScreen.getName(), modelScreen);
            }
        }
        return modelScreenMap;
    }

    /**
     * 扩展amaze情况，需要根据
     * @param screenFileDoc
     * @param sourceLocation
     * @return
     */
    public static Map<String, ModelScreen> readScreenDocument(Document screenFileDoc, String sourceLocation,String extend) {
        Map<String, ModelScreen> modelScreenMap = FastMap.newInstance();
        if (screenFileDoc != null) {
            // read document and construct ModelScreen for each screen element
            Element rootElement = screenFileDoc.getDocumentElement();
            List<? extends Element> screenElements = UtilXml.childElementList(rootElement, "screen");
            for (Element screenElement: screenElements) {

                ModelScreen modelScreen = null;
                try {
                    String className = UtilStrings.firstUpperCase(extend);
                    String fullClassName = "org.ofbiz."+extend+".widget.screen."+className+"ModelScreen";
//                    modelScreen = (ModelScreen) ObjectType.getInstance(fullClassName, new Object[]{(Element)screenElement, (Map)modelScreenMap, sourceLocation});
                    modelScreen = (ModelScreen) ConstructorUtils.invokeConstructor(ObjectType.loadClass(fullClassName),new Object[]{screenElement,modelScreenMap, sourceLocation},new Class[]{Element.class,Map.class,String.class});
                } catch (ClassNotFoundException e) {
                    String errMsg = "Error create modelScreen object named [" + extend + "ModelScreen" + "] at method readScreenDocument " + e.toString();
                    Debug.logError(e, errMsg, module);
                } catch (InstantiationException e) {
                    String errMsg = "Error create modelScreen object named [" + extend + "ModelScreen" + "] at method readScreenDocument " + e.toString();
                    Debug.logError(e, errMsg, module);
                } catch (IllegalAccessException e) {
                    String errMsg = "Error create modelScreen object named [" + extend + "ModelScreen" + "] at method readScreenDocument " + e.toString();
                    Debug.logError(e, errMsg, module);
                } catch (NoSuchMethodException e) {
                    String errMsg = "Error create modelScreen object named [" + extend + "ModelScreen" + "] at method readScreenDocument " + e.toString();
                    Debug.logError(e, errMsg, module);
                } catch (InvocationTargetException e) {
                    String errMsg = "Error create modelScreen object named [" + extend + "ModelScreen" + "] at method readScreenDocument " + e.toString();
                    Debug.logError(e, errMsg, module);
                }
                modelScreenMap.put(modelScreen.getName(), modelScreen);

                //Debug.logInfo("Read Screen with name: " + modelScreen.getName(), module);

            }
        }
        return modelScreenMap;
    }

    public static void renderReferencedScreen(String name, String location, ModelScreenWidget parentWidget, Appendable writer, Map<String, Object> context, ScreenStringRenderer screenStringRenderer) throws GeneralException, IOException {
        // check to see if the name is a composite name separated by a #, if so split it up and get it by the full loc#name
        if (ScreenFactory.isCombinedName(name)) {
            String combinedName = name;
            location = ScreenFactory.getResourceNameFromCombined(combinedName);
            name = ScreenFactory.getScreenNameFromCombined(combinedName);
        }

        ModelScreen modelScreen = null;
        if (UtilValidate.isNotEmpty(location)) {
            try {
                modelScreen = ScreenFactory.getScreenFromLocation(location, name);
            } catch (IOException e) {
                String errMsg = "Error rendering included screen named [" + name + "] at location [" + location + "]: " + e.toString();
                Debug.logError(e, errMsg, module);
                throw new RuntimeException(errMsg);
            } catch (SAXException e) {
                String errMsg = "Error rendering included screen named [" + name + "] at location [" + location + "]: " + e.toString();
                Debug.logError(e, errMsg, module);
                throw new RuntimeException(errMsg);
            } catch (ParserConfigurationException e) {
                String errMsg = "Error rendering included screen named [" + name + "] at location [" + location + "]: " + e.toString();
                Debug.logError(e, errMsg, module);
                throw new RuntimeException(errMsg);
            }
        } else {
            modelScreen = parentWidget.getModelScreen().modelScreenMap.get(name);
            if (modelScreen == null) {
                throw new IllegalArgumentException("Could not find screen with name [" + name + "] in the same file as the screen with name [" + parentWidget.getModelScreen().getName() + "]");
            }
        }
        //Debug.logInfo("parent(" + parentWidget + ") rendering(" + modelScreen + ")", module);
        modelScreen.renderScreenString(writer, context, screenStringRenderer);
    }


    public static void renderReferencedScreen(String name, String location, ModelScreenWidget parentWidget, Appendable writer, Map<String, Object> context, ScreenStringRenderer screenStringRenderer,String extend) throws GeneralException, IOException {
        // check to see if the name is a composite name separated by a #, if so split it up and get it by the full loc#name
        if (ScreenFactory.isCombinedName(name)) {
            String combinedName = name;
            location = ScreenFactory.getResourceNameFromCombined(combinedName);
            name = ScreenFactory.getScreenNameFromCombined(combinedName);
        }

        ModelScreen modelScreen = null;
        if (UtilValidate.isNotEmpty(location)) {
            try {
                modelScreen = ScreenFactory.getScreenFromLocation(location, name, extend);
            } catch (IOException e) {
                String errMsg = "Error rendering included screen named [" + name + "] at location [" + location + "]: " + e.toString();
                Debug.logError(e, errMsg, module);
                throw new RuntimeException(errMsg);
            } catch (SAXException e) {
                String errMsg = "Error rendering included screen named [" + name + "] at location [" + location + "]: " + e.toString();
                Debug.logError(e, errMsg, module);
                throw new RuntimeException(errMsg);
            } catch (ParserConfigurationException e) {
                String errMsg = "Error rendering included screen named [" + name + "] at location [" + location + "]: " + e.toString();
                Debug.logError(e, errMsg, module);
                throw new RuntimeException(errMsg);
            }
        } else {
            modelScreen = parentWidget.getModelScreen().modelScreenMap.get(name);
            if (modelScreen == null) {
                throw new IllegalArgumentException("Could not find screen with name [" + name + "] in the same file as the screen with name [" + parentWidget.getModelScreen().getName() + "]");
            }
        }
        //Debug.logInfo("parent(" + parentWidget + ") rendering(" + modelScreen + ")", module);
        modelScreen.renderScreenString(writer, context, screenStringRenderer);
    }

    /**
     * cache 保存的是extend.resourceName
     * @param resourceName
     * @param extend
     * @return
     * @throws IOException
     * @throws SAXException
     * @throws ParserConfigurationException
     */
    public static Map<String, ModelScreen> getScreensFromLocation(String resourceName,String extend)
            throws IOException, SAXException, ParserConfigurationException {
        String cacheResourceName = extend+"."+resourceName;
        Map<String, ModelScreen> modelScreenMap = screenLocationCache.get(cacheResourceName);
        if (modelScreenMap == null) {
            synchronized (ScreenFactory.class) {
                modelScreenMap = screenLocationCache.get(cacheResourceName);
                if (modelScreenMap == null) {
                    long startTime = System.currentTimeMillis();
                    ClassLoader loader = Thread.currentThread().getContextClassLoader();
                    if (loader == null) {
                        loader = ScreenFactory.class.getClassLoader();
                    }

                    URL screenFileUrl = null;
                    screenFileUrl = FlexibleLocation.resolveLocation(resourceName, loader);
                    if (screenFileUrl == null) {
                        throw new IllegalArgumentException("Could not resolve location to URL: " + resourceName);
                    }
                    Document screenFileDoc = UtilXml.readXmlDocument(screenFileUrl, true, true);
                    modelScreenMap = readScreenDocument(screenFileDoc, resourceName,extend);
                    screenLocationCache.put(cacheResourceName, modelScreenMap);
                    double totalSeconds = (System.currentTimeMillis() - startTime)/1000.0;
                    Debug.logInfo("Got " + modelScreenMap.size() + " screens in " + totalSeconds + "s from: " + screenFileUrl.toExternalForm(), module);
                }
            }
        }
        if (modelScreenMap == null) {
            throw new IllegalArgumentException("Could not find screen file with name [" + resourceName + "]");
        }
        return modelScreenMap;
    }
}
