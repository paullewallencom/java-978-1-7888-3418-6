package com.packt.j11intro.slackbot;

import com.ullink.slack.simpleslackapi.SlackUser;

import java.util.*;

public class VotingAssistant extends BaseSlackAssistant {
    // Map to store the votes.
    private Map<String, Integer> votes = new HashMap<>();

    protected String formatResult() {
        final StringBuilder sb = new StringBuilder();

        // Use the stream API to format the voting results.
        votes.entrySet().stream()
                .map(e -> new VoteResult(e.getKey(), slackSession.findUserById(e.getKey()).getRealName(), e.getValue()))
                .sorted(Comparator.comparing(VoteResult::getRealName))
                .map(vr -> vr.getRealName() + " voted [" + vr.getVote() + "]")
                .forEach(vr -> sb.append(vr).append("\n"));

        // Tally the votes.
        int up = 0;
        int down = 0;
        int abstain = 0;

        int voters = 0;
        int total = 0;

        for (Iterator<Integer> iterator = votes.values().iterator(); iterator.hasNext(); ) {
            Integer vote = iterator.next();
            if (vote == 1) {
                up++;
            } else if (vote == 0) {
                abstain++;
            } else if (vote == -1) {
                down++;
            }

            total += vote;
            voters++;
        }

        sb.append("Up Votes: " + up + "\n");
        sb.append("Abstain Votes: " + abstain + "\n");
        sb.append("Down Votes: " + down + "\n");
        sb.append("Voters: " + voters + "\n");
        sb.append("Total Votes: " + total + "\n");

        return sb.toString();
    }

    @Override
    public String getName() {
        return "Voting Assistant";
    }

    protected String getSessionTitle() {
        if (description == null) {
            return "Voting";
        } else {
            return "Voting on [" + description + "]";
        }
    }

    protected String getWarning() {
        return "Please issue only +1, 0, or -1 for agree/abstain/disagree.";
    }

    @Override
    protected boolean handleNonLifecycleInstruction(String instruction, SlackUser user) {
        boolean hasUserVoted = votes.containsKey(user.getId());

        Integer vote = parseInstructionForVote(instruction);

        if (vote == null) {
            issueWarning(getWarning(), user);
            return true;
        }

        votes.put(user.getId(), vote);

        if (hasUserVoted) {
            issueAnnouncement("Logging updated vote by " + user.getRealName() + ", with a vote of " + vote + ".");
        } else {
            issueAnnouncement("Vote by " + user.getRealName() + ", with a vote of " + vote + ".");
        }

        return true;
    }

    /**
     * Parses the incoming instruction to get a vote number.
     *
     * @param instruction 1 or +1 / 0 / -1
     * @return 1 for yes vote, 0 for abstain, -1 for no vote
     */
    protected Integer parseInstructionForVote(String instruction) {
        Integer vote = null;
        if ("1".equals(instruction) || "+1".equals(instruction)) {
            vote = 1;
        } else if ("0".equals(instruction)) {
            vote = 0;
        } else if ("-1".equals(instruction)) {
            vote = -1;
        }
        return vote;
    }
}

class VoteResult {
    private String id;
    private String realName;
    private Integer vote;

    public VoteResult(String id, String realName, Integer vote) {
        this.id = id;
        this.realName = realName;
        this.vote = vote;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        VoteResult that = (VoteResult) o;
        return Objects.equals(id, that.id);
    }

    public String getId() {
        return id;
    }

    public String getRealName() {
        return realName;
    }

    public Integer getVote() {
        return vote;
    }

    @Override
    public int hashCode() {
        return id.hashCode();
    }
}