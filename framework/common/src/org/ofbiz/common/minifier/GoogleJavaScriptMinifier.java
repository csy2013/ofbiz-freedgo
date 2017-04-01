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

import com.google.javascript.jscomp.*;
import com.google.javascript.jscomp.Compiler;
import com.google.javascript.jscomp.CompilerOptions.LanguageMode;
import org.ofbiz.base.util.Debug;


/**
 * @author Carlos Sierra Andrés
 */
public class GoogleJavaScriptMinifier implements JavaScriptMinifier {

	@Override
	public String compress(String resourceName, String content) {
		Compiler compiler = new Compiler(new LogErrorManager());

		compiler.disableThreads();

		SourceFile sourceFile = SourceFile.fromCode(resourceName, content);

		CompilerOptions compilerOptions = new CompilerOptions();

		compilerOptions.setLanguageIn(LanguageMode.ECMASCRIPT5);
		compilerOptions.setWarningLevel(
			DiagnosticGroups.NON_STANDARD_JSDOC, CheckLevel.OFF);

		setCompileOptions(compilerOptions);

		compiler.compile(
			SourceFile.fromCode("extern", ""), sourceFile,
			compilerOptions);

		return compiler.toSource();
	}

	protected void setCompileOptions(CompilerOptions compilerOptions) {
		compilerOptions.checkGlobalThisLevel = CheckLevel.OFF;
		compilerOptions.closurePass = true;
		compilerOptions.coalesceVariableNames = true;
		compilerOptions.collapseVariableDeclarations = true;
		compilerOptions.convertToDottedProperties = true;
		compilerOptions.deadAssignmentElimination = true;
		compilerOptions.flowSensitiveInlineVariables = true;
		compilerOptions.foldConstants = true;
		compilerOptions.labelRenaming = true;
		compilerOptions.removeDeadCode = true;
		compilerOptions.optimizeArgumentsArray = true;

		compilerOptions.setAssumeClosuresOnlyCaptureReferences(false);
		compilerOptions.setInlineFunctions(CompilerOptions.Reach.LOCAL_ONLY);
		compilerOptions.setInlineVariables(CompilerOptions.Reach.LOCAL_ONLY);
		compilerOptions.setRenamingPolicy(
			VariableRenamingPolicy.LOCAL, PropertyRenamingPolicy.OFF);
		compilerOptions.setRemoveUnusedVariables(
			CompilerOptions.Reach.LOCAL_ONLY);
	}

    public static final String module = GoogleJavaScriptMinifier.class.getName();

	private static class SimpleMessageFormatter implements MessageFormatter {

		@Override
		public String formatError(JSError jsError) {
			return String.format(
                    "(%s:%d): %s", jsError.sourceName, jsError.lineNumber,
                    jsError.description);
		}

		@Override
		public String formatWarning(JSError jsError) {
			return formatError(jsError);
		}

	}

	private class LogErrorManager extends BasicErrorManager {

		@Override
		public void println(CheckLevel checkLevel, JSError jsError) {
			if (checkLevel == CheckLevel.ERROR) {
                Debug.logError(jsError.format(checkLevel, _simpleMessageFormatter), module);
			}
			else if (checkLevel == CheckLevel.WARNING) {
				if (Debug.warningOn()) {
					Debug.logWarning(
                            jsError.format(checkLevel, _simpleMessageFormatter), module);
				}
			}
		}

		@Override
		protected void printSummary() {
			if (getErrorCount() > 0) {
                Debug.logError(_buildMessage(),module);
			}
			else if (Debug.warningOn() && (getWarningCount() > 0)) {
                Debug.logWarning(_buildMessage(),module);
			}
		}

		private String _buildMessage() {
			return String.format(
                    "{0} error(s), {1} warning(s)", getErrorCount(),
                    getWarningCount());
		}

		private final MessageFormatter _simpleMessageFormatter =
			new SimpleMessageFormatter();

	}

}