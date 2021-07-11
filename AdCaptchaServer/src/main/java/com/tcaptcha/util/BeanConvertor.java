/**
 * 
 */
package com.tcaptcha.util;

import java.io.StringWriter;
import java.util.List;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.apache.log4j.Logger;
import org.simpleframework.xml.Serializer;
import org.simpleframework.xml.core.Persister;

/**
 * 
 * 
 * 
 */
public class BeanConvertor {
	
	private static Logger logger = Logger.getLogger(BeanConvertor.class);
	
	private BeanConvertor(){
		
	}

    public static String serialize(Object o, String type){
        if(type != null){
			if(type.equalsIgnoreCase("XML")){
				return BeanConvertor.toXML(o);
			}

			if(type.equalsIgnoreCase("JSON")){
				return BeanConvertor.toJSONObject(o);
			}

			
		}
		return o.toString();
    }
	
	public static String toXML(Object o){
		Serializer serializer = new Persister();
		StringWriter sw = new StringWriter();
		try {
			serializer.write(o, sw);
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
		}
		return sw.toString();
	}
	
	public static String toJSONObject(Object o){
		JSONObject json = JSONObject.fromObject(o);
		return json.toString();
	}
	
	public static String toJSONArray(List<?> data){
		JSONArray json = JSONArray.fromObject(data);
		return json.toString();
	}
	
	

}
