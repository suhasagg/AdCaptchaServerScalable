package com.tcaptcha.web;

import java.io.IOException;
import java.io.PrintWriter;
import java.io.StringWriter;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.simpleframework.xml.Serializer;
import org.simpleframework.xml.core.Persister;

import com.tcaptcha.GlobalConfiguration;
import com.tcaptcha.apikey.APIKeyManager;
import com.tcaptcha.apikey.SQLDBAPIkeyManager;

/**
 * Servlet implementation class RegisterApiKeyServlet
 * 
 * 
 * 
 * 
 */

public class RegisterApiKeyServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	
	private APIKeyManager apikeyManager = SQLDBAPIkeyManager.getInstance(); 
    

    /**
     * @see HttpServlet#HttpServlet()
     */
    public RegisterApiKeyServlet() {
        super();
    }

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		PrintWriter out = response.getWriter();
		
		
		if(request.getParameter("appname")!=null && 
					!request.getParameter("appname").equals("")){
			String appName = request.getParameter("appname");
			String key = apikeyManager.register(appName);
			response.setContentType("application/xml");
			Serializer serializer = new Persister();
			StringWriter sw = new StringWriter();
			try {
				serializer.write(key, sw);
			} catch (Exception e) 
			{
				
			}
			out.write(sw.toString());
			out.flush();
			out.close();
		}else{
			out.write(GlobalConfiguration.get("tcaptcha.message.notEnoughArgs"));
			return ;
		}
	}

}
