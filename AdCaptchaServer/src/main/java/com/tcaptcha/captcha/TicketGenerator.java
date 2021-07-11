/*
 *
 */

package com.tcaptcha.captcha;

import java.util.Map;

/**
 *
 * 
 * 
 */
public interface TicketGenerator {

    /**
     * get generated code
     * @return code
     */
    public String getCode(Map<String, String> config);

    /**
     * get captcha hint text
     * @return hint text
     */
    public String getHint();

}
