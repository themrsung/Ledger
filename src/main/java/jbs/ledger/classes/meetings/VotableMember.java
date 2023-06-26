package jbs.ledger.classes.meetings;

import jbs.ledger.interfaces.common.Unique;
import jbs.ledger.io.types.meetings.VotableMemberData;

public abstract class VotableMember<M extends Unique> {
    public VotableMember(
            M member,
            long votes
    ) {
        this.member = member;
        this.votes = votes;
    }

    private final M member;
    private final long votes;

    public M getMember() {
        return member;
    }

    public long getVotes() {
        return votes;
    }

    // IO

    protected VotableMemberData toData() {
        VotableMemberData data = new VotableMemberData();

        data.member = member.getUniqueId();
        data.votes = votes;

        return data;
    }

}
