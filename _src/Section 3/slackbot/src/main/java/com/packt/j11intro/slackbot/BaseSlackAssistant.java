package com.packt.j11intro.slackbot;

import com.ullink.slack.simpleslackapi.SlackChannel;
import com.ullink.slack.simpleslackapi.SlackSession;
import com.ullink.slack.simpleslackapi.SlackUser;

import java.util.Comparator;
import java.util.Iterator;

public abstract class BaseSlackAssistant implements SlackAssistant {
    protected SlackSession slackSession;
    protected SlackChannel channel;
    protected SlackUser initiator;
    protected String description;

    @Override
    public String cancel() {
        return getSessionTitle() + " has been cancelled, too bad. The result was: \n" + formatResult();
    }

    @Override
    public String conclude() {
        return getSessionTitle() + " has been concluded. The result is: \n" + formatResult();
    }

    abstract protected String formatResult();

    abstract protected String getSessionTitle();

    @Override
    final public boolean handleInstruction(String instruction, SlackUser user) {
        if ("start ".startsWith(instruction)) {
            String descriptionStr = "";
            if (description != null) {
                descriptionStr = " (" + description + ")";
            }

            issueWarning(getName() + descriptionStr + " is still in session. Cancel or stop it first.", initiator);
        } else if ("cancel".equals(instruction)) {
            String cancellationMessage = cancel();
            issueAnnouncement(cancellationMessage);
            return false;
        } else if ("conclude".equals(instruction)) {
            String concludeMessage = conclude();
            issueAnnouncement(concludeMessage);
            return false;
        }

        return handleNonLifecycleInstruction(instruction, user);
    }

    protected abstract boolean handleNonLifecycleInstruction(String instruction, SlackUser user);

    @Override
    public void init(SlackSession slackSession, SlackChannel channel, SlackUser initiator, String description) {
        this.slackSession = slackSession;
        this.channel = channel;
        this.initiator = initiator;
        this.description = description;

        issueAnnouncement(getName() + " started. Ready to assist.");
    }

    protected void issueAnnouncement(String announcement) {
        slackSession.sendMessage(channel, announcement);
    }

    protected void issueWarning(String warning, SlackUser targetUser) {
        slackSession.sendEphemeralMessage(channel, targetUser, ":angry: " + warning);
    }
}
