package com.yuaoq.yabiz.oauth.result;

import com.yuaoq.yabiz.oauth.data.result.ModelTransactionResult.KeyTransactionResult;
import com.yuaoq.yabiz.oauth.request.BasicRequest;
import org.apache.http.HttpStatus;

import java.util.LinkedList;
import java.util.List;
import java.util.Map;


public class ServiceResult<ReturnObjectType> extends BasicResult<String, Object>
{
    protected LinkedList<ReturnObjectType> resultList;

    protected int statusCode;
    protected Map<String, KeyTransactionResult> transactions;

	protected BasicRequest<?> _request;


	// This is to kill any use of this without passing in a valid request.
	@SuppressWarnings("unused")
	private ServiceResult(){}


	public ServiceResult(BasicRequest<?> value)
    {
		boolean success = false;
		
		success = initialize(value);
		
		setInitialized(success);
    }


	protected boolean initialize(BasicRequest<?> value)
	{
		boolean initialized = super.initialize();

		if(value == null) throw new NullPointerException();

		resultList		= new LinkedList<ReturnObjectType>();
		statusCode		= HttpStatus.SC_OK;
		transactions	= null;
		_request		= value;

		return initialized;
	}


	public List<? extends ReturnObjectType> getResults(){ return resultList; }
	public void setResults(List<? extends ReturnObjectType> value)
	{
		if(resultList != null) resultList.clear();
		addResults(value);
	}
	public void addResults(List<? extends ReturnObjectType> value)
	{
		if(value == null);
		if(resultList == null) resultList = new LinkedList<ReturnObjectType>();

		resultList.addAll(value);
	}
	public void addResult(ReturnObjectType value)
	{
		if(value == null) return;
		if(resultList == null) resultList = new LinkedList<ReturnObjectType>();

		resultList.push(value);
	}
	public int getResultsCount()
	{
		if(resultList == null) return 0;
		else return resultList.size();
	}


	public int getStatusCode(){ return statusCode; }
	public void setStatusCode(int value){ statusCode = value; }

	public Map<String, KeyTransactionResult> getTransactions(){ return transactions; }
	public void setTransactions(Map<String, KeyTransactionResult> value){ transactions = value; }
}