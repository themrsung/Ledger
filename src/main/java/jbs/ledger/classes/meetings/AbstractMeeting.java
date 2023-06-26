package jbs.ledger.classes.meetings;

import jbs.ledger.interfaces.common.Unique;
import jbs.ledger.interfaces.organization.Meeting;
import jbs.ledger.io.types.meetings.MeetingData;

import java.util.ArrayList;
import java.util.Date;
import java.util.UUID;

public abstract class AbstractMeeting<M extends Unique> implements Meeting<M> {
    protected AbstractMeeting(
            UUID uniqueId,
            String symbol,
            Date date,
            ArrayList<VotableMember<M>> votableMembers,
            long totalCastableVotes,
            long castVotes,
            long castYesVotes
    ) {
        this.uniqueId = uniqueId;
        this.symbol = symbol;
        this.date = date;
        this.votableMembers = votableMembers;
        this.totalCastableVotes = totalCastableVotes;
        this.castVotes = castVotes;
        this.castYesVotes = castYesVotes;
    }

    private final UUID uniqueId;
    private final String symbol;
    private final Date date;
    private final ArrayList<VotableMember<M>> votableMembers;
    private final long totalCastableVotes;
    private long castVotes;
    private long castYesVotes;

    @Override
    public UUID getUniqueId() {
        return uniqueId;
    }

    @Override
    public String getSymbol() {
        return symbol;
    }

    @Override
    public ArrayList<VotableMember<M>> getVotableMembers() {
        return new ArrayList<>(votableMembers);
    }

    @Override
    public boolean vote(VotableMember<M> member, boolean yes) {
        if (!getVotableMembers().contains(member)) return false;

        long votes = member.getVotes();

        castVotes += votes;
        if (yes) castYesVotes += votes;

        this.votableMembers.remove(member);
        return true;
    }

    @Override
    public long getCastYesVotes() {
        return castYesVotes;
    }

    @Override
    public long getCastVotes() {
        return castVotes;
    }

    @Override
    public long getTotalCastableVotes() {
        return totalCastableVotes;
    }

    // IO
    public MeetingData toData() {
        MeetingData data = new MeetingData();

        data.uniqueId = uniqueId;
        data.symbol = symbol;
        data.date = date;

        for (VotableMember<M> member : votableMembers) {
            data.votableMembers.add(member.toData());
        }

        data.castVotes = castVotes;
        data.castYesVotes = castYesVotes;

        data.type = getType();

        return data;
    }
}
