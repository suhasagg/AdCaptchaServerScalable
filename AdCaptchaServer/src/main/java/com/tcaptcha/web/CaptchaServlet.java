/**
 * 
 */
package com.tcaptcha.web;

import java.io.IOException;
import java.util.Random;

import javax.servlet.ServletException;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;

import com.tcaptcha.GlobalConfiguration;
import com.tcaptcha.apikey.APIKeyService;
import com.tcaptcha.apikey.APIKeyServiceImpl;
import com.tcaptcha.captcha.CaptchaService;
import com.tcaptcha.captcha.CaptchaServiceImpl;
import com.tcaptcha.model.CaptchaInfo;
import com.tcaptcha.repos.KeyCodeRepository;
import com.tcaptcha.repos.cache.ehcache.EhCacheKeyCodeRepository;


/**
 * Servlet handles client request, send image to clients.
 * 
 * 
 * 
 * 
 */

public class CaptchaServlet extends HttpServlet {

    /**
     *
     */
    private static final long serialVersionUID = 1105216016439615797L;
    private static final Logger logger = Logger.getLogger(CaptchaServlet.class);
    
    public  KeyCodeRepository repos = EhCacheKeyCodeRepository.getInstance();
	
	public  APIKeyService apiKeyService = APIKeyServiceImpl.getInstance();
	
	public  CaptchaService captchaService= CaptchaServiceImpl.getInstance();

    
    protected Random rand = new Random();

    /**
     * Write image to response use configuration stored by last request.
     * The session is identified by sessionkey in url.
     *
     * @param req
     * @param res
     * @throws ServletException
     * @throws IOException
     */
    public void handleRequestWithSessionKey(HttpServletRequest req, HttpServletResponse res)
            throws ServletException, IOException {
        String key = req.getPathInfo().substring(1);
        CaptchaInfo model = repos.get(key, false);

        if (model == null) {
            res.sendError(HttpServletResponse.SC_NOT_FOUND,
                   GlobalConfiguration.get("tcaptcha.message.expireOrInvalid"));
            return;

        } else if (model.isExpiredForImage()) {
            //remove such key
            repos.get(key, true);
            res.sendError(HttpServletResponse.SC_NOT_FOUND,
                   GlobalConfiguration.get("tcaptcha.message.expireOrInvalid"));
            return;
        } else {

          
        	if (GlobalConfiguration.getBoolean("tcaptcha.checkip")) {
                if (!model.getIp().equals(req.getRemoteAddr())) {
                    res.sendError(HttpServletResponse.SC_FORBIDDEN,
                           GlobalConfiguration.get("tcaptcha.message.hostInvalid"));
                    logger.info("Request image from " + req.getRemoteAddr() + ", IP dismatches");
                    return;
                }
            }
          
            res.setContentType(captchaService.getMimeTypeByModel(model));
            captchaService.writeCaptcha(model, res.getOutputStream());

            model.setExpiredForImage(true);
            repos.put(key, model);
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
     */
    public void handleRequestWithoutSessionKey(HttpServletRequest req, HttpServletResponse res)
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

    @Override
    public void doGet(HttpServletRequest req, HttpServletResponse res)
            throws ServletException, IOException {
        if (StringUtils.isEmpty(req.getPathInfo().substring(1))) {
            handleRequestWithoutSessionKey(req, res);
        } else {
            handleRequestWithSessionKey(req, res);
        }


    }
}
