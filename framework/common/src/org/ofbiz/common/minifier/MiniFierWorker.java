package org.ofbiz.common.minifier;

import javolution.util.FastSet;
import org.apache.commons.beanutils.ConstructorUtils;
import org.ofbiz.base.util.*;
import org.ofbiz.base.util.cache.UtilCache;
import org.ofbiz.entity.GenericValue;
import org.ofbiz.service.LocalDispatcher;
import org.ofbiz.widget.screen.MacroScreenRenderer;
import org.ofbiz.widget.screen.ScreenRenderer;
import org.ofbiz.widget.screen.ScreenStringRenderer;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;
import java.util.Map;
import java.util.Set;


/**
 * Created by changsy on 16/4/3.
 */
public class MiniFierWorker {

    public final static String module = MiniFierWorker.class.getName();
    public static String lastModified = "";

    public static void minifierJs(HttpServletRequest request, HttpServletResponse response) throws Exception {
        LocalDispatcher dispatcher = (LocalDispatcher) request.getAttribute("dispatcher");
        GenericValue userLogin = (GenericValue) request.getSession().getAttribute("userLogin");
        Map<String, Object> result = dispatcher.runSync("getUserPreferenceGroup", UtilMisc.toMap("userLogin", userLogin, "userPrefGroupTypeId", "GLOBAL_PREFERENCES"));
        Map userPreMap = (Map) result.get("userPrefMap");
        String themeId = (String) userPreMap.get("VISUAL_THEME");

/*        MacroScreenViewHandler viewHandler = new MacroScreenViewHandler();
        viewHandler.setName("screen");
        viewHandler.init(request.getServletContext());
        viewHandler.setExtend(themeId.toLowerCase());
        viewHandler.render("view","component://common/widget/CommonScreens.xml#GlobalActions","","text/html","UTF-8",request,response);*/


        //此处要设置如果是amaze的情况的MacroScreenRender为AmazeMacroScreenRender
        ScreenStringRenderer screenStringRenderer = null;
        String extend = themeId.toLowerCase();
        String name = "screen";
        if (UtilValidate.isNotEmpty(extend)) {
            String screeName = UtilProperties.getPropertyValue("widget", extend + "." + name + ".name");
            String macroPath = UtilProperties.getPropertyValue("widget", extend + "." + name + ".screenrenderer");
            String className = UtilStrings.firstUpperCase(extend);
            String fullClassName = "org.ofbiz." + extend + ".widget.screen." + className + "MacroScreenRenderer";
            screenStringRenderer = (ScreenStringRenderer) ConstructorUtils.invokeConstructor(ObjectType.loadClass(fullClassName), new Object[]{screeName, macroPath}, new Class[]{String.class, String.class});
        } else {
            screenStringRenderer = new MacroScreenRenderer(UtilProperties.getPropertyValue("widget", name + ".name"), UtilProperties.getPropertyValue("widget", name + ".screenrenderer"));

        }

        ScreenRenderer screens = new ScreenRenderer(response.getWriter(), null, screenStringRenderer, extend);
        screens.populateContextForRequest(request, response, request.getServletContext());
        screens.getContext().put("simpleEncoder", StringUtil.getEncoder(UtilProperties.getPropertyValue("widget", name + ".encoder")));
        screens.render("component://common/widget/CommonScreens.xml#GlobalActions");
        Map layoutSetting = (Map) screens.getContext().get("themeResources");
        String minifiedContent = getBundleContent(layoutSetting,response);
        try {

            response.setHeader("Content-Type","text/javascript;charset=utf-8");
            response.setContentType("text/javascript");

                PrintWriter out = response.getWriter();
                out.println(minifiedContent);
                out.close();

        } catch (IOException e) {
            Debug.logError(e, "Problems writing servlet output!", module);
        }


    }

