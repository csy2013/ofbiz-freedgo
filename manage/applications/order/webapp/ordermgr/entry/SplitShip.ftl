
<script language="javascript" type="text/javascript">
//<![CDATA[
function submitForm(form, mode, value) {
    if (mode == "DN") {
        // done action; payment info
        form.action="<@ofbizUrl>updateShippingOptions/checkoutpayment</@ofbizUrl>";
        form.submit();
    } else if (mode == "CS") {
        // continue shopping
        form.action="<@ofbizUrl>updateShippingOptions/showcart</@ofbizUrl>";
        form.submit();
    } else if (mode == "NA") {
        // new address
        form.action="<@ofbizUrl>updateCheckoutOptions/editcontactmech?DONE_PAGE=splitship&partyId=${cart.getPartyId()}&preContactMechTypeId=POSTAL_ADDRESS&contactMechPurposeTypeId=SHIPPING_LOCATION</@ofbizUrl>";
        form.submit();
    } else if (mode == "SV") {
        // save option; return to current screen
        form.action="<@ofbizUrl>updateShippingOptions/splitship</@ofbizUrl>";
        form.submit();
    } else if (mode == "SA") {
        // selected shipping address
        form.action="<@ofbizUrl>updateShippingAddress/splitship</@ofbizUrl>";
        form.submit();
    }
}
//]]>
</script>

