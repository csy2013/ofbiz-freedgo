package com.yuaoq.yabiz.oauth.auth.conf;

import org.apache.commons.configuration.CompositeConfiguration;
import org.apache.commons.configuration.ConfigurationException;
import org.apache.commons.configuration.PropertiesConfiguration;
import org.apache.commons.configuration.SystemConfiguration;

public class AuthenticationConfiguration {
    
    public static final String PREFIX = "ac";
    public static final String FILE = "authenticationconfiguration.properties";
    
    public static final String PROPERTY_APPLICATION_NAME = PREFIX + ".application.name";
    public static final String PROPERTY_APPLICATION_BASE = PREFIX + ".application.base";
    public static final String PROPERTY_APPLICATION_BASE_SECURE = PREFIX + ".application.base.secure";
    public static final String PROPERTY_APPLICATION_ROOT = PREFIX + ".application.root";
    public static final String PROPERTY_APPLICATION_RESTSTYLEENDPOINT = PREFIX + ".application.reststyleendpoint";
    public static final String PROPERTY_APPLICATION_ENDPOINT_LOGIN = PREFIX + ".application.endpoint.login";
    public static final String PROPERTY_APPLICATION_ENDPOINT_CONNECT = PREFIX + ".application.endpoint.connect";
    public static final String PROPERTY_APPLICATION_ENDPOINT_CALLBACK = PREFIX + ".application.endpoint.callback";
    public static final String PROPERTY_APPLICATION_ENDPOINT_LOGOUT = PREFIX + ".application.endpoint.logout";
    public static final String PROPERTY_APPLICATION_ENDPOINT_DISCONNECT = PREFIX + ".application.endpoint.disconnect";
    public static final String PROPERTY_PARAMETER_VIA = PREFIX + ".parameter.via";
    public static final String PROPERTY_PARAMETER_STATE = PREFIX + ".parameter.state";
    public static final String PROPERTY_ATTRIBUTE_RESULTS = PREFIX + ".attribute.authresults";
    
    public static final String DEFAULT_APPLICATION_NAME = "authenticationapplication";
    public static final String DEFAULT_APPLICATION_BASE = "http://localhost:8082";
    public static final String DEFAULT_APPLICATION_BASE_SECURE = "https://localhost:8445";
    public static final String DEFAULT_APPLICATION_ROOT = "/authentication";
    public static final boolean DEFAULT_APPLICATION_RESTSTYLEENDPOINT = false;
    public static final String DEFAULT_APPLICATION_ENDPOINT_LOGIN = "/login";
    public static final String DEFAULT_APPLICATION_ENDPOINT_CONNECT = "/connect";
    public static final String DEFAULT_APPLICATION_ENDPOINT_CALLBACK = "/callback";
    public static final String DEFAULT_APPLICATION_ENDPOINT_LOGOUT = "/logout";
    public static final String DEFAULT_APPLICATION_ENDPOINT_DISCONNECT = "/disconnect";
    public static final String DEFAULT_PARAMETER_VIA = "via";
    public static final String DEFAULT_PARAMETER_STATE = "state";
    public static final String DEFAULT_ATTRIBUTE_RESULTS = "authresults";
    
    private String _applicationName;
    private String _applicationBase;
    private String _applicationBaseSecure;
    private String _applicationRoot;
    private boolean _applicationRestStyleEndpoint;
    private String _applicationEndpointLogin;
    private String _applicationEndpointConnect;
    private String _applicationEndpointCallback;
    private String _applicationEndpointLogout;
    private String _applicationEndpointDisconnect;
    private String _parameterVia;
    private String _parameterState;
    private String _attributeResults;
    
    
    private AuthenticationConfiguration() {
        initialize();
    }
    
    // http://stackoverflow.com/a/71683
    public static AuthenticationConfiguration getInstance() {
        return _Holder.instance;
    }
    
