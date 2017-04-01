package com.yuaoq.yabiz.service.dubbo.securityext.api.service;

import org.ofbiz.service.GenericServiceException;

import java.util.Map;

/**
 * Created by tusm on 15/12/15.
 */
public interface SecurityExtService {
    Map<String,Object> checkUserLogin(Map<String, ? extends Object> context) throws GenericServiceException;
}
