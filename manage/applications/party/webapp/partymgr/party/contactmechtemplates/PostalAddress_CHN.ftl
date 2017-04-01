<div>
<#if postalAddress.countryGeoId?has_content>
    <#assign country = postalAddress.getRelatedOneCache("CountryGeo")>
        ${country.get("geoName", locale)?default(country.geoId)}
    <#if postalAddress.stateProvinceGeoId?has_content>
        <#assign stateProvince = postalAddress.getRelatedOneCache("StateProvinceGeo")>
        ${stateProvince.abbreviation?default(stateProvince.geoId)}
    </#if>
    <#if postalAddress.city?has_content>
        <#assign city = postalAddress.getRelatedOneCache("CityGeo")>
        <#if city?exists>
        ${city.get("geoName", locale)?default(city.geoId)}
        </#if>
    </#if>
    <#if postalAddress.countyGeoId?has_content>
        <#assign county = postalAddress.getRelatedOneCache("CountyGeo")>
        ${county.get("geoName", locale)?default(county.geoId)}
    </#if>,

</#if>
    <br/>
${postalAddress.address1?if_exists}<br/><#if postalAddress.address2?has_content>${postalAddress.address2}<br/></#if>
<#if postalAddress.toName?has_content><b>${uiLabelMap.PartyAddrToName}:</b> ${postalAddress.toName}<br/></#if>
<#if postalAddress.attnName?has_content><b>${uiLabelMap.PartyAddrAttnName}:</b> ${postalAddress.attnName}<br/></#if>

${postalAddress.postalCode?if_exists}

</div>
<#if !postalAddress.countryGeoId?has_content>
    <#assign addr1 = postalAddress.address1?if_exists>
    <#if addr1?has_content && (addr1.indexOf(" ") > 0)>
        <#assign addressNum = addr1.substring(0, addr1.indexOf(" "))>
        <#assign addressOther = addr1.substring(addr1.indexOf(" ")+1)>
    <a target="_blank" href="${uiLabelMap.CommonLookupWhitepagesAddressLink}" class="buttontext">${uiLabelMap.CommonLookupWhitepages}</a>
    </#if>
</#if>

