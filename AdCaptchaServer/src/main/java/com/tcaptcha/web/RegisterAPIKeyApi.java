package com.tcaptcha.web;

import com.tcaptcha.apikey.APIKeyManager;
import com.tcaptcha.apikey.SQLDBAPIkeyManager;

public class RegisterAPIKeyApi {

	/**
	 * @param args
	 */
    private static final long serialVersionUID = 1L;
	
	
	public static APIKeyManager apikeyManager = SQLDBAPIkeyManager.getInstance(); 
    

    
	
	public static String RegisterAPIKey(String appname) {
			
		if(appname!=null && appname.equals("")){
			String key = apikeyManager.register(appname);
			return key;
		}else{
			return "Not enough parameter";
		}
	

}

}