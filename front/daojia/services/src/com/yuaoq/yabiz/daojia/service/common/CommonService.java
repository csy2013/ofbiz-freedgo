package com.yuaoq.yabiz.daojia.service.common;

import com.google.gson.Gson;
import com.yuaoq.yabiz.daojia.model.json.BaseResult;
import com.yuaoq.yabiz.daojia.model.json.common.*;
import com.yuaoq.yabiz.daojia.model.json.common.IndexPage.*;
import com.yuaoq.yabiz.daojia.model.json.common.act.ActivityFirst;
import com.yuaoq.yabiz.daojia.model.json.common.channel.ChannelDetail;
import com.yuaoq.yabiz.daojia.model.json.common.logging.Logging;
import com.yuaoq.yabiz.daojia.model.json.common.logging.LoginAccess;
import com.yuaoq.yabiz.daojia.model.json.map.geocoder.LocationAddress;
import com.yuaoq.yabiz.daojia.model.json.map.suggestion.SuggestionAddress;
import javolution.util.FastList;
import javolution.util.FastMap;
import org.ofbiz.base.util.*;
import org.ofbiz.common.geo.GeoWorker;
import org.ofbiz.entity.Delegator;
import org.ofbiz.entity.GenericEntityException;
import org.ofbiz.entity.GenericValue;
import org.ofbiz.entity.util.EntityUtil;
import org.ofbiz.service.DispatchContext;
import org.ofbiz.service.GenericServiceException;
import org.ofbiz.service.LocalDispatcher;
import org.ofbiz.service.ServiceUtil;

import java.io.File;
import java.io.IOException;
import java.util.*;

import static com.yuaoq.yabiz.daojia.service.common.UtilDaoJiaService.getUserAction;

/**
 * Created by changsy on 16/8/28.
 */
public class CommonService {
    
    public static final String module = CommonService.class.getName();
    
    public static Map<String, Object> DaoJia_IsFlashScreen(DispatchContext dispatchContext, Map<String, ? extends Object> context) {
        Map<String, Object> result = ServiceUtil.returnSuccess();
        Locale locale = (Locale) context.get("locale");
        result.put("resultData", new FlashScreen("lauch.isflashscreen", "0", UtilProperties.getMessage("CommonUiLabels", "CommonSuccess", locale), false, true));
        return result;
    }
    
    public static Map<String, Object> DaoJia_Logging(DispatchContext dispatchContext, Map<String, ? extends Object> context) {
        Map<String, Object> result = ServiceUtil.returnSuccess();
        String logType = (String) context.get("logType");
        String json = (String) context.get("json");
        Delegator delegator = dispatchContext.getDelegator();
        
        GenericValue gv = delegator.makeValue("DaoJiaLogging", UtilMisc.toMap("logType", logType, "json", json));
        gv.setNextSeqId();
        String code = "0";
        Locale locale = (Locale) context.get("locale");
        String msg = UtilProperties.getMessage("CommonUiLabels", "CommonSuccess", locale);
        boolean success = true;
        Map map = new HashMap();
        try {
            gv.create();
            map.put("json", "json saved");
        } catch (GenericEntityException e) {
            e.printStackTrace();
            code = "1";
            msg = e.getMessage();
            success = false;
            map.put("json", e.getMessage());
        }
        
        result.put("resultData", new Logging("logging", code, msg, map, success));
        return result;
    }
    
