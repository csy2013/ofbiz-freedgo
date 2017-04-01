<#--
Licensed to the Apache Software Foundation (ASF) under one
or more contributor license agreements.  See the NOTICE file
distributed with this work for additional information
regarding copyright ownership.  The ASF licenses this file
to you under the Apache License, Version 2.0 (the
"License"); you may not use this file except in compliance
with the License.  You may obtain a copy of the License at

http://www.apache.org/licenses/LICENSE-2.0

Unless required by applicable law or agreed to in writing,
software distributed under the License is distributed on an
"AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
KIND, either express or implied.  See the License for the
specific language governing permissions and limitations
under the License.
-->

<script language="JavaScript" type="text/javascript">
<!-- //
function lookupOrders(click) {
    orderIdValue = document.lookuporder.orderId.value;
    if (orderIdValue.length > 1) {
        document.lookuporder.action = "<@ofbizUrl>orderview</@ofbizUrl>";
        document.lookuporder.method = "get";
    } else {
        document.lookuporder.action = "<@ofbizUrl>searchorders</@ofbizUrl>";
    }

    if (click) {
        document.lookuporder.submit();
    }
    return true;
}
function toggleOrderId(master) {
    var form = document.massOrderChangeForm;
    var orders = form.elements.length;
    for (var i = 0; i < orders; i++) {
        var element = form.elements[i];
        if (element.name == "orderIdList") {
            element.checked = master.checked;
        }
    }
}
function setServiceName(selection) {
    document.massOrderChangeForm.action = selection.value;
}
function runAction() {
    var form = document.massOrderChangeForm;
    form.submit();
}

function toggleOrderIdList() {
    var form = document.massOrderChangeForm;
    var orders = form.elements.length;
    var isAllSelected = true;
    for (var i = 0; i < orders; i++) {
        var element = form.elements[i];
        if (element.name == "orderIdList" && !element.checked)
            isAllSelected = false;
    }
    jQuery('#checkAllOrders').attr("checked", isAllSelected);
}

// -->

    function paginateOrderList(viewSize, viewIndex, hideFields) {
        document.paginationForm.viewSize.value = viewSize;
        document.paginationForm.viewIndex.value = viewIndex;
        document.paginationForm.hideFields.value = hideFields;
        document.paginationForm.submit();
    }

</script>

