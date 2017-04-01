package com.yuaoq.yabiz.daojia.service.party;

import com.google.gson.Gson;
import com.yuaoq.yabiz.daojia.model.json.BaseResult;
import com.yuaoq.yabiz.daojia.model.json.login.LoginBody;
import com.yuaoq.yabiz.daojia.model.json.party.address.Address;
import com.yuaoq.yabiz.daojia.model.json.party.address.AddressList;
import com.yuaoq.yabiz.daojia.model.json.party.agreement.MeAgreement;
import com.yuaoq.yabiz.daojia.model.json.party.userInfo.UserInfo;
import com.yuaoq.yabiz.daojia.model.json.party.userInfo.UserInfoResult;
import javolution.util.FastList;
import javolution.util.FastMap;
import org.apache.commons.lang.RandomStringUtils;
import org.ofbiz.base.util.*;
import org.ofbiz.common.geo.GeoWorker;
import org.ofbiz.entity.Delegator;
import org.ofbiz.entity.GenericEntityException;
import org.ofbiz.entity.GenericValue;
import org.ofbiz.entity.util.EntityUtil;
import org.ofbiz.party.contact.ContactMechWorker;
import org.ofbiz.party.content.PartyContentWrapper;
import org.ofbiz.securityext.login.LoginEvents;
import org.ofbiz.service.DispatchContext;
import org.ofbiz.service.GenericServiceException;
import org.ofbiz.service.LocalDispatcher;
import org.ofbiz.service.ServiceUtil;
import org.ofbiz.webapp.control.LoginWorker;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.UUID;

/**
 * Created by changsy on 16/9/11.
 */
