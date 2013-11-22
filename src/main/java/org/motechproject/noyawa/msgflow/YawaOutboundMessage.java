package org.motechproject.noyawa.msgflow;

import java.util.Date;
import java.sql.ResultSet;
import java.text.DateFormat;
import java.text.SimpleDateFormat;

import org.motechproject.noyawa.constant.YawaConstant;
import org.motechproject.noyawa.dao.RegistrationDao;
import org.motechproject.noyawa.sms.SMSDouble;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class YawaOutboundMessage {	
	private static final Logger logger = LoggerFactory.getLogger(YawaOutboundMessage.class);

	private YawaConstant yawaConstant = new YawaConstant();
	private YawaRegistrationMessages yawaRegistrationMessages = new YawaRegistrationMessages();
	private RegistrationDao registrationDao = new RegistrationDao();
	Date date = new Date();
    DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");	
    ResultSet resultSet = null;
    //client_number,client_gender,client_age,client_education_level,status
    String phone = null;
    String gender = null;
    String age = null;
    String edulevel = null;
    String status = null;
    
	@Autowired
	private SMSDouble sMSDouble;
	
	@Autowired
	private YawaAlgoSubscription yawaAlgoSubscription;

	public YawaOutboundMessage(){
	}
	
		
     public void smsYawaMsg(String Originator, String inboundMsg) throws Exception {
    	 logger.info(String.format("This Class Sends 'No Yawa' Outbound Messages To Client With Message  (%S) To Originator  (%S) Message ", Originator, inboundMsg)); 	 
        	
    	       
         try {	
 			if(inboundMsg.toString().trim().matches(yawaConstant.bigStart()) || inboundMsg.toString().trim().matches(yawaConstant.smallStart()) || inboundMsg.toString().trim().matches(yawaConstant.smallStart().toUpperCase())
 					|| inboundMsg.toString().trim().matches(yawaConstant.bigYawa()) || inboundMsg.toString().trim().matches(yawaConstant.smallYawa()) || inboundMsg.toString().trim().matches(yawaConstant.smallYawa().toUpperCase())){
 				if(registrationDao.getClientNumber(Originator.toString().trim()) == null) {
 				    sMSDouble.outingMessage(Originator, yawaRegistrationMessages.wellcomeMsg);
 				    System.out.println("The message " + yawaRegistrationMessages.wellcomeMsg + " is sent to " + Originator);
 				    registrationDao.insertClientSms(Originator);
 				} else if(registrationDao.getClientNumber(Originator.toString().trim()).matches(Originator.toString().trim())){
 					sMSDouble.outingMessage(Originator, yawaRegistrationMessages.alreadyRegistered);
 				} 
 				
 			}
 			else if(inboundMsg.toString().trim().matches(yawaConstant.getM()) || inboundMsg.toString().trim().matches(yawaConstant.getM().toLowerCase()) || inboundMsg.toString().trim().matches(yawaConstant.getF()) || inboundMsg.toString().trim().matches(yawaConstant.getF().toLowerCase())) {
 				sMSDouble.outingMessage(Originator, yawaRegistrationMessages.afterGenderMsg);
 				System.out.println("The message " + yawaRegistrationMessages.afterGenderMsg + " is sent to " + Originator);
 				registrationDao.getClientGender(inboundMsg, Originator);
 			}
 			else if(inboundMsg.toString().trim().matches(yawaConstant.get15()) || inboundMsg.toString().trim().matches(yawaConstant.get16()) || inboundMsg.toString().trim().matches(yawaConstant.get17()) || inboundMsg.toString().trim().matches(yawaConstant.get18()) || inboundMsg.toString().trim().matches(yawaConstant.get19())
 					|| inboundMsg.toString().trim().matches(yawaConstant.get20()) || inboundMsg.toString().trim().matches(yawaConstant.get21()) || inboundMsg.toString().trim().matches(yawaConstant.get22()) || inboundMsg.toString().trim().matches(yawaConstant.get23()) || inboundMsg.toString().trim().matches(yawaConstant.get24())){
 				sMSDouble.outingMessage(Originator, yawaRegistrationMessages.afterAgeMsg);
 				System.out.println("The message " + yawaRegistrationMessages.afterAgeMsg + " is sent to " + Originator);
 				registrationDao.getClientAge(inboundMsg, Originator);
 			}
 			else if(inboundMsg.toString().trim().matches(yawaConstant.getJHS()) || inboundMsg.toString().trim().matches(yawaConstant.getJHS().toLowerCase()) || inboundMsg.toString().trim().matches(yawaConstant.getJHS().toUpperCase()) || inboundMsg.toString().trim().matches(yawaConstant.getSHS()) || inboundMsg.toString().trim().matches(yawaConstant.getSHS().toLowerCase())
 					|| inboundMsg.toString().trim().matches(yawaConstant.getSHS().toUpperCase()) || inboundMsg.toString().trim().matches(yawaConstant.getTER()) || inboundMsg.toString().trim().matches(yawaConstant.getTER().toLowerCase()) || inboundMsg.toString().trim().matches(yawaConstant.getTER().toUpperCase()) || inboundMsg.toString().trim().matches(yawaConstant.getNA())
 					|| inboundMsg.toString().trim().matches(yawaConstant.getNA().toLowerCase()) || inboundMsg.toString().trim().matches(yawaConstant.getNA().toUpperCase())){
 				sMSDouble.outingMessage(Originator, yawaRegistrationMessages.afterEducationLevelMsg);
 				System.out.println("The message " + yawaRegistrationMessages.afterEducationLevelMsg + " is sent to " + Originator);
 				registrationDao.getClientEducation(inboundMsg, Originator);
 			}
 			else if(inboundMsg.toString().trim().matches(yawaConstant.getWhatsapp()) || inboundMsg.toString().trim().matches(yawaConstant.getWhatsapp().toLowerCase()) || inboundMsg.toString().trim().matches(yawaConstant.getWhatsapp().toUpperCase())){
 				sMSDouble.outingMessage(Originator, yawaRegistrationMessages.afterWhatsappSms + " via Whatsapp!");
 				System.out.println("The message " + yawaRegistrationMessages.afterWhatsappSms + " is sent to " + Originator);
 				registrationDao.goWhatsapp(Originator, "1", dateFormat.format(date));
 			}
 			else if(inboundMsg.toString().trim().matches(yawaConstant.getSMS()) || inboundMsg.toString().trim().matches(yawaConstant.getSMS().toLowerCase()) || inboundMsg.toString().trim().matches(yawaConstant.getSMS().toUpperCase())){
 				resultSet = registrationDao.getClientForSms(Originator);  				
 				while (resultSet.next()){
 					phone = resultSet.getString("client_number");
 					gender = resultSet.getString("client_gender");
 					age = resultSet.getString("client_age");
 					edulevel = resultSet.getString("client_education_level");
 					status = resultSet.getString("status");					
 					yawaAlgoSubscription.SubscribeUserToCampaign(phone, age, edulevel, status);
 				}			
 				
 				System.out.println("The message " + yawaRegistrationMessages.afterSms + " is sent to " + Originator);
 			}
 			else {
 				sMSDouble.outingMessage(Originator, yawaRegistrationMessages.inputErrorMsg);
 				}
         } catch (Exception e) {
 			// TODO Auto-generated catch block
 			e.printStackTrace();
 		}			
        
     }      
  
} 
