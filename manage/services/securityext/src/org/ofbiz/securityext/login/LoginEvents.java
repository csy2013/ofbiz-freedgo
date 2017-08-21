/*******************************************************************************
 * Licensed to the Apache Software Foundation (ASF) under one
 * or more contributor license agreements.  See the NOTICE file
 * distributed with this work for additional information
 * regarding copyright ownership.  The ASF licenses this file
 * to you under the Apache License, Version 2.0 (the
 * "License"); you may not use this file except in compliance
 * with the License.  You may obtain a copy of the License at
 * <p>
 * http://www.apache.org/licenses/LICENSE-2.0
 * <p>
 * Unless required by applicable law or agreed to in writing,
 * software distributed under the License is distributed on an
 * "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
 * KIND, either express or implied.  See the License for the
 * specific language governing permissions and limitations
 * under the License.
 *******************************************************************************/

package org.ofbiz.securityext.login;

import javolution.util.FastMap;
import org.apache.commons.lang.RandomStringUtils;
import org.ofbiz.base.crypto.HashCrypt;
import org.ofbiz.base.util.*;
import org.ofbiz.base.util.string.FlexibleStringExpander;
import org.ofbiz.common.login.LoginServices;
import org.ofbiz.entity.Delegator;
import org.ofbiz.entity.GenericEntityException;
import org.ofbiz.entity.GenericValue;
import org.ofbiz.entity.util.EntityUtilProperties;
import org.ofbiz.product.product.ProductEvents;
import org.ofbiz.product.store.ProductStoreWorker;
import org.ofbiz.service.GenericServiceException;
import org.ofbiz.service.LocalDispatcher;
import org.ofbiz.service.ModelService;
import org.ofbiz.webapp.control.LoginWorker;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.util.HashMap;
import java.util.Map;


/**
 * LoginEvents - Events for UserLogin and Security handling.
 */
public class LoginEvents {

    public static final String module = LoginEvents.class.getName();
    public static final String resource = "SecurityextUiLabels";

    /**
     * Save USERNAME and PASSWORD for use by auth pages even if we start in non-auth pages.
     *
     * @param request  The HTTP request object for the current JSP or Servlet request.
     * @param response The HTTP response object for the current JSP or Servlet request.
     * @return String
     */
    public static String saveEntryParams(HttpServletRequest request, HttpServletResponse response) {
        GenericValue userLogin = (GenericValue) request.getSession().getAttribute("userLogin");
        HttpSession session = request.getSession();

        // save entry login parameters if we don't have a valid login object
        if (userLogin == null) {

            String username = request.getParameter("USERNAME");
            String password = request.getParameter("PASSWORD");

            if ((username != null) && ("true".equalsIgnoreCase(UtilProperties.getPropertyValue("security.properties", "username.lowercase")))) {
                username = username.toLowerCase();
            }
            if ((password != null) && ("true".equalsIgnoreCase(UtilProperties.getPropertyValue("security.properties", "password.lowercase")))) {
                password = password.toLowerCase();
            }

            // save parameters into the session - so they can be used later, if needed
            if (username != null) session.setAttribute("USERNAME", username);
            if (password != null) session.setAttribute("PASSWORD", password);

        } else {
            // if the login object is valid, remove attributes
            session.removeAttribute("USERNAME");
            session.removeAttribute("PASSWORD");
        }

        return "success";
    }

    /**
     * The user forgot his/her password.  This will call showPasswordHint, emailPassword or simply returns "success" in case
     * no operation has been specified.
     *
     * @param request  The HTTPRequest object for the current request
     * @param response The HTTPResponse object for the current request
     * @return String specifying the exit status of this event
     */
    public static String forgotPassword(HttpServletRequest request, HttpServletResponse response) {
        if ((UtilValidate.isNotEmpty(request.getParameter("GET_PASSWORD_HINT"))) || (UtilValidate.isNotEmpty(request.getParameter("GET_PASSWORD_HINT.x")))) {
            return showPasswordHint(request, response);
        } else if ((UtilValidate.isNotEmpty(request.getParameter("EMAIL_PASSWORD"))) || (UtilValidate.isNotEmpty(request.getParameter("EMAIL_PASSWORD.x")))) {
            return emailPassword(request, response);
        } else if ((UtilValidate.isNotEmpty(request.getParameter("MOBILE_PASSORD"))) || (UtilValidate.isNotEmpty(request.getParameter("MOBILE_PASSWORD.x")))) {
            return mobilePassword(request,response);
        } else {
            return "success";
        }
    }

