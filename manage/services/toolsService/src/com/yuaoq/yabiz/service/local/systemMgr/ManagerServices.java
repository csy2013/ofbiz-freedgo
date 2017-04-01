package com.yuaoq.yabiz.service.local.systemMgr;

import javolution.util.FastList;
import org.ofbiz.base.util.*;
import org.ofbiz.entity.Delegator;
import org.ofbiz.entity.GenericEntityException;
import org.ofbiz.entity.GenericValue;
import org.ofbiz.entity.condition.EntityCondition;
import org.ofbiz.entity.condition.EntityFunction;
import org.ofbiz.entity.condition.EntityOperator;
import org.ofbiz.entity.model.DynamicViewEntity;
import org.ofbiz.entity.model.ModelKeyMap;
import org.ofbiz.entity.util.EntityFindOptions;
import org.ofbiz.entity.util.EntityListIterator;
import org.ofbiz.entity.util.EntityUtil;
import org.ofbiz.party.contact.ContactMechWorker;
import org.ofbiz.service.DispatchContext;
import org.ofbiz.service.GenericServiceException;
import org.ofbiz.service.LocalDispatcher;
import org.ofbiz.service.ServiceUtil;

import java.nio.ByteBuffer;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.Map;

/**
 * Created by changsy on 15/12/21.
 */
public class ManagerServices {

    public static final String module = ManagerServices.class.getName();
    public static final String resource = "SystemMgrUiLabels";
    public static final String resourceError = "SystemMgrErrorUiLabels";

