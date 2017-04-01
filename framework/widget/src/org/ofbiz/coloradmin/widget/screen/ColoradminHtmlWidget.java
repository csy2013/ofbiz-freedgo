package org.ofbiz.coloradmin.widget.screen;


import org.ofbiz.base.util.GeneralException;
import org.ofbiz.base.util.UtilXml;
import org.ofbiz.widget.screen.HtmlWidget;
import org.ofbiz.widget.screen.ModelScreen;
import org.ofbiz.widget.screen.ModelScreenWidget;
import org.ofbiz.widget.screen.ScreenStringRenderer;
import org.w3c.dom.Element;

import java.io.IOException;
import java.util.List;
import java.util.Map;

/**
 * Created by changsy on 2015/1/13.
 */
public class ColoradminHtmlWidget extends HtmlWidget {
    public ColoradminHtmlWidget(ModelScreen modelScreen, Element htmlElement) {
        super(modelScreen, htmlElement);
    }

    public static class HtmlTemplateDecoratorSection extends HtmlWidget.HtmlTemplateDecoratorSection{

        public HtmlTemplateDecoratorSection(ColoradminModelScreen modelScreen, Element htmlTemplateDecoratorSectionElement) {
            super(modelScreen,htmlTemplateDecoratorSectionElement);
            List<? extends Element> subElementList = UtilXml.childElementList(htmlTemplateDecoratorSectionElement);
            this.subWidgets = ModelScreenWidget.readSubWidgets(this.modelScreen, subElementList,"coloradmin");
        }

        @Override
        public void renderWidgetString(Appendable writer, Map<String, Object> context, ScreenStringRenderer screenStringRenderer) throws GeneralException, IOException {
            // render sub-widgets
            renderSubWidgetsString(this.subWidgets, writer, context, screenStringRenderer);
        }

        @Override
        public String rawString() {
            return "<html-template-decorator-section name=\"" + this.name + "\"/>";
        }
    }
}
