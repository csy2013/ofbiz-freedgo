package com.yuaoq.yabiz.oauth.auth.service;

import com.google.api.client.auth.oauth2.TokenResponseException;
import com.google.api.client.googleapis.auth.oauth2.GoogleAuthorizationCodeTokenRequest;
import com.google.api.client.googleapis.auth.oauth2.GoogleCredential;
import com.google.api.client.googleapis.auth.oauth2.GoogleTokenResponse;
import com.google.api.client.http.GenericUrl;
import com.google.api.client.http.HttpResponse;
import com.google.api.client.http.HttpTransport;
import com.google.api.client.http.javanet.NetHttpTransport;
import com.google.api.client.json.jackson.JacksonFactory;
import com.google.api.services.oauth2.Oauth2;
import com.google.api.services.oauth2.model.Tokeninfo;
import com.google.api.services.plus.Plus;
import com.google.api.services.plus.model.Person;
import com.yuaoq.yabiz.oauth.auth.result.AuthenticationResult;
import com.yuaoq.yabiz.oauth.utilities.HttpConnectionHelper;
import org.apache.commons.lang3.StringUtils;

import java.io.IOException;


public class GooglePlusAuthenticationService extends AbstractAuthenticationService {
    private static final String SERVICE_IDENTIFIER = "googleplus";
    
    /**
     * Default HTTP transport to use to make HTTP requests.
     */
    private static final HttpTransport TRANSPORT = new NetHttpTransport();
    
