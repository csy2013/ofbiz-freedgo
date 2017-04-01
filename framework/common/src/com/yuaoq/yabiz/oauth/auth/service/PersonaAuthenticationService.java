package com.yuaoq.yabiz.oauth.auth.service;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.yuaoq.yabiz.oauth.auth.result.AuthenticationResult;
import com.yuaoq.yabiz.oauth.utilities.HttpConnectionHelper;
import org.apache.commons.lang3.StringUtils;

import java.util.HashMap;
import java.util.Map;

public class PersonaAuthenticationService extends AbstractAuthenticationService {
    private static final String SERVICE_IDENTIFIER = "persona";
    
    
    private String getDataURL(String assertion) {
        return "https://verifier.login.persona.org/verify";
    }
    
    
    @Override
    public String getIdentifier() {
        return SERVICE_IDENTIFIER;
    }
    
    
    @Override
    protected AuthenticationResult connectService(AuthenticationResult ar) {
        ar.setSuccess(true);
        return ar;
    }
    
    
    @Override
    protected AuthenticationResult validateService(AuthenticationResult ar) {
        boolean success = false;
        String assertion = null;
        
        assertion = _broker.getParameter("assertion");
        System.err.println("Fetching Assertion: " + assertion);
        
        // If there was an error in the token info, abort.
        if (StringUtils.isBlank(assertion)) {
            ar.addStatus(500, "Assertion was empty or null.");
            return ar;
        }
        
        success = getPersonaData(assertion, ar);
        
        if (!success) {
            ar.addStatus(401, "Could not fetch user data from persona.");
            return ar;
        }
        
        ar.addStatus(200, "Successfully validated user.");
        ar.setSuccess(true);
        
        return ar;
    }
    
    
    private boolean getPersonaData(String assertion, AuthenticationResult ar) {
        Map<String, String> parameters = new HashMap<String, String>();
        String responseBody = null;
        
        parameters.put("assertion", assertion);
        parameters.put("audience", _ac.getApplicationURL());
        
        responseBody = HttpConnectionHelper.postBasicResponse(getDataURL(assertion), parameters);
        
        if (StringUtils.isBlank(responseBody)) return false;

		/*
         * Returned JSON Object:
		 * {
		 *   "status": "okay",
		 *   "email": "bob@eyedee.me",
		 *   "audience": "https://example.com:443",
		 *   "expires": 1308859352261,
		 *   "issuer": "eyedee.me"
		 * }
		 */
        
        JsonObject jsonObject = new JsonParser().parse(responseBody).getAsJsonObject();
        String status = jsonObject.get("status").getAsString();
        String personaID = jsonObject.get("email").getAsString();
        String audience = jsonObject.get("audience").getAsString();
        long expires = jsonObject.get("expires").getAsLong();
        String issuer = jsonObject.get("issuer").getAsString();
        
        // The primary key we need to index against the user in the local data store.
        ar.setServiceUserID(personaID);
        
        ar.addData("status", status);
        ar.addData("email", personaID);
        ar.addData("audience", audience);
        ar.addData("expires", expires);
        ar.addData("issuer", issuer);
        
        ar.setReturnData(jsonObject);
        
        return true;
    }
    
    
    @Override
    protected AuthenticationResult disconnectService(AuthenticationResult ar) {
        ar.addStatus(200, "Successfully disconnected");
        ar.setSuccess(true);
        
        return ar;
    }
}
