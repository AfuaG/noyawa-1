package org.motechproject.noyawa;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;

import org.json.JSONException;
import org.json.JSONObject;
import org.motechproject.noyawa.domain.RegisterProgramSMS;
import org.motechproject.noyawa.eventhandler.IncomingYawaMessage;
import org.motechproject.noyawa.msgflow.YawaLongCodeRegistration;
import org.motechproject.noyawa.msgflow.YawaOutboundMessage;
import org.motechproject.noyawa.parser.RegisterProgramMessageParser;
import org.motechproject.noyawa.repository.AllProgramTypes;
import org.motechproject.noyawa.repository.AllSubscriptions;
import org.motechproject.noyawa.service.SMSHandler;
import org.motechproject.noyawa.service.SubscriptionService;
import org.motechproject.noyawa.sms.SMSDouble;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@Controller
public class GetyawaController {	
	public static final Logger logger = LoggerFactory.getLogger(GetyawaController.class);
	
    @Autowired
    private SMSHandler smsHandler;
    @Autowired
    private AllSubscriptions allSubscriptions;
    @Autowired
    SubscriptionService subscriptionService;
    @Autowired
    RegisterProgramMessageParser registerProgramParser;
    @Autowired
    private AllProgramTypes allProgramTypes;  
    @Autowired
    private IncomingYawaMessage incomingYawaMessage;
    @Autowired
    private YawaOutboundMessage yawaOutboundMessage;
    @Autowired
    private SMSDouble sMSDouble;
    @Autowired
    private YawaLongCodeRegistration yawaLongCodeRegistration;

	
    DateFormat dateFormat = new SimpleDateFormat("HHmmss");
    Calendar cal = Calendar.getInstance();
    String startTime = dateFormat.format(cal.getTime());

    private String gender = null;
    private String age = null;
    private String educaLevel = null;
    
   	@RequestMapping(value = "/getsms", method = RequestMethod.GET)
	public @ResponseBody String getSms(@RequestParam(value="phone", required=true) String phone, 
		   @RequestParam(value="msg", required=true) String msg) throws Exception {	
		yawaOutboundMessage.smsYawaMsg(phone, msg);
		return String.format(phone + "  " + msg);		
	}

   /* @RequestMapping("/getsms/{phone}/{msg}")
    @ResponseBody
    public  String getsms(@PathVariable String phone, @PathVariable String msg) throws Exception {
        yawaOutboundMessage.smsYawaMsg(phone, msg);
        return String.format(phone + "  " + msg);
    } */


    @RequestMapping(value = "/registerSms", method = RequestMethod.GET)
	public @ResponseBody String registerSms(@RequestParam(value="subNo", required=true) String subNo, @RequestParam(value="prgm", required=true) String prgm) throws JSONException{
		RegisterProgramSMS registerProgramSMS = registerProgramParser.parse(prgm + " " + startTime, subNo);
		JSONObject responseObject = new JSONObject();
		responseObject.put("phoneNumber", subNo.replaceFirst("0","233"));
		
		if (registerProgramSMS == null) {
            responseObject.put("status", "Failed");
            responseObject.put("reason", "Start Time is not valid");
        } else {
            registerProgramSMS.process(smsHandler);
            responseObject.put("status", "Success");
        }
		
		return responseObject.toString();
	} 
	
	@RequestMapping(value = "/registersms", method = RequestMethod.GET)
	public @ResponseBody String registersms(@RequestParam(value="phone", required=true) String phone, @RequestParam(value="msg", required=true) String msg, @RequestParam(value="stage", required=false, defaultValue="1") String stage) throws Exception {

        yawaLongCodeRegistration.longCodeRegistration(phone, msg);
        return String.format(phone + "  " + msg);

       /* String response = "Send gender ( m/f ), age ( 15 - 24 ), education level ( jhs,shs,ter,n/a ) to '0546687715' ";

        if (msg.matches("Start") || msg.matches("start") || msg.matches("START")){
            sMSDouble.longcodeMessage(response, phone);
        } else  {

            gender = msg.toString().substring(0,1);
            age = msg.toString().substring(2,4);
            educaLevel = msg.toString().substring(5,8);

            if (gender.toString().matches("m") || gender.toString().matches("M") || gender.toString().matches("f") || gender.toString().matches("F")) {

                if (age.matches("15") || age.matches("15") || age.matches("16") || age.matches("17") || age.matches("18") || age.matches("19") || age.matches("15") || age.matches("20") ||
                        age.matches("21") || age.matches("22") || age.matches("23") || age.matches("23"))      {

                    if (educaLevel.toString().matches("jhs") || educaLevel.toString().matches("shs") || educaLevel.toString().matches("ter") || educaLevel.toString().matches("n/a")) {
                        sMSDouble.longcodeMessage("Correct for hundred points, ", phone);
                    }
                }

            }
        }
        return gender + " " + age + " " + educaLevel ;  */
	}
	
}
