package org.motechproject.noyawa.process;

import org.motechproject.noyawa.domain.Subscription;

public interface ISubscriptionFlowProcess {

    Boolean startFor(Subscription subscription);

    Boolean stopExpired(Subscription subscription);

    Boolean stopByUser(Subscription subscription);

    Boolean rollOver(Subscription fromSubscription, Subscription toSubscription);
}
