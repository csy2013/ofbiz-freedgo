<#--<div class="panel panel-default">
    <div class="panel-heading">
        <h4 class="panel-title">订单基本信息</h4>
    </div>
    <div class="panel-body">-->
    <@htmlScreenTemplate.renderScreenletBegin id="AccountingPaymentInformation" title="订单基本信息"/>
    <div class="table-responsive">
        <table class="table table-bordered">
        <#-- order name -->
        <#if (orderName?has_content)>
            <tr>
                <td align="right" valign="top" width="15%">
                    <span>&nbsp;<b>${uiLabelMap.OrderOrderName}</b> </span>
                </td>

                <td valign="top" width="80%">
                    ${orderName}
                </td>
            </tr>
            
        </#if>
        <#-- order for party -->
        <#if (orderForParty?exists)>
            <tr>
                <td align="right" valign="top" width="15%">
                    <span>&nbsp;<b>${uiLabelMap.OrderOrderFor}</b> </span>
                </td>
                
                <td valign="top" width="80%">
                    ${Static["org.ofbiz.party.party.PartyHelper"].getPartyName(orderForParty, false)} [${orderForParty.partyId}]
                </td>
            </tr>
            
        </#if>
        <#if (cart.getPoNumber()?has_content)>
            <tr>
                <td align="right" valign="top" width="15%">
                    <span>&nbsp;<b>${uiLabelMap.OrderPONumber}</b> </span>
                </td>
                
                <td valign="top" width="80%">
                    ${cart.getPoNumber()}
                </td>
            </tr>
         
        </#if>
        <#if orderTerms?has_content>
            <tr>
                <td align="right" valign="top" width="15%">
                    <div>&nbsp;<b>${uiLabelMap.OrderOrderTerms}</b></div>
                </td>
                
                <td valign="top" width="80%">
                    <table>
                        <tr>
                            <td width="35%"><div><b>${uiLabelMap.OrderOrderTermType}</b></div></td>
                            <td width="10%"><div><b>${uiLabelMap.OrderOrderTermValue}</b></div></td>
                            <td width="10%"><div><b>${uiLabelMap.OrderOrderTermDays}</b></div></td>
                            <td width="45%"><div><b>${uiLabelMap.CommonDescription}</b></div></td>
                        </tr>
                        <tr><td colspan="4"><hr /></td></tr>
                        <#assign index=0/>
                        <#list orderTerms as orderTerm>
                        <tr>
                            <td width="35%"><div>${orderTerm.getRelatedOne("TermType").get("description",locale)}</div></td>
                            <td width="10%"><div>${orderTerm.termValue?default("")}</div></td>
                            <td width="10%"><div>${orderTerm.termDays?default("")}</div></td>
                            <td width="45%"><div>${orderTerm.textValue?default("")}</div></td>
                        </tr>
                            <#if orderTerms.size()&lt;index>
                        <tr><td colspan="4"><hr /></td></tr>
                            </#if>
                            <#assign index=index+1/>
                        </#list>
                    </table>
                </td>
            </tr>
            
        </#if>
        <#-- tracking number -->
        <#if trackingNumber?has_content>
            <tr>
                <td align="right" valign="top" width="15%">
                    <div>&nbsp;<b>${uiLabelMap.OrderTrackingNumber}</b></div>
                </td>
                
                <td valign="top" width="80%">
                    <#-- TODO: add links to UPS/FEDEX/etc based on carrier partyId  -->
                    <div>${trackingNumber}</div>
                </td>
            </tr>
            
        </#if>
        <#-- splitting preference -->
            <tr>
                <td align="right" valign="top" width="15%">
                    <div>&nbsp;<b>${uiLabelMap.OrderSplittingPreference}</b></div>
                </td>
                
                <td valign="top" width="80%">
                    <div>
                        <#if maySplit?default("N") == "N">${uiLabelMap.FacilityWaitEntireOrderReady}</#if>
                        <#if maySplit?default("Y") == "Y">${uiLabelMap.FacilityShipAvailable}</#if>
                    </div>
                </td>
            </tr>
        <#-- shipping instructions -->
        <#if shippingInstructions?has_content>
            
            <tr>
                <td align="right" valign="top" width="15%">
                    <div>&nbsp;<b>${uiLabelMap.OrderSpecialInstructions}</b></div>
                </td>
                
                <td valign="top" width="80%">
                    <div>${shippingInstructions}</div>
                </td>
            </tr>
        </#if>
            
        <#if orderType != "PURCHASE_ORDER" && (productStore.showCheckoutGiftOptions)?if_exists != "N">
        <#-- gift settings -->
            <tr>
                <td align="right" valign="top" width="15%">
                    <div>&nbsp;<b>${uiLabelMap.OrderGift}</b></div>
                </td>
                
                <td valign="top" width="80%">
                    <div>
                        <#if isGift?default("N") == "N">${uiLabelMap.OrderThisOrderNotGift}</#if>
                        <#if isGift?default("N") == "Y">${uiLabelMap.OrderThisOrderGift}</#if>
                    </div>
                </td>
            </tr>
            
            <#if giftMessage?has_content>
            <tr>
                <td align="right" valign="top" width="15%">
                    <div>&nbsp;<b>${uiLabelMap.OrderGiftMessage}</b></div>
                </td>
                
                <td valign="top" width="80%">
                    <div>${giftMessage}</div>
                </td>
            </tr>
            
            </#if>
        </#if>
        <#if shipAfterDate?has_content>
            <tr>
                <td align="right" valign="top" width="15%">
                    <div>&nbsp;<b>${uiLabelMap.OrderShipAfterDate}</b></div>
                </td>
                
                <td valign="top" width="80%">
                    <div>${shipAfterDate}</div>
                </td>
            </tr>
        </#if>
        <#if shipBeforeDate?has_content>
            <tr>
                <td align="right" valign="top" width="15%">
                    <div>&nbsp;<b>${uiLabelMap.OrderShipBeforeDate}</b></div>
                </td>
                
                <td valign="top" width="80%">
                  <div>${shipBeforeDate}</div>
                </td>
            </tr>
        </#if>
        </table>
    </div>

