package com.packt.j11intro.slackbot;

import org.junit.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class IntroSlackBotTest {
    @Test
    public void testInstructionBreakDown() {
        InstructionBreakdown instructionBreakdown = new InstructionBreakdown("yes no voting - let's go picnic");
        assertThat(instructionBreakdown.getAssistantName()).isEqualTo("yes no voting");
        assertThat(instructionBreakdown.getDescription()).isEqualTo("let's go picnic");

        instructionBreakdown = new InstructionBreakdown("voting - lets go picnic");
        assertThat(instructionBreakdown.getAssistantName()).isEqualTo("voting");
        assertThat(instructionBreakdown.getDescription()).isEqualTo("lets go picnic");

        instructionBreakdown = new InstructionBreakdown("yes no voting");
        assertThat(instructionBreakdown.getAssistantName()).isEqualTo("yes no voting");
        assertThat(instructionBreakdown.getDescription()).isNull();
    }
}