    public static Map<String, Object> findManager(DispatchContext dct, Map<String, ? extends Object> context) {
        Delegator delegator = dct.getDelegator();
        Map<String, Object> result = ServiceUtil.returnSuccess();
        String userLoginId = (String) context.get("userLoginId");
        Locale locale = (Locale) context.get("locale");
        String lookupFlag = (String) context.get("lookupFlag");
        if (lookupFlag == null) lookupFlag = "N";
        String orderFiled = (String) context.get("ORDER_FILED");
        String orderFiledBy = (String) context.get("ORDER_BY");

        result.put("orderFiled", orderFiled == null ? "" : orderFiled);
        result.put("orderBy", orderFiledBy == null ? "" : orderFiledBy);

        String userName = (String) context.get("userName");
        List<GenericValue> userList = FastList.newInstance();
        int userListSize = 0;
        int lowIndex = 0;
        int highIndex = 0;
        // set the page parameters
        int viewIndex = 0;
        try {
            viewIndex = Integer.parseInt((String) context.get("VIEW_INDEX"));
        } catch (Exception e) {
            viewIndex = 0;
        }
        result.put("viewIndex", Integer.valueOf(viewIndex));

        int viewSize = 20;
        try {
            viewSize = Integer.parseInt((String) context.get("VIEW_SIZE"));
        } catch (Exception e) {
            viewSize = 20;
        }
        result.put("viewSize", Integer.valueOf(viewSize));

        // blank param list
        String paramList = "";

        DynamicViewEntity dynamicView = new DynamicViewEntity();

        // define the main condition & expression list
        List<EntityCondition> andExprs = FastList.newInstance();
        EntityCondition mainCond = null;

        List<String> orderBy = FastList.newInstance();
        List<String> fieldsToSelect = FastList.newInstance();

        // default view settings
        dynamicView.addMemberEntity("PT", "Party");
        dynamicView.addAlias("PT", "partyId");
        dynamicView.addAlias("PT", "statusId");
        dynamicView.addAlias("PT", "partyTypeId");
        dynamicView.addAlias("PT", "createdDate");
        dynamicView.addAlias("PT", "lastModifiedDate");
        fieldsToSelect.add("partyId");
        fieldsToSelect.add("statusId");

        if (UtilValidate.isNotEmpty(orderFiled)) {
            orderBy.add(orderFiled + " " + orderFiledBy);
        }

        // ----
        // Person Fields
        // ----

        // modify the dynamic view

        dynamicView.addMemberEntity("PE", "Person");
        dynamicView.addAlias("PE", "name");
        dynamicView.addViewLink("PT", "PE", Boolean.FALSE, ModelKeyMap.makeKeyMapList("partyId"));

        fieldsToSelect.add("name");


        dynamicView.addMemberEntity("UL", "UserLogin");
        dynamicView.addAlias("UL", "userLoginId");
        dynamicView.addAlias("UL", "lastLoginTime");
        dynamicView.addAlias("UL", "enabled");
        dynamicView.addViewLink("PT", "UL", Boolean.FALSE, ModelKeyMap.makeKeyMapList("partyId"));
        fieldsToSelect.add("userLoginId");
        fieldsToSelect.add("lastLoginTime");
        fieldsToSelect.add("enabled");

        // filter on Name
        if (UtilValidate.isNotEmpty(userName)) {
            paramList = paramList + "&userName=" + userName;
            andExprs.add(EntityCondition.makeCondition(EntityFunction.UPPER_FIELD("name"), EntityOperator.LIKE, EntityFunction.UPPER("%" + userName + "%")));
        }

        // filter on user login
        if (UtilValidate.isNotEmpty(userLoginId)) {
            paramList = paramList + "&userLoginId=" + userLoginId;
            // modify the dynamic view
            // add the expr
            andExprs.add(EntityCondition.makeCondition(EntityFunction.UPPER_FIELD("userLoginId"), EntityOperator.LIKE, EntityFunction.UPPER("%" + userLoginId + "%")));
        }

        try {
            List<GenericValue> innerParties = delegator.findByAnd("PartyGroup", UtilMisc.toMap("isInner", "Y"));
            if (innerParties != null && (!innerParties.isEmpty())) {
                //查找内部公司对应的员工
                GenericValue innerParty = EntityUtil.getFirst(innerParties);
                dynamicView.addMemberEntity("PRP", "PartyRelationship");
                dynamicView.addAlias("PRP", "partyIdFrom");
                dynamicView.addViewLink("PT", "PRP", Boolean.FALSE, ModelKeyMap.makeKeyMapList("partyId", "partyIdTo"));
                andExprs.add(EntityCondition.makeCondition(EntityFunction.UPPER_FIELD("partyIdFrom"), EntityOperator.EQUALS, innerParty.get("partyId")));
            }
        } catch (GenericEntityException e) {
            e.printStackTrace();
        }

        // build the main condition
        if (andExprs.size() > 0) mainCond = EntityCondition.makeCondition(andExprs, EntityOperator.AND);


        if (lookupFlag.equals("Y")) {
            try {
                // get the indexes for the partial list
                // get the indexes for the partial list
                lowIndex = viewIndex * viewSize + 1;
                highIndex = (viewIndex + 1) * viewSize;

                // set distinct on so we only get one row per order
                EntityFindOptions findOpts = new EntityFindOptions(true, EntityFindOptions.TYPE_SCROLL_INSENSITIVE, EntityFindOptions.CONCUR_READ_ONLY, -1, highIndex, true);
                // using list iterator
                EntityListIterator pli = delegator.findListIteratorByCondition(dynamicView, mainCond, null, fieldsToSelect, orderBy, findOpts);

                // get the partial list for this page
                userList = pli.getPartialList(lowIndex, viewSize);

                // attempt to get the full size
                userListSize = pli.getResultsSizeAfterPartialList();
                if (highIndex > userListSize) {
                    highIndex = userListSize;
                }

                // close the list iterator
                pli.close();
            } catch (GenericEntityException e) {
                String errMsg = "Failure in party find operation, rolling back transaction: " + e.toString();
                Debug.logError(e, errMsg, module);
                return ServiceUtil.returnError(UtilProperties.getMessage(resource,
                        "PartyLookupPartyError",
                        UtilMisc.toMap("errMessage", e.toString()), locale));
            }
        } else {
            userListSize = 0;
        }

        result.put("userList", userList);
        result.put("userLoginId",userLoginId);
        result.put("userName",userName);
        result.put("userListSize", Integer.valueOf(userListSize));
        result.put("paramList", paramList);
        result.put("highIndex", Integer.valueOf(highIndex));
        result.put("lowIndex", Integer.valueOf(lowIndex));

        return result;
    }

