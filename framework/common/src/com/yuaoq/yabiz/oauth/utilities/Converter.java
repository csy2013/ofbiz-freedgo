package com.yuaoq.yabiz.oauth.utilities;

public class Converter
{
	public static final int toint(String value){ return toint(value, -1); }
	public static final int toint(String value, int defaultValue)
	{
		try {
			return Integer.parseInt(value);
		} catch(NumberFormatException e){
			return defaultValue;
		}
	}

	public static final long tolong(String value){ return tolong(value, -1l); }
	public static final long tolong(String value, long defaultValue)
	{
		try {
			return Long.parseLong(value);
		} catch(NumberFormatException e){
			return defaultValue;
		}
	}

	public static final short toshort(String value){ return toshort(value, (short) -1); }
	public static final short toshort(String value, short defaultValue)
	{
		try {
			return Short.parseShort(value);
		} catch(NumberFormatException e){
			return defaultValue;
		}
	}
}
