package com.packt.j11intro.slackbot;

import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties("app.slackbot.bot")
public class SlackBotProperties {
    private String accessToken;

    public String getAccessToken() {
        return accessToken;
    }

    public void setAccessToken(String accessToken) {
        this.accessToken = accessToken;
    }
}
