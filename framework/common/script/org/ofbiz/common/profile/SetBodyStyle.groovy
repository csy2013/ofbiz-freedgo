/**
 * Created by tusm on 15/11/20.
 */
//根据用户Id 获取 partyProfile 设置 isNavbarMini 值

import org.ofbiz.base.util.UtilMisc
mini = request.getParameter("mini");
println "userLogin = $userLogin"
resultData = [:]
result = dispatcher.runSync("setUserPreference", UtilMisc.toMap("userPrefTypeId", "VISUAL_THEME_NAVBARMINI", "userPrefGroupTypeId", "GLOBAL_PREFERENCES", "userPrefValue", mini, "userLogin", userLogin));
resultData.put("resultData",result);
request.setAttribute("resultData",resultData);

return "success";