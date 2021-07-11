/*
 *
 */
package com.tcaptcha.util;


import java.net.InetAddress;
import java.net.UnknownHostException;
/**
 *
 * Util methods collection
 *
 * 
 * 
 *
 */
public class Util {

    /**
     * check if given host name or ip is localhost
     * @param host
     * @return
     */
    public static boolean isLocalhost(String host) {
        if(host.equals("127.0.0.1")){
            return true;
        }
        if(host.equals("0:0:0:0:0:0:0:1")){
            return true;
        }
        try {
            InetAddress addr = InetAddress.getByName(host);
            if(addr.equals(InetAddress.getLocalHost())){
                return true;
            }
        } catch (UnknownHostException ex) {
            return false;
        }

        return false;
    }
}
