/**
 * Copyright (c) 2000-present Liferay, Inc. All rights reserved.
 *
 * This library is free software; you can redistribute it and/or modify it under
 * the terms of the GNU Lesser General Public License as published by the Free
 * Software Foundation; either version 2.1 of the License, or (at your option)
 * any later version.
 *
 * This library is distributed in the hope that it will be useful, but WITHOUT
 * ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS
 * FOR A PARTICULAR PURPOSE. See the GNU Lesser General Public License for more
 * details.
 */

package org.ofbiz.common.minifier;


import com.yahoo.platform.yui.compressor.CssCompressor;
import org.ofbiz.base.util.Debug;
import org.ofbiz.base.util.ObjectType;
import org.ofbiz.base.util.UtilProperties;

import java.io.StringReader;
import java.io.StringWriter;
import java.util.Properties;

/**
 * @author Brian Wing Shun Chan
 * @author Raymond Augé
 */
public class MinifierUtil {

    public static final String module = MinifierUtil.class.getName();

	public static String minifyCss(String content) {
        Properties properties = UtilProperties.getProperties("general.properties");
		if (!properties.getProperty("minifier.enabled").equals("true")) {
			return content;
		}

		return _instance._minifyCss(content);
	}

	public static String minifyJavaScript(String resourceName, String content) {
        Properties properties = UtilProperties.getProperties("general.properties");
        if (!properties.getProperty("minifier.enabled").equals("true")) {
			return content;
		}
        return _instance._minifyJavaScript(resourceName, content);
	}

	private static JavaScriptMinifier _getJavaScriptMinifier() {
        Properties properties = UtilProperties.getProperties("general.properties");
		try {
		/*	return (JavaScriptMinifier)InstanceFactory.newInstance(
				PropsValues.MINIFIER_JAVASCRIPT_IMPL);*/
            return (JavaScriptMinifier)ObjectType.getInstance(properties.getProperty("minifier.javascript.impl"));
		}
		catch (Exception e) {

            Debug.logError(
                    "Unable to instantiate " + properties.get("minifier.javascript.impl"),module);

			return new GoogleJavaScriptMinifier();
		}
	}

	private MinifierUtil() {
		_javaScriptMinifierInstance = _getJavaScriptMinifier();
	}

	private String _minifyCss(String content) {
        Properties properties = UtilProperties.getProperties("general.properties");
		StringWriter unsyncStringWriter = new StringWriter();
        try {
			CssCompressor cssCompressor = new CssCompressor(
				new StringReader(content));
            cssCompressor.compress(
				unsyncStringWriter, Integer.parseInt(properties.getProperty("yui.compressor.css.line.break")));
		}
		catch (Exception e) {
			Debug.logError("Unable to minfiy CSS:\n" + content, module);
            unsyncStringWriter.append(content);
		}
        return unsyncStringWriter.toString();
	}

	private String _minifyJavaScript(String resourceName, String content) {
		return _javaScriptMinifierInstance.compress(resourceName, content);
	}

    private static final MinifierUtil _instance = new MinifierUtil();

	private final JavaScriptMinifier _javaScriptMinifierInstance;

}