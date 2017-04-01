<#include "component://common/webcommon/includes/htmlScreenTemplate.ftl"/>
<div class="${panelStyle}">
    <div class="${panelHeadingStyle}">
        <div class="btn-group pull-right">
        <#if orderHeader.externalId?has_content>
            <#assign externalOrder = "(" + orderHeader.externalId + ")"/>
        </#if>
        <#assign orderType = orderHeader.getRelatedOne("OrderType")/>
        <#--<a href="javascript:document.OrderApproveOrder.submit()" class="btn btn-success btn-xs">${uiLabelMap.OrderApproveOrder}</a>-->
            <button type="button" class="btn btn-success btn-xs">操作</button>
            <button type="button" class="btn btn-success btn-xs dropdown-toggle" data-toggle="dropdown" aria-expanded="true">
                <span class="caret"></span>
            </button>
            <ul class="dropdown-menu" role="menu">
            <#if currentStatus.statusId == "ORDER_CREATED" || currentStatus.statusId == "ORDER_PROCESSING">
                <li><a href="javascript:document.OrderApproveOrder.submit()" class="btn btn-success btn-xs">${uiLabelMap.OrderApproveOrder}</a></li>
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

            <#elseif currentStatus.statusId == "ORDER_APPROVED">
                <li><a href="javascript:document.OrderHold.submit()">${uiLabelMap.OrderHold}</a></li>
                <form name="OrderHold" method="post" action="<@ofbizUrl>changeOrderStatus/orderview</@ofbizUrl>">
                    <input type="hidden" name="statusId" value="ORDER_HOLD"/>
                    <input type="hidden" name="workEffortId" value="${workEffortId?if_exists}"/>
                    <input type="hidden" name="orderId" value="${orderId?if_exists}"/>
                    <input type="hidden" name="partyId" value="${assignPartyId?if_exists}"/>
                    <input type="hidden" name="roleTypeId" value="${assignRoleTypeId?if_exists}"/>
                    <input type="hidden" name="fromDate" value="${fromDate?if_exists}"/>
                </form>

            <#elseif currentStatus.statusId == "ORDER_HOLD">
                <li><a href="javascript:document.OrderApproveOrder.submit()">${uiLabelMap.OrderApproveOrder}</a></li>
                <form name="OrderApproveOrder" method="post" action="<@ofbizUrl>changeOrderStatus/orderview</@ofbizUrl>">
                    <input type="hidden" name="statusId" value="ORDER_APPROVED"/>
                    <input type="hidden" name="setItemStatus" value="Y"/>
                    <input type="hidden" name="workEffortId" value="${workEffortId?if_exists}"/>
                    <input type="hidden" name="orderId" value="${orderId?if_exists}"/>
                    <input type="hidden" name="partyId" value="${assignPartyId?if_exists}"/>
                    <input type="hidden" name="roleTypeId" value="${assignRoleTypeId?if_exists}"/>
                    <input type="hidden" name="fromDate" value="${fromDate?if_exists}"/>
                </form>
            </#if>
            <#if currentStatus.statusId != "ORDER_COMPLETED" && currentStatus.statusId != "ORDER_CANCELLED">
                <li><a href="javascript:document.OrderCancel.submit()">${uiLabelMap.OrderCancelOrder}</a></li>
                <form name="OrderCancel" method="post" action="<@ofbizUrl>changeOrderStatus/orderview</@ofbizUrl>">
                    <input type="hidden" name="statusId" value="ORDER_CANCELLED"/>
                    <input type="hidden" name="setItemStatus" value="Y"/>
                    <input type="hidden" name="workEffortId" value="${workEffortId?if_exists}"/>
                    <input type="hidden" name="orderId" value="${orderId?if_exists}"/>
                    <input type="hidden" name="partyId" value="${assignPartyId?if_exists}"/>
                    <input type="hidden" name="roleTypeId" value="${assignRoleTypeId?if_exists}"/>
                    <input type="hidden" name="fromDate" value="${fromDate?if_exists}"/>
                </form>

            </#if>
            <#if setOrderCompleteOption>
                <li><a href="javascript:document.OrderCompleteOrder.submit()">${uiLabelMap.OrderCompleteOrder}</a></li>
                <form name="OrderCompleteOrder" method="post" action="<@ofbizUrl>changeOrderStatus</@ofbizUrl>">
                    <input type="hidden" name="statusId" value="ORDER_COMPLETED"/>
                    <input type="hidden" name="orderId" value="${orderId?if_exists}"/>
                </form>
            </#if>
                <li class="divider"></li>
            <#if currentStatus.statusId == "ORDER_APPROVED" && orderHeader.orderTypeId == "SALES_ORDER">
                <li><a href="javascript:document.PrintOrderPickSheet.submit()">${uiLabelMap.FormFieldTitle_printPickSheet}</a></li>
                <form name="PrintOrderPickSheet" method="post" action="<@ofbizUrl>orderPickSheet.pdf</@ofbizUrl>" target="_BLANK">
                    <input type="hidden" name="facilityId" value="${storeFacilityId?if_exists}"/>
                    <input type="hidden" name="orderId" value="${orderHeader.orderId?if_exists}"/>
                    <input type="hidden" name="maxNumberOfOrdersToPrint" value="1"/>
                </form>
            </#if>
                <li><a href="<@ofbizUrl>order.pdf?orderId=${orderId}</@ofbizUrl>" target="_blank">PDF</a></li>
            </ul>
        </div>
        <h4 class="${panelTitleStyle}">${orderType?if_exists.get("description", locale)?default(uiLabelMap.OrderOrder)}${uiLabelMap.CommonNbr}
            ${orderId} ${externalOrder?if_exists}</h4>

    </div>
    <div class="${panelBodyStyle}">
    <div class="form-horizontal">
    <#if orderHeader.orderName?has_content>
        <div class="form-group">
            <div  class="text-right col-md-5">${uiLabelMap.OrderOrderName}:</div> 
            <div class="col-md-7">${orderHeader.orderName}</div>
        </div>

    </#if>
    <#-- order status history -->
        <div class="form-group">
            <div  class="text-right col-md-5">当前订单状态:</div>

            <div class="col-md-7<#if currentStatus.statusCode?has_content>${currentStatus.statusCode}"</#if>>
                <span class="current-status">${uiLabelMap.OrderCurrentStatus}: ${currentStatus.get("description",locale)}</span>
            <#if orderHeaderStatuses?has_content>
                <hr/>${uiLabelMap.OrderStatusHistory}:
                <#list orderHeaderStatuses as orderHeaderStatus>
                    <#assign loopStatusItem = orderHeaderStatus.getRelatedOne("StatusItem")>
                    <#assign userlogin = orderHeaderStatus.getRelatedOne("UserLogin")>
                    <div class="text-center">
                    ${loopStatusItem.get("description",locale)} <#if orderHeaderStatus.statusDatetime?has_content>
                        - ${Static["org.ofbiz.base.util.UtilFormatOut"].formatDateTime(orderHeaderStatus.statusDatetime, "", locale, timeZone)?default("0000-00-00 00:00:00")}</#if>
                        &nbsp;
                    ${uiLabelMap.CommonBy} - <#--${Static["org.ofbiz.party.party.PartyHelper"].getPartyName(delegator, userlogin.getString("partyId"), true)}-->
                        [${orderHeaderStatus.statusUserLogin}]
                    </div>
                </#list>
                <hr/>
            </#if>
            </div>
        </div>

        <div class="form-group">
            <div  class="text-right col-md-5">${uiLabelMap.OrderDateOrdered}:</div> 

            <div class="col-md-7"><#if orderHeader.orderDate?has_content>${Static["org.ofbiz.base.util.UtilFormatOut"].formatDateTime(orderHeader.orderDate, "", locale, timeZone)!}</#if>
            </div>
        </div>

        <div class="form-group">
            <div  class="text-right col-md-5">${uiLabelMap.CommonCurrency}:</div> 

            <div class="col-md-7">${orderHeader.currencyUom?default("???")}</div>
        </div>
    <#if orderHeader.internalCode?has_content>

        <div class="form-group">
            <div  class="text-right col-md-5">${uiLabelMap.OrderInternalCode}:</div> 

            <div class="col-md-7">${orderHeader.internalCode}</div>
        </div>
    </#if>

        <div class="form-group">
            <div  class="text-right col-md-5">${uiLabelMap.OrderSalesChannel}:</div> 

            <div class="col-md-7">
            <#if orderHeader.salesChannelEnumId?has_content>
                    <#assign channel = orderHeader.getRelatedOne("SalesChannelEnumeration")>
                    ${(channel.get("description",locale))?default("N/A")}
                  <#else>
            ${uiLabelMap.CommonNA}
            </#if>
            </div>
        </div>

    <#if productStore?has_content>
        <div class="form-group">
            <div  class="text-right col-md-5">${uiLabelMap.OrderProductStore}:</div> 

            <div class="col-md-7">
            ${productStore.storeName!}&nbsp;
                <#--<a href="/catalog/control/EditProductStore?productStoreId=${productStore.productStoreId}${externalKeyParam}" target="catalogmgr" class="buttontext">(${productStore.productStoreId})</a>-->
            </div>
        </div>

    </#if>
        <div class="form-group">
            <div  class="text-right col-md-5">${uiLabelMap.OrderOriginFacility}:</div> 

            <div class="col-md-7">
            <#if orderHeader.originFacilityId?has_content>
                <#--<a href="/facility/control/EditFacility?facilityId=${orderHeader.originFacilityId}${externalKeyParam}" target="facilitymgr" class="buttontext">-->${orderHeader.originFacilityId}<#--</a>-->
            <#else>
            ${uiLabelMap.CommonNA}
            </#if>
            </div>
        </div>

        <div class="form-group">
            <div  class="text-right col-md-5">${uiLabelMap.CommonCreatedBy}:</div> 

            <div class="col-md-7">
            <#if orderHeader.createdBy?has_content>
                <#--<a href="/partymgr/control/viewprofile?userlogin_id=${orderHeader.createdBy}${externalKeyParam}" target="partymgr" class="buttontext">-->${orderHeader.createdBy}<#--</a>-->
            <#else>
            ${uiLabelMap.CommonNotSet}
            </#if>
            </div>
        </div>
    <#if (orderItem.cancelBackOrderDate)?exists>

        <div class="form-group">
            <div  class="text-right col-md-5">${uiLabelMap.FormFieldTitle_cancelBackOrderDate}:</div> 

            <div class="col-md-7"><#if orderItem.cancelBackOrderDate?has_content>${Static["org.ofbiz.base.util.UtilFormatOut"].formatDateTime(orderItem.cancelBackOrderDate, "", locale, timeZone)!}</#if></div>
        </div>
    </#if>
    <#if distributorId?exists>

        <div class="form-group">
            <div  class="text-right col-md-5">${uiLabelMap.OrderDistributor}:</div> 

            <div class="col-md-7">
                <#assign distPartyNameResult = dispatcher.runSync("getPartyNameForDate", Static["org.ofbiz.base.util.UtilMisc"].toMap("partyId", distributorId, "compareDate", orderHeader.orderDate, "userLogin", userLogin))/>
                  ${distPartyNameResult.fullName?default("[${uiLabelMap.OrderPartyNameNotFound}]")}
            </div>
        </div>
    </#if>
    <#if affiliateId?exists>
        <div class="form-group">
            <div  class="text-right col-md-5">${uiLabelMap.OrderAffiliate}:</div> 

            <div class="col-md-7">
                <#assign affPartyNameResult = dispatcher.runSync("getPartyNameForDate", Static["org.ofbiz.base.util.UtilMisc"].toMap("partyId", affiliateId, "compareDate", orderHeader.orderDate, "userLogin", userLogin))/>
                  ${affPartyNameResult.fullName?default("[${uiLabelMap.OrderPartyNameNotFound}]")}
            </div>
        </div>
    </#if>
    <#if orderContentWrapper.get("IMAGE_URL")?has_content>

        <div class="form-group">
            <div  class="text-right col-md-5">${uiLabelMap.OrderImage}:</div> 

            <div class="col-md-7">
                <a href="<@ofbizUrl>viewimage?orderId=${orderId}&amp;orderContentTypeId=IMAGE_URL</@ofbizUrl>" target="_orderImage" class="buttontext">${uiLabelMap.OrderViewImage}</a>
            </div>
        </div>
    </#if>
    <#if "SALES_ORDER" == orderHeader.orderTypeId>

        <div class="form-group">
            <div  class="text-right col-md-5">${uiLabelMap.FormFieldTitle_priority}:</div> 

            <div class="col-md-7">
                <form name="setOrderReservationPriority" method="post" action="<@ofbizUrl>setOrderReservationPriority</@ofbizUrl>">
                    <input type="hidden" name="orderId" value="${orderId}"/>
                    <select name="priority">
                        <option value="1" <#if (orderHeader.priority)?if_exists == "1">selected="selected" </#if>>${uiLabelMap.CommonHigh}</option>
                        <option value="2" <#if (orderHeader.priority)?if_exists == "2">selected="selected"
                                <#elseif !(orderHeader.priority)?has_content>selected="selected"</#if>>${uiLabelMap.CommonNormal}</option>
                        <option value="3" <#if (orderHeader.priority)?if_exists == "3">selected="selected" </#if>>${uiLabelMap.CommonLow}</option>
                    </select>
                    <input type="submit" class="btn btn-white btn-xs" value="${uiLabelMap.FormFieldTitle_reserveInventory}"/>
                </form>
            </div>
        </div>
    </#if>

        <div class="form-group">
            <div  class="text-right col-md-5">${uiLabelMap.AccountingInvoicePerShipment}:</div> 

            <div class="col-md-7">
                <form name="setInvoicePerShipment" method="post" action="<@ofbizUrl>setInvoicePerShipment</@ofbizUrl>">
                    <input type="hidden" name="orderId" value="${orderId}"/>
                    <select name="invoicePerShipment">
                        <option value="Y" <#if (orderHeader.invoicePerShipment)?if_exists == "Y">selected="selected" </#if>>${uiLabelMap.CommonYes}</option>
                        <option value="N" <#if (orderHeader.invoicePerShipment)?if_exists == "N">selected="selected" </#if>>${uiLabelMap.CommonNo}</option>
                    </select>
                    <input type="submit" class="btn btn-white btn-xs" value="${uiLabelMap.CommonUpdate}"/>
                </form>
            </div>
        </div>

    <#if orderHeader.isViewed?has_content && orderHeader.isViewed == "Y">
        <div class="form-group">
            <div  class="text-right col-md-5">${uiLabelMap.OrderViewed}:</div> 
            <div class="col-md-7">
            ${uiLabelMap.CommonYes}
            </div>
        </div>
    <#else>
        <div class="form-group" id="isViewed">
            <div  class="text-right col-md-5">${uiLabelMap.OrderMarkViewed}:</div> 

            <div class="col-md-7">
                <form id="orderViewed" action="">
                    <input type="checkbox" name="checkViewed" onclick="markOrderViewed();"/>
                    <input type="hidden" name="orderId" value="${orderId?if_exists}"/>
                    <input type="hidden" name="isViewed" value="Y"/>
                </form>
            </div>
        </div>
        <div class="form-group" id="viewed" style="display: none;">
            <div  class="text-right col-md-5">${uiLabelMap.OrderViewed}:</div> 

            <div class="col-md-7">
            ${uiLabelMap.CommonYes}
            </div>
        </div>
    </#if>
    </div>
    </div>
</div>
