package org.motechproject.noyawa.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import org.motechproject.noyawa.constant.YawaConstant;

public class RegistrationDao {
	
	private dbConnection yawaConnection = new dbConnection();
	private YawaConstant yawaConstant = new YawaConstant();
	
	Connection connection = null;
    PreparedStatement preparedStatement = null;
    ResultSet resultSet = null;
	
	public void insertClientSms(String phone_number) throws Exception{
		
		connection = yawaConnection.noYawaConnection();
		preparedStatement = connection.prepareStatement(yawaConstant.smsInsert);
		preparedStatement.setString(1, phone_number);
        preparedStatement.setString(2, " ");
        preparedStatement.setString(3, " ");
        preparedStatement.setString(4, " ");
        preparedStatement.setString(5, yawaConstant.regBegin);
        preparedStatement.executeUpdate();
        
        checkConnection();
	}
	
	public void getClientGender(String gender, String phone_number) throws Exception{
		connection = yawaConnection.noYawaConnection();
		preparedStatement = connection.prepareStatement(yawaConstant.getClientGender);
		preparedStatement.setString(1, gender);
		preparedStatement.setString(2, phone_number);
		preparedStatement.executeUpdate();
		
		checkConnection();
	}
	
	
	public void getClientAge(String age, String phone_number) throws Exception{
		
		connection = yawaConnection.noYawaConnection();
		preparedStatement = connection.prepareStatement(yawaConstant.getClientAge);
		preparedStatement.setString(1, age);
		preparedStatement.setString(2, phone_number);
		preparedStatement.executeUpdate();
		
		checkConnection();
	}
	
	public void getClientEducation(String education, String phone_number) throws Exception{
		connection = yawaConnection.noYawaConnection();
		preparedStatement = connection.prepareStatement(yawaConstant.getClientEducation);
		preparedStatement.setString(1, education);
		preparedStatement.setString(2, yawaConstant.regEnd);
		preparedStatement.setString(3, phone_number);
		preparedStatement.executeUpdate();
		
		checkConnection();
	}
	
	public void goWhatsapp(String phone_number, String mgsNumber, String entryDate) throws Exception{
		connection = yawaConnection.noYawaConnection();
		preparedStatement = connection.prepareStatement(yawaConstant.goWhatsapp);
		
		preparedStatement.setString(1, phone_number);
		preparedStatement.setString(2, mgsNumber);
		preparedStatement.setString(3, entryDate);
		preparedStatement.executeUpdate();
		
		checkConnection();
	}
	
	public ResultSet getClientForSms(String phone_number) throws Exception{
		connection = yawaConnection.noYawaConnection();
		preparedStatement = connection.prepareStatement(yawaConstant.getClientForSms);
		preparedStatement.setString(1, phone_number);	    
		resultSet = preparedStatement.executeQuery();				
		return resultSet;		
	}
	
	public String getClientNumber(String phone_number) throws Exception{
		connection = yawaConnection.noYawaConnection();
		preparedStatement = connection.prepareStatement(yawaConstant.validateClientSms);
		preparedStatement.setString(1, phone_number);	    
		resultSet = preparedStatement.executeQuery();
		String phoneNumber = null;		
		while(resultSet.next())
			phoneNumber = resultSet.getString("client_number");		
		return phoneNumber;
	}
	
	private void checkConnection() throws Exception{
		if (preparedStatement != null) {
            preparedStatement.close();
        }

        if (connection != null) {
            connection.close();
        }	
	}

}
