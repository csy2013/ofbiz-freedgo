/*******************************************************************************
 * Licensed to the Apache Software Foundation (ASF) under one
 * or more contributor license agreements.  See the NOTICE file
 * distributed with this work for additional information
 * regarding copyright ownership.  The ASF licenses this file
 * to you under the Apache License, Version 2.0 (the
 * "License"); you may not use this file except in compliance
 * with the License.  You may obtain a copy of the License at
 * <p/>
 * http://www.apache.org/licenses/LICENSE-2.0
 * <p/>
 * Unless required by applicable law or agreed to in writing,
 * software distributed under the License is distributed on an
 * "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
 * KIND, either express or implied.  See the License for the
 * specific language governing permissions and limitations
 * under the License.
 *******************************************************************************/

package org.ofbiz.sso.cas;

import org.ofbiz.base.util.UtilXml;
import org.ofbiz.entity.GenericEntityException;
import org.ofbiz.entity.GenericValue;
import org.ofbiz.sso.cas.ssl.client.AnyHostnameVerifier;
import org.ofbiz.sso.cas.ssl.client.HttpsURLConnectionFactory;
import org.ofbiz.sso.cas.ssl.client.util.CommonUtils;
import org.ofbiz.sso.commons.AbstractOFBizAuthenticationHandler;
import org.ofbiz.sso.commons.InterfaceOFBizAuthenticationHandler;
import org.ofbiz.sso.util.XmlUtils;
import org.w3c.dom.Element;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.net.URL;
import java.net.URLEncoder;
import java.util.List;
import java.util.Properties;

/**
 * The OFBiz CAS-LDAP Authentication Handler.<p>
 *
 * The ACL of a user is still controlled by OFBiz.
 *
 */
public final class OFBizCasAuthenticationHandler extends AbstractOFBizAuthenticationHandler {

    public static final String PARAM_TICKET = "ticket";

    public static final String PARAM_SERVICE = "service";

    public static final String PARAM_RENEW = "renew";

    /**
     * Public constructor, initializes some required member variables.<p>
     */
    public OFBizCasAuthenticationHandler() {

    }


    @Override
    public String login(HttpServletRequest request, HttpServletResponse response, Element rootElement) throws Exception {

        String ticket = request.getParameter(PARAM_TICKET);
        String username = request.getParameter("USERNAME");
        String password = request.getParameter("PASSWORD");

        String casUrl = UtilXml.childElementValue(rootElement, "CasUrl", "https://localhost:9443/cas");
        String casValidateUrL = UtilXml.childElementValue(rootElement, "CasValidateUrL", "https://localhost:9443/cas");
        String yaCasTcpAddr =  UtilXml.childElementValue(rootElement, "YaCasTcpAddr", "YACAS_PORT_8443_TCP_ADDR");
        String yaCasTcpPort =  UtilXml.childElementValue(rootElement, "YaCasTcpPort", "YACAS_PORT_8443_TCP_PORT");
        String yaCasUrl = System.getenv(yaCasTcpAddr);
        String yaCasPort= System.getenv(yaCasTcpPort);
        if(yaCasUrl!=null&&!yaCasUrl.equals("")&&yaCasPort!=null&&!yaCasPort.equals("")) {
            casValidateUrL = "https://"+yaCasUrl+":"+yaCasPort+"/cas";
        }
        System.out.println("casValidateUrL = " + casValidateUrL);
        System.out.println("yaCasUrl = " + yaCasUrl);
        String loginUri = UtilXml.childElementValue(rootElement, "CasLoginUri", "/login");
        String validateUri = UtilXml.childElementValue(rootElement, "CasValidateUri", "/validate");
        String serviceName = UtilXml.childElementValue(rootElement, "ServiceName", "yuaoq.com");
        String whiteHosts = UtilXml.childElementValue(rootElement, "allowHosts", "localhost");
        String requestURI = request.getRequestURI();
        if (requestURI.indexOf("?") != -1) requestURI += "&locale=" + request.getLocale().toString();
        else requestURI += "?locale=" + request.getLocale().toString();
        String url = request.getScheme() + "://" + serviceName + requestURI;
        url = URLEncoder.encode(url, "UTF-8");
        boolean casLoggedIn = false;
        if (ticket == null) {
            // forward the login page to CAS login page
            response.sendRedirect(casUrl + loginUri + "?" + PARAM_SERVICE + "=" + url);
        } else {
            // there's a ticket, we should validate the ticket
            URL validateURL = new URL(casValidateUrL + validateUri + "?" + PARAM_TICKET + "=" + ticket + "&" + PARAM_SERVICE + "=" + url);

            HttpsURLConnectionFactory httpsURLConnectionFactory = new HttpsURLConnectionFactory();

//            httpsURLConnectionFactory.setHostnameVerifier(new WhitelistHostnameVerifier(whiteHosts));
            httpsURLConnectionFactory.setHostnameVerifier(new AnyHostnameVerifier());
            httpsURLConnectionFactory.setSSLConfiguration(getSSLConfig(rootElement));
            httpsURLConnectionFactory.ignorSsl();// 此处处理信任所有的SSL证书
//            SSLUtil.ignoreSsl();
            String responseStr = CommonUtils.getResponseFromServer(validateURL, httpsURLConnectionFactory, "UTF-8");
            if (validateUri.equals("/validate")) {
                BufferedReader reader = new BufferedReader(new StringReader(responseStr));
                String result = reader.readLine();
                if (result != null && result.equals("yes")) {
                    username = reader.readLine();
                    casLoggedIn = true;
                } else {
                    response.sendRedirect(casUrl + loginUri + "?service=" + url);
                }
            } else if (validateUri.equals("/p3/serviceValidate")) {
                final String error = XmlUtils.getTextForElement(responseStr, "authenticationFailure");
                if (CommonUtils.isNotBlank(error)) {
                    return "error";
                }
                final String principal = XmlUtils.getTextForElement(responseStr, "user");
                username = principal;
                casLoggedIn = true;
                String partyId = XmlUtils.getTextForElements(responseStr, "partyId").get(0);
                String password1 = XmlUtils.getTextForElements(responseStr, "password").get(0);
                List emails = XmlUtils.getTextForElements(responseStr, "email");
                if (emails != null && (!emails.isEmpty())) {
                    String email = XmlUtils.getTextForElements(responseStr, "email").get(0);
                }
            }
        }

        if (casLoggedIn && username != null) {
            // as we cannot get the password user input in CAS login page, we use a random one
//            password = randomString();
            GenericValue result = getBaseSearchResult(username, password, rootElement, false, request);
            if (result != null) {
                return login(request, response, username, (String) result.get("currentPassword"), rootElement, result);
            }
        }
        return "error";
    }

