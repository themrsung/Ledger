package jbs.ledger.classes.meetings.shareholder;

import jbs.ledger.assetholders.person.Person;
import jbs.ledger.classes.meetings.AbstractMeeting;
import jbs.ledger.classes.meetings.VotableMember;
import jbs.ledger.io.types.meetings.MeetingData;
import jbs.ledger.io.types.meetings.MeetingType;
import jbs.ledger.io.types.meetings.VotableMemberData;
import jbs.ledger.state.LedgerState;

import java.util.ArrayList;
import java.util.Date;
import java.util.UUID;

public final class HireDirectorMeeting extends AbstractMeeting<Person> {
    private HireDirectorMeeting(
            UUID uniqueId,
            String symbol,
            Date date,
            ArrayList<VotableMember<Person>> votableMembers,
            long totalCastableVotes,
            long castVotes,
            long castYesVotes,
            Person directorToHire
    ) {
        super(uniqueId, symbol, date, votableMembers, totalCastableVotes, castVotes, castYesVotes);

        this.directorToHire = directorToHire;
    }

    private final Person directorToHire;

    public Person getDirectorToHire() {
        return directorToHire;
    }

    @Override
    public MeetingType getType() {
        return MeetingType.SHAREHOLDER_HIRE_DIRECTOR;
    }

    @Override
    public MeetingData toData() {
        MeetingData data = super.toData();

        data.member = directorToHire.getUniqueId();

        return data;
    }

    public static HireDirectorMeeting fromData(MeetingData data, LedgerState state) {
        ArrayList<VotableMember<Person>> shareholders = new ArrayList<>();

        for (VotableMemberData vmd : data.votableMembers) {
            shareholders.add(Shareholder.fromData(vmd, state));
        }

        return new HireDirectorMeeting(
                data.uniqueId,
                data.symbol,
                data.date,
                shareholders,
                data.totalCastableVotes,
                data.castVotes,
                data.castYesVotes,
                state.getPerson(data.member)
        );
    }
}
