<form action="<@ofbizUrl>UpdateProductAssoc</@ofbizUrl>" method="post" class="form-horizontal" name="editProductAssocForm">
    <input type="hidden" name="productId" value="${productId?if_exists}"/>
<#if !(productAssoc?exists)>
    <#if productId?exists && productIdTo?exists && productAssocTypeId?exists && fromDate?exists>
        <div><b><#assign uiLabelWithVar=uiLabelMap.ProductAssociationNotFound?interpret><@uiLabelWithVar/></b></div>
        <input type="hidden" name="UPDATE_MODE" value="CREATE"/>

        <div class="form-group">
            <label class="control-label col-md-3">${uiLabelMap.ProductProductId}</label>
            <div class="col-md-5"><input type="text"  class="form-control"name="PRODUCT_ID" size="20" maxlength="40" value="${productId?if_exists}"/>
            </div>
        </div>
        <div class="form-group">
            <label class="control-label col-md-3">${uiLabelMap.ProductProductIdTo}</label>

            <div class="col-md-5"><input type="text"  class="form-control"name="PRODUCT_ID_TO" size="20" maxlength="40" value="${productIdTo?if_exists}"/>
            </div>
        </div>
        <div class="form-group">
            <label class="control-label col-md-3">${uiLabelMap.ProductAssociationTypeId}</label>

            <div class="col-md-5">
                <select name="PRODUCT_ASSOC_TYPE_ID" size="1">
                    <#if productAssocTypeId?has_content>
                        <#assign curAssocType = delegator.findByPrimaryKey("ProductAssocType", Static["org.ofbiz.base.util.UtilMisc"].toMap("productAssocTypeId", productAssocTypeId))>
                        <#if curAssocType?exists>
                            <option selected="selected" value="${(curAssocType.productAssocTypeId)?if_exists}">${(curAssocType.get("description",locale))?if_exists}</option>
                            <option value="${(curAssocType.productAssocTypeId)?if_exists}"></option>
                        </#if>
                    </#if>
                    <#list assocTypes as assocType>
                        <#if assocType.productAssocTypeId=='ALSO_BOUGHT'||assocType.productAssocTypeId='PRODUCT_COMPLEMENT'||assocType.productAssocTypeId='PRODUCT_VARIANT'||assocType.productAssocTypeId='PRODUCT_UPGRADE'>
                            <option value="${(assocType.productAssocTypeId)?if_exists}">${(assocType.get("description",locale))?if_exists}</option>
                        </#if>
                    </#list>
                </select>
            </div>
        </div>
        <div class="form-group">
            <label class="control-label col-md-3">${uiLabelMap.CommonFromDate}</label>

            <div class="col-md-5">

                <@htmlTemplate.renderDateTimeField name="FROM_DATE" event="" action="" value="${fromDate?if_exists}" className="" alert="" title="Format: yyyy-MM-dd HH:mm:ss.SSS" size="25" maxlength="30" id="fromDate1" dateType="date" shortDateInput=false timeDropdownParamName="" defaultDateTimeString="" localizedIconTitle="" timeDropdown="" timeHourName="" classString="" hour1="" hour2="" timeMinutesName="" minutes="" isTwelveHour="" ampmName="" amSelected="" pmSelected="" compositeType="" formName=""/>

            </div>
        </div>
    <#else>
        <input type="hidden" name="UPDATE_MODE" value="CREATE"/>

        <div class="form-group">
            <label class="control-label col-md-3">${uiLabelMap.ProductProductId}</label>

            <div class="col-md-5"><input type="text"  class="form-control"name="PRODUCT_ID" size="20" maxlength="40" value="${productId?if_exists}"/></div>
        </div>
        <div class="form-group">
            <label class="control-label col-md-3">${uiLabelMap.ProductProductIdTo}</label>

            <div class="col-md-5">
                <@htmlTemplate.lookupField formName="editProductAssocForm" name="PRODUCT_ID_TO" id="PRODUCT_ID_TO" fieldFormName="LookupProduct"/>

            </div>
        </div>
        <div class="form-group">
            <label class="control-label col-md-3">${uiLabelMap.ProductAssociationTypeId}</label>

            <div class="col-md-5">
                <select name="PRODUCT_ASSOC_TYPE_ID" size="1">
                    <!-- <option value="">&nbsp;</option> -->
                    <#list assocTypes as assocType>
                        <#if assocType.productAssocTypeId=='ALSO_BOUGHT'||assocType.productAssocTypeId='PRODUCT_COMPLEMENT'||assocType.productAssocTypeId='PRODUCT_VARIANT'||assocType.productAssocTypeId='PRODUCT_UPGRADE'>

                            <option value="${(assocType.productAssocTypeId)?if_exists}">${(assocType.get("description",locale))?if_exists}</option>
                        </#if>
                    </#list>
                </select>

            </div>
        </div>
        <div class="form-group">
            <label class="control-label col-md-3">${uiLabelMap.CommonFromDate}</label>

            <div class="col-md-5">
                <div>
                ${fromDate?if_exists}
                        <@htmlTemplate.renderDateTimeField name="FROM_DATE" event="" action="" value="${fromDate?if_exists}" className="" alert="" title="Format: yyyy-MM-dd HH:mm:ss.SSS" size="25" maxlength="30" id="fromDate2" dateType="date" shortDateInput=false timeDropdownParamName="" defaultDateTimeString="" localizedIconTitle="" timeDropdown="" timeHourName="" classString="" hour1="" hour2="" timeMinutesName="" minutes="" isTwelveHour="" ampmName="" amSelected="" pmSelected="" compositeType="" formName=""/>

                </div>

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
<table cellspacing="0" class="table table-border">
    <div class="form-group">
        <label class="control-label col-md-3">${uiLabelMap.ProductProductId}</label>

        <div class="col-md-5"><b>${productId?if_exists}</b> ${uiLabelMap.ProductRecreateAssociation}</label>
        </div>
    </div>
    <div class="form-group">
        <label class="control-label col-md-3">${uiLabelMap.ProductProductIdTo}</label>

        <div class="col-md-5"><b>${productIdTo?if_exists}</b> ${uiLabelMap.ProductRecreateAssociation}</label>
        </div>
    </div>
    <div class="form-group">
        <label class="control-label col-md-3">${uiLabelMap.ProductAssociationType}</label>

        <div class="col-md-5">
            <b><#if curProductAssocType?exists>${(curProductAssocType.get("description",locale))?if_exists}<#else> ${productAssocTypeId?if_exists}</#if></b> ${uiLabelMap.ProductRecreateAssociation}
        </div>
    </div>
    <div class="form-group">
        <label class="control-label col-md-3">${uiLabelMap.CommonFromDate}</label>

        <div class="col-md-5"><b>${fromDate?if_exists}</b> ${uiLabelMap.ProductRecreateAssociation}</label>
        </div>
    </div>
