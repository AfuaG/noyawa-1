package org.motechproject.noyawa.eventhandler;

import org.motechproject.noyawa.SubscriptionController;
import org.motechproject.noyawa.domain.dto.SubscriptionRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class IncomingYawaMessage {
	
	private SubscriptionController controller;

    @Autowired
    public IncomingYawaMessage(SubscriptionController controller) {
        this.controller = controller;
    }
    
    public void processIncomingYawaMessage(String phoneNumber, String inputmessage) throws Exception{
    	controller.handle(subscriptionRequestFor(phoneNumber, inputmessage));    	
    }    
    
    private SubscriptionRequest subscriptionRequestFor(String messageSender, String message) {
        SubscriptionRequest request = new SubscriptionRequest();
        request.setInputMessage(message);
        request.setSubscriberNumber(messageSender);
        return request;
    }
}
