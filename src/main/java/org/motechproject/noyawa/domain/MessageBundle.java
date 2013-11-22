package org.motechproject.noyawa.domain;

import org.apache.commons.lang.StringUtils;
import org.motechproject.noyawa.repository.AllMessages;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class MessageBundle {
    public static final String PROGRAM_NAME_MARKER = "${p}";
    public static final String REQUEST_FAILURE = "request.failure";
    public static final String ENROLLMENT_SUCCESS = "enrollment.success";
    public static final String ENROLLMENT_STOPPED = "enrollment.stopped";
    public static final String ENROLLMENT_ROLLOVER = "enrollment.rollover";
    public static final String ACTIVE_SUBSCRIPTION_PRESENT = "enrollment.active.subscription.present";

    public static final String INVALID_MOBILE_NUMBER = "enrollment.invalid.mobile.number";
    public static final String NOT_ENROLLED = "not.enrolled";
    public static final String STOP_SPECIFY_PROGRAM = "stop.specify.program";
    public static final String STOP_SUCCESS = "stop.success";
    public static final String STOP_PROGRAM_SUCCESS = "stop.program.success";
        
    private AllMessages allMessages;

    @Autowired
    public MessageBundle(AllMessages allMessages) {
        this.allMessages = allMessages;
    }

    public String get(String key) {
        Message message = allMessages.findBy(key);
        return message != null ? message.getContent() : StringUtils.EMPTY;
    }
}
