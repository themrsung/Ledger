package jbs.ledger.classes.meetings.parliament;

import jbs.ledger.assetholders.person.Person;
import jbs.ledger.classes.meetings.VotableMember;
import jbs.ledger.classes.meetings.senate.Senator;
import jbs.ledger.interfaces.organization.Organization;
import jbs.ledger.interfaces.sovereignty.Sovereign;
import jbs.ledger.interfaces.sovereignty.Tripartite;
import jbs.ledger.io.types.meetings.MeetingData;
import jbs.ledger.io.types.meetings.MeetingType;
import jbs.ledger.io.types.meetings.VotableMemberData;
import jbs.ledger.state.LedgerState;

import java.util.ArrayList;
import java.util.Date;
import java.util.UUID;

public final class ChangeLawParliamentBill extends ParliamentBill {
    public static ChangeLawParliamentBill newMeeting(
            Tripartite nation,
            int oldLawIndex,
            String newLaw
    ) {
        UUID uniqueId = nation.getUniqueId();
        String symbol = nation.getSymbol() + "_내각불신임_" + UUID.randomUUID().toString().substring(0, 5);
        Date date = new Date();

        ArrayList<VotableMember<Person>> voters = new ArrayList<>();
        long votes = 0;

        for (Person s : nation.getLegislature().getMembers()) {
            voters.add(new Senator(s, 1));
            votes++;
        }

        return new ChangeLawParliamentBill(
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

    private ChangeLawParliamentBill(
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
        return MeetingType.PARLIAMENT_CHANGE_LAW;
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

        data.oldLawIndex = oldLawIndex;
        data.newLaw = newLaw;

        return data;
    }

    public static ChangeLawParliamentBill fromData(MeetingData data, LedgerState state) {
        ArrayList<VotableMember<Person>> membersOfParliament = new ArrayList<>();

        for (VotableMemberData vmd : data.votableMembers) {
            membersOfParliament.add(MemberOfParliament.fromData(vmd, state));
        }

        return new ChangeLawParliamentBill(
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
