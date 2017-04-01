package com.yuaoq.yabiz.oauth.request;

import java.util.HashMap;
import java.util.Map;


public class BasicRequest<ParameterStoreValueType>
{
	private Map<String, ParameterStoreValueType> _parameterStore;
	private boolean _initialized;


	public BasicRequest()
	{
		_initialized = false;
		initialize();

		// We should probably throw an exception here if the service is invalid
		// to prevent the instance from being used.
//		if(!_initialized) throw InvalidServiceException();
	}

	
	public final boolean initialize()
	{
		reset();

		_initialized = true;

		return true;
	}


	protected void reset()
	{
		initializeParameterStore(true);
	}


	private Map<String, ParameterStoreValueType> initializeParameterStore(){ return initializeParameterStore(false); }
	private Map<String, ParameterStoreValueType> initializeParameterStore(boolean reset)
	{
		if(_parameterStore == null || reset) _parameterStore = new HashMap<String, ParameterStoreValueType>();
		return _parameterStore;
	}

	
	protected boolean getInitialized(){ return _initialized; }
	protected void setInitialized(boolean value){ _initialized = value; }
	public boolean isInitialized(){ return _initialized; }
	public boolean initialized(){ return isInitialized(); }

	public Map<String, ParameterStoreValueType> getParameters(){ return _parameterStore; }
	public void addParameter(String key, ParameterStoreValueType value)
	{
		if(key != null){
			initializeParameterStore();
			_parameterStore.put(key, value);
		}
	}
	public void addParameters(Map<String, ? extends ParameterStoreValueType> sourceMap)
	{
		if(sourceMap != null){
			initializeParameterStore();
			_parameterStore.putAll(sourceMap);
		}
	}
	public ParameterStoreValueType getParameter(String key){ return (_parameterStore != null) ? _parameterStore.get(key) : null; }
	public boolean hasParameter(String key){ return key != null && !_parameterStore.containsKey(key); }
	public int getParameterCount(){ return (_parameterStore != null) ? _parameterStore.size() : -1; }


	public String printDiagnostics()
	{
		StringBuffer buffer = new StringBuffer();

		buffer.append("Initialized: " + isInitialized() + "\n");

		buffer.append("Parametrers: " + getParameters() + "\n");

		return buffer.toString();
	}
}
