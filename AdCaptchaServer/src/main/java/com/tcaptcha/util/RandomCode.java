/*
 *
 */

package com.tcaptcha.util;

import java.util.Random;

/**
 *
 * 
 * 
 *
 */
public class RandomCode {

    /**
     * base characters, number and uppercase alpha except confusing ones such as 0 and O, 1, I and l.
     */
    public static final String BASE = "ABCDEFGHJKMNPRTUVWXY346789";

    public static String get(int length){
        StringBuffer sb = new StringBuffer();
        Random r = new Random();
        for (int i = 0; i < length; i++) {
            sb.append(BASE.charAt(r.nextInt(BASE.length())));
        }
        return sb.toString();
    }

}
