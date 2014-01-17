package org.motechproject.noyawa.service;

import org.motechproject.noyawa.sms.SMSDouble;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.joda.time.DateTime;
import org.motechproject.noyawa.domain.dto.SMSServiceRequest;
import org.motechproject.noyawa.domain.dto.SMSServiceResponse;
import org.motechproject.noyawa.sms.SMSProvider;
import org.motechproject.model.Time;
import org.motechproject.util.DateUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


@Service
public class SMSService {
    private final static org.apache.log4j.Logger log = org.apache.log4j.Logger.getLogger(SMSService.class);
    private SMSProvider smsProvider;

    @Autowired
    public SMSService(SMSProvider smsProvider) {
        this.smsProvider = smsProvider;
    }

    public SMSServiceResponse send(SMSServiceRequest request) {
        String mobileNumber = request.getMobileNumber();
        String message = request.getMessage();
        String program = request.programKey();
        DateTime now = DateUtil.now();

        Time deliveryTime = request.getDeliveryTime();
        smsProvider.send(mobileNumber, message, deliveryTime);
        log.info("Subscriber: " + mobileNumber + ":" + message + " : @" + now);

        return new SMSServiceResponse();
    }
}
