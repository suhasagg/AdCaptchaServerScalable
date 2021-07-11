/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.tcaptcha.captcha.plaintext;

import com.tcaptcha.GlobalConfiguration;
import com.tcaptcha.captcha.AbstractCaptchaGeneratorProvider;
import com.tcaptcha.captcha.CaptchaGenerator;
import com.tcaptcha.captcha.simple.SimpleImageGeneratorProvider;

/**
 *
 * 
 * 
 *
 */
public class PlainTextGeneratorProvider extends AbstractCaptchaGeneratorProvider {

	
    private static PlainTextGeneratorProvider INSTANCE = new PlainTextGeneratorProvider();
	
	public static PlainTextGeneratorProvider getInstance() {
		return INSTANCE ;
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
        return GlobalConfiguration.get("tcaptcha.plainmode");
    }

    @Override
    public String getShortName() {
        return "Simple Plain Text";
    }

    @Override
    public CaptchaGenerator get() {
        return new PlainTextGenerator();
    }

}