    public static Map<String, Object> addManager(DispatchContext dcx, Map<String, ? extends Object> context) {

        Map<String, Object> result = ServiceUtil.returnSuccess();
        Locale locale = (Locale) context.get("locale");
        String userName = (String) context.get("userName");
        String password = (String) context.get("password");
        String repassword = (String) context.get("rePassword");
        String name = (String) context.get("name");
        String mobile = (String) context.get("mobile");
        String enabled = (String) context.get("enabled");
        String groupId = (String) context.get("groupId");
        ByteBuffer imageData = (ByteBuffer) context.get("uploadedFile");
        String _uploadedFile_fileName = (String) context.get("_uploadedFile_fileName");
        String uploadedFile_contentType = (String) context.get("_uploadedFile_contentType");

        GenericValue userLogin = (GenericValue) context.get("userLogin");

        LocalDispatcher dispatcher = dcx.getDispatcher();
        Delegator delegator = dcx.getDelegator();
        //创建职员
        //创建登录用户
        String partyId = "";
        Map<String, Object> input = UtilMisc.toMap("PASSWORD", password, "CONFIRM_PASSWORD", repassword, "USERNAME", userName, "NAME", name, "USER_MOBILE_CONTACT", mobile, "USE_ADDRESS",
                "false", "ENABLED", enabled);
        try {
            result = dispatcher.runSync("createEmployee", input);

            //创建用户头像
            if (ServiceUtil.isError(result)) {
                return result;
            }
            partyId = (String) result.get("partyId");
            if (imageData != null && imageData.hasArray()) {
                input = UtilMisc.toMap("partyId", partyId, "_uploadedFile_fileName", _uploadedFile_fileName, "uploadedFile", imageData, "_uploadedFile_contentType",
                        uploadedFile_contentType, "partyContentTypeId", "LGOIMGURL", "userLogin", userLogin, "mimeTypeId", uploadedFile_contentType, "statusId", "CTNT_PUBLISHED");
                result = dispatcher.runSync("uploadPartyContentFile", input);
                if (ServiceUtil.isError(result)) {
                    return result;
                }
            }
            //创建职员与公司关系
            //找出对于内部公司party
            String partyIdFrom = "", partyIdTo = "", roleTypeIdFrom = "", roleTypeIdTo = "";
            Date fromDate = UtilDateTime.nowTimestamp();
            List<GenericValue> innerParties = null;
            try {
                innerParties = delegator.findByAnd("PartyGroup", UtilMisc.toMap("isInner", "Y"));
                if (innerParties != null && (!innerParties.isEmpty())) {
                    //查找内部公司对应的员工
                    GenericValue innerParty = EntityUtil.getFirst(innerParties);
                    partyIdFrom = (String) innerParty.get("partyId");
                    partyIdTo = partyId;
                    roleTypeIdFrom = "INTERNAL_ORGANIZATIO";
                    roleTypeIdTo = "EMPLOYEE";


                }
            } catch (GenericEntityException e) {
                Debug.logError(e, e.getMessage(), module);
                return ServiceUtil.returnError(UtilProperties.getMessage(resource,
                        "addManager",
                        UtilMisc.toMap("errorString", e.getMessage()), locale));
            }

            result = dispatcher.runSync("createPartyRelationship", UtilMisc.toMap("partyRelationshipTypeId", "EMPLOYMENT", "partyIdFrom", partyIdFrom, "partyIdTo", partyIdTo, "roleTypeIdFrom", roleTypeIdFrom, "roleTypeIdTo", roleTypeIdTo, "fromDate", fromDate, "userLogin", userLogin));
            if (ServiceUtil.isError(result)) {
                return result;
            }
            //创建员工角色
            if (groupId != null) {
                result = dispatcher.runSync("addUserLoginToSecurityGroup", UtilMisc.toMap("userLoginId", userName, "groupId", groupId, "fromDate", UtilDateTime.nowTimestamp(), "userLogin", userLogin));
                if (ServiceUtil.isError(result)) {
                    return result;
                }
            }

        } catch (GenericServiceException e) {
            Debug.logError(e, e.getMessage(), module);
            return ServiceUtil.returnError(UtilProperties.getMessage(resource,
                    "addManager",
                    UtilMisc.toMap("errorString", e.getMessage()), locale));
        }


        result = ServiceUtil.returnSuccess();
        result.put("partyId", partyId);
        return result;
    }