    public static Map<String, Object> DaoJia_AddressSearch(DispatchContext dispatchContext, Map<String, ? extends Object> context) throws IOException {
        Map<String, Object> result = ServiceUtil.returnSuccess();
        String functionId = (String) context.get("functionId");
        String body = (String) context.get("body");
        String appVersion = (String) context.get("appVersion");
        String appName = (String) context.get("appName");
        String platCode = (String) context.get("platCode");
        String rand = (String) context.get("rand");
        Delegator delegator = dispatchContext.getDelegator();
        Locale locale = (Locale) context.get("locale");
        Map bodyMap = new Gson().fromJson(body, Map.class);
        String region = (String) bodyMap.get("region");
        String key = (String) bodyMap.get("key");
        //调用qq地图获取经纬度对应地址信息。
        String qqkey = UtilProperties.getMessage("daojia.properties", "daojia.lbs.qq.key", locale);
        String mapUrl = "http://apis.map.qq.com/ws/place/v1/suggestion/?region=" + region + "&keyword=" + key + "&key=" + qqkey;
        Address addressList = new Address("0", "成功", true, null);
        try {
            String addressJson = HttpClient.getUrlContent(mapUrl);
            SuggestionAddress address = SuggestionAddress.objectFromData(addressJson);
            List<Address.ResultObj> addrList = FastList.newInstance();
            if (UtilValidate.isNotEmpty(address)) {
                List<SuggestionAddress.Data> dataList = address.data;
                if (UtilValidate.isNotEmpty(dataList)) {
                    for (int i = 0; i < dataList.size(); i++) {
                        SuggestionAddress.Data data = dataList.get(i);
                        Address.ResultObj addr = new Address.ResultObj();
                        addr.setAdcode(String.valueOf(data.adcode));
                        addr.setAddress(data.address);
                        addr.setCity(data.city);
                        String geoId = GeoWorker.getGeoIdByName1(data.city, delegator);
                        if (geoId.equals("")) geoId = "0";
                        addr.setCityCode(Integer.parseInt(geoId));
                        String districtId = GeoWorker.getGeoIdByName1(data.district, delegator);
                        addr.setDistrict(data.district);
                        if (districtId.equals("")) districtId = "0";
                        addr.setDistrictCode(Integer.parseInt(districtId));
                        addr.setLocation(new Address.ResultObj.LocationObj(data.location.lat, data.location.lng));
                        addr.setTitle(data.title);
                        addrList.add(addr);
                    }
                }
                addressList.setResult(addrList);
            }
        } catch (HttpClientException e) {
            e.printStackTrace();
        }
        
        result.put("resultData", addressList);
        return result;
    }
    
    public static Map<String, Object> DaoJia_GetCities(DispatchContext dispatchContext, Map<String, ? extends Object> context) throws IOException {
        Map<String, Object> result = ServiceUtil.returnSuccess();
        String functionId = (String) context.get("functionId");
        String body = (String) context.get("body");
        String appVersion = (String) context.get("appVersion");
        String appName = (String) context.get("appName");
        String platCode = (String) context.get("platCode");
        String rand = (String) context.get("rand");
        Locale locale = (Locale) context.get("locale");
        Cities cities = new Cities("address.search", "0", UtilProperties.getMessage("CommonUiLabels", "CommonSuccess", locale), FastList.newInstance(), true);
        LocalDispatcher dispatcher = dispatchContext.getDispatcher();
        Map<String, Object> serviceIn = UtilMisc.toMap("countryGeoId", "CHN", "listOrderBy", "geoId");
        try {
            Map<String, Object> result1 = dispatcher.runSync("getAssociatedStateListJson", serviceIn);
            if (ServiceUtil.isSuccess(result1)) {
                List<Map> stateList = (List<Map>) result1.get("stateList");
                List areaList = FastList.newInstance();
                for (int i = 0; i < stateList.size(); i++) {
                    Map<String, String> state = stateList.get(i);
                    Area area = new Area(state.get("id"), state.get("name"), 3);
                    areaList.add(area);
                }
                cities = new Cities("address.search", "0", UtilProperties.getMessage("CommonUiLabels", "CommonSuccess", locale), areaList, true);
            }
        } catch (GenericServiceException e) {
            e.printStackTrace();
            result.put("resultData", new Cities("address.search", "1", e.getMessage(), e.getMessage(), true));
            return result;
        }
        result.put("resultData", cities);
        return result;
    }
    
