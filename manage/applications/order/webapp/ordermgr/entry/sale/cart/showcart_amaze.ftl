<script language="JavaScript" type="text/javascript">
    /* function showQohAtp() {
         document.qohAtpForm.productId.value = document.salesentryform.add_product_id.value;
         document.qohAtpForm.submit();
     }*/
    function quicklookupGiftCertificate() {
        window.location = 'AddGiftCertificate';
    }
</script>
<#assign target="getProductInventoryAvailable">
<input type="hidden" name="shipAfterDate"/>
<input type="hidden" name="shipBeforeDate"/>
<div class="am-panel am-panel-default">
    <div class="am-panel-hd am-cf">
    ${uiLabelMap.saleAddProductInfo}
    </div>
    <div class="am-panel-bd am-collapse am-in">
    <#if quantityOnHandTotal?exists && availableToPromiseTotal?exists && (productId)?exists>
        <ul>
            <li>
                <label>${uiLabelMap.ProductQuantityOnHand}</label>: ${quantityOnHandTotal}
            </li>
            <li>
                <label>${uiLabelMap.ProductAvailableToPromise}</label>: ${availableToPromiseTotal}
            </li>
        </ul>
    </#if>


    <#--why?-->
    <#--   <form name="qohAtpForm" method="post" action="<@ofbizUrl>${target}</@ofbizUrl>">
         <fieldset>
           <input type="hidden" name="facilityId" value="${facilityId?if_exists}"/>
           <input type="hidden" name="productId"/>
           <input type="hidden" id="ownerPartyId" name="ownerPartyId" value="${shoppingCart.getBillToCustomerPartyId()?if_exists}" />
         </fieldset>
       </form>-->
    <#--<form method="post" action="<@ofbizUrl>addsaleitem</@ofbizUrl>" name="quickaddform" style="margin: 0;">-->
    <#assign fieldFormName="LookupProduct">
        <div class="am-form-group am-g">
            <div class="am-u-md-3 am-control-label">${uiLabelMap.ProductProductId} :</div>
            <div class="am-u-md-3">

                <@amazeHtmlTemplate.lookupField formName="salesentryform" name="add_product_id" id="add_product_id" fieldFormName="${fieldFormName}"/>
                    <a href="javascript:quicklookup(document.salesentryform.add_product_id)"
                       class="buttontext">${uiLabelMap.OrderQuickLookup}</a>
            </div>
            <div class="am-u-md-3 am-form-lable">${uiLabelMap.OrderDesiredDeliveryDate} :</div>

            <div class="am-u-md-3">
            <#if useAsDefaultDesiredDeliveryDate?exists><#assign value = defaultDesiredDeliveryDate></#if>
                <span><@amazeHtmlTemplate.renderDateTimeField name="itemDesiredDeliveryDate" value="${value!''}" className="" alert="" title="Format: yyyy-MM-dd HH:mm:ss.SSS" size="25" maxlength="30" id="item1" dateType="date" shortDateInput=false timeDropdownParamName="" defaultDateTimeString="" localizedIconTitle="" timeDropdown="" timeHourName="" classString="" hour1="" hour2="" timeMinutesName="" minutes="" isTwelveHour="" ampmName="" amSelected="" pmSelected="" compositeType="" formName=""/></span>
            </div>
        </div>
        <div class="am-form-group am-g">
            <div class="am-u-md-2"> ${uiLabelMap.CommonComment} :</div>
            <div class="am-u-md-4 am-input-sm"><input type="text" name="itemComment" value="${defaultComment?if_exists}"/>
            </div>


            <div class="am-u-md-2">${uiLabelMap.OrderQuantity} :</div>
            <div class="am-u-md-4 am-input-sm am-u-end"><input type="text" name="quantity" value=""/></div>

        </div>
        <div class="am-form-group am-g">
            <div class="am-cf am-fr">
                <input type="button" onclick="document.salesentryform.submit();"
                                                   class="am-btn am-btn-primary am-btn-sm" value="${uiLabelMap.OrderAddToOrder}"/></div>
        </div>

    <#--</form>-->
    </div>
</div>

<script language="JavaScript" type="text/javascript">
    document.salesentryform.add_product_id.focus();
</script>

<!-- Internal cart info: productStoreId=${shoppingCart.getProductStoreId()?if_exists} locale=${shoppingCart.getLocale()?if_exists} currencyUom=${shoppingCart.getCurrency()?if_exists} userLoginId=${(shoppingCart.getUserLogin().getString("userLoginId"))?if_exists} autoUserLogin=${(shoppingCart.getAutoUserLogin().getString("userLoginId"))?if_exists} -->
