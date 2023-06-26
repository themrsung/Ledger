package jbs.ledger.classes.meetings.referendums;

import jbs.ledger.assetholders.person.Person;
import jbs.ledger.assetholders.sovereignties.nations.Nation;
import jbs.ledger.classes.meetings.VotableMember;
import jbs.ledger.classes.meetings.parliament.MemberOfParliament;
import jbs.ledger.interfaces.organization.Organization;
import jbs.ledger.interfaces.sovereignty.Tripartite;
import jbs.ledger.io.types.meetings.MeetingData;
import jbs.ledger.classes.meetings.MeetingType;
import jbs.ledger.io.types.meetings.VotableMemberData;
import jbs.ledger.state.LedgerState;

import java.util.ArrayList;
import java.util.Date;
import java.util.UUID;

public final class ElectLegisalativeReferendum extends Referendum {
    public static ElectLegisalativeReferendum newMeeting(
            Nation nation,
            Person newSenator
    ) {
        UUID uniqueId = nation.getUniqueId();
        String symbol = nation.getSymbol() + "_의원선임_" + UUID.randomUUID().toString().substring(0, 5);
        Date date = new Date();

        ArrayList<VotableMember<Person>> citizens = new ArrayList<>();
        long votes = 0;

        for (Person s : nation.getCitizens()) {
            citizens.add(new Citizen(s, 1));
            votes++;
        }

        return new ElectLegisalativeReferendum(
                uniqueId,
                symbol,
                date,
                citizens,
                votes,
                0,
                0,
                newSenator
        );
    }

    private ElectLegisalativeReferendum(
            UUID uniqueId,
            String symbol,
            Date date,
            ArrayList<VotableMember<Person>> votableMembers,
            long totalCastableVotes,
            long castVotes,
            long castYesVotes,
            Person newMember
    ) {
        super(uniqueId, symbol, date, votableMembers, totalCastableVotes, castVotes, castYesVotes);

        this.newMember = newMember;
    }

    private final Person newMember;

    public Person getNewMember() {
        return newMember;
    }

    @Override
    public MeetingType getType() {
        return MeetingType.ELECTION_GENERAL;
    }

    @Override
    public void onPassed(Organization<?> organization, LedgerState state) {
        if (organization instanceof Tripartite) {
            Tripartite tripartite = (Tripartite) organization;

            tripartite.getLegislature().addMember(newMember);
        }
    }

    @Override
    public MeetingData toData() {
        MeetingData data = super.toData();

        data.member = newMember.getUniqueId();

        return data;
    }

    public static ElectLegisalativeReferendum fromData(MeetingData data, LedgerState state) {
        ArrayList<VotableMember<Person>> membersOfParliament = new ArrayList<>();

        for (VotableMemberData vmd : data.votableMembers) {
            membersOfParliament.add(MemberOfParliament.fromData(vmd, state));
        }

        return new ElectLegisalativeReferendum(
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
