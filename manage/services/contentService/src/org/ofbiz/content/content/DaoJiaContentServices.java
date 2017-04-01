package org.ofbiz.content.content;

import org.ofbiz.base.util.UtilDateTime;
import org.ofbiz.base.util.UtilHttp;
import org.ofbiz.base.util.UtilMisc;
import org.ofbiz.base.util.UtilValidate;
import org.ofbiz.entity.Delegator;
import org.ofbiz.entity.GenericEntityException;
import org.ofbiz.entity.GenericValue;
import org.ofbiz.service.LocalDispatcher;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;
import java.util.Map;

/**
 * Created by changsy on 2016/10/14.
 */
public class DaoJiaContentServices {
    /**
     * 创建轮播图，advert,advertContent.
     *
     * @param request
     * @param response
     * @return
     */
    public static String createBanner(HttpServletRequest request, HttpServletResponse response) {
        Delegator delegator = (Delegator) request.getAttribute("delegator");
        LocalDispatcher dispatcher = (LocalDispatcher) request.getAttribute("dispatcher");
        Map<String, Object> params = UtilHttp.getParameterMap(request);
        //获取首页模板Id
        String templateId = (String) params.get("templateId");
        String advertName = (String) params.get("advertName");
        String sequenceNum = (String) params.get("sequenceNum");
        Long sequence = (sequenceNum != null ? Long.parseLong(sequenceNum) : 0);
        String tplStyle = (String) params.get("tplStyle");
        //生成advert，生成advertRel，生成advertContent
        String advertId = delegator.getNextSeqId("Advert");
        GenericValue advert = delegator.makeValue("Advert");
        advert.put("advertId", advertId);
        advert.put("advertName", advertName);
        advert.put("advertTypeId", "banner");
        advert.put("dateCreated", UtilDateTime.nowTimestamp());
        advert.put("webSiteId", "daojia");
        GenericValue advertRel = delegator.makeValue("WebSiteAdvertRel", UtilMisc.toMap("advertId", advertId, "siteIndexTemplateId", templateId, "sequenceNum", sequence, "tplStyle", tplStyle));
        //轮播图的信息
        
        //预定义
        try {
            advert.create();
            advertRel.create();
            for (int i = 0; i < 6; i++) {
                String imgSrc = (String) params.get("upload" + (i + 1) + "_imgSrc");
                String seq = (String) params.get("upload" + (i + 1) + "_sequenceNum");
                String defineType = (String) params.get("upload" + (i + 1) + "_linkType");
                String relationId = (String) params.get("upload" + (i + 1) + "_val");
                if (UtilValidate.isNotEmpty(imgSrc) && UtilValidate.isNotEmpty(seq) && UtilValidate.isNotEmpty(defineType) && UtilValidate.isNotEmpty(relationId)) {
                    GenericValue advertContent = delegator.makeValue("AdvertContent");
                    String advertContentId = delegator.getNextSeqId("AdvertContent");
                    advertContent.put("advertId", advertId);
                    advertContent.put("advertContentId", advertContentId);
                    advertContent.put("relationId", relationId);
                    advertContent.put("imgSrc", imgSrc);
                    advertContent.put("sequenceNum", Long.parseLong(seq));
                    advertContent.put("fromDate", UtilDateTime.nowTimestamp());
                    advertContent.put("defineType", "A");
                    advertContent.put("relationTypeId", defineType);
                    advertContent.create();
                }
                
            }
        } catch (GenericEntityException e) {
            e.printStackTrace();
        }
        return "success";
    }
    
    /**
     * 创建轮播图，advert,advertContent.
     *
     * @param request
     * @param response
     * @return
     */
    public static String updateBanner(HttpServletRequest request, HttpServletResponse response) {
        Delegator delegator = (Delegator) request.getAttribute("delegator");
        LocalDispatcher dispatcher = (LocalDispatcher) request.getAttribute("dispatcher");
        Map<String, Object> params = UtilHttp.getParameterMap(request);
        //获取首页模板Id
        String templateId = (String) params.get("templateId");
        String advertName = (String) params.get("advertName");
        String sequenceNum = (String) params.get("sequenceNum");
        String advertId = (String) params.get("advertId");
        Long sequence = (sequenceNum != null ? Long.parseLong(sequenceNum) : 0);
        String tplStyle = (String) params.get("tplStyle");
        //生成advert，生成advertRel，生成advertContent
        
        GenericValue advert = delegator.makeValue("Advert");
        advert.put("advertId", advertId);
        advert.put("advertName", advertName);
        advert.put("advertTypeId", "banner");
        advert.put("dateCreated", UtilDateTime.nowTimestamp());
        advert.put("webSiteId", "daojia");
        
        GenericValue advertRel = delegator.makeValue("WebSiteAdvertRel", UtilMisc.toMap("advertId", advertId, "siteIndexTemplateId", templateId, "sequenceNum", sequence, "tplStyle", tplStyle));
        //轮播图的信息
        //预定义
        try {
            advert.store();
            advertRel.store();
            for (int i = 0; i < 6; i++) {
                String imgSrc = (String) params.get("upload" + (i + 1) + "_imgSrc");
                String seq = (String) params.get("upload" + (i + 1) + "_sequenceNum");
                String defineType = (String) params.get("upload" + (i + 1) + "_linkType");
                String relationId = (String) params.get("upload" + (i + 1) + "_val");
                String advertContentId = (String) params.get("upload" + (i + 1) + "_advertContentId");
                if (UtilValidate.isNotEmpty(imgSrc) && UtilValidate.isNotEmpty(seq) && UtilValidate.isNotEmpty(defineType) && UtilValidate.isNotEmpty(relationId)) {
                    GenericValue advertContent = delegator.makeValue("AdvertContent");
                    
                    advertContent.put("advertId", advertId);
                    advertContent.put("advertContentId", advertContentId);
                    advertContent.put("relationId", relationId);
                    advertContent.put("imgSrc", imgSrc);
                    advertContent.put("sequenceNum", Long.parseLong(seq));
                    advertContent.put("fromDate", UtilDateTime.nowTimestamp());
                    advertContent.put("defineType", "A");
                    advertContent.put("relationTypeId", defineType);
                    if (UtilValidate.isEmpty(advertContentId)) {
                        advertContentId = delegator.getNextSeqId("AdvertContent");
                        advertContent.put("advertContentId", advertContentId);
                        advertContent.create();
                    } else {
                        advertContent.store();
                    }
                }
                
            }
        } catch (GenericEntityException e) {
            e.printStackTrace();
        }
        return "success";
    }
    
    public static String createBall(HttpServletRequest request, HttpServletResponse response) {
        Delegator delegator = (Delegator) request.getAttribute("delegator");
        LocalDispatcher dispatcher = (LocalDispatcher) request.getAttribute("dispatcher");
        Map<String, Object> params = UtilHttp.getParameterMap(request);
        //获取首页模板Id
        String templateId = (String) params.get("templateId");
        String advertName = (String) params.get("advertName");
        String sequenceNum = (String) params.get("sequenceNum");
        Long sequence = (sequenceNum != null ? Long.parseLong(sequenceNum) : 0);
        String tplStyle = (String) params.get("tplStyle");
        //生成advert，生成advertRel，生成advertContent
        String advertId = delegator.getNextSeqId("Advert");
        GenericValue advert = delegator.makeValue("Advert");
        advert.put("advertId", advertId);
        advert.put("advertName", advertName);
        advert.put("advertTypeId", "ball");
        advert.put("dateCreated", UtilDateTime.nowTimestamp());
        advert.put("webSiteId", "daojia");
        GenericValue advertRel = delegator.makeValue("WebSiteAdvertRel", UtilMisc.toMap("advertId", advertId, "siteIndexTemplateId", templateId, "sequenceNum", sequence, "tplStyle", tplStyle));
        //轮播图的信息
        
        //预定义
        try {
            advert.create();
            advertRel.create();
            for (int i = 0; i < 6; i++) {
                String imgSrc = (String) params.get("upload" + (i + 1) + "_imgSrc");
                String seq = (String) params.get("upload" + (i + 1) + "_sequenceNum");
                String defineType = (String) params.get("upload" + (i + 1) + "_linkType");
                String relationId = (String) params.get("upload" + (i + 1) + "_val");
                String advertContentName = (String) params.get("upload" + (i + 1) + "_advertContentName");
                if (UtilValidate.isNotEmpty(imgSrc) && UtilValidate.isNotEmpty(seq) && UtilValidate.isNotEmpty(defineType) && UtilValidate.isNotEmpty(relationId) && UtilValidate.isNotEmpty(advertContentName)) {
                    GenericValue advertContent = delegator.makeValue("AdvertContent");
                    String advertContentId = delegator.getNextSeqId("AdvertContent");
                    advertContent.put("advertId", advertId);
                    advertContent.put("advertContentId", advertContentId);
                    advertContent.put("relationId", relationId);
                    advertContent.put("imgSrc", imgSrc);
                    advertContent.put("sequenceNum", Long.parseLong(seq));
                    advertContent.put("fromDate", UtilDateTime.nowTimestamp());
                    advertContent.put("advertContentName", advertContentName);
                    advertContent.put("defineType", "A");
                    advertContent.put("relationTypeId", defineType);
                    advertContent.create();
                }
            }
        } catch (GenericEntityException e) {
            e.printStackTrace();
        }
        return "success";
    }
    
    public static String updateBall(HttpServletRequest request, HttpServletResponse response) {
        Delegator delegator = (Delegator) request.getAttribute("delegator");
        LocalDispatcher dispatcher = (LocalDispatcher) request.getAttribute("dispatcher");
        Map<String, Object> params = UtilHttp.getParameterMap(request);
        //获取首页模板Id
        String templateId = (String) params.get("templateId");
        String advertName = (String) params.get("advertName");
        String sequenceNum = (String) params.get("sequenceNum");
        String advertId = (String) params.get("advertId");
        Long sequence = (sequenceNum != null ? Long.parseLong(sequenceNum) : 0);
        //生成advert，生成advertRel，生成advertContent
        String tplStyle = (String) params.get("tplStyle");
        
        GenericValue advert = delegator.makeValue("Advert");
        advert.put("advertId", advertId);
        advert.put("advertName", advertName);
        advert.put("advertTypeId", "ball");
        advert.put("dateCreated", UtilDateTime.nowTimestamp());
        advert.put("webSiteId", "daojia");
        
        GenericValue advertRel = delegator.makeValue("WebSiteAdvertRel", UtilMisc.toMap("advertId", advertId, "siteIndexTemplateId", templateId, "sequenceNum", sequence, "tplStyle", tplStyle));
        //轮播图的信息
        //预定义
        try {
            advert.store();
            advertRel.store();
            for (int i = 0; i < 6; i++) {
                String imgSrc = (String) params.get("upload" + (i + 1) + "_imgSrc");
                String seq = (String) params.get("upload" + (i + 1) + "_sequenceNum");
                String defineType = (String) params.get("upload" + (i + 1) + "_linkType");
                String relationId = (String) params.get("upload" + (i + 1) + "_val");
                String advertContentId = (String) params.get("upload" + (i + 1) + "_advertContentId");
                String advertContentName = (String) params.get("upload" + (i + 1) + "_advertContentName");
                if (UtilValidate.isNotEmpty(imgSrc) && UtilValidate.isNotEmpty(seq) && UtilValidate.isNotEmpty(defineType) && UtilValidate.isNotEmpty(relationId) && UtilValidate.isNotEmpty(advertContentName)) {
                    GenericValue advertContent = delegator.makeValue("AdvertContent");
                    
                    advertContent.put("advertId", advertId);
                    advertContent.put("advertContentId", advertContentId);
                    advertContent.put("relationId", relationId);
                    advertContent.put("imgSrc", imgSrc);
                    advertContent.put("sequenceNum", Long.parseLong(seq));
                    advertContent.put("fromDate", UtilDateTime.nowTimestamp());
                    advertContent.put("defineType", "A");
                    advertContent.put("relationTypeId", defineType);
                    advertContent.put("advertContentName", advertContentName);
                    if (UtilValidate.isEmpty(advertContentId)) {
                        advertContentId = delegator.getNextSeqId("AdvertContent");
                        advertContent.put("advertContentId", advertContentId);
                        advertContent.create();
                    } else {
                        advertContent.store();
                    }
                }
                
            }
        } catch (GenericEntityException e) {
            e.printStackTrace();
        }
        return "success";
    }
    
