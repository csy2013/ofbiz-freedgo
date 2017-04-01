<#if locationSeqId?exists>
<@htmlScreenTemplate.renderScreenletBegin id="" title="${uiLabelMap.ProductAddProduct}"  collapsed=false/>
<form method="post" action="<@ofbizUrl>createProductFacilityLocation</@ofbizUrl>" class="form-horizontal" data-parsley-validate="true" name="createProductFacilityLocationForm" id="createProductFacilityLocationForm">

    <input type="hidden" name="facilityId" value="${facilityId?if_exists}"/>
    <input type="hidden" name="locationSeqId" value="${locationSeqId?if_exists}"/>
    <input type="hidden" name="useValues" value="true"/>

    <div class="form-group">
        <div class="col-md-3 control-label">${uiLabelMap.ProductProductId}</div>
        <div class="col-md-5">
        <@htmlTemplate.lookupField formName="createProductFacilityLocationForm" name="productId" id="productId" fieldFormName="LookupProduct" required="true"/>
            <#--<input type="text" size="10" name="productId"/>-->
        </div>
    </div>
    <div class="form-group">
        <div class="col-md-3 control-label">${uiLabelMap.ProductMinimumStock}</div>
        <div class="col-md-5">
            <input type="text" size="10" name="minimumStock" class="form-control" data-parsley-required="true"/></div>
    </div>
    <div class="form-group">
        <div class="col-md-3 control-label">${uiLabelMap.ProductMoveQuantity}</div>
        <div class="col-md-5">
            <input type="text" size="10" name="moveQuantity" class="form-control" data-parsley-required="true"/></div>
    </div>
    <div class="form-group">

        <div class="col-md-3">&nbsp;</div>
        <div class="col-md-5 pull-right">
            <input type="submit" value="保存" class="btn btn-primary btn-sm"
                                               <#-- onclick="ajaxSubmitFormUpdateAreas('createProductFacilityLocationForm', 'ajax,search-results,/facility/control/createProductFacilityLocation,')"-->/>
        </div>
    </div>
</form>
<@htmlScreenTemplate.renderScreenletEnd/>
</#if>