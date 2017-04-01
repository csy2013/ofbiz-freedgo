<div class="row">
    <div class="col-md-3"><h3>${uiLabelMap.OrderOrderItems}</h3></div>
    <div class="col-md-9">
        <div class="m-t-20 pull-right">

        <#if (shoppingCart.size() > 0)>
            <a class="btn btn-white  btn-sm" href="javascript:submitForm(document.salesentryform, 'MC', '');">${uiLabelMap.OrderRecalculateOrder}</a>
            <a class="btn btn-white  btn-sm" href="javascript:submitForm(document.salesentryform, 'DC', '');">${uiLabelMap.OrderRemoveSelected}</a>
        <#else>

        </#if>
        <#--<#if shoppingCart.size() = 0>

        <#else>
           <a class="btn btn-success  btn-sm" href="<@ofbizUrl>quickcheckout</@ofbizUrl>">${uiLabelMap.OrderQuickFinalizeOrder}</a>
           <a class="btn btn-success  btn-sm" href="<@ofbizUrl>finalizeOrder?finalizeMode=default</@ofbizUrl>">${uiLabelMap.OrderFinalizeOrderDefault}</a>
           <a class="btn btn-success  btn-sm" href="<@ofbizUrl>finalizeOrder?finalizeMode=init</@ofbizUrl>">${uiLabelMap.OrderFinalizeOrder}</a>
        </#if>-->
            <#--<a class="btn btn-white  btn-sm" href="javascript:submitForm(document.salesentryform, 'DN', '');">${uiLabelMap.OrderOrderConfirmation}</a>-->
        </div>
    </div>
</div>
<hr/>
<#if (shoppingCartSize > 0)>
<input type="hidden" name="removeSelected" value="false"/>
    <#if shoppingCart.getOrderType() == "PURCHASE_ORDER">
    <input type="hidden" name="finalizeReqShipInfo" value="false"/>
    <input type="hidden" name="finalizeReqOptions" value="false"/>
    <input type="hidden" name="finalizeReqPayInfo" value="false"/>
    <input type="hidden" name="finalizeReqAdditionalParty" value="false"/>
    </#if>


