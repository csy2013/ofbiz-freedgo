package com.yuaoq.yabiz.oauth.auth.conf;


import com.yuaoq.yabiz.oauth.auth.service.AuthenticationService;
import org.apache.commons.configuration.CompositeConfiguration;
import org.apache.commons.configuration.ConfigurationException;
import org.apache.commons.configuration.PropertiesConfiguration;
import org.apache.commons.configuration.SystemConfiguration;
import org.apache.commons.lang.StringUtils;

import java.util.*;


public class AuthenticationServiceConfiguration {
    public static final String PREFIX = "asc";
    public static final String FILE = "authenticationserviceconfiguration.properties";
    
    public static final String PROPERTY_AVAILABLESERVICES = PREFIX + ".availableservices";
    public static final String PROPERTY_SERVICE_ENABLED = PREFIX + ".service.%s.enabled";
    public static final String PROPERTY_SERVICE_NAME = PREFIX + ".service.%s.name";
    public static final String PROPERTY_SERVICE_CLASSNAME = PREFIX + ".service.%s.classname";
    public static final String PROPERTY_SERVICE_APPID = PREFIX + ".service.%s.appid";
    public static final String PROPERTY_SERVICE_APPSECRET = PREFIX + ".service.%s.appsecret";
    public static final String PROPERTY_SERVICE_CLIENTHANDLEDLOGIN = PREFIX + ".service.%s.clienthandledlogin";
    public static final String PROPERTY_SERVICE_SCOPE = PREFIX + ".service.%s.scope";
    public static final String PROPERTY_SERVICE_STATECHECK = PREFIX + ".service.%s.statecheck";
    
    public static final List<String> DEFAULT_AVAILABLESERVICES = null;
    public static final boolean DEFAULT_SERVICE_ENABLED = false;
    public static final String DEFAULT_SERVICE_NAME = null;
    public static final String DEFAULT_SERVICE_CLASSNAME = null;
    public static final String DEFAULT_SERVICE_APPID = null;
    public static final String DEFAULT_SERVICE_APPSECRET = null;
    public static final boolean DEFAULT_SERVICE_CLIENTHANDLEDLOGIN = false;
    public static final List<String> DEFAULT_SERVICE_SCOPE = null;
    public static final boolean DEFAULT_SERVICE_STATECHECK = false;
    
    
    private List<String> _enabledServices;
    private Map<String, ServiceData> _services;
    
    
    private AuthenticationServiceConfiguration() {
        initialize();
    }
    
    // http://stackoverflow.com/a/71683
    public static AuthenticationServiceConfiguration getInstance() {
        return _Holder.instance;
    }
    
    private void initialize() {
        CompositeConfiguration config = createPropertyReader();
        
        _enabledServices = new ArrayList<String>();
        _services = new LinkedHashMap<String, ServiceData>();
        
        config.setListDelimiter(',');
        String[] loadableServices = config.getStringArray(PROPERTY_AVAILABLESERVICES);
        if (loadableServices != null && loadableServices.length > 0) {
            for (String serviceName : loadableServices) {
                ServiceData serviceData = new ServiceData();
                
                if (StringUtils.isBlank(serviceName)) continue;
                
                serviceData.setEnabled(config.getBoolean(String.format(PROPERTY_SERVICE_ENABLED, serviceName)));
//				if(!serviceData.isEnabled()) continue;
                serviceData.setName(config.getString(String.format(PROPERTY_SERVICE_NAME, serviceName)));
                serviceData.setClassName(config.getString(String.format(PROPERTY_SERVICE_CLASSNAME, serviceName)));
                if (serviceData.newInstance() == null) continue;
                serviceData.setAppID(config.getString(String.format(PROPERTY_SERVICE_APPID, serviceName)));
                serviceData.setAppSecret(config.getString(String.format(PROPERTY_SERVICE_APPSECRET, serviceName)));
                serviceData.setClientHandledLogin(config.getBoolean(String.format(PROPERTY_SERVICE_CLIENTHANDLEDLOGIN, serviceName)));
                String[] scopes = config.getStringArray(String.format(PROPERTY_SERVICE_SCOPE, serviceName));
                if (scopes != null && scopes.length > 0 && !StringUtils.isBlank(scopes[0])) serviceData.setScope(Arrays.asList(scopes));
                serviceData.setStateCheck(config.getBoolean(String.format(PROPERTY_SERVICE_STATECHECK, serviceName)));
                
                _services.put(serviceName, serviceData);
                if (serviceData.isEnabled()) _enabledServices.add(serviceName);
            }
            System.err.println("Authentication Service: Available services: " + _enabledServices);
        } else {
            System.err.println("Authentication Service: No services available");
        }
    }
    
    private CompositeConfiguration createPropertyReader() {
        CompositeConfiguration config = new CompositeConfiguration();
        try {
            config.addConfiguration(new SystemConfiguration());
            config.addConfiguration(new PropertiesConfiguration(FILE));
        } catch (ConfigurationException e) {
            e.printStackTrace();
        }
        
        return config;
    }
    
