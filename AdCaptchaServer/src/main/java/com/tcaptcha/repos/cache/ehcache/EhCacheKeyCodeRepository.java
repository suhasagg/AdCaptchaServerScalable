/**
 * 
 */
package com.tcaptcha.repos.cache.ehcache;

import net.sf.ehcache.Cache;
import net.sf.ehcache.CacheManager;
import net.sf.ehcache.Element;

import org.apache.log4j.Logger;

import com.tcaptcha.GlobalConfiguration;
import com.tcaptcha.model.CaptchaInfo;
import com.tcaptcha.repos.KeyCodeRepository;
import com.tcaptcha.util.StringUtil;

/**
 * 
 * 
 * 
 */


public class EhCacheKeyCodeRepository implements KeyCodeRepository {

	private Cache cache;
	protected Logger logger = Logger.getLogger(EhCacheKeyCodeRepository.class);
    /**
     * 
     */
    private static EhCacheKeyCodeRepository INSTANCE;
	
	public static EhCacheKeyCodeRepository getInstance() {
		  
		 if(INSTANCE == null){
		  INSTANCE = new EhCacheKeyCodeRepository();
		  INSTANCE.init();
		  return INSTANCE;
		 }
		  else
		 {    INSTANCE.init();
			  return INSTANCE ;
		 }
	}


    /**
     * initialize properties.
     */
	public void init() {
        CacheManager cm = CacheManager.getInstance();
		cache = cm.getCache("keyCodeRepository");
		cache.getCacheConfiguration().setTimeToLiveSeconds(60 * GlobalConfiguration
				.getInt("tcaptcha.expireminutes"));
		cache.getCacheConfiguration().setTimeToIdleSeconds(60 * GlobalConfiguration
				.getInt("tcaptcha.expireminutes"));
	}

	/*
	 * 
	 * 
	 * 
	 */
	@Override
	public CaptchaInfo get(String key) {
		Element result =  cache.get(key);
		CaptchaInfo value = (CaptchaInfo)result.getValue();
		String[] parts =  StringUtil.splitFirst(value.getCode(), "r");
		String part1 = parts[0]; // 004
		String part2 = parts[1];
		logger.debug("Fetch from Ehcache: " + key + " ImageId:"+part1+" Code:" + part2 );
		if(result != null){
			cache.remove(key);
			return value;
		}else{
			return null;
		}
	}

	/*
	 * 
	 * 
	 * 
	 * 
	 */
	@Override
	public void put(String key, CaptchaInfo kcm) {
		Element element = new Element(key, kcm);
		cache.put(element);
		String[] parts =  StringUtil.splitFirst(kcm.getCode(), "r");
		String part1 = parts[0]; // 004
		String part2 = parts[1];
		logger.debug("Put in Ehcache: " + key + " ImageId:"+part1+" Code:" + part2 );
	}

    /**
     * get data from map.
     * @param key
     * @param removeOnGet remove key if set to ture
     * @return
     */
	@Override
	public CaptchaInfo get(String key, boolean removeOnGet) {
		Element result =  cache.get(key);
		CaptchaInfo value = (CaptchaInfo)result.getValue();
		String[] parts =  StringUtil.splitFirst(value.getCode(), "r");
		String part1 = parts[0]; // 004
		String part2 = parts[1];
		logger.debug("Fetch from Ehcache: " + key + " ImageId:"+part1+" Code:" + part2 );
		if(result != null){
			if(removeOnGet){
				cache.remove(key);
			}
			return value;
		}else{
			return null;
		}
	}

}