</#if>
    <div class="form-group">
        <label class="control-label col-md-3">${uiLabelMap.CommonThruDate}</label>

        <div class="col-md-5">
        <#if useValues>
            <#assign value = productAssoc.thruDate?if_exists>
        <#else>
            <#assign value = (request.getParameter("THRU_DATE"))?if_exists>
        </#if>
                <@htmlTemplate.renderDateTimeField name="THRU_DATE" event="" action="" value="${value}" className="" alert="" title="Format: yyyy-MM-dd HH:mm:ss.SSS" size="25" maxlength="30" id="thruDate1" dateType="date" shortDateInput=false timeDropdownParamName="" defaultDateTimeString="" localizedIconTitle="" timeDropdown="" timeHourName="" classString="" hour1="" hour2="" timeMinutesName="" minutes="" isTwelveHour="" ampmName="" amSelected="" pmSelected="" compositeType="" formName=""/>
        </div>

    </div>
    <div class="form-group">
        <label class="control-label col-md-3">${uiLabelMap.ProductSequenceNum}</label>

        <div class="col-md-5"><input type="text"  class="form-control" name="SEQUENCE_NUM" <#if useValues>value="${(productAssoc.sequenceNum)?if_exists}"
                                     <#else>value="${(request.getParameter("SEQUENCE_NUM"))?if_exists}"</#if> size="5" maxlength="10"/>
        </div>
    </div>
    <div class="form-group">
        <label class="control-label col-md-3">${uiLabelMap.ProductReason}</label>

        <div class="col-md-5"><input type="text"  class="form-control"name="REASON" <#if useValues>value="${(productAssoc.reason)?if_exists}"<#else>value="${(request.getParameter("REASON"))?if_exists}"</#if>
                                     size="60"
                                     maxlength="255"/>
        </div>
    </div>
    <div class="form-group">
        <label class="control-label col-md-3">${uiLabelMap.ProductInstruction}</label>

        <div class="col-md-5"><input type="text"  class="form-control"name="INSTRUCTION" <#if useValues>value="${(productAssoc.instruction)?if_exists}"
                                     <#else>value="${(request.getParameter("INSTRUCTION"))?if_exists}"</#if> size="60" maxlength="255"/>
        </div>
    </div>

    <div class="form-group">
        <label class="control-label col-md-3">${uiLabelMap.ProductQuantity}</label>

        <div class="col-md-5"><input type="text"  class="form-control"name="QUANTITY" <#if useValues>value="${(productAssoc.quantity)?if_exists}"
                                     <#else>value="${(request.getParameter("QUANTITY"))?if_exists}"</#if>
                                     size="10" maxlength="15"/>
        </div>
    </div>

    <div class="form-group">
        <div>&nbsp;</div>
        <div><input type="submit" <#if isCreate>value="${uiLabelMap.CommonCreate}"<#else>value="${uiLabelMap.CommonUpdate}"</#if>/></div>
    </div>
</table>
</form>