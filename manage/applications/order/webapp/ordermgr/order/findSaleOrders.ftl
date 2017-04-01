<#if parameters.ajaxUpdateEvent?default("N") != 'Y'>
<style>
    .l1 {
        position: relative;
        /*height: 70px;*/
        /*padding: 15px 85px 15px 0;*/
        margin-left: 8px;
        overflow: hidden;
        /*background: #fff;*/
        color: #3f3f3f;

    }

    .l1 ul {
        width: 100%;
        padding-top: 9px;
        /*height: 56px;*/
        overflow: hidden;
        white-space: nowrap;

    }

    .l1 li {
        min-width: 60px;
        padding-bottom: 9px;
        position: relative;
        display: block;
        vertical-align: top;
        z-index: 90;
        float: left;
        white-space: nowrap;
    }

    .l1 img {
        width: 50px;
        height: 50px;
        border: 1px solid #ebebeb;
        border-radius: 5px;
        display: inline-block;
    }

    .l1 .icon_point {
        position: absolute;
        right: 3px;
        top: -7px;
        color: #ff5757;
        width: 15px;
        height: 15px;
        text-align: center;
        line-height: 15px;
        font-size: 10px;
        border-radius: 9px;
        border: 1px solid #ff5757;
        background-color: #fff;
        white-space: nowrap;
    }
</style>
<script language="JavaScript" type="text/javascript">

    <!-- //
    function lookupOrders(click) {
        document.lookuporder.action = "<@ofbizUrl>searchsaleorders</@ofbizUrl>";
        ajaxSubmitFormUpdateAreas('lookuporder', 'ajax,search-results,,');
        //根据查询的订单的状态判断执行的动作
        $("select[name='serviceName']").html('');
        var statusId = $("select[name='orderStatusId']").val();
        if(statusId == ''){
        }else if(statusId == 'ORDER_CREATED'){
            $("select[name='serviceName']").append('<option value="<@ofbizUrl>massApproveOrders?hideFields=${requestParameters.hideFields?default("N")}${paramList}</@ofbizUrl>">${uiLabelMap.OrderApproveOrder}</option>');
            $("select[name='serviceName']").append('<option value="<@ofbizUrl>massCancelOrders?hideFields=${requestParameters.hideFields?default("N")}${paramList}</@ofbizUrl>">${uiLabelMap.OrderCancelOrder}</option>');
        }else if(statusId == 'ORDER_APPROVED'){
            $("select[name='serviceName']").append('<option value="<@ofbizUrl>massHoldOrders?hideFields=${requestParameters.hideFields?default("N")}${paramList}</@ofbizUrl>">${uiLabelMap.OrderHold}</option>');
            $("select[name='serviceName']").append('<option value="<@ofbizUrl>massCancelOrders?hideFields=${requestParameters.hideFields?default("N")}${paramList}</@ofbizUrl>">${uiLabelMap.OrderCancelOrder}</option>');
            $("select[name='serviceName']").append('<option value="<@ofbizUrl>massPickOrders?hideFields=${requestParameters.hideFields?default("N")}${paramList}</@ofbizUrl>">${uiLabelMap.OrderPickOrders}</option>');
            $("select[name='serviceName']").append('<option value="<@ofbizUrl>massQuickShipOrders?hideFields=${requestParameters.hideFields?default("N")}${paramList}</@ofbizUrl>">${uiLabelMap.OrderQuickShipEntireOrder}</option>');
        }else if(statusId == 'ORDER_HOLD'){
            $("select[name='serviceName']").append('<option value="<@ofbizUrl>massApproveOrders?hideFields=${requestParameters.hideFields?default("N")}${paramList}</@ofbizUrl>">${uiLabelMap.OrderApproveOrder}</option>');
            $("select[name='serviceName']").append('<option value="<@ofbizUrl>massCancelOrders?hideFields=${requestParameters.hideFields?default("N")}${paramList}</@ofbizUrl>">${uiLabelMap.OrderCancelOrder}</option>');
        }else if(statusId == 'ORDER_COMPLETED'){
        }else if(statusId == 'ORDER_CANCELLED'){
        }

        $("select[name='serviceName']").append('<option value="<@ofbizUrl>massPrintOrders?hideFields=${requestParameters.hideFields?default('N')}${paramList}</@ofbizUrl>">${uiLabelMap.CommonPrint}</option>');
        $("select[name='serviceName']").append('<option value="<@ofbizUrl>massCreateFileForOrders?hideFields=${requestParameters.hideFields?default('N')}${paramList}</@ofbizUrl>">生成订单PDF</option>');

    }
    function toggleOrderId(master) {
        if ($(master).is(':checked')) {
            $("input[name='orderIdList1']").each(function () {
                $(this).prop("checked", "checked");
            });
        } else {
            $("input[name='orderIdList1']").each(function () {
                $(this).prop("checked", "");
            });
        }

        /*var form = document.massOrderChangeForm;
        var orders = form.elements.length;
        for (var i = 0; i < orders; i++) {
            var element = form.elements[i];
            if (element.name == "orderIdList") {
                element.checked = master.checked;
            }
        }*/
    }
    function setServiceName(selection) {
        var sel = selection.value;
        console.log(sel);
        if(sel.indexOf("massPrintOrders")!= -1){
            $("select[name='printerName']").show();
        }else{
            $("select[name='printerName']").hide();
        }
        document.massOrderChangeForm.action = selection.value;
    }
    function runAction() {

        var form = document.massOrderChangeForm;
        var val = '';
        $("input[name='orderIdList1']").each(function () {
            if($(this).is(':checked')) {
                val += $(this).val() + ","
            }
        });
        if(val!='') {
            $("input[name='orderIdList']").val(val);
            form.submit();
        }else{
            alert('请选择至少选择一个订单');
        }
    }

    function toggleOrderIdList() {

        var isAllSelected = true;
        $("input[name='orderIdList1']").each(function () {
            if (!$(this).is(':checked')) {
                isAllSelected = false;
            }
        });
        $('#checkAllOrders').attr("checked", isAllSelected);
    }
    // -->
    function paginateOrderList(viewSize, viewIndex, hideFields) {
        document.paginationForm.viewSize.value = viewSize;
        document.paginationForm.viewIndex.value = viewIndex;
        document.paginationForm.hideFields.value = hideFields;
        document.paginationForm.submit();
    }

    function showHideQuery(hide) {
        console.log($("#lookuporder").find("input[name='hideFields']").val());
        if ($("#lookuporder").find("input[name='hideFields']").val() == 'N') {
            $('#lookuporder').show();
            $('#showHideQueryBtn').text('隐藏查询项');
            $("#lookuporder").find("input[name='hideFields']").val('Y');
        } else {
            $('#lookuporder').hide();
            $('#showHideQueryBtn').text('显示查询项');
            $("#lookuporder").find("input[name='hideFields']").val('N');
        }

    }

    $(function () {
        lookupOrders();
        //只显示打印。PDF
        $("select[name='serviceName']").html('');
        $("select[name='serviceName']").append('<option value="<@ofbizUrl>massPrintOrders?hideFields=${requestParameters.hideFields?default('N')}${paramList}</@ofbizUrl>">${uiLabelMap.CommonPrint}</option>');
        $("select[name='serviceName']").append('<option value="<@ofbizUrl>massCreateFileForOrders?hideFields=${requestParameters.hideFields?default('N')}${paramList}</@ofbizUrl>">生成订单PDF</option>');

        //隐藏打印机
        $("select[name='printerName']").hide();
        if ($("#lookuporder").find("input[name='hideFields']").val() == 'N') {
            $('#lookuporder').hide();
            $('#showHideQueryBtn').text('显示查询项');
            $("#lookuporder").find("input[name='hideFields']").val('N');
        } else {
            $('#lookuporder').show();
            $('#showHideQueryBtn').text('隐藏查询项');
            $("#lookuporder").find("input[name='hideFields']").val('Y');
        }
    });
