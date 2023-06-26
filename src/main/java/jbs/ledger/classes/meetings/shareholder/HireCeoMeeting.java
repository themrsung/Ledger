package jbs.ledger.classes.meetings.shareholder;

import jbs.ledger.assetholders.person.Person;
import jbs.ledger.classes.meetings.AbstractMeeting;
import jbs.ledger.classes.meetings.VotableMember;
import jbs.ledger.io.types.meetings.MeetingData;
import jbs.ledger.io.types.meetings.MeetingType;
import jbs.ledger.io.types.meetings.VotableMemberData;
import jbs.ledger.state.LedgerState;
import jdk.internal.misc.VM;

import java.util.ArrayList;
import java.util.Date;
import java.util.UUID;

public final class HireCeoMeeting extends AbstractMeeting<Person> {
    private HireCeoMeeting(
            UUID uniqueId,
            String symbol,
            Date date,
            ArrayList<VotableMember<Person>> votableMembers,
            long totalCastableVotes,
            long castVotes,
            long castYesVotes,
            Person ceoToHire
    ) {
        super(uniqueId, symbol, date, votableMembers, totalCastableVotes, castVotes, castYesVotes);

        this.ceoToHire = ceoToHire;
    }

    private final Person ceoToHire;

    public Person getCeoToHire() {
        return ceoToHire;
    }

    @Override
    public MeetingType getType() {
        return MeetingType.SHAREHOLDER_HIRE_CEO;
    }

    @Override
    public MeetingData toData() {
        MeetingData data = super.toData();

        data.member = ceoToHire.getUniqueId();

        return data;
    }

    public static HireCeoMeeting fromData(MeetingData data, LedgerState state) {
        ArrayList<VotableMember<Person>> shareholders = new ArrayList<>();

        for (VotableMemberData vmd : data.votableMembers) {
            shareholders.add(Shareholder.fromData(vmd, state));
        }

        return new HireCeoMeeting(
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
