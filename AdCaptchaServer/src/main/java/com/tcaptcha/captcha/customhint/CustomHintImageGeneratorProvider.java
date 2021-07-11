/**
 * 
 */
package com.tcaptcha.captcha.customhint;

import com.tcaptcha.GlobalConfiguration;
import com.tcaptcha.captcha.AbstractCaptchaGeneratorProvider;
import com.tcaptcha.captcha.math.MathImageGeneratorProvider;

/**
 * 
 * 
 * 
 */
public class CustomHintImageGeneratorProvider extends AbstractCaptchaGeneratorProvider {

    private static CustomHintImageGeneratorProvider INSTANCE = new CustomHintImageGeneratorProvider();
	
	public static CustomHintImageGeneratorProvider getInstance() {
		return INSTANCE ;
	}
	
	
	@Override
	public CustomHintImageGenerator get() {
		return new CustomHintImageGenerator();
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
		return GlobalConfiguration.get("tcaptcha.customHintmode");
	}

	@Override
	public String getShortName() {
		return "customHintImage";
	}

}
