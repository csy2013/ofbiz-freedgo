package org.ofbiz.amaze.widget.form;

import freemarker.template.TemplateException;
import javolution.util.FastList;
import org.ofbiz.base.util.*;
import org.ofbiz.base.util.string.FlexibleStringExpander;
import org.ofbiz.base.util.template.FreeMarkerWorker;
import org.ofbiz.entity.Delegator;
import org.ofbiz.widget.WidgetWorker;
import org.ofbiz.widget.form.MacroFormRenderer;
import org.ofbiz.widget.form.ModelForm;
import org.ofbiz.widget.form.ModelFormField;
import org.ofbiz.widget.form.UtilHelpText;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.StringWriter;
import java.util.List;
import java.util.Locale;
import java.util.Map;

/**
 * Created by Administrator on 2015/1/19.
 */
public class AmazeMacroFormRenderer extends MacroFormRenderer {

    public AmazeMacroFormRenderer(String macroLibraryPath, HttpServletRequest request, HttpServletResponse response) throws TemplateException, IOException {
        super(macroLibraryPath, request, response);
    }

    public AmazeMacroFormRenderer(String macroLibraryPath, Appendable writer, HttpServletRequest request, HttpServletResponse response) throws TemplateException, IOException {
        super(macroLibraryPath, writer, request, response);
    }

