<div class="am-cf am-padding-xs">
    <div class="am-panel am-panel-default">
        <div class="am-panel-hd am-cf">
        ${uiLabelMap.PageTitleEditProductAssociations}
        </div>
        <div class="am-panel-bd am-collapse am-in">
            <div class="am-g">
                <div class="am-u-lg-12">
                    <form action="<@ofbizUrl>UpdateProductAssoc</@ofbizUrl>" method="post"
                          class="am-form am-form-horizontal" name="editProductAssocForm">
                        <input type="hidden" name="productId" value="${productId?if_exists}"/>
                    <#if !(productAssoc?exists)>
                        <#if productId?exists && productIdTo?exists && productAssocTypeId?exists && fromDate?exists>
                            <div>
                                <b><#assign uiLabelWithVar=uiLabelMap.ProductAssociationNotFound?interpret><@uiLabelWithVar/></b>
                            </div>
                            <input type="hidden" name="UPDATE_MODE" value="CREATE"/>

                            <div class="am-form-group am-g">
                                <label class="am-control-label am-u-md-5 am-u-lg-5">${uiLabelMap.ProductProductId}</label>

                                <div class="am-u-md-3 am-u-lg-3 am-u-end">
                                    <input class="am-form-field am-input-sm" type="text" name="PRODUCT_ID" size="30"
                                           maxlength="60"
                                           value="${productId?if_exists}"/>
                                </div>
                            </div>
                            <div class="am-form-group am-g">
                                <label class="am-control-label am-u-md-5 am-u-lg-5">${uiLabelMap.ProductProductIdTo}</label>

                                <div class="am-u-md-3 am-u-lg-3 am-u-end">
                                    <@amazeHtmlTemplate.lookupField formName="editProductAssocForm" name="PRODUCT_ID_TO" id="PRODUCT_ID_TO" fieldFormName="LookupProduct"/>
                                </div>
                            </div>

                            <div class="am-form-group am-g">
                                <label class="am-control-label am-u-md-5 am-u-lg-5">${uiLabelMap.ProductAssociationTypeId}</label>

                                <div class="am-u-md-3 am-u-lg-3 am-u-end">
                                    <select name="PRODUCT_ASSOC_TYPE_ID"
                                            data-am-selected="{btnWidth:360, btnStyle:'default'}" size="1">
                                        <#if productAssocTypeId?has_content>
                                            <#assign curAssocType = delegator.findByPrimaryKey("ProductAssocType", Static["org.ofbiz.base.util.UtilMisc"].toMap("productAssocTypeId", productAssocTypeId))>
                                            <#if curAssocType?exists>
                                                <option selected="selected"
                                                        value="${(curAssocType.productAssocTypeId)?if_exists}">${(curAssocType.get("description",locale))?if_exists}</option>
                                                <option value="${(curAssocType.productAssocTypeId)?if_exists}"></option>
                                            </#if>
                                        </#if>
                                        <#list assocTypes as assocType>
                                            <option value="${(assocType.productAssocTypeId)?if_exists}">${(assocType.get("description",locale))?if_exists}</option>
                                        </#list>
                                    </select>
                                </div>
                            </div>

                            <div class="am-form-group am-g">
                                <label class="am-control-label am-u-md-5 am-u-lg-5">${uiLabelMap.CommonFromDate}</label>

                                <div class="am-u-md-3 am-u-lg-3 am-u-end">
                                    <@amazeHtmlTemplate.renderDateTimeField name="FROM_DATE" event="" action="" value="${fromDate?if_exists}" className="" alert="" title="${uiLabelMap.CommonSetNowEmpty}" size="25" maxlength="30" id="fromDate1" dateType="date" shortDateInput=false timeDropdownParamName="" defaultDateTimeString="" localizedIconTitle="" timeDropdown="" timeHourName="" classString="" hour1="" hour2="" timeMinutesName="" minutes="" isTwelveHour="" ampmName="" amSelected="" pmSelected="" compositeType="" formName=""/>
                                </div>
                            </div>
                        <#else>
                            <input type="hidden" name="UPDATE_MODE" value="CREATE"/>

                            <div class="am-form-group am-g">
                                <label class="am-control-label am-u-md-5 am-u-lg-5">${uiLabelMap.ProductProductId}</label>

                                <div class="am-u-md-3 am-u-lg-3 am-u-end">
                                    <input class="am-form-field am-input-sm" type="text" name="PRODUCT_ID" size="30"
                                           maxlength="60"
                                           value="${productId?if_exists}"/>
                                </div>
                            </div>
                            <div class="am-form-group am-g">
                                <label class="am-control-label am-u-md-5 am-u-lg-5">${uiLabelMap.ProductProductIdTo}</label>

                                <div class="am-u-md-3 am-u-lg-3 am-u-end">
                                    <@amazeHtmlTemplate.lookupField formName="editProductAssocForm" name="PRODUCT_ID_TO" id="PRODUCT_ID_TO" fieldFormName="LookupProduct"/>
                                </div>
                            </div>

                            <div class="am-form-group am-g">
                                <label class="am-control-label am-u-md-5 am-u-lg-5">${uiLabelMap.ProductAssociationTypeId}</label>

                                <div class="am-u-md-3 am-u-lg-3 am-u-end">
                                    <select name="PRODUCT_ASSOC_TYPE_ID"
                                            data-am-selected="{btnWidth:360, btnStyle:'default'}" size="1">
                                        <#list assocTypes as assocType>
                                            <option value="${(assocType.productAssocTypeId)?if_exists}">${(assocType.get("description",locale))?if_exists}</option>
                                        </#list>
                                    </select>
                                </div>
                            </div>

                            <div class="am-form-group am-g">
                                <label class="am-control-label am-u-md-5 am-u-lg-5">${uiLabelMap.CommonFromDate}</label>

                                <div class="am-u-md-3 am-u-lg-3 am-u-end">
                                    <@amazeHtmlTemplate.renderDateTimeField name="FROM_DATE" event="" action="" value="${fromDate?if_exists}" className="" alert="" title="${uiLabelMap.CommonSetNowEmpty}" size="25" maxlength="30" id="fromDate2" dateType="date" shortDateInput=false timeDropdownParamName="" defaultDateTimeString="" localizedIconTitle="" timeDropdown="" timeHourName="" classString="" hour1="" hour2="" timeMinutesName="" minutes="" isTwelveHour="" ampmName="" amSelected="" pmSelected="" compositeType="" formName=""/>
                                </div>
                            </div>
                        </#if>
                    <#else>
                        <#assign isCreate = false>
                        <#assign curProductAssocType = productAssoc.getRelatedOneCache("ProductAssocType")>
                        <input type="hidden" name="UPDATE_MODE" value="UPDATE"/>
                        <input type="hidden" name="PRODUCT_ID" value="${productId?if_exists}"/>
                        <input type="hidden" name="PRODUCT_ID_TO" value="${productIdTo?if_exists}"/>
                        <input type="hidden" name="PRODUCT_ASSOC_TYPE_ID" value="${productAssocTypeId?if_exists}"/>
                        <input type="hidden" name="FROM_DATE" value="${fromDate?if_exists}"/>

                        <div class="am-form-group am-g">
                            <label class="am-control-label am-u-md-5 am-u-lg-5">${uiLabelMap.ProductProductId}</label>
                            <label class="am-control-label am-u-md-3 am-u-lg-3 am-u-end">${productId?if_exists}</label>&nbsp;&nbsp;${uiLabelMap.ProductRecreateAssociation}

                        </div>
                        <div class="am-form-group am-g">
                            <label class="am-control-label am-u-md-5 am-u-lg-5">${uiLabelMap.ProductProductIdTo}</label>
                            <label class="am-control-label am-u-md-3 am-u-lg-3 am-u-end">${productIdTo?if_exists}</label>&nbsp;&nbsp;${uiLabelMap.ProductRecreateAssociation}

                        </div>

                        <div class="am-form-group am-g">
                            <label class="am-control-label am-u-md-5 am-u-lg-5">${uiLabelMap.ProductAssociationTypeId}</label>
                            <label class="am-control-label am-u-md-3 am-u-lg-3 am-u-end"><#if curProductAssocType?exists>${(curProductAssocType.get("description",locale))?if_exists}<#else> ${productAssocTypeId?if_exists}</#if></label>
                            &nbsp;&nbsp;${uiLabelMap.ProductRecreateAssociation}
                        </div>

                        <div class="am-form-group am-g">
                            <label class="am-control-label am-u-md-5 am-u-lg-5">${uiLabelMap.CommonFromDate}</label>

                            <div class="am-u-md-3 am-u-lg-3 am-u-end">
                                <@amazeHtmlTemplate.renderDateTimeField name="FROM_DATE" event="" action="" value="${fromDate?if_exists}" className="" alert="" title="${uiLabelMap.CommonSetNowEmpty}" size="25" maxlength="30" id="fromDate2" dateType="date" shortDateInput=false timeDropdownParamName="" defaultDateTimeString="" localizedIconTitle="" timeDropdown="" timeHourName="" classString="" hour1="" hour2="" timeMinutesName="" minutes="" isTwelveHour="" ampmName="" amSelected="" pmSelected="" compositeType="" formName=""/>
                            </div>
                            <label class="am-control-label am-u-md-3 am-u-lg-3 am-u-end">${fromDate?if_exists}</label>&nbsp;&nbsp;${uiLabelMap.ProductRecreateAssociation}

                        </div>
                    </#if>

                        <div class="am-form-group am-g">
                            <label class="am-control-label am-u-md-5 am-u-lg-5">${uiLabelMap.CommonThruDate}</label>

                            <div class="am-u-md-3 am-u-lg-3 am-u-end">
                            <#if useValues>
                                <#assign value = productAssoc.thruDate?if_exists>
                            <#else>
                                <#assign value = (request.getParameter("THRU_DATE"))?if_exists>
                            </#if>
                                <@amazeHtmlTemplate.renderDateTimeField name="THRU_DATE" event="" action="" value="${value}" className="" alert="" title="Format: yyyy-MM-dd HH:mm:ss.SSS" size="25" maxlength="30" id="thruDate1" dateType="date" shortDateInput=false timeDropdownParamName="" defaultDateTimeString="" localizedIconTitle="" timeDropdown="" timeHourName="" classString="" hour1="" hour2="" timeMinutesName="" minutes="" isTwelveHour="" ampmName="" amSelected="" pmSelected="" compositeType="" formName=""/>
                            </div>
                        </div>

                        <div class="am-form-group am-g">
                            <label class="am-control-label am-u-md-5 am-u-lg-5">${uiLabelMap.ProductSequenceNum}</label>

                            <div class="am-u-md-3 am-u-lg-3 am-u-end">
                                <input class="am-form-field am-input-sm" type="text" name="SEQUENCE_NUM" size="30"
                                       maxlength="60" <#if useValues>value="${(productAssoc.sequenceNum)?if_exists}"
                                       <#else>value="${(request.getParameter("SEQUENCE_NUM"))?if_exists}"</#if> />
                            </div>
                        </div>

                        <div class="am-form-group am-g">
                            <label class="am-control-label am-u-md-5 am-u-lg-5">${uiLabelMap.ProductReason}</label>

                            <div class="am-u-md-3 am-u-lg-3 am-u-end">
                                <input type="text" class="am-form-field am-input-sm" name="REASON"
                                       <#if useValues>value="${(productAssoc.reason)?if_exists}"
                                       <#else>value="${(request.getParameter("REASON"))?if_exists}"</#if> size="60"
                                       maxlength="255"/>
                            </div>
                        </div>

                        <div class="am-form-group am-g">
                            <label class="am-control-label am-u-md-5 am-u-lg-5">${uiLabelMap.ProductInstruction}</label>

                            <div class="am-u-md-3 am-u-lg-3 am-u-end">
                                <input type="text" class="am-form-field am-input-sm" name="INSTRUCTION"
                                       <#if useValues>value="${(productAssoc.instruction)?if_exists}"
                                       <#else>value="${(request.getParameter("INSTRUCTION"))?if_exists}"</#if> size="60"
                                       maxlength="255"/>
                            </div>
                        </div>

                        <div class="am-form-group am-g">
                            <label class="am-control-label am-u-md-5 am-u-lg-5">${uiLabelMap.ProductQuantity}</label>

                            <div class="am-u-md-3 am-u-lg-3 am-u-end">
                                <input type="text" class="am-form-field am-input-sm" name="QUANTITY"
                                       <#if useValues>value="${(productAssoc.quantity)?if_exists}"
                                       <#else>value="${(request.getParameter("QUANTITY"))?if_exists}"</#if> size="10"
                                       maxlength="15"/>
                            </div>
                        </div>

                        <div class="am-form-group am-g">
                            <label class="am-control-label am-u-md-5 am-u-lg-5">&nbsp;</label>

                            <div class="am-u-md-3 am-u-lg-3 am-u-end">
                                <span style="margin-left: 100px"> <input type="submit"
                                                                         class="am-btn am-btn-primary am-btn-sm"
                                                                         <#if isCreate>value="${uiLabelMap.CommonCreate}"
                                                                         <#else>value="${uiLabelMap.CommonUpdate}"</#if> /> </span>
                            </div>
                        </div>

                    </form>
                </div>
            </div>
        </div>
    </div>
