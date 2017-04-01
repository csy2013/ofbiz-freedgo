package com.yuaoq.yabiz.oauth.auth.service;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.yuaoq.yabiz.oauth.auth.result.AuthenticationResult;
import com.yuaoq.yabiz.oauth.utilities.Hmacsha1;
import com.yuaoq.yabiz.oauth.utilities.HttpConnectionHelper;
import org.apache.commons.lang3.StringUtils;

public class FacebookAuthenticationService extends AbstractAuthenticationService {
    private static final String SERVICE_IDENTIFIER = "facebook";
    public static final String ALGORITHM = "SHA-256";
    
    
    private String getLoginRedirectURL(String state) {
        return "https://www.facebook.com/v2.7/dialog/oauth?" +
                "client_id=" + getServiceApplicationID() +
                "&redirect_uri=" + HttpConnectionHelper.encode(getCallbackURL()) +
                "&" + getStateCheckParameter() + "=" + state +
                "&scope=email,public_profile";
    }
    
    
    private String getAuthenticationURL(String authenticationCode) {
        return "https://graph.facebook.com/v2.7/oauth/access_token?" +
                "client_id=" + getServiceApplicationID() +
                "&redirect_uri=" + HttpConnectionHelper.encode(getCallbackURL()) +
                "&client_secret=" + getServiceApplicationSecret() +
                "&code=" + authenticationCode;
    }
    
    
    private String getDataURL(String accessToken) {
        String appsecret_proof = "";
        try {
            appsecret_proof = Hmacsha1.getSignature(accessToken, getServiceApplicationSecret());
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "https://graph.facebook.com/v2.4/me?access_token=" + accessToken + "&appsecret_proof=" + appsecret_proof
                + "&fields=email,id,name,first_name,last_name,age_range,link,gender,locale,picture,timezone,updated_time,verified";
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
    
    
    private String getAccessToken(String authenticationCode) {
        String token = null;
        
        token = HttpConnectionHelper.getBasicResponse(getAuthenticationURL(authenticationCode));
        JsonObject jsonObject = new JsonParser().parse(token).getAsJsonObject();
        token = jsonObject.get("access_token").getAsString();
        
        return token;
    }
    
    @Override
    protected AuthenticationResult validateService(AuthenticationResult ar) {
        boolean success = false;
        String authenticationCode = null;
        String accessToken = null;
        
        authenticationCode = _broker.getParameter("code");
        
        // If there was an error in the token info, abort.
        if (StringUtils.isBlank(authenticationCode)) {
            ar.addStatus(500, "Authentication code was empty or null.");
            return ar;
        }
        
        accessToken = getAccessToken(authenticationCode);
        
        // Make sure the token was created properly
        if (StringUtils.isBlank(accessToken)) {
            ar.addStatus(500, "Failed during token creation");
            return ar;
        }
        
        success = getFacebookData(accessToken, ar);
        
        if (!success) {
            ar.addStatus(401, "Could not fetch user data from facebook.");
            return ar;
        }
        
        ar.addStatus(200, "Successfully validated user.");
        ar.setSuccess(true);
        
        return ar;
    }
    
    
    private boolean getFacebookData(String accessToken, AuthenticationResult ar) {
        String responseBody = HttpConnectionHelper.getBasicResponse(getDataURL(accessToken));
        
        if (StringUtils.isBlank(responseBody)) return false;
    
        /*{
            "id": "122242708216063",
                "name": "常胜永",
                "first_name": "胜永",
                "last_name": "常",
                "age_range": {
            "min": 21
        },
            "link": "https://www.facebook.com/app_scoped_user_id/122242708216063/",
                "gender": "male",
                "locale": "zh_CN",
                "picture": {
            "data": {
                "is_silhouette": false,
                        "url": "https://fbcdn-profile-a.akamaihd.net/hprofile-ak-xfa1/v/t1.0-1/p50x50/13781723_122328298207504_4542815544782939974_n.jpg?oh=edddd7baa2663c7d407dbb9571b7b7c7&oe=5820741D&__gda__=1478156621_70dd2e37a4385c45808851c00bc0f5b1"
            }
        },
            "timezone": 8,
                "updated_time": "2016-07-28T12:48:38+0000",
                "verified": true
        }*/
        
        JsonObject jsonObject = new JsonParser().parse(responseBody).getAsJsonObject();
        String facebookId = jsonObject.get("id").getAsString();
        String link = jsonObject.get("link").getAsString();
        String userName = jsonObject.get("name").getAsString();
        String fullName = jsonObject.get("name").getAsString();
        String firstName = jsonObject.get("first_name").getAsString();
        String lastName = jsonObject.get("last_name").getAsString();
//        String email = jsonObject.get("email").getAsString();
        String gender = jsonObject.get("gender").getAsString();
        
        JsonObject pobject = jsonObject.get("picture").getAsJsonObject();
        if(pobject!=null){
            String url = pobject.getAsJsonObject().get("data").getAsJsonObject().get("url").getAsString();
            ar.addData(KEY_PROFILEURL, url);
            ar.addData(KEY_IMAGEURL, url);
        }
        // The primary key we need to index against the user in the local data store.
        ar.setServiceUserID(facebookId);
        
//        ar.addData(KEY_PROFILEURL, link);
//        ar.addData(KEY_IMAGEURL, link);
        ar.addData(KEY_DISPLAYNAME, fullName);
    
         
        ar.addData(KEY_NAME,fullName);
        ar.addData(KEY_NICKNAME,fullName);
        ar.addData(KEY_AUTHTYPEID,"facebook");
        ar.addData(KEY_OPENID,facebookId);
        ar.addData(KEY_GENDER, gender.equals("male")?"1":"0");
        ar.addData(KEY_PROVINCE,"");
        ar.addData(KEY_CITY,"");
//        ar.addData(KEY_BIRTHDAY,birthDate);
    
     
//        ar.addData("email", email);
        ar.addData("accesstoken", accessToken);
        
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
