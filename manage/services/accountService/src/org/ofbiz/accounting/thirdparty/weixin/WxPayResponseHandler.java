package org.ofbiz.accounting.thirdparty.weixin;


import org.dom4j.Document;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;
import org.ofbiz.accounting.thirdparty.weixin.util.MD5Util;
import org.ofbiz.accounting.thirdparty.weixin.util.Sha1Util;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.util.*;


/**
 * 微信支付服务器签名支付请求应答类
 * api说明： 
 *  getKey()/setKey(),获取/设置密钥
 *  getParameter()/setParameter(),获取/设置参数值 getAllParameters(),获取所有参数
 *  isTenpaySign(),是否财付通签名,true:是 false:否
 *   getDebugInfo(),获取debug信息
 */
public class WxPayResponseHandler {

	private static final String appkey = null;

	/** 密钥 */
	private String key;

	/** 应答的参数 */
	private SortedMap parameters;

	/** debug信息 */
	private String debugInfo;

	private HttpServletRequest request;

	private HttpServletResponse response;

	private String uriEncoding;
	
	 private Hashtable xmlMap;

	private String k;

	/**
	 * 构造函数
	 * 
	 * @param request
	 * @param response
	 */
	public WxPayResponseHandler(HttpServletRequest request,
								HttpServletResponse response) {
		this.request = request;
		this.response = response;

		this.key = "";
		this.parameters = new TreeMap();
		this.debugInfo = "";
		this.uriEncoding = "";

		BufferedReader reader  =null;
		try{
			reader = new BufferedReader(new InputStreamReader(request.getInputStream()));
			StringBuilder sb = new StringBuilder();
			String line = null;
			while ((line = reader.readLine()) != null) {
				sb.append(line);
			}
			Document doc = DocumentHelper.parseText(sb.toString());
			Element root = doc.getRootElement();
			for (Iterator iterator = root.elementIterator(); iterator.hasNext();) {
				Element e = (Element) iterator.next();
				this.setParameter(e.getName(), e.getText());
			}

		}catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}

	}

	/**
	 *获取密钥
	 */
	public String getKey() {
		return key;
	}

	/**
	 *设置密钥
	 */
	public void setKey(String key) {
		this.key = key;
	}

	/**
	 * 获取参数值
	 * 
	 * @param parameter
	 *            参数名称
	 * @return String
	 */
	public String getParameter(String parameter) {
		String s = (String) this.parameters.get(parameter);
		return (null == s) ? "" : s;
	}

	/**
	 * 设置参数值
	 * 
	 * @param parameter
	 *            参数名称
	 * @param parameterValue
	 *            参数值
	 */
	public void setParameter(String parameter, String parameterValue) {
		String v = "";
		if (null != parameterValue) {
			v = parameterValue.trim();
		}
		this.parameters.put(parameter, v);
	}

	/**
	 * 返回所有的参数
	 * 
	 * @return SortedMap
	 */
	public SortedMap getAllParameters() {
		return this.parameters;
	}

	/**
	 * 是否财付通签名,规则是:按参数名称a-z排序,遇到空值的参数不参加签名。
	 * 
	 * @return boolean
	 */
	public boolean isValidSign() {
		StringBuffer sb = new StringBuffer();
		Set es = this.parameters.entrySet();
		Iterator it = es.iterator();
		while (it.hasNext()) {
			Map.Entry entry = (Map.Entry) it.next();
			String k = (String) entry.getKey();
			String v = (String) entry.getValue();
			if (!"sign".equals(k) && null != v && !"".equals(v)) {
				sb.append(k + "=" + v + "&");
			}
		}

		sb.append("key=" + this.getKey());

		// 算出摘要
		String enc = getCharacterEncoding(this.request,this.response);
		String sign = MD5Util.MD5Encode(sb.toString(), enc).toLowerCase();

		String ValidSign = this.getParameter("sign").toLowerCase();

		// debug信息
		this.setDebugInfo(sb.toString() + " => sign:" + sign + " ValidSign:"
				+ ValidSign);

		return ValidSign.equals(sign);
	}
	/**
	 * 判断微信签名
	 */
	public boolean isWXsign(){

		StringBuffer sb = new StringBuffer();
		String keys = "";
		SortedMap<String, String> signParams = new TreeMap<String, String>();
		Set es = this.parameters.entrySet();
		Iterator it = es.iterator();
		while (it.hasNext()) {
			Map.Entry entry = (Map.Entry) it.next();
			String k = (String) entry.getKey();
			String v = (String) entry.getValue();
			if (k != "SignMethod" && k != "AppSignature") {
				signParams.put(k.toLowerCase(), v);
			}
		}
		signParams.put("appkey", appkey);

		Set set = signParams.entrySet();
		Iterator pit = set.iterator();
		while (pit.hasNext()) {
			Map.Entry entry = (Map.Entry) pit.next();
			String k = (String) entry.getKey();
			String v = (String) entry.getValue();
			if (sb.length() == 0) {
				sb.append(k + "=" + v);
			} else {
				sb.append("&" + k + "=" + v);
			}
		}

		String sign = Sha1Util.getSha1(sb.toString()).toString().toLowerCase();

		this.setDebugInfo(sb.toString() + " => SHA1 sign:" + sign);
		return sign.equals(this.parameters.get("AppSignature"));
       
	}

	//判断微信维权签名
	public boolean isWXsignfeedback(){
		
		StringBuffer sb = new StringBuffer();
		 Hashtable signMap = new Hashtable();
		 Set es = this.parameters.entrySet();
		 Iterator it = es.iterator();
		 while (it.hasNext()){
			 	Map.Entry entry = (Map.Entry) it.next();
				String k = (String) entry.getKey();
				String v = (String) entry.getValue();
			 if (k != "SignMethod" && k != "AppSignature"){
				 
				 sb.append(k + "=" + v + "&");
			 }
		 }
		 signMap.put("appkey", this.key);
		 
		// ArrayList akeys = new ArrayList();
        // akeys.Sort();
         while (it.hasNext()){ 
             String v = k;
             if (sb.length() == 0)
             {
                 sb.append(k + "=" + v);
             } 
             else
             {
                 sb.append("&" + k + "=" + v);
             }
         }

         String sign = Sha1Util.getSha1(sb.toString()).toString().toLowerCase();

         this.setDebugInfo(sb.toString() + " => SHA1 sign:" + sign);

         return sign.equals("App    Signature");
     }	
		
	/**
	 * 返回处理结果给财付通服务器。
	 * 
	 * @param msg
	 * Success or fail
	 * @throws IOException
	 */
	public void sendToCFT(String msg) throws IOException {
		String strHtml = msg;
		PrintWriter out = this.getHttpServletResponse().getWriter();
		out.println(strHtml);
		out.flush();
		out.close();

	}

	/**
	 * 获取uri编码
	 * 
	 * @return String
	 */
	public String getUriEncoding() {
		return uriEncoding;
	}

	/**
	 * 设置uri编码
	 * 
	 * @param uriEncoding
	 * @throws UnsupportedEncodingException
	 */
	public void setUriEncoding(String uriEncoding)
			throws UnsupportedEncodingException {
		if (!"".equals(uriEncoding.trim())) {
			this.uriEncoding = uriEncoding;

			// 编码转换
			String enc = getCharacterEncoding(request, response);
			Iterator it = this.parameters.keySet().iterator();
			while (it.hasNext()) {
				String k = (String) it.next();
				String v = this.getParameter(k);
				v = new String(v.getBytes(uriEncoding.trim()), enc);
				this.setParameter(k, v);
			}
		}
	}

	/**
	 *获取debug信息
	 */
	public String getDebugInfo() {
		return debugInfo;
	}
	/**
	 *设置debug信息
	 */
	protected void setDebugInfo(String debugInfo) {
		this.debugInfo = debugInfo;
	}

	protected HttpServletRequest getHttpServletRequest() {
		return this.request;
	}

	protected HttpServletResponse getHttpServletResponse() {
		return this.response;
	}

	/**
	 * 获取编码字符集
	 * @param request
	 * @param response
	 * @return String
	 */

	public static String getCharacterEncoding(HttpServletRequest request,
											  HttpServletResponse response) {

		if(null == request || null == response) {
			return "gbk";
		}

		String enc = request.getCharacterEncoding();
		if(null == enc || "".equals(enc)) {
			enc = response.getCharacterEncoding();
		}

		if(null == enc || "".equals(enc)) {
			enc = "gbk";
		}

		return enc;
	}

}
