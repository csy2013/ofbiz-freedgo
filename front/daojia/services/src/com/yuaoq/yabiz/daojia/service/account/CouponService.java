package com.yuaoq.yabiz.daojia.service.account;

import com.yuaoq.yabiz.daojia.model.json.BaseResult;
import com.yuaoq.yabiz.daojia.model.json.coupon.CouponList;
import com.yuaoq.yabiz.dubbo.provider.service.ServiceImpl;
import org.ofbiz.base.util.FileUtil;
import org.ofbiz.base.util.UtilProperties;
import org.ofbiz.entity.Delegator;
import org.ofbiz.entity.GenericValue;
import org.ofbiz.service.DispatchContext;
import org.ofbiz.service.LocalDispatcher;
import org.ofbiz.service.ServiceUtil;

import java.io.File;
import java.io.IOException;
import java.util.Locale;
import java.util.Map;

/**
 * Created by changsy on 16/9/15.
 */
public class CouponService extends ServiceImpl implements ICouponService {
    
    public Map<String, Object> DaoJia_UpdateCouponRead(DispatchContext dcx, Map<String, ? extends Object> context) {
        Map<String, Object> result = ServiceUtil.returnSuccess();
        Delegator delegator = dcx.getDelegator();
        Locale locale = (Locale) context.get("locale");
        LocalDispatcher dispatcher = dcx.getDispatcher();
        String partyId = (String) context.get("partyId");
        String functionId = (String) context.get("functionId");
        String body = (String) context.get("body");
        String appVersion = (String) context.get("appVersion");
        String appName = (String) context.get("appName");
        String platCode = (String) context.get("platCode");
        String rand = (String) context.get("rand");
        BaseResult ret = new BaseResult("user.updatecouponread", "0", UtilProperties.getMessage("CommonUiLabels", "CommonSuccess", locale), true);
        result.put("resultData", ret);
        return result;
    }
    
    public Map<String, Object> DaoJia_CouponList(DispatchContext dcx, Map<String, ? extends Object> context) {
        Map<String, Object> result = ServiceUtil.returnSuccess();
        Delegator delegator = dcx.getDelegator();
        Locale locale = (Locale) context.get("locale");
        LocalDispatcher dispatcher = dcx.getDispatcher();
        String partyId = (String) context.get("partyId");
        String functionId = (String) context.get("functionId");
        String body = (String) context.get("body");
        String appVersion = (String) context.get("appVersion");
        String appName = (String) context.get("appName");
        String platCode = (String) context.get("platCode");
        String rand = (String) context.get("rand");
        BaseResult ret = new BaseResult("user.couponlist", "0", UtilProperties.getMessage("CommonUiLabels", "CommonSuccess", locale), true);
        String homePath = System.getProperty("ofbiz.home");
        String json = "";
        try {
            json = FileUtil.readString("utf-8", new File(homePath + "/daojia/daojia/config/coupon/couponList.json"));
            CouponList couponList = CouponList.objectFromData(json);
            result.put("resultData", couponList);
        } catch (IOException e) {
            e.printStackTrace();
            ret.setMsg(UtilProperties.getMessage("CommonUiLabels", "CommonError", locale));
            result.put("resultData", ret);
            
        }
        return result;
    }
    
    public Map<String, Object> DaoJia_ExchangeCouponByCode(DispatchContext dcx, Map<String, ? extends Object> context) {
        
        Map<String, Object> result = ServiceUtil.returnSuccess();
        Delegator delegator = dcx.getDelegator();
        Locale locale = (Locale) context.get("locale");
        LocalDispatcher dispatcher = dcx.getDispatcher();
        String partyId = (String) context.get("partyId");
        String functionId = (String) context.get("functionId");
        String body = (String) context.get("body");
        String appVersion = (String) context.get("appVersion");
        String appName = (String) context.get("appName");
        String platCode = (String) context.get("platCode");
        String rand = (String) context.get("rand");
        GenericValue person = null;
        BaseResult ret = new BaseResult("share.exchangeCouponByCode", "0", UtilProperties.getMessage("CommonUiLabels", "CommonSuccess", locale), true);
        result.put("resultData", ret);
        
        return result;
    }
}
