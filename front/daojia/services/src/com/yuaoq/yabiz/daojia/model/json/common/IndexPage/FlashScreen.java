package com.yuaoq.yabiz.daojia.model.json.common.IndexPage;

import com.yuaoq.yabiz.daojia.model.json.BaseResult;

/**
 * Created by changsy on 16/8/28.
 */
public class FlashScreen extends BaseResult {
    
    Object result;
    
    public FlashScreen(String id, String code, String msg, Object result, boolean success) {
        super(id, code, msg, success);
        this.result = result;
    }
}