    public void renderFieldTitle(Appendable writer, Map<String, Object> context, ModelFormField modelFormField) throws IOException {
        String tempTitleText = modelFormField.getTitle(context);
        String titleText = UtilHttp.encodeAmpersands(tempTitleText);
        String style = modelFormField.getTitleStyle();
        String id = modelFormField.getCurrentContainerId(context);

        StringBuilder sb = new StringBuilder();
        if (UtilValidate.isNotEmpty(titleText)) {
            if (" ".equals(titleText)) {
                // FIXME: we have to change the following code because it is a solution that only works with html.
                // If the title content is just a blank then render it calling renderFormatEmptySpace:
                // the method will set its content to work fine in most browser
                sb.append("&nbsp;");
            } else {
                titleText = encode(titleText, modelFormField, context);
                if (UtilValidate.isNotEmpty(modelFormField.getHeaderLink())) {
                    StringBuilder targetBuffer = new StringBuilder();
                    FlexibleStringExpander target = FlexibleStringExpander.getInstance(modelFormField.getHeaderLink());
                    String fullTarget = target.expandString(context);
                    targetBuffer.append(fullTarget);
                    String targetType = ModelFormField.HyperlinkField.DEFAULT_TARGET_TYPE;
                    if (UtilValidate.isNotEmpty(targetBuffer.toString()) && targetBuffer.toString().toLowerCase().startsWith("javascript:")) {
                        targetType="plain";
                    }
                    StringWriter sr = new StringWriter();
                    makeHyperlinkString(sr, modelFormField.getHeaderLinkStyle(), targetType, targetBuffer.toString(), null, titleText, "", modelFormField, this.request, this.response, context, "");

                    String title = sr.toString().replace("\"", "\'");
                    sr = new StringWriter();
                    sr.append("<@renderHyperlinkTitle ");
                    sr.append(" name=\"");
                    sr.append(modelFormField.getModelForm().getName());
                    sr.append("\" title=\"");
                    sr.append(FreeMarkerWorker.encodeDoubleQuotes(title));
                    sr.append("\" />");
                    executeMacro(writer, sr.toString());
                } else if (modelFormField.isSortField()) {
                    renderSortField(writer, context, modelFormField, titleText);
                } else if (modelFormField.isRowSubmit()) {
                    StringWriter sr = new StringWriter();
                    sr.append("<@renderHyperlinkTitle ");
                    sr.append(" name=\"");
                    sr.append(modelFormField.getModelForm().getName());
                    sr.append("\" title=\"");
                    sr.append(titleText);
                    sr.append("\" showSelectAll=\"Y\"/>");
                    executeMacro(writer, sr.toString());
                } else {
                    //<label for="doc-ipt-3" class="am-u-sm-2 am-form-label">电子邮件</label>
                    sb.append(titleText);
                }
            }
        }

        if (!sb.toString().isEmpty()) {
            //check for required field style on single forms
            if ("single".equals(modelFormField.getModelForm().getType()) && modelFormField.getRequiredField()) {
                String requiredStyle = modelFormField.getRequiredFieldStyle();
                if (UtilValidate.isNotEmpty(requiredStyle)) {
                    style = requiredStyle;
                }
            }

            StringWriter sr = new StringWriter();
            if(modelFormField.getModelForm().getType().equals("single")||modelFormField.getModelForm().getType().equals("upload")) {
                sr.append("<@renderFieldTitle1 ");
            }else{
                sr.append("<@renderFieldTitle ");
            }
            sr.append(" style=\"");
            sr.append(style);

            String displayHelpText = UtilProperties.getPropertyValue("widget.properties", "widget.form.displayhelpText");

            if ("Y".equals(displayHelpText)) {
                Delegator delegator = WidgetWorker.getDelegator(context);
                Locale locale = (Locale)context.get("locale");
                String entityName = modelFormField.getEntityName();
                String fieldName = modelFormField.getFieldName();
                String helpText = UtilHelpText.getEntityFieldDescription(entityName, fieldName, delegator, locale);

                sr.append("\" fieldHelpText=\"");
                sr.append(FreeMarkerWorker.encodeDoubleQuotes(helpText));
            }
            sr.append("\" title=\"");
            sr.append(sb.toString());
            if (UtilValidate.isNotEmpty(id)) {
                sr.append("\" id=\"");
                sr.append(id);
                sr.append("_title");
            }
            //只有是single类型的表单才使用label
            if(modelFormField.getModelForm().getType().equals("single")||modelFormField.getModelForm().getType().equals("upload")) {
                if (UtilValidate.isNotEmpty(id)) {
                    sr.append("\" idfor=\"");
                    sr.append(id);
                }
            }
            sr.append("\" />");
            executeMacro(writer, sr.toString());
        }
    }
    public void renderLookupField(Appendable writer, Map<String, Object> context, ModelFormField.LookupField lookupField) throws IOException {

        ModelFormField modelFormField = lookupField.getModelFormField();
        String lookupFieldFormName = lookupField.getFormName(context);
        String className = "";
        String alert = "false";
        if (UtilValidate.isNotEmpty(modelFormField.getWidgetStyle())) {
            className = modelFormField.getWidgetStyle();
            if (modelFormField.shouldBeRed(context)) {
                alert = "true";
            }
        }

        //check for required field style on single forms
        if ("single".equals(modelFormField.getModelForm().getType()) && modelFormField.getRequiredField()) {
            String requiredStyle = modelFormField.getRequiredFieldStyle();
            if (UtilValidate.isEmpty(requiredStyle)) requiredStyle = "required";
            if (UtilValidate.isEmpty(className)) className = requiredStyle;
            else className = requiredStyle + " " + className;
        }

        String name = modelFormField.getParameterName(context);
        String value = modelFormField.getEntry(context, lookupField.getDefaultValue(context));
        if (value == null) {
            value = "";
        }
        String size = Integer.toString(lookupField.getSize());
        Integer maxlength = lookupField.getMaxlength();
        String id = modelFormField.getCurrentContainerId(context);

        List<ModelForm.UpdateArea> updateAreas = modelFormField.getOnChangeUpdateAreas();

        //add default ajax auto completer to all lookup fields
        if (UtilValidate.isEmpty(updateAreas) && UtilValidate.isNotEmpty(lookupFieldFormName)) {
            String autoCompleterTarget = null;
            if (lookupFieldFormName.indexOf('?') == -1) {
                autoCompleterTarget = lookupFieldFormName + "?";
            } else {
                autoCompleterTarget = lookupFieldFormName + "&amp;amp;";
            }
            autoCompleterTarget = autoCompleterTarget + "ajaxLookup=Y";
            updateAreas = FastList.newInstance();
            updateAreas.add(new ModelForm.UpdateArea("change", id, autoCompleterTarget));
        }

        boolean ajaxEnabled = updateAreas != null && this.javaScriptEnabled;
        String autocomplete = "";
        if (!lookupField.getClientAutocompleteField() || !ajaxEnabled) {
            autocomplete = "off";
        }

        String event = modelFormField.getEvent();
        String action = modelFormField.getAction(context);
        boolean readonly = lookupField.getReadonly();

        // add lookup pop-up button
        String descriptionFieldName = lookupField.getDescriptionFieldName();
        String formName = modelFormField.getModelForm().getCurrentFormName(context);
        StringBuilder targetParameterIter = new StringBuilder();
        StringBuilder imgSrc = new StringBuilder();
        // FIXME: refactor using the StringUtils methods
        List<String> targetParameterList = lookupField.getTargetParameterList();
        targetParameterIter.append("[");
        for (String targetParameter: targetParameterList) {
            if (targetParameterIter.length()>1) {
                targetParameterIter.append(",");
            }
            targetParameterIter.append("'");
            targetParameterIter.append(targetParameter);
            targetParameterIter.append("'");
        }
        targetParameterIter.append("]");
        this.appendContentUrl(imgSrc, "/images/fieldlookup.gif");

        String ajaxUrl = "";

        if (ajaxEnabled) {
            ajaxUrl = createAjaxParamsFromUpdateAreas(updateAreas, null, context);
        }

        String lookupPresentation = lookupField.getLookupPresentation();
        if(UtilValidate.isEmpty(lookupPresentation)){
            lookupPresentation = "";
        }

        String lookupHeight = lookupField.getLookupHeight();
        if(UtilValidate.isEmpty(lookupHeight)){
            lookupHeight = "";
        }

        String lookupWidth = lookupField.getLookupWidth();
        if(UtilValidate.isEmpty(lookupWidth)){
            lookupWidth = "";
        }

        String lookupPosition = lookupField.getLookupPosition();
        if(UtilValidate.isEmpty(lookupPosition)){
            lookupPosition = "";
        }

        String fadeBackground = lookupField.getFadeBackground();
        if (UtilValidate.isEmpty(fadeBackground)){
            fadeBackground = "false";
        }
        Boolean isInitiallyCollapsed = lookupField.getInitiallyCollapsed();

        String clearText = "";
        Map<String, Object> uiLabelMap = UtilGenerics.checkMap(context.get("uiLabelMap"));
        if (uiLabelMap != null) {
            clearText = (String) uiLabelMap.get("CommonClear");
        } else {
            Debug.logWarning("Could not find uiLabelMap in context", module);
        }

        boolean showDescription = "Y".equals(UtilProperties.getPropertyValue("widget", "widget.lookup.showDescription", "N"));

        // lastViewName, used by lookup to remember the real last view name
        String lastViewName = request.getParameter("_LAST_VIEW_NAME_"); // Try to get it from parameters firstly
        if (UtilValidate.isEmpty(lastViewName)) { // get from session
            lastViewName = (String) request.getSession().getAttribute("_LAST_VIEW_NAME_");
        }
        if (UtilValidate.isEmpty(lastViewName)) {
            lastViewName = "";
        }
        StringWriter sr = new StringWriter();
        sr.append("<@renderLookupField ");
        sr.append(" className=\"");
        sr.append(className);
        sr.append("\" alert=\"");
        sr.append(alert);
        sr.append("\" name=\"");
        sr.append(name);
        sr.append("\" value=\"");
        sr.append(value);
        sr.append("\" size=\"");
        sr.append(size);
        sr.append("\" maxlength=\"");
        sr.append((maxlength != null? Integer.toString(maxlength): ""));
        sr.append("\" id=\"");
        sr.append(id);
        sr.append("\" event=\"");
        if (event != null) {
            sr.append(event);
        }
        sr.append("\" action=\"");
        if (action != null) {
            sr.append(action);
        }
        sr.append("\" readonly=");
        sr.append(Boolean.toString(readonly));

        sr.append(" autocomplete=\"");
        sr.append(autocomplete);
        sr.append("\" descriptionFieldName=\"");
        sr.append(descriptionFieldName);
        sr.append("\" formName=\"");
        sr.append(formName);
        sr.append("\" fieldFormName=\"");
        sr.append(lookupFieldFormName);
        sr.append("\" targetParameterIter=");
        sr.append(targetParameterIter.toString());
        sr.append(" imgSrc=\"");
        sr.append(imgSrc.toString());
        sr.append("\" ajaxUrl=\"");
        sr.append(ajaxUrl);
        sr.append("\" ajaxEnabled=");
        sr.append(Boolean.toString(ajaxEnabled));
        sr.append(" presentation=\"");
        sr.append(lookupPresentation);
        sr.append("\" height=\"");
        sr.append(lookupHeight);
        sr.append("\" width=\"");
        sr.append(lookupWidth);
        sr.append("\" position=\"");
        sr.append(lookupPosition);
        sr.append("\" fadeBackground=\"");
        sr.append(fadeBackground);
        sr.append("\" clearText=\"");
        sr.append(clearText);
        sr.append("\" showDescription=\"");
        sr.append(Boolean.toString(showDescription));
        sr.append("\" initiallyCollapsed=\"");
        sr.append(Boolean.toString(isInitiallyCollapsed));
        sr.append("\" lastViewName=\"");
        sr.append(lastViewName);

        /*增加asterisks，tooltip*/
        String requiredField = "";
        if (modelFormField.getRequiredField()) {
               requiredField = (String) uiLabelMap.get("CommonRequired");
        }
        sr.append("\" required=\"");
        sr.append(requiredField);
        String tooltip = modelFormField.getTooltip(context);
        sr.append("\" tooltip=\"");
        sr.append(tooltip);
        sr.append("\" />");
        executeMacro(writer, sr.toString());

//        this.addAsterisks(writer, context, modelFormField);
        this.makeHyperlinkString(writer, lookupField.getSubHyperlink(), context);
//        this.appendTooltip(writer, context, modelFormField);

    }

