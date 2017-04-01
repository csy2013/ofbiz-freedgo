package com.yuaoq.yabiz.oauth.data.connector;

import com.yuaoq.yabiz.oauth.utilities.BaseConfigurationReader;
import org.apache.commons.configuration.CompositeConfiguration;

public class DataConnectorConfiguration extends BaseConfigurationReader
{
	public static final String PREFIX									= "dcc";
	public static final String FILE										= "dataconnectorconfiguration.properties";

	public static final String PROPERTY_DATACONNECTOR_SERVER			= PREFIX + ".server";
	public static final String PROPERTY_DATACONNECTOR_PORT				= PREFIX + ".port";
	public static final String PROPERTY_DATACONNECTOR_DATASTORE			= PREFIX + ".datastore";
//	public static final String PROPERTY_DATACONNECTOR_USER				= PREFIX + ".user";
//	public static final String PROPERTY_DATACONNECTOR_PASSWORD			= PREFIX + ".password";

	public static final String DEFAULT_DATACONNECTOR_SERVER				= "127.0.0.1";
	public static final int DEFAULT_DATACONNECTOR_PORT					= 27017;
	public static final String DEFAULT_DATACONNECTOR_DATASTORE			= "mongo_datastore";
//	public static final String DEFAULT_DATACONNECTOR_USER				= null;
//	public static final String DEFAULT_DATACONNECTOR_PASSWORD			= null;

	private String _server;
	private int _port;
	private String _datastore;
//	private String _user;
//	private String _password;


	/* ***** BEGIN Singleton Access ***** */
	// http://stackoverflow.com/a/71683
	public static DataConnectorConfiguration getInstance(){ return _Holder.instance; }
	private static class _Holder { public static DataConnectorConfiguration instance = new DataConnectorConfiguration(); }
	/* ***** END Singleton Access ***** */

	
	/* ***** BEGIN Abstraction Fulfillment ***** */
	@Override
    public String getPropertyFileName(){ return FILE; }
	@Override
    protected boolean readProperties(CompositeConfiguration config)
	{
		if(config == null) return false;

		_server							= config.getString(String.format(PROPERTY_DATACONNECTOR_SERVER), DEFAULT_DATACONNECTOR_SERVER);
		_port							= config.getInt(String.format(PROPERTY_DATACONNECTOR_PORT), DEFAULT_DATACONNECTOR_PORT);
		_datastore						= config.getString(String.format(PROPERTY_DATACONNECTOR_DATASTORE), DEFAULT_DATACONNECTOR_DATASTORE);
//		_user							= config.getString(String.format(PROPERTY_DATACONNECTOR_USER), DEFAULT_DATACONNECTOR_USER);
//		_password						= config.getString(String.format(PROPERTY_DATACONNECTOR_PASSWORD), DEFAULT_DATACONNECTOR_PASSWORD);
		
		return true;
	}
	/* ***** END Abstraction Fulfillment ***** */


	/* ***** BEGIN Convenience Methods ***** */
	public String getServer(){ return _server; }
	public int getPort(){ return _port; }
	public String getDatastore(){ return _datastore; }
//	public String getUser(){ return _user; }
//	public String getPassword(){ return _password; }
	/* ***** END Convenience Methods ***** */
}
