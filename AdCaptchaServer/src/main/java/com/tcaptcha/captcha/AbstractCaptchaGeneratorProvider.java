/**
 * 
 */
package com.tcaptcha.captcha;


/**
 * 
 * 
 * 
 */
public abstract class AbstractCaptchaGeneratorProvider implements
	CaptchaGeneratorInfo {

	public abstract String getDescription();

	public abstract String getMimeType();

	public abstract String getModeCode();

	public abstract String getShortName();

	public abstract CaptchaGenerator get();

	
}
