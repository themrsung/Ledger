package jbs.ledger.classes.meetings.board;

import jbs.ledger.assetholders.person.Person;
import jbs.ledger.classes.meetings.VotableMember;
import jbs.ledger.classes.meetings.shareholder.Shareholder;
import jbs.ledger.interfaces.corporate.Corporate;
import jbs.ledger.io.types.meetings.MeetingData;
import jbs.ledger.io.types.meetings.MeetingType;
import jbs.ledger.io.types.meetings.VotableMemberData;
import jbs.ledger.state.LedgerState;

import java.util.ArrayList;
import java.util.Date;
import java.util.UUID;

public final class StockSplitInitiationMeeting extends BoardMeeting {
    public static StockSplitInitiationMeeting newMeeting(
            Corporate corporation,
            long sharesPerShare
    ) {
        UUID uniqueId = corporation.getUniqueId();
        String symbol = corporation.getSymbol() + "_주식분할_" + UUID.randomUUID().toString().substring(0, 5);
        Date date = new Date();

        ArrayList<VotableMember<Person>> voters = new ArrayList<>();
        long votes = 0;

        for (Person d : corporation.getBoard().getMembers()) {
            voters.add(new Director(d, 1));
            votes++;
        }

        return new StockSplitInitiationMeeting(
                uniqueId,
                symbol,
                date,
                voters,
                votes,
                0,
                0,
                sharesPerShare
        );
    }

    private StockSplitInitiationMeeting(
            UUID uniqueId,
            String symbol,
            Date date,
            ArrayList<VotableMember<Person>> votableMembers,
            long totalCastableVotes,
            long castVotes,
            long castYesVotes,
            long sharesPerShare
    ) {
        super(uniqueId, symbol, date, votableMembers, totalCastableVotes, castVotes, castYesVotes);

        this.sharesPerShare = sharesPerShare;
    }

    private final long sharesPerShare;

    public long getSharesPerShare() {
        return sharesPerShare;
    }

    @Override
    public MeetingType getType() {
        return null;
    }

    @Override
    public MeetingData toData() {
        MeetingData data = super.toData();

        data.sharesPerShare = sharesPerShare;

        return data;
    }

    public static StockSplitInitiationMeeting fromData(MeetingData data, LedgerState state) {
        ArrayList<VotableMember<Person>> directors = new ArrayList<>();

        for (VotableMemberData vmd : data.votableMembers) {
            directors.add(Director.fromData(vmd, state));
        }

        return new StockSplitInitiationMeeting(
                data.uniqueId,
                data.symbol,
                data.date,
                directors,
                data.totalCastableVotes,
                data.castVotes,
                data.castYesVotes,
                data.sharesPerShare
        );
    }
}
