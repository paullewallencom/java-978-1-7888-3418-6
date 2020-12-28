package com.packt.j11intro.slackbot;

import com.ullink.slack.simpleslackapi.SlackUser;

import java.util.HashMap;
import java.util.Map;

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

    @Override
    boolean handleNonLifecycleInstruction(String instruction, SlackUser user) {
        Integer count = null;
        try {
            count = Integer.parseInt(instruction);
        } catch (NumberFormatException nfe) {
            issueWarning("Please, I only can deal with integers > 0. Not " + instruction + ".", user);
            return true;
        }
        if (count <= 0) {
            issueWarning("Please, I only can deal with integers > 0. Not " + instruction + ".", user);
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
