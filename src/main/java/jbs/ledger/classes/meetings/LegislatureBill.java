package jbs.ledger.classes.meetings;

import jbs.ledger.assetholders.person.Person;

import java.util.ArrayList;
import java.util.Date;
import java.util.UUID;

/**
 * This exists because of type casting.
 */
public abstract class LegislatureBill extends AbstractMeeting<Person> {
    protected LegislatureBill(UUID uniqueId, String symbol, Date date, ArrayList<VotableMember<Person>> votableMembers, long totalCastableVotes, long castVotes, long castYesVotes) {
        super(uniqueId, symbol, date, votableMembers, totalCastableVotes, castVotes, castYesVotes);
    }
}
