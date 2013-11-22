package org.motechproject.noyawa.sms;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;

import org.motechproject.noyawa.constant.YawaDoubleConstant;
import org.springframework.beans.factory.annotation.Autowired;


public class SMSDouble {
	
	@Autowired
	YawaDoubleConstant yawaDoubleConstant;
	
	public String outingMessage(String phone, String message) throws Exception {
		
		//URL urlObject = new URL("http://23.21.156.138:2812/Receiver?" + "Text=" + URLEncoder.encode(message, "UTF-8") + "&From=7005" + "&To=" + phone + "&User=gr1m2&Pass=G1R2M3");
		URL urlObject = new URL(yawaDoubleConstant.getYawadoubleUrl().trim() + yawaDoubleConstant.getYawadoubleText().trim() + URLEncoder.encode(message, "UTF-8") + yawaDoubleConstant.getYawadoubleFrom().trim() + yawaDoubleConstant.getYawadoubleTo().trim() + phone + yawaDoubleConstant.getYawadoublePass().trim());
		HttpURLConnection connection =
	                (HttpURLConnection)urlObject.openConnection();
	            connection.setDoInput(true);
	            connection.connect();

	            int responseCode = connection.getResponseCode();
	            if(responseCode == 200) {
	                BufferedReader in = new BufferedReader(
	                    new InputStreamReader(connection.getInputStream()));

	                System.out.println("Submission result: " + in.readLine());
	                in.close();
	            }
	            return connection.toString();
	}
}
