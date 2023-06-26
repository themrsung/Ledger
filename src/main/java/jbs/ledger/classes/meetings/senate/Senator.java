package jbs.ledger.classes.meetings.senate;

import jbs.ledger.assetholders.person.Person;
import jbs.ledger.classes.meetings.VotableMember;
import jbs.ledger.io.types.meetings.VotableMemberData;
import jbs.ledger.state.LedgerState;

public final class Senator extends VotableMember<Person> {
    public Senator(
            Person member,
            long votes
    ) {
        super(member, votes);
    }

    public static Senator fromData(VotableMemberData data, LedgerState state) {
        return new Senator(
                state.getPerson(data.member),
                data.votes
        );
    }
}
