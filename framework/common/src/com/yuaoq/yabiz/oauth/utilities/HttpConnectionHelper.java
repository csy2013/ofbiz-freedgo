package com.yuaoq.yabiz.oauth.utilities;

import org.apache.commons.lang3.StringUtils;
import org.apache.http.client.ResponseHandler;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.BasicResponseHandler;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicNameValuePair;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;


public class HttpConnectionHelper
{
	private HttpConnectionHelper(){}


	public static String getBasicResponse(String url)
	{
		String responseBody = null;
		CloseableHttpClient httpclient = HttpClients.createDefault();
		HttpGet httpget = new HttpGet(url);
		ResponseHandler<String> responseHandler = new BasicResponseHandler();
/*
        ResponseHandler<String> responseHandler = new ResponseHandler<String>() {
            public String handleResponse(final HttpResponse response) throws ClientProtocolException, IOException {
                int status = response.getStatusLine().getStatusCode();
                if (status >= 200 && status < 300) {
                    HttpEntity entity = response.getEntity();
                    return entity != null ? EntityUtils.toString(entity) : null;
                } else {
                    throw new ClientProtocolException("Unexpected response status: " + status);
                }
            }
        };
*/		

		try {
			responseBody = httpclient.execute(httpget, responseHandler);
		} catch (Exception e){
		    e.printStackTrace();
		} finally {
			try {
				httpclient.close();
			} catch (IOException e){}
		}

        return responseBody;
    }

	
	private static List<BasicNameValuePair> toBasicNameValuePairList(Map<String,String> map)
	{
		List<BasicNameValuePair> parameterList = new ArrayList<BasicNameValuePair>();

		if(map == null) return null;
		
		for(String key : map.keySet()){
			String value = map.get(key);

			// Only add if they are non nulls.  Additionally, the key cannot be blank.
			if(!StringUtils.isBlank(key) && value != null) parameterList.add(new BasicNameValuePair(key, value));
		}

		return parameterList;
	}

	
	public static String postBasicResponse(String url){ return postBasicResponse(url, (List<BasicNameValuePair>) null); }
	public static String postBasicResponse(String url, Map<String, String> parameters){ return postBasicResponse(url, toBasicNameValuePairList(parameters)); }
	public static String postBasicResponse(String url, List<BasicNameValuePair> parameters)
	{
		String responseBody = null;
		CloseableHttpClient httpclient = HttpClients.createDefault();
		UrlEncodedFormEntity entity = null;
		HttpPost http = new HttpPost(url);
		ResponseHandler<String> responseHandler = new BasicResponseHandler();

		if(parameters != null && parameters.size() > 0){
			try {
				entity = new UrlEncodedFormEntity(parameters, "UTF-8");
				http.setEntity(entity);
			} catch (UnsupportedEncodingException e1){
				return null;
			}
		}

		try {
			responseBody = httpclient.execute(http, responseHandler);
		} catch (IOException e){
		} finally {
			try {
				httpclient.close();
			} catch (IOException e){}
		}

        return responseBody;
    }


	public static String encode(String value){ return encode(value, "UTF-8"); }
	public static String encode(String value, String charset)
	{
		String encoded = null;
		
		try {
			encoded = URLEncoder.encode(value, charset);
		} catch (UnsupportedEncodingException e){
			encoded = null;
		}
		
		return encoded;
	}


	public static String decode(String value){ return decode(value, "UTF-8"); }
	public static String decode(String value, String charset)
	{
		String decoded = null;
		
		try {
			decoded = URLDecoder.decode(value, charset);
		} catch (UnsupportedEncodingException e){
			decoded = null;
		}
		
		return decoded;
	}
}
