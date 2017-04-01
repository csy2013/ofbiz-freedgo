<style>
    .header {
        text-align: center;
    }
    .header h1 {
        font-size: 200%;
        color: #333;
        margin-top: 30px;
    }
    .header p {
        font-size: 14px;
    }
    .body {
        background-image: url('http://localhost:8080/images/themes/tomahawk/images/b.jpg');
    }
</style>
<link rel="stylesheet" href="/images/themes/amaze/css/amazeui.css"  media="screen,projection" type="text/css" charset="UTF-8"/>
<#if requestAttributes.uiLabelMap?exists><#assign uiLabelMap = requestAttributes.uiLabelMap></#if>
<#assign useMultitenant = Static["org.ofbiz.base.util.UtilProperties"].getPropertyValue("general.properties", "multitenant")>
<#assign username = requestParameters.USERNAME?default((sessionAttributes.autoUserLogin.userLoginId)?default(""))>
<#if username != "">
  <#assign focusName = false>
<#else>
  <#assign focusName = true>
</#if>

 <div class="header">
     <div class="am-g">
         <h1>${uiLabelMap.CommonManagerSystem}</h1>
         <#--<p>Integrated Development Environment<br/>代码编辑，代码生成，界面设计，调试，编译</p>-->
     </div>
     <hr />
 </div>

 <div class="am-g">
     <div class="am-u-lg-6 am-u-md-8 am-u-sm-centered">
         <#--<h3>${uiLabelMap.CommonRegistered}</h3>
         <hr>
         &lt;#&ndash;<div class="am-btn-group">
             <a href="#" class="am-btn am-btn-secondary am-btn-sm"><i class="am-icon-github am-icon-sm"></i> Github</a>
             <a href="#" class="am-btn am-btn-success am-btn-sm"><i class="am-icon-google-plus-square am-icon-sm"></i> Google+</a>
             <a href="#" class="am-btn am-btn-primary am-btn-sm"><i class="am-icon-stack-overflow am-icon-sm"></i> stackOverflow</a>
         </div>&ndash;&gt;
         <br>-->
         <br>

         <form method="post" action="<@ofbizUrl>login</@ofbizUrl>" name="loginform" class="am-form">
             <input type="hidden" name="JavaScriptEnabled" value="N"/>
             <label for="USERNAME">${uiLabelMap.CommonUsername}:</label>
             <input type="text" name="USERNAME" id="USERNAME" value="${username}" size="20"/
             <br>
             <label for="PASSWORD">${uiLabelMap.CommonPassword}:</label>
             <input type="password" name="PASSWORD" id="PASSWORD" value="">
             <br>
             <#--<label for="remember-me">
                 <input id="remember-me" type="checkbox">
                 记住密码
             </label>-->
             <#if ("Y" == useMultitenant) >
                 <#if !requestAttributes.tenantId?exists>
                    <br/>
                     <tr>
                         <td class="label">${uiLabelMap.CommonTenantId}</td>
                         <td><input type="text" name="tenantId" value="${parameters.tenantId?if_exists}" size="20"/></td>
                     </tr>
                 <br/>
                 <#else>
                     <input type="hidden" name="tenantId" value="${requestAttributes.tenantId?if_exists}"/>
                 </#if>
             </#if>
             <br/>
             <div class="am-cf">
                 <input type="submit" name="" value="${uiLabelMap.CommonLogin}" class="am-btn am-btn-primary am-btn-ms am-fl">
                 <a  class="am-btn am-btn-primary   am-btn-ms am-fr" href="<@ofbizUrl>forgotPassword</@ofbizUrl>">${uiLabelMap.CommonForgotYourPassword} ^_^? </a>
             </div>
         </form>
         <hr>

     </div>
 </div>

<#--<center>
  <div class="screenlet login-screenlet">
    <div class="screenlet-title-bar">
      <h3>${uiLabelMap.CommonRegistered}</h3>
    </div>
    <div class="screenlet-body">
      <form method="post" action="<@ofbizUrl>login</@ofbizUrl>" name="loginform">
        <table class="basic-table" cellspacing="0">
          <tr>
            <td class="label">${uiLabelMap.CommonUsername}</td>
            <td><input type="text" name="USERNAME" value="${username}" size="20"/></td>
          </tr>
          <tr>
            <td class="label">${uiLabelMap.CommonPassword}</td>
            <td><input type="password" name="PASSWORD" value="" size="20"/></td>
          </tr>
          <#if ("Y" == useMultitenant) >
              <#if !requestAttributes.tenantId?exists>
                  <tr>
                      <td class="label">${uiLabelMap.CommonTenantId}</td>
                      <td><input type="text" name="tenantId" value="${parameters.tenantId?if_exists}" size="20"/></td>
                  </tr>
              <#else>
                  <input type="hidden" name="tenantId" value="${requestAttributes.tenantId?if_exists}"/>
              </#if>
          </#if>
          <tr>
            <td colspan="2" align="center">
              <input type="submit" value="${uiLabelMap.CommonLogin}"/>
            </td>
          </tr>
        </table>
        <input type="hidden" name="JavaScriptEnabled" value="N"/>
        <br />
        <a href="<@ofbizUrl>forgotPassword</@ofbizUrl>">${uiLabelMap.CommonForgotYourPassword}?</a>
      </form>
    </div>
  </div>
</center>-->

<script language="JavaScript" type="text/javascript">
  document.loginform.JavaScriptEnabled.value = "Y";
  <#if focusName>
    document.loginform.USERNAME.focus();
  <#else>
    document.loginform.PASSWORD.focus();
  </#if>
</script>
