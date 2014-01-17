package org.motechproject.noyawa.service;

import org.motechproject.noyawa.domain.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.Date;
import java.text.DateFormat;
import java.text.SimpleDateFormat;


@Service
public class SMSHandler {

    SubscriptionService service;

    Date date = new Date();
    DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");

    @Autowired
    public SMSHandler(SubscriptionService service) {
        this.service = service;
    }

    public void register(RegisterProgramSMS sms) {
        Subscription subscription = sms.getDomain();
        Subscriber subscriber = new Subscriber(sms.getFromMobileNumber());
        subscription.setSubscriber(subscriber);
        service.start(subscription);
    }

    public void stop(StopSMS sms) {
        service.stopByUser(sms.getFromMobileNumber(), sms.getDomain());
    }


}
