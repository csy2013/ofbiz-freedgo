package org.ofbiz.common.phone;

import org.ofbiz.base.util.UtilXml;
import org.ofbiz.service.DispatchContext;
import org.ofbiz.service.ServiceUtil;
import org.ofbiz.widget.fo.FoScreenRenderer;
import org.ofbiz.widget.html.HtmlScreenRenderer;
import org.w3c.dom.Document;

import javax.xml.soap.*;
import javax.xml.transform.Source;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.stream.StreamResult;
import java.io.StringWriter;
import java.util.Map;

/**
 * Created by Administrator on 2014/12/25.
 * desc :模拟调用SOAP webservice
 */
public class PhoneSoapServices {

    public final static String module = PhoneSoapServices.class.getName();

    protected static final HtmlScreenRenderer htmlScreenRenderer = new HtmlScreenRenderer();
    protected static final FoScreenRenderer foScreenRenderer = new FoScreenRenderer();
    public static final String resource = "CommonUiLabels";

    public static Map<String,Object> sendSoapMessage(DispatchContext ctx, Map<String, ? extends Object> context) {
        Map<String, Object> result = null;
        try {
            String phoneId = (String) context.get("phoneId");
            String messageBody = (String) context.get("messageBody");
            String url = (String) context.get("url");

            // Create SOAP Connection
            SOAPConnectionFactory soapConnectionFactory = SOAPConnectionFactory.newInstance();
            SOAPConnection soapConnection = soapConnectionFactory.createConnection();
            // Send SOAP Message to SOAP Server

            SOAPMessage soapResponse = soapConnection.call(createSOAPRequest(phoneId,messageBody), url);

            // Process the SOAP Response
            String postResult = printSOAPResponse(soapResponse);

            soapConnection.close();


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


    private static SOAPMessage createSOAPRequest(String phoneId, String messageBody) throws Exception {

        MessageFactory messageFactory = MessageFactory.newInstance();

        SOAPMessage soapMessage = messageFactory.createMessage();
        SOAPPart soapPart = soapMessage.getSOAPPart();



        // SOAP Envelope
        SOAPEnvelope envelope = soapPart.getEnvelope();
        SOAPHeader soapHeader = envelope.getHeader();
        soapHeader.setAttribute("SOAPAction", "http://tempuri.org/senddx");

//        envelope.addNamespaceDeclaration("xsi", "http://www.w3.org/1999/XMLSchema-instance");
//
        SOAPBody soapBody = envelope.getBody();


        SOAPElement soapBodyElem = soapBody.addChildElement("senddx");
        soapBodyElem.addNamespaceDeclaration("","http://tempuri.org/");

        SOAPElement soapBodyElem1 = soapBodyElem.addChildElement("sjh");
        SOAPElement soapBodyElem2 = soapBodyElem.addChildElement("sjnr");

        soapBodyElem1.addTextNode(phoneId);
        soapBodyElem2.addTextNode(messageBody);



        soapPart.addMimeHeader("SOAPAction", "http://tempuri.org/senddx");

        soapPart.addMimeHeader("Content-Type", "text/xml; charset=utf-8");
        soapPart.addMimeHeader("Connection", "keep-alive");



        soapMessage.saveChanges();
        /* Print the request message */
        System.out.print("Request SOAP Message = ");
        soapMessage.writeTo(System.out);
        System.out.println();
        return soapMessage;
    }
    /**
     * Method used to print the SOAP Response
     */
    private static String printSOAPResponse(SOAPMessage soapResponse) throws Exception {
        TransformerFactory transformerFactory = TransformerFactory.newInstance();
        Transformer transformer = transformerFactory.newTransformer();
        Source sourceContent = soapResponse.getSOAPPart().getContent();
        System.out.print("\nResponse SOAP Message = ");
        StringWriter writer = new StringWriter();
        StreamResult result = new StreamResult(writer);
        transformer.transform(sourceContent, result);
        String output = writer.toString();
        System.out.println(output);
        return output;


    }





}
