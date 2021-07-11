/*
 *
 */

package com.tcaptcha.captcha.customhint;

import java.io.FileInputStream;
import java.util.Map;
import java.util.Random;

import com.tcaptcha.GlobalConfiguration;
import com.tcaptcha.captcha.TicketGenerator;
import com.tcaptcha.captcha.math.MathImageTicketGenerator;
import com.tcaptcha.dao.CaptchaDAOImpl;

/**
 *
 * 
 * 
 *
 */
public class CustomHintImageTicketGenerator implements TicketGenerator {

    public static int id;

    private static CustomHintImageTicketGenerator INSTANCE = new CustomHintImageTicketGenerator();
	
	 public static CustomHintImageTicketGenerator getInstance() {
			return INSTANCE ;
		}


    @Override
    public String getHint() {
      
    	FileInputStream fstream = null;
    	String hint =GlobalConfiguration.get("tcaptcha.customHintCaptchaHint");
		return hint;
    }

	@Override
	public String getCode(Map<String, String> config) {
		/*
		FileInputStream fstream = null;
		  String path =GlobalConfiguration.get("tcaptcha.databasedirectorypath");
		  try {
			fstream = new FileInputStream(path);
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		  // Get the object of DataInputStream
		  DataInputStream in = new DataInputStream(fstream);
		  BufferedReader br = new BufferedReader(new InputStreamReader(in));
		  String strLine = null;
		  //Read File Line By Line
		  
		  int i=1;
		  Random rndNumbers = new Random();
		  id = rndNumbers.nextInt(6)+1;
		  
		  try {
			while ((strLine = br.readLine()) != null)   {
			  // Print the content on the console
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
		    
	
	return id +"r"+strLine;
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
}
