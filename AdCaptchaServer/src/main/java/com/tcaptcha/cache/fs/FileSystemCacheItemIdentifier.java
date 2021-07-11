/**
 * 
 */
package com.tcaptcha.cache.fs;

import java.io.File;

import com.tcaptcha.GlobalConfiguration;
import com.tcaptcha.cache.CacheItemIdentifier;
import com.tcaptcha.model.CaptchaInfo;

/**
 * 
 *
 */
public class FileSystemCacheItemIdentifier extends CacheItemIdentifier {

    public FileSystemCacheItemIdentifier(){}

	public FileSystemCacheItemIdentifier(CaptchaInfo model) {
		super(model);
	}

	/* (non-Javadoc)
	 * @see com.classicning.tcaptcha.cache.CacheItemIdentifier#getCacheItemId()
	 */
	@Override
	public Object getCacheItemId() {
		String mode = model.getMode();
		
		String cacheDirPath = GlobalConfiguration.get("tcaptcha.cache.filecachepath")
				+ "/" + mode;
		
		File cacheDir = new File(cacheDirPath);
		if(!cacheDir.exists() || !cacheDir.isDirectory()){
			cacheDir.mkdirs();
		}
		
		String cacheFileName = strategy.toCacheId(model);
		return new File(cacheDir, cacheFileName);
	}

    

}