    public static String createFloorBanner(HttpServletRequest request, HttpServletResponse response) {
        Delegator delegator = (Delegator) request.getAttribute("delegator");
        LocalDispatcher dispatcher = (LocalDispatcher) request.getAttribute("dispatcher");
        Map<String, Object> params = UtilHttp.getParameterMap(request);
        //获取首页模板Id
        String templateId = (String) params.get("templateId");
        String advertName = (String) params.get("advertName");
        String sequenceNum = (String) params.get("sequenceNum");
        Long sequence = (sequenceNum != null ? Long.parseLong(sequenceNum) : 0);
        String tplStyle = (String) params.get("tplStyle");
        //生成advert，生成advertRel，生成advertContent
        String advertId = delegator.getNextSeqId("Advert");
        GenericValue advert = delegator.makeValue("Advert");
        advert.put("advertId", advertId);
        advert.put("advertName", advertName);
        advert.put("advertTypeId", "floorBanner");
        advert.put("dateCreated", UtilDateTime.nowTimestamp());
        advert.put("webSiteId", "daojia");
        GenericValue advertRel = delegator.makeValue("WebSiteAdvertRel", UtilMisc.toMap("advertId", advertId, "siteIndexTemplateId", templateId, "sequenceNum", sequence, "tplStyle", tplStyle));
        //轮播图的信息
        
        //预定义
        try {
            advert.create();
            advertRel.create();
            for (int i = 0; i < 6; i++) {
                String imgSrc = (String) params.get("upload" + (i + 1) + "_imgSrc");
                String seq = (String) params.get("upload" + (i + 1) + "_sequenceNum");
                String defineType = (String) params.get("upload" + (i + 1) + "_linkType");
                String relationId = (String) params.get("upload" + (i + 1) + "_val");
                if (UtilValidate.isNotEmpty(imgSrc) && UtilValidate.isNotEmpty(seq) && UtilValidate.isNotEmpty(defineType) && UtilValidate.isNotEmpty(relationId)) {
                    GenericValue advertContent = delegator.makeValue("AdvertContent");
                    String advertContentId = delegator.getNextSeqId("AdvertContent");
                    advertContent.put("advertId", advertId);
                    advertContent.put("advertContentId", advertContentId);
                    advertContent.put("relationId", relationId);
                    advertContent.put("imgSrc", imgSrc);
                    advertContent.put("sequenceNum", Long.parseLong(seq));
                    advertContent.put("fromDate", UtilDateTime.nowTimestamp());
                    advertContent.put("defineType", "A");
                    advertContent.put("relationTypeId", defineType);
                    advertContent.create();
                }
                
            }
        } catch (GenericEntityException e) {
            e.printStackTrace();
        }
        return "success";
    }
    
    public static String updateFloorBanner(HttpServletRequest request, HttpServletResponse response) {
        Delegator delegator = (Delegator) request.getAttribute("delegator");
        LocalDispatcher dispatcher = (LocalDispatcher) request.getAttribute("dispatcher");
        Map<String, Object> params = UtilHttp.getParameterMap(request);
        //获取首页模板Id
        String templateId = (String) params.get("templateId");
        String advertName = (String) params.get("advertName");
        String sequenceNum = (String) params.get("sequenceNum");
        String advertId = (String) params.get("advertId");
        Long sequence = (sequenceNum != null ? Long.parseLong(sequenceNum) : 0);
        //生成advert，生成advertRel，生成advertContent
        String tplStyle = (String) params.get("tplStyle");
        GenericValue advert = delegator.makeValue("Advert");
        advert.put("advertId", advertId);
        advert.put("advertName", advertName);
        advert.put("advertTypeId", "floorBanner");
        advert.put("dateCreated", UtilDateTime.nowTimestamp());
        advert.put("webSiteId", "daojia");
        
        GenericValue advertRel = delegator.makeValue("WebSiteAdvertRel", UtilMisc.toMap("advertId", advertId, "siteIndexTemplateId", templateId, "sequenceNum", sequence, "tplStyle", tplStyle));
        
        //轮播图的信息
        //预定义
        try {
            advertRel.store();
            advert.store();
//            advertRel.create();
            for (int i = 0; i < 6; i++) {
                String imgSrc = (String) params.get("upload" + (i + 1) + "_imgSrc");
                String seq = (String) params.get("upload" + (i + 1) + "_sequenceNum");
                String defineType = (String) params.get("upload" + (i + 1) + "_linkType");
                String relationId = (String) params.get("upload" + (i + 1) + "_val");
                String advertContentId = (String) params.get("upload" + (i + 1) + "_advertContentId");
                if (UtilValidate.isNotEmpty(imgSrc) && UtilValidate.isNotEmpty(seq) && UtilValidate.isNotEmpty(defineType) && UtilValidate.isNotEmpty(relationId)) {
                    GenericValue advertContent = delegator.makeValue("AdvertContent");
                    
                    advertContent.put("advertId", advertId);
                    advertContent.put("advertContentId", advertContentId);
                    advertContent.put("relationId", relationId);
                    advertContent.put("imgSrc", imgSrc);
                    advertContent.put("sequenceNum", Long.parseLong(seq));
                    advertContent.put("fromDate", UtilDateTime.nowTimestamp());
                    advertContent.put("defineType", "A");
                    advertContent.put("relationTypeId", defineType);
                    if (UtilValidate.isEmpty(advertContentId)) {
                        advertContentId = delegator.getNextSeqId("AdvertContent");
                        advertContent.put("advertContentId", advertContentId);
                        advertContent.create();
                    } else {
                        advertContent.store();
                    }
                }
                
            }
        } catch (GenericEntityException e) {
            e.printStackTrace();
        }
        return "success";
    }
    
    public static String createAct1(HttpServletRequest request, HttpServletResponse response) {
        Delegator delegator = (Delegator) request.getAttribute("delegator");
        LocalDispatcher dispatcher = (LocalDispatcher) request.getAttribute("dispatcher");
        Map<String, Object> params = UtilHttp.getParameterMap(request);
        //获取首页模板Id
        String templateId = (String) params.get("templateId");
        String advertName = (String) params.get("advertName");
        String sequenceNum = (String) params.get("sequenceNum");
        String tplStyle = (String) params.get("tplStyle");
        Long sequence = (sequenceNum != null ? Long.parseLong(sequenceNum) : 0);
        //生成advert，生成advertRel，生成advertContent
        String advertId = delegator.getNextSeqId("Advert");
        GenericValue advert = delegator.makeValue("Advert");
        advert.put("advertId", advertId);
        advert.put("advertName", advertName);
        advert.put("advertTypeId", "act1");
        advert.put("dateCreated", UtilDateTime.nowTimestamp());
        advert.put("webSiteId", "daojia");
        GenericValue advertRel = delegator.makeValue("WebSiteAdvertRel", UtilMisc.toMap("advertId", advertId, "siteIndexTemplateId", templateId, "sequenceNum", sequence, "tplStyle", tplStyle));
        //轮播图的信息
        
        //预定义
        try {
            advert.create();
            advertRel.create();
            for (int i = 0; i < 1; i++) {
                String imgSrc = (String) params.get("upload" + (i + 1) + "_imgSrc");
                String seq = (String) params.get("upload" + (i + 1) + "_sequenceNum");
                String defineType = (String) params.get("upload" + (i + 1) + "_linkType");
                String relationId = (String) params.get("upload" + (i + 1) + "_val");
                if (UtilValidate.isNotEmpty(imgSrc) && UtilValidate.isNotEmpty(seq) && UtilValidate.isNotEmpty(defineType) && UtilValidate.isNotEmpty(relationId)) {
                    GenericValue advertContent = delegator.makeValue("AdvertContent");
                    String advertContentId = delegator.getNextSeqId("AdvertContent");
                    advertContent.put("advertId", advertId);
                    advertContent.put("advertContentId", advertContentId);
                    advertContent.put("relationId", relationId);
                    advertContent.put("imgSrc", imgSrc);
                    advertContent.put("sequenceNum", Long.parseLong(seq));
                    advertContent.put("fromDate", UtilDateTime.nowTimestamp());
                    advertContent.put("defineType", "A");
                    advertContent.put("relationTypeId", defineType);
                    advertContent.create();
                }
                
            }
        } catch (GenericEntityException e) {
            e.printStackTrace();
        }
        return "success";
    }
    
    public static String updateAct1(HttpServletRequest request, HttpServletResponse response) {
        Delegator delegator = (Delegator) request.getAttribute("delegator");
        LocalDispatcher dispatcher = (LocalDispatcher) request.getAttribute("dispatcher");
        Map<String, Object> params = UtilHttp.getParameterMap(request);
        //获取首页模板Id
        String templateId = (String) params.get("templateId");
        String advertName = (String) params.get("advertName");
        String sequenceNum = (String) params.get("sequenceNum");
        String advertId = (String) params.get("advertId");
        Long sequence = (sequenceNum != null ? Long.parseLong(sequenceNum) : 0);
        String tplStyle = (String) params.get("tplStyle");
        //生成advert，生成advertRel，生成advertContent
        
        GenericValue advert = delegator.makeValue("Advert");
        advert.put("advertId", advertId);
        advert.put("advertName", advertName);
        advert.put("advertTypeId", "act1");
        advert.put("dateCreated", UtilDateTime.nowTimestamp());
        advert.put("webSiteId", "daojia");
        
        GenericValue advertRel = delegator.makeValue("WebSiteAdvertRel", UtilMisc.toMap("advertId", advertId, "siteIndexTemplateId", templateId, "sequenceNum", sequence, "tplStyle", tplStyle));
        //轮播图的信息
        //预定义
        try {
            advert.store();
            advertRel.store();
            for (int i = 0; i <1; i++) {
                String imgSrc = (String) params.get("upload" + (i + 1) + "_imgSrc");
                String seq = (String) params.get("upload" + (i + 1) + "_sequenceNum");
                String defineType = (String) params.get("upload" + (i + 1) + "_linkType");
                String relationId = (String) params.get("upload" + (i + 1) + "_val");
                String advertContentId = (String) params.get("upload" + (i + 1) + "_advertContentId");
                delegator.removeByAnd("AdvertContent", UtilMisc.toMap("advertId", advertId));
                if (UtilValidate.isNotEmpty(imgSrc) && UtilValidate.isNotEmpty(seq) && UtilValidate.isNotEmpty(defineType) && UtilValidate.isNotEmpty(relationId)) {
                    GenericValue advertContent = delegator.makeValue("AdvertContent");
                    
                    advertContent.put("advertId", advertId);
                    advertContent.put("advertContentId", advertContentId);
                    advertContent.put("relationId", relationId);
                    advertContent.put("imgSrc", imgSrc);
                    advertContent.put("sequenceNum", Long.parseLong(seq));
                    advertContent.put("fromDate", UtilDateTime.nowTimestamp());
                    advertContent.put("defineType", "A");
                    advertContent.put("relationTypeId", defineType);
                    advertContentId = delegator.getNextSeqId("AdvertContent");
                    advertContent.put("advertContentId", advertContentId);
                    advertContent.create();
                    
                }
                
            }
        } catch (GenericEntityException e) {
            e.printStackTrace();
        }
        return "success";
    }
    
