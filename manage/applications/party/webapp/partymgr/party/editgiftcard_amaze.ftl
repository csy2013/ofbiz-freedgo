
<!-- begin editgiftcard.ftl -->
<div class="am-cf am-padding-xs">
    <div id="screenlet_1" class="am-panel am-panel-default">
    <#if !giftCard?exists>
    <div class="am-panel-hd am-cf">${uiLabelMap.AccountingCreateNewGiftCard}</div>
    <#else>
    <div class="am-panel-hd am-cf">${uiLabelMap.AccountingEditGiftCard}</div>
    </#if>
  </div>
<div class="am-panel-bd am-collapse am-in">
   <div class="am-g am-center">
      <div class="am-u-lg-10">
    <#if !giftCard?exists>
      <form class="am-form am-form-horizontal" method="post" action="<@ofbizUrl>createGiftCard?DONE_PAGE=${donePage}</@ofbizUrl>" name="editgiftcardform" style="margin: 0;">
    <#else>
      <form class="am-form am-form-horizontal" method="post" action="<@ofbizUrl>updateGiftCard?DONE_PAGE=${donePage}</@ofbizUrl>" name="editgiftcardform" style="margin: 0;">
        <input type="hidden" name="paymentMethodId" value="${paymentMethodId}" />
    </#if>
        <input type="hidden" name="partyId" value="${partyId}"/>
        <div class="am-form-group am-g">
            <label class="am-control-label am-u-md-5 am-u-lg-5" for="cardNumber">${uiLabelMap.AccountingCardNumber}</label>
            <div class="am-u-md-7 am-u-lg-7 am-u-end">
                <div class="am-input-group">
                  <input class="am-form-field am-input-sm" type="text" size="20" maxlength="60" name="cardNumber" value="${giftCardData.cardNumber?if_exists}" />
               </div>
           </div>
        </div>
        <div class="am-form-group am-g">
            <label class="am-control-label am-u-md-5 am-u-lg-5" for="pinNumber">${uiLabelMap.AccountingPinNumber}</label>
          <div class="am-u-md-7 am-u-lg-7 am-u-end">
            <div class="am-input-group">
             <input class="am-form-field am-input-sm" type="text" size="10" maxlength="60" name="pinNumber" value="${giftCardData.pinNumber?if_exists}" />
             </div>
           </div>
        </div>
       <div class="am-form-group am-g">
           <label class="am-control-label am-u-md-5 am-u-lg-5" ">${uiLabelMap.CommonExpireDate}</label>
           <div class="am-g">
               <div class="am-input-group">
            <#assign expMonth = "">
            <#assign expYear = "">
            <#if giftCardData?exists && giftCardData.expireDate?exists>
              <#assign expDate = giftCard.expireDate>
              <#if (expDate?exists && expDate.indexOf("/") > 0)>
                <#assign expMonth = expDate.substring(0,expDate.indexOf("/"))>
                <#assign expYear = expDate.substring(expDate.indexOf("/")+1)>
              </#if>
            </#if>
                <div class="am-u-md-6 am-u-lg-6 am-u-end">
                    <div class="am-input-group">
            <select name="expMonth" onchange="makeExpDate();"  >
              <#if giftCardData?has_content && expMonth?has_content>
                <#assign ccExprMonth = expMonth>
              <#else>
                <#assign ccExprMonth = requestParameters.expMonth?if_exists>
              </#if>
              <#if ccExprMonth?has_content>
                <option value="${ccExprMonth?if_exists}">${ccExprMonth?if_exists}</option>
              </#if>
              ${screens.render("component://common/widget/CommonScreens.xml#ccmonths")}
            </select>
                      </div></div>
                <div class="am-u-md-6 am-u-lg-6 am-u-end">
                    <div class="am-input-group">
            <select name="expYear" onchange="makeExpDate();"  >
              <#if giftCard?has_content && expYear?has_content>
                <#assign ccExprYear = expYear>
              <#else>
                <#assign ccExprYear = requestParameters.expYear?if_exists>
              </#if>
              <#if ccExprYear?has_content>
                <option value="${ccExprYear?if_exists}">${ccExprYear?if_exists}</option>
              </#if>
              ${screens.render("component://common/widget/CommonScreens.xml#ccyears")}
            </select>
                        </div></div>
           </div>
          </div>
        </div>
       <div class="am-form-group am-g">
           <label class="am-control-label am-u-md-5 am-u-lg-5" for="description">${uiLabelMap.CommonDescription}</label>
           <div class="am-u-md-7 am-u-lg-7 am-u-end">
               <div class="am-input-group">
                  <input class="am-form-field am-input-sm" type="text" size="30" maxlength="60" name="description" value="${paymentMethodData.description?if_exists}" />
               </div>
           </div>
        </div>

      </form>
        <div class="am-form-group am-g">
            <div class="am-control-label am-u-md-3 am-u-lg-3"></div>
            <div class="am-control-label am-u-md-7 am-u-lg-7">
              <a href="javascript:document.editgiftcardform.submit()" class="am-btn am-btn-primary am-btn-sm">${uiLabelMap.CommonSave}</a>
            </div>
        </div>
        </div>
      </div>
    </div>
  </div>
</div>
<!-- end editgiftcard.ftl -->