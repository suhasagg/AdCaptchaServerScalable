package com.tcaptcha.web;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;

import org.apache.log4j.Logger;

import com.tcaptcha.GlobalConfiguration;
import com.tcaptcha.apikey.APIKeyService;
import com.tcaptcha.captcha.CaptchaService;
import com.tcaptcha.captcha.CaptchaServiceImpl;
import com.tcaptcha.captcha.TicketGenerator;
import com.tcaptcha.model.CaptchaInfo;
import com.tcaptcha.model.Ticket;
import com.tcaptcha.repos.KeyCodeRepository;
import com.tcaptcha.repos.cache.ehcache.EhCacheKeyCodeRepository;
import com.tcaptcha.repos.cache.memcache.MemcachedKeyCodeRepository;
import com.tcaptcha.util.StringUtil;
import com.tcaptcha.util.UUIDGenerator;


public class TicketApi {

	/**
	 * 
	 */
	
	private static final long serialVersionUID = -6672696448785035920L;
	private static final Logger logger = Logger.getLogger(TicketServlet.class);
	
	public  KeyCodeRepository repos = EhCacheKeyCodeRepository.getInstance();
	
	public  APIKeyService apiKeyService;
	
	public  CaptchaService captchaService= CaptchaServiceImpl.getInstance();

	public TicketApi() {
	}

    private static TicketApi INSTANCE = new TicketApi();
	
	public static TicketApi getInstance() {
		return INSTANCE ;
	}
	
	/**
	 * Generates Ticket for Captcha
	 * 
	 * 
	 * 
	 */

	public Ticket getTicket() {

		/**
	     *
	     */
		    int id;
		    Random rndNumbers = new Random();
		    int i=rndNumbers.nextInt(1);	    
		    String appName = "SampleApp";
   //       String mode = Integer.toString(i);
		    String mode = Integer.toString(i);
            // check api_key
		/*	
            if (apiKeyService.isApiKeyEnabled()) {
				appName = apiKeyService.getAppName((apikey), false);
				if (appName == null) {
					logger.info("Invalid Apikey, check input or contact with administrator");
				    return null;
				}
			}
      */
			Map<String, String> configParams = new HashMap<String, String>();

			

			
			
			
			TicketGenerator ticketGen = captchaService.getTicketGeneratorByMode(mode);
			String code = ticketGen.getCode(configParams);
		//	logger.info("code:"+code);
			String hint = ticketGen.getHint();

			String key = UUIDGenerator.generate(code);
			// String key = "0"+code;

			// PUSH DATA TO A GLOBAL TABLE
			CaptchaInfo model = CaptchaInfo.build(code, "127.0.0.1", mode,
					configParams);
			model.setKey(key);
			model.setHint(hint);
			repos.put(key, model);

			//Code including id+ marker 'r'+ Solution code is used to build Captcha Model
			
			Ticket ticket = toTicket(model);
			
			/*
			res.setContentType(getContentTypeByAlt(req.getParameter("alt")));
			out.write(ticket.serialize(req.getParameter("alt")));

			out.flush();
			out.close();
            */
			logger.info("Acessed by:"+appName);
		    return ticket;
	
	}
		
		
		public static String getImageUrl(String key){
			StringBuffer sb = new StringBuffer();
			// remove "key" from the url to get servlet root path
			sb.append(getRandomHost());
			sb.append("captcha/");
			sb.append(key);
			return sb.toString();
		}
		
		//Generates a Ticket with the Captcha code solution - Code is extracted and stored in code parameter of Ticket
		
		public Ticket toTicket(CaptchaInfo model){
			Ticket ticket = new Ticket();
			String icode = model.getCode();
			String[] parts = StringUtil.splitFirst(icode, "r");
			String part1 = parts[0]; // 004
			String part2 = parts[1];
			logger.info("code:"+part2);
			ticket.setUrl(getImageUrl(model.getKey()));
			ticket.setCode(part2);
			ticket.setKey(model.getKey());
			ticket.setType(captchaService.getMimeTypeByModel(model));
			ticket.setHint(model.getHint());
			
			return ticket;
		}

		public static String getRandomHost() {
			String[] hosts = GlobalConfiguration.getList("tcaptcha.hosts");
			return hosts[new Random().nextInt(hosts.length)];
		}

		
	}