    /**
     * 根据用户经纬度获取地址
     * {'longitude':118.69343377235828,'latitude':32.1641749505477,'coordType':'2'}
     *
     * @param dispatchContext
     * @param context
     * @return
     */
    public static Map<String, Object> DaoJia_GetIndex(DispatchContext dispatchContext, Map<String, ? extends Object> context) {
        
        Map<String, Object> result = ServiceUtil.returnSuccess();
        String functionId = (String) context.get("functionId");
        String body = (String) context.get("body");
        Map bodyMap = new Gson().fromJson(body, Map.class);
        Double longitude = (Double) bodyMap.get("longitude");
        Double latitude = (Double) bodyMap.get("latitude");
        String coordType = (String) bodyMap.get("coordType");
        String platCode = (String) context.get("platCode");
        String appVersion = (String) context.get("appVersion");
        String appName = (String) context.get("appName");
        String rand = (String) context.get("rand");
        Delegator delegator = dispatchContext.getDelegator();
        LocalDispatcher localDispatcher = dispatchContext.getDispatcher();
        Locale locale = (Locale) context.get("locale");
        LocalDispatcher dispatcher = dispatchContext.getDispatcher();
        
        Indexh5 indexh5 = new Indexh5("indexh5.getIndex", "0", UtilProperties.getMessage("CommonUiLabels", "CommonSuccess", locale), true, "");
        com.yuaoq.yabiz.daojia.model.json.common.StoreConfig storeConfig = new com.yuaoq.yabiz.daojia.model.json.common.StoreConfig(10, true);
        IndexConfig indexConfig = new IndexConfig("4000-020-020", 16, false, storeConfig);
        //调用qq地图获取经纬度对应地址信息。
        String key = UtilProperties.getMessage("daojia.properties", "daojia.lbs.qq.key", locale);
//          http://apis.map.qq.com/ws/geocoder/v1/?location=32.13304011114029%2C118.71821218510419&get_poi=1&key=B2GBZ-LHWWQ-PHW54-GAR2U-RQIQE-TTFF7&coord_type=1
        String url = "http://apis.map.qq.com/ws/geocoder/v1/?location=" + latitude + "," + longitude + "&get_poi=1&key=" + key + "&coord_type=5";
        try {
            String addressJson = HttpClient.getUrlContent(url);
            LocationAddress locationAddress = LocationAddress.objectFromData(addressJson);
            LocationAddress.Result.Location location = locationAddress.result.location;
            LocationAddress.Result.AdInfo adInfo = locationAddress.result.ad_info;
            String address = locationAddress.result.address;
            String title = locationAddress.result.pois.get(0).title;
            String areaCode = GeoWorker.getGeoIdByName1(adInfo.city, delegator);
            String districtCode = GeoWorker.getGeoIdByName1(adInfo.district, delegator);
            LbsAddress lbsAddress = new LbsAddress(adInfo.city, location.lng, location.lat, areaCode, districtCode, address, adInfo.district, title, adInfo.adcode);
            
            boolean isLoging = false;
            GenericValue userLogin = (GenericValue) context.get("userLogin");
            if (UtilValidate.isNotEmpty(userLogin)) {
                isLoging = true;
            }
            String baseUrl = UtilProperties.getMessage("content.properties", "daojia.baseUrl", locale);
            List<Object> indexDatas = FastList.newInstance();
            
            //选择默认首页模块
            GenericValue indexTemplate = EntityUtil.getFirst(delegator.findByAnd("WebSiteIndexTemplate", UtilMisc.toMap("webSiteId", "daojia", "isDefault", "Y")));
            if (UtilValidate.isNotEmpty(indexTemplate)) {
                //Banner
                
                List<GenericValue> indexAdvertRels = delegator.findByAnd("WebSiteIndexTemplateContent", UtilMisc.toMap("siteIndexTemplateId", indexTemplate.get("siteIndexTemplateId")), UtilMisc.toList("sequenceNum"));
                if (UtilValidate.isNotEmpty(indexAdvertRels)) {
                    for (int j = 0; j < indexAdvertRels.size(); j++) {
                        GenericValue advertRels = indexAdvertRels.get(j);
                        Long seq = advertRels.getLong("sequenceNum");
                        String advertId = advertRels.getString("advertId");
                        String tplStyle = advertRels.getString("tplStyle");
                        UtilDaoJiaService.getBannerData(delegator, baseUrl, indexDatas, advertId, tplStyle,"A",seq);
                        UtilDaoJiaService.getBallData(delegator,baseUrl,indexDatas,advertId,tplStyle,"A",seq);
                        UtilDaoJiaService.getFloorBannerData(delegator,baseUrl,indexDatas,advertId,tplStyle,"A",seq);
                        UtilDaoJiaService.getActData(delegator,baseUrl,indexDatas,advertId,tplStyle,"A",seq);
                        UtilDaoJiaService.getAct1Data(delegator,baseUrl,indexDatas,advertId,tplStyle,"A",seq);
                        UtilDaoJiaService.getAct2Data(delegator,baseUrl,indexDatas,advertId,tplStyle,"A",seq);
                        UtilDaoJiaService.getAct3Data(delegator,baseUrl,indexDatas,advertId,tplStyle,"A",seq);
                        UtilDaoJiaService.getProduct5Data(delegator,baseUrl,indexDatas,advertId,tplStyle,"A",seq,locale,dispatcher);
                        UtilDaoJiaService.getProduct7Data(delegator,baseUrl,indexDatas,advertId,tplStyle,"A",seq,locale,dispatcher);
                    }
                }
            }
            IndexResult indexResult = new IndexResult(indexConfig, isLoging, lbsAddress, indexDatas);
            indexh5 = new Indexh5("indexh5.getIndex", "0", UtilProperties.getMessage("CommonUiLabels", "CommonSuccess", locale), true, indexResult);
        } catch (
                HttpClientException e)
        
        {
            e.printStackTrace();
        } catch (
                GenericEntityException e)
        
        {
            e.printStackTrace();
            indexh5.ret = e.getMessage();
        }
        result.put("resultData", indexh5);
        return result;
    }
    
    
    public static Map<String, Object> DaoJia_GrabFloor(DispatchContext dcx, Map<String, ? extends Object> context) {
        Map<String, Object> result = ServiceUtil.returnSuccess();
        
        String functionId = (String) context.get("functionId");
        String body = (String) context.get("body");
        String appVersion = (String) context.get("appVersion");
        String appName = (String) context.get("appName");
        String platCode = (String) context.get("platCode");
        String rand = (String) context.get("rand");
        Locale locale = (Locale) context.get("locale");
        String homePath = System.getProperty("ofbiz.home");
        String json = null;
        
        GrabFloor floor = new GrabFloor("grab.grabFloor", "0", UtilProperties.getMessage("CommonUiLabels", "CommonSuccess", locale), true, "");
        try {
            json = FileUtil.readString("utf-8", new File(homePath + "/daojia/daojia/config/miaoshaList.json"));
            List<MiaoShaList> miaoshaList = MiaoShaList.arrayMiaoShaListFromData(json);
            SecKillData secKillData = new SecKillData("抢购中", 60, 8355, "距结束", 1, miaoshaList);
            
            floor = new GrabFloor("grab.grabFloor", "0", UtilProperties.getMessage("CommonUiLabels", "CommonSuccess", locale), true, secKillData);
        } catch (IOException e) {
            e.printStackTrace();
            floor.setResult(e.getMessage());
            floor.setSuccess(false);
            floor.setMsg(UtilProperties.getMessage("CommonUiLabels", "CommonError", locale));
            floor.setCode("1");
        }
        result.put("resultData", floor);
        
        return result;
    }
    
