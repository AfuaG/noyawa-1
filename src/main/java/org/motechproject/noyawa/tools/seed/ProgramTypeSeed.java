package org.motechproject.noyawa.tools.seed;

import org.motechproject.noyawa.domain.ProgramType;
import org.motechproject.noyawa.domain.builder.ProgramTypeBuilder;
import org.motechproject.noyawa.repository.AllProgramTypes;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class ProgramTypeSeed extends Seed {
    @Autowired
    private AllProgramTypes allProgramTypes;

    @Override
    protected void load() {

        ProgramType ronalProgramType = new ProgramTypeBuilder()
                .withProgramName("Ronald Care")
                .withShortCode("RO").withShortCode("Ro").withShortCode("ro")
                .withMaxWeek(10).withMinWeek(1)
                .withProgramKey(ProgramType.RONALD)
                .build();

        allProgramTypes.add(ronalProgramType);

        ProgramType kikiProgramType = new ProgramTypeBuilder()
                .withProgramName("Kiki Care")
                .withShortCode("KI").withShortCode("Ki").withShortCode("ki")
                .withMaxWeek(10).withMinWeek(1)
                .withProgramKey(ProgramType.KIKI)
                .build();
        
        allProgramTypes.add(kikiProgramType);     


        ProgramType ritaProgramType = new ProgramTypeBuilder()
                .withProgramName("Rita Care")
                .withShortCode("RI").withShortCode("Ri").withShortCode("ri")
                .withMaxWeek(10).withMinWeek(1)
                .withProgramKey(ProgramType.RITA)
                .build();
        
        allProgramTypes.add(ritaProgramType);
    }
}
