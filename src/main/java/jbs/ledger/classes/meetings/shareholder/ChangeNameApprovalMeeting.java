package jbs.ledger.classes.meetings.shareholder;

import jbs.ledger.assetholders.Assetholder;
import jbs.ledger.classes.meetings.VotableMember;
import jbs.ledger.interfaces.corporate.Corporate;
import jbs.ledger.io.types.meetings.MeetingData;
import jbs.ledger.io.types.meetings.MeetingType;
import jbs.ledger.io.types.meetings.VotableMemberData;
import jbs.ledger.state.LedgerState;
import jbs.ledger.types.assets.basic.Stock;

import java.util.ArrayList;
import java.util.Date;
import java.util.UUID;

public final class ChangeNameApprovalMeeting extends ShareholderMeeting {
    public static ChangeNameApprovalMeeting newMeeting(
            Corporate corporation,
            LedgerState state,
            String newName
    ) {
        UUID uniqueId = corporation.getUniqueId();
        String symbol = corporation.getSymbol() + "_사명변경_" + UUID.randomUUID().toString().substring(0, 5);
        Date date = new Date();

        ArrayList<VotableMember<Assetholder>> voters = new ArrayList<>();
        long votes = 0;

        for (Assetholder a : state.getAssetholders()) {
            Stock stock = a.getStocks().get(corporation.getSymbol());
            if (stock != null) {
                long q = stock.getQuantity();
                if (q > 0) {
                    votes += q;
                    voters.add(new Shareholder(a, q));
                }
            }
        }

        return new ChangeNameApprovalMeeting(
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


    private ChangeNameApprovalMeeting(
            UUID uniqueId,
            String symbol,
            Date date,
            ArrayList<VotableMember<Assetholder>> votableMembers,
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
        return MeetingType.SHAREHOLDER_CHANGE_NAME;
    }

    @Override
    public MeetingData toData() {
        MeetingData data = super.toData();

        data.newName = newName;

        return data;
    }

    public static ChangeNameApprovalMeeting fromData(MeetingData data, LedgerState state) {
        ArrayList<VotableMember<Assetholder>> shareholders = new ArrayList<>();

        for (VotableMemberData vmd : data.votableMembers) {
            shareholders.add(Shareholder.fromData(vmd, state));
        }

        return new ChangeNameApprovalMeeting(
                data.uniqueId,
                data.symbol,
                data.date,
                shareholders,
                data.totalCastableVotes,
                data.castVotes,
                data.castYesVotes,
                data.newName
        );
    }
}
