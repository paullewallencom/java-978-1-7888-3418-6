package com.packt.j11intro.slackbot;

import com.ullink.slack.simpleslackapi.SlackChannel;
import com.ullink.slack.simpleslackapi.SlackSession;
import com.ullink.slack.simpleslackapi.SlackUser;
import com.ullink.slack.simpleslackapi.events.SlackMessagePosted;
import com.ullink.slack.simpleslackapi.impl.SlackSessionFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Component
public class IntroSlackBot {
    private static final Logger LOGGER = LoggerFactory.getLogger(IntroSlackBot.class);
    private Pattern targetUsPattern;

    @Autowired
    private SlackBotProperties slackBotProperties;
    private SlackSession slackSession;
    private Map<String, SlackAssistant> slackAssistants = new HashMap<>();

    @PostConstruct
    public void init() throws IOException {
        // Initialize session with Slack.
        LOGGER.info("Will establish slack connection using accessToken: " + slackBotProperties.getAccessToken());
        slackSession = SlackSessionFactory.createWebSocketSlackSession(slackBotProperties.getAccessToken());
        slackSession.connect();
        LOGGER.info("Slack session established.");

        // Initialize pattern based on bot user id.
        targetUsPattern = Pattern.compile("^\\s*<@" + slackSession.sessionPersona().getId() + ">\\s*(.*)");

        // Add listener for incoming messages.
        slackSession.addMessagePostedListener((slackMessagePosted, slackSession) -> {
            this.handleMessagePosted(slackMessagePosted);
        });
    }

    @PreDestroy
    public void shutdown() throws IOException {
        // Disconnect the session before shutdown for proper clean up.
        slackSession.disconnect();
    }

    protected synchronized void handleMessagePosted(SlackMessagePosted smp) {
        // Logging it to show that we are receiving the messages. Try chatting in the chat room.
        if (LOGGER.isDebugEnabled()) LOGGER.debug(String.format("Received from[%s]: %s", smp.getSender().getRealName(), smp.getMessageContent()));

        // Check if message is targeted at us.
        Matcher matcher = targetUsPattern.matcher(smp.getMessageContent());
        if (!matcher.find()) {
            // Ignore non-targeted messages.
            return;
        }

        // Extract instruction.
        String instruction = matcher.group(1).trim().toLowerCase();
        LOGGER.debug("Instruction: " + instruction);

        String channelId = smp.getChannel().getId();
        SlackAssistant slackAssistant = slackAssistants.get(channelId);
        if (slackAssistant != null) {
            if (!slackAssistant.handleInstruction(instruction, smp.getSender())) {
                slackAssistants.remove(channelId);
            }

            return;
        }

        if (instruction.startsWith("start ")) {
            handleStart(smp, channelId, instruction.substring("start ".length()));
        } else {
            issueWarning("Not sure what you are trying to do.", smp.getChannel(), smp.getSender());
        }
    }

    private void handleStart(SlackMessagePosted smp, String channelId, String instruction) {
        SlackAssistant slackAssistant = null;

        InstructionBreakdown instructionBreakdown = new InstructionBreakdown(instruction);
        String assistantName = instructionBreakdown.getAssistantName();
        if ("voting".equals(assistantName)) {
            slackAssistant = new VotingAssistant();
        } else if ("yes no voting".equals(assistantName)) {
            slackAssistant = new YesNoVotingAssistant();
        } else if ("counting".equals(assistantName)) {
            slackAssistant = new CountingAssistant();
        }

        if (slackAssistant != null) {
            slackAssistant.init(slackSession, smp.getChannel(), smp.getSender(), instructionBreakdown.getDescription());
            slackAssistants.put(channelId, slackAssistant);
        } else {
            issueWarning("No assistant found for [" + assistantName + "], sorry.", smp.getChannel(), smp.getSender());
        }
    }

    void issueWarning(String warning, SlackChannel channel, SlackUser targetUser) {
        slackSession.sendEphemeralMessage(channel, targetUser, ":angry: " + warning);
    }
}

class InstructionBreakdown {
    private final static Pattern PATTERN = Pattern.compile("([\\w\\s]+\\w)(\\s*-\\s*(['\\w\\s]+))?");

    private String assistantName;
    private String description;

    InstructionBreakdown(String instruction) {
        Matcher matcher = PATTERN.matcher(instruction);
        if (matcher.matches() == false) {
            throw new RuntimeException("Unexpected mismatch with pattern[" + PATTERN + "]: " + instruction);
        }

        this.assistantName = matcher.group(1);
        this.description = matcher.group(3);
    }

    public String getAssistantName() {
        return assistantName;
    }

    public String getDescription() {
        return description;
    }
}