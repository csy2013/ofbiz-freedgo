package org.ofbiz.webtools;

import javolution.util.FastList;
import javolution.util.FastMap;
import org.ofbiz.base.util.UtilMisc;
import org.ofbiz.base.util.UtilValidate;
import org.ofbiz.entity.Delegator;
import org.ofbiz.entity.GenericEntityException;
import org.ofbiz.entity.GenericPK;
import org.ofbiz.entity.GenericValue;
import org.ofbiz.service.DispatchContext;
import org.ofbiz.service.GenericServiceException;
import org.ofbiz.service.LocalDispatcher;
import org.ofbiz.service.ServiceUtil;

import java.nio.ByteBuffer;
import java.util.List;
import java.util.Map;

/**
 * Created by changsy on 16/4/16.
 */
public class AppVersionService {

    public Map<String, Object> checkAppDevice(DispatchContext dispatchContext, Map<String, ? extends Object> context) {
        Map<String, Object> result = ServiceUtil.returnSuccess();
        String channel_tag = (String) context.get("channel_tag");
        String appId = (String) context.get(("app_id"));

        String platform = (String) context.get("platform");
        String binary_version = (String) context.get("binary_version");
        String snapshot = (String) context.get("snapshot");
        Delegator delegator = dispatchContext.getDelegator();
        GenericValue appChannel = null;
        try {
            List orderBy = FastList.newInstance();
            orderBy.add("snapshot");
            List<GenericValue> channels = delegator.findByAnd("AppChannel", UtilMisc.toMap("appId", appId, "versionId", binary_version,
                    "channelTag", channel_tag, "appType", platform), orderBy);
            if (UtilValidate.isNotEmpty(channels)) {
                appChannel = channels.get(0);
            }
            if (UtilValidate.isNotEmpty(appChannel)) {
                if (UtilValidate.isEmpty(snapshot)) {
                    //说明app 还没有最新的snapshot
                    String contentId = (String) appChannel.get("contentId");
                    String downloadUrl = (String) appChannel.get("downloadUrl");
                    String compatible = (String) appChannel.get("compatible");
                    String available = (String) appChannel.get("available");
                    snapshot = (String) appChannel.get("snapshot");
                    Map<String, String> returnMap = FastMap.newInstance();
                    //content/control/ViewSimpleContent?contentId=contentId
                    returnMap.put("url", downloadUrl + "?contentId=" + contentId);
                    returnMap.put("compatible", compatible);
                    returnMap.put("available", available);
                    returnMap.put("snapshot", snapshot);
                    result.put("data", returnMap);
                } else {
                    if (!appChannel.get("snapshot").equals(snapshot)) {
                        String contentId = (String) appChannel.get("contentId");
                        String downloadUrl = (String) appChannel.get("downloadUrl");
                        String compatible = (String) appChannel.get("compatible");
                        String available = (String) appChannel.get("available");
                        snapshot = (String) appChannel.get("snapshot");
                        Map<String, String> returnMap = FastMap.newInstance();
                    //content/control/ViewSimpleContent?contentId=contentId
                        returnMap.put("url", downloadUrl + "?contentId=" + contentId);
                        returnMap.put("compatible", compatible);
                        returnMap.put("available", available);
                        returnMap.put("snapshot", snapshot);
                        result.put("data", returnMap);
                    }
                }
            }

        } catch (GenericEntityException e) {
            e.printStackTrace();
            return ServiceUtil.returnError(e.getMessage());
        }
        return result;
    }

    public Map<String, Object> createAppVer(DispatchContext dispatchContext, Map<String, ? extends Object> context) {
        Map<String, Object> result = ServiceUtil.returnSuccess();
        ByteBuffer uploadedFile = (ByteBuffer) context.get("uploadedFile");
        String _uploadedFile_fileName = (String) context.get("_uploadedFile_fileName");
        String _uploadedFile_contentType = (String) context.get("_uploadedFile_contentType");
        GenericValue userLogin = (GenericValue) context.get("userLogin");
        String appId = (String) context.get("appId");
        String versionId = (String) context.get("versionId");
        String channelTag = (String) context.get("channelTag");
        String appType = (String) context.get("appType");

        String comments = (String) context.get("comments");
//      String downloadUrl = (String)context.get("downloadUrl");
        String compatible = (String) context.get("compatible");
        String available = (String) context.get("available");
        String snapshot = (String) context.get("snapshot");
        String appName = (String) context.get("appName");
        String downloadUrl = (String) context.get("downloadUrl");

        Delegator delegator = dispatchContext.getDelegator();
        Map serviceIn = UtilMisc.toMap();
        LocalDispatcher dispatcher = dispatchContext.getDispatcher();
        serviceIn.put("dataResourceTypeId", "LOCAL_FILE");
        serviceIn.put("dataTemplateTypeId", "NONE");
        serviceIn.put("mapKey", "APP_VERSION_FILE");
        serviceIn.put("contentName", "app version file");
        serviceIn.put("description", "app version file");
        serviceIn.put("statusId", "CTNT_IN_PROGRESS");
       /* serviceIn.put("contentAssocTypeId"  ,"contentAssocTypeId");
        serviceIn.put("contentIdFrom" ,"contentIdFrom");*/
        serviceIn.put("partyId", userLogin.get("partyId"));
        serviceIn.put("isPublic", "Y");
        serviceIn.put("uploadedFile", uploadedFile);
        serviceIn.put("_uploadedFile_fileName", _uploadedFile_fileName);
        serviceIn.put("_uploadedFile_contentType", _uploadedFile_contentType);
        serviceIn.put("userLogin", userLogin);
        try {
            Map<String, Object> ret = dispatcher.runSync("createContentFromUploadedFile", serviceIn);
            if (ServiceUtil.isError(ret)) {
                return ServiceUtil.returnError(ServiceUtil.getErrorMessage(ret));
            }
            String contentId = (String) ret.get("contentId");

            GenericValue appChannel = delegator.makeValue("AppChannel", UtilMisc.toMap("appId", appId, "versionId", versionId, "channelTag", channelTag, "appType", appType,
                    "comments", comments, "compatible", compatible, "appName", appName, "available", available, "downloadUrl", downloadUrl, "snapshot", snapshot, "contentId", contentId));
            appChannel.setNextSeqId();
            delegator.create(appChannel);
        } catch (GenericServiceException e) {
            e.printStackTrace();
            return ServiceUtil.returnError(e.getMessage());
        } catch (GenericEntityException e) {
            e.printStackTrace();
            return ServiceUtil.returnError(e.getMessage());
        }


        return result;
    }

