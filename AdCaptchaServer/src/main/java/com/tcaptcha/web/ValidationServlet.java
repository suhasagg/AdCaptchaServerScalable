/**
 * 
 */
package com.tcaptcha.web;

import java.io.IOException;
import java.io.PrintWriter;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
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
import com.tcaptcha.dao.CaptchaStatsDAOImpl;
import com.tcaptcha.model.CaptchaInfo;
import com.tcaptcha.model.ValidationResult;
import com.tcaptcha.repos.KeyCodeRepository;
import com.tcaptcha.repos.cache.ehcache.EhCacheKeyCodeRepository;
import com.tcaptcha.util.BeanConvertor;
import com.tcaptcha.util.HttpUtil;
import com.tcaptcha.util.StringUtil;

/**
 * Captcha Validation + Captcha Analytics 
 * 
 * 
 * 
 * @author Suhas Aggarwal
 */

public class ValidationServlet extends HttpServlet {

	/**
	 * 
	 */
	private static final long serialVersionUID = 2078864625353950980L;
	
	private Logger logger = Logger.getLogger(ValidationServlet.class);


	 public  KeyCodeRepository repos = EhCacheKeyCodeRepository.getInstance();
		
		public  APIKeyService apiKeyService = APIKeyServiceImpl.getInstance();
		
		public  CaptchaService captchaService= CaptchaServiceImpl.getInstance();



	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		service(req, resp);
	}

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		service(req, resp);
	}

	@Override
	public void service(ServletRequest req, ServletResponse resp)
			throws ServletException, IOException {
		PrintWriter out = resp.getWriter();
		
		String appName = null;
		// check api_key if enabled
		if (apiKeyService.isApiKeyEnabled()) {
            appName = apiKeyService.getAppName(req.getParameter("apikey"),
                    false);
            if(appName == null){
                out.write(GlobalConfiguration.get("tcaptcha.message.apikeyError"));
                return ;
            }
        }
		
		if (GlobalConfiguration.getBoolean("tcaptcha.checkip"))
		{
		if(StringUtils.isEmpty(req.getParameter("ip"))){
			out.write(GlobalConfiguration
					.get("tcaptcha.message.notEnoughArgs"));
			out.close();
			return;
		}
		}
		
		if(StringUtils.isEmpty(req.getParameter("key"))){
			out.write("false");
			out.close();
			return;
		}
		
		
		String key = req.getParameter("key");
		String code = req.getParameter("code");
	//	String ip = req.getParameter("ip");
		Date date = new Date();
		SimpleDateFormat sdf = new SimpleDateFormat("MM/dd/yyyy h:mm:ss a");
		String formattedDate = sdf.format(date);
		CaptchaInfo model = repos.get(key, true);
		if(model == null){
			out.write("false");
			out.close();
			logger.info("1 App:"+appName);
			return;
		}else{
			String scode = model.getCode();
			String[] parts =  StringUtil.splitFirst(scode, "r");
			String part1 = parts[0]; // 004
			String part2 = parts[1];
			String storedCode=part2;
			logger.info("stored code :"+storedCode);
			/*
			String path =GlobalConfiguration.get("tcaptcha.analyticsdirectorypath");
			FileWriter fstream = new FileWriter(path,true);
			BufferedWriter out1 = new BufferedWriter(fstream);
			*/
			if(StringUtils.equalsIgnoreCase(storedCode, code)){
				HttpUtil.writeResponse(resp, BeanConvertor.serialize(
	                    new ValidationResult(true, null), req.getParameter("alt")));
				 
				logger.info("0 App:"+appName);
				
					  // Create file 
					 /*
					  out1.write(part1+"#true"+"#"+formattedDate+"\n");
					 //Close the output stream
					  out1.close();
					 */ 
				if(StringUtils.equalsIgnoreCase(model.getMode(),"0")) 
				CaptchaStatsDAOImpl.getInstance().insertCaptchaStatsForCode(key,part1,part2,true,true);
			
			}else{
				HttpUtil.writeResponse(resp, BeanConvertor.serialize(
	                    new ValidationResult(false, null), req.getParameter("alt")));
				logger.info("1 App:"+appName);
				/*
				out1.write(part1+"#false"+"#"+formattedDate+"\n");
				  //Close the output stream
				out1.close();
			    */
				if(StringUtils.equalsIgnoreCase(model.getMode(),"0"))
				CaptchaStatsDAOImpl.getInstance().insertCaptchaStatsForCode(key,part1,part2,false,true);
					
				
			}
			
			out.close();
			return;
		}
		
		
	}
	
	

}
