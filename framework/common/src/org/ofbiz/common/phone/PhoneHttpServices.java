package org.ofbiz.common.phone;

import org.ofbiz.base.util.Debug;
import org.ofbiz.base.util.HttpClient;
import org.ofbiz.base.util.HttpClientException;
import org.ofbiz.base.util.UtilXml;
import org.ofbiz.service.DispatchContext;
import org.ofbiz.service.GenericServiceException;
import org.ofbiz.service.ServiceUtil;
import org.ofbiz.widget.fo.FoScreenRenderer;
import org.ofbiz.widget.html.HtmlScreenRenderer;
import org.w3c.dom.Document;
import org.w3c.dom.Element;

import java.io.IOException;
import java.util.Map;

/**
 * desc:通过调用Http post 的方式调用服务
 * Created by Administrator on 2014/12/25.
 */
public class PhoneHttpServices {

    public final static String module = PhoneSoapServices.class.getName();

    protected static final HtmlScreenRenderer htmlScreenRenderer = new HtmlScreenRenderer();
    protected static final FoScreenRenderer foScreenRenderer = new FoScreenRenderer();
    public static final String resource = "CommonUiLabels";

    /**
     * http post方式 传递的xml字符串
     * @param ctx
     * @param context
     * @return
     * @throws GenericServiceException
     */

    public static Map<String,Object> httpPostStr(DispatchContext ctx, Map<String, ? extends Object> context) throws GenericServiceException {

        String phoneId = (String) context.get("phoneId");
        String messageBody = (String) context.get("messageBody");
        String url = (String) context.get("url");

        HttpClient http = new HttpClient(url);
        http.setContentType("text/xml; charset=utf-8");
        http.setHeader("SOAPAction", "http://tempuri.org/senddx");
        String message = "<?xml version=\"1.0\" encoding=\"utf-8\"?>\n" +
                "<soap:Envelope xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\" xmlns:xsd=\"http://www.w3.org/2001/XMLSchema\" xmlns:soap=\"http://schemas.xmlsoap.org/soap/envelope/\">\n" +
                "  <soap:Body>\n" +
                "    <senddx xmlns=\"http://tempuri.org/\">\n" +
                "      <sjh>"+phoneId+"</sjh>\n" +
                "      <sjnr>"+messageBody+"</sjnr>\n" +
                "    </senddx>\n" +
                "  </soap:Body>\n" +
                "</soap:Envelope>";
        String postResult = null;
        try {
            postResult = http.post(message);
        } catch (HttpClientException e) {
            throw new GenericServiceException("Problems invoking HTTP request", e);
        }

        Map<String, Object> result = null;
        try {
            Document doc =  UtilXml.readXmlDocument(postResult);
            String value = doc.getElementsByTagName("senddxResult").item(0).getTextContent();
            result = ServiceUtil.returnSuccess();
            result.put("senddxResult",value);
        } catch (Exception e) {
            result = ServiceUtil.returnFailure(e.getMessage());
//            throw new GenericServiceException("Problems deserializing result.", e);

        }

        return result;
    }


    /**
     * http post方式 传递的xml字符串
     * @param ctx
     * @param context
     * @return
     * @throws GenericServiceException
     */

    public static Map<String,Object> httpPostXml(DispatchContext ctx, Map<String, ? extends Object> context) throws GenericServiceException {

        String phoneId = (String) context.get("phoneId");
        String messageBody = (String) context.get("messageBody");
        String url = (String) context.get("url");


        HttpClient http = new HttpClient(url);
        http.setContentType("text/xml; charset=utf-8");
        http.setHeader("SOAPAction", "http://tempuri.org/senddx");


        Document envelopeDoc = UtilXml.makeEmptyXmlDocument(null);



        Element envelopeElement = envelopeDoc.createElementNS("http://schemas.xmlsoap.org/soap/envelope/","soap:Envelope");
        envelopeDoc.appendChild(envelopeElement);


        // XML request header
        Element body =  UtilXml.addChildElement(envelopeElement, "soap:Body", envelopeDoc);

        // Example, setting up required and optional XML document
        // elements: the pickup type


        Element dxElement = envelopeDoc.createElementNS("http://tempuri.org/", "senddx");
        body.appendChild(dxElement);

        UtilXml.addChildElementValue(dxElement,"sjh",phoneId,envelopeDoc);
        UtilXml.addChildElementValue(dxElement,"sjnr",messageBody,envelopeDoc);

        // Use OFBiz UtilXml utility to create the XML document
        String requestStr = null;
        try {
            requestStr = UtilXml.writeXmlDocument(envelopeDoc);
        }
        catch (IOException e) {
            String ioeErrMsg =
                    "Error writing RatingServiceSelectionRequest XML Document"
                            + " to a String: " + e.toString();
            Debug.logError(e, ioeErrMsg, module);
            return ServiceUtil.returnFailure(ioeErrMsg);
        }

        String postResult = null;
        try {
            postResult = http.post(requestStr);
        } catch (HttpClientException e) {
            throw new GenericServiceException("Problems invoking HTTP request", e);
        }

        Map<String, Object> result = null;
        try {
            Document doc =  UtilXml.readXmlDocument(postResult);
            String value = doc.getElementsByTagName("senddxResult").item(0).getTextContent();
            result = ServiceUtil.returnSuccess();
            result.put("senddxResult",value);
        } catch (Exception e) {
            result = ServiceUtil.returnFailure(e.getMessage());
//            throw new GenericServiceException("Problems deserializing result.", e);

        }

        return result;
    }
}
