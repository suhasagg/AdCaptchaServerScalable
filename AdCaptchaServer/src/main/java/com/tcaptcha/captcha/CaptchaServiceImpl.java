/**
 *
 */
package com.tcaptcha.captcha;


import java.io.IOException;
import java.io.OutputStream;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;

import com.tcaptcha.GlobalConfiguration;
import com.tcaptcha.cache.CacheManager;
import com.tcaptcha.cache.fs.FileSystemCacheManager;
import com.tcaptcha.captcha.customhint.CustomHintImageGenerator;
import com.tcaptcha.captcha.customhint.CustomHintImageGeneratorProvider;
import com.tcaptcha.captcha.customhint.CustomHintImageTicketGenerator;
import com.tcaptcha.captcha.math.MathImageGeneratorProvider;
import com.tcaptcha.captcha.math.MathImageTicketGenerator;
import com.tcaptcha.captcha.plaintext.PlainTextGeneratorProvider;
import com.tcaptcha.captcha.plaintext.PlainTextTicketGenerator;
import com.tcaptcha.captcha.simple.SimpleImageGeneratorProvider;
import com.tcaptcha.captcha.simple.SimpleImageTicketGenerator;
import com.tcaptcha.model.CaptchaInfo;
import com.tcaptcha.util.UUIDGenerator;


/**
 * 
 * This is the default implementation of stateless captcha service interface
 * 
 * 
 * 
 * 
 */
public class CaptchaServiceImpl implements CaptchaService {

	private static Logger logger = Logger.getLogger(CaptchaServiceImpl.class);

	private CaptchaServiceImpl() {
	}

	/**
	 * because captcha service is stateful, we use providers here
	 */
	protected static Map<String, AbstractCaptchaGeneratorProvider> generatorProviders = new HashMap<String, AbstractCaptchaGeneratorProvider>();

	/**
	 * stateless code generators
	 */
	
	private static CaptchaServiceImpl instance;
	
	public static SimpleImageGeneratorProvider simpleImageGeneratorProvider = SimpleImageGeneratorProvider.getInstance();
	
	public static CustomHintImageGeneratorProvider customHintImageGeneratorProvider = CustomHintImageGeneratorProvider.getInstance();
	
	public static PlainTextGeneratorProvider  plainTextGeneratorProvider  = PlainTextGeneratorProvider.getInstance();
	
	public static MathImageGeneratorProvider  mathImageGeneratorProvider = MathImageGeneratorProvider.getInstance();
	
    public static SimpleImageTicketGenerator simpleImageTicketGenerator = SimpleImageTicketGenerator.getInstance();
	
	public static CustomHintImageTicketGenerator customHintImageTicketGenerator = CustomHintImageTicketGenerator.getInstance();
	
	public static PlainTextTicketGenerator  plainTextTicketGenerator  = PlainTextTicketGenerator.getInstance();
	
	public static MathImageTicketGenerator  mathImageTicketGenerator = MathImageTicketGenerator.getInstance();
	
	
	
	public static CaptchaServiceImpl getInstance() {
	
		if(instance == null)
		{
		
		instance= new CaptchaServiceImpl();
			
		instance.setCacheMan(cacheMan);

		instance.getGeneratorProviders().put(simpleImageGeneratorProvider.getModeCode(),
				simpleImageGeneratorProvider);
		instance.getGeneratorProviders().put(mathImageGeneratorProvider.getModeCode(),
				mathImageGeneratorProvider);
		instance.getGeneratorProviders().put(customHintImageGeneratorProvider.getModeCode(),
				customHintImageGeneratorProvider);
        instance.getGeneratorProviders().put(plainTextGeneratorProvider.getModeCode(),
        		plainTextGeneratorProvider);

		instance.getTicketGenerators().put(simpleImageGeneratorProvider.getModeCode(),
				simpleImageTicketGenerator);
		instance.getTicketGenerators().put( mathImageGeneratorProvider.getModeCode(),
				mathImageTicketGenerator);
		instance.getTicketGenerators().put(customHintImageGeneratorProvider.getModeCode(),
				customHintImageTicketGenerator);
        instance.getTicketGenerators().put(plainTextGeneratorProvider.getModeCode(),
        		 plainTextTicketGenerator);
		
		instance.lockProviders();
		return instance;
		}
	
		else
			{
			return instance;
			}
	}
	
	protected static Map<String, TicketGenerator> ticketGenerators = new HashMap<String, TicketGenerator>();

	public void lockProviders() {
		generatorProviders = Collections.unmodifiableMap(generatorProviders);
		ticketGenerators = Collections.unmodifiableMap(ticketGenerators);
	}

	
	protected static CacheManager cacheMan = FileSystemCacheManager.getInstance();

	@Override
	public CaptchaInfo generateDefaultModel(String ip) {
		if (StringUtils.isEmpty(ip)) {
			return null;
		} else {
			String code = getTicketGeneratorByMode(
					GlobalConfiguration.get("tcaptcha.defaultmode")).getCode(null);

			String key = UUIDGenerator.generate(code, ip);
			CaptchaInfo model = new CaptchaInfo(code, ip);
			model.setKey(key);
			return model;
		}
	}

