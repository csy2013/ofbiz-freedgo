
package org.ofbiz.boots.widget.screen;

import freemarker.template.TemplateException;
import javolution.util.FastMap;
import org.ofbiz.base.util.*;
import org.ofbiz.entity.GenericValue;
import org.ofbiz.widget.WidgetWorker;
import org.ofbiz.widget.screen.MacroScreenRenderer;
import org.ofbiz.widget.screen.ModelScreenWidget;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.StringWriter;
import java.math.BigDecimal;
import java.util.Map;

public class BootsMacroScreenRenderer extends MacroScreenRenderer {

    public static final String module = org.ofbiz.boots.widget.screen.BootsMacroScreenRenderer.class.getName();

    public BootsMacroScreenRenderer(String name, String macroLibraryPath) throws TemplateException, IOException {
       super(name,macroLibraryPath);
    }
    public void renderLink(Appendable writer, Map<String, Object> context, ModelScreenWidget.Link link) throws IOException {
        HttpServletResponse response = (HttpServletResponse) context.get("response");
        HttpServletRequest request = (HttpServletRequest) context.get("request");

        String targetWindow = link.getTargetWindow(context);
        String target = link.getTarget(context);

        String uniqueItemName = link.getModelScreen().getName() + "_LF_" + UtilMisc.addToBigDecimalInMap(context, "screenUniqueItemIndex", BigDecimal.ONE);

        String linkType = WidgetWorker.determineAutoLinkType(link.getLinkType(), target, link.getUrlMode(), request);
        String linkUrl = "";
        String actionUrl = "";
        StringBuilder parameters=new StringBuilder();
        String width = link.getWidth();
        if (UtilValidate.isEmpty(width)) {
            width = "300";
        }
        String height = link.getHeight();
        if (UtilValidate.isEmpty(height)) {
            height = "200";
        }
        if ("hidden-form".equals(linkType) || "ajax-window".equals(linkType)) {
            StringBuilder sb = new StringBuilder();
            WidgetWorker.buildHyperlinkUrl(sb, target, link.getUrlMode(), null, link.getPrefix(context),
                    link.getFullPath(), link.getSecure(), link.getEncode(), request, response, context);
            actionUrl = sb.toString();
            parameters.append("[");
            for (Map.Entry<String, String> parameter: link.getParameterMap(context).entrySet()) {
                if (parameters.length() >1) {
                    parameters.append(",");
                }
                parameters.append("{'name':'");
                parameters.append(parameter.getKey());
                parameters.append("'");
                parameters.append(",'value':'");
                parameters.append(parameter.getValue());
                parameters.append("'}");
            }
            parameters.append("]");

        }
        String id = link.getId(context);
        String style = link.getStyle(context);
        String name = link.getName(context);
        String text = link.getText(context);
        if (UtilValidate.isNotEmpty(target)) {
            if (!"hidden-form".equals(linkType)) {
                StringBuilder sb = new StringBuilder();
                WidgetWorker.buildHyperlinkUrl(sb, target, link.getUrlMode(), link.getParameterMap(context), link.getPrefix(context),
                        link.getFullPath(), link.getSecure(), link.getEncode(), request, response, context);
                linkUrl = sb.toString();
            }
        }
        String imgStr = "";
        ModelScreenWidget.Image img = link.getImage();
        if (img != null) {
            StringWriter sw = new StringWriter();
            renderImage(sw, context, img);
            imgStr = sw.toString();
        }
        StringWriter sr = new StringWriter();
        sr.append("<@renderLink ");
        sr.append("parameterList=");
        sr.append(parameters.length()==0?"\"\"":parameters.toString());
        sr.append(" targetWindow=\"");
        sr.append(targetWindow);
        sr.append("\" target=\"");
        sr.append(target);
        sr.append("\" uniqueItemName=\"");
        sr.append(uniqueItemName);
        sr.append("\" linkType=\"");
        sr.append(linkType);
        sr.append("\" actionUrl=\"");
        sr.append(actionUrl);
        sr.append("\" id=\"");
        sr.append(id);
        sr.append("\" style=\"");
        sr.append(style);
        sr.append("\" name=\"");
        sr.append(name);
        sr.append("\" width=\"");
        sr.append(width);
        sr.append("\" height=\"");
        sr.append(height);
        sr.append("\" linkUrl=\"");
        sr.append(linkUrl);
        sr.append("\" text=\"");
        sr.append(text);
        sr.append("\" imgStr=\"");
        sr.append(imgStr.replaceAll("\"", "\\\\\""));
        if(context.get("isEnd")!=null&&(Boolean)context.get("isEnd")){
            sr.append("\" isEnd=");
            sr.append("true");
        }else{
            sr.append("\" isEnd=");
            sr.append("false");
        }
        if(context.get("isFirst")!=null&&(Boolean)context.get("isFirst")){
            sr.append(" isFirst=");
            sr.append("true");
        }else{
            sr.append(" isFirst=");
            sr.append("false");
        }
        sr.append(" />");
        executeMacro(writer, sr.toString());
    }

