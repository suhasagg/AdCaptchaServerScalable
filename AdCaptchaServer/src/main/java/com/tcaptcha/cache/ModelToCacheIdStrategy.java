/**
 * 
 */
package com.tcaptcha.cache;

import com.tcaptcha.model.CaptchaInfo;

/**
 * 
 * 
 * 
 */
public interface ModelToCacheIdStrategy {
	
	public String toCacheId(CaptchaInfo model);

}
