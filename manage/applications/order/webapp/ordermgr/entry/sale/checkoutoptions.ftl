<div class="wizard-step-4">
<#assign shipping = !shoppingCart.containAllWorkEffortCartItems()>
    <input type="hidden" name="checkoutpage" value="quick"/>
    <input type="hidden" name="BACK_PAGE" value="quickcheckout"/>
<#--<span>${uiLabelMap.CommonAdd}:</span>-->
<#--<a href="javascript:submitForm(document.salesentryform, 'NA', '');" class="buttontext">${uiLabelMap.PartyAddNewAddress}</a>-->
<@htmlScreenTemplate.renderModalPage id="PartyAddNewAddress" name="PartyAddNewAddress" modalTitle="${StringUtil.wrapString(uiLabelMap.PartyAddNewAddress)}"
modalUrl="updateCheckoutOptionsForSale/editcontactmech" description="${uiLabelMap.PartyAddNewAddress}"
targetParameterIter="DONE_PAGE:'quickcheckout',preContactMechTypeId:'POSTAL_ADDRESS',contactMechPurposeTypeId:'SHIPPING_LOCATION',partyId:'${shoppingCart.getPartyId()}'"/>
<#if (shoppingCart.getTotalQuantity() > 1) && !shoppingCart.containAllWorkEffortCartItems()> <#-- no splitting when only rental items -->
    <a href="<@ofbizUrl>splitship</@ofbizUrl>" class="btn btn-primary btn-sm">${uiLabelMap.OrderSplitIntoMultipleShipments}</a>
    <#if (shoppingCart.getShipGroupSize() > 1)>
        <div style="color: red;">${uiLabelMap.OrderNOTEMultipleShipmentsExist}.</div>
    </#if>