<#if security.hasEntityPermission("ORDERMGR", "_VIEW", session)>
<#if parameters.hideFields?has_content>
<form name='lookupandhidefields${requestParameters.hideFields?default("Y")}' method="post" action="<@ofbizUrl>searchorders</@ofbizUrl>">
  <#if parameters.hideFields?default("N")=='Y'>
    <input type="hidden" name="hideFields" value="N"/>
  <#else>
    <input type='hidden' name='hideFields' value='Y'/>
  </#if>
  <input type="hidden" name="viewSize" value="${viewSize}"/>
  <input type="hidden" name="viewIndex" value="${viewIndex}"/>
  <input type='hidden' name='correspondingPoId' value='${requestParameters.correspondingPoId?if_exists}'/>
  <input type='hidden' name='internalCode' value='${requestParameters.internalCode?if_exists}'/>
  <input type='hidden' name='productId' value='${requestParameters.productId?if_exists}'/>
  <input type='hidden' name='inventoryItemId' value='${requestParameters.inventoryItemId?if_exists}'/>
  <input type='hidden' name='serialNumber' value='${requestParameters.serialNumber?if_exists}'/>
  <input type='hidden' name='softIdentifier' value='${requestParameters.softIdentifier?if_exists}'/>
  <input type='hidden' name='partyId' value='${requestParameters.partyId?if_exists}'/>
  <input type='hidden' name='userLoginId' value='${requestParameters.userLoginId?if_exists}'/>
  <input type='hidden' name='billingAccountId' value='${requestParameters.billingAccountId?if_exists}'/>
  <input type='hidden' name='createdBy' value='${requestParameters.createdBy?if_exists}'/>
  <input type='hidden' name='minDate' value='${requestParameters.minDate?if_exists}'/>
  <input type='hidden' name='maxDate' value='${requestParameters.maxDate?if_exists}'/>
  <input type='hidden' name='roleTypeId' value="${requestParameters.roleTypeId?if_exists}"/>
  <input type='hidden' name='orderTypeId' value='${requestParameters.orderTypeId?if_exists}'/>
  <input type='hidden' name='salesChannelEnumId' value='${requestParameters.salesChannelEnumId?if_exists}'/>
  <input type='hidden' name='productStoreId' value='${requestParameters.productStoreId?if_exists}'/>
  <input type='hidden' name='orderWebSiteId' value='${requestParameters.orderWebSiteId?if_exists}'/>
  <input type='hidden' name='orderStatusId' value='${requestParameters.orderStatusId?if_exists}'/>
  <input type='hidden' name='hasBackOrders' value='${requestParameters.hasBackOrders?if_exists}'/>
  <input type='hidden' name='filterInventoryProblems' value='${requestParameters.filterInventoryProblems?if_exists}'/>
  <input type='hidden' name='filterPartiallyReceivedPOs' value='${requestParameters.filterPartiallyReceivedPOs?if_exists}'/>
  <input type='hidden' name='filterPOsOpenPastTheirETA' value='${requestParameters.filterPOsOpenPastTheirETA?if_exists}'/>
  <input type='hidden' name='filterPOsWithRejectedItems' value='${requestParameters.filterPOsWithRejectedItems?if_exists}'/>
  <input type='hidden' name='countryGeoId' value='${requestParameters.countryGeoId?if_exists}'/>
  <input type='hidden' name='includeCountry' value='${requestParameters.includeCountry?if_exists}'/>
  <input type='hidden' name='isViewed' value='${requestParameters.isViewed?if_exists}'/>
  <input type='hidden' name='shipmentMethod' value='${requestParameters.shipmentMethod?if_exists}'/>
  <input type='hidden' name='gatewayAvsResult' value='${requestParameters.gatewayAvsResult?if_exists}'/>
  <input type='hidden' name='gatewayScoreResult' value='${requestParameters.gatewayScoreResult?if_exists}'/>