    public static Map<String, Object> DaoJia_Log_Mlog(DispatchContext dcx, Map<String, ? extends Object> context) {
        String appid = (String) context.get("appid");
        String returnurl = (String) context.get("returnurl");
        String platCode = (String) context.get("platCode");
        String appName = (String) context.get("appName");
        String appVersion = (String) context.get("appVersion");
        String body = (String) context.get("body");
        String sid = (String) context.get("sid");
        Map<String, Object> result = ServiceUtil.returnSuccess();
        Map retMap = FastMap.newInstance();
        retMap.put("c", "0");
        result.put("resultData", retMap);
        return result;
    }
    
    public static Map<String, Object> DaoJia_ViewUUID(DispatchContext dcx, Map<String, ? extends Object> context) {
        String appid = (String) context.get("appid");
        String returnurl = (String) context.get("returnurl");
        String platCode = (String) context.get("platCode");
        String appName = (String) context.get("appName");
        String appVersion = (String) context.get("appVersion");
        String body = (String) context.get("body");
        String sid = (String) context.get("sid");
        Map<String, Object> result = ServiceUtil.returnSuccess();
        Map retMap = FastMap.newInstance();
        retMap.put("c", "0");
        result.put("resultData", retMap);
        return result;
    }
    
    /**
     * {"errCode":"0","g2Int":"60","g2Sz":"10","g3Int":"60","g3Sz":"10","g4Int":"60","g4Sz":"10","ret":"1","wifiInt":"30","wifiSz":"20"}
     *
     * @param dcx
     * @param context
     * @return
     */
    
