package com.yuaoq.yabiz.service.local.party;

import javolution.util.FastList;
import javolution.util.FastMap;
import org.apache.commons.lang.RandomStringUtils;
import org.ofbiz.base.crypto.HashCrypt;
import org.ofbiz.base.util.*;
import org.ofbiz.entity.Delegator;
import org.ofbiz.entity.GenericEntityException;
import org.ofbiz.entity.GenericValue;
import org.ofbiz.entity.condition.EntityCondition;
import org.ofbiz.entity.util.EntityFindOptions;
import org.ofbiz.entity.util.EntityListIterator;
import org.ofbiz.entity.util.EntityUtil;
import org.ofbiz.service.*;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.Map;

/**
 * Created by changsy on 15/12/16.
 */
public class CustomServices {
    
    public static final String module = CustomServices.class.getName();
    public static final String resource = "PartyUiLabels";
    public static final String resourceError = "PartyErrorUiLabels";
    
    public Map<String, Object> customBaseQuery(DispatchContext ctx, Map<String, ? extends Object> context) {
        Map<String, Object> result = FastMap.newInstance();
        Delegator delegator = ctx.getDelegator();
        String loginId = (String) context.get("loginId");
        Locale locale = (Locale) context.get("locale");
        if (loginId != null) {
            GenericValue userLogin = null;
            try {
                userLogin = delegator.findByPrimaryKey("UserLogin", UtilMisc.toMap("userLoginId", loginId));
                GenericValue person = delegator.findByPrimaryKey("Person", UtilMisc.toMap("partyId", userLogin.get("partyId")));
                result.put("person", person);
                GenericValue party = delegator.findByPrimaryKey("Party", UtilMisc.toMap("partyId", userLogin.get("partyId")));
                result.put("party", party);
                GenericValue partyGroup = delegator.findByPrimaryKey("PartyGroup", UtilMisc.toMap("partyId", userLogin.get("partyId")));
                if (partyGroup != null) {
                    result.put("partyGroup", partyGroup);
                }
            } catch (GenericEntityException e) {
                return ServiceUtil.returnError(UtilProperties.getMessage(resourceError,
                        "PartyServiceImpl.cannot_query_customer_base", locale));
            }
        }
        result.put(ModelService.RESPONSE_MESSAGE, ModelService.RESPOND_SUCCESS);
        return result;
        
    }
    
    public Map<String, Object> customAddressQuery(DispatchContext ctx, Map<String, ? extends Object> context) {
        Map<String, Object> result = FastMap.newInstance();
        Delegator delegator = ctx.getDelegator();
        String loginId = (String) context.get("loginId");
        Locale locale = (Locale) context.get("locale");
        if (loginId != null) {
            GenericValue userLogin = null;
            try {
                userLogin = delegator.findByPrimaryKey("UserLogin", UtilMisc.toMap("userLoginId", loginId));
                
                List<GenericValue> partyAndContactMechList = delegator.findByAnd("PartyAndContactMech", UtilMisc.toMap("partyId", userLogin.get("partyId"), "contactMechTypeId", "POSTAL_ADDRESS"), UtilMisc.toList("-fromDate"));
                partyAndContactMechList = EntityUtil.filterByDate(partyAndContactMechList);
                result.put("contactMech", partyAndContactMechList);
            } catch (GenericEntityException e) {
                return ServiceUtil.returnError(UtilProperties.getMessage(resourceError,
                        "PartyServiceImpl.customAddressQuery.error", locale));
            }
        }
        result.put(ModelService.RESPONSE_MESSAGE, ModelService.RESPOND_SUCCESS);
        return result;
    }
    
