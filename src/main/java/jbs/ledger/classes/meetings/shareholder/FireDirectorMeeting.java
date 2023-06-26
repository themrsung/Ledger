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

public final class FireDirectorMeeting extends AbstractMeeting<Person> {
    private FireDirectorMeeting(
            UUID uniqueId,
            String symbol,
            Date date,
            ArrayList<VotableMember<Person>> votableMembers,
            long totalCastableVotes,
            long castVotes,
            long castYesVotes,
            Person directorToFire
    ) {
        super(uniqueId, symbol, date, votableMembers, totalCastableVotes, castVotes, castYesVotes);

        this.directorToFire = directorToFire;
    }

    private final Person directorToFire;

    public Person getDirectorToFire() {
        return directorToFire;
    }

    @Override
    public MeetingType getType() {
        return MeetingType.SHAREHOLDER_FIRE_DIRECTOR;
    }

    @Override
    public MeetingData toData() {
        MeetingData data = super.toData();

        data.member = directorToFire.getUniqueId();

        return data;
    }

    public static FireDirectorMeeting fromData(MeetingData data, LedgerState state) {
        ArrayList<VotableMember<Person>> shareholders = new ArrayList<>();

        for (VotableMemberData vmd : data.votableMembers) {
            shareholders.add(Shareholder.fromData(vmd, state));
        }

        return new FireDirectorMeeting(
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
