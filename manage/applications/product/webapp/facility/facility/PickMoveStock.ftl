<script language="JavaScript" type="text/javascript">
    function quicklookup(func, locationelement, facilityelement, productelement) {

        var productId = productelement.value;
        if (productId.length == 0) {
            alert("${StringUtil.wrapString(uiLabelMap.ProductFieldEmpty)}");
            return;
        }
        var facilityId = facilityelement.value;
        var request = "LookupProductInventoryLocation?productId=" + productId + "&facilityId=" + facilityId;
        window[func](locationelement, request);
    }
</script>

<@htmlScreenTemplate.renderScreenletBegin id="" title="${uiLabelMap.ProductStockMovesNeeded}"/>
<div class="button-bar">
    <a href="<@ofbizUrl>PickMoveStockSimple?facilityId=${facilityId?if_exists}</@ofbizUrl>" class="btn btn-primary btn-sm">${uiLabelMap.CommonPrint}</a>
</div>
<br/>
<form method="post" action="<@ofbizUrl>processPhysicalStockMove</@ofbizUrl>" name='selectAllForm' class='form-inline'>
<#-- general request fields -->
    <input type="hidden" name="facilityId" value="${facilityId?if_exists}"/>
    <input type="hidden" name="_useRowSubmit" value="Y"/>
    <#assign rowCount = 0>
    <div class="table-responsive">
        <table class="table table-striped table-bordered">
            <tr class="header-row">
                <td>${uiLabelMap.ProductProductId}</td>
                <td>${uiLabelMap.ProductProduct}</td>
                <td>${uiLabelMap.ProductFromLocation}</td>
                <td>${uiLabelMap.ProductQoh}</td>
                <td>${uiLabelMap.ProductAtp}</td>
                <td>${uiLabelMap.ProductToLocation}</td>
                <td>${uiLabelMap.ProductQoh}</td>
                <td>${uiLabelMap.ProductAtp}</td>
                <td>${uiLabelMap.ProductMinimumStock}</td>
                <td>${uiLabelMap.ProductMoveQuantity}</td>
                <td>${uiLabelMap.CommonConfirm}</td>
                <td align="right">
                ${uiLabelMap.ProductSelectAll}&nbsp;
                    <input type="checkbox" name="selectAll" value="Y" onclick="toggleAll(this, 'selectAllForm');highlightAllRows(this, 'moveInfoId_tableRow_', 'selectAllForm');"/>
                </td>
            </tr>
        <#if moveByOisgirInfoList?has_content || moveByPflInfoList?has_content>
            <#assign alt_row = false>
            <#list moveByOisgirInfoList?if_exists as moveByOisgirInfo>
                <#assign product = moveByOisgirInfo.product>
                <#assign facilityLocationFrom = moveByOisgirInfo.facilityLocationFrom>
                <#assign facilityLocationTypeEnumFrom = (facilityLocationFrom.getRelatedOneCache("TypeEnumeration"))?if_exists>
                <#assign facilityLocationTo = moveByOisgirInfo.facilityLocationTo>
                <#assign targetProductFacilityLocation = moveByOisgirInfo.targetProductFacilityLocation>
                <#assign facilityLocationTypeEnumTo = (facilityLocationTo.getRelatedOneCache("TypeEnumeration"))?if_exists>
                <#assign totalQuantity = moveByOisgirInfo.totalQuantity>
                <tr id="moveInfoId_tableRow_${rowCount}" valign="middle"<#if alt_row> class="alternate-row"</#if>>
                    <td>${product.productId}</td>
                    <td>${product.internalName?if_exists}</td>
                    <td>${facilityLocationFrom.areaId?if_exists}:${facilityLocationFrom.aisleId?if_exists}:${facilityLocationFrom.sectionId?if_exists}
                        :${facilityLocationFrom.levelId?if_exists}:${facilityLocationFrom.positionId?if_exists}<#if facilityLocationTypeEnumFrom?has_content>
                            (${facilityLocationTypeEnumFrom.description})</#if>[${facilityLocationFrom.locationSeqId}]
                    </td>
                    <td>${moveByOisgirInfo.quantityOnHandTotalFrom?if_exists}</td>
                    <td>${moveByOisgirInfo.availableToPromiseTotalFrom?if_exists}</td>
                    <td>${facilityLocationTo.areaId?if_exists}:${facilityLocationTo.aisleId?if_exists}:${facilityLocationTo.sectionId?if_exists}:${facilityLocationTo.levelId?if_exists}
                        :${facilityLocationTo.positionId?if_exists}<#if facilityLocationTypeEnumTo?has_content>(${facilityLocationTypeEnumTo.description})</#if>
                        [${facilityLocationTo.locationSeqId}]
                    </td>
                    <td>${moveByOisgirInfo.quantityOnHandTotalTo?if_exists}</td>
                    <td>${moveByOisgirInfo.availableToPromiseTotalTo?if_exists}</td>
                    <td>${targetProductFacilityLocation.minimumStock?if_exists}</td>
                    <td>${targetProductFacilityLocation.moveQuantity?if_exists}</td>
                    <td align="right">
                        <input type="hidden" name="productId_o_${rowCount}" value="${product.productId?if_exists}"/>
                        <input type="hidden" name="facilityId_o_${rowCount}" value="${facilityId?if_exists}"/>
                        <input type="hidden" name="locationSeqId_o_${rowCount}" value="${facilityLocationFrom.locationSeqId?if_exists}"/>
                        <input type="hidden" name="targetLocationSeqId_o_${rowCount}" value="${facilityLocationTo.locationSeqId?if_exists}"/>
                        <input type="text" name="quantityMoved_o_${rowCount}" size="6" value="${totalQuantity?string.number}"/>
                    </td>
                    <td align="right">
                        <input type="checkbox" name="_rowSubmit_o_${rowCount}" value="Y"
                               onclick="checkToggle(this, 'selectAllForm');highlightRow(this,'moveInfoId_tableRow_${rowCount}');"/>
                    </td>
                </tr>
                <#assign rowCount = rowCount + 1>
            <#-- toggle the row color -->
                <#assign alt_row = !alt_row>
            </#list>
            <#list moveByPflInfoList?if_exists as moveByPflInfo>
                <#assign product = moveByPflInfo.product>
                <#assign facilityLocationFrom = moveByPflInfo.facilityLocationFrom>
                <#assign facilityLocationTypeEnumFrom = (facilityLocationFrom.getRelatedOneCache("TypeEnumeration"))?if_exists>
                <#assign facilityLocationTo = moveByPflInfo.facilityLocationTo>
                <#assign targetProductFacilityLocation = moveByPflInfo.targetProductFacilityLocation>
                <#assign facilityLocationTypeEnumTo = (facilityLocationTo.getRelatedOneCache("TypeEnumeration"))?if_exists>
                <#assign totalQuantity = moveByPflInfo.totalQuantity>
                <tr id="moveInfoId_tableRow_${rowCount}" valign="middle"<#if alt_row> class="alternate-row"</#if>>
                    <td>${product.productId}</td>
                    <td>${product.internalName?if_exists}</td>
                    <td>${facilityLocationFrom.areaId?if_exists}:${facilityLocationFrom.aisleId?if_exists}:${facilityLocationFrom.sectionId?if_exists}
                        :${facilityLocationFrom.levelId?if_exists}:${facilityLocationFrom.positionId?if_exists}<#if facilityLocationTypeEnumFrom?has_content>
                            (${facilityLocationTypeEnumFrom.description})</#if>[${facilityLocationFrom.locationSeqId}]
                    </td>
                    <td>${moveByPflInfo.quantityOnHandTotalFrom?if_exists}</td>
                    <td>${moveByPflInfo.availableToPromiseTotalFrom?if_exists}</td>
                    <td>${facilityLocationTo.areaId?if_exists}:${facilityLocationTo.aisleId?if_exists}:${facilityLocationTo.sectionId?if_exists}:${facilityLocationTo.levelId?if_exists}
                        :${facilityLocationTo.positionId?if_exists}<#if facilityLocationTypeEnumTo?has_content>(${facilityLocationTypeEnumTo.description})</#if>
                        [${facilityLocationTo.locationSeqId}]
                    </td>
                    <td>${moveByPflInfo.quantityOnHandTotalTo?if_exists}</td>
                    <td>${moveByPflInfo.availableToPromiseTotalTo?if_exists}</td>
                    <td>${targetProductFacilityLocation.minimumStock?if_exists}</td>
                    <td>${targetProductFacilityLocation.moveQuantity?if_exists}</td>
                    <td align="right">
                        <input type="hidden" name="productId_o_${rowCount}" value="${product.productId?if_exists}"/>
                        <input type="hidden" name="facilityId_o_${rowCount}" value="${facilityId?if_exists}"/>
                        <input type="hidden" name="locationSeqId_o_${rowCount}" value="${facilityLocationFrom.locationSeqId?if_exists}"/>
                        <input type="hidden" name="targetLocationSeqId_o_${rowCount}" value="${facilityLocationTo.locationSeqId?if_exists}"/>
                        <input type="text" name="quantityMoved_o_${rowCount}" size="6" value="${totalQuantity?string.number}"/>
                    </td>
                    <td align="right">
                        <input type="checkbox" name="_rowSubmit_o_${rowCount}" value="Y"
                               onclick="checkToggle(this, 'selectAllForm');highlightRow(this,'moveInfoId_tableRow_${rowCount}');"/>
                    </td>
                </tr>
                <#assign rowCount = rowCount + 1>
            </#list>
            <tr>
                <td colspan="13" align="right">
                    <a href="javascript:document.selectAllForm.submit();" class="buttontext">${uiLabelMap.ProductConfirmSelectedMoves}</a>
                </td>
            </tr>
        <#else>
            <tr>
                <td colspan="13"><h4>${uiLabelMap.ProductNoStockMovesNeeded}.</h4></td>
            </tr>
        </#if>
        <#assign messageCount = 0>
        <#list pflWarningMessageList?if_exists as pflWarningMessage>
            <#assign messageCount = messageCount + 1>
            <tr>
                <td colspan="13"><h4>${messageCount}:${pflWarningMessage}.</h4></td>
            </tr>
        </#list>
        </table>
    </div>
    <input type="hidden" name="_rowCount" value="${rowCount}"/>