    public static String createAct2(HttpServletRequest request, HttpServletResponse response) {
        Delegator delegator = (Delegator) request.getAttribute("delegator");
        LocalDispatcher dispatcher = (LocalDispatcher) request.getAttribute("dispatcher");
        Map<String, Object> params = UtilHttp.getParameterMap(request);
        //获取首页模板Id
        String templateId = (String) params.get("templateId");
        String advertName = (String) params.get("advertName");
        String sequenceNum = (String) params.get("sequenceNum");
        String tplStyle = (String) params.get("tplStyle");
        Long sequence = (sequenceNum != null ? Long.parseLong(sequenceNum) : 0);
        //生成advert，生成advertRel，生成advertContent
        String advertId = delegator.getNextSeqId("Advert");
        GenericValue advert = delegator.makeValue("Advert");
        advert.put("advertId", advertId);
        advert.put("advertName", advertName);
        advert.put("advertTypeId", "act2");
        advert.put("dateCreated", UtilDateTime.nowTimestamp());
        advert.put("webSiteId", "daojia");
        GenericValue advertRel = delegator.makeValue("WebSiteAdvertRel", UtilMisc.toMap("advertId", advertId, "siteIndexTemplateId", templateId, "sequenceNum", sequence, "tplStyle", tplStyle));
        //轮播图的信息
        
        //预定义
        try {
            advert.create();
            advertRel.create();
            for (int i = 0; i < 2; i++) {
                String relationId = (String) params.get("upload" + (i + 1) + "_val");
                String type = (String) params.get("upload" + (i + 1) + "_type");
                String defineType = (String) params.get("upload" + (i + 1) + "_linkType");
                String advertContentName = (String) params.get("upload" + (i + 1) + "_advertContentName");
                String description = (String) params.get("upload" + (i + 1) + "_description");
                if (type.equals("act2")) {
                    Object imgSrcObj = params.get("upload" + (i + 1) + "_1_imgSrc");
                    Object seqObj = params.get("upload" + (i + 1) + "_1_sequenceNum");
                    String imgSrc = "";
                    String seq = "";
                    if (imgSrcObj instanceof List) {
                        imgSrc = ((List<String>) imgSrcObj).get(0);
                    } else {
                        imgSrc = (String) imgSrcObj;
                    }
                    if (seqObj instanceof List) {
                        seq = ((List<String>) seqObj).get(0);
                    } else {
                        seq = (String) seqObj;
                    }
                    if (UtilValidate.isNotEmpty(imgSrc) && UtilValidate.isNotEmpty(seq) && UtilValidate.isNotEmpty(defineType) && UtilValidate.isNotEmpty(relationId) && UtilValidate.isNotEmpty(type)) {
                        GenericValue advertContent = delegator.makeValue("AdvertContent");
                        String advertContentId = delegator.getNextSeqId("AdvertContent");
                        advertContent.put("advertId", advertId);
                        advertContent.put("advertContentId", advertContentId);
                        advertContent.put("relationId", relationId);
                        advertContent.put("imgSrc", imgSrc);
                        advertContent.put("sequenceNum", Long.parseLong(seq));
                        advertContent.put("fromDate", UtilDateTime.nowTimestamp());
                        advertContent.put("defineType", "A");
                        advertContent.put("relationTypeId", defineType);
                        advertContent.put("groupNum", 1l);
                        advertContent.put("subGroupNum", 1l);
                        advertContent.put("advertContentName", advertContentName);
                        advertContent.put("description", description);
                        advertContent.put("advertContentTypeId", "act2");
                        advertContent.create();
                    }
                } else {
                    for (int j = 0; j < 3; j++) {
                        Object imgSrcObj = params.get("upload" + (i + 1) + "_" + (j + 1) + "_imgSrc");
                        Object seqObj = params.get("upload" + (i + 1) + "_" + (j + 1) + "_sequenceNum");
                        
                        String imgSrc = "";
                        String seq = "";
                        
                        if (imgSrcObj instanceof List) {
                            imgSrc = ((List<String>) imgSrcObj).get(1);
                        } else {
                            imgSrc = (String) imgSrcObj;
                        }
                        if (seqObj instanceof List) {
                            seq = ((List<String>) seqObj).get(1);
                        } else {
                            seq = (String) seqObj;
                        }
                        if (UtilValidate.isNotEmpty(imgSrc) && UtilValidate.isNotEmpty(seq) && UtilValidate.isNotEmpty(defineType) && UtilValidate.isNotEmpty(relationId) && UtilValidate.isNotEmpty(type)) {
                            GenericValue advertContent = delegator.makeValue("AdvertContent");
                            String advertContentId = delegator.getNextSeqId("AdvertContent");
                            advertContent.put("advertId", advertId);
                            advertContent.put("advertContentId", advertContentId);
                            advertContent.put("relationId", relationId);
                            advertContent.put("imgSrc", imgSrc);
                            advertContent.put("sequenceNum", Long.parseLong(seq));
                            advertContent.put("fromDate", UtilDateTime.nowTimestamp());
                            advertContent.put("defineType", "A");
                            advertContent.put("relationTypeId", defineType);
                            advertContent.put("groupNum", new Long(i + 1));
                            advertContent.put("subGroupNum", new Long(j + 1));
                            advertContent.put("advertContentTypeId", "miaosha");
                            advertContent.create();
                        }
                    }
                }
                
            }
        } catch (GenericEntityException e) {
            e.printStackTrace();
        }
        return "success";
    }
    
    public static String updateAct2(HttpServletRequest request, HttpServletResponse response) {
        Delegator delegator = (Delegator) request.getAttribute("delegator");
        LocalDispatcher dispatcher = (LocalDispatcher) request.getAttribute("dispatcher");
        Map<String, Object> params = UtilHttp.getParameterMap(request);
        //获取首页模板Id
        String templateId = (String) params.get("templateId");
        String advertName = (String) params.get("advertName");
        String sequenceNum = (String) params.get("sequenceNum");
        String tplStyle = (String) params.get("tplStyle");
        String advertId = (String) params.get("advertId");
        Long sequence = (sequenceNum != null ? Long.parseLong(sequenceNum) : 0);
        
        //生成advert，生成advertRel，生成advertContent
        GenericValue advert = delegator.makeValue("Advert");
        advert.put("advertId", advertId);
        advert.put("advertName", advertName);
        advert.put("advertTypeId", "act2");
        advert.put("dateCreated", UtilDateTime.nowTimestamp());
        advert.put("webSiteId", "daojia");
        GenericValue advertRel = delegator.makeValue("WebSiteAdvertRel", UtilMisc.toMap("advertId", advertId, "siteIndexTemplateId", templateId, "sequenceNum", sequence, "tplStyle", tplStyle));
        //轮播图的信息
        //预定义
        try {
            advert.store();
            advertRel.store();
            //删除对应的act 内容
            delegator.removeByAnd("AdvertContent", UtilMisc.toMap("advertId", advertId));
            for (int i = 0; i < 2; i++) {
                String relationId = (String) params.get("upload" + (i + 1) + "_val");
                String type = (String) params.get("upload" + (i + 1) + "_type");
                String defineType = (String) params.get("upload" + (i + 1) + "_linkType");
                String advertContentName = (String) params.get("upload" + (i + 1) + "_advertContentName");
                String description = (String) params.get("upload" + (i + 1) + "_description");
                if (type.equals("act2")) {
                    Object imgSrcObj = params.get("upload" + (i + 1) + "_1_imgSrc");
                    Object seqObj = params.get("upload" + (i + 1) + "_1_sequenceNum");
                    String imgSrc = "";
                    String seq = "";
                    if (imgSrcObj instanceof List) {
                        imgSrc = ((List<String>) imgSrcObj).get(0);
                    } else {
                        imgSrc = (String) imgSrcObj;
                    }
                    if (seqObj instanceof List) {
                        seq = ((List<String>) seqObj).get(0);
                    } else {
                        seq = (String) seqObj;
                    }
                    if (UtilValidate.isNotEmpty(imgSrc) && UtilValidate.isNotEmpty(seq) && UtilValidate.isNotEmpty(defineType) && UtilValidate.isNotEmpty(relationId) && UtilValidate.isNotEmpty(type)) {
                        GenericValue advertContent = delegator.makeValue("AdvertContent");
                        String advertContentId = delegator.getNextSeqId("AdvertContent");
                        advertContent.put("advertId", advertId);
                        advertContent.put("advertContentId", advertContentId);
                        advertContent.put("relationId", relationId);
                        advertContent.put("imgSrc", imgSrc);
                        advertContent.put("sequenceNum", Long.parseLong(seq));
                        advertContent.put("fromDate", UtilDateTime.nowTimestamp());
                        advertContent.put("defineType", "A");
                        advertContent.put("relationTypeId", defineType);
                        advertContent.put("groupNum", 1l);
                        advertContent.put("subGroupNum", 1l);
                        advertContent.put("advertContentName", advertContentName);
                        advertContent.put("description", description);
                        advertContent.put("advertContentTypeId", "act2");
                        advertContent.create();
                    }
                } else {
                    for (int j = 0; j < 3; j++) {
                        Object imgSrcObj = params.get("upload" + (i + 1) + "_" + (j + 1) + "_imgSrc");
                        Object seqObj = params.get("upload" + (i + 1) + "_" + (j + 1) + "_sequenceNum");
                        
                        String imgSrc = "";
                        String seq = "";
                        
                        if (imgSrcObj instanceof List) {
                            imgSrc = ((List<String>) imgSrcObj).get(1);
                        } else {
                            imgSrc = (String) imgSrcObj;
                        }
                        if (seqObj instanceof List) {
                            seq = ((List<String>) seqObj).get(1);
                        } else {
                            seq = (String) seqObj;
                        }
                        if (UtilValidate.isNotEmpty(seq) && UtilValidate.isNotEmpty(defineType) && UtilValidate.isNotEmpty(relationId) && UtilValidate.isNotEmpty(type)) {
                            GenericValue advertContent = delegator.makeValue("AdvertContent");
                            String advertContentId = delegator.getNextSeqId("AdvertContent");
                            advertContent.put("advertId", advertId);
                            advertContent.put("advertContentId", advertContentId);
                            advertContent.put("relationId", relationId);
                            advertContent.put("imgSrc", imgSrc);
                            advertContent.put("sequenceNum", Long.parseLong(seq));
                            advertContent.put("fromDate", UtilDateTime.nowTimestamp());
                            advertContent.put("defineType", "A");
                            advertContent.put("relationTypeId", defineType);
                            advertContent.put("groupNum", new Long(i + 1));
                            advertContent.put("subGroupNum", new Long(j + 1));
                            advertContent.put("advertContentTypeId", "miaosha");
                            advertContent.create();
                        }
                    }
                }
                
            }
        } catch (GenericEntityException e) {
            e.printStackTrace();
        }
        return "success";
    }
    
    
    public static String createAct3(HttpServletRequest request, HttpServletResponse response) {
        Delegator delegator = (Delegator) request.getAttribute("delegator");
        LocalDispatcher dispatcher = (LocalDispatcher) request.getAttribute("dispatcher");
        Map<String, Object> params = UtilHttp.getParameterMap(request);
        //获取首页模板Id
        String templateId = (String) params.get("templateId");
        String advertName = (String) params.get("advertName");
        String sequenceNum = (String) params.get("sequenceNum");
        String tplStyle = (String) params.get("tplStyle");
        Long sequence = (sequenceNum != null ? Long.parseLong(sequenceNum) : 0);
        //生成advert，生成advertRel，生成advertContent
        String advertId = delegator.getNextSeqId("Advert");
        GenericValue advert = delegator.makeValue("Advert");
        advert.put("advertId", advertId);
        advert.put("advertName", advertName);
        advert.put("advertTypeId", "act3");
        advert.put("dateCreated", UtilDateTime.nowTimestamp());
        advert.put("webSiteId", "daojia");
        GenericValue advertRel = delegator.makeValue("WebSiteAdvertRel", UtilMisc.toMap("advertId", advertId, "siteIndexTemplateId", templateId, "sequenceNum", sequence, "tplStyle", tplStyle));
        //轮播图的信息
        
        //预定义
        try {
            advert.create();
            advertRel.create();
            for (int i = 0; i < 3; i++) {
                String relationId = (String) params.get("upload" + (i + 1) + "_val");
                String type = (String) params.get("upload" + (i + 1) + "_type");
                String defineType = (String) params.get("upload" + (i + 1) + "_linkType");
                String advertContentName = (String) params.get("upload" + (i + 1) + "_advertContentName");
                String description = (String) params.get("upload" + (i + 1) + "_description");
                if (type.equals("act3")) {
                    Object imgSrcObj = params.get("upload" + (i + 1) + "_1_imgSrc");
                    Object seqObj = params.get("upload" + (i + 1) + "_1_sequenceNum");
                    String imgSrc = "";
                    String seq = "";
                    if (imgSrcObj instanceof List) {
                        imgSrc = ((List<String>) imgSrcObj).get(0);
                    } else {
                        imgSrc = (String) imgSrcObj;
                    }
                    if (seqObj instanceof List) {
                        seq = ((List<String>) seqObj).get(0);
                    } else {
                        seq = (String) seqObj;
                    }
                    if (UtilValidate.isNotEmpty(imgSrc) && UtilValidate.isNotEmpty(seq) && UtilValidate.isNotEmpty(defineType) && UtilValidate.isNotEmpty(relationId) && UtilValidate.isNotEmpty(type)) {
                        GenericValue advertContent = delegator.makeValue("AdvertContent");
                        String advertContentId = delegator.getNextSeqId("AdvertContent");
                        advertContent.put("advertId", advertId);
                        advertContent.put("advertContentId", advertContentId);
                        advertContent.put("relationId", relationId);
                        advertContent.put("imgSrc", imgSrc);
                        advertContent.put("sequenceNum", Long.parseLong(seq));
                        advertContent.put("fromDate", UtilDateTime.nowTimestamp());
                        advertContent.put("defineType", "A");
                        advertContent.put("relationTypeId", defineType);
                        advertContent.put("groupNum", 1l);
                        advertContent.put("subGroupNum", 1l);
                        advertContent.put("advertContentName", advertContentName);
                        advertContent.put("description", description);
                        advertContent.put("advertContentTypeId", "act3");
                        
                        advertContent.create();
                    }
                } else {
                    for (int j = 0; j < 3; j++) {
                        Object imgSrcObj = params.get("upload" + (i + 1) + "_" + (j + 1) + "_imgSrc");
                        Object seqObj = params.get("upload" + (i + 1) + "_" + (j + 1) + "_sequenceNum");
                        
                        String imgSrc = "";
                        String seq = "";
                        
                        if (imgSrcObj instanceof List) {
                            imgSrc = ((List<String>) imgSrcObj).get(1);
                        } else {
                            imgSrc = (String) imgSrcObj;
                        }
                        if (seqObj instanceof List) {
                            seq = ((List<String>) seqObj).get(1);
                        } else {
                            seq = (String) seqObj;
                        }
                        if (UtilValidate.isNotEmpty(imgSrc) && UtilValidate.isNotEmpty(seq) && UtilValidate.isNotEmpty(defineType) && UtilValidate.isNotEmpty(relationId) && UtilValidate.isNotEmpty(type)) {
                            GenericValue advertContent = delegator.makeValue("AdvertContent");
                            String advertContentId = delegator.getNextSeqId("AdvertContent");
                            advertContent.put("advertId", advertId);
                            advertContent.put("advertContentId", advertContentId);
                            advertContent.put("relationId", relationId);
                            advertContent.put("imgSrc", imgSrc);
                            advertContent.put("sequenceNum", Long.parseLong(seq));
                            advertContent.put("fromDate", UtilDateTime.nowTimestamp());
                            advertContent.put("defineType", "A");
                            advertContent.put("relationTypeId", defineType);
                            advertContent.put("groupNum", new Long(i + 1));
                            advertContent.put("subGroupNum", new Long(j + 1));
                            advertContent.put("advertContentName", advertContentName);
                            advertContent.put("advertContentTypeId", "miaosha");
                            advertContent.put("description", description);
                            advertContent.create();
                        }
                    }
                }
                
            }
        } catch (GenericEntityException e) {
            e.printStackTrace();
        }
        return "success";
    }
    
