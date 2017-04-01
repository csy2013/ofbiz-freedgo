package com.yuaoq.yabiz.daojia.service.account;

import org.ofbiz.service.DispatchContext;

import java.util.Map;

/**
 * Created by changsy on 2017/3/31.
 */
public interface ICouponService {
    
    public Map<String, Object> DaoJia_UpdateCouponRead(DispatchContext dcx, Map<String, ? extends Object> context);
    
    public Map<String, Object> DaoJia_CouponList(DispatchContext dcx, Map<String, ? extends Object> context);
    
    public Map<String, Object> DaoJia_ExchangeCouponByCode(DispatchContext dcx, Map<String, ? extends Object> context);
    
}
