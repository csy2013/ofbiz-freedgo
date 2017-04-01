<#macro maskSensitiveNumber cardNumber>
    <#assign cardNumberDisplay = "">
    <#if cardNumber?has_content>
        <#assign size = cardNumber?length - 4>
        <#if (size > 0)>
            <#list 0 .. size-1 as foo>
                <#assign cardNumberDisplay = cardNumberDisplay + "*">
            </#list>
            <#assign cardNumberDisplay = cardNumberDisplay + cardNumber[size .. size + 3]>
        <#else>
        <#-- but if the card number has less than four digits (ie, it was entered incorrectly), display it in full -->
            <#assign cardNumberDisplay = cardNumber>
        </#if>
    </#if>
${cardNumberDisplay?if_exists}
</#macro>
<#assign seq = 0/>
<@htmlScreenTemplate.renderScreenletBegin id="AccountingPaymentInformation" title="${uiLabelMap.AccountingPaymentInformation}"/>
<#--<div class="panel panel-default">
    <div class="panel-heading">
        <h4 class="panel-title">${uiLabelMap.AccountingPaymentInformation} </h4>
    </div>
    <div class="panel-body">-->
        <div class="table-responsive">
            <table class="table .table-valign-top" cellspacing='0'>
            <#assign orderTypeId = orderReadHelper.getOrderTypeId()>
            <#if orderTypeId == "PURCHASE_ORDER">
                <tr>
                    <th>${uiLabelMap.AccountingPaymentID}</th>
                    <th>${uiLabelMap.CommonTo}</th>
                    <th>${uiLabelMap.CommonAmount}</th>
                    <th>${uiLabelMap.CommonStatus}</th>
                </tr>
                <#list orderPaymentPreferences as orderPaymentPreference>
                    <#assign payments = orderPaymentPreference.getRelated("Payment")>
                    <#list payments as payment>
                        <#assign statusItem = payment.getRelatedOne("StatusItem")>
                        <#assign partyName = delegator.findOne("PartyNameView", {"partyId" : payment.partyIdTo}, true)>
                        <tr>
                            <#if security.hasPermission("PAY_INFO_VIEW", session) || security.hasPermission("PAY_INFO_ADMIN", session)>
                                <td><a href="/accounting/control/paymentOverview?paymentId=${payment.paymentId}">${payment.paymentId}</a></td>
                            <#else>
                                <td>${payment.paymentId}</td>
                            </#if>
                            <td>${partyName.groupName?if_exists}${partyName.lastName?if_exists} ${partyName.firstName?if_exists} ${partyName.middleName?if_exists}
                                <#if security.hasPermission("PARTYMGR_VIEW", session) || security.hasPermission("PARTYMGR_ADMIN", session)>
                                    [<a href="/partymgr/control/viewprofile?partyId=${partyId}">${partyId}</a>]
                                <#else>
                                    [${partyId}]
                                </#if>
                            </td>
                            <td><@ofbizCurrency amount=payment.amount?if_exists/></td>
                            <td>${statusItem.description}</td>
                        </tr>
                    </#list>
                </#list>
            <#-- invoices -->
                <#if invoices?has_content>

                    <tr>
                        <td align="right">&nbsp;<span >${uiLabelMap.OrderInvoices}</span></td>
                        
                        <td valign="top">
                            <#list invoices as invoice>
                                <div>${uiLabelMap.CommonNbr}<a href="/accounting/control/invoiceOverview?invoiceId=${invoice}&amp;externalLoginKey=${externalLoginKey}"
                                                               class="btn btn-white btn-xs">${invoice}</a>
                                    (<a target="_BLANK" href="/accounting/control/invoice.pdf?invoiceId=${invoice}&amp;externalLoginKey=${externalLoginKey}" class="btn btn-white btn-xs">PDF</a>)
                                </div>
                            </#list>
                        </td>
                        
                    </tr>
                </#if>
            <#else>

            <#-- order payment status -->
                <tr>
                    <td>&nbsp;${uiLabelMap.OrderStatusHistory}</td>

                    <td>
                        <#assign orderPaymentStatuses = orderReadHelper.getOrderPaymentStatuses()>
                        <#if orderPaymentStatuses?has_content>
                            <#list orderPaymentStatuses as orderPaymentStatus>
                                <#assign statusItem = orderPaymentStatus.getRelatedOne("StatusItem")?if_exists>
                                <#if statusItem?has_content>
                                    <div>
                                    ${statusItem.get("description",locale)} <#if orderPaymentStatus.statusDatetime?has_content>
                                        - ${Static["org.ofbiz.base.util.UtilFormatOut"].formatDateTime(orderPaymentStatus.statusDatetime, "", locale, timeZone)!}</#if>
                                        &nbsp;
                                    ${uiLabelMap.CommonBy} - [${orderPaymentStatus.statusUserLogin?if_exists}]
                                    </div>
                                </#if>
                            </#list>
                        </#if>
                    </td>
                    
                </tr>

                <#if orderPaymentPreferences?has_content || billingAccount?has_content || invoices?has_content>
                    <#list orderPaymentPreferences as orderPaymentPreference>
                        <#assign paymentList = orderPaymentPreference.getRelated("Payment")>
                        <#assign pmBillingAddress = {}>
                        <#assign oppStatusItem = orderPaymentPreference.getRelatedOne("StatusItem")>
                        <#if outputted?default("false") == "true">

                        </#if>
                        <#assign outputted = "true">
                    <#-- try the paymentMethod first; if paymentMethodId is specified it overrides paymentMethodTypeId -->
                    <#--先找的对应的订单支付是否有PaymentMethod 如果没有 执行paymentMethodTypeId: EXT_BILLACT\FIN_ACCOUNT\-->
                        <#assign paymentMethod = orderPaymentPreference.getRelatedOne("PaymentMethod")?if_exists>
                        <#if !paymentMethod?has_content>
                            <#assign paymentMethodType = orderPaymentPreference.getRelatedOne("PaymentMethodType")>
                            <#if paymentMethodType.paymentMethodTypeId == "EXT_BILLACT">
                                <#assign outputted = "false">
                            <#-- billing account -->
                                <#if billingAccount?exists>
                                    <#if outputted?default("false") == "true">

                                    </#if>
                                    <tr>
                                        <td align="right">
                                        <#-- billing accounts require a special OrderPaymentPreference because it is skipped from above section of OPPs -->
                                            <div>支付方式:<span >${uiLabelMap.AccountingBillingAccount}</span>&nbsp;
                                                <#if billingAccountMaxAmount?has_content>
                                                    <br/>${uiLabelMap.OrderPaymentMaximumAmount}: <@ofbizCurrency amount=billingAccountMaxAmount?default(0.00) isoCode=currencyUomId/>
                                                </#if>
                                            </div>
                                        </td>
                                        
                                        <td valign="top">
                                            <table class="table" cellspacing='0'>
                                                <tr>
                                                    <td valign="top">
                                                    ${uiLabelMap.CommonNbr}<a
                                                            href="/accounting/control/EditBillingAccount?billingAccountId=${billingAccount.billingAccountId}&amp;externalLoginKey=${externalLoginKey}"
                                                            class="btn btn-white btn-xs">${billingAccount.billingAccountId}</a> - ${billingAccount.description?if_exists}
                                                    </td>
                                                    <td valign="top" align="right">
                                                        <#if orderPaymentPreference.statusId != "PAYMENT_SETTLED" && orderPaymentPreference.statusId != "PAYMENT_RECEIVED">
                                                            <@htmlScreenTemplate.renderModalPage id="AccountingReceivePayment_${seq}" name="AccountingReceivePayment_${seq}" modalTitle="${StringUtil.wrapString(uiLabelMap.AccountingReceivePayment)}"
                                                            modalUrl="receivepayment?${StringUtil.wrapString(paramString)}" description="${uiLabelMap.AccountingReceivePayment}"/>
                                                            <#assign seq = seq+1>
                                                            <#--<a href="<@ofbizUrl>receivepayment?${paramString}</@ofbizUrl>" class="btn btn-white btn-xs">${uiLabelMap.AccountingReceivePayment}</a>-->
                                                        </#if>
                                                    </td>
                                                </tr>
                                            </table>
                                        </td>
                                        <td>
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
                                                    confirmUrl="updateOrderPaymentPreference" buttonType="button"
                                                    confirmMessage="确定取消该支付" confirmTitle="订单管理-支付取消" description="取消"
                                                    targetParameterIter="orderId:'${orderId}',orderPaymentPreferenceId:'${orderPaymentPreference.orderPaymentPreferenceId}',statusId:'PAYMENT_CANCELLED',checkOutPaymentId:'${paymentMethod.paymentMethodTypeId?if_exists}'"/>
                                                    
                                                </#if>
                                            </#if>
                                        </td>
                                    </tr>
                                </#if>
                            <#elseif paymentMethodType.paymentMethodTypeId == "FIN_ACCOUNT">
                                <#assign finAccount = orderPaymentPreference.getRelatedOne("FinAccount")?if_exists/>
                                <#if (finAccount?has_content)>
                                    <#assign gatewayResponses = orderPaymentPreference.getRelated("PaymentGatewayResponse")>
                                    <#assign finAccountType = finAccount.getRelatedOne("FinAccountType")?if_exists/>
                                    <tr>
                                        <td align="right">

                                                <span >支付方式:${uiLabelMap.AccountingFinAccount}</span>
                                                <#if orderPaymentPreference.maxAmount?has_content>
                                                    <br/>${uiLabelMap.OrderPaymentMaximumAmount}
                                                    : <@ofbizCurrency amount=orderPaymentPreference.maxAmount?default(0.00) isoCode=currencyUomId/>
                                                </#if>

                                        </td>
                                        
                                        <td valign="top">

                                                <#if (finAccountType?has_content)>
                                                ${finAccountType.description?default(finAccountType.finAccountTypeId)}&nbsp;
                                                </#if>
                                                #${finAccount.finAccountCode?default(finAccount.finAccountId)} (<a
                                                    href="/accounting/control/EditFinAccount?finAccountId=${finAccount.finAccountId}&amp;externalLoginKey=${externalLoginKey}"
                                                    class="btn btn-white btn-xs">${finAccount.finAccountId}</a>)
                                                <br/>
                                            ${finAccount.finAccountName?if_exists}
                                                <br/>

                                            <#-- Authorize and Capture transactions -->

                                                    <#if orderPaymentPreference.statusId != "PAYMENT_SETTLED">
                                                        <a href="/accounting/control/AuthorizeTransaction?orderId=${orderId?if_exists}&amp;orderPaymentPreferenceId=${orderPaymentPreference.orderPaymentPreferenceId}&amp;externalLoginKey=${externalLoginKey}"
                                                           class="btn btn-white btn-xs">${uiLabelMap.AccountingAuthorize}</a>
                                                    </#if>
                                                    <#if orderPaymentPreference.statusId == "PAYMENT_AUTHORIZED">
                                                        <a href="/accounting/control/CaptureTransaction?orderId=${orderId?if_exists}&amp;orderPaymentPreferenceId=${orderPaymentPreference.orderPaymentPreferenceId}&amp;externalLoginKey=${externalLoginKey}"
                                                           class="btn btn-white btn-xs">${uiLabelMap.AccountingCapture}</a>
                                                    </#if>


                                            <#if gatewayResponses?has_content>

                                                    <hr/>
                                                    <#list gatewayResponses as gatewayResponse>
                                                        <#assign transactionCode = gatewayResponse.getRelatedOne("TranCodeEnumeration")>
                                                    ${(transactionCode.get("description",locale))?default("Unknown")}:
                                                        <#if gatewayResponse.transactionDate?has_content>${Static["org.ofbiz.base.util.UtilFormatOut"].formatDateTime(gatewayResponse.transactionDate, "", locale, timeZone)!} </#if>
                                                        <@ofbizCurrency amount=gatewayResponse.amount isoCode=currencyUomId/><br/>
                                                        (<span >${uiLabelMap.OrderReference}</span>&nbsp;${gatewayResponse.referenceNum?if_exists}
                                                        <span >${uiLabelMap.OrderAvs}</span>&nbsp;${gatewayResponse.gatewayAvsResult?default("N/A")}
                                                        <span >${uiLabelMap.OrderScore}</span>&nbsp;${gatewayResponse.gatewayScoreResult?default("N/A")})
                                                        <a href="/accounting/control/ViewGatewayResponse?paymentGatewayResponseId=${gatewayResponse.paymentGatewayResponseId}&amp;externalLoginKey=${externalLoginKey}"
                                                           class="btn btn-white btn-xs">${uiLabelMap.CommonDetails}</a>
                                                        <#if gatewayResponse_has_next>
                                                            <hr/></#if>
                                                    </#list>

                                            </#if>
                                        </td>
                                        <td>
                                            <#if (!orderHeader.statusId.equals("ORDER_COMPLETED")) && !(orderHeader.statusId.equals("ORDER_REJECTED")) && !(orderHeader.statusId.equals("ORDER_CANCELLED"))>
                                                <#if orderPaymentPreference.statusId != "PAYMENT_SETTLED">

                                                      <#--  <a href="javascript:document.CancelOrderPaymentPreference_${orderPaymentPreference.orderPaymentPreferenceId}.submit()"
                                                           class="btn btn-white btn-xs">${uiLabelMap.CommonCancel}</a>

                                                        <form name="CancelOrderPaymentPreference_${orderPaymentPreference.orderPaymentPreferenceId}" method="post"
                                                              action="<@ofbizUrl>updateOrderPaymentPreference</@ofbizUrl>">
                                                            <input type="hidden" name="orderId" value="${orderId}"/>
                                                            <input type="hidden" name="orderPaymentPreferenceId" value="${orderPaymentPreference.orderPaymentPreferenceId}"/>
                                                            <input type="hidden" name="statusId" value="PAYMENT_CANCELLED"/>
                                                            <input type="hidden" name="checkOutPaymentId" value="${paymentMethod.paymentMethodTypeId?if_exists}"/>
                                                        </form>-->
                                                    <@htmlTemplate.renderConfirmField id="CancelOrderPaymentPreference_${orderPaymentPreference.orderPaymentPreferenceId}" name="CancelOrderPaymentPreference_${orderPaymentPreference.orderPaymentPreferenceId}"
                                                    confirmUrl="updateOrderPaymentPreference" buttonType="button"
                                                    confirmMessage="确定取消该支付" confirmTitle="订单管理-支付取消" description="取消"
                                                    targetParameterIter="orderId:'${orderId}',orderPaymentPreferenceId:'${orderPaymentPreference.orderPaymentPreferenceId}',statusId:'PAYMENT_CANCELLED',checkOutPaymentId:'${paymentMethod.paymentMethodTypeId?if_exists}'"/>

                                                </#if>
                                            </#if>
                                        </td>
                                    </tr>
                                    <#if paymentList?has_content>
                                        <tr>
                                            <td align="right">
                                                <div>&nbsp;<span >${uiLabelMap.AccountingInvoicePayments}</span></div>
                                            </td>
                                            
                                            <td>
                                                <div>
                                                    <#list paymentList as paymentMap>
                                                        <a href="/accounting/control/paymentOverview?paymentId=${paymentMap.paymentId}&amp;externalLoginKey=${externalLoginKey}"
                                                           class="btn btn-white btn-xs">${paymentMap.paymentId}</a><#if paymentMap_has_next><br/></#if>
                                                    </#list>
                                                </div>
                                            </td>
                                        </tr>
                                    </#if>
                                </#if>
                            <#else>
                                <tr>
                                    <td align="right">
                                        <div>支付方式:<span >${paymentMethodType.get("description",locale)?if_exists}</span>&nbsp;
                                            <#if orderPaymentPreference.maxAmount?has_content>
                                                <br/>${uiLabelMap.OrderPaymentMaximumAmount}: <@ofbizCurrency amount=orderPaymentPreference.maxAmount?default(0.00) isoCode=currencyUomId/>
                                            </#if>
                                        </div>
                                    </td>
                                    
                                    <#if paymentMethodType.paymentMethodTypeId != "EXT_OFFLINE" && paymentMethodType.paymentMethodTypeId != "EXT_PAYPAL" && paymentMethodType.paymentMethodTypeId != "EXT_COD">
                                        <td>
                                            <div>
                                                <#if orderPaymentPreference.maxAmount?has_content>
                                                    <br/>${uiLabelMap.OrderPaymentMaximumAmount}
                                                    : <@ofbizCurrency amount=orderPaymentPreference.maxAmount?default(0.00) isoCode=currencyUomId/>
                                                </#if>
                                                <br/>&nbsp;[<#if oppStatusItem?exists>${oppStatusItem.get("description",locale)}<#else>${orderPaymentPreference.statusId}</#if>]
                                            </div>
                                        <#--
                                        <div><@ofbizCurrency amount=orderPaymentPreference.maxAmount?default(0.00) isoCode=currencyUomId/>&nbsp;-&nbsp;${(orderPaymentPreference.authDate.toString())?if_exists}</div>
                                        <div>&nbsp;<#if orderPaymentPreference.authRefNum?exists>(${uiLabelMap.OrderReference}: ${orderPaymentPreference.authRefNum})</#if></div>
                                        -->
                                        </td>
                                    <#else>
                                        <td align="right">
                                            <@htmlScreenTemplate.renderModalPage id="AccountingReceivePayment_${seq}" name="AccountingReceivePayment_${seq}" modalTitle="${StringUtil.wrapString(uiLabelMap.AccountingReceivePayment)}"
                                            modalUrl="receivepayment?${StringUtil.wrapString(paramString)}" description="${uiLabelMap.AccountingReceivePayment}"/>
                                            <#assign seq = seq+1>
                                            <#--<a href="<@ofbizUrl>receivepayment?${paramString}</@ofbizUrl>" class="btn btn-white btn-xs">${uiLabelMap.AccountingReceivePayment}</a>-->
                                        </td>
                                    </#if>
                                    <td>
                                        <#if (!orderHeader.statusId.equals("ORDER_COMPLETED")) && !(orderHeader.statusId.equals("ORDER_REJECTED")) && !(orderHeader.statusId.equals("ORDER_CANCELLED"))>
                                            <#if orderPaymentPreference.statusId != "PAYMENT_SETTLED">
                                                <div>
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
                                                    confirmUrl="updateOrderPaymentPreference" buttonType="button"
                                                    confirmMessage="确定取消该支付" confirmTitle="订单管理-支付取消" description="取消"
                                                    targetParameterIter="orderId:'${orderId}',orderPaymentPreferenceId:'${orderPaymentPreference.orderPaymentPreferenceId}',statusId:'PAYMENT_CANCELLED',checkOutPaymentId:'${paymentMethod.paymentMethodTypeId?if_exists}'"/>

                                                </div>
                                            </#if>
                                        </#if>
                                    </td>
                                </tr>
                                <#if paymentList?has_content>
                                    <tr>
                                        <td align="right">
                                            <div>&nbsp;<span >${uiLabelMap.AccountingInvoicePayments}</span></div>
                                        </td>
                                        
                                        <td>
                                            <div>
                                                <#list paymentList as paymentMap>
                                                    <a href="/accounting/control/paymentOverview?paymentId=${paymentMap.paymentId}&amp;externalLoginKey=${externalLoginKey}"
                                                       class="btn btn-white btn-xs">${paymentMap.paymentId}</a><#if paymentMap_has_next><br/></#if>
                                                </#list>
                                            </div>
                                        </td>
                                    </tr>
                                </#if>
                            </#if>
                        <#else>
                        <#--是信用卡,\是电子资金转账\礼品卡-->
                            <#if paymentMethod.paymentMethodTypeId?if_exists == "CREDIT_CARD">
                                <#assign gatewayResponses = orderPaymentPreference.getRelated("PaymentGatewayResponse")>
                                <#assign creditCard = paymentMethod.getRelatedOne("CreditCard")?if_exists>
                                <#if creditCard?has_content>
                                    <#assign pmBillingAddress = creditCard.getRelatedOne("PostalAddress")?if_exists>
                                </#if>
                                <tr>
                                    <td align="right">
                                        <div>支付方式:<span >${uiLabelMap.AccountingCreditCard}</span>
                                            <#if orderPaymentPreference.maxAmount?has_content>
                                                <br/>${uiLabelMap.OrderPaymentMaximumAmount}: <@ofbizCurrency amount=orderPaymentPreference.maxAmount?default(0.00) isoCode=currencyUomId/>
                                            </#if>
                                        </div>
                                    </td>
                                    
                                    <td valign="top">
                                        <div>
                                            <#if creditCard?has_content>
                                                <#if creditCard.companyNameOnCard?exists>${creditCard.companyNameOnCard}<br/></#if>
                                                <#if creditCard.titleOnCard?has_content>${creditCard.titleOnCard}&nbsp;</#if>
                                            ${creditCard.firstNameOnCard?default("N/A")}&nbsp;
                                                <#if creditCard.middleNameOnCard?has_content>${creditCard.middleNameOnCard}&nbsp;</#if>
                                            ${creditCard.lastNameOnCard?default("N/A")}
                                                <#if creditCard.suffixOnCard?has_content>&nbsp;${creditCard.suffixOnCard}</#if>
                                                <br/>

                                                <#if security.hasEntityPermission("PAY_INFO", "_VIEW", session)>
                                                ${creditCard.cardType}
                                                    <@maskSensitiveNumber cardNumber=creditCard.cardNumber?if_exists/>
                                                ${creditCard.expireDate}
                                                    &nbsp;[<#if oppStatusItem?exists>${oppStatusItem.get("description",locale)}<#else>${orderPaymentPreference.statusId}</#if>]
                                                <#else>
                                                ${Static["org.ofbiz.party.contact.ContactHelper"].formatCreditCard(creditCard)}
                                                    &nbsp;[<#if oppStatusItem?exists>${oppStatusItem.get("description",locale)}<#else>${orderPaymentPreference.statusId}</#if>]
                                                </#if>
                                                <br/>

                                            <#-- Authorize and Capture transactions -->
                                                <div>
                                                    <#if orderPaymentPreference.statusId != "PAYMENT_SETTLED">
                                                        <a href="/accounting/control/AuthorizeTransaction?orderId=${orderId?if_exists}&amp;orderPaymentPreferenceId=${orderPaymentPreference.orderPaymentPreferenceId}&amp;externalLoginKey=${externalLoginKey}"
                                                           class="btn btn-white btn-xs">${uiLabelMap.AccountingAuthorize}</a>
                                                    </#if>
                                                    <#if orderPaymentPreference.statusId == "PAYMENT_AUTHORIZED">
                                                        <a href="/accounting/control/CaptureTransaction?orderId=${orderId?if_exists}&amp;orderPaymentPreferenceId=${orderPaymentPreference.orderPaymentPreferenceId}&amp;externalLoginKey=${externalLoginKey}"
                                                           class="btn btn-white btn-xs">${uiLabelMap.AccountingCapture}</a>
                                                    </#if>
                                                </div>
                                            <#else>
                                            ${uiLabelMap.CommonInformation} ${uiLabelMap.CommonNot} ${uiLabelMap.CommonAvailable}
                                            </#if>
                                        </div>
                                        <#if gatewayResponses?has_content>
                                            <div>
                                                <hr/>
                                                <#list gatewayResponses as gatewayResponse>
                                                    <#assign transactionCode = gatewayResponse.getRelatedOne("TranCodeEnumeration")>
                                                ${(transactionCode.get("description",locale))?default("Unknown")}:
                                                    <#if gatewayResponse.transactionDate?has_content>${Static["org.ofbiz.base.util.UtilFormatOut"].formatDateTime(gatewayResponse.transactionDate, "", locale, timeZone)!} </#if>
                                                    <@ofbizCurrency amount=gatewayResponse.amount isoCode=currencyUomId/><br/>
                                                    (<span >${uiLabelMap.OrderReference}</span>&nbsp;${gatewayResponse.referenceNum?if_exists}
                                                    <span >${uiLabelMap.OrderAvs}</span>&nbsp;${gatewayResponse.gatewayAvsResult?default("N/A")}
                                                    <span >${uiLabelMap.OrderScore}</span>&nbsp;${gatewayResponse.gatewayScoreResult?default("N/A")})
                                                    <a href="/accounting/control/ViewGatewayResponse?paymentGatewayResponseId=${gatewayResponse.paymentGatewayResponseId}&amp;externalLoginKey=${externalLoginKey}"
                                                       class="btn btn-white btn-xs">${uiLabelMap.CommonDetails}</a>
                                                    <#if gatewayResponse_has_next>
                                                        <hr/></#if>
                                                </#list>
                                            </div>
                                        </#if>
                                    </td>
                                    <td>
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
                                                confirmUrl="updateOrderPaymentPreference" buttonType="button"
                                                confirmMessage="确定取消该支付" confirmTitle="订单管理-支付取消" description="取消"
                                                targetParameterIter="orderId:'${orderId}',orderPaymentPreferenceId:'${orderPaymentPreference.orderPaymentPreferenceId}',statusId:'PAYMENT_CANCELLED',checkOutPaymentId:'${paymentMethod.paymentMethodTypeId?if_exists}'"/>

                                            </#if>
                                        </#if>
                                    </td>
                                </tr>
                            <#--是电子资金转账-->
                            <#elseif paymentMethod.paymentMethodTypeId?if_exists == "EFT_ACCOUNT">
                                <#assign eftAccount = paymentMethod.getRelatedOne("EftAccount")>
                                <#if eftAccount?has_content>
                                    <#assign pmBillingAddress = eftAccount.getRelatedOne("PostalAddress")?if_exists>
                                </#if>
                                <tr>
                                    <td align="right">
                                        <div>支付方式:<span >${uiLabelMap.AccountingEFTAccount}</span>
                                            <#if orderPaymentPreference.maxAmount?has_content>
                                                <br/>${uiLabelMap.OrderPaymentMaximumAmount}: <@ofbizCurrency amount=orderPaymentPreference.maxAmount?default(0.00) isoCode=currencyUomId/>
                                            </#if>
                                        </div>
                                    </td>
                                    
                                    <td valign="top">
                                        <div>
                                            <#if eftAccount?has_content>
                                            ${eftAccount.nameOnAccount?if_exists}<br/>
                                                <#if eftAccount.companyNameOnAccount?exists>${eftAccount.companyNameOnAccount}<br/></#if>
                                            ${uiLabelMap.AccountingBankName}: ${eftAccount.bankName}, ${eftAccount.routingNumber}<br/>
                                            ${uiLabelMap.AccountingAccount}#: ${eftAccount.accountNumber}
                                            <#else>
                                            ${uiLabelMap.CommonInformation} ${uiLabelMap.CommonNot} ${uiLabelMap.CommonAvailable}
                                            </#if>
                                        </div>
                                    </td>
                                    <td>
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
                                                confirmUrl="updateOrderPaymentPreference" buttonType="button"
                                                confirmMessage="确定取消该支付" confirmTitle="订单管理-支付取消" description="取消"
                                                targetParameterIter="orderId:'${orderId}',orderPaymentPreferenceId:'${orderPaymentPreference.orderPaymentPreferenceId}',statusId:'PAYMENT_CANCELLED',checkOutPaymentId:'${paymentMethod.paymentMethodTypeId?if_exists}'"/>

                                            </#if>
                                        </#if>
                                    </td>
                                </tr>
                                <#if paymentList?has_content>
                                    <tr>
                                        <td align="right">
                                            <div>&nbsp;<span >${uiLabelMap.AccountingInvoicePayments}</span></div>
                                        </td>
                                        
                                        <td>
                                            <div>
                                                <#list paymentList as paymentMap>
                                                    <a href="/accounting/control/paymentOverview?paymentId=${paymentMap.paymentId}&amp;externalLoginKey=${externalLoginKey}"
                                                       class="btn btn-white btn-xs">${paymentMap.paymentId}</a><#if paymentMap_has_next><br/></#if>
                                                </#list>
                                            </div>
                                        </td>
                                    </tr>
                                </#if>
                            <#elseif paymentMethod.paymentMethodTypeId?if_exists == "GIFT_CARD">
                                <#assign giftCard = paymentMethod.getRelatedOne("GiftCard")>
                                <#if giftCard?exists>
                                    <#assign pmBillingAddress = giftCard.getRelatedOne("PostalAddress")?if_exists>
                                </#if>
                                <tr>
                                    <td align="right">
                                        <div>支付方式:<span >${uiLabelMap.OrderGiftCard}</span>
                                            <#if orderPaymentPreference.maxAmount?has_content>
                                                <br/>${uiLabelMap.OrderPaymentMaximumAmount}: <@ofbizCurrency amount=orderPaymentPreference.maxAmount?default(0.00) isoCode=currencyUomId/>
                                            </#if>
                                        </div>
                                    </td>
                                    
                                    <td valign="top">
                                        <div>
                                            <#if giftCard?has_content>
                                                <#if security.hasEntityPermission("PAY_INFO", "_VIEW", session)>
                                                ${giftCard.cardNumber?default("N/A")} [${giftCard.pinNumber?default("N/A")}]
                                                    &nbsp;[<#if oppStatusItem?exists>${oppStatusItem.get("description",locale)}<#else>${orderPaymentPreference.statusId}</#if>]
                                                <#else>
                                                    <@maskSensitiveNumber cardNumber=giftCard.cardNumber?if_exists/>
                                                    <#if !cardNumberDisplay?has_content>N/A</#if>
                                                    &nbsp;[<#if oppStatusItem?exists>${oppStatusItem.get("description",locale)}<#else>${orderPaymentPreference.statusId}</#if>]
                                                </#if>
                                            <#else>
                                            ${uiLabelMap.CommonInformation} ${uiLabelMap.CommonNot} ${uiLabelMap.CommonAvailable}
                                            </#if>
                                        </div>
                                    </td>
                                    <td>
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
                                                confirmUrl="updateOrderPaymentPreference" buttonType="button"
                                                confirmMessage="确定取消该支付" confirmTitle="订单管理-支付取消" description="取消"
                                                targetParameterIter="orderId:'${orderId}',orderPaymentPreferenceId:'${orderPaymentPreference.orderPaymentPreferenceId}',statusId:'PAYMENT_CANCELLED',checkOutPaymentId:'${paymentMethod.paymentMethodTypeId?if_exists}'"/>

                                            </#if>
                                        </#if>
                                    </td>
                                </tr>
                                <#if paymentList?has_content>
                                    <tr>
                                        <td align="right">
                                            <div>&nbsp;<span >${uiLabelMap.AccountingInvoicePayments}</span></div>
                                        </td>
                                        
                                        <td>
                                            <div>
                                                <#list paymentList as paymentMap>
                                                    <a href="/accounting/control/paymentOverview?paymentId=${paymentMap.paymentId}&amp;externalLoginKey=${externalLoginKey}"
                                                       class="btn btn-white btn-xs">${paymentMap.paymentId}</a><#if paymentMap_has_next><br/></#if>
                                                </#list>
                                            </div>
                                        </td>
                                    </tr>
                                </#if>
                            </#if>
                        </#if>
                        <#if pmBillingAddress?has_content>

                            <tr>
                                <td align="right">&nbsp;</td>
                                
                                <td valign="top">
                                    <div>
                                        <#if pmBillingAddress.toName?has_content><span >${uiLabelMap.CommonTo}</span>&nbsp;${pmBillingAddress.toName}<br/></#if>
                                        <#if pmBillingAddress.attnName?has_content><span >${uiLabelMap.CommonAttn}</span>&nbsp;${pmBillingAddress.attnName}<br/></#if>
                                    ${pmBillingAddress.address1}<br/>
                                        <#if pmBillingAddress.address2?has_content>${pmBillingAddress.address2}<br/></#if>
                                    ${pmBillingAddress.city}<#if pmBillingAddress.stateProvinceGeoId?has_content>, ${pmBillingAddress.stateProvinceGeoId} </#if>
                                    ${pmBillingAddress.postalCode?if_exists}<br/>
                                    ${pmBillingAddress.countryGeoId?if_exists}
                                    </div>
                                </td>
                                
                            </tr>
                            <#if paymentList?has_content>
                                <tr>
                                    <td align="right">
                                        <div>&nbsp;<span >${uiLabelMap.AccountingInvoicePayments}</span></div>
                                    </td>
                                    
                                    <td>
                                        <div>
                                            <#list paymentList as paymentMap>
                                                <a href="/accounting/control/paymentOverview?paymentId=${paymentMap.paymentId}&amp;externalLoginKey=${externalLoginKey}"
                                                   class="btn btn-white btn-xs">${paymentMap.paymentId}</a><#if paymentMap_has_next><br/></#if>
                                            </#list>
                                        </div>
                                    </td>
                                </tr>
                            </#if>
                        </#if>
                    </#list>

                    <#if customerPoNumber?has_content>

                        <tr>
                            <td align="right"><span >${uiLabelMap.OrderPONumber}</span></td>
                            
                            <td valign="top">${customerPoNumber?if_exists}</td>
                            
                        </tr>
                    </#if>

                <#-- invoices -->
                    <#if invoices?has_content>

                        <tr>
                            <td align="right">&nbsp;<span >${uiLabelMap.OrderInvoices}</span></td>
                            
                            <td valign="top">
                                <#list invoices as invoice>
                                    <div>${uiLabelMap.CommonNbr}<a href="/accounting/control/invoiceOverview?invoiceId=${invoice}&amp;externalLoginKey=${externalLoginKey}"
                                                                   class="btn btn-white btn-xs">${invoice}</a>
                                        (<a target="_BLANK" href="/accounting/control/invoice.pdf?invoiceId=${invoice}&amp;externalLoginKey=${externalLoginKey}" class="btn btn-white btn-xs">PDF</a>)
                                    </div>
                                </#list>
                            </td>
                            
                        </tr>
                    </#if>
                <#else>
                    <tr>
                        <td colspan="4" align="center">${uiLabelMap.OrderNoOrderPaymentPreferences}</td>
                    </tr>
                </#if>
                <#if (!orderHeader.statusId.equals("ORDER_COMPLETED")) && !(orderHeader.statusId.equals("ORDER_REJECTED")) && !(orderHeader.statusId.equals("ORDER_CANCELLED")) && (paymentMethodValueMaps?has_content)>

                    <tr>
                        <td colspan="4">
                            <form name="addPaymentMethodToOrder" method="post" action="<@ofbizUrl>addPaymentMethodToOrder</@ofbizUrl>">
                                <input type="hidden" name="orderId" value="${orderId?if_exists}"/>
                                <table class="table">
                                    <tr>
                                        <td width="29%" align="right" nowrap="nowrap"><span >${uiLabelMap.AccountingPaymentMethod}</span></td>
                                        
                                        <td nowrap="nowrap">
                                            <select name="paymentMethodId">
                                                <#list paymentMethodValueMaps as paymentMethodValueMap>
                                                    <#assign paymentMethod = paymentMethodValueMap.paymentMethod/>
                                                    <option value="${paymentMethod.get("paymentMethodId")?if_exists}">
                                                        <#if "CREDIT_CARD" == paymentMethod.paymentMethodTypeId>
                                                            <#assign creditCard = paymentMethodValueMap.creditCard/>
                                                            <#if (creditCard?has_content)>
                                                                <#if security.hasEntityPermission("PAY_INFO", "_VIEW", session)>
                                                                ${creditCard.cardType?if_exists} <@maskSensitiveNumber cardNumber=creditCard.cardNumber?if_exists/> ${creditCard.expireDate?if_exists}
                                                                <#else>
                                                                ${Static["org.ofbiz.party.contact.ContactHelper"].formatCreditCard(creditCard)}
                                                                </#if>
                                                            </#if>
                                                        <#else>
                                                        ${paymentMethod.paymentMethodTypeId?if_exists}
                                                            <#if paymentMethod.description?exists>${paymentMethod.description}</#if>
                                                            (${paymentMethod.paymentMethodId})
                                                        </#if>
                                                    </option>
                                                </#list>
                                            </select>
                                        </td>
                                        
                                    </tr>
                                    <#assign openAmount = orderReadHelper.getOrderOpenAmount()>
                                    <tr>
                                        <td width="29%" align="right"><span >${uiLabelMap.AccountingAmount}</span></td>
                                        
                                        <td nowrap="nowrap">
                                            <input type="text" name="maxAmount" value="${openAmount}"/>
                                        </td>
                                        
                                    </tr>
                                    <tr>
                                        <td align="right">&nbsp;</td>
                                        
                                        <td valign="top">
                                            <input type="submit" value="${uiLabelMap.CommonAdd}" class="smallSubmit"/>
                                        </td>
                                        
                                    </tr>
                                </table>
                            </form>
                        </td>
                    </tr>
                </#if>
            </#if>
            </table>
        </div>
 <#--   </div>
</div>-->
<@htmlScreenTemplate.renderScreenletEnd/>