    public void renderTextField(Appendable writer, Map<String, Object> context, ModelFormField.TextField textField) throws IOException {
        ModelFormField modelFormField = textField.getModelFormField();
        String name = modelFormField.getParameterName(context);
        String className = "";
        String alert = "false";
        String mask = "";
        if (UtilValidate.isNotEmpty(modelFormField.getWidgetStyle())) {
            className = modelFormField.getWidgetStyle();
            if (modelFormField.shouldBeRed(context)) {
                alert = "true";
            }
        }

        String value = modelFormField.getEntry(context, textField.getDefaultValue(context));
        String textSize = Integer.toString(textField.getSize());
        String maxlength = "";
        if (textField.getMaxlength() != null) {
            maxlength = Integer.toString(textField.getMaxlength());
        }
        String event = modelFormField.getEvent();
        String action = modelFormField.getAction(context);
        String id = modelFormField.getCurrentContainerId(context);
        String clientAutocomplete = "false";

        //check for required field style on single forms
        if ("single".equals(modelFormField.getModelForm().getType()) && modelFormField.getRequiredField()) {
            String requiredStyle = modelFormField.getRequiredFieldStyle();
            if (UtilValidate.isEmpty(requiredStyle)) requiredStyle = "required";
            if (UtilValidate.isEmpty(className)) className = requiredStyle;
            else className = requiredStyle + " " + className;
        }

        List<ModelForm.UpdateArea> updateAreas = modelFormField.getOnChangeUpdateAreas();
        boolean ajaxEnabled = updateAreas != null && this.javaScriptEnabled;
        if (textField.getClientAutocompleteField() || ajaxEnabled) {
            clientAutocomplete = "true";
        }

        if (UtilValidate.isNotEmpty(textField.getMask())) {
            mask = textField.getMask();
        }

        String ajaxUrl = createAjaxParamsFromUpdateAreas(updateAreas, null, context);
        boolean disabled = textField.getDisabled();

        StringWriter sr = new StringWriter();
        sr.append("<@renderTextField1 ");
        sr.append("name=\"");
        sr.append(name);
        sr.append("\" className=\"");
        sr.append(className);
        sr.append("\" alert=\"");
        sr.append(alert);
        sr.append("\" value=\"");
        sr.append(value);
        sr.append("\" textSize=\"");
        sr.append(textSize);
        sr.append("\" maxlength=\"");
        sr.append(maxlength);
        sr.append("\" id=\"");
        sr.append(id);
        sr.append("\" event=\"");
        if (event != null) {
            sr.append(event);
        }
        sr.append("\" action=\"");
        if (action != null) {
            sr.append(action);
        }
        sr.append("\" disabled=");
        sr.append(Boolean.toString(disabled));

        sr.append(" clientAutocomplete=\"");
        sr.append(clientAutocomplete);
        sr.append("\" ajaxUrl=\"");
        sr.append(ajaxUrl);
        sr.append("\" ajaxEnabled=");
        sr.append(Boolean.toString(ajaxEnabled));
        sr.append(" mask=\"");
        sr.append(mask);

        /*增加asterisks，tooltip*/
        String requiredField = "";
        if (modelFormField.getRequiredField()) {
            Map<String, String> uiLabelMap = UtilGenerics.checkMap(context.get("uiLabelMap"));
            requiredField = uiLabelMap.get("CommonRequired");
        }
        sr.append("\" required=\"");
        sr.append(requiredField);
        String tooltip = modelFormField.getTooltip(context);
        sr.append("\" tooltip=\"");
        sr.append(tooltip);

        sr.append("\" />");


        executeMacro(writer, sr.toString());

        ModelFormField.SubHyperlink subHyperlink = textField.getSubHyperlink();
        if (subHyperlink != null && subHyperlink.shouldUse(context)) {
            makeHyperlinkString(writer,subHyperlink,context);
        }
//        this.addAsterisks(writer, context, modelFormField);
//        this.appendTooltip(writer, context, modelFormField);
    }


