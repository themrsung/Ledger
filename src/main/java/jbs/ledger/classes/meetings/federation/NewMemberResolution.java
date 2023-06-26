package jbs.ledger.classes.meetings.federation;

import jbs.ledger.assetholders.sovereignties.federations.Federation;
import jbs.ledger.classes.meetings.VotableMember;
import jbs.ledger.interfaces.sovereignty.Sovereign;
import jbs.ledger.io.types.meetings.MeetingData;
import jbs.ledger.io.types.meetings.MeetingType;
import jbs.ledger.io.types.meetings.VotableMemberData;
import jbs.ledger.state.LedgerState;

import java.util.ArrayList;
import java.util.Date;
import java.util.UUID;

public final class NewMemberResolution extends FederationResolution {
    public static NewMemberResolution newResolution(
            Federation federation,
            Sovereign newMember
    ) {
        UUID uniqueId = federation.getUniqueId();
        String symbol = federation.getSymbol() + "_회원국초대_" + UUID.randomUUID().toString().substring(0, 5);
        Date date = new Date();

        ArrayList<VotableMember<Sovereign>> voters = new ArrayList<>();
        long votes = 0;

        for (Sovereign s : federation.getMembers()) {
            voters.add(new FederationMember(s, 1));
            votes++;
        }

        return new NewMemberResolution(
                uniqueId,
                symbol,
                date,
                voters,
                votes,
                0,
                0,
                newMember
        );
    }
    private NewMemberResolution(
            UUID uniqueId,
            String symbol,
            Date date,
            ArrayList<VotableMember<Sovereign>> votableMembers,
            long totalCastableVotes,
            long castVotes,
            long castYesVotes,
            Sovereign member
    ) {
        super(uniqueId, symbol, date, votableMembers, totalCastableVotes, castVotes, castYesVotes);
        this.newMember = member;
    }

    private final Sovereign newMember;

    public Sovereign  getNewMember() {
        return newMember;
    }

    @Override
    public MeetingType getType() {
        return MeetingType.FEDERATION_NEW_MEMBER;
    }

    @Override
    public MeetingData toData() {
        MeetingData data = super.toData();

        data.member = newMember.getUniqueId();

        return data;
    }

    public static NewMemberResolution fromData(MeetingData data, LedgerState state) {
        ArrayList<VotableMember<Sovereign>> sovereignties = new ArrayList<>();

        for (VotableMemberData vmd : data.votableMembers) {
            sovereignties.add(FederationMember.fromData(vmd, state));
        }

        return new NewMemberResolution(
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
