package org.motechproject.noyawa.tools;

import org.quartz.SchedulerException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;


public class RecreateDB {
	private static final Logger logger = LoggerFactory.getLogger(RecreateDB.class);

    public static final String APPLICATION_CONTEXT_XML = "seed/applicationContextTool.xml";

    public static void main(String[] args) throws SchedulerException {
    	logger.info("Recreate DB: START");
    	ApplicationContext context = new ClassPathXmlApplicationContext(APPLICATION_CONTEXT_XML);
        CouchDB couchDB = context.getBean(CouchDB.class);
        couchDB.recreate();
        ((ClassPathXmlApplicationContext) context).close();
        logger.info("Recreate DB: END");
    }
}