    /**
     * Show the password hint for the userLoginId specified in the request object.
     *
     * @param request  The HTTPRequest object for the current request
     * @param response The HTTPResponse object for the current request
     * @return String specifying the exit status of this event
     */
    public static String showPasswordHint(HttpServletRequest request, HttpServletResponse response) {
        Delegator delegator = (Delegator) request.getAttribute("delegator");

        String userLoginId = request.getParameter("USERNAME");
        String errMsg = null;

        if ((userLoginId != null) && ("true".equals(UtilProperties.getPropertyValue("security.properties", "username.lowercase")))) {
            userLoginId = userLoginId.toLowerCase();
        }

        if (!UtilValidate.isNotEmpty(userLoginId)) {
            // the password was incomplete
            errMsg = UtilProperties.getMessage(resource, "loginevents.username_was_empty_reenter", UtilHttp.getLocale(request));
            request.setAttribute("_ERROR_MESSAGE_", errMsg);
            return "error";
        }

        GenericValue supposedUserLogin = null;

        try {
            supposedUserLogin = delegator.findOne("UserLogin", false, "userLoginId", userLoginId);
        } catch (GenericEntityException gee) {
            Debug.logWarning(gee, "", module);
        }
        if (supposedUserLogin == null) {
            // the Username was not found
            errMsg = UtilProperties.getMessage(resource, "loginevents.username_not_found_reenter", UtilHttp.getLocale(request));
            request.setAttribute("_ERROR_MESSAGE_", errMsg);
            return "error";
        }

        String passwordHint = supposedUserLogin.getString("passwordHint");

        if (!UtilValidate.isNotEmpty(passwordHint)) {
            // the Username was not found
            errMsg = UtilProperties.getMessage(resource, "loginevents.no_password_hint_specified_try_password_emailed", UtilHttp.getLocale(request));
            request.setAttribute("_ERROR_MESSAGE_", errMsg);
            return "error";
        }

        Map<String, String> messageMap = UtilMisc.toMap("passwordHint", passwordHint);
        errMsg = UtilProperties.getMessage(resource, "loginevents.password_hint_is", messageMap, UtilHttp.getLocale(request));
        request.setAttribute("_EVENT_MESSAGE_", errMsg);
        return "success";
    }