    public static Map<String, Object> DaoJia_M_Access(DispatchContext dcx, Map<String, ? extends Object> context) {
        String appid = (String) context.get("appid");
        String returnurl = (String) context.get("returnurl");
        String platCode = (String) context.get("platCode");
        String appName = (String) context.get("appName");
        String appVersion = (String) context.get("appVersion");
        String body = (String) context.get("body");
        String sid = (String) context.get("sid");
        Map<String, Object> result = ServiceUtil.returnSuccess();
        LoginAccess access = new LoginAccess("0", "60", "10", "60", "10", "60", "10", "1", "30", "20");
        result.put("resultData", access);
        return result;
    }
    
    public static Map<String, Object> DaoJia_ShowFeedbackBanner(DispatchContext dcx, Map<String, ? extends Object> context) {
        Map<String, Object> result = ServiceUtil.returnSuccess();
        String functionId = (String) context.get("functionId");
        String body = (String) context.get("body");
        String appVersion = (String) context.get("appVersion");
        String appName = (String) context.get("appName");
        String platCode = (String) context.get("platCode");
        Locale locale = (Locale) context.get("locale");
        String homePath = System.getProperty("ofbiz.home");
        String json = null;
        try {
            json = FileUtil.readString("utf-8", new File(homePath + "/daojia/daojia/config/party/feedback.json"));
            Map retMap = new Gson().fromJson(json, Map.class);
            result.put("resultData", retMap);
        } catch (IOException e) {
            e.printStackTrace();
            BaseResult baseResult = new BaseResult("user.showfeedbackbanner", "0", UtilProperties.getMessage("CommonUiLabels", "CommonSuccess", locale), true);
            result.put("resultData", baseResult);
        }
        
        return result;
    }
    
    public static Map<String, Object> DaoJia_HelpPage(DispatchContext dcx, Map<String, ? extends Object> context) {
        Map<String, Object> result = ServiceUtil.returnSuccess();
        String functionId = (String) context.get("functionId");
        String body = (String) context.get("body");
        String appVersion = (String) context.get("appVersion");
        String appName = (String) context.get("appName");
        String platCode = (String) context.get("platCode");
        Locale locale = (Locale) context.get("locale");
        String homePath = System.getProperty("ofbiz.home");
        String json = null;
        try {
            json = FileUtil.readString("utf-8", new File(homePath + "/daojia/daojia/config/party/helppage.json"));
            Map retMap = new Gson().fromJson(json, Map.class);
            result.put("resultData", retMap);
        } catch (IOException e) {
            e.printStackTrace();
            BaseResult baseResult = new BaseResult("me.helpPage", "0", UtilProperties.getMessage("CommonUiLabels", "CommonSuccess", locale), true);
            result.put("resultData", baseResult);
        }
        
        return result;
    }
    
    public static Map<String, Object> DaoJia_GetSearchInfos(DispatchContext dcx, Map<String, ? extends Object> context) {
        Map<String, Object> result = ServiceUtil.returnSuccess();
        Delegator delegator = dcx.getDelegator();
        Locale locale = (Locale) context.get("locale");
        LocalDispatcher dispatcher = dcx.getDispatcher();
        String functionId = (String) context.get("functionId");
        String body = (String) context.get("body");
        String appVersion = (String) context.get("appVersion");
        String appName = (String) context.get("appName");
        String platCode = (String) context.get("platCode");
        String homePath = System.getProperty("ofbiz.home");
        Map<String, Object> retMap = FastMap.newInstance();
        retMap.put("id", "user.getSearchInfos");
        retMap.put("msg", "成功");
        retMap.put("code", "0");
        retMap.put("success", true);
        
        result.put("resultData", retMap);
        return result;
    }
    
