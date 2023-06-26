package jbs.ledger.classes.meetings.referendums;

import jbs.ledger.assetholders.person.Person;
import jbs.ledger.classes.meetings.VotableMember;
import jbs.ledger.io.types.meetings.VotableMemberData;
import jbs.ledger.state.LedgerState;

public final class Citizen extends VotableMember<Person> {
    public Citizen(
            Person member,
            long votes
    ) {
        super(member, votes);
    }

    public static Citizen fromData(VotableMemberData data, LedgerState state) {
        return new Citizen(
                state.getPerson(data.member),
                data.votes
        );
    }
}
