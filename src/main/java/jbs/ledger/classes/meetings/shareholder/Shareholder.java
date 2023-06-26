package jbs.ledger.classes.meetings.shareholder;

import jbs.ledger.assetholders.person.Person;
import jbs.ledger.classes.meetings.VotableMember;
import jbs.ledger.io.types.meetings.VotableMemberData;
import jbs.ledger.state.LedgerState;

public final class Shareholder extends VotableMember<Person> {
    public Shareholder(
            Person member,
            long votes
    ) {
        super(member, votes);
    }

    public static Shareholder fromData(VotableMemberData data, LedgerState state) {
        return new Shareholder(
                state.getPerson(data.member),
                data.votes
        );
    }
}