    public Map<String, Object> customAddressDetail(DispatchContext ctx, Map<String, ? extends Object> context) {
        
        Map<String, Object> result = FastMap.newInstance();
        Delegator delegator = ctx.getDelegator();
        String loginId = (String) context.get("loginId");
        String contactMechId = (String) context.get("contactMechId");
        Locale locale = (Locale) context.get("locale");
        if (loginId != null) {
            GenericValue userLogin = null;
            try {
                userLogin = delegator.findByPrimaryKey("UserLogin", UtilMisc.toMap("userLoginId", loginId));
                String partyId = (String) userLogin.get("partyId");
                GenericValue contactMech = delegator.findByPrimaryKey("ContactMech", UtilMisc.toMap("contactMechId", contactMechId));
                result.put("contactMech", contactMech);
                List<GenericValue> partyContactMechs = EntityUtil.filterByDate(delegator.findByAnd("PartyContactMech", UtilMisc.toMap("partyId", partyId, "contactMechId", contactMechId)), true);
                if (partyContactMechs != null && (!partyContactMechs.isEmpty())) {
                    GenericValue partyContactMech = EntityUtil.getFirst(partyContactMechs);
                    result.put("partyContactMech", partyContactMech);
                }
                if (contactMech != null) {
                    GenericValue postalAddress = contactMech.getRelatedOne("PostalAddress");
                    result.put("postalAddress", postalAddress);
                    GenericValue telecomNumber = contactMech.getRelatedOne("TelecomNumber");
                    result.put("telecomNumber", telecomNumber);
                }
                
                
            } catch (GenericEntityException e) {
                return ServiceUtil.returnError(UtilProperties.getMessage(resourceError,
                        "PartyServiceImpl.customAddressDetail.error", locale));
            }
        }
        result.put(ModelService.RESPONSE_MESSAGE, ModelService.RESPOND_SUCCESS);
        
        return result;
    }
    
    public Map<String, Object> customProfileDefault(DispatchContext ctx, Map<String, ? extends Object> context) {
        
        Map<String, Object> result = FastMap.newInstance();
        Delegator delegator = ctx.getDelegator();
        String loginId = (String) context.get("loginId");
        String productStoreId = (String) context.get("productStoreId");
        Locale locale = (Locale) context.get("locale");
        if (loginId != null) {
            GenericValue userLogin = null;
            try {
                userLogin = delegator.findByPrimaryKey("UserLogin", UtilMisc.toMap("userLoginId", loginId));
                if (userLogin != null) {
                    GenericValue profiledefs = null;
                    if (productStoreId != null) {
                        profiledefs = delegator.findByPrimaryKey("PartyProfileDefault", UtilMisc.toMap("partyId", userLogin.get("partyId"), "productStoreId", productStoreId));
                    } else {
                        List<GenericValue> profiles = delegator.findByAnd("PartyProfileDefault", UtilMisc.toMap("partyId", userLogin.get("partyId")));
                        if(UtilValidate.isNotEmpty(profiles)){
                            profiledefs = EntityUtil.getFirst(profiles);
                        }
                    }
                    if (profiledefs != null) {
                        result.put("profiledefs", profiledefs);
                    }
                }
            } catch (GenericEntityException e) {
                return ServiceUtil.returnError(UtilProperties.getMessage(resourceError,
                        "PartyServiceImpl.customProfileDefault.error", locale));
            }
        }
        result.put(ModelService.RESPONSE_MESSAGE, ModelService.RESPOND_SUCCESS);
        
        return result;
    }
    
    /**
     * 根据loginId, 类型目的获取用户的联系信息
     *
     * @param dcx
     * @param context
     * @return
     */
    public static Map<String, Object> queryCustomMechByPurposeType(DispatchContext dcx, Map<String, ? extends Object> context) {
        Map result = ServiceUtil.returnSuccess();
        Delegator delegator = dcx.getDelegator();
        String loginId = (String) context.get("loginId");
        List<GenericValue> resultData = new ArrayList<GenericValue>();
        String contactMechPurposeTypeId = null;
        if (context.get("contactMechPurposeTypeId") != null) {
            contactMechPurposeTypeId = (String) context.get("contactMechPurposeTypeId");
        }
        try {
            GenericValue userLogin = delegator.findByPrimaryKey("UserLogin", UtilMisc.toMap("userLoginId", loginId));
            List<GenericValue> partyContactMechs = EntityUtil.filterByDate(delegator.findByAnd("PartyContactMech", UtilMisc.toMap("partyId", userLogin.get("partyId"))), true);
            if (contactMechPurposeTypeId != null) {
                for (int i = 0; i < partyContactMechs.size(); i++) {
                    GenericValue partyContactMech = partyContactMechs.get(i);
                    List<GenericValue> partyContactMechPurposes = EntityUtil.filterByDate(partyContactMech.getRelated("PartyContactMechPurpose"), true);
                    for (GenericValue partyContactMechPurpose : partyContactMechPurposes) {
                        if (!partyContactMechPurpose.get("contactMechPurposeTypeId").equals(contactMechPurposeTypeId)) {
                            partyContactMechs.remove(partyContactMech);
                        }
                    }
                }
                
                result.put("partyContactMechs", partyContactMechs);
            } else {
                result.put("partyContactMechs", partyContactMechs);
            }
            
            
        } catch (GenericEntityException e) {
            ServiceUtil.returnError(e.getMessage());
            e.printStackTrace();
        }
        return result;
    }
    
