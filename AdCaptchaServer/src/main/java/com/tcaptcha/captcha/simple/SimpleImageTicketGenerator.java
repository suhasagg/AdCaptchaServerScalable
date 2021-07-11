/*
 *
 */

package com.tcaptcha.captcha.simple;

import java.util.Map;
import java.util.Random;

import org.apache.commons.lang.StringUtils;

import com.tcaptcha.captcha.TicketGenerator;
import com.tcaptcha.dao.CaptchaDAOImpl;
import com.tcaptcha.repos.cache.ehcache.EhCacheKeyCodeRepository;

/**
 *
 * 
 * 
 *
 */
public class SimpleImageTicketGenerator implements TicketGenerator {

	 private static SimpleImageTicketGenerator INSTANCE = new SimpleImageTicketGenerator();
		
	 public static SimpleImageTicketGenerator getInstance() {
			return INSTANCE ;
		}


    @Override
    public String getCode(Map<String, String> config) {
    	/*
    	config = config == null ? new HashMap<String, String>() : config;
    	
    	String lengthAsString = config.get("length");
    	if(!StringUtils.isNumeric(lengthAsString)){
    		lengthAsString =GlobalConfiguration.get("tcaptcha.defaultCodeLength");
    	}    	
    	int length = Integer.parseInt(lengthAsString);
    	length = length > 8 ? 8 : length;
    	*/
		
    	
    		  //Captcha Solutions Database Path is configured in Global Configuration file
    /*
    	      FileInputStream fstream = null;
    		  String path =GlobalConfiguration.get("tcaptcha.databasedirectorypath");
    		  try {
				fstream = new FileInputStream(path);
			} catch (FileNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
    		  // Read Ad Captcha Solutions corresponding to image id from Ad Captcha Solutions Database file
    		  DataInputStream in = new DataInputStream(fstream);
    		  BufferedReader br = new BufferedReader(new InputStreamReader(in));
    		  String strLine = null;
    		  //Read File Line By Line
    		*/
    		  
    		  int i=1;
    		  int maximum = 10;
    		  int minimum = 3;
    		  Random rn = new Random();
    		  int range = maximum - minimum + 1;
    		  int id =  rn.nextInt(range) + minimum;
              // nextInt is normally exclusive of the top value,
    		  // so add 1 to make it inclusive
    		  
    		  String Id = Integer.toString(id);
    		  /*	  
    		  try {
				while ((strLine = br.readLine()) != null)   {
				  
				  if(i==id)
					  break;
				  i++;  
				}
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
    		  //Close the input stream
    		  try {
				in.close();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
    	*/	    
    	
        System.out.println("ImageId Generated:"+Id);     
        String code = CaptchaDAOImpl.getInstance().getImageCode(Id);
    	return id +"r"+code; //Place a marker - 'r' to separate image_id from Advertisement Captcha Image Code 
    }

    @Override
    public String getHint() {
        return StringUtils.EMPTY;
    }

   



}
