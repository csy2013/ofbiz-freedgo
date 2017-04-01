<#assign seq = 0/>
<div class="panel panel-default f-s-12">
<#if orderPaymentPreferences?has_content>
    <#list orderPaymentPreferences as orderPaymentPreference>

        <#assign paymentList = orderPaymentPreference.getRelated("Payment")>
        <#assign pmBillingAddress = {}>
        <#assign oppStatusItem = orderPaymentPreference.getRelatedOne("StatusItem")>
        <#-- try the paymentMethod first; if paymentMethodId is specified it overrides paymentMethodTypeId -->
        <#--先找的对应的订单支付是否有PaymentMethod 如果没有 执行paymentMethodTypeId: EXT_BILLACT FIN_ACCOUNT-->
        <#assign paymentMethod = orderPaymentPreference.getRelatedOne("PaymentMethod")?if_exists>
        <#if !paymentMethod?has_content>
            <#assign paymentMethodType = orderPaymentPreference.getRelatedOne("PaymentMethodType")>
        </#if>

        <div class="panel-heading">
            <div class="text-right">
                <#if oppStatusItem.statusId == 'PAYMENT_NOT_RECEIVED'>
                    <@htmlScreenTemplate.renderModalPage id="AccountingReceivePayment_${seq}" name="AccountingReceivePayment_${seq}" modalTitle="${StringUtil.wrapString(uiLabelMap.AccountingReceivePayment)}"
                    modalUrl="receivepayment?${StringUtil.wrapString(paramString)}" description="${uiLabelMap.AccountingReceivePayment}" buttonType="custom"  buttonStyle="btn btn-white btn-xs m-r-10"/>
                </#if>
                <#if (!orderHeader.statusId.equals("ORDER_COMPLETED")) && !(orderHeader.statusId.equals("ORDER_REJECTED")) && !(orderHeader.statusId.equals("ORDER_CANCELLED"))>
                    <#if orderPaymentPreference.statusId != "PAYMENT_SETTLED">

                        <#--<a href="javascript:document.CancelOrderPaymentPreference_${orderPaymentPreference.orderPaymentPreferenceId}.submit()"
                           class="btn btn-white btn-xs">${uiLabelMap.CommonCancel}</a>

                        <form name="CancelOrderPaymentPreference_${orderPaymentPreference.orderPaymentPreferenceId}" method="post"
                              action="<@ofbizUrl>updateOrderPaymentPreference</@ofbizUrl>">
                            <input type="hidden" name="orderId" value="${orderId}"/>
                            <input type="hidden" name="orderPaymentPreferenceId" value="${orderPaymentPreference.orderPaymentPreferenceId}"/>
                            <input type="hidden" name="statusId" value="PAYMENT_CANCELLED"/>
                            <input type="hidden" name="checkOutPaymentId" value="${paymentMethod.paymentMethodTypeId?if_exists}"/>
                        </form>-->
                        <@htmlTemplate.renderConfirmField id="CancelOrderPaymentPreference_${orderPaymentPreference.orderPaymentPreferenceId}" name="CancelOrderPaymentPreference_${orderPaymentPreference.orderPaymentPreferenceId}"
                        confirmUrl="updateOrderPaymentPreference" buttonType="custom"  buttonStyle="btn btn-white btn-xs m-r-10"
                        confirmMessage="确定取消该支付" confirmTitle="订单管理-支付取消" description="取消"
                        targetParameterIter="orderId:'${orderId}',orderPaymentPreferenceId:'${orderPaymentPreference.orderPaymentPreferenceId}',statusId:'PAYMENT_CANCELLED',checkOutPaymentId:'${paymentMethod.paymentMethodTypeId?if_exists}'"/>


                    </#if>
                </#if>
            </div>
            <h4 class="panel-title">${uiLabelMap.AccountingPaymentInformation}</h4>
        </div>
    <div class="panel-body">

        <#assign orderTypeId = orderReadHelper.getOrderTypeId()>

    <#-- order payment status -->

        <div class="form-horizontal">

            <div class="form-group">
                <div class="text-right col-md-2">支付方式:</div>
                <div class="col-md-4"> ${paymentMethodType.get("description",locale)?if_exists}</div>

                <div class="text-right col-md-2">支付金额:</div>
                <div class="col-md-4"><#if orderPaymentPreference.maxAmount?has_content><@ofbizCurrency amount=orderPaymentPreference.maxAmount?default(0.00) isoCode=currencyUomId/></#if></div>
            </div>
            <div class="form-group">
                <div class="text-right col-md-2">支付方式:</div>
                <div class="col-md-4"> <#if oppStatusItem?exists>${oppStatusItem.get("description",locale)}<#else>${orderPaymentPreference.statusId}</#if></div>

            <#-- invoices -->
                <#if invoices?has_content>

                    <div class="text-right col-md-2">${uiLabelMap.OrderInvoices}:</div>

                    <div class="col-md-4">
                        <#list invoices as invoice>
                        ${uiLabelMap.CommonNbr}<a href="/accounting/control/invoiceOverview?invoiceId=${invoice}&amp;externalLoginKey=${externalLoginKey}"
                                                  class="btn btn-white btn-xs">${invoice}</a>
                            (<a target="_BLANK" href="/accounting/control/invoice.pdf?invoiceId=${invoice}&amp;externalLoginKey=${externalLoginKey}"
                                class="btn btn-white btn-xs">PDF</a>)

                        </#list>
                    </div>
                </#if>
            </div>
        </div>
    </#list>
</div>
<#else >
    <div class="panel-heading">
        <h4 class="panel-title">${uiLabelMap.AccountingPaymentInformation}</h4>
    </div>
    <div class="panel-body">
        <div class="text-danger f-s-16">客户暂未支付</div>
    </div>
</#if>
</div>
