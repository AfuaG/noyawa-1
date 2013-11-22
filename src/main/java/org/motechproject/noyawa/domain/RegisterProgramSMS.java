package org.motechproject.noyawa.domain;

import org.motechproject.noyawa.service.SMSHandler;

public class RegisterProgramSMS extends SMS<Subscription> {

    public RegisterProgramSMS(String message, Subscription domain) {
        super(message, domain);
    }

    @Override
    public void process(SMSHandler handler) {
        handler.register(this);
    }
}
