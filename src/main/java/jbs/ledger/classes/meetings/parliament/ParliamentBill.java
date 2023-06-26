package jbs.ledger.classes.meetings.parliament;

import jbs.ledger.assetholders.person.Person;
import jbs.ledger.classes.meetings.AbstractMeeting;
import jbs.ledger.classes.meetings.LegislatureBill;
import jbs.ledger.classes.meetings.VotableMember;

import java.util.ArrayList;
import java.util.Date;
import java.util.UUID;

public abstract class ParliamentBill extends LegislatureBill {
    protected ParliamentBill(
            UUID uniqueId,
            String symbol,
            Date date,
            ArrayList<VotableMember<Person>> votableMembers,
            long totalCastableVotes,
            long castVotes,
            long castYesVotes
    ) {
        super(uniqueId, symbol, date, votableMembers, totalCastableVotes, castVotes, castYesVotes);
    }
}