    /**
     * 获取用户的content内容
     *
     * @param dcx
     * @param context
     * @return
     */
    public static Map<String, Object> queryCustomContent(DispatchContext dcx, Map<String, ? extends Object> context) {
        Map result = ServiceUtil.returnSuccess();
        Delegator delegator = dcx.getDelegator();
        String loginId = (String) context.get("loginId");
        String partyContentTypeId = (String) context.get("partyContentTypeId");
        List resultPartyContents = FastList.newInstance();
        GenericValue userLogin = null;
        try {
            userLogin = delegator.findByPrimaryKey("UserLogin", UtilMisc.toMap("userLoginId", loginId));
            List<GenericValue> partyContents = delegator.findByAnd("PartyContent", UtilMisc.toMap("partyId", userLogin.get("partyId")), UtilMisc.toList("-fromDate"));
            partyContents = EntityUtil.filterByDate(partyContents);
            if (partyContents != null) {
                for (int i = 0; i < partyContents.size(); i++) {
                    GenericValue partyContent = partyContents.get(i);
                    GenericValue content = partyContent.getRelatedOne("Content");
                    GenericValue partyContentType = partyContent.getRelatedOne("PartyContentType");
                    String partyContentTypeId1 = (String) partyContentType.get("partyContentTypeId");
                    if (partyContentTypeId1.equals(partyContentTypeId) || partyContentTypeId.equals("")) {
                        Map partyContentDetail = FastMap.newInstance();
                        partyContentDetail.put("content", content);
                        partyContentDetail.put("partyContent", partyContent);
                        partyContentDetail.put("partyContentTypeId", partyContentType.get("partyContentTypeId"));
                        resultPartyContents.add(partyContentDetail);
                        if (partyContentTypeId.equals("LGOIMGURL")) {
                            //只取第一条
                            break;
                        }
                    }
                }
            }
        } catch (GenericEntityException e) {
            e.printStackTrace();
        }
        result.put("partyContents", resultPartyContents);
        return result;
    }
    
    public Map<String, Object> queryCustomDefaultPostAddress(DispatchContext ctx, Map<String, ? extends Object> context) {
        
        Map<String, Object> result = FastMap.newInstance();
        Delegator delegator = ctx.getDelegator();
        String loginId = (String) context.get("loginId");
        String productStoreId = (String) context.get("productStoreId");
        Locale locale = (Locale) context.get("locale");
        if (loginId != null) {
            GenericValue userLogin = null;
            try {
                userLogin = delegator.findByPrimaryKey("UserLogin", UtilMisc.toMap("userLoginId", loginId));
                if (userLogin != null) {
                    
                    GenericValue profiledefs = delegator.findByPrimaryKey("PartyProfileDefault", UtilMisc.toMap("partyId", userLogin.get("partyId"), "productStoreId", productStoreId));
                    String contactMechId = null;
                    if (profiledefs != null) {
                        contactMechId = profiledefs.get("defaultShipAddr") == null ? null : (String) profiledefs.get("defaultShipAddr");
                    }
                    List<GenericValue> partyAndContactMechList = null;
                    if (contactMechId != null) {
                        partyAndContactMechList = delegator.findByAnd("PartyAndContactMech",
                                UtilMisc.toMap("partyId", userLogin.get("partyId"), "contactMechTypeId", "POSTAL_ADDRESS", "contactMechId", contactMechId), UtilMisc.toList("-fromDate"));
                    } else {
                        partyAndContactMechList = delegator.findByAnd("PartyAndContactMech", UtilMisc.toMap("partyId", userLogin.get("partyId"), "contactMechTypeId", "POSTAL_ADDRESS"), UtilMisc.toList("-fromDate"));
                        
                    }
                    partyAndContactMechList = EntityUtil.filterByDate(partyAndContactMechList);
                    GenericValue partyAndContactMech = EntityUtil.getFirst(partyAndContactMechList);
                    result.put("partyAndContactMech", partyAndContactMech);
                }
            } catch (GenericEntityException e) {
                return ServiceUtil.returnError(UtilProperties.getMessage(resourceError,
                        "PartyServiceImpl.customProfileDefault.error", locale));
            }
        }
        result.put(ModelService.RESPONSE_MESSAGE, ModelService.RESPOND_SUCCESS);
        
        return result;
    }
    
