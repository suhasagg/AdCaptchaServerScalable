package com.tcaptcha.repos;

import com.tcaptcha.model.CaptchaInfo;

/**
 * Key-Code map, update itself by a specified interval.
 * 
 * 
 *
 */
public interface KeyCodeRepository {
	
	/**
	 * put data to map
	 * @param key
	 * @param kcm
	 */
	public void put(String key, CaptchaInfo kcm);
	
	/**
	 * get data from map
	 * @param key
	 * @return
	 */
	public CaptchaInfo get(String key);
	
	/**
	 * get data from map
	 * @param key
	 * @param removeOnGet whether to remvoe key after get
	 * @return
	 */
	public CaptchaInfo get(String key, boolean removeOnGet);

}
