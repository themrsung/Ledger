package jbs.ledger.classes.meetings.parliament;

import jbs.ledger.assetholders.person.Person;
import jbs.ledger.classes.meetings.VotableMember;
import jbs.ledger.io.types.meetings.VotableMemberData;
import jbs.ledger.state.LedgerState;

public final class MemberOfParliament extends VotableMember<Person> {
    public MemberOfParliament(
            Person member,
            long votes
    ) {
        super(member, votes);
    }

    public static MemberOfParliament fromData(VotableMemberData data, LedgerState state) {
        return new MemberOfParliament(
                state.getPerson(data.member),
                data.votes
        );
    }
}
