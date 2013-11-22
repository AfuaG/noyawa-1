package org.motechproject.noyawa.tools;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.motechproject.noyawa.tools.seed.SeedLoader;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class SetupSeedData {
    private final static Logger log = LoggerFactory.getLogger(SetupSeedData.class);

    public static final String APPLICATION_CONTEXT_XML = "/seed/applicationContextTool.xml";

    public static void main(String[] args) {
        log.info(String.format("Seed loading: START"));
        ApplicationContext context = new ClassPathXmlApplicationContext(APPLICATION_CONTEXT_XML);
        SeedLoader seedLoader = (SeedLoader) context.getBean("seedLoader");
        seedLoader.load();
        ((ClassPathXmlApplicationContext) context).close();
        log.info(String.format("Seed loading: END"));
    }
}