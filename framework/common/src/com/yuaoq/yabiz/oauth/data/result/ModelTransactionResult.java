package com.yuaoq.yabiz.oauth.data.result;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.HashMap;
import java.util.Map;


@JsonAutoDetect(fieldVisibility= JsonAutoDetect.Visibility.PROTECTED_AND_PUBLIC, getterVisibility= JsonAutoDetect.Visibility.NONE, isGetterVisibility= JsonAutoDetect.Visibility.NONE)
public class ModelTransactionResult<ReturnDataType>
{
	protected boolean success = false;
	protected Map<String, KeyTransactionResult> transactions = new HashMap<String, KeyTransactionResult>();
	protected ReturnDataType model = null;

	public boolean getSuccess(){ return success; }
	public void setSuccess(boolean value){ success = value; }
	public boolean isSuccess(){ return getSuccess(); }
	public boolean success(){ return isSuccess(); }

	public ReturnDataType getModel(){ return model; }
	public void setModel(ReturnDataType value){ model = value; }

	public void addKeyTransaction(KeyTransactionResult value)
	{
		if(value == null) return;
		transactions.put(value.getKey(), value);
	}

	public int getTransactionCount(){ return (transactions != null) ? transactions .size() : -1; }
	public Map<String, KeyTransactionResult> getTransactions(){ return transactions; }


	@JsonAutoDetect(fieldVisibility= JsonAutoDetect.Visibility.PROTECTED_AND_PUBLIC, getterVisibility= JsonAutoDetect.Visibility.NONE, isGetterVisibility= JsonAutoDetect.Visibility.NONE)
	public static class KeyTransactionResult
	{
		protected boolean success = false;
		protected State state = State.NOTATTEMPTED;
		protected String message = null;
		protected String key = null;
		@JsonProperty("original")
		protected Object originalValue = null;
		@JsonProperty("current")
		protected Object newValue = null;
		@JsonProperty("string") @JsonIgnore
		protected String stringValue = null;
		@JsonProperty("class") @JsonIgnore
		protected Class<?> valueClass = null;

		public boolean getSuccess(){ return success; }
		public void setSuccess(boolean value){ success = value; }
		public boolean isSuccess(){ return getSuccess(); }
		public boolean success(){ return isSuccess(); }

		public State getState(){ return state; }
		public void setState(State value){ state = value; }

		public String getMessage(){ return message; }
		public void setMessage(String value){ message = value; }

		public String getKey(){ return key; }
		public void setKey(String value){ key = value; }

		public Object getNewValue(){ return newValue; }
		public void setNewValue(Object value){ this.newValue = value; }

		public Object getOriginalValue(){ return originalValue; }
		public void setOriginalValue(Object value){ this.originalValue = value; }

		public String getStringValue(){ return stringValue; }
		public void setStringValue(String value){ this.stringValue = value; }

		public Class<?> getValueClass(){ return valueClass; }
		public void setValueClass(Class<?> value){ this.valueClass = value; }


		public enum State
		{
			ACCEPTED,
			CONVERTEDASNEEDED,
			KEYNOTAPPLICABLE,
			KEYINCOMPATIBLE,
			CONVERSIONFAILED,
			CONVERTERNOTFOUND,
			REJECTED,
			NOTATTEMPTED,
			NULLVALUE,
			IMMUTABLEKEY,
			REQUIRED;

			// I like my enums printed out in lower case, kthxbai.
			public String toString(){ return name().toLowerCase(); }
		}
	}
}

/*
@JsonAutoDetect(fieldVisibility=JsonAutoDetect.Visibility.PROTECTED_AND_PUBLIC, getterVisibility=JsonAutoDetect.Visibility.NONE, isGetterVisibility=JsonAutoDetect.Visibility.NONE)
public class ModelTransactionResult<T>
{
	protected boolean success = false;
	protected Map<String, KeyTransactionResult> details = new HashMap<String, KeyTransactionResult>();
	protected T model = null;

	public boolean getSuccess(){ return success; }
	public void setSuccess(boolean value){ success = value; }
	public boolean isSuccess(){ return getSuccess(); }
	public boolean success(){ return isSuccess(); }

	public T getModel(){ return model; }
	public void setModel(T value){ model = value; }

	public void addKeyTransaction(KeyTransactionResult value)
	{
		if(value == null) return;
		details.put(value.getKey(), value);
	}

	public int getStatusCount(){ return (details != null) ? details .size() : -1; }
	public Map<String, KeyTransactionResult> getDetails(){ return details; }


	@JsonAutoDetect(fieldVisibility=JsonAutoDetect.Visibility.PROTECTED_AND_PUBLIC, getterVisibility=JsonAutoDetect.Visibility.NONE, isGetterVisibility=JsonAutoDetect.Visibility.NONE)
	public static class KeyTransactionResult
	{
		protected boolean success = false;
		protected State state = State.NOTATTEMPTED;
		protected String message = null;
		protected String key = null;
		@JsonProperty("original")
		protected Object originalValue = null;
		@JsonProperty("current")
		protected Object newValue = null;
		@JsonProperty("string") @JsonIgnore
		protected String stringValue = null;
		@JsonProperty("class") @JsonIgnore
		protected Class<?> valueClass = null;

		public boolean getSuccess(){ return success; }
		public void setSuccess(boolean value){ success = value; }
		public boolean isSuccess(){ return getSuccess(); }
		public boolean success(){ return isSuccess(); }

		public State getState(){ return state; }
		public void setState(State value){ state = value; }

		public String getMessage(){ return message; }
		public void setMessage(String value){ message = value; }

		public String getKey(){ return key; }
		public void setKey(String value){ key = value; }

		public Object getNewValue(){ return newValue; }
		public void setNewValue(Object value){ this.newValue = value; }

		public Object getOriginalValue(){ return originalValue; }
		public void setOriginalValue(Object value){ this.originalValue = value; }

		public String getStringValue(){ return stringValue; }
		public void setStringValue(String value){ this.stringValue = value; }

		public Class<?> getValueClass(){ return valueClass; }
		public void setValueClass(Class<?> value){ this.valueClass = value; }


		public enum State
		{
			ACCEPTED,
			CONVERTEDASNEEDED,
			KEYNOTAPPLICABLE,
			KEYINCOMPATIBLE,
			CONVERSIONFAILED,
			CONVERTERNOTFOUND,
			REJECTED,
			NOTATTEMPTED,
			NULLVALUE,
			IMMUTABLEKEY,
			REQUIRED;

			// I like my enums printed out in lower case, kthxbai.
			public String toString(){ return name().toLowerCase(); }
		}
	}
}
*/