</#if>
    <br/> <br/>
    <div class="table-responsive">
        <table width="100%" class="table">
            <tr valign="top">
                <td height="100%">
                <#if shipping == true>
                    <#assign paneltitle="1)&nbsp;${uiLabelMap.OrderWhereShallWeShipIt}"/>
                <#else>
                    <#assign paneltitle="1)&nbsp;${uiLabelMap.OrderInformationAboutYou}"/>
                </#if>
                    <@htmlScreenTemplate.renderScreenletBegin id="AccountingPaymentInformation" title="${paneltitle}"/>
                   <#-- <div class="panel panel-default">
                        <div class="panel-heading">

                        </div>
                        <div class="panel-body table-responsive">-->
                            <div class="table-responsive">
                            <table class="table">
                                <tr>
                                    <td colspan="2">
                                        <span>${uiLabelMap.OrderShipToParty}:</span>
                                        <select name="shipToCustomerPartyId" onchange="submitForm(document.salesentryform, 'SC', null);">
                                        <#if cartparties?has_content>
                                            <#list cartParties as cartParty>
                                                <option value="${cartParty}">${cartParty}</option>
                                            </#list>
                                        </#if>
                                        </select>
                                    </td>
                                </tr>

                            <#if shippingContactMechList?has_content>

                                <#list shippingContactMechList as shippingContactMech>
                                    <#assign shippingAddress = shippingContactMech.getRelatedOne("PostalAddress")>
                                    <tr>
                                        <td valign="middle" width="1%">
                                            <input type="radio" name="shipping_contact_mech_id" value="${shippingAddress.contactMechId}"
                                                   onclick="submitForm(document.salesentryform, 'SA', null);"<#if shoppingCart.getShippingContactMechId()?default("") == shippingAddress.contactMechId>
                                                   checked="checked"</#if>/>
                                        </td>
                                        <td valign="top" width="99%">
                                            <div>

                                                <#assign country = shippingAddress.getRelatedOneCache("CountryGeo")>
                                            ${country.get("geoName", locale)?default(country.geoId)}
                                                <#if shippingAddress.stateProvinceGeoId?has_content>
                                                    <#assign stateProvince = shippingAddress.getRelatedOneCache("StateProvinceGeo")>
                                                ${stateProvince.abbreviation?default(stateProvince.geoId)}
                                                </#if>
                                                <#if shippingAddress.city?has_content>
                                                    <#assign city = shippingAddress.getRelatedOneCache("CityGeo")>
                                                ${city.get("geoName", locale)?default(city.geoId)}
                                                </#if>
                                                <#if shippingAddress.countyGeoId?has_content>
                                                    <#assign county = shippingAddress.getRelatedOneCache("CountyGeo")>
                                                ${county.get("geoName", locale)?default(county.geoId)}
                                                </#if>,
                                               <#-- <#if shippingAddress.countryGeoId?has_content><br/>${shippingAddress.countryGeoId}</#if>
                                                <#if shippingAddress.stateProvinceGeoId?has_content>${shippingAddress.stateProvinceGeoId}</#if>
                                                <#if shippingAddress.city?has_content>${shippingAddress.city}</#if>
                                                <#if shippingAddress.countyGeoId?has_content>${shippingAddress.countyGeoId}</#if>-->
                                                <#if shippingAddress.postalCode?has_content><br/>${shippingAddress.postalCode}</#if>
                                                <#if shippingAddress.address1?has_content>${shippingAddress.address1}<br/> </#if>
                                                <#if shippingAddress.address2?has_content>${shippingAddress.address2}<br/></#if>
                                                <#if shippingAddress.toName?has_content><b>${uiLabelMap.CommonTo}:</b>&nbsp;${shippingAddress.toName}<br/></#if>
                                                <#if shippingAddress.attnName?has_content><b>${uiLabelMap.PartyAddrAttnName}:</b>&nbsp;${shippingAddress.attnName}<br/></#if>

                                                <#--<a href="javascript:submitForm(document.salesentryform, 'EA', '${shippingAddress.contactMechId}');"
                                                   class="buttontext">${uiLabelMap.CommonUpdate}</a>-->

                                                <@htmlScreenTemplate.renderModalPage id="PartyUpdateAddress_${shippingContactMech_index}"  name="PartyUpdateAddress_${shippingContactMech_index}"
                                                modalTitle="修改客户收货地址"
                                                modalUrl="updateCheckoutOptionsForSale/editcontactmech"
                                                description="${uiLabelMap.CommonUpdate}"
                                                targetParameterIter="DONE_PAGE:'quickcheckout',contactMechId:'${shippingAddress.contactMechId}',partyId:'${shoppingCart.getPartyId()}'"/>

                                            </div>
                                        </td>
                                    </tr>

                                </#list>
                            </#if>
                            </table>

                        <#-- Party Tax Info -->
                        <#-- commented out by default because the TaxAuthority drop-down is just too wide...
                        <hr />
                        <div>&nbsp;${uiLabelMap.PartyTaxIdentification}</div>
                        ${screens.render("component://order/widget/ordermgr/OrderEntryOrderScreens.xml#customertaxinfo")}
                        -->

                    </div>
                <@htmlScreenTemplate.renderScreenletEnd/>
                </td>

                <td height="100%">
                <@htmlScreenTemplate.renderScreenletBegin id="AccountingPaymentInformation" title="${paneltitle}"/>
                  <#--  <div class="panel panel-default">
                        <div class="panel-heading">

                        <#if shipping == true>
                            <@assign panel-title="1)&nbsp;${uiLabelMap.OrderWhereShallWeShipIt}"/>
                        <#else>
                            <@assign panel-title="1)&nbsp;${uiLabelMap.OrderOptions}"/>
                        </#if>


                        </div>-->
                        <div class="table-responsive">
                            <table class="table">
                            <#if shipping == true>
                            <#--所有送货方式-->
                                <#list carrierShipmentMethodList as carrierShipmentMethod>
                                    <#assign shippingMethod = carrierShipmentMethod.shipmentMethodTypeId + "@" + carrierShipmentMethod.partyId>
                                    <tr>
                                        <td width="1%" valign="top">
                                            <input type="radio" name="shipping_method" value="${shippingMethod}"
                                                   <#if shippingMethod == chosenShippingMethod?default("N@A")>checked="checked"</#if>/>
                                        </td>
                                        <td valign="top">
                                            <div>
                                                <#if shoppingCart.getShippingContactMechId()?exists>
                                                    <#assign shippingEst = shippingEstWpr.getShippingEstimate(carrierShipmentMethod)?default(-1)>
                                                </#if>
                                                <#if carrierShipmentMethod.partyId != "_NA_">${carrierShipmentMethod.partyId?if_exists}
                                                    &nbsp;</#if>${carrierShipmentMethod.description?if_exists}
                                                <#if shippingEst?has_content>
                                                    - <#if (shippingEst > -1)><@ofbizCurrency amount=shippingEst isoCode=shoppingCart.getCurrency()/><#else>${uiLabelMap.OrderCalculatedOffline}</#if></#if>
                                            </div>
                                        </td>
                                    </tr>
                                </#list>
                                <#if !carrierShipmentMethodList?exists || carrierShipmentMethodList?size == 0>
                                    <tr>
                                        <td width="1%" valign="top">
                                            <input type="radio" name="shipping_method" value="Default" checked="checked"/>
                                        </td>
                                        <td valign="top">
                                            <div>${uiLabelMap.OrderUseDefault}.</div>
                                        </td>
                                    </tr>
                                </#if>
                                <tr>
                                    <td colspan="2">
                                        <h5>${uiLabelMap.OrderShipAllAtOnce}?</h5>
                                    </td>
                                </tr>
                                <tr>
                                    <td valign="top">
                                        <input type="radio" <#if shoppingCart.getMaySplit()?default("N") == "N">checked="checked"</#if> name="may_split" value="false"/>
                                    </td>
                                    <td valign="top">
                                        <div>${uiLabelMap.OrderPleaseWaitUntilBeforeShipping}.</div>
                                    </td>
                                </tr>
                                <tr>
                                    <td valign="top">
                                        <input <#if shoppingCart.getMaySplit()?default("N") == "Y">checked="checked"</#if> type="radio" name="may_split" value="true"/>
                                    </td>
                                    <td valign="top">
                                        <div>${uiLabelMap.OrderPleaseShipItemsBecomeAvailable}.</div>
                                    </td>
                                </tr>

                            <#else>
                                <input type="hidden" name="shipping_method" value="NO_SHIPPING@_NA_"/>
                                <input type="hidden" name="may_split" value="false"/>
                                <input type="hidden" name="is_gift" value="false"/>
                            </#if>
                                <tr>
                                    <td colspan="2">
                                        <h5>${uiLabelMap.OrderSpecialInstructions}</h5>
                                    </td>
                                </tr>
                                <tr>
                                    <td colspan="2">
                                        <textarea cols="30" rows="3" wrap="hard" name="shipping_instructions">${shoppingCart.getShippingInstructions()?if_exists}</textarea>
                                    </td>
                                </tr>
                            <#if shipping == true>
                            <#--<#if productStore.showCheckoutGiftOptions?if_exists != "N" && giftEnable?if_exists != "N">

                                <tr>
                                    <td colspan="2">
                                        <div>
                                            <span class="h5"><b>${uiLabelMap.OrderIsThisGift}</b></span>
                                            <input type="radio" <#if shoppingCart.getIsGift()?default("Y") == "Y">checked="checked"</#if> name="is_gift"
                                                   value="true"><span>${uiLabelMap.CommonYes}</span>
                                            <input type="radio" <#if shoppingCart.getIsGift()?default("N") == "N">checked="checked"</#if> name="is_gift"
                                                   value="false"><span>${uiLabelMap.CommonNo}</span>
                                        </div>
                                    </td>
                                </tr>

                                <tr>
                                    <td colspan="2">
                                        <h5>${uiLabelMap.OrderGiftMessage}</h5>
                                    </td>
                                </tr>
                                <tr>
                                    <td colspan="2">
                                        <textarea cols="30" rows="3" wrap="hard" name="gift_message">${shoppingCart.getGiftMessage()?if_exists}</textarea>
                                    </td>
                                </tr>
                            <#else>-->
                                <input type="hidden" name="is_gift" value="false"/>
                            <#--</#if>-->
                            </#if>

                                <tr>
                                    <td colspan="2">
                                        <h5>${uiLabelMap.PartyEmailAddresses}</h5>
                                    </td>
                                </tr>
                                <tr>
                                    <td colspan="2">
                                    <#if emailList?has_content>
                                        <div>${uiLabelMap.OrderEmailSentToFollowingAddresses}:</div>
                                        <div>
                                            <b>
                                                <#list emailList as email>
                                                ${email.infoString?if_exists}<#if email_has_next>,</#if>
                                                </#list>
                                            </b>
                                        </div>
                                    </#if>
                                    <#--<div>${uiLabelMap.OrderUpdateEmailAddress} <a href="<#if customerDetailLink?exists>${customerDetailLink}${shoppingCart.getPartyId()}"
                                            target="partymgr"
                                            <#else><@ofbizUrl>viewprofile?DONE_PAGE=quickcheckout</@ofbizUrl>"</#if> class="buttontext">${uiLabelMap.PartyProfile}</a>.
                                    </div>-->
                                        <br/>

                                        <div>${uiLabelMap.OrderCommaSeperatedEmailAddresses}:</div>
                                        <input type="text" size="30" name="order_additional_emails" value="${shoppingCart.getOrderAdditionalEmails()?if_exists}"/>
                                    </td>
                                </tr>
                            </table>
                        </div>
                     <@htmlScreenTemplate.renderScreenletEnd/>

                </td>


                <td height="100%">
                <#-- Payment Method Selection -->
                <@htmlScreenTemplate.renderScreenletBegin id="AccountingPaymentInformation" title="${uiLabelMap.OrderHowShallYouPay}?"/>
                    <#--<div class="panel panel-default">
                        <div class="panel-heading">
                            <h3 class="panel-title">3)${uiLabelMap.OrderHowShallYouPay}?</h3>
                        </div>-->
                        <div class="table-responsive">
                            <table class="table">
                            <#--<tr>
                                <td colspan="2">
                                &lt;#&ndash;如果店铺有信用卡或电子资金转账，用户可以定义对应的账户&ndash;&gt;
                                    <span>${uiLabelMap.CommonAdd}:</span>
                                <#if productStorePaymentMethodTypeIdMap.CREDIT_CARD?exists>
                                    <a href="javascript:submitForm(document.salesentryform, 'NC', '');" class="buttontext">${uiLabelMap.AccountingCreditCard}</a>
                                </#if>
                                <#if productStorePaymentMethodTypeIdMap.EFT_ACCOUNT?exists>
                                    <a href="javascript:submitForm(document.salesentryform, 'NE', '');" class="buttontext">${uiLabelMap.AccountingEFTAccount}</a>
                                </#if>
                                </td>
                            </tr>-->

                                <tr>
                                    <td colspan="2" align="center">
                                        <a href="javascript:submitForm(document.salesentryform, 'SP', '');" class="buttontext">${uiLabelMap.AccountingSplitPayment}</a>
                                    </td>
                                </tr>
                            <#if productStorePaymentMethodTypeIdMap?has_content>
                                <#if productStorePaymentMethodTypeIdMap.EXT_OFFLINE?exists>
                                    <tr>
                                        <td width="1%">
                                            <input type="radio" name="checkOutPaymentId" value="EXT_OFFLINE" <#if "EXT_OFFLINE" == checkOutPaymentId>checked="checked"</#if>/>
                                        </td>
                                        <td width="50%">
                                            <span>${uiLabelMap.OrderMoneyOrder}</span>
                                        </td>
                                    </tr>
                                </#if>
                                <#if productStorePaymentMethodTypeIdMap.EXT_COD?exists>
                                    <tr>
                                        <td width="1%">
                                            <input type="radio" name="checkOutPaymentId" value="EXT_COD" <#if "EXT_COD" == checkOutPaymentId>checked="checked"</#if>/>
                                        </td>
                                        <td width="50%">
                                            <span>${uiLabelMap.OrderCOD}</span>
                                        </td>
                                    </tr>
                                </#if>
                                <#if productStorePaymentMethodTypeIdMap.EXT_WORLDPAY?exists>
                                    <tr>
                                        <td width="1%">
                                            <input type="radio" name="checkOutPaymentId" value="EXT_WORLDPAY" <#if "EXT_WORLDPAY" == checkOutPaymentId>checked="checked"</#if>/>
                                        </td>
                                        <td width="50%">
                                            <span>${uiLabelMap.AccountingPayWithWorldPay}</span>
                                        </td>
                                    </tr>
                                </#if>
                                <#if productStorePaymentMethodTypeIdMap.EXT_PAYPAL?exists>
                                    <tr>
                                        <td width="1%">
                                            <input type="radio" name="checkOutPaymentId" value="EXT_PAYPAL" <#if "EXT_PAYPAL" == checkOutPaymentId>checked="checked"</#if>/>
                                        </td>
                                        <td width="50%">
                                            <span>${uiLabelMap.AccountingPayWithPayPal}</span>
                                        </td>
                                    </tr>
                                </#if>
                                <!-- begin add by changsy 14.10.28 -->
                                <#if productStorePaymentMethodTypeIdMap.EXT_ALIPAY?exists>
                                    <tr>
                                        <td width="1%">
                                            <input type="radio" name="checkOutPaymentId" value="EXT_ALIPAY" <#if "EXT_ALIPAY" == checkOutPaymentId>checked="checked"</#if>/>
                                        </td>
                                        <td width="50%">
                                            <span>${uiLabelMap.AccountingPayWithAliPay}</span>
                                        </td>
                                    </tr>
                                </#if>

                            </#if>
                            <#-- financial accounts -->
                            <#--   <#list finAccounts as finAccount>
                                   <tr>
                                       <td width="1%">
                                           <input type="radio" name="checkOutPaymentId" value="FIN_ACCOUNT|${finAccount.finAccountId}"
                                                  <#if "FIN_ACCOUNT" == checkOutPaymentId>checked="checked"</#if>/>
                                       </td>
                                       <td width="50%">
                                           <span>${uiLabelMap.AccountingFinAccount} #${finAccount.finAccountId}</span>
                                       </td>
                                   </tr>
                               </#list>-->

                            <#--  <#if !paymentMethodList?has_content>
                                  <#if (!finAccounts?has_content)>
                                      <tr>
                                          <td colspan="2">
                                              <div><b>${uiLabelMap.AccountingNoPaymentMethods}</b></div>
                                          </td>
                                      </tr>
                                  </#if>
                              <#else>
                                  <#list paymentMethodList as paymentMethod>
                                      <#if paymentMethod.paymentMethodTypeId == "CREDIT_CARD">
                                          <#if productStorePaymentMethodTypeIdMap.CREDIT_CARD?exists>
                                              <#assign creditCard = paymentMethod.getRelatedOne("CreditCard")>
                                              <tr>
                                                  <td width="1%">
                                                      <input type="radio" name="checkOutPaymentId" value="${paymentMethod.paymentMethodId}"
                                                             <#if shoppingCart.isPaymentSelected(paymentMethod.paymentMethodId)>checked="checked"</#if>/>
                                                  </td>
                                                  <td width="50%">
                                                      <span>CC:&nbsp;${Static["org.ofbiz.party.contact.ContactHelper"].formatCreditCard(creditCard)}</span>
                                                      <a href="javascript:submitForm(document.salesentryform, 'EC', '${paymentMethod.paymentMethodId}');"
                                                         class="buttontext">${uiLabelMap.CommonUpdate}</a>
                                                      <#if paymentMethod.description?has_content><br/><span>(${paymentMethod.description})</span></#if>
                                                      &nbsp;${uiLabelMap.OrderCardSecurityCode}&nbsp;<input type="text" size="5" maxlength="10"
                                                                                                            name="securityCode_${paymentMethod.paymentMethodId}" value=""/>
                                                  </td>
                                              </tr>
                                          </#if>
                                      <#elseif paymentMethod.paymentMethodTypeId == "EFT_ACCOUNT">
                                          <#if productStorePaymentMethodTypeIdMap.EFT_ACCOUNT?exists>
                                              <#assign eftAccount = paymentMethod.getRelatedOne("EftAccount")>
                                              <tr>
                                                  <td width="1%">
                                                      <input type="radio" name="checkOutPaymentId" value="${paymentMethod.paymentMethodId}"
                                                             <#if shoppingCart.isPaymentSelected(paymentMethod.paymentMethodId)>checked="checked"</#if>/>
                                                  </td>
                                                  <td width="50%">
                                                      <span>${uiLabelMap.AccountingEFTAccount}:&nbsp;${eftAccount.bankName?if_exists}: ${eftAccount.accountNumber?if_exists}</span>
                                                      <a href="javascript:submitForm(document.salesentryform, 'EE', '${paymentMethod.paymentMethodId}');"
                                                         class="buttontext">${uiLabelMap.CommonUpdate}</a>
                                                      <#if paymentMethod.description?has_content><br/><span>(${paymentMethod.description})</span></#if>
                                                  </td>
                                              </tr>
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

                                              <tr>
                                                  <td width="1%">
                                                      <input type="radio" name="checkOutPaymentId" value="${paymentMethod.paymentMethodId}"
                                                             <#if shoppingCart.isPaymentSelected(paymentMethod.paymentMethodId)>checked="checked"</#if>/>
                                                  </td>
                                                  <td width="50%">
                                                      <span>${uiLabelMap.AccountingGift}:&nbsp;${giftCardNumber}</span>
                                                      <a href="javascript:submitForm(document.salesentryform, 'EG', '${paymentMethod.paymentMethodId}');"
                                                         class="buttontext">[${uiLabelMap.CommonUpdate}]</a>
                                                      <#if paymentMethod.description?has_content><br/><span>(${paymentMethod.description})</span></#if>
                                                  </td>
                                              </tr>
                                          </#if>
                                      </#if>
                                  </#list>
                              </#if>-->

                            <#-- special billing account functionality to allow use w/ a payment method -->
                            <#--  <#if productStorePaymentMethodTypeIdMap.EXT_BILLACT?exists>
                                  <#if billingAccountList?has_content>
                                      <tr>
                                          <td colspan="2">
                                              <hr/>
                                          </td>
                                      </tr>
                                      <tr>
                                          <td width="1%">
                                              <select name="billingAccountId">
                                                  <option value=""></option>
                                                  <#list billingAccountList as billingAccount>
                                                      <#assign availableAmount = billingAccount.accountBalance?double>
                                                      <#assign accountLimit = billingAccount.accountLimit?double>
                                                      <option value="${billingAccount.billingAccountId}"
                                                              <#if billingAccount.billingAccountId == selectedBillingAccountId?default("")>selected="selected"</#if>>${billingAccount.description?default("")}
                                                          [${billingAccount.billingAccountId}] Available: <@ofbizCurrency amount=availableAmount isoCode=billingAccount.accountCurrencyUomId/>
                                                          Limit: <@ofbizCurrency amount=accountLimit isoCode=billingAccount.accountCurrencyUomId/></option>
                                                  </#list>
                                              </select>
                                          </td>
                                          <td width="50%">
                                              <span>${uiLabelMap.FormFieldTitle_billingAccountId}</span>
                                          </td>
                                      </tr>
                                      <tr>
                                          <td width="1%" align="right">
                                              <input type="text" size="5" name="billingAccountAmount" value=""/>
                                          </td>
                                          <td width="50%">
                                          ${uiLabelMap.OrderBillUpTo}
                                          </td>
                                      </tr>
                                  </#if>
                              </#if>-->


                            <#-- end of special billing account functionality -->
                            <#--使用礼品卡-->
                            <#-- <#if productStorePaymentMethodTypeIdMap.GIFT_CARD?exists>

                                 <tr>
                                     <td width="1%">
                                         <input type="checkbox" name="addGiftCard" value="Y"/>
                                     </td>
                                     <td width="50%">
                                         <span>${uiLabelMap.AccountingUseGiftCardNotOnFile}</span>
                                     </td>
                                 </tr>
                                 <tr>
                                     <td width="1%">
                                         <div>${uiLabelMap.AccountingNumber}</div>
                                     </td>
                                     <td width="50%">
                                         <input type="text" size="15" name="giftCardNumber" value="${(requestParameters.giftCardNumber)?if_exists}"
                                                onFocus="document.salesentryform.addGiftCard.checked=true;"/>
                                     </td>
                                 </tr>
                                 <#if shoppingCart.isPinRequiredForGC(delegator)>
                                     <tr>
                                         <td width="1%">
                                             <div>${uiLabelMap.AccountingPIN}</div>
                                         </td>
                                         <td width="50%">
                                             <input type="text" size="10" name="giftCardPin" value="${(requestParameters.giftCardPin)?if_exists}"
                                                    onFocus="document.salesentryform.addGiftCard.checked=true;"/>
                                         </td>
                                     </tr>
                                 </#if>
                                 <tr>
                                     <td width="1%">
                                         <div>${uiLabelMap.AccountingAmount}</div>
                                     </td>
                                     <td width="50%">
                                         <input type="text" size="6" name="giftCardAmount" value="${(requestParameters.giftCardAmount)?if_exists}"
                                                onFocus="document.salesentryform.addGiftCard.checked=true;"/>
                                     </td>
                                 </tr>
                             </#if>-->
                            <#--使用礼品卡-->
                            </table>
                        </div>

                <#-- End Payment Method Selection -->
                    <@htmlScreenTemplate.renderScreenletEnd/>
                </td>
            </tr>
        </table>
    </div>
    <div class="row">
        <div class="col-md-12">
            <a href="javascript:submitForm(document.salesentryform, 'DN', '');" class="btn btn-white btn-sm  pull-right">完成订单</a>
        </div>
    </div>
</div>