<div id="newShippingAddressForm">
    <form id="addShippingAddress" name="addShippingAddress" method="post" action="addShippingAddress" class="form-inline" data-parsley-validate="true">
        <input type="hidden" name="orderId" value="${requestParameters.orderId?if_exists}"/>
        <input type="hidden" name="partyId" value="${requestParameters.partyId?if_exists}"/>
        <input type="hidden" name="oldContactMechId" value="${requestParameters.contactMechId?if_exists}"/>
        <input type="hidden" name="shipGroupSeqId" value="${requestParameters.shipGroupSeqId?if_exists}"/>
        <input type="hidden" name="contactMechPurposeTypeId" value="SHIPPING_LOCATION"/>

        <div class="form-group">
            <input type="hidden" name="shipToCountryGeoId" id="addShippingAddress_countryGeoId" value="CHN"/>
            <div class="input-group m-b-10 m-r-5">
                <label for="stateProvinceGeoId" class="input-group-addon">${uiLabelMap.PartyState}* </label>
                <select name="shipToStateProvinceGeoId" id="addShippingAddress_stateProvinceGeoId" data-parsley-required="true" class="form-control">
                </select>
            </div>
            <div class="input-group m-b-10 m-r-5">
                <label for="city" class="input-group-addon">${uiLabelMap.PartyCity}* </label>
                <select name="shipToCity" id="addShippingAddress_city" data-parsley-required="true"  class="form-control">
                </select>
            </div>

            <div class="input-group m-b-10 m-r-5">
                <label for="countyGeoId" class="input-group-addon">县/区* </label>
                <select name="shipToCountyGeoId" id="addShippingAddress_countyGeoId" data-parsley-required="true"  class="form-control">
                </select>
            </div>
            <div class="input-group m-b-10 m-r-5">
                <label for="shipToName" class="input-group-addon">${uiLabelMap.PartyToName}</label>
                <input type="text" name="shipToName" maxlength="100" value="${(mechMap.postalAddress.toName)?default(request.getParameter('toName')?if_exists)}" class="form-control"/>
            </div>
            <#--<div class="input-group m-b-10 m-r-5">
                <label for="attnName" class="input-group-addon">${uiLabelMap.PartyAttentionName}</label>
                <input type="text" name="attnName" maxlength="100" value="${(mechMap.postalAddress.attnName)?default(request.getParameter('attnName')?if_exists)}"
                       class="form-control"/>
            </div>-->
            <div class="input-group m-b-10 m-r-5">
                <label for="address1" class="input-group-addon">${uiLabelMap.PartyAddressLine1}* </label>
                <input type="text" class="form-control" name="shipToAddress1" id="address1" value="" size="30" maxlength="30" data-parsley-required="true"/>
            </div>
            <div class="input-group m-b-10 m-r-5">
                <label for="address2" class="input-group-addon">${uiLabelMap.PartyAddressLine2}</label>
                <input type="text" name="shipToAddress2" id="address2" value="" size="30" maxlength="30" class="form-control"/>
            </div>
            <div class="input-group m-b-10 m-r-5">
                <label class="input-group-addon">${uiLabelMap.PartyContactMobilePhoneNumber}</label>
                <input type="text" name="shipMobilePhone" maxlength="255" value="${(mechMap.postalAddress.mobilePhone)?default(request.getParameter('mobilePhone')?if_exists)}"
                           class="form-control"/>
            </div>

            <div class="input-group m-b-10 m-r-5">
                <label for="postalCode" class="input-group-addon">${uiLabelMap.PartyZipCode}* </label>
                <input type="text" class="form-control" name="shipToPostalCode" id="postalCode" value="" data-parsley-required="true" size="30" maxlength="10"/>
            </div>
            <div class="input-group m-b-10 m-r-5">
                <input type="checkbox" class="form-control" name="isDefaultOrderShipAddress" id="isDefaultOrderShipAddress"   value="Y"/>
                <div for="postalCode">作为订单收货地址</div>
            </div>
            </div>
        <div class="form-group">
            <input id="submitAddShippingAddress" type="submit" value="${uiLabelMap.CommonSubmit}"/>
        </div>
    </form>
</div>