</script>
</#if>
<#if security.hasEntityPermission("ORDERMGR", "_VIEW", session)>
    <#if parameters.ajaxUpdateEvent?default("N") != 'Y'>
        <@htmlScreenTemplate.renderScreenletBegin id="findOrders" title="销售订单查询"/>
    <#--<div class="panel-toolbar text-right">
        <button onclick="showHideQuery()" id="showHideQueryBtn" value="显示查询项" class="btn btn-primary btn-sm"/>
    </div>
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
        </#if>-->
    <form method="post" name="lookuporder" id="lookuporder" action="<@ofbizUrl>searchorders</@ofbizUrl>" class="form-inline">
        <input type="hidden" name="lookupFlag" value="Y"/>
        <input type="hidden" name="hideFields" value="Y"/>
        <input type="hidden" name="viewSize" value="${viewSize}"/>
        <input type="hidden" name="viewIndex" value="${viewIndex}"/>
        <input type="hidden" name="ajaxUpdateEvent" value="Y"/>

        <#if parameters.hideFields?default("N") != "Y">
            <div class="form-group">
                <div class="input-group m-b-5 m-r-5">
                    <span class="input-group-addon">${uiLabelMap.OrderOrderId}</span>
                <#--<input type='text' class="form-control" name='orderId'/>-->
                    <@htmlTemplate.lookupField value=''formName="lookuporder" name="orderId" id="orderId" fieldFormName="LookupOrderHeader"/>
                </div>
            <#--<div class="input-group m-b-5 m-r-5">
                <span class="input-group-addon">${uiLabelMap.OrderExternalId}</span>
                <input type='text' class="form-control" name='externalId'/>
            </div>-->
            <#--  <div class="input-group m-b-5 m-r-5">
                  <span class="input-group-addon">${uiLabelMap.OrderCustomerPo}</span>
                  <input type='text' class="form-control" name='correspondingPoId' value='${requestParameters.correspondingPoId?if_exists}'/>
              </div>-->
            <#--  <div class="input-group m-b-5 m-r-5">
                  <span class="input-group-addon">${uiLabelMap.OrderInternalCode}</span>
                  <input type='text' class="form-control" name='internalCode' value='${requestParameters.internalCode?if_exists}'/>
              </div>-->
                <div class="input-group m-b-5 m-r-5">
                    <span class="input-group-addon">${uiLabelMap.ProductProductId}</span>
                <#--<input type='text' class="form-control" name='productId' value='${requestParameters.productId?if_exists}'/>-->
                    <@htmlTemplate.lookupField value='${requestParameters.productId?if_exists}' formName="lookuporder" name="productId" id="productId" fieldFormName="LookupProduct"/>
                </div>
            <#--<div class="input-group m-b-5 m-r-5">
                <span class="input-group-addon">${uiLabelMap.ProductInventoryItemId}</span>

                <input type='text' class="form-control" name='inventoryItemId' value='${requestParameters.inventoryItemId?if_exists}'/>
            </div>-->
            <#-- <div class="input-group m-b-5 m-r-5">
                 <span class="input-group-addon">${uiLabelMap.ProductSerialNumber}</span>
                 <input type='text' class="form-control" name='serialNumber' value='${requestParameters.serialNumber?if_exists}'/>
             </div>
             <div class="input-group m-b-5 m-r-5">
                 <span class="input-group-addon">${uiLabelMap.ProductSoftIdentifier}</span>
                 <input type='text' class="form-control" name='softIdentifier' value='${requestParameters.softIdentifier?if_exists}'/>
             </div>-->
            <#--<div class="input-group m-b-5 m-r-5">
                <span class="input-group-addon">${uiLabelMap.PartyRoleType}</span>


                <select name='roleTypeId' id='roleTypeId' multiple="multiple" class="form-control">
                    <#if currentRole?has_content>
                        <option value="${currentRole.roleTypeId}">${currentRole.get("description", locale)}</option>
                        <option value="${currentRole.roleTypeId}">---</option>
                    </#if>
                    <option value="">${uiLabelMap.CommonAnyRoleType}</option>
                    <#list roleTypes as roleType>
                        <option value="${roleType.roleTypeId}">${roleType.get("description", locale)}</option>
                    </#list>
                </select>

            </div>-->
                <div class="input-group m-b-5 m-r-5">
                    <span class="input-group-addon">${uiLabelMap.PartyPartyId}</span>


                    <@htmlTemplate.lookupField value='${requestParameters.partyId?if_exists}' formName="lookuporder" name="partyId" id="partyId" fieldFormName="LookupPartyName"/>

                </div>
            <#--<div class="input-group m-b-5 m-r-5">
                <span class="input-group-addon">${uiLabelMap.CommonUserLoginId}</span>

                <input type='text' class="form-control" name='userLoginId' value='${requestParameters.userLoginId?if_exists}'/>
            </div>-->
                <input type="hidden" name="orderTypeId" value="SALES_ORDER"/>
            <#--<div class="input-group m-b-5 m-r-5">
                <span class="input-group-addon">${uiLabelMap.OrderOrderType}</span>


                <select name='orderTypeId' class="form-control">
                    <#if currentType?has_content>
                        <option value="${currentType.orderTypeId}">${currentType.get("description", locale)}</option>
                        <option value="${currentType.orderTypeId}">---</option>
                    </#if>
                    <option value="">${uiLabelMap.OrderAnyOrderType}</option>
                    <#list orderTypes as orderType>
                        <option value="${orderType.orderTypeId}">${orderType.get("description", locale)}</option>
                    </#list>
                </select>

            </div>-->
            <#--<div class="input-group m-b-5 m-r-5">
                <span class="input-group-addon">${uiLabelMap.AccountingBillingAccount}</span>

                <input type='text' class="form-control" name='billingAccountId' value='${requestParameters.billingAccountId?if_exists}'/>
            </div>
            <div class="input-group m-b-5 m-r-5">
                <span class="input-group-addon">${uiLabelMap.CommonCreatedBy}</span>

                <input type='text' class="form-control" name='createdBy' value='${requestParameters.createdBy?if_exists}'/>
            </div>-->
            <#--<div class="input-group m-b-5 m-r-5">
                <span class="input-group-addon">${uiLabelMap.OrderSalesChannel}</span>


                <select name='salesChannelEnumId' class="form-control">
                    <#if currentSalesChannel?has_content>
                        <option value="${currentSalesChannel.enumId}">${currentSalesChannel.get("description", locale)}</option>
                        <option value="${currentSalesChannel.enumId}">---</option>
                    </#if>
                    <option value="">${uiLabelMap.CommonAnySalesChannel}</option>
                    <#list salesChannels as channel>
                        <option value="${channel.enumId}">${channel.get("description", locale)}</option>
                    </#list>
                </select>

            </div>-->
                <div class="input-group m-b-5 m-r-5">
                    <span class="input-group-addon">${uiLabelMap.ProductProductStore}</span>


                    <select name='productStoreId' class="form-control">
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
            <#--<div class="input-group m-b-5 m-r-5">
                <span class="input-group-addon">${uiLabelMap.ProductWebSite}</span>
                <select name='orderWebSiteId' class="form-control">
                    <#if currentWebSite?has_content>
                        <option value="${currentWebSite.webSiteId}">${currentWebSite.siteName}</option>
                        <option value="${currentWebSite.webSiteId}">---</option>
                    </#if>
                    <option value="">${uiLabelMap.CommonAnyWebSite}</option>
                    <#list webSites as webSite>
                        <option value="${webSite.webSiteId}">${webSite.siteName?if_exists}</option>
                    </#list>
                </select>

            </div>-->
                <div class="input-group m-b-5 m-r-5">
                    <span class="input-group-addon">${uiLabelMap.CommonStatus}</span>
                    <select name='orderStatusId' class="form-control">
                        <#if currentStatus?has_content>
                            <option value="${currentStatus.statusId}">${currentStatus.get("description", locale)}</option>
                            <option value="${currentStatus.statusId}">---</option>
                        </#if>
                        <option value="">${uiLabelMap.OrderAnyOrderStatus}</option>
                        <#list orderStatuses as orderStatus>
                        <#if orderStatus.statusId!='ORDER_SENT' &&  orderStatus.statusId!='ORDER_PROCESSING' && orderStatus.statusId!='ORDER_REJECTED'>
                            <option value="${orderStatus.statusId}">${orderStatus.get("description")}</option>
                        </#if>
                        </#list>
                    </select>

                </div>
            <#--<div class="input-group m-b-5 m-r-5">
                <span class="input-group-addon">${uiLabelMap.OrderContainsBackOrders}</span>
                <select name='hasBackOrders' class="form-control">
                    <#if requestParameters.hasBackOrders?has_content>
                        <option value="Y">${uiLabelMap.OrderBackOrders}</option>
                        <option value="Y">---</option>
                    </#if>
                    <option value="">${uiLabelMap.CommonShowAll}</option>
                    <option value="Y">${uiLabelMap.CommonOnly}</option>
                </select>
            </div>-->
                <div class="input-group m-b-5 m-r-5">
                    <span class="input-group-addon">${uiLabelMap.OrderSelectShippingMethod}</span>
                    <select name="shipmentMethod" class="form-control">
                        <#if currentCarrierShipmentMethod?has_content>
                            <#assign currentShipmentMethodType = currentCarrierShipmentMethod.getRelatedOne("ShipmentMethodType")>
                            <option value="${currentCarrierShipmentMethod.partyId}@${currentCarrierShipmentMethod.shipmentMethodTypeId}">${currentCarrierShipmentMethod.partyId?if_exists} ${currentShipmentMethodType.description?if_exists}</option>
                            <option value="${currentCarrierShipmentMethod.partyId}@${currentCarrierShipmentMethod.shipmentMethodTypeId}">---</option>
                        </#if>
                        <option value="">${uiLabelMap.OrderSelectShippingMethod}</option>
                        <#list carrierShipmentMethods as carrierShipmentMethod>
                            <#assign shipmentMethodType = carrierShipmentMethod.getRelatedOne("ShipmentMethodType")>
                            <option value="${carrierShipmentMethod.partyId}@${carrierShipmentMethod.shipmentMethodTypeId}">${carrierShipmentMethod.partyId?if_exists} ${shipmentMethodType.description?if_exists}</option>
                        </#list>
                    </select>
                </div>
            <#-- <div class="input-group m-b-5 m-r-5">
                 <span class="input-group-addon">${uiLabelMap.OrderViewed}</span>
                 <select name="isViewed" class="form-control">
                     <#if requestParameters.isViewed?has_content>
                         <#assign isViewed = requestParameters.isViewed>
                         <option value="${isViewed}"><#if "Y" == isViewed>${uiLabelMap.CommonYes}<#elseif "N" == isViewed>${uiLabelMap.CommonNo}</#if></option>
                     </#if>
                     <option value=""></option>
                     <option value="Y">${uiLabelMap.CommonYes}</option>
                     <option value="N">${uiLabelMap.CommonNo}</option>
                 </select>
             </div>-->
            <#--  <div class="input-group m-b-5 m-r-5">
                  <span class="input-group-addon">${uiLabelMap.OrderAddressVerification}</span>
                  <input type='text' class="form-control" name='gatewayAvsResult' value='${requestParameters.gatewayAvsResult?if_exists}'/>
              </div>-->
            <#-- <div class="input-group m-b-5 m-r-5">
                 <span class="input-group-addon">${uiLabelMap.OrderScore}</span>
                 <input type='text' class="form-control" name='gatewayScoreResult' value='${requestParameters.gatewayScoreResult?if_exists}'/>
             </div>-->
            <#--<div class="input-group m-b-5 m-r-5">
                <span class="input-group-addon">${uiLabelMap.CommonDateFilter}</span>
                <span class='input-group-addon'>${uiLabelMap.CommonFrom}</span>
                <@htmlTemplate.renderDateTimeField name="minDate" event="" action="" value="${requestParameters.minDate?if_exists}" className="" alert="" title="Format: yyyy-MM-dd HH:mm:ss.SSS" size="25" maxlength="30" id="minDate1" dateType="date" shortDateInput=false timeDropdownParamName="" defaultDateTimeString="" localizedIconTitle="" timeDropdown="" timeHourName="" classString="" hour1="" hour2="" timeMinutesName="" minutes="" isTwelveHour="" ampmName="" amSelected="" pmSelected="" compositeType="" formName=""/>
                <span class='input-group-addon'>${uiLabelMap.CommonThru}</span>
                <@htmlTemplate.renderDateTimeField name="maxDate" event="" action="" value="${requestParameters.maxDate?if_exists}" className="" alert="" title="Format: yyyy-MM-dd HH:mm:ss.SSS" size="25" maxlength="30" id="maxDate1" dateType="date" shortDateInput=false timeDropdownParamName="" defaultDateTimeString="" localizedIconTitle="" timeDropdown="" timeHourName="" classString="" hour1="" hour2="" timeMinutesName="" minutes="" isTwelveHour="" ampmName="" amSelected="" pmSelected="" compositeType="" formName=""/>
            </div>
            <div class="input-group m-b-5 m-r-5">
                <span class="input-group-addon">${uiLabelMap.OrderFilterOn} ${uiLabelMap.OrderFilterInventoryProblems}</span>

               <span class="input-group-addon"> <input type="checkbox" name="filterInventoryProblems" value="Y"
                                                       <#if requestParameters.filterInventoryProblems?default("N") == "Y">checked="checked"</#if> /></span>
            </div>
            <div class="input-group m-b-5 m-r-5">
                <span class="input-group-addon">${uiLabelMap.OrderFilterOn} ${uiLabelMap.OrderFilterPOs} ${uiLabelMap.OrderFilterPartiallyReceivedPOs}</span>
                <span class="input-group-addon"><input type="checkbox" name="filterPartiallyReceivedPOs" value="Y"
                                                       <#if requestParameters.filterPartiallyReceivedPOs?default("N") == "Y">checked="checked"</#if> /></span>
            </div>
            <div class="input-group m-b-5 m-r-5">
                <span class="input-group-addon">${uiLabelMap.OrderFilterOn} ${uiLabelMap.OrderFilterPOs} ${uiLabelMap.OrderFilterPOsOpenPastTheirETA}</span>
                 <span class="input-group-addon"><input type="checkbox" name="filterPOsOpenPastTheirETA" value="Y"
                                                        <#if requestParameters.filterPOsOpenPastTheirETA?default("N") == "Y">checked="checked"</#if> /></span>
            </div>
            <div class="input-group m-b-5 m-r-5">
                <span class="input-group-addon">${uiLabelMap.OrderFilterOn} ${uiLabelMap.OrderFilterPOs} ${uiLabelMap.OrderFilterPOsWithRejectedItems}</span>
                <span class="input-group-addon"><input type="checkbox" name="filterPOsWithRejectedItems" value="Y"
                                                       <#if requestParameters.filterPOsWithRejectedItems?default("N") == "Y">checked="checked"</#if> /></span>
            </div>-->
            <#--<div class="input-group m-b-5 m-r-5">
                <span class="input-group-addon">${uiLabelMap.OrderShipToCountry}</span>
                 <span class="input-group-addon"><select name="countryGeoId" class="form-control">
                     <#if requestParameters.countryGeoId?has_content>
                         <#assign countryGeoId = requestParameters.countryGeoId>
                         <#assign geo = delegator.findOne("Geo", Static["org.ofbiz.base.util.UtilMisc"].toMap("geoId", countryGeoId), true)>
                         <option value="${countryGeoId}">${geo.geoName?if_exists}</option>
                         <option value="${countryGeoId}">---</option>
                     <#else>
                         <option value="">---</option>
                     </#if>
                 ${screens.render("component://common/widget/CommonScreens.xml#countries")}
                 </select> </span>
                <span class="input-group-addon"><select name="includeCountry" class="form-control">
                    <#if requestParameters.includeCountry?has_content>
                        <#assign includeCountry = requestParameters.includeCountry>
                        <option value="${includeCountry}"><#if "Y" == includeCountry>${uiLabelMap.OrderOnlyInclude}<#elseif "N" == includeCountry>${uiLabelMap.OrderDoNotInclude}</#if></option>
                        <option value="${includeCountry}">---</option>
                    </#if>
                    <option value="Y">${uiLabelMap.OrderOnlyInclude}</option>
                    <option value="N">${uiLabelMap.OrderDoNotInclude}</option>
                </select></span>

            </div>-->
                <div class="input-group m-b-5 m-r-5">
                    <span class="input-group-addon">${uiLabelMap.AccountingPaymentStatus}</span>
                    <select name="paymentStatusId" class="form-control">
                        <option value="">${uiLabelMap.CommonAll}</option>
                        <#list paymentStatusList as paymentStatus>
                            <#if paymentStatus.statusId == 'PAYMENT_NOT_RECEIVED' || paymentStatus.statusId == 'PAYMENT_RECEIVED'>
                            <option value="${paymentStatus.statusId}">${paymentStatus.get("description", locale)}</option>
                            </#if>
                        </#list>
                    </select>
                </div>
                <input type="hidden" name="showAll" value="Y"/>
                <input type='button' value='${uiLabelMap.CommonFind}' class="btn btn-primary btn-sm" onclick="lookupOrders()"/>
            </div>
        </#if>
    <#--<input type="image" src="<@ofbizContentUrl>/images/spacer.gif</@ofbizContentUrl>" onclick="javascript:lookupOrders(true);"/>-->
    </form>
        <@htmlScreenTemplate.renderScreenEnd/>
        <#if requestParameters.hideFields?default("N") != "Y">
        <script language="JavaScript" type="text/javascript">
            <!--//
            document.lookuporder.orderId.focus();
            //-->
        </script>
        </#if>


    <#-- <ul>
         <#if (orderList?has_content && 0 < orderList?size)>
             <#if (orderListSize > highIndex)>
                 <li><a href="javascript:paginateOrderList('${viewSize}', '${viewIndex+1}', '${requestParameters.hideFields?default("N")}')">${uiLabelMap.CommonNext}</a></li>
             <#else>
                 <li><span class="disabled">${uiLabelMap.CommonNext}</span></li>
             </#if>
             <#if (orderListSize > 0)>
                 <li><span>${lowIndex} - ${highIndex} ${uiLabelMap.CommonOf} ${orderListSize}</span></li>
             </#if>
             <#if (viewIndex > 1)>
                 <li><a href="javascript:paginateOrderList('${viewSize}', '${viewIndex-1}', '${requestParameters.hideFields?default("N")}')">${uiLabelMap.CommonPrevious}</a></li>
             <#else>
                 <li><span class="disabled">${uiLabelMap.CommonPrevious}</span></li>
             </#if>
         </#if>
     </ul>
 -->

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
    <br/>
    <div class="row">
        <form name="massOrderChangeForm" method="post" action="javascript:void(0);" class="form-inline">
            <div class="form-group pull-left">
                <input type="hidden" name="screenLocation" value="component://order/widget/ordermgr/OrderPrintScreens.xml#OrderPDF"/>
                <div class="input-group">
                    <div class="input-group-addon">
                        <select name="serviceName" onchange="setServiceName(this);" class="form-control">
                            <option value="javascript:void(0);">&nbsp;</option>
                            <option value="<@ofbizUrl>massApproveOrders?hideFields=${requestParameters.hideFields?default("N")}${paramList}</@ofbizUrl>">${uiLabelMap.OrderApproveOrder}</option>
                            <option value="<@ofbizUrl>massHoldOrders?hideFields=${requestParameters.hideFields?default("N")}${paramList}</@ofbizUrl>">${uiLabelMap.OrderHold}</option>
                            <#--<option value="<@ofbizUrl>massProcessOrders?hideFields=${requestParameters.hideFields?default("N")}${paramList}</@ofbizUrl>">${uiLabelMap.OrderProcessOrder}</option>-->
                            <option value="<@ofbizUrl>massCancelOrders?hideFields=${requestParameters.hideFields?default("N")}${paramList}</@ofbizUrl>">${uiLabelMap.OrderCancelOrder}</option>
                            <#--<option value="<@ofbizUrl>massCancelRemainingPurchaseOrderItems?hideFields=${requestParameters.hideFields?default("N")}${paramList}</@ofbizUrl>">${uiLabelMap.OrderCancelRemainingPOItems}</option>-->
                            <#--<option value="<@ofbizUrl>massRejectOrders?hideFields=${requestParameters.hideFields?default("N")}${paramList}</@ofbizUrl>">${uiLabelMap.OrderRejectOrder}</option>-->
                            <option value="<@ofbizUrl>massPickOrders?hideFields=${requestParameters.hideFields?default("N")}${paramList}</@ofbizUrl>">${uiLabelMap.OrderPickOrders}</option>
                            <option value="<@ofbizUrl>massQuickShipOrders?hideFields=${requestParameters.hideFields?default("N")}${paramList}</@ofbizUrl>">${uiLabelMap.OrderQuickShipEntireOrder}</option>
                            <option value="<@ofbizUrl>massPrintOrders?hideFields=${requestParameters.hideFields?default('N')}${paramList}</@ofbizUrl>">${uiLabelMap.CommonPrint}</option>
                            <option value="<@ofbizUrl>massCreateFileForOrders?hideFields=${requestParameters.hideFields?default('N')}${paramList}</@ofbizUrl>">生成订单PDF</option>
                        </select>
                        <select name="printerName" class="form-control">
                            <option value="javascript:void(0);">&nbsp;</option>
                                <#list printers as printer>
                                <option value="${printer}">${printer}</option>
                            </#list>
                        </select>
                    </div>
                    <div class="input-group-addon">
                        <a href="javascript:runAction();" id="runOrderAction" class="btn btn-primary btn-sm">${uiLabelMap.OrderRunAction}</a>
                    </div>
                </div>
            </div>
            <input type="hidden" name="orderIdList" value=""/>
        </form>
    </div>
    <hr/>
    </#if>