    public static String updateAct3(HttpServletRequest request, HttpServletResponse response) {
        Delegator delegator = (Delegator) request.getAttribute("delegator");
        LocalDispatcher dispatcher = (LocalDispatcher) request.getAttribute("dispatcher");
        Map<String, Object> params = UtilHttp.getParameterMap(request);
        //获取首页模板Id
        String templateId = (String) params.get("templateId");
        String advertName = (String) params.get("advertName");
        String sequenceNum = (String) params.get("sequenceNum");
        String tplStyle = (String) params.get("tplStyle");
        String advertId = (String) params.get("advertId");
        Long sequence = (sequenceNum != null ? Long.parseLong(sequenceNum) : 0);
        //生成advert，生成advertRel，生成advertContent
        GenericValue advert = delegator.makeValue("Advert");
        advert.put("advertId", advertId);
        advert.put("advertName", advertName);
        advert.put("advertTypeId", "act3");
        advert.put("dateCreated", UtilDateTime.nowTimestamp());
        advert.put("webSiteId", "daojia");
        GenericValue advertRel = delegator.makeValue("WebSiteAdvertRel", UtilMisc.toMap("advertId", advertId, "siteIndexTemplateId", templateId, "sequenceNum", sequence, "tplStyle", tplStyle));
        //轮播图的信息
        //预定义
        try {
            advert.store();
            advertRel.store();
            //删除对应的act 内容
            delegator.removeByAnd("AdvertContent", UtilMisc.toMap("advertId", advertId));
            for (int i = 0; i < 3; i++) {
                String relationId = (String) params.get("upload" + (i + 1) + "_val");
                String type = (String) params.get("upload" + (i + 1) + "_type");
                String defineType = (String) params.get("upload" + (i + 1) + "_linkType");
                if (type.equals("act3")) {
                    Object imgSrcObj = params.get("upload" + (i + 1) + "_1_imgSrc");
                    Object seqObj = params.get("upload" + (i + 1) + "_1_sequenceNum");
                    String advertContentName = (String) params.get("upload" + (i + 1) + "_advertContentName");
                    String description = (String) params.get("upload" + (i + 1) + "_description");
                    String imgSrc = "";
                    String seq = "";
                    if (imgSrcObj instanceof List) {
                        imgSrc = ((List<String>) imgSrcObj).get(0);
                    } else {
                        imgSrc = (String) imgSrcObj;
                    }
                    if (seqObj instanceof List) {
                        seq = ((List<String>) seqObj).get(0);
                    } else {
                        seq = (String) seqObj;
                    }
                    if (UtilValidate.isNotEmpty(imgSrc) && UtilValidate.isNotEmpty(seq) && UtilValidate.isNotEmpty(defineType) && UtilValidate.isNotEmpty(relationId) && UtilValidate.isNotEmpty(type)) {
                        GenericValue advertContent = delegator.makeValue("AdvertContent");
                        String advertContentId = delegator.getNextSeqId("AdvertContent");
                        advertContent.put("advertId", advertId);
                        advertContent.put("advertContentId", advertContentId);
                        advertContent.put("relationId", relationId);
                        advertContent.put("imgSrc", imgSrc);
                        advertContent.put("sequenceNum", Long.parseLong(seq));
                        advertContent.put("fromDate", UtilDateTime.nowTimestamp());
                        advertContent.put("defineType", "A");
                        advertContent.put("relationTypeId", defineType);
                        advertContent.put("groupNum", 1l);
                        advertContent.put("subGroupNum", 1l);
                        advertContent.put("advertContentName", advertContentName);
                        advertContent.put("description", description);
                        advertContent.put("advertContentTypeId", "act3");
                        advertContent.create();
                    }
                } else {
                    for (int j = 0; j < 3; j++) {
                        Object imgSrcObj = params.get("upload" + (i + 1) + "_" + (j + 1) + "_imgSrc");
                        Object seqObj = params.get("upload" + (i + 1) + "_" + (j + 1) + "_sequenceNum");
                        
                        String imgSrc = "";
                        String seq = "";
                        
                        if (imgSrcObj instanceof List) {
                            imgSrc = ((List<String>) imgSrcObj).get(1);
                        } else {
                            imgSrc = (String) imgSrcObj;
                        }
                        if (seqObj instanceof List) {
                            seq = ((List<String>) seqObj).get(1);
                        } else {
                            seq = (String) seqObj;
                        }
                        if (UtilValidate.isNotEmpty(seq) && UtilValidate.isNotEmpty(defineType) && UtilValidate.isNotEmpty(relationId) && UtilValidate.isNotEmpty(type)) {
                            GenericValue advertContent = delegator.makeValue("AdvertContent");
                            String advertContentId = delegator.getNextSeqId("AdvertContent");
                            advertContent.put("advertId", advertId);
                            advertContent.put("advertContentId", advertContentId);
                            advertContent.put("relationId", relationId);
                            advertContent.put("imgSrc", imgSrc);
                            advertContent.put("sequenceNum", Long.parseLong(seq));
                            advertContent.put("fromDate", UtilDateTime.nowTimestamp());
                            advertContent.put("defineType", "A");
                            advertContent.put("relationTypeId", defineType);
                            advertContent.put("groupNum", new Long(i + 1));
                            advertContent.put("subGroupNum", new Long(j + 1));
                            advertContent.put("advertContentTypeId", "miaosha");
                            advertContent.create();
                        }
                    }
                }
                
            }
        } catch (GenericEntityException e) {
            e.printStackTrace();
        }
        return "success";
    }
    
    //    channel begin
    public static String createChannelBanner(HttpServletRequest request, HttpServletResponse response) {
        Delegator delegator = (Delegator) request.getAttribute("delegator");
        LocalDispatcher dispatcher = (LocalDispatcher) request.getAttribute("dispatcher");
        Map<String, Object> params = UtilHttp.getParameterMap(request);
        //获取首页模板Id
        String templateId = (String) params.get("templateId");
        String advertName = (String) params.get("advertName");
        String sequenceNum = (String) params.get("sequenceNum");
        Long sequence = (sequenceNum != null ? Long.parseLong(sequenceNum) : 0);
        String tplStyle = (String) params.get("tplStyle");
        //生成advert，生成advertRel，生成advertContent
        String advertId = delegator.getNextSeqId("Advert");
        GenericValue advert = delegator.makeValue("Advert");
        advert.put("advertId", advertId);
        advert.put("advertName", advertName);
        advert.put("advertTypeId", "banner");
        advert.put("dateCreated", UtilDateTime.nowTimestamp());
        advert.put("webSiteId", "daojia");
        GenericValue advertRel = delegator.makeValue("SpecialPageAdvertRel", UtilMisc.toMap("advertId", advertId, "specialPageId", templateId, "sequenceNum", sequence, "tplStyle", tplStyle));
        //轮播图的信息
        
        //预定义
        try {
            advert.create();
            advertRel.create();
            for (int i = 0; i < 6; i++) {
                String imgSrc = (String) params.get("upload" + (i + 1) + "_imgSrc");
                String seq = (String) params.get("upload" + (i + 1) + "_sequenceNum");
                String defineType = (String) params.get("upload" + (i + 1) + "_linkType");
                String relationId = (String) params.get("upload" + (i + 1) + "_val");
                if (UtilValidate.isNotEmpty(imgSrc) && UtilValidate.isNotEmpty(seq) && UtilValidate.isNotEmpty(defineType) && UtilValidate.isNotEmpty(relationId)) {
                    GenericValue advertContent = delegator.makeValue("AdvertContent");
                    String advertContentId = delegator.getNextSeqId("AdvertContent");
                    advertContent.put("advertId", advertId);
                    advertContent.put("advertContentId", advertContentId);
                    advertContent.put("relationId", relationId);
                    advertContent.put("imgSrc", imgSrc);
                    advertContent.put("sequenceNum", Long.parseLong(seq));
                    advertContent.put("fromDate", UtilDateTime.nowTimestamp());
                    advertContent.put("defineType", "C");
                    advertContent.put("relationTypeId", defineType);
                    advertContent.create();
                }
                
            }
        } catch (GenericEntityException e) {
            e.printStackTrace();
        }
        return "success";
    }
    
