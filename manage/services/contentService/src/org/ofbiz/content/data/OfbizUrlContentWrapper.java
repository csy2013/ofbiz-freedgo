package org.ofbiz.content.data;

import javolution.util.FastMap;
import org.ofbiz.base.util.*;
import org.ofbiz.base.util.cache.UtilCache;
import org.ofbiz.content.content.ContentWorker;
import org.ofbiz.content.content.ContentWrapper;
import org.ofbiz.entity.Delegator;
import org.ofbiz.entity.GenericEntityException;
import org.ofbiz.entity.GenericValue;
import org.ofbiz.entity.util.EntityUtil;
import org.ofbiz.service.LocalDispatcher;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.io.StringWriter;
import java.io.Writer;
import java.util.List;
import java.util.Locale;
import java.util.Map;

/**
 * Created by changsy on 16/6/6.
 */
public class OfbizUrlContentWrapper implements ContentWrapper {
  public static final String module = OfbizUrlContentWrapper.class.getName();
  public static UtilCache<String, String> ofbizUrlContentCache = UtilCache.createUtilCache("product.content.rendered", true);
  LocalDispatcher dispatcher;
  public GenericValue content;
  protected Locale locale;
  protected String mimeTypeId;


  public static OfbizUrlContentWrapper makeOfbizUrlContentWrapper(String contentId, Delegator delegator, HttpServletRequest request) {
    GenericValue content = null;
    try {
      content = delegator.findByPrimaryKey("Content", UtilMisc.toMap("contentId", contentId));
    } catch (GenericEntityException e) {
      e.printStackTrace();
    }
    return new OfbizUrlContentWrapper(content, request);
  }

  public static OfbizUrlContentWrapper makeOfbizUrlContentWrapper(GenericValue content, HttpServletRequest request) {
    return new OfbizUrlContentWrapper(content, request);
  }
  
  public OfbizUrlContentWrapper(LocalDispatcher dispatcher, GenericValue content, Locale locale, String mimeTypeId) {
    this.dispatcher = dispatcher;
    this.content = content;
    this.locale = locale;
    this.mimeTypeId = mimeTypeId;
  }
  
  public OfbizUrlContentWrapper(GenericValue content, HttpServletRequest request) {
    this.dispatcher = (LocalDispatcher) request.getAttribute("dispatcher");
    this.content = content;
    this.locale = UtilHttp.getLocale(request);
    this.mimeTypeId = "text/html";
  }

  public StringUtil.StringWrapper get(String contentTypeId) {
    if (contentTypeId.equals("slidePic")) {
      return StringUtil.makeStringWrapper(getOfbizUrlContentAsText(content, locale, mimeTypeId, content.getDelegator(), dispatcher));
    } else if (contentTypeId.equals("fileName")) {
      return StringUtil.makeStringWrapper(getOfbizUrlContentName(content, locale, mimeTypeId, content.getDelegator(), dispatcher));
    }else if(contentTypeId.equals("siteIcon")){
      return StringUtil.makeStringWrapper(getOfbizUrlContentAsText(content, locale, mimeTypeId, content.getDelegator(), dispatcher));
    }else if(contentTypeId.equals("articlePic")){
      return StringUtil.makeStringWrapper(getOfbizUrlContentAsText(content, locale, mimeTypeId, content.getDelegator(), dispatcher));
    }
    return StringUtil.makeStringWrapper("");
  }

  public String getOfbizUrlContentName(GenericValue content, Locale locale, String mimeTypeId, Delegator delegator, LocalDispatcher dispatcher) {
    return (String) content.get("contentName");
  }


  public static String getOfbizUrlContentAsText(GenericValue content, HttpServletRequest request) {
    LocalDispatcher dispatcher = (LocalDispatcher) request.getAttribute("dispatcher");
    return getOfbizUrlContentAsText(content, UtilHttp.getLocale(request), "text/html", content.getDelegator(), dispatcher);
  }
  
  public static String getOfbizUrlContentAsText(GenericValue content, Locale locale, LocalDispatcher dispatcher) {
    return getOfbizUrlContentAsText(content, locale, null, null, dispatcher);
  }
  
  public static String getOfbizUrlContentAsText(GenericValue content, Locale locale, String mimeTypeId, Delegator delegator, LocalDispatcher dispatcher) {
    try {
      Writer outWriter = new StringWriter();
      getOfbizUrlContentAsText(null, content, locale, mimeTypeId, delegator, dispatcher, outWriter);
      String outString = outWriter.toString();
      if (outString.length() > 0) {
        return outString;
      } else {
        return null;
      }
    } catch (GeneralException e) {
      Debug.logError(e, "Error rendering Content, inserting empty String", module);
      return "";
    } catch (IOException e) {
      Debug.logError(e, "Error rendering Content, inserting empty String", module);
      return "";
    }
  }
  
  public static void getOfbizUrlContentAsText(String contentId, GenericValue content, Locale locale, String mimeTypeId, Delegator delegator, LocalDispatcher dispatcher, Writer outWriter) throws GeneralException, IOException {
    if (contentId == null && content != null) {
      contentId = content.getString("contentId");
    }
    
    if (delegator == null && content != null) {
      delegator = content.getDelegator();
    }
    
    if (UtilValidate.isEmpty(mimeTypeId)) {
      mimeTypeId = "text/html";
    }
    
    if (delegator == null) {
      throw new GeneralRuntimeException("Unable to find a delegator to use!");
    }

    List<GenericValue> contentContentList = delegator.findByAndCache("Content", UtilMisc.toMap("contentId", contentId));

    GenericValue contentContent = EntityUtil.getFirst(contentContentList);
    if (contentContent != null) {
      // when rendering the content content, always include the Product Category and OfbizUrlContent records that this comes from
      Map<String, Object> inContext = FastMap.newInstance();
      inContext.put("content", content);
      inContext.put("contentContent", contentContent);
      ContentWorker.renderContentAsText(dispatcher, delegator, contentContent.getString("contentId"), outWriter, inContext, locale, mimeTypeId, null, null, true);
    }
  }
}
