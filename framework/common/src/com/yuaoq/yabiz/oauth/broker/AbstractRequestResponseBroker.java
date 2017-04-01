package com.yuaoq.yabiz.oauth.broker;

import java.util.Map;


public abstract class AbstractRequestResponseBroker<RequestType, ResponseType, RedirectResponseType> implements RequestResponseBroker<RequestType, ResponseType, RedirectResponseType>
{
	protected boolean _initialized;
	protected RequestType _request;
	protected ResponseType _response;
	protected Map<String,String> _requestData;


	@SuppressWarnings("unused")
	// Locking this down to prevent others from using it.
	private AbstractRequestResponseBroker(){}

	public AbstractRequestResponseBroker(RequestType request, ResponseType response)
	{
		_initialized = false;
		_request = null;
		_response = null;
		_requestData = null;
		
		initialize(request, response);
	}


	@Override
	public boolean reinitialize(){ return initialize(_request, _response); }


	@Override
	public boolean initialize(RequestType request, ResponseType response)
	{
		_initialized = false;
		_request = request;
		_response = response;
		_requestData = null;

		if(_request == null || _response == null) return false;

		_requestData = extractRequestData();
		_initialized = true;

		return _initialized;
	}


	@Override
	public boolean initialized(){ return _initialized; }


	@Override
	public String getAttributeString(String key){ return (String) getAttribute(key); }


	@Override
	public String getParameter(String key)
	{
		if(_requestData == null) return null;
		else return _requestData.get(key);
	}


	@Override
	public RequestType getRequest(){ return _request; }
	public RequestResponseBroker<RequestType, ResponseType, RedirectResponseType> setRequest(RequestType value){ _request = value; return this; }

	@Override
	public ResponseType getResponse(){ return _response; }
	public RequestResponseBroker<RequestType, ResponseType, RedirectResponseType> setResponse(ResponseType value){ _response = value; return this; }
}
