package org.motechproject.noyawa.msgflow;

import org.motechproject.noyawa.constant.YawaConstant;
import org.motechproject.noyawa.dao.RegistrationDao;
import org.motechproject.noyawa.sms.SMSDouble;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import java.sql.ResultSet;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;


public class YawaLongCodeRegistration {
    private static final Logger logger = LoggerFactory.getLogger(YawaLongCodeRegistration.class);

    private YawaRegistrationMessages yawaRegistrationMessages = new YawaRegistrationMessages();
    private RegistrationDao registrationDao = new RegistrationDao();
    private YawaConstant yawaConstant = new YawaConstant();

    private String gender = null;
    private String age = null;
    private String educaLevel = null;

    Date date = new Date();
    DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");

    ResultSet resultSet = null;
    String lphone = null;
    String lgender = null;
    String lage = null;
    String ledulevel = null;
    String lstatus = null;

    @Autowired
    private SMSDouble sMSDouble;

    @Autowired
    private YawaAlgoSubscription yawaAlgoSubscription;

    public void longCodeRegistration(String phone, String msg) throws Exception{
        logger.info(String.format("Sending Message From The Long Code ::  (%S) To Originator  (%S) Message ", phone, msg));
        if(msg.toString().trim().matches(yawaConstant.bigStart()) || msg.toString().trim().matches(yawaConstant.smallStart()) || msg.toString().trim().matches(yawaConstant.smallStart().toUpperCase())
                || msg.toString().trim().matches(yawaConstant.bigYawa()) || msg.toString().trim().matches(yawaConstant.smallYawa()) || msg.toString().trim().matches(yawaConstant.smallYawa().toUpperCase())){
            if(registrationDao.getClientNumber(phone.trim()) == null) {
                sMSDouble.longcodeMessage(yawaRegistrationMessages.LongCodeStart, phone);
                System.out.println("The message " + yawaRegistrationMessages.LongCodeStart + " is sent to " + phone.trim());
                registrationDao.insertClientSms(phone.trim());
            } else if(registrationDao.getClientNumber(phone.trim()).matches(phone.toString().trim())){
                sMSDouble.longcodeMessage(yawaRegistrationMessages.alreadyRegistered, phone);
            }
        } else if (msg.toString().trim().matches("s") || msg.toString().trim().matches("S")){
            resultSet = registrationDao.getClientForSms(phone.trim());
            while (resultSet.next()){
                lphone = resultSet.getString("client_number");
                lgender = resultSet.getString("client_gender");
                lage = resultSet.getString("client_age");
                ledulevel = resultSet.getString("client_education_level");
                lstatus = resultSet.getString("status");
                yawaAlgoSubscription.SubscribeUserToCampaign(lphone, lage, ledulevel, lstatus);
            }

            System.out.println("The message " + yawaRegistrationMessages.afterSms + " is sent to " + phone);

        } else if(msg.toString().trim().matches(yawaConstant.getWhatsapp()) || msg.toString().trim().matches(yawaConstant.getWhatsapp().toLowerCase()) || msg.toString().trim().matches(yawaConstant.getWhatsapp().toUpperCase())){
            sMSDouble.longcodeMessage(yawaRegistrationMessages.afterWhatsappSms + " via Whatsapp!", phone);
            System.out.println("The message " + yawaRegistrationMessages.afterWhatsappSms + " is sent to " + phone);
            registrationDao.goWhatsapp(phone, "1", dateFormat.format(date));
        } else  {

            gender = msg.toString().substring(0,1);
            age = msg.toString().substring(2,4);
            educaLevel = msg.toString().substring(5,8);

            if (gender.toString().matches("m") || gender.toString().matches("M") || gender.toString().matches("f") || gender.toString().matches("F")) {

                if (age.matches("15") || age.matches("15") || age.matches("16") || age.matches("17") || age.matches("18") || age.matches("19") || age.matches("15") || age.matches("20") ||
                        age.matches("21") || age.matches("22") || age.matches("23") || age.matches("23"))      {

                    if (educaLevel.toString().matches("jhs") || educaLevel.toString().matches("shs") || educaLevel.toString().matches("ter") || educaLevel.toString().matches("n/a")) {
                         registrationDao.getClientLongCode(gender, age, educaLevel, phone);
                         sMSDouble.longcodeMessage(yawaRegistrationMessages.LongCodeSuccess, phone);
                        logger.info(String.format("Sending Message From The Long Code ::  (%S) To Originator  (%S) Message ", phone, msg));
                    }
                }

            }
        }

    }

}
