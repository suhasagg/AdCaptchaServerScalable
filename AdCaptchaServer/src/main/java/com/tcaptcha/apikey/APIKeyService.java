/*
 *
 */

package com.tcaptcha.apikey;

/**
 *
 * 
 *  
 */
public interface APIKeyService {

    public boolean isApiKeyEnabled();

    public String getAppName(String apikey, boolean isLocal);

    

}