</div>


<div class="am-cf am-padding-xs">
    <div class="am-panel am-panel-default">
        <div class="am-panel-hd am-cf">
        ${uiLabelMap.ProductAssociationsFromProduct}
        </div>
        <div class="am-panel-bd am-collapse am-in">
            <div class="am-g">
                <div class="am-u-lg-12">
                    <table class="am-table am-table-striped am-table-hover table-main">
                        <tr>
                            <td><b>${uiLabelMap.ProductProductId}</b></td>
                            <td><b>${uiLabelMap.ProductName}</b></td>
                            <td><b>${uiLabelMap.CommonFromDateTime}</b></td>
                            <td><b>${uiLabelMap.CommonThruDateTime}</b></td>
                            <td><b>${uiLabelMap.ProductSeqNum}</b></td>
                            <td><b>${uiLabelMap.CommonQuantity}</b></td>
                            <td><b>${uiLabelMap.ProductAssociationType}</b></td>
                            <td><b>&nbsp;</b></td>
                            <td><b>&nbsp;</b></td>
                        </tr>
                    <#assign rowClass = "2">
                    <#list assocFromProducts as assocFromProduct>
                        <#assign listToProduct = assocFromProduct.getRelatedOneCache("AssocProduct")>
                        <#assign curProductAssocType = assocFromProduct.getRelatedOneCache("ProductAssocType")>
                        <tr valign="middle"<#if rowClass == "1"> class="am-active"</#if>>
                            <td>
                                <a href="<@ofbizUrl>EditProduct?productId=${(assocFromProduct.productIdTo)?if_exists}</@ofbizUrl>"
                                   class="am-btn am-btn-primary am-btn-ms am-round">${(assocFromProduct.productIdTo)?if_exists}</a></td>
                            <td><#if listToProduct?exists><a
                                    href="<@ofbizUrl>EditProduct?productId=${(assocFromProduct.productIdTo)?if_exists}</@ofbizUrl>"
                                    class="am-btn am-btn-primary am-btn-ms am-round">${(listToProduct.internalName)?if_exists}</a></#if>&nbsp;</td>
                            <td <#if (assocFromProduct.getTimestamp("fromDate"))?exists && nowDate.before(assocFromProduct.getTimestamp("fromDate"))>
                                    style="color: red;"</#if>>
                            ${(assocFromProduct.fromDate)?if_exists}&nbsp;</td>
                            <td <#if (assocFromProduct.getTimestamp("thruDate"))?exists && nowDate.after(assocFromProduct.getTimestamp("thruDate"))>
                                    style="color: red;"</#if>>
                            ${(assocFromProduct.thruDate)?if_exists}&nbsp;</td>
                            <td>&nbsp;${(assocFromProduct.sequenceNum)?if_exists}</td>
                            <td>&nbsp;${(assocFromProduct.quantity)?if_exists}</td>
                            <td><#if curProductAssocType?exists> ${(curProductAssocType.get("description",locale))?if_exists}<#else>${(assocFromProduct.productAssocTypeId)?if_exists}</#if></td>
                            <td>
                                <a href="<@ofbizUrl>UpdateProductAssoc?UPDATE_MODE=DELETE&amp;productId=${productId}&amp;PRODUCT_ID=${productId}&amp;PRODUCT_ID_TO=${(assocFromProduct.productIdTo)?if_exists}&amp;PRODUCT_ASSOC_TYPE_ID=${(assocFromProduct.productAssocTypeId)?if_exists}&amp;FROM_DATE=${(assocFromProduct.fromDate)?if_exists}&amp;useValues=true</@ofbizUrl>"
                                   class="am-btn am-btn-primary am-btn-sm">
                                ${uiLabelMap.CommonDelete}</a>
                            </td>
                            <td>
                                <a href="<@ofbizUrl>EditProductAssoc?productId=${productId}&amp;PRODUCT_ID=${productId}&amp;PRODUCT_ID_TO=${(assocFromProduct.productIdTo)?if_exists}&amp;PRODUCT_ASSOC_TYPE_ID=${(assocFromProduct.productAssocTypeId)?if_exists}&amp;FROM_DATE=${(assocFromProduct.fromDate)?if_exists}&amp;useValues=true</@ofbizUrl>"
                                   class="am-btn am-btn-primary am-btn-sm">
                                ${uiLabelMap.CommonEdit}</a>
                            </td>
                        </tr>
                        <#if rowClass == "2">
                            <#assign rowClass = "1">
                        <#else>
                            <#assign rowClass = "2">
                        </#if>
                    </#list>
                    </table>
                </div>
            </div>
        </div>
    </div>
