package com.yuaoq.yabiz.oauth.broker.servlet;

import com.google.common.net.MediaType;
import com.yuaoq.yabiz.oauth.broker.AbstractRequestResponseBroker;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;


public class ServletRequestResponseBroker extends AbstractRequestResponseBroker<HttpServletRequest,HttpServletResponse,Boolean>
{
	public ServletRequestResponseBroker(HttpServletRequest request, HttpServletResponse response){ super(request, response); }


	public Object getAttribute(String key)
	{
		if(_request == null) return null;
		else return _request.getSession().getAttribute(key);
	}


	public void setAttribute(String key, Object value)
	{
		if(_request == null) return;
		_request.getSession().setAttribute(key, value);
	}


	public void removeAttribute(String key)
	{
		if(_request == null) return;
		_request.getSession().removeAttribute(key);
	}


	public String getPathInfo()
	{
		if(_request == null) return null;
		else return _request.getContextPath() + _request.getServletPath() + _request.getPathInfo();
	}

	
	public Boolean redirect(String url)
	{
		if(url == null || url.trim().length() == 0) return false;

		try {
			_response.sendRedirect(url);
			return true;
		} catch(IOException e){
			e.printStackTrace();
		}
		
		return false;
	}


    public Map<String,String> extractRequestData()
    {
        Map<String,String> data = new HashMap<String,String>();

        if(_request == null) return data;

/*
        Map<String,String[]> urlFormEncoded = new HashMap<String,String[]>();
        if(_request.body() != null && _request.body().asFormUrlEncoded() != null) {
            urlFormEncoded = _request.body().asFormUrlEncoded();
        }

        Map<String,String[]> multipartFormData = new HashMap<String,String[]>();
        if(_request.body() != null && _request.body().asMultipartFormData() != null) {
            multipartFormData = _request.body().asMultipartFormData().asFormUrlEncoded();
        }

        Map<String,String> jsonData = new HashMap<String,String>();
        if(_request.body() != null && _request.body().asJson() != null) {
            jsonData = play.libs.Scala.asJava(
                play.api.data.FormUtils.fromJson("", 
                    play.api.libs.json.Json.parse(
                        play.libs.Json.stringify(_request.body().asJson())
                    )
                )
            );
        }
*/
        
        Map<String,String[]> queryString = _request.getParameterMap();

/*
        for(String key: urlFormEncoded.keySet()) {
            String[] values = urlFormEncoded.get(key);
            if(key.endsWith("[]")) {
                String k = key.substring(0, key.length() - 2);
                for(int i=0; i<values.length; i++) {
                    data.put(k + "[" + i + "]", values[i]);
                }
            } else {
                if(values.length > 0) {
                    data.put(key, values[0]);
                }
            }
        }

        for(String key: multipartFormData.keySet()) {
            String[] values = multipartFormData.get(key);
            if(key.endsWith("[]")) {
                String k = key.substring(0, key.length() - 2);
                for(int i=0; i<values.length; i++) {
                    data.put(k + "[" + i + "]", values[i]);
                }
            } else {
                if(values.length > 0) {
                    data.put(key, values[0]);
                }
            }
        }

        for(String key: jsonData.keySet()) {
            data.put(key, jsonData.get(key));
        }
*/
        for(String key: queryString.keySet()) {
            String[] values = queryString.get(key);
            if(key.endsWith("[]")) {
                String k = key.substring(0, key.length() - 2);
                for(int i=0; i<values.length; i++) {
                    data.put(k + "[" + i + "]", values[i]);
                }
            } else {
                if(values.length > 0) {
                    data.put(key, values[0]);
                }
            }
        }

        return data;
    }


	public MediaType getAcceptedMediaType(MediaType... preferredMediaTypes)
	{
		if(_request == null) return null;
		if(preferredMediaTypes == null) return null;

		String acceptedHeaders = _request.getHeader("Accept");
		if(acceptedHeaders == null) return null;
		
		for(MediaType type : preferredMediaTypes){
			if(type != null){
				if(acceptedHeaders.contains(type.withoutParameters().toString())) return type;
			}
		}

		return null;
	}
}
