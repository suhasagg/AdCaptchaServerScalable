/**
 * 
 */
package com.tcaptcha.captcha;

/**
 * define meta info of a captcha generator 
 * 
 * 
 * 
 * 
 */
public interface CaptchaGeneratorInfo {
	
	public String getDescription();
	
	public String getShortName();
	
	public String getModeCode();
	
	public String getMimeType();
	
}