    public static Map<String, Object> findManagerDetail(DispatchContext dcx, Map<String, ? extends Object> context) {
        Map<String,Object> result = ServiceUtil.returnSuccess();
        String userLoginId = (String) context.get("userLoginId");
        Delegator delegator = dcx.getDelegator();
        GenericValue mobile = null;
        if(userLoginId!=null){
            try {
                GenericValue userLogin = delegator.findByPrimaryKey("UserLogin", UtilMisc.toMap("userLoginId", userLoginId));
                GenericValue person = userLogin.getRelatedOne("Person");
                String partyId = (String)userLogin.get("partyId");
                List<GenericValue> mobiles = ContactMechWorker.getPartyContactMechByPurpose(delegator, partyId, false, "TELECOM_NUMBER", "PHONE_MOBILE");
                if(mobiles!=null) mobile = EntityUtil.getFirst(mobiles);
                List<GenericValue> securityGroups = delegator.findByAnd("UserLoginAndSecurityGroup", UtilMisc.toMap("userLoginId", userLoginId));
                List<GenericValue> logos = delegator.findByAnd("PartyContent", UtilMisc.toMap("partyId", partyId, "partyContentTypeId", "LGOIMGURL"), UtilMisc.toList("fromDate"));
                if(logos!=null) {
                    GenericValue partyContent = EntityUtil.getFirst(EntityUtil.filterByDate(logos));
                    if(partyContent!=null) {
                        GenericValue content = partyContent.getRelatedOne("Content");
                        result.put("partyContent", content);
                    }
                }

                securityGroups = EntityUtil.filterByDate(securityGroups);
                if(securityGroups!=null){
                    GenericValue securityGroup = EntityUtil.getFirst(securityGroups);
                    result.put("userLoginAndSecurityGroup",securityGroup);
                }

                result.put("userLogin",userLogin);
                result.put("person",person);

                result.put("userMobileContact",mobile);
                return result;
            } catch (GenericEntityException e) {
                Debug.logError(e, e.getMessage(), module);
                return ServiceUtil.returnError(e.getMessage());
            }
        }

        return null;
    }

