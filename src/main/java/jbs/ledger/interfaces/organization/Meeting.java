package jbs.ledger.interfaces.organization;

import jbs.ledger.classes.meetings.VotableMember;
import jbs.ledger.interfaces.common.Symbolic;
import jbs.ledger.interfaces.common.Unique;
import jbs.ledger.io.types.meetings.MeetingType;
import jbs.ledger.state.LedgerState;

import java.util.ArrayList;

/**
 * A meeting held by an organization.
 * @param <M> Type of member
 */
public interface Meeting<M extends Unique> extends Symbolic, Unique {
    /**
     * Gets votable members. Members will be removed from this list once votes are cast.
     * @return Copied list of votable members.
     */
    ArrayList<VotableMember<M>> getVotableMembers();

    /**
     * Processes vote
     * @param member Member to vote as
     * @param yes Whether to vote yes or no
     * @return Return whether vote was cast successfully.
     */
    boolean vote(VotableMember<M> member, boolean yes);

    long getCastYesVotes();
    long getCastVotes();
    long getTotalCastableVotes();

    default long getCastNoVotes() {
        return getCastVotes() - getCastYesVotes();
    }

    default long getRemainingVotes(M member) {
        for (VotableMember<M> m : getVotableMembers()) {
            if (m.getMember().equals(member)) {
                return m.getVotes();
            }
        }
        return 0;
    }

    /**
     * Called when the meeting has passed
     * @param organization Organization this meeting is being held
     * @param state State of the server
     */
    void onPassed(Organization<M> organization, LedgerState state);

    MeetingType getType();

    default float getPassRatioOnCastVotes() {
        return 0.5f;
    }

    default float getPassRatioOnTotalVotes() {
        return 0.25f;
    }

    default boolean hasPassed() {
        try {
            if ((float) getCastYesVotes() / getCastVotes() >= getPassRatioOnCastVotes()) {
                if (getCastYesVotes() >= (getTotalCastableVotes() * getPassRatioOnTotalVotes())) {
                    return true;
                }
            }

            return false;
        } catch (IllegalArgumentException e) {
            return false;
        }
    }

    default boolean hasFailed() {
        try {
            double votesRequired = getTotalCastableVotes() * getPassRatioOnTotalVotes();
            return getCastNoVotes() > votesRequired;
        } catch (IllegalArgumentException e) {
            return false;
        }
    }
}
