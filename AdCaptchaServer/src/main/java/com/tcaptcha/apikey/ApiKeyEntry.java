/**
 * 
 */
package com.tcaptcha.apikey;

/**
 * A POJO describe the structure of apikey item.
 *
 * 
 * 
 * 
 */
public class ApiKeyEntry {

    /**
     * database record id.
     *
     */
	public Long id;

    /**
     * application name.
     */
	public String appName;

    /**
     * api key.
     */
	public String apikey;

    /**
     * getter for id.
     * @return
     */
	public Long getId() {
		return id;
	}

    /**
     * setter for id.
     * @param id
     */
	public void setId(Long id) {
		this.id = id;
	}

    /**
     * getter for appname.
     * @return
     */
	public String getAppName() {
		return appName;
	}

    /**
     * setter for appname.
     * @param appName
     */
	public void setAppName(String appName) {
		this.appName = appName;
	}

    /**
     * getter for apikey.
     * @return
     */
	public String getApikey() {
		return apikey;
	}
    /**
     * setter for apikey
     * @param apikey
     */
	public void setApikey(String apikey) {
		this.apikey = apikey;
	}

}
