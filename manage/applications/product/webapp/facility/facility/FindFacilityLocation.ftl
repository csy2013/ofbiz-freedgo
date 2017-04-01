<#if !requestParameters.ajaxUpdateEvent?exists>
<form action="<@ofbizUrl>FindFacilityLocation</@ofbizUrl>" method="get" name="findFacilityLocation" class="form-inline" id="findFacilityLocation">
    <div class="form-group">
    <#if (facilityId?exists)>
        <input type="hidden" name="facilityId" value="${facilityId}"/>
    </#if>
    <#if !(facilityId?exists)>
        <div class="input-group m-b-10 m-r-5">
            <div class="input-group-addon"><span>${uiLabelMap.ProductFacility}</span></div>
            <input type="text" value="" size="19" maxlength="20" class="form-control"/>
        </div>
    </#if>
        <div class="input-group m-b-10 m-r-5">
            <div class="input-group-addon"><span>${uiLabelMap.ProductLocationSeqId}</span></div>

        <#if parameters.facilityId?exists>
            <#assign LookupFacilityLocationView="LookupFacilityLocation?facilityId=${facilityId}">
        <#else>
            <#assign LookupFacilityLocationView="LookupFacilityLocation">
        </#if>
        <@htmlTemplate.lookupField formName="findFacilityLocation" name="locationSeqId" id="locationSeqId" fieldFormName="${LookupFacilityLocationView}"/>
        </div>

        <div class="input-group m-b-10 m-r-5">
            <div class="input-group-addon">
                <span>${uiLabelMap.CommonArea}
                </span>
            </div>
            <input type="text" name="areaId" value="" size="19" maxlength="20" class="form-control"/>
        </div>
        <div class="input-group m-b-10 m-r-5">
            <div class="input-group-addon"><span>${uiLabelMap.ProductAisle}</span></div>
            <input type="text" name="aisleId" value="" size="19" maxlength="20" class="form-control"/>
        </div>
        <div class="input-group m-b-10 m-r-5">
            <div class="input-group-addon"><span>${uiLabelMap.ProductSection}</span></div>
            <input type="text" name="sectionId" value="" size="19" maxlength="20" class="form-control"/>
        </div>
        <div class="input-group m-b-10 m-r-5">
            <div class="input-group-addon"><span>${uiLabelMap.ProductLevel}</span></div>
            <input type="text" name="levelId" value="" size="19" maxlength="20" class="form-control"/>
        </div>
        <div class="input-group m-b-10 m-r-5">
            <div class="input-group-addon"><span>${uiLabelMap.ProductPosition}</span></div>
            <input type="text" name="positionId" value="" size="19" maxlength="20" class="form-control"/>
        </div>
        <div class="input-group m-b-10 m-r-5">
            <div class="col-md-3">&nbsp;</div>
            <input type="button" name="findBtn" value="${uiLabelMap.CommonFind}" class="btn btn-primary btn-sm"
                   onclick="ajaxSubmitFormUpdateAreas('findFacilityLocation','ajax,search-results, findFacilityLocation,')"/>
        </div>
        <input type="hidden" name="ajaxUpdateEvent" value="Y">
        <input type="hidden" name="look_up" value="Y">
    </div>
</form>
<#-- TODO: Put this in a screenlet - make it look more like the party find screen -->
<hr/>
<div class="button-bar button-style-1">
<#--<a href="<@ofbizUrl>EditFacility</@ofbizUrl>" class="btn btn-primary btn-sm">${uiLabelMap.ProductNewFacility}</a>-->
        <@htmlScreenTemplate.renderModalPage id="EditFacilityLocation"  name="EditFacilityLocation"  modalUrl="/facility/control/EditFacilityLocation" modalMsg="" modalTitle="添加库存位置"
description="新建库存位置" targetParameterIter="facilityId:${facilityId?if_exists}">

</@htmlScreenTemplate.renderModalPage>
</div>
<div id="search-results">
</#if>


