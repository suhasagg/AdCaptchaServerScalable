/**
 * 
 */
package com.tcaptcha.captcha.simple;

import com.tcaptcha.GlobalConfiguration;
import com.tcaptcha.captcha.AbstractCaptchaGeneratorProvider;
import com.tcaptcha.captcha.CaptchaGenerator;

/**
 * 
 * 
 * 
 */
public class SimpleImageGeneratorProvider extends
		AbstractCaptchaGeneratorProvider {

	
	
    private static SimpleImageGeneratorProvider INSTANCE = new SimpleImageGeneratorProvider();
	
	public static SimpleImageGeneratorProvider getInstance() {
		return INSTANCE ;
	}

	
	@Override
	public CaptchaGenerator get() {
		return new SimpleImageGenerator();
	}

	@Override
	public String getDescription() {
		return "";
	}

	@Override
	public String getMimeType() {
		return "image/" +GlobalConfiguration.get("tcaptcha.defaultImageFormat");
	}

	@Override
	public String getModeCode() {
		return GlobalConfiguration.get("tcaptcha.simplemode");
	}

	@Override
	public String getShortName() {
		return "SimpleImage";
	}

}
