package com.yuaoq.yabiz.oauth.auth.service;

import com.google.api.client.http.HttpTransport;
import com.google.api.client.http.javanet.NetHttpTransport;
import com.google.api.client.json.jackson.JacksonFactory;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.yuaoq.yabiz.oauth.auth.result.AuthenticationResult;
import com.yuaoq.yabiz.oauth.utilities.HttpConnectionHelper;
import org.apache.commons.lang3.StringUtils;


public class QQAuthenticationService extends AbstractAuthenticationService {
    private static final String SERVICE_IDENTIFIER = "qq";
    
    /**
     * Default HTTP transport to use to make HTTP requests.
     */
    private static final HttpTransport TRANSPORT = new NetHttpTransport();
    
    /**
     * Default JSON factory to use to deserialize JSON.
     */
    private static final JacksonFactory JSON_FACTORY = new JacksonFactory();
    
    
    private String getLoginRedirectURL(String state) {
        return "https://graph.qq.com/oauth2.0/authorize?response_type=code&client_id=" + getServiceApplicationID() +
                "&redirect_uri=" + HttpConnectionHelper.encode(getCallbackURL()) +
                "&" + getStateCheckParameter() + "=" + state + "&scope=get_user_info";
        
    }
    
    
    private String getAuthenticationURL(String authenticationCode) {
//        pc
        return "https://graph.qq.com/oauth2.0/token?grant_type=authorization_code&client_id=" + getServiceApplicationID() +
                "&client_secret=" + getServiceApplicationSecret() +
                "&code=" +
                authenticationCode + "&redirect_uri=" + HttpConnectionHelper.encode(getCallbackURL());
        
        /*return "https://graph.z.qq.com/moc2/token?grant_type=authorization_code&client_id=" + getServiceApplicationID() +
                "&client_secret=" + getServiceApplicationSecret() +
                "&code=" +
                authenticationCode + "&redirect_uri=" + HttpConnectionHelper.encode(getCallbackURL());*/
    }
    
    private String getOpenIdURL(String accessToken) {
//        return "https://graph.z.qq.com/moc2/me?access_token=" + accessToken;
     return "https://graph.qq.com/oauth2.0/me?access_token=" + accessToken;
    }
    
    private String getDataURL(String accessToken, String openId) {
        return "https://graph.qq.com/user/get_user_info?access_token=" + accessToken + "" +
                "&oauth_consumer_key=" + getServiceApplicationID() + "&openid=" + openId;
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
        token = StringUtils.removeEnd(
                StringUtils.removeStart(token, "access_token="),
                "&expires=");
        return token;
    }
    
    private String getOpenId(String accessToken) {
        String openId = null;
        openId = HttpConnectionHelper.getBasicResponse(getOpenIdURL(accessToken));
        
        openId = StringUtils.removeEnd(
                StringUtils.removeStart(openId, "callback("),
                ");\n");
        JsonObject jsonObject = new JsonParser().parse(openId).getAsJsonObject();
        openId = jsonObject.get("openid").getAsString();
        return openId;
    }
    
    
    @Override
    protected AuthenticationResult validateService(AuthenticationResult ar) {
        boolean success = false;
        String authenticationCode = null;
        String accessToken = null;
        String openId = null;

//        1. 如果用户成功登录并授权，则会跳转到指定的回调地址，并在redirect_uri地址后带上Authorization Code和原始的state值。
        authenticationCode = _broker.getParameter("code");
        
        // If there was an error in the token info, abort.
        if (StringUtils.isBlank(authenticationCode)) {
            ar.addStatus(500, "Authentication code was empty or null.");
            return ar;
        }
//        Step2：通过Authorization Code获取Access Token
        accessToken = getAccessToken(authenticationCode);
        // Make sure the token was created properly
        if (StringUtils.isBlank(accessToken)) {
            ar.addStatus(500, "Failed during token creation");
            return ar;
        }
        
        //Step3：（可选）权限自动续期，获取Access Token
        //Step4, 获取用户OpenID_OAuth2.0
        openId = getOpenId(accessToken);
        
        //Step5 OpenAPI调用说明_OAuth2.0
        success = getQQData(accessToken, openId, ar);
        
        if (!success) {
            ar.addStatus(401, "Could not fetch user data from facebook.");
            return ar;
        }
        
        ar.addStatus(200, "Successfully validated user.");
        ar.setSuccess(true);
        
        return ar;
    }
    
    
    private boolean getQQData(String accessToken, String openId, AuthenticationResult ar) {
        String responseBody = HttpConnectionHelper.getBasicResponse(getDataURL(accessToken, openId));
        
        if (StringUtils.isBlank(responseBody)) return false;
        
        JsonObject jsonObject = new JsonParser().parse(responseBody).getAsJsonObject();
        
        String nickname = jsonObject.get("nickname").getAsString();
        String gender = jsonObject.get("gender").getAsString();
        String figureurl = jsonObject.get("figureurl").getAsString();
        String figureurl_1 = jsonObject.get("figureurl_1").getAsString();
        String figureurl_2 = jsonObject.get("figureurl_2").getAsString();
        String figureurl_qq_1 = jsonObject.get("figureurl_qq_1").getAsString();
        String figureurl_qq_2 = jsonObject.get("figureurl_qq_2").getAsString();
        String is_yellow_vip = jsonObject.get("is_yellow_vip").getAsString();
        String vip = jsonObject.get("vip").getAsString();
        String yellow_vip_level = jsonObject.get("yellow_vip_level").getAsString();
        String level = jsonObject.get("level").getAsString();
        String is_yellow_year_vip = jsonObject.get("is_yellow_year_vip").getAsString();
        String province = jsonObject.get("province").getAsString();
        String city = jsonObject.get("city").getAsString();
        String birthDate  = jsonObject.get("year").getAsString();
    
        // The primary key we need to index against the user in the local data store.
        ar.setServiceUserID(openId);
        
        ar.addData(KEY_PROFILEURL, figureurl_qq_1);
        ar.addData(KEY_IMAGEURL, figureurl_qq_2);
        ar.addData(KEY_DISPLAYNAME, nickname);
        ar.addData(KEY_NAME,nickname);
        ar.addData(KEY_NICKNAME,nickname);
        ar.addData(KEY_AUTHTYPEID,"qq");
        ar.addData(KEY_OPENID,openId);
        ar.addData(KEY_GENDER, gender.equals("男")?"1":"0");
        ar.addData(KEY_PROVINCE,province);
        ar.addData(KEY_CITY,city);
        ar.addData(KEY_BIRTHDAY,birthDate);
        
        ar.addData("figureurl", figureurl);
        ar.addData("figureurl_1", figureurl_1);
        ar.addData("figureurl_2", figureurl_2);
        ar.addData("is_yellow_vip", is_yellow_vip);
        ar.addData("vip", vip);
        ar.addData("yellow_vip_level", yellow_vip_level);
        ar.addData("level", level);
        ar.addData("is_yellow_year_vip", is_yellow_year_vip);
//        ar.setReturnData(jsonObject);
        
        
        return true;
    }
    
    
    @Override
    protected AuthenticationResult disconnectService(AuthenticationResult ar) {
        ar.addStatus(200, "Successfully disconnected");
        ar.setSuccess(true);
        return ar;
    }
}
