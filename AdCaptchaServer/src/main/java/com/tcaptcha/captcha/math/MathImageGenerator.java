/**
 * 
 */
package com.tcaptcha.captcha.math;

import java.io.IOException;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.util.Random;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;

import com.tcaptcha.captcha.CaptchaGenerator;
import com.tcaptcha.captcha.simple.SimpleImageGenerator;
import com.tcaptcha.model.CaptchaInfo;
import com.tcaptcha.util.StringUtil;

/**
 * The Math captcha generator
 *
 * 
 * @author Suhas Aggarwal
 *
 */
public class MathImageGenerator implements CaptchaGenerator {

    private String code;
    private final Logger logger = Logger.getLogger(SimpleImageGenerator.class);
    @Override
    public void generate(OutputStream outputStream) throws IOException {
        OutputStreamWriter writer = new OutputStreamWriter(outputStream);
        logger.debug("Expression:" + code);
        writer.write(code);
        writer.close();
    }

    
    @Override
    public void setModel(CaptchaInfo model) {
    	String[] parts = StringUtil.splitFirst(model.getCode(), "r");
		String part1 = parts[0]; // 004
		String part2 = parts[1];
    	this.setCode(part2);
    }
    
    /**
     * @return the code
     */
    public String getCode() {
        return code;
    }
    
    
    /**
     * Generate random expression if numeric code passed in 
     * @param code
     */
    
    public void setCode(String code) {
		if (StringUtils.isNumeric(code)) {
			int codeValue = Integer.valueOf(code);
			if (codeValue > 0) {
				Random r = new Random();
				int value1 = r.nextInt(codeValue);
				int value2 = codeValue - value1;
				this.code = new StringBuffer().append(value1).append("+").append(value2).toString();
			} else {
				Random r = new Random();
				int value = r.nextInt(100);
				this.code = new StringBuffer().append(value).append("-").append(value).toString();
			}
		} 
	}

}
