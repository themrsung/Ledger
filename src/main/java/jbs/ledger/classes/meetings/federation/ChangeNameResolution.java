package jbs.ledger.classes.meetings.federation;

import jbs.ledger.assetholders.sovereignties.federations.Federation;
import jbs.ledger.classes.meetings.VotableMember;
import jbs.ledger.interfaces.organization.Organization;
import jbs.ledger.interfaces.sovereignty.Sovereign;
import jbs.ledger.io.types.meetings.MeetingData;
import jbs.ledger.classes.meetings.MeetingType;
import jbs.ledger.io.types.meetings.VotableMemberData;
import jbs.ledger.state.LedgerState;

import java.util.ArrayList;
import java.util.Date;
import java.util.UUID;

public final class ChangeNameResolution extends FederationResolution {
    public static ChangeNameResolution newResolution(
            Federation federation,
            String newName
    ) {
        UUID uniqueId = federation.getUniqueId();
        String symbol = federation.getSymbol() + "_국명변경_" + UUID.randomUUID().toString().substring(0, 5);
        Date date = new Date();

        ArrayList<VotableMember<Sovereign>> voters = new ArrayList<>();
        long votes = 0;

        for (Sovereign s : federation.getMembers()) {
            voters.add(new FederationMember(s, 1));
            votes++;
        }

        return new ChangeNameResolution(
                uniqueId,
                symbol,
                date,
                voters,
                votes,
                0,
                0,
                newName
        );
    }

    private ChangeNameResolution(
            UUID uniqueId,
            String symbol,
            Date date,
            ArrayList<VotableMember<Sovereign>> votableMembers,
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
        return MeetingType.FEDERATION_CHANGE_NAME;
    }

    @Override
    public void onPassed(Organization<?> organization, LedgerState state) {
        if (organization instanceof Federation) {
            Federation fed = (Federation) organization;
            fed.setName(getNewName());
        }
    }

    @Override
    public MeetingData toData() {
        MeetingData data = super.toData();

        data.newName = newName;

        return data;
    }

    public static ChangeNameResolution fromData(MeetingData data, LedgerState state) {
        ArrayList<VotableMember<Sovereign>> sovereignties = new ArrayList<>();

        for (VotableMemberData vmd : data.votableMembers) {
            sovereignties.add(FederationMember.fromData(vmd, state));
        }

        return new ChangeNameResolution(
                data.uniqueId,
                data.symbol,
                data.date,
                sovereignties,
                data.totalCastableVotes,
                data.castVotes,
                data.castYesVotes,
                data.newName
        );

    }
}
