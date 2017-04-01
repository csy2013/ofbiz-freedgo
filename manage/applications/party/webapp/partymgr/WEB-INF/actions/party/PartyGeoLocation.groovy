import org.ofbiz.base.util.UtilMisc
import org.ofbiz.base.util.UtilProperties
import org.ofbiz.base.util.UtilValidate
import org.ofbiz.common.geo.GeoWorker

uiLabelMap = UtilProperties.getResourceBundleMap("PartyUiLabels", locale)
uiLabelMap.addBottomResourceBundle("CommonUiLabels")

partyId = parameters.partyId ?: parameters.party_id
userLoginId = parameters.userlogin_id ?: parameters.userLoginId

if (!partyId && userLoginId) {
    thisUserLogin = delegator.findByPrimaryKey("UserLogin", [userLoginId : userLoginId])
    if (thisUserLogin) {
        partyId = thisUserLogin.partyId
    }
}
geoPointId = parameters.geoPointId
context.partyId = partyId

if (!geoPointId) {
    latestGeoPoint = GeoWorker.findLatestGeoPoint(delegator, "PartyAndGeoPoint", "partyId", partyId, null, null)
} else {
    latestGeoPoint = delegator.findByPrimaryKey("GeoPoint", [geoPointId : geoPointId])
}
if (latestGeoPoint) {
    context.latestGeoPoint = latestGeoPoint

    List geoCenter = UtilMisc.toList(UtilMisc.toMap("lat", latestGeoPoint.latitude, "lon", latestGeoPoint.longitude, "zoom", "13"))
  
    if (UtilValidate.isNotEmpty(latestGeoPoint) && latestGeoPoint.containsKey("latitude") && latestGeoPoint.containsKey("longitude")) {
        List geoPoints = UtilMisc.toList(UtilMisc.toMap("lat", latestGeoPoint.latitude, "lon", latestGeoPoint.longitude, "partyId", partyId,
              "link", UtilMisc.toMap("url", "viewprofile?partyId="+ partyId, "label", uiLabelMap.PartyProfile + " " + uiLabelMap.CommonOf + " " + partyId)))

        Map geoChart = UtilMisc.toMap("width", "500px", "height", "450px", "controlUI" , "small", "dataSourceId", latestGeoPoint.dataSourceId, "points", geoPoints)
        context.geoChart = geoChart
    }
    if (latestGeoPoint && latestGeoPoint.elevationUomId) {
        elevationUom = delegator.findOne("Uom", [uomId : latestGeoPoint.elevationUomId], false)
        context.elevationUomAbbr = elevationUom.abbreviation
    }
}
