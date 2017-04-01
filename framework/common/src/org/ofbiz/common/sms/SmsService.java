package org.ofbiz.common.sms;

import com.aliyuncs.DefaultAcsClient;
import com.aliyuncs.IAcsClient;
import com.aliyuncs.exceptions.ClientException;
import com.aliyuncs.exceptions.ServerException;
import com.aliyuncs.profile.DefaultProfile;
import com.aliyuncs.profile.IClientProfile;
import com.aliyuncs.sms.model.v20160927.SingleSendSmsRequest;
import com.aliyuncs.sms.model.v20160927.SingleSendSmsResponse;
import com.google.gson.Gson;
import org.ofbiz.base.util.UtilProperties;
import org.ofbiz.service.DispatchContext;
import org.ofbiz.service.ServiceUtil;

import java.io.UnsupportedEncodingException;
import java.nio.charset.Charset;
import java.util.List;
import java.util.Map;

/**
 * Created by changsy on 2016/11/23.
 */
public class SmsService {
    
    
    public static Map<String, Object> aliyunSendSingleSms(DispatchContext dxt, Map<String, ? extends Object> context) {
        String mobile = (String) context.get("mobile");
        Map paramMap = (Map) context.get("paramMap");
        String params = new Gson().toJson(paramMap);
        Map<String, Object> result = ServiceUtil.returnSuccess();
        String regionId = UtilProperties.getPropertyValue("sms.properties","aliyun.sms.regionId");
        String accessKey = UtilProperties.getPropertyValue("sms.properties","aliyun.sms.access.keyId");
        String accessSecret = UtilProperties.getPropertyValue("sms.properties","aliyun.sms.access.keySecret");
        String signName = UtilProperties.getPropertyValue("sms.properties","aliyun.sms.sign.name");
        try {
            signName = new String(UtilProperties.getPropertyValue("sms.properties","aliyun.sms.sign.name").getBytes("ISO8859-1"),"UTF8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        IClientProfile profile = DefaultProfile.getProfile(regionId, accessKey, accessSecret);
        
        try {
            DefaultProfile.addEndpoint("cn-hangzhou", "cn-hangzhou", "Sms",  "sms.aliyuncs.com");
            String templateCode = (String)context.get("templateCode");
            IAcsClient client = new DefaultAcsClient(profile);
            SingleSendSmsRequest smsRequest = new SingleSendSmsRequest();
            smsRequest.setSignName(signName);
            smsRequest.setTemplateCode(templateCode);
            smsRequest.setParamString(params);
            smsRequest.setRecNum(mobile);
            SingleSendSmsResponse httpResponse = client.getAcsResponse(smsRequest);
            String requestId =  httpResponse.getRequestId();
            result.put("requestId", requestId);
        } catch (ClientException e) {
            result.put("result", "error:" + e.getMessage());
            e.printStackTrace();
            return result;
        }
        result.put("result", "success");
        return result;
    }
}
