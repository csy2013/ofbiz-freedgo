<@htmlScreenTemplate.renderScreenletBegin id="" title="${uiLabelMap.PageTitleEditProductFeatures}"/>

<#if illegalInventoryItem?exists>
<div class="errorMessage" xmlns="http://www.w3.org/1999/html">${illegalInventoryItem}</div>
</#if>
<#-- <div class="button-bar">
   &lt;#&ndash;<a href="<@ofbizUrl>EditFacility</@ofbizUrl>" class="buttontext">${uiLabelMap.ProductNewFacility}</a>&ndash;&gt;
   <a href="<@ofbizUrl>PickMoveStockSimple?facilityId=${facilityId?if_exists}</@ofbizUrl>" class="btn btn-primary btn-sm">${uiLabelMap.CommonPrint}</a>
 </div>-->
<#if !(inventoryItem?exists)>
<form method="post" action="<@ofbizUrl>TransferInventoryItem</@ofbizUrl>" class="form-inline" id="TransferInventoryItem" name="TransferInventoryItem">
    <input type="hidden" name="facilityId" value="${facilityId}"/>

    <div class="form-group">
        <div class="input-group">
            <div class="input-group-addon">
                <label class="col-md-3 control-label">${uiLabelMap.ProductInventoryItemId}</label>
            </div>
        <#--<@htmlTemplate.lookupField formName="TransferInventoryItem" name="inventoryItemId" id="inventoryItemId" fieldFormName="LookupInventoryItem"/>-->
            <input type="text" name="inventoryItemId" size="20" maxlength="20" class="form-control"/>
        </div>

        <input type="submit" value="${uiLabelMap.ProductGetItem}" class="btn btn-primary btn-sm"/>
    </div>
