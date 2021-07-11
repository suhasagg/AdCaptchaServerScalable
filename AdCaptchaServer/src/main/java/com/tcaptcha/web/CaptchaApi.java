package com.tcaptcha.web;
import java.io.IOException;
import java.io.OutputStream;
import java.util.Random;

import org.apache.log4j.Logger;

import com.tcaptcha.cache.CacheManager;
import com.tcaptcha.captcha.CaptchaService;
import com.tcaptcha.captcha.CaptchaServiceImpl;
import com.tcaptcha.model.CaptchaInfo;
import com.tcaptcha.repos.KeyCodeRepository;
import com.tcaptcha.repos.cache.ehcache.EhCacheKeyCodeRepository;
import com.tcaptcha.repos.cache.memcache.MemcachedKeyCodeRepository;


public class CaptchaApi {

	/**
	 * @param args
	 */
	
	
	private static final long serialVersionUID = 1105216016439615797L;
    private static final Logger logger = Logger.getLogger(CaptchaServlet.class);
   
    public  CaptchaService captchaService = CaptchaServiceImpl.getInstance();
    
    public  KeyCodeRepository repos= EhCacheKeyCodeRepository.getInstance(); ;
    
    public CacheManager cacheMan;
    public static Random rand = new Random();
	
    public CaptchaApi() {
	}

    private static CaptchaApi INSTANCE = new CaptchaApi();
	
	public static CaptchaApi getInstance() {
		return INSTANCE ;
	}
	
	
	
	
	public static void main(String[] args) {
		// TODO Auto-generated method stub

	}


	 /**
     * Write Captcha Image to OutputStream supplied - Direct Api
     */
   
	public void CaptchaWithSessionKey(String key, OutputStream out) throws IOException
            {
 //       String key = req.getPathInfo().substring(1);
        CaptchaInfo model = repos.get(key, false);
       
        
        if (model == null) {
        	logger.info("Key was expired or never exists");
            return;

        } else if (model.isExpiredForImage()) {
            //remove such key
            repos.get(key, true);
            logger.info("Key was expired or never exists");
            return;
        } else {

           captchaService.writeCaptcha(model, out);

            model.setExpiredForImage(true);
            repos.put(key, model);
            return;
        }


    }


	
	/**
     * 
     * Write session key to cookie in addition if image request contains no sessionkey
     * (which means image url is requested directly).
     *
     * @param req
     * @param res
     * @throws ServletException
     * @throws IOException
     
    public void CaptchaWithoutSessionKey(HttpServletRequest req, HttpServletResponse res)
            throws ServletException, IOException {
        CaptchaInfo model = captchaService.generateDefaultModel(req.getRemoteAddr());
        String key = model.getKey();

        repos.put(key, model);

        Cookie cookie = new Cookie("tcaptchaticket", key);
        cookie.setDomain(req.getServerName());
        cookie.setMaxAge(20*60);
        res.addCookie(cookie);

        res.setContentType(captchaService.getMimeTypeByModel(model));
        captchaService.writeCaptcha(model, res.getOutputStream());

    }

*/



}
