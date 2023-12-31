package jbs.ledger.classes.meetings.shareholder;

import jbs.ledger.assetholders.Assetholder;
import jbs.ledger.classes.meetings.VotableMember;
import jbs.ledger.interfaces.corporate.Corporate;
import jbs.ledger.interfaces.organization.Organization;
import jbs.ledger.io.types.meetings.MeetingData;
import jbs.ledger.classes.meetings.MeetingType;
import jbs.ledger.io.types.meetings.VotableMemberData;
import jbs.ledger.state.LedgerState;
import jbs.ledger.types.assets.basic.Stock;
import org.bukkit.Bukkit;

import java.util.ArrayList;
import java.util.Date;
import java.util.UUID;

public final class LiquidationMeeting extends ShareholderMeeting {
    public static LiquidationMeeting newMeeting(
            Corporate corporation,
            LedgerState state
    ) {
        UUID uniqueId = corporation.getUniqueId();
        String symbol = corporation.getSymbol() + "_법인청산_" + UUID.randomUUID().toString().substring(0, 5);
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

        return new LiquidationMeeting(
                uniqueId,
                symbol,
                date,
                voters,
                votes,
                0,
                0
        );
    }

    private LiquidationMeeting(
            UUID uniqueId,
            String symbol,
            Date date,
            ArrayList<VotableMember<Assetholder>> votableMembers,
            long totalCastableVotes,
            long castVotes,
            long castYesVotes
    ) {
        super(uniqueId, symbol, date, votableMembers, totalCastableVotes, castVotes, castYesVotes);
    }

    @Override
    public MeetingType getType() {
        return MeetingType.SHAREHOLDER_LIQUIDATE;
    }

    @Override
    public void onPassed(Organization<?> organization, LedgerState state) {
        // TODO TODO TODO TODO TODO
        Bukkit.getLogger().info("LIQUIDATION IS NOT SUPPORTED AT THIS TIME");
    }

    @Override
    public MeetingData toData() {
        MeetingData data = super.toData();

        return data;
    }

    public static LiquidationMeeting fromData(MeetingData data, LedgerState state) {
        ArrayList<VotableMember<Assetholder>> shareholders = new ArrayList<>();

        for (VotableMemberData vmd : data.votableMembers) {
            shareholders.add(Shareholder.fromData(vmd, state));
        }

        return new LiquidationMeeting(
                data.uniqueId,
                data.symbol,
                data.date,
                shareholders,
                data.totalCastableVotes,
                data.castVotes,
                data.castYesVotes
        );
    }
}
