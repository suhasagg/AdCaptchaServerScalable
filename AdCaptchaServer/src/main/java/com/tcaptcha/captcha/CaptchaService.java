/*
 *
 */
package com.tcaptcha.captcha;

import java.io.IOException;
import java.io.OutputStream;

import com.tcaptcha.model.CaptchaInfo;

/**
 *
 * 
 */
public interface CaptchaService {

    public TicketGenerator getTicketGeneratorByMode(String mode);
    
    public CaptchaInfo generateDefaultModel(String ip);

    public boolean isCacheEnabled();

    public boolean isCacheSupported(CaptchaGenerator gen);
    
    public String getMimeTypeByModel(CaptchaInfo model);
    
    public CaptchaGenerator getGeneratorByMode(CaptchaInfo model);
    
    public void writeCaptchaFromGenerator(CaptchaInfo model,
            OutputStream out) throws IOException;

    public void writeCaptchaFromGeneratorAndUpdateCache(CaptchaInfo model,
            OutputStream out) throws IOException;

    public void writeCaptchaFromCache(CaptchaInfo model,
            OutputStream out) throws IOException;

    public void writeCaptcha(CaptchaInfo model,
            OutputStream out) throws IOException;

    public void writeCaptchaFromGenerator(CaptchaGenerator gen, CaptchaInfo model,
            OutputStream out) throws IOException;

    public void writeCaptchaFromGeneratorAndUpdateCache(CacheableCaptchaGenerator gen, CaptchaInfo model,
            OutputStream out) throws IOException;

    public void writeCaptchaFromCache(CacheableCaptchaGenerator gen, CaptchaInfo model,
            OutputStream out) throws IOException;

    public void writeCaptcha(CaptchaGenerator gen, CaptchaInfo model,
            OutputStream out) throws IOException;
}
