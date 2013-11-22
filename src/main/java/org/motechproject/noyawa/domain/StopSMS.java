package org.motechproject.noyawa.domain;

import org.motechproject.noyawa.service.SMSHandler;

public class StopSMS extends SMS<ProgramType> {

    public StopSMS(String message, ProgramType domain) {
        super(message, domain);
    }

    @Override
    public void process(SMSHandler handler) {
        handler.stop(this);
    }
}