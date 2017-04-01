package com.yuaoq.yabiz.daojia.model.json.common.logging;

import com.yuaoq.yabiz.daojia.model.json.BaseResult;

/**
 * Created by changsy on 16/8/29.
 */
public class Logging extends BaseResult {
    
    Object result;
    
    public Logging(String id, String code, String msg, Object result, boolean success) {
        super(id, code, msg, success);
        this.result = result;
    }
}
