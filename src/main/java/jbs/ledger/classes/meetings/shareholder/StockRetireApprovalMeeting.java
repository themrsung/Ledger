package jbs.ledger.classes.meetings.shareholder;

import jbs.ledger.assetholders.Assetholder;
import jbs.ledger.assetholders.corporations.Corporation;
import jbs.ledger.classes.meetings.VotableMember;
import jbs.ledger.interfaces.corporate.Corporate;
import jbs.ledger.interfaces.organization.Organization;
import jbs.ledger.io.types.meetings.MeetingData;
import jbs.ledger.classes.meetings.MeetingType;
import jbs.ledger.io.types.meetings.VotableMemberData;
import jbs.ledger.state.LedgerState;
import jbs.ledger.types.assets.basic.Stock;

import java.util.ArrayList;
import java.util.Date;
import java.util.UUID;

public final class StockRetireApprovalMeeting extends ShareholderMeeting {
    public static StockRetireApprovalMeeting newMeeting(
            Corporate corporation,
            LedgerState state,
            long shares
    ) {
        UUID uniqueId = corporation.getUniqueId();
        String symbol = corporation.getSymbol() + "_신주발행_" + UUID.randomUUID().toString().substring(0, 5);
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

        return new StockRetireApprovalMeeting(
                uniqueId,
                symbol,
                date,
                voters,
                votes,
                0,
                0,
                shares
        );
    }
    private StockRetireApprovalMeeting(
            UUID uniqueId,
            String symbol,
            Date date,
            ArrayList<VotableMember<Assetholder>> votableMembers,
            long totalCastableVotes,
            long castVotes,
            long castYesVotes,
            long sharesToRetire
    ) {
        super(uniqueId, symbol, date, votableMembers, totalCastableVotes, castVotes, castYesVotes);

        this.sharesToRetire = sharesToRetire;
    }

    private final long sharesToRetire;

    public long getSharesToRetire() {
        return sharesToRetire;
    }

    @Override
    public MeetingType getType() {
        return MeetingType.SHAREHOLDER_STOCK_RETIRE;
    }

    @Override
    public void onPassed(Organization<?> organization, LedgerState state) {
        if (organization instanceof Corporation) {
            Corporation corp = (Corporation) organization;

            corp.getStocks().remove(new Stock(corp.getSymbol(), getSharesToRetire()));
        }
    }

    @Override
    public MeetingData toData() {
        MeetingData data = super.toData();

        data.shares = sharesToRetire;

        return data;
    }

    public static StockRetireApprovalMeeting fromData(MeetingData data, LedgerState state) {
        ArrayList<VotableMember<Assetholder>> shareholders = new ArrayList<>();

        for (VotableMemberData vmd : data.votableMembers) {
            shareholders.add(Shareholder.fromData(vmd, state));
        }

        return new StockRetireApprovalMeeting(
                data.uniqueId,
                data.symbol,
                data.date,
                shareholders,
                data.totalCastableVotes,
                data.castVotes,
                data.castYesVotes,
                data.shares
        );
    }
}