    public static String updateChannelBanner(HttpServletRequest request, HttpServletResponse response) {
        Delegator delegator = (Delegator) request.getAttribute("delegator");
        LocalDispatcher dispatcher = (LocalDispatcher) request.getAttribute("dispatcher");
        Map<String, Object> params = UtilHttp.getParameterMap(request);
        //获取首页模板Id
        String templateId = (String) params.get("templateId");
        String advertName = (String) params.get("advertName");
        String sequenceNum = (String) params.get("sequenceNum");
        String advertId = (String) params.get("advertId");
        Long sequence = (sequenceNum != null ? Long.parseLong(sequenceNum) : 0);
        //生成advert，生成advertRel，生成advertContent
        String tplStyle = (String) params.get("tplStyle");
        GenericValue advert = delegator.makeValue("Advert");
        advert.put("advertId", advertId);
        advert.put("advertName", advertName);
        advert.put("advertTypeId", "banner");
        advert.put("dateCreated", UtilDateTime.nowTimestamp());
        advert.put("webSiteId", "daojia");
        
        GenericValue advertRel = delegator.makeValue("SpecialPageAdvertRel", UtilMisc.toMap("advertId", advertId, "specialPageId", templateId, "sequenceNum", sequence, "tplStyle", tplStyle));
        //轮播图的信息
        //预定义
        try {
            advert.store();
            advertRel.store();
            for (int i = 0; i < 6; i++) {
                String imgSrc = (String) params.get("upload" + (i + 1) + "_imgSrc");
                String seq = (String) params.get("upload" + (i + 1) + "_sequenceNum");
                String defineType = (String) params.get("upload" + (i + 1) + "_linkType");
                String relationId = (String) params.get("upload" + (i + 1) + "_val");
                String advertContentId = (String) params.get("upload" + (i + 1) + "_advertContentId");
                if (UtilValidate.isNotEmpty(imgSrc) && UtilValidate.isNotEmpty(seq) && UtilValidate.isNotEmpty(defineType) && UtilValidate.isNotEmpty(relationId)) {
                    GenericValue advertContent = delegator.makeValue("AdvertContent");
                    
                    advertContent.put("advertId", advertId);
                    advertContent.put("advertContentId", advertContentId);
                    advertContent.put("relationId", relationId);
                    advertContent.put("imgSrc", imgSrc);
                    advertContent.put("sequenceNum", Long.parseLong(seq));
                    advertContent.put("fromDate", UtilDateTime.nowTimestamp());
                    advertContent.put("defineType", "C");
                    advertContent.put("relationTypeId", defineType);
                    if (UtilValidate.isEmpty(advertContentId)) {
                        advertContentId = delegator.getNextSeqId("AdvertContent");
                        advertContent.put("advertContentId", advertContentId);
                        advertContent.create();
                    } else {
                        advertContent.store();
                    }
                }
                
            }
        } catch (GenericEntityException e) {
            e.printStackTrace();
        }
        return "success";
    }
    
    public static String createChannelFloorBanner(HttpServletRequest request, HttpServletResponse response) {
        Delegator delegator = (Delegator) request.getAttribute("delegator");
        LocalDispatcher dispatcher = (LocalDispatcher) request.getAttribute("dispatcher");
        Map<String, Object> params = UtilHttp.getParameterMap(request);
        //获取首页模板Id
        String templateId = (String) params.get("templateId");
        String advertName = (String) params.get("advertName");
        String sequenceNum = (String) params.get("sequenceNum");
        Long sequence = (sequenceNum != null ? Long.parseLong(sequenceNum) : 0);
        String tplStyle = (String) params.get("tplStyle");
        //生成advert，生成advertRel，生成advertContent
        String advertId = delegator.getNextSeqId("Advert");
        GenericValue advert = delegator.makeValue("Advert");
        advert.put("advertId", advertId);
        advert.put("advertName", advertName);
        advert.put("advertTypeId", "floorBanner");
        advert.put("dateCreated", UtilDateTime.nowTimestamp());
        advert.put("webSiteId", "daojia");
        GenericValue advertRel = delegator.makeValue("SpecialPageAdvertRel", UtilMisc.toMap("advertId", advertId, "specialPageId", templateId, "sequenceNum", sequence, "tplStyle", tplStyle));
        //轮播图的信息
        
        //预定义
        try {
            advert.create();
            advertRel.create();
            for (int i = 0; i < 6; i++) {
                String imgSrc = (String) params.get("upload" + (i + 1) + "_imgSrc");
                String seq = (String) params.get("upload" + (i + 1) + "_sequenceNum");
                String defineType = (String) params.get("upload" + (i + 1) + "_linkType");
                String relationId = (String) params.get("upload" + (i + 1) + "_val");
                if (UtilValidate.isNotEmpty(imgSrc) && UtilValidate.isNotEmpty(seq) && UtilValidate.isNotEmpty(defineType) && UtilValidate.isNotEmpty(relationId)) {
                    GenericValue advertContent = delegator.makeValue("AdvertContent");
                    String advertContentId = delegator.getNextSeqId("AdvertContent");
                    advertContent.put("advertId", advertId);
                    advertContent.put("advertContentId", advertContentId);
                    advertContent.put("relationId", relationId);
                    advertContent.put("imgSrc", imgSrc);
                    advertContent.put("sequenceNum", Long.parseLong(seq));
                    advertContent.put("fromDate", UtilDateTime.nowTimestamp());
                    advertContent.put("defineType", "C");
                    advertContent.put("relationTypeId", defineType);
                    advertContent.create();
                }
                
            }
        } catch (GenericEntityException e) {
            e.printStackTrace();
        }
        return "success";
    }
    
    public static String updateChannelFloorBanner(HttpServletRequest request, HttpServletResponse response) {
        Delegator delegator = (Delegator) request.getAttribute("delegator");
        LocalDispatcher dispatcher = (LocalDispatcher) request.getAttribute("dispatcher");
        Map<String, Object> params = UtilHttp.getParameterMap(request);
        //获取首页模板Id
        String templateId = (String) params.get("templateId");
        String advertName = (String) params.get("advertName");
        String sequenceNum = (String) params.get("sequenceNum");
        String advertId = (String) params.get("advertId");
        Long sequence = (sequenceNum != null ? Long.parseLong(sequenceNum) : 0);
        //生成advert，生成advertRel，生成advertContent
        String tplStyle = (String) params.get("tplStyle");
        GenericValue advert = delegator.makeValue("Advert");
        advert.put("advertId", advertId);
        advert.put("advertName", advertName);
        advert.put("advertTypeId", "floorBanner");
        advert.put("dateCreated", UtilDateTime.nowTimestamp());
        advert.put("webSiteId", "daojia");
        
        GenericValue advertRel = delegator.makeValue("SpecialPageAdvertRel", UtilMisc.toMap("advertId", advertId, "specialPageId", templateId, "sequenceNum", sequence, "tplStyle", tplStyle));
        
        //轮播图的信息
        //预定义
        try {
            advertRel.store();
            advert.store();
//            advertRel.create();
            for (int i = 0; i < 6; i++) {
                String imgSrc = (String) params.get("upload" + (i + 1) + "_imgSrc");
                String seq = (String) params.get("upload" + (i + 1) + "_sequenceNum");
                String defineType = (String) params.get("upload" + (i + 1) + "_linkType");
                String relationId = (String) params.get("upload" + (i + 1) + "_val");
                String advertContentId = (String) params.get("upload" + (i + 1) + "_advertContentId");
                if (UtilValidate.isNotEmpty(imgSrc) && UtilValidate.isNotEmpty(seq) && UtilValidate.isNotEmpty(defineType) && UtilValidate.isNotEmpty(relationId)) {
                    GenericValue advertContent = delegator.makeValue("AdvertContent");
                    
                    advertContent.put("advertId", advertId);
                    advertContent.put("advertContentId", advertContentId);
                    advertContent.put("relationId", relationId);
                    advertContent.put("imgSrc", imgSrc);
                    advertContent.put("sequenceNum", Long.parseLong(seq));
                    advertContent.put("fromDate", UtilDateTime.nowTimestamp());
                    advertContent.put("defineType", "C");
                    advertContent.put("relationTypeId", defineType);
                    if (UtilValidate.isEmpty(advertContentId)) {
                        advertContentId = delegator.getNextSeqId("AdvertContent");
                        advertContent.put("advertContentId", advertContentId);
                        advertContent.create();
                    } else {
                        advertContent.store();
                    }
                }
                
            }
        } catch (GenericEntityException e) {
            e.printStackTrace();
        }
        return "success";
    }
    
    
    public static String createChannelAct(HttpServletRequest request, HttpServletResponse response) {
        Delegator delegator = (Delegator) request.getAttribute("delegator");
        LocalDispatcher dispatcher = (LocalDispatcher) request.getAttribute("dispatcher");
        Map<String, Object> params = UtilHttp.getParameterMap(request);
        //获取首页模板Id
        String templateId = (String) params.get("templateId");
        String advertName = (String) params.get("advertName");
        String sequenceNum = (String) params.get("sequenceNum");
        String tplStyle = (String) params.get("tplStyle");
        Long sequence = (sequenceNum != null ? Long.parseLong(sequenceNum) : 0);
        //生成advert，生成advertRel，生成advertContent
        String advertId = delegator.getNextSeqId("Advert");
        GenericValue advert = delegator.makeValue("Advert");
        advert.put("advertId", advertId);
        advert.put("advertName", advertName);
        advert.put("advertTypeId", "act");
        advert.put("dateCreated", UtilDateTime.nowTimestamp());
        advert.put("webSiteId", "daojia");
        GenericValue advertRel = delegator.makeValue("SpecialPageAdvertRel", UtilMisc.toMap("advertId", advertId, "specialPageId", templateId, "sequenceNum", sequence, "tplStyle", tplStyle));
        //轮播图的信息
        
        //预定义
        try {
            advert.create();
            advertRel.create();
            for (int i = 0; i < 12; i++) {
                String imgSrc = (String) params.get("upload" + (i + 1) + "_imgSrc");
                String seq = (String) params.get("upload" + (i + 1) + "_sequenceNum");
                String defineType = (String) params.get("upload" + (i + 1) + "_linkType");
                String relationId = (String) params.get("upload" + (i + 1) + "_val");
                String advertContentName = (String) params.get("upload" + (i + 1) + "_advertContentName");
                String description = (String) params.get("upload" + (i + 1) + "_description");
                if (UtilValidate.isNotEmpty(imgSrc) && UtilValidate.isNotEmpty(seq) && UtilValidate.isNotEmpty(defineType) && UtilValidate.isNotEmpty(relationId)) {
                    GenericValue advertContent = delegator.makeValue("AdvertContent");
                    String advertContentId = delegator.getNextSeqId("AdvertContent");
                    advertContent.put("advertId", advertId);
                    advertContent.put("advertContentId", advertContentId);
                    advertContent.put("relationId", relationId);
                    advertContent.put("imgSrc", imgSrc);
                    advertContent.put("sequenceNum", Long.parseLong(seq));
                    advertContent.put("fromDate", UtilDateTime.nowTimestamp());
                    advertContent.put("defineType", "C");
                    advertContent.put("relationTypeId", defineType);
                    advertContent.put("advertContentName", advertContentName);
                    advertContent.put("description", description);
                    advertContent.create();
                }
                
            }
        } catch (GenericEntityException e) {
            e.printStackTrace();
        }
        return "success";
    }
    
