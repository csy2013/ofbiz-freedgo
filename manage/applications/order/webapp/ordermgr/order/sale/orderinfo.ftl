<style>
    .panel {
        margin-bottom: 0px;
    }
</style>
<#include "component://common/webcommon/includes/htmlScreenTemplate.ftl"/>
<div class="${panelStyle}">
    <div class="${panelHeadingStyle}">
        <div class="pull-right">
        <#if orderHeader.externalId?has_content>
            <#assign externalOrder = "(" + orderHeader.externalId + ")"/>
        </#if>
        <#assign orderType = orderHeader.getRelatedOne("OrderType")/>
        <#--<a href="javascript:document.OrderApproveOrder.submit()" class="btn btn-success btn-xs">${uiLabelMap.OrderApproveOrder}</a>-->

        <#if currentStatus.statusId == "ORDER_PROCESSING">
            <a href="javascript:document.OrderApproveOrder.submit()" class="btn btn-white btn-xs m-r-5 ">${uiLabelMap.OrderApproveOrder}</a>
        </#if>

        <#if currentStatus.statusId != "ORDER_COMPLETED" && currentStatus.statusId != "ORDER_CANCELLED">
            <a href="javascript:document.OrderCancel.submit()" class="btn btn-white btn-xs m-r-5">${uiLabelMap.OrderCancelOrder}</a>
        </#if>

        <#if setOrderCompleteOption>
            <a href="javascript:document.OrderCompleteOrder.submit()" class="btn btn-white btn-xs m-r-5">${uiLabelMap.OrderCompleteOrder}</a>

        </#if>
        <#if currentStatus.statusId == "ORDER_APPROVED" && orderHeader.orderTypeId == "SALES_ORDER">
            <a href="javascript:document.PrintOrderPickSheet.submit()" class="btn btn-white btn-xs m-r-5">${uiLabelMap.FormFieldTitle_printPickSheet}</a>
        </#if>
        <#if currentStatus.statusId != "ORDER_CANCELLED" && orderHeader.orderTypeId != "ORDER_REJECTED">
            <a href="<@ofbizUrl>order.pdf?orderId=${orderId}</@ofbizUrl>" target="_blank" class="btn btn-white btn-xs m-r-5">PDF</a>
        </#if>
            <form name="OrderApproveOrder" method="post" action="<@ofbizUrl>changeOrderStatus/orderview</@ofbizUrl>">
                <input type="hidden" name="statusId" value="ORDER_APPROVED"/>
                <input type="hidden" name="newStatusId" value="ORDER_APPROVED"/>
                <input type="hidden" name="setItemStatus" value="Y"/>
                <input type="hidden" name="workEffortId" value="${workEffortId?if_exists}"/>
                <input type="hidden" name="orderId" value="${orderId?if_exists}"/>
                <input type="hidden" name="partyId" value="${assignPartyId?if_exists}"/>
                <input type="hidden" name="roleTypeId" value="${assignRoleTypeId?if_exists}"/>
                <input type="hidden" name="fromDate" value="${fromDate?if_exists}"/>
            </form>
            <form name="OrderHold" method="post" action="<@ofbizUrl>changeOrderStatus/orderview</@ofbizUrl>">
                <input type="hidden" name="statusId" value="ORDER_HOLD"/>
                <input type="hidden" name="workEffortId" value="${workEffortId?if_exists}"/>
                <input type="hidden" name="orderId" value="${orderId?if_exists}"/>
                <input type="hidden" name="partyId" value="${assignPartyId?if_exists}"/>
                <input type="hidden" name="roleTypeId" value="${assignRoleTypeId?if_exists}"/>
                <input type="hidden" name="fromDate" value="${fromDate?if_exists}"/>
            </form>
        <#--<form name="OrderApproveOrder" method="post" action="<@ofbizUrl>changeOrderStatus/orderview</@ofbizUrl>">
            <input type="hidden" name="statusId" value="ORDER_APPROVED"/>
            <input type="hidden" name="setItemStatus" value="Y"/>
            <input type="hidden" name="workEffortId" value="${workEffortId?if_exists}"/>
            <input type="hidden" name="orderId" value="${orderId?if_exists}"/>
            <input type="hidden" name="partyId" value="${assignPartyId?if_exists}"/>
            <input type="hidden" name="roleTypeId" value="${assignRoleTypeId?if_exists}"/>
            <input type="hidden" name="fromDate" value="${fromDate?if_exists}"/>
        </form>-->
            <form name="OrderCancel" method="post" action="<@ofbizUrl>changeOrderStatus/orderview</@ofbizUrl>">
                <input type="hidden" name="statusId" value="ORDER_CANCELLED"/>
                <input type="hidden" name="setItemStatus" value="Y"/>
                <input type="hidden" name="workEffortId" value="${workEffortId?if_exists}"/>
                <input type="hidden" name="orderId" value="${orderId?if_exists}"/>
                <input type="hidden" name="partyId" value="${assignPartyId?if_exists}"/>
                <input type="hidden" name="roleTypeId" value="${assignRoleTypeId?if_exists}"/>
                <input type="hidden" name="fromDate" value="${fromDate?if_exists}"/>
            </form>
            <form name="OrderCompleteOrder" method="post" action="<@ofbizUrl>changeOrderStatus</@ofbizUrl>">
                <input type="hidden" name="statusId" value="ORDER_COMPLETED"/>
                <input type="hidden" name="orderId" value="${orderId?if_exists}"/>
            </form>
            <form name="PrintOrderPickSheet" method="post" action="<@ofbizUrl>orderPickSheet.pdf</@ofbizUrl>" target="_BLANK">
                <input type="hidden" name="facilityId" value="${storeFacilityId?if_exists}"/>
                <input type="hidden" name="orderId" value="${orderHeader.orderId?if_exists}"/>
                <input type="hidden" name="maxNumberOfOrdersToPrint" value="1"/>
            </form>
        </div>
        <h4 class="${panelTitleStyle}">订单概况</h4>

    </div>
