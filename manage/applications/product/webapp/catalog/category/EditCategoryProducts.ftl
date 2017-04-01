<@htmlScreenTemplate.renderScreenletBegin id="" title="${uiLabelMap.PageTitleEditCategoryProducts}"/>
<#if activeOnly>
<a href="<@ofbizUrl>EditCategoryProducts?productCategoryId=${productCategoryId?if_exists}&amp;activeOnly=false</@ofbizUrl>"
   class="btn btn-primary btn-sm">${uiLabelMap.ProductActiveAndInactive}</a>
<#else>
<a href="<@ofbizUrl>EditCategoryProducts?productCategoryId=${productCategoryId?if_exists}&amp;activeOnly=true</@ofbizUrl>" class="btn btn-primary btn-sm">${uiLabelMap.ProductActiveOnly}</a>
</#if>
<@htmlTemplate.renderModalPage id="pre_addCategoryProductMember" name="pre_addCategoryProductMember"
modalTitle="${StringUtil.wrapString(uiLabelMap.ProductAddProductCategoryMember)}" description="${uiLabelMap.ProductAddProductCategoryMember}"
modalUrl="/catalog/control/pre_addCategoryProductMember"
targetParameterIter="productCategoryId:'${productCategoryId}',activeOnly:'${activeOnly.toString()}'"/>

<@htmlTemplate.renderModalPage id="pre_copyCategoryProductMembers" name="pre_copyCategoryProductMembers"
modalTitle="${StringUtil.wrapString(uiLabelMap.ProductCopyProductCategoryMembersToAnotherCategory)}" description="${uiLabelMap.ProductCopyProductCategoryMembersToAnotherCategory}"
modalUrl="/catalog/control/pre_copyCategoryProductMembers"
targetParameterIter="productCategoryId:'${productCategoryId}',activeOnly:'${activeOnly.toString()}'"/>

<@htmlTemplate.renderModalPage id="pre_expireAllCategoryProductMembers" name="pre_expireAllCategoryProductMembers"
modalTitle="${StringUtil.wrapString(uiLabelMap.ProductExpireAllProductMembers)}" description="${uiLabelMap.ProductExpireAllProductMembers}"
modalUrl="/catalog/control/pre_expireAllCategoryProductMembers"
targetParameterIter="productCategoryId:'${productCategoryId}',activeOnly:'${activeOnly.toString()}'"/>

<@htmlTemplate.renderModalPage id="pre_removeExpiredCategoryProductMembers" name="pre_removeExpiredCategoryProductMembers"
modalTitle="${StringUtil.wrapString(uiLabelMap.ProductRemoveExpiredProductMembers)}" description="${uiLabelMap.ProductRemoveExpiredProductMembers}"
modalUrl="/catalog/control/pre_removeExpiredCategoryProductMembers"
targetParameterIter="productCategoryId:'${productCategoryId}',activeOnly:'${activeOnly.toString()}'"/>

<hr/>
<#if (listSize > 0)>
<div class="pull-right">
    <#if (viewIndex > 1)>
        <a href="<@ofbizUrl>EditCategoryProducts?productCategoryId=${productCategoryId?if_exists}&amp;VIEW_SIZE=${viewSize}&amp;VIEW_INDEX=${viewIndex-1}&amp;activeOnly=${activeOnly.toString()}</@ofbizUrl>"
           class="submenutext">${uiLabelMap.CommonPrevious}</a> |
    </#if>
    <span class="submenutextinfo">${lowIndex} - ${highIndex} ${uiLabelMap.CommonOf} ${listSize}</span>
    <#if (listSize > highIndex)>
        | <a class="lightbuttontext"
             href="<@ofbizUrl>EditCategoryProducts?productCategoryId=${productCategoryId?if_exists}&amp;VIEW_SIZE=${viewSize}&amp;VIEW_INDEX=${viewIndex+1}&amp;activeOnly=${activeOnly.toString()}</@ofbizUrl>"
             class="submenutextright">${uiLabelMap.CommonNext}</a>
    </#if>
    &nbsp;
</div>
</#if>

<#if (listSize == 0)>
<table class="table table-striped table-bordered">
    <tr class="header-row">
        <td>${uiLabelMap.ProductProductNameId}</td>
        <td>${uiLabelMap.CommonFromDateTime}</td>
        <td align="center">${uiLabelMap.ProductThruDateTimeSequenceQuantity} ${uiLabelMap.CommonComments}</td>
        <td>&nbsp;</td>
    </tr>
