package com.packt.j11intro.slackbot;

public class YesNoVotingAssistant extends VotingAssistant {
    @Override
    public String getName() {
        return "Yes No Voting Assitant";
    }

    @Override
    protected Integer parseInstructionForVote(String instruction) {
        Integer vote = null;
        if ("y".equals(instruction) || "yes".equals(instruction)) {
            vote = 1;
        } else if ("-".equals(instruction) || "abstain".equals(instruction)) {
            vote = 0;
        } else if ("n".equals(instruction) || "no".equals(instruction)) {
            vote = -1;
        }
        return vote;
    }

    @Override
    protected String getWarning() {
        return "Please issue only yes/y, -/abstain, or n/no for agree/abstain/disagree.";
    }
}
