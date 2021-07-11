/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.tcaptcha.captcha.plaintext;

import java.io.IOException;
import java.io.OutputStream;
import java.io.OutputStreamWriter;

import com.tcaptcha.captcha.CaptchaGenerator;
import com.tcaptcha.model.CaptchaInfo;
import com.tcaptcha.util.StringUtil;

/**
 * The simplest plain text captcha generator
 *
 * 
 * 
 *
 */
public class PlainTextGenerator implements CaptchaGenerator {

    private String code;

    @Override
    public void generate(OutputStream outputStream) throws IOException {
        OutputStreamWriter writer = new OutputStreamWriter(outputStream);
        
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
     * @param code the code to set
     */
    public void setCode(String code) {
        this.code = code;
    }

}