</table>
<#else>
<form method="post" action="<@ofbizUrl>updateCategoryProductMember</@ofbizUrl>" name="updateCategoryProductForm" class="form-inline">
    <input type="hidden" name="VIEW_SIZE" value="${viewSize}"/>
    <input type="hidden" name="VIEW_INDEX" value="${viewIndex}"/>
    <input type="hidden" name="activeOnly" value="${activeOnly.toString()}"/>
    <input type="hidden" name="productCategoryId" value="${productCategoryId?if_exists}"/>
    <table  class="table table-striped table-bordered">
        <thead>
        <tr class="header-row">
            <th>${uiLabelMap.ProductProductNameId}</th>
            <th>开始时间</th>
            <th>结束时间-顺序-数量-评论</th>
            <th>删除</th>
        </tr>
        </thead>

        <#assign rowCount = 0>
        <#list productCategoryMembers as productCategoryMember>
            <#assign suffix = "_o_" + productCategoryMember_index>
            <#assign product = productCategoryMember.getRelatedOne("Product")>
            <#assign hasntStarted = false>
            <#if productCategoryMember.fromDate?exists && nowTimestamp.before(productCategoryMember.getTimestamp("fromDate"))><#assign hasntStarted = true></#if>
            <#assign hasExpired = false>
            <#if productCategoryMember.thruDate?exists && nowTimestamp.after(productCategoryMember.getTimestamp("thruDate"))><#assign hasExpired = true></#if>
            <tr>
                <td>
                    <#if (product.smallImageUrl)?exists>
                        <a href="<@ofbizUrl>EditProduct?productId=${(productCategoryMember.productId)?if_exists}</@ofbizUrl>">
                            <img alt="Small Image" src="<@ofbizContentUrl>${product.smallImageUrl}</@ofbizContentUrl>" class="cssImgSmall" align="middle"/>
                        </a>
                    </#if>
                    <a href="<@ofbizUrl>EditProduct?productId=${(productCategoryMember.productId)?if_exists}</@ofbizUrl>"
                       class=""><#--<#if product?exists>${(product.internalName)?if_exists}</#if>--> [${(productCategoryMember.productId)?if_exists}]</a>
                </td>
                <td <#if hasntStarted> style="color: red;"</#if>>${(productCategoryMember.fromDate)?if_exists?string("yyyy-MM-dd HH:mm")}</td>
                <td>

                    <input type="hidden" name="productId${suffix}" value="${(productCategoryMember.productId)?if_exists}"/>
                    <input type="hidden" name="productCategoryId${suffix}" value="${(productCategoryMember.productCategoryId)?if_exists}"/>
                    <input type="hidden" name="fromDate${suffix}" value="${(productCategoryMember.fromDate)?if_exists}"/>
                    <#if hasExpired><#assign class="alert"></#if>
                    <@htmlTemplate.renderDateTimeField name="thruDate${suffix}" event="" action="" className="${class!''}" alert="" title="Format: yyyy-MM-dd HH:mm:ss.SSS" value="${(productCategoryMember.thruDate)?if_exists}" size="25" maxlength="30" id="thruDate${suffix}" dateType="date" shortDateInput=false timeDropdownParamName="" defaultDateTimeString="" localizedIconTitle="" timeDropdown="" timeHourName="" classString="" hour1="" hour2="" timeMinutesName="" minutes="" isTwelveHour="" ampmName="" amSelected="" pmSelected="" compositeType="" formName=""/>

                    <input type="text" size="2" name="sequenceNum${suffix}" class="form-control" value="${(productCategoryMember.sequenceNum)?if_exists}"/>
                    <input type="text" size="2" class="form-control" name="quantity${suffix}" value="${(productCategoryMember.quantity)?if_exists}"/>

                    <textarea class="form-control" name="comments${suffix}" rows="2" cols="40">${(productCategoryMember.comments)?if_exists}</textarea>

                    <input type="submit" value="${uiLabelMap.CommonUpdate}" class="btn btn-primary btn-sm"/>

                </td>
                <td align="center">
                    <a href="javascript:document.deleteProductFromCategory_o_${rowCount}.submit()" onclick="return confirm('确定删除该记录？');"
                       class="btn btn-primary btn-sm">${uiLabelMap.CommonDelete}</a>
                </td>
            </tr>
            <input type="hidden" value="${productCategoryMembers.size()}" name="_rowCount"/>
            <#assign rowCount = rowCount + 1>
        </#list>
    </table>
