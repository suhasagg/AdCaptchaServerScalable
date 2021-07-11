/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.tcaptcha.cache;

import com.tcaptcha.model.CaptchaInfo;

/**
 *
 * 
 * 
 */
public interface CacheIdToModelStrategy{

    public CaptchaInfo toModel(String cacheIdPresentation);

}
