package jbs.ledger.classes.meetings.federation;

import jbs.ledger.assetholders.person.Person;
import jbs.ledger.classes.meetings.VotableMember;
import jbs.ledger.interfaces.sovereignty.Sovereign;
import jbs.ledger.io.types.meetings.VotableMemberData;
import jbs.ledger.state.LedgerState;

public final class FederationMember extends VotableMember<Sovereign> {
    public FederationMember(
            Sovereign member,
            long votes
    ) {
        super(member, votes);
    }

    public static FederationMember fromData(VotableMemberData data, LedgerState state) {
        return new FederationMember(
                state.getSovereign(data.member),
                data.votes
        );
    }
}