    public void renderFieldGroupOpen(Appendable writer, Map<String, Object> context, ModelForm.FieldGroup fieldGroup) throws IOException {
        String style = fieldGroup.getStyle();
        String id = fieldGroup.getId();
        FlexibleStringExpander titleNotExpanded = FlexibleStringExpander.getInstance(fieldGroup.getTitle());
        String title = titleNotExpanded.expandString(context);
        Boolean collapsed = fieldGroup.initiallyCollapsed();
        String collapsibleAreaId = fieldGroup.getId() + "_body";
        Boolean collapsible = fieldGroup.collapsible();
        String expandToolTip = "";
        String collapseToolTip = "";

        if (UtilValidate.isNotEmpty(style) || UtilValidate.isNotEmpty(id) || UtilValidate.isNotEmpty(title)) {
            if (fieldGroup.collapsible()) {
                Map<String, Object> uiLabelMap = UtilGenerics.checkMap(context.get("uiLabelMap"));
                //Map<String, Object> paramMap = UtilGenerics.checkMap(context.get("requestParameters"));
                if (uiLabelMap != null) {
                    expandToolTip = (String) uiLabelMap.get("CommonExpand");
                    collapseToolTip = (String) uiLabelMap.get("CommonCollapse");
                }
            }
        }
        StringWriter sr = new StringWriter();
        sr.append("<@renderFieldGroupOpen ");
        sr.append(" style=\"");
        if (style != null) {
            sr.append(style);
        }
        sr.append("\" id=\"");
        sr.append(id);
        sr.append("\" title=\"");
        sr.append(title);
        sr.append("\" collapsed=");
        sr.append(Boolean.toString(collapsed));
        sr.append(" collapsibleAreaId=\"");
        sr.append(collapsibleAreaId);
        sr.append("\" collapsible=");
        sr.append(Boolean.toString(collapsible));
        sr.append(" expandToolTip=\"");
        sr.append(expandToolTip);
        sr.append("\" collapseToolTip=\"");
        sr.append(collapseToolTip);
        sr.append("\" />");
        executeMacro(writer, sr.toString());
    }

