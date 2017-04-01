<!DOCTYPE html>
<html>
<head>

    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>YA | Login</title>

    <link href="/images/themes/bootcss/css/bootstrap.min.css" rel="stylesheet"/>
    <link href="/images/font-awesome/css/font-awesome.css" rel="stylesheet"/>
    <link href="/images/themes/bootcss/css/app.css" rel="stylesheet"/>

    <link href="/images/themes/bootcss/css/animate.css" rel="stylesheet"/>
    <link href="/images/themes/bootcss/css/style.css" rel="stylesheet"/>

</head>
<#if requestAttributes.uiLabelMap?exists><#assign uiLabelMap = requestAttributes.uiLabelMap></#if>
<#assign useMultitenant = Static["org.ofbiz.base.util.UtilProperties"].getPropertyValue("general.properties", "multitenant")>
<#assign username = requestParameters.USERNAME?default((sessionAttributes.autoUserLogin.userLoginId)?default(""))>
<#if username != "">
    <#assign focusName = false>
<#else>
    <#assign focusName = true>
</#if>

<body class="gray-bg">

<div class="middle-box text-center loginscreen  animated fadeInDown">
    <div>
        <div>

            <h1 class="logo-name">Y A</h1>

        </div>
        <h3>Welcome to ${uiLabelMap.CommonManagerSystem}</h3>

    <#escape x as x?html>
        <#if requestAttributes.errorMessageList?has_content><#assign errorMessageList=requestAttributes.errorMessageList></#if>
        <#if requestAttributes.eventMessageList?has_content><#assign eventMessageList=requestAttributes.eventMessageList></#if>
        <#if requestAttributes.serviceValidationException?exists><#assign serviceValidationException = requestAttributes.serviceValidationException></#if>
        <#if requestAttributes.uiLabelMap?has_content><#assign uiLabelMap = requestAttributes.uiLabelMap></#if>

        <#if !errorMessage?has_content>
            <#assign errorMessage = requestAttributes._ERROR_MESSAGE_?if_exists>
        </#if>
        <#if !errorMessageList?has_content>
            <#assign errorMessageList = requestAttributes._ERROR_MESSAGE_LIST_?if_exists>
        </#if>
        <#if !eventMessage?has_content>
            <#assign eventMessage = requestAttributes._EVENT_MESSAGE_?if_exists>
        </#if>
        <#if !eventMessageList?has_content>
            <#assign eventMessageList = requestAttributes._EVENT_MESSAGE_LIST_?if_exists>
        </#if>

    <#-- display the error messages -->
        <#if (errorMessage?has_content || errorMessageList?has_content)>
            <div class="row">
                <div class="col-md-12">


                            <div class="alert alert-danger alert-dismissable">
                                <button type="button" class="close" data-dismiss="alert" aria-hidden="true">×</button>
                                <h4><i class="icon fa fa-ban"></i> ${StringUtil.wrapString(uiLabelMap.CommonFollowingErrorsOccurred)}:</h4>
                                <#if errorMessage?has_content>
                                ${StringUtil.wrapString(errorMessage)}
                                </#if>
                                <#if errorMessageList?has_content>
                                    <#list errorMessageList as errorMsg>
                                    ${StringUtil.wrapString(errorMsg)}
                                    </#list>
                                </#if>
                            </div>
                        </div>


            </div>
        </#if>

    <#-- display the event messages -->
        <#if (eventMessage?has_content || eventMessageList?has_content)>
            <div class="row">
                <div class="col-md-12">


                            <div class="alert alert-info alert-dismissable">
                                <button type="button" class="close" data-dismiss="alert" aria-hidden="true">×</button>
                                <h4><i class="icon fa fa-info"></i> ${StringUtil.wrapString(uiLabelMap.CommonFollowingOccurred)}:</h4>
                                <#if eventMessage?has_content>
                                ${StringUtil.wrapString(eventMessage)}
                                </#if>
                                <#if eventMessageList?has_content>
                                    <#list eventMessageList as eventMsg>
                                    ${StringUtil.wrapString(eventMsg)}
                                    </#list>
                                </#if>
                            </div>
                        </div>


            </div>
        </#if>
    </#escape>
    <#--<p>Perfectly designed and precisely prepared admin theme with over 50 pages with extra new web app views.-->
    <#--<!--Continually expanded and constantly improved Inspinia Admin Them (IN+)&ndash;&gt;-->
    <#--</p>-->
    <#--<p>. To see it in action.</p>-->

        <form class="m-t" role="form" name="loginform" action="<@ofbizUrl>login</@ofbizUrl>" method="post">
            <input type="hidden" name="JavaScriptEnabled" value="N"/>

            <div class="form-group">
                <input name="USERNAME" id="USERNAME" value="admin" class="form-control"
                       placeholder="${uiLabelMap.CommonUsername}" required="" >
            </div>
            <div class="form-group">
                <input type="password" name="PASSWORD" id="PASSWORD" class="form-control" value="changsy"
                       placeholder="${uiLabelMap.CommonPassword}" required="">
            </div>

        <#if ("Y" == useMultitenant) >
            <#if !requestAttributes.tenantId?exists>
                <div class="form-group">
                    <input type="text" name="tenantId" value="${parameters.tenantId?if_exists}"
                           class="form-control" placeholder="${uiLabelMap.CommonTenantId}" >
                </div>
            <#else>
                <input type="hidden" name="tenantId" value="${requestAttributes.tenantId?if_exists}"/>
            </#if>
        </#if>

            <button type="submit" class="btn btn-primary block full-width m-b">登录</button>

            <a href=“<@ofbizUrl>forgotPassword</@ofbizUrl>”>
                <small>${uiLabelMap.CommonForgotYourPassword}?</small>
            </a>

            <p class="text-muted text-center">
                <small>Do not have an account?</small>
            </p>
            <a class="btn btn-sm btn-white btn-block" href="#">Create an account</a>
        </form>


<!-- Mainly scripts -->
<script src="/images/jquery/jquery-2.1.1.min.js"></script>
<script src="/images/themes/bootcss/js/bootstrap.min.js"></script>

<script language="JavaScript" type="text/javascript">
    document.loginform.JavaScriptEnabled.value = "Y";
    <#if focusName>
    document.loginform.USERNAME.focus();
    <#else>
    document.loginform.PASSWORD.focus();
    </#if>
</script>