/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.tcaptcha.captcha.plaintext;

import java.util.HashMap;
import java.util.Map;

import org.apache.commons.lang.StringUtils;

import com.tcaptcha.GlobalConfiguration;
import com.tcaptcha.captcha.TicketGenerator;
import com.tcaptcha.captcha.simple.SimpleImageTicketGenerator;
import com.tcaptcha.util.RandomCode;

/**
 *
 * 
 * 
 *
 */
public class PlainTextTicketGenerator implements TicketGenerator {

	private static PlainTextTicketGenerator INSTANCE = new PlainTextTicketGenerator();
	
	 public static PlainTextTicketGenerator getInstance() {
			return INSTANCE ;
		}

	
    @Override
    public String getCode(Map<String, String> config) {
        config = config == null ? new HashMap<String, String>() : config;

        String lengthAsString = config.get("length");
        if (!StringUtils.isNumeric(lengthAsString)) {
            lengthAsString =GlobalConfiguration.get("tcaptcha.defaultCodeLength");
        }
        int length = Integer.parseInt(lengthAsString);
        length = length > 8 ? 8 : length;
        return "0"+"r"+RandomCode.get(length);
    }

    @Override
    public String getHint() {
        return GlobalConfiguration.get("tcaptcha.plainCaptchaHint");
    }
}