    public void renderFieldGroupClose(Appendable writer, Map<String, Object> context, ModelForm.FieldGroup fieldGroup) throws IOException {
        String style = fieldGroup.getStyle();
        String id = fieldGroup.getId();
        FlexibleStringExpander titleNotExpanded = FlexibleStringExpander.getInstance(fieldGroup.getTitle());
        String title = titleNotExpanded.expandString(context);

        StringWriter sr = new StringWriter();
        sr.append("<@renderFieldGroupClose ");
        sr.append(" style=\"");
        if (style != null) {
            sr.append(style);
        }
        sr.append("\" id=\"");
        if (id != null) {
            sr.append(id);
        }
        sr.append("\" title=\"");
        if (title != null) {
            sr.append(title);
        }
        sr.append("\" />");
        executeMacro(writer, sr.toString());
    }

    public void renderFormatFieldRowTitleCellOpen(Appendable writer, Map<String, Object> context, ModelFormField modelFormField) throws IOException {
        String style = modelFormField.getTitleAreaStyle();
        StringWriter sr = new StringWriter();
        sr.append("<@renderFormatFieldRowTitleCellOpen ");
        sr.append(" style=\"");
        sr.append(style);
        sr.append("\" />");
        executeMacro(writer, sr.toString());
    }


    public void renderFormatFieldRowTitleCellClose(Appendable writer, Map<String, Object> context, ModelFormField modelFormField) throws IOException {
        StringWriter sr = new StringWriter();
        sr.append("<@renderFormatFieldRowTitleCellClose />");
        executeMacro(writer, sr.toString());
    }