    public static String updateChannelAct(HttpServletRequest request, HttpServletResponse response) {
        Delegator delegator = (Delegator) request.getAttribute("delegator");
        LocalDispatcher dispatcher = (LocalDispatcher) request.getAttribute("dispatcher");
        Map<String, Object> params = UtilHttp.getParameterMap(request);
        //获取首页模板Id
        String templateId = (String) params.get("templateId");
        String advertName = (String) params.get("advertName");
        String sequenceNum = (String) params.get("sequenceNum");
        String advertId = (String) params.get("advertId");
        Long sequence = (sequenceNum != null ? Long.parseLong(sequenceNum) : 0);
        String tplStyle = (String) params.get("tplStyle");
        //生成advert，生成advertRel，生成advertContent
        
        GenericValue advert = delegator.makeValue("Advert");
        advert.put("advertId", advertId);
        advert.put("advertName", advertName);
        advert.put("advertTypeId", "act");
        advert.put("dateCreated", UtilDateTime.nowTimestamp());
        advert.put("webSiteId", "daojia");
        
        GenericValue advertRel = delegator.makeValue("SpecialPageAdvertRel", UtilMisc.toMap("advertId", advertId, "specialPageId", templateId, "sequenceNum", sequence, "tplStyle", tplStyle));
        //轮播图的信息
        //预定义
        try {
            advert.store();
            advertRel.store();
            //删除对应的act 内容
            delegator.removeByAnd("AdvertContent", UtilMisc.toMap("advertId", advertId));
            for (int i = 0; i < 12; i++) {
                String imgSrc = (String) params.get("upload" + (i + 1) + "_imgSrc");
                String seq = (String) params.get("upload" + (i + 1) + "_sequenceNum");
                String defineType = (String) params.get("upload" + (i + 1) + "_linkType");
                String relationId = (String) params.get("upload" + (i + 1) + "_val");
                String advertContentId = (String) params.get("upload" + (i + 1) + "_advertContentId");
                String advertContentName = (String) params.get("upload" + (i + 1) + "_advertContentName");
                String description = (String) params.get("upload" + (i + 1) + "_description");
                
                if (UtilValidate.isNotEmpty(imgSrc) && UtilValidate.isNotEmpty(seq) && UtilValidate.isNotEmpty(defineType) && UtilValidate.isNotEmpty(relationId)) {
                    GenericValue advertContent = delegator.makeValue("AdvertContent");
                    
                    advertContent.put("advertId", advertId);
                    advertContent.put("advertContentId", advertContentId);
                    advertContent.put("relationId", relationId);
                    advertContent.put("imgSrc", imgSrc);
                    advertContent.put("sequenceNum", Long.parseLong(seq));
                    advertContent.put("fromDate", UtilDateTime.nowTimestamp());
                    advertContent.put("defineType", "C");
                    advertContent.put("relationTypeId", defineType);
                    advertContent.put("advertContentName", advertContentName);
                    advertContent.put("description", description);
                    
                    advertContentId = delegator.getNextSeqId("AdvertContent");
                    advertContent.put("advertContentId", advertContentId);
                    advertContent.create();
                    
                }
                
            }
        } catch (GenericEntityException e) {
            e.printStackTrace();
        }
        return "success";
    }
    
    
    //    activity begin
    public static String createActivityBanner(HttpServletRequest request, HttpServletResponse response) {
        Delegator delegator = (Delegator) request.getAttribute("delegator");
        LocalDispatcher dispatcher = (LocalDispatcher) request.getAttribute("dispatcher");
        Map<String, Object> params = UtilHttp.getParameterMap(request);
        //获取首页模板Id
        String templateId = (String) params.get("templateId");
        String advertName = (String) params.get("advertName");
        String sequenceNum = (String) params.get("sequenceNum");
        Long sequence = (sequenceNum != null ? Long.parseLong(sequenceNum) : 0);
        String tplStyle = (String) params.get("tplStyle");
        //生成advert，生成advertRel，生成advertContent
        String advertId = delegator.getNextSeqId("Advert");
        GenericValue advert = delegator.makeValue("Advert");
        advert.put("advertId", advertId);
        advert.put("advertName", advertName);
        advert.put("advertTypeId", "banner");
        advert.put("dateCreated", UtilDateTime.nowTimestamp());
        advert.put("webSiteId", "daojia");
        GenericValue advertRel = delegator.makeValue("SpecialPageAdvertRel", UtilMisc.toMap("advertId", advertId, "specialPageId", templateId, "sequenceNum", sequence, "tplStyle", tplStyle));
        //轮播图的信息
        
        //预定义
        try {
            advert.create();
            advertRel.create();
            for (int i = 0; i < 6; i++) {
                String imgSrc = (String) params.get("upload" + (i + 1) + "_imgSrc");
                String seq = (String) params.get("upload" + (i + 1) + "_sequenceNum");
                String defineType = (String) params.get("upload" + (i + 1) + "_linkType");
                String relationId = (String) params.get("upload" + (i + 1) + "_val");
                if (UtilValidate.isNotEmpty(imgSrc) && UtilValidate.isNotEmpty(seq) && UtilValidate.isNotEmpty(defineType) && UtilValidate.isNotEmpty(relationId)) {
                    GenericValue advertContent = delegator.makeValue("AdvertContent");
                    String advertContentId = delegator.getNextSeqId("AdvertContent");
                    advertContent.put("advertId", advertId);
                    advertContent.put("advertContentId", advertContentId);
                    advertContent.put("relationId", relationId);
                    advertContent.put("imgSrc", imgSrc);
                    advertContent.put("sequenceNum", Long.parseLong(seq));
                    advertContent.put("fromDate", UtilDateTime.nowTimestamp());
                    advertContent.put("defineType", "B");
                    advertContent.put("relationTypeId", defineType);
                    advertContent.create();
                }
                
            }
        } catch (GenericEntityException e) {
            e.printStackTrace();
        }
        return "success";
    }
    
    public static String updateActivityBanner(HttpServletRequest request, HttpServletResponse response) {
        Delegator delegator = (Delegator) request.getAttribute("delegator");
        LocalDispatcher dispatcher = (LocalDispatcher) request.getAttribute("dispatcher");
        Map<String, Object> params = UtilHttp.getParameterMap(request);
        //获取首页模板Id
        String templateId = (String) params.get("templateId");
        String advertName = (String) params.get("advertName");
        String sequenceNum = (String) params.get("sequenceNum");
        String advertId = (String) params.get("advertId");
        Long sequence = (sequenceNum != null ? Long.parseLong(sequenceNum) : 0);
        //生成advert，生成advertRel，生成advertContent
        String tplStyle = (String) params.get("tplStyle");
        GenericValue advert = delegator.makeValue("Advert");
        advert.put("advertId", advertId);
        advert.put("advertName", advertName);
        advert.put("advertTypeId", "banner");
        advert.put("dateCreated", UtilDateTime.nowTimestamp());
        advert.put("webSiteId", "daojia");
        
        GenericValue advertRel = delegator.makeValue("SpecialPageAdvertRel", UtilMisc.toMap("advertId", advertId, "specialPageId", templateId, "sequenceNum", sequence, "tplStyle", tplStyle));
        //轮播图的信息
        //预定义
        try {
            advert.store();
            advertRel.store();
            for (int i = 0; i < 6; i++) {
                String imgSrc = (String) params.get("upload" + (i + 1) + "_imgSrc");
                String seq = (String) params.get("upload" + (i + 1) + "_sequenceNum");
                String defineType = (String) params.get("upload" + (i + 1) + "_linkType");
                String relationId = (String) params.get("upload" + (i + 1) + "_val");
                String advertContentId = (String) params.get("upload" + (i + 1) + "_advertContentId");
                if (UtilValidate.isNotEmpty(imgSrc) && UtilValidate.isNotEmpty(seq) && UtilValidate.isNotEmpty(defineType) && UtilValidate.isNotEmpty(relationId)) {
                    GenericValue advertContent = delegator.makeValue("AdvertContent");
                    
                    advertContent.put("advertId", advertId);
                    advertContent.put("advertContentId", advertContentId);
                    advertContent.put("relationId", relationId);
                    advertContent.put("imgSrc", imgSrc);
                    advertContent.put("sequenceNum", Long.parseLong(seq));
                    advertContent.put("fromDate", UtilDateTime.nowTimestamp());
                    advertContent.put("defineType", "B");
                    advertContent.put("relationTypeId", defineType);
                    if (UtilValidate.isEmpty(advertContentId)) {
                        advertContentId = delegator.getNextSeqId("AdvertContent");
                        advertContent.put("advertContentId", advertContentId);
                        advertContent.create();
                    } else {
                        advertContent.store();
                    }
                }
                
            }
        } catch (GenericEntityException e) {
            e.printStackTrace();
        }
        return "success";
    }
    
    public static String createActivityFloorBanner(HttpServletRequest request, HttpServletResponse response) {
        Delegator delegator = (Delegator) request.getAttribute("delegator");
        LocalDispatcher dispatcher = (LocalDispatcher) request.getAttribute("dispatcher");
        Map<String, Object> params = UtilHttp.getParameterMap(request);
        //获取首页模板Id
        String templateId = (String) params.get("templateId");
        String advertName = (String) params.get("advertName");
        String sequenceNum = (String) params.get("sequenceNum");
        Long sequence = (sequenceNum != null ? Long.parseLong(sequenceNum) : 0);
        String tplStyle = (String) params.get("tplStyle");
        //生成advert，生成advertRel，生成advertContent
        String advertId = delegator.getNextSeqId("Advert");
        GenericValue advert = delegator.makeValue("Advert");
        advert.put("advertId", advertId);
        advert.put("advertName", advertName);
        advert.put("advertTypeId", "floorBanner");
        advert.put("dateCreated", UtilDateTime.nowTimestamp());
        advert.put("webSiteId", "daojia");
        GenericValue advertRel = delegator.makeValue("SpecialPageAdvertRel", UtilMisc.toMap("advertId", advertId, "specialPageId", templateId, "sequenceNum", sequence, "tplStyle", tplStyle));
        //轮播图的信息
        
        //预定义
        try {
            advert.create();
            advertRel.create();
            for (int i = 0; i < 6; i++) {
                String imgSrc = (String) params.get("upload" + (i + 1) + "_imgSrc");
                String seq = (String) params.get("upload" + (i + 1) + "_sequenceNum");
                String defineType = (String) params.get("upload" + (i + 1) + "_linkType");
                String relationId = (String) params.get("upload" + (i + 1) + "_val");
                if (UtilValidate.isNotEmpty(imgSrc) && UtilValidate.isNotEmpty(seq) && UtilValidate.isNotEmpty(defineType) && UtilValidate.isNotEmpty(relationId)) {
                    GenericValue advertContent = delegator.makeValue("AdvertContent");
                    String advertContentId = delegator.getNextSeqId("AdvertContent");
                    advertContent.put("advertId", advertId);
                    advertContent.put("advertContentId", advertContentId);
                    advertContent.put("relationId", relationId);
                    advertContent.put("imgSrc", imgSrc);
                    advertContent.put("sequenceNum", Long.parseLong(seq));
                    advertContent.put("fromDate", UtilDateTime.nowTimestamp());
                    advertContent.put("defineType", "B");
                    advertContent.put("relationTypeId", defineType);
                    advertContent.create();
                }
                
            }
        } catch (GenericEntityException e) {
            e.printStackTrace();
        }
        return "success";
    }
    