    /**
     * Email the password for the userLoginId specified in the request object.
     *
     * @param request  The HTTPRequest object for the current request
     * @param response The HTTPResponse object for the current request
     * @return String specifying the exit status of this event
     */
    public static String emailPassword(HttpServletRequest request, HttpServletResponse response) {
        String defaultScreenLocation = "component://securityext/widget/EmailSecurityScreens.xml#PasswordEmail";

        Delegator delegator = (Delegator) request.getAttribute("delegator");
        LocalDispatcher dispatcher = (LocalDispatcher) request.getAttribute("dispatcher");
        String productStoreId = ProductStoreWorker.getProductStoreId(request);

        String errMsg = null;

        boolean useEncryption = "true".equals(UtilProperties.getPropertyValue("security.properties", "password.encrypt"));

        String userLoginId = request.getParameter("USERNAME");

        if ((userLoginId != null) && ("true".equals(UtilProperties.getPropertyValue("security.properties", "username.lowercase")))) {
            userLoginId = userLoginId.toLowerCase();
        }

        if (!UtilValidate.isNotEmpty(userLoginId)) {
            // the password was incomplete
            errMsg = UtilProperties.getMessage(resource, "loginevents.username_was_empty_reenter", UtilHttp.getLocale(request));
            request.setAttribute("_ERROR_MESSAGE_", errMsg);
            return "error";
        }

        GenericValue supposedUserLogin = null;
        String passwordToSend = null;

        try {
            supposedUserLogin = delegator.findOne("UserLogin", false, "userLoginId", userLoginId);
            if (supposedUserLogin == null) {
                // the Username was not found
                errMsg = UtilProperties.getMessage(resource, "loginevents.username_not_found_reenter", UtilHttp.getLocale(request));
                request.setAttribute("_ERROR_MESSAGE_", errMsg);
                return "error";
            }
            if (useEncryption) {
                // password encrypted, can't send, generate new password and email to user
                passwordToSend = RandomStringUtils.randomAlphanumeric(Integer.parseInt(UtilProperties.getPropertyValue("security", "password.length.min", "5")));
                supposedUserLogin.set("currentPassword", HashCrypt.cryptPassword(LoginServices.getHashType(), passwordToSend));
                supposedUserLogin.set("passwordHint", "Auto-Generated Password");
                if ("true".equals(UtilProperties.getPropertyValue("security.properties", "password.email_password.require_password_change"))) {
                    supposedUserLogin.set("requirePasswordChange", "Y");
                }
            } else {
                passwordToSend = supposedUserLogin.getString("currentPassword");
            }
        } catch (GenericEntityException e) {
            Debug.logWarning(e, "", module);
            Map<String, String> messageMap = UtilMisc.toMap("errorMessage", e.toString());
            errMsg = UtilProperties.getMessage(resource, "loginevents.error_accessing_password", messageMap, UtilHttp.getLocale(request));
            request.setAttribute("_ERROR_MESSAGE_", errMsg);
            return "error";
        }

        StringBuilder emails = new StringBuilder();
        GenericValue party = null;

        try {
            party = supposedUserLogin.getRelatedOne("Party");
        } catch (GenericEntityException e) {
            Debug.logWarning(e, "", module);
            party = null;
        }
        if (party != null) {
           /* Iterator<GenericValue> emailIter = UtilMisc.toIterator(ContactHelper.getContactMechByPurpose(party, "PRIMARY_EMAIL", false));
            while (emailIter != null && emailIter.hasNext()) {
                GenericValue email = emailIter.next();
                emails.append(emails.length() > 0 ? "," : "").append(email.getString("infoString"));
            }*/
            try {
                GenericValue person = delegator.findByPrimaryKey("Person", UtilMisc.toMap("partyId", party.get("partyId")));
                emails.append(person.get("email"));
            } catch (GenericEntityException e) {
                e.printStackTrace();
            }
        }

        if (!UtilValidate.isNotEmpty(emails.toString())) {
            // the Username was not found
            errMsg = UtilProperties.getMessage(resource, "loginevents.no_primary_email_address_set_contact_customer_service", UtilHttp.getLocale(request));
            request.setAttribute("_ERROR_MESSAGE_", errMsg);
            return "error";
        }

        // get the ProductStore email settings
        GenericValue productStoreEmail = null;
        try {
            productStoreEmail = delegator.findOne("ProductStoreEmailSetting", false, "productStoreId", productStoreId, "emailType", "PRDS_PWD_RETRIEVE");
        } catch (GenericEntityException e) {
            Debug.logError(e, "Problem getting ProductStoreEmailSetting", module);
        }

        String bodyScreenLocation = null;
        if (productStoreEmail != null) {
            bodyScreenLocation = productStoreEmail.getString("bodyScreenLocation");
        }
        if (UtilValidate.isEmpty(bodyScreenLocation)) {
            bodyScreenLocation = defaultScreenLocation;
        }

        // set the needed variables in new context
        Map<String, Object> bodyParameters = FastMap.newInstance();
        bodyParameters.put("useEncryption", Boolean.valueOf(useEncryption));
        bodyParameters.put("password", UtilFormatOut.checkNull(passwordToSend));
        bodyParameters.put("locale", UtilHttp.getLocale(request));
        bodyParameters.put("userLogin", supposedUserLogin);
        bodyParameters.put("productStoreId", productStoreId);

        Map<String, Object> serviceContext = FastMap.newInstance();
        serviceContext.put("bodyScreenUri", bodyScreenLocation);
        serviceContext.put("bodyParameters", bodyParameters);
        if (productStoreEmail != null) {
            serviceContext.put("subject", productStoreEmail.getString("subject"));
            serviceContext.put("sendFrom", productStoreEmail.get("fromAddress"));
            serviceContext.put("sendCc", productStoreEmail.get("ccAddress"));
            serviceContext.put("sendBcc", productStoreEmail.get("bccAddress"));
            serviceContext.put("contentType", productStoreEmail.get("contentType"));
        } else {
            GenericValue emailTemplateSetting = null;
            try {
                emailTemplateSetting = delegator.findOne("EmailTemplateSetting", true, "emailTemplateSettingId", "EMAIL_PASSWORD");
            } catch (GenericEntityException e) {
                Debug.logError(e, module);
            }
            if (emailTemplateSetting != null) {
                String subject = emailTemplateSetting.getString("subject");
                subject = FlexibleStringExpander.expandString(subject, UtilMisc.toMap("userLoginId", userLoginId));
                serviceContext.put("subject", subject);
                serviceContext.put("sendFrom", emailTemplateSetting.get("fromAddress"));
            } else {
                serviceContext.put("subject", UtilProperties.getMessage(resource, "loginservices.password_reminder_subject", UtilMisc.toMap("userLoginId", userLoginId), UtilHttp.getLocale(request)));
                serviceContext.put("sendFrom", EntityUtilProperties.getPropertyValue("general.properties", "defaultFromEmailAddress", delegator));
            }
        }
        serviceContext.put("sendTo", emails.toString());
        serviceContext.put("partyId", party.getString("partyId"));

        try {
            Map<String, Object> result = dispatcher.runSync("sendMailHiddenInLogFromScreen", serviceContext);
            if (ModelService.RESPOND_ERROR.equals(result.get(ModelService.RESPONSE_MESSAGE))) {
                Map<String, Object> messageMap = UtilMisc.toMap("errorMessage", result.get(ModelService.ERROR_MESSAGE));
                errMsg = UtilProperties.getMessage(resource, "loginevents.error_unable_email_password_contact_customer_service_errorwas", messageMap, UtilHttp.getLocale(request));
                request.setAttribute("_ERROR_MESSAGE_", errMsg);
                return "error";
            }
        } catch (GenericServiceException e) {
            Debug.logWarning(e, "", module);
            errMsg = UtilProperties.getMessage(resource, "loginevents.error_unable_email_password_contact_customer_service", UtilHttp.getLocale(request));
            request.setAttribute("_ERROR_MESSAGE_", errMsg);
            return "error";
        }
        // don't save password until after it has been sent
        if (useEncryption) {
            try {
                supposedUserLogin.store();
            } catch (GenericEntityException e) {
                Debug.logWarning(e, "", module);
                Map<String, String> messageMap = UtilMisc.toMap("errorMessage", e.toString());
                errMsg = UtilProperties.getMessage(resource, "loginevents.error_saving_new_password_email_not_correct_password", messageMap, UtilHttp.getLocale(request));
                request.setAttribute("_ERROR_MESSAGE_", errMsg);
                return "error";
            }
        }

        if (useEncryption) {
            errMsg = UtilProperties.getMessage(resource, "loginevents.new_password_createdandsent_check_email", UtilHttp.getLocale(request));
            request.setAttribute("_EVENT_MESSAGE_", errMsg);
        } else {
            errMsg = UtilProperties.getMessage(resource, "loginevents.new_password_sent_check_email", UtilHttp.getLocale(request));
            request.setAttribute("_EVENT_MESSAGE_", errMsg);
        }
        return "success";
    }