    public static String randomString(int lo, int hi) {
        int n = rand(lo, hi);
        byte b[] = new byte[n];
        for (int i = 0; i < n; i++) {
            b[i] = (byte) rand('a', 'z');
        }
        return new String(b);
    }

    private static int rand(int lo, int hi) {
        java.util.Random rn = new java.util.Random();
        int n = hi - lo + 1;
        int i = rn.nextInt() % n;
        if (i < 0)
            i = -i;
        return lo + i;
    }

    public static String randomString() {
        return randomString(5, 15);
    }

    @Override
    public String logout(HttpServletRequest request, HttpServletResponse response, Element rootElement) {
        String casUrl = UtilXml.childElementValue(rootElement, "CasUrl", "https://localhost:8443/cas");
        String logoutUri = UtilXml.childElementValue(rootElement, "CasLogoutUri", "/logout");
        try {
            response.sendRedirect(casUrl + logoutUri);
        } catch (UnsupportedEncodingException e) {
        } catch (IOException e) {
        }
        return "success";
    }


    @Override
    public GenericValue getBaseSearchResult(String username, String password,
                                            Element rootElement, boolean bindRequired, HttpServletRequest request) throws GenericEntityException, IllegalAccessException, InstantiationException, ClassNotFoundException {

        String className = UtilXml.childElementValue(rootElement, "CasBaseHandler", "OFBizLdapAuthenticationHandler");
        try {
            Class<?> handlerClass = Class.forName(className);
            InterfaceOFBizAuthenticationHandler casLdapHandler = (InterfaceOFBizAuthenticationHandler) handlerClass.newInstance();
            return casLdapHandler.getBaseSearchResult(username, password, rootElement, bindRequired, request);
        } catch (GenericEntityException e) {
            e.printStackTrace();
            throw e;
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
            throw e;
        } catch (InstantiationException e) {
            e.printStackTrace();
            throw e;
        } catch (IllegalAccessException e) {
            e.printStackTrace();
            throw e;
        }
    }

    /**
     * An HTTP WebEvent handler that checks to see is a userLogin is logged out.
     * If yes, the user is forwarded to the login page.
     *
     * @param request The HTTP request object for the current JSP or Servlet request.
     * @param response The HTTP response object for the current JSP or Servlet request.
     * @param rootElement Element root element of ldap config file
     * @return true if the user has logged out from ldap; otherwise, false.
     */
    @Override
    public boolean hasBaseLoggedOut(HttpServletRequest request, HttpServletResponse response, Element rootElement) {
        String casTGC = UtilXml.childElementValue(rootElement, "CasTGTCookieName", "CASTGC");
        String casUrl = UtilXml.childElementValue(rootElement, "CasUrl", "https://localhost:8443/cas");
        Cookie[] cookies = request.getCookies();
        if (cookies == null) return true;
        for (int i = 0; i < cookies.length; i++) {
            Cookie cookie = cookies[i];
            if (cookie.getName().equals(casTGC) && casUrl.indexOf(cookie.getDomain()) > -1) {
                return false;
            }
        }
        return true;
    }

    protected Properties getSSLConfig(Element rootElement) {
        Properties properties = new Properties();
        InputStream configFileIS = null;

        try {


            String keyStorePath = UtilXml.childElementValue(rootElement, "keyStorePath", "");
            String keyStroPass = UtilXml.childElementValue(rootElement, "keyStorePass", "changeit");
            String certificate = UtilXml.childElementValue(rootElement, "certificatePassword", "changeit");
            String keyStoreType = UtilXml.childElementValue(rootElement, "keyStoreType", "JKS");
            properties.put("keyStorePath", keyStorePath);
            properties.put("keyStorePass", keyStroPass);
            properties.put("certificatePassword", certificate);
            properties.put("keyStoreType", keyStoreType);

        } catch (Exception e) {

        } finally {
            if (configFileIS != null) {
                try {
                    configFileIS.close();
                } catch (IOException e) {
                }
            }
        }
        return properties;
    }
}
