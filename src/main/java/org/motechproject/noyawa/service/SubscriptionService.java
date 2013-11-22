package org.motechproject.noyawa.service;

import java.util.List;

import org.motechproject.noyawa.domain.ProgramType;
import org.motechproject.noyawa.domain.Subscription;

public interface SubscriptionService {
	
	void start(Subscription subscription);
    void stopExpired(Subscription subscription);    
    void rollOver(String fromMobileNumber);
    void rollOverByEvent(Subscription subscription);
    void stopByUser(String fromMobileNumber, ProgramType domain);
    Subscription findActiveSubscriptionFor(String subscriberNumber, String programName);
    List<Subscription> activeSubscriptions(String subscriberNumber);

}
