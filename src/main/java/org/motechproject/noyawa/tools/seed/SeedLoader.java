package org.motechproject.noyawa.tools.seed;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

public class SeedLoader {
    private static Logger LOG = LoggerFactory.getLogger(SeedLoader.class);
    private final List<? extends Seed> seeds;

    public SeedLoader(List<? extends Seed> seeds) {
        this.seeds = seeds;
    }

    public void load() {
        LOG.info("Started loading seeds :" + seeds.toString());
        for (Seed seed : seeds) {
            seed.run();
        }
    }
}
