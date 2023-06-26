package jbs.ledger.classes.meetings.shareholder;

import jbs.ledger.assetholders.Assetholder;
import jbs.ledger.assetholders.person.Person;
import jbs.ledger.classes.meetings.AbstractMeeting;
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

public final class FireCeoMeeting extends ShareholderMeeting {
    public static FireCeoMeeting newMeeting(
            Corporate corporation,
            LedgerState state
    ) {
        UUID uniqueId = corporation.getUniqueId();
        String symbol = corporation.getSymbol() + "_대표이사해임_" + UUID.randomUUID().toString().substring(0, 5);
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

        return new FireCeoMeeting(
                uniqueId,
                symbol,
                date,
                voters,
                votes,
                0,
                0
        );
    }

    private FireCeoMeeting(
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
        return MeetingType.SHAREHOLDER_FIRE_CEO;
    }

    @Override
    public MeetingData toData() {
        MeetingData data = super.toData();

        return data;
    }

    public static FireCeoMeeting fromData(MeetingData data, LedgerState state) {
        ArrayList<VotableMember<Assetholder>> shareholders = new ArrayList<>();

        for (VotableMemberData vmd : data.votableMembers) {
            shareholders.add(Shareholder.fromData(vmd, state));
        }

        return new FireCeoMeeting(
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
