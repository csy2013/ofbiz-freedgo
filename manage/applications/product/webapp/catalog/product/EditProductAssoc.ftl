<#--<@htmlScreenTemplate.renderScreenletBegin id="PageTitleEditProductAssociations" title="${uiLabelMap.PageTitleEditProductAssociations}" />
<form action="<@ofbizUrl>UpdateProductAssoc</@ofbizUrl>" method="post" style="margin: 0;" name="editProductAssocForm">
    <input type="hidden" name="productId" value="${productId?if_exists}"/>
<#if !(productAssoc?exists)>
    <#if productId?exists && productIdTo?exists && productAssocTypeId?exists && fromDate?exists>
        <div><b><#assign uiLabelWithVar=uiLabelMap.ProductAssociationNotFound?interpret><@uiLabelWithVar/></b></div>
        <input type="hidden" name="UPDATE_MODE" value="CREATE"/>
    <table cellspacing="0" class="table table-border table-responsive">
        <tr>
            <td align="right" class="label">${uiLabelMap.ProductProductId}</td>
            <td>&nbsp;</td>
            <td><input type="text" name="PRODUCT_ID" size="20" maxlength="40" value="${productId?if_exists}"/></td>
        </tr>
        <tr>
            <td align="right" class="label">${uiLabelMap.ProductProductIdTo}</td>
            <td>&nbsp;</td>
            <td><input type="text" name="PRODUCT_ID_TO" size="20" maxlength="40" value="${productIdTo?if_exists}"/></td>
        </tr>
        <tr>
            <td align="right" class="label">${uiLabelMap.ProductAssociationTypeId}</td>
            <td>&nbsp;</td>
            <td>
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
            </td>
        </tr>
        <tr>
            <td align="right" class="label">${uiLabelMap.CommonFromDate}</td>
            <td>&nbsp;</td>
            <td>
                <div>
                    <@htmlTemplate.renderDateTimeField name="FROM_DATE" event="" action="" value="${fromDate?if_exists}" className="" alert="" title="Format: yyyy-MM-dd HH:mm:ss.SSS" size="25" maxlength="30" id="fromDate1" dateType="date" shortDateInput=false timeDropdownParamName="" defaultDateTimeString="" localizedIconTitle="" timeDropdown="" timeHourName="" classString="" hour1="" hour2="" timeMinutesName="" minutes="" isTwelveHour="" ampmName="" amSelected="" pmSelected="" compositeType="" formName=""/>
                        ${uiLabelMap.CommonSetNowEmpty}
                </div>
            </td>
        </tr>
    <#else>
        <input type="hidden" name="UPDATE_MODE" value="CREATE"/>
    <table cellspacing="0" class="basic-table">
        <tr>
            <td align="right" class="label">${uiLabelMap.ProductProductId}</td>
            <td>&nbsp;</td>
            <td><input type="text" name="PRODUCT_ID" size="20" maxlength="40" value="${productId?if_exists}"/></td>
        </tr>
        <tr>
            <td align="right" class="label">${uiLabelMap.ProductProductIdTo}</td>
            <td>&nbsp;</td>
            <td>
                <@htmlTemplate.lookupField formName="editProductAssocForm" name="PRODUCT_ID_TO" id="PRODUCT_ID_TO" fieldFormName="LookupProduct"/>
            </td>
        </tr>
        <tr>
            <td align="right" class="label">${uiLabelMap.ProductAssociationTypeId}</td>
            <td>&nbsp;</td>
            <td>
                <select name="PRODUCT_ASSOC_TYPE_ID" size="1">
                    <!-- <option value="">&nbsp;</option> &ndash;&gt;
                    <#list assocTypes as assocType>
                        <#if assocType.productAssocTypeId=='ALSO_BOUGHT'||assocType.productAssocTypeId='PRODUCT_COMPLEMENT'||assocType.productAssocTypeId='PRODUCT_VARIANT'||assocType.productAssocTypeId='PRODUCT_UPGRADE'>

                            <option value="${(assocType.productAssocTypeId)?if_exists}">${(assocType.get("description",locale))?if_exists}</option>
                        </#if>
                    </#list>
                </select>
            </td>
        </tr>
        <tr>
            <td align="right" class="label">${uiLabelMap.CommonFromDate}</td>
            <td>&nbsp;</td>
            <td>
                <div>
                ${fromDate?if_exists}
                        <@htmlTemplate.renderDateTimeField name="FROM_DATE" event="" action="" value="${fromDate?if_exists}" className="" alert="" title="Format: yyyy-MM-dd HH:mm:ss.SSS" size="25" maxlength="30" id="fromDate2" dateType="date" shortDateInput=false timeDropdownParamName="" defaultDateTimeString="" localizedIconTitle="" timeDropdown="" timeHourName="" classString="" hour1="" hour2="" timeMinutesName="" minutes="" isTwelveHour="" ampmName="" amSelected="" pmSelected="" compositeType="" formName=""/>
                        ${uiLabelMap.CommonSetNowEmpty}
                </div>
            </td>
        </tr>
    </#if>
