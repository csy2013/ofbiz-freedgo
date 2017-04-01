
<#if !creditCard?has_content>
    <#assign creditCard = requestParameters>
</#if>

<#if !paymentMethod?has_content>
    <#assign paymentMethod = requestParameters>
</#if>
<div class="am-form-group am-g" >
    <label class="am-control-label am-u-md-5 am-u-lg-5" for="companyNameOnCard"<b>${uiLabelMap.AccountingCompanyNameCard}</b></label>
    <div class="am-u-md-7 am-u-lg-7 am-u-end">
    <div class="am-input-group">
      <input type="text" class="am-form-field am-input-sm" size="30" maxlength="60" name="companyNameOnCard" value="${creditCard.companyNameOnCard?if_exists}"/>
      </div>
    </div>
</div>
<div class="am-form-group am-g">
    <label class="am-control-label am-u-md-5 am-u-lg-5" for="titleOnCard"><b>${uiLabelMap.AccountingPrefixCard}</b></label>
    <div class="am-u-md-7 am-u-lg-7 am-u-end">
    <div class="am-input-group">
      <select name="titleOnCard"">
        <option value="">${uiLabelMap.CommonSelectOne}</option>
        <option<#if ((creditCard.titleOnCard)?default("") == "${uiLabelMap.CommonTitleMr}")> selected="selected"</#if>>${uiLabelMap.CommonTitleMr}</option>
        <option<#if ((creditCard.titleOnCard)?default("") == "Mrs.")> selected="selected"</#if>>${uiLabelMap.CommonTitleMrs}</option>
        <option<#if ((creditCard.titleOnCard)?default("") == "Ms.")> selected="selected"</#if>>${uiLabelMap.CommonTitleMs}</option>
        <option<#if ((creditCard.titleOnCard)?default("") == "Dr.")> selected="selected"</#if>>${uiLabelMap.CommonTitleDr}</option>
      </select>
    </div>
    </div>
  </div>
<div class="am-form-group am-g">
    <label class="am-control-label am-u-md-5 am-u-lg-5" for="firstNameOnCard"><b>${uiLabelMap.AccountingFirstNameCard}</b></label>
    <div class="am-u-md-7 am-u-lg-7 am-u-end">
        <div class="am-input-group">
      <input class="am-form-field am-input-sm" type="text" size="20" maxlength="60" name="firstNameOnCard" value="${(creditCard.firstNameOnCard)?if_exists}"/>
    <#if showToolTip?has_content><span class="tooltip">${uiLabelMap.CommonRequired}</span><#else>*</#if>
            </div>
        </div>
  </div>
<div class="am-form-group am-g">
    <label class="am-control-label am-u-md-5 am-u-lg-5" for="middleNameOnCard"><b>${uiLabelMap.AccountingMiddleNameCard}</b></label>
    <div class="am-u-md-7 am-u-lg-7 am-u-end">
        <div class="am-input-group">
      <input class="am-form-field am-input-sm" type="text" size="15" maxlength="60" name="middleNameOnCard" value="${(creditCard.middleNameOnCard)?if_exists}"/>
    </div>
    </div>
  </div>
<div class="am-form-group am-g">
    <label class="am-control-label am-u-md-5 am-u-lg-5" for="lastNameOnCard"><b>${uiLabelMap.AccountingLastNameCard}</b></label>
    <div class="am-u-md-7 am-u-lg-7 am-u-end">
        <div class="am-input-group">
      <input class="am-form-field am-input-sm" type="text" size="20" maxlength="60" name="lastNameOnCard" value="${(creditCard.lastNameOnCard)?if_exists}"/>
    <#if showToolTip?has_content><span class="tooltip">${uiLabelMap.CommonRequired}</span><#else>*</#if>
            </div>
        </div>
  </div>
<div class="am-form-group am-g">
    <label class="am-control-label am-u-md-5 am-u-lg-5" for="suffixOnCard"><b>${uiLabelMap.AccountingSuffixCard}</b></label>
    <div class="am-u-md-7 am-u-lg-7 am-u-end">
    <div class="am-input-group">
      <select name="suffixOnCard"">
        <option value="">${uiLabelMap.CommonSelectOne}</option>
        <option<#if ((creditCard.suffixOnCard)?default("") == "Jr.")> selected="selected"</#if>>Jr.</option>
        <option<#if ((creditCard.suffixOnCard)?default("") == "Sr.")> selected="selected"</#if>>Sr.</option>
        <option<#if ((creditCard.suffixOnCard)?default("") == "I")> selected="selected"</#if>>I</option>
        <option<#if ((creditCard.suffixOnCard)?default("") == "II")> selected="selected"</#if>>II</option>
        <option<#if ((creditCard.suffixOnCard)?default("") == "III")> selected="selected"</#if>>III</option>
        <option<#if ((creditCard.suffixOnCard)?default("") == "IV")> selected="selected"</#if>>IV</option>
        <option<#if ((creditCard.suffixOnCard)?default("") == "V")> selected="selected"</#if>>V</option>
      </select>
    </div>
        </div>
  </div>