    /**
     * Default JSON factory to use to deserialize JSON.
     */
    private static final JacksonFactory JSON_FACTORY = new JacksonFactory();
    
    
    private String getLoginRedirectURL(String state) {
        // https://developers.google.com/accounts/docs/OAuth2WebServer
        return "https://accounts.google.com/o/oauth2/auth?" +
                "response_type=code" +
                "&client_id=" + getServiceApplicationID() +
                "&redirect_uri=" + HttpConnectionHelper.encode(getCallbackURL()) +
                "&scope=https%3A%2F%2Fwww.googleapis.com%2Fauth%2Fplus.login" +
                "&" + getStateCheckParameter() + "=" + state +
                "&access_type=offline" +
                "&approval_prompt=auto" +
//			"&request_visible_actions=http%3A%2F%2Fschemas.google.com%2FAddActivity" +
//			"&cookie_policy=single_host_origin" +
//			"&proxy=oauth2relay384190406" +
//			"&origin=" + getApplicationURL() +
//			"&authuser=0" +
                "";
    }
    
    
    private String getRevokeURL(String accessToken) {
        return String.format("https://accounts.google.com/o/oauth2/revoke?token=%s", accessToken);
    }
    
    
    @Override
    public String getIdentifier() {
        return SERVICE_IDENTIFIER;
    }
    
    
    @Override
    protected AuthenticationResult connectService(AuthenticationResult ar) {
        ar.addStatus(200, "Redirecting to the authentication service for: " + getIdentifier());
        ar.setRedirectURL(getLoginRedirectURL(ar.getState()));
        ar.setSuccess(true);
        
        return ar;
    }
    
    
    @Override
    protected AuthenticationResult validateService(AuthenticationResult ar) {
/*
         Returned parameters:
			via=googleplus
			state=8164dhartcr448bvcmditnurad
			code=4/7NRP9oNxmzi7NHrJBmUWJyr4tID0.4pEToPwqpEcRshQV0ieZDAq4ywBDegI
*/
        String authenticationCode = null;
        String gPlusID = null;
        GoogleTokenResponse tokenResponse = null;
        GoogleCredential credential = null;
        Tokeninfo accessToken = null;
        Person person = null;
        
        authenticationCode = _broker.getParameter("code");
        gPlusID = _broker.getParameter("gplus_id");
        
        // If there was an error in the token info, abort.
        if (StringUtils.isBlank(authenticationCode)) {
            ar.addStatus(500, "Authentication code was empty or null.");
            return ar;
        }
        
        try {
            // Create a new authorization token
            tokenResponse = new GoogleAuthorizationCodeTokenRequest(TRANSPORT, JSON_FACTORY, getServiceApplicationID(), getServiceApplicationSecret(), authenticationCode, getCallbackURL()).execute();
            
            // Create a credential representation of the token data.
            credential = new GoogleCredential.Builder()
                    .setJsonFactory(JSON_FACTORY)
                    .setTransport(TRANSPORT)
                    .setClientSecrets(getServiceApplicationID(), getServiceApplicationSecret()).build()
                    .setFromTokenResponse(tokenResponse);
            
            // Check that the token is valid.
            Oauth2 oauth2 = new Oauth2
                    .Builder(TRANSPORT, JSON_FACTORY, credential)
                    .setApplicationName(getApplicationName()).build();
            
            // Generate the token
            accessToken = oauth2.tokeninfo().setAccessToken(credential.getAccessToken()).execute();
            
            // Make sure the token was created properly
            if (accessToken == null) {
                ar.addStatus(500, "Failed during token creation");
                return ar;
            }
            
            // If there was an error in the token info, abort.
            if (accessToken.containsKey("error")) {
                ar.addStatus(401, accessToken.get("error").toString());
                return ar;
            }
            
            // Make sure the token we got is for the intended user.
            if (gPlusID != null && !accessToken.getUserId().equals(gPlusID)) {
                ar.addStatus(401, "Token's user ID (" + accessToken.getUserId() + ") doesn't match given user ID (" + gPlusID + ") .");
                return ar;
            }
            
            // Make sure the token we got is for our app.
            if (!accessToken.getIssuedTo().equals(getServiceApplicationID())) {
                ar.addStatus(401, "Token's client ID does not match app's.");
                return ar;
            }
            
            // Store the token in the session for later use.
            _broker.setAttribute("token", tokenResponse.toString());
            
        } catch (TokenResponseException e) {
            ar.addStatus(500, "Failed to upgrade the authorization code.", e);
            return ar;
            
        } catch (IOException e) {
            ar.addStatus(500, "IOException while trying to get credentials.  Failed to read token data from Google.", e);
            return ar;
        }

//		String email = getUserMailAddressFromJsonResponse(accessToken);
        Plus plus = new Plus.Builder(TRANSPORT, JSON_FACTORY, credential)
                .setApplicationName(getApplicationName())
                .build();
        
        try {
            person = plus.people().get("me").execute();
            System.out.println("person = " + person);
        } catch (IOException e) {
            ar.addStatus(500, "IOException while trying to get person information", e);
            return ar;
        }
        
        // The primary key we need to index against the user in the local data store.
        ar.setServiceUserID(person.getId());
        
        // Some additional information about the person.
        ar.addData(KEY_PROFILEURL, person.getUrl());
        ar.addData(KEY_IMAGEURL, person.getImage().getUrl());
        ar.addData(KEY_DISPLAYNAME, person.getDisplayName());
     
        ar.addData(KEY_NAME,person.getName());
        ar.addData(KEY_NICKNAME,person.getNickname());
        ar.addData(KEY_AUTHTYPEID,"googleplus");
        ar.addData(KEY_OPENID,person.getId());
        ar.addData(KEY_GENDER, person.getGender().equals("男")?"1":"0");
        ar.addData(KEY_PROVINCE,"");
        ar.addData(KEY_CITY,"");
        ar.addData(KEY_BIRTHDAY,person.getBirthday());
        // Store any returned object we want to parse later.
//        ar.setReturnData(person);
        
        ar.addStatus(200, "Successfully validated user.");
        ar.setSuccess(true);
        
        return ar;
    }
    
    
    @Override
    protected AuthenticationResult disconnectService(AuthenticationResult ar) {
        String tokenData = null;
        
        try {
            // Only disconnect a connected user.
            tokenData = _broker.getAttributeString("token");
            
            if (StringUtils.isBlank(tokenData)) {
                ar.addStatus(401, "Current user not connected.");
                return ar;
            }
            
            // Build credential from stored token data.
            GoogleCredential credential = new GoogleCredential.Builder()
                    .setJsonFactory(JSON_FACTORY)
                    .setTransport(TRANSPORT)
                    .setClientSecrets(getServiceApplicationID(), getServiceApplicationSecret()).build()
                    .setFromTokenResponse(JSON_FACTORY.fromString(tokenData, GoogleTokenResponse.class));
            
            // Execute HTTP GET request to revoke current token.
            HttpResponse revokeResponse = TRANSPORT.createRequestFactory()
                    .buildGetRequest(new GenericUrl(getRevokeURL(credential.getAccessToken()))
                    ).execute();
            
            if (!revokeResponse.isSuccessStatusCode()) {
                ar.addStatus(400, "Failed to revoke token for given user due to no valid success code.");
                return ar;
            }
            
            // Reset the user's session.
            _broker.removeAttribute("token");
            ar.addStatus(200, "Successfully disconnected");
            ar.setSuccess(true);
            
        } catch (IOException e) {
            ar.addStatus(400, "Failed to revoke token for given user.", e);
            return ar;
        }
        
        return ar;
    }
}
