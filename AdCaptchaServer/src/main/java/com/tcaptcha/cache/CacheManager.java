/**
 * 
 */
package com.tcaptcha.cache;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import com.tcaptcha.model.CaptchaInfo;

/**
 * Cache management. Cache system provides functionality to store image files to
 * some sort of storage(dependent on implementation) and to retrive files as
 * stream.
 * 
 * 
 * 
 */
public interface CacheManager {

	/**
	 * Cache item name generation strategy
	 * 
	 * @param model the KeyCodeModel contains data in a single request
	 * @return an item name
	 */
	public CacheItemIdentifier getCacheItemIdentifier(CaptchaInfo model, ModelToCacheIdStrategy strategy);

	/**
	 * get an output stream to write cache item
	 * 
	 * @param itemname
	 * @return
	 * @throws IOException
	 */
	public OutputStream getCacheOutputStream(CacheItemIdentifier cacheId)
			throws IOException;

	/**
	 * get an input stream to retrive cached item
	 * 
	 * @param itemname
	 * @return
	 * @throws IOException
	 */
	public InputStream getCacheInputStream(CacheItemIdentifier cacheId)
			throws IOException;;

	/**
	 * test if an item exists in cache system
	 * 
	 * @param itemname
	 * @return
	 */
	public boolean hitCache(CacheItemIdentifier cacheId);

}
