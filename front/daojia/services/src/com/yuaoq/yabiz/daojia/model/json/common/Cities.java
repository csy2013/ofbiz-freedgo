package com.yuaoq.yabiz.daojia.model.json.common;

import com.yuaoq.yabiz.daojia.model.json.BaseResult;

import java.util.List;

/**
 * Created by changsy on 16/8/29.
 */
public class Cities extends BaseResult {

    
    public List<Area> result;
    
    
    public Cities(String id, String code, String msg, Object result, boolean success) {
        super(id, code, msg, success);
        this.result = (List<Area>) result;
    }
}
