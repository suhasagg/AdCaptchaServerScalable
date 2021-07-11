/**
 * 
 */
package com.tcaptcha.cache.fs;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Queue;
import java.util.concurrent.ConcurrentLinkedQueue;

import org.apache.log4j.Logger;

import com.tcaptcha.GlobalConfiguration;
import com.tcaptcha.cache.CacheItemIdentifier;
import com.tcaptcha.cache.CacheManager;
import com.tcaptcha.cache.ModelToCacheIdStrategy;
import com.tcaptcha.captcha.math.MathImageGeneratorProvider;
import com.tcaptcha.model.CaptchaInfo;

/**
 * 
 * A cache implementation based on file system.
 * 
 * 
 * 
 */
public class FileSystemCacheManager implements CacheManager {

	/**
	 * directory path to store cached items
	 */
	private static final String CACHE_FILE_PATH = GlobalConfiguration
			.get("tcaptcha.cache.filecachepath");

	private static final Logger logger = Logger.getLogger(FileSystemCacheManager.class);
	
	private static final Queue<File> fifoManager = new ConcurrentLinkedQueue<File>();

    private static FileSystemCacheManager INSTANCE = new FileSystemCacheManager();
	
	public static FileSystemCacheManager getInstance() {
		return INSTANCE ;
	}
	
	
	static {
		createCacheDirectory();
	}

	/**
	 * create the directory if not exists
	 */
	private static void createCacheDirectory() {
		if (GlobalConfiguration.getBoolean("tcaptcha.cache.enabled")) {
			File cachedir = new File(CACHE_FILE_PATH);
			if (!cachedir.exists()) {
				cachedir.mkdirs();
			}
		}
	}

	/**
	 * 
	 * retrieve cache
	 * 
	 *
	 *      
	 */
	@Override
	public InputStream getCacheInputStream(CacheItemIdentifier id)
			throws IOException {
		File cache = (File)id.getCacheItemId();
		logger.debug("Current Cache Volume " + fifoManager.size());
		FileInputStream is = new FileInputStream(cache);
		return is;

	}

	/*
	 * 
	 * 
	 * 
	 * 
	 * 
	 */
	@Override
	public OutputStream getCacheOutputStream(CacheItemIdentifier id)
			throws IOException {
		File cache = (File)id.getCacheItemId();
		testAndRelease(cache);
		FileOutputStream out = new FileOutputStream(cache);
		return out;
	}

	/**
	 * test if cache size full,
	 * 
	 * @param filename
	 */
	private synchronized void testAndRelease(File newFile) {
		if (fifoManager.size() == GlobalConfiguration
				.getInt("tcaptcha.cache.cachevolume")) {
			File oldFile = fifoManager.poll();
			if (oldFile.exists()) {
				logger.info("Deleted Cache File:"+oldFile.getAbsolutePath());
				oldFile.delete();
			}
		}
		fifoManager.offer(newFile);
	}

	/*
	 * 
	 * 
	 * 
	 */
	@Override
	public boolean hitCache(CacheItemIdentifier id) {
		File cache = (File) id.getCacheItemId();
		boolean exist = cache.exists();
		return exist;
	}

	@Override
	public CacheItemIdentifier getCacheItemIdentifier(
            CaptchaInfo model, ModelToCacheIdStrategy strategy) {
		CacheItemIdentifier cacheId = new FileSystemCacheItemIdentifier();
        cacheId.setCacheIdStrategy(strategy);
        cacheId.setModel(model);
        return cacheId;
	}

}
