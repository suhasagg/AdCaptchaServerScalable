package com.tcaptcha.web;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

import org.apache.commons.lang.StringUtils;

import com.tcaptcha.model.Ticket;



public class TestCaptcha {

	/**
	 * @param args
	 * @throws IOException 
	 */
	
	public static TicketApi ticketApi = TicketApi.getInstance();

	public static CaptchaApi captchaApi = CaptchaApi.getInstance();

	public static ValidateApi validateApi = ValidateApi.getInstance();
	
	public static void main(String[] args) throws IOException {
	

	
	for(int i=1; i<50; i++){
       
 
	 Ticket a = ticketApi.getTicket();
	 
	 String b= a.getCode()+"e";
	 String h =a.getHint();
	 String type= a.getType();
	 System.out.println("Code:"+ b);
	 System.out.println("Hint:"+ h);
	 System.out.println("Type:"+ type);
	 String c = a.getKey();
	 String path = null;
	 
	 if(StringUtils.equalsIgnoreCase(type,"text/plain"))
	 {
		 path = "D:/file1.txt";
	 }
	 
	 if(StringUtils.equalsIgnoreCase(type,"image/jpeg"))
	 {
		 path = "D:/file3.jpg";
	 }
	 
	 FileOutputStream e = new FileOutputStream(new File(path));
	 
	 captchaApi.CaptchaWithSessionKey(c, e);
	 e.flush();
     e.close();
	 
	 
	 boolean variable = validateApi.validateCaptcha(c,b);
	 System.out.println(variable);
	 }
	
	
	}




}
