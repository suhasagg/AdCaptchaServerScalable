/*
 *
 * 
 */

package com.tcaptcha.model;

import org.simpleframework.xml.Element;
import org.simpleframework.xml.Root;

/**
 *
 * 
 * 
 *
 */
@Root
public class ValidationResult {

    @Element
    private boolean success;

    @Element
    private String message;

    public ValidationResult(boolean success, String message){
        this.setMessage(message);
        this.setSuccess(success);
    }

    /**
     * @return the success
     */
    public boolean isSuccess() {
        return success;
    }

    /**
     * @param success the success to set
     */
    public void setSuccess(boolean success) {
        this.success = success;
    }

    /**
     * @return the message
     */
    public String getMessage() {
        return message;
    }

    /**
     * @param message the message to set
     */
    public void setMessage(String message) {
        this.message = message;
    }

    @Override
    public String toString(){
        return String.valueOf(success);
    }

}
