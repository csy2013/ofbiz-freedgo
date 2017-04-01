package org.ofbiz.coloradmin.widget.html;

import org.ofbiz.base.util.*;
import org.ofbiz.widget.WidgetWorker;
import org.ofbiz.widget.form.ModelForm;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashSet;
import java.util.List;
import java.util.Locale;
import java.util.Map;

/**
 * Created by changsy on 2015/1/18.
 */
public class ColoradminHtmlFormRenderer extends org.ofbiz.widget.html.HtmlFormRenderer {

    protected ColoradminHtmlFormRenderer() {}

    public ColoradminHtmlFormRenderer(HttpServletRequest request, HttpServletResponse response) {
       super(request,response);
    }

    public void renderNextPrev(Appendable writer, Map<String, Object> context, ModelForm modelForm) throws IOException {
        boolean ajaxEnabled = false;
        List<ModelForm.UpdateArea> updateAreas = modelForm.getOnPaginateUpdateAreas();
        String targetService = modelForm.getPaginateTarget(context);
        if (this.javaScriptEnabled) {
            if (UtilValidate.isNotEmpty(updateAreas)) {
                ajaxEnabled = true;
            }
        }
        if (targetService == null) {
            targetService = "${targetService}";
        }
        if (UtilValidate.isEmpty(targetService) && updateAreas == null) {
            Debug.logWarning("Cannot paginate because TargetService is empty for the form: " + modelForm.getName(), module);
            return;
        }

        // get the parameterized pagination index and size fields
        int paginatorNumber = WidgetWorker.getPaginatorNumber(context);
        String viewIndexParam = modelForm.getMultiPaginateIndexField(context);
        String viewSizeParam = modelForm.getMultiPaginateSizeField(context);

        int viewIndex = modelForm.getViewIndex(context);
        int viewSize = modelForm.getViewSize(context);
        int listSize = modelForm.getListSize(context);

        int lowIndex = modelForm.getLowIndex(context);
        int highIndex = modelForm.getHighIndex(context);
        int actualPageSize = modelForm.getActualPageSize(context);

        // if this is all there seems to be (if listSize < 0, then size is unknown)
        if (actualPageSize >= listSize && listSize >= 0) return;

        // needed for the "Page" and "rows" labels
        Map<String, String> uiLabelMap = UtilGenerics.checkMap(context.get("uiLabelMap"));
        String pageLabel = "";
        String commonDisplaying = "";
        if (uiLabelMap == null) {
            Debug.logWarning("Could not find uiLabelMap in context", module);
        } else {
            pageLabel = uiLabelMap.get("CommonPage");
            Map<String, Integer> messageMap = UtilMisc.toMap("lowCount", Integer.valueOf(lowIndex + 1), "highCount", Integer.valueOf(lowIndex + actualPageSize), "total", Integer.valueOf(listSize));
            commonDisplaying = UtilProperties.getMessage("CommonUiLabels", "CommonDisplaying", messageMap, (Locale) context.get("locale"));
        }

        // for legacy support, the viewSizeParam is VIEW_SIZE and viewIndexParam is VIEW_INDEX when the fields are "viewSize" and "viewIndex"
        if (viewIndexParam.equals("viewIndex" + "_" + paginatorNumber)) viewIndexParam = "VIEW_INDEX" + "_" + paginatorNumber;
        if (viewSizeParam.equals("viewSize" + "_" + paginatorNumber)) viewSizeParam = "VIEW_SIZE" + "_" + paginatorNumber;

        String str = (String) context.get("_QBESTRING_");

        // strip legacy viewIndex/viewSize params from the query string
        String queryString = UtilHttp.stripViewParamsFromQueryString(str, "" + paginatorNumber);

        // strip parametrized index/size params from the query string
        HashSet<String> paramNames = new HashSet<String>();
        paramNames.add(viewIndexParam);
        paramNames.add(viewSizeParam);
        queryString = UtilHttp.stripNamedParamsFromQueryString(queryString, paramNames);

        String anchor = "";
        String paginateAnchor = modelForm.getPaginateTargetAnchor();
        if (paginateAnchor != null) anchor = "#" + paginateAnchor;

        // Create separate url path String and request parameters String,
        // add viewIndex/viewSize parameters to request parameter String
        String urlPath = UtilHttp.removeQueryStringFromTarget(targetService);
        StringBuilder prepLinkBuffer = new StringBuilder();
        String prepLinkQueryString = UtilHttp.getQueryStringFromTarget(targetService);
        if (prepLinkQueryString != null) {
            prepLinkBuffer.append(prepLinkQueryString);
        }
        if (prepLinkBuffer.indexOf("?") < 0) {
            prepLinkBuffer.append("?");
        } else if (prepLinkBuffer.indexOf("?", prepLinkBuffer.length() - 1) > 0) {
            prepLinkBuffer.append("&amp;");
        }
        if (!UtilValidate.isEmpty(queryString) && !queryString.equals("null")) {
            prepLinkBuffer.append(queryString).append("&amp;");
        }
        prepLinkBuffer.append(viewSizeParam).append("=").append(viewSize).append("&amp;").append(viewIndexParam).append("=");
        String prepLinkText = prepLinkBuffer.toString();
        if (ajaxEnabled) {
            // Prepare params for prototype.js
            prepLinkText = prepLinkText.replace("?", "");
            prepLinkText = prepLinkText.replace("&amp;", "&");
        }

        /**
         * <div class="am-cf">
         共 15 条记录
         <div class="am-fr">
         <ul class="am-pagination">
         <li class="am-disabled"><a href="#">«</a></li>
         <li class="am-active"><a href="#">1</a></li>
         <li><a href="#">2</a></li>
         <li><a href="#">3</a></li>
         <li><a href="#">4</a></li>
         <li><a href="#">5</a></li>
         <li><a href="#">»</a></li>
         </ul>
         </div>
         </div>
         显示：  显示1 - 20，共70            前 第一页  1 2 3 4  ... 后 最后页
         */
        writer.append("<div class=\"").append("container").append("\">");
        writer.append("<div class=\"").append("clearfix").append("\">");

        //  show row count
        writer.append("<div class=\"").append("pull-left").append("\">");
        writer.append(commonDisplaying);
        appendWhitespace(writer);
        writer.append("</div>");


        writer.append("<div class=\"").append("pull-right").append("\">");
        appendWhitespace(writer);
        writer.append(" <ul class=\"").append("nav-pager pagination").append("\">");
        appendWhitespace(writer);
        String linkText;


        // Previous button
        writer.append("  <li");
        if (viewIndex > 0) {
            writer.append("><a href=\"");
            if (ajaxEnabled) {
                writer.append("javascript:ajaxUpdateAreas('").append(createAjaxParamsFromUpdateAreas(updateAreas, prepLinkText + (viewIndex - 1) + anchor, context)).append("')");
            } else {
                linkText = prepLinkText + (viewIndex - 1) + anchor;
                appendOfbizUrl(writer, urlPath + linkText);
            }
            writer.append("\">").append(modelForm.getPaginatePreviousLabel(context)).append("</a>");
        } else {
            // disabled button
            writer.append(" class = \"disabled\">").append(modelForm.getPaginatePreviousLabel(context));
        }
        writer.append("</li>");
        appendWhitespace(writer);

        // First button
        writer.append("  <li");
        if (viewIndex > 0) {
            writer.append("><a href=\"");
            if (ajaxEnabled) {
                writer.append("javascript:ajaxUpdateAreas('").append(createAjaxParamsFromUpdateAreas(updateAreas, prepLinkText + 0 + anchor, context)).append("')");
            } else {
                linkText = prepLinkText + 0 + anchor;
                appendOfbizUrl(writer, urlPath + linkText);
            }
            writer.append("\">").append(modelForm.getPaginateFirstLabel(context)).append("</a>");
        } else {
            // disabled button
            writer.append(" class = \"disabled\">").append(modelForm.getPaginateFirstLabel(context));
        }
        writer.append("</li>");
        appendWhitespace(writer);



        // Page select dropdown
        if (listSize > 0 && this.javaScriptEnabled) {
            writer.append("  <li>").append(pageLabel).append(" <select name=\"page\" size=\"1\" onchange=\"");
            if (ajaxEnabled) {
                writer.append("javascript:ajaxUpdateAreas('").append(createAjaxParamsFromUpdateAreas(updateAreas, prepLinkText + "' + this.value", context)).append(")");
            } else {
                linkText = prepLinkText;
                if (linkText.startsWith("/")) {
                    linkText = linkText.substring(1);
                }
                writer.append("location.href = '");
                appendOfbizUrl(writer, urlPath + linkText);
                writer.append("' + this.value;");
            }
            writer.append("\">");
            // actual value
            int page = 0;
            for (int i = 0; i < listSize;) {
                if (page == viewIndex) {
                    writer.append("<option selected value=\"");
                } else {
                    writer.append("<option value=\"");
                }
                writer.append(Integer.toString(page));
                writer.append("\">");
                writer.append(Integer.toString(1 + page));
                writer.append("</option>");
                // increment page and calculate next index
                page++;
                i = page * viewSize;
            }
            writer.append("</select></li>");
        }



        // Next button
        writer.append("  <li");
        if (highIndex < listSize) {
            writer.append("><a href=\"");
            if (ajaxEnabled) {
                writer.append("javascript:ajaxUpdateAreas('").append(createAjaxParamsFromUpdateAreas(updateAreas, prepLinkText + (viewIndex + 1) + anchor, context)).append("')");
            } else {
                linkText = prepLinkText + (viewIndex + 1) + anchor;
                appendOfbizUrl(writer, urlPath + linkText);
            }
            writer.append("\">").append(modelForm.getPaginateNextLabel(context)).append("</a>");
        } else {
            // disabled button
            writer.append(" class=\"disabled\">").append(modelForm.getPaginateNextLabel(context));
        }
        writer.append("</li>");
        appendWhitespace(writer);

        // Last button
        writer.append("  <li");
        if (highIndex < listSize) {
            int lastIndex = UtilMisc.getViewLastIndex(listSize, viewSize);
            writer.append("><a href=\"");
            if (ajaxEnabled) {
                writer.append("javascript:ajaxUpdateAreas('").append(createAjaxParamsFromUpdateAreas(updateAreas, prepLinkText + lastIndex + anchor, context)).append("')");
            } else {
                linkText = prepLinkText + lastIndex + anchor;
                appendOfbizUrl(writer, urlPath + linkText);
            }
            writer.append("\">").append(modelForm.getPaginateLastLabel(context)).append("</a>");
        } else {
            // disabled button
            writer.append(" class=\"disabled\">").append(modelForm.getPaginateLastLabel(context));
        }
        writer.append("</li>");
        appendWhitespace(writer);

        writer.append(" </ul>");
        appendWhitespace(writer);
        writer.append("</div>");
        appendWhitespace(writer);
        writer.append("</div>");
        appendWhitespace(writer);
    }
}
