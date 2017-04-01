<#if facilityId?exists && !(facilityLocation?exists)>
<form action="<@ofbizUrl>CreateFacilityLocation</@ofbizUrl>" method="post" class="form-horizontal">
    <input type="hidden" name="facilityId" value="${facilityId}"/>
<#elseif facilityLocation?exists>
<form action="<@ofbizUrl>UpdateFacilityLocation</@ofbizUrl>" method="post" class="form-horizontal">
    <input type="hidden" name="facilityId" value="${facilityId?if_exists}"/>
    <input type="hidden" name="locationSeqId" value="${locationSeqId}"/>
    <div class="form-group">
        <lable class="col-md-3 control-label">${uiLabelMap.ProductFacilityId}</lable>
        <div class="col-md-5">${facilityId?if_exists}</div>
    </div>
    <div class="form-group">
        <lable class="col-md-3 control-label">${uiLabelMap.ProductLocationSeqId}</lable>
        <div class="col-md-5">${locationSeqId}</div>
    </div>
<#else>
    <h1>${uiLabelMap.ProductNotCreateLocationFacilityId}</h1>
</#if>

<#if facilityId?exists>
    <div class="form-group">
        <lable class="col-md-3 control-label">${uiLabelMap.ProductType}</lable>
        <div class="col-md-5">
            <select name="locationTypeEnumId" class="form-control">
                <#if (facilityLocation.locationTypeEnumId)?has_content>
                    <#assign locationTypeEnum = facilityLocation.getRelatedOneCache("TypeEnumeration")?if_exists>
                    <option value="${facilityLocation.locationTypeEnumId}">${(locationTypeEnum.get("description",locale))?default(facilityLocation.locationTypeEnumId)}</option>
                    <option value="${facilityLocation.locationTypeEnumId}">----</option>
                </#if>
                <#list locationTypeEnums as locationTypeEnum>
                    <option value="${locationTypeEnum.enumId}">${locationTypeEnum.get("description",locale)}</option>
                </#list>
            </select>
        </div>
    </div>
    <div class="form-group">
        <lable class="col-md-3 control-label">${uiLabelMap.CommonArea}</lable>
        <div class="col-md-5"><input type="text" name="areaId" value="${(facilityLocation.areaId)?if_exists}" size="19" maxlength="20" class="form-control"/></div>
    </div>
    <div class="form-group">
        <lable class="col-md-3 control-label">${uiLabelMap.ProductAisle}</lable>
        <div class="col-md-5"><input type="text" name="aisleId" value="${(facilityLocation.aisleId)?if_exists}" size="19" maxlength="20" class="form-control"/></div>
    </div>
    <div class="form-group">
        <lable class="col-md-3 control-label">${uiLabelMap.ProductSection}</lable>
        <div class="col-md-5"><input type="text" name="sectionId" value="${(facilityLocation.sectionId)?if_exists}" size="19" maxlength="20" class="form-control"/></div>
    </div>
    <div class="form-group">
        <lable class="col-md-3 control-label">${uiLabelMap.ProductLevel}</lable>
        <div class="col-md-5"><input type="text" name="levelId" value="${(facilityLocation.levelId)?if_exists}" size="19" maxlength="20" class="form-control"/></div>
    </div>
    <div class="form-group">
        <lable class="col-md-3 control-label">${uiLabelMap.ProductPosition}</lable>
        <div class="col-md-5"><input type="text" name="positionId" value="${(facilityLocation.positionId)?if_exists}" size="19" maxlength="20" class="form-control"/></div>
    </div>
    <div class="form-group">
        <div class="col-md-5">&nbsp;</div>
        <#if locationSeqId?exists>
            <div class="col-md-5"><input type="submit" value="${uiLabelMap.CommonUpdate}"/></div>
        <#else>
            <div class="col-md-5"><input type="submit" value="${uiLabelMap.CommonSave}"/></div>
        </#if>
    </div>
</form>
</#if>

