/**
 * 
 */
package com.tcaptcha.model;

import java.io.Serializable;
import java.util.Map;
import java.util.Properties;

import com.tcaptcha.GlobalConfiguration;

/**
 * Data model to describe Captcha parameter memory storage.
 * 
 * 
 * 
 */
public class CaptchaInfo implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 5509547807559834350L;

	/**
	 * session key
	 */
	private String key;

	/**
	 * captcha code
	 */
	private String code;

	/**
     * 
     */
	private String ip;

	/**
	 * captcha mode
	 */
	private String mode;
	
	/**
	 * captcha hint text
	 */
	private String hint;

	/**
     * 
     */
	private boolean expiredForImage;

	/**
	 * optional properties
	 */
	private Properties config;

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getIp() {
		return ip;
	}

	public void setIp(String ip) {
		this.ip = ip;
	}

	public CaptchaInfo(String code, String ip) {
		super();
		this.code = code;
		this.ip = ip;
	}

	/**
	 * factory method to create instance
	 * 
	 * @param code
	 * @param ip
	 * @param mode
	 * @param params
	 * @return
	 */
	public static CaptchaInfo build(String code, String ip,
			String mode, Map<String, String> params) {
		CaptchaInfo instance = new CaptchaInfo(code, ip);
		instance.setMode(mode);

		Properties prop = new Properties();
		if (params != null) {
			for (String key : params.keySet()) {
				prop.setProperty(key, String.valueOf(params.get(key)));
			}
		}
		instance.setConfig(prop);

		return instance;
	}

	/**
	 * factory method to create instance
	 * 
	 * @param code
	 * @param ip
	 * @param params
	 * @return
	 */
	public static CaptchaInfo build(String code, String ip,
			Map<String, String> params) {
		CaptchaInfo instance = new CaptchaInfo(code, ip);
		instance.setMode(GlobalConfiguration.get("tcaptcha.defaultmode"));

		Properties prop = new Properties();
		if (params != null) {
			for (String key : params.keySet()) {
				prop.setProperty(key, String.valueOf(params.get(key)));
			}
		}
		instance.setConfig(prop);

		return instance;
	}

	public Properties getConfig() {
		if (this.config == null) {
			this.config = new Properties();
		}
		return config;
	}

	public void setConfig(Properties prop) {
		this.config = prop;
	}

	/**
	 * @return the key
	 */
	public String getKey() {
		return key;
	}

	/**
	 * @param key
	 *            the key to set
	 */
	public void setKey(String key) {
		this.key = key;
	}

	/**
	 * @return the expired
	 */
	public boolean isExpiredForImage() {
		return expiredForImage;
	}

	/**
	 * @param expired
	 *            the expired to set
	 */
	public void setExpiredForImage(boolean expired) {
		this.expiredForImage = expired;
	}

	public String getMode() {
		return mode;
	}

	public void setMode(String mode) {
		this.mode = mode;
	}

	public String getHint() {
		return hint;
	}

	public void setHint(String hint) {
		this.hint = hint;
	}

}
