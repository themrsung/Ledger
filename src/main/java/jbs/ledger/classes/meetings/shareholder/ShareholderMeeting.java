package jbs.ledger.classes.meetings.shareholder;

import jbs.ledger.assetholders.Assetholder;
import jbs.ledger.classes.meetings.AbstractMeeting;
import jbs.ledger.classes.meetings.VotableMember;

import java.util.ArrayList;
import java.util.Date;
import java.util.UUID;

public abstract class ShareholderMeeting extends AbstractMeeting<Assetholder> {
    protected ShareholderMeeting(
            UUID uniqueId,
            String symbol,
            Date date,
            ArrayList<VotableMember<Assetholder>> votableMembers,
            long totalCastableVotes,
            long castVotes,
            long castYesVotes
    ) {
        super(uniqueId, symbol, date, votableMembers, totalCastableVotes, castVotes, castYesVotes);
    }
}
