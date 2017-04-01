<#if geoChart?has_content>
<#-- ================================= Golbal Init ======================================-->
    <#if geoChart.id?has_content>
        <#assign id = geoChart.id>
    <#else>
        <#assign id = "map_canvas">
    </#if>

    <#if geoChart.center?has_content>
        <#assign center = geoChart.center>
        <#assign zoom = geoChart.center.zoom>
    <#elseif geoChart.points?has_content>
        <#assign center = geoChart.points[0]>
        <#assign zoom = 15> <#-- 0=World, 19=max zoom in -->
    <#else>
    <#-- hardcoded in GEOPT_ADDRESS_GOOGLE, simpler -->
    </#if>

<#-- ================================= Google Maps Init ======================================-->
    <#if geoChart.dataSourceId?has_content>
        <#if geoChart.dataSourceId?substring(geoChart.dataSourceId?length-6 , geoChart.dataSourceId?length) == "GOOGLE">
        <div id="${id}"
             style="border:1px solid #979797; background-color:#e5e3df; width:${geoChart.width}; height:${geoChart.height}; margin:2em auto;">
            <div style="padding:1em; color:gray;">${uiLabelMap.CommonLoading}</div>
        </div>
        <script src="https://maps.googleapis.com/maps/api/js?sensor=false" type="text/javascript"></script>
        </#if>
    <#--baidu Maps Init-->
        <#if geoChart.dataSourceId?substring(geoChart.dataSourceId?length-5 , geoChart.dataSourceId?length) == "BAIDU">
            <#assign mapkey = Static["org.ofbiz.base.util.UtilProperties"].getPropertyValue("map.properties", "map.baidu.key")>
        <script type="text/javascript" src="http://api.map.baidu.com/api?v=2.0&ak=${mapkey}"></script>
        </#if>
    <#--amap Maps Init-->

        <#if geoChart.dataSourceId?substring(geoChart.dataSourceId?length-4 , geoChart.dataSourceId?length) == "AMAP">


        </#if>



    <#-- ================================= Here we go with different types of maps renderer ======================================-->
        <#if geoChart.dataSourceId == "GEOPT_GOOGLE">
        <script type="text/javascript">
            function showAllMarkers(map, points) {
                if (points.length > 1) {
                    var latlngbounds = new google.maps.LatLngBounds();
                    for (var i = 0; i < latlngs.length; i++) {
                        latlngbounds.extend(latlngs[i]);
                    }
                    map.fitBounds(latlngbounds);
                }
            }

            var map = new google.maps.Map(document.getElementById("${id}"),
                <#if geoChart.points?has_content>
                        {
                            center: new google.maps.LatLng(${center.lat}, ${center.lon}),
                            zoom: ${zoom},
                            mapTypeId: google.maps.MapTypeId.ROADMAP
                        });
                    <#list geoChart.points as point>
                    var marker_${point_index} = new google.maps.Marker({
                        position: new google.maps.LatLng(${point.lat}, ${point.lon}),
                        map: map
                    });
                        <#if point.link?has_content>
                        var infoWindow = new google.maps.InfoWindow();
                        google.maps.event.addListener(marker_${point_index}, "click", function () {
                            infoWindow.setContent(("<div style=\"width:210px; padding-right:10px;\"><a href=${point.link.url}>${point.link.label}</a></div>"));
                            infoWindow.setPosition(marker_${point_index}.getPosition());
                            infoWindow.open(map);
                        });
                        </#if>
                    </#list>
                var latlngs = [
                    <#list geoChart.points as point>
                        new google.maps.LatLng(${point.lat}, ${point.lon})<#if point_has_next>,</#if>
                    </#list>
                ];
                showAllMarkers(map, latlngs);
                </#if>
        </script>
        <#elseif  geoChart.dataSourceId == "GEOPT_YAHOO">
        <#elseif  geoChart.dataSourceId == "GEOPT_MICROSOFT">
        <#elseif  geoChart.dataSourceId == "GEOPT_MAPTP">
        <#elseif  geoChart.dataSourceId == "GEOPT_ADDRESS_GOOGLE">
        <script type="text/javascript">
            var geocoder = new google.maps.Geocoder();
            var map = new google.maps.Map(document.getElementById("${id}"),
                    {
                        center: new google.maps.LatLng(38, 15),
                        zoom: 15, // 0=World, 19=max zoom in
                        mapTypeId: google.maps.MapTypeId.ROADMAP
                    });
            geocoder.geocode({'address': "${pointAddress}"}, function (result, status) {
                if (status != google.maps.GeocoderStatus.OK) {
                    showErrorAlert("${uiLabelMap.CommonErrorMessage2}", "${uiLabelMap.CommonAddressNotFound}");
                } else {
                    var position = result[0].geometry.location;
                    map.setCenter(position);
                    map.fitBounds(result[0].geometry.viewport);
                    var marker = new google.maps.Marker({
                        position: position,
                        map: map
                    });
                }
            });
        </script>
        <#elseif geoChart.dataSourceId == "GEOPT_OSM">
        <div id="${id}"
             style="border:1px solid #979797; background-color:#e5e3df; width:${geoChart.width}; height:${geoChart.height}; margin:2em auto;"></div>
        <#-- due to https://github.com/openlayers/openlayers/issues/1025 rather use a local version loaded by framework/common/widget/CommonScreens.xml -->
        <#-- script src="//www.openlayers.org/api/OpenLayers.js"></script-->
        <script type="text/javascript">
            map = new OpenLayers.Map("${id}");
            map.addLayer(new OpenLayers.Layer.OSM());
            var zoom = ${zoom};
            var center = new OpenLayers.LonLat(${center.lon},${center.lat})
                    .transform(new OpenLayers.Projection("EPSG:4326"), // transform from WGS 1984
                    map.getProjectionObject() // to Spherical Mercator Projection
            );
            var markers = new OpenLayers.Layer.Markers("Markers");
            map.addLayer(markers);
                <#if geoChart.points?has_content>
                    <#list geoChart.points as point>
                    markers.addMarker(new OpenLayers.Marker(new OpenLayers.LonLat(${point.lon} ,${point.lat}).transform(
                            new OpenLayers.Projection("EPSG:4326"), map.getProjectionObject())));
                    </#list>
                </#if>
            map.addControl(new OpenLayers.Control.PanZoomBar());
            map.addControl(new OpenLayers.Control.NavToolbar());

            map.setCenter(center, zoom);
            var newBound = markers.getDataExtent();
            map.zoomToExtent(newBound);
        </script>
        </#if>

        <#if geoChart.dataSourceId == "GEOPT_BAIDU">

            <form class="form-horizontal">
            <div class="form-group">
                <label class="col-md-3 control-label">请输入: </label>
                <div class="col-md-5">
                    <input type="text" size="25" class="form-control" id="suggestId" value=""/>
                </div>
            </div>
            </form>
        <br/>
        <div id="l-map"
             style="border:1px solid #979797; background-color:#e5e3df; width:${geoChart.width}; height:${geoChart.height}; margin:2em auto;"></div>

        <div id="searchResultPanel" style="border:1px solid #C0C0C0;width:150px;height:auto; display:none;"></div>
        <script type="text/javascript">
            // 百度地图API功能
            function G(id) {
                return document.getElementById(id);
            }
            var map = new BMap.Map("l-map");
            var adds = [];
                <#if geoChart.points?exists>
                    <#list geoChart.points as point>
                        <#if point_index ==0>
                        map.centerAndZoom(new BMap.Point(${point.lon}, ${point.lat}), 15);
                        </#if>
                    adds.push(new BMap.Point(${point.lon}, ${point.lat}));
                    </#list>
                <#else >
                map.centerAndZoom("南京", 15);
                </#if>
            var ac = new BMap.Autocomplete(    //建立一个自动完成的对象
                    {
                        "input": "suggestId"
                        , "location": map
                    });

            ac.addEventListener("onhighlight", function (e) {  //鼠标放在下拉列表上的事件
                var str = "";
                var _value = e.fromitem.value;
                var value = "";
                if (e.fromitem.index > -1) {
                    value = _value.province + _value.city + _value.district + _value.street + _value.business;
                }
                str = "FromItem<br />index = " + e.fromitem.index + "<br />value = " + value;

                value = "";
                if (e.toitem.index > -1) {
                    _value = e.toitem.value;
                    value = _value.province + _value.city + _value.district + _value.street + _value.business;
                }
                str += "<br />ToItem<br />index = " + e.toitem.index + "<br />value = " + value;
                G("searchResultPanel").innerHTML = str;
            });

            var myValue;
            ac.addEventListener("onconfirm", function (e) {    //鼠标点击下拉列表后的事件
                var _value = e.item.value;
                myValue = _value.province + _value.city + _value.district + _value.street + _value.business;
                G("searchResultPanel").innerHTML = "onconfirm<br />index = " + e.item.index + "<br />myValue = " + myValue;

                setPlace();
            });

            function setPlace() {
                map.clearOverlays();    //清除地图上所有覆盖物
                function myFun() {
                    var pp = local.getResults().getPoi(0).point;    //获取第一个智能搜索的结果
                    map.centerAndZoom(pp, 18);
                    map.addOverlay(new BMap.Marker(pp));    //添加标注
                }
                var local = new BMap.LocalSearch(map, { //智能搜索
                    onSearchComplete: myFun
                });

                local.search(myValue);
            }
            function showInfo(e) {
                document.forms['EditFacilityGeoPoint'].EditFacilityGeoPoint_latitude.value = e.point.lat;
                document.forms['EditFacilityGeoPoint'].EditFacilityGeoPoint_longitude.value = e.point.lng;
                var marker = new BMap.Point( e.point.lng,  e.point.lat);
                map.addOverlay(marker);
                marker.setLabel(new BMap.Label("我的位置:" + (i + 1), {offset: new BMap.Size(20, -10)}));
            }

            map.addEventListener("click", showInfo);
            map.enableScrollWheelZoom(true);
            var index = 0;
            var myGeo = new BMap.Geocoder();
            for (var i = 0; i < adds.length; i++) {
                console.log(adds[i]);
                var marker = new BMap.Marker(adds[i]);
                map.addOverlay(marker);
                var label = new BMap.Label("我的位置:" + (i + 1), {offset: new BMap.Size(20, -10)});
                marker.setLabel(label);
                marker.setAnimation(BMAP_ANIMATION_BOUNCE); //跳动的动画
            }
        </script>
        </#if>
        <#if geoChart.dataSourceId == "GEOPT_AMAP">
        <iframe name="amapgeo" frameborder="no"  scrolling = "auto"  style='height:800px;width:100%;' src="<@ofbizUrl>amapgeo?<#if facilityId?has_content>facilityId=${facilityId}</#if></@ofbizUrl>"></iframe>
        </#if>
    </#if>
<#else>

<h2>${uiLabelMap.CommonNoGeolocationAvailable}</h2>
</#if>
