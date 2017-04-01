<!DOCTYPE html>
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=utf-8">
    <title>输入提示后查询</title>
    <style type="text/css">

        #tip {
            background-color: #fff;
            border: 1px solid #ccc;
            padding-left: 10px;
            padding-right: 2px;
            min-height: 80px;
            top: 10px;
            font-size: 12px;
            left: 100px;
            right: 60px;
            border-radius: 3px;
            overflow: auto;
            line-height: 20px;
            min-width: 400px;
        }

        #tip input[type="button"] {
            background-color: #0D9BF2;
            height: 25px;
            text-align: center;
            line-height: 25px;
            color: #fff;
            font-size: 12px;
            border-radius: 3px;
            outline: none;
            border: 0;
            cursor: pointer;
        }

        #result1 {
            max-height: 300px;
        }
    </style>
</head>
<#assign mapkey = Static["org.ofbiz.base.util.UtilProperties"].getPropertyValue("map.properties", "map.amap.key")>
<script type="text/javascript" src="http://webapi.amap.com/maps?v=1.3&key=${mapkey}"></script>
<br/>

<div id="tip">

    <label>请输入关键字：</label>
    <input type="text" id="keyword" name="keyword" value="" onkeydown='keydown(event)' style="width: 95%;"/>
    <div id="result1" name="result1"></div>
</div>
<div id="l-map"
     style="border:1px solid #979797; background-color:#e5e3df; width:${geoChart.width}; height:${geoChart.height}; margin:2em auto;"></div>
