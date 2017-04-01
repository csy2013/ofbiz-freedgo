package org.ofbiz.amaze.widget.screen;

import org.ofbiz.base.util.UtilXml;
import org.ofbiz.widget.screen.IterateSectionWidget;
import org.w3c.dom.Element;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2015/1/15.
 */
public class AmazeIterateSectionWidget extends IterateSectionWidget {
    public AmazeIterateSectionWidget(AmazeModelScreen modelScreen, Element iterateSectionElement) {
        super(modelScreen, iterateSectionElement);
        sectionList = new ArrayList<Section>();
        List<? extends Element> childElementList = UtilXml.childElementList(iterateSectionElement);
        for (Element sectionElement: childElementList) {
            AmazeModelScreenWidget.Section section = new AmazeModelScreenWidget.Section(modelScreen, sectionElement);
            sectionList.add(section);
        }
    }
}