	@Override
	public boolean isCacheEnabled() {
		return (GlobalConfiguration.getBoolean("tcaptcha.cache.enabled"));
	}

	@Override
	public CaptchaGenerator getGeneratorByMode(CaptchaInfo model) {
		String mode = model.getMode();
		AbstractCaptchaGeneratorProvider provider = generatorProviders
				.get(mode);
		if (provider == null) {
			logger.warn("No such mode, use default mode fall back");
			provider = generatorProviders.get(GlobalConfiguration
					.get("tcaptcha.defaultmode"));
		}
		return provider.get();
	}

	public void setCacheMan(CacheManager cacheMan) {
		this.cacheMan = cacheMan;
	}

	@Override
	public TicketGenerator getTicketGeneratorByMode(String mode) {
		TicketGenerator codeGen = getTicketGenerators().get(mode);
		if (codeGen == null) {
			logger.warn("No such mode, use default mode fall back");
			codeGen = getTicketGenerators().get(
					GlobalConfiguration.get("tcaptcha.defaultmode"));
		}
		return codeGen;
	}

	@Override
	public void writeCaptchaFromGenerator(CaptchaGenerator generator,
			CaptchaInfo model, OutputStream out) throws IOException {
		generator.setModel(model);
		generator.generate(out);
		out.flush();
		out.close();
	}

	@Override
	public void writeCaptchaFromGeneratorAndUpdateCache(
			CacheableCaptchaGenerator generator, CaptchaInfo model,
			OutputStream out) throws IOException {
		generator.setModel(model);
		generator.setCacheManager(cacheMan);
		generator.generateByCache(out, true);
	}

	@Override
	public void writeCaptchaFromCache(CacheableCaptchaGenerator generator,
			CaptchaInfo model, OutputStream out) throws IOException {
		generator.setModel(model);
		generator.setCacheManager(cacheMan);
		generator.generateByCache(out, false);
	}

	@Override
	public void writeCaptcha(CaptchaGenerator generator, CaptchaInfo model,
			OutputStream out) throws IOException {
		if (this.isCacheEnabled() && this.isCacheSupported(generator)) {
			CacheableCaptchaGenerator cacheableGenerator = (CacheableCaptchaGenerator) generator;
			if (new Random().nextFloat() < GlobalConfiguration
					.getFloat("tcaptcha.cache.updateradio")) {
				writeCaptchaFromGeneratorAndUpdateCache(cacheableGenerator,
						model, out);
			} else {
				writeCaptchaFromCache(cacheableGenerator, model, out);
			}
		} else {
			writeCaptchaFromGenerator(generator, model, out);
		}
	}

	@Override
	public boolean isCacheSupported(CaptchaGenerator gen) {
		return gen instanceof CacheableCaptchaGenerator;
	}

	@Override
	public String getMimeTypeByModel(CaptchaInfo model) {
		AbstractCaptchaGeneratorProvider provider = getGeneratorProviders()
				.get(model.getMode());
		if (provider == null) {
			provider = getGeneratorProviders().get(
					GlobalConfiguration.get("tcaptcha.defaultmode"));
		}
		return provider.getMimeType();
	}

	@Override
	public void writeCaptcha(CaptchaInfo model, OutputStream out)
			throws IOException {
		CaptchaGenerator gen = getGeneratorByMode(model);
		writeCaptcha(gen, model, out);
	}

	@Override
	public void writeCaptchaFromCache(CaptchaInfo model, OutputStream out)
			throws IOException {
		CaptchaGenerator gen = getGeneratorByMode(model);
		if (gen instanceof CacheableCaptchaGenerator) {
			CacheableCaptchaGenerator cacheableGen = (CacheableCaptchaGenerator) gen;
			writeCaptchaFromCache(cacheableGen, model, out);
		} else {
			logger.warn("The generator you selected does not support cache, generate by default");
			writeCaptchaFromGenerator(gen, model, out);
		}
	}

	@Override
	public void writeCaptchaFromGenerator(CaptchaInfo model, OutputStream out)
			throws IOException {
		CaptchaGenerator gen = getGeneratorByMode(model);
		writeCaptchaFromGenerator(gen, model, out);
	}

	@Override
	public void writeCaptchaFromGeneratorAndUpdateCache(CaptchaInfo model,
			OutputStream out) throws IOException {
		CaptchaGenerator gen = getGeneratorByMode(model);
		if (gen instanceof CacheableCaptchaGenerator) {
			CacheableCaptchaGenerator cacheableGen = (CacheableCaptchaGenerator) gen;
			writeCaptchaFromGeneratorAndUpdateCache(cacheableGen, model, out);
		} else {
			logger.warn("The generator you selected dose not support cache, generate by default");
			writeCaptchaFromGenerator(gen, model, out);
		}
	}

	public Map<String, AbstractCaptchaGeneratorProvider> getGeneratorProviders() {
		return generatorProviders;
	}

	public Map<String, TicketGenerator> getTicketGenerators() {
		return ticketGenerators;
	}
}
