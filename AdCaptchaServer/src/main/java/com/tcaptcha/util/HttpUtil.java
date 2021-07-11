/*
 *
 */

package com.tcaptcha.util;

import java.io.IOException;
import java.io.Writer;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 *
 * 
 * 
 *
 */
public class HttpUtil {
    
	@SuppressWarnings("unchecked")
	public static Map<String, String> requestParamsToMap(HttpServletRequest request){
		Map<String, String> configParams = new HashMap<String, String>();
		Enumeration<String> paramNames = request.getParameterNames();
		while (paramNames.hasMoreElements()) {
			String paramName = paramNames.nextElement();
			configParams.put(paramName, request.getParameter(paramName));
		}
		return configParams;
	}

    public static void writeResponse(ServletResponse response, String content)
            throws IOException{
        Writer out = response.getWriter();
        out.write(content);
        out.flush();
        out.close();
    }

    public static String getContentTypeByAlt(String alt) {
		if (alt != null) {
			if (alt.equalsIgnoreCase("JSON")) {
				return HttpContentType.APPLICATION_JSON;
			}

			if (alt.equalsIgnoreCase("XML")) {
				return HttpContentType.APPLICATION_XML;
			}

			
		}

		return HttpContentType.TEXT_PLAIN;

	}

    public static void noCache(HttpServletResponse resp){
        resp.setHeader("Cache-Control", "must-revalidate,no-cache,no-store");
    }
    
}