</form>
<@htmlScreenTemplate.renderScreenletEnd/>
<@htmlScreenTemplate.renderScreenletBegin id="" title="${uiLabelMap.ProductQuickStockMove}"/>

<form method="post" action="<@ofbizUrl>processQuickStockMove</@ofbizUrl>" name='quickStockMove' class="form-inline">
    <input type="hidden" name="facilityId" value="${facilityId?if_exists}"/>

    <div class="table-responsive">
        <table class="table table-striped table-bordered">
            <tr class="header-row">
                <th>${uiLabelMap.ProductProduct}</th>
                <th>${uiLabelMap.ProductFromLocation}</th>
                <th>${uiLabelMap.ProductToLocation}</th>
                <th>${uiLabelMap.ProductMoveQuantity}</th>
                <th>&nbsp;</th>
            </tr>
            <tr>
                <td>
                <@htmlTemplate.lookupField formName="quickStockMove" name="productId" id="productId" fieldFormName="LookupProduct"/>
                </td>
                <td>
                    <input type="text" size="20" name="locationSeqId" maxlength="20" class="form-control"/>
                    <a href="javascript:quicklookup('call_fieldlookup2', document.quickStockMove.locationSeqId, document.quickStockMove.facilityId, document.quickStockMove.productId)">
                        <#--<button id="0_lookupId_button" class="btn btn-default btn-sm">-->
                            <i class="fa fa-search fa-fw"></i>
                        <#--</button>-->
                        <#--<img src="<@ofbizContentUrl>/images/fieldlookup.gif</@ofbizContentUrl>" width="15" height="16" border="0" alt="${uiLabelMap.CommonClickHereForFieldLookup}"/>-->
                    </a>
                </td>
                <td>
                <@htmlTemplate.lookupField formName="quickStockMove" name="targetLocationSeqId" id="targetLocationSeqId" fieldFormName="LookupFacilityLocation?facilityId=${facilityId}&amp;locationTypeEnumId=FLT_PICKLOC"/>
                </td>
                <td><input type="text" name="quantityMoved" size="6" class="form-control"/></td>
            </tr>
            <tr>
                <td colspan="13" align="right">
                    <a href="javascript:document.quickStockMove.submit();" class="btn btn-primary btn-sm">${uiLabelMap.ProductQuickStockMove}</a>
                </td>
            </tr>
        </table>
    </div>
</form>
<@htmlScreenTemplate.renderScreenletEnd/>