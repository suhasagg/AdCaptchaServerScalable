package com.tcaptcha.dao;

public interface CaptchaStatsDAO {

	public void insertCaptchaStatsForKey(String key, String imageId);
	
	public void updateCaptchaStatsForKey(String key, String code, boolean success, boolean expired);
	
	public void insertCaptchaStatsForCode(String key, String imageId, String code, boolean success, boolean expired);
	
}
