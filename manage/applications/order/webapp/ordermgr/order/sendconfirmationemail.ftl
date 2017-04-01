<#if security.hasEntityPermission("ORDERMGR", "_SEND_CONFIRMATION", session)>

    <@htmlScreenTemplate.renderScreenletBegin id="OrderSendConfirmationEmail"  title="${uiLabelMap.OrderSendConfirmationEmail}"></@htmlScreenTemplate.renderScreenletBegin>

<form method="post" action="<@ofbizUrl>sendconfirmationmail/${donePage}</@ofbizUrl>" name="sendConfirmationForm" class="form-horizontal">
    <input type="hidden" name="orderId" value="${orderId?if_exists}"/>
    <#if ! productStoreEmailSetting?exists>
        <#assign productStoreEmailSetting = {} />
    </#if>
    <input type="hidden" name="partyId" value="${partyId?if_exists}"/>
    <input type="hidden" name="contentType" value="${productStoreEmailSetting.contentType?default("")}"/>

    <div class="form-group">
        <label class="control-label col-md-4">${uiLabelMap.OrderSendConfirmationEmailSubject}&nbsp;</label>
        <div class="col-md-6">
            <input type="text" size="40" name="subject" class="form-control"
                   value="${productStoreEmailSetting.subject?default(uiLabelMap.OrderOrderConfirmation + " " + uiLabelMap.OrderNbr + orderId)?replace("\\$\\{orderId\\}",orderId,"r")}"/>
        </div>
    </div>
    <div class="form-group">
        <label class="control-label col-md-4">${uiLabelMap.OrderSendConfirmationEmailSendTo}&nbsp;</label>

        <div class="col-md-6">
            <input type="text" size="40" name="sendTo" value="${sendTo}" class="form-control"/>
        </div>
    </div>

    <div class="form-group">
        <label class="control-label col-md-4">${uiLabelMap.OrderSendConfirmationEmailCCTo}&nbsp;</label>

        <div class="col-md-6">
            <input type="text" size="40" name="sendCc" class="form-control" value="${productStoreEmailSetting.ccAddress?default("")}"/>
        </div>
    </div>
    <div class="form-group">
        <label class="control-label col-md-4">${uiLabelMap.OrderSendConfirmationEmailBCCTo}&nbsp;</label>

        <div class="col-md-6">
            <input type="text" size="40" name="sendBcc" class="form-control" value="${productStoreEmailSetting.bccAddress?default("")}"/>
        </div>
    </div>
    <div class="form-group">
        <label class="control-label col-md-4">${uiLabelMap.CommonFrom}&nbsp;</label>

        <div class="col-md-6">
            <#if productStoreEmailSetting.fromAddress?exists>
                <input type="hidden" name="sendFrom" value="${productStoreEmailSetting.fromAddress}"/>
            <#else>
                <input type="text" size="40" name="sendFrom" class="form-control" value=""/>
            </#if>
        </div>
    </div>
    <div class="form-group">
        <label class="control-label col-md-4">${uiLabelMap.OrderSendConfirmationEmailContentType}&nbsp;</label>

        <div class="col-md-6">${productStoreEmailSetting.contentType?default("text/html")}</div>
    </div>
    <div class="form-group">
        <label class="control-label col-md-4">${uiLabelMap.OrderSendConfirmationEmailBody}&nbsp;</label>

        <div class="col-md-6">
            <textarea name="body" rows="30" class="form-control" cols="80">${screens.render(productStoreEmailSetting.bodyScreenLocation?default(""))}</textarea>
        </div>
    </div>
    <div class="form-group">
        <div class="col-md-6"> &nbsp;
        </div>
        <div class="col-md-6">
            <a href="javascript:document.sendConfirmationForm.submit()" class="btn btn-primary btn-sm pull-right">${uiLabelMap.CommonSend}</a>
        </div>
    </div>
</form>

<@htmlScreenTemplate.renderScreenletEnd/>
<#else>
<h3>${uiLabelMap.OrderViewPermissionError}</h3>
</#if>