<#else>
    <#assign isCreate = false>
    <#assign curProductAssocType = productAssoc.getRelatedOneCache("ProductAssocType")>
    <input type="hidden" name="UPDATE_MODE" value="UPDATE"/>
    <input type="hidden" name="PRODUCT_ID" value="${productId?if_exists}"/>
    <input type="hidden" name="PRODUCT_ID_TO" value="${productIdTo?if_exists}"/>
    <input type="hidden" name="PRODUCT_ASSOC_TYPE_ID" value="${productAssocTypeId?if_exists}"/>
    <input type="hidden" name="FROM_DATE" value="${fromDate?if_exists}"/>
<table cellspacing="0" class="basic-table">
    <tr>
        <td align="right" class="label">${uiLabelMap.ProductProductId}</td>
        <td>&nbsp;</td>
        <td><b>${productId?if_exists}</b> ${uiLabelMap.ProductRecreateAssociation}</td>
    </tr>
    <tr>
        <td align="right" class="label">${uiLabelMap.ProductProductIdTo}</td>
        <td>&nbsp;</td>
        <td><b>${productIdTo?if_exists}</b> ${uiLabelMap.ProductRecreateAssociation}</td>
    </tr>
    <tr>
        <td align="right" class="label">${uiLabelMap.ProductAssociationType}</td>
        <td>&nbsp;</td>
        <td>
            <b><#if curProductAssocType?exists>${(curProductAssocType.get("description",locale))?if_exists}<#else> ${productAssocTypeId?if_exists}</#if></b> ${uiLabelMap.ProductRecreateAssociation}
        </td>
    </tr>
    <tr>
        <td align="right" class="label">${uiLabelMap.CommonFromDate}</td>
        <td>&nbsp;</td>
        <td><b>${fromDate?if_exists}</b> ${uiLabelMap.ProductRecreateAssociation}</td>
    </tr>
</#if>
    <tr>
        <td width="26%" align="right" class="label">${uiLabelMap.CommonThruDate}</td>
        <td>&nbsp;</td>
        <td width="74%">
            <div>
            <#if useValues>
                <#assign value = productAssoc.thruDate?if_exists>
            <#else>
                <#assign value = (request.getParameter("THRU_DATE"))?if_exists>
            </#if>
                <@htmlTemplate.renderDateTimeField name="THRU_DATE" event="" action="" value="${value}" className="" alert="" title="Format: yyyy-MM-dd HH:mm:ss.SSS" size="25" maxlength="30" id="thruDate1" dateType="date" shortDateInput=false timeDropdownParamName="" defaultDateTimeString="" localizedIconTitle="" timeDropdown="" timeHourName="" classString="" hour1="" hour2="" timeMinutesName="" minutes="" isTwelveHour="" ampmName="" amSelected="" pmSelected="" compositeType="" formName=""/>
            </div>
            </div>
        </td>
    </tr>
    <tr>
        <td width="26%" align="right" class="label">${uiLabelMap.ProductSequenceNum}</td>
        <td>&nbsp;</td>
        <td width="74%"><input type="text" name="SEQUENCE_NUM" <#if useValues>value="${(productAssoc.sequenceNum)?if_exists}"
                               <#else>value="${(request.getParameter("SEQUENCE_NUM"))?if_exists}"</#if> size="5" maxlength="10"/></td>
    </tr>
    <tr>
        <td width="26%" align="right" class="label">${uiLabelMap.ProductReason}</td>
        <td>&nbsp;</td>
        <td width="74%"><input type="text" name="REASON" <#if useValues>value="${(productAssoc.reason)?if_exists}"<#else>value="${(request.getParameter("REASON"))?if_exists}"</#if> size="60"
                               maxlength="255"/></td>
    </tr>
    <tr>
        <td width="26%" align="right" class="label">${uiLabelMap.ProductInstruction}</td>
        <td>&nbsp;</td>
        <td width="74%"><input type="text" name="INSTRUCTION" <#if useValues>value="${(productAssoc.instruction)?if_exists}"
                               <#else>value="${(request.getParameter("INSTRUCTION"))?if_exists}"</#if> size="60" maxlength="255"/></td>
    </tr>

    <tr>
        <td width="26%" align="right" class="label">${uiLabelMap.ProductQuantity}</td>
        <td>&nbsp;</td>
        <td width="74%"><input type="text" name="QUANTITY" <#if useValues>value="${(productAssoc.quantity)?if_exists}"<#else>value="${(request.getParameter("QUANTITY"))?if_exists}"</#if>
                               size="10" maxlength="15"/></td>
    </tr>

    <tr>
        <td colspan="2">&nbsp;</td>
        <td><input type="submit" <#if isCreate>value="${uiLabelMap.CommonCreate}"<#else>value="${uiLabelMap.CommonUpdate}"</#if>/></td>
    </tr>
