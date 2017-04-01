<#assign shipping = !shoppingCart.containAllWorkEffortCartItems()>
<input type="hidden" name="checkoutpage" value="quick"/>
<input type="hidden" name="BACK_PAGE" value="quickcheckout"/>

<div class="am-g">
    <div class="am-u-md-4">
        <div class="am-panel am-panel-default">
            <div class="am-panel-hd am-cf">
            <#if shipping == true>
                1)&nbsp;${uiLabelMap.OrderWhereShallWeShipIt}?
            <#else>
                1)&nbsp;${uiLabelMap.OrderInformationAboutYou}
            </#if>
            </div>
            <div class="am-panel-bd am-collapse am-in">
                <div class="am-cf am-padding-xs">
                    <div class="am-form-group">
                        <label class="am-u-md-4">${uiLabelMap.OrderShipToParty}:</label>

                        <div class="am-u-md-4">
                            <select name="shipToCustomerPartyId"
                                    onchange="javascript:submitForm(document.salesentryform, 'SC', null);">
                            <#list cartParties as cartParty>
                                <option value="${cartParty}">${cartParty}</option>
                            </#list>
                            </select>
                        </div>


                        <div class="am-u-md-4">
                            <span>${uiLabelMap.CommonAdd}:</span>
                            <a href="javascript:submitForm(document.salesentryform, 'NA', '');"
                               class="buttontext">${uiLabelMap.PartyAddNewAddress}</a>
                        </div>
                    </div>

                <#if (shoppingCart.getTotalQuantity() > 1) && !shoppingCart.containAllWorkEffortCartItems()> <#-- no splitting when only rental items -->
                    <div class="am-g">
                        <a href="<@ofbizUrl>splitship</@ofbizUrl>"
                           class="buttontext">${uiLabelMap.OrderSplitIntoMultipleShipments}</a>
                        <#if (shoppingCart.getShipGroupSize() > 1)>
                            <div style="color: red;">${uiLabelMap.OrderNOTEMultipleShipmentsExist}.</div>
                        </#if>
                    </div>
                </#if>

                <#if shippingContactMechList?has_content>

                    <#list shippingContactMechList as shippingContactMech>
                        <#assign shippingAddress = shippingContactMech.getRelatedOne("PostalAddress")>

                        <div class="am-u-md-3">
                            <input type="radio" name="shipping_contact_mech_id" value="${shippingAddress.contactMechId}"
                                   onclick="javascript:submitForm(document.salesentryform, 'SA', null);"<#if shoppingCart.getShippingContactMechId()?default("") == shippingAddress.contactMechId>
                                   checked="checked"</#if>/>
                        </div>
                        <div class="am-u-md-9">
                            <#if shippingAddress.toName?has_content><b>${uiLabelMap.CommonTo}
                                :</b>&nbsp;${shippingAddress.toName}<br/></#if>
                            <#if shippingAddress.attnName?has_content><b>${uiLabelMap.PartyAddrAttnName}
                                :</b>&nbsp;${shippingAddress.attnName}<br/></#if>
                            <#if shippingAddress.address1?has_content>${shippingAddress.address1}<br/></#if>
                            <#if shippingAddress.address2?has_content>${shippingAddress.address2}<br/></#if>
                            <#if shippingAddress.city?has_content>${shippingAddress.city}</#if>
                            <#if shippingAddress.stateProvinceGeoId?has_content>
                                <br/>${shippingAddress.stateProvinceGeoId}</#if>
                            <#if shippingAddress.postalCode?has_content><br/>${shippingAddress.postalCode}</#if>
                            <#if shippingAddress.countryGeoId?has_content><br/>${shippingAddress.countryGeoId}</#if>
                            <a href="javascript:submitForm(document.salesentryform, 'EA', '${shippingAddress.contactMechId}');"
                               class="buttontext">${uiLabelMap.CommonUpdate}</a>
                        </div>

                        <#if shippingContactMech_has_next>

                        </#if>
                    </#list>
                </#if>
                </div>
            </div>

        <#-- Party Tax Info -->
        <#-- commented out by default because the TaxAuthority drop-down is just too wide...
        <hr />
        <div>&nbsp;${uiLabelMap.PartyTaxIdentification}</div>
        ${screens.render("component://order/widget/ordermgr/OrderEntryOrderScreens.xml#customertaxinfo")}
        -->
        </div>
    </div>

    <div class="am-u-md-4">
        <div class="am-panel am-panel-default">
            <div class="am-panel-hd am-cf">
            <#if shipping == true>
                2)&nbsp;${uiLabelMap.OrderHowShallWeShipIt}?
            <#else>
                2)&nbsp;${uiLabelMap.OrderOptions}?
            </#if>
            </div>
            <div class="am-panel-bd am-collapse am-in">
                <div class="am-cf am-padding-xs">
                <#if shipping == true>
                <#--所有送货方式-->
                    <#list carrierShipmentMethodList as carrierShipmentMethod>
                        <#assign shippingMethod = carrierShipmentMethod.shipmentMethodTypeId + "@" + carrierShipmentMethod.partyId>
                        <div class="am-g am-form-group">
                        <div class="am-u-md-3">
                            <input type="radio" name="shipping_method" value="${shippingMethod}"
                                   <#if shippingMethod == chosenShippingMethod?default("N@A")>checked="checked"</#if>/>
                        </div>
                        <div class="am-u-md-9">
                            <div>
                                <#if shoppingCart.getShippingContactMechId()?exists>
                                    <#assign shippingEst = shippingEstWpr.getShippingEstimate(carrierShipmentMethod)?default(-1)>
                                </#if>
                                <#if carrierShipmentMethod.partyId != "_NA_">${carrierShipmentMethod.partyId?if_exists}
                                    &nbsp;</#if>${carrierShipmentMethod.description?if_exists}
                                <#if shippingEst?has_content>
                                    - <#if (shippingEst > -1)><@ofbizCurrency amount=shippingEst isoCode=shoppingCart.getCurrency()/><#else>${uiLabelMap.OrderCalculatedOffline}</#if></#if>
                            </div>
                        </div>
                        </div>
                    </#list>
                <hr/>
                    <#if !carrierShipmentMethodList?exists || carrierShipmentMethodList?size == 0>
                        <div class="am-g am-form-group">
                        <div class="am-u-md-3">
                            <input type="radio" name="shipping_method" value="Default" checked="checked"/>
                        </div>
                        <div class="am-u-md-9" >
                           ${uiLabelMap.OrderUseDefault}.
                        </div>
                        </div>
                    </#if>

                    <div class="am-g am-from-group">
                        <div class="am-u-md-9">
                            <h2>${uiLabelMap.OrderShipAllAtOnce}?</h2>
                        </div>

                    </div>

                <div class="am-g am-from-group">
                    <div class="am-u-md-2" >
                        <input type="radio" <#if shoppingCart.getMaySplit()?default("N") == "N">checked="checked"</#if>
                               name="may_split" value="false"/>
                    </div>
                    <div class="am-u-md-10" >
                        <div>${uiLabelMap.OrderPleaseWaitUntilBeforeShipping}.</div>
                    </div>
                </div>
                <div class="am-g am-from-group">
                    <div class="am-u-md-2" >
                        <input <#if shoppingCart.getMaySplit()?default("N") == "Y">checked="checked"</#if> type="radio"
                               name="may_split" value="true"/>
                    </div>
                    <div class="am-u-md-10">
                        ${uiLabelMap.OrderPleaseShipItemsBecomeAvailable}.
                    </div>
                </div>
                <hr/>

                <#else>
                    <input type="hidden" name="shipping_method" value="NO_SHIPPING@_NA_"/>
                    <input type="hidden" name="may_split" value="false"/>
                    <input type="hidden" name="is_gift" value="false"/>
                </#if>

                    <div class="am-g am-from-group">
                        <div class="am-u-md-3">
                        <h2>${uiLabelMap.OrderSpecialInstructions}</h2>
                        </div>

                    <div class="am-u-md-9">
                        <textarea cols="30" rows="3" wrap="hard"
                                  name="shipping_instructions">${shoppingCart.getShippingInstructions()?if_exists}</textarea>
                        </div>
                    </div>

                    <hr/>

                <#if shipping == true>
                    <#if productStore.showCheckoutGiftOptions?if_exists != "N" && giftEnable?if_exists != "N">
                        <div class="am-from-group am-g">
                                <div class="am-form-label am-u-md-4"><b>${uiLabelMap.OrderIsThisGift}</b></div>
                                <div class="am-input-group am-u-md-8">
                                    <label class="am-radio-inline">
                                <input type="radio"
                                       <#if shoppingCart.getIsGift()?default("Y") == "Y">checked="checked"</#if>
                                       name="is_gift" value="true"><span>${uiLabelMap.CommonYes}</span>
                                    </label>
                                    <label class="am-radio-inline">
                                <input type="radio"
                                       <#if shoppingCart.getIsGift()?default("N") == "N">checked="checked"</#if>
                                       name="is_gift" value="false"><span>${uiLabelMap.CommonNo}</span>
                                    </label>
                                </div>
                        </div>

                        <div class="am-form-group am-g">
                            <label class="am-u-md-3">
                                <h2>${uiLabelMap.OrderGiftMessage}</h2>
                            </label>
                            <div class="am-u-md-9">
                                <textarea cols="30" rows="3" wrap="hard"
                                          name="gift_message">${shoppingCart.getGiftMessage()?if_exists}</textarea>
                            </div>
                      </div>
                    <#else>
                        <input type="hidden" name="is_gift" value="false"/>
                    </#if>
                <hr/>
                </#if>

                    <div class="am-g am-form-group">
                     <div class="am-u-md-3">
                        <h2>${uiLabelMap.PartyEmailAddresses}</h2>
                    </div>
                    <div class="am-u-md-9">
                        <div>${uiLabelMap.OrderEmailSentToFollowingAddresses}:</div>

                            <b>
                            <#list emailList as email>
                            ${email.infoString?if_exists}<#if email_has_next>,</#if>
                            </#list>
                            </b>

                        <div>${uiLabelMap.OrderUpdateEmailAddress}
                            <a href="<#if customerDetailLink?exists>${customerDetailLink}${shoppingCart.getPartyId()}"
                                    target="partymgr"
                                    <#else><@ofbizUrl>viewprofile?DONE_PAGE=quickcheckout</@ofbizUrl>"</#if>
                                    class="buttontext">${uiLabelMap.PartyProfile}</a>.
                        </div>
                        <br/>

                        <div>${uiLabelMap.OrderCommaSeperatedEmailAddresses}:</div>
                        <input type="text" size="30" name="order_additional_emails"
                               value="${shoppingCart.getOrderAdditionalEmails()?if_exists}"/>
                    </div>

                </div>
            </div>
        </div>
    </div>
    </div>

    <div class="am-u-md-4">
    <#-- Payment Method Selection -->

        <div class="am-panel am-panel-default">
            <div class="am-panel-hd am-cf">
                3)${uiLabelMap.OrderHowShallYouPay}?
            </div>
            <div class="am-panel-bd am-collapse am-in" >
                <div class="am-cf am-padding-xs">
                    <div class="am-g">
                    <#--如果店铺有信用卡或电子资金转账，用户可以定义对应的账户-->
                        <div class="am-u-md-4">
                            <span>${uiLabelMap.CommonAdd}:</span>
                        </div>
                        <div class="am-u-md-8">
                        <#if productStorePaymentMethodTypeIdMap.CREDIT_CARD?exists>
                            <a href="javascript:submitForm(document.salesentryform, 'NC', '');"
                               class="buttontext">${uiLabelMap.AccountingCreditCard}</a>
                        </#if>
                        <#if productStorePaymentMethodTypeIdMap.EFT_ACCOUNT?exists>
                            <a href="javascript:submitForm(document.salesentryform, 'NE', '');"
                               class="buttontext">${uiLabelMap.AccountingEFTAccount}</a>
                        </#if>
                            </div>
                    </div>
                    </div>
                    <hr/>

                    <div class="am-g">
                        <div class="am-u-md-4">
                        <a href="javascript:submitForm(document.salesentryform, 'SP', '');"
                           class="buttontext">${uiLabelMap.AccountingSplitPayment}</a>
                            </div>
                    </div>
                    <hr/>

                <#if productStorePaymentMethodTypeIdMap.EXT_OFFLINE?exists>
                    <div class="am-g">
                        <div class="am-u-md-3">
                            <input type="radio" name="checkOutPaymentId" value="EXT_OFFLINE"
                                   <#if "EXT_OFFLINE" == checkOutPaymentId>checked="checked"</#if>/>
                        </div>
                        <div class="am-u-md-9">
                            <span>${uiLabelMap.OrderMoneyOrder}</span>
                        </div>
                    </div>
                </#if>
                <#if productStorePaymentMethodTypeIdMap.EXT_COD?exists>
                <div class="am-g">
                    <div class="am-u-md-3">
                        <input type="radio" name="checkOutPaymentId" value="EXT_COD"
                               <#if "EXT_COD" == checkOutPaymentId>checked="checked"</#if>/>
                    </div>
                    <div class="am-u-md-9">
                        <span>${uiLabelMap.OrderCOD}</span>
                    </div>
                </div>

                </#if>
                <#if productStorePaymentMethodTypeIdMap.EXT_WORLDPAY?exists>
                    <div class="am-g">
                        <div class="am-u-md-3">
                            <input type="radio" name="checkOutPaymentId" value="EXT_WORLDPAY"
                                   <#if "EXT_WORLDPAY" == checkOutPaymentId>checked="checked"</#if>/>
                        </div>
                        <div class="am-u-md-9">
                            <span>${uiLabelMap.AccountingPayWithWorldPay}</span>
                        </div>
                    </div>
                </#if>
                <#if productStorePaymentMethodTypeIdMap.EXT_PAYPAL?exists>
                <div class="am-g">
                    <div class="am-u-md-3">
                        <input type="radio" name="checkOutPaymentId" value="EXT_PAYPAL"
                               <#if "EXT_PAYPAL" == checkOutPaymentId>checked="checked"</#if>/>
                    </div>
                    <div class="am-u-md-9" >
                        <span>${uiLabelMap.AccountingPayWithPayPal}</span>
                    </div>
                </div>
                </#if>
                    <!-- begin add by changsy 14.10.28 -->
                <#if productStorePaymentMethodTypeIdMap.EXT_ALIPAY?exists>
                <div class="am-g">
                    <div class="am-u-md-3">
                        <input type="radio" name="checkOutPaymentId" value="EXT_ALIPAY"
                               <#if "EXT_ALIPAY" == checkOutPaymentId>checked="checked"</#if>/>
                    </div>
                    <div class="am-u-md-9">
                        <span>${uiLabelMap.AccountingPayWithAliPay}</span>
                    </div>
                </div>
                </#if>
                <!-- end add by changsy 14.10.28 -->
                <#-- financial accounts -->
                <#list finAccounts as finAccount>
                <div class="am-g">
                    <div class="am-u-md-3" >
                        <input type="radio" name="checkOutPaymentId" value="FIN_ACCOUNT|${finAccount.finAccountId}"
                               <#if "FIN_ACCOUNT" == checkOutPaymentId>checked="checked"</#if>/>
                    </div>
                    <div class="am-u-md-9" >
                        <span>${uiLabelMap.AccountingFinAccount} #${finAccount.finAccountId}</span>
                    </div>
                    </div>
                </#list>

                <#if !paymentMethodList?has_content>
                    <#if (!finAccounts?has_content)>
                        <div class="am-g">
                           <#--${uiLabelMap.AccountingNoPaymentMethods}-->
                        </div>
                    </#if>
                <#else>
                    <#list paymentMethodList as paymentMethod>
                        <#if paymentMethod.paymentMethodTypeId == "CREDIT_CARD">
                            <#if productStorePaymentMethodTypeIdMap.CREDIT_CARD?exists>
                                <#assign creditCard = paymentMethod.getRelatedOne("CreditCard")>
                            <div class="am-g">
                                <div class="am-u-md-3" >
                                    <input type="radio" name="checkOutPaymentId"
                                           value="${paymentMethod.paymentMethodId}"
                                           <#if shoppingCart.isPaymentSelected(paymentMethod.paymentMethodId)>checked="checked"</#if>/>
                                </div>
                                <div class="am-u-md-9" >
                                    <span>CC:&nbsp;${Static["org.ofbiz.party.contact.ContactHelper"].formatCreditCard(creditCard)}</span>
                                    <a href="javascript:submitForm(document.salesentryform, 'EC', '${paymentMethod.paymentMethodId}');"
                                       class="buttontext">${uiLabelMap.CommonUpdate}</a>
                                    <#if paymentMethod.description?has_content><br/><span>(${paymentMethod.description}
                                        )</span></#if>
                                    &nbsp;${uiLabelMap.OrderCardSecurityCode}&nbsp;<input type="text" size="5"
                                                                                          maxlength="10"
                                                                                          name="securityCode_${paymentMethod.paymentMethodId}"
                                                                                          value=""/>
                                </div>
                            </div>

                            </#if>
                        <#elseif paymentMethod.paymentMethodTypeId == "EFT_ACCOUNT">
                            <#if productStorePaymentMethodTypeIdMap.EFT_ACCOUNT?exists>
                                <#assign eftAccount = paymentMethod.getRelatedOne("EftAccount")>
                            <div class="am-g">
                                <div class="am-u-md-3" >
                                    <input type="radio" name="checkOutPaymentId"
                                           value="${paymentMethod.paymentMethodId}"
                                           <#if shoppingCart.isPaymentSelected(paymentMethod.paymentMethodId)>checked="checked"</#if>/>
                                </div>
                                <div class="am-u-md-9" >
                                    <span>${uiLabelMap.AccountingEFTAccount}:&nbsp;${eftAccount.bankName?if_exists}
                                        : ${eftAccount.accountNumber?if_exists}</span>
                                    <a href="javascript:submitForm(document.salesentryform, 'EE', '${paymentMethod.paymentMethodId}');"
                                       class="buttontext">${uiLabelMap.CommonUpdate}</a>
                                    <#if paymentMethod.description?has_content><br/><span>(${paymentMethod.description}
                                        )</span></#if>
                                </div>
                            </div>

                            </#if>
                        <#elseif paymentMethod.paymentMethodTypeId == "GIFT_CARD">
                            <#if productStorePaymentMethodTypeIdMap.GIFT_CARD?exists>
                                <#assign giftCard = paymentMethod.getRelatedOne("GiftCard")>

                                <#if giftCard?has_content && giftCard.cardNumber?has_content>
                                    <#assign giftCardNumber = "">
                                    <#assign pcardNumber = giftCard.cardNumber>
                                    <#if pcardNumber?has_content>
                                        <#assign psize = pcardNumber?length - 4>
                                        <#if 0 < psize>
                                            <#list 0 .. psize-1 as foo>
                                                <#assign giftCardNumber = giftCardNumber + "*">
                                            </#list>
                                            <#assign giftCardNumber = giftCardNumber + pcardNumber[psize .. psize + 3]>
                                        <#else>
                                            <#assign giftCardNumber = pcardNumber>
                                        </#if>
                                    </#if>
                                </#if>
                            <div class="am-g">
                                <div class="am-u-md-3" >
                                    <input type="radio" name="checkOutPaymentId"
                                           value="${paymentMethod.paymentMethodId}"
                                           <#if shoppingCart.isPaymentSelected(paymentMethod.paymentMethodId)>checked="checked"</#if>/>
                                </div>
                                <div class="am-u-md-9" >
                                    <span>${uiLabelMap.AccountingGift}:&nbsp;${giftCardNumber}</span>
                                    <a href="javascript:submitForm(document.salesentryform, 'EG', '${paymentMethod.paymentMethodId}');"
                                       class="buttontext">[${uiLabelMap.CommonUpdate}]</a>
                                    <#if paymentMethod.description?has_content><br/><span>(${paymentMethod.description}
                                        )</span></#if>
                                </div>
                              </div>
                            </#if>
                        </#if>
                    </#list>
                </#if>

                <#-- special billing account functionality to allow use w/ a payment method -->
                <#if productStorePaymentMethodTypeIdMap.EXT_BILLACT?exists>
                    <#if billingAccountList?has_content>

                            <hr/>

                    <div class="am-g">
                        <div class="am-u-md-3" >
                            <select name="billingAccountId">
                                <option value=""></option>
                                <#list billingAccountList as billingAccount>
                                    <#assign availableAmount = billingAccount.accountBalance?double>
                                    <#assign accountLimit = billingAccount.accountLimit?double>
                                    <option value="${billingAccount.billingAccountId}"
                                            <#if billingAccount.billingAccountId == selectedBillingAccountId?default("")>selected="selected"</#if>>${billingAccount.description?default("")}
                                        [${billingAccount.billingAccountId}]
                                        Available: <@ofbizCurrency amount=availableAmount isoCode=billingAccount.accountCurrencyUomId/>
                                        Limit: <@ofbizCurrency amount=accountLimit isoCode=billingAccount.accountCurrencyUomId/></option>
                                </#list>
                            </select>
                        </div>
                        <div class="am-u-md-9" >
                            <span>${uiLabelMap.FormFieldTitle_billingAccountId}</span>
                        </div>
                    </div>

                    <div class="am-g">
                        <div class="am-u-md-3" >
                        ${uiLabelMap.OrderBillUpTo}
                        </div>
                        <div class="am-u-md-9"  align="right">
                            <input type="text" size="5" name="billingAccountAmount" value=""/>
                        </div>
                      </div>
                    </#if>
                </#if>


                <#-- end of special billing account functionality -->
                <#--使用礼品卡-->
              <#--  <#if productStorePaymentMethodTypeIdMap.GIFT_CARD?exists>
                        <hr/>
                <div class="am-g">
                    <div class="am-u-md-3" >
                        <input type="checkbox" name="addGiftCard" value="Y"/>
                    </div>
                    <div class="am-u-md-9" >
                        <span>${uiLabelMap.AccountingUseGiftCardNotOnFile}</span>
                    </div>
                    </div>
                <div class="am-g">
                    <div class="am-u-md-3" >
                        <div>${uiLabelMap.AccountingNumber}</div>
                    </div>
                    <div class="am-u-md-9" >
                        <input type="text" size="15" name="giftCardNumber"
                               value="${(requestParameters.giftCardNumber)?if_exists}"
                               onFocus="document.salesentryform.addGiftCard.checked=true;"/>
                    </div>
                </div>
                    <#if shoppingCart.isPinRequiredForGC(delegator)>
                    <div class="am-g">
                        <div class="am-u-md-3" >
                            <div>${uiLabelMap.AccountingPIN}</div>
                        </div>
                        <div class="am-u-md-9" >
                            <input type="text" size="10" name="giftCardPin"
                                   value="${(requestParameters.giftCardPin)?if_exists}"
                                   onFocus="document.salesentryform.addGiftCard.checked=true;"/>
                        </div>
                    </div>
                    </#if>
                <div class="am-g">
                    <div class="am-u-md-3" >
                        <div>${uiLabelMap.AccountingAmount}</div>
                    </div>
                    <div class="am-u-md-9" >
                        <input type="text" size="6" name="giftCardAmount"
                               value="${(requestParameters.giftCardAmount)?if_exists}"
                               onFocus="document.salesentryform.addGiftCard.checked=true;"/>
                    </div>
                </div>
                </#if>-->
                <#--使用礼品卡-->
                </div>
            </div>
        </div>
    <#-- End Payment Method Selection -->
    </div>
</div>


