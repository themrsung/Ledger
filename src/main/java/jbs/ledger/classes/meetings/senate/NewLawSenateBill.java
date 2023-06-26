package jbs.ledger.classes.meetings.senate;

import jbs.ledger.assetholders.person.Person;
import jbs.ledger.classes.meetings.VotableMember;
import jbs.ledger.classes.meetings.parliament.MemberOfParliament;
import jbs.ledger.interfaces.organization.Organization;
import jbs.ledger.interfaces.sovereignty.Sovereign;
import jbs.ledger.interfaces.sovereignty.Tripartite;
import jbs.ledger.io.types.meetings.MeetingData;
import jbs.ledger.classes.meetings.MeetingType;
import jbs.ledger.io.types.meetings.VotableMemberData;
import jbs.ledger.state.LedgerState;

import java.util.ArrayList;
import java.util.Date;
import java.util.UUID;

public final class NewLawSenateBill extends SenateBill {
    public static NewLawSenateBill newMeeting(
            Tripartite nation,
            String newLaw
    ) {
        UUID uniqueId = nation.getUniqueId();
        String symbol = nation.getSymbol() + "_법률제정안_" + UUID.randomUUID().toString().substring(0, 5);
        Date date = new Date();

        ArrayList<VotableMember<Person>> voters = new ArrayList<>();
        long votes = 0;

        for (Person s : nation.getLegislature().getMembers()) {
            voters.add(new Senator(s, 1));
            votes++;
        }

        return new NewLawSenateBill(
                uniqueId,
                symbol,
                date,
                voters,
                votes,
                0,
                0,
                newLaw
        );
    }

    private NewLawSenateBill(
            UUID uniqueId,
            String symbol,
            Date date,
            ArrayList<VotableMember<Person>> votableMembers,
            long totalCastableVotes,
            long castVotes,
            long castYesVotes,
            String newLaw
    ) {
        super(uniqueId, symbol, date, votableMembers, totalCastableVotes, castVotes, castYesVotes);

        this.newLaw = newLaw;
    }

    private final String newLaw;

    public String getNewLaw() {
        return newLaw;
    }

    @Override
    public MeetingType getType() {
        return MeetingType.SENATE_NEW_LAW;
    }
    @Override
    public void onPassed(Organization<?> organization, LedgerState state) {
        if (organization instanceof Sovereign) {
            Sovereign sov = (Sovereign) organization;

            sov.addLaw(getNewLaw());
        }
    }
    @Override
    public MeetingData toData() {
        MeetingData data = super.toData();

        data.newLaw = newLaw;

        return data;
    }

    public static NewLawSenateBill fromData(MeetingData data, LedgerState state) {
        ArrayList<VotableMember<Person>> membersOfParliament = new ArrayList<>();

        for (VotableMemberData vmd : data.votableMembers) {
            membersOfParliament.add(MemberOfParliament.fromData(vmd, state));
        }

        return new NewLawSenateBill(
                data.uniqueId,
                data.symbol,
                data.date,
                membersOfParliament,
                data.totalCastableVotes,
                data.castVotes,
                data.castYesVotes,
                data.newLaw
        );

    }
}
