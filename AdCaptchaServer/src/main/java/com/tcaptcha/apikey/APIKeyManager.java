/**
 * 
 */
package com.tcaptcha.apikey;

import java.util.List;

/**
 * API Manager, manage api_key. API Key is design to control the accessibility
 * of captcha generator. Only registered application with valid api key could
 * get captcha key. Also api registion can be turned off.
 * 
 * 
 * 
 * 
 */
public interface APIKeyManager {

	/**
	 * test whether a key is valid.
	 * 
	 * @param apikey
	 * @return appName, null if invalid apikey
	 */
	public String getApp(String apikey);

	/**
	 * register a new key, return a generated app key
	 * 
	 * @param appName
	 * @return app_key
	 */
	public String register(String appName);

    /**
     * retrive all apikey records.
     * @return list of entries.
     */
	public List<ApiKeyEntry> list();

    /**
     * delete specified apikey item.
     * @param id id to delete
     */
	public void delete(long id);

}
