<#--<#if orderHeader.statusId != "ORDER_COMPLETED" && orderHeader.statusId != "ORDER_CANCELLED">-->
<script language="JavaScript" type="text/javascript">
    function editInstruction(shipGroupSeqId) {
        jQuery('#shippingInstructions_' + shipGroupSeqId).css({display: 'block'});
        jQuery('#saveInstruction_' + shipGroupSeqId).css({display: 'inline'});
        jQuery('#editInstruction_' + shipGroupSeqId).css({display: 'none'});
        jQuery('#instruction_' + shipGroupSeqId).css({display: 'none'});
    }
    function addInstruction(shipGroupSeqId) {
        jQuery('#shippingInstructions_' + shipGroupSeqId).css({display: 'block'});
        jQuery('#saveInstruction_' + shipGroupSeqId).css({display: 'inline'});
        jQuery('#addInstruction_' + shipGroupSeqId).css({display: 'none'});
    }
    function saveInstruction(shipGroupSeqId) {
        jQuery("#updateShippingInstructionsForm_" + shipGroupSeqId).submit();
    }
    function editGiftMessage(shipGroupSeqId) {
        jQuery('#giftMessage_' + shipGroupSeqId).css({display: 'block'});
        jQuery('#saveGiftMessage_' + shipGroupSeqId).css({display: 'inline'});
        jQuery('#editGiftMessage_' + shipGroupSeqId).css({display: 'none'});
        jQuery('#message_' + shipGroupSeqId).css({display: 'none'});
    }
    function addGiftMessage(shipGroupSeqId) {
        jQuery('#giftMessage_' + shipGroupSeqId).css({display: 'block'});
        jQuery('#saveGiftMessage_' + shipGroupSeqId).css({display: 'inline'});
        jQuery('#addGiftMessage_' + shipGroupSeqId).css({display: 'none'});
    }
    function saveGiftMessage(shipGroupSeqId) {
        jQuery("#setGiftMessageForm_" + shipGroupSeqId).submit();
    }
</script>

<#if security.hasEntityPermission("ORDERMGR", "_UPDATE", session) && (!orderHeader.salesChannelEnumId?exists || orderHeader.salesChannelEnumId != "POS_SALES_CHANNEL")>
    <@htmlScreenTemplate.renderScreenletBegin id="OrderActionsPanel" title=""/>