</form>
</#if>
<form method="post" name="lookuporder" id="lookuporder" action="<@ofbizUrl>searchorders</@ofbizUrl>" onsubmit="lookupOrders();">
<input type="hidden" name="lookupFlag" value="Y"/>
<input type="hidden" name="hideFields" value="Y"/>
<input type="hidden" name="viewSize" value="${viewSize}"/>
<input type="hidden" name="viewIndex" value="${viewIndex}"/>
<input type="hidden" name="countryGeoId" value="CHN"/>
<input type="hidden" name="includeCountry" value="Y"/>
<div class="am-cf am-padding-xs">
	<div id="findOrders" class="am-panel am-panel-default">
		<div class="am-panel-hd am-cf">
   			${uiLabelMap.OrderFindOrder}    
  		</div>
   		<div id="searchOptions_col" class="am-panel-bd am-collapse am-in">
  			<#if parameters.hideFields?default("N") != "Y">
			<div id="search-options" class="am-cf">
				<div class="am-g am-center">
					<div class="am-u-lg-10">						
						<div class="am-form-group am-g">
							<label style="text-align: right;" class="am-control-label am-u-md-3 am-u-lg-3" for="OrderOrderId">${uiLabelMap.OrderOrderId}</label>
							<div class="am-u-md-3 am-u-lg-3 am-u-end">
								<input type="text" name="orderId">
							</div>
							<label style="text-align: right;" class="am-control-label am-u-md-3 am-u-lg-3" for="ProductProductId">${uiLabelMap.ProductProductId}</label>
							<div class="am-u-md-3 am-u-lg-3 am-u-end">
								<input type="text" name='productId' value='${requestParameters.productId?if_exists}'>
							</div>
						</div>	
						<div class="am-form-group am-g">
							<label style="text-align: right;" class="am-control-label am-u-md-3 am-u-lg-3" for="PartyPartyId">${uiLabelMap.PartyPartyId}</label>
							<div class="am-u-md-4 am-u-lg-4 am-u-end">
								<@amazeHtmlTemplate.lookupField  value='${requestParameters.partyId?if_exists}' formName="lookuporder" name="partyId" id="partyId" fieldFormName="LookupPartyName"/>
							</div>
							<label style="text-align: right;" class="am-control-label am-u-md-2 am-u-lg-2" for="OrderSalesChannel">${uiLabelMap.OrderSalesChannel}</label>
							<div class="am-u-md-3 am-u-lg-3 am-u-end">
								<select name='salesChannelEnumId' data-am-selected>
			                    <#if currentSalesChannel?has_content>
				                    <option value="${currentSalesChannel.enumId}">${currentSalesChannel.get("description", locale)}</option>
				                    <option value="${currentSalesChannel.enumId}">---</option>
			                    </#if>
			                    	<option value="">${uiLabelMap.CommonAnySalesChannel}</option>
			                    <#list salesChannels as channel>
			                      	<option value="${channel.enumId}">${channel.get("description", locale)}</option>
			                    </#list>
			                  </select>
							</div>
						</div>	
						<div class="am-form-group am-g">
							<label style="text-align: right;" class="am-control-label am-u-md-3 am-u-lg-3" for="ProductProductStore">${uiLabelMap.ProductProductStore}</label>
							<div class="am-u-md-3 am-u-lg-3 am-u-end">
								<select name='productStoreId' data-am-selected>
				                    <#if currentProductStore?has_content>
					                    <option value="${currentProductStore.productStoreId}">${currentProductStore.storeName?if_exists}</option>
					                    <option value="${currentProductStore.productStoreId}">---</option>
				                    </#if>
				                    	<option value="">${uiLabelMap.CommonAnyStore}</option>
				                    <#list productStores as store>
				                      	<option value="${store.productStoreId}">${store.storeName?if_exists}</option>
				                    </#list>
				                </select>
							</div>
							<label style="text-align: right;" class="am-control-label am-u-md-3 am-u-lg-3" for="CommonStatus">${uiLabelMap.CommonStatus}</label>
							<div class="am-u-md-3 am-u-lg-3 am-u-end">
								<select name='orderStatusId' data-am-selected>
				                    <#if currentStatus?has_content>
					                    <option value="${currentStatus.statusId}">${currentStatus.get("description", locale)}</option>
					                    <option value="${currentStatus.statusId}">---</option>
				                    </#if>
				                    	<option value="">${uiLabelMap.OrderAnyOrderStatus}</option>
				                    <#list orderStatuses as orderStatus>
				                      	<option value="${orderStatus.statusId}">${orderStatus.get("description", locale)}</option>
				                    </#list>
				               </select>
							</div>
						</div>	
						<div class="am-form-group am-g">
							<label style="text-align: right;" class="am-control-label am-u-md-3 am-u-lg-3" for="AccountingPaymentStatus">${uiLabelMap.AccountingPaymentStatus}</label>
							<div class="am-u-md-3 am-u-lg-3 am-u-end">
								<select name="paymentStatusId" data-am-selected>
			                        <option value="">${uiLabelMap.CommonAll}</option>
			                        <#list paymentStatusList as paymentStatus>
			                            <option value="${paymentStatus.statusId}">${paymentStatus.get("description", locale)}</option>
			                        </#list>
			                    </select>
							</div>
							<label style="text-align: right;" class="am-control-label am-u-md-3 am-u-lg-3" for="OrderPayBy">${uiLabelMap.OrderPayBy}</label>
							<div class="am-u-md-3 am-u-lg-3 am-u-end">
								<select name= "paymentMethodTypeId" id="paymentMethodTypeId" class="form-control" data-am-selected>
								   <option value= "EXT_OFFLINE">${uiLabelMap.OrderMoneyOrder}</option>
					              <!--  <option value= "EXT_COD">${uiLabelMap.OrderCOD}</option>
					               <option value= "EXT_WORLDPAY">${uiLabelMap.AccountingPayWithWorldPay}</option> -->
					               <option value= "EXT_PAYPAL">${uiLabelMap.AccountingPayWithPayPal}</option>
					            <select>

							</div>
						</div>	
						<div class="am-form-group am-g">
							<label style="text-align: right;" class="am-control-label am-u-md-3 am-u-lg-3" for="OrderOrderId">${uiLabelMap.CommonDateFilter}</label>
							<div class="am-u-md-4 am-u-lg-4 am-u-end">
								<@amazeHtmlTemplate.renderDateTimeField name="minDate" event="" action="" value="${requestParameters.minDate?if_exists}" className="" alert="" title="Format: yyyy-MM-dd HH:mm:ss.SSS" size="25" maxlength="30" id="minDate1" dateType="date" shortDateInput=false timeDropdownParamName="" defaultDateTimeString="" localizedIconTitle="" timeDropdown="" timeHourName="" classString="" hour1="" hour2="" timeMinutesName="" minutes="" isTwelveHour="" ampmName="" amSelected="" pmSelected="" compositeType="" formName=""/>
                        		<!-- <span class='label'>${uiLabelMap.CommonFrom}</span> -->
							</div>
							<label style="text-align:center;" class="am-control-label am-u-md-1 am-u-lg-1" for="OrderOrderId">~</label>
							<div class="am-u-md-4 am-u-lg-4 am-u-end" >
								<@amazeHtmlTemplate.renderDateTimeField name="maxDate" event="" action="" value="${requestParameters.maxDate?if_exists}" className="" alert="" title="Format: yyyy-MM-dd HH:mm:ss.SSS" size="25" maxlength="30" id="maxDate1" dateType="date" shortDateInput=false timeDropdownParamName="" defaultDateTimeString="" localizedIconTitle="" timeDropdown="" timeHourName="" classString="" hour1="" hour2="" timeMinutesName="" minutes="" isTwelveHour="" ampmName="" amSelected="" pmSelected="" compositeType="" formName=""/>
                        		<!-- <span class='label'>${uiLabelMap.CommonThru}</span> -->
							</div>
						</div>	
						<div class="am-form-group am-g">
							<label style="text-align: right;" class="am-control-label am-u-md-3 am-u-lg-3" for="HotelParty">${uiLabelMap.HotelPartyId}</label>
							<div class="am-u-md-4 am-u-lg-4 am-u-end">								
								<@amazeHtmlTemplate.lookupField  value='${requestParameters.vendorPartyId?if_exists}' formName="lookuporder" name="supplierPartyId" id="supplierPartyId" fieldFormName="LookupSupplierProduct"/>
							</div>
							<!-- <label style="text-align: right;" class="am-control-label am-u-md-2 am-u-lg-2"></label>
							<div class="am-u-md-3 am-u-lg-3 am-u-end">
								
							</div> -->
							<div class="am-u-md-5 am-u-lg-5 am-u-end">
								<div class="am-input-group am-cf am-fr">
									<input type="hidden" name="showAll" value="Y"/>
									<input class="am-btn am-btn-primary am-btn-sm am-fl" type="submit"  value="${uiLabelMap.CommonFind}">
								</div>
							</div>
						</div>				
				    </div>
				 </div>
			   </div>
			</#if>
		</div>      
    </div>