    public Map<String, Object> createOAuthUser(DispatchContext ctx, Map<String, ? extends Object> context) {
        
        LocalDispatcher localDispatcher = ctx.getDispatcher();
        Map<String, Object> result = ServiceUtil.returnSuccess();
        Delegator delegator = ctx.getDelegator();
        String imageUrl = (String) context.get("imageurl");
        String displayname = (String) context.get("displayname");
        String name = (String) context.get("name");
        String nickname = (String) context.get("nickname");
        String openid = (String) context.get("openid");
        String authtypeid = (String) context.get("authtypeid");
        String gender = (String) context.get("gender");
        String province = (String) context.get("province");
        String city = (String) context.get("city");
        String birthday = (String) context.get("birthday");
        Locale locale = (Locale) context.get("locale");
        //如果用户已经存在则,直接找到对应的partyId
        //1、创建用户,生成随机密码
        final char[] availableChars = UtilProperties.getPropertyValue("captcha.properties", "captcha.characters").toCharArray();
        String password = RandomStringUtils.random(8, availableChars);
        try {
            List<GenericValue> oauthUsers = delegator.findByAnd("OauthUser", UtilMisc.toMap("openId", openid, "authTypeId", authtypeid));
            if (UtilValidate.isNotEmpty(oauthUsers)) {
                //修改oauth
                GenericValue authUser = EntityUtil.getFirst(oauthUsers);
                authUser.set("imageUrl", imageUrl);
                authUser.set("displayName", displayname);
                authUser.set("name", name);
                authUser.set("nickName", nickname);
                authUser.set("openId", openid);
                authUser.set("authTypeId", authtypeid);
                authUser.set("gender", gender);
                authUser.set("province", province);
                authUser.set("city", city);
                authUser.set("birthday", birthday);
                
                GenericValue userLogin = delegator.findByPrimaryKey("UserLogin", UtilMisc.toMap("userLoginId", authtypeid + openid));
                if (UtilValidate.isEmpty(userLogin)) {
                    Map<String, Object> newUserLogin = null;
                    try {
                        newUserLogin = localDispatcher.runSync("createPersonAndUserLogin", UtilMisc.toMap("userLoginId", authtypeid + openid,"imageUrl", imageUrl,
                                "nickname", nickname, "gender", gender, "name", name, "currentPassword", password, "currentPasswordVerify", password));
                        authUser.set("partyId", newUserLogin.get("partyId"));
                        result.put("partyId", newUserLogin.get("partyId"));
                        
                    } catch (GenericServiceException e) {
                        e.printStackTrace();
                    }
                } else {
                    //登录之后修改用户密码
                    updateCustomerPassword(userLogin, password, delegator, locale);
                    result.put("partyId", userLogin.get("partyId"));
                    authUser.set("partyId", userLogin.get("partyId"));
                }
                delegator.store(authUser);
                
            } else {
                GenericValue userLogin = delegator.findByPrimaryKey("UserLogin", UtilMisc.toMap("userLoginId", authtypeid + openid));
                String partyId = null;
                if (UtilValidate.isEmpty(userLogin)) {
                    Map<String, Object> newUserLogin = null;
                    try {
                        newUserLogin = localDispatcher.runSync("createPersonAndUserLogin", UtilMisc.toMap("userLoginId", authtypeid + openid,"imageUrl", imageUrl,
                                "nickname", nickname, "gender", gender, "name", name, "currentPassword", password, "currentPasswordVerify", password));
                        partyId = (String) newUserLogin.get("partyId");
                    } catch (GenericServiceException e) {
                        e.printStackTrace();
                    }
                } else {
                    updateCustomerPassword(userLogin, password, delegator, locale);
                    partyId = (String) userLogin.get("partyId");
                }
                
                //result.put("newUserLogin",newUserLogin);
                result.put("partyId", partyId);
                GenericValue user = delegator.makeValue("OauthUser");
                user.setPKFields(context);
                user.set("imageUrl", imageUrl);
                user.set("displayName", displayname);
                user.set("name", name);
                user.set("nickName", nickname);
                user.set("openId", openid);
                user.set("authTypeId", authtypeid);
                user.set("gender", gender);
                user.set("province", province);
                user.set("city", city);
                user.set("birthday", birthday);
                user.set("partyId", partyId);
                
                try {
                    delegator.create(user);
                } catch (GenericEntityException e) {
                    Debug.logError(e, module);
                    return ServiceUtil.returnError(e.getMessage());
                }
            }
        } catch (GenericEntityException e) {
            e.printStackTrace();
        }
        
        result.put("password", password);
        result.put("userLoginId", authtypeid + openid);
        return result;
        
        
    }
    
    
    public static Map<String, Object> updateCustomerPassword(GenericValue userLoginToUpdate, String newPassword, Delegator delegator, Locale locale) {
        Map<String, Object> result = FastMap.newInstance();
        boolean useEncryption = "true".equals(UtilProperties.getPropertyValue("security.properties", "password.encrypt"));
        userLoginToUpdate.set("currentPassword", useEncryption ? HashCrypt.cryptPassword(getHashType(), newPassword) : newPassword, false);
        userLoginToUpdate.set("passwordHint", "", false);
        userLoginToUpdate.set("requirePasswordChange", "N");
        
        try {
            userLoginToUpdate.store();
            createUserLoginPasswordHistory(delegator, (String) userLoginToUpdate.get("userLoginId"), newPassword);
        } catch (GenericEntityException e) {
            Map<String, String> messageMap = UtilMisc.toMap("errorMessage", e.getMessage());
            String errMsg = UtilProperties.getMessage(resource, "loginservices.could_not_change_password_write_failure", messageMap, locale);
            return ServiceUtil.returnError(errMsg);
        }
        
        
        result.put(ModelService.RESPONSE_MESSAGE, ModelService.RESPOND_SUCCESS);
        return result;
    }
    
