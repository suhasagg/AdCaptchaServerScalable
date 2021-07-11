/**
 * 
 */
package com.tcaptcha.captcha;

import java.io.IOException;
import java.io.OutputStream;

import com.tcaptcha.cache.CacheManager;
import com.tcaptcha.cache.ModelToCacheIdStrategy;

/**
 * 
 * 
 * 
 */
public interface CacheableCaptchaGenerator extends CaptchaGenerator, ModelToCacheIdStrategy {
	
	public void setCacheManager(CacheManager cacheManager);

	/**
	 * read cache and write to an outputstream
	 * @param outputStream
	 * @throws IOException
	 */
	public void generateByCache(OutputStream outputStream, boolean update) throws IOException;

}
