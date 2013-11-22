package org.motechproject.noyawa.tools.seed;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public abstract class Seed {
    Logger LOG = LoggerFactory.getLogger(this.getClass());

    public void run() {
        preLoad();
        load();
        postLoad();
    }

    private void preLoad() {
        LOG.info("loading: START");
    }

    private void postLoad() {
        LOG.info("loading: END");
    }

    protected abstract void load();

}