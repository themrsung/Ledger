package jbs.ledger.classes.meetings.supremecourt;

import jbs.ledger.assetholders.person.Person;
import jbs.ledger.assetholders.sovereignties.nations.PresidentialRepublic;
import jbs.ledger.classes.meetings.VotableMember;
import jbs.ledger.classes.meetings.parliament.MemberOfParliament;
import jbs.ledger.classes.meetings.senate.SenateBill;
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

public final class ImpeachPresidentSupremeCourtBill extends SupremeCourtBill {
    public static ImpeachPresidentSupremeCourtBill newMeeting(
            Tripartite nation
    ) {
        UUID uniqueId = nation.getUniqueId();
        String symbol = nation.getSymbol() + "_대통령탄핵심판_" + UUID.randomUUID().toString().substring(0, 5);
        Date date = new Date();

        ArrayList<VotableMember<Person>> justices = new ArrayList<>();
        long votes = 0;

        for (Person s : nation.getLegislature().getMembers()) {
            justices.add(new Justice(s, 1));
            votes++;
        }

        return new ImpeachPresidentSupremeCourtBill(
                uniqueId,
                symbol,
                date,
                justices,
                votes,
                0,
                0
        );
    }

    private ImpeachPresidentSupremeCourtBill(
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
        return MeetingType.SUPREME_COURT_IMPEACH_PRESIDENT;
    }

    @Override
    public void onPassed(Organization<?> organization, LedgerState state) {
        if (organization instanceof PresidentialRepublic) {
            PresidentialRepublic pr = (PresidentialRepublic) organization;
            pr.setRepresentative(null);
        }
    }

    @Override
    public MeetingData toData() {
        MeetingData data = super.toData();

        return data;
    }

    public static ImpeachPresidentSupremeCourtBill fromData(MeetingData data, LedgerState state) {
        ArrayList<VotableMember<Person>> membersOfParliament = new ArrayList<>();

        for (VotableMemberData vmd : data.votableMembers) {
            membersOfParliament.add(MemberOfParliament.fromData(vmd, state));
        }

        return new ImpeachPresidentSupremeCourtBill(
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
