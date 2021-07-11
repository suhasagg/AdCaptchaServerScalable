package com.tcaptcha.web;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;

import com.tcaptcha.GlobalConfiguration;
import com.tcaptcha.dao.CaptchaStatsDAOImpl;
import com.tcaptcha.model.CaptchaInfo;
import com.tcaptcha.repos.KeyCodeRepository;
import com.tcaptcha.repos.cache.ehcache.EhCacheKeyCodeRepository;
import com.tcaptcha.repos.cache.memcache.MemcachedKeyCodeRepository;
import com.tcaptcha.util.StringUtil;




public class ValidateApi {

	/**
	 * @param args
	 */
	/**
	 * 
	 */
	
	
	/**
	 * Captcha Validation + Captcha Analytics 
	 * 
	 * 
	 * 
	 * @author Suhas Aggarwal
	 */
	
		/**
		 * 
		 */
		private static final long serialVersionUID = 2078864625353950980L;
		
		private static final Logger logger = Logger.getLogger(ValidationServlet.class);

		public  KeyCodeRepository repos = EhCacheKeyCodeRepository.getInstance();
		
		public ValidateApi() {
		}

	    private static ValidateApi INSTANCE = new ValidateApi();
		
		public static ValidateApi getInstance() {
			return INSTANCE ;
		}
		

		
		

		public boolean validateCaptcha(String key, String code) throws IOException {
			
			
			String appName = "SampleApp";
			// check api_key if enabled
		/*
			if (apiKeyService.isApiKeyEnabled()) {
	            appName = apiKeyService.getAppName(key,false);
	            if(appName == null){
	            	logger.info("Invalid Apikey, check input or contact with administrator");
	                return false;
	            }
	        }
	*/		
						
			if(StringUtils.isEmpty(key))
				return false;
						
			
			Date date = new Date();
			SimpleDateFormat sdf = new SimpleDateFormat("MM/dd/yyyy h:mm:ss a");
			String formattedDate = sdf.format(date);
			CaptchaInfo model = repos.get(key, true);
			if(model == null){				
				logger.info("1 App:"+appName);
				return false;
			}else{
				String scode = model.getCode();
				String[] parts =  StringUtil.splitFirst(scode, "r");
				String part1 = parts[0]; // 004
				String part2 = parts[1];
				String storedCode=part2;
				logger.info("stored code :"+storedCode);
				String path =GlobalConfiguration.get("tcaptcha.analyticsdirectorypath");
				FileWriter fstream = new FileWriter(path,true);
				BufferedWriter out1 = new BufferedWriter(fstream);
				if(StringUtils.equalsIgnoreCase(storedCode, code)){
					
					 
					logger.info("0 App:"+appName);
					
						  // Create file 
						  /*
						  out1.write(part1+"#true"+"#"+formattedDate+"\n");
						  //Close the output stream
						  out1.close();
						  */
					if(StringUtils.equalsIgnoreCase(model.getMode(),"0"))
					CaptchaStatsDAOImpl.getInstance().insertCaptchaStatsForCode(key,part1,part2,true,true);	
					
					return true;  
				
				
				}else{
					
					logger.info("1 App:"+appName);
					/*
					out1.write(part1+"#false"+"#"+formattedDate+"\n");
					  //Close the output stream
					out1.close();
					*/
					if(StringUtils.equalsIgnoreCase(model.getMode(),"0"))
					CaptchaStatsDAOImpl.getInstance().insertCaptchaStatsForCode(key,part1,part2,false,true);	
					return false;  
				}
				
				
			}
			
			
		}
		
		

	}



