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

import com.yahoo.platform.yui.compressor.JavaScriptCompressor;
import org.mozilla.javascript.ErrorReporter;
import org.mozilla.javascript.EvaluatorException;
import org.ofbiz.base.util.Debug;
import org.ofbiz.base.util.UtilProperties;

import java.io.StringReader;
import java.io.StringWriter;
import java.util.Properties;

/**
 * @author
 */
public class YahooJavaScriptMinifier implements JavaScriptMinifier {


	@Override
	public String compress(String resourceName, String content) {
		StringWriter unsyncStringWriter = new StringWriter();
        Properties properties = UtilProperties.getProperties("general.properties");
		try {
			JavaScriptCompressor javaScriptCompressor =
				new JavaScriptCompressor(
					new StringReader(content),
					new JavaScriptErrorReporter());

			javaScriptCompressor.compress(
				unsyncStringWriter, Integer.parseInt(properties.getProperty("yui.compressor.css.line.break")),
                    properties.getProperty("yui.compressor.js.munge").equals("true"),
                    properties.getProperty("yui.compressor.js.verbose").equals("true"),
                    properties.getProperty("yui.compressor.js.preserve.all.semicolons").equals("true"),
                    properties.getProperty("yui.compressor.js.disable.optimizations").equals("true"));
		}
		catch (Exception e) {
			Debug.logError(("Unable to minify JavaScript:\n" + content), module);

			unsyncStringWriter.append(content);
		}

		return unsyncStringWriter.toString();
	}
    public static final String module = YahooJavaScriptMinifier.class.getName();

	private static class JavaScriptErrorReporter implements ErrorReporter {

		@Override
		public void error(
			String message, String sourceName, int line, String lineSource,
			int lineOffset) {

			if (line < 0) {
				Debug.logError(message, module);
			}
			else {
                Debug.logError((line + ": " + lineOffset + ": " + message),module);
			}
		}

		@Override
		public EvaluatorException runtimeError(
			String message, String sourceName, int line, String lineSource,
			int lineOffset) {

			error(message, sourceName, line, lineSource, lineOffset);

			return new EvaluatorException(message);
		}

		@Override
		public void warning(
			String message, String sourceName, int line, String lineSource,
			int lineOffset) {

			if (!Debug.warningOn()) {
				return;
			}

			if (line < 0) {
                Debug.logWarning(message, module);
			}
			else {
				Debug.logWarning(line + ": " + lineOffset + ": " + message, module);
			}
		}

	}

}