package org.motechproject.noyawa.process;

import org.motechproject.noyawa.domain.*;
import org.motechproject.noyawa.exception.InvalidProgramException;
import org.motechproject.noyawa.matchers.ProgramTypeMatcher;
import org.motechproject.noyawa.repository.AllShortCodes;
import org.motechproject.noyawa.repository.AllSubscriptions;
import org.motechproject.noyawa.service.SMSService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

import static ch.lambdaj.Lambda.*;
import static java.lang.String.format;
import static org.hamcrest.Matchers.equalTo;
import static org.motechproject.noyawa.domain.MessageBundle.*;
import static org.springframework.util.CollectionUtils.isEmpty;

@Component
public class ValidationProcess extends BaseSubscriptionProcess implements ISubscriptionFlowProcess {
    private AllSubscriptions allSubscriptions;
    private AllShortCodes allShortCodes;

    @Autowired
    protected ValidationProcess(SMSService smsService, MessageBundle messageBundle,
                                AllSubscriptions allSubscriptions,
                                AllShortCodes allShortCodes) {
        super(smsService, messageBundle);
        this.allSubscriptions = allSubscriptions;
        this.allShortCodes = allShortCodes;
    }

    @Override
    public Boolean startFor(Subscription subscription) {
        String subscriberNumber = subscription.subscriberNumber();
        if (subscription.isNotValid()) {
            sendMessage(subscription, messageFor(REQUEST_FAILURE));
            return false;
        }
        if (hasActiveSubscription(subscriberNumber, subscription)) {
            String content = format(messageFor(MessageBundle.ACTIVE_SUBSCRIPTION_PRESENT), subscription.programName());
            sendMessage(subscription, content);
            return false;
        }        
        return true;
    }

    @Override
    public Boolean stopExpired(Subscription subscription) {
        return true;
    }

    @Override
    public Boolean stopByUser(Subscription subscription) {
        return true;
    }

    @Override
    public Boolean rollOver(Subscription fromSubscription, Subscription toSubscription) {
        Subscription existingCareSubscription = allSubscriptions.findActiveSubscriptionFor(fromSubscription.subscriberNumber(), ProgramType.RONALD);
        if (existingCareSubscription != null) {
           
            sendMessage(existingCareSubscription.subscriberNumber(), format(messageFor("Rollover not possible, program exist already"),
                    null, null));

            fromSubscription.setStatus(SubscriptionStatus.WAITING_FOR_ROLLOVER_RESPONSE);
            allSubscriptions.update(fromSubscription);
            return true;
        }
        return fromSubscription.canRollOff();
    }

    public Subscription validateSubscriptionToStop(String subscriberNumber, ProgramType programType) {

        List<Subscription> subscriptions = allSubscriptions.getAllActiveSubscriptionsForSubscriber(subscriberNumber);
        boolean isUserWith2ProgrammesDidNotSpecifyProgramToStop = subscriptions.size() > 1 && programType == null;

        if (subscriptions.size() == 0) {
            sendMessage(subscriberNumber, messageFor(NOT_ENROLLED));
        } else if (isUserWith2ProgrammesDidNotSpecifyProgramToStop) {
            sendMessage(subscriberNumber, messageFor(MessageBundle.STOP_SPECIFY_PROGRAM));
        } else {
            Subscription subscriptionToStop = programType != null ?
                    (Subscription) selectUnique(subscriptions, having(on(Subscription.class).programKey(), equalTo(programType.getProgramKey()))) :
                    subscriptions.get(0);
            if (subscriptionToStop == null) sendMessage(subscriberNumber, messageFor(NOT_ENROLLED));
            return subscriptionToStop;
        }
        return null;
    }

    public Subscription validateForRollOver(String subscriberNumber) {
        Subscription subscription = allSubscriptions.findActiveSubscriptionFor(subscriberNumber, ProgramType.RONALD);
        if (null == subscription)
            sendMessage(subscriberNumber, messageFor(MessageBundle.NOT_ENROLLED));
        return subscription;
    }

    private boolean hasActiveSubscription(String subscriberNumber, Subscription subscription) {
        List<Subscription> activeSubscriptions = allSubscriptions.getAllActiveSubscriptionsForSubscriber(subscriberNumber);
        List<Subscription> subscriptions = select(activeSubscriptions, having(on(Subscription.class).getProgramType(),
                new ProgramTypeMatcher(subscription.getProgramType())));
        return !isEmpty(subscriptions);
    }

    private String formatShortCode(ShortCode shortCode) {
        return isEmpty(shortCode.getCodes()) ? "" : shortCode.defaultCode();
    }
}