    /**
     *
     * @param writer
     * @param context
     * @param modelFormField
     * @param maxPosition 当前 filed 的 positions 分div时需要*2
     * @throws IOException
     */
    public void renderFieldTitle(Appendable writer, Map<String, Object> context, ModelFormField modelFormField,int maxPosition) throws IOException {

        int amAvg = (12 / (maxPosition*2)); //只有1，2，3，4
        if(maxPosition==4){
            amAvg = 1;
        }
        else if(maxPosition==3){
            amAvg = 2;
        }
        else if(maxPosition==2){
            amAvg = 3;
        }
        else if(maxPosition==1){
            amAvg = 5;
        }
        boolean isEnd = false;
        if(modelFormField.getPosition()==maxPosition){
            isEnd = true;
        }
        String tempTitleText = modelFormField.getTitle(context);
        String titleText = UtilHttp.encodeAmpersands(tempTitleText);
        String style = modelFormField.getTitleStyle();
        String id = modelFormField.getCurrentContainerId(context);

        StringBuilder sb = new StringBuilder();
        if (UtilValidate.isNotEmpty(titleText)) {
            if (" ".equals(titleText)) {
                // FIXME: we have to change the following code because it is a solution that only works with html.
                // If the title content is just a blank then render it calling renderFormatEmptySpace:
                // the method will set its content to work fine in most browser
                sb.append("&nbsp;");
            } else {
                titleText = encode(titleText, modelFormField, context);
                if (UtilValidate.isNotEmpty(modelFormField.getHeaderLink())) {
                    StringBuilder targetBuffer = new StringBuilder();
                    FlexibleStringExpander target = FlexibleStringExpander.getInstance(modelFormField.getHeaderLink());
                    String fullTarget = target.expandString(context);
                    targetBuffer.append(fullTarget);
                    String targetType = ModelFormField.HyperlinkField.DEFAULT_TARGET_TYPE;
                    if (UtilValidate.isNotEmpty(targetBuffer.toString()) && targetBuffer.toString().toLowerCase().startsWith("javascript:")) {
                        targetType="plain";
                    }
                    StringWriter sr = new StringWriter();
                    makeHyperlinkString(sr, modelFormField.getHeaderLinkStyle(), targetType, targetBuffer.toString(), null, titleText, "", modelFormField, this.request, this.response, context, "");

                    String title = sr.toString().replace("\"", "\'");
                    sr = new StringWriter();
                    sr.append("<@renderHyperlinkTitle ");
                    sr.append(" name=\"");
                    sr.append(modelFormField.getModelForm().getName());
                    sr.append("\" title=\"");
                    sr.append(FreeMarkerWorker.encodeDoubleQuotes(title));
                    sr.append("\" />");
                    executeMacro(writer, sr.toString());
                } else if (modelFormField.isSortField()) {
                    renderSortField(writer, context, modelFormField, titleText);
                } else if (modelFormField.isRowSubmit()) {
                    StringWriter sr = new StringWriter();
                    sr.append("<@renderHyperlinkTitle ");
                    sr.append(" name=\"");
                    sr.append(modelFormField.getModelForm().getName());
                    sr.append("\" title=\"");
                    sr.append(titleText);
                    sr.append("\" showSelectAll=\"Y\"/>");
                    executeMacro(writer, sr.toString());
                } else {
                    //<label for="doc-ipt-3" class="am-u-sm-2 am-form-label">电子邮件</label>
                    sb.append(titleText);
                }
            }
        }

        if (!sb.toString().isEmpty()) {
            //check for required field style on single forms
            if ("single".equals(modelFormField.getModelForm().getType()) && modelFormField.getRequiredField()) {
                String requiredStyle = modelFormField.getRequiredFieldStyle();
                if (UtilValidate.isNotEmpty(requiredStyle)) {
                    style = requiredStyle;
                }
            }

            StringWriter sr = new StringWriter();
            if(modelFormField.getModelForm().getType().equals("single")||modelFormField.getModelForm().getType().equals("upload")) {
                sr.append("<@renderFieldTitle1 ");
            }else{
                sr.append("<@renderFieldTitle ");
            }
            sr.append(" style=\"");
            sr.append(style);

            String displayHelpText = UtilProperties.getPropertyValue("widget.properties", "widget.form.displayhelpText");

            if ("Y".equals(displayHelpText)) {
                Delegator delegator = WidgetWorker.getDelegator(context);
                Locale locale = (Locale)context.get("locale");
                String entityName = modelFormField.getEntityName();
                String fieldName = modelFormField.getFieldName();
                String helpText = UtilHelpText.getEntityFieldDescription(entityName, fieldName, delegator, locale);

                sr.append("\" fieldHelpText=\"");
                sr.append(FreeMarkerWorker.encodeDoubleQuotes(helpText));
            }
            sr.append("\" title=\"");
            sr.append(sb.toString());
            if (UtilValidate.isNotEmpty(id)) {
                sr.append("\" id=\"");
                sr.append(id);
                sr.append("_title");
            }
            //只有是single类型的表单才使用label
            if(modelFormField.getModelForm().getType().equals("single")||modelFormField.getModelForm().getType().equals("upload")) {
                if (UtilValidate.isNotEmpty(id)) {
                    sr.append("\" idfor=\"");
                    sr.append(id);
                }
                sr.append("\" isEnd=\"");
                sr.append(String.valueOf(isEnd));
                sr.append("\" avg=\"");
                sr.append(String.valueOf(amAvg));

            }
            sr.append("\" />");
            executeMacro(writer, sr.toString());
        }
    }


