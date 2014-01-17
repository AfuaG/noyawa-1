package org.motechproject.noyawa.process;

import org.apache.log4j.Logger;
import org.motechproject.noyawa.domain.Subscription;
import org.motechproject.noyawa.utils.DateUtils;
import org.motechproject.model.MotechEvent;
import org.motechproject.model.RunOnceSchedulableJob;
import org.motechproject.scheduler.MotechSchedulerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.HashMap;

import static java.lang.String.format;
import static org.motechproject.server.messagecampaign.EventKeys.EXTERNAL_ID_KEY;

@Component
public class YawaScheduler {
    public static final String NO_YAWA_SCHEDULER = "org.motechproject.ghana.telco.service.yawascheduler";
    private MotechSchedulerService scheduledService;
    private final Logger log = Logger.getLogger(YawaScheduler.class);

    @Autowired
    public YawaScheduler(MotechSchedulerService scheduledService) {
        this.scheduledService = scheduledService;
    }

    public void startScheduleFor(Subscription subscription) {
        String mobileNumber = subscription.subscriberNumber();
        String programKey = subscription.programKey();
        Date startTime = new DateUtils().now().toDate();

        String jobId = jobId(mobileNumber);
        MotechEvent motechEvent = new MotechEvent(NO_YAWA_SCHEDULER, new SchedulerParamsBuilder()
                .withJobId(jobId)
                .withExternalId(mobileNumber)
                .withProgram(programKey)
                .params());

        RunOnceSchedulableJob job = new RunOnceSchedulableJob(motechEvent, startTime);
        scheduledService.scheduleRunOnceJob(job);
        log.info("NO YAWA job scheduled for [" + mobileNumber + "|" + programKey + "|" + startTime + "]");
    }

    private String jobId(String mobileNumber) {
        return format("NOYAWASchedule.%s", mobileNumber);
    }

    public void stopScheduleWaitFor(Subscription subscription) {
        scheduledService.unscheduleJob(NO_YAWA_SCHEDULER, jobId(subscription.subscriberNumber()));
    }

    private static class SchedulerParamsBuilder {

        public final static String PROGRAM_KEY = "Program";
        private HashMap<String, Object> params = new HashMap<String, Object>();
        public HashMap<String, Object> params() {
            return params;
        }

        public SchedulerParamsBuilder withJobId(String id) {
            params.put(MotechSchedulerService.JOB_ID_KEY, id);
            return this;
        }

        public SchedulerParamsBuilder withExternalId(String id) {
            params.put(EXTERNAL_ID_KEY, id);
            return this;
        }

        public SchedulerParamsBuilder withProgram(String program) {
            params.put(PROGRAM_KEY, program);
            return this;
        }
    }

}
