<div class="am-panel am-panel-default">
  <div class="am-panel-hd am-cf">
      ${uiLabelMap.OrderOrderEntryCurrencyAgreementShipDates}
  </div>
  <div class="am-panel-bd am-collapse am-in">
      <table class="am-table">
      <#if agreements?exists>
          <tr><td colspan="4">&nbsp;<input type='hidden' name='hasAgreements' value='Y'/></td></tr>
          <tr>
              <td>&nbsp;</td>
              <td align='right' valign='top' nowrap="nowrap">
                  <div class='tableheadtext'>
                  ${uiLabelMap.OrderSelectAgreement}
                  </div>
              </td>
              <td>&nbsp;</td>
              <td valign='middle'>
                  <div class='tabletext' valign='top'>
                      <select name="agreementId">
                          <option value="">${uiLabelMap.CommonNone}</option>
                        <#list agreements as agreement>
                            <option value='${agreement.agreementId}' >${agreement.agreementId} - ${agreement.description?if_exists}</option>
                        </#list>
                      </select>
                  </div>
              </td>
          </tr>
      <#else>
          <input type='hidden' name='hasAgreements' value='N'/>
      </#if>
      <#if agreementRoles?exists>
          <tr>
              <td>&nbsp;</td>
              <td align='right' valign='top' nowrap="nowrap">
                  <div class='tableheadtext'>
                  ${uiLabelMap.OrderSelectAgreementRoles}
                  </div>
              </td>
              <td>&nbsp;</td>
              <td valign='middle'>
                  <div class='tabletext'>
                      <select name="agreementId">
                          <option value="">${uiLabelMap.CommonNone}</option>
                        <#list agreementRoles as agreementRole>
                            <option value='${agreementRole.agreementId?if_exists}' >${agreementRole.agreementId?if_exists} - ${agreementRole.roleTypeId?if_exists}</option>
                        </#list>
                      </select>
                  </div>
              </td>
          </tr>
      </#if>


          <tr>
              <td>&nbsp;</td>
              <td align='right' valign='middle' class='tableheadtext' nowrap="nowrap">
              ${uiLabelMap.OrderOrderName}
              </td>
              <td>&nbsp;</td>
              <td align='left'>
                  <input type='text' size='60' maxlength='100' name='orderName'/>
              </td>
          </tr>


          <tr>
              <td>&nbsp;</td>
              <td align='right' valign='middle' class='tableheadtext' nowrap="nowrap">
              ${uiLabelMap.OrderPONumber}
              </td>
              <td>&nbsp;</td>
              <td align='left'>
                  <input type="text" class='inputBox' name="correspondingPoId" size="15" />
              </td>
          </tr>


          <tr>
              <td>&nbsp;</td>
              <td align='right' valign='middle' nowrap="nowrap">
                  <div class='tableheadtext'>
                  <#if agreements?exists>${uiLabelMap.OrderSelectCurrencyOr}
            <#else>${uiLabelMap.OrderSelectCurrency}
                  </#if>
                  </div>
              </td>
              <td>&nbsp;</td>
              <td valign='middle'>
                  <div class='tabletext'>
                      <select name="currencyUomId">
                          <option value=""></option>
                      <#list currencies as currency>
                          <option value="${currency.uomId}" <#if currencyUomId?default('') == currency.uomId>selected="selected"</#if> >${currency.uomId}</option>
                      </#list>
                      </select>
                  </div>
              </td>
          </tr>
          <input type="hidden" name="currencyUomId" value="CNY"/>
          <input type="hidden" name="CURRENT_CATALOG_ID"/>
          <input type="hidden" name="workEffortId"/>
          <input type="hidden" name=" shipAfterDate"/>
          <input type="hidden" name=" shipBeforeDate"/>
          <input type="hidden" name=" shipAfterDate"/>
      </table>
  </div>
</div>