<div class="table-responsive">
    <table class="table table-bordered table-striped">
        <tr>
            <td>&nbsp;</td>
            <td colspan="2">
                <div>
                    <b>${uiLabelMap.ProductProduct}</b>
                <#--<#if (shoppingCart.getOrderType() == 'SALES_ORDER') && (productStore.showCheckoutGiftOptions)?default('Y') != 'N'>
                    <select name="GWALL" onchange="javascript:gwAll(this);">
                      <option value="">${uiLabelMap.OrderGiftWrapAllItems}</option>
                      <option value="NO^">${uiLabelMap.OrderNoGiftWrap}</option>
                      <#if allgiftWraps?has_content>
                        <#list allgiftWraps as option>
                          <option value="${option.productFeatureId?default("")}">${option.description?default("")} : <@ofbizCurrency amount=option.defaultAmount?default(0) isoCode=currencyUomId/></option>
                        </#list>
                      </#if>
                    </select>
                </#if>-->
                </div>
            </td>
            <td align="center">
                <div><b>${uiLabelMap.OrderQuantity}</b></div>
            </td>
            <td align="right">
                <div><b>${uiLabelMap.CommonUnitPrice}</b></div>
            </td>
            <td align="right">
                <div><b>${uiLabelMap.OrderAdjustments}</b></div>
            </td>
            <td align="right">
                <div><b>${uiLabelMap.OrderItemTotal}</b></div>
            </td>
            <td align="center"><input type="checkbox" name="selectAll" value="0" onclick="toggleAll(this);"/></td>
        </tr>

        <#assign itemsFromList = false>
        <#list shoppingCart.items() as cartLine>
            <#assign cartLineIndex = shoppingCart.getItemIndex(cartLine)>
            <#assign lineOptionalFeatures = cartLine.getOptionalProductFeatures()>
            <tr>
                <td colspan="8">&nbsp;</td>
            </tr>
            <tr valign="top">
                <td>&nbsp;</td>
                <td>
                    <table class="table">
                        <tr>
                            <td colspan="2">
                                <div>
                                    <#if cartLine.getProductId()?exists>
                                    <#-- product item -->
                                        <a href="<@ofbizUrl>product?product_id=${cartLine.getProductId()}</@ofbizUrl>" class="btn btn-primary btn-xs">${cartLine.getProductId()}</a> -
                                        <input size="60" type="text" name="description_${cartLineIndex}" value="${cartLine.getName()?default("")}"/><br/>
                                        <i>${cartLine.getDescription()?if_exists}</i>
                                        <#if shoppingCart.getOrderType() != "PURCHASE_ORDER">
                                        <#-- only applies to sales orders, not purchase orders -->
                                        <#-- if inventory is not required check to see if it is out of stock and needs to have a message shown about that... -->
                                            <#assign itemProduct = cartLine.getProduct()>
                                            <#assign isStoreInventoryNotRequiredAndNotAvailable = Static["org.ofbiz.product.store.ProductStoreWorker"].isStoreInventoryRequiredAndAvailable(request, itemProduct, cartLine.getQuantity(), false, false)>
                                            <#if isStoreInventoryNotRequiredAndNotAvailable && itemProduct.inventoryMessage?has_content>
                                                <b>(${itemProduct.inventoryMessage})</b>
                                            </#if>
                                        </#if>
                                    <#else>
                                    <#-- this is a non-product item -->
                                        <b>${cartLine.getItemTypeDescription()?if_exists}</b> : ${cartLine.getName()?if_exists}
                                    </#if>
                                <#-- display the item's features -->
                                    <#assign features = "">
                                    <#if cartLine.getFeaturesForSupplier(dispatcher,shoppingCart.getPartyId())?has_content>
                                        <#assign features = cartLine.getFeaturesForSupplier(dispatcher, shoppingCart.getPartyId())>
                                    <#elseif cartLine.getStandardFeatureList()?has_content>
                                        <#assign features = cartLine.getStandardFeatureList()>
                                    </#if>
                                    <#if features?has_content>
                                        <br/><i>${uiLabelMap.ProductFeatures}: <#list features as feature>${feature.description?default("")} </#list></i>
                                    </#if>
                                <#-- show links to survey response for this item -->
                                    <#if cartLine.getAttribute("surveyResponses")?has_content>
                                        <br/>Surveys:
                                        <#list cartLine.getAttribute("surveyResponses") as surveyResponseId>
                                            <a href="/content/control/ViewSurveyResponses?surveyResponseId=${surveyResponseId}&amp;externalLoginKey=${externalLoginKey}" class="buttontext"
                                               style="font-size: xx-small;">${surveyResponseId}</a>
                                        </#list>
                                    </#if>
                                </div>
                            </td>
                        </tr>
                        <#if cartLine.getRequirementId()?has_content>
                            <tr>
                                <td colspan="2">
                                    <div><b>${uiLabelMap.OrderRequirementId}</b>: ${cartLine.getRequirementId()?if_exists}</div>
                                </td>
                            </tr>
                        </#if>
                        <#if cartLine.getQuoteId()?has_content>
                            <#if cartLine.getQuoteItemSeqId()?has_content>
                                <tr>
                                    <td colspan="2">
                                        <div><b>${uiLabelMap.OrderOrderQuoteId}</b>: ${cartLine.getQuoteId()?if_exists} - ${cartLine.getQuoteItemSeqId()?if_exists}</div>
                                    </td>
                                </tr>
                            </#if>
                        </#if>
                        <#if cartLine.getItemComment()?has_content>
                            <tr>
                                <td>
                                    <div>${uiLabelMap.CommonComment} :</div>
                                </td>
                                <td>
                                    <div>${cartLine.getItemComment()?if_exists}</div>
                                </td>
                            </tr>
                        </#if>
                        <#if cartLine.getDesiredDeliveryDate()?has_content>
                            <tr>
                                <td>
                                    <div>${uiLabelMap.OrderDesiredDeliveryDate}:</div>
                                </td>
                                <td>
                                    <div>${cartLine.getDesiredDeliveryDate()?if_exists}</div>
                                </td>
                            </tr>
                        </#if>
                    <#-- inventory summary -->
                        <#if cartLine.getProductId()?exists>
                            <#assign productId = cartLine.getProductId()>
                            <#assign product = cartLine.getProduct()>
                            <tr>
                                <td colspan="2">
                                    <div>
                                        <a href="/catalog/control/EditProductInventoryItems?productId=${productId}" class="buttontext"><b>${uiLabelMap.ProductInventory}</b></a>:
                                    ${uiLabelMap.ProductAtp} = ${availableToPromiseMap.get(productId)}, ${uiLabelMap.ProductQoh} = ${quantityOnHandMap.get(productId)}
                                        <#if Static["org.ofbiz.entity.util.EntityTypeUtil"].hasParentType(delegator, "ProductType", "productTypeId", product.productTypeId, "parentTypeId", "MARKETING_PKG")>
                                        ${uiLabelMap.ProductMarketingPackageATP} = ${mktgPkgATPMap.get(productId)}, ${uiLabelMap.ProductMarketingPackageQOH} = ${mktgPkgQOHMap.get(productId)}
                                            <#if ( mktgPkgATPMap.get(cartLine.getProductId()) < cartLine.getQuantity()) && (shoppingCart.getOrderType() == 'SALES_ORDER')>
                                                <#assign backOrdered = cartLine.getQuantity() - mktgPkgATPMap.get(cartLine.getProductId())/>
                                                <span style="color: red; font-size: 15px;">[${backOrdered?if_exists}&nbsp;${uiLabelMap.OrderBackOrdered}]</span>
                                            </#if>
                                        </#if>
                                        <#if (availableToPromiseMap.get(cartLine.getProductId()) <= 0) && (shoppingCart.getOrderType() == 'SALES_ORDER') && product.productTypeId! != "DIGITAL_GOOD" && product.productTypeId! != "MARKETING_PKG_AUTO" && product.productTypeId! != "MARKETING_PKG_PICK">
                                            <span style="color: red;">[${cartLine.getQuantity()}&nbsp;${uiLabelMap.OrderBackOrdered}]</span>
                                        <#else>
                                            <#if (availableToPromiseMap.get(cartLine.getProductId()) < cartLine.getQuantity()) && (shoppingCart.getOrderType() == 'SALES_ORDER') && product.productTypeId != "DIGITAL_GOOD" && product.productTypeId != "MARKETING_PKG_AUTO" && product.productTypeId != "MARKETING_PKG_PICK">
                                                <#assign backOrdered = cartLine.getQuantity() - availableToPromiseMap.get(cartLine.getProductId())/>
                                                <span style="color: red;">[${backOrdered?if_exists}&nbsp;${uiLabelMap.OrderBackOrdered}]</span>
                                            </#if>
                                        </#if>
                                    </div>
                                </td>
                            </tr>
                        </#if>
                        <#if shoppingCart.getOrderType() == "PURCHASE_ORDER">
                            <#assign currentOrderItemType = cartLine.getItemTypeGenericValue()?if_exists/>
                            <tr>
                                <td>
                                    <div>
                                    ${uiLabelMap.OrderOrderItemType}:
                                        <select name="itemType_${cartLineIndex}">
                                            <#if currentOrderItemType?has_content>
                                                <option value="${currentOrderItemType.orderItemTypeId}">${currentOrderItemType.get("description",locale)}</option>
                                                <option value="${currentOrderItemType.orderItemTypeId}">---</option>
                                            </#if>
                                            <option value="">&nbsp;</option>
                                            <#list purchaseOrderItemTypeList as orderItemType>
                                                <option value="${orderItemType.orderItemTypeId}">${orderItemType.get("description",locale)}</option>
                                            </#list>
                                        </select>
                                    </div>
                                </td>
                            </tr>
                        </#if>

                    <#-- ship before/after date -->
                        <tr>
                            <td colspan="2">
                                <table border="0" cellpadding="0" cellspacing="0" width="100%">
                                    <tr>
                                        <td>
                                            <div>${uiLabelMap.OrderShipAfterDate}
                    <@htmlTemplate.renderDateTimeField name="shipAfterDate_${cartLineIndex}" value="${cartLine.getShipAfterDate()?default('')}" event="" action="" className="" alert="" title="Format: yyyy-MM-dd HH:mm:ss.SSS" size="25" maxlength="30" id="shipAfterDate_${cartLineIndex}" dateType="date" shortDateInput=false timeDropdownParamName="" defaultDateTimeString="" localizedIconTitle="" timeDropdown="" timeHourName="" classString="" hour1="" hour2="" timeMinutesName="" minutes="" isTwelveHour="" ampmName="" amSelected="" pmSelected="" compositeType="" formName=""/>
                                            </div>
                                        </td>
                                        <td>&nbsp;</td>
                                        <td>
                                            <div>${uiLabelMap.OrderShipBeforeDate}
                    <@htmlTemplate.renderDateTimeField name="shipBeforeDate_${cartLineIndex}" value="${cartLine.getShipBeforeDate()?default('')}" event="" action="" className="" alert="" title="Format: yyyy-MM-dd HH:mm:ss.SSS" size="25" maxlength="30" id="shipBeforeDate_${cartLineIndex}" dateType="date" shortDateInput=false timeDropdownParamName="" defaultDateTimeString="" localizedIconTitle="" timeDropdown="" timeHourName="" classString="" hour1="" hour2="" timeMinutesName="" minutes="" isTwelveHour="" ampmName="" amSelected="" pmSelected="" compositeType="" formName=""/>
                                            </div>
                                        </td>
                                    </tr>
                                </table>
                            </td>
                        </tr>

                    <#-- Show Associated Products (not for Variants) -->
                        <#if cartLine.getProductId()?exists>
                            <#assign itemProductAssocList = cartLine.getProduct().getRelated("MainProductAssoc",
                            Static["org.ofbiz.base.util.UtilMisc"].toList("productAssocTypeId", "sequenceNum"))?if_exists/>
                        </#if>
                    <#--<#if itemProductAssocList?exists && itemProductAssocList?has_content>

                      <tr>
                        <td>关联商品</td>
                        <td><a href="<@ofbizUrl>LookupAssociatedProducts?productId=${cartLine.getProductId()?if_exists}</@ofbizUrl>" class="buttontext">${uiLabelMap.OrderQuickLookup}</a></td>
                      </tr>
                      <#assign relatedProdCount = 0/>
                      <#list itemProductAssocList?if_exists as itemProductAssoc>
                        <tr>
                          <#if "PRODUCT_VARIANT" != itemProductAssoc.productAssocTypeId>
                            <#assign relatedProdCount = relatedProdCount + 1/>
                            <#if (relatedProdCount > 3)><#break></#if>
                            <@showAssoc productAssoc=itemProductAssoc />
                          </#if>
                        </tr>
                      </#list>
                    </#if>-->
                    </table>

                    <#if (cartLine.getIsPromo() && cartLine.getAlternativeOptionProductIds()?has_content)>
                    <#-- Show alternate gifts if there are any... -->
                        <div>${uiLabelMap.OrderChooseFollowingForGift}:</div>
                        <#list cartLine.getAlternativeOptionProductIds() as alternativeOptionProductId>
                            <#assign alternativeOptionProduct = delegator.findByPrimaryKeyCache("Product", Static["org.ofbiz.base.util.UtilMisc"].toMap("productId", alternativeOptionProductId))>
                            <#assign alternativeOptionName = Static["org.ofbiz.product.product.ProductContentWrapper"].getProductContentAsText(alternativeOptionProduct, "PRODUCT_NAME", locale, dispatcher)?if_exists>
                            <div><a href="<@ofbizUrl>setDesiredAlternateGwpProductId?alternateGwpProductId=${alternativeOptionProductId}&amp;alternateGwpLine=${cartLineIndex}</@ofbizUrl>"
                                    class="buttontext">Select: ${alternativeOptionName?default(alternativeOptionProductId)}</a></div>
                        </#list>
                    </#if>
                </td>

            <#-- gift wrap option -->
                <#assign showNoGiftWrapOptions = false>
            <#--礼品选择-->
                <td nowrap="nowrap" align="right">
                    <#assign giftWrapOption = lineOptionalFeatures.GIFT_WRAP?if_exists>
                    <#assign selectedOption = cartLine.getAdditionalProductFeatureAndAppl("GIFT_WRAP")?if_exists>
                    <#if giftWrapOption?has_content>
                        <select name="option^GIFT_WRAP_${cartLineIndex}" onchange="document.salesentryform.submit()">
                            <option value="NO^">${uiLabelMap.OrderNoGiftWrap}</option>
                            <#list giftWrapOption as option>
                                <option value="${option.productFeatureId}"
                                        <#if ((selectedOption.productFeatureId)?exists && selectedOption.productFeatureId == option.productFeatureId)>selected="selected"</#if>>${option.description}
                                    : <@ofbizCurrency amount=option.amount?default(0) isoCode=currencyUomId/></option>
                            </#list>
                        </select>
                    <#elseif showNoGiftWrapOptions>
                        <select name="option^GIFT_WRAP_${cartLineIndex}" onchange="document.salesentryform.submit()">
                            <option value="">${uiLabelMap.OrderNoGiftWrap}</option>
                        </select>
                    <#else>
                        &nbsp;
                    </#if>
                </td>
            <#-- end gift wrap option -->
                <td nowrap="nowrap" align="center">
                    <div>
                        <#if cartLine.getIsPromo() || cartLine.getShoppingListId()?exists>
                        ${cartLine.getQuantity()?string.number}
                        <#else>
                            <input size="6" type="text" name="update_${cartLineIndex}" value="${cartLine.getQuantity()?string.number}"/>
                        </#if>
                        <#if (cartLine.getSelectedAmount() > 0) >
                            <br/><b>${uiLabelMap.OrderAmount}:</b><br/><input size="6" type="text" name="amount_${cartLineIndex}" value="${cartLine.getSelectedAmount()?string.number}"/>
                        </#if>
                    </div>
                </td>
            <#--价格-->
                <td nowrap="nowrap" align="right">
                    <div>
                        <#if cartLine.getIsPromo() || (shoppingCart.getOrderType() == "SALES_ORDER" && !security.hasEntityPermission("ORDERMGR", "_SALES_PRICEMOD", session))>
                            <@ofbizCurrency amount=cartLine.getDisplayPrice() isoCode=currencyUomId/>
                        <#else>
                            <#if (cartLine.getSelectedAmount() > 0) >
                                <#assign price = cartLine.getBasePrice() / cartLine.getSelectedAmount()>
                            <#else>
                                <#assign price = cartLine.getBasePrice()>
                            </#if>
                            <input size="8" type="text" name="price_${cartLineIndex}" value="<@ofbizAmount amount=price/>"/>
                        </#if>
                    </div>
                </td>
                <td nowrap="nowrap" align="right">
                    <div><@ofbizCurrency amount=cartLine.getOtherAdjustments() isoCode=currencyUomId/></div>
                </td>
                <td nowrap="nowrap" align="right">
                    <div><@ofbizCurrency amount=cartLine.getDisplayItemSubTotal() isoCode=currencyUomId/></div>
                </td>
                <td nowrap="nowrap" align="center">
                    <div><#if !cartLine.getIsPromo()><input type="checkbox" name="selectedItem" value="${cartLineIndex}" onclick="checkToggle(this);"/><#else>&nbsp;</#if></div>
                </td>
            </tr>
        </#list>

        <#if shoppingCart.getAdjustments()?has_content>

            <tr>
                <td colspan="6" nowrap="nowrap" align="right">
                    <div>${uiLabelMap.OrderSubTotal}:</div>
                </td>
                <td nowrap="nowrap" align="right">
                    <div><@ofbizCurrency amount=shoppingCart.getSubTotal() isoCode=currencyUomId/></div>
                </td>
                <td>&nbsp;</td>
            </tr>
            <#list shoppingCart.getAdjustments() as cartAdjustment>
                <#assign adjustmentType = cartAdjustment.getRelatedOneCache("OrderAdjustmentType")>
                <#assign productPromo = cartAdjustment.getRelatedOneCache("ProductPromo")>
                <tr>
                    <td colspan="6" nowrap="nowrap" align="right">
                        <div>
                            <i>${uiLabelMap.OrderAdjustment}</i> - ${adjustmentType.get("description",locale)?if_exists}:${productPromo.promoName?if_exists}
                            <#if cartAdjustment.productPromoId?has_content><a href="<@ofbizUrl>showPromotionDetails?productPromoId=${cartAdjustment.productPromoId}</@ofbizUrl>"
                                                                              class="buttontext">${uiLabelMap.CommonDetails}</a></#if>:
                        </div>
                    </td>
                    <td nowrap="nowrap" align="right">
                        <div><@ofbizCurrency amount=Static["org.ofbiz.order.order.OrderReadHelper"].calcOrderAdjustment(cartAdjustment, shoppingCart.getSubTotal()) isoCode=currencyUomId/></div>
                    </td>
                    <td>&nbsp;</td>
                </tr>
            </#list>
        </#if>

        <tr>
            <td colspan="6" align="right" valign="bottom">
                <div><b>${uiLabelMap.OrderCartTotal}:</b></div>
            </td>
            <td align="right" valign="bottom">
                <div><b><@ofbizCurrency amount=shoppingCart.getGrandTotal() isoCode=currencyUomId/></b></div>
            </td>
        </tr>
        <tr>
            <td colspan="8">&nbsp;</td>
        </tr>
    </table>
</div>
<#else>
<div>${uiLabelMap.OrderNoOrderItemsToDisplay}</div>
</#if>
</fieldset>
</div>