    public void renderPortalPageColumnBegin(Appendable writer, Map<String, Object> context, ModelScreenWidget.PortalPage portalPage, GenericValue portalPageColumn, int count) throws GeneralException, IOException {
        String portalPageId = portalPage.getActualPortalPageId();
        String originalPortalPageId = portalPage.getOriginalPortalPageId();
        String columnSeqId = portalPageColumn.getString("columnSeqId");
        String columnWidthPercentage = portalPageColumn.getString("columnWidthPercentage");
        String columnWidthPixels = portalPageColumn.getString("columnWidthPixels");
        String confMode = portalPage.getConfMode(context);

        Map<String, String> uiLabelMap = UtilGenerics.cast(context.get("uiLabelMap"));
        String delColumnLabel = "";
        String delColumnHint = "";
        String addPortletLabel = "";
        String addPortletHint = "";
        String colWidthLabel = "";
        String setColumnSizeHint = "";

        int i = 12/count;

        if (uiLabelMap == null) {
            Debug.logWarning("Could not find uiLabelMap in context", module);
        } else {
            delColumnLabel = uiLabelMap.get("CommonDeleteColumn");
            delColumnHint = uiLabelMap.get("CommonDeleteThisColumn");

            addPortletLabel = uiLabelMap.get("CommonAddAPortlet");
            addPortletHint = uiLabelMap.get("CommonAddPortletToPage");
            colWidthLabel = uiLabelMap.get("CommonWidth");
            setColumnSizeHint = uiLabelMap.get("CommonSetColumnWidth");
        }

        StringWriter sr = new StringWriter();
        sr.append("<@renderPortalPageColumnBegin ");
        sr.append("originalPortalPageId=\"");
        sr.append(originalPortalPageId);
        sr.append("\" portalPageId=\"");
        sr.append(portalPageId);
        sr.append("\" columnSeqId=\"");
        sr.append(columnSeqId);
        sr.append("\" ");
        if (UtilValidate.isNotEmpty(columnWidthPixels)) {
            sr.append("width=\"");
            sr.append(columnWidthPixels);
            sr.append("px\"");
        } else if (UtilValidate.isNotEmpty(columnWidthPercentage)) {
            sr.append("width=\"");
            sr.append(columnWidthPercentage);
            sr.append("%\"");
        }
        sr.append(" confMode=\"");
        sr.append(confMode);
        sr.append("\" delColumnLabel=\"");
        sr.append(delColumnLabel);
        sr.append("\" delColumnHint=\"");
        sr.append(delColumnHint);
        sr.append("\" addPortletLabel=\"");
        sr.append(addPortletLabel);
        sr.append("\" addPortletHint=\"");
        sr.append(addPortletHint);
        sr.append("\" colWidthLabel=\"");
        sr.append(colWidthLabel);
        sr.append("\" setColumnSizeHint=\"");
        sr.append(setColumnSizeHint);
        sr.append("\" avg=");
        sr.append(Integer.toString(i));
        sr.append(" />");
        executeMacro(writer, sr.toString());
    }

    @Override
    public void renderColumnContainerBegin(Appendable writer, Map<String, Object> context, ModelScreenWidget.ColumnContainer columnContainer) throws IOException {
        String containerId = columnContainer.getId(context);

        Map<String, Object> parameters = FastMap.newInstance();
        parameters.put("id", containerId);
        parameters.put("style", columnContainer.getStyle(context));
        executeMacro(writer, "renderColumnContainerBegin", parameters);
    }

    @Override
    public void renderColumnContainerEnd(Appendable writer, Map<String, Object> context, ModelScreenWidget.ColumnContainer columnContainer) throws IOException {
        executeMacro(writer, "renderColumnContainerEnd", null);
    }

    @Override
    public void renderColumnBegin(Appendable writer, Map<String, Object> context, ModelScreenWidget.Column column) throws IOException {
        String containerId = column.getId(context);
        Map<String, Object> parameters = FastMap.newInstance();
        parameters.put("id", containerId);
        parameters.put("style", column.getStyle(context));
        executeMacro(writer, "renderColumnBegin", parameters);
    }

    @Override
    public void renderColumnEnd(Appendable writer, Map<String, Object> context, ModelScreenWidget.Column column) throws IOException {
        String containerId = column.getId(context);
        Map<String, Object> parameters = FastMap.newInstance();
        parameters.put("id", containerId);
        parameters.put("style", column.getStyle(context));
        executeMacro(writer, "renderColumnEnd", parameters);
    }
}
