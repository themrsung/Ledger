package jbs.ledger.classes.meetings.supremecourt;

import jbs.ledger.assetholders.person.Person;
import jbs.ledger.classes.meetings.AbstractMeeting;
import jbs.ledger.classes.meetings.VotableMember;

import java.util.ArrayList;
import java.util.Date;
import java.util.UUID;

public abstract class SupremeCourtBill extends AbstractMeeting<Person> {
    private SupremeCourtBill(
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