<#if foundLocations?exists>
<div class="table-responsive">
    <table class="table table-striped table-bordered">
        <tr class="header-row-2">
            <th>${uiLabelMap.ProductFacility}</th>
            <th>${uiLabelMap.ProductLocationSeqId}</th>
            <th>${uiLabelMap.ProductType}</th>
            <th>区域</th>
            <th>${uiLabelMap.ProductAisle}</th>
            <th>${uiLabelMap.ProductSection}</th>
            <th>${uiLabelMap.ProductLevel}</th>
            <th>${uiLabelMap.ProductPosition}</th>
            <th>&nbsp;</th>
        </tr>
        <#assign rowClass = "2">
        <#list foundLocations as location>
            <#assign locationTypeEnum = location.getRelatedOneCache("TypeEnumeration")?if_exists>
            <tr valign="middle"<#if rowClass == "1"> class="alternate-row"</#if>>
                <td><a href="<@ofbizUrl>EditFacility?facilityId=${(location.facilityId)?if_exists}</@ofbizUrl>">${(location.facilityId)?if_exists}</a></td>
                <td>
                    <#--<a href="<@ofbizUrl>EditFacilityLocation?facilityId=${facilityId}&locationSeqId=${(location.locationSeqId)?if_exists}</@ofbizUrl>">${(location.locationSeqId)?if_exists}</a>-->
                ${(location.locationSeqId)?if_exists}
                </td>
                <td>${(locationTypeEnum.get("description",locale))?default(location.locationTypeEnumId?if_exists)}</td>
                <td>${(location.areaId)?if_exists}</td>
                <td>${(location.aisleId)?if_exists}</td>
                <td>${(location.sectionId)?if_exists}</td>
                <td>${(location.levelId)?if_exists}</td>
                <td>${(location.positionId)?if_exists}</td>
                <td class="button-col">
                    <a class="btn btn-primary btn-sm"
                       href="<@ofbizUrl>EditInventoryItem?facilityId=${(location.facilityId)?if_exists}&locationSeqId=${(location.locationSeqId)?if_exists}</@ofbizUrl>">${uiLabelMap.ProductNewInventoryItem}</a>
                    <#if itemId?exists>
                        <a class="btn btn-primary btn-sm"
                           href="<@ofbizUrl>UpdateInventoryItem?inventoryItemId=${itemId}&facilityId=${facilityId}&locationSeqId=${(location.locationSeqId)?if_exists}</@ofbizUrl>">${uiLabelMap.ProductSetItem} ${itemId}</a>
                    </#if>
                    <@htmlScreenTemplate.renderModalPage id="EditFacilityLocation_${(location.locationSeqId)?if_exists}"  name="EditFacilityLocation_${(location.locationSeqId)?if_exists}"
                    modalUrl="/facility/control/EditFacilityLocation" modalMsg="" modalTitle="添加库存位置" description="修改位置" targetParameterIter="facilityId:'${(location.facilityId)?if_exists}',locationSeqId:'${(location.locationSeqId)?if_exists}'">
                    </@htmlScreenTemplate.renderModalPage>

                    <@htmlScreenTemplate.renderModalPage id="ListProductFacilityLocation_${(location.locationSeqId)?if_exists}"  name="ListProductFacilityLocation_${(location.locationSeqId)?if_exists}"
                    modalUrl="/facility/control/ListProductFacilityLocation" modalMsg="" modalType="view" modalTitle="位置产品" description="位置产品" targetParameterIter="facilityId:'${(location.facilityId)?if_exists}',locationSeqId:'${(location.locationSeqId)?if_exists}'">
                    </@htmlScreenTemplate.renderModalPage>
                    <@htmlScreenTemplate.renderConfirmField id="DeleteProductFacilityLocation_${(location.locationSeqId)?if_exists}"  name="DeleteProductFacilityLocation_${(location.locationSeqId)?if_exists}"
                    confirmUrl="/facility/control/DeleteFacilityLocation" confirmMessage="确定删除该库存位置?"  confirmTitle="位置删除" description="位置删除" targetParameterIter="facilityId:'${(location.facilityId)?if_exists}',locationSeqId:'${(location.locationSeqId)?if_exists}'">
                    </@htmlScreenTemplate.renderConfirmField>
                </td>
            </tr>
        <#-- toggle the row color -->
            <#if rowClass == "2">
                <#assign rowClass = "1">
            <#else>
                <#assign rowClass = "2">
            </#if>
        </#list>
    </table>
</div>
</div>
</#if>