    public void renderFormatFieldRowWidgetCellOpen(Appendable writer, Map<String, Object> context, ModelFormField modelFormField, int positions, int positionSpan, Integer nextPositionInRow) throws IOException {
        int amAvg = 0;
        String areaStyle = modelFormField.getWidgetAreaStyle();
        StringWriter sr = new StringWriter();
        sr.append("<@renderFormatFieldRowWidgetCellOpen ");
        sr.append(" positionSpan=");
        sr.append(Integer.toString(positionSpan));
        sr.append(" style=\"");
        sr.append(areaStyle);
        sr.append("\"");
        sr.append(" avg=");
        sr.append(amAvg+"");
        sr.append(" />");
        executeMacro(writer, sr.toString());

    }

    public void renderFormatFieldRowWidgetCellOpen(Appendable writer, Map<String, Object> context, ModelFormField modelFormField, int positions, int positionSpan, Integer nextPositionInRow,int maxPosition) throws IOException{

        int amAvg = (12 / (maxPosition*2)); //只有1，2，3，4
        if(maxPosition==4){
            amAvg = 2;
        }
        else if(maxPosition==3){
            amAvg = 2;
        }
        else if(maxPosition==2){
            amAvg = 3;
        }
        else if(maxPosition==1){
            amAvg = 7;
        }
        Boolean isEnd=false;
        if(maxPosition==modelFormField.getPosition()){
            isEnd = true;
        }else if(modelFormField.getPosition()>=(nextPositionInRow==null?1:nextPositionInRow)){
            isEnd = true;
        }
        String areaStyle = modelFormField.getWidgetAreaStyle();
        StringWriter sr = new StringWriter();
        sr.append("<@renderFormatFieldRowWidgetCellOpen ");
        sr.append(" positionSpan=");
        sr.append(Integer.toString(positionSpan));
        sr.append(" style=\"");
        sr.append(areaStyle);

        sr.append("\" isEnd=\"");
        sr.append(String.valueOf(isEnd));
        sr.append("\" avg=\"");
        sr.append(String.valueOf(amAvg));
        sr.append("\" />");
        executeMacro(writer, sr.toString());
    }