</div>
<input type="image" src="<@ofbizContentUrl>/images/spacer.gif</@ofbizContentUrl>" onclick="lookupOrders(true);"/>
</form>
<#if requestParameters.hideFields?default("N") != "Y">
<script language="JavaScript" type="text/javascript">
<!--//
document.lookuporder.orderId.focus();
//-->
</script>
</#if>

<br />

<div class="am-cf am-padding-xs">
	<div id="findOrdersList" class="am-panel am-panel-default">
		<div class="am-panel-hd am-cf">
			${uiLabelMap.OrderOrderFound}
			<#if (orderList?has_content && 0 < orderList?size)>
		        <#if (orderListSize > highIndex)>
		          <a href="javascript:paginateOrderList('${viewSize}', '${viewIndex+1}', '${requestParameters.hideFields?default("N")}')">${uiLabelMap.CommonNext}</a>
		        <#else>
		          <span class="disabled">${uiLabelMap.CommonNext}</span>
		        </#if>
		        <#if (orderListSize > 0)>
		          <span>${lowIndex} - ${highIndex} ${uiLabelMap.CommonOf} ${orderListSize}</span>
		        </#if>
		        <#if (viewIndex > 1)>
		          <a href="javascript:paginateOrderList('${viewSize}', '${viewIndex-1}', '${requestParameters.hideFields?default("N")}')">${uiLabelMap.CommonPrevious}</a>
		        <#else>
		          <span class="disabled">${uiLabelMap.CommonPrevious}</span>
		        </#if>
		     </#if>
		     <br class="clear" />
		 </div>		 
		 <div id="screenlet_1_col" class="am-panel-bd am-collapse am-in">
		 	<form name="paginationForm" method="post" action="<@ofbizUrl>searchorders</@ofbizUrl>">
		      <input type="hidden" name="viewSize"/>
		      <input type="hidden" name="viewIndex"/>
		      <input type="hidden" name="hideFields"/>
		      <#if paramIdList?exists && paramIdList?has_content>
		        <#list paramIdList as paramIds>
		          <#assign paramId = paramIds.split("=")/>
		          <input type="hidden" name="${paramId[0]}" value="${paramId[1]}"/>
		        </#list>
		      </#if>
		    </form>
		    <form name="massOrderChangeForm" method="post" action="javascript:void(0);">
			    <input type="hidden" name="screenLocation" value="component://order/widget/ordermgr/OrderPrintScreens.xml#OrderPDF"/>
	        	    <div id="search-options" class="am-cf">
						<div class="am-g am-center">
							<div class="am-u-lg-10">						
								<div class="am-form-group am-g">
									<!-- <label style="text-align: right;" class="am-control-label am-u-md-3 am-u-lg-3" for=""></label> -->
									<div style="text-align: right;" class="am-u-md-10 am-u-lg-10 am-u-end">
										<select name="serviceName" onchange="setServiceName(this);" data-am-selected>
								           <option value="javascript:void(0);">&nbsp;</option>
								           <option value="<@ofbizUrl>massApproveOrders?hideFields=${requestParameters.hideFields?default("N")}${paramList}</@ofbizUrl>">${uiLabelMap.OrderApproveOrder}</option>
								           <option value="<@ofbizUrl>massHoldOrders?hideFields=${requestParameters.hideFields?default("N")}${paramList}</@ofbizUrl>">${uiLabelMap.OrderHold}</option>
								           <option value="<@ofbizUrl>massProcessOrders?hideFields=${requestParameters.hideFields?default("N")}${paramList}</@ofbizUrl>">${uiLabelMap.OrderProcessOrder}</option>
								           <option value="<@ofbizUrl>massCancelOrders?hideFields=${requestParameters.hideFields?default("N")}${paramList}</@ofbizUrl>">${uiLabelMap.OrderCancelOrder}</option>
								           <option value="<@ofbizUrl>massCancelRemainingPurchaseOrderItems?hideFields=${requestParameters.hideFields?default("N")}${paramList}</@ofbizUrl>">${uiLabelMap.OrderCancelRemainingPOItems}</option>
								           <option value="<@ofbizUrl>massRejectOrders?hideFields=${requestParameters.hideFields?default("N")}${paramList}</@ofbizUrl>">${uiLabelMap.OrderRejectOrder}</option>
								           <option value="<@ofbizUrl>massPickOrders?hideFields=${requestParameters.hideFields?default("N")}${paramList}</@ofbizUrl>">${uiLabelMap.OrderPickOrders}</option>
								           <option value="<@ofbizUrl>massQuickShipOrders?hideFields=${requestParameters.hideFields?default("N")}${paramList}</@ofbizUrl>">${uiLabelMap.OrderQuickShipEntireOrder}</option>
								           <option value="<@ofbizUrl>massPrintOrders?hideFields=${requestParameters.hideFields?default('N')}${paramList}</@ofbizUrl>">${uiLabelMap.CommonPrint}</option>
								           <option value="<@ofbizUrl>massCreateFileForOrders?hideFields=${requestParameters.hideFields?default('N')}${paramList}</@ofbizUrl>">${uiLabelMap.ContentCreateFile}</option>
								        </select>
									</div>
									<!-- <div class="am-u-md-3 am-u-lg-3 am-u-end">
								        <select name="printerName" data-am-selected>
								           <option value="javascript:void(0);">&nbsp;</option>
								           <#list printers as printer>
								           <option value="${printer}">${printer}</option>
								           </#list>
								        </select>
									</div> -->
									<div class="am-u-md-2 am-u-lg-2 am-u-end">
										<div class="am-input-group am-cf am-fr">
											<input class="am-btn am-btn-primary am-btn-sm am-fl" onclick="runAction();"  value="${uiLabelMap.OrderRunAction}">
										</div>
									</div>
								</div>	
							</div>
						</div>
					</div>
				 	<div class="am-g">
						<div class="am-u-sm-12">
							<table class="am-table am-table-striped am-table-hover table-main" >	
			 					<tr class="header-row">
						          <td width="1%">
						            <input type="checkbox" id="checkAllOrders" name="checkAllOrders" value="1" onchange="toggleOrderId(this);"/>
						          </td>
						         <!--  <td width="5%">${uiLabelMap.OrderOrderType}</td> -->
						          <td width="5%">${uiLabelMap.OrderOrderId}</td>
						          <td width="20%">${uiLabelMap.PartyName}</td>
						         <!--  <td width="5%" align="right">${uiLabelMap.OrderSurvey}</td>
						          <td width="5%" align="right">${uiLabelMap.OrderItemsOrdered}</td>
						          <td width="5%" align="right">${uiLabelMap.OrderItemsBackOrdered}</td>
						          <td width="5%" align="right">${uiLabelMap.OrderItemsReturned}</td> -->
						          <td width="10%" align="right">${uiLabelMap.OrderRemainingSubTotal}</td>
						          <td width="10%" align="right">${uiLabelMap.OrderOrderTotal}</td>
						          <td width="5%">&nbsp;</td>
						            <#if (requestParameters.filterInventoryProblems?default("N") == "Y") || (requestParameters.filterPOsOpenPastTheirETA?default("N") == "Y") || (requestParameters.filterPOsWithRejectedItems?default("N") == "Y") || (requestParameters.filterPartiallyReceivedPOs?default("N") == "Y")>
						              <td width="15%">${uiLabelMap.CommonStatus}</td>
						              <td width="5%">${uiLabelMap.CommonFilter}</td>
						            <#else>
						              <td width="20%">${uiLabelMap.CommonStatus}</td>
						            </#if>
						          <td width="20%">${uiLabelMap.OrderDate}</td>
						          <!-- <td width="5%">${uiLabelMap.PartyPartyId}</td> -->
						          <td width="10%">&nbsp;</td>
						        </tr>
						        <#if orderList?has_content>
						          <#assign alt_row = false>
						          <#list orderList as orderHeader>
						            <#assign orh = Static["org.ofbiz.order.order.OrderReadHelper"].getHelper(orderHeader)>
						            <#assign statusItem = orderHeader.getRelatedOneCache("StatusItem")>
						            <#assign orderType = orderHeader.getRelatedOneCache("OrderType")>
						            <#if orderType.orderTypeId == "PURCHASE_ORDER">
						              <#assign displayParty = orh.getSupplierAgent()?if_exists>
						            <#else>
						              <#assign displayParty = orh.getPlacingParty()?if_exists>
						            </#if>
						            <#assign partyId = displayParty.partyId?default("_NA_")>
						            <tr valign="middle"<#if alt_row> class="alternate-row"</#if>>
						              <td>
						                 <input type="checkbox" name="orderIdList" value="${orderHeader.orderId}" onchange="toggleOrderIdList();"/>
						              </td>
						             <!--  <td>${orderType.get("description",locale)?default(orderType.orderTypeId?default(""))}</td> -->
						              <td><a href="<@ofbizUrl>orderview?orderId=${orderHeader.orderId}</@ofbizUrl>" class='buttontext'>${orderHeader.orderId}</a></td>
						              <td>
						                <div>
						                  <#if displayParty?has_content>
						                      <#assign displayPartyNameResult = dispatcher.runSync("getPartyNameForDate", Static["org.ofbiz.base.util.UtilMisc"].toMap("partyId", displayParty.partyId, "compareDate", orderHeader.orderDate, "userLogin", userLogin))/>
						                      ${displayPartyNameResult.fullName?default("[${uiLabelMap.OrderPartyNameNotFound}]")}
						                  <#else>
						                    ${uiLabelMap.CommonNA}
						                  </#if>
						                </div>
						                <#--
						                <div>
						                <#if placingParty?has_content>
						                  <#assign partyId = placingParty.partyId>
						                  <#if placingParty.getEntityName() == "Person">
						                    <#if placingParty.lastName?exists>
						                      ${placingParty.lastName}<#if placingParty.firstName?exists>, ${placingParty.firstName}</#if>
						                    <#else>
						                      ${uiLabelMap.CommonNA}
						                    </#if>
						                  <#else>
						                    <#if placingParty.groupName?exists>
						                      ${placingParty.groupName}
						                    <#else>
						                      ${uiLabelMap.CommonNA}
						                    </#if>
						                  </#if>
						                <#else>
						                  ${uiLabelMap.CommonNA}
						                </#if>
						                </div>
						                -->
						              </td>
						             <!--  <td align="right">${orh.hasSurvey()?string.number}</td>
						              <td align="right">${orh.getTotalOrderItemsQuantity()?string.number}</td>
						              <td align="right">${orh.getOrderBackorderQuantity()?string.number}</td>
						              <td align="right">${orh.getOrderReturnedQuantity()?string.number}</td> -->
						              <td align="right"><@ofbizCurrency amount=orderHeader.remainingSubTotal isoCode=orh.getCurrency()/></td>
						              <td align="right"><@ofbizCurrency amount=orderHeader.grandTotal isoCode=orh.getCurrency()/></td>
						
						              <td>&nbsp;</td>
						              <td>${statusItem.get("description",locale)?default(statusItem.statusId?default("N/A"))}</td>
						              </td>
						              <#if (requestParameters.filterInventoryProblems?default("N") == "Y") || (requestParameters.filterPOsOpenPastTheirETA?default("N") == "Y") || (requestParameters.filterPOsWithRejectedItems?default("N") == "Y") || (requestParameters.filterPartiallyReceivedPOs?default("N") == "Y")>
						                  <td>
						                      <#if filterInventoryProblems.contains(orderHeader.orderId)>
						                        Inv&nbsp;
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
						              </#if>
						              <td>${orderHeader.getString("orderDate")}</td>
						             <!--  <td>
						                <#if partyId != "_NA_">
						                  <a href="${customerDetailLink}${partyId}" class="buttontext">${partyId}</a>
						                <#else>
						                  ${uiLabelMap.CommonNA}
						                </#if>
						              </td> -->
						              <td align='right'>
						                <a href="<@ofbizUrl>orderview?orderId=${orderHeader.orderId}</@ofbizUrl>" class='buttontext'>${uiLabelMap.CommonView}</a>
						              </td>
						            </tr>
						            <#-- toggle the row color -->
						            <#assign alt_row = !alt_row>
						          </#list>
						        <#else>
						          <tr>
						            <td colspan='4'><h3>${uiLabelMap.OrderNoOrderFound}</h3></td>
						          </tr>
						        </#if>
						        <#if lookupErrorMessage?exists>
						          <tr>
						            <td colspan='4'><h3>${lookupErrorMessage}</h3></td>
						          </tr>
						        </#if>
			 				</table>
			 			</div>
			 		</div>
			 	</form>
			 </div>
		</div>
	</div>
<#else>
  <h3>${uiLabelMap.OrderViewPermissionError}</h3>
</#if>
		
