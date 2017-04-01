package com.yuaoq.yabiz.oauth.auth;

import com.yuaoq.yabiz.oauth.auth.conf.AuthenticationConfiguration;
import com.yuaoq.yabiz.oauth.auth.result.AuthenticationResult;
import com.yuaoq.yabiz.oauth.auth.service.AuthenticationService;
import com.yuaoq.yabiz.oauth.auth.service.AuthenticationServiceHelper;
import com.yuaoq.yabiz.oauth.broker.RequestResponseBroker;
import org.apache.commons.lang3.StringUtils;

public class AuthenticationHelper {
    protected RequestResponseBroker<?, ?, ?> _broker;
    protected AuthenticationConfiguration _ac;
    
    
    public AuthenticationHelper(RequestResponseBroker<?, ?, ?> broker) {
        initialize(broker);
    }
    
    
    public boolean initialize(RequestResponseBroker<?, ?, ?> broker) {
        boolean success = false;
        
        _broker = broker;
        _ac = AuthenticationConfiguration.getInstance();
        
        if (_broker != null && _broker.initialized()) success = true;
        
        return success;
    }
    
    
    public AuthenticationResult processResults(AuthenticationResult ar) {
        // Were we successful in initiating the request.  This is not about _completing_, just initiating.
        if (ar == null || !ar.success()) {
            System.err.println(ar.printDiagnostics());
            return ar;
        }
        
        if (ar.hasRedirectURL()) {
            _broker.redirect(ar.getRedirectURL());
        } else {
            _broker.redirect(AuthenticationConfiguration.getInstance().getApplicationURL());
        }
        
        System.err.println(ar.printDiagnostics());
        return ar;
    }
    
    
    public AuthenticationResult handleLogin() {
        AuthenticationResult ar = null;
        
        // Ask the AuthenticationHelper to start the connection process.
        // If something went wrong, check the codes in the AuthentcationResults to figure out what to do.
        ar = connect();
        
        // Were we successful in initiating the request.  This is not about _completing_, just initiating.
        if (ar == null || !ar.success()) {
            System.err.println(ar.printDiagnostics());
            return ar;
        }
        
        if (ar.hasRedirectURL()) {
            _broker.redirect(ar.getRedirectURL());
        } else {
            _broker.redirect(AuthenticationConfiguration.getInstance().getApplicationURL());
        }
        
        System.err.println(ar.printDiagnostics());
        return ar;
    }
    
    
    public AuthenticationResult handleLogout() {
        AuthenticationResult ar = null;
        
        // Ask the AuthenticationHelper to logout the user.
        // If something went wrong, check the codes in the AuthentcationResults to figure out what to do.
        ar = disconnect();
        
        // Were we successful in initiating the request.  This is not about _completing_, just initiating.
        if (ar == null || !ar.success()) {
            System.err.println(ar.printDiagnostics());
            return ar;
        }
        
        if (ar.hasRedirectURL()) {
            _broker.redirect(ar.getRedirectURL());
        } else {
            _broker.redirect(AuthenticationConfiguration.getInstance().getApplicationURL());
        }
        
        System.err.println(ar.printDiagnostics());
        return ar;
    }
    
    
    public AuthenticationResult handleCallback() {
        AuthenticationResult ar = null;
        
        // Ask the AuthenticationHelper to validate the login that just occurred.
        // If something went wrong, check the codes in the AuthentcationResults to figure out what to do.
        ar = validate();
        
        // Were we successful in initiating the request.  This is not about _completing_, just initiating.
        if (ar == null || !ar.success()) {
            System.err.println(ar.printDiagnostics());
            return ar;
        }
        
        /*if (ar.hasRedirectURL()) {
            _broker.redirect(ar.getRedirectURL());
        } else {
            _broker.redirect(AuthenticationConfiguration.getInstance().getApplicationURL());
        }*/
        
        System.err.println(ar.printDiagnostics());
        return ar;
    }
    
    
    public AuthenticationResult handleDisconnect() {
        AuthenticationResult ar = null;
        
        // Ask the AuthenticationHelper to disconnect the service.
        // If something went wrong, check the codes in the AuthentcationResults to figure out what to do.
        ar = disconnect();
        
        // Were we successful in initiating the request.  This is not about _completing_, just initiating.
        if (ar == null || !ar.success()) {
            System.err.println(ar.printDiagnostics());
            return ar;
        }
        
        if (ar.hasRedirectURL()) {
            _broker.redirect(ar.getRedirectURL());
        } else {
            _broker.redirect(AuthenticationConfiguration.getInstance().getApplicationURL());
        }
        
        System.err.println(ar.printDiagnostics());
        return ar;
    }
    
    
    public AuthenticationResult connect() {
        return connect(extractService());
    }
    
    public AuthenticationResult connect(String service) {
        return service(Request.CONNECT, service);
    }
    
    public AuthenticationResult validate() {
        return validate(extractService());
    }
    
    public AuthenticationResult validate(String service) {
        return service(Request.VALIDATE, service);
    }
    
    public AuthenticationResult disconnect() {
        return disconnect(extractService());
    }
    
    public AuthenticationResult disconnect(String service) {
        return service(Request.DISCONNECT, service);
    }
    
    protected AuthenticationResult service(Request request) {
        return service(request, _broker.getParameter(_ac.getParameterVia()));
    }
    
    protected AuthenticationResult service(Request request, String service) {
        AuthenticationService authService = AuthenticationServiceHelper.getAuthenticationService(service, _broker);
        AuthenticationResult authResults = AuthenticationServiceHelper.newAuthenticationResults(service);
        
        if (authService != null) {
            switch (request) {
                case CONNECT:
                    authResults = authService.connect();
                    break;
                case VALIDATE:
                    authResults = authService.validate();
                    break;
                case DISCONNECT:
                    authResults = authService.disconnect();
                    break;
                default:
            }
            
            if (authResults == null) {
                authResults = AuthenticationServiceHelper.newAuthenticationResults(service);
                authResults.addStatus(500, request + ": Invalid results returned for service: " + service);
            }
        } else authResults.addStatus(404, "Authentication service '" + service + "' not found");
        
        // Save the results for use outside of this scope.
        setProcessedResults(authResults);
        
        return authResults;
    }
    
    public AuthenticationResult getProcessedResults() {
        return (AuthenticationResult) _broker.getAttribute(_ac.getAttributeResults());
    }
    
    public void setProcessedResults(AuthenticationResult result) {
        // Save the results for use outside of this scope.
        _broker.setAttribute(_ac.getAttributeResults(), result);
    }
    
    private String extractService() {
        String service = null;
        
        if (_ac.isApplicationRestStyleEndpoint()) {
            service = _broker.getPathInfo();
            
            if (_ac.getApplicationRoot() != null) {
                service = StringUtils.substringAfter(service, _ac.getApplicationRoot());
            }
            
            service = StringUtils.substringBefore(
                    StringUtils.substringAfter(
                            StringUtils.substringAfter(service, "/")
                            , "/")
                    , "/");
        } else {
            service = _broker.getParameter(_ac.getParameterVia());
        }
        
        return service;
    }
    
    private enum Request {
        CONNECT,
        VALIDATE,
        DISCONNECT
    }
}