</div>


<div class="am-cf am-padding-xs">
    <div class="am-panel am-panel-default">
        <div class="am-panel-hd am-cf">
        ${uiLabelMap.ProductAssociationsToProduct}
        </div>
        <div class="am-panel-bd am-collapse am-in">
            <div class="am-g">
                <div class="am-u-lg-12">
                    <table class="am-table am-table-striped am-table-hover table-main">
                        <tr>
                            <td><b>${uiLabelMap.ProductProductId}</b></td>
                            <td><b>${uiLabelMap.ProductName}</b></td>
                            <td><b>${uiLabelMap.CommonFromDateTime}</b></td>
                            <td><b>${uiLabelMap.CommonThruDateTime}</b></td>
                            <td><b>${uiLabelMap.ProductAssociationType}</b></td>
                            <td><b>&nbsp;</b></td>
                        </tr>
                    <#assign rowClass = "2">
                    <#list assocToProducts as assocToProduct>
                        <#assign listToProduct = assocToProduct.getRelatedOneCache("MainProduct")>
                        <#assign curProductAssocType = assocToProduct.getRelatedOneCache("ProductAssocType")>
                        <tr valign="middle"<#if rowClass == "1"> class="am-active"</#if>>
                            <td><a href="<@ofbizUrl>EditProduct?productId=${(assocToProduct.productId)?if_exists}</@ofbizUrl>" class="am-btn am-btn-primary am-btn-ms am-round">${(assocToProduct.productId)?if_exists}</a></td>
                            <td><#if listToProduct?exists><a href="<@ofbizUrl>EditProduct?productId=${(assocToProduct.productId)?if_exists}</@ofbizUrl>" class="am-btn am-btn-primary am-btn-ms am-round">${(listToProduct.internalName)?if_exists}</a></#if></td>
                            <td>${(assocToProduct.getTimestamp("fromDate"))?if_exists}&nbsp;</td>
                            <td>${(assocToProduct.getTimestamp("thruDate"))?if_exists}&nbsp;</td>
                            <td><#if curProductAssocType?exists> ${(curProductAssocType.get("description",locale))?if_exists}<#else> ${(assocToProduct.productAssocTypeId)?if_exists}</#if></td>
                            <td>
                                <a href="<@ofbizUrl>UpdateProductAssoc?UPDATE_MODE=DELETE&amp;productId=${(assocToProduct.productIdTo)?if_exists}&amp;PRODUCT_ID=${(assocToProduct.productId)?if_exists}&amp;PRODUCT_ID_TO=${(assocToProduct.productIdTo)?if_exists}&amp;PRODUCT_ASSOC_TYPE_ID=${(assocToProduct.productAssocTypeId)?if_exists}&amp;FROM_DATE=${(assocToProduct.fromDate)?if_exists}&amp;useValues=true</@ofbizUrl>" class="am-btn am-btn-primary am-btn-sm">
                                ${uiLabelMap.CommonDelete}</a>
                            </td>
                        </tr>
                        <#if rowClass == "2">
                            <#assign rowClass = "1">
                        <#else>
                            <#assign rowClass = "2">
                        </#if>
                    </#list>
                    </table>
                </div>
            </div>
        </div>
    </div>
</div>