public class PartyService {
    /**
     * 到家服务用户登录
     *
     * @param request
     * @param response
     * @return
     */
    public static String DaoJia_Login(HttpServletRequest request, HttpServletResponse response) {
        //检查验证码
        Map<String, Object> result = FastMap.newInstance();
        Boolean _PASSED_ = true;
        String checkCode = request.getParameter("CHECK_CODE").trim();
        Delegator delegator = (Delegator) request.getAttribute("delegator");
        
        HttpSession session = request.getSession();
        Map captchaCodeMap = (Map) session.getAttribute("_CAPTCHA_CODE_");
        if (captchaCodeMap != null) {
            String code = (String) captchaCodeMap.get("dj_login");
            if (code != null && code.equals(checkCode)) {
                _PASSED_ = true;
            }
        }
        if (_PASSED_) {
            String userName = request.getParameter("USERNAME").trim();
            String password = request.getParameter("PASSWORD").trim();
            if (userName.equals("") || password.equals("")) {
                result.put("errcode", 6);
                result.put("message", "账号或密码不正确");
                result.put("needauth", true);
                request.setAttribute("resultData", result);
            }
            
            String ret = LoginEvents.storeLogin(request, response);
            String referer = request.getHeader("Referer");
            referer = UtilHttp.decodeURL(referer);
            Map<String, Object> bodyMap = UtilHttp.getQueryStringOnlyParameterMap(referer);
            String body = (String) bodyMap.get("body");
            String returnLink = body.substring(0, body.indexOf(",\"h5Coords\"")) + "}";
            LoginBody bodyLink = LoginBody.objectFromData(returnLink);
            if (ret.equals("requirePasswordChange")) {
                result.put("errcode", 105);
                result.put("succcb", "https://changsy.cn/mobile-daojia/dj/findpassword");
                result.put("needauth", true);
                request.setAttribute("resultData", result);
            }
            if (ret.equals("error")) {
                result.put("errcode", 6);
                result.put("message", "账号或密码不正确");
                result.put("needauth", true);
                request.setAttribute("resultData", result);
            } else {
                GenericValue userLogin = (GenericValue) request.getSession().getAttribute("userLogin");
                result.put("errcode", 0);
                result.put("needauth", true);
                String userId = userLogin.getString("userLoginId");
                result.put("userId", userId);
                String mobile = ContactMechWorker.getMobile(delegator, userId);
                if (UtilValidate.isNotEmpty(mobile)) {
                    StringBuffer p = new StringBuffer();
                    char[] c_mobile = mobile.toCharArray();
                    for (int i = 0; i < c_mobile.length; i++) {
                        char o = c_mobile[i];
                        if (i > 2 && i < (c_mobile.length - 4)) {
                            p.append("*");
                        } else {
                            p.append(o);
                        }
                        
                    }
                    result.put("mobile", p.toString());
                }
                
                //JD_284ubc3b41a4
                result.put("pin", "JD_" + RandomStringUtils.randomAlphabetic(12));
                //b8b3a3b7-7d9f-4ae0-8097-5911ce728203
                result.put("sid", UUID.randomUUID().toString());
                //6732dcac25eecc3ce162b3a8e2a87511
                result.put("h5sid", request.getSession().getId());
                result.put("succcb", response.encodeURL(bodyLink.returnLink));
                
                request.setAttribute("resultData", result);
            }
            
        } else {
            result.put("errcode", 257);
            result.put("message", "图片验证码错误，请重试");
            result.put("needauth", true);
            request.setAttribute("resultData", result);
        }
        
        return "success";
    }
    
    
    public static String DaoJia_GetUserInfo(HttpServletRequest request, HttpServletResponse response) {
        Locale locale = request.getLocale();
        LocalDispatcher dispatcher = (LocalDispatcher) request.getAttribute("dispatcher");
        String result = LoginWorker.checkLogin(request, response);
        UserInfo info = null;
        if (result.equals("success")) {
            GenericValue userLogin = (GenericValue) request.getSession().getAttribute("userLogin");
//     "jdPeasBalance": "8",  "birthday": "1-1-1", "gendar": "0", "nickName": "360buy_csy", "yunMidImageUrl": null,  "yunBigImageUrl": null, "yunSmaImageUrl": null,  "mobile": "137****8361"
            String partyId = userLogin.getString("partyId");
            try {
                Map<String, Object> userRet = dispatcher.runSync("DaoJia_GetUserInfo", UtilMisc.toMap("partyId", partyId));
                if (ServiceUtil.isSuccess(userRet)) {
                    GenericValue person = (GenericValue) userRet.get("person");
                    Map<String, String> image = (Map<String, String>) userRet.get("personLogos");
                    String yunMidImageUrl = "", yunBigImageUrl = "", yunSmaImageUrl = "";
                    String baseUrl = UtilProperties.getMessage("content.properties", "daojia.baseUrl", locale);
                    if (UtilValidate.isNotEmpty(image)) {
                        yunMidImageUrl = baseUrl + image.get("yunMidImageUrl");
                        yunBigImageUrl = baseUrl + image.get("yunBigImageUrl");
                        yunSmaImageUrl = baseUrl + image.get("yunSmaImageUrl");
                        
                    }
                    List<Map<String, Object>> contacts = (List<Map<String, Object>>) userRet.get("contactMechs");
                    String mobile = "";
                    if (UtilValidate.isNotEmpty(contacts)) {
                        for (int i = 0; i < contacts.size(); i++) {
                            Map<String, Object> contactMap = (Map) contacts.get(i);
                            if (contactMap.get("telecomNumber") != null) {
                                GenericValue teleNumber = (GenericValue) contactMap.get("telecomNumber");
                                mobile = teleNumber.getString("contactNumber");
                                mobile = UtilStrings.getMaskString(mobile, 3, 4);
                            }
                        }
                    }
                    UserInfoResult infoResult = new UserInfoResult("8", person.getString("birthDate"), person.getString("gender") == "M" ? "0" : "1", person.getString("nickname"),
                            yunMidImageUrl, yunBigImageUrl, yunSmaImageUrl, mobile);
                    info = new UserInfo("user.getUserInfo", "0", UtilProperties.getMessage("CommonUiLabels", "CommonSuccess", locale), true, infoResult, "0");
                }
                
            } catch (GenericServiceException e) {
                e.printStackTrace();
                info = new UserInfo("user.getUserInfo", "0", UtilProperties.getMessage("CommonUiLabels", "CommonSuccess", locale), true, "GW_LOGIN_GET_PIN_FAILURE", "0");
            }
            
        } else {
            info = new UserInfo("user.getUserInfo", "0", UtilProperties.getMessage("CommonUiLabels", "CommonSuccess", locale), true, "GW_LOGIN_GET_PIN_FAILURE", "0");
        }
        request.setAttribute("resultData", info);
        return "success";
    }
    
