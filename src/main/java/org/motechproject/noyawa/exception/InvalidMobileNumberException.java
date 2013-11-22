package org.motechproject.noyawa.exception;

import org.motechproject.noyawa.domain.MessageBundle;

public class InvalidMobileNumberException extends RuntimeException {
    public InvalidMobileNumberException() {
        super(MessageBundle.INVALID_MOBILE_NUMBER);
    }
}
