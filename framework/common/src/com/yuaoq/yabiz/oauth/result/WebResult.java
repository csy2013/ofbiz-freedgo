package com.yuaoq.yabiz.oauth.result;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.common.net.MediaType;
import com.yuaoq.yabiz.oauth.data.json.helpers.BaseModelModule;
import com.yuaoq.yabiz.oauth.request.BasicRequest;
import com.yuaoq.yabiz.oauth.request.WebRequest;

import java.util.Map;


@JsonAutoDetect(fieldVisibility= JsonAutoDetect.Visibility.PROTECTED_AND_PUBLIC, getterVisibility= JsonAutoDetect.Visibility.NONE, isGetterVisibility= JsonAutoDetect.Visibility.NONE)
public abstract class WebResult<ReturnObjectType, ResultType, RenderTargetType, RedirectTargetType> extends ServiceResult<ReturnObjectType>
{
	public static final MediaType DEFAULT_MEDIATYPE		= MediaType.JSON_UTF_8;
	public static final boolean DEFAULT_AUTOMATICCOUNT	= true;
	public static final boolean DEFAULT_REDIRECT		= false;
	public static final boolean DEFAULT_RENDER			= false;

    protected String version;
	protected int count;
    protected WebRequest.Action action;
    protected Map<String, String> parameters;

    private WebRequest<?> _request;
	private boolean _redirect;
	private RedirectTargetType _redirectTarget;
	private boolean _render;
	private RenderTargetType _renderTarget;
	private boolean _automaticCount;


	public WebResult(WebRequest<?> value){ super(value); }


	protected boolean initialize(BasicRequest<?> value)
	{
		boolean initialized = super.initialize(value);

		count			= 0;
		_redirect		= DEFAULT_REDIRECT;
		_redirectTarget	= null;
		_render			= DEFAULT_RENDER;
		_renderTarget	= null;
		_automaticCount	= DEFAULT_AUTOMATICCOUNT;
		_request		= (WebRequest<?>) value;

		action = _request.getAction();
		version = _request.getVersion();
		parameters = _request.getParameters();

		return initialized;
	}


	public boolean getRedirect(){ return _redirect; }
	public void setRedirect(boolean value){ _redirect = value; }
	public boolean redirect(){ return getRedirect(); }
	public void redirect(boolean value){ setRedirect(value); }

	public RedirectTargetType getRedirectTarget(){ return _redirectTarget; }
	public void setRedirectTarget(RedirectTargetType value){ _redirectTarget = value; }

	public boolean getRender(){ return _render; }
	public void setRender(boolean value){ _render = value; }
	public boolean render(){ return getRender(); }
	public void render(boolean value){ setRender(value); }

	public RenderTargetType getRenderTarget(){ return _renderTarget; }
	public void setRenderTarget(RenderTargetType value){ _renderTarget = value; }

	public boolean getAutomaticCount(){ return _automaticCount; }
	public void setAutomaticCount(boolean value){ _automaticCount = value; }
	public boolean automaticCount(){ return getAutomaticCount(); }
	public void automaticCount(boolean value){ setAutomaticCount(value); }

	public int getCount(){ return count; }
	public void setCount(int value){ count = value; }


	public ObjectMapper getConfiguredObjectMapper()
	{
		ObjectMapper mapper = new ObjectMapper();

		mapper.registerModule(new BaseModelModule());
		mapper.setSerializationInclusion(Include.NON_NULL);

/*
		// http://stackoverflow.com/a/7108530/223362
		// Doesnt seem to work if @JsonAutoDetect is also present.
		// And contained classes wont get serialized unless @JsonAutoDetect is set...
		// So we have to do it per class instead of globally.
		mapper.setVisibilityChecker(mapper.getSerializationConfig().getDefaultVisibilityChecker()
			.withFieldVisibility(JsonAutoDetect.Visibility.PROTECTED_AND_PUBLIC)
			.withGetterVisibility(JsonAutoDetect.Visibility.NONE)
			.withIsGetterVisibility(JsonAutoDetect.Visibility.NONE)
			.withSetterVisibility(JsonAutoDetect.Visibility.PUBLIC_ONLY)
			.withCreatorVisibility(JsonAutoDetect.Visibility.PUBLIC_ONLY)
		);
*/
		
		return mapper;
	}


	public String toJson()
	{
		ObjectMapper mapper = getConfiguredObjectMapper();
		String jsonString = null;

		try {
//			if(getParameter("pretty") != null) mapper.enable(SerializationFeature.INDENT_OUTPUT);
			jsonString = mapper.writeValueAsString(this);
		} catch (JsonProcessingException e){
System.err.println("JsonProcessingException e: " + e);
System.err.println(printDiagnostics());
		}

System.err.println(printDiagnostics());
		
		return jsonString;
	}
	public String toXML(){ return toJson(); }
	public String toHtml(){ return toJson(); }


	public String getOutput()
	{
		MediaType mediaType = _request.getMediaType();

		if(mediaType == null) mediaType = DEFAULT_MEDIATYPE;
		if(mediaType.is(MediaType.JSON_UTF_8)){
			return toJson();
		} else if(mediaType.is(MediaType.XML_UTF_8)){
			return toXML();
		} else if(mediaType.is(MediaType.HTML_UTF_8)){
			return toHtml();
		}
		
		return toJson();
	}


	public abstract ResultType process();
}