<@htmlScreenTemplate.renderScreenletBegin id="OrderItemGroups" title="${uiLabelMap.OrderItemGroups}"></@htmlScreenTemplate.renderScreenletBegin>
<div class="table-responsive">
        <table class="table table-bordered table-striped">
          <#assign shipGroups = cart.getShipGroups()>
          <#if (shipGroups.size() > 0)>
            <#assign groupIdx = 0>
            <#list shipGroups as group>
              <#assign shipEstimateWrapper = Static["org.ofbiz.order.shoppingcart.shipping.ShippingEstimateWrapper"].getWrapper(dispatcher, cart, groupIdx)>
              <#assign carrierShipmentMethods = shipEstimateWrapper.getShippingMethods()>
              <#assign groupNumber = groupIdx + 1>
              <form method="post" action="#" name="editgroupform${groupIdx}"  class="form-inline">
                <input type="hidden" name="groupIndex" value="${groupIdx}"/>
                <tr>
                  <td>
                    <div><b>${uiLabelMap.CommonGroup} ${groupNumber}:</b></div>
                    <#list group.getShipItems() as item>
                      <#assign groupItem = group.getShipItemInfo(item)>
                      <div>&nbsp;&nbsp;&nbsp;${item.getName()} - (${groupItem.getItemQuantity()})</div>
                    </#list>
                  </td>
                  <td>
                    <div>
                      <span>${uiLabelMap.CommonAdd}:</span>
                      <a href="javascript:submitForm(document.editgroupform${groupIdx}, 'NA', '');" class="buttontext">${uiLabelMap.PartyAddNewAddress}</a>
                    </div>
                    <div>
                      <#assign selectedContactMechId = cart.getShippingContactMechId(groupIdx)?default("")>
                      <select name="shippingContactMechId" class="form-control" onchange="submitForm(document.editgroupform${groupIdx}, 'SA', null);" >
                        <option value="">${uiLabelMap.OrderSelectShippingAddress}</option>
                        <#list shippingContactMechList as shippingContactMech>
                          <#assign shippingAddress = shippingContactMech.getRelatedOne("PostalAddress")>
                          <option value="${shippingAddress.contactMechId}" <#if (shippingAddress.contactMechId == selectedContactMechId)>selected="selected"</#if>>${shippingAddress.address1}</option>
                        </#list>
                      </select>
                    </div>
                    <#if cart.getShipmentMethodTypeId(groupIdx)?exists>
                      <#assign selectedShippingMethod = cart.getShipmentMethodTypeId(groupIdx) + "@" + cart.getCarrierPartyId(groupIdx)>
                    <#else>
                      <#assign selectedShippingMethod = "">
                    </#if>
                    <select name="shipmentMethodString" class="form-control">
                      <option value="">${uiLabelMap.OrderSelectShippingMethod}</option>
                      <#list carrierShipmentMethods as carrierShipmentMethod>
                        <#assign shippingEst = shipEstimateWrapper.getShippingEstimate(carrierShipmentMethod)?default(-1)>
                        <#assign shippingMethod = carrierShipmentMethod.shipmentMethodTypeId + "@" + carrierShipmentMethod.partyId>
                        <option value="${shippingMethod}" <#if (shippingMethod == selectedShippingMethod)>selected="selected"</#if>>
                          <#if carrierShipmentMethod.partyId != "_NA_">
                            ${carrierShipmentMethod.partyId?if_exists}&nbsp;
                          </#if>
                          ${carrierShipmentMethod.description?if_exists}
                          <#if shippingEst?has_content>
                            &nbsp;-&nbsp;
                            <#if (shippingEst > -1)>
                              <@ofbizCurrency amount=shippingEst isoCode=cart.getCurrency()/>
                            <#else>
                              ${uiLabelMap.OrderCalculatedOffline}
                            </#if>
                          </#if>
                        </option>
                      </#list>
                    </select>

                    <h4>${uiLabelMap.OrderSpecialInstructions}</h4>
                    <textarea class='textAreaBox' cols="35" rows="3" wrap="hard" name="shippingInstructions">${cart.getShippingInstructions(groupIdx)?if_exists}</textarea>
                  </td>
                  <td>
                    <div>
                      <select name="maySplit" class="form-control">
                        <#assign maySplitStr = cart.getMaySplit(groupIdx)?default("")>
                        <option value="">${uiLabelMap.OrderSplittingPreference}</option>
                        <option value="false" <#if maySplitStr == "N">selected="selected"</#if>>${uiLabelMap.OrderShipAllItemsTogether}</option>
                        <option value="true" <#if maySplitStr == "Y">selected="selected"</#if>>${uiLabelMap.OrderShipItemsWhenAvailable}</option>
                      </select>
                    </div>
                    <div>
                      <select name="isGift" class="form-control">
                        <#assign isGiftStr = cart.getIsGift(groupIdx)?default("")>
                        <option value="">${uiLabelMap.OrderIsGift} ?</option>
                        <option value="false" <#if isGiftStr == "N">selected="selected"</#if>>${uiLabelMap.OrderNotAGift}</option>
                        <option value="true" <#if isGiftStr == "Y">selected="selected"</#if>>${uiLabelMap.OrderYesIsAGift}</option>
                      </select>
                    </div>

                    <h4>${uiLabelMap.OrderGiftMessage}</h4>
                    <textarea class='textAreaBox' cols="30" rows="3" wrap="hard" name="giftMessage">${cart.getGiftMessage(groupIdx)?if_exists}</textarea>
                  </td>
                  <td><input type="button" class="btn btn-primary btn-sm" value="${uiLabelMap.CommonSave}" onclick="submitForm(document.editgroupform${groupIdx}, 'SV', null);"/></td>
                </tr>
                <#assign groupIdx = groupIdx + 1>

              </form>
            </#list>
          <#else>
            <div>${uiLabelMap.OrderNoShipGroupsDefined}.</div>
          </#if>
        </table>
</div>
<@htmlScreenTemplate.renderScreenletEnd/>

