/**
 * 
 */
package com.tcaptcha.web;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;

import javax.servlet.ServletException;
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
import com.tcaptcha.captcha.TicketGenerator;
import com.tcaptcha.model.CaptchaInfo;
import com.tcaptcha.model.Ticket;
import com.tcaptcha.repos.KeyCodeRepository;
import com.tcaptcha.repos.cache.ehcache.EhCacheKeyCodeRepository;
import com.tcaptcha.util.StringUtil;
import com.tcaptcha.util.UUIDGenerator;
import com.tcaptcha.util.Util;


/**
 * Servlet handles application request, receiving and storing the captcha
 * parameters
 * 
 * 
 * 
 * 
 */

public class TicketServlet extends HttpServlet {

	/**
     *
     */
	private static final long serialVersionUID = -6672696448785035920L;
	private static final Logger logger = Logger.getLogger(TicketServlet.class);
    
	 public  KeyCodeRepository repos = EhCacheKeyCodeRepository.getInstance();
		
		public  APIKeyService apiKeyService = APIKeyServiceImpl.getInstance();
		
		public  CaptchaService captchaService= CaptchaServiceImpl.getInstance();



	@SuppressWarnings("unchecked")
	@Override
	protected void service(HttpServletRequest req, HttpServletResponse res)
			throws ServletException, IOException {
		PrintWriter out = res.getWriter();
		String appName = null;

		// check api_key
		if (apiKeyService.isApiKeyEnabled()) {
			appName = apiKeyService.getAppName(req.getParameter("apikey"), Util
					.isLocalhost(req.getRemoteAddr()));
			if (appName == null) {
				out.write(GlobalConfiguration.get("tcaptcha.message.apikeyError"));
				return;
			}
		}

		Map<String, String> configParams = new HashMap<String, String>();
        
		String ip = null;
		
		if (GlobalConfiguration.getBoolean("tcaptcha.checkip")){
		if (!StringUtils.isEmpty(req.getParameter("ip"))) {
			ip = req.getParameter("ip");
		} else {
			out.write(GlobalConfiguration.get("tcaptcha.message.notEnoughArgs"));
			return;
		}
       }
        
		String mode = null;
		if (StringUtils.isNumeric(req.getParameter("mode"))) {
			mode = req.getParameter("mode");

		} else {
			mode =GlobalConfiguration.get("tcaptcha.defaultmode");
		}

		Enumeration<String> paramNames = req.getParameterNames();
		while(paramNames.hasMoreElements()){
			String paramName = paramNames.nextElement();
			configParams.put(paramName, req.getParameter(paramName));
		}
		
		TicketGenerator ticketGen = captchaService
				.getTicketGeneratorByMode(mode);
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
		
		res.setContentType(getContentTypeByAlt(req.getParameter("alt")));
		out.write(ticket.serialize(req.getParameter("alt")));

		out.flush();
		out.close();

		logger.info("Appname:" + appName);
	}
	
	private String getContentTypeByAlt(String alt) {
		if(alt != null){
			if(alt.equalsIgnoreCase("JSON")){
				return "application/json";
			}
			
			if(alt.equalsIgnoreCase("XML")){
				return "application/xml";
			}
			
		}
		
		return "text/plain";
		
	}

	private String getImageUrl(String key){
		StringBuffer sb = new StringBuffer();
		// remove "key" from the url to get servlet root path
		sb.append(getRandomHost());
		sb.append("captcha/");
		sb.append(key);
		return sb.toString();
	}
	
	//Generates a Ticket with the Captcha code solution - Code is extracted and stored in code parameter of Ticket
	
	private Ticket toTicket(CaptchaInfo model){
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

	protected String getRandomHost() {
		String[] hosts =GlobalConfiguration.getList("tcaptcha.hosts");
		return hosts[new Random().nextInt(hosts.length)];
	}

	@Override
	public void doGet(HttpServletRequest req, HttpServletResponse res)
			throws ServletException, IOException {
		service(req, res);
	}

	@Override
	public void doPost(HttpServletRequest req, HttpServletResponse res)
			throws ServletException, IOException {
		service(req, res);
	}
}
