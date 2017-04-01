package com.yuaoq.yabiz.daojia.model.json.party.userInfo;

import com.yuaoq.yabiz.daojia.model.json.BaseResult;

/**
 * Created by changsy on 16/9/13.
 */
public class UserInfo extends BaseResult {
    
    
    public Object result;
    public String type;
    public UserInfo(String id, String code, String msg, boolean success) {
        super(id, code, msg, success);
    }
    
    public UserInfo(String id, String code, String msg, boolean success, String result, String type) {
        super(id, code, msg, success);
        this.result = result;
        this.type = type;
    }
    
    public UserInfo(String id, String code, String msg, boolean success, UserInfoResult result, String type) {
        super(id, code, msg, success);
        this.result = result;
        this.type = type;
    }
    
}