    public void renderFormatFieldRowWidgetCellClose(Appendable writer, Map<String, Object> context, ModelFormField modelFormField, int positions, int positionSpan, Integer nextPositionInRow) throws IOException {
        int position = modelFormField.getPosition();
        StringWriter sr = new StringWriter();
        sr.append("<@renderFormatFieldRowWidgetCellClose />");
        executeMacro(writer, sr.toString());
    }

    @Override
    public void renderFileldGroupTabStart(Appendable writer, Map<String, Object> context, List<ModelForm.FieldGroupBase> fieldGroupBases) throws IOException {
        if(fieldGroupBases!=null&&(!fieldGroupBases.isEmpty()))
        {
            StringWriter sr = new StringWriter();
            sr.append("<@renderFileldGroupTabStart />");
            executeMacro(writer, sr.toString());
            for (int i = 0; i < fieldGroupBases.size(); i++) {
                ModelForm.FieldGroupBase fieldGroupBase = fieldGroupBases.get(i);
                String style = ((ModelForm.FieldGroup)fieldGroupBase).getStyle();
                FlexibleStringExpander titleNotExpanded = FlexibleStringExpander.getInstance(((ModelForm.FieldGroup) fieldGroupBase).getTitle());
                String title = titleNotExpanded.expandString(context);
                if(style!=null&&style.equals("tabs")) {
                    sr = new StringWriter();
                    sr.append("<@renderFileldGroupTabNavStart ");
                    sr.append(" seq=");
                    sr.append(Integer.toString(i));
                    sr.append(" title=\"");
                    sr.append(title);
                    sr.append("\" id=\"");
                    sr.append(((ModelForm.FieldGroup) fieldGroupBase).getId());
                    sr.append("\" />");
                    executeMacro(writer, sr.toString());
                }
            }
            sr = new StringWriter();
            sr.append("<@renderFileldGroupTabNavEnd />");
            executeMacro(writer, sr.toString());
        }
    }


    @Override
    public void renderFileldGroupTabEnd(Appendable writer, Map<String, Object> context, List<ModelForm.FieldGroupBase> fieldGroup) throws IOException {
        if(fieldGroup!=null&&(!fieldGroup.isEmpty()))
        {  StringWriter sr = new StringWriter();
            sr.append("<@renderFileldGroupTabEnd />");
            executeMacro(writer, sr.toString());
        }
    }
}
