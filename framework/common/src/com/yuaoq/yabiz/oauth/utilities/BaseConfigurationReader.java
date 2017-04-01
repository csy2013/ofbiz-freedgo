package com.yuaoq.yabiz.oauth.utilities;

import org.apache.commons.configuration.CompositeConfiguration;
import org.apache.commons.configuration.ConfigurationException;
import org.apache.commons.configuration.PropertiesConfiguration;
import org.apache.commons.configuration.SystemConfiguration;

public abstract class BaseConfigurationReader
{
	private boolean _success;
	protected CompositeConfiguration _config;


	public BaseConfigurationReader()
	{
		_success = false;

		_config = createPropertyReader(getPropertyFileName());
		_success = readProperties(_config);
	}


	public abstract String getPropertyFileName();
	protected abstract boolean readProperties(CompositeConfiguration config);


	public boolean getSuccess(){ return _success; }
	public void setSuccess(boolean value){ _success = value; }
	public boolean isSuccess(){ return getSuccess(); }
	public boolean success(){ return isSuccess(); }


	protected CompositeConfiguration createPropertyReader(String file)
	{
		CompositeConfiguration config = new CompositeConfiguration();

		try {
			config.addConfiguration(new SystemConfiguration());
			config.addConfiguration(new PropertiesConfiguration(file));
		} catch(ConfigurationException e){
		}

		return config;
	}
}
