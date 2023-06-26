package jbs.ledger.classes.meetings.board;

import jbs.ledger.assetholders.person.Person;
import jbs.ledger.classes.meetings.VotableMember;
import jbs.ledger.classes.meetings.shareholder.CashDividendApprovalMeeting;
import jbs.ledger.classes.meetings.shareholder.Shareholder;
import jbs.ledger.classes.meetings.shareholder.StockDividendApprovalMeeting;
import jbs.ledger.interfaces.corporate.Corporate;
import jbs.ledger.interfaces.organization.Organization;
import jbs.ledger.io.types.meetings.MeetingData;
import jbs.ledger.io.types.meetings.MeetingType;
import jbs.ledger.io.types.meetings.VotableMemberData;
import jbs.ledger.state.LedgerState;

import java.util.ArrayList;
import java.util.Date;
import java.util.UUID;

public final class StockDividendInitiationMeeting extends BoardMeeting {
    public static StockDividendInitiationMeeting newMeeting(
            Corporate corporation,
            long sharesPerShare
    ) {
        UUID uniqueId = corporation.getUniqueId();
        String symbol = corporation.getSymbol() + "_주식배당_" + UUID.randomUUID().toString().substring(0, 5);
        Date date = new Date();

        ArrayList<VotableMember<Person>> voters = new ArrayList<>();
        long votes = 0;

        for (Person d : corporation.getBoard().getMembers()) {
            voters.add(new Director(d, 1));
            votes++;
        }

        return new StockDividendInitiationMeeting(
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

    private StockDividendInitiationMeeting(
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
        return MeetingType.BOARD_STOCK_DIVIDEND;
    }

    @Override
    public void onPassed(Organization<?> organization, LedgerState state) {
        if (organization instanceof Corporate) {
            Corporate corp = (Corporate) organization;
            corp.addOpenMeeting(StockDividendApprovalMeeting.newMeeting(
                    corp,
                    state,
                    getSharesPerShare()
            ));

        }
    }

    @Override
    public MeetingData toData() {
        MeetingData data = super.toData();

        data.sharesPerShare = sharesPerShare;

        return data;
    }

    public static StockDividendInitiationMeeting fromData(MeetingData data, LedgerState state) {
        ArrayList<VotableMember<Person>> directors = new ArrayList<>();

        for (VotableMemberData vmd : data.votableMembers) {
            directors.add(Director.fromData(vmd, state));
        }

        return new StockDividendInitiationMeeting(
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