    private void initialize() {
        CompositeConfiguration config = createPropertyReader();
        
        _applicationName = config.getString(String.format(PROPERTY_APPLICATION_NAME), DEFAULT_APPLICATION_NAME);
        _applicationBase = config.getString(String.format(PROPERTY_APPLICATION_BASE), DEFAULT_APPLICATION_BASE);
        _applicationBaseSecure = config.getString(String.format(PROPERTY_APPLICATION_BASE_SECURE), DEFAULT_APPLICATION_BASE_SECURE);
        _applicationRoot = config.getString(String.format(PROPERTY_APPLICATION_ROOT), DEFAULT_APPLICATION_ROOT);
        _applicationRestStyleEndpoint = config.getBoolean(String.format(PROPERTY_APPLICATION_RESTSTYLEENDPOINT), DEFAULT_APPLICATION_RESTSTYLEENDPOINT);
        _applicationEndpointLogin = config.getString(String.format(PROPERTY_APPLICATION_ENDPOINT_LOGIN), DEFAULT_APPLICATION_ENDPOINT_LOGIN);
        _applicationEndpointConnect = config.getString(String.format(PROPERTY_APPLICATION_ENDPOINT_CONNECT), DEFAULT_APPLICATION_ENDPOINT_CONNECT);
        _applicationEndpointCallback = config.getString(String.format(PROPERTY_APPLICATION_ENDPOINT_CALLBACK), DEFAULT_APPLICATION_ENDPOINT_CALLBACK);
        _applicationEndpointLogout = config.getString(String.format(PROPERTY_APPLICATION_ENDPOINT_LOGOUT), DEFAULT_APPLICATION_ENDPOINT_LOGOUT);
        _applicationEndpointDisconnect = config.getString(String.format(PROPERTY_APPLICATION_ENDPOINT_DISCONNECT), DEFAULT_APPLICATION_ENDPOINT_DISCONNECT);
        _parameterVia = config.getString(String.format(PROPERTY_PARAMETER_VIA), DEFAULT_PARAMETER_VIA);
        _parameterState = config.getString(String.format(PROPERTY_PARAMETER_STATE), DEFAULT_PARAMETER_STATE);
        _attributeResults = config.getString(String.format(PROPERTY_ATTRIBUTE_RESULTS), DEFAULT_ATTRIBUTE_RESULTS);
    }
    
    private CompositeConfiguration createPropertyReader() {
        CompositeConfiguration config = new CompositeConfiguration();
        
        try {
            config.addConfiguration(new SystemConfiguration());
            config.addConfiguration(new PropertiesConfiguration(FILE));
        } catch (ConfigurationException e) {
        }
        
        return config;
    }
    
    public String getApplicationName() {
        return _applicationName;
    }
    
    public String getApplicationBase() {
        return _applicationBase;
    }
    
    public String getApplicationBaseSecure() {
        return _applicationBaseSecure;
    }
    
    public String getApplicationRoot() {
        return _applicationRoot;
    }
    
    public boolean getApplicationRestStyleEndpoint() {
        return _applicationRestStyleEndpoint;
    }
    
    public boolean isApplicationRestStyleEndpoint() {
        return getApplicationRestStyleEndpoint();
    }
    
    public String getApplicationEndpointLogin() {
        return _applicationEndpointLogin;
    }
    
    public String getApplicationEndpointConnect() {
        return _applicationEndpointConnect;
    }
    
    public String getApplicationEndpointCallback() {
        return _applicationEndpointCallback;
    }
    
    public String getApplicationEndpointLogout() {
        return _applicationEndpointLogout;
    }
    
    public String getApplicationEndpointDisconnect() {
        return _applicationEndpointDisconnect;
    }
    
    public String getParameterVia() {
        return _parameterVia;
    }
    
    public String getParameterState() {
        return _parameterState;
    }
    
    public String getAttributeResults() {
        return _attributeResults;
    }
    
    public String getApplicationURL() {
        return getApplicationURL(true, false);
    }
    
