package com.yuaoq.yabiz.daojia.service.account;

import com.yuaoq.yabiz.daojia.model.json.account.AccountInfo;
import org.ofbiz.base.util.FileUtil;
import org.ofbiz.base.util.UtilProperties;
import org.ofbiz.service.DispatchContext;
import org.ofbiz.service.ServiceUtil;

import java.io.File;
import java.io.IOException;
import java.util.Locale;
import java.util.Map;

/**
 * Created by changsy on 16/9/14.
 */
public class AccountService {
    
    public Map<String, Object> DaoJia_AccountInfo(DispatchContext dcx, Map<String, ? extends Object> context) {
        Map<String, Object> result = ServiceUtil.returnSuccess();
        Locale locale = (Locale) context.get("locale");
        String homePath = System.getProperty("ofbiz.home");
        String json = null;
        AccountInfo accountInfo = new AccountInfo("account.qry.accountinfo", "0", UtilProperties.getMessage("content.properties", "CommonSuccess", locale), true, "");
        try {
            json = FileUtil.readString("utf-8", new File(homePath + "/daojia/daojia/config/account/accountInfo.json"));
        } catch (IOException e) {
            e.printStackTrace();
        }
        accountInfo = AccountInfo.objectFromData(json);
        result.put("resultData", accountInfo);
        return result;
    }
    
}
