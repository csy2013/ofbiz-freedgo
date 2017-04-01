<form name="addscarr" id="addscarr"  method="post" action="<@ofbizUrl>prepareCreateShipMeth</@ofbizUrl>" class="form-inline">
    <input type="hidden" name="newShipMethod" value="Y"/>
    <input type="hidden" name="productStoreId" value="${productStoreId?if_exists}"/>
    <div class="form-group">
        <div class="input-group">
            <div class="input-group-addon"> <span>${uiLabelMap.ProductCarrierShipmentMethod}</span></div>
            <select name="carrierShipmentString" class="form-control" id="carrierShipmentString">

              <#list carrierShipmentMethods as shipmentMethod>
                <option value="${shipmentMethod.partyId}|${shipmentMethod.roleTypeId}|${shipmentMethod.shipmentMethodTypeId}">${shipmentMethod.shipmentMethodTypeId} (${shipmentMethod.partyId}/${shipmentMethod.roleTypeId})</option>
              </#list>
            </select>
            </div>

        <#--<input type="submit" class="btn btn-primary btn-sm" value="${uiLabelMap.CommonAdd}"/>-->
    <@htmlScreenTemplate.renderModalPage id="prepareCreateShipMeth" name="EditProductStoreShipSetup" modalTitle="为店铺增加送货方式"  modalUrl="prepareCreateShipMeth" description="为店铺增加送货方式"
    targetParameterIter="newShipMethod:'Y',productStoreId:'${productStoreId}',carrierShipmentString:$('#carrierShipmentString').val()"/>
    </div>
</form>


