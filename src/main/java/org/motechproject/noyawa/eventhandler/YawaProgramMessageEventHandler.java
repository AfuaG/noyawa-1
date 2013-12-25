package org.motechproject.noyawa.eventhandler;

import org.motechproject.noyawa.domain.Subscription;
import org.motechproject.noyawa.process.MessengerProcess;
import org.motechproject.noyawa.service.SubscriptionService;
import org.motechproject.model.MotechEvent;
import org.motechproject.server.event.annotations.MotechListener;
import org.motechproject.server.messagecampaign.EventKeys;
import org.motechproject.server.messagecampaign.dao.AllMessageCampaigns;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Map;

import static org.motechproject.server.messagecampaign.EventKeys.MESSAGE_CAMPAIGN_SEND_EVENT_SUBJECT;

@Service
public class YawaProgramMessageEventHandler {
    private static final Logger logger = LoggerFactory.getLogger(YawaProgramMessageEventHandler.class);
    private SubscriptionService service;
    private MessengerProcess messenger;
    private AllMessageCampaigns allMessageCampaigns;

    @Autowired
    public YawaProgramMessageEventHandler(MessengerProcess messenger, SubscriptionService service, AllMessageCampaigns allMessageCampaigns) {
        this.messenger = messenger;
        this.service = service;
        this.allMessageCampaigns = allMessageCampaigns;
    }

    @MotechListener(subjects = {MESSAGE_CAMPAIGN_SEND_EVENT_SUBJECT})
    public void sendMessageReminder(MotechEvent event) {
        Map params = event.getParameters();
        String programKey = (String) params.get(EventKeys.CAMPAIGN_NAME_KEY);
        String subscriberNumber = (String) params.get(EventKeys.EXTERNAL_ID_KEY);

        logger.info(String.format("This Class Sends 'No Yawa' Outbound Messages To Client With Message  (%S) To Originator  (%S) Message ",subscriberNumber ,programKey ));

        Subscription subscription = service.findActiveSubscriptionFor(subscriberNumber, programKey);
        if (subscription != null) {
            messenger.process(subscription, (String) event.getParameters().get(EventKeys.GENERATED_MESSAGE_KEY));
        }
    }
}