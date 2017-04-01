
<#if shipmentPackageDatas?has_content>
<div class="table-responsive">
    <table class="table table-bordered table-striped">
        <tr class="header-row">
          <td>${uiLabelMap.ProductPackage}</td>
          <td>${uiLabelMap.CommonCreated}</td>
          <td>&nbsp;</td>
          <td>&nbsp;</td>
        </tr>
        <#assign alt_row = false>
        <#list shipmentPackageDatas as shipmentPackageData>
          <#assign shipmentPackage = shipmentPackageData.shipmentPackage>
          <#assign shipmentPackageContents = shipmentPackageData.shipmentPackageContents?if_exists>
          <#assign shipmentPackageRouteSegs = shipmentPackageData.shipmentPackageRouteSegs?if_exists>
          <#assign weightUom = shipmentPackageData.weightUom?if_exists>
          <tr valign="middle"<#if alt_row> class="alternate-row"</#if>>
            <td>${shipmentPackage.shipmentPackageSeqId}</td>
            <td>${(shipmentPackage.dateCreated.toString())?if_exists}</td>
            <td><span class="label">${uiLabelMap.ProductWeight}</span> ${shipmentPackage.weight?if_exists}</td>
            <td><span class="label">${uiLabelMap.ProductWeightUnit}</span> <#if weightUom?has_content>${weightUom.get("description",locale)}<#else>${shipmentPackage.weightUomId?if_exists}</#if></td>
          </tr>
          <#list shipmentPackageContents as shipmentPackageContent>
            <tr valign="middle"<#if alt_row> class="alternate-row"</#if>>
              <td>&nbsp;</td>
              <td><span class="label">${uiLabelMap.ProductItem}</span> ${shipmentPackageContent.shipmentItemSeqId}</td>
              <td><span class="label">${uiLabelMap.ProductQuantity}</span> ${shipmentPackageContent.quantity?if_exists}</td>
              <td>&nbsp;</td>
            </tr>
          </#list>
          <#list shipmentPackageRouteSegs as shipmentPackageRouteSeg>
            <tr valign="middle"<#if alt_row> class="alternate-row"</#if>>
              <td>&nbsp;</td>
              <td><span class="label">${uiLabelMap.ProductRouteSegment}</span> ${shipmentPackageRouteSeg.shipmentRouteSegmentId}</td>
              <td><span class="label">${uiLabelMap.ProductTracking}</span> ${shipmentPackageRouteSeg.trackingCode?if_exists}</td>
              <td><span class="label">${uiLabelMap.ProductBox}</span> ${shipmentPackageRouteSeg.boxNumber?if_exists}</td>
            </tr>
          </#list>
          <#-- toggle the row color -->
          <#assign alt_row = !alt_row>
        </#list>
      </table>
    </div>
</#if>