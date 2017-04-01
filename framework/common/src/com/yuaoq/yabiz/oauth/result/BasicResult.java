package com.yuaoq.yabiz.oauth.result;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;


public class BasicResult<DataStoreValueType, ReturnDataType>
{
	public static final int DEFAULT_CODE				= -1;
	public static final String DEFAULT_MESSAGE			= null;
	public static final Exception DEFAULT_EXCEPTION		= null;
	public static final StatusLevel DEFAULT_LEVEL		= StatusLevel.INFO;

	protected boolean success;
	protected ReturnDataType returnData;
	protected LinkedList<Status> statusArchive;
	protected Map<String, DataStoreValueType> dataStore;

	private boolean _initialized;


	public BasicResult()
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
		success = false;
		returnData = null;
		initializeStatusArchive(true);
		initializeDataStore(true);
	}


	private LinkedList<Status> initializeStatusArchive(){ return initializeStatusArchive(false); }
	private LinkedList<Status> initializeStatusArchive(boolean reset)
	{
		if(statusArchive == null || reset) statusArchive = new LinkedList<Status>();
		return statusArchive;
	}


	private Map<String, DataStoreValueType> initializeDataStore(){ return initializeDataStore(false); }
	private Map<String, DataStoreValueType> initializeDataStore(boolean reset)
	{
		if(dataStore == null || reset) dataStore = new HashMap<String, DataStoreValueType>();
		return dataStore;
	}

	
	protected boolean getInitialized(){ return _initialized; }
	protected void setInitialized(boolean value){ _initialized = value; }
	public boolean isInitialized(){ return _initialized; }
	public boolean initialized(){ return isInitialized(); }

	public boolean getSuccess(){ return success; }
	public void setSuccess(boolean value){ success = value; }
	public boolean isSuccess(){ return getSuccess(); }
	public boolean success(){ return isSuccess(); }

	public ReturnDataType getReturnData(){ return returnData; }
	public void setReturnData(ReturnDataType value){ returnData = value; }
	public boolean hasReturnData(){ return getReturnData() != null; }

	public Map<String,DataStoreValueType> getDataStore(){ return dataStore; }
	public void addData(String key, DataStoreValueType value)
	{
		if(key != null){
			initializeDataStore();
			dataStore.put(key, value);
		}
	}
	public void addData(Map<String, ? extends DataStoreValueType> sourceMap)
	{
		if(sourceMap != null){
			initializeDataStore();
			dataStore.putAll(sourceMap);
		}
	}
	public DataStoreValueType getData(String key){ return (dataStore != null) ? dataStore.get(key) : null; }
	public boolean hasData(String key){ return key != null && !dataStore.containsKey(key); }
	public int getDataCount(){ return (dataStore != null) ? dataStore.size() : -1; }


	public int getRecentStatusCode()
	{
		Status status = getRecentStatus();
		if(status != null) return status.getCode();
		else return DEFAULT_CODE;
	}

	public String getRecentStatusMessage()
	{
		Status status = getRecentStatus();
		if(status != null) return status.getMessage();
		else return DEFAULT_MESSAGE;
	}
	public boolean hasMessage(){ return getRecentStatusMessage() != null; }


	public Exception getRecentStatusException()
	{
		Status status = getRecentStatus();
		if(status != null) return status.getException();
		else return DEFAULT_EXCEPTION;
	}
	public boolean hasException(){ return getRecentStatusException() != null; }


	public StatusLevel getRecentStatusLevel()
	{
		Status status = getRecentStatus();
		if(status != null) return status.getLevel();
		else return DEFAULT_LEVEL;
	}
	public boolean hasLevel(){ return getRecentStatusMessage() != null; }


	public Status getRecentStatus()
	{
		if(statusArchive != null && statusArchive.size() > 0) return statusArchive.getLast();
		else return null;
	}


	public Status addStatus(int code, String message){ return addStatus(code, message, (Exception) null); }
	public Status addStatus(int code, String message, Exception e){ return addStatus(code, message, e, null); }
	public Status addStatus(int code, String message, StatusLevel level){ return addStatus(code, message, null, level); }
	public Status addStatus(int code, String message, Exception e, StatusLevel level)
	{
		Status status = new Status();
		status.setCode(code);
		status.setMessage(message);
		status.setException(e);
		status.setLevel(level);

		return addStatus(status);
	}
	

	public Status addStatus(Status value)
	{
		initializeStatusArchive();

		if(value != null){
			statusArchive.add(value);
			return value;
		}

		return null;
	}

	public int getStatusCount(){ return (statusArchive != null) ? statusArchive.size() : -1; }
	public LinkedList<Status> getStatusArchive(){ return statusArchive; }


	public String printDiagnostics()
	{
		StringBuffer buffer = new StringBuffer();

		buffer.append("Initialized: " + isInitialized() + "\n");
		buffer.append("Success: " + success() + "\n");

		buffer.append("Return Data: " + getReturnData() + "\n");
		
		buffer.append("Code: " + getRecentStatusCode() + "\n");
		if(hasMessage()) buffer.append("Message: " + getRecentStatusMessage() + "\n");
		if(hasException()) buffer.append("Exception: " + getRecentStatusException() + "\n");
		if(hasLevel()) buffer.append("Level: " + getRecentStatusLevel() + "\n");

		buffer.append("Status count: " + getStatusCount() + "\n");
		buffer.append("Data count: " + getDataCount() + "\n");

		return buffer.toString();
	}


	public enum StatusLevel
	{
		DEBUG,
		INFO,
		WARNING,
		FATAL;

		// I like my enums printed out in lower case.
		public String toString(){ return name().toLowerCase(); }
	}


	public class Status
	{
		protected int code					= DEFAULT_CODE;
		protected String message			= DEFAULT_MESSAGE;
		protected Exception exception		= DEFAULT_EXCEPTION;
		protected StatusLevel level			= DEFAULT_LEVEL;

		public int getCode(){ return code; }
		public void setCode(int value){ code = value; }

		public String getMessage(){ return message; }
		public void setMessage(String value){ message = value; }
		public boolean hasMessage(){ return getMessage() != null; }

		public Exception getException(){ return exception; }
		public void setException(Exception value){ exception = value; }
		public boolean hasException(){ return getException() != null; }

		public StatusLevel getLevel(){ return level; }
		public void setLevel(StatusLevel value){ level = value; }
	}
}