    public static Map<String, Object> modifyManager(DispatchContext dcx, Map<String, ? extends Object> context) {

        Map<String, Object> result = ServiceUtil.returnSuccess();
        Locale locale = (Locale) context.get("locale");
        String userName = (String) context.get("userName");
        String password = (String) context.get("password");
        String repassword = (String) context.get("rePassword");
        String name = (String) context.get("name");
        String mobile = (String) context.get("mobile");
        String enabled = (String) context.get("enabled");
        String groupId = (String) context.get("groupId");
        ByteBuffer imageData = (ByteBuffer) context.get("uploadedFile");
        String _uploadedFile_fileName = (String) context.get("_uploadedFile_fileName");
        String uploadedFile_contentType = (String) context.get("_uploadedFile_contentType");
        String userLoginId = (String) context.get("userLoginId");
        String oldGroupId = (String)context.get("oldGroupId");
        String oldMobile = (String)context.get("oldMobile");
        String partyId = (String)context.get("partyId");
        String contactMechId = (String)context.get("contactMechId");

        GenericValue userLogin = (GenericValue) context.get("userLogin");

        LocalDispatcher dispatcher = dcx.getDispatcher();
        Delegator delegator = dcx.getDelegator();
        //修改职员
        //创建登录用户
        Map<String, Object> input = UtilMisc.toMap("name", name, "partyId", partyId, "userLogin", userLogin);
        try {
            //修改职员
            result = dispatcher.runSync("updatePerson", input);
            if (ServiceUtil.isError(result)) {
                return result;
            }
            //修改用户登录信息
            input = UtilMisc.toMap("userLoginId", userLoginId, "enabled", enabled, "userLogin", userLogin);
            result = dispatcher.runSync("updateUserLoginSecurity",input);
            if (ServiceUtil.isError(result)) {
                return result;
            }
            //修改密码
            if(password!=null && repassword!=null){
                if(!password.equals(repassword)){
                    return ServiceUtil.returnError(UtilProperties.getMessage(resource, "PartyPasswordMatchError", locale));
                }else{
                    input = UtilMisc.toMap("userLoginId", userLoginId, "newPassword", password, "newPasswordVerify", repassword, "userLogin", userLogin);
                    result = dispatcher.runSync("updatePassword",input);
                    if (ServiceUtil.isError(result)) {
                        return result;
                    }
                }
            }else{

            }
//            修改电话号码
            if(mobile==null){

            }
            else if(oldMobile!=null && mobile!=null ){
                if(!mobile.equals(oldMobile)) {
                    result = dispatcher.runSync("updatePartyTelecomNumber", UtilMisc.toMap("contactMechId", contactMechId, "contactMechTypeId", "TELECOM_NUMBER", "partyId", partyId, "contactNumber", mobile, "userLogin", userLogin));
                    if (ServiceUtil.isError(result)) {
                        return result;
                    }
                }
            }else{
                result = dispatcher.runSync("createPartyTelecomNumber", UtilMisc.toMap("contactMechTypeId", "TELECOM_NUMBER", "contactMechPurposeTypeId","PHONE_MOBILE","partyId", partyId, "contactNumber", mobile, "userLogin", userLogin));
                if (ServiceUtil.isError(result)) {
                    return result;
                }
            }

            if (imageData != null && imageData.hasArray() && _uploadedFile_fileName!=null) {
                input = UtilMisc.toMap("partyId", partyId, "_uploadedFile_fileName", _uploadedFile_fileName, "uploadedFile", imageData, "_uploadedFile_contentType",
                        uploadedFile_contentType, "partyContentTypeId", "LGOIMGURL", "userLogin", userLogin, "mimeTypeId", uploadedFile_contentType, "statusId", "CTNT_PUBLISHED");
                result = dispatcher.runSync("uploadPartyContentFile", input);
                if (ServiceUtil.isError(result)) {
                    return result;
                }
            }

            //修改员工角色
            if (groupId != null) {

                if(oldGroupId!=null &&(!oldGroupId.equals(groupId))) {
                    //先删除旧角色 （新增服务）
                    dispatcher.runSync("removeSecurityGroupByUserLogin", UtilMisc.toMap("userLoginId", userName, "userLogin", userLogin));
                    if (ServiceUtil.isError(result)) {
                        return result;
                    }
                    //新增角色
                    result = dispatcher.runSync("addUserLoginToSecurityGroup", UtilMisc.toMap("userLoginId", userName, "groupId", groupId, "fromDate", UtilDateTime.nowTimestamp(), "userLogin", userLogin));
                    if (ServiceUtil.isError(result)) {
                        return result;
                    }
                }else if(oldGroupId==null){
                    //新增角色
                    result = dispatcher.runSync("addUserLoginToSecurityGroup", UtilMisc.toMap("userLoginId", userName, "groupId", groupId, "fromDate", UtilDateTime.nowTimestamp(), "userLogin", userLogin));
                    if (ServiceUtil.isError(result)) {
                        return result;
                    }
                }
            }

        } catch (GenericServiceException e) {
            Debug.logError(e, e.getMessage(), module);
            return ServiceUtil.returnError(UtilProperties.getMessage(resource,
                    "addManager",
                    UtilMisc.toMap("errorString", e.getMessage()), locale));
        }


        result = ServiceUtil.returnSuccess();
        return result;
    }

    /**
     * 根据userloginId找出所有的UserLoginSecurityGroup 置thruDate 为当前时间
     * @param dcx
     * @param context
     * @return
     */
    public static Map<String,Object> removeSecurityGroupByUserLogin(DispatchContext dcx, Map<String, ? extends Object> context) {
        Map<String,Object> result = ServiceUtil.returnSuccess();
        String userLoginId = (String)context.get("userLoginId");
        Delegator delegator = dcx.getDelegator();
        try {
            List<GenericValue> securityGroups = delegator.findByAnd("UserLoginSecurityGroup", UtilMisc.toMap("userLoginId", userLoginId));
            securityGroups = EntityUtil.filterByDate(securityGroups);
            if(securityGroups!=null){
                for(GenericValue securityGroup : securityGroups){
                    securityGroup.set("thruDate", UtilDateTime.nowTimestamp());
                    securityGroup.store();
                }
            }
        } catch (GenericEntityException e) {
            ServiceUtil.returnError(e.getMessage());
        }

        return result;
    }
    public static Map<String, Object> deleteManager(DispatchContext dcx, Map<String, ? extends Object> context) {
        return null;
    }

    public static Map<String, Object> managerDetail(DispatchContext dcx, Map<String, ? extends Object> context) {
        return null;
    }
}