    public int getEnabledServicesCount() {
        return (_enabledServices == null) ? 0 : _enabledServices.size();
    }
    
    public List<String> getEnabledServices() {
        return _enabledServices;
    }
    
    public boolean getServiceEnabled(String service) {
        ServiceData serviceData = getServiceData(service);
        if (serviceData != null) return serviceData.getEnabled();
        else return DEFAULT_SERVICE_ENABLED;
    }
    
    public boolean isServiceEnabled(String service) {
        return getServiceEnabled(service);
    }
    
    public String getServiceName(String service) {
        ServiceData serviceData = getServiceData(service);
        if (serviceData != null) return serviceData.getName();
        else return DEFAULT_SERVICE_NAME;
    }
    
    public String getServiceClassName(String service) {
        ServiceData serviceData = getServiceData(service);
        if (serviceData != null) return serviceData.getClassName();
        else return DEFAULT_SERVICE_CLASSNAME;
    }
    
    public String getServiceAppID(String service) {
        ServiceData serviceData = getServiceData(service);
        if (serviceData != null) return serviceData.getAppID();
        else return DEFAULT_SERVICE_APPID;
    }
    
    public String getServiceAppSecret(String service) {
        ServiceData serviceData = getServiceData(service);
        if (serviceData != null) return serviceData.getAppSecret();
        else return DEFAULT_SERVICE_APPSECRET;
    }
    
    public boolean getServiceClientHandledLogin(String service) {
        ServiceData serviceData = getServiceData(service);
        if (serviceData != null) return serviceData.getClientHandledLogin();
        else return DEFAULT_SERVICE_CLIENTHANDLEDLOGIN;
    }
    
    public List<String> getServiceScope(String service) {
        ServiceData serviceData = getServiceData(service);
        if (serviceData != null) return serviceData.getScope();
        else return DEFAULT_SERVICE_SCOPE;
    }
    
    public boolean getServiceStateCheck(String service) {
        ServiceData serviceData = getServiceData(service);
        if (serviceData != null) return serviceData.getStateCheck();
        else return DEFAULT_SERVICE_STATECHECK;
    }
    
    public AuthenticationService newServiceInstance(String service) {
        ServiceData serviceData = getServiceData(service);
        if (serviceData != null) return serviceData.newInstance();
        else return null;
    }
    
    private ServiceData getServiceData(String value) {
        return _services.get(value);
    }
    
    private static class _Holder {
        public static AuthenticationServiceConfiguration instance = new AuthenticationServiceConfiguration();
    }
    
    private class ServiceData {
        private boolean _enabled;
        private String _name;
        private String _className;
        private String _appID;
        private String _appSecret;
        private boolean _clientHandledLogin;
        private List<String> _scope;
        private boolean _stateCheck;
        
        public ServiceData() {
            initialize();
        }
        
        public boolean initialize() {
            _enabled = DEFAULT_SERVICE_ENABLED;
            _name = DEFAULT_SERVICE_NAME;
            _className = DEFAULT_SERVICE_CLASSNAME;
            _appID = DEFAULT_SERVICE_APPID;
            _appSecret = DEFAULT_SERVICE_APPSECRET;
            _clientHandledLogin = DEFAULT_SERVICE_CLIENTHANDLEDLOGIN;
            _scope = DEFAULT_SERVICE_SCOPE;
            _stateCheck = DEFAULT_SERVICE_STATECHECK;
            
            return true;
        }
        
        public boolean getEnabled() {
            return _enabled;
        }
        
        public boolean isEnabled() {
            return getEnabled();
        }
        
        public void setEnabled(boolean value) {
            _enabled = value;
        }
        
        public String getName() {
            return _name;
        }
        
        public void setName(String value) {
            _name = value;
        }
        
        public String getClassName() {
            return _className;
        }
        
        public void setClassName(String value) {
            _className = value;
        }
        
        public String getAppID() {
            return _appID;
        }
        
        public void setAppID(String value) {
            _appID = value;
        }
        
        public String getAppSecret() {
            return _appSecret;
        }
        
        public void setAppSecret(String value) {
            _appSecret = value;
        }
        
        public boolean getClientHandledLogin() {
            return _clientHandledLogin;
        }
        
        public void setClientHandledLogin(boolean value) {
            _clientHandledLogin = value;
        }
        
        public List<String> getScope() {
            return _scope;
        }
        
        public void setScope(List<String> value) {
            _scope = value;
        }
        
        public boolean getStateCheck() {
            return _stateCheck;
        }
        
        public void setStateCheck(boolean value) {
            _stateCheck = value;
        }
        
        public AuthenticationService newInstance() {
            try {
                return (AuthenticationService) Class.forName(getClassName()).newInstance();
            } catch (InstantiationException | IllegalAccessException | ClassNotFoundException e) {
                System.err.println("Something went wrong trying to instantiate " + getClassName() + ": " + e);
            } catch (Throwable th) {
                System.err.println("Something went horribly wrong trying to instantiate " + getClassName() + ": " + th);
            }
            
            return null;
        }
    }
}
