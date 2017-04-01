package com.yuaoq.yabiz.oauth.auth.service;

import com.google.api.client.http.HttpTransport;
import com.google.api.client.http.javanet.NetHttpTransport;
import com.google.api.client.json.jackson.JacksonFactory;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.yuaoq.yabiz.oauth.auth.result.AuthenticationResult;
import com.yuaoq.yabiz.oauth.utilities.HttpConnectionHelper;
import org.apache.commons.lang3.StringUtils;


public class WeixinAuthenticationService extends AbstractAuthenticationService {
    private static final String SERVICE_IDENTIFIER = "weixin";
    
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
        return "https://open.weixin.qq.com/connect/oauth2/authorize?appid=" +getServiceApplicationID()+
                "&redirect_uri="+HttpConnectionHelper.encode(getCallbackURL())+
                "&response_type=code&scope=snsapi_userinfo&state=STATE#wechat_redirect";
    }
    
    
    private String getAuthenticationURL(String authenticationCode) {
        
        return "https://api.weixin.qq.com/sns/oauth2/access_token?appid=" +getServiceApplicationID() +
                "&secret="+getServiceApplicationSecret()+
                "&code="+authenticationCode+"&grant_type=authorization_code";
    }
    
    
    private String getDataURL(String accessToken, String openId) {
        return "https://api.weixin.qq.com/sns/userinfo?access_token="+accessToken+"&openid=OPENID&lang=zh_CN" ;
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
    
    
    
    private String getOpenId(String authenticationCode) {
        String openId = null;
        openId = HttpConnectionHelper.getBasicResponse(getAuthenticationURL(authenticationCode));
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
        authenticationCode = _broker.getParameter("code");
        
        // If there was an error in the token info, abort.
        if (StringUtils.isBlank(authenticationCode)) {
            ar.addStatus(500, "Authentication code was empty or null.");
            return ar;
        }
        //第二步：通过code换取网页授权access_token
        accessToken = getAccessToken(authenticationCode);
        openId = getOpenId(authenticationCode);
        // Make sure the token was created properly
        if (StringUtils.isBlank(accessToken)) {
            ar.addStatus(500, "Failed during token creation");
            return ar;
        }
    
        //第三步：刷新access_token（如果需要）
        //第四步：拉取用户信息(需scope为 snsapi_userinfo)
        success = getWeixinData(accessToken, openId, ar);
        
        if (!success) {
            ar.addStatus(401, "Could not fetch user data from facebook.");
            return ar;
        }
        
        ar.addStatus(200, "Successfully validated user.");
        ar.setSuccess(true);
        
        return ar;
    }
    
    
    private boolean getWeixinData(String accessToken, String openId, AuthenticationResult ar) {
        String responseBody = HttpConnectionHelper.getBasicResponse(getDataURL(accessToken,openId));
        
        if (StringUtils.isBlank(responseBody)) return false;
        
		/*
		 * Returned JSON Object:
		 * {
		 * 	"id":"153443987",
		 * 	"name":"Firstname Lastname",
		 * 	"first_name":"Firstname",
		 * 	"last_name":"Lastname",
		 * 	"link":"http://www.facebook.com/first.last",
		 * 	"username":"first.last",
		 * 	"email":"someemail@facebook.com",
		 * 	"timezone":-5,
		 * 	"locale":"en_US",
		 * 	"verified":true,
		 * 	"updated_time":"2012-11-15T12:59:27+0000"
		 * }
		 */
        
        JsonObject jsonObject = new JsonParser().parse(responseBody).getAsJsonObject();
        String openid = jsonObject.get("openid").getAsString();
        String nickname = jsonObject.get("nickname").getAsString();
        String sex = jsonObject.get("sex").getAsString();
        String province = jsonObject.get("province").getAsString();
        String city = jsonObject.get("city").getAsString();
        String country = jsonObject.get("country").getAsString();
        String headimgurl = jsonObject.get("headimgurl").getAsString();
        String privilege = jsonObject.get("privilege").getAsString();
        String unionid = jsonObject.get("unionid").getAsString();
        
        // The primary key we need to index against the user in the local data store.
        ar.setServiceUserID(openid);
        
        ar.addData(KEY_PROFILEURL, headimgurl);
        ar.addData(KEY_IMAGEURL, headimgurl);
        ar.addData(KEY_DISPLAYNAME, nickname);
        
        ar.addData("userName", nickname);
        ar.addData("nickname", nickname);
        ar.addData("sex", sex);
        ar.addData("province", province);
        ar.addData("city", city);
        ar.addData("country", country);
        ar.addData("privilege", privilege);
        ar.addData("unionid", unionid);
        
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
