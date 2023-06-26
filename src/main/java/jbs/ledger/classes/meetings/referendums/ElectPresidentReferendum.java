package jbs.ledger.classes.meetings.referendums;

import jbs.ledger.assetholders.person.Person;
import jbs.ledger.assetholders.sovereignties.nations.Nation;
import jbs.ledger.assetholders.sovereignties.nations.PresidentialRepublic;
import jbs.ledger.classes.meetings.VotableMember;
import jbs.ledger.classes.meetings.parliament.MemberOfParliament;
import jbs.ledger.interfaces.organization.Meeting;
import jbs.ledger.interfaces.organization.Organization;
import jbs.ledger.io.types.meetings.MeetingData;
import jbs.ledger.classes.meetings.MeetingType;
import jbs.ledger.io.types.meetings.VotableMemberData;
import jbs.ledger.state.LedgerState;

import java.util.ArrayList;
import java.util.Date;
import java.util.UUID;

public final class ElectPresidentReferendum extends Referendum {
    public static ElectPresidentReferendum newMeeting(
            Nation nation,
            Person newPresident
    ) {
        UUID uniqueId = nation.getUniqueId();
        String symbol = nation.getSymbol() + "_대통령선임_" + UUID.randomUUID().toString().substring(0, 5);
        Date date = new Date();

        ArrayList<VotableMember<Person>> citizens = new ArrayList<>();
        long votes = 0;

        for (Person s : nation.getCitizens()) {
            citizens.add(new Citizen(s, 1));
            votes++;
        }

        return new ElectPresidentReferendum(
                uniqueId,
                symbol,
                date,
                citizens,
                votes,
                0,
                0,
                newPresident
        );
    }

    private ElectPresidentReferendum(
            UUID uniqueId,
            String symbol,
            Date date,
            ArrayList<VotableMember<Person>> votableMembers,
            long totalCastableVotes,
            long castVotes,
            long castYesVotes,
            Person newPresident
    ) {
        super(uniqueId, symbol, date, votableMembers, totalCastableVotes, castVotes, castYesVotes);

        this.newPresident = newPresident;
    }

    private final Person newPresident;

    public Person getNewPresident() {
        return newPresident;
    }

    @Override
    public MeetingType getType() {
        return MeetingType.ELECTION_PRESIDENTIAL;
    }

    @Override
    public void onPassed(Organization<?> organization, LedgerState state) {
        if (organization instanceof PresidentialRepublic) {
            PresidentialRepublic prc = (PresidentialRepublic) organization;

            prc.setRepresentative(newPresident);

            for (Meeting<Person> vote : prc.getOpenMeetings()) {
                if (vote.getType() == MeetingType.ELECTION_PRESIDENTIAL) {
                    prc.removeOpenMeeting(vote);
                }
            }
        }
    }

    @Override
    public MeetingData toData() {
        MeetingData data = super.toData();

        data.member = newPresident.getUniqueId();

        return data;
    }

    public static ElectPresidentReferendum fromData(MeetingData data, LedgerState state) {
        ArrayList<VotableMember<Person>> membersOfParliament = new ArrayList<>();

        for (VotableMemberData vmd : data.votableMembers) {
            membersOfParliament.add(MemberOfParliament.fromData(vmd, state));
        }

        return new ElectPresidentReferendum(
                data.uniqueId,
                data.symbol,
                data.date,
                membersOfParliament,
                data.totalCastableVotes,
                data.castVotes,
                data.castYesVotes,
                state.getPerson(data.member)
        );

    }
}
