package jbs.ledger.classes.meetings.supremecourt;

import jbs.ledger.assetholders.Assetholder;
import jbs.ledger.assetholders.person.Person;
import jbs.ledger.classes.meetings.VotableMember;
import jbs.ledger.io.types.meetings.VotableMemberData;
import jbs.ledger.state.LedgerState;

public final class Justice extends VotableMember<Person> {
    public Justice(
            Person member,
            long votes
    ) {
        super(member, votes);
    }

    public static Justice fromData(VotableMemberData data, LedgerState state) {
        return new Justice(
                state.getPerson(data.member),
                data.votes
        );
    }
}
