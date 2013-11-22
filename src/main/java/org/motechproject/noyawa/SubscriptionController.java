package org.motechproject.noyawa;

import org.apache.commons.lang.StringUtils;
import org.motechproject.noyawa.domain.ProgramType;
import org.motechproject.noyawa.domain.SMS;
import org.motechproject.noyawa.domain.Subscription;
import org.motechproject.noyawa.domain.dto.SubscriptionRequest;
import org.motechproject.noyawa.process.UserMessageParserProcess;
import org.motechproject.noyawa.repository.AllSubscriptions;
import org.motechproject.noyawa.service.SMSHandler;
import org.motechproject.noyawa.service.SubscriptionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;

import static ch.lambdaj.Lambda.*;
import static org.hamcrest.Matchers.containsString;
import static org.hamcrest.Matchers.isIn;

@Controller
@RequestMapping("/subscription")
public class SubscriptionController {
    public static final String UNREGISTER = "UNREGISTER";
    public static final String ROLL_OVER = "ROLL_OVER";
    @Autowired
    private UserMessageParserProcess subscriptionParser;
    @Autowired
    private SMSHandler smsHandler;
    @Autowired
    private AllSubscriptions allSubscriptions;
    @Autowired
    SubscriptionService subscriptionService;

    @RequestMapping("/handle")
    @ResponseBody
    public void handle(@ModelAttribute SubscriptionRequest request) throws Exception {
        SMS sms = subscriptionParser.process(request.getSubscriberNumber(), request.getInputMessage());
        if (sms == null) return;       
        sms.process(smsHandler);
    }

    @RequestMapping("/search")
    public ModelAndView search(@RequestParam String subscriberNumber, @RequestParam String programName, @RequestParam String status) {
        ModelAndView modelAndView = new ModelAndView("searchResults");
        List<Subscription> subscriptions = allSubscriptions.getAll();
        if (!StringUtils.isEmpty(subscriberNumber)) {
            subscriptions = filter(having(on(Subscription.class).getSubscriber().getNumber(), containsString(subscriberNumber)), subscriptions);
        }
        if (!StringUtils.isEmpty(programName)) {
            subscriptions = filter(having(on(Subscription.class).getProgramType().getProgramKey(), isIn(programName.split("/"))), subscriptions);
        }
        if (!StringUtils.isEmpty(status)) {
            subscriptions = filter(having(on(Subscription.class).getStatusName(), isIn(status.split("/"))), subscriptions);
        }

        modelAndView.addObject("subscriptions", subscriptions);
        return modelAndView;
    }
    
    @RequestMapping("/unregister/{subscriptionNumber}/{programType}")
    @ResponseBody
    public String unRegister(@PathVariable("subscriptionNumber") String subscriptionNumber, @PathVariable("programType") String programType) {
        subscriptionService.stopByUser(subscriptionNumber, new ProgramType().setProgramKey(programType));
        return "success";
    }
}
