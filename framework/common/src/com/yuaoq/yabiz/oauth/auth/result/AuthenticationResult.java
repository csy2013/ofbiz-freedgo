package com.yuaoq.yabiz.oauth.auth.result;

import com.yuaoq.yabiz.oauth.result.BasicResult;


public class AuthenticationResult extends BasicResult<Object, Object> {
    protected String _service;
    protected String _serviceUserID;
    protected String _redirectURL;
    protected String _state;
    
    public AuthenticationResult(String service) {
        // Do the parent class stuff
        super();
        
        // Do our own stuff
        initialize(service);
        
        // We should probably throw an exception here if the service is invalid
        // to prevent the instance from being used.
//		if(!initialized) throw InvalidServiceException();
    }
    
    
    public boolean initialize(String service) {
        super.initialize();
        
        _service = service;
        
        if (_service == null) throw new IllegalArgumentException("Service not specified upon initialization");
        
        return true;
    }
    
    
    protected void reset() {
        super.reset();
        
        _service = null;
        _serviceUserID = null;
        _redirectURL = null;
        _state = null;
    }
    
    
    public String getService() {
        return _service;
    }
//	private void setService(String value){ _service = value; }
    
    public String getServiceUserID() {
        return _serviceUserID;
    }
    
    public void setServiceUserID(String value) {
        _serviceUserID = value;
    }
    
    public boolean hasServiceUserID() {
        return getServiceUserID() != null;
    }
    
    public String getRedirectURL() {
        return _redirectURL;
    }
    
    public void setRedirectURL(String value) {
        _redirectURL = value;
    }
    
    public boolean hasRedirectURL() {
        return getRedirectURL() != null;
    }
    
    public String getState() {
        return _state;
    }
    
    public void setState(String value) {
        _state = value;
    }
    
    public boolean hasState() {
        return getState() != null;
    }
    
    public String printDiagnostics() {
        StringBuffer buffer = new StringBuffer(super.printDiagnostics());
        
        buffer.append("\n\n");
        buffer.append("AuthenticationResults:\n");
        buffer.append("---------------------------------\n");
        buffer.append("Service: " + getService() + "\n");
        buffer.append("Service User ID: " + getServiceUserID() + "\n");
        buffer.append("Redirect URL: " + getRedirectURL() + "\n");
        buffer.append("State: " + getState() + "\n");
        
        return buffer.toString();
    }
}