    public static String updateActivityFloorBanner(HttpServletRequest request, HttpServletResponse response) {
        Delegator delegator = (Delegator) request.getAttribute("delegator");
        LocalDispatcher dispatcher = (LocalDispatcher) request.getAttribute("dispatcher");
        Map<String, Object> params = UtilHttp.getParameterMap(request);
        //获取首页模板Id
        String templateId = (String) params.get("templateId");
        String advertName = (String) params.get("advertName");
        String sequenceNum = (String) params.get("sequenceNum");
        String advertId = (String) params.get("advertId");
        Long sequence = (sequenceNum != null ? Long.parseLong(sequenceNum) : 0);
        //生成advert，生成advertRel，生成advertContent
        String tplStyle = (String) params.get("tplStyle");
        GenericValue advert = delegator.makeValue("Advert");
        advert.put("advertId", advertId);
        advert.put("advertName", advertName);
        advert.put("advertTypeId", "floorBanner");
        advert.put("dateCreated", UtilDateTime.nowTimestamp());
        advert.put("webSiteId", "daojia");
        
        GenericValue advertRel = delegator.makeValue("SpecialPageAdvertRel", UtilMisc.toMap("advertId", advertId, "specialPageId", templateId, "sequenceNum", sequence, "tplStyle", tplStyle));
        
        //轮播图的信息
        //预定义
        try {
            advertRel.store();
            advert.store();
//            advertRel.create();
            for (int i = 0; i < 6; i++) {
                String imgSrc = (String) params.get("upload" + (i + 1) + "_imgSrc");
                String seq = (String) params.get("upload" + (i + 1) + "_sequenceNum");
                String defineType = (String) params.get("upload" + (i + 1) + "_linkType");
                String relationId = (String) params.get("upload" + (i + 1) + "_val");
                String advertContentId = (String) params.get("upload" + (i + 1) + "_advertContentId");
                if (UtilValidate.isNotEmpty(imgSrc) && UtilValidate.isNotEmpty(seq) && UtilValidate.isNotEmpty(defineType) && UtilValidate.isNotEmpty(relationId)) {
                    GenericValue advertContent = delegator.makeValue("AdvertContent");
                    
                    advertContent.put("advertId", advertId);
                    advertContent.put("advertContentId", advertContentId);
                    advertContent.put("relationId", relationId);
                    advertContent.put("imgSrc", imgSrc);
                    advertContent.put("sequenceNum", Long.parseLong(seq));
                    advertContent.put("fromDate", UtilDateTime.nowTimestamp());
                    advertContent.put("defineType", "B");
                    advertContent.put("relationTypeId", defineType);
                    if (UtilValidate.isEmpty(advertContentId)) {
                        advertContentId = delegator.getNextSeqId("AdvertContent");
                        advertContent.put("advertContentId", advertContentId);
                        advertContent.create();
                    } else {
                        advertContent.store();
                    }
                }
                
            }
        } catch (GenericEntityException e) {
            e.printStackTrace();
        }
        return "success";
    }
    
    
    public static String createActivityAct(HttpServletRequest request, HttpServletResponse response) {
        Delegator delegator = (Delegator) request.getAttribute("delegator");
        LocalDispatcher dispatcher = (LocalDispatcher) request.getAttribute("dispatcher");
        Map<String, Object> params = UtilHttp.getParameterMap(request);
        //获取首页模板Id
        String templateId = (String) params.get("templateId");
        String advertName = (String) params.get("advertName");
        String sequenceNum = (String) params.get("sequenceNum");
        String tplStyle = (String) params.get("tplStyle");
        Long sequence = (sequenceNum != null ? Long.parseLong(sequenceNum) : 0);
        //生成advert，生成advertRel，生成advertContent
        String advertId = delegator.getNextSeqId("Advert");
        GenericValue advert = delegator.makeValue("Advert");
        advert.put("advertId", advertId);
        advert.put("advertName", advertName);
        advert.put("advertTypeId", "act");
        advert.put("dateCreated", UtilDateTime.nowTimestamp());
        advert.put("webSiteId", "daojia");
        GenericValue advertRel = delegator.makeValue("SpecialPageAdvertRel", UtilMisc.toMap("advertId", advertId, "specialPageId", templateId, "sequenceNum", sequence, "tplStyle", tplStyle));
        //轮播图的信息
        
        //预定义
        try {
            advert.create();
            advertRel.create();
            for (int i = 0; i < 12; i++) {
                String imgSrc = (String) params.get("upload" + (i + 1) + "_imgSrc");
                String seq = (String) params.get("upload" + (i + 1) + "_sequenceNum");
                String defineType = (String) params.get("upload" + (i + 1) + "_linkType");
                String relationId = (String) params.get("upload" + (i + 1) + "_val");
                String advertContentName = (String) params.get("upload" + (i + 1) + "_advertContentName");
                String description = (String) params.get("upload" + (i + 1) + "_description");
                
                if (UtilValidate.isNotEmpty(imgSrc) && UtilValidate.isNotEmpty(seq) && UtilValidate.isNotEmpty(defineType) && UtilValidate.isNotEmpty(relationId)) {
                    GenericValue advertContent = delegator.makeValue("AdvertContent");
                    String advertContentId = delegator.getNextSeqId("AdvertContent");
                    advertContent.put("advertId", advertId);
                    advertContent.put("advertContentId", advertContentId);
                    advertContent.put("relationId", relationId);
                    advertContent.put("imgSrc", imgSrc);
                    advertContent.put("sequenceNum", Long.parseLong(seq));
                    advertContent.put("fromDate", UtilDateTime.nowTimestamp());
                    advertContent.put("defineType", "B");
                    advertContent.put("relationTypeId", defineType);
                    advertContent.put("advertContentName", advertContentName);
                    advertContent.put("description", description);
                    advertContent.create();
                }
                
            }
        } catch (GenericEntityException e) {
            e.printStackTrace();
        }
        return "success";
    }
    
    public static String updateActivityAct(HttpServletRequest request, HttpServletResponse response) {
        Delegator delegator = (Delegator) request.getAttribute("delegator");
        LocalDispatcher dispatcher = (LocalDispatcher) request.getAttribute("dispatcher");
        Map<String, Object> params = UtilHttp.getParameterMap(request);
        //获取首页模板Id
        String templateId = (String) params.get("templateId");
        String advertName = (String) params.get("advertName");
        String sequenceNum = (String) params.get("sequenceNum");
        String advertId = (String) params.get("advertId");
        Long sequence = (sequenceNum != null ? Long.parseLong(sequenceNum) : 0);
        String tplStyle = (String) params.get("tplStyle");
        //生成advert，生成advertRel，生成advertContent
        
        GenericValue advert = delegator.makeValue("Advert");
        advert.put("advertId", advertId);
        advert.put("advertName", advertName);
        advert.put("advertTypeId", "act");
        advert.put("dateCreated", UtilDateTime.nowTimestamp());
        advert.put("webSiteId", "daojia");
        
        GenericValue advertRel = delegator.makeValue("SpecialPageAdvertRel", UtilMisc.toMap("advertId", advertId, "specialPageId", templateId, "sequenceNum", sequence, "tplStyle", tplStyle));
        //轮播图的信息
        //预定义
        try {
            advert.store();
            advertRel.store();
            //删除对应的act 内容
            delegator.removeByAnd("AdvertContent", UtilMisc.toMap("advertId", advertId));
            for (int i = 0; i < 12; i++) {
                String imgSrc = (String) params.get("upload" + (i + 1) + "_imgSrc");
                String seq = (String) params.get("upload" + (i + 1) + "_sequenceNum");
                String defineType = (String) params.get("upload" + (i + 1) + "_linkType");
                String relationId = (String) params.get("upload" + (i + 1) + "_val");
                String advertContentId = (String) params.get("upload" + (i + 1) + "_advertContentId");
                String advertContentName = (String) params.get("upload" + (i + 1) + "_advertContentName");
                String description = (String) params.get("upload" + (i + 1) + "_description");
                
                if (UtilValidate.isNotEmpty(imgSrc) && UtilValidate.isNotEmpty(seq) && UtilValidate.isNotEmpty(defineType) && UtilValidate.isNotEmpty(relationId)) {
                    GenericValue advertContent = delegator.makeValue("AdvertContent");
                    
                    advertContent.put("advertId", advertId);
                    advertContent.put("advertContentId", advertContentId);
                    advertContent.put("relationId", relationId);
                    advertContent.put("imgSrc", imgSrc);
                    advertContent.put("sequenceNum", Long.parseLong(seq));
                    advertContent.put("fromDate", UtilDateTime.nowTimestamp());
                    advertContent.put("defineType", "B");
                    advertContent.put("relationTypeId", defineType);
                    advertContent.put("advertContentName", advertContentName);
                    advertContent.put("description", description);
                    
                    advertContentId = delegator.getNextSeqId("AdvertContent");
                    advertContent.put("advertContentId", advertContentId);
                    advertContent.create();
                    
                }
                
            }
        } catch (GenericEntityException e) {
            e.printStackTrace();
        }
        return "success";
    }
    
    public static String createActivityProduct5(HttpServletRequest request, HttpServletResponse response) {
        Delegator delegator = (Delegator) request.getAttribute("delegator");
        LocalDispatcher dispatcher = (LocalDispatcher) request.getAttribute("dispatcher");
        Map<String, Object> params = UtilHttp.getParameterMap(request);
        //获取首页模板Id
        String templateId = (String) params.get("templateId");
        String advertName = (String) params.get("advertName");
        String sequenceNum = (String) params.get("sequenceNum");
        String tplStyle = (String) params.get("tplStyle");
        Long sequence = (sequenceNum != null ? Long.parseLong(sequenceNum) : 0);
        //生成advert，生成advertRel，生成advertContent
        String advertId = delegator.getNextSeqId("Advert");
        GenericValue advert = delegator.makeValue("Advert");
        advert.put("advertId", advertId);
        advert.put("advertName", advertName);
        advert.put("advertTypeId", "product5");
        advert.put("dateCreated", UtilDateTime.nowTimestamp());
        advert.put("webSiteId", "daojia");
        GenericValue advertRel = delegator.makeValue("SpecialPageAdvertRel", UtilMisc.toMap("advertId", advertId, "specialPageId", templateId, "sequenceNum", sequence, "tplStyle", tplStyle));
        //轮播图的信息
        
        //预定义
        try {
            advert.create();
            advertRel.create();
            for (int i = 0; i < 1; i++) {
                String imgSrc = (String) params.get("upload" + (i + 1) + "_imgSrc");
                String seq = (String) params.get("upload" + (i + 1) + "_sequenceNum");
                String defineType = (String) params.get("upload" + (i + 1) + "_linkType");
                String relationId = (String) params.get("upload" + (i + 1) + "_val");
                if (UtilValidate.isNotEmpty(seq) && UtilValidate.isNotEmpty(defineType) && UtilValidate.isNotEmpty(relationId)) {
                    GenericValue advertContent = delegator.makeValue("AdvertContent");
                    String advertContentId = delegator.getNextSeqId("AdvertContent");
                    advertContent.put("advertId", advertId);
                    advertContent.put("advertContentId", advertContentId);
                    advertContent.put("relationId", relationId);
                    advertContent.put("imgSrc", imgSrc);
                    advertContent.put("sequenceNum", Long.parseLong(seq));
                    advertContent.put("fromDate", UtilDateTime.nowTimestamp());
                    advertContent.put("defineType", "B");
                    advertContent.put("relationTypeId", defineType);
                    advertContent.create();
                }
                
            }
        } catch (GenericEntityException e) {
            e.printStackTrace();
        }
        return "success";
    }
    
    public static String updateActivityProduct5(HttpServletRequest request, HttpServletResponse response) {
        Delegator delegator = (Delegator) request.getAttribute("delegator");
        LocalDispatcher dispatcher = (LocalDispatcher) request.getAttribute("dispatcher");
        Map<String, Object> params = UtilHttp.getParameterMap(request);
        //获取首页模板Id
        String templateId = (String) params.get("templateId");
        String advertName = (String) params.get("advertName");
        String sequenceNum = (String) params.get("sequenceNum");
        String tplStyle = (String) params.get("tplStyle");
        String advertId = (String) params.get("advertId");
        Long sequence = (sequenceNum != null ? Long.parseLong(sequenceNum) : 0);
        //生成advert，生成advertRel，生成advertContent
        GenericValue advert = delegator.makeValue("Advert");
        advert.put("advertId", advertId);
        advert.put("advertName", advertName);
        advert.put("advertTypeId", "product5");
        advert.put("dateCreated", UtilDateTime.nowTimestamp());
        advert.put("webSiteId", "daojia");
        GenericValue advertRel = delegator.makeValue("SpecialPageAdvertRel", UtilMisc.toMap("advertId", advertId, "specialPageId", templateId, "sequenceNum", sequence, "tplStyle", tplStyle));
        //轮播图的信息
        
        //预定义
        try {
            advert.store();
            advertRel.store();
            delegator.removeByAnd("AdvertContent", UtilMisc.toMap("advertId", advertId));
            for (int i = 0; i < 1; i++) {
                String imgSrc = (String) params.get("upload" + (i + 1) + "_imgSrc");
                String seq = (String) params.get("upload" + (i + 1) + "_sequenceNum");
                String defineType = (String) params.get("upload" + (i + 1) + "_linkType");
                String relationId = (String) params.get("upload" + (i + 1) + "_val");
                if (UtilValidate.isNotEmpty(seq) && UtilValidate.isNotEmpty(defineType) && UtilValidate.isNotEmpty(relationId)) {
                    GenericValue advertContent = delegator.makeValue("AdvertContent");
                    String advertContentId = delegator.getNextSeqId("AdvertContent");
                    advertContent.put("advertId", advertId);
                    advertContent.put("advertContentId", advertContentId);
                    advertContent.put("relationId", relationId);
                    advertContent.put("imgSrc", imgSrc);
                    advertContent.put("sequenceNum", Long.parseLong(seq));
                    advertContent.put("fromDate", UtilDateTime.nowTimestamp());
                    advertContent.put("defineType", "B");
                    advertContent.put("relationTypeId", defineType);
                    advertContent.create();
                }
                
            }
        } catch (GenericEntityException e) {
            e.printStackTrace();
        }
        return "success";
    }
    