<div id="searchResultPanel" style="border:1px solid #C0C0C0;width:150px;height:auto; display:none;"></div>
<script type="text/javascript">
    var map = new AMap.Map('l-map', {
        resizeEnable: true,
        view: new AMap.View2D({
        <#if path?default('') == 'party'>
            center: new AMap.LngLat(${longitude},${latitude}),
        <#else>
            <#if geoChart.points?exists>
                <#list geoChart.points as point>
                    <#if point_index ==0>
                        center: new AMap.LngLat(${point.lon}, ${point.lat}),
                    </#if>
                </#list>
            <#else>
                city: '南京市',
            </#if>
        </#if>
            zoom: 13
        })
    });
    var clickEventListener = AMap.event.addListener(map, 'click', function (e) {
            true,
        <#if path?default('') ==  'party'>
    parent.document.updateMapForm.lat.value = e.lnglat.getLat();
        parent.document.updateMapForm.lng.value = e.lnglat.getLng();
    <#else>

        parent.document.forms['EditFacilityGeoPoint'].EditFacilityGeoPoint_latitude.value = e.lnglat.getLat();
        parent.document.forms['EditFacilityGeoPoint'].EditFacilityGeoPoint_longitude.value = e.lnglat.getLng();
    </#if>
        var marker = new AMap.Marker({
            map: map,
            position: new AMap.LngLat(e.lnglat.getLng(), e.lnglat.getLat())
        });
        false
    });
    var windowsArr = [];
    var marker = [];

    var marker1 = new AMap.Marker({
        map: map
    });

    document.getElementById("keyword").onkeyup = keydown;
    //输入提示
    function autoSearch() {
        var keywords = document.getElementById("keyword").value;
        var auto;
        //加载输入提示插件
        AMap.service(["AMap.Autocomplete"], function () {
            var autoOptions = {
                city: "" //城市，默认全国
            };
            auto = new AMap.Autocomplete(autoOptions);
            //查询成功时返回查询结果
            if (keywords.length > 0) {
                auto.search(keywords, function (status, result) {
                    autocomplete_CallBack(result);
                });
            }
            else {
                document.getElementById("result1").style.display = "none";
            }
        });
    }
    //输出输入提示结果的回调函数
    function autocomplete_CallBack(data) {

        var resultStr = "";
        var tipArr = data.tips;
        if (tipArr && tipArr.length > 0) {
            for (var i = 0; i < tipArr.length; i++) {

                resultStr += "<div id='divid" + (i + 1) + "' onmouseover='openMarkerTipById(" + (i + 1)
                        + ",this)' onclick='selectResult(" + i + ")' onmouseout='onmouseout_MarkerStyle(" + (i + 1)
                        + ",this)' style=\"font-size: 13px;cursor:pointer;padding:5px 5px 5px 5px;\"" + "data=" + tipArr[i].adcode + ">" + tipArr[i].name + "<span style='color:#C1C1C1;'>" + tipArr[i].district + "</span></div>";
            }
        }
        else {
            resultStr = " π__π 亲,人家找不到结果!<br />要不试试：<br />1.请确保所有字词拼写正确<br />2.尝试不同的关键字<br />3.尝试更宽泛的关键字";
        }
        document.getElementById("result1").curSelect = -1;
        document.getElementById("result1").tipArr = tipArr;
        document.getElementById("result1").innerHTML = resultStr;
        document.getElementById("result1").style.display = "block";
    }

    //输入提示框鼠标滑过时的样式
    function openMarkerTipById(pointid, thiss) {  //根据id打开搜索结果点tip
        thiss.style.background = '#CAE1FF';
    }

    //输入提示框鼠标移出时的样式
    function onmouseout_MarkerStyle(pointid, thiss) {  //鼠标移开后点样式恢复
        thiss.style.background = "";
    }
    //从输入提示框中选择关键字并查询
    function selectResult(index) {
        if (index < 0) {
            return;
        }
        if (navigator.userAgent.indexOf("MSIE") > 0) {
            document.getElementById("keyword").onpropertychange = null;
            document.getElementById("keyword").onfocus = focus_callback;
        }
        //截取输入提示的关键字部分
        var text = document.getElementById("divid" + (index + 1)).innerHTML.replace(/<[^>].*?>.*<\/[^>].*?>/g, "");
        var cityCode = document.getElementById("divid" + (index + 1)).getAttribute('data');
        document.getElementById("keyword").value = text;
        document.getElementById("result1").style.display = "none";
        //根据选择的输入提示关键字查询
        map.plugin(["AMap.PlaceSearch"], function () {
            var msearch = new AMap.PlaceSearch();  //构造地点查询类
            AMap.event.addListener(msearch, "complete", placeSearch_CallBack); //查询成功时的回调函数
            msearch.setCity(cityCode);
            msearch.search(text);  //关键字查询查询
        });
    }

    //定位选择输入提示关键字
    function focus_callback() {
        if (navigator.userAgent.indexOf("MSIE") > 0) {
            document.getElementById("keyword").onpropertychange = autoSearch;
        }
    }
    //输出关键字查询结果的回调函数
    function placeSearch_CallBack(data) {
        //清空地图上的InfoWindow和Marker
        windowsArr = [];
        marker = [];
        map.clearMap();
        var resultStr1 = "";
        var poiArr = data.poiList.pois;
        var resultCount = poiArr.length;
        for (var i = 0; i < resultCount; i++) {
            resultStr1 += "<div id='divid" + (i + 1) + "' onmouseover='openMarkerTipById1(" + i + ",this)' onmouseout='onmouseout_MarkerStyle(" + (i + 1) + ",this)' style=\"font-size: 12px;cursor:pointer;padding:0px 0 4px 2px; border-bottom:1px solid #C1FFC1;\"><table><tr><td><img src=\"http://webapi.amap.com/images/" + (i + 1) + ".png\"></td>" + "<td><h3><font color=\"#00a6ac\">名称: " + poiArr[i].name + "</font></h3>";
            resultStr1 += TipContents(poiArr[i].type, poiArr[i].address, poiArr[i].tel) + "</td></tr></table></div>";
            addmarker(i, poiArr[i]);
            if (i == 0) {

            <#if path?default('') == 'party'>
                parent.document.updateMapForm.lat.value = poiArr[i].location.getLat();
                parent.document.updateMapForm.lng.value = poiArr[i].location.getLng();
            <#else>
                parent.document.forms['EditFacilityGeoPoint'].EditFacilityGeoPoint_latitude.value = poiArr[i].location.lat;
                parent.document.forms['EditFacilityGeoPoint'].EditFacilityGeoPoint_longitude.value = poiArr[i].location.lng;
            </#if>
            }
        }
        map.setFitView();
    }
    //鼠标滑过查询结果改变背景样式，根据id打开信息窗体
    function openMarkerTipById1(pointid, thiss) {
        thiss.style.background = '#CAE1FF';
        windowsArr[pointid].open(map, marker[pointid]);
    }
    //添加查询结果的marker&infowindow
    function addmarker(i, d) {
        var lngX = d.location.getLng();
        var latY = d.location.getLat();
        var markerOption = {
            map: map,
            icon: "http://webapi.amap.com/images/" + (i + 1) + ".png",
            position: new AMap.LngLat(lngX, latY)
        };
        var mar = new AMap.Marker(markerOption);
        marker.push(new AMap.LngLat(lngX, latY));

        var infoWindow = new AMap.InfoWindow({
            content: "<h3><font color=\"#00a6ac\">  " + (i + 1) + ". " + d.name + "</font></h3>" + TipContents(d.type, d.address, d.tel),
            size: new AMap.Size(300, 0),
            autoMove: true,
            offset: new AMap.Pixel(0, -30)
        });
        windowsArr.push(infoWindow);
        var aa = function (e) {
            infoWindow.open(map, mar.getPosition());
        };
        AMap.event.addListener(mar, "mouseover", aa);
    }

    //infowindow显示内容
    function TipContents(type, address, tel) {  //窗体内容
        if (type == "" || type == "undefined" || type == null || type == " undefined" || typeof type == "undefined") {
            type = "暂无";
        }
        if (address == "" || address == "undefined" || address == null || address == " undefined" || typeof address == "undefined") {
            address = "暂无";
        }
        if (tel == "" || tel == "undefined" || tel == null || tel == " undefined" || typeof address == "tel") {
            tel = "暂无";
        }
        var str = "  地址：" + address + "<br />  电话：" + tel + " <br />  类型：" + type;
        return str;
    }
    function keydown(event) {
        var key = (event || window.event).keyCode;
        var result = document.getElementById("result1");
        var cur = result.curSelect;
        if (key === 40) {//down
            if (cur + 1 < result.childNodes.length) {
                if (result.childNodes[cur]) {
                    result.childNodes[cur].style.background = '';
                }
                result.curSelect = cur + 1;
                result.childNodes[cur + 1].style.background = '#CAE1FF';
                document.getElementById("keyword").value = result.tipArr[cur + 1].name;
            }
        } else if (key === 38) {//up
            if (cur - 1 >= 0) {
                if (result.childNodes[cur]) {
                    result.childNodes[cur].style.background = '';
                }
                result.curSelect = cur - 1;
                result.childNodes[cur - 1].style.background = '#CAE1FF';
                document.getElementById("keyword").value = result.tipArr[cur - 1].name;
            }
        } else if (key === 13) {
            var res = document.getElementById("result1");
            if (res && res['curSelect'] !== -1) {
                selectResult(document.getElementById("result1").curSelect);
            }
        } else {
            autoSearch();
        }
    }


</script>
</body>
</html>
