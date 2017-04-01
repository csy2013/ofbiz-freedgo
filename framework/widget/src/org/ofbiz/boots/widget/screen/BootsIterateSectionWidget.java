package org.ofbiz.boots.widget.screen;
import org.ofbiz.base.util.UtilXml;
import org.ofbiz.widget.screen.IterateSectionWidget;
import org.w3c.dom.Element;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2015/1/15.
 */
public class BootsIterateSectionWidget extends IterateSectionWidget {
    public BootsIterateSectionWidget(BootsModelScreen modelScreen, Element iterateSectionElement) {
        super(modelScreen, iterateSectionElement);
        sectionList = new ArrayList<Section>();
        List<? extends Element> childElementList = UtilXml.childElementList(iterateSectionElement);
        for (Element sectionElement: childElementList) {
            BootsModelScreenWidget.Section section = new BootsModelScreenWidget.Section(modelScreen, sectionElement);
            sectionList.add(section);
        }
    }
}