</form>
<#else>
    <#if !(inventoryTransfer?exists)>
    <form method="post" action="<@ofbizUrl>CreateInventoryTransfer</@ofbizUrl>" name="transferform" class="form-horizontal">
    <#else>
    <form method="post" action="<@ofbizUrl>UpdateInventoryTransfer</@ofbizUrl>" name="transferform" class="form-horizontal">
        <input type="hidden" name="inventoryTransferId" value="${inventoryTransferId?if_exists}"/>
    </#if>

    <script language="JavaScript" type="text/javascript">
        function setNow(field) {
            eval('document.transferform.' + field + '.value="${nowTimestamp}"');
        }
    </script>


    <input type="hidden" name="inventoryItemId" value="${inventoryItemId?if_exists}"/>
    <input type="hidden" name="facilityId" value="${facilityId?if_exists}"/>
    <input type="hidden" name="locationSeqId" value="${(inventoryItem.locationSeqId)?if_exists}"/>
    <div class="form-group">
        <label class="col-md-3 control-label">${uiLabelMap.ProductInventoryItemId}</span></label>

        <div class="col-md-5">${inventoryItemId}</div>
    </div>
    <div class="form-group">

        <label class="col-md-3 control-label">${uiLabelMap.ProductInventoryItemTypeId}</label>

        <div class="col-md-5">
            <#if inventoryItemType?exists>
                    ${(inventoryItemType.get("description",locale))?if_exists}
                </#if>
        </div>
    </div>
    <div class="form-group">

        <label class="col-md-3 control-label">${uiLabelMap.ProductProductId}</span></label>

        <div class="col-md-5">
            <#if inventoryItem?exists && (inventoryItem.productId)?exists>
                <a href="/catalog/control/EditProduct?productId=${(inventoryItem.productId)?if_exists}" class="btn btn-primary btn-sm">${(inventoryItem.productId)?if_exists}</a>
            </#if>
        </div>
    </div>
    <div class="form-group">

        <label class="col-md-3 control-label">${uiLabelMap.CommonStatus}</label>

        <div class="col-md-5">${(inventoryStatus.get("description",locale))?default("--")}</div>
    </div>

    <div class="form-group">

        <label class="col-md-3 control-label">${uiLabelMap.ProductComments}</label>

        <div class="col-md-5">${(inventoryItem.comments)?default("--")}</div>
    </div>

    <div class="form-group">

        <label class="col-md-3 control-label">${uiLabelMap.ProductSerialAtpQoh}</label>

        <#if inventoryItem?exists && inventoryItem.inventoryItemTypeId.equals("NON_SERIAL_INV_ITEM")>
            <div class="col-md-5">
            ${(inventoryItem.availableToPromiseTotal)?if_exists}&nbsp;
                /&nbsp;${(inventoryItem.quantityOnHandTotal)?if_exists}
            </div>
        <#elseif inventoryItem?exists && inventoryItem.inventoryItemTypeId.equals("SERIALIZED_INV_ITEM")>
            <div class="col-md-5">${(inventoryItem.serialNumber)?if_exists}</div>
        <#elseif inventoryItem?exists>
            <div class="alert" width="74%">${uiLabelMap.ProductErrorType} ${(inventoryItem.inventoryItemTypeId)?if_exists} ${uiLabelMap.ProductUnknownSpecifyType}.</div>
        </#if>
    </div>

    <div class="form-group">

        <label class="col-md-3 control-label">${uiLabelMap.ProductTransferStatus}</label>

        <div class="col-md-5">
            <select name="statusId" class="form-control">
                <#if (inventoryTransfer.statusId)?exists>
                    <#assign curStatusItem = inventoryTransfer.getRelatedOneCache("StatusItem")>
                    <option value="${(inventoryTransfer.statusId)?if_exists}">${(curStatusItem.get("description",locale))?if_exists}</option>
                </#if>
                <#list statusItems as statusItem>
                    <option value="${(statusItem.statusId)?if_exists}">${(statusItem.get("description",locale))?if_exists}</option>
                </#list>
            </select>
        </div>
    </div>
    <div class="form-group">

        <label class="col-md-3 control-label">${uiLabelMap.ProductTransferSendDate}</label>

        <div class="col-md-5">
            <input type="text" name="sendDate" value="${(inventoryTransfer.sendDate)?if_exists}" size="22" class="form-control"/>
            <a href="#" onclick="setNow('sendDate')" class="btn btn-primary btn-sm">${uiLabelMap.CommonNow}</a>
        </div>
    </div>
    <#if !(inventoryTransfer?exists)>
        <div class="form-group">

            <label class="col-md-3 control-label">${uiLabelMap.ProductToFacilityContainer}</label>

            <div class="col-md-5">
                <div>
                    <select name="facilityIdTo" class="form-control">
                        <#list facilities as nextFacility>
                            <option value="${(nextFacility.facilityId)?if_exists}">${(nextFacility.facilityName)?if_exists} [${(nextFacility.facilityId)?if_exists}]</option>
                        </#list>
                    </select>
                    <span class="tooltip">${uiLabelMap.ProductSelectFacility}</span>
                    <br/>
                    <input type="text" name="containerIdTo" value="${(inventoryTransfer.containerIdTo)?if_exists}" size="20" maxlength="20" class="form-control"/>
                    <span class="tooltip">${uiLabelMap.ProductOrEnterContainerId}</span>
                </div>
            </div>
        </div>
        <div class="form-group">

            <label class="col-md-3 control-label">${uiLabelMap.ProductToLocation}</label>

            <div class="col-md-5">
                <@htmlTemplate.lookupField value="${(inventoryTransfer.locationSeqIdTo)?if_exists}" formName="transferform" name="locationSeqIdTo" id="locationSeqIdTo" fieldFormName="LookupFacilityLocation"/>
            </div>
        </div>
        <div class="form-group">

            <label class="col-md-3 control-label">${uiLabelMap.ProductComments}</label>

            <div class="col-md-5">
                <input type="text" name="comments" size="60" maxlength="250" class="form-control"/>
            </div>
        </div>
        <div class="form-group">

            <label class="col-md-3 control-label">${uiLabelMap.ProductQuantityToTransfer}</label>

            <div class="col-md-5">
                <#if inventoryItem?exists && inventoryItem.inventoryItemTypeId.equals("NON_SERIAL_INV_ITEM")>
                    <input type="text" size="5" name="xferQty" value="${(inventoryItem.availableToPromiseTotal)?if_exists}" class="form-control"/>
                <#elseif inventoryItem?exists && inventoryItem.inventoryItemTypeId.equals("SERIALIZED_INV_ITEM")>
                    <input type="hidden" name="xferQty" value="1"/>
                    1
                <#elseif inventoryItem?exists>
                    <span class="alert">${uiLabelMap.ProductErrorType} ${(inventoryItem.inventoryItemTypeId)?if_exists} ${uiLabelMap.ProductUnknownSpecifyType}.</span>
                </#if>
            </div>
        </div>
    <#else>
        <div class="form-group">

            <label class="col-md-3 control-label">${uiLabelMap.ProductTransferReceiveDate}</label>

            <div class="col-md-5">
                <input type="text" name="receiveDate" value="${(inventoryTransfer.receiveDate)?if_exists}" size="22" class="form-control"/>
                <a href="#" onclick="setNow('receiveDate')" class="btn btn-primary btn-sm">${uiLabelMap.CommonNow}</a>
            </div>
        </div>
        <div class="form-group">
            <label class="col-md-3 control-label">${uiLabelMap.ProductToFacilityContainer}</label>
            <#assign fac = delegator.findByPrimaryKey("Facility", Static["org.ofbiz.base.util.UtilMisc"].toMap("facilityId", inventoryTransfer.facilityIdTo))>
            <div class="col-md-5">${(fac.facilityName)?default("&nbsp;")}</div>
        </div>
        <div class="form-group">
            <label class="col-md-3 control-label">${uiLabelMap.ProductToLocation}</label>
            <div class="col-md-5">
                <@htmlTemplate.lookupField value="${(inventoryTransfer.locationSeqIdTo)?if_exists}" formName="transferform" name="locationSeqIdTo" id="locationSeqIdTo" fieldFormName="LookupFacilityLocation?facilityId=${inventoryTransfer.facilityIdTo}"/>
            </div>
        </div>
        <div class="form-group">
            <label class="col-md-3 control-label">${uiLabelMap.ProductComments}</label>
            <div class="col-md-5">
                <input type="text" name="comments" value="${(inventoryTransfer.comments)?if_exists}" size="60" maxlength="250" class="form-control"/>
            </div>
        </div>
    </#if>
    <div class="form-group">
        <div class="col-md-3">&nbsp;</div>
        <#if !(inventoryTransfer?exists)>
            <div class="col-md-5"><input type="submit" value="${uiLabelMap.ProductTransfer}" class="btn btn-primary btn-sm"/></div>
        <#else>
            <div class="col-md-5"><input type="submit" value="${uiLabelMap.CommonUpdate}" class="btn btn-primary btn-sm"/></div>
        </#if>
    </div>
</form>
</#if>
<@htmlScreenTemplate.renderScreenletEnd/>