    protected static String getBundleContent(Map layoutSetting,HttpServletResponse response)
            throws IOException {


        String cacheFileName = "barebone.js";
        String content = (String) UtilCache.getOrCreateUtilCache("cache-page", 0, 0, 0, true, false, cacheFileName).get(cacheFileName);
        if (content != null) {
//            response.setHeader("Last-Modified",lastModified);
//            response.setStatus(403);
            return content;
        } else {
            Set<String> javaScriptSet = FastSet.newInstance();
            List<String> fileListNames = (List<String>) layoutSetting.get("javaScripts");
            if (UtilValidate.isNotEmpty(fileListNames)) {
                for (int i = 0; i < fileListNames.size(); i++) {
                    String s = fileListNames.get(i);
                    javaScriptSet.add(s);
                }
            }
            List hdrLists = (List) layoutSetting.get("VT_HDR_JAVASCRIPT");
            if (UtilValidate.isNotEmpty(hdrLists)) {
                for (int i = 0; i < hdrLists.size(); i++) {
                    String hdrStr = (String) hdrLists.get(i);
                    javaScriptSet.add(hdrStr);
                }
            }


            String[] fileNames = javaScriptSet.toArray(new String[javaScriptSet.size()]);

            if (fileNames.length == 0) {
                content = "";
            } else {
                content = aggregateJavaScript(fileNames);
            }

            response.setHeader("max-age","0");
            lastModified = new Long(System.currentTimeMillis()).toString();
            response.setHeader("Last-Modified",lastModified);
            UtilCache.findCache("cache-page").put(cacheFileName, content);
            return content;
        }
    }

    public static String aggregateJavaScript(String[] fileNames) throws IOException {

        StringBuilder sb = new StringBuilder(fileNames.length * 2);

        for (String fileName : fileNames) {
            String homePath = System.getProperty("ofbiz.home");
            String content = FileUtil.readString("UTF-8", new File(homePath + File.separator + "framework/images/webapp" + fileName));
            if (UtilValidate.isEmpty(content)) {
                continue;
            }

            sb.append(content);
            sb.append("\r\n");
        }

        return getJavaScriptContent(merge(fileNames, "+"), sb.toString());
    }
    protected static String getJavaScriptContent(
            String resourceName, String content) {

        return MinifierUtil.minifyJavaScript(resourceName,content);
    }
    public static String merge(Object[] array, String delimiter) {
        if (array == null) {
            return null;
        }

        if (array.length == 0) {
            return "";
        }

        StringBuilder sb = new StringBuilder(2 * array.length - 1);

        for (int i = 0; i < array.length; i++) {
            if (i != 0) {
                sb.append(delimiter);
            }

            sb.append(String.valueOf(array[i]).trim());
        }

        return sb.toString();
    }

