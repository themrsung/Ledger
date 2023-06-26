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

public final class ChangeLawSenateBill extends SenateBill {
    public static ChangeLawSenateBill newMeeting(
            Tripartite nation,
            int oldLawIndex,
            String newLaw
    ) {
        UUID uniqueId = nation.getUniqueId();
        String symbol = nation.getSymbol() + "_법률개정안_" + UUID.randomUUID().toString().substring(0, 5);
        Date date = new Date();

        ArrayList<VotableMember<Person>> voters = new ArrayList<>();
        long votes = 0;

        for (Person s : nation.getLegislature().getMembers()) {
            voters.add(new Senator(s, 1));
            votes++;
        }

        return new ChangeLawSenateBill(
                uniqueId,
                symbol,
                date,
                voters,
                votes,
                0,
                0,
                oldLawIndex,
                newLaw
        );
    }

    private ChangeLawSenateBill(
            UUID uniqueId,
            String symbol,
            Date date,
            ArrayList<VotableMember<Person>> votableMembers,
            long totalCastableVotes,
            long castVotes,
            long castYesVotes,
            int oldLawIndex,
            String newLaw
    ) {
        super(uniqueId, symbol, date, votableMembers, totalCastableVotes, castVotes, castYesVotes);

        this.oldLawIndex = oldLawIndex;
        this.newLaw = newLaw;
    }

    private final int oldLawIndex;
    private final String newLaw;

    public int getOldLawIndex() {
        return oldLawIndex;
    }

    public String getNewLaw() {
        return newLaw;
    }

    @Override
    public MeetingType getType() {
        return MeetingType.SENATE_CHANGE_LAW;
    }
    @Override
    public void onPassed(Organization<?> organization, LedgerState state) {
        if (organization instanceof Sovereign) {
            Sovereign sov = (Sovereign) organization;

            sov.changeLaw(getOldLawIndex(), getNewLaw());
        }
    }
    @Override
    public MeetingData toData() {
        MeetingData data = super.toData();

        data.newLaw = newLaw;
        data.oldLawIndex = oldLawIndex;

        return data;
    }

    public static ChangeLawSenateBill fromData(MeetingData data, LedgerState state) {
        ArrayList<VotableMember<Person>> membersOfParliament = new ArrayList<>();

        for (VotableMemberData vmd : data.votableMembers) {
            membersOfParliament.add(MemberOfParliament.fromData(vmd, state));
        }

        return new ChangeLawSenateBill(
                data.uniqueId,
                data.symbol,
                data.date,
                membersOfParliament,
                data.totalCastableVotes,
                data.castVotes,
                data.castYesVotes,
                data.oldLawIndex,
                data.newLaw
        );

    }
}