<#--<div class="panel panel-default">
    <div class="panel-heading">
        <h4 class="panel-title">&nbsp;${uiLabelMap.OrderActions}</h4>
    </div>
    <div class="panel-body">-->
        <div class="btn-group">
            <#if security.hasEntityPermission("FACILITY", "_CREATE", session) && ((orderHeader.statusId == "ORDER_APPROVED") || (orderHeader.statusId == "ORDER_SENT"))>
            <#-- Special shipment options -->
                <#if orderHeader.orderTypeId == "SALES_ORDER">
                    <form name="quickShipOrder" method="post" action="<@ofbizUrl>quickShipOrder</@ofbizUrl>">
                        <input type="hidden" name="orderId" value="${orderId}"/>
                    </form>
                    <a href="javascript:document.quickShipOrder.submit()" class="btn btn-white  m-b-10 m-r-10 ">
                        <span class="fa-stack fa-1x text-success"><i class="fa fa-circle fa-stack-2x"></i><i
                                class="fa fa-flag fa-stack-1x fa-inverse"></i></span> ${uiLabelMap.OrderQuickShipEntireOrder}</a>
                 </#if>
            </#if>
        <#-- Refunds/Returns for Sales Orders and Delivery Schedules -->
            <#--<#if orderHeader.statusId != "ORDER_COMPLETED" && orderHeader.statusId != "ORDER_CANCELLED">
            <@htmlScreenTemplate.renderModalPage id="OrderViewEditDeliveryScheduleInfo" modalUrl="/ordermgr/control/OrderDeliveryScheduleInfo?orderId=${orderId}" modalTitle="${StringUtil.wrapString(uiLabelMap.OrderViewEditDeliveryScheduleInfo)}"
                name="OrderDeliveryScheduleInfo" description="${uiLabelMap.OrderViewEditDeliveryScheduleInfo}" buttonStyle="btn btn-white  m-b-10 m-r-10" buttonType="custom"
            buttonSpanStyle="<span class='fa-stack fa-1x text-warning'> <i class='fa fa-circle-o fa-stack-2x'></i> <i class='fa fa-cog fa-stack-1x'></i></span>"/>
               &lt;#&ndash; <a href="<@ofbizUrl>OrderDeliveryScheduleInfo?orderId=${orderId}</@ofbizUrl>" class="btn btn-white  m-b-10 m-r-10 ">
                    <span class="fa-stack fa-1x text-warning"> <i class="fa fa-circle-o fa-stack-2x"></i> <i
                            class="fa fa-cog fa-stack-1x"></i></span>${uiLabelMap.OrderViewEditDeliveryScheduleInfo}</a>&ndash;&gt;
            </#if>-->

            <#if security.hasEntityPermission("ORDERMGR", "_RETURN", session) && orderHeader.statusId == "ORDER_COMPLETED">
                <#if returnableItems?has_content>
                    <a href="javascript:document.quickRefundOrder.submit()" class="btn btn-white  m-b-10 m-r-10 ">
                        <span class="fa-stack fa-1x text-warning"><i class="fa fa-circle fa-stack-2x"></i><i class="fa fa-backward fa-stack-1x fa-inverse"></i></span>
                        ${uiLabelMap.OrderQuickRefundEntireOrder}
                        <form name="quickRefundOrder" method="post" action="<@ofbizUrl>quickRefundOrder</@ofbizUrl>">
                        <input type="hidden" name="orderId" value="${orderId}"/>
                        <input type="hidden" name="receiveReturn" value="true"/>
                        <input type="hidden" name="returnHeaderTypeId" value="${returnHeaderTypeId}"/>
                    </form>
                    </a>

                    <a href="javascript:document.quickreturn.submit()" class="btn btn-white  m-b-10 m-r-10 ">
                        <span class="fa-stack fa-1x text-info"><i class="fa fa-square-o fa-stack-2x"></i><i class="fa fa-mail-reply fa-stack-1x"></i></span>
                    ${uiLabelMap.OrderCreateReturn}
                        <form name="quickreturn" method="post" action="<@ofbizUrl>quickreturn</@ofbizUrl>">
                            <input type="hidden" name="orderId" value="${orderId}"/>
                            <input type="hidden" name="party_id" value="${partyId?if_exists}"/>
                            <input type="hidden" name="returnHeaderTypeId" value="${returnHeaderTypeId}"/>
                            <input type="hidden" name="needsInventoryReceive" value="${needsInventoryReceive?default("N")}"/>
                        </form>
                    </a>
                </#if>
            </#if>

            <#if orderHeader?has_content && orderHeader.statusId != "ORDER_CANCELLED">
                <#if orderHeader.statusId != "ORDER_COMPLETED">
                <#--
                  <li><a href="<@ofbizUrl>cancelOrderItem?${paramString}</@ofbizUrl>" class="buttontext">${uiLabelMap.OrderCancelAllItems}</a></li>
                -->

                    <@htmlScreenTemplate.renderModalPage id="editOrderItems" modalUrl="/ordermgr/control/editOrderItems?orderId=${orderId}" modalTitle="${StringUtil.wrapString(uiLabelMap.OrderEditItems)}"
                    name="OrderDeliveryScheduleInfo" description="${uiLabelMap.OrderEditItems}" buttonStyle="btn btn-white  m-b-10 m-r-10" buttonType="custom" modalType="view"
                    buttonSpanStyle="<span class='fa-stack fa-1x text-info'><i class='fa fa-circle fa-stack-2x'></i><i class='fa fa-gears fa-stack-1x fa-inverse'></i></span>" modalStyle="modal-lg"/>

                   <#-- <a href="<@ofbizUrl>editOrderItems?${paramString}</@ofbizUrl>" class="btn btn-white  m-b-10 m-r-10 ">
                        <span class="fa-stack fa-1x text-info"><i class="fa fa-circle fa-stack-2x"></i><i class="fa fa-gears fa-stack-1x fa-inverse"></i></span>
                    ${uiLabelMap.OrderEditItems}</a>-->


                    <#--<a href="javascript:document.createOrderItemShipGroup.submit()" class="btn btn-white  m-b-10 m-r-10 ">
                        <span class="fa-stack fa-1x text-warning"><i class="fa fa-circle fa-stack-2x"></i><i class="fa fa-car fa-stack-1x fa-inverse"></i></span>
                        创建运输组
                        <form name="createOrderItemShipGroup" method="post" action="<@ofbizUrl>createOrderItemShipGroup</@ofbizUrl>">
                            <input type="hidden" name="orderId" value="${orderId}"/>
                        </form>
                    </a>-->

                    <#--<@htmlScreenTemplate.renderConfirmField id="createOrderItemShipGroup" name="createOrderItemShipGroup" buttonType="custom" buttonStyle="btn btn-white  m-b-10 m-r-10"
                    buttonSpanStyle="<span class='fa-stack fa-1x text-warning'><i class='fa fa-circle fa-stack-2x'></i><i class='fa fa-car fa-stack-1x fa-inverse'></i></span>"
                    confirmTitle="创建新的运输组" confirmMessage="确定为该订单创建新的运输组吗" targetParameterIter="orderId:'${orderId}'"
                    confirmUrl="createOrderItemShipGroup" description="创建新的运输组"/>-->

                </#if>
              <#--  <a href="<@ofbizUrl>loadCartFromOrder?${paramString}&amp;finalizeMode=init</@ofbizUrl>" class="btn btn-white  m-b-10 m-r-10 ">
                    <span class="fa-stack fa-1x text-primary"> <i class="fa fa-circle fa-stack-2x"></i> <i class="fa fa-coffee fa-stack-1x fa-inverse"></i></span>
                    根据订单新建</a>-->
                <#if orderHeader.statusId == "ORDER_COMPLETED">
                    <a href="<@ofbizUrl>loadCartForReplacementOrder?${paramString}</@ofbizUrl>" class="btn btn-white  m-b-10 m-r-10 ">
                        <span class="fa-stack fa-1x text-warning"><i class="fa fa-circle fa-stack-2x"></i><i class="fa fa-car fa-stack-1x fa-inverse"></i></span>
                    ${uiLabelMap.OrderCreateReplacementOrder}</a>
                </#if>
            </#if>
            <#--<a href="<@ofbizUrl>OrderHistory?orderId=${orderId}</@ofbizUrl>" class="btn btn-white  m-b-10 m-r-10 ">
                <span class="fa-stack fa-1x text-danger"> <i class="fa fa-circle fa-stack-2x"></i> <i class="fa fa-tasks fa-stack-1x fa-inverse"></i></span>
                订单历史</a>-->
        </div>
     <@htmlScreenTemplate.renderScreenletEnd/>
<#--</#if>-->
</#if>