    public static String mobilePassword(HttpServletRequest request, HttpServletResponse response) {

        Delegator delegator = (Delegator) request.getAttribute("delegator");

        String errMsg = null;

        boolean useEncryption = "true".equals(UtilProperties.getPropertyValue("security.properties", "password.encrypt"));

        String userLoginId = request.getParameter("USERNAME");
        String newPassword = request.getParameter("newPassword");
        String newPasswordVerify = request.getParameter("newPasswordVerify");
        String checkCode = request.getParameter("CHECK_CODE");
        Boolean _PASSED_ = false;
        if (UtilValidate.isNotEmpty(checkCode)) {
            HttpSession session = request.getSession();
            Map<String, String> captchaCodeMap = (Map<String, String>) session.getAttribute("_PHONE_CODE_");
            if (UtilValidate.isNotEmpty(captchaCodeMap)) {
                String code = captchaCodeMap.get("check");
                if (code != null && code.equalsIgnoreCase(checkCode)) {
                    _PASSED_ = true;
                }
            }
        }
        if(!_PASSED_){
            errMsg = UtilProperties.getMessage(resource, "loginevents.username_was_empty_reenter", UtilHttp.getLocale(request));
            request.setAttribute("_ERROR_MESSAGE_", errMsg);
            return "error";
        }

        if ((userLoginId != null) && ("true".equals(UtilProperties.getPropertyValue("security.properties", "username.lowercase")))) {
            userLoginId = userLoginId.toLowerCase();
        }

        if (!UtilValidate.isNotEmpty(userLoginId)) {
            // the password was incomplete
            errMsg = UtilProperties.getMessage(resource, "loginevents.username_was_empty_reenter", UtilHttp.getLocale(request));
            request.setAttribute("_ERROR_MESSAGE_", errMsg);
            return "error";
        }

        if(newPassword==null || newPasswordVerify == null || (!newPassword.equals(newPasswordVerify))){
            errMsg = UtilProperties.getMessage(resource, "loginevents.password_did_not_match_verify_password", UtilHttp.getLocale(request));
            request.setAttribute("_ERROR_MESSAGE_", errMsg);
            return "error";
        }

        GenericValue supposedUserLogin = null;
        try {
            supposedUserLogin = delegator.findOne("UserLogin", false, "userLoginId", userLoginId);
            if (supposedUserLogin == null) {
                // the Username was not found
                errMsg = UtilProperties.getMessage(resource, "loginevents.username_not_found_reenter", UtilHttp.getLocale(request));
                request.setAttribute("_ERROR_MESSAGE_", errMsg);
                return "error";
            }
            if (useEncryption) {
                // password encrypted, can't send, generate new password and email to user
                supposedUserLogin.set("currentPassword", HashCrypt.cryptPassword(LoginServices.getHashType(), newPassword));
                supposedUserLogin.set("passwordHint", "Auto-Generated Password");
            } else {
                supposedUserLogin.set("currentPassword", newPassword);
            }
        } catch (GenericEntityException e) {
            Debug.logWarning(e, "", module);
            Map<String, String> messageMap = UtilMisc.toMap("errorMessage", e.toString());
            errMsg = UtilProperties.getMessage(resource, "loginevents.error_accessing_password", messageMap, UtilHttp.getLocale(request));
            request.setAttribute("_ERROR_MESSAGE_", errMsg);
            return "error";
        }
        // don't save password until after it has been sent
        if (useEncryption) {
            try {
                supposedUserLogin.store();
            } catch (GenericEntityException e) {
                Debug.logWarning(e, "", module);
                Map<String, String> messageMap = UtilMisc.toMap("errorMessage", e.toString());
                errMsg = UtilProperties.getMessage(resource, "loginevents.error_saving_new_password_email_not_correct_password", messageMap, UtilHttp.getLocale(request));
                request.setAttribute("_ERROR_MESSAGE_", errMsg);
                return "error";
            }
        }

        if (useEncryption) {
            errMsg = UtilProperties.getMessage(resource, "loginevents.new_password_created_success", UtilHttp.getLocale(request));
            request.setAttribute("_EVENT_MESSAGE_", errMsg);
        } else {
            errMsg = UtilProperties.getMessage(resource, "loginevents.new_password_created_success", UtilHttp.getLocale(request));
            request.setAttribute("_EVENT_MESSAGE_", errMsg);
        }
        //删除验证码
        HttpSession session = request.getSession();
        Map<String, String> captchaCodeMap = (Map<String, String>) session.getAttribute("_PHONE_CODE_");
        captchaCodeMap.remove("check");
        return "success";
    }

