/**
 * 
 */
package com.tcaptcha.web;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;

import com.tcaptcha.apikey.APIKeyManager;
import com.tcaptcha.apikey.ApiKeyEntry;
import com.tcaptcha.apikey.SQLDBAPIkeyManager;

/**
 * 
 * 
 * 
 */

public class ApiKeyManagerServlet extends HttpServlet {

	/**
	 * 
	 */
	private static final long serialVersionUID = -5979285924093269645L;


	private APIKeyManager apiKeyManager=SQLDBAPIkeyManager.getInstance();

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		if(StringUtils.equalsIgnoreCase(req.getParameter("action"), "del")){
			if(StringUtils.isNumeric(req.getParameter("id"))){
				apiKeyManager.delete(Long.parseLong(req.getParameter("id")));
			}
			resp.sendRedirect("./manager");
		} else {
			List<ApiKeyEntry> data = apiKeyManager.list();
			req.setAttribute("apikeys", data);
			req.getRequestDispatcher("/WEB-INF/manager.jsp").forward(req, resp);
		}
	}
	
	

}