    public static Map<String, Object> DaoJia_GetAddressN(DispatchContext dcx, Map<String, ? extends Object> context) {
        Map<String, Object> result = ServiceUtil.returnSuccess();
        Delegator delegator = dcx.getDelegator();
        Locale locale = (Locale) context.get("locale");
        LocalDispatcher dispatcher = dcx.getDispatcher();
        String functionId = (String) context.get("functionId");
        String body = (String) context.get("body");
        String appVersion = (String) context.get("appVersion");
        String appName = (String) context.get("appName");
        String platCode = (String) context.get("platCode");
        String homePath = System.getProperty("ofbiz.home");
        Map<String, Object> retMap = FastMap.newInstance();
        Map bodyMap = new Gson().fromJson(body, Map.class);
        Double longitude = (Double) bodyMap.get("longitude");
        Double latitude = (Double) bodyMap.get("latitude");
        String coordType = (String) bodyMap.get("coordType");
        
        //调用qq地图获取经纬度对应地址信息。
        String key = UtilProperties.getMessage("daojia.properties", "daojia.lbs.qq.key", locale);
//          http://apis.map.qq.com/ws/geocoder/v1/?location=32.13304011114029%2C118.71821218510419&get_poi=1&key=B2GBZ-LHWWQ-PHW54-GAR2U-RQIQE-TTFF7&coord_type=1
        String url = "http://apis.map.qq.com/ws/geocoder/v1/?location=" + latitude + "," + longitude + "&get_poi=1&key=" + key + "&coord_type=5";
        try {
            String addressJson = HttpClient.getUrlContent(url);
            LocationAddress locationAddress = LocationAddress.objectFromData(addressJson);
            LocationAddress.Result.Location location = locationAddress.result.location;
            LocationAddress.Result.AdInfo adInfo = locationAddress.result.ad_info;
            String address = locationAddress.result.address;
            String title = locationAddress.result.pois.get(0).title;
            String areaCode = GeoWorker.getGeoIdByName1(adInfo.city, delegator);
            String districtCode = GeoWorker.getGeoIdByName1(adInfo.district, delegator);
            AddressN addressN = new AddressN(adInfo.city, location.lng, location.lat, areaCode, districtCode, address, adInfo.district, title, adInfo.adcode);
            retMap.put("result", addressN);
        } catch (HttpClientException e) {
            e.printStackTrace();
        }
        
        retMap.put("id", "user.getSearchInfos");
        retMap.put("msg", "成功");
        retMap.put("code", "0");
        retMap.put("success", true);
        
        result.put("resultData", retMap);
        return result;
    }
    
    public static Map<String, Object> DaoJia_FindRedPointByPin(DispatchContext dcx, Map<String, ? extends Object> context) {
        Map<String, Object> result = ServiceUtil.returnSuccess();
        Delegator delegator = dcx.getDelegator();
        Locale locale = (Locale) context.get("locale");
        LocalDispatcher dispatcher = dcx.getDispatcher();
        String functionId = (String) context.get("functionId");
        String body = (String) context.get("body");
        String appVersion = (String) context.get("appVersion");
        String appName = (String) context.get("appName");
        String platCode = (String) context.get("platCode");
        String homePath = System.getProperty("ofbiz.home");
        Map<String, Object> retMap = FastMap.newInstance();
        retMap.put("id", "msgcenter.findRedPointByPin");
        retMap.put("msg", "成功");
        retMap.put("code", "0");
        retMap.put("success", true);
        List<Map> retMaps = FastList.newInstance();
        Map map = FastMap.newInstance();
        map.put("msgType", 0);
        map.put("status", 0);
        retMaps.add(map);
        
        Map map1 = FastMap.newInstance();
        map1.put("msgType", 1);
        map1.put("status", 0);
        retMaps.add(map1);
        Map map2 = FastMap.newInstance();
        map2.put("msgType", 2);
        map2.put("status", 0);
        retMaps.add(map2);
        Map map3 = FastMap.newInstance();
        map3.put("msgType", 3);
        map3.put("status", 0);
        retMaps.add(map3);
        Map map4 = FastMap.newInstance();
        map4.put("msgType", 4);
        map4.put("status", 0);
        retMaps.add(map4);
        retMap.put("result", retMaps);
        result.put("resultData", retMap);
        return result;
    }
    
    public static Map<String, Object> DaoJia_GetVoucherListFive(DispatchContext dcx, Map<String, ? extends Object> context) {
        Map<String, Object> result = ServiceUtil.returnSuccess();
        Delegator delegator = dcx.getDelegator();
        Locale locale = (Locale) context.get("locale");
        LocalDispatcher dispatcher = dcx.getDispatcher();
        String functionId = (String) context.get("functionId");
        String body = (String) context.get("body");
        String appVersion = (String) context.get("appVersion");
        String appName = (String) context.get("appName");
        String platCode = (String) context.get("platCode");
        String homePath = System.getProperty("ofbiz.home");
        Map<String, Object> retMap = FastMap.newInstance();
        retMap.put("id", "voucher.getVoucherListFive");
        retMap.put("msg", "系统繁忙，请稍后再试(A1104)");
        retMap.put("code", "0");
        retMap.put("success", true);
        retMap.put("detail", "优惠券列表使用降级方案");
        
        result.put("resultData", retMap);
        return result;
    }
    
