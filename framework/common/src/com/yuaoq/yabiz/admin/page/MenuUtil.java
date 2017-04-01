package com.yuaoq.yabiz.admin.page;


import javolution.util.FastList;
import org.ofbiz.base.util.StringUtil;
import org.ofbiz.base.util.UtilURL;
import org.ofbiz.base.util.UtilXml;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.xml.sax.SAXException;

import javax.xml.parsers.ParserConfigurationException;
import java.io.IOException;
import java.net.URL;
import java.util.List;

/**
 * Created by csy on 15/6/19.
 */
public class MenuUtil {


    public static String getMenuLocation(String appId) throws IOException, ParserConfigurationException, SAXException {
        URL url = UtilURL.fromResource("Menus.xml");
        Document doc = UtilXml.readXmlDocument(url.openStream(), "");
        List elementList = UtilXml.childElementList(doc.getDocumentElement(),"app");
        for (int i = 0; i < elementList.size(); i++) {
            Element element = (Element) elementList.get(i);
            String id = element.getAttribute("id");
            if(id.equals(appId)){
                return  UtilXml.childElementValue(element,"location");

            }
        }
        return null;

    }

    public static String getMenuName(String appId) throws IOException, ParserConfigurationException, SAXException {
        URL url = UtilURL.fromResource("Menus.xml");
        Document doc = UtilXml.readXmlDocument(url.openStream(), "");
        List elementList = UtilXml.childElementList(doc.getDocumentElement(),"app");
        for (int i = 0; i < elementList.size(); i++) {
            Element element = (Element) elementList.get(i);
            String id = element.getAttribute("id");
            if(id.equals(appId)){
                return  UtilXml.childElementValue(element,"name");

            }
        }
        return null;
    }

    public static List<String> getMenuLabel(String appId) throws IOException, ParserConfigurationException, SAXException {
        URL url = UtilURL.fromResource("Menus.xml");
        Document doc = UtilXml.readXmlDocument(url.openStream(), "");
        List elementList = UtilXml.childElementList(doc.getDocumentElement(), "app");
        for (int i = 0; i < elementList.size(); i++) {
            Element element = (Element) elementList.get(i);
            String id = element.getAttribute("id");
            if (id.equals(appId)) {
                String uiLabels =  UtilXml.childElementValue(element, "uiLabel");
                return StringUtil.split(uiLabels, ",");

            }
        }
        return null;
    }

    public static String getMenuStyle(String appId) throws IOException, ParserConfigurationException, SAXException {
        URL url = UtilURL.fromResource("Menus.xml");
        Document doc = UtilXml.readXmlDocument(url.openStream(), "");
        List elementList = UtilXml.childElementList(doc.getDocumentElement(), "app");
        for (int i = 0; i < elementList.size(); i++) {
            Element element = (Element) elementList.get(i);
            String id = element.getAttribute("id");
            if (id.equals(appId)) {
                return UtilXml.childElementValue(element, "iconStyle");

            }
        }
        return null;
    }

    public static List<WebInfo> getWebInfos()throws IOException, ParserConfigurationException, SAXException {
        URL url = UtilURL.fromResource("Menus.xml");
        Document doc = UtilXml.readXmlDocument(url.openStream(), "");
        List elementList = UtilXml.childElementList(doc.getDocumentElement(), "app");
        List list = FastList.newInstance();
        for (int i = 0; i < elementList.size(); i++) {
            Element element = (Element) elementList.get(i);
            String id = element.getAttribute("id");
            String location = UtilXml.childElementValue(element, "location");
            String uiLabel =UtilXml.childElementValue(element, "uiLabel");
            String name =UtilXml.childElementValue(element, "name");
            String iconStyle =UtilXml.childElementValue(element, "iconStyle");
            String basePermission =UtilXml.childElementValue(element, "basePermission");
            String contextRoot =UtilXml.childElementValue(element, "contextRoot");
            String title =UtilXml.childElementValue(element, "title");
            WebInfo webInfo = new WebInfo(id,location,name,uiLabel,iconStyle,contextRoot,basePermission,title);
            list.add(webInfo);
        }
        return list;
    }

}