<#assign orh = Static["org.ofbiz.order.order.OrderReadHelper"].getHelper(orderHeader)>
    <div class="${panelBodyStyle} f-s-12">
        <div class="form-horizontal">
        <#if orderHeader.orderName?has_content>
            <div class="form-group">
                <div class="control-label col-md-5">${uiLabelMap.OrderOrderName}:</div>
                <div class="col-md-7">${orderHeader.orderName}</div>
            </div>

        </#if>
            <div class="form-group">
                <div class="control-label col-md-2">订单编号:</div>

                <div class="col-md-4">${orderId}
                </div>
                <div class="control-label col-md-2">${uiLabelMap.OrderDateOrdered}:</div>
                <div class="col-md-4"><#if orderHeader.orderDate?has_content>${Static["org.ofbiz.base.util.UtilFormatOut"].formatDateTime(orderHeader.orderDate, "", locale, timeZone)!}</#if>
                </div>
            </div>
            <div class="form-group">
                <div class="control-label col-md-2">当前订单状态:</div>
                <div class="col-md-4">${currentStatus.get("description")}

                        <#if (returnableItems?has_content == false) && currentStatus.statusId == 'ORDER_COMPLETED'>
                            <span class="text-danger">(已退货)</span>
                        </#if>
                </div>
                <div class="control-label col-md-2">优惠劵名称:</div>
                <div class="col-md-4"></div>
            </div>


            <div class="form-group">
                <div class="control-label col-md-2">${uiLabelMap.OrderSalesChannel}:</div>
                <div class="col-md-4">
                <#if orderHeader.salesChannelEnumId?has_content>
                    <#assign channel = orderHeader.getRelatedOne("SalesChannelEnumeration")>
                    ${(channel.get("description",locale))?default("N/A")}
                  <#else>
                ${uiLabelMap.CommonNA}
                </#if>
                </div>
                <div class="control-label col-md-2">${uiLabelMap.OrderProductStore}:</div>
                <div class="col-md-4">${productStore.storeName!}&nbsp;</div>
            </div>

            <div class="form-group">
                <div class="control-label col-md-2">优惠劵码:</div>
                <div class="col-md-4">${currentStatus.get("description",locale)}</div>
                <div class="control-label col-md-2">订单原始金额:</div>
                <div class="col-md-4"><@ofbizCurrency amount=orh.getOrderItemsSubTotal() isoCode=orh.getCurrency()/></div>
            </div>

            <div class="form-group">
                <div class="control-label col-md-2">订单优惠金额:</div>
                <div class="col-md-4"><@ofbizCurrency amount=orh.calcOrderPromoAdjustmentsBd(orh.getAdjustments()) isoCode=orh.getCurrency()/></div>
                <div class="control-label col-md-2">订单交易金额:</div>
                <div class="col-md-4"><@ofbizCurrency amount=orh.getOrderGrandTotal() isoCode=orh.getCurrency()/></div>
            </div>
            <div class="form-group">
                <div class="control-label col-md-2">订单使用积分:</div>
                <div class="col-md-4">${currentStatus.get("description",locale)}</div>
                <div class="control-label col-md-2">订单修改金额:</div>
                <div class="col-md-4"><@ofbizCurrency amount=orh.getOrderAdjustmentsTotal() isoCode=orh.getCurrency()/></div>
            </div>
        </div>
    </div>
</div>

${screens.render("component://order/widget/ordermgr/OrderViewScreens.xml#saleOrderpaymentinfo")}
${screens.render("component://order/widget/ordermgr/OrderViewScreens.xml#ordershipGroupsinfo1")}