<@htmlScreenTemplate.renderScreenletBegin id="OrderItemGroups" title="${uiLabelMap.OrderAssignItems}"></@htmlScreenTemplate.renderScreenletBegin>
<div class="table-responsive">
        <table class="table table-bordered table-striped">
          <tr>
            <td><div><b>${uiLabelMap.OrderProduct}</b></div></td>
            <td align="center"><div><b>${uiLabelMap.OrderTotalQty}</b></div></td>
            <td>&nbsp;</td>
            <td align="center"><div><b>${uiLabelMap.OrderMoveQty}</b></div></td>
            <td>&nbsp;</td>
            <td>&nbsp;</td>

          </tr>

          <#list cart.items() as cartLine>
            <#assign cartLineIndex = cart.getItemIndex(cartLine)>
            <tr>
              <form method="post" action="<@ofbizUrl>updatesplit</@ofbizUrl>" name="editgroupform" style="margin: 0;">
                <input type="hidden" name="itemIndex" value="${cartLineIndex}"/>
                <td>
                  <div>
                    <#if cartLine.getProductId()?exists>
                      <#-- product item -->
                      <#-- start code to display a small image of the product -->
                      <#assign smallImageUrl = Static["org.ofbiz.product.product.ProductContentWrapper"].getProductContentAsText(cartLine.getProduct(), "SMALL_IMAGE_URL", locale, dispatcher)?if_exists>
                      <#if !smallImageUrl?string?has_content><#assign smallImageUrl = "/images/defaultImage.jpg"></#if>
                      <#if smallImageUrl?string?has_content>
                        <a href="<@ofbizUrl>product?product_id=${cartLine.getProductId()}</@ofbizUrl>">
                          <img src="<@ofbizContentUrl>${requestAttributes.contentPathPrefix?if_exists}${smallImageUrl}</@ofbizContentUrl>" class="cssImgSmall" alt="" />
                        </a>
                      </#if>
                      <#-- end code to display a small image of the product -->
                      <a href="<@ofbizUrl>product?product_id=${cartLine.getProductId()}</@ofbizUrl>" class="buttontext">${cartLine.getProductId()} -
                      ${cartLine.getName()?if_exists}</a> :<#-- ${cartLine.getDescription()?if_exists}-->

                      <#-- display the registered ship groups and quantity -->
                      <#assign itemShipGroups = cart.getShipGroups(cartLine)>
                      <#list itemShipGroups.entrySet() as group>
                        <div>
                          <#assign groupNumber = group.getKey() + 1>
                          <b>Group - </b>${groupNumber} / <b>${uiLabelMap.CommonQuantity} - </b>${group.getValue()}
                        </div>
                      </#list>

                      <#-- if inventory is not required check to see if it is out of stock and needs to have a message shown about that... -->
                      <#assign itemProduct = cartLine.getProduct()>
                      <#assign isStoreInventoryNotRequiredAndNotAvailable = Static["org.ofbiz.product.store.ProductStoreWorker"].isStoreInventoryRequiredAndAvailable(request, itemProduct, cartLine.getQuantity(), false, false)>
                      <#if isStoreInventoryNotRequiredAndNotAvailable && itemProduct.inventoryMessage?has_content>
                        <b>(${itemProduct.inventoryMessage})</b>
                      </#if>

                    <#else>
                      <#-- this is a non-product item -->
                      <b>${cartLine.getItemTypeDescription()?if_exists}</b> : ${cartLine.getName()?if_exists}
                    </#if>
                  </div>

                </td>
                <td align="right">
                  <div>${cartLine.getQuantity()?string.number}&nbsp;&nbsp;&nbsp;</div>
                </td>

                <td align="center">
                  <input size="6" class="inputBox" type="text" name="quantity" value="${cartLine.getQuantity()?string.number}" class="form-control"/>
                </td>

                <td>
                  <div>${uiLabelMap.CommonFrom}:
                    <select name="fromGroupIndex"  class="form-control">
                      <#list itemShipGroups.entrySet() as group>
                        <#assign groupNumber = group.getKey() + 1>
                        <option value="${group.getKey()}">${uiLabelMap.CommonGroup} ${groupNumber}</option>
                      </#list>
                    </select>
                  </div>
                </td>
                <td>
                  <div>${uiLabelMap.CommonTo}:
                    <select name="toGroupIndex" class="form-control">
                      <#list 0..(cart.getShipGroupSize() - 1) as groupIdx>
                        <#assign groupNumber = groupIdx + 1>
                        <option value="${groupIdx}">${uiLabelMap.CommonGroup} ${groupNumber}</option>
                      </#list>
                      <option value="-1">${uiLabelMap.CommonNew} ${uiLabelMap.CommonGroup}</option>
                    </select>
                  </div>
                </td>
                <td><input type="submit" class="btn btn-primary btn-sm" value="${uiLabelMap.CommonSubmit}"/></td>
              </form>
            </tr>
          </#list>
        </table>
    </div>
<@htmlScreenTemplate.renderScreenletEnd/>

<div class="row">
  <div class="col-md-12 text-left">
     <a href="<@ofbizUrl>saleorderentry</@ofbizUrl>" class="btn btn-primary btn-sm">${uiLabelMap.OrderBacktoShoppingCart}</a>
     <a href="<@ofbizUrl>saleorderentry</@ofbizUrl>" class="btn btn-primary btn-sm">${uiLabelMap.CommonContinue}</a>

  </div>
</div>
