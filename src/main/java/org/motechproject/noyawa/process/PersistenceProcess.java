package org.motechproject.noyawa.process;

import org.motechproject.noyawa.domain.MessageBundle;
import org.motechproject.noyawa.domain.Subscription;
import org.motechproject.noyawa.domain.SubscriptionStatus;
import org.motechproject.noyawa.repository.AllSubscribers;
import org.motechproject.noyawa.repository.AllSubscriptions;
import org.motechproject.noyawa.service.SMSService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import static org.motechproject.noyawa.domain.SubscriptionStatus.WAITING_FOR_ROLLOVER_RESPONSE;

@Component
public class PersistenceProcess extends BaseSubscriptionProcess implements ISubscriptionFlowProcess {
    private AllSubscribers allSubscribers;
    private AllSubscriptions allSubscriptions;

    @Autowired
    public PersistenceProcess(AllSubscribers allSubscribers, AllSubscriptions allSubscriptions, SMSService smsService, MessageBundle messageBundle) {
        super(smsService, messageBundle);
        this.allSubscribers = allSubscribers;
        this.allSubscriptions = allSubscriptions;
    }

    @Override
    public Boolean startFor(Subscription subscription) {
        subscription.setStatus(SubscriptionStatus.ACTIVE);
        allSubscribers.add(subscription.getSubscriber());
        allSubscriptions.add(subscription);
        return true;
    }

    @Override
    public Boolean stopExpired(Subscription subscription) {
        subscription.setStatus(SubscriptionStatus.EXPIRED);
        allSubscriptions.update(subscription);
        return true;
    }

    @Override
    public Boolean stopByUser(Subscription subscription) {
        subscription.setStatus(SubscriptionStatus.SUSPENDED);
        allSubscriptions.update(subscription);
        return true;
    }

    @Override
    public Boolean rollOver(Subscription fromSubscription, Subscription toSubscription) {
        if (!WAITING_FOR_ROLLOVER_RESPONSE.equals(fromSubscription.getStatus())) {
            performRollOver(fromSubscription, toSubscription);
        } else allSubscriptions.update(fromSubscription);
        return true;
    }

    private void performRollOver(Subscription fromSubscription, Subscription toSubscription) {
        fromSubscription.setStatus(SubscriptionStatus.ROLLED_OFF);
        toSubscription.setStatus(SubscriptionStatus.ACTIVE);
        allSubscriptions.add(toSubscription);
        allSubscriptions.update(fromSubscription);
    }

}

