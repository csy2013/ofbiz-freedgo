<div class="breadcrumbs">
<#assign isDefaultTheme = !layoutSettings.VT_FTR_TMPLT_LOC?contains("multiflex")>
<#if isDefaultTheme>
    <a href="<@ofbizUrl>main</@ofbizUrl>" class="linktext">${uiLabelMap.CommonMain}</a> &gt;
<#else>
<ul>
    <li>
        <a href="<@ofbizUrl>main</@ofbizUrl>" class="linktext">${uiLabelMap.CommonMain}</a>
    </li>
</#if>
<#-- Show the category branch -->
<#assign crumbs = Static["org.ofbiz.product.category.CategoryWorker"].getTrail(request)/>
<#list crumbs as crumb>
  <#if catContentWrappers?exists && catContentWrappers[crumb]?exists>
    <#if !isDefaultTheme>
        <li>
            <a href="<@ofbizCatalogUrl currentCategoryId=crumb previousCategoryId=previousCategoryId!""/>" class="<#if crumb_has_next>linktext<#else>buttontextdisabled</#if>">
              <#if catContentWrappers[crumb].get("CATEGORY_NAME")?exists>
                     ${catContentWrappers[crumb].get("CATEGORY_NAME")}
                   <#elseif catContentWrappers[crumb].get("DESCRIPTION")?exists>
              ${catContentWrappers[crumb].get("DESCRIPTION")}
              <#else>
              ${crumb}
              </#if>
            </a>
        </li>
    <#else>
        <a href="<@ofbizCatalogUrl currentCategoryId=crumb previousCategoryId=previousCategoryId!""/>" class="<#if crumb_has_next>linktext<#else>buttontextdisabled</#if>">
          <#if catContentWrappers[crumb].get("CATEGORY_NAME")?exists>
                   ${catContentWrappers[crumb].get("CATEGORY_NAME")}
                 <#elseif catContentWrappers[crumb].get("DESCRIPTION")?exists>
          ${catContentWrappers[crumb].get("DESCRIPTION")}
          <#else>
          ${crumb}
          </#if>
        </a>
      <#if crumb_has_next> &gt;</#if>
    </#if>
    <#assign previousCategoryId = crumb />
  </#if>
</#list>
<#-- Show the product, if there is one -->
<#if productContentWrapper?exists>
  <#if isDefaultTheme>
      &nbsp;&gt; ${productContentWrapper.get("PRODUCT_NAME")?if_exists}
  <#else>
      <li>${productContentWrapper.get("PRODUCT_NAME")?if_exists}</li>
  </ul>
  </#if>
</#if>
</div>
