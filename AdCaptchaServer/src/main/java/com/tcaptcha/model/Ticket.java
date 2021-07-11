/**
 * 
 */
package com.tcaptcha.model;

import java.io.StringWriter;

import net.sf.json.JSONObject;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.simpleframework.xml.Element;
import org.simpleframework.xml.Root;
import org.simpleframework.xml.Serializer;
import org.simpleframework.xml.core.Persister;

/**
 * 
 * 
 * 
 */
@Root
public class Ticket {
	
	private static Logger logger = Logger.getLogger(Ticket.class);
	
	@Element
	private String url;
	
	@Element
	private String type;
	
	@Element
	private String key;
	
	@Element
	private String code;
	
	@Element
	private String hint;

	public String getUrl() {
		return url;
	}

	public void setUrl(String captchaUrl) {
		this.url = captchaUrl;
	}

	public String getType() {
		return type;
	}

	public void setType(String mimeType) {
		this.type = mimeType;
	}

	public String getKey() {
		return key;
	}

	public void setKey(String key) {
		this.key = key;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getHint() {
		return hint;
	}

	public void setHint(String hint) {
		this.hint = hint;
	}
	
	public String toLineSeparatedText(){
		String[] properties = new String[]{
				this.getUrl(),
				this.getType(),
				this.getKey(),
				this.getCode(),
				this.getHint()
		};
		return StringUtils.join(properties, System.getProperty("line.separator"));
	}
	
	public String serialize(String type){
		if(type != null){
			if(type.equalsIgnoreCase("XML")){
				Serializer serializer = new Persister();
				StringWriter sw = new StringWriter();
				try {
					serializer.write(this, sw);
				} catch (Exception e) {
					logger.error(e.getMessage(), e);
				}
				return sw.toString();
			}
			
			if(type.equalsIgnoreCase("JSON")){
				JSONObject json = JSONObject.fromObject(this);
				return json.toString();
			}
			
			
		}
		return toLineSeparatedText();
	}

}