    public static String createActivityAct1(HttpServletRequest request, HttpServletResponse response) {
        Delegator delegator = (Delegator) request.getAttribute("delegator");
        LocalDispatcher dispatcher = (LocalDispatcher) request.getAttribute("dispatcher");
        Map<String, Object> params = UtilHttp.getParameterMap(request);
        //获取首页模板Id
        String templateId = (String) params.get("templateId");
        String advertName = (String) params.get("advertName");
        String sequenceNum = (String) params.get("sequenceNum");
        String tplStyle = (String) params.get("tplStyle");
        Long sequence = (sequenceNum != null ? Long.parseLong(sequenceNum) : 0);
        //生成advert，生成advertRel，生成advertContent
        String advertId = delegator.getNextSeqId("Advert");
        GenericValue advert = delegator.makeValue("Advert");
        advert.put("advertId", advertId);
        advert.put("advertName", advertName);
        advert.put("advertTypeId", "act1");
        advert.put("dateCreated", UtilDateTime.nowTimestamp());
        advert.put("webSiteId", "daojia");
        GenericValue advertRel = delegator.makeValue("SpecialPageAdvertRel", UtilMisc.toMap("advertId", advertId, "specialPageId", templateId, "sequenceNum", sequence, "tplStyle", tplStyle));
        //轮播图的信息
        
        //预定义
        try {
            advert.create();
            advertRel.create();
            for (int i = 0; i < 1; i++) {
                String imgSrc = (String) params.get("upload" + (i + 1) + "_imgSrc");
                String seq = (String) params.get("upload" + (i + 1) + "_sequenceNum");
                String defineType = (String) params.get("upload" + (i + 1) + "_linkType");
                String relationId = (String) params.get("upload" + (i + 1) + "_val");
                if (UtilValidate.isNotEmpty(imgSrc) && UtilValidate.isNotEmpty(seq) && UtilValidate.isNotEmpty(defineType) && UtilValidate.isNotEmpty(relationId)) {
                    GenericValue advertContent = delegator.makeValue("AdvertContent");
                    String advertContentId = delegator.getNextSeqId("AdvertContent");
                    advertContent.put("advertId", advertId);
                    advertContent.put("advertContentId", advertContentId);
                    advertContent.put("relationId", relationId);
                    advertContent.put("imgSrc", imgSrc);
                    advertContent.put("sequenceNum", Long.parseLong(seq));
                    advertContent.put("fromDate", UtilDateTime.nowTimestamp());
                    advertContent.put("defineType", "B");
                    advertContent.put("relationTypeId", defineType);
                    advertContent.create();
                }
                
            }
        } catch (GenericEntityException e) {
            e.printStackTrace();
        }
        return "success";
    }
    
    public static String updateActivityAct1(HttpServletRequest request, HttpServletResponse response) {
        Delegator delegator = (Delegator) request.getAttribute("delegator");
        LocalDispatcher dispatcher = (LocalDispatcher) request.getAttribute("dispatcher");
        Map<String, Object> params = UtilHttp.getParameterMap(request);
        //获取首页模板Id
        String templateId = (String) params.get("templateId");
        String advertName = (String) params.get("advertName");
        String sequenceNum = (String) params.get("sequenceNum");
        String advertId = (String) params.get("advertId");
        Long sequence = (sequenceNum != null ? Long.parseLong(sequenceNum) : 0);
        String tplStyle = (String) params.get("tplStyle");
        //生成advert，生成advertRel，生成advertContent
        
        GenericValue advert = delegator.makeValue("Advert");
        advert.put("advertId", advertId);
        advert.put("advertName", advertName);
        advert.put("advertTypeId", "act1");
        advert.put("dateCreated", UtilDateTime.nowTimestamp());
        advert.put("webSiteId", "daojia");
        
        GenericValue advertRel = delegator.makeValue("SpecialPageAdvertRel", UtilMisc.toMap("advertId", advertId, "specialPageId", templateId, "sequenceNum", sequence, "tplStyle", tplStyle));
        //轮播图的信息
        //预定义
        try {
            advert.store();
            advertRel.store();
            for (int i = 0; i < 1; i++) {
                String imgSrc = (String) params.get("upload" + (i + 1) + "_imgSrc");
                String seq = (String) params.get("upload" + (i + 1) + "_sequenceNum");
                String defineType = (String) params.get("upload" + (i + 1) + "_linkType");
                String relationId = (String) params.get("upload" + (i + 1) + "_val");
                String advertContentId = (String) params.get("upload" + (i + 1) + "_advertContentId");
                delegator.removeByAnd("AdvertContent", UtilMisc.toMap("advertId", advertId));
                if (UtilValidate.isNotEmpty(imgSrc) && UtilValidate.isNotEmpty(seq) && UtilValidate.isNotEmpty(defineType) && UtilValidate.isNotEmpty(relationId)) {
                    GenericValue advertContent = delegator.makeValue("AdvertContent");
                    advertContent.put("advertId", advertId);
                    advertContent.put("advertContentId", advertContentId);
                    advertContent.put("relationId", relationId);
                    advertContent.put("imgSrc", imgSrc);
                    advertContent.put("sequenceNum", Long.parseLong(seq));
                    advertContent.put("fromDate", UtilDateTime.nowTimestamp());
                    advertContent.put("defineType", "B");
                    advertContent.put("relationTypeId", defineType);
                    advertContentId = delegator.getNextSeqId("AdvertContent");
                    advertContent.put("advertContentId", advertContentId);
                    advertContent.create();
                    
                }
                
            }
        } catch (GenericEntityException e) {
            e.printStackTrace();
        }
        return "success";
    }
    
    public static String createChannelProduct7(HttpServletRequest request, HttpServletResponse response) {
        Delegator delegator = (Delegator) request.getAttribute("delegator");
        LocalDispatcher dispatcher = (LocalDispatcher) request.getAttribute("dispatcher");
        Map<String, Object> params = UtilHttp.getParameterMap(request);
        //获取首页模板Id
        String templateId = (String) params.get("templateId");
        String advertName = (String) params.get("advertName");
        String sequenceNum = (String) params.get("sequenceNum");
        String tplStyle = (String) params.get("tplStyle");
        Long sequence = (sequenceNum != null ? Long.parseLong(sequenceNum) : 0);
        //生成advert，生成advertRel，生成advertContent
        String advertId = delegator.getNextSeqId("Advert");
        GenericValue advert = delegator.makeValue("Advert");
        advert.put("advertId", advertId);
        advert.put("advertName", advertName);
        advert.put("advertTypeId", "product7");
        advert.put("dateCreated", UtilDateTime.nowTimestamp());
        advert.put("webSiteId", "daojia");
        GenericValue advertRel = delegator.makeValue("SpecialPageAdvertRel", UtilMisc.toMap("advertId", advertId, "specialPageId", templateId, "sequenceNum", sequence, "tplStyle", tplStyle));
        //轮播图的信息
        
        //预定义
        try {
            advert.create();
            advertRel.create();
            for (int i = 0; i < 1; i++) {
                String imgSrc = (String) params.get("upload" + (i + 1) + "_imgSrc");
                String seq = (String) params.get("upload" + (i + 1) + "_sequenceNum");
                String defineType = (String) params.get("upload" + (i + 1) + "_linkType");
                String relationId = (String) params.get("upload" + (i + 1) + "_val");
                if (UtilValidate.isNotEmpty(seq) && UtilValidate.isNotEmpty(defineType) && UtilValidate.isNotEmpty(relationId)) {
                    GenericValue advertContent = delegator.makeValue("AdvertContent");
                    String advertContentId = delegator.getNextSeqId("AdvertContent");
                    advertContent.put("advertId", advertId);
                    advertContent.put("advertContentId", advertContentId);
                    advertContent.put("relationId", relationId);
                    advertContent.put("imgSrc", imgSrc);
                    advertContent.put("sequenceNum", Long.parseLong(seq));
                    advertContent.put("fromDate", UtilDateTime.nowTimestamp());
                    advertContent.put("defineType", "C");
                    advertContent.put("relationTypeId", defineType);
                    advertContent.create();
                }
                
            }
        } catch (GenericEntityException e) {
            e.printStackTrace();
        }
        return "success";
    }
    
    public static String updateChannelProduct7(HttpServletRequest request, HttpServletResponse response) {
        Delegator delegator = (Delegator) request.getAttribute("delegator");
        LocalDispatcher dispatcher = (LocalDispatcher) request.getAttribute("dispatcher");
        Map<String, Object> params = UtilHttp.getParameterMap(request);
        //获取首页模板Id
        String templateId = (String) params.get("templateId");
        String advertName = (String) params.get("advertName");
        String sequenceNum = (String) params.get("sequenceNum");
        String tplStyle = (String) params.get("tplStyle");
        String advertId = (String) params.get("advertId");
        Long sequence = (sequenceNum != null ? Long.parseLong(sequenceNum) : 0);
        //生成advert，生成advertRel，生成advertContent
        GenericValue advert = delegator.makeValue("Advert");
        advert.put("advertId", advertId);
        advert.put("advertName", advertName);
        advert.put("advertTypeId", "product7");
        advert.put("dateCreated", UtilDateTime.nowTimestamp());
        advert.put("webSiteId", "daojia");
        GenericValue advertRel = delegator.makeValue("SpecialPageAdvertRel", UtilMisc.toMap("advertId", advertId, "specialPageId", templateId, "sequenceNum", sequence, "tplStyle", tplStyle));
        //轮播图的信息
        
        //预定义
        try {
            advert.store();
            advertRel.store();
            delegator.removeByAnd("AdvertContent", UtilMisc.toMap("advertId", advertId));
            for (int i = 0; i < 1; i++) {
                String imgSrc = (String) params.get("upload" + (i + 1) + "_imgSrc");
                String seq = (String) params.get("upload" + (i + 1) + "_sequenceNum");
                String defineType = (String) params.get("upload" + (i + 1) + "_linkType");
                String relationId = (String) params.get("upload" + (i + 1) + "_val");
                if (UtilValidate.isNotEmpty(seq) && UtilValidate.isNotEmpty(defineType) && UtilValidate.isNotEmpty(relationId)) {
                    GenericValue advertContent = delegator.makeValue("AdvertContent");
                    String advertContentId = delegator.getNextSeqId("AdvertContent");
                    advertContent.put("advertId", advertId);
                    advertContent.put("advertContentId", advertContentId);
                    advertContent.put("relationId", relationId);
                    advertContent.put("imgSrc", imgSrc);
                    advertContent.put("sequenceNum", Long.parseLong(seq));
                    advertContent.put("fromDate", UtilDateTime.nowTimestamp());
                    advertContent.put("defineType", "C");
                    advertContent.put("relationTypeId", defineType);
                    advertContent.create();
                }
                
            }
        } catch (GenericEntityException e) {
            e.printStackTrace();
        }
        return "success";
    }
    
}