    public static String storeCheckLogin(HttpServletRequest request, HttpServletResponse response) {
        String responseString = LoginWorker.checkLogin(request, response);
        if ("error".equals(responseString)) {
            return responseString;
        }
        // if we are logged in okay, do the check store customer role
        return ProductEvents.checkStoreCustomerRole(request, response);
    }

    public static String storeLogin(HttpServletRequest request, HttpServletResponse response) {
        String responseString = LoginWorker.login(request, response);
        if (!"success".equals(responseString)) {
            return responseString;
        }
        // 如果是rememberMe 则在LoginWork里面autoLoginSet设置atuouserlogin

        if ("Y".equals(request.getParameter("rememberMe"))) {
            LoginWorker.autoLoginSet(request, response);
        }
        // if we logged in okay, do the check store customer role
        return ProductEvents.checkStoreCustomerRole(request, response);
    }
    /**
     * 注册时手机获取验证码  Add By Changsy 2015-4-3 09:23:00
     *
     * @param request
     * @param response
     * @return
     */
    public static String getMobileAuthCode(HttpServletRequest request, HttpServletResponse response) {

//        final char[] availableChars = UtilProperties.getPropertyValue("general.properties", "mobileAuthoCode.characters").toCharArray();
        final char[] availableChars = "0123456789abcdefghijklmnopqrstuvwxyz".toCharArray();
        String length = request.getParameter("l");
        if (UtilValidate.isEmpty(length)) length = "4";
        String validateCode = RandomStringUtils.random(Integer.parseInt(length), availableChars);
        String type = request.getParameter("type");
        
        if (UtilValidate.isEmpty(type)) type = "reg";
        HttpSession session = request.getSession();
        Map captchaCodeMap = (Map) session.getAttribute("_PHONE_CODE_");
        if (captchaCodeMap == null) {
            captchaCodeMap = new HashMap();
            //保存到session中
            session.setAttribute("_PHONE_CODE_", captchaCodeMap);
        }
        captchaCodeMap.put(type, validateCode);
        String phoneId = request.getParameter("phoneId");
        LocalDispatcher dispatcher = (LocalDispatcher) request.getAttribute("dispatcher");
        String errMsg;
        try {
            Map<String, Object> serviceContext = FastMap.newInstance();
            serviceContext.put("phoneId", phoneId);
            Map<String, Object> messageMap = UtilMisc.toMap("message", validateCode);
            String messageBody = UtilProperties.getMessage(resource, "loginevents.phone_register_phone_auth_code_messageBody", messageMap, UtilHttp.getLocale(request));
            serviceContext.put("messageBody", messageBody);
            serviceContext.put("messageType", "PHONE_REGISTER");
            serviceContext.put("code",validateCode);
            Map<String, Object> result = dispatcher.runSync("mobileMessageService", serviceContext);

            if (ModelService.RESPOND_ERROR.equals(result.get(ModelService.RESPONSE_MESSAGE))) {
                messageMap = UtilMisc.toMap("errorMessage", result.get(ModelService.ERROR_MESSAGE));
                errMsg = UtilProperties.getMessage(resource, "loginevents.error_unable_phone_password_contact_customer_service_errorwas", messageMap, UtilHttp.getLocale(request));
                request.setAttribute("_ERROR_MESSAGE_", errMsg);
                return "error";
            } else {
                for (Object obj : result.keySet()) {
                    Object val = result.get(obj);
                    request.setAttribute(obj.toString(), val);
                }
            }

        } catch (GenericServiceException e) {
            Debug.logWarning(e, "", module);
            errMsg = UtilProperties.getMessage(resource, "loginevents.error_unable_phone_password_contact_customer_service_errorwas", UtilHttp.getLocale(request));
            request.setAttribute("_ERROR_MESSAGE_", errMsg);
            return "error";
        }
        return "success";
        
    }
    
    
    /**
     * 检查手机注册验证码是否正确
     *
     * @param request
     * @param response
     * @return
     */
    public static String checkMobileCheckCode(HttpServletRequest request, HttpServletResponse response) {
        Boolean _PASSED_ = false;
        String checkCode = request.getParameter("CHECK_CODE");
        String type = request.getParameter("type");
        if (UtilValidate.isEmpty(type)) type = "reg";
        if (UtilValidate.isNotEmpty(checkCode)) {
            HttpSession session = request.getSession();
            Map<String, String> captchaCodeMap = (Map<String, String>) session.getAttribute("_PHONE_CODE_");
            if (UtilValidate.isNotEmpty(captchaCodeMap)) {
                String code = captchaCodeMap.get(type);
                if (code != null && code.equalsIgnoreCase(checkCode)) {
                    _PASSED_ = true;
                }
            }
            
        } else {
            _PASSED_ = false;
        }
        request.setAttribute("_PASSED_", _PASSED_);
        return "success";
    }
}
