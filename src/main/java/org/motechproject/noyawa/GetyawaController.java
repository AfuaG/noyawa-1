package org.motechproject.noyawa;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;

import org.json.JSONException;
import org.json.JSONObject;
import org.motechproject.noyawa.domain.RegisterProgramSMS;
import org.motechproject.noyawa.eventhandler.IncomingYawaMessage;
import org.motechproject.noyawa.msgflow.YawaOutboundMessage;
import org.motechproject.noyawa.parser.RegisterProgramMessageParser;
import org.motechproject.noyawa.repository.AllProgramTypes;
import org.motechproject.noyawa.repository.AllSubscriptions;
import org.motechproject.noyawa.service.SMSHandler;
import org.motechproject.noyawa.service.SubscriptionService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

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
	
    DateFormat dateFormat = new SimpleDateFormat("HHmmss");
    Calendar cal = Calendar.getInstance();
    String startTime = dateFormat.format(cal.getTime());
    
	@RequestMapping(value = "/getsms", method = RequestMethod.GET) 
	public @ResponseBody String getSms(@RequestParam(value="phone", required=true) String phone, 
		   @RequestParam(value="msg", required=true) String msg) throws Exception {	
		yawaOutboundMessage.smsYawaMsg(phone, msg);
		return String.format(phone + "  " + msg);		
	}
	
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
		incomingYawaMessage.processIncomingYawaMessage(phone, msg  + stage);
		JSONObject responseObject = new JSONObject();
		responseObject.put("phoneNumber", phone.replaceFirst("0","233"));
		return responseObject.toString();
	}
	
}
