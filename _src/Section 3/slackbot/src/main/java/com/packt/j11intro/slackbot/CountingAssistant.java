package com.packt.j11intro.slackbot;

import com.ullink.slack.simpleslackapi.SlackUser;

import java.util.HashMap;
import java.util.Map;

/**
 * An assistant useful for counting. Very handy in situation where
 * one needs to tally headcounts. e.g. counting attendees to a dinner
 * party where participants could bring extra attendee(s).
 *
 * @see handleNonLifecycleInstruction #handleNonLifecycleInstruction(String, SlackUser) for
 * valid instructions
 */
public class CountingAssistant extends BaseSlackAssistant {
    private Map<String, Integer> userCountMap = new HashMap<>();

    @Override
    protected String formatResult() {
        StringBuilder sb = new StringBuilder();

        int total = 0;
        for (Map.Entry<String, Integer> entry : userCountMap.entrySet()) {
            String id = entry.getKey();
            Integer count = entry.getValue();
            total += count;
            sb.append(slackSession.findUserById(id).getRealName()).append(" asking for " + count + "\n");
        }
        sb.append("Total count: " + total);

        return sb.toString();
    }

    @Override
    public String getName() {
        return "Counting Assistant";
    }

    @Override
    protected String getSessionTitle() {
        if (description == null) {
            return "Counting";
        } else {
            return "Counting for " + description;
        }
    }

    /**
     * Handle the instruction from participants in the counting exercise.
     *
     * @param instruction zero or positive integer
     * @param user        the participant
     * @return true always, this method does not allow breaking lifecycle
     */
    @Override
    protected boolean handleNonLifecycleInstruction(String instruction, SlackUser user) {
        Integer count = null;

        // Turn string into integer, try at least.
        try {
            count = Integer.parseInt(instruction);
        } catch (NumberFormatException nfe) {
            issueWarning("Please, I only can deal with integers > 0. Not " + instruction + ".", user);
            return true;
        }

        // We only take positive integer. Negative does not make sense here.
        // 0 is allowed if need to "reset" to not attending.
        if (count < 0) {
            issueWarning("Please, I only can deal with integers >= 0. Not " + instruction + ".", user);
            return true;
        }

        if (userCountMap.containsKey(user.getId())) {
            issueAnnouncement("Updating count for " + user.getRealName() + " to " + count);
        } else {
            issueAnnouncement("Will take note and update count for " + user.getRealName() + " to " + count);
        }

        userCountMap.put(user.getId(), count);

        return true;
    }
}
