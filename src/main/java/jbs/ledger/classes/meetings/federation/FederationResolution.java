package jbs.ledger.classes.meetings.federation;

import jbs.ledger.assetholders.person.Person;
import jbs.ledger.classes.meetings.AbstractMeeting;
import jbs.ledger.classes.meetings.VotableMember;
import jbs.ledger.interfaces.sovereignty.Sovereign;

import java.util.ArrayList;
import java.util.Date;
import java.util.UUID;

public abstract class FederationResolution extends AbstractMeeting<Sovereign> {
    private FederationResolution(
            UUID uniqueId,
            String symbol,
            Date date,
            ArrayList<VotableMember<Sovereign>> votableMembers,
            long totalCastableVotes,
            long castVotes,
            long castYesVotes
    ) {
        super(uniqueId, symbol, date, votableMembers, totalCastableVotes, castVotes, castYesVotes);
    }
}
