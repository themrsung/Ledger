package jbs.ledger.classes.meetings.shareholder;

import jbs.ledger.assetholders.Assetholder;
import jbs.ledger.classes.meetings.VotableMember;
import jbs.ledger.io.types.meetings.VotableMemberData;
import jbs.ledger.state.LedgerState;

public final class Shareholder extends VotableMember<Assetholder> {
    public Shareholder(
            Assetholder member,
            long votes
    ) {
        super(member, votes);
    }

    public static Shareholder fromData(VotableMemberData data, LedgerState state) {
        return new Shareholder(
                state.getAssetholder(data.member),
                data.votes
        );
    }
}