    public static String getHashType() {
        String hashType = UtilProperties.getPropertyValue("security.properties", "password.encrypt.hash.type");
        
        if (UtilValidate.isEmpty(hashType)) {
            Debug.logWarning("Password encrypt hash type is not specified in security.properties, use SHA", module);
            hashType = "SHA";
        }
        
        return hashType;
    }
    
    private static void createUserLoginPasswordHistory(Delegator delegator, String userLoginId, String currentPassword) throws GenericEntityException {
        int passwordChangeHistoryLimit = 0;
        try {
            passwordChangeHistoryLimit = Integer.parseInt(UtilProperties.getPropertyValue("security.properties", "password.change.history.limit", "0"));
        } catch (NumberFormatException nfe) {
            //No valid value is found so don't bother to save any password history
            passwordChangeHistoryLimit = 0;
        }
        if (passwordChangeHistoryLimit == 0 || passwordChangeHistoryLimit < 0) {
            // Not saving password history, so return from here.
            return;
        }
        
        EntityFindOptions efo = new EntityFindOptions();
        efo.setResultSetType(EntityFindOptions.TYPE_SCROLL_INSENSITIVE);
        EntityListIterator eli = delegator.find("UserLoginPasswordHistory", EntityCondition.makeConditionMap("userLoginId", userLoginId), null, null, UtilMisc.toList("-fromDate"), efo);
        Timestamp nowTimestamp = UtilDateTime.nowTimestamp();
        GenericValue pwdHist;
        if ((pwdHist = eli.next()) != null) {
            // updating password so set end date on previous password in history
            pwdHist.set("thruDate", nowTimestamp);
            pwdHist.store();
            // check if we have hit the limit on number of password changes to be saved. If we did then delete the oldest password from history.
            eli.last();
            int rowIndex = eli.currentIndex();
            if (rowIndex == passwordChangeHistoryLimit) {
                eli.afterLast();
                pwdHist = eli.previous();
                pwdHist.remove();
            }
        }
        eli.close();
        
        // save this password in history
        GenericValue userLoginPwdHistToCreate = delegator.makeValue("UserLoginPasswordHistory", UtilMisc.toMap("userLoginId", userLoginId, "fromDate", nowTimestamp));
        boolean useEncryption = "true".equals(UtilProperties.getPropertyValue("security.properties", "password.encrypt"));
        userLoginPwdHistToCreate.set("currentPassword", useEncryption ? HashCrypt.cryptPassword(getHashType(), currentPassword) : currentPassword);
        userLoginPwdHistToCreate.create();
    }
}