</form>
    <#assign rowCount = 0>
    <#list productCategoryMembers as productCategoryMember>
    <form name="deleteProductFromCategory_o_${rowCount}" method="post" action="<@ofbizUrl>removeCategoryProductMember</@ofbizUrl> ">
        <input type="hidden" name="VIEW_SIZE" value="${viewSize}"/>
        <input type="hidden" name="VIEW_INDEX" value="${viewIndex}"/>
        <input type="hidden" name="productId" value="${(productCategoryMember.productId)?if_exists}"/>
        <input type="hidden" name="productCategoryId" value="${(productCategoryMember.productCategoryId)?if_exists}"/>
        <input type="hidden" name="fromDate" value="${(productCategoryMember.fromDate)?if_exists}"/>
        <input type="hidden" name="activeOnly" value="${activeOnly.toString()}"/>
    </form>
        <#assign rowCount = rowCount + 1>
    </#list>
</#if>

<#if (listSize > 0)>
<div class="pull-right">
    <#if (viewIndex > 1)>
        <a href="<@ofbizUrl>EditCategoryProducts?productCategoryId=${productCategoryId?if_exists}&amp;VIEW_SIZE=${viewSize}&amp;VIEW_INDEX=${viewIndex-1}&amp;activeOnly=${activeOnly.toString()}</@ofbizUrl>"
           class="submenutext">${uiLabelMap.CommonPrevious}</a> |
    </#if>
    <span class="submenutextinfo">${lowIndex} - ${highIndex} ${uiLabelMap.CommonOf} ${listSize}</span>
    <#if (listSize > highIndex)>
        | <a class="lightbuttontext"
             href="<@ofbizUrl>EditCategoryProducts?productCategoryId=${productCategoryId?if_exists}&amp;VIEW_SIZE=${viewSize}&amp;VIEW_INDEX=${viewIndex+1}&amp;activeOnly=${activeOnly.toString()}</@ofbizUrl>"
             class="submenutextright">${uiLabelMap.CommonNext}</a>
    </#if>
    &nbsp;
</div>

</#if>
<@htmlScreenTemplate.renderScreenletEnd/>
<#--<@htmlScreenTemplate.renderScreenletBegin id="" title="${uiLabelMap.ProductAddProductCategoryMember}"/>

<form method="post" action="<@ofbizUrl>addCategoryProductMember</@ofbizUrl>" class="form-horizontal" name="addProductCategoryMemberForm">
    <input type="hidden" name="productCategoryId" value="${productCategoryId?if_exists}"/>
    <input type="hidden" name="activeOnly" value="${activeOnly.toString()}"/>

    <div class="form-group">
        <label class="control-label col-md-3">${uiLabelMap.ProductProductId}</label>

        <div class="col-md-5 ">
        <@htmlTemplate.lookupField formName="addProductCategoryMemberForm" name="productId" id="productId" fieldFormName="LookupProduct"/>
        </div>
    </div>
    <div class="form-group">
        <label class="control-label col-md-3">${uiLabelMap.CommonFromDate}</label>

        <div class="col-md-5 ">
        <@htmlTemplate.renderDateTimeField name="fromDate" event="" action="" className="" alert="" title="Format: yyyy-MM-dd HH:mm:ss.SSS" value="" size="25" maxlength="30" id="fromDate1" dateType="date" shortDateInput=false timeDropdownParamName="" defaultDateTimeString="" localizedIconTitle="" timeDropdown="" timeHourName="" classString="" hour1="" hour2="" timeMinutesName="" minutes="" isTwelveHour="" ampmName="" amSelected="" pmSelected="" compositeType="" formName=""/>
        </div>
    </div>
    <div class="form-group">
        <label class="control-label col-md-3">${uiLabelMap.CommonComments}</label>

        <div class="col-md-5 ">
            <textarea name="comments" rows="2" cols="40" class="form-control"></textarea>
        </div>
    </div>
    <div class="form-group">
        <div class="col-md-3">&nbsp;</div>
        <div class="col-md-5  pull-right">
            <input type="submit" value="${uiLabelMap.CommonAdd}" class="btn btn-primary btn-sm"/>
        </div>
    </div>