<div id="search-results">
    <div class="table-responsive">
        <table class="table table-bordered table-striped">
            <tr class="header-row">
                <th>
                    <input type="checkbox" id="checkAllOrders" name="checkAllOrders" value="1" onchange="toggleOrderId(this);"/>
                </th>
            <#--<th>${uiLabelMap.OrderOrderType}</th>-->
                <th>${uiLabelMap.OrderOrderId}</th>
                <th>买家信息</th>
            <#--<th>${uiLabelMap.OrderSurvey}</th>-->
                <th>订购商品</th>
                <th>订单总价</th>
            <#--<th>缺货数</th>-->
            <#--<th>退货数</th>-->
                <th>实付金额</th>
                <#if (requestParameters.filterInventoryProblems?default("N") == "Y") || (requestParameters.filterPOsOpenPastTheirETA?default("N") == "Y") || (requestParameters.filterPOsWithRejectedItems?default("N") == "Y") || (requestParameters.filterPartiallyReceivedPOs?default("N") == "Y")>
                    <th>${uiLabelMap.CommonStatus}</th>
                    <th>${uiLabelMap.CommonFilter}</th>
                <#else>
                    <th>订单状态</th>
                </#if>
                <th>下单时间</th>
            <#--<th>${uiLabelMap.PartyPartyId}</th>-->
                <th>详细</th>
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
                            <input type="checkbox" name="orderIdList1" value="${orderHeader.orderId}" onchange="toggleOrderIdList();"/>
                        </td>
                    <#--<td>${orderType.get("description",locale)?default(orderType.orderTypeId?default(""))}</td>-->
                        <td><a href="<@ofbizUrl>orderview?orderId=${orderHeader.orderId}</@ofbizUrl>" class='buttontext'>${orderHeader.orderId}</a></td>
                        <td>
                            <div>

                                <#if displayParty?has_content>
                                    <#assign displayPartyNameResult = dispatcher.runSync("getPartyNameForDate", Static["org.ofbiz.base.util.UtilMisc"].toMap("partyId", displayParty.partyId, "compareDate", orderHeader.orderDate, "userLogin", userLogin))/>

                                    <a href="${customerDetailLink}${partyId}"
                                       class="">姓名:${displayPartyNameResult.fullName?default("[${uiLabelMap.OrderPartyNameNotFound}]")}</a>

                                <#else>
                                ${uiLabelMap.CommonNA}
                                </#if>
                            </div>
                            <#assign address = orh.getShippingAddress()?if_exists/>
                            <#if address?has_content>
                                <div>收件人: ${address.toName?default("")}</div>
                                <div>${address.mobilePhone?default("")}</div>
                            </#if>
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
                    <#--<td>${orh.hasSurvey()?string.number}</td>-->
                        <td width="220px">
                        <#--${orh.getTotalOrderItemsQuantity()?string.number}-->
                        <#--<div class="l1">
                            <ul>
                                <li><img src="https://changsy.cn//images/www_changsy_cn/products/10002/medium.jpg"><i class="icon_point"> 3 </i></li>
                                <li><img src="https://changsy.cn//images/www_changsy_cn/products/10003/medium.jpg"><i class="icon_point"> 5 </i></li>
                                <li><img src="https://changsy.cn//images/www_changsy_cn/products/10002/medium.jpg"></li>
                            </ul>
                            <div><span class="l3">共9件</span><span class="l4">0.1kg</span> <i class="dy f0"></i></div>
                        </div>-->
                            <#assign  orderItems = orh.getOrderItems()>
                            <#if orderItems?has_content>
                                <div class="l1">
                                    <ul>
                                        <#list orderItems as orderItem>
                                            <#assign miniProduct = orderItem.getRelatedOne("Product")>
                                            <#assign mediumImageUrl = Static["org.ofbiz.product.product.ProductContentWrapper"].getProductContentAsText(miniProduct,"MEDIUM_IMAGE_URL",locale,dispatcher)>
                                            <#assign quantity = orderItem.getBigDecimal("quantity")>
                                            <li><a href="/catalog/control/EditProduct?productId=${miniProduct.get("productId")}"
                                                   title="产品标识:${miniProduct.get("productId")}，产品名称：${miniProduct.get("productName")}"><img src="${mediumImageUrl}"><i
                                                    class="icon_point">${quantity?string.number}</i></a></li>
                                        </#list>
                                    </ul>
                                    <div><span class="l3">共${orh.getTotalOrderItemsQuantity()?string.number}件</span><span class="l4">0.1kg</span><i class="dy f0"></i></div>
                                </div>
                            </#if>

                        </td>
                    <#--<td>${orh.getOrderBackorderQuantity()?string.number}</td>-->
                    <#--<td>${orh.getOrderReturnedQuantity()?string.number}</td>-->
                        <td><@ofbizCurrency amount=orh.getOrderItemsSubTotal() isoCode=orh.getCurrency()/></td>
                        <td><@ofbizCurrency amount=orh.getOrderGrandTotal() isoCode=orh.getCurrency()/></td>
                        <td>
                            <#if statusItem.statusId?default("N/A") == "ORDER_CREATED">
                                <#assign itemStatusStyle ="label-danger"/>
                            <#elseif statusItem.statusId?default("N/A") == "ORDER_PICKED">
                                <#assign itemStatusStyle ="label-default"/>
                            <#elseif statusItem.statusId?default("N/A") == "ORDER_PACKED">
                                <#assign itemStatusStyle ="label-default"/>
                            <#elseif statusItem.statusId?default("N/A") == "ORDER_APPROVED">
                                <#assign itemStatusStyle ="label-primary"/>
                            <#elseif statusItem.statusId?default("N/A") == "ORDER_SHIPPED">
                                <#assign itemStatusStyle ="label-info"/>
                            <#elseif statusItem.statusId?default("N/A") == "ORDER_COMPLETED">
                                <#assign itemStatusStyle ="label-success"/>
                            <#elseif statusItem.statusId?default("N/A") == "ORDER_REJECTED">
                                <#assign itemStatusStyle ="label-warning"/>
                            <#elseif statusItem.statusId?default("N/A") == "ORDER_CANCELLED">
                                <#assign itemStatusStyle ="label-warning"/>
                            <#else>
                                <#assign itemStatusStyle ="label-default"/>
                            </#if>
                            <div class="label ${itemStatusStyle}">${statusItem.get("description")?default(statusItem.statusId?default("N/A"))}</div>
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
                        <td>${orderHeader.orderDate?string('yyyy-MM-dd hh:mm')}</td>
                    <#--<td>
                        <#if partyId != "_NA_">
                            <a href="${customerDetailLink}${partyId}" class="buttontext">${partyId}</a>
                        <#else>
                        ${uiLabelMap.CommonNA}
                        </#if>
                    </td>-->
                        <td>
                            <a href="<@ofbizUrl>orderview?orderId=${orderHeader.orderId}</@ofbizUrl>" class='btn btn-white' title="订单处理"><i class="fa fa-cogs"></i></a>
                        </td>
                    </tr>
                <#-- toggle the row color -->
                    <#assign alt_row = !alt_row>
                </#list>

            <#else>
                <tr>
                    <td colspan='13'><h3>${uiLabelMap.OrderNoOrderFound}</h3></td>
                </tr>
            </#if>
            <#if lookupErrorMessage?exists>
                <tr>
                    <td colspan='13'><h3>${lookupErrorMessage}</h3></td>
                </tr>
            </#if>
        </table>
    </div>
<#--${context}-->
    <#if orderList?has_content>

        <#include "component://common/webcommon/includes/htmlTemplate.ftl"/>
        <#assign viewIndexFirst = 0/>
        <#assign viewIndexPrevious = viewIndex - 1/>
        <#assign viewIndexNext = viewIndex + 1/>
        <#assign commonUrl = "searchsaleorders"/>
        <#assign viewIndexLast = Static["org.ofbiz.base.util.UtilMisc"].getViewLastIndex(orderListSize, viewSize) />
        <#assign messageMap = Static["org.ofbiz.base.util.UtilMisc"].toMap("lowCount", lowIndex, "highCount", highIndex, "total", orderListSize)/>
        <#assign commonDisplaying = Static["org.ofbiz.base.util.UtilProperties"].getMessage("CommonUiLabels", "CommonDisplaying", messageMap, locale)/>
        <@nextPrev1 commonUrl=commonUrl ajaxEnabled=true paramUrl=paramList viewIndex=viewIndex highIndex=highIndex listSize=orderListSize viewSize=viewSize commonDisplaying=commonDisplaying />
    </#if>
</div>
<#else>
<div class="row">${uiLabelMap.OrderViewPermissionError}</div>
</#if>