    public static Map<String, Object> DaoJia_GetUserInfo(DispatchContext dcx, Map<String, ? extends Object> context) {
        Map<String, Object> result = ServiceUtil.returnSuccess();
        Delegator delegator = dcx.getDelegator();
        Locale locale = (Locale) context.get("locale");
        LocalDispatcher dispatcher = dcx.getDispatcher();
        String partyId = (String) context.get("partyId");
        GenericValue person = null;
        try {
            person = delegator.findByPrimaryKey("Person", UtilMisc.toMap("partyId", partyId));
            
            
            String image = PartyContentWrapper.getPartyContentAsText(person.getRelatedOne("Party"), "LGOIMGURL", locale, "text/html", delegator, dispatcher, false);
            Map<String, String> map = FastMap.newInstance();
            map.put("yunMidImageUrl", image);
            map.put("yunBigImageUrl", image);
            map.put("yunSmaImageUrl", image);
            List<Map<String, Object>> contactMap = ContactMechWorker.getPartyContactMechValueMaps(delegator, partyId, false);
            result.put("person", person);
            result.put("personLogos", map);
            result.put("contactMechs", contactMap);
        } catch (GenericEntityException e) {
            e.printStackTrace();
            return ServiceUtil.returnError(e.getMessage());
        }
        
        return result;
    }
    
    public static Map<String, Object> DaoJia_AddressList(DispatchContext dcx, Map<String, ? extends Object> context) {
        Map<String, Object> result = ServiceUtil.returnSuccess();
        Delegator delegator = dcx.getDelegator();
        Locale locale = (Locale) context.get("locale");
        LocalDispatcher dispatcher = dcx.getDispatcher();
        
        String functionId = (String) context.get("functionId");
        String body = (String) context.get("body");
        String appVersion = (String) context.get("appVersion");
        String appName = (String) context.get("appName");
        String platCode = (String) context.get("platCode");
        String rand = (String) context.get("rand");
        GenericValue userLogin = (GenericValue) context.get("userLogin");
        
        AddressList addressRet = new AddressList("addresspdj.getAddressList", "0", UtilProperties.getMessage("content.properties", "CommonSuccess", locale), true);
        if (UtilValidate.isNotEmpty(userLogin)) {
            String partyId = userLogin.getString("partyId");
            List<GenericValue> postalAddresses = ContactMechWorker.getPartyContactMechByPurpose(delegator, partyId, false, "POSTAL_ADDRESS", "SHIPPING_LOCATION");
            if (UtilValidate.isNotEmpty(postalAddresses)) {
                List addresses = FastList.newInstance();
                for (int i = 0; i < postalAddresses.size(); i++) {
                    GenericValue postalAddress = postalAddresses.get(i);
                    Integer mechId = Integer.valueOf(postalAddress.getString("contactMechId"));
                    String addressName = postalAddress.getString("attnName");
                    String name = postalAddress.getString("toName");
                    String cityId = postalAddress.getString("city");
                    String countyId = postalAddress.getString("countyGeoId");
                    String cityName = GeoWorker.getGeoNameById(cityId, delegator);
                    String countyName = GeoWorker.getGeoNameById(countyId, delegator);
                    String stateProvinceGeoId = postalAddress.getString("stateProvinceGeoId");
                    String provinceGeoName = GeoWorker.getGeoNameById(stateProvinceGeoId, delegator);
                    String poi = postalAddress.getString("address1");
                    String addressDetail = postalAddress.getString("address2");
                    String fullAddress = provinceGeoName + cityName + countyName + poi + postalAddress.getString("address2");
                    String mobile = postalAddress.getString("mobilePhone");
                    long lastUsedTime = postalAddress.getTimestamp("lastUpdatedStamp").getTime();
                    Address address = new Address(mechId, addressName, name, cityId, countyId, cityName, countyName, poi, addressDetail, fullAddress, mobile, lastUsedTime);
                    addresses.add(address);
                }
                
                addressRet.result = addresses;
            }
            
        }
        result.put("resultData", addressRet);
        return result;
    }
    
