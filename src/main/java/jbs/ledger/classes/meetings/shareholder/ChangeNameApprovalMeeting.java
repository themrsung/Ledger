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

public final class ChangeNameApprovalMeeting extends AbstractMeeting<Person> {
    private ChangeNameApprovalMeeting(
            UUID uniqueId,
            String symbol,
            Date date,
            ArrayList<VotableMember<Person>> votableMembers,
            long totalCastableVotes,
            long castVotes,
            long castYesVotes,
            String newName
    ) {
        super(uniqueId, symbol, date, votableMembers, totalCastableVotes, castVotes, castYesVotes);
        this.newName = newName;
    }

    private final String newName;

    public String getNewName() {
        return newName;
    }

    @Override
    public MeetingType getType() {
        return MeetingType.SHAREHOLDER_CHANGE_NAME;
    }

    @Override
    public MeetingData toData() {
        MeetingData data = super.toData();

        data.newName = newName;

        return data;
    }

    public static ChangeNameApprovalMeeting fromData(MeetingData data, LedgerState state) {
        ArrayList<VotableMember<Person>> shareholders = new ArrayList<>();

        for (VotableMemberData vmd : data.votableMembers) {
            shareholders.add(Shareholder.fromData(vmd, state));
        }

        return new ChangeNameApprovalMeeting(
                data.uniqueId,
                data.symbol,
                data.date,
                shareholders,
                data.totalCastableVotes,
                data.castVotes,
                data.castYesVotes,
                data.newName
        );
    }
}
