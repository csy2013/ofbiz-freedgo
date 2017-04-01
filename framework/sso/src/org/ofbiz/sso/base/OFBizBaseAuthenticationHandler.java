/*******************************************************************************
 * Licensed to the Apache Software Foundation (ASF) under one
 * or more contributor license agreements.  See the NOTICE file
 * distributed with this work for additional information
 * regarding copyright ownership.  The ASF licenses this file
 * to you under the Apache License, Version 2.0 (the
 * "License"); you may not use this file except in compliance
 * with the License.  You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing,
 * software distributed under the License is distributed on an
 * "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
 * KIND, either express or implied.  See the License for the
 * specific language governing permissions and limitations
 * under the License.
 *******************************************************************************/

package org.ofbiz.sso.base;

import org.ofbiz.entity.Delegator;
import org.ofbiz.entity.GenericEntityException;
import org.ofbiz.entity.GenericValue;
import org.ofbiz.service.LocalDispatcher;
import org.ofbiz.sso.commons.AbstractOFBizAuthenticationHandler;
import org.w3c.dom.Element;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

/**
 * The OFBiz LDAP Authentication Handler.<p>
 *
 * The ACL of a user is still controlled by OFBiz.
 *
 */
public final class OFBizBaseAuthenticationHandler extends AbstractOFBizAuthenticationHandler {

    /**
     * Public constructor, initializes some required member variables.<p>
     */
    public OFBizBaseAuthenticationHandler() {

    }
/*
    @Override
    public SearchResult getldapSearchResult(String username, String password, Element rootElement, boolean bindRequired) throws NamingException {
        DirContext ctx = null;
        SearchResult result = null;
        String ldapURL = UtilXml.childElementValue(rootElement, "URL", "ldap://localhost:389");
        String authenType = UtilXml.childElementValue(rootElement, "AuthenType", "simple");
        String baseDN = UtilXml.childElementValue(rootElement, "BaseDN");
        Hashtable<String, String> env = new Hashtable<String, String>();
        env.put(Context.INITIAL_CONTEXT_FACTORY,"com.sun.jndi.ldap.LdapCtxFactory");
        env.put(Context.PROVIDER_URL, ldapURL);
        env.put(Context.SECURITY_AUTHENTICATION, "none");
        try {
            ctx = new InitialDirContext(env);
            SearchControls controls = new SearchControls();
            // ldap search timeout
            controls.setTimeLimit(1000); //TODO maybe properties...
            // ldap search count
            controls.setCountLimit(2);  //TODO maybe properties...
            // ldap search scope
            String sub = UtilXml.childElementValue(rootElement, "Scope", "sub").toLowerCase().trim();
            if (sub.equals("sub")) {
                controls.setSearchScope(SearchControls.SUBTREE_SCOPE);
            } else if (sub.equals("one")) {
                controls.setSearchScope(SearchControls.ONELEVEL_SCOPE);
            } else {
                controls.setSearchScope(SearchControls.OBJECT_SCOPE);
            }
            String filter = UtilXml.childElementValue(rootElement, "Filter", "(objectclass=*)");
            String attribute = UtilXml.childElementValue(rootElement, "Attribute", "uid=%u");
            attribute = LdapUtils.getFilterWithValues(attribute, username);
            NamingEnumeration<SearchResult> answer = ctx.search(baseDN,
                    // Filter expression
                    "(&(" + filter + ") (" + attribute +"))",
                    controls);
            if (answer.hasMoreElements()) {
                result = answer.next();
                if (bindRequired) {
                    env.put(Context.SECURITY_AUTHENTICATION, authenType);
                    // specify the username
                    String userDN = result.getName() + "," + baseDN;
                    env.put(Context.SECURITY_PRINCIPAL, userDN);
                    // specify the password
                    env.put(Context.SECURITY_CREDENTIALS, password);
                    ctx = new InitialDirContext(env);
                }
            }
        } catch (NamingException e) {
            // No ldap service found, or cannot login.
            throw new NamingException(e.getLocalizedMessage());
        }

        return result;
    }*/

    @Override
    public GenericValue getBaseSearchResult(String username, String password, Element rootElement, boolean bindRequired, HttpServletRequest request) throws  GenericEntityException {
        LocalDispatcher dispatcher =(LocalDispatcher) request.getAttribute( "dispatcher");
        Delegator delegator =dispatcher.getDelegator();
        GenericValue userTryToLogin= delegator.findOne( "UserLogin" , false ,"userLoginId" , username);
        if(userTryToLogin!=null) {
            String currentPas = userTryToLogin.getString("currentPassword");
            HttpSession session = request.getSession();
            session.setAttribute("USERNAME", username);
            if (currentPas != null && (!currentPas.equals(""))) {
                session.setAttribute("PASSWORD", currentPas);
            } else {
                session.setAttribute("PASSWORD", password);
            }
        }
        return  userTryToLogin;
    }


}
