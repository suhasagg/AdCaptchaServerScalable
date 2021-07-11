/*
 *
 */

package com.tcaptcha.apikey;

import org.apache.commons.lang.StringUtils;

import com.tcaptcha.GlobalConfiguration;

/**
 *
 * 
 * 
 *
 */
public class APIKeyServiceImpl implements APIKeyService {

    
    private APIKeyManager apiKeyManager= SQLDBAPIkeyManager.getInstance();

   private static  APIKeyServiceImpl INSTANCE = new  APIKeyServiceImpl();
	
	public static  APIKeyServiceImpl getInstance() {
		return INSTANCE ;
	}
      
    @Override
    public boolean isApiKeyEnabled() {
        return Boolean.parseBoolean(GlobalConfiguration.get("tcaptcha.apikey.enableapikey"));
    }

    @Override
    public String getAppName(String apikey, boolean isLocal) {
        if(StringUtils.isNotEmpty(apikey)){
            return apiKeyManager.getApp(apikey);
        } else if(isLocal){
            return "localhost";
        } else {
            return null;
        }
    }

}
