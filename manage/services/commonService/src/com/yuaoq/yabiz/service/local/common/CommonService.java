package com.yuaoq.yabiz.service.local.common;

import javolution.util.FastMap;
import org.ofbiz.base.util.StringUtil;
import org.ofbiz.base.util.UtilMisc;
import org.ofbiz.base.util.UtilValidate;
import org.ofbiz.entity.Delegator;
import org.ofbiz.entity.GenericEntityException;
import org.ofbiz.entity.GenericValue;
import org.ofbiz.service.DispatchContext;
import org.ofbiz.service.ModelService;

import java.util.List;
import java.util.Map;

/**
 * Created by changsy on 15/12/19.
 */
public class CommonService {

    public static Map<String,Object> getStorePaymentOptions(DispatchContext dcx,Map<String,? extends Object> context){
        Map<String,Object> resultData = FastMap.newInstance();
        String productStoreId = (String)context.get("productStoreId");
        Delegator delegator = dcx.getDelegator();
        GenericValue productStore = null;
        try {
            productStore = delegator.findByPrimaryKey("ProductStore", UtilMisc.toMap("productStoreId", productStoreId));
            List<GenericValue> productStorePaymentSettingList = productStore.getRelatedCache("ProductStorePaymentSetting");
            resultData.put("productStorePaymentSettingList", productStorePaymentSettingList);
        } catch (GenericEntityException e) {
            e.printStackTrace();
        }
        resultData.put(ModelService.RESPONSE_MESSAGE, ModelService.RESPOND_SUCCESS);
        return resultData;

    }

    public static Map<String,Object> entityFieldExists(DispatchContext dcx,Map<String,? extends Object> context){
        Map<String,Object> resultData = FastMap.newInstance();
        String entityName = (String)context.get("entityName");
        String field = (String)context.get("fieldName");
        String value = (String)context.get("keyValue");

        if(value.indexOf(":")!=-1){
            List<String> keyValue = StringUtil.split(value,":");
            value = keyValue.get(1);
           if(UtilValidate.isEmpty(field)){
               field = keyValue.get(0);
           }
        }
        Delegator delegator = dcx.getDelegator();
        List<GenericValue> entities = null;
        try {
            entities = delegator.findByAnd(entityName, UtilMisc.toMap(field, value));
//            entities = EntityUtil.filterByDate(entities);
            if(UtilValidate.isNotEmpty(entities)){
                resultData.put("entityData",entities.get(0));
                resultData.put(ModelService.RESPONSE_MESSAGE, ModelService.RESPOND_SUCCESS);
            }else{
                resultData.put(ModelService.RESPONSE_MESSAGE, ModelService.RESPOND_FAIL);
            }

        } catch (GenericEntityException e) {
            e.printStackTrace();
        }

        return resultData;
    }
}
