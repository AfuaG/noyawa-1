package org.motechproject.noyawa.parser;

import org.motechproject.noyawa.domain.SMS;
import org.motechproject.noyawa.exception.MessageParseFailException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

import static java.util.Arrays.asList;

@Component
public class CompositeInputMessageParser {

    private List<MessageParser> messageParsers;

    @Autowired
    public CompositeInputMessageParser(RegisterProgramMessageParser registerProgramParser,
                                       StopMessageParser stopMessageParser) {
        messageParsers = asList(registerProgramParser, stopMessageParser);
    }

    public SMS parse(String message, String senderMobileNumber) {
        for (MessageParser parser : messageParsers) {
            SMS sms = parser.parse(message, senderMobileNumber);
            if(sms != null) return sms;
        }
        throw new MessageParseFailException("Input Message is not valid <" + message + ">");
    }

    public void recompilePatterns() {
        for (MessageParser parser : messageParsers) parser.recompilePatterns();
    }
}