    public String getApplicationURL(boolean full, boolean secure) {
        String base = null;
        
        base = getApplicationBase();
        if (secure) base = getApplicationBaseSecure();
        
        if (!full) base = getApplicationRoot();
        else base += getApplicationRoot();
        
        return base;
    }
    
    public String getApplicationLoginURL() {
        return getApplicationLoginURL(true, false);
    }
    
    public String getApplicationLoginURL(boolean full, boolean secure) {
        return getApplicationURL(full, secure) + getApplicationEndpointLogin();
    }
    
    public String getApplicationConnectURL() {
        return getApplicationConnectURL(true, false);
    }
    
    public String getApplicationConnectURL(boolean full, boolean secure) {
        return getApplicationURL(full, secure) + getApplicationEndpointConnect();
    }
    
    public String getApplicationCallbackURL() {
        return getApplicationCallbackURL(true, false);
    }
    
    public String getApplicationCallbackURL(boolean full, boolean secure) {
        return getApplicationURL(full, secure) + getApplicationEndpointCallback();
    }
    
    public String getApplicationLogoutURL() {
        return getApplicationLogoutURL(true, false);
    }
    
    public String getApplicationLogoutURL(boolean full, boolean secure) {
        return getApplicationURL(full, secure) + getApplicationEndpointLogout();
    }
    
    public String getApplicationDisconnectURL() {
        return getApplicationDisconnectURL(true, false);
    }
    
    public String getApplicationDisconnectURL(boolean full, boolean secure) {
        return getApplicationURL(full, secure) + getApplicationEndpointDisconnect();
    }
    
    public String getApplicationServiceLoginURL(String service) {
        return getApplicationServiceLoginURL(service, true, false);
    }
    
    public String getApplicationServiceLoginURL(String service, boolean full, boolean secure) {
        String base = getApplicationLoginURL(full, secure);
        
        if (isApplicationRestStyleEndpoint()) base += "/" + service;
        else base += "?" + getParameterVia() + "=" + service;
        
        return base;
    }
    
    public String getApplicationServiceConnectURL(String service) {
        return getApplicationServiceConnectURL(service, true, false);
    }
    
    public String getApplicationServiceConnectURL(String service, boolean full, boolean secure) {
        String base = getApplicationConnectURL(full, secure);
        
        if (isApplicationRestStyleEndpoint()) base += "/" + service;
        else base += "?" + getParameterVia() + "=" + service;
        
        return base;
    }
    
    public String getApplicationServiceCallbackURL(String service) {
        return getApplicationServiceCallbackURL(service, true, false);
    }
    
    public String getApplicationServiceCallbackURL(String service, boolean full, boolean secure) {
        String base = getApplicationCallbackURL(full, secure);
        
        if (isApplicationRestStyleEndpoint()) base += "/" + service;
        else base += "?" + getParameterVia() + "=" + service;
        
        return base;
    }
    
    public String getApplicationServiceLogoutURL(String service) {
        return getApplicationServiceLogoutURL(service, true, false);
    }
    
    public String getApplicationServiceLogoutURL(String service, boolean full, boolean secure) {
        String base = getApplicationLogoutURL(full, secure);
        
        if (isApplicationRestStyleEndpoint()) base += "/" + service;
        else base += "?" + getParameterVia() + "=" + service;
        
        return base;
    }
    
    public String getApplicationServiceDisconnectURL(String service) {
        return getApplicationServiceDisconnectURL(service, true, false);
    }
    
    public String getApplicationServiceDisconnectURL(String service, boolean full, boolean secure) {
        String base = getApplicationDisconnectURL(full, secure);
        
        if (isApplicationRestStyleEndpoint()) base += "/" + service;
        else base += "?" + getParameterVia() + "=" + service;
        
        return base;
    }
    
    private static class _Holder {
        public static AuthenticationConfiguration instance = new AuthenticationConfiguration();
    }
}