    public static Map<String, Object> DaoJia_GetChannel(DispatchContext dcx, Map<String, ? extends Object> context) {
        Map<String, Object> result = ServiceUtil.returnSuccess();
        String functionId = (String) context.get("functionId");
        String body = (String) context.get("body");
        String appVersion = (String) context.get("appVersion");
        String appName = (String) context.get("appName");
        String platCode = (String) context.get("platCode");
        String lng = (String) context.get("lng");
        String lat = (String) context.get("lat");
        String city_id = (String) context.get("city_id");
        String rand = (String) context.get("rand");
        Delegator delegator = dcx.getDelegator();
        Locale locale = (Locale) context.get("locale");
        LocalDispatcher dispatcher = dcx.getDispatcher();
//        {'ref':'#channelPage/channelId:00001/channelName:xxx/LID:5','longitude':118.723518,'latitude':32.130951,'city':'xxx','address':'xxx','coordType':'2','channelId':'00001'}
        Map bodyMap = new Gson().fromJson(body, Map.class);
        String channelId = (String) bodyMap.get("channelId");
        String baseUrl = UtilProperties.getMessage("content.properties", "daojia.baseUrl", locale);
        ChannelDetail channelDetail = new ChannelDetail("channel.getChannelDetail", "0", "成功", true);
        try {
            ChannelDetail.Result channelResult= new ChannelDetail.Result();
            ChannelDetail.Result.Config config = new ChannelDetail.Result.Config();
            
            GenericValue specialPage = delegator.findByPrimaryKey("SpecialPage", UtilMisc.toMap("specialPageId", channelId));
            if(UtilValidate.isNotEmpty(specialPage)){
                String name = specialPage.getString("pageName");
                config.gloabTitle = name;
                config.isWaimai = "NO";
                config.userAction = "";
            }
            channelResult.config = config;
            List<GenericValue> pages = delegator.findByAnd("SpecialPageAdvertRel", UtilMisc.toMap("specialPageId", channelId), UtilMisc.toList("sequenceNum"));
            List<Object> indexDatas = FastList.newInstance();
            if (UtilValidate.isNotEmpty(pages)) {
                for (int k = 0; k < pages.size(); k++) {
                    GenericValue specialPageRel = pages.get(k);
                    List<GenericValue> specialAdvertRels = delegator.findByAnd("SpecialPageContent", UtilMisc.toMap("advertId", specialPageRel.get("advertId")));
                    for (int j = 0; j < specialAdvertRels.size(); j++) {
                        GenericValue advertRels = specialAdvertRels.get(j);
                        Long seq = (Long) advertRels.get("sequenceNum");
                        String advertId = advertRels.getString("advertId");
                        String tplStyle = advertRels.getString("tplStyle");
                        UtilDaoJiaService.getBannerData(delegator, baseUrl, indexDatas, advertId, tplStyle,"C",seq);
                        UtilDaoJiaService.getBallData(delegator,baseUrl,indexDatas,advertId,tplStyle,"C",seq);
                        UtilDaoJiaService.getFloorBannerData(delegator,baseUrl,indexDatas,advertId,tplStyle,"C",seq);
                        UtilDaoJiaService.getActData(delegator,baseUrl,indexDatas,advertId,tplStyle,"C",seq);
                        UtilDaoJiaService.getAct1Data(delegator,baseUrl,indexDatas,advertId,tplStyle,"C",seq);
                        UtilDaoJiaService.getAct2Data(delegator,baseUrl,indexDatas,advertId,tplStyle,"C",seq);
                        UtilDaoJiaService.getAct3Data(delegator,baseUrl,indexDatas,advertId,tplStyle,"C",seq);
                        UtilDaoJiaService.getProduct5Data(delegator,baseUrl,indexDatas,advertId,tplStyle,"C",seq,locale,dispatcher);
                        UtilDaoJiaService.getProduct7Data(delegator,baseUrl,indexDatas,advertId,tplStyle,"C",seq,locale,dispatcher);
                    }
                 }
                channelResult.data = indexDatas;
                channelDetail.result =channelResult;
            }
        } catch (GenericEntityException e) {
            e.printStackTrace();
        }
       
        result.put("resultData", channelDetail);
        return result;
    }
    
