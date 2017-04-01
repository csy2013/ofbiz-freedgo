package com.yuaoq.yabiz.oauth.request;

import com.google.common.net.MediaType;
import com.yuaoq.yabiz.oauth.broker.RequestResponseBroker;


public abstract class WebRequest<UserType> extends BasicRequest<String>
{
	public static final MediaType DEFAULT_MEDIATYPE		= MediaType.JSON_UTF_8;
	public static final String KEY_ACTION				= "action";
	public static final String KEY_VERSION				= "version";
	public static final String KEY_FORMAT				= "format";

	private RequestResponseBroker<?,?,?> _broker;
	private String _version;
	private Action _action;
	private MediaType _mediaType;


	// This is to kill any use of this without passing in a valid request.
	@SuppressWarnings("unused")
	private WebRequest(){}
	public WebRequest(RequestResponseBroker<?,?,?> broker){ this(broker, null); }
	public WebRequest(RequestResponseBroker<?,?,?> broker, Action value)
	{
		initialize(broker, value);
	}

	
	public boolean initialize(RequestResponseBroker<?,?,?> broker, Action value)
	{
		boolean initialized = super.initialize();

		if(broker == null) throw new NullPointerException();

		_version		= "1";
		_action			= value;
		_mediaType		= getDefaultMediaType();
		_broker			= broker;

		addParameters(_broker.extractRequestData());

		// Override only if we have a version parameter
		if(hasVersionParameter()) setVersion(extractVersion());
		// Override only if we have an action parameter
		if(hasActionParameter()) setAction(extractAction());
		setMediaType(extractMediaType());
		
		setInitialized(initialized);
		return initialized;
	}


	public RequestResponseBroker<?,?,?> getBroker(){ return _broker; }
	public void setBroker(RequestResponseBroker<?,?,?> value){ _broker = value; }


	public String getVersion(){ return _version; }
	public void setVersion(String value){ _version = value; }
	public String extractVersion()
	{
		if(getVersionParameter() != null) return getParameter(getVersionParameter());
		else return null;
	}
	public String getVersionParameter(){ return KEY_VERSION; }
	public boolean hasVersionParameter(){ return (extractVersion() != null); }


	public Action getAction(){ return _action; }
	public void setAction(Action value){ _action = value; }
	public Action extractAction()
	{
		Action action = null;
		
		if(getActionParameter() != null && getParameter(getActionParameter()) != null){
			try {
				action = Action.valueOf(getParameter(getActionParameter()).toUpperCase());
			} catch(IllegalArgumentException e){
				action = Action.UNKNOWN;
			}
		}

		return action;
	}
	public String getActionParameter(){ return KEY_ACTION; }
	public boolean hasActionParameter(){ return (extractAction() != null); }

	
	public MediaType getDefaultMediaType(){ return DEFAULT_MEDIATYPE; }
	public MediaType getMediaType(){ return _mediaType; }
	public void setMediaType(MediaType value){ _mediaType = value; }
	private MediaType extractMediaType()
	{
		MediaType mediaType = null;
		String key = KEY_FORMAT;
		String format = null;
		
		// See if we have a format override and use that first.
		if(key != null){
			format = getParameter(key);
			if(format != null){
				if(format.equalsIgnoreCase(MediaType.JSON_UTF_8.subtype())) mediaType = MediaType.JSON_UTF_8;
				else if(format.equalsIgnoreCase(MediaType.XML_UTF_8.subtype())) mediaType = MediaType.XML_UTF_8;
				else if(format.equalsIgnoreCase(MediaType.HTML_UTF_8.subtype())) mediaType = MediaType.HTML_UTF_8;
			}
		}


		// No format variable, so rely on what the client accepts, in order of our
		// preferences.
		if(mediaType == null){
			MediaType[] preferredMediaTypes = {MediaType.JSON_UTF_8, MediaType.XML_UTF_8, MediaType.HTML_UTF_8};
			mediaType = _broker.getAcceptedMediaType(preferredMediaTypes);
		}

		// Default it.
		if(mediaType == null) mediaType = getDefaultMediaType();

		return mediaType;
	}
	public String getMediaTypeAsString()
	{
		MediaType mediaType = _mediaType;

		if(mediaType == null) mediaType = DEFAULT_MEDIATYPE;

		return mediaType.withoutParameters().toString();
	}

	
	public abstract UserType getAuthenticatedUser();
	public abstract void setAuthenticatedUser(UserType user);
	

	public enum Action
	{
		GET,
		CREATE,
		UPDATE,
		DELETE,
		UNKNOWN;

		// I like my enums printed out in lower case, kthxbai.
		public String toString(){ return name().toLowerCase(); }

		// http://stackoverflow.com/a/2965252
		public static Action fromString(String text) throws IllegalArgumentException
		{
			if(text != null){
				for(Action action : Action.values()){
					if(text.equalsIgnoreCase(action.toString())){
						return action;
					}
				}
			}

			throw new IllegalArgumentException();
		}
	}
}