</table>
</form>
<@htmlScreenTemplate.renderScreenletEnd/>-->
<#if productId?exists && product?exists>

    <@htmlScreenTemplate.renderScreenletBegin id="ProductAssociationsFromProduct" title="${uiLabelMap.ProductAssociationsFromProduct}" />

    <@htmlTemplate.renderModalPage id="pre_UpdateProductAssoc" name="pre_UpdateProductAssoc"
    modalTitle="${StringUtil.wrapString(uiLabelMap.PageTitleEditProductAssociations)}" description="${uiLabelMap.PageTitleEditProductAssociations}"
    modalUrl="/catalog/control/pre_UpdateProductAssoc"
    targetParameterIter="productId:'${productId?if_exists}'"/>
<hr/>

<table cellspacing="0" class="table table-border table-responsive">
    <tr class="header-row">
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
        <tr valign="middle"<#if rowClass == "1"> class="alternate-row"</#if>>
            <td><a href="<@ofbizUrl>EditProduct?productId=${(assocFromProduct.productIdTo)?if_exists}</@ofbizUrl>" class="buttontext">${(assocFromProduct.productIdTo)?if_exists}</a></td>
            <td><#if listToProduct?exists><a href="<@ofbizUrl>EditProduct?productId=${(assocFromProduct.productIdTo)?if_exists}</@ofbizUrl>"
                                             class="buttontext">${(listToProduct.internalName)?if_exists}</a></#if>&nbsp;</td>
            <td <#if (assocFromProduct.getTimestamp("fromDate"))?exists && nowDate.before(assocFromProduct.getTimestamp("fromDate"))> style="color: red;"</#if>>
            ${(assocFromProduct.fromDate)?if_exists}&nbsp;</td>
            <td <#if (assocFromProduct.getTimestamp("thruDate"))?exists && nowDate.after(assocFromProduct.getTimestamp("thruDate"))> style="color: red;"</#if>>
            ${(assocFromProduct.thruDate)?if_exists}&nbsp;</td>
            <td>&nbsp;${(assocFromProduct.sequenceNum)?if_exists}</td>
            <td>&nbsp;${(assocFromProduct.quantity)?if_exists}</td>
            <td><#if curProductAssocType?exists> ${(curProductAssocType.get("description",locale))?if_exists}<#else>${(assocFromProduct.productAssocTypeId)?if_exists}</#if></td>
            <td>
               <#-- <a href="<@ofbizUrl>UpdateProductAssoc?UPDATE_MODE=DELETE&amp;productId=${productId}&amp;PRODUCT_ID=${productId}&amp;PRODUCT_ID_TO=${(assocFromProduct.productIdTo)?if_exists}&amp;PRODUCT_ASSOC_TYPE_ID=${(assocFromProduct.productAssocTypeId)?if_exists}&amp;FROM_DATE=${(assocFromProduct.fromDate)?if_exists}&amp;useValues=true</@ofbizUrl>"
                   class="buttontext">
                ${uiLabelMap.CommonDelete}</a>-->

               <@htmlScreenTemplate.renderConfirmField id="UpdateProductAssoc${assocFromProduct_index}" description="${uiLabelMap.CommonDelete}" name="UpdateProductAssoc${assocFromProduct_index}" confirmTitle="${uiLabelMap.CommonDelete}"
               confirmUrl="UpdateProductAssoc" confirmMessage="确定删除该记录?"
                targetParameterIter="UPDATE_MODE:'DELETE',productId:'${productId}',PRODUCT_ID:'${productId}',PRODUCT_ID_TO:'${(assocFromProduct.productIdTo)?if_exists}',PRODUCT_ASSOC_TYPE_ID:'${(assocFromProduct.productAssocTypeId)?if_exists}',FROM_DATE:'${(assocFromProduct.fromDate)?if_exists}',useValues:'true'"/>
            </td>
            <td>
              <#--  <a href="<@ofbizUrl>EditProductAssoc?productId=${productId}&amp;PRODUCT_ID=${productId}&amp;PRODUCT_ID_TO=${(assocFromProduct.productIdTo)?if_exists}&amp;PRODUCT_ASSOC_TYPE_ID=${(assocFromProduct.productAssocTypeId)?if_exists}&amp;FROM_DATE=${(assocFromProduct.fromDate)?if_exists}&amp;useValues=true</@ofbizUrl>"
                   class="buttontext">
                ${uiLabelMap.CommonEdit}-->
                  <#--</a>-->
            <@htmlScreenTemplate.renderModalPage id="pre_UpdateProductAssoc${assocFromProduct_index}" description="${uiLabelMap.CommonEdit}" name="pre_UpdateProductAssoc${assocFromProduct_index}" modalTitle="${StringUtil.wrapString(uiLabelMap.CommonEdit)}"
                modalUrl="pre_UpdateProductAssoc"
                targetParameterIter="productId:'${productId}',PRODUCT_ID:'${productId}',PRODUCT_ID_TO:'${(assocFromProduct.productIdTo)?if_exists}',PRODUCT_ASSOC_TYPE_ID:'${(assocFromProduct.productAssocTypeId)?if_exists}',FROM_DATE:'${(assocFromProduct.fromDate)?if_exists}',useValues:'true'"/>
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
    <@htmlScreenTemplate.renderScreenletEnd/>

    <@htmlScreenTemplate.renderScreenletBegin id="ProductAssociationsToProduct" title="${uiLabelMap.ProductAssociationsToProduct}" />
