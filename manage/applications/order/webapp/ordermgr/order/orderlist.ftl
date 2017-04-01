    <script type="text/javascript">
        var checkBoxNameStart = "view";
        var formName = "findorder";
        function setCheckboxes() {
            // This would be clearer with camelCase variable names
            var allCheckbox = document.forms[formName].elements[checkBoxNameStart + "all"];
            for (i = 0; i < document.forms[formName].elements.length; i++) {
                var elem = document.forms[formName].elements[i];
                if (elem.name.indexOf(checkBoxNameStart) == 0 && elem.name.indexOf("_") < 0 && elem.type == "checkbox") {
                    elem.checked = allCheckbox.checked;
                }
            }
        }

    </script>

    <#macro pagination>
    <table class="basic-table" cellspacing='0'>
        <tr>
            <td>
                <#if state.hasPrevious()>
                    <a href="<@ofbizUrl>orderlist?viewIndex=${state.getViewIndex() - 1}&amp;viewSize=${state.getViewSize()}&amp;filterDate=${filterDate?if_exists}</@ofbizUrl>"
                       class="buttontext">上页</a>
                </#if>
            </td>
            <td align="right">
                <#if state.hasNext()>
                    <a href="<@ofbizUrl>orderlist?viewIndex=${state.getViewIndex() + 1}&amp;viewSize=${state.getViewSize()}&amp;filterDate=${filterDate?if_exists}</@ofbizUrl>"
                       class="buttontext">下页</a>
                </#if>
            </td>
        </tr>
    </table>
    </#macro>
    <#-- order list -->
    <@htmlScreenTemplate.renderScreenletBegin id="orderLookup" title="${uiLabelMap.OrderLookupOrder}"/>
    <form method="post" name="findorder" action="<@ofbizUrl>orderlist</@ofbizUrl>" class="form-inline">
        <input type="hidden" name="changeStatusAndTypeState" value="Y"/>

        <div class="input-group m-b-10 m-r-10">
            <span class="input-group-addon">${uiLabelMap.CommonStatus}</span>
            <span class="input-group-addon"><input type="checkbox" class="checkbox-inline" name="viewall" value="Y" onclick="setCheckboxes()"
                   <#if state.hasAllStatus()>checked="checked"</#if> />
            ${uiLabelMap.CommonAll}
            <input type="checkbox" class="checkbox-inline" name="viewcreated" value="Y" <#if state.hasStatus('viewcreated')>checked="checked"</#if> />${uiLabelMap.CommonCreated}
            <input type="checkbox" class="checkbox-inline" name="viewprocessing" value="Y" <#if state.hasStatus('viewprocessing')>checked="checked"</#if> />${uiLabelMap.CommonProcessing}
            <input type="checkbox" class="checkbox-inline" name="viewapproved" value="Y" <#if state.hasStatus('viewapproved')>checked="checked"</#if> />${uiLabelMap.CommonApproved}
            <input type="checkbox" class="checkbox-inline" name="viewhold" value="Y" <#if state.hasStatus('viewhold')>checked="checked"</#if> />${uiLabelMap.CommonHeld}
            <input type="checkbox" class="checkbox-inline" name="viewcompleted" value="Y" <#if state.hasStatus('viewcompleted')>checked="checked"</#if> />${uiLabelMap.CommonCompleted}
        <#--input type="checkbox" name="viewsent" value="Y" <#if state.hasStatus('viewsent')>checked="checked"</#if> />${uiLabelMap.CommonSent}-->
            <input type="checkbox" class="checkbox-inline" name="viewrejected" value="Y" <#if state.hasStatus('viewrejected')>checked="checked"</#if> />${uiLabelMap.CommonRejected}
            <input type="checkbox" class="checkbox-inline" name="viewcancelled" value="Y" <#if state.hasStatus('viewcancelled')>checked="checked"</#if> />${uiLabelMap.CommonCancelled}
                </span>
        </div>
        <div class="input-group m-b-10 m-r-10">
            <span class="input-group-addon">${uiLabelMap.CommonType}</span>
            <span class="input-group-addon"><input type="checkbox" class="checkbox-inline" name="view_SALES_ORDER" value="Y" <#if state.hasType('view_SALES_ORDER')>checked="checked"</#if>/></span>
            <span class="input-group-addon">${descr_SALES_ORDER}</span>
            <span class="input-group-addon"><input type="checkbox" class="checkbox-inline" name="view_PURCHASE_ORDER" value="Y" <#if state.hasType('view_PURCHASE_ORDER')>checked="checked"</#if>/></span>
            <span class="input-group-addon">${descr_PURCHASE_ORDER}</span>

        </div>
       <#-- <div class="input-group m-b-10 m-r-10">
            <span class="input-group-addon">${uiLabelMap.CommonFilter}</span>
            <span class="input-group-addon"><input type="checkbox" class="checkbox-inline" name="filterInventoryProblems" value="Y"
                   <#if state.hasFilter('filterInventoryProblems')>checked="checked"</#if>/></span>
            <span class="input-group-addon">${uiLabelMap.OrderFilterInventoryProblems}</span>
            <span class="input-group-addon"><input type="checkbox" class="checkbox-inline" name="filterAuthProblems" value="Y"
                           <#if state.hasFilter('filterAuthProblems')>checked="checked"</#if>/></span>
                           <span class="input-group-addon">${uiLabelMap.OrderFilterAuthProblems}</span>

        </div>-->
        <#--<div class="input-group m-b-10 m-r-10">
            <span class="input-group-addon">${uiLabelMap.CommonFilter} (${uiLabelMap.OrderFilterPOs})</span>

            <span class="input-group-addon"><input type="checkbox" class="checkbox-inline" name="filterPartiallyReceivedPOs" value="Y"
                   <#if state.hasFilter('filterPartiallyReceivedPOs')>checked="checked"</#if>/></span>
            <span class="input-group-addon">${uiLabelMap.OrderFilterPartiallyReceivedPOs}</span>
            <span class="input-group-addon"><input type="checkbox" class="checkbox-inline" name="filterPOsOpenPastTheirETA" value="Y"
                   <#if state.hasFilter('filterPOsOpenPastTheirETA')>checked="checked"</#if>/></span>
            <span class="input-group-addon"> ${uiLabelMap.OrderFilterPOsOpenPastTheirETA}</span>
            <span class="input-group-addon"><input type="checkbox" class="checkbox-inline" name="filterPOsWithRejectedItems" value="Y"
                   <#if state.hasFilter('filterPOsWithRejectedItems')>checked="checked"</#if>/></span>
            <span class="input-group-addon">${uiLabelMap.OrderFilterPOsWithRejectedItems}</span>

        </div>-->


        <a href="javascript:document.findorder.submit()" class="btn btn-primary btn-sm m-b-10">${uiLabelMap.CommonFind}</a>


    </form>
    <@htmlScreenTemplate.renderScreenEnd/>
    <hr/>
    <#if hasPermission>

    <div class="table-responsive">
        <table class="table table-striped table-bordered">
            <tr class="header-row">
                <th width="15%">${uiLabelMap.CommonDate}</th>
                <th width="10%">${uiLabelMap.OrderOrder} ${uiLabelMap.CommonNbr}</th>
                <th width="10%">${uiLabelMap.OrderOrderName}</th>
                <th width="10%">${uiLabelMap.OrderOrderType}</th>
                <th width="10%">${uiLabelMap.OrderOrderBillFromParty}</th>
                <th width="10%">${uiLabelMap.OrderOrderBillToParty}</th>
                <th width="10%">${uiLabelMap.OrderProductStore}</th>
                <th width="10%">${uiLabelMap.CommonAmount}</th>
                <th width="10%">${uiLabelMap.OrderTrackingCode}</th>
                <#if state.hasFilter('filterInventoryProblems') || state.hasFilter('filterAuthProblems') || state.hasFilter('filterPOsOpenPastTheirETA') || state.hasFilter('filterPOsWithRejectedItems') || state.hasFilter('filterPartiallyReceivedPOs')>
                    <th width="10%">${uiLabelMap.CommonStatus}</th>
                    <th width="5%">${uiLabelMap.CommonFilter}</th>
                <#else>
                    <th colspan="2" width="15%">${uiLabelMap.CommonStatus}</th>
                </#if>
            </tr>
            <#list orderHeaderList as orderHeader>
                <#assign status = orderHeader.getRelatedOneCache("StatusItem")>
                <#assign orh = Static["org.ofbiz.order.order.OrderReadHelper"].getHelper(orderHeader)>
                <#assign billToParty = orh.getBillToParty()?if_exists>
                <#assign billFromParty = orh.getBillFromParty()?if_exists>
                <#if billToParty?has_content>
                    <#assign billToPartyNameResult = dispatcher.runSync("getPartyNameForDate", Static["org.ofbiz.base.util.UtilMisc"].toMap("partyId", billToParty.partyId, "compareDate", orderHeader.orderDate, "userLogin", userLogin))/>
                    <#assign billTo = billToPartyNameResult.fullName?default("[${uiLabelMap.OrderPartyNameNotFound}]")/>
                <#-- <#assign billTo = Static["org.ofbiz.party.party.PartyHelper"].getPartyName(billToParty, true)?if_exists> -->
                <#else>
                    <#assign billTo = ''/>
                </#if>
                <#if billFromParty?has_content>
                    <#assign billFrom = Static["org.ofbiz.party.party.PartyHelper"].getPartyName(billFromParty, true)?if_exists>
                <#else>
                    <#assign billFrom = ''/>
                </#if>
                <#assign productStore = orderHeader.getRelatedOneCache("ProductStore")?if_exists />
                <tr>
                    <td><#if orderHeader.orderDate?has_content>${Static["org.ofbiz.base.util.UtilFormatOut"].formatDateTime(orderHeader.orderDate, "", locale, timeZone)!}</#if></td>
                    <td>
                        <a href="<@ofbizUrl>orderview?orderId=${orderHeader.orderId}</@ofbizUrl>" class="buttontext">${orderHeader.orderId}</a>
                    </td>
                    <td>${orderHeader.orderName?if_exists}</td>
                    <td>${orderHeader.getRelatedOneCache("OrderType").get("description",locale)}</td>
                    <td>${billFrom?if_exists}</td>
                    <td>${billTo?if_exists}</td>
                    <td><#if productStore?has_content>${productStore.storeName?default(productStore.productStoreId)}</#if></td>
                    <td><@ofbizCurrency amount=orderHeader.grandTotal isoCode=orderHeader.currencyUom/></td>
                    <td>
                        <#assign trackingCodes = orderHeader.getRelated("TrackingCodeOrder")>
                        <#list trackingCodes as trackingCode>
                            <#if trackingCode?has_content>
                                <a href="/marketing/control/FindTrackingCodeOrders?trackingCodeId=${trackingCode.trackingCodeId}&amp;externalLoginKey=${requestAttributes.externalLoginKey?if_exists}">${trackingCode.trackingCodeId}</a><br/>
                            </#if>
                        </#list>
                    </td>
                    <td>${orderHeader.getRelatedOneCache("StatusItem").get("description",locale)}</td>
                    <#if state.hasFilter('filterInventoryProblems') || state.hasFilter('filterAuthProblems') || state.hasFilter('filterPOsOpenPastTheirETA') || state.hasFilter('filterPOsWithRejectedItems') || state.hasFilter('filterPartiallyReceivedPOs')>
                        <td>
                            <#if filterInventoryProblems.contains(orderHeader.orderId)>
                                Inv&nbsp;
                            </#if>
                            <#if filterAuthProblems.contains(orderHeader.orderId)>
                                Aut&nbsp;
                            </#if>
                            <#if filterPOsOpenPastTheirETA.contains(orderHeader.orderId)>
                                ETA&nbsp;
                            </#if>
                            <#if filterPOsWithRejectedItems.contains(orderHeader.orderId)>
                                Rej&nbsp;
                            </#if>
                            <#if filterPartiallyReceivedPOs.contains(orderHeader.orderId)>
                                Part&nbsp;
                            </#if>
                        </td>
                    <#else>
                        <td>&nbsp;</td>
                    </#if>
                </tr>
            </#list>
            <#if !orderHeaderList?has_content>
                <tr>
                    <td colspan="9"><h3>${uiLabelMap.OrderNoOrderFound}</h3></td>
                </tr>
            </#if>
        </table>
    </div>
        <@pagination/>
    <#else>
    <h3>${uiLabelMap.OrderViewPermissionError}</h3>
    </#if>

