package com.packt.j11intro.slackbot;

import com.ullink.slack.simpleslackapi.SlackChannel;
import com.ullink.slack.simpleslackapi.SlackSession;
import com.ullink.slack.simpleslackapi.SlackUser;

/**
 * A slack assistant is an abstraction for a helper bot that can function just by instruction from different users.
 */
public interface SlackAssistant {
    /**
     * Gets the name of this assistant.
     *
     * @return name of assistant.
     */
    String getName();

    /**
     * Initializes the session.
     *
     * @param slackSession the slack session in use
     * @param channel      the channel this session of assistance is for
     * @param initiator    the initiator of this session
     */
    void init(SlackSession slackSession, SlackChannel channel, SlackUser initiator);

    /**
     * Cancels the assistance.
     *
     * @return text to display as part of cancellation.
     */
    String cancel();

    /**
     * Concludes the assistance. This should be the normal flow.
     *
     * @return text to display to summarize as part of conclusion.
     */
    String conclude();

    /**
     * Generic method to handle instruction passed to this assistant.
     *
     * @param instruction instruction text
     * @param user        the user that sent the instruction
     * @return true if to remain as channel assistant, false otherwise.
     */
    boolean handleInstruction(String instruction, SlackUser user);
}