    public static void minifierCss(HttpServletRequest request, HttpServletResponse response) throws Exception {
        LocalDispatcher dispatcher = (LocalDispatcher) request.getAttribute("dispatcher");
        GenericValue userLogin = (GenericValue) request.getSession().getAttribute("userLogin");
        Map<String, Object> result = dispatcher.runSync("getUserPreferenceGroup", UtilMisc.toMap("userLogin", userLogin, "userPrefGroupTypeId", "GLOBAL_PREFERENCES"));
        Map userPreMap = (Map) result.get("userPrefMap");
        String themeId = (String) userPreMap.get("VISUAL_THEME");
        //此处要设置如果是amaze的情况的MacroScreenRender为AmazeMacroScreenRender
        ScreenStringRenderer screenStringRenderer = null;
        String extend = themeId.toLowerCase();
        String name = "screen";
        if (UtilValidate.isNotEmpty(extend)) {
            String screeName = UtilProperties.getPropertyValue("widget", extend + "." + name + ".name");
            String macroPath = UtilProperties.getPropertyValue("widget", extend + "." + name + ".screenrenderer");
            String className = UtilStrings.firstUpperCase(extend);
            String fullClassName = "org.ofbiz." + extend + ".widget.screen." + className + "MacroScreenRenderer";
            screenStringRenderer = (ScreenStringRenderer) ConstructorUtils.invokeConstructor(ObjectType.loadClass(fullClassName), new Object[]{screeName, macroPath}, new Class[]{String.class, String.class});
        } else {
            screenStringRenderer = new MacroScreenRenderer(UtilProperties.getPropertyValue("widget", name + ".name"), UtilProperties.getPropertyValue("widget", name + ".screenrenderer"));

        }

        ScreenRenderer screens = new ScreenRenderer(response.getWriter(), null, screenStringRenderer, extend);
        screens.populateContextForRequest(request, response, request.getServletContext());
        screens.getContext().put("simpleEncoder", StringUtil.getEncoder(UtilProperties.getPropertyValue("widget", name + ".encoder")));
        screens.render("component://common/widget/CommonScreens.xml#GlobalActions");
        Map layoutSetting = (Map) screens.getContext().get("themeResources");
        String minifiedContent = getCssContent(layoutSetting);
        response.setContentType("text/css");
        response.setHeader("Content-Type","text/css;charset=utf-8");
        try {
            PrintWriter out = response.getWriter();
            out.println(minifiedContent);
            out.close();
        } catch (IOException e) {
            Debug.logError(e, "Problems writing servlet output!", module);
        }
    }


    protected static String getCssContent(Map layoutSetting)
            throws IOException {
        String cacheFileName = "barebone.css";
        String content = (String) UtilCache.getOrCreateUtilCache("cache-page", 0, 0, 0, true, false, cacheFileName).get(cacheFileName);
        if (content != null) {
            return content.toString();
        } else {
            Set<String> cssSet = FastSet.newInstance();

            List<String> vtNames = (List<String>) layoutSetting.get("VT_STYLESHEET");
            if (UtilValidate.isNotEmpty(vtNames)) {
                for (int i = 0; i < vtNames.size(); i++) {
                    String s = vtNames.get(i);
                    cssSet.add(s);
                }
            }
            List<String> fileListNames = (List<String>) layoutSetting.get("styleSheets");
            if (UtilValidate.isNotEmpty(fileListNames)) {
                for (int i = 0; i < fileListNames.size(); i++) {
                    String s = fileListNames.get(i);
                    cssSet.add(s);
                }
            }

            String[] fileNames = cssSet.toArray(new String[cssSet.size()]);

            if (fileNames.length == 0) {
                content = "";
            } else {
                content = aggregateCss(fileNames);
            }

            UtilCache.findCache("cache-page").put(cacheFileName, content);

            return content;
        }
    }


    public static String aggregateCss(String[] fileNames) throws IOException {

        StringBuilder sb = new StringBuilder(fileNames.length * 2);

        for (String fileName : fileNames) {
            String homePath = System.getProperty("ofbiz.home");
            String content = FileUtil.readString("UTF-8", new File(homePath + File.separator + "framework/images/webapp" + fileName));
            if (UtilValidate.isEmpty(content)) {
                continue;
            }

            sb.append(content);
            sb.append("\r\n");
        }

        return getCssContent(merge(fileNames, "+"), sb.toString());
    }

    protected static String getCssContent(
            String resourceName, String content) {
        return MinifierUtil.minifyCss(content);
    }



    public static String getCacheFileName(HttpServletRequest request) {
        StringBuffer cacheKeyGenerator = new StringBuffer();
        cacheKeyGenerator.append(request.isSecure() ? "https://" : "http://");
        cacheKeyGenerator.append(request.getRequestURI());
        String queryString = request.getQueryString();

        if (queryString != null) {
            cacheKeyGenerator.append(sterilizeQueryString(queryString));
        }
        return cacheKeyGenerator.toString();
    }

    protected static String sterilizeQueryString(String queryString) {
        String s = queryString.replace('/', '_');
        return s.replace('\\', '_');
    }


}