    public static Map<String, Object> DaoJia_AddAddress(DispatchContext dcx, Map<String, ? extends Object> context) {
        Map<String, Object> result = ServiceUtil.returnSuccess();
        Delegator delegator = dcx.getDelegator();
        Locale locale = (Locale) context.get("locale");
        LocalDispatcher dispatcher = dcx.getDispatcher();
        String functionId = (String) context.get("functionId");
        String body = (String) context.get("body");
        String appVersion = (String) context.get("appVersion");
        String appName = (String) context.get("appName");
        String platCode = (String) context.get("platCode");
        GenericValue userLogin = (GenericValue) context.get("userLogin");
        Address address = Address.objectFromData(body);
        BaseResult ret = new BaseResult("addresspdj.addAddress", "0", UtilProperties.getMessage("CommonUiLabels", "CommonSuccess", locale), true);
        if (UtilValidate.isNotEmpty(address)) {
            Map<String, Object> serviceIn = FastMap.newInstance();
//            serviceIn.put("contactMechId", address.id);
            serviceIn.put("toName", address.name);
            serviceIn.put("mobilePhone", address.mobile);
            serviceIn.put("address2", address.addressDetail);
            serviceIn.put("address1", address.poi);
            serviceIn.put("userLogin", userLogin);
            //city ,county 需要转换
            
            serviceIn.put("partyId", userLogin.getString("partyId"));
            serviceIn.put("roleTypeId", "CUSTOMER");
            serviceIn.put("contactMechPurposeTypeId", "SHIPPING_LOCATION");
            
            serviceIn.put("city", "CN-" + address.cityId);
            String provinceGeoId = "CN-" + address.cityId.substring(0, 2);
            serviceIn.put("stateProvinceGeoId", provinceGeoId);
            serviceIn.put("countyGeoId", "CN-" + address.countyId);
            serviceIn.put("countryGeoId", "CHN");
            
            try {
                Map<String, Object> addressRet = dispatcher.runSync("createPartyPostalAddress", serviceIn);
                if (ServiceUtil.isSuccess(addressRet)) {
                    String contactMechId = (String) addressRet.get("contactMechId");
                    GenericValue postalAddress = delegator.findByPrimaryKey("PostalAddress", UtilMisc.toMap("contactMechId", contactMechId));
                    
                    GenericValue gv = delegator.makeValue("GeoPoint");
                    gv.set("longitude", String.valueOf(address.longitude));
                    gv.set("latitude", String.valueOf(address.latitude));
                    gv.setNextSeqId();
                    gv.create();
                    String pointId = gv.getString("geoPointId");
                    postalAddress.set("geoPointId", pointId);
                    postalAddress.store();
                    
                    Integer mechId = Integer.valueOf(postalAddress.getString("contactMechId"));
                    String addressName = postalAddress.getString("attnName");
                    String name = postalAddress.getString("toName");
                    String cityId = postalAddress.getString("city");
                    String countyId = postalAddress.getString("countyGeoId");
                    String cityName = GeoWorker.getGeoNameById(cityId, delegator);
                    String countyName = GeoWorker.getGeoNameById(countyId, delegator);
                    String stateProvinceGeoId = postalAddress.getString("stateProvinceGeoId");
                    String provinceGeoName = GeoWorker.getGeoNameById(stateProvinceGeoId, delegator);
                    String poi = postalAddress.getString("address1");
                    String addressDetail = postalAddress.getString("address2");
                    String fullAddress = provinceGeoName + cityName + countyName + poi + postalAddress.getString("address2");
                    String mobile = postalAddress.getString("mobilePhone");
                    long lastUsedTime = postalAddress.getTimestamp("lastUpdatedStamp").getTime();
                    Address retAddress = new Address(mechId, addressName, name, cityId, countyId, cityName, countyName, poi, addressDetail, fullAddress, mobile, lastUsedTime);
                    Map<String, Object> retMap = FastMap.newInstance();
                    retMap.put("result", retAddress);
                    retMap.put("id", "addresspdj.addAddress");
                    retMap.put("code", "0");
                    retMap.put("msg", UtilProperties.getMessage("CommonUiLabels", "CommonSuccess", locale));
                    retMap.put("success", true);
                    result.put("resultData", retMap);
                }
            } catch (GenericServiceException e) {
                e.printStackTrace();
                ret.setMsg(UtilProperties.getMessage("CommonUiLabels", "CommonError", locale));
                result.put("resultData", ret);
            } catch (GenericEntityException e) {
                e.printStackTrace();
                ret.setMsg(UtilProperties.getMessage("CommonUiLabels", "CommonError", locale));
                result.put("resultData", ret);
            }
        }
        return result;
    }
    
