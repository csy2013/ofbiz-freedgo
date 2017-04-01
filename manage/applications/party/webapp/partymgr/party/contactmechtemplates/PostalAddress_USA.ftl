
  <div>
    <#if postalAddress.toName?has_content><b>${uiLabelMap.PartyAddrToName}:</b> ${postalAddress.toName}<br /></#if>
    <#if postalAddress.attnName?has_content><b>${uiLabelMap.PartyAddrAttnName}:</b> ${postalAddress.attnName}<br /></#if>
    ${postalAddress.address1?if_exists}<br />
    <#if postalAddress.address2?has_content>${postalAddress.address2}<br /></#if>
    ${postalAddress.city?if_exists},
    <#if postalAddress.stateProvinceGeoId?has_content>
      <#assign stateProvince = postalAddress.getRelatedOneCache("StateProvinceGeo")>
      ${stateProvince.abbreviation?default(stateProvince.geoId)}
    </#if>
    ${postalAddress.postalCode?if_exists}
    <#if postalAddress.countryGeoId?has_content><br />
      <#assign country = postalAddress.getRelatedOneCache("CountryGeo")>
      ${country.get("geoName", locale)?default(country.geoId)}
    </#if>
    </div>
    <#if !postalAddress.countryGeoId?has_content>
    <#assign addr1 = postalAddress.address1?if_exists>
    <#if addr1?has_content && (addr1.indexOf(" ") > 0)>
      <#assign addressNum = addr1.substring(0, addr1.indexOf(" "))>
      <#assign addressOther = addr1.substring(addr1.indexOf(" ")+1)>
      <a target="_blank" href="${uiLabelMap.CommonLookupWhitepagesAddressLink}" class="buttontext">${uiLabelMap.CommonLookupWhitepages}</a>
    </#if>
  </#if>

