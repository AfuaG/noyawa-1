package org.motechproject.noyawa.sms;

import org.joda.time.DateTime;
import org.motechproject.model.Time;
import org.motechproject.sms.api.service.SmsService;
import org.motechproject.util.DateUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class YawaService implements SMSProvider {
	
	private static final Logger logger = LoggerFactory.getLogger(YawaService.class);

    SmsService smsService;    
    SMSDouble sMSDouble;

   /* @Autowired
    public YawaService(SmsService smsService) {
        this.smsService = smsService;
    }*/
    
    @Autowired
    public YawaService(SMSDouble sMSDouble) {
        this.sMSDouble = sMSDouble;
    }

    @Override
    public boolean send(String mobileNumber, String payload, Time deliveryTime) {
        if (null != deliveryTime) {
            DateTime now = DateUtil.now();
            DateTime deliveryDateTime = now.withTimeAtStartOfDay().withHourOfDay(deliveryTime.getHour()).withMinuteOfHour(deliveryTime.getMinute());
            if (deliveryDateTime.isBefore(now)) {
                deliveryDateTime = deliveryDateTime.plusDays(1);
            }
            try {
				sMSDouble.outingMessage(mobileNumber, payload);
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
            //smsService.sendSMS(mobileNumber, payload, deliveryDateTime);
        } else {
        	try {
				sMSDouble.outingMessage(mobileNumber, payload);
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
            //smsService.sendSMS(mobileNumber, payload);
        }
        
        logger.info("Subscriber: " + mobileNumber + ":" + payload + " Sent Today");
        
        return true;
    }
}