    public static Map<String, Object> DaoJia_UpdateAddress(DispatchContext dcx, Map<String, ? extends Object> context) {
        Map<String, Object> result = ServiceUtil.returnSuccess();
        Delegator delegator = dcx.getDelegator();
        Locale locale = (Locale) context.get("locale");
        LocalDispatcher dispatcher = dcx.getDispatcher();
        String functionId = (String) context.get("functionId");
        String body = (String) context.get("body");
        String appVersion = (String) context.get("appVersion");
        String appName = (String) context.get("appName");
        String platCode = (String) context.get("platCode");
        Address address = Address.objectFromData(body);
        GenericValue userLogin = (GenericValue) context.get("userLogin");
        
        BaseResult ret = new BaseResult("addresspdj.addAddress", "0", UtilProperties.getMessage("CommonUiLabels", "CommonSuccess", locale), true);
        if (UtilValidate.isNotEmpty(address)) {
            Map<String, Object> serviceIn = FastMap.newInstance();
            serviceIn.put("contactMechId", String.valueOf(address.id));
            serviceIn.put("toName", address.name);
            serviceIn.put("mobilePhone", address.mobile);
            serviceIn.put("address2", address.addressDetail);
            serviceIn.put("address1", address.poi);
            //countryGeoId ,countyGeoId ,stateProvinceGeoId，city  需要转换
            serviceIn.put("city", "CN-" + address.cityId);
            String provinceGeoId = "CN-" + address.cityId.substring(0, 2);
            serviceIn.put("stateProvinceGeoId", provinceGeoId);
            serviceIn.put("countyGeoId", "CN-" + address.countyId);
            serviceIn.put("countryGeoId", "CHN");
            serviceIn.put("userLogin", userLogin);
            serviceIn.put("partyId", userLogin.getString("partyId"));
            
            try {
                Map<String, Object> addressRet = dispatcher.runSync("updatePartyPostalAddress", serviceIn);
                if (ServiceUtil.isSuccess(addressRet)) {
                    String contactMechId = (String) addressRet.get("contactMechId");
                    GenericValue postalAddress = delegator.findByPrimaryKey("PostalAddress", UtilMisc.toMap("contactMechId", contactMechId));
                    if (UtilValidate.isNotEmpty(postalAddress)) {
                        String geoPointId = postalAddress.getString("geoPointId");
                        if (UtilValidate.isNotEmpty(geoPointId)) {
                            GenericValue gv = delegator.findByPrimaryKey("GeoPoint", UtilMisc.toMap("geoPointId", geoPointId));
                            gv.set("longitude", String.valueOf(address.longitude));
                            gv.set("latitude", String.valueOf(address.latitude));
                            gv.store();
                        } else {
                            GenericValue gv = delegator.makeValue("GeoPoint");
                            gv.set("longitude", String.valueOf(address.longitude));
                            gv.set("latitude", String.valueOf(address.latitude));
                            gv.setNextSeqId();
                            gv.create();
                            String pointId = gv.getString("geoPointId");
                            postalAddress.set("geoPointId", pointId);
                            postalAddress.store();
                        }
                    }
                }
            } catch (GenericServiceException e) {
                e.printStackTrace();
                ret.setMsg(UtilProperties.getMessage("CommonUiLabels", "CommonError", locale));
            } catch (GenericEntityException e) {
                e.printStackTrace();
                ret.setMsg(UtilProperties.getMessage("CommonUiLabels", "CommonError", locale));
            }
        }
        result.put("resultData", ret);
        
        return result;
    }
    
