/**
 * 
 */
package com.tcaptcha.repos.cache.memcache;

import java.io.IOException;

import net.spy.memcached.AddrUtil;
import net.spy.memcached.MemcachedClient;

import org.apache.log4j.Logger;

import com.tcaptcha.GlobalConfiguration;
import com.tcaptcha.model.CaptchaInfo;
import com.tcaptcha.repos.KeyCodeRepository;
import com.tcaptcha.repos.cache.ehcache.EhCacheKeyCodeRepository;
import com.tcaptcha.util.StringUtil;

/**
 * 
 * 
 * 
 */

public class MemcachedKeyCodeRepository implements KeyCodeRepository {

    protected MemcachedClient memcache;

    protected Logger logger = Logger.getLogger(MemcachedKeyCodeRepository.class);

    public MemcachedKeyCodeRepository(){
        try {
            init();
        } catch (IOException ex) {
            logger.fatal(ex.getMessage(), ex);
        }

    }

    
   private static MemcachedKeyCodeRepository INSTANCE;
	
	public static MemcachedKeyCodeRepository getInstance() {
		 
		if(INSTANCE == null){
			  return new MemcachedKeyCodeRepository();
			 }
			  else
			 {
				  return INSTANCE ;
			 }
		
	}
    
    
    protected void init() throws IOException{
        memcache  = new MemcachedClient(AddrUtil.getAddresses(
               GlobalConfiguration.get("tcaptcha.mc.host")));
        
    }

	/* 
	 * 
	 */
	@Override
	public CaptchaInfo get(String key) {
        return this.get(key, true);
	}

	/* 
	 * 
	 */
	@Override
	public void put(String key, CaptchaInfo kcm) {
		memcache.set(key, 60*GlobalConfiguration.getInt("tcaptcha.expireminutes"), kcm);
		String[] parts =  StringUtil.splitFirst(kcm.getCode(), "r");
		String part1 = parts[0]; // 004
		String part2 = parts[1];
		logger.debug("Put in Memcached: " + key + " ImageId:"+part1+" Code:" + part2 );
	}

	@Override
	public CaptchaInfo get(String key, boolean removeOnGet) {
		CaptchaInfo kcm = (CaptchaInfo)memcache.get(key);
		String[] parts =  StringUtil.splitFirst(kcm.getCode(), "r");
		String part1 = parts[0]; // 004
		String part2 = parts[1];
		
		if(kcm != null){
            if(removeOnGet){
                memcache.delete(key);
            logger.debug("Removed From Memcached: " + key + " ImageId:"+part1 + " Code:" + part2);
            }
            return kcm;
        }else{
            return null;
        }
	}

}