</form>
<@htmlScreenTemplate.renderScreenletEnd/>-->
<#--<@htmlScreenTemplate.renderScreenletBegin id="" title="${uiLabelMap.ProductCopyProductCategoryMembersToAnotherCategory}"/>
<form method="post" action="<@ofbizUrl>copyCategoryProductMembers</@ofbizUrl>" class="form-horizontal" name="copyCategoryProductMembersForm">
    <input type="hidden" name="productCategoryId" value="${productCategoryId?if_exists}"/>
    <input type="hidden" name="activeOnly" value="${activeOnly.toString()}"/>

    <div class="form-group">
        <label class="control-label col-md-3">${uiLabelMap.ProductTargetProductCategory}</label>

        <div class="col-md-5 ">
        <@htmlTemplate.lookupField formName="copyCategoryProductMembersForm" name="productCategoryIdTo" id="productCategoryIdTo" fieldFormName="LookupProductCategory"/>
        </div>
    </div>
    <div class="form-group">
        <label class="control-label col-md-3">${uiLabelMap.ProductOptionalFilterWithDate}</label>

        <div class="col-md-5 ">
        <@htmlTemplate.renderDateTimeField name="validDate" event="" action="" className="" alert="" title="Format: yyyy-MM-dd HH:mm:ss.SSS" value="" size="25" maxlength="30" id="validDate1" dateType="date" shortDateInput=false timeDropdownParamName="" defaultDateTimeString="" localizedIconTitle="" timeDropdown="" timeHourName="" classString="" hour1="" hour2="" timeMinutesName="" minutes="" isTwelveHour="" ampmName="" amSelected="" pmSelected="" compositeType="" formName=""/>
        </div>
    </div>
    <div class="form-group">
        <label class="control-label col-md-3">${uiLabelMap.ProductIncludeSubCategories}</label>

        <div class="col-md-5 ">
            <select name="recurse" class="form-control">
                <option value="N">${uiLabelMap.CommonN}</option>
                <option value="Y">${uiLabelMap.CommonY}</option>
            </select>
        </div>
    </div>
    <div class="form-group">
        <div class="col-md-3">&nbsp;</div>
        <div class="col-md-5  pull-right">
            <input type="submit" value="${uiLabelMap.CommonCopy}" class="btn btn-primary btn-sm"/>
        </div>
    </div>
</form>
<@htmlScreenTemplate.renderScreenletEnd/>-->
<#--<@htmlScreenTemplate.renderScreenletBegin id="" title="${uiLabelMap.ProductExpireAllProductMembers}"/>

<form method="post" action="<@ofbizUrl>expireAllCategoryProductMembers</@ofbizUrl>" class="form-horizontal" name="expireAllCategoryProductMembersForm">
    <input type="hidden" name="productCategoryId" value="${productCategoryId?if_exists}"/>
    <input type="hidden" name="activeOnly" value="${activeOnly.toString()}"/>

    <div class="form-group">
        <label class="control-label col-md-3">${uiLabelMap.ProductOptionalExpirationDate}</label>

        <div class="col-md-5 ">
        <@htmlTemplate.renderDateTimeField name="thruDate" event="" action="" className="" alert="" title="Format: yyyy-MM-dd HH:mm:ss.SSS" value="" size="25" maxlength="30" id="thruDate2" dateType="date" shortDateInput=false timeDropdownParamName="" defaultDateTimeString="" localizedIconTitle="" timeDropdown="" timeHourName="" classString="" hour1="" hour2="" timeMinutesName="" minutes="" isTwelveHour="" ampmName="" amSelected="" pmSelected="" compositeType="" formName=""/>
        </div>
    </div>
    <div class="form-group">
        <div class="col-md-3">&nbsp;</div>
        <div class="col-md-5  pull-right">
            <input type="submit" value="${uiLabelMap.CommonExpireAll}" class="btn btn-primary btn-sm"/>
        </div>
    </div>
</form>

<@htmlScreenTemplate.renderScreenletEnd/>-->
<#--<@htmlScreenTemplate.renderScreenletBegin id="" title="${uiLabelMap.ProductRemoveExpiredProductMembers}"/>
<form method="post" action="<@ofbizUrl>removeExpiredCategoryProductMembers</@ofbizUrl>" class="form-horizontal" name="removeExpiredCategoryProductMembersForm">
    <input type="hidden" name="productCategoryId" value="${productCategoryId?if_exists}"/>
    <input type="hidden" name="activeOnly" value="${activeOnly.toString()}"/>

    <div class="form-group">
        <label class="control-label col-md-3">${uiLabelMap.ProductOptionalExpiredBeforeDate}</label>
        <div class="col-md-5 ">
    <@htmlTemplate.renderDateTimeField name="validDate" event="" action="" className="" alert="" title="Format: yyyy-MM-dd HH:mm:ss.SSS" value="" size="25" maxlength="30" id="validDate2" dateType="date" shortDateInput=false timeDropdownParamName="" defaultDateTimeString="" localizedIconTitle="" timeDropdown="" timeHourName="" classString="" hour1="" hour2="" timeMinutesName="" minutes="" isTwelveHour="" ampmName="" amSelected="" pmSelected="" compositeType="" formName=""/>
        </div>
    </div>
    <div class="form-group">
        <div class="col-md-3">&nbsp;</div>
        <div class="col-md-5  pull-right">
            <input type="submit" value="${uiLabelMap.CommonRemoveExpired}" class="btn btn-primary btn-sm"/>
    </div>
        </div>
</form>
<@htmlScreenTemplate.renderScreenletEnd/>-->
