package org.motechproject.noyawa.msgflow;

import org.motechproject.noyawa.eventhandler.IncomingYawaMessage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

public class YawaAlgoSubscription {
	
	private static final Logger logger = LoggerFactory.getLogger(YawaAlgoSubscription.class);
	
	@Autowired
    private IncomingYawaMessage incomingYawaMessage;
	
	public void SubscribeUserToCampaign(String phone, String age, String edulevel,String status) throws Exception{
	
	   if (age == " " || edulevel == " "){
		   
		   System.out.println("Your registration is incomplete, kindly send start to short code 7005");
		   
	   } else {
		   
		   if(Integer.parseInt(age) >= 15 && Integer.parseInt(age) <= 19){
				
				if(edulevel.toString().trim().matches("na".toString().trim()) || edulevel.toString().trim().matches("Na".toString().trim()) || edulevel.toString().trim().matches("NA".toString().trim())){
					incomingYawaMessage.processIncomingYawaMessage(phone, "ro 1");
				}	
				else if(!edulevel.toString().trim().matches("na".toString().trim()) || !edulevel.toString().trim().matches("Na".toString().trim()) || !edulevel.toString().trim().matches("NA".toString().trim())){
	            	incomingYawaMessage.processIncomingYawaMessage(phone, "ki 1");
				}		
			}				
			else if(Integer.parseInt(age) >= 20 && Integer.parseInt(age) <= 24){
				incomingYawaMessage.processIncomingYawaMessage(phone, "ri 1");			
			}			   
	   }						
	  
	
	}

}
