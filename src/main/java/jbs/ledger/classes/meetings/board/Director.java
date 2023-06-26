package jbs.ledger.classes.meetings.board;

import jbs.ledger.assetholders.person.Person;
import jbs.ledger.classes.meetings.VotableMember;
import jbs.ledger.io.types.meetings.VotableMemberData;
import jbs.ledger.state.LedgerState;

public final class Director extends VotableMember<Person> {
    public Director(
            Person member,
            long votes
    ) {
        super(member, votes);
    }

    public static Director fromData(VotableMemberData data, LedgerState state) {
        return new Director(
                state.getPerson(data.member),
                data.votes
        );
    }
}
