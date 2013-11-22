package org.motechproject.noyawa.tools.seed;

import org.motechproject.noyawa.domain.builder.ShortCodeBuilder;
import org.motechproject.noyawa.repository.AllShortCodes;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import static org.motechproject.noyawa.domain.ShortCode.*;

@Component
public class ShortCodeSeed extends Seed {
    @Autowired
    private AllShortCodes allShortCodes;

    @Override
    protected void load() {
        allShortCodes.add(new ShortCodeBuilder().withCodeKey(RELATIVE).withShortCode("R").withShortCode("r").build());
        allShortCodes.add(new ShortCodeBuilder().withCodeKey(STOP).withShortCode("stop").withShortCode("s").build());
        allShortCodes.add(new ShortCodeBuilder().withCodeKey(REGISTER_WITH_AGE_GENDER_EDUCATION_LEVEL).withShortCode("age gender edu").build());
        allShortCodes.add(new ShortCodeBuilder().withCodeKey(REGISTER_WITH_AGE_GENDER).withShortCode("age gender").build());
    }
}