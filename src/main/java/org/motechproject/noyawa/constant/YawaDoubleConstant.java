package org.motechproject.noyawa.constant;

import java.util.Properties;

public class YawaDoubleConstant {
	
	    //For Yawa Messages
		//-----------------
		Properties prop = new Properties();
				
		public String getYawadoubleUrl() throws Exception{		
			prop.load(getClass().getClassLoader().getResourceAsStream("yawasmsdouble.properties"));
			return prop.getProperty("yawadouble.url");
		}
		
		public String getYawadoubleText() throws Exception{
			prop.load(getClass().getClassLoader().getResourceAsStream("yawasmsdouble.properties"));		
			return prop.getProperty("yawadouble.text");
		}
		
		public String getYawadoubleFrom() throws Exception{
			prop.load(getClass().getClassLoader().getResourceAsStream("yawasmsdouble.properties"));
			return prop.getProperty("yawadouble.from");
		}
		
		public String getYawadoubleTo() throws Exception{
			prop.load(getClass().getClassLoader().getResourceAsStream("yawasmsdouble.properties"));
			return prop.getProperty("yawadouble.to");
		}
		
		public String getYawadoublePass() throws Exception{
			prop.load(getClass().getClassLoader().getResourceAsStream("yawasmsdouble.properties"));
			return prop.getProperty("yawadouble.pass");
		}

}
