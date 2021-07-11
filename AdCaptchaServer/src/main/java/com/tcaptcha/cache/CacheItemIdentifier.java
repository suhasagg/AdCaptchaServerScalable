/*
 *
 */
package com.tcaptcha.cache;


import com.tcaptcha.model.CaptchaInfo;

/**
 *
 * 
 * 
 *
 */
public abstract class CacheItemIdentifier {
	
	protected CaptchaInfo model;
	protected ModelToCacheIdStrategy strategy;

    public CacheItemIdentifier(){}

	public CacheItemIdentifier(CaptchaInfo model){
		this.model = model;
	}
	
	public CaptchaInfo getModel() {
		return model;
	}

	public void setModel(CaptchaInfo model) {
		this.model = model;
	}
	
	public void setCacheIdStrategy(ModelToCacheIdStrategy strategy){
		this.strategy = strategy;
	}
	
	public abstract Object getCacheItemId();
    
}
