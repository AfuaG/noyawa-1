package org.motechproject.noyawa.tools.seed;

import org.motechproject.noyawa.domain.Message;
import org.motechproject.noyawa.repository.AllMessages;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import static org.motechproject.noyawa.domain.MessageBundle.*;

@Component
public class MessageSeed extends Seed {
    @Autowired
    private AllMessages allMessages;

    @Override
    protected void load() {

        save(INVALID_MOBILE_NUMBER, "Invalid Phone Number");
        save(NOT_ENROLLED, "You are not subscribed to this program.");
        addEnrolment();
        addStop();
    }

    private void addEnrolment() {
        save(REQUEST_FAILURE, "Sorry, kindly send start 1 to short code 7005.");
        save(ENROLLMENT_SUCCESS, "Welcome to No Yawa Program. You are registered & will receive SMSs from ${d} every Weds &Sat. To stop these msgs, send STOP.");
        save(ENROLLMENT_STOPPED, "Your No Yawa Program has ended. Thanks for using the program.");
        save(ENROLLMENT_ROLLOVER, "You have successfully been rolled over to the Noyawa program.");
        save(ACTIVE_SUBSCRIPTION_PRESENT, "You already registered for No Yawa program. Please wait for the program to complete. Or send STOP to end it now.");
    }


    private void addStop() {
        save(STOP_SPECIFY_PROGRAM, "Sorry we are having trouble processing your request. Please specify your enrolled program with your stop request.");
        save(STOP_SUCCESS, "Thank you for using the service. You can subscribe again at any time.");
        save(STOP_PROGRAM_SUCCESS, "Thank you for using the service. You can subscribe again at any time.");
    }

    private void save(String messageKey, String message) {
        allMessages.add(new Message(messageKey, message));
    }

}
