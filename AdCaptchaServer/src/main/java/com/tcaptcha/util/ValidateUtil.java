/**
 * 
 */
package com.tcaptcha.util;

import java.util.regex.Pattern;

import com.tcaptcha.GlobalConfiguration;

/**
 * utils for string validation
 * 
 * 
 */
public class ValidateUtil {

	private ValidateUtil() {
	}

	/**
	 * 
	 */
	public static final String CODE_NUMALPHA_PATTERN = "[a-zA-Z0-9]+";

	/**
	 * 
	 * @param code
	 * @return
	 */
	public static boolean isValidCode(String code) {
		if (code.length() > GlobalConfiguration
				.getInt("tcaptcha.maxcodelength")) {
			return false;
		}
		return Pattern.matches(CODE_NUMALPHA_PATTERN, code);
	}

	/**
	 * @FIXME
	 * @param userAgent
	 * @return
	 */
	public static boolean isValidUserAgent(String userAgent) {
		throw new UnsupportedOperationException("Not Implemented");
	}

}
