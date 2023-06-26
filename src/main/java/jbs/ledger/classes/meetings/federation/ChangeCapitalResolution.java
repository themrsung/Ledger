package jbs.ledger.classes.meetings.federation;

import jbs.ledger.assetholders.person.Person;
import jbs.ledger.assetholders.sovereignties.federations.Federation;
import jbs.ledger.classes.meetings.VotableMember;
import jbs.ledger.classes.meetings.board.Director;
import jbs.ledger.classes.meetings.board.StockDividendInitiationMeeting;
import jbs.ledger.interfaces.corporate.Corporate;
import jbs.ledger.interfaces.organization.Organization;
import jbs.ledger.interfaces.sovereignty.Sovereign;
import jbs.ledger.io.types.meetings.MeetingData;
import jbs.ledger.io.types.meetings.MeetingType;
import jbs.ledger.io.types.meetings.VotableMemberData;
import jbs.ledger.state.LedgerState;

import java.util.ArrayList;
import java.util.Date;
import java.util.UUID;

public final class ChangeCapitalResolution extends FederationResolution {
    public static ChangeCapitalResolution newResolution(
            Federation federation,
            Sovereign newCapital
    ) {
        UUID uniqueId = federation.getUniqueId();
        String symbol = federation.getSymbol() + "_수도변경_" + UUID.randomUUID().toString().substring(0, 5);
        Date date = new Date();

        ArrayList<VotableMember<Sovereign>> voters = new ArrayList<>();
        long votes = 0;

        for (Sovereign s : federation.getMembers()) {
            voters.add(new FederationMember(s, 1));
            votes++;
        }

        return new ChangeCapitalResolution(
                uniqueId,
                symbol,
                date,
                voters,
                votes,
                0,
                0,
                newCapital
        );
    }

    private ChangeCapitalResolution(
            UUID uniqueId,
            String symbol,
            Date date,
            ArrayList<VotableMember<Sovereign>> votableMembers,
            long totalCastableVotes,
            long castVotes,
            long castYesVotes,
            Sovereign capital
    ) {
        super(uniqueId, symbol, date, votableMembers, totalCastableVotes, castVotes, castYesVotes);
        this.newCapital = capital;
    }

    private final Sovereign newCapital;

    public Sovereign getNewCapital() {
        return newCapital;
    }

    @Override
    public MeetingType getType() {
        return MeetingType.FEDERATION_NEW_MEMBER;
    }

    @Override
    public void onPassed(Organization<?> organization, LedgerState state) {
        if (organization instanceof Federation) {
            Federation fed = (Federation) organization;
            fed.setRepresentative(getNewCapital());
        }
    }

    @Override
    public MeetingData toData() {
        MeetingData data = super.toData();

        data.member = newCapital.getUniqueId();

        return data;
    }

    public static ChangeCapitalResolution fromData(MeetingData data, LedgerState state) {
        ArrayList<VotableMember<Sovereign>> sovereignties = new ArrayList<>();

        for (VotableMemberData vmd : data.votableMembers) {
            sovereignties.add(FederationMember.fromData(vmd, state));
        }

        return new ChangeCapitalResolution(
                data.uniqueId,
                data.symbol,
                data.date,
                sovereignties,
                data.totalCastableVotes,
                data.castVotes,
                data.castYesVotes,
                state.getSovereign(data.member)
        );

    }
}
