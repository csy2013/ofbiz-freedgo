<#if security.hasEntityPermission("ORDERMGR", "_VIEW", session)>
    <#--
<div class="panel panel-default">
    <div class="panel-heading">
        <h3>${uiLabelMap.OrderOrderStatisticsPage}</h3>
    </div>
    <div class="panel-body">-->
        <@htmlScreenTemplate.renderScreenletBegin id="OrderActionsPanel" title="${uiLabelMap.OrderOrderStatisticsPage}"/>
        <div class="table-responsive">
            <table class="table table-bordered" cellspacing='0'>
                <tr>
                    <td>&nbsp;</td>
                    <td>&nbsp;</td>
                    <td align="right">${uiLabelMap.CommonToday}</td>
                    <td align="right">${uiLabelMap.OrderWTD}</td>
                    <td align="right">${uiLabelMap.OrderMTD}</td>
                    <td align="right">${uiLabelMap.OrderYTD}</td>
                </tr>

                <tr class="info">
                    <td>${uiLabelMap.OrderOrdersTotals}</td>
                    <td colspan="5">&nbsp;</td>
                </tr>
                <tr class="info">
                    <td>&nbsp;</td>
                    <td>${uiLabelMap.OrderGrossDollarAmountsIncludesAdjustmentsAndPendingOrders}</td>
                    <td align="right">${dayItemTotal}</td>
                    <td align="right">${weekItemTotal}</td>
                    <td align="right">${monthItemTotal}</td>
                    <td align="right">${yearItemTotal}</td>
                </tr class="info">
                <tr class="info">
                    <td>&nbsp;</td>
                    <td>${uiLabelMap.OrderPaidDollarAmountsIncludesAdjustments}</td>
                    <td align="right">${dayItemTotalPaid}</td>
                    <td align="right">${weekItemTotalPaid}</td>
                    <td align="right">${monthItemTotalPaid}</td>
                    <td align="right">${yearItemTotalPaid}</td>
                </tr>
                <tr class="info">
                    <td>&nbsp;</td>
                    <td>${uiLabelMap.OrderPendingPaymentDollarAmountsIncludesAdjustments}</td>
                    <td align="right">${dayItemTotalPending}</td>
                    <td align="right">${weekItemTotalPending}</td>
                    <td align="right">${monthItemTotalPending}</td>
                    <td align="right">${yearItemTotalPending}</td>
                </tr>

                <tr>
                    <td>${uiLabelMap.OrderOrdersItemCounts}</td>
                    <td colspan="5">&nbsp;</td>
                </tr>
                <tr class="alternate-row">
                    <td>&nbsp;</td>
                    <td>${uiLabelMap.OrderGrossItemsSoldIncludesPromotionsAndPendingOrders}</td>
                    <td align="right">${dayItemCount?string.number}</td>
                    <td align="right">${weekItemCount?string.number}</td>
                    <td align="right">${monthItemCount?string.number}</td>
                    <td align="right">${yearItemCount?string.number}</td>
                </tr>
                <tr>
                    <td>&nbsp;</td>
                    <td>${uiLabelMap.OrderPaidItemsSoldIncludesPromotions}</td>
                    <td align="right">${dayItemCountPaid?string.number}</td>
                    <td align="right">${weekItemCountPaid?string.number}</td>
                    <td align="right">${monthItemCountPaid?string.number}</td>
                    <td align="right">${yearItemCountPaid?string.number}</td>
                </tr>
                <tr class="alternate-row">
                    <td>&nbsp;</td>
                    <td>${uiLabelMap.OrderPendingPaymentItemsSoldIncludesPromotions}</td>
                    <td align="right">${dayItemCountPending?string.number}</td>
                    <td align="right">${weekItemCountPending?string.number}</td>
                    <td align="right">${monthItemCountPending?string.number}</td>
                    <td align="right">${yearItemCountPending?string.number}</td>
                </tr>

                <tr class="success">
                    <td>${uiLabelMap.OrderOrdersPending}</td>
                    <td colspan="5">&nbsp;</td>
                </tr>
                <tr class="success">
                    <td>&nbsp;</td>
                    <td>${uiLabelMap.OrderWaitingPayment}</td>
                    <td align="right">${waitingPayment?default(0)?string.number}</td>
                    <td align="right">--</td>
                    <td align="right">--</td>
                    <td align="right">--</td>
                </tr>
                <tr  class="success">
                    <td>&nbsp;</td>
                    <td>${uiLabelMap.OrderWaitingApproval}</td>
                    <td align="right">${waitingApproval?default(0)?string.number}</td>
                    <td align="right">--</td>
                    <td align="right">--</td>
                    <td align="right">--</td>
                </tr>
                <tr  class="success">
                    <td>&nbsp;</td>
                    <td>${uiLabelMap.OrderWaitingCompletion}</td>
                    <td align="right">${waitingComplete?default(0)?string.number}</td>
                    <td align="right">--</td>
                    <td align="right">--</td>
                    <td align="right">--</td>
                </tr>

                <tr class="warning">
                    <td>${uiLabelMap.OrderStatusChanges}</td>
                    <td colspan="5">&nbsp;</td>
                </tr>
                <tr class="warning">
                    <td>&nbsp;</td>
                    <td>${uiLabelMap.OrderCreated}</td>
                    <td align="right">${dayOrder?size?default(0)?string.number}</td>
                    <td align="right">${weekOrder?size?default(0)?string.number}</td>
                    <td align="right">${monthOrder?size?default(0)?string.number}</td>
                    <td align="right">${yearOrder?size?default(0)?string.number}</td>
                </tr>
                <tr class="warning">
                    <td>&nbsp;</td>
                    <td>${uiLabelMap.OrderApproved}</td>
                    <td align="right">${dayApprove?size?default(0)?string.number}</td>
                    <td align="right">${weekApprove?size?default(0)?string.number}</td>
                    <td align="right">${monthApprove?size?default(0)?string.number}</td>
                    <td align="right">${yearApprove?size?default(0)?string.number}</td>
                </tr>
                <tr class="warning">
                    <td>&nbsp;</td>
                    <td>${uiLabelMap.OrderCompleted}</td>
                    <td align="right">${dayComplete?size?default(0)?string.number}</td>
                    <td align="right">${weekComplete?size?default(0)?string.number}</td>
                    <td align="right">${monthComplete?size?default(0)?string.number}</td>
                    <td align="right">${yearComplete?size?default(0)?string.number}</td>
                </tr>
                <tr class="warning">
                    <td>&nbsp;</td>
                    <td>${uiLabelMap.OrderCancelled}</td>
                    <td align="right">${dayCancelled?size?default(0)?string.number}</td>
                    <td align="right">${weekCancelled?size?default(0)?string.number}</td>
                    <td align="right">${monthCancelled?size?default(0)?string.number}</td>
                    <td align="right">${yearCancelled?size?default(0)?string.number}</td>
                </tr>
                <tr class="warning">
                    <td>&nbsp;</td>
                    <td>${uiLabelMap.OrderRejected}</td>
                    <td align="right">${dayRejected?size?default(0)?string.number}</td>
                    <td align="right">${weekRejected?size?default(0)?string.number}</td>
                    <td align="right">${monthRejected?size?default(0)?string.number}</td>
                    <td align="right">${yearRejected?size?default(0)?string.number}</td>
                </tr>
            </table>
        </div>
        <@htmlScreenTemplate.renderScreenletEnd/>
<#else>
<h3>${uiLabelMap.OrderViewPermissionError}</h3>
</#if>
