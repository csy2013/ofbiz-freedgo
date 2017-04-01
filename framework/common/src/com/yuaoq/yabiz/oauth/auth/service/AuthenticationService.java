package com.yuaoq.yabiz.oauth.auth.service;

import com.yuaoq.yabiz.oauth.auth.result.AuthenticationResult;
import com.yuaoq.yabiz.oauth.broker.RequestResponseBroker;

public interface AuthenticationService {
    String KEY_PROFILEURL = "profileurl";
    String KEY_IMAGEURL = "imageurl";
    String KEY_DISPLAYNAME = "displayname";
    String KEY_NAME = "name";
    String KEY_NICKNAME = "nickname";
    String KEY_OPENID = "openid";
    String KEY_AUTHTYPEID = "authtypeid";
    String KEY_GENDER = "gender";
    String KEY_PROVINCE = "province";
    String KEY_CITY = "city";
    String KEY_BIRTHDAY = "birthday";
    
    boolean initialize(RequestResponseBroker<?, ?, ?> broker);
    AuthenticationResult connect();
    AuthenticationResult validate();
    AuthenticationResult disconnect();
}
