package com.tcaptcha.dao;


public interface CaptchaDAO {
	
	public byte[] getImageById(String id);
	
	public String getImageCode(String id);

}