<table cellspacing="0" class="table table-border table-responsive">
    <tr class="header-row">
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
        <tr valign="middle"<#if rowClass == "1"> class="alternate-row"</#if>>
            <td><a href="<@ofbizUrl>EditProduct?productId=${(assocToProduct.productId)?if_exists}</@ofbizUrl>" class="buttontext">${(assocToProduct.productId)?if_exists}</a></td>
            <td><#if listToProduct?exists><a href="<@ofbizUrl>EditProduct?productId=${(assocToProduct.productId)?if_exists}</@ofbizUrl>"
                                             class="buttontext">${(listToProduct.internalName)?if_exists}</a></#if></td>
            <td>${(assocToProduct.getTimestamp("fromDate"))?if_exists}&nbsp;</td>
            <td>${(assocToProduct.getTimestamp("thruDate"))?if_exists}&nbsp;</td>
            <td><#if curProductAssocType?exists> ${(curProductAssocType.get("description",locale))?if_exists}<#else> ${(assocToProduct.productAssocTypeId)?if_exists}</#if></td>
            <td>
                <a href="<@ofbizUrl>UpdateProductAssoc?UPDATE_MODE=DELETE&amp;productId=${(assocToProduct.productIdTo)?if_exists}&amp;PRODUCT_ID=${(assocToProduct.productId)?if_exists}&amp;PRODUCT_ID_TO=${(assocToProduct.productIdTo)?if_exists}&amp;PRODUCT_ASSOC_TYPE_ID=${(assocToProduct.productAssocTypeId)?if_exists}&amp;FROM_DATE=${(assocToProduct.fromDate)?if_exists}&amp;useValues=true</@ofbizUrl>"
                   class="buttontext">
                ${uiLabelMap.CommonDelete}</a>
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
    <@htmlScreenTemplate.renderScreenletEnd/>
</#if>
<br/>
<span class="tooltip">${uiLabelMap.CommonNote} : ${uiLabelMap.ProductHighlightedExplanation}</span>