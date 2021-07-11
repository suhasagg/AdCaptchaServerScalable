/*
 *
 */

package com.tcaptcha.captcha.math;

import java.util.Map;
import java.util.Random;

import com.tcaptcha.GlobalConfiguration;
import com.tcaptcha.captcha.TicketGenerator;
import com.tcaptcha.captcha.simple.SimpleImageTicketGenerator;

/**
 *
 * 
 * 
 *
 */
public class MathImageTicketGenerator implements TicketGenerator {

	private static MathImageTicketGenerator INSTANCE = new MathImageTicketGenerator();
	
	 public static MathImageTicketGenerator getInstance() {
			return INSTANCE ;
		}

	
    @Override
    public String getHint() {
        return GlobalConfiguration.get("tcaptcha.mathCaptchaHint");
    }

	@Override
	public String getCode(Map<String, String> config) {
		return "0"+"r"+String.valueOf(new Random().nextInt(100));
	}

}
