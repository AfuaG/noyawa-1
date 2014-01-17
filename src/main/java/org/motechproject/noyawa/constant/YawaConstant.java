package org.motechproject.noyawa.constant;

public class YawaConstant {
	
	
	public String smallStart(){
		return "start";
	}
	
	public String bigStart(){
		return "Start";
	}	

	public String smallYawa(){
		return "noyawa";
	}
	
	public String bigYawa(){
		return "Noyawa";
	}
	
	public String get15(){		
		return "15";		
	}
	public String get16(){		
		return "16";		
	}
	public String get17(){		
		return "17";		
	}
	public String get18(){		
		return "18";		
	}
	public String get19(){		
		return "19";		
	}
	public String get20(){		
		return "20";		
	}
	public String get21(){		
		return "21";		
	}
	public String get22(){		
		return "22";		
	}
	public String get23(){		
		return "23";		
	}
	public String get24(){		
		return "24";		
	}	
	
	public String getJHS(){
		return "Jhs";
	}
	public String getSHS(){
		return "Shs";
	}
	public String getTER(){
		return "Ter";
	}
	public String getNA(){
		return "Na";
	}
	
	public String getM(){
		return "M";
	}
	public String getF(){
		return "F";
	}
	
	public String getGo(){
		return "Go";
	}
	
	public String getWhatsapp(){
		return "w";
	}
	
	public String getSMS(){
		return "s";
	}
	
	public String getCampaignName(){
		return "NoYawaCampaign";		
	}
	
	//For All Yawa SQL
	//----------------
	
	public String regBegin = "Incomplete";
	public String regEnd = "Completed";
	
	public String smsInsert = "INSERT INTO clients_sms_registration(client_number,client_gender,client_age,client_education_level,status) values(?,?,?,?,?)";
	public String getClientGender = "UPDATE clients_sms_registration SET client_gender=? WHERE client_number=?";
	public String getClientAge = "UPDATE clients_sms_registration SET client_age=? WHERE client_number=?";
	public String getClientEducation = "UPDATE clients_sms_registration SET client_education_level=?, status=? WHERE client_number=?";
    public String goWhatsapp = "INSERT INTO clients(phone_number,msg_week,created_at) values(?,?,?)";
	public String getClientForSms = "SELECT client_number,client_gender,client_age,client_education_level,status FROM clients_sms_registration WHERE client_number=?";
	public String validateClientSms ="SELECT client_number FROM clients_sms_registration WHERE client_number=?";

    //Long Code Registration
    public String getClientLongCode ="UPDATE clients_sms_registration SET client_gender=?, client_age=?, client_education_level=?, status='LongCode' WHERE client_number=?";

}
