package com.yuaoq.yabiz.oauth.auth.service;

import com.yuaoq.yabiz.oauth.auth.conf.AuthenticationServiceConfiguration;
import com.yuaoq.yabiz.oauth.auth.result.AuthenticationResult;
import com.yuaoq.yabiz.oauth.broker.RequestResponseBroker;


public class AuthenticationServiceHelper {
    public static AuthenticationService getAuthenticationService(String service, RequestResponseBroker<?, ?, ?> broker) throws IllegalArgumentException {
        AuthenticationService as = null;
        
        if (service == null) throw new IllegalArgumentException("Null service name passed in.");
        
        as = AuthenticationServiceConfiguration.getInstance().newServiceInstance(service);
        
        if (as == null) throw new IllegalArgumentException("Service: " + service + " could not be instantiated.");
        
        as.initialize(broker);
        
        return as;
    }
    
    
    public static AuthenticationResult newAuthenticationResults(String service) {
        return new AuthenticationResult(service);
    }
}
