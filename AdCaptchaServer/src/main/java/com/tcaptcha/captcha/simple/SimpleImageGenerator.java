/**
 *
 */
package com.tcaptcha.captcha.simple;

import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Properties;
import java.util.Random;

import javax.imageio.ImageIO;

import org.apache.commons.io.IOUtils;
import org.apache.commons.io.output.TeeOutputStream;
import org.apache.log4j.Logger;

import com.tcaptcha.GlobalConfiguration;
import com.tcaptcha.cache.CacheItemIdentifier;
import com.tcaptcha.cache.CacheManager;
import com.tcaptcha.captcha.CacheableCaptchaGenerator;
import com.tcaptcha.dao.CaptchaDAOImpl;
import com.tcaptcha.model.CaptchaInfo;
import com.tcaptcha.util.StringUtil;


/**
 * Advertisement Captcha Generator
 * This is a stateful implementation.
 * Creates Advertisement Captcha from static Advertisement Captcha Images - Generated by Advertisement Captcha Image Generation APIs
 * 
 * @author Suhas Aggarwal
 */
public class SimpleImageGenerator implements CacheableCaptchaGenerator {

	private final Logger logger = Logger.getLogger(SimpleImageGenerator.class);

	/**
     * 
     */
	private CacheManager cacheManager;

	
	/**
	 * key code model
	 */
	protected CaptchaInfo model;
	/**
	 * code to show on image.
	 */
	protected String code;
	
	/**
	 * Image Id
	 */
	protected String id;
	
	
	private final Random rand = new Random();

	public SimpleImageGenerator() {
	}

	
	protected BufferedImage createImage() {
	
		// Read static Advertisement Captcha Images Generated from Advertisement Captcha Image Generation APIs
		 BufferedImage image = null;
		 
		 
		 byte[] imageInByte = CaptchaDAOImpl.getInstance().getImageById(getId());
      //   String path =GlobalConfiguration.get("tcaptcha.imagedirectorypath");
	  
	   //Path of Advertisement Captcha Image Directory
	     
		 InputStream in = new ByteArrayInputStream(imageInByte);
		 try {
	            image = ImageIO.read(in);
	        } catch (IOException e) {
	            e.printStackTrace();
	        }
	     return image;
	
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.tcaptcha.service.CaptchaGenerator#generate()
	 */
	@Override
	public void generate(OutputStream outputStream) throws IOException {
		long s1 = System.currentTimeMillis();
		final BufferedImage image = this.createImage();
		ImageIO.write(image,GlobalConfiguration.get("tcaptcha.defaultImageFormat"),
				outputStream);
		long s2 = System.currentTimeMillis();
		logger.debug("New Image Generated In: " + (s2 - s1) + " ms.");
	}

	@Override
	public void setModel(CaptchaInfo model) {
		this.model = model;
		String codevalue=model.getCode();
		String[] parts = StringUtil.splitFirst(codevalue, "r");
		String part1 = parts[0]; // 004
		String part2 = parts[1];
		this.setCode(part2);                //Derive Advertisement Captcha code derived from Advertisement Captcha Solution Database
		logger.debug("Code: " + part2);
		this.setId(part1);                  //Derive Image Id
		logger.debug("Id: " + part1);
		this.setProperties(model.getConfig());
	}

	protected void setCode(String code) {
		this.code = code;

	}

	protected void setId(String Id) {
		this.id = Id;

	}
	
	protected void setProperties(Properties properties) {
		if (properties == null) {
			properties = new Properties();
		}
     }

	/**
	 * @return the code
	 */
	public String getCode() {
		return code;
	}

	
	public String getId() {
		return id;
	}
	
	
	

	@Override
	public void generateByCache(OutputStream outputStream, boolean update)
			throws IOException {
		if (cacheManager == null) {
			throw new IllegalAccessError("No cache manager specified");
		}

		CacheItemIdentifier cacheId = cacheManager.getCacheItemIdentifier(
				model, this);
		boolean hit = cacheManager.hitCache(cacheId);
		if (hit && !update) {
			long s1 = System.currentTimeMillis();
			InputStream in = cacheManager.getCacheInputStream(cacheId);
			IOUtils.copy(in, outputStream);
			in.close();
			long s2 = System.currentTimeMillis();
			logger.debug("Generated From Cache in " + (s2 - s1) + " ms.");
		} else {
			OutputStream cacheOutputStream = cacheManager
					.getCacheOutputStream(cacheId);
			OutputStream outputStreams = new TeeOutputStream(cacheOutputStream,
					outputStream);
			this.generate(outputStreams);
			outputStreams.flush();
			outputStreams.close();
		}
	}

	@Override
	public void setCacheManager(CacheManager cacheManager) {
		this.cacheManager = cacheManager;

	}

	@Override
	public String toCacheId(CaptchaInfo model) {
		Properties props = model.getConfig();
		String icode = model.getCode();
	
		String captchaCode = icode;
		String metadata = "###";
		
	
        StringBuffer sb = new StringBuffer();
		sb.append(captchaCode);
		sb.append("-");
		sb.append(metadata);		
		return sb.toString();
	}

}
