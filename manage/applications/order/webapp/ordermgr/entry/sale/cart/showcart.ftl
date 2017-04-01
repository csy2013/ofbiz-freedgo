<div class="wizard-step-2">
    <fieldset>
        <legend class="pull-left width-full">增加订单项</legend>

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
        <div>
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
        </div>

            <div class="form-group">
                <div class="input-group m-b-10 m-l-5">
                    <label class="input-group-addon"> ${uiLabelMap.ProductProductId} :</label>

                <#assign fieldFormName="LookupProduct">
                <@htmlTemplate.lookupField formName="salesentryform" name="add_product_id" id="add_product_id" fieldFormName="${fieldFormName}"/>
                   <#-- <a href="javascript:quicklookup(document.salesentryform.add_product_id)" class="buttontext">${uiLabelMap.OrderQuickLookup}</a>-->
                </div>

                <div class="input-group m-b-10 m-l-5">
                    <label class="input-group-addon">${uiLabelMap.OrderQuantity} :</label>
                    <input type="text" size="6" name="quantity" value="" class="form-control"/>
                </div>
                <div class="input-group m-b-10 m-l-5">
                    <label class="input-group-addon">${uiLabelMap.OrderDesiredDeliveryDate} :</label>

                <#if useAsDefaultDesiredDeliveryDate?exists>
                    <#assign value = defaultDesiredDeliveryDate>
                </#if>
                <@htmlTemplate.renderDateTimeField name="itemDesiredDeliveryDate" value="${value!''}" className="" alert="" title="Format: yyyy-MM-dd HH:mm:ss.SSS" size="25" maxlength="30" id="item1" dateType="date" shortDateInput=false timeDropdownParamName="" defaultDateTimeString="" localizedIconTitle="" timeDropdown="" timeHourName="" classString="" hour1="" hour2="" timeMinutesName="" minutes="" isTwelveHour="" ampmName="" amSelected="" pmSelected="" compositeType="" formName=""/>
                    <div class="input-group-addon"> <input type="checkbox" name="useAsDefaultDesiredDeliveryDate" value="true"<#if useAsDefaultDesiredDeliveryDate?exists> checked="checked"</#if>/>
                    缺省设置</div>

                </div>
                <input type="hidden" name="shipAfterDate"/>
                <input type="hidden" name="shipBeforeDate"/>

                <div class="input-group m-b-10 m-l-5">
                    <label class="input-group-addon">${uiLabelMap.CommonComment} :</label>

                    <input type="text" size="40" name="itemComment" value="${defaultComment?if_exists}" class="form-control"/>
                    <div class="input-group-addon"><input type="checkbox" name="useAsDefaultComment" value="true" <#if useAsDefaultComment?exists>checked="checked"</#if> />
                    ${uiLabelMap.OrderUseDefaultComment}
                    </div>

                </div>

                    <input type="button" onclick="document.salesentryform.submit();" class="btn btn-primary btn-sm m-b-10" value="${uiLabelMap.OrderAddToOrder}"/>
            </div>

        <#--</form>-->
            <script language="JavaScript" type="text/javascript">
                document.salesentryform.add_product_id.focus();
            </script>