    public static Map<String, Object> DaoJia_AddFeedback(DispatchContext dcx, Map<String, ? extends Object> context) {
        Map<String, Object> result = ServiceUtil.returnSuccess();
        Delegator delegator = dcx.getDelegator();
        Locale locale = (Locale) context.get("locale");
        LocalDispatcher dispatcher = dcx.getDispatcher();
        String functionId = (String) context.get("functionId");
        String body = (String) context.get("body");
        String appVersion = (String) context.get("appVersion");
        String appName = (String) context.get("appName");
        String platCode = (String) context.get("platCode");
        GenericValue userLogin = (GenericValue) context.get("userLogin");
        BaseResult ret = new BaseResult("addresspdj.addAddress", "0", UtilProperties.getMessage("CommonUiLabels", "CommonSuccess", locale), true);
        
        Map map = new Gson().fromJson(body, Map.class);
        GenericValue gv = delegator.makeValue("DaoJiaFeedback");
        gv.setNextSeqId();
        gv.set("infoString", map.get("content"));
        if (UtilValidate.isNotEmpty(userLogin)) {
            gv.set("partyId", userLogin.get("partyId"));
        }
        try {
            gv.create();
        } catch (GenericEntityException e) {
            e.printStackTrace();
            ret.setMsg(UtilProperties.getMessage("CommonUiLabels", "CommonError", locale));
        }
        result.put("resultData", ret);
        return result;
    }
    
    public static Map<String, Object> DaoJia_SetDefaultAddress(DispatchContext dcx, Map<String, ? extends Object> context) {
        Map<String, Object> result = ServiceUtil.returnSuccess();
        Delegator delegator = dcx.getDelegator();
        Locale locale = (Locale) context.get("locale");
        LocalDispatcher dispatcher = dcx.getDispatcher();
        String functionId = (String) context.get("functionId");
        String body = (String) context.get("body");
        String appVersion = (String) context.get("appVersion");
        String appName = (String) context.get("appName");
        String platCode = (String) context.get("platCode");
        GenericValue userLogin = (GenericValue) context.get("userLogin");
        BaseResult ret = new BaseResult("addresspdj.addAddress", "0", UtilProperties.getMessage("CommonUiLabels", "CommonSuccess", locale), true);
        
        Map map = new Gson().fromJson(body, Map.class);
        String contactMechId = (String) map.get("id");
        try {
            GenericValue gv = EntityUtil.getFirst(delegator.findList("ProductStore", null, UtilMisc.toSet("productStoreId"), null, null, true));
            if (UtilValidate.isNotEmpty(gv)) {
                Map<String, Object> retser = dispatcher.runSync("setPartyProfileDefaults", UtilMisc.toMap("userLogin", userLogin, "partyId", userLogin.get("partyId"), "defaultShipAddr", contactMechId, "productStoreId", gv.getString("productStoreId")));
                if (!ServiceUtil.isSuccess(retser)) {
                    ret.setMsg(ServiceUtil.getErrorMessage(retser));
                }
            }
        } catch (GenericServiceException e) {
            e.printStackTrace();
            ret.setMsg(UtilProperties.getMessage("CommonUiLabels", "CommonError", locale));
        } catch (GenericEntityException e) {
            e.printStackTrace();
            ret.setMsg(UtilProperties.getMessage("CommonUiLabels", "CommonError", locale));
        }
        result.put("resultData", ret);
        return result;
    }
    
