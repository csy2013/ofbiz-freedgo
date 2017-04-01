<#if facility?exists && facilityId?has_content>
<form action="<@ofbizUrl>UpdateFacility</@ofbizUrl>" name="EditFacilityForm" method="post" class="form-horizontal" data-parsley-validate="true">
    <input type="hidden" name="facilityId" value="${facilityId?if_exists}"/>
    <div class="form-group">
        <label class="col-md-3 control-label">${uiLabelMap.ProductFacilityId}</label>
        <div class="col-md-5">
        ${facilityId?if_exists} <span class="tooltip">${uiLabelMap.ProductNotModificationRecrationFacility}</span>
        </div>
    </div>
<#else>
<form action="<@ofbizUrl>CreateFacility</@ofbizUrl>" name="EditFacilityForm" method="post" class="form-horizontal" data-parsley-validate="true">
    <#if facilityId?exists>
        <h3>${uiLabelMap.ProductCouldNotFindFacilityWithId} "${facilityId?if_exists}".</h3>
    </#if>
</#if>

    <div class="form-group">
        <label class="col-md-3 control-label">场所名称</label>
        <div class="col-md-5">
            <input type="text"  class="form-control" name="facilityName" value="${facility.facilityName?if_exists}" size="30" maxlength="60" data-parsley-required="true"/>
            <span class="tooltip">${uiLabelMap.CommonRequired}</span>
        </div>
    </div>
    <div class="form-group">
        <label class="col-md-3 control-label">${uiLabelMap.ProductFacilityTypeId}</label>
        <div class="col-md-5">
            <select name="facilityTypeId" class="form-control" data-parsley-required="true">
                <option selected="selected" value='${facilityType.facilityTypeId?if_exists}'>${facilityType.get("description",locale)?if_exists}</option>
            <#list facilityTypes as nextFacilityType>
                <#if nextFacilityType.facilityTypeId!= 'ROOM'&& nextFacilityType.facilityTypeId!= 'BUILDING'&& nextFacilityType.facilityTypeId!= 'CALL_CENTER'
                && nextFacilityType.facilityTypeId!= 'FLOOR'&& nextFacilityType.facilityTypeId!= 'OFFICE' >
                <option value='${nextFacilityType.facilityTypeId?if_exists}'>${nextFacilityType.get("description",locale)?if_exists}</option>
                </#if>
            </#list>
            </select>
        </div>
    </div>
   <#-- <div class="form-group">
        <label class="col-md-3 control-label">${uiLabelMap.FormFieldTitle_parentFacilityId}</label>
        <div class="col-md-5">
        <@htmlTemplate.lookupField value="${facility.parentFacilityId?if_exists}" formName="EditFacilityForm" name="parentFacilityId" id="parentFacilityId" fieldFormName="LookupFacility"/>
        </div>
    </div>-->
    <div class="form-group">
        <label class="col-md-3 control-label">${uiLabelMap.ProductFacilityOwner}</label>
        <div class="col-md-5">
        <@htmlTemplate.lookupField value="${facility.ownerPartyId?if_exists}" formName="EditFacilityForm" name="ownerPartyId" id="ownerPartyId" fieldFormName="LookupPartyName" required="true"/>
            <span class="tooltip">${uiLabelMap.CommonRequired}</span>
        </div>
    </div>

    <div class="form-group">
        <label class="col-md-3 control-label">场所描述</label>
        <div class="col-md-5"><input type="text"  class="form-control" name="description" value="${facility.description?if_exists}" size="60" maxlength="250"/></div>
    </div>
    <div class="form-group">
        <label class="col-md-3 control-label">${uiLabelMap.ProductFacilityDefaultWeightUnit}</label>
        <div class="col-md-5">
            <select name="defaultWeightUomId"  class="form-control">
                <option value=''>${uiLabelMap.CommonNone}</option>
            <#list weightUomList as uom>
                <option value='${uom.uomId}'
                    <#if (facility.defaultWeightUomId?has_content) && (uom.uomId == facility.defaultWeightUomId)>
                        selected="selected"
                    </#if>
                        >${uom.get("description",locale)?default(uom.uomId)}</option>
            </#list>
            </select>
        </div>
    </div>
    <div class="form-group">
        <label class="col-md-3 control-label">${uiLabelMap.ProductFacilityDefaultInventoryItemType}</label>
        <div class="col-md-5">
            <select name="defaultInventoryItemTypeId"  class="form-control">
            <#list inventoryItemTypes as nextInventoryItemType>
                <option value='${nextInventoryItemType.inventoryItemTypeId}'
                    <#if (facility.defaultInventoryItemTypeId?has_content) && (nextInventoryItemType.inventoryItemTypeId == facility.defaultInventoryItemTypeId)>
                        selected="selected"
                    </#if>
                        >${nextInventoryItemType.get("description",locale)?default(nextInventoryItemType.inventoryItemTypeId)}</option>
            </#list>
            </select>
        </div>
    </div>

    <div class="form-group">
        <label class="col-md-3 control-label">${uiLabelMap.ProductFacilitySize}</label>
        <div class="col-md-5"><input type="text"  class="form-control" name="facilitySize" value="${facility.facilitySize?if_exists}" size="10" maxlength="20"/></div>
    </div>
    <div class="form-group">
        <label class="col-md-3 control-label">${uiLabelMap.ProductFacilityDefaultAreaUnit}</label>
        <div class="col-md-5">
            <select name="facilitySizeUomId"  class="form-control">
                <option value=''>${uiLabelMap.CommonNone}</option>
            <#list areaUomList as uom>
                <option value='${uom.uomId}'
                    <#if (facility.facilitySizeUomId?has_content) && (uom.uomId == facility.facilitySizeUomId)>
                        selected="selected"
                    </#if>
                        >${uom.get("description",locale)?default(uom.uomId)}</option>
            </#list>
            </select>
        </div>
    </div>
    <div class="form-group">
        <label class="col-md-3 control-label">${uiLabelMap.ProductDefaultDaysToShip}</label>
        <div class="col-md-5"><input type="text"  class="form-control" name="defaultDaysToShip" value="${facility.defaultDaysToShip?if_exists}" size="10" maxlength="20"/></div>
    </div>
    <div class="form-group">
        <div class="col-md-5">&nbsp;</div>
    <#if facilityId?has_content>
        <div class="col-md-5 pull-right"><input type="submit" name="Update" value="${uiLabelMap.CommonUpdate}" class="btn btn-primary btn-sm"/></div>
    <#else>
        <div class="col-md-5 pull-right"><input type="submit" name="Update" value="${uiLabelMap.CommonSave}" class="btn btn-primary btn-sm"/></div>
    </#if>
    </div> 
</form>
