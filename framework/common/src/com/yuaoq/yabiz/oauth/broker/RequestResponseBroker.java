package com.yuaoq.yabiz.oauth.broker;

import com.google.common.net.MediaType;

import java.util.Map;


public interface RequestResponseBroker<RequestType, ResponseType, RedirectResponseType>
{
	boolean reinitialize();
	boolean initialize(RequestType request, ResponseType response);
	boolean initialized();

	RequestType getRequest();
	ResponseType getResponse();

	String getAttributeString(String key);
	Object getAttribute(String key);

	void setAttribute(String key, Object value);

	void removeAttribute(String key);

	String getParameter(String key);
	String getPathInfo();

	MediaType getAcceptedMediaType(MediaType... preferredMediaTypes);

	RedirectResponseType redirect(String url);

	Map<String,String> extractRequestData();
}
