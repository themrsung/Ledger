package jbs.ledger.classes.meetings.referendums;

import jbs.ledger.assetholders.person.Person;
import jbs.ledger.assetholders.sovereignties.nations.Nation;
import jbs.ledger.classes.meetings.VotableMember;
import jbs.ledger.classes.meetings.parliament.MemberOfParliament;
import jbs.ledger.interfaces.organization.Organization;
import jbs.ledger.io.types.meetings.MeetingData;
import jbs.ledger.classes.meetings.MeetingType;
import jbs.ledger.io.types.meetings.VotableMemberData;
import jbs.ledger.state.LedgerState;

import java.util.ArrayList;
import java.util.Date;
import java.util.UUID;

public final class ChangeNameReferendum extends Referendum {
    public static ChangeNameReferendum newMeeting(
            Nation nation,
            String newName
    ) {
        UUID uniqueId = nation.getUniqueId();
        String symbol = nation.getSymbol() + "_국명변경_" + UUID.randomUUID().toString().substring(0, 5);
        Date date = new Date();

        ArrayList<VotableMember<Person>> citizens = new ArrayList<>();
        long votes = 0;

        for (Person s : nation.getCitizens()) {
            citizens.add(new Citizen(s, 1));
            votes++;
        }

        return new ChangeNameReferendum(
                uniqueId,
                symbol,
                date,
                citizens,
                votes,
                0,
                0,
                newName
        );
    }

    private ChangeNameReferendum(
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
        return MeetingType.REFERENDUM_CHANGE_NAME;
    }

    @Override
    public void onPassed(Organization<?> organization, LedgerState state) {
        if (organization instanceof Nation) {
            Nation sov = (Nation) organization;

            sov.setName(getNewName());
        }
    }

    @Override
    public MeetingData toData() {
        MeetingData data = super.toData();

        data.newName = newName;

        return data;
    }

    public static ChangeNameReferendum fromData(MeetingData data, LedgerState state) {
        ArrayList<VotableMember<Person>> membersOfParliament = new ArrayList<>();

        for (VotableMemberData vmd : data.votableMembers) {
            membersOfParliament.add(MemberOfParliament.fromData(vmd, state));
        }

        return new ChangeNameReferendum(
                data.uniqueId,
                data.symbol,
                data.date,
                membersOfParliament,
                data.totalCastableVotes,
                data.castVotes,
                data.castYesVotes,
                data.newName
        );

    }
}