    public static Map<String, Object> DaoJia_DeleteAddress(DispatchContext dcx, Map<String, ? extends Object> context) {
        Map<String, Object> result = ServiceUtil.returnSuccess();
        Delegator delegator = dcx.getDelegator();
        Locale locale = (Locale) context.get("locale");
        LocalDispatcher dispatcher = dcx.getDispatcher();
        String functionId = (String) context.get("functionId");
        String body = (String) context.get("body");
        String appVersion = (String) context.get("appVersion");
        String appName = (String) context.get("appName");
        String platCode = (String) context.get("platCode");
        GenericValue userLogin = (GenericValue) context.get("userLogin");
        BaseResult ret = new BaseResult("addresspdj.addAddress", "0", UtilProperties.getMessage("CommonUiLabels", "CommonSuccess", locale), true);
        
        Map map = new Gson().fromJson(body, Map.class);
        String contactMechId = (String) map.get("id");
        try {
            Map<String, Object> retser = dispatcher.runSync("deletePartyContactMech", UtilMisc.toMap("partyId", userLogin.get("partyId"), "contactMechId", contactMechId, "userLogin", userLogin));
            if (!ServiceUtil.isSuccess(retser)) {
                ret.setMsg(ServiceUtil.getErrorMessage(retser));
            }
        } catch (GenericServiceException e) {
            e.printStackTrace();
            ret.setMsg(UtilProperties.getMessage("CommonUiLabels", "CommonError", locale));
        }
        result.put("resultData", ret);
        return result;
    }
    
    public static Map<String, Object> DaoJia_MeAgreement(DispatchContext dcx, Map<String, ? extends Object> context) {
        Map<String, Object> result = ServiceUtil.returnSuccess();
        Delegator delegator = dcx.getDelegator();
        Locale locale = (Locale) context.get("locale");
        LocalDispatcher dispatcher = dcx.getDispatcher();
        String functionId = (String) context.get("functionId");
        String body = (String) context.get("body");
        String appVersion = (String) context.get("appVersion");
        String appName = (String) context.get("appName");
        String platCode = (String) context.get("platCode");
        String homePath = System.getProperty("ofbiz.home");
        String json = null;
        try {
            json = FileUtil.readString("utf-8", new File(homePath + "/daojia/daojia/config/party/meAgreement.json"));
        } catch (IOException e) {
            e.printStackTrace();
        }
        MeAgreement agreement = MeAgreement.objectFromData(json);
        result.put("resultData", agreement);
        return result;
    }
    
    public static Map<String, Object> DaoJia_UserRedDot(DispatchContext dcx, Map<String, ? extends Object> context) {
        Map<String, Object> result = ServiceUtil.returnSuccess();
        Delegator delegator = dcx.getDelegator();
        Locale locale = (Locale) context.get("locale");
        LocalDispatcher dispatcher = dcx.getDispatcher();
        String functionId = (String) context.get("functionId");
        String body = (String) context.get("body");
        String appVersion = (String) context.get("appVersion");
        String appName = (String) context.get("appName");
        String platCode = (String) context.get("platCode");
        String homePath = System.getProperty("ofbiz.home");
        GenericValue userLogin = (GenericValue) context.get("userLogin");
        
        
        Map<String, Object> retMap = FastMap.newInstance();
        if(UtilValidate.isEmpty(userLogin)) {
            retMap.put("id", "user.redDot");
            retMap.put("msg", "请您登录");
            retMap.put("code", "201");
            retMap.put("detail", "");
            retMap.put("success", false);
        }else{
            retMap.put("id", "user.redDot");
            retMap.put("msg", "成功");
            retMap.put("code", "0");
            Map resultMap = FastMap.newInstance();
            resultMap.put("hasNewCoupon",false);
            resultMap.put("hasWaitPayOrder",false);
            retMap.put("result", resultMap);
            retMap.put("success", false);
        }
        
        result.put("resultData", retMap);
        return result;
    }
    
    /**
     * {"type":"0","code":"0","msg":"请求成功","result":true,"detail":"","success":true}
     * @param request
     * @param response
     * @return
     */
    public static String DaoJia_UserLogout(HttpServletRequest request,HttpServletResponse response){
        Map<String,Object> retMap = FastMap.newInstance();
        String result = LoginWorker.logout(request,response);
        retMap.put("id","userInfo.logout");
        retMap.put("type","0");
        retMap.put("code","0");
        retMap.put("msg","请求成功");
        retMap.put("result",true);
        retMap.put("detail","");
        retMap.put("success",true);
        request.setAttribute("resultData",retMap);
        return result;
    }
    
    
}
