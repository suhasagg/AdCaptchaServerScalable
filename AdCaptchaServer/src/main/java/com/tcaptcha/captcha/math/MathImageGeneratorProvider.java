/**
 * 
 */
package com.tcaptcha.captcha.math;

import com.tcaptcha.GlobalConfiguration;
import com.tcaptcha.captcha.AbstractCaptchaGeneratorProvider;
import com.tcaptcha.captcha.CaptchaGenerator;
import com.tcaptcha.captcha.plaintext.PlainTextGeneratorProvider;

/**
 * 
 * 
 * 
 */
public class MathImageGeneratorProvider extends AbstractCaptchaGeneratorProvider
 {

    private static MathImageGeneratorProvider INSTANCE = new MathImageGeneratorProvider();
	
	public static MathImageGeneratorProvider getInstance() {
		return INSTANCE ;
	}

	@Override
    public CaptchaGenerator get() {
        return new MathImageGenerator();
    }	
	
	
	@Override
	public String getDescription() {
		return "";
	}

	@Override
	public String getMimeType() {
		 return "text/plain";
	}

	@Override
	public String getModeCode() {
		return GlobalConfiguration.get("tcaptcha.mathmode");
	}

	@Override
	public String getShortName() {
		return "Algebra Expression";
	}

}
