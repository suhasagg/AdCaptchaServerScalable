/**
 * 
 */
package com.tcaptcha.captcha;

import java.io.IOException;
import java.io.OutputStream;

import com.tcaptcha.model.CaptchaInfo;

/**
 * Generate captcha images
 * 
 * 
 *
 */
public interface CaptchaGenerator {
	
	/**
	 * create images and write to an outputstream
	 * @param outputStream stream to write
	 * @throws IOException
	 */
	public void generate(OutputStream outputStream) throws IOException;
	
	
	/**
	 * specify data and configuration on the captcha
	 * @param model
	 */
	public void setModel(CaptchaInfo model);

}
