<div>
  <#if contactMeches?has_content>
  <div class="table-responsive">
      <table class="table table-striped table-bordered">
      <#list contactMeches as contactMechMap>
          <#assign contactMech = contactMechMap.contactMech>
          <#assign productStoreContactMech = contactMechMap.productStoreContactMech>
          <tr>
            <td>
              ${contactMechMap.contactMechType.get("description",locale)}
            </td>
            <td valign="top">
              <#list contactMechMap.productStoreContactMechPurposes as productStoreContactMechPurpose>
                  <#assign contactMechPurposeType = productStoreContactMechPurpose.getRelatedOneCache("ContactMechPurposeType")>
                      <#if contactMechPurposeType?has_content>
                        <b>${contactMechPurposeType.get("description",locale)}</b>
                      <#else>
                        <b>${uiLabelMap.ProductPurposeTypeNotFoundWithId}: "${productStoreContactMechPurpose.contactMechPurposeTypeId}"</b>
                      </#if>
                      <#if productStoreContactMechPurpose.thruDate?has_content>
                      (${uiLabelMap.CommonExpire}: ${productStoreContactMechPurpose.thruDate.toString()})
                      </#if>
                      <br />
              </#list>
              <#if "POSTAL_ADDRESS" = contactMech.contactMechTypeId>
                  <#assign postalAddress = contactMechMap.postalAddress>
                    <#if postalAddress.toName?has_content><b>${uiLabelMap.CommonTo}:</b> ${postalAddress.toName}<br /></#if>
                    <#if postalAddress.attnName?has_content><b>${uiLabelMap.CommonAttn}:</b> ${postalAddress.attnName}<br /></#if>
                    <#if postalAddress.countryGeoId?has_content>${postalAddress.countryGeoId}</#if>&nbsp;&nbsp;
                    ${postalAddress.stateProvinceGeoId?if_exists}&nbsp;&nbsp;${postalAddress.city?if_exists},
                    ${postalAddress.address1?if_exists}<br />
                    <#if postalAddress.address2?has_content>${postalAddress.address2?if_exists}<br /></#if>
                    邮件编码:${postalAddress.postalCode?if_exists}

                  <#if (postalAddress?has_content && !postalAddress.countryGeoId?has_content) || postalAddress.countryGeoId = "CHN">
                      <#assign addr1 = postalAddress.address1?if_exists>
                      <#if (addr1.indexOf(" ") > 0)>
                        <#assign addressNum = addr1.substring(0, addr1.indexOf(" "))>
                        <#assign addressOther = addr1.substring(addr1.indexOf(" ")+1)>
                        <br /><a target='_blank' href='${uiLabelMap.CommonLookupWhitepagesAddressLink}' class='buttontext'>${uiLabelMap.CommonLookupWhitepages}</a>
                      </#if>
                  </#if>
                  <#if postalAddress.geoPointId?has_content>
                    <#if contactMechPurposeType?has_content>
                      <#assign popUptitle = contactMechPurposeType.get("description",locale) + uiLabelMap.CommonGeoLocation>
                    </#if>
                    <br /><a href="javascript:popUp('<@ofbizUrl>geoLocation?geoPointId=${postalAddress.geoPointId}</@ofbizUrl>', '${popUptitle?if_exists}', '450', '550')" class="buttontext">${uiLabelMap.CommonGeoLocation}</a>
                  </#if>
              <#elseif "TELECOM_NUMBER" = contactMech.contactMechTypeId>
                  <#assign telecomNumber = contactMechMap.telecomNumber>
                    ${telecomNumber.countryCode?if_exists}
                    <#if telecomNumber.areaCode?has_content>${telecomNumber.areaCode}-</#if>${telecomNumber.contactNumber?if_exists}
                    <#if productStoreContactMech.extension?has_content>${uiLabelMap.CommonExt} ${productStoreContactMech.extension}</#if>
                    <#if (telecomNumber?has_content && !telecomNumber.countryCode?has_content) || telecomNumber.countryCode = "011">
                      <#--<br /><a target='_blank' href='${uiLabelMap.CommonLookupAnywhoLink}' class='buttontext'>${uiLabelMap.CommonLookupAnywho}</a>-->
                      <#--<a target='_blank' href='${uiLabelMap.CommonLookupWhitepagesTelNumberLink}' class='buttontext'>${uiLabelMap.CommonLookupWhitepages}</a>-->
                    </#if>
              <#--<#elseif "EMAIL_ADDRESS" = contactMech.contactMechTypeId>
                    ${contactMech.infoString?if_exists}
                    <a href='mailto:${contactMech.infoString?if_exists}' class='buttontext'>${uiLabelMap.CommonSendEmail}</a>
              <#elseif "WEB_ADDRESS" = contactMech.contactMechTypeId>
                    ${contactMech.infoString?if_exists}
                    <#assign openAddress = contactMech.infoString?default("")>
                    <#if !openAddress?starts_with("http") && !openAddress?starts_with("HTTP")><#assign openAddress = "http://" + openAddress></#if>
                    <a target='_blank' href='${openAddress}' class='buttontext'>((${uiLabelMap.CommonOpenPageNewWindow})</a>-->
              <#else>
                    ${contactMech.infoString?if_exists}
              </#if>
              <br />(${uiLabelMap.CommonUpdated}: ${productStoreContactMech.fromDate.toString()})
              <#if productStoreContactMech.thruDate?has_content><br /><b>${uiLabelMap.CommonUpdatedEffectiveThru}:&nbsp;${productStoreContactMech.thruDate.toString()}</b></#if>
            </td>
            <td>

              <#if security.hasEntityPermission("FACILITY", "_UPDATE", session)>
                  <@htmlScreenTemplate.renderModalPage id="EditContactMechForPhone_${contactMech.contactMechId}"  name="EditContactMechForPhone_${contactMech.contactMechId}" buttonType="button" modalUrl="/catalog/control/EditContactMech" modalMsg="" modalTitle="修改"
                  description="${uiLabelMap.CommonUpdate}" targetParameterIter="productStoreId:'${productStoreId}',contactMechId:contactMechId='${contactMech.contactMechId}'">
                  </@htmlScreenTemplate.renderModalPage>
                <#--<a href='<@ofbizUrl>EditContactMech?productStoreId=${productStoreId}&amp;contactMechId=${contactMech.contactMechId}</@ofbizUrl>' class="btn btn-primary btn-sm">${uiLabelMap.CommonUpdate}</a>-->
              </#if>
                <br/><br/>
              <#if security.hasEntityPermission("FACILITY", "_DELETE", session)>

                <form action="<@ofbizUrl>deleteContactMech/ViewContactMechs</@ofbizUrl>" name="deleteContactForm_${contactMechMap_index}" method="post">
                  <input type="hidden" name="productStoreId" value="${productStoreId?if_exists}"/>
                  <input type="hidden" name="contactMechId" value="${contactMech.contactMechId?if_exists}"/>
                </form>
                <a href="javascript:document.deleteContactForm_${contactMechMap_index}.submit()" class="btn btn-primary btn-sm">${uiLabelMap.CommonExpire}</a>
              </#if>
            </td>
          </tr>
      </#list>
    </table>
      </div>
  <#else>
    <div class="screenlet-body">${uiLabelMap.CommonNoContactInformationOnFile}.</div>
  </#if>
</div>
