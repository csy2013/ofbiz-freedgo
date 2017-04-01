<#if security.hasEntityPermission("ORDERMGR", "_UPDATE", session)>
<#--<@htmlScreenTemplate.renderScreenletBegin id="receiveOfflinePayments" title="${uiLabelMap.OrderReceiveOfflinePayments}" collapsed=false/>-->

<form method="post" action="<@ofbizUrl>receiveOfflinePayments/${donePage}</@ofbizUrl>" name="paysetupform" class="form-inline">
    <#if requestParameters.workEffortId?exists>
        <input type="hidden" name="workEffortId" value="${requestParameters.workEffortId}"/>
    </#if>
    <div class="table-responsive">
        <table class="table" cellspacing='0'>
            <tr class="header-row">
                <td width="30%" align="right">${uiLabelMap.OrderPaymentType}</td>
                <td width="1">&nbsp;&nbsp;&nbsp;</td>
                <td width="1">${uiLabelMap.OrderAmount}</td>
                <td width="1">&nbsp;&nbsp;&nbsp;</td>
                <td width="70%">${uiLabelMap.OrderReference}</td>
            </tr>
            <#list paymentMethodTypes as payType>
            <#if payType.paymentMethodTypeId!='CERTIFIED_CHECK' && payType.paymentMethodTypeId!='COMPANY_ACCOUNT' && payType.paymentMethodTypeId!='COMPANY_CHECK'
            && payType.paymentMethodTypeId!='CREDIT_CARD' && payType.paymentMethodTypeId!='EFT_ACCOUNT'
            && payType.paymentMethodTypeId!='EXT_BILLACT' && payType.paymentMethodTypeId!='EXT_GOOGLE_CHECKOUT' && payType.paymentMethodTypeId!='EXT_IDEAL'
            && payType.paymentMethodTypeId!='EXT_PAYPAL' && payType.paymentMethodTypeId!='PERSONAL_CHECK'
            && payType.paymentMethodTypeId!='EXT_WORLDPAY' && payType.paymentMethodTypeId!='FIN_ACCOUNT' && payType.paymentMethodTypeId!='MONEY_ORDER'>
                <tr>
                    <td width="30%" align="right">${payType.get("description",locale)?default(payType.paymentMethodTypeId)}</td>
                    <td width="1">&nbsp;&nbsp;&nbsp;</td>
                    <td width="1"><input type="text" size="7" name="${payType.paymentMethodTypeId}_amount" class="form-control"/></td>
                    <td width="1">&nbsp;&nbsp;&nbsp;</td>
                    <td width="70%"><input type="text" size="15" name="${payType.paymentMethodTypeId}_reference" class="form-control"/></td>
                </tr>
            </#if>
            </#list>
        </table>
    </div>
    <div class="form-group">
        <div class="col-md-5">&nbsp;</div>
        <div class="col-md-7">
            <button type="submit" name="submitBtn" value="${uiLabelMap.CommonSave}"/>
            <#--<a href="javascript:document.paysetupform.submit()" class="btn btn-white btn-sm  pull-right">${uiLabelMap.CommonSave}</a></div>-->
    </div>
        </div>
</form>


<#--<@htmlScreenTemplate.renderScreenletEnd/>-->
<#else>
<h3>${uiLabelMap.OrderViewPermissionError}</h3>
</#if>