    public Map<String, Object> updateAppVer(DispatchContext dispatchContext, Map<String, ? extends Object> context) {
        Map<String, Object> result = ServiceUtil.returnSuccess();

        ByteBuffer uploadedFile = (ByteBuffer) context.get("uploadedFile");
        String _uploadedFile_fileName = (String) context.get("_uploadedFile_fileName");
        String _uploadedFile_contentType = (String) context.get("_uploadedFile_contentType");
        GenericValue userLogin = (GenericValue) context.get("userLogin");
        String appId = (String) context.get("appId");
        String versionId = (String) context.get("versionId");
        String channelTag = (String) context.get("channelTag");
        String appType = (String) context.get("appType");

        String comments = (String) context.get("comments");
//      String downloadUrl = (String)context.get("downloadUrl");
        String compatible = (String) context.get("compatible");
        String available = (String) context.get("available");
        String snapshot = (String) context.get("snapshot");
        String appName = (String) context.get("appName");
        String contentId = (String) context.get("contentId");
        String downloadUrl = (String) context.get("downloadUrl");
        String appChannelId = (String) context.get("appChannelId");
        Delegator delegator = dispatchContext.getDelegator();
        Map serviceIn = UtilMisc.toMap();
        LocalDispatcher dispatcher = dispatchContext.getDispatcher();
        serviceIn.put("dataResourceTypeId", "LOCAL_FILE");
        serviceIn.put("dataTemplateTypeId", "NONE");
        serviceIn.put("mapKey", "APP_VERSION_FILE");
        serviceIn.put("contentName", "app version file");
        serviceIn.put("description", "app version file");
        serviceIn.put("statusId", "CTNT_IN_PROGRESS");
       /* serviceIn.put("contentAssocTypeId"  ,"contentAssocTypeId");
        serviceIn.put("contentIdFrom" ,"contentIdFrom");*/
//        serviceIn.put("partyId" ,userLogin.get("partyId"));
        serviceIn.put("isPublic", "Y");
        serviceIn.put("uploadedFile", uploadedFile);
        serviceIn.put("_uploadedFile_fileName", _uploadedFile_fileName);
        serviceIn.put("_uploadedFile_contentType", _uploadedFile_contentType);
        serviceIn.put("userLogin", userLogin);
        serviceIn.put("contentId", contentId);


        try {
            GenericValue content = delegator.findByPrimaryKey("Content", UtilMisc.toMap("contentId", contentId));
            serviceIn.put("dataResourceId", content.get("dataResourceId"));
            Map<String, Object> ret = dispatcher.runSync("updateContentAndUploadedFile", serviceIn);
            if (ServiceUtil.isError(ret)) {
                return ServiceUtil.returnError(ServiceUtil.getErrorMessage(ret));
            }

            GenericValue appChannel = delegator.makeValue("AppChannel", UtilMisc.toMap("appChannelId",appChannelId,"appId", appId, "versionId", versionId, "channelTag", channelTag, "appType", appType,
                    "comments", comments, "compatible", compatible, "appName", appName, "available", available, "downloadUrl", downloadUrl, "snapshot", snapshot, "contentId", contentId));

            delegator.store(appChannel);
        } catch (GenericServiceException e) {
            e.printStackTrace();
            return ServiceUtil.returnError(e.getMessage());
        } catch (GenericEntityException e) {
            e.printStackTrace();
            return ServiceUtil.returnError(e.getMessage());
        }


        return result;
    }

    public Map<String, Object> deleteAppVer(DispatchContext dcx, Map<String, ? extends Object> context) {
        Map<String, Object> result = ServiceUtil.returnSuccess();
        String appChannelId = (String) context.get("appChannelId");
        String versionId = (String) context.get("versionId");
        String channelTag = (String) context.get("channelTag");
        String appType = (String) context.get("appType");
        String snapshot = (String) context.get("snapshot");
        Delegator delegator = dcx.getDelegator();
        GenericPK pk = delegator.makePK("AppChannel", UtilMisc.toMap("appChannelId", appChannelId));
        try {
            delegator.removeByPrimaryKey(pk);
        } catch (GenericEntityException e) {
            e.printStackTrace();
            return ServiceUtil.returnError(e.getMessage());
        }
        return result;
    }


}
