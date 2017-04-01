

<#if picklistInfoList?has_content>
    <#list picklistInfoList as picklistInfo>

    <#assign picklist = picklistInfo.picklist>
    <#if picklistInfo_index%2 ==1>
        <@htmlScreenTemplate.renderScreenletBegin id="" title="${uiLabelMap.ProductPicklistManage}" collapsed=false style="panel-info"/>
     <#else>
         <@htmlScreenTemplate.renderScreenletBegin id="" title="${uiLabelMap.ProductPicklistManage}" collapsed=false style="panel-primary"/>
    </#if>
    <#-- Picklist -->
    <form method="post" action="<@ofbizUrl>updatePicklist</@ofbizUrl>" class="form-inline">
        <input type="hidden" name="facilityId" value="${facilityId}"/>
        <input type="hidden" name="picklistId" value="${picklist.picklistId}"/>

        <div class="form-group-sm">
            <div class="input-group">
                <label class="input-group-addon">${uiLabelMap.ProductPickList}</label> ${picklist.picklistId}</div>
            <div class="input-group"><label class="input-group-addon">${uiLabelMap.CommonDate}</label>${picklist.picklistDate?string('yyyy-MM-dd hh:mm:ss')}</div>
            <select name="statusId" class="form-control">
                <option value="${picklistInfo.statusItem.statusId}" selected>${picklistInfo.statusItem.get("description",locale)}</option>
                <option value="${picklistInfo.statusItem.statusId}">---</option>
                <#list picklistInfo.statusValidChangeToDetailList as statusValidChangeToDetail>
                    <option value="${statusValidChangeToDetail.get("statusIdTo", locale)}">${statusValidChangeToDetail.get("description", locale)}
                        (${statusValidChangeToDetail.get("transitionName", locale)})
                    </option>
                </#list>
            </select>

            <input type="submit" value="${uiLabelMap.CommonUpdate}" class="btn btn-primary btn-sm"/>
            <label class="control-label">${uiLabelMap.ProductCreatedModifiedBy}</label> ${picklist.createdByUserLogin}/${picklist.lastModifiedByUserLogin}
            <a href="<@ofbizUrl>PicklistReport.pdf?picklistId=${picklist.picklistId}</@ofbizUrl>" target="_blank" class="btn btn-primary btn-sm">${uiLabelMap.ProductPick}
                /${uiLabelMap.ProductPacking} ${uiLabelMap.CommonReports}</a>

        </div>
    </form>


        <#if picklistInfo.shipmentMethodType?has_content>
        <div class="alert  fade in m-b-15">
            <p>${uiLabelMap.CommonFor} ${uiLabelMap.ProductShipmentMethodType}
                : ${picklistInfo.shipmentMethodType.description?default(picklistInfo.shipmentMethodType.shipmentMethodTypeId)}</p>
        </div>
        </#if>


    <#-- PicklistRole -->
        <#list picklistInfo.picklistRoleInfoList?if_exists as picklistRoleInfo>
        <div class="alert  fade in m-b-15">
            <p>${uiLabelMap.PartyParty}
                : ${picklistRoleInfo.partyNameView.firstName?if_exists} ${picklistRoleInfo.partyNameView.middleName?if_exists} ${picklistRoleInfo.partyNameView.lastName?if_exists} ${picklistRoleInfo.partyNameView.groupName?if_exists}</p>

            <p>${uiLabelMap.PartyRole}: ${picklistRoleInfo.roleType.description}</p>

            <p>${uiLabelMap.CommonFrom}${picklistRoleInfo.picklistRole.fromDate}</p>
            <#if picklistRoleInfo.picklistRole.thruDate?exists><p>${uiLabelMap.CommonThru}${picklistRoleInfo.picklistRole.thruDate}</p></#if>
        </div>
        </#list>

    <form method="post" action="<@ofbizUrl>createPicklistRole</@ofbizUrl>" class="form-inline">
        <input type="hidden" name="facilityId" value="${facilityId}"/>
        <input type="hidden" name="picklistId" value="${picklist.picklistId}"/>
        <input type="hidden" name="roleTypeId" value="PICKER"/>

        <div class="form-group">
            <div class="input-group">
                <span class="input-group-addon">${uiLabelMap.ProductAssignPicker}</span>
                <select name="partyId" class="form-control">
                    <#list partyRoleAndPartyDetailList as partyRoleAndPartyDetail>
                        <option value="${partyRoleAndPartyDetail.partyId}">${partyRoleAndPartyDetail.firstName?if_exists} ${partyRoleAndPartyDetail.middleName?if_exists} ${partyRoleAndPartyDetail.lastName?if_exists} ${partyRoleAndPartyDetail.groupName?if_exists}
                            [${partyRoleAndPartyDetail.partyId}]
                        </option>
                    </#list>
                </select>
            </div>
            <input type="submit" value="${uiLabelMap.CommonAdd}" class="btn btn-primary btn-sm"/>
        </div>
    </form>
    <br/>

    <#-- PicklistStatusHistory -->

        <#list picklistInfo.picklistStatusHistoryInfoList?if_exists as picklistStatusHistoryInfo>
        <div class="alert  fade in m-b-15">
            <p>${uiLabelMap.CommonStatus}:${uiLabelMap.CommonChange} ${uiLabelMap.CommonFrom} ${picklistStatusHistoryInfo.statusItem.get("description",locale)}
            ${uiLabelMap.CommonTo} ${picklistStatusHistoryInfo.statusItemTo.description}
            ${uiLabelMap.CommonOn} ${picklistStatusHistoryInfo.picklistStatusHistory.changeDate}
            ${uiLabelMap.CommonBy} ${picklistStatusHistoryInfo.picklistStatusHistory.changeUserLoginId}</p>
        </div>
        </#list>

    <hr/>
    <#-- PicklistBin -->
        <#list picklistInfo.picklistBinInfoList?if_exists as picklistBinInfo>
            <#assign isBinComplete = Static["org.ofbiz.shipment.picklist.PickListServices"].isBinComplete(delegator, picklistBinInfo.picklistBin.picklistBinId)/>
            <#if (!isBinComplete)>
            <div class="alert  fade in m-b-15">
                <p>${uiLabelMap.ProductBinNum}${picklistBinInfo.picklistBin.binLocationNumber}&nbsp;(${picklistBinInfo.picklistBin.picklistBinId})
                    <#if picklistBinInfo.primaryOrderHeader?exists>${uiLabelMap.ProductPrimaryOrderId} ${picklistBinInfo.primaryOrderHeader.orderId}</#if>
                <#if picklistBinInfo.primaryOrderItemShipGroup?exists>${uiLabelMap.ProductPrimaryShipGroupSeqId} ${picklistBinInfo.primaryOrderItemShipGroup.shipGroupSeqId}</#if>
                <#if !picklistBinInfo.picklistItemInfoList?has_content><a href="javascript:document.DeletePicklistBin_${picklistInfo_index}_${picklistBinInfo_index}.submit()"
                                                                          class="buttontext">${uiLabelMap.CommonDelete}</a></#if></p>
                <form name="DeletePicklistBin_${picklistInfo_index}_${picklistBinInfo_index}" method="post" action="<@ofbizUrl>deletePicklistBin</@ofbizUrl>">
                    <input type="hidden" name="picklistBinId" value="${picklistBinInfo.picklistBin.picklistBinId}"/>
                    <input type="hidden" name="facilityId" value="${facilityId?if_exists}"/>
                </form>
            </div>
            <div class="alert  fade in m-b-15">

                <form method="post" action="<@ofbizUrl>updatePicklistBin</@ofbizUrl>" class="form-inline">
                    <input type="hidden" name="facilityId" value="${facilityId}"/>
                    <input type="hidden" name="picklistBinId" value="${picklistBinInfo.picklistBin.picklistBinId}"/>

                    <div class="from-group">
                        <label class="control-label">${uiLabelMap.CommonUpdate} ${uiLabelMap.ProductBinNum}</label>
                        <div class="input-group"><span class="input-group-addon">${uiLabelMap.ProductLocation} ${uiLabelMap.CommonNbr}</span>
                            <input type="text" size="2" name="binLocationNumber" value="${picklistBinInfo.picklistBin.binLocationNumber}" class="form-control"/>
                        </div>
                        <div class="input-group"><span class="input-group-addon">${uiLabelMap.PageTitlePickList}</span>
                            <select name="picklistId" class="form-control">
                                <#list picklistActiveList as picklistActive>
                                    <#assign picklistActiveStatusItem = picklistActive.getRelatedOneCache("StatusItem")>
                                    <option value="${picklistActive.picklistId}"<#if picklistActive.picklistId == picklist.picklistId> selected="selected"</#if>>${picklistActive.picklistId}
                                        [${uiLabelMap.CommonDate}:${picklistActive.picklistDate},${uiLabelMap.CommonStatus}:${picklistActiveStatusItem.get("description",locale)}]
                                    </option>
                                </#list>
                            </select>
                        </div>

                        <input type="submit" value="${uiLabelMap.CommonUpdate}" class="btn btn-primary btn-sm"/>
                    </div>
                </form>
            </div>
            <br/>
                <#if picklistBinInfo.picklistItemInfoList?has_content>
                <div class="table-responsive">
                    <table class="table table-bordered">
                        <tr class="header-row">
                            <th>${uiLabelMap.ProductOrderId}</
                            >
                            <th>${uiLabelMap.ProductOrderShipGroupId}</th>
                            <th>${uiLabelMap.ProductOrderItem}</th>
                            <th>${uiLabelMap.ProductProduct}</th>
                            <th>${uiLabelMap.ProductInventoryItem}</th>
                            <th>${uiLabelMap.ProductLocation}</th>
                            <th>${uiLabelMap.ProductQuantity}</th>
                            <th>&nbsp;</th>
                        </tr>
                        <#assign alt_row = false>
                        <#list picklistBinInfo.picklistItemInfoList?if_exists as picklistItemInfo>
                            <#assign picklistItem = picklistItemInfo.picklistItem>
                            <#assign inventoryItemAndLocation = picklistItemInfo.inventoryItemAndLocation>
                            <tr valign="middle"<#if alt_row> class="alternate-row"</#if>>
                                <td>${picklistItem.orderId}</td>
                                <td>${picklistItem.shipGroupSeqId}</td>
                                <td>${picklistItem.orderItemSeqId}</td>
                                <td>${picklistItemInfo.orderItem.productId}<#if picklistItemInfo.orderItem.productId != inventoryItemAndLocation.productId>
                                    &nbsp;[${inventoryItemAndLocation.productId}]</#if></td>
                                <td>${inventoryItemAndLocation.inventoryItemId}</td>
                                <td>${inventoryItemAndLocation.areaId?if_exists}-${inventoryItemAndLocation.aisleId?if_exists}-${inventoryItemAndLocation.sectionId?if_exists}
                                    -${inventoryItemAndLocation.levelId?if_exists}-${inventoryItemAndLocation.positionId?if_exists}</td>
                                <td>${picklistItem.quantity}</td>
                                <#if !picklistItemInfo.itemIssuanceList?has_content>
                                    <td>
                                        <form name="deletePicklistItem_${picklist.picklistId}_${picklistItem.orderId}_${picklistItemInfo_index}" method="post"
                                              action="<@ofbizUrl>deletePicklistItem</@ofbizUrl>">
                                            <input type="hidden" name="picklistBinId" value="${picklistItemInfo.picklistItem.picklistBinId}"/>
                                            <input type="hidden" name="orderId" value="${picklistItemInfo.picklistItem.orderId}"/>
                                            <input type="hidden" name="orderItemSeqId" value="${picklistItemInfo.picklistItem.orderItemSeqId}"/>
                                            <input type="hidden" name="shipGroupSeqId" value="${picklistItemInfo.picklistItem.shipGroupSeqId}"/>
                                            <input type="hidden" name="inventoryItemId" value="${picklistItemInfo.picklistItem.inventoryItemId}"/>
                                            <input type="hidden" name="facilityId" value="${facilityId?if_exists}"/>
                                            <a href="javascript:if (confirm('确定删除该条记录?')) document.deletePicklistItem_${picklist.picklistId}_${picklistItem.orderId}_${picklistItemInfo_index}.submit()"
                                               class='btn btn-primary btn-sm'>&nbsp;${uiLabelMap.CommonDelete}&nbsp;</a>
                                        </form>
                                    </td>
                                </#if>
                                <td>
                                <#-- picklistItem.orderItemShipGrpInvRes (do we want to display any of this info?) -->
                                <#-- picklistItemInfo.itemIssuanceList -->
                                    <#list picklistItemInfo.itemIssuanceList?if_exists as itemIssuance>
                                        <b>${uiLabelMap.ProductIssue} ${uiLabelMap.CommonTo} ${uiLabelMap.ProductShipmentItemSeqId}:</b> ${itemIssuance.shipmentId}
                                        :${itemIssuance.shipmentItemSeqId}
                                        <b>${uiLabelMap.ProductQuantity}:</b> ${itemIssuance.quantity}
                                        <b>${uiLabelMap.CommonDate}: </b> ${itemIssuance.issuedDateTime}
                                    </#list>
                                </td>
                            </tr>
                        <#-- toggle the row color -->
                            <#assign alt_row = !alt_row>
                        </#list>
                    </table>
                </div>
                    <#if picklistBinInfo.productStore.managedByLot?exists && picklistBinInfo.productStore.managedByLot = "Y">
                    <div class="table-responsive">
                        <table class="table table-bordered">
                            <tr class="header-row">
                                <th>${uiLabelMap.ProductOrderId}</th>
                                <th>${uiLabelMap.ProductOrderShipGroupId}</th>
                                <th>${uiLabelMap.ProductOrderItem}</th>
                                <th>${uiLabelMap.ProductProduct}</th>
                                <th>${uiLabelMap.ProductInventoryItem}</th>
                                <th>${uiLabelMap.ProductLotId}</th>
                                <th>${uiLabelMap.ProductQuantity}</th>
                                <th>&nbsp;</th>
                            </tr>
                            <#assign alt_row = false>
                            <#list picklistBinInfo.picklistItemInfoList?if_exists as picklistItemInfo>
                                <#assign picklistItem = picklistItemInfo.picklistItem>
                                <#assign inventoryItemAndLocation = picklistItemInfo.inventoryItemAndLocation>
                                <#if !picklistItemInfo.product.lotIdFilledIn?has_content || picklistItemInfo.product.lotIdFilledIn != "Forbidden">
                                    <form name="editPicklistItem_${picklist.picklistId}_${picklistItem.orderId}_${picklistItemInfo_index}" method="post" class="form-inline"
                                          action="<@ofbizUrl>editPicklistItem</@ofbizUrl>">
                                        <div class="input-group-sm">
                                        <tr valign="middle"<#if alt_row> class="alternate-row"</#if>>
                                            <td>${picklistItem.orderId}</td>
                                            <td>${picklistItem.shipGroupSeqId}</td>
                                            <td>${picklistItem.orderItemSeqId}</td>
                                            <td>${picklistItemInfo.orderItem.productId}<#if picklistItemInfo.orderItem.productId != inventoryItemAndLocation.productId>
                                                &nbsp;[${inventoryItemAndLocation.productId}]</#if></td>
                                            <td>${inventoryItemAndLocation.inventoryItemId}</td>
                                            <td><input type="text" class="form-control" name="lotId" <#if inventoryItemAndLocation.lotId?has_content>value="${inventoryItemAndLocation.lotId}"</#if> /></td>
                                            <td><input type="text" class="form-control" name="quantity" value="${picklistItem.quantity}"/></td>
                                            <td>
                                                <input type="hidden" name="picklistBinId" value="${picklistItemInfo.picklistItem.picklistBinId}"/>
                                                <input type="hidden" name="orderId" value="${picklistItemInfo.picklistItem.orderId}"/>
                                                <input type="hidden" name="orderItemSeqId" value="${picklistItemInfo.picklistItem.orderItemSeqId}"/>
                                                <input type="hidden" name="shipGroupSeqId" value="${picklistItemInfo.picklistItem.shipGroupSeqId}"/>
                                                <input type="hidden" name="inventoryItemId" value="${picklistItemInfo.picklistItem.inventoryItemId}"/>
                                                <input type="hidden" name="facilityId" value="${facilityId?if_exists}"/>
                                                <input type="hidden" name="productId" value="${picklistItemInfo.orderItem.productId}"/>
                                                <#if inventoryItemAndLocation.lotId?has_content>
                                                    <input type="hidden" name="oldLotId" value="${inventoryItemAndLocation.lotId}"/>
                                                </#if>
                                                <a href='javascript:document.editPicklistItem_${picklist.picklistId}_${picklistItem.orderId}_${picklistItemInfo_index}.submit()'
                                                   class='btn btn-primary btn-sm'>&nbsp;${uiLabelMap.CommonEdit}&nbsp;</a>
                                            </td>
                                        </tr>
                                        </div>
                                    </form>
                                <#-- toggle the row color -->
                                    <#assign alt_row = !alt_row>
                                </#if>
                            </#list>
                        </table>
                    </div>
                    </#if>
                </#if>
            </#if>
        </#list>
        <#if picklistInfo_has_next>
        <hr/>
        </#if>
        <@htmlScreenTemplate.renderScreenletEnd/>
    </#list>
<#else>
<h4>${uiLabelMap.ProductNoPicksStarted}.</h4>
</#if>
