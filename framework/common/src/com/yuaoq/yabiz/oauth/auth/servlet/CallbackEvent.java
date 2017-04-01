package com.yuaoq.yabiz.oauth.auth.servlet;


import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.yuaoq.yabiz.oauth.auth.AuthenticationHelper;
import com.yuaoq.yabiz.oauth.auth.result.AuthenticationResult;
import com.yuaoq.yabiz.oauth.broker.servlet.ServletRequestResponseBroker;
import org.ofbiz.base.util.Debug;
import org.ofbiz.base.util.UtilMisc;
import org.ofbiz.common.CommonEvents;
import org.ofbiz.service.GenericServiceException;
import org.ofbiz.service.LocalDispatcher;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.io.Writer;
import java.text.DateFormat;
import java.util.Map;

public class CallbackEvent {
    public static final String module = CallbackEvent.class.getName();
    
    public static String doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        return doPost(request, response);
    }
    
    
    /**
     * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
     */
    public static String doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        AuthenticationHelper ah = new AuthenticationHelper(new ServletRequestResponseBroker(request, response));
        AuthenticationResult ar = ah.handleCallback();
        Map<String, Object> resultData = ar.getDataStore();
        
        //创建用户
//        调用createPersonAndUserLogin
        LocalDispatcher localDispatcher = (LocalDispatcher) request.getAttribute("dispatcher");
        // profileurl";
//       "imageurl";"displayname"; "name"; "nickname"; "openid"; "authtypeid"; "gender"; "province"; "city"; "borthday";
//        1、创建用户
        String imageUrl = (String) resultData.get("imageurl");
        String displayname = (String) resultData.get("displayname");
        String name = (String) resultData.get("name");
        String nickname = (String) resultData.get("nickname");
        String openid = (String) resultData.get("openid");
        String authtypeid = (String) resultData.get("authtypeid");
        String gender = (String) resultData.get("gender");
        String province = (String) resultData.get("province");
        String city = (String) resultData.get("city");
        String birthday = (String) resultData.get("birthday");
        try {


            Map<String, Object> result = localDispatcher.runSync("createOAuthUser", UtilMisc.toMap("imageurl",imageUrl,"displayname",displayname,
                    "nickname",nickname,"gender",gender,"name",name,"openid",openid,"authtypeid",authtypeid,"province",province,"city",city,"birthday",birthday));
            resultData.put("userLoginId", result.get("userLoginId"));
            resultData.put("password",result.get("password"));
            resultData.put("partyId",result.get("partyId"));
        } catch (GenericServiceException e) {
            request.setAttribute("_ERROR_MESSAGE_", e.getMessage());
            return "error";
        }

//

//       3, 返回用户user(email,password)
        Gson gson = new GsonBuilder().registerTypeAdapter(java.util.Date.class, new CommonEvents.UtilDateSerializer())
                .setDateFormat(DateFormat.LONG).create();
        String result = gson.toJson(resultData);
        response.setContentType("application/x-json");
        // jsonStr.length is not reliable for unicode characters
        try {
            response.setContentLength(result.getBytes("UTF8").length);
        } catch (UnsupportedEncodingException e) {
            Debug.logError("Problems with Json encoding: " + e, module);
        }
        
        // return the JSON String
        Writer out;
        try {
            out = response.getWriter();
            out.write(result);
            out.flush();
        } catch (IOException e) {
            Debug.logError(e, module);
        }
        return "success";
    }
}