<div class="am-form-group am-g">
    <label class="am-control-label am-u-md-5 am-u-lg-5" for="cardType"><b>${uiLabelMap.AccountingCardType}</b></label>
    <div class="am-u-md-7 am-u-lg-7 am-u-end">
        <div class="am-input-group">
      <select name="cardType"">
        <#if creditCard.cardType?exists>
          <option>${creditCard.cardType}</option>
          <option value="${creditCard.cardType}">---</option>
        </#if>
        ${screens.render("component://common/widget/CommonScreens.xml#cctypes")}
      </select>
    <#if showToolTip?has_content><span class="tooltip">${uiLabelMap.CommonRequired}</span><#else>*</#if></div>
  </div>
  </div>
<div class="am-form-group am-g">
    <label class="am-control-label am-u-md-5 am-u-lg-5" ><b>${uiLabelMap.AccountingCardNumber}</b></label>
    <div class="am-u-md-7 am-u-lg-7 am-u-end">
        <div class="am-input-group">
        <#if creditCard?has_content>
            <#if cardNumberMinDisplay?has_content>
                <#-- create a display version of the card where all but the last four digits are * -->
                <#assign cardNumberDisplay = "">
                <#assign cardNumber = creditCard.cardNumber?if_exists>
                <#if cardNumber?has_content>
                    <#assign size = cardNumber?length - 4>
                    <#if (size > 0)>
                        <#list 0 .. size-1 as foo>
                            <#assign cardNumberDisplay = cardNumberDisplay + "*">
                        </#list>
                        <#assign cardNumberDisplay = cardNumberDisplay + cardNumber[size .. size + 3]>
                    <#else>
                        <#-- but if the card number has less than four digits (ie, it was entered incorrectly), display it in full -->
                        <#assign cardNumberDisplay = cardNumber>
                    </#if>
                </#if>
                <input type="text" class="required am-form-field am-input-sm" size="20" maxlength="30" name="cardNumber" onfocus="this.value = '';" value="${cardNumberDisplay?if_exists}" />
            <#else>
                <input class="am-form-field am-input-sm" type="text" size="20" maxlength="30" name="cardNumber" value="${creditCard.cardNumber?if_exists}"/>
            </#if>
        <#else>
            <input class="am-form-field am-input-sm" type="text" size="20" maxlength="30" name="cardNumber" value="${creditCard.cardNumber?if_exists}"/>
        </#if>
    <#if showToolTip?has_content><span class="tooltip">${uiLabelMap.CommonRequired}</span><#else>*</#if></div>
  </div>
  </div>
  <#--<tr>
    <td width="26%" align="right" valign="middle">${uiLabelMap.AccountingCardSecurityCode}</td>
    <td width="5">&nbsp;</td>
    <td width="74%">
        <input type="text" size="5" maxlength="10" name="cardSecurityCode" value="${creditCard.cardSecurityCode?if_exists}" />
    </td>
  </tr>-->
<div class="am-form-group am-g">
    <label class="am-control-label am-u-md-5 am-u-lg-5" for="cardType"><b>${uiLabelMap.AccountingExpirationDate}</b></label>
    <div class="am-input-group">

      <#assign expMonth = "">
      <#assign expYear = "">
      <#if creditCard?exists && creditCard.expireDate?exists>
        <#assign expDate = creditCard.expireDate>
        <#if (expDate?exists && expDate.indexOf("/") > 0)>
          <#assign expMonth = expDate.substring(0,expDate.indexOf("/"))>
          <#assign expYear = expDate.substring(expDate.indexOf("/")+1)>
        </#if>
      </#if>
          <div class="am-u-md-6 am-u-lg-6 am-u-end">
              <div class="am-input-group">
      <select name="expMonth" >
        <#if creditCard?has_content && expMonth?has_content>
          <#assign ccExprMonth = expMonth>
        <#else>
          <#assign ccExprMonth = requestParameters.expMonth?if_exists>
        </#if>
        <#if ccExprMonth?has_content>
          <option value="${ccExprMonth?if_exists}">${ccExprMonth?if_exists}</option>
        </#if>
        ${screens.render("component://common/widget/CommonScreens.xml#ccmonths")}
      </select>
            </div>
        </div>
    <div class="am-u-md-6 am-u-lg-6 am-u-end">
        <div class="am-input-group">
      <select name="expYear">
        <#if creditCard?has_content && expYear?has_content>
          <#assign ccExprYear = expYear>
        <#else>
          <#assign ccExprYear = requestParameters.expYear?if_exists>
        </#if>
        <#if ccExprYear?has_content>
          <option value="${ccExprYear?if_exists}">${ccExprYear?if_exists}</option>
        </#if>
        ${screens.render("component://common/widget/CommonScreens.xml#ccyears")}
      </select>
      </div>
  </div>
    <#if showToolTip?has_content><span class="tooltip">${uiLabelMap.CommonRequired}</span><#else>*</#if></div>

  </div>
<div class="am-form-group am-g">
    <label class="am-control-label am-u-md-5 am-u-lg-5" for="cardType"><b>${uiLabelMap.CommonDescription}</b></label>
    <div class="am-u-md-7 am-u-lg-7 am-u-end">
        <div class="am-input-group">
      <input type="text" size="20" maxlength="30" name="description" value="${paymentMethod.description?if_exists}"/>
    </div>
</div>
</div>


