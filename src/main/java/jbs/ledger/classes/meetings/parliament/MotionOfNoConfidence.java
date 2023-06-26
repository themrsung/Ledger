package jbs.ledger.classes.meetings.parliament;

import jbs.ledger.assetholders.person.Person;
import jbs.ledger.assetholders.sovereignties.nations.ParliamentaryRepublic;
import jbs.ledger.classes.meetings.VotableMember;
import jbs.ledger.classes.meetings.federation.FederationMember;
import jbs.ledger.classes.meetings.federation.FederationResolution;
import jbs.ledger.classes.meetings.senate.ChangeLawSenateBill;
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

public final class MotionOfNoConfidence extends ParliamentBill {
    public static MotionOfNoConfidence newMeeting(
            Tripartite nation
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

        return new MotionOfNoConfidence(
                uniqueId,
                symbol,
                date,
                voters,
                votes,
                0,
                0
        );
    }

    private MotionOfNoConfidence(
            UUID uniqueId,
            String symbol,
            Date date,
            ArrayList<VotableMember<Person>> votableMembers,
            long totalCastableVotes,
            long castVotes,
            long castYesVotes
    ) {
        super(uniqueId, symbol, date, votableMembers, totalCastableVotes, castVotes, castYesVotes);
    }

    @Override
    public MeetingType getType() {
        return MeetingType.PARLIAMENT_NO_CONFIDENCE;
    }

    @Override
    public void onPassed(Organization<?> organization, LedgerState state) {
        if (organization instanceof ParliamentaryRepublic) {
            ParliamentaryRepublic pr = (ParliamentaryRepublic) organization;
            pr.setRepresentative(null);
        }
    }

    @Override
    public MeetingData toData() {
        MeetingData data = super.toData();

        return data;
    }

    public static MotionOfNoConfidence fromData(MeetingData data, LedgerState state) {
        ArrayList<VotableMember<Person>> membersOfParliament = new ArrayList<>();

        for (VotableMemberData vmd : data.votableMembers) {
            membersOfParliament.add(MemberOfParliament.fromData(vmd, state));
        }

        return new MotionOfNoConfidence(
                data.uniqueId,
                data.symbol,
                data.date,
                membersOfParliament,
                data.totalCastableVotes,
                data.castVotes,
                data.castYesVotes
        );

    }
}
