package com.packt.j11intro.slackbot;

/**
 * YesNo Voting Assistant is one that takes yes/no/abstain for
 * answer instead of the less friendly +1/0/-1.
 */
public class YesNoVotingAssistant extends VotingAssistant {
    @Override
    public String getName() {
        return "Yes No Voting Assistant";
    }

    /**
     * Parses the instruction and extract vote.
     *
     * @param instruction yes or y / abstrain or - / no or n are valid
     * @return 1 for yes vote, -1 for no vote, 0 for abstain
     */
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