    public static Map<String, Object> DaoJia_GetActivityFirst(DispatchContext dcx, Map<String, ? extends Object> context) {
        Map<String, Object> result = ServiceUtil.returnSuccess();
        String functionId = (String) context.get("functionId");
        String body = (String) context.get("body");
        String appVersion = (String) context.get("appVersion");
        String appName = (String) context.get("appName");
        String platCode = (String) context.get("platCode");
        String lng = (String) context.get("lng");
        String lat = (String) context.get("lat");
        String city_id = (String) context.get("city_id");
        String rand = (String) context.get("rand");
        Delegator delegator = dcx.getDelegator();
        Locale locale = (Locale) context.get("locale");
        LocalDispatcher dispatcher = dcx.getDispatcher();
//        {'longitude':118.73441,'latitude':32.1399,'activityId':'15823','city':'xxx','shareFlag':false,'coordType':2}
        Map bodyMap = new Gson().fromJson(body, Map.class);
        String activityId = (String) bodyMap.get("activityId");
        String baseUrl = UtilProperties.getMessage("content.properties", "daojia.baseUrl", locale);
        ActivityFirst activityFirst = new ActivityFirst("channel.getChannelDetail", "0", "成功", true);
        try {
            ActivityFirst.Result activityResult= new ActivityFirst.Result();
            ActivityFirst.Result.Config config = new ActivityFirst.Result.Config();
            
            GenericValue specialPage = delegator.findByPrimaryKey("SpecialPage", UtilMisc.toMap("specialPageId", activityId));
            if(UtilValidate.isNotEmpty(specialPage)){
                String name = specialPage.getString("pageName");
                config.gloabTitle = name;
                config.isShowStoreButton = true;
                config.isCart = false;
            }
            activityResult.config = config;
            List<GenericValue> pages = delegator.findByAnd("SpecialPageAdvertRel", UtilMisc.toMap("specialPageId", activityId), UtilMisc.toList("sequenceNum"));
            List<Object> indexDatas = FastList.newInstance();
            if (UtilValidate.isNotEmpty(pages)) {
                for (int k = 0; k < pages.size(); k++) {
                    GenericValue specialPageRel = pages.get(k);
                    Long seq = specialPageRel.getLong("sequenceNum");
                    List<GenericValue> specialAdvertRels = delegator.findByAnd("SpecialPageContent", UtilMisc.toMap("advertId", specialPageRel.get("advertId")));
                    for (int j = 0; j < specialAdvertRels.size(); j++) {
                        GenericValue advertRels = specialAdvertRels.get(j);
                        String advertId = advertRels.getString("advertId");
                        String tplStyle = advertRels.getString("tplStyle");
                        UtilDaoJiaService.getBannerData(delegator, baseUrl, indexDatas, advertId, tplStyle,"B",seq);
                        UtilDaoJiaService.getBallData(delegator,baseUrl,indexDatas,advertId,tplStyle,"B",seq);
                        UtilDaoJiaService.getFloorBannerData(delegator,baseUrl,indexDatas,advertId,tplStyle,"B",seq);
                        UtilDaoJiaService.getActData(delegator,baseUrl,indexDatas,advertId,tplStyle,"B",seq);
                        UtilDaoJiaService.getAct1Data(delegator,baseUrl,indexDatas,advertId,tplStyle,"B",seq);
                        UtilDaoJiaService.getAct2Data(delegator,baseUrl,indexDatas,advertId,tplStyle,"B",seq);
                        UtilDaoJiaService.getAct3Data(delegator,baseUrl,indexDatas,advertId,tplStyle,"B",seq);
                        UtilDaoJiaService.getProduct5Data(delegator,baseUrl,indexDatas,advertId,tplStyle,"B",seq,locale,dispatcher);
                        UtilDaoJiaService.getProduct7Data(delegator,baseUrl,indexDatas,advertId,tplStyle,"B",seq,locale,dispatcher);
                    }
                }
                activityResult.data = indexDatas;
                activityFirst.result =activityResult;
            }
        } catch (GenericEntityException e) {
            e.printStackTrace();
        }
        
        result.put("resultData", activityFirst);
        return result;
    }
    
   
}
