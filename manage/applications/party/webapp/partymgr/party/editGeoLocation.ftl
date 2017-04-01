<#assign mapType = Static["org.ofbiz.base.util.UtilProperties"].getPropertyValue("map.properties", "current.map.type")>
<#if mapType == 'GOOGLE'>
<script src="https://maps.googleapis.com/maps/api/js?sensor=false" type="text/javascript"></script>
<script type="text/javascript">
    function load() {
        var geocoder = new google.maps.Geocoder();
        var center = new google.maps.LatLng(${latitude!38}, ${longitude!15});
        var map = new google.maps.Map(document.getElementById("map"),
          { center: center,
            zoom: 15, // 0=World, 19=max zoom in
            mapTypeId: google.maps.MapTypeId.ROADMAP
          });

        var marker = new google.maps.Marker({
          position: center,
          map: map,
          draggable: true
        });
    
        document.getElementById("lat").value = center.lat().toFixed(5);
        document.getElementById("lng").value = center.lng().toFixed(5);

        google.maps.event.addListener(marker, "dragend", function() {
          var point = marker.getPosition();
          map.panTo(point);
          document.getElementById("lat").value = point.lat().toFixed(5);
          document.getElementById("lng").value = point.lng().toFixed(5);
        });
    
    
        google.maps.event.addListener(map, "moveend", function() {
            map.clearOverlays();
            var center = map.getCenter();
            var marker = new GMarker(center, {draggable: true});
            map.addOverlay(marker);
            document.getElementById("lat").value = center.lat().toFixed(5);
            document.getElementById("lng").value = center.lng().toFixed(5);
        });
    
        google.maps.event.addListener(marker, "dragend", function() {
            var point = marker.getPoint();
            map.panTo(point);
            document.getElementById("lat").value = point.lat().toFixed(5);
            document.getElementById("lng").value = point.lng().toFixed(5);
        });
    }

    function showAddress(address) {
        var map = new google.maps.Map(document.getElementById("map"),
          { center: new google.maps.LatLng(${latitude!38}, ${longitude!15}),
            zoom: 15, // 0=World, 19=max zoom in
            mapTypeId: google.maps.MapTypeId.ROADMAP
          });
        var geocoder = new google.maps.Geocoder();
        if (geocoder) {
            geocoder.geocode({'address': address}, function(result, status) {
              if (status != google.maps.GeocoderStatus.OK) {
                showErrorAlert("${uiLabelMap.CommonErrorMessage2}","${uiLabelMap.CommonAddressNotFound}");
            } else {
                var point = result[0].geometry.location; 
                var lat = point.lat().toFixed(5);
                var lng = point.lng().toFixed(5);
                document.getElementById("lat").value = lat; 
                document.getElementById("lng").value = lng;
                //map.clearOverlays()
                map.setCenter(point, 14);
        
                var marker = new google.maps.Marker({
                  position: new google.maps.LatLng(lat, lng),
                  map: map,
                  draggable: true
                });
                
                google.maps.event.addListener(marker, "dragend", function() {
                  var point = marker.getPosition();
                  map.panTo(point);
                  document.getElementById("lat").value = point.lat().toFixed(5);
                  document.getElementById("lng").value = point.lng().toFixed(5);
                });

                google.maps.event.addListener(map, "moveend", function() {
                    //map.clearOverlays();
                    var center = map.getCenter();
                    var marker = new google.maps.Marker(center, {draggable: true});
                    map.addOverlay(marker);
                    document.getElementById("lat").value = center.lat().toFixed(5);
                    document.getElementById("lng").value = center.lng().toFixed(5);
                });

                google.maps.event.addListener(marker, "dragend", function() {
                    var pt = marker.getPoint();
                    map.panTo(pt);
                    document.getElementById("lat").value = pt.lat().toFixed(5);
                    document.getElementById("lng").value = pt.lng().toFixed(5);
                });
            }
        });
        }
    }
</script>

<body onload="load()">
    <center>
        <div align="center" id="map" style="border:1px solid #979797; background-color:#e5e3df; width:500px; height:450px; margin:2em auto;"><br/></div>
        <form action="#" onsubmit="showAddress(this.address.value); return false">
            <input type="text" size="50" name="address"/>
            <input type="submit" value="${uiLabelMap.CommonSearch}"/>
        </form>
        <br/><br/>
        <form id="updateMapForm" method="post" action="<@ofbizUrl>editGeoLocation</@ofbizUrl>">
            <input type="hidden" name="partyId" value="${partyId?if_exists}"/>
            <input type="hidden" name="geoPointId" value="${geoPointId?if_exists}"/>
            <input type="hidden" name="lat" id="lat" value="${latitude}"/>
            <input type="hidden" name="lng" id="lng" value="${longitude}"/>
            <input type="hidden" name="geopt" value="GEOPT_GOOGLE"/>
            <input type="submit" id="createMapButton" class="smallSubmit" value="${uiLabelMap.CommonSubmit}">
        </form>
        <br/><br/><br/>
    </center>
