package com.packt.j11intro.slackbot;

import com.ullink.slack.simpleslackapi.SlackSession;
import com.ullink.slack.simpleslackapi.events.SlackMessagePosted;
import com.ullink.slack.simpleslackapi.impl.SlackSessionFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import java.io.IOException;

@Component
public class IntroSlackBot {
    private static final Logger LOGGER = LoggerFactory.getLogger(IntroSlackBot.class);

    @Autowired
    private SlackBotProperties slackBotProperties;
    private SlackSession slackSession;

    @PostConstruct
    public void init() throws IOException {
        LOGGER.info("Will establish slack connection using accessToken: " + slackBotProperties.getAccessToken());
        slackSession = SlackSessionFactory.createWebSocketSlackSession(slackBotProperties.getAccessToken());
        slackSession.connect();
        LOGGER.info("Slack session established.");

        slackSession.addMessagePostedListener((slackMessagePosted, slackSession) -> {
            this.handleMessagePosted(slackMessagePosted);
        });
    }

    @PreDestroy
    public void shutdown() throws IOException {
        // Disconnect the session before shutdown for proper clean up.
        slackSession.disconnect();
    }

    protected void handleMessagePosted(SlackMessagePosted smp) {
        // Logging it to show that we are receiving the messages. Try chatting in the chat room.
        if (LOGGER.isDebugEnabled()) LOGGER.debug(String.format("Received from[%s]: %s", smp.getSender().getRealName(), smp.getMessageContent()));
    }
}
