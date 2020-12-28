package com.packt.j11intro.slackbot;

import com.ullink.slack.simpleslackapi.SlackChannel;
import com.ullink.slack.simpleslackapi.SlackSession;
import com.ullink.slack.simpleslackapi.SlackUser;

public abstract class BaseSlackAssistant implements SlackAssistant {
    protected SlackSession slackSession;
    protected SlackChannel channel;
    protected SlackUser initiator;

    @Override
    public void init(SlackSession slackSession, SlackChannel channel, SlackUser initiator) {
        this.slackSession = slackSession;
        this.channel = channel;
        this.initiator = initiator;

        issueAnnouncement(getName() + " started. Ready to assist.");
    }

    @Override
    final public boolean handleInstruction(String instruction, SlackUser user) {
        if ("start ".startsWith(instruction)) {
            issueWarning(getName() + " is still in session. Cancel or stop it first.", initiator);
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

    abstract boolean handleNonLifecycleInstruction(String instruction, SlackUser user);

    protected void issueAnnouncement(String announcement) {
        slackSession.sendMessage(channel, announcement);
    }

    protected void issueWarning(String warning, SlackUser targetUser) {
        slackSession.sendEphemeralMessage(channel, targetUser, ":angry: " + warning);
    }
}