</body>
<#elseif mapType == 'BAIDU'>
<#assign mapkey = Static["org.ofbiz.base.util.UtilProperties"].getPropertyValue("map.properties", "map.baidu.key")>
<script type="text/javascript" src="http://api.map.baidu.com/api?v=2.0&ak=${mapkey}"></script>
<div class="am-g am-center am-cf">
    <div class="am-form-group ">
        <label class="am-control-label am-u-md-4 am-u-lg-4 am-text-right"
               id="EditFacilityGeoPoint_information_title" for="EditFacilityGeoPoint_information">
            请输入: </label>

        <div class="am-u-md-4 am-u-end"><input type="text" size="25" class="am-form-field am-input-sm"
                                               id="suggestId" value=""/></div>

    </div>
</div>
<br/>
<div id="map"
     style="border:1px solid #979797; background-color:#e5e3df; width:1024px; height:600px; margin:2em auto;"></div>

<div id="searchResultPanel" style="border:1px solid #C0C0C0;width:150px;height:auto; display:none;"></div>
<script type="text/javascript">
    // 百度地图API功能
    function G(id) {
        return document.getElementById(id);
    }
    var map = new BMap.Map("map");
    var adds = [];
    <#if (longitude?has_content && latitude?has_content)>
    adds.push(new BMap.Point(${longitude},${latitude}));
    map.centerAndZoom(new BMap.Point(${longitude},${latitude}), 15);
    <#else>
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
        document.updateMapForm.lng.value = e.point.lng;
        document.updateMapForm.lat.value = e.point.lat;
        var marker =  new BMap.Marker(new BMap.Point(e.point.lng,e.point.lat));
        map.addOverlay(marker);
        marker.setLabel(new BMap.Label("我的位置:", {offset: new BMap.Size(20, -10)}));
        marker.setAnimation(BMAP_ANIMATION_BOUNCE); //跳动的动画

    }
    map.addEventListener("click", showInfo);

    map.enableScrollWheelZoom(true);
    var index = 0;
    var myGeo = new BMap.Geocoder();

    for (var i = 0; i < adds.length; i++) {
        var marker = new BMap.Marker(adds[i]);
        map.addOverlay(marker);
        marker.setLabel(new BMap.Label("我的位置:" + (i + 1), {offset: new BMap.Size(20, -10)}));
        marker.setAnimation(BMAP_ANIMATION_BOUNCE); //跳动的动画
    }
</script>
<body>
<center>

    <form id="updateMapForm" name="updateMapForm" method="post" action="<@ofbizUrl>editGeoLocation</@ofbizUrl>">
        <input type="hidden" name="partyId" value="${partyId?if_exists}"/>
        <input type="hidden" name="geoPointId" value="${geoPointId?if_exists}"/>
        <input type="hidden" name="lat" id="lat" value="${latitude}"/>
        <input type="hidden" name="lng" id="lng" value="${longitude}"/>
        <input type="hidden" name="geopt" value="GEOPT_BAIDU"/>
        <input type="submit" id="createMapButton" class="smallSubmit" value="${uiLabelMap.CommonSubmit}">
    </form>

<#elseif mapType == 'AMAP'>
    <iframe name="amapgeo" frameborder="no"  scrolling = "auto"  style='height:800px;width:100%;' src="<@ofbizUrl>amapgeo?longitude=${longitude}&latitude=${latitude}&path=party</@ofbizUrl>"></iframe>
    <form id="updateMapForm" name="updateMapForm" method="post" action="<@ofbizUrl>editGeoLocation</@ofbizUrl>">
        <input type="hidden" name="partyId" value="${partyId?if_exists}"/>
        <input type="hidden" name="geoPointId" value="${geoPointId?if_exists}"/>
        <input type="hidden" name="lat" id="lat" value="${latitude}"/>
        <input type="hidden" name="lng" id="lng" value="${longitude}"/>
        <input type="hidden" name="geopt" value="GEOPT_AMAP"/>
        <input type="submit" id="createMapButton" class="smallSubmit" value="${uiLabelMap.CommonSubmit}">
    </form>
<#else>
</#if>